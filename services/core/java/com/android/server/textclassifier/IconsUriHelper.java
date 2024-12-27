package com.android.server.textclassifier;

import android.util.ArrayMap;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class IconsUriHelper {
    public static final IconsUriHelper$$ExternalSyntheticLambda0 DEFAULT_ID_SUPPLIER =
            new IconsUriHelper$$ExternalSyntheticLambda0();
    public static final IconsUriHelper sSingleton = new IconsUriHelper();
    public final Supplier mIdSupplier;
    public final Map mPackageIds;

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
