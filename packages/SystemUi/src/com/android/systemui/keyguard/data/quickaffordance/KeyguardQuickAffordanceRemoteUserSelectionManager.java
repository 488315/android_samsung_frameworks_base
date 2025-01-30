package com.android.systemui.keyguard.data.quickaffordance;

import android.os.UserHandle;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.customization.data.content.CustomizationProviderClient;
import com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardQuickAffordanceRemoteUserSelectionManager implements KeyguardQuickAffordanceSelectionManager {
    public final ReadonlyStateFlow _selections;
    public final KeyguardQuickAffordanceProviderClientFactory clientFactory;
    public final ReadonlyStateFlow clientOrNull;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow selections;
    public final UserHandle userHandle;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1 keyguardQuickAffordanceRemoteUserSelectionManager$userId$1 = new KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1(this, null);
        conflatedCallbackFlow.getClass();
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(keyguardQuickAffordanceRemoteUserSelectionManager$userId$1));
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardQuickAffordanceRemoteUserSelectionManager this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1$2", m277f = "KeyguardQuickAffordanceRemoteUserSelectionManager.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardQuickAffordanceRemoteUserSelectionManager;
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
                    CustomizationProviderClientImpl customizationProviderClientImpl;
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
                                int intValue = ((Number) obj).intValue();
                                KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager = this.this$0;
                                if (!keyguardQuickAffordanceRemoteUserSelectionManager.userHandle.isSystem() || keyguardQuickAffordanceRemoteUserSelectionManager.userHandle.getIdentifier() == intValue) {
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
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, null);
        this.clientOrNull = stateIn;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new C1571x2bc755a1(null)), coroutineScope, startedEagerly, MapsKt__MapsKt.emptyMap());
        this._selections = stateIn2;
        this.selections = stateIn2;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    /* renamed from: getSelections */
    public final Flow mo1577getSelections() {
        return this.selections;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public final void setSelections(String str, List list) {
        CustomizationProviderClient customizationProviderClient = (CustomizationProviderClient) this.clientOrNull.getValue();
        if (customizationProviderClient != null) {
            BuildersKt.launch$default(this.scope, null, null, new C1573xbef10b67(customizationProviderClient, str, list, null), 3);
        }
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public final Map getSelections() {
        return (Map) this._selections.getValue();
    }
}
