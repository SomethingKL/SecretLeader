package JUnitTesting;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

// all test classes included in suite go here
@Suite.SuiteClasses({
   SLTest.class,
   PolicyCardTest.class,
   PlayerListTest.class
})

public class TestSuite {  
}  