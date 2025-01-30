package android.p009os;

import android.annotation.SystemApi;
import android.util.MathUtils;

@SystemApi
/* loaded from: classes3.dex */
public final class ParcelableHolder implements Parcelable {
  public static final Parcelable.Creator<ParcelableHolder> CREATOR =
      new Parcelable.Creator<ParcelableHolder>() { // from class: android.os.ParcelableHolder.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableHolder createFromParcel(Parcel parcel) {
          ParcelableHolder parcelable = new ParcelableHolder();
          parcelable.readFromParcel(parcel);
          return parcelable;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableHolder[] newArray(int size) {
          return new ParcelableHolder[size];
        }
      };
  private Parcel mParcel;
  private Parcelable mParcelable;
  private int mStability;

  public ParcelableHolder(int stability) {
    this.mStability = 0;
    this.mStability = stability;
  }

  private ParcelableHolder() {
    this.mStability = 0;
  }

  @Override // android.p009os.Parcelable
  public int getStability() {
    return this.mStability;
  }

  public void setParcelable(Parcelable p) {
    if (p != null && getStability() > p.getStability()) {
      throw new BadParcelableException(
          "A ParcelableHolder can only hold things at its stability or higher. The"
              + " ParcelableHolder's stability is "
              + getStability()
              + ", but the parcelable's stability is "
              + p.getStability());
    }
    this.mParcelable = p;
    Parcel parcel = this.mParcel;
    if (parcel != null) {
      parcel.recycle();
      this.mParcel = null;
    }
  }

  public <T extends Parcelable> T getParcelable(Class<T> cls) {
    Parcel parcel = this.mParcel;
    if (parcel == null) {
      Parcelable parcelable = this.mParcelable;
      if (parcelable != null && !cls.isInstance(parcelable)) {
        throw new BadParcelableException(
            "The ParcelableHolder has "
                + this.mParcelable.getClass().getName()
                + ", but the requested type is "
                + cls.getName());
      }
      return (T) this.mParcelable;
    }
    parcel.setDataPosition(0);
    T t = (T) this.mParcel.readParcelable(cls.getClassLoader());
    if (t != null && !cls.isInstance(t)) {
      throw new BadParcelableException(
          "The ParcelableHolder has "
              + t.getClass().getName()
              + ", but the requested type is "
              + cls.getName());
    }
    this.mParcelable = t;
    this.mParcel.recycle();
    this.mParcel = null;
    return t;
  }

  public void readFromParcel(Parcel parcel) {
    int wireStability = parcel.readInt();
    if (this.mStability != wireStability) {
      throw new IllegalArgumentException(
          "Expected stability " + this.mStability + " but got " + wireStability);
    }
    this.mParcelable = null;
    int dataSize = parcel.readInt();
    if (dataSize < 0) {
      throw new IllegalArgumentException("dataSize from parcel is negative");
    }
    if (dataSize == 0) {
      Parcel parcel2 = this.mParcel;
      if (parcel2 != null) {
        parcel2.recycle();
        this.mParcel = null;
        return;
      }
      return;
    }
    if (this.mParcel == null) {
      this.mParcel = Parcel.obtain();
    }
    this.mParcel.setDataPosition(0);
    this.mParcel.setDataSize(0);
    int dataStartPos = parcel.dataPosition();
    this.mParcel.appendFrom(parcel, dataStartPos, dataSize);
    parcel.setDataPosition(MathUtils.addOrThrow(dataStartPos, dataSize));
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeInt(this.mStability);
    Parcel parcel2 = this.mParcel;
    if (parcel2 != null) {
      parcel.writeInt(parcel2.dataSize());
      Parcel parcel3 = this.mParcel;
      parcel.appendFrom(parcel3, 0, parcel3.dataSize());
    } else {
      if (this.mParcelable == null) {
        parcel.writeInt(0);
        return;
      }
      int sizePos = parcel.dataPosition();
      parcel.writeInt(0);
      int dataStartPos = parcel.dataPosition();
      parcel.writeParcelable(this.mParcelable, 0);
      int dataSize = parcel.dataPosition() - dataStartPos;
      parcel.setDataPosition(sizePos);
      parcel.writeInt(dataSize);
      parcel.setDataPosition(MathUtils.addOrThrow(parcel.dataPosition(), dataSize));
    }
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    Parcel parcel = this.mParcel;
    if (parcel != null) {
      return parcel.hasFileDescriptors() ? 1 : 0;
    }
    Parcelable parcelable = this.mParcelable;
    if (parcelable != null) {
      return parcelable.describeContents();
    }
    return 0;
  }
}
