package com.android.systemui.media.mediaoutput.common;

import androidx.datastore.preferences.core.Preferences;
import com.android.systemui.media.mediaoutput.entity.Configuration;
import com.google.gson.Gson;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class DataStoreExt$special$$inlined$map$5 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$5$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;

        /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$5$2$1, reason: invalid class name */
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

        /* JADX WARN: Removed duplicated region for block: B:27:0x0069  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1;
            Object failure;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i = anonymousClass1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    anonymousClass1.label = i - Integer.MIN_VALUE;
                } else {
                    anonymousClass1 = new AnonymousClass1(continuation);
                }
            }
            AnonymousClass1 anonymousClass12 = anonymousClass1;
            Object obj2 = anonymousClass12.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = anonymousClass12.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                PreferenceKeys.INSTANCE.getClass();
                String str = (String) ((Preferences) obj).get(PreferenceKeys.SCPM_CONFIGURATION);
                if (str != null) {
                    try {
                        int i3 = Result.$r8$clinit;
                        failure = (Configuration) new Gson().fromJson(str, Configuration.class);
                    } catch (Throwable th) {
                        int i4 = Result.$r8$clinit;
                        failure = new Result.Failure(th);
                    }
                    if (failure instanceof Result.Failure) {
                        failure = null;
                    }
                    Configuration configuration = (Configuration) failure;
                    if (configuration == null) {
                        configuration = new Configuration(false, null, false, false, 15, null);
                    }
                    anonymousClass12.label = 1;
                    if (this.$this_unsafeFlow.emit(configuration, anonymousClass12) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
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

    public DataStoreExt$special$$inlined$map$5(Flow flow) {
        this.$this_unsafeTransform$inlined = flow;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object objCollect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
