package com.samsung.systemui.splugins;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.android.systemui.Dependency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SPluginPolicyInteractor {
    private static final String ALL_SPLUGIN_LOAD_FAILED = "all_splugin_load_failed";
    private static final String BUNDLE_EVENT_TYPE = "EventType";
    private static final String EVENT_TYPE_LOAD_FAILED = "LoadFailed";
    private static final String GOODLOCK_EVENTS_PROVIDER_URI = "content://com.samsung.android.goodlock.splugineventprovider";
    private static final String METHOD_EVENT = "Event";
    private static final String TAG = "SPluginPolicyInteractor";
    private final Context mContext;
    private final PackageManager mPackageManager;
    private final Map<String, Long> mTargetPackages;

    public SPluginPolicyInteractor(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        HashMap hashMap = new HashMap();
        this.mTargetPackages = hashMap;
        hashMap.put("com.samsung.android.keyscafe", 100300000L);
        new Handler((Looper) Dependency.get(Dependency.BG_LOOPER)).post(new Runnable() { // from class: com.samsung.systemui.splugins.SPluginPolicyInteractor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SPluginPolicyInteractor.this.lambda$new$0();
            }
        });
    }

    private long getPackageVersionCode(String str) {
        try {
            return this.mContext.getPackageManager().getPackageInfo(str, 0).getLongVersionCode();
        } catch (PackageManager.NameNotFoundException unused) {
            return 0L;
        }
    }

    private boolean isInvalidVersion(String str) {
        return getPackageVersionCode(str) < this.mTargetPackages.get(str).longValue();
    }

    private boolean isPackageInstalled(String str) {
        try {
            this.mPackageManager.getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    private void sendData(String str, Bundle bundle) {
        try {
            this.mContext.getContentResolver().call(Uri.parse(GOODLOCK_EVENTS_PROVIDER_URI), str, (String) null, bundle);
        } catch (IllegalArgumentException unused) {
        }
    }

    /* renamed from: applyUrgentOSUpgradePolicy, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        Iterator<Map.Entry<String, Long>> it = this.mTargetPackages.entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            if (isPackageInstalled(key)) {
                applyUrgentOSUpgradePolicy(key);
            }
        }
    }

    public synchronized void onPluginLoadFailed(String str) {
        String string = Settings.Secure.getString(this.mContext.getContentResolver(), ALL_SPLUGIN_LOAD_FAILED);
        if (string == null || !string.contains(str)) {
            if (string == null) {
                string = "";
            }
            Settings.Secure.putString(this.mContext.getContentResolver(), ALL_SPLUGIN_LOAD_FAILED, string + str + "|");
        }
        sendEvent();
    }

    public synchronized void onPluginLoaded(String str) {
        String string = Settings.Secure.getString(this.mContext.getContentResolver(), ALL_SPLUGIN_LOAD_FAILED);
        if (string != null && string.contains(str)) {
            Settings.Secure.putString(this.mContext.getContentResolver(), ALL_SPLUGIN_LOAD_FAILED, string.replace(str + "|", ""));
        }
    }

    public void sendEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_EVENT_TYPE, EVENT_TYPE_LOAD_FAILED);
        sendData(METHOD_EVENT, bundle);
    }

    public void applyUrgentOSUpgradePolicy(String str) {
        try {
            if (this.mTargetPackages.containsKey(str)) {
                if (isInvalidVersion(str)) {
                    if (this.mPackageManager.getApplicationEnabledSetting(str) != 2) {
                        this.mPackageManager.setApplicationEnabledSetting(str, 2, 1);
                        onPluginLoadFailed(str);
                    }
                } else {
                    this.mPackageManager.setApplicationEnabledSetting(str, 1, 1);
                    onPluginLoaded(str);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
