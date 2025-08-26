package com.android.systemui.keyboard.shortcut.data.repository;

import com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class ShortcutHelperCustomizationModeRepository$isCustomizationModeEnabled$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public ShortcutHelperCustomizationModeRepository$isCustomizationModeEnabled$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        ShortcutHelperCustomizationModeRepository$isCustomizationModeEnabled$1 shortcutHelperCustomizationModeRepository$isCustomizationModeEnabled$1 = new ShortcutHelperCustomizationModeRepository$isCustomizationModeEnabled$1((Continuation) obj3);
        shortcutHelperCustomizationModeRepository$isCustomizationModeEnabled$1.Z$0 = zBooleanValue;
        shortcutHelperCustomizationModeRepository$isCustomizationModeEnabled$1.L$0 = (ShortcutHelperState) obj2;
        return shortcutHelperCustomizationModeRepository$isCustomizationModeEnabled$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 && (((ShortcutHelperState) this.L$0) instanceof ShortcutHelperState.Active));
    }
}
