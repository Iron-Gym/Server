package com.kbm.Iron.Gym.service;

import com.kbm.Iron.Gym.entity.Schedule;
import com.kbm.Iron.Gym.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }


    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Optional<Schedule> getScheduleById(Integer id) {
        return scheduleRepository.findById(id);
    }
}
