package com.kbm.Iron.Gym.service;

import com.kbm.Iron.Gym.entity.Schedule;
import com.kbm.Iron.Gym.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getAllSchedules() {
        try {
            return scheduleRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching all schedules", e);
            return List.of();
        }
    }

    public Schedule saveSchedule(Schedule schedule) {
        try {
            return scheduleRepository.save(schedule);
        } catch (Exception e) {
            logger.error("Error saving schedule", e);
            return null;
        }
    }

    public Optional<Schedule> getScheduleById(Integer id) {
        try {
            return scheduleRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error fetching schedule by id: " + id, e);
            return Optional.empty();
        }
    }
}
