package com.android.server.timezonedetector;

import android.app.timezonedetector.TelephonyTimeZoneSuggestion;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TimeZoneDetectorService$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TimeZoneDetectorService f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TimeZoneDetectorService$$ExternalSyntheticLambda0(
            TimeZoneDetectorService timeZoneDetectorService, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = timeZoneDetectorService;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        switch (this.$r8$classId) {
            case 0:
                TimeZoneDetectorService timeZoneDetectorService = this.f$0;
                TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion =
                        (TelephonyTimeZoneSuggestion) this.f$1;
                TimeZoneDetectorStrategyImpl timeZoneDetectorStrategyImpl =
                        (TimeZoneDetectorStrategyImpl)
                                timeZoneDetectorService.mTimeZoneDetectorStrategy;
                synchronized (timeZoneDetectorStrategyImpl) {
                    ConfigurationInternal configurationInternal =
                            timeZoneDetectorStrategyImpl.mCurrentConfigurationInternal;
                    Objects.requireNonNull(telephonyTimeZoneSuggestion);
                    if (telephonyTimeZoneSuggestion.getZoneId() == null) {
                        i = 0;
                    } else {
                        int i2 = 4;
                        if (telephonyTimeZoneSuggestion.getMatchType() != 5
                                && telephonyTimeZoneSuggestion.getMatchType() != 4) {
                            i2 = 1;
                            if (telephonyTimeZoneSuggestion.getQuality() == 1) {
                                i = 3;
                            } else if (telephonyTimeZoneSuggestion.getQuality() == 2) {
                                i = 2;
                            } else if (telephonyTimeZoneSuggestion.getQuality() != 3) {
                                throw new AssertionError();
                            }
                        }
                        i = i2;
                    }
                    timeZoneDetectorStrategyImpl.mTelephonySuggestionsBySlotIndex.put(
                            Integer.valueOf(telephonyTimeZoneSuggestion.getSlotIndex()),
                            new TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion(
                                    telephonyTimeZoneSuggestion, i));
                    timeZoneDetectorStrategyImpl.doAutoTimeZoneDetection(
                            configurationInternal,
                            "New telephony time zone suggested. suggestion="
                                    + telephonyTimeZoneSuggestion);
                }
                return;
            default:
                ((TimeZoneDetectorStrategyImpl) this.f$0.mTimeZoneDetectorStrategy)
                        .handleLocationAlgorithmEvent((LocationAlgorithmEvent) this.f$1);
                return;
        }
    }
}
