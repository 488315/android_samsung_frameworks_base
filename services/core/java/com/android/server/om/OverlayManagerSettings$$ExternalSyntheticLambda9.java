package com.android.server.om;

import java.util.ArrayList;
import java.util.function.Function;

public final /* synthetic */ class OverlayManagerSettings$$ExternalSyntheticLambda9
        implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ OverlayManagerSettings$$ExternalSyntheticLambda9(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return OverlayManagerSettings.SettingsItem.m739$$Nest$mgetOverlayInfo(
                        (OverlayManagerSettings.SettingsItem) obj);
            default:
                return new ArrayList();
        }
    }
}
