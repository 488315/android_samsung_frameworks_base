package com.android.server.enterprise.ucm;

import android.util.Log;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;

public abstract /* synthetic */ class UniversalCredentialManagerService$$ExternalSyntheticOutline0 {
    public static boolean m(
            ContextInfo contextInfo, CredentialStorage credentialStorage, String str, String str2) {
        Log.i(str, str2);
        UniversalCredentialManagerService.validateContextInfo(contextInfo);
        return UniversalCredentialManagerService.isValidParam(credentialStorage);
    }
}
