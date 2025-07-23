package com.samsung.android.allshare;

import android.service.timezone.TimeZoneProviderService;

/* loaded from: classes6.dex */
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

    ERROR(String str) {
        this.enumString = str;
    }

    public String enumToString() {
        return this.enumString;
    }

    public static ERROR stringToEnum(String str) {
        if (str == null) {
            return FAIL;
        }
        if (str.equals(TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY)) {
            return SUCCESS;
        }
        if (str.equals("OUT_OF_MEMORY")) {
            return OUT_OF_MEMORY;
        }
        if (str.equals("INVALID_ARGUMENT")) {
            return INVALID_ARGUMENT;
        }
        if (str.equals("BAD_RESPONSE")) {
            return BAD_RESPONSE;
        }
        if (str.equals("CONTENT_NOT_AVAILABLE")) {
            return CONTENT_NOT_AVAILABLE;
        }
        if (str.equals("DELETED")) {
            return DELETED;
        }
        if (str.equals("FAIL")) {
            return FAIL;
        }
        if (str.equals("FEATURE_NOT_SUPPORTED")) {
            return FEATURE_NOT_SUPPORTED;
        }
        if (str.equals("FRAMEWORK_NOT_INSTALLED")) {
            return FRAMEWORK_NOT_INSTALLED;
        }
        if (str.equals("INVALID_DEVICE")) {
            return INVALID_DEVICE;
        }
        if (str.equals("INVALID_OBJECT")) {
            return INVALID_OBJECT;
        }
        if (str.equals("INVALID_STATE")) {
            return INVALID_STATE;
        }
        if (str.equals("ITEM_NOT_EXIST")) {
            return ITEM_NOT_EXIST;
        }
        if (str.equals("NETWORK_NOT_AVAILABLE")) {
            return NETWORK_NOT_AVAILABLE;
        }
        if (str.equals("NO_RESPONSE")) {
            return NO_RESPONSE;
        }
        if (str.equals("NOT_SUPPORTED_FRAMEWORK_VERSION")) {
            return NOT_SUPPORTED_FRAMEWORK_VERSION;
        }
        if (str.equals("PERMISSION_NOT_ALLOWED")) {
            return PERMISSION_NOT_ALLOWED;
        }
        if (str.equals("SERVICE_NOT_CONNECTED")) {
            return SERVICE_NOT_CONNECTED;
        }
        if (str.equals("TIME_OUT")) {
            return TIME_OUT;
        }
        return FAIL;
    }
}
