package com.samsung.android.service.ProtectedATCommand;

import android.util.Slog;
import com.samsung.android.service.ProtectedATCommand.list.ATCommands;

/* loaded from: classes6.dex */
public class ATCommandCheckerWithJDM extends ATCommandChecker {
    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkAdditionalCondition(String str) {
        if (checkJDMOpenCommand(str) == 1) {
            Slog.i("PACMClassifier", "This command is allowed because this device is a JDM device");
            return 161;
        }
        if (checkJDMProtectedCommand(str) != 1) {
            return 1;
        }
        Slog.e("PACMClassifier", "This command is not allowed because this device is a JDM device");
        return 177;
    }

    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkProtectedCommand(Device device, ATCommands aTCommands, String str) {
        Slog.i("PACMClassifier", "This Command is a protected command");
        int checkAllCondition = checkAllCondition(device, aTCommands, str);
        if (checkAllCondition != 1) {
            return checkAllCondition;
        }
        return 161;
    }

    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkUnregisteredCommand(Device device, ATCommands aTCommands) {
        int checkAbsoluteCondition = checkAbsoluteCondition(device, aTCommands);
        if (checkAbsoluteCondition != 1) {
            return checkAbsoluteCondition;
        }
        Slog.i("PACMClassifier", "Although this command is an unregistered command, the command is allowed because this device is a JDM device");
        return 161;
    }

    private int checkJDMOpenCommand(String str) {
        Slog.i("PACMClassifier", "Input Cmd : " + str);
        if (str == null) {
            Slog.e("PACMClassifier", "cmd is null");
            return -268435456;
        }
        String[] strArr = {"AT+IFPMICCN=0,0,6,0", "AT+BATTTEST=4,7", "AT+PRODCODE=2,"};
        for (int i = 0; i < 3; i++) {
            try {
                if (str.indexOf(strArr[i]) == 0) {
                    Slog.i("PACMClassifier", "This command is a JDM open command");
                    return 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return -268435456;
            }
        }
        return 176;
    }

    private int checkJDMProtectedCommand(String str) {
        Slog.i("PACMClassifier", "Input Cmd : " + str);
        if (str == null) {
            Slog.e("PACMClassifier", "cmd is null");
            return -268435456;
        }
        String[] strArr = {"AT+ALERTDIS=0,", "AT+DEBUGLVC=0,5", "AT+DEBUGLVC=0,6", "AT+DEVROOTK=2,2,", "AT+DEVROOTK=2,3,"};
        for (int i = 0; i < 5; i++) {
            try {
                if (str.indexOf(strArr[i]) == 0) {
                    Slog.i("PACMClassifier", "This command is a JDM protected command");
                    return 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return -268435456;
            }
        }
        return 176;
    }
}
