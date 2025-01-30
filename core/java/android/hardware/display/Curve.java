package android.hardware.display;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes2.dex */
public final class Curve implements Parcelable {
  public static final Parcelable.Creator<Curve> CREATOR =
      new Parcelable.Creator<Curve>() { // from class: android.hardware.display.Curve.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Curve createFromParcel(Parcel in) {
          float[] x = in.createFloatArray();
          float[] y = in.createFloatArray();
          return new Curve(x, y);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Curve[] newArray(int size) {
          return new Curve[size];
        }
      };

  /* renamed from: mX */
  private final float[] f136mX;

  /* renamed from: mY */
  private final float[] f137mY;

  public Curve(float[] x, float[] y) {
    this.f136mX = x;
    this.f137mY = y;
  }

  public float[] getX() {
    return this.f136mX;
  }

  public float[] getY() {
    return this.f137mY;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeFloatArray(this.f136mX);
    out.writeFloatArray(this.f137mY);
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
    int size = this.f136mX.length;
    for (int i = 0; i < size; i++) {
      if (i != 0) {
        sb.append(", ");
      }
      sb.append(NavigationBarInflaterView.KEY_CODE_START)
          .append(this.f136mX[i])
          .append(", ")
          .append(this.f137mY[i])
          .append(NavigationBarInflaterView.KEY_CODE_END);
    }
    sb.append(NavigationBarInflaterView.SIZE_MOD_END);
    return sb.toString();
  }
}
