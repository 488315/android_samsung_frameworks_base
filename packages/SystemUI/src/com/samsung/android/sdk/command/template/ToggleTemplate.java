package com.samsung.android.sdk.command.template;

import android.os.Bundle;

/* loaded from: classes4.dex */
public class ToggleTemplate extends CommandTemplate {
    public final boolean mIsChecked;

    public ToggleTemplate(boolean z) {
        super("toggle");
        this.mIsChecked = z;
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putBoolean("key_new_value", this.mIsChecked);
        return dataBundle;
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final int getTemplateType() {
        return 2;
    }

    public ToggleTemplate(Bundle bundle) {
        super(bundle);
        this.mIsChecked = bundle.getBoolean("key_new_value");
    }
}
