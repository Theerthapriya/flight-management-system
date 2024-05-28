package com.priya.fms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@Entity
@Table(name = "FLIGHT_SCHEDULE")
@NoArgsConstructor
@AllArgsConstructor
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FLIGHT_NO")
    private String flightNo;
    @Column(name = "DEP_DATE")
    private Date depDate;
    @Column(name = "ARR_DATE")
    private Date arrDate;
    @Column(name = "ORIGIN")
    private String origin;
    @Column(name = "DESTINATION")
    private String destination;
    @Column(name = "ARR_TIME")
    private String arrTime;
    @Column(name = "DEP_TIME")
    private String depTime;
    @Column(name = "DURATION")
    private String duration;
    @Column(name = "CAPACITY")
    private Long capacity;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;
    @Column(name = "UPDATED_DATE")
    private Timestamp updatedDate;

}
