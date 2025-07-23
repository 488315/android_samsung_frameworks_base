package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.statusbar.policy.QSClockBellTower;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class QSClockBellAlternateCalendarUtil {
    private SettingsHelper.OnChangedCallback mAlternateCalendarSettingCallback;
    public String mCachedAlternateCalendar = ".";
    public final Context mContext;
    public final Handler mHandler;
    private final SettingsHelper mSettingsHelper;
    public QSClockBellTower.AnonymousClass1 mUpdateNotifyNewClockTime;
    public static final Uri SETTING_KEY_LUNAR_CALENDAR_URI = Settings.System.getUriFor(SettingsHelper.INDEX_LUNAR_CALENDAR);
    public static final Uri SETTING_KEY_HIJRI_CALENDAR_URI = Settings.System.getUriFor(SettingsHelper.INDEX_HIJRI_CALENDAR);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                QSClockBellTower.AnonymousClass1 anonymousClass1;
                Uri uri2 = QSClockBellAlternateCalendarUtil.SETTING_KEY_LUNAR_CALENDAR_URI;
                QSClockBellAlternateCalendarUtil qSClockBellAlternateCalendarUtil = QSClockBellAlternateCalendarUtil.this;
                Log.d("QSClockBellTower", "QSClockBellAlternateCalendarUtil receive SettingsHelper callback !");
                Handler handler2 = qSClockBellAlternateCalendarUtil.mHandler;
                if (handler2 == null || (anonymousClass1 = qSClockBellAlternateCalendarUtil.mUpdateNotifyNewClockTime) == null) {
                    return;
                }
                qSClockBellAlternateCalendarUtil.mCachedAlternateCalendar = ".";
                handler2.post(anonymousClass1);
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
        if (QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR) {
            return this.mSettingsHelper.isLunarCalendarEnabled() || this.mSettingsHelper.isHijriCalendarEnabled() || QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_PERSIAN;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateAlternateCalendar(java.lang.String r9) {
        /*
            r8 = this;
            java.lang.String r0 = "."
            boolean r0 = r0.equals(r9)
            if (r0 != 0) goto L30
            java.lang.String r0 = "android.intent.action.TIME_SET"
            boolean r0 = r0.equals(r9)
            if (r0 != 0) goto L30
            java.lang.String r0 = "android.intent.action.DATE_CHANGED"
            boolean r0 = r0.equals(r9)
            if (r0 != 0) goto L30
            java.lang.String r0 = "android.intent.action.LOCALE_CHANGED"
            boolean r0 = r0.equals(r9)
            if (r0 != 0) goto L30
            java.lang.String r0 = "android.intent.action.TIMEZONE_CHANGED"
            boolean r0 = r0.equals(r9)
            if (r0 != 0) goto L30
            java.lang.String r0 = "android.intent.action.USER_SWITCHED"
            boolean r9 = r0.equals(r9)
            if (r9 == 0) goto L8d
        L30:
            android.content.Context r9 = r8.mContext
            boolean r0 = com.android.systemui.QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_HIJRI
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String[] r3 = new java.lang.String[]{r0}
            r7 = 0
            android.content.ContentResolver r1 = r9.getContentResolver()     // Catch: java.lang.Throwable -> L6f java.lang.UnsupportedOperationException -> L72
            java.lang.String r9 = "content://com.samsung.android.app.clockpack.provider/clock_pack_settings/get_alternate_calendar_complete_text"
            android.net.Uri r2 = android.net.Uri.parse(r9)     // Catch: java.lang.Throwable -> L6f java.lang.UnsupportedOperationException -> L72
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r9 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L6f java.lang.UnsupportedOperationException -> L72
            if (r9 == 0) goto L68
            int r0 = r9.getCount()     // Catch: java.lang.Throwable -> L62 java.lang.UnsupportedOperationException -> L66
            if (r0 <= 0) goto L68
            boolean r0 = r9.moveToFirst()     // Catch: java.lang.Throwable -> L62 java.lang.UnsupportedOperationException -> L66
            if (r0 == 0) goto L68
            r0 = 0
            java.lang.String r0 = r9.getString(r0)     // Catch: java.lang.Throwable -> L62 java.lang.UnsupportedOperationException -> L66
            goto L69
        L62:
            r0 = move-exception
            r8 = r0
            r7 = r9
            goto L8e
        L66:
            r0 = move-exception
            goto L74
        L68:
            r0 = r7
        L69:
            if (r9 == 0) goto L7d
            r9.close()
            goto L7d
        L6f:
            r0 = move-exception
            r8 = r0
            goto L8e
        L72:
            r0 = move-exception
            r9 = r7
        L74:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L62
            if (r9 == 0) goto L7c
            r9.close()
        L7c:
            r0 = r7
        L7d:
            boolean r9 = android.text.TextUtils.isEmpty(r0)
            if (r9 != 0) goto L8b
            java.lang.String r9 = " ("
            java.lang.String r1 = ")"
            java.lang.String r7 = androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0.m(r9, r0, r1)
        L8b:
            r8.mCachedAlternateCalendar = r7
        L8d:
            return
        L8e:
            if (r7 == 0) goto L93
            r7.close()
        L93:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.QSClockBellAlternateCalendarUtil.updateAlternateCalendar(java.lang.String):void");
    }
}
