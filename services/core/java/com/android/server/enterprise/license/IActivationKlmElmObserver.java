package com.android.server.enterprise.license;

import com.samsung.android.knox.license.LicenseResult;

public interface IActivationKlmElmObserver {
    default void onUpdateContainerLicenseStatus(String str) {}

    void onUpdateElm(String str, LicenseResult licenseResult);

    void onUpdateKlm(String str, LicenseResult licenseResult);
}
