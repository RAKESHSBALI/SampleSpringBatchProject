package com.bali.apps.springbatch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;

@SpringBootApplication
public class SpringBatchApplication {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job importNiftyJob;

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

	@PostConstruct
	public void runJob() throws Exception {
		jobLauncher.run(importNiftyJob, new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters());
	}



}
