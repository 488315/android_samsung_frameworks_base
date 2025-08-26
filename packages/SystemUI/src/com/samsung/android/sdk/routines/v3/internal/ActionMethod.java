package com.samsung.android.sdk.routines.v3.internal;

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
