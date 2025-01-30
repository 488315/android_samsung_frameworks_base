package com.samsung.android.wifitrackerlib;

import android.net.wifi.hotspot2.PasspointConfiguration;
import com.android.wifitrackerlib.PasspointWifiEntry;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final /* synthetic */ class SavedScannedTracker$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Map f$0;

    public /* synthetic */ SavedScannedTracker$$ExternalSyntheticLambda2(int i, Map map) {
        this.$r8$classId = i;
        this.f$0 = map;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Map map = this.f$0;
                PasspointWifiEntry passpointWifiEntry = (PasspointWifiEntry) ((Map.Entry) obj).getValue();
                PasspointConfiguration passpointConfiguration = (PasspointConfiguration) map.remove(passpointWifiEntry.mKey);
                if (passpointConfiguration == null) {
                    return true;
                }
                passpointWifiEntry.updatePasspointConfig(passpointConfiguration);
                return false;
            default:
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                standardWifiEntry.updateConfig((List) this.f$0.remove(standardWifiEntry.mKey));
                return !standardWifiEntry.isSaved();
        }
    }
}
