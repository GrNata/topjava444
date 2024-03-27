package ru.javawebinar.topjava.service;

import org.ehcache.xml.model.TimeUnit;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.TimingRules;

//import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;

import static org.slf4j.LoggerFactory.getLogger;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public abstract class AbstractServiceTest {

    @ClassRule
    public static ExternalResource summary = TimingRules.SUMMARY;

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
//    public Stopwatch stopwatch = new Stopwatch() {
//        @Override
//        protected void finished(long nanos, Description description) {
////            String result = String.format("%-95s %7d", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
//            String result = String.format("%-95s %7d", description.getDisplayName(), (nanos / 1_000_000));
//            results.append(result).append('\n');
//            log.info(result + " ms\n");
//        }
//    };
//
//
//    //    https://dzone.com/articles/applying-new-jdk-11-string-methods
//    private static final String DELIM = "-".repeat(103);
//
//    @AfterClass
//    public static void printResult() {
//        log.info("\n" + DELIM +
//                "\nTest                                                                                       Duration, ms" +
//                "\n" + DELIM + "\n" + results + DELIM + "\n");
//        results.setLength(0);
//    }
    public Stopwatch stopwatch = TimingRules.STOPWATCH;
}