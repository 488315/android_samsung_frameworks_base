package com.android.net.module.util;

import android.text.TextUtils;
import com.android.net.module.util.DnsPacketUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes6.dex */
public class DnsPacket {
    public static final int ANSECTION = 1;
    public static final int ARSECTION = 3;
    public static final int NSSECTION = 2;
    static final int NUM_SECTIONS = 4;
    public static final int QDSECTION = 0;
    private static final String TAG = "DnsPacket";
    private static final int TYPE_CNAME = 5;
    public static final int TYPE_SVCB = 64;
    protected final DnsHeader mHeader;
    protected final List<DnsRecord>[] mRecords;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecordType {
    }

    public static class ParseException extends RuntimeException {
        public String reason;

        public ParseException(String str) {
            super(str);
            this.reason = str;
        }

        public ParseException(String str, Throwable th) {
            super(str, th);
            this.reason = str;
        }
    }

    public static class DnsHeader {
        private static final int FLAGS_SECTION_QR_BIT = 15;
        private static final int SIZE_IN_BYTES = 12;
        private static final String TAG = "DnsHeader";
        private final int mFlags;
        private final int mId;
        private final int[] mRecordCount;

        public DnsHeader(ByteBuffer byteBuffer) throws BufferUnderflowException {
            Objects.requireNonNull(byteBuffer);
            this.mId = Short.toUnsignedInt(byteBuffer.getShort());
            this.mFlags = Short.toUnsignedInt(byteBuffer.getShort());
            this.mRecordCount = new int[4];
            for (int i = 0; i < 4; i++) {
                this.mRecordCount[i] = Short.toUnsignedInt(byteBuffer.getShort());
            }
        }

        public boolean isResponse() {
            return (this.mFlags & 32768) != 0;
        }

        public DnsHeader(int i, int i2, int i3, int i4) {
            this.mId = i;
            this.mFlags = i2;
            this.mRecordCount = new int[]{i3, i4, 0, 0};
        }

        public int getRecordCount(int i) {
            return this.mRecordCount[i];
        }

        public int getFlags() {
            return this.mFlags;
        }

        public int getId() {
            return this.mId;
        }

        public String toString() {
            return "DnsHeader{id=" + this.mId + ", flags=" + this.mFlags + ", recordCounts=" + Arrays.toString(this.mRecordCount) + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }
            DnsHeader dnsHeader = (DnsHeader) obj;
            return this.mId == dnsHeader.mId && this.mFlags == dnsHeader.mFlags && Arrays.equals(this.mRecordCount, dnsHeader.mRecordCount);
        }

        public int hashCode() {
            return (this.mId * 31) + (this.mFlags * 37) + Arrays.hashCode(this.mRecordCount);
        }

        public byte[] getBytes() {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(12);
            byteBufferAllocate.putShort((short) this.mId);
            byteBufferAllocate.putShort((short) this.mFlags);
            for (int i = 0; i < 4; i++) {
                byteBufferAllocate.putShort((short) this.mRecordCount[i]);
            }
            return byteBufferAllocate.array();
        }
    }

    public static class DnsRecord {
        public static final int MAXNAMESIZE = 255;
        public static final int NAME_COMPRESSION = 192;
        public static final int NAME_NORMAL = 0;
        private static final String TAG = "DnsRecord";
        public final String dName;
        private final byte[] mRdata;
        public final int nsClass;
        public final int nsType;
        public final int rType;
        public final long ttl;

        protected DnsRecord(int i, ByteBuffer byteBuffer) throws BufferUnderflowException, ParseException {
            Objects.requireNonNull(byteBuffer);
            this.rType = i;
            String name = DnsPacketUtils.DnsRecordParser.parseName(byteBuffer, 0, true);
            this.dName = name;
            if (name.length() > 255) {
                throw new ParseException("Parse name fail, name size is too long: " + name.length());
            }
            this.nsType = Short.toUnsignedInt(byteBuffer.getShort());
            this.nsClass = Short.toUnsignedInt(byteBuffer.getShort());
            if (i != 0) {
                this.ttl = Integer.toUnsignedLong(byteBuffer.getInt());
                byte[] bArr = new byte[Short.toUnsignedInt(byteBuffer.getShort())];
                this.mRdata = bArr;
                byteBuffer.get(bArr);
                return;
            }
            this.ttl = 0L;
            this.mRdata = null;
        }

        public static DnsRecord parse(int i, ByteBuffer byteBuffer) throws BufferUnderflowException, ParseException {
            Objects.requireNonNull(byteBuffer);
            int iPosition = byteBuffer.position();
            DnsPacketUtils.DnsRecordParser.parseName(byteBuffer, 0, true);
            int unsignedInt = Short.toUnsignedInt(byteBuffer.getShort());
            byteBuffer.position(iPosition);
            if (unsignedInt == 64) {
                return new DnsSvcbRecord(i, byteBuffer);
            }
            return new DnsRecord(i, byteBuffer);
        }

        public static DnsRecord makeAOrAAAARecord(int i, String str, int i2, long j, InetAddress inetAddress) throws IOException {
            return new DnsRecord(i, str, inetAddress.getAddress().length == 4 ? 1 : 28, i2, j, inetAddress, null);
        }

        public static DnsRecord makeCNameRecord(int i, String str, int i2, long j, String str2) throws IOException {
            return new DnsRecord(i, str, 5, i2, j, null, str2);
        }

        public static DnsRecord makeQuestion(String str, int i, int i2) {
            return new DnsRecord(str, i, i2);
        }

        private static String requireHostName(String str) {
            if (DnsPacketUtils.DnsRecordParser.isHostName(str)) {
                return str;
            }
            throw new IllegalArgumentException("Expected domain name but got " + str);
        }

        private DnsRecord(String str, int i, int i2) {
            this.rType = 0;
            this.dName = requireHostName(str);
            this.nsType = i;
            this.nsClass = i2;
            this.mRdata = null;
            this.ttl = 0L;
        }

        private DnsRecord(int i, String str, int i2, int i3, long j, InetAddress inetAddress, String str2) throws IOException {
            this.rType = i;
            this.dName = requireHostName(str);
            this.nsType = i2;
            this.nsClass = i3;
            if (i < 0 || i >= 4 || i == 0) {
                throw new IllegalArgumentException("Unexpected record type: " + i);
            }
            this.mRdata = i2 == 5 ? DnsPacketUtils.DnsRecordParser.domainNameToLabels(str2) : inetAddress.getAddress();
            this.ttl = j;
        }

        public byte[] getRR() {
            byte[] bArr = this.mRdata;
            if (bArr == null) {
                return null;
            }
            return (byte[]) bArr.clone();
        }

        public byte[] getBytes() throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.write(DnsPacketUtils.DnsRecordParser.domainNameToLabels(this.dName));
            dataOutputStream.writeShort(this.nsType);
            dataOutputStream.writeShort(this.nsClass);
            if (this.rType != 0) {
                dataOutputStream.writeInt((int) this.ttl);
                byte[] bArr = this.mRdata;
                if (bArr == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(bArr.length);
                    dataOutputStream.write(this.mRdata);
                }
            }
            return byteArrayOutputStream.toByteArray();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }
            DnsRecord dnsRecord = (DnsRecord) obj;
            return this.rType == dnsRecord.rType && this.nsType == dnsRecord.nsType && this.nsClass == dnsRecord.nsClass && this.ttl == dnsRecord.ttl && TextUtils.equals(this.dName, dnsRecord.dName) && Arrays.equals(this.mRdata, dnsRecord.mRdata);
        }

        public int hashCode() {
            int iHash = Objects.hash(this.dName) * 31;
            long j = this.ttl;
            return iHash + (((int) j) * 37) + (((int) (j >> 32)) * 41) + (this.nsType * 43) + (this.nsClass * 47) + (this.rType * 53) + Arrays.hashCode(this.mRdata);
        }

        public String toString() {
            return "DnsRecord{rType=" + this.rType + ", dName='" + this.dName + "', nsType=" + this.nsType + ", nsClass=" + this.nsClass + ", ttl=" + this.ttl + ", mRdata=" + Arrays.toString(this.mRdata) + '}';
        }
    }

    public List<DnsRecord> getRecords(int i) {
        return this.mRecords[i];
    }

    public DnsPacket(byte[] bArr) throws ParseException {
        if (bArr == null) {
            throw new ParseException("Parse header failed, null input data");
        }
        try {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
            this.mHeader = new DnsHeader(byteBufferWrap);
            this.mRecords = new ArrayList[4];
            for (int i = 0; i < 4; i++) {
                int recordCount = this.mHeader.getRecordCount(i);
                this.mRecords[i] = new ArrayList(recordCount);
                for (int i2 = 0; i2 < recordCount; i2++) {
                    try {
                        this.mRecords[i].add(DnsRecord.parse(i, byteBufferWrap));
                    } catch (BufferUnderflowException e) {
                        throw new ParseException("Parse record fail", e);
                    }
                }
            }
        } catch (BufferUnderflowException e2) {
            throw new ParseException("Parse Header fail, bad input data", e2);
        }
    }

    public DnsPacket(DnsHeader dnsHeader, List<DnsRecord> list, List<DnsRecord> list2) {
        this.mHeader = (DnsHeader) Objects.requireNonNull(dnsHeader);
        this.mRecords = new List[]{listUnmodifiableList, Collections.unmodifiableList(new ArrayList(list2)), new ArrayList(), new ArrayList()};
        List<DnsRecord> listUnmodifiableList = Collections.unmodifiableList(new ArrayList(list));
        for (int i = 0; i < 4; i++) {
            if (this.mHeader.mRecordCount[i] != this.mRecords[i].size()) {
                throw new IllegalArgumentException("Record count mismatch: expected " + this.mHeader.mRecordCount[i] + " but was " + this.mRecords[i]);
            }
        }
    }

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(this.mHeader.getBytes());
        for (int i = 0; i < 4; i++) {
            Iterator<DnsRecord> it = this.mRecords[i].iterator();
            while (it.hasNext()) {
                byteArrayOutputStream.write(it.next().getBytes());
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public String toString() {
        return "DnsPacket{header=" + this.mHeader + ", records='" + Arrays.toString(this.mRecords) + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        DnsPacket dnsPacket = (DnsPacket) obj;
        return Objects.equals(this.mHeader, dnsPacket.mHeader) && Arrays.deepEquals(this.mRecords, dnsPacket.mRecords);
    }

    public int hashCode() {
        return (Objects.hash(this.mHeader) * 31) + Arrays.hashCode(this.mRecords);
    }
}
