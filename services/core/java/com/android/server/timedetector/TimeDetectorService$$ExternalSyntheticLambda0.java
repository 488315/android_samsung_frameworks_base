package com.android.server.timedetector;

import android.app.time.ExternalTimeSuggestion;
import android.app.timedetector.TelephonyTimeSuggestion;
import android.util.Slog;

import java.util.Objects;

public final /* synthetic */ class TimeDetectorService$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TimeDetectorService f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TimeDetectorService$$ExternalSyntheticLambda0(
            TimeDetectorService timeDetectorService,
            TelephonyTimeSuggestion telephonyTimeSuggestion) {
        this.$r8$classId = 1;
        this.f$0 = timeDetectorService;
        this.f$1 = telephonyTimeSuggestion;
    }

    public /* synthetic */ TimeDetectorService$$ExternalSyntheticLambda0(
            TimeDetectorService timeDetectorService, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = timeDetectorService;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TimeDetectorService timeDetectorService = this.f$0;
                ExternalTimeSuggestion externalTimeSuggestion = (ExternalTimeSuggestion) this.f$1;
                TimeDetectorStrategyImpl timeDetectorStrategyImpl =
                        (TimeDetectorStrategyImpl) timeDetectorService.mTimeDetectorStrategy;
                synchronized (timeDetectorStrategyImpl) {
                    Slog.d(
                            "time_detector",
                            "External suggestion received. currentUserConfig="
                                    + timeDetectorStrategyImpl.mCurrentConfigurationInternal
                                    + " suggestion="
                                    + externalTimeSuggestion);
                    Objects.requireNonNull(externalTimeSuggestion);
                    if (timeDetectorStrategyImpl.validateAutoSuggestionTime(
                            externalTimeSuggestion.getUnixEpochTime(), externalTimeSuggestion)) {
                        timeDetectorStrategyImpl.mLastExternalSuggestion.set(
                                externalTimeSuggestion);
                        timeDetectorStrategyImpl.doAutoTimeDetection(
                                "External time suggestion received: suggestion="
                                        + externalTimeSuggestion);
                        return;
                    }
                    return;
                }
            case 1:
                TimeDetectorService timeDetectorService2 = this.f$0;
                TelephonyTimeSuggestion telephonyTimeSuggestion =
                        (TelephonyTimeSuggestion) this.f$1;
                TimeDetectorStrategyImpl timeDetectorStrategyImpl2 =
                        (TimeDetectorStrategyImpl) timeDetectorService2.mTimeDetectorStrategy;
                synchronized (timeDetectorStrategyImpl2) {
                    if (telephonyTimeSuggestion.getUnixEpochTime() == null) {
                        return;
                    }
                    if (timeDetectorStrategyImpl2.validateAutoSuggestionTime(
                            telephonyTimeSuggestion.getUnixEpochTime(), telephonyTimeSuggestion)) {
                        if (timeDetectorStrategyImpl2.storeTelephonySuggestion(
                                telephonyTimeSuggestion)) {
                            timeDetectorStrategyImpl2.doAutoTimeDetection(
                                    "New telephony time suggested. suggestion="
                                            + telephonyTimeSuggestion);
                            return;
                        }
                        return;
                    }
                    return;
                }
            case 2:
                ((TimeDetectorStrategyImpl) this.f$0.mTimeDetectorStrategy)
                        .suggestGnssTime((GnssTimeSuggestion) this.f$1);
                return;
            default:
                ((TimeDetectorStrategyImpl) this.f$0.mTimeDetectorStrategy)
                        .suggestNetworkTime((NetworkTimeSuggestion) this.f$1);
                return;
        }
    }
}
