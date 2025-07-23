package android.opengl;

/* loaded from: classes3.dex */
public class EGLSurface extends EGLObjectHandle {
    private EGLSurface(long j) {
        super(j);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EGLSurface) && getNativeHandle() == ((EGLSurface) obj).getNativeHandle();
    }
}
