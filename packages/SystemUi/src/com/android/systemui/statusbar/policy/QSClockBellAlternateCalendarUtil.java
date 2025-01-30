package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSClockBellAlternateCalendarUtil {
    public final QSClockBellAlternateCalendarUtil$$ExternalSyntheticLambda0 mAlternateCalendarSettingCallback;
    public String mCachedAlternateCalendar = ".";
    public final Context mContext;
    public final Handler mHandler;
    public final SettingsHelper mSettingsHelper;
    public Runnable mUpdateNotifyNewClockTime;
    public static final Uri SETTING_KEY_LUNAR_CALENDAR_URI = Settings.System.getUriFor("aodlock_support_lunar");
    public static final Uri SETTING_KEY_HIJRI_CALENDAR_URI = Settings.System.getUriFor("aodlock_support_hijri");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UpdateHelperByContent extends ContentObserver {
        public UpdateHelperByContent(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            ExifInterface$$ExternalSyntheticOutline0.m36m(new StringBuilder("receive that alternate_calendar content has been changed ! "), QSClockBellAlternateCalendarUtil.this.mCachedAlternateCalendar, " will be updated", "QSClockBellTower");
            QSClockBellAlternateCalendarUtil qSClockBellAlternateCalendarUtil = QSClockBellAlternateCalendarUtil.this;
            qSClockBellAlternateCalendarUtil.mCachedAlternateCalendar = ".";
            qSClockBellAlternateCalendarUtil.mHandler.post(qSClockBellAlternateCalendarUtil.mUpdateNotifyNewClockTime);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.policy.QSClockBellAlternateCalendarUtil$$ExternalSyntheticLambda0, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    public QSClockBellAlternateCalendarUtil(Context context, Handler handler, SettingsHelper settingsHelper) {
        ?? r0 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.policy.QSClockBellAlternateCalendarUtil$$ExternalSyntheticLambda0
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
        this.mAlternateCalendarSettingCallback = r0;
        Uri[] uriArr = {SETTING_KEY_LUNAR_CALENDAR_URI, SETTING_KEY_HIJRI_CALENDAR_URI};
        this.mContext = context;
        this.mHandler = handler;
        this.mSettingsHelper = settingsHelper;
        if (QpRune.QUICK_STYLE_ALTERNATE_CALENDAR) {
            settingsHelper.registerCallback(r0, uriArr);
            UpdateHelperByContent updateHelperByContent = new UpdateHelperByContent(handler);
            try {
                QSClockBellAlternateCalendarUtil.this.mContext.getContentResolver().registerContentObserver(Uri.parse("content://com.samsung.android.app.clockpack.provider/alternate_calendar"), true, updateHelperByContent);
            } catch (Exception e) {
                Log.e("QSClockBellTower", "Exception is caught in init()", e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x008e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateAlternateCalendar(String str) {
        UnsupportedOperationException e;
        Cursor cursor;
        String str2;
        if (".".equals(str) || "android.intent.action.TIME_SET".equals(str) || "android.intent.action.DATE_CHANGED".equals(str) || "android.intent.action.LOCALE_CHANGED".equals(str) || "android.intent.action.TIMEZONE_CHANGED".equals(str) || "android.intent.action.USER_SWITCHED".equals(str)) {
            Cursor cursor2 = null;
            try {
                cursor = this.mContext.getContentResolver().query(Uri.parse("content://com.samsung.android.app.clockpack.provider/clock_pack_settings/get_alternate_calendar_complete_text"), new String[]{String.valueOf(QpRune.QUICK_STYLE_ALTERNATE_CALENDAR_HIJRI ? 1 : 0)}, null, null, null);
            } catch (UnsupportedOperationException e2) {
                e = e2;
                cursor = null;
            } catch (Throwable th) {
                th = th;
                if (cursor2 != null) {
                }
                throw th;
            }
            if (cursor != null) {
                try {
                    try {
                    } catch (UnsupportedOperationException e3) {
                        e = e3;
                        e.printStackTrace();
                        if (cursor != null) {
                            cursor.close();
                        }
                        str2 = null;
                        this.mCachedAlternateCalendar = TextUtils.isEmpty(str2) ? null : PathParser$$ExternalSyntheticOutline0.m29m(" (", str2, ")");
                    }
                    if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                        str2 = cursor.getString(0);
                        if (cursor != null) {
                            cursor.close();
                        }
                        this.mCachedAlternateCalendar = TextUtils.isEmpty(str2) ? null : PathParser$$ExternalSyntheticOutline0.m29m(" (", str2, ")");
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            }
            str2 = null;
            if (cursor != null) {
            }
            this.mCachedAlternateCalendar = TextUtils.isEmpty(str2) ? null : PathParser$$ExternalSyntheticOutline0.m29m(" (", str2, ")");
        }
    }
}
