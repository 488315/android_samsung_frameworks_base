package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class DeviceEntryIconViewModel$isInteractive$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeviceEntryIconView.IconType.values().length];
            try {
                iArr[DeviceEntryIconView.IconType.LOCK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceEntryIconView.IconType.UNLOCK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeviceEntryIconView.IconType.FINGERPRINT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DeviceEntryIconView.IconType.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DeviceEntryIconViewModel$isInteractive$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        DeviceEntryIconViewModel$isInteractive$1 deviceEntryIconViewModel$isInteractive$1 = new DeviceEntryIconViewModel$isInteractive$1((Continuation) obj3);
        deviceEntryIconViewModel$isInteractive$1.L$0 = (DeviceEntryIconView.IconType) obj;
        deviceEntryIconViewModel$isInteractive$1.Z$0 = zBooleanValue;
        return deviceEntryIconViewModel$isInteractive$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002b  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DeviceEntryIconView.IconType iconType = (DeviceEntryIconView.IconType) this.L$0;
        boolean z = this.Z$0;
        int i = WhenMappings.$EnumSwitchMapping$0[iconType.ordinal()];
        boolean z2 = false;
        if (i != 1) {
            if (i == 2) {
                z2 = true;
            } else if (i != 3 && i != 4) {
                throw new NoWhenBranchMatchedException();
            }
        } else if (z) {
        }
        return Boolean.valueOf(z2);
    }
}
