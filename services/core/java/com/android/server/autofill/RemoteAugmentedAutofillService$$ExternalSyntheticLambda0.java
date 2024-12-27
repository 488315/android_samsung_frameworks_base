package com.android.server.autofill;

import android.os.IBinder;
import android.service.autofill.augmented.IAugmentedAutofillService;

import java.util.function.Function;

public final /* synthetic */ class RemoteAugmentedAutofillService$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IAugmentedAutofillService.Stub.asInterface((IBinder) obj);
    }
}
