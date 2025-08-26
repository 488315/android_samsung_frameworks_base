package android.service.quickaccesswallet;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.service.quickaccesswallet.IQuickAccessWalletService;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet.Flags;

/* loaded from: classes3.dex */
public abstract class QuickAccessWalletService extends Service {
    public static final String ACTION_VIEW_WALLET = "android.service.quickaccesswallet.action.VIEW_WALLET";
    public static final String ACTION_VIEW_WALLET_SETTINGS = "android.service.quickaccesswallet.action.VIEW_WALLET_SETTINGS";
    public static final String SERVICE_INTERFACE = "android.service.quickaccesswallet.QuickAccessWalletService";
    public static final String SERVICE_META_DATA = "android.quickaccesswallet";
    private static final String TAG = "QAWalletService";
    public static final String TILE_SERVICE_META_DATA = "android.quickaccesswallet.tile";
    private IQuickAccessWalletServiceCallbacks mEventListener;
    private String mEventListenerId;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final IQuickAccessWalletService mInterface = new AnonymousClass1();

    public PendingIntent getGestureTargetActivityPendingIntent() {
        return null;
    }

    public PendingIntent getTargetActivityPendingIntent() {
        return null;
    }

    public abstract void onWalletCardSelected(SelectWalletCardRequest selectWalletCardRequest);

    public abstract void onWalletCardsRequested(GetWalletCardsRequest getWalletCardsRequest, GetWalletCardsCallback getWalletCardsCallback);

    public abstract void onWalletDismissed();

    /* renamed from: android.service.quickaccesswallet.QuickAccessWalletService$1, reason: invalid class name */
    class AnonymousClass1 extends IQuickAccessWalletService.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWalletCardsRequested$0(GetWalletCardsRequest getWalletCardsRequest, IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            QuickAccessWalletService.this.onWalletCardsRequestedInternal(getWalletCardsRequest, iQuickAccessWalletServiceCallbacks);
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletCardsRequested(final GetWalletCardsRequest getWalletCardsRequest, final IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            QuickAccessWalletService.this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onWalletCardsRequested$0(getWalletCardsRequest, iQuickAccessWalletServiceCallbacks);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWalletCardSelected$1(SelectWalletCardRequest selectWalletCardRequest) {
            QuickAccessWalletService.this.onWalletCardSelected(selectWalletCardRequest);
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletCardSelected(final SelectWalletCardRequest selectWalletCardRequest) {
            QuickAccessWalletService.this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onWalletCardSelected$1(selectWalletCardRequest);
                }
            });
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletDismissed() {
            Handler handler = QuickAccessWalletService.this.mHandler;
            final QuickAccessWalletService quickAccessWalletService = QuickAccessWalletService.this;
            handler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    quickAccessWalletService.onWalletDismissed();
                }
            });
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onTargetActivityIntentRequested(final IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            QuickAccessWalletService.this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onTargetActivityIntentRequested$2(iQuickAccessWalletServiceCallbacks);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTargetActivityIntentRequested$2(IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            QuickAccessWalletService.this.onTargetActivityIntentRequestedInternal(iQuickAccessWalletServiceCallbacks);
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onGestureTargetActivityIntentRequested(final IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            if (Flags.launchWalletOptionOnPowerDoubleTap()) {
                QuickAccessWalletService.this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onGestureTargetActivityIntentRequested$3(iQuickAccessWalletServiceCallbacks);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGestureTargetActivityIntentRequested$3(IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            QuickAccessWalletService.this.onGestureTargetActivityIntentRequestedInternal(iQuickAccessWalletServiceCallbacks);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$registerWalletServiceEventListener$4(WalletServiceEventListenerRequest walletServiceEventListenerRequest, IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            QuickAccessWalletService.this.registerDismissWalletListenerInternal(walletServiceEventListenerRequest, iQuickAccessWalletServiceCallbacks);
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void registerWalletServiceEventListener(final WalletServiceEventListenerRequest walletServiceEventListenerRequest, final IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            QuickAccessWalletService.this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$registerWalletServiceEventListener$4(walletServiceEventListenerRequest, iQuickAccessWalletServiceCallbacks);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$unregisterWalletServiceEventListener$5(WalletServiceEventListenerRequest walletServiceEventListenerRequest) {
            QuickAccessWalletService.this.unregisterDismissWalletListenerInternal(walletServiceEventListenerRequest);
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void unregisterWalletServiceEventListener(final WalletServiceEventListenerRequest walletServiceEventListenerRequest) {
            QuickAccessWalletService.this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$unregisterWalletServiceEventListener$5(walletServiceEventListenerRequest);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onWalletCardsRequestedInternal(GetWalletCardsRequest getWalletCardsRequest, IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
        onWalletCardsRequested(getWalletCardsRequest, new GetWalletCardsCallbackImpl(getWalletCardsRequest, iQuickAccessWalletServiceCallbacks, this.mHandler, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTargetActivityIntentRequestedInternal(IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
        try {
            iQuickAccessWalletServiceCallbacks.onTargetActivityPendingIntentReceived(getTargetActivityPendingIntent());
        } catch (RemoteException e) {
            Log.w(TAG, "Error returning wallet cards", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGestureTargetActivityIntentRequestedInternal(IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
        if (Flags.launchWalletOptionOnPowerDoubleTap()) {
            try {
                iQuickAccessWalletServiceCallbacks.onGestureTargetActivityPendingIntentReceived(getGestureTargetActivityPendingIntent());
            } catch (RemoteException e) {
                Log.w(TAG, "Error", e);
            }
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            Log.w(TAG, "Wrong action");
            return null;
        }
        return this.mInterface.asBinder();
    }

    public final void sendWalletServiceEvent(final WalletServiceEvent walletServiceEvent) {
        this.mHandler.post(new Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sendWalletServiceEvent$0(walletServiceEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendWalletServiceEventInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$sendWalletServiceEvent$0(WalletServiceEvent walletServiceEvent) {
        IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks = this.mEventListener;
        if (iQuickAccessWalletServiceCallbacks == null) {
            Log.i(TAG, "No dismiss listener registered");
            return;
        }
        try {
            iQuickAccessWalletServiceCallbacks.onWalletServiceEvent(walletServiceEvent);
        } catch (RemoteException e) {
            Log.w(TAG, "onWalletServiceEvent error", e);
            this.mEventListenerId = null;
            this.mEventListener = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerDismissWalletListenerInternal(WalletServiceEventListenerRequest walletServiceEventListenerRequest, IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
        this.mEventListenerId = walletServiceEventListenerRequest.getListenerId();
        this.mEventListener = iQuickAccessWalletServiceCallbacks;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterDismissWalletListenerInternal(WalletServiceEventListenerRequest walletServiceEventListenerRequest) {
        String str = this.mEventListenerId;
        if (str != null && str.equals(walletServiceEventListenerRequest.getListenerId())) {
            this.mEventListenerId = null;
            this.mEventListener = null;
        } else {
            Log.w(TAG, "dismiss listener missing or replaced");
        }
    }
}
