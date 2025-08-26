package com.android.systemui.statusbar.phone.ongoingactivity;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.TouchInterceptFrameLayout;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController;
import com.android.systemui.statusbar.phone.ongoingactivity.animation.ViewPropertyCapture;
import com.android.systemui.statusbar.phone.ongoingactivity.animation.VisibilityTransition;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.scheduling.DefaultScheduler;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes3.dex */
public final class ChipAnimationController {
    public static final PathInterpolator AlPHA_INTERPOLATOR;
    public static final PathInterpolator CARD_TRANSITION_COLLAPSE_INTERPOLATOR;
    public static final PathInterpolator CARD_TRANSITION_EXPAND_INTERPOLATOR;
    public static final PathInterpolator CHANGE_BOUND_INTERPOLATOR;
    public final Map animationMap;
    public final MutexImpl animationMutex;
    public final View clockView;
    public final ContextScope coroutineScope;
    public View hideTransitionView;
    public final Function0 isLeftClockPosition;
    public final NotificationIconAreaController notificationIconAreaController;
    public final View onGoingActivityChip;
    public final View onGoingCallChip;
    public final OngoingActivityController ongoingActivityController;
    public final PhoneStatusBarView statusBar;
    public final TouchInterceptFrameLayout touchInterceptActivityChip;
    public final TouchInterceptFrameLayout touchInterceptCallChip;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateChipHide$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $animate;
        final /* synthetic */ int $state;
        final /* synthetic */ View $v;
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(View view, int i, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$v = view;
            this.$state = i;
            this.$animate = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ChipAnimationController.this.new AnonymousClass1(this.$v, this.$state, this.$animate, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Can't wrap try/catch for region: R(7:0|2|(5:(1:(1:(7:6|53|7|(5:33|(1:35)(1:36)|37|38|39)|40|41|42)(2:11|12))(1:13))(3:14|(1:17)|22)|54|20|(5:23|(7:25|30|33|(0)(0)|37|38|39)|40|41|42)|22)|18|51|19|(1:(0))) */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x00f1, code lost:
        
            r15 = r14;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00f3, code lost:
        
            r14 = move-exception;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00d3 A[Catch: all -> 0x0024, TryCatch #1 {all -> 0x0024, blocks: (B:7:0x001f, B:25:0x00ba, B:27:0x00be, B:30:0x00c3, B:33:0x00ca, B:35:0x00d3, B:37:0x00da, B:36:0x00d7, B:40:0x00e2), top: B:53:0x001f }] */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00d7 A[Catch: all -> 0x0024, TryCatch #1 {all -> 0x0024, blocks: (B:7:0x001f, B:25:0x00ba, B:27:0x00be, B:30:0x00c3, B:33:0x00ca, B:35:0x00d3, B:37:0x00da, B:36:0x00d7, B:40:0x00e2), top: B:53:0x001f }] */
        /* JADX WARN: Type inference failed for: r9v3, types: [kotlinx.coroutines.sync.Mutex] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            ChipAnimationController chipAnimationController;
            MutexImpl mutexImpl;
            boolean z;
            View view;
            int i;
            Throwable th;
            Mutex mutex;
            String strViewInfo;
            ChipAnimationController chipAnimationController2;
            int i2;
            View view2;
            boolean z2;
            TouchInterceptFrameLayout touchInterceptFrameLayout;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i3 = this.label;
            try {
                if (i3 == 0) {
                    ResultKt.throwOnFailure(obj);
                    chipAnimationController = ChipAnimationController.this;
                    mutexImpl = chipAnimationController.animationMutex;
                    View view3 = this.$v;
                    int i4 = this.$state;
                    z = this.$animate;
                    this.L$0 = mutexImpl;
                    this.L$1 = chipAnimationController;
                    this.L$2 = view3;
                    this.I$0 = i4;
                    this.Z$0 = z;
                    this.label = 1;
                    if (mutexImpl.lock(this) != coroutineSingletons) {
                        view = view3;
                        i = i4;
                    }
                    return coroutineSingletons;
                }
                if (i3 != 1) {
                    if (i3 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    z2 = this.Z$0;
                    i2 = this.I$0;
                    view2 = (View) this.L$2;
                    chipAnimationController2 = (ChipAnimationController) this.L$1;
                    mutex = (Mutex) this.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                        if (z2 && (((touchInterceptFrameLayout = chipAnimationController2.touchInterceptCallChip) == null || !touchInterceptFrameLayout.isTouchInProgress) && view2.getVisibility() != i2)) {
                            if (view2.equals(chipAnimationController2.onGoingCallChip)) {
                                ChipAnimationController.access$handleOnGoingActivityChipAnimation(chipAnimationController2, false);
                            } else {
                                ChipAnimationController.access$handleOnGoingCallChipAnimation(chipAnimationController2, false);
                            }
                            Unit unit = Unit.INSTANCE;
                            mutex.unlock(null);
                            return Unit.INSTANCE;
                        }
                        PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                        chipAnimationController2.hideView(view2, i2);
                        Unit unit2 = Unit.INSTANCE;
                        mutex.unlock(null);
                        return unit2;
                    } catch (Throwable th2) {
                        th = th2;
                        mutex.unlock(null);
                        throw th;
                    }
                }
                boolean z3 = this.Z$0;
                i = this.I$0;
                view = (View) this.L$2;
                ChipAnimationController chipAnimationController3 = (ChipAnimationController) this.L$1;
                ?? r9 = (Mutex) this.L$0;
                ResultKt.throwOnFailure(obj);
                chipAnimationController = chipAnimationController3;
                z = z3;
                mutexImpl = r9;
                Log.i("{ChipAnimationController}", "animateChipHide() view:" + strViewInfo + " state:" + i + " animate:" + z + " visibility:" + view.getVisibility());
                this.L$0 = mutexImpl;
                this.L$1 = chipAnimationController;
                this.L$2 = view;
                this.I$0 = i;
                this.Z$0 = z;
                this.label = 2;
                if (ChipAnimationController.access$cancelAnimation(chipAnimationController, view, this) != coroutineSingletons) {
                    chipAnimationController2 = chipAnimationController;
                    mutex = mutexImpl;
                    i2 = i;
                    view2 = view;
                    z2 = z;
                    if (z2) {
                        if (view2.equals(chipAnimationController2.onGoingCallChip)) {
                        }
                        Unit unit3 = Unit.INSTANCE;
                        mutex.unlock(null);
                        return Unit.INSTANCE;
                    }
                    PathInterpolator pathInterpolator2 = ChipAnimationController.AlPHA_INTERPOLATOR;
                    chipAnimationController2.hideView(view2, i2);
                    Unit unit22 = Unit.INSTANCE;
                    mutex.unlock(null);
                    return unit22;
                }
                return coroutineSingletons;
            } catch (Throwable th3) {
                th = th3;
                mutex = mutexImpl;
                mutex.unlock(null);
                throw th;
            }
            PathInterpolator pathInterpolator3 = ChipAnimationController.AlPHA_INTERPOLATOR;
            chipAnimationController.getClass();
            strViewInfo = ChipAnimationController.viewInfo(view);
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateChipShow$1, reason: invalid class name and case insensitive filesystem */
    final class C10971 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $animate;
        final /* synthetic */ View $v;
        Object L$0;
        Object L$1;
        Object L$2;
        boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10971(View view, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$v = view;
            this.$animate = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ChipAnimationController.this.new C10971(this.$v, this.$animate, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10971) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00ac A[Catch: all -> 0x0027, TryCatch #0 {all -> 0x0027, blocks: (B:7:0x0022, B:22:0x00a4, B:24:0x00ac, B:26:0x00b3, B:29:0x00ba, B:31:0x00c2, B:36:0x00e5, B:32:0x00c6, B:34:0x00ce, B:35:0x00d2, B:39:0x00ed), top: B:50:0x0022 }] */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00c2 A[Catch: all -> 0x0027, TryCatch #0 {all -> 0x0027, blocks: (B:7:0x0022, B:22:0x00a4, B:24:0x00ac, B:26:0x00b3, B:29:0x00ba, B:31:0x00c2, B:36:0x00e5, B:32:0x00c6, B:34:0x00ce, B:35:0x00d2, B:39:0x00ed), top: B:50:0x0022 }] */
        /* JADX WARN: Removed duplicated region for block: B:32:0x00c6 A[Catch: all -> 0x0027, TryCatch #0 {all -> 0x0027, blocks: (B:7:0x0022, B:22:0x00a4, B:24:0x00ac, B:26:0x00b3, B:29:0x00ba, B:31:0x00c2, B:36:0x00e5, B:32:0x00c6, B:34:0x00ce, B:35:0x00d2, B:39:0x00ed), top: B:50:0x0022 }] */
        /* JADX WARN: Type inference failed for: r10v4, types: [kotlinx.coroutines.sync.Mutex] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            ChipAnimationController chipAnimationController;
            MutexImpl mutexImpl;
            View view;
            boolean z;
            Throwable th;
            Mutex mutex;
            String strViewInfo;
            View view2;
            boolean z2;
            ChipAnimationController chipAnimationController2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        chipAnimationController = ChipAnimationController.this;
                        mutexImpl = chipAnimationController.animationMutex;
                        view = this.$v;
                        z = this.$animate;
                        this.L$0 = mutexImpl;
                        this.L$1 = chipAnimationController;
                        this.L$2 = view;
                        this.Z$0 = z;
                        this.label = 1;
                        if (mutexImpl.lock(this) != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        z2 = this.Z$0;
                        view2 = (View) this.L$2;
                        chipAnimationController2 = (ChipAnimationController) this.L$1;
                        mutex = (Mutex) this.L$0;
                        try {
                            ResultKt.throwOnFailure(obj);
                            if (Intrinsics.areEqual(chipAnimationController2.hideTransitionView, view2)) {
                                TransitionManager.endTransitions(chipAnimationController2.statusBar);
                            }
                            if (z2 && view2.getVisibility() != 0) {
                                if (!view2.equals(chipAnimationController2.onGoingCallChip)) {
                                    ChipAnimationController.access$handleOnGoingCallChipAnimation(chipAnimationController2, true);
                                } else if (view2.equals(chipAnimationController2.onGoingActivityChip)) {
                                    ChipAnimationController.access$handleOnGoingActivityChipAnimation(chipAnimationController2, true);
                                } else {
                                    Boxing.boxInt(Log.d("{ChipAnimationController}", "animateChipShow() else case v:" + view2));
                                }
                                Unit unit = Unit.INSTANCE;
                                mutex.unlock(null);
                                return Unit.INSTANCE;
                            }
                            chipAnimationController2.showView(view2);
                            Unit unit2 = Unit.INSTANCE;
                            mutex.unlock(null);
                            return unit2;
                        } catch (Throwable th2) {
                            th = th2;
                            mutex.unlock(null);
                            throw th;
                        }
                    }
                    boolean z3 = this.Z$0;
                    view = (View) this.L$2;
                    ChipAnimationController chipAnimationController3 = (ChipAnimationController) this.L$1;
                    ?? r10 = (Mutex) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    chipAnimationController = chipAnimationController3;
                    z = z3;
                    mutexImpl = r10;
                    Log.i("{ChipAnimationController}", "animateChipShow() view:" + strViewInfo + " animate:" + z + " view_visibility:" + view.getVisibility());
                    this.L$0 = mutexImpl;
                    this.L$1 = chipAnimationController;
                    this.L$2 = view;
                    this.Z$0 = z;
                    this.label = 2;
                    if (ChipAnimationController.access$cancelAnimation(chipAnimationController, view, this) != coroutineSingletons) {
                        mutex = mutexImpl;
                        view2 = view;
                        z2 = z;
                        chipAnimationController2 = chipAnimationController;
                        if (Intrinsics.areEqual(chipAnimationController2.hideTransitionView, view2)) {
                        }
                        if (z2) {
                            if (!view2.equals(chipAnimationController2.onGoingCallChip)) {
                            }
                            Unit unit3 = Unit.INSTANCE;
                            mutex.unlock(null);
                            return Unit.INSTANCE;
                        }
                        chipAnimationController2.showView(view2);
                        Unit unit22 = Unit.INSTANCE;
                        mutex.unlock(null);
                        return unit22;
                    }
                    return coroutineSingletons;
                } catch (Throwable th3) {
                    th = th3;
                    mutex = mutexImpl;
                    mutex.unlock(null);
                    throw th;
                }
                PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                chipAnimationController.getClass();
                strViewInfo = ChipAnimationController.viewInfo(view);
            } catch (Throwable th4) {
                th = th4;
            }
        }
    }

    static {
        new Companion(null);
        PathInterpolator pathInterpolator = new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f);
        AlPHA_INTERPOLATOR = pathInterpolator;
        CardStackView.Companion companion = CardStackView.Companion;
        companion.getClass();
        CARD_TRANSITION_EXPAND_INTERPOLATOR = CardStackView.expandRootInterpolator;
        companion.getClass();
        CARD_TRANSITION_COLLAPSE_INTERPOLATOR = CardStackView.collapseRootInterpolator;
        CHANGE_BOUND_INTERPOLATOR = pathInterpolator;
    }

    public ChipAnimationController(PhoneStatusBarView phoneStatusBarView, NotificationIconAreaController notificationIconAreaController, OngoingActivityController ongoingActivityController, Function0 function0) {
        this.statusBar = phoneStatusBarView;
        this.notificationIconAreaController = notificationIconAreaController;
        this.ongoingActivityController = ongoingActivityController;
        this.isLeftClockPosition = function0;
        this.clockView = phoneStatusBarView.findViewById(R.id.clock);
        View viewFindViewById = phoneStatusBarView.findViewById(R.id.ongoing_call_chip);
        this.onGoingCallChip = viewFindViewById;
        this.touchInterceptCallChip = viewFindViewById instanceof TouchInterceptFrameLayout ? (TouchInterceptFrameLayout) viewFindViewById : null;
        View viewFindViewById2 = phoneStatusBarView.findViewById(R.id.ongoing_activity_capsule);
        this.onGoingActivityChip = viewFindViewById2;
        this.touchInterceptActivityChip = viewFindViewById2 instanceof TouchInterceptFrameLayout ? (TouchInterceptFrameLayout) viewFindViewById2 : null;
        this.animationMap = new LinkedHashMap();
        Symbol symbol = MutexKt.NO_OWNER;
        this.animationMutex = new MutexImpl(false);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher.immediate);
    }

    public static final Object access$animateSpring(final ChipAnimationController chipAnimationController, final View view, final DynamicAnimation.ViewProperty viewProperty, float f, SuspendLambda suspendLambda) {
        chipAnimationController.getClass();
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(suspendLambda), 1);
        cancellableContinuationImpl.initCancellability();
        final SpringAnimation springAnimation = new SpringAnimation(view, viewProperty, f);
        SpringForce springForce = new SpringForce(f);
        springForce.setDampingRatio(0.808f);
        springForce.setStiffness(75.0f);
        springAnimation.setSpring(springForce);
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateSpring$2$springAnimation$1$2
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f2, float f3) {
                if (cancellableContinuationImpl.isActive()) {
                    DynamicAnimation.ViewProperty viewProperty2 = viewProperty;
                    ChipAnimationController chipAnimationController2 = chipAnimationController;
                    View view2 = view;
                    PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                    chipAnimationController2.getClass();
                    Log.d("{ChipAnimationController}", "animateSpring(" + viewProperty2 + ") End! V:" + ChipAnimationController.viewInfo(view2));
                    CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                    int i = Result.$r8$clinit;
                    cancellableContinuation.resumeWith(Unit.INSTANCE);
                }
            }
        });
        springAnimation.start();
        cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateSpring$2$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                String name = viewProperty.getName();
                View view2 = view;
                PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                chipAnimationController.getClass();
                MediaSessions$H$$ExternalSyntheticOutline0.m("invokeOnCancellation animateSpring(", name, ") Cancelled! V:", ChipAnimationController.viewInfo(view2), "{ChipAnimationController}");
                springAnimation.cancel();
                return Unit.INSTANCE;
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        return result == CoroutineSingletons.COROUTINE_SUSPENDED ? result : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$cancelAnimation(ChipAnimationController chipAnimationController, View view, ContinuationImpl continuationImpl) {
        ChipAnimationController$cancelAnimation$1 chipAnimationController$cancelAnimation$1;
        chipAnimationController.getClass();
        if (continuationImpl instanceof ChipAnimationController$cancelAnimation$1) {
            chipAnimationController$cancelAnimation$1 = (ChipAnimationController$cancelAnimation$1) continuationImpl;
            int i = chipAnimationController$cancelAnimation$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                chipAnimationController$cancelAnimation$1.label = i - Integer.MIN_VALUE;
            } else {
                chipAnimationController$cancelAnimation$1 = new ChipAnimationController$cancelAnimation$1(chipAnimationController, continuationImpl);
            }
        }
        Object obj = chipAnimationController$cancelAnimation$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = chipAnimationController$cancelAnimation$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("cancelAnimation() v:", viewInfo(view), "{ChipAnimationController}");
            Job job = (Job) ((LinkedHashMap) chipAnimationController.animationMap).get(view);
            if (job != null) {
                if (job.isActive()) {
                    chipAnimationController$cancelAnimation$1.L$0 = chipAnimationController;
                    chipAnimationController$cancelAnimation$1.L$1 = view;
                    chipAnimationController$cancelAnimation$1.label = 1;
                    if (JobKt.cancelAndJoin(job, chipAnimationController$cancelAnimation$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    Boxing.boxInt(Log.d("{ChipAnimationController}", "View:" + viewInfo(view) + " Animation already cancelled/completed !"));
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            view = (View) chipAnimationController$cancelAnimation$1.L$1;
            chipAnimationController = (ChipAnimationController) chipAnimationController$cancelAnimation$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        chipAnimationController.animationMap.remove(view);
        return Unit.INSTANCE;
    }

    public static final void access$handleOnGoingActivityChipAnimation(ChipAnimationController chipAnimationController, boolean z) {
        View view = chipAnimationController.onGoingActivityChip;
        Log.d("{ChipAnimationController}", "handleOnGoingActivityChipAnimation() visible:" + (view != null ? Integer.valueOf(view.getVisibility()) : null) + " show:" + z);
        View view2 = chipAnimationController.onGoingActivityChip;
        if (view2 != null) {
            if (!z) {
                chipAnimationController.startChipTransitionAnimation(false, view2, 8, CHANGE_BOUND_INTERPOLATOR, null);
            } else {
                startAnimation$default(chipAnimationController, view2);
                chipAnimationController.startChipTransitionAnimation(true, view2, 0, CHANGE_BOUND_INTERPOLATOR, null);
            }
        }
    }

    public static final void access$handleOnGoingCallChipAnimation(ChipAnimationController chipAnimationController, boolean z) {
        View view = chipAnimationController.onGoingCallChip;
        if (view != null) {
            if (!z) {
                chipAnimationController.startChipTransitionAnimation(false, view, 8, CHANGE_BOUND_INTERPOLATOR, null);
                return;
            }
            startAnimation$default(chipAnimationController, view);
            chipAnimationController.startChipTransitionAnimation(true, view, 0, CHANGE_BOUND_INTERPOLATOR, null);
            chipAnimationController.ongoingActivityController.updateAdapter();
            View view2 = chipAnimationController.onGoingActivityChip;
            if (view2 != null) {
                view2.requestLayout();
            }
        }
    }

    public static void startAnimation$default(final ChipAnimationController chipAnimationController, final View view) {
        StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(view.getVisibility(), "startAnimation() v:", viewInfo(view), " show:true view_visibility:", " required_state:");
        sbM890m.append(0);
        Log.d("{ChipAnimationController}", sbM890m.toString());
        if (view.getVisibility() == 0) {
            Log.i("{ChipAnimationController}", "startAnimation() Already hidden/Visible! Ignore Animation");
            chipAnimationController.showView(view);
            return;
        }
        view.setVisibility(0);
        view.setAlpha(0.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        JobImpl jobImplJob$default = JobKt.Job$default();
        jobImplJob$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                View view2 = view;
                PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                Log.d("{ChipAnimationController}", "Job invokeOnCompletion() show:true");
                this.f$1.showView(view2);
                return Unit.INSTANCE;
            }
        });
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        HandlerContext handlerContext = MainDispatcherLoader.dispatcher;
        handlerContext.getClass();
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(handlerContext, jobImplJob$default)), null, null, new ChipAnimationController$startAnimation$2(chipAnimationController, view, true, 0, null), 3);
        chipAnimationController.animationMap.put(view, jobImplJob$default);
    }

    public static String viewInfo(View view) {
        int id = view.getId();
        return id != -1 ? view.getResources().getResourceEntryName(id) : view.toString();
    }

    public final void animateChipHide(View view, int i, boolean z) {
        BuildersKt.launch$default(this.coroutineScope, null, null, new AnonymousClass1(view, i, z, null), 3);
    }

    public final void animateChipShow(View view, boolean z) {
        this.notificationIconAreaController.setAnimationsEnabled(false);
        BuildersKt.launch$default(this.coroutineScope, null, null, new C10971(view, z, null), 3);
    }

    public final void clockViewNudgeAnimation() {
        final View view;
        if (!((Boolean) this.isLeftClockPosition.invoke()).booleanValue() || (view = this.clockView) == null) {
            return;
        }
        Log.d("{ChipAnimationController}", "nudgeAnimation() view:" + viewInfo(view));
        float fApplyDimension = TypedValue.applyDimension(1, 2.0f, view.getResources().getDisplayMetrics());
        if (this.statusBar.getLayoutDirection() == 1) {
            fApplyDimension = -fApplyDimension;
        }
        SpringForce stiffness = new SpringForce().setDampingRatio(0.85f).setStiffness(400.0f);
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X);
        springAnimation.setSpring(stiffness);
        final float f = 0.45f;
        final float f2 = 100.0f;
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$nudgeAnimation$1
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f3, float f4) {
                SpringForce stiffness2 = new SpringForce().setDampingRatio(f).setStiffness(f2);
                SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X);
                springAnimation2.setSpring(stiffness2);
                final View view2 = view;
                springAnimation2.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$nudgeAnimation$1.1
                    public final void onAnimationEnd(DynamicAnimation dynamicAnimation2, boolean z2, float f5, float f6) {
                        Log.d("{ChipAnimationController}", "nudgeAnimation final v.translationX= " + view2.getTranslationX());
                    }
                });
                springAnimation2.animateToFinalPosition(0.0f);
            }
        });
        springAnimation.animateToFinalPosition(-fApplyDimension);
    }

    public final void hideView(View view, int i) {
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i, "hideView() id:", viewInfo(view), " state:", "{ChipAnimationController}");
        view.setAlpha(0.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setVisibility(i);
        this.notificationIconAreaController.setAnimationsEnabled(true);
    }

    public final void showView(View view) {
        Log.d("{ChipAnimationController}", "showView() id:" + viewInfo(view));
        view.setAlpha(1.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setVisibility(0);
        this.notificationIconAreaController.setAnimationsEnabled(true);
        this.ongoingActivityController.startMarqueeAnimation();
    }

    public final void startChipTransitionAnimation(final boolean z, final View view, int i, Interpolator interpolator, OngoingCardController.AnonymousClass2 anonymousClass2) {
        View view2;
        Log.d("{ChipAnimationController}", "startChipTransitionAnimation() start! show:" + z);
        final TransitionSet transitionSet = new TransitionSet();
        if (anonymousClass2 != null) {
            transitionSet.addTransition(new ViewPropertyCapture(view, anonymousClass2));
        }
        if (!z) {
            VisibilityTransition visibilityTransition = new VisibilityTransition();
            visibilityTransition.addTarget(R.id.ongoing_call_chip);
            transitionSet.addTransition(visibilityTransition);
            this.hideTransitionView = view;
        }
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(z ? 600L : 500L);
        changeBounds.setInterpolator(interpolator);
        transitionSet.addTransition(changeBounds);
        if (Intrinsics.areEqual(view, this.onGoingCallChip) && (view2 = this.onGoingActivityChip) != null && view2.getVisibility() == 0) {
            VisibilityTransition visibilityTransition2 = new VisibilityTransition();
            visibilityTransition2.addTarget(R.id.capsule_item_noti_expanded_info);
            transitionSet.addTransition(visibilityTransition2);
        }
        transitionSet.setOrdering(0);
        PhoneStatusBarView phoneStatusBarView = this.statusBar;
        TransitionManager.endTransitions(phoneStatusBarView);
        transitionSet.addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController.startChipTransitionAnimation.2
            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionCancel(Transition transition) {
                Log.d("{ChipAnimationController}", "onTransitionCancel()");
                transitionSet.removeListener((Transition.TransitionListener) this);
                ChipAnimationController chipAnimationController = this;
                TouchInterceptFrameLayout touchInterceptFrameLayout = chipAnimationController.touchInterceptActivityChip;
                if (touchInterceptFrameLayout != null) {
                    touchInterceptFrameLayout.isAnimationInProgress = false;
                }
                chipAnimationController.notificationIconAreaController.setAnimationsEnabled(true);
                this.hideTransitionView = null;
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                View view3;
                Log.d("{ChipAnimationController}", "onTransitionEnd()");
                transitionSet.removeListener((Transition.TransitionListener) this);
                ChipAnimationController chipAnimationController = this;
                TouchInterceptFrameLayout touchInterceptFrameLayout = chipAnimationController.touchInterceptActivityChip;
                if (touchInterceptFrameLayout != null) {
                    touchInterceptFrameLayout.isAnimationInProgress = false;
                }
                chipAnimationController.notificationIconAreaController.setAnimationsEnabled(true);
                if (!z && Intrinsics.areEqual(view, this.onGoingCallChip) && (view3 = this.onGoingActivityChip) != null && view3.getVisibility() == 0) {
                    this.clockViewNudgeAnimation();
                }
                View view4 = view;
                KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(view4.getLeft(), view4.getRight(), "Transition View() left:", " right:", " top:"), view4.getTop(), " bottom:", view4.getBottom(), "{ChipAnimationController}");
                this.hideTransitionView = null;
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition) {
                View view3;
                Log.d("{ChipAnimationController}", "onTransitionStart()");
                if (z) {
                    ChipAnimationController chipAnimationController = this;
                    View view4 = view;
                    PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                    chipAnimationController.getClass();
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("nudgeClockIfNeeded() view:", ChipAnimationController.viewInfo(view4), "{ChipAnimationController}");
                    if (!view4.equals(chipAnimationController.onGoingActivityChip) && !view4.equals(chipAnimationController.onGoingCallChip)) {
                        Log.d("{ChipAnimationController}", "nudgeClockIfNeeded() skip!");
                    } else if (view4.equals(chipAnimationController.onGoingActivityChip) && (view3 = chipAnimationController.onGoingCallChip) != null && view3.getVisibility() == 0) {
                        Log.d("{ChipAnimationController}", "nudgeClockIfNeeded() skip! call chip is visible");
                    } else {
                        chipAnimationController.clockViewNudgeAnimation();
                    }
                }
                TouchInterceptFrameLayout touchInterceptFrameLayout = this.touchInterceptActivityChip;
                if (touchInterceptFrameLayout != null) {
                    touchInterceptFrameLayout.isAnimationInProgress = true;
                }
            }
        });
        this.notificationIconAreaController.setAnimationsEnabled(false);
        TransitionManager.endTransitions(phoneStatusBarView);
        TransitionManager.beginDelayedTransition(phoneStatusBarView, transitionSet);
        view.setVisibility(i);
        Log.d("{ChipAnimationController}", "startChipTransitionAnimation() End! show:" + z);
    }
}
