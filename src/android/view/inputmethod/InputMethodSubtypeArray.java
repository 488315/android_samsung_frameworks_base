package android.view.inputmethod;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.util.Printer;
import android.util.Slog;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes4.dex */
public class InputMethodSubtypeArray {
    private static final String TAG = "InputMethodSubtypeArray";
    private volatile byte[] mCompressedData;
    private final int mCount;
    private volatile int mDecompressedSize;
    private volatile InputMethodSubtype[] mInstance;
    private final Object mLockObject = new Object();

    public InputMethodSubtypeArray(List<InputMethodSubtype> list) {
        if (list == null) {
            this.mCount = 0;
            return;
        }
        int size = list.size();
        this.mCount = size;
        this.mInstance = (InputMethodSubtype[]) list.toArray(new InputMethodSubtype[size]);
    }

    public InputMethodSubtypeArray(Parcel parcel) {
        int readInt = parcel.readInt();
        this.mCount = readInt;
        if (readInt < 0) {
            throw new BadParcelableException("mCount must be non-negative.");
        }
        if (readInt > 0) {
            this.mDecompressedSize = parcel.readInt();
            this.mCompressedData = parcel.createByteArray();
        }
    }

    public void writeToParcel(Parcel parcel) {
        int length;
        int i = this.mCount;
        if (i == 0) {
            parcel.writeInt(i);
            return;
        }
        byte[] bArr = this.mCompressedData;
        int i2 = this.mDecompressedSize;
        if (bArr == null && i2 == 0) {
            synchronized (this.mLockObject) {
                bArr = this.mCompressedData;
                i2 = this.mDecompressedSize;
                if (bArr == null && i2 == 0) {
                    byte[] marshall = marshall(this.mInstance);
                    byte[] compress = compress(marshall);
                    if (compress == null) {
                        Slog.i(TAG, "Failed to compress data.");
                        length = -1;
                    } else {
                        length = marshall.length;
                    }
                    this.mDecompressedSize = length;
                    this.mCompressedData = compress;
                    i2 = length;
                    bArr = compress;
                }
            }
        }
        if (bArr != null && i2 > 0) {
            parcel.writeInt(this.mCount);
            parcel.writeInt(i2);
            parcel.writeByteArray(bArr);
        } else {
            Slog.i(TAG, "Unexpected state. Behaving as an empty array.");
            parcel.writeInt(0);
        }
    }

    public InputMethodSubtype get(int i) {
        if (i < 0 || this.mCount <= i) {
            throw new ArrayIndexOutOfBoundsException();
        }
        InputMethodSubtype[] inputMethodSubtypeArr = this.mInstance;
        if (inputMethodSubtypeArr == null) {
            synchronized (this.mLockObject) {
                inputMethodSubtypeArr = this.mInstance;
                if (inputMethodSubtypeArr == null) {
                    byte[] decompress = decompress(this.mCompressedData, this.mDecompressedSize);
                    this.mCompressedData = null;
                    this.mDecompressedSize = 0;
                    if (decompress != null) {
                        inputMethodSubtypeArr = unmarshall(decompress);
                    } else {
                        Slog.e(TAG, "Failed to decompress data. Returns null as fallback.");
                        inputMethodSubtypeArr = new InputMethodSubtype[this.mCount];
                    }
                    this.mInstance = inputMethodSubtypeArr;
                }
            }
        }
        return inputMethodSubtypeArr[i];
    }

    public ArrayList<InputMethodSubtype> toList() {
        ArrayList<InputMethodSubtype> arrayList = new ArrayList<>(this.mCount);
        for (int i = 0; i < this.mCount; i++) {
            arrayList.add(get(i));
        }
        return arrayList;
    }

    public int getCount() {
        return this.mCount;
    }

    void dump(Printer printer, String str) {
        String str2 = str + "  ";
        for (int i = 0; i < this.mCount; i++) {
            printer.println(str + "InputMethodSubtype #" + i + ":");
            InputMethodSubtype inputMethodSubtype = get(i);
            if (inputMethodSubtype != null) {
                inputMethodSubtype.dump(printer, str2);
            } else {
                printer.println(str2 + "missing subtype");
            }
        }
    }

    private static byte[] marshall(InputMethodSubtype[] inputMethodSubtypeArr) {
        Parcel parcel;
        try {
            parcel = Parcel.obtain();
        } catch (Throwable th) {
            th = th;
            parcel = null;
        }
        try {
            parcel.writeTypedArray(inputMethodSubtypeArr, 0);
            byte[] marshall = parcel.marshall();
            if (parcel != null) {
                parcel.recycle();
            }
            return marshall;
        } catch (Throwable th2) {
            th = th2;
            if (parcel != null) {
                parcel.recycle();
            }
            throw th;
        }
    }

    private static InputMethodSubtype[] unmarshall(byte[] bArr) {
        Parcel parcel;
        try {
            parcel = Parcel.obtain();
        } catch (Throwable th) {
            th = th;
            parcel = null;
        }
        try {
            parcel.unmarshall(bArr, 0, bArr.length);
            parcel.setDataPosition(0);
            InputMethodSubtype[] inputMethodSubtypeArr = (InputMethodSubtype[]) parcel.createTypedArray(InputMethodSubtype.CREATOR);
            if (parcel != null) {
                parcel.recycle();
            }
            return inputMethodSubtypeArr;
        } catch (Throwable th2) {
            th = th2;
            if (parcel != null) {
                parcel.recycle();
            }
            throw th;
        }
    }

    private static byte[] compress(byte[] bArr) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    gZIPOutputStream.write(bArr);
                    gZIPOutputStream.finish();
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    gZIPOutputStream.close();
                    byteArrayOutputStream.close();
                    return byteArray;
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Slog.e(TAG, "Failed to compress the data.", e);
            return null;
        }
    }

    private static byte[] decompress(byte[] bArr, int i) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
                try {
                    byte[] bArr2 = new byte[i];
                    int i2 = 0;
                    while (i2 < i) {
                        int read = gZIPInputStream.read(bArr2, i2, i - i2);
                        if (read < 0) {
                            break;
                        }
                        i2 += read;
                    }
                    if (i != i2) {
                        gZIPInputStream.close();
                        byteArrayInputStream.close();
                        return null;
                    }
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    return bArr2;
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Slog.e(TAG, "Failed to decompress the data.", e);
            return null;
        }
    }
}
