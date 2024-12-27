package com.android.server.pm.verify.domain;

import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Patterns;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedIntentInfo;
import com.android.server.SystemConfig;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.pkg.AndroidPackage;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DomainVerificationCollector {
    public final Matcher mDomainMatcher = DOMAIN_NAME_WITH_WILDCARD.matcher("");
    public final PlatformCompat mPlatformCompat;
    public final SystemConfig mSystemConfig;
    public static final Pattern DOMAIN_NAME_WITH_WILDCARD = Pattern.compile("(\\*\\.)?" + Patterns.DOMAIN_NAME.pattern());
    public static final DomainVerificationCollector$$ExternalSyntheticLambda0 ARRAY_SET_COLLECTOR = new DomainVerificationCollector$$ExternalSyntheticLambda0();

    public DomainVerificationCollector(PlatformCompat platformCompat, SystemConfig systemConfig) {
        this.mPlatformCompat = platformCompat;
        this.mSystemConfig = systemConfig;
    }

    public final ArraySet collectDomains(AndroidPackage androidPackage, boolean z, boolean z2) {
        ArraySet arraySet = new ArraySet();
        collectDomains(androidPackage, z, z2, arraySet, ARRAY_SET_COLLECTOR);
        return arraySet;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0079, code lost:
    
        if (r4 == false) goto L19;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1, types: [int] */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r12v2, types: [android.content.IntentFilter] */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [int] */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r9v2, types: [java.util.List] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object collectDomains(com.android.server.pm.pkg.AndroidPackage r18, boolean r19, boolean r20, java.lang.Object r21, java.util.function.BiFunction r22) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.verify.domain.DomainVerificationCollector.collectDomains(com.android.server.pm.pkg.AndroidPackage, boolean, boolean, java.lang.Object, java.util.function.BiFunction):java.lang.Object");
    }

    public final Object collectDomainsInternal(AndroidPackage androidPackage, boolean z, boolean z2, Object obj, BiFunction biFunction) {
        boolean matches;
        boolean z3;
        Object obj2;
        BiFunction biFunction2;
        boolean z4;
        List activities = androidPackage.getActivities();
        int size = activities.size();
        boolean z5 = true;
        int i = 0;
        for (int i2 = 0; i2 < size && z5; i2++) {
            List intents = ((ParsedActivity) activities.get(i2)).getIntents();
            int size2 = intents.size();
            for (int i3 = 0; i3 < size2 && z5; i3++) {
                IntentFilter intentFilter = ((ParsedIntentInfo) intents.get(i3)).getIntentFilter();
                if ((!z || intentFilter.getAutoVerify()) && intentFilter.hasCategory("android.intent.category.DEFAULT") && intentFilter.handlesWebUris(z)) {
                    int countDataAuthorities = intentFilter.countDataAuthorities();
                    for (int i4 = 0; i4 < countDataAuthorities && z5; i4++) {
                        String host = intentFilter.getDataAuthority(i4).getHost();
                        if (TextUtils.isEmpty(host)) {
                            z3 = z2;
                            matches = false;
                        } else {
                            this.mDomainMatcher.reset(host);
                            matches = this.mDomainMatcher.matches();
                            z3 = z2;
                        }
                        if (matches == z3) {
                            int estimatedByteSizeOf = android.content.pm.verify.domain.DomainVerificationUtils.estimatedByteSizeOf(host) + i;
                            if (estimatedByteSizeOf < 1048576) {
                                obj2 = obj;
                                biFunction2 = biFunction;
                                z4 = true;
                            } else {
                                obj2 = obj;
                                biFunction2 = biFunction;
                                z4 = false;
                            }
                            Object apply = biFunction2.apply(obj2, host);
                            if (apply != null) {
                                return apply;
                            }
                            i = estimatedByteSizeOf;
                            z5 = z4;
                        }
                    }
                }
            }
        }
        return null;
    }
}
