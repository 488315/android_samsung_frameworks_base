package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public abstract class HandwritingGesture {
    public static final int GESTURE_TYPE_DELETE = 4;
    public static final int GESTURE_TYPE_DELETE_RANGE = 64;
    public static final int GESTURE_TYPE_INSERT = 2;
    public static final int GESTURE_TYPE_INSERT_MODE = 128;
    public static final int GESTURE_TYPE_JOIN_OR_SPLIT = 16;
    public static final int GESTURE_TYPE_NONE = 0;
    public static final int GESTURE_TYPE_REMOVE_SPACE = 8;
    public static final int GESTURE_TYPE_SELECT = 1;
    public static final int GESTURE_TYPE_SELECT_RANGE = 32;
    public static final int GRANULARITY_CHARACTER = 2;
    static final int GRANULARITY_UNDEFINED = 0;
    public static final int GRANULARITY_WORD = 1;
    String mFallbackText;
    int mType = 0;

    @Retention(RetentionPolicy.SOURCE)
    @interface GestureType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GestureTypeFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface Granularity {
    }

    HandwritingGesture() {
    }

    public final int getGestureType() {
        return this.mType;
    }

    public final String getFallbackText() {
        return this.mFallbackText;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final byte[] toByteArray() throws Throwable {
        Parcel parcelObtain;
        if (!(this instanceof Parcelable)) {
            throw new UnsupportedOperationException(getClass() + " is not Parcelable");
        }
        if ((((Parcelable) this).describeContents() & 1) != 0) {
            throw new UnsupportedOperationException("Gesture that contains FD is not supported");
        }
        try {
            parcelObtain = Parcel.obtain();
            try {
                ParcelableHandwritingGesture.of(this).writeToParcel(parcelObtain, 0);
                byte[] bArrMarshall = parcelObtain.marshall();
                if (parcelObtain != null) {
                    parcelObtain.recycle();
                }
                return bArrMarshall;
            } catch (Throwable th) {
                th = th;
                if (parcelObtain != null) {
                    parcelObtain.recycle();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            parcelObtain = null;
        }
    }

    public static HandwritingGesture fromByteArray(byte[] bArr) throws Throwable {
        Parcel parcelObtain;
        try {
            parcelObtain = Parcel.obtain();
        } catch (Throwable th) {
            th = th;
            parcelObtain = null;
        }
        try {
            parcelObtain.unmarshall(bArr, 0, bArr.length);
            parcelObtain.setDataPosition(0);
            HandwritingGesture handwritingGesture = ParcelableHandwritingGesture.CREATOR.createFromParcel(parcelObtain).get();
            if (parcelObtain != null) {
                parcelObtain.recycle();
            }
            return handwritingGesture;
        } catch (Throwable th2) {
            th = th2;
            if (parcelObtain != null) {
                parcelObtain.recycle();
            }
            throw th;
        }
    }
}
