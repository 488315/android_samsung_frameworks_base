package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;

public class SepiaFilter extends Filter {
    private Program mProgram;
    private final String mSepiaShader;
    private int mTarget;

    @GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;

    public SepiaFilter(String name) {
        super(name);
        this.mTileSize = 640;
        this.mTarget = 0;
        this.mSepiaShader =
                "precision mediump float;\n"
                        + "uniform sampler2D tex_sampler_0;\n"
                        + "uniform mat3 matrix;\n"
                        + "varying vec2 v_texcoord;\n"
                        + "void main() {\n"
                        + "  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n"
                        + "  vec3 new_color = min(matrix * color.rgb, 1.0);\n"
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
                                        + "uniform mat3 matrix;\n"
                                        + "varying vec2 v_texcoord;\n"
                                        + "void main() {\n"
                                        + "  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n"
                                        + "  vec3 new_color = min(matrix * color.rgb, 1.0);\n"
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
    public void process(FilterContext context) {
        Frame input = pullInput("image");
        FrameFormat inputFormat = input.getFormat();
        Frame output = context.getFrameManager().newFrame(inputFormat);
        if (this.mProgram == null || inputFormat.getTarget() != this.mTarget) {
            initProgram(context, inputFormat.getTarget());
            initParameters();
        }
        this.mProgram.process(input, output);
        pushOutput("image", output);
        output.release();
    }

    private void initParameters() {
        float[] weights = {
            0.3930664f,
            0.3491211f,
            0.27197266f,
            0.76904297f,
            0.68603516f,
            0.53564453f,
            0.18896484f,
            0.16796875f,
            0.13085938f
        };
        this.mProgram.setHostValue("matrix", weights);
    }
}
