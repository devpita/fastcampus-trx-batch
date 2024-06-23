package com.pitachips.trxbatch.controller;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pitachips.trxbatch.controller.dto.JobControllerResponse;
import com.pitachips.trxbatch.controller.dto.enums.JobControllerResponseCode;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JobController {

    private final JobLauncher jobLauncher;

    private final JobRegistry jobRegistry;

    @GetMapping(value = "/jobLauncher/{jobName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public JobControllerResponse launchJob(@PathVariable String jobName, @RequestParam Map<String, String> params) {

        Job job;
        try {
            job = jobRegistry.getJob(jobName);
        } catch (NoSuchJobException e) {
            log.error("Job not found: {}", jobName);
            return new JobControllerResponse(JobControllerResponseCode.FAIL, "Job not found");
        }

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            jobParametersBuilder.addString(entry.getKey(), entry.getValue());
        }

        JobParameters jobParameters = jobParametersBuilder.toJobParameters();
        log.info("Launching job: {}, with params: {}", jobName, jobParameters);


        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException |
                 JobRestartException |
                 JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            log.error("Failed to launch job: {}", jobName, e);
            return new JobControllerResponse(JobControllerResponseCode.FAIL, "Failed to launch job");
        }

        return new JobControllerResponse(JobControllerResponseCode.SUCCESS, "Job launched successfully");
    }
}
