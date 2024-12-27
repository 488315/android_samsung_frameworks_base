package com.android.server.am.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;

import java.io.IOException;

/* loaded from: classes5.dex */
public final class Capabilities extends MessageNano {
    private static volatile Capabilities[] _emptyArray;
    public FrameworkCapability[] frameworkCapabilities;
    public Capability[] values;
    public VMCapability[] vmCapabilities;
    public VMInfo vmInfo;

    public static Capabilities[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new Capabilities[0];
                }
            }
        }
        return _emptyArray;
    }

    public Capabilities() {
        clear();
    }

    public Capabilities clear() {
        this.values = Capability.emptyArray();
        this.vmCapabilities = VMCapability.emptyArray();
        this.frameworkCapabilities = FrameworkCapability.emptyArray();
        this.vmInfo = null;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
        if (this.values != null && this.values.length > 0) {
            for (int i = 0; i < this.values.length; i++) {
                Capability element = this.values[i];
                if (element != null) {
                    output.writeMessage(1, element);
                }
            }
        }
        if (this.vmCapabilities != null && this.vmCapabilities.length > 0) {
            for (int i2 = 0; i2 < this.vmCapabilities.length; i2++) {
                VMCapability element2 = this.vmCapabilities[i2];
                if (element2 != null) {
                    output.writeMessage(2, element2);
                }
            }
        }
        if (this.frameworkCapabilities != null && this.frameworkCapabilities.length > 0) {
            for (int i3 = 0; i3 < this.frameworkCapabilities.length; i3++) {
                FrameworkCapability element3 = this.frameworkCapabilities[i3];
                if (element3 != null) {
                    output.writeMessage(3, element3);
                }
            }
        }
        if (this.vmInfo != null) {
            output.writeMessage(4, this.vmInfo);
        }
        super.writeTo(output);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int size = super.computeSerializedSize();
        if (this.values != null && this.values.length > 0) {
            for (int i = 0; i < this.values.length; i++) {
                Capability element = this.values[i];
                if (element != null) {
                    size += CodedOutputByteBufferNano.computeMessageSize(1, element);
                }
            }
        }
        if (this.vmCapabilities != null && this.vmCapabilities.length > 0) {
            for (int i2 = 0; i2 < this.vmCapabilities.length; i2++) {
                VMCapability element2 = this.vmCapabilities[i2];
                if (element2 != null) {
                    size += CodedOutputByteBufferNano.computeMessageSize(2, element2);
                }
            }
        }
        if (this.frameworkCapabilities != null && this.frameworkCapabilities.length > 0) {
            for (int i3 = 0; i3 < this.frameworkCapabilities.length; i3++) {
                FrameworkCapability element3 = this.frameworkCapabilities[i3];
                if (element3 != null) {
                    size += CodedOutputByteBufferNano.computeMessageSize(3, element3);
                }
            }
        }
        if (this.vmInfo != null) {
            return size + CodedOutputByteBufferNano.computeMessageSize(4, this.vmInfo);
        }
        return size;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public Capabilities mergeFrom(CodedInputByteBufferNano input) throws IOException {
        while (true) {
            int tag = input.readTag();
            switch (tag) {
                case 0:
                    return this;
                case 10:
                    int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 10);
                    int i = this.values == null ? 0 : this.values.length;
                    Capability[] newArray = new Capability[i + arrayLength];
                    if (i != 0) {
                        System.arraycopy(this.values, 0, newArray, 0, i);
                    }
                    while (i < newArray.length - 1) {
                        newArray[i] = new Capability();
                        input.readMessage(newArray[i]);
                        input.readTag();
                        i++;
                    }
                    newArray[i] = new Capability();
                    input.readMessage(newArray[i]);
                    this.values = newArray;
                    break;
                case 18:
                    int arrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(input, 18);
                    int i2 = this.vmCapabilities == null ? 0 : this.vmCapabilities.length;
                    VMCapability[] newArray2 = new VMCapability[i2 + arrayLength2];
                    if (i2 != 0) {
                        System.arraycopy(this.vmCapabilities, 0, newArray2, 0, i2);
                    }
                    while (i2 < newArray2.length - 1) {
                        newArray2[i2] = new VMCapability();
                        input.readMessage(newArray2[i2]);
                        input.readTag();
                        i2++;
                    }
                    newArray2[i2] = new VMCapability();
                    input.readMessage(newArray2[i2]);
                    this.vmCapabilities = newArray2;
                    break;
                case 26:
                    int arrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(input, 26);
                    int i3 =
                            this.frameworkCapabilities == null
                                    ? 0
                                    : this.frameworkCapabilities.length;
                    FrameworkCapability[] newArray3 = new FrameworkCapability[i3 + arrayLength3];
                    if (i3 != 0) {
                        System.arraycopy(this.frameworkCapabilities, 0, newArray3, 0, i3);
                    }
                    while (i3 < newArray3.length - 1) {
                        newArray3[i3] = new FrameworkCapability();
                        input.readMessage(newArray3[i3]);
                        input.readTag();
                        i3++;
                    }
                    newArray3[i3] = new FrameworkCapability();
                    input.readMessage(newArray3[i3]);
                    this.frameworkCapabilities = newArray3;
                    break;
                case 34:
                    if (this.vmInfo == null) {
                        this.vmInfo = new VMInfo();
                    }
                    input.readMessage(this.vmInfo);
                    break;
                default:
                    if (!WireFormatNano.parseUnknownField(input, tag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static Capabilities parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
        return (Capabilities) MessageNano.mergeFrom(new Capabilities(), data);
    }

    public static Capabilities parseFrom(CodedInputByteBufferNano input) throws IOException {
        return new Capabilities().mergeFrom(input);
    }
}
