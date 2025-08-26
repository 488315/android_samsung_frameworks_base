package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.statusbar.policy.QSClockBellTower;
import com.android.systemui.util.SettingsHelper;

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
                QSClockBellAlternateCalendarUtil qSClockBellAlternateCalendarUtil = this.f$0;
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
    /* JADX WARN: Removed duplicated region for block: B:27:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateAlternateCalendar(String str) {
        Throwable th;
        Cursor cursorQuery;
        String string;
        if (".".equals(str) || "android.intent.action.TIME_SET".equals(str) || "android.intent.action.DATE_CHANGED".equals(str) || "android.intent.action.LOCALE_CHANGED".equals(str) || "android.intent.action.TIMEZONE_CHANGED".equals(str) || "android.intent.action.USER_SWITCHED".equals(str)) {
            Context context = this.mContext;
            ?? r7 = 0;
            try {
                try {
                    cursorQuery = context.getContentResolver().query(Uri.parse("content://com.samsung.android.app.clockpack.provider/clock_pack_settings/get_alternate_calendar_complete_text"), new String[]{String.valueOf(QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_HIJRI ? 1 : 0)}, null, null, null);
                    if (cursorQuery != null) {
                        try {
                            string = (cursorQuery.getCount() <= 0 || !cursorQuery.moveToFirst()) ? null : cursorQuery.getString(0);
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                        } catch (UnsupportedOperationException e) {
                            e = e;
                            e.printStackTrace();
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            string = null;
                            this.mCachedAlternateCalendar = TextUtils.isEmpty(string) ? null : ContentInViewNode$Request$$ExternalSyntheticOutline0.m(" (", string, ")");
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    r7 = context;
                    if (r7 != 0) {
                        throw th;
                    }
                    r7.close();
                    throw th;
                }
            } catch (UnsupportedOperationException e2) {
                e = e2;
                cursorQuery = null;
            } catch (Throwable th3) {
                th = th3;
                if (r7 != 0) {
                }
            }
            this.mCachedAlternateCalendar = TextUtils.isEmpty(string) ? null : ContentInViewNode$Request$$ExternalSyntheticOutline0.m(" (", string, ")");
        }
    }
}
