package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* loaded from: classes4.dex */
public class ModeAction extends CommandAction {
    public final String mExtraValue;
    public final int mNewMode;

    public ModeAction(int i) {
        this.mNewMode = i;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final String getActionTemplateId() {
        return null;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final int getActionType() {
        return 6;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putInt("key_new_mode", this.mNewMode);
        String str = this.mExtraValue;
        if (str != null) {
            dataBundle.putString("key_extra_value", str);
        }
        return dataBundle;
    }

    public ModeAction(int i, String str) {
        this.mNewMode = i;
        this.mExtraValue = str;
    }

    public ModeAction(Bundle bundle) {
        super(bundle);
        this.mNewMode = bundle.getInt("key_new_mode");
        this.mExtraValue = bundle.containsKey("key_extra_value") ? bundle.getString("key_extra_value") : null;
    }
}
