package com.android.systemui.statusbar.pipeline.wifi.data.repository.demo;

import android.os.Bundle;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.sec.ims.settings.ImsProfile;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class DemoModeWifiDataSource {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DemoModeWifiDataSource$special$$inlined$map$1 _wifiCommands;
    public final ReadonlySharedFlow wifiEvents;

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

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public DemoModeWifiDataSource(DemoModeController demoModeController, CoroutineScope coroutineScope) {
        final Flow flowDemoFlowForCommand = demoModeController.demoFlowForCommand();
        ?? r0 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DemoModeWifiDataSource this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DemoModeWifiDataSource demoModeWifiDataSource) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = demoModeWifiDataSource;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
                java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
                	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
                	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
                	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
                	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
                 */
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:53:0x00df  */
                /* JADX WARN: Removed duplicated region for block: B:55:0x00e5  */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws NumberFormatException {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    WifiNetworkModel.HotspotDeviceType hotspotDeviceType;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i2 - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i3 = anonymousClass1.label;
                    if (i3 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Bundle bundle = (Bundle) obj;
                        int i4 = DemoModeWifiDataSource.$r8$clinit;
                        this.this$0.getClass();
                        String string = bundle.getString(ImsProfile.PDN_WIFI);
                        if (string != null) {
                            if (string.equals("show")) {
                                String string2 = bundle.getString(ActionResults.RESULT_SET_VOLUME_SUCCESS);
                                Integer numValueOf = string2 != null ? Integer.valueOf(Integer.parseInt(string2)) : null;
                                int activity = DemoModeWifiDataSource.toActivity(bundle.getString("activity"));
                                String string3 = bundle.getString("ssid");
                                boolean z = Boolean.parseBoolean(bundle.getString("fully"));
                                String string4 = bundle.getString("hotspot");
                                if (string4 != null) {
                                    switch (string4.hashCode()) {
                                        case -1109985830:
                                            if (!string4.equals("laptop")) {
                                                hotspotDeviceType = WifiNetworkModel.HotspotDeviceType.INVALID;
                                                break;
                                            } else {
                                                hotspotDeviceType = WifiNetworkModel.HotspotDeviceType.LAPTOP;
                                                break;
                                            }
                                        case -881377690:
                                            if (string4.equals("tablet")) {
                                                hotspotDeviceType = WifiNetworkModel.HotspotDeviceType.TABLET;
                                                break;
                                            }
                                            break;
                                        case -284840886:
                                            if (string4.equals("unknown")) {
                                                hotspotDeviceType = WifiNetworkModel.HotspotDeviceType.UNKNOWN;
                                                break;
                                            }
                                            break;
                                        case 3005871:
                                            if (string4.equals("auto")) {
                                                hotspotDeviceType = WifiNetworkModel.HotspotDeviceType.AUTO;
                                                break;
                                            }
                                            break;
                                        case 3387192:
                                            if (string4.equals(SignalSeverity.NONE)) {
                                                hotspotDeviceType = WifiNetworkModel.HotspotDeviceType.NONE;
                                                break;
                                            }
                                            break;
                                        case 106642798:
                                            if (string4.equals("phone")) {
                                                hotspotDeviceType = WifiNetworkModel.HotspotDeviceType.PHONE;
                                                break;
                                            }
                                            break;
                                        case 112903375:
                                            if (string4.equals("watch")) {
                                                hotspotDeviceType = WifiNetworkModel.HotspotDeviceType.WATCH;
                                                break;
                                            }
                                            break;
                                    }
                                    carrierMerged = new FakeWifiEventModel.Wifi(numValueOf, activity, string3, Boolean.valueOf(z), hotspotDeviceType);
                                }
                            } else if (string.equals("carriermerged")) {
                                String string5 = bundle.getString("slot");
                                int i5 = string5 != null ? Integer.parseInt(string5) : 10;
                                String string6 = bundle.getString(ActionResults.RESULT_SET_VOLUME_SUCCESS);
                                int i6 = string6 != null ? Integer.parseInt(string6) : 0;
                                String string7 = bundle.getString("numlevels");
                                if (string7 != null) {
                                    i = Integer.parseInt(string7);
                                } else {
                                    MobileConnectionRepository.Companion.getClass();
                                    i = MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS;
                                }
                                carrierMerged = new FakeWifiEventModel.CarrierMerged(i5, i6, i, DemoModeWifiDataSource.toActivity(bundle.getString("activity")));
                            } else {
                                carrierMerged = FakeWifiEventModel.WifiDisabled.INSTANCE;
                            }
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(carrierMerged, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i3 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowDemoFlowForCommand.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this._wifiCommands = r0;
        this.wifiEvents = FlowKt.shareIn(r0, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
    }

    public static int toActivity(String str) {
        if (str == null) {
            return 0;
        }
        int iHashCode = str.hashCode();
        return iHashCode != 3365 ? iHashCode != 110414 ? (iHashCode == 100357129 && str.equals("inout")) ? 3 : 0 : !str.equals("out") ? 0 : 2 : !str.equals("in") ? 0 : 1;
    }
}
