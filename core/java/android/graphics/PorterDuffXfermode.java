package android.graphics;


/* loaded from: classes.dex */
public class PorterDuffXfermode extends Xfermode {
  public PorterDuffXfermode(PorterDuff.Mode mode) {
    this.porterDuffMode = mode.nativeInt;
  }
}
