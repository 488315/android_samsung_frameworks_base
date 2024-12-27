package com.android.systemui.qs.nano;

import com.android.systemui.util.nano.ComponentNameProto;
import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class QsTileState extends MessageNano {
    private static volatile QsTileState[] _emptyArray;
    private Object identifier_;
    private Object optionalBooleanState_;
    private Object optionalLabel_;
    private Object optionalSecondaryLabel_;
    public int state;
    private int identifierCase_ = 0;
    private int optionalBooleanStateCase_ = 0;
    private int optionalLabelCase_ = 0;
    private int optionalSecondaryLabelCase_ = 0;

    public QsTileState() {
        clear();
    }

    public static QsTileState[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                try {
                    if (_emptyArray == null) {
                        _emptyArray = new QsTileState[0];
                    }
                } finally {
                }
            }
        }
        return _emptyArray;
    }

    public QsTileState clear() {
        this.state = 0;
        clearIdentifier();
        clearOptionalBooleanState();
        clearOptionalLabel();
        clearOptionalSecondaryLabel();
        this.cachedSize = -1;
        return this;
    }

    public QsTileState clearIdentifier() {
        this.identifierCase_ = 0;
        this.identifier_ = null;
        return this;
    }

    public QsTileState clearOptionalBooleanState() {
        this.optionalBooleanStateCase_ = 0;
        this.optionalBooleanState_ = null;
        return this;
    }

    public QsTileState clearOptionalLabel() {
        this.optionalLabelCase_ = 0;
        this.optionalLabel_ = null;
        return this;
    }

    public QsTileState clearOptionalSecondaryLabel() {
        this.optionalSecondaryLabelCase_ = 0;
        this.optionalSecondaryLabel_ = null;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        int computeStringSize = this.identifierCase_ == 1 ? CodedOutputByteBufferNano.computeStringSize(1, (String) this.identifier_) : 0;
        if (this.identifierCase_ == 2) {
            computeStringSize += CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano) this.identifier_);
        }
        int i = this.state;
        if (i != 0) {
            computeStringSize += CodedOutputByteBufferNano.computeInt32Size(3, i);
        }
        if (this.optionalBooleanStateCase_ == 4) {
            ((Boolean) this.optionalBooleanState_).getClass();
            computeStringSize += CodedOutputByteBufferNano.computeTagSize(4) + 1;
        }
        if (this.optionalLabelCase_ == 5) {
            computeStringSize += CodedOutputByteBufferNano.computeStringSize(5, (String) this.optionalLabel_);
        }
        return this.optionalSecondaryLabelCase_ == 6 ? computeStringSize + CodedOutputByteBufferNano.computeStringSize(6, (String) this.optionalSecondaryLabel_) : computeStringSize;
    }

    public QsTileState setBooleanState(boolean z) {
        this.optionalBooleanStateCase_ = 4;
        this.optionalBooleanState_ = Boolean.valueOf(z);
        return this;
    }

    public QsTileState setComponentName(ComponentNameProto componentNameProto) {
        componentNameProto.getClass();
        this.identifierCase_ = 2;
        this.identifier_ = componentNameProto;
        return this;
    }

    public QsTileState setLabel(String str) {
        this.optionalLabelCase_ = 5;
        this.optionalLabel_ = str;
        return this;
    }

    public QsTileState setSecondaryLabel(String str) {
        this.optionalSecondaryLabelCase_ = 6;
        this.optionalSecondaryLabel_ = str;
        return this;
    }

    public QsTileState setSpec(String str) {
        this.identifierCase_ = 1;
        this.identifier_ = str;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (this.identifierCase_ == 1) {
            codedOutputByteBufferNano.writeString(1, (String) this.identifier_);
        }
        if (this.identifierCase_ == 2) {
            codedOutputByteBufferNano.writeMessage(2, (MessageNano) this.identifier_);
        }
        int i = this.state;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(3, i);
        }
        if (this.optionalBooleanStateCase_ == 4) {
            boolean booleanValue = ((Boolean) this.optionalBooleanState_).booleanValue();
            codedOutputByteBufferNano.writeTag(4, 0);
            codedOutputByteBufferNano.writeRawByte(booleanValue ? 1 : 0);
        }
        if (this.optionalLabelCase_ == 5) {
            codedOutputByteBufferNano.writeString(5, (String) this.optionalLabel_);
        }
        if (this.optionalSecondaryLabelCase_ == 6) {
            codedOutputByteBufferNano.writeString(6, (String) this.optionalSecondaryLabel_);
        }
    }

    @Override // com.google.protobuf.nano.MessageNano
    public QsTileState mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                this.identifier_ = codedInputByteBufferNano.readString();
                this.identifierCase_ = 1;
            } else if (readTag == 18) {
                if (this.identifierCase_ != 2) {
                    this.identifier_ = new ComponentNameProto();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.identifier_);
                this.identifierCase_ = 2;
            } else if (readTag == 24) {
                int readRawVarint32 = codedInputByteBufferNano.readRawVarint32();
                if (readRawVarint32 == 0 || readRawVarint32 == 1 || readRawVarint32 == 2) {
                    this.state = readRawVarint32;
                }
            } else if (readTag == 32) {
                this.optionalBooleanState_ = Boolean.valueOf(codedInputByteBufferNano.readRawVarint32() != 0);
                this.optionalBooleanStateCase_ = 4;
            } else if (readTag == 42) {
                this.optionalLabel_ = codedInputByteBufferNano.readString();
                this.optionalLabelCase_ = 5;
            } else if (readTag == 50) {
                this.optionalSecondaryLabel_ = codedInputByteBufferNano.readString();
                this.optionalSecondaryLabelCase_ = 6;
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                return this;
            }
        }
    }
}
