package android.opengl;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL;

/* loaded from: classes3.dex */
class GLErrorWrapper extends GLWrapperBase {
    boolean mCheckError;
    boolean mCheckThread;
    Thread mOurThread;

    public GLErrorWrapper(GL gl, int i) {
        super(gl);
        this.mCheckError = (i & 1) != 0;
        this.mCheckThread = (i & 2) != 0;
    }

    private void checkThread() {
        if (this.mCheckThread) {
            Thread currentThread = Thread.currentThread();
            Thread thread = this.mOurThread;
            if (thread == null) {
                this.mOurThread = currentThread;
            } else if (!thread.equals(currentThread)) {
                throw new GLException(28672, "OpenGL method called from wrong thread.");
            }
        }
    }

    private void checkError() {
        int glGetError;
        if (this.mCheckError && (glGetError = this.mgl.glGetError()) != 0) {
            throw new GLException(glGetError);
        }
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glActiveTexture(int i) {
        checkThread();
        this.mgl.glActiveTexture(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glAlphaFunc(int i, float f) {
        checkThread();
        this.mgl.glAlphaFunc(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glAlphaFuncx(int i, int i2) {
        checkThread();
        this.mgl.glAlphaFuncx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBindTexture(int i, int i2) {
        checkThread();
        this.mgl.glBindTexture(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glBlendFunc(int i, int i2) {
        checkThread();
        this.mgl.glBlendFunc(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClear(int i) {
        checkThread();
        this.mgl.glClear(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearColor(float f, float f2, float f3, float f4) {
        checkThread();
        this.mgl.glClearColor(f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearColorx(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl.glClearColorx(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearDepthf(float f) {
        checkThread();
        this.mgl.glClearDepthf(f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearDepthx(int i) {
        checkThread();
        this.mgl.glClearDepthx(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearStencil(int i) {
        checkThread();
        this.mgl.glClearStencil(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClientActiveTexture(int i) {
        checkThread();
        this.mgl.glClientActiveTexture(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColor4f(float f, float f2, float f3, float f4) {
        checkThread();
        this.mgl.glColor4f(f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColor4x(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl.glColor4x(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColorMask(boolean z, boolean z2, boolean z3, boolean z4) {
        checkThread();
        this.mgl.glColorMask(z, z2, z3, z4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColorPointer(int i, int i2, int i3, Buffer buffer) {
        checkThread();
        this.mgl.glColorPointer(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glCompressedTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, Buffer buffer) {
        checkThread();
        this.mgl.glCompressedTexImage2D(i, i2, i3, i4, i5, i6, i7, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glCompressedTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, Buffer buffer) {
        checkThread();
        this.mgl.glCompressedTexSubImage2D(i, i2, i3, i4, i5, i6, i7, i8, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glCopyTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        checkThread();
        this.mgl.glCopyTexImage2D(i, i2, i3, i4, i5, i6, i7, i8);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glCopyTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        checkThread();
        this.mgl.glCopyTexSubImage2D(i, i2, i3, i4, i5, i6, i7, i8);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glCullFace(int i) {
        checkThread();
        this.mgl.glCullFace(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDeleteTextures(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl.glDeleteTextures(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDeleteTextures(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl.glDeleteTextures(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthFunc(int i) {
        checkThread();
        this.mgl.glDepthFunc(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthMask(boolean z) {
        checkThread();
        this.mgl.glDepthMask(z);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthRangef(float f, float f2) {
        checkThread();
        this.mgl.glDepthRangef(f, f2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthRangex(int i, int i2) {
        checkThread();
        this.mgl.glDepthRangex(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDisable(int i) {
        checkThread();
        this.mgl.glDisable(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDisableClientState(int i) {
        checkThread();
        this.mgl.glDisableClientState(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDrawArrays(int i, int i2, int i3) {
        checkThread();
        this.mgl.glDrawArrays(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDrawElements(int i, int i2, int i3, Buffer buffer) {
        checkThread();
        this.mgl.glDrawElements(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11Ext, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glEnable(int i) {
        checkThread();
        this.mgl.glEnable(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11Ext
    public void glEnableClientState(int i) {
        checkThread();
        this.mgl.glEnableClientState(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFinish() {
        checkThread();
        this.mgl.glFinish();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFlush() {
        checkThread();
        this.mgl.glFlush();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogf(int i, float f) {
        checkThread();
        this.mgl.glFogf(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogfv(int i, float[] fArr, int i2) {
        checkThread();
        this.mgl.glFogfv(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogfv(int i, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl.glFogfv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogx(int i, int i2) {
        checkThread();
        this.mgl.glFogx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogxv(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl.glFogxv(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogxv(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl.glFogxv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFrontFace(int i) {
        checkThread();
        this.mgl.glFrontFace(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFrustumf(float f, float f2, float f3, float f4, float f5, float f6) {
        checkThread();
        this.mgl.glFrustumf(f, f2, f3, f4, f5, f6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFrustumx(int i, int i2, int i3, int i4, int i5, int i6) {
        checkThread();
        this.mgl.glFrustumx(i, i2, i3, i4, i5, i6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glGenTextures(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl.glGenTextures(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glGenTextures(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl.glGenTextures(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public int glGetError() {
        checkThread();
        return this.mgl.glGetError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetIntegerv(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl.glGetIntegerv(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetIntegerv(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl.glGetIntegerv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public String glGetString(int i) {
        checkThread();
        String glGetString = this.mgl.glGetString(i);
        checkError();
        return glGetString;
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glHint(int i, int i2) {
        checkThread();
        this.mgl.glHint(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelf(int i, float f) {
        checkThread();
        this.mgl.glLightModelf(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelfv(int i, float[] fArr, int i2) {
        checkThread();
        this.mgl.glLightModelfv(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelfv(int i, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl.glLightModelfv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelx(int i, int i2) {
        checkThread();
        this.mgl.glLightModelx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelxv(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl.glLightModelxv(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelxv(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl.glLightModelxv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightf(int i, int i2, float f) {
        checkThread();
        this.mgl.glLightf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightfv(int i, int i2, float[] fArr, int i3) {
        checkThread();
        this.mgl.glLightfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightfv(int i, int i2, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl.glLightfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightx(int i, int i2, int i3) {
        checkThread();
        this.mgl.glLightx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightxv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl.glLightxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightxv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl.glLightxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLineWidth(float f) {
        checkThread();
        this.mgl.glLineWidth(f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLineWidthx(int i) {
        checkThread();
        this.mgl.glLineWidthx(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadIdentity() {
        checkThread();
        this.mgl.glLoadIdentity();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixf(float[] fArr, int i) {
        checkThread();
        this.mgl.glLoadMatrixf(fArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixf(FloatBuffer floatBuffer) {
        checkThread();
        this.mgl.glLoadMatrixf(floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixx(int[] iArr, int i) {
        checkThread();
        this.mgl.glLoadMatrixx(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixx(IntBuffer intBuffer) {
        checkThread();
        this.mgl.glLoadMatrixx(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLogicOp(int i) {
        checkThread();
        this.mgl.glLogicOp(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialf(int i, int i2, float f) {
        checkThread();
        this.mgl.glMaterialf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialfv(int i, int i2, float[] fArr, int i3) {
        checkThread();
        this.mgl.glMaterialfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialfv(int i, int i2, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl.glMaterialfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialx(int i, int i2, int i3) {
        checkThread();
        this.mgl.glMaterialx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialxv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl.glMaterialxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialxv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl.glMaterialxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMatrixMode(int i) {
        checkThread();
        this.mgl.glMatrixMode(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixf(float[] fArr, int i) {
        checkThread();
        this.mgl.glMultMatrixf(fArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixf(FloatBuffer floatBuffer) {
        checkThread();
        this.mgl.glMultMatrixf(floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixx(int[] iArr, int i) {
        checkThread();
        this.mgl.glMultMatrixx(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixx(IntBuffer intBuffer) {
        checkThread();
        this.mgl.glMultMatrixx(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultiTexCoord4f(int i, float f, float f2, float f3, float f4) {
        checkThread();
        this.mgl.glMultiTexCoord4f(i, f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultiTexCoord4x(int i, int i2, int i3, int i4, int i5) {
        checkThread();
        this.mgl.glMultiTexCoord4x(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glNormal3f(float f, float f2, float f3) {
        checkThread();
        this.mgl.glNormal3f(f, f2, f3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glNormal3x(int i, int i2, int i3) {
        checkThread();
        this.mgl.glNormal3x(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glNormalPointer(int i, int i2, Buffer buffer) {
        checkThread();
        this.mgl.glNormalPointer(i, i2, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glOrthof(float f, float f2, float f3, float f4, float f5, float f6) {
        checkThread();
        this.mgl.glOrthof(f, f2, f3, f4, f5, f6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glOrthox(int i, int i2, int i3, int i4, int i5, int i6) {
        checkThread();
        this.mgl.glOrthox(i, i2, i3, i4, i5, i6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPixelStorei(int i, int i2) {
        checkThread();
        this.mgl.glPixelStorei(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPointSize(float f) {
        checkThread();
        this.mgl.glPointSize(f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPointSizex(int i) {
        checkThread();
        this.mgl.glPointSizex(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPolygonOffset(float f, float f2) {
        checkThread();
        this.mgl.glPolygonOffset(f, f2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPolygonOffsetx(int i, int i2) {
        checkThread();
        this.mgl.glPolygonOffsetx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPopMatrix() {
        checkThread();
        this.mgl.glPopMatrix();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPushMatrix() {
        checkThread();
        this.mgl.glPushMatrix();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glReadPixels(int i, int i2, int i3, int i4, int i5, int i6, Buffer buffer) {
        checkThread();
        this.mgl.glReadPixels(i, i2, i3, i4, i5, i6, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glRotatef(float f, float f2, float f3, float f4) {
        checkThread();
        this.mgl.glRotatef(f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glRotatex(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl.glRotatex(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glSampleCoverage(float f, boolean z) {
        checkThread();
        this.mgl.glSampleCoverage(f, z);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glSampleCoveragex(int i, boolean z) {
        checkThread();
        this.mgl.glSampleCoveragex(i, z);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glScalef(float f, float f2, float f3) {
        checkThread();
        this.mgl.glScalef(f, f2, f3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glScalex(int i, int i2, int i3) {
        checkThread();
        this.mgl.glScalex(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glScissor(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl.glScissor(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glShadeModel(int i) {
        checkThread();
        this.mgl.glShadeModel(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glStencilFunc(int i, int i2, int i3) {
        checkThread();
        this.mgl.glStencilFunc(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glStencilMask(int i) {
        checkThread();
        this.mgl.glStencilMask(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glStencilOp(int i, int i2, int i3) {
        checkThread();
        this.mgl.glStencilOp(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexCoordPointer(int i, int i2, int i3, Buffer buffer) {
        checkThread();
        this.mgl.glTexCoordPointer(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvf(int i, int i2, float f) {
        checkThread();
        this.mgl.glTexEnvf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvfv(int i, int i2, float[] fArr, int i3) {
        checkThread();
        this.mgl.glTexEnvfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvfv(int i, int i2, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl.glTexEnvfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvx(int i, int i2, int i3) {
        checkThread();
        this.mgl.glTexEnvx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvxv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl.glTexEnvxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvxv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl.glTexEnvxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, Buffer buffer) {
        checkThread();
        this.mgl.glTexImage2D(i, i2, i3, i4, i5, i6, i7, i8, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexParameterf(int i, int i2, float f) {
        checkThread();
        this.mgl.glTexParameterf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexParameterx(int i, int i2, int i3) {
        checkThread();
        this.mgl.glTexParameterx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameteriv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11.glTexParameteriv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameteriv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glTexParameteriv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, Buffer buffer) {
        checkThread();
        this.mgl.glTexSubImage2D(i, i2, i3, i4, i5, i6, i7, i8, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTranslatef(float f, float f2, float f3) {
        checkThread();
        this.mgl.glTranslatef(f, f2, f3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTranslatex(int i, int i2, int i3) {
        checkThread();
        this.mgl.glTranslatex(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glVertexPointer(int i, int i2, int i3, Buffer buffer) {
        checkThread();
        this.mgl.glVertexPointer(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glViewport(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl.glViewport(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanef(int i, float[] fArr, int i2) {
        checkThread();
        this.mgl11.glClipPlanef(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanef(int i, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl11.glClipPlanef(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanex(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl11.glClipPlanex(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanex(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glClipPlanex(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexfOES(float f, float f2, float f3, float f4, float f5) {
        checkThread();
        this.mgl11Ext.glDrawTexfOES(f, f2, f3, f4, f5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexfvOES(float[] fArr, int i) {
        checkThread();
        this.mgl11Ext.glDrawTexfvOES(fArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexfvOES(FloatBuffer floatBuffer) {
        checkThread();
        this.mgl11Ext.glDrawTexfvOES(floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexiOES(int i, int i2, int i3, int i4, int i5) {
        checkThread();
        this.mgl11Ext.glDrawTexiOES(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexivOES(int[] iArr, int i) {
        checkThread();
        this.mgl11Ext.glDrawTexivOES(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexivOES(IntBuffer intBuffer) {
        checkThread();
        this.mgl11Ext.glDrawTexivOES(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexsOES(short s, short s2, short s3, short s4, short s5) {
        checkThread();
        this.mgl11Ext.glDrawTexsOES(s, s2, s3, s4, s5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexsvOES(short[] sArr, int i) {
        checkThread();
        this.mgl11Ext.glDrawTexsvOES(sArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexsvOES(ShortBuffer shortBuffer) {
        checkThread();
        this.mgl11Ext.glDrawTexsvOES(shortBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexxOES(int i, int i2, int i3, int i4, int i5) {
        checkThread();
        this.mgl11Ext.glDrawTexxOES(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexxvOES(int[] iArr, int i) {
        checkThread();
        this.mgl11Ext.glDrawTexxvOES(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexxvOES(IntBuffer intBuffer) {
        checkThread();
        this.mgl11Ext.glDrawTexxvOES(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10Ext
    public int glQueryMatrixxOES(int[] iArr, int i, int[] iArr2, int i2) {
        checkThread();
        int glQueryMatrixxOES = this.mgl10Ext.glQueryMatrixxOES(iArr, i, iArr2, i2);
        checkError();
        return glQueryMatrixxOES;
    }

    @Override // javax.microedition.khronos.opengles.GL10Ext
    public int glQueryMatrixxOES(IntBuffer intBuffer, IntBuffer intBuffer2) {
        checkThread();
        int glQueryMatrixxOES = this.mgl10Ext.glQueryMatrixxOES(intBuffer, intBuffer2);
        checkError();
        return glQueryMatrixxOES;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glBindBuffer(int i, int i2) {
        checkThread();
        this.mgl11.glBindBuffer(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glBufferData(int i, int i2, Buffer buffer, int i3) {
        checkThread();
        this.mgl11.glBufferData(i, i2, buffer, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glBufferSubData(int i, int i2, int i3, Buffer buffer) {
        checkThread();
        this.mgl11.glBufferSubData(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glColor4ub(byte b, byte b2, byte b3, byte b4) {
        checkThread();
        this.mgl11.glColor4ub(b, b2, b3, b4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glColorPointer(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl11.glColorPointer(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glDeleteBuffers(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl11.glDeleteBuffers(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glDeleteBuffers(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glDeleteBuffers(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glDrawElements(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl11.glDrawElements(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGenBuffers(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl11.glGenBuffers(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGenBuffers(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glGenBuffers(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBooleanv(int i, boolean[] zArr, int i2) {
        checkThread();
        this.mgl11.glGetBooleanv(i, zArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBooleanv(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glGetBooleanv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBufferParameteriv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11.glGetBufferParameteriv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBufferParameteriv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glGetBufferParameteriv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanef(int i, float[] fArr, int i2) {
        checkThread();
        this.mgl11.glGetClipPlanef(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanef(int i, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl11.glGetClipPlanef(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanex(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl11.glGetClipPlanex(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanex(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glGetClipPlanex(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFixedv(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl11.glGetFixedv(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFixedv(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glGetFixedv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFloatv(int i, float[] fArr, int i2) {
        checkThread();
        this.mgl11.glGetFloatv(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFloatv(int i, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl11.glGetFloatv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightfv(int i, int i2, float[] fArr, int i3) {
        checkThread();
        this.mgl11.glGetLightfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightfv(int i, int i2, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl11.glGetLightfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightxv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11.glGetLightxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightxv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glGetLightxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialfv(int i, int i2, float[] fArr, int i3) {
        checkThread();
        this.mgl11.glGetMaterialfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialfv(int i, int i2, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl11.glGetMaterialfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialxv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11.glGetMaterialxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialxv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glGetMaterialxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetPointerv(int i, Buffer[] bufferArr) {
        checkThread();
        this.mgl11.glGetPointerv(i, bufferArr);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnviv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11.glGetTexEnviv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnviv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glGetTexEnviv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnvxv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11.glGetTexEnvxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnvxv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glGetTexEnvxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterfv(int i, int i2, float[] fArr, int i3) {
        checkThread();
        this.mgl11.glGetTexParameterfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterfv(int i, int i2, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl11.glGetTexParameterfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameteriv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11.glGetTexParameteriv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameteriv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glGetTexParameteriv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterxv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11.glGetTexParameterxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterxv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glGetTexParameterxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public boolean glIsBuffer(int i) {
        checkThread();
        boolean glIsBuffer = this.mgl11.glIsBuffer(i);
        checkError();
        return glIsBuffer;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public boolean glIsEnabled(int i) {
        checkThread();
        boolean glIsEnabled = this.mgl11.glIsEnabled(i);
        checkError();
        return glIsEnabled;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public boolean glIsTexture(int i) {
        checkThread();
        boolean glIsTexture = this.mgl11.glIsTexture(i);
        checkError();
        return glIsTexture;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glNormalPointer(int i, int i2, int i3) {
        checkThread();
        this.mgl11.glNormalPointer(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterf(int i, float f) {
        checkThread();
        this.mgl11.glPointParameterf(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterfv(int i, float[] fArr, int i2) {
        checkThread();
        this.mgl11.glPointParameterfv(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterfv(int i, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl11.glPointParameterfv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterx(int i, int i2) {
        checkThread();
        this.mgl11.glPointParameterx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterxv(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl11.glPointParameterxv(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterxv(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glPointParameterxv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointSizePointerOES(int i, int i2, Buffer buffer) {
        checkThread();
        this.mgl11.glPointSizePointerOES(i, i2, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexCoordPointer(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl11.glTexCoordPointer(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexEnvi(int i, int i2, int i3) {
        checkThread();
        this.mgl11.glTexEnvi(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexEnviv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11.glTexEnviv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexEnviv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glTexEnviv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11, javax.microedition.khronos.opengles.GL11Ext
    public void glTexParameterfv(int i, int i2, float[] fArr, int i3) {
        checkThread();
        this.mgl11.glTexParameterfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameterfv(int i, int i2, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl11.glTexParameterfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameteri(int i, int i2, int i3) {
        checkThread();
        this.mgl11.glTexParameteri(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameterxv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11.glTexParameterxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameterxv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11.glTexParameterxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glVertexPointer(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl11.glVertexPointer(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glCurrentPaletteMatrixOES(int i) {
        checkThread();
        this.mgl11Ext.glCurrentPaletteMatrixOES(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glLoadPaletteFromModelViewMatrixOES() {
        checkThread();
        this.mgl11Ext.glLoadPaletteFromModelViewMatrixOES();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glMatrixIndexPointerOES(int i, int i2, int i3, Buffer buffer) {
        checkThread();
        this.mgl11Ext.glMatrixIndexPointerOES(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glMatrixIndexPointerOES(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl11Ext.glMatrixIndexPointerOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glWeightPointerOES(int i, int i2, int i3, Buffer buffer) {
        checkThread();
        this.mgl11Ext.glWeightPointerOES(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glWeightPointerOES(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl11Ext.glWeightPointerOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBindFramebufferOES(int i, int i2) {
        checkThread();
        this.mgl11ExtensionPack.glBindFramebufferOES(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBindRenderbufferOES(int i, int i2) {
        checkThread();
        this.mgl11ExtensionPack.glBindRenderbufferOES(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBlendEquation(int i) {
        checkThread();
        this.mgl11ExtensionPack.glBlendEquation(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBlendEquationSeparate(int i, int i2) {
        checkThread();
        this.mgl11ExtensionPack.glBlendEquationSeparate(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBlendFuncSeparate(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl11ExtensionPack.glBlendFuncSeparate(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public int glCheckFramebufferStatusOES(int i) {
        checkThread();
        int glCheckFramebufferStatusOES = this.mgl11ExtensionPack.glCheckFramebufferStatusOES(i);
        checkError();
        return glCheckFramebufferStatusOES;
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteFramebuffersOES(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl11ExtensionPack.glDeleteFramebuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteFramebuffersOES(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glDeleteFramebuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteRenderbuffersOES(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl11ExtensionPack.glDeleteRenderbuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteRenderbuffersOES(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glDeleteRenderbuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glFramebufferRenderbufferOES(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl11ExtensionPack.glFramebufferRenderbufferOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glFramebufferTexture2DOES(int i, int i2, int i3, int i4, int i5) {
        checkThread();
        this.mgl11ExtensionPack.glFramebufferTexture2DOES(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenerateMipmapOES(int i) {
        checkThread();
        this.mgl11ExtensionPack.glGenerateMipmapOES(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenFramebuffersOES(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl11ExtensionPack.glGenFramebuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenFramebuffersOES(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glGenFramebuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenRenderbuffersOES(int i, int[] iArr, int i2) {
        checkThread();
        this.mgl11ExtensionPack.glGenRenderbuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenRenderbuffersOES(int i, IntBuffer intBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glGenRenderbuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetFramebufferAttachmentParameterivOES(int i, int i2, int i3, int[] iArr, int i4) {
        checkThread();
        this.mgl11ExtensionPack.glGetFramebufferAttachmentParameterivOES(i, i2, i3, iArr, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetFramebufferAttachmentParameterivOES(int i, int i2, int i3, IntBuffer intBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glGetFramebufferAttachmentParameterivOES(i, i2, i3, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetRenderbufferParameterivOES(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11ExtensionPack.glGetRenderbufferParameterivOES(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetRenderbufferParameterivOES(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glGetRenderbufferParameterivOES(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenfv(int i, int i2, float[] fArr, int i3) {
        checkThread();
        this.mgl11ExtensionPack.glGetTexGenfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenfv(int i, int i2, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glGetTexGenfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGeniv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11ExtensionPack.glGetTexGeniv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGeniv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glGetTexGeniv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenxv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11ExtensionPack.glGetTexGenxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenxv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glGetTexGenxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public boolean glIsFramebufferOES(int i) {
        checkThread();
        boolean glIsFramebufferOES = this.mgl11ExtensionPack.glIsFramebufferOES(i);
        checkError();
        return glIsFramebufferOES;
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public boolean glIsRenderbufferOES(int i) {
        checkThread();
        this.mgl11ExtensionPack.glIsRenderbufferOES(i);
        checkError();
        return false;
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glRenderbufferStorageOES(int i, int i2, int i3, int i4) {
        checkThread();
        this.mgl11ExtensionPack.glRenderbufferStorageOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenf(int i, int i2, float f) {
        checkThread();
        this.mgl11ExtensionPack.glTexGenf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenfv(int i, int i2, float[] fArr, int i3) {
        checkThread();
        this.mgl11ExtensionPack.glTexGenfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenfv(int i, int i2, FloatBuffer floatBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glTexGenfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGeni(int i, int i2, int i3) {
        checkThread();
        this.mgl11ExtensionPack.glTexGeni(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGeniv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11ExtensionPack.glTexGeniv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGeniv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glTexGeniv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenx(int i, int i2, int i3) {
        checkThread();
        this.mgl11ExtensionPack.glTexGenx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenxv(int i, int i2, int[] iArr, int i3) {
        checkThread();
        this.mgl11ExtensionPack.glTexGenxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenxv(int i, int i2, IntBuffer intBuffer) {
        checkThread();
        this.mgl11ExtensionPack.glTexGenxv(i, i2, intBuffer);
        checkError();
    }
}
