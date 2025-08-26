package com.samsung.android.knox.appconfig;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EdmUtils;
import com.samsung.android.knox.appconfig.info.ResultInfo;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ApplicationRestrictionsManager {
    public static final String TAG = "ApplicationRestrictionsManager";
    public static volatile ApplicationRestrictionsManager sApplicationRestrictionsManager;
    public static final List<String> settingsRestrictionsPackageList = Collections.unmodifiableList(new ArrayList<String>() { // from class: com.samsung.android.knox.appconfig.ApplicationRestrictionsManager.1
        {
            add("com.samsung.accessibility");
            add("com.samsung.android.honeyboard");
            add("com.samsung.android.server.wifi.mobilewips.client");
            add("com.samsung.android.server.wifi.mobilewips");
            add("com.sec.android.inputmethod");
            add("com.samsung.android.app.telephonyui");
            add("com.samsung.android.app.smartcapture");
        }
    });
    public final Context mContext;
    public final ContextInfo mContextInfo;
    public IKnoxCustomManager mService;

    private ApplicationRestrictionsManager(Context context, ContextInfo contextInfo) {
        this.mContext = context;
        this.mContextInfo = contextInfo;
    }

    public static ApplicationRestrictionsManager createInstance(Context context, ContextInfo contextInfo) {
        return new ApplicationRestrictionsManager(context, contextInfo);
    }

    public static synchronized ApplicationRestrictionsManager getInstance(Context context) {
        try {
            if (sApplicationRestrictionsManager == null) {
                sApplicationRestrictionsManager = new ApplicationRestrictionsManager(context, new ContextInfo(Process.myUid()));
            }
        } catch (Throwable th) {
            throw th;
        }
        return sApplicationRestrictionsManager;
    }

    public final boolean canUseAppRestrictions() {
        return EdmUtils.getAPILevelForInternal() >= 33;
    }

    public Bundle getApplicationRestrictions(String str, int i) {
        IKnoxCustomManager service = getService();
        if (service == null) {
            return new Bundle();
        }
        if (str == null) {
            str = "";
        }
        try {
            return service.getApplicationRestrictionsInternal(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getGalaxyAIKeyList() {
        return EdmConstants.AI_TOP_LEVEL_KEYS;
    }

    public final IKnoxCustomManager getService() {
        if (this.mService == null) {
            this.mService = IKnoxCustomManager.Stub.asInterface(ServiceManager.getService("knoxcustom"));
        }
        return this.mService;
    }

    public List<String> getSettingsRestrictionsPackageList() {
        return settingsRestrictionsPackageList;
    }

    public boolean isSettingPolicyApplied() {
        Bundle applicationRestrictions = getApplicationRestrictions(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, 0);
        return (applicationRestrictions == null || applicationRestrictions.isEmpty()) ? false : true;
    }

    public final Bundle safeBundle(Bundle bundle) {
        return bundle != null ? bundle : new Bundle();
    }

    public final String safePackageName(String str) {
        return str != null ? str : "";
    }

    public Bundle setApplicationRestrictions(String str, Bundle bundle, int i) {
        if (!canUseAppRestrictions()) {
            return new Bundle();
        }
        IKnoxCustomManager service = getService();
        if (service == null) {
            return new Bundle();
        }
        try {
            ContextInfo contextInfo = this.mContextInfo;
            String packageName = this.mContext.getPackageName();
            if (str == null) {
                str = "";
            }
            return service.setApplicationRestrictionsInternal(contextInfo, packageName, str, safeBundle(bundle), i, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setKeyedAppStatesReport(String str, Bundle bundle, int i) {
        IKnoxCustomManager service = getService();
        if (service == null) {
            return ResultInfo.ERROR_UNKNOWN;
        }
        try {
            ContextInfo contextInfo = this.mContextInfo;
            String packageName = this.mContext.getPackageName();
            if (str == null) {
                str = "";
            }
            service.setKeyedAppStatesReport(contextInfo, packageName, str, safeBundle(bundle), i);
            return ResultInfo.ERROR_NONE;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static synchronized ApplicationRestrictionsManager getInstance(Context context, int i) {
        String packageName = context.getPackageName();
        if (packageName != null && packageName.equals("com.samsung.android.knox.kpecore")) {
            sApplicationRestrictionsManager = new ApplicationRestrictionsManager(context, new ContextInfo(Process.myUid(), false, i));
        } else {
            throw new SecurityException("Can only be called by com.samsung.android.knox.kpecore");
        }
        return sApplicationRestrictionsManager;
    }

    public Bundle setApplicationRestrictions(String str, Bundle bundle, int i, IApplicationRestrictionsResultCallback iApplicationRestrictionsResultCallback) {
        if (!canUseAppRestrictions()) {
            return new Bundle();
        }
        IKnoxCustomManager service = getService();
        if (service == null) {
            return new Bundle();
        }
        try {
            ContextInfo contextInfo = this.mContextInfo;
            String packageName = this.mContext.getPackageName();
            if (str == null) {
                str = "";
            }
            return service.setApplicationRestrictionsInternal(contextInfo, packageName, str, safeBundle(bundle), i, iApplicationRestrictionsResultCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle setApplicationRestrictions(String str, String str2, Bundle bundle, int i) {
        if (!canUseAppRestrictions()) {
            return new Bundle();
        }
        if (!"com.samsung.android.knox.kpecore".equals(this.mContext.getPackageName())) {
            return new Bundle();
        }
        IKnoxCustomManager service = getService();
        if (service == null) {
            return new Bundle();
        }
        try {
            ContextInfo contextInfo = this.mContextInfo;
            if (str == null) {
                str = "";
            }
            return service.setApplicationRestrictionsInternal(contextInfo, str, str2 != null ? str2 : "", safeBundle(bundle), i, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
