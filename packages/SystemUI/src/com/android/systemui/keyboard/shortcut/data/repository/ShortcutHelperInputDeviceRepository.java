package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.InputManager;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class ShortcutHelperInputDeviceRepository {
    public final ReadonlyStateFlow activeInputDevice;
    public final CoroutineContext bgCoroutineContext;
    public final InputManager inputManager;

    public ShortcutHelperInputDeviceRepository(ShortcutHelperStateRepository shortcutHelperStateRepository, CoroutineScope coroutineScope, CoroutineContext coroutineContext, InputManager inputManager) {
        this.bgCoroutineContext = coroutineContext;
        this.inputManager = inputManager;
        final ReadonlyStateFlow readonlyStateFlow = shortcutHelperStateRepository.state;
        Flow flow = new Flow() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ShortcutHelperInputDeviceRepository this$0;

                /* renamed from: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ShortcutHelperInputDeviceRepository shortcutHelperInputDeviceRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = shortcutHelperInputDeviceRepository;
                }

                /* JADX WARN: Code restructure failed: missing block: B:25:0x0066, code lost:
                
                    if (r2.emit(r9, r0) != r1) goto L27;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    FlowCollector flowCollector2;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objWithContext = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objWithContext);
                        ShortcutHelperState shortcutHelperState = (ShortcutHelperState) obj;
                        boolean z = shortcutHelperState instanceof ShortcutHelperState.Active;
                        flowCollector = this.$this_unsafeFlow;
                        if (z) {
                            ShortcutHelperInputDeviceRepository shortcutHelperInputDeviceRepository = this.this$0;
                            CoroutineContext coroutineContext = shortcutHelperInputDeviceRepository.bgCoroutineContext;
                            ShortcutHelperInputDeviceRepository$activeInputDevice$1$1 shortcutHelperInputDeviceRepository$activeInputDevice$1$1 = new ShortcutHelperInputDeviceRepository$activeInputDevice$1$1(shortcutHelperInputDeviceRepository, shortcutHelperState, null);
                            anonymousClass1.L$0 = flowCollector;
                            anonymousClass1.label = 1;
                            objWithContext = BuildersKt.withContext(coroutineContext, shortcutHelperInputDeviceRepository$activeInputDevice$1$1, anonymousClass1);
                            if (objWithContext != coroutineSingletons) {
                                flowCollector2 = flowCollector;
                            }
                            return coroutineSingletons;
                        }
                        objWithContext = null;
                        anonymousClass1.L$0 = null;
                        anonymousClass1.label = 2;
                    } else {
                        if (i2 != 1) {
                            if (i2 != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(objWithContext);
                            return Unit.INSTANCE;
                        }
                        flowCollector2 = (FlowCollector) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(objWithContext);
                    }
                    flowCollector = flowCollector2;
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.activeInputDevice = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Lazily, null);
    }
}
