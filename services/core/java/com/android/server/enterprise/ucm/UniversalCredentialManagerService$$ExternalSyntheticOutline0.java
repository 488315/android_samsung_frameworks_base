package com.android.server.enterprise.ucm;

import android.util.Log;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class UniversalCredentialManagerService$$ExternalSyntheticOutline0 {
    public static boolean m(
            ContextInfo contextInfo, CredentialStorage credentialStorage, String str, String str2) {
        Log.i(str, str2);
        UniversalCredentialManagerService.validateContextInfo(contextInfo);
        return UniversalCredentialManagerService.isValidParam(credentialStorage);
    }
}
