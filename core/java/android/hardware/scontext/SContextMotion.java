package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextMotion extends SContextEventContext {
  public static final Parcelable.Creator<SContextMotion> CREATOR =
      new Parcelable.Creator<
          SContextMotion>() { // from class: android.hardware.scontext.SContextMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMotion createFromParcel(Parcel in) {
          return new SContextMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMotion[] newArray(int size) {
          return new SContextMotion[size];
        }
      };
  private Bundle mContext;

  SContextMotion() {
    this.mContext = new Bundle();
  }

  SContextMotion(Parcel src) {
    readFromParcel(src);
  }

  public int getType() {
    return this.mContext.getInt("Type");
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
