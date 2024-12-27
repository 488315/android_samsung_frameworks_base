package com.android.server.enterprise.license;

import com.samsung.android.knox.license.LicenseResult;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface IActivationKlmElmObserver {
    default void onUpdateContainerLicenseStatus(String str) {}

    void onUpdateElm(String str, LicenseResult licenseResult);

    void onUpdateKlm(String str, LicenseResult licenseResult);
}
