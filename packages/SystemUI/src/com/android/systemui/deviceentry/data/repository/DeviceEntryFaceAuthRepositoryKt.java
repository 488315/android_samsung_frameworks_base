package com.android.systemui.deviceentry.data.repository;

import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.plugins.log.TableLogBufferBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes2.dex */
public abstract class DeviceEntryFaceAuthRepositoryKt {
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryKt$andAllFlows$$inlined$combine$1] */
    public static final DeviceEntryFaceAuthRepositoryKt$andAllFlows$$inlined$combine$1 access$andAllFlows(final List list, final String str, final TableLogBuffer tableLogBuffer) {
        List list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add((Flow) ((Pair) it.next()).getFirst());
        }
        final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
        return new Flow() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryKt$andAllFlows$$inlined$combine$1

            /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryKt$andAllFlows$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                final /* synthetic */ String $combinedLoggingInfo$inlined;
                final /* synthetic */ TableLogBuffer $tableLogBuffer$inlined;
                final /* synthetic */ List $this_andAllFlows$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, TableLogBuffer tableLogBuffer, String str, List list) {
                    super(3, continuation);
                    this.$tableLogBuffer$inlined = tableLogBuffer;
                    this.$combinedLoggingInfo$inlined = str;
                    this.$this_andAllFlows$inlined = list;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.$tableLogBuffer$inlined, this.$combinedLoggingInfo$inlined, this.$this_andAllFlows$inlined);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Boolean[] boolArr = (Boolean[]) ((Object[]) this.L$1);
                        if (boolArr.length == 0) {
                            throw new UnsupportedOperationException("Empty array can't be reduced.");
                        }
                        Boolean boolValueOf = boolArr[0];
                        int length = boolArr.length - 1;
                        if (1 <= length) {
                            int i2 = 1;
                            while (true) {
                                boolean zBooleanValue = boolArr[i2].booleanValue();
                                boolean zBooleanValue2 = boolValueOf.booleanValue();
                                TableLogBuffer tableLogBuffer = this.$tableLogBuffer$inlined;
                                String str = (String) ((Pair) this.$this_andAllFlows$inlined.get(i2)).getSecond();
                                tableLogBuffer.getClass();
                                TableLogBufferBase.DefaultImpls.logChange(tableLogBuffer, "", str, zBooleanValue);
                                boolValueOf = Boolean.valueOf(zBooleanValue2 && zBooleanValue);
                                if (i2 == length) {
                                    break;
                                }
                                i2++;
                            }
                        }
                        boolean zBooleanValue3 = boolValueOf.booleanValue();
                        TableLogBuffer tableLogBuffer2 = this.$tableLogBuffer$inlined;
                        String str2 = this.$combinedLoggingInfo$inlined;
                        tableLogBuffer2.getClass();
                        TableLogBufferBase.DefaultImpls.logChange(tableLogBuffer2, "", str2, zBooleanValue3);
                        Boolean boolValueOf2 = Boolean.valueOf(zBooleanValue3);
                        this.label = 1;
                        if (flowCollector.emit(boolValueOf2, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryKt$andAllFlows$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Boolean[flowArr2.length];
                    }
                }, new AnonymousClass3(null, tableLogBuffer, str, list), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        };
    }
}
