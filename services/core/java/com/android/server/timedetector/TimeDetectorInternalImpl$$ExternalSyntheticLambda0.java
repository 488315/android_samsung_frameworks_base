package com.android.server.timedetector;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TimeDetectorInternalImpl$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TimeDetectorInternalImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TimeDetectorInternalImpl$$ExternalSyntheticLambda0(
            TimeDetectorInternalImpl timeDetectorInternalImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = timeDetectorInternalImpl;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TimeDetectorInternalImpl timeDetectorInternalImpl = this.f$0;
                ((TimeDetectorStrategyImpl) timeDetectorInternalImpl.mTimeDetectorStrategy)
                        .suggestNetworkTime((NetworkTimeSuggestion) this.f$1);
                break;
            default:
                TimeDetectorInternalImpl timeDetectorInternalImpl2 = this.f$0;
                ((TimeDetectorStrategyImpl) timeDetectorInternalImpl2.mTimeDetectorStrategy)
                        .suggestGnssTime((GnssTimeSuggestion) this.f$1);
                break;
        }
    }
}
