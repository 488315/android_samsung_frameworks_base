package com.android.server.wm;

import android.content.ComponentName;
import com.android.internal.util.function.QuintConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class LaunchObserverRegistryImpl$$ExternalSyntheticLambda2 implements QuintConsumer {
    public final /* synthetic */ int $r8$classId;

    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        switch (this.$r8$classId) {
            case 0:
                LaunchObserverRegistryImpl launchObserverRegistryImpl = (LaunchObserverRegistryImpl) obj;
                long longValue = ((Long) obj2).longValue();
                ComponentName componentName = (ComponentName) obj3;
                long longValue2 = ((Long) obj4).longValue();
                int intValue = ((Integer) obj5).intValue();
                for (int i = 0; i < launchObserverRegistryImpl.mList.size(); i++) {
                    ((ActivityMetricsLaunchObserver) launchObserverRegistryImpl.mList.get(i)).onActivityLaunchFinished(longValue, componentName, longValue2, intValue);
                }
                break;
            default:
                LaunchObserverRegistryImpl launchObserverRegistryImpl2 = (LaunchObserverRegistryImpl) obj;
                long longValue3 = ((Long) obj2).longValue();
                ComponentName componentName2 = (ComponentName) obj3;
                int intValue2 = ((Integer) obj4).intValue();
                int intValue3 = ((Integer) obj5).intValue();
                for (int i2 = 0; i2 < launchObserverRegistryImpl2.mList.size(); i2++) {
                    ((ActivityMetricsLaunchObserver) launchObserverRegistryImpl2.mList.get(i2)).onActivityLaunched(intValue2, intValue3, longValue3, componentName2);
                }
                break;
        }
    }
}
