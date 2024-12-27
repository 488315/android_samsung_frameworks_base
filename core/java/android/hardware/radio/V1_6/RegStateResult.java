package android.hardware.radio.V1_6;

import android.hardware.radio.V1_0.RegState;
import android.hardware.radio.V1_4.RadioTechnology;
import android.hardware.radio.V1_5.CellIdentity;
import android.hardware.radio.V1_5.RegStateResult;
import android.hardware.radio.V1_5.RegistrationFailCause;
import android.internal.hidl.safe_union.V1_0.Monostate;
import android.media.MediaMetrics;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

public final class RegStateResult {
    public int regState = 0;
    public int rat = 0;
    public int reasonForDenial = 0;
    public CellIdentity cellIdentity = new CellIdentity();
    public String registeredPlmn = new String();
    public AccessTechnologySpecificInfo accessTechnologySpecificInfo =
            new AccessTechnologySpecificInfo();

    public static final class AccessTechnologySpecificInfo {
        private byte hidl_d = 0;
        private Object hidl_o;

        public AccessTechnologySpecificInfo() {
            this.hidl_o = null;
            this.hidl_o = new Monostate();
        }

        public static final class hidl_discriminator {
            public static final byte cdmaInfo = 1;
            public static final byte eutranInfo = 2;
            public static final byte geranDtmSupported = 4;
            public static final byte ngranNrVopsInfo = 3;
            public static final byte noinit = 0;

            public static final String getName(byte value) {
                switch (value) {
                    case 0:
                        return "noinit";
                    case 1:
                        return "cdmaInfo";
                    case 2:
                        return "eutranInfo";
                    case 3:
                        return "ngranNrVopsInfo";
                    case 4:
                        return "geranDtmSupported";
                    default:
                        return "Unknown";
                }
            }

            private hidl_discriminator() {}
        }

        public void noinit(Monostate noinit) {
            this.hidl_d = (byte) 0;
            this.hidl_o = noinit;
        }

        public Monostate noinit() {
            if (this.hidl_d != 0) {
                String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
                throw new IllegalStateException(
                        "Read access to inactive union components is disallowed. Discriminator"
                                + " value is "
                                + ((int) this.hidl_d)
                                + " (corresponding to "
                                + hidl_discriminator.getName(this.hidl_d)
                                + "), and hidl_o is of type "
                                + className
                                + MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !Monostate.class.isInstance(this.hidl_o)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (Monostate) this.hidl_o;
        }

        public void cdmaInfo(
                RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo cdmaInfo) {
            this.hidl_d = (byte) 1;
            this.hidl_o = cdmaInfo;
        }

        public RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo cdmaInfo() {
            if (this.hidl_d != 1) {
                String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
                throw new IllegalStateException(
                        "Read access to inactive union components is disallowed. Discriminator"
                                + " value is "
                                + ((int) this.hidl_d)
                                + " (corresponding to "
                                + hidl_discriminator.getName(this.hidl_d)
                                + "), and hidl_o is of type "
                                + className
                                + MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null
                    && !RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo.class
                            .isInstance(this.hidl_o)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo)
                    this.hidl_o;
        }

        public void eutranInfo(
                RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo eutranInfo) {
            this.hidl_d = (byte) 2;
            this.hidl_o = eutranInfo;
        }

        public RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo eutranInfo() {
            if (this.hidl_d != 2) {
                String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
                throw new IllegalStateException(
                        "Read access to inactive union components is disallowed. Discriminator"
                                + " value is "
                                + ((int) this.hidl_d)
                                + " (corresponding to "
                                + hidl_discriminator.getName(this.hidl_d)
                                + "), and hidl_o is of type "
                                + className
                                + MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null
                    && !RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo.class
                            .isInstance(this.hidl_o)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo) this.hidl_o;
        }

        public void ngranNrVopsInfo(NrVopsInfo ngranNrVopsInfo) {
            this.hidl_d = (byte) 3;
            this.hidl_o = ngranNrVopsInfo;
        }

        public NrVopsInfo ngranNrVopsInfo() {
            if (this.hidl_d != 3) {
                String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
                throw new IllegalStateException(
                        "Read access to inactive union components is disallowed. Discriminator"
                                + " value is "
                                + ((int) this.hidl_d)
                                + " (corresponding to "
                                + hidl_discriminator.getName(this.hidl_d)
                                + "), and hidl_o is of type "
                                + className
                                + MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !NrVopsInfo.class.isInstance(this.hidl_o)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (NrVopsInfo) this.hidl_o;
        }

        public void geranDtmSupported(boolean geranDtmSupported) {
            this.hidl_d = (byte) 4;
            this.hidl_o = Boolean.valueOf(geranDtmSupported);
        }

        public boolean geranDtmSupported() {
            if (this.hidl_d != 4) {
                String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
                throw new IllegalStateException(
                        "Read access to inactive union components is disallowed. Discriminator"
                                + " value is "
                                + ((int) this.hidl_d)
                                + " (corresponding to "
                                + hidl_discriminator.getName(this.hidl_d)
                                + "), and hidl_o is of type "
                                + className
                                + MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !Boolean.class.isInstance(this.hidl_o)) {
                throw new Error("Union is in a corrupted state.");
            }
            return ((Boolean) this.hidl_o).booleanValue();
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(Object otherObject) {
            if (this == otherObject) {
                return true;
            }
            if (otherObject == null
                    || otherObject.getClass() != AccessTechnologySpecificInfo.class) {
                return false;
            }
            AccessTechnologySpecificInfo other = (AccessTechnologySpecificInfo) otherObject;
            if (this.hidl_d == other.hidl_d && HidlSupport.deepEquals(this.hidl_o, other.hidl_o)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(
                    Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)),
                    Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d))));
        }

        public final String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            switch (this.hidl_d) {
                case 0:
                    builder.append(".noinit = ");
                    builder.append(noinit());
                    break;
                case 1:
                    builder.append(".cdmaInfo = ");
                    builder.append(cdmaInfo());
                    break;
                case 2:
                    builder.append(".eutranInfo = ");
                    builder.append(eutranInfo());
                    break;
                case 3:
                    builder.append(".ngranNrVopsInfo = ");
                    builder.append(ngranNrVopsInfo());
                    break;
                case 4:
                    builder.append(".geranDtmSupported = ");
                    builder.append(geranDtmSupported());
                    break;
                default:
                    throw new Error(
                            "Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            builder.append("}");
            return builder.toString();
        }

        public final void readFromParcel(HwParcel parcel) {
            HwBlob blob = parcel.readBuffer(20L);
            readEmbeddedFromParcel(parcel, blob, 0L);
        }

        public static final ArrayList<AccessTechnologySpecificInfo> readVectorFromParcel(
                HwParcel parcel) {
            ArrayList<AccessTechnologySpecificInfo> _hidl_vec = new ArrayList<>();
            HwBlob _hidl_blob = parcel.readBuffer(16L);
            int _hidl_vec_size = _hidl_blob.getInt32(8L);
            HwBlob childBlob =
                    parcel.readEmbeddedBuffer(_hidl_vec_size * 20, _hidl_blob.handle(), 0L, true);
            _hidl_vec.clear();
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                AccessTechnologySpecificInfo _hidl_vec_element = new AccessTechnologySpecificInfo();
                _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 20);
                _hidl_vec.add(_hidl_vec_element);
            }
            return _hidl_vec;
        }

        public final void readEmbeddedFromParcel(
                HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
            this.hidl_d = _hidl_blob.getInt8(0 + _hidl_offset);
            switch (this.hidl_d) {
                case 0:
                    this.hidl_o = new Monostate();
                    ((Monostate) this.hidl_o)
                            .readEmbeddedFromParcel(parcel, _hidl_blob, 4 + _hidl_offset);
                    return;
                case 1:
                    this.hidl_o =
                            new RegStateResult.AccessTechnologySpecificInfo
                                    .Cdma2000RegistrationInfo();
                    ((RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo)
                                    this.hidl_o)
                            .readEmbeddedFromParcel(parcel, _hidl_blob, 4 + _hidl_offset);
                    return;
                case 2:
                    this.hidl_o =
                            new RegStateResult.AccessTechnologySpecificInfo
                                    .EutranRegistrationInfo();
                    ((RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo)
                                    this.hidl_o)
                            .readEmbeddedFromParcel(parcel, _hidl_blob, 4 + _hidl_offset);
                    return;
                case 3:
                    this.hidl_o = new NrVopsInfo();
                    ((NrVopsInfo) this.hidl_o)
                            .readEmbeddedFromParcel(parcel, _hidl_blob, 4 + _hidl_offset);
                    return;
                case 4:
                    this.hidl_o = false;
                    this.hidl_o = Boolean.valueOf(_hidl_blob.getBool(4 + _hidl_offset));
                    return;
                default:
                    throw new IllegalStateException(
                            "Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(HwParcel parcel) {
            HwBlob _hidl_blob = new HwBlob(20);
            writeEmbeddedToBlob(_hidl_blob, 0L);
            parcel.writeBuffer(_hidl_blob);
        }

        public static final void writeVectorToParcel(
                HwParcel parcel, ArrayList<AccessTechnologySpecificInfo> _hidl_vec) {
            HwBlob _hidl_blob = new HwBlob(16);
            int _hidl_vec_size = _hidl_vec.size();
            _hidl_blob.putInt32(8L, _hidl_vec_size);
            _hidl_blob.putBool(12L, false);
            HwBlob childBlob = new HwBlob(_hidl_vec_size * 20);
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 20);
            }
            _hidl_blob.putBlob(0L, childBlob);
            parcel.writeBuffer(_hidl_blob);
        }

        public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
            _hidl_blob.putInt8(0 + _hidl_offset, this.hidl_d);
            switch (this.hidl_d) {
                case 0:
                    noinit().writeEmbeddedToBlob(_hidl_blob, 4 + _hidl_offset);
                    return;
                case 1:
                    cdmaInfo().writeEmbeddedToBlob(_hidl_blob, 4 + _hidl_offset);
                    return;
                case 2:
                    eutranInfo().writeEmbeddedToBlob(_hidl_blob, 4 + _hidl_offset);
                    return;
                case 3:
                    ngranNrVopsInfo().writeEmbeddedToBlob(_hidl_blob, 4 + _hidl_offset);
                    return;
                case 4:
                    _hidl_blob.putBool(4 + _hidl_offset, geranDtmSupported());
                    return;
                default:
                    throw new Error(
                            "Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }
    }

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != RegStateResult.class) {
            return false;
        }
        RegStateResult other = (RegStateResult) otherObject;
        if (this.regState == other.regState
                && this.rat == other.rat
                && this.reasonForDenial == other.reasonForDenial
                && HidlSupport.deepEquals(this.cellIdentity, other.cellIdentity)
                && HidlSupport.deepEquals(this.registeredPlmn, other.registeredPlmn)
                && HidlSupport.deepEquals(
                        this.accessTechnologySpecificInfo, other.accessTechnologySpecificInfo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.regState))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rat))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.reasonForDenial))),
                Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentity)),
                Integer.valueOf(HidlSupport.deepHashCode(this.registeredPlmn)),
                Integer.valueOf(HidlSupport.deepHashCode(this.accessTechnologySpecificInfo)));
    }

    public final String toString() {
        return "{.regState = "
                + RegState.toString(this.regState)
                + ", .rat = "
                + RadioTechnology.toString(this.rat)
                + ", .reasonForDenial = "
                + RegistrationFailCause.toString(this.reasonForDenial)
                + ", .cellIdentity = "
                + this.cellIdentity
                + ", .registeredPlmn = "
                + this.registeredPlmn
                + ", .accessTechnologySpecificInfo = "
                + this.accessTechnologySpecificInfo
                + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(224L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<RegStateResult> readVectorFromParcel(HwParcel parcel) {
        ArrayList<RegStateResult> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob =
                parcel.readEmbeddedBuffer(_hidl_vec_size * 224, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            RegStateResult _hidl_vec_element = new RegStateResult();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 224);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(
            HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.regState = _hidl_blob.getInt32(_hidl_offset + 0);
        this.rat = _hidl_blob.getInt32(_hidl_offset + 4);
        this.reasonForDenial = _hidl_blob.getInt32(_hidl_offset + 8);
        this.cellIdentity.readEmbeddedFromParcel(parcel, _hidl_blob, _hidl_offset + 16);
        this.registeredPlmn = _hidl_blob.getString(_hidl_offset + 184);
        parcel.readEmbeddedBuffer(
                this.registeredPlmn.getBytes().length + 1,
                _hidl_blob.handle(),
                _hidl_offset + 184 + 0,
                false);
        this.accessTechnologySpecificInfo.readEmbeddedFromParcel(
                parcel, _hidl_blob, _hidl_offset + 200);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(224);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(
            HwParcel parcel, ArrayList<RegStateResult> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 224);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 224);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(0 + _hidl_offset, this.regState);
        _hidl_blob.putInt32(4 + _hidl_offset, this.rat);
        _hidl_blob.putInt32(8 + _hidl_offset, this.reasonForDenial);
        this.cellIdentity.writeEmbeddedToBlob(_hidl_blob, 16 + _hidl_offset);
        _hidl_blob.putString(184 + _hidl_offset, this.registeredPlmn);
        this.accessTechnologySpecificInfo.writeEmbeddedToBlob(_hidl_blob, 200 + _hidl_offset);
    }
}
