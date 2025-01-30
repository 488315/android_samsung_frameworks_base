package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType;
import java.util.HashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$carrierIdIconOverrideExists$1", m277f = "MobileIconInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconInteractorImpl$carrierIdIconOverrideExists$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$carrierIdIconOverrideExists$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation<? super MobileIconInteractorImpl$carrierIdIconOverrideExists$1> continuation) {
        super(4, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        int intValue = ((Number) obj).intValue();
        MobileIconInteractorImpl$carrierIdIconOverrideExists$1 mobileIconInteractorImpl$carrierIdIconOverrideExists$1 = new MobileIconInteractorImpl$carrierIdIconOverrideExists$1(this.this$0, (Continuation) obj4);
        mobileIconInteractorImpl$carrierIdIconOverrideExists$1.I$0 = intValue;
        mobileIconInteractorImpl$carrierIdIconOverrideExists$1.L$0 = (ResolvedNetworkType) obj2;
        mobileIconInteractorImpl$carrierIdIconOverrideExists$1.L$1 = (MobileServiceState) obj3;
        return mobileIconInteractorImpl$carrierIdIconOverrideExists$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x0236, code lost:
    
        if (r1.useGlobal5gIcon(r11) != false) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0064, code lost:
    
        if (r1.useGlobal5gIcon(r11) != false) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ca, code lost:
    
        if ((r11 == 4) != false) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00e4, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r6.get(r3, r11, new java.lang.Object[0]), "PCT") != false) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x011c, code lost:
    
        if (r1.useGlobal5gIcon(r11) == false) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01bf, code lost:
    
        if (r1.useGlobal5gIcon(r11) != false) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01fb, code lost:
    
        if (r1.useGlobal5gIcon(r11) == false) goto L118;
     */
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
        ResolvedNetworkType resolvedNetworkType = (ResolvedNetworkType) this.L$0;
        MobileServiceState mobileServiceState = (MobileServiceState) this.L$1;
        MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
        MobileDataIconResource mobileDataIconResource = mobileIconInteractorImpl.dataIconResource;
        HashMap hashMap = mobileDataIconResource.carrierIconOverrides;
        CarrierInfraMediator.Values values = CarrierInfraMediator.Values.ICON_BRANDING;
        boolean z = false;
        CarrierInfraMediator carrierInfraMediator = mobileDataIconResource.carrierInfraMediator;
        int i = mobileIconInteractorImpl.slotId;
        if (hashMap.containsKey(carrierInfraMediator.get(values, i, new Object[0]))) {
            if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_TMOBILE_FAMILY, i, new Object[0])) {
                String lookupKey = resolvedNetworkType.getLookupKey();
                if (Intrinsics.areEqual(lookupKey, MobileMappings.toDisplayIconKey(999)) ? true : Intrinsics.areEqual(lookupKey, MobileMappings.toDisplayIconKey(5)) ? true : Intrinsics.areEqual(lookupKey, Integer.toString(20))) {
                }
                z = true;
            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_AMX_FAMILY, i, new Object[0])) {
                String lookupKey2 = resolvedNetworkType.getLookupKey();
                if (Intrinsics.areEqual(lookupKey2, Integer.toString(13))) {
                    if (!Intrinsics.areEqual(carrierInfraMediator.get(values, i, new Object[0]), "CDR") && !Intrinsics.areEqual(carrierInfraMediator.get(values, i, new Object[0]), "PCT")) {
                        if (Intrinsics.areEqual(carrierInfraMediator.get(values, i, new Object[0]), "AMX") || Intrinsics.areEqual(carrierInfraMediator.get(values, i, new Object[0]), "TCE")) {
                            int i2 = mobileServiceState.optionalRadioTech;
                            if (!(i2 == 3)) {
                            }
                        }
                    }
                    z = true;
                } else {
                    if (Intrinsics.areEqual(lookupKey2, Integer.toString(15))) {
                    }
                    z = true;
                }
            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_OPEN, i, new Object[0])) {
                String lookupKey3 = resolvedNetworkType.getLookupKey();
                if (!(Intrinsics.areEqual(lookupKey3, MobileMappings.toDisplayIconKey(999)) ? true : Intrinsics.areEqual(lookupKey3, MobileMappings.toDisplayIconKey(5)) ? true : Intrinsics.areEqual(lookupKey3, Integer.toString(20)))) {
                    if (!(Intrinsics.areEqual(lookupKey3, Integer.toString(13)) ? true : Intrinsics.areEqual(lookupKey3, MobileMappings.toDisplayIconKey(1)))) {
                    }
                }
            } else if (Intrinsics.areEqual(carrierInfraMediator.get(values, i, new Object[0]), "ATT")) {
                String lookupKey4 = resolvedNetworkType.getLookupKey();
                if (Intrinsics.areEqual(lookupKey4, MobileMappings.toDisplayIconKey(999)) ? true : Intrinsics.areEqual(lookupKey4, MobileMappings.toDisplayIconKey(5)) ? true : Intrinsics.areEqual(lookupKey4, Integer.toString(20))) {
                }
                z = true;
            } else if (Intrinsics.areEqual(carrierInfraMediator.get(values, i, new Object[0]), "AIO")) {
                String lookupKey5 = resolvedNetworkType.getLookupKey();
                if (Intrinsics.areEqual(lookupKey5, MobileMappings.toDisplayIconKey(999)) ? true : Intrinsics.areEqual(lookupKey5, MobileMappings.toDisplayIconKey(5)) ? true : Intrinsics.areEqual(lookupKey5, Integer.toString(20))) {
                }
            } else {
                if (Intrinsics.areEqual(carrierInfraMediator.get(values, i, new Object[0]), "USC")) {
                    String lookupKey6 = resolvedNetworkType.getLookupKey();
                    if (Intrinsics.areEqual(lookupKey6, MobileMappings.toDisplayIconKey(999)) ? true : Intrinsics.areEqual(lookupKey6, MobileMappings.toDisplayIconKey(5)) ? true : Intrinsics.areEqual(lookupKey6, Integer.toString(20))) {
                    }
                }
                z = true;
            }
        }
        return Boolean.valueOf(z);
    }
}
