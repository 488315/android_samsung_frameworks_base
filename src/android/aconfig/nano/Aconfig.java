package android.aconfig.nano;

import android.internal.framework.protobuf.nano.CodedInputByteBufferNano;
import android.internal.framework.protobuf.nano.CodedOutputByteBufferNano;
import android.internal.framework.protobuf.nano.InternalNano;
import android.internal.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import android.internal.framework.protobuf.nano.MessageNano;
import android.internal.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes.dex */
public interface Aconfig {
    public static final int DISABLED = 2;
    public static final int ENABLED = 1;
    public static final int READ_ONLY = 1;
    public static final int READ_WRITE = 2;

    public static final class flag_declaration extends MessageNano {
        private static volatile flag_declaration[] _emptyArray;
        public String[] bug;
        public String description;
        public boolean isExported;
        public boolean isFixedReadOnly;
        public flag_metadata metadata;
        public String name;
        public String namespace;

        public static flag_declaration[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new flag_declaration[0];
                    }
                }
            }
            return _emptyArray;
        }

        public flag_declaration() {
            clear();
        }

        public flag_declaration clear() {
            this.name = "";
            this.namespace = "";
            this.description = "";
            this.bug = WireFormatNano.EMPTY_STRING_ARRAY;
            this.isFixedReadOnly = false;
            this.isExported = false;
            this.metadata = null;
            this.cachedSize = -1;
            return this;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.name.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.name);
            }
            if (!this.namespace.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.namespace);
            }
            if (!this.description.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.description);
            }
            String[] strArr = this.bug;
            if (strArr != null && strArr.length > 0) {
                int i = 0;
                while (true) {
                    String[] strArr2 = this.bug;
                    if (i >= strArr2.length) {
                        break;
                    }
                    String str = strArr2[i];
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(4, str);
                    }
                    i++;
                }
            }
            boolean z = this.isFixedReadOnly;
            if (z) {
                codedOutputByteBufferNano.writeBool(5, z);
            }
            boolean z2 = this.isExported;
            if (z2) {
                codedOutputByteBufferNano.writeBool(6, z2);
            }
            flag_metadata flag_metadataVar = this.metadata;
            if (flag_metadataVar != null) {
                codedOutputByteBufferNano.writeMessage(7, flag_metadataVar);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.name.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
            }
            if (!this.namespace.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.namespace);
            }
            if (!this.description.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.description);
            }
            String[] strArr = this.bug;
            if (strArr != null && strArr.length > 0) {
                int i = 0;
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    String[] strArr2 = this.bug;
                    if (i >= strArr2.length) {
                        break;
                    }
                    String str = strArr2[i];
                    if (str != null) {
                        i3++;
                        i2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                    i++;
                }
                computeSerializedSize = computeSerializedSize + i2 + i3;
            }
            boolean z = this.isFixedReadOnly;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(5, z);
            }
            boolean z2 = this.isExported;
            if (z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(6, z2);
            }
            flag_metadata flag_metadataVar = this.metadata;
            return flag_metadataVar != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(7, flag_metadataVar) : computeSerializedSize;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public flag_declaration mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    this.name = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    this.namespace = codedInputByteBufferNano.readString();
                } else if (readTag == 26) {
                    this.description = codedInputByteBufferNano.readString();
                } else if (readTag == 34) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                    String[] strArr = this.bug;
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
                    this.bug = strArr2;
                } else if (readTag == 40) {
                    this.isFixedReadOnly = codedInputByteBufferNano.readBool();
                } else if (readTag == 48) {
                    this.isExported = codedInputByteBufferNano.readBool();
                } else if (readTag != 58) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    if (this.metadata == null) {
                        this.metadata = new flag_metadata();
                    }
                    codedInputByteBufferNano.readMessage(this.metadata);
                }
            }
            return this;
        }

        public static flag_declaration parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (flag_declaration) MessageNano.mergeFrom(new flag_declaration(), bArr);
        }

        public static flag_declaration parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new flag_declaration().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class flag_metadata extends MessageNano {
        public static final int PURPOSE_BUGFIX = 2;
        public static final int PURPOSE_FEATURE = 1;
        public static final int PURPOSE_UNSPECIFIED = 0;
        private static volatile flag_metadata[] _emptyArray;
        public int purpose;

        public static flag_metadata[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new flag_metadata[0];
                    }
                }
            }
            return _emptyArray;
        }

        public flag_metadata() {
            clear();
        }

        public flag_metadata clear() {
            this.purpose = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.purpose;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            int i = this.purpose;
            return i != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(1, i) : computeSerializedSize;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public flag_metadata mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
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
                    int readInt32 = codedInputByteBufferNano.readInt32();
                    if (readInt32 == 0 || readInt32 == 1 || readInt32 == 2) {
                        this.purpose = readInt32;
                    }
                }
            }
            return this;
        }

        public static flag_metadata parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (flag_metadata) MessageNano.mergeFrom(new flag_metadata(), bArr);
        }

        public static flag_metadata parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new flag_metadata().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class flag_declarations extends MessageNano {
        private static volatile flag_declarations[] _emptyArray;
        public String container;
        public flag_declaration[] flag;
        public String package_;

        public static flag_declarations[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new flag_declarations[0];
                    }
                }
            }
            return _emptyArray;
        }

        public flag_declarations() {
            clear();
        }

        public flag_declarations clear() {
            this.package_ = "";
            this.flag = flag_declaration.emptyArray();
            this.container = "";
            this.cachedSize = -1;
            return this;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.package_.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.package_);
            }
            flag_declaration[] flag_declarationVarArr = this.flag;
            if (flag_declarationVarArr != null && flag_declarationVarArr.length > 0) {
                int i = 0;
                while (true) {
                    flag_declaration[] flag_declarationVarArr2 = this.flag;
                    if (i >= flag_declarationVarArr2.length) {
                        break;
                    }
                    flag_declaration flag_declarationVar = flag_declarationVarArr2[i];
                    if (flag_declarationVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, flag_declarationVar);
                    }
                    i++;
                }
            }
            if (!this.container.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.container);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.package_.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.package_);
            }
            flag_declaration[] flag_declarationVarArr = this.flag;
            if (flag_declarationVarArr != null && flag_declarationVarArr.length > 0) {
                int i = 0;
                while (true) {
                    flag_declaration[] flag_declarationVarArr2 = this.flag;
                    if (i >= flag_declarationVarArr2.length) {
                        break;
                    }
                    flag_declaration flag_declarationVar = flag_declarationVarArr2[i];
                    if (flag_declarationVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, flag_declarationVar);
                    }
                    i++;
                }
            }
            return !this.container.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(3, this.container) : computeSerializedSize;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public flag_declarations mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    this.package_ = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    flag_declaration[] flag_declarationVarArr = this.flag;
                    int length = flag_declarationVarArr == null ? 0 : flag_declarationVarArr.length;
                    int i = repeatedFieldArrayLength + length;
                    flag_declaration[] flag_declarationVarArr2 = new flag_declaration[i];
                    if (length != 0) {
                        System.arraycopy(flag_declarationVarArr, 0, flag_declarationVarArr2, 0, length);
                    }
                    while (length < i - 1) {
                        flag_declaration flag_declarationVar = new flag_declaration();
                        flag_declarationVarArr2[length] = flag_declarationVar;
                        codedInputByteBufferNano.readMessage(flag_declarationVar);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    flag_declaration flag_declarationVar2 = new flag_declaration();
                    flag_declarationVarArr2[length] = flag_declarationVar2;
                    codedInputByteBufferNano.readMessage(flag_declarationVar2);
                    this.flag = flag_declarationVarArr2;
                } else if (readTag != 26) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.container = codedInputByteBufferNano.readString();
                }
            }
            return this;
        }

        public static flag_declarations parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (flag_declarations) MessageNano.mergeFrom(new flag_declarations(), bArr);
        }

        public static flag_declarations parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new flag_declarations().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class flag_value extends MessageNano {
        private static volatile flag_value[] _emptyArray;
        public String name;
        public String package_;
        public int permission;
        public int state;

        public static flag_value[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new flag_value[0];
                    }
                }
            }
            return _emptyArray;
        }

        public flag_value() {
            clear();
        }

        public flag_value clear() {
            this.package_ = "";
            this.name = "";
            this.state = 1;
            this.permission = 1;
            this.cachedSize = -1;
            return this;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.package_.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.package_);
            }
            if (!this.name.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.name);
            }
            int i = this.state;
            if (i != 1) {
                codedOutputByteBufferNano.writeInt32(3, i);
            }
            int i2 = this.permission;
            if (i2 != 1) {
                codedOutputByteBufferNano.writeInt32(4, i2);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.package_.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.package_);
            }
            if (!this.name.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.name);
            }
            int i = this.state;
            if (i != 1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i);
            }
            int i2 = this.permission;
            return i2 != 1 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(4, i2) : computeSerializedSize;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public flag_value mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    this.package_ = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    this.name = codedInputByteBufferNano.readString();
                } else if (readTag == 24) {
                    int readInt32 = codedInputByteBufferNano.readInt32();
                    if (readInt32 == 1 || readInt32 == 2) {
                        this.state = readInt32;
                    }
                } else if (readTag != 32) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int readInt322 = codedInputByteBufferNano.readInt32();
                    if (readInt322 == 1 || readInt322 == 2) {
                        this.permission = readInt322;
                    }
                }
            }
            return this;
        }

        public static flag_value parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (flag_value) MessageNano.mergeFrom(new flag_value(), bArr);
        }

        public static flag_value parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new flag_value().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class flag_values extends MessageNano {
        private static volatile flag_values[] _emptyArray;
        public flag_value[] flagValue;

        public static flag_values[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new flag_values[0];
                    }
                }
            }
            return _emptyArray;
        }

        public flag_values() {
            clear();
        }

        public flag_values clear() {
            this.flagValue = flag_value.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            flag_value[] flag_valueVarArr = this.flagValue;
            if (flag_valueVarArr != null && flag_valueVarArr.length > 0) {
                int i = 0;
                while (true) {
                    flag_value[] flag_valueVarArr2 = this.flagValue;
                    if (i >= flag_valueVarArr2.length) {
                        break;
                    }
                    flag_value flag_valueVar = flag_valueVarArr2[i];
                    if (flag_valueVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, flag_valueVar);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            flag_value[] flag_valueVarArr = this.flagValue;
            if (flag_valueVarArr != null && flag_valueVarArr.length > 0) {
                int i = 0;
                while (true) {
                    flag_value[] flag_valueVarArr2 = this.flagValue;
                    if (i >= flag_valueVarArr2.length) {
                        break;
                    }
                    flag_value flag_valueVar = flag_valueVarArr2[i];
                    if (flag_valueVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, flag_valueVar);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public flag_values mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag != 10) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    flag_value[] flag_valueVarArr = this.flagValue;
                    int length = flag_valueVarArr == null ? 0 : flag_valueVarArr.length;
                    int i = repeatedFieldArrayLength + length;
                    flag_value[] flag_valueVarArr2 = new flag_value[i];
                    if (length != 0) {
                        System.arraycopy(flag_valueVarArr, 0, flag_valueVarArr2, 0, length);
                    }
                    while (length < i - 1) {
                        flag_value flag_valueVar = new flag_value();
                        flag_valueVarArr2[length] = flag_valueVar;
                        codedInputByteBufferNano.readMessage(flag_valueVar);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    flag_value flag_valueVar2 = new flag_value();
                    flag_valueVarArr2[length] = flag_valueVar2;
                    codedInputByteBufferNano.readMessage(flag_valueVar2);
                    this.flagValue = flag_valueVarArr2;
                }
            }
            return this;
        }

        public static flag_values parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (flag_values) MessageNano.mergeFrom(new flag_values(), bArr);
        }

        public static flag_values parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new flag_values().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class tracepoint extends MessageNano {
        private static volatile tracepoint[] _emptyArray;
        public int permission;
        public String source;
        public int state;

        public static tracepoint[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new tracepoint[0];
                    }
                }
            }
            return _emptyArray;
        }

        public tracepoint() {
            clear();
        }

        public tracepoint clear() {
            this.source = "";
            this.state = 1;
            this.permission = 1;
            this.cachedSize = -1;
            return this;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.source.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.source);
            }
            int i = this.state;
            if (i != 1) {
                codedOutputByteBufferNano.writeInt32(2, i);
            }
            int i2 = this.permission;
            if (i2 != 1) {
                codedOutputByteBufferNano.writeInt32(3, i2);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.source.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.source);
            }
            int i = this.state;
            if (i != 1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i);
            }
            int i2 = this.permission;
            return i2 != 1 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(3, i2) : computeSerializedSize;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public tracepoint mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    this.source = codedInputByteBufferNano.readString();
                } else if (readTag == 16) {
                    int readInt32 = codedInputByteBufferNano.readInt32();
                    if (readInt32 == 1 || readInt32 == 2) {
                        this.state = readInt32;
                    }
                } else if (readTag != 24) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int readInt322 = codedInputByteBufferNano.readInt32();
                    if (readInt322 == 1 || readInt322 == 2) {
                        this.permission = readInt322;
                    }
                }
            }
            return this;
        }

        public static tracepoint parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (tracepoint) MessageNano.mergeFrom(new tracepoint(), bArr);
        }

        public static tracepoint parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new tracepoint().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class parsed_flag extends MessageNano {
        private static volatile parsed_flag[] _emptyArray;
        public String[] bug;
        public String container;
        public String description;
        public boolean isExported;
        public boolean isFixedReadOnly;
        public flag_metadata metadata;
        public String name;
        public String namespace;
        public String package_;
        public int permission;
        public int state;
        public tracepoint[] trace;

        public static parsed_flag[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new parsed_flag[0];
                    }
                }
            }
            return _emptyArray;
        }

        public parsed_flag() {
            clear();
        }

        public parsed_flag clear() {
            this.package_ = "";
            this.name = "";
            this.namespace = "";
            this.description = "";
            this.bug = WireFormatNano.EMPTY_STRING_ARRAY;
            this.state = 1;
            this.permission = 1;
            this.trace = tracepoint.emptyArray();
            this.isFixedReadOnly = false;
            this.isExported = false;
            this.container = "";
            this.metadata = null;
            this.cachedSize = -1;
            return this;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.package_.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.package_);
            }
            if (!this.name.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.name);
            }
            if (!this.namespace.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.namespace);
            }
            if (!this.description.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.description);
            }
            String[] strArr = this.bug;
            int i = 0;
            if (strArr != null && strArr.length > 0) {
                int i2 = 0;
                while (true) {
                    String[] strArr2 = this.bug;
                    if (i2 >= strArr2.length) {
                        break;
                    }
                    String str = strArr2[i2];
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(5, str);
                    }
                    i2++;
                }
            }
            int i3 = this.state;
            if (i3 != 1) {
                codedOutputByteBufferNano.writeInt32(6, i3);
            }
            int i4 = this.permission;
            if (i4 != 1) {
                codedOutputByteBufferNano.writeInt32(7, i4);
            }
            tracepoint[] tracepointVarArr = this.trace;
            if (tracepointVarArr != null && tracepointVarArr.length > 0) {
                while (true) {
                    tracepoint[] tracepointVarArr2 = this.trace;
                    if (i >= tracepointVarArr2.length) {
                        break;
                    }
                    tracepoint tracepointVar = tracepointVarArr2[i];
                    if (tracepointVar != null) {
                        codedOutputByteBufferNano.writeMessage(8, tracepointVar);
                    }
                    i++;
                }
            }
            boolean z = this.isFixedReadOnly;
            if (z) {
                codedOutputByteBufferNano.writeBool(9, z);
            }
            boolean z2 = this.isExported;
            if (z2) {
                codedOutputByteBufferNano.writeBool(10, z2);
            }
            if (!this.container.equals("")) {
                codedOutputByteBufferNano.writeString(11, this.container);
            }
            flag_metadata flag_metadataVar = this.metadata;
            if (flag_metadataVar != null) {
                codedOutputByteBufferNano.writeMessage(12, flag_metadataVar);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.package_.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.package_);
            }
            if (!this.name.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.name);
            }
            if (!this.namespace.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.namespace);
            }
            if (!this.description.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(4, this.description);
            }
            String[] strArr = this.bug;
            int i = 0;
            if (strArr != null && strArr.length > 0) {
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    String[] strArr2 = this.bug;
                    if (i2 >= strArr2.length) {
                        break;
                    }
                    String str = strArr2[i2];
                    if (str != null) {
                        i4++;
                        i3 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                    i2++;
                }
                computeSerializedSize = computeSerializedSize + i3 + i4;
            }
            int i5 = this.state;
            if (i5 != 1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, i5);
            }
            int i6 = this.permission;
            if (i6 != 1) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(7, i6);
            }
            tracepoint[] tracepointVarArr = this.trace;
            if (tracepointVarArr != null && tracepointVarArr.length > 0) {
                while (true) {
                    tracepoint[] tracepointVarArr2 = this.trace;
                    if (i >= tracepointVarArr2.length) {
                        break;
                    }
                    tracepoint tracepointVar = tracepointVarArr2[i];
                    if (tracepointVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, tracepointVar);
                    }
                    i++;
                }
            }
            boolean z = this.isFixedReadOnly;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(9, z);
            }
            boolean z2 = this.isExported;
            if (z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(10, z2);
            }
            if (!this.container.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(11, this.container);
            }
            flag_metadata flag_metadataVar = this.metadata;
            return flag_metadataVar != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(12, flag_metadataVar) : computeSerializedSize;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public parsed_flag mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        break;
                    case 10:
                        this.package_ = codedInputByteBufferNano.readString();
                        break;
                    case 18:
                        this.name = codedInputByteBufferNano.readString();
                        break;
                    case 26:
                        this.namespace = codedInputByteBufferNano.readString();
                        break;
                    case 34:
                        this.description = codedInputByteBufferNano.readString();
                        break;
                    case 42:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                        String[] strArr = this.bug;
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
                        this.bug = strArr2;
                        break;
                    case 48:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        if (readInt32 != 1 && readInt32 != 2) {
                            break;
                        } else {
                            this.state = readInt32;
                            break;
                        }
                    case 56:
                        int readInt322 = codedInputByteBufferNano.readInt32();
                        if (readInt322 != 1 && readInt322 != 2) {
                            break;
                        } else {
                            this.permission = readInt322;
                            break;
                        }
                    case 66:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                        tracepoint[] tracepointVarArr = this.trace;
                        int length2 = tracepointVarArr == null ? 0 : tracepointVarArr.length;
                        int i2 = repeatedFieldArrayLength2 + length2;
                        tracepoint[] tracepointVarArr2 = new tracepoint[i2];
                        if (length2 != 0) {
                            System.arraycopy(tracepointVarArr, 0, tracepointVarArr2, 0, length2);
                        }
                        while (length2 < i2 - 1) {
                            tracepoint tracepointVar = new tracepoint();
                            tracepointVarArr2[length2] = tracepointVar;
                            codedInputByteBufferNano.readMessage(tracepointVar);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        tracepoint tracepointVar2 = new tracepoint();
                        tracepointVarArr2[length2] = tracepointVar2;
                        codedInputByteBufferNano.readMessage(tracepointVar2);
                        this.trace = tracepointVarArr2;
                        break;
                    case 72:
                        this.isFixedReadOnly = codedInputByteBufferNano.readBool();
                        break;
                    case 80:
                        this.isExported = codedInputByteBufferNano.readBool();
                        break;
                    case 90:
                        this.container = codedInputByteBufferNano.readString();
                        break;
                    case 98:
                        if (this.metadata == null) {
                            this.metadata = new flag_metadata();
                        }
                        codedInputByteBufferNano.readMessage(this.metadata);
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

        public static parsed_flag parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (parsed_flag) MessageNano.mergeFrom(new parsed_flag(), bArr);
        }

        public static parsed_flag parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new parsed_flag().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class parsed_flags extends MessageNano {
        private static volatile parsed_flags[] _emptyArray;
        public parsed_flag[] parsedFlag;

        public static parsed_flags[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new parsed_flags[0];
                    }
                }
            }
            return _emptyArray;
        }

        public parsed_flags() {
            clear();
        }

        public parsed_flags clear() {
            this.parsedFlag = parsed_flag.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            parsed_flag[] parsed_flagVarArr = this.parsedFlag;
            if (parsed_flagVarArr != null && parsed_flagVarArr.length > 0) {
                int i = 0;
                while (true) {
                    parsed_flag[] parsed_flagVarArr2 = this.parsedFlag;
                    if (i >= parsed_flagVarArr2.length) {
                        break;
                    }
                    parsed_flag parsed_flagVar = parsed_flagVarArr2[i];
                    if (parsed_flagVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, parsed_flagVar);
                    }
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            parsed_flag[] parsed_flagVarArr = this.parsedFlag;
            if (parsed_flagVarArr != null && parsed_flagVarArr.length > 0) {
                int i = 0;
                while (true) {
                    parsed_flag[] parsed_flagVarArr2 = this.parsedFlag;
                    if (i >= parsed_flagVarArr2.length) {
                        break;
                    }
                    parsed_flag parsed_flagVar = parsed_flagVarArr2[i];
                    if (parsed_flagVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, parsed_flagVar);
                    }
                    i++;
                }
            }
            return computeSerializedSize;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public parsed_flags mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag != 10) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    parsed_flag[] parsed_flagVarArr = this.parsedFlag;
                    int length = parsed_flagVarArr == null ? 0 : parsed_flagVarArr.length;
                    int i = repeatedFieldArrayLength + length;
                    parsed_flag[] parsed_flagVarArr2 = new parsed_flag[i];
                    if (length != 0) {
                        System.arraycopy(parsed_flagVarArr, 0, parsed_flagVarArr2, 0, length);
                    }
                    while (length < i - 1) {
                        parsed_flag parsed_flagVar = new parsed_flag();
                        parsed_flagVarArr2[length] = parsed_flagVar;
                        codedInputByteBufferNano.readMessage(parsed_flagVar);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    parsed_flag parsed_flagVar2 = new parsed_flag();
                    parsed_flagVarArr2[length] = parsed_flagVar2;
                    codedInputByteBufferNano.readMessage(parsed_flagVar2);
                    this.parsedFlag = parsed_flagVarArr2;
                }
            }
            return this;
        }

        public static parsed_flags parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (parsed_flags) MessageNano.mergeFrom(new parsed_flags(), bArr);
        }

        public static parsed_flags parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new parsed_flags().mergeFrom(codedInputByteBufferNano);
        }
    }
}
