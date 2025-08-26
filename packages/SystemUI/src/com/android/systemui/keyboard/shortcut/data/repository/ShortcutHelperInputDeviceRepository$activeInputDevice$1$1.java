package com.android.systemui.keyboard.shortcut.data.repository;

import com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class ShortcutHelperInputDeviceRepository$activeInputDevice$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ShortcutHelperState $it;
    int label;
    final /* synthetic */ ShortcutHelperInputDeviceRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutHelperInputDeviceRepository$activeInputDevice$1$1(ShortcutHelperInputDeviceRepository shortcutHelperInputDeviceRepository, ShortcutHelperState shortcutHelperState, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shortcutHelperInputDeviceRepository;
        this.$it = shortcutHelperState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutHelperInputDeviceRepository$activeInputDevice$1$1(this.this$0, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShortcutHelperInputDeviceRepository$activeInputDevice$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.this$0.inputManager.getInputDevice(((ShortcutHelperState.Active) this.$it).deviceId);
    }
}
