package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextBounceLongMotion extends SContextEventContext {
  public static final Parcelable.Creator<SContextBounceLongMotion> CREATOR =
      new Parcelable.Creator<
          SContextBounceLongMotion>() { // from class:
                                        // android.hardware.scontext.SContextBounceLongMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextBounceLongMotion createFromParcel(Parcel in) {
          return new SContextBounceLongMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextBounceLongMotion[] newArray(int size) {
          return new SContextBounceLongMotion[size];
        }
      };
  private Bundle mContext;

  SContextBounceLongMotion() {
    this.mContext = new Bundle();
  }

  SContextBounceLongMotion(Parcel src) {
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
            // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeBundle(this.mContext);
  }

  private void readFromParcel(Parcel src) {
    this.mContext = src.readBundle();
  }
}
