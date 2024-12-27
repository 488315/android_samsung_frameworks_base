package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

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
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        DeviceEntryIconViewModel$isInteractive$1 deviceEntryIconViewModel$isInteractive$1 = new DeviceEntryIconViewModel$isInteractive$1((Continuation) obj3);
        deviceEntryIconViewModel$isInteractive$1.L$0 = (DeviceEntryIconView.IconType) obj;
        deviceEntryIconViewModel$isInteractive$1.Z$0 = booleanValue;
        return deviceEntryIconViewModel$isInteractive$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002d, code lost:
    
        if (r2 != false) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r3) {
        /*
            r2 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r2.label
            if (r0 != 0) goto L35
            kotlin.ResultKt.throwOnFailure(r3)
            java.lang.Object r3 = r2.L$0
            com.android.systemui.keyguard.ui.view.DeviceEntryIconView$IconType r3 = (com.android.systemui.keyguard.ui.view.DeviceEntryIconView.IconType) r3
            boolean r2 = r2.Z$0
            int[] r0 = com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$isInteractive$1.WhenMappings.$EnumSwitchMapping$0
            int r3 = r3.ordinal()
            r3 = r0[r3]
            r0 = 0
            r1 = 1
            if (r3 == r1) goto L2d
            r2 = 2
            if (r3 == r2) goto L2b
            r2 = 3
            if (r3 == r2) goto L30
            r2 = 4
            if (r3 != r2) goto L25
            goto L30
        L25:
            kotlin.NoWhenBranchMatchedException r2 = new kotlin.NoWhenBranchMatchedException
            r2.<init>()
            throw r2
        L2b:
            r0 = r1
            goto L30
        L2d:
            if (r2 == 0) goto L30
            goto L2b
        L30:
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r0)
            return r2
        L35:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$isInteractive$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
