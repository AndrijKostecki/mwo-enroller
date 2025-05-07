package com.company.enroller.controllers;


import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllMeeting() {
        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findMeetingById(@PathVariable("id") long id) {
        Meeting meeting = meetingService.findMeetingById(id);
        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
        if (meetingService.findMeetingById(meeting.getId()) != null) {
            return new ResponseEntity<String >(
                    "Unable to create. A participant with login " + meeting.getId() + " already exist.",
                    HttpStatus.CONFLICT);
        }
        meetingService.addMeeting(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Meeting meeting = meetingService.findMeetingById(id);
        if (meeting == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        meetingService.delete(meeting);
        return new ResponseEntity<Meeting>(HttpStatus.OK);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Meeting meeting) {
        Meeting meeting1= meetingService.findMeetingById(id);
        if (meeting1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        meeting.setTitle(meeting.getTitle());
        meetingService.update(meeting);
        return new ResponseEntity<Participant>(HttpStatus.OK);
    }

}

