package osteam.backland.domain.phone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import osteam.backland.domain.phone.repository.PhoneOneToManyRepository;
import osteam.backland.domain.phone.repository.PhoneOneToOneRepository;

@Service
@RequiredArgsConstructor
public class PhoneCreateService {
    private final PhoneOneToOneRepository phoneOneToOneRepository;
    private final PhoneOneToManyRepository phoneOneToManyRepository;
}
