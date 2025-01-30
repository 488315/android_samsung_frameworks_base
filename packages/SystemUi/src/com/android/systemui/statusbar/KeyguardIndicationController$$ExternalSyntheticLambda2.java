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
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.Iterator;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardIndicationController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ KeyguardIndicationController f$0;

    @Override // java.lang.Runnable
    public final void run() {
        final CharSequence charSequence;
        int i;
        final KeyguardIndicationController keyguardIndicationController = this.f$0;
        DevicePolicyManager devicePolicyManager = keyguardIndicationController.mDevicePolicyManager;
        if (devicePolicyManager.isDeviceManaged()) {
            charSequence = devicePolicyManager.getDeviceOwnerOrganizationName();
        } else {
            if (devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()) {
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
                if (i != -10000) {
                    charSequence = devicePolicyManager.getOrganizationNameForUser(i);
                }
            }
            charSequence = null;
        }
        final Resources resources = keyguardIndicationController.mContext.getResources();
        final String string = charSequence == null ? devicePolicyManager.getResources().getString("SystemUi.KEYGUARD_MANAGEMENT_DISCLOSURE", new KeyguardIndicationController$$ExternalSyntheticLambda1(resources, 1)) : DeviceConfig.getBoolean("device_policy_manager", "add-isfinanced-device", true) ? devicePolicyManager.isFinancedDevice() : devicePolicyManager.isDeviceManaged() && devicePolicyManager.getDeviceOwnerType(devicePolicyManager.getDeviceOwnerComponentOnAnyUser()) == 1 ? resources.getString(R.string.do_financed_disclosure_with_name, charSequence) : devicePolicyManager.getResources().getString("SystemUi.KEYGUARD_NAMED_MANAGEMENT_DISCLOSURE", new Supplier() { // from class: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return resources.getString(R.string.do_disclosure_with_name, charSequence);
            }
        }, charSequence);
        ((ExecutorImpl) keyguardIndicationController.mExecutor).execute(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardIndicationController keyguardIndicationController2 = KeyguardIndicationController.this;
                CharSequence charSequence2 = string;
                if (((KeyguardStateControllerImpl) keyguardIndicationController2.mKeyguardStateController).mShowing) {
                    KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = keyguardIndicationController2.mRotateTextViewController;
                    KeyguardIndication.Builder builder = new KeyguardIndication.Builder();
                    builder.mMessage = charSequence2;
                    builder.mTextColor = keyguardIndicationController2.mInitialTextColorState;
                    keyguardIndicationRotateTextViewController.updateIndication(1, builder.build(), false);
                }
            }
        });
    }
}
