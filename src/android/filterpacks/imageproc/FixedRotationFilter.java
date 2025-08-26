package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;
import com.samsung.android.wallpaperbackup.GenerateXML;

/* loaded from: classes.dex */
public class FixedRotationFilter extends Filter {
    private ShaderProgram mProgram;

    @GenerateFieldPort(hasDefault = true, name = GenerateXML.ROTATION)
    private int mRotation;

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        return frameFormat;
    }

    public FixedRotationFilter(String str) {
        super(str);
        this.mRotation = 0;
        this.mProgram = null;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("image", ImageFormat.create(3, 3));
        addOutputBasedOnInput("image", "image");
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Quad quad;
        Quad quad2;
        Frame framePullInput = pullInput("image");
        if (this.mRotation == 0) {
            pushOutput("image", framePullInput);
            return;
        }
        FrameFormat format = framePullInput.getFormat();
        if (this.mProgram == null) {
            this.mProgram = ShaderProgram.createIdentity(filterContext);
        }
        MutableFrameFormat mutableFrameFormatMutableCopy = format.mutableCopy();
        int width = format.getWidth();
        int height = format.getHeight();
        Point point = new Point(0.0f, 0.0f);
        Point point2 = new Point(1.0f, 0.0f);
        Point point3 = new Point(0.0f, 1.0f);
        Point point4 = new Point(1.0f, 1.0f);
        int iRound = Math.round(this.mRotation / 90.0f) % 4;
        if (iRound == 1) {
            quad = new Quad(point3, point, point4, point2);
            mutableFrameFormatMutableCopy.setDimensions(height, width);
        } else {
            if (iRound == 2) {
                quad2 = new Quad(point4, point3, point2, point);
            } else if (iRound == 3) {
                quad = new Quad(point2, point4, point, point3);
                mutableFrameFormatMutableCopy.setDimensions(height, width);
            } else {
                quad2 = new Quad(point, point2, point3, point4);
            }
            Frame frameNewFrame = filterContext.getFrameManager().newFrame(mutableFrameFormatMutableCopy);
            this.mProgram.setSourceRegion(quad2);
            this.mProgram.process(framePullInput, frameNewFrame);
            pushOutput("image", frameNewFrame);
            frameNewFrame.release();
        }
        quad2 = quad;
        Frame frameNewFrame2 = filterContext.getFrameManager().newFrame(mutableFrameFormatMutableCopy);
        this.mProgram.setSourceRegion(quad2);
        this.mProgram.process(framePullInput, frameNewFrame2);
        pushOutput("image", frameNewFrame2);
        frameNewFrame2.release();
    }
}
