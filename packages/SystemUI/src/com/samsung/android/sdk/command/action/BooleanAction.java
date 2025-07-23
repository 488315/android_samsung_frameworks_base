package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BooleanAction extends CommandAction {
    public final boolean mNewState;

    public BooleanAction(boolean z) {
        this.mNewState = z;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final String getActionTemplateId() {
        return null;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final int getActionType() {
        return 1;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putBoolean("key_new_state", this.mNewState);
        return dataBundle;
    }

    public BooleanAction(Bundle bundle) {
        super(bundle);
        this.mNewState = bundle.getBoolean("key_new_state");
    }
}
