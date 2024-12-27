package com.android.server.pm.verify.domain;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import java.util.regex.Matcher;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class DomainVerificationUtils {
    public static final ThreadLocal sCachedMatcher = ThreadLocal.withInitial(new DomainVerificationUtils$$ExternalSyntheticLambda0());

    public static boolean isDomainVerificationIntent(Intent intent, long j) {
        int size;
        if (!intent.isWebIntent()) {
            return false;
        }
        String host = intent.getData().getHost();
        if (TextUtils.isEmpty(host) || !((Matcher) sCachedMatcher.get()).reset(host).matches() || (size = CollectionUtils.size(intent.getCategories())) > 2) {
            return false;
        }
        if (size == 2) {
            return intent.hasCategory("android.intent.category.DEFAULT") && intent.hasCategory("android.intent.category.BROWSABLE");
        }
        boolean z = (j & 65536) != 0;
        return (size == 0 || intent.hasCategory("android.intent.category.BROWSABLE")) ? z : intent.hasCategory("android.intent.category.DEFAULT");
    }

    public static void throwPackageUnavailable(String str) {
        throw new PackageManager.NameNotFoundException(XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " unavailable"));
    }
}
