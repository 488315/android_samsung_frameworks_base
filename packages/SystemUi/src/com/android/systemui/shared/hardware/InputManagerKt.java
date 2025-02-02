package com.android.systemui.shared.hardware;

import android.hardware.input.InputManager;
import android.view.InputDevice;
import kotlin.collections.ArraysKt___ArraysKt$asSequence$$inlined$Sequence$4;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class InputManagerKt {
    public static final boolean hasInputDevice(final InputManager inputManager, Function1 function1) {
        int[] inputDeviceIds = inputManager.getInputDeviceIds();
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(inputDeviceIds.length == 0 ? EmptySequence.INSTANCE : new ArraysKt___ArraysKt$asSequence$$inlined$Sequence$4(inputDeviceIds), new Function1() { // from class: com.android.systemui.shared.hardware.InputManagerKt$getInputDeviceSequence$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return inputManager.getInputDevice(((Number) obj).intValue());
            }
        }));
        while (filteringSequence$iterator$1.hasNext()) {
            if (((Boolean) function1.invoke((InputDevice) filteringSequence$iterator$1.next())).booleanValue()) {
                return true;
            }
        }
        return false;
    }
}
