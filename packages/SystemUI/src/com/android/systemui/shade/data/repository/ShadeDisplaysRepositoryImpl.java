package com.android.systemui.shade.data.repository;

import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.shade.display.ShadeDisplayPolicy;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import java.util.Set;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeDisplaysRepositoryImpl implements MutableShadeDisplaysRepository {
    public final StateFlowImpl _committedDisplayId;
    public final StateFlowImpl displayId;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 displayIdFromPolicy;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 keyguardAwareDisplayPolicy;
    public final ReadonlyStateFlow pendingDisplayId;
    public final ReadonlyStateFlow policy;

    public ShadeDisplaysRepositoryImpl(final GlobalSettings globalSettings, final ShadeDisplayPolicy shadeDisplayPolicy, CoroutineScope coroutineScope, final Set<ShadeDisplayPolicy> set, boolean z, KeyguardRepository keyguardRepository, DisplayRepository displayRepository) {
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new ShadeDisplaysRepositoryImpl$policy$1(null), SettingsProxyExt.INSTANCE.observerFlow(globalSettings, "shade_display_awareness"));
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ ShadeDisplayPolicy $defaultPolicy$inlined;
                public final /* synthetic */ GlobalSettings $globalSettings$inlined;
                public final /* synthetic */ Set $policies$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, GlobalSettings globalSettings, Set set, ShadeDisplayPolicy shadeDisplayPolicy) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$globalSettings$inlined = globalSettings;
                    this.$policies$inlined = set;
                    this.$defaultPolicy$inlined = shadeDisplayPolicy;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L6e
                    L27:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r9)
                        kotlin.Unit r8 = (kotlin.Unit) r8
                        com.android.systemui.util.settings.GlobalSettings r8 = r7.$globalSettings$inlined
                        java.lang.String r9 = "shade_display_awareness"
                        java.lang.String r2 = r8.getString(r9)
                        java.util.Set r4 = r7.$policies$inlined
                        java.util.Iterator r4 = r4.iterator()
                    L43:
                        boolean r5 = r4.hasNext()
                        if (r5 == 0) goto L5a
                        java.lang.Object r5 = r4.next()
                        com.android.systemui.shade.display.ShadeDisplayPolicy r5 = (com.android.systemui.shade.display.ShadeDisplayPolicy) r5
                        java.lang.String r6 = r5.getName()
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r6 == 0) goto L43
                        goto L63
                    L5a:
                        com.android.systemui.shade.display.ShadeDisplayPolicy r5 = r7.$defaultPolicy$inlined
                        java.lang.String r2 = r5.getName()
                        r8.putString(r9, r2)
                    L63:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        java.lang.Object r7 = r7.emit(r5, r0)
                        if (r7 != r1) goto L6e
                        return r1
                    L6e:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, globalSettings, set, shadeDisplayPolicy), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        ReadonlyStateFlow stateIn = FlowKt.stateIn(distinctUntilChanged, coroutineScope, SharingStarted.Companion.Eagerly, shadeDisplayPolicy);
        this.policy = stateIn;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.transformLatest(stateIn, new ShadeDisplaysRepositoryImpl$special$$inlined$flatMapLatest$1(null)), ((DisplayRepositoryImpl) displayRepository).displayRepositoryFromLib.getDisplayIds(), new ShadeDisplaysRepositoryImpl$displayIdFromPolicy$2(null));
        this.displayIdFromPolicy = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = z ? new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((KeyguardRepositoryImpl) keyguardRepository).isKeyguardShowing, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new ShadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1(null)) : flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.keyguardAwareDisplayPolicy = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.pendingDisplayId = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(0);
        this._committedDisplayId = MutableStateFlow;
        this.displayId = MutableStateFlow;
    }
}
