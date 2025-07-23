package android.opengl;

/* loaded from: classes3.dex */
public class EGLImage extends EGLObjectHandle {
    private EGLImage(long j) {
        super(j);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EGLImage) && getNativeHandle() == ((EGLImage) obj).getNativeHandle();
    }
}
