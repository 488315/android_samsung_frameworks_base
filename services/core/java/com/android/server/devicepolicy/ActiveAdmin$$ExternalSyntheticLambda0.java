package com.android.server.devicepolicy;

import android.net.wifi.WifiSsid;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

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
