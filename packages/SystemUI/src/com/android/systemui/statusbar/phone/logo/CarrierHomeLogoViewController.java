package com.android.systemui.statusbar.phone.logo;

import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimType;
import com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.IntRange;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class CarrierHomeLogoViewController extends ViewController implements Dumpable, ConfigurationController.ConfigurationListener {
    public final CarrierConfigTracker carrierConfigTracker;
    public final CarrierInfraMediator carrierInfraMediator;
    public final CarrierLogoVisibilityManager carrierLogoVisibilityManager;
    public final ConfigurationController configurationController;
    public final DarkIconDispatcher darkIconDispatcher;
    public final CarrierHomeLogoViewController$defaultDataListener$1 defaultDataListener;
    public final DeviceProvisionedController deviceProvisionedController;
    public final CarrierHomeLogoViewController$deviceProvisionedListener$1 deviceProvisionedListener;
    public final DumpManager dumpManager;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final CarrierLogoView logoView;
    public final CarrierHomeLogoViewController$quickStarListener$1 quickStarListener;
    public final CarrierHomeLogoViewController$special$$inlined$map$1 serviceStateChanged;
    private final SettingsHelper settingsHelper;
    private final SettingsHelper.OnChangedCallback settingsListener;
    public final SimCardInfoUtil simCardInfoUtil;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 simStateChanged;
    public final SlimIndicatorViewMediator slimIndicatorViewMediator;
    public final int slotId;
    public final Flow spnUpdated;
    public final SubscriptionManager subscriptionManager;
    public boolean userSetup;
    public final LinkedList visibilityHistory;

    public final class Factory {
        public final BroadcastDispatcher broadcastDispatcher;
        public final CarrierConfigTracker carrierConfigTracker;
        public final CarrierInfraMediator carrierInfraMediator;
        public final ConfigurationController configurationController;
        public final DarkIconDispatcher darkIconDispatcher;
        public final DeviceProvisionedController deviceProvisionedController;
        public final DumpManager dumpManager;
        public final IndicatorScaleGardener indicatorScaleGardener;
        private final SettingsHelper settingsHelper;
        public final SimCardInfoUtil simCardInfoUtil;
        public final SlimIndicatorViewMediator slimIndicatorViewMediator;
        public final SubscriptionManager subscriptionManager;
        public final TelephonyManager telephonyManager;

        public Factory(CarrierInfraMediator carrierInfraMediator, DarkIconDispatcher darkIconDispatcher, DumpManager dumpManager, SettingsHelper settingsHelper, SlimIndicatorViewMediator slimIndicatorViewMediator, SimCardInfoUtil simCardInfoUtil, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener, BroadcastDispatcher broadcastDispatcher, SubscriptionManager subscriptionManager, TelephonyManager telephonyManager, CarrierConfigTracker carrierConfigTracker, DeviceProvisionedController deviceProvisionedController) {
            this.carrierInfraMediator = carrierInfraMediator;
            this.darkIconDispatcher = darkIconDispatcher;
            this.dumpManager = dumpManager;
            this.settingsHelper = settingsHelper;
            this.slimIndicatorViewMediator = slimIndicatorViewMediator;
            this.simCardInfoUtil = simCardInfoUtil;
            this.configurationController = configurationController;
            this.indicatorScaleGardener = indicatorScaleGardener;
            this.broadcastDispatcher = broadcastDispatcher;
            this.subscriptionManager = subscriptionManager;
            this.telephonyManager = telephonyManager;
            this.carrierConfigTracker = carrierConfigTracker;
            this.deviceProvisionedController = deviceProvisionedController;
        }

        public final CarrierHomeLogoViewController create(View view, int i) {
            SettingsHelper settingsHelper = this.settingsHelper;
            CarrierLogoVisibilityManager carrierLogoVisibilityManager = new CarrierLogoVisibilityManager(this.carrierInfraMediator, CarrierInfraMediator.Conditions.CARRIER_LOGO_ON_HOME_SCREEN, this.simCardInfoUtil, this.telephonyManager);
            CarrierLogoView carrierLogoView = (CarrierLogoView) view.findViewById(R.id.carrier_logo);
            SubscriptionManager subscriptionManager = this.subscriptionManager;
            return new CarrierHomeLogoViewController(view, this.broadcastDispatcher, this.dumpManager, settingsHelper, this.configurationController, carrierLogoVisibilityManager, i, this.carrierInfraMediator, this.darkIconDispatcher, this.slimIndicatorViewMediator, carrierLogoView, this.indicatorScaleGardener, subscriptionManager, this.simCardInfoUtil, this.carrierConfigTracker, this.deviceProvisionedController);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$onViewAttached$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$onViewAttached$1$1, reason: invalid class name and collision with other inner class name */
        final class C05371 extends SuspendLambda implements Function2 {
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ CarrierHomeLogoViewController this$0;

            /* renamed from: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$onViewAttached$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C05381 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ CarrierHomeLogoViewController this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C05381(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = carrierHomeLogoViewController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C05381(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C05381) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final CarrierHomeLogoViewController carrierHomeLogoViewController = this.this$0;
                        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = carrierHomeLogoViewController.simStateChanged;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController.onViewAttached.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                CarrierHomeLogoViewController carrierHomeLogoViewController2 = carrierHomeLogoViewController;
                                CarrierHomeLogoViewController.access$updateSimTypes(carrierHomeLogoViewController2);
                                carrierHomeLogoViewController2.updateCarrierLogoVisibility();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

            /* renamed from: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$onViewAttached$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ CarrierHomeLogoViewController this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = carrierHomeLogoViewController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final CarrierHomeLogoViewController carrierHomeLogoViewController = this.this$0;
                        CarrierHomeLogoViewController$special$$inlined$map$1 carrierHomeLogoViewController$special$$inlined$map$1 = carrierHomeLogoViewController.serviceStateChanged;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController.onViewAttached.1.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                CarrierHomeLogoViewController carrierHomeLogoViewController2 = carrierHomeLogoViewController;
                                carrierHomeLogoViewController2.getClass();
                                boolean z = false;
                                CarrierInfraMediator carrierInfraMediator = carrierHomeLogoViewController2.carrierInfraMediator;
                                int i2 = carrierHomeLogoViewController2.slotId;
                                boolean zAreEqual = Intrinsics.areEqual(carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING, i2, new Object[0]), "ORANGE");
                                CarrierLogoVisibilityManager carrierLogoVisibilityManager = carrierHomeLogoViewController2.carrierLogoVisibilityManager;
                                if (!zAreEqual) {
                                    List<SubscriptionInfo> completeActiveSubscriptionInfoList = carrierHomeLogoViewController2.subscriptionManager.getCompleteActiveSubscriptionInfoList();
                                    ArrayList arrayList = new ArrayList();
                                    for (Object obj3 : completeActiveSubscriptionInfoList) {
                                        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj3;
                                        Object obj4 = carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING, i2, new Object[0]);
                                        boolean zAreEqual2 = Intrinsics.areEqual(obj4, "SKT");
                                        SimCardInfoUtil simCardInfoUtil = carrierHomeLogoViewController2.simCardInfoUtil;
                                        if (zAreEqual2) {
                                            if (SimType.SKT == simCardInfoUtil.getSimCardInfo(subscriptionInfo.getSubscriptionId())) {
                                                arrayList.add(obj3);
                                            }
                                        } else if (Intrinsics.areEqual(obj4, "KTT")) {
                                            if (SimType.KT == simCardInfoUtil.getSimCardInfo(subscriptionInfo.getSubscriptionId())) {
                                                arrayList.add(obj3);
                                            }
                                        } else if (Intrinsics.areEqual(obj4, "LGT") && SimType.LGT == simCardInfoUtil.getSimCardInfo(subscriptionInfo.getSubscriptionId())) {
                                            arrayList.add(obj3);
                                        }
                                    }
                                    if (!arrayList.isEmpty()) {
                                        ListIterator listIterator = arrayList.listIterator();
                                        while (listIterator.hasNext()) {
                                            ServiceStateModel serviceStateModel = (ServiceStateModel) carrierLogoVisibilityManager.serviceStateHash.get(Integer.valueOf(SubscriptionManager.getSlotIndex(((SubscriptionInfo) listIterator.next()).getSubscriptionId())));
                                            boolean z2 = (serviceStateModel == null || !serviceStateModel.connected || serviceStateModel.roaming) ? false : true;
                                            carrierLogoVisibilityManager.networkCondition = z2;
                                            if (z2) {
                                                break;
                                            }
                                        }
                                    } else {
                                        carrierLogoVisibilityManager.networkCondition = false;
                                    }
                                } else {
                                    ServiceStateModel serviceStateModel2 = (ServiceStateModel) carrierLogoVisibilityManager.serviceStateHash.get(Integer.valueOf(SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultSubscriptionId())));
                                    if (serviceStateModel2 != null) {
                                        if (serviceStateModel2.connected && !serviceStateModel2.roaming) {
                                            z = true;
                                        }
                                        carrierLogoVisibilityManager.networkCondition = z;
                                    }
                                }
                                carrierHomeLogoViewController2.updateCarrierLogoVisibility();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (carrierHomeLogoViewController$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

            /* renamed from: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$onViewAttached$1$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ CarrierHomeLogoViewController this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = carrierHomeLogoViewController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final CarrierHomeLogoViewController carrierHomeLogoViewController = this.this$0;
                        Flow flow = carrierHomeLogoViewController.spnUpdated;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController.onViewAttached.1.1.3.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                CarrierHomeLogoViewController carrierHomeLogoViewController2 = carrierHomeLogoViewController;
                                CarrierHomeLogoViewController.access$updateSimTypes(carrierHomeLogoViewController2);
                                carrierHomeLogoViewController2.updateCarrierLogoVisibility();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flow.collect(flowCollector, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C05371(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = carrierHomeLogoViewController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C05371 c05371 = new C05371(this.this$0, continuation);
                c05371.L$0 = obj;
                return c05371;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05371) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, null, null, new C05381(this.this$0, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 3);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = CarrierHomeLogoViewController.this.new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                C05371 c05371 = new C05371(CarrierHomeLogoViewController.this, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c05371, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Type inference failed for: r2v15, types: [com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$deviceProvisionedListener$1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$defaultDataListener$1] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$quickStarListener$1] */
    public CarrierHomeLogoViewController(View view, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, SettingsHelper settingsHelper, ConfigurationController configurationController, CarrierLogoVisibilityManager carrierLogoVisibilityManager, int i, CarrierInfraMediator carrierInfraMediator, DarkIconDispatcher darkIconDispatcher, SlimIndicatorViewMediator slimIndicatorViewMediator, CarrierLogoView carrierLogoView, IndicatorScaleGardener indicatorScaleGardener, SubscriptionManager subscriptionManager, SimCardInfoUtil simCardInfoUtil, CarrierConfigTracker carrierConfigTracker, DeviceProvisionedController deviceProvisionedController) {
        super(view);
        this.dumpManager = dumpManager;
        this.settingsHelper = settingsHelper;
        this.configurationController = configurationController;
        this.carrierLogoVisibilityManager = carrierLogoVisibilityManager;
        this.slotId = i;
        this.carrierInfraMediator = carrierInfraMediator;
        this.darkIconDispatcher = darkIconDispatcher;
        this.slimIndicatorViewMediator = slimIndicatorViewMediator;
        this.logoView = carrierLogoView;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.subscriptionManager = subscriptionManager;
        this.simCardInfoUtil = simCardInfoUtil;
        this.carrierConfigTracker = carrierConfigTracker;
        this.deviceProvisionedController = deviceProvisionedController;
        this.visibilityHistory = new LinkedList();
        this.settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$settingsListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                CarrierHomeLogoViewController carrierHomeLogoViewController = this.this$0;
                carrierHomeLogoViewController.carrierLogoVisibilityManager.settingEnabled = carrierHomeLogoViewController.settingsHelper.isCarrierLogoEnabled();
                carrierHomeLogoViewController.updateCarrierLogoVisibility();
                EmergencyButtonController$$ExternalSyntheticOutline0.m("Carrier logo setting changed=", "CarrierHomeLogoViewController", carrierHomeLogoViewController.settingsHelper.isCarrierLogoEnabled());
            }
        };
        this.defaultDataListener = new CarrierConfigTracker.DefaultDataSubscriptionChangedListener() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$defaultDataListener$1
            @Override // com.android.systemui.util.CarrierConfigTracker.DefaultDataSubscriptionChangedListener
            public final void onDefaultSubscriptionChanged(int i2) {
                CarrierHomeLogoViewController carrierHomeLogoViewController = this.this$0;
                CarrierLogoVisibilityManager carrierLogoVisibilityManager2 = carrierHomeLogoViewController.carrierLogoVisibilityManager;
                int slotIndex = SubscriptionManager.getSlotIndex(i2);
                carrierLogoVisibilityManager2.defaultSubscriptionSlotId = slotIndex;
                CarrierInfraMediator carrierInfraMediator2 = carrierLogoVisibilityManager2.carrierInfraMediator;
                CarrierInfraMediator.Conditions conditions = carrierLogoVisibilityManager2.featureName;
                boolean zIsEnabled = carrierInfraMediator2.isEnabled(conditions, slotIndex, new Object[0]);
                carrierLogoVisibilityManager2.featureEnabled = zIsEnabled;
                Log.d("CarrierLogoVisibilityManager", "Default data subscription is changed to slot" + carrierLogoVisibilityManager2.defaultSubscriptionSlotId + " " + conditions + "=" + zIsEnabled);
                carrierHomeLogoViewController.updateCarrierLogoVisibility();
            }
        };
        this.userSetup = ((DeviceProvisionedControllerImpl) deviceProvisionedController).isCurrentUserSetup();
        this.quickStarListener = new SlimIndicatorViewSubscriber() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$quickStarListener$1
            @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
            public final void updateQuickStarStyle() {
                CarrierHomeLogoViewController carrierHomeLogoViewController = this.this$0;
                CarrierLogoVisibilityManager carrierLogoVisibilityManager2 = carrierHomeLogoViewController.carrierLogoVisibilityManager;
                SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) carrierHomeLogoViewController.slimIndicatorViewMediator;
                carrierLogoVisibilityManager2.quickStarEnabled = !(slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected && slimIndicatorViewMediatorImpl.mCarrierCrew.mIsHomeCarrierDisabled == 1);
                carrierHomeLogoViewController.updateCarrierLogoVisibility();
            }
        };
        this.simStateChanged = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CarrierHomeLogoViewController$simStateChanged$1(this, null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.SIM_STATE_CHANGED"), null, 14));
        this.serviceStateChanged = new CarrierHomeLogoViewController$special$$inlined$map$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.SERVICE_STATE"), null, new CarrierHomeLogoViewController$$ExternalSyntheticLambda0(), 14), this);
        this.spnUpdated = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.telephony.action.SERVICE_PROVIDERS_UPDATED"), null, 14);
        this.deviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$deviceProvisionedListener$1
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSetupChanged() {
                CarrierHomeLogoViewController carrierHomeLogoViewController = this.this$0;
                carrierHomeLogoViewController.userSetup = ((DeviceProvisionedControllerImpl) carrierHomeLogoViewController.deviceProvisionedController).isCurrentUserSetup();
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void access$updateSimTypes(CarrierHomeLogoViewController carrierHomeLogoViewController) {
        boolean zAreEqual;
        boolean z;
        List<SubscriptionInfo> completeActiveSubscriptionInfoList = carrierHomeLogoViewController.subscriptionManager.getCompleteActiveSubscriptionInfoList();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(completeActiveSubscriptionInfoList, 10));
        Iterator<T> it = completeActiveSubscriptionInfoList.iterator();
        while (it.hasNext()) {
            arrayList.add(carrierHomeLogoViewController.simCardInfoUtil.getSimCardInfo(((SubscriptionInfo) it.next()).getSubscriptionId()));
        }
        CarrierLogoVisibilityManager carrierLogoVisibilityManager = carrierHomeLogoViewController.carrierLogoVisibilityManager;
        carrierLogoVisibilityManager.simTypes = arrayList;
        boolean z2 = false;
        if (!arrayList.isEmpty()) {
            int size = arrayList.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                Object obj = arrayList.get(i);
                i++;
                SimType simType = (SimType) obj;
                Object obj2 = carrierLogoVisibilityManager.carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING, carrierLogoVisibilityManager.defaultSubscriptionSlotId, new Object[0]);
                if (Intrinsics.areEqual(obj2, "SKT")) {
                    z = simType == SimType.SKT;
                } else if (Intrinsics.areEqual(obj2, "KTT")) {
                    if (simType == SimType.KT) {
                    }
                } else if (!Intrinsics.areEqual(obj2, "LGT")) {
                    if (Intrinsics.areEqual(obj2, "ORANGE") && simType == SimType.ORANGE) {
                        int i2 = carrierLogoVisibilityManager.defaultSubscriptionSlotId;
                        SimCardInfoUtil simCardInfoUtil = carrierLogoVisibilityManager.simCardInfoUtil;
                        String simOperatorNameForPhone = simCardInfoUtil.telephonyManager.getSimOperatorNameForPhone(i2);
                        if (Intrinsics.areEqual(simOperatorNameForPhone, "Orange F")) {
                            String simOperatorNumericForPhone = simCardInfoUtil.telephonyManager.getSimOperatorNumericForPhone(i2);
                            String networkOperatorForPhone = simCardInfoUtil.telephonyManager.getNetworkOperatorForPhone(i2);
                            Log.d("SimCardInfoUtil", "numeric information, sim=" + simOperatorNumericForPhone + " plmn=" + networkOperatorForPhone);
                            if (simOperatorNumericForPhone.length() >= 3 && networkOperatorForPhone.length() >= 3) {
                                IntRange intRange = new IntRange(0, 2);
                                String strSubstring = simOperatorNumericForPhone.substring(intRange.first, intRange.last + 1);
                                IntRange intRange2 = new IntRange(0, 2);
                                zAreEqual = Intrinsics.areEqual(strSubstring, networkOperatorForPhone.substring(intRange2.first, intRange2.last + 1));
                            }
                            if (!zAreEqual) {
                            }
                        } else {
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("spn isn't matched with Orange=", simOperatorNameForPhone, "SimCardInfoUtil");
                        }
                        zAreEqual = false;
                        if (!zAreEqual) {
                        }
                    }
                } else if (simType == SimType.LGT) {
                }
                if (z) {
                    z2 = true;
                    break;
                }
            }
        }
        carrierLogoVisibilityManager.matchedSim = z2;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(" userSetup=" + this.userSetup);
        printWriter.println();
        CarrierLogoVisibilityManager carrierLogoVisibilityManager = this.carrierLogoVisibilityManager;
        carrierLogoVisibilityManager.getClass();
        printWriter.println("Last visibility state:");
        printWriter.println(carrierLogoVisibilityManager.toString());
        Iterator it = this.visibilityHistory.iterator();
        while (it.hasNext()) {
            printWriter.println((String) it.next());
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() throws Resources.NotFoundException {
        float f = this.indicatorScaleGardener.getLatestScaleModel(getContext()).ratio;
        CarrierLogoView carrierLogoView = this.logoView;
        if (carrierLogoView != null) {
            ViewGroup.LayoutParams layoutParams = carrierLogoView.getLayoutParams();
            layoutParams.width = MathKt__MathJVMKt.roundToInt(carrierLogoView.getDrawable().getIntrinsicWidth() * f);
            layoutParams.height = MathKt__MathJVMKt.roundToInt(carrierLogoView.getDrawable().getIntrinsicHeight() * f);
            carrierLogoView.setLayoutParams(layoutParams);
        }
        this.mView.setPaddingRelative(0, 0, MathKt__MathJVMKt.roundToInt(getResources().getDimensionPixelSize(R.dimen.status_carrier_logo_margin_end) * f), 0);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDisplayDeviceTypeChanged() throws Resources.NotFoundException {
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            onDensityOrFontScaleChanged();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b8  */
    @Override // com.android.systemui.util.ViewController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onViewAttached() {
        String string;
        this.carrierConfigTracker.addDefaultDataSubscriptionChangedListener(this.defaultDataListener);
        boolean zIsCarrierLogoEnabled = this.settingsHelper.isCarrierLogoEnabled();
        CarrierLogoVisibilityManager carrierLogoVisibilityManager = this.carrierLogoVisibilityManager;
        carrierLogoVisibilityManager.settingEnabled = zIsCarrierLogoEnabled;
        boolean z = false;
        this.settingsHelper.registerCallback(this.settingsListener, Settings.System.getUriFor(SettingsHelper.INDEX_INDICATOR_SHOW_NETWORK_INFORMATION));
        CarrierLogoView carrierLogoView = this.logoView;
        if (carrierLogoView != null) {
            CarrierInfraMediator.Values values = CarrierInfraMediator.Values.ICON_BRANDING;
            CarrierInfraMediator carrierInfraMediator = this.carrierInfraMediator;
            int i = this.slotId;
            Object obj = carrierInfraMediator.get(values, i, new Object[0]);
            carrierLogoView.setImageResource(Intrinsics.areEqual(obj, "SKT") ? R.drawable.stat_notify_operator_logo_skt : Intrinsics.areEqual(obj, "KTT") ? R.drawable.stat_notify_operator_logo_kt : Intrinsics.areEqual(obj, "LGT") ? R.drawable.stat_notify_operator_logo_lgu : Intrinsics.areEqual(obj, "ORANGE") ? R.drawable.stat_notify_operator_logo_org : 0);
            String str = (String) carrierInfraMediator.get(values, i, new Object[0]);
            switch (str.hashCode()) {
                case -1955522002:
                    if (!str.equals("ORANGE")) {
                        string = "";
                        break;
                    } else {
                        string = "Orange F";
                        break;
                    }
                case 74763:
                    if (str.equals("KTT")) {
                        string = getContext().getString(R.string.status_bar_carrier_logo_kt_tts);
                        break;
                    }
                    break;
                case 75321:
                    if (str.equals("LGT")) {
                        string = getContext().getString(R.string.status_bar_carrier_logo_lgu_tts);
                        break;
                    }
                    break;
                case 82172:
                    if (str.equals("SKT")) {
                        string = getContext().getString(R.string.status_bar_carrier_logo_skt_tts);
                        break;
                    }
                    break;
            }
            carrierLogoView.setContentDescription(string);
            this.darkIconDispatcher.addDarkReceiver(carrierLogoView);
        }
        ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).addCallback(this.deviceProvisionedListener);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this);
        SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator;
        if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected && slimIndicatorViewMediatorImpl.mCarrierCrew.mIsHomeCarrierDisabled == 1) {
            z = true;
        }
        carrierLogoVisibilityManager.quickStarEnabled = !z;
        slimIndicatorViewMediatorImpl.registerSubscriber("CarrierHomeLogoViewController", this.quickStarListener);
        this.dumpManager.registerNormalDumpable("CarrierHomeLogoViewController", this);
        RepeatWhenAttachedKt.repeatWhenAttached(this.mView, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(null));
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.carrierConfigTracker.removeDataSubscriptionChangedListener(this.defaultDataListener);
        this.settingsHelper.unregisterCallback(this.settingsListener);
        CarrierLogoView carrierLogoView = this.logoView;
        carrierLogoView.getClass();
        this.darkIconDispatcher.removeDarkReceiver(carrierLogoView);
        ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).removeCallback(this.deviceProvisionedListener);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this);
        ((SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator).unregisterSubscriber("CarrierHomeLogoViewController");
        this.dumpManager.unregisterDumpable("CarrierHomeLogoViewController");
    }

    public final void updateCarrierLogoVisibility() {
        if (DeviceState.isTestModeIndicatorGarden()) {
            this.mView.setVisibility(0);
            return;
        }
        CarrierLogoVisibilityManager carrierLogoVisibilityManager = this.carrierLogoVisibilityManager;
        if (carrierLogoVisibilityManager.getVisible() != this.mView.getVisibility()) {
            this.mView.setVisibility(carrierLogoVisibilityManager.getVisible());
            if (this.visibilityHistory.size() > 10) {
                this.visibilityHistory.poll();
            }
            this.visibilityHistory.offer(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(Long.valueOf(System.currentTimeMillis())) + " " + carrierLogoVisibilityManager);
            ListPopupWindow$$ExternalSyntheticOutline0.m(carrierLogoVisibilityManager.getVisible(), "Visibility changed=", "CarrierHomeLogoViewController");
        }
    }
}
