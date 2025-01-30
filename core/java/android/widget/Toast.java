package android.widget;

import android.app.Activity;
import android.app.ActivityThread;
import android.app.INotificationManager;
import android.app.ITransientNotification;
import android.app.ITransientNotificationCallback;
import android.compat.Compatibility;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.p009os.Binder;
import android.p009os.Debug;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.SystemProperties;
import android.provider.Settings;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.IWindowManager;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.IAccessibilityManager;
import android.widget.Toast;
import com.android.internal.C4337R;
import com.android.internal.util.Preconditions;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.custom.CustomDeviceManagerProxy;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class Toast {
    private static final long CHANGE_TEXT_TOASTS_IN_THE_SYSTEM = 147798919;
    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    private static final int MAX_LOOP_COUNT = 100;
    public static final int SEM_DISPLAY_TYPE_DEFAULT = 0;
    public static final int SEM_DISPLAY_TYPE_DEX = 1;
    public static final int SEM_LENGTH_LONG_DOUBLE = 1000;
    static final String TAG = "Toast";
    private static INotificationManager sService;
    private final List<Callback> mCallbacks;
    private final Context mContext;
    int mCustomDisplayId;
    Context mDisplayContext;
    int mDuration;
    private final Handler mHandler;
    private boolean mIsCustomToast;
    private View mNextView;
    View mNextViewForDex;
    final ITransientNotificationStubC4181TN mTN;
    private CharSequence mText;
    private final Binder mToken;
    static final boolean localLOGV = Debug.semIsProductDev();
    static final boolean DEBUG = Debug.semIsProductDev();

    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    public Toast(Context context) {
        this(context, null);
    }

    public Toast(Context context, Looper looper) {
        this.mDisplayContext = null;
        this.mNextViewForDex = null;
        this.mIsCustomToast = false;
        this.mCustomDisplayId = -1;
        this.mContext = context;
        Binder binder = new Binder();
        this.mToken = binder;
        Looper looper2 = getLooper(looper);
        this.mHandler = new Handler(looper2);
        ArrayList arrayList = new ArrayList();
        this.mCallbacks = arrayList;
        ITransientNotificationStubC4181TN iTransientNotificationStubC4181TN = new ITransientNotificationStubC4181TN(context, context.getPackageName(), binder, arrayList, looper2);
        this.mTN = iTransientNotificationStubC4181TN;
        iTransientNotificationStubC4181TN.f625mY = context.getResources().getDimensionPixelSize(C4337R.dimen.toast_y_offset);
        iTransientNotificationStubC4181TN.mGravity = context.getResources().getInteger(C4337R.integer.config_toastDefaultGravity);
    }

    private Looper getLooper(Looper looper) {
        if (looper != null) {
            return looper;
        }
        return (Looper) Preconditions.checkNotNull(Looper.myLooper(), "Can't toast on a thread that has not called Looper.prepare()");
    }

    private boolean isSpeg() {
        Context context;
        PackageManager pm;
        return CoreRune.SYSFW_APP_SPEG && (context = ActivityThread.currentApplication()) != null && (pm = context.getPackageManager()) != null && pm.isSpeg(Binder.getCallingUid());
    }

    /* JADX WARN: Removed duplicated region for block: B:104:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0214  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0233  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x02a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void show() {
        INotificationManager service;
        String pkg;
        View view;
        Context context;
        int displayId;
        int uid;
        Context context2;
        CharSequence charSequence;
        String oldText;
        int idx1;
        int idx2;
        Object[] objArr;
        if (isSpeg()) {
            return;
        }
        CustomDeviceManagerProxy knoxCustomManager = CustomDeviceManagerProxy.getInstance();
        if (knoxCustomManager != null && !knoxCustomManager.getToastEnabledState()) {
            Log.m98i(TAG, "Knox Customization: Not showing toast");
            return;
        }
        if (checkGameHomeAllowList()) {
            return;
        }
        if (Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
            Preconditions.checkState((this.mNextView == null && this.mText == null) ? false : true, "You must either set a text or a view");
        } else if (this.mNextView == null) {
            throw new RuntimeException("setView must have been called");
        }
        this.mTN.mIsCustomView = this.mIsCustomToast;
        Log.m98i(TAG, "show: caller = " + Debug.getCallers(1));
        this.mDisplayContext = null;
        int contextDispId = this.mContext.getDisplayId();
        int focusedDisplayId = semGetFocusedDisplayId();
        boolean isActivityContext = getActivityContext(this.mContext) != null;
        boolean isDexDualMode = isDexDualModeEnabled(this.mContext);
        boolean isFocusInDesktop = isDexDualMode && focusedDisplayId == 2;
        Log.m98i(TAG, "show: isDexDualMode = " + isDexDualMode);
        if (!this.mIsCustomToast && !isActivityContext && isFocusInDesktop && isDexDualMode && this.mContext.getApplicationContext() != null) {
            this.mDisplayContext = semCreateDisplayContext(2);
        }
        if (ViewRune.WIDGET_ONEUI_TOAST_SUPPRORT_SUB_DISPLAY && !this.mIsCustomToast && focusedDisplayId == 1 && contextDispId != 1 && this.mContext.getApplicationContext() != null) {
            this.mDisplayContext = semCreateDisplayContext(1);
        }
        Log.m98i(TAG, "show: contextDispId = " + contextDispId + " mCustomDisplayId = " + this.mCustomDisplayId + " focusedDisplayId = " + focusedDisplayId + " isActivityContext = " + isActivityContext);
        if (knoxCustomManager != null && knoxCustomManager.getToastShowPackageNameState()) {
            PackageManager pm = this.mContext.getPackageManager();
            ApplicationInfo info = this.mContext.getApplicationInfo();
            String appName = pm.getApplicationLabel(info).toString();
            View view2 = this.mNextView;
            if (view2 != null) {
                TextView tv = (TextView) view2.findViewById(16908299);
                if (tv != null && appName != null) {
                    if (!tv.getText().toString().startsWith(appName)) {
                        try {
                            Spanned spannedText = new SpannableString(tv.getText());
                            oldText = Html.toHtml(spannedText);
                            idx1 = oldText.indexOf(62);
                            idx2 = oldText.lastIndexOf(60);
                            objArr = new Object[2];
                            objArr[0] = appName;
                        } catch (Exception e) {
                            e = e;
                        }
                        try {
                            objArr[1] = oldText.substring(idx1 + 1, idx2);
                            tv.setText(Html.fromHtml(String.format("%1s: %2s", objArr)));
                        } catch (Exception e2) {
                            e = e2;
                            Log.m97e(TAG, "Exception thrown :", e);
                            tv.setText(String.format("%1s: %2s", appName, tv.getText().toString()));
                            if (!this.mIsCustomToast) {
                                this.mNextViewForDex = ToastPresenter.getTextToastView(context2, this.mText);
                                if (localLOGV) {
                                }
                            }
                            if (!this.mTN.mIsCustomOffset) {
                            }
                            service = getService();
                            pkg = this.mContext.getOpPackageName();
                            ITransientNotificationStubC4181TN tn = this.mTN;
                            view = this.mNextViewForDex;
                            if (view == null) {
                            }
                            tn.mNextView = new WeakReference<>(view);
                            context = this.mDisplayContext;
                            if (context == null) {
                            }
                            int displayId2 = context.getDisplayId();
                            if (this.mCustomDisplayId != -1) {
                            }
                            uid = -1;
                            PackageManager pm2 = this.mContext.getPackageManager();
                            ApplicationInfo ai = pm2.getApplicationInfo(pkg, 0);
                            uid = ai.uid;
                            boolean isUiContext = this.mContext.isUiContext();
                            if (service == null) {
                            }
                        }
                    }
                }
            } else if (appName != null && (charSequence = this.mText) != null && !charSequence.toString().startsWith(appName)) {
                try {
                    Spanned spannedText2 = new SpannableString(this.mText);
                    String oldText2 = Html.toHtml(spannedText2);
                    int idx12 = oldText2.indexOf(62);
                    int idx22 = oldText2.lastIndexOf(60);
                    this.mText = Html.fromHtml(String.format("%1s: %2s", appName, oldText2.substring(idx12 + 1, idx22)));
                } catch (Exception e3) {
                    Log.m97e(TAG, "Exception thrown :", e3);
                    this.mText = String.format("%1s: %2s", appName, this.mText.toString());
                }
            }
        }
        if (!this.mIsCustomToast && (context2 = this.mDisplayContext) != null) {
            this.mNextViewForDex = ToastPresenter.getTextToastView(context2, this.mText);
            if (localLOGV) {
                Log.m100v(TAG, "show: new view = " + this.mNextViewForDex);
            }
        }
        if (!this.mTN.mIsCustomOffset) {
            Context context3 = this.mDisplayContext;
            if (context3 == null) {
                this.mTN.f625mY = this.mContext.getResources().getDimensionPixelSize(C4337R.dimen.toast_y_offset);
            } else {
                this.mTN.f625mY = context3.getResources().getDimensionPixelSize(C4337R.dimen.toast_y_offset);
            }
        }
        service = getService();
        pkg = this.mContext.getOpPackageName();
        ITransientNotificationStubC4181TN tn2 = this.mTN;
        view = this.mNextViewForDex;
        if (view == null) {
            view = this.mNextView;
        }
        tn2.mNextView = new WeakReference<>(view);
        context = this.mDisplayContext;
        if (context == null) {
            context = this.mContext;
        }
        int displayId22 = context.getDisplayId();
        if (this.mCustomDisplayId != -1) {
            displayId = displayId22;
        } else {
            int displayId3 = this.mCustomDisplayId;
            displayId = displayId3;
        }
        uid = -1;
        try {
            PackageManager pm22 = this.mContext.getPackageManager();
            ApplicationInfo ai2 = pm22.getApplicationInfo(pkg, 0);
            uid = ai2.uid;
        } catch (Exception e4) {
            Log.m97e(TAG, "show: cannot get uid!!!", e4);
        }
        boolean isUiContext2 = this.mContext.isUiContext();
        if (service == null) {
            try {
                if (Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
                    View view3 = this.mNextView;
                    if (view3 != null) {
                        service.enqueueToastForDex(pkg, this.mToken, tn2, this.mDuration, isUiContext2, displayId, semGetMessageFromTv(view3), uid);
                        return;
                    }
                    ITransientNotificationCallback callback = new CallbackBinder(this.mCallbacks, this.mHandler);
                    IBinder iBinder = this.mToken;
                    CharSequence charSequence2 = this.mText;
                    service.enqueueTextToastForDex(pkg, iBinder, charSequence2, this.mDuration, isUiContext2, displayId, callback, charSequence2 != null ? charSequence2.toString() : "", uid);
                    return;
                }
                service.enqueueToastForDex(pkg, this.mToken, tn2, this.mDuration, isUiContext2, displayId, semGetMessageFromTv(this.mNextView), uid);
            } catch (RemoteException e5) {
            }
        }
    }

    public void cancel() {
        if (Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM) && this.mNextView == null) {
            try {
                getService().cancelToast(this.mContext.getOpPackageName(), this.mToken);
            } catch (RemoteException e) {
            }
        } else {
            this.mTN.cancel();
        }
    }

    @Deprecated
    public void setView(View view) {
        this.mNextView = view;
        this.mIsCustomToast = true;
        Log.m98i(TAG, "setView: it's a custom toast");
    }

    @Deprecated
    public View getView() {
        return this.mNextView;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
        this.mTN.mDuration = duration;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setMargin(float horizontalMargin, float verticalMargin) {
        if (isSystemRenderedTextToast()) {
            Log.m96e(TAG, "setMargin() shouldn't be called on text toasts, the values won't be used");
        }
        this.mTN.mHorizontalMargin = horizontalMargin;
        this.mTN.mVerticalMargin = verticalMargin;
    }

    public float getHorizontalMargin() {
        if (isSystemRenderedTextToast()) {
            Log.m96e(TAG, "getHorizontalMargin() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mHorizontalMargin;
    }

    public float getVerticalMargin() {
        if (isSystemRenderedTextToast()) {
            Log.m96e(TAG, "getVerticalMargin() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mVerticalMargin;
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (isSystemRenderedTextToast()) {
            Log.m96e(TAG, "setGravity() shouldn't be called on text toasts, the values won't be used");
        }
        this.mTN.mGravity = gravity;
        this.mTN.f624mX = xOffset;
        this.mTN.f625mY = yOffset;
        this.mTN.mIsCustomOffset = true;
    }

    public int getGravity() {
        if (isSystemRenderedTextToast()) {
            Log.m96e(TAG, "getGravity() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mGravity;
    }

    public int getXOffset() {
        if (isSystemRenderedTextToast()) {
            Log.m96e(TAG, "getXOffset() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.f624mX;
    }

    public int getYOffset() {
        if (isSystemRenderedTextToast()) {
            Log.m96e(TAG, "getYOffset() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.f625mY;
    }

    private boolean isSystemRenderedTextToast() {
        return Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM) && this.mNextView == null;
    }

    public void addCallback(Callback callback) {
        Preconditions.checkNotNull(callback);
        synchronized (this.mCallbacks) {
            this.mCallbacks.add(callback);
        }
    }

    public void removeCallback(Callback callback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.remove(callback);
        }
    }

    public WindowManager.LayoutParams getWindowParams() {
        if (Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
            if (this.mNextView != null) {
                return this.mTN.mParams;
            }
            return null;
        }
        return this.mTN.mParams;
    }

    public static Toast makeText(Context context, CharSequence text, int duration) {
        return makeText(context, null, text, duration);
    }

    public static Toast makeText(Context context, Looper looper, CharSequence text, int duration) {
        Toast result = new Toast(context, looper);
        if (Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
            result.mText = text;
        } else {
            result.mNextView = ToastPresenter.getTextToastView(context, text);
        }
        result.mDuration = duration;
        return result;
    }

    public static Toast makeCustomToastWithIcon(Context context, Looper looper, CharSequence text, int duration, Drawable icon) {
        if (icon == null) {
            throw new IllegalArgumentException("Drawable icon should not be null for makeCustomToastWithIcon");
        }
        Toast result = new Toast(context, looper);
        result.mNextView = ToastPresenter.getTextToastViewWithIcon(context, text, icon);
        result.mDuration = duration;
        return result;
    }

    public static Toast makeText(Context context, int resId, int duration) throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }

    public void setText(int resId) {
        setText(this.mContext.getText(resId));
    }

    public void setText(CharSequence s) {
        if (Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
            if (this.mNextView != null) {
                throw new IllegalStateException("Text provided for custom toast, remove previous setView() calls if you want a text toast instead.");
            }
            this.mText = s;
            return;
        }
        View view = this.mNextView;
        if (view == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        TextView tv = (TextView) view.findViewById(16908299);
        if (tv == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        tv.setText(s);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static INotificationManager getService() {
        INotificationManager iNotificationManager = sService;
        if (iNotificationManager != null) {
            return iNotificationManager;
        }
        INotificationManager asInterface = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        sService = asInterface;
        return asInterface;
    }

    /* renamed from: android.widget.Toast$TN */
    private static class ITransientNotificationStubC4181TN extends ITransientNotification.Stub {
        private static final int CANCEL = 2;
        private static final int HIDE = 1;
        private static final int SHOW = 0;
        private final WeakReference<List<Callback>> mCallbacks;
        int mDuration;
        int mGravity;
        final Handler mHandler;
        float mHorizontalMargin;
        boolean mIsCustomOffset;
        boolean mIsCustomView = false;
        WeakReference<View> mNextView;
        final String mPackageName;
        private final WindowManager.LayoutParams mParams;
        private final ToastPresenter mPresenter;
        final Binder mToken;
        float mVerticalMargin;
        View mView;
        WindowManager mWM;

        /* renamed from: mX */
        int f624mX;

        /* renamed from: mY */
        int f625mY;

        ITransientNotificationStubC4181TN(Context context, String packageName, Binder token, List<Callback> callbacks, Looper looper) {
            IAccessibilityManager accessibilityManager = IAccessibilityManager.Stub.asInterface(ServiceManager.getService(Context.ACCESSIBILITY_SERVICE));
            ToastPresenter toastPresenter = new ToastPresenter(context, accessibilityManager, Toast.getService(), packageName);
            this.mPresenter = toastPresenter;
            this.mParams = toastPresenter.getLayoutParams();
            this.mPackageName = packageName;
            this.mToken = token;
            this.mCallbacks = new WeakReference<>(callbacks);
            this.mHandler = new Handler(looper, null) { // from class: android.widget.Toast.TN.1
                @Override // android.p009os.Handler
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            IBinder token2 = (IBinder) msg.obj;
                            ITransientNotificationStubC4181TN.this.handleShow(token2);
                            break;
                        case 1:
                            ITransientNotificationStubC4181TN.this.handleHide();
                            ITransientNotificationStubC4181TN.this.mNextView = null;
                            break;
                        case 2:
                            ITransientNotificationStubC4181TN.this.handleHide();
                            ITransientNotificationStubC4181TN.this.mNextView = null;
                            try {
                                Toast.getService().cancelToast(ITransientNotificationStubC4181TN.this.mPackageName, ITransientNotificationStubC4181TN.this.mToken);
                                break;
                            } catch (RemoteException e) {
                                return;
                            }
                    }
                }
            };
        }

        private List<Callback> getCallbacks() {
            synchronized (this.mCallbacks) {
                if (this.mCallbacks.get() != null) {
                    return new ArrayList(this.mCallbacks.get());
                }
                return new ArrayList();
            }
        }

        @Override // android.app.ITransientNotification
        public void show(IBinder windowToken) {
            if (Toast.localLOGV) {
                Log.m100v(Toast.TAG, "SHOW: " + this);
            }
            this.mHandler.obtainMessage(0, windowToken).sendToTarget();
        }

        @Override // android.app.ITransientNotification
        public void hide() {
            if (Toast.localLOGV) {
                Log.m100v(Toast.TAG, "HIDE: " + this);
            }
            this.mHandler.obtainMessage(1).sendToTarget();
        }

        public void cancel() {
            if (Toast.localLOGV) {
                Log.m100v(Toast.TAG, "CANCEL: " + this);
            }
            this.mHandler.obtainMessage(2).sendToTarget();
        }

        public void handleShow(IBinder windowToken) {
            WeakReference<View> weakReference;
            Log.m100v(Toast.TAG, "HANDLE SHOW: " + this + " mView=" + this.mView + " mNextView=" + this.mNextView);
            if (!this.mHandler.hasMessages(2) && !this.mHandler.hasMessages(1) && (weakReference = this.mNextView) != null && this.mView != weakReference.get()) {
                handleHide();
                if (this.mIsCustomView) {
                    this.mParams.semClearExtensionFlags(131072);
                } else {
                    this.mParams.semAddExtensionFlags(131072);
                }
                View view = this.mNextView.get();
                this.mView = view;
                if (view != null) {
                    this.mPresenter.show(view, this.mToken, windowToken, this.mDuration, this.mGravity, this.f624mX, this.f625mY, this.mHorizontalMargin, this.mVerticalMargin, new CallbackBinder(getCallbacks(), this.mHandler));
                }
            }
        }

        public void handleHide() {
            if (Toast.localLOGV) {
                Log.m100v(Toast.TAG, "HANDLE HIDE: " + this + " mView=" + this.mView);
            }
            View view = this.mView;
            if (view != null) {
                Preconditions.checkState(view == this.mPresenter.getView(), "Trying to hide toast view different than the last one displayed");
                this.mPresenter.hide(new CallbackBinder(getCallbacks(), this.mHandler));
                this.mView = null;
            }
        }
    }

    public static abstract class Callback {
        public void onToastShown() {
        }

        public void onToastHidden() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackBinder extends ITransientNotificationCallback.Stub {
        private final List<Callback> mCallbacks;
        private final Handler mHandler;

        private CallbackBinder(List<Callback> callbacks, Handler handler) {
            this.mCallbacks = callbacks;
            this.mHandler = handler;
        }

        @Override // android.app.ITransientNotificationCallback
        public void onToastShown() {
            this.mHandler.post(new Runnable() { // from class: android.widget.Toast$CallbackBinder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Toast.CallbackBinder.this.lambda$onToastShown$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onToastShown$0() {
            for (Callback callback : getCallbacks()) {
                callback.onToastShown();
            }
        }

        @Override // android.app.ITransientNotificationCallback
        public void onToastHidden() {
            this.mHandler.post(new Runnable() { // from class: android.widget.Toast$CallbackBinder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Toast.CallbackBinder.this.lambda$onToastHidden$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onToastHidden$1() {
            for (Callback callback : getCallbacks()) {
                callback.onToastHidden();
            }
        }

        private List<Callback> getCallbacks() {
            ArrayList arrayList;
            synchronized (this.mCallbacks) {
                arrayList = new ArrayList(this.mCallbacks);
            }
            return arrayList;
        }
    }

    private boolean checkGameHomeAllowList() {
        if (!"1".equals(SystemProperties.get("sys.boot_completed"))) {
            Log.m98i(TAG, "Boot is not completed yet. Don't read settings db.");
            return false;
        }
        int gameNoInterruption = Settings.System.getInt(this.mContext.getContentResolver(), "game_no_interruption", 0);
        if (gameNoInterruption <= 0) {
            return false;
        }
        String allowList = Settings.System.getString(this.mContext.getContentResolver(), "game_no_interruption_white_list");
        if (allowList != null) {
            String packageName = this.mContext.getPackageName();
            if (allowList.contains(packageName)) {
                Log.m98i(TAG, "GameNoInterruption mode. Show game toast. " + allowList);
                return false;
            }
            Log.m98i(TAG, "GameNoInterruption mode. Block toast " + allowList);
            return true;
        }
        Log.m98i(TAG, "gameNoInterruption is on, but allowList is null.");
        return false;
    }

    public static Toast semMakeAction(Context context, CharSequence text, int duration, CharSequence action, View.OnClickListener listener) {
        return makeText(context, null, text, duration);
    }

    private int semGetFocusedDisplayId() {
        if (this.mCustomDisplayId != -1) {
            int focusedDisplayId = this.mCustomDisplayId;
            return focusedDisplayId;
        }
        try {
            IWindowManager wm = WindowManagerGlobal.getWindowManagerService();
            int focusedDisplayId2 = wm.getTopFocusedDisplayId();
            return focusedDisplayId2;
        } catch (RemoteException e) {
            Log.m102w(TAG, "Unable to get focusedDisplayId");
            return 0;
        }
    }

    public void semSetPreferredDisplayType(int displayId) {
        if (displayId == 1) {
            this.mCustomDisplayId = 2;
        } else {
            this.mCustomDisplayId = 0;
        }
    }

    private boolean isDexDualModeEnabled(Context context) {
        boolean isDesktopDualMode = false;
        SemDesktopModeManager desktopModeManager = (SemDesktopModeManager) context.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        if (desktopModeManager != null) {
            SemDesktopModeState state = desktopModeManager.getDesktopModeState();
            isDesktopDualMode = state != null && state.enabled == 4 && state.getDisplayType() == 102;
        }
        if (localLOGV) {
            Log.m100v(TAG, "isDexDualModeEnabled: isDesktopDualMode = " + isDesktopDualMode);
        }
        return isDesktopDualMode;
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

    private Context semCreateDisplayContext(int displayType) {
        String dispCategory;
        Context displayContextTemp;
        DisplayManager dm = (DisplayManager) this.mContext.getSystemService(Context.DISPLAY_SERVICE);
        if (dm == null) {
            return null;
        }
        if (displayType == 2) {
            dispCategory = DisplayManager.DISPLAY_CATEGORY_DESKTOP;
        } else if (displayType == 1) {
            dispCategory = "com.samsung.android.hardware.display.category.BUILTIN";
        } else {
            return null;
        }
        Display[] displays = dm.getDisplays(dispCategory);
        for (Display d : displays) {
            if ((d.getDisplayId() == 1 || d.getDisplayId() == 2) && (displayContextTemp = this.mContext.createDisplayContext(d)) != null) {
                Context dispContext = new ContextThemeWrapper(displayContextTemp, 16974123);
                return dispContext;
            }
        }
        return null;
    }

    private String semGetMessageFromTv(View view) {
        CharSequence cs;
        if (view != null) {
            View tv = view.findViewById(16908299);
            if ((tv instanceof TextView) && (cs = ((TextView) tv).getText()) != null) {
                return cs.toString();
            }
            return "";
        }
        return "";
    }
}
