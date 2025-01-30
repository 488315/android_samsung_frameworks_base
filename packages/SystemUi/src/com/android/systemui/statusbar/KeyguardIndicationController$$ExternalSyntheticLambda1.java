package com.android.systemui.statusbar;

import android.app.admin.DevicePolicyManager;
import android.content.res.Resources;
import com.android.systemui.R;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardIndicationController$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardIndicationController$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManager devicePolicyManager = ((KeyguardIndicationController) this.f$0).mDevicePolicyManager;
                return Boolean.valueOf(devicePolicyManager.isDeviceManaged() || devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile());
            default:
                return ((Resources) this.f$0).getString(R.string.do_disclosure_generic);
        }
    }
}
