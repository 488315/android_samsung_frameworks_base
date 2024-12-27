package com.samsung.android.allshare;

import android.service.timezone.TimeZoneProviderService;

/* loaded from: classes3.dex */
public enum ERROR {
    SUCCESS(TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY),
    OUT_OF_MEMORY("OUT_OF_MEMORY"),
    INVALID_ARGUMENT("INVALID_ARGUMENT"),
    INVALID_OBJECT("INVALID_OBJECT"),
    INVALID_STATE("INVALID_STATE"),
    SERVICE_NOT_CONNECTED("SERVICE_NOT_CONNECTED"),
    NO_RESPONSE("NO_RESPONSE"),
    BAD_RESPONSE("BAD_RESPONSE"),
    NETWORK_NOT_AVAILABLE("NETWORK_NOT_AVAILABLE"),
    CONTENT_NOT_AVAILABLE("CONTENT_NOT_AVAILABLE"),
    INVALID_DEVICE("INVALID_DEVICE"),
    FEATURE_NOT_SUPPORTED("FEATURE_NOT_SUPPORTED"),
    PERMISSION_NOT_ALLOWED("PERMISSION_NOT_ALLOWED"),
    TIME_OUT("TIME_OUT"),
    ITEM_NOT_EXIST("ITEM_NOT_EXIST"),
    DELETED("DELETED"),
    FRAMEWORK_NOT_INSTALLED("FRAMEWORK_NOT_INSTALLED"),
    FAIL("FAIL"),
    NOT_SUPPORTED_FRAMEWORK_VERSION("NOT_SUPPORTED_FRAMEWORK_VERSION");

    private final String enumString;

    ERROR(String enumStr) {
        this.enumString = enumStr;
    }

    public String enumToString() {
        return this.enumString;
    }

    public static ERROR stringToEnum(String enumStr) {
        if (enumStr == null) {
            return FAIL;
        }
        if (enumStr.equals(TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY)) {
            return SUCCESS;
        }
        if (enumStr.equals("OUT_OF_MEMORY")) {
            return OUT_OF_MEMORY;
        }
        if (enumStr.equals("INVALID_ARGUMENT")) {
            return INVALID_ARGUMENT;
        }
        if (enumStr.equals("BAD_RESPONSE")) {
            return BAD_RESPONSE;
        }
        if (enumStr.equals("CONTENT_NOT_AVAILABLE")) {
            return CONTENT_NOT_AVAILABLE;
        }
        if (enumStr.equals("DELETED")) {
            return DELETED;
        }
        if (enumStr.equals("FAIL")) {
            return FAIL;
        }
        if (enumStr.equals("FEATURE_NOT_SUPPORTED")) {
            return FEATURE_NOT_SUPPORTED;
        }
        if (enumStr.equals("FRAMEWORK_NOT_INSTALLED")) {
            return FRAMEWORK_NOT_INSTALLED;
        }
        if (enumStr.equals("INVALID_DEVICE")) {
            return INVALID_DEVICE;
        }
        if (enumStr.equals("INVALID_OBJECT")) {
            return INVALID_OBJECT;
        }
        if (enumStr.equals("INVALID_STATE")) {
            return INVALID_STATE;
        }
        if (enumStr.equals("ITEM_NOT_EXIST")) {
            return ITEM_NOT_EXIST;
        }
        if (enumStr.equals("NETWORK_NOT_AVAILABLE")) {
            return NETWORK_NOT_AVAILABLE;
        }
        if (enumStr.equals("NO_RESPONSE")) {
            return NO_RESPONSE;
        }
        if (enumStr.equals("NOT_SUPPORTED_FRAMEWORK_VERSION")) {
            return NOT_SUPPORTED_FRAMEWORK_VERSION;
        }
        if (enumStr.equals("PERMISSION_NOT_ALLOWED")) {
            return PERMISSION_NOT_ALLOWED;
        }
        if (enumStr.equals("SERVICE_NOT_CONNECTED")) {
            return SERVICE_NOT_CONNECTED;
        }
        if (enumStr.equals("TIME_OUT")) {
            return TIME_OUT;
        }
        return FAIL;
    }
}
