package com.samsung.android.server.pm.install;

import android.content.pm.ASKSManager;
import android.content.pm.Signature;
import android.util.Slog;
import com.samsung.android.server.pm.install.UnknownSourceAppManager;

/* loaded from: classes2.dex */
public class ASKSHelper extends UnknownSourceAppManager.AbstractASKSHelper {
    @Override // com.samsung.android.server.pm.install.UnknownSourceAppManager.AbstractASKSHelper
    public int checkUnknownSourcePackage(String str, String[] strArr, String[] strArr2, String str2, Signature[] signatureArr, String str3, String str4, String str5, int i, String str6, String str7, int i2) {
        try {
            return ASKSManager.getASKSManager().checkUnknownSourcePackage(str, strArr, strArr2, str2, signatureArr, str3, str4, str5, i, str6, str7, i2);
        } catch (Exception e) {
            Slog.e("UnknownSourceAppManager", "Exception during checkUnknownSourcePackage: " + e);
            return 0;
        }
    }

    @Override // com.samsung.android.server.pm.install.UnknownSourceAppManager.AbstractASKSHelper
    public boolean isTrustedStore(String str, int i) {
        try {
            return ASKSManager.getASKSManager().isTrustedStore(str, i);
        } catch (Exception e) {
            Slog.e("UnknownSourceAppManager", "Exception during isTrustedStore: " + e);
            return false;
        }
    }
}
