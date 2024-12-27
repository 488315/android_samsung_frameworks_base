package com.android.server.enterprise.adapterlayer;

import android.net.IDnsResolver;

public final class DnsResolverAdapter {
    public static DnsResolverAdapter sInstance;
    public static final Object sLock = new Object();
    public volatile IDnsResolver mDnsResolver;

    public interface CheckedRemoteRequest {
        void execute(IDnsResolver iDnsResolver);
    }

    public static DnsResolverAdapter getInstance() {
        DnsResolverAdapter dnsResolverAdapter = sInstance;
        if (dnsResolverAdapter == null) {
            synchronized (sLock) {
                try {
                    dnsResolverAdapter = sInstance;
                    if (dnsResolverAdapter == null) {
                        dnsResolverAdapter = new DnsResolverAdapter();
                        sInstance = dnsResolverAdapter;
                    }
                } finally {
                }
            }
        }
        return dnsResolverAdapter;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void runWithExceptionHandling(
            com.android.server.enterprise.adapterlayer.DnsResolverAdapter.CheckedRemoteRequest r4) {
        /*
            r3 = this;
            android.net.IDnsResolver r0 = r3.mDnsResolver
            java.lang.String r1 = "DnsResolverAdapter"
            if (r0 != 0) goto L21
            java.lang.String r0 = "dnsresolver"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r0)
            if (r2 != 0) goto L17
            java.lang.String r0 = "updateEnforceDnsUidForNetwork: Failed to get binder for dns resolver service"
            android.util.Log.e(r1, r0)
            r0 = 0
            goto L23
        L17:
            android.os.IBinder r0 = android.os.ServiceManager.getService(r0)
            android.net.IDnsResolver r0 = android.net.IDnsResolver.Stub.asInterface(r0)
            r3.mDnsResolver = r0
        L21:
            android.net.IDnsResolver r0 = r3.mDnsResolver
        L23:
            if (r0 != 0) goto L2b
            java.lang.String r3 = "Failed to get dns resolver service"
            android.util.Log.e(r1, r3)
            return
        L2b:
            android.net.IDnsResolver r3 = r3.mDnsResolver     // Catch: java.lang.Throwable -> L37
            r4.execute(r3)     // Catch: java.lang.Throwable -> L37
            java.lang.String r3 = "dnsresolver called successfully"
            android.util.Log.d(r1, r3)     // Catch: java.lang.Throwable -> L37
            goto L3d
        L37:
            r3 = move-exception
            java.lang.String r4 = "Error calling dnsresolver service: "
            com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(r3, r4, r1)
        L3d:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.adapterlayer.DnsResolverAdapter.runWithExceptionHandling(com.android.server.enterprise.adapterlayer.DnsResolverAdapter$CheckedRemoteRequest):void");
    }
}
