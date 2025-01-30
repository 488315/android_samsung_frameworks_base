package com.android.systemui.qs;

import android.app.Dialog;
import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.provider.DeviceConfig;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSSecurityFooterUtils implements DialogInterface.OnClickListener {
    public final ActivityStarter mActivityStarter;
    public final Context mContext;
    public final DevicePolicyManager mDpm;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mManagementDialogCaCertStringSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mManagementDialogNetworkStringSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mManagementDialogStringSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mManagementMessageSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mManagementMonitoringStringSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mManagementMultipleVpnStringSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mManagementTitleSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mMonitoringSubtitleCaCertStringSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mMonitoringSubtitleNetworkStringSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mMonitoringSubtitleVpnStringSupplier;
    public final SecurityController mSecurityController;
    public final AtomicBoolean mShouldUseSettingsButton = new AtomicBoolean(false);
    public final UserTracker mUserTracker;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mViewPoliciesButtonStringSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mWorkProfileDialogCaCertStringSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mWorkProfileDialogNetworkStringSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mWorkProfileMonitoringStringSupplier;
    public final QSSecurityFooterUtils$$ExternalSyntheticLambda3 mWorkProfileNetworkStringSupplier;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            new Intent("android.settings.VPN_SETTINGS");
            QSSecurityFooterUtils.this.getClass();
            throw null;
        }
    }

    /* JADX WARN: Type inference failed for: r4v10, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v11, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v12, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v13, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v14, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v15, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v16, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v6, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v7, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v8, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v9, types: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3] */
    public QSSecurityFooterUtils(Context context, DevicePolicyManager devicePolicyManager, UserTracker userTracker, Handler handler, ActivityStarter activityStarter, SecurityController securityController, Looper looper, DialogLaunchAnimator dialogLaunchAnimator) {
        final int i = 0;
        this.mManagementTitleSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i2 = 6;
        this.mManagementMessageSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i2) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i3 = 7;
        this.mManagementMonitoringStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i3) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i4 = 8;
        this.mManagementMultipleVpnStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i4) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i5 = 9;
        this.mWorkProfileMonitoringStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i5) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i6 = 10;
        this.mWorkProfileNetworkStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i6) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i7 = 11;
        this.mMonitoringSubtitleCaCertStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i7) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i8 = 12;
        this.mMonitoringSubtitleNetworkStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i8) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i9 = 13;
        this.mMonitoringSubtitleVpnStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i9) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i10 = 14;
        this.mViewPoliciesButtonStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i10) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i11 = 1;
        this.mManagementDialogStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i11) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i12 = 2;
        this.mManagementDialogCaCertStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i12) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i13 = 3;
        this.mWorkProfileDialogCaCertStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i13) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i14 = 4;
        this.mManagementDialogNetworkStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i14) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        final int i15 = 5;
        this.mWorkProfileDialogNetworkStringSupplier = new Supplier(this) { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
            public final /* synthetic */ QSSecurityFooterUtils f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i15) {
                    case 0:
                        Context context2 = this.f$0.mContext;
                        if (context2 == null) {
                            return null;
                        }
                        return context2.getString(R.string.monitoring_title_device_owned);
                    case 1:
                        Context context3 = this.f$0.mContext;
                        if (context3 == null) {
                            return null;
                        }
                        return context3.getString(R.string.monitoring_description_management);
                    case 2:
                        Context context4 = this.f$0.mContext;
                        if (context4 == null) {
                            return null;
                        }
                        return context4.getString(R.string.monitoring_description_management_ca_certificate);
                    case 3:
                        Context context5 = this.f$0.mContext;
                        if (context5 == null) {
                            return null;
                        }
                        return context5.getString(R.string.monitoring_description_managed_profile_ca_certificate);
                    case 4:
                        Context context6 = this.f$0.mContext;
                        if (context6 == null) {
                            return null;
                        }
                        return context6.getString(R.string.monitoring_description_management_network_logging);
                    case 5:
                        Context context7 = this.f$0.mContext;
                        if (context7 == null) {
                            return null;
                        }
                        return context7.getString(R.string.monitoring_description_managed_profile_network_logging);
                    case 6:
                        Context context8 = this.f$0.mContext;
                        if (context8 == null) {
                            return null;
                        }
                        return context8.getString(R.string.quick_settings_disclosure_management);
                    case 7:
                        Context context9 = this.f$0.mContext;
                        if (context9 == null) {
                            return null;
                        }
                        return context9.getString(R.string.quick_settings_disclosure_management_monitoring);
                    case 8:
                        Context context10 = this.f$0.mContext;
                        if (context10 == null) {
                            return null;
                        }
                        return context10.getString(R.string.quick_settings_disclosure_management_vpns);
                    case 9:
                        Context context11 = this.f$0.mContext;
                        if (context11 == null) {
                            return null;
                        }
                        return context11.getString(R.string.quick_settings_disclosure_managed_profile_monitoring);
                    case 10:
                        Context context12 = this.f$0.mContext;
                        if (context12 == null) {
                            return null;
                        }
                        return context12.getString(R.string.quick_settings_disclosure_managed_profile_network_activity);
                    case 11:
                        Context context13 = this.f$0.mContext;
                        if (context13 == null) {
                            return null;
                        }
                        return context13.getString(R.string.monitoring_subtitle_ca_certificate);
                    case 12:
                        Context context14 = this.f$0.mContext;
                        if (context14 == null) {
                            return null;
                        }
                        return context14.getString(R.string.monitoring_subtitle_network_logging);
                    case 13:
                        Context context15 = this.f$0.mContext;
                        if (context15 == null) {
                            return null;
                        }
                        return context15.getString(R.string.monitoring_subtitle_vpn);
                    default:
                        Context context16 = this.f$0.mContext;
                        if (context16 == null) {
                            return null;
                        }
                        return context16.getString(R.string.monitoring_button_view_policies);
                }
            }
        };
        this.mContext = context;
        this.mDpm = devicePolicyManager;
        this.mUserTracker = userTracker;
        this.mActivityStarter = activityStarter;
        this.mSecurityController = securityController;
        new Handler(looper);
    }

    public View createDialogView(Context context) {
        SpannableStringBuilder spannableStringBuilder;
        Drawable loadIcon;
        CharSequence charSequence = null;
        if (((SecurityControllerImpl) this.mSecurityController).isParentalControlsEnabled()) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.quick_settings_footer_dialog_parental_controls, (ViewGroup) null, false);
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
        boolean isDeviceManaged = ((SecurityControllerImpl) this.mSecurityController).isDeviceManaged();
        boolean hasWorkProfile = ((SecurityControllerImpl) this.mSecurityController).hasWorkProfile();
        CharSequence deviceOwnerOrganizationName = ((SecurityControllerImpl) this.mSecurityController).mDevicePolicyManager.getDeviceOwnerOrganizationName();
        boolean hasCACertInCurrentUser = ((SecurityControllerImpl) this.mSecurityController).hasCACertInCurrentUser();
        boolean hasCACertInWorkProfile = ((SecurityControllerImpl) this.mSecurityController).hasCACertInWorkProfile();
        boolean isNetworkLoggingEnabled = ((SecurityControllerImpl) this.mSecurityController).mDevicePolicyManager.isNetworkLoggingEnabled(null);
        String primaryVpnName = ((SecurityControllerImpl) this.mSecurityController).getPrimaryVpnName();
        String workProfileVpnName = ((SecurityControllerImpl) this.mSecurityController).getWorkProfileVpnName();
        View inflate2 = LayoutInflater.from(context).inflate(R.layout.quick_settings_footer_dialog, (ViewGroup) null, false);
        ((TextView) inflate2.findViewById(R.id.device_management_subtitle)).setText(getManagementTitle(deviceOwnerOrganizationName));
        String string = !isDeviceManaged ? null : deviceOwnerOrganizationName != null ? isFinancedDevice() ? this.mContext.getString(R.string.monitoring_financed_description_named_management, deviceOwnerOrganizationName, deviceOwnerOrganizationName) : this.mDpm.getResources().getString("SystemUi.QS_DIALOG_NAMED_MANAGEMENT", new QSSecurityFooterUtils$$ExternalSyntheticLambda0(this, deviceOwnerOrganizationName, 3), deviceOwnerOrganizationName) : this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT", this.mManagementDialogStringSupplier);
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
                    spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_NAMED_VPN", new QSSecurityFooterUtils$$ExternalSyntheticLambda1(this, primaryVpnName, 3), primaryVpnName));
                } else {
                    spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_TWO_NAMED_VPN", new QSSecurityFooterUtils$$ExternalSyntheticLambda2(this, primaryVpnName, workProfileVpnName, 0), primaryVpnName, workProfileVpnName));
                }
            } else if (primaryVpnName != null && workProfileVpnName != null) {
                spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_TWO_NAMED_VPN", new QSSecurityFooterUtils$$ExternalSyntheticLambda2(this, primaryVpnName, workProfileVpnName, 1), primaryVpnName, workProfileVpnName));
            } else if (workProfileVpnName != null) {
                spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_WORK_PROFILE_NAMED_VPN", new QSSecurityFooterUtils$$ExternalSyntheticLambda1(this, workProfileVpnName, 4), workProfileVpnName));
            } else if (hasWorkProfile) {
                spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_PERSONAL_PROFILE_NAMED_VPN", new QSSecurityFooterUtils$$ExternalSyntheticLambda1(this, primaryVpnName, 5), primaryVpnName));
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
        return null;
    }

    public CharSequence getManagementTitle(CharSequence charSequence) {
        return (charSequence == null || !isFinancedDevice()) ? this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_TITLE", this.mManagementTitleSupplier) : this.mContext.getString(R.string.monitoring_title_financed_device, charSequence);
    }

    public final String getMangedDeviceGeneralText(CharSequence charSequence) {
        return charSequence == null ? this.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT", this.mManagementMessageSupplier) : isFinancedDevice() ? this.mContext.getString(R.string.quick_settings_financed_disclosure_named_management, charSequence) : this.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT", new QSSecurityFooterUtils$$ExternalSyntheticLambda0(this, charSequence, 4), charSequence);
    }

    public String getSettingsButton() {
        return this.mDpm.getResources().getString("SystemUi.QS_DIALOG_VIEW_POLICIES", this.mViewPoliciesButtonStringSupplier);
    }

    public final boolean isFinancedDevice() {
        if (DeviceConfig.getBoolean("device_policy_manager", "add-isfinanced-device", true)) {
            return ((SecurityControllerImpl) this.mSecurityController).mDevicePolicyManager.isFinancedDevice();
        }
        if (((SecurityControllerImpl) this.mSecurityController).isDeviceManaged()) {
            SecurityControllerImpl securityControllerImpl = (SecurityControllerImpl) this.mSecurityController;
            if (securityControllerImpl.mDevicePolicyManager.getDeviceOwnerType(securityControllerImpl.mDevicePolicyManager.getDeviceOwnerComponentOnAnyUser()) == 1) {
                return true;
            }
        }
        return false;
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
