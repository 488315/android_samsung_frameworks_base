package com.samsung.context.sdk.samsunganalytics.internal.terms;

/* loaded from: classes4.dex */
public enum RegisterType {
    /* JADX INFO: Fake field, exist only in values array */
    DELETE_APP_DATA("com.sec.android.diagmonagent.sa.terms.DELETE_APP_DATA"),
    DELETE_SENSITIVE_APP_DATA("com.sec.android.diagmonagent.sa.terms.DELETE_SENSITIVE_APP_DATA"),
    SEND_PREVIOUS_REGISTRATION_INFO("None");

    private final String action;

    RegisterType(String str) {
        this.action = str;
    }

    public final String getAction() {
        return this.action;
    }
}
