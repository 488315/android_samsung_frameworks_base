package com.android.systemui.volume.panel.component.anc.domain.interactor;

import android.bluetooth.BluetoothDevice;
import android.net.Uri;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.systemui.slice.SliceViewManagerExtKt;
import com.android.systemui.volume.domain.model.AudioOutputDevice;
import com.android.systemui.volume.panel.component.anc.data.repository.AncSliceRepository;
import com.android.systemui.volume.panel.component.anc.data.repository.AncSliceRepositoryImpl;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes3.dex */
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
        Uri uri;
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
                String fastPairCustomizedField = BluetoothUtils.getFastPairCustomizedField(bluetoothDevice, "HEARABLE_CONTROL_SLICE_WITH_WIDTH");
                if (fastPairCustomizedField == null || fastPairCustomizedField.length() == 0) {
                    uri = null;
                } else {
                    uri = Uri.parse(fastPairCustomizedField + i2 + "&version=2&is_collapsed=" + z + "&hide_label=" + z2);
                }
                final Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22 = uri == null ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null) : FlowKt.flowOn(SliceViewManagerExtKt.sliceForUri(ancSliceRepositoryImpl.sliceViewManager, uri), ancSliceRepositoryImpl.mainCoroutineContext);
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

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
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
                                Slice slice = (Slice) obj;
                                boolean z = false;
                                if (slice != null) {
                                    this.this$0.getClass();
                                    if (Arrays.asList(slice.mHints).contains("error")) {
                                        z = true;
                                    } else {
                                        Iterator it = Arrays.asList(slice.mItems).iterator();
                                        while (it.hasNext()) {
                                            if (Intrinsics.areEqual(((SliceItem) it.next()).mFormat, "slice")) {
                                                break;
                                            }
                                        }
                                        z = true;
                                    }
                                }
                                if (!z) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
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

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object objCollect = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22.collect(new AnonymousClass2(flowCollector2, ancSliceInteractor), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
