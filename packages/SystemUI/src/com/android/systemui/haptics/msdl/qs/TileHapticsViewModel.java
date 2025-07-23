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
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileHapticsViewModel extends ExclusiveActivatable {
    public final ChannelLimitedFlowMerge hapticsState;
    public final MSDLPlayer msdlPlayer;
    public final StateFlowImpl tileAnimationState;
    public final StateFlowImpl tileInteractionState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public TileHapticsViewModel(MSDLPlayer mSDLPlayer, TileViewModel tileViewModel) {
        this.msdlPlayer = mSDLPlayer;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(TileInteractionState.IDLE);
        this.tileInteractionState = MutableStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(TileAnimationState.IDLE);
        this.tileAnimationState = MutableStateFlow2;
        this.hapticsState = FlowKt.merge(FlowKt.distinctUntilChanged(new SafeFlow(new TileHapticsViewModel$special$$inlined$transform$1(com.android.systemui.util.kotlin.FlowKt.pairwise(FlowKt.mapLatest(tileViewModel.state, new TileHapticsViewModel$toggleHapticsState$1(null))), null, this))), FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(MutableStateFlow, MutableStateFlow2, new TileHapticsViewModel$interactionHapticsState$1(null))));
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.haptics.msdl.qs.StateAwareExpandableKt$withStateAwareness$3] */
    public final StateAwareExpandableKt$withStateAwareness$3 createStateAwareExpandable(final Expandable expandable) {
        final TileHapticsViewModel$createStateAwareExpandable$1 tileHapticsViewModel$createStateAwareExpandable$1 = new TileHapticsViewModel$createStateAwareExpandable$1(this);
        final TileHapticsViewModel$createStateAwareExpandable$2 tileHapticsViewModel$createStateAwareExpandable$2 = new TileHapticsViewModel$createStateAwareExpandable$2(this);
        final TileHapticsViewModel$createStateAwareExpandable$3 tileHapticsViewModel$createStateAwareExpandable$3 = new TileHapticsViewModel$createStateAwareExpandable$3(this);
        final TileHapticsViewModel$createStateAwareExpandable$4 tileHapticsViewModel$createStateAwareExpandable$4 = new TileHapticsViewModel$createStateAwareExpandable$4(this);
        return new Expandable() { // from class: com.android.systemui.haptics.msdl.qs.StateAwareExpandableKt$withStateAwareness$3
            @Override // com.android.systemui.animation.Expandable
            public final ActivityTransitionAnimator.Controller activityTransitionController(Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num2, boolean z) {
                ActivityTransitionAnimator.Controller activityTransitionController = Expandable.this.activityTransitionController(num, transitionCookie, componentName, num2, z);
                if (activityTransitionController != null) {
                    return new ActivityTransitionAnimator.Controller(tileHapticsViewModel$createStateAwareExpandable$3, tileHapticsViewModel$createStateAwareExpandable$4) { // from class: com.android.systemui.haptics.msdl.qs.StateAwareExpandableKt$withStateAwareness$1
                        public final /* synthetic */ ActivityTransitionAnimator.Controller $$delegate_0;
                        public final /* synthetic */ Function0 $onActivityLaunchTransitionEnd;
                        public final /* synthetic */ Function0 $onActivityLaunchTransitionStart;

                        {
                            this.$onActivityLaunchTransitionStart = r2;
                            this.$onActivityLaunchTransitionEnd = r3;
                            this.$$delegate_0 = ActivityTransitionAnimator.Controller.this;
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
                            ActivityTransitionAnimator.Controller.this.onTransitionAnimationCancelled();
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final void onTransitionAnimationEnd(boolean z2) {
                            this.$onActivityLaunchTransitionEnd.invoke();
                            ActivityTransitionAnimator.Controller.this.onTransitionAnimationEnd(z2);
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
                            this.$$delegate_0.onTransitionAnimationProgress(state, f, f2);
                        }

                        @Override // com.android.systemui.animation.TransitionAnimator.Controller
                        public final void onTransitionAnimationStart(boolean z2) {
                            this.$onActivityLaunchTransitionStart.invoke();
                            ActivityTransitionAnimator.Controller.this.onTransitionAnimationStart(z2);
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
                DialogTransitionAnimator.Controller dialogTransitionController = Expandable.this.dialogTransitionController(dialogCuj);
                if (dialogTransitionController != null) {
                    return new DialogTransitionAnimator.Controller(tileHapticsViewModel$createStateAwareExpandable$1, tileHapticsViewModel$createStateAwareExpandable$2) { // from class: com.android.systemui.haptics.msdl.qs.StateAwareExpandableKt$withStateAwareness$2
                        public final /* synthetic */ DialogTransitionAnimator.Controller $$delegate_0;
                        public final /* synthetic */ Function0 $onDialogDrawingEnd;
                        public final /* synthetic */ Function0 $onDialogDrawingStart;

                        {
                            this.$onDialogDrawingStart = r2;
                            this.$onDialogDrawingEnd = r3;
                            this.$$delegate_0 = DialogTransitionAnimator.Controller.this;
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
                            DialogTransitionAnimator.Controller.this.startDrawingInOverlayOf(viewGroup);
                        }

                        @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                        public final void stopDrawingInOverlay() {
                            this.$onDialogDrawingEnd.invoke();
                            DialogTransitionAnimator.Controller.this.stopDrawingInOverlay();
                        }
                    };
                }
                return null;
            }
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x005d, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$onActivated$1 r0 = (com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$onActivated$1 r0 = new com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$onActivated$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L40
            if (r2 == r4) goto L38
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2e:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.haptics.msdl.qs.TileHapticsViewModel r5 = (com.android.systemui.haptics.msdl.qs.TileHapticsViewModel) r5
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L36
            goto L60
        L36:
            r6 = move-exception
            goto L66
        L38:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.haptics.msdl.qs.TileHapticsViewModel r5 = (com.android.systemui.haptics.msdl.qs.TileHapticsViewModel) r5
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L36
            goto L55
        L40:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge r6 = r5.hapticsState     // Catch: java.lang.Throwable -> L36
            com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$onActivated$2 r2 = new com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$onActivated$2     // Catch: java.lang.Throwable -> L36
            r2.<init>()     // Catch: java.lang.Throwable -> L36
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L36
            r0.label = r4     // Catch: java.lang.Throwable -> L36
            java.lang.Object r6 = r6.collect(r2, r0)     // Catch: java.lang.Throwable -> L36
            if (r6 != r1) goto L55
            goto L5f
        L55:
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L36
            r0.label = r3     // Catch: java.lang.Throwable -> L36
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)     // Catch: java.lang.Throwable -> L36
            if (r6 != r1) goto L60
        L5f:
            return r1
        L60:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L36
            r6.<init>()     // Catch: java.lang.Throwable -> L36
            throw r6     // Catch: java.lang.Throwable -> L36
        L66:
            kotlinx.coroutines.flow.StateFlowImpl r0 = r5.tileInteractionState
            com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$TileInteractionState r1 = com.android.systemui.haptics.msdl.qs.TileHapticsViewModel.TileInteractionState.IDLE
            r0.setValue(r1)
            kotlinx.coroutines.flow.StateFlowImpl r5 = r5.tileAnimationState
            com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$TileAnimationState r0 = com.android.systemui.haptics.msdl.qs.TileHapticsViewModel.TileAnimationState.IDLE
            r5.setValue(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
