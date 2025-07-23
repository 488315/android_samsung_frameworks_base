package com.android.server.connectivity.metrics.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes6.dex */
public interface IpConnectivityLogClass {
    public static final int BLUETOOTH = 1;
    public static final int CELLULAR = 2;
    public static final int ETHERNET = 3;
    public static final int LOWPAN = 9;
    public static final int MULTIPLE = 6;
    public static final int NONE = 5;
    public static final int UNKNOWN = 0;
    public static final int WIFI = 4;
    public static final int WIFI_NAN = 8;
    public static final int WIFI_P2P = 7;

    public static final class NetworkId extends MessageNano {
        private static volatile NetworkId[] _emptyArray;
        public int networkId;

        public static NetworkId[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new NetworkId[0];
                    }
                }
            }
            return _emptyArray;
        }

        public NetworkId() {
            clear();
        }

        public NetworkId clear() {
            this.networkId = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.networkId;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            int i = this.networkId;
            return i != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(1, i) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public NetworkId mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag != 8) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.networkId = codedInputByteBufferNano.readInt32();
                }
            }
            return this;
        }

        public static NetworkId parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (NetworkId) MessageNano.mergeFrom(new NetworkId(), bArr);
        }

        public static NetworkId parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new NetworkId().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class Pair extends MessageNano {
        private static volatile Pair[] _emptyArray;
        public int key;
        public int value;

        public static Pair[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new Pair[0];
                    }
                }
            }
            return _emptyArray;
        }

        public Pair() {
            clear();
        }

        public Pair clear() {
            this.key = 0;
            this.value = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.key;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            int i2 = this.value;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(2, i2);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            int i = this.key;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
            }
            int i2 = this.value;
            return i2 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(2, i2) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public Pair mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 8) {
                    this.key = codedInputByteBufferNano.readInt32();
                } else if (readTag != 16) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.value = codedInputByteBufferNano.readInt32();
                }
            }
            return this;
        }

        public static Pair parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (Pair) MessageNano.mergeFrom(new Pair(), bArr);
        }

        public static Pair parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new Pair().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class DefaultNetworkEvent extends MessageNano {
        public static final int DISCONNECT = 3;
        public static final int DUAL = 3;
        public static final int INVALIDATION = 2;
        public static final int IPV4 = 1;
        public static final int IPV6 = 2;
        public static final int NONE = 0;
        public static final int OUTSCORED = 1;
        public static final int UNKNOWN = 0;
        private static volatile DefaultNetworkEvent[] _emptyArray;
        public long defaultNetworkDurationMs;
        public long finalScore;
        public long initialScore;
        public int ipSupport;
        public NetworkId networkId;
        public long noDefaultNetworkDurationMs;
        public int previousDefaultNetworkLinkLayer;
        public NetworkId previousNetworkId;
        public int previousNetworkIpSupport;
        public int[] transportTypes;
        public long validationDurationMs;

        public static DefaultNetworkEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new DefaultNetworkEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public DefaultNetworkEvent() {
            clear();
        }

        public DefaultNetworkEvent clear() {
            this.defaultNetworkDurationMs = 0L;
            this.validationDurationMs = 0L;
            this.initialScore = 0L;
            this.finalScore = 0L;
            this.ipSupport = 0;
            this.previousDefaultNetworkLinkLayer = 0;
            this.networkId = null;
            this.previousNetworkId = null;
            this.previousNetworkIpSupport = 0;
            this.transportTypes = WireFormatNano.EMPTY_INT_ARRAY;
            this.noDefaultNetworkDurationMs = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            NetworkId networkId = this.networkId;
            if (networkId != null) {
                codedOutputByteBufferNano.writeMessage(1, networkId);
            }
            NetworkId networkId2 = this.previousNetworkId;
            if (networkId2 != null) {
                codedOutputByteBufferNano.writeMessage(2, networkId2);
            }
            int i = this.previousNetworkIpSupport;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(3, i);
            }
            int[] iArr = this.transportTypes;
            if (iArr != null && iArr.length > 0) {
                int i2 = 0;
                while (true) {
                    int[] iArr2 = this.transportTypes;
                    if (i2 >= iArr2.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeInt32(4, iArr2[i2]);
                    i2++;
                }
            }
            long j = this.defaultNetworkDurationMs;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(5, j);
            }
            long j2 = this.noDefaultNetworkDurationMs;
            if (j2 != 0) {
                codedOutputByteBufferNano.writeInt64(6, j2);
            }
            long j3 = this.initialScore;
            if (j3 != 0) {
                codedOutputByteBufferNano.writeInt64(7, j3);
            }
            long j4 = this.finalScore;
            if (j4 != 0) {
                codedOutputByteBufferNano.writeInt64(8, j4);
            }
            int i3 = this.ipSupport;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(9, i3);
            }
            int i4 = this.previousDefaultNetworkLinkLayer;
            if (i4 != 0) {
                codedOutputByteBufferNano.writeInt32(10, i4);
            }
            long j5 = this.validationDurationMs;
            if (j5 != 0) {
                codedOutputByteBufferNano.writeInt64(11, j5);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int[] iArr;
            int computeSerializedSize = super.computeSerializedSize();
            NetworkId networkId = this.networkId;
            if (networkId != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, networkId);
            }
            NetworkId networkId2 = this.previousNetworkId;
            if (networkId2 != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, networkId2);
            }
            int i = this.previousNetworkIpSupport;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i);
            }
            int[] iArr2 = this.transportTypes;
            if (iArr2 != null && iArr2.length > 0) {
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    iArr = this.transportTypes;
                    if (i2 >= iArr.length) {
                        break;
                    }
                    i3 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr[i2]);
                    i2++;
                }
                computeSerializedSize = computeSerializedSize + i3 + iArr.length;
            }
            long j = this.defaultNetworkDurationMs;
            if (j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(5, j);
            }
            long j2 = this.noDefaultNetworkDurationMs;
            if (j2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(6, j2);
            }
            long j3 = this.initialScore;
            if (j3 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(7, j3);
            }
            long j4 = this.finalScore;
            if (j4 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(8, j4);
            }
            int i4 = this.ipSupport;
            if (i4 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(9, i4);
            }
            int i5 = this.previousDefaultNetworkLinkLayer;
            if (i5 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(10, i5);
            }
            long j5 = this.validationDurationMs;
            return j5 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt64Size(11, j5) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public DefaultNetworkEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        break;
                    case 10:
                        if (this.networkId == null) {
                            this.networkId = new NetworkId();
                        }
                        codedInputByteBufferNano.readMessage(this.networkId);
                        break;
                    case 18:
                        if (this.previousNetworkId == null) {
                            this.previousNetworkId = new NetworkId();
                        }
                        codedInputByteBufferNano.readMessage(this.previousNetworkId);
                        break;
                    case 24:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        if (readInt32 != 0 && readInt32 != 1 && readInt32 != 2 && readInt32 != 3) {
                            break;
                        } else {
                            this.previousNetworkIpSupport = readInt32;
                            break;
                        }
                    case 32:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 32);
                        int[] iArr = this.transportTypes;
                        int length = iArr == null ? 0 : iArr.length;
                        int i = repeatedFieldArrayLength + length;
                        int[] iArr2 = new int[i];
                        if (length != 0) {
                            System.arraycopy(iArr, 0, iArr2, 0, length);
                        }
                        while (length < i - 1) {
                            iArr2[length] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        iArr2[length] = codedInputByteBufferNano.readInt32();
                        this.transportTypes = iArr2;
                        break;
                    case 34:
                        int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i2 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i2++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        int[] iArr3 = this.transportTypes;
                        int length2 = iArr3 == null ? 0 : iArr3.length;
                        int i3 = i2 + length2;
                        int[] iArr4 = new int[i3];
                        if (length2 != 0) {
                            System.arraycopy(iArr3, 0, iArr4, 0, length2);
                        }
                        while (length2 < i3) {
                            iArr4[length2] = codedInputByteBufferNano.readInt32();
                            length2++;
                        }
                        this.transportTypes = iArr4;
                        codedInputByteBufferNano.popLimit(pushLimit);
                        break;
                    case 40:
                        this.defaultNetworkDurationMs = codedInputByteBufferNano.readInt64();
                        break;
                    case 48:
                        this.noDefaultNetworkDurationMs = codedInputByteBufferNano.readInt64();
                        break;
                    case 56:
                        this.initialScore = codedInputByteBufferNano.readInt64();
                        break;
                    case 64:
                        this.finalScore = codedInputByteBufferNano.readInt64();
                        break;
                    case 72:
                        int readInt322 = codedInputByteBufferNano.readInt32();
                        if (readInt322 != 0 && readInt322 != 1 && readInt322 != 2 && readInt322 != 3) {
                            break;
                        } else {
                            this.ipSupport = readInt322;
                            break;
                        }
                    case 80:
                        int readInt323 = codedInputByteBufferNano.readInt32();
                        switch (readInt323) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                                this.previousDefaultNetworkLinkLayer = readInt323;
                                break;
                        }
                    case 88:
                        this.validationDurationMs = codedInputByteBufferNano.readInt64();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            break;
                        }
                }
            }
            return this;
        }

        public static DefaultNetworkEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (DefaultNetworkEvent) MessageNano.mergeFrom(new DefaultNetworkEvent(), bArr);
        }

        public static DefaultNetworkEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new DefaultNetworkEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class IpReachabilityEvent extends MessageNano {
        private static volatile IpReachabilityEvent[] _emptyArray;
        public int eventType;
        public String ifName;

        public static IpReachabilityEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new IpReachabilityEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public IpReachabilityEvent() {
            clear();
        }

        public IpReachabilityEvent clear() {
            this.ifName = "";
            this.eventType = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.ifName);
            }
            int i = this.eventType;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(2, i);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.ifName.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.ifName);
            }
            int i = this.eventType;
            return i != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(2, i) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public IpReachabilityEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    this.ifName = codedInputByteBufferNano.readString();
                } else if (readTag != 16) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.eventType = codedInputByteBufferNano.readInt32();
                }
            }
            return this;
        }

        public static IpReachabilityEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (IpReachabilityEvent) MessageNano.mergeFrom(new IpReachabilityEvent(), bArr);
        }

        public static IpReachabilityEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new IpReachabilityEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class NetworkEvent extends MessageNano {
        private static volatile NetworkEvent[] _emptyArray;
        public int eventType;
        public int latencyMs;
        public NetworkId networkId;

        public static NetworkEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new NetworkEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public NetworkEvent() {
            clear();
        }

        public NetworkEvent clear() {
            this.networkId = null;
            this.eventType = 0;
            this.latencyMs = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            NetworkId networkId = this.networkId;
            if (networkId != null) {
                codedOutputByteBufferNano.writeMessage(1, networkId);
            }
            int i = this.eventType;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(2, i);
            }
            int i2 = this.latencyMs;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i2);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            NetworkId networkId = this.networkId;
            if (networkId != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, networkId);
            }
            int i = this.eventType;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i);
            }
            int i2 = this.latencyMs;
            return i2 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(3, i2) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public NetworkEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    if (this.networkId == null) {
                        this.networkId = new NetworkId();
                    }
                    codedInputByteBufferNano.readMessage(this.networkId);
                } else if (readTag == 16) {
                    this.eventType = codedInputByteBufferNano.readInt32();
                } else if (readTag != 24) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.latencyMs = codedInputByteBufferNano.readInt32();
                }
            }
            return this;
        }

        public static NetworkEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (NetworkEvent) MessageNano.mergeFrom(new NetworkEvent(), bArr);
        }

        public static NetworkEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new NetworkEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ValidationProbeEvent extends MessageNano {
        private static volatile ValidationProbeEvent[] _emptyArray;
        public int latencyMs;
        public NetworkId networkId;
        public int probeResult;
        public int probeType;

        public static ValidationProbeEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new ValidationProbeEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ValidationProbeEvent() {
            clear();
        }

        public ValidationProbeEvent clear() {
            this.networkId = null;
            this.latencyMs = 0;
            this.probeType = 0;
            this.probeResult = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            NetworkId networkId = this.networkId;
            if (networkId != null) {
                codedOutputByteBufferNano.writeMessage(1, networkId);
            }
            int i = this.latencyMs;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(2, i);
            }
            int i2 = this.probeType;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i2);
            }
            int i3 = this.probeResult;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i3);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            NetworkId networkId = this.networkId;
            if (networkId != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, networkId);
            }
            int i = this.latencyMs;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i);
            }
            int i2 = this.probeType;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i2);
            }
            int i3 = this.probeResult;
            return i3 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(4, i3) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public ValidationProbeEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    if (this.networkId == null) {
                        this.networkId = new NetworkId();
                    }
                    codedInputByteBufferNano.readMessage(this.networkId);
                } else if (readTag == 16) {
                    this.latencyMs = codedInputByteBufferNano.readInt32();
                } else if (readTag == 24) {
                    this.probeType = codedInputByteBufferNano.readInt32();
                } else if (readTag != 32) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.probeResult = codedInputByteBufferNano.readInt32();
                }
            }
            return this;
        }

        public static ValidationProbeEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (ValidationProbeEvent) MessageNano.mergeFrom(new ValidationProbeEvent(), bArr);
        }

        public static ValidationProbeEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ValidationProbeEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class DNSLookupBatch extends MessageNano {
        private static volatile DNSLookupBatch[] _emptyArray;
        public int[] eventTypes;
        public long getaddrinfoErrorCount;
        public Pair[] getaddrinfoErrors;
        public long getaddrinfoQueryCount;
        public long gethostbynameErrorCount;
        public Pair[] gethostbynameErrors;
        public long gethostbynameQueryCount;
        public int[] latenciesMs;
        public NetworkId networkId;
        public int[] returnCodes;

        public static DNSLookupBatch[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new DNSLookupBatch[0];
                    }
                }
            }
            return _emptyArray;
        }

        public DNSLookupBatch() {
            clear();
        }

        public DNSLookupBatch clear() {
            this.latenciesMs = WireFormatNano.EMPTY_INT_ARRAY;
            this.getaddrinfoQueryCount = 0L;
            this.gethostbynameQueryCount = 0L;
            this.getaddrinfoErrorCount = 0L;
            this.gethostbynameErrorCount = 0L;
            this.getaddrinfoErrors = Pair.emptyArray();
            this.gethostbynameErrors = Pair.emptyArray();
            this.networkId = null;
            this.eventTypes = WireFormatNano.EMPTY_INT_ARRAY;
            this.returnCodes = WireFormatNano.EMPTY_INT_ARRAY;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            NetworkId networkId = this.networkId;
            if (networkId != null) {
                codedOutputByteBufferNano.writeMessage(1, networkId);
            }
            int[] iArr = this.eventTypes;
            int i = 0;
            if (iArr != null && iArr.length > 0) {
                int i2 = 0;
                while (true) {
                    int[] iArr2 = this.eventTypes;
                    if (i2 >= iArr2.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeInt32(2, iArr2[i2]);
                    i2++;
                }
            }
            int[] iArr3 = this.returnCodes;
            if (iArr3 != null && iArr3.length > 0) {
                int i3 = 0;
                while (true) {
                    int[] iArr4 = this.returnCodes;
                    if (i3 >= iArr4.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeInt32(3, iArr4[i3]);
                    i3++;
                }
            }
            int[] iArr5 = this.latenciesMs;
            if (iArr5 != null && iArr5.length > 0) {
                int i4 = 0;
                while (true) {
                    int[] iArr6 = this.latenciesMs;
                    if (i4 >= iArr6.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeInt32(4, iArr6[i4]);
                    i4++;
                }
            }
            long j = this.getaddrinfoQueryCount;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(5, j);
            }
            long j2 = this.gethostbynameQueryCount;
            if (j2 != 0) {
                codedOutputByteBufferNano.writeInt64(6, j2);
            }
            long j3 = this.getaddrinfoErrorCount;
            if (j3 != 0) {
                codedOutputByteBufferNano.writeInt64(7, j3);
            }
            long j4 = this.gethostbynameErrorCount;
            if (j4 != 0) {
                codedOutputByteBufferNano.writeInt64(8, j4);
            }
            Pair[] pairArr = this.getaddrinfoErrors;
            if (pairArr != null && pairArr.length > 0) {
                int i5 = 0;
                while (true) {
                    Pair[] pairArr2 = this.getaddrinfoErrors;
                    if (i5 >= pairArr2.length) {
                        break;
                    }
                    Pair pair = pairArr2[i5];
                    if (pair != null) {
                        codedOutputByteBufferNano.writeMessage(9, pair);
                    }
                    i5++;
                }
            }
            Pair[] pairArr3 = this.gethostbynameErrors;
            if (pairArr3 != null && pairArr3.length > 0) {
                while (true) {
                    Pair[] pairArr4 = this.gethostbynameErrors;
                    if (i >= pairArr4.length) {
                        break;
                    }
                    Pair pair2 = pairArr4[i];
                    if (pair2 != null) {
                        codedOutputByteBufferNano.writeMessage(10, pair2);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int[] iArr;
            int[] iArr2;
            int[] iArr3;
            int computeSerializedSize = super.computeSerializedSize();
            NetworkId networkId = this.networkId;
            if (networkId != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, networkId);
            }
            int[] iArr4 = this.eventTypes;
            int i = 0;
            if (iArr4 != null && iArr4.length > 0) {
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    iArr3 = this.eventTypes;
                    if (i2 >= iArr3.length) {
                        break;
                    }
                    i3 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr3[i2]);
                    i2++;
                }
                computeSerializedSize = computeSerializedSize + i3 + iArr3.length;
            }
            int[] iArr5 = this.returnCodes;
            if (iArr5 != null && iArr5.length > 0) {
                int i4 = 0;
                int i5 = 0;
                while (true) {
                    iArr2 = this.returnCodes;
                    if (i4 >= iArr2.length) {
                        break;
                    }
                    i5 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr2[i4]);
                    i4++;
                }
                computeSerializedSize = computeSerializedSize + i5 + iArr2.length;
            }
            int[] iArr6 = this.latenciesMs;
            if (iArr6 != null && iArr6.length > 0) {
                int i6 = 0;
                int i7 = 0;
                while (true) {
                    iArr = this.latenciesMs;
                    if (i6 >= iArr.length) {
                        break;
                    }
                    i7 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr[i6]);
                    i6++;
                }
                computeSerializedSize = computeSerializedSize + i7 + iArr.length;
            }
            long j = this.getaddrinfoQueryCount;
            if (j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(5, j);
            }
            long j2 = this.gethostbynameQueryCount;
            if (j2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(6, j2);
            }
            long j3 = this.getaddrinfoErrorCount;
            if (j3 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(7, j3);
            }
            long j4 = this.gethostbynameErrorCount;
            if (j4 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(8, j4);
            }
            Pair[] pairArr = this.getaddrinfoErrors;
            if (pairArr != null && pairArr.length > 0) {
                int i8 = 0;
                while (true) {
                    Pair[] pairArr2 = this.getaddrinfoErrors;
                    if (i8 >= pairArr2.length) {
                        break;
                    }
                    Pair pair = pairArr2[i8];
                    if (pair != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(9, pair);
                    }
                    i8++;
                }
            }
            Pair[] pairArr3 = this.gethostbynameErrors;
            if (pairArr3 != null && pairArr3.length > 0) {
                while (true) {
                    Pair[] pairArr4 = this.gethostbynameErrors;
                    if (i >= pairArr4.length) {
                        break;
                    }
                    Pair pair2 = pairArr4[i];
                    if (pair2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(10, pair2);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public DNSLookupBatch mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        break;
                    case 10:
                        if (this.networkId == null) {
                            this.networkId = new NetworkId();
                        }
                        codedInputByteBufferNano.readMessage(this.networkId);
                        break;
                    case 16:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 16);
                        int[] iArr = this.eventTypes;
                        int length = iArr == null ? 0 : iArr.length;
                        int i = repeatedFieldArrayLength + length;
                        int[] iArr2 = new int[i];
                        if (length != 0) {
                            System.arraycopy(iArr, 0, iArr2, 0, length);
                        }
                        while (length < i - 1) {
                            iArr2[length] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        iArr2[length] = codedInputByteBufferNano.readInt32();
                        this.eventTypes = iArr2;
                        break;
                    case 18:
                        int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i2 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i2++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        int[] iArr3 = this.eventTypes;
                        int length2 = iArr3 == null ? 0 : iArr3.length;
                        int i3 = i2 + length2;
                        int[] iArr4 = new int[i3];
                        if (length2 != 0) {
                            System.arraycopy(iArr3, 0, iArr4, 0, length2);
                        }
                        while (length2 < i3) {
                            iArr4[length2] = codedInputByteBufferNano.readInt32();
                            length2++;
                        }
                        this.eventTypes = iArr4;
                        codedInputByteBufferNano.popLimit(pushLimit);
                        break;
                    case 24:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 24);
                        int[] iArr5 = this.returnCodes;
                        int length3 = iArr5 == null ? 0 : iArr5.length;
                        int i4 = repeatedFieldArrayLength2 + length3;
                        int[] iArr6 = new int[i4];
                        if (length3 != 0) {
                            System.arraycopy(iArr5, 0, iArr6, 0, length3);
                        }
                        while (length3 < i4 - 1) {
                            iArr6[length3] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        iArr6[length3] = codedInputByteBufferNano.readInt32();
                        this.returnCodes = iArr6;
                        break;
                    case 26:
                        int pushLimit2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position2 = codedInputByteBufferNano.getPosition();
                        int i5 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i5++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position2);
                        int[] iArr7 = this.returnCodes;
                        int length4 = iArr7 == null ? 0 : iArr7.length;
                        int i6 = i5 + length4;
                        int[] iArr8 = new int[i6];
                        if (length4 != 0) {
                            System.arraycopy(iArr7, 0, iArr8, 0, length4);
                        }
                        while (length4 < i6) {
                            iArr8[length4] = codedInputByteBufferNano.readInt32();
                            length4++;
                        }
                        this.returnCodes = iArr8;
                        codedInputByteBufferNano.popLimit(pushLimit2);
                        break;
                    case 32:
                        int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 32);
                        int[] iArr9 = this.latenciesMs;
                        int length5 = iArr9 == null ? 0 : iArr9.length;
                        int i7 = repeatedFieldArrayLength3 + length5;
                        int[] iArr10 = new int[i7];
                        if (length5 != 0) {
                            System.arraycopy(iArr9, 0, iArr10, 0, length5);
                        }
                        while (length5 < i7 - 1) {
                            iArr10[length5] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            length5++;
                        }
                        iArr10[length5] = codedInputByteBufferNano.readInt32();
                        this.latenciesMs = iArr10;
                        break;
                    case 34:
                        int pushLimit3 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position3 = codedInputByteBufferNano.getPosition();
                        int i8 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            i8++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position3);
                        int[] iArr11 = this.latenciesMs;
                        int length6 = iArr11 == null ? 0 : iArr11.length;
                        int i9 = i8 + length6;
                        int[] iArr12 = new int[i9];
                        if (length6 != 0) {
                            System.arraycopy(iArr11, 0, iArr12, 0, length6);
                        }
                        while (length6 < i9) {
                            iArr12[length6] = codedInputByteBufferNano.readInt32();
                            length6++;
                        }
                        this.latenciesMs = iArr12;
                        codedInputByteBufferNano.popLimit(pushLimit3);
                        break;
                    case 40:
                        this.getaddrinfoQueryCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 48:
                        this.gethostbynameQueryCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 56:
                        this.getaddrinfoErrorCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 64:
                        this.gethostbynameErrorCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 74:
                        int repeatedFieldArrayLength4 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                        Pair[] pairArr = this.getaddrinfoErrors;
                        int length7 = pairArr == null ? 0 : pairArr.length;
                        int i10 = repeatedFieldArrayLength4 + length7;
                        Pair[] pairArr2 = new Pair[i10];
                        if (length7 != 0) {
                            System.arraycopy(pairArr, 0, pairArr2, 0, length7);
                        }
                        while (length7 < i10 - 1) {
                            Pair pair = new Pair();
                            pairArr2[length7] = pair;
                            codedInputByteBufferNano.readMessage(pair);
                            codedInputByteBufferNano.readTag();
                            length7++;
                        }
                        Pair pair2 = new Pair();
                        pairArr2[length7] = pair2;
                        codedInputByteBufferNano.readMessage(pair2);
                        this.getaddrinfoErrors = pairArr2;
                        break;
                    case 82:
                        int repeatedFieldArrayLength5 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 82);
                        Pair[] pairArr3 = this.gethostbynameErrors;
                        int length8 = pairArr3 == null ? 0 : pairArr3.length;
                        int i11 = repeatedFieldArrayLength5 + length8;
                        Pair[] pairArr4 = new Pair[i11];
                        if (length8 != 0) {
                            System.arraycopy(pairArr3, 0, pairArr4, 0, length8);
                        }
                        while (length8 < i11 - 1) {
                            Pair pair3 = new Pair();
                            pairArr4[length8] = pair3;
                            codedInputByteBufferNano.readMessage(pair3);
                            codedInputByteBufferNano.readTag();
                            length8++;
                        }
                        Pair pair4 = new Pair();
                        pairArr4[length8] = pair4;
                        codedInputByteBufferNano.readMessage(pair4);
                        this.gethostbynameErrors = pairArr4;
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            break;
                        }
                }
            }
            return this;
        }

        public static DNSLookupBatch parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (DNSLookupBatch) MessageNano.mergeFrom(new DNSLookupBatch(), bArr);
        }

        public static DNSLookupBatch parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new DNSLookupBatch().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class DNSLatencies extends MessageNano {
        private static volatile DNSLatencies[] _emptyArray;
        public int aCount;
        public int aaaaCount;
        public int[] latenciesMs;
        public int queryCount;
        public int returnCode;
        public int type;

        public static DNSLatencies[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new DNSLatencies[0];
                    }
                }
            }
            return _emptyArray;
        }

        public DNSLatencies() {
            clear();
        }

        public DNSLatencies clear() {
            this.type = 0;
            this.returnCode = 0;
            this.queryCount = 0;
            this.aCount = 0;
            this.aaaaCount = 0;
            this.latenciesMs = WireFormatNano.EMPTY_INT_ARRAY;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.type;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            int i2 = this.returnCode;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(2, i2);
            }
            int i3 = this.queryCount;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i3);
            }
            int i4 = this.aCount;
            if (i4 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i4);
            }
            int i5 = this.aaaaCount;
            if (i5 != 0) {
                codedOutputByteBufferNano.writeInt32(5, i5);
            }
            int[] iArr = this.latenciesMs;
            if (iArr != null && iArr.length > 0) {
                int i6 = 0;
                while (true) {
                    int[] iArr2 = this.latenciesMs;
                    if (i6 >= iArr2.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeInt32(6, iArr2[i6]);
                    i6++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            int i = this.type;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
            }
            int i2 = this.returnCode;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i2);
            }
            int i3 = this.queryCount;
            if (i3 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i3);
            }
            int i4 = this.aCount;
            if (i4 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, i4);
            }
            int i5 = this.aaaaCount;
            if (i5 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, i5);
            }
            int[] iArr = this.latenciesMs;
            if (iArr == null || iArr.length <= 0) {
                return computeSerializedSize;
            }
            int i6 = 0;
            int i7 = 0;
            while (true) {
                int[] iArr2 = this.latenciesMs;
                if (i6 < iArr2.length) {
                    i7 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr2[i6]);
                    i6++;
                } else {
                    return computeSerializedSize + i7 + iArr2.length;
                }
            }
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public DNSLatencies mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 8) {
                    this.type = codedInputByteBufferNano.readInt32();
                } else if (readTag == 16) {
                    this.returnCode = codedInputByteBufferNano.readInt32();
                } else if (readTag == 24) {
                    this.queryCount = codedInputByteBufferNano.readInt32();
                } else if (readTag == 32) {
                    this.aCount = codedInputByteBufferNano.readInt32();
                } else if (readTag == 40) {
                    this.aaaaCount = codedInputByteBufferNano.readInt32();
                } else if (readTag == 48) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 48);
                    int[] iArr = this.latenciesMs;
                    int length = iArr == null ? 0 : iArr.length;
                    int i = repeatedFieldArrayLength + length;
                    int[] iArr2 = new int[i];
                    if (length != 0) {
                        System.arraycopy(iArr, 0, iArr2, 0, length);
                    }
                    while (length < i - 1) {
                        iArr2[length] = codedInputByteBufferNano.readInt32();
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    iArr2[length] = codedInputByteBufferNano.readInt32();
                    this.latenciesMs = iArr2;
                } else if (readTag != 50) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                    int position = codedInputByteBufferNano.getPosition();
                    int i2 = 0;
                    while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                        codedInputByteBufferNano.readInt32();
                        i2++;
                    }
                    codedInputByteBufferNano.rewindToPosition(position);
                    int[] iArr3 = this.latenciesMs;
                    int length2 = iArr3 == null ? 0 : iArr3.length;
                    int i3 = i2 + length2;
                    int[] iArr4 = new int[i3];
                    if (length2 != 0) {
                        System.arraycopy(iArr3, 0, iArr4, 0, length2);
                    }
                    while (length2 < i3) {
                        iArr4[length2] = codedInputByteBufferNano.readInt32();
                        length2++;
                    }
                    this.latenciesMs = iArr4;
                    codedInputByteBufferNano.popLimit(pushLimit);
                }
            }
            return this;
        }

        public static DNSLatencies parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (DNSLatencies) MessageNano.mergeFrom(new DNSLatencies(), bArr);
        }

        public static DNSLatencies parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new DNSLatencies().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ConnectStatistics extends MessageNano {
        private static volatile ConnectStatistics[] _emptyArray;
        public int connectBlockingCount;
        public int connectCount;
        public Pair[] errnosCounters;
        public int ipv6AddrCount;
        public int[] latenciesMs;
        public int[] nonBlockingLatenciesMs;

        public static ConnectStatistics[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new ConnectStatistics[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ConnectStatistics() {
            clear();
        }

        public ConnectStatistics clear() {
            this.connectCount = 0;
            this.connectBlockingCount = 0;
            this.ipv6AddrCount = 0;
            this.latenciesMs = WireFormatNano.EMPTY_INT_ARRAY;
            this.nonBlockingLatenciesMs = WireFormatNano.EMPTY_INT_ARRAY;
            this.errnosCounters = Pair.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.connectCount;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            int i2 = this.ipv6AddrCount;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(2, i2);
            }
            int[] iArr = this.latenciesMs;
            int i3 = 0;
            if (iArr != null && iArr.length > 0) {
                int i4 = 0;
                while (true) {
                    int[] iArr2 = this.latenciesMs;
                    if (i4 >= iArr2.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeInt32(3, iArr2[i4]);
                    i4++;
                }
            }
            Pair[] pairArr = this.errnosCounters;
            if (pairArr != null && pairArr.length > 0) {
                int i5 = 0;
                while (true) {
                    Pair[] pairArr2 = this.errnosCounters;
                    if (i5 >= pairArr2.length) {
                        break;
                    }
                    Pair pair = pairArr2[i5];
                    if (pair != null) {
                        codedOutputByteBufferNano.writeMessage(4, pair);
                    }
                    i5++;
                }
            }
            int i6 = this.connectBlockingCount;
            if (i6 != 0) {
                codedOutputByteBufferNano.writeInt32(5, i6);
            }
            int[] iArr3 = this.nonBlockingLatenciesMs;
            if (iArr3 != null && iArr3.length > 0) {
                while (true) {
                    int[] iArr4 = this.nonBlockingLatenciesMs;
                    if (i3 >= iArr4.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeInt32(6, iArr4[i3]);
                    i3++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int[] iArr;
            int computeSerializedSize = super.computeSerializedSize();
            int i = this.connectCount;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
            }
            int i2 = this.ipv6AddrCount;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i2);
            }
            int[] iArr2 = this.latenciesMs;
            int i3 = 0;
            if (iArr2 != null && iArr2.length > 0) {
                int i4 = 0;
                int i5 = 0;
                while (true) {
                    iArr = this.latenciesMs;
                    if (i4 >= iArr.length) {
                        break;
                    }
                    i5 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr[i4]);
                    i4++;
                }
                computeSerializedSize = computeSerializedSize + i5 + iArr.length;
            }
            Pair[] pairArr = this.errnosCounters;
            if (pairArr != null && pairArr.length > 0) {
                int i6 = 0;
                while (true) {
                    Pair[] pairArr2 = this.errnosCounters;
                    if (i6 >= pairArr2.length) {
                        break;
                    }
                    Pair pair = pairArr2[i6];
                    if (pair != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, pair);
                    }
                    i6++;
                }
            }
            int i7 = this.connectBlockingCount;
            if (i7 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, i7);
            }
            int[] iArr3 = this.nonBlockingLatenciesMs;
            if (iArr3 == null || iArr3.length <= 0) {
                return computeSerializedSize;
            }
            int i8 = 0;
            while (true) {
                int[] iArr4 = this.nonBlockingLatenciesMs;
                if (i3 < iArr4.length) {
                    i8 += CodedOutputByteBufferNano.computeInt32SizeNoTag(iArr4[i3]);
                    i3++;
                } else {
                    return computeSerializedSize + i8 + iArr4.length;
                }
            }
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public ConnectStatistics mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 8) {
                    this.connectCount = codedInputByteBufferNano.readInt32();
                } else if (readTag == 16) {
                    this.ipv6AddrCount = codedInputByteBufferNano.readInt32();
                } else if (readTag == 24) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 24);
                    int[] iArr = this.latenciesMs;
                    int length = iArr == null ? 0 : iArr.length;
                    int i = repeatedFieldArrayLength + length;
                    int[] iArr2 = new int[i];
                    if (length != 0) {
                        System.arraycopy(iArr, 0, iArr2, 0, length);
                    }
                    while (length < i - 1) {
                        iArr2[length] = codedInputByteBufferNano.readInt32();
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    iArr2[length] = codedInputByteBufferNano.readInt32();
                    this.latenciesMs = iArr2;
                } else if (readTag == 26) {
                    int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                    int position = codedInputByteBufferNano.getPosition();
                    int i2 = 0;
                    while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                        codedInputByteBufferNano.readInt32();
                        i2++;
                    }
                    codedInputByteBufferNano.rewindToPosition(position);
                    int[] iArr3 = this.latenciesMs;
                    int length2 = iArr3 == null ? 0 : iArr3.length;
                    int i3 = i2 + length2;
                    int[] iArr4 = new int[i3];
                    if (length2 != 0) {
                        System.arraycopy(iArr3, 0, iArr4, 0, length2);
                    }
                    while (length2 < i3) {
                        iArr4[length2] = codedInputByteBufferNano.readInt32();
                        length2++;
                    }
                    this.latenciesMs = iArr4;
                    codedInputByteBufferNano.popLimit(pushLimit);
                } else if (readTag == 34) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                    Pair[] pairArr = this.errnosCounters;
                    int length3 = pairArr == null ? 0 : pairArr.length;
                    int i4 = repeatedFieldArrayLength2 + length3;
                    Pair[] pairArr2 = new Pair[i4];
                    if (length3 != 0) {
                        System.arraycopy(pairArr, 0, pairArr2, 0, length3);
                    }
                    while (length3 < i4 - 1) {
                        Pair pair = new Pair();
                        pairArr2[length3] = pair;
                        codedInputByteBufferNano.readMessage(pair);
                        codedInputByteBufferNano.readTag();
                        length3++;
                    }
                    Pair pair2 = new Pair();
                    pairArr2[length3] = pair2;
                    codedInputByteBufferNano.readMessage(pair2);
                    this.errnosCounters = pairArr2;
                } else if (readTag == 40) {
                    this.connectBlockingCount = codedInputByteBufferNano.readInt32();
                } else if (readTag == 48) {
                    int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 48);
                    int[] iArr5 = this.nonBlockingLatenciesMs;
                    int length4 = iArr5 == null ? 0 : iArr5.length;
                    int i5 = repeatedFieldArrayLength3 + length4;
                    int[] iArr6 = new int[i5];
                    if (length4 != 0) {
                        System.arraycopy(iArr5, 0, iArr6, 0, length4);
                    }
                    while (length4 < i5 - 1) {
                        iArr6[length4] = codedInputByteBufferNano.readInt32();
                        codedInputByteBufferNano.readTag();
                        length4++;
                    }
                    iArr6[length4] = codedInputByteBufferNano.readInt32();
                    this.nonBlockingLatenciesMs = iArr6;
                } else if (readTag != 50) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int pushLimit2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                    int position2 = codedInputByteBufferNano.getPosition();
                    int i6 = 0;
                    while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                        codedInputByteBufferNano.readInt32();
                        i6++;
                    }
                    codedInputByteBufferNano.rewindToPosition(position2);
                    int[] iArr7 = this.nonBlockingLatenciesMs;
                    int length5 = iArr7 == null ? 0 : iArr7.length;
                    int i7 = i6 + length5;
                    int[] iArr8 = new int[i7];
                    if (length5 != 0) {
                        System.arraycopy(iArr7, 0, iArr8, 0, length5);
                    }
                    while (length5 < i7) {
                        iArr8[length5] = codedInputByteBufferNano.readInt32();
                        length5++;
                    }
                    this.nonBlockingLatenciesMs = iArr8;
                    codedInputByteBufferNano.popLimit(pushLimit2);
                }
            }
            return this;
        }

        public static ConnectStatistics parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (ConnectStatistics) MessageNano.mergeFrom(new ConnectStatistics(), bArr);
        }

        public static ConnectStatistics parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ConnectStatistics().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class DHCPEvent extends MessageNano {
        public static final int ERROR_CODE_FIELD_NUMBER = 3;
        public static final int STATE_TRANSITION_FIELD_NUMBER = 2;
        private static volatile DHCPEvent[] _emptyArray;
        public int durationMs;
        public String ifName;
        private int valueCase_ = 0;
        private Object value_;

        public int getValueCase() {
            return this.valueCase_;
        }

        public DHCPEvent clearValue() {
            this.valueCase_ = 0;
            this.value_ = null;
            return this;
        }

        public static DHCPEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new DHCPEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public boolean hasStateTransition() {
            return this.valueCase_ == 2;
        }

        public String getStateTransition() {
            if (this.valueCase_ == 2) {
                return (String) this.value_;
            }
            return "";
        }

        public DHCPEvent setStateTransition(String str) {
            this.valueCase_ = 2;
            this.value_ = str;
            return this;
        }

        public boolean hasErrorCode() {
            return this.valueCase_ == 3;
        }

        public int getErrorCode() {
            if (this.valueCase_ == 3) {
                return ((Integer) this.value_).intValue();
            }
            return 0;
        }

        public DHCPEvent setErrorCode(int i) {
            this.valueCase_ = 3;
            this.value_ = Integer.valueOf(i);
            return this;
        }

        public DHCPEvent() {
            clear();
        }

        public DHCPEvent clear() {
            this.ifName = "";
            this.durationMs = 0;
            clearValue();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.ifName);
            }
            if (this.valueCase_ == 2) {
                codedOutputByteBufferNano.writeString(2, (String) this.value_);
            }
            if (this.valueCase_ == 3) {
                codedOutputByteBufferNano.writeInt32(3, ((Integer) this.value_).intValue());
            }
            int i = this.durationMs;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(4, i);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.ifName.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.ifName);
            }
            if (this.valueCase_ == 2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, (String) this.value_);
            }
            if (this.valueCase_ == 3) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, ((Integer) this.value_).intValue());
            }
            int i = this.durationMs;
            return i != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(4, i) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public DHCPEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    this.ifName = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    this.value_ = codedInputByteBufferNano.readString();
                    this.valueCase_ = 2;
                } else if (readTag == 24) {
                    this.value_ = Integer.valueOf(codedInputByteBufferNano.readInt32());
                    this.valueCase_ = 3;
                } else if (readTag != 32) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.durationMs = codedInputByteBufferNano.readInt32();
                }
            }
            return this;
        }

        public static DHCPEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (DHCPEvent) MessageNano.mergeFrom(new DHCPEvent(), bArr);
        }

        public static DHCPEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new DHCPEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ApfProgramEvent extends MessageNano {
        private static volatile ApfProgramEvent[] _emptyArray;
        public int currentRas;
        public boolean dropMulticast;
        public long effectiveLifetime;
        public int filteredRas;
        public boolean hasIpv4Addr;
        public long lifetime;
        public int programLength;

        public static ApfProgramEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new ApfProgramEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ApfProgramEvent() {
            clear();
        }

        public ApfProgramEvent clear() {
            this.lifetime = 0L;
            this.effectiveLifetime = 0L;
            this.filteredRas = 0;
            this.currentRas = 0;
            this.programLength = 0;
            this.dropMulticast = false;
            this.hasIpv4Addr = false;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long j = this.lifetime;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(1, j);
            }
            int i = this.filteredRas;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(2, i);
            }
            int i2 = this.currentRas;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i2);
            }
            int i3 = this.programLength;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i3);
            }
            boolean z = this.dropMulticast;
            if (z) {
                codedOutputByteBufferNano.writeBool(5, z);
            }
            boolean z2 = this.hasIpv4Addr;
            if (z2) {
                codedOutputByteBufferNano.writeBool(6, z2);
            }
            long j2 = this.effectiveLifetime;
            if (j2 != 0) {
                codedOutputByteBufferNano.writeInt64(7, j2);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            long j = this.lifetime;
            if (j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, j);
            }
            int i = this.filteredRas;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i);
            }
            int i2 = this.currentRas;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i2);
            }
            int i3 = this.programLength;
            if (i3 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, i3);
            }
            boolean z = this.dropMulticast;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(5, z);
            }
            boolean z2 = this.hasIpv4Addr;
            if (z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(6, z2);
            }
            long j2 = this.effectiveLifetime;
            return j2 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt64Size(7, j2) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public ApfProgramEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 8) {
                    this.lifetime = codedInputByteBufferNano.readInt64();
                } else if (readTag == 16) {
                    this.filteredRas = codedInputByteBufferNano.readInt32();
                } else if (readTag == 24) {
                    this.currentRas = codedInputByteBufferNano.readInt32();
                } else if (readTag == 32) {
                    this.programLength = codedInputByteBufferNano.readInt32();
                } else if (readTag == 40) {
                    this.dropMulticast = codedInputByteBufferNano.readBool();
                } else if (readTag == 48) {
                    this.hasIpv4Addr = codedInputByteBufferNano.readBool();
                } else if (readTag != 56) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.effectiveLifetime = codedInputByteBufferNano.readInt64();
                }
            }
            return this;
        }

        public static ApfProgramEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (ApfProgramEvent) MessageNano.mergeFrom(new ApfProgramEvent(), bArr);
        }

        public static ApfProgramEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ApfProgramEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ApfStatistics extends MessageNano {
        private static volatile ApfStatistics[] _emptyArray;
        public int droppedRas;
        public long durationMs;
        public Pair[] hardwareCounters;
        public int matchingRas;
        public int maxProgramSize;
        public int parseErrors;
        public int programUpdates;
        public int programUpdatesAll;
        public int programUpdatesAllowingMulticast;
        public int receivedRas;
        public int totalPacketDropped;
        public int totalPacketProcessed;
        public int zeroLifetimeRas;

        public static ApfStatistics[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new ApfStatistics[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ApfStatistics() {
            clear();
        }

        public ApfStatistics clear() {
            this.durationMs = 0L;
            this.receivedRas = 0;
            this.matchingRas = 0;
            this.droppedRas = 0;
            this.zeroLifetimeRas = 0;
            this.parseErrors = 0;
            this.programUpdates = 0;
            this.maxProgramSize = 0;
            this.programUpdatesAll = 0;
            this.programUpdatesAllowingMulticast = 0;
            this.totalPacketProcessed = 0;
            this.totalPacketDropped = 0;
            this.hardwareCounters = Pair.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long j = this.durationMs;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(1, j);
            }
            int i = this.receivedRas;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(2, i);
            }
            int i2 = this.matchingRas;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i2);
            }
            int i3 = this.droppedRas;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(5, i3);
            }
            int i4 = this.zeroLifetimeRas;
            if (i4 != 0) {
                codedOutputByteBufferNano.writeInt32(6, i4);
            }
            int i5 = this.parseErrors;
            if (i5 != 0) {
                codedOutputByteBufferNano.writeInt32(7, i5);
            }
            int i6 = this.programUpdates;
            if (i6 != 0) {
                codedOutputByteBufferNano.writeInt32(8, i6);
            }
            int i7 = this.maxProgramSize;
            if (i7 != 0) {
                codedOutputByteBufferNano.writeInt32(9, i7);
            }
            int i8 = this.programUpdatesAll;
            if (i8 != 0) {
                codedOutputByteBufferNano.writeInt32(10, i8);
            }
            int i9 = this.programUpdatesAllowingMulticast;
            if (i9 != 0) {
                codedOutputByteBufferNano.writeInt32(11, i9);
            }
            int i10 = this.totalPacketProcessed;
            if (i10 != 0) {
                codedOutputByteBufferNano.writeInt32(12, i10);
            }
            int i11 = this.totalPacketDropped;
            if (i11 != 0) {
                codedOutputByteBufferNano.writeInt32(13, i11);
            }
            Pair[] pairArr = this.hardwareCounters;
            if (pairArr != null && pairArr.length > 0) {
                int i12 = 0;
                while (true) {
                    Pair[] pairArr2 = this.hardwareCounters;
                    if (i12 >= pairArr2.length) {
                        break;
                    }
                    Pair pair = pairArr2[i12];
                    if (pair != null) {
                        codedOutputByteBufferNano.writeMessage(14, pair);
                    }
                    i12++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            long j = this.durationMs;
            if (j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, j);
            }
            int i = this.receivedRas;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i);
            }
            int i2 = this.matchingRas;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i2);
            }
            int i3 = this.droppedRas;
            if (i3 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, i3);
            }
            int i4 = this.zeroLifetimeRas;
            if (i4 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, i4);
            }
            int i5 = this.parseErrors;
            if (i5 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(7, i5);
            }
            int i6 = this.programUpdates;
            if (i6 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(8, i6);
            }
            int i7 = this.maxProgramSize;
            if (i7 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(9, i7);
            }
            int i8 = this.programUpdatesAll;
            if (i8 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(10, i8);
            }
            int i9 = this.programUpdatesAllowingMulticast;
            if (i9 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(11, i9);
            }
            int i10 = this.totalPacketProcessed;
            if (i10 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(12, i10);
            }
            int i11 = this.totalPacketDropped;
            if (i11 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(13, i11);
            }
            Pair[] pairArr = this.hardwareCounters;
            if (pairArr != null && pairArr.length > 0) {
                int i12 = 0;
                while (true) {
                    Pair[] pairArr2 = this.hardwareCounters;
                    if (i12 >= pairArr2.length) {
                        break;
                    }
                    Pair pair = pairArr2[i12];
                    if (pair != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(14, pair);
                    }
                    i12++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public ApfStatistics mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        break;
                    case 8:
                        this.durationMs = codedInputByteBufferNano.readInt64();
                        break;
                    case 16:
                        this.receivedRas = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.matchingRas = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        this.droppedRas = codedInputByteBufferNano.readInt32();
                        break;
                    case 48:
                        this.zeroLifetimeRas = codedInputByteBufferNano.readInt32();
                        break;
                    case 56:
                        this.parseErrors = codedInputByteBufferNano.readInt32();
                        break;
                    case 64:
                        this.programUpdates = codedInputByteBufferNano.readInt32();
                        break;
                    case 72:
                        this.maxProgramSize = codedInputByteBufferNano.readInt32();
                        break;
                    case 80:
                        this.programUpdatesAll = codedInputByteBufferNano.readInt32();
                        break;
                    case 88:
                        this.programUpdatesAllowingMulticast = codedInputByteBufferNano.readInt32();
                        break;
                    case 96:
                        this.totalPacketProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 104:
                        this.totalPacketDropped = codedInputByteBufferNano.readInt32();
                        break;
                    case 114:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 114);
                        Pair[] pairArr = this.hardwareCounters;
                        int length = pairArr == null ? 0 : pairArr.length;
                        int i = repeatedFieldArrayLength + length;
                        Pair[] pairArr2 = new Pair[i];
                        if (length != 0) {
                            System.arraycopy(pairArr, 0, pairArr2, 0, length);
                        }
                        while (length < i - 1) {
                            Pair pair = new Pair();
                            pairArr2[length] = pair;
                            codedInputByteBufferNano.readMessage(pair);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        Pair pair2 = new Pair();
                        pairArr2[length] = pair2;
                        codedInputByteBufferNano.readMessage(pair2);
                        this.hardwareCounters = pairArr2;
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            break;
                        }
                }
            }
            return this;
        }

        public static ApfStatistics parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (ApfStatistics) MessageNano.mergeFrom(new ApfStatistics(), bArr);
        }

        public static ApfStatistics parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ApfStatistics().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class RaEvent extends MessageNano {
        private static volatile RaEvent[] _emptyArray;
        public long dnsslLifetime;
        public long prefixPreferredLifetime;
        public long prefixValidLifetime;
        public long rdnssLifetime;
        public long routeInfoLifetime;
        public long routerLifetime;

        public static RaEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new RaEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public RaEvent() {
            clear();
        }

        public RaEvent clear() {
            this.routerLifetime = 0L;
            this.prefixValidLifetime = 0L;
            this.prefixPreferredLifetime = 0L;
            this.routeInfoLifetime = 0L;
            this.rdnssLifetime = 0L;
            this.dnsslLifetime = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long j = this.routerLifetime;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(1, j);
            }
            long j2 = this.prefixValidLifetime;
            if (j2 != 0) {
                codedOutputByteBufferNano.writeInt64(2, j2);
            }
            long j3 = this.prefixPreferredLifetime;
            if (j3 != 0) {
                codedOutputByteBufferNano.writeInt64(3, j3);
            }
            long j4 = this.routeInfoLifetime;
            if (j4 != 0) {
                codedOutputByteBufferNano.writeInt64(4, j4);
            }
            long j5 = this.rdnssLifetime;
            if (j5 != 0) {
                codedOutputByteBufferNano.writeInt64(5, j5);
            }
            long j6 = this.dnsslLifetime;
            if (j6 != 0) {
                codedOutputByteBufferNano.writeInt64(6, j6);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            long j = this.routerLifetime;
            if (j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, j);
            }
            long j2 = this.prefixValidLifetime;
            if (j2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(2, j2);
            }
            long j3 = this.prefixPreferredLifetime;
            if (j3 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(3, j3);
            }
            long j4 = this.routeInfoLifetime;
            if (j4 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(4, j4);
            }
            long j5 = this.rdnssLifetime;
            if (j5 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(5, j5);
            }
            long j6 = this.dnsslLifetime;
            return j6 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt64Size(6, j6) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public RaEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 8) {
                    this.routerLifetime = codedInputByteBufferNano.readInt64();
                } else if (readTag == 16) {
                    this.prefixValidLifetime = codedInputByteBufferNano.readInt64();
                } else if (readTag == 24) {
                    this.prefixPreferredLifetime = codedInputByteBufferNano.readInt64();
                } else if (readTag == 32) {
                    this.routeInfoLifetime = codedInputByteBufferNano.readInt64();
                } else if (readTag == 40) {
                    this.rdnssLifetime = codedInputByteBufferNano.readInt64();
                } else if (readTag != 48) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.dnsslLifetime = codedInputByteBufferNano.readInt64();
                }
            }
            return this;
        }

        public static RaEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (RaEvent) MessageNano.mergeFrom(new RaEvent(), bArr);
        }

        public static RaEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new RaEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class IpProvisioningEvent extends MessageNano {
        private static volatile IpProvisioningEvent[] _emptyArray;
        public int eventType;
        public String ifName;
        public int latencyMs;

        public static IpProvisioningEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new IpProvisioningEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public IpProvisioningEvent() {
            clear();
        }

        public IpProvisioningEvent clear() {
            this.ifName = "";
            this.eventType = 0;
            this.latencyMs = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.ifName);
            }
            int i = this.eventType;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(2, i);
            }
            int i2 = this.latencyMs;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i2);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.ifName.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.ifName);
            }
            int i = this.eventType;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i);
            }
            int i2 = this.latencyMs;
            return i2 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(3, i2) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public IpProvisioningEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    this.ifName = codedInputByteBufferNano.readString();
                } else if (readTag == 16) {
                    this.eventType = codedInputByteBufferNano.readInt32();
                } else if (readTag != 24) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.latencyMs = codedInputByteBufferNano.readInt32();
                }
            }
            return this;
        }

        public static IpProvisioningEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (IpProvisioningEvent) MessageNano.mergeFrom(new IpProvisioningEvent(), bArr);
        }

        public static IpProvisioningEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new IpProvisioningEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class NetworkStats extends MessageNano {
        private static volatile NetworkStats[] _emptyArray;
        public long durationMs;
        public boolean everValidated;
        public int ipSupport;
        public int noConnectivityReports;
        public boolean portalFound;
        public int validationAttempts;
        public Pair[] validationEvents;
        public Pair[] validationStates;

        public static NetworkStats[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new NetworkStats[0];
                    }
                }
            }
            return _emptyArray;
        }

        public NetworkStats() {
            clear();
        }

        public NetworkStats clear() {
            this.durationMs = 0L;
            this.ipSupport = 0;
            this.everValidated = false;
            this.portalFound = false;
            this.noConnectivityReports = 0;
            this.validationAttempts = 0;
            this.validationEvents = Pair.emptyArray();
            this.validationStates = Pair.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long j = this.durationMs;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(1, j);
            }
            int i = this.ipSupport;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(2, i);
            }
            boolean z = this.everValidated;
            if (z) {
                codedOutputByteBufferNano.writeBool(3, z);
            }
            boolean z2 = this.portalFound;
            if (z2) {
                codedOutputByteBufferNano.writeBool(4, z2);
            }
            int i2 = this.noConnectivityReports;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(5, i2);
            }
            int i3 = this.validationAttempts;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(6, i3);
            }
            Pair[] pairArr = this.validationEvents;
            int i4 = 0;
            if (pairArr != null && pairArr.length > 0) {
                int i5 = 0;
                while (true) {
                    Pair[] pairArr2 = this.validationEvents;
                    if (i5 >= pairArr2.length) {
                        break;
                    }
                    Pair pair = pairArr2[i5];
                    if (pair != null) {
                        codedOutputByteBufferNano.writeMessage(7, pair);
                    }
                    i5++;
                }
            }
            Pair[] pairArr3 = this.validationStates;
            if (pairArr3 != null && pairArr3.length > 0) {
                while (true) {
                    Pair[] pairArr4 = this.validationStates;
                    if (i4 >= pairArr4.length) {
                        break;
                    }
                    Pair pair2 = pairArr4[i4];
                    if (pair2 != null) {
                        codedOutputByteBufferNano.writeMessage(8, pair2);
                    }
                    i4++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            long j = this.durationMs;
            if (j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, j);
            }
            int i = this.ipSupport;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i);
            }
            boolean z = this.everValidated;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(3, z);
            }
            boolean z2 = this.portalFound;
            if (z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(4, z2);
            }
            int i2 = this.noConnectivityReports;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, i2);
            }
            int i3 = this.validationAttempts;
            if (i3 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, i3);
            }
            Pair[] pairArr = this.validationEvents;
            int i4 = 0;
            if (pairArr != null && pairArr.length > 0) {
                int i5 = 0;
                while (true) {
                    Pair[] pairArr2 = this.validationEvents;
                    if (i5 >= pairArr2.length) {
                        break;
                    }
                    Pair pair = pairArr2[i5];
                    if (pair != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, pair);
                    }
                    i5++;
                }
            }
            Pair[] pairArr3 = this.validationStates;
            if (pairArr3 != null && pairArr3.length > 0) {
                while (true) {
                    Pair[] pairArr4 = this.validationStates;
                    if (i4 >= pairArr4.length) {
                        break;
                    }
                    Pair pair2 = pairArr4[i4];
                    if (pair2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, pair2);
                    }
                    i4++;
                }
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public NetworkStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 8) {
                    this.durationMs = codedInputByteBufferNano.readInt64();
                } else if (readTag == 16) {
                    int readInt32 = codedInputByteBufferNano.readInt32();
                    if (readInt32 == 0 || readInt32 == 1 || readInt32 == 2 || readInt32 == 3) {
                        this.ipSupport = readInt32;
                    }
                } else if (readTag == 24) {
                    this.everValidated = codedInputByteBufferNano.readBool();
                } else if (readTag == 32) {
                    this.portalFound = codedInputByteBufferNano.readBool();
                } else if (readTag == 40) {
                    this.noConnectivityReports = codedInputByteBufferNano.readInt32();
                } else if (readTag == 48) {
                    this.validationAttempts = codedInputByteBufferNano.readInt32();
                } else if (readTag == 58) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                    Pair[] pairArr = this.validationEvents;
                    int length = pairArr == null ? 0 : pairArr.length;
                    int i = repeatedFieldArrayLength + length;
                    Pair[] pairArr2 = new Pair[i];
                    if (length != 0) {
                        System.arraycopy(pairArr, 0, pairArr2, 0, length);
                    }
                    while (length < i - 1) {
                        Pair pair = new Pair();
                        pairArr2[length] = pair;
                        codedInputByteBufferNano.readMessage(pair);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    Pair pair2 = new Pair();
                    pairArr2[length] = pair2;
                    codedInputByteBufferNano.readMessage(pair2);
                    this.validationEvents = pairArr2;
                } else if (readTag != 66) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                    Pair[] pairArr3 = this.validationStates;
                    int length2 = pairArr3 == null ? 0 : pairArr3.length;
                    int i2 = repeatedFieldArrayLength2 + length2;
                    Pair[] pairArr4 = new Pair[i2];
                    if (length2 != 0) {
                        System.arraycopy(pairArr3, 0, pairArr4, 0, length2);
                    }
                    while (length2 < i2 - 1) {
                        Pair pair3 = new Pair();
                        pairArr4[length2] = pair3;
                        codedInputByteBufferNano.readMessage(pair3);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    Pair pair4 = new Pair();
                    pairArr4[length2] = pair4;
                    codedInputByteBufferNano.readMessage(pair4);
                    this.validationStates = pairArr4;
                }
            }
            return this;
        }

        public static NetworkStats parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (NetworkStats) MessageNano.mergeFrom(new NetworkStats(), bArr);
        }

        public static NetworkStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new NetworkStats().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class WakeupStats extends MessageNano {
        private static volatile WakeupStats[] _emptyArray;
        public long applicationWakeups;
        public long durationSec;
        public Pair[] ethertypeCounts;
        public Pair[] ipNextHeaderCounts;
        public long l2BroadcastCount;
        public long l2MulticastCount;
        public long l2UnicastCount;
        public long noUidWakeups;
        public long nonApplicationWakeups;
        public long rootWakeups;
        public long systemWakeups;
        public long totalWakeups;

        public static WakeupStats[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new WakeupStats[0];
                    }
                }
            }
            return _emptyArray;
        }

        public WakeupStats() {
            clear();
        }

        public WakeupStats clear() {
            this.durationSec = 0L;
            this.totalWakeups = 0L;
            this.rootWakeups = 0L;
            this.systemWakeups = 0L;
            this.applicationWakeups = 0L;
            this.nonApplicationWakeups = 0L;
            this.noUidWakeups = 0L;
            this.ethertypeCounts = Pair.emptyArray();
            this.ipNextHeaderCounts = Pair.emptyArray();
            this.l2UnicastCount = 0L;
            this.l2MulticastCount = 0L;
            this.l2BroadcastCount = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long j = this.durationSec;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(1, j);
            }
            long j2 = this.totalWakeups;
            if (j2 != 0) {
                codedOutputByteBufferNano.writeInt64(2, j2);
            }
            long j3 = this.rootWakeups;
            if (j3 != 0) {
                codedOutputByteBufferNano.writeInt64(3, j3);
            }
            long j4 = this.systemWakeups;
            if (j4 != 0) {
                codedOutputByteBufferNano.writeInt64(4, j4);
            }
            long j5 = this.applicationWakeups;
            if (j5 != 0) {
                codedOutputByteBufferNano.writeInt64(5, j5);
            }
            long j6 = this.nonApplicationWakeups;
            if (j6 != 0) {
                codedOutputByteBufferNano.writeInt64(6, j6);
            }
            long j7 = this.noUidWakeups;
            if (j7 != 0) {
                codedOutputByteBufferNano.writeInt64(7, j7);
            }
            Pair[] pairArr = this.ethertypeCounts;
            int i = 0;
            if (pairArr != null && pairArr.length > 0) {
                int i2 = 0;
                while (true) {
                    Pair[] pairArr2 = this.ethertypeCounts;
                    if (i2 >= pairArr2.length) {
                        break;
                    }
                    Pair pair = pairArr2[i2];
                    if (pair != null) {
                        codedOutputByteBufferNano.writeMessage(8, pair);
                    }
                    i2++;
                }
            }
            Pair[] pairArr3 = this.ipNextHeaderCounts;
            if (pairArr3 != null && pairArr3.length > 0) {
                while (true) {
                    Pair[] pairArr4 = this.ipNextHeaderCounts;
                    if (i >= pairArr4.length) {
                        break;
                    }
                    Pair pair2 = pairArr4[i];
                    if (pair2 != null) {
                        codedOutputByteBufferNano.writeMessage(9, pair2);
                    }
                    i++;
                }
            }
            long j8 = this.l2UnicastCount;
            if (j8 != 0) {
                codedOutputByteBufferNano.writeInt64(10, j8);
            }
            long j9 = this.l2MulticastCount;
            if (j9 != 0) {
                codedOutputByteBufferNano.writeInt64(11, j9);
            }
            long j10 = this.l2BroadcastCount;
            if (j10 != 0) {
                codedOutputByteBufferNano.writeInt64(12, j10);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            long j = this.durationSec;
            if (j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, j);
            }
            long j2 = this.totalWakeups;
            if (j2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(2, j2);
            }
            long j3 = this.rootWakeups;
            if (j3 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(3, j3);
            }
            long j4 = this.systemWakeups;
            if (j4 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(4, j4);
            }
            long j5 = this.applicationWakeups;
            if (j5 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(5, j5);
            }
            long j6 = this.nonApplicationWakeups;
            if (j6 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(6, j6);
            }
            long j7 = this.noUidWakeups;
            if (j7 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(7, j7);
            }
            Pair[] pairArr = this.ethertypeCounts;
            int i = 0;
            if (pairArr != null && pairArr.length > 0) {
                int i2 = 0;
                while (true) {
                    Pair[] pairArr2 = this.ethertypeCounts;
                    if (i2 >= pairArr2.length) {
                        break;
                    }
                    Pair pair = pairArr2[i2];
                    if (pair != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, pair);
                    }
                    i2++;
                }
            }
            Pair[] pairArr3 = this.ipNextHeaderCounts;
            if (pairArr3 != null && pairArr3.length > 0) {
                while (true) {
                    Pair[] pairArr4 = this.ipNextHeaderCounts;
                    if (i >= pairArr4.length) {
                        break;
                    }
                    Pair pair2 = pairArr4[i];
                    if (pair2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(9, pair2);
                    }
                    i++;
                }
            }
            long j8 = this.l2UnicastCount;
            if (j8 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(10, j8);
            }
            long j9 = this.l2MulticastCount;
            if (j9 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(11, j9);
            }
            long j10 = this.l2BroadcastCount;
            return j10 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt64Size(12, j10) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public WakeupStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        break;
                    case 8:
                        this.durationSec = codedInputByteBufferNano.readInt64();
                        break;
                    case 16:
                        this.totalWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 24:
                        this.rootWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 32:
                        this.systemWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 40:
                        this.applicationWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 48:
                        this.nonApplicationWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 56:
                        this.noUidWakeups = codedInputByteBufferNano.readInt64();
                        break;
                    case 66:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                        Pair[] pairArr = this.ethertypeCounts;
                        int length = pairArr == null ? 0 : pairArr.length;
                        int i = repeatedFieldArrayLength + length;
                        Pair[] pairArr2 = new Pair[i];
                        if (length != 0) {
                            System.arraycopy(pairArr, 0, pairArr2, 0, length);
                        }
                        while (length < i - 1) {
                            Pair pair = new Pair();
                            pairArr2[length] = pair;
                            codedInputByteBufferNano.readMessage(pair);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        Pair pair2 = new Pair();
                        pairArr2[length] = pair2;
                        codedInputByteBufferNano.readMessage(pair2);
                        this.ethertypeCounts = pairArr2;
                        break;
                    case 74:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                        Pair[] pairArr3 = this.ipNextHeaderCounts;
                        int length2 = pairArr3 == null ? 0 : pairArr3.length;
                        int i2 = repeatedFieldArrayLength2 + length2;
                        Pair[] pairArr4 = new Pair[i2];
                        if (length2 != 0) {
                            System.arraycopy(pairArr3, 0, pairArr4, 0, length2);
                        }
                        while (length2 < i2 - 1) {
                            Pair pair3 = new Pair();
                            pairArr4[length2] = pair3;
                            codedInputByteBufferNano.readMessage(pair3);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        Pair pair4 = new Pair();
                        pairArr4[length2] = pair4;
                        codedInputByteBufferNano.readMessage(pair4);
                        this.ipNextHeaderCounts = pairArr4;
                        break;
                    case 80:
                        this.l2UnicastCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 88:
                        this.l2MulticastCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 96:
                        this.l2BroadcastCount = codedInputByteBufferNano.readInt64();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            break;
                        }
                }
            }
            return this;
        }

        public static WakeupStats parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (WakeupStats) MessageNano.mergeFrom(new WakeupStats(), bArr);
        }

        public static WakeupStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WakeupStats().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class IpConnectivityEvent extends MessageNano {
        public static final int APF_PROGRAM_EVENT_FIELD_NUMBER = 9;
        public static final int APF_STATISTICS_FIELD_NUMBER = 10;
        public static final int CONNECT_STATISTICS_FIELD_NUMBER = 14;
        public static final int DEFAULT_NETWORK_EVENT_FIELD_NUMBER = 2;
        public static final int DHCP_EVENT_FIELD_NUMBER = 6;
        public static final int DNS_LATENCIES_FIELD_NUMBER = 13;
        public static final int DNS_LOOKUP_BATCH_FIELD_NUMBER = 5;
        public static final int IP_PROVISIONING_EVENT_FIELD_NUMBER = 7;
        public static final int IP_REACHABILITY_EVENT_FIELD_NUMBER = 3;
        public static final int NETWORK_EVENT_FIELD_NUMBER = 4;
        public static final int NETWORK_STATS_FIELD_NUMBER = 19;
        public static final int RA_EVENT_FIELD_NUMBER = 11;
        public static final int VALIDATION_PROBE_EVENT_FIELD_NUMBER = 8;
        public static final int WAKEUP_STATS_FIELD_NUMBER = 20;
        private static volatile IpConnectivityEvent[] _emptyArray;
        private int eventCase_ = 0;
        private Object event_;
        public String ifName;
        public int linkLayer;
        public int networkId;
        public long timeMs;
        public long transports;

        public int getEventCase() {
            return this.eventCase_;
        }

        public IpConnectivityEvent clearEvent() {
            this.eventCase_ = 0;
            this.event_ = null;
            return this;
        }

        public static IpConnectivityEvent[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new IpConnectivityEvent[0];
                    }
                }
            }
            return _emptyArray;
        }

        public boolean hasDefaultNetworkEvent() {
            return this.eventCase_ == 2;
        }

        public DefaultNetworkEvent getDefaultNetworkEvent() {
            if (this.eventCase_ == 2) {
                return (DefaultNetworkEvent) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setDefaultNetworkEvent(DefaultNetworkEvent defaultNetworkEvent) {
            defaultNetworkEvent.getClass();
            this.eventCase_ = 2;
            this.event_ = defaultNetworkEvent;
            return this;
        }

        public boolean hasIpReachabilityEvent() {
            return this.eventCase_ == 3;
        }

        public IpReachabilityEvent getIpReachabilityEvent() {
            if (this.eventCase_ == 3) {
                return (IpReachabilityEvent) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setIpReachabilityEvent(IpReachabilityEvent ipReachabilityEvent) {
            ipReachabilityEvent.getClass();
            this.eventCase_ = 3;
            this.event_ = ipReachabilityEvent;
            return this;
        }

        public boolean hasNetworkEvent() {
            return this.eventCase_ == 4;
        }

        public NetworkEvent getNetworkEvent() {
            if (this.eventCase_ == 4) {
                return (NetworkEvent) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setNetworkEvent(NetworkEvent networkEvent) {
            networkEvent.getClass();
            this.eventCase_ = 4;
            this.event_ = networkEvent;
            return this;
        }

        public boolean hasDnsLookupBatch() {
            return this.eventCase_ == 5;
        }

        public DNSLookupBatch getDnsLookupBatch() {
            if (this.eventCase_ == 5) {
                return (DNSLookupBatch) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setDnsLookupBatch(DNSLookupBatch dNSLookupBatch) {
            dNSLookupBatch.getClass();
            this.eventCase_ = 5;
            this.event_ = dNSLookupBatch;
            return this;
        }

        public boolean hasDnsLatencies() {
            return this.eventCase_ == 13;
        }

        public DNSLatencies getDnsLatencies() {
            if (this.eventCase_ == 13) {
                return (DNSLatencies) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setDnsLatencies(DNSLatencies dNSLatencies) {
            dNSLatencies.getClass();
            this.eventCase_ = 13;
            this.event_ = dNSLatencies;
            return this;
        }

        public boolean hasConnectStatistics() {
            return this.eventCase_ == 14;
        }

        public ConnectStatistics getConnectStatistics() {
            if (this.eventCase_ == 14) {
                return (ConnectStatistics) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setConnectStatistics(ConnectStatistics connectStatistics) {
            connectStatistics.getClass();
            this.eventCase_ = 14;
            this.event_ = connectStatistics;
            return this;
        }

        public boolean hasDhcpEvent() {
            return this.eventCase_ == 6;
        }

        public DHCPEvent getDhcpEvent() {
            if (this.eventCase_ == 6) {
                return (DHCPEvent) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setDhcpEvent(DHCPEvent dHCPEvent) {
            dHCPEvent.getClass();
            this.eventCase_ = 6;
            this.event_ = dHCPEvent;
            return this;
        }

        public boolean hasIpProvisioningEvent() {
            return this.eventCase_ == 7;
        }

        public IpProvisioningEvent getIpProvisioningEvent() {
            if (this.eventCase_ == 7) {
                return (IpProvisioningEvent) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setIpProvisioningEvent(IpProvisioningEvent ipProvisioningEvent) {
            ipProvisioningEvent.getClass();
            this.eventCase_ = 7;
            this.event_ = ipProvisioningEvent;
            return this;
        }

        public boolean hasValidationProbeEvent() {
            return this.eventCase_ == 8;
        }

        public ValidationProbeEvent getValidationProbeEvent() {
            if (this.eventCase_ == 8) {
                return (ValidationProbeEvent) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setValidationProbeEvent(ValidationProbeEvent validationProbeEvent) {
            validationProbeEvent.getClass();
            this.eventCase_ = 8;
            this.event_ = validationProbeEvent;
            return this;
        }

        public boolean hasApfProgramEvent() {
            return this.eventCase_ == 9;
        }

        public ApfProgramEvent getApfProgramEvent() {
            if (this.eventCase_ == 9) {
                return (ApfProgramEvent) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setApfProgramEvent(ApfProgramEvent apfProgramEvent) {
            apfProgramEvent.getClass();
            this.eventCase_ = 9;
            this.event_ = apfProgramEvent;
            return this;
        }

        public boolean hasApfStatistics() {
            return this.eventCase_ == 10;
        }

        public ApfStatistics getApfStatistics() {
            if (this.eventCase_ == 10) {
                return (ApfStatistics) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setApfStatistics(ApfStatistics apfStatistics) {
            apfStatistics.getClass();
            this.eventCase_ = 10;
            this.event_ = apfStatistics;
            return this;
        }

        public boolean hasRaEvent() {
            return this.eventCase_ == 11;
        }

        public RaEvent getRaEvent() {
            if (this.eventCase_ == 11) {
                return (RaEvent) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setRaEvent(RaEvent raEvent) {
            raEvent.getClass();
            this.eventCase_ = 11;
            this.event_ = raEvent;
            return this;
        }

        public boolean hasNetworkStats() {
            return this.eventCase_ == 19;
        }

        public NetworkStats getNetworkStats() {
            if (this.eventCase_ == 19) {
                return (NetworkStats) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setNetworkStats(NetworkStats networkStats) {
            networkStats.getClass();
            this.eventCase_ = 19;
            this.event_ = networkStats;
            return this;
        }

        public boolean hasWakeupStats() {
            return this.eventCase_ == 20;
        }

        public WakeupStats getWakeupStats() {
            if (this.eventCase_ == 20) {
                return (WakeupStats) this.event_;
            }
            return null;
        }

        public IpConnectivityEvent setWakeupStats(WakeupStats wakeupStats) {
            wakeupStats.getClass();
            this.eventCase_ = 20;
            this.event_ = wakeupStats;
            return this;
        }

        public IpConnectivityEvent() {
            clear();
        }

        public IpConnectivityEvent clear() {
            this.timeMs = 0L;
            this.linkLayer = 0;
            this.networkId = 0;
            this.ifName = "";
            this.transports = 0L;
            clearEvent();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long j = this.timeMs;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(1, j);
            }
            if (this.eventCase_ == 2) {
                codedOutputByteBufferNano.writeMessage(2, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 3) {
                codedOutputByteBufferNano.writeMessage(3, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 4) {
                codedOutputByteBufferNano.writeMessage(4, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 5) {
                codedOutputByteBufferNano.writeMessage(5, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 6) {
                codedOutputByteBufferNano.writeMessage(6, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 7) {
                codedOutputByteBufferNano.writeMessage(7, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 8) {
                codedOutputByteBufferNano.writeMessage(8, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 9) {
                codedOutputByteBufferNano.writeMessage(9, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 10) {
                codedOutputByteBufferNano.writeMessage(10, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 11) {
                codedOutputByteBufferNano.writeMessage(11, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 13) {
                codedOutputByteBufferNano.writeMessage(13, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 14) {
                codedOutputByteBufferNano.writeMessage(14, (MessageNano) this.event_);
            }
            int i = this.linkLayer;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(15, i);
            }
            int i2 = this.networkId;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(16, i2);
            }
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(17, this.ifName);
            }
            long j2 = this.transports;
            if (j2 != 0) {
                codedOutputByteBufferNano.writeInt64(18, j2);
            }
            if (this.eventCase_ == 19) {
                codedOutputByteBufferNano.writeMessage(19, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 20) {
                codedOutputByteBufferNano.writeMessage(20, (MessageNano) this.event_);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            long j = this.timeMs;
            if (j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, j);
            }
            if (this.eventCase_ == 2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 3) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 4) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 5) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 6) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 7) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 8) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 9) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(9, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 10) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(10, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 11) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(11, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 13) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(13, (MessageNano) this.event_);
            }
            if (this.eventCase_ == 14) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(14, (MessageNano) this.event_);
            }
            int i = this.linkLayer;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(15, i);
            }
            int i2 = this.networkId;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(16, i2);
            }
            if (!this.ifName.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(17, this.ifName);
            }
            long j2 = this.transports;
            if (j2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(18, j2);
            }
            if (this.eventCase_ == 19) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(19, (MessageNano) this.event_);
            }
            return this.eventCase_ == 20 ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(20, (MessageNano) this.event_) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public IpConnectivityEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        break;
                    case 8:
                        this.timeMs = codedInputByteBufferNano.readInt64();
                        break;
                    case 18:
                        if (this.eventCase_ != 2) {
                            this.event_ = new DefaultNetworkEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 2;
                        break;
                    case 26:
                        if (this.eventCase_ != 3) {
                            this.event_ = new IpReachabilityEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 3;
                        break;
                    case 34:
                        if (this.eventCase_ != 4) {
                            this.event_ = new NetworkEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 4;
                        break;
                    case 42:
                        if (this.eventCase_ != 5) {
                            this.event_ = new DNSLookupBatch();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 5;
                        break;
                    case 50:
                        if (this.eventCase_ != 6) {
                            this.event_ = new DHCPEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 6;
                        break;
                    case 58:
                        if (this.eventCase_ != 7) {
                            this.event_ = new IpProvisioningEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 7;
                        break;
                    case 66:
                        if (this.eventCase_ != 8) {
                            this.event_ = new ValidationProbeEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 8;
                        break;
                    case 74:
                        if (this.eventCase_ != 9) {
                            this.event_ = new ApfProgramEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 9;
                        break;
                    case 82:
                        if (this.eventCase_ != 10) {
                            this.event_ = new ApfStatistics();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 10;
                        break;
                    case 90:
                        if (this.eventCase_ != 11) {
                            this.event_ = new RaEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 11;
                        break;
                    case 106:
                        if (this.eventCase_ != 13) {
                            this.event_ = new DNSLatencies();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 13;
                        break;
                    case 114:
                        if (this.eventCase_ != 14) {
                            this.event_ = new ConnectStatistics();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 14;
                        break;
                    case 120:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        switch (readInt32) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                                this.linkLayer = readInt32;
                                break;
                        }
                    case 128:
                        this.networkId = codedInputByteBufferNano.readInt32();
                        break;
                    case 138:
                        this.ifName = codedInputByteBufferNano.readString();
                        break;
                    case 144:
                        this.transports = codedInputByteBufferNano.readInt64();
                        break;
                    case 154:
                        if (this.eventCase_ != 19) {
                            this.event_ = new NetworkStats();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 19;
                        break;
                    case 162:
                        if (this.eventCase_ != 20) {
                            this.event_ = new WakeupStats();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                        this.eventCase_ = 20;
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            break;
                        }
                }
            }
            return this;
        }

        public static IpConnectivityEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (IpConnectivityEvent) MessageNano.mergeFrom(new IpConnectivityEvent(), bArr);
        }

        public static IpConnectivityEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new IpConnectivityEvent().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class IpConnectivityLog extends MessageNano {
        private static volatile IpConnectivityLog[] _emptyArray;
        public int droppedEvents;
        public IpConnectivityEvent[] events;
        public int version;

        public static IpConnectivityLog[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new IpConnectivityLog[0];
                    }
                }
            }
            return _emptyArray;
        }

        public IpConnectivityLog() {
            clear();
        }

        public IpConnectivityLog clear() {
            this.events = IpConnectivityEvent.emptyArray();
            this.droppedEvents = 0;
            this.version = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            IpConnectivityEvent[] ipConnectivityEventArr = this.events;
            if (ipConnectivityEventArr != null && ipConnectivityEventArr.length > 0) {
                int i = 0;
                while (true) {
                    IpConnectivityEvent[] ipConnectivityEventArr2 = this.events;
                    if (i >= ipConnectivityEventArr2.length) {
                        break;
                    }
                    IpConnectivityEvent ipConnectivityEvent = ipConnectivityEventArr2[i];
                    if (ipConnectivityEvent != null) {
                        codedOutputByteBufferNano.writeMessage(1, ipConnectivityEvent);
                    }
                    i++;
                }
            }
            int i2 = this.droppedEvents;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(2, i2);
            }
            int i3 = this.version;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i3);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            IpConnectivityEvent[] ipConnectivityEventArr = this.events;
            if (ipConnectivityEventArr != null && ipConnectivityEventArr.length > 0) {
                int i = 0;
                while (true) {
                    IpConnectivityEvent[] ipConnectivityEventArr2 = this.events;
                    if (i >= ipConnectivityEventArr2.length) {
                        break;
                    }
                    IpConnectivityEvent ipConnectivityEvent = ipConnectivityEventArr2[i];
                    if (ipConnectivityEvent != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, ipConnectivityEvent);
                    }
                    i++;
                }
            }
            int i2 = this.droppedEvents;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i2);
            }
            int i3 = this.version;
            return i3 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(3, i3) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public IpConnectivityLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    IpConnectivityEvent[] ipConnectivityEventArr = this.events;
                    int length = ipConnectivityEventArr == null ? 0 : ipConnectivityEventArr.length;
                    int i = repeatedFieldArrayLength + length;
                    IpConnectivityEvent[] ipConnectivityEventArr2 = new IpConnectivityEvent[i];
                    if (length != 0) {
                        System.arraycopy(ipConnectivityEventArr, 0, ipConnectivityEventArr2, 0, length);
                    }
                    while (length < i - 1) {
                        IpConnectivityEvent ipConnectivityEvent = new IpConnectivityEvent();
                        ipConnectivityEventArr2[length] = ipConnectivityEvent;
                        codedInputByteBufferNano.readMessage(ipConnectivityEvent);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    IpConnectivityEvent ipConnectivityEvent2 = new IpConnectivityEvent();
                    ipConnectivityEventArr2[length] = ipConnectivityEvent2;
                    codedInputByteBufferNano.readMessage(ipConnectivityEvent2);
                    this.events = ipConnectivityEventArr2;
                } else if (readTag == 16) {
                    this.droppedEvents = codedInputByteBufferNano.readInt32();
                } else if (readTag != 24) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.version = codedInputByteBufferNano.readInt32();
                }
            }
            return this;
        }

        public static IpConnectivityLog parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (IpConnectivityLog) MessageNano.mergeFrom(new IpConnectivityLog(), bArr);
        }

        public static IpConnectivityLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new IpConnectivityLog().mergeFrom(codedInputByteBufferNano);
        }
    }
}
