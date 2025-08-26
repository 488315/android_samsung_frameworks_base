package com.android.systemui.statusbar.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
final class KeyguardStatusBarViewModel$isVisible$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public KeyguardStatusBarViewModel$isVisible$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj4).booleanValue();
        KeyguardStatusBarViewModel$isVisible$1 keyguardStatusBarViewModel$isVisible$1 = new KeyguardStatusBarViewModel$isVisible$1((Continuation) obj5);
        keyguardStatusBarViewModel$isVisible$1.L$0 = (SceneKey) obj;
        keyguardStatusBarViewModel$isVisible$1.L$1 = (Set) obj2;
        keyguardStatusBarViewModel$isVisible$1.Z$0 = zBooleanValue;
        keyguardStatusBarViewModel$isVisible$1.Z$1 = zBooleanValue2;
        return keyguardStatusBarViewModel$isVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SceneKey sceneKey = (SceneKey) this.L$0;
        Set set = (Set) this.L$1;
        return Boolean.valueOf((!Intrinsics.areEqual(sceneKey, Scenes.Lockscreen) || set.contains(Overlays.NotificationsShade) || set.contains(Overlays.QuickSettingsShade) || set.contains(Overlays.Bouncer) || this.Z$0 || this.Z$1) ? false : true);
    }
}
