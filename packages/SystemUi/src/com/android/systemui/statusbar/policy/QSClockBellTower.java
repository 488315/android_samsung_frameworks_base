package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.DateTimePatternGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.QpRune;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoModeCommandReceiver;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.shade.SecPanelExpansionStateNotifier;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public StringBuilder mFirstTimeZoneChangeLogString;
    public final Handler mHandler;
    public StringBuilder mLastTimeZoneChangeLogString;
    public Locale mLocale;
    public QSClockBellSound mQSClockBellSound;
    public final QSClockQuickStarHelper mQSClockQuickStarHelper;
    public String mQuickStarDateStringFormat;
    public String mQuickStarDateStringPattern;
    public final C34203 mSettingListener;
    public final SettingsHelper mSettingsHelper;
    public String mShortenDateStringFormat;
    public String mShortenDateStringPattern;
    public final TimeBroadcastReceiver mTimeBroadcastReceiver;
    public final RunnableC34181 mUpdateNotifyNewClockTime;
    public final HashMap mAudienceList = new HashMap();
    public final Date mCurrentDate = new Date();
    public final Date mCurrentShortenDate = new Date();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TimeAudience {
        String getTicket();

        void notifyTimeChanged(QSClockBellSound qSClockBellSound);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TimeBroadcastReceiver extends BroadcastReceiver {
        public String mTimeZoneString;

        public /* synthetic */ TimeBroadcastReceiver(QSClockBellTower qSClockBellTower, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (QSClockBellTower.this.mHandler == null) {
                Log.e("QSClockBellTower", "onReceive() mHandler is null");
                return;
            }
            String action = intent.getAction();
            StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("onReceive(", action, ") mTimeZoneString:");
            m4m.append(this.mTimeZoneString);
            Log.d("QSClockBellTower", m4m.toString());
            action.getClass();
            action.hashCode();
            switch (action) {
                case "android.intent.action.LOCALE_CHANGED":
                case "android.intent.action.CONFIGURATION_CHANGED":
                    QSClockBellTower.this.mHandler.post(new QSClockBellTower$$ExternalSyntheticLambda0(this, 3));
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
            if (QpRune.QUICK_STYLE_ALTERNATE_CALENDAR) {
                QSClockBellTower.this.mAlternateCalendarUtil.updateAlternateCalendar(action);
            }
            QSClockBellTower qSClockBellTower = QSClockBellTower.this;
            qSClockBellTower.mHandler.post(qSClockBellTower.mUpdateNotifyNewClockTime);
        }

        public final void updateTimeZone(String str) {
            QSClockBellTower.this.mCalendar = Calendar.getInstance(TimeZone.getTimeZone(str));
            TimeZone timeZone = QSClockBellTower.this.mCalendar.getTimeZone();
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("updateTimeZone() newTimezone: ", str, ", defaultTimezone: ");
            m4m.append(calendar.getTimeZone());
            Log.d("QSClockBellTower", m4m.toString());
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
            qSClockBellTower.mQuickStarDateStringFormat = null;
            SimpleDateFormat simpleDateFormat3 = qSClockBellTower.mClockFormatWithSeconds;
            if (simpleDateFormat3 != null) {
                simpleDateFormat3.setTimeZone(timeZone);
            }
            AbstractC0689x6838b71d.m68m("putLastTimeZoneChangeLog(", str, ")", "QSClockBellTower");
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.statusbar.policy.QSClockBellTower$1, java.lang.Runnable] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.systemui.statusbar.policy.QSClockBellTower$3, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    public QSClockBellTower(Context context, QSClockBellAlternateCalendarUtil qSClockBellAlternateCalendarUtil, SlimIndicatorViewMediator slimIndicatorViewMediator, final BroadcastDispatcher broadcastDispatcher, final ScreenLifecycle screenLifecycle, BootAnimationFinishedCache bootAnimationFinishedCache, SettingsHelper settingsHelper) {
        int i = 0;
        this.mTimeBroadcastReceiver = new TimeBroadcastReceiver(this, i);
        ?? r4 = new Runnable() { // from class: com.android.systemui.statusbar.policy.QSClockBellTower.1
            @Override // java.lang.Runnable
            public final void run() {
                long currentTimeMillis = System.currentTimeMillis();
                QSClockBellTower.this.mCalendar.setTimeInMillis(currentTimeMillis);
                QSClockBellTower.this.ringBellOfTower();
                Log.d("QSClockBellTower", "Everyone heard the bell. run(currentTime:" + currentTimeMillis + ", getTime():" + QSClockBellTower.this.mCalendar.getTime() + ", getTimeZone():" + QSClockBellTower.this.mCalendar.getTimeZone() + ")");
            }
        };
        this.mUpdateNotifyNewClockTime = r4;
        ?? r6 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.policy.QSClockBellTower.3
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                QSClockBellTower qSClockBellTower = QSClockBellTower.this;
                qSClockBellTower.mQuickStarDateStringFormat = null;
                qSClockBellTower.ringBellOfTower();
            }
        };
        this.mSettingListener = r6;
        this.mContext = context;
        this.mHandler = (Handler) Dependency.get(Dependency.MAIN_HANDLER);
        this.mQSClockQuickStarHelper = new QSClockQuickStarHelper(slimIndicatorViewMediator, new QSClockBellTower$$ExternalSyntheticLambda0(this, i));
        this.mSettingsHelper = settingsHelper;
        settingsHelper.registerCallback(r6, Settings.Global.getUriFor("quickstar_indicator_clock_date_format"));
        this.mCalendar = Calendar.getInstance(TimeZone.getDefault());
        if (context != null) {
            this.mLocale = context.getResources().getConfiguration().getLocales().get(0);
        }
        this.mAlternateCalendarUtil = qSClockBellAlternateCalendarUtil;
        qSClockBellAlternateCalendarUtil.mUpdateNotifyNewClockTime = r4;
        this.mQSClockBellSound = new QSClockBellSound("00:00", "00:00", "....", "..", false, "00:00:00", false, "..");
        ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.statusbar.policy.QSClockBellTower$$ExternalSyntheticLambda1
            @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
            public final void onBootAnimationFinished() {
                final QSClockBellTower qSClockBellTower = QSClockBellTower.this;
                QSClockBellTower.TimeBroadcastReceiver timeBroadcastReceiver = qSClockBellTower.mTimeBroadcastReceiver;
                timeBroadcastReceiver.getClass();
                Handler handler = (Handler) Dependency.get(Dependency.TIME_TICK_HANDLER);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.TIME_TICK");
                intentFilter.addAction("android.intent.action.TIME_SET");
                intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
                intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
                intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
                intentFilter.addAction("android.intent.action.USER_SWITCHED");
                if (QpRune.QUICK_STYLE_ALTERNATE_CALENDAR) {
                    intentFilter.addAction("android.intent.action.DATE_CHANGED");
                }
                UserHandle userHandle = UserHandle.ALL;
                BroadcastDispatcher broadcastDispatcher2 = broadcastDispatcher;
                broadcastDispatcher2.registerReceiverWithHandler(timeBroadcastReceiver, intentFilter, handler, userHandle);
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("android.intent.action.TIMEZONE_CHANGED");
                broadcastDispatcher2.registerReceiverWithHandler(timeBroadcastReceiver, intentFilter2, handler);
                timeBroadcastReceiver.updateTimeZone(TimeZone.getDefault().getID());
                screenLifecycle.addObserver(new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.policy.QSClockBellTower.2
                    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
                    public final void onScreenTurnedOn() {
                        QSClockBellTower qSClockBellTower2 = QSClockBellTower.this;
                        Handler handler2 = qSClockBellTower2.mHandler;
                        if (handler2 != null) {
                            handler2.post(qSClockBellTower2.mUpdateNotifyNewClockTime);
                        }
                    }
                });
                qSClockBellTower.ringBellOfTower();
                StringBuilder sb = new StringBuilder("First TimeZoneChange");
                sb.append(" (currentTime:");
                sb.append(System.currentTimeMillis());
                qSClockBellTower.mFirstTimeZoneChangeLogString = sb;
                if (qSClockBellTower.mCalendar != null) {
                    sb.append(", Calendar.getTime():");
                    sb.append(qSClockBellTower.mCalendar.getTime());
                    sb.append(", Calendar.getTimeZone():");
                    sb.append(qSClockBellTower.mCalendar.getTimeZone());
                    sb.append(")");
                }
                if (qSClockBellTower.mQSClockBellSound != null) {
                    StringBuilder sb2 = qSClockBellTower.mFirstTimeZoneChangeLogString;
                    sb2.append(" FIRST TIME BELL: ");
                    sb2.append(qSClockBellTower.mQSClockBellSound.toString());
                }
                final QSClockQuickStarHelper qSClockQuickStarHelper = qSClockBellTower.mQSClockQuickStarHelper;
                ((SlimIndicatorViewMediatorImpl) qSClockQuickStarHelper.mSlimIndicatorViewMediator).registerSubscriber("QSClockBellTower", qSClockQuickStarHelper);
                ((SecPanelExpansionStateNotifier) Dependency.get(SecPanelExpansionStateNotifier.class)).registerTicket(new SecPanelExpansionStateNotifier.SecPanelExpansionStateTicket() { // from class: com.android.systemui.statusbar.policy.QSClockQuickStarHelper.1
                    @Override // com.android.systemui.shade.SecPanelExpansionStateNotifier.SecPanelExpansionStateTicket
                    public final String getName() {
                        return "QSClockBellTower";
                    }

                    @Override // com.android.systemui.shade.SecPanelExpansionStateNotifier.SecPanelExpansionStateTicket
                    public final void onSecPanelExpansionStateChanged(int i2, boolean z) {
                        QSClockQuickStarHelper.this.updateSecondsClockHandler();
                    }
                });
                Log.d("QSClockBellTower", "init(" + qSClockBellTower.mQSClockBellSound.toString() + ")");
            }
        });
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
        int i = 2;
        if (string != null) {
            this.mCalendar.setTimeInMillis(Long.parseLong(string));
        } else if (string2 != null && string2.length() == 4) {
            boolean z = false;
            int parseInt = Integer.parseInt(string2.substring(0, 2));
            int parseInt2 = Integer.parseInt(string2.substring(2));
            Context context = this.mContext;
            if (context != null && !DeviceState.isTesting()) {
                z = DateFormat.is24HourFormat(context, KeyguardUpdateMonitor.getCurrentUser());
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
            handler.post(new QSClockBellTower$$ExternalSyntheticLambda0(this, i));
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
        boolean is24HourFormat = (context == null || DeviceState.isTesting()) ? false : DateFormat.is24HourFormat(context, KeyguardUpdateMonitor.getCurrentUser());
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
            handler.post(new QSClockBellTower$$ExternalSyntheticLambda0(this, 1));
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

    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r10v0 com.android.systemui.statusbar.policy.QSClockBellSound, still in use, count: 2, list:
          (r10v0 com.android.systemui.statusbar.policy.QSClockBellSound) from 0x016c: MOVE (r13v3 com.android.systemui.statusbar.policy.QSClockBellSound) = (r10v0 com.android.systemui.statusbar.policy.QSClockBellSound)
          (r10v0 com.android.systemui.statusbar.policy.QSClockBellSound) from 0x01ac: MOVE (r13v5 com.android.systemui.statusbar.policy.QSClockBellSound) = (r10v0 com.android.systemui.statusbar.policy.QSClockBellSound)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
        	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
        	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:447)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    public final void ringBellOfTower(boolean r17) {
        /*
            Method dump skipped, instructions count: 673
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.QSClockBellTower.ringBellOfTower(boolean):void");
    }
}
