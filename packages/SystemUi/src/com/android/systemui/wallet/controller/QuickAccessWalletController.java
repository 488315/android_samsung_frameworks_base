package com.android.systemui.wallet.controller;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.service.quickaccesswallet.GetWalletCardsRequest;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QuickAccessWalletController {
    public static final long RECREATION_TIME_WINDOW = TimeUnit.MINUTES.toMillis(10);
    public final Executor mBgExecutor;
    public final SystemClock mClock;
    public final Context mContext;
    public C36491 mDefaultPaymentAppObserver;
    public final Executor mExecutor;
    public long mQawClientCreatedTimeMillis;
    public QuickAccessWalletClient mQuickAccessWalletClient;
    public final SecureSettings mSecureSettings;
    public C36502 mWalletPreferenceObserver;
    public int mWalletPreferenceChangeEvents = 0;
    public int mDefaultPaymentAppChangeEvents = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallet.controller.QuickAccessWalletController$1 */
    public final class C36491 extends ContentObserver {
        public final /* synthetic */ QuickAccessWalletClient.OnWalletCardsRetrievedCallback val$cardsRetriever;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C36491(Handler handler, QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback) {
            super(handler);
            this.val$cardsRetriever = onWalletCardsRetrievedCallback;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            Executor executor = QuickAccessWalletController.this.mExecutor;
            final QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback = this.val$cardsRetriever;
            executor.execute(new Runnable() { // from class: com.android.systemui.wallet.controller.QuickAccessWalletController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    QuickAccessWalletController.C36491 c36491 = QuickAccessWalletController.C36491.this;
                    QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback2 = onWalletCardsRetrievedCallback;
                    QuickAccessWalletController.this.reCreateWalletClient();
                    QuickAccessWalletController.this.updateWalletPreference();
                    QuickAccessWalletController.this.queryWalletCards(onWalletCardsRetrievedCallback2);
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallet.controller.QuickAccessWalletController$2 */
    public final class C36502 extends ContentObserver {
        public C36502(Handler handler) {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum WalletChangeEvent {
        DEFAULT_PAYMENT_APP_CHANGE,
        WALLET_PREFERENCE_CHANGE
    }

    public QuickAccessWalletController(Context context, Executor executor, Executor executor2, SecureSettings secureSettings, QuickAccessWalletClient quickAccessWalletClient, SystemClock systemClock) {
        this.mContext = context;
        this.mExecutor = executor;
        this.mBgExecutor = executor2;
        this.mSecureSettings = secureSettings;
        this.mQuickAccessWalletClient = quickAccessWalletClient;
        this.mClock = systemClock;
        ((SystemClockImpl) systemClock).getClass();
        this.mQawClientCreatedTimeMillis = android.os.SystemClock.elapsedRealtime();
    }

    public final void queryWalletCards(QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback) {
        ((SystemClockImpl) this.mClock).getClass();
        if (android.os.SystemClock.elapsedRealtime() - this.mQawClientCreatedTimeMillis > RECREATION_TIME_WINDOW) {
            Log.i("QAWController", "Re-creating the QAW client to avoid stale.");
            reCreateWalletClient();
        }
        if (!this.mQuickAccessWalletClient.isWalletFeatureAvailable()) {
            Log.d("QAWController", "QuickAccessWallet feature is not available.");
            return;
        }
        Context context = this.mContext;
        this.mQuickAccessWalletClient.getWalletCards(this.mBgExecutor, new GetWalletCardsRequest(context.getResources().getDimensionPixelSize(R.dimen.wallet_tile_card_view_width), context.getResources().getDimensionPixelSize(R.dimen.wallet_tile_card_view_height), context.getResources().getDimensionPixelSize(R.dimen.wallet_icon_size), 1), onWalletCardsRetrievedCallback);
    }

    public final void reCreateWalletClient() {
        this.mQuickAccessWalletClient = QuickAccessWalletClient.create(this.mContext, this.mBgExecutor);
        ((SystemClockImpl) this.mClock).getClass();
        this.mQawClientCreatedTimeMillis = android.os.SystemClock.elapsedRealtime();
    }

    public final void setupWalletChangeObservers(QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback, WalletChangeEvent... walletChangeEventArr) {
        for (WalletChangeEvent walletChangeEvent : walletChangeEventArr) {
            WalletChangeEvent walletChangeEvent2 = WalletChangeEvent.WALLET_PREFERENCE_CHANGE;
            SecureSettings secureSettings = this.mSecureSettings;
            if (walletChangeEvent == walletChangeEvent2) {
                if (this.mWalletPreferenceObserver == null) {
                    C36502 c36502 = new C36502(null);
                    this.mWalletPreferenceObserver = c36502;
                    secureSettings.registerContentObserverForUser("lockscreen_show_wallet", false, (ContentObserver) c36502, -1);
                }
                this.mWalletPreferenceChangeEvents++;
            } else if (walletChangeEvent == WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE) {
                if (this.mDefaultPaymentAppObserver == null) {
                    C36491 c36491 = new C36491(null, onWalletCardsRetrievedCallback);
                    this.mDefaultPaymentAppObserver = c36491;
                    secureSettings.registerContentObserverForUser("nfc_payment_default_component", false, (ContentObserver) c36491, -1);
                }
                this.mDefaultPaymentAppChangeEvents++;
            }
        }
    }

    public final void unregisterWalletChangeObservers(WalletChangeEvent... walletChangeEventArr) {
        C36491 c36491;
        C36502 c36502;
        for (WalletChangeEvent walletChangeEvent : walletChangeEventArr) {
            WalletChangeEvent walletChangeEvent2 = WalletChangeEvent.WALLET_PREFERENCE_CHANGE;
            SecureSettings secureSettings = this.mSecureSettings;
            if (walletChangeEvent == walletChangeEvent2 && (c36502 = this.mWalletPreferenceObserver) != null) {
                int i = this.mWalletPreferenceChangeEvents - 1;
                this.mWalletPreferenceChangeEvents = i;
                if (i == 0) {
                    secureSettings.unregisterContentObserver(c36502);
                }
            } else if (walletChangeEvent == WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE && (c36491 = this.mDefaultPaymentAppObserver) != null) {
                int i2 = this.mDefaultPaymentAppChangeEvents - 1;
                this.mDefaultPaymentAppChangeEvents = i2;
                if (i2 == 0) {
                    secureSettings.unregisterContentObserver(c36491);
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
