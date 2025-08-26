package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.ServiceState;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.IntIterator;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class MobileConnectionsRepositoryKairosImpl$doAnyModemsSupportEmergencyCalls$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MobileConnectionsRepositoryKairosImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryKairosImpl$doAnyModemsSupportEmergencyCalls$2(MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryKairosImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MobileConnectionsRepositoryKairosImpl$doAnyModemsSupportEmergencyCalls$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryKairosImpl$doAnyModemsSupportEmergencyCalls$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z = true;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int activeModemCount = this.this$0.telephonyManager.getActiveModemCount();
        boolean zHasSystemFeature = this.this$0.context.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
        boolean zHasSystemFeature2 = this.this$0.context.getPackageManager().hasSystemFeature("android.hardware.telephony.calling");
        if (zHasSystemFeature && !zHasSystemFeature2) {
            return Boolean.FALSE;
        }
        IntRange intRangeUntil = RangesKt___RangesKt.until(0, activeModemCount);
        MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl = this.this$0;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator it = intRangeUntil.iterator();
        while (it.hasNext()) {
            arrayList.add(mobileConnectionsRepositoryKairosImpl.telephonyManager.getServiceStateForSlot(((IntIterator) it).nextInt()));
        }
        if (arrayList.isEmpty()) {
            z = false;
        } else {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                ServiceState serviceState = (ServiceState) obj2;
                if (serviceState != null && serviceState.isEmergencyOnly()) {
                    break;
                }
            }
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
