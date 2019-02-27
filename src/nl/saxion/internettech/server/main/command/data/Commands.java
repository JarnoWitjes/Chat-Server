package nl.saxion.internettech.server.main.command.data;

/**
 * @author jarnowitjes on 2019-02-26
 */
public class Commands {

    private Commands()
    {}

    //General commands
    public static final String BROADCAST = "/bcst";
    public static final String GET_USERS = "/getusers";
    public static final String QUIT = "/quit";

    //Group commands
    public static final String CREATE_GROUP = "/creategroup";
    public static final String GET_GROUPS = "/getgroups";
    public static final String JOIN_GROUP = "/joingroup";
    public static final String SEND_GROUP_MESSAGE = "/group";
    public static final String LEAVE_GROUP = "/leavegroup";
    public static final String KICK = "/kick";

    //Encryption commands
    public static final String PRIVATE_MESSAGE = "/pm";
}