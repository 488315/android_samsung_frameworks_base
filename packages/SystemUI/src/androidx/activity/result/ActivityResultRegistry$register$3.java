package androidx.activity.result;

import android.util.Log;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.lifecycle.LifecycleEventObserver;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ActivityResultRegistry$register$3 extends ActivityResultLauncher {
    public final /* synthetic */ String $key;
    public final /* synthetic */ ActivityResultRegistry this$0;

    public ActivityResultRegistry$register$3(ActivityResultRegistry activityResultRegistry, String str, ActivityResultContract activityResultContract) {
        this.this$0 = activityResultRegistry;
        this.$key = str;
    }

    public final void unregister() {
        Integer num;
        ActivityResultRegistry activityResultRegistry = this.this$0;
        ArrayList arrayList = (ArrayList) activityResultRegistry.launchedKeys;
        String str = this.$key;
        if (!arrayList.contains(str) && (num = (Integer) activityResultRegistry.keyToRc.remove(str)) != null) {
            activityResultRegistry.rcToKey.remove(num);
        }
        activityResultRegistry.keyToCallback.remove(str);
        if (activityResultRegistry.parsedPendingResults.containsKey(str)) {
            StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Dropping pending result for request ", str, ": ");
            m.append(((LinkedHashMap) activityResultRegistry.parsedPendingResults).get(str));
            Log.w("ActivityResultRegistry", m.toString());
            activityResultRegistry.parsedPendingResults.remove(str);
        }
        if (activityResultRegistry.pendingResults.containsKey(str)) {
            Log.w("ActivityResultRegistry", "Dropping pending result for request " + str + ": " + ((ActivityResult) activityResultRegistry.pendingResults.getParcelable(str, ActivityResult.class)));
            activityResultRegistry.pendingResults.remove(str);
        }
        ActivityResultRegistry.LifecycleContainer lifecycleContainer = (ActivityResultRegistry.LifecycleContainer) ((LinkedHashMap) activityResultRegistry.keyToLifecycleContainers).get(str);
        if (lifecycleContainer != null) {
            ArrayList arrayList2 = (ArrayList) lifecycleContainer.observers;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                lifecycleContainer.lifecycle.removeObserver((LifecycleEventObserver) obj);
            }
            ((ArrayList) lifecycleContainer.observers).clear();
            activityResultRegistry.keyToLifecycleContainers.remove(str);
        }
    }
}
