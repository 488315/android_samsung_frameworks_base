package com.android.server.om;

import com.android.server.om.OverlayManagerSettings;
import java.util.ArrayList;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverlayManagerSettings$$ExternalSyntheticLambda9 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ OverlayManagerSettings$$ExternalSyntheticLambda9(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return OverlayManagerSettings.SettingsItem.m739$$Nest$mgetOverlayInfo((OverlayManagerSettings.SettingsItem) obj);
            default:
                return new ArrayList();
        }
    }
}
