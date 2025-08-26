package com.android.systemui.media.mediaoutput.common;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import com.android.systemui.media.mediaoutput.entity.Configuration;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class DataStoreExt$special$$inlined$map$3 implements Flow {
    public final /* synthetic */ DataStore $this_isSpotifyCastingPriority$inlined;
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$3$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ DataStore $this_isSpotifyCastingPriority$inlined;
        public final /* synthetic */ FlowCollector $this_unsafeFlow;

        /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$3$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
            Object L$0;
            Object L$1;
            Object L$2;
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

        public AnonymousClass2(FlowCollector flowCollector, DataStore dataStore) {
            this.$this_unsafeFlow = flowCollector;
            this.$this_isSpotifyCastingPriority$inlined = dataStore;
        }

        /* JADX WARN: Code restructure failed: missing block: B:41:0x00de, code lost:
        
            if (r10.emit(r9, r0) == r1) goto L42;
         */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1;
            Object objFirst;
            Preferences preferences;
            FlowCollector flowCollector;
            FlowCollector flowCollector2;
            Object objValueOf;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i = anonymousClass1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    anonymousClass1.label = i - Integer.MIN_VALUE;
                } else {
                    anonymousClass1 = new AnonymousClass1(continuation);
                }
            }
            Object objFirst2 = anonymousClass1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = anonymousClass1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(objFirst2);
                Preferences preferences2 = (Preferences) obj;
                DataStoreExt.INSTANCE.getClass();
                DataStoreExt$special$$inlined$map$5 dataStoreExt$special$$inlined$map$5 = new DataStoreExt$special$$inlined$map$5(this.$this_isSpotifyCastingPriority$inlined.getData());
                anonymousClass1.L$0 = this;
                FlowCollector flowCollector3 = this.$this_unsafeFlow;
                anonymousClass1.L$1 = flowCollector3;
                anonymousClass1.L$2 = preferences2;
                anonymousClass1.label = 1;
                objFirst = FlowKt.first(dataStoreExt$special$$inlined$map$5, anonymousClass1);
                if (objFirst != coroutineSingletons) {
                    preferences = preferences2;
                    flowCollector = flowCollector3;
                }
                return coroutineSingletons;
            }
            if (i2 == 1) {
                Preferences preferences3 = (Preferences) anonymousClass1.L$2;
                flowCollector = (FlowCollector) anonymousClass1.L$1;
                AnonymousClass2 anonymousClass2 = (AnonymousClass2) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objFirst2);
                preferences = preferences3;
                this = anonymousClass2;
                objFirst = objFirst2;
            } else {
                if (i2 != 2) {
                    if (i2 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(objFirst2);
                    return Unit.INSTANCE;
                }
                flowCollector2 = (FlowCollector) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objFirst2);
                flowCollector = flowCollector2;
                objValueOf = objFirst2;
                anonymousClass1.L$0 = null;
                anonymousClass1.L$1 = null;
                anonymousClass1.L$2 = null;
                anonymousClass1.label = 3;
            }
            if (!((Configuration) objFirst).getSupportSpotifyMediaProvider()) {
                objFirst = null;
            }
            Configuration configuration = (Configuration) objFirst;
            if (configuration == null) {
                DataStoreExt dataStoreExt = DataStoreExt.INSTANCE;
                DataStore dataStore = this.$this_isSpotifyCastingPriority$inlined;
                dataStoreExt.getClass();
                DataStoreExt$special$$inlined$map$2 dataStoreExt$special$$inlined$map$2IsCastingPriority = DataStoreExt.isCastingPriority(dataStore);
                anonymousClass1.L$0 = flowCollector;
                anonymousClass1.L$1 = null;
                anonymousClass1.L$2 = null;
                anonymousClass1.label = 2;
                objFirst2 = FlowKt.first(dataStoreExt$special$$inlined$map$2IsCastingPriority, anonymousClass1);
                if (objFirst2 != coroutineSingletons) {
                    flowCollector2 = flowCollector;
                    flowCollector = flowCollector2;
                    objValueOf = objFirst2;
                    anonymousClass1.L$0 = null;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.L$2 = null;
                    anonymousClass1.label = 3;
                }
                return coroutineSingletons;
            }
            PreferenceKeys.INSTANCE.getClass();
            Boolean bool = (Boolean) preferences.get(PreferenceKeys.SPOTIFY_CASTING_PRIORITY);
            objValueOf = Boolean.valueOf((bool == null && (bool = (Boolean) configuration.getPlaybackPreferences().get("com.spotify.music")) == null) ? true : bool.booleanValue());
            anonymousClass1.L$0 = null;
            anonymousClass1.L$1 = null;
            anonymousClass1.L$2 = null;
            anonymousClass1.label = 3;
        }
    }

    public DataStoreExt$special$$inlined$map$3(Flow flow, DataStore dataStore) {
        this.$this_unsafeTransform$inlined = flow;
        this.$this_isSpotifyCastingPriority$inlined = dataStore;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object objCollect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.$this_isSpotifyCastingPriority$inlined), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
