package com.android.systemui.keyboard.data.repository;

import android.hardware.input.InputManager;
import android.hardware.input.KeyboardBacklightState;
import android.view.InputDevice;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.keyboard.data.model.Keyboard;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import com.android.systemui.keyboard.shared.model.BacklightModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__MergeKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyboardRepositoryImpl implements KeyboardRepository {
    public final Flow backlight;
    public final InputManager inputManager;
    public final Flow isAnyKeyboardConnected;
    public final ReadonlySharedFlow keyboardsChange;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DeviceAdded implements DeviceChange {
        public final int deviceId;

        public DeviceAdded(int i) {
            this.deviceId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DeviceAdded) && this.deviceId == ((DeviceAdded) obj).deviceId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.deviceId);
        }

        public final String toString() {
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("DeviceAdded(deviceId="), this.deviceId, ")");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DeviceChange {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DeviceRemoved implements DeviceChange {
        public static final DeviceRemoved INSTANCE = new DeviceRemoved();

        private DeviceRemoved() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FreshStart implements DeviceChange {
        public static final FreshStart INSTANCE = new FreshStart();

        private FreshStart() {
        }
    }

    static {
        new Companion(null);
    }

    public KeyboardRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, InputManager inputManager) {
        this.inputManager = inputManager;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        KeyboardRepositoryImpl$keyboardsChange$1 keyboardRepositoryImpl$keyboardsChange$1 = new KeyboardRepositoryImpl$keyboardsChange$1(this, null);
        conflatedCallbackFlow.getClass();
        final Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(keyboardRepositoryImpl$keyboardsChange$1);
        Flow flow = new Flow() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$1$2 */
            public final class C14412 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyboardRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$1$2", m277f = "KeyboardRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C14412.this.emit(null, this);
                    }
                }

                public C14412(FlowCollector flowCollector, KeyboardRepositoryImpl keyboardRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyboardRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Pair pair = (Pair) obj;
                                Set set = (Set) pair.component1();
                                KeyboardRepositoryImpl.DeviceChange deviceChange = (KeyboardRepositoryImpl.DeviceChange) pair.component2();
                                ArrayList arrayList = new ArrayList();
                                for (Object obj3 : set) {
                                    InputDevice inputDevice = this.this$0.inputManager.getInputDevice(((Number) obj3).intValue());
                                    if ((inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard()) ? false : true) {
                                        arrayList.add(obj3);
                                    }
                                }
                                Pair pair2 = new Pair(arrayList, deviceChange);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(pair2, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C14412(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        final ReadonlySharedFlow shareIn = FlowKt.shareIn(flow, coroutineScope, SharingStarted.Companion.Lazily, 1);
        this.keyboardsChange = shareIn;
        final KeyboardRepositoryImpl$newlyConnectedKeyboard$1 keyboardRepositoryImpl$newlyConnectedKeyboard$1 = new KeyboardRepositoryImpl$newlyConnectedKeyboard$1(null);
        int i = FlowKt__MergeKt.$r8$clinit;
        final Flow flow2 = new Flow() { // from class: kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1$2 */
            public final class C48442 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ Function2 $transform$inlined;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1$2", m277f = "Merge.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut, IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C48442.this.emit(null, this);
                    }
                }

                public C48442(FlowCollector flowCollector, Function2 function2) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$transform$inlined = function2;
                }

                /* JADX WARN: Removed duplicated region for block: B:19:0x005a A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object obj2;
                    CoroutineSingletons coroutineSingletons;
                    int i;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            obj2 = anonymousClass1.result;
                            coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                FlowCollector flowCollector2 = this.$this_unsafeFlow;
                                anonymousClass1.L$0 = flowCollector2;
                                anonymousClass1.label = 1;
                                Object invoke = this.$transform$inlined.invoke(obj, anonymousClass1);
                                if (invoke == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                                obj2 = invoke;
                                flowCollector = flowCollector2;
                            } else {
                                if (i != 1) {
                                    if (i != 2) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                    return Unit.INSTANCE;
                                }
                                flowCollector = (FlowCollector) anonymousClass1.L$0;
                                ResultKt.throwOnFailure(obj2);
                            }
                            anonymousClass1.L$0 = null;
                            anonymousClass1.label = 2;
                            if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    obj2 = anonymousClass1.result;
                    coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                    if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C48442(flowCollector, keyboardRepositoryImpl$newlyConnectedKeyboard$1), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow3 = new Flow() { // from class: kotlinx.coroutines.flow.FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new FlowKt__MergeKt$flattenConcat$1$1(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        FlowKt.flowOn(new Flow() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$mapNotNull$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$mapNotNull$1$2 */
            public final class C14442 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyboardRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$mapNotNull$1$2", m277f = "KeyboardRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getUsbConnectionType}, m279m = "emit")
                /* renamed from: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C14442.this.emit(null, this);
                    }
                }

                public C14442(FlowCollector flowCollector, KeyboardRepositoryImpl keyboardRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyboardRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                InputDevice inputDevice = this.this$0.inputManager.getInputDevice(((Number) obj).intValue());
                                Keyboard keyboard = inputDevice == null ? null : new Keyboard(inputDevice.getVendorId(), inputDevice.getProductId());
                                if (keyboard != null) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(keyboard, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C14442(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        this.isAnyKeyboardConnected = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$2$2 */
            public final class C14422 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$2$2", m277f = "KeyboardRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C14422.this.emit(null, this);
                    }
                }

                public C14422(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Boolean valueOf = Boolean.valueOf(!((Collection) ((Pair) obj).component1()).isEmpty());
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C14422(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
        final Flow conflatedCallbackFlow3 = ConflatedCallbackFlow.conflatedCallbackFlow(new KeyboardRepositoryImpl$backlightStateListener$1(this, null));
        this.backlight = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$3$2 */
            public final class C14432 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$3$2", m277f = "KeyboardRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C14432.this.emit(null, this);
                    }
                }

                public C14432(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                KeyboardBacklightState keyboardBacklightState = (KeyboardBacklightState) obj;
                                BacklightModel backlightModel = new BacklightModel(keyboardBacklightState.getBrightnessLevel(), keyboardBacklightState.getMaxBrightnessLevel());
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(backlightModel, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C14432(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
    }

    public static final void access$sendWithLogging(KeyboardRepositoryImpl keyboardRepositoryImpl, ProducerScope producerScope, Object obj) {
        keyboardRepositoryImpl.getClass();
        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, obj, "KeyboardRepositoryImpl");
    }
}
