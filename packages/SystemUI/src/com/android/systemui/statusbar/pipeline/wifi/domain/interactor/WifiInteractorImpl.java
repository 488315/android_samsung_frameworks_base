package com.android.systemui.statusbar.pipeline.wifi.domain.interactor;

import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ImsRegStateUtil;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.ui.util.WifiSignalIconResource;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WifiInteractorImpl implements WifiInteractor {
    public final StateFlow activity;
    public final ReadonlyStateFlow areNetworksAvailable;
    public final StateFlow hideDuringMobileSwitching;
    public final StateFlow isDefault;
    public final ReadonlyStateFlow isDefaultValidated;
    public final StateFlow isEnabled;
    public final WifiInteractorImpl$special$$inlined$map$3 isForceHidden;
    public final WifiInteractorImpl$special$$inlined$map$1 ssid;
    public final StateFlow wifiConnectivityTestReported;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 wifiIconGroup;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 wifiNetwork;

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3] */
    public WifiInteractorImpl(ConnectivityRepository connectivityRepository, WifiRepository wifiRepository, CoroutineScope coroutineScope, ImsRegStateUtil imsRegStateUtil, WifiSignalIconResource wifiSignalIconResource, TableLogBuffer tableLogBuffer) {
        this.ssid = new WifiInteractorImpl$special$$inlined$map$1(wifiRepository.getWifiNetwork());
        this.isEnabled = wifiRepository.isWifiEnabled();
        this.isDefault = wifiRepository.isWifiDefault();
        ConnectivityRepositoryImpl connectivityRepositoryImpl = (ConnectivityRepositoryImpl) connectivityRepository;
        final ReadonlyStateFlow readonlyStateFlow = connectivityRepositoryImpl.defaultConnections;
        Flow logDiffsForTable = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L50
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel r5 = (com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel) r5
                        com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Wifi r6 = r5.wifi
                        boolean r6 = r6.isDefault
                        if (r6 == 0) goto L40
                        boolean r5 = r5.isValidated
                        if (r5 == 0) goto L40
                        r5 = r3
                        goto L41
                    L40:
                        r5 = 0
                    L41:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "", "isDefaultValidated", false);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(wifiRepository.getWifiNetwork(), wifiRepository.getReceivedInetCondition(), FlowKt.stateIn(logDiffsForTable, coroutineScope, WhileSubscribed$default, bool), new WifiInteractorImpl$wifiNetwork$1(null));
        this.wifiNetwork = combine;
        this.activity = wifiRepository.getWifiActivity();
        final ReadonlyStateFlow readonlyStateFlow2 = connectivityRepositoryImpl.forceHiddenSlots;
        this.isForceHidden = new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.Set r5 = (java.util.Set) r5
                        com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot r6 = com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot.WIFI
                        boolean r5 = r5.contains(r6)
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.areNetworksAvailable = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(combine, wifiRepository.getWifiScanResults(), new WifiInteractorImpl$areNetworksAvailable$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.wifiIconGroup = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(combine, imsRegStateUtil.ePDGConnected, new WifiInteractorImpl$wifiIconGroup$1(wifiSignalIconResource, null));
        this.hideDuringMobileSwitching = wifiRepository.getHideDuringMobileSwitching();
        this.wifiConnectivityTestReported = wifiRepository.getWifiConnectivityTestReported();
    }
}
