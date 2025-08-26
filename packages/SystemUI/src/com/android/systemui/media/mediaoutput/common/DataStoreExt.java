package com.android.systemui.media.mediaoutput.common;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class DataStoreExt {
    public static final DataStoreExt INSTANCE = new DataStoreExt();

    private DataStoreExt() {
    }

    public static DataStoreExt$special$$inlined$map$2 isCastingPriority(DataStore dataStore) {
        final Flow data = dataStore.getData();
        return new DataStoreExt$special$$inlined$map$2(new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$1

            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Boolean boolValueOf;
                    boolean zBooleanValue;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Preferences preferences = (Preferences) obj;
                        PreferenceKeys preferenceKeys = PreferenceKeys.INSTANCE;
                        preferenceKeys.getClass();
                        Boolean bool = (Boolean) preferences.get(PreferenceKeys.MIRRORING_PRIORITY);
                        if (bool != null) {
                            zBooleanValue = bool.booleanValue();
                        } else {
                            preferenceKeys.getClass();
                            Integer num = (Integer) preferences.get(PreferenceKeys.CASTING_PRIORITY);
                            if (num != null) {
                                boolValueOf = Boolean.valueOf(num.intValue() == 0);
                            } else {
                                boolValueOf = null;
                            }
                            zBooleanValue = boolValueOf != null ? boolValueOf.booleanValue() : true;
                        }
                        Boolean boolValueOf2 = Boolean.valueOf(zBooleanValue);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf2, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = data.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
    }
}
