package android.opengl;

public class EGLContext extends EGLObjectHandle {
    private EGLContext(long handle) {
        super(handle);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EGLContext)) {
            return false;
        }
        EGLContext that = (EGLContext) o;
        return getNativeHandle() == that.getNativeHandle();
    }
}
