package com.samsung.android.knox.net;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.IMiscPolicy;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class GlobalProxy {
    public static String TAG = "GlobalProxy";
    public final ContextInfo mContextInfo;
    public IMiscPolicy mService;

    public GlobalProxy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final boolean canUsePacOrAuthConfig() {
        return KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION >= 17;
    }

    public ProxyProperties getGlobalProxy() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getGlobalProxyEnforcingSecurityPermission(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.e(TAG, "RemoteException at method getGlobalProxy");
            return null;
        }
    }

    public final IMiscPolicy getService() {
        if (this.mService == null) {
            this.mService = IMiscPolicy.Stub.asInterface(ServiceManager.getService("misc_policy"));
        }
        return this.mService;
    }

    public final boolean isUsingPacOrAuthConfig(ProxyProperties proxyProperties) {
        return !TextUtils.isEmpty(proxyProperties.getPacFileUrl()) || proxyProperties.isAuthenticationConfigured();
    }

    public int setGlobalProxy(ProxyProperties proxyProperties) {
        if (proxyProperties == null) {
            if (getService() != null) {
                try {
                    return this.mService.clearGlobalProxyEnableEnforcingSecurityPermission(this.mContextInfo);
                } catch (RemoteException unused) {
                    Log.e(TAG, "RemoteException at method setGlobalProxy");
                }
            }
            return 0;
        }
        if (isUsingPacOrAuthConfig(proxyProperties) && !canUsePacOrAuthConfig()) {
            return 0;
        }
        EnterpriseLicenseManager.log(this.mContextInfo, "GlobalProxy.setGlobalProxy");
        if (!TextUtils.isEmpty(proxyProperties.getHostname()) && proxyProperties.getPortNumber() < 0) {
            Log.e(TAG, "inValid proxyPort");
            return 0;
        }
        if (getService() != null) {
            try {
                return this.mService.setGlobalProxyEnforcingSecurityPermission(this.mContextInfo, proxyProperties);
            } catch (RemoteException unused2) {
                Log.e(TAG, "RemoteException at method setGlobalProxy");
            }
        }
        return 0;
    }
}
