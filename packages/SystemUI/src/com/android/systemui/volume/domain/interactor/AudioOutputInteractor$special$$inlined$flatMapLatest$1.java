package com.android.systemui.volume.domain.interactor;

import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AudioOutputInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ AudioRepository $audioRepository$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AudioOutputInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioOutputInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, AudioRepository audioRepository, AudioOutputInteractor audioOutputInteractor) {
        super(3, continuation);
        this.$audioRepository$inlined = audioRepository;
        this.this$0 = audioOutputInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AudioOutputInteractor$special$$inlined$flatMapLatest$1 audioOutputInteractor$special$$inlined$flatMapLatest$1 = new AudioOutputInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$audioRepository$inlined, this.this$0);
        audioOutputInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        audioOutputInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return audioOutputInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                final ReadonlyStateFlow communicationDevice = ((AudioRepositoryImpl) this.$audioRepository$inlined).getCommunicationDevice();
                final AudioOutputInteractor audioOutputInteractor = this.this$0;
                flow = new Flow() { // from class: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$1

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AudioOutputInteractor this$0;

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, AudioOutputInteractor audioOutputInteractor) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = audioOutputInteractor;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                            /*
                                Method dump skipped, instructions count: 253
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, audioOutputInteractor), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            } else {
                final AudioOutputInteractor audioOutputInteractor2 = this.this$0;
                final Flow flow2 = audioOutputInteractor2.mediaOutputInteractor.currentConnectedDevice;
                flow = new Flow() { // from class: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AudioOutputInteractor this$0;

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, AudioOutputInteractor audioOutputInteractor) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = audioOutputInteractor;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                            /*
                                r5 = this;
                                boolean r0 = r7 instanceof com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r7
                                com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2$2$1 r0 = (com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2$2$1 r0 = new com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2$2$1
                                r0.<init>(r7)
                            L18:
                                java.lang.Object r7 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r7)
                                goto L8a
                            L27:
                                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                r5.<init>(r6)
                                throw r5
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r7)
                                com.android.settingslib.media.MediaDevice r6 = (com.android.settingslib.media.MediaDevice) r6
                                if (r6 == 0) goto L7e
                                com.android.systemui.volume.domain.interactor.AudioOutputInteractor r7 = r5.this$0
                                r7.getClass()
                                boolean r7 = r6 instanceof com.android.settingslib.media.BluetoothMediaDevice
                                if (r7 == 0) goto L53
                                com.android.systemui.volume.domain.model.AudioOutputDevice$Bluetooth r7 = new com.android.systemui.volume.domain.model.AudioOutputDevice$Bluetooth
                                com.android.settingslib.media.BluetoothMediaDevice r6 = (com.android.settingslib.media.BluetoothMediaDevice) r6
                                com.android.settingslib.bluetooth.CachedBluetoothDevice r2 = r6.mCachedDevice
                                java.lang.String r2 = r2.getName()
                                android.graphics.drawable.Drawable r4 = r6.getIcon()
                                com.android.settingslib.bluetooth.CachedBluetoothDevice r6 = r6.mCachedDevice
                                r7.<init>(r2, r4, r6)
                                goto L7f
                            L53:
                                int r7 = r6.getDeviceType()
                                r2 = 3
                                if (r7 == r2) goto L70
                                int r7 = r6.getDeviceType()
                                r2 = 2
                                if (r7 != r2) goto L62
                                goto L70
                            L62:
                                com.android.systemui.volume.domain.model.AudioOutputDevice$BuiltIn r7 = new com.android.systemui.volume.domain.model.AudioOutputDevice$BuiltIn
                                java.lang.String r2 = r6.getName()
                                android.graphics.drawable.Drawable r6 = r6.getIcon()
                                r7.<init>(r2, r6)
                                goto L7f
                            L70:
                                com.android.systemui.volume.domain.model.AudioOutputDevice$Wired r7 = new com.android.systemui.volume.domain.model.AudioOutputDevice$Wired
                                java.lang.String r2 = r6.getName()
                                android.graphics.drawable.Drawable r6 = r6.getIcon()
                                r7.<init>(r2, r6)
                                goto L7f
                            L7e:
                                r7 = 0
                            L7f:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                                java.lang.Object r5 = r5.emit(r7, r0)
                                if (r5 != r1) goto L8a
                                return r1
                            L8a:
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                return r5
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, audioOutputInteractor2), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
