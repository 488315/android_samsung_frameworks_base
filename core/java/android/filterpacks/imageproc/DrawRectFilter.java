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

  public DrawRectFilter(String name) {
    super(name);
    this.mColorRed = 0.8f;
    this.mColorGreen = 0.8f;
    this.mColorBlue = 0.0f;
    this.mVertexShader =
        "attribute vec4 aPosition;\nvoid main() {\n  gl_Position = aPosition;\n}\n";
    this.mFixedColorFragmentShader =
        "precision mediump float;\n"
            + "uniform vec4 color;\n"
            + "void main() {\n"
            + "  gl_FragColor = color;\n"
            + "}\n";
  }

  @Override // android.filterfw.core.Filter
  public void setupPorts() {
    addMaskedInputPort("image", ImageFormat.create(3, 3));
    addMaskedInputPort("box", ObjectFormat.fromClass(Quad.class, 1));
    addOutputBasedOnInput("image", "image");
  }

  @Override // android.filterfw.core.Filter
  public FrameFormat getOutputFormat(String portName, FrameFormat inputFormat) {
    return inputFormat;
  }

  @Override // android.filterfw.core.Filter
  public void prepare(FilterContext context) {
    this.mProgram =
        new ShaderProgram(
            context,
            "attribute vec4 aPosition;\nvoid main() {\n  gl_Position = aPosition;\n}\n",
            "precision mediump float;\n"
                + "uniform vec4 color;\n"
                + "void main() {\n"
                + "  gl_FragColor = color;\n"
                + "}\n");
  }

  @Override // android.filterfw.core.Filter
  public void process(FilterContext env) {
    Frame imageFrame = pullInput("image");
    Frame boxFrame = pullInput("box");
    Quad box = (Quad) boxFrame.getObjectValue();
    Quad box2 = box.scaled(2.0f).translated(-1.0f, -1.0f);
    GLFrame output = (GLFrame) env.getFrameManager().duplicateFrame(imageFrame);
    output.focus();
    renderBox(box2);
    pushOutput("image", output);
    output.release();
  }

  private void renderBox(Quad box) {
    float[] color = {this.mColorRed, this.mColorGreen, this.mColorBlue, 1.0f};
    float[] vertexValues = {
      box.f61p0.f59x,
      box.f61p0.f60y,
      box.f62p1.f59x,
      box.f62p1.f60y,
      box.f64p3.f59x,
      box.f64p3.f60y,
      box.f63p2.f59x,
      box.f63p2.f60y
    };
    this.mProgram.setHostValue("color", color);
    this.mProgram.setAttributeValues("aPosition", vertexValues, 2);
    this.mProgram.setVertexCount(4);
    this.mProgram.beginDrawing();
    GLES20.glLineWidth(1.0f);
    GLES20.glDrawArrays(2, 0, 4);
  }
}
