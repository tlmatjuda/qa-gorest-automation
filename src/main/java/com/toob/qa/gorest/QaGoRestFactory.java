package com.toob.qa.gorest;

import com.toob.qabase.QaBaseAutomationModule;
import org.springframework.context.annotation.ComponentScan;

@QaBaseAutomationModule
@ComponentScan( basePackageClasses = QaGoRestPackage.class )
public class QaGoRestFactory {}
