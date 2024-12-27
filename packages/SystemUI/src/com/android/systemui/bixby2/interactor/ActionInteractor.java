package com.android.systemui.bixby2.interactor;

import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.provider.ICommandActionCallback;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ActionInteractor {
    List<String> getSupportingActions();

    Command loadStatefulCommandInteractor(String str, Command command);

    default Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) {
        return null;
    }

    void performCommandActionInteractor(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback);
}
