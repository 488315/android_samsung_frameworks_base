package com.android.systemui.bixby2.interactor;

import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.provider.ICommandActionCallback;
import java.util.List;

public interface ActionInteractor {
    List<String> getSupportingActions();

    Command loadStatefulCommandInteractor(String str, Command command);

    default Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) {
        return null;
    }

    void performCommandActionInteractor(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback);
}
