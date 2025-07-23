package android.filterfw.core;

/* loaded from: classes.dex */
public class SimpleFrameManager extends FrameManager {
    @Override // android.filterfw.core.FrameManager
    public Frame newFrame(FrameFormat frameFormat) {
        return createNewFrame(frameFormat);
    }

    @Override // android.filterfw.core.FrameManager
    public Frame newBoundFrame(FrameFormat frameFormat, int i, long j) {
        if (frameFormat.getTarget() == 3) {
            GLFrame gLFrame = new GLFrame(frameFormat, this, i, j);
            gLFrame.init(getGLEnvironment());
            return gLFrame;
        }
        throw new RuntimeException("Attached frames are not supported for target type: " + FrameFormat.targetToString(frameFormat.getTarget()) + "!");
    }

    private Frame createNewFrame(FrameFormat frameFormat) {
        int target = frameFormat.getTarget();
        if (target == 1) {
            return new SimpleFrame(frameFormat, this);
        }
        if (target == 2) {
            return new NativeFrame(frameFormat, this);
        }
        if (target == 3) {
            GLFrame gLFrame = new GLFrame(frameFormat, this);
            gLFrame.init(getGLEnvironment());
            return gLFrame;
        }
        if (target == 4) {
            return new VertexFrame(frameFormat, this);
        }
        throw new RuntimeException("Unsupported frame target type: " + FrameFormat.targetToString(frameFormat.getTarget()) + "!");
    }

    @Override // android.filterfw.core.FrameManager
    public Frame retainFrame(Frame frame) {
        frame.incRefCount();
        return frame;
    }

    @Override // android.filterfw.core.FrameManager
    public Frame releaseFrame(Frame frame) {
        int decRefCount = frame.decRefCount();
        if (decRefCount == 0 && frame.hasNativeAllocation()) {
            frame.releaseNativeAllocation();
            return null;
        }
        if (decRefCount >= 0) {
            return frame;
        }
        throw new RuntimeException("Frame reference count dropped below 0!");
    }
}
