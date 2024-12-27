package com.android.server.os;

import android.app.ApplicationExitInfo;
import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NativeTombstoneManager$$ExternalSyntheticLambda3 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        long timestamp = ((ApplicationExitInfo) obj2).getTimestamp() - ((ApplicationExitInfo) obj).getTimestamp();
        if (timestamp < 0) {
            return -1;
        }
        return timestamp == 0 ? 0 : 1;
    }
}
