package android.service.dreams;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.dreams.utils.DreamAccessibility;
import android.util.Log;
import android.util.MathUtils;
import android.util.Slog;
import android.view.ActionMode;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

import com.android.internal.R;
import com.android.internal.util.DumpUtils;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class DreamService extends Service implements Window.Callback {
    public static final boolean DEFAULT_SHOW_COMPLICATIONS = false;
    public static final int DREAM_CATEGORY_DEFAULT = 0;
    public static final int DREAM_CATEGORY_HOME_PANEL = 2;
    public static final int DREAM_CATEGORY_LOW_LIGHT = 1;
    public static final String DREAM_META_DATA = "android.service.dream";
    private static final String DREAM_META_DATA_ROOT_TAG = "dream";
    public static final String DREAM_SERVICE = "dreams";
    static final String EXTRA_DREAM_OVERLAY_COMPONENT =
            "android.service.dream.DreamService.dream_overlay_component";
    public static final String SERVICE_INTERFACE = "android.service.dreams.DreamService";
    private Activity mActivity;
    private boolean mCanDoze;
    private boolean mDebug;
    private Runnable mDispatchAfterOnAttachedToWindow;
    private volatile int mDozeScreenBrightness;
    private int mDozeScreenMode;
    private volatile int mDozeScreenState;
    private volatile int mDozeScreenStateReason;
    private boolean mDozing;
    private DreamAccessibility mDreamAccessibility;
    private ComponentName mDreamComponent;
    private final IDreamManager mDreamManager;
    private DreamServiceWrapper mDreamServiceWrapper;
    private IBinder mDreamToken;
    private boolean mFinished;
    private boolean mFullscreen;
    private final Handler mHandler;
    private final Injector mInjector;
    private boolean mInteractive;
    private IDreamOverlayCallback mOverlayCallback;
    private DreamOverlayConnectionHandler mOverlayConnection;
    private boolean mPreviewMode;
    private boolean mRedirectWake;
    private boolean mScreenBright;
    private boolean mShouldShowComplications;
    private boolean mShouldWaitForTransitionToAodUi;
    private boolean mStarted;
    private final String mTag;
    private Integer mTrackingConfirmKey;
    private boolean mWaking;
    private Window mWindow;
    private boolean mWindowless;
    private static final String TAG = DreamService.class.getSimpleName();
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    @Retention(RetentionPolicy.SOURCE)
    @interface DreamCategory {}

    public interface Injector {
        DreamOverlayConnectionHandler createOverlayConnection(ComponentName componentName);

        ComponentName getDreamActivityComponent();

        ComponentName getDreamComponent();

        IDreamManager getDreamManager();

        String getDreamPackageName();

        Handler getHandler();

        PackageManager getPackageManager();

        Resources getResources();

        ServiceInfo getServiceInfo();

        void init(Context context);
    }

    private static final class DefaultInjector implements Injector {
        private Class<?> mClassName;
        private Context mContext;

        private DefaultInjector() {}

        @Override // android.service.dreams.DreamService.Injector
        public void init(Context context) {
            this.mContext = context;
            this.mClassName = context.getClass();
        }

        @Override // android.service.dreams.DreamService.Injector
        public DreamOverlayConnectionHandler createOverlayConnection(
                ComponentName overlayComponent) {
            Resources resources = this.mContext.getResources();
            return new DreamOverlayConnectionHandler(
                    this.mContext,
                    Looper.getMainLooper(),
                    new Intent().setComponent(overlayComponent),
                    resources.getInteger(R.integer.config_minDreamOverlayDurationMs),
                    resources.getInteger(R.integer.config_dreamOverlayMaxReconnectAttempts),
                    resources.getInteger(R.integer.config_dreamOverlayReconnectTimeoutMs));
        }

        @Override // android.service.dreams.DreamService.Injector
        public ComponentName getDreamActivityComponent() {
            return new ComponentName(this.mContext, (Class<?>) DreamActivity.class);
        }

        @Override // android.service.dreams.DreamService.Injector
        public ComponentName getDreamComponent() {
            return new ComponentName(this.mContext, this.mClassName);
        }

        @Override // android.service.dreams.DreamService.Injector
        public String getDreamPackageName() {
            return this.mContext.getApplicationContext().getPackageName();
        }

        @Override // android.service.dreams.DreamService.Injector
        public IDreamManager getDreamManager() {
            return IDreamManager.Stub.asInterface(
                    ServiceManager.getService(DreamService.DREAM_SERVICE));
        }

        @Override // android.service.dreams.DreamService.Injector
        public ServiceInfo getServiceInfo() {
            return DreamService.fetchServiceInfo(this.mContext, getDreamComponent());
        }

        @Override // android.service.dreams.DreamService.Injector
        public Handler getHandler() {
            return new Handler(Looper.getMainLooper());
        }

        @Override // android.service.dreams.DreamService.Injector
        public PackageManager getPackageManager() {
            return this.mContext.getPackageManager();
        }

        @Override // android.service.dreams.DreamService.Injector
        public Resources getResources() {
            return this.mContext.getResources();
        }
    }

    public DreamService() {
        this(new DefaultInjector());
    }

    public DreamService(Injector injector) {
        this.mTag =
                TAG
                        + NavigationBarInflaterView.SIZE_MOD_START
                        + getClass().getSimpleName()
                        + NavigationBarInflaterView.SIZE_MOD_END;
        this.mScreenBright = true;
        this.mDozeScreenState = 0;
        this.mDozeScreenStateReason = 0;
        this.mDozeScreenBrightness = -1;
        this.mDozeScreenMode = 0;
        this.mDebug = false;
        this.mTrackingConfirmKey = null;
        this.mInjector = injector;
        this.mInjector.init(this);
        this.mDreamManager = this.mInjector.getDreamManager();
        this.mHandler = this.mInjector.getHandler();
    }

    public void setDebug(boolean dbg) {
        this.mDebug = dbg;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (Flags.dreamHandlesConfirmKeys()) {
            if (this.mInteractive && this.mWindow.superDispatchKeyEvent(event)) {
                return true;
            }
            if (KeyEvent.isConfirmKey(event.getKeyCode())) {
                switch (event.getAction()) {
                    case 0:
                        if (this.mTrackingConfirmKey != null) {
                            return true;
                        }
                        this.mTrackingConfirmKey = Integer.valueOf(event.getKeyCode());
                        return true;
                    case 1:
                        if (this.mTrackingConfirmKey == null
                                || this.mTrackingConfirmKey.intValue() != event.getKeyCode()) {
                            return true;
                        }
                        this.mTrackingConfirmKey = null;
                        KeyguardManager keyguardManager =
                                (KeyguardManager) getSystemService(KeyguardManager.class);
                        if (!keyguardManager.isKeyguardLocked()) {
                            wakeUp();
                            return true;
                        }
                        keyguardManager.requestDismissKeyguard(
                                getActivity(),
                                new KeyguardManager
                                        .KeyguardDismissCallback() { // from class:
                                                                     // android.service.dreams.DreamService.1
                                    @Override // android.app.KeyguardManager.KeyguardDismissCallback
                                    public void onDismissError() {
                                        Log.e(
                                                DreamService.TAG,
                                                "Could not dismiss keyguard on confirm key");
                                    }
                                });
                        return true;
                    default:
                        return true;
                }
            }
        }
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on keyEvent");
            }
            wakeUp();
            return true;
        }
        if (event.getKeyCode() == 4) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on back key");
            }
            wakeUp();
            return true;
        }
        return this.mWindow.superDispatchKeyEvent(event);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on keyShortcutEvent");
            }
            wakeUp();
            return true;
        }
        return this.mWindow.superDispatchKeyShortcutEvent(event);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!this.mInteractive && event.getActionMasked() == 1) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on touchEvent");
            }
            wakeUp();
            return true;
        }
        return this.mWindow.superDispatchTouchEvent(event);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(MotionEvent event) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on trackballEvent");
            }
            wakeUp();
            return true;
        }
        return this.mWindow.superDispatchTrackballEvent(event);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on genericMotionEvent");
            }
            wakeUp();
            return true;
        }
        return this.mWindow.superDispatchGenericMotionEvent(event);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return false;
    }

    @Override // android.view.Window.Callback
    public View onCreatePanelView(int featureId) {
        return null;
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int featureId, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {}

    @Override // android.view.Window.Callback
    public void onContentChanged() {}

    @Override // android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {}

    @Override // android.view.Window.Callback
    public void onAttachedToWindow() {}

    @Override // android.view.Window.Callback
    public void onDetachedFromWindow() {}

    @Override // android.view.Window.Callback
    public void onPanelClosed(int featureId, Menu menu) {}

    @Override // android.view.Window.Callback
    public boolean onSearchRequested(SearchEvent event) {
        return onSearchRequested();
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        return false;
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        return null;
    }

    @Override // android.view.Window.Callback
    public void onActionModeStarted(ActionMode mode) {}

    @Override // android.view.Window.Callback
    public void onActionModeFinished(ActionMode mode) {}

    public WindowManager getWindowManager() {
        if (this.mWindow != null) {
            return this.mWindow.getWindowManager();
        }
        return null;
    }

    public Window getWindow() {
        return this.mWindow;
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    public void setContentView(int layoutResID) {
        getWindow().setContentView(layoutResID);
    }

    public void setContentView(View view) {
        getWindow().setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getWindow().setContentView(view, params);
    }

    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getWindow().addContentView(view, params);
    }

    public <T extends View> T findViewById(int i) {
        return (T) getWindow().findViewById(i);
    }

    public final <T extends View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t == null) {
            throw new IllegalArgumentException(
                    "ID does not reference a View inside this DreamService");
        }
        return t;
    }

    public void setInteractive(boolean interactive) {
        this.mInteractive = interactive;
        updateAccessibilityMessage();
    }

    public boolean isInteractive() {
        return this.mInteractive;
    }

    public void setFullscreen(boolean fullscreen) {
        if (this.mFullscreen != fullscreen) {
            this.mFullscreen = fullscreen;
            applyWindowFlags(this.mFullscreen ? 1024 : 0, 1024);
        }
    }

    public boolean isFullscreen() {
        return this.mFullscreen;
    }

    public void setScreenBright(boolean screenBright) {
        if (this.mScreenBright != screenBright && !this.mPreviewMode) {
            this.mScreenBright = screenBright;
            applyWindowFlags(this.mScreenBright ? 128 : 0, 128);
        }
    }

    public boolean isScreenBright() {
        return getWindowFlagValue(128, this.mScreenBright);
    }

    public void setWindowless(boolean windowless) {
        this.mWindowless = windowless;
    }

    public boolean isWindowless() {
        return this.mWindowless;
    }

    public boolean canDoze() {
        return this.mCanDoze;
    }

    public void startDozing() {
        if (this.mCanDoze && !this.mDozing) {
            this.mDozing = true;
            updateDoze();
        }
    }

    private void updateDoze() {
        if (this.mDreamToken == null) {
            Slog.w(this.mTag, "Updating doze without a dream token.");
        } else if (this.mDozing) {
            try {
                this.mDreamManager.semStartDozing(
                        this.mDreamToken,
                        this.mDozeScreenState,
                        this.mDozeScreenStateReason,
                        this.mDozeScreenBrightness,
                        this.mDozeScreenMode,
                        this.mShouldWaitForTransitionToAodUi);
            } catch (RemoteException e) {
            }
        }
    }

    public void stopDozing() {
        if (this.mDozing) {
            this.mDozing = false;
            try {
                this.mDreamManager.stopDozing(this.mDreamToken);
            } catch (RemoteException e) {
            }
        }
    }

    public boolean isDozing() {
        return this.mDozing;
    }

    public int getDozeScreenState() {
        return this.mDozeScreenState;
    }

    public void setDozeScreenState(int state) {
        setDozeScreenState(state, 0);
    }

    public void setDozeScreenState(int state, int reason) {
        if (this.mDozeScreenState != state) {
            this.mDozeScreenState = state;
            this.mDozeScreenStateReason = reason;
            updateDoze();
        }
    }

    public void setDozeScreenState(int state, boolean shouldWaitForTransitionToAodUi) {
        if (this.mDozeScreenState != state
                || this.mShouldWaitForTransitionToAodUi != shouldWaitForTransitionToAodUi) {
            this.mDozeScreenState = state;
            this.mShouldWaitForTransitionToAodUi = shouldWaitForTransitionToAodUi;
            updateDoze();
        }
    }

    public void setDozeScreenState(int state, int reason, boolean shouldWaitForTransitionToAodUi) {
        if (this.mDozeScreenState != state
                || this.mShouldWaitForTransitionToAodUi != shouldWaitForTransitionToAodUi) {
            this.mDozeScreenState = state;
            this.mDozeScreenStateReason = reason;
            this.mShouldWaitForTransitionToAodUi = shouldWaitForTransitionToAodUi;
            updateDoze();
        }
    }

    public void semSetDozeScreenBrightness(int dozeMode, int brightness) {
        boolean needToUpdateDoze = false;
        if (this.mDozeScreenBrightness != brightness) {
            this.mDozeScreenBrightness = brightness;
            needToUpdateDoze = true;
        }
        if (this.mDozeScreenMode != dozeMode) {
            this.mDozeScreenMode = dozeMode;
            needToUpdateDoze = true;
        }
        if (needToUpdateDoze) {
            updateDoze();
        }
    }

    public int getDozeScreenBrightness() {
        return this.mDozeScreenBrightness;
    }

    public void setDozeScreenBrightness(int brightness) {
        if (brightness != -1) {
            brightness = clampAbsoluteBrightness(brightness);
        }
        if (this.mDozeScreenBrightness != brightness) {
            this.mDozeScreenBrightness = brightness;
            updateDoze();
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        if (this.mDebug) {
            Slog.v(this.mTag, "onCreate()");
        }
        this.mDreamComponent = this.mInjector.getDreamComponent();
        this.mShouldShowComplications =
                fetchShouldShowComplications(
                        this.mInjector.getPackageManager(), this.mInjector.getServiceInfo());
        this.mOverlayCallback = new AnonymousClass2();
        super.onCreate();
    }

    /* renamed from: android.service.dreams.DreamService$2, reason: invalid class name */
    class AnonymousClass2 extends IDreamOverlayCallback.Stub {
        AnonymousClass2() {}

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onExitRequested$0() {
            DreamService.this.finish();
        }

        @Override // android.service.dreams.IDreamOverlayCallback
        public void onExitRequested() {
            DreamService.this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.service.dreams.DreamService$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            DreamService.AnonymousClass2.this.lambda$onExitRequested$0();
                        }
                    });
        }

        @Override // android.service.dreams.IDreamOverlayCallback
        public void onRedirectWake(boolean redirect) {
            DreamService.this.mRedirectWake = redirect;
        }
    }

    public void onDreamingStarted() {
        if (this.mDebug) {
            Slog.v(this.mTag, "onDreamingStarted()");
        }
    }

    public void onDreamingStopped() {
        if (this.mDebug) {
            Slog.v(this.mTag, "onDreamingStopped()");
        }
    }

    public void onWakeUp() {
        if (this.mOverlayConnection != null) {
            this.mOverlayConnection.addConsumer(
                    new Consumer() { // from class:
                                     // android.service.dreams.DreamService$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            DreamService.this.lambda$onWakeUp$0((IDreamOverlayClient) obj);
                        }
                    });
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onWakeUp$0(IDreamOverlayClient overlay) {
        try {
            try {
                overlay.wakeUp();
            } catch (RemoteException e) {
                Slog.e(TAG, "Error waking the overlay service", e);
            }
        } finally {
            finish();
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (this.mDebug) {
            Slog.v(this.mTag, "onBind() intent = " + intent);
        }
        this.mDreamServiceWrapper = new DreamServiceWrapper();
        ComponentName overlayComponent =
                (ComponentName)
                        intent.getParcelableExtra(
                                EXTRA_DREAM_OVERLAY_COMPONENT, ComponentName.class);
        if (!this.mWindowless && overlayComponent != null) {
            this.mOverlayConnection = this.mInjector.createOverlayConnection(overlayComponent);
            if (!this.mOverlayConnection.bind()) {
                this.mOverlayConnection = null;
            }
        }
        return this.mDreamServiceWrapper;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        if (this.mOverlayConnection != null) {
            this.mOverlayConnection.unbind();
            this.mOverlayConnection = null;
        }
        return super.onUnbind(intent);
    }

    public final void finish() {
        if (this.mOverlayConnection != null) {
            this.mOverlayConnection.addConsumer(
                    new Consumer() { // from class:
                                     // android.service.dreams.DreamService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            DreamService.this.lambda$finish$1((IDreamOverlayClient) obj);
                        }
                    });
        }
        if (this.mDebug) {
            Slog.v(this.mTag, "finish(): mFinished=" + this.mFinished);
        }
        Activity activity = this.mActivity;
        if (activity != null) {
            if (!activity.isFinishing()) {
                activity.finishAndRemoveTask();
            }
        } else {
            if (this.mFinished) {
                return;
            }
            this.mFinished = true;
            if (this.mDreamToken != null) {
                try {
                    this.mDreamManager.finishSelf(this.mDreamToken, true);
                } catch (RemoteException e) {
                }
            } else {
                if (this.mDebug) {
                    Slog.v(this.mTag, "finish() called when not attached.");
                }
                stopSelf();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finish$1(IDreamOverlayClient overlay) {
        try {
            overlay.endDream();
            this.mOverlayConnection.unbind();
            this.mOverlayConnection = null;
        } catch (RemoteException e) {
            Log.e(this.mTag, "could not inform overlay of dream end:" + e);
        }
    }

    public final void wakeUp() {
        wakeUp(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void comeToFront() {
        this.mOverlayConnection.addConsumer(
                new Consumer() { // from class:
                                 // android.service.dreams.DreamService$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DreamService.this.lambda$comeToFront$2((IDreamOverlayClient) obj);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$comeToFront$2(IDreamOverlayClient overlay) {
        try {
            overlay.comeToFront();
        } catch (RemoteException e) {
            Log.e(this.mTag, "could not tell overlay to come to front:" + e);
        }
    }

    public boolean getRedirectWake() {
        return this.mOverlayConnection != null && this.mRedirectWake;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wakeUp(boolean fromSystem) {
        if (this.mDebug) {
            Slog.v(
                    this.mTag,
                    "wakeUp(): fromSystem="
                            + fromSystem
                            + ", mWaking="
                            + this.mWaking
                            + ", mFinished="
                            + this.mFinished);
        }
        if (!fromSystem && getRedirectWake()) {
            this.mOverlayConnection.addConsumer(
                    new Consumer() { // from class:
                                     // android.service.dreams.DreamService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            DreamService.this.lambda$wakeUp$3((IDreamOverlayClient) obj);
                        }
                    });
            return;
        }
        if (!this.mWaking && !this.mFinished) {
            this.mWaking = true;
            if (this.mActivity != null) {
                this.mActivity.convertToTranslucent(null, null);
            }
            onWakeUp();
            if (!fromSystem && !this.mFinished) {
                if (this.mActivity == null) {
                    Slog.w(this.mTag, "WakeUp was called before the dream was attached.");
                } else {
                    try {
                        this.mDreamManager.finishSelf(this.mDreamToken, false);
                    } catch (RemoteException e) {
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$wakeUp$3(IDreamOverlayClient overlay) {
        try {
            overlay.onWakeRequested();
        } catch (RemoteException e) {
            Log.e(this.mTag, "could not inform overlay of dream wakeup:" + e);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        if (this.mDebug) {
            Slog.v(this.mTag, "onDestroy()");
        }
        detach();
        this.mOverlayCallback = null;
        super.onDestroy();
    }

    public static DreamMetadata getDreamMetadata(Context context, ServiceInfo serviceInfo) {
        return getDreamMetadata(context.getPackageManager(), serviceInfo);
    }

    public static DreamMetadata getDreamMetadata(
            PackageManager packageManager, ServiceInfo serviceInfo) {
        if (serviceInfo == null) {
            return null;
        }
        TypedArray rawMetadata =
                packageManager.extractPackageItemInfoAttributes(
                        serviceInfo, DREAM_META_DATA, "dream", R.styleable.Dream);
        if (rawMetadata != null) {
            try {
                try {
                    DreamMetadata dreamMetadata =
                            new DreamMetadata(
                                    convertToComponentName(rawMetadata.getString(0), serviceInfo),
                                    rawMetadata.getDrawable(1),
                                    rawMetadata.getBoolean(2, false),
                                    rawMetadata.getInt(3, 0));
                    if (rawMetadata != null) {
                        rawMetadata.close();
                    }
                    return dreamMetadata;
                } catch (Exception exception) {
                    Log.e(TAG, "Failed to create read metadata", exception);
                    if (rawMetadata != null) {
                        rawMetadata.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                if (rawMetadata != null) {
                    try {
                        rawMetadata.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (rawMetadata != null) {
            rawMetadata.close();
        }
        return null;
    }

    private static ComponentName convertToComponentName(
            String flattenedString, ServiceInfo serviceInfo) {
        if (flattenedString == null) {
            return null;
        }
        if (!flattenedString.contains("/")) {
            return new ComponentName(serviceInfo.packageName, flattenedString);
        }
        ComponentName cn = ComponentName.unflattenFromString(flattenedString);
        if (cn == null) {
            return null;
        }
        if (!cn.getPackageName().equals(serviceInfo.packageName)) {
            Log.w(
                    TAG,
                    "Inconsistent package name in component: "
                            + cn.getPackageName()
                            + ", should be: "
                            + serviceInfo.packageName);
            return null;
        }
        return cn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detach() {
        if (this.mStarted) {
            if (this.mDebug) {
                Slog.v(this.mTag, "detach(): Calling onDreamingStopped()");
            }
            this.mStarted = false;
            onDreamingStopped();
        }
        if (this.mActivity != null && !this.mActivity.isFinishing()) {
            this.mActivity.finishAndRemoveTask();
        } else {
            finish();
        }
        this.mDreamToken = null;
        this.mCanDoze = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attach(
            IBinder dreamToken,
            boolean canDoze,
            boolean isPreviewMode,
            final IRemoteCallback started) {
        if (this.mDreamToken != null) {
            Slog.e(
                    this.mTag,
                    "attach() called when dream with token="
                            + this.mDreamToken
                            + " already attached");
            return;
        }
        if (this.mFinished || this.mWaking) {
            Slog.w(this.mTag, "attach() called after dream already finished");
            try {
                this.mDreamManager.finishSelf(dreamToken, true);
                return;
            } catch (RemoteException e) {
                return;
            }
        }
        this.mDreamToken = dreamToken;
        this.mCanDoze = canDoze;
        this.mPreviewMode = isPreviewMode;
        if (this.mPreviewMode) {
            this.mScreenBright = false;
        }
        if (this.mWindowless && !this.mCanDoze && !isCallerSystemUi()) {
            throw new IllegalStateException("Only doze or SystemUI dreams can be windowless.");
        }
        this.mDispatchAfterOnAttachedToWindow =
                new Runnable() { // from class:
                                 // android.service.dreams.DreamService$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        DreamService.this.lambda$attach$4(started);
                    }
                };
        if (!this.mWindowless) {
            Intent i = new Intent();
            i.setComponent(this.mInjector.getDreamActivityComponent());
            i.setPackage(this.mInjector.getDreamPackageName());
            i.setFlags(268697600);
            DreamActivity.setCallback(i, new DreamActivityCallbacks(this.mDreamToken));
            ServiceInfo serviceInfo = this.mInjector.getServiceInfo();
            CharSequence title =
                    fetchDreamLabel(
                            this.mInjector.getPackageManager(),
                            this.mInjector.getResources(),
                            serviceInfo,
                            isPreviewMode);
            DreamActivity.setTitle(i, title);
            try {
                this.mDreamManager.startDreamActivity(i);
                return;
            } catch (RemoteException e2) {
                Log.w(
                        this.mTag,
                        "Could not connect to activity task manager to start dream activity");
                e2.rethrowFromSystemServer();
                return;
            } catch (SecurityException e3) {
                Log.w(
                        this.mTag,
                        "Received SecurityException trying to start DreamActivity. Aborting dream"
                            + " start.");
                detach();
                return;
            }
        }
        this.mDispatchAfterOnAttachedToWindow.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attach$4(IRemoteCallback started) {
        if (this.mWindow != null || this.mWindowless) {
            this.mStarted = true;
            try {
                onDreamingStarted();
                try {
                    started.sendResult(null);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                try {
                    started.sendResult(null);
                    throw th;
                } catch (RemoteException e2) {
                    throw e2.rethrowFromSystemServer();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onWindowCreated(Window w) {
        this.mWindow = w;
        this.mWindow.setCallback(this);
        this.mWindow.requestFeature(1);
        WindowManager.LayoutParams lp = this.mWindow.getAttributes();
        lp.flags |= (this.mFullscreen ? 1024 : 0) | 21561601 | (this.mScreenBright ? 128 : 0);
        lp.layoutInDisplayCutoutMode = 3;
        this.mWindow.setAttributes(lp);
        this.mWindow.clearFlags(Integer.MIN_VALUE);
        this.mWindow
                .getDecorView()
                .getWindowInsetsController()
                .hide(WindowInsets.Type.systemBars());
        this.mWindow.setDecorFitsSystemWindows(false);
        updateAccessibilityMessage();
        this.mWindow.getDecorView().addOnAttachStateChangeListener(new AnonymousClass3());
    }

    /* renamed from: android.service.dreams.DreamService$3, reason: invalid class name */
    class AnonymousClass3 implements View.OnAttachStateChangeListener {
        private Consumer<IDreamOverlayClient> mDreamStartOverlayConsumer;

        AnonymousClass3() {}

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View v) {
            DreamService.this.mDispatchAfterOnAttachedToWindow.run();
            if (DreamService.this.mOverlayConnection != null) {
                this.mDreamStartOverlayConsumer =
                        new Consumer() { // from class:
                                         // android.service.dreams.DreamService$3$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                DreamService.AnonymousClass3.this.lambda$onViewAttachedToWindow$0(
                                        (IDreamOverlayClient) obj);
                            }
                        };
                DreamService.this.mOverlayConnection.addConsumer(this.mDreamStartOverlayConsumer);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onViewAttachedToWindow$0(IDreamOverlayClient overlay) {
            if (DreamService.this.mWindow == null) {
                Slog.d(DreamService.TAG, "mWindow is null");
                return;
            }
            try {
                overlay.startDream(
                        DreamService.this.mWindow.getAttributes(),
                        DreamService.this.mOverlayCallback,
                        DreamService.this.mDreamComponent.flattenToString(),
                        DreamService.this.mShouldShowComplications);
            } catch (RemoteException e) {
                Log.e(DreamService.this.mTag, "could not send window attributes:" + e);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View v) {
            if (DreamService.this.mActivity == null
                    || !DreamService.this.mActivity.isChangingConfigurations()) {
                DreamService.this.mWindow = null;
                DreamService.this.mActivity = null;
                DreamService.this.finish();
            }
            if (DreamService.this.mOverlayConnection != null
                    && this.mDreamStartOverlayConsumer != null) {
                DreamService.this.mOverlayConnection.removeConsumer(
                        this.mDreamStartOverlayConsumer);
            }
        }
    }

    private void updateAccessibilityMessage() {
        if (this.mWindow == null) {
            return;
        }
        if (this.mDreamAccessibility == null) {
            View rootView = this.mWindow.getDecorView();
            this.mDreamAccessibility = new DreamAccessibility(this, rootView);
        }
        this.mDreamAccessibility.updateAccessibilityConfiguration(Boolean.valueOf(isInteractive()));
    }

    private boolean getWindowFlagValue(int flag, boolean defaultValue) {
        return this.mWindow == null
                ? defaultValue
                : (this.mWindow.getAttributes().flags & flag) != 0;
    }

    private void applyWindowFlags(int flags, int mask) {
        if (this.mWindow != null) {
            WindowManager.LayoutParams lp = this.mWindow.getAttributes();
            lp.flags = applyFlags(lp.flags, flags, mask);
            this.mWindow.setAttributes(lp);
            this.mWindow.getWindowManager().updateViewLayout(this.mWindow.getDecorView(), lp);
        }
    }

    private boolean isCallerSystemUi() {
        return checkCallingOrSelfPermission(Manifest.permission.STATUS_BAR_SERVICE) == 0;
    }

    private int applyFlags(int oldFlags, int flags, int mask) {
        return ((~mask) & oldFlags) | (flags & mask);
    }

    private static boolean fetchShouldShowComplications(
            PackageManager packageManager, ServiceInfo serviceInfo) {
        DreamMetadata metadata = getDreamMetadata(packageManager, serviceInfo);
        if (metadata != null) {
            return metadata.showComplications;
        }
        return false;
    }

    private static CharSequence fetchDreamLabel(
            PackageManager pm,
            Resources resources,
            ServiceInfo serviceInfo,
            boolean isPreviewMode) {
        if (serviceInfo == null) {
            return null;
        }
        CharSequence dreamLabel = serviceInfo.loadLabel(pm);
        if (!isPreviewMode || dreamLabel == null) {
            return dreamLabel;
        }
        return resources.getString(R.string.dream_preview_title, dreamLabel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ServiceInfo fetchServiceInfo(Context context, ComponentName componentName) {
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getServiceInfo(componentName, PackageManager.ComponentInfoFlags.of(128L));
        } catch (PackageManager.NameNotFoundException e) {
            if (DEBUG) {
                Log.w(TAG, "cannot find component " + componentName.flattenToShortString());
                return null;
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$5(
            FileDescriptor fd, String[] args, PrintWriter pw1, String prefix) {
        dumpOnHandler(fd, pw1, args);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(final FileDescriptor fd, PrintWriter pw, final String[] args) {
        DumpUtils.dumpAsync(
                this.mHandler,
                new DumpUtils
                        .Dump() { // from class:
                                  // android.service.dreams.DreamService$$ExternalSyntheticLambda5
                    @Override // com.android.internal.util.DumpUtils.Dump
                    public final void dump(PrintWriter printWriter, String str) {
                        DreamService.this.lambda$dump$5(fd, args, printWriter, str);
                    }
                },
                pw,
                "",
                1000L);
    }

    protected void dumpOnHandler(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print(this.mTag + ": ");
        if (this.mFinished) {
            pw.println("stopped");
        } else {
            pw.println(
                    "running (dreamToken="
                            + this.mDreamToken
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
        pw.println("  window: " + this.mWindow);
        pw.print("  flags:");
        if (isInteractive()) {
            pw.print(" interactive");
        }
        if (isFullscreen()) {
            pw.print(" fullscreen");
        }
        if (isScreenBright()) {
            pw.print(" bright");
        }
        if (isWindowless()) {
            pw.print(" windowless");
        }
        if (isDozing()) {
            pw.print(" dozing");
        } else if (canDoze()) {
            pw.print(" candoze");
        }
        pw.println();
        if (canDoze()) {
            pw.println("  doze screen state: " + Display.stateToString(this.mDozeScreenState));
            pw.println("  doze screen brightness: " + this.mDozeScreenBrightness);
        }
    }

    private static int clampAbsoluteBrightness(int value) {
        return MathUtils.constrain(value, 0, 255);
    }

    final class DreamServiceWrapper extends IDreamService.Stub {
        DreamServiceWrapper() {}

        @Override // android.service.dreams.IDreamService
        public void attach(
                final IBinder dreamToken,
                final boolean canDoze,
                final boolean isPreviewMode,
                final IRemoteCallback started) {
            DreamService.this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            DreamService.DreamServiceWrapper.this.lambda$attach$0(
                                    dreamToken, canDoze, isPreviewMode, started);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$attach$0(
                IBinder dreamToken,
                boolean canDoze,
                boolean isPreviewMode,
                IRemoteCallback started) {
            DreamService.this.attach(dreamToken, canDoze, isPreviewMode, started);
        }

        @Override // android.service.dreams.IDreamService
        public void detach() {
            Handler handler = DreamService.this.mHandler;
            final DreamService dreamService = DreamService.this;
            handler.post(
                    new Runnable() { // from class:
                                     // android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DreamService.this.detach();
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wakeUp$1() {
            DreamService.this.wakeUp(true);
        }

        @Override // android.service.dreams.IDreamService
        public void wakeUp() {
            DreamService.this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            DreamService.DreamServiceWrapper.this.lambda$wakeUp$1();
                        }
                    });
        }

        @Override // android.service.dreams.IDreamService
        public void comeToFront() {
            if (!Flags.dreamHandlesBeingObscured()) {
                return;
            }
            Handler handler = DreamService.this.mHandler;
            final DreamService dreamService = DreamService.this;
            handler.post(
                    new Runnable() { // from class:
                                     // android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            DreamService.this.comeToFront();
                        }
                    });
        }
    }

    public final class DreamActivityCallbacks extends Binder {
        private final IBinder mActivityDreamToken;

        DreamActivityCallbacks(IBinder token) {
            this.mActivityDreamToken = token;
        }

        public void onActivityCreated(DreamActivity activity) {
            if (this.mActivityDreamToken != DreamService.this.mDreamToken
                    || DreamService.this.mFinished) {
                Slog.d(
                        DreamService.TAG,
                        "DreamActivity was created after the dream was finished or a new dream"
                            + " started, finishing DreamActivity");
                if (!activity.isFinishing()) {
                    activity.finishAndRemoveTask();
                    return;
                }
                return;
            }
            if (DreamService.this.mActivity != null) {
                Slog.w(
                        DreamService.TAG,
                        "A DreamActivity has already been started, finishing latest DreamActivity");
                if (!activity.isFinishing()) {
                    activity.finishAndRemoveTask();
                    return;
                }
                return;
            }
            DreamService.this.mActivity = activity;
            DreamService.this.onWindowCreated(activity.getWindow());
        }

        public void onActivityDestroyed() {
            DreamService.this.mActivity = null;
            DreamService.this.mWindow = null;
            DreamService.this.detach();
        }
    }

    public static final class DreamMetadata {
        public final int dreamCategory;
        public final Drawable previewImage;
        public final ComponentName settingsActivity;
        public final boolean showComplications;

        public DreamMetadata(
                ComponentName settingsActivity,
                Drawable previewImage,
                boolean showComplications,
                int dreamCategory) {
            this.settingsActivity = settingsActivity;
            this.previewImage = previewImage;
            this.showComplications = showComplications;
            if (com.android.internal.hidden_from_bootclasspath.android.service.controls.flags.Flags
                    .homePanelDream()) {
                this.dreamCategory = dreamCategory;
            } else {
                this.dreamCategory = 0;
            }
        }
    }

    public static void setDreamOverlayComponent(Intent intent, ComponentName component) {
        intent.putExtra(EXTRA_DREAM_OVERLAY_COMPONENT, component);
    }
}
