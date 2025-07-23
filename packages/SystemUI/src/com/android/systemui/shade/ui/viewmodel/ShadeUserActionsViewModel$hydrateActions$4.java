package com.android.systemui.shade.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.TransitionKey;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.shared.model.TransitionKeys;
import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.builders.MapBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShadeUserActionsViewModel$hydrateActions$4 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public ShadeUserActionsViewModel$hydrateActions$4(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        ShadeUserActionsViewModel$hydrateActions$4 shadeUserActionsViewModel$hydrateActions$4 = new ShadeUserActionsViewModel$hydrateActions$4((Continuation) obj4);
        shadeUserActionsViewModel$hydrateActions$4.L$0 = (ShadeMode) obj;
        shadeUserActionsViewModel$hydrateActions$4.Z$0 = booleanValue;
        shadeUserActionsViewModel$hydrateActions$4.L$1 = (SceneKey) obj3;
        return shadeUserActionsViewModel$hydrateActions$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ShadeMode shadeMode = (ShadeMode) this.L$0;
        boolean z = this.Z$0;
        SceneKey sceneKey = (SceneKey) this.L$1;
        MapBuilder mapBuilder = new MapBuilder();
        if (!z) {
            Swipe.Companion.getClass();
            Swipe swipe = Swipe.Up;
            UserActionResult.Companion companion = UserActionResult.Companion;
            TransitionKeys.INSTANCE.getClass();
            TransitionKey transitionKey = TransitionKeys.ToSplitShade;
            if (!(shadeMode instanceof ShadeMode.Split)) {
                transitionKey = null;
            }
            mapBuilder.put(swipe, UserActionResult.Companion.invoke$default(companion, sceneKey, transitionKey, 4));
        }
        if (shadeMode instanceof ShadeMode.Single) {
            Swipe.Companion.getClass();
            mapBuilder.put(Swipe.Down, UserActionResult.Companion.invoke$default(UserActionResult.Companion, Scenes.QuickSettings, null, 6));
        }
        return mapBuilder.build();
    }
}
