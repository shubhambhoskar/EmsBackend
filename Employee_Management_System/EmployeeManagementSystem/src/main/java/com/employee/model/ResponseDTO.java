package com.employee.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

		private LocalDateTime time;
		private Object data;
		private HttpStatus msg;
		private Integer statusCode;
		private Object err;
	    
	    public static ResponseDTO getResponse(Object obj, LocalDateTime time, HttpStatus message, Integer status,Object errors ){
	        ResponseDTO rDTO= new ResponseDTO();
	        rDTO.setTime(time);
	        rDTO.setData(obj);
	        rDTO.setMsg(message);
	        rDTO.setStatusCode(status);
	        rDTO.setErr(errors);
	        return rDTO;
	    }
	
}
