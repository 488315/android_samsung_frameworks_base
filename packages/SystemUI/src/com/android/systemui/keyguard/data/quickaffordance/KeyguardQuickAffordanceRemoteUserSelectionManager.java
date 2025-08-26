package com.android.systemui.keyguard.data.quickaffordance;

import android.os.UserHandle;
import com.android.app.tracing.FlowTracing;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.customization.data.content.CustomizationProviderClient;
import com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* loaded from: classes2.dex */
public final class KeyguardQuickAffordanceRemoteUserSelectionManager implements KeyguardQuickAffordanceSelectionManager {
    public final ReadonlyStateFlow _selections;
    public final KeyguardQuickAffordanceProviderClientFactory clientFactory;
    public final ReadonlyStateFlow clientOrNull;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow selections;
    public final UserHandle userHandle;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 userId;
    public final UserTracker userTracker;

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

    public KeyguardQuickAffordanceRemoteUserSelectionManager(CoroutineScope coroutineScope, UserTracker userTracker, KeyguardQuickAffordanceProviderClientFactory keyguardQuickAffordanceProviderClientFactory, UserHandle userHandle) {
        this.scope = coroutineScope;
        this.userTracker = userTracker;
        this.clientFactory = keyguardQuickAffordanceProviderClientFactory;
        this.userHandle = userHandle;
        FlowTracing flowTracing = FlowTracing.INSTANCE;
        KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1 keyguardQuickAffordanceRemoteUserSelectionManager$userId$1 = new KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1(this, null);
        flowTracing.getClass();
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1TracedConflatedCallbackFlow = FlowTracing.tracedConflatedCallbackFlow("userId", keyguardQuickAffordanceRemoteUserSelectionManager$userId$1);
        this.userId = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1TracedConflatedCallbackFlow;
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1TracedConflatedCallbackFlow);
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardQuickAffordanceRemoteUserSelectionManager this$0;

                /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardQuickAffordanceRemoteUserSelectionManager;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    CustomizationProviderClientImpl customizationProviderClientImpl;
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
                        int iIntValue = ((Number) obj).intValue();
                        KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager = this.this$0;
                        if (!keyguardQuickAffordanceRemoteUserSelectionManager.userHandle.isSystem() || keyguardQuickAffordanceRemoteUserSelectionManager.userHandle.getIdentifier() == iIntValue) {
                            customizationProviderClientImpl = null;
                        } else {
                            KeyguardQuickAffordanceProviderClientFactoryImpl keyguardQuickAffordanceProviderClientFactoryImpl = (KeyguardQuickAffordanceProviderClientFactoryImpl) keyguardQuickAffordanceRemoteUserSelectionManager.clientFactory;
                            keyguardQuickAffordanceProviderClientFactoryImpl.getClass();
                            customizationProviderClientImpl = new CustomizationProviderClientImpl(((UserTrackerImpl) keyguardQuickAffordanceProviderClientFactoryImpl.userTracker).getUserContext(), keyguardQuickAffordanceProviderClientFactoryImpl.backgroundDispatcher);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(customizationProviderClientImpl, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, null);
        this.clientOrNull = readonlyStateFlowStateIn;
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(FlowKt.transformLatest(readonlyStateFlowStateIn, new KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1(null)), coroutineScope, startedEagerly, MapsKt__MapsKt.emptyMap());
        this._selections = readonlyStateFlowStateIn2;
        this.selections = readonlyStateFlowStateIn2;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public final Map getSelections() {
        return (Map) this._selections.$$delegate_0.getValue();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public final void setSelections(String str, List list) {
        CustomizationProviderClient customizationProviderClient = (CustomizationProviderClient) this.clientOrNull.$$delegate_0.getValue();
        if (customizationProviderClient != null) {
            CoroutineTracingKt.launchTraced$default(this.scope, null, null, new KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1(customizationProviderClient, str, list, null), 7);
        }
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    /* renamed from: getSelections */
    public final Flow mo2614getSelections() {
        return this.selections;
    }
}
