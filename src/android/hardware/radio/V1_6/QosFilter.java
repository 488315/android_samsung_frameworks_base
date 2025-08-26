package android.hardware.radio.V1_6;

import android.internal.hidl.safe_union.V1_0.Monostate;
import android.media.MediaMetrics;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.lock.LsConstants;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class QosFilter {
    public ArrayList<String> localAddresses = new ArrayList<>();
    public ArrayList<String> remoteAddresses = new ArrayList<>();
    public MaybePort localPort = new MaybePort();
    public MaybePort remotePort = new MaybePort();
    public byte protocol = 0;
    public TypeOfService tos = new TypeOfService();
    public Ipv6FlowLabel flowLabel = new Ipv6FlowLabel();
    public IpsecSpi spi = new IpsecSpi();
    public byte direction = 0;
    public int precedence = 0;

    public static final class TypeOfService {
        private byte hidl_d = 0;
        private Object hidl_o;

        public TypeOfService() {
            this.hidl_o = null;
            this.hidl_o = new Monostate();
        }

        public static final class hidl_discriminator {
            public static final byte noinit = 0;
            public static final byte value = 1;

            public static final String getName(byte b) {
                if (b == 0) {
                    return "noinit";
                }
                if (b == 1) {
                    return "value";
                }
                return LsConstants.TAG_UNKNOWN;
            }

            private hidl_discriminator() {
            }
        }

        public void noinit(Monostate monostate) {
            this.hidl_d = (byte) 0;
            this.hidl_o = monostate;
        }

        public Monostate noinit() {
            if (this.hidl_d != 0) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Monostate.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (Monostate) this.hidl_o;
        }

        public void value(byte b) {
            this.hidl_d = (byte) 1;
            this.hidl_o = Byte.valueOf(b);
        }

        public byte value() {
            if (this.hidl_d != 1) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Byte.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return ((Byte) this.hidl_o).byteValue();
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != TypeOfService.class) {
                return false;
            }
            TypeOfService typeOfService = (TypeOfService) obj;
            return this.hidl_d == typeOfService.hidl_d && HidlSupport.deepEquals(this.hidl_o, typeOfService.hidl_o);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)), Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d))));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            byte b = this.hidl_d;
            if (b == 0) {
                sb.append(".noinit = ");
                sb.append(noinit());
            } else if (b == 1) {
                sb.append(".value = ");
                sb.append((int) value());
            } else {
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(2L), 0L);
        }

        public static final ArrayList<TypeOfService> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<TypeOfService> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 2, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                TypeOfService typeOfService = new TypeOfService();
                typeOfService.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 2);
                arrayList.add(typeOfService);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            byte int8 = hwBlob.getInt8(j);
            this.hidl_d = int8;
            if (int8 == 0) {
                Monostate monostate = new Monostate();
                this.hidl_o = monostate;
                monostate.readEmbeddedFromParcel(hwParcel, hwBlob, j + 1);
            } else if (int8 == 1) {
                this.hidl_o = 0;
                this.hidl_o = Byte.valueOf(hwBlob.getInt8(j + 1));
            } else {
                throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(2);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<TypeOfService> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 2);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 2);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt8(j, this.hidl_d);
            byte b = this.hidl_d;
            if (b == 0) {
                noinit().writeEmbeddedToBlob(hwBlob, j + 1);
            } else {
                if (b == 1) {
                    hwBlob.putInt8(j + 1, value());
                    return;
                }
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }
    }

    public static final class Ipv6FlowLabel {
        private byte hidl_d = 0;
        private Object hidl_o;

        public Ipv6FlowLabel() {
            this.hidl_o = null;
            this.hidl_o = new Monostate();
        }

        public static final class hidl_discriminator {
            public static final byte noinit = 0;
            public static final byte value = 1;

            public static final String getName(byte b) {
                if (b == 0) {
                    return "noinit";
                }
                if (b == 1) {
                    return "value";
                }
                return LsConstants.TAG_UNKNOWN;
            }

            private hidl_discriminator() {
            }
        }

        public void noinit(Monostate monostate) {
            this.hidl_d = (byte) 0;
            this.hidl_o = monostate;
        }

        public Monostate noinit() {
            if (this.hidl_d != 0) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Monostate.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (Monostate) this.hidl_o;
        }

        public void value(int i) {
            this.hidl_d = (byte) 1;
            this.hidl_o = Integer.valueOf(i);
        }

        public int value() {
            if (this.hidl_d != 1) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Integer.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return ((Integer) this.hidl_o).intValue();
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != Ipv6FlowLabel.class) {
                return false;
            }
            Ipv6FlowLabel ipv6FlowLabel = (Ipv6FlowLabel) obj;
            return this.hidl_d == ipv6FlowLabel.hidl_d && HidlSupport.deepEquals(this.hidl_o, ipv6FlowLabel.hidl_o);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)), Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d))));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            byte b = this.hidl_d;
            if (b == 0) {
                sb.append(".noinit = ");
                sb.append(noinit());
            } else if (b == 1) {
                sb.append(".value = ");
                sb.append(value());
            } else {
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
        }

        public static final ArrayList<Ipv6FlowLabel> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<Ipv6FlowLabel> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                Ipv6FlowLabel ipv6FlowLabel = new Ipv6FlowLabel();
                ipv6FlowLabel.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 8);
                arrayList.add(ipv6FlowLabel);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            byte int8 = hwBlob.getInt8(j);
            this.hidl_d = int8;
            if (int8 == 0) {
                Monostate monostate = new Monostate();
                this.hidl_o = monostate;
                monostate.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
            } else if (int8 == 1) {
                this.hidl_o = 0;
                this.hidl_o = Integer.valueOf(hwBlob.getInt32(j + 4));
            } else {
                throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(8);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Ipv6FlowLabel> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 8);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt8(j, this.hidl_d);
            byte b = this.hidl_d;
            if (b == 0) {
                noinit().writeEmbeddedToBlob(hwBlob, j + 4);
            } else {
                if (b == 1) {
                    hwBlob.putInt32(j + 4, value());
                    return;
                }
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }
    }

    public static final class IpsecSpi {
        private byte hidl_d = 0;
        private Object hidl_o;

        public IpsecSpi() {
            this.hidl_o = null;
            this.hidl_o = new Monostate();
        }

        public static final class hidl_discriminator {
            public static final byte noinit = 0;
            public static final byte value = 1;

            public static final String getName(byte b) {
                if (b == 0) {
                    return "noinit";
                }
                if (b == 1) {
                    return "value";
                }
                return LsConstants.TAG_UNKNOWN;
            }

            private hidl_discriminator() {
            }
        }

        public void noinit(Monostate monostate) {
            this.hidl_d = (byte) 0;
            this.hidl_o = monostate;
        }

        public Monostate noinit() {
            if (this.hidl_d != 0) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Monostate.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (Monostate) this.hidl_o;
        }

        public void value(int i) {
            this.hidl_d = (byte) 1;
            this.hidl_o = Integer.valueOf(i);
        }

        public int value() {
            if (this.hidl_d != 1) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Integer.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return ((Integer) this.hidl_o).intValue();
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != IpsecSpi.class) {
                return false;
            }
            IpsecSpi ipsecSpi = (IpsecSpi) obj;
            return this.hidl_d == ipsecSpi.hidl_d && HidlSupport.deepEquals(this.hidl_o, ipsecSpi.hidl_o);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)), Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d))));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            byte b = this.hidl_d;
            if (b == 0) {
                sb.append(".noinit = ");
                sb.append(noinit());
            } else if (b == 1) {
                sb.append(".value = ");
                sb.append(value());
            } else {
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
        }

        public static final ArrayList<IpsecSpi> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<IpsecSpi> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                IpsecSpi ipsecSpi = new IpsecSpi();
                ipsecSpi.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 8);
                arrayList.add(ipsecSpi);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            byte int8 = hwBlob.getInt8(j);
            this.hidl_d = int8;
            if (int8 == 0) {
                Monostate monostate = new Monostate();
                this.hidl_o = monostate;
                monostate.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
            } else if (int8 == 1) {
                this.hidl_o = 0;
                this.hidl_o = Integer.valueOf(hwBlob.getInt32(j + 4));
            } else {
                throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(8);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<IpsecSpi> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 8);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt8(j, this.hidl_d);
            byte b = this.hidl_d;
            if (b == 0) {
                noinit().writeEmbeddedToBlob(hwBlob, j + 4);
            } else {
                if (b == 1) {
                    hwBlob.putInt32(j + 4, value());
                    return;
                }
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != QosFilter.class) {
            return false;
        }
        QosFilter qosFilter = (QosFilter) obj;
        return HidlSupport.deepEquals(this.localAddresses, qosFilter.localAddresses) && HidlSupport.deepEquals(this.remoteAddresses, qosFilter.remoteAddresses) && HidlSupport.deepEquals(this.localPort, qosFilter.localPort) && HidlSupport.deepEquals(this.remotePort, qosFilter.remotePort) && this.protocol == qosFilter.protocol && HidlSupport.deepEquals(this.tos, qosFilter.tos) && HidlSupport.deepEquals(this.flowLabel, qosFilter.flowLabel) && HidlSupport.deepEquals(this.spi, qosFilter.spi) && this.direction == qosFilter.direction && this.precedence == qosFilter.precedence;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.localAddresses)), Integer.valueOf(HidlSupport.deepHashCode(this.remoteAddresses)), Integer.valueOf(HidlSupport.deepHashCode(this.localPort)), Integer.valueOf(HidlSupport.deepHashCode(this.remotePort)), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.protocol))), Integer.valueOf(HidlSupport.deepHashCode(this.tos)), Integer.valueOf(HidlSupport.deepHashCode(this.flowLabel)), Integer.valueOf(HidlSupport.deepHashCode(this.spi)), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.direction))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.precedence))));
    }

    public final String toString() {
        return "{.localAddresses = " + this.localAddresses + ", .remoteAddresses = " + this.remoteAddresses + ", .localPort = " + this.localPort + ", .remotePort = " + this.remotePort + ", .protocol = " + QosProtocol.toString(this.protocol) + ", .tos = " + this.tos + ", .flowLabel = " + this.flowLabel + ", .spi = " + this.spi + ", .direction = " + QosFilterDirection.toString(this.direction) + ", .precedence = " + this.precedence + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final ArrayList<QosFilter> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<QosFilter> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            QosFilter qosFilter = new QosFilter();
            qosFilter.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 88);
            arrayList.add(qosFilter);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        int int32 = hwBlob.getInt32(j + 8);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j, true);
        this.localAddresses.clear();
        for (int i = 0; i < int32; i++) {
            new String();
            int i2 = i * 16;
            String string = embeddedBuffer.getString(i2);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, embeddedBuffer.handle(), i2, false);
            this.localAddresses.add(string);
        }
        int int322 = hwBlob.getInt32(j + 24);
        HwBlob embeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j + 16, true);
        this.remoteAddresses.clear();
        for (int i3 = 0; i3 < int322; i3++) {
            new String();
            int i4 = i3 * 16;
            String string2 = embeddedBuffer2.getString(i4);
            hwParcel.readEmbeddedBuffer(string2.getBytes().length + 1, embeddedBuffer2.handle(), i4, false);
            this.remoteAddresses.add(string2);
        }
        this.localPort.readEmbeddedFromParcel(hwParcel, hwBlob, j + 32);
        this.remotePort.readEmbeddedFromParcel(hwParcel, hwBlob, j + 44);
        this.protocol = hwBlob.getInt8(j + 56);
        this.tos.readEmbeddedFromParcel(hwParcel, hwBlob, j + 57);
        this.flowLabel.readEmbeddedFromParcel(hwParcel, hwBlob, j + 60);
        this.spi.readEmbeddedFromParcel(hwParcel, hwBlob, j + 68);
        this.direction = hwBlob.getInt8(j + 76);
        this.precedence = hwBlob.getInt32(j + 80);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<QosFilter> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        int size = this.localAddresses.size();
        hwBlob.putInt32(8 + j, size);
        hwBlob.putBool(12 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.localAddresses.get(i));
        }
        hwBlob.putBlob(j, hwBlob2);
        int size2 = this.remoteAddresses.size();
        long j2 = 16 + j;
        hwBlob.putInt32(24 + j, size2);
        hwBlob.putBool(28 + j, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putString(i2 * 16, this.remoteAddresses.get(i2));
        }
        hwBlob.putBlob(j2, hwBlob3);
        this.localPort.writeEmbeddedToBlob(hwBlob, 32 + j);
        this.remotePort.writeEmbeddedToBlob(hwBlob, 44 + j);
        hwBlob.putInt8(56 + j, this.protocol);
        this.tos.writeEmbeddedToBlob(hwBlob, 57 + j);
        this.flowLabel.writeEmbeddedToBlob(hwBlob, 60 + j);
        this.spi.writeEmbeddedToBlob(hwBlob, 68 + j);
        hwBlob.putInt8(76 + j, this.direction);
        hwBlob.putInt32(j + 80, this.precedence);
    }
}
