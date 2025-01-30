package com.samsung.android.knox.appconfig;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmUtils;
import com.samsung.android.knox.appconfig.info.ResultInfo;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ApplicationRestrictionsManager {
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

    public ApplicationRestrictionsManager(Context context) {
        this(context, new ContextInfo(Process.myUid()));
    }

    public static synchronized ApplicationRestrictionsManager getInstance(Context context) {
        ApplicationRestrictionsManager applicationRestrictionsManager;
        synchronized (ApplicationRestrictionsManager.class) {
            if (sApplicationRestrictionsManager == null) {
                sApplicationRestrictionsManager = new ApplicationRestrictionsManager(context);
            }
            applicationRestrictionsManager = sApplicationRestrictionsManager;
        }
        return applicationRestrictionsManager;
    }

    public final Bundle getApplicationRestrictions(String str, int i) {
        if (getService() == null) {
            return new Bundle();
        }
        if (str == null) {
            try {
                str = new String();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mService.getApplicationRestrictionsInternal(str, i);
    }

    public final IKnoxCustomManager getService() {
        if (this.mService == null) {
            this.mService = IKnoxCustomManager.Stub.asInterface(ServiceManager.getService("knoxcustom"));
        }
        return this.mService;
    }

    public final List<String> getSettingsRestrictionsPackageList() {
        return settingsRestrictionsPackageList;
    }

    public final boolean isSettingPolicyApplied() {
        Bundle applicationRestrictions = getApplicationRestrictions(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, 0);
        return (applicationRestrictions == null || applicationRestrictions.isEmpty()) ? false : true;
    }

    public final Bundle setApplicationRestrictions(String str, Bundle bundle, int i) {
        if (EdmUtils.getAPILevelForInternal() < 33) {
            return new Bundle();
        }
        if (getService() == null) {
            return new Bundle();
        }
        if (str == null) {
            try {
                str = new String();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        String str2 = str;
        if (bundle == null) {
            bundle = new Bundle();
        }
        return this.mService.setApplicationRestrictionsInternal(this.mContextInfo, this.mContext.getPackageName(), str2, bundle, i);
    }

    public final int setKeyedAppStatesReport(String str, Bundle bundle, int i) {
        if (getService() == null) {
            return ResultInfo.ERROR_UNKNOWN;
        }
        if (str == null) {
            try {
                str = new String();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        String str2 = str;
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.mService.setKeyedAppStatesReport(this.mContextInfo, this.mContext.getPackageName(), str2, bundle, i);
        return ResultInfo.ERROR_NONE;
    }

    public ApplicationRestrictionsManager(Context context, int i) {
        this(context, new ContextInfo(Process.myUid(), false, i));
    }

    public ApplicationRestrictionsManager(Context context, ContextInfo contextInfo) {
        this.mContext = context;
        this.mContextInfo = contextInfo;
    }

    public static synchronized ApplicationRestrictionsManager getInstance(Context context, int i) {
        ApplicationRestrictionsManager applicationRestrictionsManager;
        synchronized (ApplicationRestrictionsManager.class) {
            String packageName = context.getPackageName();
            if (packageName != null && packageName.equals("com.samsung.android.knox.kpecore")) {
                sApplicationRestrictionsManager = new ApplicationRestrictionsManager(context, i);
                applicationRestrictionsManager = sApplicationRestrictionsManager;
            } else {
                throw new SecurityException("Can only be called by com.samsung.android.knox.kpecore");
            }
        }
        return applicationRestrictionsManager;
    }

    public final Bundle setApplicationRestrictions(String str, String str2, Bundle bundle, int i) {
        if (EdmUtils.getAPILevelForInternal() < 33) {
            return new Bundle();
        }
        if (getService() != null && "com.samsung.android.knox.kpecore".equals(this.mContext.getPackageName())) {
            if (str == null) {
                try {
                    str = this.mContext.getPackageName();
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            String str3 = str;
            if (str2 == null) {
                str2 = new String();
            }
            String str4 = str2;
            if (bundle == null) {
                bundle = new Bundle();
            }
            return this.mService.setApplicationRestrictionsInternal(this.mContextInfo, str3, str4, bundle, i);
        }
        return new Bundle();
    }
}
