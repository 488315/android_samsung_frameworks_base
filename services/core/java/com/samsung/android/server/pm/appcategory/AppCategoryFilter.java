package com.samsung.android.server.pm.appcategory;

import android.util.ArrayMap;
import java.util.ArrayList;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCategoryFilter {
    public AnonymousClass1 mAppCategoryCallback;
    public boolean mEnabled;
    public Object mLock;
    public AppCategoryListParser mParser;

    /* renamed from: -$$Nest$mmakeChangedAppList, reason: not valid java name */
    public static ArrayList m1231$$Nest$mmakeChangedAppList(AppCategoryFilter appCategoryFilter, final AppCategoryListParserWithScpm appCategoryListParserWithScpm, AppCategoryListParserWithScpm appCategoryListParserWithScpm2) {
        final ArrayList arrayList = new ArrayList();
        ((ArrayMap) appCategoryListParserWithScpm2.mPackageMap).forEach(new BiConsumer() { // from class: com.samsung.android.server.pm.appcategory.AppCategoryFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AppCategoryListParser appCategoryListParser = appCategoryListParserWithScpm;
                ArrayList arrayList2 = arrayList;
                String str = (String) obj;
                Integer num = (Integer) obj2;
                Integer num2 = (Integer) ((ArrayMap) appCategoryListParser.mPackageMap).get(str);
                if ((num2 != null ? num2.intValue() : -1) != num.intValue()) {
                    arrayList2.add(str);
                }
            }
        });
        ((ArrayMap) appCategoryListParserWithScpm.mPackageMap).keySet().removeAll(((ArrayMap) appCategoryListParserWithScpm2.mPackageMap).keySet());
        arrayList.addAll(((ArrayMap) appCategoryListParserWithScpm.mPackageMap).keySet());
        return arrayList;
    }

    public void clearItemsLocked() {
        ((ArrayMap) this.mParser.mPackageMap).clear();
    }

    public final int getPackageCategory(String str) {
        int intValue;
        synchronized (this.mLock) {
            Integer num = (Integer) ((ArrayMap) this.mParser.mPackageMap).get(str);
            intValue = num != null ? num.intValue() : -1;
        }
        return intValue;
    }

    public void loadItemsInternalLocked(String str) {
        if (str == null) {
            this.mParser.parseAppCategoryList();
        } else {
            this.mParser.parseAppCategoryList(str);
        }
    }
}
