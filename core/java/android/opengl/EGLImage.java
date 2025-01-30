package android.opengl;

/* loaded from: classes3.dex */
public class EGLImage extends EGLObjectHandle {
  private EGLImage(long handle) {
    super(handle);
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof EGLImage)) {
      return false;
    }
    EGLImage that = (EGLImage) o;
    return getNativeHandle() == that.getNativeHandle();
  }
}
