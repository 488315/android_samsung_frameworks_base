package com.android.server.ibs;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IntelligentBatterySaverSettingsObserver {
    public Context mContext;
    public Handler mHandler;
    public IntelligentBatterySaverService mIBSService;
    public IntelligentBatterySaverSettingsObserver$$ExternalSyntheticLambda0 mRunnable;
    public SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            String lastPathSegment = uri == null ? null : uri.getLastPathSegment();
            if (lastPathSegment != null) {
                switch (lastPathSegment) {
                    case "ibs_end_minute":
                    case "ibs_start_minute":
                    case "ibs_start_hour":
                    case "ibs_end_hour":
                        IntelligentBatterySaverSettingsObserver
                                intelligentBatterySaverSettingsObserver =
                                        IntelligentBatterySaverSettingsObserver.this;
                        Handler handler = intelligentBatterySaverSettingsObserver.mHandler;
                        if (handler != null) {
                            if (!handler.hasCallbacks(
                                    intelligentBatterySaverSettingsObserver.mRunnable)) {
                                handler.postDelayed(
                                        intelligentBatterySaverSettingsObserver.mRunnable, 1000L);
                                break;
                            } else {
                                Slog.d(
                                        "IntelligentBatterySaverSettingsObserver",
                                        "Had existed callback.");
                                break;
                            }
                        } else {
                            Slog.w(
                                    "IntelligentBatterySaverSettingsObserver",
                                    "Handler is null, cannot post delayed runnable.");
                            break;
                        }
                    case "ibs_switch":
                        IntelligentBatterySaverSettingsObserver.this.updateSwitchSetting();
                        break;
                    default:
                        Slog.d(
                                "IntelligentBatterySaverSettingsObserver",
                                "unknown settings change: ".concat(lastPathSegment));
                        break;
                }
            }
        }
    }

    public final void updateSwitchSetting() {
        int i = Settings.Global.getInt(this.mContext.getContentResolver(), "ibs_switch", 1);
        IntelligentBatterySaverService intelligentBatterySaverService = this.mIBSService;
        if (i == 0) {
            intelligentBatterySaverService.mIBSFastDrainPolicy.reportClearState(128);
        } else {
            intelligentBatterySaverService.mIBSFastDrainPolicy.reportSetState(128);
            updateTimeSetting();
        }
    }

    public final void updateTimeSetting() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        final int i = Settings.Global.getInt(contentResolver, "ibs_start_hour", 23);
        final int i2 = Settings.Global.getInt(contentResolver, "ibs_start_minute", 0);
        final int i3 = Settings.Global.getInt(contentResolver, "ibs_end_hour", 7);
        final int i4 = Settings.Global.getInt(contentResolver, "ibs_end_minute", 0);
        final IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy =
                this.mIBSService.mIBSFastDrainPolicy;
        IntelligentBatterySaverFastDrainPolicy.IntelligentBatterySaverFastDrainHandler
                intelligentBatterySaverFastDrainHandler =
                        intelligentBatterySaverFastDrainPolicy.mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            intelligentBatterySaverFastDrainHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            IntelligentBatterySaverFastDrainPolicy
                                    intelligentBatterySaverFastDrainPolicy2 =
                                            IntelligentBatterySaverFastDrainPolicy.this;
                            int i5 = i;
                            int i6 = i2;
                            int i7 = i3;
                            int i8 = i4;
                            intelligentBatterySaverFastDrainPolicy2.getClass();
                            intelligentBatterySaverFastDrainPolicy2.mStartTime =
                                    IntelligentBatterySaverFastDrainPolicy.getCustomTime(i5, i6);
                            intelligentBatterySaverFastDrainPolicy2.mEndTime =
                                    IntelligentBatterySaverFastDrainPolicy.getCustomTime(i7, i8);
                            intelligentBatterySaverFastDrainPolicy2.initAlarm(false);
                            intelligentBatterySaverFastDrainPolicy2.initAlarm(true);
                            Slog.i(
                                    "IntelligentBatterySaverFastDrainPolicy",
                                    "Set time from "
                                            + intelligentBatterySaverFastDrainPolicy2.mStartTime
                                            + " to "
                                            + intelligentBatterySaverFastDrainPolicy2.mEndTime);
                        }
                    });
        } else {
            Slog.w(
                    "IntelligentBatterySaverFastDrainPolicy",
                    "Handler is null, cannot update time settings.");
        }
    }
}
