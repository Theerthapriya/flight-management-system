package com.priya.fms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOKING")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKING_ID")
    private Long bookingId;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "PAX_NAME")
    private String paxName;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "AGE")
    private Long age;
    @Column(name = "PNR")
    private String pnr;
    @Column(name = "TICKET_NO")
    private Long ticketNo;
    @Column(name = "FLIGHT_NO")
    private String flightNo;
    @Column(name = "SEAT_NO")
    private String seatNo;
    @Column(name = "BOOKING_CLASS")
    private String bookingClass;
    @Column(name = "BOOKING_DATE")
    private Date bookingDate;
    @Column(name = "FLIGHT_DATE")
    private Date flightDate;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "FARE")
    private Long fare;
    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;
    @Column(name = "UPDATED_DATE")
    private Timestamp updatedDate;

}
