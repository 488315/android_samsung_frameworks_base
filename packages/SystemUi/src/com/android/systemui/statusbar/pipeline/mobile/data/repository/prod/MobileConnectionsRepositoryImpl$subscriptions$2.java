package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimType;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$subscriptions$2", m277f = "MobileConnectionsRepositoryImpl.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileConnectionsRepositoryImpl$subscriptions$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryImpl$subscriptions$2(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, Continuation<? super MobileConnectionsRepositoryImpl$subscriptions$2> continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionsRepositoryImpl$subscriptions$2 mobileConnectionsRepositoryImpl$subscriptions$2 = new MobileConnectionsRepositoryImpl$subscriptions$2(this.this$0, continuation);
        mobileConnectionsRepositoryImpl$subscriptions$2.L$0 = obj;
        return mobileConnectionsRepositoryImpl$subscriptions$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryImpl$subscriptions$2) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:81:0x012f  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SimCardInfoUtil simCardInfoUtil;
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
        Map map = mobileConnectionsRepositoryImpl.subIdRepositoryCache;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = ((LinkedHashMap) map).entrySet().iterator();
        while (true) {
            boolean z2 = false;
            if (!it.hasNext()) {
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            int intValue = ((Number) entry.getKey()).intValue();
            int i = ((FullMobileConnectionRepository) entry.getValue()).slotId;
            Iterator it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                SubscriptionModel subscriptionModel = (SubscriptionModel) it2.next();
                if (subscriptionModel.subscriptionId == intValue && subscriptionModel.simSlotId == i) {
                    z2 = true;
                    break;
                }
            }
            if (z2) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        mobileConnectionsRepositoryImpl.subIdRepositoryCache = new LinkedHashMap(linkedHashMap);
        Iterator it3 = list.iterator();
        boolean z3 = false;
        while (true) {
            boolean hasNext = it3.hasNext();
            simCardInfoUtil = mobileConnectionsRepositoryImpl.simCardInfoUtil;
            if (!hasNext) {
                break;
            }
            if (SubscriptionManager.getSlotIndex(((SubscriptionModel) it3.next()).subscriptionId) == 1 && simCardInfoUtil.isSimSettingOn(1)) {
                z3 = true;
            }
        }
        CarrierInfraMediator carrierInfraMediator = mobileConnectionsRepositoryImpl.carrierInfraMediator;
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA, 0, new Object[0]) && !carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_OPEN, 0, new Object[0])) {
            if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                Iterator it4 = list.iterator();
                boolean z4 = false;
                while (it4.hasNext()) {
                    int slotIndex = SubscriptionManager.getSlotIndex(((SubscriptionModel) it4.next()).subscriptionId);
                    if (slotIndex != -1 && TextUtils.equals((String) carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING_FROM_CARRIER_FEATURE, slotIndex, new Object[0]), (String) carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING_FROM_CSC_FEATURE, slotIndex, new Object[0]))) {
                        z4 = true;
                    }
                }
                if (!z4) {
                    Iterator it5 = list.iterator();
                    z = false;
                    while (it5.hasNext()) {
                        int slotIndex2 = SubscriptionManager.getSlotIndex(((SubscriptionModel) it5.next()).subscriptionId);
                        if (slotIndex2 != -1 && Intrinsics.areEqual(carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING_FROM_CARRIER_FEATURE, slotIndex2, new Object[0]), "VZW")) {
                            z = true;
                        }
                    }
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("SimMobility: shouldUseVZWIconPolicy - ", z, "MobileConnectionsRepository");
                    CarrierInfraMediator.Values values = CarrierInfraMediator.Values.OVERRIDE_ICON_BRANDING;
                    Object[] objArr = new Object[1];
                    objArr[0] = z ? "VZW" : "";
                    carrierInfraMediator.set(values, objArr);
                }
                z = false;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("SimMobility: shouldUseVZWIconPolicy - ", z, "MobileConnectionsRepository");
                CarrierInfraMediator.Values values2 = CarrierInfraMediator.Values.OVERRIDE_ICON_BRANDING;
                Object[] objArr2 = new Object[1];
                objArr2[0] = z ? "VZW" : "";
                carrierInfraMediator.set(values2, objArr2);
            } else {
                if (simCardInfoUtil.getSimCardInfo(((SubscriptionModel) list.get(0)).subscriptionId) == SimType.VZW) {
                    z = true;
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("SimMobility: shouldUseVZWIconPolicy - ", z, "MobileConnectionsRepository");
                    CarrierInfraMediator.Values values22 = CarrierInfraMediator.Values.OVERRIDE_ICON_BRANDING;
                    Object[] objArr22 = new Object[1];
                    objArr22[0] = z ? "VZW" : "";
                    carrierInfraMediator.set(values22, objArr22);
                }
                z = false;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("SimMobility: shouldUseVZWIconPolicy - ", z, "MobileConnectionsRepository");
                CarrierInfraMediator.Values values222 = CarrierInfraMediator.Values.OVERRIDE_ICON_BRANDING;
                Object[] objArr222 = new Object[1];
                objArr222[0] = z ? "VZW" : "";
                carrierInfraMediator.set(values222, objArr222);
            }
        }
        for (Map.Entry entry2 : ((LinkedHashMap) mobileConnectionsRepositoryImpl.subIdRepositoryCache).entrySet()) {
            int intValue2 = ((Number) entry2.getKey()).intValue();
            FullMobileConnectionRepository fullMobileConnectionRepository = (FullMobileConnectionRepository) entry2.getValue();
            Integer num = (Integer) mobileConnectionsRepositoryImpl.carrierMergedSubId.getValue();
            fullMobileConnectionRepository._isCarrierMerged.setValue(Boolean.valueOf(num != null && intValue2 == num.intValue()));
            fullMobileConnectionRepository.setSim1On(z3);
        }
        return Unit.INSTANCE;
    }
}
