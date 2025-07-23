package android.opengl;

/* loaded from: classes3.dex */
public class EGLDisplay extends EGLObjectHandle {
    private EGLDisplay(long j) {
        super(j);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EGLDisplay) && getNativeHandle() == ((EGLDisplay) obj).getNativeHandle();
    }
}
