package com.android.systemui.plugins.omni;

import android.app.appsearch.AppSearchBatchResult;
import android.app.appsearch.AppSearchManager;
import android.app.appsearch.AppSearchResult;
import android.app.appsearch.BatchResultCallback;
import android.app.appsearch.GenericDocument;
import android.app.appsearch.GetByDocumentIdRequest;
import android.app.appsearch.GlobalSearchSession;
import android.app.appsearch.exceptions.AppSearchException;
import android.app.appsearch.observer.DocumentChangeInfo;
import android.app.appsearch.observer.ObserverCallback;
import android.app.appsearch.observer.ObserverSpec;
import android.app.appsearch.observer.SchemaChangeInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.permission.SafeCloseable;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AssistStateManager implements SafeCloseable {
    public static final boolean DEBUG = false;
    private static final int KEEP_ALIVE = 1;
    private static final String OMNI_APP_SEARCH_CS_HELPER_AVAILABLE_PROPERTY = "isCSHelperAvailable";
    private static final String OMNI_APP_SEARCH_DATABASE = "OmniEntryPoint";
    private static final String OMNI_APP_SEARCH_ID = "entry_point";
    private static final String OMNI_APP_SEARCH_NAMESPACE = "omni";
    private static final String OMNI_APP_SEARCH_PROPERTY = "isAvailable";
    private static final String OMNI_APP_SEARCH_VIS_AVAILABLE_PROPERTY = "isVISAvailable";
    private static final String OMNI_PROPERTY = "omni.AWARE";
    private static final int POOL_SIZE;
    private static final String TAG = "AssistStateManager";
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;
    private final Context mContext;
    private boolean mIsInitSession;
    private final ObserverCallback mObserverCallback;
    private final SimpleBroadcastReceiver mOmniPackageReceiver;
    public static final String OMNI_PACKAGE = "com.google.android.googlequicksearchbox";
    public static String GOOGLE_SEARCH_ACTIVITY = "com.google.android.apps.search.omnient.host.activity.OmnientActivity";
    private static ComponentName COMPONENT_NAME_GOOGLE_SEARCH = new ComponentName(OMNI_PACKAGE, GOOGLE_SEARCH_ACTIVITY);
    public static String GOOGLE_SEARCH_INTRO_ACTIVITY = "com.google.android.apps.search.omnient.onboarding.ui.OnboardingActivity";
    private static ComponentName COMPONENT_NAME_GOOGLE_SEARCH_INTRO = new ComponentName(OMNI_PACKAGE, GOOGLE_SEARCH_INTRO_ACTIVITY);
    public static final MainThreadInitializedObject<AssistStateManager> INSTANCE = new MainThreadInitializedObject<>(new AssistStateManager$$ExternalSyntheticLambda4());
    private CompletableFuture<GlobalSearchSession> mGlobalSearchSessionFuture = new CompletableFuture<>();
    private final GetByDocumentIdRequest mGetByDocumentIdRequest = new GetByDocumentIdRequest.Builder(OMNI_APP_SEARCH_NAMESPACE).addIds(OMNI_APP_SEARCH_ID).build();
    private boolean mIsOmniAvailable = false;
    private boolean mIsOmniAware = false;
    private Optional<Boolean> mIsCsHelperAvailable = Optional.empty();
    private Optional<Boolean> mIsVisAvailable = Optional.empty();
    private boolean mIsOmniPackageEnabled = true;
    private boolean mIsInMultiWindow = true;

    public static /* synthetic */ AssistStateManager $r8$lambda$q9fEfm8Tlfk2rlNY_eYcW4qeT3U(Context context) {
        return new AssistStateManager(context);
    }

    static {
        int max = Math.max(Runtime.getRuntime().availableProcessors(), 2);
        POOL_SIZE = max;
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(max, max, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
    }

    private AssistStateManager(Context context) {
        SimpleBroadcastReceiver simpleBroadcastReceiver = new SimpleBroadcastReceiver(new AssistStateManager$$ExternalSyntheticLambda0(this, 0));
        this.mOmniPackageReceiver = simpleBroadcastReceiver;
        this.mIsInitSession = false;
        this.mContext = context;
        this.mObserverCallback = new ObserverCallback() { // from class: com.android.systemui.plugins.omni.AssistStateManager.1
            @Override // android.app.appsearch.observer.ObserverCallback
            public void onDocumentChanged(DocumentChangeInfo documentChangeInfo) {
                Log.i(AssistStateManager.TAG, "onDocumentChanged");
                if (documentChangeInfo.getChangedDocumentIds().contains(AssistStateManager.OMNI_APP_SEARCH_ID)) {
                    AssistStateManager.this.updateIsOmniAvailableFromAppSearch(null);
                }
            }

            @Override // android.app.appsearch.observer.ObserverCallback
            public void onSchemaChanged(SchemaChangeInfo schemaChangeInfo) {
            }
        };
        initGlobalSearchSession(context, null);
        updateIsOmniAvailableFromAppSearch(null);
        requestUpdateOmniPackageInfo();
        simpleBroadcastReceiver.registerPkgActions(context, OMNI_PACKAGE, "android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REMOVED");
    }

    private ActivityInfo getActivityInfo(ComponentName componentName) {
        try {
            return this.mContext.getPackageManager().getActivityInfo(componentName, 128);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initGlobalSearchSession(Context context, Consumer<Boolean> consumer) {
        ((AppSearchManager) context.getSystemService(AppSearchManager.class)).createGlobalSearchSession(new AssistStateManager$$ExternalSyntheticLambda2(2), new AssistStateManager$$ExternalSyntheticLambda1(this, consumer, 1));
    }

    private boolean isInMultiWindowMode(ComponentName componentName) {
        ActivityInfo activityInfo = getActivityInfo(componentName);
        return activityInfo == null || activityInfo.resizeMode != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closeGlobalSearchSession$1(GlobalSearchSession globalSearchSession) {
        try {
            globalSearchSession.unregisterObserverCallback(OMNI_PACKAGE, this.mObserverCallback);
        } catch (AppSearchException e) {
            Log.w(TAG, "close AppSearchException = " + e);
        }
        Log.i(TAG, "close searchSession.close");
        globalSearchSession.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initGlobalSearchSession$2(Consumer consumer, AppSearchResult appSearchResult) {
        Log.i(TAG, "initGlobalSearchSession result = " + appSearchResult);
        boolean isSuccess = appSearchResult.isSuccess();
        this.mIsInitSession = isSuccess;
        if (isSuccess) {
            Log.i(TAG, "initGlobalSearchSession success");
            GlobalSearchSession globalSearchSession = (GlobalSearchSession) appSearchResult.getResultValue();
            registerSearchSessionObserver(globalSearchSession);
            this.mGlobalSearchSessionFuture.complete(globalSearchSession);
        } else {
            Log.i(TAG, "initGlobalSearchSession failed");
            this.mGlobalSearchSessionFuture.completeExceptionally(new AppSearchException(appSearchResult.getResultCode(), appSearchResult.getErrorMessage()));
        }
        if (consumer != null) {
            NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("initGlobalSearchSession callback is not null : mIsInitSeesion = "), this.mIsInitSession, TAG);
            consumer.accept(Boolean.valueOf(this.mIsInitSession));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Intent intent) {
        requestUpdateOmniPackageInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestUpdateOmniPackageInfo$3() {
        boolean z = true;
        this.mIsInMultiWindow = isInMultiWindowMode(COMPONENT_NAME_GOOGLE_SEARCH) || isInMultiWindowMode(COMPONENT_NAME_GOOGLE_SEARCH_INTRO);
        try {
            if (this.mContext.getPackageManager().getApplicationEnabledSetting(OMNI_PACKAGE) > 1) {
                z = false;
            }
            this.mIsOmniPackageEnabled = z;
        } catch (IllegalArgumentException unused) {
            this.mIsOmniPackageEnabled = false;
            Log.w(TAG, "requestUpdateOmniPackageInfo getApplicationEnabledSetting IllegalArgumentException");
        }
        NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("requestUpdateOmniPackageInfo mIsOmniPackageEnabled = "), this.mIsOmniPackageEnabled, TAG);
        try {
            this.mIsOmniAware = this.mContext.getPackageManager().getProperty(OMNI_PROPERTY, OMNI_PACKAGE).getBoolean();
            Log.i(TAG, "requestUpdateOmniPackageInfo mIsOmniAware = " + this.mIsOmniAware);
        } catch (PackageManager.NameNotFoundException unused2) {
            this.mIsOmniAware = false;
            Log.w(TAG, "requestUpdateOmniPackageInfo mIsOmniAware failed = " + this.mIsOmniAware);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateIsOmniAvailableFromAppSearch$4(final Consumer consumer, GlobalSearchSession globalSearchSession) {
        globalSearchSession.getByDocumentId(OMNI_PACKAGE, OMNI_APP_SEARCH_DATABASE, this.mGetByDocumentIdRequest, new AssistStateManager$$ExternalSyntheticLambda2(3), new BatchResultCallback<String, GenericDocument>() { // from class: com.android.systemui.plugins.omni.AssistStateManager.2
            @Override // android.app.appsearch.BatchResultCallback
            public void onResult(AppSearchBatchResult<String, GenericDocument> appSearchBatchResult) {
                boolean z;
                try {
                    Log.i(AssistStateManager.TAG, "onResult getSuccesses.isEmpty = " + appSearchBatchResult.getSuccesses().isEmpty());
                    GenericDocument genericDocument = appSearchBatchResult.getSuccesses().values().stream().findFirst().get();
                    AssistStateManager.this.mIsOmniAvailable = genericDocument.getPropertyBoolean(AssistStateManager.OMNI_APP_SEARCH_PROPERTY);
                    Set<String> propertyNames = genericDocument.getPropertyNames();
                    if (propertyNames.contains(AssistStateManager.OMNI_APP_SEARCH_CS_HELPER_AVAILABLE_PROPERTY)) {
                        AssistStateManager.this.mIsCsHelperAvailable = Optional.of(Boolean.valueOf(genericDocument.getPropertyBoolean(AssistStateManager.OMNI_APP_SEARCH_CS_HELPER_AVAILABLE_PROPERTY)));
                    }
                    if (propertyNames.contains(AssistStateManager.OMNI_APP_SEARCH_VIS_AVAILABLE_PROPERTY)) {
                        AssistStateManager.this.mIsVisAvailable = Optional.of(Boolean.valueOf(genericDocument.getPropertyBoolean(AssistStateManager.OMNI_APP_SEARCH_VIS_AVAILABLE_PROPERTY)));
                    }
                    StringBuilder sb = new StringBuilder("onResult mIsOmniAvailable = ");
                    sb.append(AssistStateManager.this.mIsOmniAvailable);
                    sb.append(", isCsHelperAvailable = ");
                    Optional optional = AssistStateManager.this.mIsCsHelperAvailable;
                    Boolean bool = Boolean.FALSE;
                    sb.append(optional.orElse(bool));
                    sb.append(", isVisAvailable = ");
                    sb.append(AssistStateManager.this.mIsVisAvailable.orElse(bool));
                    Log.i(AssistStateManager.TAG, sb.toString());
                } catch (Exception e) {
                    Log.w(AssistStateManager.TAG, "onResult exception = " + e);
                }
                Consumer consumer2 = consumer;
                if (consumer2 != null) {
                    if (!AssistStateManager.this.mIsOmniAvailable) {
                        Optional optional2 = AssistStateManager.this.mIsCsHelperAvailable;
                        Boolean bool2 = Boolean.FALSE;
                        if (!((Boolean) optional2.orElse(bool2)).booleanValue() && !((Boolean) AssistStateManager.this.mIsVisAvailable.orElse(bool2)).booleanValue()) {
                            z = false;
                            consumer2.accept(Boolean.valueOf(z));
                        }
                    }
                    z = true;
                    consumer2.accept(Boolean.valueOf(z));
                }
            }

            @Override // android.app.appsearch.BatchResultCallback
            public void onSystemError(Throwable th) {
                Log.w(AssistStateManager.TAG, "onSystemError = " + th.getMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Void lambda$updateIsOmniAvailableFromAppSearch$5(Throwable th) {
        Log.w(TAG, "updateIsOmniAvailableFromAppSearch exception = " + th);
        return null;
    }

    private void registerSearchSessionObserver(GlobalSearchSession globalSearchSession) {
        Log.i(TAG, "registerSearchSessionObserver searchSession = " + globalSearchSession);
        try {
            globalSearchSession.registerObserverCallback(OMNI_PACKAGE, new ObserverSpec.Builder().build(), new AssistStateManager$$ExternalSyntheticLambda2(1), this.mObserverCallback);
        } catch (AppSearchException e) {
            Log.w(TAG, "registerSearchSessionObserver AppSearchException = " + e);
        }
    }

    private void requestUpdateOmniPackageInfo() {
        Log.i(TAG, "requestUpdateOmniPackageInfo");
        THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.android.systemui.plugins.omni.AssistStateManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                AssistStateManager.this.lambda$requestUpdateOmniPackageInfo$3();
            }
        });
    }

    public void close() {
        Log.i(TAG, "close");
        closeGlobalSearchSession();
        this.mOmniPackageReceiver.unregisterReceiverSafely(this.mContext);
    }

    public void closeGlobalSearchSession() {
        this.mGlobalSearchSessionFuture.thenAcceptAsync((Consumer<? super GlobalSearchSession>) new AssistStateManager$$ExternalSyntheticLambda0(this, 1));
    }

    public void initSearchSession(Context context, Consumer<Boolean> consumer) {
        closeGlobalSearchSession();
        this.mGlobalSearchSessionFuture = new CompletableFuture<>();
        initGlobalSearchSession(context, consumer);
    }

    public Optional<Boolean> isCsHelperAvailable() {
        Log.d(TAG, "isCsHelperAvailable mIsOmniAware = " + this.mIsOmniAware + ", mIsCsHelperAvailable = " + this.mIsCsHelperAvailable);
        return !this.mIsOmniAware ? Optional.of(Boolean.FALSE) : this.mIsCsHelperAvailable;
    }

    public boolean isInMultiWindow() {
        NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("isInMultiWindow mIsInMultiWindow = "), this.mIsInMultiWindow, TAG);
        return this.mIsInMultiWindow;
    }

    public boolean isInitSession() {
        return this.mIsInitSession;
    }

    public boolean isOmniAvailable() {
        StringBuilder sb = new StringBuilder("isOmniAvailable mIsOmniAware = ");
        sb.append(this.mIsOmniAware);
        sb.append(", mIsOmniAvailable = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mIsOmniAvailable, TAG);
        if (this.mIsOmniAware) {
            return this.mIsOmniAvailable;
        }
        return false;
    }

    public boolean isOmniPackageEnabled() {
        NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("isOmniPackageEnabled mIsOmniPackageEnabled = "), this.mIsOmniPackageEnabled, TAG);
        return this.mIsOmniPackageEnabled;
    }

    public Optional<Boolean> isVisAvailable() {
        Log.d(TAG, "isVisAvailable mIsOmniAware = " + this.mIsOmniAware + ", mIsVisAvailable = " + this.mIsVisAvailable);
        return !this.mIsOmniAware ? Optional.of(Boolean.FALSE) : this.mIsVisAvailable;
    }

    public void updateIsOmniAvailableFromAppSearch(Consumer<Boolean> consumer) {
        Log.i(TAG, "updateIsOmniAvailableFromAppSearch");
        int i = 0;
        this.mGlobalSearchSessionFuture.thenAcceptAsync((Consumer<? super GlobalSearchSession>) new AssistStateManager$$ExternalSyntheticLambda1(this, consumer, i), (Executor) new AssistStateManager$$ExternalSyntheticLambda2(i)).exceptionally((Function<Throwable, ? extends Void>) new AssistStateManager$$ExternalSyntheticLambda3());
    }
}
