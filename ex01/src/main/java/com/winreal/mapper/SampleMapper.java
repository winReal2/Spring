package com.winreal.mapper;

import org.apache.ibatis.annotations.Select;

public interface SampleMapper {
	
	@Select("select sysdate from dual")
	//오늘의 시간을 가져오는 추상메서드
	String getTime();
	
	String getTime2();
	
}
