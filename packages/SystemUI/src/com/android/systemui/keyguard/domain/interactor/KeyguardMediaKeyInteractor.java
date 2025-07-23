package com.android.systemui.keyguard.domain.interactor;

import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.telephony.domain.interactor.TelephonyInteractor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardMediaKeyInteractor extends ExclusiveActivatable {
    public final TelephonyInteractor telephonyInteractor;

    public KeyguardMediaKeyInteractor(TelephonyInteractor telephonyInteractor, AudioRepository audioRepository) {
        this.telephonyInteractor = telephonyInteractor;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L43
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.telephony.domain.interactor.TelephonyInteractor r4 = r4.telephonyInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r4 = r4.isInCall
            com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$2 r5 = new kotlinx.coroutines.flow.FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$2
                static {
                    /*
                        com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$2 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$2
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$2) com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$2.INSTANCE com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$2
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$2.<clinit>():void");
                }

                {
                    /*
                        r0 = this;
                        r0.<init>()
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$2.<init>():void");
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final java.lang.Object emit(java.lang.Object r1, kotlin.coroutines.Continuation r2) {
                    /*
                        r0 = this;
                        java.lang.Boolean r1 = (java.lang.Boolean) r1
                        r1.booleanValue()
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }
            r0.label = r3
            kotlinx.coroutines.flow.StateFlow r4 = r4.$$delegate_0
            java.lang.Object r4 = r4.collect(r5, r0)
            if (r4 != r1) goto L43
            return r1
        L43:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
