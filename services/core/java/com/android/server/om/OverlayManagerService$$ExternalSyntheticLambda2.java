package com.android.server.om;

import android.content.pm.UserPackage;
import android.util.ArraySet;
import android.util.SparseArray;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverlayManagerService$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingConsumer {
    public final /* synthetic */ SparseArray f$0;

    public final void acceptOrThrow(Object obj) {
        SparseArray sparseArray = this.f$0;
        UserPackage userPackage = (UserPackage) obj;
        ArraySet arraySet = (ArraySet) sparseArray.get(userPackage.userId);
        if (arraySet == null) {
            arraySet = new ArraySet();
            sparseArray.put(userPackage.userId, arraySet);
        }
        arraySet.add(userPackage.packageName);
    }
}
