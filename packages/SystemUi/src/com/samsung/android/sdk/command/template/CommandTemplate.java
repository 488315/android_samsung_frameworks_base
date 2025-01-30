package com.samsung.android.sdk.command.template;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class CommandTemplate {
    public final String mTemplateId;
    public static final C47581 NO_TEMPLATE = new CommandTemplate() { // from class: com.samsung.android.sdk.command.template.CommandTemplate.1
        @Override // com.samsung.android.sdk.command.template.CommandTemplate
        public final int getTemplateType() {
            return 1;
        }
    };
    public static final C47592 ERROR_TEMPLATE = new CommandTemplate() { // from class: com.samsung.android.sdk.command.template.CommandTemplate.2
        @Override // com.samsung.android.sdk.command.template.CommandTemplate
        public final int getTemplateType() {
            return 0;
        }
    };

    public CommandTemplate() {
        this.mTemplateId = "";
    }

    public Bundle getDataBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("key_template_id", this.mTemplateId);
        bundle.putInt("key_template_type", getTemplateType());
        return bundle;
    }

    public int getTemplateType() {
        return 0;
    }

    public CommandTemplate(Bundle bundle) {
        this.mTemplateId = bundle.getString("key_template_id", "");
    }

    public CommandTemplate(String str) {
        this.mTemplateId = str;
    }
}
