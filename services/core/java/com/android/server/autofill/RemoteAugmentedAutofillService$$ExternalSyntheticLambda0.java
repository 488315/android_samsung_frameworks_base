package com.android.server.autofill;

import android.os.IBinder;
import android.service.autofill.augmented.IAugmentedAutofillService;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RemoteAugmentedAutofillService$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IAugmentedAutofillService.Stub.asInterface((IBinder) obj);
    }
}
