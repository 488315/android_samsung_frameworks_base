package com.android.systemui.haptics.msdl.qs;

import android.content.ComponentName;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.window.WindowAnimationState;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.qs.panels.ui.viewmodel.TileViewModel;
import com.google.android.msdl.data.model.MSDLToken;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes2.dex */
public final class TileHapticsViewModel extends ExclusiveActivatable {
    public final ChannelLimitedFlowMerge hapticsState;
    public final MSDLPlayer msdlPlayer;
    public final StateFlowImpl tileAnimationState;
    public final StateFlowImpl tileInteractionState;

    public interface Factory {
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class TileAnimationState {
        public static final /* synthetic */ TileAnimationState[] $VALUES;
        public static final TileAnimationState ACTIVITY_LAUNCH;
        public static final TileAnimationState DIALOG_LAUNCH;
        public static final TileAnimationState IDLE;

        static {
            TileAnimationState tileAnimationState = new TileAnimationState("IDLE", 0);
            IDLE = tileAnimationState;
            TileAnimationState tileAnimationState2 = new TileAnimationState("DIALOG_LAUNCH", 1);
            DIALOG_LAUNCH = tileAnimationState2;
            TileAnimationState tileAnimationState3 = new TileAnimationState("ACTIVITY_LAUNCH", 2);
            ACTIVITY_LAUNCH = tileAnimationState3;
            TileAnimationState[] tileAnimationStateArr = {tileAnimationState, tileAnimationState2, tileAnimationState3};
            $VALUES = tileAnimationStateArr;
            EnumEntriesKt.enumEntries(tileAnimationStateArr);
        }

        private TileAnimationState(String str, int i) {
        }

        public static TileAnimationState valueOf(String str) {
            return (TileAnimationState) Enum.valueOf(TileAnimationState.class, str);
        }

        public static TileAnimationState[] values() {
            return (TileAnimationState[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class TileHapticsState {
        public static final /* synthetic */ TileHapticsState[] $VALUES;
        public static final TileHapticsState LONG_PRESS;
        public static final TileHapticsState NO_HAPTICS;
        public static final TileHapticsState TOGGLE_OFF;
        public static final TileHapticsState TOGGLE_ON;

        static {
            TileHapticsState tileHapticsState = new TileHapticsState("TOGGLE_ON", 0);
            TOGGLE_ON = tileHapticsState;
            TileHapticsState tileHapticsState2 = new TileHapticsState("TOGGLE_OFF", 1);
            TOGGLE_OFF = tileHapticsState2;
            TileHapticsState tileHapticsState3 = new TileHapticsState("LONG_PRESS", 2);
            LONG_PRESS = tileHapticsState3;
            TileHapticsState tileHapticsState4 = new TileHapticsState("NO_HAPTICS", 3);
            NO_HAPTICS = tileHapticsState4;
            TileHapticsState[] tileHapticsStateArr = {tileHapticsState, tileHapticsState2, tileHapticsState3, tileHapticsState4};
            $VALUES = tileHapticsStateArr;
            EnumEntriesKt.enumEntries(tileHapticsStateArr);
        }

        private TileHapticsState(String str, int i) {
        }

        public static TileHapticsState valueOf(String str) {
            return (TileHapticsState) Enum.valueOf(TileHapticsState.class, str);
        }

        public static TileHapticsState[] values() {
            return (TileHapticsState[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class TileInteractionState {
        public static final /* synthetic */ TileInteractionState[] $VALUES;
        public static final TileInteractionState CLICKED;
        public static final TileInteractionState IDLE;
        public static final TileInteractionState LONG_CLICKED;

        static {
            TileInteractionState tileInteractionState = new TileInteractionState("IDLE", 0);
            IDLE = tileInteractionState;
            TileInteractionState tileInteractionState2 = new TileInteractionState("CLICKED", 1);
            CLICKED = tileInteractionState2;
            TileInteractionState tileInteractionState3 = new TileInteractionState("LONG_CLICKED", 2);
            LONG_CLICKED = tileInteractionState3;
            TileInteractionState[] tileInteractionStateArr = {tileInteractionState, tileInteractionState2, tileInteractionState3};
            $VALUES = tileInteractionStateArr;
            EnumEntriesKt.enumEntries(tileInteractionStateArr);
        }

        private TileInteractionState(String str, int i) {
        }

        public static TileInteractionState valueOf(String str) {
            return (TileInteractionState) Enum.valueOf(TileInteractionState.class, str);
        }

        public static TileInteractionState[] values() {
            return (TileInteractionState[]) $VALUES.clone();
        }
    }

    /* renamed from: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$createStateAwareExpandable$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function0 {
        public AnonymousClass1(Object obj) {
            super(0, obj, TileHapticsViewModel.class, "onDialogDrawingStart", "onDialogDrawingStart()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ((TileHapticsViewModel) this.receiver).tileAnimationState.setValue(TileAnimationState.DIALOG_LAUNCH);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$createStateAwareExpandable$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function0 {
        public AnonymousClass2(Object obj) {
            super(0, obj, TileHapticsViewModel.class, "onDialogDrawingEnd", "onDialogDrawingEnd()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ((TileHapticsViewModel) this.receiver).tileAnimationState.setValue(TileAnimationState.IDLE);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$createStateAwareExpandable$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function0 {
        public AnonymousClass3(Object obj) {
            super(0, obj, TileHapticsViewModel.class, "onActivityLaunchTransitionStart", "onActivityLaunchTransitionStart()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ((TileHapticsViewModel) this.receiver).tileAnimationState.setValue(TileAnimationState.ACTIVITY_LAUNCH);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$createStateAwareExpandable$4, reason: invalid class name */
    final /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function0 {
        public AnonymousClass4(Object obj) {
            super(0, obj, TileHapticsViewModel.class, "onActivityLaunchTransitionEnd", "onActivityLaunchTransitionEnd()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ((TileHapticsViewModel) this.receiver).tileAnimationState.setValue(TileAnimationState.IDLE);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$onActivated$1, reason: invalid class name and case insensitive filesystem */
    final class C08681 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08681(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TileHapticsViewModel.this.onActivated(this);
        }
    }

    public TileHapticsViewModel(MSDLPlayer mSDLPlayer, TileViewModel tileViewModel) {
        this.msdlPlayer = mSDLPlayer;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(TileInteractionState.IDLE);
        this.tileInteractionState = stateFlowImplMutableStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(TileAnimationState.IDLE);
        this.tileAnimationState = stateFlowImplMutableStateFlow2;
        this.hapticsState = FlowKt.merge(FlowKt.distinctUntilChanged(new SafeFlow(new TileHapticsViewModel$special$$inlined$transform$1(com.android.systemui.util.kotlin.FlowKt.pairwise(FlowKt.mapLatest(tileViewModel.state, new TileHapticsViewModel$toggleHapticsState$1(null))), null, this))), FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImplMutableStateFlow, stateFlowImplMutableStateFlow2, new TileHapticsViewModel$interactionHapticsState$1(null))));
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.haptics.msdl.qs.StateAwareExpandableKt$withStateAwareness$3] */
    public final StateAwareExpandableKt$withStateAwareness$3 createStateAwareExpandable(final Expandable expandable) {
        final AnonymousClass1 anonymousClass1 = new AnonymousClass1(this);
        final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
        final AnonymousClass3 anonymousClass3 = new AnonymousClass3(this);
        final AnonymousClass4 anonymousClass4 = new AnonymousClass4(this);
        return new Expandable() { // from class: com.android.systemui.haptics.msdl.qs.StateAwareExpandableKt$withStateAwareness$3
            @Override // com.android.systemui.animation.Expandable
            public final ActivityTransitionAnimator.Controller activityTransitionController(Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num2, boolean z) {
                ActivityTransitionAnimator.Controller controllerActivityTransitionController = expandable.activityTransitionController(num, transitionCookie, componentName, num2, z);
                if (controllerActivityTransitionController != null) {
                    return new ActivityTransitionAnimator.Controller(anonymousClass3, anonymousClass4) { // from class: com.android.systemui.haptics.msdl.qs.StateAwareExpandableKt$withStateAwareness$1
                        public final /* synthetic */ ActivityTransitionAnimator.Controller $$delegate_0;
                        public final /* synthetic */ Function0 $onActivityLaunchTransitionEnd;
                        public final /* synthetic */ Function0 $onActivityLaunchTransitionStart;

                        {
                            this.$onActivityLaunchTransitionStart = function0;
                            this.$onActivityLaunchTransitionEnd = function0;
                            this.$$delegate_0 = this.$delegate;
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final TransitionAnimator.State createAnimatorState() {
                            return this.$$delegate_0.createAnimatorState();
                        }

                        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                        public final ComponentName getComponent() {
                            return this.$$delegate_0.getComponent();
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final View getOpeningWindowSyncView() {
                            return this.$$delegate_0.getOpeningWindowSyncView();
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final ViewGroup getTransitionContainer() {
                            return this.$$delegate_0.getTransitionContainer();
                        }

                        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                        public final ActivityTransitionAnimator.TransitionCookie getTransitionCookie() {
                            return this.$$delegate_0.getTransitionCookie();
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final WindowAnimationState getWindowAnimatorState() {
                            return this.$$delegate_0.getWindowAnimatorState();
                        }

                        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                        public final boolean isBelowAnimatingWindow() {
                            return this.$$delegate_0.isBelowAnimatingWindow();
                        }

                        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                        public final boolean isDialogLaunch() {
                            return this.$$delegate_0.isDialogLaunch();
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final boolean isLaunching() {
                            return this.$$delegate_0.isLaunching();
                        }

                        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                        public final void onDispose() {
                            this.$$delegate_0.onDispose();
                        }

                        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                        public final void onIntentStarted(boolean z2) {
                            this.$$delegate_0.onIntentStarted(z2);
                        }

                        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                        public final void onTransitionAnimationCancelled() {
                            this.$onActivityLaunchTransitionEnd.invoke();
                            this.$delegate.onTransitionAnimationCancelled();
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final void onTransitionAnimationEnd(boolean z2) {
                            this.$onActivityLaunchTransitionEnd.invoke();
                            this.$delegate.onTransitionAnimationEnd(z2);
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
                            this.$$delegate_0.onTransitionAnimationProgress(state, f, f2);
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final void onTransitionAnimationStart(boolean z2) {
                            this.$onActivityLaunchTransitionStart.invoke();
                            this.$delegate.onTransitionAnimationStart(z2);
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final void setTransitionContainer(ViewGroup viewGroup) {
                            this.$$delegate_0.setTransitionContainer(viewGroup);
                        }
                    };
                }
                return null;
            }

            @Override // com.android.systemui.animation.Expandable
            public final DialogTransitionAnimator.Controller dialogTransitionController(DialogCuj dialogCuj) {
                DialogTransitionAnimator.Controller controllerDialogTransitionController = expandable.dialogTransitionController(dialogCuj);
                if (controllerDialogTransitionController != null) {
                    return new DialogTransitionAnimator.Controller(anonymousClass1, anonymousClass2) { // from class: com.android.systemui.haptics.msdl.qs.StateAwareExpandableKt$withStateAwareness$2
                        public final /* synthetic */ DialogTransitionAnimator.Controller $$delegate_0;
                        public final /* synthetic */ Function0 $onDialogDrawingEnd;
                        public final /* synthetic */ Function0 $onDialogDrawingStart;

                        {
                            this.$onDialogDrawingStart = function0;
                            this.$onDialogDrawingEnd = function0;
                            this.$$delegate_0 = this.$delegate;
                        }

                        @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                        public final TransitionAnimator.Controller createExitController() {
                            return this.$$delegate_0.createExitController();
                        }

                        @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                        public final TransitionAnimator.Controller createTransitionController() {
                            return this.$$delegate_0.createTransitionController();
                        }

                        @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                        public final DialogCuj getCuj() {
                            return this.$$delegate_0.getCuj();
                        }

                        @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                        public final Object getSourceIdentity() {
                            return this.$$delegate_0.getSourceIdentity();
                        }

                        @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                        public final ViewRootImpl getViewRoot() {
                            return this.$$delegate_0.getViewRoot();
                        }

                        @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                        public final InteractionJankMonitor.Configuration.Builder jankConfigurationBuilder() {
                            return this.$$delegate_0.jankConfigurationBuilder();
                        }

                        @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                        public final void onExitAnimationCancelled() {
                            this.$$delegate_0.onExitAnimationCancelled();
                        }

                        @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                        public final boolean shouldAnimateExit() {
                            return this.$$delegate_0.shouldAnimateExit();
                        }

                        @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                        public final void startDrawingInOverlayOf(ViewGroup viewGroup) {
                            this.$onDialogDrawingStart.invoke();
                            this.$delegate.startDrawingInOverlayOf(viewGroup);
                        }

                        @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                        public final void stopDrawingInOverlay() {
                            this.$onDialogDrawingEnd.invoke();
                            this.$delegate.stopDrawingInOverlay();
                        }
                    };
                }
                return null;
            }
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x005d, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        C08681 c08681;
        if (continuation instanceof C08681) {
            c08681 = (C08681) continuation;
            int i = c08681.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08681.label = i - Integer.MIN_VALUE;
            } else {
                c08681 = new C08681(continuation);
            }
        }
        Object obj = c08681.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08681.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ChannelLimitedFlowMerge channelLimitedFlowMerge = this.hapticsState;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel.onActivated.2

                    /* renamed from: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$onActivated$2$WhenMappings */
                    public abstract /* synthetic */ class WhenMappings {
                        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                        static {
                            int[] iArr = new int[TileHapticsState.values().length];
                            try {
                                iArr[TileHapticsState.TOGGLE_ON.ordinal()] = 1;
                            } catch (NoSuchFieldError unused) {
                            }
                            try {
                                iArr[TileHapticsState.TOGGLE_OFF.ordinal()] = 2;
                            } catch (NoSuchFieldError unused2) {
                            }
                            try {
                                iArr[TileHapticsState.LONG_PRESS.ordinal()] = 3;
                            } catch (NoSuchFieldError unused3) {
                            }
                            try {
                                iArr[TileHapticsState.NO_HAPTICS.ordinal()] = 4;
                            } catch (NoSuchFieldError unused4) {
                            }
                            $EnumSwitchMapping$0 = iArr;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation2) {
                        MSDLToken mSDLToken;
                        int i3 = WhenMappings.$EnumSwitchMapping$0[((TileHapticsState) obj2).ordinal()];
                        if (i3 == 1) {
                            mSDLToken = MSDLToken.SWITCH_ON;
                        } else if (i3 == 2) {
                            mSDLToken = MSDLToken.SWITCH_OFF;
                        } else if (i3 == 3) {
                            mSDLToken = MSDLToken.LONG_PRESS;
                        } else {
                            if (i3 != 4) {
                                throw new NoWhenBranchMatchedException();
                            }
                            mSDLToken = null;
                        }
                        if (mSDLToken != null) {
                            TileHapticsViewModel tileHapticsViewModel = TileHapticsViewModel.this;
                            MSDLPlayer mSDLPlayer = tileHapticsViewModel.msdlPlayer;
                            MSDLPlayer.Companion companion = MSDLPlayer.Companion;
                            mSDLPlayer.playToken(mSDLToken, null);
                            tileHapticsViewModel.tileInteractionState.setValue(TileInteractionState.IDLE);
                            tileHapticsViewModel.tileAnimationState.setValue(TileAnimationState.IDLE);
                        }
                        return Unit.INSTANCE;
                    }
                };
                c08681.L$0 = this;
                c08681.label = 1;
                if (channelLimitedFlowMerge.collect(flowCollector, c08681) == coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                this = (TileHapticsViewModel) c08681.L$0;
                ResultKt.throwOnFailure(obj);
                throw new KotlinNothingValueException();
            }
            this = (TileHapticsViewModel) c08681.L$0;
            ResultKt.throwOnFailure(obj);
            c08681.L$0 = this;
            c08681.label = 2;
        } catch (Throwable th) {
            this.tileInteractionState.setValue(TileInteractionState.IDLE);
            this.tileAnimationState.setValue(TileAnimationState.IDLE);
            throw th;
        }
    }
}
