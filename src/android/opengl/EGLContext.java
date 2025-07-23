package android.opengl;

/* loaded from: classes3.dex */
public class EGLContext extends EGLObjectHandle {
    private EGLContext(long j) {
        super(j);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EGLContext) && getNativeHandle() == ((EGLContext) obj).getNativeHandle();
    }
}
