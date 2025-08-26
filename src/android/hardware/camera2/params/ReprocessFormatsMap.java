package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class ReprocessFormatsMap {
    private final int[] mEntry;
    private final int mInputCount;

    public ReprocessFormatsMap(int[] iArr) {
        Preconditions.checkNotNull(iArr, "entry must not be null");
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        while (i < iArr.length) {
            int iCheckArgumentFormatInternal = StreamConfigurationMap.checkArgumentFormatInternal(iArr[i]);
            int i3 = i + 1;
            if (length - 1 < 1) {
                throw new IllegalArgumentException(String.format("Input %x had no output format length listed", Integer.valueOf(iCheckArgumentFormatInternal)));
            }
            int i4 = iArr[i3];
            length -= 2;
            i += 2;
            for (int i5 = 0; i5 < i4; i5++) {
                StreamConfigurationMap.checkArgumentFormatInternal(iArr[i + i5]);
            }
            if (i4 > 0) {
                if (length < i4) {
                    throw new IllegalArgumentException(String.format("Input %x had too few output formats listed (actual: %d, expected: %d)", Integer.valueOf(iCheckArgumentFormatInternal), Integer.valueOf(length), Integer.valueOf(i4)));
                }
                i += i4;
                length -= i4;
            }
            i2++;
        }
        this.mEntry = iArr;
        this.mInputCount = i2;
    }

    public int[] getInputs() {
        int[] iArr = new int[this.mInputCount];
        int length = this.mEntry.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr2 = this.mEntry;
            if (i < iArr2.length) {
                int i3 = iArr2[i];
                int i4 = i + 1;
                if (length - 1 < 1) {
                    throw new AssertionError(String.format("Input %x had no output format length listed", Integer.valueOf(i3)));
                }
                int i5 = iArr2[i4];
                length -= 2;
                i += 2;
                if (i5 > 0) {
                    if (length < i5) {
                        throw new AssertionError(String.format("Input %x had too few output formats listed (actual: %d, expected: %d)", Integer.valueOf(i3), Integer.valueOf(length), Integer.valueOf(i5)));
                    }
                    i += i5;
                    length -= i5;
                }
                iArr[i2] = i3;
                i2++;
            } else {
                return StreamConfigurationMap.imageFormatToPublic(iArr);
            }
        }
    }

    public int[] getOutputs(int i) {
        int length = this.mEntry.length;
        int i2 = 0;
        while (true) {
            int[] iArr = this.mEntry;
            if (i2 < iArr.length) {
                int i3 = iArr[i2];
                int i4 = i2 + 1;
                if (length - 1 < 1) {
                    throw new AssertionError(String.format("Input %x had no output format length listed", Integer.valueOf(i)));
                }
                int i5 = iArr[i4];
                int i6 = length - 2;
                int i7 = i2 + 2;
                if (i5 > 0 && i6 < i5) {
                    throw new AssertionError(String.format("Input %x had too few output formats listed (actual: %d, expected: %d)", Integer.valueOf(i), Integer.valueOf(i6), Integer.valueOf(i5)));
                }
                if (i3 == i) {
                    int[] iArr2 = new int[i5];
                    for (int i8 = 0; i8 < i5; i8++) {
                        iArr2[i8] = this.mEntry[i7 + i8];
                    }
                    return StreamConfigurationMap.imageFormatToPublic(iArr2);
                }
                i2 = i7 + i5;
                length = i6 - i5;
            } else {
                throw new IllegalArgumentException(String.format("Input format %x was not one in #getInputs", Integer.valueOf(i)));
            }
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof ReprocessFormatsMap) {
            return Arrays.equals(this.mEntry, ((ReprocessFormatsMap) obj).mEntry);
        }
        return false;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mEntry);
    }
}
