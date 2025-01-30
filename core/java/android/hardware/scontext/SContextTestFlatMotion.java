package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextTestFlatMotion extends SContextEventContext {
  public static final Parcelable.Creator<SContextTestFlatMotion> CREATOR =
      new Parcelable.Creator<
          SContextTestFlatMotion>() { // from class:
                                      // android.hardware.scontext.SContextTestFlatMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextTestFlatMotion createFromParcel(Parcel in) {
          return new SContextTestFlatMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextTestFlatMotion[] newArray(int size) {
          return new SContextTestFlatMotion[size];
        }
      };
  private Bundle mContext;

  SContextTestFlatMotion() {
    this.mContext = new Bundle();
  }

  SContextTestFlatMotion(Parcel src) {
    readFromParcel(src);
  }

  public int getAction() {
    return this.mContext.getInt("Action");
  }

  @Override // android.hardware.scontext.SContextEventContext,
            // com.samsung.android.hardware.context.SemContextEventContext
  public void setValues(Bundle context) {
    this.mContext = context;
  }

  @Override // com.samsung.android.hardware.context.SemContextEventContext,
            // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeBundle(this.mContext);
  }

  private void readFromParcel(Parcel src) {
    this.mContext = src.readBundle();
  }
}
