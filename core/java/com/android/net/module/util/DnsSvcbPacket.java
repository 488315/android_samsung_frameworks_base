package com.android.net.module.util;

import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes5.dex */
public class DnsSvcbPacket extends DnsPacket {
    private static final String TAG = DnsSvcbPacket.class.getSimpleName();
    public static final int TYPE_SVCB = 64;

    private DnsSvcbPacket(byte[] data) throws DnsPacket.ParseException {
        super(data);
        int questions = this.mHeader.getRecordCount(0);
        if (questions != 1) {
            throw new DnsPacket.ParseException("Unexpected question count " + questions);
        }
        int nsType = this.mRecords[0].get(0).nsType;
        if (nsType != 64) {
            throw new DnsPacket.ParseException("Unexpected query type " + nsType);
        }
    }

    public boolean isResponse() {
        return this.mHeader.isResponse();
    }

    public boolean isSupported(String alpn) {
        return findSvcbRecord(alpn) != null;
    }

    public String getTargetName(String alpn) {
        DnsSvcbRecord record = findSvcbRecord(alpn);
        if (record != null) {
            return record.getTargetName();
        }
        return null;
    }

    public int getPort(String alpn) {
        DnsSvcbRecord record = findSvcbRecord(alpn);
        if (record != null) {
            return record.getPort();
        }
        return -1;
    }

    public List<InetAddress> getAddresses(String alpn) {
        DnsSvcbRecord record = findSvcbRecord(alpn);
        if (record == null) {
            return Collections.EMPTY_LIST;
        }
        List<InetAddress> out = getAddressesFromAdditionalSection();
        return out.size() > 0 ? out : record.getAddresses();
    }

    public String getDohPath(String alpn) {
        DnsSvcbRecord record = findSvcbRecord(alpn);
        if (record != null) {
            return record.getDohPath();
        }
        return null;
    }

    private DnsSvcbRecord findSvcbRecord(String alpn) {
        for (DnsPacket.DnsRecord record : this.mRecords[1]) {
            if (record instanceof DnsSvcbRecord) {
                DnsSvcbRecord svcbRecord = (DnsSvcbRecord) record;
                if (svcbRecord.getAlpns().contains(alpn)) {
                    return svcbRecord;
                }
            }
        }
        return null;
    }

    private List<InetAddress> getAddressesFromAdditionalSection() {
        List<InetAddress> out = new ArrayList<>();
        if (this.mHeader.getRecordCount(3) == 0) {
            return out;
        }
        for (DnsPacket.DnsRecord record : this.mRecords[3]) {
            if (record.nsType != 1 && record.nsType != 28) {
                Log.d(TAG, "Found type other than A/AAAA in Additional section: " + record.nsType);
            } else {
                try {
                    out.add(InetAddress.getByAddress(record.getRR()));
                } catch (UnknownHostException e) {
                    Log.w(TAG, "Failed to parse address");
                }
            }
        }
        return out;
    }

    public static DnsSvcbPacket fromResponse(byte[] data) throws DnsPacket.ParseException {
        DnsSvcbPacket out = new DnsSvcbPacket(data);
        if (!out.isResponse()) {
            throw new DnsPacket.ParseException("Not an answer packet");
        }
        return out;
    }
}
