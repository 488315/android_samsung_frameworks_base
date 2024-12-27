package com.android.server.am;

import android.content.res.Configuration;
import android.util.Slog;
import com.samsung.android.localeoverlaymanager.OverlayChangeObserver;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OverlayChangeObserverImpl extends OverlayChangeObserver {
    public final ActivityManagerService mAm;
    public final String mCallingAttributionTag;
    public final String mCallingPackage;
    public final int mCallingPid;
    public final Configuration mConfiguration;

    public OverlayChangeObserverImpl(Configuration configuration, String str, String str2, int i, int i2, ActivityManagerService activityManagerService) {
        super(0, i);
        this.mConfiguration = configuration;
        this.mCallingPackage = str;
        this.mCallingAttributionTag = str2;
        this.mCallingPid = i2;
        this.mAm = activityManagerService;
    }

    @Override // com.samsung.android.localeoverlaymanager.OverlayChangeObserver
    public final void onChangeCompleted(int i) {
        synchronized (this.overlayLock) {
            try {
                Slog.d("ActivityManager", "onLocaleOverlaysInstalled. token= " + i + " callback done = " + this.callbackCompleted);
                if (!this.callbackCompleted) {
                    this.callbackCompleted = true;
                    this.mAm.overlaysInstallComplete(this.mConfiguration, this.mCallingPackage, this.mCallingAttributionTag, this.mCallingPid, this.mTimeoutRunnable);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
