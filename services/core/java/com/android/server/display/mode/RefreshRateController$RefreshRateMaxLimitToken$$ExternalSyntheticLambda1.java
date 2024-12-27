package com.android.server.display.mode;

import java.util.function.Function;

public final /* synthetic */
class RefreshRateController$RefreshRateMaxLimitToken$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        RefreshRateToken refreshRateToken = (RefreshRateToken) obj;
        switch (this.$r8$classId) {
        }
        return Integer.valueOf(refreshRateToken.mInfo.mRefreshRate);
    }
}
