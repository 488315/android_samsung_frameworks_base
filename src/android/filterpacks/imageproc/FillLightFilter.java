package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.util.Log;

/* loaded from: classes.dex */
public class FillLightFilter extends Filter {

    @GenerateFieldPort(hasDefault = true, name = "strength")
    private float mBacklight;
    private final String mFillLightShader;
    private Program mProgram;
    private int mTarget;

    @GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        return frameFormat;
    }

    public FillLightFilter(String str) {
        super(str);
        this.mTileSize = 640;
        this.mBacklight = 0.0f;
        this.mTarget = 0;
        this.mFillLightShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform float mult;\nuniform float igamma;\nvarying vec2 v_texcoord;\nvoid main()\n{\n  const vec3 color_weights = vec3(0.25, 0.5, 0.25);\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float lightmask = dot(color.rgb, color_weights);\n  float backmask = (1.0 - lightmask);\n  vec3 ones = vec3(1.0, 1.0, 1.0);\n  vec3 diff = pow(mult * color.rgb, igamma * ones) - color.rgb;\n  diff = min(diff, 1.0);\n  vec3 new_color = min(color.rgb + diff * backmask, 1.0);\n  gl_FragColor = vec4(new_color, color.a);\n}\n";
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("image", ImageFormat.create(3));
        addOutputBasedOnInput("image", "image");
    }

    public void initProgram(FilterContext filterContext, int i) {
        if (i == 3) {
            ShaderProgram shaderProgram = new ShaderProgram(filterContext, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform float mult;\nuniform float igamma;\nvarying vec2 v_texcoord;\nvoid main()\n{\n  const vec3 color_weights = vec3(0.25, 0.5, 0.25);\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float lightmask = dot(color.rgb, color_weights);\n  float backmask = (1.0 - lightmask);\n  vec3 ones = vec3(1.0, 1.0, 1.0);\n  vec3 diff = pow(mult * color.rgb, igamma * ones) - color.rgb;\n  diff = min(diff, 1.0);\n  vec3 new_color = min(color.rgb + diff * backmask, 1.0);\n  gl_FragColor = vec4(new_color, color.a);\n}\n");
            Log.e("FillLight", "tile size: " + this.mTileSize);
            shaderProgram.setMaximumTileSize(this.mTileSize);
            this.mProgram = shaderProgram;
            this.mTarget = i;
            return;
        }
        throw new RuntimeException("Filter FillLight does not support frames of target " + i + "!");
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame pullInput = pullInput("image");
        FrameFormat format = pullInput.getFormat();
        Frame newFrame = filterContext.getFrameManager().newFrame(format);
        if (this.mProgram == null || format.getTarget() != this.mTarget) {
            initProgram(filterContext, format.getTarget());
            updateParameters();
        }
        this.mProgram.process(pullInput, newFrame);
        pushOutput("image", newFrame);
        newFrame.release();
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(String str, FilterContext filterContext) {
        if (this.mProgram != null) {
            updateParameters();
        }
    }

    private void updateParameters() {
        float f = 1.0f / (((1.0f - this.mBacklight) * 0.7f) + 0.3f);
        this.mProgram.setHostValue("mult", Float.valueOf(f));
        this.mProgram.setHostValue("igamma", Float.valueOf(1.0f / ((0.7f * f) + 0.3f)));
    }
}
