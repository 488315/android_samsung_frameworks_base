package com.android.systemui.statusbar.chips.call.ui.viewmodel;

import android.R;
import android.content.Context;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.call.domain.interactor.CallChipInteractor;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger;
import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import com.android.systemui.util.time.SystemClock;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CallChipViewModel implements OngoingActivityChipViewModel {
    public static final Companion Companion = null;
    public static final Icon.Resource phoneIcon;
    public final ActivityStarter activityStarter;
    public final ReadonlyStateFlow chip;
    public final ReadonlyStateFlow chipLegacy;
    public final ReadonlyStateFlow chipWithReturnAnimation;
    public final Context context;
    public final OngoingCallModel latestState;
    public final TransitionState latestTransitionState;
    public final Logger logger;
    public final StateFlowImpl transitionState;
    public final StatusBarChipsUiEventLogger uiEventLogger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TransitionState {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class NoTransition implements TransitionState {
            public static final NoTransition INSTANCE = new NoTransition();

            private NoTransition() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof NoTransition);
            }

            public final int hashCode() {
                return 28185635;
            }

            public final String toString() {
                return "NoTransition";
            }
        }
    }

    static {
        new Companion(null);
        phoneIcon = new Icon.Resource(R.drawable.input_method_fullscreen_background, new ContentDescription.Resource(com.android.systemui.R.string.ongoing_call_content_description));
    }

    public CallChipViewModel(Context context, CoroutineScope coroutineScope, CallChipInteractor callChipInteractor, final SystemClock systemClock, ActivityStarter activityStarter, LogBuffer logBuffer, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger) {
        this.context = context;
        this.activityStarter = activityStarter;
        this.uiEventLogger = statusBarChipsUiEventLogger;
        StatusBarChipLogTags.INSTANCE.getClass();
        this.logger = new Logger(logBuffer, StringsKt__StringsKt.padEnd(20, "OngoingCallVM"));
        new ActivityTransitionAnimator.TransitionCookie(String.valueOf(CallChipViewModel.class));
        TransitionState.NoTransition noTransition = TransitionState.NoTransition.INSTANCE;
        this.transitionState = StateFlowKt.MutableStateFlow(noTransition);
        this.latestState = OngoingCallModel.NoCall.INSTANCE;
        this.latestTransitionState = noTransition;
        this.chipWithReturnAnimation = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(new OngoingActivityChipModel.Inactive(false, null, 3, null)));
        final ReadonlyStateFlow readonlyStateFlow = callChipInteractor.ongoingCallState;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ SystemClock $systemClock$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CallChipViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CallChipViewModel callChipViewModel, SystemClock systemClock) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = callChipViewModel;
                    this.$systemClock$inlined = systemClock;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r28, kotlin.coroutines.Continuation r29) {
                    /*
                        Method dump skipped, instructions count: 347
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, systemClock), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.chipLegacy = stateIn;
        this.chip = stateIn;
    }
}
