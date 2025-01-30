package com.android.systemui.media.controls.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.settings.SecureSettings;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardMediaController {
    public final Context context;
    public final Uri lockScreenMediaPlayerUri;
    public final SecureSettings secureSettings;
    public boolean useSplitShade;

    public KeyguardMediaController(KeyguardBypassController keyguardBypassController, SysuiStatusBarStateController sysuiStatusBarStateController, Context context, SecureSettings secureSettings, final Handler handler, ConfigurationController configurationController) {
        this.context = context;
        this.secureSettings = secureSettings;
        ((StatusBarStateControllerImpl) sysuiStatusBarStateController).addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.media.controls.ui.KeyguardMediaController.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                KeyguardMediaController.this.getClass();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                KeyguardMediaController.this.getClass();
            }
        });
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.KeyguardMediaController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                KeyguardMediaController keyguardMediaController = KeyguardMediaController.this;
                boolean shouldUseSplitNotificationShade = LargeScreenUtils.shouldUseSplitNotificationShade(keyguardMediaController.context.getResources());
                if (keyguardMediaController.useSplitShade == shouldUseSplitNotificationShade) {
                    return;
                }
                keyguardMediaController.useSplitShade = shouldUseSplitNotificationShade;
            }
        });
        secureSettings.registerContentObserverForUser("media_controls_lock_screen", new ContentObserver(handler) { // from class: com.android.systemui.media.controls.ui.KeyguardMediaController$settingsObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                if (Intrinsics.areEqual(uri, KeyguardMediaController.this.lockScreenMediaPlayerUri)) {
                    KeyguardMediaController keyguardMediaController = KeyguardMediaController.this;
                    keyguardMediaController.secureSettings.getBoolForUser(-2, "media_controls_lock_screen", true);
                    keyguardMediaController.getClass();
                    KeyguardMediaController.this.getClass();
                }
            }
        }, -1);
        boolean shouldUseSplitNotificationShade = LargeScreenUtils.shouldUseSplitNotificationShade(context.getResources());
        if (this.useSplitShade != shouldUseSplitNotificationShade) {
            this.useSplitShade = shouldUseSplitNotificationShade;
        }
        secureSettings.getBoolForUser(-2, "media_controls_lock_screen", true);
        this.lockScreenMediaPlayerUri = Settings.Secure.getUriFor("media_controls_lock_screen");
    }

    public static /* synthetic */ void getUseSplitShade$annotations() {
    }
}
