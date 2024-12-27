package com.samsung.android.knox.appconfig;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class GalaxyAIConfiguration {
    public static final String TAG = "GalaxyAIConfiguration";
    public final Context mContext;
    public final Injector mInjector;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public final EnterpriseDeviceManager getEDM() {
            return EnterpriseDeviceManager.getInstance(this.mContext);
        }

        public final EnterpriseKnoxManager getEKM() {
            return EnterpriseKnoxManager.getInstance(this.mContext);
        }
    }

    public GalaxyAIConfiguration(Context context) {
        this(new Injector(context));
    }

    public GalaxyAIConfiguration(Injector injector) {
        this.mInjector = injector;
        this.mContext = injector.mContext;
    }

    public final void action(Bundle bundle, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                setInterpreterState(bundle, i, true);
                for (String str : bundle.keySet()) {
                    if (str.hashCode() == 51082197 && str.equals("key_samsung_interpreter")) {
                        setInterpreterState(bundle.getBundle(str), i, false);
                    }
                    Log.d(TAG, "Unknown restriction key: " + str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void clearAllRestrictions(Bundle bundle, int i) {
        setInterpreterState(bundle, i, true);
    }

    public final void setInterpreterState(Bundle bundle, int i, boolean z) {
        boolean z2 = z || !bundle.getBoolean("grayout", false);
        if (i == 0) {
            EnterpriseDeviceManager.getInstance(this.mInjector.mContext).getApplicationPolicy().setApplicationStateList(new String[]{"com.samsung.android.app.interpreter"}, z2);
        } else {
            EnterpriseKnoxManager.getInstance(this.mInjector.mContext).getKnoxContainerManager(i).getApplicationPolicy().setApplicationStateList(new String[]{"com.samsung.android.app.interpreter"}, z2);
        }
    }
}
