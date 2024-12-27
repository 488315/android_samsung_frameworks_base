package com.android.server.display.mode;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RefreshRateController$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((RefreshRateToken) obj)
                        instanceof RefreshRateController.RefreshRateMaxLimitToken;
            case 1:
                return ((RefreshRateToken) obj) instanceof RefreshRateController.PassiveModeToken;
            case 2:
                return ((RefreshRateToken) obj)
                        instanceof RefreshRateController.LowRefreshRateToken;
            case 3:
                return ((RefreshRateToken) obj)
                        instanceof RefreshRateController.LowRefreshRateToken;
            case 4:
                return ((RefreshRateToken) obj)
                        instanceof RefreshRateController.RefreshRateMaxLimitToken;
            case 5:
                return ((RefreshRateToken) obj)
                        instanceof RefreshRateController.RefreshRateMinLimitToken;
            default:
                return ((Integer) obj).intValue() >= 60;
        }
    }
}
