package com.android.systemui.slice;

import android.net.Uri;
import androidx.slice.Slice;
import androidx.slice.SliceViewManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public abstract class SliceViewManagerExtKt {

    /* renamed from: com.android.systemui.slice.SliceViewManagerExtKt$sliceForUri$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Uri $sliceUri;
        final /* synthetic */ SliceViewManager $this_sliceForUri;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SliceViewManager sliceViewManager, Uri uri, Continuation continuation) {
            super(2, continuation);
            this.$this_sliceForUri = sliceViewManager;
            this.$sliceUri = uri;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_sliceForUri, this.$sliceUri, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0066, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r5, r6) == r0) goto L16;
         */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            final SliceViewManager.SliceCallback sliceCallback;
            ProducerScope producerScope;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope2 = (ProducerScope) this.L$0;
                sliceCallback = new SliceViewManager.SliceCallback() { // from class: com.android.systemui.slice.SliceViewManagerExtKt$sliceForUri$1$callback$1

                    /* renamed from: com.android.systemui.slice.SliceViewManagerExtKt$sliceForUri$1$callback$1$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                        final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
                        final /* synthetic */ Slice $it;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(ProducerScope producerScope, Slice slice, Continuation continuation) {
                            super(2, continuation);
                            this.$$this$conflatedCallbackFlow = producerScope;
                            this.$it = slice;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass1(this.$$this$conflatedCallbackFlow, this.$it, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                SendChannel sendChannel = this.$$this$conflatedCallbackFlow;
                                Slice slice = this.$it;
                                this.label = 1;
                                if (((ChannelCoroutine) sendChannel)._channel.send(slice, this) == coroutineSingletons) {
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

                    @Override // androidx.slice.SliceViewManager.SliceCallback
                    public final void onSliceUpdated(Slice slice) {
                        ProducerScope producerScope3 = producerScope2;
                        CoroutineTracingKt.launchTraced$default(producerScope3, null, null, new AnonymousClass1(producerScope3, slice, null), 7);
                    }
                };
                Slice sliceBindSlice = this.$this_sliceForUri.bindSlice(this.$sliceUri);
                this.L$0 = producerScope2;
                this.L$1 = sliceCallback;
                this.label = 1;
                if (((ChannelCoroutine) producerScope2)._channel.send(sliceBindSlice, this) != coroutineSingletons) {
                    producerScope = producerScope2;
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            sliceCallback = (SliceViewManager.SliceCallback) this.L$1;
            producerScope = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            this.$this_sliceForUri.registerSliceCallback(this.$sliceUri, sliceCallback);
            final SliceViewManager sliceViewManager = this.$this_sliceForUri;
            final Uri uri = this.$sliceUri;
            Function0 function0 = new Function0() { // from class: com.android.systemui.slice.SliceViewManagerExtKt$sliceForUri$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    sliceViewManager.unregisterSliceCallback(uri, sliceCallback);
                    return Unit.INSTANCE;
                }
            };
            this.L$0 = null;
            this.L$1 = null;
            this.label = 2;
        }
    }

    public static final Flow sliceForUri(SliceViewManager sliceViewManager, Uri uri) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(sliceViewManager, uri, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(anonymousClass1);
    }
}
