package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.format.ImageFormat;

/* loaded from: classes.dex */
public class GLTextureSource extends Filter {
    private Frame mFrame;

    @GenerateFieldPort(name = "height")
    private int mHeight;

    @GenerateFieldPort(hasDefault = true, name = "repeatFrame")
    private boolean mRepeatFrame;

    @GenerateFieldPort(name = "texId")
    private int mTexId;

    @GenerateFieldPort(hasDefault = true, name = "timestamp")
    private long mTimestamp;

    @GenerateFieldPort(name = "width")
    private int mWidth;

    public GLTextureSource(String str) {
        super(str);
        this.mRepeatFrame = false;
        this.mTimestamp = -1L;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addOutputPort("frame", ImageFormat.create(3, 3));
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(String str, FilterContext filterContext) {
        Frame frame = this.mFrame;
        if (frame != null) {
            frame.release();
            this.mFrame = null;
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        if (this.mFrame == null) {
            Frame frameNewBoundFrame = filterContext.getFrameManager().newBoundFrame(ImageFormat.create(this.mWidth, this.mHeight, 3, 3), 100, this.mTexId);
            this.mFrame = frameNewBoundFrame;
            frameNewBoundFrame.setTimestamp(this.mTimestamp);
        }
        pushOutput("frame", this.mFrame);
        if (this.mRepeatFrame) {
            return;
        }
        closeOutputPort("frame");
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(FilterContext filterContext) {
        Frame frame = this.mFrame;
        if (frame != null) {
            frame.release();
        }
    }
}
