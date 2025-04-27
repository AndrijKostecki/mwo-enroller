package com.company.enroller.persistence;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Component("meetingService")

public class MeetingService {

	DatabaseConnector connector;


	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";

		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Meeting getMeeting(Long id) {

		String hql = "FROM Meeting";

		Query query = connector.getSession().createQuery(hql);
		return (Meeting) query.list().get(Math.toIntExact(id));
    }

	public Meeting add(Meeting meeting) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();
		return meeting;
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



}
