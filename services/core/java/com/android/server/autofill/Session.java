package com.android.server.autofill;

import android.R;
import android.app.ActivityClient;
import android.app.ActivityTaskManager;
import android.app.IAssistDataReceiver;
import android.app.PendingIntent;
import android.app.assist.AssistStructure;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.credentials.GetCredentialException;
import android.credentials.GetCredentialResponse;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.metrics.LogMaker;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.os.TransactionTooLargeException;
import android.os.UserManager;
import android.provider.Settings;
import android.service.assist.classification.FieldClassification;
import android.service.assist.classification.FieldClassificationRequest;
import android.service.assist.classification.FieldClassificationResponse;
import android.service.assist.classification.IFieldClassificationCallback;
import android.service.assist.classification.IFieldClassificationService;
import android.service.autofill.AutofillServiceInfo;
import android.service.autofill.Dataset;
import android.service.autofill.Field;
import android.service.autofill.FieldClassification;
import android.service.autofill.FillContext;
import android.service.autofill.FillEventHistory;
import android.service.autofill.FillRequest;
import android.service.autofill.FillResponse;
import android.service.autofill.Flags;
import android.service.autofill.IAutoFillService;
import android.service.autofill.InlinePresentation;
import android.service.autofill.InternalSanitizer;
import android.service.autofill.SaveInfo;
import android.service.autofill.SaveRequest;
import android.service.autofill.ValueFinder;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LocalLog;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.view.Display;
import android.view.autofill.AutofillFeatureFlags;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAutoFillManagerClient;
import android.view.inputmethod.InlineSuggestion;
import android.view.inputmethod.InlineSuggestionInfo;
import android.view.inputmethod.InlineSuggestionsRequest;

import com.android.internal.infra.ServiceConnector;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.autofill.ui.AutoFillUI;
import com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda1;
import com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda3;
import com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda8;
import com.android.server.autofill.ui.AutoFillUI.AnonymousClass3;
import com.android.server.autofill.ui.DialogFillUi;
import com.android.server.autofill.ui.InlineContentProviderImpl;
import com.android.server.autofill.ui.InlineFillUi;
import com.android.server.autofill.ui.InlineSuggestionFactory;
import com.android.server.autofill.ui.PendingUi;
import com.android.server.autofill.ui.RemoteInlineSuggestionViewConnector;
import com.android.server.contentcapture.ContentCaptureManagerService;
import com.android.server.contentcapture.ContentCapturePerUserService;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.utils.Slogf;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Session
        implements RemoteFillService.FillServiceCallbacks,
                ViewState.Listener,
                AutoFillUI.AutoFillUiCallback,
                ValueFinder,
                RemoteFieldClassificationService.FieldClassificationServiceCallbacks {
    public static final RequestId mRequestId;
    public static final AtomicInteger sIdCounterForPcc;
    public final int id;
    public IBinder mActivityToken;
    public final AssistDataReceiverImpl mAssistReceiver;
    public Runnable mAugmentedAutofillDestroyer;
    public ArrayList mAugmentedAutofillableIds;
    public ArrayList mAugmentedRequestsLogs;
    public final ClassificationState mClassificationState;
    public IAutoFillManagerClient mClient;
    public Bundle mClientState;
    public Session$$ExternalSyntheticLambda3 mClientVulture;
    public final boolean mCompatMode;
    public final ComponentName mComponentName;
    public final Context mContext;
    public ArrayList mContexts;
    public AutofillId mCurrentViewId;
    public final AnonymousClass1 mDelayedFillBroadcastReceiver;
    public boolean mDelayedFillBroadcastReceiverRegistered;
    public PendingIntent mDelayedFillPendingIntent;
    public boolean mDestroyed;
    public int mDisplayId;
    public int mFieldClassificationIdSnapshot;
    public final FillRequestEventLogger mFillRequestEventLogger;
    public int mFillRequestIdSnapshot;
    public final FillResponseEventLogger mFillResponseEventLogger;
    public final int mFlags;
    public final Handler mHandler;
    public boolean mHasCallback;
    public final boolean mIgnoreViewStateResetToEmpty;
    public final AutofillInlineSessionController mInlineSessionController;
    public final boolean mIsPrimaryCredential;
    public AutofillId[] mLastFillDialogTriggerIds;
    public Pair mLastInlineSuggestionsRequest;
    public long mLatencyBaseTime;
    public final Object mLock;
    public boolean mLogViewEntered;
    public boolean mLoggedInlineDatasetShown;
    public final MetricsLogger mMetricsLogger;
    public final PccAssistDataReceiverImpl mPccAssistReceiver;
    public PendingUi mPendingSaveUi;
    public final PresentationStatsEventLogger mPresentationStatsEventLogger;
    public AutofillId mPreviousNonNullEnteredViewId;
    public boolean mPreviouslyFillDialogPotentiallyStarted;
    public final RemoteFillService mRemoteFillService;
    public int mRequestCount;
    public final SparseArray mRequestLogs;
    public SparseArray mResponses;
    public final SaveEventLogger mSaveEventLogger;
    public boolean mSaveOnAllViewsInvisible;
    public final SecondaryProviderHandler mSecondaryProviderHandler;
    public SparseArray mSecondaryResponses;
    public ArrayList mSelectedDatasetIds;
    public final AutofillManagerServiceImpl mService;
    public final SessionCommittedEventLogger mSessionCommittedEventLogger;
    public final SessionFlags mSessionFlags;
    public int mSessionState;
    public final long mStartTime;
    public final AutoFillUI mUi;
    public final LocalLog mUiLatencyHistory;
    public long mUiShownTime;
    public AssistStructure.ViewNode mUrlBar;
    public final ArrayMap mViewStates;
    public final LocalLog mWtfHistory;
    public final int taskId;
    public final int uid;
    public final int userId;

    /* renamed from: com.android.server.autofill.Session$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}
    }

    /* renamed from: com.android.server.autofill.Session$3, reason: invalid class name */
    public final class AnonymousClass3 implements InlineFillUi.InlineSuggestionUiCallback {
        public final /* synthetic */ int $r8$classId;
        public Object this$0;
        public Object val$focusedId;
        public Object val$response;

        public AnonymousClass3() {
            this.$r8$classId = 1;
            this.val$response = new LinkedHashSet();
            this.val$focusedId = new LinkedHashSet();
            this.this$0 = new LinkedHashMap();
        }

        public AnonymousClass3(Session session, FillResponse fillResponse, AutofillId autofillId) {
            this.$r8$classId = 0;
            this.this$0 = session;
            this.val$response = fillResponse;
            this.val$focusedId = autofillId;
        }

        @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
        public void authenticate() {
            ((Session) this.this$0)
                    .authenticate(
                            ((FillResponse) this.val$response).getRequestId(),
                            ((FillResponse) this.val$response).getAuthentication(),
                            ((FillResponse) this.val$response).getClientState(),
                            2);
        }

        @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
        public void autofill(Dataset dataset, int i) {
            ((Session) this.this$0)
                    .fill(((FillResponse) this.val$response).getRequestId(), i, dataset, 2);
        }

        @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
        public void onError() {
            synchronized (((Session) this.this$0).mLock) {
                AutofillInlineSessionController autofillInlineSessionController =
                        ((Session) this.this$0).mInlineSessionController;
                autofillInlineSessionController.mInlineFillUi =
                        new InlineFillUi((AutofillId) this.val$focusedId);
                autofillInlineSessionController.requestImeToShowInlineSuggestionsLocked();
            }
        }

        @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
        public void onInflate() {
            ((Session) this.this$0).onShown(2, 1);
        }

        @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
        public void startIntentSender(IntentSender intentSender) {
            ((Session) this.this$0).startIntentSender(intentSender, new Intent());
        }

        public String toString() {
            switch (this.$r8$classId) {
                case 1:
                    StringBuilder sb = new StringBuilder("DatasetComputationContainer[");
                    if (((Set) this.val$response) != null) {
                        sb.append(", autofillIds=");
                        sb.append((Set) this.val$response);
                    }
                    if (((Set) this.val$focusedId) != null) {
                        sb.append(", mDatasets=");
                        sb.append((Set) this.val$focusedId);
                    }
                    if (((Map) this.this$0) != null) {
                        sb.append(", mAutofillIdToDatasetMap=");
                        sb.append((Map) this.this$0);
                    }
                    sb.append(']');
                    return sb.toString();
                default:
                    return super.toString();
            }
        }
    }

    public final class AssistDataReceiverImpl extends IAssistDataReceiver.Stub {
        public FillRequest mLastFillRequest;
        public FillRequest mPendingFillRequest;
        public InlineSuggestionsRequest mPendingInlineSuggestionsRequest;
        public boolean mWaitForInlineRequest;

        public AssistDataReceiverImpl() {}

        public final void maybeRequestFillLocked() {
            if (this.mPendingFillRequest == null) {
                return;
            }
            Session.this.mFieldClassificationIdSnapshot = Session.sIdCounterForPcc.get();
            if (this.mWaitForInlineRequest) {
                if (this.mPendingInlineSuggestionsRequest == null) {
                    return;
                } else {
                    this.mPendingFillRequest =
                            new FillRequest(
                                    this.mPendingFillRequest.getId(),
                                    this.mPendingFillRequest.getFillContexts(),
                                    this.mPendingFillRequest.getHints(),
                                    this.mPendingFillRequest.getClientState(),
                                    this.mPendingFillRequest.getFlags(),
                                    this.mPendingInlineSuggestionsRequest,
                                    this.mPendingFillRequest.getDelayedFillIntentSender());
                }
            }
            FillRequest fillRequest = this.mPendingFillRequest;
            this.mLastFillRequest = fillRequest;
            if (!Session.this.shouldRequestSecondaryProvider(fillRequest.getFlags())
                    || Session.this.mSecondaryProviderHandler == null) {
                Session session = Session.this;
                RemoteFillService remoteFillService = session.mRemoteFillService;
                if (remoteFillService != null) {
                    if (session.mIsPrimaryCredential) {
                        FillRequest m298$$Nest$maddCredentialManagerDataToClientState =
                                Session.m298$$Nest$maddCredentialManagerDataToClientState(
                                        session,
                                        this.mPendingFillRequest,
                                        this.mPendingInlineSuggestionsRequest,
                                        session.id);
                        this.mPendingFillRequest =
                                m298$$Nest$maddCredentialManagerDataToClientState;
                        Session session2 = Session.this;
                        session2.mRemoteFillService.onFillCredentialRequest(
                                m298$$Nest$maddCredentialManagerDataToClientState,
                                session2.mClient.asBinder());
                    } else {
                        remoteFillService.onFillRequest(this.mPendingFillRequest);
                    }
                }
            } else {
                Slog.v("AutofillSession", "Requesting fill response to secondary provider.");
                Session session3 = Session.this;
                if (!session3.mIsPrimaryCredential) {
                    this.mPendingFillRequest =
                            Session.m298$$Nest$maddCredentialManagerDataToClientState(
                                    session3,
                                    this.mPendingFillRequest,
                                    this.mPendingInlineSuggestionsRequest,
                                    session3.id);
                }
                SecondaryProviderHandler secondaryProviderHandler =
                        Session.this.mSecondaryProviderHandler;
                FillRequest fillRequest2 = this.mPendingFillRequest;
                int flags = fillRequest2.getFlags();
                IBinder asBinder = Session.this.mClient.asBinder();
                secondaryProviderHandler.getClass();
                Slog.v(
                        "SecondaryProviderHandler",
                        "Requesting fill response to secondary provider.");
                secondaryProviderHandler.mLastFlag = flags;
                RemoteFillService remoteFillService2 = secondaryProviderHandler.mRemoteFillService;
                if (remoteFillService2 == null
                        || !remoteFillService2.isCredentialAutofillService()) {
                    secondaryProviderHandler.mRemoteFillService.onFillRequest(fillRequest2);
                } else {
                    Slog.v(
                            "SecondaryProviderHandler",
                            "About to call CredAutofill service as secondary provider");
                    secondaryProviderHandler.mRemoteFillService.onFillCredentialRequest(
                            fillRequest2, asBinder);
                }
            }
            this.mPendingInlineSuggestionsRequest = null;
            this.mWaitForInlineRequest = false;
            this.mPendingFillRequest = null;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Session session4 = Session.this;
            int i = (int) (elapsedRealtime - session4.mLatencyBaseTime);
            session4.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                    new PresentationStatsEventLogger$$ExternalSyntheticLambda1(i, 8));
            Session.this.mFillRequestEventLogger.mEventInternal.ifPresent(
                    new FillRequestEventLogger$$ExternalSyntheticLambda0(i, 5));
            Session.this.mFillRequestEventLogger.logAndEndEvent();
        }

        public final void onHandleAssistData(Bundle bundle) {
            AutofillManagerServiceImpl autofillManagerServiceImpl;
            ContentCaptureManagerService.LocalService localService;
            Session session = Session.this;
            if (session.mRemoteFillService == null) {
                session.wtf(
                        null,
                        "onHandleAssistData() called without a remote service."
                            + " mForAugmentedAutofillOnly: %s",
                        Boolean.valueOf(session.mSessionFlags.mAugmentedAutofillOnly));
                return;
            }
            AutofillId autofillId = session.mCurrentViewId;
            if (autofillId == null) {
                Slog.w("AutofillSession", "No current view id - session might have finished");
                return;
            }
            AssistStructure assistStructure =
                    (AssistStructure) bundle.getParcelable("structure", AssistStructure.class);
            if (assistStructure == null) {
                Slog.e(
                        "AutofillSession",
                        "No assist structure - app might have crashed providing it");
                return;
            }
            Bundle bundle2 = bundle.getBundle("receiverExtras");
            if (bundle2 == null) {
                Slog.e(
                        "AutofillSession",
                        "No receiver extras - app might have crashed providing it");
                return;
            }
            int i = bundle2.getInt("android.service.autofill.extra.REQUEST_ID");
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillSession",
                        "New structure for requestId " + i + ": " + assistStructure);
            }
            synchronized (Session.this.mLock) {
                try {
                    try {
                        assistStructure.ensureDataForAutofill();
                        ArrayList autofillIds = Helper.getAutofillIds(assistStructure, false);
                        for (int i2 = 0; i2 < autofillIds.size(); i2++) {
                            ((AutofillId) autofillIds.get(i2)).setSessionId(Session.this.id);
                        }
                        int flags = assistStructure.getFlags();
                        Session session2 = Session.this;
                        if (session2.mCompatMode) {
                            String[] urlBarResourceIdsForCompatMode =
                                    session2.mService.getUrlBarResourceIdsForCompatMode(
                                            session2.mComponentName.getPackageName());
                            if (Helper.sDebug) {
                                Slog.d(
                                        "AutofillSession",
                                        "url_bars in compat mode: "
                                                + Arrays.toString(urlBarResourceIdsForCompatMode));
                            }
                            if (urlBarResourceIdsForCompatMode != null) {
                                Session.this.mUrlBar =
                                        Helper.sanitizeUrlBar(
                                                assistStructure, urlBarResourceIdsForCompatMode);
                                AssistStructure.ViewNode viewNode = Session.this.mUrlBar;
                                if (viewNode != null) {
                                    AutofillId autofillId2 = viewNode.getAutofillId();
                                    if (Helper.sDebug) {
                                        Slog.d(
                                                "AutofillSession",
                                                "Setting urlBar as id="
                                                        + autofillId2
                                                        + " and domain "
                                                        + Session.this.mUrlBar.getWebDomain());
                                    }
                                    Session session3 = Session.this;
                                    session3.mViewStates.put(
                                            autofillId2,
                                            new ViewState(
                                                    autofillId2,
                                                    session3,
                                                    512,
                                                    session3.mIsPrimaryCredential));
                                }
                            }
                            flags |= 2;
                        }
                        int i3 = flags;
                        assistStructure.sanitizeForParceling(true);
                        Session session4 = Session.this;
                        if (session4.mContexts == null) {
                            session4.mContexts = new ArrayList(1);
                        }
                        Session.this.mContexts.add(new FillContext(i, assistStructure, autofillId));
                        Session.this.cancelCurrentRequestLocked();
                        int size = Session.this.mContexts.size();
                        for (int i4 = 0; i4 < size; i4++) {
                            Session session5 = Session.this;
                            Session.m300$$Nest$mfillContextWithAllowedValuesLocked(
                                    session5, (FillContext) session5.mContexts.get(i4), i3);
                        }
                        ArrayList mergePreviousSessionLocked =
                                Session.this.mergePreviousSessionLocked(false);
                        List m301$$Nest$mgetTypeHintsForProvider =
                                Session.m301$$Nest$mgetTypeHintsForProvider(Session.this);
                        Session session6 = Session.this;
                        session6.mDelayedFillPendingIntent =
                                Session.m299$$Nest$mcreatePendingIntent(session6, i);
                        Session session7 = Session.this;
                        Bundle bundle3 = session7.mClientState;
                        PendingIntent pendingIntent = session7.mDelayedFillPendingIntent;
                        this.mPendingFillRequest =
                                new FillRequest(
                                        i,
                                        mergePreviousSessionLocked,
                                        m301$$Nest$mgetTypeHintsForProvider,
                                        bundle3,
                                        i3,
                                        null,
                                        pendingIntent != null
                                                ? pendingIntent.getIntentSender()
                                                : null);
                        maybeRequestFillLocked();
                    } catch (RuntimeException e) {
                        Session.this.wtf(
                                e,
                                "Exception lazy loading assist structure for %s: %s",
                                assistStructure.getActivityComponent(),
                                e);
                        return;
                    }
                } finally {
                }
            }
            Session session8 = Session.this;
            IBinder iBinder = session8.mActivityToken;
            if (iBinder == null
                    || (localService =
                                    (autofillManagerServiceImpl = session8.mService)
                                            .mContentCaptureManagerInternal)
                            == null) {
                return;
            }
            int i5 = autofillManagerServiceImpl.mUserId;
            synchronized (ContentCaptureManagerService.this.mLock) {
                try {
                    if (!ContentCaptureManagerService.this.isDisabledLocked(i5)) {
                        ContentCapturePerUserService contentCapturePerUserService =
                                (ContentCapturePerUserService)
                                        ContentCaptureManagerService.this.getServiceForUserLocked(
                                                i5);
                        if (contentCapturePerUserService != null) {
                            contentCapturePerUserService.sendActivityAssistDataLocked(
                                    iBinder, bundle);
                        }
                    } else if (ContentCaptureManagerService.this.verbose) {
                        Slog.v(
                                "ContentCaptureManagerService",
                                "sendActivityAssistData() disabled!");
                    }
                } finally {
                }
            }
        }

        public final void onHandleAssistScreenshot(Bitmap bitmap) {}
    }

    public final class AugmentedAutofillErrorCallback implements Runnable {
        public WeakReference mSessionWeakRef;

        @Override // java.lang.Runnable
        public final void run() {
            Session session = (Session) this.mSessionWeakRef.get();
            if (Session.m302$$Nest$smlogIfSessionNull(session, "AugmentedAutofillErrorCallback:")) {
                return;
            }
            synchronized (session.mLock) {
                session.cancelAugmentedAutofillLocked();
                AutofillInlineSessionController autofillInlineSessionController =
                        session.mInlineSessionController;
                autofillInlineSessionController.mInlineFillUi =
                        new InlineFillUi(session.mCurrentViewId);
                autofillInlineSessionController.requestImeToShowInlineSuggestionsLocked();
            }
        }
    }

    public final class AugmentedAutofillInlineSuggestionRendererOnResultListener
            implements RemoteCallback.OnResultListener {
        public final AutofillId mFocusedId;
        public final Consumer mRequestAugmentedAutofill;
        public final WeakReference mSessionWeakRef;

        public AugmentedAutofillInlineSuggestionRendererOnResultListener(
                Session session,
                AutofillId autofillId,
                AugmentedAutofillInlineSuggestionRequestConsumer
                        augmentedAutofillInlineSuggestionRequestConsumer) {
            this.mSessionWeakRef = new WeakReference(session);
            this.mFocusedId = autofillId;
            this.mRequestAugmentedAutofill = augmentedAutofillInlineSuggestionRequestConsumer;
        }

        public final void onResult(Bundle bundle) {
            Session session = (Session) this.mSessionWeakRef.get();
            if (Session.m302$$Nest$smlogIfSessionNull(
                    session, "AugmentedAutofillInlineSuggestionRendererOnResultListener:")) {
                return;
            }
            synchronized (session.mLock) {
                session.mInlineSessionController.onCreateInlineSuggestionsRequestLocked(
                        this.mFocusedId, this.mRequestAugmentedAutofill, bundle);
            }
        }
    }

    public final class AugmentedAutofillInlineSuggestionRequestConsumer implements Consumer {
        public final AutofillValue mCurrentValue;
        public final AutofillId mFocusedId;
        public final boolean mIsAllowlisted;
        public final int mMode = 1;
        public final WeakReference mSessionWeakRef;

        public AugmentedAutofillInlineSuggestionRequestConsumer(
                Session session, AutofillId autofillId, boolean z, AutofillValue autofillValue) {
            this.mSessionWeakRef = new WeakReference(session);
            this.mFocusedId = autofillId;
            this.mIsAllowlisted = z;
            this.mCurrentValue = autofillValue;
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            InlineSuggestionsRequest inlineSuggestionsRequest = (InlineSuggestionsRequest) obj;
            Session session = (Session) this.mSessionWeakRef.get();
            if (Session.m302$$Nest$smlogIfSessionNull(
                    session, "AugmentedAutofillInlineSuggestionRequestConsumer:")) {
                return;
            }
            AutofillId autofillId = this.mFocusedId;
            boolean z = this.mIsAllowlisted;
            int i = this.mMode;
            AutofillValue autofillValue = this.mCurrentValue;
            synchronized (session.mLock) {
                RemoteAugmentedAutofillService remoteAugmentedAutofillServiceLocked =
                        session.mService.getRemoteAugmentedAutofillServiceLocked();
                session.logAugmentedAutofillRequestLocked(
                        i,
                        remoteAugmentedAutofillServiceLocked.getComponentName(),
                        autofillId,
                        z,
                        Boolean.valueOf(inlineSuggestionsRequest != null));
                int i2 = session.id;
                IAutoFillManagerClient iAutoFillManagerClient = session.mClient;
                int i3 = session.taskId;
                ComponentName componentName = session.mComponentName;
                IBinder iBinder = session.mActivityToken;
                AutofillId withoutSession = AutofillId.withoutSession(autofillId);
                AugmentedAutofillInlineSuggestionsResponseCallback
                        augmentedAutofillInlineSuggestionsResponseCallback =
                                new AugmentedAutofillInlineSuggestionsResponseCallback();
                augmentedAutofillInlineSuggestionsResponseCallback.mSessionWeakRef =
                        new WeakReference(session);
                AugmentedAutofillErrorCallback augmentedAutofillErrorCallback =
                        new AugmentedAutofillErrorCallback();
                augmentedAutofillErrorCallback.mSessionWeakRef = new WeakReference(session);
                remoteAugmentedAutofillServiceLocked.onRequestAutofillLocked(
                        i2,
                        iAutoFillManagerClient,
                        i3,
                        componentName,
                        iBinder,
                        withoutSession,
                        autofillValue,
                        inlineSuggestionsRequest,
                        augmentedAutofillInlineSuggestionsResponseCallback,
                        augmentedAutofillErrorCallback,
                        session.mService.getRemoteInlineSuggestionRenderServiceLocked(),
                        session.userId);
            }
        }
    }

    public final class AugmentedAutofillInlineSuggestionsResponseCallback implements Function {
        public WeakReference mSessionWeakRef;

        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            Boolean valueOf;
            InlineFillUi inlineFillUi = (InlineFillUi) obj;
            Session session = (Session) this.mSessionWeakRef.get();
            if (Session.m302$$Nest$smlogIfSessionNull(
                    session, "AugmentedAutofillInlineSuggestionsResponseCallback:")) {
                return Boolean.FALSE;
            }
            synchronized (session.mLock) {
                AutofillInlineSessionController autofillInlineSessionController =
                        session.mInlineSessionController;
                autofillInlineSessionController.mInlineFillUi = inlineFillUi;
                valueOf =
                        Boolean.valueOf(
                                autofillInlineSessionController
                                        .requestImeToShowInlineSuggestionsLocked());
            }
            return valueOf;
        }
    }

    public final class ClassificationState {
        public ArrayMap mClassificationCombinedHintsMap;
        public ArrayMap mClassificationGroupHintsMap;
        public ArrayMap mClassificationHintsMap;
        public ArrayMap mGroupHintsToAutofillIdMap;
        public ArrayMap mHintsToAutofillIdMap;
        public FieldClassificationResponse mLastFieldClassificationResponse;
        public FieldClassificationRequest mPendingFieldClassificationRequest;
        public int mState;

        public static void processDetections(Set set, AutofillId autofillId, ArrayMap arrayMap) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                Set arraySet = arrayMap.containsKey(str) ? (Set) arrayMap.get(str) : new ArraySet();
                arraySet.add(autofillId);
                arrayMap.put(str, arraySet);
            }
        }

        public final boolean processResponse() {
            ArrayMap arrayMap = this.mClassificationHintsMap;
            if (arrayMap != null && !arrayMap.isEmpty()) {
                return true;
            }
            FieldClassificationResponse fieldClassificationResponse =
                    this.mLastFieldClassificationResponse;
            if (fieldClassificationResponse == null) {
                return false;
            }
            this.mClassificationHintsMap = new ArrayMap();
            this.mClassificationGroupHintsMap = new ArrayMap();
            this.mHintsToAutofillIdMap = new ArrayMap();
            this.mGroupHintsToAutofillIdMap = new ArrayMap();
            this.mClassificationCombinedHintsMap = new ArrayMap();
            for (FieldClassification fieldClassification :
                    fieldClassificationResponse.getClassifications()) {
                AutofillId autofillId = fieldClassification.getAutofillId();
                Set<String> hints = fieldClassification.getHints();
                Set groupHints = fieldClassification.getGroupHints();
                ArraySet arraySet = new ArraySet(hints);
                this.mClassificationHintsMap.put(autofillId, hints);
                if (groupHints != null) {
                    this.mClassificationGroupHintsMap.put(autofillId, groupHints);
                    arraySet.addAll(groupHints);
                }
                this.mClassificationCombinedHintsMap.put(autofillId, arraySet);
                processDetections(hints, autofillId, this.mHintsToAutofillIdMap);
                processDetections(groupHints, autofillId, this.mGroupHintsToAutofillIdMap);
            }
            return true;
        }

        public final String toString() {
            String str;
            StringBuilder sb = new StringBuilder("ClassificationState: [state=");
            int i = this.mState;
            if (i == 1) {
                str = "STATE_INITIAL";
            } else if (i == 2) {
                str = "STATE_PENDING_ASSIST_REQUEST";
            } else if (i == 3) {
                str = "STATE_PENDING_REQUEST";
            } else if (i == 4) {
                str = "STATE_RESPONSE";
            } else if (i != 5) {
                str = "UNKNOWN_CLASSIFICATION_STATE_" + this.mState;
            } else {
                str = "STATE_INVALIDATED";
            }
            sb.append(str);
            sb.append(", mPendingFieldClassificationRequest=");
            sb.append(this.mPendingFieldClassificationRequest);
            sb.append(", mLastFieldClassificationResponse=");
            sb.append(this.mLastFieldClassificationResponse);
            sb.append(", mClassificationHintsMap=");
            sb.append(this.mClassificationHintsMap);
            sb.append(", mClassificationGroupHintsMap=");
            sb.append(this.mClassificationGroupHintsMap);
            sb.append(", mHintsToAutofillIdMap=");
            sb.append(this.mHintsToAutofillIdMap);
            sb.append(", mGroupHintsToAutofillIdMap=");
            sb.append(this.mGroupHintsToAutofillIdMap);
            sb.append("]");
            return sb.toString();
        }
    }

    public final class PccAssistDataReceiverImpl extends IAssistDataReceiver.Stub {
        public PccAssistDataReceiverImpl() {}

        public final void maybeRequestFieldClassificationFromServiceLocked() {
            Session session = Session.this;
            if (session.mClassificationState.mPendingFieldClassificationRequest == null) {
                Slog.w(
                        "AutofillSession",
                        "Received AssistData without pending classification request");
                return;
            }
            final RemoteFieldClassificationService remoteFieldClassificationServiceLocked =
                    session.mService.getRemoteFieldClassificationServiceLocked();
            if (remoteFieldClassificationServiceLocked != null) {
                final WeakReference weakReference = new WeakReference(Session.this);
                final FieldClassificationRequest fieldClassificationRequest =
                        Session.this.mClassificationState.mPendingFieldClassificationRequest;
                final long elapsedRealtime = SystemClock.elapsedRealtime();
                if (Helper.sVerbose) {
                    Slog.v(
                            "AutofillRemoteFieldClassificationService",
                            "onFieldClassificationRequest request:" + fieldClassificationRequest);
                }
                remoteFieldClassificationServiceLocked.run(
                        new ServiceConnector.VoidJob() { // from class:
                            // com.android.server.autofill.RemoteFieldClassificationService$$ExternalSyntheticLambda1
                            public final void runNoResult(Object obj) {
                                final RemoteFieldClassificationService
                                        remoteFieldClassificationService =
                                                RemoteFieldClassificationService.this;
                                FieldClassificationRequest fieldClassificationRequest2 =
                                        fieldClassificationRequest;
                                final WeakReference weakReference2 = weakReference;
                                final long j = elapsedRealtime;
                                int i = RemoteFieldClassificationService.$r8$clinit;
                                remoteFieldClassificationService.getClass();
                                ((IFieldClassificationService) obj)
                                        .onFieldClassificationRequest(
                                                fieldClassificationRequest2,
                                                new IFieldClassificationCallback
                                                        .Stub() { // from class:
                                                    // com.android.server.autofill.RemoteFieldClassificationService.1
                                                    public final void cancel() {}

                                                    public final boolean isCompleted() {
                                                        return false;
                                                    }

                                                    public final void onCancellable(
                                                            ICancellationSignal
                                                                    iCancellationSignal) {
                                                        if (Helper.sDebug) {
                                                            int i2 =
                                                                    RemoteFieldClassificationService
                                                                            .$r8$clinit;
                                                            Log.d(
                                                                    "AutofillRemoteFieldClassificationService",
                                                                    "onCancellable");
                                                        }
                                                        WeakReference weakReference3 =
                                                                weakReference2;
                                                        int i3 =
                                                                RemoteFieldClassificationService
                                                                        .$r8$clinit;
                                                        RemoteFieldClassificationService
                                                                .m297$$Nest$mlogFieldClassificationEvent(
                                                                        RemoteFieldClassificationService
                                                                                .this,
                                                                        j,
                                                                        (FieldClassificationServiceCallbacks)
                                                                                Helper.weakDeref(
                                                                                        weakReference3,
                                                                                        "onCancellable"
                                                                                            + " "),
                                                                        3,
                                                                        null);
                                                    }

                                                    public final void onFailure() {
                                                        if (Helper.sDebug) {
                                                            int i2 =
                                                                    RemoteFieldClassificationService
                                                                            .$r8$clinit;
                                                            Slog.d(
                                                                    "AutofillRemoteFieldClassificationService",
                                                                    "onFailure");
                                                        }
                                                        WeakReference weakReference3 =
                                                                weakReference2;
                                                        int i3 =
                                                                RemoteFieldClassificationService
                                                                        .$r8$clinit;
                                                        RemoteFieldClassificationService
                                                                .m297$$Nest$mlogFieldClassificationEvent(
                                                                        RemoteFieldClassificationService
                                                                                .this,
                                                                        j,
                                                                        (FieldClassificationServiceCallbacks)
                                                                                Helper.weakDeref(
                                                                                        weakReference3,
                                                                                        "onFailure"
                                                                                            + " "),
                                                                        2,
                                                                        null);
                                                    }

                                                    public final void onSuccess(
                                                            FieldClassificationResponse
                                                                    fieldClassificationResponse) {
                                                        String str;
                                                        if (Helper.sDebug) {
                                                            if (Build.IS_DEBUGGABLE) {
                                                                int i2 =
                                                                        RemoteFieldClassificationService
                                                                                .$r8$clinit;
                                                                Slog.d(
                                                                        "AutofillRemoteFieldClassificationService",
                                                                        "onSuccess Response: "
                                                                                + fieldClassificationResponse);
                                                            } else {
                                                                if (fieldClassificationResponse
                                                                                == null
                                                                        || fieldClassificationResponse
                                                                                        .getClassifications()
                                                                                == null) {
                                                                    str = "null response";
                                                                } else {
                                                                    str =
                                                                            "size: "
                                                                                    + fieldClassificationResponse
                                                                                            .getClassifications()
                                                                                            .size();
                                                                }
                                                                int i3 =
                                                                        RemoteFieldClassificationService
                                                                                .$r8$clinit;
                                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                "onSuccess ",
                                                                                str,
                                                                                "AutofillRemoteFieldClassificationService");
                                                            }
                                                        }
                                                        WeakReference weakReference3 =
                                                                weakReference2;
                                                        int i4 =
                                                                RemoteFieldClassificationService
                                                                        .$r8$clinit;
                                                        FieldClassificationServiceCallbacks
                                                                fieldClassificationServiceCallbacks =
                                                                        (FieldClassificationServiceCallbacks)
                                                                                Helper.weakDeref(
                                                                                        weakReference3,
                                                                                        "onSuccess"
                                                                                            + " ");
                                                        RemoteFieldClassificationService
                                                                .m297$$Nest$mlogFieldClassificationEvent(
                                                                        RemoteFieldClassificationService
                                                                                .this,
                                                                        j,
                                                                        fieldClassificationServiceCallbacks,
                                                                        1,
                                                                        fieldClassificationResponse);
                                                        if (fieldClassificationServiceCallbacks
                                                                == null) {
                                                            return;
                                                        }
                                                        Session.ClassificationState
                                                                classificationState =
                                                                        ((Session)
                                                                                        fieldClassificationServiceCallbacks)
                                                                                .mClassificationState;
                                                        classificationState.mState = 4;
                                                        classificationState
                                                                        .mLastFieldClassificationResponse =
                                                                fieldClassificationResponse;
                                                        classificationState
                                                                        .mPendingFieldClassificationRequest =
                                                                null;
                                                        classificationState.processResponse();
                                                    }
                                                });
                            }
                        });
            }
            ClassificationState classificationState = Session.this.mClassificationState;
            classificationState.mState = 3;
            classificationState.mPendingFieldClassificationRequest = null;
        }

        public final void onHandleAssistData(Bundle bundle) {
            AssistStructure assistStructure =
                    (AssistStructure) bundle.getParcelable("structure", AssistStructure.class);
            if (assistStructure == null) {
                Slog.e(
                        "AutofillSession",
                        "No assist structure for pcc detection - app might have crashed providing"
                            + " it");
                return;
            }
            Bundle bundle2 = bundle.getBundle("receiverExtras");
            if (bundle2 == null) {
                Slog.e(
                        "AutofillSession",
                        "No receiver extras for pcc detection - app might have crashed providing"
                            + " it");
                return;
            }
            int i = bundle2.getInt("android.service.autofill.extra.REQUEST_ID");
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillSession",
                        "New structure for PCC Detection: requestId " + i + ": " + assistStructure);
            }
            synchronized (Session.this.mLock) {
                try {
                    try {
                        assistStructure.ensureDataForAutofill();
                        ArrayList autofillIds = Helper.getAutofillIds(assistStructure, false);
                        for (int i2 = 0; i2 < autofillIds.size(); i2++) {
                            ((AutofillId) autofillIds.get(i2)).setSessionId(Session.this.id);
                        }
                        ClassificationState classificationState = Session.this.mClassificationState;
                        classificationState.mState = 3;
                        classificationState.mPendingFieldClassificationRequest =
                                new FieldClassificationRequest(assistStructure);
                        maybeRequestFieldClassificationFromServiceLocked();
                    } catch (RuntimeException e) {
                        Session.this.wtf(
                                e,
                                "Exception lazy loading assist structure for %s: %s",
                                assistStructure.getActivityComponent(),
                                e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onHandleAssistScreenshot(Bitmap bitmap) {}
    }

    public final class SaveResult {
        public final boolean mLogSaveShown;
        public final boolean mRemoveSession;
        public final int mSaveDialogNotShowReason;

        public SaveResult(int i, boolean z, boolean z2) {
            this.mLogSaveShown = z;
            this.mRemoveSession = z2;
            this.mSaveDialogNotShowReason = i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SaveResult: [logSaveShown=");
            sb.append(this.mLogSaveShown);
            sb.append(", removeSession=");
            sb.append(this.mRemoveSession);
            sb.append(", saveDialogNotShowReason=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(
                    this.mSaveDialogNotShowReason, sb, "]");
        }
    }

    public final class SessionFlags {
        public boolean mAugmentedAutofillOnly;
        public boolean mExpiredResponse;
        public boolean mFillDialogDisabled;
        public boolean mInlineSupportedByService;
        public boolean mScreenHasCredmanField;
        public boolean mShowingSaveUi;
    }

    /* renamed from: -$$Nest$maddCredentialManagerDataToClientState, reason: not valid java name */
    public static FillRequest m298$$Nest$maddCredentialManagerDataToClientState(
            Session session,
            FillRequest fillRequest,
            InlineSuggestionsRequest inlineSuggestionsRequest,
            int i) {
        session.getClass();
        if (fillRequest.getClientState() == null) {
            fillRequest =
                    new FillRequest(
                            fillRequest.getId(),
                            fillRequest.getFillContexts(),
                            fillRequest.getHints(),
                            new Bundle(),
                            fillRequest.getFlags(),
                            inlineSuggestionsRequest,
                            fillRequest.getDelayedFillIntentSender());
        }
        fillRequest.getClientState().putInt("autofill_session_id", i);
        fillRequest.getClientState().putInt("autofill_request_id", fillRequest.getId());
        final int id = fillRequest.getId();
        ResultReceiver resultReceiver =
                new ResultReceiver(
                        session.mHandler) { // from class: com.android.server.autofill.Session.4
                    public final AutofillId mAutofillId;

                    {
                        this.mAutofillId = Session.this.mCurrentViewId;
                    }

                    @Override // android.os.ResultReceiver
                    public final void onReceiveResult(int i2, Bundle bundle) {
                        Bundle data;
                        Dataset dataset = null;
                        if (i2 == 0) {
                            Slog.d(
                                    "AutofillSession",
                                    "onReceiveResult from Credential Manager bottom sheet with"
                                        + " mCurrentViewId: "
                                            + this.mAutofillId);
                            GetCredentialResponse getCredentialResponse =
                                    (GetCredentialResponse)
                                            bundle.getParcelable(
                                                    "android.service.credentials.extra.GET_CREDENTIAL_RESPONSE",
                                                    GetCredentialResponse.class);
                            if (Flags.autofillCredmanDevIntegration()) {
                                Session.this.sendCredentialManagerResponseToApp(
                                        null, getCredentialResponse, this.mAutofillId);
                                return;
                            }
                            Session.this.getClass();
                            if (getCredentialResponse != null
                                    && (data = getCredentialResponse.getCredential().getData())
                                            != null) {
                                dataset =
                                        (Dataset)
                                                data.getParcelable(
                                                        "android.view.autofill.extra.AUTHENTICATION_RESULT",
                                                        Dataset.class);
                            }
                            Dataset dataset2 = dataset;
                            if (dataset2 != null) {
                                Session.this.autoFill(id, -1, dataset2, false, 4);
                                return;
                            }
                            return;
                        }
                        if (i2 != -1) {
                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                    i2,
                                    "Unknown resultCode from credential manager bottom sheet: ",
                                    "AutofillSession");
                            return;
                        }
                        String[] stringArray =
                                bundle.getStringArray(
                                        "android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION");
                        if (stringArray == null || stringArray.length < 2) {
                            return;
                        }
                        String str = stringArray[0];
                        String str2 = stringArray[1];
                        Slog.w(
                                "AutofillSession",
                                "Credman bottom sheet from pinned entry failed with: + "
                                        + str
                                        + " , "
                                        + str2);
                        Session.this.sendCredentialManagerResponseToApp(
                                new GetCredentialException(str, str2), null, this.mAutofillId);
                    }
                };
        Parcel obtain = Parcel.obtain();
        resultReceiver.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        ResultReceiver resultReceiver2 =
                (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(obtain);
        obtain.recycle();
        fillRequest
                .getClientState()
                .putParcelable("android.credentials.AUTOFILL_RESULT_RECEIVER", resultReceiver2);
        return fillRequest;
    }

    /* renamed from: -$$Nest$mcreatePendingIntent, reason: not valid java name */
    public static PendingIntent m299$$Nest$mcreatePendingIntent(Session session, int i) {
        session.getClass();
        Slog.d("AutofillSession", "createPendingIntent for request " + i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return PendingIntent.getBroadcast(
                    session.mContext,
                    session.id,
                    new Intent("android.service.autofill.action.DELAYED_FILL")
                            .setPackage("android")
                            .putExtra("android.service.autofill.extra.REQUEST_ID", i),
                    1375731712);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* renamed from: -$$Nest$mfillContextWithAllowedValuesLocked, reason: not valid java name */
    public static void m300$$Nest$mfillContextWithAllowedValuesLocked(
            Session session, FillContext fillContext, int i) {
        int size = session.mViewStates.size();
        AutofillId[] autofillIdArr = new AutofillId[size];
        for (int i2 = 0; i2 < size; i2++) {
            autofillIdArr[i2] = ((ViewState) session.mViewStates.valueAt(i2)).id;
        }
        AssistStructure.ViewNode[] findViewNodesByAutofillIds =
                fillContext.findViewNodesByAutofillIds(autofillIdArr);
        int size2 = session.mViewStates.size();
        for (int i3 = 0; i3 < size2; i3++) {
            ViewState viewState = (ViewState) session.mViewStates.valueAt(i3);
            AssistStructure.ViewNode viewNode = findViewNodesByAutofillIds[i3];
            if (viewNode != null) {
                AutofillValue autofillValue = viewState.mCurrentValue;
                AutofillValue autofillValue2 = viewState.mAutofilledValue;
                AssistStructure.AutofillOverlay autofillOverlay =
                        new AssistStructure.AutofillOverlay();
                if (autofillValue2 != null && autofillValue2.equals(autofillValue)) {
                    autofillOverlay.value = autofillValue;
                }
                AutofillId autofillId = session.mCurrentViewId;
                if (autofillId != null) {
                    boolean equals = autofillId.equals(viewState.id);
                    autofillOverlay.focused = equals;
                    if (equals && (i & 1) != 0) {
                        autofillOverlay.value = autofillValue;
                    }
                }
                viewNode.setAutofillOverlay(autofillOverlay);
            } else if (Helper.sVerbose) {
                Slog.v(
                        "AutofillSession",
                        "fillContextWithAllowedValuesLocked(): no node for " + viewState.id);
            }
        }
    }

    /* renamed from: -$$Nest$mgetTypeHintsForProvider, reason: not valid java name */
    public static List m301$$Nest$mgetTypeHintsForProvider(Session session) {
        String str;
        if (!session.mService.isPccClassificationEnabled()) {
            return Collections.EMPTY_LIST;
        }
        AutofillManagerService autofillManagerService =
                (AutofillManagerService) session.mService.mMaster;
        synchronized (autofillManagerService.mFlagLock) {
            str = autofillManagerService.mPccProviderHints;
        }
        if (Helper.sVerbose) {
            Slog.v("AutofillSession", "TypeHints flag:" + str);
        }
        return TextUtils.isEmpty(str) ? new ArrayList() : List.of((Object[]) str.split(","));
    }

    /* renamed from: -$$Nest$smlogIfSessionNull, reason: not valid java name */
    public static boolean m302$$Nest$smlogIfSessionNull(Session session, String str) {
        if (session == null) {
            Slog.wtf("AutofillSession", str.concat(" Session null"));
            return true;
        }
        if (session.mDestroyed) {
            Slog.w("AutofillSession", str.concat(" Session destroyed, but following through"));
        }
        return false;
    }

    static {
        RequestId requestId = new RequestId();
        int nextInt = new Random().nextInt(999) + 2;
        if (Helper.sDebug) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    nextInt, "RequestId(): startId= ", "RequestId");
        }
        requestId.sIdCounter = new AtomicInteger(nextInt);
        mRequestId = requestId;
        sIdCounterForPcc = new AtomicInteger(2);
    }

    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.server.autofill.Session$1] */
    public Session(
            AutofillManagerServiceImpl autofillManagerServiceImpl,
            AutoFillUI autoFillUI,
            Context context,
            Handler handler,
            int i,
            Object obj,
            int i2,
            int i3,
            int i4,
            IBinder iBinder,
            IBinder iBinder2,
            boolean z,
            LocalLog localLog,
            LocalLog localLog2,
            ComponentName componentName,
            ComponentName componentName2,
            boolean z2,
            boolean z3,
            boolean z4,
            int i5,
            InputMethodManagerInternal inputMethodManagerInternal,
            boolean z5) {
        ComponentName componentName3;
        ComponentName componentName4;
        SecondaryProviderHandler secondaryProviderHandler;
        ComponentName componentName5;
        MetricsLogger metricsLogger;
        int i6;
        ComponentName componentName6;
        RemoteFillService remoteFillService;
        int displayId;
        Context context2 = context;
        MetricsLogger metricsLogger2 = new MetricsLogger();
        this.mMetricsLogger = metricsLogger2;
        this.mSessionState = 0;
        this.mViewStates = new ArrayMap();
        this.mFillRequestIdSnapshot = -2;
        this.mFieldClassificationIdSnapshot = -2;
        this.mRequestLogs = new SparseArray(1);
        this.mDisplayId = 0;
        this.mAssistReceiver = new AssistDataReceiverImpl();
        this.mPccAssistReceiver = new PccAssistDataReceiverImpl();
        ClassificationState classificationState = new ClassificationState();
        classificationState.mState = 1;
        this.mClassificationState = classificationState;
        this.mDelayedFillBroadcastReceiver =
                new BroadcastReceiver() { // from class: com.android.server.autofill.Session.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent) {
                        if (!intent.getAction()
                                .equals("android.service.autofill.action.DELAYED_FILL")) {
                            Slog.wtf("AutofillSession", "Unexpected action is received.");
                            return;
                        }
                        if (!intent.hasExtra("android.service.autofill.extra.REQUEST_ID")) {
                            Slog.e(
                                    "AutofillSession",
                                    "Delay fill action is missing request id extra.");
                            return;
                        }
                        Slog.v(
                                "AutofillSession",
                                "mDelayedFillBroadcastReceiver delayed fill action received");
                        synchronized (Session.this.mLock) {
                            int intExtra =
                                    intent.getIntExtra(
                                            "android.service.autofill.extra.REQUEST_ID", 0);
                            FillResponse fillResponse =
                                    (FillResponse)
                                            intent.getParcelableExtra(
                                                    "android.service.autofill.extra.FILL_RESPONSE",
                                                    FillResponse.class);
                            Session.this.mFillRequestEventLogger.maybeSetRequestTriggerReason(2);
                            AssistDataReceiverImpl assistDataReceiverImpl =
                                    Session.this.mAssistReceiver;
                            FillRequest fillRequest = assistDataReceiverImpl.mLastFillRequest;
                            if (fillRequest != null && intExtra == fillRequest.getId()) {
                                Slog.v(
                                        "AutofillSession",
                                        "processDelayedFillLocked: calling onFillRequestSuccess"
                                            + " with new response");
                                Session session = Session.this;
                                session.mService.getServicePackageName();
                                session.onFillRequestSuccess(
                                        intExtra,
                                        fillResponse,
                                        assistDataReceiverImpl.mLastFillRequest.getFlags());
                            }
                        }
                    }
                };
        if (i2 < 0) {
            wtf(null, "Non-positive sessionId: %s", Integer.valueOf(i2));
        }
        this.id = i2;
        this.mFlags = i5;
        this.userId = i;
        this.taskId = i3;
        this.uid = i4;
        this.mService = autofillManagerServiceImpl;
        this.mLock = obj;
        this.mUi = autoFillUI;
        this.mHandler = handler;
        String string = context.getResources().getString(R.string.date_time);
        ComponentName unflattenFromString =
                (string == null || string.isEmpty())
                        ? null
                        : ComponentName.unflattenFromString(string);
        if (unflattenFromString == null) {
            Slog.w("AutofillSession", "Invalid CredentialAutofillService");
        }
        if (!z5) {
            componentName3 = componentName;
            componentName4 = unflattenFromString;
        } else if (componentName == null || componentName.equals(unflattenFromString)) {
            componentName3 = unflattenFromString;
            componentName4 = null;
        } else {
            componentName4 = componentName;
            componentName3 = unflattenFromString;
        }
        Slog.v(
                "AutofillSession",
                "Primary service component name: "
                        + componentName3
                        + ", secondary service component name: "
                        + componentName4);
        if (componentName3 == null) {
            componentName5 = unflattenFromString;
            componentName6 = componentName4;
            metricsLogger = metricsLogger2;
            remoteFillService = null;
            i6 = 1;
            secondaryProviderHandler = null;
        } else {
            secondaryProviderHandler = null;
            componentName5 = unflattenFromString;
            metricsLogger = metricsLogger2;
            i6 = 1;
            componentName6 = componentName4;
            remoteFillService =
                    new RemoteFillService(context, componentName3, i, this, z3, componentName5);
        }
        this.mRemoteFillService = remoteFillService;
        this.mSecondaryProviderHandler =
                componentName6 != null
                        ? new SecondaryProviderHandler(
                                context,
                                i,
                                z3,
                                new Session$$ExternalSyntheticLambda0(this),
                                componentName6,
                                componentName5)
                        : secondaryProviderHandler;
        this.mActivityToken = iBinder;
        this.mHasCallback = z;
        this.mUiLatencyHistory = localLog;
        this.mWtfHistory = localLog2;
        WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked == null) {
                    throw new IllegalArgumentException(
                            "getDisplayId: No activity record matching token=" + iBinder);
                }
                displayId = forTokenLocked.getDisplayId();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (UserManager.isVisibleBackgroundUsersEnabled()) {
            if (context.getDisplayId() != displayId) {
                if (Helper.sDebug) {
                    Slogf.d(
                            "AutofillHelper",
                            "Creating context for display %d",
                            Integer.valueOf(displayId));
                }
                Display display =
                        ((DisplayManager) context2.getSystemService(DisplayManager.class))
                                .getDisplay(displayId);
                if (display == null) {
                    Slogf.wtf(
                            "AutofillHelper",
                            "Could not get context with displayId %d, Autofill operations will"
                                + " probably fail)",
                            Integer.valueOf(displayId));
                } else {
                    context2 = context2.createDisplayContext(display);
                }
            } else if (Helper.sDebug) {
                Slogf.d(
                        "AutofillHelper",
                        "getDisplayContext(): context %s already has displayId %d",
                        context2,
                        Integer.valueOf(displayId));
            }
        }
        this.mContext = context2;
        this.mComponentName = componentName2;
        this.mCompatMode = z2;
        this.mSessionState = i6;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mStartTime = elapsedRealtime;
        this.mLatencyBaseTime = elapsedRealtime;
        this.mRequestCount = 0;
        this.mPresentationStatsEventLogger =
                new PresentationStatsEventLogger(i2, i4, elapsedRealtime);
        this.mFillRequestEventLogger = new FillRequestEventLogger(i2);
        this.mFillResponseEventLogger = new FillResponseEventLogger(i2);
        SessionCommittedEventLogger sessionCommittedEventLogger =
                new SessionCommittedEventLogger(i2);
        this.mSessionCommittedEventLogger = sessionCommittedEventLogger;
        sessionCommittedEventLogger.mEventInternal.ifPresent(
                new SessionCommittedEventLogger$$ExternalSyntheticLambda0(i4, 0));
        this.mSaveEventLogger = new SaveEventLogger(i2, this.mLatencyBaseTime);
        this.mIsPrimaryCredential = z5;
        this.mIgnoreViewStateResetToEmpty =
                AutofillFeatureFlags.shouldIgnoreViewStateResetToEmpty();
        synchronized (obj) {
            SessionFlags sessionFlags = new SessionFlags();
            this.mSessionFlags = sessionFlags;
            sessionFlags.mAugmentedAutofillOnly = z4;
            AutofillServiceInfo autofillServiceInfo = autofillManagerServiceImpl.mInfo;
            sessionFlags.mInlineSupportedByService =
                    autofillServiceInfo != null
                            ? autofillServiceInfo.isInlineSuggestionsEnabled()
                            : false;
            setClientLocked(iBinder2);
        }
        this.mDisplayId = getActivityDisplayId(this.mActivityToken);
        this.mInlineSessionController =
                new AutofillInlineSessionController(
                        inputMethodManagerInternal,
                        i,
                        componentName2,
                        handler,
                        obj,
                        new AnonymousClass2());
        metricsLogger.write(newLogMaker(906).addTaggedData(1452, Integer.valueOf(i5)));
        this.mLogViewEntered = false;
    }

    public static void addFallbackDatasets(
            AnonymousClass3 anonymousClass3, AnonymousClass3 anonymousClass32) {
        for (AutofillId autofillId : (Set) anonymousClass32.val$response) {
            if (!((Set) anonymousClass3.val$response).contains(autofillId)) {
                if (((Set) ((LinkedHashMap) ((Map) anonymousClass32.this$0)).get(autofillId))
                        .isEmpty()) {
                    return;
                }
                Set<Dataset> set =
                        (Set) ((LinkedHashMap) ((Map) anonymousClass32.this$0)).get(autofillId);
                LinkedHashSet linkedHashSet = new LinkedHashSet(set);
                ((Set) anonymousClass3.val$response).add(autofillId);
                ((Map) anonymousClass3.this$0).put(autofillId, linkedHashSet);
                ((Set) anonymousClass3.val$focusedId).addAll(linkedHashSet);
                for (Dataset dataset : set) {
                    Iterator it = dataset.getFieldIds().iterator();
                    while (it.hasNext()) {
                        AutofillId autofillId2 = (AutofillId) it.next();
                        if (!autofillId2.equals(autofillId)) {
                            ((Set)
                                            ((LinkedHashMap) ((Map) anonymousClass32.this$0))
                                                    .get(autofillId2))
                                    .remove(dataset);
                        }
                    }
                }
            }
        }
    }

    public static void copyFieldsFromDataset(
            Dataset dataset,
            int i,
            AutofillId autofillId,
            ArrayList arrayList,
            ArrayList arrayList2,
            ArrayList arrayList3,
            ArrayList arrayList4,
            ArrayList arrayList5,
            ArrayList arrayList6,
            ArrayList arrayList7) {
        arrayList.add(autofillId);
        arrayList2.add((AutofillValue) dataset.getFieldValues().get(i));
        arrayList3.add(dataset.getFieldPresentation(i));
        arrayList4.add(dataset.getFieldDialogPresentation(i));
        arrayList5.add(dataset.getFieldInlinePresentation(i));
        arrayList6.add(dataset.getFieldInlineTooltipPresentation(i));
        arrayList7.add(dataset.getFilter(i));
    }

    public static void dumpNumericValue(
            PrintWriter printWriter, LogMaker logMaker, String str, int i) {
        Object taggedData = logMaker.getTaggedData(i);
        int intValue = !(taggedData instanceof Number) ? 0 : ((Number) taggedData).intValue();
        if (intValue != 0) {
            printWriter.print(", ");
            printWriter.print(str);
            printWriter.print('=');
            printWriter.print(intValue);
        }
    }

    public static int getActivityDisplayId(IBinder iBinder) {
        try {
            return ActivityClient.getInstance().getDisplayId(iBinder);
        } catch (RuntimeException e) {
            Slog.e("AutofillSession", "Error in getActivityDisplayId", e);
            return 0;
        }
    }

    public static String sessionStateAsString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                i, "UNKNOWN_SESSION_STATE_")
                                        : "STATE_REMOVED"
                                : "STATE_FINISHED"
                        : "STATE_ACTIVE"
                : "STATE_UNKNOWN";
    }

    public final void addTaggedDataToRequestLogLocked(int i, int i2, Object obj) {
        LogMaker logMaker = (LogMaker) this.mRequestLogs.get(i);
        if (logMaker == null) {
            PendingIntentController$$ExternalSyntheticOutline0.m(
                    i2,
                    i,
                    "addTaggedDataToRequestLogLocked(tag=",
                    "): no log for id ",
                    "AutofillSession");
        } else {
            logMaker.addTaggedData(i2, obj);
        }
    }

    public final void authenticate(int i, IntentSender intentSender, Bundle bundle, int i2) {
        Object obj;
        if (Helper.sDebug) {
            Slog.d(
                    "AutofillSession",
                    "authenticate(): requestId="
                            + i
                            + "; datasetIdx=65535; intentSender="
                            + intentSender);
        }
        synchronized (this.mLock) {
            try {
                this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                        new PresentationStatsEventLogger$$ExternalSyntheticLambda1(2, 1));
                if (this.mDestroyed) {
                    Slog.w(
                            "AutofillSession",
                            "Call to Session#authenticate() rejected - session: "
                                    + this.id
                                    + " destroyed");
                    return;
                }
                Intent createAuthFillInIntentLocked = createAuthFillInIntentLocked(i, bundle);
                if (createAuthFillInIntentLocked == null) {
                    forceRemoveFromServiceLocked(0);
                    return;
                }
                AutofillManagerServiceImpl autofillManagerServiceImpl = this.mService;
                int i3 = this.id;
                Bundle bundle2 = this.mClientState;
                Object obj2 = autofillManagerServiceImpl.mLock;
                synchronized (obj2) {
                    try {
                        try {
                            if (autofillManagerServiceImpl.isValidEventLocked(
                                    i3, "setAuthenticationSelected()")) {
                                obj = obj2;
                                autofillManagerServiceImpl.mEventHistory.addEvent(
                                        new FillEventHistory.Event(
                                                2, null, bundle2, null, null, null, null, null,
                                                null, null, null, 0, i2));
                            } else {
                                obj = obj2;
                            }
                            this.mHandler.sendMessage(
                                    PooledLambda.obtainMessage(
                                            new Session$$ExternalSyntheticLambda7(),
                                            this,
                                            Integer.valueOf(
                                                    AutofillManager.makeAuthenticationId(
                                                            i, GnssNative.GNSS_AIDING_TYPE_ALL)),
                                            intentSender,
                                            createAuthFillInIntentLocked,
                                            Boolean.valueOf(i2 == 2)));
                            return;
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                throw th;
            } finally {
            }
        }
    }

    public final void autoFill(int i, int i2, Dataset dataset, boolean z, int i3) {
        Intent createAuthFillInIntentLocked;
        if (Helper.sDebug) {
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "autoFill(): requestId=", "; datasetIdx=", "; dataset=");
            m.append(dataset);
            Slog.d("AutofillSession", m.toString());
        }
        synchronized (this.mLock) {
            try {
            } catch (Throwable th) {
                throw th;
            }
            if (this.mDestroyed) {
                Slog.w(
                        "AutofillSession",
                        "Call to Session#autoFill() rejected - session: " + this.id + " destroyed");
                return;
            }
            PresentationStatsEventLogger presentationStatsEventLogger =
                    this.mPresentationStatsEventLogger;
            presentationStatsEventLogger.mEventInternal.ifPresent(
                    new PresentationStatsEventLogger$$ExternalSyntheticLambda1(i2, 5));
            presentationStatsEventLogger.mEventInternal.ifPresent(
                    new PresentationStatsEventLogger$$ExternalSyntheticLambda22(
                            presentationStatsEventLogger, 0));
            PresentationStatsEventLogger presentationStatsEventLogger2 =
                    this.mPresentationStatsEventLogger;
            presentationStatsEventLogger2.mEventInternal.ifPresent(
                    new PresentationStatsEventLogger$$ExternalSyntheticLambda7(
                            dataset.getEligibleReason(), 1, presentationStatsEventLogger2));
            if (dataset.getAuthentication() == null) {
                if (z) {
                    AutofillManagerServiceImpl autofillManagerServiceImpl = this.mService;
                    String id = dataset.getId();
                    int i4 = this.id;
                    Bundle bundle = this.mClientState;
                    synchronized (autofillManagerServiceImpl.mLock) {
                        try {
                            if (autofillManagerServiceImpl.isValidEventLocked(
                                    i4, "logDatasetSelected()")) {
                                autofillManagerServiceImpl.mEventHistory.addEvent(
                                        new FillEventHistory.Event(
                                                0, id, bundle, null, null, null, null, null, null,
                                                null, null, 0, i3));
                            }
                        } finally {
                        }
                    }
                }
                AutofillId autofillId = this.mCurrentViewId;
                if (autofillId != null) {
                    this.mInlineSessionController.hideInlineSuggestionsUiLocked(autofillId);
                }
                autoFillApp(dataset);
                return;
            }
            AutofillManagerServiceImpl autofillManagerServiceImpl2 = this.mService;
            String id2 = dataset.getId();
            int i5 = this.id;
            Bundle bundle2 = this.mClientState;
            synchronized (autofillManagerServiceImpl2.mLock) {
                try {
                    if (autofillManagerServiceImpl2.isValidEventLocked(
                            i5, "logDatasetAuthenticationSelected()")) {
                        autofillManagerServiceImpl2.mEventHistory.addEvent(
                                new FillEventHistory.Event(
                                        1, id2, bundle2, null, null, null, null, null, null, null,
                                        null, 0, i3));
                    }
                } finally {
                }
            }
            this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                    new PresentationStatsEventLogger$$ExternalSyntheticLambda1(1, 1));
            setViewStatesLocked(null, dataset, 64, false, true);
            if (dataset.getCredentialFillInIntent() == null
                    || !Flags.autofillCredmanIntegration()) {
                createAuthFillInIntentLocked = createAuthFillInIntentLocked(i, this.mClientState);
            } else {
                Slog.d("AutofillSession", "Setting credential fill intent");
                createAuthFillInIntentLocked = dataset.getCredentialFillInIntent();
            }
            if (createAuthFillInIntentLocked == null) {
                forceRemoveFromServiceLocked(0);
                return;
            } else {
                startAuthentication(
                        AutofillManager.makeAuthenticationId(i, i2),
                        dataset.getAuthentication(),
                        createAuthFillInIntentLocked,
                        false);
                return;
            }
            throw th;
        }
    }

    public final void autoFillApp(Dataset dataset) {
        boolean z;
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                Slog.w(
                        "AutofillSession",
                        "Call to Session#autoFillApp() rejected - session: "
                                + this.id
                                + " destroyed");
                return;
            }
            try {
                int size = dataset.getFieldIds().size();
                ArrayList arrayList = new ArrayList(size);
                ArrayList arrayList2 = new ArrayList(size);
                boolean z2 =
                        size == 1
                                && ((AutofillId) dataset.getFieldIds().get(0))
                                        .equals(this.mCurrentViewId);
                boolean z3 = false;
                for (int i = 0; i < size; i++) {
                    if (dataset.getFieldValues().get(i) != null) {
                        AutofillId autofillId = (AutofillId) dataset.getFieldIds().get(i);
                        ViewState viewState = (ViewState) this.mViewStates.get(autofillId);
                        AutofillManagerService autofillManagerService =
                                (AutofillManagerService) this.mService.mMaster;
                        synchronized (autofillManagerService.mFlagLock) {
                            z = autofillManagerService.mIsFillFieldsFromCurrentSessionOnly;
                        }
                        if (!z || viewState == null || viewState.id.getSessionId() == this.id) {
                            arrayList.add(autofillId);
                            arrayList2.add((AutofillValue) dataset.getFieldValues().get(i));
                            if (viewState != null && (viewState.mState & 64) != 0) {
                                if (Helper.sVerbose) {
                                    Slog.v(
                                            "AutofillSession",
                                            "autofillApp(): view " + autofillId + " waiting auth");
                                }
                                viewState.resetState(64);
                                z3 = true;
                            }
                        } else if (Helper.sVerbose) {
                            Slog.v(
                                    "AutofillSession",
                                    "Skipping filling view: "
                                            + autofillId
                                            + " as it isn't part of the current session: "
                                            + this.id);
                        }
                    }
                }
                if (!arrayList.isEmpty()) {
                    if (z3) {
                        AutoFillUI autoFillUI = this.mUi;
                        autoFillUI.mHandler.post(
                                new AutoFillUI$$ExternalSyntheticLambda1(autoFillUI, this, 3));
                    }
                    if (Helper.sVerbose) {
                        Slog.v(
                                "AutofillSession",
                                "Total views to be autofilled: " + arrayList.size());
                    }
                    this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                            new PresentationStatsEventLogger$$ExternalSyntheticLambda4(
                                    2, arrayList));
                    if (Helper.sDebug) {
                        Slog.d(
                                "AutofillSession",
                                "autoFillApp(): the buck is on the app: " + dataset);
                    }
                    this.mClient.autofill(this.id, arrayList, arrayList2, z2);
                    if (dataset.getId() != null) {
                        if (this.mSelectedDatasetIds == null) {
                            this.mSelectedDatasetIds = new ArrayList();
                        }
                        this.mSelectedDatasetIds.add(dataset.getId());
                    }
                    setViewStatesLocked(null, dataset, 4, false, true);
                }
            } catch (RemoteException e) {
                Slog.w("AutofillSession", "Error autofilling activity: " + e);
            }
        }
    }

    public final void callSaveLocked() {
        if (this.mDestroyed) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Call to Session#callSaveLocked() rejected - session: "),
                    this.id,
                    " destroyed",
                    "AutofillSession");
            this.mSaveEventLogger.mEventInternal.ifPresent(
                    new SaveEventLogger$$ExternalSyntheticLambda3(false));
            this.mSaveEventLogger.logAndEndEvent();
            return;
        }
        if (this.mRemoteFillService == null) {
            wtf(
                    null,
                    "callSaveLocked() called without a remote service. mForAugmentedAutofillOnly:"
                        + " %s",
                    Boolean.valueOf(this.mSessionFlags.mAugmentedAutofillOnly));
            this.mSaveEventLogger.mEventInternal.ifPresent(
                    new SaveEventLogger$$ExternalSyntheticLambda3(false));
            this.mSaveEventLogger.logAndEndEvent();
            return;
        }
        if (Helper.sVerbose) {
            Slog.v(
                    "AutofillSession",
                    "callSaveLocked(" + this.id + "): mViewStates=" + this.mViewStates);
        }
        if (this.mContexts == null) {
            Slog.w("AutofillSession", "callSaveLocked(): no contexts");
            this.mSaveEventLogger.mEventInternal.ifPresent(
                    new SaveEventLogger$$ExternalSyntheticLambda3(false));
            this.mSaveEventLogger.logAndEndEvent();
            return;
        }
        updateValuesForSaveLocked();
        cancelCurrentRequestLocked();
        ArrayList mergePreviousSessionLocked = mergePreviousSessionLocked(true);
        FieldClassificationResponse fieldClassificationResponse =
                this.mClassificationState.mLastFieldClassificationResponse;
        if (this.mService.isPccClassificationEnabled()
                && fieldClassificationResponse != null
                && !fieldClassificationResponse.getClassifications().isEmpty()) {
            if (this.mClientState == null) {
                this.mClientState = new Bundle();
            }
            this.mClientState.putParcelableArrayList(
                    "detections",
                    new ArrayList<>(fieldClassificationResponse.getClassifications()));
        }
        final SaveRequest saveRequest =
                new SaveRequest(
                        mergePreviousSessionLocked, this.mClientState, this.mSelectedDatasetIds);
        final RemoteFillService remoteFillService = this.mRemoteFillService;
        remoteFillService.getClass();
        remoteFillService
                .postAsync(
                        new ServiceConnector
                                .Job() { // from class:
                                         // com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda9
                            public final Object run(Object obj) {
                                return RemoteFillService.$r8$lambda$0drfjd02UtjtR1pzZwZfvLOqncQ(
                                        RemoteFillService.this,
                                        saveRequest,
                                        (IAutoFillService) obj);
                            }
                        })
                .orTimeout(5000L, TimeUnit.MILLISECONDS)
                .whenComplete(
                        new BiConsumer() { // from class:
                                           // com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda10
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                final RemoteFillService remoteFillService2 = RemoteFillService.this;
                                final IntentSender intentSender = (IntentSender) obj;
                                final Throwable th = (Throwable) obj2;
                                int i = RemoteFillService.$r8$clinit;
                                remoteFillService2.getClass();
                                Handler.getMain()
                                        .post(
                                                new Runnable() { // from class:
                                                                 // com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda0
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        RemoteFillService
                                                                .$r8$lambda$1hw0FNOarmG_sgD3O92JoX9WtBE(
                                                                        RemoteFillService.this,
                                                                        th,
                                                                        intentSender);
                                                    }
                                                });
                            }
                        });
    }

    public final void cancelAugmentedAutofillLocked() {
        RemoteAugmentedAutofillService remoteAugmentedAutofillServiceLocked =
                this.mService.getRemoteAugmentedAutofillServiceLocked();
        if (remoteAugmentedAutofillServiceLocked == null) {
            Slog.w("AutofillSession", "cancelAugmentedAutofillLocked(): no service for user");
            return;
        }
        if (Helper.sVerbose) {
            Slog.v("AutofillSession", "cancelAugmentedAutofillLocked() on " + this.mCurrentViewId);
        }
        remoteAugmentedAutofillServiceLocked.run(
                new RemoteAugmentedAutofillService$$ExternalSyntheticLambda1());
    }

    public final void cancelCurrentRequestLocked() {
        ArrayList arrayList;
        RemoteFillService remoteFillService = this.mRemoteFillService;
        if (remoteFillService == null) {
            wtf(
                    null,
                    "cancelCurrentRequestLocked() called without a remote service."
                        + " mForAugmentedAutofillOnly: %s",
                    Boolean.valueOf(this.mSessionFlags.mAugmentedAutofillOnly));
            return;
        }
        int cancelCurrentRequest = remoteFillService.cancelCurrentRequest();
        if (cancelCurrentRequest == Integer.MIN_VALUE || (arrayList = this.mContexts) == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (((FillContext) this.mContexts.get(size)).getRequestId() == cancelCurrentRequest) {
                if (Helper.sDebug) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                            cancelCurrentRequest,
                            "cancelCurrentRequest(): id = ",
                            "AutofillSession");
                }
                this.mContexts.remove(size);
                return;
            }
        }
    }

    public final void cancelSave() {
        synchronized (this.mLock) {
            try {
                this.mSessionFlags.mShowingSaveUi = false;
                if (!this.mDestroyed) {
                    this.mHandler.sendMessage(
                            PooledLambda.obtainMessage(
                                    new Session$$ExternalSyntheticLambda2(1), this));
                    return;
                }
                Slog.w(
                        "AutofillSession",
                        "Call to Session#cancelSave() rejected - session: "
                                + this.id
                                + " destroyed");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Intent createAuthFillInIntentLocked(int i, Bundle bundle) {
        Intent intent = new Intent();
        FillContext fillContextByRequestIdLocked = getFillContextByRequestIdLocked(i);
        if (fillContextByRequestIdLocked == null) {
            wtf(
                    null,
                    "createAuthFillInIntentLocked(): no FillContext. requestId=%d; mContexts=%s",
                    Integer.valueOf(i),
                    this.mContexts);
            return null;
        }
        Pair pair = this.mLastInlineSuggestionsRequest;
        if (pair != null && ((Integer) pair.first).intValue() == i) {
            intent.putExtra(
                    "android.view.autofill.extra.INLINE_SUGGESTIONS_REQUEST",
                    (Parcelable) this.mLastInlineSuggestionsRequest.second);
        }
        intent.putExtra(
                "android.view.autofill.extra.ASSIST_STRUCTURE",
                fillContextByRequestIdLocked.getStructure());
        intent.putExtra("android.view.autofill.extra.CLIENT_STATE", bundle);
        return intent;
    }

    public final ViewState createOrUpdateViewStateLocked(
            AutofillId autofillId, int i, AutofillValue autofillValue) {
        ViewState viewState = (ViewState) this.mViewStates.get(autofillId);
        if (viewState != null) {
            viewState.setState(i);
        } else {
            viewState = new ViewState(autofillId, this, i, this.mIsPrimaryCredential);
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillSession",
                        "Adding autofillable view with id " + autofillId + " and state " + i);
            }
            viewState.mCurrentValue = findValueLocked(autofillId);
            this.mViewStates.put(autofillId, viewState);
        }
        if ((i & 4) != 0) {
            viewState.mAutofilledValue = autofillValue;
        }
        return viewState;
    }

    public final FillResponse createShallowCopy(
            FillResponse fillResponse, AnonymousClass3 anonymousClass3) {
        ArrayList arrayList = new ArrayList((Set) anonymousClass3.val$focusedId);
        SaveInfo saveInfo = fillResponse.getSaveInfo();
        if (saveInfo != null
                && ArrayUtils.isEmpty(saveInfo.getOptionalIds())
                && ArrayUtils.isEmpty(saveInfo.getRequiredIds())
                && (saveInfo.getFlags() & 4) == 0) {
            synchronized (this.mLock) {
                try {
                    ArrayMap arrayMap = this.mClassificationState.mHintsToAutofillIdMap;
                    if (arrayMap != null && !arrayMap.isEmpty()) {
                        ArraySet arraySet = new ArraySet();
                        int type = saveInfo.getType();
                        if (type == 0) {
                            Iterator it = arrayMap.values().iterator();
                            while (it.hasNext()) {
                                arraySet.addAll((Set) it.next());
                            }
                        } else {
                            Set hintsForSaveType = HintsHelper.getHintsForSaveType(type);
                            for (Map.Entry entry : arrayMap.entrySet()) {
                                if (((ArraySet) hintsForSaveType)
                                        .contains((String) entry.getKey())) {
                                    arraySet.addAll((Collection) entry.getValue());
                                }
                            }
                        }
                        if (!arraySet.isEmpty()) {
                            AutofillId[] autofillIdArr = new AutofillId[arraySet.size()];
                            this.mSaveEventLogger.mEventInternal.ifPresent(
                                    new SaveEventLogger$$ExternalSyntheticLambda3(1));
                            arraySet.toArray(autofillIdArr);
                            saveInfo = SaveInfo.copy(saveInfo, autofillIdArr);
                        }
                    }
                } finally {
                }
            }
        }
        return FillResponse.shallowCopy(fillResponse, arrayList, saveInfo);
    }

    public final RemoteFillService destroyLocked() {
        if (Helper.sVerbose) {
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    new StringBuilder("destroyLocked for session: "), this.id, "AutofillSession");
        }
        if (Helper.sVerbose) {
            Slog.v("AutofillSession", "logAllEvents(" + this.id + "): commitReason: 5");
        }
        this.mSessionCommittedEventLogger.mEventInternal.ifPresent(
                new SessionCommittedEventLogger$$ExternalSyntheticLambda0(5, 1));
        this.mSessionCommittedEventLogger.mEventInternal.ifPresent(
                new SessionCommittedEventLogger$$ExternalSyntheticLambda0(this.mRequestCount, 2));
        SessionCommittedEventLogger sessionCommittedEventLogger = this.mSessionCommittedEventLogger;
        final long elapsedRealtime = SystemClock.elapsedRealtime() - this.mStartTime;
        sessionCommittedEventLogger.mEventInternal.ifPresent(
                new Consumer() { // from class:
                                 // com.android.server.autofill.SessionCommittedEventLogger$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((SessionCommittedEventLogger.SessionCommittedEventInternal) obj)
                                        .mSessionDurationMillis =
                                elapsedRealtime;
                    }
                });
        this.mFillRequestEventLogger.logAndEndEvent();
        this.mFillResponseEventLogger.logAndEndEvent();
        this.mPresentationStatsEventLogger.logAndEndEvent();
        this.mSaveEventLogger.logAndEndEvent();
        SessionCommittedEventLogger sessionCommittedEventLogger2 =
                this.mSessionCommittedEventLogger;
        if (sessionCommittedEventLogger2.mEventInternal.isPresent()) {
            SessionCommittedEventLogger.SessionCommittedEventInternal
                    sessionCommittedEventInternal =
                            (SessionCommittedEventLogger.SessionCommittedEventInternal)
                                    sessionCommittedEventLogger2.mEventInternal.get();
            if (Helper.sVerbose) {
                StringBuilder sb = new StringBuilder("Log AutofillSessionCommitted: sessionId=");
                sb.append(sessionCommittedEventLogger2.mSessionId);
                sb.append(" mComponentPackageUid=");
                sb.append(sessionCommittedEventInternal.mComponentPackageUid);
                sb.append(" mRequestCount=");
                sb.append(sessionCommittedEventInternal.mRequestCount);
                sb.append(" mCommitReason=");
                sb.append(sessionCommittedEventInternal.mCommitReason);
                sb.append(" mSessionDurationMillis=");
                sb.append(sessionCommittedEventInternal.mSessionDurationMillis);
                sb.append(" mServiceUid=");
                sb.append(sessionCommittedEventInternal.mServiceUid);
                sb.append(" mSaveInfoCount=");
                sb.append(sessionCommittedEventInternal.mSaveInfoCount);
                sb.append(" mSaveDataTypeCount=");
                sb.append(sessionCommittedEventInternal.mSaveDataTypeCount);
                sb.append(" mLastFillResponseHasSaveInfo=");
                ProxyManager$$ExternalSyntheticOutline0.m(
                        "SessionCommittedEventLogger",
                        sb,
                        sessionCommittedEventInternal.mLastFillResponseHasSaveInfo);
            }
            FrameworkStatsLog.write(
                    FrameworkStatsLog.AUTOFILL_SESSION_COMMITTED,
                    sessionCommittedEventLogger2.mSessionId,
                    sessionCommittedEventInternal.mComponentPackageUid,
                    sessionCommittedEventInternal.mRequestCount,
                    sessionCommittedEventInternal.mCommitReason,
                    sessionCommittedEventInternal.mSessionDurationMillis,
                    sessionCommittedEventInternal.mServiceUid,
                    sessionCommittedEventInternal.mSaveInfoCount,
                    sessionCommittedEventInternal.mSaveDataTypeCount,
                    sessionCommittedEventInternal.mLastFillResponseHasSaveInfo);
            sessionCommittedEventLogger2.mEventInternal = Optional.empty();
        } else {
            Slog.w(
                    "SessionCommittedEventLogger",
                    "Shouldn't be logging AutofillSessionCommitted again for same session.");
        }
        if (this.mDestroyed) {
            return null;
        }
        Slog.d("AutofillSession", "clearPendingIntentLocked");
        if (this.mDelayedFillPendingIntent != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mDelayedFillPendingIntent.cancel();
                this.mDelayedFillPendingIntent = null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        unregisterDelayedFillBroadcastLocked();
        unlinkClientVultureLocked();
        AutoFillUI autoFillUI = this.mUi;
        autoFillUI.mHandler.post(
                new AutoFillUI$$ExternalSyntheticLambda3(
                        autoFillUI, this.mPendingSaveUi, this, true));
        AutoFillUI autoFillUI2 = this.mUi;
        autoFillUI2.mHandler.post(new AutoFillUI$$ExternalSyntheticLambda1(autoFillUI2, this, 4));
        AutofillId autofillId = this.mCurrentViewId;
        if (autofillId != null) {
            AutofillInlineSessionController autofillInlineSessionController =
                    this.mInlineSessionController;
            AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                    autofillInlineSessionController.mSession;
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.onInlineSuggestionsResponseLocked(
                        new InlineFillUi(autofillId));
                autofillInlineSessionController.mSession.destroySessionLocked();
                autofillInlineSessionController.mSession = null;
            }
            autofillInlineSessionController.mInlineFillUi = null;
        }
        RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderServiceLocked =
                this.mService.getRemoteInlineSuggestionRenderServiceLocked();
        if (remoteInlineSuggestionRenderServiceLocked != null) {
            remoteInlineSuggestionRenderServiceLocked.destroySuggestionViews(this.userId, this.id);
        }
        this.mDestroyed = true;
        int size = this.mRequestLogs.size();
        if (size > 0) {
            if (Helper.sVerbose) {
                Slog.v("AutofillSession", "destroyLocked(): logging " + size + " requests");
            }
            for (int i = 0; i < size; i++) {
                this.mMetricsLogger.write((LogMaker) this.mRequestLogs.valueAt(i));
            }
        }
        ArrayList arrayList = this.mAugmentedRequestsLogs;
        int size2 = arrayList == null ? 0 : arrayList.size();
        if (size2 > 0) {
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillSession",
                        "destroyLocked(): logging " + size + " augmented requests");
            }
            for (int i2 = 0; i2 < size2; i2++) {
                this.mMetricsLogger.write((LogMaker) this.mAugmentedRequestsLogs.get(i2));
            }
        }
        LogMaker addTaggedData = newLogMaker(919).addTaggedData(1455, Integer.valueOf(size));
        if (size2 > 0) {
            addTaggedData.addTaggedData(1631, Integer.valueOf(size2));
        }
        if (this.mSessionFlags.mAugmentedAutofillOnly) {
            addTaggedData.addTaggedData(1720, 1);
        }
        this.mMetricsLogger.write(addTaggedData);
        return this.mRemoteFillService;
    }

    public final void fill(int i, int i2, Dataset dataset, int i3) {
        synchronized (this.mLock) {
            try {
                if (!this.mDestroyed) {
                    this.mHandler.sendMessage(
                            PooledLambda.obtainMessage(
                                    new Session$$ExternalSyntheticLambda6(),
                                    this,
                                    Integer.valueOf(i),
                                    Integer.valueOf(i2),
                                    dataset,
                                    Boolean.TRUE,
                                    Integer.valueOf(i3)));
                    return;
                }
                Slog.w(
                        "AutofillSession",
                        "Call to Session#fill() rejected - session: " + this.id + " destroyed");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String findByAutofillId(AutofillId autofillId) {
        CharSequence[] charSequenceArr;
        synchronized (this.mLock) {
            try {
                AutofillValue findValueLocked = findValueLocked(autofillId);
                if (findValueLocked != null) {
                    if (findValueLocked.isText()) {
                        return findValueLocked.getTextValue().toString();
                    }
                    if (findValueLocked.isList()) {
                        int size = this.mContexts.size() - 1;
                        while (true) {
                            if (size >= 0) {
                                AssistStructure.ViewNode findViewNode =
                                        Helper.findViewNode(
                                                ((FillContext) this.mContexts.get(size))
                                                        .getStructure(),
                                                new Helper$$ExternalSyntheticLambda0(
                                                        0, autofillId));
                                if (findViewNode != null
                                        && findViewNode.getAutofillOptions() != null) {
                                    charSequenceArr = findViewNode.getAutofillOptions();
                                    break;
                                }
                                size--;
                            } else {
                                charSequenceArr = null;
                                break;
                            }
                        }
                        if (charSequenceArr != null) {
                            CharSequence charSequence =
                                    charSequenceArr[findValueLocked.getListValue()];
                            return charSequence != null ? charSequence.toString() : null;
                        }
                        Slog.w(
                                "AutofillSession",
                                "findByAutofillId(): no autofill options for id " + autofillId);
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final AutofillValue findRawValueByAutofillId(AutofillId autofillId) {
        AutofillValue findValueLocked;
        synchronized (this.mLock) {
            findValueLocked = findValueLocked(autofillId);
        }
        return findValueLocked;
    }

    public final AutofillValue findValueFromThisSessionOnlyLocked(AutofillId autofillId) {
        AutofillValue autofillValue;
        ViewState viewState = (ViewState) this.mViewStates.get(autofillId);
        if (viewState == null) {
            if (!Helper.sDebug) {
                return null;
            }
            Slog.d("AutofillSession", "findValueLocked(): no view state for " + autofillId);
            return null;
        }
        AutofillValue autofillValue2 = viewState.mCurrentValue;
        if ((autofillValue2 == null || autofillValue2.isEmpty())
                && (autofillValue = viewState.mCandidateSaveValue) != null
                && !autofillValue.isEmpty()) {
            if (Helper.sDebug) {
                Slog.d(
                        "AutofillSession",
                        "findValueLocked(): current value for "
                                + autofillId
                                + " is empty, using candidateSaveValue instead.");
            }
            return autofillValue;
        }
        if (autofillValue2 != null || !Helper.sDebug) {
            return autofillValue2;
        }
        Slog.d(
                "AutofillSession",
                "findValueLocked(): no current value for "
                        + autofillId
                        + ", checking value from previous fill contexts");
        return getValueFromContextsLocked(autofillId);
    }

    public final AutofillValue findValueLocked(AutofillId autofillId) {
        AutofillValue findValueFromThisSessionOnlyLocked =
                findValueFromThisSessionOnlyLocked(autofillId);
        if (findValueFromThisSessionOnlyLocked != null) {
            return getSanitizedValue(
                    Helper.createSanitizers(getSaveInfoLocked()),
                    autofillId,
                    findValueFromThisSessionOnlyLocked);
        }
        ArrayList previousSessionsLocked = this.mService.getPreviousSessionsLocked(this);
        if (previousSessionsLocked == null) {
            return null;
        }
        if (Helper.sDebug) {
            Slog.d(
                    "AutofillSession",
                    "findValueLocked(): looking on "
                            + previousSessionsLocked.size()
                            + " previous sessions for autofillId "
                            + autofillId);
        }
        for (int i = 0; i < previousSessionsLocked.size(); i++) {
            Session session = (Session) previousSessionsLocked.get(i);
            AutofillValue findValueFromThisSessionOnlyLocked2 =
                    session.findValueFromThisSessionOnlyLocked(autofillId);
            if (findValueFromThisSessionOnlyLocked2 != null) {
                return getSanitizedValue(
                        Helper.createSanitizers(session.getSaveInfoLocked()),
                        autofillId,
                        findValueFromThisSessionOnlyLocked2);
            }
        }
        return null;
    }

    public final void forceRemoveFromServiceLocked(int i) {
        if (Helper.sVerbose) {
            Slog.v("AutofillSession", "forceRemoveFromServiceLocked(): " + this.mPendingSaveUi);
        }
        PendingUi pendingUi = this.mPendingSaveUi;
        boolean z = false;
        boolean z2 = pendingUi != null && pendingUi.mState == 2;
        this.mPendingSaveUi = null;
        removeFromServiceLocked();
        AutoFillUI autoFillUI = this.mUi;
        autoFillUI.mHandler.post(
                new AutoFillUI$$ExternalSyntheticLambda3(autoFillUI, this.mPendingSaveUi, this, z));
        if (!z2) {
            try {
                this.mClient.setSessionFinished(i, (List) null);
            } catch (RemoteException e) {
                Slog.e("AutofillSession", "Error notifying client to finish session", e);
            }
        }
        Runnable runnable = this.mAugmentedAutofillDestroyer;
        if (runnable != null) {
            runnable.run();
            this.mAugmentedAutofillDestroyer = null;
        }
    }

    public final int getAutofillServiceUid() {
        ServiceInfo serviceInfo = this.mService.mServiceInfo;
        if (serviceInfo == null) {
            return -1;
        }
        return serviceInfo.applicationInfo.uid;
    }

    public final IAutoFillManagerClient getClient() {
        IAutoFillManagerClient iAutoFillManagerClient;
        synchronized (this.mLock) {
            iAutoFillManagerClient = this.mClient;
        }
        return iAutoFillManagerClient;
    }

    public final int getDetectionPreferenceForLogging() {
        boolean z;
        if (!this.mService.isPccClassificationEnabled()) {
            return 0;
        }
        AutofillManagerService autofillManagerService =
                (AutofillManagerService) this.mService.mMaster;
        synchronized (autofillManagerService.mFlagLock) {
            z = autofillManagerService.mPccPreferProviderOverPcc;
        }
        return z ? 1 : 2;
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x0147  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.service.autofill.FillResponse getEffectiveFillResponse(
            android.service.autofill.FillResponse r47) {
        /*
            Method dump skipped, instructions count: 1031
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.Session.getEffectiveFillResponse(android.service.autofill.FillResponse):android.service.autofill.FillResponse");
    }

    public final FillContext getFillContextByRequestIdLocked(int i) {
        ArrayList arrayList = this.mContexts;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            FillContext fillContext = (FillContext) this.mContexts.get(i2);
            if (fillContext.getRequestId() == i) {
                return fillContext;
            }
        }
        return null;
    }

    public final FillResponse getLastResponseLocked(String str) {
        String format =
                (!Helper.sDebug || str == null)
                        ? null
                        : String.format(str, Integer.valueOf(this.id));
        if (this.mContexts == null) {
            if (format != null) {
                Slog.d("AutofillSession", format.concat(": no contexts"));
            }
            return null;
        }
        SparseArray sparseArray = this.mResponses;
        if (sparseArray == null) {
            if (Helper.sVerbose && format != null) {
                Slog.v("AutofillSession", format.concat(": no responses on session"));
            }
            return null;
        }
        int i = -1;
        if (sparseArray != null && sparseArray.size() != 0) {
            ArrayList arrayList = new ArrayList();
            int size = this.mResponses.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(Integer.valueOf(this.mResponses.keyAt(i2)));
            }
            if (arrayList.size() == 1) {
                i = 0;
            } else {
                int i3 = 0;
                while (true) {
                    if (i3 >= arrayList.size() - 1) {
                        break;
                    }
                    int i4 = i3 + 1;
                    if (((Integer) arrayList.get(i4)).intValue()
                                    - ((Integer) arrayList.get(i3)).intValue()
                            > 5000) {
                        i = i3;
                        z = true;
                        break;
                    }
                    i3 = i4;
                }
                if (!z) {
                    i = arrayList.size() - 1;
                }
                if (Helper.sDebug) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                            i, "getLastRequestIdIndex(): latestRequestIdIndex = ", "RequestId");
                }
            }
        }
        if (i < 0) {
            if (format != null) {
                StringBuilder m =
                        Preconditions$$ExternalSyntheticOutline0.m(
                                format, ": did not get last response. mResponses=");
                m.append(this.mResponses);
                m.append(", mViewStates=");
                m.append(this.mViewStates);
                Slog.w("AutofillSession", m.toString());
            }
            return null;
        }
        FillResponse fillResponse = (FillResponse) this.mResponses.valueAt(i);
        if (Helper.sVerbose && format != null) {
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(format, ": mResponses=");
            m2.append(this.mResponses);
            m2.append(", mContexts=");
            m2.append(this.mContexts);
            m2.append(", mViewStates=");
            m2.append(this.mViewStates);
            Slog.v("AutofillSession", m2.toString());
        }
        return fillResponse;
    }

    public final AutofillValue getSanitizedValue(
            ArrayMap arrayMap, AutofillId autofillId, AutofillValue autofillValue) {
        if (arrayMap == null || autofillValue == null) {
            return autofillValue;
        }
        ViewState viewState = (ViewState) this.mViewStates.get(autofillId);
        AutofillValue autofillValue2 = viewState == null ? null : viewState.mSanitizedValue;
        if (autofillValue2 == null) {
            InternalSanitizer internalSanitizer = (InternalSanitizer) arrayMap.get(autofillId);
            if (internalSanitizer == null) {
                return autofillValue;
            }
            autofillValue2 = internalSanitizer.sanitize(autofillValue);
            if (Helper.sDebug) {
                Slog.d(
                        "AutofillSession",
                        "Value for "
                                + autofillId
                                + "("
                                + autofillValue
                                + ") sanitized to "
                                + autofillValue2);
            }
            if (viewState != null) {
                viewState.mSanitizedValue = autofillValue2;
            }
        }
        return autofillValue2;
    }

    public final SaveInfo getSaveInfoLocked() {
        FillResponse lastResponseLocked = getLastResponseLocked(null);
        if (lastResponseLocked == null) {
            return null;
        }
        return lastResponseLocked.getSaveInfo();
    }

    public final Drawable getServiceIcon(FillResponse fillResponse) {
        int iconResourceId = fillResponse.getIconResourceId();
        Drawable drawable =
                iconResourceId != 0
                        ? ((AutofillManagerService) this.mService.mMaster)
                                .getContext()
                                .getPackageManager()
                                .getDrawable(
                                        this.mService.getServicePackageName(), iconResourceId, null)
                        : null;
        if (drawable != null) {
            return drawable;
        }
        AutofillManagerServiceImpl autofillManagerServiceImpl = this.mService;
        ServiceInfo serviceInfo = autofillManagerServiceImpl.mServiceInfo;
        return serviceInfo != null
                ? serviceInfo.loadIcon(
                        autofillManagerServiceImpl.mMaster.getContext().getPackageManager())
                : null;
    }

    public final AutoFillUI getUiForShowing() {
        AutoFillUI autoFillUI;
        synchronized (this.mLock) {
            AutoFillUI autoFillUI2 = this.mUi;
            autoFillUI2.mHandler.post(
                    new AutoFillUI$$ExternalSyntheticLambda1(autoFillUI2, this, 0));
            autoFillUI = this.mUi;
        }
        return autoFillUI;
    }

    public final AutofillValue getValueFromContextsLocked(AutofillId autofillId) {
        for (int size = this.mContexts.size() - 1; size >= 0; size--) {
            AssistStructure.ViewNode findViewNode =
                    Helper.findViewNode(
                            ((FillContext) this.mContexts.get(size)).getStructure(),
                            new Helper$$ExternalSyntheticLambda0(0, autofillId));
            if (findViewNode != null) {
                AutofillValue autofillValue = findViewNode.getAutofillValue();
                if (Helper.sDebug) {
                    Slog.d(
                            "AutofillSession",
                            "getValueFromContexts("
                                    + this.id
                                    + "/"
                                    + autofillId
                                    + ") at "
                                    + size
                                    + ": "
                                    + autofillValue);
                }
                if (autofillValue != null && !autofillValue.isEmpty()) {
                    return autofillValue;
                }
            }
        }
        return null;
    }

    public final boolean isEmptyResponse(FillResponse fillResponse) {
        boolean z = true;
        if (fillResponse == null) {
            return true;
        }
        SaveInfo saveInfo = fillResponse.getSaveInfo();
        synchronized (this.mLock) {
            try {
                if (fillResponse.getDatasets() != null) {
                    if (fillResponse.getDatasets().isEmpty()) {}
                    z = false;
                }
                if (fillResponse.getAuthentication() == null
                        && ((saveInfo == null
                                        || (ArrayUtils.isEmpty(saveInfo.getOptionalIds())
                                                && ArrayUtils.isEmpty(saveInfo.getRequiredIds())
                                                && (saveInfo.getFlags() & 4) == 0))
                                && ArrayUtils.isEmpty(fillResponse.getFieldClassificationIds()))) {}
                z = false;
            } finally {
            }
        }
        return z;
    }

    public final void logAugmentedAutofillRequestLocked(
            int i, ComponentName componentName, AutofillId autofillId, boolean z, Boolean bool) {
        StringBuilder sb = new StringBuilder("aug:id=");
        sb.append(this.id);
        sb.append(" u=");
        ServiceKeeper$$ExternalSyntheticOutline0.m(this.uid, i, " m=", " a=", sb);
        sb.append(ComponentName.flattenToShortString(this.mComponentName));
        sb.append(" f=");
        sb.append(autofillId);
        sb.append(" s=");
        sb.append(componentName);
        sb.append(" w=");
        sb.append(z);
        sb.append(" i=");
        sb.append(bool);
        ((AutofillManagerService) this.mService.mMaster).mRequestsHistory.log(sb.toString());
    }

    public final void logContextCommittedLocked(
            ArrayList arrayList, ArrayList arrayList2, int i, int i2) {
        String str;
        ArrayList arrayList3;
        ArrayList arrayList4;
        ArrayList arrayList5;
        ArrayList arrayList6;
        ArrayList arrayList7;
        ArrayList arrayList8;
        AutofillId[] autofillIdArr;
        android.service.autofill.FieldClassification[] fieldClassificationArr;
        int i3;
        boolean z;
        String str2;
        AutofillValue autofillValue;
        int i4;
        ArrayList arrayList9;
        boolean z2;
        String str3;
        String str4;
        List list;
        AutofillValue autofillValue2;
        ArrayList arrayList10;
        AutofillValue autofillValue3;
        ArrayList arrayList11;
        if (Helper.sVerbose) {
            StringBuilder sb = new StringBuilder("logContextCommittedLocked (");
            ServiceKeeper$$ExternalSyntheticOutline0.m(
                    this.id, i2, "): commit_reason:", " no_save_reason:", sb);
            GmsAlarmManager$$ExternalSyntheticOutline0.m(sb, i, "AutofillSession");
        }
        FillResponse lastResponseLocked = getLastResponseLocked("logContextCommited(%s)");
        if (lastResponseLocked == null) {
            return;
        }
        this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(
                i2 != 1 ? i2 != 2 ? i2 != 4 ? 0 : 3 : 6 : 4);
        this.mPresentationStatsEventLogger.logAndEndEvent();
        int flags = lastResponseLocked.getFlags();
        if ((flags & 1) == 0) {
            if (Helper.sVerbose) {
                ProxyManager$$ExternalSyntheticOutline0.m(
                        flags, "logContextCommittedLocked(): ignored by flags ", "AutofillSession");
                return;
            }
            return;
        }
        int size = this.mResponses.size();
        int i5 = 0;
        ArraySet arraySet = null;
        boolean z3 = false;
        while (true) {
            str = "logContextCommitted() skipping idless dataset ";
            if (i5 >= size) {
                break;
            }
            List datasets = ((FillResponse) this.mResponses.valueAt(i5)).getDatasets();
            if (datasets != null && !datasets.isEmpty()) {
                for (int i6 = 0; i6 < datasets.size(); i6++) {
                    Dataset dataset = (Dataset) datasets.get(i6);
                    String id = dataset.getId();
                    if (id != null) {
                        ArrayList arrayList12 = this.mSelectedDatasetIds;
                        if (arrayList12 == null || !arrayList12.contains(id)) {
                            if (Helper.sVerbose) {
                                Slog.v("AutofillSession", "adding ignored dataset ".concat(id));
                            }
                            if (arraySet == null) {
                                arraySet = new ArraySet();
                            }
                            arraySet.add(id);
                        }
                        z3 = true;
                    } else if (Helper.sVerbose) {
                        Slog.v(
                                "AutofillSession",
                                "logContextCommitted() skipping idless dataset " + dataset);
                    }
                }
            } else if (Helper.sVerbose) {
                ProxyManager$$ExternalSyntheticOutline0.m(
                        i5, "logContextCommitted() no datasets at ", "AutofillSession");
            }
            i5++;
        }
        ArraySet arraySet2 = arraySet;
        int i7 = 0;
        ArrayList arrayList13 = null;
        ArrayList arrayList14 = null;
        ArrayMap arrayMap = null;
        while (i7 < this.mViewStates.size()) {
            ViewState viewState = (ViewState) this.mViewStates.valueAt(i7);
            int i8 = viewState.mState;
            if ((i8 & 8) != 0) {
                if ((i8 & 2048) != 0) {
                    String str5 = viewState.mDatasetId;
                    if (str5 == null) {
                        Slog.w(
                                "AutofillSession",
                                "logContextCommitted(): no dataset id on " + viewState);
                    } else {
                        AutofillValue autofillValue4 = viewState.mAutofilledValue;
                        AutofillValue autofillValue5 = viewState.mCurrentValue;
                        if (autofillValue4 == null || !autofillValue4.equals(autofillValue5)) {
                            if (Helper.sDebug) {
                                Slog.d(
                                        "AutofillSession",
                                        "logContextCommitted() found changed state: " + viewState);
                            }
                            if (arrayList13 == null) {
                                arrayList13 = new ArrayList();
                                arrayList14 = new ArrayList();
                            }
                            arrayList13.add(viewState.id);
                            arrayList14.add(str5);
                            i3 = size;
                            z = z3;
                            str2 = str;
                        } else if (Helper.sDebug) {
                            Slog.d(
                                    "AutofillSession",
                                    "logContextCommitted(): ignoring changed "
                                            + viewState
                                            + " because it has same value that was autofilled");
                        }
                    }
                } else {
                    AutofillValue autofillValue6 = viewState.mCurrentValue;
                    if (autofillValue6 == null) {
                        if (Helper.sDebug) {
                            Slog.d(
                                    "AutofillSession",
                                    "logContextCommitted(): skipping view without current value ( "
                                            + viewState
                                            + ")");
                        }
                    } else if (z3) {
                        int i9 = 0;
                        while (i9 < size) {
                            List datasets2 =
                                    ((FillResponse) this.mResponses.valueAt(i9)).getDatasets();
                            if (datasets2 == null || datasets2.isEmpty()) {
                                autofillValue = autofillValue6;
                                i4 = size;
                                arrayList9 = arrayList14;
                                z2 = z3;
                                str3 = str;
                                if (Helper.sVerbose) {
                                    ProxyManager$$ExternalSyntheticOutline0.m(
                                            i9,
                                            "logContextCommitted() no datasets at ",
                                            "AutofillSession");
                                }
                            } else {
                                i4 = size;
                                ArrayMap arrayMap2 = arrayMap;
                                int i10 = 0;
                                while (i10 < datasets2.size()) {
                                    Dataset dataset2 = (Dataset) datasets2.get(i10);
                                    boolean z4 = z3;
                                    String id2 = dataset2.getId();
                                    if (id2 == null) {
                                        if (Helper.sVerbose) {
                                            Slog.v("AutofillSession", str + dataset2);
                                        }
                                        autofillValue2 = autofillValue6;
                                        arrayList10 = arrayList14;
                                        str4 = str;
                                        list = datasets2;
                                    } else {
                                        ArrayList fieldValues = dataset2.getFieldValues();
                                        str4 = str;
                                        list = datasets2;
                                        int i11 = 0;
                                        while (i11 < fieldValues.size()) {
                                            if (autofillValue6.equals(
                                                    (AutofillValue) fieldValues.get(i11))) {
                                                if (Helper.sDebug) {
                                                    autofillValue3 = autofillValue6;
                                                    Slog.d(
                                                            "AutofillSession",
                                                            "field "
                                                                    + viewState.id
                                                                    + " was manually filled with"
                                                                    + " value set by dataset "
                                                                    + id2);
                                                } else {
                                                    autofillValue3 = autofillValue6;
                                                }
                                                if (arrayMap2 == null) {
                                                    arrayMap2 = new ArrayMap();
                                                }
                                                ArrayMap arrayMap3 = arrayMap2;
                                                ArraySet arraySet3 =
                                                        (ArraySet) arrayMap3.get(viewState.id);
                                                if (arraySet3 == null) {
                                                    arrayList11 = arrayList14;
                                                    arraySet3 = new ArraySet(1);
                                                    arrayMap3.put(viewState.id, arraySet3);
                                                } else {
                                                    arrayList11 = arrayList14;
                                                }
                                                arraySet3.add(id2);
                                                arrayMap2 = arrayMap3;
                                            } else {
                                                autofillValue3 = autofillValue6;
                                                arrayList11 = arrayList14;
                                            }
                                            i11++;
                                            autofillValue6 = autofillValue3;
                                            arrayList14 = arrayList11;
                                        }
                                        autofillValue2 = autofillValue6;
                                        arrayList10 = arrayList14;
                                        ArrayList arrayList15 = this.mSelectedDatasetIds;
                                        if (arrayList15 == null || !arrayList15.contains(id2)) {
                                            if (Helper.sVerbose) {
                                                Slog.v(
                                                        "AutofillSession",
                                                        "adding ignored dataset ".concat(id2));
                                            }
                                            if (arraySet2 == null) {
                                                arraySet2 = new ArraySet();
                                            }
                                            arraySet2.add(id2);
                                        }
                                    }
                                    i10++;
                                    z3 = z4;
                                    str = str4;
                                    datasets2 = list;
                                    autofillValue6 = autofillValue2;
                                    arrayList14 = arrayList10;
                                }
                                autofillValue = autofillValue6;
                                arrayList9 = arrayList14;
                                z2 = z3;
                                str3 = str;
                                arrayMap = arrayMap2;
                            }
                            i9++;
                            size = i4;
                            z3 = z2;
                            str = str3;
                            autofillValue6 = autofillValue;
                            arrayList14 = arrayList9;
                        }
                        i3 = size;
                        z = z3;
                        str2 = str;
                    }
                }
                i7++;
                size = i3;
                z3 = z;
                str = str2;
            }
            i3 = size;
            z = z3;
            str2 = str;
            arrayList14 = arrayList14;
            i7++;
            size = i3;
            z3 = z;
            str = str2;
        }
        ArrayList arrayList16 = arrayList14;
        if (arrayMap != null) {
            int size2 = arrayMap.size();
            ArrayList arrayList17 = new ArrayList(size2);
            ArrayList arrayList18 = new ArrayList(size2);
            for (int i12 = 0; i12 < size2; i12++) {
                AutofillId autofillId = (AutofillId) arrayMap.keyAt(i12);
                ArraySet arraySet4 = (ArraySet) arrayMap.valueAt(i12);
                arrayList17.add(autofillId);
                arrayList18.add(new ArrayList(arraySet4));
            }
            arrayList3 = arrayList17;
            arrayList4 = arrayList18;
        } else {
            arrayList3 = null;
            arrayList4 = null;
        }
        AutofillManagerServiceImpl autofillManagerServiceImpl = this.mService;
        int i13 = this.id;
        Bundle bundle = this.mClientState;
        ArrayList arrayList19 = this.mSelectedDatasetIds;
        ComponentName componentName = this.mComponentName;
        boolean z5 = this.mCompatMode;
        if (autofillManagerServiceImpl.isValidEventLocked(i13, "logDatasetNotSelected()")) {
            if (Helper.sVerbose) {
                StringBuilder sb2 =
                        new StringBuilder("logContextCommitted() with FieldClassification: id=");
                sb2.append(i13);
                sb2.append(", selectedDatasets=");
                sb2.append(arrayList19);
                sb2.append(", ignoredDatasetIds=");
                sb2.append(arraySet2);
                sb2.append(", changedAutofillIds=");
                sb2.append(arrayList13);
                sb2.append(", changedDatasetIds=");
                arrayList7 = arrayList16;
                sb2.append(arrayList7);
                sb2.append(", manuallyFilledFieldIds=");
                sb2.append(arrayList3);
                sb2.append(", detectedFieldIds=");
                arrayList5 = arrayList;
                sb2.append(arrayList5);
                sb2.append(", detectedFieldClassifications=");
                arrayList6 = arrayList2;
                sb2.append(arrayList6);
                sb2.append(", appComponentName=");
                sb2.append(componentName.toShortString());
                sb2.append(", compatMode=");
                sb2.append(z5);
                sb2.append(", saveDialogNotShowReason=");
                GmsAlarmManager$$ExternalSyntheticOutline0.m(sb2, i, "AutofillManagerServiceImpl");
            } else {
                arrayList5 = arrayList;
                arrayList6 = arrayList2;
                arrayList7 = arrayList16;
            }
            if (arrayList5 != null) {
                int size3 = arrayList.size();
                AutofillId[] autofillIdArr2 = new AutofillId[size3];
                arrayList5.toArray(autofillIdArr2);
                android.service.autofill.FieldClassification[] fieldClassificationArr2 =
                        new android.service.autofill.FieldClassification[arrayList2.size()];
                arrayList6.toArray(fieldClassificationArr2);
                float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
                int i14 = 0;
                int i15 = 0;
                while (i14 < size3) {
                    android.service.autofill.FieldClassification[] fieldClassificationArr3 =
                            fieldClassificationArr2;
                    List<FieldClassification.Match> matches =
                            fieldClassificationArr2[i14].getMatches();
                    ArrayList arrayList20 = arrayList4;
                    int size4 = matches.size();
                    int i16 = i15 + size4;
                    for (int i17 = 0; i17 < size4; i17++) {
                        f = matches.get(i17).getScore() + f;
                    }
                    i14++;
                    i15 = i16;
                    arrayList4 = arrayList20;
                    fieldClassificationArr2 = fieldClassificationArr3;
                }
                arrayList8 = arrayList4;
                autofillManagerServiceImpl.mMetricsLogger.write(
                        Helper.newLogMaker(
                                        1273,
                                        componentName,
                                        autofillManagerServiceImpl.getServicePackageName(),
                                        i13,
                                        z5)
                                .setCounterValue(size3)
                                .addTaggedData(1274, Integer.valueOf((int) ((f * 100.0f) / i15))));
                autofillIdArr = autofillIdArr2;
                fieldClassificationArr = fieldClassificationArr2;
            } else {
                arrayList8 = arrayList4;
                autofillIdArr = null;
                fieldClassificationArr = null;
            }
            autofillManagerServiceImpl.mEventHistory.addEvent(
                    new FillEventHistory.Event(
                            4,
                            null,
                            bundle,
                            arrayList19,
                            arraySet2,
                            arrayList13,
                            arrayList7,
                            arrayList3,
                            arrayList8,
                            autofillIdArr,
                            fieldClassificationArr,
                            i));
        }
        this.mSessionCommittedEventLogger.mEventInternal.ifPresent(
                new SessionCommittedEventLogger$$ExternalSyntheticLambda0(i2, 1));
        this.mSessionCommittedEventLogger.mEventInternal.ifPresent(
                new SessionCommittedEventLogger$$ExternalSyntheticLambda0(this.mRequestCount, 2));
        this.mSaveEventLogger.maybeSetSaveUiNotShownReason(i);
    }

    public final void logPresentationStatsOnViewEnteredLocked(
            FillResponse fillResponse, boolean z) {
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda1(
                        fillResponse.getRequestId(), 6));
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda0(2, z));
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda1(
                        this.mFieldClassificationIdSnapshot, 0));
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda11(
                        fillResponse.getDatasets(), this.mCurrentViewId));
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda4(0, this.mCurrentViewId));
    }

    public final ArrayList mergePreviousSessionLocked(boolean z) {
        ArrayList previousSessionsLocked = this.mService.getPreviousSessionsLocked(this);
        if (previousSessionsLocked == null) {
            return new ArrayList(this.mContexts);
        }
        if (Helper.sDebug) {
            StringBuilder sb = new StringBuilder("mergeSessions(");
            sb.append(this.id);
            sb.append("): Merging the content of ");
            sb.append(previousSessionsLocked.size());
            sb.append(" sessions for task ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, this.taskId, "AutofillSession");
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < previousSessionsLocked.size(); i++) {
            Session session = (Session) previousSessionsLocked.get(i);
            ArrayList arrayList2 = session.mContexts;
            if (arrayList2 == null) {
                StringBuilder sb2 = new StringBuilder("mergeSessions(");
                sb2.append(this.id);
                sb2.append("): Not merging null contexts from ");
                HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb2, session.id, "AutofillSession");
            } else {
                if (z) {
                    session.updateValuesForSaveLocked();
                }
                if (Helper.sDebug) {
                    StringBuilder sb3 = new StringBuilder("mergeSessions(");
                    sb3.append(this.id);
                    sb3.append("): adding ");
                    sb3.append(arrayList2.size());
                    sb3.append(" context from previous session #");
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            sb3, session.id, "AutofillSession");
                }
                arrayList.addAll(arrayList2);
                if (this.mClientState == null && session.mClientState != null) {
                    if (Helper.sDebug) {
                        StringBuilder sb4 = new StringBuilder("mergeSessions(");
                        sb4.append(this.id);
                        sb4.append("): setting client state from previous session");
                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                sb4, session.id, "AutofillSession");
                    }
                    this.mClientState = session.mClientState;
                }
            }
        }
        arrayList.addAll(this.mContexts);
        return arrayList;
    }

    public final LogMaker newLogMaker(int i) {
        return Helper.newLogMaker(
                i,
                this.mComponentName,
                this.mService.getServicePackageName(),
                this.id,
                this.mCompatMode);
    }

    public final void notifyClientFillDialogTriggerIds(List list) {
        try {
            if (Helper.sVerbose) {
                Slog.v("AutofillSession", "notifyFillDialogTriggerIds(): " + list);
            }
            getClient().notifyFillDialogTriggerIds(list);
        } catch (RemoteException e) {
            Slog.w("AutofillSession", "Cannot set trigger ids for fill dialog", e);
        }
    }

    public final void notifyDisableAutofillToClient(ComponentName componentName, long j) {
        synchronized (this.mLock) {
            if (this.mCurrentViewId == null) {
                return;
            }
            try {
                this.mClient.notifyDisableAutofill(j, componentName);
            } catch (RemoteException e) {
                Slog.e(
                        "AutofillSession",
                        "Error notifying client disable autofill: id=" + this.mCurrentViewId,
                        e);
            }
        }
    }

    public final void notifyUnavailableToClient(ArrayList arrayList, int i) {
        synchronized (this.mLock) {
            AutofillId autofillId = this.mCurrentViewId;
            if (autofillId == null) {
                return;
            }
            try {
                if (this.mHasCallback) {
                    this.mClient.notifyNoFillUi(this.id, autofillId, i);
                } else if (i != 0) {
                    this.mClient.setSessionFinished(i, arrayList);
                }
            } catch (RemoteException e) {
                Slog.e(
                        "AutofillSession",
                        "Error notifying client no fill UI: id=" + this.mCurrentViewId,
                        e);
            }
        }
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public final void onFillRequestFailure(int i, Throwable th) {
        String message = th.getMessage();
        boolean z = th instanceof TimeoutException;
        boolean z2 = !TextUtils.isEmpty(message);
        synchronized (this.mLock) {
            try {
                this.mFillResponseEventLogger.startLogForNewResponse();
                this.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(i, 5));
                this.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(this.uid, 2));
                this.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(-1, 1));
                this.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(-1, 6));
                this.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(
                                getDetectionPreferenceForLogging(), 4));
                this.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(
                                (int) (SystemClock.elapsedRealtime() - this.mLatencyBaseTime), 7));
                unregisterDelayedFillBroadcastLocked();
                if (this.mDestroyed) {
                    Slog.w(
                            "AutofillSession",
                            "Call to Session#onFillRequestFailureOrTimeout(req="
                                    + i
                                    + ") rejected - session: "
                                    + this.id
                                    + " destroyed");
                    this.mFillResponseEventLogger.maybeSetResponseStatus(5);
                    this.mFillResponseEventLogger.logAndEndEvent();
                    return;
                }
                if (Helper.sDebug) {
                    Slog.d(
                            "AutofillSession",
                            "finishing session due to service ".concat(z ? "timeout" : "failure"));
                }
                AutofillManagerServiceImpl autofillManagerServiceImpl = this.mService;
                synchronized (autofillManagerServiceImpl.mLock) {
                    autofillManagerServiceImpl.mEventHistory = null;
                }
                this.mLastFillDialogTriggerIds = null;
                LogMaker logMaker = (LogMaker) this.mRequestLogs.get(i);
                if (logMaker == null) {
                    Slog.w(
                            "AutofillSession",
                            "onFillRequestFailureOrTimeout(): no log for id " + i);
                } else {
                    logMaker.setType(z ? 2 : 11);
                }
                if (z2) {
                    ServiceInfo serviceInfo = this.mService.mServiceInfo;
                    int i2 = serviceInfo == null ? 0 : serviceInfo.applicationInfo.targetSdkVersion;
                    if (i2 >= 29) {
                        Slog.w(
                                "AutofillSession",
                                "onFillRequestFailureOrTimeout(): not showing '"
                                        + ((Object) message)
                                        + "' because service's targetting API "
                                        + i2);
                        z2 = false;
                    }
                    if (message != null) {
                        logMaker.addTaggedData(1572, Integer.valueOf(message.length()));
                    }
                }
                if (th instanceof TimeoutException) {
                    this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(5);
                    this.mFillResponseEventLogger.maybeSetResponseStatus(4);
                } else if (th instanceof TransactionTooLargeException) {
                    this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(7);
                    this.mFillResponseEventLogger.maybeSetResponseStatus(6);
                } else {
                    this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(7);
                    this.mFillResponseEventLogger.maybeSetResponseStatus(1);
                }
                this.mPresentationStatsEventLogger.logAndEndEvent();
                FillResponseEventLogger fillResponseEventLogger = this.mFillResponseEventLogger;
                fillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda2(
                                1, fillResponseEventLogger));
                this.mFillResponseEventLogger.logAndEndEvent();
                notifyUnavailableToClient(null, 6);
                if (z2) {
                    getUiForShowing().showError(message, this);
                }
                removeFromService();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public final void onFillRequestSuccess(int i, FillResponse fillResponse, int i2) {
        long j;
        synchronized (this.mLock) {
            try {
                this.mFillResponseEventLogger.startLogForNewResponse();
                this.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(i, 5));
                this.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(this.uid, 2));
                this.mFillResponseEventLogger.maybeSetResponseStatus(2);
                FillResponseEventLogger fillResponseEventLogger = this.mFillResponseEventLogger;
                fillResponseEventLogger.getClass();
                fillResponseEventLogger.startResponseProcessingTimestamp =
                        SystemClock.elapsedRealtime();
                int elapsedRealtime = (int) (SystemClock.elapsedRealtime() - this.mLatencyBaseTime);
                this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                        new PresentationStatsEventLogger$$ExternalSyntheticLambda1(
                                elapsedRealtime, 10));
                this.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(elapsedRealtime, 7));
                this.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(
                                getDetectionPreferenceForLogging(), 4));
                if (this.mDestroyed) {
                    Slog.w(
                            "AutofillSession",
                            "Call to Session#onFillRequestSuccess() rejected - session: "
                                    + this.id
                                    + " destroyed");
                    this.mFillResponseEventLogger.maybeSetResponseStatus(5);
                    this.mFillResponseEventLogger.logAndEndEvent();
                    return;
                }
                if (this.mSessionFlags.mShowingSaveUi) {
                    Slog.w(
                            "AutofillSession",
                            "Call to Session#onFillRequestSuccess() rejected - session: "
                                    + this.id
                                    + " is showing saveUi");
                    this.mFillResponseEventLogger.maybeSetResponseStatus(5);
                    this.mFillResponseEventLogger.logAndEndEvent();
                    return;
                }
                LogMaker logMaker = (LogMaker) this.mRequestLogs.get(i);
                if (logMaker != null) {
                    logMaker.setType(10);
                } else {
                    Slog.w("AutofillSession", "onFillRequestSuccess(): no request log for id " + i);
                }
                if (fillResponse == null) {
                    this.mFillResponseEventLogger.mEventInternal.ifPresent(
                            new FillResponseEventLogger$$ExternalSyntheticLambda0(0, 6));
                    if (logMaker != null) {
                        logMaker.addTaggedData(909, -1);
                    }
                    processNullResponseLocked(i, i2);
                    return;
                }
                AutofillId[] fieldClassificationIds = fillResponse.getFieldClassificationIds();
                if (fieldClassificationIds != null
                        && !this.mService.isFieldClassificationEnabledLocked()) {
                    Slog.w(
                            "AutofillSession",
                            "Ignoring " + fillResponse + " because field detection is disabled");
                    processNullResponseLocked(i, i2);
                    return;
                }
                this.mLastFillDialogTriggerIds = fillResponse.getFillDialogTriggerIds();
                if ((fillResponse.getFlags() & 4) != 0) {
                    Slog.v(
                            "AutofillSession",
                            "Service requested to wait for delayed fill response.");
                    if (!this.mDelayedFillBroadcastReceiverRegistered) {
                        Slog.v("AutofillSession", "registerDelayedFillBroadcastLocked()");
                        this.mContext.registerReceiver(
                                this.mDelayedFillBroadcastReceiver,
                                new IntentFilter("android.service.autofill.action.DELAYED_FILL"));
                        this.mDelayedFillBroadcastReceiverRegistered = true;
                    }
                }
                AutofillManagerServiceImpl autofillManagerServiceImpl = this.mService;
                int i3 = this.id;
                autofillManagerServiceImpl.getClass();
                autofillManagerServiceImpl.mEventHistory =
                        new FillEventHistory(i3, fillResponse.getClientState());
                if (this.mLogViewEntered) {
                    this.mLogViewEntered = false;
                    this.mService.logViewEntered(this.id);
                }
                long disableDuration = fillResponse.getDisableDuration();
                boolean z = disableDuration > 0;
                if (z) {
                    int flags = fillResponse.getFlags();
                    boolean z2 = (flags & 2) != 0;
                    notifyDisableAutofillToClient(z2 ? this.mComponentName : null, disableDuration);
                    if (z2) {
                        j = disableDuration;
                        this.mService.disableAutofillForActivity(
                                this.mComponentName, j, this.id, this.mCompatMode);
                    } else {
                        j = disableDuration;
                        this.mService.disableAutofillForApp(
                                this.mComponentName.getPackageName(), this.id, j, this.mCompatMode);
                    }
                    synchronized (this.mLock) {
                        try {
                            this.mSessionFlags.getClass();
                            if (triggerAugmentedAutofillLocked(i2) != null) {
                                this.mSessionFlags.mAugmentedAutofillOnly = true;
                                if (Helper.sDebug) {
                                    Slog.d(
                                            "AutofillSession",
                                            "Service disabled autofill for "
                                                    + this.mComponentName
                                                    + ", but session is kept for augmented autofill"
                                                    + " only");
                                }
                                return;
                            }
                            if (Helper.sDebug) {
                                StringBuilder sb =
                                        new StringBuilder("Service disabled autofill for ");
                                sb.append(this.mComponentName);
                                sb.append(": flags=");
                                sb.append(flags);
                                sb.append(", duration=");
                                TimeUtils.formatDuration(j, sb);
                                Slog.d("AutofillSession", sb.toString());
                            }
                        } finally {
                        }
                    }
                }
                List datasets = fillResponse.getDatasets();
                if (((datasets == null || datasets.isEmpty())
                                && fillResponse.getAuthentication() == null)
                        || z) {
                    notifyUnavailableToClient(null, z ? 4 : 0);
                    synchronized (this.mLock) {
                        AutofillInlineSessionController autofillInlineSessionController =
                                this.mInlineSessionController;
                        autofillInlineSessionController.mInlineFillUi =
                                new InlineFillUi(this.mCurrentViewId);
                        autofillInlineSessionController.requestImeToShowInlineSuggestionsLocked();
                    }
                }
                if (logMaker != null) {
                    logMaker.addTaggedData(
                            909,
                            Integer.valueOf(
                                    fillResponse.getDatasets() == null
                                            ? 0
                                            : fillResponse.getDatasets().size()));
                    if (fieldClassificationIds != null) {
                        logMaker.addTaggedData(
                                1271, Integer.valueOf(fieldClassificationIds.length));
                    }
                }
                int size = datasets == null ? 0 : datasets.size();
                synchronized (this.mLock) {
                    this.mFillResponseEventLogger.mEventInternal.ifPresent(
                            new FillResponseEventLogger$$ExternalSyntheticLambda0(size, 6));
                    this.mFillResponseEventLogger.mEventInternal.ifPresent(
                            new FillResponseEventLogger$$ExternalSyntheticLambda0(size, 1));
                    processResponseLockedForPcc(fillResponse, fillResponse.getClientState(), i2);
                    FillResponseEventLogger fillResponseEventLogger2 =
                            this.mFillResponseEventLogger;
                    fillResponseEventLogger2.mEventInternal.ifPresent(
                            new FillResponseEventLogger$$ExternalSyntheticLambda2(
                                    1, fillResponseEventLogger2));
                    this.mFillResponseEventLogger.logAndEndEvent();
                }
            } finally {
            }
        }
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public final void onSaveRequestFailure(String str, CharSequence charSequence) {
        boolean z = !TextUtils.isEmpty(charSequence);
        synchronized (this.mLock) {
            try {
                this.mSessionFlags.mShowingSaveUi = false;
                SaveEventLogger saveEventLogger = this.mSaveEventLogger;
                saveEventLogger.getClass();
                saveEventLogger.mEventInternal.ifPresent(
                        new SaveEventLogger$$ExternalSyntheticLambda0(
                                SystemClock.elapsedRealtime()
                                        - saveEventLogger.mSessionStartTimestamp,
                                0));
                this.mSaveEventLogger.logAndEndEvent();
                if (this.mDestroyed) {
                    Slog.w(
                            "AutofillSession",
                            "Call to Session#onSaveRequestFailure() rejected - session: "
                                    + this.id
                                    + " destroyed");
                    return;
                }
                if (z) {
                    ServiceInfo serviceInfo = this.mService.mServiceInfo;
                    int i = serviceInfo == null ? 0 : serviceInfo.applicationInfo.targetSdkVersion;
                    if (i >= 29) {
                        Slog.w(
                                "AutofillSession",
                                "onSaveRequestFailure(): not showing '"
                                        + ((Object) charSequence)
                                        + "' because service's targetting API "
                                        + i);
                        z = false;
                    }
                }
                LogMaker type =
                        Helper.newLogMaker(918, this.mComponentName, str, this.id, this.mCompatMode)
                                .setType(11);
                if (charSequence != null) {
                    type.addTaggedData(1572, Integer.valueOf(((String) charSequence).length()));
                }
                this.mMetricsLogger.write(type);
                if (z) {
                    getUiForShowing().showError(charSequence, this);
                }
                removeFromService();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public final void onSaveRequestSuccess(String str, IntentSender intentSender) {
        synchronized (this.mLock) {
            try {
                this.mSessionFlags.mShowingSaveUi = false;
                this.mSaveEventLogger.mEventInternal.ifPresent(
                        new SaveEventLogger$$ExternalSyntheticLambda3(true));
                SaveEventLogger saveEventLogger = this.mSaveEventLogger;
                saveEventLogger.getClass();
                saveEventLogger.mEventInternal.ifPresent(
                        new SaveEventLogger$$ExternalSyntheticLambda0(
                                SystemClock.elapsedRealtime()
                                        - saveEventLogger.mSessionStartTimestamp,
                                0));
                this.mSaveEventLogger.logAndEndEvent();
                if (this.mDestroyed) {
                    Slog.w(
                            "AutofillSession",
                            "Call to Session#onSaveRequestSuccess() rejected - session: "
                                    + this.id
                                    + " destroyed");
                    return;
                }
                this.mMetricsLogger.write(
                        Helper.newLogMaker(918, this.mComponentName, str, this.id, this.mCompatMode)
                                .setType(intentSender == null ? 10 : 1));
                if (intentSender != null) {
                    if (Helper.sDebug) {
                        Slog.d("AutofillSession", "Starting intent sender on save()");
                    }
                    startIntentSender(intentSender, null);
                }
                removeFromService();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onServiceDied(Object obj) {
        Slog.w("AutofillSession", "removing session because service died");
        synchronized (this.mLock) {
            forceRemoveFromServiceLocked(0);
        }
    }

    public final void onShown(int i, int i2) {
        synchronized (this.mLock) {
            try {
                this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                        new PresentationStatsEventLogger$$ExternalSyntheticLambda1(i, 15));
                if (i == 2) {
                    PresentationStatsEventLogger presentationStatsEventLogger =
                            this.mPresentationStatsEventLogger;
                    presentationStatsEventLogger.mEventInternal.ifPresent(
                            new PresentationStatsEventLogger$$ExternalSyntheticLambda22(
                                    presentationStatsEventLogger, 1));
                    if (!this.mLoggedInlineDatasetShown) {
                        this.mService.logDatasetShown(this.id, i, this.mClientState);
                        Slog.d("AutofillSession", "onShown(): " + i + ", " + i2);
                    }
                    this.mLoggedInlineDatasetShown = true;
                } else {
                    this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                            new PresentationStatsEventLogger$$ExternalSyntheticLambda1(i2, 7));
                    PresentationStatsEventLogger presentationStatsEventLogger2 =
                            this.mPresentationStatsEventLogger;
                    presentationStatsEventLogger2.getClass();
                    presentationStatsEventLogger2.mEventInternal.ifPresent(
                            new PresentationStatsEventLogger$$ExternalSyntheticLambda1(
                                    (int)
                                            (SystemClock.elapsedRealtime()
                                                    - presentationStatsEventLogger2
                                                            .mSessionStartTimestamp),
                                    9));
                    this.mService.logDatasetShown(this.id, i, this.mClientState);
                    Slog.d("AutofillSession", "onShown(): " + i + ", " + i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSwitchInputMethodLocked() {
        ViewState viewState;
        if (this.mSessionFlags.mExpiredResponse
                || this.mService.getRemoteInlineSuggestionRenderServiceLocked() == null) {
            return;
        }
        if (!this.mSessionFlags.mInlineSupportedByService
                && ((viewState = (ViewState) this.mViewStates.get(this.mCurrentViewId)) == null
                        || (viewState.mState & 4096) == 0)) {
            return;
        }
        SessionFlags sessionFlags = this.mSessionFlags;
        sessionFlags.mExpiredResponse = true;
        this.mAugmentedAutofillableIds = null;
        if (sessionFlags.mAugmentedAutofillOnly) {
            this.mCurrentViewId = null;
        }
    }

    public final void processNullResponseLocked(int i, int i2) {
        ArrayList arrayList;
        unregisterDelayedFillBroadcastLocked();
        if ((i2 & 1) != 0) {
            AutoFillUI uiForShowing = getUiForShowing();
            uiForShowing.showError(
                    uiForShowing.mContext.getString(
                            R.string.config_customAdbPublicKeyConfirmationComponent),
                    this);
        }
        FillContext fillContextByRequestIdLocked = getFillContextByRequestIdLocked(i);
        if (fillContextByRequestIdLocked != null) {
            arrayList = Helper.getAutofillIds(fillContextByRequestIdLocked.getStructure(), true);
        } else {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "processNullResponseLocked(): no context for req ", "AutofillSession");
            arrayList = null;
        }
        this.mFillResponseEventLogger.mEventInternal.ifPresent(
                new FillResponseEventLogger$$ExternalSyntheticLambda0(0, 1));
        FillResponseEventLogger fillResponseEventLogger = this.mFillResponseEventLogger;
        fillResponseEventLogger.mEventInternal.ifPresent(
                new FillResponseEventLogger$$ExternalSyntheticLambda2(1, fillResponseEventLogger));
        this.mFillResponseEventLogger.logAndEndEvent();
        AutofillManagerServiceImpl autofillManagerServiceImpl = this.mService;
        synchronized (autofillManagerServiceImpl.mLock) {
            autofillManagerServiceImpl.mEventHistory = null;
        }
        Runnable triggerAugmentedAutofillLocked = triggerAugmentedAutofillLocked(i2);
        this.mAugmentedAutofillDestroyer = triggerAugmentedAutofillLocked;
        if (triggerAugmentedAutofillLocked == null && (i2 & 4) == 0) {
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillSession",
                        "canceling session "
                                + this.id
                                + " when service returned null and it cannot be augmented."
                                + " AutofillableIds: "
                                + arrayList);
            }
            notifyUnavailableToClient(arrayList, 2);
            removeFromService();
            return;
        }
        if ((i2 & 4) != 0) {
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillSession",
                        "keeping session "
                                + this.id
                                + " when service returned null and augmented service is disabled"
                                + " for password fields. AutofillableIds: "
                                + arrayList);
            }
            this.mInlineSessionController.hideInlineSuggestionsUiLocked(this.mCurrentViewId);
        } else if (Helper.sVerbose) {
            Slog.v(
                    "AutofillSession",
                    "keeping session "
                            + this.id
                            + " when service returned null but it can be augmented."
                            + " AutofillableIds: "
                            + arrayList);
        }
        this.mAugmentedAutofillableIds = arrayList;
        try {
            this.mClient.setState(32);
        } catch (RemoteException e) {
            Slog.e("AutofillSession", "Error setting client to autofill-only", e);
        }
    }

    public final void processResponseLocked(FillResponse fillResponse, Bundle bundle, int i) {
        AutoFillUI autoFillUI = this.mUi;
        autoFillUI.mHandler.post(new AutoFillUI$$ExternalSyntheticLambda1(autoFillUI, this, 2));
        if ((fillResponse.getFlags() & 4) == 0) {
            Slog.d("AutofillSession", "Service did not request to wait for delayed fill response.");
            unregisterDelayedFillBroadcastLocked();
        }
        int requestId = fillResponse.getRequestId();
        if (Helper.sVerbose) {
            Slog.v(
                    "AutofillSession",
                    "processResponseLocked(): mCurrentViewId="
                            + this.mCurrentViewId
                            + ",flags="
                            + i
                            + ", reqId="
                            + requestId
                            + ", resp="
                            + fillResponse
                            + ",newClientState="
                            + bundle);
        }
        if (this.mResponses == null) {
            this.mResponses = new SparseArray(2);
        }
        this.mResponses.put(requestId, fillResponse);
        this.mClientState = bundle != null ? bundle : fillResponse.getClientState();
        boolean z = bundle != null && bundle.getBoolean("webview_requested_credential", false);
        List datasets = fillResponse.getDatasets();
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda0(0, z));
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda1(
                        sIdCounterForPcc.get(), 0));
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda11(
                        datasets, this.mCurrentViewId));
        this.mFillResponseEventLogger.mEventInternal.ifPresent(
                new FillResponseEventLogger$$ExternalSyntheticLambda2(0, datasets));
        setViewStatesLocked(fillResponse, 2, false, true);
        FillResponse lastResponseLocked = getLastResponseLocked(null);
        if (lastResponseLocked != null) {
            AutofillId[] fillDialogTriggerIds = lastResponseLocked.getFillDialogTriggerIds();
            notifyClientFillDialogTriggerIds(
                    fillDialogTriggerIds != null ? Arrays.asList(fillDialogTriggerIds) : null);
        }
        updateTrackedIdsLocked();
        AutofillId autofillId = this.mCurrentViewId;
        if (autofillId == null) {
            return;
        }
        ((ViewState) this.mViewStates.get(autofillId)).maybeCallOnFillReady(i);
    }

    public final void processResponseLockedForPcc(FillResponse fillResponse, Bundle bundle, int i) {
        synchronized (this.mLock) {
            try {
                FillResponse effectiveFillResponse = getEffectiveFillResponse(fillResponse);
                if (isEmptyResponse(effectiveFillResponse)) {
                    processNullResponseLocked(
                            effectiveFillResponse != null
                                    ? effectiveFillResponse.getRequestId()
                                    : 0,
                            i);
                } else {
                    processResponseLocked(effectiveFillResponse, bundle, i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeFromService() {
        synchronized (this.mLock) {
            removeFromServiceLocked();
        }
    }

    public final void removeFromServiceLocked() {
        if (Helper.sVerbose) {
            Slog.v(
                    "AutofillSession",
                    "removeFromServiceLocked(" + this.id + "): " + this.mPendingSaveUi);
        }
        if (this.mDestroyed) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "Call to Session#removeFromServiceLocked() rejected - session: "),
                    this.id,
                    " destroyed",
                    "AutofillSession");
            return;
        }
        PendingUi pendingUi = this.mPendingSaveUi;
        if (pendingUi != null && pendingUi.mState == 2) {
            Slog.i(
                    "AutofillSession",
                    "removeFromServiceLocked() ignored, waiting for pending save ui");
            return;
        }
        RemoteFillService destroyLocked = destroyLocked();
        this.mService.mSessions.remove(this.id);
        if (destroyLocked != null) {
            destroyLocked.unbind();
        }
        SecondaryProviderHandler secondaryProviderHandler = this.mSecondaryProviderHandler;
        if (secondaryProviderHandler != null) {
            secondaryProviderHandler.mRemoteFillService.unbind();
        }
        this.mSessionState = 3;
    }

    public final void requestAssistStructureForPccLocked(int i) {
        int andIncrement;
        int i2 = this.mClassificationState.mState;
        if (i2 == 1 || i2 == 5) {
            this.mFillRequestIdSnapshot = sIdCounterForPcc.get();
            this.mClassificationState.mState = 3;
            do {
                andIncrement = sIdCounterForPcc.getAndIncrement();
            } while (andIncrement == Integer.MIN_VALUE);
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillSession",
                        "request id is " + andIncrement + ", requesting assist structure for pcc");
            }
            try {
                Bundle bundle = new Bundle();
                bundle.putInt("android.service.autofill.extra.REQUEST_ID", andIncrement);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (!ActivityTaskManager.getService()
                            .requestAutofillData(
                                    this.mPccAssistReceiver, bundle, this.mActivityToken, i)) {
                        Slog.w(
                                "AutofillSession",
                                "failed to request autofill data for " + this.mActivityToken);
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public final void requestFallbackFromFillDialog() {
        setFillDialogDisabled();
        synchronized (this.mLock) {
            try {
                AutofillId autofillId = this.mCurrentViewId;
                if (autofillId == null) {
                    return;
                }
                ((ViewState) this.mViewStates.get(autofillId)).maybeCallOnFillReady(this.mFlags);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void requestNewFillResponseLocked(ViewState viewState, int i, int i2) {
        int incrementAndGet;
        boolean shouldRequestSecondaryProvider = shouldRequestSecondaryProvider(i2);
        FillResponse fillResponse =
                shouldRequestSecondaryProvider
                        ? viewState.mSecondaryFillResponse
                        : viewState.mPrimaryFillResponse;
        this.mFillRequestEventLogger.startLogForNewRequest();
        this.mRequestCount++;
        this.mFillRequestEventLogger.mEventInternal.ifPresent(
                new FillRequestEventLogger$$ExternalSyntheticLambda0(this.uid, 1));
        this.mFillRequestEventLogger.mEventInternal.ifPresent(
                new FillRequestEventLogger$$ExternalSyntheticLambda0(this.mFlags, 4));
        if (this.mPreviouslyFillDialogPotentiallyStarted) {
            this.mFillRequestEventLogger.maybeSetRequestTriggerReason(3);
        } else if ((i2 & 1) != 0) {
            this.mFillRequestEventLogger.maybeSetRequestTriggerReason(1);
        } else {
            this.mFillRequestEventLogger.maybeSetRequestTriggerReason(4);
        }
        if (fillResponse != null) {
            setViewStatesLocked(fillResponse, 1, true, true);
            this.mFillRequestEventLogger.maybeSetRequestTriggerReason(5);
        }
        SessionFlags sessionFlags = this.mSessionFlags;
        sessionFlags.mExpiredResponse = false;
        this.mSessionState = 1;
        if (sessionFlags.mAugmentedAutofillOnly || this.mRemoteFillService == null) {
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillSession",
                        "requestNewFillResponse(): triggering augmented autofill instead"
                            + " (mForAugmentedAutofillOnly="
                                + this.mSessionFlags.mAugmentedAutofillOnly
                                + ", flags="
                                + i2
                                + ")");
            }
            this.mSessionFlags.mAugmentedAutofillOnly = true;
            this.mFillRequestEventLogger.mEventInternal.ifPresent(
                    new FillRequestEventLogger$$ExternalSyntheticLambda0(1, 3));
            this.mFillRequestEventLogger.mEventInternal.ifPresent(
                    new FillRequestEventLogger$$ExternalSyntheticLambda2());
            this.mFillRequestEventLogger.logAndEndEvent();
            triggerAugmentedAutofillLocked(i2);
            return;
        }
        viewState.setState(i);
        RequestId requestId = mRequestId;
        do {
            incrementAndGet = requestId.sIdCounter.incrementAndGet() % 32768;
            if (incrementAndGet < 2) {
                incrementAndGet = 2;
            }
            requestId.sIdCounter.set(incrementAndGet);
        } while ((incrementAndGet % 2 == 1) != shouldRequestSecondaryProvider);
        if (Helper.sDebug) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    incrementAndGet, "nextId(): requestId = ", "RequestId");
        }
        int size = this.mRequestLogs.size() + 1;
        LogMaker addTaggedData = newLogMaker(907).addTaggedData(1454, Integer.valueOf(size));
        if (i2 != 0) {
            addTaggedData.addTaggedData(1452, Integer.valueOf(i2));
        }
        this.mRequestLogs.put(incrementAndGet, addTaggedData);
        if (Helper.sVerbose) {
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            size,
                            incrementAndGet,
                            "Requesting structure for request #",
                            " ,requestId=",
                            ", flags="),
                    i2,
                    "AutofillSession");
        }
        boolean z = (i2 & 2048) != 0;
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda1(incrementAndGet, 6));
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda0(2, z));
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda1(
                        this.mFieldClassificationIdSnapshot, 0));
        this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda1(
                        getAutofillServiceUid(), 2));
        this.mFillRequestEventLogger.mEventInternal.ifPresent(
                new FillRequestEventLogger$$ExternalSyntheticLambda0(incrementAndGet, 3));
        this.mFillRequestEventLogger.mEventInternal.ifPresent(
                new FillRequestEventLogger$$ExternalSyntheticLambda0(getAutofillServiceUid(), 0));
        this.mSaveEventLogger.mEventInternal.ifPresent(
                new SaveEventLogger$$ExternalSyntheticLambda1(getAutofillServiceUid(), 6));
        this.mSessionCommittedEventLogger.mEventInternal.ifPresent(
                new SessionCommittedEventLogger$$ExternalSyntheticLambda0(
                        getAutofillServiceUid(), 5));
        if (this.mSessionFlags.mInlineSupportedByService) {
            FillRequestEventLogger fillRequestEventLogger = this.mFillRequestEventLogger;
            final Context context = this.mContext;
            final int i3 = this.userId;
            fillRequestEventLogger.mEventInternal.ifPresent(
                    new Consumer() { // from class:
                                     // com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            Context context2 = context;
                            int i4 = i3;
                            FillRequestEventLogger.FillRequestEventInternal
                                    fillRequestEventInternal =
                                            (FillRequestEventLogger.FillRequestEventInternal) obj;
                            String stringForUser =
                                    Settings.Secure.getStringForUser(
                                            context2.getContentResolver(),
                                            "default_input_method",
                                            i4);
                            if (TextUtils.isEmpty(stringForUser)) {
                                Slog.w("FillRequestEventLogger", "No default IME found");
                                return;
                            }
                            ComponentName unflattenFromString =
                                    ComponentName.unflattenFromString(stringForUser);
                            if (unflattenFromString == null) {
                                Slog.w("FillRequestEventLogger", "No default IME found");
                                return;
                            }
                            String packageName = unflattenFromString.getPackageName();
                            try {
                                fillRequestEventInternal.mInlineSuggestionHostUid =
                                        context2.getPackageManager()
                                                .getApplicationInfoAsUser(
                                                        packageName,
                                                        PackageManager.ApplicationInfoFlags.of(0L),
                                                        i4)
                                                .uid;
                            } catch (PackageManager.NameNotFoundException unused) {
                                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                                        "Couldn't find packageName: ",
                                        packageName,
                                        "FillRequestEventLogger");
                            }
                        }
                    });
        }
        this.mFillRequestEventLogger.mEventInternal.ifPresent(
                new FillRequestEventLogger$$ExternalSyntheticLambda2(
                        !this.mSessionFlags.mFillDialogDisabled));
        cancelCurrentRequestLocked();
        if (this.mService.isPccClassificationEnabled()
                && this.mClassificationState.mHintsToAutofillIdMap == null) {
            if (Helper.sVerbose) {
                Slog.v("AutofillSession", "triggering field classification");
            }
            requestAssistStructureForPccLocked(i2 | 512);
        }
        RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderServiceLocked =
                this.mService.getRemoteInlineSuggestionRenderServiceLocked();
        if (!this.mSessionFlags.mInlineSupportedByService
                || remoteInlineSuggestionRenderServiceLocked == null
                || ((i2 & 16) != 0 && (i2 & 64) == 0)) {
            AssistDataReceiverImpl assistDataReceiverImpl = this.mAssistReceiver;
            assistDataReceiverImpl.mPendingFillRequest = null;
            assistDataReceiverImpl.mWaitForInlineRequest = false;
            assistDataReceiverImpl.mPendingInlineSuggestionsRequest = null;
        } else {
            AssistDataReceiverImpl assistDataReceiverImpl2 = this.mAssistReceiver;
            assistDataReceiverImpl2.mPendingFillRequest = null;
            assistDataReceiverImpl2.mWaitForInlineRequest = true;
            assistDataReceiverImpl2.mPendingInlineSuggestionsRequest = null;
            remoteInlineSuggestionRenderServiceLocked.getInlineSuggestionsRendererInfo(
                    new RemoteCallback(
                            new InlineSuggestionRendorInfoCallbackOnResultListener(
                                    new WeakReference(this),
                                    incrementAndGet,
                                    new InlineSuggestionRequestConsumer(
                                            new WeakReference(assistDataReceiverImpl2),
                                            new WeakReference(viewState)),
                                    this.mCurrentViewId),
                            this.mHandler));
            viewState.setState(EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("android.service.autofill.extra.REQUEST_ID", incrementAndGet);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!ActivityTaskManager.getService()
                        .requestAutofillData(
                                this.mAssistReceiver, bundle, this.mActivityToken, i2)) {
                    Slog.w(
                            "AutofillSession",
                            "failed to request autofill data for " + this.mActivityToken);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (RemoteException unused) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x004d, code lost:

       if ((r0.mState & com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) == 0) goto L66;
    */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean requestNewFillResponseOnViewEnteredIfNecessaryLocked(
            android.view.autofill.AutofillId r13,
            com.android.server.autofill.ViewState r14,
            int r15) {
        /*
            Method dump skipped, instructions count: 319
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.Session.requestNewFillResponseOnViewEnteredIfNecessaryLocked(android.view.autofill.AutofillId,"
                    + " com.android.server.autofill.ViewState, int):boolean");
    }

    public final boolean requestShowFillDialog(
            final FillResponse fillResponse, final AutofillId autofillId, final String str, int i) {
        boolean z;
        boolean z2;
        final Drawable serviceIcon;
        synchronized (this.mLock) {
            SessionFlags sessionFlags = this.mSessionFlags;
            z =
                    (sessionFlags.mFillDialogDisabled || sessionFlags.mScreenHasCredmanField)
                            ? false
                            : true;
        }
        if (!z) {
            if (Helper.sDebug) {
                Log.w("AutofillSession", "requestShowFillDialog: fill dialog is disabled");
            }
            return false;
        }
        if ((i & 128) != 0) {
            if (Helper.sDebug) {
                Log.w("AutofillSession", "requestShowFillDialog: IME is showing");
            }
            return false;
        }
        AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                this.mInlineSessionController.mSession;
        if (autofillInlineSuggestionsRequestSession != null) {
            synchronized (autofillInlineSuggestionsRequestSession.mLock) {
                try {
                    z2 =
                            !autofillInlineSuggestionsRequestSession.mDestroyed
                                    && autofillInlineSuggestionsRequestSession.mImeShowing;
                } finally {
                }
            }
        } else {
            z2 = false;
        }
        if (z2) {
            return false;
        }
        synchronized (this.mLock) {
            AutofillId[] autofillIdArr = this.mLastFillDialogTriggerIds;
            if (autofillIdArr != null && ArrayUtils.contains(autofillIdArr, autofillId)) {
                synchronized (this.mLock) {
                    serviceIcon = getServiceIcon(fillResponse);
                }
                final AutoFillUI uiForShowing = getUiForShowing();
                final String servicePackageName = this.mService.getServicePackageName();
                final ComponentName componentName = this.mComponentName;
                int i2 = this.id;
                boolean z3 = this.mCompatMode;
                final PresentationStatsEventLogger presentationStatsEventLogger =
                        this.mPresentationStatsEventLogger;
                final Object obj = this.mLock;
                uiForShowing.getClass();
                if (Helper.sVerbose) {
                    Slog.v(
                            "AutofillUI",
                            "showFillDialog for "
                                    + componentName.toShortString()
                                    + ": "
                                    + fillResponse);
                }
                final LogMaker addTaggedData =
                        Helper.newLogMaker(910, componentName, servicePackageName, i2, z3)
                                .addTaggedData(911, Integer.valueOf(str == null ? 0 : str.length()))
                                .addTaggedData(
                                        909,
                                        Integer.valueOf(
                                                fillResponse.getDatasets() != null
                                                        ? fillResponse.getDatasets().size()
                                                        : 0));
                uiForShowing.mHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                AutoFillUI autoFillUI = AutoFillUI.this;
                                AutoFillUI.AutoFillUiCallback autoFillUiCallback = this;
                                FillResponse fillResponse2 = fillResponse;
                                AutofillId autofillId2 = autofillId;
                                String str2 = str;
                                Drawable drawable = serviceIcon;
                                String str3 = servicePackageName;
                                ComponentName componentName2 = componentName;
                                Object obj2 = obj;
                                PresentationStatsEventLogger presentationStatsEventLogger2 =
                                        presentationStatsEventLogger;
                                LogMaker logMaker = addTaggedData;
                                if (autoFillUiCallback != autoFillUI.mCallback) {
                                    return;
                                }
                                autoFillUI.hideAllUiThread(autoFillUiCallback);
                                autoFillUI.mFillDialog =
                                        new DialogFillUi(
                                                autoFillUI.mContext,
                                                fillResponse2,
                                                autofillId2,
                                                str2,
                                                drawable,
                                                str3,
                                                componentName2,
                                                autoFillUI.mOverlayControl,
                                                autoFillUI.mUiModeMgr.isNightMode(),
                                                autoFillUI
                                                .new AnonymousClass3(
                                                        autoFillUiCallback,
                                                        fillResponse2,
                                                        obj2,
                                                        presentationStatsEventLogger2,
                                                        autofillId2,
                                                        logMaker));
                            }
                        });
                return true;
            }
            if (Helper.sDebug) {
                Log.w("AutofillSession", "Last fill dialog triggered ids are changed.");
            }
            return false;
        }
    }

    public final boolean requestShowInlineSuggestionsLocked(FillResponse fillResponse, String str) {
        int i;
        InlineFillUi inlineFillUi;
        AutofillId autofillId = this.mCurrentViewId;
        boolean z = false;
        if (autofillId == null) {
            Log.w(
                    "AutofillSession",
                    "requestShowInlineSuggestionsLocked(): no view currently focused");
            return false;
        }
        AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                this.mInlineSessionController.mSession;
        Optional empty =
                autofillInlineSuggestionsRequestSession != null
                        ? autofillInlineSuggestionsRequestSession.mDestroyed
                                ? Optional.empty()
                                : Optional.ofNullable(
                                        autofillInlineSuggestionsRequestSession.mImeRequest)
                        : Optional.empty();
        if (!empty.isPresent()) {
            Log.w("AutofillSession", "InlineSuggestionsRequest unavailable");
            return false;
        }
        RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderServiceLocked =
                this.mService.getRemoteInlineSuggestionRenderServiceLocked();
        if (remoteInlineSuggestionRenderServiceLocked == null) {
            Log.w("AutofillSession", "RemoteInlineSuggestionRenderService not found");
            return false;
        }
        synchronized (this.mLock) {
            this.mLoggedInlineDatasetShown = false;
        }
        InlineSuggestionsRequest inlineSuggestionsRequest = (InlineSuggestionsRequest) empty.get();
        InlineFillUi.InlineFillUiInfo inlineFillUiInfo =
                new InlineFillUi.InlineFillUiInfo(
                        inlineSuggestionsRequest,
                        autofillId,
                        str,
                        remoteInlineSuggestionRenderServiceLocked,
                        this.userId,
                        this.id);
        final AnonymousClass3 anonymousClass3 = new AnonymousClass3(this, fillResponse, autofillId);
        AutofillManagerService autofillManagerService =
                (AutofillManagerService) this.mService.mMaster;
        synchronized (autofillManagerService.mFlagLock) {
            i = autofillManagerService.mMaxInputLengthForAutofill;
        }
        if (fillResponse.getAuthentication() != null
                && fillResponse.getInlinePresentation() != null) {
            InlinePresentation inlinePresentation = fillResponse.getInlinePresentation();
            final int requestId = fillResponse.getRequestId();
            boolean z2 = Flags.autofillCredmanIntegration() && (fillResponse.getFlags() & 8) != 0;
            Runnable runnable =
                    new Runnable(
                            anonymousClass3,
                            requestId) { // from class:
                                         // com.android.server.autofill.ui.InlineSuggestionFactory$$ExternalSyntheticLambda0
                        public final /* synthetic */ InlineFillUi.InlineSuggestionUiCallback f$0;
                        public final /* synthetic */ int f$1;

                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.authenticate();
                        }
                    };
            InlinePresentation mergedInlinePresentation =
                    InlineSuggestionFactory.mergedInlinePresentation(
                            inlineSuggestionsRequest, 0, inlinePresentation, z2);
            inlineFillUi =
                    new InlineFillUi(
                            inlineFillUiInfo,
                            new InlineSuggestion(
                                    new InlineSuggestionInfo(
                                            mergedInlinePresentation.getInlinePresentationSpec(),
                                            "android:autofill",
                                            mergedInlinePresentation.getAutofillHints(),
                                            "android:autofill:action",
                                            mergedInlinePresentation.isPinned(),
                                            InlineSuggestionFactory.createInlineSuggestionTooltip(
                                                    inlineSuggestionsRequest,
                                                    inlineFillUiInfo,
                                                    "android:autofill",
                                                    fillResponse.getInlineTooltipPresentation())),
                                    new InlineContentProviderImpl(
                                            new RemoteInlineSuggestionViewConnector(
                                                    inlineFillUiInfo,
                                                    mergedInlinePresentation,
                                                    runnable,
                                                    anonymousClass3),
                                            null)),
                            i);
        } else if (fillResponse.getDatasets() != null) {
            if (Flags.autofillCredmanIntegration() && (fillResponse.getFlags() & 8) != 0) {
                z = true;
            }
            inlineFillUi =
                    new InlineFillUi(
                            inlineFillUiInfo,
                            InlineSuggestionFactory.createInlineSuggestions(
                                    inlineFillUiInfo,
                                    "android:autofill",
                                    fillResponse.getDatasets(),
                                    anonymousClass3,
                                    z),
                            i);
        } else {
            inlineFillUi = new InlineFillUi(inlineFillUiInfo, new SparseArray(), i);
        }
        AutofillInlineSessionController autofillInlineSessionController =
                this.mInlineSessionController;
        autofillInlineSessionController.mInlineFillUi = inlineFillUi;
        return autofillInlineSessionController.requestImeToShowInlineSuggestionsLocked();
    }

    public final void requestShowSoftInput(AutofillId autofillId) {
        IAutoFillManagerClient client = getClient();
        if (client != null) {
            try {
                client.requestShowSoftInput(autofillId);
            } catch (RemoteException e) {
                Slog.e("AutofillSession", "Error sending input show up notification", e);
            }
        }
    }

    public final void sendCredentialManagerResponseToApp(
            GetCredentialException getCredentialException,
            GetCredentialResponse getCredentialResponse,
            AutofillId autofillId) {
        boolean z;
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                Slog.w(
                        "AutofillSession",
                        "Call to Session#sendCredentialManagerResponseToApp() rejected - session: "
                                + this.id
                                + " destroyed");
                return;
            }
            try {
                ViewState viewState = (ViewState) this.mViewStates.get(autofillId);
                AutofillManagerService autofillManagerService =
                        (AutofillManagerService) this.mService.mMaster;
                synchronized (autofillManagerService.mFlagLock) {
                    z = autofillManagerService.mIsFillFieldsFromCurrentSessionOnly;
                }
                if (z
                        && viewState != null
                        && viewState.id.getSessionId() != this.id
                        && Helper.sVerbose) {
                    Slog.v(
                            "AutofillSession",
                            "Skipping sending credential response to view: "
                                    + autofillId
                                    + " as it isn't part of the current session: "
                                    + this.id);
                }
                if (getCredentialException != null) {
                    if (autofillId.isVirtualInt()) {
                        sendResponseToViewNode(getCredentialException, null, autofillId);
                    } else {
                        this.mClient.onGetCredentialException(
                                this.id,
                                autofillId,
                                getCredentialException.getType(),
                                getCredentialException.getMessage());
                    }
                } else if (getCredentialResponse == null) {
                    Slog.w(
                            "AutofillSession",
                            "sendCredentialManagerResponseToApp called with null responseand"
                                + " exception");
                } else if (autofillId.isVirtualInt()) {
                    sendResponseToViewNode(null, getCredentialResponse, autofillId);
                } else {
                    this.mClient.onGetCredentialResponse(
                            this.id, autofillId, getCredentialResponse);
                }
            } catch (RemoteException e) {
                Slog.w("AutofillSession", "Error sending credential response to activity: " + e);
            }
        }
    }

    public final void sendResponseToViewNode(
            GetCredentialException getCredentialException,
            GetCredentialResponse getCredentialResponse,
            AutofillId autofillId) {
        AssistStructure.ViewNode viewNode;
        int size = this.mContexts.size() - 1;
        while (true) {
            if (size < 0) {
                viewNode = null;
                break;
            }
            viewNode =
                    Helper.findViewNode(
                            ((FillContext) this.mContexts.get(size)).getStructure(),
                            new Helper$$ExternalSyntheticLambda0(0, autofillId));
            if (viewNode != null) {
                break;
            } else {
                size--;
            }
        }
        if (viewNode == null || viewNode.getPendingCredentialCallback() == null) {
            Slog.w("AutofillSession", "View node not found after GetCredentialResponse");
            return;
        }
        Bundle bundle = new Bundle();
        if (getCredentialResponse != null) {
            bundle.putParcelable(
                    "android.service.credentials.extra.GET_CREDENTIAL_RESPONSE",
                    getCredentialResponse);
            viewNode.getPendingCredentialCallback().send(0, bundle);
        } else if (getCredentialException != null) {
            bundle.putStringArray(
                    "android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION",
                    new String[] {
                        getCredentialException.getType(), getCredentialException.getMessage()
                    });
            viewNode.getPendingCredentialCallback().send(-1, bundle);
        }
    }

    public final void setAuthenticationResultLocked(int i, Bundle bundle) {
        Dataset dataset;
        Dataset dataset2;
        boolean z;
        Bundle data;
        if (this.mDestroyed) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "Call to Session#setAuthenticationResultLocked() rejected - session: "),
                    this.id,
                    " destroyed",
                    "AutofillSession");
            return;
        }
        if (Helper.sDebug) {
            Slog.d(
                    "AutofillSession",
                    "setAuthenticationResultLocked(): id= " + i + ", data=" + bundle);
        }
        int requestIdFromAuthenticationId = AutofillManager.getRequestIdFromAuthenticationId(i);
        boolean z2 = true;
        if (requestIdFromAuthenticationId == 1) {
            Dataset dataset3 =
                    bundle == null
                            ? null
                            : (Dataset)
                                    bundle.getParcelable(
                                            "android.view.autofill.extra.AUTHENTICATION_RESULT",
                                            Dataset.class);
            if (Helper.sDebug) {
                StringBuilder sb =
                        new StringBuilder("Auth result for augmented autofill: sessionId=");
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        this.id, i, ", authId=", ", dataset=", sb);
                sb.append(dataset3);
                Slog.d("AutofillSession", sb.toString());
            }
            AutofillId autofillId =
                    (dataset3 == null || dataset3.getFieldIds().size() != 1)
                            ? null
                            : (AutofillId) dataset3.getFieldIds().get(0);
            AutofillValue autofillValue =
                    (dataset3 == null || dataset3.getFieldValues().size() != 1)
                            ? null
                            : (AutofillValue) dataset3.getFieldValues().get(0);
            ClipData fieldContent = dataset3 != null ? dataset3.getFieldContent() : null;
            if (autofillId == null || (autofillValue == null && fieldContent == null)) {
                if (Helper.sDebug) {
                    Slog.d("AutofillSession", "Rejecting empty/invalid auth result");
                }
                AutofillManagerServiceImpl autofillManagerServiceImpl = this.mService;
                synchronized (autofillManagerServiceImpl.mLock) {
                    autofillManagerServiceImpl.mAugmentedAutofillEventHistory = null;
                }
                removeFromServiceLocked();
            } else {
                RemoteAugmentedAutofillService remoteAugmentedAutofillService =
                        this.mService.mRemoteAugmentedAutofillService;
                if (remoteAugmentedAutofillService == null) {
                    Slog.e(
                            "AutofillSession",
                            "Can't fill after auth: RemoteAugmentedAutofillService is null");
                    AutofillManagerServiceImpl autofillManagerServiceImpl2 = this.mService;
                    synchronized (autofillManagerServiceImpl2.mLock) {
                        autofillManagerServiceImpl2.mAugmentedAutofillEventHistory = null;
                    }
                    removeFromServiceLocked();
                } else {
                    autofillId.setSessionId(this.id);
                    this.mCurrentViewId = autofillId;
                    this.mService.logAugmentedAutofillSelected(
                            this.id,
                            dataset3.getId(),
                            bundle.getBundle("android.view.autofill.extra.CLIENT_STATE"));
                    if (fieldContent != null) {
                        remoteAugmentedAutofillService
                                .getAutofillUriGrantsManager()
                                .grantUriPermissions(
                                        this.mComponentName,
                                        this.mActivityToken,
                                        this.userId,
                                        fieldContent);
                    }
                    if (Helper.sDebug) {
                        Slog.d(
                                "AutofillSession",
                                "Filling after auth: fieldId="
                                        + autofillId
                                        + ", value="
                                        + autofillValue
                                        + ", content="
                                        + fieldContent);
                    }
                    try {
                        if (fieldContent != null) {
                            this.mClient.autofillContent(this.id, autofillId, fieldContent);
                        } else {
                            this.mClient.autofill(
                                    this.id,
                                    dataset3.getFieldIds(),
                                    dataset3.getFieldValues(),
                                    true);
                        }
                    } catch (RemoteException e) {
                        Slog.w(
                                "AutofillSession",
                                "Error filling after auth: fieldId="
                                        + autofillId
                                        + ", value="
                                        + autofillValue
                                        + ", content="
                                        + fieldContent,
                                e);
                    }
                    AutofillInlineSessionController autofillInlineSessionController =
                            this.mInlineSessionController;
                    autofillInlineSessionController.mInlineFillUi = new InlineFillUi(autofillId);
                    autofillInlineSessionController.requestImeToShowInlineSuggestionsLocked();
                }
            }
            this.mPresentationStatsEventLogger.logAndEndEvent();
            return;
        }
        SparseArray sparseArray = this.mResponses;
        if (sparseArray == null) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                    i, "setAuthenticationResultLocked(", "): no responses", "AutofillSession");
            this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(2);
            this.mPresentationStatsEventLogger.logAndEndEvent();
            removeFromService();
            return;
        }
        FillResponse fillResponse =
                requestIdFromAuthenticationId % 2 == 1
                        ? (FillResponse) this.mSecondaryResponses.get(requestIdFromAuthenticationId)
                        : (FillResponse) sparseArray.get(requestIdFromAuthenticationId);
        if (fillResponse == null || bundle == null) {
            Slog.w("AutofillSession", "no authenticated response");
            this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(2);
            this.mPresentationStatsEventLogger.logAndEndEvent();
            removeFromService();
            return;
        }
        int datasetIdFromAuthenticationId = AutofillManager.getDatasetIdFromAuthenticationId(i);
        if (datasetIdFromAuthenticationId != 65535) {
            dataset = (Dataset) fillResponse.getDatasets().get(datasetIdFromAuthenticationId);
            if (dataset == null) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                        datasetIdFromAuthenticationId,
                        "no dataset with index ",
                        " on fill response",
                        "AutofillSession");
                this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(2);
                this.mPresentationStatsEventLogger.logAndEndEvent();
                removeFromService();
                return;
            }
        } else {
            dataset = null;
        }
        this.mSessionFlags.mExpiredResponse = false;
        Parcelable parcelable =
                bundle.getParcelable("android.view.autofill.extra.AUTHENTICATION_RESULT");
        GetCredentialException getCredentialException =
                (GetCredentialException)
                        bundle.getSerializable(
                                "android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION",
                                GetCredentialException.class);
        Bundle bundle2 = bundle.getBundle("android.view.autofill.extra.CLIENT_STATE");
        if (Helper.sDebug) {
            StringBuilder sb2 = new StringBuilder("setAuthenticationResultLocked(): result=");
            sb2.append(parcelable);
            sb2.append(", clientState=");
            sb2.append(bundle2);
            sb2.append(", authenticationId=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb2, i, "AutofillSession");
        }
        if (Flags.autofillCredmanDevIntegration()
                && getCredentialException != null
                && !getCredentialException
                        .getType()
                        .equals("android.credentials.GetCredentialException.TYPE_USER_CANCELED")) {
            if (dataset == null || dataset.getFieldIds().size() != 1) {
                return;
            }
            if (Helper.sDebug) {
                Slog.d(
                        "AutofillSession",
                        "setAuthenticationResultLocked(): result returns withCredential Manager"
                            + " Exception");
            }
            sendCredentialManagerResponseToApp(
                    getCredentialException, null, (AutofillId) dataset.getFieldIds().get(0));
            return;
        }
        if (parcelable instanceof FillResponse) {
            if (Helper.sDebug) {
                Slog.d(
                        "AutofillSession",
                        "setAuthenticationResultLocked(): received FillResponse from authentication"
                            + " flow");
            }
            addTaggedDataToRequestLogLocked(
                    requestIdFromAuthenticationId,
                    1453,
                    Integer.valueOf(
                            FrameworkStatsLog
                                    .MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_720P_HD_ALMOST));
            this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(1);
            FillResponse fillResponse2 = (FillResponse) parcelable;
            setViewStatesLocked(fillResponse, 1, true, true);
            fillResponse2.setRequestId(fillResponse.getRequestId());
            processResponseLockedForPcc(fillResponse2, bundle2, 0);
            return;
        }
        if (parcelable instanceof GetCredentialResponse) {
            if (Helper.sDebug) {
                Slog.d(
                        "AutofillSession",
                        "Received GetCredentialResponse from authentication flow");
            }
            if (!Flags.autofillCredmanDevIntegration()) {
                if (Flags.autofillCredmanIntegration()) {
                    GetCredentialResponse getCredentialResponse =
                            (GetCredentialResponse) parcelable;
                    Dataset dataset4 =
                            (getCredentialResponse == null
                                            || (data =
                                                            getCredentialResponse
                                                                    .getCredential()
                                                                    .getData())
                                                    == null)
                                    ? null
                                    : (Dataset)
                                            data.getParcelable(
                                                    "android.view.autofill.extra.AUTHENTICATION_RESULT",
                                                    Dataset.class);
                    if (dataset4 != null) {
                        autoFill(
                                requestIdFromAuthenticationId,
                                datasetIdFromAuthenticationId,
                                dataset4,
                                false,
                                0);
                        return;
                    }
                    return;
                }
                return;
            }
            GetCredentialResponse getCredentialResponse2 = (GetCredentialResponse) parcelable;
            if (dataset == null || dataset.getFieldIds().size() != 1) {
                return;
            }
            AutofillId autofillId2 = (AutofillId) dataset.getFieldIds().get(0);
            if (Helper.sDebug) {
                Slog.d(
                        "AutofillSession",
                        "Received GetCredentialResponse from authentication flow,for autofillId: "
                                + autofillId2);
            }
            sendCredentialManagerResponseToApp(null, getCredentialResponse2, autofillId2);
            return;
        }
        if (!(parcelable instanceof Dataset)) {
            if (parcelable != null) {
                Slog.w("AutofillSession", "service returned invalid auth type: " + parcelable);
            }
            addTaggedDataToRequestLogLocked(requestIdFromAuthenticationId, 1453, 1128);
            this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(2);
            processNullResponseLocked(requestIdFromAuthenticationId, 0);
            return;
        }
        if (Helper.sDebug) {
            Slog.d(
                    "AutofillSession",
                    "setAuthenticationResultLocked(): received Dataset from authentication flow");
        }
        if (datasetIdFromAuthenticationId == 65535) {
            PendingIntentController$$ExternalSyntheticOutline0.m(
                    datasetIdFromAuthenticationId,
                    i,
                    "invalid index (",
                    ") for authentication id ",
                    "AutofillSession");
            addTaggedDataToRequestLogLocked(requestIdFromAuthenticationId, 1453, 1127);
            this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(2);
            return;
        }
        addTaggedDataToRequestLogLocked(requestIdFromAuthenticationId, 1453, 1126);
        this.mPresentationStatsEventLogger.maybeSetAuthenticationResult(1);
        if (bundle2 != null) {
            if (Helper.sDebug) {
                Slog.d("AutofillSession", "Updating client state from auth dataset");
            }
            this.mClientState = bundle2;
        }
        Dataset dataset5 = (Dataset) parcelable;
        FillResponse effectiveFillResponse =
                getEffectiveFillResponse(new FillResponse.Builder().addDataset(dataset5).build());
        if (effectiveFillResponse == null || effectiveFillResponse.getDatasets().size() == 0) {
            StringBuilder sb3 =
                    new StringBuilder(
                            "No datasets in fill response on authentication. response = ");
            sb3.append(effectiveFillResponse == null ? "null" : effectiveFillResponse.toString());
            Log.wtf("AutofillSession", sb3.toString());
            dataset2 = dataset5;
        } else {
            List<Dataset> datasets = effectiveFillResponse.getDatasets();
            Dataset dataset6 = (Dataset) effectiveFillResponse.getDatasets().get(0);
            if (datasets.size() > 1) {
                Dataset.Builder builder = new Dataset.Builder();
                for (Dataset dataset7 : datasets) {
                    if (!dataset7.getFieldIds().isEmpty()) {
                        for (int i2 = 0; i2 < dataset7.getFieldIds().size(); i2++) {
                            builder.setField(
                                    (AutofillId) dataset7.getFieldIds().get(i2),
                                    new Field.Builder()
                                            .setValue(
                                                    (AutofillValue)
                                                            dataset7.getFieldValues().get(i2))
                                            .build());
                        }
                    }
                }
                dataset6 = builder.setId(dataset5.getId()).build();
            }
            dataset2 = dataset6;
        }
        Dataset dataset8 = (Dataset) fillResponse.getDatasets().get(datasetIdFromAuthenticationId);
        if (bundle.containsKey(
                "android.view.autofill.extra.AUTHENTICATION_RESULT_EPHEMERAL_DATASET")) {
            z =
                    bundle.getBoolean(
                            "android.view.autofill.extra.AUTHENTICATION_RESULT_EPHEMERAL_DATASET");
        } else {
            if (dataset8 != null && dataset8.getFieldIds() != null) {
                int size = dataset8.getFieldIds().size();
                for (int i3 = 0; i3 < size; i3++) {
                    InlinePresentation fieldInlinePresentation =
                            dataset8.getFieldInlinePresentation(i3);
                    if (fieldInlinePresentation != null && fieldInlinePresentation.isPinned()) {
                        break;
                    }
                }
            }
            z2 = false;
            z = z2;
        }
        if (!z) {
            fillResponse.getDatasets().set(datasetIdFromAuthenticationId, dataset2);
        }
        autoFill(requestIdFromAuthenticationId, datasetIdFromAuthenticationId, dataset2, false, 0);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.autofill.Session$$ExternalSyntheticLambda3] */
    public final void setClientLocked(IBinder iBinder) {
        unlinkClientVultureLocked();
        IAutoFillManagerClient asInterface = IAutoFillManagerClient.Stub.asInterface(iBinder);
        this.mClient = asInterface;
        this.mClientVulture =
                new IBinder
                        .DeathRecipient() { // from class:
                                            // com.android.server.autofill.Session$$ExternalSyntheticLambda3
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        Session session = Session.this;
                        synchronized (session.mLock) {
                            try {
                                Slog.d(
                                        "AutofillSession",
                                        "handling death of "
                                                + session.mActivityToken
                                                + " when saving="
                                                + session.mSessionFlags.mShowingSaveUi);
                                if (session.mSessionFlags.mShowingSaveUi) {
                                    AutoFillUI autoFillUI = session.mUi;
                                    autoFillUI.mHandler.post(
                                            new AutoFillUI$$ExternalSyntheticLambda1(
                                                    autoFillUI, session, 3));
                                } else {
                                    AutoFillUI autoFillUI2 = session.mUi;
                                    autoFillUI2.mHandler.post(
                                            new AutoFillUI$$ExternalSyntheticLambda3(
                                                    autoFillUI2,
                                                    session.mPendingSaveUi,
                                                    session,
                                                    false));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        try {
            asInterface.asBinder().linkToDeath(this.mClientVulture, 0);
        } catch (RemoteException e) {
            AccountManagerService$$ExternalSyntheticOutline0.m(
                    "could not set binder death listener on autofill client: ",
                    e,
                    "AutofillSession");
            this.mClientVulture = null;
        }
    }

    public final void setFillDialogDisabled() {
        synchronized (this.mLock) {
            this.mSessionFlags.mFillDialogDisabled = true;
        }
        notifyClientFillDialogTriggerIds(null);
    }

    public final void setViewStatesLocked(FillResponse fillResponse, int i, boolean z, boolean z2) {
        List datasets = fillResponse.getDatasets();
        if (datasets != null && !datasets.isEmpty()) {
            for (int i2 = 0; i2 < datasets.size(); i2++) {
                Dataset dataset = (Dataset) datasets.get(i2);
                if (dataset == null) {
                    Slog.w("AutofillSession", "Ignoring null dataset on " + datasets);
                } else {
                    setViewStatesLocked(fillResponse, dataset, i, z, z2);
                }
            }
        } else if (fillResponse.getAuthentication() != null) {
            for (AutofillId autofillId : fillResponse.getAuthenticationIds()) {
                ViewState createOrUpdateViewStateLocked =
                        createOrUpdateViewStateLocked(autofillId, i, null);
                if (z) {
                    if (z2) {
                        createOrUpdateViewStateLocked.mPrimaryFillResponse = null;
                    } else {
                        createOrUpdateViewStateLocked.mSecondaryFillResponse = null;
                    }
                } else if (z2) {
                    createOrUpdateViewStateLocked.mPrimaryFillResponse = fillResponse;
                } else {
                    createOrUpdateViewStateLocked.mSecondaryFillResponse = fillResponse;
                }
            }
        }
        SaveInfo saveInfo = fillResponse.getSaveInfo();
        if (saveInfo != null) {
            AutofillId[] requiredIds = saveInfo.getRequiredIds();
            if (requiredIds != null) {
                for (AutofillId autofillId2 : requiredIds) {
                    createOrUpdateViewStateLocked(autofillId2, i, null);
                }
            }
            AutofillId[] optionalIds = saveInfo.getOptionalIds();
            if (optionalIds != null) {
                for (AutofillId autofillId3 : optionalIds) {
                    createOrUpdateViewStateLocked(autofillId3, i, null);
                }
            }
        }
        AutofillId[] authenticationIds = fillResponse.getAuthenticationIds();
        if (authenticationIds != null) {
            for (AutofillId autofillId4 : authenticationIds) {
                createOrUpdateViewStateLocked(autofillId4, i, null);
            }
        }
    }

    public final void setViewStatesLocked(
            FillResponse fillResponse, Dataset dataset, int i, boolean z, boolean z2) {
        ArrayList fieldIds = dataset.getFieldIds();
        ArrayList fieldValues = dataset.getFieldValues();
        for (int i2 = 0; i2 < fieldIds.size(); i2++) {
            ViewState createOrUpdateViewStateLocked =
                    createOrUpdateViewStateLocked(
                            (AutofillId) fieldIds.get(i2), i, (AutofillValue) fieldValues.get(i2));
            String id = dataset.getId();
            if (id != null) {
                createOrUpdateViewStateLocked.mDatasetId = id;
            }
            if (z) {
                if (z2) {
                    createOrUpdateViewStateLocked.mPrimaryFillResponse = null;
                } else {
                    createOrUpdateViewStateLocked.mSecondaryFillResponse = null;
                }
            } else if (fillResponse != null) {
                if (z2) {
                    createOrUpdateViewStateLocked.mPrimaryFillResponse = fillResponse;
                } else {
                    createOrUpdateViewStateLocked.mSecondaryFillResponse = fillResponse;
                }
            }
        }
    }

    public final boolean shouldRequestSecondaryProvider(int i) {
        boolean z;
        AutofillManagerService autofillManagerService =
                (AutofillManagerService) this.mService.mMaster;
        synchronized (autofillManagerService.mFlagLock) {
            z = autofillManagerService.mAutofillCredmanIntegrationEnabled;
        }
        if (!z || this.mSecondaryProviderHandler == null) {
            return false;
        }
        return this.mIsPrimaryCredential ? (i & 2048) == 0 : (i & 2048) != 0;
    }

    public final void startAuthentication(
            int i, IntentSender intentSender, Intent intent, boolean z) {
        try {
            synchronized (this.mLock) {
                this.mClient.authenticate(this.id, i, intentSender, intent, z);
            }
        } catch (RemoteException e) {
            Slog.e("AutofillSession", "Error launching auth intent", e);
        }
    }

    public final void startIntentSender(IntentSender intentSender, Intent intent) {
        synchronized (this.mLock) {
            try {
                if (!this.mDestroyed) {
                    if (intent == null) {
                        removeFromServiceLocked();
                    }
                    this.mHandler.sendMessage(
                            PooledLambda.obtainMessage(
                                    new Session$$ExternalSyntheticLambda1(1),
                                    this,
                                    intentSender,
                                    intent));
                } else {
                    Slog.w(
                            "AutofillSession",
                            "Call to Session#startIntentSender() rejected - session: "
                                    + this.id
                                    + " destroyed");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startNewEventForPresentationStatsEventLogger() {
        synchronized (this.mLock) {
            this.mPresentationStatsEventLogger.startNewEvent();
            this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                    new PresentationStatsEventLogger$$ExternalSyntheticLambda1(
                            getDetectionPreferenceForLogging(), 12));
            this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                    new PresentationStatsEventLogger$$ExternalSyntheticLambda1(
                            getAutofillServiceUid(), 2));
        }
    }

    public final String toString() {
        return "Session: [id="
                + this.id
                + ", component="
                + this.mComponentName
                + ", state="
                + sessionStateAsString(this.mSessionState)
                + "]";
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0196  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Runnable triggerAugmentedAutofillLocked(int r13) {
        /*
            Method dump skipped, instructions count: 422
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.Session.triggerAugmentedAutofillLocked(int):java.lang.Runnable");
    }

    public final void unlinkClientVultureLocked() {
        IAutoFillManagerClient iAutoFillManagerClient = this.mClient;
        if (iAutoFillManagerClient == null || this.mClientVulture == null) {
            return;
        }
        if (!iAutoFillManagerClient.asBinder().unlinkToDeath(this.mClientVulture, 0)) {
            Slog.w(
                    "AutofillSession",
                    "unlinking vulture from death failed for " + this.mActivityToken);
        }
        this.mClientVulture = null;
    }

    public final void unregisterDelayedFillBroadcastLocked() {
        if (this.mDelayedFillBroadcastReceiverRegistered) {
            Slog.v("AutofillSession", "unregisterDelayedFillBroadcastLocked()");
            this.mContext.unregisterReceiver(this.mDelayedFillBroadcastReceiver);
            this.mDelayedFillBroadcastReceiverRegistered = false;
        }
    }

    public final void updateLocked(
            AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i, int i2) {
        AutofillId autofillId2;
        AutofillValue autofillValue2;
        CharSequence textValue;
        AutofillValue autofillValue3;
        if (this.mDestroyed) {
            Slog.w(
                    "AutofillSession",
                    "Call to Session#updateLocked() rejected - session: "
                            + autofillId
                            + " destroyed");
            return;
        }
        String str = null;
        if (i == 5) {
            this.mSessionFlags.mExpiredResponse = true;
            if (Helper.sDebug) {
                Slog.d("AutofillSession", "Set the response has expired.");
            }
            this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                    new PresentationStatsEventLogger$$ExternalSyntheticLambda1());
            this.mPresentationStatsEventLogger.logAndEndEvent();
            AutofillInlineSessionController autofillInlineSessionController =
                    this.mInlineSessionController;
            autofillInlineSessionController.mInlineFillUi = null;
            AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                    autofillInlineSessionController.mSession;
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mInlineFillUi = null;
                return;
            }
            return;
        }
        autofillId.setSessionId(this.id);
        if (Helper.sVerbose) {
            StringBuilder sb = new StringBuilder("updateLocked(");
            sb.append(this.id);
            sb.append("): id=");
            sb.append(autofillId);
            sb.append(", action=");
            sb.append(
                    i != 1
                            ? i != 2
                                    ? i != 3
                                            ? i != 4
                                                    ? i != 5
                                                            ? VibrationParam$1$$ExternalSyntheticOutline0
                                                                    .m(i, "UNKNOWN_")
                                                            : "RESPONSE_EXPIRED"
                                                    : "VALUE_CHANGED"
                                            : "VIEW_EXITED"
                                    : "VIEW_ENTERED"
                            : "START_SESSION");
            sb.append(", flags=");
            sb.append(i2);
            Slog.v("AutofillSession", sb.toString());
        }
        final ViewState viewState = (ViewState) this.mViewStates.get(autofillId);
        if (Helper.sVerbose) {
            Slog.v(
                    "AutofillSession",
                    "updateLocked("
                            + this.id
                            + "): mCurrentViewId="
                            + this.mCurrentViewId
                            + ", mExpiredResponse="
                            + this.mSessionFlags.mExpiredResponse
                            + ", viewState="
                            + viewState);
        }
        if (viewState == null) {
            if (i != 1 && i != 4 && i != 2) {
                if (Helper.sVerbose) {
                    Slog.v("AutofillSession", "Ignoring specific action when viewState=null");
                    return;
                }
                return;
            }
            if (Helper.sVerbose) {
                Slog.v("AutofillSession", "Creating viewState for " + autofillId);
            }
            FillResponse lastResponseLocked = getLastResponseLocked(null);
            boolean contains =
                    lastResponseLocked == null
                            ? false
                            : ArrayUtils.contains(lastResponseLocked.getIgnoredIds(), autofillId);
            ViewState viewState2 =
                    new ViewState(autofillId, this, contains ? 128 : 1, this.mIsPrimaryCredential);
            this.mViewStates.put(autofillId, viewState2);
            if (contains) {
                if (Helper.sDebug) {
                    Slog.d("AutofillSession", "updateLocked(): ignoring view " + viewState2);
                    return;
                }
                return;
            }
            viewState = viewState2;
        }
        if ((i2 & 256) != 0) {
            if (Helper.sDebug) {
                Log.d("AutofillSession", "force to reset fill dialog state");
            }
            this.mSessionFlags.mFillDialogDisabled = false;
        }
        if ((i2 & 512) != 0) {
            requestAssistStructureForPccLocked(i2);
            return;
        }
        if ((i2 & 1024) != 0) {
            this.mSessionFlags.mScreenHasCredmanField = true;
        }
        if (i == 1) {
            AutofillId autofillId3 = viewState.id;
            this.mCurrentViewId = autofillId3;
            this.mPreviousNonNullEnteredViewId = autofillId3;
            if (autofillValue != null) {
                viewState.mCurrentValue = autofillValue;
            }
            if (rect != null) {
                viewState.mVirtualBounds = rect;
            }
            viewState.maybeCallOnFillReady(i2);
            startNewEventForPresentationStatsEventLogger();
            this.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                    new PresentationStatsEventLogger$$ExternalSyntheticLambda0(1));
            if ((i2 & 64) != 0) {
                this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(8);
                this.mPreviouslyFillDialogPotentiallyStarted = true;
            } else {
                this.mSessionFlags.mFillDialogDisabled = true;
                this.mPreviouslyFillDialogPotentiallyStarted = false;
            }
            requestNewFillResponseLocked(viewState, 16, i2);
            return;
        }
        if (i == 2) {
            this.mLatencyBaseTime = SystemClock.elapsedRealtime();
            boolean z = this.mPreviouslyFillDialogPotentiallyStarted;
            this.mPreviouslyFillDialogPotentiallyStarted = false;
            if (Helper.sVerbose && rect != null) {
                Slog.v("AutofillSession", "entered on virtual child " + autofillId + ": " + rect);
            }
            boolean equals = Objects.equals(this.mCurrentViewId, viewState.id);
            AutofillId autofillId4 = viewState.id;
            this.mCurrentViewId = autofillId4;
            if (autofillValue != null) {
                viewState.mCurrentValue = autofillValue;
            }
            boolean z2 = equals || Objects.equals(autofillId4, this.mPreviousNonNullEnteredViewId);
            AutofillId autofillId5 = this.mCurrentViewId;
            if (autofillId5 != null) {
                this.mPreviousNonNullEnteredViewId = autofillId5;
            }
            boolean z3 = (i2 & 2048) != 0;
            if (shouldRequestSecondaryProvider(i2)) {
                if (requestNewFillResponseOnViewEnteredIfNecessaryLocked(
                        autofillId, viewState, i2)) {
                    Slog.v("AutofillSession", "Started a new fill request for secondary provider.");
                    return;
                }
                FillResponse fillResponse = viewState.mSecondaryFillResponse;
                if (fillResponse != null) {
                    logPresentationStatsOnViewEnteredLocked(fillResponse, z3);
                }
                if (autofillValue != null) {
                    viewState.mCurrentValue = autofillValue;
                }
                if (rect != null) {
                    viewState.mVirtualBounds = rect;
                }
                viewState.maybeCallOnFillReady(i2);
                return;
            }
            if (this.mCompatMode && (viewState.mState & 512) != 0) {
                if (Helper.sDebug) {
                    Slog.d(
                            "AutofillSession",
                            "Ignoring VIEW_ENTERED on URL BAR (id=" + autofillId + ")");
                    return;
                }
                return;
            }
            synchronized (this.mLock) {
                try {
                    if (!this.mLogViewEntered) {
                        this.mService.logViewEntered(this.id);
                    }
                    this.mLogViewEntered = true;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (!z && !z2) {
                this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(2);
                this.mPresentationStatsEventLogger.logAndEndEvent();
            }
            if ((i2 & 1) == 0) {
                ArrayList arrayList = this.mAugmentedAutofillableIds;
                if (arrayList != null && arrayList.contains(autofillId)) {
                    if (equals) {
                        if (Helper.sDebug) {
                            Slog.d(
                                    "AutofillSession",
                                    "skip augmented autofill for same view: same view entered");
                            return;
                        }
                        return;
                    } else {
                        if (Helper.sDebug) {
                            Slog.d("AutofillSession", "trigger augmented autofill.");
                        }
                        triggerAugmentedAutofillLocked(i2);
                        return;
                    }
                }
                if (this.mSessionFlags.mAugmentedAutofillOnly && equals) {
                    if (Helper.sDebug) {
                        Slog.d(
                                "AutofillSession",
                                "skip augmented autofill for same view: standard autofill"
                                    + " disabled.");
                        return;
                    }
                    return;
                }
            }
            if (!z) {
                startNewEventForPresentationStatsEventLogger();
            }
            if (requestNewFillResponseOnViewEnteredIfNecessaryLocked(autofillId, viewState, i2)) {
                if (z) {
                    this.mPresentationStatsEventLogger.logAndEndEvent();
                    startNewEventForPresentationStatsEventLogger();
                    return;
                }
                return;
            }
            FillResponse fillResponse2 = viewState.mPrimaryFillResponse;
            if (fillResponse2 != null) {
                logPresentationStatsOnViewEnteredLocked(fillResponse2, z3);
            }
            if (!equals) {
                if (autofillValue != null) {
                    viewState.mCurrentValue = autofillValue;
                }
                if (rect != null) {
                    viewState.mVirtualBounds = rect;
                }
                viewState.maybeCallOnFillReady(i2);
                return;
            }
            DialogFillUi dialogFillUi = getUiForShowing().mFillDialog;
            if (dialogFillUi == null ? false : dialogFillUi.mDialog.isShowing()) {
                setFillDialogDisabled();
                synchronized (this.mLock) {
                    autofillId2 = this.mCurrentViewId;
                }
                requestShowSoftInput(autofillId2);
                return;
            }
            return;
        }
        if (i == 3) {
            if (Objects.equals(this.mCurrentViewId, viewState.id)) {
                if (Helper.sVerbose) {
                    Slog.v("AutofillSession", "Exiting view " + autofillId);
                }
                AutoFillUI autoFillUI = this.mUi;
                autoFillUI.mHandler.post(
                        new AutoFillUI$$ExternalSyntheticLambda1(autoFillUI, this, 3));
                AutoFillUI autoFillUI2 = this.mUi;
                autoFillUI2.mHandler.post(
                        new AutoFillUI$$ExternalSyntheticLambda1(autoFillUI2, this, 1));
                if ((viewState.mState & 4096) != 0) {
                    viewState.resetState(4096);
                    cancelAugmentedAutofillLocked();
                }
                AutofillInlineSessionController autofillInlineSessionController2 =
                        this.mInlineSessionController;
                autofillInlineSessionController2.mInlineFillUi = null;
                AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession2 =
                        autofillInlineSessionController2.mSession;
                if (autofillInlineSuggestionsRequestSession2 != null) {
                    autofillInlineSuggestionsRequestSession2.mInlineFillUi = null;
                }
                if ((viewState.mState
                                & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT)
                        == 0) {
                    this.mCurrentViewId = null;
                }
                this.mPresentationStatsEventLogger.maybeSetNoPresentationEventReason(2);
                return;
            }
            return;
        }
        if (i != 4) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "updateLocked(): unknown action: ", "AutofillSession");
            return;
        }
        if (this.mCompatMode && (viewState.mState & 512) != 0) {
            AssistStructure.ViewNode viewNode = this.mUrlBar;
            String trim = viewNode == null ? null : viewNode.getText().toString().trim();
            if (trim == null) {
                wtf(null, "URL bar value changed, but current value is null", new Object[0]);
                return;
            }
            if (autofillValue == null || !autofillValue.isText()) {
                wtf(null, "URL bar value changed to null or non-text: %s", autofillValue);
                return;
            }
            if (autofillValue.getTextValue().toString().equals(trim)) {
                if (Helper.sDebug) {
                    Slog.d("AutofillSession", "Ignoring change on URL bar as it's the same");
                    return;
                }
                return;
            } else if (this.mSaveOnAllViewsInvisible) {
                if (Helper.sDebug) {
                    Slog.d(
                            "AutofillSession",
                            "Ignoring change on URL because session will finish when views are"
                                + " gone");
                    return;
                }
                return;
            } else {
                if (Helper.sDebug) {
                    Slog.d("AutofillSession", "Finishing session because URL bar changed");
                }
                forceRemoveFromServiceLocked(5);
                return;
            }
        }
        if (Objects.equals(autofillValue, viewState.mCurrentValue)) {
            return;
        }
        if ((autofillValue == null || autofillValue.isEmpty())
                && (autofillValue2 = viewState.mCurrentValue) != null
                && autofillValue2.isText()
                && viewState.mCurrentValue.getTextValue() != null
                && getSaveInfoLocked() != null) {
            int length = viewState.mCurrentValue.getTextValue().length();
            if (Helper.sDebug) {
                Slog.d(
                        "AutofillSession",
                        "updateLocked("
                                + autofillId
                                + "): resetting value that was "
                                + length
                                + " chars long");
            }
            this.mMetricsLogger.write(
                    newLogMaker(1124).addTaggedData(1125, Integer.valueOf(length)));
        }
        if (!this.mIgnoreViewStateResetToEmpty
                || (!(autofillValue == null || autofillValue.isEmpty())
                        || (autofillValue3 = viewState.mCurrentValue) == null
                        || !autofillValue3.isText()
                        || viewState.mCurrentValue.getTextValue() == null
                        || viewState.mCurrentValue.getTextValue().length() <= 1)) {
            viewState.mCandidateSaveValue = null;
        } else {
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillSession",
                        "value is resetting to empty, caching the last non-empty value");
            }
            viewState.mCandidateSaveValue = viewState.mCurrentValue;
        }
        if (autofillValue != null
                && autofillValue.isText()
                && (textValue = autofillValue.getTextValue()) != null) {
            str = textValue.toString();
        }
        String str2 = "";
        String str3 = str == null ? "" : str;
        AutofillValue autofillValue4 = viewState.mCurrentValue;
        if (autofillValue4 != null && autofillValue4.isText()) {
            str2 = autofillValue4.getTextValue().toString();
        }
        if ((viewState.mState & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                == 0) {
            char[] charArray = str2.toCharArray();
            int length2 = charArray.length;
            int i3 = -1;
            int i4 = 0;
            while (true) {
                if (i4 >= length2) {
                    break;
                }
                int indexOf = TextUtils.indexOf(str3, charArray[i4], i3 + 1);
                if (indexOf == -1) {
                    viewState.setState(
                            EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION);
                    break;
                } else {
                    i4++;
                    i3 = indexOf;
                }
            }
        } else {
            char[] charArray2 = str3.toCharArray();
            int length3 = charArray2.length;
            int i5 = -1;
            int i6 = 0;
            while (true) {
                if (i6 >= length3) {
                    break;
                }
                i5 = TextUtils.indexOf(str2, charArray2[i6], i5 + 1);
                if (i5 == -1) {
                    viewState.setState(32768);
                    break;
                }
                i6++;
            }
        }
        viewState.mCurrentValue = autofillValue;
        AutofillValue autofillValue5 = viewState.mAutofilledValue;
        if (autofillValue5 != null) {
            if (autofillValue5.equals(autofillValue)) {
                if (Helper.sVerbose) {
                    Slog.v("AutofillSession", "ignoring autofilled change on id " + autofillId);
                }
                this.mInlineSessionController.hideInlineSuggestionsUiLocked(viewState.id);
                viewState.resetState(8);
                return;
            }
            if (viewState.id.equals(this.mCurrentViewId) && (viewState.mState & 4) != 0) {
                if (Helper.sVerbose) {
                    Slog.v("AutofillSession", "field changed after autofill on id " + autofillId);
                }
                viewState.resetState(4);
                ((ViewState) this.mViewStates.get(this.mCurrentViewId)).maybeCallOnFillReady(i2);
            }
        }
        if (str != null) {
            final PresentationStatsEventLogger presentationStatsEventLogger =
                    this.mPresentationStatsEventLogger;
            final int length4 = str.length();
            presentationStatsEventLogger.mEventInternal.ifPresent(
                    new Consumer() { // from class:
                                     // com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda15
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            AutofillId autofillId6;
                            PresentationStatsEventLogger presentationStatsEventLogger2 =
                                    PresentationStatsEventLogger.this;
                            ViewState viewState3 = viewState;
                            int i7 = length4;
                            PresentationStatsEventLogger.PresentationStatsEventInternal
                                    presentationStatsEventInternal =
                                            (PresentationStatsEventLogger
                                                            .PresentationStatsEventInternal)
                                                    obj;
                            presentationStatsEventLogger2.getClass();
                            int elapsedRealtime =
                                    (int)
                                            (SystemClock.elapsedRealtime()
                                                    - presentationStatsEventLogger2
                                                            .mSessionStartTimestamp);
                            if (viewState3 == null
                                    || (autofillId6 = viewState3.id) == null
                                    || autofillId6.getViewId()
                                            != presentationStatsEventInternal.mFocusedId) {
                                HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("Bad view state for: "),
                                        presentationStatsEventInternal.mFocusedId,
                                        "PresentationStatsEventLogger");
                                return;
                            }
                            if ((viewState3.mState & 4) != 0) {
                                presentationStatsEventInternal.mAutofilledTimestampMs =
                                        elapsedRealtime;
                                return;
                            }
                            if (presentationStatsEventInternal.mFieldFirstLength == -1) {
                                presentationStatsEventInternal.mFieldFirstLength = i7;
                            }
                            presentationStatsEventInternal.mFieldLastLength = i7;
                            if (presentationStatsEventInternal.mFieldModifiedFirstTimestampMs
                                    == -1) {
                                presentationStatsEventInternal.mFieldModifiedFirstTimestampMs =
                                        elapsedRealtime;
                            }
                            presentationStatsEventInternal.mFieldModifiedLastTimestampMs =
                                    elapsedRealtime;
                        }
                    });
        }
        if (viewState.id.equals(this.mCurrentViewId)) {
            int i7 = viewState.mState;
            if ((i7 & 8192) != 0) {
                if ((i7 & 32768) != 0) {
                    AutofillInlineSessionController autofillInlineSessionController3 =
                            this.mInlineSessionController;
                    AutofillId autofillId6 = viewState.id;
                    InlineFillUi inlineFillUi = autofillInlineSessionController3.mInlineFillUi;
                    if (inlineFillUi != null && inlineFillUi.mAutofillId.equals(autofillId6)) {
                        autofillInlineSessionController3.mInlineFillUi.mFilterMatchingDisabled =
                                true;
                    }
                }
                AutofillInlineSessionController autofillInlineSessionController4 =
                        this.mInlineSessionController;
                AutofillId autofillId7 = this.mCurrentViewId;
                InlineFillUi inlineFillUi2 = autofillInlineSessionController4.mInlineFillUi;
                if (inlineFillUi2 != null && inlineFillUi2.mAutofillId.equals(autofillId7)) {
                    autofillInlineSessionController4.mInlineFillUi.mFilterText = str;
                    autofillInlineSessionController4.requestImeToShowInlineSuggestionsLocked();
                }
                viewState.setState(8);
                AutoFillUI uiForShowing = getUiForShowing();
                uiForShowing.mHandler.post(
                        new AutoFillUI$$ExternalSyntheticLambda8(
                                uiForShowing, (AutoFillUI.AutoFillUiCallback) this, str));
            }
        }
        if (viewState.id.equals(this.mCurrentViewId)
                && (viewState.mState & 4096) != 0
                && !TextUtils.isEmpty(str)) {
            this.mInlineSessionController.hideInlineSuggestionsUiLocked(this.mCurrentViewId);
        }
        viewState.setState(8);
        AutoFillUI uiForShowing2 = getUiForShowing();
        uiForShowing2.mHandler.post(
                new AutoFillUI$$ExternalSyntheticLambda8(
                        uiForShowing2, (AutoFillUI.AutoFillUiCallback) this, str));
    }

    public final void updateTrackedIdsLocked() {
        ArraySet arraySet;
        AutofillId autofillId;
        int i;
        boolean z;
        AutofillId[] autofillIdArr;
        AutofillId[] autofillIdArr2 = null;
        FillResponse lastResponseLocked = getLastResponseLocked(null);
        if (lastResponseLocked == null) {
            return;
        }
        this.mSaveOnAllViewsInvisible = false;
        SaveInfo saveInfo = lastResponseLocked.getSaveInfo();
        boolean z2 = true;
        if (saveInfo != null) {
            AutofillId triggerId = saveInfo.getTriggerId();
            if (triggerId != null) {
                this.mMetricsLogger.write(newLogMaker(1228));
                this.mSaveEventLogger.mEventInternal.ifPresent(
                        new SaveEventLogger$$ExternalSyntheticLambda1(3, 3));
            }
            i = saveInfo.getFlags();
            this.mSaveOnAllViewsInvisible = (i & 1) != 0;
            this.mFillResponseEventLogger.mEventInternal.ifPresent(
                    new FillResponseEventLogger$$ExternalSyntheticLambda0());
            this.mSaveEventLogger.mEventInternal.ifPresent(
                    new SaveEventLogger$$ExternalSyntheticLambda1(
                            lastResponseLocked.getRequestId(), 2));
            this.mSaveEventLogger.mEventInternal.ifPresent(
                    new SaveEventLogger$$ExternalSyntheticLambda1(this.uid, 0));
            this.mSaveEventLogger.mEventInternal.ifPresent(
                    new SaveEventLogger$$ExternalSyntheticLambda1());
            this.mSaveEventLogger.mEventInternal.ifPresent(
                    new SaveEventLogger$$ExternalSyntheticLambda1(i, 1));
            if (this.mSaveOnAllViewsInvisible) {
                arraySet = new ArraySet();
                if (saveInfo.getRequiredIds() != null) {
                    Collections.addAll(arraySet, saveInfo.getRequiredIds());
                    this.mSaveEventLogger.mEventInternal.ifPresent(
                            new SaveEventLogger$$ExternalSyntheticLambda1(1, 3));
                }
                if (saveInfo.getOptionalIds() != null) {
                    Collections.addAll(arraySet, saveInfo.getOptionalIds());
                    this.mSaveEventLogger.mEventInternal.ifPresent(
                            new SaveEventLogger$$ExternalSyntheticLambda1(2, 3));
                }
            } else {
                arraySet = null;
            }
            if ((i & 2) != 0) {
                this.mSaveEventLogger.mEventInternal.ifPresent(
                        new SaveEventLogger$$ExternalSyntheticLambda1(0, 3));
                this.mSaveEventLogger.maybeSetSaveUiNotShownReason(8);
                z = false;
            } else {
                z = true;
            }
            autofillId = triggerId;
        } else {
            this.mSaveEventLogger.maybeSetSaveUiNotShownReason(2);
            arraySet = null;
            autofillId = null;
            i = 0;
            z = true;
        }
        List datasets = lastResponseLocked.getDatasets();
        ArraySet arraySet2 = null;
        if (datasets != null) {
            for (int i2 = 0; i2 < datasets.size(); i2++) {
                ArrayList fieldIds = ((Dataset) datasets.get(i2)).getFieldIds();
                if (fieldIds != null) {
                    for (int i3 = 0; i3 < fieldIds.size(); i3++) {
                        AutofillId autofillId2 = (AutofillId) fieldIds.get(i3);
                        if (autofillId2 != null
                                && (arraySet == null || !arraySet.contains(autofillId2))) {
                            arraySet2 = ArrayUtils.add(arraySet2, autofillId2);
                        }
                    }
                }
            }
        }
        try {
            if (Helper.sVerbose) {
                StringBuilder sb = new StringBuilder();
                sb.append("updateTrackedIdsLocked(): trackedViews: ");
                sb.append(arraySet);
                sb.append(" fillableIds: ");
                sb.append(arraySet2);
                sb.append(" triggerId: ");
                sb.append(autofillId);
                sb.append(" saveOnFinish:");
                sb.append(z);
                sb.append(" flags: ");
                sb.append(i);
                sb.append(" hasSaveInfo: ");
                if (saveInfo == null) {
                    z2 = false;
                }
                sb.append(z2);
                Slog.v("AutofillSession", sb.toString());
            }
            IAutoFillManagerClient iAutoFillManagerClient = this.mClient;
            int i4 = this.id;
            if (arraySet == null) {
                autofillIdArr = null;
            } else {
                AutofillId[] autofillIdArr3 = new AutofillId[arraySet.size()];
                for (int i5 = 0; i5 < arraySet.size(); i5++) {
                    autofillIdArr3[i5] = (AutofillId) arraySet.valueAt(i5);
                }
                autofillIdArr = autofillIdArr3;
            }
            boolean z3 = this.mSaveOnAllViewsInvisible;
            if (arraySet2 != null) {
                autofillIdArr2 = new AutofillId[arraySet2.size()];
                for (int i6 = 0; i6 < arraySet2.size(); i6++) {
                    autofillIdArr2[i6] = (AutofillId) arraySet2.valueAt(i6);
                }
            }
            iAutoFillManagerClient.setTrackedViews(
                    i4, autofillIdArr, z3, z, autofillIdArr2, autofillId);
        } catch (RemoteException e) {
            Slog.w("AutofillSession", "Cannot set tracked ids", e);
        }
    }

    public final void updateValuesForSaveLocked() {
        ArrayMap createSanitizers = Helper.createSanitizers(getSaveInfoLocked());
        int size = this.mContexts.size();
        for (int i = 0; i < size; i++) {
            FillContext fillContext = (FillContext) this.mContexts.get(i);
            int size2 = this.mViewStates.size();
            AutofillId[] autofillIdArr = new AutofillId[size2];
            for (int i2 = 0; i2 < size2; i2++) {
                autofillIdArr[i2] = ((ViewState) this.mViewStates.valueAt(i2)).id;
            }
            AssistStructure.ViewNode[] findViewNodesByAutofillIds =
                    fillContext.findViewNodesByAutofillIds(autofillIdArr);
            if (Helper.sVerbose) {
                Slog.v("AutofillSession", "updateValuesForSaveLocked(): updating " + fillContext);
            }
            for (int i3 = 0; i3 < this.mViewStates.size(); i3++) {
                ViewState viewState = (ViewState) this.mViewStates.valueAt(i3);
                AutofillId autofillId = viewState.id;
                AutofillValue autofillValue = viewState.mCurrentValue;
                if (autofillValue != null) {
                    AssistStructure.ViewNode viewNode = findViewNodesByAutofillIds[i3];
                    if (viewNode == null) {
                        Slog.w(
                                "AutofillSession",
                                "callSaveLocked(): did not find node with id " + autofillId);
                    } else {
                        if (Helper.sVerbose) {
                            Slog.v(
                                    "AutofillSession",
                                    "updateValuesForSaveLocked(): updating "
                                            + autofillId
                                            + " to "
                                            + autofillValue);
                        }
                        AutofillValue autofillValue2 = viewState.mSanitizedValue;
                        if (autofillValue2 == null) {
                            autofillValue2 =
                                    getSanitizedValue(createSanitizers, autofillId, autofillValue);
                        }
                        if (autofillValue2 != null) {
                            viewNode.updateAutofillValue(autofillValue2);
                        } else if (Helper.sDebug) {
                            Slog.d(
                                    "AutofillSession",
                                    "updateValuesForSaveLocked(): not updating field "
                                            + autofillId
                                            + " because it failed sanitization");
                        }
                    }
                } else if (Helper.sVerbose) {
                    Slog.v(
                            "AutofillSession",
                            "updateValuesForSaveLocked(): skipping " + autofillId);
                }
            }
            fillContext.getStructure().sanitizeForParceling(false);
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillSession",
                        "updateValuesForSaveLocked(): dumping structure of "
                                + fillContext
                                + " before calling service.save()");
                fillContext.getStructure().dump(false);
            }
        }
    }

    public final void wtf(Exception exc, String str, Object... objArr) {
        String format = String.format(str, objArr);
        synchronized (this.mLock) {
            this.mWtfHistory.log(format);
        }
        if (exc != null) {
            Slog.wtf("AutofillSession", format, exc);
        } else {
            Slog.wtf("AutofillSession", format);
        }
    }
}
