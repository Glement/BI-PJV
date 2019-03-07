package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import suite01.TestSuite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	TestSuite.class,
	suite02.TestSuite.class
})

public class MainSuite {

}
