package com.android.server.credentials.metrics;

import android.util.Slog;
import java.util.AbstractMap;
import java.util.Map;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r15v0 com.android.server.credentials.metrics.ApiName, still in use, count: 1, list:
  (r15v0 com.android.server.credentials.metrics.ApiName) from 0x007c: IGET (r15v0 com.android.server.credentials.metrics.ApiName) A[WRAPPED] com.android.server.credentials.metrics.ApiName.mInnerMetricCode int
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ApiName {
    UNKNOWN("UNKNOWN"),
    GET_CREDENTIAL("GET_CREDENTIAL"),
    GET_CREDENTIAL_VIA_REGISTRY("GET_CREDENTIAL_VIA_REGISTRY"),
    /* JADX INFO: Fake field, exist only in values array */
    EF15("CREATE_CREDENTIAL"),
    /* JADX INFO: Fake field, exist only in values array */
    EF8("CLEAR_CREDENTIAL"),
    IS_ENABLED_CREDENTIAL_PROVIDER_SERVICE("IS_ENABLED_CREDENTIAL_PROVIDER_SERVICE"),
    SET_ENABLED_PROVIDERS("SET_ENABLED_PROVIDERS"),
    GET_CREDENTIAL_PROVIDER_SERVICES("GET_CREDENTIAL_PROVIDER_SERVICES"),
    /* JADX INFO: Fake field, exist only in values array */
    EF84("REGISTER_CREDENTIAL_DESCRIPTION"),
    /* JADX INFO: Fake field, exist only in values array */
    EF91("UNREGISTER_CREDENTIAL_DESCRIPTION");

    public static final Map sRequestInfoToMetric;
    private final int mInnerMetricCode;

    static {
        ApiName apiName = GET_CREDENTIAL;
        ApiName apiName2 = GET_CREDENTIAL_VIA_REGISTRY;
        sRequestInfoToMetric = Map.ofEntries(new AbstractMap.SimpleEntry("android.credentials.selection.TYPE_CREATE", Integer.valueOf(r15.mInnerMetricCode)), new AbstractMap.SimpleEntry("android.credentials.selection.TYPE_GET", Integer.valueOf(apiName.mInnerMetricCode)), new AbstractMap.SimpleEntry("android.credentials.selection.TYPE_GET_VIA_REGISTRY", Integer.valueOf(apiName2.mInnerMetricCode)), new AbstractMap.SimpleEntry("android.credentials.selection.TYPE_UNDEFINED", Integer.valueOf(r8.mInnerMetricCode)));
    }

    public ApiName(String str) {
        this.mInnerMetricCode = r2;
    }

    public static int getMetricCodeFromRequestInfo(String str) {
        Map map = sRequestInfoToMetric;
        if (map.containsKey(str)) {
            return ((Integer) map.get(str)).intValue();
        }
        Slog.i("ApiName", "Attempted to use an unsupported string key request info");
        return UNKNOWN.mInnerMetricCode;
    }

    public static ApiName valueOf(String str) {
        return (ApiName) Enum.valueOf(ApiName.class, str);
    }

    public static ApiName[] values() {
        return (ApiName[]) $VALUES.clone();
    }

    public final int getMetricCode() {
        return this.mInnerMetricCode;
    }
}
