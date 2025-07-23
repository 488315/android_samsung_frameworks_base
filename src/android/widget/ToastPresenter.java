package android.widget;

import android.app.INotificationManager;
import android.app.ITransientNotificationCallback;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
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

/* loaded from: classes5.dex */
public class ToastPresenter {
    public static final int DEVICE_DEFAULT_TEXT_TOAST_LAYOUT = 17367473;
    private static final long LONG_DURATION_TIMEOUT = 7000;
    private static final float MAX_FONT_SCALE = 1.3f;
    private static final long SHORT_DURATION_TIMEOUT = 4000;
    private static final String TAG = "ToastPresenter";
    public static final int TEXT_TOAST_LAYOUT = 17367518;
    public static final int TEXT_TOAST_LAYOUT_WITH_ICON = 17367519;
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

    public static View getTextToastView(Context context, CharSequence charSequence) {
        View inflate;
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true) && typedValue.data != 0) {
            inflate = LayoutInflater.from(context).inflate(17367473, (ViewGroup) null);
        } else {
            inflate = LayoutInflater.from(context).inflate(17367518, (ViewGroup) null);
        }
        TextView textView = (TextView) inflate.findViewById(16908299);
        textView.lambda$setTextAsync$0(charSequence);
        semCheckMaxFontScale(context, textView, context.getResources().getDimensionPixelSize(R.dimen.sem_toast_text_size));
        return inflate;
    }

    public static View getTextToastViewWithIcon(Context context, CharSequence charSequence, Drawable drawable) {
        if (drawable == null) {
            return getTextToastView(context, charSequence);
        }
        View inflate = LayoutInflater.from(context).inflate(17367519, (ViewGroup) null);
        ((TextView) inflate.findViewById(16908299)).lambda$setTextAsync$0(charSequence);
        ImageView imageView = (ImageView) inflate.findViewById(16908294);
        if (imageView != null) {
            imageView.lambda$setImageURIAsync$0(drawable);
        }
        return inflate;
    }

    public ToastPresenter(Context context, IAccessibilityManager iAccessibilityManager, INotificationManager iNotificationManager, String str) {
        this.mContext = new WeakReference<>(context);
        this.mResources = context.getResources();
        this.mNotificationManager = iNotificationManager;
        this.mPackageName = str;
        this.mContextPackageName = context.getPackageName();
        this.mAccessibilityManagerService = iAccessibilityManager;
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
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = -2;
        layoutParams.width = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = 16973828;
        layoutParams.type = 2005;
        layoutParams.setFitInsetsIgnoringVisibility(true);
        layoutParams.setTitle(WINDOW_TITLE);
        layoutParams.flags = 152;
        setShowForAllUsersIfApplicable(layoutParams, this.mPackageName);
        layoutParams.receiveInsetsIgnoringZOrder = true;
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() | WindowInsets.Type.ime());
        return layoutParams;
    }

    private void adjustLayoutParams(WindowManager.LayoutParams layoutParams, IBinder iBinder, int i, int i2, int i3, int i4, float f, float f2, boolean z) {
        int absoluteGravity = Gravity.getAbsoluteGravity(i2, this.mResources.getConfiguration().getLayoutDirection());
        layoutParams.gravity = absoluteGravity;
        if ((absoluteGravity & 7) == 7) {
            layoutParams.horizontalWeight = 1.0f;
        }
        if ((absoluteGravity & 112) == 112) {
            layoutParams.verticalWeight = 1.0f;
        }
        layoutParams.x = i3;
        layoutParams.y = i4;
        layoutParams.horizontalMargin = f;
        layoutParams.verticalMargin = f2;
        layoutParams.packageName = this.mContextPackageName;
        layoutParams.hideTimeoutMilliseconds = i == 1 ? LONG_DURATION_TIMEOUT : SHORT_DURATION_TIMEOUT;
        layoutParams.token = iBinder;
        if (z && layoutParams.windowAnimations == 16973828) {
            layoutParams.windowAnimations = 0;
        }
    }

    public void updateLayoutParams(int i, int i2, float f, float f2, int i3) {
        Preconditions.checkState(this.mView != null, "Toast must be showing to update its layout parameters.");
        this.mParams.gravity = Gravity.getAbsoluteGravity(i3, this.mResources.getConfiguration().getLayoutDirection());
        this.mParams.x = i;
        this.mParams.y = i2;
        this.mParams.horizontalMargin = f;
        this.mParams.verticalMargin = f2;
        this.mView.setLayoutParams(this.mParams);
    }

    private void setShowForAllUsersIfApplicable(WindowManager.LayoutParams layoutParams, String str) {
        if (isCrossUserPackage(str)) {
            layoutParams.privateFlags = 16;
        }
    }

    private boolean isCrossUserPackage(String str) {
        return ArrayUtils.contains(this.mResources.getStringArray(R.array.config_toastCrossUserPackages), str);
    }

    public void show(View view, IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, float f, float f2, ITransientNotificationCallback iTransientNotificationCallback) {
        show(view, iBinder, iBinder2, i, i2, i3, i4, f, f2, iTransientNotificationCallback, false);
    }

    public void show(View view, IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, float f, float f2, ITransientNotificationCallback iTransientNotificationCallback, boolean z) {
        SemDesktopModeState desktopModeState;
        boolean z2 = false;
        Preconditions.checkState(this.mView == null, "Only one toast at a time is allowed, call hide() first.");
        this.mView = view;
        this.mToken = iBinder;
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) this.mContext.get().getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        if (semDesktopModeManager != null && (desktopModeState = semDesktopModeManager.getDesktopModeState()) != null && desktopModeState.enabled == 4) {
            z2 = true;
        }
        int dimensionPixelSize = semGetFocusedDisplayId() == 1 ? this.mResources.getDimensionPixelSize(R.dimen.sem_toast_y_offset_cover) : i4;
        if (!z2) {
            dimensionPixelSize = semGetAdjustedYoffset(i2, dimensionPixelSize);
            Log.v(TAG, "yOffset = " + dimensionPixelSize);
        }
        semPrintDebugMessage(this.mView);
        adjustLayoutParams(this.mParams, iBinder2, i, i2, i3, dimensionPixelSize, f, f2, z);
        addToastView();
        trySendAccessibilityEvent(this.mView, this.mPackageName);
        if (iTransientNotificationCallback != null) {
            try {
                iTransientNotificationCallback.onToastShown();
            } catch (RemoteException e) {
                Log.w(TAG, "Error calling back " + this.mPackageName + " to notify onToastShow()", e);
            }
        }
    }

    public void hide(ITransientNotificationCallback iTransientNotificationCallback) {
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
        if (iTransientNotificationCallback != null) {
            try {
                iTransientNotificationCallback.onToastHidden();
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

    public void trySendAccessibilityEvent(View view, String str) {
        Context context = this.mContext.get();
        if (context == null) {
            return;
        }
        AccessibilityManager accessibilityManager = new AccessibilityManager(context, this.mAccessibilityManagerService, context.getUserId());
        if (!accessibilityManager.isEnabled()) {
            accessibilityManager.removeClient();
            return;
        }
        AccessibilityEvent obtain = AccessibilityEvent.obtain(64);
        obtain.setClassName(Toast.class.getName());
        obtain.setPackageName(str);
        view.dispatchPopulateAccessibilityEvent(obtain);
        accessibilityManager.sendAccessibilityEvent(obtain);
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
        if (this.mView.getContext().getResources().getBoolean(R.bool.config_showNavigationBar)) {
            return this.mView.getContext().getResources().getDimensionPixelSize(R.dimen.navigation_bar_height);
        }
        return 0;
    }

    private int semGetAdjustedYoffset(int i, int i2) {
        int i3;
        int integer = this.mResources.getInteger(R.integer.config_toastDefaultGravity);
        int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.toast_y_offset);
        if (this.mResources.getConfiguration().orientation != 2) {
            Context context = this.mContext.get();
            this.mContext.get();
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            int dimensionPixelSize2 = this.mContext.get().getResources().getDimensionPixelSize(R.dimen.sem_toast_fingerPrint_y_offset);
            boolean z = false;
            if (fingerprintManager != null) {
                i3 = fingerprintManager.semGetIconBottomMargin();
                if (FingerprintManager.semGetSensorPosition() == 2) {
                    z = true;
                }
            } else {
                i3 = 0;
            }
            if (z && i3 > 0 && dimensionPixelSize == i2 && integer == i) {
                return (i3 + dimensionPixelSize2) - semGetNavigationBarHeight();
            }
        }
        return i2;
    }

    private void semPrintDebugMessage(View view) {
        View findViewById = view.findViewById(16908299);
        if (findViewById instanceof TextView) {
            CharSequence text = ((TextView) findViewById).getText();
            if (text.length() > 0) {
                char charAt = (char) (text.charAt(0) + 1);
                if (text.length() > 3) {
                    Log.v(TAG, "Text: " + charAt + ((Object) text.subSequence(1, 4)) + " in " + this);
                    return;
                }
                Log.v(TAG, "Text: " + charAt + ((Object) text.subSequence(1, text.length())) + " in " + this);
            }
        }
    }

    private int semGetSipHeight() {
        WindowManager windowManager = getWindowManager(this.mView);
        if (windowManager == null) {
            return 0;
        }
        WindowInsets windowInsets = windowManager.getCurrentWindowMetrics().getWindowInsets();
        return windowInsets.getInsets(WindowInsets.Type.ime()).bottom - windowInsets.getInsets(WindowInsets.Type.navigationBars()).bottom;
    }

    private static void semCheckMaxFontScale(Context context, TextView textView, int i) {
        float f = context.getResources().getConfiguration().fontScale;
        if (f > MAX_FONT_SCALE) {
            textView.setTextSize(0, (i / f) * MAX_FONT_SCALE);
        }
    }

    private int semGetFocusedDisplayId() {
        try {
            return WindowManagerGlobal.getWindowManagerService().getTopFocusedDisplayId();
        } catch (RemoteException unused) {
            Log.w(TAG, "Unable to get focusedDisplayId");
            return 0;
        }
    }
}
