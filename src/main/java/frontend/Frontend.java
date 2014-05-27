package frontend;

import messageSystem.messages.MessageToAccountServiceToCheckPass;
import messageSystem.messages.MessageToAccountServiceToRegister;
import services.AccountService;
import messageSystem.MessageSystem;
import messageSystem.Subscriber;
import messageSystem.Address;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Frontend extends HttpServlet implements Subscriber, Runnable{
    private static final String AUTH_STATUS_FIELD_NAME = "auth_done";
    private static final String INFO_BASE_FAULT = "Сервер не может связаться с базой";
    private static final String INFO_BASE_NO_USER = "Пользователь с таким именем не обнаружен";
    private static final String INFO_BASE_USER_EXISTS = "Логин занят";
    private static final String INFO_BASE_WRONG_PASS = "Неправильная пара логин, пароль";
    private static final String INFO_BASE_IN_PROGRESS = "Проверка пароля...";
    private static final String INFO_BASE_AUTH_DONE = "Авторизован, id = ";
    private static final String INFO_BASE_AUTH_NONE = "Не авторизован";

    private static final String STATUS_NAME = "status";
    private static final String INFO_404 = "Указанный адрес не найден, Вы возвращены на главную";
    private static final String INFO_NO_ACCESS = "Авторизуйтесь, чтобы войти в игру";
    private static final String REFRESH_TIME = "1000";

    private Address address;
    private MessageSystem messageSystem;

    private Map<String, UserSession> sessions = new HashMap<>();

    public Frontend(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
        address = new Address();
        messageSystem.addService(this);
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        System.out.append("\n\n").append("Frontend: doGet: ").append(request.getPathInfo());

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getPathInfo().equals("/index")) {
            handleGetIndex(request, response);
        }else if (request.getPathInfo().equals("/login")) {
            handleGetLogin(request, response);
        }else if (request.getPathInfo().equals("/register")) {
            handleGetRegister(request, response);
        }else if (request.getPathInfo().equals("/game")) {
            handleGetGame(request, response);
        }else if (request.getPathInfo().equals("/logout")) {
            handleGetLogout(request, response);
        }else{
            handleGet404(request, response);
        }
    }

    private void handleGetIndex(HttpServletRequest request, HttpServletResponse response
    ) throws IOException{
        Map<String, Object> pageVariables = new HashMap<>();
        UserSession currentSession = getSession(request.getSession().getId());
        outputUserId(pageVariables, currentSession);
        pageVariables.put("refreshPeriod", REFRESH_TIME);

        response.getWriter().println(PageGenerator.getPage("index.tml", pageVariables));
    }

    private void handleGetLogin(HttpServletRequest request, HttpServletResponse response
    ) throws IOException{
        Map<String, Object> pageVariables = new HashMap<>();
        UserSession currentSession = getSession(request.getSession().getId());
        if(currentSession.getUserId() != null && currentSession.getUserId() == -5){
            response.sendRedirect("/index");
            return;
        }
        outputUserId(pageVariables, currentSession);
        pageVariables.put("refreshPeriod", REFRESH_TIME);

        response.getWriter().println(PageGenerator.getPage("login.tml", pageVariables));
    }

    private void handleGetRegister(HttpServletRequest request, HttpServletResponse response
    ) throws IOException{
        Map<String, Object> pageVariables = new HashMap<>();
        UserSession currentSession = getSession(request.getSession().getId());
        if(currentSession.getUserId() != null && currentSession.getUserId() == -5){
            response.sendRedirect("/index");
            return;
        }
        outputUserId(pageVariables, currentSession);
        pageVariables.put("refreshPeriod", REFRESH_TIME);

        response.getWriter().println(PageGenerator.getPage("register.tml", pageVariables));
    }

    private void handleGetGame(HttpServletRequest request, HttpServletResponse response
    ) throws IOException{
        Map<String, Object> pageVariables = new HashMap<>();
        UserSession currentSession = getSession(request.getSession().getId());
        if(outputUserId(pageVariables, currentSession)){
            pageVariables.put("serverTime", getTime());
            pageVariables.put("refreshPeriod", REFRESH_TIME);
            response.getWriter().println(PageGenerator.getPage("game.tml", pageVariables));
        }else{
            getSession(request.getSession().getId()).setUserMessage(INFO_NO_ACCESS);
            response.sendRedirect("/index");
        }
    }

    private void handleGetLogout(HttpServletRequest request, HttpServletResponse response
    ) throws IOException{
        Map<String, Object> pageVariables = new HashMap<>();
        UserSession currentSession = getSession(request.getSession().getId());
        if(currentSession.getUserId() >= 0){
            currentSession.setUserId(null);
        }
        response.sendRedirect("/index");
    }

    private void handleGet404(HttpServletRequest request, HttpServletResponse response
    ) throws IOException{
        System.out.append("miss ").append("\n");
        getSession(request.getSession().getId()).setUserMessage(INFO_404);
        System.out.append(getSession(request.getSession().getId()).getUserMessage()).append("\n");
        response.sendRedirect("/index");
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        System.out.append("\n\n").append("Frontend: doPost: ").append(request.getPathInfo());

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getPathInfo().equals("/login")) {
            handlePostLogin(request, response);
        }else if (request.getPathInfo().equals("/register")) {
            handlePostRegister(request, response);
        }else{
            response.sendRedirect("/index");
        }
    }

    private void handlePostLogin(HttpServletRequest request, HttpServletResponse response
    ) throws IOException{
        System.out.append("Frontend: handlePostLogin: ").append("\n");
        UserSession currentSession = getSession(request.getSession().getId());
        //base is not working with this session`s request
        if(currentSession.getUserId() == null || currentSession.getUserId() != -5){
            String login = request.getParameter("login");
            String pass = request.getParameter("pass");
            getMessageSystem().sendMessage(new MessageToAccountServiceToCheckPass(this.getAddress(),
                    getMessageSystem().getAddressService().getService(AccountService.class),
                    login, pass, currentSession.getSessionId()));
            currentSession.setUserId((long) -5);
        }
        response.sendRedirect("/index");
    }

    private void handlePostRegister(HttpServletRequest request, HttpServletResponse response
    ) throws IOException{
        UserSession currentSession = getSession(request.getSession().getId());
        //base is not working with this session`s request
        if(currentSession.getUserId() == null || currentSession.getUserId() != -5){
            String login = request.getParameter("login");
            String pass = request.getParameter("pass");
            getMessageSystem().sendMessage(new MessageToAccountServiceToRegister(this.getAddress(),
                    getMessageSystem().getAddressService().getService(AccountService.class),
                    login, pass, currentSession.getSessionId()));
            currentSession.setUserId((long) -5);
        }
        response.sendRedirect("/index");
    }


    private UserSession getSession(String sessionKey){
        UserSession session;
        if(sessions.containsKey(sessionKey)) {
            session = sessions.get(sessionKey);
        }else{
            session = new UserSession(sessionKey);
            sessions.put(sessionKey, session);
        }
        return session;
    }

    private boolean outputUserId(Map<String, Object> pageVariables, UserSession currentSession){
        Long userId = currentSession.getUserId();
        pageVariables.put(STATUS_NAME, currentSession.getUserMessage());
        currentSession.setUserMessage("");
        if(userId == null){
            pageVariables.put(AUTH_STATUS_FIELD_NAME, INFO_BASE_AUTH_NONE);
        }else if(userId == -2){
            pageVariables.put(AUTH_STATUS_FIELD_NAME, INFO_BASE_USER_EXISTS);
        }else if(userId == -3){
            pageVariables.put(AUTH_STATUS_FIELD_NAME, INFO_BASE_WRONG_PASS);
        }else if(userId == -4){
            pageVariables.put(AUTH_STATUS_FIELD_NAME, INFO_BASE_FAULT);
        }else if(userId == -5){
            pageVariables.put(AUTH_STATUS_FIELD_NAME, INFO_BASE_IN_PROGRESS);
        }else if(userId == -1){
            pageVariables.put(AUTH_STATUS_FIELD_NAME, INFO_BASE_NO_USER);
        }else if(userId >= 0){
            pageVariables.put(AUTH_STATUS_FIELD_NAME, INFO_BASE_AUTH_DONE + userId);
            return true;
        }
        return false;
    }

    public static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat formatter = new SimpleDateFormat("HH.mm.ss");
        return formatter.format(date);
    }

    public void updateUserId(String sessionId, long userId){
        sessions.get(sessionId).setUserId(userId);
        System.out.append("updateUserId").
                append(String.valueOf(sessions.get(sessionId).getUserId())).append("\n");
    }

    public void run(){
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageSystem.execForSubscriber(this);
        }
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    @Override
    public Address getAddress() {
        return address;
    }
}
