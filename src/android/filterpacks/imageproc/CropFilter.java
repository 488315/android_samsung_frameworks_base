package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.format.ObjectFormat;
import android.filterfw.geometry.Quad;

/* loaded from: classes.dex */
public class CropFilter extends Filter {

    @GenerateFieldPort(name = "fillblack")
    private boolean mFillBlack;
    private final String mFragShader;
    private FrameFormat mLastFormat;

    @GenerateFieldPort(name = "oheight")
    private int mOutputHeight;

    @GenerateFieldPort(name = "owidth")
    private int mOutputWidth;
    private Program mProgram;

    public CropFilter(String str) {
        super(str);
        this.mLastFormat = null;
        this.mOutputWidth = -1;
        this.mOutputHeight = -1;
        this.mFillBlack = false;
        this.mFragShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  const vec2 lo = vec2(0.0, 0.0);\n  const vec2 hi = vec2(1.0, 1.0);\n  const vec4 black = vec4(0.0, 0.0, 0.0, 1.0);\n  bool out_of_bounds =\n    any(lessThan(v_texcoord, lo)) ||\n    any(greaterThan(v_texcoord, hi));\n  if (out_of_bounds) {\n    gl_FragColor = black;\n  } else {\n    gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n  }\n}\n";
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("image", ImageFormat.create(3));
        addMaskedInputPort("box", ObjectFormat.fromClass(Quad.class, 1));
        addOutputBasedOnInput("image", "image");
    }

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        MutableFrameFormat mutableCopy = frameFormat.mutableCopy();
        mutableCopy.setDimensions(0, 0);
        return mutableCopy;
    }

    protected void createProgram(FilterContext filterContext, FrameFormat frameFormat) {
        FrameFormat frameFormat2 = this.mLastFormat;
        if (frameFormat2 == null || frameFormat2.getTarget() != frameFormat.getTarget()) {
            this.mLastFormat = frameFormat;
            this.mProgram = null;
            if (frameFormat.getTarget() == 3) {
                if (this.mFillBlack) {
                    this.mProgram = new ShaderProgram(filterContext, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  const vec2 lo = vec2(0.0, 0.0);\n  const vec2 hi = vec2(1.0, 1.0);\n  const vec4 black = vec4(0.0, 0.0, 0.0, 1.0);\n  bool out_of_bounds =\n    any(lessThan(v_texcoord, lo)) ||\n    any(greaterThan(v_texcoord, hi));\n  if (out_of_bounds) {\n    gl_FragColor = black;\n  } else {\n    gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n  }\n}\n");
                } else {
                    this.mProgram = ShaderProgram.createIdentity(filterContext);
                }
            }
            if (this.mProgram != null) {
                return;
            }
            throw new RuntimeException("Could not create a program for crop filter " + this + "!");
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame pullInput = pullInput("image");
        Frame pullInput2 = pullInput("box");
        createProgram(filterContext, pullInput.getFormat());
        Quad quad = (Quad) pullInput2.getObjectValue();
        MutableFrameFormat mutableCopy = pullInput.getFormat().mutableCopy();
        int i = this.mOutputWidth;
        if (i == -1) {
            i = mutableCopy.getWidth();
        }
        int i2 = this.mOutputHeight;
        if (i2 == -1) {
            i2 = mutableCopy.getHeight();
        }
        mutableCopy.setDimensions(i, i2);
        Frame newFrame = filterContext.getFrameManager().newFrame(mutableCopy);
        Program program = this.mProgram;
        if (program instanceof ShaderProgram) {
            ((ShaderProgram) program).setSourceRegion(quad);
        }
        this.mProgram.process(pullInput, newFrame);
        pushOutput("image", newFrame);
        newFrame.release();
    }
}
