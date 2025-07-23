package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.InputManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Code restructure failed: missing block: B:19:0x0066, code lost:
                
                    if (r2.emit(r9, r0) == r1) goto L26;
                 */
                /* JADX WARN: Removed duplicated region for block: B:21:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository$special$$inlined$map$1$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        r5 = 0
                        if (r2 == 0) goto L3b
                        if (r2 == r4) goto L33
                        if (r2 != r3) goto L2b
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L69
                    L2b:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L33:
                        java.lang.Object r7 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L5b
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r9)
                        com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState r8 = (com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState) r8
                        boolean r9 = r8 instanceof com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState.Active
                        kotlinx.coroutines.flow.FlowCollector r2 = r7.$this_unsafeFlow
                        if (r9 == 0) goto L5d
                        com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository r7 = r7.this$0
                        kotlin.coroutines.CoroutineContext r9 = r7.bgCoroutineContext
                        com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository$activeInputDevice$1$1 r6 = new com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository$activeInputDevice$1$1
                        r6.<init>(r7, r8, r5)
                        r0.L$0 = r2
                        r0.label = r4
                        java.lang.Object r9 = kotlinx.coroutines.BuildersKt.withContext(r9, r6, r0)
                        if (r9 != r1) goto L5a
                        goto L68
                    L5a:
                        r7 = r2
                    L5b:
                        r2 = r7
                        goto L5e
                    L5d:
                        r9 = r5
                    L5e:
                        r0.L$0 = r5
                        r0.label = r3
                        java.lang.Object r7 = r2.emit(r9, r0)
                        if (r7 != r1) goto L69
                    L68:
                        return r1
                    L69:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperInputDeviceRepository$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.activeInputDevice = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Lazily, null);
    }
}
