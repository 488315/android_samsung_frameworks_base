package android.hardware;

import android.util.IntArray;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public final class DisplayLuts {
    private ArrayList<Entry> mEntries = new ArrayList<>();
    private IntArray mOffsets = new IntArray();
    private int mTotalLength = 0;

    public static class Entry {
        private static final int LUT_LENGTH_LIMIT = 100000;
        private float[] mBuffer;
        private int mDimension;
        private int mSamplingKey;
        private int mSize;

        public Entry(float[] fArr, int i, int i2) {
            if (fArr == null || fArr.length < 1) {
                throw new IllegalArgumentException("The buffer cannot be empty!");
            }
            if (fArr.length >= 100000) {
                throw new IllegalArgumentException("The lut length is too big to handle!");
            }
            if (i != 1 && i != 3) {
                throw new IllegalArgumentException("The dimension should be either 1D or 3D!");
            }
            if (i == 3) {
                if (fArr.length <= 3) {
                    throw new IllegalArgumentException("The 3d lut size of each dimension should be over 1!");
                }
                if (fArr.length % 3 != 0) {
                    throw new IllegalArgumentException("The lut buffer of 3dlut should have 3 channels!");
                }
                double cbrt = Math.cbrt(r1 / 3);
                int i3 = (int) cbrt;
                if (cbrt == i3) {
                    this.mSize = i3;
                } else {
                    throw new IllegalArgumentException("Cannot get the cube root of the 3d lut buffer!");
                }
            } else {
                this.mSize = fArr.length;
            }
            this.mBuffer = fArr;
            this.mDimension = i;
            this.mSamplingKey = i2;
        }

        public int getDimension() {
            return this.mDimension;
        }

        public int getSize() {
            return this.mSize;
        }

        public float[] getBuffer() {
            return this.mBuffer;
        }

        public int getSamplingKey() {
            return this.mSamplingKey;
        }

        public String toString() {
            return "Entry{dimension=" + dimensionToString(getDimension()) + ", size(each dimension)=" + getSize() + ", samplingKey=" + samplingKeyToString(getSamplingKey()) + "}";
        }

        private static String dimensionToString(int i) {
            if (i == 1) {
                return "ONE_DIMENSION";
            }
            if (i == 3) {
                return "THREE_DIMENSION";
            }
            return "";
        }

        private static String samplingKeyToString(int i) {
            if (i == 0) {
                return "SAMPLING_KEY_RGB";
            }
            if (i == 1) {
                return "SAMPLING_KEY_MAX_RGB";
            }
            if (i == 2) {
                return "SAMPLING_KEY_CIE_Y";
            }
            return "";
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DisplayLuts{\n");
        Iterator<Entry> it = this.mEntries.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            sb.append(ShaderAssembler.NEWLINE);
        }
        sb.append("}");
        return sb.toString();
    }

    private void addEntry(Entry entry) {
        this.mEntries.add(entry);
        this.mOffsets.add(this.mTotalLength);
        this.mTotalLength += entry.getBuffer().length;
    }

    private void clear() {
        this.mOffsets.clear();
        this.mTotalLength = 0;
        this.mEntries.clear();
    }

    public void set(Entry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("The entry is null!");
        }
        clear();
        addEntry(entry);
    }

    public void set(Entry entry, Entry entry2) {
        if (entry == null || entry2 == null) {
            throw new IllegalArgumentException("The entry is null!");
        }
        if (entry.getDimension() != 1 || entry2.getDimension() != 3) {
            throw new IllegalArgumentException("The entries should be 1D and 3D in order!");
        }
        clear();
        addEntry(entry);
        addEntry(entry2);
    }

    public boolean valid() {
        return this.mEntries.size() > 0;
    }

    public float[] getLutBuffers() {
        float[] fArr = new float[this.mTotalLength];
        for (int i = 0; i < this.mEntries.size(); i++) {
            float[] buffer = this.mEntries.get(i).getBuffer();
            System.arraycopy(buffer, 0, fArr, this.mOffsets.get(i), buffer.length);
        }
        return fArr;
    }

    public int[] getOffsets() {
        return this.mOffsets.toArray();
    }

    public int[] getLutSizes() {
        int[] iArr = new int[this.mEntries.size()];
        for (int i = 0; i < this.mEntries.size(); i++) {
            iArr[i] = this.mEntries.get(i).getSize();
        }
        return iArr;
    }

    public int[] getLutDimensions() {
        int[] iArr = new int[this.mEntries.size()];
        for (int i = 0; i < this.mEntries.size(); i++) {
            iArr[i] = this.mEntries.get(i).getDimension();
        }
        return iArr;
    }

    public int[] getLutSamplingKeys() {
        int[] iArr = new int[this.mEntries.size()];
        for (int i = 0; i < this.mEntries.size(); i++) {
            iArr[i] = this.mEntries.get(i).getSamplingKey();
        }
        return iArr;
    }
}
