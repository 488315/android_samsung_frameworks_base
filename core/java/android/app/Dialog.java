package android.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityEvent;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import android.window.WindowOnBackInvokedDispatcher;
import com.android.internal.R;
import com.android.internal.app.WindowDecorActionBar;
import com.android.internal.policy.PhoneWindow;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class Dialog implements DialogInterface, Window.Callback, KeyEvent.Callback, View.OnCreateContextMenuListener, Window.OnWindowDismissedCallback {
    private static final int CANCEL = 68;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_ANCHORED_GRAVITY = 51;
    private static final float DIALOG_DARK_DIM_AMOUNT = 0.65f;
    private static final float DIALOG_DIM_AMOUNT = 0.18f;
    private static final String DIALOG_HIERARCHY_TAG = "android:dialogHierarchy";
    private static final float DIALOG_POP_OVER_ELEVATION = 8.0f;
    private static final float DIALOG_REDUCE_TRANSPARENCY_DIM_AMOUNT = 0.35f;
    private static final String DIALOG_SHOWING_TAG = "android:dialogShowing";
    private static final int DISMISS = 67;
    private static final int MAX_LOOP_COUNT = 100;
    private static final String SAMSUNG_BASIC_INTERACTION_METADATA_NAME = "SamsungBasicInteraction";
    private static final String SAMSUNG_BASIC_INTERACTION_METADATA_VALUE = "SEP10";
    public static final int SEM_ANCHOR_TYPE_DEFAULT = 0;
    public static final int SEM_ANCHOR_TYPE_TOOLBAR = 1;
    private static final int SHOW = 69;
    private static final String TAG = "Dialog";
    private static final int UNPOSITIONED_DIALOG = -1;
    protected static boolean mIsDarkActionBar = false;
    private ActionBar mActionBar;
    private ActionMode mActionMode;
    private int mActionModeTypeStarting;
    private int mAnchorType;
    private View mAnchorView;
    private String mCancelAndDismissTaken;
    private Message mCancelMessage;
    protected boolean mCancelable;
    private boolean mCanceled;
    final Context mContext;
    private boolean mCreated;
    View mDecor;
    private OnBackInvokedCallback mDefaultBackCallback;
    private final Runnable mDismissAction;
    private Message mDismissMessage;
    private Runnable mDismissOverride;
    private final Handler mHandler;
    private boolean mHasFocus;
    private boolean mIsDeviceDefault;
    private boolean mIsDeviceDefaultDark;
    private boolean mIsSamsungBasicInteraction;
    private final Handler mListenersHandler;
    private boolean mNeedToUpdate;
    private DialogInterface.OnKeyListener mOnKeyListener;
    private final View.OnLayoutChangeListener mOnLayoutChangeListener;
    private Activity mOwnerActivity;
    private boolean mReconsiderForAlignToAnchor;
    private Runnable mRemoveOnLayoutChangeListnerRunnable;
    private View mRootView;
    private int mRootViewOrientation;
    private int mRootViewSwWidthDp;
    private SearchEvent mSearchEvent;
    private Message mShowMessage;
    private boolean mShowing;
    final Window mWindow;
    private final WindowManager mWindowManager;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        this.mNeedToUpdate = isNeedToUpdateAttributes(v);
        alignToAnchor();
    }

    public Dialog(Context context) {
        this(context, 0, true);
    }

    public Dialog(Context context, int themeResId) {
        this(context, themeResId, true);
    }

    Dialog(Context context, int themeResId, boolean createContextThemeWrapper) {
        ActivityInfo ai;
        this.mCancelable = true;
        this.mCreated = false;
        this.mShowing = false;
        this.mCanceled = false;
        this.mHandler = new Handler();
        this.mActionModeTypeStarting = 0;
        this.mDismissAction = new Runnable() { // from class: android.app.Dialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Dialog.this.dismissDialog();
            }
        };
        this.mHasFocus = false;
        this.mReconsiderForAlignToAnchor = false;
        this.mIsSamsungBasicInteraction = false;
        this.mRootViewOrientation = 0;
        this.mRootViewSwWidthDp = 0;
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: android.app.Dialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                Dialog.this.lambda$new$0(view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        };
        this.mRemoveOnLayoutChangeListnerRunnable = null;
        if (createContextThemeWrapper) {
            if (themeResId == 0) {
                TypedValue outValue = new TypedValue();
                context.getTheme().resolveAttribute(16843528, outValue, true);
                themeResId = outValue.resourceId;
            }
            this.mContext = new ContextThemeWrapper(context, themeResId);
        } else {
            this.mContext = context;
        }
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Window w = new PhoneWindow(this.mContext);
        this.mWindow = w;
        w.setCallback(this);
        w.setOnWindowDismissedCallback(this);
        w.setOnWindowSwipeDismissedCallback(new Window.OnWindowSwipeDismissedCallback() { // from class: android.app.Dialog$$ExternalSyntheticLambda2
            @Override // android.view.Window.OnWindowSwipeDismissedCallback
            public final void onWindowSwipeDismissed() {
                Dialog.this.lambda$new$1();
            }
        });
        w.setWindowManager(this.mWindowManager, null, null);
        w.setGravity(17);
        try {
            ApplicationInfo info = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
            if (info.metaData != null) {
                String data = info.metaData.getString(SAMSUNG_BASIC_INTERACTION_METADATA_NAME);
                this.mIsSamsungBasicInteraction = SAMSUNG_BASIC_INTERACTION_METADATA_VALUE.equals(data);
            }
        } catch (Exception e) {
            Log.e(TAG, "exceptioin!! " + e);
        }
        boolean isMetaDataInActivity = false;
        Activity activity = getActivityContext(context);
        if (activity != null && (ai = activity.getActivityInfo()) != null && ai.metaData != null) {
            String data2 = ai.metaData.getString(SAMSUNG_BASIC_INTERACTION_METADATA_NAME);
            isMetaDataInActivity = SAMSUNG_BASIC_INTERACTION_METADATA_VALUE.equals(data2);
        }
        TypedValue themeValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, themeValue, false);
        if (themeValue.data != 0) {
            this.mIsDeviceDefault = true;
            TypedValue colorValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, colorValue, true);
            this.mIsDeviceDefaultDark = colorValue.data != 0;
            if (!mIsDarkActionBar) {
                w.setGravity(80);
            }
        }
        Log.i(TAG, "mIsDeviceDefault = " + this.mIsDeviceDefault + ", mIsSamsungBasicInteraction = " + this.mIsSamsungBasicInteraction + ", isMetaDataInActivity = " + isMetaDataInActivity);
        if (this.mIsSamsungBasicInteraction || isMetaDataInActivity) {
            w.setGravity(80);
        }
        this.mListenersHandler = new ListenersHandler(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        if (this.mCancelable) {
            cancel();
        }
    }

    @Deprecated
    protected Dialog(Context context, boolean cancelable, Message cancelCallback) {
        this(context);
        this.mCancelable = cancelable;
        this.mCancelMessage = cancelCallback;
    }

    protected Dialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        this(context);
        this.mCancelable = cancelable;
        setOnCancelListener(cancelListener);
    }

    public final Context getContext() {
        return this.mContext;
    }

    public ActionBar getActionBar() {
        return this.mActionBar;
    }

    public final void setOwnerActivity(Activity activity) {
        this.mOwnerActivity = activity;
        getWindow().setVolumeControlStream(this.mOwnerActivity.getVolumeControlStream());
    }

    public final Activity getOwnerActivity() {
        return this.mOwnerActivity;
    }

    public boolean isShowing() {
        return this.mDecor != null && this.mDecor.getVisibility() == 0;
    }

    public void create() {
        if (!this.mCreated) {
            dispatchOnCreate(null);
        }
    }

    public void show() {
        if (this.mShowing) {
            if (this.mDecor != null) {
                if (this.mWindow.hasFeature(8)) {
                    this.mWindow.invalidatePanelMenu(8);
                }
                this.mDecor.setVisibility(0);
                return;
            }
            return;
        }
        this.mCanceled = false;
        if (!this.mCreated) {
            dispatchOnCreate(null);
        } else {
            Configuration config = this.mContext.getResources().getConfiguration();
            this.mWindow.getDecorView().dispatchConfigurationChanged(config);
        }
        onStart();
        this.mDecor = this.mWindow.getDecorView();
        if (this.mActionBar == null && this.mWindow.hasFeature(8)) {
            ApplicationInfo info = this.mContext.getApplicationInfo();
            this.mWindow.setDefaultIcon(info.icon);
            this.mWindow.setDefaultLogo(info.logo);
            this.mActionBar = new WindowDecorActionBar(this);
        }
        WindowManager.LayoutParams l = this.mWindow.getAttributes();
        boolean restoreSoftInputMode = false;
        if ((l.softInputMode & 256) == 0) {
            l.softInputMode |= 256;
            restoreSoftInputMode = true;
        }
        if (this.mIsDeviceDefault) {
            if (this.mIsDeviceDefaultDark && 0 == 0) {
                l.dimAmount = DIALOG_DARK_DIM_AMOUNT;
            } else {
                boolean isReduceTransparency = Settings.System.getInt(this.mContext.getContentResolver(), "accessibility_reduce_transparency", 0) == 1;
                l.dimAmount = isReduceTransparency ? DIALOG_REDUCE_TRANSPARENCY_DIM_AMOUNT : 0.18f;
            }
            boolean isReduceTransparency2 = this instanceof ProgressDialog;
            if (isReduceTransparency2 && ((ProgressDialog) this).getCurrentProgressStyle() == 1000) {
                int dialogBaseSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.tw_progress_circle_dialog_size);
                l.height = dialogBaseSize;
                l.width = dialogBaseSize;
            }
            l.dimDuration = this.mContext.getResources().getInteger(R.integer.sem_dialog_dim_duration);
        }
        this.mWindowManager.addView(this.mDecor, l);
        if (this.mIsDeviceDefault) {
            float f = l.dimAmount;
        }
        if (restoreSoftInputMode) {
            l.softInputMode &= -257;
        }
        if (this.mIsDeviceDefault && l.width > 0) {
            Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.tw_dialog_background_material, this.mContext.getTheme());
            if (this.mDecor != null && this.mDecor.getBackground() != null && drawable.getConstantState() != null && drawable.getConstantState().equals(this.mDecor.getBackground().getConstantState())) {
                int bottomInset = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_dialog_background_inset_vertical);
                InsetDrawable insetDrawable = new InsetDrawable(drawable, 0, 0, 0, bottomInset);
                this.mDecor.setBackground(insetDrawable);
            }
        }
        this.mShowing = true;
        sendShowMessage();
    }

    public void semSetAnchor(View anchor) {
        semSetAnchor(anchor, 0);
    }

    public void semSetAnchor(View anchor, int anchorType) {
        if (isSupportAnchor()) {
            Log.i(TAG, "semSetAnchor anchorView = " + anchor + " , anchorType : " + anchorType);
            this.mAnchorView = anchor;
            this.mAnchorType = anchorType;
            this.mRootView = this.mAnchorView.getRootView();
            if (this.mRootView != null) {
                semClearAnchorListener();
                this.mRootView.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
                this.mRemoveOnLayoutChangeListnerRunnable = new Runnable() { // from class: android.app.Dialog$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Dialog.this.lambda$semSetAnchor$2();
                    }
                };
                this.mNeedToUpdate = isNeedToUpdateAttributes(this.mRootView);
            }
            alignToAnchor();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$semSetAnchor$2() {
        this.mRootView.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
    }

    private boolean isNeedToUpdateAttributes(View rootView) {
        boolean needToUpdate = false;
        int orientation = rootView.getContext().getResources().getConfiguration().orientation;
        int smallestScreenWidthDp = rootView.getContext().getResources().getConfiguration().smallestScreenWidthDp;
        if (orientation != this.mRootViewOrientation) {
            this.mRootViewOrientation = orientation;
            needToUpdate = true;
        }
        if (smallestScreenWidthDp != this.mRootViewSwWidthDp) {
            this.mRootViewSwWidthDp = smallestScreenWidthDp;
            needToUpdate = true;
        }
        if (this.mReconsiderForAlignToAnchor) {
            needToUpdate = true;
            this.mReconsiderForAlignToAnchor = false;
            Log.i(TAG, "Reconsidered to update LayoutParams");
        }
        if (needToUpdate) {
            Log.i(TAG, "Dialog LayoutParams update is needed");
        }
        return needToUpdate;
    }

    private void alignToAnchor() {
        WindowInsets insets;
        Resources res = this.mContext.getResources();
        WindowManager.LayoutParams params = this.mWindow.getAttributes();
        if (!isSupportAnchor() || this.mAnchorView == null) {
            Log.e(TAG, "AnchorView is null state or not on Large Screen");
            params.gravity = 81;
            params.x = 0;
            params.y = 0;
            this.mWindow.setAttributes(params);
            return;
        }
        if (!this.mNeedToUpdate) {
            return;
        }
        int[] screenPos = new int[2];
        int[] windowPos = new int[2];
        int width = this.mAnchorView.getWidth();
        int height = this.mAnchorView.getHeight();
        this.mAnchorView.getLocationOnScreen(screenPos);
        this.mAnchorView.getLocationInWindow(windowPos);
        if (this.mAnchorView.getVisibility() == 0 && screenPos[0] <= 0 && windowPos[0] <= 0 && screenPos[1] <= 0 && windowPos[1] <= 0) {
            this.mReconsiderForAlignToAnchor = true;
            Log.e(TAG, "AnchorView position is invalid, so do not update position");
            return;
        }
        boolean isMultiWindowMode = res.getConfiguration().windowConfiguration.getWindowingMode() == 5 || res.getConfiguration().windowConfiguration.getWindowingMode() == 6;
        int statusBarHeight = res.getDimensionPixelSize(R.dimen.status_bar_height);
        View appView = WindowManagerGlobal.getInstance().getWindowView(this.mAnchorView.getApplicationWindowToken());
        if (appView == null) {
            ViewRootImpl viewRootImpl = this.mAnchorView.getViewRootImpl();
            if (viewRootImpl != null) {
                appView = viewRootImpl.getView();
            } else {
                Log.e(TAG, "Cannot find app view");
            }
        }
        if (appView != null && (insets = appView.getRootWindowInsets()) != null) {
            statusBarHeight = insets.getSystemWindowInsetTop();
            Log.i(TAG, "top inset = " + statusBarHeight);
        }
        if (isMultiWindowMode || res.getConfiguration().windowConfiguration.isPopOver()) {
            if (isMultiWindowMode && screenPos[1] != windowPos[1]) {
                statusBarHeight = 0;
            }
            screenPos[0] = windowPos[0];
            screenPos[1] = windowPos[1];
        }
        if (this.mAnchorType == 1) {
            int actionButtonWidth = res.getDimensionPixelSize(R.dimen.sem_action_button_min_width_overflow);
            if (res.getConfiguration().getLayoutDirection() == 0) {
                screenPos[0] = (screenPos[0] + width) - actionButtonWidth;
            }
            width = actionButtonWidth;
        }
        int dialogWidth = semGetDialogWidth();
        int xOffset = (dialogWidth - width) / 2;
        params.gravity = 51;
        params.x = screenPos[0] - xOffset;
        params.y = ((screenPos[1] + height) - statusBarHeight) + res.getDimensionPixelSize(R.dimen.sem_dialog_window_margin_in_large);
        this.mWindow.setAttributes(params);
        this.mNeedToUpdate = false;
    }

    public void semSetAnchor(int x, int y) {
        Resources res = this.mContext.getResources();
        if (isSupportAnchor()) {
            Log.i(TAG, "semSetAnchor set x : " + x + ", y : " + y);
            int dialogWidth = semGetDialogWidth();
            int statusBarHeight = res.getConfiguration().windowConfiguration.isPopOver() ? 0 : res.getDimensionPixelSize(R.dimen.status_bar_height);
            WindowManager.LayoutParams l = this.mWindow.getAttributes();
            l.gravity = 51;
            l.x = x - (dialogWidth / 2);
            l.y = (y - statusBarHeight) + res.getDimensionPixelSize(R.dimen.sem_dialog_window_margin_in_large);
            this.mWindow.setAttributes(l);
        }
    }

    private int semGetDialogWidth() {
        Resources res = this.mContext.getResources();
        float dlgWidth = TypedValue.applyDimension(1, res.getConfiguration().screenWidthDp, res.getDisplayMetrics());
        boolean isPortrait = res.getConfiguration().orientation == 1;
        DisplayMetrics metrics = res.getDisplayMetrics();
        TypedValue minWidth = new TypedValue();
        if (isPortrait) {
            this.mContext.getTheme().resolveAttribute(16843607, minWidth, true);
        } else {
            this.mContext.getTheme().resolveAttribute(16843606, minWidth, true);
        }
        int min = 0;
        if (minWidth.type == 5) {
            min = (int) minWidth.getDimension(metrics);
        } else if (minWidth.type == 6) {
            min = (int) minWidth.getFraction(dlgWidth, dlgWidth);
        }
        if (min == 0) {
            res.getValue(R.dimen.sem_config_prefDialogWidth, minWidth, true);
            if (minWidth.type == 5) {
                int min2 = (int) minWidth.getDimension(metrics);
                return min2;
            }
            if (minWidth.type == 6) {
                int min3 = (int) minWidth.getFraction(metrics.widthPixels, metrics.widthPixels);
                return min3;
            }
            return min;
        }
        return min;
    }

    public void hide() {
        if (this.mDecor != null) {
            this.mDecor.setVisibility(8);
        }
    }

    @Override // android.content.DialogInterface
    public void dismiss() {
        semClearAnchorListener();
        if (this.mDismissOverride != null) {
            this.mDismissOverride.run();
        } else if (Looper.myLooper() == this.mHandler.getLooper()) {
            dismissDialog();
        } else {
            this.mHandler.post(this.mDismissAction);
        }
    }

    void dismissDialog() {
        if (this.mDecor == null || !this.mShowing) {
            return;
        }
        if (this.mWindow.isDestroyed()) {
            Log.e(TAG, "Tried to dismissDialog() but the Dialog's window was already destroyed!");
            return;
        }
        try {
            this.mWindowManager.removeViewImmediate(this.mDecor);
        } finally {
            if (this.mActionMode != null) {
                this.mActionMode.finish();
            }
            this.mDecor = null;
            this.mWindow.closeAllPanels();
            onStop();
            this.mShowing = false;
            sendDismissMessage();
        }
    }

    private void sendDismissMessage() {
        if (this.mDismissMessage != null) {
            Message.obtain(this.mDismissMessage).sendToTarget();
        }
    }

    private void sendShowMessage() {
        if (this.mShowMessage != null) {
            Message.obtain(this.mShowMessage).sendToTarget();
        }
    }

    void dispatchOnCreate(Bundle savedInstanceState) {
        if (!this.mCreated) {
            onCreate(savedInstanceState);
            this.mCreated = true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
    }

    protected void onStart() {
        if (this.mActionBar != null) {
            this.mActionBar.setShowHideAnimationEnabled(true);
        }
        if (allowsRegisterDefaultOnBackInvokedCallback() && this.mContext != null && WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this.mContext)) {
            this.mDefaultBackCallback = new OnBackInvokedCallback() { // from class: android.app.Dialog$$ExternalSyntheticLambda4
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    Dialog.this.onBackPressed();
                }
            };
            getOnBackInvokedDispatcher().registerSystemOnBackInvokedCallback(this.mDefaultBackCallback);
        }
    }

    protected void onStop() {
        if (this.mActionBar != null) {
            this.mActionBar.setShowHideAnimationEnabled(false);
        }
        if (this.mDefaultBackCallback != null) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mDefaultBackCallback);
            this.mDefaultBackCallback = null;
        }
    }

    protected boolean allowsRegisterDefaultOnBackInvokedCallback() {
        return true;
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(DIALOG_SHOWING_TAG, this.mShowing);
        if (this.mCreated) {
            bundle.putBundle(DIALOG_HIERARCHY_TAG, this.mWindow.saveHierarchyState());
        }
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Bundle dialogHierarchyState = savedInstanceState.getBundle(DIALOG_HIERARCHY_TAG);
        if (dialogHierarchyState == null) {
            return;
        }
        dispatchOnCreate(savedInstanceState);
        this.mWindow.restoreHierarchyState(dialogHierarchyState);
        if (savedInstanceState.getBoolean(DIALOG_SHOWING_TAG)) {
            show();
        }
    }

    public Window getWindow() {
        return this.mWindow;
    }

    public View getCurrentFocus() {
        if (this.mWindow != null) {
            return this.mWindow.getCurrentFocus();
        }
        return null;
    }

    public <T extends View> T findViewById(int i) {
        return (T) this.mWindow.findViewById(i);
    }

    public final <T extends View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t == null) {
            throw new IllegalArgumentException("ID does not reference a View inside this Dialog");
        }
        return t;
    }

    public void setContentView(int layoutResID) {
        this.mWindow.setContentView(layoutResID);
    }

    public void setContentView(View view) {
        this.mWindow.setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        this.mWindow.setContentView(view, params);
    }

    public void addContentView(View view, ViewGroup.LayoutParams params) {
        this.mWindow.addContentView(view, params);
    }

    public void setTitle(CharSequence title) {
        this.mWindow.setTitle(title);
        this.mWindow.getAttributes().setTitle(title);
    }

    public void setTitle(int titleId) {
        setTitle(this.mContext.getText(titleId));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            event.startTracking();
            return true;
        }
        if (keyCode == 111) {
            if (this.mCancelable) {
                cancel();
                event.startTracking();
                return true;
            }
            if (this.mWindow.shouldCloseOnTouchOutside()) {
                dismiss();
                event.startTracking();
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.isTracking() && !event.isCanceled()) {
            switch (keyCode) {
                case 4:
                    if (!WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this.mContext) || !allowsRegisterDefaultOnBackInvokedCallback()) {
                        onBackPressed();
                        break;
                    }
                    break;
                case 111:
                    break;
            }
            return true;
        }
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return false;
    }

    @Deprecated
    public void onBackPressed() {
        if (this.mCancelable) {
            cancel();
        }
    }

    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mCancelable && this.mShowing && this.mWindow.shouldCloseOnTouch(this.mContext, event)) {
            cancel();
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

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        if (this.mDecor != null) {
            this.mWindowManager.updateViewLayout(this.mDecor, params);
        }
    }

    @Override // android.view.Window.Callback
    public void onContentChanged() {
    }

    public boolean getDialogFocus() {
        return this.mHasFocus;
    }

    @Override // android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        this.mHasFocus = hasFocus;
    }

    @Override // android.view.Window.Callback
    public void onAttachedToWindow() {
    }

    @Override // android.view.Window.Callback
    public void onDetachedFromWindow() {
        semClearAnchorListener();
    }

    @Override // android.view.Window.OnWindowDismissedCallback
    public void onWindowDismissed(boolean finishTask, boolean suppressWindowTransition) {
        dismiss();
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent event) {
        if ((this.mOnKeyListener == null || !this.mOnKeyListener.onKey(this, event.getKeyCode(), event)) && !this.mWindow.superDispatchKeyEvent(event)) {
            return event.dispatch(this, this.mDecor != null ? this.mDecor.getKeyDispatcherState() : null, this);
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        if (this.mWindow.superDispatchKeyShortcutEvent(event)) {
            return true;
        }
        return onKeyShortcut(event.getKeyCode(), event);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.mWindow.superDispatchTouchEvent(ev)) {
            return true;
        }
        getContext().getResources().getConfiguration();
        return onTouchEvent(ev);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(MotionEvent ev) {
        if (this.mWindow.superDispatchTrackballEvent(ev)) {
            return true;
        }
        return onTrackballEvent(ev);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        if (this.mWindow.superDispatchGenericMotionEvent(ev)) {
            return true;
        }
        return onGenericMotionEvent(ev);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        event.setClassName(getClass().getName());
        event.setPackageName(this.mContext.getPackageName());
        ViewGroup.LayoutParams params = getWindow().getAttributes();
        boolean isFullScreen = params.width == -1 && params.height == -1;
        event.setFullScreen(isFullScreen);
        return false;
    }

    @Override // android.view.Window.Callback
    public View onCreatePanelView(int featureId) {
        return null;
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == 0) {
            return onCreateOptionsMenu(menu);
        }
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (featureId == 0) {
            return onPrepareOptionsMenu(menu) && menu.hasVisibleItems();
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == 8) {
            this.mActionBar.dispatchMenuVisibilityChanged(true);
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onPanelClosed(int featureId, Menu menu) {
        if (featureId == 8) {
            this.mActionBar.dispatchMenuVisibilityChanged(false);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void onOptionsMenuClosed(Menu menu) {
    }

    public void openOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            this.mWindow.openPanel(0, null);
        }
    }

    public void closeOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            this.mWindow.closePanel(0);
        }
    }

    public void invalidateOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            this.mWindow.invalidatePanelMenu(0);
        }
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
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

    public boolean onContextItemSelected(MenuItem item) {
        return false;
    }

    public void onContextMenuClosed(Menu menu) {
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested(SearchEvent searchEvent) {
        this.mSearchEvent = searchEvent;
        return onSearchRequested();
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        SearchManager searchManager = (SearchManager) this.mContext.getSystemService("search");
        ComponentName appName = getAssociatedActivity();
        if (appName != null && searchManager.getSearchableInfo(appName) != null) {
            searchManager.startSearch(null, false, appName, null, false);
            dismiss();
            return true;
        }
        return false;
    }

    public final SearchEvent getSearchEvent() {
        return this.mSearchEvent;
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        if (this.mActionBar != null && this.mActionModeTypeStarting == 0) {
            return this.mActionBar.startActionMode(callback);
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
    public void onActionModeStarted(ActionMode mode) {
        this.mActionMode = mode;
    }

    @Override // android.view.Window.Callback
    public void onActionModeFinished(ActionMode mode) {
        if (mode == this.mActionMode) {
            this.mActionMode = null;
        }
    }

    private ComponentName getAssociatedActivity() {
        Activity activity = this.mOwnerActivity;
        Context context = getContext();
        while (true) {
            Context context2 = null;
            if (activity != null || context == null) {
                break;
            }
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else {
                if (context instanceof ContextWrapper) {
                    context2 = ((ContextWrapper) context).getBaseContext();
                }
                context = context2;
            }
        }
        if (activity == null) {
            return null;
        }
        return activity.getComponentName();
    }

    public void takeKeyEvents(boolean get) {
        this.mWindow.takeKeyEvents(get);
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

    public void setCancelable(boolean flag) {
        this.mCancelable = flag;
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        if (cancel && !this.mCancelable) {
            this.mCancelable = true;
        }
        this.mWindow.setCloseOnTouchOutside(cancel);
    }

    @Override // android.content.DialogInterface
    public void cancel() {
        if (!this.mCanceled && this.mCancelMessage != null) {
            this.mCanceled = true;
            Message.obtain(this.mCancelMessage).sendToTarget();
        }
        dismiss();
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener listener) {
        if (this.mCancelAndDismissTaken != null) {
            throw new IllegalStateException("OnCancelListener is already taken by " + this.mCancelAndDismissTaken + " and can not be replaced.");
        }
        if (listener != null) {
            this.mCancelMessage = this.mListenersHandler.obtainMessage(68, listener);
        } else {
            this.mCancelMessage = null;
        }
    }

    public void setCancelMessage(Message msg) {
        this.mCancelMessage = msg;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        if (this.mCancelAndDismissTaken != null) {
            throw new IllegalStateException("OnDismissListener is already taken by " + this.mCancelAndDismissTaken + " and can not be replaced.");
        }
        if (listener != null) {
            this.mDismissMessage = this.mListenersHandler.obtainMessage(67, listener);
        } else {
            this.mDismissMessage = null;
        }
    }

    public void setOnShowListener(DialogInterface.OnShowListener listener) {
        if (listener != null) {
            this.mShowMessage = this.mListenersHandler.obtainMessage(69, listener);
        } else {
            this.mShowMessage = null;
        }
    }

    public void setDismissMessage(Message msg) {
        this.mDismissMessage = msg;
    }

    public void setDismissOverride(Runnable override) {
        this.mDismissOverride = override;
    }

    public boolean takeCancelAndDismissListeners(String msg, DialogInterface.OnCancelListener cancel, DialogInterface.OnDismissListener dismiss) {
        if (this.mCancelAndDismissTaken != null) {
            this.mCancelAndDismissTaken = null;
        } else if (this.mCancelMessage != null || this.mDismissMessage != null) {
            return false;
        }
        setOnCancelListener(cancel);
        setOnDismissListener(dismiss);
        this.mCancelAndDismissTaken = msg;
        return true;
    }

    public final void setVolumeControlStream(int streamType) {
        getWindow().setVolumeControlStream(streamType);
    }

    public final int getVolumeControlStream() {
        return getWindow().getVolumeControlStream();
    }

    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.mOnKeyListener = onKeyListener;
    }

    private static final class ListenersHandler extends Handler {
        private final WeakReference<DialogInterface> mDialog;

        public ListenersHandler(Dialog dialog) {
            this.mDialog = new WeakReference<>(dialog);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 67:
                    ((DialogInterface.OnDismissListener) msg.obj).onDismiss(this.mDialog.get());
                    break;
                case 68:
                    ((DialogInterface.OnCancelListener) msg.obj).onCancel(this.mDialog.get());
                    break;
                case 69:
                    ((DialogInterface.OnShowListener) msg.obj).onShow(this.mDialog.get());
                    break;
            }
        }
    }

    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return this.mWindow.getOnBackInvokedDispatcher();
    }

    private void semClearAnchorListener() {
        if (this.mRemoveOnLayoutChangeListnerRunnable == null) {
            return;
        }
        this.mRemoveOnLayoutChangeListnerRunnable.run();
        this.mRemoveOnLayoutChangeListnerRunnable = null;
    }

    private boolean isEmbedActivityMode() {
        return this.mContext.getResources().getConfiguration().windowConfiguration.getEmbedActivityMode() == 2 || this.mContext.getResources().getConfiguration().windowConfiguration.getEmbedActivityMode() == 3;
    }

    private boolean isSupportAnchor() {
        if (isEmbedActivityMode() || this.mContext.getResources().getConfiguration().windowConfiguration.isPopOver() || this.mContext.getResources().getBoolean(R.bool.sem_config_dialogLargeScreen)) {
            return true;
        }
        Log.i(TAG, "semSetAnchor isn't supported");
        return false;
    }

    private Activity getActivityContext(Context context) {
        Activity activity = null;
        Context tempContext = context;
        for (int count = 0; activity == null && tempContext != null && count < 100; count++) {
            if (tempContext instanceof Activity) {
                activity = (Activity) tempContext;
            } else {
                tempContext = tempContext instanceof ContextWrapper ? ((ContextWrapper) tempContext).getBaseContext() : null;
            }
        }
        return activity;
    }
}
