package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.util.SettingsHelper;

public final class QSClockBellAlternateCalendarUtil {
    private SettingsHelper.OnChangedCallback mAlternateCalendarSettingCallback;
    public String mCachedAlternateCalendar = ".";
    public final Context mContext;
    public final Handler mHandler;
    private final SettingsHelper mSettingsHelper;
    public Runnable mUpdateNotifyNewClockTime;
    public static final Uri SETTING_KEY_LUNAR_CALENDAR_URI = Settings.System.getUriFor(SettingsHelper.INDEX_LUNAR_CALENDAR);
    public static final Uri SETTING_KEY_HIJRI_CALENDAR_URI = Settings.System.getUriFor(SettingsHelper.INDEX_HIJRI_CALENDAR);

    public final class UpdateHelperByContent extends ContentObserver {
        public UpdateHelperByContent(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("receive that alternate_calendar content has been changed ! "), QSClockBellAlternateCalendarUtil.this.mCachedAlternateCalendar, " will be updated", "QSClockBellTower");
            QSClockBellAlternateCalendarUtil qSClockBellAlternateCalendarUtil = QSClockBellAlternateCalendarUtil.this;
            qSClockBellAlternateCalendarUtil.mCachedAlternateCalendar = ".";
            qSClockBellAlternateCalendarUtil.mHandler.post(qSClockBellAlternateCalendarUtil.mUpdateNotifyNewClockTime);
        }
    }

    public QSClockBellAlternateCalendarUtil(Context context, Handler handler, SettingsHelper settingsHelper) {
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.policy.QSClockBellAlternateCalendarUtil$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Runnable runnable;
                QSClockBellAlternateCalendarUtil qSClockBellAlternateCalendarUtil = QSClockBellAlternateCalendarUtil.this;
                qSClockBellAlternateCalendarUtil.getClass();
                Log.d("QSClockBellTower", "QSClockBellAlternateCalendarUtil receive SettingsHelper callback !");
                Handler handler2 = qSClockBellAlternateCalendarUtil.mHandler;
                if (handler2 == null || (runnable = qSClockBellAlternateCalendarUtil.mUpdateNotifyNewClockTime) == null) {
                    return;
                }
                qSClockBellAlternateCalendarUtil.mCachedAlternateCalendar = ".";
                handler2.post(runnable);
            }
        };
        this.mAlternateCalendarSettingCallback = onChangedCallback;
        Uri[] uriArr = {SETTING_KEY_LUNAR_CALENDAR_URI, SETTING_KEY_HIJRI_CALENDAR_URI};
        this.mContext = context;
        this.mHandler = handler;
        this.mSettingsHelper = settingsHelper;
        if (QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR) {
            settingsHelper.registerCallback(onChangedCallback, uriArr);
            UpdateHelperByContent updateHelperByContent = new UpdateHelperByContent(handler);
            try {
                QSClockBellAlternateCalendarUtil.this.mContext.getContentResolver().registerContentObserver(Uri.parse("content://com.samsung.android.app.clockpack.provider/alternate_calendar"), true, updateHelperByContent);
            } catch (Exception e) {
                Log.e("QSClockBellTower", "Exception is caught in init()", e);
            }
        }
    }

    public final boolean isAlternateCalendarEnabled() {
        return QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR && (this.mSettingsHelper.isLunarCalendarEnabled() || this.mSettingsHelper.isHijriCalendarEnabled() || QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_PERSIAN);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateAlternateCalendar(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "."
            boolean r0 = r0.equals(r8)
            if (r0 != 0) goto L30
            java.lang.String r0 = "android.intent.action.TIME_SET"
            boolean r0 = r0.equals(r8)
            if (r0 != 0) goto L30
            java.lang.String r0 = "android.intent.action.DATE_CHANGED"
            boolean r0 = r0.equals(r8)
            if (r0 != 0) goto L30
            java.lang.String r0 = "android.intent.action.LOCALE_CHANGED"
            boolean r0 = r0.equals(r8)
            if (r0 != 0) goto L30
            java.lang.String r0 = "android.intent.action.TIMEZONE_CHANGED"
            boolean r0 = r0.equals(r8)
            if (r0 != 0) goto L30
            java.lang.String r0 = "android.intent.action.USER_SWITCHED"
            boolean r8 = r0.equals(r8)
            if (r8 == 0) goto L8b
        L30:
            android.content.Context r8 = r7.mContext
            boolean r0 = com.android.systemui.QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_HIJRI
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String[] r3 = new java.lang.String[]{r0}
            r0 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L6e java.lang.UnsupportedOperationException -> L70
            java.lang.String r8 = "content://com.samsung.android.app.clockpack.provider/clock_pack_settings/get_alternate_calendar_complete_text"
            android.net.Uri r2 = android.net.Uri.parse(r8)     // Catch: java.lang.Throwable -> L6e java.lang.UnsupportedOperationException -> L70
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L6e java.lang.UnsupportedOperationException -> L70
            if (r8 == 0) goto L67
            int r1 = r8.getCount()     // Catch: java.lang.Throwable -> L62 java.lang.UnsupportedOperationException -> L65
            if (r1 <= 0) goto L67
            boolean r1 = r8.moveToFirst()     // Catch: java.lang.Throwable -> L62 java.lang.UnsupportedOperationException -> L65
            if (r1 == 0) goto L67
            r1 = 0
            java.lang.String r1 = r8.getString(r1)     // Catch: java.lang.Throwable -> L62 java.lang.UnsupportedOperationException -> L65
            goto L68
        L62:
            r7 = move-exception
            r0 = r8
            goto L8c
        L65:
            r1 = move-exception
            goto L72
        L67:
            r1 = r0
        L68:
            if (r8 == 0) goto L7b
            r8.close()
            goto L7b
        L6e:
            r7 = move-exception
            goto L8c
        L70:
            r1 = move-exception
            r8 = r0
        L72:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L62
            if (r8 == 0) goto L7a
            r8.close()
        L7a:
            r1 = r0
        L7b:
            boolean r8 = android.text.TextUtils.isEmpty(r1)
            if (r8 != 0) goto L89
            java.lang.String r8 = " ("
            java.lang.String r0 = ")"
            java.lang.String r0 = androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0.m(r8, r1, r0)
        L89:
            r7.mCachedAlternateCalendar = r0
        L8b:
            return
        L8c:
            if (r0 == 0) goto L91
            r0.close()
        L91:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.QSClockBellAlternateCalendarUtil.updateAlternateCalendar(java.lang.String):void");
    }
}
