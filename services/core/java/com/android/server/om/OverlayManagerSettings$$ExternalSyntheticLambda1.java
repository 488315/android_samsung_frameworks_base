package com.android.server.om;

import android.util.Pair;
import com.android.server.om.OverlayManagerSettings;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverlayManagerSettings$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Set f$0;

    public /* synthetic */ OverlayManagerSettings$$ExternalSyntheticLambda1(int i, Set set) {
        this.$r8$classId = i;
        this.f$0 = set;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Set set = this.f$0;
        OverlayManagerSettings.SettingsItem settingsItem = (OverlayManagerSettings.SettingsItem) obj;
        switch (i) {
            case 0:
                set.add(new Pair(settingsItem.mOverlay, settingsItem.mBaseCodePath));
                break;
            default:
                set.add(settingsItem.mBaseCodePath);
                break;
        }
    }
}
