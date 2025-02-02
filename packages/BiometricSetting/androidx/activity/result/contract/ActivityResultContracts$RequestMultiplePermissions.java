package androidx.activity.result.contract;

import android.content.Intent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt;

/* compiled from: ActivityResultContracts.kt */
/* loaded from: classes.dex */
public final class ActivityResultContracts$RequestMultiplePermissions extends ActivityResultContract<String[], Map<String, Boolean>> {
    @Override // androidx.activity.result.contract.ActivityResultContract
    public final Object parseResult(Intent intent, int i) {
        if (i != -1) {
            return MapsKt.emptyMap();
        }
        if (intent == null) {
            return MapsKt.emptyMap();
        }
        String[] stringArrayExtra = intent.getStringArrayExtra("androidx.activity.result.contract.extra.PERMISSIONS");
        int[] intArrayExtra = intent.getIntArrayExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS");
        if (intArrayExtra == null || stringArrayExtra == null) {
            return MapsKt.emptyMap();
        }
        ArrayList arrayList = new ArrayList(intArrayExtra.length);
        for (int i2 : intArrayExtra) {
            arrayList.add(Boolean.valueOf(i2 == 0));
        }
        ArrayList arrayList2 = new ArrayList();
        for (String str : stringArrayExtra) {
            if (str != null) {
                arrayList2.add(str);
            }
        }
        Iterator it = arrayList2.iterator();
        Iterator it2 = arrayList.iterator();
        ArrayList arrayList3 = new ArrayList(Math.min(arrayList2.size(), arrayList.size()));
        while (it.hasNext() && it2.hasNext()) {
            arrayList3.add(new Pair(it.next(), it2.next()));
        }
        return MapsKt.toMap(arrayList3);
    }
}
