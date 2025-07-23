package com.android.server.net;

import java.net.InetAddress;

/* compiled from: NetlinkTracker.java */
/* loaded from: classes6.dex */
class DnsServerEntry implements Comparable<DnsServerEntry> {
    public final InetAddress address;
    public long expiry;

    public DnsServerEntry(InetAddress inetAddress, long j) throws IllegalArgumentException {
        this.address = inetAddress;
        this.expiry = j;
    }

    @Override // java.lang.Comparable
    public int compareTo(DnsServerEntry dnsServerEntry) {
        return Long.compare(dnsServerEntry.expiry, this.expiry);
    }
}
