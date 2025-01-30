package com.android.systemui.dreams;

import android.animation.Animator;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.PhoneWindow;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.complication.dagger.ComplicationComponent;
import com.android.systemui.dreams.DreamOverlayService;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.dagger.ComplicationComponent;
import com.android.systemui.dreams.dagger.DreamOverlayComponent;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.Arrays;
import java.util.HashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DreamOverlayService extends android.service.dreams.DreamOverlayService {
    public static final boolean DEBUG = Log.isLoggable("DreamOverlayService", 3);
    public final Context mContext;
    public boolean mDestroyed;
    public final DreamOverlayCallbackController mDreamOverlayCallbackController;
    public final DreamOverlayComponent mDreamOverlayComponent;
    public DreamOverlayContainerViewController mDreamOverlayContainerViewController;
    public final DelayableExecutor mExecutor;
    public final C12762 mExitAnimationFinishedCallback;
    public final KeyguardUpdateMonitorCallback mKeyguardCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LifecycleRegistry mLifecycleRegistry;
    public final ComponentName mLowLightDreamComponent;
    public boolean mStarted;
    public final DreamOverlayStateController mStateController;
    public final UiEventLogger mUiEventLogger;
    public Window mWindow;
    public final WindowManager mWindowManager;
    public final String mWindowTitle;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.dreams.DreamOverlayService$1 */
    public final class C12751 extends KeyguardUpdateMonitorCallback {
        public C12751() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onShadeExpandedChanged(final boolean z) {
            ((ExecutorImpl) DreamOverlayService.this.mExecutor).execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DreamOverlayService.C12751 c12751 = DreamOverlayService.C12751.this;
                    boolean z2 = z;
                    LifecycleRegistry lifecycleRegistry = DreamOverlayService.this.mLifecycleRegistry;
                    Lifecycle.State state = lifecycleRegistry.mState;
                    Lifecycle.State state2 = Lifecycle.State.RESUMED;
                    if (state == state2 || state == Lifecycle.State.STARTED) {
                        if (z2) {
                            state2 = Lifecycle.State.STARTED;
                        }
                        lifecycleRegistry.setCurrentState(state2);
                    }
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Type inference failed for: r7v0, types: [com.android.systemui.dreams.DreamOverlayService$2] */
    public DreamOverlayService(Context context, DreamOverlayLifecycleOwner dreamOverlayLifecycleOwner, DelayableExecutor delayableExecutor, WindowManager windowManager, ComplicationComponent.Factory factory, ComplicationComponent.Factory factory2, DreamOverlayComponent.Factory factory3, DreamOverlayStateController dreamOverlayStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, UiEventLogger uiEventLogger, TouchInsetManager touchInsetManager, ComponentName componentName, DreamOverlayCallbackController dreamOverlayCallbackController, String str) {
        super(delayableExecutor);
        this.mStarted = false;
        this.mDestroyed = false;
        C12751 c12751 = new C12751();
        this.mKeyguardCallback = c12751;
        this.mExitAnimationFinishedCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.dreams.DreamOverlayService.2
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onStateChanged() {
                DreamOverlayService dreamOverlayService = DreamOverlayService.this;
                if (dreamOverlayService.mStateController.containsState(8)) {
                    return;
                }
                dreamOverlayService.mStateController.removeCallback((DreamOverlayStateController.Callback) dreamOverlayService.mExitAnimationFinishedCallback);
                dreamOverlayService.resetCurrentDreamOverlayLocked();
            }
        };
        this.mContext = context;
        this.mExecutor = delayableExecutor;
        this.mWindowManager = windowManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mLowLightDreamComponent = componentName;
        keyguardUpdateMonitor.registerCallback(c12751);
        this.mStateController = dreamOverlayStateController;
        this.mUiEventLogger = uiEventLogger;
        this.mDreamOverlayCallbackController = dreamOverlayCallbackController;
        this.mWindowTitle = str;
        com.android.systemui.complication.dagger.ComplicationComponent create = factory.create(dreamOverlayLifecycleOwner, new DreamOverlayService$$ExternalSyntheticLambda0(), new ViewModelStore(), touchInsetManager);
        this.mDreamOverlayComponent = factory3.create(dreamOverlayLifecycleOwner, create.getComplicationHostViewController(), touchInsetManager, new HashSet(Arrays.asList(factory2.create(create.getVisibilityController(), touchInsetManager).getHideComplicationTouchHandler())));
        this.mLifecycleRegistry = dreamOverlayLifecycleOwner.registry;
        ((ExecutorImpl) delayableExecutor).execute(new DreamOverlayService$$ExternalSyntheticLambda1(this, 0));
    }

    public final void onDestroy() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardCallback);
        ((ExecutorImpl) this.mExecutor).execute(new DreamOverlayService$$ExternalSyntheticLambda1(this, 1));
        super.onDestroy();
    }

    public final void onEndDream() {
        resetCurrentDreamOverlayLocked();
    }

    public final void onStartDream(WindowManager.LayoutParams layoutParams) {
        boolean z;
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
        DreamOverlayTouchMonitor dreamOverlayTouchMonitor = this.mDreamOverlayComponent.getDreamOverlayTouchMonitor();
        dreamOverlayTouchMonitor.mLifecycle.addObserver(dreamOverlayTouchMonitor.mLifecycleObserver);
        final DreamOverlayStateController dreamOverlayStateController = this.mStateController;
        final boolean shouldShowComplications = shouldShowComplications();
        dreamOverlayStateController.getClass();
        dreamOverlayStateController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayStateController dreamOverlayStateController2 = DreamOverlayStateController.this;
                dreamOverlayStateController2.getClass();
                dreamOverlayStateController2.mCallbacks.forEach(new DreamOverlayStateController$$ExternalSyntheticLambda0(2));
            }
        });
        PhoneWindow phoneWindow = new PhoneWindow(this.mContext);
        this.mWindow = phoneWindow;
        phoneWindow.setTitle(this.mWindowTitle);
        this.mWindow.setAttributes(layoutParams);
        this.mWindow.setWindowManager(null, layoutParams.token, "DreamOverlay", true);
        boolean z2 = false;
        this.mWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mWindow.clearFlags(VideoPlayer.MEDIA_ERROR_SYSTEM);
        this.mWindow.addFlags(8);
        this.mWindow.requestFeature(1);
        this.mWindow.getDecorView().getWindowInsetsController().hide(WindowInsets.Type.systemBars());
        this.mWindow.setDecorFitsSystemWindows(false);
        if (DEBUG) {
            Log.d("DreamOverlayService", "adding overlay window to dream");
        }
        this.mDreamOverlayContainerViewController.init();
        View view = this.mDreamOverlayContainerViewController.mView;
        if (view != null && (viewGroup = (ViewGroup) view.getParent()) != null) {
            Log.w("DreamOverlayService", "Removing dream overlay container view parent!");
            viewGroup.removeView(view);
        }
        this.mWindow.setContentView(this.mDreamOverlayContainerViewController.mView);
        try {
            this.mWindowManager.addView(this.mWindow.getDecorView(), this.mWindow.getAttributes());
            z = true;
        } catch (WindowManager.BadTokenException e) {
            Log.e("DreamOverlayService", "Dream activity window invalid: " + layoutParams.packageName, e);
            z = false;
        }
        if (!z) {
            resetCurrentDreamOverlayLocked();
            return;
        }
        this.mLifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
        DreamOverlayStateController dreamOverlayStateController2 = this.mStateController;
        dreamOverlayStateController2.getClass();
        dreamOverlayStateController2.modifyState(2, 1);
        ComponentName dreamComponent = getDreamComponent();
        DreamOverlayStateController dreamOverlayStateController3 = this.mStateController;
        if (dreamComponent != null && dreamComponent.equals(this.mLowLightDreamComponent)) {
            z2 = true;
        }
        dreamOverlayStateController3.setLowLightActive(z2);
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
            Animator animator = dreamOverlayAnimationsController.mAnimator;
            if (animator != null) {
                animator.cancel();
            }
            dreamOverlayAnimationsController.mAnimator = null;
            DreamOverlayStateController dreamOverlayStateController = dreamOverlayAnimationsController.mOverlayStateController;
            dreamOverlayStateController.getClass();
            dreamOverlayStateController.modifyState(2, 8);
        }
    }

    public final void resetCurrentDreamOverlayLocked() {
        Window window;
        if (this.mStateController.containsState(8)) {
            this.mStateController.addCallback((DreamOverlayStateController.Callback) this.mExitAnimationFinishedCallback);
            return;
        }
        if (this.mStarted && (window = this.mWindow) != null) {
            try {
                this.mWindowManager.removeView(window.getDecorView());
            } catch (IllegalArgumentException e) {
                Log.e("DreamOverlayService", "Error removing decor view when resetting overlay", e);
            }
        }
        DreamOverlayStateController dreamOverlayStateController = this.mStateController;
        dreamOverlayStateController.getClass();
        dreamOverlayStateController.modifyState(1, 1);
        this.mStateController.setLowLightActive(false);
        DreamOverlayStateController dreamOverlayStateController2 = this.mStateController;
        dreamOverlayStateController2.getClass();
        dreamOverlayStateController2.modifyState(1, 4);
        this.mDreamOverlayContainerViewController = null;
        this.mWindow = null;
        this.mStarted = false;
    }
}
