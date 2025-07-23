package com.android.systemui.display.data.repository;

import android.os.Trace;
import com.android.app.displaylib.PerDisplayInstanceProviderWithTeardown;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.display.dagger.SystemUIDisplaySubcomponent;
import kotlin.Result;
import kotlin.Unit;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.ExceptionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisplayComponentInstanceProvider implements PerDisplayInstanceProviderWithTeardown {
    public final SystemUIDisplaySubcomponent.Factory componentFactory;

    public DisplayComponentInstanceProvider(SystemUIDisplaySubcomponent.Factory factory) {
        this.componentFactory = factory;
    }

    @Override // com.android.app.displaylib.PerDisplayInstanceProvider
    public final Object createInstance(int i) {
        Object failure;
        try {
            int i2 = Result.$r8$clinit;
            failure = this.componentFactory.create(i);
        } catch (Throwable th) {
            int i3 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        if (failure instanceof Result.Failure) {
            failure = null;
        }
        return (SystemUIDisplaySubcomponent) failure;
    }

    @Override // com.android.app.displaylib.PerDisplayInstanceProviderWithTeardown
    public final void destroyInstance(Object obj) {
        SystemUIDisplaySubcomponent systemUIDisplaySubcomponent = (SystemUIDisplaySubcomponent) obj;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("Destroying a display component instance");
        }
        try {
            CoroutineScopeKt.cancel(systemUIDisplaySubcomponent.getDisplayCoroutineScope(), ExceptionsKt.CancellationException("Cancelling scope associated to the display.", null));
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
