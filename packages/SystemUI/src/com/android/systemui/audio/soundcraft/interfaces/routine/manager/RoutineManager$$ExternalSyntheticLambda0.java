package com.android.systemui.audio.soundcraft.interfaces.routine.manager;

import com.samsung.android.sdk.routines.automationservice.AutomationServiceProvider;
import com.samsung.android.sdk.routines.automationservice.internal.AutomationServiceImpl;
import com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class RoutineManager$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = RoutineManager.$r8$clinit;
        AutomationServiceProvider.INSTANCE.getClass();
        return new AutomationServiceImpl(new ContentHandlerImpl());
    }
}
