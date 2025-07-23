package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.InputManager;
import android.view.InputDevice;
import com.android.systemui.shared.hardware.InputManagerKt$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt$asSequence$$inlined$Sequence$4;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ShortcutHelperStateRepository$findPhysicalKeyboardId$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShortcutHelperStateRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutHelperStateRepository$findPhysicalKeyboardId$2(ShortcutHelperStateRepository shortcutHelperStateRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shortcutHelperStateRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutHelperStateRepository$findPhysicalKeyboardId$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShortcutHelperStateRepository$findPhysicalKeyboardId$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        InputManager inputManager = this.this$0.inputManager;
        int[] inputDeviceIds = inputManager.getInputDeviceIds();
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(inputDeviceIds.length == 0 ? EmptySequence.INSTANCE : new ArraysKt___ArraysKt$asSequence$$inlined$Sequence$4(inputDeviceIds), new InputManagerKt$$ExternalSyntheticLambda0(inputManager)));
        while (true) {
            if (!filteringSequence$iterator$1.hasNext()) {
                obj2 = null;
                break;
            }
            obj2 = filteringSequence$iterator$1.next();
            InputDevice inputDevice = (InputDevice) obj2;
            if (inputDevice.isEnabled() && inputDevice.isFullKeyboard() && !inputDevice.isVirtual()) {
                break;
            }
        }
        InputDevice inputDevice2 = (InputDevice) obj2;
        return new Integer(inputDevice2 != null ? inputDevice2.getId() : -1);
    }
}
