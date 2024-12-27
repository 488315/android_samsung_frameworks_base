package com.android.server.textclassifier;

import android.util.ArrayMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class IconsUriHelper {
    public static final IconsUriHelper$$ExternalSyntheticLambda0 DEFAULT_ID_SUPPLIER = new IconsUriHelper$$ExternalSyntheticLambda0();
    public static final IconsUriHelper sSingleton = new IconsUriHelper();
    public final Supplier mIdSupplier;
    public final Map mPackageIds;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ResourceInfo {
        public final int id;
        public final String packageName;

        public ResourceInfo(String str, int i) {
            Objects.requireNonNull(str);
            this.packageName = str;
            this.id = i;
        }
    }

    public IconsUriHelper() {
        ArrayMap arrayMap = new ArrayMap();
        this.mPackageIds = arrayMap;
        this.mIdSupplier = DEFAULT_ID_SUPPLIER;
        arrayMap.put("android", "android");
    }
}
