package com.kbm.Iron.Gym.controller;

import com.kbm.Iron.Gym.entity.Schedule;
import com.kbm.Iron.Gym.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    //get all schedules
    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules(){
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    //save a schedule
    @PostMapping
    public ResponseEntity<Schedule> saveSchedule(@RequestBody Schedule schedule){
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return ResponseEntity.ok(savedSchedule);
    }

    //get a schedule by id
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Integer id){
        Optional<Schedule> getSchedule = scheduleService.getScheduleById(id);
        return getSchedule.isPresent() ?  ResponseEntity.ok(getSchedule.get()) : ResponseEntity.notFound().build();
    }
}
