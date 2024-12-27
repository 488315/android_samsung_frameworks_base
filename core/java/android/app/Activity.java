package android.app;

import android.annotation.SystemApi;
import android.app.assist.AssistContent;
import android.app.compat.CompatChanges;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.ComponentCallbacksController;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentSender;
import android.content.LocusId;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.GraphicsEnvironment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.OutcomeReceiver;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Dumpable;
import android.util.EventLog;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SuperNotCalledException;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.RemoteAnimationDefinition;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityEvent;
import android.view.autofill.AutofillClientController;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.ContentCaptureManager;
import android.view.translation.TranslationSpec;
import android.view.translation.UiTranslationController;
import android.view.translation.UiTranslationSpec;
import android.webkit.WebView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import android.window.SplashScreen;
import android.window.WindowOnBackInvokedDispatcher;

import com.android.internal.R;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.app.ToolbarActionBar;
import com.android.internal.app.WindowDecorActionBar;
import com.android.internal.policy.DecorView;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.util.dump.DumpableContainerImpl;

import dalvik.system.VMRuntime;

import com.samsung.android.core.CompatSandbox;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class Activity extends ContextThemeWrapper
        implements LayoutInflater.Factory2,
                Window.Callback,
                KeyEvent.Callback,
                View.OnCreateContextMenuListener,
                ComponentCallbacks2,
                Window.OnWindowDismissedCallback,
                ContentCaptureManager.ContentCaptureClient {
    private static final int CONTENT_CAPTURE_PAUSE = 3;
    private static final int CONTENT_CAPTURE_RESUME = 2;
    private static final int CONTENT_CAPTURE_START = 1;
    private static final int CONTENT_CAPTURE_STOP = 4;
    private static final boolean DEBUG_LIFECYCLE = false;
    public static final int DEFAULT_KEYS_DIALER = 1;
    public static final int DEFAULT_KEYS_DISABLE = 0;
    public static final int DEFAULT_KEYS_SEARCH_GLOBAL = 4;
    public static final int DEFAULT_KEYS_SEARCH_LOCAL = 3;
    public static final int DEFAULT_KEYS_SHORTCUT = 2;
    public static final int DONT_FINISH_TASK_WITH_ACTIVITY = 0;
    public static final String DUMP_ARG_AUTOFILL = "--autofill";
    public static final String DUMP_ARG_CONTENT_CAPTURE = "--contentcapture";
    public static final String DUMP_ARG_DUMP_DUMPABLE = "--dump-dumpable";
    public static final String DUMP_ARG_LIST_DUMPABLES = "--list-dumpables";
    public static final String DUMP_ARG_TRANSLATION = "--translation";
    private static final long DUMP_IGNORES_SPECIAL_ARGS = 149254050;
    public static final int FINISH_TASK_WITH_ACTIVITY = 2;
    public static final int FINISH_TASK_WITH_ROOT_ACTIVITY = 1;
    protected static final int[] FOCUSED_STATE_SET = {16842908};
    static final String FRAGMENTS_TAG = "android:fragments";
    public static final int FULLSCREEN_MODE_REQUEST_ENTER = 1;
    public static final int FULLSCREEN_MODE_REQUEST_EXIT = 0;
    private static final String HAS_CURRENT_PERMISSIONS_REQUEST_KEY =
            "android:hasCurrentPermissionsRequest";
    private static final String KEYBOARD_SHORTCUTS_RECEIVER_DESKTOP_PKG_NAME =
            "com.sec.android.dexsystemui";
    private static final String KEYBOARD_SHORTCUTS_RECEIVER_PKG_NAME = "com.android.systemui";
    private static final int LOG_AM_ON_ACTIVITY_RESULT_CALLED = 30062;
    private static final int LOG_AM_ON_CREATE_CALLED = 30057;
    private static final int LOG_AM_ON_DESTROY_CALLED = 30060;
    private static final int LOG_AM_ON_PAUSE_CALLED = 30021;
    private static final int LOG_AM_ON_RESTART_CALLED = 30058;
    private static final int LOG_AM_ON_RESUME_CALLED = 30022;
    private static final int LOG_AM_ON_START_CALLED = 30059;
    private static final int LOG_AM_ON_STOP_CALLED = 30049;
    private static final int LOG_AM_ON_TOP_RESUMED_GAINED_CALLED = 30064;
    private static final int LOG_AM_ON_TOP_RESUMED_LOST_CALLED = 30065;
    public static final int OVERRIDE_TRANSITION_CLOSE = 1;
    public static final int OVERRIDE_TRANSITION_OPEN = 0;
    private static final String REQUEST_PERMISSIONS_WHO_PREFIX = "@android:requestPermissions:";
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_FIRST_USER = 1;
    public static final int RESULT_OK = -1;
    private static final String SAVED_DIALOGS_TAG = "android:savedDialogs";
    private static final String SAVED_DIALOG_ARGS_KEY_PREFIX = "android:dialog_args_";
    private static final String SAVED_DIALOG_IDS_KEY = "android:savedDialogIds";
    private static final String SAVED_DIALOG_KEY_PREFIX = "android:dialog_";
    private static final String SPEG_PACKAGE_NAME = "com.samsung.speg";
    private static final String TAG = "Activity";
    private static final String TAG_SPEG = "SPEG";
    private static final String WINDOW_HIERARCHY_TAG = "android:viewHierarchyState";
    ActivityInfo mActivityInfo;
    private Application mApplication;
    private IBinder mAssistToken;
    private AutofillClientController mAutofillClientController;
    private ComponentCallbacksController mCallbacksController;
    boolean mCalled;
    private ComponentCaller mCaller;
    private boolean mChangeCanvasToTranslucent;
    private ComponentName mComponent;
    int mConfigChangeFlags;
    private ContentCaptureManager mContentCaptureManager;
    private ComponentCaller mCurrentCaller;
    private OnBackInvokedCallback mDefaultBackCallback;
    private boolean mDestroyed;
    private DumpableContainerImpl mDumpableContainer;
    String mEmbeddedID;
    private boolean mEnableDefaultActionBarUp;
    boolean mEnterAnimationComplete;
    boolean mFinished;
    private boolean mHasCurrentPermissionsRequest;
    private int mIdent;
    private boolean mInOutsideLongPress;
    private boolean mInOutsideTouch;
    private ComponentCaller mInitialCaller;
    private Instrumentation mInstrumentation;
    Intent mIntent;
    private boolean mIsInMultiWindowMode;
    boolean mIsInPictureInPictureMode;
    private boolean mIsTopResumedActivity;
    NonConfigurationInstances mLastNonConfigurationInstances;
    private int mLastTaskDescriptionHashCode;
    boolean mLaunchedFromBubble;
    private GestureDetector mLongPressDetector;
    ActivityThread mMainThread;
    private SparseArray<ManagedDialog> mManagedDialogs;
    private MenuInflater mMenuInflater;
    Activity mParent;
    String mReferrer;
    private boolean mRestoredFromBundle;
    boolean mResumed;
    ActivityOptions.SceneTransitionInfo mSceneTransitionInfo;
    private ScreenCaptureCallbackHandler mScreenCaptureCallbackHandler;
    private SearchEvent mSearchEvent;
    private SearchManager mSearchManager;
    private IBinder mShareableActivityToken;
    private boolean mShouldDockBigOverlays;
    private SplashScreen mSplashScreen;
    boolean mStartedActivity;
    boolean mStopped;
    private CharSequence mTitle;
    private IBinder mToken;
    private TranslucentConversionListener mTranslucentCallback;
    private Thread mUiThread;
    private UiTranslationController mUiTranslationController;
    private IVoiceInteractionManagerService mVoiceInteractionManagerService;
    VoiceInteractor mVoiceInteractor;
    private Window mWindow;
    private WindowManager mWindowManager;
    private int mWindowingMode;
    private boolean mDoReportFullyDrawn = true;
    private boolean mCanEnterPictureInPicture = false;
    boolean mChangingConfigurations = false;
    Configuration mCurrentConfig = Configuration.EMPTY;
    private final ArrayList<Application.ActivityLifecycleCallbacks> mActivityLifecycleCallbacks =
            new ArrayList<>();
    View mDecor = null;
    boolean mWindowAdded = false;
    boolean mVisibleFromServer = false;
    boolean mVisibleFromClient = true;
    ActionBar mActionBar = null;
    private int mTitleColor = 0;
    final Handler mHandler = new Handler();
    final FragmentController mFragments = FragmentController.createController(new HostCallbacks());
    private final ArrayList<ManagedCursor> mManagedCursors = new ArrayList<>();
    int mResultCode = 0;
    Intent mResultData = null;
    private boolean mTitleReady = false;
    private int mActionModeTypeStarting = 0;
    private int mDefaultKeyMode = 0;
    private SpannableStringBuilder mDefaultKeySsb = null;
    private final ActivityManager.TaskDescription mTaskDescription =
            new ActivityManager.TaskDescription();
    private int mLastRequestedOrientation = -2;
    private final Object mInstanceTracker = StrictMode.trackActivity(this);
    final ActivityTransitionState mActivityTransitionState = new ActivityTransitionState();
    SharedElementCallback mEnterTransitionListener = SharedElementCallback.NULL_CALLBACK;
    SharedElementCallback mExitTransitionListener = SharedElementCallback.NULL_CALLBACK;
    private final Window.WindowControllerCallback mWindowControllerCallback =
            new Window.WindowControllerCallback() { // from class: android.app.Activity.1
                @Override // android.view.Window.WindowControllerCallback
                public void toggleFreeformWindowingMode() {
                    ActivityClient.getInstance().toggleFreeformWindowingMode(Activity.this.mToken);
                }

                @Override // android.view.Window.WindowControllerCallback
                public void enterPictureInPictureModeIfPossible() {
                    if (Activity.this.mActivityInfo.supportsPictureInPicture()) {
                        Activity.this.enterPictureInPictureMode();
                    }
                }

                @Override // android.view.Window.WindowControllerCallback
                public boolean isTaskRoot() {
                    return ActivityClient.getInstance()
                                    .getTaskForActivity(Activity.this.mToken, true)
                            >= 0;
                }

                @Override // android.view.Window.WindowControllerCallback
                public void updateStatusBarColor(int color) {
                    Activity.this.mTaskDescription.setStatusBarColor(color);
                    Activity.this.setTaskDescription(Activity.this.mTaskDescription);
                }

                @Override // android.view.Window.WindowControllerCallback
                public void updateSystemBarsAppearance(int appearance) {
                    Activity.this.mTaskDescription.setSystemBarsAppearance(appearance);
                    Activity.this.setTaskDescription(Activity.this.mTaskDescription);
                }

                @Override // android.view.Window.WindowControllerCallback
                public void updateNavigationBarColor(int color) {
                    Activity.this.mTaskDescription.setNavigationBarColor(color);
                    Activity.this.setTaskDescription(Activity.this.mTaskDescription);
                }
            };
    private int mDexTaskDocking = -1;
    private boolean mIsPopOver = false;
    private final GestureDetector.SimpleOnGestureListener mLongPressListener =
            new GestureDetector.SimpleOnGestureListener() { // from class: android.app.Activity.3
                @Override // android.view.GestureDetector.SimpleOnGestureListener,
                // android.view.GestureDetector.OnGestureListener
                public void onLongPress(MotionEvent e) {
                    Activity.this.mInOutsideLongPress = true;
                    Activity.this.applyTransparentPopOver();
                }
            };

    @Retention(RetentionPolicy.SOURCE)
    @interface ContentCaptureNotificationType {}

    @Retention(RetentionPolicy.SOURCE)
    @interface DefaultKeyMode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface FullscreenModeRequest {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface OverrideTransition {}

    public interface ScreenCaptureCallback {
        void onScreenCaptured();
    }

    public interface SemTranslucentConversionListener {
        void onTranslucentConversionCompleted(boolean z);
    }

    @SystemApi
    public interface TranslucentConversionListener {
        void onTranslucentConversionComplete(boolean z);
    }

    private static native String getDlWarning();

    private static class ManagedDialog {
        Bundle mArgs;
        Dialog mDialog;

        private ManagedDialog() {}
    }

    static final class NonConfigurationInstances {
        Object activity;
        HashMap<String, Object> children;
        FragmentManagerNonConfig fragments;
        ArrayMap<String, LoaderManager> loaders;
        VoiceInteractor voiceInteractor;

        NonConfigurationInstances() {}
    }

    private static final class ManagedCursor {
        private final Cursor mCursor;
        private boolean mReleased = false;
        private boolean mUpdated = false;

        ManagedCursor(Cursor cursor) {
            this.mCursor = cursor;
        }
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public void setIntent(Intent newIntent) {
        internalSetIntent(newIntent, null);
    }

    public ComponentCaller getCaller() {
        return this.mCaller;
    }

    public void setIntent(Intent newIntent, ComponentCaller newCaller) {
        internalSetIntent(newIntent, newCaller);
    }

    private void internalSetIntent(Intent newIntent, ComponentCaller newCaller) {
        this.mIntent = newIntent;
        this.mCaller = newCaller;
    }

    public void setLocusContext(LocusId locusId, Bundle bundle) {
        try {
            ActivityManager.getService()
                    .setActivityLocusContext(this.mComponent, locusId, this.mToken);
        } catch (RemoteException re) {
            re.rethrowFromSystemServer();
        }
        if (locusId != null) {
            setLocusContextToContentCapture(locusId, bundle);
        }
    }

    public final Application getApplication() {
        return this.mApplication;
    }

    @Deprecated
    public final boolean isChild() {
        return this.mParent != null;
    }

    @Deprecated
    public final Activity getParent() {
        return this.mParent;
    }

    public WindowManager getWindowManager() {
        return this.mWindowManager;
    }

    public Window getWindow() {
        return this.mWindow;
    }

    @Deprecated
    public LoaderManager getLoaderManager() {
        return this.mFragments.getLoaderManager();
    }

    public View getCurrentFocus() {
        if (this.mWindow != null) {
            return this.mWindow.getCurrentFocus();
        }
        return null;
    }

    private ContentCaptureManager getContentCaptureManager() {
        if (!UserHandle.isApp(Process.myUid())) {
            return null;
        }
        if (this.mContentCaptureManager == null) {
            this.mContentCaptureManager =
                    (ContentCaptureManager) getSystemService(ContentCaptureManager.class);
        }
        return this.mContentCaptureManager;
    }

    private String getContentCaptureTypeAsString(int type) {
        switch (type) {
            case 1:
                return "START";
            case 2:
                return "RESUME";
            case 3:
                return "PAUSE";
            case 4:
                return "STOP";
            default:
                return "UNKNOW-" + type;
        }
    }

    private void notifyContentCaptureManagerIfNeeded(int type) {
        if (Trace.isTagEnabled(64L)) {
            Trace.traceBegin(
                    64L,
                    "notifyContentCapture("
                            + getContentCaptureTypeAsString(type)
                            + ") for "
                            + this.mComponent.toShortString());
        }
        try {
            ContentCaptureManager cm = getContentCaptureManager();
            if (cm == null) {
                return;
            }
            switch (type) {
                case 1:
                    Window window = getWindow();
                    if (window != null) {
                        cm.updateWindowAttributes(window.getAttributes());
                    }
                    cm.onActivityCreated(
                            this.mToken, this.mShareableActivityToken, getComponentName());
                    break;
                case 2:
                    cm.onActivityResumed();
                    break;
                case 3:
                    cm.onActivityPaused();
                    break;
                case 4:
                    cm.onActivityDestroyed();
                    break;
                default:
                    Log.wtf(TAG, "Invalid @ContentCaptureNotificationType: " + type);
                    break;
            }
        } finally {
            Trace.traceEnd(64L);
        }
    }

    private void setLocusContextToContentCapture(LocusId locusId, Bundle bundle) {
        ContentCaptureManager cm = getContentCaptureManager();
        if (cm == null) {
            return;
        }
        ContentCaptureContext.Builder contentCaptureContextBuilder =
                new ContentCaptureContext.Builder(locusId);
        if (bundle != null) {
            contentCaptureContextBuilder.setExtras(bundle);
        }
        cm.getMainContentCaptureSession()
                .setContentCaptureContext(contentCaptureContextBuilder.build());
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        if (newBase != null) {
            newBase.setAutofillClient(getAutofillClient());
            newBase.setContentCaptureOptions(getContentCaptureOptions());
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final AutofillManager.AutofillClient getAutofillClient() {
        return getAutofillClientController();
    }

    private AutofillClientController getAutofillClientController() {
        if (this.mAutofillClientController == null) {
            this.mAutofillClientController = new AutofillClientController(this);
        }
        return this.mAutofillClientController;
    }

    @Override // android.content.Context
    public final ContentCaptureManager.ContentCaptureClient getContentCaptureClient() {
        return this;
    }

    public void registerActivityLifecycleCallbacks(
            Application.ActivityLifecycleCallbacks callback) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.add(callback);
        }
    }

    public void unregisterActivityLifecycleCallbacks(
            Application.ActivityLifecycleCallbacks callback) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.remove(callback);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        if (CompatChanges.isChangeEnabled(Context.OVERRIDABLE_COMPONENT_CALLBACKS)
                && this.mCallbacksController == null) {
            this.mCallbacksController = new ComponentCallbacksController();
        }
        if (this.mCallbacksController != null) {
            this.mCallbacksController.registerCallbacks(callback);
        } else {
            super.registerComponentCallbacks(callback);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        if (this.mCallbacksController != null) {
            this.mCallbacksController.unregisterCallbacks(callback);
        } else {
            super.unregisterComponentCallbacks(callback);
        }
    }

    private void dispatchActivityPreCreated(Bundle savedInstanceState) {
        getApplication().dispatchActivityPreCreated(this, savedInstanceState);
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((Application.ActivityLifecycleCallbacks) obj)
                        .onActivityPreCreated(this, savedInstanceState);
            }
        }
    }

    private void dispatchActivityCreated(Bundle savedInstanceState) {
        getApplication().dispatchActivityCreated(this, savedInstanceState);
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((Application.ActivityLifecycleCallbacks) obj)
                        .onActivityCreated(this, savedInstanceState);
            }
        }
    }

    private void dispatchActivityPostCreated(Bundle savedInstanceState) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((Application.ActivityLifecycleCallbacks) obj)
                        .onActivityPostCreated(this, savedInstanceState);
            }
        }
        getApplication().dispatchActivityPostCreated(this, savedInstanceState);
    }

    private void dispatchActivityPreStarted() {
        getApplication().dispatchActivityPreStarted(this);
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityPreStarted(this);
            }
        }
    }

    private void dispatchActivityStarted() {
        getApplication().dispatchActivityStarted(this);
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityStarted(this);
            }
        }
    }

    private void dispatchActivityPostStarted() {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityPostStarted(this);
            }
        }
        getApplication().dispatchActivityPostStarted(this);
    }

    private void dispatchActivityPreResumed() {
        getApplication().dispatchActivityPreResumed(this);
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityPreResumed(this);
            }
        }
    }

    private void dispatchActivityResumed() {
        getApplication().dispatchActivityResumed(this);
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityResumed(this);
            }
        }
    }

    private void dispatchActivityPostResumed() {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityPostResumed(this);
            }
        }
        getApplication().dispatchActivityPostResumed(this);
    }

    private void dispatchActivityPrePaused() {
        getApplication().dispatchActivityPrePaused(this);
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i]).onActivityPrePaused(this);
            }
        }
    }

    private void dispatchActivityPaused() {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i]).onActivityPaused(this);
            }
        }
        getApplication().dispatchActivityPaused(this);
    }

    private void dispatchActivityPostPaused() {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i]).onActivityPostPaused(this);
            }
        }
        getApplication().dispatchActivityPostPaused(this);
    }

    private void dispatchActivityPreStopped() {
        getApplication().dispatchActivityPreStopped(this);
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i]).onActivityPreStopped(this);
            }
        }
    }

    private void dispatchActivityStopped() {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i]).onActivityStopped(this);
            }
        }
        getApplication().dispatchActivityStopped(this);
    }

    private void dispatchActivityPostStopped() {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i]).onActivityPostStopped(this);
            }
        }
        getApplication().dispatchActivityPostStopped(this);
    }

    private void dispatchActivityPreSaveInstanceState(Bundle outState) {
        getApplication().dispatchActivityPreSaveInstanceState(this, outState);
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i])
                        .onActivityPreSaveInstanceState(this, outState);
            }
        }
    }

    private void dispatchActivitySaveInstanceState(Bundle outState) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i])
                        .onActivitySaveInstanceState(this, outState);
            }
        }
        getApplication().dispatchActivitySaveInstanceState(this, outState);
    }

    private void dispatchActivityPostSaveInstanceState(Bundle outState) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i])
                        .onActivityPostSaveInstanceState(this, outState);
            }
        }
        getApplication().dispatchActivityPostSaveInstanceState(this, outState);
    }

    private void dispatchActivityPreDestroyed() {
        getApplication().dispatchActivityPreDestroyed(this);
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i])
                        .onActivityPreDestroyed(this);
            }
        }
    }

    private void dispatchActivityDestroyed() {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i]).onActivityDestroyed(this);
            }
        }
        getApplication().dispatchActivityDestroyed(this);
    }

    private void dispatchActivityPostDestroyed() {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = callbacks.length - 1; i >= 0; i--) {
                ((Application.ActivityLifecycleCallbacks) callbacks[i])
                        .onActivityPostDestroyed(this);
            }
        }
        getApplication().dispatchActivityPostDestroyed(this);
    }

    private void dispatchActivityConfigurationChanged() {
        if (getApplication() != null) {
            getApplication().dispatchActivityConfigurationChanged(this);
        }
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityConfigurationChanged(this);
            }
        }
    }

    private Object[] collectActivityLifecycleCallbacks() {
        Object[] callbacks = null;
        synchronized (this.mActivityLifecycleCallbacks) {
            if (this.mActivityLifecycleCallbacks.size() > 0) {
                callbacks = this.mActivityLifecycleCallbacks.toArray();
            }
        }
        return callbacks;
    }

    private void notifyVoiceInteractionManagerServiceActivityEvent(int type) {
        if (this.mVoiceInteractionManagerService == null) {
            this.mVoiceInteractionManagerService =
                    IVoiceInteractionManagerService.Stub.asInterface(
                            ServiceManager.getService(Context.VOICE_INTERACTION_MANAGER_SERVICE));
            if (this.mVoiceInteractionManagerService == null) {
                Log.w(
                        TAG,
                        "notifyVoiceInteractionManagerServiceActivityEvent: Can not get"
                                + " VoiceInteractionManagerService");
                return;
            }
        }
        try {
            this.mVoiceInteractionManagerService.notifyActivityEventChanged(this.mToken, type);
        } catch (RemoteException e) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        if (this.mLastNonConfigurationInstances != null) {
            this.mFragments.restoreLoaderNonConfig(this.mLastNonConfigurationInstances.loaders);
        }
        if (this.mActivityInfo.parentActivityName != null) {
            if (this.mActionBar == null) {
                this.mEnableDefaultActionBarUp = true;
            } else {
                this.mActionBar.setDefaultDisplayHomeAsUpEnabled(true);
            }
        }
        if (savedInstanceState != null) {
            getAutofillClientController().onActivityCreated(savedInstanceState);
            Parcelable p = savedInstanceState.getParcelable(FRAGMENTS_TAG);
            this.mFragments.restoreAllState(
                    p,
                    this.mLastNonConfigurationInstances != null
                            ? this.mLastNonConfigurationInstances.fragments
                            : null);
        }
        this.mFragments.dispatchCreate();
        dispatchActivityCreated(savedInstanceState);
        if (this.mVoiceInteractor != null) {
            this.mVoiceInteractor.attachActivity(this);
        }
        this.mRestoredFromBundle = savedInstanceState != null;
        this.mCalled = true;
        boolean aheadOfTimeBack =
                WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this);
        if (aheadOfTimeBack) {
            this.mDefaultBackCallback =
                    new OnBackInvokedCallback() { // from class:
                        // android.app.Activity$$ExternalSyntheticLambda0
                        @Override // android.window.OnBackInvokedCallback
                        public final void onBackInvoked() {
                            Activity.this.onBackInvoked();
                        }
                    };
            getOnBackInvokedDispatcher()
                    .registerSystemOnBackInvokedCallback(this.mDefaultBackCallback);
        }
    }

    public final SplashScreen getSplashScreen() {
        return getOrCreateSplashScreen();
    }

    private SplashScreen getOrCreateSplashScreen() {
        SplashScreen splashScreen;
        synchronized (this) {
            if (this.mSplashScreen == null) {
                this.mSplashScreen = new SplashScreen.SplashScreenImpl(this);
            }
            splashScreen = this.mSplashScreen;
        }
        return splashScreen;
    }

    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        onCreate(savedInstanceState);
    }

    final void performRestoreInstanceState(Bundle savedInstanceState) {
        onRestoreInstanceState(savedInstanceState);
        restoreManagedDialogs(savedInstanceState);
    }

    final void performRestoreInstanceState(
            Bundle savedInstanceState, PersistableBundle persistentState) {
        onRestoreInstanceState(savedInstanceState, persistentState);
        if (savedInstanceState != null) {
            restoreManagedDialogs(savedInstanceState);
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Bundle windowState;
        if (this.mWindow != null
                && (windowState = savedInstanceState.getBundle(WINDOW_HIERARCHY_TAG)) != null) {
            this.mWindow.restoreHierarchyState(windowState);
        }
    }

    public void onRestoreInstanceState(
            Bundle savedInstanceState, PersistableBundle persistentState) {
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }

    private void restoreManagedDialogs(Bundle savedInstanceState) {
        Bundle b = savedInstanceState.getBundle(SAVED_DIALOGS_TAG);
        if (b == null) {
            return;
        }
        int[] ids = b.getIntArray(SAVED_DIALOG_IDS_KEY);
        int numDialogs = ids.length;
        this.mManagedDialogs = new SparseArray<>(numDialogs);
        for (int dialogId : ids) {
            Bundle dialogState = b.getBundle(savedDialogKeyFor(dialogId));
            if (dialogState != null) {
                ManagedDialog md = new ManagedDialog();
                md.mArgs = b.getBundle(savedDialogArgsKeyFor(dialogId));
                md.mDialog = createDialog(Integer.valueOf(dialogId), dialogState, md.mArgs);
                if (md.mDialog != null) {
                    this.mManagedDialogs.put(dialogId, md);
                    onPrepareDialog(dialogId, md.mDialog, md.mArgs);
                    md.mDialog.onRestoreInstanceState(dialogState);
                }
            }
        }
    }

    private Dialog createDialog(Integer dialogId, Bundle state, Bundle args) {
        Dialog dialog = onCreateDialog(dialogId.intValue(), args);
        if (dialog == null) {
            return null;
        }
        dialog.dispatchOnCreate(state);
        return dialog;
    }

    private static String savedDialogKeyFor(int key) {
        return SAVED_DIALOG_KEY_PREFIX + key;
    }

    private static String savedDialogArgsKeyFor(int key) {
        return SAVED_DIALOG_ARGS_KEY_PREFIX + key;
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        if (!isChild()) {
            this.mTitleReady = true;
            onTitleChanged(getTitle(), getTitleColor());
        }
        this.mCalled = true;
        notifyContentCaptureManagerIfNeeded(1);
        notifyVoiceInteractionManagerServiceActivityEvent(1);
    }

    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        onPostCreate(savedInstanceState);
    }

    protected void onStart() {
        this.mCalled = true;
        this.mFragments.doLoaderStart();
        dispatchActivityStarted();
        getAutofillClientController().onActivityStarted();
    }

    protected void onRestart() {
        this.mCalled = true;
    }

    @Deprecated
    public void onStateNotSaved() {}

    protected void onResume() {
        dispatchActivityResumed();
        this.mActivityTransitionState.onResume(this);
        getAutofillClientController().onActivityResumed();
        notifyContentCaptureManagerIfNeeded(2);
        this.mCalled = true;
        if (this.mIsInPictureInPictureMode
                && getResources().getConfiguration().windowConfiguration.getWindowingMode() != 2) {
            Slog.w(TAG, "[PipTaskOrganizer] init mIsInPictureInPictureMode false activity=" + this);
            this.mIsInPictureInPictureMode = false;
        }
    }

    protected void onPostResume() {
        Window win = getWindow();
        if (win != null) {
            win.makeActive();
        }
        if (this.mActionBar != null) {
            this.mActionBar.setShowHideAnimationEnabled(true);
        }
        notifyVoiceInteractionManagerServiceActivityEvent(2);
        this.mCalled = true;
    }

    public void onTopResumedActivityChanged(boolean isTopResumedActivity) {}

    final void performTopResumedActivityChanged(boolean isTopResumedActivity, String reason) {
        if (MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED
                && this.mResumed
                && !"pausing".equals(reason)
                && (!isTopResumedActivity || this.mIsTopResumedActivity)) {
            return;
        }
        this.mIsTopResumedActivity = isTopResumedActivity;
        onTopResumedActivityChanged(isTopResumedActivity);
        if (isTopResumedActivity) {
            EventLogTags.writeWmOnTopResumedGainedCalled(
                    this.mIdent, getComponentName().getClassName(), reason);
        } else {
            EventLogTags.writeWmOnTopResumedLostCalled(
                    this.mIdent, getComponentName().getClassName(), reason);
        }
    }

    void setVoiceInteractor(IVoiceInteractor voiceInteractor) {
        if (this.mVoiceInteractor != null) {
            VoiceInteractor.Request[] requests = this.mVoiceInteractor.getActiveRequests();
            if (requests != null) {
                for (VoiceInteractor.Request activeRequest :
                        this.mVoiceInteractor.getActiveRequests()) {
                    activeRequest.cancel();
                    activeRequest.clear();
                }
            }
        }
        if (voiceInteractor == null) {
            this.mVoiceInteractor = null;
        } else {
            this.mVoiceInteractor =
                    new VoiceInteractor(voiceInteractor, this, this, Looper.myLooper());
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public int getNextAutofillId() {
        return getAutofillClientController().getNextAutofillId();
    }

    public boolean isVoiceInteraction() {
        return this.mVoiceInteractor != null;
    }

    public boolean isVoiceInteractionRoot() {
        return this.mVoiceInteractor != null
                && ActivityClient.getInstance().isRootVoiceInteraction(this.mToken);
    }

    public VoiceInteractor getVoiceInteractor() {
        return this.mVoiceInteractor;
    }

    public boolean isLocalVoiceInteractionSupported() {
        try {
            return ActivityTaskManager.getService().supportsLocalVoiceInteraction();
        } catch (RemoteException e) {
            return false;
        }
    }

    public void startLocalVoiceInteraction(Bundle privateOptions) {
        ActivityClient.getInstance().startLocalVoiceInteraction(this.mToken, privateOptions);
    }

    public void onLocalVoiceInteractionStarted() {}

    public void onLocalVoiceInteractionStopped() {}

    public void stopLocalVoiceInteraction() {
        ActivityClient.getInstance().stopLocalVoiceInteraction(this.mToken);
    }

    protected void onNewIntent(Intent intent) {}

    public void onNewIntent(Intent intent, ComponentCaller caller) {
        onNewIntent(intent);
    }

    final void performSaveInstanceState(Bundle outState) {
        dispatchActivityPreSaveInstanceState(outState);
        onSaveInstanceState(outState);
        saveManagedDialogs(outState);
        this.mActivityTransitionState.saveState(outState);
        storeHasCurrentPermissionRequest(outState);
        dispatchActivityPostSaveInstanceState(outState);
    }

    final void performSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        dispatchActivityPreSaveInstanceState(outState);
        onSaveInstanceState(outState, outPersistentState);
        saveManagedDialogs(outState);
        storeHasCurrentPermissionRequest(outState);
        dispatchActivityPostSaveInstanceState(outState);
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle(WINDOW_HIERARCHY_TAG, this.mWindow.saveHierarchyState());
        Parcelable p = this.mFragments.saveAllState();
        if (p != null) {
            outState.putParcelable(FRAGMENTS_TAG, p);
        }
        getAutofillClientController().onSaveInstanceState(outState);
        dispatchActivitySaveInstanceState(outState);
    }

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        onSaveInstanceState(outState);
    }

    private void saveManagedDialogs(Bundle outState) {
        int numDialogs;
        if (this.mManagedDialogs == null || (numDialogs = this.mManagedDialogs.size()) == 0) {
            return;
        }
        Bundle dialogState = new Bundle();
        int[] ids = new int[this.mManagedDialogs.size()];
        for (int i = 0; i < numDialogs; i++) {
            int key = this.mManagedDialogs.keyAt(i);
            ids[i] = key;
            ManagedDialog md = this.mManagedDialogs.valueAt(i);
            dialogState.putBundle(savedDialogKeyFor(key), md.mDialog.onSaveInstanceState());
            if (md.mArgs != null) {
                dialogState.putBundle(savedDialogArgsKeyFor(key), md.mArgs);
            }
        }
        dialogState.putIntArray(SAVED_DIALOG_IDS_KEY, ids);
        outState.putBundle(SAVED_DIALOGS_TAG, dialogState);
    }

    protected void onPause() {
        dispatchActivityPaused();
        getAutofillClientController().onActivityPaused();
        notifyContentCaptureManagerIfNeeded(3);
        notifyVoiceInteractionManagerServiceActivityEvent(3);
        this.mCalled = true;
    }

    protected void onUserLeaveHint() {}

    @Deprecated
    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
        return false;
    }

    public CharSequence onCreateDescription() {
        return null;
    }

    public void onProvideAssistData(Bundle data) {}

    public void onProvideAssistContent(AssistContent outContent) {}

    public void onGetDirectActions(
            CancellationSignal cancellationSignal, Consumer<List<DirectAction>> callback) {
        callback.accept(Collections.emptyList());
    }

    public void onPerformDirectAction(
            String actionId,
            Bundle arguments,
            CancellationSignal cancellationSignal,
            Consumer<Bundle> resultListener) {}

    public final void requestShowKeyboardShortcuts() {
        ComponentName sysuiComponent =
                ComponentName.unflattenFromString(
                        getResources().getString(R.string.config_systemUIServiceComponent));
        Intent intent = new Intent(Intent.ACTION_SHOW_KEYBOARD_SHORTCUTS);
        intent.setPackage(sysuiComponent.getPackageName());
        if (getDisplay().getDisplayId() == 2) {
            intent.setPackage(KEYBOARD_SHORTCUTS_RECEIVER_DESKTOP_PKG_NAME);
        }
        sendBroadcastAsUser(intent, Process.myUserHandle());
    }

    public final void dismissKeyboardShortcutsHelper() {
        ComponentName sysuiComponent =
                ComponentName.unflattenFromString(
                        getResources().getString(R.string.config_systemUIServiceComponent));
        Intent intent = new Intent(Intent.ACTION_DISMISS_KEYBOARD_SHORTCUTS);
        intent.setPackage(sysuiComponent.getPackageName());
        if (getDisplay().getDisplayId() == 2) {
            intent.setPackage(KEYBOARD_SHORTCUTS_RECEIVER_DESKTOP_PKG_NAME);
        }
        sendBroadcastAsUser(intent, Process.myUserHandle());
    }

    @Override // android.view.Window.Callback
    public void onProvideKeyboardShortcuts(
            List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
        if (menu == null) {
            return;
        }
        KeyboardShortcutGroup group = null;
        int menuSize = menu.size();
        for (int i = 0; i < menuSize; i++) {
            MenuItem item = menu.getItem(i);
            CharSequence title = item.getTitle();
            char alphaShortcut = item.getAlphabeticShortcut();
            int alphaModifiers = item.getAlphabeticModifiers();
            if (title != null && alphaShortcut != 0) {
                if (group == null) {
                    int resource = this.mApplication.getApplicationInfo().labelRes;
                    group = new KeyboardShortcutGroup(resource != 0 ? getString(resource) : null);
                }
                group.addItem(new KeyboardShortcutInfo(title, alphaShortcut, alphaModifiers));
            }
        }
        if (group != null) {
            data.add(group);
        }
    }

    public boolean showAssist(Bundle args) {
        return ActivityClient.getInstance().showAssistFromActivity(this.mToken, args);
    }

    protected void onStop() {
        if (this.mActionBar != null) {
            this.mActionBar.setShowHideAnimationEnabled(false);
        }
        this.mActivityTransitionState.onStop(this);
        dispatchActivityStopped();
        this.mTranslucentCallback = null;
        this.mCalled = true;
        getAutofillClientController().onActivityStopped(this.mIntent, this.mChangingConfigurations);
        this.mEnterAnimationComplete = false;
        notifyVoiceInteractionManagerServiceActivityEvent(4);
    }

    protected void onDestroy() {
        this.mCalled = true;
        getAutofillClientController().onActivityDestroyed();
        if (this.mManagedDialogs != null) {
            int numDialogs = this.mManagedDialogs.size();
            for (int i = 0; i < numDialogs; i++) {
                ManagedDialog md = this.mManagedDialogs.valueAt(i);
                if (md.mDialog.isShowing()) {
                    md.mDialog.dismiss();
                }
            }
            this.mManagedDialogs = null;
        }
        synchronized (this.mManagedCursors) {
            int numCursors = this.mManagedCursors.size();
            for (int i2 = 0; i2 < numCursors; i2++) {
                ManagedCursor c = this.mManagedCursors.get(i2);
                if (c != null) {
                    c.mCursor.close();
                }
            }
            this.mManagedCursors.clear();
        }
        if (this.mSearchManager != null) {
            this.mSearchManager.stopSearch();
        }
        if (this.mActionBar != null) {
            this.mActionBar.onDestroy();
        }
        dispatchActivityDestroyed();
        notifyContentCaptureManagerIfNeeded(4);
        if (this.mUiTranslationController != null) {
            this.mUiTranslationController.onActivityDestroyed();
        }
        if (this.mDefaultBackCallback != null) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mDefaultBackCallback);
            this.mDefaultBackCallback = null;
        }
        if (this.mCallbacksController != null) {
            this.mCallbacksController.clearCallbacks();
        }
    }

    public void reportFullyDrawn() {
        if (this.mDoReportFullyDrawn) {
            if (Trace.isTagEnabled(64L)) {
                Trace.traceBegin(64L, "reportFullyDrawn() for " + this.mComponent.toShortString());
            }
            this.mDoReportFullyDrawn = false;
            try {
                ActivityClient.getInstance()
                        .reportActivityFullyDrawn(this.mToken, this.mRestoredFromBundle);
                VMRuntime.getRuntime().notifyStartupCompleted();
            } finally {
                Trace.traceEnd(64L);
            }
        }
    }

    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
        onMultiWindowModeChanged(isInMultiWindowMode);
    }

    @Deprecated
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {}

    public boolean isInMultiWindowMode() {
        return this.mIsInMultiWindowMode;
    }

    public void onPictureInPictureModeChanged(
            boolean isInPictureInPictureMode, Configuration newConfig) {
        onPictureInPictureModeChanged(isInPictureInPictureMode);
    }

    public void onPictureInPictureUiStateChanged(PictureInPictureUiState pipState) {}

    @Deprecated
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {}

    public boolean isInPictureInPictureMode() {
        return this.mIsInPictureInPictureMode;
    }

    @Deprecated
    public void enterPictureInPictureMode() {
        enterPictureInPictureMode(new PictureInPictureParams.Builder().build());
    }

    public boolean enterPictureInPictureMode(PictureInPictureParams params) {
        if (!deviceSupportsPictureInPictureMode()) {
            return false;
        }
        if (params == null) {
            throw new IllegalArgumentException("Expected non-null picture-in-picture params");
        }
        if (!this.mCanEnterPictureInPicture) {
            throw new IllegalStateException("Activity must be resumed to enter picture-in-picture");
        }
        this.mIsInPictureInPictureMode =
                ActivityClient.getInstance().enterPictureInPictureMode(this.mToken, params);
        return this.mIsInPictureInPictureMode;
    }

    public void setPictureInPictureParams(PictureInPictureParams params) {
        if (!deviceSupportsPictureInPictureMode()) {
            return;
        }
        if (params == null) {
            throw new IllegalArgumentException("Expected non-null picture-in-picture params");
        }
        ActivityClient.getInstance().setPictureInPictureParams(this.mToken, params);
    }

    public int getMaxNumPictureInPictureActions() {
        return ActivityTaskManager.getMaxNumPictureInPictureActions(this);
    }

    private boolean deviceSupportsPictureInPictureMode() {
        if (!MultiWindowCoreState.MW_ENABLED) {
            return false;
        }
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE);
    }

    public boolean onPictureInPictureRequested() {
        return false;
    }

    public void requestFullscreenMode(
            int request, OutcomeReceiver<Void, Throwable> approvalCallback) {
        FullscreenRequestHandler.requestFullscreenMode(
                request, approvalCallback, this.mCurrentConfig, getActivityToken());
    }

    public void setShouldDockBigOverlays(boolean shouldDockBigOverlays) {
        ActivityClient.getInstance().setShouldDockBigOverlays(this.mToken, shouldDockBigOverlays);
        this.mShouldDockBigOverlays = shouldDockBigOverlays;
    }

    public boolean shouldDockBigOverlays() {
        return this.mShouldDockBigOverlays;
    }

    void dispatchMovedToDisplay(int displayId, Configuration config) {
        updateDisplay(displayId);
        onMovedToDisplay(displayId, config);
    }

    public void onMovedToDisplay(int displayId, Configuration config) {}

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        this.mCalled = true;
        boolean isPopOver = newConfig.windowConfiguration.isPopOver();
        if (this.mIsPopOver != isPopOver) {
            this.mIsPopOver = isPopOver;
            this.mInOutsideTouch = false;
            if (this.mInOutsideLongPress) {
                this.mInOutsideLongPress = false;
                clearTransparentPopOver();
            }
        }
        this.mFragments.dispatchConfigurationChanged(newConfig);
        if (this.mWindow != null) {
            this.mWindow.onConfigurationChanged(newConfig);
        }
        if (this.mActionBar != null) {
            this.mActionBar.onConfigurationChanged(newConfig);
        }
        dispatchActivityConfigurationChanged();
        if (this.mCallbacksController != null) {
            this.mCallbacksController.dispatchConfigurationChanged(newConfig);
        }
    }

    public int getChangingConfigurations() {
        return this.mConfigChangeFlags;
    }

    public Object getLastNonConfigurationInstance() {
        if (this.mLastNonConfigurationInstances != null) {
            return this.mLastNonConfigurationInstances.activity;
        }
        return null;
    }

    public Object onRetainNonConfigurationInstance() {
        return null;
    }

    HashMap<String, Object> getLastNonConfigurationChildInstances() {
        if (this.mLastNonConfigurationInstances != null) {
            return this.mLastNonConfigurationInstances.children;
        }
        return null;
    }

    HashMap<String, Object> onRetainNonConfigurationChildInstances() {
        return null;
    }

    NonConfigurationInstances retainNonConfigurationInstances() {
        Object activity = onRetainNonConfigurationInstance();
        HashMap<String, Object> children = onRetainNonConfigurationChildInstances();
        FragmentManagerNonConfig fragments = this.mFragments.retainNestedNonConfig();
        this.mFragments.doLoaderStart();
        this.mFragments.doLoaderStop(true);
        ArrayMap<String, LoaderManager> loaders = this.mFragments.retainLoaderNonConfig();
        if (activity == null
                && children == null
                && fragments == null
                && loaders == null
                && this.mVoiceInteractor == null) {
            return null;
        }
        NonConfigurationInstances nci = new NonConfigurationInstances();
        nci.activity = activity;
        nci.children = children;
        nci.fragments = fragments;
        nci.loaders = loaders;
        if (this.mVoiceInteractor != null) {
            this.mVoiceInteractor.retainInstance();
            nci.voiceInteractor = this.mVoiceInteractor;
        }
        return nci;
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCalled = true;
        this.mFragments.dispatchLowMemory();
        if (this.mCallbacksController != null) {
            this.mCallbacksController.dispatchLowMemory();
        }
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        this.mCalled = true;
        this.mFragments.dispatchTrimMemory(level);
        if (this.mCallbacksController != null) {
            this.mCallbacksController.dispatchTrimMemory(level);
        }
    }

    @Deprecated
    public FragmentManager getFragmentManager() {
        return this.mFragments.getFragmentManager();
    }

    @Deprecated
    public void onAttachFragment(Fragment fragment) {}

    @Deprecated
    public final Cursor managedQuery(
            Uri uri, String[] projection, String selection, String sortOrder) {
        Cursor c = getContentResolver().query(uri, projection, selection, null, sortOrder);
        if (c != null) {
            startManagingCursor(c);
        }
        return c;
    }

    @Deprecated
    public final Cursor managedQuery(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {
        Cursor c = getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
        if (c != null) {
            startManagingCursor(c);
        }
        return c;
    }

    @Deprecated
    public void startManagingCursor(Cursor c) {
        synchronized (this.mManagedCursors) {
            this.mManagedCursors.add(new ManagedCursor(c));
        }
    }

    @Deprecated
    public void stopManagingCursor(Cursor c) {
        synchronized (this.mManagedCursors) {
            int N = this.mManagedCursors.size();
            int i = 0;
            while (true) {
                if (i >= N) {
                    break;
                }
                ManagedCursor mc = this.mManagedCursors.get(i);
                if (mc.mCursor != c) {
                    i++;
                } else {
                    this.mManagedCursors.remove(i);
                    break;
                }
            }
        }
    }

    @Deprecated
    public void setPersistent(boolean isPersistent) {}

    public <T extends View> T findViewById(int i) {
        return (T) getWindow().findViewById(i);
    }

    public final <T extends View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t == null) {
            throw new IllegalArgumentException("ID does not reference a View inside this Activity");
        }
        return t;
    }

    public ActionBar getActionBar() {
        initWindowDecorActionBar();
        return this.mActionBar;
    }

    public void setActionBar(Toolbar toolbar) {
        ActionBar ab = getActionBar();
        if (ab instanceof WindowDecorActionBar) {
            throw new IllegalStateException(
                    "This Activity already has an action bar supplied by the window decor. Do not"
                        + " request Window.FEATURE_ACTION_BAR and set android:windowActionBar to"
                        + " false in your theme to use a Toolbar instead.");
        }
        this.mMenuInflater = null;
        if (ab != null) {
            ab.onDestroy();
        }
        if (toolbar != null) {
            ToolbarActionBar tbab = new ToolbarActionBar(toolbar, getTitle(), this);
            this.mActionBar = tbab;
            this.mWindow.setCallback(tbab.getWrappedWindowCallback());
        } else {
            this.mActionBar = null;
            this.mWindow.setCallback(this);
        }
        invalidateOptionsMenu();
    }

    private void initWindowDecorActionBar() {
        Window window = getWindow();
        window.getDecorView();
        if (isChild() || !window.hasFeature(8) || this.mActionBar != null) {
            return;
        }
        this.mActionBar = new WindowDecorActionBar(this);
        this.mActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
        this.mWindow.setDefaultIcon(this.mActivityInfo.getIconResource());
        this.mWindow.setDefaultLogo(this.mActivityInfo.getLogoResource());
    }

    private void idsUiUpdated() {
        ActivityThread.currentActivityThread().getIdsController().uiUpdated(3);
    }

    public void setContentView(int layoutResID) {
        getWindow().setContentView(layoutResID);
        initWindowDecorActionBar();
        idsUiUpdated();
    }

    public void setContentView(View view) {
        getWindow().setContentView(view);
        initWindowDecorActionBar();
        idsUiUpdated();
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getWindow().setContentView(view, params);
        initWindowDecorActionBar();
        idsUiUpdated();
    }

    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getWindow().addContentView(view, params);
        initWindowDecorActionBar();
    }

    public TransitionManager getContentTransitionManager() {
        return getWindow().getTransitionManager();
    }

    public void setContentTransitionManager(TransitionManager tm) {
        getWindow().setTransitionManager(tm);
    }

    public Scene getContentScene() {
        return getWindow().getContentScene();
    }

    public void setFinishOnTouchOutside(boolean finish) {
        this.mWindow.setCloseOnTouchOutside(finish);
    }

    public final void setDefaultKeyMode(int mode) {
        this.mDefaultKeyMode = mode;
        switch (mode) {
            case 0:
            case 2:
                this.mDefaultKeySsb = null;
                return;
            case 1:
            case 3:
            case 4:
                this.mDefaultKeySsb = new SpannableStringBuilder();
                Selection.setSelection(this.mDefaultKeySsb, 0);
                return;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled;
        if (keyCode == 4) {
            if (getApplicationInfo().targetSdkVersion >= 5) {
                Slog.d(TAG, "onKeyDown(KEYCODE_BACK), activity=" + this);
                event.startTracking();
            } else {
                onBackPressed();
            }
            return true;
        }
        if (keyCode == 111 && this.mWindow.shouldCloseOnTouchOutside()) {
            event.startTracking();
            finish();
            return true;
        }
        if (this.mDefaultKeyMode == 0) {
            return false;
        }
        if (this.mDefaultKeyMode == 2) {
            Window w = getWindow();
            return w.hasFeature(0) && w.performPanelShortcut(0, keyCode, event, 2);
        }
        if (keyCode == 61) {
            return false;
        }
        boolean clearSpannable = false;
        if (event.getRepeatCount() != 0 || event.isSystem()) {
            clearSpannable = true;
            handled = false;
        } else {
            handled =
                    TextKeyListener.getInstance()
                            .onKeyDown(null, this.mDefaultKeySsb, keyCode, event);
            if (handled && this.mDefaultKeySsb.length() > 0) {
                String str = this.mDefaultKeySsb.toString();
                clearSpannable = true;
                switch (this.mDefaultKeyMode) {
                    case 1:
                        Intent intent =
                                new Intent(Intent.ACTION_DIAL, Uri.parse(WebView.SCHEME_TEL + str));
                        intent.addFlags(268435456);
                        startActivity(intent);
                        break;
                    case 3:
                        startSearch(str, false, null, false);
                        break;
                    case 4:
                        startSearch(str, false, null, true);
                        break;
                }
            }
        }
        if (clearSpannable) {
            this.mDefaultKeySsb.clear();
            this.mDefaultKeySsb.clearSpans();
            Selection.setSelection(this.mDefaultKeySsb, 0);
        }
        return handled;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        int sdkVersion = getApplicationInfo().targetSdkVersion;
        if (sdkVersion >= 5) {
            if (keyCode == 4
                    && event.isTracking()
                    && !event.isCanceled()
                    && this.mDefaultBackCallback == null) {
                onBackPressed();
                return true;
            }
            if (keyCode == 4) {
                boolean hasCallback = this.mDefaultBackCallback != null;
                Slog.d(
                        TAG,
                        "onKeyUp(KEYCODE_BACK) isTracking()="
                                + event.isTracking()
                                + " isCanceled()="
                                + event.isCanceled()
                                + " hasCallback="
                                + hasCallback);
            }
        }
        return keyCode == 111 && event.isTracking();
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return false;
    }

    private static final class RequestFinishCallback extends IRequestFinishCallback.Stub {
        private final WeakReference<Activity> mActivityRef;

        RequestFinishCallback(WeakReference<Activity> activityRef) {
            this.mActivityRef = activityRef;
        }

        @Override // android.app.IRequestFinishCallback
        public void requestFinish() {
            final Activity activity = this.mActivityRef.get();
            if (activity != null) {
                Handler handler = activity.mHandler;
                Objects.requireNonNull(activity);
                handler.post(
                        new Runnable() { // from class:
                            // android.app.Activity$RequestFinishCallback$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                Activity.this.finishAfterTransition();
                            }
                        });
            }
        }
    }

    @Deprecated
    public void onBackPressed() {
        if (this.mActionBar != null && this.mActionBar.collapseActionView()) {
            return;
        }
        FragmentManager fragmentManager = this.mFragments.getFragmentManager();
        if (!fragmentManager.isStateSaved() && fragmentManager.popBackStackImmediate()) {
            return;
        }
        onBackInvoked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackInvoked() {
        Slog.d(TAG, "onBackInvoked, activity=" + this + ", caller=" + Debug.getCallers(3));
        ActivityClient.getInstance()
                .onBackPressed(this.mToken, new RequestFinishCallback(new WeakReference(this)));
        if (isTaskRoot()) {
            getAutofillClientController().onActivityBackPressed(this.mIntent);
        }
    }

    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        ActionBar actionBar = getActionBar();
        return actionBar != null && actionBar.onKeyShortcut(keyCode, event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mWindow.shouldCloseOnTouch(this, event)) {
            finish();
            return true;
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent event) {
        return false;
    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        return false;
    }

    public void onUserInteraction() {}

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        View decor;
        if (this.mParent == null && (decor = this.mDecor) != null && decor.getParent() != null) {
            getWindowManager().updateViewLayout(decor, params);
            if (this.mContentCaptureManager != null) {
                this.mContentCaptureManager.updateWindowAttributes(params);
            }
        }
    }

    @Override // android.view.Window.Callback
    public void onContentChanged() {}

    @Override // android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {}

    @Override // android.view.Window.Callback
    public void onAttachedToWindow() {}

    @Override // android.view.Window.Callback
    public void onDetachedFromWindow() {}

    public boolean hasWindowFocus() {
        View d;
        Window w = getWindow();
        if (w != null && (d = w.getDecorView()) != null) {
            return d.hasWindowFocus();
        }
        return false;
    }

    @Override // android.view.Window.OnWindowDismissedCallback
    public void onWindowDismissed(boolean finishTask, boolean suppressWindowTransition) {
        finish(finishTask ? 2 : 0);
        if (suppressWindowTransition) {
            overridePendingTransition(0, 0);
        }
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent event) {
        onUserInteraction();
        int keyCode = event.getKeyCode();
        if (keyCode == 82 && this.mActionBar != null && this.mActionBar.onMenuKeyEvent(event)) {
            return true;
        }
        Window win = getWindow();
        if (win.superDispatchKeyEvent(event)) {
            return true;
        }
        View decor = this.mDecor;
        if (decor == null) {
            decor = win.getDecorView();
        }
        return event.dispatch(this, decor != null ? decor.getKeyDispatcherState() : null, this);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        onUserInteraction();
        if (getWindow().superDispatchKeyShortcutEvent(event)) {
            return true;
        }
        return onKeyShortcut(event.getKeyCode(), event);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            onUserInteraction();
        }
        if (interceptTouchEventForPopOver(ev) || getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(MotionEvent ev) {
        onUserInteraction();
        if (getWindow().superDispatchTrackballEvent(ev)) {
            return true;
        }
        return onTrackballEvent(ev);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        onUserInteraction();
        if (getWindow().superDispatchGenericMotionEvent(ev)) {
            return true;
        }
        return onGenericMotionEvent(ev);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        event.setClassName(getClass().getName());
        event.setPackageName(getPackageName());
        ViewGroup.LayoutParams params = getWindow().getAttributes();
        boolean isFullScreen = params.width == -1 && params.height == -1;
        event.setFullScreen(isFullScreen);
        CharSequence title = getTitle();
        if (!TextUtils.isEmpty(title)) {
            event.getText().add(title);
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public View onCreatePanelView(int featureId) {
        return null;
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == 0) {
            boolean show = onCreateOptionsMenu(menu);
            return show | this.mFragments.dispatchCreateOptionsMenu(menu, getMenuInflater());
        }
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (featureId == 0) {
            boolean goforit = onPrepareOptionsMenu(menu);
            return goforit | this.mFragments.dispatchPrepareOptionsMenu(menu);
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == 8) {
            initWindowDecorActionBar();
            if (this.mActionBar != null) {
                this.mActionBar.dispatchMenuVisibilityChanged(true);
            } else {
                Log.e(TAG, "Tried to open action bar menu with no action bar");
            }
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        CharSequence titleCondensed = item.getTitleCondensed();
        switch (featureId) {
            case 0:
                if (titleCondensed != null) {
                    EventLog.writeEvent(50000, 0, titleCondensed.toString());
                }
                if (onOptionsItemSelected(item)
                        || this.mFragments.dispatchOptionsItemSelected(item)) {
                    return true;
                }
                if (item.getItemId() != 16908332
                        || this.mActionBar == null
                        || (this.mActionBar.getDisplayOptions() & 4) == 0) {
                    return false;
                }
                if (this.mParent == null) {
                    return onNavigateUp();
                }
                return this.mParent.onNavigateUpFromChild(this);
            case 6:
                if (titleCondensed != null) {
                    EventLog.writeEvent(50000, 1, titleCondensed.toString());
                }
                if (onContextItemSelected(item)) {
                    return true;
                }
                return this.mFragments.dispatchContextItemSelected(item);
            default:
                return false;
        }
    }

    @Override // android.view.Window.Callback
    public void onPanelClosed(int featureId, Menu menu) {
        switch (featureId) {
            case 0:
                this.mFragments.dispatchOptionsMenuClosed(menu);
                onOptionsMenuClosed(menu);
                break;
            case 6:
                onContextMenuClosed(menu);
                break;
            case 8:
                initWindowDecorActionBar();
                this.mActionBar.dispatchMenuVisibilityChanged(false);
                break;
        }
    }

    public void invalidateOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            if (this.mActionBar == null || !this.mActionBar.invalidateOptionsMenu()) {
                this.mWindow.invalidatePanelMenu(0);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.mParent != null) {
            return this.mParent.onCreateOptionsMenu(menu);
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (this.mParent != null) {
            return this.mParent.onPrepareOptionsMenu(menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.mParent != null) {
            return this.mParent.onOptionsItemSelected(item);
        }
        return false;
    }

    public boolean onNavigateUp() {
        Intent upIntent = getParentActivityIntent();
        if (upIntent != null) {
            if (this.mActivityInfo.taskAffinity == null) {
                finish();
                return true;
            }
            if (shouldUpRecreateTask(upIntent)) {
                TaskStackBuilder b = TaskStackBuilder.create(this);
                onCreateNavigateUpTaskStack(b);
                onPrepareNavigateUpTaskStack(b);
                b.startActivities();
                if (this.mResultCode != 0 || this.mResultData != null) {
                    Log.i(TAG, "onNavigateUp only finishing topmost activity to return a result");
                    finish();
                    return true;
                }
                finishAffinity();
                return true;
            }
            navigateUpTo(upIntent);
            return true;
        }
        return false;
    }

    @Deprecated
    public boolean onNavigateUpFromChild(Activity child) {
        return onNavigateUp();
    }

    public void onCreateNavigateUpTaskStack(TaskStackBuilder builder) {
        builder.addParentStack(this);
    }

    public void onPrepareNavigateUpTaskStack(TaskStackBuilder builder) {}

    public void onOptionsMenuClosed(Menu menu) {
        if (this.mParent != null) {
            this.mParent.onOptionsMenuClosed(menu);
        }
    }

    public void openOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            if (this.mActionBar == null || !this.mActionBar.openOptionsMenu()) {
                this.mWindow.openPanel(0, null);
            }
        }
    }

    public void closeOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            if (this.mActionBar == null || !this.mActionBar.closeOptionsMenu()) {
                this.mWindow.closePanel(0);
            }
        }
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(
            ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {}

    public void registerForContextMenu(View view) {
        view.setOnCreateContextMenuListener(this);
    }

    public void unregisterForContextMenu(View view) {
        view.setOnCreateContextMenuListener(null);
    }

    public void openContextMenu(View view) {
        view.showContextMenu();
    }

    public void closeContextMenu() {
        if (this.mWindow.hasFeature(6)) {
            this.mWindow.closePanel(6);
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (this.mParent != null) {
            return this.mParent.onContextItemSelected(item);
        }
        return false;
    }

    public void onContextMenuClosed(Menu menu) {
        if (this.mParent != null) {
            this.mParent.onContextMenuClosed(menu);
        }
    }

    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return null;
    }

    @Deprecated
    protected Dialog onCreateDialog(int id, Bundle args) {
        return onCreateDialog(id);
    }

    @Deprecated
    protected void onPrepareDialog(int id, Dialog dialog) {
        dialog.setOwnerActivity(this);
    }

    @Deprecated
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
        onPrepareDialog(id, dialog);
    }

    @Deprecated
    public final void showDialog(int id) {
        showDialog(id, null);
    }

    @Deprecated
    public final boolean showDialog(int id, Bundle args) {
        if (this.mManagedDialogs == null) {
            this.mManagedDialogs = new SparseArray<>();
        }
        ManagedDialog md = this.mManagedDialogs.get(id);
        if (md == null) {
            md = new ManagedDialog();
            md.mDialog = createDialog(Integer.valueOf(id), null, args);
            if (md.mDialog == null) {
                return false;
            }
            this.mManagedDialogs.put(id, md);
        }
        md.mArgs = args;
        onPrepareDialog(id, md.mDialog, args);
        md.mDialog.show();
        return true;
    }

    @Deprecated
    public final void dismissDialog(int id) {
        if (this.mManagedDialogs == null) {
            throw missingDialog(id);
        }
        ManagedDialog md = this.mManagedDialogs.get(id);
        if (md == null) {
            throw missingDialog(id);
        }
        md.mDialog.dismiss();
    }

    private IllegalArgumentException missingDialog(int id) {
        return new IllegalArgumentException(
                "no dialog with id " + id + " was ever shown via Activity#showDialog");
    }

    @Deprecated
    public final void removeDialog(int id) {
        ManagedDialog md;
        if (this.mManagedDialogs != null && (md = this.mManagedDialogs.get(id)) != null) {
            md.mDialog.dismiss();
            this.mManagedDialogs.remove(id);
        }
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested(SearchEvent searchEvent) {
        this.mSearchEvent = searchEvent;
        boolean result = onSearchRequested();
        this.mSearchEvent = null;
        return result;
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        int uiMode = getResources().getConfiguration().uiMode & 15;
        if (uiMode == 4 || uiMode == 6) {
            return false;
        }
        startSearch(null, false, null, false);
        return true;
    }

    public final SearchEvent getSearchEvent() {
        return this.mSearchEvent;
    }

    public void startSearch(
            String initialQuery,
            boolean selectInitialQuery,
            Bundle appSearchData,
            boolean globalSearch) {
        ensureSearchManager();
        this.mSearchManager.startSearch(
                initialQuery, selectInitialQuery, getComponentName(), appSearchData, globalSearch);
    }

    public void triggerSearch(String query, Bundle appSearchData) {
        ensureSearchManager();
        this.mSearchManager.triggerSearch(query, getComponentName(), appSearchData);
    }

    public void takeKeyEvents(boolean get) {
        getWindow().takeKeyEvents(get);
    }

    public final boolean requestWindowFeature(int featureId) {
        return getWindow().requestFeature(featureId);
    }

    public final void setFeatureDrawableResource(int featureId, int resId) {
        getWindow().setFeatureDrawableResource(featureId, resId);
    }

    public final void setFeatureDrawableUri(int featureId, Uri uri) {
        getWindow().setFeatureDrawableUri(featureId, uri);
    }

    public final void setFeatureDrawable(int featureId, Drawable drawable) {
        getWindow().setFeatureDrawable(featureId, drawable);
    }

    public final void setFeatureDrawableAlpha(int featureId, int alpha) {
        getWindow().setFeatureDrawableAlpha(featureId, alpha);
    }

    public LayoutInflater getLayoutInflater() {
        return getWindow().getLayoutInflater();
    }

    public MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            initWindowDecorActionBar();
            if (this.mActionBar != null) {
                this.mMenuInflater = new MenuInflater(this.mActionBar.getThemedContext(), this);
            } else {
                this.mMenuInflater = new MenuInflater(this);
            }
        }
        return this.mMenuInflater;
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper,
    // android.content.Context
    public void setTheme(int resid) {
        super.setTheme(resid);
        this.mWindow.setTheme(resid);
    }

    @Override // android.view.ContextThemeWrapper
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        int colorPrimary;
        if (this.mParent == null) {
            super.onApplyThemeResource(theme, resid, first);
        } else {
            try {
                theme.setTo(this.mParent.getTheme());
            } catch (Exception e) {
            }
            theme.applyStyle(resid, false);
        }
        TypedArray a = theme.obtainStyledAttributes(R.styleable.ActivityTaskDescription);
        if (this.mTaskDescription.getPrimaryColor() == 0
                && (colorPrimary = a.getColor(1, 0)) != 0
                && Color.alpha(colorPrimary) == 255) {
            this.mTaskDescription.setPrimaryColor(colorPrimary);
        }
        int colorBackground = a.getColor(0, 0);
        if (colorBackground != 0 && Color.alpha(colorBackground) == 255) {
            this.mTaskDescription.setBackgroundColor(colorBackground);
        }
        int colorBackgroundFloating = a.getColor(4, 0);
        if (colorBackgroundFloating != 0 && Color.alpha(colorBackgroundFloating) == 255) {
            this.mTaskDescription.setBackgroundColorFloating(colorBackgroundFloating);
        }
        int statusBarColor = a.getColor(2, 0);
        if (statusBarColor != 0) {
            this.mTaskDescription.setStatusBarColor(statusBarColor);
        }
        int navigationBarColor = a.getColor(3, 0);
        if (navigationBarColor != 0) {
            this.mTaskDescription.setNavigationBarColor(navigationBarColor);
        }
        int targetSdk = getApplicationInfo().targetSdkVersion;
        boolean targetPreQ = targetSdk < 29;
        if (!targetPreQ) {
            this.mTaskDescription.setEnsureStatusBarContrastWhenTransparent(a.getBoolean(5, false));
            this.mTaskDescription.setEnsureNavigationBarContrastWhenTransparent(
                    a.getBoolean(6, true));
        }
        a.recycle();
        if (first
                && this.mTaskDescription.getSystemBarsAppearance() == 0
                && this.mWindow != null
                && this.mWindow.getSystemBarAppearance() != 0) {
            this.mTaskDescription.setSystemBarsAppearance(this.mWindow.getSystemBarAppearance());
        }
        setTaskDescription(this.mTaskDescription);
    }

    public final void requestPermissions(String[] permissions, int requestCode) {
        requestPermissions(permissions, requestCode, getDeviceId());
    }

    public final void requestPermissions(String[] permissions, int requestCode, int deviceId) {
        if (requestCode < 0) {
            throw new IllegalArgumentException("requestCode should be >= 0");
        }
        if (this.mHasCurrentPermissionsRequest) {
            Log.w(TAG, "Can request only one set of permissions at a time");
            onRequestPermissionsResult(requestCode, new String[0], new int[0], deviceId);
            return;
        }
        if (!getAttributionSource().getRenouncedPermissions().isEmpty()) {
            int permissionCount = permissions.length;
            for (int i = 0; i < permissionCount; i++) {
                if (getAttributionSource().getRenouncedPermissions().contains(permissions[i])) {
                    throw new IllegalArgumentException(
                            "Cannot request renounced permission: " + permissions[i]);
                }
            }
        }
        int permissionCount2 = getDeviceId();
        PackageManager packageManager =
                permissionCount2 == deviceId
                        ? getPackageManager()
                        : createDeviceContext(deviceId).getPackageManager();
        Intent intent = packageManager.buildRequestPermissionsIntent(permissions);
        startActivityForResult(REQUEST_PERMISSIONS_WHO_PREFIX, intent, requestCode, null);
        this.mHasCurrentPermissionsRequest = true;
    }

    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {}

    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults, int deviceId) {
        onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean shouldShowRequestPermissionRationale(String permission) {
        return getPackageManager().shouldShowRequestPermissionRationale(permission);
    }

    public boolean shouldShowRequestPermissionRationale(String permission, int deviceId) {
        PackageManager packageManager =
                getDeviceId() == deviceId
                        ? getPackageManager()
                        : createDeviceContext(deviceId).getPackageManager();
        return packageManager.shouldShowRequestPermissionRationale(permission);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode, null);
    }

    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        if (this.mParent == null) {
            Bundle options2 = transferSpringboardActivityOptions(options);
            Instrumentation.ActivityResult ar =
                    this.mInstrumentation.execStartActivity(
                            this,
                            this.mMainThread.getApplicationThread(),
                            this.mToken,
                            this,
                            intent,
                            requestCode,
                            options2);
            if (ar != null) {
                this.mMainThread.sendActivityResult(
                        this.mToken,
                        this.mEmbeddedID,
                        requestCode,
                        ar.getResultCode(),
                        ar.getResultData());
            }
            if (requestCode >= 0) {
                this.mStartedActivity = true;
            }
            cancelInputsAndStartExitTransition(options2);
            return;
        }
        if (options != null) {
            this.mParent.startActivityFromChild(this, intent, requestCode, options);
        } else {
            this.mParent.startActivityFromChild(this, intent, requestCode);
        }
    }

    private void cancelInputsAndStartExitTransition(Bundle options) {
        View decor = this.mWindow != null ? this.mWindow.peekDecorView() : null;
        if (decor != null) {
            decor.cancelPendingInputEvents();
        }
        if (options != null) {
            this.mActivityTransitionState.startExitOutTransition(this, options);
        }
    }

    public boolean isActivityTransitionRunning() {
        return this.mActivityTransitionState.isTransitionRunning();
    }

    private Bundle transferSpringboardActivityOptions(Bundle options) {
        ActivityOptions.SceneTransitionInfo info;
        if (options == null
                && this.mWindow != null
                && !this.mWindow.isActive()
                && (info = getSceneTransitionInfo()) != null) {
            return ActivityOptions.makeBasic().setSceneTransitionInfo(info).toBundle();
        }
        return options;
    }

    @SystemApi
    public void startActivityForResultAsUser(Intent intent, int requestCode, UserHandle user) {
        startActivityForResultAsUser(intent, requestCode, null, user);
    }

    @SystemApi
    public void startActivityForResultAsUser(
            Intent intent, int requestCode, Bundle options, UserHandle user) {
        startActivityForResultAsUser(intent, this.mEmbeddedID, requestCode, options, user);
    }

    @SystemApi
    public void startActivityForResultAsUser(
            Intent intent, String resultWho, int requestCode, Bundle options, UserHandle user) {
        if (this.mParent != null) {
            throw new RuntimeException("Can't be called from a child");
        }
        Bundle options2 = transferSpringboardActivityOptions(options);
        Instrumentation.ActivityResult ar =
                this.mInstrumentation.execStartActivity(
                        this,
                        this.mMainThread.getApplicationThread(),
                        this.mToken,
                        resultWho,
                        intent,
                        requestCode,
                        options2,
                        user);
        if (ar != null) {
            this.mMainThread.sendActivityResult(
                    this.mToken,
                    this.mEmbeddedID,
                    requestCode,
                    ar.getResultCode(),
                    ar.getResultData());
        }
        if (requestCode >= 0) {
            this.mStartedActivity = true;
        }
        cancelInputsAndStartExitTransition(options2);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivityAsUser(Intent intent, UserHandle user) {
        startActivityAsUser(intent, null, user);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivityAsUser(Intent intent, Bundle options, UserHandle user) {
        if (this.mParent != null) {
            throw new RuntimeException("Can't be called from a child");
        }
        Bundle options2 = transferSpringboardActivityOptions(options);
        Instrumentation.ActivityResult ar =
                this.mInstrumentation.execStartActivity(
                        this,
                        this.mMainThread.getApplicationThread(),
                        this.mToken,
                        this.mEmbeddedID,
                        intent,
                        -1,
                        options2,
                        user);
        if (ar != null) {
            this.mMainThread.sendActivityResult(
                    this.mToken, this.mEmbeddedID, -1, ar.getResultCode(), ar.getResultData());
        }
        cancelInputsAndStartExitTransition(options2);
    }

    public void startActivityAsCaller(
            Intent intent, Bundle options, boolean ignoreTargetSecurity, int userId) {
        startActivityAsCaller(intent, options, ignoreTargetSecurity, userId, -1);
    }

    public void startActivityAsCaller(
            Intent intent,
            Bundle options,
            boolean ignoreTargetSecurity,
            int userId,
            int requestCode) {
        if (this.mParent != null) {
            throw new RuntimeException("Can't be called from a child");
        }
        Bundle options2 = transferSpringboardActivityOptions(options);
        Instrumentation.ActivityResult ar =
                this.mInstrumentation.execStartActivityAsCaller(
                        this,
                        this.mMainThread.getApplicationThread(),
                        this.mToken,
                        this,
                        intent,
                        requestCode,
                        options2,
                        ignoreTargetSecurity,
                        userId);
        if (ar != null) {
            this.mMainThread.sendActivityResult(
                    this.mToken,
                    this.mEmbeddedID,
                    requestCode,
                    ar.getResultCode(),
                    ar.getResultData());
        }
        cancelInputsAndStartExitTransition(options2);
    }

    public void startIntentSenderForResult(
            IntentSender intent,
            int requestCode,
            Intent fillInIntent,
            int flagsMask,
            int flagsValues,
            int extraFlags)
            throws IntentSender.SendIntentException {
        startIntentSenderForResult(
                intent,
                requestCode,
                fillInIntent,
                flagsMask,
                flagsValues,
                extraFlags,
                (Bundle) null);
    }

    public void startIntentSenderForResult(
            IntentSender intent,
            String who,
            int requestCode,
            Intent fillInIntent,
            int flagsMask,
            int flagsValues,
            Bundle options)
            throws IntentSender.SendIntentException {
        startIntentSenderForResultInner(
                intent, who, requestCode, fillInIntent, flagsMask, flagsValues, options);
    }

    public void startIntentSenderForResult(
            IntentSender intent,
            int requestCode,
            Intent fillInIntent,
            int flagsMask,
            int flagsValues,
            int extraFlags,
            Bundle options)
            throws IntentSender.SendIntentException {
        if (this.mParent == null) {
            startIntentSenderForResultInner(
                    intent,
                    this.mEmbeddedID,
                    requestCode,
                    fillInIntent,
                    flagsMask,
                    flagsValues,
                    options);
        } else if (options != null) {
            this.mParent.startIntentSenderFromChild(
                    this,
                    intent,
                    requestCode,
                    fillInIntent,
                    flagsMask,
                    flagsValues,
                    extraFlags,
                    options);
        } else {
            this.mParent.startIntentSenderFromChild(
                    this, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
        }
    }

    public void startIntentSenderForResultInner(
            IntentSender intent,
            String who,
            int requestCode,
            Intent fillInIntent,
            int flagsMask,
            int flagsValues,
            Bundle options)
            throws IntentSender.SendIntentException {
        Bundle options2;
        int result;
        try {
            options2 = transferSpringboardActivityOptions(options);
            String resolvedType = null;
            if (fillInIntent != null) {
                try {
                    fillInIntent.migrateExtraStreamToClipData(this);
                    fillInIntent.prepareToLeaveProcess(this);
                    resolvedType = fillInIntent.resolveTypeIfNeeded(getContentResolver());
                } catch (RemoteException e) {
                }
            }
            result =
                    ActivityTaskManager.getService()
                            .startActivityIntentSender(
                                    this.mMainThread.getApplicationThread(),
                                    intent != null ? intent.getTarget() : null,
                                    intent != null ? intent.getWhitelistToken() : null,
                                    fillInIntent,
                                    resolvedType,
                                    this.mToken,
                                    who,
                                    requestCode,
                                    flagsMask,
                                    flagsValues,
                                    options2);
        } catch (RemoteException e2) {
        }
        if (result == -96) {
            throw new IntentSender.SendIntentException();
        }
        Instrumentation.checkStartActivityResult(result, null);
        if (options2 != null) {
            cancelInputsAndStartExitTransition(options2);
        }
        if (requestCode >= 0) {
            this.mStartedActivity = true;
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent) {
        startActivity(intent, null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent, Bundle options) {
        getAutofillClientController().onStartActivity(intent, this.mIntent);
        if (options != null) {
            startActivityForResult(intent, -1, options);
        } else {
            startActivityForResult(intent, -1);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivities(Intent[] intents) {
        startActivities(intents, null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivities(Intent[] intents, Bundle options) {
        this.mInstrumentation.execStartActivities(
                this, this.mMainThread.getApplicationThread(), this.mToken, this, intents, options);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startIntentSender(
            IntentSender intent,
            Intent fillInIntent,
            int flagsMask,
            int flagsValues,
            int extraFlags)
            throws IntentSender.SendIntentException {
        startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startIntentSender(
            IntentSender intent,
            Intent fillInIntent,
            int flagsMask,
            int flagsValues,
            int extraFlags,
            Bundle options)
            throws IntentSender.SendIntentException {
        if (options != null) {
            startIntentSenderForResult(
                    intent, -1, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        } else {
            startIntentSenderForResult(
                    intent, -1, fillInIntent, flagsMask, flagsValues, extraFlags);
        }
    }

    public boolean startActivityIfNeeded(Intent intent, int requestCode) {
        return startActivityIfNeeded(intent, requestCode, null);
    }

    public boolean startActivityIfNeeded(Intent intent, int requestCode, Bundle options) {
        int result;
        if (Instrumentation.DEBUG_START_ACTIVITY) {
            Log.d(
                    "Instrumentation",
                    "startActivity: intent="
                            + intent
                            + " requestCode="
                            + requestCode
                            + " options="
                            + options,
                    new Throwable());
        }
        if (this.mParent == null) {
            try {
                Uri referrer = onProvideReferrer();
                if (referrer != null) {
                    intent.putExtra(Intent.EXTRA_REFERRER, referrer);
                }
                intent.migrateExtraStreamToClipData(this);
                intent.prepareToLeaveProcess(this);
                int result2 =
                        ActivityTaskManager.getService()
                                .startActivity(
                                        this.mMainThread.getApplicationThread(),
                                        getOpPackageName(),
                                        getAttributionTag(),
                                        intent,
                                        intent.resolveTypeIfNeeded(getContentResolver()),
                                        this.mToken,
                                        this.mEmbeddedID,
                                        requestCode,
                                        1,
                                        null,
                                        options);
                result = result2;
            } catch (RemoteException e) {
                result = 1;
            }
            Instrumentation.checkStartActivityResult(result, intent);
            if (requestCode >= 0) {
                this.mStartedActivity = true;
            }
            return result != 1;
        }
        throw new UnsupportedOperationException(
                "startActivityIfNeeded can only be called from a top-level activity");
    }

    public boolean startNextMatchingActivity(Intent intent) {
        return startNextMatchingActivity(intent, null);
    }

    public boolean startNextMatchingActivity(Intent intent, Bundle options) {
        if (this.mParent == null) {
            try {
                intent.migrateExtraStreamToClipData(this);
                intent.prepareToLeaveProcess(this);
                return ActivityTaskManager.getService()
                        .startNextMatchingActivity(this.mToken, intent, options);
            } catch (RemoteException e) {
                return false;
            }
        }
        throw new UnsupportedOperationException(
                "startNextMatchingActivity can only be called from a top-level activity");
    }

    @Deprecated
    public void startActivityFromChild(Activity child, Intent intent, int requestCode) {
        startActivityFromChild(child, intent, requestCode, null);
    }

    @Deprecated
    public void startActivityFromChild(
            Activity child, Intent intent, int requestCode, Bundle options) {
        Bundle options2 = transferSpringboardActivityOptions(options);
        Instrumentation.ActivityResult ar =
                this.mInstrumentation.execStartActivity(
                        this,
                        this.mMainThread.getApplicationThread(),
                        this.mToken,
                        child,
                        intent,
                        requestCode,
                        options2);
        if (ar != null) {
            this.mMainThread.sendActivityResult(
                    this.mToken,
                    child.mEmbeddedID,
                    requestCode,
                    ar.getResultCode(),
                    ar.getResultData());
        }
        cancelInputsAndStartExitTransition(options2);
    }

    @Deprecated
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        startActivityFromFragment(fragment, intent, requestCode, null);
    }

    @Deprecated
    public void startActivityFromFragment(
            Fragment fragment, Intent intent, int requestCode, Bundle options) {
        startActivityForResult(fragment.mWho, intent, requestCode, options);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startActivityAsUserFromFragment(
            Fragment fragment, Intent intent, int requestCode, Bundle options, UserHandle user) {
        startActivityForResultAsUser(intent, fragment.mWho, requestCode, options, user);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivityForResult(String who, Intent intent, int requestCode, Bundle options) {
        Uri referrer = onProvideReferrer();
        if (referrer != null) {
            intent.putExtra(Intent.EXTRA_REFERRER, referrer);
        }
        Bundle options2 = transferSpringboardActivityOptions(options);
        Instrumentation.ActivityResult ar =
                this.mInstrumentation.execStartActivity(
                        this,
                        this.mMainThread.getApplicationThread(),
                        this.mToken,
                        who,
                        intent,
                        requestCode,
                        options2);
        if (ar != null) {
            this.mMainThread.sendActivityResult(
                    this.mToken, who, requestCode, ar.getResultCode(), ar.getResultData());
        }
        cancelInputsAndStartExitTransition(options2);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean canStartActivityForResult() {
        return true;
    }

    @Deprecated
    public void startIntentSenderFromChild(
            Activity child,
            IntentSender intent,
            int requestCode,
            Intent fillInIntent,
            int flagsMask,
            int flagsValues,
            int extraFlags)
            throws IntentSender.SendIntentException {
        startIntentSenderFromChild(
                child, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, null);
    }

    @Deprecated
    public void startIntentSenderFromChild(
            Activity child,
            IntentSender intent,
            int requestCode,
            Intent fillInIntent,
            int flagsMask,
            int flagsValues,
            int extraFlags,
            Bundle options)
            throws IntentSender.SendIntentException {
        startIntentSenderForResultInner(
                intent,
                child.mEmbeddedID,
                requestCode,
                fillInIntent,
                flagsMask,
                flagsValues,
                options);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startIntentSenderFromFragment(
            Fragment fragment,
            IntentSender intent,
            int requestCode,
            Intent fillInIntent,
            int flagsMask,
            int flagsValues,
            Bundle options)
            throws IntentSender.SendIntentException {
        startIntentSenderForResultInner(
                intent, fragment.mWho, requestCode, fillInIntent, flagsMask, flagsValues, options);
    }

    public void overrideActivityTransition(int overrideType, int enterAnim, int exitAnim) {
        overrideActivityTransition(overrideType, enterAnim, exitAnim, 0);
    }

    public void overrideActivityTransition(
            int overrideType, int enterAnim, int exitAnim, int backgroundColor) {
        if (overrideType != 0 && overrideType != 1) {
            throw new IllegalArgumentException("Override type must be either open or close");
        }
        ActivityClient.getInstance()
                .overrideActivityTransition(
                        this.mToken, overrideType == 0, enterAnim, exitAnim, backgroundColor);
    }

    public void clearOverrideActivityTransition(int overrideType) {
        if (overrideType != 0 && overrideType != 1) {
            throw new IllegalArgumentException("Override type must be either open or close");
        }
        ActivityClient.getInstance()
                .clearOverrideActivityTransition(this.mToken, overrideType == 0);
    }

    public void semAdjustPopOverOptions(
            int[] widthDp, int[] heightDp, Point[] marginDp, int[] position) {
        if (widthDp == null || widthDp.length == 2) {
            if (heightDp == null || heightDp.length == 2) {
                if (marginDp == null || marginDp.length == 2) {
                    if (position != null && position.length != 2) {
                        return;
                    }
                    ActivityClient.getInstance()
                            .adjustPopOverOptions(
                                    this.mToken, widthDp, heightDp, marginDp, position);
                }
            }
        }
    }

    @Deprecated
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        overridePendingTransition(enterAnim, exitAnim, 0);
    }

    public void semOverridePendingTransition(int enterAnim, int exitAnim) {
        ActivityClient.getInstance()
                .overridePendingTaskTransition(this.mToken, getPackageName(), enterAnim, exitAnim);
    }

    @Deprecated
    public void overridePendingTransition(int enterAnim, int exitAnim, int backgroundColor) {
        ActivityClient.getInstance()
                .overridePendingTransition(
                        this.mToken, getPackageName(), enterAnim, exitAnim, backgroundColor);
    }

    public final void setResult(int resultCode) {
        synchronized (this) {
            this.mResultCode = resultCode;
            this.mResultData = null;
        }
    }

    public final void setForceSendResultForMediaProjection() {
        ActivityClient.getInstance().setForceSendResultForMediaProjection(this.mToken);
    }

    public final void setResult(int resultCode, Intent data) {
        synchronized (this) {
            this.mResultCode = resultCode;
            this.mResultData = data;
        }
    }

    public Uri getReferrer() {
        if (CoreRune.SYSFW_APP_SPEG && SPEG_PACKAGE_NAME.equals(this.mReferrer)) {
            Intent launcher = new Intent(Intent.ACTION_MAIN);
            launcher.addCategory(Intent.CATEGORY_HOME);
            ResolveInfo resolveInfo = getPackageManager().resolveActivity(launcher, 65536);
            if (resolveInfo == null) {
                Log.w("SPEG", "resolveInfo is null");
                return null;
            }
            Log.d("SPEG", "Pretend to be the default launcher");
            return new Uri.Builder()
                    .scheme("android-app")
                    .authority(resolveInfo.activityInfo.packageName)
                    .build();
        }
        Intent intent = getIntent();
        if (intent != null) {
            try {
                Uri referrer = (Uri) intent.getParcelableExtra(Intent.EXTRA_REFERRER, Uri.class);
                if (referrer != null) {
                    return referrer;
                }
                String referrerName = intent.getStringExtra(Intent.EXTRA_REFERRER_NAME);
                if (referrerName != null) {
                    return Uri.parse(referrerName);
                }
            } catch (BadParcelableException e) {
                Log.w(
                        TAG,
                        "Cannot read referrer from intent; intent extras contain unknown custom"
                                + " Parcelable objects");
            }
        }
        if (this.mReferrer != null) {
            return new Uri.Builder().scheme("android-app").authority(this.mReferrer).build();
        }
        return null;
    }

    public Uri onProvideReferrer() {
        return null;
    }

    public String getCallingPackage() {
        return ActivityClient.getInstance().getCallingPackage(this.mToken);
    }

    public ComponentName getCallingActivity() {
        return ActivityClient.getInstance().getCallingActivity(this.mToken);
    }

    public int getLaunchedFromUid() {
        return ActivityClient.getInstance().getLaunchedFromUid(getActivityToken());
    }

    public String getLaunchedFromPackage() {
        return ActivityClient.getInstance().getLaunchedFromPackage(getActivityToken());
    }

    public ComponentCaller getInitialCaller() {
        return this.mInitialCaller;
    }

    public ComponentCaller getCurrentCaller() {
        if (this.mCurrentCaller == null) {
            throw new IllegalStateException(
                    "The caller is null because #getCurrentCaller should be called within"
                            + " #onNewIntent or #onActivityResult methods");
        }
        return this.mCurrentCaller;
    }

    public void setVisible(boolean visible) {
        if (this.mVisibleFromClient != visible) {
            this.mVisibleFromClient = visible;
            if (this.mVisibleFromServer) {
                if (!visible) {
                    this.mDecor.setVisibility(4);
                } else {
                    makeVisible();
                }
            }
        }
    }

    void makeVisible() {
        if (!this.mWindowAdded) {
            ViewManager wm = getWindowManager();
            wm.addView(this.mDecor, getWindow().getAttributes());
            this.mWindowAdded = true;
        }
        this.mDecor.setVisibility(0);
    }

    public boolean isFinishing() {
        return this.mFinished;
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public boolean isChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public void recreate() {
        if (this.mParent != null) {
            throw new IllegalStateException("Can only be called on top-level activity");
        }
        if (Looper.myLooper() != this.mMainThread.getLooper()) {
            throw new IllegalStateException("Must be called from main thread");
        }
        this.mMainThread.scheduleRelaunchActivity(this.mToken);
    }

    private void finish(int finishTask) {
        int resultCode;
        Intent resultData;
        if (this.mParent == null) {
            synchronized (this) {
                resultCode = this.mResultCode;
                resultData = this.mResultData;
            }
            if (resultData != null) {
                resultData.prepareToLeaveProcess(this);
            }
            if (ActivityClient.getInstance()
                    .finishActivity(this.mToken, resultCode, resultData, finishTask)) {
                this.mFinished = true;
            }
        } else {
            this.mParent.finishFromChild(this);
        }
        getAutofillClientController().onActivityFinish(this.mIntent);
    }

    public void finish() {
        finish(0);
    }

    public void finishAffinity() {
        if (this.mParent != null) {
            throw new IllegalStateException("Can not be called from an embedded activity");
        }
        if (this.mResultCode != 0 || this.mResultData != null) {
            throw new IllegalStateException("Can not be called to deliver a result");
        }
        if (ActivityClient.getInstance().finishActivityAffinity(this.mToken)) {
            this.mFinished = true;
        }
    }

    @Deprecated
    public void finishFromChild(Activity child) {
        finish();
    }

    public void finishAfterTransition() {
        if (!this.mActivityTransitionState.startExitBackTransition(this)) {
            finish();
        }
    }

    public void finishActivity(int requestCode) {
        if (this.mParent == null) {
            ActivityClient.getInstance()
                    .finishSubActivity(this.mToken, this.mEmbeddedID, requestCode);
        } else {
            this.mParent.finishActivityFromChild(this, requestCode);
        }
    }

    @Deprecated
    public void finishActivityFromChild(Activity child, int requestCode) {
        ActivityClient.getInstance().finishSubActivity(this.mToken, child.mEmbeddedID, requestCode);
    }

    public void finishAndRemoveTask() {
        finish(1);
    }

    public boolean releaseInstance() {
        return ActivityClient.getInstance().releaseActivityInstance(this.mToken);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {}

    public void onActivityResult(
            int requestCode, int resultCode, Intent data, ComponentCaller caller) {
        onActivityResult(requestCode, resultCode, data);
    }

    public void onActivityReenter(int resultCode, Intent data) {}

    public PendingIntent createPendingResult(int requestCode, Intent data, int flags) {
        String packageName = getPackageName();
        try {
            data.prepareToLeaveProcess(this);
            IIntentSender target =
                    ActivityManager.getService()
                            .getIntentSenderWithFeature(
                                    3,
                                    packageName,
                                    getAttributionTag(),
                                    this.mParent == null ? this.mToken : this.mParent.mToken,
                                    this.mEmbeddedID,
                                    requestCode,
                                    new Intent[] {data},
                                    null,
                                    flags,
                                    null,
                                    getUserId());
            if (target != null) {
                return new PendingIntent(target);
            }
            return null;
        } catch (RemoteException e) {
            return null;
        }
    }

    public void setRequestedOrientation(int requestedOrientation) {
        if (requestedOrientation == this.mLastRequestedOrientation) {
            return;
        }
        if (this.mParent == null) {
            ActivityClient.getInstance().setRequestedOrientation(this.mToken, requestedOrientation);
        } else {
            this.mParent.setRequestedOrientation(requestedOrientation);
        }
        this.mLastRequestedOrientation = requestedOrientation;
    }

    public int getRequestedOrientation() {
        if (this.mLastRequestedOrientation != -2) {
            return this.mLastRequestedOrientation;
        }
        if (this.mParent == null) {
            return ActivityClient.getInstance().getRequestedOrientation(this.mToken);
        }
        return this.mParent.getRequestedOrientation();
    }

    public int getTaskId() {
        return ActivityClient.getInstance().getTaskForActivity(this.mToken, false);
    }

    public boolean isTaskRoot() {
        return this.mWindowControllerCallback.isTaskRoot();
    }

    public boolean moveTaskToBack(boolean nonRoot) {
        return ActivityClient.getInstance().moveActivityTaskToBack(this.mToken, nonRoot);
    }

    public String getLocalClassName() {
        String pkg = getPackageName();
        String cls = this.mComponent.getClassName();
        int packageLen = pkg.length();
        if (!cls.startsWith(pkg) || cls.length() <= packageLen || cls.charAt(packageLen) != '.') {
            return cls;
        }
        return cls.substring(packageLen + 1);
    }

    public ComponentName getComponentName() {
        return this.mComponent;
    }

    @Override // android.view.contentcapture.ContentCaptureManager.ContentCaptureClient
    public final ComponentName contentCaptureClientGetComponentName() {
        return getComponentName();
    }

    public SharedPreferences getPreferences(int mode) {
        return getSharedPreferences(getLocalClassName(), mode);
    }

    public boolean isLaunchedFromBubble() {
        return this.mLaunchedFromBubble;
    }

    private void ensureSearchManager() {
        if (this.mSearchManager != null) {
            return;
        }
        try {
            this.mSearchManager = new SearchManager(this, null);
        } catch (ServiceManager.ServiceNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper,
    // android.content.Context
    public Object getSystemService(String name) {
        if (getBaseContext() == null) {
            throw new IllegalStateException(
                    "System services not available to Activities before onCreate()");
        }
        if (Context.WINDOW_SERVICE.equals(name)) {
            return this.mWindowManager;
        }
        if ("search".equals(name)) {
            ensureSearchManager();
            return this.mSearchManager;
        }
        return super.getSystemService(name);
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
        onTitleChanged(title, this.mTitleColor);
        if (this.mParent != null) {
            this.mParent.onChildTitleChanged(this, title);
        }
    }

    public void setTitle(int titleId) {
        setTitle(getText(titleId));
    }

    @Deprecated
    public void setTitleColor(int textColor) {
        this.mTitleColor = textColor;
        onTitleChanged(this.mTitle, textColor);
    }

    public final CharSequence getTitle() {
        return this.mTitle;
    }

    public final int getTitleColor() {
        return this.mTitleColor;
    }

    protected void onTitleChanged(CharSequence title, int color) {
        if (this.mTitleReady) {
            Window win = getWindow();
            if (win != null) {
                win.setTitle(title);
                if (color != 0) {
                    win.setTitleColor(color);
                }
            }
            if (this.mActionBar != null) {
                this.mActionBar.setWindowTitle(title);
            }
        }
    }

    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {}

    public void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
        if (this.mTaskDescription != taskDescription) {
            this.mTaskDescription.copyFromPreserveHiddenFields(taskDescription);
            if (taskDescription.getIconFilename() == null && taskDescription.getIcon() != null) {
                int size = ActivityManager.getLauncherLargeIconSizeInner(this);
                Bitmap icon =
                        Bitmap.createScaledBitmap(taskDescription.getIcon(), size, size, true);
                this.mTaskDescription.setIcon(Icon.createWithBitmap(icon));
            }
        }
        if (this.mLastTaskDescriptionHashCode == this.mTaskDescription.hashCode()) {
            return;
        }
        this.mLastTaskDescriptionHashCode = this.mTaskDescription.hashCode();
        ActivityClient.getInstance().setTaskDescription(this.mToken, this.mTaskDescription);
    }

    @Deprecated
    public final void setProgressBarVisibility(boolean visible) {
        getWindow().setFeatureInt(2, visible ? -1 : -2);
    }

    @Deprecated
    public final void setProgressBarIndeterminateVisibility(boolean visible) {
        getWindow().setFeatureInt(5, visible ? -1 : -2);
    }

    @Deprecated
    public final void setProgressBarIndeterminate(boolean indeterminate) {
        getWindow().setFeatureInt(2, indeterminate ? -3 : -4);
    }

    @Deprecated
    public final void setProgress(int progress) {
        getWindow().setFeatureInt(2, progress + 0);
    }

    @Deprecated
    public final void setSecondaryProgress(int secondaryProgress) {
        getWindow().setFeatureInt(2, secondaryProgress + 20000);
    }

    public final void setVolumeControlStream(int streamType) {
        getWindow().setVolumeControlStream(streamType);
    }

    public final int getVolumeControlStream() {
        return getWindow().getVolumeControlStream();
    }

    public final void setMediaController(MediaController controller) {
        getWindow().setMediaController(controller);
    }

    public final MediaController getMediaController() {
        return getWindow().getMediaController();
    }

    public final void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != this.mUiThread) {
            this.mHandler.post(action);
        } else {
            action.run();
        }
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if (!"fragment".equals(name)) {
            return onCreateView(name, context, attrs);
        }
        return this.mFragments.onCreateView(parent, name, context, attrs);
    }

    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        dumpInner(prefix, fd, writer, args);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public final boolean addDumpable(Dumpable dumpable) {
        if (this.mDumpableContainer == null) {
            this.mDumpableContainer = new DumpableContainerImpl();
        }
        return this.mDumpableContainer.addDumpable(dumpable);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void dumpInternal(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        char c;
        if (this.mAutofillClientController != null) {
            addDumpable(this.mAutofillClientController);
        }
        if (this.mUiTranslationController != null) {
            addDumpable(this.mUiTranslationController);
        }
        if (this.mContentCaptureManager != null) {
            this.mContentCaptureManager.addDumpable(this);
        }
        boolean dumpInternalState = true;
        String arg = null;
        if (args != null && args.length > 0) {
            arg = args[0];
            boolean isSpecialCase = true;
            switch (arg.hashCode()) {
                case -645125871:
                    if (arg.equals(DUMP_ARG_TRANSLATION)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 100470631:
                    if (arg.equals(DUMP_ARG_DUMP_DUMPABLE)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 472614934:
                    if (arg.equals(DUMP_ARG_LIST_DUMPABLES)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1159329357:
                    if (arg.equals(DUMP_ARG_CONTENT_CAPTURE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1455016274:
                    if (arg.equals(DUMP_ARG_AUTOFILL)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    dumpLegacyDumpable(prefix, writer, arg, AutofillClientController.DUMPABLE_NAME);
                    return;
                case 1:
                    dumpLegacyDumpable(prefix, writer, arg, ContentCaptureManager.DUMPABLE_NAME);
                    return;
                case 2:
                    dumpLegacyDumpable(prefix, writer, arg, UiTranslationController.DUMPABLE_NAME);
                    return;
                case 3:
                    if (this.mDumpableContainer == null) {
                        writer.print(prefix);
                        writer.println("No dumpables");
                        return;
                    } else {
                        this.mDumpableContainer.listDumpables(prefix, writer);
                        return;
                    }
                case 4:
                    if (args.length == 1) {
                        writer.print(DUMP_ARG_DUMP_DUMPABLE);
                        writer.println(" requires the dumpable name");
                        break;
                    } else if (this.mDumpableContainer == null) {
                        writer.println("no dumpables");
                        break;
                    } else {
                        String[] prunedArgs = new String[args.length - 2];
                        System.arraycopy(args, 2, prunedArgs, 0, prunedArgs.length);
                        this.mDumpableContainer.dumpOneDumpable(
                                prefix, writer, args[1], prunedArgs);
                        break;
                    }
                default:
                    isSpecialCase = false;
                    break;
            }
            if (isSpecialCase) {
                dumpInternalState = !CompatChanges.isChangeEnabled(DUMP_IGNORES_SPECIAL_ARGS);
            }
        }
        if (dumpInternalState) {
            dump(prefix, fd, writer, args);
        } else {
            Log.i(TAG, "Not calling dump() on " + this + " because of special argument " + arg);
        }
    }

    void dumpInner(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        String innerPrefix = prefix + "  ";
        writer.print(prefix);
        writer.print("Local Activity ");
        writer.print(Integer.toHexString(System.identityHashCode(this)));
        writer.println(" State:");
        writer.print(innerPrefix);
        writer.print("mResumed=");
        writer.print(this.mResumed);
        writer.print(" mStopped=");
        writer.print(this.mStopped);
        writer.print(" mFinished=");
        writer.println(this.mFinished);
        writer.print(innerPrefix);
        writer.print("mIsInMultiWindowMode=");
        writer.print(this.mIsInMultiWindowMode);
        writer.print(" mIsInPictureInPictureMode=");
        writer.println(this.mIsInPictureInPictureMode);
        writer.print(innerPrefix);
        writer.print("mChangingConfigurations=");
        writer.println(this.mChangingConfigurations);
        writer.print(innerPrefix);
        writer.print("mCurrentConfig=");
        writer.println(this.mCurrentConfig);
        this.mFragments.dumpLoaders(innerPrefix, fd, writer, args);
        this.mFragments.getFragmentManager().dump(innerPrefix, fd, writer, args);
        if (this.mVoiceInteractor != null) {
            this.mVoiceInteractor.dump(innerPrefix, fd, writer, args);
        }
        if (getWindow() instanceof PhoneWindow) {
            ((PhoneWindow) getWindow()).dumpColors(prefix, fd, writer, args);
        }
        if (getWindow() != null
                && getWindow().peekDecorView() != null
                && getWindow().peekDecorView().getViewRootImpl() != null) {
            getWindow().peekDecorView().getViewRootImpl().dump(prefix, writer);
        }
        this.mHandler.getLooper().dump(new PrintWriterPrinter(writer), prefix);
        ResourcesManager.getInstance().dump(prefix, writer);
        if (this.mDumpableContainer != null) {
            this.mDumpableContainer.dumpAllDumpables(prefix, writer, args);
        }
    }

    private void dumpLegacyDumpable(
            String prefix, PrintWriter writer, String legacyOption, String dumpableName) {
        writer.printf(
                "%s%s option deprecated. Use %s %s instead\n",
                prefix, legacyOption, DUMP_ARG_DUMP_DUMPABLE, dumpableName);
    }

    public boolean isImmersive() {
        return ActivityClient.getInstance().isImmersive(this.mToken);
    }

    final boolean isTopOfTask() {
        if (this.mToken == null || this.mWindow == null) {
            return false;
        }
        return ActivityClient.getInstance().isTopOfTask(getActivityToken());
    }

    public boolean setTranslucent(boolean translucent) {
        if (translucent) {
            return convertToTranslucent(null, null);
        }
        return convertFromTranslucentInternal();
    }

    @SystemApi
    public void convertFromTranslucent() {
        convertFromTranslucentInternal();
    }

    private boolean convertFromTranslucentInternal() {
        this.mTranslucentCallback = null;
        if (ActivityClient.getInstance().convertFromTranslucent(this.mToken)) {
            WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, true);
            return true;
        }
        return false;
    }

    public void semConvertFromTranslucent(boolean skipSetWindowOpaque) {
        Log.d(
                TAG,
                "semConvertFromTranslucent, activity=" + this + ", caller=" + Debug.getCallers(3));
        this.mTranslucentCallback = null;
        if (ActivityClient.getInstance().convertFromTranslucent(this.mToken, skipSetWindowOpaque)
                && !skipSetWindowOpaque) {
            WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, true);
        }
    }

    @SystemApi
    public boolean convertToTranslucent(
            TranslucentConversionListener callback, ActivityOptions options) {
        this.mTranslucentCallback = callback;
        this.mChangeCanvasToTranslucent =
                ActivityClient.getInstance()
                        .convertToTranslucent(
                                this.mToken, options == null ? null : options.toBundle());
        WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, false);
        if (!this.mChangeCanvasToTranslucent && this.mTranslucentCallback != null) {
            this.mTranslucentCallback.onTranslucentConversionComplete(true);
        }
        return this.mChangeCanvasToTranslucent;
    }

    public boolean semConvertToTranslucent(final SemTranslucentConversionListener callback) {
        Log.d(TAG, "semConvertToTranslucent, activity=" + this + ", caller=" + Debug.getCallers(3));
        return convertToTranslucent(
                new TranslucentConversionListener() { // from class: android.app.Activity.2
                    @Override // android.app.Activity.TranslucentConversionListener
                    public void onTranslucentConversionComplete(boolean drawComplete) {
                        if (callback != null) {
                            callback.onTranslucentConversionCompleted(drawComplete);
                        }
                    }
                },
                null);
    }

    void onTranslucentConversionComplete(boolean drawComplete) {
        if (this.mTranslucentCallback != null) {
            this.mTranslucentCallback.onTranslucentConversionComplete(drawComplete);
            this.mTranslucentCallback = null;
        }
        if (this.mChangeCanvasToTranslucent) {
            WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, false);
        }
    }

    public void onNewSceneTransitionInfo(ActivityOptions.SceneTransitionInfo info) {
        this.mActivityTransitionState.setEnterSceneTransitionInfo(this, info);
        if (!this.mStopped) {
            this.mActivityTransitionState.enterReady(this);
        }
    }

    ActivityOptions.SceneTransitionInfo getSceneTransitionInfo() {
        ActivityOptions.SceneTransitionInfo sceneTransitionInfo = this.mSceneTransitionInfo;
        this.mSceneTransitionInfo = null;
        return sceneTransitionInfo;
    }

    @Deprecated
    public boolean requestVisibleBehind(boolean visible) {
        return false;
    }

    @Deprecated
    public void onVisibleBehindCanceled() {
        this.mCalled = true;
    }

    @SystemApi
    @Deprecated
    public boolean isBackgroundVisibleBehind() {
        return false;
    }

    @SystemApi
    @Deprecated
    public void onBackgroundVisibleBehindChanged(boolean visible) {}

    public void onEnterAnimationComplete() {}

    public void dispatchEnterAnimationComplete() {
        this.mEnterAnimationComplete = true;
        this.mInstrumentation.onEnterAnimationComplete();
        onEnterAnimationComplete();
        if (getWindow() != null && getWindow().getDecorView() != null) {
            View decorView = getWindow().getDecorView();
            decorView.getViewTreeObserver().dispatchOnEnterAnimationComplete();
        }
    }

    public void setImmersive(boolean i) {
        ActivityClient.getInstance().setImmersive(this.mToken, i);
    }

    public void setVrModeEnabled(boolean enabled, ComponentName requestedComponent)
            throws PackageManager.NameNotFoundException {
        if (ActivityClient.getInstance().setVrMode(this.mToken, enabled, requestedComponent) != 0) {
            throw new PackageManager.NameNotFoundException(requestedComponent.flattenToString());
        }
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return this.mWindow.getDecorView().startActionMode(callback);
    }

    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        return this.mWindow.getDecorView().startActionMode(callback, type);
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        if (this.mActionModeTypeStarting == 0) {
            initWindowDecorActionBar();
            if (this.mActionBar != null) {
                return this.mActionBar.startActionMode(callback);
            }
            return null;
        }
        return null;
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        try {
            this.mActionModeTypeStarting = type;
            return onWindowStartingActionMode(callback);
        } finally {
            this.mActionModeTypeStarting = 0;
        }
    }

    @Override // android.view.Window.Callback
    public void onActionModeStarted(ActionMode mode) {}

    @Override // android.view.Window.Callback
    public void onActionModeFinished(ActionMode mode) {}

    public boolean shouldUpRecreateTask(Intent targetIntent) {
        try {
            PackageManager pm = getPackageManager();
            ComponentName cn = targetIntent.getComponent();
            if (cn == null) {
                cn = targetIntent.resolveActivity(pm);
            }
            ActivityInfo info = pm.getActivityInfo(cn, 0);
            if (info.taskAffinity == null) {
                return false;
            }
            return ActivityClient.getInstance()
                    .shouldUpRecreateTask(this.mToken, info.taskAffinity);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public boolean navigateUpTo(Intent upIntent) {
        Intent upIntent2;
        int resultCode;
        Intent resultData;
        if (this.mParent == null) {
            if (upIntent.getComponent() == null) {
                ComponentName destInfo = upIntent.resolveActivity(getPackageManager());
                if (destInfo == null) {
                    return false;
                }
                Intent upIntent3 = new Intent(upIntent);
                upIntent3.setComponent(destInfo);
                upIntent2 = upIntent3;
            } else {
                upIntent2 = upIntent;
            }
            synchronized (this) {
                resultCode = this.mResultCode;
                resultData = this.mResultData;
            }
            if (resultData != null) {
                resultData.prepareToLeaveProcess(this);
            }
            upIntent2.prepareToLeaveProcess(this);
            String resolvedType = upIntent2.resolveTypeIfNeeded(getContentResolver());
            return ActivityClient.getInstance()
                    .navigateUpTo(this.mToken, upIntent2, resolvedType, resultCode, resultData);
        }
        return this.mParent.navigateUpToFromChild(this, upIntent);
    }

    @Deprecated
    public boolean navigateUpToFromChild(Activity child, Intent upIntent) {
        return navigateUpTo(upIntent);
    }

    public Intent getParentActivityIntent() {
        String parentName = this.mActivityInfo.parentActivityName;
        if (TextUtils.isEmpty(parentName)) {
            return null;
        }
        ComponentName target = new ComponentName(this, parentName);
        try {
            ActivityInfo parentInfo = getPackageManager().getActivityInfo(target, 0);
            String parentActivity = parentInfo.parentActivityName;
            if (parentActivity == null) {
                Intent parentIntent = Intent.makeMainActivity(target);
                return parentIntent;
            }
            Intent parentIntent2 = new Intent().setComponent(target);
            return parentIntent2;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(
                    TAG,
                    "getParentActivityIntent: bad parentActivityName '"
                            + parentName
                            + "' in manifest");
            return null;
        }
    }

    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        if (callback == null) {
            callback = SharedElementCallback.NULL_CALLBACK;
        }
        this.mEnterTransitionListener = callback;
    }

    public void setExitSharedElementCallback(SharedElementCallback callback) {
        if (callback == null) {
            callback = SharedElementCallback.NULL_CALLBACK;
        }
        this.mExitTransitionListener = callback;
    }

    public void postponeEnterTransition() {
        this.mActivityTransitionState.postponeEnterTransition();
    }

    public void startPostponedEnterTransition() {
        this.mActivityTransitionState.startPostponedEnterTransition();
    }

    public DragAndDropPermissions requestDragAndDropPermissions(DragEvent event) {
        DragAndDropPermissions dragAndDropPermissions = DragAndDropPermissions.obtain(event);
        if (dragAndDropPermissions != null && dragAndDropPermissions.take(getActivityToken())) {
            return dragAndDropPermissions;
        }
        return null;
    }

    final void setParent(Activity parent) {
        this.mParent = parent;
    }

    final void attach(
            Context context,
            ActivityThread aThread,
            Instrumentation instr,
            IBinder token,
            int ident,
            Application application,
            Intent intent,
            ActivityInfo info,
            CharSequence title,
            Activity parent,
            String id,
            NonConfigurationInstances lastNonConfigurationInstances,
            Configuration config,
            String referrer,
            IVoiceInteractor voiceInteractor,
            Window window,
            ViewRootImpl.ActivityConfigCallback activityConfigCallback,
            IBinder assistToken,
            IBinder shareableActivityToken) {
        attach(
                context,
                aThread,
                instr,
                token,
                ident,
                application,
                intent,
                info,
                title,
                parent,
                id,
                lastNonConfigurationInstances,
                config,
                referrer,
                voiceInteractor,
                window,
                activityConfigCallback,
                assistToken,
                shareableActivityToken,
                null);
    }

    final void attach(
            Context context,
            ActivityThread aThread,
            Instrumentation instr,
            IBinder token,
            int ident,
            Application application,
            Intent intent,
            ActivityInfo info,
            CharSequence title,
            Activity parent,
            String id,
            NonConfigurationInstances lastNonConfigurationInstances,
            Configuration config,
            String referrer,
            IVoiceInteractor voiceInteractor,
            Window window,
            ViewRootImpl.ActivityConfigCallback activityConfigCallback,
            IBinder assistToken,
            IBinder shareableActivityToken,
            IBinder initialCallerInfoAccessToken) {
        String referrer2;
        if (com.android.sdksandbox.flags.Flags.sandboxActivitySdkBasedContext()) {
            this.mIntent = intent;
        }
        attachBaseContext(context);
        this.mFragments.attachHost(null);
        this.mActivityInfo = info;
        this.mWindow = new PhoneWindow(this, window, activityConfigCallback);
        if (CoreRune.SYSFW_APP_SPEG) {
            String parentRef = this.mParent != null ? this.mParent.mReferrer : null;
            referrer2 = referrer;
            if (SPEG_PACKAGE_NAME.equals(referrer2) || SPEG_PACKAGE_NAME.equals(parentRef)) {
                Slog.d("SPEG", "Activity launched");
                referrer2 = SPEG_PACKAGE_NAME;
            }
        } else {
            referrer2 = referrer;
        }
        this.mWindow.setWindowControllerCallback(this.mWindowControllerCallback);
        this.mWindow.setCallback(this);
        this.mWindow.setOnWindowDismissedCallback(this);
        this.mWindow.getLayoutInflater().setPrivateFactory(this);
        if (info.softInputMode != 0) {
            this.mWindow.setSoftInputMode(info.softInputMode);
        }
        if (info.uiOptions != 0) {
            this.mWindow.setUiOptions(info.uiOptions);
        }
        this.mUiThread = Thread.currentThread();
        this.mMainThread = aThread;
        this.mInstrumentation = instr;
        this.mToken = token;
        this.mAssistToken = assistToken;
        this.mShareableActivityToken = shareableActivityToken;
        this.mIdent = ident;
        this.mApplication = application;
        this.mIntent = intent;
        this.mReferrer = referrer2;
        this.mComponent = intent.getComponent();
        this.mTitle = title;
        this.mParent = parent;
        this.mEmbeddedID = id;
        this.mLastNonConfigurationInstances = lastNonConfigurationInstances;
        if (voiceInteractor != null) {
            if (lastNonConfigurationInstances != null) {
                this.mVoiceInteractor = lastNonConfigurationInstances.voiceInteractor;
            } else {
                this.mVoiceInteractor =
                        new VoiceInteractor(voiceInteractor, this, this, Looper.myLooper());
            }
        }
        this.mWindow.setWindowManager(
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE),
                this.mToken,
                this.mComponent.flattenToString(),
                (info.flags & 512) != 0);
        if (this.mParent != null) {
            this.mWindow.setContainer(this.mParent.getWindow());
        }
        this.mWindowManager = this.mWindow.getWindowManager();
        this.mCurrentConfig = config;
        this.mWindow.setColorMode(info.colorMode);
        this.mWindow.setPreferMinimalPostProcessing((info.flags & 33554432) != 0);
        getAutofillClientController().onActivityAttached(application);
        setContentCaptureOptions(application.getContentCaptureOptions());
        this.mWindowingMode = config.windowConfiguration.getWindowingMode();
        this.mLongPressDetector = new GestureDetector(this, this.mLongPressListener, this.mHandler);
        this.mIsPopOver = config.windowConfiguration.isPopOver();
        this.mDexTaskDocking = config.windowConfiguration.getDexTaskDockingState();
        if (android.security.Flags.contentUriPermissionApis()) {
            this.mInitialCaller =
                    new ComponentCaller(getActivityToken(), initialCallerInfoAccessToken);
            this.mCaller = this.mInitialCaller;
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final IBinder getActivityToken() {
        return this.mParent != null ? this.mParent.getActivityToken() : this.mToken;
    }

    public final IBinder getAssistToken() {
        return this.mParent != null ? this.mParent.getAssistToken() : this.mAssistToken;
    }

    public final IBinder getShareableActivityToken() {
        return this.mParent != null
                ? this.mParent.getShareableActivityToken()
                : this.mShareableActivityToken;
    }

    public final ActivityThread getActivityThread() {
        return this.mMainThread;
    }

    public final ActivityInfo getActivityInfo() {
        return this.mActivityInfo;
    }

    final void performCreate(Bundle icicle) {
        performCreate(icicle, null);
    }

    final void performCreate(Bundle icicle, PersistableBundle persistentState) {
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "performCreate:" + this.mComponent.getClassName());
        }
        dispatchActivityPreCreated(icicle);
        this.mCanEnterPictureInPicture = true;
        int windowingMode =
                CompatSandbox.getCompatWindowingMode(
                        getResources().getConfiguration(),
                        getResources().getConfiguration().windowConfiguration.getWindowingMode());
        this.mIsInMultiWindowMode = WindowConfiguration.inMultiWindowMode(windowingMode);
        this.mIsInPictureInPictureMode = windowingMode == 2;
        this.mShouldDockBigOverlays =
                getResources().getBoolean(R.bool.config_dockBigOverlayWindows);
        restoreHasCurrentPermissionRequest(icicle);
        long startTime = SystemClock.uptimeMillis();
        if (persistentState != null) {
            onCreate(icicle, persistentState);
        } else {
            onCreate(icicle);
        }
        long duration = SystemClock.uptimeMillis() - startTime;
        EventLogTags.writeWmOnCreateCalled(
                this.mIdent, getComponentName().getClassName(), "performCreate", duration);
        this.mActivityTransitionState.readState(icicle);
        this.mVisibleFromClient = true ^ this.mWindow.getWindowStyle().getBoolean(10, false);
        this.mFragments.dispatchActivityCreated();
        this.mActivityTransitionState.setEnterSceneTransitionInfo(this, getSceneTransitionInfo());
        dispatchActivityPostCreated(icicle);
        Trace.traceEnd(32L);
    }

    final void performNewIntent(Intent intent) {
        Trace.traceBegin(32L, "performNewIntent");
        this.mCanEnterPictureInPicture = true;
        onNewIntent(intent);
        Trace.traceEnd(32L);
    }

    final void performNewIntent(Intent intent, ComponentCaller caller) {
        Trace.traceBegin(32L, "performNewIntent");
        this.mCanEnterPictureInPicture = true;
        this.mCurrentCaller = caller;
        onNewIntent(intent, caller);
        this.mCurrentCaller = null;
        Trace.traceEnd(32L);
    }

    final void performStart(String reason) {
        String dlwarning;
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "performStart:" + this.mComponent.getClassName());
        }
        dispatchActivityPreStarted();
        this.mActivityTransitionState.setEnterSceneTransitionInfo(this, getSceneTransitionInfo());
        this.mFragments.noteStateNotSaved();
        this.mCalled = false;
        this.mFragments.execPendingActions();
        long startTime = SystemClock.uptimeMillis();
        this.mInstrumentation.callActivityOnStart(this);
        long duration = SystemClock.uptimeMillis() - startTime;
        EventLogTags.writeWmOnStartCalled(
                this.mIdent, getComponentName().getClassName(), reason, duration);
        if (!this.mCalled) {
            throw new SuperNotCalledException(
                    "Activity "
                            + this.mComponent.toShortString()
                            + " did not call through to super.onStart()");
        }
        this.mFragments.dispatchStart();
        this.mFragments.reportLoaderStart();
        boolean isAppDebuggable = (this.mApplication.getApplicationInfo().flags & 2) != 0;
        if (isAppDebuggable && (dlwarning = getDlWarning()) != null) {
            String appName = getApplicationInfo().loadLabel(getPackageManager()).toString();
            String warning =
                    "Detected problems with app native libraries\n"
                            + "(please consult log for detail):\n"
                            + dlwarning;
            if (isAppDebuggable) {
                new AlertDialog.Builder(this)
                        .setTitle(appName)
                        .setMessage(warning)
                        .setPositiveButton(17039370, (DialogInterface.OnClickListener) null)
                        .setCancelable(false)
                        .show();
            } else {
                Toast.makeText(this, appName + "\n" + warning, 1).show();
            }
        }
        GraphicsEnvironment.getInstance().showAngleInUseDialogBox(this);
        this.mActivityTransitionState.enterReady(this);
        dispatchActivityPostStarted();
        if (CoreRune.FW_APPLOCK) {
            ActivityManager am = (ActivityManager) getSystemService("activity");
            String pkgName = getPackageName();
            boolean isLocked = am.isAppLockedPackage(pkgName);
            if (isLocked) {
                startAppLockService();
            }
        }
        Trace.traceEnd(32L);
    }

    final void performRestart(boolean start) {
        Trace.traceBegin(32L, "performRestart");
        this.mCanEnterPictureInPicture = true;
        this.mFragments.noteStateNotSaved();
        if (this.mToken != null && this.mParent == null) {
            WindowManagerGlobal.getInstance().setStoppedState(this.mToken, false);
        }
        if (this.mStopped) {
            this.mStopped = false;
            synchronized (this.mManagedCursors) {
                int N = this.mManagedCursors.size();
                for (int i = 0; i < N; i++) {
                    ManagedCursor mc = this.mManagedCursors.get(i);
                    if (mc.mReleased || mc.mUpdated) {
                        if (!mc.mCursor.requery() && getApplicationInfo().targetSdkVersion >= 14) {
                            throw new IllegalStateException(
                                    "trying to requery an already closed cursor  " + mc.mCursor);
                        }
                        mc.mReleased = false;
                        mc.mUpdated = false;
                    }
                }
            }
            this.mCalled = false;
            long startTime = SystemClock.uptimeMillis();
            this.mInstrumentation.callActivityOnRestart(this);
            long duration = SystemClock.uptimeMillis() - startTime;
            EventLogTags.writeWmOnRestartCalled(
                    this.mIdent, getComponentName().getClassName(), "performRestart", duration);
            if (!this.mCalled) {
                throw new SuperNotCalledException(
                        "Activity "
                                + this.mComponent.toShortString()
                                + " did not call through to super.onRestart()");
            }
            if (start) {
                performStart("performRestart");
            }
        }
        Trace.traceEnd(32L);
    }

    final void performResume(boolean followedByPause, String reason) {
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "performResume:" + this.mComponent.getClassName());
        }
        dispatchActivityPreResumed();
        this.mFragments.execPendingActions();
        this.mLastNonConfigurationInstances = null;
        getAutofillClientController().onActivityPerformResume(followedByPause);
        this.mCalled = false;
        long startTime = SystemClock.uptimeMillis();
        this.mInstrumentation.callActivityOnResume(this);
        long duration = SystemClock.uptimeMillis() - startTime;
        EventLogTags.writeWmOnResumeCalled(
                this.mIdent, getComponentName().getClassName(), reason, duration);
        if (!this.mCalled) {
            throw new SuperNotCalledException(
                    "Activity "
                            + this.mComponent.toShortString()
                            + " did not call through to super.onResume()");
        }
        if (!this.mVisibleFromClient && !this.mFinished) {
            Log.w(TAG, "An activity without a UI must call finish() before onResume() completes");
            if (getApplicationInfo().targetSdkVersion > 22) {
                throw new IllegalStateException(
                        "Activity "
                                + this.mComponent.toShortString()
                                + " did not call finish() prior to onResume() completing");
            }
        }
        this.mCalled = false;
        this.mFragments.dispatchResume();
        this.mFragments.execPendingActions();
        onPostResume();
        if (!this.mCalled) {
            throw new SuperNotCalledException(
                    "Activity "
                            + this.mComponent.toShortString()
                            + " did not call through to super.onPostResume()");
        }
        dispatchActivityPostResumed();
        Trace.traceEnd(32L);
    }

    final void performPause() {
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "performPause:" + this.mComponent.getClassName());
        }
        if (MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED
                && this.mIsTopResumedActivity
                && this.mResumed) {
            performTopResumedActivityChanged(false, "pausing");
        }
        dispatchActivityPrePaused();
        this.mDoReportFullyDrawn = false;
        this.mFragments.dispatchPause();
        this.mCalled = false;
        long startTime = SystemClock.uptimeMillis();
        onPause();
        long duration = SystemClock.uptimeMillis() - startTime;
        EventLogTags.writeWmOnPausedCalled(
                this.mIdent, getComponentName().getClassName(), "performPause", duration);
        this.mResumed = false;
        if (!this.mCalled && getApplicationInfo().targetSdkVersion >= 9) {
            throw new SuperNotCalledException(
                    "Activity "
                            + this.mComponent.toShortString()
                            + " did not call through to super.onPause()");
        }
        dispatchActivityPostPaused();
        Trace.traceEnd(32L);
    }

    final void performUserLeaving() {
        onUserInteraction();
        onUserLeaveHint();
    }

    final void performStop(boolean preserveWindow, String reason) {
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "performStop:" + this.mComponent.getClassName());
        }
        this.mDoReportFullyDrawn = false;
        this.mFragments.doLoaderStop(this.mChangingConfigurations);
        this.mCanEnterPictureInPicture = false;
        if (!this.mStopped) {
            dispatchActivityPreStopped();
            if (this.mWindow != null) {
                this.mWindow.closeAllPanels();
            }
            if (!preserveWindow && this.mToken != null && this.mParent == null) {
                WindowManagerGlobal.getInstance().setStoppedState(this.mToken, true);
            }
            this.mFragments.dispatchStop();
            this.mCalled = false;
            long startTime = SystemClock.uptimeMillis();
            this.mInstrumentation.callActivityOnStop(this);
            long duration = SystemClock.uptimeMillis() - startTime;
            EventLogTags.writeWmOnStopCalled(
                    this.mIdent, getComponentName().getClassName(), reason, duration);
            if (!this.mCalled) {
                throw new SuperNotCalledException(
                        "Activity "
                                + this.mComponent.toShortString()
                                + " did not call through to super.onStop()");
            }
            synchronized (this.mManagedCursors) {
                int N = this.mManagedCursors.size();
                for (int i = 0; i < N; i++) {
                    ManagedCursor mc = this.mManagedCursors.get(i);
                    if (!mc.mReleased) {
                        mc.mCursor.deactivate();
                        mc.mReleased = true;
                    }
                }
            }
            this.mStopped = true;
            dispatchActivityPostStopped();
        }
        this.mResumed = false;
        Trace.traceEnd(32L);
    }

    final void performDestroy() {
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "performDestroy:" + this.mComponent.getClassName());
        }
        dispatchActivityPreDestroyed();
        this.mDestroyed = true;
        this.mWindow.destroy();
        this.mFragments.dispatchDestroy();
        long startTime = SystemClock.uptimeMillis();
        onDestroy();
        long duration = SystemClock.uptimeMillis() - startTime;
        EventLogTags.writeWmOnDestroyCalled(
                this.mIdent, getComponentName().getClassName(), "performDestroy", duration);
        this.mFragments.doLoaderDestroy();
        if (this.mVoiceInteractor != null) {
            this.mVoiceInteractor.detachActivity();
        }
        dispatchActivityPostDestroyed();
        Trace.traceEnd(32L);
    }

    final void dispatchMultiWindowModeChanged(
            boolean isInMultiWindowMode, Configuration newConfig) {
        this.mIsInMultiWindowMode = isInMultiWindowMode;
        this.mFragments.dispatchMultiWindowModeChanged(isInMultiWindowMode, newConfig);
        this.mWindowingMode = newConfig.windowConfiguration.getWindowingMode();
        if (this.mWindow.getDecorView() != null) {
            boolean split =
                    this.mWindowingMode == 6
                            && WindowConfiguration.isSplitScreenWindowingMode(
                                    newConfig.windowConfiguration);
            ((DecorView) this.mWindow.getDecorView())
                    .onWindowingModeChanged(this.mWindowingMode, split);
        }
        if (this.mWindow != null) {
            this.mWindow.onMultiWindowModeChanged();
        }
        onMultiWindowModeChanged(isInMultiWindowMode, newConfig);
    }

    final void dispatchPictureInPictureModeChanged(
            boolean isInPictureInPictureMode, Configuration newConfig) {
        this.mIsInPictureInPictureMode = isInPictureInPictureMode;
        this.mFragments.dispatchPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (this.mWindow != null) {
            this.mWindow.onPictureInPictureModeChanged(isInPictureInPictureMode);
        }
        onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public final boolean isResumed() {
        return this.mResumed;
    }

    public final boolean semIsResumed() {
        return this.mResumed;
    }

    private void storeHasCurrentPermissionRequest(Bundle bundle) {
        if (bundle != null && this.mHasCurrentPermissionsRequest) {
            bundle.putBoolean(HAS_CURRENT_PERMISSIONS_REQUEST_KEY, true);
        }
    }

    private void restoreHasCurrentPermissionRequest(Bundle bundle) {
        if (bundle != null) {
            this.mHasCurrentPermissionsRequest =
                    bundle.getBoolean(HAS_CURRENT_PERMISSIONS_REQUEST_KEY, false);
        }
    }

    void dispatchActivityResult(
            String who,
            int requestCode,
            int resultCode,
            Intent data,
            ComponentCaller caller,
            String reason) {
        internalDispatchActivityResult(who, requestCode, resultCode, data, caller, reason);
    }

    void dispatchActivityResult(
            String who, int requestCode, int resultCode, Intent data, String reason) {
        if (android.security.Flags.contentUriPermissionApis()) {
            internalDispatchActivityResult(
                    who,
                    requestCode,
                    resultCode,
                    data,
                    new ComponentCaller(getActivityToken(), null),
                    reason);
        } else {
            internalDispatchActivityResult(who, requestCode, resultCode, data, null, reason);
        }
    }

    private void internalDispatchActivityResult(
            String who,
            int requestCode,
            int resultCode,
            Intent data,
            ComponentCaller caller,
            String reason) {
        this.mFragments.noteStateNotSaved();
        if (who == null) {
            if (android.security.Flags.contentUriPermissionApis()) {
                this.mCurrentCaller = caller;
                onActivityResult(requestCode, resultCode, data, caller);
                this.mCurrentCaller = null;
            } else {
                onActivityResult(requestCode, resultCode, data);
            }
        } else if (who.startsWith(REQUEST_PERMISSIONS_WHO_PREFIX)) {
            String who2 = who.substring(REQUEST_PERMISSIONS_WHO_PREFIX.length());
            if (TextUtils.isEmpty(who2)) {
                dispatchRequestPermissionsResult(requestCode, data);
            } else {
                Fragment frag = this.mFragments.findFragmentByWho(who2);
                if (frag != null) {
                    dispatchRequestPermissionsResultToFragment(requestCode, data, frag);
                }
            }
        } else if (who.startsWith("@android:view:")) {
            ArrayList<ViewRootImpl> views =
                    WindowManagerGlobal.getInstance().getRootViews(getActivityToken());
            Iterator<ViewRootImpl> it = views.iterator();
            while (it.hasNext()) {
                ViewRootImpl viewRoot = it.next();
                if (viewRoot.getView() != null
                        && viewRoot.getView()
                                .dispatchActivityResult(who, requestCode, resultCode, data)) {
                    return;
                }
            }
        } else if (who.startsWith(AutofillClientController.AUTO_FILL_AUTH_WHO_PREFIX)) {
            getAutofillClientController().onDispatchActivityResult(requestCode, resultCode, data);
        } else {
            Fragment frag2 = this.mFragments.findFragmentByWho(who);
            if (frag2 != null) {
                frag2.onActivityResult(requestCode, resultCode, data);
            }
        }
        EventLogTags.writeWmOnActivityResultCalled(
                this.mIdent, getComponentName().getClassName(), reason);
    }

    public void startLockTask() {
        ActivityClient.getInstance().startLockTaskModeByToken(this.mToken);
    }

    public void stopLockTask() {
        ActivityClient.getInstance().stopLockTaskModeByToken(this.mToken);
    }

    public void showLockTaskEscapeMessage() {
        ActivityClient.getInstance().showLockTaskEscapeMessage(this.mToken);
    }

    public boolean isOverlayWithDecorCaptionEnabled() {
        return this.mWindow.isOverlayWithDecorCaptionEnabled();
    }

    public void setOverlayWithDecorCaptionEnabled(boolean enabled) {
        this.mWindow.setOverlayWithDecorCaptionEnabled(enabled);
    }

    private void dispatchRequestPermissionsResult(int requestCode, Intent data) {
        this.mHasCurrentPermissionsRequest = false;
        String[] permissions =
                data != null
                        ? data.getStringArrayExtra(PackageManager.EXTRA_REQUEST_PERMISSIONS_NAMES)
                        : new String[0];
        int[] grantResults =
                data != null
                        ? data.getIntArrayExtra(PackageManager.EXTRA_REQUEST_PERMISSIONS_RESULTS)
                        : new int[0];
        int deviceId =
                data != null
                        ? data.getIntExtra(PackageManager.EXTRA_REQUEST_PERMISSIONS_DEVICE_ID, 0)
                        : 0;
        onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);
    }

    private void dispatchRequestPermissionsResultToFragment(
            int requestCode, Intent data, Fragment fragment) {
        String[] permissions =
                data != null
                        ? data.getStringArrayExtra(PackageManager.EXTRA_REQUEST_PERMISSIONS_NAMES)
                        : new String[0];
        int[] grantResults =
                data != null
                        ? data.getIntArrayExtra(PackageManager.EXTRA_REQUEST_PERMISSIONS_RESULTS)
                        : new int[0];
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public final boolean isVisibleForAutofill() {
        return !this.mStopped;
    }

    public void setDisablePreviewScreenshots(boolean disable) {
        setRecentsScreenshotEnabled(!disable);
    }

    public void setRecentsScreenshotEnabled(boolean enabled) {
        ActivityClient.getInstance().setRecentsScreenshotEnabled(this.mToken, enabled);
    }

    @Deprecated
    public void semSetDisablePreviewScreenshots(boolean disable) {
        setRecentsScreenshotEnabled(!disable);
    }

    public void setShowWhenLocked(boolean showWhenLocked) {
        ActivityClient.getInstance().setShowWhenLocked(this.mToken, showWhenLocked);
    }

    public void setInheritShowWhenLocked(boolean inheritShowWhenLocked) {
        ActivityClient.getInstance().setInheritShowWhenLocked(this.mToken, inheritShowWhenLocked);
    }

    public void setTurnScreenOn(boolean turnScreenOn) {
        ActivityClient.getInstance().setTurnScreenOn(this.mToken, turnScreenOn);
    }

    public void setAllowCrossUidActivitySwitchFromBelow(boolean allowed) {
        ActivityClient.getInstance().setAllowCrossUidActivitySwitchFromBelow(this.mToken, allowed);
    }

    public void registerRemoteAnimations(RemoteAnimationDefinition definition) {
        ActivityClient.getInstance().registerRemoteAnimations(this.mToken, definition);
    }

    public void unregisterRemoteAnimations() {
        ActivityClient.getInstance().unregisterRemoteAnimations(this.mToken);
    }

    public void updateUiTranslationState(
            int state,
            TranslationSpec sourceSpec,
            TranslationSpec targetSpec,
            List<AutofillId> viewIds,
            UiTranslationSpec uiTranslationSpec) {
        if (this.mUiTranslationController == null) {
            this.mUiTranslationController =
                    new UiTranslationController(this, getApplicationContext());
        }
        this.mUiTranslationController.updateUiTranslationState(
                state, sourceSpec, targetSpec, viewIds, uiTranslationSpec);
    }

    public void enableTaskLocaleOverride() {
        ActivityClient.getInstance().enableTaskLocaleOverride(this.mToken);
    }

    public void changeToHorizontalSplitLayout() {
        MultiWindowManager multiWindowManager = new MultiWindowManager();
        multiWindowManager.changeToHorizontalSplitLayout(getActivityToken());
    }

    public void setActivityRecordInputSinkEnabled(boolean enabled) {
        ActivityClient.getInstance().setActivityRecordInputSinkEnabled(this.mToken, enabled);
    }

    class HostCallbacks extends FragmentHostCallback<Activity> {
        public HostCallbacks() {
            super(Activity.this);
        }

        @Override // android.app.FragmentHostCallback
        public void onDump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            Activity.this.dump(prefix, fd, writer, args);
        }

        @Override // android.app.FragmentHostCallback
        public boolean onShouldSaveFragmentState(Fragment fragment) {
            return !Activity.this.isFinishing();
        }

        @Override // android.app.FragmentHostCallback
        public LayoutInflater onGetLayoutInflater() {
            LayoutInflater result = Activity.this.getLayoutInflater();
            if (onUseFragmentManagerInflaterFactory()) {
                return result.cloneInContext(Activity.this);
            }
            return result;
        }

        @Override // android.app.FragmentHostCallback
        public boolean onUseFragmentManagerInflaterFactory() {
            return Activity.this.getApplicationInfo().targetSdkVersion >= 21;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.app.FragmentHostCallback
        public Activity onGetHost() {
            return Activity.this;
        }

        @Override // android.app.FragmentHostCallback
        public void onInvalidateOptionsMenu() {
            Activity.this.invalidateOptionsMenu();
        }

        @Override // android.app.FragmentHostCallback
        public void onStartActivityFromFragment(
                Fragment fragment, Intent intent, int requestCode, Bundle options) {
            Activity.this.startActivityFromFragment(fragment, intent, requestCode, options);
        }

        @Override // android.app.FragmentHostCallback
        public void onStartActivityAsUserFromFragment(
                Fragment fragment,
                Intent intent,
                int requestCode,
                Bundle options,
                UserHandle user) {
            Activity.this.startActivityAsUserFromFragment(
                    fragment, intent, requestCode, options, user);
        }

        @Override // android.app.FragmentHostCallback
        public void onStartIntentSenderFromFragment(
                Fragment fragment,
                IntentSender intent,
                int requestCode,
                Intent fillInIntent,
                int flagsMask,
                int flagsValues,
                int extraFlags,
                Bundle options)
                throws IntentSender.SendIntentException {
            if (Activity.this.mParent == null) {
                Activity.this.startIntentSenderForResultInner(
                        intent,
                        fragment.mWho,
                        requestCode,
                        fillInIntent,
                        flagsMask,
                        flagsValues,
                        options);
            } else if (options != null) {
                Activity.this.mParent.startIntentSenderFromFragment(
                        fragment,
                        intent,
                        requestCode,
                        fillInIntent,
                        flagsMask,
                        flagsValues,
                        options);
            }
        }

        @Override // android.app.FragmentHostCallback
        public void onRequestPermissionsFromFragment(
                Fragment fragment, String[] permissions, int requestCode) {
            String who = Activity.REQUEST_PERMISSIONS_WHO_PREFIX + fragment.mWho;
            Intent intent =
                    Activity.this.getPackageManager().buildRequestPermissionsIntent(permissions);
            Activity.this.startActivityForResult(who, intent, requestCode, null);
        }

        @Override // android.app.FragmentHostCallback
        public boolean onHasWindowAnimations() {
            return Activity.this.getWindow() != null;
        }

        @Override // android.app.FragmentHostCallback
        public int onGetWindowAnimations() {
            Window w = Activity.this.getWindow();
            if (w == null) {
                return 0;
            }
            return w.getAttributes().windowAnimations;
        }

        @Override // android.app.FragmentHostCallback
        public void onAttachFragment(Fragment fragment) {
            Activity.this.onAttachFragment(fragment);
        }

        @Override // android.app.FragmentHostCallback, android.app.FragmentContainer
        public <T extends View> T onFindViewById(int i) {
            return (T) Activity.this.findViewById(i);
        }

        @Override // android.app.FragmentHostCallback, android.app.FragmentContainer
        public boolean onHasView() {
            Window w = Activity.this.getWindow();
            return (w == null || w.peekDecorView() == null) ? false : true;
        }
    }

    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        if (this.mWindow == null) {
            throw new IllegalStateException(
                    "OnBackInvokedDispatcher are not available on non-visual activities");
        }
        return this.mWindow.getOnBackInvokedDispatcher();
    }

    private boolean interceptTouchEventForPopOver(MotionEvent ev) {
        if (!this.mIsPopOver) {
            return false;
        }
        if (ev.getAction() == 0 && isOutOfBounds(ev)) {
            this.mInOutsideTouch = true;
        }
        if (!this.mInOutsideTouch) {
            return false;
        }
        onOutsideTouchEventForPopOver(ev);
        return true;
    }

    private void onOutsideTouchEventForPopOver(MotionEvent ev) {
        if (!this.mInOutsideLongPress) {
            this.mLongPressDetector.onTouchEvent(ev);
        }
        if (ev.getAction() == 1 || ev.getAction() == 3) {
            this.mInOutsideTouch = false;
            if (this.mInOutsideLongPress) {
                this.mInOutsideLongPress = false;
                clearTransparentPopOver();
            } else if (ev.getAction() == 1 && isOutOfBounds(ev)) {
                onBackPressed();
            }
        }
    }

    private boolean isOutOfBounds(MotionEvent ev) {
        return this.mWindow.peekDecorView() != null && this.mWindow.isOutOfBounds(this, ev);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyTransparentPopOver() {
        WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
        attrs.semAddExtensionFlags(2);
        this.mWindow.setAttributes(attrs);
    }

    private void clearTransparentPopOver() {
        WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
        attrs.semClearExtensionFlags(2);
        this.mWindow.setAttributes(attrs);
    }

    public void registerScreenCaptureCallback(Executor executor, ScreenCaptureCallback callback) {
        if (this.mScreenCaptureCallbackHandler == null) {
            this.mScreenCaptureCallbackHandler = new ScreenCaptureCallbackHandler(this.mToken);
        }
        this.mScreenCaptureCallbackHandler.registerScreenCaptureCallback(executor, callback);
    }

    public void unregisterScreenCaptureCallback(ScreenCaptureCallback callback) {
        if (this.mScreenCaptureCallbackHandler != null) {
            this.mScreenCaptureCallbackHandler.unregisterScreenCaptureCallback(callback);
        }
    }

    private void startAppLockService() {
        boolean showWhenLocked = false;
        WindowManager.LayoutParams lp = this.mWindow != null ? this.mWindow.getAttributes() : null;
        if (lp != null && ((lp.flags & 4194304) != 0 || (lp.flags & 524288) != 0)) {
            showWhenLocked = true;
        }
        try {
            ActivityTaskManager.getService()
                    .startAppLockService(
                            this.mToken, getIntent(), showWhenLocked, getPackageName());
        } catch (RemoteException e) {
        }
    }

    public boolean semExitMultiWindowMode() {
        MultiWindowManager multiWindowManager = new MultiWindowManager();
        return multiWindowManager.exitMultiWindow(getActivityToken(), true);
    }

    int getWindowingMode() {
        return this.mWindowingMode;
    }

    void releaseActivityFocusIfNeeded() {
        if (this.mDecor instanceof DecorView) {
            ((DecorView) this.mDecor).releaseActivityFocusIfNeeded();
        }
    }

    int getDexTaskDocking() {
        return this.mDexTaskDocking;
    }

    void onDexTaskDockingChanged(int state) {
        if (CoreRune.IS_DEBUG_LEVEL_MID) {
            Log.i(
                    TAG,
                    "onDexTaskDockingChanged="
                            + WindowConfiguration.dexTaskDockingStateToString(state)
                            + "   mDecor="
                            + this.mDecor
                            + " state in number?"
                            + state);
        }
        if (this.mDecor != null) {
            ((DecorView) this.mDecor).onDexTaskDockingChanged(state);
        }
        this.mDexTaskDocking = state;
    }
}
