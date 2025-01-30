package com.android.systemui.p016qs;

import com.android.systemui.R;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSSecurityFooter$$ExternalSyntheticLambda3 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSSecurityFooter f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ QSSecurityFooter$$ExternalSyntheticLambda3(QSSecurityFooter qSSecurityFooter, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = qSSecurityFooter;
        this.f$1 = str;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.mContext.getString(R.string.monitoring_description_named_vpn, this.f$1);
            case 1:
                return this.f$0.mContext.getString(R.string.monitoring_description_managed_profile_named_vpn, this.f$1);
            case 2:
                return this.f$0.mContext.getString(R.string.monitoring_description_personal_profile_named_vpn, this.f$1);
            case 3:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_management_named_vpn, this.f$1);
            case 4:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_managed_profile_named_vpn, this.f$1);
            default:
                return this.f$0.mContext.getString(R.string.quick_settings_disclosure_personal_profile_named_vpn, this.f$1);
        }
    }
}
