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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.partitionName.equals("")) {
                output.writeString(1, this.partitionName);
            }
            if (this.device != null && this.device.length > 0) {
                for (int i = 0; i < this.device.length; i++) {
                    String element = this.device[i];
                    if (element != null) {
                        output.writeString(2, element);
                    }
                }
            }
            if (this.build != null && this.build.length > 0) {
                for (int i2 = 0; i2 < this.build.length; i2++) {
                    String element2 = this.build[i2];
                    if (element2 != null) {
                        output.writeString(3, element2);
                    }
                }
            }
            if (!this.version.equals("")) {
                output.writeString(4, this.version);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.partitionName.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.partitionName);
            }
            if (this.device != null && this.device.length > 0) {
                int dataCount = 0;
                int dataSize = 0;
                for (int i = 0; i < this.device.length; i++) {
                    String element = this.device[i];
                    if (element != null) {
                        dataCount++;
                        dataSize += CodedOutputByteBufferNano.computeStringSizeNoTag(element);
                    }
                }
                size = size + dataSize + (dataCount * 1);
            }
            if (this.build != null && this.build.length > 0) {
                int dataCount2 = 0;
                int dataSize2 = 0;
                for (int i2 = 0; i2 < this.build.length; i2++) {
                    String element2 = this.build[i2];
                    if (element2 != null) {
                        dataCount2++;
                        dataSize2 += CodedOutputByteBufferNano.computeStringSizeNoTag(element2);
                    }
                }
                size = size + dataSize2 + (dataCount2 * 1);
            }
            if (!this.version.equals("")) {
                return size + CodedOutputByteBufferNano.computeStringSize(4, this.version);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public PartitionState mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.partitionName = input.readString();
                        break;
                    case 18:
                        int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 18);
                        int i = this.device == null ? 0 : this.device.length;
                        String[] newArray = new String[i + arrayLength];
                        if (i != 0) {
                            System.arraycopy(this.device, 0, newArray, 0, i);
                        }
                        while (i < newArray.length - 1) {
                            newArray[i] = input.readString();
                            input.readTag();
                            i++;
                        }
                        newArray[i] = input.readString();
                        this.device = newArray;
                        break;
                    case 26:
                        int arrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(input, 26);
                        int i2 = this.build == null ? 0 : this.build.length;
                        String[] newArray2 = new String[i2 + arrayLength2];
                        if (i2 != 0) {
                            System.arraycopy(this.build, 0, newArray2, 0, i2);
                        }
                        while (i2 < newArray2.length - 1) {
                            newArray2[i2] = input.readString();
                            input.readTag();
                            i2++;
                        }
                        newArray2[i2] = input.readString();
                        this.build = newArray2;
                        break;
                    case 34:
                        this.version = input.readString();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static PartitionState parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (PartitionState) MessageNano.mergeFrom(new PartitionState(), data);
        }

        public static PartitionState parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new PartitionState().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.device != null && this.device.length > 0) {
                for (int i = 0; i < this.device.length; i++) {
                    String element = this.device[i];
                    if (element != null) {
                        output.writeString(1, element);
                    }
                }
            }
            if (this.build != null && this.build.length > 0) {
                for (int i2 = 0; i2 < this.build.length; i2++) {
                    String element2 = this.build[i2];
                    if (element2 != null) {
                        output.writeString(2, element2);
                    }
                }
            }
            if (!this.buildIncremental.equals("")) {
                output.writeString(3, this.buildIncremental);
            }
            if (this.timestamp != 0) {
                output.writeInt64(4, this.timestamp);
            }
            if (!this.sdkLevel.equals("")) {
                output.writeString(5, this.sdkLevel);
            }
            if (!this.securityPatchLevel.equals("")) {
                output.writeString(6, this.securityPatchLevel);
            }
            if (this.partitionState != null && this.partitionState.length > 0) {
                for (int i3 = 0; i3 < this.partitionState.length; i3++) {
                    PartitionState element3 = this.partitionState[i3];
                    if (element3 != null) {
                        output.writeMessage(7, element3);
                    }
                }
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.device != null && this.device.length > 0) {
                int dataCount = 0;
                int dataSize = 0;
                for (int i = 0; i < this.device.length; i++) {
                    String element = this.device[i];
                    if (element != null) {
                        dataCount++;
                        dataSize += CodedOutputByteBufferNano.computeStringSizeNoTag(element);
                    }
                }
                size = size + dataSize + (dataCount * 1);
            }
            if (this.build != null && this.build.length > 0) {
                int dataCount2 = 0;
                int dataSize2 = 0;
                for (int i2 = 0; i2 < this.build.length; i2++) {
                    String element2 = this.build[i2];
                    if (element2 != null) {
                        dataCount2++;
                        dataSize2 += CodedOutputByteBufferNano.computeStringSizeNoTag(element2);
                    }
                }
                size = size + dataSize2 + (dataCount2 * 1);
            }
            if (!this.buildIncremental.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(3, this.buildIncremental);
            }
            if (this.timestamp != 0) {
                size += CodedOutputByteBufferNano.computeInt64Size(4, this.timestamp);
            }
            if (!this.sdkLevel.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(5, this.sdkLevel);
            }
            if (!this.securityPatchLevel.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(6, this.securityPatchLevel);
            }
            if (this.partitionState != null && this.partitionState.length > 0) {
                for (int i3 = 0; i3 < this.partitionState.length; i3++) {
                    PartitionState element3 = this.partitionState[i3];
                    if (element3 != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(7, element3);
                    }
                }
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public DeviceState mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 10);
                        int i = this.device == null ? 0 : this.device.length;
                        String[] newArray = new String[i + arrayLength];
                        if (i != 0) {
                            System.arraycopy(this.device, 0, newArray, 0, i);
                        }
                        while (i < newArray.length - 1) {
                            newArray[i] = input.readString();
                            input.readTag();
                            i++;
                        }
                        newArray[i] = input.readString();
                        this.device = newArray;
                        break;
                    case 18:
                        int arrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(input, 18);
                        int i2 = this.build == null ? 0 : this.build.length;
                        String[] newArray2 = new String[i2 + arrayLength2];
                        if (i2 != 0) {
                            System.arraycopy(this.build, 0, newArray2, 0, i2);
                        }
                        while (i2 < newArray2.length - 1) {
                            newArray2[i2] = input.readString();
                            input.readTag();
                            i2++;
                        }
                        newArray2[i2] = input.readString();
                        this.build = newArray2;
                        break;
                    case 26:
                        this.buildIncremental = input.readString();
                        break;
                    case 32:
                        this.timestamp = input.readInt64();
                        break;
                    case 42:
                        this.sdkLevel = input.readString();
                        break;
                    case 50:
                        this.securityPatchLevel = input.readString();
                        break;
                    case 58:
                        int arrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(input, 58);
                        int i3 = this.partitionState == null ? 0 : this.partitionState.length;
                        PartitionState[] newArray3 = new PartitionState[i3 + arrayLength3];
                        if (i3 != 0) {
                            System.arraycopy(this.partitionState, 0, newArray3, 0, i3);
                        }
                        while (i3 < newArray3.length - 1) {
                            newArray3[i3] = new PartitionState();
                            input.readMessage(newArray3[i3]);
                            input.readTag();
                            i3++;
                        }
                        newArray3[i3] = new PartitionState();
                        input.readMessage(newArray3[i3]);
                        this.partitionState = newArray3;
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static DeviceState parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (DeviceState) MessageNano.mergeFrom(new DeviceState(), data);
        }

        public static DeviceState parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new DeviceState().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.packageName.equals("")) {
                output.writeString(1, this.packageName);
            }
            if (this.version != 0) {
                output.writeInt64(2, this.version);
            }
            if (this.isCompressed) {
                output.writeBool(3, this.isCompressed);
            }
            if (this.decompressedSize != 0) {
                output.writeInt64(4, this.decompressedSize);
            }
            if (this.sourceVersion != 0) {
                output.writeInt64(5, this.sourceVersion);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.packageName.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.packageName);
            }
            if (this.version != 0) {
                size += CodedOutputByteBufferNano.computeInt64Size(2, this.version);
            }
            if (this.isCompressed) {
                size += CodedOutputByteBufferNano.computeBoolSize(3, this.isCompressed);
            }
            if (this.decompressedSize != 0) {
                size += CodedOutputByteBufferNano.computeInt64Size(4, this.decompressedSize);
            }
            if (this.sourceVersion != 0) {
                return size + CodedOutputByteBufferNano.computeInt64Size(5, this.sourceVersion);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public ApexInfo mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.packageName = input.readString();
                        break;
                    case 16:
                        this.version = input.readInt64();
                        break;
                    case 24:
                        this.isCompressed = input.readBool();
                        break;
                    case 32:
                        this.decompressedSize = input.readInt64();
                        break;
                    case 40:
                        this.sourceVersion = input.readInt64();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static ApexInfo parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (ApexInfo) MessageNano.mergeFrom(new ApexInfo(), data);
        }

        public static ApexInfo parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new ApexInfo().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.apexInfo != null && this.apexInfo.length > 0) {
                for (int i = 0; i < this.apexInfo.length; i++) {
                    ApexInfo element = this.apexInfo[i];
                    if (element != null) {
                        output.writeMessage(1, element);
                    }
                }
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.apexInfo != null && this.apexInfo.length > 0) {
                for (int i = 0; i < this.apexInfo.length; i++) {
                    ApexInfo element = this.apexInfo[i];
                    if (element != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(1, element);
                    }
                }
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public ApexMetadata mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 10);
                        int i = this.apexInfo == null ? 0 : this.apexInfo.length;
                        ApexInfo[] newArray = new ApexInfo[i + arrayLength];
                        if (i != 0) {
                            System.arraycopy(this.apexInfo, 0, newArray, 0, i);
                        }
                        while (i < newArray.length - 1) {
                            newArray[i] = new ApexInfo();
                            input.readMessage(newArray[i]);
                            input.readTag();
                            i++;
                        }
                        newArray[i] = new ApexInfo();
                        input.readMessage(newArray[i]);
                        this.apexInfo = newArray;
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static ApexMetadata parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (ApexMetadata) MessageNano.mergeFrom(new ApexMetadata(), data);
        }

        public static ApexMetadata parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new ApexMetadata().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.type != 0) {
                output.writeInt32(1, this.type);
            }
            if (this.wipe) {
                output.writeBool(2, this.wipe);
            }
            if (this.downgrade) {
                output.writeBool(3, this.downgrade);
            }
            if (this.propertyFiles != null) {
                InternalNano.serializeMapField(output, this.propertyFiles, 4, 9, 9);
            }
            if (this.precondition != null) {
                output.writeMessage(5, this.precondition);
            }
            if (this.postcondition != null) {
                output.writeMessage(6, this.postcondition);
            }
            if (this.retrofitDynamicPartitions) {
                output.writeBool(7, this.retrofitDynamicPartitions);
            }
            if (this.requiredCache != 0) {
                output.writeInt64(8, this.requiredCache);
            }
            if (this.splDowngrade) {
                output.writeBool(9, this.splDowngrade);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.type != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(1, this.type);
            }
            if (this.wipe) {
                size += CodedOutputByteBufferNano.computeBoolSize(2, this.wipe);
            }
            if (this.downgrade) {
                size += CodedOutputByteBufferNano.computeBoolSize(3, this.downgrade);
            }
            if (this.propertyFiles != null) {
                size += InternalNano.computeMapFieldSize(this.propertyFiles, 4, 9, 9);
            }
            if (this.precondition != null) {
                size += CodedOutputByteBufferNano.computeMessageSize(5, this.precondition);
            }
            if (this.postcondition != null) {
                size += CodedOutputByteBufferNano.computeMessageSize(6, this.postcondition);
            }
            if (this.retrofitDynamicPartitions) {
                size += CodedOutputByteBufferNano.computeBoolSize(7, this.retrofitDynamicPartitions);
            }
            if (this.requiredCache != 0) {
                size += CodedOutputByteBufferNano.computeInt64Size(8, this.requiredCache);
            }
            if (this.splDowngrade) {
                return size + CodedOutputByteBufferNano.computeBoolSize(9, this.splDowngrade);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public OtaMetadata mergeFrom(CodedInputByteBufferNano input) throws IOException {
            MapFactories.MapFactory mapFactory = MapFactories.getMapFactory();
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 8:
                        int value = input.readInt32();
                        switch (value) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.type = value;
                                break;
                        }
                    case 16:
                        this.wipe = input.readBool();
                        break;
                    case 24:
                        this.downgrade = input.readBool();
                        break;
                    case 34:
                        this.propertyFiles = InternalNano.mergeMapEntry(input, this.propertyFiles, mapFactory, 9, 9, null, 10, 18);
                        break;
                    case 42:
                        if (this.precondition == null) {
                            this.precondition = new DeviceState();
                        }
                        input.readMessage(this.precondition);
                        break;
                    case 50:
                        if (this.postcondition == null) {
                            this.postcondition = new DeviceState();
                        }
                        input.readMessage(this.postcondition);
                        break;
                    case 56:
                        this.retrofitDynamicPartitions = input.readBool();
                        break;
                    case 64:
                        this.requiredCache = input.readInt64();
                        break;
                    case 72:
                        this.splDowngrade = input.readBool();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static OtaMetadata parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (OtaMetadata) MessageNano.mergeFrom(new OtaMetadata(), data);
        }

        public static OtaMetadata parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new OtaMetadata().mergeFrom(input);
        }
    }
}
