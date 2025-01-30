package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BooleanAction extends CommandAction {
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
