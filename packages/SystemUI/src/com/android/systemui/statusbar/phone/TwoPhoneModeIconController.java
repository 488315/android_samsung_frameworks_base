package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel;
import java.io.PrintWriter;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TwoPhoneModeIconController implements Dumpable {
    public final CarrierInfraMediator carrierInfraMediator;
    public final ConfigurationController configurationController;
    public final Context context;
    public final DarkIconDispatcher darkIconDispatcher;
    public final DelayableExecutor executor;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public boolean isBModeCreated;
    public boolean isBModeUser;
    public boolean isOwner;
    public TwoPhoneModeIconView modeIconView;
    private final SettingsHelper settingsHelper;
    public final SlimIndicatorViewMediator slimIndicatorViewMediator;
    public final UserManager userManager;
    public final UserTracker userTracker;
    public TwoPhoneModeState state = new TwoPhoneModeState(false, false, false, false);
    public int currentUserId = ActivityManager.getCurrentUser();
    public final TwoPhoneModeIconController$userTrackerCallback$1 userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$userTrackerCallback$1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            final TwoPhoneModeIconController twoPhoneModeIconController = TwoPhoneModeIconController.this;
            twoPhoneModeIconController.currentUserId = i;
            Log.d("TwoPhoneModeIconController", "User switched to " + i);
            twoPhoneModeIconController.updateTwoPhoneMode();
            if (twoPhoneModeIconController.isBModeCreated) {
                if (twoPhoneModeIconController.isOwner || twoPhoneModeIconController.isBModeUser) {
                    twoPhoneModeIconController.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$showSwitchDoneToast$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TwoPhoneModeIconController twoPhoneModeIconController2 = TwoPhoneModeIconController.this;
                            String string = twoPhoneModeIconController2.context.getString(twoPhoneModeIconController2.isBModeUser ? R.string.switched_to_twophone_mode : R.string.switched_to_onephone_mode);
                            Toast.makeText(TwoPhoneModeIconController.this.context, string, 1000).show();
                            Log.d("TwoPhoneModeIconController", "Two phone mode switched toast ".concat(string));
                        }
                    }, 5000L);
                }
            }
        }
    };
    private final SettingsHelper.OnChangedCallback settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$settingsListener$1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            TwoPhoneModeIconController.this.updateTwoPhoneMode();
        }
    };
    public final TwoPhoneModeIconController$quickStarListener$1 quickStarListener = new SlimIndicatorViewSubscriber() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$quickStarListener$1
        @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
        public final void updateQuickStarStyle() {
            TwoPhoneModeIconController.this.updateTwoPhoneMode();
        }
    };
    public final TwoPhoneModeIconController$configurationListener$1 configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$configurationListener$1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            TwoPhoneModeIconController twoPhoneModeIconController = TwoPhoneModeIconController.this;
            float f = twoPhoneModeIconController.indicatorScaleGardener.getLatestScaleModel(twoPhoneModeIconController.context).ratio;
            TwoPhoneModeIconView twoPhoneModeIconView = twoPhoneModeIconController.modeIconView;
            if (twoPhoneModeIconView == null) {
                twoPhoneModeIconView = null;
            }
            twoPhoneModeIconView.setScaleX(f);
            TwoPhoneModeIconView twoPhoneModeIconView2 = twoPhoneModeIconController.modeIconView;
            if (twoPhoneModeIconView2 == null) {
                twoPhoneModeIconView2 = null;
            }
            twoPhoneModeIconView2.setScaleY(f);
            int dimensionPixelSize = twoPhoneModeIconController.context.getResources().getDimensionPixelSize(R.dimen.two_phone_mode_icon_padding_start);
            TwoPhoneModeIconView twoPhoneModeIconView3 = twoPhoneModeIconController.modeIconView;
            (twoPhoneModeIconView3 != null ? twoPhoneModeIconView3 : null).setPaddingRelative(MathKt__MathJVMKt.roundToInt(dimensionPixelSize * f), 0, 0, 0);
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TwoPhoneModeState {
        public final boolean callEnabled;
        public final boolean msgEnabled;
        public final boolean registered;
        public final boolean userCreated;

        public TwoPhoneModeState(boolean z, boolean z2, boolean z3, boolean z4) {
            this.userCreated = z;
            this.registered = z2;
            this.callEnabled = z3;
            this.msgEnabled = z4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TwoPhoneModeState)) {
                return false;
            }
            TwoPhoneModeState twoPhoneModeState = (TwoPhoneModeState) obj;
            return this.userCreated == twoPhoneModeState.userCreated && this.registered == twoPhoneModeState.registered && this.callEnabled == twoPhoneModeState.callEnabled && this.msgEnabled == twoPhoneModeState.msgEnabled;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.msgEnabled) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.userCreated) * 31, 31, this.registered), 31, this.callEnabled);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("TwoPhoneModeState(userCreated=");
            sb.append(this.userCreated);
            sb.append(", registered=");
            sb.append(this.registered);
            sb.append(", callEnabled=");
            sb.append(this.callEnabled);
            sb.append(", msgEnabled=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.msgEnabled, ")");
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.TwoPhoneModeIconController$userTrackerCallback$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.statusbar.phone.TwoPhoneModeIconController$quickStarListener$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.statusbar.phone.TwoPhoneModeIconController$configurationListener$1] */
    public TwoPhoneModeIconController(Context context, CarrierInfraMediator carrierInfraMediator, UserManager userManager, SettingsHelper settingsHelper, DarkIconDispatcher darkIconDispatcher, SlimIndicatorViewMediator slimIndicatorViewMediator, DumpManager dumpManager, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener, DelayableExecutor delayableExecutor, UserTracker userTracker) {
        this.context = context;
        this.carrierInfraMediator = carrierInfraMediator;
        this.userManager = userManager;
        this.settingsHelper = settingsHelper;
        this.darkIconDispatcher = darkIconDispatcher;
        this.slimIndicatorViewMediator = slimIndicatorViewMediator;
        this.configurationController = configurationController;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.executor = delayableExecutor;
        this.userTracker = userTracker;
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("featureEnabled=", featureEnabled(), printWriter);
        printWriter.println("state=" + this.state);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("two phone mode created=", this.isBModeCreated, printWriter);
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("current user(", this.currentUserId, ") is BMode=", this.isBModeUser, " or Owner="), this.isOwner, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("blocked by quick star=", ((SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator).isBlocked(SPluginSlimIndicatorModel.DB_KEY_TWO_PHONE_MODE_ICON), printWriter);
    }

    public final boolean featureEnabled() {
        return this.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SHOW_TWO_PHONE_MODE_ICON, 0, new Object[0]);
    }

    public final int getViewWidth() {
        TwoPhoneModeIconView twoPhoneModeIconView = this.modeIconView;
        if (twoPhoneModeIconView == null) {
            return 0;
        }
        if (twoPhoneModeIconView == null) {
            twoPhoneModeIconView = null;
        }
        return twoPhoneModeIconView.getMeasuredWidth();
    }

    public final void onViewAttached(ViewGroup viewGroup) {
        TwoPhoneModeIconView twoPhoneModeIconView = new TwoPhoneModeIconView(this.context);
        this.modeIconView = twoPhoneModeIconView;
        twoPhoneModeIconView.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
        TwoPhoneModeIconView twoPhoneModeIconView2 = this.modeIconView;
        if (twoPhoneModeIconView2 == null) {
            twoPhoneModeIconView2 = null;
        }
        twoPhoneModeIconView2.setVisibility(8);
        TwoPhoneModeIconView twoPhoneModeIconView3 = this.modeIconView;
        if (twoPhoneModeIconView3 == null) {
            twoPhoneModeIconView3 = null;
        }
        viewGroup.addView(twoPhoneModeIconView3);
        TwoPhoneModeIconView twoPhoneModeIconView4 = this.modeIconView;
        this.darkIconDispatcher.addDarkReceiver(twoPhoneModeIconView4 != null ? twoPhoneModeIconView4 : null);
        this.settingsHelper.registerCallback(this.settingsListener, Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_REGISTER), Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_ACCOUNT), Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_CALL_ENABLED), Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_SMS_ENABLED));
        ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, this.executor);
        ((SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator).registerSubscriber("TwoPhoneModeIconController", this.quickStarListener);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationListener);
        updateTwoPhoneMode();
    }

    public final void onViewDetached() {
        TwoPhoneModeIconView twoPhoneModeIconView = this.modeIconView;
        if (twoPhoneModeIconView == null) {
            twoPhoneModeIconView = null;
        }
        this.darkIconDispatcher.removeDarkReceiver(twoPhoneModeIconView);
        this.settingsHelper.unregisterCallback(this.settingsListener);
        ((SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator).unregisterSubscriber("TwoPhoneModeIconController");
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationListener);
        ((UserTrackerImpl) this.userTracker).removeCallback(this.userTrackerCallback);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0132  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateTwoPhoneMode() {
        /*
            Method dump skipped, instructions count: 352
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.TwoPhoneModeIconController.updateTwoPhoneMode():void");
    }
}
