package com.fastcampus.loan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fastcampus.loan.domain.Counsel;
import com.fastcampus.loan.dto.CounselDTO;
import com.fastcampus.loan.repository.CounselRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class CounselServiceTest {

    @InjectMocks
    CounselServiceImpl counselService;

    @Mock
    private CounselRepository counselRepository;

    //modelmapper 는 각각 다른 오브젝트를 맵핑해주는 유틸성이기 때문
    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_returnResponseOfNewCounselEntity_When_RequestCounsel() {
        Counsel entity = Counsel.builder()
                .name("Member Kim")
                .cellPhone("010*1111-2222")
                .email("abc@def.g")
                .memo("저는 대출을 받고 싶어요. 연락주세요")
                .zipCode("12345")
                .address("서울 특별시 동대문구 어딘동")
                .addressDetail("101동 101호")
                .build();

        CounselDTO.Request request = CounselDTO.Request.builder()
                .name("Member Kim")
                .cellPhone("010*1111-2222")
                .email("abc@def.g")
                .memo("저는 대출을 받고 싶어요. 연락주세요")
                .zipCode("12345")
                .address("서울 특별시 동대문구 어딘동")
                .addressDetail("101동 101호")
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);

        CounselDTO.Response actual = counselService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());

    }

}