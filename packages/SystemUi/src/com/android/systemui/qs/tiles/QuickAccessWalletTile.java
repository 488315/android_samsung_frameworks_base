package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.service.quickaccesswallet.GetWalletCardsError;
import android.service.quickaccesswallet.GetWalletCardsResponse;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.service.quickaccesswallet.WalletCard;
import android.util.Log;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import com.android.systemui.wallet.controller.QuickAccessWalletController$$ExternalSyntheticLambda0;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QuickAccessWalletTile extends SQSTileImpl {
    public final WalletCardRetriever mCardRetriever;
    Drawable mCardViewDrawable;
    public final QuickAccessWalletController mController;
    public boolean mIsWalletUpdating;
    public final KeyguardStateController mKeyguardStateController;
    public final CharSequence mLabel;
    public final PackageManager mPackageManager;
    public final SecureSettings mSecureSettings;
    public WalletCard mSelectedCard;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WalletCardRetriever implements QuickAccessWalletClient.OnWalletCardsRetrievedCallback {
        public /* synthetic */ WalletCardRetriever(QuickAccessWalletTile quickAccessWalletTile, int i) {
            this();
        }

        public final void onWalletCardRetrievalError(GetWalletCardsError getWalletCardsError) {
            QuickAccessWalletTile quickAccessWalletTile = QuickAccessWalletTile.this;
            quickAccessWalletTile.mIsWalletUpdating = false;
            quickAccessWalletTile.mCardViewDrawable = null;
            quickAccessWalletTile.mSelectedCard = null;
            quickAccessWalletTile.refreshState(null);
        }

        public final void onWalletCardsRetrieved(GetWalletCardsResponse getWalletCardsResponse) {
            Log.i("QuickAccessWalletTile", "Successfully retrieved wallet cards.");
            QuickAccessWalletTile.this.mIsWalletUpdating = false;
            List<WalletCard> walletCards = getWalletCardsResponse.getWalletCards();
            if (walletCards.isEmpty()) {
                Log.d("QuickAccessWalletTile", "No wallet cards exist.");
                QuickAccessWalletTile quickAccessWalletTile = QuickAccessWalletTile.this;
                quickAccessWalletTile.mCardViewDrawable = null;
                quickAccessWalletTile.mSelectedCard = null;
                quickAccessWalletTile.refreshState(null);
                return;
            }
            int selectedIndex = getWalletCardsResponse.getSelectedIndex();
            if (selectedIndex >= walletCards.size()) {
                Log.w("QuickAccessWalletTile", "Error retrieving cards: Invalid selected card index.");
                QuickAccessWalletTile quickAccessWalletTile2 = QuickAccessWalletTile.this;
                quickAccessWalletTile2.mSelectedCard = null;
                quickAccessWalletTile2.mCardViewDrawable = null;
                return;
            }
            QuickAccessWalletTile.this.mSelectedCard = walletCards.get(selectedIndex);
            if (QuickAccessWalletTile.this.mSelectedCard.getCardImage().getType() == 4) {
                QuickAccessWalletTile.this.mCardViewDrawable = null;
            } else {
                QuickAccessWalletTile quickAccessWalletTile3 = QuickAccessWalletTile.this;
                quickAccessWalletTile3.mCardViewDrawable = quickAccessWalletTile3.mSelectedCard.getCardImage().loadDrawable(QuickAccessWalletTile.this.mContext);
            }
            QuickAccessWalletTile.this.refreshState(null);
        }

        private WalletCardRetriever() {
        }
    }

    public QuickAccessWalletTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardStateController keyguardStateController, PackageManager packageManager, SecureSettings secureSettings, QuickAccessWalletController quickAccessWalletController) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mLabel = this.mContext.getString(R.string.wallet_title);
        this.mCardRetriever = new WalletCardRetriever(this, 0);
        this.mIsWalletUpdating = true;
        this.mController = quickAccessWalletController;
        this.mKeyguardStateController = keyguardStateController;
        this.mPackageManager = packageManager;
        this.mSecureSettings = secureSettings;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        CharSequence serviceLabel = this.mController.mQuickAccessWalletClient.getServiceLabel();
        return serviceLabel == null ? this.mLabel : serviceLabel;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        final GhostedViewLaunchAnimatorController fromView = view == null ? null : ActivityLaunchAnimator.Controller.fromView(view, 32);
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.QuickAccessWalletTile$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                QuickAccessWalletTile quickAccessWalletTile = QuickAccessWalletTile.this;
                ActivityLaunchAnimator.Controller controller = fromView;
                boolean z = quickAccessWalletTile.mSelectedCard != null;
                QuickAccessWalletController quickAccessWalletController = quickAccessWalletTile.mController;
                quickAccessWalletController.mQuickAccessWalletClient.getWalletPendingIntent(quickAccessWalletController.mExecutor, new QuickAccessWalletController$$ExternalSyntheticLambda0(quickAccessWalletController, quickAccessWalletTile.mActivityStarter, controller, z));
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        this.mController.unregisterWalletChangeObservers(QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (z) {
            QuickAccessWalletController.WalletChangeEvent[] walletChangeEventArr = {QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE};
            QuickAccessWalletController quickAccessWalletController = this.mController;
            WalletCardRetriever walletCardRetriever = this.mCardRetriever;
            quickAccessWalletController.setupWalletChangeObservers(walletCardRetriever, walletChangeEventArr);
            if (!quickAccessWalletController.mQuickAccessWalletClient.isWalletServiceAvailable() || !quickAccessWalletController.mQuickAccessWalletClient.isWalletFeatureAvailable()) {
                Log.i("QuickAccessWalletTile", "QAW service is unavailable, recreating the wallet client.");
                quickAccessWalletController.reCreateWalletClient();
            }
            quickAccessWalletController.queryWalletCards(walletCardRetriever);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        QuickAccessWalletController quickAccessWalletController = this.mController;
        CharSequence serviceLabel = quickAccessWalletController.mQuickAccessWalletClient.getServiceLabel();
        if (serviceLabel == null) {
            serviceLabel = this.mLabel;
        }
        booleanState.label = serviceLabel;
        booleanState.contentDescription = serviceLabel;
        Drawable tileIcon = quickAccessWalletController.mQuickAccessWalletClient.getTileIcon();
        booleanState.icon = tileIcon == null ? QSTileImpl.ResourceIcon.get(R.drawable.ic_wallet_lockscreen) : new QSTileImpl.DrawableIcon(tileIcon);
        boolean z = !this.mKeyguardStateController.isUnlocked();
        if (!quickAccessWalletController.mQuickAccessWalletClient.isWalletServiceAvailable() || !quickAccessWalletController.mQuickAccessWalletClient.isWalletFeatureAvailable()) {
            booleanState.state = 0;
            booleanState.secondaryLabel = null;
            booleanState.sideViewCustomDrawable = null;
            return;
        }
        WalletCard walletCard = this.mSelectedCard;
        if (walletCard != null) {
            booleanState.state = z ? 1 : 2;
            booleanState.secondaryLabel = walletCard.getContentDescription();
            booleanState.sideViewCustomDrawable = this.mCardViewDrawable;
        } else {
            booleanState.state = 1;
            booleanState.secondaryLabel = this.mContext.getString(this.mIsWalletUpdating ? R.string.wallet_secondary_label_updating : R.string.wallet_secondary_label_no_card);
            booleanState.sideViewCustomDrawable = null;
        }
        booleanState.stateDescription = booleanState.secondaryLabel;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final boolean isAvailable() {
        PackageManager packageManager = this.mPackageManager;
        return (!packageManager.hasSystemFeature("android.hardware.nfc.hce") || packageManager.hasSystemFeature("org.chromium.arc") || ((SecureSettingsImpl) this.mSecureSettings).getStringForUser(-2, "nfc_payment_default_component") == null) ? false : true;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.handlesLongClick = false;
        return booleanState;
    }
}
