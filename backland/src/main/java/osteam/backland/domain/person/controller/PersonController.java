package osteam.backland.domain.person.controller;

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
import osteam.backland.domain.person.service.ValidCheckService;

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
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonCreateService personCreateService;
    private final PersonUpdateService personUpdateService;
    private final PersonSearchService personSearchService;
    private final ValidCheckService validCheckService;

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
    @PostMapping("/create")
    public String person(@RequestBody @Valid PersonCreateRequest personCreateRequest) {
        log.debug("Controller : Create");
        PersonDTO personDTO = new PersonDTO(personCreateRequest.getName(), personCreateRequest.getPhone());
        if (validCheckService.duplicatePersonOnly(personDTO.getPhone()).isPresent()) {
            log.debug("PersonOnly 중복 발생");
            personUpdateService.updateNamePersonOnly(personDTO);
        }
        else {
            personCreateService.one(personDTO);
        }
        if (validCheckService.duplicatePersonOneToOne(personDTO.getPhone()).isPresent()) {
            log.debug("PersonOneToOne 중복 발생");
            personUpdateService.updateNamePersonOneToOne(personDTO);
        }
        else {
            personCreateService.oneToOne(personDTO);
        }
        if (validCheckService.duplicatePersonOneToMany(personDTO.getPhone()).isPresent()) {
            log.debug("PersonOneToMany 중복 발생");
            personUpdateService.updateNamePersonOneToMany(personDTO);
        }
        else {
            personCreateService.oneToMany(personDTO);
        }
        return personCreateRequest.getName();
    }

    /**
     * 전체 검색 기능
     */
    @GetMapping("/search-all")
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
