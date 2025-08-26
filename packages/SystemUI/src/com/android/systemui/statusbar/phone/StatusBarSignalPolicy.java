package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.os.DeadObjectException;
import android.os.Handler;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.net.VpnConfig;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.TaskbarIndicatorController;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener$Stub$Proxy;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.phone.ui.StatusBarIconList;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor;
import com.android.systemui.statusbar.pipeline.ethernet.shared.StatusBarSignalPolicyRefactorEthernet;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public class StatusBarSignalPolicy implements SignalCallback, SecurityController.SecurityControllerCallback, TunerService.Tunable, CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AirplaneModeInteractor mAirplaneModeInteractor;
    public int mAirplaneResId;
    public final Context mContext;
    public final EthernetInteractor mEthernetInteractor;
    public boolean mHideAirplane;
    public boolean mHideEthernet;
    public boolean mHideMobile;
    public final StatusBarIconController mIconController;
    public boolean mIsAirplaneMode;
    public final JavaAdapter mJavaAdapter;
    public final NetworkController mNetworkController;
    public final SecurityController mSecurityController;
    public final String mSlotAirplane;
    public final String mSlotEthernet;
    public final String mSlotMobile;
    public final String mSlotVpn;
    public final TaskbarIndicatorController mTaskbarIndicatorController;
    public final TunerService mTunerService;
    public final Handler mHandler = Handler.getMain();
    public final AnonymousClass1 mDesktopStatusBarIconUpdateCallback = new DesktopCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarSignalPolicy.1
        @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.DesktopCallback
        public final void updateDesktopStatusBarIcons() {
            StatusBarSignalPolicy statusBarSignalPolicy = StatusBarSignalPolicy.this;
            TaskbarIndicatorController taskbarIndicatorController = statusBarSignalPolicy.mTaskbarIndicatorController;
            boolean z = statusBarSignalPolicy.mIsAirplaneMode;
            int i = statusBarSignalPolicy.mAirplaneResId;
            taskbarIndicatorController.getClass();
            try {
                ITaskbarStatusIconListener$Stub$Proxy iTaskbarStatusIconListener$Stub$Proxy = taskbarIndicatorController.taskbarStatusIconListener;
                if (iTaskbarStatusIconListener$Stub$Proxy != null) {
                    iTaskbarStatusIconListener$Stub$Proxy.setAirplaneMode(z, i);
                }
            } catch (DeadObjectException unused) {
                Log.e(taskbarIndicatorController.TAG, "setAirplaneMode taskbarStatusIconListener was dead, but non-null");
            }
        }
    };

    public interface DesktopCallback {
        void updateDesktopStatusBarIcons();
    }

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.statusbar.phone.StatusBarSignalPolicy$1] */
    public StatusBarSignalPolicy(Context context, StatusBarIconController statusBarIconController, NetworkController networkController, SecurityController securityController, TunerService tunerService, JavaAdapter javaAdapter, AirplaneModeInteractor airplaneModeInteractor, EthernetInteractor ethernetInteractor, CoverScreenIconController coverScreenIconController, TaskbarIndicatorController taskbarIndicatorController) {
        this.mContext = context;
        this.mIconController = statusBarIconController;
        this.mJavaAdapter = javaAdapter;
        this.mNetworkController = networkController;
        this.mSecurityController = securityController;
        this.mTunerService = tunerService;
        this.mAirplaneModeInteractor = airplaneModeInteractor;
        this.mEthernetInteractor = ethernetInteractor;
        this.mSlotAirplane = context.getString(17043261);
        this.mSlotMobile = context.getString(17043287);
        this.mSlotEthernet = context.getString(17043276);
        this.mSlotVpn = context.getString(17043308);
        this.mTaskbarIndicatorController = taskbarIndicatorController;
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController.SecurityControllerCallback
    public final void onStateChanged() {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarSignalPolicy$$ExternalSyntheticLambda0
            /* JADX WARN: Removed duplicated region for block: B:52:0x00d1  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
                boolean z;
                boolean z2;
                boolean vpnValidationStatus;
                StatusBarSignalPolicy statusBarSignalPolicy = this.f$0;
                SecurityControllerImpl securityControllerImpl = (SecurityControllerImpl) statusBarSignalPolicy.mSecurityController;
                int[] profileIdsWithDisabled = securityControllerImpl.mUserManager.getProfileIdsWithDisabled(securityControllerImpl.mVpnUserId);
                int length = profileIdsWithDisabled.length;
                int i = 0;
                while (true) {
                    z = true;
                    if (i >= length) {
                        z2 = false;
                        break;
                    }
                    if (securityControllerImpl.mCurrentVpns.get(profileIdsWithDisabled[i]) != null) {
                        z2 = true;
                        break;
                    }
                    i++;
                }
                boolean zIsVpnBranded = securityControllerImpl.isVpnBranded();
                VpnConfig vpnConfig = (VpnConfig) securityControllerImpl.mCurrentVpns.get(securityControllerImpl.mVpnUserId);
                if (vpnConfig == null) {
                    int[] enabledProfileIds = securityControllerImpl.mUserManager.getEnabledProfileIds(securityControllerImpl.mVpnUserId);
                    int length2 = enabledProfileIds.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length2) {
                            vpnValidationStatus = true;
                            break;
                        }
                        VpnConfig vpnConfig2 = (VpnConfig) securityControllerImpl.mCurrentVpns.get(enabledProfileIds[i2]);
                        if (vpnConfig2 != null && !securityControllerImpl.getVpnValidationStatus(vpnConfig2)) {
                            vpnValidationStatus = false;
                            break;
                        }
                        i2++;
                    }
                } else {
                    vpnValidationStatus = securityControllerImpl.getVpnValidationStatus(vpnConfig);
                }
                int i3 = zIsVpnBranded ? vpnValidationStatus ? R.drawable.stat_sys_branded_vpn : R.drawable.stat_sys_no_internet_branded_vpn : vpnValidationStatus ? R.drawable.stat_sys_vpn_ic : R.drawable.stat_sys_no_internet_vpn_ic;
                if (z2 && securityControllerImpl.isSecureWifiEnabled()) {
                    i3 = R.drawable.stat_sys_securewifi_ic;
                }
                Iterator it = securityControllerImpl.mUserManager.getUsers().iterator();
                while (true) {
                    if (it.hasNext()) {
                        VpnConfig vpnConfig3 = (VpnConfig) securityControllerImpl.mCurrentVpns.get(((UserInfo) it.next()).id);
                        if (vpnConfig3 != null && vpnConfig3.legacy) {
                            break;
                        }
                    } else if (securityControllerImpl.isSecureWifiEnabled()) {
                        PackageManager packageManager = securityControllerImpl.mContext.getPackageManager();
                        if (packageManager.checkSignatures("android", "com.samsung.android.fast") == 0) {
                            if (packageManager.getPackageInfo("com.samsung.android.fast", 0).applicationInfo.targetSdkVersion >= 33) {
                                if (packageManager.checkPermission("android.permission.POST_NOTIFICATIONS", "com.samsung.android.fast") != 0) {
                                    z = false;
                                }
                            }
                        }
                    }
                }
                String string = statusBarSignalPolicy.mContext.getResources().getString(R.string.accessibility_vpn_on);
                StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarSignalPolicy.mIconController;
                ArrayList arrayList = statusBarIconControllerImpl.mSystemIconsAllowList;
                final String str = statusBarSignalPolicy.mSlotVpn;
                boolean zContains = arrayList.contains(str);
                if (z) {
                    if (zContains) {
                        statusBarIconControllerImpl.mSystemIconsAllowList.removeIf(new Predicate() { // from class: com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl$$ExternalSyntheticLambda3
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                String str2 = str;
                                String str3 = StatusBarIconControllerImpl.EXTERNAL_SLOT_SUFFIX;
                                return ((String) obj).equals(str2);
                            }
                        });
                    }
                } else if (!zContains) {
                    statusBarIconControllerImpl.mSystemIconsAllowList.add(str);
                }
                if (statusBarIconControllerImpl.mStatusBarPipelineFlags.isIconControlledByFlags(str)) {
                    Log.i("StatusBarIconController", "Ignoring removal of (" + str + "). It should be controlled elsewhere");
                } else {
                    StatusBarIconList statusBarIconList = statusBarIconControllerImpl.mStatusBarIconList;
                    if (statusBarIconList.getIconHolder(0, str) != null) {
                        int viewIndex = statusBarIconList.getViewIndex(0, str);
                        ((StatusBarIconList.Slot) statusBarIconList.mSlots.get(statusBarIconList.findOrInsertSlot(str))).removeForTag(0);
                        statusBarIconControllerImpl.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda2(viewIndex, 2));
                    }
                }
                statusBarIconControllerImpl.setIcon(string, str, i3);
                statusBarIconControllerImpl.setIconVisibility(str, z2);
            }
        });
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if ("icon_blacklist".equals(str)) {
            ArraySet iconHideList = StatusBarIconController.getIconHideList(this.mContext, str2);
            boolean zContains = iconHideList.contains(this.mSlotAirplane);
            boolean zContains2 = iconHideList.contains(this.mSlotMobile);
            boolean zContains3 = iconHideList.contains(this.mSlotEthernet);
            if (zContains == this.mHideAirplane && zContains2 == this.mHideMobile && zContains3 == this.mHideEthernet) {
                return;
            }
            this.mHideAirplane = zContains;
            this.mHideMobile = zContains2;
            this.mHideEthernet = zContains3;
            NetworkControllerImpl networkControllerImpl = (NetworkControllerImpl) this.mNetworkController;
            networkControllerImpl.removeCallback(this);
            networkControllerImpl.addCallback(this);
            updateAirplaneModeIcon(((Boolean) this.mAirplaneModeInteractor.isAirplaneMode.$$delegate_0.getValue()).booleanValue());
        }
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setEthernetIndicators(IconState iconState) {
        int i = StatusBarSignalPolicyRefactorEthernet.$r8$clinit;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mTunerService.addTunable(this, "icon_blacklist");
        ((NetworkControllerImpl) this.mNetworkController).addCallback(this);
        ((SecurityControllerImpl) this.mSecurityController).addCallback(this);
        ReadonlyStateFlow readonlyStateFlow = this.mAirplaneModeInteractor.isAirplaneMode;
        final int i = 0;
        Consumer consumer = new Consumer(this) { // from class: com.android.systemui.statusbar.phone.StatusBarSignalPolicy$$ExternalSyntheticLambda1
            public final /* synthetic */ StatusBarSignalPolicy f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                StatusBarSignalPolicy statusBarSignalPolicy = this.f$0;
                switch (i2) {
                    case 0:
                        statusBarSignalPolicy.updateAirplaneModeIcon(((Boolean) obj).booleanValue());
                        break;
                    default:
                        Icon.Resource resource = (Icon.Resource) obj;
                        int i3 = StatusBarSignalPolicy.$r8$clinit;
                        statusBarSignalPolicy.getClass();
                        int i4 = StatusBarSignalPolicyRefactorEthernet.$r8$clinit;
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        String str = statusBarSignalPolicy.mSlotEthernet;
                        StatusBarIconController statusBarIconController = statusBarSignalPolicy.mIconController;
                        if (resource == null) {
                            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
                            break;
                        } else {
                            Context context = statusBarSignalPolicy.mContext;
                            ContentDescription.Companion.getClass();
                            StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarIconController;
                            statusBarIconControllerImpl.setIcon(ContentDescription.Companion.loadContentDescription(resource.contentDescription, context), str, resource.res);
                            statusBarIconControllerImpl.setIconVisibility(str, true);
                            break;
                        }
                }
            }
        };
        JavaAdapter javaAdapter = this.mJavaAdapter;
        javaAdapter.alwaysCollectFlow(readonlyStateFlow, consumer);
        int i2 = StatusBarSignalPolicyRefactorEthernet.$r8$clinit;
        final int i3 = 1;
        javaAdapter.alwaysCollectFlow(this.mEthernetInteractor.icon, new Consumer(this) { // from class: com.android.systemui.statusbar.phone.StatusBarSignalPolicy$$ExternalSyntheticLambda1
            public final /* synthetic */ StatusBarSignalPolicy f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i3;
                StatusBarSignalPolicy statusBarSignalPolicy = this.f$0;
                switch (i22) {
                    case 0:
                        statusBarSignalPolicy.updateAirplaneModeIcon(((Boolean) obj).booleanValue());
                        break;
                    default:
                        Icon.Resource resource = (Icon.Resource) obj;
                        int i32 = StatusBarSignalPolicy.$r8$clinit;
                        statusBarSignalPolicy.getClass();
                        int i4 = StatusBarSignalPolicyRefactorEthernet.$r8$clinit;
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        String str = statusBarSignalPolicy.mSlotEthernet;
                        StatusBarIconController statusBarIconController = statusBarSignalPolicy.mIconController;
                        if (resource == null) {
                            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
                            break;
                        } else {
                            Context context = statusBarSignalPolicy.mContext;
                            ContentDescription.Companion.getClass();
                            StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarIconController;
                            statusBarIconControllerImpl.setIcon(ContentDescription.Companion.loadContentDescription(resource.contentDescription, context), str, resource.res);
                            statusBarIconControllerImpl.setIconVisibility(str, true);
                            break;
                        }
                }
            }
        });
        this.mTaskbarIndicatorController.setDesktopStatusBarIconCallback(this.mDesktopStatusBarIconUpdateCallback);
    }

    public final void updateAirplaneModeIcon(boolean z) {
        int i = StatusBarSignalPolicyRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        boolean z2 = z && !this.mHideAirplane;
        this.mIsAirplaneMode = z2;
        StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) this.mIconController;
        String str = this.mSlotAirplane;
        statusBarIconControllerImpl.setIconVisibility(str, z2);
        if (this.mIsAirplaneMode) {
            statusBarIconControllerImpl.setIcon(this.mContext.getString(R.string.accessibility_airplane_mode), str, R.drawable.samsung_stat_sys_airplane_mode);
        }
        this.mAirplaneResId = R.drawable.samsung_stat_sys_airplane_mode;
        boolean z3 = this.mIsAirplaneMode;
        TaskbarIndicatorController taskbarIndicatorController = this.mTaskbarIndicatorController;
        taskbarIndicatorController.getClass();
        try {
            ITaskbarStatusIconListener$Stub$Proxy iTaskbarStatusIconListener$Stub$Proxy = taskbarIndicatorController.taskbarStatusIconListener;
            if (iTaskbarStatusIconListener$Stub$Proxy != null) {
                iTaskbarStatusIconListener$Stub$Proxy.setAirplaneMode(z3, R.drawable.samsung_stat_sys_airplane_mode);
            }
        } catch (DeadObjectException unused) {
            Log.e(taskbarIndicatorController.TAG, "setAirplaneMode taskbarStatusIconListener was dead, but non-null");
        }
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setIsAirplaneMode(IconState iconState) {
    }
}
