package com.android.systemui.statusbar.phone.fragment;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class CollapsedStatusBarFragmentExt implements KeyguardStateController.Callback, BootAnimationFinishedCache.BootAnimationFinishedListener {
    public final ActivityManager activityManager;
    public final DelayableExecutor bgExecutor;
    public final CoroutineScope bgScope;
    public final CollapsedStatusBarFragmentExt$cameraListener$1 cameraListener;
    public final CameraManager cameraManager;
    public final Context context;
    public final Lazy displayLifecycleLazy;
    public final Lazy keyguardStateControllerLazy;
    public boolean lastFullscreenFlagState;
    public final Handler mainHandler;
    public final SecPanelExpansionStateInteractor secPanelExpansionStateInteractor;
    public final Lazy secondScreenUtil;
    public boolean shouldHideIconsForNextAppWindow;
    public final SlimIndicatorViewMediator slimIndicatorViewMediator;
    public final StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager;
    public final StatusBarModeRepositoryStore statusBarModeRepository;
    public CollapsedStatusBarFragment$$ExternalSyntheticLambda1 updateRunnable;
    public String lastFullscreenPkgName = "";
    public String updateStatusBarVisibilitiesCallers = "empty";

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt$onBootAnimationFinished$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CollapsedStatusBarFragmentExt.this.new AnonymousClass1(continuation);
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
                ReadonlyStateFlow readonlyStateFlow = ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) CollapsedStatusBarFragmentExt.this.statusBarModeRepository.getDefaultDisplay())).isInFullscreenMode;
                final CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt = CollapsedStatusBarFragmentExt.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt.onBootAnimationFinished.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) throws SecurityException {
                        String packageName;
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt2 = collapsedStatusBarFragmentExt;
                        float fFloatValue = ((Number) collapsedStatusBarFragmentExt2.secPanelExpansionStateInteractor.shadeFraction.getValue()).floatValue();
                        if (collapsedStatusBarFragmentExt2.lastFullscreenFlagState != zBooleanValue && (!((KeyguardStateControllerImpl) ((KeyguardStateController) collapsedStatusBarFragmentExt2.keyguardStateControllerLazy.get())).mShowing || fFloatValue <= 0.0f)) {
                            if (zBooleanValue) {
                                try {
                                    List<ActivityManager.RunningTaskInfo> runningTasks = collapsedStatusBarFragmentExt2.activityManager.getRunningTasks(1);
                                    List<ActivityManager.RunningTaskInfo> list = runningTasks;
                                    if (list != null && !list.isEmpty()) {
                                        ComponentName componentName = runningTasks.get(0).topActivity;
                                        if (componentName == null || (packageName = componentName.getPackageName()) == null) {
                                            packageName = "";
                                        }
                                        collapsedStatusBarFragmentExt2.lastFullscreenPkgName = packageName;
                                    }
                                    collapsedStatusBarFragmentExt2.printStatusBarInfoLog("fullscreen");
                                } catch (Exception unused) {
                                    collapsedStatusBarFragmentExt2.lastFullscreenPkgName = "";
                                }
                            } else {
                                collapsedStatusBarFragmentExt2.lastFullscreenPkgName = "";
                            }
                            collapsedStatusBarFragmentExt2.lastFullscreenFlagState = zBooleanValue;
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt$cameraListener$1] */
    public CollapsedStatusBarFragmentExt(Context context, CoroutineScope coroutineScope, Handler handler, DelayableExecutor delayableExecutor, BootAnimationFinishedCache bootAnimationFinishedCache, Lazy lazy, Lazy lazy2, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, SlimIndicatorViewMediator slimIndicatorViewMediator, CameraManager cameraManager, StatusBarModeRepositoryStore statusBarModeRepositoryStore, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor, ActivityManager activityManager, Lazy lazy3) {
        this.context = context;
        this.bgScope = coroutineScope;
        this.mainHandler = handler;
        this.bgExecutor = delayableExecutor;
        this.keyguardStateControllerLazy = lazy;
        this.displayLifecycleLazy = lazy2;
        this.statusBarHideIconsForBouncerManager = statusBarHideIconsForBouncerManager;
        this.slimIndicatorViewMediator = slimIndicatorViewMediator;
        this.cameraManager = cameraManager;
        this.statusBarModeRepository = statusBarModeRepositoryStore;
        this.secPanelExpansionStateInteractor = secPanelExpansionStateInteractor;
        this.activityManager = activityManager;
        this.secondScreenUtil = lazy3;
        ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(this);
        this.cameraListener = new CameraManager.AvailabilityCallback() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt$cameraListener$1
            public final void onCameraOpened(String str, String str2) {
                boolean z = ((KeyguardStateControllerImpl) ((KeyguardStateController) this.this$0.keyguardStateControllerLazy.get())).mKeyguardFadingAway || ((KeyguardStateControllerImpl) ((KeyguardStateController) this.this$0.keyguardStateControllerLazy.get())).mKeyguardGoingAway || ((KeyguardStateControllerImpl) ((KeyguardStateController) this.this$0.keyguardStateControllerLazy.get())).mOccluded || ((KeyguardStateControllerImpl) ((KeyguardStateController) this.this$0.keyguardStateControllerLazy.get())).mShowing;
                this.this$0.printStatusBarInfoLog(MotionLayout$$ExternalSyntheticOutline0.m("onCameraOpened(cId:", str, ", pId:", str2, ")"));
                if (z) {
                    CollapsedStatusBarFragmentExt.access$updateHideIconsForNextAppWindow(this.this$0, "CameraManager.AvailabilityCallback");
                }
            }

            public final void onCameraClosed(String str) {
            }
        };
    }

    public static final boolean access$isShouldHidePackage(CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt, String str) {
        collapsedStatusBarFragmentExt.getClass();
        if (str != null && str.length() != 0) {
            if (StringsKt__StringsKt.contains(str, "com.sec.android.app.camera", false) || StringsKt__StringsKt.contains(str, "com.samsung.android.app.routines", false) || StringsKt__StringsKt.contains(str, "com.samsung.android.app.dressroom", false)) {
                return true;
            }
            if (StringsKt__StringsKt.contains(str, "com.sec.android.app.launcher", false) || StringsKt__StringsKt.contains(str, "com.samsung.android.dialer", false)) {
                if (collapsedStatusBarFragmentExt.lastFullscreenFlagState && collapsedStatusBarFragmentExt.lastFullscreenPkgName.equals(str)) {
                    return true;
                }
                if (!DeviceType.isTablet() && ((!BasicRune.BASIC_FOLDABLE_TYPE_FOLD || collapsedStatusBarFragmentExt.context.getResources().getConfiguration().semDisplayDeviceType != 0) && collapsedStatusBarFragmentExt.context.getResources().getConfiguration().orientation == 2)) {
                    return true;
                }
            } else if (collapsedStatusBarFragmentExt.lastFullscreenFlagState && collapsedStatusBarFragmentExt.lastFullscreenPkgName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static final void access$updateHideIconsForNextAppWindow(final CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt, String str) {
        collapsedStatusBarFragmentExt.getClass();
        collapsedStatusBarFragmentExt.printStatusBarInfoLog("updateHideIconsForNextAppWindow(" + str + ") shouldHideIconsForNextAppWindow will be true");
        collapsedStatusBarFragmentExt.shouldHideIconsForNextAppWindow = true;
        collapsedStatusBarFragmentExt.bgExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt$updateHideIconsForNextAppWindow$1
            @Override // java.lang.Runnable
            public final void run() {
                CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt2 = this.this$0;
                collapsedStatusBarFragmentExt2.shouldHideIconsForNextAppWindow = false;
                collapsedStatusBarFragmentExt2.postUpdateStatusBarVisibility();
            }
        }, 300L);
    }

    @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
    public final void onBootAnimationFinished() {
        this.lastFullscreenFlagState = ((Boolean) ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) this.statusBarModeRepository.getDefaultDisplay())).isInFullscreenMode.$$delegate_0.getValue()).booleanValue();
        BuildersKt.launch$default(this.bgScope, null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardShowingChanged() {
        printStatusBarInfoLog("onKeyguardShowingChanged()");
        CollapsedStatusBarFragment$$ExternalSyntheticLambda1 collapsedStatusBarFragment$$ExternalSyntheticLambda1 = this.updateRunnable;
        if (collapsedStatusBarFragment$$ExternalSyntheticLambda1 != null) {
            collapsedStatusBarFragment$$ExternalSyntheticLambda1.run();
        }
    }

    public final void postUpdateStatusBarVisibility() {
        CollapsedStatusBarFragment$$ExternalSyntheticLambda1 collapsedStatusBarFragment$$ExternalSyntheticLambda1 = this.updateRunnable;
        if (collapsedStatusBarFragment$$ExternalSyntheticLambda1 != null) {
            Handler handler = this.mainHandler;
            if (handler.hasCallbacks(collapsedStatusBarFragment$$ExternalSyntheticLambda1)) {
                handler.removeCallbacks(collapsedStatusBarFragment$$ExternalSyntheticLambda1);
            }
            handler.postDelayed(collapsedStatusBarFragment$$ExternalSyntheticLambda1, 200L);
        }
    }

    public final void printStatusBarInfoLog(String str) {
        Lazy lazy = this.keyguardStateControllerLazy;
        boolean z = ((KeyguardStateControllerImpl) ((KeyguardStateController) lazy.get())).mKeyguardFadingAway;
        boolean z2 = ((KeyguardStateControllerImpl) ((KeyguardStateController) lazy.get())).mKeyguardGoingAway;
        boolean z3 = ((KeyguardStateControllerImpl) ((KeyguardStateController) lazy.get())).mOccluded;
        boolean z4 = ((KeyguardStateControllerImpl) ((KeyguardStateController) lazy.get())).mShowing;
        StringBuilder sb = new StringBuilder(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  "));
        sb.append("  shouldHideIconsForNextAppWindow:" + this.shouldHideIconsForNextAppWindow);
        sb.append(", lastFullscreen(pkg:".concat(this.lastFullscreenPkgName));
        sb.append(", flag:" + this.lastFullscreenFlagState + ")");
        StringBuilder sb2 = new StringBuilder(",    keyguard(fa:");
        sb2.append(z);
        sb.append(sb2.toString());
        sb.append(", ga:" + z2);
        sb.append(", oc:" + z3);
        sb.append(", show:" + z4 + ")");
        Log.d("CollapsedStatusBarFragmentExt", sb.toString());
    }
}
