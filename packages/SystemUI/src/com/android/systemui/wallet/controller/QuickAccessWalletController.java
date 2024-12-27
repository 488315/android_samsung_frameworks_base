package com.android.systemui.wallet.controller;

import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.service.quickaccesswallet.GetWalletCardsRequest;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class QuickAccessWalletController {
    public static final long RECREATION_TIME_WINDOW = TimeUnit.MINUTES.toMillis(10);
    public final Executor mBgExecutor;
    public final SystemClock mClock;
    public final Context mContext;
    public AnonymousClass1 mDefaultPaymentAppObserver;
    public QuickAccessWalletController$$ExternalSyntheticLambda0 mDefaultWalletAppObserver;
    public final Executor mExecutor;
    public long mQawClientCreatedTimeMillis;
    public QuickAccessWalletClient mQuickAccessWalletClient;
    public final RoleManager mRoleManager;
    public final SecureSettings mSecureSettings;
    public AnonymousClass2 mWalletPreferenceObserver;
    public int mWalletPreferenceChangeEvents = 0;
    public int mDefaultPaymentAppChangeEvents = 0;
    public int mDefaultWalletAppChangeEvents = 0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.wallet.controller.QuickAccessWalletController$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContentObserver {
        public final /* synthetic */ QuickAccessWalletClient.OnWalletCardsRetrievedCallback val$cardsRetriever;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Handler handler, QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback) {
            super(handler);
            this.val$cardsRetriever = onWalletCardsRetrievedCallback;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            QuickAccessWalletController.this.mExecutor.execute(new QuickAccessWalletController$$ExternalSyntheticLambda1(this, this.val$cardsRetriever, 1));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.wallet.controller.QuickAccessWalletController$2, reason: invalid class name */
    public final class AnonymousClass2 extends ContentObserver {
        public AnonymousClass2(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            QuickAccessWalletController.this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallet.controller.QuickAccessWalletController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    QuickAccessWalletController.this.updateWalletPreference();
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public enum WalletChangeEvent {
        DEFAULT_PAYMENT_APP_CHANGE,
        DEFAULT_WALLET_APP_CHANGE,
        WALLET_PREFERENCE_CHANGE
    }

    public QuickAccessWalletController(Context context, Executor executor, Executor executor2, SecureSettings secureSettings, QuickAccessWalletClient quickAccessWalletClient, SystemClock systemClock, RoleManager roleManager) {
        this.mContext = context;
        this.mExecutor = executor;
        this.mBgExecutor = executor2;
        this.mSecureSettings = secureSettings;
        this.mRoleManager = roleManager;
        this.mQuickAccessWalletClient = quickAccessWalletClient;
        this.mClock = systemClock;
        this.mQawClientCreatedTimeMillis = systemClock.elapsedRealtime();
    }

    public final void queryWalletCards(QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback, int i) {
        if (this.mClock.elapsedRealtime() - this.mQawClientCreatedTimeMillis > RECREATION_TIME_WINDOW) {
            Log.i("QAWController", "Re-creating the QAW client to avoid stale.");
            reCreateWalletClient();
        }
        if (!this.mQuickAccessWalletClient.isWalletFeatureAvailable()) {
            Log.d("QAWController", "QuickAccessWallet feature is not available.");
        } else {
            this.mQuickAccessWalletClient.getWalletCards(this.mBgExecutor, new GetWalletCardsRequest(this.mContext.getResources().getDimensionPixelSize(R.dimen.wallet_tile_card_view_width), this.mContext.getResources().getDimensionPixelSize(R.dimen.wallet_tile_card_view_height), this.mContext.getResources().getDimensionPixelSize(R.dimen.wallet_icon_size), i), onWalletCardsRetrievedCallback);
        }
    }

    public final void reCreateWalletClient() {
        this.mQuickAccessWalletClient = QuickAccessWalletClient.create(this.mContext, this.mBgExecutor);
        this.mQawClientCreatedTimeMillis = this.mClock.elapsedRealtime();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v4, types: [android.app.role.OnRoleHoldersChangedListener, com.android.systemui.wallet.controller.QuickAccessWalletController$$ExternalSyntheticLambda0] */
    public final void setupWalletChangeObservers(final QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback, WalletChangeEvent... walletChangeEventArr) {
        for (WalletChangeEvent walletChangeEvent : walletChangeEventArr) {
            WalletChangeEvent walletChangeEvent2 = WalletChangeEvent.WALLET_PREFERENCE_CHANGE;
            SecureSettings secureSettings = this.mSecureSettings;
            if (walletChangeEvent == walletChangeEvent2) {
                if (this.mWalletPreferenceObserver == null) {
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
                    this.mWalletPreferenceObserver = anonymousClass2;
                    secureSettings.registerContentObserverForUserSync("lockscreen_show_wallet", false, (ContentObserver) anonymousClass2, -1);
                }
                this.mWalletPreferenceChangeEvents++;
            } else if (walletChangeEvent == WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE) {
                if (this.mDefaultPaymentAppObserver == null) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(null, onWalletCardsRetrievedCallback);
                    this.mDefaultPaymentAppObserver = anonymousClass1;
                    secureSettings.registerContentObserverForUserSync("nfc_payment_default_component", false, (ContentObserver) anonymousClass1, -1);
                }
                this.mDefaultPaymentAppChangeEvents++;
            } else if (walletChangeEvent == WalletChangeEvent.DEFAULT_WALLET_APP_CHANGE) {
                if (this.mDefaultWalletAppObserver == null) {
                    ?? r3 = new OnRoleHoldersChangedListener() { // from class: com.android.systemui.wallet.controller.QuickAccessWalletController$$ExternalSyntheticLambda0
                        public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
                            QuickAccessWalletController quickAccessWalletController = QuickAccessWalletController.this;
                            QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback2 = onWalletCardsRetrievedCallback;
                            quickAccessWalletController.getClass();
                            if (str.equals("android.app.role.WALLET")) {
                                quickAccessWalletController.mExecutor.execute(new QuickAccessWalletController$$ExternalSyntheticLambda1(quickAccessWalletController, onWalletCardsRetrievedCallback2, 0));
                            }
                        }
                    };
                    this.mDefaultWalletAppObserver = r3;
                    this.mRoleManager.addOnRoleHoldersChangedListenerAsUser(this.mExecutor, r3, UserHandle.ALL);
                }
                this.mDefaultWalletAppChangeEvents++;
            }
        }
    }

    public final void unregisterWalletChangeObservers(WalletChangeEvent... walletChangeEventArr) {
        QuickAccessWalletController$$ExternalSyntheticLambda0 quickAccessWalletController$$ExternalSyntheticLambda0;
        AnonymousClass1 anonymousClass1;
        AnonymousClass2 anonymousClass2;
        for (WalletChangeEvent walletChangeEvent : walletChangeEventArr) {
            WalletChangeEvent walletChangeEvent2 = WalletChangeEvent.WALLET_PREFERENCE_CHANGE;
            SecureSettings secureSettings = this.mSecureSettings;
            if (walletChangeEvent == walletChangeEvent2 && (anonymousClass2 = this.mWalletPreferenceObserver) != null) {
                int i = this.mWalletPreferenceChangeEvents - 1;
                this.mWalletPreferenceChangeEvents = i;
                if (i == 0) {
                    secureSettings.unregisterContentObserverSync(anonymousClass2);
                }
            } else if (walletChangeEvent == WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE && (anonymousClass1 = this.mDefaultPaymentAppObserver) != null) {
                int i2 = this.mDefaultPaymentAppChangeEvents - 1;
                this.mDefaultPaymentAppChangeEvents = i2;
                if (i2 == 0) {
                    secureSettings.unregisterContentObserverSync(anonymousClass1);
                }
            } else if (walletChangeEvent == WalletChangeEvent.DEFAULT_WALLET_APP_CHANGE && (quickAccessWalletController$$ExternalSyntheticLambda0 = this.mDefaultWalletAppObserver) != null) {
                int i3 = this.mDefaultWalletAppChangeEvents - 1;
                this.mDefaultWalletAppChangeEvents = i3;
                if (i3 == 0) {
                    this.mRoleManager.removeOnRoleHoldersChangedListenerAsUser(quickAccessWalletController$$ExternalSyntheticLambda0, UserHandle.ALL);
                }
            }
        }
    }

    public final void updateWalletPreference() {
        if (this.mQuickAccessWalletClient.isWalletServiceAvailable() && this.mQuickAccessWalletClient.isWalletFeatureAvailable()) {
            this.mQuickAccessWalletClient.isWalletFeatureAvailableWhenDeviceLocked();
        }
    }
}
