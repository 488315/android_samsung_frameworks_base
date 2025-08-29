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
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.dreams.IDreamManager;
import android.service.dreams.IDreamOverlayCallback;
import android.service.dreams.IDreamService;
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
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.util.DumpUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class DreamService extends Service implements Window.Callback {
    private static final boolean DEBUG = Log.isLoggable("DreamService", 3);
    public static final boolean DEFAULT_SHOW_COMPLICATIONS = false;
    public static final int DREAM_CATEGORY_DEFAULT = 0;
    public static final int DREAM_CATEGORY_HOME_PANEL = 2;
    public static final int DREAM_CATEGORY_LOW_LIGHT = 1;
    public static final String DREAM_META_DATA = "android.service.dream";
    private static final String DREAM_META_DATA_ROOT_TAG = "dream";
    public static final String DREAM_SERVICE = "dreams";
    static final String EXTRA_DREAM_OVERLAY_COMPONENT = "android.service.dream.DreamService.dream_overlay_component";
    public static final String SERVICE_INTERFACE = "android.service.dreams.DreamService";
    private static final String TAG = "DreamService";
    private Activity mActivity;
    private boolean mCanDoze;
    private boolean mDebug;
    private Runnable mDispatchAfterOnAttachedToWindow;
    private int mDozeScreenBrightness;
    private float mDozeScreenBrightnessFloat;
    private int mDozeScreenMode;
    private int mDozeScreenState;
    private int mDozeScreenStateReason;
    private boolean mDozing;
    private DreamAccessibility mDreamAccessibility;
    private ComponentName mDreamComponent;
    private final IDreamManager mDreamManager;
    private DreamServiceWrapper mDreamServiceWrapper;
    private IBinder mDreamToken;
    private boolean mFinished;
    private boolean mFullscreen;
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
    private boolean mUseNormalBrightnessForDoze;
    private boolean mWaking;
    private Window mWindow;
    private boolean mWindowless;

    @Retention(RetentionPolicy.SOURCE)
    @interface DreamCategory {
    }

    public interface Injector {
        DreamOverlayConnectionHandler createOverlayConnection(ComponentName componentName, Runnable runnable);

        ComponentName getDreamActivityComponent();

        ComponentName getDreamComponent();

        IDreamManager getDreamManager();

        String getDreamPackageName();

        PackageManager getPackageManager();

        Resources getResources();

        ServiceInfo getServiceInfo();

        WakefulHandler getWakefulHandler();

        void init(Context context);
    }

    public interface WakefulHandler {
        Handler getHandler();

        void postIfNeeded(Runnable runnable);
    }

    private int applyFlags(int i, int i2, int i3) {
        return ((~i3) & i) | (i2 & i3);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onActionModeFinished(ActionMode actionMode) {
    }

    @Override // android.view.Window.Callback
    public void onActionModeStarted(ActionMode actionMode) {
    }

    @Override // android.view.Window.Callback
    public void onAttachedToWindow() {
    }

    @Override // android.view.Window.Callback
    public void onContentChanged() {
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int i, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public View onCreatePanelView(int i) {
        return null;
    }

    @Override // android.view.Window.Callback
    public void onDetachedFromWindow() {
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int i, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onPanelClosed(int i, Menu menu) {
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int i, View view, Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
    }

    @Override // android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        return null;
    }

    private static final class WakefulHandlerImpl implements WakefulHandler {
        private static final String SERVICE_HANDLER_WAKE_LOCK_TAG = "dream:service:handler";
        private Context mContext;
        private Handler mHandler = new Handler(Looper.getMainLooper());
        private PowerManager.WakeLock mWakeLock = getWakeLock();

        private PowerManager.WakeLock getWakeLock() {
            PowerManager powerManager;
            if (this.mContext.checkCallingOrSelfPermission(Manifest.permission.WAKE_LOCK) == 0 && (powerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class)) != null) {
                return powerManager.newWakeLock(1, SERVICE_HANDLER_WAKE_LOCK_TAG);
            }
            return null;
        }

        WakefulHandlerImpl(Context context) {
            this.mContext = context;
        }

        @Override // android.service.dreams.DreamService.WakefulHandler
        public void postIfNeeded(Runnable runnable) {
            if (this.mHandler.getLooper().isCurrentThread()) {
                runnable.run();
                return;
            }
            PowerManager.WakeLock wakeLock = this.mWakeLock;
            if (wakeLock != null) {
                this.mHandler.post(wakeLock.wrap(runnable));
            } else {
                this.mHandler.post(runnable);
            }
        }

        @Override // android.service.dreams.DreamService.WakefulHandler
        public Handler getHandler() {
            return this.mHandler;
        }
    }

    private static final class DefaultInjector implements Injector {
        private Class<?> mClassName;
        private Context mContext;
        private WakefulHandler mWakefulHandler;

        private DefaultInjector() {
        }

        @Override // android.service.dreams.DreamService.Injector
        public void init(Context context) {
            this.mContext = context;
            this.mClassName = context.getClass();
        }

        @Override // android.service.dreams.DreamService.Injector
        public DreamOverlayConnectionHandler createOverlayConnection(ComponentName componentName, Runnable runnable) {
            return new DreamOverlayConnectionHandler(this.mContext, Looper.getMainLooper(), new Intent().setComponent(componentName), runnable);
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
            return IDreamManager.Stub.asInterface(ServiceManager.getService(DreamService.DREAM_SERVICE));
        }

        @Override // android.service.dreams.DreamService.Injector
        public ServiceInfo getServiceInfo() {
            return DreamService.fetchServiceInfo(this.mContext, getDreamComponent());
        }

        @Override // android.service.dreams.DreamService.Injector
        public WakefulHandler getWakefulHandler() {
            synchronized (this) {
                if (this.mWakefulHandler == null) {
                    this.mWakefulHandler = new WakefulHandlerImpl(this.mContext);
                }
            }
            return this.mWakefulHandler;
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
        this.mTag = TAG + NavigationBarInflaterView.SIZE_MOD_START + getClass().getSimpleName() + NavigationBarInflaterView.SIZE_MOD_END;
        this.mScreenBright = true;
        this.mDozeScreenState = 0;
        this.mDozeScreenStateReason = 0;
        this.mDozeScreenBrightness = -1;
        this.mDozeScreenBrightnessFloat = Float.NaN;
        this.mDozeScreenMode = 0;
        this.mDebug = false;
        this.mTrackingConfirmKey = null;
        this.mInjector = injector;
        injector.init(this);
        this.mDreamManager = injector.getDreamManager();
    }

    public void setDebug(boolean z) {
        this.mDebug = z;
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        Integer num;
        if (Flags.dreamHandlesConfirmKeys()) {
            if (this.mInteractive && this.mWindow.superDispatchKeyEvent(keyEvent)) {
                return true;
            }
            if (KeyEvent.isConfirmKey(keyEvent.getKeyCode())) {
                int action = keyEvent.getAction();
                if (action == 0) {
                    if (this.mTrackingConfirmKey != null) {
                        return true;
                    }
                    this.mTrackingConfirmKey = Integer.valueOf(keyEvent.getKeyCode());
                } else {
                    if (action != 1 || (num = this.mTrackingConfirmKey) == null || num.intValue() != keyEvent.getKeyCode()) {
                        return true;
                    }
                    this.mTrackingConfirmKey = null;
                    KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KeyguardManager.class);
                    if (!keyguardManager.isKeyguardLocked()) {
                        wakeUp(false);
                        return true;
                    }
                    keyguardManager.requestDismissKeyguard(getActivity(), new KeyguardManager.KeyguardDismissCallback(this) { // from class: android.service.dreams.DreamService.1
                        @Override // android.app.KeyguardManager.KeyguardDismissCallback
                        public void onDismissError() {
                            Log.e(DreamService.TAG, "Could not dismiss keyguard on confirm key");
                        }
                    });
                }
                return true;
            }
        }
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on keyEvent");
            }
            wakeUp(false);
            return true;
        }
        if (keyEvent.getKeyCode() == 4) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on back key");
            }
            wakeUp(false);
            return true;
        }
        return this.mWindow.superDispatchKeyEvent(keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on keyShortcutEvent");
            }
            wakeUp(false);
            return true;
        }
        return this.mWindow.superDispatchKeyShortcutEvent(keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.mInteractive && motionEvent.getActionMasked() == 1) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on touchEvent");
            }
            wakeUp(false);
            return true;
        }
        return this.mWindow.superDispatchTouchEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on trackballEvent");
            }
            wakeUp(false);
            return true;
        }
        return this.mWindow.superDispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.mTag, "Waking up on genericMotionEvent");
            }
            wakeUp(false);
            return true;
        }
        return this.mWindow.superDispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return onSearchRequested();
    }

    public WindowManager getWindowManager() {
        Window window = this.mWindow;
        if (window != null) {
            return window.getWindowManager();
        }
        return null;
    }

    public Window getWindow() {
        return this.mWindow;
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    public void setContentView(int i) {
        getWindow().setContentView(i);
    }

    public void setContentView(View view) {
        getWindow().setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        getWindow().setContentView(view, layoutParams);
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        getWindow().addContentView(view, layoutParams);
    }

    public <T extends View> T findViewById(int i) {
        return (T) getWindow().findViewById(i);
    }

    public final <T extends View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this DreamService");
    }

    public void setInteractive(boolean z) {
        this.mInteractive = z;
    }

    public boolean isInteractive() {
        return this.mInteractive;
    }

    public void setFullscreen(boolean z) {
        if (this.mFullscreen != z) {
            this.mFullscreen = z;
            applyWindowFlags(z ? 1024 : 0, 1024);
        }
    }

    public boolean isFullscreen() {
        return this.mFullscreen;
    }

    public void setScreenBright(boolean z) {
        if (this.mScreenBright == z || this.mPreviewMode) {
            return;
        }
        this.mScreenBright = z;
        applyWindowFlags(z ? 128 : 0, 128);
    }

    public boolean isScreenBright() {
        return getWindowFlagValue(128, this.mScreenBright);
    }

    public void setWindowless(boolean z) {
        this.mWindowless = z;
    }

    public boolean isWindowless() {
        return this.mWindowless;
    }

    public boolean canDoze() {
        return this.mCanDoze;
    }

    public void startDozing() {
        synchronized (this) {
            if (this.mCanDoze && !this.mDozing) {
                this.mDozing = true;
                updateDoze();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postIfNeeded(Runnable runnable) {
        this.mInjector.getWakefulHandler().postIfNeeded(runnable);
    }

    private void updateDoze() {
        postIfNeeded(new Runnable() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDoze$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDoze$0() {
        if (this.mDreamToken == null) {
            Slog.w(this.mTag, "Updating doze without a dream token.");
            return;
        }
        if (this.mDozing) {
            try {
                Slog.v(this.mTag, "UpdateDoze mDozeScreenState=" + this.mDozeScreenState + " mDozeScreenBrightness=" + this.mDozeScreenBrightness + " mDozeScreenBrightnessFloat=" + this.mDozeScreenBrightnessFloat);
                if (Flags.startAndStopDozingInBackground()) {
                    this.mDreamManager.semStartDozingOneWay(this.mDreamToken, this.mDozeScreenState, this.mDozeScreenStateReason, this.mDozeScreenBrightnessFloat, this.mDozeScreenBrightness, this.mUseNormalBrightnessForDoze, this.mDozeScreenMode, this.mShouldWaitForTransitionToAodUi);
                } else {
                    this.mDreamManager.startDozing(this.mDreamToken, this.mDozeScreenState, this.mDozeScreenStateReason, this.mDozeScreenBrightnessFloat, this.mDozeScreenBrightness, this.mUseNormalBrightnessForDoze);
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public void stopDozing() {
        postIfNeeded(new Runnable() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$stopDozing$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopDozing$1() {
        IBinder iBinder = this.mDreamToken;
        if (iBinder != null && this.mDozing) {
            this.mDozing = false;
            try {
                this.mDreamManager.stopDozing(iBinder);
            } catch (RemoteException unused) {
            }
        }
    }

    public boolean isDozing() {
        return this.mDozing;
    }

    public int getDozeScreenState() {
        return this.mDozeScreenState;
    }

    public void setDozeScreenState(int i) {
        setDozeScreenState(i, 0, false);
    }

    public void setDozeScreenState(int i, int i2, boolean z) {
        synchronized (this) {
            if (this.mDozeScreenState != i || this.mUseNormalBrightnessForDoze != z) {
                this.mDozeScreenState = i;
                this.mDozeScreenStateReason = i2;
                this.mUseNormalBrightnessForDoze = z;
                updateDoze();
            }
        }
    }

    public boolean getUseNormalBrightnessForDoze() {
        return this.mUseNormalBrightnessForDoze;
    }

    public void setDozeScreenState(int i, boolean z) {
        if (this.mDozeScreenState == i && this.mShouldWaitForTransitionToAodUi == z) {
            return;
        }
        this.mDozeScreenState = i;
        this.mShouldWaitForTransitionToAodUi = z;
        updateDoze();
    }

    public void setDozeScreenState(int i, int i2, boolean z, boolean z2) {
        if (this.mDozeScreenState == i && this.mShouldWaitForTransitionToAodUi == z2) {
            return;
        }
        this.mDozeScreenState = i;
        this.mDozeScreenStateReason = i2;
        this.mUseNormalBrightnessForDoze = z;
        this.mShouldWaitForTransitionToAodUi = z2;
        updateDoze();
    }

    public void semSetDozeScreenBrightness(int i, int i2) {
        boolean z;
        boolean z2 = true;
        if (this.mDozeScreenBrightness != i2) {
            this.mDozeScreenBrightness = i2;
            z = true;
        } else {
            z = false;
        }
        if (this.mDozeScreenMode != i) {
            this.mDozeScreenMode = i;
        } else {
            z2 = z;
        }
        if (z2) {
            updateDoze();
        }
    }

    public int getDozeScreenBrightness() {
        return this.mDozeScreenBrightness;
    }

    public void setDozeScreenBrightness(int i) {
        if (i != -1) {
            i = clampAbsoluteBrightness(i);
        }
        synchronized (this) {
            if (this.mDozeScreenBrightness != i) {
                this.mDozeScreenBrightness = i;
                updateDoze();
            }
        }
    }

    public void setDozeScreenBrightnessFloat(float f) {
        if (!Float.isNaN(f)) {
            f = clampAbsoluteBrightnessFloat(f);
        }
        synchronized (this) {
            if (!BrightnessSynchronizer.floatEquals(this.mDozeScreenBrightnessFloat, f)) {
                this.mDozeScreenBrightnessFloat = f;
                updateDoze();
            }
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        if (this.mDebug) {
            Slog.v(this.mTag, "onCreate()");
        }
        this.mDreamComponent = this.mInjector.getDreamComponent();
        this.mShouldShowComplications = fetchShouldShowComplications(this.mInjector.getPackageManager(), this.mInjector.getServiceInfo());
        this.mOverlayCallback = new AnonymousClass2();
        super.onCreate();
    }

    /* renamed from: android.service.dreams.DreamService$2, reason: invalid class name */
    class AnonymousClass2 extends IDreamOverlayCallback.Stub {
        AnonymousClass2() {
        }

        @Override // android.service.dreams.IDreamOverlayCallback
        public void onExitRequested() {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamService.this.postIfNeeded(new Runnable() { // from class: android.service.dreams.DreamService$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onExitRequested$0();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onExitRequested$0() {
            DreamService.this.finishInternal();
        }

        @Override // android.service.dreams.IDreamOverlayCallback
        public void onRedirectWake(boolean z) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamService.this.mRedirectWake = z;
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
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
        DreamOverlayConnectionHandler dreamOverlayConnectionHandler = this.mOverlayConnection;
        if (dreamOverlayConnectionHandler != null) {
            dreamOverlayConnectionHandler.addConsumer(new Consumer() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$onWakeUp$2((IDreamOverlayClient) obj);
                }
            });
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onWakeUp$2(IDreamOverlayClient iDreamOverlayClient) {
        try {
            iDreamOverlayClient.wakeUp();
        } catch (RemoteException e) {
            Slog.e(TAG, "Error waking the overlay service", e);
        } finally {
            finish();
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (this.mDebug) {
            Slog.v(this.mTag, "onBind() intent = " + intent);
        }
        this.mDreamServiceWrapper = new DreamServiceWrapper(new WeakReference(this));
        ComponentName componentName = (ComponentName) intent.getParcelableExtra(EXTRA_DREAM_OVERLAY_COMPONENT, ComponentName.class);
        if (!this.mWindowless && componentName != null) {
            DreamOverlayConnectionHandler dreamOverlayConnectionHandlerCreateOverlayConnection = this.mInjector.createOverlayConnection(componentName, new Runnable() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.finish();
                }
            });
            this.mOverlayConnection = dreamOverlayConnectionHandlerCreateOverlayConnection;
            if (!dreamOverlayConnectionHandlerCreateOverlayConnection.bind()) {
                this.mOverlayConnection = null;
            }
        }
        return this.mDreamServiceWrapper;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        DreamOverlayConnectionHandler dreamOverlayConnectionHandler = this.mOverlayConnection;
        if (dreamOverlayConnectionHandler != null) {
            dreamOverlayConnectionHandler.unbind();
            this.mOverlayConnection = null;
        }
        return super.onUnbind(intent);
    }

    public final void finish() {
        postIfNeeded(new Runnable() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.finishInternal();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishInternal() {
        DreamOverlayConnectionHandler dreamOverlayConnectionHandler = this.mOverlayConnection;
        if (dreamOverlayConnectionHandler != null) {
            dreamOverlayConnectionHandler.addConsumer(new Consumer() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$finishInternal$3((IDreamOverlayClient) obj);
                }
            });
        }
        if (this.mDebug) {
            Slog.v(this.mTag, "finish(): mFinished=" + this.mFinished);
        }
        Activity activity = this.mActivity;
        if (activity != null) {
            if (activity.isFinishing()) {
                return;
            }
            activity.finishAndRemoveTask();
        } else {
            if (this.mFinished) {
                return;
            }
            this.mFinished = true;
            if (this.mDreamToken == null) {
                if (this.mDebug) {
                    Slog.v(this.mTag, "finish() called when not attached.");
                }
                stopSelf();
            } else {
                try {
                    if (Flags.startAndStopDozingInBackground()) {
                        this.mDreamManager.finishSelfOneway(this.mDreamToken, true);
                    } else {
                        this.mDreamManager.finishSelf(this.mDreamToken, true);
                    }
                } catch (RemoteException unused) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishInternal$3(IDreamOverlayClient iDreamOverlayClient) {
        try {
            iDreamOverlayClient.endDream();
            this.mOverlayConnection.unbind();
            this.mOverlayConnection = null;
        } catch (RemoteException e) {
            Log.e(this.mTag, "could not inform overlay of dream end:" + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$wakeUp$4() {
        wakeUp(false);
    }

    public final void wakeUp() {
        postIfNeeded(new Runnable() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$wakeUp$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void comeToFront() {
        DreamOverlayConnectionHandler dreamOverlayConnectionHandler = this.mOverlayConnection;
        if (dreamOverlayConnectionHandler == null) {
            return;
        }
        dreamOverlayConnectionHandler.addConsumer(new Consumer() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$comeToFront$5((IDreamOverlayClient) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$comeToFront$5(IDreamOverlayClient iDreamOverlayClient) {
        try {
            iDreamOverlayClient.comeToFront();
        } catch (RemoteException e) {
            Log.e(this.mTag, "could not tell overlay to come to front:" + e);
        }
    }

    public boolean getRedirectWake() {
        return this.mOverlayConnection != null && this.mRedirectWake;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wakeUp(boolean z) {
        if (this.mDebug) {
            Slog.v(this.mTag, "wakeUp(): fromSystem=" + z + ", mWaking=" + this.mWaking + ", mFinished=" + this.mFinished);
        }
        if (!z && getRedirectWake()) {
            this.mOverlayConnection.addConsumer(new Consumer() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$wakeUp$6((IDreamOverlayClient) obj);
                }
            });
            return;
        }
        if (this.mWaking || this.mFinished) {
            return;
        }
        this.mWaking = true;
        Activity activity = this.mActivity;
        if (activity != null) {
            activity.convertToTranslucent(null, null);
        }
        onWakeUp();
        if (z || this.mFinished) {
            return;
        }
        if (this.mActivity == null) {
            Slog.w(this.mTag, "WakeUp was called before the dream was attached.");
            return;
        }
        try {
            if (Flags.startAndStopDozingInBackground()) {
                this.mDreamManager.finishSelfOneway(this.mDreamToken, false);
            } else {
                this.mDreamManager.finishSelf(this.mDreamToken, false);
            }
        } catch (RemoteException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$wakeUp$6(IDreamOverlayClient iDreamOverlayClient) {
        try {
            iDreamOverlayClient.onWakeRequested();
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

    public static DreamMetadata getDreamMetadata(PackageManager packageManager, ServiceInfo serviceInfo) {
        if (serviceInfo == null) {
            return null;
        }
        TypedArray typedArrayExtractPackageItemInfoAttributes = packageManager.extractPackageItemInfoAttributes(serviceInfo, DREAM_META_DATA, "dream", R.styleable.Dream);
        if (typedArrayExtractPackageItemInfoAttributes == null) {
            if (typedArrayExtractPackageItemInfoAttributes != null) {
                typedArrayExtractPackageItemInfoAttributes.close();
            }
            return null;
        }
        try {
            try {
                DreamMetadata dreamMetadata = new DreamMetadata(convertToComponentName(typedArrayExtractPackageItemInfoAttributes.getString(0), serviceInfo, packageManager), typedArrayExtractPackageItemInfoAttributes.getDrawable(1), typedArrayExtractPackageItemInfoAttributes.getBoolean(2, false), typedArrayExtractPackageItemInfoAttributes.getInt(3, 0));
                if (typedArrayExtractPackageItemInfoAttributes != null) {
                    typedArrayExtractPackageItemInfoAttributes.close();
                }
                return dreamMetadata;
            } catch (Exception e) {
                Log.e(TAG, "Failed to create read metadata", e);
                if (typedArrayExtractPackageItemInfoAttributes != null) {
                    typedArrayExtractPackageItemInfoAttributes.close();
                }
                return null;
            }
        } catch (Throwable th) {
            if (typedArrayExtractPackageItemInfoAttributes != null) {
                try {
                    typedArrayExtractPackageItemInfoAttributes.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static ComponentName convertToComponentName(String str, ServiceInfo serviceInfo, PackageManager packageManager) {
        ComponentName componentName;
        if (str == null) {
            return null;
        }
        if (str.contains("/")) {
            componentName = ComponentName.unflattenFromString(str);
        } else {
            componentName = new ComponentName(serviceInfo.packageName, str);
        }
        if (componentName == null) {
            return null;
        }
        if (!componentName.getPackageName().equals(serviceInfo.packageName)) {
            Log.w(TAG, "Inconsistent package name in component: " + componentName.getPackageName() + ", should be: " + serviceInfo.packageName);
            return null;
        }
        if (new Intent().setComponent(componentName).resolveActivityInfo(packageManager, 0) != null) {
            return componentName;
        }
        Log.w(TAG, "Dream settings activity not found: " + componentName);
        return null;
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
        Activity activity = this.mActivity;
        if (activity != null && !activity.isFinishing()) {
            this.mActivity.finishAndRemoveTask();
        } else {
            finishInternal();
        }
        this.mDreamToken = null;
        this.mCanDoze = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attach(IBinder iBinder, boolean z, boolean z2, final IRemoteCallback iRemoteCallback) {
        if (this.mDreamToken != null) {
            Slog.e(this.mTag, "attach() called when dream with token=" + this.mDreamToken + " already attached");
            return;
        }
        if (this.mFinished || this.mWaking) {
            Slog.w(this.mTag, "attach() called after dream already finished");
            try {
                if (Flags.startAndStopDozingInBackground()) {
                    this.mDreamManager.finishSelfOneway(iBinder, true);
                    return;
                } else {
                    this.mDreamManager.finishSelf(iBinder, true);
                    return;
                }
            } catch (RemoteException unused) {
                return;
            }
        }
        this.mDreamToken = iBinder;
        this.mCanDoze = z;
        this.mPreviewMode = z2;
        if (z2) {
            this.mScreenBright = false;
        }
        if (this.mWindowless && !z && !isCallerSystemUi()) {
            throw new IllegalStateException("Only doze or SystemUI dreams can be windowless.");
        }
        Runnable runnable = new Runnable() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$attach$7(iRemoteCallback);
            }
        };
        this.mDispatchAfterOnAttachedToWindow = runnable;
        if (!this.mWindowless) {
            Intent intent = new Intent();
            intent.setComponent(this.mInjector.getDreamActivityComponent());
            intent.setPackage(this.mInjector.getDreamPackageName());
            intent.setFlags(268697600);
            DreamActivity.setCallback(intent, new DreamActivityCallbacks(this.mDreamToken, new WeakReference(this)));
            DreamActivity.setTitle(intent, fetchDreamLabel(this.mInjector.getPackageManager(), this.mInjector.getResources(), this.mInjector.getServiceInfo(), z2));
            try {
                this.mDreamManager.startDreamActivity(intent);
                return;
            } catch (RemoteException e) {
                Log.w(this.mTag, "Could not connect to activity task manager to start dream activity");
                e.rethrowFromSystemServer();
                return;
            } catch (SecurityException unused2) {
                Log.w(this.mTag, "Received SecurityException trying to start DreamActivity. Aborting dream start.");
                detach();
                return;
            }
        }
        runnable.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attach$7(IRemoteCallback iRemoteCallback) {
        if (this.mWindow != null || this.mWindowless) {
            this.mStarted = true;
            try {
                onDreamingStarted();
                try {
                    iRemoteCallback.sendResult(null);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                try {
                    iRemoteCallback.sendResult(null);
                    throw th;
                } catch (RemoteException e2) {
                    throw e2.rethrowFromSystemServer();
                }
            }
        }
    }

    private void onWindowCreated(Window window) {
        this.mWindow = window;
        window.setCallback(this);
        this.mWindow.requestFeature(1);
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        attributes.flags |= (this.mFullscreen ? 1024 : 0) | 21561601 | (this.mScreenBright ? 128 : 0);
        attributes.layoutInDisplayCutoutMode = 3;
        this.mWindow.setAttributes(attributes);
        this.mWindow.clearFlags(Integer.MIN_VALUE);
        this.mWindow.getDecorView().getWindowInsetsController().hide(WindowInsets.Type.systemBars());
        this.mWindow.setDecorFitsSystemWindows(false);
        updateAccessibilityMessage();
        this.mWindow.getDecorView().addOnAttachStateChangeListener(new AnonymousClass3());
    }

    /* renamed from: android.service.dreams.DreamService$3, reason: invalid class name */
    class AnonymousClass3 implements View.OnAttachStateChangeListener {
        private Consumer<IDreamOverlayClient> mDreamStartOverlayConsumer;

        AnonymousClass3() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
            DreamService.this.mDispatchAfterOnAttachedToWindow.run();
            if (DreamService.this.mOverlayConnection != null) {
                this.mDreamStartOverlayConsumer = new Consumer() { // from class: android.service.dreams.DreamService$3$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$onViewAttachedToWindow$0((IDreamOverlayClient) obj);
                    }
                };
                DreamService.this.mOverlayConnection.addConsumer(this.mDreamStartOverlayConsumer);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onViewAttachedToWindow$0(IDreamOverlayClient iDreamOverlayClient) {
            if (DreamService.this.mWindow == null) {
                Slog.d(DreamService.TAG, "mWindow is null");
                return;
            }
            try {
                iDreamOverlayClient.startDream(DreamService.this.mWindow.getAttributes(), DreamService.this.mOverlayCallback, DreamService.this.mDreamComponent.flattenToString(), DreamService.this.mPreviewMode, DreamService.this.mShouldShowComplications);
            } catch (RemoteException e) {
                Log.e(DreamService.this.mTag, "could not send window attributes:" + e);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            if (DreamService.this.mActivity == null || !DreamService.this.mActivity.isChangingConfigurations()) {
                DreamService.this.mWindow = null;
                DreamService.this.mActivity = null;
                DreamService.this.finishInternal();
            }
            if (DreamService.this.mOverlayConnection == null || this.mDreamStartOverlayConsumer == null) {
                return;
            }
            DreamService.this.mOverlayConnection.removeConsumer(this.mDreamStartOverlayConsumer);
        }
    }

    private void updateAccessibilityMessage() {
        Window window = this.mWindow;
        if (window == null) {
            return;
        }
        if (this.mDreamAccessibility == null) {
            this.mDreamAccessibility = new DreamAccessibility(this, window.getDecorView(), new Runnable() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.wakeUp();
                }
            });
        }
        this.mDreamAccessibility.updateAccessibilityConfiguration();
    }

    private boolean getWindowFlagValue(int i, boolean z) {
        Window window = this.mWindow;
        return window == null ? z : (window.getAttributes().flags & i) != 0;
    }

    private void applyWindowFlags(int i, int i2) {
        Window window = this.mWindow;
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.flags = applyFlags(attributes.flags, i, i2);
            this.mWindow.setAttributes(attributes);
            this.mWindow.getWindowManager().updateViewLayout(this.mWindow.getDecorView(), attributes);
        }
    }

    private boolean isCallerSystemUi() {
        return checkCallingOrSelfPermission(Manifest.permission.STATUS_BAR_SERVICE) == 0;
    }

    private static boolean fetchShouldShowComplications(PackageManager packageManager, ServiceInfo serviceInfo) {
        DreamMetadata dreamMetadata = getDreamMetadata(packageManager, serviceInfo);
        if (dreamMetadata != null) {
            return dreamMetadata.showComplications;
        }
        return false;
    }

    private static CharSequence fetchDreamLabel(PackageManager packageManager, Resources resources, ServiceInfo serviceInfo, boolean z) {
        if (serviceInfo == null) {
            return null;
        }
        CharSequence charSequenceLoadLabel = serviceInfo.loadLabel(packageManager);
        return (!z || charSequenceLoadLabel == null) ? charSequenceLoadLabel : resources.getString(R.string.dream_preview_title, charSequenceLoadLabel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ServiceInfo fetchServiceInfo(Context context, ComponentName componentName) {
        try {
            return context.getPackageManager().getServiceInfo(componentName, PackageManager.ComponentInfoFlags.of(128L));
        } catch (PackageManager.NameNotFoundException unused) {
            if (!DEBUG) {
                return null;
            }
            Log.w(TAG, "cannot find component " + componentName.flattenToShortString());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(final FileDescriptor fileDescriptor, PrintWriter printWriter, final String[] strArr) {
        DumpUtils.dumpAsync(this.mInjector.getWakefulHandler().getHandler(), new DumpUtils.Dump() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda11
            @Override // com.android.internal.util.DumpUtils.Dump
            public final void dump(PrintWriter printWriter2, String str) {
                this.f$0.lambda$dump$8(fileDescriptor, strArr, printWriter2, str);
            }
        }, printWriter, "", 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$8(FileDescriptor fileDescriptor, String[] strArr, PrintWriter printWriter, String str) {
        dumpOnHandler(fileDescriptor, printWriter, strArr);
    }

    protected void dumpOnHandler(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(this.mTag + ": ");
        if (this.mFinished) {
            printWriter.println("stopped");
        } else {
            printWriter.println("running (dreamToken=" + this.mDreamToken + NavigationBarInflaterView.KEY_CODE_END);
        }
        printWriter.println("  window: " + this.mWindow);
        printWriter.print("  flags:");
        if (isInteractive()) {
            printWriter.print(" interactive");
        }
        if (isFullscreen()) {
            printWriter.print(" fullscreen");
        }
        if (isScreenBright()) {
            printWriter.print(" bright");
        }
        if (isWindowless()) {
            printWriter.print(" windowless");
        }
        if (isDozing()) {
            printWriter.print(" dozing");
        } else if (canDoze()) {
            printWriter.print(" candoze");
        }
        printWriter.println();
        if (canDoze()) {
            printWriter.println("  doze screen state: " + Display.stateToString(this.mDozeScreenState));
            printWriter.println("  doze screen brightness: " + this.mDozeScreenBrightness);
        }
    }

    private static int clampAbsoluteBrightness(int i) {
        return MathUtils.constrain(i, 0, 255);
    }

    private static float clampAbsoluteBrightnessFloat(float f) {
        return f == -1.0f ? f : MathUtils.constrain(f, 0.0f, 1.0f);
    }

    static final class DreamServiceWrapper extends IDreamService.Stub {
        final WeakReference<DreamService> mService;

        DreamServiceWrapper(WeakReference<DreamService> weakReference) {
            this.mService = weakReference;
        }

        private void post(final Consumer<DreamService> consumer) {
            final DreamService dreamService = this.mService.get();
            if (dreamService == null) {
                return;
            }
            dreamService.postIfNeeded(new Runnable() { // from class: android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(dreamService);
                }
            });
        }

        @Override // android.service.dreams.IDreamService
        public void attach(final IBinder iBinder, final boolean z, final boolean z2, final IRemoteCallback iRemoteCallback) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                post(new Consumer() { // from class: android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DreamService) obj).attach(iBinder, z, z2, iRemoteCallback);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.service.dreams.IDreamService
        public void detach() {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                post(new Consumer() { // from class: android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DreamService) obj).detach();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.service.dreams.IDreamService
        public void wakeUp() {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                post(new Consumer() { // from class: android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DreamService) obj).wakeUp(true);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.service.dreams.IDreamService
        public void comeToFront() {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (Flags.dreamHandlesBeingObscured()) {
                    post(new Consumer() { // from class: android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((DreamService) obj).comeToFront();
                        }
                    });
                }
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onActivityCreated(DreamActivity dreamActivity, IBinder iBinder) {
        if (iBinder != this.mDreamToken || this.mFinished) {
            Slog.d(TAG, "DreamActivity was created after the dream was finished or a new dream started, finishing DreamActivity");
            if (dreamActivity.isFinishing()) {
                return;
            }
            dreamActivity.finishAndRemoveTask();
            return;
        }
        if (this.mActivity != null) {
            Slog.w(TAG, "A DreamActivity has already been started, finishing latest DreamActivity");
            if (dreamActivity.isFinishing()) {
                return;
            }
            dreamActivity.finishAndRemoveTask();
            return;
        }
        this.mActivity = dreamActivity;
        onWindowCreated(dreamActivity.getWindow());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onActivityDestroyed() {
        this.mActivity = null;
        this.mWindow = null;
        detach();
    }

    public static final class DreamActivityCallbacks extends Binder {
        private final IBinder mActivityDreamToken;
        private WeakReference<DreamService> mService;

        DreamActivityCallbacks(IBinder iBinder, WeakReference<DreamService> weakReference) {
            this.mActivityDreamToken = iBinder;
            this.mService = weakReference;
        }

        public void onActivityCreated(DreamActivity dreamActivity) {
            DreamService dreamService = this.mService.get();
            if (dreamService == null) {
                return;
            }
            dreamService.onActivityCreated(dreamActivity, this.mActivityDreamToken);
        }

        public void onActivityDestroyed() {
            DreamService dreamService = this.mService.get();
            if (dreamService == null) {
                return;
            }
            dreamService.onActivityDestroyed();
            this.mService = null;
        }
    }

    public static final class DreamMetadata {
        public final int dreamCategory;
        public final Drawable previewImage;
        public final ComponentName settingsActivity;
        public final boolean showComplications;

        public DreamMetadata(ComponentName componentName, Drawable drawable, boolean z, int i) {
            this.settingsActivity = componentName;
            this.previewImage = drawable;
            this.showComplications = z;
            if (com.android.internal.hidden_from_bootclasspath.android.service.controls.flags.Flags.homePanelDream()) {
                this.dreamCategory = i;
            } else {
                this.dreamCategory = 0;
            }
        }
    }

    public static void setDreamOverlayComponent(Intent intent, ComponentName componentName) {
        intent.putExtra(EXTRA_DREAM_OVERLAY_COMPONENT, componentName);
    }
}
