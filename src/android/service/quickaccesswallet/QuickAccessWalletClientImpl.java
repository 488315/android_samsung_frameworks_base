package android.service.quickaccesswallet;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.quickaccesswallet.IQuickAccessWalletService;
import android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet.Flags;
import com.android.internal.widget.LockPatternUtils;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class QuickAccessWalletClientImpl implements QuickAccessWalletClient, ServiceConnection {
    private static final int MSG_TIMEOUT_SERVICE = 5;
    private static final long SERVICE_CONNECTION_TIMEOUT_MS = 60000;
    public static final String SETTING_KEY = "lockscreen_show_wallet";
    private static final String TAG = "QAWalletSClient";
    private final Context mContext;
    private final Map<QuickAccessWalletClient.WalletServiceEventListener, String> mEventListeners;
    private boolean mIsConnected;
    private final Executor mLifecycleExecutor;
    private final Queue<ApiCaller> mRequestQueue;
    private IQuickAccessWalletService mService;
    private QuickAccessWalletServiceInfo mServiceInfo = null;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }

    QuickAccessWalletClientImpl(Context context, Executor executor) {
        this.mContext = context.getApplicationContext();
        this.mLifecycleExecutor = executor == null ? new PendingIntent$$ExternalSyntheticLambda0() : executor;
        this.mRequestQueue = new ArrayDeque();
        this.mEventListeners = new HashMap(1);
    }

    private QuickAccessWalletServiceInfo ensureServiceInfo() {
        if (this.mServiceInfo == null) {
            this.mServiceInfo = QuickAccessWalletServiceInfo.tryCreate(this.mContext);
        }
        return this.mServiceInfo;
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public boolean isWalletServiceAvailable() {
        return ensureServiceInfo() != null;
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public boolean isWalletFeatureAvailable() {
        int currentUser = ActivityManager.getCurrentUser();
        return currentUser == 0 && checkUserSetupComplete() && !new LockPatternUtils(this.mContext).isUserInLockdown(currentUser);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public boolean isWalletFeatureAvailableWhenDeviceLocked() {
        return checkSecureSetting("lockscreen_show_wallet");
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void getWalletCards(GetWalletCardsRequest getWalletCardsRequest, QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback) {
        getWalletCards(this.mContext.getMainExecutor(), getWalletCardsRequest, onWalletCardsRetrievedCallback);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void getWalletCards(Executor executor, final GetWalletCardsRequest getWalletCardsRequest, final QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback) {
        if (!isWalletServiceAvailable()) {
            executor.execute(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    QuickAccessWalletClient.OnWalletCardsRetrievedCallback.this.onWalletCardRetrievalError(new GetWalletCardsError(null, null));
                }
            });
        } else {
            final AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, executor, onWalletCardsRetrievedCallback);
            executeApiCall(new ApiCaller(this, "onWalletCardsRequested") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.2
                @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
                public void performApiCall(IQuickAccessWalletService iQuickAccessWalletService) throws RemoteException {
                    iQuickAccessWalletService.onWalletCardsRequested(getWalletCardsRequest, anonymousClass1);
                }

                @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
                public void onApiError() {
                    anonymousClass1.onGetWalletCardsFailure(new GetWalletCardsError(null, null));
                }
            });
        }
    }

    /* renamed from: android.service.quickaccesswallet.QuickAccessWalletClientImpl$1, reason: invalid class name */
    class AnonymousClass1 extends BaseCallbacks {
        final /* synthetic */ QuickAccessWalletClient.OnWalletCardsRetrievedCallback val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(QuickAccessWalletClientImpl quickAccessWalletClientImpl, Executor executor, QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback) {
            super();
            this.val$executor = executor;
            this.val$callback = onWalletCardsRetrievedCallback;
        }

        @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onGetWalletCardsSuccess(final GetWalletCardsResponse getWalletCardsResponse) {
            Executor executor = this.val$executor;
            final QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    QuickAccessWalletClient.OnWalletCardsRetrievedCallback.this.onWalletCardsRetrieved(getWalletCardsResponse);
                }
            });
        }

        @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onGetWalletCardsFailure(final GetWalletCardsError getWalletCardsError) {
            Executor executor = this.val$executor;
            final QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    QuickAccessWalletClient.OnWalletCardsRetrievedCallback.this.onWalletCardRetrievalError(getWalletCardsError);
                }
            });
        }
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void selectWalletCard(final SelectWalletCardRequest selectWalletCardRequest) {
        if (isWalletServiceAvailable()) {
            executeApiCall(new ApiCaller(this, "onWalletCardSelected") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.3
                @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
                public void performApiCall(IQuickAccessWalletService iQuickAccessWalletService) throws RemoteException {
                    iQuickAccessWalletService.onWalletCardSelected(selectWalletCardRequest);
                }
            });
        }
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void notifyWalletDismissed() {
        if (isWalletServiceAvailable()) {
            executeApiCall(new ApiCaller(this, "onWalletDismissed") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.4
                @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
                public void performApiCall(IQuickAccessWalletService iQuickAccessWalletService) throws RemoteException {
                    iQuickAccessWalletService.onWalletDismissed();
                }
            });
        }
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void addWalletServiceEventListener(QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener) {
        addWalletServiceEventListener(this.mContext.getMainExecutor(), walletServiceEventListener);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void addWalletServiceEventListener(Executor executor, final QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener) {
        if (isWalletServiceAvailable()) {
            final AnonymousClass5 anonymousClass5 = new AnonymousClass5(this, executor, walletServiceEventListener);
            executeApiCall(new ApiCaller("registerListener") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.6
                @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
                public void performApiCall(IQuickAccessWalletService iQuickAccessWalletService) throws RemoteException {
                    String uuid = UUID.randomUUID().toString();
                    WalletServiceEventListenerRequest walletServiceEventListenerRequest = new WalletServiceEventListenerRequest(uuid);
                    QuickAccessWalletClientImpl.this.mEventListeners.put(walletServiceEventListener, uuid);
                    iQuickAccessWalletService.registerWalletServiceEventListener(walletServiceEventListenerRequest, anonymousClass5);
                }
            });
        }
    }

    /* renamed from: android.service.quickaccesswallet.QuickAccessWalletClientImpl$5, reason: invalid class name */
    class AnonymousClass5 extends BaseCallbacks {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ QuickAccessWalletClient.WalletServiceEventListener val$listener;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass5(QuickAccessWalletClientImpl quickAccessWalletClientImpl, Executor executor, QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener) {
            super();
            this.val$executor = executor;
            this.val$listener = walletServiceEventListener;
        }

        @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onWalletServiceEvent(final WalletServiceEvent walletServiceEvent) {
            Executor executor = this.val$executor;
            final QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener = this.val$listener;
            executor.execute(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    QuickAccessWalletClient.WalletServiceEventListener.this.onWalletServiceEvent(walletServiceEvent);
                }
            });
        }
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void removeWalletServiceEventListener(final QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener) {
        if (isWalletServiceAvailable()) {
            executeApiCall(new ApiCaller("unregisterListener") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.7
                @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
                public void performApiCall(IQuickAccessWalletService iQuickAccessWalletService) throws RemoteException {
                    String str = (String) QuickAccessWalletClientImpl.this.mEventListeners.remove(walletServiceEventListener);
                    if (str == null) {
                        return;
                    }
                    iQuickAccessWalletService.unregisterWalletServiceEventListener(new WalletServiceEventListenerRequest(str));
                }
            });
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        disconnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnect$1() {
        disconnectInternal(true);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void disconnect() {
        this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                QuickAccessWalletClientImpl.this.lambda$disconnect$1();
            }
        });
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public Intent createWalletIntent() {
        QuickAccessWalletServiceInfo ensureServiceInfo = ensureServiceInfo();
        if (ensureServiceInfo == null) {
            return null;
        }
        return createIntent(ensureServiceInfo.getWalletActivity(), ensureServiceInfo.getComponentName().getPackageName(), ensureServiceInfo.getUserId(), QuickAccessWalletService.ACTION_VIEW_WALLET);
    }

    /* renamed from: android.service.quickaccesswallet.QuickAccessWalletClientImpl$8, reason: invalid class name */
    class AnonymousClass8 extends BaseCallbacks {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ QuickAccessWalletClient.WalletPendingIntentCallback val$pendingIntentCallback;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass8(QuickAccessWalletClientImpl quickAccessWalletClientImpl, Executor executor, QuickAccessWalletClient.WalletPendingIntentCallback walletPendingIntentCallback) {
            super();
            this.val$executor = executor;
            this.val$pendingIntentCallback = walletPendingIntentCallback;
        }

        @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onTargetActivityPendingIntentReceived(final PendingIntent pendingIntent) {
            Executor executor = this.val$executor;
            final QuickAccessWalletClient.WalletPendingIntentCallback walletPendingIntentCallback = this.val$pendingIntentCallback;
            executor.execute(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    QuickAccessWalletClient.WalletPendingIntentCallback.this.onWalletPendingIntentRetrieved(pendingIntent);
                }
            });
        }
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void getWalletPendingIntent(Executor executor, QuickAccessWalletClient.WalletPendingIntentCallback walletPendingIntentCallback) {
        final AnonymousClass8 anonymousClass8 = new AnonymousClass8(this, executor, walletPendingIntentCallback);
        executeApiCall(new ApiCaller(this, "getTargetActivityPendingIntent") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.9
            @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
            void performApiCall(IQuickAccessWalletService iQuickAccessWalletService) throws RemoteException {
                iQuickAccessWalletService.onTargetActivityIntentRequested(anonymousClass8);
            }
        });
    }

    /* renamed from: android.service.quickaccesswallet.QuickAccessWalletClientImpl$10, reason: invalid class name */
    class AnonymousClass10 extends BaseCallbacks {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ QuickAccessWalletClient.GesturePendingIntentCallback val$gesturePendingIntentCallback;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass10(QuickAccessWalletClientImpl quickAccessWalletClientImpl, Executor executor, QuickAccessWalletClient.GesturePendingIntentCallback gesturePendingIntentCallback) {
            super();
            this.val$executor = executor;
            this.val$gesturePendingIntentCallback = gesturePendingIntentCallback;
        }

        @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onGestureTargetActivityPendingIntentReceived(final PendingIntent pendingIntent) {
            if (Flags.launchWalletOptionOnPowerDoubleTap()) {
                Executor executor = this.val$executor;
                final QuickAccessWalletClient.GesturePendingIntentCallback gesturePendingIntentCallback = this.val$gesturePendingIntentCallback;
                executor.execute(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$10$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        QuickAccessWalletClient.GesturePendingIntentCallback.this.onGesturePendingIntentRetrieved(pendingIntent);
                    }
                });
            }
        }
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void getGestureTargetActivityPendingIntent(Executor executor, QuickAccessWalletClient.GesturePendingIntentCallback gesturePendingIntentCallback) {
        final AnonymousClass10 anonymousClass10 = new AnonymousClass10(this, executor, gesturePendingIntentCallback);
        executeApiCall(new ApiCaller(this, "getGestureTargetActivityPendingIntent") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.11
            @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
            void performApiCall(IQuickAccessWalletService iQuickAccessWalletService) throws RemoteException {
                iQuickAccessWalletService.onGestureTargetActivityIntentRequested(anonymousClass10);
            }
        });
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public Intent createWalletSettingsIntent() {
        QuickAccessWalletServiceInfo ensureServiceInfo = ensureServiceInfo();
        if (ensureServiceInfo == null) {
            return null;
        }
        return createIntent(ensureServiceInfo.getSettingsActivity(), ensureServiceInfo.getComponentName().getPackageName(), UserHandle.myUserId(), QuickAccessWalletService.ACTION_VIEW_WALLET_SETTINGS);
    }

    private Intent createIntent(String str, String str2, int i, String str3) {
        PackageManager packageManager = this.mContext.createContextAsUser(UserHandle.of(i), 0).getPackageManager();
        if (TextUtils.isEmpty(str)) {
            str = queryActivityForAction(packageManager, str2, str3);
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ComponentName componentName = new ComponentName(str2, str);
        if (isActivityEnabled(packageManager, componentName)) {
            return new Intent(str3).setComponent(componentName);
        }
        return null;
    }

    private static String queryActivityForAction(PackageManager packageManager, String str, String str2) {
        ResolveInfo resolveActivity = packageManager.resolveActivity(new Intent(str2).setPackage(str), 0);
        if (resolveActivity == null || resolveActivity.activityInfo == null || !resolveActivity.activityInfo.exported) {
            return null;
        }
        return resolveActivity.activityInfo.name;
    }

    private static boolean isActivityEnabled(PackageManager packageManager, ComponentName componentName) {
        int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
        if (componentEnabledSetting == 1) {
            return true;
        }
        if (componentEnabledSetting != 0) {
            return false;
        }
        try {
            return packageManager.getActivityInfo(componentName, 0).isEnabled();
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public Drawable getLogo() {
        QuickAccessWalletServiceInfo ensureServiceInfo = ensureServiceInfo();
        if (ensureServiceInfo == null) {
            return null;
        }
        return ensureServiceInfo.getWalletLogo(this.mContext);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public Drawable getTileIcon() {
        QuickAccessWalletServiceInfo ensureServiceInfo = ensureServiceInfo();
        if (ensureServiceInfo == null) {
            return null;
        }
        return ensureServiceInfo.getTileIcon();
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public UserHandle getUser() {
        QuickAccessWalletServiceInfo ensureServiceInfo = ensureServiceInfo();
        if (ensureServiceInfo == null) {
            return null;
        }
        return UserHandle.of(ensureServiceInfo.getUserId());
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public CharSequence getServiceLabel() {
        QuickAccessWalletServiceInfo ensureServiceInfo = ensureServiceInfo();
        if (ensureServiceInfo == null) {
            return null;
        }
        return ensureServiceInfo.getServiceLabel(this.mContext);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public CharSequence getShortcutShortLabel() {
        QuickAccessWalletServiceInfo ensureServiceInfo = ensureServiceInfo();
        if (ensureServiceInfo == null) {
            return null;
        }
        return ensureServiceInfo.getShortcutShortLabel(this.mContext);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public CharSequence getShortcutLongLabel() {
        QuickAccessWalletServiceInfo ensureServiceInfo = ensureServiceInfo();
        if (ensureServiceInfo == null) {
            return null;
        }
        return ensureServiceInfo.getShortcutLongLabel(this.mContext);
    }

    private void connect() {
        this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                QuickAccessWalletClientImpl.this.connectInternal();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectInternal() {
        QuickAccessWalletServiceInfo ensureServiceInfo = ensureServiceInfo();
        if (ensureServiceInfo == null) {
            Log.w(TAG, "Wallet service unavailable");
            return;
        }
        if (this.mIsConnected) {
            return;
        }
        this.mIsConnected = true;
        final Intent intent = new Intent(QuickAccessWalletService.SERVICE_INTERFACE);
        intent.setComponent(ensureServiceInfo.getComponentName());
        final int i = 33;
        if (this.mServiceInfo == null) {
            this.mLifecycleExecutor.execute(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    QuickAccessWalletClientImpl.this.lambda$connectInternal$2(intent, i);
                }
            });
        } else {
            this.mLifecycleExecutor.execute(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    QuickAccessWalletClientImpl.this.lambda$connectInternal$3(intent, i);
                }
            });
        }
        resetServiceConnectionTimeout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectInternal$2(Intent intent, int i) {
        this.mContext.bindService(intent, this, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectInternal$3(Intent intent, int i) {
        this.mContext.bindServiceAsUser(intent, this, i, UserHandle.of(this.mServiceInfo.getUserId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onConnectedInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onServiceConnected$8(IQuickAccessWalletService iQuickAccessWalletService) {
        if (!this.mIsConnected) {
            Log.w(TAG, "onConnectInternal but connection closed");
            this.mService = null;
            return;
        }
        this.mService = iQuickAccessWalletService;
        Iterator it = new ArrayList(this.mRequestQueue).iterator();
        while (it.hasNext()) {
            ApiCaller apiCaller = (ApiCaller) it.next();
            performApiCallInternal(apiCaller, this.mService);
            this.mRequestQueue.remove(apiCaller);
        }
    }

    private void resetServiceConnectionTimeout() {
        this.mHandler.removeMessages(5);
        this.mHandler.postDelayed(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                QuickAccessWalletClientImpl.this.lambda$resetServiceConnectionTimeout$4();
            }
        }, 5, 60000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetServiceConnectionTimeout$4() {
        disconnectInternal(true);
    }

    private void disconnectInternal(boolean z) {
        if (!this.mIsConnected) {
            Log.w(TAG, "already disconnected");
            return;
        }
        if (z && !this.mEventListeners.isEmpty()) {
            Iterator<QuickAccessWalletClient.WalletServiceEventListener> it = this.mEventListeners.keySet().iterator();
            while (it.hasNext()) {
                removeWalletServiceEventListener(it.next());
            }
            this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    QuickAccessWalletClientImpl.this.lambda$disconnectInternal$5();
                }
            });
            return;
        }
        this.mIsConnected = false;
        this.mLifecycleExecutor.execute(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                QuickAccessWalletClientImpl.this.lambda$disconnectInternal$6();
            }
        });
        this.mService = null;
        this.mEventListeners.clear();
        this.mRequestQueue.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnectInternal$5() {
        disconnectInternal(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnectInternal$6() {
        this.mContext.unbindService(this);
    }

    private void executeApiCall(final ApiCaller apiCaller) {
        this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                QuickAccessWalletClientImpl.this.lambda$executeApiCall$7(apiCaller);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: executeInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$executeApiCall$7(ApiCaller apiCaller) {
        IQuickAccessWalletService iQuickAccessWalletService;
        if (this.mIsConnected && (iQuickAccessWalletService = this.mService) != null) {
            performApiCallInternal(apiCaller, iQuickAccessWalletService);
        } else {
            this.mRequestQueue.add(apiCaller);
            connect();
        }
    }

    private void performApiCallInternal(ApiCaller apiCaller, IQuickAccessWalletService iQuickAccessWalletService) {
        if (iQuickAccessWalletService == null) {
            apiCaller.onApiError();
            return;
        }
        try {
            apiCaller.performApiCall(iQuickAccessWalletService);
            resetServiceConnectionTimeout();
        } catch (RemoteException e) {
            Log.w(TAG, "executeInternal error: " + apiCaller.mDesc, e);
            apiCaller.onApiError();
            disconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class ApiCaller {
        private final String mDesc;

        abstract void performApiCall(IQuickAccessWalletService iQuickAccessWalletService) throws RemoteException;

        private ApiCaller(String str) {
            this.mDesc = str;
        }

        void onApiError() {
            Log.w(QuickAccessWalletClientImpl.TAG, "api error: " + this.mDesc);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        final IQuickAccessWalletService asInterface = IQuickAccessWalletService.Stub.asInterface(iBinder);
        this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                QuickAccessWalletClientImpl.this.lambda$onServiceConnected$8(asInterface);
            }
        });
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(ComponentName componentName) {
        disconnect();
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(ComponentName componentName) {
        disconnect();
    }

    private boolean checkSecureSetting(String str) {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), str, 0) == 1;
    }

    private boolean checkUserSetupComplete() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0, -2) == 1;
    }

    private static class BaseCallbacks extends IQuickAccessWalletServiceCallbacks.Stub {
        private BaseCallbacks() {
        }

        public void onGetWalletCardsSuccess(GetWalletCardsResponse getWalletCardsResponse) {
            throw new IllegalStateException();
        }

        public void onGetWalletCardsFailure(GetWalletCardsError getWalletCardsError) {
            throw new IllegalStateException();
        }

        public void onWalletServiceEvent(WalletServiceEvent walletServiceEvent) {
            throw new IllegalStateException();
        }

        public void onTargetActivityPendingIntentReceived(PendingIntent pendingIntent) {
            throw new IllegalStateException();
        }

        public void onGestureTargetActivityPendingIntentReceived(PendingIntent pendingIntent) {
            throw new IllegalStateException();
        }
    }
}
