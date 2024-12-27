package com.android.systemui.qs;

import com.android.systemui.R;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSSecurityFooter$$ExternalSyntheticLambda16 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSSecurityFooter f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ QSSecurityFooter$$ExternalSyntheticLambda16(QSSecurityFooter qSSecurityFooter, CharSequence charSequence, String str) {
        this.$r8$classId = 2;
        this.f$0 = qSSecurityFooter;
        this.f$2 = charSequence;
        this.f$1 = str;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.mContext.getString(R.string.monitoring_description_two_named_vpns, this.f$1, (String) this.f$2);
            case 1:
                return this.f$0.mContext.getString(R.string.monitoring_description_two_named_vpns, this.f$1, (String) this.f$2);
            default:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_named_management_named_vpn, (CharSequence) this.f$2, this.f$1);
        }
    }

    public /* synthetic */ QSSecurityFooter$$ExternalSyntheticLambda16(QSSecurityFooter qSSecurityFooter, String str, String str2, int i) {
        this.$r8$classId = i;
        this.f$0 = qSSecurityFooter;
        this.f$1 = str;
        this.f$2 = str2;
    }
}
