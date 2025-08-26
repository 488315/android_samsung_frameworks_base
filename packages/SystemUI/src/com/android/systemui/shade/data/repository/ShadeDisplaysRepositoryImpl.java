package com.android.systemui.shade.data.repository;

import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.shade.display.ShadeDisplayPolicy;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import java.util.Iterator;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
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
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    ShadeDisplayPolicy shadeDisplayPolicy;
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
                        GlobalSettings globalSettings = this.$globalSettings$inlined;
                        String string = globalSettings.getString("shade_display_awareness");
                        Iterator it = this.$policies$inlined.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                shadeDisplayPolicy = this.$defaultPolicy$inlined;
                                globalSettings.putString("shade_display_awareness", shadeDisplayPolicy.getName());
                                break;
                            }
                            shadeDisplayPolicy = (ShadeDisplayPolicy) it.next();
                            if (Intrinsics.areEqual(shadeDisplayPolicy.getName(), string)) {
                                break;
                            }
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(shadeDisplayPolicy, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, globalSettings, set, shadeDisplayPolicy), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowDistinctUntilChanged, coroutineScope, SharingStarted.Companion.Eagerly, shadeDisplayPolicy);
        this.policy = readonlyStateFlowStateIn;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.transformLatest(readonlyStateFlowStateIn, new ShadeDisplaysRepositoryImpl$special$$inlined$flatMapLatest$1(null)), ((DisplayRepositoryImpl) displayRepository).displayRepositoryFromLib.getDisplayIds(), new ShadeDisplaysRepositoryImpl$displayIdFromPolicy$2(null));
        this.displayIdFromPolicy = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = z ? new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((KeyguardRepositoryImpl) keyguardRepository).isKeyguardShowing, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new ShadeDisplaysRepositoryImpl$keyguardAwareDisplayPolicy$1(null)) : flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.keyguardAwareDisplayPolicy = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.pendingDisplayId = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(0);
        this._committedDisplayId = stateFlowImplMutableStateFlow;
        this.displayId = stateFlowImplMutableStateFlow;
    }
}
