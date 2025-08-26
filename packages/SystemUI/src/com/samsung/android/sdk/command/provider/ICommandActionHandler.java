package com.samsung.android.sdk.command.provider;

import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICommandActionHandler {
    List createStatelessCommands();

    Command loadStatefulCommand(String str);

    Command loadStatefulCommand(String str, CommandAction commandAction);

    CommandAction migrateCommandAction(String str, CommandAction commandAction);

    void performCommandAction(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback);
}
