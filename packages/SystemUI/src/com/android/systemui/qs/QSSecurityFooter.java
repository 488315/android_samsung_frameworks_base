package com.android.systemui.qs;

import android.app.Dialog;
import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.bar.SecurityFooterBar;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import com.android.systemui.util.ShadowDelegateUtil;
import com.android.systemui.util.ViewController;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSSecurityFooter extends ViewController implements View.OnClickListener, DialogInterface.OnClickListener {
    public final ActivityStarter mActivityStarter;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Callback mCallback;
    public final Context mContext;
    public SystemUIDialog mDialog;
    public final DevicePolicyManager mDpm;
    public final TextView mFooterText;
    public CharSequence mFooterTextContent;
    public final H mHandler;
    public boolean mIsVisible;
    public final Handler mMainHandler;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mManagementDialogCaCertStringSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mManagementDialogNetworkStringSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mManagementDialogStringSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mManagementMessageSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mManagementMonitoringStringSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mManagementMultipleVpnStringSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mManagementTitleSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mMonitoringSubtitleCaCertStringSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mMonitoringSubtitleNetworkStringSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mMonitoringSubtitleVpnStringSupplier;
    public final ImageView mPrimaryFooterIcon;
    public Drawable mPrimaryFooterIconDrawable;
    public int mPrimaryFooterIconId;
    public final AnonymousClass1 mReceiver;
    public final SecurityController mSecurityController;
    public final AtomicBoolean mShouldUseSettingsButton;
    public final AnonymousClass3 mUpdateDisplayState;
    public final AnonymousClass2 mUpdatePrimaryIcon;
    public final UserTracker mUserTracker;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mViewPoliciesButtonStringSupplier;
    public SecurityFooterBar mVisibilityChangedListener;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mWorkProfileDialogCaCertStringSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mWorkProfileDialogNetworkStringSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mWorkProfileMonitoringStringSupplier;
    public final QSSecurityFooter$$ExternalSyntheticLambda0 mWorkProfileNetworkStringSupplier;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Callback implements SecurityController.SecurityControllerCallback {
        public /* synthetic */ Callback(QSSecurityFooter qSSecurityFooter, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.SecurityController.SecurityControllerCallback
        public final void onStateChanged() {
            QSSecurityFooter.this.mHandler.sendEmptyMessage(1);
        }

        private Callback() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class H extends Handler {
        public /* synthetic */ H(QSSecurityFooter qSSecurityFooter, Looper looper, int i) {
            this(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String str = null;
            try {
                int i = message.what;
                QSSecurityFooter qSSecurityFooter = QSSecurityFooter.this;
                if (i == 1) {
                    str = "handleRefreshState";
                    QSSecurityFooter.m2061$$Nest$mhandleRefreshState(qSSecurityFooter);
                } else if (i == 0) {
                    str = "handleClick";
                    qSSecurityFooter.mShouldUseSettingsButton.set(false);
                    qSSecurityFooter.mHandler.post(new QSSecurityFooter$$ExternalSyntheticLambda28(qSSecurityFooter));
                    DevicePolicyEventLogger.createEvent(57).write();
                }
            } catch (Throwable th) {
                Log.w("QSSecurityFooter", "Error in " + str, th);
            }
        }

        private H(Looper looper) {
            super(looper);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class VpnSpan extends ClickableSpan {
        public VpnSpan() {
        }

        public final boolean equals(Object obj) {
            return obj instanceof VpnSpan;
        }

        public final int hashCode() {
            return 314159257;
        }

        @Override // android.text.style.ClickableSpan
        public final void onClick(View view) {
            Intent intent = new Intent("android.settings.VPN_SETTINGS");
            QSSecurityFooter.this.mDialog.dismiss();
            QSSecurityFooter.this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        }
    }

    /* renamed from: -$$Nest$mhandleRefreshState, reason: not valid java name */
    public static void m2061$$Nest$mhandleRefreshState(QSSecurityFooter qSSecurityFooter) {
        CharSequence charSequence;
        boolean z;
        boolean z2;
        boolean z3;
        CharSequence charSequence2;
        String string;
        Drawable loadIcon;
        boolean isDeviceManaged = ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).mDevicePolicyManager.isDeviceManaged();
        boolean z4 = UserManager.isDeviceInDemoMode(qSSecurityFooter.mContext) && ((UserTrackerImpl) qSSecurityFooter.mUserTracker).getUserInfo().isDemo();
        boolean hasWorkProfile$1 = ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).hasWorkProfile$1();
        boolean hasCACertInCurrentUser = ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).hasCACertInCurrentUser();
        boolean hasCACertInWorkProfile = ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).hasCACertInWorkProfile();
        boolean isNetworkLoggingEnabled = ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).mDevicePolicyManager.isNetworkLoggingEnabled(null);
        String primaryVpnName = ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).getPrimaryVpnName();
        String workProfileVpnName = ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).getWorkProfileVpnName();
        CharSequence deviceOwnerOrganizationName = ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).mDevicePolicyManager.getDeviceOwnerOrganizationName();
        SecurityControllerImpl securityControllerImpl = (SecurityControllerImpl) qSSecurityFooter.mSecurityController;
        int workProfileUserId$1 = securityControllerImpl.getWorkProfileUserId$1(securityControllerImpl.mCurrentUserId);
        CharSequence organizationNameForUser = workProfileUserId$1 == -10000 ? null : securityControllerImpl.mDevicePolicyManager.getOrganizationNameForUser(workProfileUserId$1);
        boolean isOrganizationOwnedDeviceWithManagedProfile = ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).mDevicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile();
        boolean isParentalControlsEnabled = ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).isParentalControlsEnabled();
        SecurityControllerImpl securityControllerImpl2 = (SecurityControllerImpl) qSSecurityFooter.mSecurityController;
        UserHandle of = UserHandle.of(securityControllerImpl2.getWorkProfileUserId$1(securityControllerImpl2.mCurrentUserId));
        boolean z5 = (of == null || securityControllerImpl2.mUserManager.isQuietModeEnabled(of)) ? false : true;
        boolean z6 = hasCACertInWorkProfile || workProfileVpnName != null || (hasWorkProfile$1 && isNetworkLoggingEnabled);
        boolean z7 = qSSecurityFooter.mIsVisible;
        if ((isDeviceManaged && !z4) || hasCACertInCurrentUser || primaryVpnName != null || isOrganizationOwnedDeviceWithManagedProfile || isParentalControlsEnabled || (z6 && z5)) {
            charSequence = deviceOwnerOrganizationName;
            z = true;
        } else {
            charSequence = deviceOwnerOrganizationName;
            z = false;
        }
        if (z7 != z) {
            charSequence2 = organizationNameForUser;
            z3 = isNetworkLoggingEnabled;
            z2 = hasWorkProfile$1;
            StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("updateVisibility: isVisible: ", " -> ", " [(isDeviceManaged(", z7, z);
            m.append(isDeviceManaged);
            m.append(") && !isDemoDevice(");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, !z4, ")) || hasCACerts(", hasCACertInCurrentUser, ") || vpnName!=null(");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, primaryVpnName != null, ") || isProfileOwnerOfOrganizationOwnedDevice(", isOrganizationOwnedDeviceWithManagedProfile, ") || isParentalControlsEnabled(");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, isParentalControlsEnabled, ") || (hasDisclosableWorkProfilePolicy(", z6, ") && isWorkProfileOn(");
            m.append(z5);
            m.append("))]");
            Log.d("QSSecurityFooter", m.toString());
        } else {
            z2 = hasWorkProfile$1;
            z3 = isNetworkLoggingEnabled;
            charSequence2 = organizationNameForUser;
        }
        qSSecurityFooter.mIsVisible = z;
        if (z && isOrganizationOwnedDeviceWithManagedProfile && (!z6 || !z5)) {
            qSSecurityFooter.mPrimaryFooterIcon.setClickable(false);
            qSSecurityFooter.mPrimaryFooterIcon.setVisibility(8);
        } else {
            qSSecurityFooter.mPrimaryFooterIcon.setClickable(true);
            qSSecurityFooter.mPrimaryFooterIcon.setVisibility(0);
        }
        if (isParentalControlsEnabled) {
            string = qSSecurityFooter.mContext.getString(R.string.quick_settings_disclosure_parental_controls);
        } else if (isDeviceManaged) {
            if (hasCACertInCurrentUser || hasCACertInWorkProfile || z3) {
                CharSequence charSequence3 = charSequence;
                string = charSequence3 == null ? qSSecurityFooter.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT_MONITORING", qSSecurityFooter.mManagementMonitoringStringSupplier) : qSSecurityFooter.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_MONITORING", new QSSecurityFooter$$ExternalSyntheticLambda15(qSSecurityFooter, charSequence3, 2), charSequence3);
            } else if (primaryVpnName == null && workProfileVpnName == null) {
                string = qSSecurityFooter.getMangedDeviceGeneralText(charSequence);
            } else {
                CharSequence charSequence4 = charSequence;
                if (primaryVpnName == null || workProfileVpnName == null) {
                    String str = primaryVpnName != null ? primaryVpnName : workProfileVpnName;
                    string = charSequence4 == null ? qSSecurityFooter.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda17(qSSecurityFooter, str, 3), str) : qSSecurityFooter.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda16(qSSecurityFooter, charSequence4, str), charSequence4, str);
                } else {
                    string = charSequence4 == null ? qSSecurityFooter.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT_MULTIPLE_VPNS", qSSecurityFooter.mManagementMultipleVpnStringSupplier) : qSSecurityFooter.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_MULTIPLE_VPNS", new QSSecurityFooter$$ExternalSyntheticLambda15(qSSecurityFooter, charSequence4, 1), charSequence4);
                }
            }
        } else if (hasCACertInCurrentUser || (hasCACertInWorkProfile && z5)) {
            CharSequence charSequence5 = charSequence2;
            if (hasCACertInWorkProfile && z5) {
                string = charSequence5 == null ? qSSecurityFooter.mDpm.getResources().getString("SystemUi.QS_MSG_WORK_PROFILE_MONITORING", qSSecurityFooter.mWorkProfileMonitoringStringSupplier) : qSSecurityFooter.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_WORK_PROFILE_MONITORING", new QSSecurityFooter$$ExternalSyntheticLambda15(qSSecurityFooter, charSequence5, 3), charSequence5);
            } else {
                if (hasCACertInCurrentUser) {
                    string = qSSecurityFooter.mContext.getString(R.string.quick_settings_disclosure_monitoring);
                }
                string = null;
            }
        } else if (primaryVpnName != null || (workProfileVpnName != null && z5)) {
            if (primaryVpnName != null && workProfileVpnName != null) {
                string = qSSecurityFooter.mContext.getString(R.string.quick_settings_disclosure_vpns);
            } else if (workProfileVpnName == null || !z5) {
                if (primaryVpnName != null) {
                    string = z2 ? qSSecurityFooter.mDpm.getResources().getString("SystemUi.QS_MSG_PERSONAL_PROFILE_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda17(qSSecurityFooter, primaryVpnName, 5), primaryVpnName) : qSSecurityFooter.mContext.getString(R.string.quick_settings_disclosure_named_vpn, primaryVpnName);
                }
                string = null;
            } else {
                string = qSSecurityFooter.mDpm.getResources().getString("SystemUi.QS_MSG_WORK_PROFILE_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda17(qSSecurityFooter, workProfileVpnName, 4), workProfileVpnName);
            }
        } else if (z2 && z3 && z5) {
            string = qSSecurityFooter.mDpm.getResources().getString("SystemUi.QS_MSG_WORK_PROFILE_NETWORK", qSSecurityFooter.mWorkProfileNetworkStringSupplier);
        } else {
            if (isOrganizationOwnedDeviceWithManagedProfile) {
                string = qSSecurityFooter.getMangedDeviceGeneralText(charSequence2);
            }
            string = null;
        }
        qSSecurityFooter.mFooterTextContent = string;
        int i = (primaryVpnName == null && workProfileVpnName == null) ? R.drawable.quickpanel_icon_managed_device : ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).isVpnBranded() ? R.drawable.stat_sys_branded_vpn : R.drawable.stat_sys_vpn_ic;
        if (((SecurityControllerImpl) qSSecurityFooter.mSecurityController).isSecureWifiEnabled()) {
            i = R.drawable.stat_sys_securewifi_ic;
        }
        if (qSSecurityFooter.mPrimaryFooterIconId != i) {
            qSSecurityFooter.mPrimaryFooterIconId = i;
        }
        if (!isParentalControlsEnabled) {
            qSSecurityFooter.mPrimaryFooterIconDrawable = null;
        } else if (qSSecurityFooter.mPrimaryFooterIconDrawable == null) {
            DeviceAdminInfo deviceAdminInfo = ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).getDeviceAdminInfo();
            SecurityControllerImpl securityControllerImpl3 = (SecurityControllerImpl) qSSecurityFooter.mSecurityController;
            if (deviceAdminInfo == null) {
                securityControllerImpl3.getClass();
                loadIcon = null;
            } else {
                loadIcon = deviceAdminInfo.loadIcon(securityControllerImpl3.mPackageManager);
            }
            qSSecurityFooter.mPrimaryFooterIconDrawable = loadIcon;
        }
        qSSecurityFooter.mMainHandler.post(qSSecurityFooter.mUpdatePrimaryIcon);
        qSSecurityFooter.mMainHandler.post(qSSecurityFooter.mUpdateDisplayState);
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v14, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v16, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v17, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v18, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v19, types: [com.android.systemui.qs.QSSecurityFooter$2] */
    /* JADX WARN: Type inference failed for: r0v20, types: [com.android.systemui.qs.QSSecurityFooter$3] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.qs.QSSecurityFooter$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0] */
    public QSSecurityFooter(View view, UserTracker userTracker, Handler handler, ActivityStarter activityStarter, SecurityController securityController, Looper looper, BroadcastDispatcher broadcastDispatcher) {
        super(view);
        int i = 0;
        this.mCallback = new Callback(this, i);
        this.mShouldUseSettingsButton = new AtomicBoolean(false);
        this.mFooterTextContent = null;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.QSSecurityFooter.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.app.action.SHOW_DEVICE_MONITORING_DIALOG")) {
                    QSSecurityFooter qSSecurityFooter = QSSecurityFooter.this;
                    qSSecurityFooter.mShouldUseSettingsButton.set(false);
                    qSSecurityFooter.mHandler.post(new QSSecurityFooter$$ExternalSyntheticLambda28(qSSecurityFooter));
                }
            }
        };
        final int i2 = 0;
        this.mManagementTitleSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i3 = i2;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i3) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i3 = 11;
        this.mManagementMessageSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i3;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i4 = 12;
        this.mManagementMonitoringStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i4;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i5 = 13;
        this.mManagementMultipleVpnStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i5;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i6 = 14;
        this.mWorkProfileMonitoringStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i6;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i7 = 1;
        this.mWorkProfileNetworkStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i7;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i8 = 2;
        this.mMonitoringSubtitleCaCertStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i8;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i9 = 3;
        this.mMonitoringSubtitleNetworkStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i9;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i10 = 4;
        this.mMonitoringSubtitleVpnStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i10;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i11 = 5;
        this.mViewPoliciesButtonStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i11;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i12 = 6;
        this.mManagementDialogStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i12;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i13 = 7;
        this.mManagementDialogCaCertStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i13;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i14 = 8;
        this.mWorkProfileDialogCaCertStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i14;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i15 = 9;
        this.mManagementDialogNetworkStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i15;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        final int i16 = 10;
        this.mWorkProfileDialogNetworkStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0
            public final /* synthetic */ QSSecurityFooter f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i16;
                QSSecurityFooter qSSecurityFooter = this.f$0;
                switch (i32) {
                    case 0:
                        Context context = qSSecurityFooter.mContext;
                        if (context == null) {
                            return null;
                        }
                        return context.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context2 = qSSecurityFooter.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 2:
                        Context context3 = qSSecurityFooter.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 3:
                        Context context4 = qSSecurityFooter.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_subtitle_network_logging);
                    case 4:
                        Context context5 = qSSecurityFooter.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_subtitle_vpn);
                    case 5:
                        Context context6 = qSSecurityFooter.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_button_view_policies);
                    case 6:
                        Context context7 = qSSecurityFooter.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_management);
                    case 7:
                        Context context8 = qSSecurityFooter.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.monitoring_description_management_ca_certificate);
                    case 8:
                        Context context9 = qSSecurityFooter.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 9:
                        Context context10 = qSSecurityFooter.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.monitoring_description_management_network_logging);
                    case 10:
                        Context context11 = qSSecurityFooter.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 11:
                        Context context12 = qSSecurityFooter.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_management);
                    case 12:
                        Context context13 = qSSecurityFooter.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 13:
                        Context context14 = qSSecurityFooter.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.quick_settings_disclosure_management_vpns);
                    default:
                        Context context15 = qSSecurityFooter.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                }
            }
        };
        this.mUpdatePrimaryIcon = new Runnable() { // from class: com.android.systemui.qs.QSSecurityFooter.2
            @Override // java.lang.Runnable
            public final void run() {
                QSSecurityFooter qSSecurityFooter = QSSecurityFooter.this;
                Drawable drawable = qSSecurityFooter.mPrimaryFooterIconDrawable;
                if (drawable == null) {
                    drawable = qSSecurityFooter.mContext.getDrawable(qSSecurityFooter.mPrimaryFooterIconId);
                }
                Resources resources = QSSecurityFooter.this.mContext.getResources();
                QSSecurityFooter.this.mPrimaryFooterIcon.setImageDrawable(ShadowDelegateUtil.INSTANCE.createShadowDrawable(drawable, resources.getDimension(R.dimen.security_shadow_radius), 0.2f, resources.getDimensionPixelSize(R.dimen.sec_qs_footer_icon_size)));
            }
        };
        this.mUpdateDisplayState = new Runnable() { // from class: com.android.systemui.qs.QSSecurityFooter.3
            @Override // java.lang.Runnable
            public final void run() {
                CharSequence charSequence;
                QSSecurityFooter qSSecurityFooter = QSSecurityFooter.this;
                boolean z = qSSecurityFooter.mIsVisible;
                if (z && (charSequence = qSSecurityFooter.mFooterTextContent) != null) {
                    qSSecurityFooter.mFooterText.setText(charSequence);
                }
                if (z && ((ViewController) QSSecurityFooter.this).mView.getVisibility() == 0) {
                    return;
                }
                if (z || ((ViewController) QSSecurityFooter.this).mView.getVisibility() != 8) {
                    ((ViewController) QSSecurityFooter.this).mView.setVisibility(z ? 0 : 8);
                    QSSecurityFooter qSSecurityFooter2 = QSSecurityFooter.this;
                    SecurityFooterBar securityFooterBar = qSSecurityFooter2.mVisibilityChangedListener;
                    if (securityFooterBar != null) {
                        int visibility = ((ViewController) qSSecurityFooter2).mView.getVisibility();
                        securityFooterBar.getClass();
                        securityFooterBar.showBar(visibility == 0);
                    }
                }
            }
        };
        this.mFooterText = (TextView) this.mView.findViewById(R.id.footer_text);
        this.mPrimaryFooterIcon = (ImageView) this.mView.findViewById(R.id.primary_footer_icon);
        this.mPrimaryFooterIconId = R.drawable.quickpanel_icon_managed_device;
        Context context = view.getContext();
        this.mContext = context;
        this.mDpm = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mMainHandler = handler;
        this.mActivityStarter = activityStarter;
        this.mSecurityController = securityController;
        this.mHandler = new H(this, looper, i);
        this.mUserTracker = userTracker;
        this.mBroadcastDispatcher = broadcastDispatcher;
        init();
    }

    public View createDialogView() {
        SpannableStringBuilder spannableStringBuilder;
        Drawable loadIcon;
        CharSequence charSequence = null;
        if (((SecurityControllerImpl) this.mSecurityController).isParentalControlsEnabled()) {
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.sec_quick_settings_footer_dialog_parental_controls, (ViewGroup) null, false);
            DeviceAdminInfo deviceAdminInfo = ((SecurityControllerImpl) this.mSecurityController).getDeviceAdminInfo();
            SecurityControllerImpl securityControllerImpl = (SecurityControllerImpl) this.mSecurityController;
            if (deviceAdminInfo == null) {
                securityControllerImpl.getClass();
                loadIcon = null;
            } else {
                loadIcon = deviceAdminInfo.loadIcon(securityControllerImpl.mPackageManager);
            }
            if (loadIcon != null) {
                ((ImageView) inflate.findViewById(R.id.parental_controls_icon)).setImageDrawable(loadIcon);
            }
            TextView textView = (TextView) inflate.findViewById(R.id.parental_controls_title);
            SecurityControllerImpl securityControllerImpl2 = (SecurityControllerImpl) this.mSecurityController;
            if (deviceAdminInfo == null) {
                securityControllerImpl2.getClass();
            } else {
                charSequence = deviceAdminInfo.loadLabel(securityControllerImpl2.mPackageManager);
            }
            textView.setText(charSequence);
            return inflate;
        }
        boolean isDeviceManaged = ((SecurityControllerImpl) this.mSecurityController).mDevicePolicyManager.isDeviceManaged();
        boolean hasWorkProfile$1 = ((SecurityControllerImpl) this.mSecurityController).hasWorkProfile$1();
        CharSequence deviceOwnerOrganizationName = ((SecurityControllerImpl) this.mSecurityController).mDevicePolicyManager.getDeviceOwnerOrganizationName();
        boolean hasCACertInCurrentUser = ((SecurityControllerImpl) this.mSecurityController).hasCACertInCurrentUser();
        boolean hasCACertInWorkProfile = ((SecurityControllerImpl) this.mSecurityController).hasCACertInWorkProfile();
        boolean isNetworkLoggingEnabled = ((SecurityControllerImpl) this.mSecurityController).mDevicePolicyManager.isNetworkLoggingEnabled(null);
        String primaryVpnName = ((SecurityControllerImpl) this.mSecurityController).getPrimaryVpnName();
        String workProfileVpnName = ((SecurityControllerImpl) this.mSecurityController).getWorkProfileVpnName();
        View inflate2 = LayoutInflater.from(this.mContext).inflate(R.layout.sec_quick_settings_footer_dialog, (ViewGroup) null, false);
        ((TextView) inflate2.findViewById(R.id.device_management_subtitle)).setText(getManagementTitle(deviceOwnerOrganizationName));
        String string = !isDeviceManaged ? null : deviceOwnerOrganizationName != null ? isFinancedDevice() ? this.mContext.getString(R.string.monitoring_financed_description_named_management, deviceOwnerOrganizationName, deviceOwnerOrganizationName) : this.mDpm.getResources().getString("SystemUi.QS_DIALOG_NAMED_MANAGEMENT", new QSSecurityFooter$$ExternalSyntheticLambda15(this, deviceOwnerOrganizationName, 0), deviceOwnerOrganizationName) : this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT", this.mManagementDialogStringSupplier);
        if (string == null) {
            inflate2.findViewById(R.id.device_management_disclosures).setVisibility(8);
        } else {
            inflate2.findViewById(R.id.device_management_disclosures).setVisibility(0);
            ((TextView) inflate2.findViewById(R.id.device_management_warning)).setText(string);
            this.mShouldUseSettingsButton.set(true);
        }
        String string2 = (hasCACertInCurrentUser || hasCACertInWorkProfile) ? isDeviceManaged ? this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_CA_CERT", this.mManagementDialogCaCertStringSupplier) : hasCACertInWorkProfile ? this.mDpm.getResources().getString("SystemUi.QS_DIALOG_WORK_PROFILE_CA_CERT", this.mWorkProfileDialogCaCertStringSupplier) : this.mContext.getString(R.string.monitoring_description_ca_certificate) : null;
        if (string2 == null) {
            inflate2.findViewById(R.id.ca_certs_disclosures).setVisibility(8);
        } else {
            inflate2.findViewById(R.id.ca_certs_disclosures).setVisibility(0);
            TextView textView2 = (TextView) inflate2.findViewById(R.id.ca_certs_warning);
            textView2.setText(string2);
            textView2.setMovementMethod(new LinkMovementMethod());
            ((TextView) inflate2.findViewById(R.id.ca_certs_subtitle)).setText(this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MONITORING_CA_CERT_SUBTITLE", this.mMonitoringSubtitleCaCertStringSupplier));
        }
        String string3 = !isNetworkLoggingEnabled ? null : isDeviceManaged ? this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_NETWORK", this.mManagementDialogNetworkStringSupplier) : this.mDpm.getResources().getString("SystemUi.QS_DIALOG_WORK_PROFILE_NETWORK", this.mWorkProfileDialogNetworkStringSupplier);
        if (string3 == null) {
            inflate2.findViewById(R.id.network_logging_disclosures).setVisibility(8);
        } else {
            inflate2.findViewById(R.id.network_logging_disclosures).setVisibility(0);
            ((TextView) inflate2.findViewById(R.id.network_logging_warning)).setText(string3);
            ((TextView) inflate2.findViewById(R.id.network_logging_subtitle)).setText(this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MONITORING_NETWORK_SUBTITLE", this.mMonitoringSubtitleNetworkStringSupplier));
        }
        if (primaryVpnName == null && workProfileVpnName == null) {
            spannableStringBuilder = null;
        } else {
            spannableStringBuilder = new SpannableStringBuilder();
            if (isDeviceManaged) {
                if (primaryVpnName == null || workProfileVpnName == null) {
                    if (primaryVpnName == null) {
                        primaryVpnName = workProfileVpnName;
                    }
                    spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda17(this, primaryVpnName, 0), primaryVpnName));
                } else {
                    spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_TWO_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda16(this, primaryVpnName, workProfileVpnName, 0), primaryVpnName, workProfileVpnName));
                }
            } else if (primaryVpnName != null && workProfileVpnName != null) {
                spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_TWO_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda16(this, primaryVpnName, workProfileVpnName, 1), primaryVpnName, workProfileVpnName));
            } else if (workProfileVpnName != null) {
                spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_WORK_PROFILE_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda17(this, workProfileVpnName, 1), workProfileVpnName));
            } else if (hasWorkProfile$1) {
                spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_PERSONAL_PROFILE_NAMED_VPN", new QSSecurityFooter$$ExternalSyntheticLambda17(this, primaryVpnName, 2), primaryVpnName));
            } else {
                spannableStringBuilder.append((CharSequence) this.mContext.getString(R.string.monitoring_description_named_vpn, primaryVpnName));
            }
            spannableStringBuilder.append((CharSequence) this.mContext.getString(R.string.monitoring_description_vpn_settings_separator));
            spannableStringBuilder.append(this.mContext.getString(R.string.monitoring_description_vpn_settings), new VpnSpan(), 0);
        }
        if (spannableStringBuilder == null) {
            inflate2.findViewById(R.id.vpn_disclosures).setVisibility(8);
        } else {
            inflate2.findViewById(R.id.vpn_disclosures).setVisibility(0);
            TextView textView3 = (TextView) inflate2.findViewById(R.id.vpn_warning);
            textView3.setText(spannableStringBuilder);
            textView3.setMovementMethod(new LinkMovementMethod());
            ((TextView) inflate2.findViewById(R.id.vpn_subtitle)).setText(this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MONITORING_VPN_SUBTITLE", this.mMonitoringSubtitleVpnStringSupplier));
        }
        boolean z = string != null;
        int i = string2 != null ? 1 : 0;
        boolean z2 = string3 != null;
        boolean z3 = spannableStringBuilder != null;
        if (!z) {
            int i2 = z2 ? i + 1 : i;
            if (z3) {
                i2++;
            }
            if (i2 == 1) {
                if (i != 0) {
                    inflate2.findViewById(R.id.ca_certs_subtitle).setVisibility(8);
                }
                if (z2) {
                    inflate2.findViewById(R.id.network_logging_subtitle).setVisibility(8);
                }
                if (z3) {
                    inflate2.findViewById(R.id.vpn_subtitle).setVisibility(8);
                }
            }
        }
        return inflate2;
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public CharSequence getManagementTitle(CharSequence charSequence) {
        return (charSequence == null || !isFinancedDevice()) ? this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_TITLE", this.mManagementTitleSupplier) : this.mContext.getString(R.string.monitoring_title_financed_device, charSequence);
    }

    public final String getMangedDeviceGeneralText(CharSequence charSequence) {
        return charSequence == null ? this.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT", this.mManagementMessageSupplier) : isFinancedDevice() ? this.mContext.getString(R.string.quick_settings_financed_disclosure_named_management, charSequence) : this.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT", new QSSecurityFooter$$ExternalSyntheticLambda15(this, charSequence, 4), charSequence);
    }

    public String getSettingsButton() {
        return this.mDpm.getResources().getString("SystemUi.QS_DIALOG_VIEW_POLICIES", this.mViewPoliciesButtonStringSupplier);
    }

    public final View getView() {
        return this.mView;
    }

    public final boolean isFinancedDevice() {
        if (((SecurityControllerImpl) this.mSecurityController).mDevicePolicyManager.isDeviceManaged()) {
            SecurityController securityController = this.mSecurityController;
            if (((SecurityControllerImpl) securityController).mDevicePolicyManager.getDeviceOwnerType(((SecurityControllerImpl) securityController).mDevicePolicyManager.getDeviceOwnerComponentOnAnyUser()) == 1) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        this.mHandler.sendEmptyMessage(0);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mBroadcastDispatcher.registerReceiverWithHandler(this.mReceiver, new IntentFilter("android.app.action.SHOW_DEVICE_MONITORING_DIALOG"), this.mHandler, UserHandle.ALL);
        this.mPrimaryFooterIcon.setOnClickListener(this);
        updateTextMaxWidth();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mBroadcastDispatcher.unregisterReceiver(this.mReceiver);
        this.mPrimaryFooterIcon.setOnClickListener(null);
    }

    public final void updateTextMaxWidth() {
        TextView textView = this.mFooterText;
        int panelWidth = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelWidth(this.mContext);
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        textView.setMaxWidth(((panelWidth - (secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getPanelSidePadding(this.mContext) * 2)) - this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_footer_icon_size)) - this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_footer_icon_margin_start));
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            Intent intent = new Intent("android.settings.ENTERPRISE_PRIVACY_SETTINGS");
            dialogInterface.dismiss();
            this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        }
    }
}
