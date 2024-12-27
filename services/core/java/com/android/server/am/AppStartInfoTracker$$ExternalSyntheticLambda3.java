package com.android.server.am;

import android.os.UserHandle;
import android.util.SparseArray;

import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppStartInfoTracker$$ExternalSyntheticLambda3
        implements BiFunction {
    public final /* synthetic */ int f$0;

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        int i = this.f$0;
        SparseArray sparseArray = (SparseArray) obj2;
        int size = sparseArray.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            if (UserHandle.getUserId(sparseArray.keyAt(size)) == i) {
                sparseArray.removeAt(size);
                break;
            }
            size--;
        }
        return Integer.valueOf(sparseArray.size() != 0 ? 0 : 1);
    }
}
