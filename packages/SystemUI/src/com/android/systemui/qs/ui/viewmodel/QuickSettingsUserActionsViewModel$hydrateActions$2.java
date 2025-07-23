package com.android.systemui.qs.ui.viewmodel;

import com.android.compose.animation.scene.Back;
import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.scene.shared.model.SceneFamilies;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.builders.MapBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QuickSettingsUserActionsViewModel$hydrateActions$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public QuickSettingsUserActionsViewModel$hydrateActions$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        QuickSettingsUserActionsViewModel$hydrateActions$2 quickSettingsUserActionsViewModel$hydrateActions$2 = new QuickSettingsUserActionsViewModel$hydrateActions$2((Continuation) obj3);
        quickSettingsUserActionsViewModel$hydrateActions$2.Z$0 = booleanValue;
        quickSettingsUserActionsViewModel$hydrateActions$2.L$0 = (SceneKey) obj2;
        return quickSettingsUserActionsViewModel$hydrateActions$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        SceneKey sceneKey = (SceneKey) this.L$0;
        MapBuilder mapBuilder = new MapBuilder();
        if (!z) {
            Back back = Back.INSTANCE;
            UserActionResult.Companion companion = UserActionResult.Companion;
            mapBuilder.put(back, UserActionResult.Companion.invoke$default(companion, sceneKey, null, 6));
            Swipe.Companion.getClass();
            mapBuilder.put(Swipe.Up, UserActionResult.Companion.invoke$default(companion, sceneKey, null, 6));
            mapBuilder.put(new Swipe(SwipeDirection.Up, 1, null, Edge.Bottom, null), UserActionResult.Companion.invoke$default(companion, SceneFamilies.Home, null, 6));
        }
        return mapBuilder.build();
    }
}
