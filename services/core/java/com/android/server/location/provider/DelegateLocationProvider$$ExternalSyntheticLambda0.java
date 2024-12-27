package com.android.server.location.provider;

import com.android.server.location.provider.AbstractLocationProvider;
import java.util.function.UnaryOperator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DelegateLocationProvider$$ExternalSyntheticLambda0 implements UnaryOperator {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DelegateLocationProvider$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                StationaryThrottlingLocationProvider stationaryThrottlingLocationProvider = (StationaryThrottlingLocationProvider) obj2;
                return stationaryThrottlingLocationProvider.mDelegate.mController.setListener(stationaryThrottlingLocationProvider);
            default:
                return (AbstractLocationProvider.State) obj2;
        }
    }
}
