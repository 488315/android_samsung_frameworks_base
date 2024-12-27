package com.android.systemui.wallet.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.service.quickaccesswallet.GetWalletCardsError;
import android.service.quickaccesswallet.GetWalletCardsRequest;
import android.service.quickaccesswallet.GetWalletCardsResponse;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.service.quickaccesswallet.SelectWalletCardRequest;
import android.service.quickaccesswallet.WalletCard;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.wallet.ui.WalletCardCarousel;
import com.android.systemui.wallet.ui.WalletScreenController;
import com.android.systemui.wallet.util.WalletCardUtilsKt;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class WalletScreenController implements WalletCardCarousel.OnSelectionListener, QuickAccessWalletClient.OnWalletCardsRetrievedCallback, KeyguardStateController.Callback {
    public static final long SELECTION_DELAY_MILLIS = TimeUnit.SECONDS.toMillis(30);
    public final ActivityStarter mActivityStarter;
    public final WalletCardCarousel mCardCarousel;
    public Context mContext;
    public final Executor mExecutor;
    public final FalsingManager mFalsingManager;
    public final Handler mHandler;
    boolean mIsDismissed;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final SharedPreferences mPrefs;
    String mSelectedCardId;
    public final WalletScreenController$$ExternalSyntheticLambda0 mSelectionRunnable = new Runnable() { // from class: com.android.systemui.wallet.ui.WalletScreenController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            WalletScreenController.this.selectCard();
        }
    };
    public final UiEventLogger mUiEventLogger;
    public final QuickAccessWalletClient mWalletClient;
    public final WalletView mWalletView;

    class QAWalletCardViewInfo implements WalletCardViewInfo {
        public final Drawable mCardDrawable;
        public final Drawable mIconDrawable;
        public final WalletCard mWalletCard;

        public QAWalletCardViewInfo(Context context, WalletCard walletCard) {
            this.mWalletCard = walletCard;
            if (walletCard.getCardImage().getType() == 4) {
                this.mCardDrawable = null;
            } else {
                this.mCardDrawable = walletCard.getCardImage().loadDrawable(context);
            }
            Icon cardIcon = walletCard.getCardIcon();
            this.mIconDrawable = cardIcon != null ? cardIcon.loadDrawable(context) : null;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.wallet.ui.WalletScreenController$$ExternalSyntheticLambda0] */
    public WalletScreenController(Context context, WalletView walletView, QuickAccessWalletClient quickAccessWalletClient, ActivityStarter activityStarter, Executor executor, Handler handler, UserTracker userTracker, FalsingManager falsingManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, UiEventLogger uiEventLogger) {
        this.mContext = context;
        this.mWalletClient = quickAccessWalletClient;
        this.mActivityStarter = activityStarter;
        this.mExecutor = executor;
        this.mHandler = handler;
        this.mFalsingManager = falsingManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mUiEventLogger = uiEventLogger;
        SharedPreferences sharedPreferences = ((UserTrackerImpl) userTracker).getUserContext().getSharedPreferences("WalletScreenCtrl", 0);
        this.mPrefs = sharedPreferences;
        this.mWalletView = walletView;
        int i = sharedPreferences.getInt("wallet_view_height", -1);
        walletView.setMinimumHeight(i == -1 ? this.mContext.getResources().getDimensionPixelSize(R.dimen.min_wallet_empty_height) : i);
        walletView.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        WalletCardCarousel walletCardCarousel = walletView.mCardCarousel;
        this.mCardCarousel = walletCardCarousel;
        if (walletCardCarousel != null) {
            walletCardCarousel.mSelectionListener = this;
        }
    }

    public final void onCardSelected(WalletCardViewInfo walletCardViewInfo) {
        if (this.mIsDismissed) {
            return;
        }
        String str = this.mSelectedCardId;
        if (str != null && !str.equals(((QAWalletCardViewInfo) walletCardViewInfo).mWalletCard.getCardId())) {
            this.mUiEventLogger.log(WalletUiEvent.QAW_CHANGE_CARD);
        }
        this.mSelectedCardId = ((QAWalletCardViewInfo) walletCardViewInfo).mWalletCard.getCardId();
        selectCard();
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardFadingAwayChanged() {
        queryWalletCards();
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onUnlockedChanged() {
        queryWalletCards();
    }

    public final void onWalletCardRetrievalError(final GetWalletCardsError getWalletCardsError) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.wallet.ui.WalletScreenController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                WalletScreenController walletScreenController = WalletScreenController.this;
                GetWalletCardsError getWalletCardsError2 = getWalletCardsError;
                if (walletScreenController.mIsDismissed) {
                    return;
                }
                WalletView walletView = walletScreenController.mWalletView;
                CharSequence message = getWalletCardsError2.getMessage();
                walletView.getClass();
                if (TextUtils.isEmpty(message)) {
                    message = walletView.getResources().getText(R.string.wallet_error_generic);
                }
                walletView.mErrorView.setText(message);
                walletView.mErrorView.setVisibility(0);
                walletView.mCardCarouselContainer.setVisibility(8);
                walletView.mEmptyStateView.setVisibility(8);
            }
        });
    }

    public final void onWalletCardsRetrieved(final GetWalletCardsResponse getWalletCardsResponse) {
        if (this.mIsDismissed) {
            return;
        }
        Log.i("WalletScreenCtrl", "Successfully retrieved wallet cards.");
        final List list = (List) WalletCardUtilsKt.getPaymentCards(getWalletCardsResponse.getWalletCards()).stream().map(new Function() { // from class: com.android.systemui.wallet.ui.WalletScreenController$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new WalletScreenController.QAWalletCardViewInfo(WalletScreenController.this.mContext, (WalletCard) obj);
            }
        }).collect(Collectors.toList());
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.wallet.ui.WalletScreenController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                final WalletScreenController walletScreenController = WalletScreenController.this;
                List list2 = list;
                GetWalletCardsResponse getWalletCardsResponse2 = getWalletCardsResponse;
                if (walletScreenController.mIsDismissed) {
                    return;
                }
                if (list2.isEmpty()) {
                    walletScreenController.showEmptyStateView();
                } else {
                    int selectedIndex = getWalletCardsResponse2.getSelectedIndex();
                    if (selectedIndex >= list2.size()) {
                        Log.w("WalletScreenCtrl", "Invalid selected card index, showing empty state.");
                        walletScreenController.showEmptyStateView();
                    } else {
                        walletScreenController.mWalletView.showCardCarousel(list2, selectedIndex, true ^ walletScreenController.mKeyguardStateController.isUnlocked(), walletScreenController.mKeyguardUpdateMonitor.isUdfpsEnrolled() && walletScreenController.mKeyguardUpdateMonitor.isFingerprintDetectionRunning());
                    }
                }
                walletScreenController.mUiEventLogger.log(WalletUiEvent.QAW_IMPRESSION);
                walletScreenController.mWalletView.setMinimumHeight(0);
                walletScreenController.mWalletView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.wallet.ui.WalletScreenController.1
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        WalletScreenController.this.mWalletView.removeOnLayoutChangeListener(this);
                        WalletScreenController.this.mPrefs.edit().putInt("wallet_view_height", i4 - i2).apply();
                    }
                });
            }
        });
    }

    public final void queryWalletCards() {
        if (this.mIsDismissed) {
            return;
        }
        WalletCardCarousel walletCardCarousel = this.mCardCarousel;
        int i = walletCardCarousel.mCardWidthPx;
        int i2 = walletCardCarousel.mCardHeightPx;
        if (i == 0 || i2 == 0) {
            return;
        }
        this.mWalletView.setVisibility(0);
        this.mWalletView.mErrorView.setVisibility(8);
        this.mWalletClient.getWalletCards(this.mExecutor, new GetWalletCardsRequest(i, i2, this.mContext.getResources().getDimensionPixelSize(R.dimen.wallet_screen_header_icon_size), 10), this);
    }

    public final void selectCard() {
        this.mHandler.removeCallbacks(this.mSelectionRunnable);
        String str = this.mSelectedCardId;
        if (this.mIsDismissed || str == null) {
            return;
        }
        this.mWalletClient.selectWalletCard(new SelectWalletCardRequest(str));
        this.mHandler.postDelayed(this.mSelectionRunnable, SELECTION_DELAY_MILLIS);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.systemui.wallet.ui.WalletScreenController$$ExternalSyntheticLambda4] */
    public final void showEmptyStateView() {
        Drawable logo = this.mWalletClient.getLogo();
        CharSequence serviceLabel = this.mWalletClient.getServiceLabel();
        CharSequence shortcutLongLabel = this.mWalletClient.getShortcutLongLabel();
        final Intent createWalletIntent = this.mWalletClient.createWalletIntent();
        if (logo != null && !TextUtils.isEmpty(serviceLabel) && !TextUtils.isEmpty(shortcutLongLabel) && createWalletIntent != null) {
            this.mWalletView.showEmptyStateView(logo, serviceLabel, shortcutLongLabel, new View.OnClickListener() { // from class: com.android.systemui.wallet.ui.WalletScreenController$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    WalletScreenController walletScreenController = WalletScreenController.this;
                    walletScreenController.mActivityStarter.startActivity(createWalletIntent, true);
                }
            });
            return;
        }
        Log.w("WalletScreenCtrl", "QuickAccessWalletService manifest entry mis-configured");
        this.mWalletView.setVisibility(8);
        this.mPrefs.edit().putInt("wallet_view_height", 0).apply();
    }
}
