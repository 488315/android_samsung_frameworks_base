package android.widget;

import android.app.Activity;
import android.app.ActivityThread;
import android.app.Application;
import android.app.INotificationManager;
import android.app.ITransientNotification;
import android.app.ITransientNotificationCallback;
import android.compat.Compatibility;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.IAccessibilityManager;
import android.widget.flags.Flags;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import com.samsung.android.knox.custom.CustomDeviceManagerProxy;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
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
    final TN mTN;
    private CharSequence mText;
    private final Binder mToken;
    static final boolean localLOGV = Debug.semIsProductDev();
    static final boolean DEBUG = Debug.semIsProductDev();

    public static abstract class Callback {
        public void onToastHidden() {
        }

        public void onToastShown() {
        }
    }

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
        TN tn = new TN(context, context.getPackageName(), binder, arrayList, looper2);
        this.mTN = tn;
        tn.mY = context.getResources().getDimensionPixelSize(R.dimen.toast_y_offset);
        tn.mGravity = context.getResources().getInteger(R.integer.config_toastDefaultGravity);
    }

    private Looper getLooper(Looper looper) {
        return looper != null ? looper : (Looper) Preconditions.checkNotNull(Looper.myLooper(), "Can't toast on a thread that has not called Looper.prepare()");
    }

    private boolean isSpeg() {
        Application applicationCurrentApplication;
        PackageManager packageManager;
        return CoreRune.SYSFW_APP_SPEG && (applicationCurrentApplication = ActivityThread.currentApplication()) != null && (packageManager = applicationCurrentApplication.getPackageManager()) != null && packageManager.isSpeg(Binder.getCallingUid());
    }

    public void show() {
        Context context;
        CharSequence charSequence;
        if (isSpeg()) {
            return;
        }
        CustomDeviceManagerProxy customDeviceManagerProxy = CustomDeviceManagerProxy.getInstance();
        if (customDeviceManagerProxy != null && !customDeviceManagerProxy.getToastEnabledState()) {
            Log.i(TAG, "Knox Customization: Not showing toast");
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
        Log.i(TAG, "show: caller = " + Debug.getCallers(1));
        this.mDisplayContext = null;
        int displayId = this.mContext.getDisplayId();
        int iSemGetFocusedDisplayId = semGetFocusedDisplayId();
        boolean z = getActivityContext(this.mContext) != null;
        boolean z2 = iSemGetFocusedDisplayId == getExternalDesktopDisplayId();
        if (!this.mIsCustomToast && !z && z2 && this.mContext.getApplicationContext() != null) {
            this.mDisplayContext = semCreateDisplayContext(getExternalDesktopDisplayId());
        }
        if (ViewRune.WIDGET_ONEUI_TOAST_SUPPRORT_SUB_DISPLAY && !this.mIsCustomToast && iSemGetFocusedDisplayId == 1 && displayId != 1 && this.mContext.getApplicationContext() != null) {
            this.mDisplayContext = semCreateDisplayContext(1);
        }
        Log.i(TAG, "show: contextDispId = " + displayId + " mCustomDisplayId = " + this.mCustomDisplayId + " focusedDisplayId = " + iSemGetFocusedDisplayId + " isActivityContext = " + z);
        if (customDeviceManagerProxy != null && customDeviceManagerProxy.getToastShowPackageNameState()) {
            String string = this.mContext.getPackageManager().getApplicationLabel(this.mContext.getApplicationInfo()).toString();
            View view = this.mNextView;
            if (view != null) {
                TextView textView = (TextView) view.findViewById(16908299);
                if (textView != null && string != null && !textView.getText().toString().startsWith(string)) {
                    try {
                        String html = Html.toHtml(new SpannableString(textView.getText()));
                        textView.lambda$setTextAsync$0(Html.fromHtml(String.format("%1s: %2s", string, html.substring(html.indexOf(62) + 1, html.lastIndexOf(60)))));
                    } catch (Exception e) {
                        Log.e(TAG, "Exception thrown :", e);
                        textView.lambda$setTextAsync$0(String.format("%1s: %2s", string, textView.getText().toString()));
                    }
                }
            } else if (string != null && (charSequence = this.mText) != null && !charSequence.toString().startsWith(string)) {
                try {
                    String html2 = Html.toHtml(new SpannableString(this.mText));
                    this.mText = Html.fromHtml(String.format("%1s: %2s", string, html2.substring(html2.indexOf(62) + 1, html2.lastIndexOf(60))));
                } catch (Exception e2) {
                    Log.e(TAG, "Exception thrown :", e2);
                    this.mText = String.format("%1s: %2s", string, this.mText.toString());
                }
            }
        }
        if (!this.mIsCustomToast && (context = this.mDisplayContext) != null) {
            this.mNextViewForDex = ToastPresenter.getTextToastView(context, this.mText);
            if (localLOGV) {
                Log.v(TAG, "show: new view = " + this.mNextViewForDex);
            }
        }
        if (!this.mTN.mIsCustomOffset) {
            Context context2 = this.mDisplayContext;
            if (context2 != null) {
                this.mTN.mY = context2.getResources().getDimensionPixelSize(R.dimen.toast_y_offset);
            } else {
                this.mTN.mY = this.mContext.getResources().getDimensionPixelSize(R.dimen.toast_y_offset);
            }
        }
        INotificationManager service = getService();
        String opPackageName = this.mContext.getOpPackageName();
        TN tn = this.mTN;
        if (Flags.toastNoWeakref()) {
            View view2 = this.mNextViewForDex;
            if (view2 == null) {
                view2 = this.mNextView;
            }
            tn.mNextView = view2;
        } else {
            View view3 = this.mNextViewForDex;
            if (view3 == null) {
                view3 = this.mNextView;
            }
            tn.mNextViewWeakRef = new WeakReference<>(view3);
        }
        Context context3 = this.mDisplayContext;
        if (context3 == null) {
            context3 = this.mContext;
        }
        int displayId2 = context3.getDisplayId();
        int i = this.mCustomDisplayId;
        int i2 = -1;
        int i3 = i != -1 ? i : displayId2;
        try {
            i2 = this.mContext.getPackageManager().getApplicationInfo(opPackageName, 0).uid;
        } catch (Exception e3) {
            Log.e(TAG, "show: cannot get uid!!!", e3);
        }
        int i4 = i2;
        boolean zIsUiContext = this.mContext.isUiContext();
        if (service != null) {
            try {
                if (Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
                    View view4 = this.mNextView;
                    if (view4 != null) {
                        service.enqueueToastForDex(opPackageName, this.mToken, tn, this.mDuration, zIsUiContext, i3, semGetMessageFromTv(view4), i4);
                        return;
                    }
                    CallbackBinder callbackBinder = new CallbackBinder(this.mCallbacks, this.mHandler);
                    Binder binder = this.mToken;
                    CharSequence charSequence2 = this.mText;
                    service.enqueueTextToastForDex(opPackageName, binder, charSequence2, this.mDuration, zIsUiContext, i3, callbackBinder, charSequence2 != null ? charSequence2.toString() : "", i4);
                    return;
                }
                service.enqueueToastForDex(opPackageName, this.mToken, tn, this.mDuration, zIsUiContext, i3, semGetMessageFromTv(this.mNextView), i4);
            } catch (RemoteException unused) {
            }
        }
    }

    public void cancel() {
        if (Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM) && this.mNextView == null) {
            try {
                getService().cancelToast(this.mContext.getOpPackageName(), this.mToken);
            } catch (RemoteException unused) {
            }
        } else {
            this.mTN.cancel();
        }
    }

    @Deprecated
    public void setView(View view) {
        this.mNextView = view;
        this.mIsCustomToast = true;
        Log.i(TAG, "setView: it's a custom toast");
    }

    @Deprecated
    public View getView() {
        return this.mNextView;
    }

    public void setDuration(int i) {
        this.mDuration = i;
        this.mTN.mDuration = i;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setMargin(float f, float f2) {
        if (isSystemRenderedTextToast()) {
            Log.e(TAG, "setMargin() shouldn't be called on text toasts, the values won't be used");
        }
        this.mTN.mHorizontalMargin = f;
        this.mTN.mVerticalMargin = f2;
    }

    public float getHorizontalMargin() {
        if (isSystemRenderedTextToast()) {
            Log.e(TAG, "getHorizontalMargin() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mHorizontalMargin;
    }

    public float getVerticalMargin() {
        if (isSystemRenderedTextToast()) {
            Log.e(TAG, "getVerticalMargin() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mVerticalMargin;
    }

    public void setGravity(int i, int i2, int i3) {
        if (isSystemRenderedTextToast()) {
            Log.e(TAG, "setGravity() shouldn't be called on text toasts, the values won't be used");
        }
        this.mTN.mGravity = i;
        this.mTN.mX = i2;
        this.mTN.mY = i3;
        this.mTN.mIsCustomOffset = true;
    }

    public int getGravity() {
        if (isSystemRenderedTextToast()) {
            Log.e(TAG, "getGravity() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mGravity;
    }

    public int getXOffset() {
        if (isSystemRenderedTextToast()) {
            Log.e(TAG, "getXOffset() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mX;
    }

    public int getYOffset() {
        if (isSystemRenderedTextToast()) {
            Log.e(TAG, "getYOffset() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mY;
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

    public static Toast makeText(Context context, CharSequence charSequence, int i) {
        return makeText(context, null, charSequence, i);
    }

    public static Toast makeText(Context context, Looper looper, CharSequence charSequence, int i) {
        Toast toast = new Toast(context, looper);
        if (Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
            toast.mText = charSequence;
        } else {
            toast.mNextView = ToastPresenter.getTextToastView(context, charSequence);
        }
        toast.mDuration = i;
        return toast;
    }

    public static Toast makeCustomToastWithIcon(Context context, Looper looper, CharSequence charSequence, int i, Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable icon should not be null for makeCustomToastWithIcon");
        }
        Toast toast = new Toast(context, looper);
        toast.mNextView = ToastPresenter.getTextToastViewWithIcon(context, charSequence, drawable);
        toast.mDuration = i;
        return toast;
    }

    public static Toast makeText(Context context, int i, int i2) throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(i), i2);
    }

    public void setText(int i) {
        setText(this.mContext.getText(i));
    }

    public void setText(CharSequence charSequence) {
        if (Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
            if (this.mNextView != null) {
                throw new IllegalStateException("Text provided for custom toast, remove previous setView() calls if you want a text toast instead.");
            }
            this.mText = charSequence;
            return;
        }
        View view = this.mNextView;
        if (view == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        TextView textView = (TextView) view.findViewById(16908299);
        if (textView == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        textView.lambda$setTextAsync$0(charSequence);
    }

    public TN getTn() {
        return this.mTN;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static INotificationManager getService() {
        INotificationManager iNotificationManager = sService;
        if (iNotificationManager != null) {
            return iNotificationManager;
        }
        INotificationManager iNotificationManagerAsInterface = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        sService = iNotificationManagerAsInterface;
        return iNotificationManagerAsInterface;
    }

    public static class TN extends ITransientNotification.Stub {
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
        View mNextView;
        WeakReference<View> mNextViewWeakRef;
        final String mPackageName;
        private final WindowManager.LayoutParams mParams;
        private final ToastPresenter mPresenter;
        final Binder mToken;
        float mVerticalMargin;
        View mView;
        WindowManager mWM;
        int mX;
        int mY;

        TN(Context context, String str, Binder binder, List<Callback> list, Looper looper) {
            ToastPresenter toastPresenter = new ToastPresenter(context, IAccessibilityManager.Stub.asInterface(ServiceManager.getService(Context.ACCESSIBILITY_SERVICE)), Toast.getService(), str);
            this.mPresenter = toastPresenter;
            this.mParams = toastPresenter.getLayoutParams();
            this.mPackageName = str;
            this.mToken = binder;
            this.mCallbacks = new WeakReference<>(list);
            this.mHandler = new Handler(looper, null) { // from class: android.widget.Toast.TN.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 0) {
                        TN.this.handleShow((IBinder) message.obj);
                        return;
                    }
                    if (i == 1) {
                        TN.this.handleHide();
                        if (Flags.toastNoWeakref()) {
                            TN.this.mNextView = null;
                            return;
                        } else {
                            TN.this.mNextViewWeakRef = null;
                            return;
                        }
                    }
                    if (i != 2) {
                        return;
                    }
                    TN.this.handleHide();
                    if (Flags.toastNoWeakref()) {
                        TN.this.mNextView = null;
                    } else {
                        TN.this.mNextViewWeakRef = null;
                    }
                    try {
                        Toast.getService().cancelToast(TN.this.mPackageName, TN.this.mToken);
                    } catch (RemoteException unused) {
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
        public void show(IBinder iBinder) {
            if (Toast.localLOGV) {
                Log.v(Toast.TAG, "SHOW: " + this);
            }
            this.mHandler.obtainMessage(0, iBinder).sendToTarget();
        }

        @Override // android.app.ITransientNotification
        public void hide() {
            if (Toast.localLOGV) {
                Log.v(Toast.TAG, "HIDE: " + this);
            }
            this.mHandler.obtainMessage(1).sendToTarget();
        }

        public void cancel() {
            if (Toast.localLOGV) {
                Log.v(Toast.TAG, "CANCEL: " + this);
            }
            this.mHandler.obtainMessage(2).sendToTarget();
        }

        public void handleShow(IBinder iBinder) {
            if (Flags.toastNoWeakref()) {
                if (Toast.localLOGV) {
                    Log.v(Toast.TAG, "HANDLE SHOW: " + this + " mView=" + this.mView + " mNextView=" + this.mNextView);
                }
            } else if (Toast.localLOGV) {
                Log.v(Toast.TAG, "HANDLE SHOW: " + this + " mView=" + this.mView + " mNextView=" + this.mNextViewWeakRef);
            }
            if (this.mHandler.hasMessages(2) || this.mHandler.hasMessages(1)) {
                return;
            }
            if (Flags.toastNoWeakref()) {
                View view = this.mNextView;
                if (view == null || this.mView == view) {
                    return;
                }
                handleHide();
                if (this.mIsCustomView) {
                    this.mParams.semClearExtensionFlags(131072);
                } else {
                    this.mParams.semAddExtensionFlags(131072);
                }
                View view2 = this.mNextView;
                this.mView = view2;
                if (view2 != null) {
                    this.mPresenter.show(view2, this.mToken, iBinder, this.mDuration, this.mGravity, this.mX, this.mY, this.mHorizontalMargin, this.mVerticalMargin, new CallbackBinder(getCallbacks(), this.mHandler));
                    return;
                }
                return;
            }
            WeakReference<View> weakReference = this.mNextViewWeakRef;
            if (weakReference == null || this.mView == weakReference.get()) {
                return;
            }
            handleHide();
            if (this.mIsCustomView) {
                this.mParams.semClearExtensionFlags(131072);
            } else {
                this.mParams.semAddExtensionFlags(131072);
            }
            View view3 = this.mNextViewWeakRef.get();
            this.mView = view3;
            if (view3 != null) {
                this.mPresenter.show(view3, this.mToken, iBinder, this.mDuration, this.mGravity, this.mX, this.mY, this.mHorizontalMargin, this.mVerticalMargin, new CallbackBinder(getCallbacks(), this.mHandler));
            }
        }

        public void handleHide() {
            if (Toast.localLOGV) {
                Log.v(Toast.TAG, "HANDLE HIDE: " + this + " mView=" + this.mView);
            }
            View view = this.mView;
            if (view != null) {
                Preconditions.checkState(view == this.mPresenter.getView(), "Trying to hide toast view different than the last one displayed");
                this.mPresenter.hide(new CallbackBinder(getCallbacks(), this.mHandler));
                this.mView = null;
            }
        }

        public View getNextView() {
            if (Flags.toastNoWeakref()) {
                return this.mNextView;
            }
            WeakReference<View> weakReference = this.mNextViewWeakRef;
            if (weakReference != null) {
                return weakReference.get();
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackBinder extends ITransientNotificationCallback.Stub {
        private final List<Callback> mCallbacks;
        private final Handler mHandler;

        private CallbackBinder(List<Callback> list, Handler handler) {
            this.mCallbacks = list;
            this.mHandler = handler;
        }

        @Override // android.app.ITransientNotificationCallback
        public void onToastShown() {
            this.mHandler.post(new Runnable() { // from class: android.widget.Toast$CallbackBinder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onToastShown$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onToastShown$0() {
            Iterator<Callback> it = getCallbacks().iterator();
            while (it.hasNext()) {
                it.next().onToastShown();
            }
        }

        @Override // android.app.ITransientNotificationCallback
        public void onToastHidden() {
            this.mHandler.post(new Runnable() { // from class: android.widget.Toast$CallbackBinder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onToastHidden$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onToastHidden$1() {
            Iterator<Callback> it = getCallbacks().iterator();
            while (it.hasNext()) {
                it.next().onToastHidden();
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
            Log.i(TAG, "Boot is not completed yet. Don't read settings db.");
            return false;
        }
        if (Settings.System.getInt(this.mContext.getContentResolver(), "game_no_interruption", 0) > 0) {
            String string = Settings.System.getString(this.mContext.getContentResolver(), "game_no_interruption_white_list");
            if (string != null) {
                if (string.contains(this.mContext.getPackageName())) {
                    Log.i(TAG, "GameNoInterruption mode. Show game toast. " + string);
                    return false;
                }
                Log.i(TAG, "GameNoInterruption mode. Block toast " + string);
                return true;
            }
            Log.i(TAG, "gameNoInterruption is on, but allowList is null.");
        }
        return false;
    }

    public static Toast semMakeAction(Context context, CharSequence charSequence, int i, CharSequence charSequence2, View.OnClickListener onClickListener) {
        return makeText(context, null, charSequence, i);
    }

    private int semGetFocusedDisplayId() {
        int i = this.mCustomDisplayId;
        if (i != -1) {
            return i;
        }
        try {
            return WindowManagerGlobal.getWindowManagerService().getTopFocusedDisplayId();
        } catch (RemoteException unused) {
            Log.w(TAG, "Unable to get focusedDisplayId");
            return 0;
        }
    }

    public void semSetPreferredDisplayType(int i) {
        this.mCustomDisplayId = 0;
        if (i == 1) {
            this.mCustomDisplayId = 2;
        } else {
            this.mCustomDisplayId = 0;
        }
    }

    private int getExternalDesktopDisplayId() {
        for (Display display : ((DisplayManager) this.mContext.getSystemService(Context.DISPLAY_SERVICE)).getDisplays()) {
            if ((display.getFlags() & 131072) != 0) {
                return display.getDisplayId();
            }
        }
        return -1;
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

    private Context semCreateDisplayContext(int i) {
        Display[] displays;
        Context contextCreateDisplayContext;
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService(Context.DISPLAY_SERVICE);
        if (displayManager == null) {
            return null;
        }
        if (i == 1) {
            displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        } else {
            displays = displayManager.getDisplays();
        }
        for (Display display : displays) {
            if (i == display.getDisplayId() && (contextCreateDisplayContext = this.mContext.createDisplayContext(display)) != null) {
                return new ContextThemeWrapper(contextCreateDisplayContext, 16974123);
            }
        }
        return null;
    }

    private String semGetMessageFromTv(View view) {
        CharSequence text;
        if (view != null) {
            View viewFindViewById = view.findViewById(16908299);
            if ((viewFindViewById instanceof TextView) && (text = ((TextView) viewFindViewById).getText()) != null) {
                return text.toString();
            }
            return "";
        }
        return "";
    }
}
