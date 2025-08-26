package com.android.systemui.audio.soundcraft.interfaces.routine.condition;

import android.content.Context;
import com.samsung.android.sdk.routines.v3.internal.ConditionStatusManagerImpl;
import com.samsung.android.sdk.routines.v3.internal.RoutineSdkImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class PlayingAudioConditionHandler$$ExternalSyntheticLambda3 implements Function0 {
    public final /* synthetic */ Context f$0;

    public /* synthetic */ PlayingAudioConditionHandler$$ExternalSyntheticLambda3(Context context) {
        this.f$0 = context;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Context context = this.f$0;
        int i = PlayingAudioConditionHandler.$r8$clinit;
        RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
        if (routineSdkImpl.a == null) {
            routineSdkImpl.a = new ConditionStatusManagerImpl();
        }
        routineSdkImpl.a.getClass();
        ConditionStatusManagerImpl.notifyConditionChanged(context);
        return Unit.INSTANCE;
    }
}
