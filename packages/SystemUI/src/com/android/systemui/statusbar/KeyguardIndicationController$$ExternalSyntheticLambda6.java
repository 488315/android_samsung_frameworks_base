package com.android.systemui.statusbar;

import android.app.admin.DevicePolicyManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import com.android.systemui.R;
import com.android.systemui.keyguard.KeyguardIndication;
import com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.Iterator;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardIndicationController$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ KeyguardIndicationController f$0;

    /* JADX WARN: Removed duplicated region for block: B:15:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0077  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        final CharSequence organizationNameForUser;
        int i;
        boolean zIsFinancedDevice;
        final KeyguardIndicationController keyguardIndicationController = this.f$0;
        if (keyguardIndicationController.mDevicePolicyManager.isDeviceManaged()) {
            organizationNameForUser = keyguardIndicationController.mDevicePolicyManager.getDeviceOwnerOrganizationName();
        } else if (keyguardIndicationController.mDevicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()) {
            Iterator it = keyguardIndicationController.mUserManager.getProfiles(UserHandle.myUserId()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    i = -10000;
                    break;
                }
                UserInfo userInfo = (UserInfo) it.next();
                if (userInfo.isManagedProfile()) {
                    i = userInfo.id;
                    break;
                }
            }
            organizationNameForUser = i == -10000 ? null : keyguardIndicationController.mDevicePolicyManager.getOrganizationNameForUser(i);
        }
        final Resources resources = keyguardIndicationController.mContext.getResources();
        if (DeviceConfig.getBoolean("device_policy_manager", "add-isfinanced-device", true)) {
            zIsFinancedDevice = keyguardIndicationController.mDevicePolicyManager.isFinancedDevice();
        } else if (keyguardIndicationController.mDevicePolicyManager.isDeviceManaged()) {
            DevicePolicyManager devicePolicyManager = keyguardIndicationController.mDevicePolicyManager;
            boolean z = devicePolicyManager.getDeviceOwnerType(devicePolicyManager.getDeviceOwnerComponentOnAnyUser()) == 1;
            zIsFinancedDevice = z;
        }
        final String string = organizationNameForUser == null ? keyguardIndicationController.mDevicePolicyManager.getResources().getString("SystemUi.KEYGUARD_MANAGEMENT_DISCLOSURE", new KeyguardIndicationController$$ExternalSyntheticLambda5(resources, 1)) : zIsFinancedDevice ? resources.getString(R.string.do_financed_disclosure_with_name, organizationNameForUser) : keyguardIndicationController.mDevicePolicyManager.getResources().getString("SystemUi.KEYGUARD_NAMED_MANAGEMENT_DISCLOSURE", new Supplier() { // from class: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return resources.getString(R.string.do_disclosure_with_name, organizationNameForUser);
            }
        }, organizationNameForUser);
        keyguardIndicationController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardIndicationController keyguardIndicationController2 = keyguardIndicationController;
                CharSequence charSequence = string;
                if (((KeyguardStateControllerImpl) keyguardIndicationController2.mKeyguardStateController).mShowing) {
                    KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = keyguardIndicationController2.mRotateTextViewController;
                    KeyguardIndication.Builder builder = new KeyguardIndication.Builder();
                    builder.mMessage = charSequence;
                    builder.mTextColor = keyguardIndicationController2.mInitialTextColorState;
                    keyguardIndicationRotateTextViewController.updateIndication(1, builder.build(), false);
                }
            }
        });
    }
}
