package com.android.systemui.volume.panel.component.anc.domain.interactor;

import android.bluetooth.BluetoothDevice;
import android.net.Uri;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.systemui.slice.SliceViewManagerExtKt;
import com.android.systemui.volume.domain.model.AudioOutputDevice;
import com.android.systemui.volume.panel.component.anc.data.repository.AncSliceRepository;
import com.android.systemui.volume.panel.component.anc.data.repository.AncSliceRepositoryImpl;
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
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

public final class AncSliceInteractor$ancSlice$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ boolean $hideLabel$inlined;
    final /* synthetic */ boolean $isCollapsed$inlined;
    final /* synthetic */ int $width$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AncSliceInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AncSliceInteractor$ancSlice$$inlined$flatMapLatest$1(Continuation continuation, AncSliceInteractor ancSliceInteractor, int i, boolean z, boolean z2) {
        super(3, continuation);
        this.this$0 = ancSliceInteractor;
        this.$width$inlined = i;
        this.$isCollapsed$inlined = z;
        this.$hideLabel$inlined = z2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AncSliceInteractor$ancSlice$$inlined$flatMapLatest$1 ancSliceInteractor$ancSlice$$inlined$flatMapLatest$1 = new AncSliceInteractor$ancSlice$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$width$inlined, this.$isCollapsed$inlined, this.$hideLabel$inlined);
        ancSliceInteractor$ancSlice$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        ancSliceInteractor$ancSlice$$inlined$flatMapLatest$1.L$1 = obj2;
        return ancSliceInteractor$ancSlice$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        Uri parse;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            AudioOutputDevice audioOutputDevice = (AudioOutputDevice) this.L$1;
            if (audioOutputDevice instanceof AudioOutputDevice.Bluetooth) {
                AncSliceRepository ancSliceRepository = this.this$0.ancSliceRepository;
                BluetoothDevice bluetoothDevice = ((AudioOutputDevice.Bluetooth) audioOutputDevice).cachedBluetoothDevice.mDevice;
                int i2 = this.$width$inlined;
                boolean z = this.$isCollapsed$inlined;
                boolean z2 = this.$hideLabel$inlined;
                AncSliceRepositoryImpl ancSliceRepositoryImpl = (AncSliceRepositoryImpl) ancSliceRepository;
                ancSliceRepositoryImpl.getClass();
                String controlUriMetaData = BluetoothUtils.getControlUriMetaData(bluetoothDevice);
                if (controlUriMetaData == null || controlUriMetaData.length() == 0) {
                    parse = null;
                } else {
                    parse = Uri.parse(controlUriMetaData + i2 + "&version=2&is_collapsed=" + z + "&hide_label=" + z2);
                }
                final Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22 = parse == null ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null) : FlowKt.flowOn(SliceViewManagerExtKt.sliceForUri(ancSliceRepositoryImpl.sliceViewManager, parse), ancSliceRepositoryImpl.mainCoroutineContext);
                final AncSliceInteractor ancSliceInteractor = this.this$0;
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.volume.panel.component.anc.domain.interactor.AncSliceInteractor$ancSlice$lambda$3$$inlined$filter$1

                    /* renamed from: com.android.systemui.volume.panel.component.anc.domain.interactor.AncSliceInteractor$ancSlice$lambda$3$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AncSliceInteractor this$0;

                        /* renamed from: com.android.systemui.volume.panel.component.anc.domain.interactor.AncSliceInteractor$ancSlice$lambda$3$$inlined$filter$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            Object L$1;
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

                        public AnonymousClass2(FlowCollector flowCollector, AncSliceInteractor ancSliceInteractor) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = ancSliceInteractor;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                            /*
                                r6 = this;
                                boolean r0 = r8 instanceof com.android.systemui.volume.panel.component.anc.domain.interactor.AncSliceInteractor$ancSlice$lambda$3$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r8
                                com.android.systemui.volume.panel.component.anc.domain.interactor.AncSliceInteractor$ancSlice$lambda$3$$inlined$filter$1$2$1 r0 = (com.android.systemui.volume.panel.component.anc.domain.interactor.AncSliceInteractor$ancSlice$lambda$3$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.volume.panel.component.anc.domain.interactor.AncSliceInteractor$ancSlice$lambda$3$$inlined$filter$1$2$1 r0 = new com.android.systemui.volume.panel.component.anc.domain.interactor.AncSliceInteractor$ancSlice$lambda$3$$inlined$filter$1$2$1
                                r0.<init>(r8)
                            L18:
                                java.lang.Object r8 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r8)
                                goto L7e
                            L27:
                                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                r6.<init>(r7)
                                throw r6
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r8)
                                r8 = r7
                                androidx.slice.Slice r8 = (androidx.slice.Slice) r8
                                r2 = 0
                                if (r8 == 0) goto L6f
                                com.android.systemui.volume.panel.component.anc.domain.interactor.AncSliceInteractor r4 = r6.this$0
                                r4.getClass()
                                java.lang.String[] r4 = r8.mHints
                                java.util.List r4 = java.util.Arrays.asList(r4)
                                java.lang.String r5 = "error"
                                boolean r4 = r4.contains(r5)
                                if (r4 == 0) goto L4c
                                goto L6e
                            L4c:
                                androidx.slice.SliceItem[] r8 = r8.mItems
                                java.util.List r8 = java.util.Arrays.asList(r8)
                                java.util.Iterator r8 = r8.iterator()
                            L56:
                                boolean r4 = r8.hasNext()
                                if (r4 == 0) goto L6e
                                java.lang.Object r4 = r8.next()
                                androidx.slice.SliceItem r4 = (androidx.slice.SliceItem) r4
                                java.lang.String r4 = r4.mFormat
                                java.lang.String r5 = "slice"
                                boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
                                if (r4 == 0) goto L56
                                goto L6f
                            L6e:
                                r2 = r3
                            L6f:
                                r8 = r2 ^ 1
                                if (r8 == 0) goto L7e
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                java.lang.Object r6 = r6.emit(r7, r0)
                                if (r6 != r1) goto L7e
                                return r1
                            L7e:
                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                return r6
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.anc.domain.interactor.AncSliceInteractor$ancSlice$lambda$3$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, ancSliceInteractor), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
