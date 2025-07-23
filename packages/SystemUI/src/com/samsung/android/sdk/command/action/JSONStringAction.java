package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class JSONStringAction extends CommandAction {
    public final String mNewValue;

    public JSONStringAction(String str) {
        this.mNewValue = str;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final int getActionType() {
        return 5;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putString("key_new_value", this.mNewValue);
        return dataBundle;
    }

    public JSONStringAction(Bundle bundle) {
        super(bundle);
        this.mNewValue = bundle.getString("key_new_value");
    }
}
