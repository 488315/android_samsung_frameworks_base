package android.app;

import android.util.LongSparseLongArray;
import java.util.function.Supplier;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsManager$HistoricalOp$$ExternalSyntheticLambda1
    implements Supplier {
  public final /* synthetic */ AppOpsManager.HistoricalOp f$0;

  public /* synthetic */ AppOpsManager$HistoricalOp$$ExternalSyntheticLambda1(
      AppOpsManager.HistoricalOp historicalOp) {
    this.f$0 = historicalOp;
  }

  @Override // java.util.function.Supplier
  public final Object get() {
    LongSparseLongArray orCreateRejectCount;
    orCreateRejectCount = this.f$0.getOrCreateRejectCount();
    return orCreateRejectCount;
  }
}
