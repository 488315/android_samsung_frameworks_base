package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TriggerAction extends CommandAction {
    public final CommandAction mTargetCommandAction;
    public final boolean mTriggerState;

    public TriggerAction(boolean z, CommandAction commandAction) {
        this.mTriggerState = z;
        this.mTargetCommandAction = commandAction;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final String getActionTemplateId() {
        return null;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final int getActionType() {
        return 99;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putBoolean("key_trigger_state", this.mTriggerState);
        CommandAction commandAction = this.mTargetCommandAction;
        if (commandAction != null) {
            dataBundle.putBundle("key_target_command_action", commandAction.getDataBundle());
        }
        return dataBundle;
    }

    public TriggerAction(Bundle bundle) {
        super(bundle);
        this.mTriggerState = bundle.getBoolean("key_trigger_state");
        if (bundle.containsKey("key_target_command_action")) {
            this.mTargetCommandAction = CommandAction.createActionFromBundle(bundle.getBundle("key_target_command_action"));
        }
    }
}
