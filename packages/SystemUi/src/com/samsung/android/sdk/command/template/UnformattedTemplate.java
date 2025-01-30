package com.samsung.android.sdk.command.template;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class UnformattedTemplate extends CommandTemplate {
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
