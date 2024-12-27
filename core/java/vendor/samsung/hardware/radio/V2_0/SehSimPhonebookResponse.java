package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehSimPhonebookResponse {
    public ArrayList<Integer> lengthAlphas = new ArrayList<>();
    public ArrayList<Integer> dataTypeAlphas = new ArrayList<>();
    public ArrayList<String> alphaTags = new ArrayList<>();
    public ArrayList<Integer> lengthNumbers = new ArrayList<>();
    public ArrayList<Integer> dataTypeNumbers = new ArrayList<>();
    public ArrayList<String> numbers = new ArrayList<>();
    public int recordIndex = 0;
    public int nextIndex = 0;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehSimPhonebookResponse.class) {
            return false;
        }
        SehSimPhonebookResponse other = (SehSimPhonebookResponse) otherObject;
        if (HidlSupport.deepEquals(this.lengthAlphas, other.lengthAlphas)
                && HidlSupport.deepEquals(this.dataTypeAlphas, other.dataTypeAlphas)
                && HidlSupport.deepEquals(this.alphaTags, other.alphaTags)
                && HidlSupport.deepEquals(this.lengthNumbers, other.lengthNumbers)
                && HidlSupport.deepEquals(this.dataTypeNumbers, other.dataTypeNumbers)
                && HidlSupport.deepEquals(this.numbers, other.numbers)
                && this.recordIndex == other.recordIndex
                && this.nextIndex == other.nextIndex) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.lengthAlphas)),
                Integer.valueOf(HidlSupport.deepHashCode(this.dataTypeAlphas)),
                Integer.valueOf(HidlSupport.deepHashCode(this.alphaTags)),
                Integer.valueOf(HidlSupport.deepHashCode(this.lengthNumbers)),
                Integer.valueOf(HidlSupport.deepHashCode(this.dataTypeNumbers)),
                Integer.valueOf(HidlSupport.deepHashCode(this.numbers)),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.recordIndex))),
                Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.nextIndex))));
    }

    public final String toString() {
        return "{.lengthAlphas = "
                + this.lengthAlphas
                + ", .dataTypeAlphas = "
                + this.dataTypeAlphas
                + ", .alphaTags = "
                + this.alphaTags
                + ", .lengthNumbers = "
                + this.lengthNumbers
                + ", .dataTypeNumbers = "
                + this.dataTypeNumbers
                + ", .numbers = "
                + this.numbers
                + ", .recordIndex = "
                + this.recordIndex
                + ", .nextIndex = "
                + this.nextIndex
                + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(104L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehSimPhonebookResponse> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehSimPhonebookResponse> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob =
                parcel.readEmbeddedBuffer(_hidl_vec_size * 104, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehSimPhonebookResponse _hidl_vec_element = new SehSimPhonebookResponse();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 104);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(
            HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        int _hidl_vec_size = _hidl_blob.getInt32(_hidl_offset + 0 + 8);
        HwBlob childBlob =
                parcel.readEmbeddedBuffer(
                        _hidl_vec_size * 4, _hidl_blob.handle(), _hidl_offset + 0 + 0, true);
        this.lengthAlphas.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            this.lengthAlphas.add(Integer.valueOf(childBlob.getInt32(_hidl_index_0 * 4)));
        }
        int _hidl_vec_size2 = _hidl_blob.getInt32(_hidl_offset + 16 + 8);
        HwBlob childBlob2 =
                parcel.readEmbeddedBuffer(
                        _hidl_vec_size2 * 4, _hidl_blob.handle(), _hidl_offset + 16 + 0, true);
        this.dataTypeAlphas.clear();
        for (int _hidl_index_02 = 0; _hidl_index_02 < _hidl_vec_size2; _hidl_index_02++) {
            this.dataTypeAlphas.add(Integer.valueOf(childBlob2.getInt32(_hidl_index_02 * 4)));
        }
        int _hidl_vec_size3 = _hidl_blob.getInt32(_hidl_offset + 32 + 8);
        HwBlob childBlob3 =
                parcel.readEmbeddedBuffer(
                        _hidl_vec_size3 * 16, _hidl_blob.handle(), _hidl_offset + 32 + 0, true);
        this.alphaTags.clear();
        for (int _hidl_index_03 = 0; _hidl_index_03 < _hidl_vec_size3; _hidl_index_03++) {
            new String();
            String _hidl_vec_element = childBlob3.getString(_hidl_index_03 * 16);
            parcel.readEmbeddedBuffer(
                    _hidl_vec_element.getBytes().length + 1,
                    childBlob3.handle(),
                    (_hidl_index_03 * 16) + 0,
                    false);
            this.alphaTags.add(_hidl_vec_element);
        }
        int _hidl_vec_size4 = _hidl_blob.getInt32(_hidl_offset + 48 + 8);
        HwBlob childBlob4 =
                parcel.readEmbeddedBuffer(
                        _hidl_vec_size4 * 4, _hidl_blob.handle(), _hidl_offset + 48 + 0, true);
        this.lengthNumbers.clear();
        for (int _hidl_index_04 = 0; _hidl_index_04 < _hidl_vec_size4; _hidl_index_04++) {
            this.lengthNumbers.add(Integer.valueOf(childBlob4.getInt32(_hidl_index_04 * 4)));
        }
        int _hidl_vec_size5 = _hidl_blob.getInt32(_hidl_offset + 64 + 8);
        HwBlob childBlob5 =
                parcel.readEmbeddedBuffer(
                        _hidl_vec_size5 * 4, _hidl_blob.handle(), _hidl_offset + 64 + 0, true);
        this.dataTypeNumbers.clear();
        for (int _hidl_index_05 = 0; _hidl_index_05 < _hidl_vec_size5; _hidl_index_05++) {
            this.dataTypeNumbers.add(Integer.valueOf(childBlob5.getInt32(_hidl_index_05 * 4)));
        }
        int _hidl_vec_size6 = _hidl_blob.getInt32(_hidl_offset + 80 + 8);
        HwBlob childBlob6 =
                parcel.readEmbeddedBuffer(
                        _hidl_vec_size6 * 16, _hidl_blob.handle(), _hidl_offset + 80 + 0, true);
        this.numbers.clear();
        for (int _hidl_index_06 = 0; _hidl_index_06 < _hidl_vec_size6; _hidl_index_06++) {
            new String();
            String _hidl_vec_element2 = childBlob6.getString(_hidl_index_06 * 16);
            parcel.readEmbeddedBuffer(
                    _hidl_vec_element2.getBytes().length + 1,
                    childBlob6.handle(),
                    (_hidl_index_06 * 16) + 0,
                    false);
            this.numbers.add(_hidl_vec_element2);
        }
        this.recordIndex = _hidl_blob.getInt32(_hidl_offset + 96);
        this.nextIndex = _hidl_blob.getInt32(_hidl_offset + 100);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(104);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(
            HwParcel parcel, ArrayList<SehSimPhonebookResponse> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 104);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 104);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        int _hidl_vec_size = this.lengthAlphas.size();
        _hidl_blob.putInt32(_hidl_offset + 0 + 8, _hidl_vec_size);
        _hidl_blob.putBool(_hidl_offset + 0 + 12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 4);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            childBlob.putInt32(_hidl_index_0 * 4, this.lengthAlphas.get(_hidl_index_0).intValue());
        }
        _hidl_blob.putBlob(_hidl_offset + 0 + 0, childBlob);
        int _hidl_vec_size2 = this.dataTypeAlphas.size();
        _hidl_blob.putInt32(_hidl_offset + 16 + 8, _hidl_vec_size2);
        _hidl_blob.putBool(_hidl_offset + 16 + 12, false);
        HwBlob childBlob2 = new HwBlob(_hidl_vec_size2 * 4);
        for (int _hidl_index_02 = 0; _hidl_index_02 < _hidl_vec_size2; _hidl_index_02++) {
            childBlob2.putInt32(
                    _hidl_index_02 * 4, this.dataTypeAlphas.get(_hidl_index_02).intValue());
        }
        _hidl_blob.putBlob(_hidl_offset + 16 + 0, childBlob2);
        int _hidl_vec_size3 = this.alphaTags.size();
        _hidl_blob.putInt32(_hidl_offset + 32 + 8, _hidl_vec_size3);
        _hidl_blob.putBool(_hidl_offset + 32 + 12, false);
        HwBlob childBlob3 = new HwBlob(_hidl_vec_size3 * 16);
        for (int _hidl_index_03 = 0; _hidl_index_03 < _hidl_vec_size3; _hidl_index_03++) {
            childBlob3.putString(_hidl_index_03 * 16, this.alphaTags.get(_hidl_index_03));
        }
        _hidl_blob.putBlob(_hidl_offset + 32 + 0, childBlob3);
        int _hidl_vec_size4 = this.lengthNumbers.size();
        _hidl_blob.putInt32(_hidl_offset + 48 + 8, _hidl_vec_size4);
        _hidl_blob.putBool(_hidl_offset + 48 + 12, false);
        HwBlob childBlob4 = new HwBlob(_hidl_vec_size4 * 4);
        for (int _hidl_index_04 = 0; _hidl_index_04 < _hidl_vec_size4; _hidl_index_04++) {
            childBlob4.putInt32(
                    _hidl_index_04 * 4, this.lengthNumbers.get(_hidl_index_04).intValue());
        }
        _hidl_blob.putBlob(_hidl_offset + 48 + 0, childBlob4);
        int _hidl_vec_size5 = this.dataTypeNumbers.size();
        _hidl_blob.putInt32(_hidl_offset + 64 + 8, _hidl_vec_size5);
        _hidl_blob.putBool(_hidl_offset + 64 + 12, false);
        HwBlob childBlob5 = new HwBlob(_hidl_vec_size5 * 4);
        for (int _hidl_index_05 = 0; _hidl_index_05 < _hidl_vec_size5; _hidl_index_05++) {
            childBlob5.putInt32(
                    _hidl_index_05 * 4, this.dataTypeNumbers.get(_hidl_index_05).intValue());
        }
        _hidl_blob.putBlob(_hidl_offset + 64 + 0, childBlob5);
        int _hidl_vec_size6 = this.numbers.size();
        _hidl_blob.putInt32(_hidl_offset + 80 + 8, _hidl_vec_size6);
        _hidl_blob.putBool(_hidl_offset + 80 + 12, false);
        HwBlob childBlob6 = new HwBlob(_hidl_vec_size6 * 16);
        for (int _hidl_index_06 = 0; _hidl_index_06 < _hidl_vec_size6; _hidl_index_06++) {
            childBlob6.putString(_hidl_index_06 * 16, this.numbers.get(_hidl_index_06));
        }
        _hidl_blob.putBlob(_hidl_offset + 80 + 0, childBlob6);
        _hidl_blob.putInt32(_hidl_offset + 96, this.recordIndex);
        _hidl_blob.putInt32(_hidl_offset + 100, this.nextIndex);
    }
}
