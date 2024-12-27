package com.android.server.credentials.metrics;

import android.service.credentials.CredentialEntry;

import java.util.Map;
import java.util.function.Consumer;

public final /* synthetic */ class ProviderSessionMetric$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Map f$0;

    public /* synthetic */ ProviderSessionMetric$$ExternalSyntheticLambda0(int i, Map map) {
        this.$r8$classId = i;
        this.f$0 = map;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Map map = this.f$0;
        CredentialEntry credentialEntry = (CredentialEntry) obj;
        switch (i) {
            case 0:
                String substring = credentialEntry.getType().substring(r2.length() - 20);
                map.put(
                        substring,
                        Integer.valueOf(((Integer) map.getOrDefault(substring, 0)).intValue() + 1));
                break;
            default:
                String substring2 = credentialEntry.getType().substring(r2.length() - 20);
                map.put(
                        substring2,
                        Integer.valueOf(
                                ((Integer) map.getOrDefault(substring2, 0)).intValue() + 1));
                break;
        }
    }
}
