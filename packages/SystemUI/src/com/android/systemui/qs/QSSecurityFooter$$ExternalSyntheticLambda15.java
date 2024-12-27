package com.android.systemui.qs;

import com.android.systemui.R;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSSecurityFooter$$ExternalSyntheticLambda15 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSSecurityFooter f$0;
    public final /* synthetic */ CharSequence f$1;

    public /* synthetic */ QSSecurityFooter$$ExternalSyntheticLambda15(QSSecurityFooter qSSecurityFooter, CharSequence charSequence, int i) {
        this.$r8$classId = i;
        this.f$0 = qSSecurityFooter;
        this.f$1 = charSequence;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.mContext.getString(R.string.monitoring_description_named_management, this.f$1);
            case 1:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_named_management_vpns, this.f$1);
            case 2:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_named_management_monitoring, this.f$1);
            case 3:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_named_managed_profile_monitoring, this.f$1);
            default:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_named_management, this.f$1);
        }
    }
}
