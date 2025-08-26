package android.database;

import android.content.res.Resources;
import android.database.sqlite.SQLiteClosable;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.R;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.CloseGuard;
import java.util.Objects;

/* loaded from: classes.dex */
public class CursorWindow extends SQLiteClosable implements Parcelable {
    public static final Parcelable.Creator<CursorWindow> CREATOR = new Parcelable.Creator<CursorWindow>() { // from class: android.database.CursorWindow.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CursorWindow createFromParcel(Parcel parcel) {
            return new CursorWindow(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CursorWindow[] newArray(int i) {
            return new CursorWindow[i];
        }
    };
    private static final String STATS_TAG = "CursorWindowStats";
    private static int sCursorWindowSize = -1;
    private final CloseGuard mCloseGuard;
    private int mFilledRows;
    private final String mName;
    private int mStartPos;
    private int mTotalRows;
    public long mWindowPtr;

    private CloseGuard createCloseGuard$ravenwood() {
        return null;
    }

    private static int getCursorWindowSize$ravenwood() {
        return 1024;
    }

    @FastNative
    private static native boolean nativeAllocRow(long j);

    @FastNative
    private static native void nativeClear(long j);

    private static native void nativeCopyStringToBuffer(long j, int i, int i2, CharArrayBuffer charArrayBuffer);

    private static native long nativeCreate(String str, int i);

    private static native long nativeCreateFromParcel(Parcel parcel);

    private static native void nativeDispose(long j);

    @FastNative
    private static native void nativeFreeLastRow(long j);

    private static native byte[] nativeGetBlob(long j, int i, int i2);

    @FastNative
    private static native double nativeGetDouble(long j, int i, int i2);

    @FastNative
    private static native long nativeGetLong(long j, int i, int i2);

    private static native String nativeGetName(long j);

    @FastNative
    private static native int nativeGetNumRows(long j);

    private static native String nativeGetString(long j, int i, int i2);

    @FastNative
    private static native int nativeGetType(long j, int i, int i2);

    private static native boolean nativePutBlob(long j, byte[] bArr, int i, int i2);

    @FastNative
    private static native boolean nativePutDouble(long j, double d, int i, int i2);

    @FastNative
    private static native boolean nativePutLong(long j, long j2, int i, int i2);

    @FastNative
    private static native boolean nativePutNull(long j, int i, int i2);

    private static native boolean nativePutString(long j, String str, int i, int i2);

    @FastNative
    private static native boolean nativeSetNumColumns(long j, int i);

    private static native void nativeWriteToParcel(long j, Parcel parcel);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CursorWindow(String str) {
        this(str, getCursorWindowSize());
    }

    public CursorWindow(String str, long j) {
        if (j < 0) {
            throw new IllegalArgumentException("Window size cannot be less than 0");
        }
        this.mStartPos = 0;
        this.mTotalRows = 0;
        str = (str == null || str.length() == 0) ? "<unnamed>" : str;
        this.mName = str;
        long jNativeCreate = nativeCreate(str, (int) j);
        this.mWindowPtr = jNativeCreate;
        if (jNativeCreate == 0) {
            throw new AssertionError();
        }
        this.mCloseGuard = createCloseGuard();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @Deprecated
    public CursorWindow(boolean z) {
        this((String) null);
    }

    private CursorWindow(Parcel parcel) {
        this.mStartPos = parcel.readInt();
        long jNativeCreateFromParcel = nativeCreateFromParcel(parcel);
        this.mWindowPtr = jNativeCreateFromParcel;
        if (jNativeCreateFromParcel == 0) {
            throw new AssertionError();
        }
        this.mName = nativeGetName(jNativeCreateFromParcel);
        this.mCloseGuard = createCloseGuard();
    }

    private CloseGuard createCloseGuard() {
        CloseGuard closeGuard = CloseGuard.get();
        closeGuard.open("CursorWindow.close");
        return closeGuard;
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            dispose();
        } finally {
            super.finalize();
        }
    }

    private void dispose() {
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            closeGuard.close();
        }
        long j = this.mWindowPtr;
        if (j != 0) {
            nativeDispose(j);
            this.mWindowPtr = 0L;
        }
    }

    public String getName() {
        return this.mName;
    }

    public void clear() {
        acquireReference();
        try {
            this.mStartPos = 0;
            nativeClear(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public int getStartPosition() {
        return this.mStartPos;
    }

    public void setStartPosition(int i) {
        this.mStartPos = i;
    }

    public int getNumRows() {
        acquireReference();
        try {
            return nativeGetNumRows(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public boolean setNumColumns(int i) {
        acquireReference();
        try {
            return nativeSetNumColumns(this.mWindowPtr, i);
        } finally {
            releaseReference();
        }
    }

    public boolean allocRow() {
        acquireReference();
        try {
            return nativeAllocRow(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public void freeLastRow() {
        acquireReference();
        try {
            nativeFreeLastRow(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    @Deprecated
    public boolean isNull(int i, int i2) {
        return getType(i, i2) == 0;
    }

    @Deprecated
    public boolean isBlob(int i, int i2) {
        int type = getType(i, i2);
        return type == 4 || type == 0;
    }

    @Deprecated
    public boolean isLong(int i, int i2) {
        return getType(i, i2) == 1;
    }

    @Deprecated
    public boolean isFloat(int i, int i2) {
        return getType(i, i2) == 2;
    }

    @Deprecated
    public boolean isString(int i, int i2) {
        int type = getType(i, i2);
        return type == 3 || type == 0;
    }

    public int getType(int i, int i2) {
        acquireReference();
        try {
            return nativeGetType(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public byte[] getBlob(int i, int i2) {
        acquireReference();
        try {
            return nativeGetBlob(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public String getString(int i, int i2) {
        acquireReference();
        try {
            return nativeGetString(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public void copyStringToBuffer(int i, int i2, CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer == null) {
            throw new IllegalArgumentException("CharArrayBuffer should not be null");
        }
        acquireReference();
        try {
            nativeCopyStringToBuffer(this.mWindowPtr, i - this.mStartPos, i2, charArrayBuffer);
        } finally {
            releaseReference();
        }
    }

    public long getLong(int i, int i2) {
        acquireReference();
        try {
            return nativeGetLong(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public double getDouble(int i, int i2) {
        acquireReference();
        try {
            return nativeGetDouble(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public short getShort(int i, int i2) {
        return (short) getLong(i, i2);
    }

    public int getInt(int i, int i2) {
        return (int) getLong(i, i2);
    }

    public float getFloat(int i, int i2) {
        return (float) getDouble(i, i2);
    }

    public boolean putBlob(byte[] bArr, int i, int i2) {
        Objects.requireNonNull(bArr);
        acquireReference();
        try {
            return nativePutBlob(this.mWindowPtr, bArr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public boolean putString(String str, int i, int i2) {
        Objects.requireNonNull(str);
        acquireReference();
        try {
            return nativePutString(this.mWindowPtr, str, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public boolean putLong(long j, int i, int i2) {
        acquireReference();
        try {
            return nativePutLong(this.mWindowPtr, j, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public boolean putDouble(double d, int i, int i2) {
        acquireReference();
        try {
            return nativePutDouble(this.mWindowPtr, d, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public boolean putNull(int i, int i2) {
        acquireReference();
        try {
            return nativePutNull(this.mWindowPtr, i - this.mStartPos, i2);
        } finally {
            releaseReference();
        }
    }

    public static CursorWindow newFromParcel(Parcel parcel) {
        return CREATOR.createFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        acquireReference();
        try {
            parcel.writeInt(this.mStartPos);
            nativeWriteToParcel(this.mWindowPtr, parcel);
            releaseReference();
            if ((i & 1) != 0) {
            }
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.sqlite.SQLiteClosable
    protected void onAllReferencesReleased() {
        dispose();
    }

    private static int getCursorWindowSize() {
        if (sCursorWindowSize < 0) {
            sCursorWindowSize = Resources.getSystem().getInteger(R.integer.config_cursorWindowSize) * 1024;
        }
        return sCursorWindowSize;
    }

    public void setTotalRows(int i) {
        this.mTotalRows = i;
    }

    public int getTotalRows() {
        return this.mTotalRows;
    }

    public void setFilledRows(int i) {
        this.mFilledRows = i;
    }

    public int getFilledRows() {
        return this.mFilledRows;
    }

    public String toString() {
        return getName() + " {" + Long.toHexString(this.mWindowPtr) + "}";
    }
}
