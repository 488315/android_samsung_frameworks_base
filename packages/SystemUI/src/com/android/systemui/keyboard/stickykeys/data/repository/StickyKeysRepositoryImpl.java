package com.android.systemui.keyboard.stickykeys.data.repository;

import android.hardware.input.InputManager;
import com.android.systemui.keyboard.stickykeys.StickyKeysLogger;
import com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepository;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StickyKeysRepositoryImpl implements StickyKeysRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final InputManager inputManager;
    public final Flow settingEnabled;
    public final Flow stickyKeys;
    public final StickyKeysLogger stickyKeysLogger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public StickyKeysRepositoryImpl(InputManager inputManager, CoroutineDispatcher coroutineDispatcher, UserAwareSecureSettingsRepository userAwareSecureSettingsRepository, StickyKeysLogger stickyKeysLogger) {
        this.inputManager = inputManager;
        this.stickyKeysLogger = stickyKeysLogger;
        final Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new StickyKeysRepositoryImpl$stickyKeys$1(this, null));
        this.stickyKeys = FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ StickyKeysRepositoryImpl this$0;

                /* renamed from: com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, StickyKeysRepositoryImpl stickyKeysRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = stickyKeysRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto Le6
                    L28:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L30:
                        kotlin.ResultKt.throwOnFailure(r8)
                        android.hardware.input.StickyModifierState r7 = (android.hardware.input.StickyModifierState) r7
                        r7.getClass()
                        int r8 = com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl.$r8$clinit
                        com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl r8 = r6.this$0
                        r8.getClass()
                        java.util.LinkedHashMap r8 = new java.util.LinkedHashMap
                        r8.<init>()
                        boolean r2 = r7.isAltGrModifierOn()
                        r4 = 0
                        if (r2 == 0) goto L54
                        com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey r2 = com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey.ALT_GR
                        com.android.systemui.keyboard.stickykeys.shared.model.Locked r5 = com.android.systemui.keyboard.stickykeys.shared.model.Locked.m2583boximpl(r4)
                        r8.put(r2, r5)
                    L54:
                        boolean r2 = r7.isAltGrModifierLocked()
                        if (r2 == 0) goto L63
                        com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey r2 = com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey.ALT_GR
                        com.android.systemui.keyboard.stickykeys.shared.model.Locked r5 = com.android.systemui.keyboard.stickykeys.shared.model.Locked.m2583boximpl(r3)
                        r8.put(r2, r5)
                    L63:
                        boolean r2 = r7.isAltModifierOn()
                        if (r2 == 0) goto L72
                        com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey r2 = com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey.ALT
                        com.android.systemui.keyboard.stickykeys.shared.model.Locked r5 = com.android.systemui.keyboard.stickykeys.shared.model.Locked.m2583boximpl(r4)
                        r8.put(r2, r5)
                    L72:
                        boolean r2 = r7.isAltModifierLocked()
                        if (r2 == 0) goto L81
                        com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey r2 = com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey.ALT
                        com.android.systemui.keyboard.stickykeys.shared.model.Locked r5 = com.android.systemui.keyboard.stickykeys.shared.model.Locked.m2583boximpl(r3)
                        r8.put(r2, r5)
                    L81:
                        boolean r2 = r7.isCtrlModifierOn()
                        if (r2 == 0) goto L90
                        com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey r2 = com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey.CTRL
                        com.android.systemui.keyboard.stickykeys.shared.model.Locked r5 = com.android.systemui.keyboard.stickykeys.shared.model.Locked.m2583boximpl(r4)
                        r8.put(r2, r5)
                    L90:
                        boolean r2 = r7.isCtrlModifierLocked()
                        if (r2 == 0) goto L9f
                        com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey r2 = com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey.CTRL
                        com.android.systemui.keyboard.stickykeys.shared.model.Locked r5 = com.android.systemui.keyboard.stickykeys.shared.model.Locked.m2583boximpl(r3)
                        r8.put(r2, r5)
                    L9f:
                        boolean r2 = r7.isMetaModifierOn()
                        if (r2 == 0) goto Lae
                        com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey r2 = com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey.META
                        com.android.systemui.keyboard.stickykeys.shared.model.Locked r5 = com.android.systemui.keyboard.stickykeys.shared.model.Locked.m2583boximpl(r4)
                        r8.put(r2, r5)
                    Lae:
                        boolean r2 = r7.isMetaModifierLocked()
                        if (r2 == 0) goto Lbd
                        com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey r2 = com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey.META
                        com.android.systemui.keyboard.stickykeys.shared.model.Locked r5 = com.android.systemui.keyboard.stickykeys.shared.model.Locked.m2583boximpl(r3)
                        r8.put(r2, r5)
                    Lbd:
                        boolean r2 = r7.isShiftModifierOn()
                        if (r2 == 0) goto Lcc
                        com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey r2 = com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey.SHIFT
                        com.android.systemui.keyboard.stickykeys.shared.model.Locked r4 = com.android.systemui.keyboard.stickykeys.shared.model.Locked.m2583boximpl(r4)
                        r8.put(r2, r4)
                    Lcc:
                        boolean r7 = r7.isShiftModifierLocked()
                        if (r7 == 0) goto Ldb
                        com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey r7 = com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey.SHIFT
                        com.android.systemui.keyboard.stickykeys.shared.model.Locked r2 = com.android.systemui.keyboard.stickykeys.shared.model.Locked.m2583boximpl(r3)
                        r8.put(r7, r2)
                    Ldb:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto Le6
                        return r1
                    Le6:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new StickyKeysRepositoryImpl$stickyKeys$3(this, null)), coroutineDispatcher);
        this.settingEnabled = FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(userAwareSecureSettingsRepository.boolSetting("accessibility_sticky_keys", false), new StickyKeysRepositoryImpl$settingEnabled$1(this, null)), coroutineDispatcher);
    }
}
