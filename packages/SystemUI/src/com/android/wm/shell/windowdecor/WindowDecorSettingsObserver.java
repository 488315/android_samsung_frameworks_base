package com.android.wm.shell.windowdecor;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class WindowDecorSettingsObserver extends ContentObserver {
    public final Uri mColorThemeColorUri;
    public final DesktopModeWindowDecorViewModel mDecorViewModel;
    public final Uri mFullScreenUri;
    public final Handler mHandler;
    public final Uri mSetupFinishedUri;

    public WindowDecorSettingsObserver(Context context, Handler handler, DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel) {
        super(null);
        this.mHandler = handler;
        this.mDecorViewModel = desktopModeWindowDecorViewModel;
        ContentResolver contentResolver = context.getContentResolver();
        Uri uriFor = Settings.System.getUriFor("wallpapertheme_color");
        this.mColorThemeColorUri = uriFor;
        Uri uriFor2 = Settings.Global.getUriFor("multi_window_menu_in_full_screen");
        this.mFullScreenUri = uriFor2;
        Uri uriFor3 = Settings.Secure.getUriFor(SettingsHelper.INDEX_USER_SETUP_COMPLETE);
        this.mSetupFinishedUri = uriFor3;
        if (CoreRune.MW_CAPTION_THEME) {
            contentResolver.registerContentObserver(uriFor, false, this, -1);
            desktopModeWindowDecorViewModel.updateColorThemeState();
        }
        if (CoreRune.MW_CAPTION_FULL_SCREEN) {
            contentResolver.registerContentObserver(uriFor2, false, this, -1);
            desktopModeWindowDecorViewModel.updateFullscreenHandlerState();
        }
        if (CoreRune.MW_CAPTION_HANDLE) {
            contentResolver.registerContentObserver(uriFor3, false, this, -1);
            desktopModeWindowDecorViewModel.updateSetupCompleteState();
        }
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        super.onChange(z, uri);
        if (CoreRune.MW_CAPTION_THEME && this.mColorThemeColorUri.equals(uri)) {
            final int i = 0;
            this.mHandler.post(new Runnable(this) { // from class: com.android.wm.shell.windowdecor.WindowDecorSettingsObserver$$ExternalSyntheticLambda0
                public final /* synthetic */ WindowDecorSettingsObserver f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i2 = i;
                    WindowDecorSettingsObserver windowDecorSettingsObserver = this.f$0;
                    switch (i2) {
                        case 0:
                            windowDecorSettingsObserver.mDecorViewModel.updateColorThemeState();
                            break;
                        case 1:
                            windowDecorSettingsObserver.mDecorViewModel.updateFullscreenHandlerState();
                            break;
                        default:
                            windowDecorSettingsObserver.mDecorViewModel.updateSetupCompleteState();
                            break;
                    }
                }
            });
        } else if (CoreRune.MW_CAPTION_FULL_SCREEN && this.mFullScreenUri.equals(uri)) {
            final int i2 = 1;
            this.mHandler.post(new Runnable(this) { // from class: com.android.wm.shell.windowdecor.WindowDecorSettingsObserver$$ExternalSyntheticLambda0
                public final /* synthetic */ WindowDecorSettingsObserver f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i22 = i2;
                    WindowDecorSettingsObserver windowDecorSettingsObserver = this.f$0;
                    switch (i22) {
                        case 0:
                            windowDecorSettingsObserver.mDecorViewModel.updateColorThemeState();
                            break;
                        case 1:
                            windowDecorSettingsObserver.mDecorViewModel.updateFullscreenHandlerState();
                            break;
                        default:
                            windowDecorSettingsObserver.mDecorViewModel.updateSetupCompleteState();
                            break;
                    }
                }
            });
        } else if (CoreRune.MW_CAPTION_HANDLE && this.mSetupFinishedUri.equals(uri)) {
            final int i3 = 2;
            this.mHandler.post(new Runnable(this) { // from class: com.android.wm.shell.windowdecor.WindowDecorSettingsObserver$$ExternalSyntheticLambda0
                public final /* synthetic */ WindowDecorSettingsObserver f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i22 = i3;
                    WindowDecorSettingsObserver windowDecorSettingsObserver = this.f$0;
                    switch (i22) {
                        case 0:
                            windowDecorSettingsObserver.mDecorViewModel.updateColorThemeState();
                            break;
                        case 1:
                            windowDecorSettingsObserver.mDecorViewModel.updateFullscreenHandlerState();
                            break;
                        default:
                            windowDecorSettingsObserver.mDecorViewModel.updateSetupCompleteState();
                            break;
                    }
                }
            });
        }
    }
}
