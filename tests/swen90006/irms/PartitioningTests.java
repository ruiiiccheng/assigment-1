package swen90006.irms;

import org.junit.*;

import static org.junit.Assert.*;


public class PartitioningTests {
    // The IRMS instance variable irms is shared across all test methods in this class
    protected IRMS irms;

    /**
     * The setup method annotated with "@Before" runs before each test.
     * By default, it initializes the IRMS instance and creates a dummy user.
     * Use this method to set up any common test data or state.
     */

    @Before
    public void setUp() throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        irms = new IRMS();
        irms.registerAnalyst("analystA", "Password1!");

    }

    /**
     * The teardown method annotated with "@After" runs after each test.
     * It's useful for cleaning up resources or resetting states.
     * Currently, this method doesn't perform any actions, but you can customize it as needed.
     */
    @After
    public void tearDown() {
        // No resources to clean up in this example, but this is where you would do so if needed
    }

    /**
     * This is a basic example test annotated with "@Test" to demonstrate how to use assertions in JUnit.
     * The assertEquals method checks if the expected value matches the actual value.
     */

    @Test
    public void aTest() {
        final int expected = 2;
        final int actual = 1 + 1;
        // Use of assertEquals to verify that the expected value matches the actual value
        assertEquals(expected, actual);
    }

    /**
     * This test checks if the InvalidAnalystNameException is correctly thrown when registering with an invalid analyst name.
     * The expected exception is specified in the @Test annotation.
     */
    @Test(expected = InvalidAnalystNameException.class)
    public void anExceptionTest()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.registerAnalyst("aa", "Password1!");
    }

    /**
     * This is an example of a test that is designed to fail.
     * It shows how to include an error message to provide feedback when a test doesn't pass.
     */
    @Test
    public void aFailedTest() {
        // This test currently fails to demonstrate how JUnit reports errors
        final int expected = 3;
        final int actual = 1 + 2;
        // Uncomment the following line to observe a test failure.
        assertEquals("Some failure message", expected, actual);
    }
    // ADD YOUR TESTS HERE
    // This is the section where you will add your own tests.
    // Follow the examples above to create your tests.


    /**
     * Start the test of each equivalence classes
     * For the five functions that are to implemented, the task cases are names as "FxECxx"
     */

    @Test(expected = DuplicateAnalystException.class)
    public void isRegistered_F1EC1() //duplicate name
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.registerAnalyst("analystA", "Password1!");
    }

    @Test(expected = InvalidAnalystNameException.class)
    public void isRegistered_F1EC2()//less than 4 chars
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.registerAnalyst("ana", "Password1!");
    }

    @Test(expected = InvalidAnalystNameException.class)
    public void isRegistered_F1EC3()//contain non-letter characters
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.registerAnalyst("ana226", "Password1!");
    }

    @Test(expected = InvalidPasswordException.class)
    public void isRegistered_F1EC4()// < 10 characters
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.registerAnalyst("SeanC", "Pass");
    }

    @Test(expected = InvalidPasswordException.class)
    public void isRegistered_F1EC5()//more than 16 chars
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.registerAnalyst("SeanC", "Password6622622662!");
    }

    @Test(expected = InvalidPasswordException.class)
    public void isRegistered_F1EC6()//no letter existing
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.registerAnalyst("SeanC", "6622622662!");
    }

    @Test(expected = InvalidPasswordException.class)
    public void isRegistered_F1EC7()//no numbers existing
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.registerAnalyst("SeanC", "Passwordddd!");
    }

    @Test(expected = InvalidPasswordException.class)
    public void isRegistered_F1EC8()//no special character existing
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.registerAnalyst("SeanC", "Passwordddd");
    }

    @Test//(expected = InvalidPasswordException.class)
    public void isRegistered_F1EC9()//accepted case
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.registerAnalyst("SeanC", "yangziyou2226!");
    }

    //Now start the test for authenticate

    @Test//(expected = IncorrectPasswordException.class)
    public void isAuthenticated_F2EC1()//accepted case
            throws IncorrectPasswordException, NoSuchAnalystException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.authenticate("analystA", "Password1!");
    }

    @Test(expected = IncorrectPasswordException.class)
    public void isAuthenticated_F2EC2()//accepted case
            throws IncorrectPasswordException, NoSuchAnalystException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.authenticate("analystA", "Passwo1!");
    }

    @Test(expected = NoSuchAnalystException.class)
    public void isAuthenticated_F2EC3()//accepted case
            throws IncorrectPasswordException, NoSuchAnalystException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.authenticate("analystYY", "Password1!");
    }

    //Now start the test for request Supervisor Access
    @Test(expected = NoSuchAnalystException.class)
    public void F3EC1()//non-existing name
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.requestSupervisorAccess("analystYY", "Password1!");
    }

    @Test(expected = InvalidBadgeIDException.class)
    public void F3EC2_a()//non-number input
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.requestSupervisorAccess("analystA", "Password1!");
    }

    @Test(expected = InvalidBadgeIDException.class)
    public void F3EC2_b()//unmatched ones
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // Test registration with an invalid username
        // to test whether the appropriate exception is thrown.
        irms.requestSupervisorAccess("analystA", "2266");
    }

    @Test//(expected = InvalidBadgeIDException.class)
    public void F3EC3()//ID is "1234" --> Supervisor
            throws NoSuchAnalystException, InvalidBadgeIDException {
        irms.requestSupervisorAccess("analystA", "1234");
    }

    @Test
    public void F3EC4()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        irms.requestSupervisorAccess("analystA", "1235");
    }

    @Test(expected = NoSuchAnalystException.class)
    public void F4EC1()
        //strat for submit incident
            throws NoSuchAnalystException, UnauthenticatedAnalystException, DuplicateIncidentException, InvalidRatingException, IncidentRejectException {
        irms.submitIncident("analystCC", "Inc001", 3);
    }

    @Test(expected = UnauthenticatedAnalystException.class)
    public void F4EC2()
            throws NoSuchAnalystException, UnauthenticatedAnalystException, DuplicateIncidentException, InvalidRatingException, IncidentRejectException {
        irms.submitIncident("analystA", "Inc001", 3);
    }

    public class IRMSSubmitIncidentTest {

        private IRMS system;

        @Before
        public void setUp() {
            system = new IRMS();
        }

        @After
        public void tearDown() {
            system = null;
        }
    }

    /**@Test(expected = UnauthenticatedAnalystException.class)
    public void F4EC1_a()
            throws NoSuchAnalystException, UnauthenticatedAnalystException, DuplicateIncidentException, InvalidRatingException, IncidentRejectException {
        irms.submitIncident("analystA", "Inc001", 3);
    }*/

    // Trying from here just use exception to replace all designated ones
    @Test(expected = DuplicateIncidentException.class)
    public void F4EC3()
            throws Exception {
        // Setup authenticated
        irms.registerAnalyst("analystS", "ValidPass123!");
        irms.authenticate("analystS", "ValidPass123!");
        // submit and store the first incident
        // Try to submit duplicate incident ID
        irms.submitIncident("analystS", "INC001", 5);
        irms.submitIncident("analystS", "INC001", 7);
    }

    @Test(expected = InvalidRatingException.class)
    public void F4EC4()
            throws Exception {
        // Setup authenticated analyst
        irms.registerAnalyst("analystS", "ValidPass123!");
        irms.authenticate("analystS", "ValidPass123!");
        irms.submitIncident("analystS", "INC001", -1);
    }

    @Test(expected = InvalidRatingException.class)
    public void F4EC5()
            throws Exception {
        // Setup authenticated analyst
        irms.registerAnalyst("analystS", "ValidPass123!");
        irms.authenticate("analystS", "ValidPass123!");
        irms.submitIncident("analystS", "INC001", 10);
    }

    @Test//(expected = InvalidRatingException.class)
    public void F4EC6()//accepct cases
            throws Exception {
        irms.registerAnalyst("supervisor", "ValidPass123!");
        irms.authenticate("supervisor", "ValidPass123!");
        irms.submitIncident("supervisor", "INC002", 3);
            }

    @Test
    public void F4EC7() throws Exception {
        // Setup authenticated member (default role)
        irms.registerAnalyst("member", "ValidPass123!");
        irms.authenticate("member", "ValidPass123!");
        // Submit to empty list - should succeed
        irms.submitIncident("member", "INC001", 5);
    }
    @Test
    public void F4EC8() throws Exception {
        // Setup authenticated member (default role)
        irms.registerAnalyst("member", "ValidPass123!");
        irms.authenticate("member", "ValidPass123!");
        // Submit to empty list - should succeed
        irms.submitIncident("member", "INC001", 2);
        irms.submitIncident("member", "INC002", 6);
        irms.submitIncident("member", "INC003", 8);
    }

    @Test(expected = IncidentRejectException.class)
    public void F4EC9() throws Exception {
        // Setup authenticated member (default role)
        irms.registerAnalyst("member", "ValidPass123!");
        irms.authenticate("member", "ValidPass123!");
        // Submit to empty list - should succeed
        irms.submitIncident("member", "INC001", 8);
        irms.submitIncident("member", "INC002", 3);
        irms.submitIncident("member", "INC003", 6);

    }

    //Now start the test of get Incident
    @Test(expected = NoSuchAnalystException.class)
    public void F5EC1() throws Exception {
        irms.getIncident("nonresigter_Analyst", -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void F5EC2() throws Exception {
        // Setup: Register analyst and add some incidents
        irms.registerAnalyst("analystT", "ValidPass123!");
        irms.authenticate("analystT", "ValidPass123!");
        irms.submitIncident("analystT", "INC001", 5);
        // the index < 0
        irms.getIncident("analystT", -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void F5EC3() throws Exception {
        // Setup: Register analyst and add some incidents
        irms.registerAnalyst("analystT", "ValidPass123!");
        irms.authenticate("analystT", "ValidPass123!");
        irms.submitIncident("analystT", "INC001", 5);
        // the index < 0
        irms.getIncident("analystT", -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void F5EC4() throws Exception {
        // Setup: Register analyst and add some incidents
        irms.registerAnalyst("analystS", "ValidPass123!");
        irms.authenticate("analystS", "ValidPass123!");
        irms.submitIncident("analystS", "INC001", 2);
        irms.submitIncident("analystS", "INC002", 6);
        irms.submitIncident("analystS", "INC003", 9);
        // the index is > length
        irms.getIncident("analystS", 11);
    }

    @Test//(expected = IndexOutOfBoundsException.class)
    public void F5EC5() throws Exception {
        //
        irms.registerAnalyst("analystS", "ValidPass123!");
        irms.authenticate("analystS", "ValidPass123!");
        irms.submitIncident("analystS", "INC001", 2);
        irms.submitIncident("analystS", "INC002", 6);
        irms.submitIncident("analystS", "INC003", 9);
        // should be success this time
        irms.getIncident("analystS", 2);
    }



}








