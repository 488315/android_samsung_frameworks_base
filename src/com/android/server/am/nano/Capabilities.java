package com.android.server.am.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes6.dex */
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
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        Capability[] capabilityArr = this.values;
        int i = 0;
        if (capabilityArr != null && capabilityArr.length > 0) {
            int i2 = 0;
            while (true) {
                Capability[] capabilityArr2 = this.values;
                if (i2 >= capabilityArr2.length) {
                    break;
                }
                Capability capability = capabilityArr2[i2];
                if (capability != null) {
                    codedOutputByteBufferNano.writeMessage(1, capability);
                }
                i2++;
            }
        }
        VMCapability[] vMCapabilityArr = this.vmCapabilities;
        if (vMCapabilityArr != null && vMCapabilityArr.length > 0) {
            int i3 = 0;
            while (true) {
                VMCapability[] vMCapabilityArr2 = this.vmCapabilities;
                if (i3 >= vMCapabilityArr2.length) {
                    break;
                }
                VMCapability vMCapability = vMCapabilityArr2[i3];
                if (vMCapability != null) {
                    codedOutputByteBufferNano.writeMessage(2, vMCapability);
                }
                i3++;
            }
        }
        FrameworkCapability[] frameworkCapabilityArr = this.frameworkCapabilities;
        if (frameworkCapabilityArr != null && frameworkCapabilityArr.length > 0) {
            while (true) {
                FrameworkCapability[] frameworkCapabilityArr2 = this.frameworkCapabilities;
                if (i >= frameworkCapabilityArr2.length) {
                    break;
                }
                FrameworkCapability frameworkCapability = frameworkCapabilityArr2[i];
                if (frameworkCapability != null) {
                    codedOutputByteBufferNano.writeMessage(3, frameworkCapability);
                }
                i++;
            }
        }
        VMInfo vMInfo = this.vmInfo;
        if (vMInfo != null) {
            codedOutputByteBufferNano.writeMessage(4, vMInfo);
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int iComputeSerializedSize = super.computeSerializedSize();
        Capability[] capabilityArr = this.values;
        int i = 0;
        if (capabilityArr != null && capabilityArr.length > 0) {
            int i2 = 0;
            while (true) {
                Capability[] capabilityArr2 = this.values;
                if (i2 >= capabilityArr2.length) {
                    break;
                }
                Capability capability = capabilityArr2[i2];
                if (capability != null) {
                    iComputeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, capability);
                }
                i2++;
            }
        }
        VMCapability[] vMCapabilityArr = this.vmCapabilities;
        if (vMCapabilityArr != null && vMCapabilityArr.length > 0) {
            int i3 = 0;
            while (true) {
                VMCapability[] vMCapabilityArr2 = this.vmCapabilities;
                if (i3 >= vMCapabilityArr2.length) {
                    break;
                }
                VMCapability vMCapability = vMCapabilityArr2[i3];
                if (vMCapability != null) {
                    iComputeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, vMCapability);
                }
                i3++;
            }
        }
        FrameworkCapability[] frameworkCapabilityArr = this.frameworkCapabilities;
        if (frameworkCapabilityArr != null && frameworkCapabilityArr.length > 0) {
            while (true) {
                FrameworkCapability[] frameworkCapabilityArr2 = this.frameworkCapabilities;
                if (i >= frameworkCapabilityArr2.length) {
                    break;
                }
                FrameworkCapability frameworkCapability = frameworkCapabilityArr2[i];
                if (frameworkCapability != null) {
                    iComputeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, frameworkCapability);
                }
                i++;
            }
        }
        VMInfo vMInfo = this.vmInfo;
        return vMInfo != null ? iComputeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(4, vMInfo) : iComputeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public Capabilities mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int tag = codedInputByteBufferNano.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == 10) {
                int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                Capability[] capabilityArr = this.values;
                int length = capabilityArr == null ? 0 : capabilityArr.length;
                int i = repeatedFieldArrayLength + length;
                Capability[] capabilityArr2 = new Capability[i];
                if (length != 0) {
                    System.arraycopy(capabilityArr, 0, capabilityArr2, 0, length);
                }
                while (length < i - 1) {
                    Capability capability = new Capability();
                    capabilityArr2[length] = capability;
                    codedInputByteBufferNano.readMessage(capability);
                    codedInputByteBufferNano.readTag();
                    length++;
                }
                Capability capability2 = new Capability();
                capabilityArr2[length] = capability2;
                codedInputByteBufferNano.readMessage(capability2);
                this.values = capabilityArr2;
            } else if (tag == 18) {
                int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                VMCapability[] vMCapabilityArr = this.vmCapabilities;
                int length2 = vMCapabilityArr == null ? 0 : vMCapabilityArr.length;
                int i2 = repeatedFieldArrayLength2 + length2;
                VMCapability[] vMCapabilityArr2 = new VMCapability[i2];
                if (length2 != 0) {
                    System.arraycopy(vMCapabilityArr, 0, vMCapabilityArr2, 0, length2);
                }
                while (length2 < i2 - 1) {
                    VMCapability vMCapability = new VMCapability();
                    vMCapabilityArr2[length2] = vMCapability;
                    codedInputByteBufferNano.readMessage(vMCapability);
                    codedInputByteBufferNano.readTag();
                    length2++;
                }
                VMCapability vMCapability2 = new VMCapability();
                vMCapabilityArr2[length2] = vMCapability2;
                codedInputByteBufferNano.readMessage(vMCapability2);
                this.vmCapabilities = vMCapabilityArr2;
            } else if (tag == 26) {
                int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                FrameworkCapability[] frameworkCapabilityArr = this.frameworkCapabilities;
                int length3 = frameworkCapabilityArr == null ? 0 : frameworkCapabilityArr.length;
                int i3 = repeatedFieldArrayLength3 + length3;
                FrameworkCapability[] frameworkCapabilityArr2 = new FrameworkCapability[i3];
                if (length3 != 0) {
                    System.arraycopy(frameworkCapabilityArr, 0, frameworkCapabilityArr2, 0, length3);
                }
                while (length3 < i3 - 1) {
                    FrameworkCapability frameworkCapability = new FrameworkCapability();
                    frameworkCapabilityArr2[length3] = frameworkCapability;
                    codedInputByteBufferNano.readMessage(frameworkCapability);
                    codedInputByteBufferNano.readTag();
                    length3++;
                }
                FrameworkCapability frameworkCapability2 = new FrameworkCapability();
                frameworkCapabilityArr2[length3] = frameworkCapability2;
                codedInputByteBufferNano.readMessage(frameworkCapability2);
                this.frameworkCapabilities = frameworkCapabilityArr2;
            } else if (tag != 34) {
                if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, tag)) {
                    break;
                }
            } else {
                if (this.vmInfo == null) {
                    this.vmInfo = new VMInfo();
                }
                codedInputByteBufferNano.readMessage(this.vmInfo);
            }
        }
        return this;
    }

    public static Capabilities parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (Capabilities) MessageNano.mergeFrom(new Capabilities(), bArr);
    }

    public static Capabilities parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new Capabilities().mergeFrom(codedInputByteBufferNano);
    }
}
