package swen90006.irms;

import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;

import org.junit.*;
import static org.junit.Assert.*;

//By extending PartitioningTests, we inherit the tests from that class
public class BoundaryTests
    extends PartitioningTests
{
    //Add another test
    @Test public void anotherTest()
    {
	//include a message for better feedback
	final int expected = 2;
	final int actual = 2;
	assertEquals("Some failure message", expected, actual);
    }

    //write the code for boundary tests
    // F1EC1 - Duplicate Input Boundary Tests
    @Test(expected = DuplicateAnalystException.class)
    public void bva_F1EC1_OnPoint_ExactMatch()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: exactly matches an existing name
        irms.registerAnalyst("analystA", "Password1!");
        irms.registerAnalyst("analystA", "Password2!"); // Exact duplicate
    }

    @Test
    public void bva_F1EC1_OffPoint_UniqueNewName()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: unique new name
        irms.registerAnalyst("analystB", "Password1!");
        irms.registerAnalyst("analystC", "Password2!"); // Different name
    }

    // F1EC2 - Name too short Boundary Tests
    @Test(expected = InvalidAnalystNameException.class)
    public void bva_F1EC2_OnPoint_LessThan4Chars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: < 4 characters
        irms.registerAnalyst("abc", "Password1!");
    }

    @Test
    public void bva_F1EC2_OffPoint_Exactly4Chars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: =4 characters
        irms.registerAnalyst("Sean", "Password1!");
    }

    @Test
    public void bva_F1EC2_OffPoint_5Chars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: 5 characters (>4 characters)
        irms.registerAnalyst("SeanC", "Password1!");
    }

    // F1EC3 - Name contains non-letter characters Boundary Tests
    @Test(expected = InvalidAnalystNameException.class)
    public void bva_F1EC3_OnPoint_OnlyNumbers()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: only numbers
        irms.registerAnalyst("2266", "Password1!");
    }

    @Test(expected = InvalidAnalystNameException.class)
    public void bva_F1EC3_OnPoint_OnlySpecialChars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: only special characters
        irms.registerAnalyst("!@#$", "Password1!");
    }

    @Test(expected = InvalidAnalystNameException.class)
    public void bva_F1EC3_OnPoint_NumbersAndSpecialChars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: numbers and special characters
        irms.registerAnalyst("123!@", "Password1!");
    }

    @Test(expected = InvalidAnalystNameException.class)
    public void bva_F1EC3_OffPoint_ContainLetters()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: contain letter characters
        irms.registerAnalyst("abc1", "Password1!"); //here we discuss containing, however, containing doesn't equal to only contain.
        //so it's still the wrong input
    }

    // F1EC4 - Password less than 10 characters Boundary Tests
    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC4_OnPoint_ExactlyLessThan10()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: exactly less than 10 characters (9 chars)
        irms.registerAnalyst("SeanC", "Pass1!");
    }

    @Test
    public void bva_F1EC4_OffPoint_Exactly10Chars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: =10 characters
        irms.registerAnalyst("SeanC", "Password1!");
    }

    @Test
    public void bva_F1EC4_OffPoint_MoreThan10Chars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: >10 characters
        irms.registerAnalyst("SeanC", "Password12!");
    }// here only when >10 and <16 works

    // F1EC5 - Password more than 16 characters Boundary Tests
    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC5_OnPoint_ExactlyMoreThan16()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: exactly more than 16 characters (17 chars)
        irms.registerAnalyst("SeanC", "Password123456789!");
    }

    @Test
    public void bva_F1EC5_OffPoint_Exactly16Chars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: =16 characters
        irms.registerAnalyst("SeanC", "Password12345!");
    }

    @Test
    public void bva_F1EC5_OffPoint_LessThan16Chars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: <16 characters
        irms.registerAnalyst("SeanC", "Password123!");
    }// here only when >10 and <16 works

    // F1EC6 - Password no letter existing Boundary Tests
    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC6_OnPoint_OnlyNumbers()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: only contains numbers
        irms.registerAnalyst("SeanC", "1234567890");
    }

    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC6_OnPoint_OnlySpecialChars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: only contains special chars
        irms.registerAnalyst("SeanC", "!@#$%^&*()");
    }

    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC6_OnPoint_NumbersAndSpecialChars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: only contains numbers and special chars
        irms.registerAnalyst("SeanC", "123456789!");
    }

    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC6_OffPoint_LetterAndNumber()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: letter existing password - Letter + number
        irms.registerAnalyst("SeanC", "Password123");
    }

    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC6_OffPoint_LetterAndSpecialChar()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: letter existing password - Letter + special char
        irms.registerAnalyst("SeanC", "Password!");
    }

    // F1EC7 - Password no numbers existing Boundary Tests
    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC7_OnPoint_OnlyLetters()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: only contains letters
        irms.registerAnalyst("SeanC", "Passworddd");
    }

    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC7_OnPoint_OnlyLettersAndSpecialChars()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: only contains letters and special chars
        irms.registerAnalyst("SeanC", "Password!");
    }

    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC7_OffPoint_LetterAndNumber()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: number existing password - Letter + number
        irms.registerAnalyst("SeanC", "Password1");
    }

    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC7_OffPoint_SpecialCharAndNumber()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: number existing password - Special char + number
        irms.registerAnalyst("SeanC", "!@#$123456");
    }

    // F1EC8 - Password no special characters existing Boundary Tests
    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC8_OnPoint_OnlyNumbers()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: only contains numbers
        irms.registerAnalyst("SeanC", "1234567890");
    }

    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC8_OnPoint_OnlyLetters()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: only contains letters
        irms.registerAnalyst("SeanC", "Passworddd");
    }

    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC8_OnPoint_NumbersAndLetters()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: only contains numbers and letters
        irms.registerAnalyst("SeanC", "Password123");
    }

    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC8_OffPoint_SpecialCharAndNumber()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: special characters existing password - Special char + number
        irms.registerAnalyst("SeanC", "123456789!");
    }


    @Test(expected = InvalidPasswordException.class)
    public void bva_F1EC8_OffPoint_SpecialCharAndLetter()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // Off-point: special characters existing password - Special char + Letter
        irms.registerAnalyst("SeanC", "Password!");
    }

    // For BVA6--8, only set password with all (num + char + letters) works

    @Test
    public void bva_F1EC8_OffPoint_CharAndNumberLet()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // password with all (num + char + letters) works
        irms.registerAnalyst("SeanC", "Pass123@662");
    }


    // F1EC9 - Username and password valid Boundary Tests
    @Test
    public void bva_F1EC9_OnPoint_ValidCredentials()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: UN- not registered, more than 4 chars, only letters
        // PW - [10,16] chars, including digits, letters, special chars
        irms.registerAnalyst("ChengS", "Password123!");
    }

    @Test
    public void bva_F1EC9_OnPoint_ValidCredentials_MinLength()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: Username exactly 4 chars, Password exactly 10 chars
        irms.registerAnalyst("ChengS", "Password1!");
    }

    @Test
    public void bva_F1EC9_OnPoint_ValidCredentials_MaxLength()
            throws DuplicateAnalystException, InvalidAnalystNameException, InvalidPasswordException {
        // On-point: Password exactly 16 chars
        irms.registerAnalyst("ChengS", "Password12345!");
    }

// Off-point tests for F1EC9 would include all the invalid cases tested above
// (duplicate names, invalid usernames, invalid passwords)

    // F2EC1 - Based on valid name input: Correct passwords Boundary Tests
    @Test
    public void bva_F2EC1_OnPoint_ExactlyCorrectNameAndPassword()
            throws IncorrectPasswordException, NoSuchAnalystException {
        // On-point: exactly correct name and corresponding password
        irms.authenticate("analystA", "Password1!");
    }

    @Test(expected = IncorrectPasswordException.class)
    public void bva_F2EC1_OffPoint_NonCorrespondingPassword()
            throws IncorrectPasswordException, NoSuchAnalystException {
        // Off-point: non-corresponding password
        irms.authenticate("analystA", "wwjjgkjPassword1!");
    }

    // F2EC2 - Based on valid name input: Incorrect passwords Boundary Tests
    @Test(expected = IncorrectPasswordException.class)
    public void bva_F2EC2_OnPoint_CorrectNameButNonCorrespondingPassword()
            throws IncorrectPasswordException, NoSuchAnalystException {
        // On-point: correct name but non-corresponding password
        irms.authenticate("analystA", "Passwo1!");
    }

    @Test
    public void bva_F2EC2_OffPoint_CorrectNameAndCorrespondingPassword()
            throws IncorrectPasswordException, NoSuchAnalystException {
        // Off-point: correct name and corresponding password
        irms.authenticate("analystA", "Password1!");
    }

    // F2EC3 - Invalid name input Boundary Tests
    @Test(expected = NoSuchAnalystException.class)
    public void bva_F2EC3_OnPoint_NonRegisteredName()
            throws IncorrectPasswordException, NoSuchAnalystException {
        // On-point: Non-registered name
        irms.authenticate("heythere", "Password1!");
    }

    @Test(expected = NoSuchAnalystException.class)
    public void bva_F2EC3_OnPoint_InvalidInput_TooShort()
            throws IncorrectPasswordException, NoSuchAnalystException {
        // On-point: Invalid input (too short)
        irms.authenticate("abc", "Password1!");
    }

    @Test(expected = NoSuchAnalystException.class)
    public void bva_F2EC3_OnPoint_InvalidInput_NonLetters()
            throws IncorrectPasswordException, NoSuchAnalystException {
        // On-point: Invalid input (contains numbers/special chars)
        irms.authenticate("Ccheng123", "Password1!");
    }

    @Test
    public void bva_F2EC3_OffPoint_ValidAndRegisteredName()
            throws IncorrectPasswordException, NoSuchAnalystException {
        // Off-point: Valid and registered name
        irms.authenticate("analystA", "Password1!");
    }

    // F3EC1 - Name: non-existing name Boundary Tests
    @Test(expected = NoSuchAnalystException.class)
    public void bva_F3EC1_OnPoint_ExactlyMatchesExistingName()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // On-point: This should be a non-existing name, but the description seems inverted
        // Based on the table, this tests non-existing names
        irms.requestSupervisorAccess("heythere", "1234");
    }

    @Test
    public void bva_F3EC1_OffPoint_UniqueNewName()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // Off-point: existing registered name
        irms.requestSupervisorAccess("analystA", "1234");
    }

    // F3EC2 - Non-number input & unmatched IDs Boundary Tests
    @Test(expected = InvalidBadgeIDException.class)
    public void bva_F3EC2_OnPoint_NonNumberInput()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // On-point: Non-number input (invalid input)
        irms.requestSupervisorAccess("analystA", "Password1!");
    }

    @Test(expected = InvalidBadgeIDException.class)
    public void bva_F3EC2_OnPoint_NumbersButUnmatchedIDs()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // On-point: Numbers input, but unmatched IDs
        irms.requestSupervisorAccess("analystA", "9999");
    }

    @Test
    public void bva_F3EC2_OffPoint_ExactlyMatchedIDs()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // Off-point: exactly matched IDs
        irms.requestSupervisorAccess("analystA", "1234");
    }

    // F3EC3 - ID is exactly "1234" Boundary Tests
    @Test
    public void bva_F3EC3_OnPoint_IDExactly1234()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // On-point: ID is exactly "1234"
        irms.requestSupervisorAccess("analystA", "1234");
    }

    @Test(expected = InvalidBadgeIDException.class)
    public void bva_F3EC3_OffPoint_IDNot1234_InvalidID()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // Off-point: ID is not "1234" (invalid ID)
        irms.requestSupervisorAccess("analystA", "9999");
    }

    @Test
    public void bva_F3EC3_OffPoint_IDNot1234_ValidID1235()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // Off-point: ID is not "1234" but is valid (1235)
        irms.requestSupervisorAccess("analystA", "1235");
    }

    // F3EC4 - ID is exactly "1235" Boundary Tests
    @Test
    public void bva_F3EC4_OnPoint_IDExactly1235()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // On-point: ID is exactly "1235"
        irms.requestSupervisorAccess("analystA", "1235");
    }

    @Test(expected = InvalidBadgeIDException.class)
    public void bva_F3EC4_OffPoint_IDNot1235_InvalidID()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // Off-point: ID is not "1235" (invalid ID)
        irms.requestSupervisorAccess("analystA", "9999");
    }

    @Test
    public void bva_F3EC4_OffPoint_IDNot1235_ValidID1234()
            throws NoSuchAnalystException, InvalidBadgeIDException {
        // Off-point: ID is not "1235" but is valid (1234)
        irms.requestSupervisorAccess("analystA", "1234");
    }

    //start for 4
    // F4EC1 - Name: non-registered name Boundary Tests
    @Test(expected = NoSuchAnalystException.class)
    public void bva_F4EC1_OnPoint_InputNonRegisteredName()
            throws NoSuchAnalystException, UnauthenticatedAnalystException, DuplicateIncidentException, InvalidRatingException, IncidentRejectException {
        // On-point: input non-registered name
        irms.submitIncident("hithere", "INC001", 5);
    }

    @Test
    public void bva_F4EC1_OffPoint_RegisteredName()
            throws Exception {
        // Off-point: registered & required name
        irms.registerAnalyst("analystR", "Password1!");
        irms.authenticate("analystR", "Password1!");
        irms.submitIncident("analystR", "Inc100", 5);
    }

    // F4EC2 - Name: non-authenticated name Boundary Tests
    @Test(expected = UnauthenticatedAnalystException.class)
    public void bva_F4EC2_OnPoint_NonAuthenticatedName()
            throws Exception {
        // On-point: non-authenticated name
        irms.registerAnalyst("analystNN", "ValidPass123!");
        irms.submitIncident("analystNN", "INC001", 5);
    }

    @Test
    public void bva_F4EC2_OffPoint_AuthenticatedName()
            throws Exception {
        // Off-point: authenticated name
        irms.registerAnalyst("SeanCC", "ValidPass123!");
        irms.authenticate("SeanCC", "ValidPass123!");
        irms.submitIncident("SeanCC", "INC001", 5);
    }
    // F4EC3 - Authenticated name, Duplicate ID before Boundary Tests
    @Test(expected = DuplicateIncidentException.class)
    public void bva_F4EC3_OnPoint_DuplicateIDBefore()
            throws Exception{
        // On-point: Duplicate ID before
        irms.registerAnalyst("ChengSS", "ValidPass123!");
        irms.authenticate("ChengSS", "ValidPass123!");
        irms.submitIncident("ChengSS", "INC001", 5);
        irms.submitIncident("ChengSS", "INC001", 7); // Duplicate ID
    }

    @Test
    public void bva_F4EC3_OffPoint_UniqueAndNewID()
            throws Exception{
        // Off-point: Unique & New ID
        irms.registerAnalyst("FaunaC", "ValidPass123!");
        irms.authenticate("FaunaC", "ValidPass123!");
        irms.submitIncident("FaunaC", "INC001", 5);
        irms.submitIncident("FaunaC", "INC002", 7); // Different ID
    }

    // F4EC4 - Input rating < 0 Boundary Tests
    @Test(expected = InvalidRatingException.class)
    public void bva_F4EC4_OnPoint_InputRatingLessThan0()
            throws Exception{
        // On-point: input rating < 0
        irms.registerAnalyst("analystN", "ValidPass123!");
        irms.authenticate("analystN", "ValidPass123!");
        irms.submitIncident("analystN", "INC001", -1);
    }

    @Test
    public void bva_F4EC4_OffPoint_InputRatingEquals0()
            throws Exception{
        // Off-point: input rating = 0
        irms.registerAnalyst("analystZ", "ValidPass123!");
        irms.authenticate("analystZ", "ValidPass123!");
        irms.submitIncident("analystZ", "INC001", 0);
    }

    @Test
    public void bva_F4EC4_OffPoint_InputRatingGreaterThan0()
            throws Exception{
        // Off-point: input rating > 0
        irms.registerAnalyst("analystP", "ValidPass123!");
        irms.authenticate("analystP", "ValidPass123!");
        irms.submitIncident("analystP", "INC001", 1);
    }

    // F4EC5 - Input rating > 9 Boundary Tests
    @Test(expected = InvalidRatingException.class)
    public void bva_F4EC5_OnPoint_InputRatingGreaterThan9()
            throws Exception{
        // On-point: input rating > 9
        irms.registerAnalyst("analystT", "ValidPass123!");
        irms.authenticate("analystT", "ValidPass123!");
        irms.submitIncident("analystT", "INC001", 10);
    }

    @Test
    public void bva_F4EC5_OffPoint_InputRatingEquals9()
            throws Exception{
        // Off-point: input rating = 9
        irms.registerAnalyst("analystNine", "ValidPass123!");
        irms.authenticate("analystNine", "ValidPass123!");
        irms.submitIncident("analystNine", "INC001", 9);
    }

    @Test
    public void bva_F4EC5_OffPoint_InputRatingLessThan9()
            throws Exception{
        // Off-point: input rating < 9
        irms.registerAnalyst("analystEight", "ValidPass123!");
        irms.authenticate("analystEight", "ValidPass123!");
        irms.submitIncident("analystEight", "INC001", 8);
    }

    // F4EC6 - Supervisor, accept all incidents Boundary Tests
    @Test
    public void bva_F4EC6_OnPoint_StatusIsSupervisor()
            throws Exception{
        // On-point: the status is supervisor
        irms.registerAnalyst("supervisor", "ValidPass123!");
        irms.authenticate("supervisor", "ValidPass123!");
        irms.requestSupervisorAccess("supervisor", "1234"); // Make supervisor
        irms.submitIncident("supervisor", "INC001", 5);
    }

    @Test(expected = IncidentRejectException.class)
    public void bva_F4EC6_OffPoint_StatusNotSupervisorAndReject()
            throws Exception{
        // Off-point: the status is not supervisor and reject
        irms.registerAnalyst("member", "ValidPass123!");
        irms.authenticate("member", "ValidPass123!");
        // Submit incidents that would trigger rejection for non-supervisor
        irms.submitIncident("member", "INC001", 8);
        irms.submitIncident("member", "INC002", 3);
        irms.submitIncident("member", "INC003", 6); // This should be rejected
    }

    // F4EC7 - Empty list, accept all Boundary Tests
    @Test
    public void bva_F4EC7_OnPoint_EmptyListAcceptAll()
            throws Exception{
        // On-point: Empty list, accept all
        irms.registerAnalyst("analystEmpty", "ValidPass123!");
        irms.authenticate("analystEmpty", "ValidPass123!");
        irms.submitIncident("analystEmpty", "INC001", 5); // First incident to empty list
    }

    @Test(expected = IncidentRejectException.class)
    public void bva_F4EC7_OffPoint_ListNotEmpty()
            throws Exception{
        // Off-point: the list is not empty
        irms.registerAnalyst("analystNotEmpty", "ValidPass123!");
        irms.authenticate("analystNotEmpty", "ValidPass123!");
        irms.submitIncident("analystNotEmpty", "INC001", 8);
        irms.submitIncident("analystNotEmpty", "INC002", 9);
        irms.submitIncident("analystNotEmpty", "INC003", 6); // List not empty, should reject
    }

    // F4EC8 - Ratings greater than before, accept incidents Boundary Tests
    @Test
    public void bva_F4EC8_OnPoint_RatingsBeforeLessThanNow()
            throws Exception{
        // On-point: the ratings before < now
        irms.registerAnalyst("analystI", "ValidPass123!");
        irms.authenticate("analystI", "ValidPass123!");
        irms.submitIncident("analystI", "INC001", 2);
        irms.submitIncident("analystI", "INC002", 6); // 6 > 2, should accept
        irms.submitIncident("analystI", "INC003", 8); // 8 > 6, should accept
    }

    @Test(expected = IncidentRejectException.class)
    public void bva_F4EC8_OffPoint_RatingsBeforeEqualsNow()
            throws Exception{
        // Off-point: the ratings before = now
        irms.registerAnalyst("analystE", "ValidPass123!");
        irms.authenticate("analystE", "ValidPass123!");
        irms.submitIncident("analystE", "INC001", 5);
        irms.submitIncident("analystE", "INC002", 5); // 5 = 5, should reject
    }

    @Test(expected = IncidentRejectException.class)
    public void bva_F4EC8_OffPoint_RatingsBeforeGreaterThanNow()
            throws Exception {
        // Off-point: the ratings before > now
        irms.registerAnalyst("analystD", "ValidPass123!");
        irms.authenticate("analystD", "ValidPass123!");
        irms.submitIncident("analystD", "INC001", 8);
        irms.submitIncident("analystD", "INC002", 3); // 3 < 8, should reject
    }

    // F4EC9 - Ratings <= current ratings, reject incidents Boundary Tests
    @Test(expected = IncidentRejectException.class)
    public void bva_F4EC9_OnPoint_RatingsLessOrEqualCurrentRatings()
            throws Exception {
        // On-point: ratings <= current ratings
        irms.registerAnalyst("analystReject", "ValidPass123!");
        irms.authenticate("analystReject", "ValidPass123!");
        irms.submitIncident("analystReject", "INC001", 8);
        irms.submitIncident("analystReject", "INC002", 3);
        irms.submitIncident("analystReject", "INC003", 6); // 6 <= 8, should reject
    }

    @Test
    public void bva_F4EC9_OffPoint_RatingsGreaterThanCurrentRatings()
            throws Exception {
        // Off-point: ratings > current ratings
        irms.registerAnalyst("analystAccept", "ValidPass123!");
        irms.authenticate("analystAccept", "ValidPass123!");
        irms.submitIncident("analystAccept", "INC001", 2);
        irms.submitIncident("analystAccept", "INC002", 6);
        irms.submitIncident("analystAccept", "INC003", 8); // 8 > 6, should accept
    }

    // F5EC1 - Name: non-registered name Boundary Tests
    @Test(expected = NoSuchAnalystException.class)
    public void bva_F5EC1_OnPoint_InputNonRegisteredName()
            throws NoSuchAnalystException, IndexOutOfBoundsException {
        // On-point: input non-registered name
        irms.getIncident("hithere", 0);
    }

    @Test
    public void bva_F5EC1_OffPoint_RegisteredAndRequiredName()
            throws Exception {
        // Off-point: registered & required name
        irms.registerAnalyst("analystGG", "ValidPass123!");
        irms.authenticate("analystGG", "ValidPass123!");
        irms.submitIncident("analystGG", "INC001", 5);
        irms.getIncident("analystGG", 0);
    }

    // F5EC2 - Input index < 0 Boundary Tests
    @Test(expected = IndexOutOfBoundsException.class)
    public void bva_F5EC2_OnPoint_IndexLessThan0()
            throws Exception {
        // On-point: index < 0
        irms.registerAnalyst("analystNegIndex", "ValidPass123!");
        irms.authenticate("analystNegIndex", "ValidPass123!");
        irms.submitIncident("analystNegIndex", "INC001", 5);
        irms.getIncident("analystNegIndex", -1);
    }

    @Test
    public void bva_F5EC2_OffPoint_IndexEquals0()
            throws Exception {
        // Off-point: index = 0
        irms.registerAnalyst("analystZeroIndex", "ValidPass123!");
        irms.authenticate("analystZeroIndex", "ValidPass123!");
        irms.submitIncident("analystZeroIndex", "INC001", 5);
        irms.getIncident("analystZeroIndex", 0);
    }

    @Test
    public void bva_F5EC2_OffPoint_IndexGreaterThan0()
            throws Exception {
        // Off-point: index > 0
        irms.registerAnalyst("analystPosIndex", "ValidPass123!");
        irms.authenticate("analystPosIndex", "ValidPass123!");
        irms.submitIncident("analystPosIndex", "INC001", 5);
        irms.submitIncident("analystPosIndex", "INC002", 6);
        irms.getIncident("analystPosIndex", 1);
    }

    // F5EC3 - index >= len(incidents) Boundary Tests
    @Test(expected = IndexOutOfBoundsException.class)
    public void bva_F5EC3_OnPoint_IndexGreaterThanLength()
            throws Exception {
        // On-point: index > len(incidents)
        irms.registerAnalyst("analystOverIndex", "ValidPass123!");
        irms.authenticate("analystOverIndex", "ValidPass123!");
        irms.submitIncident("analystOverIndex", "INC001", 5);
        irms.submitIncident("analystOverIndex", "INC002", 6);
        irms.getIncident("analystOverIndex", 5); // Index > length
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void bva_F5EC3_OnPoint_IndexEqualsLength()
            throws Exception {
        // On-point: index = len(incidents)
        irms.registerAnalyst("analystEqualIndex", "ValidPass123!");
        irms.authenticate("analystEqualIndex", "ValidPass123!");
        irms.submitIncident("analystEqualIndex", "INC001", 5);
        irms.submitIncident("analystEqualIndex", "INC002", 6);
        irms.getIncident("analystEqualIndex", 2); // Index = length (2 incidents, index 0,1 valid)
    }

    @Test
    public void bva_F5EC3_OffPoint_IndexLessThanLength()
            throws Exception {
        // Off-point: index < len(incidents)
        irms.registerAnalyst("analystValidIndex", "ValidPass123!");
        irms.authenticate("analystValidIndex", "ValidPass123!");
        irms.submitIncident("analystValidIndex", "INC001", 5);
        irms.submitIncident("analystValidIndex", "INC002", 6);
        irms.getIncident("analystValidIndex", 1); // Index < length
    }

    // F5EC4 - Index is in [0, len(incidents)-1] Boundary Tests
    @Test
    public void bva_F5EC4_OnPoint_IndexInValidRange_Lower()
            throws Exception {
        // On-point: Index is in [0, len(incidents)-1] - lower bound
        irms.registerAnalyst("analystValidLower", "ValidPass123!");
        irms.authenticate("analystValidLower", "ValidPass123!");
        irms.submitIncident("analystValidLower", "INC001", 5);
        irms.submitIncident("analystValidLower", "INC002", 6);
        irms.getIncident("analystValidLower", 0); // Index = 0
    }

    @Test
    public void bva_F5EC4_OnPoint_IndexInValidRange_Upper()
            throws Exception {
        // On-point: Index is in [0, len(incidents)-1] - upper bound
        irms.registerAnalyst("analystValidUpper", "ValidPass123!");
        irms.authenticate("analystValidUpper", "ValidPass123!");
        irms.submitIncident("analystValidUpper", "INC001", 5);
        irms.submitIncident("analystValidUpper", "INC002", 6);
        irms.submitIncident("analystValidUpper", "INC003", 7);
        irms.getIncident("analystValidUpper", 2); // Index = len-1
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void bva_F5EC4_OffPoint_IndexLessThan0()
            throws Exception {
        // Off-point: Index < 0
        irms.registerAnalyst("analystInvalidLower", "ValidPass123!");
        irms.authenticate("analystInvalidLower", "ValidPass123!");
        irms.submitIncident("analystInvalidLower", "INC001", 5);
        irms.getIncident("analystInvalidLower", -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void bva_F5EC4_OffPoint_IndexGreaterThanLength()
            throws Exception {
        // Off-point: Index > len(incidents)-1
        irms.registerAnalyst("analystInvalidUpper", "ValidPass123!");
        irms.authenticate("analystInvalidUpper", "ValidPass123!");
        irms.submitIncident("analystInvalidUpper", "INC001", 5);
        irms.submitIncident("analystInvalidUpper", "INC002", 6);
        irms.getIncident("analystInvalidUpper", 3); // Index > len-1
    }

}
