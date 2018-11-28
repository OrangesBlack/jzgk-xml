package com.jzkg.xml.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TestPojo {

	private Long id;
	private String name;
	private Integer count;
	private Boolean isDefault;

	public String testGame() {
		return null;
	}

	public Integer testCount() {
		return null;
	}
}