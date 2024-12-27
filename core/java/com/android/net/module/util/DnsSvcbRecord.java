package com.android.net.module.util;

import android.text.TextUtils;
import android.util.SparseArray;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

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
    private static final String TAG = DnsSvcbRecord.class.getSimpleName();
    private final SparseArray<SvcParam> mAllSvcParams;
    private final int mSvcPriority;
    private final String mTargetName;

    public DnsSvcbRecord(int rType, ByteBuffer buff)
            throws IllegalStateException, DnsPacket.ParseException {
        super(rType, buff);
        this.mAllSvcParams = new SparseArray<>();
        if (this.nsType != 64) {
            throw new IllegalStateException("incorrect nsType: " + this.nsType);
        }
        if (this.nsClass != 1) {
            throw new DnsPacket.ParseException("incorrect nsClass: " + this.nsClass);
        }
        if (rType == 0) {
            this.mSvcPriority = 0;
            this.mTargetName = "";
            return;
        }
        byte[] rdata = getRR();
        if (rdata == null) {
            throw new DnsPacket.ParseException("SVCB rdata is empty");
        }
        ByteBuffer buf = ByteBuffer.wrap(rdata).asReadOnlyBuffer();
        this.mSvcPriority = Short.toUnsignedInt(buf.getShort());
        this.mTargetName = DnsPacketUtils.DnsRecordParser.parseName(buf, 0, false);
        if (this.mTargetName.length() > 255) {
            throw new DnsPacket.ParseException(
                    "Failed to parse SVCB target name, name size is too long: "
                            + this.mTargetName.length());
        }
        while (buf.remaining() >= 4) {
            SvcParam svcParam = parseSvcParam(buf);
            int key = svcParam.getKey();
            if (this.mAllSvcParams.get(key) != null) {
                throw new DnsPacket.ParseException(
                        "Invalid DnsSvcbRecord, key " + key + " is repeated");
            }
            this.mAllSvcParams.put(key, svcParam);
        }
        if (buf.hasRemaining()) {
            throw new DnsPacket.ParseException(
                    "Invalid DnsSvcbRecord. Got "
                            + buf.remaining()
                            + " remaining bytes after parsing");
        }
    }

    public String getTargetName() {
        return this.mTargetName;
    }

    public List<String> getAlpns() {
        SvcParamAlpn sp = (SvcParamAlpn) this.mAllSvcParams.get(1);
        List<String> list = sp != null ? sp.getValue() : Collections.EMPTY_LIST;
        return Collections.unmodifiableList(list);
    }

    public int getPort() {
        SvcParamPort sp = (SvcParamPort) this.mAllSvcParams.get(3);
        if (sp != null) {
            return sp.getValue().intValue();
        }
        return -1;
    }

    public List<InetAddress> getAddresses() {
        List<InetAddress> out = new ArrayList<>();
        SvcParamIpHint sp4 = (SvcParamIpHint) this.mAllSvcParams.get(4);
        if (sp4 != null) {
            out.addAll(sp4.getValue());
        }
        SvcParamIpHint sp6 = (SvcParamIpHint) this.mAllSvcParams.get(6);
        if (sp6 != null) {
            out.addAll(sp6.getValue());
        }
        return out;
    }

    public String getDohPath() {
        SvcParamDohPath sp = (SvcParamDohPath) this.mAllSvcParams.get(7);
        return sp != null ? sp.getValue() : "";
    }

    @Override // com.android.net.module.util.DnsPacket.DnsRecord
    public String toString() {
        if (this.rType == 0) {
            return this.dName + " IN SVCB";
        }
        StringJoiner sj = new StringJoiner(" ");
        for (int i = 0; i < this.mAllSvcParams.size(); i++) {
            sj.add(this.mAllSvcParams.valueAt(i).toString());
        }
        return this.dName
                + " "
                + this.ttl
                + " IN SVCB "
                + this.mSvcPriority
                + " "
                + this.mTargetName
                + " "
                + sj.toString();
    }

    private static SvcParam parseSvcParam(ByteBuffer buf) throws DnsPacket.ParseException {
        try {
            int key = Short.toUnsignedInt(buf.getShort());
            switch (key) {
                case 0:
                    return new SvcParamMandatory(buf);
                case 1:
                    return new SvcParamAlpn(buf);
                case 2:
                    return new SvcParamNoDefaultAlpn(buf);
                case 3:
                    return new SvcParamPort(buf);
                case 4:
                    return new SvcParamIpv4Hint(buf);
                case 5:
                    return new SvcParamEch(buf);
                case 6:
                    return new SvcParamIpv6Hint(buf);
                case 7:
                    return new SvcParamDohPath(buf);
                default:
                    return new SvcParamGeneric(key, buf);
            }
        } catch (BufferUnderflowException e) {
            throw new DnsPacket.ParseException("Malformed packet", e);
        }
    }

    private abstract static class SvcParam<T> {
        private final int mKey;

        abstract T getValue();

        SvcParam(int key) {
            this.mKey = key;
        }

        int getKey() {
            return this.mKey;
        }
    }

    private static class SvcParamMandatory extends SvcParam<short[]> {
        private final short[] mValue;

        private SvcParamMandatory(ByteBuffer buf)
                throws BufferUnderflowException, DnsPacket.ParseException {
            super(0);
            int len = Short.toUnsignedInt(buf.getShort());
            ByteBuffer svcParamValue = DnsSvcbRecord.sliceAndAdvance(buf, len);
            this.mValue = SvcParamValueUtil.toShortArray(svcParamValue);
            if (this.mValue.length == 0) {
                throw new DnsPacket.ParseException("mandatory value must be non-empty");
            }
        }

        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public short[] getValue() {
            return null;
        }

        public String toString() {
            StringJoiner valueJoiner = new StringJoiner(",");
            for (short key : this.mValue) {
                valueJoiner.add(DnsSvcbRecord.toKeyName(key));
            }
            return DnsSvcbRecord.toKeyName(getKey()) + "=" + valueJoiner.toString();
        }
    }

    private static class SvcParamAlpn extends SvcParam<List<String>> {
        private final List<String> mValue;

        SvcParamAlpn(ByteBuffer buf) throws BufferUnderflowException, DnsPacket.ParseException {
            super(1);
            int len = Short.toUnsignedInt(buf.getShort());
            ByteBuffer svcParamValue = DnsSvcbRecord.sliceAndAdvance(buf, len);
            this.mValue = SvcParamValueUtil.toStringList(svcParamValue);
            if (this.mValue.isEmpty()) {
                throw new DnsPacket.ParseException("alpn value must be non-empty");
            }
        }

        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public List<String> getValue() {
            return Collections.unmodifiableList(this.mValue);
        }

        public String toString() {
            return DnsSvcbRecord.toKeyName(getKey()) + "=" + TextUtils.join(",", this.mValue);
        }
    }

    private static class SvcParamNoDefaultAlpn extends SvcParam<Void> {
        SvcParamNoDefaultAlpn(ByteBuffer buf)
                throws BufferUnderflowException, DnsPacket.ParseException {
            super(2);
            int len = buf.getShort();
            if (len != 0) {
                throw new DnsPacket.ParseException("no-default-alpn value must be empty");
            }
        }

        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public Void getValue() {
            return null;
        }

        public String toString() {
            return DnsSvcbRecord.toKeyName(getKey());
        }
    }

    private static class SvcParamPort extends SvcParam<Integer> {
        private final int mValue;

        SvcParamPort(ByteBuffer buf) throws BufferUnderflowException, DnsPacket.ParseException {
            super(3);
            int len = buf.getShort();
            if (len != 2) {
                throw new DnsPacket.ParseException("key port len is not 2 but " + len);
            }
            this.mValue = Short.toUnsignedInt(buf.getShort());
        }

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

        private SvcParamIpHint(int key, ByteBuffer buf, int addrLen)
                throws BufferUnderflowException, DnsPacket.ParseException {
            super(key);
            int len = Short.toUnsignedInt(buf.getShort());
            ByteBuffer svcParamValue = DnsSvcbRecord.sliceAndAdvance(buf, len);
            this.mValue = SvcParamValueUtil.toInetAddressList(svcParamValue, addrLen);
            if (this.mValue.isEmpty()) {
                throw new DnsPacket.ParseException(
                        DnsSvcbRecord.toKeyName(getKey()) + " value must be non-empty");
            }
        }

        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public List<InetAddress> getValue() {
            return Collections.unmodifiableList(this.mValue);
        }

        public String toString() {
            StringJoiner valueJoiner = new StringJoiner(",");
            for (InetAddress ip : this.mValue) {
                valueJoiner.add(ip.getHostAddress());
            }
            return DnsSvcbRecord.toKeyName(getKey()) + "=" + valueJoiner.toString();
        }
    }

    private static class SvcParamIpv4Hint extends SvcParamIpHint {
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        SvcParamIpv4Hint(java.nio.ByteBuffer r3)
                throws java.nio.BufferUnderflowException,
                        com.android.net.module.util.DnsPacket.ParseException {
            /*
                r2 = this;
                r0 = 4
                r1 = 0
                r2.<init>(r0, r3, r0)
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.net.module.util.DnsSvcbRecord.SvcParamIpv4Hint.<init>(java.nio.ByteBuffer):void");
        }
    }

    private static class SvcParamIpv6Hint extends SvcParamIpHint {
        SvcParamIpv6Hint(ByteBuffer buf) throws BufferUnderflowException, DnsPacket.ParseException {
            super(6, buf, 16);
        }
    }

    private static class SvcParamEch extends SvcParamGeneric {
        SvcParamEch(ByteBuffer buf) throws BufferUnderflowException, DnsPacket.ParseException {
            super(5, buf);
        }
    }

    private static class SvcParamDohPath extends SvcParam<String> {
        private final String mValue;

        SvcParamDohPath(ByteBuffer buf) throws BufferUnderflowException, DnsPacket.ParseException {
            super(7);
            int len = Short.toUnsignedInt(buf.getShort());
            byte[] value = new byte[len];
            buf.get(value);
            this.mValue = new String(value, StandardCharsets.UTF_8);
        }

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

        SvcParamGeneric(int key, ByteBuffer buf)
                throws BufferUnderflowException, DnsPacket.ParseException {
            super(key);
            int len = Short.toUnsignedInt(buf.getShort());
            this.mValue = new byte[len];
            buf.get(this.mValue);
        }

        @Override // com.android.net.module.util.DnsSvcbRecord.SvcParam
        public byte[] getValue() {
            return null;
        }

        public String toString() {
            StringBuilder out = new StringBuilder();
            out.append(DnsSvcbRecord.toKeyName(getKey()));
            if (this.mValue != null && this.mValue.length > 0) {
                out.append("=");
                out.append(HexDump.toHexString(this.mValue));
            }
            return out.toString();
        }
    }

    public static String toKeyName(int key) {
        switch (key) {
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
                return "key" + key;
        }
    }

    public static ByteBuffer sliceAndAdvance(ByteBuffer buf, int length)
            throws BufferUnderflowException {
        if (buf.remaining() < length) {
            throw new BufferUnderflowException();
        }
        int pos = buf.position();
        ByteBuffer out = ((ByteBuffer) buf.slice().limit(length)).slice();
        buf.position(pos + length);
        return out.asReadOnlyBuffer();
    }

    private static class SvcParamValueUtil {
        private SvcParamValueUtil() {}

        public static List<String> toStringList(ByteBuffer buf)
                throws BufferUnderflowException, DnsPacket.ParseException {
            List<String> out = new ArrayList<>();
            while (buf.hasRemaining()) {
                int alpnLen = Byte.toUnsignedInt(buf.get());
                if (alpnLen == 0) {
                    throw new DnsPacket.ParseException("alpn should not be an empty string");
                }
                byte[] alpn = new byte[alpnLen];
                buf.get(alpn);
                out.add(new String(alpn, StandardCharsets.UTF_8));
            }
            return out;
        }

        public static short[] toShortArray(ByteBuffer buf)
                throws BufferUnderflowException, DnsPacket.ParseException {
            if (buf.remaining() % 2 != 0) {
                throw new DnsPacket.ParseException("Can't parse whole byte array");
            }
            ShortBuffer sb = buf.asShortBuffer();
            short[] out = new short[sb.remaining()];
            sb.get(out);
            return out;
        }

        public static List<InetAddress> toInetAddressList(ByteBuffer buf, int addrLen)
                throws BufferUnderflowException, DnsPacket.ParseException {
            if (buf.remaining() % addrLen != 0) {
                throw new DnsPacket.ParseException("Can't parse whole byte array");
            }
            List<InetAddress> out = new ArrayList<>();
            byte[] addr = new byte[addrLen];
            while (buf.remaining() >= addrLen) {
                buf.get(addr);
                try {
                    out.add(InetAddress.getByAddress(addr));
                } catch (UnknownHostException e) {
                    throw new DnsPacket.ParseException("Can't parse byte array as an IP address");
                }
            }
            return out;
        }
    }
}
