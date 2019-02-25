package com.fanqiao.secondkill.exception;


import com.fanqiao.secondkill.result.CodeMessage;
import lombok.Data;

@Data
public class GlobalException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private CodeMessage cm;
	
	public GlobalException(CodeMessage cm) {
		super(cm.toString());
		this.cm = cm;
	}
}
