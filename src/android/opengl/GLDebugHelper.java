package android.opengl;

import java.io.Writer;
import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.opengles.GL;

/* loaded from: classes3.dex */
public class GLDebugHelper {
    public static final int CONFIG_CHECK_GL_ERROR = 1;
    public static final int CONFIG_CHECK_THREAD = 2;
    public static final int CONFIG_LOG_ARGUMENT_NAMES = 4;
    public static final int ERROR_WRONG_THREAD = 28672;

    public static GL wrap(GL gl, int i, Writer writer) {
        if (i != 0) {
            gl = new GLErrorWrapper(gl, i);
        }
        if (writer != null) {
            return new GLLogWrapper(gl, writer, (i & 4) != 0);
        }
        return gl;
    }

    public static EGL wrap(EGL egl, int i, Writer writer) {
        return writer != null ? new EGLLogWrapper(egl, i, writer) : egl;
    }
}
