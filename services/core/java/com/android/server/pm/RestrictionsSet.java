package com.android.server.pm;

import android.os.Bundle;
import android.util.IntArray;
import android.util.SparseArray;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.BundleUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class RestrictionsSet {
    public final SparseArray mUserRestrictions = new SparseArray(0);

    public boolean updateRestrictions(int i, Bundle bundle) {
        if (!(!UserRestrictionsUtils.areEqual((Bundle) this.mUserRestrictions.get(i), bundle))) {
            return false;
        }
        if (!BundleUtils.isEmpty(bundle)) {
            this.mUserRestrictions.put(i, bundle);
        } else {
            this.mUserRestrictions.delete(i);
        }
        return true;
    }

    public boolean removeRestrictionsForAllUsers(String str) {
        boolean z = false;
        for (int i = 0; i < this.mUserRestrictions.size(); i++) {
            Bundle bundle = (Bundle) this.mUserRestrictions.valueAt(i);
            if (UserRestrictionsUtils.contains(bundle, str)) {
                bundle.remove(str);
                z = true;
            }
        }
        return z;
    }

    public Bundle mergeAll() {
        Bundle bundle = new Bundle();
        for (int i = 0; i < this.mUserRestrictions.size(); i++) {
            UserRestrictionsUtils.merge(bundle, (Bundle) this.mUserRestrictions.valueAt(i));
        }
        return bundle;
    }

    public Bundle getRestrictions(int i) {
        return (Bundle) this.mUserRestrictions.get(i);
    }

    public Bundle getRestrictionsNonNull(int i) {
        return UserRestrictionsUtils.nonNull((Bundle) this.mUserRestrictions.get(i));
    }

    public boolean remove(int i) {
        boolean contains = this.mUserRestrictions.contains(i);
        this.mUserRestrictions.remove(i);
        return contains;
    }

    public void removeAllRestrictions() {
        this.mUserRestrictions.clear();
    }

    public static RestrictionsSet readRestrictions(TypedXmlPullParser typedXmlPullParser, String str) {
        RestrictionsSet restrictionsSet = new RestrictionsSet();
        int i = 0;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                String name = typedXmlPullParser.getName();
                if (next == 3 && str.equals(name)) {
                    return restrictionsSet;
                }
                if (next == 2 && "restrictions_user".equals(name)) {
                    i = typedXmlPullParser.getAttributeInt((String) null, "user_id");
                } else if (next == 2 && "restrictions".equals(name)) {
                    restrictionsSet.updateRestrictions(i, UserRestrictionsUtils.readRestrictions(typedXmlPullParser));
                }
            } else {
                throw new XmlPullParserException("restrictions cannot be read as xml is malformed.");
            }
        }
    }

    public IntArray getUserIds() {
        IntArray intArray = new IntArray(this.mUserRestrictions.size());
        for (int i = 0; i < this.mUserRestrictions.size(); i++) {
            intArray.add(this.mUserRestrictions.keyAt(i));
        }
        return intArray;
    }

    public int size() {
        return this.mUserRestrictions.size();
    }

    public int keyAt(int i) {
        return this.mUserRestrictions.keyAt(i);
    }

    public Bundle valueAt(int i) {
        return (Bundle) this.mUserRestrictions.valueAt(i);
    }
}
