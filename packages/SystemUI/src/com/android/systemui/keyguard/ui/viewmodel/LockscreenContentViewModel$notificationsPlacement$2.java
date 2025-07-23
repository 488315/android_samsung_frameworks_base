package com.android.systemui.keyguard.ui.viewmodel;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import com.android.systemui.keyguard.shared.model.ClockSize;
import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class LockscreenContentViewModel$notificationsPlacement$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public LockscreenContentViewModel$notificationsPlacement$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        LockscreenContentViewModel$notificationsPlacement$2 lockscreenContentViewModel$notificationsPlacement$2 = new LockscreenContentViewModel$notificationsPlacement$2((Continuation) obj3);
        lockscreenContentViewModel$notificationsPlacement$2.L$0 = (ShadeMode) obj;
        lockscreenContentViewModel$notificationsPlacement$2.L$1 = (ClockSize) obj2;
        return lockscreenContentViewModel$notificationsPlacement$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ShadeMode shadeMode = (ShadeMode) this.L$0;
        ClockSize clockSize = (ClockSize) this.L$1;
        if (shadeMode instanceof ShadeMode.Split) {
            Alignment.Companion.getClass();
            final BiasAlignment biasAlignment = Alignment.Companion.TopEnd;
            return new Object(biasAlignment) { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$NotificationsPlacement$BesideClock
                public final Alignment alignment;

                {
                    this.alignment = biasAlignment;
                }

                public final boolean equals(Object obj2) {
                    if (this == obj2) {
                        return true;
                    }
                    return (obj2 instanceof LockscreenContentViewModel$NotificationsPlacement$BesideClock) && Intrinsics.areEqual(this.alignment, ((LockscreenContentViewModel$NotificationsPlacement$BesideClock) obj2).alignment);
                }

                public final int hashCode() {
                    return this.alignment.hashCode();
                }

                public final String toString() {
                    return "BesideClock(alignment=" + this.alignment + ")";
                }
            };
        }
        if (clockSize == ClockSize.SMALL) {
            return LockscreenContentViewModel$NotificationsPlacement$BelowClock.INSTANCE;
        }
        Alignment.Companion.getClass();
        final BiasAlignment biasAlignment2 = Alignment.Companion.TopStart;
        return new Object(biasAlignment2) { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$NotificationsPlacement$BesideClock
            public final Alignment alignment;

            {
                this.alignment = biasAlignment2;
            }

            public final boolean equals(Object obj2) {
                if (this == obj2) {
                    return true;
                }
                return (obj2 instanceof LockscreenContentViewModel$NotificationsPlacement$BesideClock) && Intrinsics.areEqual(this.alignment, ((LockscreenContentViewModel$NotificationsPlacement$BesideClock) obj2).alignment);
            }

            public final int hashCode() {
                return this.alignment.hashCode();
            }

            public final String toString() {
                return "BesideClock(alignment=" + this.alignment + ")";
            }
        };
    }
}
