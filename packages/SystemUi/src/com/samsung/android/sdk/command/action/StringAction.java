package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class StringAction extends CommandAction {
    public final String mNewValue;

    public StringAction(String str) {
        this.mNewValue = str;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final int getActionType() {
        return 4;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putString("key_new_value", this.mNewValue);
        return dataBundle;
    }

    public StringAction(Bundle bundle) {
        super(bundle);
        this.mNewValue = bundle.getString("key_new_value");
    }
}
