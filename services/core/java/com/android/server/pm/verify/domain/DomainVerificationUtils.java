package com.android.server.pm.verify.domain;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Patterns;
import com.android.internal.util.CollectionUtils;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.pkg.AndroidPackage;
import java.util.function.Supplier;
import java.util.regex.Matcher;

/* loaded from: classes3.dex */
public abstract class DomainVerificationUtils {
  public static final ThreadLocal sCachedMatcher =
      ThreadLocal.withInitial(
          new Supplier() { // from class:
                           // com.android.server.pm.verify.domain.DomainVerificationUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
              Matcher lambda$static$0;
              lambda$static$0 = DomainVerificationUtils.lambda$static$0();
              return lambda$static$0;
            }
          });

  public static /* synthetic */ Matcher lambda$static$0() {
    return Patterns.DOMAIN_NAME.matcher("");
  }

  public static PackageManager.NameNotFoundException throwPackageUnavailable(String str) {
    throw new PackageManager.NameNotFoundException("Package " + str + " unavailable");
  }

  public static boolean isDomainVerificationIntent(Intent intent, long j) {
    int size;
    if (!intent.isWebIntent()) {
      return false;
    }
    String host = intent.getData().getHost();
    if (TextUtils.isEmpty(host)
        || !((Matcher) sCachedMatcher.get()).reset(host).matches()
        || (size = CollectionUtils.size(intent.getCategories())) > 2) {
      return false;
    }
    if (size == 2) {
      return intent.hasCategory("android.intent.category.DEFAULT")
          && intent.hasCategory("android.intent.category.BROWSABLE");
    }
    boolean z = (j & 65536) != 0;
    return (size == 0 || intent.hasCategory("android.intent.category.BROWSABLE"))
        ? z
        : intent.hasCategory("android.intent.category.DEFAULT");
  }

  public static boolean isChangeEnabled(
      PlatformCompat platformCompat, AndroidPackage androidPackage, long j) {
    return platformCompat.isChangeEnabledInternalNoLogging(j, buildMockAppInfo(androidPackage));
  }

  public static ApplicationInfo buildMockAppInfo(AndroidPackage androidPackage) {
    ApplicationInfo applicationInfo = new ApplicationInfo();
    applicationInfo.packageName = androidPackage.getPackageName();
    applicationInfo.targetSdkVersion = androidPackage.getTargetSdkVersion();
    return applicationInfo;
  }
}
