package com.samsung.android.service.ProtectedATCommand;

import android.util.Slog;
import com.samsung.android.service.ProtectedATCommand.list.ATCommands;

/* loaded from: classes6.dex */
public class ATCommandCheckerWithInHouse extends ATCommandChecker {
    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkAdditionalCondition(String str) {
        return 1;
    }

    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkProtectedCommand(Device device, ATCommands aTCommands, String str) {
        Slog.i("PACMClassifier", "This Command is a protected command");
        int iCheckAllCondition = checkAllCondition(device, aTCommands, str);
        if (iCheckAllCondition != 1) {
            return iCheckAllCondition;
        }
        Slog.i("PACMClassifier", "This Command is NOT_ALLOWED_PROTECTED_AT_COMMAND");
        return 177;
    }

    @Override // com.samsung.android.service.ProtectedATCommand.ATCommandChecker
    protected int checkUnregisteredCommand(Device device, ATCommands aTCommands) {
        int iCheckAbsoluteCondition = checkAbsoluteCondition(device, aTCommands);
        if (iCheckAbsoluteCondition != 1) {
            return iCheckAbsoluteCondition;
        }
        Slog.i("PACMClassifier", "This command is not allowed because the command is an unregistered command");
        return 175;
    }
}
