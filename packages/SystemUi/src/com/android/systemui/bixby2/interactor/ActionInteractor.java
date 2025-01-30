package com.android.systemui.bixby2.interactor;

import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.provider.ICommandActionCallback;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ActionInteractor {
    List<String> getSupportingActions();

    Command loadStatefulCommandInteractor(String str, Command command);

    default Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) {
        return null;
    }

    void performCommandActionInteractor(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback);
}
