package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ModeAction extends CommandAction {
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
