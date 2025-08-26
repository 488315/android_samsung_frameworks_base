package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.android.internal.vibrator.persistence.XmlConstants;

/* loaded from: classes.dex */
public class RedEyeFilter extends Filter {
    private static final float DEFAULT_RED_INTENSITY = 1.3f;
    private static final float MIN_RADIUS = 10.0f;
    private static final float RADIUS_RATIO = 0.06f;
    private final Canvas mCanvas;

    @GenerateFieldPort(name = "centers")
    private float[] mCenters;
    private int mHeight;
    private final Paint mPaint;
    private Program mProgram;
    private float mRadius;
    private Bitmap mRedEyeBitmap;
    private Frame mRedEyeFrame;
    private final String mRedEyeShader;
    private int mTarget;

    @GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;
    private int mWidth;

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        return frameFormat;
    }

    public RedEyeFilter(String str) {
        super(str);
        this.mTileSize = 640;
        this.mCanvas = new Canvas();
        this.mPaint = new Paint();
        this.mWidth = 0;
        this.mHeight = 0;
        this.mTarget = 0;
        this.mRedEyeShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float intensity;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_1, v_texcoord);\n  if (mask.a > 0.0) {\n    float green_blue = color.g + color.b;\n    float red_intensity = color.r / green_blue;\n    if (red_intensity > intensity) {\n      color.r = 0.5 * green_blue;\n    }\n  }\n  gl_FragColor = color;\n}\n";
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("image", ImageFormat.create(3));
        addOutputBasedOnInput("image", "image");
    }

    public void initProgram(FilterContext filterContext, int i) {
        if (i == 3) {
            ShaderProgram shaderProgram = new ShaderProgram(filterContext, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float intensity;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_1, v_texcoord);\n  if (mask.a > 0.0) {\n    float green_blue = color.g + color.b;\n    float red_intensity = color.r / green_blue;\n    if (red_intensity > intensity) {\n      color.r = 0.5 * green_blue;\n    }\n  }\n  gl_FragColor = color;\n}\n");
            shaderProgram.setMaximumTileSize(this.mTileSize);
            this.mProgram = shaderProgram;
            shaderProgram.setHostValue(XmlConstants.ATTRIBUTE_INTENSITY, Float.valueOf(DEFAULT_RED_INTENSITY));
            this.mTarget = i;
            return;
        }
        throw new RuntimeException("Filter RedEye does not support frames of target " + i + "!");
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame framePullInput = pullInput("image");
        FrameFormat format = framePullInput.getFormat();
        Frame frameNewFrame = filterContext.getFrameManager().newFrame(format);
        if (this.mProgram == null || format.getTarget() != this.mTarget) {
            initProgram(filterContext, format.getTarget());
        }
        if (format.getWidth() != this.mWidth || format.getHeight() != this.mHeight) {
            this.mWidth = format.getWidth();
            this.mHeight = format.getHeight();
        }
        createRedEyeFrame(filterContext);
        this.mProgram.process(new Frame[]{framePullInput, this.mRedEyeFrame}, frameNewFrame);
        pushOutput("image", frameNewFrame);
        frameNewFrame.release();
        this.mRedEyeFrame.release();
        this.mRedEyeFrame = null;
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(String str, FilterContext filterContext) {
        if (this.mProgram != null) {
            updateProgramParams();
        }
    }

    private void createRedEyeFrame(FilterContext filterContext) {
        int i = this.mWidth / 2;
        int i2 = this.mHeight / 2;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        this.mCanvas.setBitmap(bitmapCreateBitmap);
        this.mPaint.setColor(-1);
        this.mRadius = Math.max(MIN_RADIUS, Math.min(i, i2) * RADIUS_RATIO);
        int i3 = 0;
        while (true) {
            float[] fArr = this.mCenters;
            if (i3 < fArr.length) {
                this.mCanvas.drawCircle(fArr[i3] * i, fArr[i3 + 1] * i2, this.mRadius, this.mPaint);
                i3 += 2;
            } else {
                Frame frameNewFrame = filterContext.getFrameManager().newFrame(ImageFormat.create(i, i2, 3, 3));
                this.mRedEyeFrame = frameNewFrame;
                frameNewFrame.setBitmap(bitmapCreateBitmap);
                bitmapCreateBitmap.recycle();
                return;
            }
        }
    }

    private void updateProgramParams() {
        if (this.mCenters.length % 2 == 1) {
            throw new RuntimeException("The size of center array must be even.");
        }
    }
}
