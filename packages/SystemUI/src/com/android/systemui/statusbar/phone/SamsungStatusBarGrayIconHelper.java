package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SamsungStatusBarGrayIconHelper {
    public final Context context;
    public PhoneStatusBarViewController$onViewAttached$3 grayIconChangedCallback;
    public int homeIndicatorIconColor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SettingsObserver extends ContentObserver {
        public final Context context;
        public final Uri customStatusUri;

        public SettingsObserver(Context context) {
            super(new Handler());
            this.context = context;
            this.customStatusUri = Settings.System.getUriFor("need_dark_statusbar");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            int i;
            SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper = SamsungStatusBarGrayIconHelper.this;
            samsungStatusBarGrayIconHelper.getClass();
            try {
                i = Settings.System.getInt(samsungStatusBarGrayIconHelper.context.getContentResolver(), "need_dark_statusbar");
            } catch (Exception unused) {
                i = 1;
            }
            samsungStatusBarGrayIconHelper.homeIndicatorIconColor = i;
            int i2 = SamsungStatusBarGrayIconHelper.this.homeIndicatorIconColor;
            Log.d("SamsungStatusBarGrayIconHelper", "homeIndicatorIconColor changed to " + i2 + "(0x" + Integer.toHexString(i2) + ")");
        }
    }

    static {
        new Companion(null);
    }

    public SamsungStatusBarGrayIconHelper(Context context) {
        int i;
        this.context = context;
        SettingsObserver settingsObserver = new SettingsObserver(context);
        try {
            i = Settings.System.getInt(context.getContentResolver(), "need_dark_statusbar");
        } catch (Exception unused) {
            i = 1;
        }
        this.homeIndicatorIconColor = i;
        settingsObserver.context.getContentResolver().registerContentObserver(settingsObserver.customStatusUri, false, settingsObserver);
    }
}
