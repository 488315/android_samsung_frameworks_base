package com.android.systemui.qs.footer.domain.interactor;

import android.graphics.drawable.Drawable;
import android.os.UserManager;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.QSSecurityFooterUtils;
import com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda0;
import com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda1;
import com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda2;
import com.android.systemui.qs.footer.domain.model.SecurityButtonConfig;
import com.android.systemui.security.data.model.SecurityModel;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$securityButtonConfig$1$1", m277f = "FooterActionsInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class FooterActionsInteractorImpl$securityButtonConfig$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SecurityModel $security;
    int label;
    final /* synthetic */ FooterActionsInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FooterActionsInteractorImpl$securityButtonConfig$1$1(FooterActionsInteractorImpl footerActionsInteractorImpl, SecurityModel securityModel, Continuation<? super FooterActionsInteractorImpl$securityButtonConfig$1$1> continuation) {
        super(2, continuation);
        this.this$0 = footerActionsInteractorImpl;
        this.$security = securityModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FooterActionsInteractorImpl$securityButtonConfig$1$1(this.this$0, this.$security, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FooterActionsInteractorImpl$securityButtonConfig$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String string;
        Drawable drawable;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        QSSecurityFooterUtils qSSecurityFooterUtils = this.this$0.qsSecurityFooterUtils;
        SecurityModel securityModel = this.$security;
        qSSecurityFooterUtils.getClass();
        boolean z = securityModel.isDeviceManaged;
        boolean z2 = UserManager.isDeviceInDemoMode(qSSecurityFooterUtils.mContext) && ((UserTrackerImpl) qSSecurityFooterUtils.mUserTracker).getUserInfo().isDemo();
        boolean z3 = securityModel.hasWorkProfile;
        boolean z4 = securityModel.hasCACertInWorkProfile;
        boolean z5 = securityModel.isNetworkLoggingEnabled;
        String str = securityModel.workProfileVpnName;
        boolean z6 = z4 || str != null || (z3 && z5);
        boolean z7 = securityModel.hasCACertInCurrentUser;
        String str2 = securityModel.primaryVpnName;
        boolean z8 = securityModel.isProfileOwnerOfOrganizationOwnedDevice;
        boolean z9 = securityModel.isParentalControlsEnabled;
        boolean z10 = securityModel.isWorkProfileOn;
        if (!((z && !z2) || z7 || str2 != null || z8 || z9 || (z6 && z10))) {
            return null;
        }
        boolean z11 = !z8 || (z6 && z10);
        if (z9) {
            string = qSSecurityFooterUtils.mContext.getString(R.string.quick_settings_disclosure_parental_controls);
        } else if (z) {
            String str3 = securityModel.deviceOwnerOrganizationName;
            if (z7 || z4 || z5) {
                string = str3 == null ? qSSecurityFooterUtils.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT_MONITORING", qSSecurityFooterUtils.mManagementMonitoringStringSupplier) : qSSecurityFooterUtils.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_MONITORING", new QSSecurityFooterUtils$$ExternalSyntheticLambda0(qSSecurityFooterUtils, str3, 2), str3);
            } else if (str2 == null && str == null) {
                string = qSSecurityFooterUtils.getMangedDeviceGeneralText(str3);
            } else if (str2 == null || str == null) {
                String str4 = str2 != null ? str2 : str;
                string = str3 == null ? qSSecurityFooterUtils.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT_NAMED_VPN", new QSSecurityFooterUtils$$ExternalSyntheticLambda1(qSSecurityFooterUtils, str4, 0), str4) : qSSecurityFooterUtils.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_NAMED_VPN", new QSSecurityFooterUtils$$ExternalSyntheticLambda2(qSSecurityFooterUtils, str3, str4), str3, str4);
            } else {
                string = str3 == null ? qSSecurityFooterUtils.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT_MULTIPLE_VPNS", qSSecurityFooterUtils.mManagementMultipleVpnStringSupplier) : qSSecurityFooterUtils.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_MULTIPLE_VPNS", new QSSecurityFooterUtils$$ExternalSyntheticLambda0(qSSecurityFooterUtils, str3, 0), str3);
            }
        } else {
            String str5 = securityModel.workProfileOrganizationName;
            if (z7 || (z4 && z10)) {
                if (z4 && z10) {
                    string = str5 == null ? qSSecurityFooterUtils.mDpm.getResources().getString("SystemUi.QS_MSG_WORK_PROFILE_MONITORING", qSSecurityFooterUtils.mWorkProfileMonitoringStringSupplier) : qSSecurityFooterUtils.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_WORK_PROFILE_MONITORING", new QSSecurityFooterUtils$$ExternalSyntheticLambda0(qSSecurityFooterUtils, str5, 1), str5);
                } else {
                    if (z7) {
                        string = qSSecurityFooterUtils.mContext.getString(R.string.quick_settings_disclosure_monitoring);
                    }
                    string = null;
                }
            } else if (str2 != null || (str != null && z10)) {
                if (str2 != null && str != null) {
                    string = qSSecurityFooterUtils.mContext.getString(R.string.quick_settings_disclosure_vpns);
                } else if (str == null || !z10) {
                    if (str2 != null) {
                        string = z3 ? qSSecurityFooterUtils.mDpm.getResources().getString("SystemUi.QS_MSG_PERSONAL_PROFILE_NAMED_VPN", new QSSecurityFooterUtils$$ExternalSyntheticLambda1(qSSecurityFooterUtils, str2, 2), str2) : qSSecurityFooterUtils.mContext.getString(R.string.quick_settings_disclosure_named_vpn, str2);
                    }
                    string = null;
                } else {
                    string = qSSecurityFooterUtils.mDpm.getResources().getString("SystemUi.QS_MSG_WORK_PROFILE_NAMED_VPN", new QSSecurityFooterUtils$$ExternalSyntheticLambda1(qSSecurityFooterUtils, str, 1), str);
                }
            } else if (z3 && z5 && z10) {
                string = qSSecurityFooterUtils.mDpm.getResources().getString("SystemUi.QS_MSG_WORK_PROFILE_NETWORK", qSSecurityFooterUtils.mWorkProfileNetworkStringSupplier);
            } else {
                if (z8) {
                    string = qSSecurityFooterUtils.getMangedDeviceGeneralText(str5);
                }
                string = null;
            }
        }
        String str6 = string.toString();
        Icon resource = (!z9 || (drawable = securityModel.deviceAdminIcon) == null) ? (str2 == null && str == null) ? new Icon.Resource(R.drawable.ic_info_outline, null) : securityModel.isVpnBranded ? new Icon.Resource(R.drawable.stat_sys_branded_vpn, null) : new Icon.Resource(R.drawable.stat_sys_vpn_ic, null) : new Icon.Loaded(drawable, null);
        if (((SecurityControllerImpl) qSSecurityFooterUtils.mSecurityController).isSecureWifiEnabled()) {
            resource = new Icon.Resource(R.drawable.stat_sys_securewifi_ic, null);
        }
        return new SecurityButtonConfig(resource, str6, z11);
    }
}
