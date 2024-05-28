package com.priya.fms.repo;

import com.priya.fms.dto.BookingPnrDto;
import com.priya.fms.dto.PnrDto;
import com.priya.fms.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity>  findAllByUserId(Long userId);
 
    @Query(value="select pnr,\n"
    		+ "max(user_id) as userId,\n"
    		+ "max(booking_date) as bookingDate,\n"
    		+ "max(flight_date) as flightDate,\n"
    		+ "max(flight_no) as flightNo,\n"
    		+ "sum(fare) as fare,\n"
    		+ "max(status) as status\n"
    		+ "from booking\n"
    		+ "where user_id = ?1 \n"
    		+ "group by pnr" , nativeQuery=true)
    List<PnrDto>  findAllByPnr(Long userId);
    
    @Query(value="select pnr,\n"
    		+ "max(user_id) as userId,\n"
    		+ "max(booking_date) as bookingDate,\n"
    		+ "max(flight_date) as flightDate,\n"
    		+ "max(flight_no) as flightNo,\n"
    		+ "sum(fare) as fare,\n"
    		+ "max(status) as status\n"
    		+ "from booking\n"
    		+ "where flight_date = :flightDate \n"
    		+ "and flight_no = :flightNo \n"
    		+ "group by pnr" , nativeQuery=true)
    List<PnrDto>  getBookingsByFlight(@Param("flightDate")Date flightDate,@Param("flightNo")String flightNo);
    
    List<BookingEntity>  findAllByFlightDateAndFlightNoAndStatus(Date flightDate,String FlightNo,String status);
    
    List<BookingEntity>  findAllByPnr(String pnr);
    
    
    @Query(value="select seat_no\n"
    		+ "    		from booking \n"
    		+ "    		where flight_no = ?1 and  flight_date=?2 and status='Booked'", nativeQuery=true)
    List<String>  findAllByFlightNumAndFlightDate(String flightNo,Date date);
    
    @Modifying
    @Transactional
    @Query(value="update booking set status='Cancelled' where pnr = ?1", nativeQuery=true)
    int  cancelBooking(String pnr);
    
    @Modifying
    @Transactional
    @Query(value="update booking set status='Cancelled' where flight_no =:flightNo  and flight_date = :flightDate  ", nativeQuery=true)
    int  cancelBookingByFlight(@Param("flightDate")Date flightDate,@Param("flightNo")String flightNo);
}
