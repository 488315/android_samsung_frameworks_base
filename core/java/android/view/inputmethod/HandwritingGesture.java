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
  @interface GestureType {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface GestureTypeFlags {}

  @interface Granularity {}

  HandwritingGesture() {}

  public final int getGestureType() {
    return this.mType;
  }

  public final String getFallbackText() {
    return this.mFallbackText;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public final byte[] toByteArray() {
    if (!(this instanceof Parcelable)) {
      throw new UnsupportedOperationException(getClass() + " is not Parcelable");
    }
    Parcelable self = (Parcelable) this;
    if ((self.describeContents() & 1) != 0) {
      throw new UnsupportedOperationException("Gesture that contains FD is not supported");
    }
    Parcel parcel = null;
    try {
      parcel = Parcel.obtain();
      ParcelableHandwritingGesture.m165of(this).writeToParcel(parcel, 0);
      return parcel.marshall();
    } finally {
      if (parcel != null) {
        parcel.recycle();
      }
    }
  }

  public static HandwritingGesture fromByteArray(byte[] buffer) {
    Parcel parcel = null;
    try {
      parcel = Parcel.obtain();
      parcel.unmarshall(buffer, 0, buffer.length);
      parcel.setDataPosition(0);
      return ParcelableHandwritingGesture.CREATOR.createFromParcel(parcel).get();
    } finally {
      if (parcel != null) {
        parcel.recycle();
      }
    }
  }
}
