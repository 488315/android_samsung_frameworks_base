package android.ota.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MapFactories;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes3.dex */
public interface OtaPackageMetadata {

    public static final class PartitionState extends MessageNano {
        private static volatile PartitionState[] _emptyArray;
        public String[] build;
        public String[] device;
        public String partitionName;
        public String version;

        public static PartitionState[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new PartitionState[0];
                    }
                }
            }
            return _emptyArray;
        }

        public PartitionState() {
            clear();
        }

        public PartitionState clear() {
            this.partitionName = "";
            this.device = WireFormatNano.EMPTY_STRING_ARRAY;
            this.build = WireFormatNano.EMPTY_STRING_ARRAY;
            this.version = "";
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.partitionName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.partitionName);
            }
            String[] strArr = this.device;
            int i = 0;
            if (strArr != null && strArr.length > 0) {
                int i2 = 0;
                while (true) {
                    String[] strArr2 = this.device;
                    if (i2 >= strArr2.length) {
                        break;
                    }
                    String str = strArr2[i2];
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(2, str);
                    }
                    i2++;
                }
            }
            String[] strArr3 = this.build;
            if (strArr3 != null && strArr3.length > 0) {
                while (true) {
                    String[] strArr4 = this.build;
                    if (i >= strArr4.length) {
                        break;
                    }
                    String str2 = strArr4[i];
                    if (str2 != null) {
                        codedOutputByteBufferNano.writeString(3, str2);
                    }
                    i++;
                }
            }
            if (!this.version.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.version);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int iComputeSerializedSize = super.computeSerializedSize();
            if (!this.partitionName.equals("")) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.partitionName);
            }
            String[] strArr = this.device;
            int i = 0;
            if (strArr != null && strArr.length > 0) {
                int i2 = 0;
                int iComputeStringSizeNoTag = 0;
                int i3 = 0;
                while (true) {
                    String[] strArr2 = this.device;
                    if (i2 >= strArr2.length) {
                        break;
                    }
                    String str = strArr2[i2];
                    if (str != null) {
                        i3++;
                        iComputeStringSizeNoTag += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                    i2++;
                }
                iComputeSerializedSize = iComputeSerializedSize + iComputeStringSizeNoTag + i3;
            }
            String[] strArr3 = this.build;
            if (strArr3 != null && strArr3.length > 0) {
                int iComputeStringSizeNoTag2 = 0;
                int i4 = 0;
                while (true) {
                    String[] strArr4 = this.build;
                    if (i >= strArr4.length) {
                        break;
                    }
                    String str2 = strArr4[i];
                    if (str2 != null) {
                        i4++;
                        iComputeStringSizeNoTag2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
                    }
                    i++;
                }
                iComputeSerializedSize = iComputeSerializedSize + iComputeStringSizeNoTag2 + i4;
            }
            return !this.version.equals("") ? iComputeSerializedSize + CodedOutputByteBufferNano.computeStringSize(4, this.version) : iComputeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public PartitionState mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int tag = codedInputByteBufferNano.readTag();
                if (tag == 0) {
                    break;
                }
                if (tag == 10) {
                    this.partitionName = codedInputByteBufferNano.readString();
                } else if (tag == 18) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    String[] strArr = this.device;
                    int length = strArr == null ? 0 : strArr.length;
                    int i = repeatedFieldArrayLength + length;
                    String[] strArr2 = new String[i];
                    if (length != 0) {
                        System.arraycopy(strArr, 0, strArr2, 0, length);
                    }
                    while (length < i - 1) {
                        strArr2[length] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    strArr2[length] = codedInputByteBufferNano.readString();
                    this.device = strArr2;
                } else if (tag == 26) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    String[] strArr3 = this.build;
                    int length2 = strArr3 == null ? 0 : strArr3.length;
                    int i2 = repeatedFieldArrayLength2 + length2;
                    String[] strArr4 = new String[i2];
                    if (length2 != 0) {
                        System.arraycopy(strArr3, 0, strArr4, 0, length2);
                    }
                    while (length2 < i2 - 1) {
                        strArr4[length2] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    strArr4[length2] = codedInputByteBufferNano.readString();
                    this.build = strArr4;
                } else if (tag != 34) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, tag)) {
                        break;
                    }
                } else {
                    this.version = codedInputByteBufferNano.readString();
                }
            }
            return this;
        }

        public static PartitionState parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (PartitionState) MessageNano.mergeFrom(new PartitionState(), bArr);
        }

        public static PartitionState parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new PartitionState().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class DeviceState extends MessageNano {
        private static volatile DeviceState[] _emptyArray;
        public String[] build;
        public String buildIncremental;
        public String[] device;
        public PartitionState[] partitionState;
        public String sdkLevel;
        public String securityPatchLevel;
        public long timestamp;

        public static DeviceState[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new DeviceState[0];
                    }
                }
            }
            return _emptyArray;
        }

        public DeviceState() {
            clear();
        }

        public DeviceState clear() {
            this.device = WireFormatNano.EMPTY_STRING_ARRAY;
            this.build = WireFormatNano.EMPTY_STRING_ARRAY;
            this.buildIncremental = "";
            this.timestamp = 0L;
            this.sdkLevel = "";
            this.securityPatchLevel = "";
            this.partitionState = PartitionState.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            String[] strArr = this.device;
            int i = 0;
            if (strArr != null && strArr.length > 0) {
                int i2 = 0;
                while (true) {
                    String[] strArr2 = this.device;
                    if (i2 >= strArr2.length) {
                        break;
                    }
                    String str = strArr2[i2];
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(1, str);
                    }
                    i2++;
                }
            }
            String[] strArr3 = this.build;
            if (strArr3 != null && strArr3.length > 0) {
                int i3 = 0;
                while (true) {
                    String[] strArr4 = this.build;
                    if (i3 >= strArr4.length) {
                        break;
                    }
                    String str2 = strArr4[i3];
                    if (str2 != null) {
                        codedOutputByteBufferNano.writeString(2, str2);
                    }
                    i3++;
                }
            }
            if (!this.buildIncremental.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.buildIncremental);
            }
            long j = this.timestamp;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(4, j);
            }
            if (!this.sdkLevel.equals("")) {
                codedOutputByteBufferNano.writeString(5, this.sdkLevel);
            }
            if (!this.securityPatchLevel.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.securityPatchLevel);
            }
            PartitionState[] partitionStateArr = this.partitionState;
            if (partitionStateArr != null && partitionStateArr.length > 0) {
                while (true) {
                    PartitionState[] partitionStateArr2 = this.partitionState;
                    if (i >= partitionStateArr2.length) {
                        break;
                    }
                    PartitionState partitionState = partitionStateArr2[i];
                    if (partitionState != null) {
                        codedOutputByteBufferNano.writeMessage(7, partitionState);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int iComputeSerializedSize = super.computeSerializedSize();
            String[] strArr = this.device;
            int i = 0;
            if (strArr != null && strArr.length > 0) {
                int i2 = 0;
                int iComputeStringSizeNoTag = 0;
                int i3 = 0;
                while (true) {
                    String[] strArr2 = this.device;
                    if (i2 >= strArr2.length) {
                        break;
                    }
                    String str = strArr2[i2];
                    if (str != null) {
                        i3++;
                        iComputeStringSizeNoTag += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                    i2++;
                }
                iComputeSerializedSize = iComputeSerializedSize + iComputeStringSizeNoTag + i3;
            }
            String[] strArr3 = this.build;
            if (strArr3 != null && strArr3.length > 0) {
                int i4 = 0;
                int iComputeStringSizeNoTag2 = 0;
                int i5 = 0;
                while (true) {
                    String[] strArr4 = this.build;
                    if (i4 >= strArr4.length) {
                        break;
                    }
                    String str2 = strArr4[i4];
                    if (str2 != null) {
                        i5++;
                        iComputeStringSizeNoTag2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
                    }
                    i4++;
                }
                iComputeSerializedSize = iComputeSerializedSize + iComputeStringSizeNoTag2 + i5;
            }
            if (!this.buildIncremental.equals("")) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.buildIncremental);
            }
            long j = this.timestamp;
            if (j != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(4, j);
            }
            if (!this.sdkLevel.equals("")) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeStringSize(5, this.sdkLevel);
            }
            if (!this.securityPatchLevel.equals("")) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeStringSize(6, this.securityPatchLevel);
            }
            PartitionState[] partitionStateArr = this.partitionState;
            if (partitionStateArr != null && partitionStateArr.length > 0) {
                while (true) {
                    PartitionState[] partitionStateArr2 = this.partitionState;
                    if (i >= partitionStateArr2.length) {
                        break;
                    }
                    PartitionState partitionState = partitionStateArr2[i];
                    if (partitionState != null) {
                        iComputeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, partitionState);
                    }
                    i++;
                }
            }
            return iComputeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public DeviceState mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int tag = codedInputByteBufferNano.readTag();
                if (tag == 0) {
                    break;
                }
                if (tag == 10) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    String[] strArr = this.device;
                    int length = strArr == null ? 0 : strArr.length;
                    int i = repeatedFieldArrayLength + length;
                    String[] strArr2 = new String[i];
                    if (length != 0) {
                        System.arraycopy(strArr, 0, strArr2, 0, length);
                    }
                    while (length < i - 1) {
                        strArr2[length] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    strArr2[length] = codedInputByteBufferNano.readString();
                    this.device = strArr2;
                } else if (tag == 18) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    String[] strArr3 = this.build;
                    int length2 = strArr3 == null ? 0 : strArr3.length;
                    int i2 = repeatedFieldArrayLength2 + length2;
                    String[] strArr4 = new String[i2];
                    if (length2 != 0) {
                        System.arraycopy(strArr3, 0, strArr4, 0, length2);
                    }
                    while (length2 < i2 - 1) {
                        strArr4[length2] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    strArr4[length2] = codedInputByteBufferNano.readString();
                    this.build = strArr4;
                } else if (tag == 26) {
                    this.buildIncremental = codedInputByteBufferNano.readString();
                } else if (tag == 32) {
                    this.timestamp = codedInputByteBufferNano.readInt64();
                } else if (tag == 42) {
                    this.sdkLevel = codedInputByteBufferNano.readString();
                } else if (tag == 50) {
                    this.securityPatchLevel = codedInputByteBufferNano.readString();
                } else if (tag != 58) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, tag)) {
                        break;
                    }
                } else {
                    int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                    PartitionState[] partitionStateArr = this.partitionState;
                    int length3 = partitionStateArr == null ? 0 : partitionStateArr.length;
                    int i3 = repeatedFieldArrayLength3 + length3;
                    PartitionState[] partitionStateArr2 = new PartitionState[i3];
                    if (length3 != 0) {
                        System.arraycopy(partitionStateArr, 0, partitionStateArr2, 0, length3);
                    }
                    while (length3 < i3 - 1) {
                        PartitionState partitionState = new PartitionState();
                        partitionStateArr2[length3] = partitionState;
                        codedInputByteBufferNano.readMessage(partitionState);
                        codedInputByteBufferNano.readTag();
                        length3++;
                    }
                    PartitionState partitionState2 = new PartitionState();
                    partitionStateArr2[length3] = partitionState2;
                    codedInputByteBufferNano.readMessage(partitionState2);
                    this.partitionState = partitionStateArr2;
                }
            }
            return this;
        }

        public static DeviceState parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (DeviceState) MessageNano.mergeFrom(new DeviceState(), bArr);
        }

        public static DeviceState parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new DeviceState().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ApexInfo extends MessageNano {
        private static volatile ApexInfo[] _emptyArray;
        public long decompressedSize;
        public boolean isCompressed;
        public String packageName;
        public long sourceVersion;
        public long version;

        public static ApexInfo[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new ApexInfo[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ApexInfo() {
            clear();
        }

        public ApexInfo clear() {
            this.packageName = "";
            this.version = 0L;
            this.isCompressed = false;
            this.decompressedSize = 0L;
            this.sourceVersion = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.packageName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.packageName);
            }
            long j = this.version;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(2, j);
            }
            boolean z = this.isCompressed;
            if (z) {
                codedOutputByteBufferNano.writeBool(3, z);
            }
            long j2 = this.decompressedSize;
            if (j2 != 0) {
                codedOutputByteBufferNano.writeInt64(4, j2);
            }
            long j3 = this.sourceVersion;
            if (j3 != 0) {
                codedOutputByteBufferNano.writeInt64(5, j3);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int iComputeSerializedSize = super.computeSerializedSize();
            if (!this.packageName.equals("")) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.packageName);
            }
            long j = this.version;
            if (j != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(2, j);
            }
            boolean z = this.isCompressed;
            if (z) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(3, z);
            }
            long j2 = this.decompressedSize;
            if (j2 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(4, j2);
            }
            long j3 = this.sourceVersion;
            return j3 != 0 ? iComputeSerializedSize + CodedOutputByteBufferNano.computeInt64Size(5, j3) : iComputeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public ApexInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int tag = codedInputByteBufferNano.readTag();
                if (tag == 0) {
                    break;
                }
                if (tag == 10) {
                    this.packageName = codedInputByteBufferNano.readString();
                } else if (tag == 16) {
                    this.version = codedInputByteBufferNano.readInt64();
                } else if (tag == 24) {
                    this.isCompressed = codedInputByteBufferNano.readBool();
                } else if (tag == 32) {
                    this.decompressedSize = codedInputByteBufferNano.readInt64();
                } else if (tag != 40) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, tag)) {
                        break;
                    }
                } else {
                    this.sourceVersion = codedInputByteBufferNano.readInt64();
                }
            }
            return this;
        }

        public static ApexInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (ApexInfo) MessageNano.mergeFrom(new ApexInfo(), bArr);
        }

        public static ApexInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ApexInfo().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class ApexMetadata extends MessageNano {
        private static volatile ApexMetadata[] _emptyArray;
        public ApexInfo[] apexInfo;

        public static ApexMetadata[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new ApexMetadata[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ApexMetadata() {
            clear();
        }

        public ApexMetadata clear() {
            this.apexInfo = ApexInfo.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            ApexInfo[] apexInfoArr = this.apexInfo;
            if (apexInfoArr != null && apexInfoArr.length > 0) {
                int i = 0;
                while (true) {
                    ApexInfo[] apexInfoArr2 = this.apexInfo;
                    if (i >= apexInfoArr2.length) {
                        break;
                    }
                    ApexInfo apexInfo = apexInfoArr2[i];
                    if (apexInfo != null) {
                        codedOutputByteBufferNano.writeMessage(1, apexInfo);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int iComputeSerializedSize = super.computeSerializedSize();
            ApexInfo[] apexInfoArr = this.apexInfo;
            if (apexInfoArr != null && apexInfoArr.length > 0) {
                int i = 0;
                while (true) {
                    ApexInfo[] apexInfoArr2 = this.apexInfo;
                    if (i >= apexInfoArr2.length) {
                        break;
                    }
                    ApexInfo apexInfo = apexInfoArr2[i];
                    if (apexInfo != null) {
                        iComputeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, apexInfo);
                    }
                    i++;
                }
            }
            return iComputeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public ApexMetadata mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int tag = codedInputByteBufferNano.readTag();
                if (tag == 0) {
                    break;
                }
                if (tag != 10) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, tag)) {
                        break;
                    }
                } else {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    ApexInfo[] apexInfoArr = this.apexInfo;
                    int length = apexInfoArr == null ? 0 : apexInfoArr.length;
                    int i = repeatedFieldArrayLength + length;
                    ApexInfo[] apexInfoArr2 = new ApexInfo[i];
                    if (length != 0) {
                        System.arraycopy(apexInfoArr, 0, apexInfoArr2, 0, length);
                    }
                    while (length < i - 1) {
                        ApexInfo apexInfo = new ApexInfo();
                        apexInfoArr2[length] = apexInfo;
                        codedInputByteBufferNano.readMessage(apexInfo);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    ApexInfo apexInfo2 = new ApexInfo();
                    apexInfoArr2[length] = apexInfo2;
                    codedInputByteBufferNano.readMessage(apexInfo2);
                    this.apexInfo = apexInfoArr2;
                }
            }
            return this;
        }

        public static ApexMetadata parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (ApexMetadata) MessageNano.mergeFrom(new ApexMetadata(), bArr);
        }

        public static ApexMetadata parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ApexMetadata().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class OtaMetadata extends MessageNano {
        public static final int AB = 1;
        public static final int BLOCK = 2;
        public static final int BRICK = 3;
        public static final int UNKNOWN = 0;
        private static volatile OtaMetadata[] _emptyArray;
        public boolean downgrade;
        public DeviceState postcondition;
        public DeviceState precondition;
        public Map<String, String> propertyFiles;
        public long requiredCache;
        public boolean retrofitDynamicPartitions;
        public boolean splDowngrade;
        public int type;
        public boolean wipe;

        public static OtaMetadata[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new OtaMetadata[0];
                    }
                }
            }
            return _emptyArray;
        }

        public OtaMetadata() {
            clear();
        }

        public OtaMetadata clear() {
            this.type = 0;
            this.wipe = false;
            this.downgrade = false;
            this.propertyFiles = null;
            this.precondition = null;
            this.postcondition = null;
            this.retrofitDynamicPartitions = false;
            this.requiredCache = 0L;
            this.splDowngrade = false;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.type;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            boolean z = this.wipe;
            if (z) {
                codedOutputByteBufferNano.writeBool(2, z);
            }
            boolean z2 = this.downgrade;
            if (z2) {
                codedOutputByteBufferNano.writeBool(3, z2);
            }
            Map<String, String> map = this.propertyFiles;
            if (map != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, map, 4, 9, 9);
            }
            DeviceState deviceState = this.precondition;
            if (deviceState != null) {
                codedOutputByteBufferNano.writeMessage(5, deviceState);
            }
            DeviceState deviceState2 = this.postcondition;
            if (deviceState2 != null) {
                codedOutputByteBufferNano.writeMessage(6, deviceState2);
            }
            boolean z3 = this.retrofitDynamicPartitions;
            if (z3) {
                codedOutputByteBufferNano.writeBool(7, z3);
            }
            long j = this.requiredCache;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(8, j);
            }
            boolean z4 = this.splDowngrade;
            if (z4) {
                codedOutputByteBufferNano.writeBool(9, z4);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int iComputeSerializedSize = super.computeSerializedSize();
            int i = this.type;
            if (i != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
            }
            boolean z = this.wipe;
            if (z) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(2, z);
            }
            boolean z2 = this.downgrade;
            if (z2) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(3, z2);
            }
            Map<String, String> map = this.propertyFiles;
            if (map != null) {
                iComputeSerializedSize += InternalNano.computeMapFieldSize(map, 4, 9, 9);
            }
            DeviceState deviceState = this.precondition;
            if (deviceState != null) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, deviceState);
            }
            DeviceState deviceState2 = this.postcondition;
            if (deviceState2 != null) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, deviceState2);
            }
            boolean z3 = this.retrofitDynamicPartitions;
            if (z3) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(7, z3);
            }
            long j = this.requiredCache;
            if (j != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(8, j);
            }
            boolean z4 = this.splDowngrade;
            return z4 ? iComputeSerializedSize + CodedOutputByteBufferNano.computeBoolSize(9, z4) : iComputeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public OtaMetadata mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            CodedInputByteBufferNano codedInputByteBufferNano2;
            MapFactories.MapFactory mapFactory = MapFactories.getMapFactory();
            while (true) {
                int tag = codedInputByteBufferNano.readTag();
                if (tag == 0) {
                    break;
                }
                if (tag == 8) {
                    codedInputByteBufferNano2 = codedInputByteBufferNano;
                    int int32 = codedInputByteBufferNano2.readInt32();
                    if (int32 == 0 || int32 == 1 || int32 == 2 || int32 == 3) {
                        this.type = int32;
                    }
                } else if (tag == 16) {
                    codedInputByteBufferNano2 = codedInputByteBufferNano;
                    this.wipe = codedInputByteBufferNano2.readBool();
                } else if (tag == 24) {
                    codedInputByteBufferNano2 = codedInputByteBufferNano;
                    this.downgrade = codedInputByteBufferNano2.readBool();
                } else if (tag != 34) {
                    if (tag == 42) {
                        if (this.precondition == null) {
                            this.precondition = new DeviceState();
                        }
                        codedInputByteBufferNano.readMessage(this.precondition);
                    } else if (tag == 50) {
                        if (this.postcondition == null) {
                            this.postcondition = new DeviceState();
                        }
                        codedInputByteBufferNano.readMessage(this.postcondition);
                    } else if (tag == 56) {
                        this.retrofitDynamicPartitions = codedInputByteBufferNano.readBool();
                    } else if (tag == 64) {
                        this.requiredCache = codedInputByteBufferNano.readInt64();
                    } else if (tag != 72) {
                        if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, tag)) {
                            break;
                        }
                    } else {
                        this.splDowngrade = codedInputByteBufferNano.readBool();
                    }
                    codedInputByteBufferNano2 = codedInputByteBufferNano;
                } else {
                    codedInputByteBufferNano2 = codedInputByteBufferNano;
                    this.propertyFiles = InternalNano.mergeMapEntry(codedInputByteBufferNano2, this.propertyFiles, mapFactory, 9, 9, null, 10, 18);
                }
                codedInputByteBufferNano = codedInputByteBufferNano2;
            }
            return this;
        }

        public static OtaMetadata parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (OtaMetadata) MessageNano.mergeFrom(new OtaMetadata(), bArr);
        }

        public static OtaMetadata parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new OtaMetadata().mergeFrom(codedInputByteBufferNano);
        }
    }
}
