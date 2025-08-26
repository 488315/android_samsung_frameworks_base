package com.android.systemui.shade.ui.viewmodel;

import android.content.Context;
import androidx.compose.runtime.State;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
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
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

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

    public interface Factory {
        ShadeHeaderViewModel create();
    }

    /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ShadeHeaderViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ ShadeHeaderViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(ShadeHeaderViewModel shadeHeaderViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = shadeHeaderViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Hydrator hydrator = this.this$0.hydrator;
                    this.label = 1;
                    if (hydrator.activate(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = ShadeHeaderViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(ShadeHeaderViewModel.this, null), 7);
                this.label = 1;
                if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
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
        Hydrator hydrator = new Hydrator("ShadeHeaderViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        this.createTintedIconManager = new ShadeHeaderViewModel$createTintedIconManager$1(factory);
        this.createBatteryMeterViewController = new ShadeHeaderViewModel$createBatteryMeterViewController$1(factory2);
        ShadeModeInteractorImpl shadeModeInteractorImpl = (ShadeModeInteractorImpl) shadeModeInteractor;
        Boolean boolValueOf = Boolean.valueOf(((Boolean) shadeModeInteractorImpl.isShadeLayoutWide.$$delegate_0.getValue()).booleanValue() || !((Set) sceneInteractor.currentOverlays.getValue()).contains(Overlays.NotificationsShade));
        ReadonlyStateFlow readonlyStateFlow = shadeModeInteractorImpl.isShadeLayoutWide;
        ShadeHeaderViewModel$showClock$2 shadeHeaderViewModel$showClock$2 = new ShadeHeaderViewModel$showClock$2(this);
        final StateFlow stateFlow = sceneInteractor.currentOverlays;
        this.showClock$delegate = hydrator.hydratedStateOf("showClock", boolValueOf, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, stateFlow, shadeHeaderViewModel$showClock$2));
        ShadeHeaderViewModel$HeaderChipHighlight$None shadeHeaderViewModel$HeaderChipHighlight$None = ShadeHeaderViewModel$HeaderChipHighlight$None.INSTANCE;
        this.notificationsChipHighlight$delegate = hydrator.hydratedStateOf("notificationsChipHighlight", shadeHeaderViewModel$HeaderChipHighlight$None, new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        Set set = (Set) obj;
                        Object obj3 = set.contains(Overlays.NotificationsShade) ? ShadeHeaderViewModel$HeaderChipHighlight$Strong.INSTANCE : set.contains(Overlays.QuickSettingsShade) ? ShadeHeaderViewModel$HeaderChipHighlight$Weak.INSTANCE : ShadeHeaderViewModel$HeaderChipHighlight$None.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(obj3, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = stateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.quickSettingsChipHighlight$delegate = hydrator.hydratedStateOf("quickSettingsChipHighlight", shadeHeaderViewModel$HeaderChipHighlight$None, new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        Set set = (Set) obj;
                        Object obj3 = set.contains(Overlays.QuickSettingsShade) ? ShadeHeaderViewModel$HeaderChipHighlight$Strong.INSTANCE : set.contains(Overlays.NotificationsShade) ? ShadeHeaderViewModel$HeaderChipHighlight$Weak.INSTANCE : ShadeHeaderViewModel$HeaderChipHighlight$None.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(obj3, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = stateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.isSingleCarrier = mobileIconsInteractor.isSingleCarrier();
        EmptyList emptyList = EmptyList.INSTANCE;
        final Flow filteredSubscriptions = mobileIconsInteractor.getFilteredSubscriptions();
        this.mobileSubIds$delegate = hydrator.hydratedStateOf("mobileSubIds", emptyList, new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$3

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        List list = (List) obj;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            arrayList.add(new Integer(((SubscriptionModel) it.next()).subscriptionId));
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(arrayList, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = filteredSubscriptions.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.privacyItems = privacyChipInteractor.privacyItems;
        this.isMicCameraIndicationEnabled = privacyChipInteractor.isMicCameraIndicationEnabled;
        this.isLocationIndicationEnabled = privacyChipInteractor.isLocationIndicationEnabled;
        this.isPrivacyChipVisible = privacyChipInteractor.isChipVisible;
        this.isPrivacyChipEnabled = privacyChipInteractor.isChipEnabled;
        this.longerPattern = context.getString(R.string.abbrev_wday_month_day_no_year_alarm);
        this.shorterPattern = context.getString(R.string.abbrev_month_day_no_year);
        ChannelFlowTransformLatest channelFlowTransformLatestMapLatest = FlowKt.mapLatest(shadeHeaderClockInteractor.onTimezoneOrLocaleChanged, new ShadeHeaderViewModel$longerDateFormat$1(this, null));
        this.longerDateFormat = channelFlowTransformLatestMapLatest;
        ChannelFlowTransformLatest channelFlowTransformLatestMapLatest2 = FlowKt.mapLatest(shadeHeaderClockInteractor.onTimezoneOrLocaleChanged, new ShadeHeaderViewModel$shorterDateFormat$1(this, null));
        this.shorterDateFormat = channelFlowTransformLatestMapLatest2;
        ShadeHeaderViewModel$longerDateText$2 shadeHeaderViewModel$longerDateText$2 = new ShadeHeaderViewModel$longerDateText$2(null);
        ShadeHeaderClockInteractor$special$$inlined$map$1 shadeHeaderClockInteractor$special$$inlined$map$1 = shadeHeaderClockInteractor.currentTime;
        this.longerDateText$delegate = hydrator.hydratedStateOf("longerDateText", "", new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(channelFlowTransformLatestMapLatest, shadeHeaderClockInteractor$special$$inlined$map$1, shadeHeaderViewModel$longerDateText$2));
        this.shorterDateText$delegate = hydrator.hydratedStateOf("shorterDateText", "", new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(channelFlowTransformLatestMapLatest2, shadeHeaderClockInteractor$special$$inlined$map$1, new ShadeHeaderViewModel$shorterDateText$2(null)));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
