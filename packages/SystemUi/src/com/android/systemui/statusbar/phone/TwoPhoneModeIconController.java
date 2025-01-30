package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.UserManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public ImageView modeIconView;
    public final SettingsHelper settingsHelper;
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
                    twoPhoneModeIconController.executor.executeDelayed(5000L, new Runnable() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$showSwitchDoneToast$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TwoPhoneModeIconController twoPhoneModeIconController2 = TwoPhoneModeIconController.this;
                            String string = twoPhoneModeIconController2.context.getString(twoPhoneModeIconController2.isBModeUser ? R.string.switched_to_twophone_mode : R.string.switched_to_onephone_mode);
                            Toast.makeText(TwoPhoneModeIconController.this.context, string, 1000).show();
                            Log.d("TwoPhoneModeIconController", "Two phone mode switched toast " + string);
                        }
                    });
                }
            }
        }
    };
    public final TwoPhoneModeIconController$settingsListener$1 settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.TwoPhoneModeIconController$settingsListener$1
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
            ImageView imageView = twoPhoneModeIconController.modeIconView;
            if (imageView == null) {
                imageView = null;
            }
            imageView.setScaleX(f);
            ImageView imageView2 = twoPhoneModeIconController.modeIconView;
            if (imageView2 == null) {
                imageView2 = null;
            }
            imageView2.setScaleY(f);
            int dimensionPixelSize = twoPhoneModeIconController.context.getResources().getDimensionPixelSize(R.dimen.two_phone_mode_icon_padding_start);
            ImageView imageView3 = twoPhoneModeIconController.modeIconView;
            (imageView3 != null ? imageView3 : null).setPaddingRelative(MathKt__MathJVMKt.roundToInt(dimensionPixelSize * f), 0, 0, 0);
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDisplayDeviceTypeChanged() {
            if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                onDensityOrFontScaleChanged();
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            boolean z = this.userCreated;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int i2 = i * 31;
            boolean z2 = this.registered;
            int i3 = z2;
            if (z2 != 0) {
                i3 = 1;
            }
            int i4 = (i2 + i3) * 31;
            boolean z3 = this.callEnabled;
            int i5 = z3;
            if (z3 != 0) {
                i5 = 1;
            }
            int i6 = (i4 + i5) * 31;
            boolean z4 = this.msgEnabled;
            return i6 + (z4 ? 1 : z4 ? 1 : 0);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("TwoPhoneModeState(userCreated=");
            sb.append(this.userCreated);
            sb.append(", registered=");
            sb.append(this.registered);
            sb.append(", callEnabled=");
            sb.append(this.callEnabled);
            sb.append(", msgEnabled=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.msgEnabled, ")");
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.TwoPhoneModeIconController$userTrackerCallback$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.TwoPhoneModeIconController$settingsListener$1] */
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
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("featureEnabled=", featureEnabled(), printWriter);
        printWriter.println("state=" + this.state);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("two phone mode created=", this.isBModeCreated, printWriter);
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("current user(", this.currentUserId, ") is BMode=", this.isBModeUser, " or Owner="), this.isOwner, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("blocked by quick star=", ((SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator).isBlocked(SPluginSlimIndicatorModel.DB_KEY_TWO_PHONE_MODE_ICON), printWriter);
    }

    public final boolean featureEnabled() {
        return this.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SHOW_TWO_PHONE_MODE_ICON, 0, new Object[0]);
    }

    public final int getViewWidth() {
        ImageView imageView = this.modeIconView;
        if (imageView == null) {
            return 0;
        }
        if (imageView == null) {
            imageView = null;
        }
        return imageView.getMeasuredWidth();
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x014e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateTwoPhoneMode() {
        boolean z;
        int i;
        ArrayList arrayList = new ArrayList(this.userManager.getUsers());
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((UserInfo) it.next()).isBMode()) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        this.isBModeCreated = z;
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Object next = it2.next();
            if (((UserInfo) next).id == this.currentUserId) {
                arrayList2.add(next);
            }
        }
        UserInfo userInfo = (UserInfo) CollectionsKt___CollectionsKt.getOrNull(0, arrayList2);
        this.isBModeUser = userInfo != null ? userInfo.isBMode() : false;
        this.isOwner = this.currentUserId == 0;
        SettingsHelper settingsHelper = this.settingsHelper;
        boolean hasTwoPhoneAccount = settingsHelper.hasTwoPhoneAccount();
        boolean isTwoPhoneRegistered = settingsHelper.isTwoPhoneRegistered();
        SettingsHelper.ItemMap itemMap = settingsHelper.mItemLists;
        this.state = new TwoPhoneModeState(hasTwoPhoneAccount, isTwoPhoneRegistered, itemMap.get("two_call_enabled").getIntValue() == 1, itemMap.get("two_sms_enabled").getIntValue() == 1);
        boolean z2 = this.isBModeCreated;
        Context context = this.context;
        if (z2 || context.getResources().getBoolean(android.R.bool.config_earcFeatureDisabled_allowed)) {
            TwoPhoneModeState twoPhoneModeState = this.state;
            if (twoPhoneModeState.userCreated) {
                boolean z3 = this.isOwner;
                boolean z4 = twoPhoneModeState.msgEnabled;
                boolean z5 = twoPhoneModeState.registered;
                boolean z6 = twoPhoneModeState.callEnabled;
                if (z3) {
                    if ((z6 || z4) && z5) {
                        ImageView imageView = this.modeIconView;
                        if (imageView == null) {
                            imageView = null;
                        }
                        imageView.setContentDescription(context.getString(R.string.status_bar_one_phone_mode_tts));
                        i = R.drawable.stat_sys_two_phone_p_mode;
                    }
                } else if (this.isBModeUser) {
                    if ((z6 || z4) && z5) {
                        ImageView imageView2 = this.modeIconView;
                        if (imageView2 == null) {
                            imageView2 = null;
                        }
                        imageView2.setContentDescription(context.getString(R.string.status_bar_two_phone_mode_tts));
                        i = R.drawable.stat_sys_two_phone_b_mode;
                    } else {
                        ImageView imageView3 = this.modeIconView;
                        if (imageView3 == null) {
                            imageView3 = null;
                        }
                        imageView3.setContentDescription(context.getString(R.string.status_bar_two_phone_mode_blocked_tts));
                        i = R.drawable.stat_sys_two_phone_b_mode_blocked;
                    }
                }
                Log.d("TwoPhoneModeIconController", "updateTwoPhoneMode state=" + this.state + " current user(" + this.currentUserId + ") is BMode=" + this.isBModeUser + " -> icon=" + i);
                boolean z7 = i == 0;
                if (this.modeIconView != null) {
                    return;
                }
                boolean z8 = z7 && !((SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator).isBlocked(SPluginSlimIndicatorModel.DB_KEY_TWO_PHONE_MODE_ICON);
                ImageView imageView4 = this.modeIconView;
                if (imageView4 == null) {
                    imageView4 = null;
                }
                imageView4.setImageResource(i);
                ImageView imageView5 = this.modeIconView;
                (imageView5 != null ? imageView5 : null).setVisibility(z8 ? 0 : 8);
                return;
            }
        }
        i = 0;
        Log.d("TwoPhoneModeIconController", "updateTwoPhoneMode state=" + this.state + " current user(" + this.currentUserId + ") is BMode=" + this.isBModeUser + " -> icon=" + i);
        if (i == 0) {
        }
        if (this.modeIconView != null) {
        }
    }
}
