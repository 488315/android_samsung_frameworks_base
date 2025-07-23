package android.opengl;

/* loaded from: classes3.dex */
public class EGLSync extends EGLObjectHandle {
    private EGLSync(long j) {
        super(j);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EGLSync) && getNativeHandle() == ((EGLSync) obj).getNativeHandle();
    }
}
