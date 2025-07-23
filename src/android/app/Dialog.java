package android.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayInfo;
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
import com.android.internal.policy.DecorView;
import com.android.internal.policy.PhoneWindow;
import com.samsung.android.rune.CoreRune;
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

    protected boolean allowsRegisterDefaultOnBackInvokedCallback() {
        return true;
    }

    @Override // android.view.Window.Callback
    public void onAttachedToWindow() {
    }

    @Override // android.view.Window.Callback
    public void onContentChanged() {
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onContextMenuClosed(Menu menu) {
    }

    protected void onCreate(Bundle bundle) {
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override // android.view.Window.Callback
    public View onCreatePanelView(int i) {
        return null;
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

    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onOptionsMenuClosed(Menu menu) {
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.mNeedToUpdate = isNeedToUpdateAttributes(view);
        alignToAnchor();
    }

    public Dialog(Context context) {
        this(context, 0, true);
    }

    public Dialog(Context context, int i) {
        this(context, i, true);
    }

    Dialog(Context context, int i, boolean z) {
        ActivityInfo activityInfo;
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
            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                Dialog.this.lambda$new$0(view, i2, i3, i4, i5, i6, i7, i8, i9);
            }
        };
        this.mRemoveOnLayoutChangeListnerRunnable = null;
        if (z) {
            if (i == 0) {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(16843528, typedValue, true);
                i = typedValue.resourceId;
            }
            this.mContext = new ContextThemeWrapper(context, i);
        } else {
            this.mContext = context;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mWindowManager = windowManager;
        PhoneWindow phoneWindow = new PhoneWindow(this.mContext);
        this.mWindow = phoneWindow;
        phoneWindow.setCallback(this);
        phoneWindow.setOnWindowDismissedCallback(this);
        phoneWindow.setOnWindowSwipeDismissedCallback(new Window.OnWindowSwipeDismissedCallback() { // from class: android.app.Dialog$$ExternalSyntheticLambda2
            @Override // android.view.Window.OnWindowSwipeDismissedCallback
            public final void onWindowSwipeDismissed() {
                Dialog.this.lambda$new$1();
            }
        });
        phoneWindow.setWindowManager(windowManager, null, null);
        phoneWindow.setGravity(17);
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                this.mIsSamsungBasicInteraction = SAMSUNG_BASIC_INTERACTION_METADATA_VALUE.equals(applicationInfo.metaData.getString(SAMSUNG_BASIC_INTERACTION_METADATA_NAME));
            }
        } catch (Exception e) {
            Log.e(TAG, "exceptioin!! " + e);
        }
        Activity activityContext = getActivityContext(context);
        boolean equals = (activityContext == null || (activityInfo = activityContext.getActivityInfo()) == null || activityInfo.metaData == null) ? false : SAMSUNG_BASIC_INTERACTION_METADATA_VALUE.equals(activityInfo.metaData.getString(SAMSUNG_BASIC_INTERACTION_METADATA_NAME));
        TypedValue typedValue2 = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue2, false);
        if (typedValue2.data != 0) {
            this.mIsDeviceDefault = true;
            TypedValue typedValue3 = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, typedValue3, true);
            this.mIsDeviceDefaultDark = typedValue3.data != 0;
            if (!mIsDarkActionBar) {
                phoneWindow.setGravity(80);
            }
        }
        Log.i(TAG, "mIsDeviceDefault = " + this.mIsDeviceDefault + ", mIsSamsungBasicInteraction = " + this.mIsSamsungBasicInteraction + ", isMetaDataInActivity = " + equals);
        if (this.mIsSamsungBasicInteraction || equals) {
            phoneWindow.setGravity(80);
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
    protected Dialog(Context context, boolean z, Message message) {
        this(context);
        this.mCancelable = z;
        this.mCancelMessage = message;
    }

    protected Dialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        this(context);
        this.mCancelable = z;
        setOnCancelListener(onCancelListener);
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
        View view = this.mDecor;
        return view != null && view.getVisibility() == 0;
    }

    public void create() {
        if (this.mCreated) {
            return;
        }
        dispatchOnCreate(null);
    }

    public void show() {
        boolean z;
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
            this.mWindow.getDecorView().dispatchConfigurationChanged(this.mContext.getResources().getConfiguration());
        }
        onStart();
        View decorView = this.mWindow.getDecorView();
        this.mDecor = decorView;
        if (this.mIsDeviceDefault && (decorView instanceof DecorView)) {
            ((DecorView) decorView).semSetIsDialog();
        }
        if (this.mActionBar == null && this.mWindow.hasFeature(8)) {
            ApplicationInfo applicationInfo = this.mContext.getApplicationInfo();
            this.mWindow.setDefaultIcon(applicationInfo.icon);
            this.mWindow.setDefaultLogo(applicationInfo.logo);
            this.mActionBar = new WindowDecorActionBar(this);
        }
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        if ((attributes.softInputMode & 256) == 0) {
            attributes.softInputMode |= 256;
            z = true;
        } else {
            z = false;
        }
        if (this.mIsDeviceDefault) {
            if (this.mIsDeviceDefaultDark) {
                attributes.dimAmount = DIALOG_DARK_DIM_AMOUNT;
            } else {
                attributes.dimAmount = Settings.System.getInt(this.mContext.getContentResolver(), "accessibility_reduce_transparency", 0) == 1 ? DIALOG_REDUCE_TRANSPARENCY_DIM_AMOUNT : 0.18f;
            }
            if ((this instanceof ProgressDialog) && ((ProgressDialog) this).getCurrentProgressStyle() == 1000) {
                int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.tw_progress_circle_dialog_size);
                attributes.height = dimensionPixelSize;
                attributes.width = dimensionPixelSize;
            }
            attributes.dimDuration = this.mContext.getResources().getInteger(R.integer.sem_dialog_dim_duration);
        }
        this.mWindowManager.addView(this.mDecor, attributes);
        if (this.mIsDeviceDefault) {
            float f = attributes.dimAmount;
        }
        if (z) {
            attributes.softInputMode &= -257;
        }
        if (this.mIsDeviceDefault && attributes.width > 0) {
            Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.tw_dialog_background_material, this.mContext.getTheme());
            View view = this.mDecor;
            if (view != null && view.getBackground() != null && drawable.getConstantState() != null && drawable.getConstantState().equals(this.mDecor.getBackground().getConstantState())) {
                this.mDecor.setBackground(new InsetDrawable(drawable, 0, 0, 0, this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_dialog_background_inset_vertical)));
            }
        }
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN && this.mIsDeviceDefault && this.mContext.getDisplayId() == 1 && attributes.type != 2011 && hasCutoutOnBottom(this.mContext.getDisplay())) {
            Drawable drawable2 = this.mContext.getResources().getDrawable(R.drawable.tw_dialog_background_material, this.mContext.getTheme());
            View view2 = this.mDecor;
            if (view2 != null && view2.getBackground() != null && drawable2.getConstantState() != null && drawable2.getConstantState().equals(this.mDecor.getBackground().getConstantState())) {
                getWindow().setBackgroundDrawableResource(R.drawable.tw_dialog_background_material_cover_screen);
            }
        }
        this.mShowing = true;
        sendShowMessage();
    }

    public void semSetAnchor(View view) {
        semSetAnchor(view, 0);
    }

    public void semSetAnchor(View view, int i) {
        if (isSupportAnchor()) {
            Log.i(TAG, "semSetAnchor anchorView = " + view + " , anchorType : " + i);
            this.mAnchorView = view;
            this.mAnchorType = i;
            View rootView = view.getRootView();
            this.mRootView = rootView;
            if (rootView != null) {
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

    private boolean isNeedToUpdateAttributes(View view) {
        boolean z;
        int i = view.getContext().getResources().getConfiguration().orientation;
        int i2 = view.getContext().getResources().getConfiguration().smallestScreenWidthDp;
        boolean z2 = true;
        if (i != this.mRootViewOrientation) {
            this.mRootViewOrientation = i;
            z = true;
        } else {
            z = false;
        }
        if (i2 != this.mRootViewSwWidthDp) {
            this.mRootViewSwWidthDp = i2;
            z = true;
        }
        if (this.mReconsiderForAlignToAnchor) {
            this.mReconsiderForAlignToAnchor = false;
            Log.i(TAG, "Reconsidered to update LayoutParams");
        } else {
            z2 = z;
        }
        if (z2) {
            Log.i(TAG, "Dialog LayoutParams update is needed");
        }
        return z2;
    }

    private void alignToAnchor() {
        View view;
        WindowInsets rootWindowInsets;
        Resources resources = this.mContext.getResources();
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        if (!isSupportAnchor() || (view = this.mAnchorView) == null) {
            Log.e(TAG, "AnchorView is null state or not on Large Screen");
            attributes.gravity = 81;
            attributes.x = 0;
            attributes.y = 0;
            this.mWindow.setAttributes(attributes);
            return;
        }
        if (this.mNeedToUpdate) {
            int[] iArr = new int[2];
            int[] iArr2 = new int[2];
            int width = view.getWidth();
            int height = this.mAnchorView.getHeight();
            this.mAnchorView.getLocationOnScreen(iArr);
            this.mAnchorView.getLocationInWindow(iArr2);
            if (this.mAnchorView.getVisibility() == 0 && iArr[0] <= 0 && iArr2[0] <= 0 && iArr[1] <= 0 && iArr2[1] <= 0) {
                this.mReconsiderForAlignToAnchor = true;
                Log.e(TAG, "AnchorView position is invalid, so do not update position");
                return;
            }
            boolean z = resources.getConfiguration().windowConfiguration.getWindowingMode() == 5 || resources.getConfiguration().windowConfiguration.getWindowingMode() == 6;
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.status_bar_height);
            View windowView = WindowManagerGlobal.getInstance().getWindowView(this.mAnchorView.getApplicationWindowToken());
            if (windowView == null) {
                ViewRootImpl viewRootImpl = this.mAnchorView.getViewRootImpl();
                if (viewRootImpl != null) {
                    windowView = viewRootImpl.getView();
                } else {
                    Log.e(TAG, "Cannot find app view");
                }
            }
            if (windowView != null && (rootWindowInsets = windowView.getRootWindowInsets()) != null) {
                dimensionPixelSize = rootWindowInsets.getSystemWindowInsetTop();
                Log.i(TAG, "top inset = " + dimensionPixelSize);
            }
            if (z || resources.getConfiguration().windowConfiguration.isPopOver()) {
                if (z && iArr[1] != iArr2[1]) {
                    dimensionPixelSize = 0;
                }
                iArr[0] = iArr2[0];
                iArr[1] = iArr2[1];
            }
            if (this.mAnchorType == 1) {
                int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.sem_action_button_min_width_overflow);
                if (resources.getConfiguration().getLayoutDirection() == 0) {
                    iArr[0] = (iArr[0] + width) - dimensionPixelSize2;
                }
                width = dimensionPixelSize2;
            }
            int semGetDialogWidth = (semGetDialogWidth() - width) / 2;
            attributes.gravity = 51;
            attributes.x = iArr[0] - semGetDialogWidth;
            attributes.y = ((iArr[1] + height) - dimensionPixelSize) + resources.getDimensionPixelSize(R.dimen.sem_dialog_window_margin_in_large);
            this.mWindow.setAttributes(attributes);
            this.mNeedToUpdate = false;
        }
    }

    public void semSetAnchor(int i, int i2) {
        Resources resources = this.mContext.getResources();
        if (isSupportAnchor()) {
            Log.i(TAG, "semSetAnchor set x : " + i + ", y : " + i2);
            int semGetDialogWidth = semGetDialogWidth();
            int dimensionPixelSize = resources.getConfiguration().windowConfiguration.isPopOver() ? 0 : resources.getDimensionPixelSize(R.dimen.status_bar_height);
            WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
            attributes.gravity = 51;
            attributes.x = i - (semGetDialogWidth / 2);
            attributes.y = (i2 - dimensionPixelSize) + resources.getDimensionPixelSize(R.dimen.sem_dialog_window_margin_in_large);
            this.mWindow.setAttributes(attributes);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int semGetDialogWidth() {
        /*
            r8 = this;
            android.content.Context r0 = r8.mContext
            android.content.res.Resources r0 = r0.getResources()
            android.content.res.Configuration r1 = r0.getConfiguration()
            int r1 = r1.screenWidthDp
            float r1 = (float) r1
            android.util.DisplayMetrics r2 = r0.getDisplayMetrics()
            r3 = 1
            float r1 = android.util.TypedValue.applyDimension(r3, r1, r2)
            android.content.res.Configuration r2 = r0.getConfiguration()
            int r2 = r2.orientation
            r4 = 0
            if (r2 != r3) goto L21
            r2 = r3
            goto L22
        L21:
            r2 = r4
        L22:
            android.util.DisplayMetrics r5 = r0.getDisplayMetrics()
            android.util.TypedValue r6 = new android.util.TypedValue
            r6.<init>()
            if (r2 == 0) goto L3a
            android.content.Context r8 = r8.mContext
            android.content.res.Resources$Theme r8 = r8.getTheme()
            r2 = 16843607(0x1010357, float:2.3695954E-38)
            r8.resolveAttribute(r2, r6, r3)
            goto L46
        L3a:
            android.content.Context r8 = r8.mContext
            android.content.res.Resources$Theme r8 = r8.getTheme()
            r2 = 16843606(0x1010356, float:2.3695951E-38)
            r8.resolveAttribute(r2, r6, r3)
        L46:
            int r8 = r6.type
            r2 = 6
            r7 = 5
            if (r8 != r7) goto L52
            float r8 = r6.getDimension(r5)
        L50:
            int r4 = (int) r8
            goto L5b
        L52:
            int r8 = r6.type
            if (r8 != r2) goto L5b
            float r8 = r6.getFraction(r1, r1)
            goto L50
        L5b:
            if (r4 != 0) goto L7c
            r8 = 17106046(0x105047e, float:2.4431465E-38)
            r0.getValue(r8, r6, r3)
            int r8 = r6.type
            if (r8 != r7) goto L6d
            float r8 = r6.getDimension(r5)
        L6b:
            int r8 = (int) r8
            return r8
        L6d:
            int r8 = r6.type
            if (r8 != r2) goto L7c
            int r8 = r5.widthPixels
            float r8 = (float) r8
            int r0 = r5.widthPixels
            float r0 = (float) r0
            float r8 = r6.getFraction(r8, r0)
            goto L6b
        L7c:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.Dialog.semGetDialogWidth():int");
    }

    public void hide() {
        View view = this.mDecor;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    @Override // android.content.DialogInterface
    public void dismiss() {
        semClearAnchorListener();
        Runnable runnable = this.mDismissOverride;
        if (runnable != null) {
            runnable.run();
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
            ActionMode actionMode = this.mActionMode;
            if (actionMode != null) {
                actionMode.finish();
            }
            this.mDecor = null;
            this.mWindow.closeAllPanels();
            onStop();
            this.mShowing = false;
            sendDismissMessage();
        }
    }

    private void sendDismissMessage() {
        Message message = this.mDismissMessage;
        if (message != null) {
            Message.obtain(message).sendToTarget();
        }
    }

    private void sendShowMessage() {
        Message message = this.mShowMessage;
        if (message != null) {
            Message.obtain(message).sendToTarget();
        }
    }

    void dispatchOnCreate(Bundle bundle) {
        if (this.mCreated) {
            return;
        }
        onCreate(bundle);
        this.mCreated = true;
    }

    protected void onStart() {
        Context context;
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(true);
        }
        if (allowsRegisterDefaultOnBackInvokedCallback() && (context = this.mContext) != null && WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(context)) {
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
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(false);
        }
        if (this.mDefaultBackCallback != null) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mDefaultBackCallback);
            this.mDefaultBackCallback = null;
        }
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(DIALOG_SHOWING_TAG, this.mShowing);
        if (this.mCreated) {
            bundle.putBundle(DIALOG_HIERARCHY_TAG, this.mWindow.saveHierarchyState());
        }
        return bundle;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(DIALOG_HIERARCHY_TAG);
        if (bundle2 == null) {
            return;
        }
        dispatchOnCreate(bundle);
        this.mWindow.restoreHierarchyState(bundle2);
        if (bundle.getBoolean(DIALOG_SHOWING_TAG)) {
            show();
        }
    }

    public Window getWindow() {
        return this.mWindow;
    }

    public View getCurrentFocus() {
        Window window = this.mWindow;
        if (window != null) {
            return window.getCurrentFocus();
        }
        return null;
    }

    public <T extends View> T findViewById(int i) {
        return (T) this.mWindow.findViewById(i);
    }

    public final <T extends View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Dialog");
    }

    public void setContentView(int i) {
        this.mWindow.setContentView(i);
    }

    public void setContentView(View view) {
        this.mWindow.setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mWindow.setContentView(view, layoutParams);
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mWindow.addContentView(view, layoutParams);
    }

    public void setTitle(CharSequence charSequence) {
        this.mWindow.setTitle(charSequence);
        this.mWindow.getAttributes().setTitle(charSequence);
    }

    public void setTitle(int i) {
        setTitle(this.mContext.getText(i));
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            keyEvent.startTracking();
            return true;
        }
        if (i != 111) {
            return false;
        }
        if (this.mCancelable) {
            cancel();
            keyEvent.startTracking();
            return true;
        }
        if (!this.mWindow.shouldCloseOnTouchOutside()) {
            return false;
        }
        dismiss();
        keyEvent.startTracking();
        return true;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (!keyEvent.isTracking() || keyEvent.isCanceled()) {
            return false;
        }
        if (i != 4) {
            return i == 111;
        }
        if (WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this.mContext) && allowsRegisterDefaultOnBackInvokedCallback()) {
            return false;
        }
        onBackPressed();
        return true;
    }

    @Deprecated
    public void onBackPressed() {
        if (this.mCancelable) {
            cancel();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mCancelable || !this.mShowing || !this.mWindow.shouldCloseOnTouch(this.mContext, motionEvent)) {
            return false;
        }
        cancel();
        return true;
    }

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        View view = this.mDecor;
        if (view != null) {
            this.mWindowManager.updateViewLayout(view, layoutParams);
        }
    }

    public boolean getDialogFocus() {
        return this.mHasFocus;
    }

    @Override // android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        this.mHasFocus = z;
    }

    @Override // android.view.Window.Callback
    public void onDetachedFromWindow() {
        semClearAnchorListener();
    }

    @Override // android.view.Window.OnWindowDismissedCallback
    public void onWindowDismissed(boolean z, boolean z2) {
        dismiss();
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        DialogInterface.OnKeyListener onKeyListener = this.mOnKeyListener;
        if ((onKeyListener != null && onKeyListener.onKey(this, keyEvent.getKeyCode(), keyEvent)) || this.mWindow.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        View view = this.mDecor;
        return keyEvent.dispatch(this, view != null ? view.getKeyDispatcherState() : null, this);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if (this.mWindow.superDispatchKeyShortcutEvent(keyEvent)) {
            return true;
        }
        return onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mWindow.superDispatchTouchEvent(motionEvent)) {
            return true;
        }
        getContext().getResources().getConfiguration();
        return onTouchEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        if (this.mWindow.superDispatchTrackballEvent(motionEvent)) {
            return true;
        }
        return onTrackballEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mWindow.superDispatchGenericMotionEvent(motionEvent)) {
            return true;
        }
        return onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName(getClass().getName());
        accessibilityEvent.setPackageName(this.mContext.getPackageName());
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        accessibilityEvent.setFullScreen(attributes.width == -1 && attributes.height == -1);
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int i, Menu menu) {
        if (i == 0) {
            return onCreateOptionsMenu(menu);
        }
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int i, View view, Menu menu) {
        if (i == 0) {
            return onPrepareOptionsMenu(menu) && menu.hasVisibleItems();
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int i, Menu menu) {
        if (i == 8) {
            this.mActionBar.dispatchMenuVisibilityChanged(true);
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public void onPanelClosed(int i, Menu menu) {
        if (i == 8) {
            this.mActionBar.dispatchMenuVisibilityChanged(false);
        }
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

    public void registerForContextMenu(View view) {
        view.setOnCreateContextMenuListener(this);
    }

    public void unregisterForContextMenu(View view) {
        view.setOnCreateContextMenuListener(null);
    }

    public void openContextMenu(View view) {
        view.showContextMenu();
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested(SearchEvent searchEvent) {
        this.mSearchEvent = searchEvent;
        return onSearchRequested();
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        SearchManager searchManager = (SearchManager) this.mContext.getSystemService("search");
        ComponentName associatedActivity = getAssociatedActivity();
        if (associatedActivity == null || searchManager.getSearchableInfo(associatedActivity) == null) {
            return false;
        }
        searchManager.startSearch(null, false, associatedActivity, null, false);
        dismiss();
        return true;
    }

    public final SearchEvent getSearchEvent() {
        return this.mSearchEvent;
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        ActionBar actionBar = this.mActionBar;
        if (actionBar == null || this.mActionModeTypeStarting != 0) {
            return null;
        }
        return actionBar.startActionMode(callback);
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

    @Override // android.view.Window.Callback
    public void onActionModeStarted(ActionMode actionMode) {
        this.mActionMode = actionMode;
    }

    @Override // android.view.Window.Callback
    public void onActionModeFinished(ActionMode actionMode) {
        if (actionMode == this.mActionMode) {
            this.mActionMode = null;
        }
    }

    private ComponentName getAssociatedActivity() {
        Activity activity = this.mOwnerActivity;
        Context context = getContext();
        while (activity == null && context != null) {
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else {
                context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
            }
        }
        if (activity == null) {
            return null;
        }
        return activity.getComponentName();
    }

    public void takeKeyEvents(boolean z) {
        this.mWindow.takeKeyEvents(z);
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

    public void setCancelable(boolean z) {
        this.mCancelable = z;
    }

    public void setCanceledOnTouchOutside(boolean z) {
        if (z && !this.mCancelable) {
            this.mCancelable = true;
        }
        this.mWindow.setCloseOnTouchOutside(z);
    }

    @Override // android.content.DialogInterface
    public void cancel() {
        Message message;
        if (!this.mCanceled && (message = this.mCancelMessage) != null) {
            this.mCanceled = true;
            Message.obtain(message).sendToTarget();
        }
        dismiss();
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (this.mCancelAndDismissTaken != null) {
            throw new IllegalStateException("OnCancelListener is already taken by " + this.mCancelAndDismissTaken + " and can not be replaced.");
        }
        if (onCancelListener != null) {
            this.mCancelMessage = this.mListenersHandler.obtainMessage(68, onCancelListener);
        } else {
            this.mCancelMessage = null;
        }
    }

    public void setCancelMessage(Message message) {
        this.mCancelMessage = message;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        if (this.mCancelAndDismissTaken != null) {
            throw new IllegalStateException("OnDismissListener is already taken by " + this.mCancelAndDismissTaken + " and can not be replaced.");
        }
        if (onDismissListener != null) {
            this.mDismissMessage = this.mListenersHandler.obtainMessage(67, onDismissListener);
        } else {
            this.mDismissMessage = null;
        }
    }

    public void setOnShowListener(DialogInterface.OnShowListener onShowListener) {
        if (onShowListener != null) {
            this.mShowMessage = this.mListenersHandler.obtainMessage(69, onShowListener);
        } else {
            this.mShowMessage = null;
        }
    }

    public void setDismissMessage(Message message) {
        this.mDismissMessage = message;
    }

    public void setDismissOverride(Runnable runnable) {
        this.mDismissOverride = runnable;
    }

    public boolean takeCancelAndDismissListeners(String str, DialogInterface.OnCancelListener onCancelListener, DialogInterface.OnDismissListener onDismissListener) {
        if (this.mCancelAndDismissTaken != null) {
            this.mCancelAndDismissTaken = null;
        } else if (this.mCancelMessage != null || this.mDismissMessage != null) {
            return false;
        }
        setOnCancelListener(onCancelListener);
        setOnDismissListener(onDismissListener);
        this.mCancelAndDismissTaken = str;
        return true;
    }

    public final void setVolumeControlStream(int i) {
        getWindow().setVolumeControlStream(i);
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
        public void handleMessage(Message message) {
            switch (message.what) {
                case 67:
                    ((DialogInterface.OnDismissListener) message.obj).onDismiss(this.mDialog.get());
                    break;
                case 68:
                    ((DialogInterface.OnCancelListener) message.obj).onCancel(this.mDialog.get());
                    break;
                case 69:
                    ((DialogInterface.OnShowListener) message.obj).onShow(this.mDialog.get());
                    break;
            }
        }
    }

    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return this.mWindow.getOnBackInvokedDispatcher();
    }

    private void semClearAnchorListener() {
        Runnable runnable = this.mRemoveOnLayoutChangeListnerRunnable;
        if (runnable == null) {
            return;
        }
        runnable.run();
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
        for (int i = 0; activity == null && context != null && i < 100; i++) {
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else {
                context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
            }
        }
        return activity;
    }

    private boolean hasCutoutOnBottom(Display display) {
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        if (displayInfo.displayCutout != null) {
            return !displayInfo.displayCutout.getBoundingRectBottom().isEmpty();
        }
        return false;
    }
}
