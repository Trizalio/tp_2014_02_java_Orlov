package frontend;

import services.AccountService;
import org.junit.Assert;
import org.junit.Test;


public class AccountServiceTest {
    private static Integer localTestNumber = 0;

    private void printTestNumber(){
        System.out.append("\n");
        System.out.append(localTestNumber.toString());
        localTestNumber += 1;
    }

/*
    @Test
    public void testService() throws Exception {
        testInitBase();
        testAttemptToAddGoodUser();
        testSetUserKeyForGoodUser();
        testCheckGoodUserKeyForGoodUser();
//        testGetUserKeyForBadUser();
        testCheckBadUserKeyForGoodUser();
        testCheckUserKeyForBadUser();
        testAttemptToAddBadUser();
        testAttemptToLoginWithGoodLoginAndGoodPass();
        testAttemptToLoginWithGoodLoginAndBadPass();
        testAttemptToLoginWithBadLogin();
        testAttemptToRenameGoodUser();
        testAttemptToRenameBadUser();
        testAttemptToDeleteGoodUser();
        testAttemptToDeleteBadUser();
    }

    public boolean testInitBase() throws Exception {
        printTestNumber();
        System.out.append(". testAttemptToAddGoodUser\n");
        String login = "good";
        String password = "guy";
        boolean result = AccountService.initBase();
        Assert.assertTrue(result);
        return result;
    }

    public boolean testAttemptToAddGoodUser() throws Exception {
        printTestNumber();
        System.out.append(". testAttemptToAddGoodUser\n");
        String login = "good";
        String password = "guy";
        boolean result = AccountService.registerUser(login, password);
        Assert.assertTrue(result);
        return result;
    }

    public boolean testSetUserKeyForGoodUser() throws Exception {
        printTestNumber();
        System.out.append(". testGetUserKeyForGoodUser\n");
        String login = "good";
        String key = "good";

        boolean result = AccountService.setUserKey(login, key);
        Assert.assertNotNull(result);
        return result;
    }

    public void testCheckGoodUserKeyForGoodUser() throws Exception {
        printTestNumber();
        System.out.append(". testCheckGoodUserKeyForGoodUser\n");
        String login = "good";
        String key = "good";

        Assert.assertTrue(AccountService.checkUserKey(login, key));
    }

    public void testGetUserKeyForBadUser() throws Exception {
        printTestNumber();
        System.out.append(". testGetUserKeyForBadUser\n");
        String login = "bad";
        String key = "good";
        Assert.assertNull(AccountService.setUserKey(login, key));
    }

    public void testCheckBadUserKeyForGoodUser() throws Exception {
        printTestNumber();
        System.out.append(". testCheckBadUserKeyForGoodUser\n");
        String login = "good";
        String key = "bad";
        Assert.assertFalse(AccountService.checkUserKey(login, key));
    }

    public void testCheckUserKeyForBadUser() throws Exception {
        printTestNumber();
        System.out.append(". testCheckUserKeyForBadUser\n");
        String login = "bad";
        String key = "good";
        Assert.assertFalse(AccountService.checkUserKey(login, key));
    }

    public void testAttemptToAddBadUser() throws Exception {
        printTestNumber();
        System.out.append(". testAttemptToAddBadUser\n");
        String login = "good";
        String password = "guy";
        Assert.assertFalse(AccountService.registerUser(login, password));
    }

    public void testAttemptToLoginWithGoodLoginAndGoodPass() throws Exception {
        printTestNumber();
        System.out.append(". testAttemptToLoginWithGoodLoginAndGoodPass\n");
        String login = "good";
        String password = "guy";
        Assert.assertTrue(AccountService.loginUser(login, password));
    }

    public void testAttemptToLoginWithGoodLoginAndBadPass() throws Exception {
        printTestNumber();
        System.out.append(". testAttemptToLoginWithGoodLoginAndBadPass\n");
        String login = "good";
        String password = "folk";
        Assert.assertFalse(AccountService.loginUser(login, password));
    }

    public void testAttemptToLoginWithBadLogin() throws Exception {
        printTestNumber();
        System.out.append(". testAttemptToLoginWithBadLogin\n");
        String login = "bad";
        String password = "guy";
        Assert.assertFalse(AccountService.loginUser(login, password));
    }
    public void testAttemptToRenameGoodUser() throws Exception {
        printTestNumber();
        System.out.append(". testAttemptToRenameGoodUser\n");
        String loginOld = "good";
        String loginNew = "awesome";
        Assert.assertTrue(AccountService.modifyUser(loginOld, loginNew));
    }
    public void testAttemptToRenameBadUser() throws Exception {
        printTestNumber();
        System.out.append(". testAttemptToRenameBadUser\n");
        String loginOld = "bad";
        String loginNew = "awesome";
        Assert.assertFalse(AccountService.modifyUser(loginOld, loginNew));
    }
    public void testAttemptToDeleteGoodUser() throws Exception {
        printTestNumber();
        System.out.append(". testAttemptToDeleteGoodUser\n");
        String login = "awesome";
        Assert.assertTrue(AccountService.deleteUser(login));
    }
    public void testAttemptToDeleteBadUser() throws Exception {
        printTestNumber();
        System.out.append(". testAttemptToDeleteBadUser\n");
        String login = "good";
        Assert.assertFalse(AccountService.deleteUser(login));
    }
*/
}
