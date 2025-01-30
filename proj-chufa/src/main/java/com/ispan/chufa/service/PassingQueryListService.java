package com.ispan.chufa.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PassingQueryListService {

	static List<Long> mule = new ArrayList<>();
		
		public List<Long> getMule(){
			return mule;
		}
		
		public void setMule(List<Long> queryKeys) {
			mule = queryKeys;
	}
}
