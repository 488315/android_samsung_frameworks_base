package com.samsung.android.sdk.command.provider;

import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ICommandActionHandler {
    List createStatelessCommands();

    Command loadStatefulCommand(String str);

    Command loadStatefulCommand(String str, CommandAction commandAction);

    CommandAction migrateCommandAction(String str, CommandAction commandAction);

    void performCommandAction(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback);
}
