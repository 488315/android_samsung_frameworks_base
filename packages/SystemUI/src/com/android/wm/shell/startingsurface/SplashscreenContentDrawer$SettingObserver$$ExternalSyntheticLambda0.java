package com.android.wm.shell.startingsurface;

import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SplashscreenContentDrawer.SettingObserver settingObserver = (SplashscreenContentDrawer.SettingObserver) obj;
                int userId = settingObserver.this$0.mContext.getUserId();
                settingObserver.this$0.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE), false, settingObserver, userId);
                settingObserver.this$0.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.INDEX_CURRENT_SEC_APPICON_THEME_PACKAGE), false, settingObserver, userId);
                settingObserver.updateSettings(false);
                break;
            case 1:
                int i2 = SplashscreenContentDrawer.SettingObserver.$r8$clinit;
                ((SplashscreenContentDrawer.SettingObserver) obj).updateSettings(false);
                break;
            default:
                SplashscreenContentDrawer.PreloadIconData preloadIconData = ((SplashscreenContentDrawer.PreLoadIconDataHandler) obj).this$0.mPreloadIcon;
                if (preloadIconData.mIsPreloaded) {
                    preloadIconData.mIsPreloaded = false;
                    preloadIconData.mContext = null;
                    preloadIconData.mPreloadIconDrawable = null;
                    break;
                }
                break;
        }
    }
}
