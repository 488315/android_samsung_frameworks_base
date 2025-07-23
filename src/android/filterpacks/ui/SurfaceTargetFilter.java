package android.filterpacks.ui;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.GLEnvironment;
import android.filterfw.core.GLFrame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.util.Log;
import android.view.Surface;

/* loaded from: classes.dex */
public class SurfaceTargetFilter extends Filter {
    private static final String TAG = "SurfaceRenderFilter";
    private final int RENDERMODE_FILL_CROP;
    private final int RENDERMODE_FIT;
    private final int RENDERMODE_STRETCH;
    private float mAspectRatio;
    private GLEnvironment mGlEnv;
    private boolean mLogVerbose;
    private ShaderProgram mProgram;
    private int mRenderMode;

    @GenerateFieldPort(hasDefault = true, name = "renderMode")
    private String mRenderModeString;
    private GLFrame mScreen;

    @GenerateFieldPort(name = "oheight")
    private int mScreenHeight;

    @GenerateFieldPort(name = "owidth")
    private int mScreenWidth;

    @GenerateFinalPort(name = "surface")
    private Surface mSurface;
    private int mSurfaceId;

    public SurfaceTargetFilter(String str) {
        super(str);
        this.RENDERMODE_STRETCH = 0;
        this.RENDERMODE_FIT = 1;
        this.RENDERMODE_FILL_CROP = 2;
        this.mRenderMode = 1;
        this.mAspectRatio = 1.0f;
        this.mSurfaceId = -1;
        this.mLogVerbose = Log.isLoggable(TAG, 2);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        if (this.mSurface == null) {
            throw new RuntimeException("NULL Surface passed to SurfaceTargetFilter");
        }
        addMaskedInputPort("frame", ImageFormat.create(3));
    }

    public void updateRenderMode() {
        String str = this.mRenderModeString;
        if (str != null) {
            if (str.equals("stretch")) {
                this.mRenderMode = 0;
            } else if (this.mRenderModeString.equals("fit")) {
                this.mRenderMode = 1;
            } else if (this.mRenderModeString.equals("fill_crop")) {
                this.mRenderMode = 2;
            } else {
                throw new RuntimeException("Unknown render mode '" + this.mRenderModeString + "'!");
            }
        }
        updateTargetRect();
    }

    @Override // android.filterfw.core.Filter
    public void prepare(FilterContext filterContext) {
        this.mGlEnv = filterContext.getGLEnvironment();
        ShaderProgram createIdentity = ShaderProgram.createIdentity(filterContext);
        this.mProgram = createIdentity;
        createIdentity.setSourceRect(0.0f, 1.0f, 1.0f, -1.0f);
        this.mProgram.setClearsOutput(true);
        this.mProgram.setClearColor(0.0f, 0.0f, 0.0f);
        this.mScreen = (GLFrame) filterContext.getFrameManager().newBoundFrame(ImageFormat.create(this.mScreenWidth, this.mScreenHeight, 3, 3), 101, 0L);
        updateRenderMode();
    }

    @Override // android.filterfw.core.Filter
    public void open(FilterContext filterContext) {
        registerSurface();
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        boolean z;
        if (this.mLogVerbose) {
            Log.v(TAG, "Starting frame processing");
        }
        Frame pullInput = pullInput("frame");
        float width = pullInput.getFormat().getWidth() / pullInput.getFormat().getHeight();
        if (width != this.mAspectRatio) {
            if (this.mLogVerbose) {
                Log.v(TAG, "New aspect ratio: " + width + ", previously: " + this.mAspectRatio);
            }
            this.mAspectRatio = width;
            updateTargetRect();
        }
        if (this.mLogVerbose) {
            Log.v(TAG, "Got input format: " + pullInput.getFormat());
        }
        if (pullInput.getFormat().getTarget() != 3) {
            pullInput = filterContext.getFrameManager().duplicateFrameToTarget(pullInput, 3);
            z = true;
        } else {
            z = false;
        }
        this.mGlEnv.activateSurfaceWithId(this.mSurfaceId);
        this.mProgram.process(pullInput, this.mScreen);
        this.mGlEnv.swapBuffers();
        if (z) {
            pullInput.release();
        }
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(String str, FilterContext filterContext) {
        this.mScreen.setViewport(0, 0, this.mScreenWidth, this.mScreenHeight);
        updateTargetRect();
    }

    @Override // android.filterfw.core.Filter
    public void close(FilterContext filterContext) {
        unregisterSurface();
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(FilterContext filterContext) {
        GLFrame gLFrame = this.mScreen;
        if (gLFrame != null) {
            gLFrame.release();
        }
    }

    private void updateTargetRect() {
        int i;
        ShaderProgram shaderProgram;
        int i2 = this.mScreenWidth;
        if (i2 <= 0 || (i = this.mScreenHeight) <= 0 || (shaderProgram = this.mProgram) == null) {
            return;
        }
        float f = (i2 / i) / this.mAspectRatio;
        int i3 = this.mRenderMode;
        if (i3 == 0) {
            shaderProgram.setTargetRect(0.0f, 0.0f, 1.0f, 1.0f);
            return;
        }
        if (i3 == 1) {
            if (f > 1.0f) {
                shaderProgram.setTargetRect(0.5f - (0.5f / f), 0.0f, 1.0f / f, 1.0f);
                return;
            } else {
                shaderProgram.setTargetRect(0.0f, 0.5f - (f * 0.5f), 1.0f, f);
                return;
            }
        }
        if (i3 != 2) {
            return;
        }
        if (f > 1.0f) {
            shaderProgram.setTargetRect(0.0f, 0.5f - (f * 0.5f), 1.0f, f);
        } else {
            shaderProgram.setTargetRect(0.5f - (0.5f / f), 0.0f, 1.0f / f, 1.0f);
        }
    }

    private void registerSurface() {
        int registerSurface = this.mGlEnv.registerSurface(this.mSurface);
        this.mSurfaceId = registerSurface;
        if (registerSurface >= 0) {
            return;
        }
        throw new RuntimeException("Could not register Surface: " + this.mSurface);
    }

    private void unregisterSurface() {
        int i = this.mSurfaceId;
        if (i > 0) {
            this.mGlEnv.unregisterSurfaceId(i);
        }
    }
}
