package com.android.systemui.bixby2.interactor;

import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.provider.ICommandActionCallback;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface ActionInteractor {
    List<String> getSupportingActions();

    Command loadStatefulCommandInteractor(String str, Command command);

    default Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) {
        return null;
    }

    void performCommandActionInteractor(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback);
}
