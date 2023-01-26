package com.sohwakmo.hr.controller;

import com.sohwakmo.hr.dto.CalReadDto;
import com.sohwakmo.hr.dto.OrgReadDto;
import com.sohwakmo.hr.service.OrgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/org")
public class OrgRestController {
    private final OrgService orgService;

    @GetMapping("/allList")
    public ResponseEntity<List<OrgReadDto>> readAllOrgList(){
        log.info("readAllOrgList()");

        List<OrgReadDto> list = orgService.readAllOrgList();
        log.info("# of list={}", list.size());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/memberInfo/{memberNo}")
    public ResponseEntity<List<OrgReadDto>> getMemberInfo(@PathVariable Long memberNo){
        log.info("getMemberInfo(member={})", memberNo);

        List<OrgReadDto> memberEntity = orgService.readMemberInfo(memberNo);
        log.info("memberEntity ok");

        return ResponseEntity.ok(memberEntity);
    }

    @GetMapping("/calendarList")
    public ResponseEntity<List<CalReadDto>> readAllCalList(){
        log.info("readAllCalList()");

        List<CalReadDto> list = orgService.readAllCalList();
        log.info("# of list={}", list.size());

        return ResponseEntity.ok(list);
    }


}
