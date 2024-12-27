package com.android.server.om;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class OverlayManagerSettings$$ExternalSyntheticLambda4
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ OverlayManagerSettings$$ExternalSyntheticLambda4(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((OverlayManagerSettings.SettingsItem) obj).mUserId
                        == ((DumpState) obj2).mUserId;
            case 1:
                return ((OverlayManagerSettings.SettingsItem) obj)
                        .mOverlay
                        .getPackageName()
                        .equals(((DumpState) obj2).mPackageName);
            case 2:
                return ((OverlayManagerSettings.SettingsItem) obj)
                        .mOverlay
                        .getOverlayName()
                        .equals(((DumpState) obj2).mOverlayName);
            default:
                return !((OverlayManagerSettings.SettingsItem) obj)
                        .mTargetPackageName.equals((String) obj2);
        }
    }
}
