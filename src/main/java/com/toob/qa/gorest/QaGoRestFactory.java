package com.toob.qa.gorest;

import com.toob.qabase.QaBaseAutomationModule;
import org.springframework.context.annotation.ComponentScan;


/**
 * `@QaBaseAutomationModule` is the QABase magic annotation, similar to `@SpringBootApplication` but tailored for QABase.
 *
 * It bootstraps QABase automation by wiring up Spring Boot configuration, auto-configuration, and QABase’s core module factory
 * (including config, Allure, DSL beans, and more).
 */
@QaBaseAutomationModule
@ComponentScan( basePackageClasses = QaGoRestPackage.class )
public class QaGoRestFactory {}
