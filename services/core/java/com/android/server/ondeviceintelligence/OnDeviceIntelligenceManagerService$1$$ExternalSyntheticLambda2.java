package com.android.server.ondeviceintelligence;

import android.R;
import android.os.Bundle;
import android.provider.DeviceConfig;
import android.util.Log;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda2(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                BundleUtil.tryCloseResource((Bundle) obj);
                return;
            case 1:
                BundleUtil.tryCloseResource((Bundle) obj);
                return;
            case 2:
                BundleUtil.tryCloseResource((Bundle) obj);
                return;
            case 3:
                BundleUtil.tryCloseResource((Bundle) obj);
                return;
            case 4:
                BundleUtil.tryCloseResource((Bundle) obj);
                return;
            default:
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService =
                        ((OnDeviceIntelligenceManagerService.AnonymousClass2) obj).this$0;
                onDeviceIntelligenceManagerService.getClass();
                Log.d("OnDeviceIntelligenceManagerService", "registerDeviceConfigChangeListener");
                synchronized (onDeviceIntelligenceManagerService.mLock) {
                    try {
                        str = onDeviceIntelligenceManagerService.mTemporaryConfigNamespace;
                        if (str == null) {
                            str =
                                    onDeviceIntelligenceManagerService
                                            .mContext
                                            .getResources()
                                            .getString(R.string.default_browser);
                        }
                    } finally {
                    }
                }
                if (str.isEmpty()) {
                    Slog.e(
                            "OnDeviceIntelligenceManagerService",
                            "config_defaultOnDeviceIntelligenceDeviceConfigNamespace is empty");
                    return;
                } else {
                    DeviceConfig.addOnPropertiesChangedListener(
                            str,
                            onDeviceIntelligenceManagerService.mConfigExecutor,
                            onDeviceIntelligenceManagerService.mOnPropertiesChangedListener);
                    return;
                }
        }
    }
}
