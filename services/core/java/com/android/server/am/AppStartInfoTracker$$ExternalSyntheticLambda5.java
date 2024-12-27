package com.android.server.am;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppStartInfoTracker$$ExternalSyntheticLambda5
        implements BiFunction {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        SparseArray sparseArray = (SparseArray) obj2;
        for (int i = 0; i < sparseArray.size(); i++) {
            AppStartInfoTracker.AppStartInfoContainer appStartInfoContainer =
                    (AppStartInfoTracker.AppStartInfoContainer) sparseArray.valueAt(i);
            appStartInfoContainer.mMonitoringModeEnabled = false;
            if (appStartInfoContainer.mInfos.size()
                    > (appStartInfoContainer.mMonitoringModeEnabled
                            ? 100
                            : appStartInfoContainer.mMaxCapacity)) {
                Collections.sort(
                        appStartInfoContainer.mInfos,
                        new AppStartInfoTracker$$ExternalSyntheticLambda1(1));
                ArrayList arrayList = appStartInfoContainer.mInfos;
                arrayList
                        .subList(
                                0,
                                arrayList.size()
                                        - (appStartInfoContainer.mMonitoringModeEnabled
                                                ? 100
                                                : appStartInfoContainer.mMaxCapacity))
                        .clear();
                appStartInfoContainer.mInfos.trimToSize();
            }
        }
        return 0;
    }
}
