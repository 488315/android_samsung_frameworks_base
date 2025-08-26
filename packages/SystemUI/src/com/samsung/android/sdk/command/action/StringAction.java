package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* loaded from: classes4.dex */
public class StringAction extends CommandAction {
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
