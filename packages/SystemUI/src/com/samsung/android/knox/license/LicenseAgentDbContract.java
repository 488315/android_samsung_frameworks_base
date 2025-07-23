package com.samsung.android.knox.license;

import android.net.Uri;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LicenseAgentDbContract {
    public static final String DEVICE_OWNER_REMOVED = "DeviceOwnerRemoved";
    public static final String DUMP_STARTED = "dumpStarted";
    public static final String ELM_REGISTRATION_INTERNAL = "ELMRegistrationInternal";
    public static final String GET_ACTIVATION_METHOD = "getActivations";
    public static final String GET_ALL_ACTIVATIONS_METHOD = "getAllActivations";
    public static final String IS_EULA_ACCEPTED_ON_DEVICE = "IsEulaAcceptedOnDevice";
    public static final String KLM_DEACTIVATION_INTERNAL = "KLMDeactivationInternal";
    public static final String KLM_REGISTRATION_INTERNAL = "KLMRegistrationInternal";
    public static final String LICENSE_VALIDATION_INTERNAL = "licenseValidationInternal";
    public static final String PACKAGE_NAME_REMOVED = "packageName";
    public static final String PACKAGE_REMOVED_INTERNAL = "packageRemovedInternal";
    public static final String PROFILE_OWNER_REMOVED = "ProfileOwnerRemoved";
    public static final Uri DB_URI = Uri.parse("content://com.samsung.klmsagent.provider/");
    public static final String COLUMN_PACKAGE_NAME = "PACKAGE_NAME";
    public static final String COLUMN_LICENSE_STATUS = "LICENSE_STATUS";
    public static final String COLUMN_LICENSE_KEY = "LICENSE_KEY";
    public static final String COLUMN_SKU = "SKU";
    public static final String COLUMN_PRODUCT_TYPE = "PRODUCT_TYPE";
    public static final String COLUMN_ACTIVATION_TS = "TIME";
    public static final String[] DEFAULT_PROJECTION = {COLUMN_PACKAGE_NAME, COLUMN_LICENSE_STATUS, COLUMN_LICENSE_KEY, COLUMN_SKU, COLUMN_PRODUCT_TYPE, COLUMN_ACTIVATION_TS};

    private LicenseAgentDbContract() {
    }
}
