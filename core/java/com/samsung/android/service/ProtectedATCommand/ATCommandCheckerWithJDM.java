package com.samsung.android.service.ProtectedATCommand;

import android.util.Slog;

import com.samsung.android.service.ProtectedATCommand.list.ATCommands;

/* loaded from: classes6.dex */
public class ATCommandCheckerWithJDM extends ATCommandChecker {
    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkAdditionalCondition(String command) {
        if (checkJDMOpenCommand(command) == 1) {
            Slog.i("PACMClassifier", "This command is allowed because this device is a JDM device");
            return 161;
        }
        if (checkJDMProtectedCommand(command) != 1) {
            return 1;
        }
        Slog.e("PACMClassifier", "This command is not allowed because this device is a JDM device");
        return 177;
    }

    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkProtectedCommand(Device device, ATCommands atCommand, String strCmd) {
        Slog.i("PACMClassifier", "This Command is a protected command");
        int result = checkAllCondition(device, atCommand, strCmd);
        if (result != 1) {
            return result;
        }
        return 161;
    }

    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkUnregisteredCommand(Device device, ATCommands atCommand) {
        int result = checkAbsoluteCondition(device, atCommand);
        if (result != 1) {
            return result;
        }
        Slog.i(
                "PACMClassifier",
                "Although this command is an unregistered command, the command is allowed because"
                    + " this device is a JDM device");
        return 161;
    }

    private int checkJDMOpenCommand(String strCmd) {
        Slog.i("PACMClassifier", "Input Cmd : " + strCmd);
        if (strCmd == null) {
            Slog.e("PACMClassifier", "cmd is null");
            return -268435456;
        }
        String[] openCmds = {"AT+IFPMICCN=0,0,6,0", "AT+BATTTEST=4,7", "AT+PRODCODE=2,"};
        try {
            for (String openCmd : openCmds) {
                if (strCmd.indexOf(openCmd) == 0) {
                    Slog.i("PACMClassifier", "This command is a JDM open command");
                    return 1;
                }
            }
            return 176;
        } catch (Exception e) {
            e.printStackTrace();
            return -268435456;
        }
    }

    private int checkJDMProtectedCommand(String strCmd) {
        Slog.i("PACMClassifier", "Input Cmd : " + strCmd);
        if (strCmd == null) {
            Slog.e("PACMClassifier", "cmd is null");
            return -268435456;
        }
        String[] protectedCmds = {
            "AT+ALERTDIS=0,",
            "AT+DEBUGLVC=0,5",
            "AT+DEBUGLVC=0,6",
            "AT+DEVROOTK=2,2,",
            "AT+DEVROOTK=2,3,"
        };
        try {
            for (String protectedCmd : protectedCmds) {
                if (strCmd.indexOf(protectedCmd) == 0) {
                    Slog.i("PACMClassifier", "This command is a JDM protected command");
                    return 1;
                }
            }
            return 176;
        } catch (Exception e) {
            e.printStackTrace();
            return -268435456;
        }
    }
}
