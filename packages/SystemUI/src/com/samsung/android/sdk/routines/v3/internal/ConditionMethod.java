package com.samsung.android.sdk.routines.v3.internal;

/* loaded from: classes4.dex */
public enum ConditionMethod {
    UNKNOWN("unknown"),
    IS_SATISFIED("isSatisfied"),
    ON_ENABLED("onEnabled"),
    ON_DISABLED("onDisabled"),
    GET_LABEL_PARAM("getLabelParam"),
    IS_VALID("isValid"),
    IS_SUPPORT("isSupport"),
    GET_CONFIG_TEMPLATE_CONTENTS("getConfigTemplateContents"),
    GET_ERROR_DIALOG_CONTENTS("getErrorDialogContents"),
    ON_MIGRATE("onMigrate");

    public final String a;

    ConditionMethod(String str) {
        this.a = str;
    }
}
