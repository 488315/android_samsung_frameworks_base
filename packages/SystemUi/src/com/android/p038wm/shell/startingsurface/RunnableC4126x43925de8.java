package com.android.p038wm.shell.startingsurface;

import android.provider.Settings;
import com.android.p038wm.shell.startingsurface.SplashscreenContentDrawer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC4126x43925de8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RunnableC4126x43925de8(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SplashscreenContentDrawer.SettingObserver settingObserver = (SplashscreenContentDrawer.SettingObserver) this.f$0;
                settingObserver.this$0.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("current_sec_active_themepackage"), false, settingObserver, settingObserver.this$0.mContext.getUserId());
                settingObserver.this$0.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("current_sec_appicon_theme_package"), false, settingObserver, settingObserver.this$0.mContext.getUserId());
                settingObserver.updateSettings(false);
                break;
            case 1:
                ((SplashscreenContentDrawer.SettingObserver) this.f$0).updateSettings(false);
                break;
            default:
                SplashscreenContentDrawer.PreloadIconData preloadIconData = ((SplashscreenContentDrawer.PreLoadIconDataHandler) this.f$0).this$0.mPreloadIcon;
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
