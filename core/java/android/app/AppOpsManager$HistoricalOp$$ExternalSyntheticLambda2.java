package android.app;

import android.app.AppOpsManager;
import android.util.LongSparseLongArray;
import java.util.function.Supplier;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsManager$HistoricalOp$$ExternalSyntheticLambda2 implements Supplier {
    public final /* synthetic */ AppOpsManager.HistoricalOp f$0;

    public /* synthetic */ AppOpsManager$HistoricalOp$$ExternalSyntheticLambda2(AppOpsManager.HistoricalOp historicalOp) {
        this.f$0 = historicalOp;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        LongSparseLongArray orCreateAccessDuration;
        orCreateAccessDuration = this.f$0.getOrCreateAccessDuration();
        return orCreateAccessDuration;
    }
}
