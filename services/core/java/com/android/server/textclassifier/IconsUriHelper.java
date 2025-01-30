package com.android.server.textclassifier;

import android.net.Uri;
import android.util.ArrayMap;
import android.util.Log;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final class IconsUriHelper {
  public static final Supplier DEFAULT_ID_SUPPLIER =
      new Supplier() { // from class:
                       // com.android.server.textclassifier.IconsUriHelper$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
          String lambda$static$0;
          lambda$static$0 = IconsUriHelper.lambda$static$0();
          return lambda$static$0;
        }
      };
  public static final IconsUriHelper sSingleton = new IconsUriHelper(null);
  public final Supplier mIdSupplier;
  public final Map mPackageIds;

  public static /* synthetic */ String lambda$static$0() {
    return UUID.randomUUID().toString();
  }

  public IconsUriHelper(Supplier supplier) {
    ArrayMap arrayMap = new ArrayMap();
    this.mPackageIds = arrayMap;
    this.mIdSupplier = supplier == null ? DEFAULT_ID_SUPPLIER : supplier;
    arrayMap.put("android", "android");
  }

  public static IconsUriHelper getInstance() {
    return sSingleton;
  }

  public Uri getContentUri(String str, int i) {
    Uri build;
    Objects.requireNonNull(str);
    synchronized (this.mPackageIds) {
      if (!this.mPackageIds.containsKey(str)) {
        this.mPackageIds.put(str, (String) this.mIdSupplier.get());
      }
      build =
          new Uri.Builder()
              .scheme("content")
              .authority("com.android.textclassifier.icons")
              .path((String) this.mPackageIds.get(str))
              .appendPath(Integer.toString(i))
              .build();
    }
    return build;
  }

  public ResourceInfo getResourceInfo(Uri uri) {
    if (!"content".equals(uri.getScheme())
        || !"com.android.textclassifier.icons".equals(uri.getAuthority())) {
      return null;
    }
    List<String> pathSegments = uri.getPathSegments();
    try {
    } catch (Exception e) {
      Log.v("IconsUriHelper", "Could not get resource info. Reason: " + e.getMessage());
    }
    synchronized (this.mPackageIds) {
      String str = pathSegments.get(0);
      int parseInt = Integer.parseInt(pathSegments.get(1));
      for (String str2 : this.mPackageIds.keySet()) {
        if (str.equals(this.mPackageIds.get(str2))) {
          return new ResourceInfo(str2, parseInt);
        }
      }
      return null;
    }
  }

  public final class ResourceInfo {

    /* renamed from: id */
    public final int f1728id;
    public final String packageName;

    public ResourceInfo(String str, int i) {
      Objects.requireNonNull(str);
      this.packageName = str;
      this.f1728id = i;
    }
  }
}
