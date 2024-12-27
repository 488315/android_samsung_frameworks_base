package com.android.server.autofill.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.hardware.display.DisplayManager;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.service.autofill.FillResponse;
import android.util.Slog;
import android.view.Display;
import android.view.View;
import android.view.autofill.AutofillId;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.LocalServices;
import com.android.server.UiModeManagerService;
import com.android.server.UiThread;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.autofill.Helper;
import com.android.server.autofill.PresentationStatsEventLogger;
import com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda0;
import com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda27;
import com.android.server.autofill.SaveEventLogger;
import com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda0;
import com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda3;
import com.android.server.autofill.Session;
import com.android.server.autofill.Session$$ExternalSyntheticLambda9;
import com.android.server.autofill.ui.FillUi;
import com.android.server.autofill.ui.SaveUi;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AutoFillUI {
    public AutoFillUiCallback mCallback;
    public final Context mContext;
    public Context mContextForResources;
    public AutoFillUI$$ExternalSyntheticLambda9 mCreateFillUiRunnable;
    public DialogFillUi mFillDialog;
    public FillUi mFillUi;
    public final OverlayControl mOverlayControl;
    public SaveUi mSaveUi;
    public AutoFillUiCallback mSaveUiCallback;
    public final Handler mHandler = UiThread.getHandler();
    public final MetricsLogger mMetricsLogger = new MetricsLogger();
    public final UiModeManagerService.LocalService mUiModeMgr = (UiModeManagerService.LocalService) LocalServices.getService(UiModeManagerService.LocalService.class);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.autofill.ui.AutoFillUI$1, reason: invalid class name */
    public final class AnonymousClass1 implements SaveUi.OnSaveListener {
        public final /* synthetic */ int $r8$classId = 1;
        public final /* synthetic */ AutoFillUiCallback val$callback;
        public final /* synthetic */ Object val$focusedId;
        public final /* synthetic */ LogMaker val$log;
        public final /* synthetic */ Object val$response;

        public AnonymousClass1(LogMaker logMaker, SaveEventLogger saveEventLogger, Session session, PendingUi pendingUi) {
            this.val$log = logMaker;
            this.val$response = saveEventLogger;
            this.val$callback = session;
            this.val$focusedId = pendingUi;
        }

        public AnonymousClass1(LogMaker logMaker, AutoFillUiCallback autoFillUiCallback, FillResponse fillResponse, AutofillId autofillId) {
            this.val$log = logMaker;
            this.val$callback = autoFillUiCallback;
            this.val$response = fillResponse;
            this.val$focusedId = autofillId;
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void onCancel(IntentSender intentSender) {
            this.val$log.setType(5);
            SaveEventLogger saveEventLogger = (SaveEventLogger) this.val$response;
            if (saveEventLogger != null) {
                saveEventLogger.mEventInternal.ifPresent(new SaveEventLogger$$ExternalSyntheticLambda3(2));
            }
            AutoFillUI autoFillUI = AutoFillUI.this;
            AutoFillUiCallback autoFillUiCallback = this.val$callback;
            autoFillUI.hideSaveUiUiThread(autoFillUiCallback);
            if (intentSender != null) {
                try {
                    intentSender.sendIntent(autoFillUI.mContext, 0, null, null, null);
                } catch (IntentSender.SendIntentException e) {
                    Slog.e("AutofillUI", "Error starting negative action listener: " + intentSender, e);
                }
            }
            ((Session) autoFillUiCallback).cancelSave();
            autoFillUI.destroySaveUiUiThread((PendingUi) this.val$focusedId, true);
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public final void onDestroy() {
            switch (this.$r8$classId) {
                case 0:
                    if (this.val$log.getType() == 0) {
                        this.val$log.setType(2);
                    }
                    AutoFillUI.this.mMetricsLogger.write(this.val$log);
                    break;
                default:
                    if (this.val$log.getType() == 0) {
                        this.val$log.setType(2);
                        ((Session) this.val$callback).cancelSave();
                    }
                    AutoFillUI.this.mMetricsLogger.write(this.val$log);
                    SaveEventLogger saveEventLogger = (SaveEventLogger) this.val$response;
                    if (saveEventLogger != null) {
                        saveEventLogger.mEventInternal.ifPresent(new SaveEventLogger$$ExternalSyntheticLambda3(4));
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void onSave() {
            this.val$log.setType(4);
            SaveEventLogger saveEventLogger = (SaveEventLogger) this.val$response;
            if (saveEventLogger != null) {
                saveEventLogger.mEventInternal.ifPresent(new SaveEventLogger$$ExternalSyntheticLambda3(3));
            }
            AutoFillUI.this.hideSaveUiUiThread(this.val$callback);
            Session session = (Session) this.val$callback;
            synchronized (session.mLock) {
                try {
                    if (session.mDestroyed) {
                        Slog.w("AutofillSession", "Call to Session#save() rejected - session: " + session.id + " destroyed");
                    } else {
                        SaveEventLogger saveEventLogger2 = session.mSaveEventLogger;
                        saveEventLogger2.getClass();
                        saveEventLogger2.mEventInternal.ifPresent(new SaveEventLogger$$ExternalSyntheticLambda0(SystemClock.elapsedRealtime() - saveEventLogger2.mSessionStartTimestamp, 1));
                        session.mHandler.sendMessage(PooledLambda.obtainMessage(new Session$$ExternalSyntheticLambda9(), session.mService, session));
                    }
                } finally {
                }
            }
            AutoFillUI.this.destroySaveUiUiThread((PendingUi) this.val$focusedId, true);
        }

        public void requestHideFillUi() {
            AutoFillUiCallback autoFillUiCallback = AutoFillUI.this.mCallback;
            if (autoFillUiCallback != null) {
                AutofillId autofillId = (AutofillId) this.val$focusedId;
                Session session = (Session) autoFillUiCallback;
                synchronized (session.mLock) {
                    try {
                        session.mClient.requestHideFillUi(session.id, autofillId);
                    } catch (RemoteException e) {
                        Slog.e("AutofillSession", "Error requesting to hide fill UI", e);
                    }
                    session.mInlineSessionController.hideInlineSuggestionsUiLocked(autofillId);
                    session.mPresentationStatsEventLogger.mEventInternal.ifPresent(new PresentationStatsEventLogger$$ExternalSyntheticLambda27());
                }
            }
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void startIntentSender(IntentSender intentSender, Intent intent) {
            ((Session) this.val$callback).startIntentSender(intentSender, intent);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.autofill.ui.AutoFillUI$3, reason: invalid class name */
    public final class AnonymousClass3 {
        public final /* synthetic */ AutoFillUiCallback val$callback;
        public final /* synthetic */ AutofillId val$focusedId;
        public final /* synthetic */ LogMaker val$log;
        public final /* synthetic */ PresentationStatsEventLogger val$presentationStatsEventLogger;
        public final /* synthetic */ FillResponse val$response;
        public final /* synthetic */ Object val$sessionLock;

        public AnonymousClass3(AutoFillUiCallback autoFillUiCallback, FillResponse fillResponse, Object obj, PresentationStatsEventLogger presentationStatsEventLogger, AutofillId autofillId, LogMaker logMaker) {
            this.val$callback = autoFillUiCallback;
            this.val$response = fillResponse;
            this.val$sessionLock = obj;
            this.val$presentationStatsEventLogger = presentationStatsEventLogger;
            this.val$focusedId = autofillId;
            this.val$log = logMaker;
        }

        public final void log(int i) {
            this.val$log.setType(i);
            AutoFillUI.this.mMetricsLogger.write(this.val$log);
        }

        public final void onCanceled() {
            log(2);
            synchronized (this.val$sessionLock) {
                PresentationStatsEventLogger presentationStatsEventLogger = this.val$presentationStatsEventLogger;
                if (presentationStatsEventLogger != null) {
                    presentationStatsEventLogger.mEventInternal.ifPresent(new PresentationStatsEventLogger$$ExternalSyntheticLambda0(3));
                }
            }
            AutoFillUI.this.hideFillDialogUiThread(this.val$callback);
            ((Session) this.val$callback).requestShowSoftInput(this.val$focusedId);
            ((Session) this.val$callback).requestFallbackFromFillDialog();
        }

        public final void onResponsePicked(FillResponse fillResponse) {
            log(3);
            AutoFillUiCallback autoFillUiCallback = this.val$callback;
            AutoFillUI autoFillUI = AutoFillUI.this;
            autoFillUI.hideFillDialogUiThread(autoFillUiCallback);
            AutoFillUiCallback autoFillUiCallback2 = autoFillUI.mCallback;
            if (autoFillUiCallback2 != null) {
                ((Session) autoFillUiCallback2).authenticate(fillResponse.getRequestId(), fillResponse.getAuthentication(), fillResponse.getClientState(), 3);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface AutoFillUiCallback {
    }

    public AutoFillUI(Context context) {
        this.mContext = context;
        this.mOverlayControl = new OverlayControl(context);
        this.mContextForResources = getContextForResources(context, 0);
    }

    public static Context getContextForResources(Context context, int i) {
        DisplayManager displayManager;
        Display display;
        return (i == 0 || (displayManager = (DisplayManager) context.getSystemService("display")) == null || (display = displayManager.getDisplay(i)) == null) ? context : context.createDisplayContext(display);
    }

    public final void destroySaveUiUiThread(PendingUi pendingUi, boolean z) {
        if (this.mSaveUi == null) {
            if (Helper.sDebug) {
                Slog.d("AutofillUI", "destroySaveUiUiThread(): already destroyed");
                return;
            }
            return;
        }
        if (Helper.sDebug) {
            Slog.d("AutofillUI", "destroySaveUiUiThread(): " + pendingUi);
        }
        SaveUi saveUi = this.mSaveUi;
        OverlayControl overlayControl = saveUi.mOverlayControl;
        SaveUi.OneActionThenDestroyListener oneActionThenDestroyListener = saveUi.mListener;
        try {
            if (Helper.sDebug) {
                Slog.d("SaveUi", "destroy()");
            }
            if (saveUi.mDestroyed) {
                throw new IllegalStateException("cannot interact with a destroyed instance");
            }
            oneActionThenDestroyListener.onDestroy();
            saveUi.mHandler.removeCallbacksAndMessages(oneActionThenDestroyListener);
            saveUi.mDialog.dismiss();
            saveUi.mDestroyed = true;
            overlayControl.setOverlayAllowed(true);
            this.mSaveUi = null;
            this.mSaveUiCallback = null;
            if (pendingUi != null && z) {
                try {
                    if (Helper.sDebug) {
                        Slog.d("AutofillUI", "destroySaveUiUiThread(): notifying client");
                    }
                    pendingUi.client.setSaveUiState(pendingUi.sessionId, false);
                } catch (RemoteException e) {
                    Slog.e("AutofillUI", "Error notifying client to set save UI state to hidden: " + e);
                }
            }
            if (this.mCreateFillUiRunnable != null) {
                if (Helper.sDebug) {
                    Slog.d("AutofillUI", "start the pending fill UI request..");
                }
                this.mHandler.post(this.mCreateFillUiRunnable);
                this.mCreateFillUiRunnable = null;
            }
        } catch (Throwable th) {
            overlayControl.setOverlayAllowed(true);
            throw th;
        }
    }

    public final void dump(PrintWriter printWriter) {
        ProcessList$$ExternalSyntheticOutline0.m(printWriter, "Autofill UI", "  ", "Night mode: ");
        printWriter.println(this.mUiModeMgr.isNightMode());
        if (this.mFillUi != null) {
            printWriter.print("  ");
            printWriter.println("showsFillUi: true");
            FillUi fillUi = this.mFillUi;
            fillUi.getClass();
            printWriter.print("    ");
            printWriter.print("mCallback: ");
            AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "    ", "mFullScreen: ", fillUi.mCallback != null);
            AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "    ", "mVisibleDatasetsMaxCount: ", fillUi.mFullScreen);
            printWriter.println(fillUi.mVisibleDatasetsMaxCount);
            if (fillUi.mHeader != null) {
                printWriter.print("    ");
                printWriter.print("mHeader: ");
                printWriter.println(fillUi.mHeader);
            }
            if (fillUi.mListView != null) {
                printWriter.print("    ");
                printWriter.print("mListView: ");
                printWriter.println(fillUi.mListView);
            }
            if (fillUi.mFooter != null) {
                printWriter.print("    ");
                printWriter.print("mFooter: ");
                printWriter.println(fillUi.mFooter);
            }
            FillUi.ItemsAdapter itemsAdapter = fillUi.mAdapter;
            if (itemsAdapter != null) {
                printWriter.print("    ");
                printWriter.print("mAdapter: ");
                printWriter.println(itemsAdapter);
            }
            if (fillUi.mFilterText != null) {
                printWriter.print("    ");
                printWriter.print("mFilterText: ");
                String str = fillUi.mFilterText;
                if (str == null) {
                    printWriter.println("null");
                } else {
                    printWriter.print(str.length());
                    printWriter.println("_chars");
                }
            }
            printWriter.print("    ");
            printWriter.print("mContentWidth: ");
            BroadcastStats$$ExternalSyntheticOutline0.m(fillUi.mContentWidth, printWriter, "    ", "mContentHeight: ");
            BroadcastStats$$ExternalSyntheticOutline0.m(fillUi.mContentHeight, printWriter, "    ", "mDestroyed: ");
            AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "    ", "mContext: ", fillUi.mDestroyed);
            printWriter.println(fillUi.mContext);
            printWriter.print("    ");
            printWriter.print("theme id: ");
            int i = fillUi.mThemeId;
            printWriter.print(i);
            if (i == 16974893) {
                printWriter.println(" (dark)");
            } else if (i != 16974907) {
                printWriter.println("(UNKNOWN_MODE)");
            } else {
                printWriter.println(" (light)");
            }
            FillUi.AnchoredWindow anchoredWindow = fillUi.mWindow;
            if (anchoredWindow != null) {
                printWriter.print("    ");
                printWriter.print("mWindow: ");
                printWriter.println();
                printWriter.print("      ");
                printWriter.print("showing: ");
                AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "      ", "view: ", anchoredWindow.mShowing);
                printWriter.println(anchoredWindow.mContentView);
                if (anchoredWindow.mShowParams != null) {
                    printWriter.print("      ");
                    printWriter.print("params: ");
                    printWriter.println(anchoredWindow.mShowParams);
                }
                printWriter.print("      ");
                printWriter.print("screen coordinates: ");
                View view = anchoredWindow.mContentView;
                if (view == null) {
                    printWriter.println("N/A");
                } else {
                    int[] locationOnScreen = view.getLocationOnScreen();
                    printWriter.print(locationOnScreen[0]);
                    printWriter.print("x");
                    printWriter.println(locationOnScreen[1]);
                }
            }
        } else {
            printWriter.print("  ");
            printWriter.println("showsFillUi: false");
        }
        if (this.mSaveUi != null) {
            printWriter.print("  ");
            printWriter.println("showsSaveUi: true");
            SaveUi saveUi = this.mSaveUi;
            saveUi.getClass();
            printWriter.print("    ");
            printWriter.print("title: ");
            printWriter.println(saveUi.mTitle);
            printWriter.print("    ");
            printWriter.print("subtitle: ");
            printWriter.println(saveUi.mSubTitle);
            printWriter.print("    ");
            printWriter.print("pendingUi: ");
            printWriter.println(saveUi.mPendingUi);
            printWriter.print("    ");
            printWriter.print("service: ");
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, saveUi.mServicePackageName, "    ", "app: ");
            printWriter.println(saveUi.mComponentName.toShortString());
            printWriter.print("    ");
            printWriter.print("compat mode: ");
            AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "    ", "theme id: ", saveUi.mCompatMode);
            int i2 = saveUi.mThemeId;
            printWriter.print(i2);
            if (i2 == 16974895) {
                printWriter.println(" (dark)");
            } else if (i2 != 16974908) {
                printWriter.println("(UNKNOWN_MODE)");
            } else {
                printWriter.println(" (light)");
            }
            View decorView = saveUi.mDialog.getWindow().getDecorView();
            int[] locationOnScreen2 = decorView.getLocationOnScreen();
            printWriter.print("    ");
            printWriter.print("coordinates: ");
            printWriter.print('(');
            printWriter.print(locationOnScreen2[0]);
            printWriter.print(',');
            printWriter.print(locationOnScreen2[1]);
            printWriter.print(')');
            printWriter.print('(');
            printWriter.print(decorView.getWidth() + locationOnScreen2[0]);
            printWriter.print(',');
            printWriter.print(decorView.getHeight() + locationOnScreen2[1]);
            printWriter.println(')');
            printWriter.print("    ");
            printWriter.print("destroyed: ");
            printWriter.println(saveUi.mDestroyed);
        } else {
            printWriter.print("  ");
            printWriter.println("showsSaveUi: false");
        }
        if (this.mFillDialog == null) {
            printWriter.print("  ");
            printWriter.println("showsFillDialog: false");
            return;
        }
        printWriter.print("  ");
        printWriter.println("showsFillDialog: true");
        DialogFillUi dialogFillUi = this.mFillDialog;
        dialogFillUi.getClass();
        printWriter.print("    ");
        printWriter.print("service: ");
        ProcessList$$ExternalSyntheticOutline0.m(printWriter, dialogFillUi.mServicePackageName, "    ", "app: ");
        printWriter.println(dialogFillUi.mComponentName.toShortString());
        printWriter.print("    ");
        printWriter.print("theme id: ");
        int i3 = dialogFillUi.mThemeId;
        printWriter.print(i3);
        if (i3 == 16974895) {
            printWriter.println(" (dark)");
        } else if (i3 != 16974908) {
            printWriter.println("(UNKNOWN_MODE)");
        } else {
            printWriter.println(" (light)");
        }
        View decorView2 = dialogFillUi.mDialog.getWindow().getDecorView();
        int[] locationOnScreen3 = decorView2.getLocationOnScreen();
        printWriter.print("    ");
        printWriter.print("coordinates: ");
        printWriter.print('(');
        printWriter.print(locationOnScreen3[0]);
        printWriter.print(',');
        printWriter.print(locationOnScreen3[1]);
        printWriter.print(')');
        printWriter.print('(');
        printWriter.print(decorView2.getWidth() + locationOnScreen3[0]);
        printWriter.print(',');
        printWriter.print(decorView2.getHeight() + locationOnScreen3[1]);
        printWriter.println(')');
        printWriter.print("    ");
        printWriter.print("destroyed: ");
        printWriter.println(dialogFillUi.mDestroyed);
    }

    public final void hideAllUiThread(AutoFillUiCallback autoFillUiCallback) {
        hideFillUiUiThread(autoFillUiCallback, true);
        hideFillDialogUiThread(autoFillUiCallback);
        PendingUi hideSaveUiUiThread = hideSaveUiUiThread(autoFillUiCallback);
        if (hideSaveUiUiThread == null || hideSaveUiUiThread.mState != 4) {
            return;
        }
        if (Helper.sDebug) {
            Slog.d("AutofillUI", "hideAllUiThread(): destroying Save UI because pending restoration is finished");
        }
        destroySaveUiUiThread(hideSaveUiUiThread, true);
    }

    public final void hideFillDialogUiThread(AutoFillUiCallback autoFillUiCallback) {
        DialogFillUi dialogFillUi = this.mFillDialog;
        if (dialogFillUi != null) {
            if (autoFillUiCallback == null || autoFillUiCallback == this.mCallback) {
                OverlayControl overlayControl = dialogFillUi.mOverlayControl;
                try {
                    if (Helper.sDebug) {
                        Slog.d("DialogFillUi", "destroy()");
                    }
                    if (dialogFillUi.mDestroyed) {
                        throw new IllegalStateException("cannot interact with a destroyed instance");
                    }
                    dialogFillUi.mDialog.dismiss();
                    dialogFillUi.mDestroyed = true;
                    overlayControl.setOverlayAllowed(true);
                    this.mFillDialog = null;
                } catch (Throwable th) {
                    overlayControl.setOverlayAllowed(true);
                    throw th;
                }
            }
        }
    }

    public final void hideFillUiUiThread(AutoFillUiCallback autoFillUiCallback, boolean z) {
        FillUi fillUi = this.mFillUi;
        if (fillUi != null) {
            if (autoFillUiCallback == null || autoFillUiCallback == this.mCallback) {
                if (fillUi.mDestroyed) {
                    throw new IllegalStateException("cannot interact with a destroyed instance");
                }
                FillUi.AnchoredWindow anchoredWindow = fillUi.mWindow;
                if (anchoredWindow != null) {
                    anchoredWindow.hide(false);
                }
                fillUi.mCallback.onDestroy();
                if (z) {
                    AnonymousClass1 anonymousClass1 = fillUi.mCallback;
                    AutoFillUiCallback autoFillUiCallback2 = AutoFillUI.this.mCallback;
                    if (autoFillUiCallback2 != null) {
                        AutofillId autofillId = (AutofillId) anonymousClass1.val$focusedId;
                        Session session = (Session) autoFillUiCallback2;
                        synchronized (session.mLock) {
                            try {
                                session.mClient.requestHideFillUiWhenDestroyed(session.id, autofillId);
                            } catch (RemoteException e) {
                                Slog.e("AutofillSession", "Error requesting to hide fill UI", e);
                            }
                            session.mInlineSessionController.hideInlineSuggestionsUiLocked(autofillId);
                        }
                    }
                }
                fillUi.mDestroyed = true;
                this.mFillUi = null;
            }
        }
    }

    public final PendingUi hideSaveUiUiThread(AutoFillUiCallback autoFillUiCallback) {
        if (Helper.sVerbose) {
            Slog.v("AutofillUI", "hideSaveUiUiThread(): mSaveUi=" + this.mSaveUi + ", callback=" + autoFillUiCallback + ", mCallback=" + this.mCallback);
        }
        SaveUi saveUi = this.mSaveUi;
        if (saveUi == null || this.mSaveUiCallback != autoFillUiCallback) {
            return null;
        }
        return saveUi.hide();
    }

    public final void showError(CharSequence charSequence, AutoFillUiCallback autoFillUiCallback) {
        Slog.w("AutofillUI", "showError(): " + ((Object) charSequence));
        this.mHandler.post(new AutoFillUI$$ExternalSyntheticLambda8(this, autoFillUiCallback, charSequence));
    }
}
