package com.android.server;

import android.content.pm.SignedPackage;
import android.os.CarrierAssociatedAppEntry;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
