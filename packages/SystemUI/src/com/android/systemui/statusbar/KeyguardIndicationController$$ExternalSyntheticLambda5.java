package com.android.systemui.statusbar;

import android.content.res.Resources;
import com.android.systemui.R;
import java.util.function.Supplier;

public final /* synthetic */ class KeyguardIndicationController$$ExternalSyntheticLambda5 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardIndicationController$$ExternalSyntheticLambda5(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                KeyguardIndicationController keyguardIndicationController = (KeyguardIndicationController) obj;
                return Boolean.valueOf(keyguardIndicationController.mDevicePolicyManager.isDeviceManaged() || keyguardIndicationController.mDevicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile());
            default:
                return ((Resources) obj).getString(R.string.do_disclosure_generic);
        }
    }
}
