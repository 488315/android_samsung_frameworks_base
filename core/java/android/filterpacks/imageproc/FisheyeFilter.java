package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;

/* loaded from: classes.dex */
public class FisheyeFilter extends Filter {
    private static final String TAG = "FisheyeFilter";
    private static final String mFisheyeShader =
            "precision mediump float;\n"
                    + "uniform sampler2D tex_sampler_0;\n"
                    + "uniform vec2 scale;\n"
                    + "uniform float alpha;\n"
                    + "uniform float radius2;\n"
                    + "uniform float factor;\n"
                    + "varying vec2 v_texcoord;\n"
                    + "void main() {\n"
                    + "  const float m_pi_2 = 1.570963;\n"
                    + "  const float min_dist = 0.01;\n"
                    + "  vec2 coord = v_texcoord - vec2(0.5, 0.5);\n"
                    + "  float dist = length(coord * scale);\n"
                    + "  dist = max(dist, min_dist);\n"
                    + "  float radian = m_pi_2 - atan(alpha * sqrt(radius2 - dist * dist), dist);\n"
                    + "  float scalar = radian * factor / dist;\n"
                    + "  vec2 new_coord = coord * scalar + vec2(0.5, 0.5);\n"
                    + "  gl_FragColor = texture2D(tex_sampler_0, new_coord);\n"
                    + "}\n";
    private int mHeight;
    private Program mProgram;

    @GenerateFieldPort(hasDefault = true, name = "scale")
    private float mScale;

    private int mTarget;

    @GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;

    private int mWidth;

    public FisheyeFilter(String name) {
        super(name);
        this.mScale = 0.0f;
        this.mTileSize = 640;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mTarget = 0;
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
                ShaderProgram shaderProgram = new ShaderProgram(context, mFisheyeShader);
                shaderProgram.setMaximumTileSize(this.mTileSize);
                this.mProgram = shaderProgram;
                this.mTarget = target;
                return;
            default:
                throw new RuntimeException(
                        "Filter FisheyeFilter does not support frames of target " + target + "!");
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext context) {
        Frame input = pullInput("image");
        FrameFormat inputFormat = input.getFormat();
        Frame output = context.getFrameManager().newFrame(inputFormat);
        if (this.mProgram == null || inputFormat.getTarget() != this.mTarget) {
            initProgram(context, inputFormat.getTarget());
        }
        if (inputFormat.getWidth() != this.mWidth || inputFormat.getHeight() != this.mHeight) {
            updateFrameSize(inputFormat.getWidth(), inputFormat.getHeight());
        }
        this.mProgram.process(input, output);
        pushOutput("image", output);
        output.release();
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(String name, FilterContext context) {
        if (this.mProgram != null) {
            updateProgramParams();
        }
    }

    private void updateFrameSize(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        updateProgramParams();
    }

    private void updateProgramParams() {
        float[] scale = new float[2];
        if (this.mWidth > this.mHeight) {
            scale[0] = 1.0f;
            scale[1] = this.mHeight / this.mWidth;
        } else {
            scale[0] = this.mWidth / this.mHeight;
            scale[1] = 1.0f;
        }
        float alpha = (this.mScale * 2.0f) + 0.75f;
        float bound2 = ((scale[0] * scale[0]) + (scale[1] * scale[1])) * 0.25f;
        float bound = (float) Math.sqrt(bound2);
        float radius = 1.15f * bound;
        float radius2 = radius * radius;
        float max_radian =
                1.5707964f
                        - ((float)
                                Math.atan((alpha / bound) * ((float) Math.sqrt(radius2 - bound2))));
        float factor = bound / max_radian;
        this.mProgram.setHostValue("scale", scale);
        this.mProgram.setHostValue("radius2", Float.valueOf(radius2));
        this.mProgram.setHostValue("factor", Float.valueOf(factor));
        this.mProgram.setHostValue("alpha", Float.valueOf(alpha));
    }
}
