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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.name.equals("")) {
                output.writeString(1, this.name);
            }
            if (!this.namespace.equals("")) {
                output.writeString(2, this.namespace);
            }
            if (!this.description.equals("")) {
                output.writeString(3, this.description);
            }
            if (this.bug != null && this.bug.length > 0) {
                for (int i = 0; i < this.bug.length; i++) {
                    String element = this.bug[i];
                    if (element != null) {
                        output.writeString(4, element);
                    }
                }
            }
            if (this.isFixedReadOnly) {
                output.writeBool(5, this.isFixedReadOnly);
            }
            if (this.isExported) {
                output.writeBool(6, this.isExported);
            }
            if (this.metadata != null) {
                output.writeMessage(7, this.metadata);
            }
            super.writeTo(output);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.name.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.name);
            }
            if (!this.namespace.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(2, this.namespace);
            }
            if (!this.description.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(3, this.description);
            }
            if (this.bug != null && this.bug.length > 0) {
                int dataCount = 0;
                int dataSize = 0;
                for (int i = 0; i < this.bug.length; i++) {
                    String element = this.bug[i];
                    if (element != null) {
                        dataCount++;
                        dataSize += CodedOutputByteBufferNano.computeStringSizeNoTag(element);
                    }
                }
                size = size + dataSize + (dataCount * 1);
            }
            if (this.isFixedReadOnly) {
                size += CodedOutputByteBufferNano.computeBoolSize(5, this.isFixedReadOnly);
            }
            if (this.isExported) {
                size += CodedOutputByteBufferNano.computeBoolSize(6, this.isExported);
            }
            if (this.metadata != null) {
                return size + CodedOutputByteBufferNano.computeMessageSize(7, this.metadata);
            }
            return size;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public flag_declaration mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.name = input.readString();
                        break;
                    case 18:
                        this.namespace = input.readString();
                        break;
                    case 26:
                        this.description = input.readString();
                        break;
                    case 34:
                        int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 34);
                        int i = this.bug == null ? 0 : this.bug.length;
                        String[] newArray = new String[i + arrayLength];
                        if (i != 0) {
                            System.arraycopy(this.bug, 0, newArray, 0, i);
                        }
                        while (i < newArray.length - 1) {
                            newArray[i] = input.readString();
                            input.readTag();
                            i++;
                        }
                        newArray[i] = input.readString();
                        this.bug = newArray;
                        break;
                    case 40:
                        this.isFixedReadOnly = input.readBool();
                        break;
                    case 48:
                        this.isExported = input.readBool();
                        break;
                    case 58:
                        if (this.metadata == null) {
                            this.metadata = new flag_metadata();
                        }
                        input.readMessage(this.metadata);
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static flag_declaration parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (flag_declaration) MessageNano.mergeFrom(new flag_declaration(), data);
        }

        public static flag_declaration parseFrom(CodedInputByteBufferNano input)
                throws IOException {
            return new flag_declaration().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.purpose != 0) {
                output.writeInt32(1, this.purpose);
            }
            super.writeTo(output);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.purpose != 0) {
                return size + CodedOutputByteBufferNano.computeInt32Size(1, this.purpose);
            }
            return size;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public flag_metadata mergeFrom(CodedInputByteBufferNano input) throws IOException {
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
                                this.purpose = value;
                                break;
                        }
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static flag_metadata parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (flag_metadata) MessageNano.mergeFrom(new flag_metadata(), data);
        }

        public static flag_metadata parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new flag_metadata().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.package_.equals("")) {
                output.writeString(1, this.package_);
            }
            if (this.flag != null && this.flag.length > 0) {
                for (int i = 0; i < this.flag.length; i++) {
                    flag_declaration element = this.flag[i];
                    if (element != null) {
                        output.writeMessage(2, element);
                    }
                }
            }
            if (!this.container.equals("")) {
                output.writeString(3, this.container);
            }
            super.writeTo(output);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.package_.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.package_);
            }
            if (this.flag != null && this.flag.length > 0) {
                for (int i = 0; i < this.flag.length; i++) {
                    flag_declaration element = this.flag[i];
                    if (element != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(2, element);
                    }
                }
            }
            if (!this.container.equals("")) {
                return size + CodedOutputByteBufferNano.computeStringSize(3, this.container);
            }
            return size;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public flag_declarations mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.package_ = input.readString();
                        break;
                    case 18:
                        int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 18);
                        int i = this.flag == null ? 0 : this.flag.length;
                        flag_declaration[] newArray = new flag_declaration[i + arrayLength];
                        if (i != 0) {
                            System.arraycopy(this.flag, 0, newArray, 0, i);
                        }
                        while (i < newArray.length - 1) {
                            newArray[i] = new flag_declaration();
                            input.readMessage(newArray[i]);
                            input.readTag();
                            i++;
                        }
                        newArray[i] = new flag_declaration();
                        input.readMessage(newArray[i]);
                        this.flag = newArray;
                        break;
                    case 26:
                        this.container = input.readString();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static flag_declarations parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (flag_declarations) MessageNano.mergeFrom(new flag_declarations(), data);
        }

        public static flag_declarations parseFrom(CodedInputByteBufferNano input)
                throws IOException {
            return new flag_declarations().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.package_.equals("")) {
                output.writeString(1, this.package_);
            }
            if (!this.name.equals("")) {
                output.writeString(2, this.name);
            }
            if (this.state != 1) {
                output.writeInt32(3, this.state);
            }
            if (this.permission != 1) {
                output.writeInt32(4, this.permission);
            }
            super.writeTo(output);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.package_.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.package_);
            }
            if (!this.name.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(2, this.name);
            }
            if (this.state != 1) {
                size += CodedOutputByteBufferNano.computeInt32Size(3, this.state);
            }
            if (this.permission != 1) {
                return size + CodedOutputByteBufferNano.computeInt32Size(4, this.permission);
            }
            return size;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public flag_value mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.package_ = input.readString();
                        break;
                    case 18:
                        this.name = input.readString();
                        break;
                    case 24:
                        int value = input.readInt32();
                        switch (value) {
                            case 1:
                            case 2:
                                this.state = value;
                                break;
                        }
                    case 32:
                        int value2 = input.readInt32();
                        switch (value2) {
                            case 1:
                            case 2:
                                this.permission = value2;
                                break;
                        }
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static flag_value parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (flag_value) MessageNano.mergeFrom(new flag_value(), data);
        }

        public static flag_value parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new flag_value().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.flagValue != null && this.flagValue.length > 0) {
                for (int i = 0; i < this.flagValue.length; i++) {
                    flag_value element = this.flagValue[i];
                    if (element != null) {
                        output.writeMessage(1, element);
                    }
                }
            }
            super.writeTo(output);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.flagValue != null && this.flagValue.length > 0) {
                for (int i = 0; i < this.flagValue.length; i++) {
                    flag_value element = this.flagValue[i];
                    if (element != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(1, element);
                    }
                }
            }
            return size;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public flag_values mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 10);
                        int i = this.flagValue == null ? 0 : this.flagValue.length;
                        flag_value[] newArray = new flag_value[i + arrayLength];
                        if (i != 0) {
                            System.arraycopy(this.flagValue, 0, newArray, 0, i);
                        }
                        while (i < newArray.length - 1) {
                            newArray[i] = new flag_value();
                            input.readMessage(newArray[i]);
                            input.readTag();
                            i++;
                        }
                        newArray[i] = new flag_value();
                        input.readMessage(newArray[i]);
                        this.flagValue = newArray;
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static flag_values parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (flag_values) MessageNano.mergeFrom(new flag_values(), data);
        }

        public static flag_values parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new flag_values().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.source.equals("")) {
                output.writeString(1, this.source);
            }
            if (this.state != 1) {
                output.writeInt32(2, this.state);
            }
            if (this.permission != 1) {
                output.writeInt32(3, this.permission);
            }
            super.writeTo(output);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.source.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.source);
            }
            if (this.state != 1) {
                size += CodedOutputByteBufferNano.computeInt32Size(2, this.state);
            }
            if (this.permission != 1) {
                return size + CodedOutputByteBufferNano.computeInt32Size(3, this.permission);
            }
            return size;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public tracepoint mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.source = input.readString();
                        break;
                    case 16:
                        int value = input.readInt32();
                        switch (value) {
                            case 1:
                            case 2:
                                this.state = value;
                                break;
                        }
                    case 24:
                        int value2 = input.readInt32();
                        switch (value2) {
                            case 1:
                            case 2:
                                this.permission = value2;
                                break;
                        }
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static tracepoint parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (tracepoint) MessageNano.mergeFrom(new tracepoint(), data);
        }

        public static tracepoint parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new tracepoint().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.package_.equals("")) {
                output.writeString(1, this.package_);
            }
            if (!this.name.equals("")) {
                output.writeString(2, this.name);
            }
            if (!this.namespace.equals("")) {
                output.writeString(3, this.namespace);
            }
            if (!this.description.equals("")) {
                output.writeString(4, this.description);
            }
            if (this.bug != null && this.bug.length > 0) {
                for (int i = 0; i < this.bug.length; i++) {
                    String element = this.bug[i];
                    if (element != null) {
                        output.writeString(5, element);
                    }
                }
            }
            int i2 = this.state;
            if (i2 != 1) {
                output.writeInt32(6, this.state);
            }
            if (this.permission != 1) {
                output.writeInt32(7, this.permission);
            }
            if (this.trace != null && this.trace.length > 0) {
                for (int i3 = 0; i3 < this.trace.length; i3++) {
                    tracepoint element2 = this.trace[i3];
                    if (element2 != null) {
                        output.writeMessage(8, element2);
                    }
                }
            }
            if (this.isFixedReadOnly) {
                output.writeBool(9, this.isFixedReadOnly);
            }
            if (this.isExported) {
                output.writeBool(10, this.isExported);
            }
            if (!this.container.equals("")) {
                output.writeString(11, this.container);
            }
            if (this.metadata != null) {
                output.writeMessage(12, this.metadata);
            }
            super.writeTo(output);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.package_.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.package_);
            }
            if (!this.name.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(2, this.name);
            }
            if (!this.namespace.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(3, this.namespace);
            }
            if (!this.description.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(4, this.description);
            }
            if (this.bug != null && this.bug.length > 0) {
                int dataCount = 0;
                int dataSize = 0;
                for (int i = 0; i < this.bug.length; i++) {
                    String element = this.bug[i];
                    if (element != null) {
                        dataCount++;
                        dataSize += CodedOutputByteBufferNano.computeStringSizeNoTag(element);
                    }
                }
                size = size + dataSize + (dataCount * 1);
            }
            int dataCount2 = this.state;
            if (dataCount2 != 1) {
                size += CodedOutputByteBufferNano.computeInt32Size(6, this.state);
            }
            if (this.permission != 1) {
                size += CodedOutputByteBufferNano.computeInt32Size(7, this.permission);
            }
            if (this.trace != null && this.trace.length > 0) {
                for (int i2 = 0; i2 < this.trace.length; i2++) {
                    tracepoint element2 = this.trace[i2];
                    if (element2 != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(8, element2);
                    }
                }
            }
            if (this.isFixedReadOnly) {
                size += CodedOutputByteBufferNano.computeBoolSize(9, this.isFixedReadOnly);
            }
            if (this.isExported) {
                size += CodedOutputByteBufferNano.computeBoolSize(10, this.isExported);
            }
            if (!this.container.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(11, this.container);
            }
            if (this.metadata != null) {
                return size + CodedOutputByteBufferNano.computeMessageSize(12, this.metadata);
            }
            return size;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public parsed_flag mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.package_ = input.readString();
                        break;
                    case 18:
                        this.name = input.readString();
                        break;
                    case 26:
                        this.namespace = input.readString();
                        break;
                    case 34:
                        this.description = input.readString();
                        break;
                    case 42:
                        int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 42);
                        int i = this.bug == null ? 0 : this.bug.length;
                        String[] newArray = new String[i + arrayLength];
                        if (i != 0) {
                            System.arraycopy(this.bug, 0, newArray, 0, i);
                        }
                        while (i < newArray.length - 1) {
                            newArray[i] = input.readString();
                            input.readTag();
                            i++;
                        }
                        newArray[i] = input.readString();
                        this.bug = newArray;
                        break;
                    case 48:
                        int value = input.readInt32();
                        switch (value) {
                            case 1:
                            case 2:
                                this.state = value;
                                break;
                        }
                    case 56:
                        int value2 = input.readInt32();
                        switch (value2) {
                            case 1:
                            case 2:
                                this.permission = value2;
                                break;
                        }
                    case 66:
                        int arrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(input, 66);
                        int i2 = this.trace == null ? 0 : this.trace.length;
                        tracepoint[] newArray2 = new tracepoint[i2 + arrayLength2];
                        if (i2 != 0) {
                            System.arraycopy(this.trace, 0, newArray2, 0, i2);
                        }
                        while (i2 < newArray2.length - 1) {
                            newArray2[i2] = new tracepoint();
                            input.readMessage(newArray2[i2]);
                            input.readTag();
                            i2++;
                        }
                        newArray2[i2] = new tracepoint();
                        input.readMessage(newArray2[i2]);
                        this.trace = newArray2;
                        break;
                    case 72:
                        this.isFixedReadOnly = input.readBool();
                        break;
                    case 80:
                        this.isExported = input.readBool();
                        break;
                    case 90:
                        this.container = input.readString();
                        break;
                    case 98:
                        if (this.metadata == null) {
                            this.metadata = new flag_metadata();
                        }
                        input.readMessage(this.metadata);
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static parsed_flag parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (parsed_flag) MessageNano.mergeFrom(new parsed_flag(), data);
        }

        public static parsed_flag parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new parsed_flag().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.parsedFlag != null && this.parsedFlag.length > 0) {
                for (int i = 0; i < this.parsedFlag.length; i++) {
                    parsed_flag element = this.parsedFlag[i];
                    if (element != null) {
                        output.writeMessage(1, element);
                    }
                }
            }
            super.writeTo(output);
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.parsedFlag != null && this.parsedFlag.length > 0) {
                for (int i = 0; i < this.parsedFlag.length; i++) {
                    parsed_flag element = this.parsedFlag[i];
                    if (element != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(1, element);
                    }
                }
            }
            return size;
        }

        @Override // android.internal.framework.protobuf.nano.MessageNano
        public parsed_flags mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 10);
                        int i = this.parsedFlag == null ? 0 : this.parsedFlag.length;
                        parsed_flag[] newArray = new parsed_flag[i + arrayLength];
                        if (i != 0) {
                            System.arraycopy(this.parsedFlag, 0, newArray, 0, i);
                        }
                        while (i < newArray.length - 1) {
                            newArray[i] = new parsed_flag();
                            input.readMessage(newArray[i]);
                            input.readTag();
                            i++;
                        }
                        newArray[i] = new parsed_flag();
                        input.readMessage(newArray[i]);
                        this.parsedFlag = newArray;
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static parsed_flags parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (parsed_flags) MessageNano.mergeFrom(new parsed_flags(), data);
        }

        public static parsed_flags parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new parsed_flags().mergeFrom(input);
        }
    }
}
