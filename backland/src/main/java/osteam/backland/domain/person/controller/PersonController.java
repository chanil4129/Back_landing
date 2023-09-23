package osteam.backland.domain.person.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osteam.backland.domain.person.controller.request.PersonCreateRequest;
import osteam.backland.domain.person.controller.request.SearchByNameRequest;
import osteam.backland.domain.person.controller.request.SearchByPhoneRequest;
import osteam.backland.domain.person.controller.response.PersonBooksResponse;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.entity.dto.PersonOneToManyDTO;
import osteam.backland.domain.person.service.PersonCreateService;
import osteam.backland.domain.person.service.PersonSearchService;
import osteam.backland.domain.person.service.PersonUpdateService;
import osteam.backland.domain.person.service.PersonValidationService;

import java.util.Set;

/**
 * PersonController
 * 등록 수정 검색 구현
 * <p>
 * 등록 - 입력된 이름과 전화번호를 personOnly, personOneToOne, personOneToMany에 저장
 * 수정 - 이미 등록된 전화번호가 입력될 경우 해당 전화번호의 소유주 이름을 변경
 * 검색 - 전체 출력, 이름으로 검색, 전화번호로 검색 구현, 검색은 전부 OneToMany 테이블로 진행.
 */
@Slf4j
@Tag(name = "전화번호부", description = "전화번호부 관련 api입니다.")
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonCreateService personCreateService;
    private final PersonUpdateService personUpdateService;
    private final PersonSearchService personSearchService;
    private final PersonValidationService personValidationService;

    @GetMapping("/init")
    public ResponseEntity<String> person() {
        log.debug("Controller : Application Init");
        PersonDTO personDTO=new PersonDTO("홍길동","01000000000");
        personCreateService.createAll(personDTO);
        return ResponseEntity.ok("init");
    }

    /**
     * 등록 기능
     * personRequest를 service에 그대로 넘겨주지 말 것.
     *
     * @param personCreateRequest
     * @return 성공 시 이름 반환
     */
    @Operation(summary = "주소록 등록", description = "이름, 전화번호로 주소록을 만듭니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/create")
    public String person(@RequestBody @Valid PersonCreateRequest personCreateRequest) {
        log.debug("Controller : Create");
        PersonDTO personDTO = new PersonDTO(personCreateRequest.getName(), personCreateRequest.getPhone());
        personCreateService.oneCreate(personDTO);
        personCreateService.oneToOneCreate(personDTO);
        personCreateService.oneToManyCreate(personDTO);
        return personCreateRequest.getName();
    }

    /**
     * 전체 검색 기능
     */
    @GetMapping("/search-all")
    @Operation(summary = "전체 검색 기능", description = "주소록 전체를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PersonBooksResponse.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = PersonBooksResponse.class)))
    })
    public ResponseEntity<PersonBooksResponse> getPeople() {
        log.debug("Controller : Search All");
        Set<PersonDTO> personOnlyDTOS = personSearchService.searchAllPersonOnly();
        Set<PersonDTO> personOneToOneDTOS = personSearchService.searchAllPersonOneToOne();
        Set<PersonOneToManyDTO> personOneToManyDTOS = personSearchService.searchAllPersonOneToMany();

        PersonBooksResponse response = new PersonBooksResponse();
        response.updatePersonOnly(personOnlyDTOS);
        response.updatePersonOneToOne(personOneToOneDTOS);
        response.updatePersonOneToMany(personOneToManyDTOS);

        return ResponseEntity.ok(response);
    }

    /**
     * 이름으로 검색
     *
     * @param request
     */
    @GetMapping("/name")
    @Operation(summary = "이름으로 검색", description = "이름으로 주소록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PersonBooksResponse.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = PersonBooksResponse.class)))
    })
    public ResponseEntity<PersonBooksResponse> getPeopleByName(@RequestBody @Valid SearchByNameRequest request) {
        log.debug("Controller : Search Name");
        Set<PersonDTO> personOnlyDTOS = personSearchService.searchPersonOnlyByNameContaining(request.getName());
        Set<PersonDTO> personOneToOneDTOS = personSearchService.searchPersonOneToOneByNameContaining(request.getName());
        Set<PersonOneToManyDTO> personOneToManyDTOS = personSearchService.searchPersonOneToManyByNameContaining(request.getName());

        PersonBooksResponse response = new PersonBooksResponse();
        response.updatePersonOnly(personOnlyDTOS);
        response.updatePersonOneToOne(personOneToOneDTOS);
        response.updatePersonOneToMany(personOneToManyDTOS);

        return ResponseEntity.ok(response);
    }

    /**
     * 번호로 검색
     *
     * @param request
     */
    @GetMapping("/phone")
    @Operation(summary = "번호로 검색", description = "번호로 주소록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PersonBooksResponse.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = PersonBooksResponse.class)))
    })
    public ResponseEntity<PersonBooksResponse> getPeopleByPhone(@RequestBody @Valid SearchByPhoneRequest request) {
        log.debug("Controller : Saerch Phone");
        Set<PersonDTO> personOnlyDTOS = personSearchService.searchPersonOnlyByPhoneContaining(request.getPhone());
        Set<PersonDTO> personOneToOneDTOS = personSearchService.searchPersonOneToOneByPhoneContaining(request.getPhone());
        Set<PersonOneToManyDTO> personOneToManyDTOS = personSearchService.searchPersonOneToManyByPhoneContaining(request.getPhone());

        PersonBooksResponse response = new PersonBooksResponse();
        response.updatePersonOnly(personOnlyDTOS);
        response.updatePersonOneToOne(personOneToOneDTOS);
        response.updatePersonOneToMany(personOneToManyDTOS);

        return ResponseEntity.ok(response);
    }
}
