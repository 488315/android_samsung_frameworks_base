package com.android.systemui.audio.soundcraft.interfaces.routine.manager;

import com.samsung.android.sdk.routines.automationservice.AutomationServiceProvider;
import com.samsung.android.sdk.routines.automationservice.internal.AutomationServiceImpl;
import com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class RoutineManager$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = RoutineManager.$r8$clinit;
        AutomationServiceProvider.INSTANCE.getClass();
        return new AutomationServiceImpl(new ContentHandlerImpl());
    }
}
