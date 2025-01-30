package com.android.systemui.p016qs;

import com.android.systemui.R;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSSecurityFooter$$ExternalSyntheticLambda2 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSSecurityFooter f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ CharSequence f$2;

    public /* synthetic */ QSSecurityFooter$$ExternalSyntheticLambda2(QSSecurityFooter qSSecurityFooter, CharSequence charSequence, String str) {
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
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_named_management_named_vpn, this.f$2, this.f$1);
        }
    }

    public /* synthetic */ QSSecurityFooter$$ExternalSyntheticLambda2(QSSecurityFooter qSSecurityFooter, String str, String str2, int i) {
        this.$r8$classId = i;
        this.f$0 = qSSecurityFooter;
        this.f$1 = str;
        this.f$2 = str2;
    }
}
