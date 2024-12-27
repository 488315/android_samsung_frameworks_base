package com.samsung.android.server.packagefeature.core;

import com.samsung.android.server.util.CoreLogger;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PackageFeatureGroupRecord$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ PackageFeatureGroupRecord f$0;
    public final /* synthetic */ boolean f$1 = true;
    public final /* synthetic */ Supplier f$2;
    public final /* synthetic */ Consumer f$3;

    public /* synthetic */ PackageFeatureGroupRecord$$ExternalSyntheticLambda3(PackageFeatureGroupRecord packageFeatureGroupRecord, Supplier supplier, Consumer consumer) {
        this.f$0 = packageFeatureGroupRecord;
        this.f$2 = supplier;
        this.f$3 = consumer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PackageFeatureGroupRecord packageFeatureGroupRecord = this.f$0;
        boolean z = this.f$1;
        Supplier supplier = this.f$2;
        Consumer consumer = this.f$3;
        CoreLogger coreLogger = packageFeatureGroupRecord.mLogger;
        if (z) {
            try {
                coreLogger.log(2, "Start " + ((String) supplier.get()), null);
            } catch (Throwable th) {
                coreLogger.log(5, "Failed " + ((String) supplier.get()), th);
                return;
            }
        }
        consumer.accept(Boolean.TRUE);
    }
}
