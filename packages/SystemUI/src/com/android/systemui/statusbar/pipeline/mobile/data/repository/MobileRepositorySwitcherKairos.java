package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.EventsInit;
import com.android.systemui.kairos.EventsKt;
import com.android.systemui.kairos.Incremental;
import com.android.systemui.kairos.IncrementalInit;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.StateKt$$ExternalSyntheticLambda7;
import com.android.systemui.kairos.StateKt$$ExternalSyntheticLambda8;
import com.android.systemui.kairos.SwitchKt;
import com.android.systemui.kairos.SwitchKt$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.SwitchKt$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.internal.ActivationResult;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.internal.IncrementalImpl;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.InitKt$constInit$1;
import com.android.systemui.kairos.internal.MapNode;
import com.android.systemui.kairos.internal.MuxLifecycle;
import com.android.systemui.kairos.internal.MuxLifecycleState;
import com.android.systemui.kairos.internal.MuxPromptActivator;
import com.android.systemui.kairos.internal.MuxPromptKt$switchPromptImplSingle$$inlined$mapImpl$1;
import com.android.systemui.kairos.internal.MuxPromptKt$switchPromptImplSingle$2;
import com.android.systemui.kairos.internal.MuxPromptKt$switchPromptImplSingle$switchPromptImpl$2$1;
import com.android.systemui.kairos.internal.NetworkScope;
import com.android.systemui.kairos.internal.NodeConnection;
import com.android.systemui.kairos.internal.Schedulable;
import com.android.systemui.kairos.internal.StateImpl;
import com.android.systemui.kairos.internal.store.Single;
import com.android.systemui.kairos.internal.store.SingletonMapK;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepositoryKairos;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileRepositorySwitcherKairos implements MobileConnectionsRepositoryKairos, KairosBuilder {
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final StateInit activeMobileDataRepository;
    public final StateInit activeMobileDataSubscriptionId;
    public final EventsInit activeSubChangedInGroupEvent;
    public final StateInit defaultConnectionIsValidated;
    public final StateInit defaultDataSubId;
    public final StateInit defaultDataSubRatConfig;
    public final StateInit defaultMobileIconGroup;
    public final StateInit defaultMobileIconMapping;
    public final DemoMobileConnectionsRepositoryKairos.Factory demoRepositoryFactory;
    public final StateInit hasCarrierMergedConnection;
    public final StateInit isAnySimSecure;
    public final State isDemoMode;
    public final StateInit isDeviceEmergencyCallCapable;
    public final StateInit isInEcmMode;
    public final IncrementalInit mobileConnectionsBySubId;
    public final StateInit mobileIsDefault;
    public final MobileConnectionsRepositoryKairosImpl realRepository;
    public final StateInit subscriptions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Module {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }

    public MobileRepositorySwitcherKairos(MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, DemoMobileConnectionsRepositoryKairos.Factory factory, final DemoModeController demoModeController) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.realRepository = mobileConnectionsRepositoryKairosImpl;
        this.demoRepositoryFactory = factory;
        final int i = 0;
        this.isDemoMode = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i) {
                    case 0:
                        DemoModeController demoModeController2 = (DemoModeController) demoModeController;
                        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new MobileRepositorySwitcherKairos$isDemoMode$1$1(demoModeController2, null));
                        demoModeController2.getClass();
                        Boolean bool = Boolean.FALSE;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return buildScopeImpl.stateScope.holdState(BuildScope.DefaultImpls.toEvents(buildScopeImpl, conflatedCallbackFlow), bool);
                    default:
                        final MobileRepositorySwitcherKairos mobileRepositorySwitcherKairos = (MobileRepositorySwitcherKairos) demoModeController;
                        return ((BuildScopeImpl) buildScope).mapLatestBuild(mobileRepositorySwitcherKairos.isDemoMode, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda17
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                BuildScope buildScope2 = (BuildScope) obj2;
                                boolean booleanValue = ((Boolean) obj3).booleanValue();
                                MobileRepositorySwitcherKairos mobileRepositorySwitcherKairos2 = MobileRepositorySwitcherKairos.this;
                                if (!booleanValue) {
                                    return mobileRepositorySwitcherKairos2.realRepository;
                                }
                                DemoMobileConnectionsRepositoryKairos create = new MobileRepositorySwitcherKairos$activeRepo$1$1$1(mobileRepositorySwitcherKairos2).this$0.demoRepositoryFactory.create();
                                create.activate(buildScope2);
                                return create;
                            }
                        });
                }
            }
        });
        final int i2 = 1;
        State buildState = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i2) {
                    case 0:
                        DemoModeController demoModeController2 = (DemoModeController) this;
                        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new MobileRepositorySwitcherKairos$isDemoMode$1$1(demoModeController2, null));
                        demoModeController2.getClass();
                        Boolean bool = Boolean.FALSE;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return buildScopeImpl.stateScope.holdState(BuildScope.DefaultImpls.toEvents(buildScopeImpl, conflatedCallbackFlow), bool);
                    default:
                        final MobileRepositorySwitcherKairos mobileRepositorySwitcherKairos = (MobileRepositorySwitcherKairos) this;
                        return ((BuildScopeImpl) buildScope).mapLatestBuild(mobileRepositorySwitcherKairos.isDemoMode, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda17
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                BuildScope buildScope2 = (BuildScope) obj2;
                                boolean booleanValue = ((Boolean) obj3).booleanValue();
                                MobileRepositorySwitcherKairos mobileRepositorySwitcherKairos2 = MobileRepositorySwitcherKairos.this;
                                if (!booleanValue) {
                                    return mobileRepositorySwitcherKairos2.realRepository;
                                }
                                DemoMobileConnectionsRepositoryKairos create = new MobileRepositorySwitcherKairos$activeRepo$1$1$1(mobileRepositorySwitcherKairos2).this$0.demoRepositoryFactory.create();
                                create.activate(buildScope2);
                                return create;
                            }
                        });
                }
            }
        });
        final int i3 = 14;
        StateInit map = StateKt.map(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i3) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final EventsInit mapNotNull = EventsKt.mapNotNull(EventsKt.map(StateKt.getChanges(map), new StateKt$$ExternalSyntheticLambda8(map, 0)), new SwitchKt$$ExternalSyntheticLambda0(0));
        final StateInit map2 = StateKt.map(map, new Function2() { // from class: com.android.systemui.kairos.SwitchKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Events[] eventsArr = {EventsInit.this, new EventsInit(new Init("patches", new IncrementalKt$$ExternalSyntheticLambda4((Incremental) obj2, 0)))};
                final SwitchKt$$ExternalSyntheticLambda0 switchKt$$ExternalSyntheticLambda0 = new SwitchKt$$ExternalSyntheticLambda0(1);
                return EventsKt.map(MergeKt.merge((Events[]) Arrays.copyOf(eventsArr, 2)), new Function2() { // from class: com.android.systemui.kairos.MergeKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        Iterator it = ((List) obj4).iterator();
                        if (!it.hasNext()) {
                            throw new UnsupportedOperationException("Empty collection can't be reduced.");
                        }
                        Object next = it.next();
                        while (it.hasNext()) {
                            next = SwitchKt$$ExternalSyntheticLambda0.this.invoke(next, it.next());
                        }
                        return next;
                    }
                });
            }
        });
        final SwitchKt$$ExternalSyntheticLambda3 switchKt$$ExternalSyntheticLambda3 = new SwitchKt$$ExternalSyntheticLambda3(1);
        final EventsImpl eventsImpl = new EventsImpl() { // from class: com.android.systemui.kairos.SwitchKt$switchEventsPromptly$$inlined$mapImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activate = ((StateImpl) map2.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(evalScope)).changes.activate(evalScope, schedulable);
                if (activate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, Function3.this), nodeConnection.schedulerUpstream), activate.needsEval);
            }
        };
        final EventsInit eventsInit = new EventsInit(new Init(null, new InitKt$constInit$1(new MuxPromptKt$switchPromptImplSingle$$inlined$mapImpl$1(MuxPromptKt$switchPromptImplSingle$2.INSTANCE, new MuxLifecycle(new MuxLifecycleState.Inactive(new MuxPromptActivator(null, new Function1() { // from class: com.android.systemui.kairos.SwitchKt$switchEventsPromptly$$inlined$switchPromptImplSingle$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                EvalScope evalScope = (EvalScope) obj;
                return new Single((EventsImpl) EventsKt.getInit((Events) ((StateImpl) State.this.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(evalScope)).store.getCurrentWithEpoch(evalScope).getFirst()).connect(evalScope)).getEntries();
            }
        }, new SingletonMapK.Factory(), new Function1() { // from class: com.android.systemui.kairos.SwitchKt$switchEventsPromptly$$inlined$switchPromptImplSingle$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                final MuxPromptKt$switchPromptImplSingle$switchPromptImpl$2$1 muxPromptKt$switchPromptImplSingle$switchPromptImpl$2$1 = MuxPromptKt$switchPromptImplSingle$switchPromptImpl$2$1.INSTANCE;
                final EventsImpl eventsImpl2 = EventsImpl.this;
                return new EventsImpl() { // from class: com.android.systemui.kairos.SwitchKt$switchEventsPromptly$$inlined$switchPromptImplSingle$2.1
                    @Override // com.android.systemui.kairos.internal.EventsImpl
                    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                        ActivationResult activate = eventsImpl2.activate(evalScope, schedulable);
                        if (activate == null) {
                            return null;
                        }
                        NodeConnection nodeConnection = activate.connection;
                        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, Function3.this), nodeConnection.schedulerUpstream), activate.needsEval);
                    }
                };
            }
        })))))));
        final StateInit flatMap = StateKt.flatMap(map, new StateKt$$ExternalSyntheticLambda7());
        this.mobileConnectionsBySubId = new IncrementalInit(new Init("switchIncremental", new Function1() { // from class: com.android.systemui.kairos.SwitchKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                NetworkScope networkScope = (NetworkScope) obj;
                StateImpl stateImpl = (StateImpl) StateInit.this.init.connect(networkScope);
                return new IncrementalImpl("switchIncremental", "switchIncremental", stateImpl.changes, (EventsImpl) EventsKt.getInit(eventsInit).connect(networkScope), stateImpl.store);
            }
        }));
        final int i4 = 1;
        this.subscriptions = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i4) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i5 = 2;
        this.activeMobileDataSubscriptionId = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i5) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i6 = 3;
        this.activeMobileDataRepository = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i6) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i7 = 4;
        this.activeSubChangedInGroupEvent = SwitchKt.switchEvents(StateKt.map(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i7) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        }));
        final int i8 = 5;
        this.defaultDataSubRatConfig = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i8) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i9 = 6;
        this.defaultMobileIconMapping = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i9) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i10 = 7;
        this.defaultMobileIconGroup = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i10) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i11 = 0;
        this.isDeviceEmergencyCallCapable = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i11) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i12 = 8;
        this.isAnySimSecure = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i12) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i13 = 9;
        this.defaultDataSubId = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i13) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i14 = 10;
        this.mobileIsDefault = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i14) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i15 = 11;
        this.hasCarrierMergedConnection = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i15) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i16 = 12;
        this.defaultConnectionIsValidated = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i16) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
        final int i17 = 13;
        this.isInEcmMode = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = (MobileConnectionsRepositoryKairos) obj2;
                switch (i17) {
                    case 0:
                        return mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable();
                    case 1:
                        return mobileConnectionsRepositoryKairos.getSubscriptions();
                    case 2:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId();
                    case 3:
                        return mobileConnectionsRepositoryKairos.getActiveMobileDataRepository();
                    case 4:
                        return mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent();
                    case 5:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig();
                    case 6:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping();
                    case 7:
                        return mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup();
                    case 8:
                        return mobileConnectionsRepositoryKairos.isAnySimSecure();
                    case 9:
                        return mobileConnectionsRepositoryKairos.getDefaultDataSubId();
                    case 10:
                        return mobileConnectionsRepositoryKairos.getMobileIsDefault();
                    case 11:
                        return mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection();
                    case 12:
                        return mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated();
                    case 13:
                        return mobileConnectionsRepositoryKairos.isInEcmMode();
                    default:
                        return mobileConnectionsRepositoryKairos.getMobileConnectionsBySubId();
                }
            }
        });
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getActiveMobileDataRepository() {
        return this.activeMobileDataRepository;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getActiveMobileDataSubscriptionId() {
        return this.activeMobileDataSubscriptionId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final Events getActiveSubChangedInGroupEvent() {
        return this.activeSubChangedInGroupEvent;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getDefaultConnectionIsValidated() {
        return this.defaultConnectionIsValidated;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getDefaultDataSubId() {
        return this.defaultDataSubId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getDefaultDataSubRatConfig() {
        return this.defaultDataSubRatConfig;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getDefaultMobileIconGroup() {
        return this.defaultMobileIconGroup;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getDefaultMobileIconMapping() {
        return this.defaultMobileIconMapping;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getHasCarrierMergedConnection() {
        return this.hasCarrierMergedConnection;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final Incremental getMobileConnectionsBySubId() {
        return this.mobileConnectionsBySubId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getMobileIsDefault() {
        return this.mobileIsDefault;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getSubscriptions() {
        return this.subscriptions;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State isAnySimSecure() {
        return this.isAnySimSecure;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State isDeviceEmergencyCallCapable() {
        return this.isDeviceEmergencyCallCapable;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State isInEcmMode() {
        return this.isInEcmMode;
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }

    public static /* synthetic */ void getActiveRepo$annotations() {
    }
}
