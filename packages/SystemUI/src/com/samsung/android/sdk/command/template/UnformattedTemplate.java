package com.samsung.android.sdk.command.template;

import android.os.Bundle;

/* loaded from: classes4.dex */
public class UnformattedTemplate extends CommandTemplate {
    public final String mJSONString;

    public UnformattedTemplate(String str) {
        super("unformatted");
        this.mJSONString = str;
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putString("key_new_value", this.mJSONString);
        return dataBundle;
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final int getTemplateType() {
        return 6;
    }

    public UnformattedTemplate(Bundle bundle) {
        super(bundle);
        this.mJSONString = bundle.getString("key_new_value");
    }
}
