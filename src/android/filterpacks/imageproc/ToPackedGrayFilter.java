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

/* loaded from: classes.dex */
public class ToPackedGrayFilter extends Filter {
    private final String mColorToPackedGrayShader;

    @GenerateFieldPort(hasDefault = true, name = "keepAspectRatio")
    private boolean mKeepAspectRatio;

    @GenerateFieldPort(hasDefault = true, name = "oheight")
    private int mOHeight;

    @GenerateFieldPort(hasDefault = true, name = "owidth")
    private int mOWidth;
    private Program mProgram;

    public ToPackedGrayFilter(String str) {
        super(str);
        this.mOWidth = 0;
        this.mOHeight = 0;
        this.mKeepAspectRatio = false;
        this.mColorToPackedGrayShader = "precision mediump float;\nconst vec4 coeff_y = vec4(0.299, 0.587, 0.114, 0);\nuniform sampler2D tex_sampler_0;\nuniform float pix_stride;\nvarying vec2 v_texcoord;\nvoid main() {\n  for (int i = 0; i < 4; ++i) {\n    vec4 p = texture2D(tex_sampler_0,\n                       v_texcoord + vec2(pix_stride * float(i), 0.0));\n    gl_FragColor[i] = dot(p, coeff_y);\n  }\n}\n";
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("image", ImageFormat.create(3, 3));
        addOutputBasedOnInput("image", "image");
    }

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        return convertInputFormat(frameFormat);
    }

    private void checkOutputDimensions(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new RuntimeException("Invalid output dimensions: " + i + " " + i2);
        }
    }

    private FrameFormat convertInputFormat(FrameFormat frameFormat) {
        int i = this.mOWidth;
        int i2 = this.mOHeight;
        int width = frameFormat.getWidth();
        int height = frameFormat.getHeight();
        if (this.mOWidth == 0) {
            i = width;
        }
        if (this.mOHeight == 0) {
            i2 = height;
        }
        if (this.mKeepAspectRatio) {
            if (width > height) {
                i = Math.max(i, i2);
                i2 = (height * i) / width;
            } else {
                i2 = Math.max(i, i2);
                i = (width * i2) / height;
            }
        }
        return ImageFormat.create((i <= 0 || i >= 4) ? 4 * (i / 4) : 4, i2, 1, 2);
    }

    @Override // android.filterfw.core.Filter
    public void prepare(FilterContext filterContext) {
        this.mProgram = new ShaderProgram(filterContext, "precision mediump float;\nconst vec4 coeff_y = vec4(0.299, 0.587, 0.114, 0);\nuniform sampler2D tex_sampler_0;\nuniform float pix_stride;\nvarying vec2 v_texcoord;\nvoid main() {\n  for (int i = 0; i < 4; ++i) {\n    vec4 p = texture2D(tex_sampler_0,\n                       v_texcoord + vec2(pix_stride * float(i), 0.0));\n    gl_FragColor[i] = dot(p, coeff_y);\n  }\n}\n");
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame pullInput = pullInput("image");
        FrameFormat format = pullInput.getFormat();
        FrameFormat convertInputFormat = convertInputFormat(format);
        int width = convertInputFormat.getWidth();
        int height = convertInputFormat.getHeight();
        checkOutputDimensions(width, height);
        this.mProgram.setHostValue("pix_stride", Float.valueOf(1.0f / width));
        MutableFrameFormat mutableCopy = format.mutableCopy();
        mutableCopy.setDimensions(width / 4, height);
        Frame newFrame = filterContext.getFrameManager().newFrame(mutableCopy);
        this.mProgram.process(pullInput, newFrame);
        Frame newFrame2 = filterContext.getFrameManager().newFrame(convertInputFormat);
        newFrame2.setDataFromFrame(newFrame);
        newFrame.release();
        pushOutput("image", newFrame2);
        newFrame2.release();
    }
}
