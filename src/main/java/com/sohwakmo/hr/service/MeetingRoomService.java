package com.sohwakmo.hr.service;

import com.sohwakmo.hr.domain.Employee;
import com.sohwakmo.hr.domain.MeetingRoom;
import com.sohwakmo.hr.dto.MeetingRoomCreateDto;
import com.sohwakmo.hr.dto.MeetingRoomUpdateDto;
import com.sohwakmo.hr.repository.EmployeeRepository;
import com.sohwakmo.hr.repository.MeetingRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;

    public List<MeetingRoom> read(String employeeNo) {
        log.info("read()", employeeNo);

        List<MeetingRoom> list = meetingRoomRepository.findByEmployeeNoOrderByMeetingRoomNoDesc(employeeNo);

        return list;
    }



    @Transactional
    public MeetingRoom read(Integer meetingRoomNo) {
        log.info("read(meetingRoomNo = {})", meetingRoomNo);

        return meetingRoomRepository.findById(meetingRoomNo).get();
    }


    public MeetingRoom create(MeetingRoomCreateDto dto) {
        log.info("create(dto={})", dto);

        MeetingRoom entity = meetingRoomRepository.save(dto.toEntity());

        return entity;
    }

    // 삭제
    public void delete(Integer meetingRoomNo) {
        log.info("delete(meetingRoomNo={})", meetingRoomNo);


        meetingRoomRepository.deleteById(meetingRoomNo);
    }

    @Transactional
    public Integer update(MeetingRoomUpdateDto dto) {
        log.info("update(dto={})", dto);

        MeetingRoom entity = meetingRoomRepository.findById(dto.getMeetingRoomNo()).get();
        MeetingRoom newMeetingRoom = entity.update(dto.getTitle(), dto.getAttendee(), dto.getPurpose());
        log.info("newMeetingRoom = {}",newMeetingRoom.toString());

        return entity.getMeetingRoomNo();
    }


    public List<MeetingRoom> getTodayReservation(String employeeNo, String formatedNow) {
        formatedNow = formatedNow.substring(0, 2) + "-" + formatedNow.substring(3);
        List<MeetingRoom> todayReservationList = meetingRoomRepository.findByReserveDateContaining(formatedNow);
        List<MeetingRoom> myTodayReservationList = new ArrayList<>();
        for (MeetingRoom m : todayReservationList) {
            if(m.getEmployeeNo().equals(employeeNo)) myTodayReservationList.add(m); // 내가 예약했을 경우
            List<String> attendList = m.getAttendee();
            for (String s : attendList) {
                if(s.equals(employeeNo)) myTodayReservationList.add(m);
            }
        }
        return myTodayReservationList;
    }

}
