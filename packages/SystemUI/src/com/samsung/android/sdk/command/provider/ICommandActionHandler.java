package com.samsung.android.sdk.command.provider;

import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ICommandActionHandler {
    List createStatelessCommands();

    Command loadStatefulCommand(String str);

    Command loadStatefulCommand(String str, CommandAction commandAction);

    CommandAction migrateCommandAction(String str, CommandAction commandAction);

    void performCommandAction(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback);
}
