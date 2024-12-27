package com.android.systemui.qs;

import com.android.systemui.R;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class QSSecurityFooterUtils$$ExternalSyntheticLambda2 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSSecurityFooterUtils f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ QSSecurityFooterUtils$$ExternalSyntheticLambda2(QSSecurityFooterUtils qSSecurityFooterUtils, CharSequence charSequence, String str) {
        this.$r8$classId = 0;
        this.f$0 = qSSecurityFooterUtils;
        this.f$1 = charSequence;
        this.f$2 = str;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_named_management_named_vpn, (CharSequence) this.f$1, this.f$2);
            case 1:
                return this.f$0.mContext.getString(R.string.monitoring_description_two_named_vpns, this.f$2, (String) this.f$1);
            default:
                return this.f$0.mContext.getString(R.string.monitoring_description_two_named_vpns, this.f$2, (String) this.f$1);
        }
    }

    public /* synthetic */ QSSecurityFooterUtils$$ExternalSyntheticLambda2(QSSecurityFooterUtils qSSecurityFooterUtils, String str, String str2, int i) {
        this.$r8$classId = i;
        this.f$0 = qSSecurityFooterUtils;
        this.f$2 = str;
        this.f$1 = str2;
    }
}
