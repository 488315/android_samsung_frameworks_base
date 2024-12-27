package com.android.server.devicepolicy;

import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import com.android.internal.util.FunctionalUtils;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda173 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId = 2;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda173(DevicePolicyManagerService devicePolicyManagerService, int i, List list, Predicate predicate) {
        this.f$0 = devicePolicyManagerService;
        this.f$2 = i;
        this.f$1 = list;
        this.f$3 = predicate;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda173(DevicePolicyManagerService devicePolicyManagerService, String str, int i, String str2) {
        this.f$0 = devicePolicyManagerService;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = str2;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda173(DevicePolicyManagerService devicePolicyManagerService, String str, String str2, int i) {
        this.f$0 = devicePolicyManagerService;
        this.f$1 = str;
        this.f$3 = str2;
        this.f$2 = i;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                String str = (String) this.f$1;
                int i = this.f$2;
                String str2 = (String) this.f$3;
                devicePolicyManagerService.getClass();
                if ("default_input_method".equals(str)) {
                    if (!TextUtils.equals(Settings.Secure.getStringForUser(devicePolicyManagerService.mInjector.mContext.getContentResolver(), "default_input_method", i), str2)) {
                        ((ArraySet) devicePolicyManagerService.mSetupContentObserver.mUserIdsWithPendingChangesByOwner).add(Integer.valueOf(i));
                    }
                    devicePolicyManagerService.getUserData(i).mCurrentInputMethodSet = true;
                    devicePolicyManagerService.saveSettingsLocked(i, false, false, false);
                }
                Settings.Secure.putStringForUser(devicePolicyManagerService.mInjector.mContext.getContentResolver(), str, str2, i);
                if (str.equals("location_mode") && Integer.parseInt(str2) != 0) {
                    devicePolicyManagerService.showLocationSettingsEnabledNotification(UserHandle.of(i));
                    break;
                }
                break;
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                Settings.System.putStringForUser(devicePolicyManagerService2.mInjector.mContext.getContentResolver(), (String) this.f$1, (String) this.f$3, this.f$2);
                break;
            default:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                int i2 = this.f$2;
                List list = (List) this.f$1;
                Predicate predicate = (Predicate) this.f$3;
                for (UserInfo userInfo : devicePolicyManagerService3.mUserManager.getProfiles(i2)) {
                    int i3 = userInfo.id;
                    if (i3 == i2) {
                        list.add(Integer.valueOf(i3));
                    } else if (userInfo.isManagedProfile() && predicate.test(userInfo)) {
                        list.add(Integer.valueOf(userInfo.id));
                    }
                }
                break;
        }
    }
}
