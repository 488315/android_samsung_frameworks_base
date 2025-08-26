package com.android.systemui.dreams;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ServiceLifecycleDispatcher;
import androidx.lifecycle.ViewModelStore;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.PhoneWindow;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.ambient.touch.dagger.AmbientTouchComponent;
import com.android.systemui.ambient.touch.scrim.ScrimManager;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.shared.log.CommunalUiEvent;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.complication.dagger.ComplicationComponent;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.dreams.DreamOverlayService;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.dagger.DreamComplicationComponent;
import com.android.systemui.dreams.dagger.DreamModule$$ExternalSyntheticLambda0;
import com.android.systemui.dreams.dagger.DreamOverlayComponent;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.navigationbar.gestural.domain.GestureInteractor;
import com.android.systemui.navigationbar.gestural.domain.TaskMatcher;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.function.Consumer;
import kotlinx.coroutines.Job;

/* loaded from: classes2.dex */
public class DreamOverlayService extends android.service.dreams.DreamOverlayService implements LifecycleOwner {
    public static final boolean DEBUG = Log.isLoggable("DreamOverlayService", 3);
    public static final TaskMatcher.TopActivityType DREAM_TYPE_MATCHER = new TaskMatcher.TopActivityType(5);
    public final AmbientTouchComponent.Factory mAmbientTouchComponentFactory;
    public boolean mBouncerShowing;
    public final AnonymousClass3 mBouncerShowingConsumer;
    public boolean mCommunalAvailable;
    public final CommunalInteractor mCommunalInteractor;
    public final CommunalSettingsInteractor mCommunalSettingsInteractor;
    public boolean mCommunalVisible;
    public final AnonymousClass2 mCommunalVisibleConsumer;
    public final ComplicationComponent.Factory mComplicationComponentFactory;
    public final Context mContext;
    public final AnonymousClass4 mCurrentOverlaysConsumer;
    public boolean mDestroyed;
    public final ServiceLifecycleDispatcher mDispatcher;
    public final DreamComplicationComponent.Factory mDreamComplicationComponentFactory;
    public final DreamOverlayCallbackController mDreamOverlayCallbackController;
    public final DreamOverlayComponent.Factory mDreamOverlayComponentFactory;
    public DreamOverlayContainerViewController mDreamOverlayContainerViewController;
    public final DelayableExecutor mExecutor;
    public final ArrayList mFlows;
    public final GestureInteractor mGestureInteractor;
    public final ComponentName mHomeControlPanelDreamComponent;
    public final DreamOverlayService$$ExternalSyntheticLambda0 mIsCommunalAvailableCallback;
    public final KeyguardUpdateMonitorCallback mKeyguardCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final DreamOverlayLifecycleOwner mLifecycleOwner;
    public final LifecycleRegistry mLifecycleRegistry;
    public final ComponentName mLowLightDreamComponent;
    public final AnonymousClass5 mPickupConsumer;
    public final PowerInteractor mPowerInteractor;
    public final ResetHandler mResetHandler;
    public final ScrimManager mScrimManager;
    public boolean mShadeExpanded;
    public boolean mStarted;
    public final DreamOverlayStateController mStateController;
    public final SystemDialogsCloser mSystemDialogsCloser;
    public final TouchInsetManager mTouchInsetManager;
    public TouchMonitor mTouchMonitor;
    public final UiEventLogger mUiEventLogger;
    public Window mWindow;
    public final WindowManager mWindowManager;
    public final String mWindowTitle;

    /* renamed from: com.android.systemui.dreams.DreamOverlayService$1, reason: invalid class name */
    class AnonymousClass1 extends KeyguardUpdateMonitorCallback {
        public AnonymousClass1() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onShadeExpandedChanged(final boolean z) {
            DreamOverlayService.this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DreamOverlayService.AnonymousClass1 anonymousClass1 = this.f$0;
                    boolean z2 = z;
                    DreamOverlayService dreamOverlayService = DreamOverlayService.this;
                    if (dreamOverlayService.mShadeExpanded == z2) {
                        return;
                    }
                    dreamOverlayService.mShadeExpanded = z2;
                    dreamOverlayService.updateLifecycleStateLocked();
                    dreamOverlayService.updateGestureBlockingLocked();
                }
            });
        }
    }

    /* renamed from: com.android.systemui.dreams.DreamOverlayService$2, reason: invalid class name */
    public class AnonymousClass2 implements Consumer {
        public AnonymousClass2() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            DreamOverlayService.this.mExecutor.execute(new DreamOverlayService$2$$ExternalSyntheticLambda0(this, (Boolean) obj));
        }
    }

    /* renamed from: com.android.systemui.dreams.DreamOverlayService$3, reason: invalid class name */
    public class AnonymousClass3 implements Consumer {
        public AnonymousClass3() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            DreamOverlayService.this.mExecutor.execute(new DreamOverlayService$2$$ExternalSyntheticLambda0(this, (Boolean) obj));
        }
    }

    /* renamed from: com.android.systemui.dreams.DreamOverlayService$4, reason: invalid class name */
    public class AnonymousClass4 implements Consumer {
        public AnonymousClass4() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            DreamOverlayService.this.mExecutor.execute(new DreamOverlayService$2$$ExternalSyntheticLambda0(this, (Set) obj));
        }
    }

    /* renamed from: com.android.systemui.dreams.DreamOverlayService$5, reason: invalid class name */
    public class AnonymousClass5 implements Consumer {
        public AnonymousClass5() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            DreamOverlayService.this.mExecutor.execute(new DreamOverlayService$$ExternalSyntheticLambda1(this, 2));
        }
    }

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

    public final class ResetHandler {
        public final ArrayList mPendingCallbacks;
        public final DreamOverlayStateController.Callback mStateCallback;

        public interface Callback {
            void onComplete();
        }

        public final class Info extends Record {
            public final Callback callback;
            public final String source;

            public /* synthetic */ Info(Callback callback, String str, int i) {
                this(callback, str);
            }

            @Override // java.lang.Record
            public final boolean equals(Object obj) {
                if (!(obj instanceof Info)) {
                    return false;
                }
                Info info = (Info) obj;
                return Objects.equals(this.callback, info.callback) && Objects.equals(this.source, info.source);
            }

            @Override // java.lang.Record
            public final int hashCode() {
                Callback callback = this.callback;
                String str = this.source;
                return Objects.hashCode(str) + (Objects.hashCode(callback) * 31);
            }

            @Override // java.lang.Record
            public final String toString() {
                Object[] objArr = {this.callback, this.source};
                String[] strArrSplit = "callback;source".length() == 0 ? new String[0] : "callback;source".split(";");
                StringBuilder sb = new StringBuilder();
                sb.append(Info.class.getSimpleName());
                sb.append("[");
                for (int i = 0; i < strArrSplit.length; i++) {
                    sb.append(strArrSplit[i]);
                    sb.append("=");
                    sb.append(objArr[i]);
                    if (i != strArrSplit.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                return sb.toString();
            }

            private Info(Callback callback, String str) {
                this.callback = callback;
                this.source = str;
            }
        }

        public /* synthetic */ ResetHandler(DreamOverlayService dreamOverlayService, int i) {
            this();
        }

        public final void process(boolean z) {
            DreamOverlayService dreamOverlayService;
            Window window;
            View containerView;
            ViewGroup viewGroup;
            while (true) {
                dreamOverlayService = DreamOverlayService.this;
                if (dreamOverlayService.mStateController.containsState(8) || this.mPendingCallbacks.isEmpty()) {
                    break;
                }
                Info info = (Info) this.mPendingCallbacks.removeFirst();
                boolean z2 = DreamOverlayService.DEBUG;
                DreamOverlayContainerViewController dreamOverlayContainerViewController = dreamOverlayService.mDreamOverlayContainerViewController;
                if (dreamOverlayContainerViewController != null && (containerView = dreamOverlayContainerViewController.getContainerView()) != null && (viewGroup = (ViewGroup) containerView.getParent()) != null) {
                    Log.w("DreamOverlayService", "Removing dream overlay container view parent!");
                    viewGroup.removeView(containerView);
                }
                if (dreamOverlayService.mStarted && (window = dreamOverlayService.mWindow) != null) {
                    try {
                        window.clearContentView();
                        dreamOverlayService.mWindowManager.removeView(dreamOverlayService.mWindow.getDecorView());
                    } catch (IllegalArgumentException e) {
                        Log.e("DreamOverlayService", "Error removing decor view when resetting overlay", e);
                    }
                }
                dreamOverlayService.mStateController.setOverlayActive(false);
                dreamOverlayService.mStateController.setLowLightActive(false);
                DreamOverlayStateController dreamOverlayStateController = dreamOverlayService.mStateController;
                dreamOverlayStateController.getClass();
                dreamOverlayStateController.modifyState(1, 4);
                dreamOverlayService.mDreamOverlayCallbackController.onWakeUp();
                DreamOverlayContainerViewController dreamOverlayContainerViewController2 = dreamOverlayService.mDreamOverlayContainerViewController;
                if (dreamOverlayContainerViewController2 != null) {
                    dreamOverlayContainerViewController2.destroy();
                    dreamOverlayService.mDreamOverlayContainerViewController = null;
                }
                TouchMonitor touchMonitor = dreamOverlayService.mTouchMonitor;
                if (touchMonitor != null) {
                    touchMonitor.destroy();
                    dreamOverlayService.mTouchMonitor = null;
                }
                dreamOverlayService.mWindow = null;
                dreamOverlayService.mGestureInteractor.removeGestureBlockedMatcher(DreamOverlayService.DREAM_TYPE_MATCHER, GestureInteractor.Scope.Global);
                dreamOverlayService.mStarted = false;
                info.callback.onComplete();
                if (z) {
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("reset overlay (delayed) for "), info.source, "DreamOverlayService");
                }
            }
            if (this.mPendingCallbacks.isEmpty()) {
                dreamOverlayService.mStateController.removeCallback(this.mStateCallback);
            }
        }

        public final boolean reset(Callback callback, String str) {
            if (this.mPendingCallbacks.isEmpty()) {
                DreamOverlayService.this.mStateController.addCallback(this.mStateCallback);
            }
            Info info = new Info(callback, str, 0);
            this.mPendingCallbacks.add(info);
            process(false);
            boolean zContains = this.mPendingCallbacks.contains(info);
            boolean z = !zContains;
            if (zContains) {
                Log.d("DreamOverlayService", "delayed resetting from: ".concat(str));
            }
            return z;
        }

        private ResetHandler() {
            this.mPendingCallbacks = new ArrayList();
            this.mStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.dreams.DreamOverlayService.ResetHandler.1
                @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
                public final void onStateChanged() {
                    ResetHandler.this.process(true);
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda0, java.util.function.Consumer] */
    public DreamOverlayService(Context context, DreamOverlayLifecycleOwner dreamOverlayLifecycleOwner, DelayableExecutor delayableExecutor, WindowManager windowManager, ComplicationComponent.Factory factory, DreamComplicationComponent.Factory factory2, DreamOverlayComponent.Factory factory3, AmbientTouchComponent.Factory factory4, DreamOverlayStateController dreamOverlayStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, ScrimManager scrimManager, CommunalInteractor communalInteractor, CommunalSettingsInteractor communalSettingsInteractor, SceneInteractor sceneInteractor, SystemDialogsCloser systemDialogsCloser, UiEventLogger uiEventLogger, TouchInsetManager touchInsetManager, ComponentName componentName, ComponentName componentName2, DreamOverlayCallbackController dreamOverlayCallbackController, KeyguardInteractor keyguardInteractor, GestureInteractor gestureInteractor, WakeGestureMonitor wakeGestureMonitor, PowerInteractor powerInteractor, String str) {
        super(delayableExecutor);
        int i = 0;
        this.mStarted = false;
        this.mDestroyed = false;
        this.mShadeExpanded = false;
        this.mCommunalVisible = false;
        this.mBouncerShowing = false;
        ArrayList arrayList = new ArrayList();
        this.mFlows = arrayList;
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = new ServiceLifecycleDispatcher(this);
        this.mDispatcher = serviceLifecycleDispatcher;
        ?? r5 = new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DreamOverlayService dreamOverlayService = this.f$0;
                boolean z = DreamOverlayService.DEBUG;
                dreamOverlayService.mCommunalAvailable = ((Boolean) obj).booleanValue();
                if (dreamOverlayService.mStarted) {
                    dreamOverlayService.mCommunalSettingsInteractor.isV2FlagEnabled();
                    dreamOverlayService.redirectWake(dreamOverlayService.mCommunalAvailable);
                }
            }
        };
        this.mIsCommunalAvailableCallback = r5;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mKeyguardCallback = anonymousClass1;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mCommunalVisibleConsumer = anonymousClass2;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mBouncerShowingConsumer = anonymousClass3;
        this.mCurrentOverlaysConsumer = new AnonymousClass4();
        this.mPickupConsumer = new AnonymousClass5();
        this.mResetHandler = new ResetHandler(this, i);
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
        this.mComplicationComponentFactory = factory;
        this.mDreamComplicationComponentFactory = factory2;
        this.mDreamOverlayCallbackController = dreamOverlayCallbackController;
        this.mWindowTitle = str;
        this.mCommunalInteractor = communalInteractor;
        this.mCommunalSettingsInteractor = communalSettingsInteractor;
        this.mSystemDialogsCloser = systemDialogsCloser;
        this.mGestureInteractor = gestureInteractor;
        this.mDreamOverlayComponentFactory = factory3;
        this.mAmbientTouchComponentFactory = factory4;
        this.mTouchInsetManager = touchInsetManager;
        this.mLifecycleOwner = dreamOverlayLifecycleOwner;
        this.mLifecycleRegistry = dreamOverlayLifecycleOwner.registry;
        this.mPowerInteractor = powerInteractor;
        delayableExecutor.execute(new DreamOverlayService$$ExternalSyntheticLambda1(this, i));
        LifecycleRegistry lifecycleRegistry = serviceLifecycleDispatcher.registry;
        arrayList.add(JavaAdapterKt.collectFlow(lifecycleRegistry, communalInteractor.isCommunalAvailable(), (Consumer) r5));
        arrayList.add(JavaAdapterKt.collectFlow(lifecycleRegistry, communalInteractor.isCommunalVisible, anonymousClass2));
        int i2 = SceneContainerFlag.$r8$clinit;
        arrayList.add(JavaAdapterKt.collectFlow(lifecycleRegistry, keyguardInteractor.primaryBouncerShowing, anonymousClass3));
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
        int i = SceneContainerFlag.$r8$clinit;
        CommunalInteractor communalInteractor = this.mCommunalInteractor;
        CommunalSceneInteractor.changeScene$default(communalInteractor.communalSceneInteractor, CommunalScenes.Blank, "dream come to front", null, null, 8);
    }

    public final void onCreate() {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.mDispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_CREATE);
        super.onCreate();
    }

    public final void onDestroy() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardCallback);
        ArrayList arrayList = this.mFlows;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Job) obj).cancel(new CancellationException());
        }
        this.mFlows.clear();
        this.mExecutor.execute(new DreamOverlayService$$ExternalSyntheticLambda1(this, 1));
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.mDispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_STOP);
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_DESTROY);
        super.onDestroy();
    }

    public final void onEndDream() {
        this.mResetHandler.reset(new DreamOverlayService$$ExternalSyntheticLambda2(), "ending dream");
    }

    public final void onStart(Intent intent, int i) {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.mDispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_START);
        super.onStart(intent, i);
    }

    public final void onStartDream(final WindowManager.LayoutParams layoutParams) {
        View containerView;
        ViewGroup viewGroup;
        DaggerReferenceGlobalRootComponent.ComplicationComponentImpl complicationComponentImpl = (DaggerReferenceGlobalRootComponent.ComplicationComponentImpl) this.mComplicationComponentFactory.create(this.mLifecycleOwner, new DreamOverlayService$$ExternalSyntheticLambda2(), new ViewModelStore(), this.mTouchInsetManager);
        DreamComplicationComponent dreamComplicationComponentCreate = this.mDreamComplicationComponentFactory.create(complicationComponentImpl.getVisibilityController(), this.mTouchInsetManager);
        DreamOverlayComponent dreamOverlayComponentCreate = this.mDreamOverlayComponentFactory.create(this.mLifecycleOwner, complicationComponentImpl.getComplicationHostViewController(), this.mTouchInsetManager);
        ArrayList arrayList = new ArrayList(List.of(((DaggerReferenceGlobalRootComponent.DreamComplicationComponentImpl) dreamComplicationComponentCreate).getHideComplicationTouchHandler()));
        this.mCommunalSettingsInteractor.isV2FlagEnabled();
        DaggerReferenceGlobalRootComponent.DreamOverlayComponentImpl dreamOverlayComponentImpl = (DaggerReferenceGlobalRootComponent.DreamOverlayComponentImpl) dreamOverlayComponentCreate;
        arrayList.add(dreamOverlayComponentImpl.getCommunalTouchHandler());
        AmbientTouchComponent ambientTouchComponentCreate = this.mAmbientTouchComponentFactory.create(this.mLifecycleOwner, new HashSet(arrayList), "DreamOverlayService");
        this.mLifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
        this.mUiEventLogger.log(DreamOverlayEvent.DREAM_OVERLAY_ENTER_START);
        if (this.mDestroyed) {
            return;
        }
        if (!this.mStarted || this.mResetHandler.reset(new ResetHandler.Callback() { // from class: com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda3
            @Override // com.android.systemui.dreams.DreamOverlayService.ResetHandler.Callback
            public final void onComplete() {
                WindowManager.LayoutParams layoutParams2 = layoutParams;
                boolean z = DreamOverlayService.DEBUG;
                this.f$0.onStartDream(layoutParams2);
            }
        }, "starting with dream already started")) {
            this.mDreamOverlayContainerViewController = dreamOverlayComponentImpl.getDreamOverlayContainerViewController();
            TouchMonitor touchMonitor = ((DaggerReferenceGlobalRootComponent.AmbientTouchComponentImpl) ambientTouchComponentCreate).getTouchMonitor();
            this.mTouchMonitor = touchMonitor;
            touchMonitor.init();
            final DreamOverlayStateController dreamOverlayStateController = this.mStateController;
            final boolean zShouldShowComplications = shouldShowComplications();
            dreamOverlayStateController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    DreamOverlayStateController dreamOverlayStateController2 = dreamOverlayStateController;
                    boolean z = zShouldShowComplications;
                    DreamLogger dreamLogger = dreamOverlayStateController2.mLogger;
                    dreamLogger.getClass();
                    DreamLogger$$ExternalSyntheticLambda0 dreamLogger$$ExternalSyntheticLambda0 = new DreamLogger$$ExternalSyntheticLambda0(6);
                    LogMessage logMessageObtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, dreamLogger$$ExternalSyntheticLambda0, null);
                    logMessageObtain.setBool1(z);
                    dreamLogger.getBuffer().commit(logMessageObtain);
                    dreamOverlayStateController2.mShouldShowComplications = z;
                    dreamOverlayStateController2.notifyCallbacksLocked(new DreamOverlayStateController$$ExternalSyntheticLambda0(1));
                }
            });
            PhoneWindow phoneWindow = new PhoneWindow(this.mContext);
            this.mWindow = phoneWindow;
            phoneWindow.setTitle(this.mWindowTitle);
            this.mWindow.setAttributes(layoutParams);
            this.mWindow.setWindowManager(null, layoutParams.token, "DreamOverlay", true);
            boolean z = false;
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
            DreamOverlayContainerViewController dreamOverlayContainerViewController = this.mDreamOverlayContainerViewController;
            if (dreamOverlayContainerViewController != null && (containerView = dreamOverlayContainerViewController.getContainerView()) != null && (viewGroup = (ViewGroup) containerView.getParent()) != null) {
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
                if (dreamComponent != null && dreamComponent.equals(this.mHomeControlPanelDreamComponent)) {
                    z = true;
                }
                dreamOverlayStateController2.getClass();
                dreamOverlayStateController2.modifyState(z ? 2 : 1, 64);
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
                this.mCommunalSettingsInteractor.isV2FlagEnabled();
                redirectWake(this.mCommunalAvailable);
                updateGestureBlockingLocked();
            } catch (WindowManager.BadTokenException e) {
                Log.e("DreamOverlayService", "Dream activity window invalid: " + layoutParams.packageName, e);
                this.mResetHandler.reset(new DreamOverlayService$$ExternalSyntheticLambda2(), "couldn't add window while starting");
            }
        }
    }

    public final void onWakeRequested() {
        this.mUiEventLogger.log(CommunalUiEvent.DREAM_TO_COMMUNAL_HUB_DREAM_AWAKE_START);
        int i = SceneContainerFlag.$r8$clinit;
        CommunalInteractor communalInteractor = this.mCommunalInteractor;
        SceneKey sceneKey = CommunalScenes.Communal;
        this.mCommunalSettingsInteractor.isV2FlagEnabled();
        CommunalSceneInteractor.changeScene$default(communalInteractor.communalSceneInteractor, sceneKey, "dream wake requested", null, null, 8);
    }

    public final void onWakeUp() {
        if (this.mDreamOverlayContainerViewController != null) {
            this.mDreamOverlayCallbackController.onWakeUp();
            DreamOverlayContainerViewController dreamOverlayContainerViewController = this.mDreamOverlayContainerViewController;
            if (dreamOverlayContainerViewController.mWakingUpFromSwipe) {
                return;
            }
            dreamOverlayContainerViewController.mDreamOverlayAnimationsController.cancelAnimations();
        }
    }

    public final void updateGestureBlockingLocked() {
        if (!this.mStarted || this.mShadeExpanded || this.mBouncerShowing || isDreamInPreviewMode()) {
            this.mGestureInteractor.removeGestureBlockedMatcher(DREAM_TYPE_MATCHER, GestureInteractor.Scope.Global);
        } else {
            this.mGestureInteractor.addGestureBlockedMatcher(DREAM_TYPE_MATCHER, GestureInteractor.Scope.Global);
        }
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
