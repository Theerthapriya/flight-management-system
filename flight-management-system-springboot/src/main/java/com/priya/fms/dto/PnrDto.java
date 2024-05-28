package com.priya.fms.dto;

import java.sql.Date;

public interface PnrDto {
	 Long getUserId();
	 String getPnr();
	 String getFlightNo();
	 String getBookingDate();
	 String getFlightDate();
	 String getStatus();
	 Long getFare();
}
