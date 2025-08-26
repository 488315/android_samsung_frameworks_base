package com.android.systemui.controls.controller;

import android.service.controls.IControlsSubscription;
import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class StatefulControlSubscriber$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StatefulControlSubscriber f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ StatefulControlSubscriber$$ExternalSyntheticLambda2(StatefulControlSubscriber statefulControlSubscriber, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = statefulControlSubscriber;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                StatefulControlSubscriber statefulControlSubscriber = this.f$0;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onError subscriptionOpen = ", "StatefulControlSubscriber", statefulControlSubscriber.subscriptionOpen);
                if (statefulControlSubscriber.subscriptionOpen) {
                    statefulControlSubscriber.subscriptionOpen = false;
                    Log.e("StatefulControlSubscriber", "onError receive from '" + statefulControlSubscriber.provider.componentName + "': " + ((String) this.f$1));
                }
                break;
            default:
                IControlsSubscription iControlsSubscription = (IControlsSubscription) this.f$1;
                StatefulControlSubscriber statefulControlSubscriber2 = this.f$0;
                statefulControlSubscriber2.subscriptionOpen = true;
                statefulControlSubscriber2.subscription = iControlsSubscription;
                statefulControlSubscriber2.provider.startSubscription(iControlsSubscription, statefulControlSubscriber2.requestLimit);
                break;
        }
        return Unit.INSTANCE;
    }
}
