package com.samsung.android.sdk.routines.v3.internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum ActionMethod {
    UNKNOWN("unknown"),
    GET_CURRENT_PARAM("getCurrentParam"),
    PERFORM_ACTION("performAction"),
    RECOVER_ACTION("recoverAction"),
    GET_LABEL_PARAM("getLabelParam"),
    GET_PREVIEW_IMAGE_FILE_DESCRIPTOR("getPreviewImageFileDescriptor"),
    IS_VALID("isValid"),
    IS_SUPPORT("isSupport"),
    GET_CONFIG_TEMPLATE_CONTENTS("getConfigTemplateContents"),
    GET_ERROR_DIALOG_CONTENTS("getErrorDialogContents"),
    ON_MIGRATE("onMigrate");

    public final String a;

    ActionMethod(String str) {
        this.a = str;
    }
}
