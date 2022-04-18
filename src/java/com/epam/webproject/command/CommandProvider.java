package com.epam.webproject.command;

import com.epam.webproject.command.impl.GoToLoginPageCommand;
import java.util.EnumMap;

public class CommandProvider {
    
    private static final EnumMap<CommandType, Command> commands = new EnumMap(CommandType.class);
    private static CommandProvider instance;
    
    static {
        commands.put(CommandType.GO_TO_LOGIN_PAGE_COMMAND, new GoToLoginPageCommand());
        //commands.put(CommandType.GO_TO_ERROR_PAGE_COMMAND, new GoToErrorPageCommand());
    }
    
    private CommandProvider() {
    }
    
    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }
    
     public Command getCommand(String commandName) {
        if (commandName == null) {
            return commands.get(CommandType.DEFAULT);
        }
        CommandType commandType;
        try {
            commandType = CommandType.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = CommandType.DEFAULT;
        }
        return commands.get(commandType);
    }
}
