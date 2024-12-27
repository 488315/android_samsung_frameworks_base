package com.android.server.translation;

import android.os.IBinder;
import android.service.translation.ITranslationService;

import java.util.function.Function;

public final /* synthetic */ class RemoteTranslationService$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ITranslationService.Stub.asInterface((IBinder) obj);
    }
}
