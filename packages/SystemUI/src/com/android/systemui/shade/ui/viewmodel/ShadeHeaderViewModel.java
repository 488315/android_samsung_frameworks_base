package com.android.systemui.shade.ui.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.os.UserHandle;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.shade.domain.interactor.PrivacyChipInteractor;
import com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import java.util.Date;
import java.util.Locale;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class ShadeHeaderViewModel {
    public final StateFlowImpl _longerDateText;
    public final StateFlowImpl _shorterDateText;
    public final ActivityStarter activityStarter;
    public final ShadeHeaderClockInteractor clockInteractor;
    public final ReadonlyStateFlow isDisabled;
    public final ReadonlyStateFlow isLocationIndicationEnabled;
    public final ReadonlyStateFlow isMicCameraIndicationEnabled;
    public final ReadonlyStateFlow isPrivacyChipEnabled;
    public final ReadonlyStateFlow isPrivacyChipVisible;
    public final ReadonlyStateFlow isSingleCarrier;
    public final StateFlowImpl longerDateFormat;
    public final ReadonlyStateFlow longerDateText;
    public final String longerPattern;
    public final MobileIconsViewModel mobileIconsViewModel;
    public final ReadonlyStateFlow mobileSubIds;
    public final PrivacyChipInteractor privacyChipInteractor;
    public final ReadonlyStateFlow privacyItems;
    public final SceneInteractor sceneInteractor;
    public final StateFlowImpl shorterDateFormat;
    public final ReadonlyStateFlow shorterDateText;
    public final String shorterPattern;

    /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = ShadeHeaderViewModel.this.new AnonymousClass3(continuation);
            anonymousClass3.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ShadeHeaderViewModel.access$updateDateTexts(ShadeHeaderViewModel.this, this.Z$0);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShadeHeaderViewModel.this.new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ShadeHeaderViewModel.access$updateDateTexts(ShadeHeaderViewModel.this, false);
            return Unit.INSTANCE;
        }
    }

    public ShadeHeaderViewModel(CoroutineScope coroutineScope, Context context, ActivityStarter activityStarter, SceneInteractor sceneInteractor, ShadeInteractor shadeInteractor, MobileIconsInteractor mobileIconsInteractor, MobileIconsViewModel mobileIconsViewModel, PrivacyChipInteractor privacyChipInteractor, ShadeHeaderClockInteractor shadeHeaderClockInteractor, BroadcastDispatcher broadcastDispatcher) {
        this.activityStarter = activityStarter;
        MobileIconsInteractorImpl mobileIconsInteractorImpl = (MobileIconsInteractorImpl) mobileIconsInteractor;
        ReadonlyStateFlow readonlyStateFlow = mobileIconsInteractorImpl.isSingleCarrier;
        final ReadonlyStateFlow readonlyStateFlow2 = mobileIconsInteractorImpl.filteredSubscriptions;
        Flow flow = new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1

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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), EmptyList.INSTANCE);
        ReadonlyStateFlow readonlyStateFlow3 = privacyChipInteractor.privacyItems;
        ReadonlyStateFlow readonlyStateFlow4 = privacyChipInteractor.isMicCameraIndicationEnabled;
        ReadonlyStateFlow readonlyStateFlow5 = privacyChipInteractor.isLocationIndicationEnabled;
        ReadonlyStateFlow readonlyStateFlow6 = privacyChipInteractor.isChipVisible;
        ReadonlyStateFlow readonlyStateFlow7 = privacyChipInteractor.isChipEnabled;
        final ReadonlyStateFlow readonlyStateFlow8 = ((ShadeInteractorImpl) shadeInteractor).isQsEnabled;
        FlowKt.stateIn(new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel$special$$inlined$map$2

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
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
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
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        String string = context.getString(R.string.abbrev_wday_month_day_no_year_alarm);
        this.longerPattern = string;
        String string2 = context.getString(R.string.abbrev_month_day_no_year);
        this.shorterPattern = string2;
        DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(string, Locale.getDefault());
        DisplayContext displayContext = DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE;
        instanceForSkeleton.setContext(displayContext);
        this.longerDateFormat = StateFlowKt.MutableStateFlow(instanceForSkeleton);
        DateFormat instanceForSkeleton2 = DateFormat.getInstanceForSkeleton(string2, Locale.getDefault());
        instanceForSkeleton2.setContext(displayContext);
        this.shorterDateFormat = StateFlowKt.MutableStateFlow(instanceForSkeleton2);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow("");
        this._shorterDateText = MutableStateFlow;
        FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow("");
        this._longerDateText = MutableStateFlow2;
        FlowKt.asStateFlow(MutableStateFlow2);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, intentFilter, UserHandle.SYSTEM, new Function2() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel.2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Intent intent = (Intent) obj;
                return Boolean.valueOf(Intrinsics.areEqual(intent.getAction(), "android.intent.action.TIMEZONE_CHANGED") || Intrinsics.areEqual(intent.getAction(), "android.intent.action.LOCALE_CHANGED"));
            }
        }, 12), new AnonymousClass3(null)), coroutineScope);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(null), 3);
    }

    public static final void access$updateDateTexts(ShadeHeaderViewModel shadeHeaderViewModel, boolean z) {
        StateFlowImpl stateFlowImpl = shadeHeaderViewModel.shorterDateFormat;
        StateFlowImpl stateFlowImpl2 = shadeHeaderViewModel.longerDateFormat;
        if (z) {
            DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(shadeHeaderViewModel.longerPattern, Locale.getDefault());
            DisplayContext displayContext = DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE;
            instanceForSkeleton.setContext(displayContext);
            stateFlowImpl2.updateState(null, instanceForSkeleton);
            DateFormat instanceForSkeleton2 = DateFormat.getInstanceForSkeleton(shadeHeaderViewModel.shorterPattern, Locale.getDefault());
            instanceForSkeleton2.setContext(displayContext);
            stateFlowImpl.updateState(null, instanceForSkeleton2);
        }
        Date date = new Date();
        shadeHeaderViewModel._longerDateText.updateState(null, ((DateFormat) stateFlowImpl2.getValue()).format(date));
        shadeHeaderViewModel._shorterDateText.updateState(null, ((DateFormat) stateFlowImpl.getValue()).format(date));
    }
}
