package com.android.systemui.statusbar.notification.logging.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

/* loaded from: classes3.dex */
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
        int iComputeInt32Size = i != 0 ? CodedOutputByteBufferNano.computeInt32Size(1, i) : 0;
        if (!this.packageName.equals("")) {
            iComputeInt32Size += CodedOutputByteBufferNano.computeStringSize(2, this.packageName);
        }
        int i2 = this.instanceId;
        if (i2 != 0) {
            iComputeInt32Size += CodedOutputByteBufferNano.computeInt32Size(3, i2);
        }
        int i3 = this.groupInstanceId;
        if (i3 != 0) {
            iComputeInt32Size += CodedOutputByteBufferNano.computeInt32Size(4, i3);
        }
        if (this.isGroupSummary) {
            iComputeInt32Size += CodedOutputByteBufferNano.computeTagSize(5) + 1;
        }
        int i4 = this.section;
        return i4 != 0 ? CodedOutputByteBufferNano.computeInt32Size(6, i4) + iComputeInt32Size : iComputeInt32Size;
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
            int tag = codedInputByteBufferNano.readTag();
            if (tag != 0) {
                if (tag == 8) {
                    this.uid = codedInputByteBufferNano.readRawVarint32();
                } else if (tag == 18) {
                    this.packageName = codedInputByteBufferNano.readString();
                } else if (tag == 24) {
                    this.instanceId = codedInputByteBufferNano.readRawVarint32();
                } else if (tag == 32) {
                    this.groupInstanceId = codedInputByteBufferNano.readRawVarint32();
                } else if (tag == 40) {
                    this.isGroupSummary = codedInputByteBufferNano.readRawVarint32() != 0;
                } else if (tag == 48) {
                    int rawVarint32 = codedInputByteBufferNano.readRawVarint32();
                    switch (rawVarint32) {
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
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            this.section = rawVarint32;
                            break;
                    }
                } else if (!codedInputByteBufferNano.skipField(tag)) {
                }
            }
        }
        return this;
    }
}
