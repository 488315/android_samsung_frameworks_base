package android.widget;

import android.app.INotificationManager;
import android.app.ITransientNotificationCallback;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.IWindowManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.IAccessibilityManager;
import com.android.internal.R;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class ToastPresenter {
    public static final int DEVICE_DEFAULT_TEXT_TOAST_LAYOUT = 17367430;
    private static final long LONG_DURATION_TIMEOUT = 7000;
    private static final float MAX_FONT_SCALE = 1.3f;
    private static final long SHORT_DURATION_TIMEOUT = 4000;
    private static final String TAG = "ToastPresenter";
    public static final int TEXT_TOAST_LAYOUT = 17367475;
    public static final int TEXT_TOAST_LAYOUT_WITH_ICON = 17367476;
    private static final String WINDOW_TITLE = "Toast";
    static final boolean localLOGV = Debug.semIsProductDev();
    private final IAccessibilityManager mAccessibilityManagerService;
    private final WeakReference<Context> mContext;
    private final String mContextPackageName;
    private final INotificationManager mNotificationManager;
    private final String mPackageName;
    private final WindowManager.LayoutParams mParams = createLayoutParams();
    private final Resources mResources;
    private IBinder mToken;
    private View mView;

    public static View getTextToastView(Context context, CharSequence text) {
        TypedValue outValue = new TypedValue();
        boolean isDeviceDefault = context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, outValue, true) && outValue.data != 0;
        View view = isDeviceDefault ? LayoutInflater.from(context).inflate(17367430, (ViewGroup) null) : LayoutInflater.from(context).inflate(17367475, (ViewGroup) null);
        TextView textView = (TextView) view.findViewById(16908299);
        textView.lambda$setTextAsync$0(text);
        semCheckMaxFontScale(context, textView, context.getResources().getDimensionPixelSize(R.dimen.sem_toast_text_size));
        return view;
    }

    public static View getTextToastViewWithIcon(Context context, CharSequence text, Drawable icon) {
        if (icon == null) {
            return getTextToastView(context, text);
        }
        View view = LayoutInflater.from(context).inflate(17367476, (ViewGroup) null);
        TextView textView = (TextView) view.findViewById(16908299);
        textView.lambda$setTextAsync$0(text);
        ImageView imageView = (ImageView) view.findViewById(16908294);
        if (imageView != null) {
            imageView.lambda$setImageURIAsync$0(icon);
        }
        return view;
    }

    public ToastPresenter(Context context, IAccessibilityManager accessibilityManager, INotificationManager notificationManager, String packageName) {
        this.mContext = new WeakReference<>(context);
        this.mResources = context.getResources();
        this.mNotificationManager = notificationManager;
        this.mPackageName = packageName;
        this.mContextPackageName = context.getPackageName();
        this.mAccessibilityManagerService = accessibilityManager;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public WindowManager.LayoutParams getLayoutParams() {
        return this.mParams;
    }

    public View getView() {
        return this.mView;
    }

    public IBinder getToken() {
        return this.mToken;
    }

    private WindowManager.LayoutParams createLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = -2;
        params.width = -2;
        params.format = -3;
        params.windowAnimations = 16973828;
        params.type = 2005;
        params.setFitInsetsIgnoringVisibility(true);
        params.setTitle(WINDOW_TITLE);
        params.flags = 152;
        setShowForAllUsersIfApplicable(params, this.mPackageName);
        params.receiveInsetsIgnoringZOrder = true;
        params.setFitInsetsTypes(params.getFitInsetsTypes() | WindowInsets.Type.ime());
        return params;
    }

    private void adjustLayoutParams(WindowManager.LayoutParams params, IBinder windowToken, int duration, int gravity, int xOffset, int yOffset, float horizontalMargin, float verticalMargin, boolean removeWindowAnimations) {
        Configuration config = this.mResources.getConfiguration();
        int absGravity = Gravity.getAbsoluteGravity(gravity, config.getLayoutDirection());
        params.gravity = absGravity;
        if ((absGravity & 7) == 7) {
            params.horizontalWeight = 1.0f;
        }
        if ((absGravity & 112) == 112) {
            params.verticalWeight = 1.0f;
        }
        params.x = xOffset;
        params.y = yOffset;
        params.horizontalMargin = horizontalMargin;
        params.verticalMargin = verticalMargin;
        params.packageName = this.mContextPackageName;
        params.hideTimeoutMilliseconds = duration == 1 ? LONG_DURATION_TIMEOUT : SHORT_DURATION_TIMEOUT;
        params.token = windowToken;
        if (removeWindowAnimations && params.windowAnimations == 16973828) {
            params.windowAnimations = 0;
        }
    }

    public void updateLayoutParams(int xOffset, int yOffset, float horizontalMargin, float verticalMargin, int gravity) {
        Preconditions.checkState(this.mView != null, "Toast must be showing to update its layout parameters.");
        Configuration config = this.mResources.getConfiguration();
        this.mParams.gravity = Gravity.getAbsoluteGravity(gravity, config.getLayoutDirection());
        this.mParams.x = xOffset;
        this.mParams.y = yOffset;
        this.mParams.horizontalMargin = horizontalMargin;
        this.mParams.verticalMargin = verticalMargin;
        this.mView.setLayoutParams(this.mParams);
    }

    private void setShowForAllUsersIfApplicable(WindowManager.LayoutParams params, String packageName) {
        if (isCrossUserPackage(packageName)) {
            params.privateFlags = 16;
        }
    }

    private boolean isCrossUserPackage(String packageName) {
        String[] packages = this.mResources.getStringArray(R.array.config_toastCrossUserPackages);
        return ArrayUtils.contains(packages, packageName);
    }

    public void show(View view, IBinder token, IBinder windowToken, int duration, int gravity, int xOffset, int yOffset, float horizontalMargin, float verticalMargin, ITransientNotificationCallback callback) {
        show(view, token, windowToken, duration, gravity, xOffset, yOffset, horizontalMargin, verticalMargin, callback, false);
    }

    public void show(View view, IBinder token, IBinder windowToken, int duration, int gravity, int xOffset, int yOffset, float horizontalMargin, float verticalMargin, ITransientNotificationCallback callback, boolean removeWindowAnimations) {
        boolean isDeskTopMode;
        int yOffset2;
        int yOffset3;
        boolean z = false;
        Preconditions.checkState(this.mView == null, "Only one toast at a time is allowed, call hide() first.");
        this.mView = view;
        this.mToken = token;
        SemDesktopModeManager desktopModeManager = (SemDesktopModeManager) this.mContext.get().getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        if (desktopModeManager == null) {
            isDeskTopMode = false;
        } else {
            SemDesktopModeState state = desktopModeManager.getDesktopModeState();
            if (state != null && state.enabled == 4) {
                z = true;
            }
            boolean isDeskTopMode2 = z;
            isDeskTopMode = isDeskTopMode2;
        }
        if (semGetFocusedDisplayId() != 1) {
            yOffset2 = yOffset;
        } else {
            yOffset2 = this.mResources.getDimensionPixelSize(R.dimen.sem_toast_y_offset_cover);
        }
        if (isDeskTopMode) {
            yOffset3 = yOffset2;
        } else {
            int yOffset4 = semGetAdjustedYoffset(gravity, yOffset2);
            Log.v(TAG, "yOffset = " + yOffset4);
            yOffset3 = yOffset4;
        }
        semPrintDebugMessage(this.mView);
        adjustLayoutParams(this.mParams, windowToken, duration, gravity, xOffset, yOffset3, horizontalMargin, verticalMargin, removeWindowAnimations);
        addToastView();
        trySendAccessibilityEvent(this.mView, this.mPackageName);
        if (callback != null) {
            try {
                callback.onToastShown();
            } catch (RemoteException e) {
                Log.w(TAG, "Error calling back " + this.mPackageName + " to notify onToastShow()", e);
            }
        }
    }

    public void hide(ITransientNotificationCallback callback) {
        Preconditions.checkState(this.mView != null, "No toast to hide.");
        WindowManager windowManager = getWindowManager(this.mView);
        if (this.mView.getParent() != null && windowManager != null) {
            windowManager.removeViewImmediate(this.mView);
        }
        try {
            this.mNotificationManager.finishToken(this.mPackageName, this.mToken);
        } catch (RemoteException e) {
            Log.w(TAG, "Error finishing toast window token from package " + this.mPackageName, e);
        }
        if (callback != null) {
            try {
                callback.onToastHidden();
            } catch (RemoteException e2) {
                Log.w(TAG, "Error calling back " + this.mPackageName + " to notify onToastHide()", e2);
            }
        }
        this.mView = null;
        this.mToken = null;
    }

    private WindowManager getWindowManager(View view) {
        Context context = this.mContext.get();
        if (context == null && view != null) {
            context = view.getContext();
        }
        if (context != null) {
            return (WindowManager) context.getSystemService(WindowManager.class);
        }
        return null;
    }

    public void trySendAccessibilityEvent(View view, String packageName) {
        Context context = this.mContext.get();
        if (context == null) {
            return;
        }
        AccessibilityManager accessibilityManager = new AccessibilityManager(context, this.mAccessibilityManagerService, context.getUserId());
        if (!accessibilityManager.isEnabled()) {
            accessibilityManager.removeClient();
            return;
        }
        AccessibilityEvent event = AccessibilityEvent.obtain(64);
        event.setClassName(Toast.class.getName());
        event.setPackageName(packageName);
        view.dispatchPopulateAccessibilityEvent(event);
        accessibilityManager.sendAccessibilityEvent(event);
        accessibilityManager.removeClient();
    }

    private void addToastView() {
        WindowManager windowManager = getWindowManager(this.mView);
        if (windowManager == null) {
            return;
        }
        if (this.mView.getParent() != null) {
            if (localLOGV) {
                Log.v(TAG, "REMOVE! " + this.mView + " in " + this);
            }
            windowManager.removeView(this.mView);
        }
        if (localLOGV) {
            Log.v(TAG, "ADD! " + this.mView + " in " + this);
        }
        try {
            windowManager.addView(this.mView, this.mParams);
        } catch (WindowManager.BadTokenException e) {
            Log.w(TAG, "Error while attempting to show toast from " + this.mPackageName, e);
        } catch (WindowManager.InvalidDisplayException e2) {
            Log.w(TAG, "Cannot show toast from " + this.mPackageName + " on display it was scheduled on.", e2);
        }
    }

    private int semGetNavigationBarHeight() {
        boolean hasNavigationBar = this.mView.getContext().getResources().getBoolean(R.bool.config_showNavigationBar);
        if (!hasNavigationBar) {
            return 0;
        }
        int navigationBarHeight = this.mView.getContext().getResources().getDimensionPixelSize(R.dimen.navigation_bar_height);
        return navigationBarHeight;
    }

    private int semGetAdjustedYoffset(int gravity, int yOffset) {
        int defaultGravity = this.mResources.getInteger(R.integer.config_toastDefaultGravity);
        int defaultOffsetY = this.mResources.getDimensionPixelSize(R.dimen.toast_y_offset);
        if (this.mResources.getConfiguration().orientation == 2) {
            return yOffset;
        }
        Context context = this.mContext.get();
        this.mContext.get();
        FingerprintManager fpm = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        boolean isFingerPrintInDisplay = false;
        int fingerIconHeight = 0;
        int mFingerOffsetY = this.mContext.get().getResources().getDimensionPixelSize(R.dimen.sem_toast_fingerPrint_y_offset);
        if (fpm != null) {
            fingerIconHeight = fpm.semGetIconBottomMargin();
            isFingerPrintInDisplay = FingerprintManager.semGetSensorPosition() == 2;
        }
        if (!isFingerPrintInDisplay || fingerIconHeight <= 0 || defaultOffsetY != yOffset || defaultGravity != gravity) {
            return yOffset;
        }
        int adjustedYOffset = (fingerIconHeight + mFingerOffsetY) - semGetNavigationBarHeight();
        return adjustedYOffset;
    }

    private void semPrintDebugMessage(View view) {
        View logView = view.findViewById(16908299);
        if (logView instanceof TextView) {
            CharSequence text = ((TextView) logView).getText();
            if (text.length() > 0) {
                char firstChar = (char) (text.charAt(0) + 1);
                if (text.length() > 3) {
                    Log.v(TAG, "Text: " + firstChar + ((Object) text.subSequence(1, 4)) + " in " + this);
                } else {
                    Log.v(TAG, "Text: " + firstChar + ((Object) text.subSequence(1, text.length())) + " in " + this);
                }
            }
        }
    }

    private int semGetSipHeight() {
        WindowManager windowManager = getWindowManager(this.mView);
        if (windowManager == null) {
            return 0;
        }
        WindowInsets windowInsets = windowManager.getCurrentWindowMetrics().getWindowInsets();
        Insets imeInset = windowInsets.getInsets(WindowInsets.Type.ime());
        Insets navibarInset = windowInsets.getInsets(WindowInsets.Type.navigationBars());
        return imeInset.bottom - navibarInset.bottom;
    }

    private static void semCheckMaxFontScale(Context context, TextView textview, int baseSize) {
        float currentFontScale = context.getResources().getConfiguration().fontScale;
        if (currentFontScale > MAX_FONT_SCALE) {
            float scaleBase = baseSize / currentFontScale;
            textview.setTextSize(0, MAX_FONT_SCALE * scaleBase);
        }
    }

    private int semGetFocusedDisplayId() {
        try {
            IWindowManager wm = WindowManagerGlobal.getWindowManagerService();
            int focusedDisplayId = wm.getTopFocusedDisplayId();
            return focusedDisplayId;
        } catch (RemoteException e) {
            Log.w(TAG, "Unable to get focusedDisplayId");
            return 0;
        }
    }
}
