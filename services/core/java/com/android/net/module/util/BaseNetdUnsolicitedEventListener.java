package com.android.net.module.util;

import android.net.INetdUnsolicitedEventListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BaseNetdUnsolicitedEventListener extends INetdUnsolicitedEventListener.Stub {
    @Override // android.net.INetdUnsolicitedEventListener
    public final String getInterfaceHash() {
        return "2be6ff6fb01645cdddb3bb60f6de5727e5733267";
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final int getInterfaceVersion() {
        return 15;
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final void onInterfaceAdded(String str) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final void onInterfaceAddressRemoved(String str, String str2, int i, int i2) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final void onInterfaceAddressUpdated(String str, String str2, int i, int i2) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final void onInterfaceChanged(String str, boolean z) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final void onInterfaceClassActivityChanged(boolean z, int i, long j, int i2) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final void onInterfaceDnsServerInfo(String str, long j, String[] strArr) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final void onInterfaceLinkStateChanged(String str, boolean z) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final void onInterfaceRemoved(String str) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final void onQuotaLimitReached(String str, String str2) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final void onRouteChanged(boolean z, String str, String str2, String str3) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public final void onStrictCleartextDetected(int i, String str) {
    }
}
