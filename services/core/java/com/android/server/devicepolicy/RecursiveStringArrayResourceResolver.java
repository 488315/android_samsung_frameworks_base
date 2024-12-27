package com.android.server.devicepolicy;

import android.content.res.Resources;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RecursiveStringArrayResourceResolver {
    public final Resources mResources;

    public RecursiveStringArrayResourceResolver(Resources resources) {
        this.mResources = resources;
    }

    public final Set resolve(int i, String str, Collection collection) {
        String[] stringArray = this.mResources.getStringArray(i);
        ArrayList arrayList = new ArrayList(collection);
        HashSet hashSet = new HashSet();
        for (String str2 : stringArray) {
            String substring = str2.startsWith("#import:") ? str2.substring(8) : null;
            if (substring == null) {
                hashSet.add(str2);
            } else if (arrayList.contains(substring)) {
                continue;
            } else {
                String[] split = substring.split("/", 2);
                String str3 = split[0];
                String str4 = split[1];
                if (Objects.equals(str3, ".")) {
                    str3 = str;
                }
                int identifier = this.mResources.getIdentifier(str4, "array", str3);
                if (identifier == 0) {
                    throw new Resources.NotFoundException(
                            AnyMotionDetector$$ExternalSyntheticOutline0.m(str3, ":array/", str4));
                }
                Set resolve = resolve(identifier, str3, arrayList);
                arrayList.addAll(resolve);
                hashSet.addAll(resolve);
            }
        }
        return hashSet;
    }
}
