package com.company.enroller.persistence;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component("meetingService")
public class MeetingService {


    DatabaseConnector connector;

    public MeetingService() {

        connector = DatabaseConnector.getInstance();
    }


    public Collection<Meeting> getAll() {
        String hql = "FROM Meeting";
        Query query = this.connector.getSession().createQuery(hql);
        return query.list();
    }

    public Meeting findMeetingById(long id) {
        String hql = "FROM Meeting";
        Query query = this.connector.getSession().createQuery(hql);

        return (Meeting) query.list().get(Math.toIntExact(id));
    }

    public void addMeeting(Meeting meeting) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(meeting);
        transaction.commit();
    }

    public void delete(Meeting meeting) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().delete(meeting);
        transaction.commit();
    }

    public void update(Meeting meeting) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().merge(meeting);
        transaction.commit();
    }

    public Collection<Participant> getParticipants(long meetingId) {
        Meeting meeting = findMeetingById(meetingId);
        if (meeting == null) {
            return Collections.emptyList();
        }
        return meeting.getParticipants();
    }

    public void addParticipantToMeeting(long meetingId, Participant participant) {
        Meeting meeting = findMeetingById(meetingId);
        if (meeting != null) {
            meeting.addParticipant(participant);
            update(meeting);
        }
    }

    public void removeParticipantFromMeeting(long meetingId, Participant participant) {
        Meeting meeting = findMeetingById(meetingId);
        if (meeting != null) {
            meeting.removeParticipant(participant);
            update(meeting);
        }
    }


}

