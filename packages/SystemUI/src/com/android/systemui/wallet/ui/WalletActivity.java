package com.android.systemui.wallet.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Bundle;
import android.os.Handler;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.service.quickaccesswallet.WalletServiceEvent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;
import androidx.activity.ComponentActivity;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class WalletActivity extends ComponentActivity implements QuickAccessWalletClient.WalletServiceEventListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter mActivityStarter;
    public final DeviceEntryFaceAuthInteractor mDeviceEntryFaceAuthInteractor;
    public final Executor mExecutor;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final Handler mHandler;
    public boolean mHasRegisteredListener;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final StatusBarKeyguardViewManager mKeyguardViewManager;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker mUserTracker;
    public QuickAccessWalletClient mWalletClient;
    public WalletScreenController mWalletScreenController;

    public WalletActivity(KeyguardStateController keyguardStateController, KeyguardDismissUtil keyguardDismissUtil, ActivityStarter activityStarter, Executor executor, Handler handler, FalsingManager falsingManager, FalsingCollector falsingCollector, UserTracker userTracker, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarKeyguardViewManager statusBarKeyguardViewManager, UiEventLogger uiEventLogger, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor) {
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardDismissUtil = keyguardDismissUtil;
        this.mActivityStarter = activityStarter;
        this.mExecutor = executor;
        this.mHandler = handler;
        this.mFalsingManager = falsingManager;
        this.mFalsingCollector = falsingCollector;
        this.mUserTracker = userTracker;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardViewManager = statusBarKeyguardViewManager;
        this.mUiEventLogger = uiEventLogger;
        this.mDeviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(Integer.MIN_VALUE);
        requestWindowFeature(1);
        setContentView(R.layout.quick_access_wallet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        if (toolbar != null) {
            setActionBar(toolbar);
        }
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getActionBar();
        Drawable drawable = getDrawable(R.drawable.ic_close);
        drawable.setTint(getColor(R.color.material_dynamic_neutral70));
        actionBar.setHomeAsUpIndicator(drawable);
        getActionBar().setHomeActionContentDescription(R.string.accessibility_desc_close);
        WalletView walletView = (WalletView) requireViewById(R.id.wallet_view);
        this.mWalletClient = QuickAccessWalletClient.create(this, this.mExecutor);
        this.mWalletScreenController = new WalletScreenController(this, walletView, this.mWalletClient, this.mActivityStarter, this.mExecutor, this.mHandler, this.mUserTracker, this.mFalsingManager, this.mKeyguardUpdateMonitor, this.mKeyguardStateController, this.mUiEventLogger);
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.wallet.ui.WalletActivity.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                Log.d("WalletActivity", "Biometric running state has changed.");
                WalletActivity.this.mWalletScreenController.queryWalletCards();
            }
        };
        walletView.mFalsingCollector = this.mFalsingCollector;
        final int i = 0;
        walletView.mShowWalletAppOnClickListener = new View.OnClickListener(this) { // from class: com.android.systemui.wallet.ui.WalletActivity$$ExternalSyntheticLambda0
            public final /* synthetic */ WalletActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                final WalletActivity walletActivity = this.f$0;
                switch (i2) {
                    case 0:
                        if (walletActivity.mWalletClient.createWalletIntent() != null) {
                            if (!walletActivity.mFalsingManager.isFalseTap(1)) {
                                if (!walletActivity.mKeyguardStateController.isUnlocked()) {
                                    walletActivity.mUiEventLogger.log(WalletUiEvent.QAW_UNLOCK_FROM_SHOW_ALL_BUTTON);
                                    walletActivity.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.wallet.ui.WalletActivity$$ExternalSyntheticLambda2
                                        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                                        public final boolean onDismiss() {
                                            WalletActivity walletActivity2 = WalletActivity.this;
                                            walletActivity2.mUiEventLogger.log(WalletUiEvent.QAW_SHOW_ALL);
                                            walletActivity2.mActivityStarter.startActivity(walletActivity2.mWalletClient.createWalletIntent(), true);
                                            walletActivity2.finish();
                                            return false;
                                        }
                                    }, false, true);
                                    break;
                                } else {
                                    walletActivity.mUiEventLogger.log(WalletUiEvent.QAW_SHOW_ALL);
                                    walletActivity.mActivityStarter.startActivity(walletActivity.mWalletClient.createWalletIntent(), true);
                                    walletActivity.finish();
                                    break;
                                }
                            }
                        } else {
                            Log.w("WalletActivity", "Unable to create wallet app intent.");
                            break;
                        }
                        break;
                    default:
                        int i3 = WalletActivity.$r8$clinit;
                        walletActivity.getClass();
                        Log.d("WalletActivity", "Wallet action button is clicked.");
                        if (!walletActivity.mFalsingManager.isFalseTap(1)) {
                            walletActivity.mUiEventLogger.log(WalletUiEvent.QAW_UNLOCK_FROM_UNLOCK_BUTTON);
                            walletActivity.mKeyguardDismissUtil.executeWhenUnlocked(new WalletActivity$$ExternalSyntheticLambda3(), false, false);
                            break;
                        } else {
                            Log.d("WalletActivity", "False tap detected on wallet action button.");
                            break;
                        }
                }
            }
        };
        final int i2 = 1;
        walletView.mDeviceLockedActionOnClickListener = new View.OnClickListener(this) { // from class: com.android.systemui.wallet.ui.WalletActivity$$ExternalSyntheticLambda0
            public final /* synthetic */ WalletActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                final WalletActivity walletActivity = this.f$0;
                switch (i22) {
                    case 0:
                        if (walletActivity.mWalletClient.createWalletIntent() != null) {
                            if (!walletActivity.mFalsingManager.isFalseTap(1)) {
                                if (!walletActivity.mKeyguardStateController.isUnlocked()) {
                                    walletActivity.mUiEventLogger.log(WalletUiEvent.QAW_UNLOCK_FROM_SHOW_ALL_BUTTON);
                                    walletActivity.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.wallet.ui.WalletActivity$$ExternalSyntheticLambda2
                                        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                                        public final boolean onDismiss() {
                                            WalletActivity walletActivity2 = WalletActivity.this;
                                            walletActivity2.mUiEventLogger.log(WalletUiEvent.QAW_SHOW_ALL);
                                            walletActivity2.mActivityStarter.startActivity(walletActivity2.mWalletClient.createWalletIntent(), true);
                                            walletActivity2.finish();
                                            return false;
                                        }
                                    }, false, true);
                                    break;
                                } else {
                                    walletActivity.mUiEventLogger.log(WalletUiEvent.QAW_SHOW_ALL);
                                    walletActivity.mActivityStarter.startActivity(walletActivity.mWalletClient.createWalletIntent(), true);
                                    walletActivity.finish();
                                    break;
                                }
                            }
                        } else {
                            Log.w("WalletActivity", "Unable to create wallet app intent.");
                            break;
                        }
                        break;
                    default:
                        int i3 = WalletActivity.$r8$clinit;
                        walletActivity.getClass();
                        Log.d("WalletActivity", "Wallet action button is clicked.");
                        if (!walletActivity.mFalsingManager.isFalseTap(1)) {
                            walletActivity.mUiEventLogger.log(WalletUiEvent.QAW_UNLOCK_FROM_UNLOCK_BUTTON);
                            walletActivity.mKeyguardDismissUtil.executeWhenUnlocked(new WalletActivity$$ExternalSyntheticLambda3(), false, false);
                            break;
                        } else {
                            Log.d("WalletActivity", "False tap detected on wallet action button.");
                            break;
                        }
                }
            }
        };
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wallet_activity_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).removeCallback(this.mWalletScreenController);
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mKeyguardUpdateMonitorCallback;
        if (keyguardUpdateMonitorCallback != null) {
            this.mKeyguardUpdateMonitor.removeCallback(keyguardUpdateMonitorCallback);
        }
        WalletScreenController walletScreenController = this.mWalletScreenController;
        if (!walletScreenController.mIsDismissed) {
            walletScreenController.mIsDismissed = true;
            walletScreenController.mSelectedCardId = null;
            walletScreenController.mHandler.removeCallbacks(walletScreenController.mSelectionRunnable);
            walletScreenController.mWalletClient.notifyWalletDismissed();
            WalletView walletView = walletScreenController.mWalletView;
            if (walletView.mCardCarouselContainer.getVisibility() == 0) {
                walletView.mCardCarousel.animate().translationX(walletView.mAnimationTranslationX).setInterpolator(walletView.mOutInterpolator).setDuration(200L).start();
                walletView.mCardCarouselContainer.animate().alpha(0.0f).setDuration(100L).setStartDelay(50L).start();
            }
            walletScreenController.mContext = null;
        }
        this.mWalletClient.removeWalletServiceEventListener(this);
        this.mHasRegisteredListener = false;
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            finish();
            return true;
        }
        if (itemId != R.id.wallet_lockscreen_settings) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.mActivityStarter.startActivity(new Intent("android.settings.LOCK_SCREEN_SETTINGS").addFlags(335544320), true);
        return true;
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        this.mKeyguardViewManager.requestFp(false);
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mWalletScreenController.queryWalletCards();
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mKeyguardViewManager;
        Utils.getColorAttrDefaultColor(this, android.R.^attr-private.colorAccentPrimary, 0);
        statusBarKeyguardViewManager.requestFp(true);
        this.mDeviceEntryFaceAuthInteractor.onWalletLaunched();
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        if (!this.mHasRegisteredListener) {
            this.mWalletClient.addWalletServiceEventListener(this);
            this.mHasRegisteredListener = true;
        }
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mWalletScreenController);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        finish();
    }

    public final void onWalletServiceEvent(WalletServiceEvent walletServiceEvent) {
        int eventType = walletServiceEvent.getEventType();
        if (eventType != 1) {
            if (eventType != 2) {
                Log.w("WalletActivity", "onWalletServiceEvent: Unknown event type");
            } else {
                this.mWalletScreenController.queryWalletCards();
            }
        }
    }
}
