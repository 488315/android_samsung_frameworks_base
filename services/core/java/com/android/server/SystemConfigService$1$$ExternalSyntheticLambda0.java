package com.android.server;

import android.content.pm.SignedPackage;
import android.os.CarrierAssociatedAppEntry;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SystemConfigService$1$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SystemConfigService$1$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((SignedPackage) obj).getData();
            case 1:
                return (String) ((Map.Entry) obj).getKey();
            case 2:
                return (List)
                        ((List) ((Map.Entry) obj).getValue())
                                .stream()
                                        .map(new SystemConfigService$1$$ExternalSyntheticLambda0(3))
                                        .collect(Collectors.toList());
            default:
                return ((CarrierAssociatedAppEntry) obj).packageName;
        }
    }
}
