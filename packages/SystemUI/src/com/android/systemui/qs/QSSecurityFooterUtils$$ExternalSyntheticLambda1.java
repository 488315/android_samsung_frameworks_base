package com.android.systemui.qs;

import com.android.systemui.R;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSSecurityFooterUtils$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSSecurityFooterUtils f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ QSSecurityFooterUtils$$ExternalSyntheticLambda1(QSSecurityFooterUtils qSSecurityFooterUtils, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = qSSecurityFooterUtils;
        this.f$1 = str;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_management_named_vpn, this.f$1);
            case 1:
                return this.f$0.mContext.getString(R.string.monitoring_description_managed_device_named_vpn, this.f$1);
            case 2:
                return this.f$0.mContext.getString(R.string.monitoring_description_managed_profile_named_vpn, this.f$1);
            case 3:
                return this.f$0.mContext.getString(R.string.monitoring_description_personal_profile_named_vpn, this.f$1);
            case 4:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_managed_profile_named_vpn, this.f$1);
            default:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_personal_profile_named_vpn, this.f$1);
        }
    }
}
