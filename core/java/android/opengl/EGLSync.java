package android.opengl;

public class EGLSync extends EGLObjectHandle {
    private EGLSync(long handle) {
        super(handle);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EGLSync)) {
            return false;
        }
        EGLSync that = (EGLSync) o;
        return getNativeHandle() == that.getNativeHandle();
    }
}
