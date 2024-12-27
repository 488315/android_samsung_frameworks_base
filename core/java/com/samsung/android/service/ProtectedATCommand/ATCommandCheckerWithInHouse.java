package com.samsung.android.service.ProtectedATCommand;

import android.util.Slog;
import com.samsung.android.service.ProtectedATCommand.list.ATCommands;

/* loaded from: classes6.dex */
public class ATCommandCheckerWithInHouse extends ATCommandChecker {
    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkAdditionalCondition(String string) {
        return 1;
    }

    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkProtectedCommand(Device device, ATCommands atCommand, String strCmd) {
        Slog.i("PACMClassifier", "This Command is a protected command");
        int result = checkAllCondition(device, atCommand, strCmd);
        if (result != 1) {
            return result;
        }
        Slog.i("PACMClassifier", "This Command is NOT_ALLOWED_PROTECTED_AT_COMMAND");
        return 177;
    }

    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkUnregisteredCommand(Device device, ATCommands atCommand) {
        int result = checkAbsoluteCondition(device, atCommand);
        if (result != 1) {
            return result;
        }
        Slog.i("PACMClassifier", "This command is not allowed because the command is an unregistered command");
        return 175;
    }
}
