package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import android.os.Bundle;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel;
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
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class DemoModeMobileConnectionDataSource {
    public final DemoModeMobileConnectionDataSource$special$$inlined$map$1 _mobileCommands;
    public final ReadonlySharedFlow mobileEvents;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public DemoModeMobileConnectionDataSource(DemoModeController demoModeController, CoroutineScope coroutineScope) {
        final Flow flowDemoFlowForCommand = demoModeController.demoFlowForCommand();
        ?? r0 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DemoModeMobileConnectionDataSource this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DemoModeMobileConnectionDataSource demoModeMobileConnectionDataSource) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = demoModeMobileConnectionDataSource;
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
                /* JADX WARN: Removed duplicated region for block: B:117:0x01a9  */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                /* JADX WARN: Removed duplicated region for block: B:86:0x0139  */
                /* JADX WARN: Type inference failed for: r4v5, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel$MobileDisabled] */
                /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel$Mobile] */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup;
                    int i;
                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2;
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
                        this.this$0.getClass();
                        String string = bundle.getString("mobile");
                        if (string != null) {
                            if (string.equals("show")) {
                                String string2 = bundle.getString(ActionResults.RESULT_SET_VOLUME_SUCCESS);
                                Integer numValueOf = string2 != null ? Integer.valueOf(Integer.parseInt(string2)) : null;
                                String string3 = bundle.getString("datatype");
                                if (string3 != null) {
                                    switch (string3.hashCode()) {
                                        case 101:
                                            if (!string3.equals("e")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                break;
                                            } else {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.E;
                                                break;
                                            }
                                        case 103:
                                            if (string3.equals("g")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.G;
                                                break;
                                            }
                                            break;
                                        case 104:
                                            if (string3.equals("h")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.H;
                                                break;
                                            }
                                            break;
                                        case 1639:
                                            if (string3.equals("1x")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.ONE_X;
                                                break;
                                            }
                                            break;
                                        case 1684:
                                            if (string3.equals("3g")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.THREE_G;
                                                break;
                                            }
                                            break;
                                        case 1715:
                                            if (string3.equals("4g")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.FOUR_G;
                                                break;
                                            }
                                            break;
                                        case 1746:
                                            if (string3.equals("5g")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.NR_5G;
                                                break;
                                            }
                                            break;
                                        case 3267:
                                            if (string3.equals("h+")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.H_PLUS;
                                                break;
                                            }
                                            break;
                                        case 53208:
                                            if (string3.equals("4g+")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.FOUR_G_PLUS;
                                                break;
                                            }
                                            break;
                                        case 54169:
                                            if (string3.equals("5g+")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.NR_5G_PLUS;
                                                break;
                                            }
                                            break;
                                        case 54227:
                                            if (string3.equals("5ge")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.LTE_CA_5G_E;
                                                break;
                                            }
                                            break;
                                        case 99470:
                                            if (string3.equals("dis")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.DATA_DISABLED;
                                                break;
                                            }
                                            break;
                                        case 107485:
                                            if (string3.equals("lte")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.LTE;
                                                break;
                                            }
                                            break;
                                        case 109267:
                                            if (string3.equals("not")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.NOT_DEFAULT_DATA;
                                                break;
                                            }
                                            break;
                                        case 3332078:
                                            if (string3.equals("lte+")) {
                                                signalIcon$MobileIconGroup2 = TelephonyIcons.LTE_PLUS;
                                                break;
                                            }
                                            break;
                                    }
                                    signalIcon$MobileIconGroup = signalIcon$MobileIconGroup2;
                                } else {
                                    signalIcon$MobileIconGroup = null;
                                }
                                String string4 = bundle.getString("slot");
                                Integer numValueOf2 = string4 != null ? Integer.valueOf(Integer.parseInt(string4)) : null;
                                String string5 = bundle.getString("carrierid");
                                Integer numValueOf3 = string5 != null ? Integer.valueOf(Integer.parseInt(string5)) : null;
                                boolean z = Boolean.parseBoolean(bundle.getString("inflate"));
                                String string6 = bundle.getString("activity");
                                if (string6 != null) {
                                    int iHashCode = string6.hashCode();
                                    if (iHashCode == 3365) {
                                        if (string6.equals("in")) {
                                            i = 1;
                                        }
                                        mobileDisabled = Integer.valueOf(i);
                                    } else if (iHashCode != 110414) {
                                        i = (iHashCode == 100357129 && string6.equals("inout")) ? 3 : 0;
                                        mobileDisabled = Integer.valueOf(i);
                                    } else {
                                        if (string6.equals("out")) {
                                            i = 2;
                                        }
                                        mobileDisabled = Integer.valueOf(i);
                                    }
                                }
                                Integer num = mobileDisabled;
                                boolean zAreEqual = Intrinsics.areEqual(bundle.getString("carriernetworkchange"), "show");
                                boolean zAreEqual2 = Intrinsics.areEqual(bundle.getString("roam"), "show");
                                String string7 = bundle.getString("networkname");
                                if (string7 == null) {
                                    string7 = "demo mode";
                                }
                                mobileDisabled = new FakeNetworkEventModel.Mobile(numValueOf, signalIcon$MobileIconGroup, numValueOf2, numValueOf3, z, num, zAreEqual, zAreEqual2, string7, Boolean.parseBoolean(bundle.getString("slice")), Boolean.parseBoolean(bundle.getString("ntn")));
                            } else {
                                String string8 = bundle.getString("slot");
                                mobileDisabled = new FakeNetworkEventModel.MobileDisabled(string8 != null ? Integer.valueOf(Integer.parseInt(string8)) : null);
                            }
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(mobileDisabled, anonymousClass1) == coroutineSingletons) {
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
        this._mobileCommands = r0;
        this.mobileEvents = FlowKt.shareIn(r0, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
    }
}
