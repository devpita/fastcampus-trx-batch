package com.pitachips.trxbatch.job.monthlyTrxReport;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReport;

@Configuration
@RequiredArgsConstructor
public class MonthlyTrxReportJobConfiguration extends DefaultBatchConfiguration {

    private static final String JOB_NAME = "monthlyTrxReportJob";

    private final MonthlyTrxReportPagingItemReader monthlyTrxReportPagingItemReader;
    private final MonthlyTrxReportClassifier monthlyTrxReportClassifier;


    @Bean
    public Job monthlyTrxReportJob(JobRepository jobRepository, Step customerMonthlyTrxReportStep) {
        return new JobBuilder(JOB_NAME, jobRepository).start(customerMonthlyTrxReportStep).build();
    }

    @Bean
    public Step customerMonthlyTrxReportStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("customerMonthlyTrxReportStep",
                               jobRepository).<CustomerMonthlyTrxReport, CustomerMonthlyTrxReport>chunk(100, transactionManager)
                                             .reader(monthlyTrxReportPagingItemReader)
                                             .writer(classifierCompositeItemWriter())
                                             .build();
    }


    @Bean
    @StepScope
    public ClassifierCompositeItemWriter<CustomerMonthlyTrxReport> classifierCompositeItemWriter() {
        return new ClassifierCompositeItemWriterBuilder<CustomerMonthlyTrxReport>().classifier(monthlyTrxReportClassifier)
                                                                                   .build();
    }


}
