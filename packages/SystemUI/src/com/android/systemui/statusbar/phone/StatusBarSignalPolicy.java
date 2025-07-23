package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.Handler;
import android.util.ArraySet;
import android.util.Log;
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
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor;
import com.android.systemui.statusbar.pipeline.ethernet.shared.StatusBarSignalPolicyRefactorEthernet;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.util.function.Consumer;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        this.mSlotAirplane = context.getString(17043257);
        this.mSlotMobile = context.getString(17043283);
        this.mSlotEthernet = context.getString(17043272);
        this.mSlotVpn = context.getString(17043304);
        this.mTaskbarIndicatorController = taskbarIndicatorController;
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController.SecurityControllerCallback
    public final void onStateChanged() {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarSignalPolicy$$ExternalSyntheticLambda0
            /* JADX WARN: Code restructure failed: missing block: B:55:0x00ce, code lost:
            
                if (r0.checkPermission("android.permission.POST_NOTIFICATIONS", "com.samsung.android.fast") == 0) goto L53;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 336
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarSignalPolicy$$ExternalSyntheticLambda0.run():void");
            }
        });
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if ("icon_blacklist".equals(str)) {
            ArraySet iconHideList = StatusBarIconController.getIconHideList(this.mContext, str2);
            boolean contains = iconHideList.contains(this.mSlotAirplane);
            boolean contains2 = iconHideList.contains(this.mSlotMobile);
            boolean contains3 = iconHideList.contains(this.mSlotEthernet);
            if (contains == this.mHideAirplane && contains2 == this.mHideMobile && contains3 == this.mHideEthernet) {
                return;
            }
            this.mHideAirplane = contains;
            this.mHideMobile = contains2;
            this.mHideEthernet = contains3;
            NetworkController networkController = this.mNetworkController;
            ((NetworkControllerImpl) networkController).removeCallback(this);
            ((NetworkControllerImpl) networkController).addCallback(this);
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
                boolean z = false;
                int i2 = i;
                StatusBarSignalPolicy statusBarSignalPolicy = this.f$0;
                switch (i2) {
                    case 0:
                        boolean booleanValue = ((Boolean) obj).booleanValue();
                        statusBarSignalPolicy.getClass();
                        int i3 = StatusBarSignalPolicyRefactor.$r8$clinit;
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        if (booleanValue && !statusBarSignalPolicy.mHideAirplane) {
                            z = true;
                        }
                        statusBarSignalPolicy.mIsAirplaneMode = z;
                        StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarSignalPolicy.mIconController;
                        String str = statusBarSignalPolicy.mSlotAirplane;
                        statusBarIconControllerImpl.setIconVisibility(str, z);
                        if (statusBarSignalPolicy.mIsAirplaneMode) {
                            statusBarIconControllerImpl.setIcon(statusBarSignalPolicy.mContext.getString(R.string.accessibility_airplane_mode), str, R.drawable.samsung_stat_sys_airplane_mode);
                        }
                        statusBarSignalPolicy.mAirplaneResId = R.drawable.samsung_stat_sys_airplane_mode;
                        boolean z2 = statusBarSignalPolicy.mIsAirplaneMode;
                        TaskbarIndicatorController taskbarIndicatorController = statusBarSignalPolicy.mTaskbarIndicatorController;
                        taskbarIndicatorController.getClass();
                        try {
                            ITaskbarStatusIconListener$Stub$Proxy iTaskbarStatusIconListener$Stub$Proxy = taskbarIndicatorController.taskbarStatusIconListener;
                            if (iTaskbarStatusIconListener$Stub$Proxy != null) {
                                iTaskbarStatusIconListener$Stub$Proxy.setAirplaneMode(z2, R.drawable.samsung_stat_sys_airplane_mode);
                                break;
                            }
                        } catch (DeadObjectException unused) {
                            Log.e(taskbarIndicatorController.TAG, "setAirplaneMode taskbarStatusIconListener was dead, but non-null");
                            return;
                        }
                        break;
                    default:
                        Icon.Resource resource = (Icon.Resource) obj;
                        int i4 = StatusBarSignalPolicy.$r8$clinit;
                        statusBarSignalPolicy.getClass();
                        int i5 = StatusBarSignalPolicyRefactorEthernet.$r8$clinit;
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        String str2 = statusBarSignalPolicy.mSlotEthernet;
                        StatusBarIconController statusBarIconController = statusBarSignalPolicy.mIconController;
                        if (resource == null) {
                            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str2, false);
                            break;
                        } else {
                            Context context = statusBarSignalPolicy.mContext;
                            ContentDescription.Companion.getClass();
                            StatusBarIconControllerImpl statusBarIconControllerImpl2 = (StatusBarIconControllerImpl) statusBarIconController;
                            statusBarIconControllerImpl2.setIcon(ContentDescription.Companion.loadContentDescription(resource.contentDescription, context), str2, resource.res);
                            statusBarIconControllerImpl2.setIconVisibility(str2, true);
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
                boolean z = false;
                int i22 = i3;
                StatusBarSignalPolicy statusBarSignalPolicy = this.f$0;
                switch (i22) {
                    case 0:
                        boolean booleanValue = ((Boolean) obj).booleanValue();
                        statusBarSignalPolicy.getClass();
                        int i32 = StatusBarSignalPolicyRefactor.$r8$clinit;
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        if (booleanValue && !statusBarSignalPolicy.mHideAirplane) {
                            z = true;
                        }
                        statusBarSignalPolicy.mIsAirplaneMode = z;
                        StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarSignalPolicy.mIconController;
                        String str = statusBarSignalPolicy.mSlotAirplane;
                        statusBarIconControllerImpl.setIconVisibility(str, z);
                        if (statusBarSignalPolicy.mIsAirplaneMode) {
                            statusBarIconControllerImpl.setIcon(statusBarSignalPolicy.mContext.getString(R.string.accessibility_airplane_mode), str, R.drawable.samsung_stat_sys_airplane_mode);
                        }
                        statusBarSignalPolicy.mAirplaneResId = R.drawable.samsung_stat_sys_airplane_mode;
                        boolean z2 = statusBarSignalPolicy.mIsAirplaneMode;
                        TaskbarIndicatorController taskbarIndicatorController = statusBarSignalPolicy.mTaskbarIndicatorController;
                        taskbarIndicatorController.getClass();
                        try {
                            ITaskbarStatusIconListener$Stub$Proxy iTaskbarStatusIconListener$Stub$Proxy = taskbarIndicatorController.taskbarStatusIconListener;
                            if (iTaskbarStatusIconListener$Stub$Proxy != null) {
                                iTaskbarStatusIconListener$Stub$Proxy.setAirplaneMode(z2, R.drawable.samsung_stat_sys_airplane_mode);
                                break;
                            }
                        } catch (DeadObjectException unused) {
                            Log.e(taskbarIndicatorController.TAG, "setAirplaneMode taskbarStatusIconListener was dead, but non-null");
                            return;
                        }
                        break;
                    default:
                        Icon.Resource resource = (Icon.Resource) obj;
                        int i4 = StatusBarSignalPolicy.$r8$clinit;
                        statusBarSignalPolicy.getClass();
                        int i5 = StatusBarSignalPolicyRefactorEthernet.$r8$clinit;
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        String str2 = statusBarSignalPolicy.mSlotEthernet;
                        StatusBarIconController statusBarIconController = statusBarSignalPolicy.mIconController;
                        if (resource == null) {
                            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str2, false);
                            break;
                        } else {
                            Context context = statusBarSignalPolicy.mContext;
                            ContentDescription.Companion.getClass();
                            StatusBarIconControllerImpl statusBarIconControllerImpl2 = (StatusBarIconControllerImpl) statusBarIconController;
                            statusBarIconControllerImpl2.setIcon(ContentDescription.Companion.loadContentDescription(resource.contentDescription, context), str2, resource.res);
                            statusBarIconControllerImpl2.setIconVisibility(str2, true);
                            break;
                        }
                }
            }
        });
        this.mTaskbarIndicatorController.setDesktopStatusBarIconCallback(this.mDesktopStatusBarIconUpdateCallback);
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setIsAirplaneMode(IconState iconState) {
    }
}
