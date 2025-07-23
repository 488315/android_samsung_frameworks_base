package com.android.systemui.communal.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalHubState extends MessageNano {
    public CommunalWidgetItem[] widgets;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CommunalWidgetItem extends MessageNano {
        private static volatile CommunalWidgetItem[] _emptyArray;
        public String componentName;
        public int rank;
        public int spanY;
        public int spanYNew;
        public int userSerialNumber;
        public int widgetId;

        public CommunalWidgetItem() {
            clear();
        }

        public static CommunalWidgetItem[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    try {
                        if (_emptyArray == null) {
                            _emptyArray = new CommunalWidgetItem[0];
                        }
                    } finally {
                    }
                }
            }
            return _emptyArray;
        }

        public CommunalWidgetItem clear() {
            this.widgetId = 0;
            this.componentName = "";
            this.rank = 0;
            this.userSerialNumber = 0;
            this.spanY = 0;
            this.spanYNew = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public int computeSerializedSize() {
            int i = this.widgetId;
            int computeInt32Size = i != 0 ? CodedOutputByteBufferNano.computeInt32Size(1, i) : 0;
            if (!this.componentName.equals("")) {
                computeInt32Size += CodedOutputByteBufferNano.computeStringSize(2, this.componentName);
            }
            int i2 = this.rank;
            if (i2 != 0) {
                computeInt32Size += CodedOutputByteBufferNano.computeInt32Size(3, i2);
            }
            int i3 = this.userSerialNumber;
            if (i3 != 0) {
                computeInt32Size += CodedOutputByteBufferNano.computeInt32Size(4, i3);
            }
            int i4 = this.spanY;
            if (i4 != 0) {
                computeInt32Size += CodedOutputByteBufferNano.computeInt32Size(5, i4);
            }
            int i5 = this.spanYNew;
            return i5 != 0 ? CodedOutputByteBufferNano.computeInt32Size(6, i5) + computeInt32Size : computeInt32Size;
        }

        @Override // com.google.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.widgetId;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            if (!this.componentName.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.componentName);
            }
            int i2 = this.rank;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i2);
            }
            int i3 = this.userSerialNumber;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i3);
            }
            int i4 = this.spanY;
            if (i4 != 0) {
                codedOutputByteBufferNano.writeInt32(5, i4);
            }
            int i5 = this.spanYNew;
            if (i5 != 0) {
                codedOutputByteBufferNano.writeInt32(6, i5);
            }
        }

        @Override // com.google.protobuf.nano.MessageNano
        public CommunalWidgetItem mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 8) {
                    this.widgetId = codedInputByteBufferNano.readRawVarint32();
                } else if (readTag == 18) {
                    this.componentName = codedInputByteBufferNano.readString();
                } else if (readTag == 24) {
                    this.rank = codedInputByteBufferNano.readRawVarint32();
                } else if (readTag == 32) {
                    this.userSerialNumber = codedInputByteBufferNano.readRawVarint32();
                } else if (readTag == 40) {
                    this.spanY = codedInputByteBufferNano.readRawVarint32();
                } else if (readTag == 48) {
                    this.spanYNew = codedInputByteBufferNano.readRawVarint32();
                } else if (!codedInputByteBufferNano.skipField(readTag)) {
                    break;
                }
            }
            return this;
        }
    }

    public CommunalHubState() {
        clear();
    }

    public static CommunalHubState parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (CommunalHubState) MessageNano.mergeFrom(new CommunalHubState(), bArr);
    }

    public CommunalHubState clear() {
        this.widgets = CommunalWidgetItem.emptyArray();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        CommunalWidgetItem[] communalWidgetItemArr = this.widgets;
        int i = 0;
        if (communalWidgetItemArr == null || communalWidgetItemArr.length <= 0) {
            return 0;
        }
        int i2 = 0;
        while (true) {
            CommunalWidgetItem[] communalWidgetItemArr2 = this.widgets;
            if (i >= communalWidgetItemArr2.length) {
                return i2;
            }
            CommunalWidgetItem communalWidgetItem = communalWidgetItemArr2[i];
            if (communalWidgetItem != null) {
                i2 = CodedOutputByteBufferNano.computeMessageSize(1, communalWidgetItem) + i2;
            }
            i++;
        }
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        CommunalWidgetItem[] communalWidgetItemArr = this.widgets;
        if (communalWidgetItemArr == null || communalWidgetItemArr.length <= 0) {
            return;
        }
        int i = 0;
        while (true) {
            CommunalWidgetItem[] communalWidgetItemArr2 = this.widgets;
            if (i >= communalWidgetItemArr2.length) {
                return;
            }
            CommunalWidgetItem communalWidgetItem = communalWidgetItemArr2[i];
            if (communalWidgetItem != null) {
                codedOutputByteBufferNano.writeMessage(1, communalWidgetItem);
            }
            i++;
        }
    }

    @Override // com.google.protobuf.nano.MessageNano
    public CommunalHubState mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            }
            if (readTag == 10) {
                int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                CommunalWidgetItem[] communalWidgetItemArr = this.widgets;
                int length = communalWidgetItemArr == null ? 0 : communalWidgetItemArr.length;
                int i = repeatedFieldArrayLength + length;
                CommunalWidgetItem[] communalWidgetItemArr2 = new CommunalWidgetItem[i];
                if (length != 0) {
                    System.arraycopy(communalWidgetItemArr, 0, communalWidgetItemArr2, 0, length);
                }
                while (length < i - 1) {
                    CommunalWidgetItem communalWidgetItem = new CommunalWidgetItem();
                    communalWidgetItemArr2[length] = communalWidgetItem;
                    codedInputByteBufferNano.readMessage(communalWidgetItem);
                    codedInputByteBufferNano.readTag();
                    length++;
                }
                CommunalWidgetItem communalWidgetItem2 = new CommunalWidgetItem();
                communalWidgetItemArr2[length] = communalWidgetItem2;
                codedInputByteBufferNano.readMessage(communalWidgetItem2);
                this.widgets = communalWidgetItemArr2;
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                break;
            }
        }
        return this;
    }
}
