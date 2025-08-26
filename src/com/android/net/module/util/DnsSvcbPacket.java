package com.android.net.module.util;

import android.util.Log;
import com.android.net.module.util.DnsPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class DnsSvcbPacket extends DnsPacket {
    private static final String TAG = "DnsSvcbPacket";
    public static final int TYPE_SVCB = 64;

    private DnsSvcbPacket(byte[] bArr) throws DnsPacket.ParseException {
        super(bArr);
        int recordCount = this.mHeader.getRecordCount(0);
        if (recordCount != 1) {
            throw new DnsPacket.ParseException("Unexpected question count " + recordCount);
        }
        int i = this.mRecords[0].get(0).nsType;
        if (i == 64) {
            return;
        }
        throw new DnsPacket.ParseException("Unexpected query type " + i);
    }

    public boolean isResponse() {
        return this.mHeader.isResponse();
    }

    public boolean isSupported(String str) {
        return findSvcbRecord(str) != null;
    }

    public String getTargetName(String str) {
        DnsSvcbRecord dnsSvcbRecordFindSvcbRecord = findSvcbRecord(str);
        if (dnsSvcbRecordFindSvcbRecord != null) {
            return dnsSvcbRecordFindSvcbRecord.getTargetName();
        }
        return null;
    }

    public int getPort(String str) {
        DnsSvcbRecord dnsSvcbRecordFindSvcbRecord = findSvcbRecord(str);
        if (dnsSvcbRecordFindSvcbRecord != null) {
            return dnsSvcbRecordFindSvcbRecord.getPort();
        }
        return -1;
    }

    public List<InetAddress> getAddresses(String str) {
        DnsSvcbRecord dnsSvcbRecordFindSvcbRecord = findSvcbRecord(str);
        if (dnsSvcbRecordFindSvcbRecord == null) {
            return Collections.EMPTY_LIST;
        }
        List<InetAddress> addressesFromAdditionalSection = getAddressesFromAdditionalSection();
        return addressesFromAdditionalSection.size() > 0 ? addressesFromAdditionalSection : dnsSvcbRecordFindSvcbRecord.getAddresses();
    }

    public String getDohPath(String str) {
        DnsSvcbRecord dnsSvcbRecordFindSvcbRecord = findSvcbRecord(str);
        if (dnsSvcbRecordFindSvcbRecord != null) {
            return dnsSvcbRecordFindSvcbRecord.getDohPath();
        }
        return null;
    }

    private DnsSvcbRecord findSvcbRecord(String str) {
        for (DnsPacket.DnsRecord dnsRecord : this.mRecords[1]) {
            if (dnsRecord instanceof DnsSvcbRecord) {
                DnsSvcbRecord dnsSvcbRecord = (DnsSvcbRecord) dnsRecord;
                if (dnsSvcbRecord.getAlpns().contains(str)) {
                    return dnsSvcbRecord;
                }
            }
        }
        return null;
    }

    private List<InetAddress> getAddressesFromAdditionalSection() {
        ArrayList arrayList = new ArrayList();
        if (this.mHeader.getRecordCount(3) != 0) {
            for (DnsPacket.DnsRecord dnsRecord : this.mRecords[3]) {
                if (dnsRecord.nsType != 1 && dnsRecord.nsType != 28) {
                    Log.d(TAG, "Found type other than A/AAAA in Additional section: " + dnsRecord.nsType);
                } else {
                    try {
                        arrayList.add(InetAddress.getByAddress(dnsRecord.getRR()));
                    } catch (UnknownHostException unused) {
                        Log.w(TAG, "Failed to parse address");
                    }
                }
            }
        }
        return arrayList;
    }

    public static DnsSvcbPacket fromResponse(byte[] bArr) throws DnsPacket.ParseException {
        DnsSvcbPacket dnsSvcbPacket = new DnsSvcbPacket(bArr);
        if (dnsSvcbPacket.isResponse()) {
            return dnsSvcbPacket;
        }
        throw new DnsPacket.ParseException("Not an answer packet");
    }
}
