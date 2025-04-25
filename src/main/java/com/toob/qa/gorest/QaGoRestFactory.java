package com.toob.qa.gorest;

import com.toob.qabase.QABaseTestConfig;
import org.springframework.context.annotation.ComponentScan;

@QABaseTestConfig
@ComponentScan( basePackageClasses = QaGoRestPackage.class )
public class QaGoRestFactory {}
