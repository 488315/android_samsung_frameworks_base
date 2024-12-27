package com.android.server.pm;

import android.os.Bundle;
import android.util.IntArray;
import android.util.SparseArray;
import com.android.server.BundleUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RestrictionsSet {
    public final SparseArray mUserRestrictions = new SparseArray(0);

    public final Bundle getRestrictions(int i) {
        return (Bundle) this.mUserRestrictions.get(i);
    }

    public final IntArray getUserIds() {
        IntArray intArray = new IntArray(this.mUserRestrictions.size());
        for (int i = 0; i < this.mUserRestrictions.size(); i++) {
            intArray.add(this.mUserRestrictions.keyAt(i));
        }
        return intArray;
    }

    public int keyAt(int i) {
        return this.mUserRestrictions.keyAt(i);
    }

    public int size() {
        return this.mUserRestrictions.size();
    }

    public final boolean updateRestrictions(int i, Bundle bundle) {
        if (!(!UserRestrictionsUtils.areEqual((Bundle) this.mUserRestrictions.get(i), bundle))) {
            return false;
        }
        if (BundleUtils.isEmpty(bundle)) {
            this.mUserRestrictions.delete(i);
        } else {
            this.mUserRestrictions.put(i, bundle);
        }
        return true;
    }

    public Bundle valueAt(int i) {
        return (Bundle) this.mUserRestrictions.valueAt(i);
    }
}
