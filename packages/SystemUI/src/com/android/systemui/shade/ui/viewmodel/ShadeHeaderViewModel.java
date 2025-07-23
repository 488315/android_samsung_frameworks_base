package com.android.systemui.shade.ui.viewmodel;

import android.content.Context;
import androidx.compose.runtime.State;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.shade.domain.interactor.PrivacyChipInteractor;
import com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor;
import com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import dagger.Lazy;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeHeaderViewModel extends ExclusiveActivatable {
    public final ActivityStarter activityStarter;
    public final ShadeHeaderClockInteractor clockInteractor;
    public final Function2 createBatteryMeterViewController;
    public final Function2 createTintedIconManager;
    public final Hydrator hydrator;
    public final ReadonlyStateFlow isLocationIndicationEnabled;
    public final ReadonlyStateFlow isMicCameraIndicationEnabled;
    public final ReadonlyStateFlow isPrivacyChipEnabled;
    public final ReadonlyStateFlow isPrivacyChipVisible;
    public final StateFlow isSingleCarrier;
    public final KairosNetwork kairosNetwork;
    public final ChannelFlowTransformLatest longerDateFormat;
    public final State longerDateText$delegate;
    public final String longerPattern;
    public final MobileIconsViewModel mobileIconsViewModel;
    public final State mobileSubIds$delegate;
    public final State notificationsChipHighlight$delegate;
    public final PrivacyChipInteractor privacyChipInteractor;
    public final ReadonlyStateFlow privacyItems;
    public final State quickSettingsChipHighlight$delegate;
    public final SceneInteractor sceneInteractor;
    public final ShadeInteractor shadeInteractor;
    public final ShadeModeInteractor shadeModeInteractor;
    public final ChannelFlowTransformLatest shorterDateFormat;
    public final State shorterDateText$delegate;
    public final String shorterPattern;
    public final State showClock$delegate;
    public final StatusBarIconController statusBarIconController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        ShadeHeaderViewModel create();
    }

    public ShadeHeaderViewModel(Context context, ActivityStarter activityStarter, SceneInteractor sceneInteractor, ShadeInteractor shadeInteractor, ShadeModeInteractor shadeModeInteractor, MobileIconsInteractor mobileIconsInteractor, MobileIconsViewModel mobileIconsViewModel, PrivacyChipInteractor privacyChipInteractor, ShadeHeaderClockInteractor shadeHeaderClockInteractor, TintedIconManager.Factory factory, BatteryMeterViewController.Factory factory2, StatusBarIconController statusBarIconController, KairosNetwork kairosNetwork, Lazy lazy) {
        this.activityStarter = activityStarter;
        this.sceneInteractor = sceneInteractor;
        this.shadeInteractor = shadeInteractor;
        this.shadeModeInteractor = shadeModeInteractor;
        this.mobileIconsViewModel = mobileIconsViewModel;
        this.privacyChipInteractor = privacyChipInteractor;
        this.clockInteractor = shadeHeaderClockInteractor;
        this.statusBarIconController = statusBarIconController;
        this.kairosNetwork = kairosNetwork;
        Hydrator hydrator = new Hydrator("ShadeHeaderViewModel.hydrator", null, 2, 0 == true ? 1 : 0);
        this.hydrator = hydrator;
        this.createTintedIconManager = new ShadeHeaderViewModel$createTintedIconManager$1(factory);
        this.createBatteryMeterViewController = new ShadeHeaderViewModel$createBatteryMeterViewController$1(factory2);
        ShadeModeInteractorImpl shadeModeInteractorImpl = (ShadeModeInteractorImpl) shadeModeInteractor;
        Boolean valueOf = Boolean.valueOf(((Boolean) shadeModeInteractorImpl.isShadeLayoutWide.$$delegate_0.getValue()).booleanValue() || !((Set) sceneInteractor.currentOverlays.getValue()).contains(Overlays.NotificationsShade));
        ReadonlyStateFlow readonlyStateFlow = shadeModeInteractorImpl.isShadeLayoutWide;
        ShadeHeaderViewModel$showClock$2 shadeHeaderViewModel$showClock$2 = new ShadeHeaderViewModel$showClock$2(this);
        final StateFlow stateFlow = sceneInteractor.currentOverlays;
        this.showClock$delegate = hydrator.hydratedStateOf("showClock", valueOf, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, stateFlow, shadeHeaderViewModel$showClock$2));
        ShadeHeaderViewModel$HeaderChipHighlight$None shadeHeaderViewModel$HeaderChipHighlight$None = ShadeHeaderViewModel$HeaderChipHighlight$None.INSTANCE;
        this.notificationsChipHighlight$delegate = hydrator.hydratedStateOf("notificationsChipHighlight", shadeHeaderViewModel$HeaderChipHighlight$None, new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L57
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.Set r5 = (java.util.Set) r5
                        com.android.compose.animation.scene.OverlayKey r6 = com.android.systemui.scene.shared.model.Overlays.NotificationsShade
                        boolean r6 = r5.contains(r6)
                        if (r6 == 0) goto L3f
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$Strong r5 = com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$Strong.INSTANCE
                        goto L4c
                    L3f:
                        com.android.compose.animation.scene.OverlayKey r6 = com.android.systemui.scene.shared.model.Overlays.QuickSettingsShade
                        boolean r5 = r5.contains(r6)
                        if (r5 == 0) goto L4a
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$Weak r5 = com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$Weak.INSTANCE
                        goto L4c
                    L4a:
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$None r5 = com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$None.INSTANCE
                    L4c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L57
                        return r1
                    L57:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.quickSettingsChipHighlight$delegate = hydrator.hydratedStateOf("quickSettingsChipHighlight", shadeHeaderViewModel$HeaderChipHighlight$None, new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L57
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.Set r5 = (java.util.Set) r5
                        com.android.compose.animation.scene.OverlayKey r6 = com.android.systemui.scene.shared.model.Overlays.QuickSettingsShade
                        boolean r6 = r5.contains(r6)
                        if (r6 == 0) goto L3f
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$Strong r5 = com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$Strong.INSTANCE
                        goto L4c
                    L3f:
                        com.android.compose.animation.scene.OverlayKey r6 = com.android.systemui.scene.shared.model.Overlays.NotificationsShade
                        boolean r5 = r5.contains(r6)
                        if (r5 == 0) goto L4a
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$Weak r5 = com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$Weak.INSTANCE
                        goto L4c
                    L4a:
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$None r5 = com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$HeaderChipHighlight$None.INSTANCE
                    L4c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L57
                        return r1
                    L57:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isSingleCarrier = mobileIconsInteractor.isSingleCarrier();
        EmptyList emptyList = EmptyList.INSTANCE;
        final Flow filteredSubscriptions = mobileIconsInteractor.getFilteredSubscriptions();
        this.mobileSubIds$delegate = hydrator.hydratedStateOf("mobileSubIds", emptyList, new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$3$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L67
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.List r6 = (java.util.List) r6
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        java.util.ArrayList r7 = new java.util.ArrayList
                        r2 = 10
                        int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r6, r2)
                        r7.<init>(r2)
                        java.util.Iterator r6 = r6.iterator()
                    L45:
                        boolean r2 = r6.hasNext()
                        if (r2 == 0) goto L5c
                        java.lang.Object r2 = r6.next()
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel r2 = (com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel) r2
                        int r2 = r2.subscriptionId
                        java.lang.Integer r4 = new java.lang.Integer
                        r4.<init>(r2)
                        r7.add(r4)
                        goto L45
                    L5c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L67
                        return r1
                    L67:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.privacyItems = privacyChipInteractor.privacyItems;
        this.isMicCameraIndicationEnabled = privacyChipInteractor.isMicCameraIndicationEnabled;
        this.isLocationIndicationEnabled = privacyChipInteractor.isLocationIndicationEnabled;
        this.isPrivacyChipVisible = privacyChipInteractor.isChipVisible;
        this.isPrivacyChipEnabled = privacyChipInteractor.isChipEnabled;
        this.longerPattern = context.getString(R.string.abbrev_wday_month_day_no_year_alarm);
        this.shorterPattern = context.getString(R.string.abbrev_month_day_no_year);
        ChannelFlowTransformLatest mapLatest = FlowKt.mapLatest(shadeHeaderClockInteractor.onTimezoneOrLocaleChanged, new ShadeHeaderViewModel$longerDateFormat$1(this, null));
        this.longerDateFormat = mapLatest;
        ChannelFlowTransformLatest mapLatest2 = FlowKt.mapLatest(shadeHeaderClockInteractor.onTimezoneOrLocaleChanged, new ShadeHeaderViewModel$shorterDateFormat$1(this, null));
        this.shorterDateFormat = mapLatest2;
        ShadeHeaderViewModel$longerDateText$2 shadeHeaderViewModel$longerDateText$2 = new ShadeHeaderViewModel$longerDateText$2(null);
        ShadeHeaderClockInteractor$special$$inlined$map$1 shadeHeaderClockInteractor$special$$inlined$map$1 = shadeHeaderClockInteractor.currentTime;
        this.longerDateText$delegate = hydrator.hydratedStateOf("longerDateText", "", new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mapLatest, shadeHeaderClockInteractor$special$$inlined$map$1, shadeHeaderViewModel$longerDateText$2));
        this.shorterDateText$delegate = hydrator.hydratedStateOf("shorterDateText", "", new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mapLatest2, shadeHeaderClockInteractor$special$$inlined$map$1, new ShadeHeaderViewModel$shorterDateText$2(null)));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$onActivated$1 r0 = (com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$onActivated$1 r0 = new com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$onActivated$2 r5 = new com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
