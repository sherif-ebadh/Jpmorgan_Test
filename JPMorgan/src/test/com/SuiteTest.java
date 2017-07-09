package test.com;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.com.jpmorgan.RecordControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	RecordControllerTest.class, 
})
public class SuiteTest {
	
}