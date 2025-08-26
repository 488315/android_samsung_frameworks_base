package com.android.net.module.util;

import android.text.TextUtils;
import android.util.SparseArray;
import com.android.net.module.util.DnsPacket;
import com.android.net.module.util.DnsPacketUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public final class DnsSvcbRecord extends DnsPacket.DnsRecord {
    private static final int KEY_ALPN = 1;
    private static final int KEY_DOHPATH = 7;
    private static final int KEY_ECH = 5;
    private static final int KEY_IPV4HINT = 4;
    private static final int KEY_IPV6HINT = 6;
    private static final int KEY_MANDATORY = 0;
    private static final int KEY_NO_DEFAULT_ALPN = 2;
    private static final int KEY_PORT = 3;
    private static final int MINSVCPARAMSIZE = 4;
    private static final String TAG = "DnsSvcbRecord";
    private final SparseArray<SvcParam> mAllSvcParams;
    private final int mSvcPriority;
    private final String mTargetName;

    public DnsSvcbRecord(int i, ByteBuffer byteBuffer) throws IllegalStateException, BufferUnderflowException, DnsPacket.ParseException {
        super(i, byteBuffer);
        this.mAllSvcParams = new SparseArray<>();
        if (this.nsType != 64) {
            throw new IllegalStateException("incorrect nsType: " + this.nsType);
        }
        if (this.nsClass != 1) {
            throw new DnsPacket.ParseException("incorrect nsClass: " + this.nsClass);
        }
        if (i == 0) {
            this.mSvcPriority = 0;
            this.mTargetName = "";
            return;
        }
        byte[] rr = getRR();
        if (rr == null) {
            throw new DnsPacket.ParseException("SVCB rdata is empty");
        }
        ByteBuffer byteBufferAsReadOnlyBuffer = ByteBuffer.wrap(rr).asReadOnlyBuffer();
        this.mSvcPriority = Short.toUnsignedInt(byteBufferAsReadOnlyBuffer.getShort());
        String name = DnsPacketUtils.DnsRecordParser.parseName(byteBufferAsReadOnlyBuffer, 0, false);
        this.mTargetName = name;
        if (name.length() > 255) {
            throw new DnsPacket.ParseException("Failed to parse SVCB target name, name size is too long: " + name.length());
        }
        while (byteBufferAsReadOnlyBuffer.remaining() >= 4) {
            SvcParam svcParam = parseSvcParam(byteBufferAsReadOnlyBuffer);
            int key = svcParam.getKey();
            if (this.mAllSvcParams.get(key) != null) {
                throw new DnsPacket.ParseException("Invalid DnsSvcbRecord, key " + key + " is repeated");
            }
            this.mAllSvcParams.put(key, svcParam);
        }
        if (byteBufferAsReadOnlyBuffer.hasRemaining()) {
            throw new DnsPacket.ParseException("Invalid DnsSvcbRecord. Got " + byteBufferAsReadOnlyBuffer.remaining() + " remaining bytes after parsing");
        }
    }

    public String getTargetName() {
        return this.mTargetName;
    }

    public List<String> getAlpns() {
        SvcParamAlpn svcParamAlpn = (SvcParamAlpn) this.mAllSvcParams.get(1);
        return Collections.unmodifiableList(svcParamAlpn != null ? svcParamAlpn.getValue() : Collections.EMPTY_LIST);
    }

    public int getPort() {
        SvcParamPort svcParamPort = (SvcParamPort) this.mAllSvcParams.get(3);
        if (svcParamPort != null) {
            return svcParamPort.getValue().intValue();
        }
        return -1;
    }

    public List<InetAddress> getAddresses() {
        ArrayList arrayList = new ArrayList();
        SvcParamIpHint svcParamIpHint = (SvcParamIpHint) this.mAllSvcParams.get(4);
        if (svcParamIpHint != null) {
            arrayList.addAll(svcParamIpHint.getValue());
        }
        SvcParamIpHint svcParamIpHint2 = (SvcParamIpHint) this.mAllSvcParams.get(6);
        if (svcParamIpHint2 != null) {
            arrayList.addAll(svcParamIpHint2.getValue());
        }
        return arrayList;
    }

    public String getDohPath() {
        SvcParamDohPath svcParamDohPath = (SvcParamDohPath) this.mAllSvcParams.get(7);
        return svcParamDohPath != null ? svcParamDohPath.getValue() : "";
    }

    @Override // com.android.net.module.util.DnsPacket.DnsRecord
    public String toString() {
        if (this.rType == 0) {
            return this.dName + " IN SVCB";
        }
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (int i = 0; i < this.mAllSvcParams.size(); i++) {
            stringJoiner.add(this.mAllSvcParams.valueAt(i).toString());
        }
        return this.dName + " " + this.ttl + " IN SVCB " + this.mSvcPriority + " " + this.mTargetName + " " + stringJoiner.toString();
    }

    private static SvcParam parseSvcParam(ByteBuffer byteBuffer) throws DnsPacket.ParseException {
        try {
            int unsignedInt = Short.toUnsignedInt(byteBuffer.getShort());
            switch (unsignedInt) {
                case 0:
                    return new SvcParamMandatory(byteBuffer);
                case 1:
                    return new SvcParamAlpn(byteBuffer);
                case 2:
                    return new SvcParamNoDefaultAlpn(byteBuffer);
                case 3:
                    return new SvcParamPort(byteBuffer);
                case 4:
                    return new SvcParamIpv4Hint(byteBuffer);
                case 5:
                    return new SvcParamEch(byteBuffer);
                case 6:
                    return new SvcParamIpv6Hint(byteBuffer);
                case 7:
                    return new SvcParamDohPath(byteBuffer);
                default:
                    return new SvcParamGeneric(unsignedInt, byteBuffer);
            }
        } catch (BufferUnderflowException e) {
            throw new DnsPacket.ParseException("Malformed packet", e);
        }
    }

    private static abstract class SvcParam<T> {
        private final int mKey;

        abstract T getValue();

        SvcParam(int i) {
            this.mKey = i;
        }

        int getKey() {
            return this.mKey;
        }
    }

    private static class SvcParamMandatory extends SvcParam<short[]> {
        private final short[] mValue;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public short[] getValue() {
            return null;
        }

        private SvcParamMandatory(ByteBuffer byteBuffer) throws BufferUnderflowException, DnsPacket.ParseException {
            super(0);
            short[] shortArray = SvcParamValueUtil.toShortArray(DnsSvcbRecord.sliceAndAdvance(byteBuffer, Short.toUnsignedInt(byteBuffer.getShort())));
            this.mValue = shortArray;
            if (shortArray.length == 0) {
                throw new DnsPacket.ParseException("mandatory value must be non-empty");
            }
        }

        public String toString() {
            StringJoiner stringJoiner = new StringJoiner(",");
            for (short s : this.mValue) {
                stringJoiner.add(DnsSvcbRecord.toKeyName(s));
            }
            return DnsSvcbRecord.toKeyName(getKey()) + "=" + stringJoiner.toString();
        }
    }

    private static class SvcParamAlpn extends SvcParam<List<String>> {
        private final List<String> mValue;

        SvcParamAlpn(ByteBuffer byteBuffer) throws BufferUnderflowException, DnsPacket.ParseException {
            super(1);
            List<String> stringList = SvcParamValueUtil.toStringList(DnsSvcbRecord.sliceAndAdvance(byteBuffer, Short.toUnsignedInt(byteBuffer.getShort())));
            this.mValue = stringList;
            if (stringList.isEmpty()) {
                throw new DnsPacket.ParseException("alpn value must be non-empty");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public List<String> getValue() {
            return Collections.unmodifiableList(this.mValue);
        }

        public String toString() {
            return DnsSvcbRecord.toKeyName(getKey()) + "=" + TextUtils.join(",", this.mValue);
        }
    }

    private static class SvcParamNoDefaultAlpn extends SvcParam<Void> {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public Void getValue() {
            return null;
        }

        SvcParamNoDefaultAlpn(ByteBuffer byteBuffer) throws BufferUnderflowException, DnsPacket.ParseException {
            super(2);
            if (byteBuffer.getShort() != 0) {
                throw new DnsPacket.ParseException("no-default-alpn value must be empty");
            }
        }

        public String toString() {
            return DnsSvcbRecord.toKeyName(getKey());
        }
    }

    private static class SvcParamPort extends SvcParam<Integer> {
        private final int mValue;

        SvcParamPort(ByteBuffer byteBuffer) throws BufferUnderflowException, DnsPacket.ParseException {
            super(3);
            short s = byteBuffer.getShort();
            if (s != 2) {
                throw new DnsPacket.ParseException("key port len is not 2 but " + ((int) s));
            }
            this.mValue = Short.toUnsignedInt(byteBuffer.getShort());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public Integer getValue() {
            return Integer.valueOf(this.mValue);
        }

        public String toString() {
            return DnsSvcbRecord.toKeyName(getKey()) + "=" + this.mValue;
        }
    }

    private static class SvcParamIpHint extends SvcParam<List<InetAddress>> {
        private final List<InetAddress> mValue;

        private SvcParamIpHint(int i, ByteBuffer byteBuffer, int i2) throws BufferUnderflowException, DnsPacket.ParseException {
            super(i);
            List<InetAddress> inetAddressList = SvcParamValueUtil.toInetAddressList(DnsSvcbRecord.sliceAndAdvance(byteBuffer, Short.toUnsignedInt(byteBuffer.getShort())), i2);
            this.mValue = inetAddressList;
            if (inetAddressList.isEmpty()) {
                throw new DnsPacket.ParseException(DnsSvcbRecord.toKeyName(getKey()) + " value must be non-empty");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public List<InetAddress> getValue() {
            return Collections.unmodifiableList(this.mValue);
        }

        public String toString() {
            StringJoiner stringJoiner = new StringJoiner(",");
            Iterator<InetAddress> it = this.mValue.iterator();
            while (it.hasNext()) {
                stringJoiner.add(it.next().getHostAddress());
            }
            return DnsSvcbRecord.toKeyName(getKey()) + "=" + stringJoiner.toString();
        }
    }

    private static class SvcParamIpv4Hint extends SvcParamIpHint {
        /* JADX WARN: Illegal instructions before constructor call */
        SvcParamIpv4Hint(ByteBuffer byteBuffer) throws BufferUnderflowException, DnsPacket.ParseException {
            int i = 4;
            super(i, byteBuffer, i);
        }
    }

    private static class SvcParamIpv6Hint extends SvcParamIpHint {
        SvcParamIpv6Hint(ByteBuffer byteBuffer) throws BufferUnderflowException, DnsPacket.ParseException {
            super(6, byteBuffer, 16);
        }
    }

    private static class SvcParamEch extends SvcParamGeneric {
        SvcParamEch(ByteBuffer byteBuffer) throws BufferUnderflowException, DnsPacket.ParseException {
            super(5, byteBuffer);
        }
    }

    private static class SvcParamDohPath extends SvcParam<String> {
        private final String mValue;

        SvcParamDohPath(ByteBuffer byteBuffer) throws BufferUnderflowException, DnsPacket.ParseException {
            super(7);
            byte[] bArr = new byte[Short.toUnsignedInt(byteBuffer.getShort())];
            byteBuffer.get(bArr);
            this.mValue = new String(bArr, StandardCharsets.UTF_8);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public String getValue() {
            return this.mValue;
        }

        public String toString() {
            return DnsSvcbRecord.toKeyName(getKey()) + "=" + this.mValue;
        }
    }

    private static class SvcParamGeneric extends SvcParam<byte[]> {
        private final byte[] mValue;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public byte[] getValue() {
            return null;
        }

        SvcParamGeneric(int i, ByteBuffer byteBuffer) throws BufferUnderflowException, DnsPacket.ParseException {
            super(i);
            byte[] bArr = new byte[Short.toUnsignedInt(byteBuffer.getShort())];
            this.mValue = bArr;
            byteBuffer.get(bArr);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(DnsSvcbRecord.toKeyName(getKey()));
            byte[] bArr = this.mValue;
            if (bArr != null && bArr.length > 0) {
                sb.append("=");
                sb.append(HexDump.toHexString(this.mValue));
            }
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String toKeyName(int i) {
        switch (i) {
            case 0:
                return "mandatory";
            case 1:
                return "alpn";
            case 2:
                return "no-default-alpn";
            case 3:
                return "port";
            case 4:
                return "ipv4hint";
            case 5:
                return "ech";
            case 6:
                return "ipv6hint";
            case 7:
                return "dohpath";
            default:
                return "key" + i;
        }
    }

    public static ByteBuffer sliceAndAdvance(ByteBuffer byteBuffer, int i) throws BufferUnderflowException {
        if (byteBuffer.remaining() < i) {
            throw new BufferUnderflowException();
        }
        int iPosition = byteBuffer.position();
        ByteBuffer byteBufferSlice = ((ByteBuffer) byteBuffer.slice().limit(i)).slice();
        byteBuffer.position(iPosition + i);
        return byteBufferSlice.asReadOnlyBuffer();
    }

    private static class SvcParamValueUtil {
        private SvcParamValueUtil() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static List<String> toStringList(ByteBuffer byteBuffer) throws BufferUnderflowException, DnsPacket.ParseException {
            ArrayList arrayList = new ArrayList();
            while (byteBuffer.hasRemaining()) {
                int unsignedInt = Byte.toUnsignedInt(byteBuffer.get());
                if (unsignedInt == 0) {
                    throw new DnsPacket.ParseException("alpn should not be an empty string");
                }
                byte[] bArr = new byte[unsignedInt];
                byteBuffer.get(bArr);
                arrayList.add(new String(bArr, StandardCharsets.UTF_8));
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static short[] toShortArray(ByteBuffer byteBuffer) throws BufferUnderflowException, DnsPacket.ParseException {
            if (byteBuffer.remaining() % 2 != 0) {
                throw new DnsPacket.ParseException("Can't parse whole byte array");
            }
            ShortBuffer shortBufferAsShortBuffer = byteBuffer.asShortBuffer();
            short[] sArr = new short[shortBufferAsShortBuffer.remaining()];
            shortBufferAsShortBuffer.get(sArr);
            return sArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static List<InetAddress> toInetAddressList(ByteBuffer byteBuffer, int i) throws BufferUnderflowException, DnsPacket.ParseException {
            if (byteBuffer.remaining() % i != 0) {
                throw new DnsPacket.ParseException("Can't parse whole byte array");
            }
            ArrayList arrayList = new ArrayList();
            byte[] bArr = new byte[i];
            while (byteBuffer.remaining() >= i) {
                byteBuffer.get(bArr);
                try {
                    arrayList.add(InetAddress.getByAddress(bArr));
                } catch (UnknownHostException unused) {
                    throw new DnsPacket.ParseException("Can't parse byte array as an IP address");
                }
            }
            return arrayList;
        }
    }
}
