package android.hardware.radio.V1_5;

import android.media.MediaMetrics;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.lock.LsConstants;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class RadioAccessSpecifier {
    public int radioAccessNetwork = 0;
    public Bands bands = new Bands();
    public ArrayList<Integer> channels = new ArrayList<>();

    public static final class Bands {
        private byte hidl_d = 0;
        private Object hidl_o;

        public Bands() {
            this.hidl_o = null;
            this.hidl_o = new ArrayList();
        }

        public static final class hidl_discriminator {
            public static final byte eutranBands = 2;
            public static final byte geranBands = 0;
            public static final byte ngranBands = 3;
            public static final byte utranBands = 1;

            public static final String getName(byte b) {
                if (b == 0) {
                    return "geranBands";
                }
                if (b == 1) {
                    return "utranBands";
                }
                if (b == 2) {
                    return "eutranBands";
                }
                if (b == 3) {
                    return "ngranBands";
                }
                return LsConstants.TAG_UNKNOWN;
            }

            private hidl_discriminator() {
            }
        }

        public void geranBands(ArrayList<Integer> arrayList) {
            this.hidl_d = (byte) 0;
            this.hidl_o = arrayList;
        }

        public ArrayList<Integer> geranBands() {
            if (this.hidl_d != 0) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !ArrayList.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (ArrayList) this.hidl_o;
        }

        public void utranBands(ArrayList<Integer> arrayList) {
            this.hidl_d = (byte) 1;
            this.hidl_o = arrayList;
        }

        public ArrayList<Integer> utranBands() {
            if (this.hidl_d != 1) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !ArrayList.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (ArrayList) this.hidl_o;
        }

        public void eutranBands(ArrayList<Integer> arrayList) {
            this.hidl_d = (byte) 2;
            this.hidl_o = arrayList;
        }

        public ArrayList<Integer> eutranBands() {
            if (this.hidl_d != 2) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !ArrayList.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (ArrayList) this.hidl_o;
        }

        public void ngranBands(ArrayList<Integer> arrayList) {
            this.hidl_d = (byte) 3;
            this.hidl_o = arrayList;
        }

        public ArrayList<Integer> ngranBands() {
            if (this.hidl_d != 3) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !ArrayList.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (ArrayList) this.hidl_o;
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != Bands.class) {
                return false;
            }
            Bands bands = (Bands) obj;
            return this.hidl_d == bands.hidl_d && HidlSupport.deepEquals(this.hidl_o, bands.hidl_o);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)), Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d))));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            byte b = this.hidl_d;
            if (b == 0) {
                sb.append(".geranBands = ");
                sb.append(geranBands());
            } else if (b == 1) {
                sb.append(".utranBands = ");
                sb.append(utranBands());
            } else if (b == 2) {
                sb.append(".eutranBands = ");
                sb.append(eutranBands());
            } else if (b == 3) {
                sb.append(".ngranBands = ");
                sb.append(ngranBands());
            } else {
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
        }

        public static final ArrayList<Bands> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<Bands> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                Bands bands = new Bands();
                bands.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 24);
                arrayList.add(bands);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            byte int8 = hwBlob.getInt8(j);
            this.hidl_d = int8;
            int i = 0;
            if (int8 == 0) {
                this.hidl_o = new ArrayList();
                int int32 = hwBlob.getInt32(j + 16);
                HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j + 8, true);
                ((ArrayList) this.hidl_o).clear();
                while (i < int32) {
                    ((ArrayList) this.hidl_o).add(Integer.valueOf(embeddedBuffer.getInt32(i * 4)));
                    i++;
                }
                return;
            }
            if (int8 == 1) {
                this.hidl_o = new ArrayList();
                int int322 = hwBlob.getInt32(j + 16);
                HwBlob embeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 4, hwBlob.handle(), j + 8, true);
                ((ArrayList) this.hidl_o).clear();
                while (i < int322) {
                    ((ArrayList) this.hidl_o).add(Integer.valueOf(embeddedBuffer2.getInt32(i * 4)));
                    i++;
                }
                return;
            }
            if (int8 == 2) {
                this.hidl_o = new ArrayList();
                int int323 = hwBlob.getInt32(j + 16);
                HwBlob embeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 4, hwBlob.handle(), j + 8, true);
                ((ArrayList) this.hidl_o).clear();
                while (i < int323) {
                    ((ArrayList) this.hidl_o).add(Integer.valueOf(embeddedBuffer3.getInt32(i * 4)));
                    i++;
                }
                return;
            }
            if (int8 == 3) {
                this.hidl_o = new ArrayList();
                int int324 = hwBlob.getInt32(j + 16);
                HwBlob embeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 4, hwBlob.handle(), j + 8, true);
                ((ArrayList) this.hidl_o).clear();
                while (i < int324) {
                    ((ArrayList) this.hidl_o).add(Integer.valueOf(embeddedBuffer4.getInt32(i * 4)));
                    i++;
                }
                return;
            }
            throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(24);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Bands> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 24);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt8(j, this.hidl_d);
            byte b = this.hidl_d;
            int i = 0;
            if (b == 0) {
                int size = geranBands().size();
                long j2 = 8 + j;
                hwBlob.putInt32(16 + j, size);
                hwBlob.putBool(j + 20, false);
                HwBlob hwBlob2 = new HwBlob(size * 4);
                while (i < size) {
                    hwBlob2.putInt32(i * 4, geranBands().get(i).intValue());
                    i++;
                }
                hwBlob.putBlob(j2, hwBlob2);
                return;
            }
            if (b == 1) {
                int size2 = utranBands().size();
                long j3 = 8 + j;
                hwBlob.putInt32(16 + j, size2);
                hwBlob.putBool(j + 20, false);
                HwBlob hwBlob3 = new HwBlob(size2 * 4);
                while (i < size2) {
                    hwBlob3.putInt32(i * 4, utranBands().get(i).intValue());
                    i++;
                }
                hwBlob.putBlob(j3, hwBlob3);
                return;
            }
            if (b == 2) {
                int size3 = eutranBands().size();
                long j4 = 8 + j;
                hwBlob.putInt32(16 + j, size3);
                hwBlob.putBool(j + 20, false);
                HwBlob hwBlob4 = new HwBlob(size3 * 4);
                while (i < size3) {
                    hwBlob4.putInt32(i * 4, eutranBands().get(i).intValue());
                    i++;
                }
                hwBlob.putBlob(j4, hwBlob4);
                return;
            }
            if (b == 3) {
                int size4 = ngranBands().size();
                long j5 = 8 + j;
                hwBlob.putInt32(16 + j, size4);
                hwBlob.putBool(j + 20, false);
                HwBlob hwBlob5 = new HwBlob(size4 * 4);
                while (i < size4) {
                    hwBlob5.putInt32(i * 4, ngranBands().get(i).intValue());
                    i++;
                }
                hwBlob.putBlob(j5, hwBlob5);
                return;
            }
            throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != RadioAccessSpecifier.class) {
            return false;
        }
        RadioAccessSpecifier radioAccessSpecifier = (RadioAccessSpecifier) obj;
        return this.radioAccessNetwork == radioAccessSpecifier.radioAccessNetwork && HidlSupport.deepEquals(this.bands, radioAccessSpecifier.bands) && HidlSupport.deepEquals(this.channels, radioAccessSpecifier.channels);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.radioAccessNetwork))), Integer.valueOf(HidlSupport.deepHashCode(this.bands)), Integer.valueOf(HidlSupport.deepHashCode(this.channels)));
    }

    public final String toString() {
        return "{.radioAccessNetwork = " + RadioAccessNetworks.toString(this.radioAccessNetwork) + ", .bands = " + this.bands + ", .channels = " + this.channels + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final ArrayList<RadioAccessSpecifier> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RadioAccessSpecifier> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            RadioAccessSpecifier radioAccessSpecifier = new RadioAccessSpecifier();
            radioAccessSpecifier.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 48);
            arrayList.add(radioAccessSpecifier);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.radioAccessNetwork = hwBlob.getInt32(j);
        this.bands.readEmbeddedFromParcel(hwParcel, hwBlob, 8 + j);
        long j2 = j + 32;
        int int32 = hwBlob.getInt32(j + 40);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j2, true);
        this.channels.clear();
        for (int i = 0; i < int32; i++) {
            this.channels.add(Integer.valueOf(embeddedBuffer.getInt32(i * 4)));
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RadioAccessSpecifier> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.radioAccessNetwork);
        this.bands.writeEmbeddedToBlob(hwBlob, 8 + j);
        int size = this.channels.size();
        long j2 = 32 + j;
        hwBlob.putInt32(40 + j, size);
        hwBlob.putBool(j + 44, false);
        HwBlob hwBlob2 = new HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.channels.get(i).intValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
