package com.samsung.android.wallpaper.live.sdk.service.displaymonitor;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService;
import com.samsung.android.wallpaper.live.sdk.utils.SdkCommonUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;
import com.samsung.android.wallpaper.live.sdk.utils.SdkReflectUtils;

/* loaded from: classes4.dex */
public abstract class AbstractAodDisplayStateMonitor extends DisplayStateMonitor {
    public final AnonymousClass1 mObserver;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.samsung.android.wallpaper.live.sdk.service.displaymonitor.AbstractAodDisplayStateMonitor$1] */
    public AbstractAodDisplayStateMonitor(Context context) {
        super(context);
        this.mObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.samsung.android.wallpaper.live.sdk.service.displaymonitor.AbstractAodDisplayStateMonitor.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                synchronized (AbstractAodDisplayStateMonitor.this) {
                    try {
                        int currentWhich = AbstractAodDisplayStateMonitor.this.getCurrentWhich() & 60;
                        if ((currentWhich == 4 || currentWhich == 16) && !AbstractAodDisplayStateMonitor.this.isMyDisplayDisabledByFoldState()) {
                            AbstractAodDisplayStateMonitor abstractAodDisplayStateMonitor = AbstractAodDisplayStateMonitor.this;
                            abstractAodDisplayStateMonitor.onAodShowStateChanged(abstractAodDisplayStateMonitor.isShowingAod());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
    }

    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    public synchronized DisplayState getDisplayState() {
        if (isMyDisplayDisabledByFoldState()) {
            return DisplayState.OFF;
        }
        if (isInteractive()) {
            return DisplayState.ON;
        }
        if (!isShowingAod()) {
            return DisplayState.OFF;
        }
        boolean z = true;
        if (Settings.System.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_AOD_SHOW_LOCKSCREEN_WALLPAPER, 1) == 0) {
            z = false;
        }
        if (z) {
            return DisplayState.AOD_WITH_WALLPAPER;
        }
        return DisplayState.AOD_WITHOUT_WALLPAPER;
    }

    public final boolean isShowingAod() {
        Context context = this.mContext;
        int focusedUserId = SdkCommonUtils.getFocusedUserId(context);
        int iSemGetMyUserId = UserHandle.semGetMyUserId();
        SdkLog.v("SdkCommonUtils", "isShowingAod : " + focusedUserId + ", " + iSemGetMyUserId);
        if (focusedUserId != iSemGetMyUserId) {
            if (Settings.System.semGetIntForUser(context.getContentResolver(), SettingsHelper.INDEX_AOD_SHOW_STATE, 0, -2) != 0) {
                return true;
            }
        } else if (Settings.System.getInt(context.getContentResolver(), SettingsHelper.INDEX_AOD_SHOW_STATE, 0) != 0) {
            return true;
        }
        return false;
    }

    public abstract void onAodShowStateChanged(boolean z);

    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    public final void start(LiveWallpaperService.BaseEngine.AnonymousClass1 anonymousClass1) {
        super.start(anonymousClass1);
        int focusedUserId = SdkCommonUtils.getFocusedUserId(this.mContext);
        int iSemGetMyUserId = UserHandle.semGetMyUserId();
        SdkLog.i("AbstractAodDisplayStateMonitor", "start : " + focusedUserId + ", " + iSemGetMyUserId);
        if (focusedUserId != iSemGetMyUserId) {
            SdkReflectUtils.invoke(this.mContext.getContentResolver(), SdkReflectUtils.getMethod(ContentResolver.class, "registerContentObserverAsUser", Uri.class, Boolean.TYPE, ContentObserver.class, UserHandle.class), Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_STATE), Boolean.FALSE, this.mObserver, UserHandle.semOf(-2));
        } else {
            this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_STATE), false, this.mObserver);
        }
    }

    @Override // com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor
    public final void stop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mObserver);
        super.stop();
    }
}
