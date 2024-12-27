package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.DateTimePatternGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoModeCommandReceiver;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.statusbar.policy.QSClockBellTower;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.sec.ims.settings.ImsProfile;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSClockBellTower implements DemoModeCommandReceiver, Dumpable {
    public static final boolean DEBUG = DeviceType.isEngOrUTBinary();
    public static final String NNBSP_UNICODE = String.valueOf((char) 8239);
    public final QSClockBellAlternateCalendarUtil mAlternateCalendarUtil;
    public Calendar mCalendar;
    public SimpleDateFormat mClockFormat;
    public String mClockFormatString;
    public String mClockFormatStringWithSeconds;
    public SimpleDateFormat mClockFormatWithSeconds;
    public SimpleDateFormat mContentDescriptionFormat;
    public final Context mContext;
    public String mDateStringFormat;
    public String mDateStringPattern;
    public boolean mDemoMode;
    public final StringBuilder mFirstTimeZoneChangeLogString;
    public final Handler mHandler;
    public StringBuilder mLastTimeZoneChangeLogString;
    public Locale mLocale;
    public QSClockBellSound mQSClockBellSound;
    public final QSClockQuickStarHelper mQSClockQuickStarHelper;
    public String mShortenDateStringFormat;
    public String mShortenDateStringPattern;
    public final AnonymousClass1 mUpdateNotifyNewClockTime;
    public final HashMap mAudienceList = new HashMap();
    public final Date mCurrentDate = new Date();
    public final Date mCurrentShortenDate = new Date();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface TimeAudience {
        String getTicket();

        void notifyTimeChanged(QSClockBellSound qSClockBellSound);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TimeBroadcastReceiver extends BroadcastReceiver {
        public String mTimeZoneString;

        public /* synthetic */ TimeBroadcastReceiver(QSClockBellTower qSClockBellTower, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            QSClockBellAlternateCalendarUtil qSClockBellAlternateCalendarUtil;
            int i = 2;
            if (QSClockBellTower.this.mHandler == null) {
                Log.e("QSClockBellTower", "onReceive() mHandler is null");
                return;
            }
            String action = intent.getAction();
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("onReceive(", action, ") mTimeZoneString:");
            m.append(this.mTimeZoneString);
            Log.d("QSClockBellTower", m.toString());
            action.getClass();
            switch (action) {
                case "android.intent.action.LOCALE_CHANGED":
                case "android.intent.action.CONFIGURATION_CHANGED":
                    QSClockBellTower.this.mHandler.post(new QSClockBellTower$$ExternalSyntheticLambda2(this, i));
                    break;
                case "android.intent.action.TIMEZONE_CHANGED":
                    final String stringExtra = intent.getStringExtra("time-zone");
                    Log.d("QSClockBellTower", "Quickpanel Indicator Clock TimeZone(" + this.mTimeZoneString + " >> " + stringExtra + ")");
                    this.mTimeZoneString = stringExtra;
                    QSClockBellTower.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.QSClockBellTower$TimeBroadcastReceiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            QSClockBellTower.TimeBroadcastReceiver.this.updateTimeZone(stringExtra);
                        }
                    });
                    break;
            }
            if (QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR && (qSClockBellAlternateCalendarUtil = QSClockBellTower.this.mAlternateCalendarUtil) != null) {
                qSClockBellAlternateCalendarUtil.updateAlternateCalendar(action);
            }
            QSClockBellTower qSClockBellTower = QSClockBellTower.this;
            qSClockBellTower.mHandler.post(qSClockBellTower.mUpdateNotifyNewClockTime);
        }

        public final void updateTimeZone(String str) {
            QSClockBellTower.this.mCalendar = Calendar.getInstance(TimeZone.getTimeZone(str));
            TimeZone timeZone = QSClockBellTower.this.mCalendar.getTimeZone();
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("updateTimeZone() newTimezone: ", str, ", defaultTimezone: ");
            m.append(calendar.getTimeZone());
            Log.d("QSClockBellTower", m.toString());
            SimpleDateFormat simpleDateFormat = QSClockBellTower.this.mClockFormat;
            if (simpleDateFormat != null) {
                simpleDateFormat.setTimeZone(timeZone);
            }
            SimpleDateFormat simpleDateFormat2 = QSClockBellTower.this.mContentDescriptionFormat;
            if (simpleDateFormat2 != null) {
                simpleDateFormat2.setTimeZone(timeZone);
            }
            QSClockBellTower qSClockBellTower = QSClockBellTower.this;
            qSClockBellTower.mDateStringFormat = null;
            qSClockBellTower.mShortenDateStringFormat = null;
            qSClockBellTower.mQSClockQuickStarHelper.mQuickStarDateStringFormat = null;
            SimpleDateFormat simpleDateFormat3 = qSClockBellTower.mClockFormatWithSeconds;
            if (simpleDateFormat3 != null) {
                simpleDateFormat3.setTimeZone(timeZone);
            }
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("putLastTimeZoneChangeLog(", str, ")", "QSClockBellTower");
            QSClockBellTower qSClockBellTower2 = QSClockBellTower.this;
            StringBuilder sb = new StringBuilder("Last TimeZoneChange");
            sb.append(" (");
            sb.append(str);
            sb.append(")  (currentTime:");
            sb.append(System.currentTimeMillis());
            qSClockBellTower2.mLastTimeZoneChangeLogString = sb;
            QSClockBellTower qSClockBellTower3 = QSClockBellTower.this;
            if (qSClockBellTower3.mCalendar != null) {
                StringBuilder sb2 = qSClockBellTower3.mLastTimeZoneChangeLogString;
                sb2.append(", Calendar.getTime():");
                sb2.append(QSClockBellTower.this.mCalendar.getTime());
                sb2.append(", Calendar.getTimeZone():");
                sb2.append(QSClockBellTower.this.mCalendar.getTimeZone());
                sb2.append(")");
            }
            QSClockBellTower qSClockBellTower4 = QSClockBellTower.this;
            if (qSClockBellTower4.mQSClockBellSound != null) {
                StringBuilder sb3 = qSClockBellTower4.mLastTimeZoneChangeLogString;
                sb3.append(" LAST TIME BELL: ");
                sb3.append(QSClockBellTower.this.mQSClockBellSound.toString());
            }
        }

        private TimeBroadcastReceiver() {
            this.mTimeZoneString = TimeZone.getDefault().getID();
        }
    }

    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.systemui.statusbar.policy.QSClockBellTower$1, java.lang.Runnable] */
    public QSClockBellTower(Context context, Handler handler, QSClockBellAlternateCalendarUtil qSClockBellAlternateCalendarUtil, SlimIndicatorViewMediator slimIndicatorViewMediator, BroadcastDispatcher broadcastDispatcher, ScreenLifecycle screenLifecycle, SettingsHelper settingsHelper) {
        TimeBroadcastReceiver timeBroadcastReceiver = new TimeBroadcastReceiver(this, 0);
        ?? r6 = new Runnable() { // from class: com.android.systemui.statusbar.policy.QSClockBellTower.1
            @Override // java.lang.Runnable
            public final void run() {
                long currentTimeMillis = System.currentTimeMillis();
                QSClockBellTower.this.mCalendar.setTimeInMillis(currentTimeMillis);
                QSClockBellTower.this.ringBellOfTower();
                Log.d("QSClockBellTower", "Everyone heard the bell. run(currentTime:" + currentTimeMillis + ", getTime():" + QSClockBellTower.this.mCalendar.getTime() + ", getTimeZone():" + QSClockBellTower.this.mCalendar.getTimeZone() + ")");
            }
        };
        this.mUpdateNotifyNewClockTime = r6;
        this.mContext = context;
        this.mHandler = handler;
        QSClockQuickStarHelper qSClockQuickStarHelper = new QSClockQuickStarHelper(slimIndicatorViewMediator, new QSClockBellTower$$ExternalSyntheticLambda2(this, 0), context, settingsHelper);
        this.mQSClockQuickStarHelper = qSClockQuickStarHelper;
        this.mCalendar = Calendar.getInstance(TimeZone.getDefault());
        if (context != null) {
            this.mLocale = context.getResources().getConfiguration().getLocales().get(0);
        }
        boolean z = QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR;
        if (z) {
            this.mAlternateCalendarUtil = qSClockBellAlternateCalendarUtil;
            qSClockBellAlternateCalendarUtil.mUpdateNotifyNewClockTime = r6;
        } else {
            this.mAlternateCalendarUtil = null;
        }
        this.mQSClockBellSound = new QSClockBellSound("00:00", "00:00", "....", "..", false, "00:00:00", false, "..");
        Handler handler2 = (Handler) Dependency.sDependency.getDependencyInner(Dependency.TIME_TICK_HANDLER);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        if (z) {
            intentFilter.addAction("android.intent.action.DATE_CHANGED");
        }
        broadcastDispatcher.registerReceiverWithHandler(timeBroadcastReceiver, intentFilter, handler2, UserHandle.ALL);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.TIMEZONE_CHANGED");
        broadcastDispatcher.registerReceiverWithHandler(timeBroadcastReceiver, intentFilter2, handler2);
        timeBroadcastReceiver.updateTimeZone(TimeZone.getDefault().getID());
        screenLifecycle.addObserver(new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.policy.QSClockBellTower.2
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOn() {
                QSClockBellTower qSClockBellTower = QSClockBellTower.this;
                Handler handler3 = qSClockBellTower.mHandler;
                if (handler3 != null) {
                    handler3.post(qSClockBellTower.mUpdateNotifyNewClockTime);
                }
            }
        });
        ringBellOfTower();
        StringBuilder sb = new StringBuilder("First TimeZoneChange");
        sb.append(" (currentTime:");
        sb.append(System.currentTimeMillis());
        this.mFirstTimeZoneChangeLogString = sb;
        if (this.mCalendar != null) {
            sb.append(", Calendar.getTime():");
            sb.append(this.mCalendar.getTime());
            sb.append(", Calendar.getTimeZone():");
            sb.append(this.mCalendar.getTimeZone());
            sb.append(")");
        }
        if (this.mQSClockBellSound != null) {
            StringBuilder sb2 = this.mFirstTimeZoneChangeLogString;
            sb2.append(" FIRST TIME BELL: ");
            sb2.append(this.mQSClockBellSound.toString());
        }
        qSClockQuickStarHelper.init();
        Log.d("QSClockBellTower", "init(" + this.mQSClockBellSound.toString() + ")");
    }

    public static String getBasicSmallTime(String str) {
        int indexOf = str.indexOf(61184);
        int indexOf2 = str.indexOf(61185);
        if (indexOf < 0 || indexOf2 <= indexOf) {
            return str;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        spannableStringBuilder.delete(indexOf, indexOf2 + 1);
        if (Character.isWhitespace(spannableStringBuilder.charAt(0))) {
            int i = 0;
            while (spannableStringBuilder.length() > i && Character.isWhitespace(spannableStringBuilder.charAt(i))) {
                i++;
            }
            spannableStringBuilder.delete(0, i);
        }
        return spannableStringBuilder.toString();
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void dispatchDemoCommand(Bundle bundle, String str) {
        String string = bundle.getString("millis");
        String string2 = bundle.getString("hhmm");
        if (string != null) {
            this.mCalendar.setTimeInMillis(Long.parseLong(string));
        } else if (string2 != null && string2.length() == 4) {
            boolean z = false;
            int parseInt = Integer.parseInt(string2.substring(0, 2));
            int parseInt2 = Integer.parseInt(string2.substring(2));
            Context context = this.mContext;
            if (context != null && !DeviceState.isTesting()) {
                z = DateFormat.is24HourFormat(context, ((UserTrackerImpl) ((UserTracker) Dependency.sDependency.getDependencyInner(UserTracker.class))).getUserId());
            }
            if (z) {
                this.mCalendar.set(11, parseInt);
            } else {
                this.mCalendar.set(10, parseInt);
            }
            this.mCalendar.set(12, parseInt2);
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new QSClockBellTower$$ExternalSyntheticLambda2(this, 1));
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        if (this.mFirstTimeZoneChangeLogString != null) {
            printWriter.println("   " + ((Object) this.mFirstTimeZoneChangeLogString));
        }
        if (this.mLastTimeZoneChangeLogString != null) {
            printWriter.println("   " + ((Object) this.mLastTimeZoneChangeLogString));
        }
        printWriter.print("    mAudienceList(");
        for (String str : this.mAudienceList.keySet()) {
            if (!TextUtils.isEmpty(str)) {
                printWriter.print(str + ", ");
            }
        }
        printWriter.println(")\n");
    }

    public final String getBestPatternFormat(boolean z) {
        Locale locale;
        Context context = this.mContext;
        boolean is24HourFormat = (context == null || DeviceState.isTesting()) ? false : DateFormat.is24HourFormat(context, ((UserTrackerImpl) ((UserTracker) Dependency.sDependency.getDependencyInner(UserTracker.class))).getUserId());
        if (z && is24HourFormat && (locale = this.mLocale) != null && locale.getLanguage().equals(new Locale("ko").getLanguage())) {
            return "a H:mm:ss";
        }
        DateTimePatternGenerator dateTimePatternGenerator = DateTimePatternGenerator.getInstance(this.mLocale);
        StringBuilder sb = new StringBuilder();
        sb.append(is24HourFormat ? ImsProfile.TIMER_NAME_H : "h");
        sb.append("m");
        if (z) {
            sb.append("s");
        }
        return dateTimePatternGenerator.getBestPattern(sb.toString());
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void onDemoModeFinished() {
        this.mDemoMode = false;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new QSClockBellTower$$ExternalSyntheticLambda2(this, 0));
        }
    }

    public final void registerAudience(TimeAudience timeAudience) {
        if (timeAudience == null) {
            return;
        }
        Log.d("QSClockBellTower", "registerAudience() ticket:" + timeAudience.getTicket());
        this.mAudienceList.put(timeAudience.getTicket(), timeAudience);
        timeAudience.notifyTimeChanged(this.mQSClockBellSound);
    }

    public final void ringBellOfTower() {
        ringBellOfTower(this.mDemoMode);
    }

    public final void unregisterAudience(TimeAudience timeAudience) {
        if (timeAudience == null) {
            return;
        }
        Log.d("QSClockBellTower", "unregisterAudience() ticket:" + timeAudience.getTicket());
        this.mAudienceList.remove(timeAudience.getTicket());
    }

    public final void ringBellOfTower(boolean z) {
        SimpleDateFormat simpleDateFormat;
        String replaceAll;
        String sb;
        QSClockBellAlternateCalendarUtil qSClockBellAlternateCalendarUtil;
        String str;
        String charSequence;
        SimpleDateFormat simpleDateFormat2;
        if (this.mLocale == null) {
            replaceAll = "12:34";
        } else {
            String bestPatternFormat = getBestPatternFormat(false);
            if (bestPatternFormat.equals(this.mClockFormatString)) {
                simpleDateFormat = this.mClockFormat;
            } else {
                if (DEBUG) {
                    Log.d("QSClockBellTower", "getSmallTime recalculate time format (mClockFormatString:" + this.mClockFormatString + ", format:" + bestPatternFormat + ")");
                }
                this.mContentDescriptionFormat = new SimpleDateFormat(bestPatternFormat);
                SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(QSClockUtils.getBasicClockFormat(bestPatternFormat));
                this.mClockFormat = simpleDateFormat3;
                this.mClockFormatString = getBestPatternFormat(false);
                simpleDateFormat = simpleDateFormat3;
            }
            replaceAll = getBasicSmallTime(simpleDateFormat.format(this.mCalendar.getTime())).replaceAll(NNBSP_UNICODE, "");
        }
        String str2 = replaceAll;
        SimpleDateFormat simpleDateFormat4 = this.mContentDescriptionFormat;
        String format = simpleDateFormat4 != null ? simpleDateFormat4.format(this.mCalendar.getTime()) : "";
        if (TextUtils.isEmpty(this.mDateStringFormat)) {
            this.mDateStringPattern = this.mContext.getString(R.string.system_ui_quick_panel_date_pattern);
            try {
                this.mDateStringFormat = DateFormat.getBestDateTimePattern(Locale.getDefault(), this.mDateStringPattern).trim();
            } catch (ExceptionInInitializerError unused) {
                this.mDateStringFormat = null;
                sb = "";
            }
        }
        this.mCurrentDate.setTime(System.currentTimeMillis());
        StringBuilder sb2 = new StringBuilder(DateFormat.format(this.mDateStringFormat, this.mCurrentDate).toString());
        boolean z2 = QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR;
        if (z2 && (qSClockBellAlternateCalendarUtil = this.mAlternateCalendarUtil) != null && qSClockBellAlternateCalendarUtil.isAlternateCalendarEnabled()) {
            if (z2) {
                if (".".equals(qSClockBellAlternateCalendarUtil.mCachedAlternateCalendar)) {
                    qSClockBellAlternateCalendarUtil.updateAlternateCalendar(".");
                }
                str = qSClockBellAlternateCalendarUtil.mCachedAlternateCalendar;
            } else {
                str = null;
            }
            sb2.append(TextUtils.emptyIfNull(str));
        }
        sb = sb2.toString();
        if (TextUtils.isEmpty(this.mShortenDateStringFormat)) {
            this.mShortenDateStringPattern = this.mContext.getString(R.string.quick_panel_shorten_date_pattern);
            try {
                this.mShortenDateStringFormat = DateFormat.getBestDateTimePattern(Locale.getDefault(), this.mShortenDateStringPattern).trim();
            } catch (ExceptionInInitializerError unused2) {
                this.mShortenDateStringFormat = null;
                charSequence = "";
            }
        }
        this.mCurrentShortenDate.setTime(System.currentTimeMillis());
        charSequence = DateFormat.format(this.mShortenDateStringFormat, this.mCurrentShortenDate).toString();
        QSClockQuickStarHelper qSClockQuickStarHelper = this.mQSClockQuickStarHelper;
        String str3 = "12:34:56";
        if (qSClockQuickStarHelper.shouldShowSecondsClock() && this.mLocale != null) {
            String bestPatternFormat2 = getBestPatternFormat(true);
            if (bestPatternFormat2.equals(this.mClockFormatStringWithSeconds)) {
                simpleDateFormat2 = this.mClockFormatWithSeconds;
            } else {
                Log.d("QSClockBellTower", "getSmallTimeWithSeconds() recalculate time format (mClockFormatString:" + this.mClockFormatStringWithSeconds + ", format:" + bestPatternFormat2 + ")");
                simpleDateFormat2 = new SimpleDateFormat(QSClockUtils.getBasicClockFormat(bestPatternFormat2));
                this.mClockFormatWithSeconds = simpleDateFormat2;
                this.mClockFormatStringWithSeconds = getBestPatternFormat(true);
            }
            this.mCalendar.setTimeInMillis(System.currentTimeMillis());
            str3 = getBasicSmallTime(simpleDateFormat2.format(this.mCalendar.getTime()));
        }
        final QSClockBellSound qSClockBellSound = new QSClockBellSound(str2, format, sb, charSequence, z, str3, qSClockQuickStarHelper.shouldShowSecondsClock(), qSClockQuickStarHelper.getQuickStarDateViewText());
        this.mQSClockBellSound = qSClockBellSound;
        Log.d("QSClockBellTower", "He is ready to ring the bell. (((" + qSClockBellSound.toString() + ")))");
        this.mAudienceList.values().stream().filter(new QSClockBellTower$$ExternalSyntheticLambda0()).forEach(new Consumer() { // from class: com.android.systemui.statusbar.policy.QSClockBellTower$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((QSClockBellTower.TimeAudience) obj).notifyTimeChanged(QSClockBellSound.this);
            }
        });
    }
}
