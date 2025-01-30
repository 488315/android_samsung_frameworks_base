package android.opengl;

/* loaded from: classes3.dex */
public class EGLSurface extends EGLObjectHandle {
  private EGLSurface(long handle) {
    super(handle);
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof EGLSurface)) {
      return false;
    }
    EGLSurface that = (EGLSurface) o;
    return getNativeHandle() == that.getNativeHandle();
  }
}
