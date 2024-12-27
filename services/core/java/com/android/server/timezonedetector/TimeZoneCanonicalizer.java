package com.android.server.timezonedetector;

import com.android.i18n.timezone.TimeZoneFinder;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TimeZoneCanonicalizer implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String str = (String) obj;
        String findCanonicalTimeZoneId = TimeZoneFinder.getInstance().getCountryZonesFinder().findCanonicalTimeZoneId(str);
        return findCanonicalTimeZoneId == null ? str : findCanonicalTimeZoneId;
    }
}
