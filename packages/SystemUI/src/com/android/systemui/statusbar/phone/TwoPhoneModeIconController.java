package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
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
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes3.dex */
public final class TwoPhoneModeIconController implements Dumpable {
    public final boolean TEST_DEBUG;
    public final CarrierInfraMediator carrierInfraMediator;
    public final ConfigurationController configurationController;
    public final TwoPhoneModeIconController$configurationListener$1 configurationListener;
    public final Context context;
    public int currentUserId;
    public final DarkIconDispatcher darkIconDispatcher;
    public final DelayableExecutor executor;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public boolean isBModeCreated;
    public boolean isBModeUser;
    public boolean isOwner;
    public TwoPhoneModeIconView modeIconView;
    public final TwoPhoneModeIconController$quickStarListener$1 quickStarListener;
    private final SettingsHelper settingsHelper;
    private final SettingsHelper.OnChangedCallback settingsListener;
    public final SlimIndicatorViewMediator slimIndicatorViewMediator;
    public TwoPhoneModeState state;
    public final UserManager userManager;
    public final UserTracker userTracker;
    public final TwoPhoneModeIconController$userTrackerCallback$1 userTrackerCallback;

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
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.msgEnabled, ")");
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
        boolean zIsTestModeIndicatorGarden = DeviceState.isTestModeIndicatorGarden();
        this.TEST_DEBUG = zIsTestModeIndicatorGarden;
        this.state = new TwoPhoneModeState(zIsTestModeIndicatorGarden, zIsTestModeIndicatorGarden, zIsTestModeIndicatorGarden, zIsTestModeIndicatorGarden);
        this.isBModeCreated = zIsTestModeIndicatorGarden;
        this.isBModeUser = zIsTestModeIndicatorGarden;
        this.isOwner = zIsTestModeIndicatorGarden;
        this.currentUserId = ActivityManager.getCurrentUser();
        this.userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                final TwoPhoneModeIconController twoPhoneModeIconController = this.this$0;
                twoPhoneModeIconController.currentUserId = i;
                Log.d("TwoPhoneModeIconController", "User switched to " + i);
                twoPhoneModeIconController.updateTwoPhoneMode();
                if (twoPhoneModeIconController.isBModeCreated) {
                    if (twoPhoneModeIconController.isOwner || twoPhoneModeIconController.isBModeUser) {
                        twoPhoneModeIconController.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$showSwitchDoneToast$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                TwoPhoneModeIconController twoPhoneModeIconController2 = twoPhoneModeIconController;
                                String string = twoPhoneModeIconController2.context.getString(twoPhoneModeIconController2.isBModeUser ? R.string.switched_to_twophone_mode : R.string.switched_to_onephone_mode);
                                Toast.makeText(twoPhoneModeIconController.context, string, 1000).show();
                                Log.d("TwoPhoneModeIconController", "Two phone mode switched toast " + string);
                            }
                        }, 5000L);
                    }
                }
            }
        };
        this.settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$settingsListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                this.this$0.updateTwoPhoneMode();
            }
        };
        this.quickStarListener = new SlimIndicatorViewSubscriber() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$quickStarListener$1
            @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
            public final void updateQuickStarStyle() {
                this.this$0.updateTwoPhoneMode();
            }
        };
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() throws Resources.NotFoundException {
                TwoPhoneModeIconController twoPhoneModeIconController = this.this$0;
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

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDisplayDeviceTypeChanged() throws Resources.NotFoundException {
                if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                    onDensityOrFontScaleChanged();
                }
            }
        };
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "featureEnabled=", featureEnabled());
        printWriter.println("state=" + this.state);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "two phone mode created=", this.isBModeCreated);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("current user(", this.currentUserId, ") is BMode=", this.isBModeUser, " or Owner="), this.isOwner, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "blocked by quick star=", ((SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator).isBlocked(SPluginSlimIndicatorModel.DB_KEY_TWO_PHONE_MODE_ICON));
    }

    public final boolean featureEnabled() {
        return this.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SHOW_TWO_PHONE_MODE_ICON, 0, new Object[0]) || this.TEST_DEBUG;
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0100  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateTwoPhoneMode() {
        int i;
        boolean z;
        int size;
        int i2;
        ArrayList arrayList = new ArrayList(this.userManager.getUsers());
        boolean z2 = this.TEST_DEBUG;
        if (!z2) {
            if (arrayList.isEmpty()) {
                z = false;
                this.isBModeCreated = z;
                ArrayList arrayList2 = new ArrayList();
                size = arrayList.size();
                i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    if (((UserInfo) obj).id == this.currentUserId) {
                        arrayList2.add(obj);
                    }
                }
                UserInfo userInfo = (UserInfo) CollectionsKt___CollectionsKt.getOrNull(0, arrayList2);
                this.isBModeUser = userInfo == null ? userInfo.isBMode() : false;
            } else {
                int size2 = arrayList.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList.get(i3);
                    i3++;
                    if (((UserInfo) obj2).isBMode()) {
                        z = true;
                        break;
                    }
                }
                z = false;
                this.isBModeCreated = z;
                ArrayList arrayList22 = new ArrayList();
                size = arrayList.size();
                i2 = 0;
                while (i2 < size) {
                }
                UserInfo userInfo2 = (UserInfo) CollectionsKt___CollectionsKt.getOrNull(0, arrayList22);
                this.isBModeUser = userInfo2 == null ? userInfo2.isBMode() : false;
            }
        }
        this.isOwner = this.currentUserId == 0;
        if (!z2) {
            this.state = new TwoPhoneModeState(this.settingsHelper.hasTwoPhoneAccount(), this.settingsHelper.isTwoPhoneRegistered(), this.settingsHelper.isTwoPhoneCallEnabled(), this.settingsHelper.isTwoPhoneSMSEnabled());
        }
        if (this.isBModeCreated || this.context.getResources().getBoolean(android.R.bool.config_enable_emergency_call_while_sim_locked)) {
            TwoPhoneModeState twoPhoneModeState = this.state;
            if (twoPhoneModeState.userCreated) {
                boolean z3 = this.isOwner;
                boolean z4 = twoPhoneModeState.registered;
                boolean z5 = twoPhoneModeState.msgEnabled;
                boolean z6 = twoPhoneModeState.callEnabled;
                if (z3) {
                    if ((z6 || z5) && z4) {
                        TwoPhoneModeIconView twoPhoneModeIconView = this.modeIconView;
                        if (twoPhoneModeIconView == null) {
                            twoPhoneModeIconView = null;
                        }
                        twoPhoneModeIconView.setContentDescription(this.context.getString(R.string.status_bar_one_phone_mode_tts));
                        i = R.drawable.stat_sys_two_phone_p_mode;
                    } else {
                        i = 0;
                    }
                } else if (this.isBModeUser) {
                    if ((z6 || z5) && z4) {
                        TwoPhoneModeIconView twoPhoneModeIconView2 = this.modeIconView;
                        if (twoPhoneModeIconView2 == null) {
                            twoPhoneModeIconView2 = null;
                        }
                        twoPhoneModeIconView2.setContentDescription(this.context.getString(R.string.status_bar_two_phone_mode_tts));
                        i = R.drawable.stat_sys_two_phone_b_mode;
                    } else {
                        TwoPhoneModeIconView twoPhoneModeIconView3 = this.modeIconView;
                        if (twoPhoneModeIconView3 == null) {
                            twoPhoneModeIconView3 = null;
                        }
                        twoPhoneModeIconView3.setContentDescription(this.context.getString(R.string.status_bar_two_phone_mode_blocked_tts));
                        i = R.drawable.stat_sys_two_phone_b_mode_blocked;
                    }
                }
            }
        }
        Log.d("TwoPhoneModeIconController", "updateTwoPhoneMode state=" + this.state + " current user(" + this.currentUserId + ") is BMode=" + this.isBModeUser + " -> icon=" + i);
        boolean z7 = i != 0;
        if (this.modeIconView == null) {
            return;
        }
        boolean z8 = z7 && !((SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator).isBlocked(SPluginSlimIndicatorModel.DB_KEY_TWO_PHONE_MODE_ICON);
        TwoPhoneModeIconView twoPhoneModeIconView4 = this.modeIconView;
        if (twoPhoneModeIconView4 == null) {
            twoPhoneModeIconView4 = null;
        }
        twoPhoneModeIconView4.setImageResource(i);
        TwoPhoneModeIconView twoPhoneModeIconView5 = this.modeIconView;
        (twoPhoneModeIconView5 != null ? twoPhoneModeIconView5 : null).setVisibility(z8 ? 0 : 8);
    }
}
