package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GLFrame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.format.ObjectFormat;
import android.filterfw.geometry.Quad;
import android.opengl.GLES20;

/* loaded from: classes.dex */
public class DrawRectFilter extends Filter {

    @GenerateFieldPort(hasDefault = true, name = "colorBlue")
    private float mColorBlue;

    @GenerateFieldPort(hasDefault = true, name = "colorGreen")
    private float mColorGreen;

    @GenerateFieldPort(hasDefault = true, name = "colorRed")
    private float mColorRed;
    private final String mFixedColorFragmentShader;
    private ShaderProgram mProgram;
    private final String mVertexShader;

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        return frameFormat;
    }

    public DrawRectFilter(String str) {
        super(str);
        this.mColorRed = 0.8f;
        this.mColorGreen = 0.8f;
        this.mColorBlue = 0.0f;
        this.mVertexShader = "attribute vec4 aPosition;\nvoid main() {\n  gl_Position = aPosition;\n}\n";
        this.mFixedColorFragmentShader = "precision mediump float;\nuniform vec4 color;\nvoid main() {\n  gl_FragColor = color;\n}\n";
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("image", ImageFormat.create(3, 3));
        addMaskedInputPort("box", ObjectFormat.fromClass(Quad.class, 1));
        addOutputBasedOnInput("image", "image");
    }

    @Override // android.filterfw.core.Filter
    public void prepare(FilterContext filterContext) {
        this.mProgram = new ShaderProgram(filterContext, "attribute vec4 aPosition;\nvoid main() {\n  gl_Position = aPosition;\n}\n", "precision mediump float;\nuniform vec4 color;\nvoid main() {\n  gl_FragColor = color;\n}\n");
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame framePullInput = pullInput("image");
        Quad quadTranslated = ((Quad) pullInput("box").getObjectValue()).scaled(2.0f).translated(-1.0f, -1.0f);
        GLFrame gLFrame = (GLFrame) filterContext.getFrameManager().duplicateFrame(framePullInput);
        gLFrame.focus();
        renderBox(quadTranslated);
        pushOutput("image", gLFrame);
        gLFrame.release();
    }

    private void renderBox(Quad quad) {
        float[] fArr = {this.mColorRed, this.mColorGreen, this.mColorBlue, 1.0f};
        float[] fArr2 = {quad.p0.x, quad.p0.y, quad.p1.x, quad.p1.y, quad.p3.x, quad.p3.y, quad.p2.x, quad.p2.y};
        this.mProgram.setHostValue("color", fArr);
        this.mProgram.setAttributeValues("aPosition", fArr2, 2);
        this.mProgram.setVertexCount(4);
        this.mProgram.beginDrawing();
        GLES20.glLineWidth(1.0f);
        GLES20.glDrawArrays(2, 0, 4);
    }
}
