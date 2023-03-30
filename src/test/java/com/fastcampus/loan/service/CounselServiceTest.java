package com.fastcampus.loan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fastcampus.loan.domain.Counsel;
import com.fastcampus.loan.dto.CounselDTO;
import com.fastcampus.loan.exception.BaseException;
import com.fastcampus.loan.exception.ResultType;
import com.fastcampus.loan.repository.CounselRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

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

    @Test
    void Should_ReturnResponseOfExistCounselEntity_When_RequestExistCounselId() {
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        CounselDTO.Response actual = counselService.get(findId);

        assertThat(actual.getCounselId()).isSameAs(findId);
    }

    @Test
    void Should_ThrowException_When_RequestNotExistCounselId() {
        Long findId = 2L;

        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        Assertions.assertThrows(BaseException.class, () -> counselService.get(findId));

    }

    @Test
    void Should_returnUpdatedResponseOfExistCounselEntity_When_RequestUpdateExistCounselInfo() {
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .name("Member Kim")
                .build();

        CounselDTO.Request request = CounselDTO.Request.builder()
                .name("Member Kang")
                .build();


        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        CounselDTO.Response actual = counselService.update(findId, request);

        assertThat(actual.getCounselId()).isSameAs(findId);
        assertThat(actual.getName()).isSameAs(request.getName());


    }

    @Test
    void Should_DeletedCounselEntity_When_RequestDeleteExistCounselInfo() {
        Long targetId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        when(counselRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

        counselService.delete(targetId);

        assertThat(entity.getIsDeleted()).isSameAs(true);

    }
}