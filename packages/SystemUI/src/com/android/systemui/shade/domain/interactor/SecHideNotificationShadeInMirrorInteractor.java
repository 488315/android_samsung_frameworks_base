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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecHideNotificationShadeInMirrorInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Handler mainHandler;
    public final NotificationShadeWindowController notificationShadeWindowController;
    private final SettingsHelper settingsHelper;
    public final StatusBarWindowController statusBarWindowController;
    public final Uri settingsValue = Settings.Global.getUriFor(SettingsHelper.INDEX_SMART_VIEW_SHOW_NOTIFICATION_ON);
    private final SettingsHelper.OnChangedCallback settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.shade.domain.interactor.SecHideNotificationShadeInMirrorInteractor$settingsListener$1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            if (uri != null && uri.equals(Settings.Global.getUriFor(SettingsHelper.INDEX_SMART_VIEW_SHOW_NOTIFICATION_ON))) {
                int i = SecHideNotificationShadeInMirrorInteractor.$r8$clinit;
                SecHideNotificationShadeInMirrorInteractor secHideNotificationShadeInMirrorInteractor = SecHideNotificationShadeInMirrorInteractor.this;
                secHideNotificationShadeInMirrorInteractor.getClass();
                secHideNotificationShadeInMirrorInteractor.mainHandler.post(new SecHideNotificationShadeInMirrorInteractor$notify$1(secHideNotificationShadeInMirrorInteractor));
            }
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SecHideNotificationShadeInMirrorInteractor(Handler handler, NotificationShadeWindowController notificationShadeWindowController, StatusBarWindowController statusBarWindowController, SettingsHelper settingsHelper) {
        this.mainHandler = handler;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.statusBarWindowController = statusBarWindowController;
        this.settingsHelper = settingsHelper;
    }

    public final void setup() {
        this.settingsHelper.registerCallback(this.settingsListener, this.settingsValue);
        Unit unit = Unit.INSTANCE;
        Log.d("SecHideNotificationShadeInMirrorInteractor", "setup()");
        this.mainHandler.post(new SecHideNotificationShadeInMirrorInteractor$notify$1(this));
    }

    public final void tearDown() {
        this.settingsHelper.unregisterCallback(this.settingsListener);
        Unit unit = Unit.INSTANCE;
        Log.d("SecHideNotificationShadeInMirrorInteractor", "tearDown()");
    }
}
