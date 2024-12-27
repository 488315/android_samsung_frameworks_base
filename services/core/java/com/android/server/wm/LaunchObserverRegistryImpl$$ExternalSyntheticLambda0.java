package com.android.server.wm;

import java.util.function.BiConsumer;

public final /* synthetic */ class LaunchObserverRegistryImpl$$ExternalSyntheticLambda0
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        LaunchObserverRegistryImpl launchObserverRegistryImpl = (LaunchObserverRegistryImpl) obj;
        switch (this.$r8$classId) {
            case 0:
                launchObserverRegistryImpl.mList.remove((ActivityMetricsLaunchObserver) obj2);
                break;
            case 1:
                launchObserverRegistryImpl.mList.add((ActivityMetricsLaunchObserver) obj2);
                break;
            case 2:
                long longValue = ((Long) obj2).longValue();
                for (int i = 0; i < launchObserverRegistryImpl.mList.size(); i++) {
                    ((ActivityMetricsLaunchObserver) launchObserverRegistryImpl.mList.get(i))
                            .onActivityLaunchCancelled(longValue);
                }
                break;
            default:
                long longValue2 = ((Long) obj2).longValue();
                for (int i2 = 0; i2 < launchObserverRegistryImpl.mList.size(); i2++) {
                    ((ActivityMetricsLaunchObserver) launchObserverRegistryImpl.mList.get(i2))
                            .onIntentFailed(longValue2);
                }
                break;
        }
    }
}
