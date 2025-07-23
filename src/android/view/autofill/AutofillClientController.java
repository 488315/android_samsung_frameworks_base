package android.view.autofill;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Dumpable;
import android.util.Log;
import android.util.Slog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowManagerGlobal;
import android.view.autofill.AutofillManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public final class AutofillClientController implements AutofillManager.AutofillClient, Dumpable {
    public static final String AUTOFILL_RESET_NEEDED = "@android:autofillResetNeeded";
    public static final String AUTO_FILL_AUTH_WHO_PREFIX = "@android:autoFillAuth:";
    public static final String DUMPABLE_NAME = "AutofillManager";
    public static final String LAST_AUTOFILL_ID = "android:lastAutofillId";
    private static final String TAG = "AutofillClientController";
    private final Activity mActivity;
    private boolean mAutoFillIgnoreFirstResumePause;
    private boolean mAutoFillResetNeeded;
    private AutofillManager mAutofillManager;
    private AutofillPopupWindow mAutofillPopupWindow;
    public int mLastAutofillId = View.LAST_APP_AUTOFILL_ID;
    private Boolean mRelayoutFix;
    private static final String LOG_TAG = "autofill_client";
    public static final boolean DEBUG = Log.isLoggable(LOG_TAG, 3);

    public AutofillClientController(Activity activity) {
        this.mActivity = activity;
    }

    private AutofillManager getAutofillManager() {
        if (this.mAutofillManager == null) {
            this.mAutofillManager = (AutofillManager) this.mActivity.getSystemService(AutofillManager.class);
        }
        return this.mAutofillManager;
    }

    public boolean isRelayoutFixEnabled() {
        AutofillManager autofillManager = getAutofillManager();
        if (autofillManager == null) {
            if (!Helper.sDebug) {
                return false;
            }
            Log.d(TAG, "isRelayoutFixEnabled() : getAutofillManager() == null");
            return false;
        }
        if (this.mRelayoutFix == null) {
            this.mRelayoutFix = Boolean.valueOf(autofillManager.isRelayoutFixEnabled());
        }
        return this.mRelayoutFix.booleanValue();
    }

    public void onActivityAttached(Application application) {
        this.mActivity.setAutofillOptions(application.getAutofillOptions());
    }

    public void onActivityCreated(Bundle bundle) {
        this.mAutoFillResetNeeded = bundle.getBoolean(AUTOFILL_RESET_NEEDED, false);
        this.mLastAutofillId = bundle.getInt(LAST_AUTOFILL_ID, View.LAST_APP_AUTOFILL_ID);
        if (this.mAutoFillResetNeeded) {
            getAutofillManager().onCreate(bundle);
        }
    }

    public void onActivityStarted() {
        if (this.mAutoFillResetNeeded) {
            getAutofillManager().onVisibleForAutofill();
        }
    }

    public void onActivityResumed() {
        if (Helper.sVerbose) {
            Log.v(TAG, "onActivityResumed()");
        }
        if (isRelayoutFixEnabled()) {
            return;
        }
        if (Helper.sVerbose) {
            Log.v(TAG, "onActivityResumed(): Relayout fix not enabled");
        }
        forResume();
    }

    public void onActivityPostResumed() {
        if (Helper.sVerbose) {
            Log.v(TAG, "onActivityPostResumed()");
        }
        if (isRelayoutFixEnabled()) {
            if (Helper.sVerbose) {
                Log.v(TAG, "onActivityPostResumed(): Relayout fix enabled");
            }
            forResume();
        }
    }

    private void forResume() {
        View currentFocus;
        enableAutofillCompatibilityIfNeeded();
        boolean isRelayoutFixEnabled = isRelayoutFixEnabled();
        if (isRelayoutFixEnabled) {
            if (getAutofillManager().shouldRetryFill()) {
                if (Helper.sVerbose) {
                    Log.v(TAG, "forResume(): Autofill potential relayout. Retrying fill.");
                }
                getAutofillManager().attemptRefill();
            } else if (Helper.sVerbose) {
                Log.v(TAG, "forResume(): Not attempting refill.");
            }
        }
        if (!this.mAutoFillResetNeeded || this.mAutoFillIgnoreFirstResumePause || (currentFocus = this.mActivity.getCurrentFocus()) == null || !currentFocus.canNotifyAutofillEnterExitEvent()) {
            return;
        }
        if (isRelayoutFixEnabled && getAutofillManager().isAuthenticationPending()) {
            if (Helper.sVerbose) {
                Log.v(TAG, "forResume(): ignoring focus due to auth pending");
            }
        } else {
            if (Helper.sVerbose) {
                Log.v(TAG, "forResume(): notifyViewEntered");
            }
            getAutofillManager().notifyViewEntered(currentFocus);
        }
    }

    public void onActivityPerformResume(boolean z) {
        if (this.mAutoFillResetNeeded) {
            this.mAutoFillIgnoreFirstResumePause = z;
            if (z && DEBUG) {
                Slog.v(TAG, "autofill will ignore first pause when relaunching " + this);
            }
        }
    }

    public void onActivityPaused() {
        if (this.mAutoFillResetNeeded) {
            if (!this.mAutoFillIgnoreFirstResumePause) {
                if (DEBUG) {
                    Log.v(TAG, "autofill notifyViewExited " + this);
                }
                View currentFocus = this.mActivity.getCurrentFocus();
                if (currentFocus == null || !currentFocus.canNotifyAutofillEnterExitEvent()) {
                    return;
                }
                getAutofillManager().notifyViewExited(currentFocus);
                return;
            }
            if (DEBUG) {
                Log.v(TAG, "autofill got first pause " + this);
            }
            this.mAutoFillIgnoreFirstResumePause = false;
        }
    }

    public void onActivityStopped(Intent intent, boolean z) {
        if (this.mAutoFillResetNeeded) {
            getAutofillManager().onInvisibleForAutofill(!z);
        } else if (intent != null && intent.hasExtra(AutofillManager.EXTRA_RESTORE_SESSION_TOKEN) && intent.hasExtra(AutofillManager.EXTRA_RESTORE_CROSS_ACTIVITY)) {
            restoreAutofillSaveUi(intent);
        }
    }

    public void onActivityDestroyed() {
        if (this.mActivity.isFinishing() && this.mAutoFillResetNeeded) {
            getAutofillManager().onActivityFinishing();
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(LAST_AUTOFILL_ID, this.mLastAutofillId);
        if (this.mAutoFillResetNeeded) {
            bundle.putBoolean(AUTOFILL_RESET_NEEDED, true);
            getAutofillManager().onSaveInstanceState(bundle);
        }
    }

    public void onActivityFinish(Intent intent) {
        if (intent == null || !intent.hasExtra(AutofillManager.EXTRA_RESTORE_SESSION_TOKEN)) {
            return;
        }
        restoreAutofillSaveUi(intent);
    }

    public void onActivityBackPressed(Intent intent) {
        if (intent == null || !intent.hasExtra(AutofillManager.EXTRA_RESTORE_SESSION_TOKEN)) {
            return;
        }
        restoreAutofillSaveUi(intent);
    }

    public void onDispatchActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            intent = null;
        }
        getAutofillManager().onAuthenticationResult(i, intent, this.mActivity.getCurrentFocus());
    }

    public void onStartActivity(Intent intent, Intent intent2) {
        if (intent2 != null && intent2.hasExtra(AutofillManager.EXTRA_RESTORE_SESSION_TOKEN) && intent2.hasExtra(AutofillManager.EXTRA_RESTORE_CROSS_ACTIVITY) && TextUtils.equals(this.mActivity.getPackageName(), intent.resolveActivity(this.mActivity.getPackageManager()).getPackageName())) {
            IBinder iBinderExtra = intent2.getIBinderExtra(AutofillManager.EXTRA_RESTORE_SESSION_TOKEN);
            intent2.removeExtra(AutofillManager.EXTRA_RESTORE_SESSION_TOKEN);
            intent2.removeExtra(AutofillManager.EXTRA_RESTORE_CROSS_ACTIVITY);
            intent.putExtra(AutofillManager.EXTRA_RESTORE_SESSION_TOKEN, iBinderExtra);
            intent.putExtra(AutofillManager.EXTRA_RESTORE_CROSS_ACTIVITY, true);
        }
    }

    public void restoreAutofillSaveUi(Intent intent) {
        IBinder iBinderExtra = intent.getIBinderExtra(AutofillManager.EXTRA_RESTORE_SESSION_TOKEN);
        intent.removeExtra(AutofillManager.EXTRA_RESTORE_SESSION_TOKEN);
        intent.removeExtra(AutofillManager.EXTRA_RESTORE_CROSS_ACTIVITY);
        getAutofillManager().onPendingSaveUi(2, iBinderExtra);
    }

    public void enableAutofillCompatibilityIfNeeded() {
        AutofillManager autofillManager;
        if (!this.mActivity.isAutofillCompatibilityEnabled() || (autofillManager = (AutofillManager) this.mActivity.getSystemService(AutofillManager.class)) == null) {
            return;
        }
        autofillManager.enableCompatibilityMode();
    }

    @Override // android.util.Dumpable
    public String getDumpableName() {
        return DUMPABLE_NAME;
    }

    @Override // android.util.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        AutofillManager autofillManager = getAutofillManager();
        if (autofillManager != null) {
            autofillManager.dump("", printWriter);
            printWriter.print("");
            printWriter.print("Autofill Compat Mode: ");
            printWriter.println(this.mActivity.isAutofillCompatibilityEnabled());
            return;
        }
        printWriter.print("");
        printWriter.println("No AutofillManager");
    }

    public int getNextAutofillId() {
        if (this.mLastAutofillId == 2147483646) {
            this.mLastAutofillId = View.LAST_APP_AUTOFILL_ID;
        }
        int i = this.mLastAutofillId + 1;
        this.mLastAutofillId = i;
        return i;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public AutofillId autofillClientGetNextAutofillId() {
        return new AutofillId(getNextAutofillId());
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean autofillClientIsCompatibilityModeEnabled() {
        return this.mActivity.isAutofillCompatibilityEnabled();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean autofillClientIsVisibleForAutofill() {
        return this.mActivity.isVisibleForAutofill();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public ComponentName autofillClientGetComponentName() {
        return this.mActivity.getComponentName();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public IBinder autofillClientGetActivityToken() {
        return this.mActivity.getActivityToken();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean[] autofillClientGetViewVisibility(AutofillId[] autofillIdArr) {
        int length = autofillIdArr.length;
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            AutofillId autofillId = autofillIdArr[i];
            if (autofillId == null) {
                zArr[i] = false;
            } else {
                View autofillClientFindViewByAutofillIdTraversal = autofillClientFindViewByAutofillIdTraversal(autofillId);
                if (autofillClientFindViewByAutofillIdTraversal != null) {
                    if (!autofillId.isVirtualInt()) {
                        zArr[i] = autofillClientFindViewByAutofillIdTraversal.isVisibleToUser();
                    } else {
                        zArr[i] = autofillClientFindViewByAutofillIdTraversal.isVisibleToUserForAutofill(autofillId.getVirtualChildIntId());
                    }
                }
            }
        }
        if (Helper.sVerbose) {
            Log.v(TAG, "autofillClientGetViewVisibility(): " + Arrays.toString(zArr));
        }
        return zArr;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public View autofillClientFindViewByAccessibilityIdTraversal(int i, int i2) {
        View findViewByAccessibilityIdTraversal;
        ArrayList<ViewRootImpl> rootViews = WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
        for (int i3 = 0; i3 < rootViews.size(); i3++) {
            View view = rootViews.get(i3).getView();
            if (view != null && view.getAccessibilityWindowId() == i2 && (findViewByAccessibilityIdTraversal = view.findViewByAccessibilityIdTraversal(i)) != null) {
                return findViewByAccessibilityIdTraversal;
            }
        }
        return null;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public View autofillClientFindViewByAutofillIdTraversal(AutofillId autofillId) {
        View findViewByAutofillIdTraversal;
        if (autofillId == null) {
            return null;
        }
        ArrayList<ViewRootImpl> rootViews = WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
        for (int i = 0; i < rootViews.size(); i++) {
            View view = rootViews.get(i).getView();
            if (view != null && (findViewByAutofillIdTraversal = view.findViewByAutofillIdTraversal(autofillId.getViewId())) != null) {
                return findViewByAutofillIdTraversal;
            }
        }
        return null;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public View[] autofillClientFindViewsByAutofillIdTraversal(AutofillId[] autofillIdArr) {
        View[] viewArr = new View[autofillIdArr.length];
        ArrayList<ViewRootImpl> rootViews = WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
        for (int i = 0; i < rootViews.size(); i++) {
            View view = rootViews.get(i).getView();
            if (view != null) {
                int length = autofillIdArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    AutofillId autofillId = autofillIdArr[i2];
                    if (autofillId != null && viewArr[i2] == null) {
                        viewArr[i2] = view.findViewByAutofillIdTraversal(autofillId.getViewId());
                    }
                }
            }
        }
        return viewArr;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public List<View> autofillClientFindAutofillableViewsByTraversal() {
        ArrayList arrayList = new ArrayList();
        ArrayList<ViewRootImpl> rootViews = WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
        for (int i = 0; i < rootViews.size(); i++) {
            View view = rootViews.get(i).getView();
            if (view != null) {
                view.findAutofillableViewsByTraversal(arrayList);
            }
        }
        return arrayList;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean autofillClientIsFillUiShowing() {
        AutofillPopupWindow autofillPopupWindow = this.mAutofillPopupWindow;
        return autofillPopupWindow != null && autofillPopupWindow.isShowing();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean autofillClientRequestHideFillUi() {
        AutofillPopupWindow autofillPopupWindow = this.mAutofillPopupWindow;
        if (autofillPopupWindow == null) {
            return false;
        }
        autofillPopupWindow.dismiss();
        this.mAutofillPopupWindow = null;
        return true;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean autofillClientRequestShowFillUi(View view, int i, int i2, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) {
        boolean isShowing;
        AutofillPopupWindow autofillPopupWindow = this.mAutofillPopupWindow;
        if (autofillPopupWindow == null) {
            this.mAutofillPopupWindow = new AutofillPopupWindow(iAutofillWindowPresenter);
            isShowing = false;
        } else {
            isShowing = autofillPopupWindow.isShowing();
        }
        this.mAutofillPopupWindow.update(view, 0, 0, i, i2, rect);
        return !isShowing && this.mAutofillPopupWindow.isShowing();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public void autofillClientDispatchUnhandledKey(View view, KeyEvent keyEvent) {
        ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.dispatchKeyFromAutofill(keyEvent);
        }
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean isDisablingEnterExitEventForAutofill() {
        return this.mAutoFillIgnoreFirstResumePause || !this.mActivity.isResumed();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public void autofillClientResetableStateAvailable() {
        this.mAutoFillResetNeeded = true;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public void autofillClientRunOnUiThread(Runnable runnable) {
        this.mActivity.runOnUiThread(runnable);
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public void autofillClientAuthenticate(int i, IntentSender intentSender, Intent intent, boolean z) {
        IntentSender intentSender2;
        try {
            intentSender2 = intentSender;
        } catch (IntentSender.SendIntentException e) {
            e = e;
            intentSender2 = intentSender;
        }
        try {
            this.mActivity.startIntentSenderForResult(intentSender2, AUTO_FILL_AUTH_WHO_PREFIX, i, intent, 0, 0, ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
        } catch (IntentSender.SendIntentException e2) {
            e = e2;
            Log.e(TAG, "authenticate() failed for intent:" + intentSender2, e);
        }
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean isActivityResumed() {
        return this.mActivity.isResumed();
    }
}
