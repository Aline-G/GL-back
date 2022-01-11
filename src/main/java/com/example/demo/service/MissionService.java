package com.example.demo.service;


import com.example.demo.exception.MissionException;
import com.example.demo.repository.MissionRepository;
import com.example.demo.vo.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MissionService {
    @Autowired
    MissionRepository missionRepository;

    public Mission saveMission(Mission mission){
        return this.missionRepository.save(mission);
    }

    public HttpStatus deleteMission(int id) throws MissionException{
        //Check existence of this id
        if(!missionRepository.existsById(id)){
            throw new MissionException("Id doesn't exist", HttpStatus.BAD_REQUEST);
        }

        try{
            this.missionRepository.delete(this.missionRepository.findById(id));
            return HttpStatus.OK;
        }catch (Exception exception){
            throw new MissionException("May delete LineBill attached to this mission before trying to delete it",HttpStatus.CONFLICT);
        }
    }

    public Mission findById(int id) throws MissionException {
        if(!this.missionRepository.existsById(id)){
            throw new MissionException("Mission" +id+ " doesn't exist",HttpStatus.NOT_FOUND);
        }
        return this.missionRepository.findById(id);
    }

    public List<Mission> getMissionList() {
        return StreamSupport.stream(this.missionRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
