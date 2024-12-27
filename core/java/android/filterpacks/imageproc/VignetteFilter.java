package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.hardware.Camera;

public class VignetteFilter extends Filter {
    private int mHeight;
    private Program mProgram;

    @GenerateFieldPort(hasDefault = true, name = "scale")
    private float mScale;

    private final float mShade;
    private final float mSlope;
    private int mTarget;

    @GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;

    private final String mVignetteShader;
    private int mWidth;

    public VignetteFilter(String name) {
        super(name);
        this.mScale = 0.0f;
        this.mTileSize = 640;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mTarget = 0;
        this.mSlope = 20.0f;
        this.mShade = 0.85f;
        this.mVignetteShader =
                "precision mediump float;\n"
                    + "uniform sampler2D tex_sampler_0;\n"
                    + "uniform float range;\n"
                    + "uniform float inv_max_dist;\n"
                    + "uniform float shade;\n"
                    + "uniform vec2 scale;\n"
                    + "varying vec2 v_texcoord;\n"
                    + "void main() {\n"
                    + "  const float slope = 20.0;\n"
                    + "  vec2 coord = v_texcoord - vec2(0.5, 0.5);\n"
                    + "  float dist = length(coord * scale);\n"
                    + "  float lumen = shade / (1.0 + exp((dist * inv_max_dist - range) * slope)) +"
                    + " (1.0 - shade);\n"
                    + "  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n"
                    + "  gl_FragColor = vec4(color.rgb * lumen, color.a);\n"
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
                                    + "uniform float range;\n"
                                    + "uniform float inv_max_dist;\n"
                                    + "uniform float shade;\n"
                                    + "uniform vec2 scale;\n"
                                    + "varying vec2 v_texcoord;\n"
                                    + "void main() {\n"
                                    + "  const float slope = 20.0;\n"
                                    + "  vec2 coord = v_texcoord - vec2(0.5, 0.5);\n"
                                    + "  float dist = length(coord * scale);\n"
                                    + "  float lumen = shade / (1.0 + exp((dist * inv_max_dist -"
                                    + " range) * slope)) + (1.0 - shade);\n"
                                    + "  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n"
                                    + "  gl_FragColor = vec4(color.rgb * lumen, color.a);\n"
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

    private void initParameters() {
        if (this.mProgram != null) {
            float[] scale = new float[2];
            if (this.mWidth > this.mHeight) {
                scale[0] = 1.0f;
                scale[1] = this.mHeight / this.mWidth;
            } else {
                scale[0] = this.mWidth / this.mHeight;
                scale[1] = 1.0f;
            }
            float max_dist =
                    ((float) Math.sqrt((scale[0] * scale[0]) + (scale[1] * scale[1]))) * 0.5f;
            this.mProgram.setHostValue("scale", scale);
            this.mProgram.setHostValue("inv_max_dist", Float.valueOf(1.0f / max_dist));
            this.mProgram.setHostValue(Camera.Parameters.WHITE_BALANCE_SHADE, Float.valueOf(0.85f));
            updateParameters();
        }
    }

    private void updateParameters() {
        this.mProgram.setHostValue(
                "range", Float.valueOf(1.3f - (((float) Math.sqrt(this.mScale)) * 0.7f)));
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
        }
        if (inputFormat.getWidth() != this.mWidth || inputFormat.getHeight() != this.mHeight) {
            this.mWidth = inputFormat.getWidth();
            this.mHeight = inputFormat.getHeight();
            initParameters();
        }
        Frame output = context.getFrameManager().newFrame(inputFormat);
        this.mProgram.process(input, output);
        pushOutput("image", output);
        output.release();
    }
}
