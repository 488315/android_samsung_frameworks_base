package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.graphics.Color;

/* loaded from: classes.dex */
public class TintFilter extends Filter {
  private Program mProgram;
  private int mTarget;

  @GenerateFieldPort(hasDefault = true, name = "tile_size")
  private int mTileSize;

  @GenerateFieldPort(hasDefault = true, name = "tint")
  private int mTint;

  private final String mTintShader;

  public TintFilter(String name) {
    super(name);
    this.mTint = -16776961;
    this.mTileSize = 640;
    this.mTarget = 0;
    this.mTintShader =
        "precision mediump float;\n"
            + "uniform sampler2D tex_sampler_0;\n"
            + "uniform vec3 tint;\n"
            + "uniform vec3 color_ratio;\n"
            + "varying vec2 v_texcoord;\n"
            + "void main() {\n"
            + "  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n"
            + "  float avg_color = dot(color_ratio, color.rgb);\n"
            + "  vec3 new_color = min(0.8 * avg_color + 0.2 * tint, 1.0);\n"
            + "  gl_FragColor = vec4(new_color.rgb, color.a);\n"
            + "}\n";
  }

  @Override // android.filterfw.core.Filter
  public void setupPorts() {
    addMaskedInputPort("image", ImageFormat.create(3));
    addOutputBasedOnInput("image", "image");
  }

  @Override // android.filterfw.core.Filter
  public FrameFormat getOutputFormat(String portName, FrameFormat inputFormat) {
    return inputFormat;
  }

  public void initProgram(FilterContext context, int target) {
    switch (target) {
      case 3:
        ShaderProgram shaderProgram =
            new ShaderProgram(
                context,
                "precision mediump float;\n"
                    + "uniform sampler2D tex_sampler_0;\n"
                    + "uniform vec3 tint;\n"
                    + "uniform vec3 color_ratio;\n"
                    + "varying vec2 v_texcoord;\n"
                    + "void main() {\n"
                    + "  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n"
                    + "  float avg_color = dot(color_ratio, color.rgb);\n"
                    + "  vec3 new_color = min(0.8 * avg_color + 0.2 * tint, 1.0);\n"
                    + "  gl_FragColor = vec4(new_color.rgb, color.a);\n"
                    + "}\n");
        shaderProgram.setMaximumTileSize(this.mTileSize);
        this.mProgram = shaderProgram;
        this.mTarget = target;
        return;
      default:
        throw new RuntimeException(
            "Filter Sharpen does not support frames of target " + target + "!");
    }
  }

  @Override // android.filterfw.core.Filter
  public void fieldPortValueUpdated(String name, FilterContext context) {
    if (this.mProgram != null) {
      updateParameters();
    }
  }

  @Override // android.filterfw.core.Filter
  public void process(FilterContext context) {
    Frame input = pullInput("image");
    FrameFormat inputFormat = input.getFormat();
    if (this.mProgram == null || inputFormat.getTarget() != this.mTarget) {
      initProgram(context, inputFormat.getTarget());
      initParameters();
    }
    Frame output = context.getFrameManager().newFrame(inputFormat);
    this.mProgram.process(input, output);
    pushOutput("image", output);
    output.release();
  }

  private void initParameters() {
    float[] color_ratio = {0.21f, 0.71f, 0.07f};
    this.mProgram.setHostValue("color_ratio", color_ratio);
    updateParameters();
  }

  private void updateParameters() {
    float[] tint_color = {
      Color.red(this.mTint) / 255.0f,
      Color.green(this.mTint) / 255.0f,
      Color.blue(this.mTint) / 255.0f
    };
    this.mProgram.setHostValue("tint", tint_color);
  }
}
