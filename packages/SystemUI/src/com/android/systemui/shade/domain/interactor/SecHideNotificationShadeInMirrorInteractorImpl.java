package com.android.systemui.shade.domain.interactor;

import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.SettingsHelper;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecHideNotificationShadeInMirrorInteractorImpl implements SecHideNotificationShadeInMirrorInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Handler mainHandler;
    public final NotificationShadeWindowController notificationShadeWindowController;
    private final SettingsHelper settingsHelper;
    public final StatusBarWindowController statusBarWindowController;
    public final Uri settingsValue = Settings.Global.getUriFor(SettingsHelper.INDEX_SMART_VIEW_SHOW_NOTIFICATION_ON);
    private final SettingsHelper.OnChangedCallback settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.shade.domain.interactor.SecHideNotificationShadeInMirrorInteractorImpl$settingsListener$1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            if (uri != null && uri.equals(Settings.Global.getUriFor(SettingsHelper.INDEX_SMART_VIEW_SHOW_NOTIFICATION_ON))) {
                int i = SecHideNotificationShadeInMirrorInteractorImpl.$r8$clinit;
                SecHideNotificationShadeInMirrorInteractorImpl secHideNotificationShadeInMirrorInteractorImpl = SecHideNotificationShadeInMirrorInteractorImpl.this;
                secHideNotificationShadeInMirrorInteractorImpl.getClass();
                secHideNotificationShadeInMirrorInteractorImpl.mainHandler.post(new SecHideNotificationShadeInMirrorInteractorImpl$notify$1(secHideNotificationShadeInMirrorInteractorImpl));
            }
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SecHideNotificationShadeInMirrorInteractorImpl(Handler handler, NotificationShadeWindowController notificationShadeWindowController, StatusBarWindowController statusBarWindowController, SettingsHelper settingsHelper) {
        this.mainHandler = handler;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.statusBarWindowController = statusBarWindowController;
        this.settingsHelper = settingsHelper;
    }

    public final void setup() {
        this.settingsHelper.registerCallback(this.settingsListener, this.settingsValue);
        Unit unit = Unit.INSTANCE;
        Log.d("SecHideNotificationShadeInMirrorInteractor", "setup()");
        this.mainHandler.post(new SecHideNotificationShadeInMirrorInteractorImpl$notify$1(this));
    }

    public final void tearDown() {
        this.settingsHelper.unregisterCallback(this.settingsListener);
        Unit unit = Unit.INSTANCE;
        Log.d("SecHideNotificationShadeInMirrorInteractor", "tearDown()");
    }
}
