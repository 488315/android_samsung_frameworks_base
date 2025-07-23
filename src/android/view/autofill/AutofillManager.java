package android.view.autofill;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SystemApi;
import android.app.ActivityOptions;
import android.app.assist.AssistStructure;
import android.content.AutofillOptions;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.credentials.GetCredentialException;
import android.credentials.GetCredentialResponse;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.metrics.LogMaker;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.service.autofill.FillEventHistory;
import android.service.autofill.Flags;
import android.service.autofill.UserData;
import android.service.credentials.CredentialProviderService;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.ContentInfo;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.autofill.AutofillManager;
import android.view.autofill.IAugmentedAutofillManagerClient;
import android.view.autofill.IAutoFillManagerClient;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.os.IResultReceiver;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.SyncResultReceiver;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.xmlpull.v1.XmlPullParserException;
import sun.misc.Cleaner;

/* loaded from: classes4.dex */
public final class AutofillManager {
    public static final int ACTION_RESPONSE_EXPIRED = 5;
    public static final int ACTION_START_SESSION = 1;
    public static final int ACTION_VALUE_CHANGED = 4;
    public static final int ACTION_VIEW_ENTERED = 2;
    public static final int ACTION_VIEW_EXITED = 3;
    public static final String ANY_HINT = "any";
    private static final int AUTHENTICATION_ID_DATASET_ID_MASK = 65535;
    private static final int AUTHENTICATION_ID_DATASET_ID_SHIFT = 16;
    public static final int AUTHENTICATION_ID_DATASET_ID_UNDEFINED = 65535;
    public static final int COMMIT_REASON_ACTIVITY_FINISHED = 1;
    public static final int COMMIT_REASON_SESSION_DESTROYED = 5;
    public static final int COMMIT_REASON_UNKNOWN = 0;
    public static final int COMMIT_REASON_VIEW_CHANGED = 4;
    public static final int COMMIT_REASON_VIEW_CLICKED = 3;
    public static final int COMMIT_REASON_VIEW_COMMITTED = 2;
    private static final boolean DBG = false;
    public static final int DEFAULT_LOGGING_LEVEL;
    public static final int DEFAULT_MAX_PARTITIONS_SIZE = 10;
    public static final String EXTRA_ASSIST_STRUCTURE = "android.view.autofill.extra.ASSIST_STRUCTURE";
    public static final String EXTRA_AUGMENTED_AUTOFILL_CLIENT = "android.view.autofill.extra.AUGMENTED_AUTOFILL_CLIENT";
    public static final String EXTRA_AUTHENTICATION_RESULT = "android.view.autofill.extra.AUTHENTICATION_RESULT";
    public static final String EXTRA_AUTHENTICATION_RESULT_EPHEMERAL_DATASET = "android.view.autofill.extra.AUTHENTICATION_RESULT_EPHEMERAL_DATASET";
    public static final String EXTRA_AUTH_STATE = "android.view.autofill.extra.AUTH_STATE";
    public static final String EXTRA_AUTOFILL_REQUEST_ID = "android.view.autofill.extra.AUTOFILL_REQUEST_ID";
    public static final String EXTRA_CLIENT_STATE = "android.view.autofill.extra.CLIENT_STATE";
    public static final String EXTRA_INLINE_SUGGESTIONS_REQUEST = "android.view.autofill.extra.INLINE_SUGGESTIONS_REQUEST";
    public static final String EXTRA_RESTORE_CROSS_ACTIVITY = "android.view.autofill.extra.RESTORE_CROSS_ACTIVITY";
    public static final String EXTRA_RESTORE_SESSION_TOKEN = "android.view.autofill.extra.RESTORE_SESSION_TOKEN";
    public static final int FC_SERVICE_TIMEOUT = 5000;
    public static final int FLAG_ADD_CLIENT_DEBUG = 2;
    public static final int FLAG_ADD_CLIENT_ENABLED = 1;
    public static final int FLAG_ADD_CLIENT_ENABLED_FOR_AUGMENTED_AUTOFILL_ONLY = 8;
    public static final int FLAG_ADD_CLIENT_VERBOSE = 4;
    public static final int FLAG_SMART_SUGGESTION_OFF = 0;
    public static final int FLAG_SMART_SUGGESTION_SYSTEM = 1;
    private static final String LAST_AUTOFILLED_DATA_TAG = "android:lastAutoFilledData";
    public static final int MAX_TEMP_AUGMENTED_SERVICE_DURATION_MS = 120000;
    public static final int NO_LOGGING = 0;
    public static final int NO_SESSION = Integer.MAX_VALUE;
    public static final int PENDING_UI_OPERATION_CANCEL = 1;
    public static final int PENDING_UI_OPERATION_RESTORE = 2;
    public static final String PINNED_DATASET_ID = "PINNED_DATASET_ID";
    public static final int RECEIVER_FLAG_SESSION_FOR_AUGMENTED_AUTOFILL_ONLY = 1;
    public static final int RESULT_CODE_NOT_SERVICE = -1;
    public static final int RESULT_OK = 0;
    private static final String SESSION_ID_TAG = "android:sessionId";
    public static final int SET_STATE_FLAG_DEBUG = 8;
    public static final int SET_STATE_FLAG_ENABLED = 1;
    public static final int SET_STATE_FLAG_FOR_AUTOFILL_ONLY = 32;
    public static final int SET_STATE_FLAG_RESET_CLIENT = 4;
    public static final int SET_STATE_FLAG_RESET_SESSION = 2;
    public static final int SET_STATE_FLAG_VERBOSE = 16;
    public static final int STATE_ACTIVE = 1;
    public static final int STATE_DISABLED_BY_SERVICE = 4;
    public static final int STATE_FINISHED = 2;
    public static final int STATE_PENDING_AUTHENTICATION = 7;
    public static final int STATE_SHOWING_SAVE_UI = 3;
    private static final String STATE_TAG = "android:state";
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_UNKNOWN_COMPAT_MODE = 5;
    public static final int STATE_UNKNOWN_FAILED = 6;
    private static final int SYNC_CALLS_TIMEOUT_MS = 5000;
    private static final String TAG = "AutofillManager";
    private Set<String> mAllowedActivitySet;
    private IAugmentedAutofillManagerClient mAugmentedAutofillServiceClient;
    private AutofillStateFingerprint mAutofillStateFingerprint;
    private AutofillCallback mCallback;
    private CompatibilityBridge mCompatibilityBridge;
    private final Context mContext;
    private Set<String> mDeniedActivitySet;
    private boolean mEnabled;
    private boolean mEnabledForAugmentedAutofillOnly;
    private Set<AutofillId> mEnteredForAugmentedAutofillIds;
    private ArraySet<AutofillId> mEnteredIds;
    private final String[] mFillDialogEnabledHints;
    private List<AutofillId> mFillDialogTriggerIds;
    private ArraySet<AutofillId> mFillableIds;
    private boolean mForAugmentedAutofillOnly;
    private AutofillId mIdShownFillUi;
    private boolean mImproveFillDialogEnabled;
    private final boolean mIsCredmanIntegrationEnabled;
    private final boolean mIsFillAndSaveDialogDisabledForCredentialManager;
    private final boolean mIsFillDialogEnabled;
    private AtomicBoolean mIsFillRequested;
    private boolean mIsPackageFullyAllowedForAutofill;
    private boolean mIsPackageFullyDeniedForAutofill;
    private boolean mIsPackagePartiallyAllowedForAutofill;
    private boolean mIsPackagePartiallyDeniedForAutofill;
    private boolean mIsTriggerFillRequestOnFilteredImportantViewsEnabled;
    private boolean mIsTriggerFillRequestOnUnimportantViewEnabled;
    private ParcelableMap mLastAutofilledData;
    private Set<String> mNonAutofillableImeActionIdSet;
    private boolean mOnInvisibleCalled;
    private final AutofillOptions mOptions;
    private final boolean mRelativePositionForRelayout;
    private final boolean mRelayoutFix;
    private boolean mRelayoutFixDeprecated;
    private boolean mSaveOnFinish;
    private AutofillId mSaveTriggerId;
    private boolean mScreenHasCredmanField;
    private final IAutoFillManager mService;
    private IAutoFillManagerClient mServiceClient;
    private Cleaner mServiceClientCleaner;
    private boolean mShouldAlwaysIncludeWebviewInAssistStructure;
    private boolean mShouldEnableAutofillOnAllViewTypes;
    private boolean mShouldEnableMultilineFilter;
    private boolean mShouldIncludeAllChildrenViewInAssistStructure;
    private boolean mShouldIncludeAllViewsWithAutofillTypeNotNoneInAssistStructure;
    private boolean mShouldIncludeInvisibleViewInAssistStructure;
    private TrackedViews mTrackedViews;
    private final MetricsLogger mMetricsLogger = new MetricsLogger();
    private final Object mLock = new Object();
    private int mSessionId = Integer.MAX_VALUE;
    private int mState = 0;
    private boolean mShowAutofillDialogCalled = false;
    private boolean mShouldIgnoreCredentialViews = false;
    private final ArraySet<AutofillId> mAllTrackedViews = new ArraySet<>();
    private boolean mFillReAttemptNeeded = false;
    private Map<Integer, AutofillId> mFingerprintToViewMap = new ArrayMap();

    public static abstract class AutofillCallback {
        public static final int EVENT_INPUT_HIDDEN = 2;
        public static final int EVENT_INPUT_SHOWN = 1;
        public static final int EVENT_INPUT_UNAVAILABLE = 3;

        @Retention(RetentionPolicy.SOURCE)
        public @interface AutofillEventType {
        }

        public void onAutofillEvent(View view, int i) {
        }

        public void onAutofillEvent(View view, int i, int i2) {
        }
    }

    public interface AutofillClient {
        void autofillClientAuthenticate(int i, IntentSender intentSender, Intent intent, boolean z);

        void autofillClientDispatchUnhandledKey(View view, KeyEvent keyEvent);

        List<View> autofillClientFindAutofillableViewsByTraversal();

        View autofillClientFindViewByAccessibilityIdTraversal(int i, int i2);

        View autofillClientFindViewByAutofillIdTraversal(AutofillId autofillId);

        View[] autofillClientFindViewsByAutofillIdTraversal(AutofillId[] autofillIdArr);

        IBinder autofillClientGetActivityToken();

        ComponentName autofillClientGetComponentName();

        AutofillId autofillClientGetNextAutofillId();

        boolean[] autofillClientGetViewVisibility(AutofillId[] autofillIdArr);

        boolean autofillClientIsCompatibilityModeEnabled();

        boolean autofillClientIsFillUiShowing();

        boolean autofillClientIsVisibleForAutofill();

        boolean autofillClientRequestHideFillUi();

        boolean autofillClientRequestShowFillUi(View view, int i, int i2, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter);

        void autofillClientResetableStateAvailable();

        void autofillClientRunOnUiThread(Runnable runnable);

        boolean isActivityResumed();

        boolean isDisablingEnterExitEventForAutofill();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutofillCommitReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SmartSuggestionMode {
    }

    public static int getDatasetIdFromAuthenticationId(int i) {
        return i & 65535;
    }

    public static int getRequestIdFromAuthenticationId(int i) {
        return i >> 16;
    }

    public static int makeAuthenticationId(int i, int i2) {
        return (i << 16) | (i2 & 65535);
    }

    static {
        DEFAULT_LOGGING_LEVEL = Build.IS_DEBUGGABLE ? 2 : 0;
    }

    public AutofillManager(Context context, IAutoFillManager iAutoFillManager) {
        boolean z = false;
        this.mIsTriggerFillRequestOnUnimportantViewEnabled = false;
        this.mNonAutofillableImeActionIdSet = new ArraySet();
        this.mIsPackageFullyDeniedForAutofill = false;
        this.mIsPackagePartiallyDeniedForAutofill = false;
        this.mDeniedActivitySet = new ArraySet();
        this.mIsPackageFullyAllowedForAutofill = false;
        this.mIsPackagePartiallyAllowedForAutofill = false;
        this.mAllowedActivitySet = new ArraySet();
        Context context2 = (Context) Objects.requireNonNull(context, "context cannot be null");
        this.mContext = context2;
        this.mService = iAutoFillManager;
        AutofillOptions autofillOptions = context.getAutofillOptions();
        this.mOptions = autofillOptions;
        this.mIsFillRequested = new AtomicBoolean(false);
        this.mAutofillStateFingerprint = AutofillStateFingerprint.createInstance();
        boolean isFillDialogEnabled = AutofillFeatureFlags.isFillDialogEnabled();
        this.mIsFillDialogEnabled = isFillDialogEnabled;
        String[] fillDialogEnabledHints = AutofillFeatureFlags.getFillDialogEnabledHints();
        this.mFillDialogEnabledHints = fillDialogEnabledHints;
        this.mIsFillAndSaveDialogDisabledForCredentialManager = AutofillFeatureFlags.isFillAndSaveDialogDisabledForCredentialManager();
        if (Helper.sDebug) {
            Log.d("AutofillManager", "Fill dialog is enabled:" + isFillDialogEnabled + ", hints=" + Arrays.toString(fillDialogEnabledHints));
        }
        if (autofillOptions != null) {
            Helper.sDebug = (autofillOptions.loggingLevel & 2) != 0;
            Helper.sVerbose = (autofillOptions.loggingLevel & 4) != 0;
        }
        this.mIsTriggerFillRequestOnUnimportantViewEnabled = AutofillFeatureFlags.isTriggerFillRequestOnUnimportantViewEnabled();
        this.mIsTriggerFillRequestOnFilteredImportantViewsEnabled = AutofillFeatureFlags.isTriggerFillRequestOnFilteredImportantViewsEnabled();
        this.mShouldEnableAutofillOnAllViewTypes = AutofillFeatureFlags.shouldEnableAutofillOnAllViewTypes();
        this.mNonAutofillableImeActionIdSet = AutofillFeatureFlags.getNonAutofillableImeActionIdSetFromFlag();
        this.mShouldEnableMultilineFilter = AutofillFeatureFlags.shouldEnableMultilineFilter();
        String denylistStringFromFlag = AutofillFeatureFlags.getDenylistStringFromFlag();
        String allowlistStringFromFlag = AutofillFeatureFlags.getAllowlistStringFromFlag();
        String packageName = context2.getPackageName();
        this.mIsPackageFullyDeniedForAutofill = isPackageFullyAllowedOrDeniedForAutofill(denylistStringFromFlag, packageName);
        this.mIsPackageFullyAllowedForAutofill = isPackageFullyAllowedOrDeniedForAutofill(allowlistStringFromFlag, packageName);
        if (!this.mIsPackageFullyDeniedForAutofill) {
            this.mIsPackagePartiallyDeniedForAutofill = isPackagePartiallyDeniedOrAllowedForAutofill(denylistStringFromFlag, packageName);
        }
        if (!this.mIsPackageFullyAllowedForAutofill) {
            this.mIsPackagePartiallyAllowedForAutofill = isPackagePartiallyDeniedOrAllowedForAutofill(allowlistStringFromFlag, packageName);
        }
        if (this.mIsPackagePartiallyDeniedForAutofill) {
            this.mDeniedActivitySet = getDeniedOrAllowedActivitySetFromString(denylistStringFromFlag, packageName);
        }
        if (this.mIsPackagePartiallyAllowedForAutofill) {
            this.mAllowedActivitySet = getDeniedOrAllowedActivitySetFromString(allowlistStringFromFlag, packageName);
        }
        this.mShouldIncludeAllViewsWithAutofillTypeNotNoneInAssistStructure = AutofillFeatureFlags.shouldIncludeAllViewsAutofillTypeNotNoneInAssistStructrue();
        this.mShouldIncludeAllChildrenViewInAssistStructure = AutofillFeatureFlags.shouldIncludeAllChildrenViewInAssistStructure();
        this.mShouldAlwaysIncludeWebviewInAssistStructure = AutofillFeatureFlags.shouldAlwaysIncludeWebviewInAssistStructure();
        this.mShouldIncludeInvisibleViewInAssistStructure = AutofillFeatureFlags.shouldIncludeInvisibleViewInAssistStructure();
        this.mRelayoutFixDeprecated = AutofillFeatureFlags.shouldIgnoreRelayoutWhenAuthPending();
        this.mRelayoutFix = Flags.relayoutFix() && AutofillFeatureFlags.enableRelayoutFixes();
        this.mRelativePositionForRelayout = AutofillFeatureFlags.enableRelativeLocationForRelayout();
        this.mIsCredmanIntegrationEnabled = Flags.autofillCredmanIntegration();
        if (Flags.improveFillDialogAconfig() && AutofillFeatureFlags.isImproveFillDialogEnabled()) {
            z = true;
        }
        this.mImproveFillDialogEnabled = z;
    }

    public boolean isImproveFillDialogEnabled() {
        return this.mImproveFillDialogEnabled;
    }

    public boolean isRelayoutFixEnabled() {
        return this.mRelayoutFix;
    }

    public boolean isRelativePositionForRelayoutEnabled() {
        return this.mRelativePositionForRelayout;
    }

    public boolean isTriggerFillRequestOnFilteredImportantViewsEnabled() {
        return this.mIsTriggerFillRequestOnFilteredImportantViewsEnabled;
    }

    public boolean isTriggerFillRequestOnUnimportantViewEnabled() {
        return this.mIsTriggerFillRequestOnUnimportantViewEnabled;
    }

    private boolean isPassingImeActionCheck(EditText editText) {
        if (!this.mNonAutofillableImeActionIdSet.contains(String.valueOf(editText.getImeOptions()))) {
            return true;
        }
        Log.d("AutofillManager", "view not autofillable - not passing ime action check");
        return false;
    }

    private boolean isPassingMultilineCheck(EditText editText) {
        if (editText.getMinLines() <= 1) {
            return true;
        }
        Log.d("AutofillManager", "view not autofillable - has multiline input type");
        return false;
    }

    private boolean isPackageFullyAllowedOrDeniedForAutofill(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(":;");
        return str.indexOf(sb.toString()) != -1;
    }

    private boolean isPackagePartiallyDeniedOrAllowedForAutofill(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(":");
        return str.indexOf(sb.toString()) != -1;
    }

    public boolean shouldIncludeAllChildrenViewsWithAutofillTypeNotNoneInAssistStructure() {
        return this.mShouldIncludeAllViewsWithAutofillTypeNotNoneInAssistStructure;
    }

    public boolean shouldIncludeAllChildrenViewInAssistStructure() {
        return this.mShouldIncludeAllChildrenViewInAssistStructure;
    }

    public boolean shouldAlwaysIncludeWebviewInAssistStructure() {
        return this.mShouldAlwaysIncludeWebviewInAssistStructure;
    }

    public boolean shouldIncludeInvisibleViewInAssistStructure() {
        return this.mShouldIncludeInvisibleViewInAssistStructure;
    }

    private Set<String> getDeniedOrAllowedActivitySetFromString(String str, String str2) {
        int indexOf = str.indexOf(str2 + ":");
        int indexOf2 = str.indexOf(NavigationBarInflaterView.GRAVITY_SEPARATOR, indexOf);
        int length = indexOf + str2.length() + 1;
        if (length >= indexOf2) {
            Log.e("AutofillManager", "Failed to get denied activity names from list because it's wrongly formatted");
            return new ArraySet();
        }
        return new ArraySet(Arrays.asList(str.substring(length, indexOf2).split(",")));
    }

    public boolean isActivityDeniedForAutofill() {
        AutofillClient client;
        if (this.mIsPackageFullyDeniedForAutofill) {
            return true;
        }
        if (!this.mIsPackagePartiallyDeniedForAutofill || (client = getClient()) == null) {
            return false;
        }
        return this.mDeniedActivitySet.contains(client.autofillClientGetComponentName().flattenToShortString());
    }

    public boolean isActivityAllowedForAutofill() {
        AutofillClient client;
        if (this.mIsPackageFullyAllowedForAutofill) {
            return true;
        }
        if (!this.mIsPackagePartiallyAllowedForAutofill || (client = getClient()) == null) {
            return false;
        }
        return this.mAllowedActivitySet.contains(client.autofillClientGetComponentName().flattenToShortString());
    }

    public boolean isAutofillable(View view) {
        if (view.getAutofillType() == 0) {
            return false;
        }
        if (!view.isImportantForAutofill() && isActivityDeniedForAutofill()) {
            return false;
        }
        if (isActivityAllowedForAutofill()) {
            return true;
        }
        if (view instanceof EditText) {
            if (!this.mShouldEnableMultilineFilter || isPassingMultilineCheck((EditText) view)) {
                return isPassingImeActionCheck((EditText) view);
            }
            return false;
        }
        if (view.isImportantForAutofill() || this.mShouldEnableAutofillOnAllViewTypes || (view instanceof CheckBox) || (view instanceof Spinner) || (view instanceof DatePicker) || (view instanceof TimePicker) || (view instanceof RadioGroup)) {
            return true;
        }
        Log.d("AutofillManager", "view is not autofillable - not important and filtered by view type check");
        return false;
    }

    public void enableCompatibilityMode() {
        synchronized (this.mLock) {
            if (Helper.sDebug) {
                Slog.d("AutofillManager", "creating CompatibilityBridge for " + this.mContext);
            }
            this.mCompatibilityBridge = new CompatibilityBridge();
        }
    }

    public void onCreate(Bundle bundle) {
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                this.mLastAutofilledData = (ParcelableMap) bundle.getParcelable(LAST_AUTOFILLED_DATA_TAG, ParcelableMap.class);
                if (isActiveLocked()) {
                    Log.w("AutofillManager", "New session was started before onCreate()");
                    return;
                }
                this.mSessionId = bundle.getInt(SESSION_ID_TAG, Integer.MAX_VALUE);
                this.mState = bundle.getInt(STATE_TAG, 0);
                if (this.mSessionId != Integer.MAX_VALUE) {
                    boolean tryAddServiceClientIfNeededLocked = tryAddServiceClientIfNeededLocked();
                    AutofillClient client = getClient();
                    if (client != null) {
                        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
                        try {
                            if (tryAddServiceClientIfNeededLocked) {
                                this.mService.restoreSession(this.mSessionId, client.autofillClientGetActivityToken(), this.mServiceClient.asBinder(), syncResultReceiver);
                                if (syncResultReceiver.getIntResult() == 1) {
                                    if (Helper.sDebug) {
                                        Log.d("AutofillManager", "session " + this.mSessionId + " was restored");
                                    }
                                    client.autofillClientResetableStateAvailable();
                                }
                            } else {
                                Log.w("AutofillManager", "No service client for session " + this.mSessionId);
                            }
                            Log.w("AutofillManager", "Session " + this.mSessionId + " could not be restored");
                            this.mSessionId = Integer.MAX_VALUE;
                            this.mState = 0;
                        } catch (RemoteException e) {
                            Log.e("AutofillManager", "Could not figure out if there was an autofill session", e);
                        } catch (SyncResultReceiver.TimeoutException e2) {
                            Log.e("AutofillManager", "Fail to get session restore status: " + e2);
                        }
                    }
                }
            }
        }
    }

    public void onVisibleForAutofill() {
        Choreographer.getInstance().postCallback(4, new Runnable() { // from class: android.view.autofill.AutofillManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                AutofillManager.this.lambda$onVisibleForAutofill$0();
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVisibleForAutofill$0() {
        TrackedViews trackedViews;
        synchronized (this.mLock) {
            if (this.mEnabled && isActiveLocked() && (trackedViews = this.mTrackedViews) != null) {
                trackedViews.onVisibleForAutofillChangedLocked();
            }
        }
    }

    public void onInvisibleForAutofill(boolean z) {
        synchronized (this.mLock) {
            this.mOnInvisibleCalled = true;
            if (z) {
                if (this.mRelayoutFix && isAuthenticationPending()) {
                    Log.i("AutofillManager", "onInvisibleForAutofill(): Ignoring expiringResponse due to pending authentication");
                    try {
                        this.mService.notifyNotExpiringResponseDuringAuth(this.mSessionId, this.mContext.getUserId());
                    } catch (RemoteException unused) {
                    }
                } else {
                    Log.i("AutofillManager", "onInvisibleForAutofill(): expiringResponse");
                    updateSessionLocked(null, null, null, 5, 0);
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                int i = this.mSessionId;
                if (i != Integer.MAX_VALUE) {
                    bundle.putInt(SESSION_ID_TAG, i);
                }
                int i2 = this.mState;
                if (i2 != 0) {
                    bundle.putInt(STATE_TAG, i2);
                }
                ParcelableMap parcelableMap = this.mLastAutofilledData;
                if (parcelableMap != null) {
                    bundle.putParcelable(LAST_AUTOFILLED_DATA_TAG, parcelableMap);
                }
            }
        }
    }

    public boolean isCompatibilityModeEnabledLocked() {
        return this.mCompatibilityBridge != null;
    }

    public boolean isEnabled() {
        if (!hasAutofillFeature()) {
            return false;
        }
        synchronized (this.mLock) {
            if (isDisabledByServiceLocked()) {
                return false;
            }
            return tryAddServiceClientIfNeededLocked() ? this.mEnabled : false;
        }
    }

    public FillEventHistory getFillEventHistory() {
        try {
            SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
            this.mService.getFillEventHistory(syncResultReceiver);
            return (FillEventHistory) syncResultReceiver.getParcelableResult();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException e2) {
            Log.e("AutofillManager", "Fail to get fill event history: " + e2);
            return null;
        }
    }

    public void requestAutofill(View view) {
        notifyViewEntered(view, !view.isFocused() ? 17 : 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void requestAutofillFromNewSession(View view) {
        cancel();
        notifyViewEntered(view);
    }

    public void requestAutofill(View view, int i, Rect rect) {
        notifyViewEntered(view, i, rect, !view.isFocused() ? 17 : 1);
    }

    public boolean isAuthenticationPending() {
        return this.mState == 7;
    }

    public void notifyViewEnteredIgnoredDuringAuthCount() {
        try {
            this.mService.notifyViewEnteredIgnoredDuringAuthCount(this.mSessionId, this.mContext.getUserId());
        } catch (RemoteException unused) {
        }
    }

    public boolean shouldRetryFill() {
        boolean z;
        synchronized (this.mLock) {
            z = isAuthenticationPending() && this.mFillReAttemptNeeded;
        }
        return z;
    }

    public boolean attemptRefill() {
        Log.i("AutofillManager", "Attempting refill");
        List<View> autofillClientFindAutofillableViewsByTraversal = getClient().autofillClientFindAutofillableViewsByTraversal();
        if (Helper.sDebug) {
            Log.d("AutofillManager", "Autofillable views count:" + autofillClientFindAutofillableViewsByTraversal.size());
        }
        return this.mAutofillStateFingerprint.attemptRefill(autofillClientFindAutofillableViewsByTraversal, this);
    }

    public void notifyViewEntered(View view) {
        notifyViewEntered(view, 0);
    }

    @Deprecated
    public void notifyVirtualViewsReady(View view, SparseArray<VirtualViewFillInfo> sparseArray) {
        boolean z;
        Objects.requireNonNull(sparseArray);
        if (sparseArray.size() == 0) {
            throw new IllegalArgumentException("No VirtualViewInfo found");
        }
        if (shouldSuppressDialogsForCredman(view) && this.mIsFillAndSaveDialogDisabledForCredentialManager) {
            z = true;
            this.mScreenHasCredmanField = true;
            if (isCredmanRequested(view)) {
                if (Helper.sDebug) {
                    Log.d("AutofillManager", "Prefetching fill response for credMan: " + view.getAutofillId().toString());
                }
            } else {
                if (Helper.sDebug) {
                    Log.d("AutofillManager", "Ignoring Fill Dialog request since important for credMan:" + view.getAutofillId().toString());
                    return;
                }
                return;
            }
        } else {
            z = false;
        }
        for (int i = 0; i < sparseArray.size(); i++) {
            VirtualViewFillInfo valueAt = sparseArray.valueAt(i);
            notifyViewReadyInner(getAutofillId(view, sparseArray.keyAt(i)), valueAt == null ? null : valueAt.getAutofillHints(), z);
        }
    }

    public void notifyViewEnteredForFillDialog(View view) {
        boolean z;
        if (shouldSuppressDialogsForCredman(view) && this.mIsFillAndSaveDialogDisabledForCredentialManager) {
            z = true;
            this.mScreenHasCredmanField = true;
            if (isCredmanRequested(view)) {
                if (Helper.sDebug) {
                    Log.d("AutofillManager", "Prefetching fill response for credMan: " + view.getAutofillId().toString());
                }
            } else {
                if (Helper.sDebug) {
                    Log.d("AutofillManager", "Ignoring Fill Dialog request since important for credMan:" + view.getAutofillId().toString());
                    return;
                }
                return;
            }
        } else {
            z = false;
        }
        notifyViewReadyInner(view.getAutofillId(), view.getAutofillHints(), z);
    }

    private void notifyViewReadyInner(AutofillId autofillId, String[] strArr, boolean z) {
        int i;
        if (!isImproveFillDialogEnabled() || z) {
            if (Helper.sDebug) {
                Log.d("AutofillManager", "notifyViewReadyInner:" + autofillId);
            }
            if (hasAutofillFeature()) {
                synchronized (this.mLock) {
                    if (this.mAllTrackedViews.contains(autofillId)) {
                        return;
                    }
                    this.mAllTrackedViews.add(autofillId);
                    TrackedViews trackedViews = this.mTrackedViews;
                    if (trackedViews != null) {
                        trackedViews.checkViewState(autofillId);
                    }
                    if (this.mIsFillRequested.get()) {
                        return;
                    }
                    if (AutofillFeatureFlags.isAutofillPccClassificationEnabled()) {
                        synchronized (this.mLock) {
                            if (!isActiveLocked()) {
                                if (tryAddServiceClientIfNeededLocked(z)) {
                                    startSessionLocked(AutofillId.NO_AUTOFILL_ID, null, null, 512);
                                } else if (Helper.sVerbose) {
                                    Log.v("AutofillManager", "not starting session: no service client");
                                }
                            }
                        }
                    }
                    boolean z2 = true;
                    if (!this.mIsFillDialogEnabled) {
                        if (strArr != null) {
                            boolean z3 = false;
                            for (String str : strArr) {
                                String[] strArr2 = this.mFillDialogEnabledHints;
                                int length = strArr2.length;
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= length) {
                                        break;
                                    }
                                    if (strArr2[i2].equalsIgnoreCase(str)) {
                                        z3 = true;
                                        break;
                                    }
                                    i2++;
                                }
                                if (z3) {
                                    break;
                                }
                            }
                            z2 = z3;
                        } else {
                            z2 = false;
                        }
                    }
                    if (z2) {
                        if (Helper.sDebug) {
                            Log.d("AutofillManager", "Triggering pre-emptive request for fill dialog.");
                        }
                        if (z) {
                            if (Helper.sDebug) {
                                Log.d("AutofillManager", "Pre fill request is triggered for credMan");
                            }
                            i = 2128;
                        } else {
                            i = 80;
                        }
                        int i3 = i;
                        synchronized (this.mLock) {
                            notifyViewEnteredLocked(null, AutofillId.NO_AUTOFILL_ID, null, null, i3);
                        }
                    }
                }
            }
        }
    }

    private boolean hasFillDialogUiFeature() {
        return this.mIsFillDialogEnabled || !ArrayUtils.isEmpty(this.mFillDialogEnabledHints);
    }

    private int getImeStateFlag(View view) {
        WindowInsets rootWindowInsets;
        return (view == null || (rootWindowInsets = view.getRootWindowInsets()) == null || !rootWindowInsets.isVisible(WindowInsets.Type.ime())) ? 0 : 128;
    }

    private boolean shouldIgnoreViewEnteredLocked(AutofillId autofillId, int i) {
        ArraySet<AutofillId> arraySet;
        if (isDisabledByServiceLocked()) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "ignoring notifyViewEntered(flags=" + i + ", view=" + autofillId + ") on state " + getStateAsStringLocked() + " because disabled by svc");
            }
            return true;
        }
        if (!isFinishedLocked() || (i & 1) != 0 || (arraySet = this.mEnteredIds) == null || !arraySet.contains(autofillId)) {
            return false;
        }
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "ignoring notifyViewEntered(flags=" + i + ", view=" + autofillId + ") on state " + getStateAsStringLocked() + " because view was already entered: " + this.mEnteredIds);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isClientVisibleForAutofillLocked() {
        AutofillClient client = getClient();
        return client != null && client.autofillClientIsVisibleForAutofill();
    }

    private boolean isClientDisablingEnterExitEvent() {
        AutofillClient client = getClient();
        return client != null && client.isDisablingEnterExitEventForAutofill();
    }

    private void notifyViewEntered(View view, int i) {
        AutofillCallback notifyViewEnteredLocked;
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                notifyViewEnteredLocked = notifyViewEnteredLocked(view, view.getAutofillId(), null, view.getAutofillValue(), i);
            }
            if (notifyViewEnteredLocked != null) {
                this.mCallback.onAutofillEvent(view, 3);
            }
        }
    }

    public void notifyViewExited(View view) {
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                notifyViewExitedLocked(view);
            }
        }
    }

    void notifyViewExitedLocked(View view) {
        if (tryAddServiceClientIfNeededLocked()) {
            if ((this.mEnabled || this.mEnabledForAugmentedAutofillOnly) && isActiveLocked() && !isClientDisablingEnterExitEvent()) {
                updateSessionLocked(view.getAutofillId(), null, null, 3, 0);
            }
        }
    }

    public void notifyViewVisibilityChanged(View view, boolean z) {
        notifyViewVisibilityChangedInternal(view, 0, z, false);
    }

    public void notifyViewVisibilityChanged(View view, int i, boolean z) {
        notifyViewVisibilityChangedInternal(view, i, z, true);
    }

    private void notifyViewVisibilityChangedInternal(View view, int i, boolean z, boolean z2) {
        AutofillId autofillId;
        ArraySet<AutofillId> arraySet;
        synchronized (this.mLock) {
            if (this.mForAugmentedAutofillOnly) {
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "notifyViewVisibilityChanged(): ignoring on augmented only mode");
                }
                return;
            }
            if (this.mRelayoutFixDeprecated && this.mState == 7) {
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "notifyViewVisibilityChanged(): ignoring in auth pending mode");
                }
                return;
            }
            if (this.mEnabled && isActiveLocked()) {
                if (z2) {
                    autofillId = getAutofillId(view, i);
                } else {
                    autofillId = view.getAutofillId();
                }
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "visibility changed for " + autofillId + ": " + z);
                }
                if (!z && (arraySet = this.mFillableIds) != null && arraySet.contains(autofillId)) {
                    if (Helper.sDebug) {
                        Log.d("AutofillManager", "Hidding UI when view " + autofillId + " became invisible");
                    }
                    requestHideFillUi(autofillId, view);
                }
                TrackedViews trackedViews = this.mTrackedViews;
                if (trackedViews != null) {
                    trackedViews.notifyViewVisibilityChangedLocked(autofillId, z);
                } else if (Helper.sVerbose) {
                    Log.v("AutofillManager", "Ignoring visibility change on " + autofillId + ": no tracked views");
                }
            } else if (!z2 && z) {
                startAutofillIfNeededLocked(view);
            }
        }
    }

    public void notifyViewEntered(View view, int i, Rect rect) {
        notifyViewEntered(view, i, rect, 0);
    }

    private void notifyViewEntered(View view, int i, Rect rect, int i2) {
        AutofillCallback notifyViewEnteredLocked;
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                notifyViewEnteredLocked = notifyViewEnteredLocked(view, getAutofillId(view, i), rect, null, i2);
            }
            if (notifyViewEnteredLocked != null) {
                notifyViewEnteredLocked.onAutofillEvent(view, i, 3);
            }
        }
    }

    private AutofillCallback notifyViewEnteredLocked(View view, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i) {
        AutofillManager autofillManager;
        AutofillId autofillId2;
        if (shouldIgnoreViewEnteredLocked(autofillId, i)) {
            return null;
        }
        if (!tryAddServiceClientIfNeededLocked(isCredmanRequested(view))) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "ignoring notifyViewEntered(" + autofillId + "): no service client");
            }
            return null;
        }
        if (!this.mEnabled && !this.mEnabledForAugmentedAutofillOnly) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "ignoring notifyViewEntered(" + autofillId + "): disabled");
            }
            return this.mCallback;
        }
        if (this.mIsCredmanIntegrationEnabled && isCredmanRequested(view)) {
            i |= 2048;
        }
        this.mIsFillRequested.set(true);
        if (!isClientDisablingEnterExitEvent()) {
            if ((view instanceof TextView) && ((TextView) view).isAnyPasswordInputType()) {
                i |= 4;
            }
            if (AutofillFeatureFlags.isFillAndSaveDialogDisabledForCredentialManager() && this.mScreenHasCredmanField) {
                i |= 1024;
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "updating session with flag screen has credman view");
                }
            }
            int imeStateFlag = getImeStateFlag(view) | i;
            if (!isActiveLocked()) {
                startSessionLocked(autofillId, rect, autofillValue, imeStateFlag);
                autofillManager = this;
                autofillId2 = autofillId;
            } else {
                if (this.mForAugmentedAutofillOnly && (imeStateFlag & 1) != 0) {
                    if (Helper.sDebug) {
                        Log.d("AutofillManager", "notifyViewEntered(" + autofillId + "): resetting mForAugmentedAutofillOnly on manual request");
                    }
                    this.mForAugmentedAutofillOnly = false;
                }
                if ((imeStateFlag & 64) != 0) {
                    imeStateFlag |= 256;
                }
                autofillManager = this;
                autofillId2 = autofillId;
                autofillManager.updateSessionLocked(autofillId2, rect, autofillValue, 2, imeStateFlag);
            }
            autofillManager.addEnteredIdLocked(autofillId2);
        }
        return null;
    }

    private void addEnteredIdLocked(AutofillId autofillId) {
        if (this.mEnteredIds == null) {
            this.mEnteredIds = new ArraySet<>(1);
        }
        autofillId.resetSessionId();
        this.mEnteredIds.add(autofillId);
    }

    void notifyImeAnimationStart(long j) {
        try {
            this.mService.notifyImeAnimationStart(this.mSessionId, j, this.mContext.getUserId());
        } catch (RemoteException e) {
            Log.w("AutofillManager", "notifyImeAnimationStart(): RemoteException caught but ignored " + e);
        }
    }

    void notifyImeAnimationEnd(long j) {
        try {
            this.mService.notifyImeAnimationEnd(this.mSessionId, j, this.mContext.getUserId());
        } catch (RemoteException e) {
            Log.w("AutofillManager", "notifyImeAnimationStart(): RemoteException caught but ignored " + e);
        }
    }

    public void notifyViewExited(View view, int i) {
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "notifyViewExited(" + view.getAutofillId() + ", " + i);
        }
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                notifyViewExitedLocked(view, i);
            }
        }
    }

    private void notifyViewExitedLocked(View view, int i) {
        if (tryAddServiceClientIfNeededLocked()) {
            if ((this.mEnabled || this.mEnabledForAugmentedAutofillOnly) && isActiveLocked() && !isClientDisablingEnterExitEvent()) {
                updateSessionLocked(getAutofillId(view, i), null, null, 3, 0);
            }
        }
    }

    public void notifyValueChanged(View view) {
        AutofillValue autofillValue;
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                AutofillId autofillId = null;
                boolean z = false;
                if (this.mLastAutofilledData == null) {
                    view.setAutofilled(false, false);
                    autofillValue = null;
                } else {
                    AutofillId autofillId2 = view.getAutofillId();
                    if (this.mLastAutofilledData.containsKey(autofillId2)) {
                        AutofillValue autofillValue2 = view.getAutofillValue();
                        boolean z2 = this.mLastAutofilledData.keySet().size() == 1;
                        if (Objects.equals(this.mLastAutofilledData.get(autofillId2), autofillValue2)) {
                            view.setAutofilled(true, z2);
                            try {
                                this.mService.setViewAutofilled(this.mSessionId, autofillId2, this.mContext.getUserId());
                            } catch (RemoteException unused) {
                            }
                        } else {
                            view.setAutofilled(false, false);
                            this.mLastAutofilledData.remove(autofillId2);
                        }
                        autofillId = autofillId2;
                        autofillValue = autofillValue2;
                        z = true;
                    } else {
                        view.setAutofilled(false, false);
                        autofillId = autofillId2;
                        autofillValue = null;
                    }
                }
                if (this.mEnabled && isActiveLocked()) {
                    if (autofillId == null) {
                        autofillId = view.getAutofillId();
                    }
                    if (!z) {
                        autofillValue = view.getAutofillValue();
                    }
                    updateSessionLocked(autofillId, null, autofillValue, 4, getImeStateFlag(view));
                    return;
                }
                if (!startAutofillIfNeededLocked(view) && Helper.sVerbose) {
                    Log.v("AutofillManager", "notifyValueChanged(" + view.getAutofillId() + "): ignoring on state " + getStateAsStringLocked());
                }
            }
        }
    }

    public void notifyValueChanged(View view, int i, AutofillValue autofillValue) {
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                if (this.mLastAutofilledData != null) {
                    AutofillId autofillId = new AutofillId(view.getAutofillId(), i, this.mSessionId);
                    if (this.mLastAutofilledData.containsKey(autofillId) && Objects.equals(this.mLastAutofilledData.get(autofillId), autofillValue)) {
                        if (Helper.sDebug) {
                            Log.v("AutofillManager", "notifyValueChanged() virtual view autofilled successfully:" + i + " value:" + autofillValue);
                        }
                        try {
                            this.mService.setViewAutofilled(this.mSessionId, autofillId, this.mContext.getUserId());
                        } catch (RemoteException e) {
                            Log.w("AutofillManager", "RemoteException caught but ignored " + e);
                        }
                    }
                }
                if (this.mEnabled && isActiveLocked()) {
                    updateSessionLocked(getAutofillId(view, i), null, autofillValue, 4, getImeStateFlag(view));
                    return;
                }
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "notifyValueChanged(" + view.getAutofillId() + ":" + i + "): ignoring on state " + getStateAsStringLocked());
                }
            }
        }
    }

    public void notifyViewClicked(View view) {
        notifyViewClicked(view.getAutofillId());
    }

    public void notifyViewClicked(View view, int i) {
        notifyViewClicked(getAutofillId(view, i));
    }

    private void notifyViewClicked(AutofillId autofillId) {
        if (hasAutofillFeature()) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "notifyViewClicked(): id=" + autofillId + ", trigger=" + this.mSaveTriggerId);
            }
            synchronized (this.mLock) {
                if (this.mEnabled && isActiveLocked()) {
                    AutofillId autofillId2 = this.mSaveTriggerId;
                    if (autofillId2 != null && autofillId2.equals(autofillId)) {
                        if (Helper.sDebug) {
                            Log.d("AutofillManager", "triggering commit by click of " + autofillId);
                        }
                        commitLocked(3);
                        this.mMetricsLogger.write(newLog(MetricsProto.MetricsEvent.AUTOFILL_SAVE_EXPLICITLY_TRIGGERED));
                    }
                }
            }
        }
    }

    public void onActivityFinishing() {
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                if (this.mSaveOnFinish) {
                    if (Helper.sDebug) {
                        Log.d("AutofillManager", "onActivityFinishing(): calling commitLocked()");
                    }
                    commitLocked(1);
                } else {
                    if (Helper.sDebug) {
                        Log.d("AutofillManager", "onActivityFinishing(): calling cancelLocked()");
                    }
                    cancelLocked();
                }
            }
        }
    }

    public void commit() {
        if (hasAutofillFeature()) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "commit() called by app");
            }
            synchronized (this.mLock) {
                commitLocked(2);
            }
        }
    }

    private void commitLocked(int i) {
        if (this.mEnabled || isActiveLocked()) {
            finishSessionLocked(i);
        }
    }

    public void cancel() {
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "cancel() called by app or augmented autofill service");
        }
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                cancelLocked();
            }
        }
    }

    private void cancelLocked() {
        if (this.mEnabled || isActiveLocked()) {
            cancelSessionLocked();
        }
    }

    public void disableOwnedAutofillServices() {
        disableAutofillServices();
    }

    public void disableAutofillServices() {
        if (hasAutofillFeature()) {
            try {
                this.mService.disableOwnedAutofillServices(this.mContext.getUserId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean hasEnabledAutofillServices() {
        if (this.mService == null) {
            return false;
        }
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        try {
            this.mService.isServiceEnabled(this.mContext.getUserId(), this.mContext.getPackageName(), syncResultReceiver);
            return syncResultReceiver.getIntResult() == 1;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException e2) {
            throw new RuntimeException("Fail to get enabled autofill services status. " + e2);
        }
    }

    public ComponentName getAutofillServiceComponentName() {
        if (this.mService == null) {
            return null;
        }
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        try {
            this.mService.getAutofillServiceComponentName(syncResultReceiver);
            return (ComponentName) syncResultReceiver.getParcelableResult();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException e2) {
            throw new RuntimeException("Fail to get autofill services component name. " + e2);
        }
    }

    public String getUserDataId() {
        try {
            SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
            this.mService.getUserDataId(syncResultReceiver);
            return syncResultReceiver.getStringResult();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException e2) {
            throw new RuntimeException("Fail to get user data id for field classification. " + e2);
        }
    }

    public UserData getUserData() {
        try {
            SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
            this.mService.getUserData(syncResultReceiver);
            return (UserData) syncResultReceiver.getParcelableResult();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException e2) {
            throw new RuntimeException("Fail to get user data for field classification. " + e2);
        }
    }

    public void setUserData(UserData userData) {
        try {
            this.mService.setUserData(userData);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isFieldClassificationEnabled() {
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        try {
            this.mService.isFieldClassificationEnabled(syncResultReceiver);
            return syncResultReceiver.getIntResult() == 1;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException e2) {
            throw new RuntimeException("Fail to get field classification enabled status. " + e2);
        }
    }

    public String getDefaultFieldClassificationAlgorithm() {
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        try {
            this.mService.getDefaultFieldClassificationAlgorithm(syncResultReceiver);
            return syncResultReceiver.getStringResult();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException e2) {
            throw new RuntimeException("Fail to get default field classification algorithm. " + e2);
        }
    }

    public List<String> getAvailableFieldClassificationAlgorithms() {
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        try {
            this.mService.getAvailableFieldClassificationAlgorithms(syncResultReceiver);
            String[] stringArrayResult = syncResultReceiver.getStringArrayResult();
            return stringArrayResult != null ? Arrays.asList(stringArrayResult) : Collections.EMPTY_LIST;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException e2) {
            throw new RuntimeException("Fail to get available field classification algorithms. " + e2);
        }
    }

    public boolean isAutofillSupported() {
        if (this.mService == null) {
            return false;
        }
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        try {
            this.mService.isServiceSupported(this.mContext.getUserId(), syncResultReceiver);
            return syncResultReceiver.getIntResult() == 1;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException e2) {
            throw new RuntimeException("Fail to get autofill supported status. " + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AutofillClient getClient() {
        AutofillClient autofillClient = this.mContext.getAutofillClient();
        if (autofillClient == null && Helper.sVerbose) {
            Log.v("AutofillManager", "No AutofillClient for " + this.mContext.getPackageName() + " on context " + this.mContext);
        }
        return autofillClient;
    }

    public boolean isAutofillUiShowing() {
        AutofillClient autofillClient = this.mContext.getAutofillClient();
        return autofillClient != null && autofillClient.autofillClientIsFillUiShowing();
    }

    public boolean shouldIgnoreCredentialViews() {
        return this.mShouldIgnoreCredentialViews;
    }

    public void onAuthenticationResult(int i, Intent intent, View view) {
        AutofillManager autofillManager;
        Parcelable parcelableExtra;
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "onAuthenticationResult(): authId= " + i + ", data=" + intent);
        }
        if (!hasAutofillFeature()) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "onAuthenticationResult(): autofill not enabled");
                return;
            }
            return;
        }
        synchronized (this.mLock) {
            if (!isActiveLocked()) {
                Log.w("AutofillManager", "onAuthenticationResult(): sessionId=" + this.mSessionId + " not active");
                return;
            }
            this.mState = 1;
            if (this.mOnInvisibleCalled || view == null || !view.canNotifyAutofillEnterExitEvent()) {
                autofillManager = this;
            } else {
                notifyViewExitedLocked(view);
                autofillManager = this;
                autofillManager.notifyViewEnteredLocked(view, view.getAutofillId(), null, view.getAutofillValue(), 0);
            }
            if (intent == null) {
                Log.i("AutofillManager", "onAuthenticationResult(): empty intent");
                return;
            }
            if (intent.getParcelableExtra(EXTRA_AUTHENTICATION_RESULT) != null) {
                parcelableExtra = intent.getParcelableExtra(EXTRA_AUTHENTICATION_RESULT);
            } else {
                parcelableExtra = (intent.getParcelableExtra(CredentialProviderService.EXTRA_GET_CREDENTIAL_RESPONSE) == null || !Flags.autofillCredmanIntegration()) ? null : intent.getParcelableExtra(CredentialProviderService.EXTRA_GET_CREDENTIAL_RESPONSE);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_AUTHENTICATION_RESULT, parcelableExtra);
            Serializable serializableExtra = intent.getSerializableExtra(CredentialProviderService.EXTRA_GET_CREDENTIAL_EXCEPTION, GetCredentialException.class);
            if (serializableExtra != null && Flags.autofillCredmanIntegration()) {
                bundle.putSerializable(CredentialProviderService.EXTRA_GET_CREDENTIAL_EXCEPTION, serializableExtra);
            }
            Bundle bundleExtra = intent.getBundleExtra(EXTRA_CLIENT_STATE);
            if (bundleExtra != null) {
                bundle.putBundle(EXTRA_CLIENT_STATE, bundleExtra);
            }
            if (intent.hasExtra(EXTRA_AUTHENTICATION_RESULT_EPHEMERAL_DATASET)) {
                bundle.putBoolean(EXTRA_AUTHENTICATION_RESULT_EPHEMERAL_DATASET, intent.getBooleanExtra(EXTRA_AUTHENTICATION_RESULT_EPHEMERAL_DATASET, false));
            }
            try {
                autofillManager.mService.setAuthenticationResult(bundle, autofillManager.mSessionId, i, autofillManager.mContext.getUserId());
            } catch (RemoteException e) {
                Log.e("AutofillManager", "Error delivering authentication result", e);
            }
        }
    }

    public AutofillId getNextAutofillId() {
        AutofillClient client = getClient();
        if (client == null) {
            return null;
        }
        AutofillId autofillClientGetNextAutofillId = client.autofillClientGetNextAutofillId();
        if (autofillClientGetNextAutofillId == null && Helper.sDebug) {
            Log.d("AutofillManager", "getNextAutofillId(): client " + client + " returned null");
        }
        return autofillClientGetNextAutofillId;
    }

    private static AutofillId getAutofillId(View view, int i) {
        return new AutofillId(view.getAutofillViewId(), i);
    }

    private void startSessionLocked(AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i) {
        int i2;
        Rect rect2;
        AutofillValue autofillValue2;
        String str;
        IBinder iBinder;
        int i3;
        boolean z;
        AutofillOptions autofillOptions;
        Set<AutofillId> set = this.mEnteredForAugmentedAutofillIds;
        if ((set == null || !set.contains(autofillId)) && !this.mEnabledForAugmentedAutofillOnly) {
            i2 = i;
        } else {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "Starting session for augmented autofill on " + autofillId);
            }
            i2 = i | 8;
        }
        if (Helper.sVerbose) {
            StringBuilder sb = new StringBuilder("startSessionLocked(): id=");
            sb.append(autofillId);
            sb.append(", bounds=");
            rect2 = rect;
            sb.append(rect2);
            sb.append(", value=");
            autofillValue2 = autofillValue;
            sb.append(autofillValue2);
            sb.append(", flags=");
            sb.append(i2);
            sb.append(", state=");
            sb.append(getStateAsStringLocked());
            sb.append(", compatMode=");
            sb.append(isCompatibilityModeEnabledLocked());
            sb.append(", augmentedOnly=");
            sb.append(this.mForAugmentedAutofillOnly);
            sb.append(", enabledAugmentedOnly=");
            sb.append(this.mEnabledForAugmentedAutofillOnly);
            sb.append(", enteredIds=");
            sb.append(this.mEnteredIds);
            Log.v("AutofillManager", sb.toString());
        } else {
            rect2 = rect;
            autofillValue2 = autofillValue;
        }
        if (this.mForAugmentedAutofillOnly && !this.mEnabledForAugmentedAutofillOnly && (i2 & 1) != 0) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "resetting mForAugmentedAutofillOnly on manual autofill request");
            }
            this.mForAugmentedAutofillOnly = false;
        }
        if (this.mState != 0 && !isFinishedLocked() && (i2 & 1) == 0) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "not automatically starting session for " + autofillId + " on state " + getStateAsStringLocked() + " and flags " + i2);
                return;
            }
            return;
        }
        try {
            AutofillClient client = getClient();
            if (client == null) {
                return;
            }
            SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
            ComponentName autofillClientGetComponentName = client.autofillClientGetComponentName();
            if (!this.mEnabledForAugmentedAutofillOnly && (autofillOptions = this.mOptions) != null && autofillOptions.isAutofillDisabledLocked(autofillClientGetComponentName)) {
                if (this.mOptions.isAugmentedAutofillEnabled(this.mContext)) {
                    if (Helper.sDebug) {
                        Log.d("AutofillManager", "startSession(" + autofillClientGetComponentName + "): disabled by service but allowlisted for augmented autofill");
                        i2 |= 8;
                    }
                } else {
                    if (Helper.sDebug) {
                        Log.d("AutofillManager", "startSession(" + autofillClientGetComponentName + "): ignored because disabled by service and not allowlisted for augmented autofill");
                    }
                    setSessionFinished(4, null);
                    client.autofillClientResetableStateAvailable();
                    return;
                }
            }
            int i4 = i2;
            IAutoFillManager iAutoFillManager = this.mService;
            IBinder autofillClientGetActivityToken = client.autofillClientGetActivityToken();
            IBinder asBinder = this.mServiceClient.asBinder();
            int userId = this.mContext.getUserId();
            if (this.mCallback != null) {
                str = "startSession(";
                iBinder = asBinder;
                i3 = userId;
                z = true;
            } else {
                str = "startSession(";
                iBinder = asBinder;
                i3 = userId;
                z = false;
            }
            String str2 = str;
            iAutoFillManager.startSession(autofillClientGetActivityToken, iBinder, autofillId, rect2, autofillValue2, i3, z, i4, autofillClientGetComponentName, isCompatibilityModeEnabledLocked(), syncResultReceiver);
            int intResult = syncResultReceiver.getIntResult();
            this.mSessionId = intResult;
            if (intResult != Integer.MAX_VALUE) {
                this.mState = 1;
                this.mAutofillStateFingerprint.setSessionId(intResult);
            }
            if ((syncResultReceiver.getOptionalExtraIntResult(0) & 1) != 0) {
                if (Helper.sDebug) {
                    Log.d("AutofillManager", str2 + autofillClientGetComponentName + "): for augmented only");
                }
                this.mForAugmentedAutofillOnly = true;
            }
            client.autofillClientResetableStateAvailable();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException e2) {
            Log.w("AutofillManager", "Exception getting result from SyncResultReceiver: " + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishSessionLocked(int i) {
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "finishSessionLocked(): " + getStateAsStringLocked());
        }
        if (isActiveLocked()) {
            try {
                this.mService.finishSession(this.mSessionId, this.mContext.getUserId(), i);
                resetSessionLocked(true);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private void cancelSessionLocked() {
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "cancelSessionLocked(): " + getStateAsStringLocked());
        }
        if (isActiveLocked()) {
            try {
                this.mService.cancelSession(this.mSessionId, this.mContext.getUserId());
                resetSessionLocked(true);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private void resetSessionLocked(boolean z) {
        this.mSessionId = Integer.MAX_VALUE;
        this.mState = 0;
        this.mTrackedViews = null;
        this.mFillableIds = null;
        this.mSaveTriggerId = null;
        this.mIdShownFillUi = null;
        this.mIsFillRequested.set(false);
        this.mShowAutofillDialogCalled = false;
        this.mFillDialogTriggerIds = null;
        this.mScreenHasCredmanField = false;
        this.mAllTrackedViews.clear();
        if (z) {
            this.mEnteredIds = null;
        }
        this.mFillReAttemptNeeded = false;
        this.mFingerprintToViewMap.clear();
        this.mAutofillStateFingerprint = AutofillStateFingerprint.createInstance();
    }

    private void updateSessionLocked(AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i, int i2) {
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "updateSessionLocked(): id=" + autofillId + ", bounds=" + rect + ", value=" + autofillValue + ", action=" + i + ", flags=" + i2);
        }
        try {
            this.mService.updateSession(this.mSessionId, autofillId, rect, autofillValue, i, i2, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean tryAddServiceClientIfNeededLocked() {
        return tryAddServiceClientIfNeededLocked(false);
    }

    private boolean tryAddServiceClientIfNeededLocked(boolean z) {
        AutofillClient client = getClient();
        if (client == null) {
            return false;
        }
        if (this.mService == null) {
            Log.w("AutofillManager", "Autofill service is null!");
            return false;
        }
        if (this.mServiceClient == null) {
            this.mServiceClient = new AutofillManagerClient();
            try {
                final int userId = this.mContext.getUserId();
                SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
                this.mService.addClient(this.mServiceClient, client.autofillClientGetComponentName(), userId, syncResultReceiver, z);
                try {
                    int intResult = syncResultReceiver.getIntResult();
                    this.mEnabled = (intResult & 1) != 0;
                    Helper.sDebug = (intResult & 2) != 0;
                    Helper.sVerbose = (intResult & 4) != 0;
                    this.mEnabledForAugmentedAutofillOnly = (intResult & 8) != 0;
                    if (Helper.sVerbose) {
                        Log.v("AutofillManager", "receiver results: flags=" + intResult + " enabled=" + this.mEnabled + ", enabledForAugmentedOnly: " + this.mEnabledForAugmentedAutofillOnly);
                    }
                    final IAutoFillManager iAutoFillManager = this.mService;
                    final IAutoFillManagerClient iAutoFillManagerClient = this.mServiceClient;
                    this.mServiceClientCleaner = Cleaner.create(this, new Runnable() { // from class: android.view.autofill.AutofillManager$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            IAutoFillManager.this.removeClient(iAutoFillManagerClient, userId);
                        }
                    });
                } catch (SyncResultReceiver.TimeoutException e) {
                    Log.w("AutofillManager", "Failed to initialize autofill: " + e);
                    this.mService.removeClient(this.mServiceClient, userId);
                    this.mServiceClient = null;
                    return false;
                }
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
        return true;
    }

    private boolean startAutofillIfNeededLocked(View view) {
        if (this.mState == 0 && this.mSessionId == Integer.MAX_VALUE && (view instanceof EditText) && !TextUtils.isEmpty(((EditText) view).getText()) && !view.isFocused() && view.isImportantForAutofill() && view.isLaidOut() && view.isVisibleToUser()) {
            boolean tryAddServiceClientIfNeededLocked = tryAddServiceClientIfNeededLocked();
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "startAutofillIfNeededLocked(): enabled=" + this.mEnabled + " mServiceClient=" + this.mServiceClient);
            }
            if (tryAddServiceClientIfNeededLocked && this.mEnabled && !isClientDisablingEnterExitEvent()) {
                AutofillId autofillId = view.getAutofillId();
                AutofillValue autofillValue = view.getAutofillValue();
                startSessionLocked(autofillId, null, null, 0);
                updateSessionLocked(autofillId, null, autofillValue, 4, 0);
                addEnteredIdLocked(autofillId);
                return true;
            }
        }
        return false;
    }

    public void registerCallback(AutofillCallback autofillCallback) {
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                if (autofillCallback == null) {
                    return;
                }
                boolean z = this.mCallback != null;
                this.mCallback = autofillCallback;
                if (!z) {
                    try {
                        this.mService.setHasCallback(this.mSessionId, this.mContext.getUserId(), true);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public void unregisterCallback(AutofillCallback autofillCallback) {
        if (hasAutofillFeature()) {
            synchronized (this.mLock) {
                if (autofillCallback != null) {
                    AutofillCallback autofillCallback2 = this.mCallback;
                    if (autofillCallback2 != null && autofillCallback == autofillCallback2) {
                        this.mCallback = null;
                        try {
                            this.mService.setHasCallback(this.mSessionId, this.mContext.getUserId(), false);
                        } catch (RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }
            }
        }
    }

    @SystemApi
    public void setAugmentedAutofillWhitelist(Set<String> set, Set<ComponentName> set2) {
        if (hasAutofillFeature()) {
            SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
            try {
                this.mService.setAugmentedAutofillWhitelist(Helper.toList(set), Helper.toList(set2), syncResultReceiver);
                int intResult = syncResultReceiver.getIntResult();
                if (intResult == -1) {
                    throw new SecurityException("caller is not user's Augmented Autofill Service");
                }
                if (intResult != 0) {
                    Log.wtf("AutofillManager", "setAugmentedAutofillWhitelist(): received invalid result: " + intResult);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (SyncResultReceiver.TimeoutException e2) {
                Log.e("AutofillManager", "Fail to get the result of set AugmentedAutofill whitelist. " + e2);
            }
        }
    }

    public void notifyViewEnteredForAugmentedAutofill(View view) {
        AutofillId autofillId = view.getAutofillId();
        synchronized (this.mLock) {
            if (this.mEnteredForAugmentedAutofillIds == null) {
                this.mEnteredForAugmentedAutofillIds = new ArraySet(1);
            }
            this.mEnteredForAugmentedAutofillIds.add(autofillId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestShowFillUi(int i, AutofillId autofillId, int i2, int i3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) {
        AutofillCallback autofillCallback;
        AutofillClient client;
        View findView = findView(autofillId);
        if (findView == null) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mSessionId == i && (client = getClient()) != null && client.autofillClientRequestShowFillUi(findView, i2, i3, rect, iAutofillWindowPresenter)) {
                autofillCallback = this.mCallback;
                this.mIdShownFillUi = autofillId;
            } else {
                autofillCallback = null;
            }
        }
        if (autofillCallback != null) {
            if (autofillId.isVirtualInt()) {
                autofillCallback.onAutofillEvent(findView, autofillId.getVirtualChildIntId(), 1);
            } else {
                autofillCallback.onAutofillEvent(findView, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void authenticate(int i, int i2, IntentSender intentSender, Intent intent, boolean z) {
        synchronized (this.mLock) {
            if (i == this.mSessionId) {
                if (this.mRelayoutFixDeprecated || this.mRelayoutFix) {
                    this.mState = 7;
                    if (Helper.sVerbose) {
                        Log.v("AutofillManager", "entering STATE_PENDING_AUTHENTICATION : mRelayoutFix:" + this.mRelayoutFix);
                    }
                }
                AutofillClient client = getClient();
                if (client != null) {
                    this.mOnInvisibleCalled = false;
                    client.autofillClientAuthenticate(i2, intentSender, intent, z);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchUnhandledKey(int i, AutofillId autofillId, KeyEvent keyEvent) {
        AutofillClient client;
        View findView = findView(autofillId);
        if (findView == null) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mSessionId == i && (client = getClient()) != null) {
                client.autofillClientDispatchUnhandledKey(findView, keyEvent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setState(int i) {
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "setState(" + i + ": " + DebugUtils.flagsToString(AutofillManager.class, "SET_STATE_FLAG_", i) + NavigationBarInflaterView.KEY_CODE_END);
        }
        synchronized (this.mLock) {
            try {
                if ((i & 32) != 0) {
                    this.mForAugmentedAutofillOnly = true;
                    return;
                }
                boolean z = (i & 1) != 0;
                this.mEnabled = z;
                if (!z || (i & 2) != 0) {
                    resetSessionLocked(true);
                }
                if ((i & 4) != 0) {
                    this.mServiceClient = null;
                    this.mAugmentedAutofillServiceClient = null;
                    Cleaner cleaner = this.mServiceClientCleaner;
                    if (cleaner != null) {
                        cleaner.clean();
                        this.mServiceClientCleaner = null;
                    }
                    notifyReenableAutofill();
                }
                Helper.sDebug = (i & 8) != 0;
                Helper.sVerbose = (i & 16) != 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void setAutofilledIfValuesIs(View view, AutofillValue autofillValue, boolean z) {
        if (Objects.equals(view.getAutofillValue(), autofillValue)) {
            synchronized (this.mLock) {
                if (this.mLastAutofilledData == null) {
                    this.mLastAutofilledData = new ParcelableMap(1);
                }
                this.mLastAutofilledData.put(view.getAutofillId(), autofillValue);
            }
            view.setAutofilled(true, z);
            if (Helper.sDebug) {
                Log.d("AutofillManager", "View " + view.getAutofillId() + " autofilled synchronously.");
            }
            try {
                this.mService.setViewAutofilled(this.mSessionId, view.getAutofillId(), this.mContext.getUserId());
                return;
            } catch (RemoteException e) {
                Log.w("AutofillManager", "Unable to log due to " + e);
                return;
            }
        }
        if (Helper.sDebug) {
            Log.d("AutofillManager", "View " + view.getAutofillId() + " " + view.getClass().toString() + " from " + view.getClass().getPackageName() + " : didn't fill in synchronously. It may fill asynchronously.");
        }
    }

    private String getString(Object obj) {
        return obj == null ? PerfettoProtoLogImpl.NULL_STRING : obj.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetCredentialException(int i, AutofillId autofillId, String str, String str2) {
        synchronized (this.mLock) {
            if (i != this.mSessionId) {
                Log.w("AutofillManager", "onGetCredentialException afm sessionIds don't match");
                return;
            }
            AutofillClient client = getClient();
            if (client == null) {
                Log.w("AutofillManager", "onGetCredentialException afm client id null");
                return;
            }
            ArrayList<AutofillId> arrayList = new ArrayList<>();
            View[] autofillClientFindViewsByAutofillIdTraversal = client.autofillClientFindViewsByAutofillIdTraversal(Helper.toArray(new ArrayList(Collections.singleton(autofillId))));
            if (autofillClientFindViewsByAutofillIdTraversal != null && autofillClientFindViewsByAutofillIdTraversal.length != 0) {
                View view = autofillClientFindViewsByAutofillIdTraversal[0];
                if (view == null) {
                    Log.i("AutofillManager", "onGetCredentialException View is null");
                    Log.d("AutofillManager", "onGetCredentialException(): no View with id " + autofillId);
                    arrayList.add(autofillId);
                }
                if (autofillId.isVirtualInt()) {
                    Log.i("AutofillManager", "onGetCredentialException afm client id is virtual");
                } else {
                    Log.i("AutofillManager", "onGetCredentialException afm client id is NOT virtual");
                    view.onGetCredentialException(str, str2);
                }
                handleFailedIdsLocked(arrayList);
                return;
            }
            Log.w("AutofillManager", "onGetCredentialException afm client view not found");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetCredentialResponse(int i, AutofillId autofillId, GetCredentialResponse getCredentialResponse) {
        synchronized (this.mLock) {
            if (i != this.mSessionId) {
                Log.w("AutofillManager", "onGetCredentialResponse afm sessionIds don't match");
                return;
            }
            AutofillClient client = getClient();
            if (client == null) {
                Log.w("AutofillManager", "onGetCredentialResponse afm client id null");
                return;
            }
            ArrayList<AutofillId> arrayList = new ArrayList<>();
            View[] autofillClientFindViewsByAutofillIdTraversal = client.autofillClientFindViewsByAutofillIdTraversal(Helper.toArray(new ArrayList(Collections.singleton(autofillId))));
            if (autofillClientFindViewsByAutofillIdTraversal != null && autofillClientFindViewsByAutofillIdTraversal.length != 0) {
                View view = autofillClientFindViewsByAutofillIdTraversal[0];
                if (view == null) {
                    Log.i("AutofillManager", "onGetCredentialResponse View is null");
                    Log.d("AutofillManager", "onGetCredentialResponse(): no View with id " + autofillId);
                    arrayList.add(autofillId);
                }
                if (autofillId.isVirtualInt()) {
                    Log.i("AutofillManager", "onGetCredentialResponse afm client id is virtual");
                } else {
                    Log.i("AutofillManager", "onGetCredentialResponse afm client id is NOT virtual");
                    view.onGetCredentialResponse(getCredentialResponse);
                }
                handleFailedIdsLocked(arrayList);
                return;
            }
            Log.w("AutofillManager", "onGetCredentialResponse afm client view not found");
        }
    }

    private void handleFailedIdsLocked(ArrayList<AutofillId> arrayList) {
        handleFailedIdsLocked(arrayList, null, false, false);
    }

    private void handleFailedIdsLocked(ArrayList<AutofillId> arrayList, ArrayList<AutofillValue> arrayList2, boolean z, boolean z2) {
        if (!arrayList.isEmpty() && Helper.sVerbose) {
            Log.v("AutofillManager", "autofill(): total failed views: " + arrayList);
        }
        if (this.mRelayoutFix && !arrayList.isEmpty()) {
            this.mFillReAttemptNeeded = true;
            this.mAutofillStateFingerprint.storeFailedIdsAndValues(arrayList, arrayList2, z);
        }
        try {
            this.mService.setAutofillFailure(this.mSessionId, arrayList, z2, this.mContext.getUserId());
            if (!this.mRelayoutFix || arrayList.isEmpty()) {
                return;
            }
            if (!getClient().isActivityResumed()) {
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "handleFailedIdsLocked(): failed id's exist, but activity not resumed");
                }
            } else {
                if (z2) {
                    Log.i("AutofillManager", "handleFailedIdsLocked(): Attempted refill, but failed");
                    return;
                }
                Log.i("AutofillManager", "handleFailedIdsLocked(): Attempting refill");
                attemptRefill();
                this.mFillReAttemptNeeded = false;
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void autofill(int i, List<AutofillId> list, List<AutofillValue> list2, boolean z) {
        synchronized (this.mLock) {
            if (i != this.mSessionId) {
                return;
            }
            AutofillClient client = getClient();
            if (client == null) {
                return;
            }
            autofill(client.autofillClientFindViewsByAutofillIdTraversal(Helper.toArray(list)), list, list2, z, false);
        }
    }

    void autofill(View[] viewArr, List<AutofillId> list, List<AutofillValue> list2, boolean z, boolean z2) {
        List<AutofillId> list3 = list;
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "autofill() ids:" + list3 + " isRefill:" + z2);
        }
        synchronized (this.mLock) {
            if (getClient() == null) {
                return;
            }
            if (list3 == null) {
                Log.i("AutofillManager", "autofill(): No id's to fill");
                return;
            }
            if (this.mRelayoutFix && z2) {
                try {
                    this.mService.setAutofillIdsAttemptedForRefill(this.mSessionId, list3, this.mContext.getUserId());
                } catch (RemoteException unused) {
                }
            }
            int size = list3.size();
            ArrayList<AutofillId> arrayList = new ArrayList<>();
            ArrayList<AutofillValue> arrayList2 = new ArrayList<>();
            if (this.mLastAutofilledData == null) {
                this.mLastAutofilledData = new ParcelableMap(size);
            }
            ArrayMap arrayMap = null;
            int i = 0;
            int i2 = 0;
            while (i < size) {
                AutofillId autofillId = list3.get(i);
                AutofillValue autofillValue = list2.get(i);
                View view = viewArr[i];
                if (view == null) {
                    Log.d("AutofillManager", "autofill(): no View with id " + autofillId);
                    arrayList.add(autofillId);
                    arrayList2.add(autofillValue);
                } else {
                    this.mLastAutofilledData.put(autofillId, autofillValue);
                    if (autofillId.isVirtualInt()) {
                        if (arrayMap == null) {
                            arrayMap = new ArrayMap(1);
                        }
                        SparseArray sparseArray = (SparseArray) arrayMap.get(view);
                        if (sparseArray == null) {
                            sparseArray = new SparseArray(5);
                            arrayMap.put(view, sparseArray);
                        }
                        sparseArray.put(autofillId.getVirtualChildIntId(), autofillValue);
                    } else {
                        view.autofill(autofillValue);
                        setAutofilledIfValuesIs(view, autofillValue, z);
                        i2++;
                    }
                }
                i++;
                list3 = list;
            }
            handleFailedIdsLocked(arrayList, arrayList2, z, z2);
            if (arrayMap != null) {
                for (int i3 = 0; i3 < arrayMap.size(); i3++) {
                    View view2 = (View) arrayMap.keyAt(i3);
                    SparseArray<AutofillValue> sparseArray2 = (SparseArray) arrayMap.valueAt(i3);
                    view2.autofill(sparseArray2);
                    i2 += sparseArray2.size();
                }
            }
            this.mMetricsLogger.write(newLog(MetricsProto.MetricsEvent.AUTOFILL_DATASET_APPLIED).addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VALUES, Integer.valueOf(size)).addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VIEWS_FILLED, Integer.valueOf(i2)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void autofillContent(int i, AutofillId autofillId, ClipData clipData) {
        synchronized (this.mLock) {
            if (i != this.mSessionId) {
                return;
            }
            AutofillClient client = getClient();
            if (client == null) {
                return;
            }
            View autofillClientFindViewByAutofillIdTraversal = client.autofillClientFindViewByAutofillIdTraversal(autofillId);
            if (autofillClientFindViewByAutofillIdTraversal == null) {
                Log.d("AutofillManager", "autofillContent(): no view with id " + autofillId);
                reportAutofillContentFailure(autofillId);
                return;
            }
            if (autofillClientFindViewByAutofillIdTraversal.performReceiveContent(new ContentInfo.Builder(clipData, 4).build()) != null) {
                Log.w("AutofillManager", "autofillContent(): receiver could not insert content: id=" + autofillId + ", view=" + autofillClientFindViewByAutofillIdTraversal + ", clip=" + clipData);
                reportAutofillContentFailure(autofillId);
                return;
            }
            this.mMetricsLogger.write(newLog(MetricsProto.MetricsEvent.AUTOFILL_DATASET_APPLIED).addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VALUES, 1).addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VIEWS_FILLED, 1));
        }
    }

    private void reportAutofillContentFailure(AutofillId autofillId) {
        try {
            this.mService.setAutofillFailure(this.mSessionId, Collections.singletonList(autofillId), false, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private LogMaker newLog(int i) {
        LogMaker addTaggedData = new LogMaker(i).addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_SESSION_ID, Integer.valueOf(this.mSessionId));
        if (isCompatibilityModeEnabledLocked()) {
            addTaggedData.addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_COMPAT_MODE, 1);
        }
        AutofillClient client = getClient();
        if (client == null) {
            addTaggedData.setPackageName(this.mContext.getPackageName());
            return addTaggedData;
        }
        addTaggedData.setComponentName(new ComponentName(client.autofillClientGetComponentName().getPackageName(), ""));
        return addTaggedData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTrackedViews(int i, AutofillId[] autofillIdArr, boolean z, boolean z2, AutofillId[] autofillIdArr2, AutofillId autofillId, boolean z3) {
        if (autofillId != null) {
            autofillId.resetSessionId();
        }
        ArraySet arraySet = new ArraySet();
        synchronized (this.mLock) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "setTrackedViews(): sessionId=" + i + ", trackedIds=" + Arrays.toString(autofillIdArr) + ", saveOnAllViewsInvisible=" + z + ", saveOnFinish=" + z2 + ", fillableIds=" + Arrays.toString(autofillIdArr2) + ", saveTrigerId=" + autofillId + ", mFillableIds=" + this.mFillableIds + ", shouldGrabViewFingerprints=" + z3 + ", mEnabled=" + this.mEnabled + ", mSessionId=" + this.mSessionId);
            }
            if (this.mEnabled && this.mSessionId == i) {
                this.mSaveOnFinish = z2;
                if (autofillIdArr2 != null) {
                    if (this.mFillableIds == null) {
                        this.mFillableIds = new ArraySet<>(autofillIdArr2.length);
                    }
                    for (AutofillId autofillId2 : autofillIdArr2) {
                        if (autofillId2 != null) {
                            autofillId2.resetSessionId();
                            this.mFillableIds.add(autofillId2);
                        }
                    }
                }
                AutofillId autofillId3 = this.mSaveTriggerId;
                if (autofillId3 != null && !autofillId3.equals(autofillId)) {
                    setNotifyOnClickLocked(this.mSaveTriggerId, false);
                }
                if (autofillId != null && !autofillId.equals(this.mSaveTriggerId)) {
                    this.mSaveTriggerId = autofillId;
                    setNotifyOnClickLocked(autofillId, true);
                }
                if (!z) {
                    autofillIdArr = null;
                }
                ArraySet<AutofillId> arraySet2 = this.mFillableIds;
                if (arraySet2 != null) {
                    arraySet.addAll((ArraySet) arraySet2);
                }
                if (autofillIdArr != null) {
                    for (AutofillId autofillId4 : autofillIdArr) {
                        if (autofillId4 != null) {
                            autofillId4.resetSessionId();
                            arraySet.add(autofillId4);
                        }
                    }
                }
                if (!arraySet.isEmpty()) {
                    this.mTrackedViews = new TrackedViews(autofillIdArr, Helper.toArray(arraySet));
                } else {
                    this.mTrackedViews = null;
                }
            }
            if (this.mRelayoutFix && z3) {
                this.mAutofillStateFingerprint.setUseRelativePosition(this.mRelativePositionForRelayout);
                this.mAutofillStateFingerprint.storeStatePriorToAuthentication(getClient(), arraySet);
            }
        }
    }

    private void setNotifyOnClickLocked(AutofillId autofillId, boolean z) {
        View findView = findView(autofillId);
        if (findView == null) {
            Log.w("AutofillManager", "setNotifyOnClick(): invalid id: " + autofillId);
            return;
        }
        findView.setNotifyAutofillManagerOnClick(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSaveUiState(int i, boolean z) {
        if (Helper.sDebug) {
            Log.d("AutofillManager", "setSaveUiState(" + i + "): " + z);
        }
        synchronized (this.mLock) {
            if (this.mSessionId != Integer.MAX_VALUE) {
                Log.w("AutofillManager", "setSaveUiState(" + i + ", " + z + ") called on existing session " + this.mSessionId + "; cancelling it");
                cancelSessionLocked();
            }
            if (z) {
                this.mSessionId = i;
                this.mState = 3;
            } else {
                this.mSessionId = Integer.MAX_VALUE;
                this.mState = 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSessionFinished(int i, List<AutofillId> list) {
        if (list != null) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                list.get(i2).resetSessionId();
            }
        }
        synchronized (this.mLock) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "setSessionFinished(): from " + getStateAsStringLocked() + " to " + getStateAsString(i) + "; autofillableIds=" + list);
            }
            if (list != null) {
                this.mEnteredIds = new ArraySet<>(list);
            }
            if (i != 5 && i != 6) {
                resetSessionLocked(false);
                this.mState = i;
            }
            resetSessionLocked(true);
            this.mState = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAugmentedAutofillClient(IResultReceiver iResultReceiver) {
        synchronized (this.mLock) {
            if (this.mAugmentedAutofillServiceClient == null) {
                this.mAugmentedAutofillServiceClient = new AugmentedAutofillManagerClient();
            }
            Bundle bundle = new Bundle();
            bundle.putBinder(EXTRA_AUGMENTED_AUTOFILL_CLIENT, this.mAugmentedAutofillServiceClient.asBinder());
            try {
                iResultReceiver.send(0, bundle);
            } catch (RemoteException e) {
                Log.w("AutofillManager", "Could not send AugmentedAutofillClient back: " + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestShowSoftInput(AutofillId autofillId) {
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "requestShowSoftInput(" + autofillId + NavigationBarInflaterView.KEY_CODE_END);
        }
        AutofillClient client = getClient();
        if (client == null) {
            return;
        }
        final View autofillClientFindViewByAutofillIdTraversal = client.autofillClientFindViewByAutofillIdTraversal(autofillId);
        if (autofillClientFindViewByAutofillIdTraversal == null) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "View is not found");
                return;
            }
            return;
        }
        Handler handler = autofillClientFindViewByAutofillIdTraversal.getHandler();
        if (handler == null) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "Ignoring requestShowSoftInput due to no handler in view");
            }
        } else {
            if (handler.getLooper() != Looper.myLooper()) {
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "Scheduling showSoftInput() on the view UI thread");
                }
                handler.post(new Runnable() { // from class: android.view.autofill.AutofillManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.requestShowSoftInputInViewThread(View.this);
                    }
                });
                return;
            }
            requestShowSoftInputInViewThread(autofillClientFindViewByAutofillIdTraversal);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void requestShowSoftInputInViewThread(View view) {
        if (!view.isFocused()) {
            Log.w("AutofillManager", "Ignoring requestShowSoftInput() due to non-focused view");
            return;
        }
        boolean showSoftInput = ((InputMethodManager) view.getContext().getSystemService(InputMethodManager.class)).showSoftInput(view, 0);
        if (Helper.sVerbose) {
            Log.v("AutofillManager", " InputMethodManager.showSoftInput returns " + showSoftInput);
        }
    }

    public void requestHideFillUi() {
        requestHideFillUi(this.mIdShownFillUi, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestHideFillUi(AutofillId autofillId, boolean z) {
        AutofillClient client;
        View findView = autofillId == null ? null : findView(autofillId);
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "requestHideFillUi(" + autofillId + "): anchor = " + findView);
        }
        if (findView != null) {
            requestHideFillUi(autofillId, findView);
        } else {
            if (!z || (client = getClient()) == null) {
                return;
            }
            client.autofillClientRequestHideFillUi();
        }
    }

    private void requestHideFillUi(AutofillId autofillId, View view) {
        AutofillCallback autofillCallback;
        synchronized (this.mLock) {
            AutofillClient client = getClient();
            autofillCallback = null;
            if (client != null && client.autofillClientRequestHideFillUi()) {
                this.mIdShownFillUi = null;
                autofillCallback = this.mCallback;
            }
        }
        if (autofillCallback != null) {
            if (autofillId.isVirtualInt()) {
                autofillCallback.onAutofillEvent(view, autofillId.getVirtualChildIntId(), 2);
            } else {
                autofillCallback.onAutofillEvent(view, 2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDisableAutofill(long j, ComponentName componentName) {
        synchronized (this.mLock) {
            if (this.mOptions == null) {
                return;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime() + j;
            if (elapsedRealtime < 0) {
                elapsedRealtime = Long.MAX_VALUE;
            }
            if (componentName != null) {
                if (this.mOptions.disabledActivities == null) {
                    this.mOptions.disabledActivities = new ArrayMap<>();
                }
                this.mOptions.disabledActivities.put(componentName.flattenToString(), Long.valueOf(elapsedRealtime));
            } else {
                this.mOptions.appDisabledExpiration = elapsedRealtime;
            }
        }
    }

    void notifyReenableAutofill() {
        synchronized (this.mLock) {
            AutofillOptions autofillOptions = this.mOptions;
            if (autofillOptions == null) {
                return;
            }
            autofillOptions.appDisabledExpiration = 0L;
            this.mOptions.disabledActivities = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyNoFillUi(int i, AutofillId autofillId, int i2) {
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "notifyNoFillUi(): sessionFinishedState=" + i2);
        }
        if (findView(autofillId) == null) {
            return;
        }
        notifyCallback(i, autofillId, 3);
        if (i2 != 0) {
            setSessionFinished(i2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCallback(int i, AutofillId autofillId, int i2) {
        AutofillCallback autofillCallback;
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "notifyCallback(): sessionId=" + i + ", autofillId=" + autofillId + ", event=" + i2);
        }
        View findView = findView(autofillId);
        if (findView == null) {
            return;
        }
        synchronized (this.mLock) {
            autofillCallback = (this.mSessionId != i || getClient() == null) ? null : this.mCallback;
        }
        if (autofillCallback != null) {
            if (autofillId.isVirtualInt()) {
                autofillCallback.onAutofillEvent(findView, autofillId.getVirtualChildIntId(), i2);
            } else {
                autofillCallback.onAutofillEvent(findView, i2);
            }
        }
    }

    private boolean shouldSuppressDialogsForCredman(View view) {
        if (view == null) {
            return false;
        }
        return view.isCredential() || isCredmanRequested(view);
    }

    private boolean isCredmanRequested(View view) {
        return (view == null || view.getViewCredentialHandler() == null) ? false : true;
    }

    private View findView(AutofillId autofillId) {
        AutofillClient client = getClient();
        if (client != null) {
            return client.autofillClientFindViewByAutofillIdTraversal(autofillId);
        }
        return null;
    }

    public boolean hasAutofillFeature() {
        return this.mService != null;
    }

    public void onPendingSaveUi(int i, IBinder iBinder) {
        if (Helper.sVerbose) {
            Log.v("AutofillManager", "onPendingSaveUi(" + i + "): " + iBinder);
        }
        synchronized (this.mLock) {
            try {
                this.mService.onPendingSaveUi(i, iBinder);
            } catch (RemoteException e) {
                Log.e("AutofillManager", "Error in onPendingSaveUi: ", e);
            }
        }
    }

    public void dump(String str, PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.print(str);
            printWriter.println("AutofillManager:");
            String str2 = str + "  ";
            printWriter.print(str2);
            printWriter.print("sessionId: ");
            printWriter.println(this.mSessionId);
            printWriter.print(str2);
            printWriter.print("state: ");
            printWriter.println(getStateAsStringLocked());
            printWriter.print(str2);
            printWriter.print("context: ");
            printWriter.println(this.mContext);
            printWriter.print(str2);
            printWriter.print("service client: ");
            printWriter.println(this.mServiceClient);
            AutofillClient client = getClient();
            if (client != null) {
                printWriter.print(str2);
                printWriter.print("client: ");
                printWriter.print(client);
                printWriter.print(" (");
                printWriter.print(client.autofillClientGetActivityToken());
                printWriter.println(')');
            }
            printWriter.print(str2);
            printWriter.print("enabled: ");
            printWriter.println(this.mEnabled);
            printWriter.print(str2);
            printWriter.print("enabledAugmentedOnly: ");
            printWriter.println(this.mForAugmentedAutofillOnly);
            printWriter.print(str2);
            printWriter.print("hasService: ");
            boolean z = true;
            printWriter.println(this.mService != null);
            printWriter.print(str2);
            printWriter.print("hasCallback: ");
            if (this.mCallback == null) {
                z = false;
            }
            printWriter.println(z);
            printWriter.print(str2);
            printWriter.print("onInvisibleCalled ");
            printWriter.println(this.mOnInvisibleCalled);
            printWriter.print(str2);
            printWriter.print("last autofilled data: ");
            printWriter.println(this.mLastAutofilledData);
            printWriter.print(str2);
            printWriter.print("id of last fill UI shown: ");
            printWriter.println(this.mIdShownFillUi);
            printWriter.print(str2);
            printWriter.print("tracked views: ");
            if (this.mTrackedViews == null) {
                printWriter.println(PerfettoProtoLogImpl.NULL_STRING);
            } else {
                String str3 = str2 + "  ";
                printWriter.println();
                printWriter.print(str3);
                printWriter.print("visible:");
                printWriter.println(this.mTrackedViews.mVisibleTrackedIds);
                printWriter.print(str3);
                printWriter.print("invisible:");
                printWriter.println(this.mTrackedViews.mInvisibleTrackedIds);
            }
            printWriter.print(str2);
            printWriter.print("fillable ids: ");
            printWriter.println(this.mFillableIds);
            printWriter.print(str2);
            printWriter.print("entered ids: ");
            printWriter.println(this.mEnteredIds);
            if (this.mEnteredForAugmentedAutofillIds != null) {
                printWriter.print(str2);
                printWriter.print("entered ids for augmented autofill: ");
                printWriter.println(this.mEnteredForAugmentedAutofillIds);
            }
            if (this.mForAugmentedAutofillOnly) {
                printWriter.print(str2);
                printWriter.println("For Augmented Autofill Only");
            }
            printWriter.print(str2);
            printWriter.print("save trigger id: ");
            printWriter.println(this.mSaveTriggerId);
            printWriter.print(str2);
            printWriter.print("save on finish(): ");
            printWriter.println(this.mSaveOnFinish);
            if (this.mOptions != null) {
                printWriter.print(str2);
                printWriter.print("options: ");
                this.mOptions.dumpShort(printWriter);
                printWriter.println();
            }
            printWriter.print(str2);
            printWriter.print("fill dialog enabled: ");
            printWriter.println(this.mIsFillDialogEnabled);
            printWriter.print(str2);
            printWriter.print("fill dialog enabled hints: ");
            printWriter.println(Arrays.toString(this.mFillDialogEnabledHints));
            printWriter.print(str2);
            printWriter.print("compat mode enabled: ");
            if (this.mCompatibilityBridge != null) {
                String str4 = str2 + "  ";
                printWriter.println("true");
                printWriter.print(str4);
                printWriter.print("windowId: ");
                printWriter.println(this.mCompatibilityBridge.mFocusedWindowId);
                printWriter.print(str4);
                printWriter.print("nodeId: ");
                printWriter.println(this.mCompatibilityBridge.mFocusedNodeId);
                printWriter.print(str4);
                printWriter.print("virtualId: ");
                printWriter.println(AccessibilityNodeInfo.getVirtualDescendantId(this.mCompatibilityBridge.mFocusedNodeId));
                printWriter.print(str4);
                printWriter.print("focusedBounds: ");
                printWriter.println(this.mCompatibilityBridge.mFocusedBounds);
            } else {
                printWriter.println("false");
            }
            printWriter.print(str2);
            printWriter.print("debug: ");
            printWriter.print(Helper.sDebug);
            printWriter.print(" verbose: ");
            printWriter.println(Helper.sVerbose);
        }
    }

    private String getStateAsStringLocked() {
        return getStateAsString(this.mState);
    }

    private static String getStateAsString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "ACTIVE";
            case 2:
                return "FINISHED";
            case 3:
                return "SHOWING_SAVE_UI";
            case 4:
                return "DISABLED_BY_SERVICE";
            case 5:
                return "UNKNOWN_COMPAT_MODE";
            case 6:
                return "UNKNOWN_FAILED";
            case 7:
                return "PENDING_AUTHENTICATION";
            default:
                return "INVALID:" + i;
        }
    }

    public static String getSmartSuggestionModeToString(int i) {
        if (i == 0) {
            return "OFF";
        }
        if (i == 1) {
            return "SYSTEM";
        }
        return "INVALID:" + i;
    }

    private boolean isActiveLocked() {
        return this.mState == 1 || isPendingAuthenticationLocked();
    }

    private boolean isPendingAuthenticationLocked() {
        return (this.mRelayoutFixDeprecated || this.mRelayoutFix) && this.mState == 7;
    }

    private boolean isDisabledByServiceLocked() {
        return this.mState == 4;
    }

    private boolean isFinishedLocked() {
        return this.mState == 2;
    }

    void post(Runnable runnable) {
        AutofillClient client = getClient();
        if (client == null) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "ignoring post() because client is null");
                return;
            }
            return;
        }
        client.autofillClientRunOnUiThread(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFillDialogTriggerIds(List<AutofillId> list) {
        this.mFillDialogTriggerIds = list;
    }

    @Deprecated
    public boolean showAutofillDialog(View view) {
        if (isImproveFillDialogEnabled()) {
            Log.i("AutofillManager", "showAutofillDialog() return false due to improve fill dialog");
            return false;
        }
        Objects.requireNonNull(view);
        if (!shouldShowAutofillDialog(view, view.getAutofillId())) {
            return false;
        }
        this.mShowAutofillDialogCalled = true;
        final WeakReference weakReference = new WeakReference(view);
        post(new Runnable() { // from class: android.view.autofill.AutofillManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AutofillManager.this.lambda$showAutofillDialog$3(weakReference);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAutofillDialog$3(WeakReference weakReference) {
        View view = (View) weakReference.get();
        if (view != null) {
            notifyViewEntered(view);
        }
    }

    @Deprecated
    public boolean showAutofillDialog(View view, final int i) {
        if (isImproveFillDialogEnabled()) {
            Log.i("AutofillManager", "showAutofillDialog() return false due to improve fill dialog");
            return false;
        }
        Objects.requireNonNull(view);
        if (!shouldShowAutofillDialog(view, getAutofillId(view, i))) {
            return false;
        }
        this.mShowAutofillDialogCalled = true;
        final WeakReference weakReference = new WeakReference(view);
        post(new Runnable() { // from class: android.view.autofill.AutofillManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AutofillManager.this.lambda$showAutofillDialog$4(weakReference, i);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAutofillDialog$4(WeakReference weakReference, int i) {
        View view = (View) weakReference.get();
        if (view != null) {
            notifyViewEntered(view, i, null, 0);
        }
    }

    private boolean shouldShowAutofillDialog(View view, AutofillId autofillId) {
        if (!hasFillDialogUiFeature() || this.mShowAutofillDialogCalled || this.mFillDialogTriggerIds == null || this.mScreenHasCredmanField || (getImeStateFlag(view) == 128 && !isImproveFillDialogEnabled())) {
            return false;
        }
        int size = this.mFillDialogTriggerIds.size();
        for (int i = 0; i < size; i++) {
            if (this.mFillDialogTriggerIds.get(i).equalsIgnoreSession(autofillId)) {
                return true;
            }
        }
        return false;
    }

    private final class CompatibilityBridge implements AccessibilityManager.AccessibilityPolicy {
        AccessibilityServiceInfo mCompatServiceInfo;
        private final Rect mFocusedBounds = new Rect();
        private final Rect mTempBounds = new Rect();
        private int mFocusedWindowId = -1;
        private long mFocusedNodeId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;

        private boolean isVirtualNode(int i) {
            return (i == -1 || i == Integer.MAX_VALUE) ? false : true;
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityPolicy
        public int getRelevantEventTypes(int i) {
            return i | 2073;
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityPolicy
        public boolean isEnabled(boolean z) {
            return true;
        }

        CompatibilityBridge() {
            AccessibilityManager.getInstance(AutofillManager.this.mContext).setAccessibilityPolicy(this);
        }

        private AccessibilityServiceInfo getCompatServiceInfo() {
            synchronized (AutofillManager.this.mLock) {
                AccessibilityServiceInfo accessibilityServiceInfo = this.mCompatServiceInfo;
                if (accessibilityServiceInfo != null) {
                    return accessibilityServiceInfo;
                }
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("android", "com.android.server.autofill.AutofillCompatAccessibilityService"));
                try {
                    AccessibilityServiceInfo accessibilityServiceInfo2 = new AccessibilityServiceInfo(AutofillManager.this.mContext.getPackageManager().resolveService(intent, 1048704), AutofillManager.this.mContext);
                    this.mCompatServiceInfo = accessibilityServiceInfo2;
                    return accessibilityServiceInfo2;
                } catch (IOException | XmlPullParserException unused) {
                    Log.e("AutofillManager", "Cannot find compat autofill service:" + intent);
                    throw new IllegalStateException("Cannot find compat autofill service");
                }
            }
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityPolicy
        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(List<AccessibilityServiceInfo> list) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(getCompatServiceInfo());
            return list;
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityPolicy
        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, List<AccessibilityServiceInfo> list) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(getCompatServiceInfo());
            return list;
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityPolicy
        public AccessibilityEvent onAccessibilityEvent(AccessibilityEvent accessibilityEvent, boolean z, int i) {
            AutofillClient client;
            int eventType = accessibilityEvent.getEventType();
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "onAccessibilityEvent(" + AccessibilityEvent.eventTypeToString(eventType) + "): virtualId=" + AccessibilityNodeInfo.getVirtualDescendantId(accessibilityEvent.getSourceNodeId()) + ", client=" + AutofillManager.this.getClient());
            }
            if (eventType == 1) {
                synchronized (AutofillManager.this.mLock) {
                    notifyViewClicked(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
                }
            } else if (eventType == 8) {
                synchronized (AutofillManager.this.mLock) {
                    if (this.mFocusedWindowId == accessibilityEvent.getWindowId() && this.mFocusedNodeId == accessibilityEvent.getSourceNodeId()) {
                        return accessibilityEvent;
                    }
                    if (this.mFocusedWindowId != -1 && this.mFocusedNodeId != AccessibilityNodeInfo.UNDEFINED_NODE_ID) {
                        notifyViewExited(this.mFocusedWindowId, this.mFocusedNodeId);
                        this.mFocusedWindowId = -1;
                        this.mFocusedNodeId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
                        this.mFocusedBounds.set(0, 0, 0, 0);
                    }
                    int windowId = accessibilityEvent.getWindowId();
                    long sourceNodeId = accessibilityEvent.getSourceNodeId();
                    if (notifyViewEntered(windowId, sourceNodeId, this.mFocusedBounds)) {
                        this.mFocusedWindowId = windowId;
                        this.mFocusedNodeId = sourceNodeId;
                    }
                }
            } else if (eventType == 16) {
                synchronized (AutofillManager.this.mLock) {
                    if (this.mFocusedWindowId == accessibilityEvent.getWindowId() && this.mFocusedNodeId == accessibilityEvent.getSourceNodeId()) {
                        notifyValueChanged(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
                    }
                }
            } else if (eventType == 2048 && (client = AutofillManager.this.getClient()) != null) {
                synchronized (AutofillManager.this.mLock) {
                    if (client.autofillClientIsFillUiShowing()) {
                        notifyViewEntered(this.mFocusedWindowId, this.mFocusedNodeId, this.mFocusedBounds);
                    }
                    updateTrackedViewsLocked();
                }
            }
            if (z) {
                return accessibilityEvent;
            }
            return null;
        }

        private boolean notifyViewEntered(int i, long j, Rect rect) {
            View findViewByAccessibilityId;
            AccessibilityNodeInfo findVirtualNodeByAccessibilityId;
            int virtualDescendantId = AccessibilityNodeInfo.getVirtualDescendantId(j);
            if (!isVirtualNode(virtualDescendantId) || (findViewByAccessibilityId = findViewByAccessibilityId(i, j)) == null || (findVirtualNodeByAccessibilityId = findVirtualNodeByAccessibilityId(findViewByAccessibilityId, virtualDescendantId)) == null || !findVirtualNodeByAccessibilityId.isEditable()) {
                return false;
            }
            Rect rect2 = this.mTempBounds;
            findVirtualNodeByAccessibilityId.getBoundsInScreen(rect2);
            if (rect2.equals(rect)) {
                return false;
            }
            rect.set(rect2);
            AutofillManager.this.notifyViewEntered(findViewByAccessibilityId, virtualDescendantId, rect2);
            return true;
        }

        private void notifyViewExited(int i, long j) {
            View findViewByAccessibilityId;
            int virtualDescendantId = AccessibilityNodeInfo.getVirtualDescendantId(j);
            if (isVirtualNode(virtualDescendantId) && (findViewByAccessibilityId = findViewByAccessibilityId(i, j)) != null) {
                AutofillManager.this.notifyViewExited(findViewByAccessibilityId, virtualDescendantId);
            }
        }

        private void notifyValueChanged(int i, long j) {
            View findViewByAccessibilityId;
            AccessibilityNodeInfo findVirtualNodeByAccessibilityId;
            int virtualDescendantId = AccessibilityNodeInfo.getVirtualDescendantId(j);
            if (!isVirtualNode(virtualDescendantId) || (findViewByAccessibilityId = findViewByAccessibilityId(i, j)) == null || (findVirtualNodeByAccessibilityId = findVirtualNodeByAccessibilityId(findViewByAccessibilityId, virtualDescendantId)) == null) {
                return;
            }
            AutofillManager.this.notifyValueChanged(findViewByAccessibilityId, virtualDescendantId, AutofillValue.forText(findVirtualNodeByAccessibilityId.getText()));
        }

        private void notifyViewClicked(int i, long j) {
            View findViewByAccessibilityId;
            int virtualDescendantId = AccessibilityNodeInfo.getVirtualDescendantId(j);
            if (!isVirtualNode(virtualDescendantId) || (findViewByAccessibilityId = findViewByAccessibilityId(i, j)) == null || findVirtualNodeByAccessibilityId(findViewByAccessibilityId, virtualDescendantId) == null) {
                return;
            }
            AutofillManager.this.notifyViewClicked(findViewByAccessibilityId, virtualDescendantId);
        }

        private void updateTrackedViewsLocked() {
            if (AutofillManager.this.mTrackedViews != null) {
                AutofillManager.this.mTrackedViews.onVisibleForAutofillChangedLocked();
            }
        }

        private View findViewByAccessibilityId(int i, long j) {
            AutofillClient client = AutofillManager.this.getClient();
            if (client == null) {
                return null;
            }
            return client.autofillClientFindViewByAccessibilityIdTraversal(AccessibilityNodeInfo.getAccessibilityViewId(j), i);
        }

        private AccessibilityNodeInfo findVirtualNodeByAccessibilityId(View view, int i) {
            AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
            if (accessibilityNodeProvider == null) {
                return null;
            }
            return accessibilityNodeProvider.createAccessibilityNodeInfo(i);
        }
    }

    private class TrackedViews {
        boolean mHasNewTrackedView;
        private final ArraySet<AutofillId> mInvisibleDialogTrackedIds;
        private final ArraySet<AutofillId> mInvisibleTrackedIds;
        boolean mIsTrackedSaveView;
        private final ArraySet<AutofillId> mVisibleDialogTrackedIds;
        private final ArraySet<AutofillId> mVisibleTrackedIds;

        private <T> boolean isInSet(ArraySet<T> arraySet, T t) {
            return arraySet != null && arraySet.contains(t);
        }

        private <T> ArraySet<T> addToSet(ArraySet<T> arraySet, T t) {
            if (arraySet == null) {
                arraySet = new ArraySet<>(1);
            }
            arraySet.add(t);
            return arraySet;
        }

        private <T> ArraySet<T> removeFromSet(ArraySet<T> arraySet, T t) {
            if (arraySet == null) {
                return null;
            }
            arraySet.remove(t);
            if (arraySet.isEmpty()) {
                return null;
            }
            return arraySet;
        }

        TrackedViews(AutofillId[] autofillIdArr, AutofillId[] autofillIdArr2) {
            ArraySet<AutofillId> arraySet = new ArraySet<>();
            this.mVisibleTrackedIds = arraySet;
            ArraySet<AutofillId> arraySet2 = new ArraySet<>();
            this.mInvisibleTrackedIds = arraySet2;
            if (!ArrayUtils.isEmpty(autofillIdArr)) {
                this.mIsTrackedSaveView = true;
                initialTrackedViews(autofillIdArr, arraySet, arraySet2);
            }
            ArraySet<AutofillId> arraySet3 = new ArraySet<>();
            this.mVisibleDialogTrackedIds = arraySet3;
            ArraySet<AutofillId> arraySet4 = new ArraySet<>();
            this.mInvisibleDialogTrackedIds = arraySet4;
            if (!ArrayUtils.isEmpty(autofillIdArr2)) {
                initialTrackedViews(autofillIdArr2, arraySet3, arraySet4);
                AutofillManager.this.mAllTrackedViews.addAll(Arrays.asList(autofillIdArr2));
            }
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "TrackedViews(trackedIds=" + Arrays.toString(autofillIdArr) + "):  mVisibleTrackedIds=" + arraySet + " mInvisibleTrackedIds=" + arraySet2 + " allTrackedIds=" + Arrays.toString(autofillIdArr2) + " mVisibleDialogTrackedIds=" + arraySet3 + " mInvisibleDialogTrackedIds=" + arraySet4);
            }
            if (this.mIsTrackedSaveView && arraySet.isEmpty()) {
                AutofillManager.this.finishSessionLocked(4);
            }
        }

        private void initialTrackedViews(AutofillId[] autofillIdArr, ArraySet<AutofillId> arraySet, ArraySet<AutofillId> arraySet2) {
            boolean[] zArr;
            AutofillClient client = AutofillManager.this.getClient();
            if (ArrayUtils.isEmpty(autofillIdArr) || client == null) {
                return;
            }
            if (client.autofillClientIsVisibleForAutofill()) {
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "client is visible, check tracked ids");
                }
                zArr = client.autofillClientGetViewVisibility(autofillIdArr);
            } else {
                zArr = new boolean[autofillIdArr.length];
            }
            int length = autofillIdArr.length;
            for (int i = 0; i < length; i++) {
                AutofillId autofillId = autofillIdArr[i];
                autofillId.resetSessionId();
                if (zArr[i]) {
                    addToSet(arraySet, autofillId);
                } else {
                    addToSet(arraySet2, autofillId);
                }
            }
        }

        void notifyViewVisibilityChangedLocked(AutofillId autofillId, boolean z) {
            if (Helper.sDebug) {
                Log.d("AutofillManager", "notifyViewVisibilityChangedLocked(): id=" + autofillId + " isVisible=" + z);
            }
            if (AutofillManager.this.isClientVisibleForAutofillLocked()) {
                if (z) {
                    if (isInSet(this.mInvisibleTrackedIds, autofillId)) {
                        removeFromSet(this.mInvisibleTrackedIds, autofillId);
                        addToSet(this.mVisibleTrackedIds, autofillId);
                    }
                    if (isInSet(this.mInvisibleDialogTrackedIds, autofillId)) {
                        removeFromSet(this.mInvisibleDialogTrackedIds, autofillId);
                        addToSet(this.mVisibleDialogTrackedIds, autofillId);
                    }
                } else {
                    if (isInSet(this.mVisibleTrackedIds, autofillId)) {
                        removeFromSet(this.mVisibleTrackedIds, autofillId);
                        addToSet(this.mInvisibleTrackedIds, autofillId);
                    }
                    if (isInSet(this.mVisibleDialogTrackedIds, autofillId)) {
                        removeFromSet(this.mVisibleDialogTrackedIds, autofillId);
                        addToSet(this.mInvisibleDialogTrackedIds, autofillId);
                    }
                }
            } else if (Helper.sDebug) {
                Log.d("AutofillManager", "notifyViewVisibilityChangedLocked(): ignoring view visibility change since activity has stopped");
            }
            if (this.mIsTrackedSaveView && this.mVisibleTrackedIds.isEmpty()) {
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "No more visible tracked save ids. Invisible = " + this.mInvisibleTrackedIds);
                }
                AutofillManager.this.finishSessionLocked(4);
            }
            if (this.mVisibleDialogTrackedIds.isEmpty()) {
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "No more visible tracked fill dialog ids. Invisible = " + this.mInvisibleDialogTrackedIds);
                }
                processNoVisibleTrackedAllViews();
            }
        }

        void onVisibleForAutofillChangedLocked() {
            if (AutofillManager.this.getClient() != null) {
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "onVisibleForAutofillChangedLocked(): inv= " + this.mInvisibleTrackedIds + " vis=" + this.mVisibleTrackedIds);
                }
                onVisibleForAutofillChangedInternalLocked(this.mVisibleTrackedIds, this.mInvisibleTrackedIds);
                onVisibleForAutofillChangedInternalLocked(this.mVisibleDialogTrackedIds, this.mInvisibleDialogTrackedIds);
            }
            if (this.mIsTrackedSaveView && this.mVisibleTrackedIds.isEmpty()) {
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "onVisibleForAutofillChangedLocked(): no more visible ids");
                }
                AutofillManager.this.finishSessionLocked(4);
            }
            if (this.mVisibleDialogTrackedIds.isEmpty()) {
                if (Helper.sVerbose) {
                    Log.v("AutofillManager", "onVisibleForAutofillChangedLocked(): no more visible ids");
                }
                processNoVisibleTrackedAllViews();
            }
        }

        void onVisibleForAutofillChangedInternalLocked(ArraySet<AutofillId> arraySet, ArraySet<AutofillId> arraySet2) {
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "onVisibleForAutofillChangedLocked(): inv= " + arraySet2 + " vis=" + arraySet);
            }
            ArraySet arraySet3 = new ArraySet();
            arraySet3.addAll((ArraySet) arraySet);
            arraySet3.addAll((ArraySet) arraySet2);
            if (arraySet3.isEmpty()) {
                return;
            }
            arraySet.clear();
            arraySet2.clear();
            initialTrackedViews(Helper.toArray(arraySet3), arraySet, arraySet2);
        }

        private void processNoVisibleTrackedAllViews() {
            AutofillManager.this.mShowAutofillDialogCalled = false;
        }

        void checkViewState(AutofillId autofillId) {
            if (this.mHasNewTrackedView) {
                return;
            }
            AutofillManager.this.mIsFillRequested.set(false);
            this.mHasNewTrackedView = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class AutofillManagerClient extends IAutoFillManagerClient.Stub {
        private final WeakReference<AutofillManager> mAfm;

        private AutofillManagerClient(AutofillManager autofillManager) {
            this.mAfm = new WeakReference<>(autofillManager);
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setState(final int i) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.setState(i);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void autofill(final int i, final List<AutofillId> list, final List<AutofillValue> list2, final boolean z) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.autofill(i, list, list2, z);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void onGetCredentialResponse(final int i, final AutofillId autofillId, final GetCredentialResponse getCredentialResponse) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.onGetCredentialResponse(i, autofillId, getCredentialResponse);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void onGetCredentialException(final int i, final AutofillId autofillId, final String str, final String str2) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.onGetCredentialException(i, autofillId, str, str2);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void autofillContent(final int i, final AutofillId autofillId, final ClipData clipData) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.autofillContent(i, autofillId, clipData);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void authenticate(final int i, final int i2, final IntentSender intentSender, final Intent intent, final boolean z) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.authenticate(i, i2, intentSender, intent, z);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestShowFillUi(final int i, final AutofillId autofillId, final int i2, final int i3, final Rect rect, final IAutofillWindowPresenter iAutofillWindowPresenter) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.requestShowFillUi(i, autofillId, i2, i3, rect, iAutofillWindowPresenter);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestHideFillUi(int i, final AutofillId autofillId) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.requestHideFillUi(autofillId, false);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestHideFillUiWhenDestroyed(int i, final AutofillId autofillId) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.requestHideFillUi(autofillId, true);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyNoFillUi(final int i, final AutofillId autofillId, final int i2) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.notifyNoFillUi(i, autofillId, i2);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillUiShown(final int i, final AutofillId autofillId) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.notifyCallback(i, autofillId, 1);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillUiHidden(final int i, final AutofillId autofillId) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.notifyCallback(i, autofillId, 2);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyDisableAutofill(final long j, final ComponentName componentName) throws RemoteException {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.notifyDisableAutofill(j, componentName);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void dispatchUnhandledKey(final int i, final AutofillId autofillId, final KeyEvent keyEvent) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.dispatchUnhandledKey(i, autofillId, keyEvent);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void startIntentSender(final IntentSender intentSender, final Intent intent) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.AutofillManagerClient.lambda$startIntentSender$14(AutofillManager.this, intentSender, intent);
                    }
                });
            }
        }

        static /* synthetic */ void lambda$startIntentSender$14(AutofillManager autofillManager, IntentSender intentSender, Intent intent) {
            IntentSender intentSender2;
            try {
                intentSender2 = intentSender;
            } catch (IntentSender.SendIntentException e) {
                e = e;
                intentSender2 = intentSender;
            }
            try {
                autofillManager.mContext.startIntentSender(intentSender2, intent, 0, 0, 0, ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
            } catch (IntentSender.SendIntentException e2) {
                e = e2;
                Log.e("AutofillManager", "startIntentSender() failed for intent:" + intentSender2, e);
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setTrackedViews(final int i, final AutofillId[] autofillIdArr, final boolean z, final boolean z2, final AutofillId[] autofillIdArr2, final AutofillId autofillId, final boolean z3) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.setTrackedViews(i, autofillIdArr, z, z2, autofillIdArr2, autofillId, z3);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setSaveUiState(final int i, final boolean z) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.setSaveUiState(i, z);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setSessionFinished(final int i, final List<AutofillId> list) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.setSessionFinished(i, list);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void getAugmentedAutofillClient(final IResultReceiver iResultReceiver) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.getAugmentedAutofillClient(iResultReceiver);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestShowSoftInput(final AutofillId autofillId) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.requestShowSoftInput(autofillId);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillDialogTriggerIds(final List<AutofillId> list) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AutofillManagerClient$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.setFillDialogTriggerIds(list);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class AugmentedAutofillManagerClient extends IAugmentedAutofillManagerClient.Stub {
        private final WeakReference<AutofillManager> mAfm;

        private AugmentedAutofillManagerClient(AutofillManager autofillManager) {
            this.mAfm = new WeakReference<>(autofillManager);
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public AssistStructure.ViewNodeParcelable getViewNodeParcelable(AutofillId autofillId) {
            AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager == null) {
                return null;
            }
            final View view = getView(autofillManager, autofillId);
            if (view == null) {
                Log.w("AutofillManager", "getViewNodeParcelable(" + autofillId + "): could not find view");
                return null;
            }
            ViewRootImpl viewRootImpl = view.getViewRootImpl();
            if (viewRootImpl != null && (viewRootImpl.getWindowFlags() & 8192) == 0) {
                final AssistStructure.ViewNodeBuilder viewNodeBuilder = new AssistStructure.ViewNodeBuilder();
                viewNodeBuilder.setAutofillId(view.getAutofillId());
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AugmentedAutofillManagerClient$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.AugmentedAutofillManagerClient.lambda$getViewNodeParcelable$0(View.this, viewNodeBuilder, countDownLatch);
                    }
                });
                try {
                    countDownLatch.await(5000L, TimeUnit.MILLISECONDS);
                    AssistStructure.ViewNode viewNode = viewNodeBuilder.getViewNode();
                    if (viewNode != null && autofillId.equals(viewNode.getAutofillId())) {
                        return new AssistStructure.ViewNodeParcelable(viewNode);
                    }
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
            }
            return null;
        }

        static /* synthetic */ void lambda$getViewNodeParcelable$0(View view, AssistStructure.ViewNodeBuilder viewNodeBuilder, CountDownLatch countDownLatch) {
            view.onProvideAutofillStructure(viewNodeBuilder, 0);
            countDownLatch.countDown();
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public Rect getViewCoordinates(AutofillId autofillId) {
            View view;
            AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager == null || (view = getView(autofillManager, autofillId)) == null) {
                return null;
            }
            Rect rect = new Rect();
            view.getWindowVisibleDisplayFrame(rect);
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            Rect rect2 = new Rect(iArr[0], iArr[1] - rect.top, iArr[0] + view.getWidth(), (iArr[1] - rect.top) + view.getHeight());
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "Coordinates for " + autofillId + ": " + rect2);
            }
            return rect2;
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void autofill(final int i, final List<AutofillId> list, final List<AutofillValue> list2, final boolean z) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AugmentedAutofillManagerClient$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.autofill(i, list, list2, z);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void requestShowFillUi(final int i, final AutofillId autofillId, final int i2, final int i3, final Rect rect, final IAutofillWindowPresenter iAutofillWindowPresenter) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AugmentedAutofillManagerClient$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.requestShowFillUi(i, autofillId, i2, i3, rect, iAutofillWindowPresenter);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void requestHideFillUi(int i, final AutofillId autofillId) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager != null) {
                autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AugmentedAutofillManagerClient$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutofillManager.this.requestHideFillUi(autofillId, false);
                    }
                });
            }
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public boolean requestAutofill(int i, AutofillId autofillId) {
            final AutofillManager autofillManager = this.mAfm.get();
            if (autofillManager == null || autofillManager.mSessionId != i) {
                if (Helper.sDebug) {
                    Slog.d("AutofillManager", "Autofill not available or sessionId doesn't match");
                }
                return false;
            }
            final View view = getView(autofillManager, autofillId);
            if (view == null || !view.isFocused()) {
                if (Helper.sDebug) {
                    Slog.d("AutofillManager", "View not available or is not on focus");
                }
                return false;
            }
            if (Helper.sVerbose) {
                Log.v("AutofillManager", "requestAutofill() by AugmentedAutofillService.");
            }
            autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillManager$AugmentedAutofillManagerClient$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AutofillManager.this.requestAutofillFromNewSession(view);
                }
            });
            return true;
        }

        private View getView(AutofillManager autofillManager, AutofillId autofillId) {
            AutofillClient client = autofillManager.getClient();
            if (client == null) {
                Log.w("AutofillManager", "getView(" + autofillId + "): no autofill client");
                return null;
            }
            View autofillClientFindViewByAutofillIdTraversal = client.autofillClientFindViewByAutofillIdTraversal(autofillId);
            if (autofillClientFindViewByAutofillIdTraversal == null) {
                Log.w("AutofillManager", "getView(" + autofillId + "): could not find view");
            }
            return autofillClientFindViewByAutofillIdTraversal;
        }
    }
}
