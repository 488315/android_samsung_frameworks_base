package com.android.server.biometrics.log;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BiometricContextProvider$$ExternalSyntheticLambda1 implements BiConsumer {
    public final /* synthetic */ BiometricContextProvider f$0;

    public /* synthetic */ BiometricContextProvider$$ExternalSyntheticLambda1(BiometricContextProvider biometricContextProvider) {
        this.f$0 = biometricContextProvider;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        BiometricContextProvider biometricContextProvider = this.f$0;
        OperationContextExt operationContextExt = (OperationContextExt) obj;
        biometricContextProvider.getClass();
        operationContextExt.update(biometricContextProvider, operationContextExt.mAidlContext.isCrypto);
        ((Consumer) obj2).accept(operationContextExt.mAidlContext);
    }
}
