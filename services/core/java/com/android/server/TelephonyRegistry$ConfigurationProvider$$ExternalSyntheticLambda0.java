package com.android.server;

import android.app.compat.CompatChanges;
import android.os.UserHandle;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ UserHandle f$1;

    public /* synthetic */ TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda0(
            int i, UserHandle userHandle, String str) {
        this.$r8$classId = i;
        this.f$0 = str;
        this.f$1 = userHandle;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(
                        CompatChanges.isChangeEnabled(182478738L, this.f$0, this.f$1));
            case 1:
                return Boolean.valueOf(
                        CompatChanges.isChangeEnabled(181658987L, this.f$0, this.f$1));
            case 2:
                return Boolean.valueOf(
                        CompatChanges.isChangeEnabled(157233955L, this.f$0, this.f$1));
            case 3:
                return Boolean.valueOf(
                        CompatChanges.isChangeEnabled(184323934L, this.f$0, this.f$1));
            default:
                return Boolean.valueOf(
                        CompatChanges.isChangeEnabled(183164979L, this.f$0, this.f$1));
        }
    }
}
