package com.android.systemui.dreams;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.service.dreams.Flags;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ServiceLifecycleDispatcher;
import androidx.lifecycle.ViewModelStore;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.PhoneWindow;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.ambient.touch.dagger.AmbientTouchComponent;
import com.android.systemui.ambient.touch.scrim.ScrimManager;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.complication.dagger.ComplicationComponent;
import com.android.systemui.dreams.DreamOverlayService;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.dagger.ComplicationComponent;
import com.android.systemui.dreams.dagger.DreamModule$$ExternalSyntheticLambda0;
import com.android.systemui.dreams.dagger.DreamOverlayComponent;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class DreamOverlayService extends android.service.dreams.DreamOverlayService implements LifecycleOwner {
    public static final boolean DEBUG = Log.isLoggable("DreamOverlayService", 3);
    public final AmbientTouchComponent mAmbientTouchComponent;
    public boolean mBouncerShowing;
    public final AnonymousClass3 mBouncerShowingConsumer;
    public boolean mCommunalAvailable;
    public final CommunalInteractor mCommunalInteractor;
    public boolean mCommunalVisible;
    public final AnonymousClass2 mCommunalVisibleConsumer;
    public final Context mContext;
    public boolean mDestroyed;
    public final ServiceLifecycleDispatcher mDispatcher;
    public final DreamOverlayCallbackController mDreamOverlayCallbackController;
    public final DreamOverlayComponent mDreamOverlayComponent;
    public DreamOverlayContainerViewController mDreamOverlayContainerViewController;
    public final DelayableExecutor mExecutor;
    public final DreamOverlayStateController.Callback mExitAnimationFinishedCallback;
    public final ComponentName mHomeControlPanelDreamComponent;
    public final DreamOverlayService$$ExternalSyntheticLambda0 mIsCommunalAvailableCallback;
    public final KeyguardUpdateMonitorCallback mKeyguardCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LifecycleRegistry mLifecycleRegistry;
    public final ComponentName mLowLightDreamComponent;
    public final ScrimManager mScrimManager;
    public boolean mShadeExpanded;
    public boolean mStarted;
    public final DreamOverlayStateController mStateController;
    public final SystemDialogsCloser mSystemDialogsCloser;
    public TouchMonitor mTouchMonitor;
    public final UiEventLogger mUiEventLogger;
    public Window mWindow;
    public final WindowManager mWindowManager;
    public final String mWindowTitle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.dreams.DreamOverlayService$1, reason: invalid class name */
    class AnonymousClass1 extends KeyguardUpdateMonitorCallback {
        public AnonymousClass1() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onShadeExpandedChanged(final boolean z) {
            DreamOverlayService.this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DreamOverlayService.AnonymousClass1 anonymousClass1 = DreamOverlayService.AnonymousClass1.this;
                    boolean z2 = z;
                    DreamOverlayService dreamOverlayService = DreamOverlayService.this;
                    if (dreamOverlayService.mShadeExpanded == z2) {
                        return;
                    }
                    dreamOverlayService.mShadeExpanded = z2;
                    dreamOverlayService.updateLifecycleStateLocked();
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.dreams.DreamOverlayService$2, reason: invalid class name */
    public final class AnonymousClass2 implements Consumer {
        public AnonymousClass2() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            DreamOverlayService.this.mExecutor.execute(new DreamOverlayService$2$$ExternalSyntheticLambda0(this, (Boolean) obj));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.dreams.DreamOverlayService$3, reason: invalid class name */
    public final class AnonymousClass3 implements Consumer {
        public AnonymousClass3() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            DreamOverlayService.this.mExecutor.execute(new DreamOverlayService$2$$ExternalSyntheticLambda0(this, (Boolean) obj));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public enum DreamOverlayEvent implements UiEventLogger.UiEventEnum {
        DREAM_OVERLAY_ENTER_START(989),
        DREAM_OVERLAY_COMPLETE_START(990);

        private final int mId;

        DreamOverlayEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public DreamOverlayService(Context context, DreamOverlayLifecycleOwner dreamOverlayLifecycleOwner, DelayableExecutor delayableExecutor, WindowManager windowManager, ComplicationComponent.Factory factory, ComplicationComponent.Factory factory2, DreamOverlayComponent.Factory factory3, AmbientTouchComponent.Factory factory4, DreamOverlayStateController dreamOverlayStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, ScrimManager scrimManager, CommunalInteractor communalInteractor, SystemDialogsCloser systemDialogsCloser, UiEventLogger uiEventLogger, TouchInsetManager touchInsetManager, ComponentName componentName, ComponentName componentName2, DreamOverlayCallbackController dreamOverlayCallbackController, KeyguardInteractor keyguardInteractor, String str) {
        super(delayableExecutor);
        this.mStarted = false;
        this.mDestroyed = false;
        this.mShadeExpanded = false;
        this.mCommunalVisible = false;
        this.mBouncerShowing = false;
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = new ServiceLifecycleDispatcher(this);
        this.mDispatcher = serviceLifecycleDispatcher;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean z;
                DreamOverlayService dreamOverlayService = DreamOverlayService.this;
                boolean z2 = DreamOverlayService.DEBUG;
                dreamOverlayService.getClass();
                dreamOverlayService.mCommunalAvailable = ((Boolean) obj).booleanValue();
                if (dreamOverlayService.mStarted && Flags.dreamWakeRedirect()) {
                    if (dreamOverlayService.mCommunalAvailable) {
                        com.android.systemui.Flags.FEATURE_FLAGS.getClass();
                        z = true;
                    } else {
                        z = false;
                    }
                    dreamOverlayService.redirectWake(z);
                }
            }
        };
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mKeyguardCallback = anonymousClass1;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mExitAnimationFinishedCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.dreams.DreamOverlayService.4
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onStateChanged() {
                DreamOverlayService dreamOverlayService = DreamOverlayService.this;
                if (dreamOverlayService.mStateController.containsState(8)) {
                    return;
                }
                DreamOverlayStateController dreamOverlayStateController2 = dreamOverlayService.mStateController;
                dreamOverlayStateController2.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda3(dreamOverlayStateController2, dreamOverlayService.mExitAnimationFinishedCallback, 0));
                dreamOverlayService.resetCurrentDreamOverlayLocked();
            }
        };
        this.mContext = context;
        this.mExecutor = delayableExecutor;
        this.mWindowManager = windowManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mScrimManager = scrimManager;
        this.mLowLightDreamComponent = componentName;
        this.mHomeControlPanelDreamComponent = componentName2;
        keyguardUpdateMonitor.registerCallback(anonymousClass1);
        this.mStateController = dreamOverlayStateController;
        this.mUiEventLogger = uiEventLogger;
        this.mDreamOverlayCallbackController = dreamOverlayCallbackController;
        this.mWindowTitle = str;
        this.mCommunalInteractor = communalInteractor;
        this.mSystemDialogsCloser = systemDialogsCloser;
        com.android.systemui.complication.dagger.ComplicationComponent create = factory.create(dreamOverlayLifecycleOwner, new DreamOverlayService$$ExternalSyntheticLambda1(), new ViewModelStore(), touchInsetManager);
        com.android.systemui.dreams.complication.dagger.ComplicationComponent create2 = factory2.create(create.getVisibilityController(), touchInsetManager);
        DreamOverlayComponent create3 = factory3.create(dreamOverlayLifecycleOwner, create.getComplicationHostViewController(), touchInsetManager);
        this.mDreamOverlayComponent = create3;
        this.mAmbientTouchComponent = factory4.create(dreamOverlayLifecycleOwner, new HashSet(Arrays.asList(create2.getHideComplicationTouchHandler(), create3.getCommunalTouchHandler())));
        this.mLifecycleRegistry = dreamOverlayLifecycleOwner.registry;
        delayableExecutor.execute(new DreamOverlayService$$ExternalSyntheticLambda2(this, 0));
        LifecycleRegistry lifecycleRegistry = serviceLifecycleDispatcher.registry;
        JavaAdapterKt.collectFlow(lifecycleRegistry, communalInteractor.isCommunalAvailable, consumer);
        JavaAdapterKt.collectFlow(lifecycleRegistry, communalInteractor.isCommunalVisible, anonymousClass2);
        JavaAdapterKt.collectFlow(lifecycleRegistry, keyguardInteractor.primaryBouncerShowing, anonymousClass3);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.mDispatcher.registry;
    }

    public final void onComeToFront() {
        DreamOverlayContainerViewController dreamOverlayContainerViewController = this.mDreamOverlayContainerViewController;
        if (dreamOverlayContainerViewController != null && dreamOverlayContainerViewController.mAnyBouncerShowing) {
            this.mScrimManager.mCurrentController.expand(new ShadeExpansionChangeEvent(1.0f, false, true));
        }
        ((DreamModule$$ExternalSyntheticLambda0) this.mSystemDialogsCloser).f$0.closeSystemDialogs();
        CommunalInteractor communalInteractor = this.mCommunalInteractor;
        ((CommunalSceneRepositoryImpl) communalInteractor.communalSceneInteractor.communalSceneRepository).changeScene(CommunalScenes.Blank, null);
    }

    public final void onCreate() {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.mDispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_CREATE);
        super.onCreate();
    }

    public final void onDestroy() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardCallback);
        this.mExecutor.execute(new DreamOverlayService$$ExternalSyntheticLambda2(this, 1));
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.mDispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_STOP);
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_DESTROY);
        super.onDestroy();
    }

    public final void onEndDream() {
        resetCurrentDreamOverlayLocked();
    }

    public final void onStart(Intent intent, int i) {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.mDispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_START);
        super.onStart(intent, i);
    }

    public final void onStartDream(WindowManager.LayoutParams layoutParams) {
        ViewGroup viewGroup;
        this.mLifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
        this.mUiEventLogger.log(DreamOverlayEvent.DREAM_OVERLAY_ENTER_START);
        if (this.mDestroyed) {
            return;
        }
        if (this.mStarted) {
            resetCurrentDreamOverlayLocked();
        }
        this.mDreamOverlayContainerViewController = this.mDreamOverlayComponent.getDreamOverlayContainerViewController();
        TouchMonitor touchMonitor = this.mAmbientTouchComponent.getTouchMonitor();
        this.mTouchMonitor = touchMonitor;
        if (touchMonitor.mInitialized) {
            throw new IllegalStateException("TouchMonitor already initialized");
        }
        touchMonitor.mLifecycle.addObserver(touchMonitor.mLifecycleObserver);
        com.android.systemui.Flags.FEATURE_FLAGS.getClass();
        boolean z = true;
        touchMonitor.mInitialized = true;
        final DreamOverlayStateController dreamOverlayStateController = this.mStateController;
        final boolean shouldShowComplications = shouldShowComplications();
        dreamOverlayStateController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayStateController dreamOverlayStateController2 = DreamOverlayStateController.this;
                boolean z2 = shouldShowComplications;
                DreamLogger dreamLogger = dreamOverlayStateController2.mLogger;
                dreamLogger.getClass();
                DreamLogger$logShouldShowComplications$1 dreamLogger$logShouldShowComplications$1 = new Function1() { // from class: com.android.systemui.dreams.DreamLogger$logShouldShowComplications$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Dream overlay should show complications: ", ((LogMessage) obj).getBool1());
                    }
                };
                LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, dreamLogger$logShouldShowComplications$1, null);
                obtain.setBool1(z2);
                dreamLogger.getBuffer().commit(obtain);
                dreamOverlayStateController2.notifyCallbacksLocked(new DreamOverlayStateController$$ExternalSyntheticLambda0(2));
            }
        });
        PhoneWindow phoneWindow = new PhoneWindow(this.mContext);
        this.mWindow = phoneWindow;
        phoneWindow.setTitle(this.mWindowTitle);
        this.mWindow.setAttributes(layoutParams);
        this.mWindow.setWindowManager(null, layoutParams.token, "DreamOverlay", true);
        this.mWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mWindow.clearFlags(Integer.MIN_VALUE);
        this.mWindow.addFlags(8);
        this.mWindow.addPrivateFlags(16);
        this.mWindow.requestFeature(1);
        this.mWindow.getDecorView().getWindowInsetsController().hide(WindowInsets.Type.systemBars());
        this.mWindow.setDecorFitsSystemWindows(false);
        if (DEBUG) {
            Log.d("DreamOverlayService", "adding overlay window to dream");
        }
        this.mDreamOverlayContainerViewController.init();
        View containerView = this.mDreamOverlayContainerViewController.getContainerView();
        if (containerView != null && (viewGroup = (ViewGroup) containerView.getParent()) != null) {
            Log.w("DreamOverlayService", "Removing dream overlay container view parent!");
            viewGroup.removeView(containerView);
        }
        this.mWindow.setContentView(this.mDreamOverlayContainerViewController.getContainerView());
        try {
            this.mWindowManager.addView(this.mWindow.getDecorView(), this.mWindow.getAttributes());
            updateLifecycleStateLocked();
            this.mStateController.setOverlayActive(true);
            ComponentName dreamComponent = getDreamComponent();
            this.mStateController.setLowLightActive(dreamComponent != null && dreamComponent.equals(this.mLowLightDreamComponent));
            DreamOverlayStateController dreamOverlayStateController2 = this.mStateController;
            boolean z2 = dreamComponent != null && dreamComponent.equals(this.mHomeControlPanelDreamComponent);
            dreamOverlayStateController2.getClass();
            dreamOverlayStateController2.modifyState(z2 ? 2 : 1, 64);
            this.mUiEventLogger.log(DreamOverlayEvent.DREAM_OVERLAY_COMPLETE_START);
            DreamOverlayCallbackController dreamOverlayCallbackController = this.mDreamOverlayCallbackController;
            dreamOverlayCallbackController.isDreaming = true;
            for (KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1 keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1 : dreamOverlayCallbackController.callbacks) {
                keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1.getClass();
                ChannelExt channelExt = ChannelExt.INSTANCE;
                Boolean bool = Boolean.TRUE;
                channelExt.getClass();
                ChannelExt.trySendWithFailureLogging(keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1.$$this$conflatedCallbackFlow, bool, "KeyguardRepositoryImpl", "updated isDreamingWithOverlay");
            }
            this.mStarted = true;
            if (Flags.dreamWakeRedirect()) {
                if (this.mCommunalAvailable) {
                    com.android.systemui.Flags.FEATURE_FLAGS.getClass();
                } else {
                    z = false;
                }
                redirectWake(z);
            }
        } catch (WindowManager.BadTokenException e) {
            Log.e("DreamOverlayService", "Dream activity window invalid: " + layoutParams.packageName, e);
            resetCurrentDreamOverlayLocked();
        }
    }

    public final void onWakeRequested() {
        CommunalInteractor communalInteractor = this.mCommunalInteractor;
        ((CommunalSceneRepositoryImpl) communalInteractor.communalSceneInteractor.communalSceneRepository).changeScene(CommunalScenes.Communal, null);
    }

    public final void onWakeUp() {
        if (this.mDreamOverlayContainerViewController != null) {
            DreamOverlayCallbackController dreamOverlayCallbackController = this.mDreamOverlayCallbackController;
            dreamOverlayCallbackController.isDreaming = false;
            for (KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1 keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1 : dreamOverlayCallbackController.callbacks) {
                keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1.getClass();
                ChannelExt channelExt = ChannelExt.INSTANCE;
                Boolean bool = Boolean.FALSE;
                channelExt.getClass();
                ChannelExt.trySendWithFailureLogging(keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1.$$this$conflatedCallbackFlow, bool, "KeyguardRepositoryImpl", "updated isDreamingWithOverlay");
            }
            DreamOverlayContainerViewController dreamOverlayContainerViewController = this.mDreamOverlayContainerViewController;
            if (dreamOverlayContainerViewController.mWakingUpFromSwipe) {
                return;
            }
            DreamOverlayAnimationsController dreamOverlayAnimationsController = dreamOverlayContainerViewController.mDreamOverlayAnimationsController;
            dreamOverlayAnimationsController.cancelAnimations();
            dreamOverlayAnimationsController.mOverlayStateController.modifyState(2, 8);
        }
    }

    public final void resetCurrentDreamOverlayLocked() {
        Window window;
        if (this.mStateController.containsState(8)) {
            this.mStateController.addCallback(this.mExitAnimationFinishedCallback);
            return;
        }
        if (this.mStarted && (window = this.mWindow) != null) {
            try {
                this.mWindowManager.removeView(window.getDecorView());
            } catch (IllegalArgumentException e) {
                Log.e("DreamOverlayService", "Error removing decor view when resetting overlay", e);
            }
        }
        this.mStateController.setOverlayActive(false);
        this.mStateController.setLowLightActive(false);
        DreamOverlayStateController dreamOverlayStateController = this.mStateController;
        dreamOverlayStateController.getClass();
        dreamOverlayStateController.modifyState(1, 4);
        this.mDreamOverlayContainerViewController = null;
        TouchMonitor touchMonitor = this.mTouchMonitor;
        if (touchMonitor != null) {
            if (!touchMonitor.mInitialized) {
                throw new IllegalStateException("TouchMonitor not initialized");
            }
            touchMonitor.stopMonitoring(true);
            touchMonitor.mLifecycle.removeObserver(touchMonitor.mLifecycleObserver);
            com.android.systemui.Flags.FEATURE_FLAGS.getClass();
            touchMonitor.mInitialized = false;
            this.mTouchMonitor = null;
        }
        this.mWindow = null;
        this.mStarted = false;
    }

    public final void updateLifecycleStateLocked() {
        LifecycleRegistry lifecycleRegistry = this.mLifecycleRegistry;
        Lifecycle.State state = lifecycleRegistry.state;
        Lifecycle.State state2 = Lifecycle.State.RESUMED;
        if (state == state2 || state == Lifecycle.State.STARTED) {
            if (this.mShadeExpanded || this.mCommunalVisible || this.mBouncerShowing) {
                state2 = Lifecycle.State.STARTED;
            }
            lifecycleRegistry.setCurrentState(state2);
        }
    }
}
