package android.app;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Application;
import android.app.IRequestFinishCallback;
import android.app.Instrumentation;
import android.app.PictureInPictureParams;
import android.app.VoiceInteractor;
import android.app.assist.AssistContent;
import android.app.compat.CompatChanges;
import android.app.jank.JankTracker;
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
import android.view.Choreographer;
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
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import dalvik.system.VMRuntime;
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

/* loaded from: classes.dex */
public class Activity extends ContextThemeWrapper implements LayoutInflater.Factory2, Window.Callback, KeyEvent.Callback, View.OnCreateContextMenuListener, ComponentCallbacks2, Window.OnWindowDismissedCallback, ContentCaptureManager.ContentCaptureClient {
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
    private static final String HAS_CURRENT_PERMISSIONS_REQUEST_KEY = "android:hasCurrentPermissionsRequest";
    private static final String KEYBOARD_SHORTCUTS_RECEIVER_DESKTOP_PKG_NAME = "com.sec.android.dexsystemui";
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
    private JankTracker mJankTracker;
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
    private final ArrayList<Application.ActivityLifecycleCallbacks> mActivityLifecycleCallbacks = new ArrayList<>();
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
    private final ActivityManager.TaskDescription mTaskDescription = new ActivityManager.TaskDescription();
    private int mLastRequestedOrientation = -2;
    private final Object mInstanceTracker = StrictMode.trackActivity(this);
    final ActivityTransitionState mActivityTransitionState = new ActivityTransitionState();
    SharedElementCallback mEnterTransitionListener = SharedElementCallback.NULL_CALLBACK;
    SharedElementCallback mExitTransitionListener = SharedElementCallback.NULL_CALLBACK;
    private final Window.WindowControllerCallback mWindowControllerCallback = new Window.WindowControllerCallback() { // from class: android.app.Activity.1
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
            return ActivityClient.getInstance().getTaskForActivity(Activity.this.mToken, true) >= 0;
        }

        @Override // android.view.Window.WindowControllerCallback
        public void updateStatusBarColor(int i) {
            Activity.this.mTaskDescription.setStatusBarColor(i);
            Activity activity = Activity.this;
            activity.setTaskDescription(activity.mTaskDescription);
        }

        @Override // android.view.Window.WindowControllerCallback
        public void updateSystemBarsAppearance(int i) {
            Activity.this.mTaskDescription.setSystemBarsAppearance(i);
            Activity activity = Activity.this;
            activity.setTaskDescription(activity.mTaskDescription);
        }

        @Override // android.view.Window.WindowControllerCallback
        public void updateNavigationBarColor(int i) {
            Activity.this.mTaskDescription.setNavigationBarColor(i);
            Activity activity = Activity.this;
            activity.setTaskDescription(activity.mTaskDescription);
        }
    };
    private int mDexTaskDocking = -1;
    private boolean mIsPopOver = false;
    private final GestureDetector.SimpleOnGestureListener mLongPressListener = new GestureDetector.SimpleOnGestureListener() { // from class: android.app.Activity.3
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            Activity.this.mInOutsideLongPress = true;
            Activity.this.applyTransparentPopOver();
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    @interface ContentCaptureNotificationType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface DefaultKeyMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FullscreenModeRequest {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OverrideTransition {
    }

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

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean canStartActivityForResult() {
        return true;
    }

    @Override // android.content.Context
    public final ContentCaptureManager.ContentCaptureClient getContentCaptureClient() {
        return this;
    }

    @SystemApi
    @Deprecated
    public boolean isBackgroundVisibleBehind() {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onActionModeFinished(ActionMode actionMode) {
    }

    @Override // android.view.Window.Callback
    public void onActionModeStarted(ActionMode actionMode) {
    }

    public void onActivityReenter(int i, Intent intent) {
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
    }

    @Deprecated
    public void onAttachFragment(Fragment fragment) {
    }

    @Override // android.view.Window.Callback
    public void onAttachedToWindow() {
    }

    @SystemApi
    @Deprecated
    public void onBackgroundVisibleBehindChanged(boolean z) {
    }

    protected void onChildTitleChanged(Activity activity, CharSequence charSequence) {
    }

    @Override // android.view.Window.Callback
    public void onContentChanged() {
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
    }

    public CharSequence onCreateDescription() {
        return null;
    }

    @Deprecated
    protected Dialog onCreateDialog(int i) {
        return null;
    }

    @Override // android.view.Window.Callback
    public View onCreatePanelView(int i) {
        return null;
    }

    @Deprecated
    public boolean onCreateThumbnail(Bitmap bitmap, Canvas canvas) {
        return false;
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return null;
    }

    @Override // android.view.Window.Callback
    public void onDetachedFromWindow() {
    }

    public void onEnterAnimationComplete() {
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return false;
    }

    public void onLocalVoiceInteractionStarted() {
    }

    public void onLocalVoiceInteractionStopped() {
    }

    public void onMovedToDisplay(int i, Configuration configuration) {
    }

    @Deprecated
    public void onMultiWindowModeChanged(boolean z) {
    }

    protected void onNewIntent(Intent intent) {
    }

    public void onPerformDirectAction(String str, Bundle bundle, CancellationSignal cancellationSignal, Consumer<Bundle> consumer) {
    }

    @Deprecated
    public void onPictureInPictureModeChanged(boolean z) {
    }

    public boolean onPictureInPictureRequested() {
        return false;
    }

    public void onPictureInPictureUiStateChanged(PictureInPictureUiState pictureInPictureUiState) {
    }

    public void onPrepareNavigateUpTaskStack(TaskStackBuilder taskStackBuilder) {
    }

    public void onProvideAssistContent(AssistContent assistContent) {
    }

    public void onProvideAssistData(Bundle bundle) {
    }

    public Uri onProvideReferrer() {
        return null;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
    }

    HashMap<String, Object> onRetainNonConfigurationChildInstances() {
        return null;
    }

    public Object onRetainNonConfigurationInstance() {
        return null;
    }

    @Deprecated
    public void onStateNotSaved() {
    }

    public void onTopResumedActivityChanged(boolean z) {
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    public void onUserInteraction() {
    }

    protected void onUserLeaveHint() {
    }

    @Override // android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
    }

    @Deprecated
    public boolean requestVisibleBehind(boolean z) {
        return false;
    }

    @Deprecated
    public void setPersistent(boolean z) {
    }

    private static class ManagedDialog {
        Bundle mArgs;
        Dialog mDialog;

        private ManagedDialog() {
        }
    }

    static final class NonConfigurationInstances {
        Object activity;
        HashMap<String, Object> children;
        FragmentManagerNonConfig fragments;
        ArrayMap<String, LoaderManager> loaders;
        VoiceInteractor voiceInteractor;

        NonConfigurationInstances() {
        }
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

    public void setIntent(Intent intent) {
        internalSetIntent(intent, null);
    }

    public ComponentCaller getCaller() {
        return this.mCaller;
    }

    public void setIntent(Intent intent, ComponentCaller componentCaller) {
        internalSetIntent(intent, componentCaller);
    }

    private void internalSetIntent(Intent intent, ComponentCaller componentCaller) {
        this.mIntent = intent;
        this.mCaller = componentCaller;
    }

    public void setLocusContext(LocusId locusId, Bundle bundle) {
        try {
            ActivityManager.getService().setActivityLocusContext(this.mComponent, locusId, this.mToken);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (locusId != null) {
            setLocusContextToContentCapture(locusId, bundle);
        }
    }

    public final void requestOpenInBrowserEducation() {
        try {
            ActivityTaskManager.getService().requestOpenInBrowserEducation(this.mToken);
        } catch (RemoteException unused) {
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
        Window window = this.mWindow;
        if (window != null) {
            return window.getCurrentFocus();
        }
        return null;
    }

    private ContentCaptureManager getContentCaptureManager() {
        if (!UserHandle.isApp(Process.myUid())) {
            return null;
        }
        if (this.mContentCaptureManager == null) {
            this.mContentCaptureManager = (ContentCaptureManager) getSystemService(ContentCaptureManager.class);
        }
        return this.mContentCaptureManager;
    }

    private String getContentCaptureTypeAsString(int i) {
        if (i == 1) {
            return "START";
        }
        if (i == 2) {
            return "RESUME";
        }
        if (i == 3) {
            return "PAUSE";
        }
        if (i == 4) {
            return "STOP";
        }
        return "UNKNOW-" + i;
    }

    private void notifyContentCaptureManagerIfNeeded(int i) {
        if (Trace.isTagEnabled(64L)) {
            Trace.traceBegin(64L, "notifyContentCapture(" + getContentCaptureTypeAsString(i) + ") for " + this.mComponent.toShortString());
        }
        try {
            ContentCaptureManager contentCaptureManager = getContentCaptureManager();
            if (contentCaptureManager == null) {
                return;
            }
            if (i == 1) {
                Window window = getWindow();
                if (window != null) {
                    contentCaptureManager.updateWindowAttributes(window.getAttributes());
                }
                contentCaptureManager.onActivityCreated(this.mToken, this.mShareableActivityToken, getComponentName());
            } else if (i == 2) {
                contentCaptureManager.onActivityResumed();
            } else if (i == 3) {
                contentCaptureManager.onActivityPaused();
            } else if (i == 4) {
                contentCaptureManager.onActivityDestroyed();
            } else {
                Log.wtf(TAG, "Invalid @ContentCaptureNotificationType: " + i);
            }
        } finally {
            Trace.traceEnd(64L);
        }
    }

    private void setLocusContextToContentCapture(LocusId locusId, Bundle bundle) {
        ContentCaptureManager contentCaptureManager = getContentCaptureManager();
        if (contentCaptureManager == null) {
            return;
        }
        ContentCaptureContext.Builder builder = new ContentCaptureContext.Builder(locusId);
        if (bundle != null) {
            builder.setExtras(bundle);
        }
        contentCaptureManager.getMainContentCaptureSession().setContentCaptureContext(builder.build());
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        if (context != null) {
            context.setAutofillClient(getAutofillClient());
            context.setContentCaptureOptions(getContentCaptureOptions());
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

    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.add(activityLifecycleCallbacks);
        }
    }

    public void unregisterActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.remove(activityLifecycleCallbacks);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(ComponentCallbacks componentCallbacks) {
        if (CompatChanges.isChangeEnabled(Context.OVERRIDABLE_COMPONENT_CALLBACKS) && this.mCallbacksController == null) {
            this.mCallbacksController = new ComponentCallbacksController();
        }
        ComponentCallbacksController componentCallbacksController = this.mCallbacksController;
        if (componentCallbacksController != null) {
            componentCallbacksController.registerCallbacks(componentCallbacks);
        } else {
            super.registerComponentCallbacks(componentCallbacks);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(ComponentCallbacks componentCallbacks) {
        ComponentCallbacksController componentCallbacksController = this.mCallbacksController;
        if (componentCallbacksController != null) {
            componentCallbacksController.unregisterCallbacks(componentCallbacks);
        } else {
            super.unregisterComponentCallbacks(componentCallbacks);
        }
    }

    private void dispatchActivityPreCreated(Bundle bundle) {
        getApplication().dispatchActivityPreCreated(this, bundle);
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityPreCreated(this, bundle);
            }
        }
    }

    private void dispatchActivityCreated(Bundle bundle) {
        getApplication().dispatchActivityCreated(this, bundle);
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityCreated(this, bundle);
            }
        }
    }

    private void dispatchActivityPostCreated(Bundle bundle) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityPostCreated(this, bundle);
            }
        }
        getApplication().dispatchActivityPostCreated(this, bundle);
    }

    private void dispatchActivityPreStarted() {
        getApplication().dispatchActivityPreStarted(this);
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityPreStarted(this);
            }
        }
    }

    private void dispatchActivityStarted() {
        getApplication().dispatchActivityStarted(this);
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityStarted(this);
            }
        }
    }

    private void dispatchActivityPostStarted() {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityPostStarted(this);
            }
        }
        getApplication().dispatchActivityPostStarted(this);
    }

    private void dispatchActivityPreResumed() {
        getApplication().dispatchActivityPreResumed(this);
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityPreResumed(this);
            }
        }
    }

    private void dispatchActivityResumed() {
        getApplication().dispatchActivityResumed(this);
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityResumed(this);
            }
        }
    }

    private void dispatchActivityPostResumed() {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityPostResumed(this);
            }
        }
        getApplication().dispatchActivityPostResumed(this);
    }

    private void dispatchActivityPrePaused() {
        getApplication().dispatchActivityPrePaused(this);
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPrePaused(this);
            }
        }
    }

    private void dispatchActivityPaused() {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPaused(this);
            }
        }
        getApplication().dispatchActivityPaused(this);
    }

    private void dispatchActivityPostPaused() {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPostPaused(this);
            }
        }
        getApplication().dispatchActivityPostPaused(this);
    }

    private void dispatchActivityPreStopped() {
        getApplication().dispatchActivityPreStopped(this);
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPreStopped(this);
            }
        }
    }

    private void dispatchActivityStopped() {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityStopped(this);
            }
        }
        getApplication().dispatchActivityStopped(this);
    }

    private void dispatchActivityPostStopped() {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPostStopped(this);
            }
        }
        getApplication().dispatchActivityPostStopped(this);
    }

    private void dispatchActivityPreSaveInstanceState(Bundle bundle) {
        getApplication().dispatchActivityPreSaveInstanceState(this, bundle);
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPreSaveInstanceState(this, bundle);
            }
        }
    }

    private void dispatchActivitySaveInstanceState(Bundle bundle) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivitySaveInstanceState(this, bundle);
            }
        }
        getApplication().dispatchActivitySaveInstanceState(this, bundle);
    }

    private void dispatchActivityPostSaveInstanceState(Bundle bundle) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPostSaveInstanceState(this, bundle);
            }
        }
        getApplication().dispatchActivityPostSaveInstanceState(this, bundle);
    }

    private void dispatchActivityPreDestroyed() {
        getApplication().dispatchActivityPreDestroyed(this);
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPreDestroyed(this);
            }
        }
    }

    private void dispatchActivityDestroyed() {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityDestroyed(this);
            }
        }
        getApplication().dispatchActivityDestroyed(this);
    }

    private void dispatchActivityPostDestroyed() {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPostDestroyed(this);
            }
        }
        getApplication().dispatchActivityPostDestroyed(this);
    }

    private void dispatchActivityConfigurationChanged() {
        if (getApplication() != null) {
            getApplication().dispatchActivityConfigurationChanged(this);
        }
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((Application.ActivityLifecycleCallbacks) obj).onActivityConfigurationChanged(this);
            }
        }
    }

    private Object[] collectActivityLifecycleCallbacks() {
        Object[] array;
        synchronized (this.mActivityLifecycleCallbacks) {
            array = this.mActivityLifecycleCallbacks.size() > 0 ? this.mActivityLifecycleCallbacks.toArray() : null;
        }
        return array;
    }

    private void notifyVoiceInteractionManagerServiceActivityEvent(int i) {
        if (this.mVoiceInteractionManagerService == null) {
            IVoiceInteractionManagerService asInterface = IVoiceInteractionManagerService.Stub.asInterface(ServiceManager.getService(Context.VOICE_INTERACTION_MANAGER_SERVICE));
            this.mVoiceInteractionManagerService = asInterface;
            if (asInterface == null) {
                Log.w(TAG, "notifyVoiceInteractionManagerServiceActivityEvent: Can not get VoiceInteractionManagerService");
                return;
            }
        }
        try {
            this.mVoiceInteractionManagerService.notifyActivityEventChanged(this.mToken, i);
        } catch (RemoteException unused) {
        }
    }

    protected void onCreate(Bundle bundle) {
        NonConfigurationInstances nonConfigurationInstances = this.mLastNonConfigurationInstances;
        if (nonConfigurationInstances != null) {
            this.mFragments.restoreLoaderNonConfig(nonConfigurationInstances.loaders);
        }
        if (this.mActivityInfo.parentActivityName != null) {
            ActionBar actionBar = this.mActionBar;
            if (actionBar == null) {
                this.mEnableDefaultActionBarUp = true;
            } else {
                actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            }
        }
        if (bundle != null) {
            getAutofillClientController().onActivityCreated(bundle);
            Parcelable parcelable = bundle.getParcelable(FRAGMENTS_TAG);
            FragmentController fragmentController = this.mFragments;
            NonConfigurationInstances nonConfigurationInstances2 = this.mLastNonConfigurationInstances;
            fragmentController.restoreAllState(parcelable, nonConfigurationInstances2 != null ? nonConfigurationInstances2.fragments : null);
        }
        this.mFragments.dispatchCreate();
        dispatchActivityCreated(bundle);
        VoiceInteractor voiceInteractor = this.mVoiceInteractor;
        if (voiceInteractor != null) {
            voiceInteractor.attachActivity(this);
        }
        this.mRestoredFromBundle = bundle != null;
        this.mCalled = true;
        if (WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this)) {
            this.mDefaultBackCallback = new OnBackInvokedCallback() { // from class: android.app.Activity$$ExternalSyntheticLambda0
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    Activity.this.onBackInvoked();
                }
            };
            getOnBackInvokedDispatcher().registerSystemOnBackInvokedCallback(this.mDefaultBackCallback);
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

    public void onCreate(Bundle bundle, PersistableBundle persistableBundle) {
        onCreate(bundle);
    }

    final void performRestoreInstanceState(Bundle bundle) {
        onRestoreInstanceState(bundle);
        restoreManagedDialogs(bundle);
    }

    final void performRestoreInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        onRestoreInstanceState(bundle, persistableBundle);
        if (bundle != null) {
            restoreManagedDialogs(bundle);
        }
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        Bundle bundle2;
        if (this.mWindow == null || (bundle2 = bundle.getBundle(WINDOW_HIERARCHY_TAG)) == null) {
            return;
        }
        this.mWindow.restoreHierarchyState(bundle2);
    }

    public void onRestoreInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        if (bundle != null) {
            onRestoreInstanceState(bundle);
        }
    }

    private void restoreManagedDialogs(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(SAVED_DIALOGS_TAG);
        if (bundle2 == null) {
            return;
        }
        int[] intArray = bundle2.getIntArray(SAVED_DIALOG_IDS_KEY);
        this.mManagedDialogs = new SparseArray<>(intArray.length);
        for (int i : intArray) {
            Bundle bundle3 = bundle2.getBundle(savedDialogKeyFor(i));
            if (bundle3 != null) {
                ManagedDialog managedDialog = new ManagedDialog();
                managedDialog.mArgs = bundle2.getBundle(savedDialogArgsKeyFor(i));
                managedDialog.mDialog = createDialog(Integer.valueOf(i), bundle3, managedDialog.mArgs);
                if (managedDialog.mDialog != null) {
                    this.mManagedDialogs.put(i, managedDialog);
                    onPrepareDialog(i, managedDialog.mDialog, managedDialog.mArgs);
                    managedDialog.mDialog.onRestoreInstanceState(bundle3);
                }
            }
        }
    }

    private Dialog createDialog(Integer num, Bundle bundle, Bundle bundle2) {
        Dialog onCreateDialog = onCreateDialog(num.intValue(), bundle2);
        if (onCreateDialog == null) {
            return null;
        }
        onCreateDialog.dispatchOnCreate(bundle);
        return onCreateDialog;
    }

    private static String savedDialogKeyFor(int i) {
        return SAVED_DIALOG_KEY_PREFIX + i;
    }

    private static String savedDialogArgsKeyFor(int i) {
        return SAVED_DIALOG_ARGS_KEY_PREFIX + i;
    }

    protected void onPostCreate(Bundle bundle) {
        if (!isChild()) {
            this.mTitleReady = true;
            onTitleChanged(getTitle(), getTitleColor());
        }
        this.mCalled = true;
        notifyContentCaptureManagerIfNeeded(1);
        notifyVoiceInteractionManagerServiceActivityEvent(1);
    }

    public void onPostCreate(Bundle bundle, PersistableBundle persistableBundle) {
        onPostCreate(bundle);
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

    protected void onResume() {
        dispatchActivityResumed();
        this.mActivityTransitionState.onResume(this);
        getAutofillClientController().onActivityResumed();
        notifyContentCaptureManagerIfNeeded(2);
        this.mCalled = true;
        if (!this.mIsInPictureInPictureMode || getResources().getConfiguration().windowConfiguration.getWindowingMode() == 2) {
            return;
        }
        Slog.w(TAG, "[PipTaskOrganizer] init mIsInPictureInPictureMode false activity=" + this);
        this.mIsInPictureInPictureMode = false;
    }

    protected void onPostResume() {
        Window window = getWindow();
        if (window != null) {
            window.makeActive();
        }
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(true);
        }
        notifyVoiceInteractionManagerServiceActivityEvent(2);
        getAutofillClientController().onActivityPostResumed();
        if (android.app.jank.Flags.detailedAppJankMetricsApi()) {
            startAppJankTracking();
        }
        this.mCalled = true;
    }

    final void performTopResumedActivityChanged(boolean z, String str) {
        if (!MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED || !this.mResumed || "pausing".equals(str) || (z && !this.mIsTopResumedActivity)) {
            this.mIsTopResumedActivity = z;
            onTopResumedActivityChanged(z);
            if (z) {
                EventLogTags.writeWmOnTopResumedGainedCalled(this.mIdent, getComponentName().getClassName(), str);
            } else {
                EventLogTags.writeWmOnTopResumedLostCalled(this.mIdent, getComponentName().getClassName(), str);
            }
        }
    }

    void setVoiceInteractor(IVoiceInteractor iVoiceInteractor) {
        VoiceInteractor voiceInteractor = this.mVoiceInteractor;
        if (voiceInteractor != null && voiceInteractor.getActiveRequests() != null) {
            for (VoiceInteractor.Request request : this.mVoiceInteractor.getActiveRequests()) {
                request.cancel();
                request.clear();
            }
        }
        if (iVoiceInteractor == null) {
            this.mVoiceInteractor = null;
        } else {
            this.mVoiceInteractor = new VoiceInteractor(iVoiceInteractor, this, this, Looper.myLooper());
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
        return this.mVoiceInteractor != null && ActivityClient.getInstance().isRootVoiceInteraction(this.mToken);
    }

    public VoiceInteractor getVoiceInteractor() {
        return this.mVoiceInteractor;
    }

    public boolean isLocalVoiceInteractionSupported() {
        try {
            return ActivityTaskManager.getService().supportsLocalVoiceInteraction();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void startLocalVoiceInteraction(Bundle bundle) {
        ActivityClient.getInstance().startLocalVoiceInteraction(this.mToken, bundle);
    }

    public void stopLocalVoiceInteraction() {
        ActivityClient.getInstance().stopLocalVoiceInteraction(this.mToken);
    }

    public void onNewIntent(Intent intent, ComponentCaller componentCaller) {
        onNewIntent(intent);
    }

    final void performSaveInstanceState(Bundle bundle) {
        dispatchActivityPreSaveInstanceState(bundle);
        onSaveInstanceState(bundle);
        saveManagedDialogs(bundle);
        this.mActivityTransitionState.saveState(bundle);
        storeHasCurrentPermissionRequest(bundle);
        dispatchActivityPostSaveInstanceState(bundle);
    }

    final void performSaveInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        dispatchActivityPreSaveInstanceState(bundle);
        onSaveInstanceState(bundle, persistableBundle);
        saveManagedDialogs(bundle);
        storeHasCurrentPermissionRequest(bundle);
        dispatchActivityPostSaveInstanceState(bundle);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putBundle(WINDOW_HIERARCHY_TAG, this.mWindow.saveHierarchyState());
        Parcelable saveAllState = this.mFragments.saveAllState();
        if (saveAllState != null) {
            bundle.putParcelable(FRAGMENTS_TAG, saveAllState);
        }
        getAutofillClientController().onSaveInstanceState(bundle);
        dispatchActivitySaveInstanceState(bundle);
    }

    public void onSaveInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        onSaveInstanceState(bundle);
    }

    private void saveManagedDialogs(Bundle bundle) {
        int size;
        SparseArray<ManagedDialog> sparseArray = this.mManagedDialogs;
        if (sparseArray == null || (size = sparseArray.size()) == 0) {
            return;
        }
        Bundle bundle2 = new Bundle();
        int[] iArr = new int[this.mManagedDialogs.size()];
        for (int i = 0; i < size; i++) {
            int keyAt = this.mManagedDialogs.keyAt(i);
            iArr[i] = keyAt;
            ManagedDialog valueAt = this.mManagedDialogs.valueAt(i);
            bundle2.putBundle(savedDialogKeyFor(keyAt), valueAt.mDialog.onSaveInstanceState());
            if (valueAt.mArgs != null) {
                bundle2.putBundle(savedDialogArgsKeyFor(keyAt), valueAt.mArgs);
            }
        }
        bundle2.putIntArray(SAVED_DIALOG_IDS_KEY, iArr);
        bundle.putBundle(SAVED_DIALOGS_TAG, bundle2);
    }

    protected void onPause() {
        dispatchActivityPaused();
        getAutofillClientController().onActivityPaused();
        notifyContentCaptureManagerIfNeeded(3);
        notifyVoiceInteractionManagerServiceActivityEvent(3);
        this.mCalled = true;
    }

    public void onGetDirectActions(CancellationSignal cancellationSignal, Consumer<List<DirectAction>> consumer) {
        consumer.accept(Collections.EMPTY_LIST);
    }

    public final void requestShowKeyboardShortcuts() {
        ComponentName unflattenFromString = ComponentName.unflattenFromString(getResources().getString(R.string.config_systemUIServiceComponent));
        Intent intent = new Intent(Intent.ACTION_SHOW_KEYBOARD_SHORTCUTS);
        intent.setPackage(unflattenFromString.getPackageName());
        sendBroadcastAsUser(intent, Process.myUserHandle());
    }

    public final void dismissKeyboardShortcutsHelper() {
        ComponentName unflattenFromString = ComponentName.unflattenFromString(getResources().getString(R.string.config_systemUIServiceComponent));
        Intent intent = new Intent(Intent.ACTION_DISMISS_KEYBOARD_SHORTCUTS);
        intent.setPackage(unflattenFromString.getPackageName());
        sendBroadcastAsUser(intent, Process.myUserHandle());
    }

    @Override // android.view.Window.Callback
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
        if (menu == null) {
            return;
        }
        int size = menu.size();
        KeyboardShortcutGroup keyboardShortcutGroup = null;
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = menu.getItem(i2);
            CharSequence title = item.getTitle();
            char alphabeticShortcut = item.getAlphabeticShortcut();
            int alphabeticModifiers = item.getAlphabeticModifiers();
            if (title != null && alphabeticShortcut != 0) {
                if (keyboardShortcutGroup == null) {
                    int i3 = this.mApplication.getApplicationInfo().labelRes;
                    keyboardShortcutGroup = new KeyboardShortcutGroup(i3 != 0 ? getString(i3) : null);
                }
                keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(title, alphabeticShortcut, alphabeticModifiers));
            }
        }
        if (keyboardShortcutGroup != null) {
            list.add(keyboardShortcutGroup);
        }
    }

    public boolean showAssist(Bundle bundle) {
        return ActivityClient.getInstance().showAssistFromActivity(this.mToken, bundle);
    }

    protected void onStop() {
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(false);
        }
        this.mActivityTransitionState.onStop(this);
        dispatchActivityStopped();
        this.mTranslucentCallback = null;
        this.mCalled = true;
        getAutofillClientController().onActivityStopped(this.mIntent, this.mChangingConfigurations);
        notifyVoiceInteractionManagerServiceActivityEvent(4);
    }

    protected void onDestroy() {
        this.mCalled = true;
        getAutofillClientController().onActivityDestroyed();
        SparseArray<ManagedDialog> sparseArray = this.mManagedDialogs;
        if (sparseArray != null) {
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                ManagedDialog valueAt = this.mManagedDialogs.valueAt(i);
                if (valueAt.mDialog.isShowing()) {
                    valueAt.mDialog.dismiss();
                }
            }
            this.mManagedDialogs = null;
        }
        synchronized (this.mManagedCursors) {
            int size2 = this.mManagedCursors.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ManagedCursor managedCursor = this.mManagedCursors.get(i2);
                if (managedCursor != null) {
                    managedCursor.mCursor.close();
                }
            }
            this.mManagedCursors.clear();
        }
        SearchManager searchManager = this.mSearchManager;
        if (searchManager != null) {
            searchManager.stopSearch();
        }
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.onDestroy();
        }
        dispatchActivityDestroyed();
        notifyContentCaptureManagerIfNeeded(4);
        UiTranslationController uiTranslationController = this.mUiTranslationController;
        if (uiTranslationController != null) {
            uiTranslationController.onActivityDestroyed();
        }
        if (this.mDefaultBackCallback != null) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mDefaultBackCallback);
            this.mDefaultBackCallback = null;
        }
        ComponentCallbacksController componentCallbacksController = this.mCallbacksController;
        if (componentCallbacksController != null) {
            componentCallbacksController.clearCallbacks();
        }
    }

    public void reportFullyDrawn() {
        if (this.mDoReportFullyDrawn) {
            if (Trace.isTagEnabled(64L)) {
                Trace.traceBegin(64L, "reportFullyDrawn() for " + this.mComponent.toShortString());
            }
            this.mDoReportFullyDrawn = false;
            try {
                ActivityClient.getInstance().reportActivityFullyDrawn(this.mToken, this.mRestoredFromBundle);
                VMRuntime.getRuntime().notifyStartupCompleted();
            } finally {
                Trace.traceEnd(64L);
            }
        }
    }

    public void onMultiWindowModeChanged(boolean z, Configuration configuration) {
        onMultiWindowModeChanged(z);
    }

    public boolean isInMultiWindowMode() {
        return this.mIsInMultiWindowMode;
    }

    public void onPictureInPictureModeChanged(boolean z, Configuration configuration) {
        onPictureInPictureModeChanged(z);
    }

    public boolean isInPictureInPictureMode() {
        return this.mIsInPictureInPictureMode;
    }

    @Deprecated
    public void enterPictureInPictureMode() {
        enterPictureInPictureMode(new PictureInPictureParams.Builder().build());
    }

    public boolean enterPictureInPictureMode(PictureInPictureParams pictureInPictureParams) {
        if (!deviceSupportsPictureInPictureMode()) {
            return false;
        }
        if (pictureInPictureParams == null) {
            throw new IllegalArgumentException("Expected non-null picture-in-picture params");
        }
        if (!this.mCanEnterPictureInPicture) {
            if (isTvImplicitEnterPipProhibited()) {
                Log.e(TAG, "Activity must be resumed to enter picture-in-picture and not about to be paused. Implicit app entry is only permitted on TV if android.permission.TV_IMPLICIT_ENTER_PIP is held by the app.");
                return false;
            }
            throw new IllegalStateException("Activity must be resumed to enter picture-in-picture");
        }
        boolean enterPictureInPictureMode = ActivityClient.getInstance().enterPictureInPictureMode(this.mToken, pictureInPictureParams);
        this.mIsInPictureInPictureMode = enterPictureInPictureMode;
        return enterPictureInPictureMode;
    }

    public void setPictureInPictureParams(PictureInPictureParams pictureInPictureParams) {
        if (deviceSupportsPictureInPictureMode()) {
            if (pictureInPictureParams == null) {
                throw new IllegalArgumentException("Expected non-null picture-in-picture params");
            }
            ActivityClient.getInstance().setPictureInPictureParams(this.mToken, pictureInPictureParams);
        }
    }

    public int getMaxNumPictureInPictureActions() {
        return ActivityTaskManager.getMaxNumPictureInPictureActions(this);
    }

    private boolean isTvImplicitEnterPipProhibited() {
        PackageManager packageManager = getPackageManager();
        return Flags.enableTvImplicitEnterPipRestriction() && packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK) && packageManager.checkPermission(Manifest.permission.TV_IMPLICIT_ENTER_PIP, getPackageName()) == -1;
    }

    private boolean deviceSupportsPictureInPictureMode() {
        if (MultiWindowCoreState.MW_ENABLED) {
            return getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE);
        }
        return false;
    }

    public void requestFullscreenMode(int i, OutcomeReceiver<Void, Throwable> outcomeReceiver) {
        FullscreenRequestHandler.requestFullscreenMode(i, outcomeReceiver, this.mCurrentConfig, getActivityToken());
    }

    public void setShouldDockBigOverlays(boolean z) {
        ActivityClient.getInstance().setShouldDockBigOverlays(this.mToken, z);
        this.mShouldDockBigOverlays = z;
    }

    public boolean shouldDockBigOverlays() {
        return this.mShouldDockBigOverlays;
    }

    void dispatchMovedToDisplay(int i, Configuration configuration) {
        updateDisplay(i);
        onMovedToDisplay(i, configuration);
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
        boolean isPopOver = configuration.windowConfiguration.isPopOver();
        if (this.mIsPopOver != isPopOver) {
            this.mIsPopOver = isPopOver;
            this.mInOutsideTouch = false;
            if (this.mInOutsideLongPress) {
                this.mInOutsideLongPress = false;
                clearTransparentPopOver();
            }
        }
        this.mFragments.dispatchConfigurationChanged(configuration);
        Window window = this.mWindow;
        if (window != null) {
            window.onConfigurationChanged(configuration);
        }
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.onConfigurationChanged(configuration);
        }
        dispatchActivityConfigurationChanged();
        ComponentCallbacksController componentCallbacksController = this.mCallbacksController;
        if (componentCallbacksController != null) {
            componentCallbacksController.dispatchConfigurationChanged(configuration);
        }
    }

    public int getChangingConfigurations() {
        return this.mConfigChangeFlags;
    }

    public Object getLastNonConfigurationInstance() {
        NonConfigurationInstances nonConfigurationInstances = this.mLastNonConfigurationInstances;
        if (nonConfigurationInstances != null) {
            return nonConfigurationInstances.activity;
        }
        return null;
    }

    HashMap<String, Object> getLastNonConfigurationChildInstances() {
        NonConfigurationInstances nonConfigurationInstances = this.mLastNonConfigurationInstances;
        if (nonConfigurationInstances != null) {
            return nonConfigurationInstances.children;
        }
        return null;
    }

    NonConfigurationInstances retainNonConfigurationInstances() {
        Object onRetainNonConfigurationInstance = onRetainNonConfigurationInstance();
        HashMap<String, Object> onRetainNonConfigurationChildInstances = onRetainNonConfigurationChildInstances();
        FragmentManagerNonConfig retainNestedNonConfig = this.mFragments.retainNestedNonConfig();
        this.mFragments.doLoaderStart();
        this.mFragments.doLoaderStop(true);
        ArrayMap<String, LoaderManager> retainLoaderNonConfig = this.mFragments.retainLoaderNonConfig();
        if (onRetainNonConfigurationInstance == null && onRetainNonConfigurationChildInstances == null && retainNestedNonConfig == null && retainLoaderNonConfig == null && this.mVoiceInteractor == null) {
            return null;
        }
        NonConfigurationInstances nonConfigurationInstances = new NonConfigurationInstances();
        nonConfigurationInstances.activity = onRetainNonConfigurationInstance;
        nonConfigurationInstances.children = onRetainNonConfigurationChildInstances;
        nonConfigurationInstances.fragments = retainNestedNonConfig;
        nonConfigurationInstances.loaders = retainLoaderNonConfig;
        VoiceInteractor voiceInteractor = this.mVoiceInteractor;
        if (voiceInteractor != null) {
            voiceInteractor.retainInstance();
            nonConfigurationInstances.voiceInteractor = this.mVoiceInteractor;
        }
        return nonConfigurationInstances;
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCalled = true;
        this.mFragments.dispatchLowMemory();
        ComponentCallbacksController componentCallbacksController = this.mCallbacksController;
        if (componentCallbacksController != null) {
            componentCallbacksController.dispatchLowMemory();
        }
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        this.mCalled = true;
        this.mFragments.dispatchTrimMemory(i);
        ComponentCallbacksController componentCallbacksController = this.mCallbacksController;
        if (componentCallbacksController != null) {
            componentCallbacksController.dispatchTrimMemory(i);
        }
    }

    @Deprecated
    public FragmentManager getFragmentManager() {
        return this.mFragments.getFragmentManager();
    }

    @Deprecated
    public final Cursor managedQuery(Uri uri, String[] strArr, String str, String str2) {
        Cursor query = getContentResolver().query(uri, strArr, str, null, str2);
        if (query != null) {
            startManagingCursor(query);
        }
        return query;
    }

    @Deprecated
    public final Cursor managedQuery(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Cursor query = getContentResolver().query(uri, strArr, str, strArr2, str2);
        if (query != null) {
            startManagingCursor(query);
        }
        return query;
    }

    @Deprecated
    public void startManagingCursor(Cursor cursor) {
        synchronized (this.mManagedCursors) {
            this.mManagedCursors.add(new ManagedCursor(cursor));
        }
    }

    @Deprecated
    public void stopManagingCursor(Cursor cursor) {
        synchronized (this.mManagedCursors) {
            int size = this.mManagedCursors.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (this.mManagedCursors.get(i).mCursor == cursor) {
                    this.mManagedCursors.remove(i);
                    break;
                }
                i++;
            }
        }
    }

    public <T extends View> T findViewById(int i) {
        return (T) getWindow().findViewById(i);
    }

    public final <T extends View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Activity");
    }

    public ActionBar getActionBar() {
        initWindowDecorActionBar();
        return this.mActionBar;
    }

    public void setActionBar(Toolbar toolbar) {
        ActionBar actionBar = getActionBar();
        if (actionBar instanceof WindowDecorActionBar) {
            throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_ACTION_BAR and set android:windowActionBar to false in your theme to use a Toolbar instead.");
        }
        this.mMenuInflater = null;
        if (actionBar != null) {
            actionBar.onDestroy();
        }
        if (toolbar != null) {
            ToolbarActionBar toolbarActionBar = new ToolbarActionBar(toolbar, getTitle(), this);
            this.mActionBar = toolbarActionBar;
            this.mWindow.setCallback(toolbarActionBar.getWrappedWindowCallback());
        } else {
            this.mActionBar = null;
            this.mWindow.setCallback(this);
        }
        invalidateOptionsMenu();
    }

    private void initWindowDecorActionBar() {
        Window window = getWindow();
        window.getDecorView();
        if (!isChild() && window.hasFeature(8) && this.mActionBar == null) {
            WindowDecorActionBar windowDecorActionBar = new WindowDecorActionBar(this);
            this.mActionBar = windowDecorActionBar;
            windowDecorActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
            this.mWindow.setDefaultIcon(this.mActivityInfo.getIconResource());
            this.mWindow.setDefaultLogo(this.mActivityInfo.getLogoResource());
        }
    }

    private void idsUiUpdated() {
        ActivityThread.currentActivityThread().getIdsController().uiUpdated(3);
    }

    public void setContentView(int i) {
        getWindow().setContentView(i);
        initWindowDecorActionBar();
        idsUiUpdated();
    }

    public void setContentView(View view) {
        getWindow().setContentView(view);
        initWindowDecorActionBar();
        idsUiUpdated();
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        getWindow().setContentView(view, layoutParams);
        initWindowDecorActionBar();
        idsUiUpdated();
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        getWindow().addContentView(view, layoutParams);
        initWindowDecorActionBar();
    }

    public TransitionManager getContentTransitionManager() {
        return getWindow().getTransitionManager();
    }

    public void setContentTransitionManager(TransitionManager transitionManager) {
        getWindow().setTransitionManager(transitionManager);
    }

    public Scene getContentScene() {
        return getWindow().getContentScene();
    }

    public void setFinishOnTouchOutside(boolean z) {
        this.mWindow.setCloseOnTouchOutside(z);
    }

    public final void setDefaultKeyMode(int i) {
        this.mDefaultKeyMode = i;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3 && i != 4) {
                        throw new IllegalArgumentException();
                    }
                }
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            this.mDefaultKeySsb = spannableStringBuilder;
            Selection.setSelection(spannableStringBuilder, 0);
            return;
        }
        this.mDefaultKeySsb = null;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z;
        boolean z2 = true;
        if (i == 4) {
            if (getApplicationInfo().targetSdkVersion >= 5) {
                Slog.d(TAG, "onKeyDown(KEYCODE_BACK), activity=" + this);
                keyEvent.startTracking();
            } else {
                onBackPressed();
            }
            return true;
        }
        if (i == 111 && this.mWindow.shouldCloseOnTouchOutside()) {
            keyEvent.startTracking();
            finish();
            return true;
        }
        int i2 = this.mDefaultKeyMode;
        if (i2 == 0) {
            return false;
        }
        if (i2 == 2) {
            Window window = getWindow();
            return window.hasFeature(0) && window.performPanelShortcut(0, i, keyEvent, 2);
        }
        if (i == 61) {
            return false;
        }
        if (keyEvent.getRepeatCount() != 0 || keyEvent.isSystem()) {
            z = false;
        } else {
            z = TextKeyListener.getInstance().onKeyDown(null, this.mDefaultKeySsb, i, keyEvent);
            if (!z || this.mDefaultKeySsb.length() <= 0) {
                z2 = false;
            } else {
                String spannableStringBuilder = this.mDefaultKeySsb.toString();
                int i3 = this.mDefaultKeyMode;
                if (i3 == 1) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(WebView.SCHEME_TEL + spannableStringBuilder));
                    intent.addFlags(268435456);
                    startActivity(intent);
                } else if (i3 == 3) {
                    startSearch(spannableStringBuilder, false, null, false);
                } else if (i3 == 4) {
                    startSearch(spannableStringBuilder, false, null, true);
                }
            }
        }
        if (z2) {
            this.mDefaultKeySsb.clear();
            this.mDefaultKeySsb.clearSpans();
            Selection.setSelection(this.mDefaultKeySsb, 0);
        }
        return z;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (getApplicationInfo().targetSdkVersion >= 5) {
            if (i == 4 && keyEvent.isTracking() && !keyEvent.isCanceled() && this.mDefaultBackCallback == null) {
                onBackPressed();
                return true;
            }
            if (i == 4) {
                Slog.d(TAG, "onKeyUp(KEYCODE_BACK) isTracking()=" + keyEvent.isTracking() + " isCanceled()=" + keyEvent.isCanceled() + " hasCallback=" + (this.mDefaultBackCallback != null));
            }
        }
        return i == 111 && keyEvent.isTracking();
    }

    private static final class RequestFinishCallback extends IRequestFinishCallback.Stub {
        private final WeakReference<Activity> mActivityRef;

        RequestFinishCallback(WeakReference<Activity> weakReference) {
            this.mActivityRef = weakReference;
        }

        @Override // android.app.IRequestFinishCallback
        public void requestFinish() {
            final Activity activity = this.mActivityRef.get();
            if (activity != null) {
                Handler handler = activity.mHandler;
                Objects.requireNonNull(activity);
                handler.post(new Runnable() { // from class: android.app.Activity$RequestFinishCallback$$ExternalSyntheticLambda0
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
        ActionBar actionBar = this.mActionBar;
        if (actionBar == null || !actionBar.collapseActionView()) {
            FragmentManager fragmentManager = this.mFragments.getFragmentManager();
            if (fragmentManager.isStateSaved() || !fragmentManager.popBackStackImmediate()) {
                onBackInvoked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackInvoked() {
        if (CoreRune.FW_PREDICTIVE_BACK_ANIM_LOG) {
            Slog.d(TAG, "onBackInvoked, activity=" + this + ", caller=" + Debug.getCallers(3));
        }
        ActivityClient.getInstance().onBackPressed(this.mToken, new RequestFinishCallback(new WeakReference(this)));
        if (isTaskRoot()) {
            getAutofillClientController().onActivityBackPressed(this.mIntent);
        }
    }

    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        ActionBar actionBar = getActionBar();
        return actionBar != null && actionBar.onKeyShortcut(i, keyEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mWindow.shouldCloseOnTouch(this, motionEvent)) {
            return false;
        }
        finish();
        return true;
    }

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        View view;
        if (this.mParent != null || (view = this.mDecor) == null || view.getParent() == null) {
            return;
        }
        getWindowManager().updateViewLayout(view, layoutParams);
        ContentCaptureManager contentCaptureManager = this.mContentCaptureManager;
        if (contentCaptureManager != null) {
            contentCaptureManager.updateWindowAttributes(layoutParams);
        }
    }

    public boolean hasWindowFocus() {
        View decorView;
        Window window = getWindow();
        if (window == null || (decorView = window.getDecorView()) == null) {
            return false;
        }
        return decorView.hasWindowFocus();
    }

    @Override // android.view.Window.OnWindowDismissedCallback
    public void onWindowDismissed(boolean z, boolean z2) {
        finish(z ? 2 : 0);
        if (z2) {
            overridePendingTransition(0, 0);
        }
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        ActionBar actionBar;
        onUserInteraction();
        if (keyEvent.getKeyCode() == 82 && (actionBar = this.mActionBar) != null && actionBar.onMenuKeyEvent(keyEvent)) {
            return true;
        }
        Window window = getWindow();
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        View view = this.mDecor;
        if (view == null) {
            view = window.getDecorView();
        }
        return keyEvent.dispatch(this, view != null ? view.getKeyDispatcherState() : null, this);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        onUserInteraction();
        if (getWindow().superDispatchKeyShortcutEvent(keyEvent)) {
            return true;
        }
        return onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            onUserInteraction();
        }
        if (interceptTouchEventForPopOver(motionEvent) || getWindow().superDispatchTouchEvent(motionEvent)) {
            return true;
        }
        return onTouchEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        onUserInteraction();
        if (getWindow().superDispatchTrackballEvent(motionEvent)) {
            return true;
        }
        return onTrackballEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        onUserInteraction();
        if (getWindow().superDispatchGenericMotionEvent(motionEvent)) {
            return true;
        }
        return onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName(getClass().getName());
        accessibilityEvent.setPackageName(getPackageName());
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        accessibilityEvent.setFullScreen(attributes.width == -1 && attributes.height == -1);
        CharSequence title = getTitle();
        if (!TextUtils.isEmpty(title)) {
            accessibilityEvent.getText().add(title);
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int i, Menu menu) {
        if (i != 0) {
            return false;
        }
        return this.mFragments.dispatchCreateOptionsMenu(menu, getMenuInflater()) | onCreateOptionsMenu(menu);
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int i, View view, Menu menu) {
        if (i != 0) {
            return true;
        }
        return this.mFragments.dispatchPrepareOptionsMenu(menu) | onPrepareOptionsMenu(menu);
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int i, Menu menu) {
        if (i == 8) {
            initWindowDecorActionBar();
            ActionBar actionBar = this.mActionBar;
            if (actionBar != null) {
                actionBar.dispatchMenuVisibilityChanged(true);
            } else {
                Log.e(TAG, "Tried to open action bar menu with no action bar");
            }
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        ActionBar actionBar;
        CharSequence titleCondensed = menuItem.getTitleCondensed();
        if (i != 0) {
            if (i != 6) {
                return false;
            }
            if (titleCondensed != null) {
                EventLog.writeEvent(50000, 1, titleCondensed.toString());
            }
            if (onContextItemSelected(menuItem)) {
                return true;
            }
            return this.mFragments.dispatchContextItemSelected(menuItem);
        }
        if (titleCondensed != null) {
            EventLog.writeEvent(50000, 0, titleCondensed.toString());
        }
        if (onOptionsItemSelected(menuItem) || this.mFragments.dispatchOptionsItemSelected(menuItem)) {
            return true;
        }
        if (menuItem.getItemId() != 16908332 || (actionBar = this.mActionBar) == null || (actionBar.getDisplayOptions() & 4) == 0) {
            return false;
        }
        Activity activity = this.mParent;
        if (activity == null) {
            return onNavigateUp();
        }
        return activity.onNavigateUpFromChild(this);
    }

    @Override // android.view.Window.Callback
    public void onPanelClosed(int i, Menu menu) {
        if (i == 0) {
            this.mFragments.dispatchOptionsMenuClosed(menu);
            onOptionsMenuClosed(menu);
        } else if (i == 6) {
            onContextMenuClosed(menu);
        } else {
            if (i != 8) {
                return;
            }
            initWindowDecorActionBar();
            this.mActionBar.dispatchMenuVisibilityChanged(false);
        }
    }

    public void invalidateOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            ActionBar actionBar = this.mActionBar;
            if (actionBar == null || !actionBar.invalidateOptionsMenu()) {
                this.mWindow.invalidatePanelMenu(0);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        Activity activity = this.mParent;
        if (activity != null) {
            return activity.onCreateOptionsMenu(menu);
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        Activity activity = this.mParent;
        if (activity != null) {
            return activity.onPrepareOptionsMenu(menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Activity activity = this.mParent;
        if (activity != null) {
            return activity.onOptionsItemSelected(menuItem);
        }
        return false;
    }

    public boolean onNavigateUp() {
        Intent parentActivityIntent = getParentActivityIntent();
        if (parentActivityIntent == null) {
            return false;
        }
        if (this.mActivityInfo.taskAffinity == null) {
            finish();
            return true;
        }
        if (shouldUpRecreateTask(parentActivityIntent)) {
            TaskStackBuilder create = TaskStackBuilder.create(this);
            onCreateNavigateUpTaskStack(create);
            onPrepareNavigateUpTaskStack(create);
            create.startActivities();
            if (this.mResultCode != 0 || this.mResultData != null) {
                Log.i(TAG, "onNavigateUp only finishing topmost activity to return a result");
                finish();
                return true;
            }
            finishAffinity();
            return true;
        }
        navigateUpTo(parentActivityIntent);
        return true;
    }

    @Deprecated
    public boolean onNavigateUpFromChild(Activity activity) {
        return onNavigateUp();
    }

    public void onCreateNavigateUpTaskStack(TaskStackBuilder taskStackBuilder) {
        taskStackBuilder.addParentStack(this);
    }

    public void onOptionsMenuClosed(Menu menu) {
        Activity activity = this.mParent;
        if (activity != null) {
            activity.onOptionsMenuClosed(menu);
        }
    }

    public void openOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            ActionBar actionBar = this.mActionBar;
            if (actionBar == null || !actionBar.openOptionsMenu()) {
                this.mWindow.openPanel(0, null);
            }
        }
    }

    public void closeOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            ActionBar actionBar = this.mActionBar;
            if (actionBar == null || !actionBar.closeOptionsMenu()) {
                this.mWindow.closePanel(0);
            }
        }
    }

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

    public boolean onContextItemSelected(MenuItem menuItem) {
        Activity activity = this.mParent;
        if (activity != null) {
            return activity.onContextItemSelected(menuItem);
        }
        return false;
    }

    public void onContextMenuClosed(Menu menu) {
        Activity activity = this.mParent;
        if (activity != null) {
            activity.onContextMenuClosed(menu);
        }
    }

    @Deprecated
    protected Dialog onCreateDialog(int i, Bundle bundle) {
        return onCreateDialog(i);
    }

    @Deprecated
    protected void onPrepareDialog(int i, Dialog dialog) {
        dialog.setOwnerActivity(this);
    }

    @Deprecated
    protected void onPrepareDialog(int i, Dialog dialog, Bundle bundle) {
        onPrepareDialog(i, dialog);
    }

    @Deprecated
    public final void showDialog(int i) {
        showDialog(i, null);
    }

    @Deprecated
    public final boolean showDialog(int i, Bundle bundle) {
        if (this.mManagedDialogs == null) {
            this.mManagedDialogs = new SparseArray<>();
        }
        ManagedDialog managedDialog = this.mManagedDialogs.get(i);
        if (managedDialog == null) {
            managedDialog = new ManagedDialog();
            managedDialog.mDialog = createDialog(Integer.valueOf(i), null, bundle);
            if (managedDialog.mDialog == null) {
                return false;
            }
            this.mManagedDialogs.put(i, managedDialog);
        }
        managedDialog.mArgs = bundle;
        onPrepareDialog(i, managedDialog.mDialog, bundle);
        managedDialog.mDialog.show();
        return true;
    }

    @Deprecated
    public final void dismissDialog(int i) {
        SparseArray<ManagedDialog> sparseArray = this.mManagedDialogs;
        if (sparseArray == null) {
            throw missingDialog(i);
        }
        ManagedDialog managedDialog = sparseArray.get(i);
        if (managedDialog == null) {
            throw missingDialog(i);
        }
        managedDialog.mDialog.dismiss();
    }

    private IllegalArgumentException missingDialog(int i) {
        return new IllegalArgumentException("no dialog with id " + i + " was ever shown via Activity#showDialog");
    }

    @Deprecated
    public final void removeDialog(int i) {
        ManagedDialog managedDialog;
        SparseArray<ManagedDialog> sparseArray = this.mManagedDialogs;
        if (sparseArray == null || (managedDialog = sparseArray.get(i)) == null) {
            return;
        }
        managedDialog.mDialog.dismiss();
        this.mManagedDialogs.remove(i);
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested(SearchEvent searchEvent) {
        this.mSearchEvent = searchEvent;
        boolean onSearchRequested = onSearchRequested();
        this.mSearchEvent = null;
        return onSearchRequested;
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        int i = getResources().getConfiguration().uiMode & 15;
        if (i == 4 || i == 6) {
            return false;
        }
        startSearch(null, false, null, false);
        return true;
    }

    public final SearchEvent getSearchEvent() {
        return this.mSearchEvent;
    }

    public void startSearch(String str, boolean z, Bundle bundle, boolean z2) {
        ensureSearchManager();
        this.mSearchManager.startSearch(str, z, getComponentName(), bundle, z2);
    }

    public void triggerSearch(String str, Bundle bundle) {
        ensureSearchManager();
        this.mSearchManager.triggerSearch(str, getComponentName(), bundle);
    }

    public void takeKeyEvents(boolean z) {
        getWindow().takeKeyEvents(z);
    }

    public final boolean requestWindowFeature(int i) {
        return getWindow().requestFeature(i);
    }

    public final void setFeatureDrawableResource(int i, int i2) {
        getWindow().setFeatureDrawableResource(i, i2);
    }

    public final void setFeatureDrawableUri(int i, Uri uri) {
        getWindow().setFeatureDrawableUri(i, uri);
    }

    public final void setFeatureDrawable(int i, Drawable drawable) {
        getWindow().setFeatureDrawable(i, drawable);
    }

    public final void setFeatureDrawableAlpha(int i, int i2) {
        getWindow().setFeatureDrawableAlpha(i, i2);
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

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public void setTheme(int i) {
        super.setTheme(i);
        this.mWindow.setTheme(i);
    }

    @Override // android.view.ContextThemeWrapper
    protected void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        Window window;
        int color;
        Activity activity = this.mParent;
        if (activity == null) {
            super.onApplyThemeResource(theme, i, z);
        } else {
            try {
                theme.setTo(activity.getTheme());
            } catch (Exception unused) {
            }
            theme.applyStyle(i, false);
        }
        TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(R.styleable.ActivityTaskDescription);
        if (this.mTaskDescription.getPrimaryColor() == 0 && (color = obtainStyledAttributes.getColor(1, 0)) != 0 && Color.alpha(color) == 255) {
            this.mTaskDescription.setPrimaryColor(color);
        }
        int color2 = obtainStyledAttributes.getColor(0, 0);
        if (color2 != 0 && Color.alpha(color2) == 255) {
            this.mTaskDescription.setBackgroundColor(color2);
        }
        int color3 = obtainStyledAttributes.getColor(4, 0);
        if (color3 != 0 && Color.alpha(color3) == 255) {
            this.mTaskDescription.setBackgroundColorFloating(color3);
        }
        int color4 = obtainStyledAttributes.getColor(2, 0);
        if (color4 != 0) {
            this.mTaskDescription.setStatusBarColor(color4);
        }
        int color5 = obtainStyledAttributes.getColor(3, 0);
        if (color5 != 0) {
            this.mTaskDescription.setNavigationBarColor(color5);
        }
        if (getApplicationInfo().targetSdkVersion >= 29) {
            this.mTaskDescription.setEnsureStatusBarContrastWhenTransparent(obtainStyledAttributes.getBoolean(5, false));
            this.mTaskDescription.setEnsureNavigationBarContrastWhenTransparent(obtainStyledAttributes.getBoolean(6, true));
        }
        obtainStyledAttributes.recycle();
        if (z && this.mTaskDescription.getSystemBarsAppearance() == 0 && (window = this.mWindow) != null && window.getSystemBarAppearance() != 0) {
            this.mTaskDescription.setSystemBarsAppearance(this.mWindow.getSystemBarAppearance());
        }
        setTaskDescription(this.mTaskDescription);
    }

    public final void requestPermissions(String[] strArr, int i) {
        requestPermissions(strArr, i, getDeviceId());
    }

    public final void requestPermissions(final String[] strArr, final int i, final int i2) {
        if (getApplicationInfo().targetSdkVersion < 23) {
            onRequestPermissionsResult(i, new String[0], new int[0], i2);
        }
        if (i < 0) {
            throw new IllegalArgumentException("requestCode should be >= 0");
        }
        if (this.mHasCurrentPermissionsRequest) {
            Log.w(TAG, "Can request only one set of permissions at a time");
            onRequestPermissionsResult(i, new String[0], new int[0], i2);
            return;
        }
        if (!getAttributionSource().getRenouncedPermissions().isEmpty()) {
            for (int i3 = 0; i3 < strArr.length; i3++) {
                if (getAttributionSource().getRenouncedPermissions().contains(strArr[i3])) {
                    throw new IllegalArgumentException("Cannot request renounced permission: " + strArr[i3]);
                }
            }
        }
        Context createDeviceContext = getDeviceId() == i2 ? this : createDeviceContext(i2);
        if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.permissionRequestShortCircuitEnabled()) {
            int[] permissionRequestStates = getPermissionRequestStates(createDeviceContext, strArr);
            for (int i4 : permissionRequestStates) {
                if (i4 != 1) {
                }
            }
            this.mHasCurrentPermissionsRequest = true;
            Log.v(TAG, "No requestable permission in the request.");
            final int[] iArr = new int[permissionRequestStates.length];
            for (int i5 = 0; i5 < permissionRequestStates.length; i5++) {
                if (permissionRequestStates[i5] == 0) {
                    iArr[i5] = 0;
                } else {
                    iArr[i5] = -1;
                }
            }
            this.mHandler.post(new Runnable() { // from class: android.app.Activity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Activity.this.lambda$requestPermissions$0(i, strArr, iArr, i2);
                }
            });
            return;
        }
        startActivityForResult(REQUEST_PERMISSIONS_WHO_PREFIX, createDeviceContext.getPackageManager().buildRequestPermissionsIntent(strArr), i, null);
        this.mHasCurrentPermissionsRequest = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestPermissions$0(int i, String[] strArr, int[] iArr, int i2) {
        this.mHasCurrentPermissionsRequest = false;
        onRequestPermissionsResult(i, strArr, iArr, i2);
    }

    private int[] getPermissionRequestStates(Context context, String[] strArr) {
        int length = strArr.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            if (str == null) {
                iArr[i] = 2;
            } else {
                iArr[i] = context.getPermissionRequestState(str);
            }
        }
        return iArr;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr, int i2) {
        onRequestPermissionsResult(i, strArr, iArr);
    }

    public boolean shouldShowRequestPermissionRationale(String str) {
        return getPackageManager().shouldShowRequestPermissionRationale(str);
    }

    public boolean shouldShowRequestPermissionRationale(String str, int i) {
        return (getDeviceId() == i ? getPackageManager() : createDeviceContext(i).getPackageManager()).shouldShowRequestPermissionRationale(str);
    }

    public void startActivityForResult(Intent intent, int i) {
        startActivityForResult(intent, i, null);
    }

    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        Activity activity = this.mParent;
        if (activity != null) {
            if (bundle != null) {
                activity.startActivityFromChild(this, intent, i, bundle);
                return;
            } else {
                activity.startActivityFromChild(this, intent, i);
                return;
            }
        }
        Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
        Instrumentation.ActivityResult execStartActivity = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, this, intent, i, transferSpringboardActivityOptions);
        if (execStartActivity != null) {
            this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, i, execStartActivity.getResultCode(), execStartActivity.getResultData());
        }
        if (i >= 0) {
            this.mStartedActivity = true;
        }
        cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
    }

    private void cancelInputsAndStartExitTransition(Bundle bundle) {
        Window window = this.mWindow;
        View peekDecorView = window != null ? window.peekDecorView() : null;
        if (peekDecorView != null) {
            peekDecorView.cancelPendingInputEvents();
        }
        if (bundle != null) {
            this.mActivityTransitionState.startExitOutTransition(this, bundle);
        }
    }

    public boolean isActivityTransitionRunning() {
        return this.mActivityTransitionState.isTransitionRunning();
    }

    private Bundle transferSpringboardActivityOptions(Bundle bundle) {
        Window window;
        ActivityOptions.SceneTransitionInfo sceneTransitionInfo;
        return (bundle != null || (window = this.mWindow) == null || window.isActive() || (sceneTransitionInfo = getSceneTransitionInfo()) == null) ? bundle : ActivityOptions.makeBasic().setSceneTransitionInfo(sceneTransitionInfo).toBundle();
    }

    @SystemApi
    public void startActivityForResultAsUser(Intent intent, int i, UserHandle userHandle) {
        startActivityForResultAsUser(intent, i, null, userHandle);
    }

    @SystemApi
    public void startActivityForResultAsUser(Intent intent, int i, Bundle bundle, UserHandle userHandle) {
        startActivityForResultAsUser(intent, this.mEmbeddedID, i, bundle, userHandle);
    }

    @SystemApi
    public void startActivityForResultAsUser(Intent intent, String str, int i, Bundle bundle, UserHandle userHandle) {
        if (this.mParent != null) {
            throw new RuntimeException("Can't be called from a child");
        }
        Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
        Instrumentation.ActivityResult execStartActivity = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, str, intent, i, transferSpringboardActivityOptions, userHandle);
        if (execStartActivity != null) {
            this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, i, execStartActivity.getResultCode(), execStartActivity.getResultData());
        }
        if (i >= 0) {
            this.mStartedActivity = true;
        }
        cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivityAsUser(Intent intent, UserHandle userHandle) {
        startActivityAsUser(intent, null, userHandle);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivityAsUser(Intent intent, Bundle bundle, UserHandle userHandle) {
        if (this.mParent != null) {
            throw new RuntimeException("Can't be called from a child");
        }
        Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
        Instrumentation.ActivityResult execStartActivity = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, this.mEmbeddedID, intent, -1, transferSpringboardActivityOptions, userHandle);
        if (execStartActivity != null) {
            this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, -1, execStartActivity.getResultCode(), execStartActivity.getResultData());
        }
        cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
    }

    public void startActivityAsCaller(Intent intent, Bundle bundle, boolean z, int i) {
        startActivityAsCaller(intent, bundle, z, i, -1);
    }

    public void startActivityAsCaller(Intent intent, Bundle bundle, boolean z, int i, int i2) {
        if (this.mParent != null) {
            throw new RuntimeException("Can't be called from a child");
        }
        Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
        Instrumentation.ActivityResult execStartActivityAsCaller = this.mInstrumentation.execStartActivityAsCaller(this, this.mMainThread.getApplicationThread(), this.mToken, this, intent, i2, transferSpringboardActivityOptions, z, i);
        if (execStartActivityAsCaller != null) {
            this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, i2, execStartActivityAsCaller.getResultCode(), execStartActivityAsCaller.getResultData());
        }
        cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4) throws IntentSender.SendIntentException {
        startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, (Bundle) null);
    }

    public void startIntentSenderForResult(IntentSender intentSender, String str, int i, Intent intent, int i2, int i3, Bundle bundle) throws IntentSender.SendIntentException {
        startIntentSenderForResultInner(intentSender, str, i, intent, i2, i3, bundle);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) throws IntentSender.SendIntentException {
        Activity activity = this.mParent;
        if (activity == null) {
            startIntentSenderForResultInner(intentSender, this.mEmbeddedID, i, intent, i2, i3, bundle);
        } else if (bundle != null) {
            activity.startIntentSenderFromChild(this, intentSender, i, intent, i2, i3, i4, bundle);
        } else {
            activity.startIntentSenderFromChild(this, intentSender, i, intent, i2, i3, i4);
        }
    }

    public void startIntentSenderForResultInner(IntentSender intentSender, String str, int i, Intent intent, int i2, int i3, Bundle bundle) throws IntentSender.SendIntentException {
        Bundle transferSpringboardActivityOptions;
        String str2;
        int startActivityIntentSender;
        try {
            transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
            if (intent != null) {
                intent.migrateExtraStreamToClipData(this);
                intent.prepareToLeaveProcess(this);
                str2 = intent.resolveTypeIfNeeded(getContentResolver());
            } else {
                str2 = null;
            }
            startActivityIntentSender = ActivityTaskManager.getService().startActivityIntentSender(this.mMainThread.getApplicationThread(), intentSender != null ? intentSender.getTarget() : null, intentSender != null ? intentSender.getWhitelistToken() : null, intent, str2, this.mToken, str, i, i2, i3, transferSpringboardActivityOptions);
        } catch (RemoteException unused) {
        }
        if (startActivityIntentSender == -96) {
            throw new IntentSender.SendIntentException();
        }
        Instrumentation.checkStartActivityResult(startActivityIntentSender, null);
        if (transferSpringboardActivityOptions != null) {
            cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
        }
        if (i >= 0) {
            this.mStartedActivity = true;
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent) {
        startActivity(intent, null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent, Bundle bundle) {
        getAutofillClientController().onStartActivity(intent, this.mIntent);
        if (bundle != null) {
            startActivityForResult(intent, -1, bundle);
        } else {
            startActivityForResult(intent, -1);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivities(Intent[] intentArr) {
        startActivities(intentArr, null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivities(Intent[] intentArr, Bundle bundle) {
        this.mInstrumentation.execStartActivities(this, this.mMainThread.getApplicationThread(), this.mToken, this, intentArr, bundle);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startIntentSender(IntentSender intentSender, Intent intent, int i, int i2, int i3) throws IntentSender.SendIntentException {
        startIntentSender(intentSender, intent, i, i2, i3, null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startIntentSender(IntentSender intentSender, Intent intent, int i, int i2, int i3, Bundle bundle) throws IntentSender.SendIntentException {
        if (bundle != null) {
            startIntentSenderForResult(intentSender, -1, intent, i, i2, i3, bundle);
        } else {
            startIntentSenderForResult(intentSender, -1, intent, i, i2, i3);
        }
    }

    public boolean startActivityIfNeeded(Intent intent, int i) {
        return startActivityIfNeeded(intent, i, null);
    }

    public boolean startActivityIfNeeded(Intent intent, int i, Bundle bundle) {
        Bundle bundle2;
        int i2;
        if (Instrumentation.DEBUG_START_ACTIVITY) {
            StringBuilder sb = new StringBuilder("startActivity: intent=");
            sb.append(intent);
            sb.append(" requestCode=");
            sb.append(i);
            sb.append(" options=");
            bundle2 = bundle;
            sb.append(bundle2);
            Log.d(Instrumentation.TAG, sb.toString(), new Throwable());
        } else {
            bundle2 = bundle;
        }
        if (this.mParent == null) {
            try {
                Uri onProvideReferrer = onProvideReferrer();
                if (onProvideReferrer != null) {
                    intent.putExtra(Intent.EXTRA_REFERRER, onProvideReferrer);
                }
                intent.migrateExtraStreamToClipData(this);
                intent.prepareToLeaveProcess(this);
                i2 = ActivityTaskManager.getService().startActivity(this.mMainThread.getApplicationThread(), getOpPackageName(), getAttributionTag(), intent, intent.resolveTypeIfNeeded(getContentResolver()), this.mToken, this.mEmbeddedID, i, 1, null, bundle2);
            } catch (RemoteException unused) {
                i2 = 1;
            }
            Instrumentation.checkStartActivityResult(i2, intent);
            if (i >= 0) {
                this.mStartedActivity = true;
            }
            return i2 != 1;
        }
        throw new UnsupportedOperationException("startActivityIfNeeded can only be called from a top-level activity");
    }

    public boolean startNextMatchingActivity(Intent intent) {
        return startNextMatchingActivity(intent, null);
    }

    public boolean startNextMatchingActivity(Intent intent, Bundle bundle) {
        if (this.mParent == null) {
            try {
                intent.migrateExtraStreamToClipData(this);
                intent.prepareToLeaveProcess(this);
                return ActivityTaskManager.getService().startNextMatchingActivity(this.mToken, intent, bundle);
            } catch (RemoteException unused) {
                return false;
            }
        }
        throw new UnsupportedOperationException("startNextMatchingActivity can only be called from a top-level activity");
    }

    @Deprecated
    public void startActivityFromChild(Activity activity, Intent intent, int i) {
        startActivityFromChild(activity, intent, i, null);
    }

    @Deprecated
    public void startActivityFromChild(Activity activity, Intent intent, int i, Bundle bundle) {
        Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
        Instrumentation.ActivityResult execStartActivity = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, activity, intent, i, transferSpringboardActivityOptions);
        if (execStartActivity != null) {
            this.mMainThread.sendActivityResult(this.mToken, activity.mEmbeddedID, i, execStartActivity.getResultCode(), execStartActivity.getResultData());
        }
        cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
    }

    @Deprecated
    public void startActivityFromFragment(Fragment fragment, Intent intent, int i) {
        startActivityFromFragment(fragment, intent, i, null);
    }

    @Deprecated
    public void startActivityFromFragment(Fragment fragment, Intent intent, int i, Bundle bundle) {
        startActivityForResult(fragment.mWho, intent, i, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startActivityAsUserFromFragment(Fragment fragment, Intent intent, int i, Bundle bundle, UserHandle userHandle) {
        startActivityForResultAsUser(intent, fragment.mWho, i, bundle, userHandle);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivityForResult(String str, Intent intent, int i, Bundle bundle) {
        Uri onProvideReferrer = onProvideReferrer();
        if (onProvideReferrer != null) {
            intent.putExtra(Intent.EXTRA_REFERRER, onProvideReferrer);
        }
        Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
        Instrumentation.ActivityResult execStartActivity = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, str, intent, i, transferSpringboardActivityOptions);
        if (execStartActivity != null) {
            this.mMainThread.sendActivityResult(this.mToken, str, i, execStartActivity.getResultCode(), execStartActivity.getResultData());
        }
        cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
    }

    @Deprecated
    public void startIntentSenderFromChild(Activity activity, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4) throws IntentSender.SendIntentException {
        startIntentSenderFromChild(activity, intentSender, i, intent, i2, i3, i4, null);
    }

    @Deprecated
    public void startIntentSenderFromChild(Activity activity, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) throws IntentSender.SendIntentException {
        startIntentSenderForResultInner(intentSender, activity.mEmbeddedID, i, intent, i2, i3, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int i, Intent intent, int i2, int i3, Bundle bundle) throws IntentSender.SendIntentException {
        startIntentSenderForResultInner(intentSender, fragment.mWho, i, intent, i2, i3, bundle);
    }

    public void overrideActivityTransition(int i, int i2, int i3) {
        overrideActivityTransition(i, i2, i3, 0);
    }

    public void overrideActivityTransition(int i, int i2, int i3, int i4) {
        boolean z;
        IBinder iBinder;
        int i5;
        int i6;
        int i7;
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Override type must be either open or close");
        }
        ActivityClient activityClient = ActivityClient.getInstance();
        IBinder iBinder2 = this.mToken;
        if (i == 0) {
            z = true;
            i5 = i2;
            i6 = i3;
            i7 = i4;
            iBinder = iBinder2;
        } else {
            z = false;
            iBinder = iBinder2;
            i5 = i2;
            i6 = i3;
            i7 = i4;
        }
        activityClient.overrideActivityTransition(iBinder, z, i5, i6, i7);
    }

    public void clearOverrideActivityTransition(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Override type must be either open or close");
        }
        ActivityClient.getInstance().clearOverrideActivityTransition(this.mToken, i == 0);
    }

    public void semAdjustPopOverOptions(int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3) {
        if (iArr == null || iArr.length == 2) {
            if (iArr2 == null || iArr2.length == 2) {
                if (pointArr == null || pointArr.length == 2) {
                    if (iArr3 == null || iArr3.length == 2) {
                        ActivityClient.getInstance().adjustPopOverOptions(this.mToken, iArr, iArr2, pointArr, iArr3);
                    }
                }
            }
        }
    }

    @Deprecated
    public void overridePendingTransition(int i, int i2) {
        overridePendingTransition(i, i2, 0);
    }

    public void semOverridePendingTransition(int i, int i2) {
        ActivityClient.getInstance().overridePendingTaskTransition(this.mToken, getPackageName(), i, i2);
    }

    @Deprecated
    public void overridePendingTransition(int i, int i2, int i3) {
        ActivityClient.getInstance().overridePendingTransition(this.mToken, getPackageName(), i, i2, i3);
    }

    public final void setResult(int i) {
        synchronized (this) {
            this.mResultCode = i;
            this.mResultData = null;
        }
    }

    public final void setForceSendResultForMediaProjection() {
        ActivityClient.getInstance().setForceSendResultForMediaProjection(this.mToken);
    }

    public final void setResult(int i, Intent intent) {
        synchronized (this) {
            this.mResultCode = i;
            this.mResultData = intent;
        }
    }

    public Uri getReferrer() {
        if (CoreRune.SYSFW_APP_SPEG && SPEG_PACKAGE_NAME.equals(this.mReferrer)) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            ResolveInfo resolveActivity = getPackageManager().resolveActivity(intent, 65536);
            if (resolveActivity == null) {
                Log.w("SPEG", "resolveInfo is null");
                return null;
            }
            Log.d("SPEG", "Pretend to be the default launcher");
            return new Uri.Builder().scheme("android-app").authority(resolveActivity.activityInfo.packageName).build();
        }
        Intent intent2 = getIntent();
        if (intent2 != null) {
            try {
                Uri uri = (Uri) intent2.getParcelableExtra(Intent.EXTRA_REFERRER, Uri.class);
                if (uri != null) {
                    return uri;
                }
                String stringExtra = intent2.getStringExtra(Intent.EXTRA_REFERRER_NAME);
                if (stringExtra != null) {
                    return Uri.parse(stringExtra);
                }
            } catch (BadParcelableException unused) {
                Log.w(TAG, "Cannot read referrer from intent; intent extras contain unknown custom Parcelable objects");
            }
        }
        if (this.mReferrer != null) {
            return new Uri.Builder().scheme("android-app").authority(this.mReferrer).build();
        }
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
        ComponentCaller componentCaller = this.mCurrentCaller;
        if (componentCaller != null) {
            return componentCaller;
        }
        throw new IllegalStateException("The caller is null because #getCurrentCaller should be called within #onNewIntent or #onActivityResult methods");
    }

    public void setVisible(boolean z) {
        if (this.mVisibleFromClient != z) {
            this.mVisibleFromClient = z;
            if (this.mVisibleFromServer) {
                if (z) {
                    makeVisible();
                } else {
                    this.mDecor.setVisibility(4);
                }
            }
        }
    }

    void makeVisible() {
        if (!this.mWindowAdded) {
            getWindowManager().addView(this.mDecor, getWindow().getAttributes());
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

    private void finish(int i) {
        int i2;
        Intent intent;
        if (Instrumentation.DEBUG_FINISH_ACTIVITY) {
            Log.d(Instrumentation.TAG, "finishActivity: finishTask=" + i, new Throwable());
        }
        Activity activity = this.mParent;
        if (activity == null) {
            synchronized (this) {
                i2 = this.mResultCode;
                intent = this.mResultData;
            }
            if (intent != null) {
                intent.prepareToLeaveProcess(this);
            }
            if (ActivityClient.getInstance().finishActivity(this.mToken, i2, intent, i)) {
                this.mFinished = true;
            }
        } else {
            activity.finishFromChild(this);
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
    public void finishFromChild(Activity activity) {
        finish();
    }

    public void finishAfterTransition() {
        if (this.mActivityTransitionState.startExitBackTransition(this)) {
            return;
        }
        finish();
    }

    public void finishActivity(int i) {
        Activity activity = this.mParent;
        if (activity == null) {
            ActivityClient.getInstance().finishSubActivity(this.mToken, this.mEmbeddedID, i);
        } else {
            activity.finishActivityFromChild(this, i);
        }
    }

    @Deprecated
    public void finishActivityFromChild(Activity activity, int i) {
        ActivityClient.getInstance().finishSubActivity(this.mToken, activity.mEmbeddedID, i);
    }

    public void finishAndRemoveTask() {
        finish(1);
    }

    public boolean releaseInstance() {
        return ActivityClient.getInstance().releaseActivityInstance(this.mToken);
    }

    public void onActivityResult(int i, int i2, Intent intent, ComponentCaller componentCaller) {
        onActivityResult(i, i2, intent);
    }

    public PendingIntent createPendingResult(int i, Intent intent, int i2) {
        String packageName = getPackageName();
        try {
            intent.prepareToLeaveProcess(this);
            IActivityManager service = ActivityManager.getService();
            String attributionTag = getAttributionTag();
            Activity activity = this.mParent;
            IIntentSender intentSenderWithFeature = service.getIntentSenderWithFeature(3, packageName, attributionTag, activity == null ? this.mToken : activity.mToken, this.mEmbeddedID, i, new Intent[]{intent}, null, i2, null, getUserId());
            if (intentSenderWithFeature != null) {
                return new PendingIntent(intentSenderWithFeature);
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    public void setRequestedOrientation(int i) {
        if (i == this.mLastRequestedOrientation) {
            return;
        }
        Activity activity = this.mParent;
        if (activity == null) {
            ActivityClient.getInstance().setRequestedOrientation(this.mToken, i);
        } else {
            activity.setRequestedOrientation(i);
        }
        this.mLastRequestedOrientation = i;
    }

    public int getRequestedOrientation() {
        int i = this.mLastRequestedOrientation;
        if (i != -2) {
            return i;
        }
        Activity activity = this.mParent;
        if (activity == null) {
            return ActivityClient.getInstance().getRequestedOrientation(this.mToken);
        }
        return activity.getRequestedOrientation();
    }

    public int getTaskId() {
        return ActivityClient.getInstance().getTaskForActivity(this.mToken, false);
    }

    public boolean isTaskRoot() {
        return this.mWindowControllerCallback.isTaskRoot();
    }

    public boolean moveTaskToBack(boolean z) {
        return ActivityClient.getInstance().moveActivityTaskToBack(this.mToken, z);
    }

    public String getLocalClassName() {
        String packageName = getPackageName();
        String className = this.mComponent.getClassName();
        int length = packageName.length();
        return (className.startsWith(packageName) && className.length() > length && className.charAt(length) == '.') ? className.substring(length + 1) : className;
    }

    public ComponentName getComponentName() {
        return this.mComponent;
    }

    @Override // android.view.contentcapture.ContentCaptureManager.ContentCaptureClient
    public final ComponentName contentCaptureClientGetComponentName() {
        return getComponentName();
    }

    public SharedPreferences getPreferences(int i) {
        return getSharedPreferences(getLocalClassName(), i);
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

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String str) {
        if (getBaseContext() == null) {
            throw new IllegalStateException("System services not available to Activities before onCreate()");
        }
        if (Context.WINDOW_SERVICE.equals(str)) {
            return this.mWindowManager;
        }
        if ("search".equals(str)) {
            ensureSearchManager();
            return this.mSearchManager;
        }
        return super.getSystemService(str);
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        onTitleChanged(charSequence, this.mTitleColor);
        Activity activity = this.mParent;
        if (activity != null) {
            activity.onChildTitleChanged(this, charSequence);
        }
    }

    public void setTitle(int i) {
        setTitle(getText(i));
    }

    @Deprecated
    public void setTitleColor(int i) {
        this.mTitleColor = i;
        onTitleChanged(this.mTitle, i);
    }

    public final CharSequence getTitle() {
        return this.mTitle;
    }

    public final int getTitleColor() {
        return this.mTitleColor;
    }

    protected void onTitleChanged(CharSequence charSequence, int i) {
        if (this.mTitleReady) {
            Window window = getWindow();
            if (window != null) {
                window.setTitle(charSequence);
                if (i != 0) {
                    window.setTitleColor(i);
                }
            }
            ActionBar actionBar = this.mActionBar;
            if (actionBar != null) {
                actionBar.setWindowTitle(charSequence);
            }
        }
    }

    public void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
        ActivityManager.TaskDescription taskDescription2 = this.mTaskDescription;
        if (taskDescription2 != taskDescription) {
            taskDescription2.copyFromPreserveHiddenFields(taskDescription);
            if (taskDescription.getIconFilename() == null && taskDescription.getIcon() != null) {
                int launcherLargeIconSizeInner = ActivityManager.getLauncherLargeIconSizeInner(this);
                this.mTaskDescription.setIcon(Icon.createWithBitmap(Bitmap.createScaledBitmap(taskDescription.getIcon(), launcherLargeIconSizeInner, launcherLargeIconSizeInner, true)));
            }
        }
        if (this.mLastTaskDescriptionHashCode == this.mTaskDescription.hashCode()) {
            return;
        }
        this.mLastTaskDescriptionHashCode = this.mTaskDescription.hashCode();
        ActivityClient.getInstance().setTaskDescription(this.mToken, this.mTaskDescription);
    }

    @Deprecated
    public final void setProgressBarVisibility(boolean z) {
        getWindow().setFeatureInt(2, z ? -1 : -2);
    }

    @Deprecated
    public final void setProgressBarIndeterminateVisibility(boolean z) {
        getWindow().setFeatureInt(5, z ? -1 : -2);
    }

    @Deprecated
    public final void setProgressBarIndeterminate(boolean z) {
        getWindow().setFeatureInt(2, z ? -3 : -4);
    }

    @Deprecated
    public final void setProgress(int i) {
        getWindow().setFeatureInt(2, i);
    }

    @Deprecated
    public final void setSecondaryProgress(int i) {
        getWindow().setFeatureInt(2, i + 20000);
    }

    public final void setVolumeControlStream(int i) {
        getWindow().setVolumeControlStream(i);
    }

    public final int getVolumeControlStream() {
        return getWindow().getVolumeControlStream();
    }

    public final void setMediaController(MediaController mediaController) {
        getWindow().setMediaController(mediaController);
    }

    public final MediaController getMediaController() {
        return getWindow().getMediaController();
    }

    public final void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != this.mUiThread) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        if (!"fragment".equals(str)) {
            return onCreateView(str, context, attributeSet);
        }
        return this.mFragments.onCreateView(view, str, context, attributeSet);
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        dumpInner(str, fileDescriptor, printWriter, strArr);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public final boolean addDumpable(Dumpable dumpable) {
        if (this.mDumpableContainer == null) {
            this.mDumpableContainer = new DumpableContainerImpl();
        }
        return this.mDumpableContainer.addDumpable(dumpable);
    }

    public void dumpInternal(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str2;
        AutofillClientController autofillClientController = this.mAutofillClientController;
        if (autofillClientController != null) {
            addDumpable(autofillClientController);
        }
        UiTranslationController uiTranslationController = this.mUiTranslationController;
        if (uiTranslationController != null) {
            addDumpable(uiTranslationController);
        }
        ContentCaptureManager contentCaptureManager = this.mContentCaptureManager;
        if (contentCaptureManager != null) {
            contentCaptureManager.addDumpable(this);
        }
        boolean z = true;
        if (strArr == null || strArr.length <= 0) {
            str2 = null;
        } else {
            str2 = strArr[0];
            str2.hashCode();
            switch (str2) {
                case "--translation":
                    dumpLegacyDumpable(str, printWriter, str2, UiTranslationController.DUMPABLE_NAME);
                    return;
                case "--dump-dumpable":
                    if (strArr.length == 1) {
                        printWriter.print(DUMP_ARG_DUMP_DUMPABLE);
                        printWriter.println(" requires the dumpable name");
                    } else if (this.mDumpableContainer == null) {
                        printWriter.println("no dumpables");
                    } else {
                        int length = strArr.length - 2;
                        String[] strArr2 = new String[length];
                        System.arraycopy(strArr, 2, strArr2, 0, length);
                        this.mDumpableContainer.dumpOneDumpable(str, printWriter, strArr[1], strArr2);
                    }
                    z = true ^ CompatChanges.isChangeEnabled(DUMP_IGNORES_SPECIAL_ARGS);
                    break;
                case "--list-dumpables":
                    DumpableContainerImpl dumpableContainerImpl = this.mDumpableContainer;
                    if (dumpableContainerImpl == null) {
                        printWriter.print(str);
                        printWriter.println("No dumpables");
                        return;
                    } else {
                        dumpableContainerImpl.listDumpables(str, printWriter);
                        return;
                    }
                case "--contentcapture":
                    dumpLegacyDumpable(str, printWriter, str2, ContentCaptureManager.DUMPABLE_NAME);
                    return;
                case "--autofill":
                    dumpLegacyDumpable(str, printWriter, str2, AutofillClientController.DUMPABLE_NAME);
                    return;
            }
        }
        if (z) {
            dump(str, fileDescriptor, printWriter, strArr);
            return;
        }
        Log.i(TAG, "Not calling dump() on " + this + " because of special argument " + str2);
    }

    void dumpInner(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str2 = str + "  ";
        printWriter.print(str);
        printWriter.print("Local Activity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        printWriter.print(str2);
        printWriter.print("mResumed=");
        printWriter.print(this.mResumed);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mFinished=");
        printWriter.println(this.mFinished);
        printWriter.print(str2);
        printWriter.print("mIsInMultiWindowMode=");
        printWriter.print(this.mIsInMultiWindowMode);
        printWriter.print(" mIsInPictureInPictureMode=");
        printWriter.println(this.mIsInPictureInPictureMode);
        printWriter.print(str2);
        printWriter.print("mChangingConfigurations=");
        printWriter.println(this.mChangingConfigurations);
        printWriter.print(str2);
        printWriter.print("mCurrentConfig=");
        printWriter.println(this.mCurrentConfig);
        this.mFragments.dumpLoaders(str2, fileDescriptor, printWriter, strArr);
        this.mFragments.getFragmentManager().dump(str2, fileDescriptor, printWriter, strArr);
        VoiceInteractor voiceInteractor = this.mVoiceInteractor;
        if (voiceInteractor != null) {
            voiceInteractor.dump(str2, fileDescriptor, printWriter, strArr);
        }
        if (getWindow() instanceof PhoneWindow) {
            ((PhoneWindow) getWindow()).dumpColors(str, fileDescriptor, printWriter, strArr);
        }
        if (getWindow() != null && getWindow().peekDecorView() != null && getWindow().peekDecorView().getViewRootImpl() != null) {
            getWindow().peekDecorView().getViewRootImpl().dump(str, printWriter);
        }
        this.mHandler.getLooper().dump(new PrintWriterPrinter(printWriter), str);
        ResourcesManager.getInstance().dump(str, printWriter);
        DumpableContainerImpl dumpableContainerImpl = this.mDumpableContainer;
        if (dumpableContainerImpl != null) {
            dumpableContainerImpl.dumpAllDumpables(str, printWriter, strArr);
        }
    }

    private void dumpLegacyDumpable(String str, PrintWriter printWriter, String str2, String str3) {
        printWriter.printf("%s%s option deprecated. Use %s %s instead\n", str, str2, DUMP_ARG_DUMP_DUMPABLE, str3);
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

    public boolean setTranslucent(boolean z) {
        if (z) {
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
        if (!ActivityClient.getInstance().convertFromTranslucent(this.mToken)) {
            return false;
        }
        WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, true);
        return true;
    }

    public void semConvertFromTranslucent(boolean z) {
        Log.d(TAG, "semConvertFromTranslucent, activity=" + this + ", caller=" + Debug.getCallers(3));
        this.mTranslucentCallback = null;
        if (!ActivityClient.getInstance().convertFromTranslucent(this.mToken, z) || z) {
            return;
        }
        WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, true);
    }

    @SystemApi
    public boolean convertToTranslucent(TranslucentConversionListener translucentConversionListener, ActivityOptions activityOptions) {
        TranslucentConversionListener translucentConversionListener2;
        this.mTranslucentCallback = translucentConversionListener;
        this.mChangeCanvasToTranslucent = ActivityClient.getInstance().convertToTranslucent(this.mToken, activityOptions == null ? null : activityOptions.toBundle());
        WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, false);
        if (!this.mChangeCanvasToTranslucent && (translucentConversionListener2 = this.mTranslucentCallback) != null) {
            translucentConversionListener2.onTranslucentConversionComplete(true);
        }
        return this.mChangeCanvasToTranslucent;
    }

    public boolean semConvertToTranslucent(final SemTranslucentConversionListener semTranslucentConversionListener) {
        Log.d(TAG, "semConvertToTranslucent, activity=" + this + ", caller=" + Debug.getCallers(3));
        return convertToTranslucent(new TranslucentConversionListener(this) { // from class: android.app.Activity.2
            @Override // android.app.Activity.TranslucentConversionListener
            public void onTranslucentConversionComplete(boolean z) {
                SemTranslucentConversionListener semTranslucentConversionListener2 = semTranslucentConversionListener;
                if (semTranslucentConversionListener2 != null) {
                    semTranslucentConversionListener2.onTranslucentConversionCompleted(z);
                }
            }
        }, null);
    }

    void onTranslucentConversionComplete(boolean z) {
        TranslucentConversionListener translucentConversionListener = this.mTranslucentCallback;
        if (translucentConversionListener != null) {
            translucentConversionListener.onTranslucentConversionComplete(z);
            this.mTranslucentCallback = null;
        }
        if (this.mChangeCanvasToTranslucent) {
            WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, false);
        }
    }

    public void onNewSceneTransitionInfo(ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        this.mActivityTransitionState.setEnterSceneTransitionInfo(this, sceneTransitionInfo);
        if (this.mStopped) {
            return;
        }
        this.mActivityTransitionState.enterReady(this);
    }

    ActivityOptions.SceneTransitionInfo getSceneTransitionInfo() {
        ActivityOptions.SceneTransitionInfo sceneTransitionInfo = this.mSceneTransitionInfo;
        this.mSceneTransitionInfo = null;
        return sceneTransitionInfo;
    }

    @Deprecated
    public void onVisibleBehindCanceled() {
        this.mCalled = true;
    }

    public void dispatchEnterAnimationComplete() {
        onEnterAnimationComplete();
        if (getWindow() == null || getWindow().getDecorView() == null) {
            return;
        }
        getWindow().getDecorView().getViewTreeObserver().dispatchOnEnterAnimationComplete();
    }

    public void setImmersive(boolean z) {
        ActivityClient.getInstance().setImmersive(this.mToken, z);
    }

    public void setVrModeEnabled(boolean z, ComponentName componentName) throws PackageManager.NameNotFoundException {
        if (ActivityClient.getInstance().setVrMode(this.mToken, z, componentName) != 0) {
            throw new PackageManager.NameNotFoundException(componentName.flattenToString());
        }
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return this.mWindow.getDecorView().startActionMode(callback);
    }

    public ActionMode startActionMode(ActionMode.Callback callback, int i) {
        return this.mWindow.getDecorView().startActionMode(callback, i);
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        if (this.mActionModeTypeStarting != 0) {
            return null;
        }
        initWindowDecorActionBar();
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            return actionBar.startActionMode(callback);
        }
        return null;
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        try {
            this.mActionModeTypeStarting = i;
            return onWindowStartingActionMode(callback);
        } finally {
            this.mActionModeTypeStarting = 0;
        }
    }

    public boolean shouldUpRecreateTask(Intent intent) {
        try {
            PackageManager packageManager = getPackageManager();
            ComponentName component = intent.getComponent();
            if (component == null) {
                component = intent.resolveActivity(packageManager);
            }
            ActivityInfo activityInfo = packageManager.getActivityInfo(component, 0);
            if (activityInfo.taskAffinity == null) {
                return false;
            }
            return ActivityClient.getInstance().shouldUpRecreateTask(this.mToken, activityInfo.taskAffinity);
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public boolean navigateUpTo(Intent intent) {
        Intent intent2;
        int i;
        Intent intent3;
        Activity activity = this.mParent;
        if (activity == null) {
            if (intent.getComponent() == null) {
                ComponentName resolveActivity = intent.resolveActivity(getPackageManager());
                if (resolveActivity == null) {
                    return false;
                }
                Intent intent4 = new Intent(intent);
                intent4.setComponent(resolveActivity);
                intent2 = intent4;
            } else {
                intent2 = intent;
            }
            synchronized (this) {
                i = this.mResultCode;
                intent3 = this.mResultData;
            }
            if (intent3 != null) {
                intent3.prepareToLeaveProcess(this);
            }
            intent2.prepareToLeaveProcess(this);
            return ActivityClient.getInstance().navigateUpTo(this.mToken, intent2, intent2.resolveTypeIfNeeded(getContentResolver()), i, intent3);
        }
        return activity.navigateUpToFromChild(this, intent);
    }

    @Deprecated
    public boolean navigateUpToFromChild(Activity activity, Intent intent) {
        return navigateUpTo(intent);
    }

    public Intent getParentActivityIntent() {
        String str = this.mActivityInfo.parentActivityName;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ComponentName componentName = new ComponentName(this, str);
        try {
            if (getPackageManager().getActivityInfo(componentName, 0).parentActivityName == null) {
                return Intent.makeMainActivity(componentName);
            }
            return new Intent().setComponent(componentName);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "getParentActivityIntent: bad parentActivityName '" + str + "' in manifest");
            return null;
        }
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        if (sharedElementCallback == null) {
            sharedElementCallback = SharedElementCallback.NULL_CALLBACK;
        }
        this.mEnterTransitionListener = sharedElementCallback;
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        if (sharedElementCallback == null) {
            sharedElementCallback = SharedElementCallback.NULL_CALLBACK;
        }
        this.mExitTransitionListener = sharedElementCallback;
    }

    public void postponeEnterTransition() {
        this.mActivityTransitionState.postponeEnterTransition();
    }

    public void startPostponedEnterTransition() {
        this.mActivityTransitionState.startPostponedEnterTransition();
    }

    public DragAndDropPermissions requestDragAndDropPermissions(DragEvent dragEvent) {
        DragAndDropPermissions obtain = DragAndDropPermissions.obtain(dragEvent);
        if (obtain == null || !obtain.take(getActivityToken())) {
            return null;
        }
        return obtain;
    }

    final void setParent(Activity activity) {
        this.mParent = activity;
    }

    final void attach(Context context, ActivityThread activityThread, Instrumentation instrumentation, IBinder iBinder, int i, Application application, Intent intent, ActivityInfo activityInfo, CharSequence charSequence, Activity activity, String str, NonConfigurationInstances nonConfigurationInstances, Configuration configuration, String str2, IVoiceInteractor iVoiceInteractor, Window window, ViewRootImpl.ActivityConfigCallback activityConfigCallback, IBinder iBinder2, IBinder iBinder3) {
        attach(context, activityThread, instrumentation, iBinder, i, application, intent, activityInfo, charSequence, activity, str, nonConfigurationInstances, configuration, str2, iVoiceInteractor, window, activityConfigCallback, iBinder2, iBinder3, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void attach(android.content.Context r11, android.app.ActivityThread r12, android.app.Instrumentation r13, android.os.IBinder r14, int r15, android.app.Application r16, android.content.Intent r17, android.content.pm.ActivityInfo r18, java.lang.CharSequence r19, android.app.Activity r20, java.lang.String r21, android.app.Activity.NonConfigurationInstances r22, android.content.res.Configuration r23, java.lang.String r24, com.android.internal.app.IVoiceInteractor r25, android.view.Window r26, android.view.ViewRootImpl.ActivityConfigCallback r27, android.os.IBinder r28, android.os.IBinder r29, android.os.IBinder r30) {
        /*
            Method dump skipped, instructions count: 327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.Activity.attach(android.content.Context, android.app.ActivityThread, android.app.Instrumentation, android.os.IBinder, int, android.app.Application, android.content.Intent, android.content.pm.ActivityInfo, java.lang.CharSequence, android.app.Activity, java.lang.String, android.app.Activity$NonConfigurationInstances, android.content.res.Configuration, java.lang.String, com.android.internal.app.IVoiceInteractor, android.view.Window, android.view.ViewRootImpl$ActivityConfigCallback, android.os.IBinder, android.os.IBinder, android.os.IBinder):void");
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final IBinder getActivityToken() {
        Activity activity = this.mParent;
        return activity != null ? activity.getActivityToken() : this.mToken;
    }

    public final IBinder getAssistToken() {
        Activity activity = this.mParent;
        return activity != null ? activity.getAssistToken() : this.mAssistToken;
    }

    public final IBinder getShareableActivityToken() {
        Activity activity = this.mParent;
        return activity != null ? activity.getShareableActivityToken() : this.mShareableActivityToken;
    }

    public final ActivityThread getActivityThread() {
        return this.mMainThread;
    }

    public final ActivityInfo getActivityInfo() {
        return this.mActivityInfo;
    }

    final void performCreate(Bundle bundle) {
        performCreate(bundle, null);
    }

    final void performCreate(Bundle bundle, PersistableBundle persistableBundle) {
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "performCreate:" + this.mComponent.getClassName());
        }
        dispatchActivityPreCreated(bundle);
        this.mCanEnterPictureInPicture = true;
        int compatWindowingMode = CompatSandbox.getCompatWindowingMode(getResources().getConfiguration(), getResources().getConfiguration().windowConfiguration.getWindowingMode());
        this.mIsInMultiWindowMode = WindowConfiguration.inMultiWindowMode(compatWindowingMode);
        this.mIsInPictureInPictureMode = compatWindowingMode == 2;
        this.mShouldDockBigOverlays = getResources().getBoolean(R.bool.config_dockBigOverlayWindows);
        restoreHasCurrentPermissionRequest(bundle);
        long uptimeMillis = SystemClock.uptimeMillis();
        if (persistableBundle != null) {
            onCreate(bundle, persistableBundle);
        } else {
            onCreate(bundle);
        }
        EventLogTags.writeWmOnCreateCalled(this.mIdent, getComponentName().getClassName(), "performCreate", SystemClock.uptimeMillis() - uptimeMillis);
        this.mActivityTransitionState.readState(bundle);
        this.mVisibleFromClient = !this.mWindow.getWindowStyle().getBoolean(10, false);
        this.mFragments.dispatchActivityCreated();
        this.mActivityTransitionState.setEnterSceneTransitionInfo(this, getSceneTransitionInfo());
        dispatchActivityPostCreated(bundle);
        Trace.traceEnd(32L);
    }

    final void performNewIntent(Intent intent) {
        Trace.traceBegin(32L, "performNewIntent");
        this.mCanEnterPictureInPicture = true;
        onNewIntent(intent);
        Trace.traceEnd(32L);
    }

    final void performNewIntent(Intent intent, ComponentCaller componentCaller) {
        Trace.traceBegin(32L, "performNewIntent");
        this.mCanEnterPictureInPicture = true;
        this.mCurrentCaller = componentCaller;
        onNewIntent(intent, componentCaller);
        this.mCurrentCaller = null;
        Trace.traceEnd(32L);
    }

    final void performStart(String str) {
        String dlWarning;
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "performStart:" + this.mComponent.getClassName());
        }
        dispatchActivityPreStarted();
        this.mActivityTransitionState.setEnterSceneTransitionInfo(this, getSceneTransitionInfo());
        this.mFragments.noteStateNotSaved();
        this.mCalled = false;
        this.mFragments.execPendingActions();
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mInstrumentation.callActivityOnStart(this);
        EventLogTags.writeWmOnStartCalled(this.mIdent, getComponentName().getClassName(), str, SystemClock.uptimeMillis() - uptimeMillis);
        if (!this.mCalled) {
            throw new SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onStart()");
        }
        this.mFragments.dispatchStart();
        this.mFragments.reportLoaderStart();
        boolean z = (this.mApplication.getApplicationInfo().flags & 2) != 0;
        if (z && (dlWarning = getDlWarning()) != null) {
            String charSequence = getApplicationInfo().loadLabel(getPackageManager()).toString();
            String str2 = "Detected problems with app native libraries\n(please consult log for detail):\n" + dlWarning;
            if (z) {
                new AlertDialog.Builder(this).setTitle(charSequence).setMessage(str2).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).setCancelable(false).show();
            } else {
                Toast.makeText(this, charSequence + ShaderAssembler.NEWLINE + str2, 1).show();
            }
        }
        GraphicsEnvironment.getInstance().showAngleInUseDialogBox(this);
        this.mActivityTransitionState.enterReady(this);
        dispatchActivityPostStarted();
        if (CoreRune.FW_APPLOCK && ((ActivityManager) getSystemService("activity")).isAppLockedPackage(getPackageName())) {
            startAppLockService();
        }
        Trace.traceEnd(32L);
    }

    final void performRestart(boolean z) {
        Trace.traceBegin(32L, "performRestart");
        this.mCanEnterPictureInPicture = true;
        this.mFragments.noteStateNotSaved();
        if (this.mToken != null && this.mParent == null) {
            WindowManagerGlobal.getInstance().setStoppedState(this.mToken, false);
        }
        if (this.mStopped) {
            this.mStopped = false;
            synchronized (this.mManagedCursors) {
                int size = this.mManagedCursors.size();
                for (int i = 0; i < size; i++) {
                    ManagedCursor managedCursor = this.mManagedCursors.get(i);
                    if (managedCursor.mReleased || managedCursor.mUpdated) {
                        if (!managedCursor.mCursor.requery() && getApplicationInfo().targetSdkVersion >= 14) {
                            throw new IllegalStateException("trying to requery an already closed cursor  " + managedCursor.mCursor);
                        }
                        managedCursor.mReleased = false;
                        managedCursor.mUpdated = false;
                    }
                }
            }
            this.mCalled = false;
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mInstrumentation.callActivityOnRestart(this);
            EventLogTags.writeWmOnRestartCalled(this.mIdent, getComponentName().getClassName(), "performRestart", SystemClock.uptimeMillis() - uptimeMillis);
            if (!this.mCalled) {
                throw new SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onRestart()");
            }
            if (z) {
                performStart("performRestart");
            }
        }
        Trace.traceEnd(32L);
    }

    final void performResume(boolean z, String str) {
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "performResume:" + this.mComponent.getClassName());
        }
        dispatchActivityPreResumed();
        this.mCanEnterPictureInPicture = true;
        this.mFragments.execPendingActions();
        this.mLastNonConfigurationInstances = null;
        getAutofillClientController().onActivityPerformResume(z);
        this.mCalled = false;
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mInstrumentation.callActivityOnResume(this);
        EventLogTags.writeWmOnResumeCalled(this.mIdent, getComponentName().getClassName(), str, SystemClock.uptimeMillis() - uptimeMillis);
        if (!this.mCalled) {
            throw new SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onResume()");
        }
        if (!this.mVisibleFromClient && !this.mFinished) {
            Log.w(TAG, "An activity without a UI must call finish() before onResume() completes");
            if (getApplicationInfo().targetSdkVersion > 22) {
                throw new IllegalStateException("Activity " + this.mComponent.toShortString() + " did not call finish() prior to onResume() completing");
            }
        }
        this.mCalled = false;
        this.mFragments.dispatchResume();
        this.mFragments.execPendingActions();
        onPostResume();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onPostResume()");
        }
        dispatchActivityPostResumed();
        Trace.traceEnd(32L);
    }

    final void performPause() {
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "performPause:" + this.mComponent.getClassName());
        }
        if (isTvImplicitEnterPipProhibited()) {
            this.mCanEnterPictureInPicture = false;
        }
        if (MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED && this.mIsTopResumedActivity && this.mResumed) {
            performTopResumedActivityChanged(false, "pausing");
        }
        dispatchActivityPrePaused();
        this.mDoReportFullyDrawn = false;
        this.mFragments.dispatchPause();
        if (android.app.jank.Flags.detailedAppJankMetricsApi()) {
            stopAppJankTracking();
        }
        this.mCalled = false;
        long uptimeMillis = SystemClock.uptimeMillis();
        onPause();
        EventLogTags.writeWmOnPausedCalled(this.mIdent, getComponentName().getClassName(), "performPause", SystemClock.uptimeMillis() - uptimeMillis);
        this.mResumed = false;
        if (!this.mCalled && getApplicationInfo().targetSdkVersion >= 9) {
            throw new SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onPause()");
        }
        dispatchActivityPostPaused();
        Trace.traceEnd(32L);
    }

    final void performUserLeaving() {
        onUserInteraction();
        if (isTvImplicitEnterPipProhibited()) {
            this.mCanEnterPictureInPicture = false;
        }
        onUserLeaveHint();
    }

    final void performStop(boolean z, String str) {
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "performStop:" + this.mComponent.getClassName());
        }
        this.mDoReportFullyDrawn = false;
        this.mFragments.doLoaderStop(this.mChangingConfigurations);
        this.mCanEnterPictureInPicture = false;
        if (!this.mStopped) {
            dispatchActivityPreStopped();
            Window window = this.mWindow;
            if (window != null) {
                window.closeAllPanels();
            }
            if (!z && this.mToken != null && this.mParent == null) {
                WindowManagerGlobal.getInstance().setStoppedState(this.mToken, true);
            }
            this.mFragments.dispatchStop();
            this.mCalled = false;
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mInstrumentation.callActivityOnStop(this);
            EventLogTags.writeWmOnStopCalled(this.mIdent, getComponentName().getClassName(), str, SystemClock.uptimeMillis() - uptimeMillis);
            if (!this.mCalled) {
                throw new SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onStop()");
            }
            synchronized (this.mManagedCursors) {
                int size = this.mManagedCursors.size();
                for (int i = 0; i < size; i++) {
                    ManagedCursor managedCursor = this.mManagedCursors.get(i);
                    if (!managedCursor.mReleased) {
                        managedCursor.mCursor.deactivate();
                        managedCursor.mReleased = true;
                    }
                }
            }
            this.mStopped = true;
            dispatchActivityPostStopped();
            if (CoreRune.SYSPERF_DYNAMIC_BOOST) {
                try {
                    ActivityTaskManager.getService().notifyPerformStop(this.mComponent.getPackageName());
                } catch (RemoteException unused) {
                }
            }
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
        long uptimeMillis = SystemClock.uptimeMillis();
        onDestroy();
        EventLogTags.writeWmOnDestroyCalled(this.mIdent, getComponentName().getClassName(), "performDestroy", SystemClock.uptimeMillis() - uptimeMillis);
        this.mFragments.doLoaderDestroy();
        VoiceInteractor voiceInteractor = this.mVoiceInteractor;
        if (voiceInteractor != null) {
            voiceInteractor.detachActivity();
        }
        dispatchActivityPostDestroyed();
        Trace.traceEnd(32L);
    }

    final void dispatchMultiWindowModeChanged(boolean z, Configuration configuration) {
        this.mIsInMultiWindowMode = z;
        this.mFragments.dispatchMultiWindowModeChanged(z, configuration);
        this.mWindowingMode = configuration.windowConfiguration.getWindowingMode();
        if (this.mWindow.getDecorView() != null) {
            ((DecorView) this.mWindow.getDecorView()).onWindowingModeChanged(this.mWindowingMode, this.mWindowingMode == 6 && WindowConfiguration.isSplitScreenWindowingMode(configuration.windowConfiguration));
        }
        Window window = this.mWindow;
        if (window != null) {
            window.onMultiWindowModeChanged();
        }
        onMultiWindowModeChanged(z, configuration);
    }

    final void dispatchPictureInPictureModeChanged(boolean z, Configuration configuration) {
        this.mIsInPictureInPictureMode = z;
        this.mFragments.dispatchPictureInPictureModeChanged(z, configuration);
        Window window = this.mWindow;
        if (window != null) {
            window.onPictureInPictureModeChanged(z);
        }
        onPictureInPictureModeChanged(z, configuration);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public final boolean isResumed() {
        return this.mResumed;
    }

    public final boolean semIsResumed() {
        return this.mResumed;
    }

    private void storeHasCurrentPermissionRequest(Bundle bundle) {
        if (bundle == null || !this.mHasCurrentPermissionsRequest) {
            return;
        }
        bundle.putBoolean(HAS_CURRENT_PERMISSIONS_REQUEST_KEY, true);
    }

    private void restoreHasCurrentPermissionRequest(Bundle bundle) {
        if (bundle != null) {
            this.mHasCurrentPermissionsRequest = bundle.getBoolean(HAS_CURRENT_PERMISSIONS_REQUEST_KEY, false);
        }
    }

    void dispatchActivityResult(String str, int i, int i2, Intent intent, ComponentCaller componentCaller, String str2) {
        internalDispatchActivityResult(str, i, i2, intent, componentCaller, str2);
    }

    void dispatchActivityResult(String str, int i, int i2, Intent intent, String str2) {
        if (com.android.internal.hidden_from_bootclasspath.android.security.Flags.contentUriPermissionApis()) {
            internalDispatchActivityResult(str, i, i2, intent, new ComponentCaller(getActivityToken(), null), str2);
        } else {
            internalDispatchActivityResult(str, i, i2, intent, null, str2);
        }
    }

    private void internalDispatchActivityResult(String str, int i, int i2, Intent intent, ComponentCaller componentCaller, String str2) {
        this.mFragments.noteStateNotSaved();
        if (str == null) {
            if (com.android.internal.hidden_from_bootclasspath.android.security.Flags.contentUriPermissionApis()) {
                this.mCurrentCaller = componentCaller;
                onActivityResult(i, i2, intent, componentCaller);
                this.mCurrentCaller = null;
            } else {
                onActivityResult(i, i2, intent);
            }
        } else if (str.startsWith(REQUEST_PERMISSIONS_WHO_PREFIX)) {
            String substring = str.substring(28);
            if (TextUtils.isEmpty(substring)) {
                dispatchRequestPermissionsResult(i, intent);
            } else {
                Fragment findFragmentByWho = this.mFragments.findFragmentByWho(substring);
                if (findFragmentByWho != null) {
                    dispatchRequestPermissionsResultToFragment(i, intent, findFragmentByWho);
                }
            }
        } else if (str.startsWith("@android:view:")) {
            Iterator<ViewRootImpl> it = WindowManagerGlobal.getInstance().getRootViews(getActivityToken()).iterator();
            while (it.hasNext()) {
                ViewRootImpl next = it.next();
                if (next.getView() != null && next.getView().dispatchActivityResult(str, i, i2, intent)) {
                    return;
                }
            }
        } else if (str.startsWith(AutofillClientController.AUTO_FILL_AUTH_WHO_PREFIX)) {
            getAutofillClientController().onDispatchActivityResult(i, i2, intent);
        } else {
            Fragment findFragmentByWho2 = this.mFragments.findFragmentByWho(str);
            if (findFragmentByWho2 != null) {
                findFragmentByWho2.onActivityResult(i, i2, intent);
            }
        }
        EventLogTags.writeWmOnActivityResultCalled(this.mIdent, getComponentName().getClassName(), str2);
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

    public void setOverlayWithDecorCaptionEnabled(boolean z) {
        this.mWindow.setOverlayWithDecorCaptionEnabled(z);
    }

    private void dispatchRequestPermissionsResult(int i, Intent intent) {
        String[] strArr;
        int[] iArr;
        this.mHasCurrentPermissionsRequest = false;
        if (intent != null) {
            strArr = intent.getStringArrayExtra(PackageManager.EXTRA_REQUEST_PERMISSIONS_NAMES);
        } else {
            strArr = new String[0];
        }
        if (intent != null) {
            iArr = intent.getIntArrayExtra(PackageManager.EXTRA_REQUEST_PERMISSIONS_RESULTS);
        } else {
            iArr = new int[0];
        }
        onRequestPermissionsResult(i, strArr, iArr, intent != null ? intent.getIntExtra(PackageManager.EXTRA_REQUEST_PERMISSIONS_DEVICE_ID, 0) : 0);
    }

    private void dispatchRequestPermissionsResultToFragment(int i, Intent intent, Fragment fragment) {
        String[] strArr;
        int[] iArr;
        if (intent != null) {
            strArr = intent.getStringArrayExtra(PackageManager.EXTRA_REQUEST_PERMISSIONS_NAMES);
        } else {
            strArr = new String[0];
        }
        if (intent != null) {
            iArr = intent.getIntArrayExtra(PackageManager.EXTRA_REQUEST_PERMISSIONS_RESULTS);
        } else {
            iArr = new int[0];
        }
        fragment.onRequestPermissionsResult(i, strArr, iArr);
    }

    public final boolean isVisibleForAutofill() {
        return !this.mStopped;
    }

    public void setDisablePreviewScreenshots(boolean z) {
        setRecentsScreenshotEnabled(!z);
    }

    public void setRecentsScreenshotEnabled(boolean z) {
        ActivityClient.getInstance().setRecentsScreenshotEnabled(this.mToken, z);
    }

    @Deprecated
    public void semSetDisablePreviewScreenshots(boolean z) {
        setRecentsScreenshotEnabled(!z);
    }

    public void setShowWhenLocked(boolean z) {
        ActivityClient.getInstance().setShowWhenLocked(this.mToken, z);
    }

    public void setInheritShowWhenLocked(boolean z) {
        ActivityClient.getInstance().setInheritShowWhenLocked(this.mToken, z);
    }

    public void setTurnScreenOn(boolean z) {
        ActivityClient.getInstance().setTurnScreenOn(this.mToken, z);
    }

    public void setAllowCrossUidActivitySwitchFromBelow(boolean z) {
        ActivityClient.getInstance().setAllowCrossUidActivitySwitchFromBelow(this.mToken, z);
    }

    public void registerRemoteAnimations(RemoteAnimationDefinition remoteAnimationDefinition) {
        ActivityClient.getInstance().registerRemoteAnimations(this.mToken, remoteAnimationDefinition);
    }

    public void unregisterRemoteAnimations() {
        ActivityClient.getInstance().unregisterRemoteAnimations(this.mToken);
    }

    public void updateUiTranslationState(int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, UiTranslationSpec uiTranslationSpec) {
        if (this.mUiTranslationController == null) {
            this.mUiTranslationController = new UiTranslationController(this, getApplicationContext());
        }
        this.mUiTranslationController.updateUiTranslationState(i, translationSpec, translationSpec2, list, uiTranslationSpec);
    }

    public void enableTaskLocaleOverride() {
        ActivityClient.getInstance().enableTaskLocaleOverride(this.mToken);
    }

    public void changeToHorizontalSplitLayout() {
        new MultiWindowManager().changeToHorizontalSplitLayout(getActivityToken());
    }

    public void setActivityRecordInputSinkEnabled(boolean z) {
        ActivityClient.getInstance().setActivityRecordInputSinkEnabled(this.mToken, z);
    }

    class HostCallbacks extends FragmentHostCallback<Activity> {
        public HostCallbacks() {
            super(Activity.this);
        }

        @Override // android.app.FragmentHostCallback
        public void onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            Activity.this.dump(str, fileDescriptor, printWriter, strArr);
        }

        @Override // android.app.FragmentHostCallback
        public boolean onShouldSaveFragmentState(Fragment fragment) {
            return !Activity.this.isFinishing();
        }

        @Override // android.app.FragmentHostCallback
        public LayoutInflater onGetLayoutInflater() {
            LayoutInflater layoutInflater = Activity.this.getLayoutInflater();
            return onUseFragmentManagerInflaterFactory() ? layoutInflater.cloneInContext(Activity.this) : layoutInflater;
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
        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int i, Bundle bundle) {
            Activity.this.startActivityFromFragment(fragment, intent, i, bundle);
        }

        @Override // android.app.FragmentHostCallback
        public void onStartActivityAsUserFromFragment(Fragment fragment, Intent intent, int i, Bundle bundle, UserHandle userHandle) {
            Activity.this.startActivityAsUserFromFragment(fragment, intent, i, bundle, userHandle);
        }

        @Override // android.app.FragmentHostCallback
        public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) throws IntentSender.SendIntentException {
            if (Activity.this.mParent == null) {
                Activity.this.startIntentSenderForResultInner(intentSender, fragment.mWho, i, intent, i2, i3, bundle);
            } else if (bundle != null) {
                Activity.this.mParent.startIntentSenderFromFragment(fragment, intentSender, i, intent, i2, i3, bundle);
            }
        }

        @Override // android.app.FragmentHostCallback
        public void onRequestPermissionsFromFragment(Fragment fragment, String[] strArr, int i) {
            Activity.this.startActivityForResult(Activity.REQUEST_PERMISSIONS_WHO_PREFIX + fragment.mWho, Activity.this.getPackageManager().buildRequestPermissionsIntent(strArr), i, null);
        }

        @Override // android.app.FragmentHostCallback
        public boolean onHasWindowAnimations() {
            return Activity.this.getWindow() != null;
        }

        @Override // android.app.FragmentHostCallback
        public int onGetWindowAnimations() {
            Window window = Activity.this.getWindow();
            if (window == null) {
                return 0;
            }
            return window.getAttributes().windowAnimations;
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
            Window window = Activity.this.getWindow();
            return (window == null || window.peekDecorView() == null) ? false : true;
        }
    }

    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        Window window = this.mWindow;
        if (window == null) {
            throw new IllegalStateException("OnBackInvokedDispatcher are not available on non-visual activities");
        }
        return window.getOnBackInvokedDispatcher();
    }

    private boolean interceptTouchEventForPopOver(MotionEvent motionEvent) {
        if (!this.mIsPopOver) {
            return false;
        }
        if (motionEvent.getAction() == 0 && isOutOfBounds(motionEvent)) {
            this.mInOutsideTouch = true;
        }
        if (!this.mInOutsideTouch) {
            return false;
        }
        onOutsideTouchEventForPopOver(motionEvent);
        return true;
    }

    private void onOutsideTouchEventForPopOver(MotionEvent motionEvent) {
        if (!this.mInOutsideLongPress) {
            this.mLongPressDetector.onTouchEvent(motionEvent);
        }
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            this.mInOutsideTouch = false;
            if (this.mInOutsideLongPress) {
                this.mInOutsideLongPress = false;
                clearTransparentPopOver();
            } else if (motionEvent.getAction() == 1 && isOutOfBounds(motionEvent)) {
                onBackPressed();
            }
        }
    }

    private boolean isOutOfBounds(MotionEvent motionEvent) {
        return this.mWindow.peekDecorView() != null && this.mWindow.isOutOfBounds(this, motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyTransparentPopOver() {
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        attributes.semAddExtensionFlags(2);
        this.mWindow.setAttributes(attributes);
    }

    private void clearTransparentPopOver() {
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        attributes.semClearExtensionFlags(2);
        this.mWindow.setAttributes(attributes);
    }

    public void registerScreenCaptureCallback(Executor executor, ScreenCaptureCallback screenCaptureCallback) {
        if (this.mScreenCaptureCallbackHandler == null) {
            this.mScreenCaptureCallbackHandler = new ScreenCaptureCallbackHandler(this.mToken);
        }
        this.mScreenCaptureCallbackHandler.registerScreenCaptureCallback(executor, screenCaptureCallback);
    }

    public void unregisterScreenCaptureCallback(ScreenCaptureCallback screenCaptureCallback) {
        ScreenCaptureCallbackHandler screenCaptureCallbackHandler = this.mScreenCaptureCallbackHandler;
        if (screenCaptureCallbackHandler != null) {
            screenCaptureCallbackHandler.unregisterScreenCaptureCallback(screenCaptureCallback);
        }
    }

    private void startAppJankTracking() {
        if (!android.app.jank.Flags.detailedAppJankMetricsLoggingEnabled() || this.mApplication.getApplicationInfo().category == -1 || getWindow() == null || getWindow().peekDecorView() == null) {
            return;
        }
        DecorView decorView = (DecorView) getWindow().peekDecorView();
        if (decorView.getVisibility() == 0) {
            decorView.setAppJankStatsCallback(new DecorView.AppJankStatsCallback() { // from class: android.app.Activity.4
                @Override // com.android.internal.policy.DecorView.AppJankStatsCallback
                public JankTracker getAppJankTracker() {
                    return Activity.this.mJankTracker;
                }
            });
            if (this.mJankTracker == null) {
                if (android.app.jank.Flags.viewrootChoreographer()) {
                    this.mJankTracker = new JankTracker(decorView);
                } else {
                    this.mJankTracker = new JankTracker(Choreographer.getInstance(), decorView);
                }
            }
            this.mJankTracker.setActivityName(getComponentName().getClassName());
            this.mJankTracker.setAppUid(Process.myUid());
            this.mJankTracker.enableAppJankTracking();
        }
    }

    private void stopAppJankTracking() {
        JankTracker jankTracker;
        if (android.app.jank.Flags.detailedAppJankMetricsLoggingEnabled() && (jankTracker = this.mJankTracker) != null) {
            jankTracker.disableAppJankTracking();
        }
    }

    private void startAppLockService() {
        Window window = this.mWindow;
        WindowManager.LayoutParams attributes = window != null ? window.getAttributes() : null;
        try {
            ActivityTaskManager.getService().startAppLockService(this.mToken, getIntent(), (attributes == null || ((attributes.flags & 4194304) == 0 && (attributes.flags & 524288) == 0)) ? false : true, getPackageName());
        } catch (RemoteException unused) {
        }
    }

    public boolean semExitMultiWindowMode() {
        return new MultiWindowManager().exitMultiWindow(getActivityToken(), true);
    }

    int getWindowingMode() {
        return this.mWindowingMode;
    }

    void releaseActivityFocusIfNeeded() {
        View view = this.mDecor;
        if (view instanceof DecorView) {
            ((DecorView) view).releaseActivityFocusIfNeeded();
        }
    }

    int getDexTaskDocking() {
        return this.mDexTaskDocking;
    }

    void onDexTaskDockingChanged(int i) {
        if (CoreRune.IS_DEBUG_LEVEL_MID) {
            Log.i(TAG, "onDexTaskDockingChanged=" + WindowConfiguration.dexTaskDockingStateToString(i) + "   mDecor=" + this.mDecor + " state in number?" + i);
        }
        View view = this.mDecor;
        if (view != null) {
            ((DecorView) view).onDexTaskDockingChanged(i);
        }
        this.mDexTaskDocking = i;
    }
}
