package com.android.systemui.statusbar.notification.logging.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Notifications$Notification extends MessageNano {
    private static volatile Notifications$Notification[] _emptyArray;
    public int groupInstanceId;
    public int instanceId;
    public boolean isGroupSummary;
    public String packageName;
    public int section;
    public int uid;

    public Notifications$Notification() {
        clear();
    }

    public static Notifications$Notification[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                try {
                    if (_emptyArray == null) {
                        _emptyArray = new Notifications$Notification[0];
                    }
                } finally {
                }
            }
        }
        return _emptyArray;
    }

    public Notifications$Notification clear() {
        this.uid = 0;
        this.packageName = "";
        this.instanceId = 0;
        this.groupInstanceId = 0;
        this.isGroupSummary = false;
        this.section = 0;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        int i = this.uid;
        int computeInt32Size = i != 0 ? CodedOutputByteBufferNano.computeInt32Size(1, i) : 0;
        if (!this.packageName.equals("")) {
            computeInt32Size += CodedOutputByteBufferNano.computeStringSize(2, this.packageName);
        }
        int i2 = this.instanceId;
        if (i2 != 0) {
            computeInt32Size += CodedOutputByteBufferNano.computeInt32Size(3, i2);
        }
        int i3 = this.groupInstanceId;
        if (i3 != 0) {
            computeInt32Size += CodedOutputByteBufferNano.computeInt32Size(4, i3);
        }
        if (this.isGroupSummary) {
            computeInt32Size += CodedOutputByteBufferNano.computeTagSize(5) + 1;
        }
        int i4 = this.section;
        return i4 != 0 ? computeInt32Size + CodedOutputByteBufferNano.computeInt32Size(6, i4) : computeInt32Size;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        int i = this.uid;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(1, i);
        }
        if (!this.packageName.equals("")) {
            codedOutputByteBufferNano.writeString(2, this.packageName);
        }
        int i2 = this.instanceId;
        if (i2 != 0) {
            codedOutputByteBufferNano.writeInt32(3, i2);
        }
        int i3 = this.groupInstanceId;
        if (i3 != 0) {
            codedOutputByteBufferNano.writeInt32(4, i3);
        }
        boolean z = this.isGroupSummary;
        if (z) {
            codedOutputByteBufferNano.writeTag(5, 0);
            codedOutputByteBufferNano.writeRawByte(z ? 1 : 0);
        }
        int i4 = this.section;
        if (i4 != 0) {
            codedOutputByteBufferNano.writeInt32(6, i4);
        }
    }

    @Override // com.google.protobuf.nano.MessageNano
    public Notifications$Notification mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 8) {
                this.uid = codedInputByteBufferNano.readRawVarint32();
            } else if (readTag == 18) {
                this.packageName = codedInputByteBufferNano.readString();
            } else if (readTag == 24) {
                this.instanceId = codedInputByteBufferNano.readRawVarint32();
            } else if (readTag == 32) {
                this.groupInstanceId = codedInputByteBufferNano.readRawVarint32();
            } else if (readTag == 40) {
                this.isGroupSummary = codedInputByteBufferNano.readRawVarint32() != 0;
            } else if (readTag == 48) {
                int readRawVarint32 = codedInputByteBufferNano.readRawVarint32();
                switch (readRawVarint32) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        this.section = readRawVarint32;
                        break;
                }
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                return this;
            }
        }
    }
}
