package com.android.server.enterprise.license;

import com.samsung.android.knox.license.LicenseResult;

/* loaded from: classes2.dex */
public interface IActivationKlmElmObserver {
    default void onUpdateContainerLicenseStatus(String str) {
    }

    default void onUpdateElm(String str, LicenseResult licenseResult) {
    }

    default void onUpdateKlm(String str, LicenseResult licenseResult) {
    }
}
