package com.android.server.devicepolicy;

import android.net.wifi.WifiSsid;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ActiveAdmin$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new String(((WifiSsid) obj).getBytes(), StandardCharsets.UTF_8);
            default:
                return WifiSsid.fromBytes(((String) obj).getBytes(StandardCharsets.UTF_8));
        }
    }
}
