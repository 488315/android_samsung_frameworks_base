package com.android.internal.widget.remotecompose.core;

import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.Theme;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes5.dex */
public class CoreDocument {
    String mContentDescription;
    ArrayList<Operation> mOperations;
    RemoteComposeState mRemoteComposeState = new RemoteComposeState();
    TimeVariables mTimeVariables = new TimeVariables();
    Version mVersion = new Version(0, 1, 0);
    long mRequiredCapabilities = 0;
    int mWidth = 0;
    int mHeight = 0;
    int mContentScroll = 0;
    int mContentSizing = 0;
    int mContentMode = 0;
    int mContentAlignment = 34;
    RemoteComposeBuffer mBuffer = new RemoteComposeBuffer(this.mRemoteComposeState);
    HashSet<ClickCallbacks> mClickListeners = new HashSet<>();
    HashSet<ClickAreaRepresentation> mClickAreas = new HashSet<>();
    private final float[] mScaleOutput = new float[2];
    private final float[] mTranslateOutput = new float[2];
    private int mRepaintNext = -1;

    public interface ClickCallbacks {
        void click(int i, String str);
    }

    public String getContentDescription() {
        return this.mContentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.mContentDescription = contentDescription;
    }

    public long getRequiredCapabilities() {
        return this.mRequiredCapabilities;
    }

    public void setRequiredCapabilities(long requiredCapabilities) {
        this.mRequiredCapabilities = requiredCapabilities;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int width) {
        this.mWidth = width;
        this.mRemoteComposeState.setWindowWidth(width);
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int height) {
        this.mHeight = height;
        this.mRemoteComposeState.setWindowHeight(height);
    }

    public RemoteComposeBuffer getBuffer() {
        return this.mBuffer;
    }

    public void setBuffer(RemoteComposeBuffer buffer) {
        this.mBuffer = buffer;
    }

    public RemoteComposeState getRemoteComposeState() {
        return this.mRemoteComposeState;
    }

    public void setRemoteComposeState(RemoteComposeState remoteComposeState) {
        this.mRemoteComposeState = remoteComposeState;
    }

    public int getContentScroll() {
        return this.mContentScroll;
    }

    public int getContentSizing() {
        return this.mContentSizing;
    }

    public int getContentMode() {
        return this.mContentMode;
    }

    public void setRootContentBehavior(int scroll, int alignment, int sizing, int mode) {
        this.mContentScroll = scroll;
        this.mContentAlignment = alignment;
        this.mContentSizing = sizing;
        this.mContentMode = mode;
    }

    public void computeScale(float w, float h, float[] scaleOutput) {
        float contentScaleX = 1.0f;
        float contentScaleY = 1.0f;
        if (this.mContentSizing == 2) {
            switch (this.mContentMode) {
                case 1:
                    float scaleX = w / this.mWidth;
                    float scaleY = h / this.mHeight;
                    float scale = Math.min(1.0f, Math.min(scaleX, scaleY));
                    contentScaleX = scale;
                    contentScaleY = scale;
                    break;
                case 2:
                    float scale2 = w / this.mWidth;
                    contentScaleX = scale2;
                    contentScaleY = scale2;
                    break;
                case 3:
                    float scale3 = h / this.mHeight;
                    contentScaleX = scale3;
                    contentScaleY = scale3;
                    break;
                case 4:
                    float scaleX2 = w / this.mWidth;
                    float scaleY2 = h / this.mHeight;
                    float scale4 = Math.min(scaleX2, scaleY2);
                    contentScaleX = scale4;
                    contentScaleY = scale4;
                    break;
                case 5:
                    float scaleX3 = w / this.mWidth;
                    float scaleY3 = h / this.mHeight;
                    float scale5 = Math.max(scaleX3, scaleY3);
                    contentScaleX = scale5;
                    contentScaleY = scale5;
                    break;
                case 6:
                    float scaleX4 = w / this.mWidth;
                    float scaleY4 = h / this.mHeight;
                    contentScaleX = scaleX4;
                    contentScaleY = scaleY4;
                    break;
            }
        }
        scaleOutput[0] = contentScaleX;
        scaleOutput[1] = contentScaleY;
    }

    private void computeTranslate(float w, float h, float contentScaleX, float contentScaleY, float[] translateOutput) {
        int horizontalContentAlignment = this.mContentAlignment & 240;
        int verticalContentAlignment = this.mContentAlignment & 15;
        float translateX = 0.0f;
        float translateY = 0.0f;
        float contentWidth = this.mWidth * contentScaleX;
        float contentHeight = this.mHeight * contentScaleY;
        switch (horizontalContentAlignment) {
            case 32:
                translateX = (w - contentWidth) / 2.0f;
                break;
            case 64:
                translateX = w - contentWidth;
                break;
        }
        switch (verticalContentAlignment) {
            case 2:
                translateY = (h - contentHeight) / 2.0f;
                break;
            case 4:
                translateY = h - contentHeight;
                break;
        }
        translateOutput[0] = translateX;
        translateOutput[1] = translateY;
    }

    public Set<ClickAreaRepresentation> getClickAreas() {
        return this.mClickAreas;
    }

    static class Version {
        public final int major;
        public final int minor;
        public final int patchLevel;

        Version(int major, int minor, int patchLevel) {
            this.major = major;
            this.minor = minor;
            this.patchLevel = patchLevel;
        }
    }

    public static class ClickAreaRepresentation {
        float mBottom;
        String mContentDescription;
        int mId;
        float mLeft;
        String mMetadata;
        float mRight;
        float mTop;

        public ClickAreaRepresentation(int id, String contentDescription, float left, float top, float right, float bottom, String metadata) {
            this.mId = id;
            this.mContentDescription = contentDescription;
            this.mLeft = left;
            this.mTop = top;
            this.mRight = right;
            this.mBottom = bottom;
            this.mMetadata = metadata;
        }

        public boolean contains(float x, float y) {
            return x >= this.mLeft && x < this.mRight && y >= this.mTop && y < this.mBottom;
        }

        public float getLeft() {
            return this.mLeft;
        }

        public float getTop() {
            return this.mTop;
        }

        public float width() {
            return Math.max(0.0f, this.mRight - this.mLeft);
        }

        public float height() {
            return Math.max(0.0f, this.mBottom - this.mTop);
        }

        public int getId() {
            return this.mId;
        }

        public String getContentDescription() {
            return this.mContentDescription;
        }

        public String getMetadata() {
            return this.mMetadata;
        }
    }

    public void initFromBuffer(RemoteComposeBuffer buffer) {
        this.mOperations = new ArrayList<>();
        buffer.inflateFromBuffer(this.mOperations);
        this.mBuffer = buffer;
    }

    public void initializeContext(RemoteContext context) {
        this.mRemoteComposeState.reset();
        this.mClickAreas.clear();
        this.mRemoteComposeState.setNextId(42);
        context.mDocument = this;
        context.mRemoteComposeState = this.mRemoteComposeState;
        context.mMode = RemoteContext.ContextMode.DATA;
        this.mTimeVariables.updateTime(context);
        Iterator<Operation> it = this.mOperations.iterator();
        while (it.hasNext()) {
            Operation op = it.next();
            if (op instanceof VariableSupport) {
                ((VariableSupport) op).updateVariables(context);
                ((VariableSupport) op).registerListening(context);
            }
            op.apply(context);
        }
        context.mMode = RemoteContext.ContextMode.UNSET;
    }

    public boolean canBeDisplayed(int majorVersion, int minorVersion, long capabilities) {
        return this.mVersion.major <= majorVersion && this.mVersion.minor <= minorVersion;
    }

    void setVersion(int majorVersion, int minorVersion, int patch) {
        this.mVersion = new Version(majorVersion, minorVersion, patch);
    }

    public void addClickArea(int id, String contentDescription, float left, float top, float right, float bottom, String metadata) {
        this.mClickAreas.add(new ClickAreaRepresentation(id, contentDescription, left, top, right, bottom, metadata));
    }

    public void addClickListener(ClickCallbacks callback) {
        this.mClickListeners.add(callback);
    }

    public void onClick(float x, float y) {
        Iterator<ClickAreaRepresentation> it = this.mClickAreas.iterator();
        while (it.hasNext()) {
            ClickAreaRepresentation clickArea = it.next();
            if (clickArea.contains(x, y)) {
                warnClickListeners(clickArea);
            }
        }
    }

    public void performClick(int id) {
        Iterator<ClickAreaRepresentation> it = this.mClickAreas.iterator();
        while (it.hasNext()) {
            ClickAreaRepresentation clickArea = it.next();
            if (clickArea.mId == id) {
                warnClickListeners(clickArea);
            }
        }
    }

    private void warnClickListeners(ClickAreaRepresentation clickArea) {
        Iterator<ClickCallbacks> it = this.mClickListeners.iterator();
        while (it.hasNext()) {
            ClickCallbacks listener = it.next();
            listener.click(clickArea.mId, clickArea.mMetadata);
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        Iterator<Operation> it = this.mOperations.iterator();
        while (it.hasNext()) {
            Operation op = it.next();
            builder.append(op.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    public int needsRepaint() {
        return this.mRepaintNext;
    }

    public void paint(RemoteContext context, int theme) {
        context.mMode = RemoteContext.ContextMode.PAINT;
        context.setTheme(-1);
        context.mRemoteComposeState = this.mRemoteComposeState;
        if (this.mContentSizing == 2) {
            computeScale(context.mWidth, context.mHeight, this.mScaleOutput);
            computeTranslate(context.mWidth, context.mHeight, this.mScaleOutput[0], this.mScaleOutput[1], this.mTranslateOutput);
            context.mPaintContext.translate(this.mTranslateOutput[0], this.mTranslateOutput[1]);
            context.mPaintContext.scale(this.mScaleOutput[0], this.mScaleOutput[1]);
        }
        this.mTimeVariables.updateTime(context);
        context.loadFloat(5, getWidth());
        context.loadFloat(6, getHeight());
        this.mRepaintNext = context.updateOps();
        Iterator<Operation> it = this.mOperations.iterator();
        while (it.hasNext()) {
            Operation op = it.next();
            boolean apply = true;
            if (theme != -1) {
                apply = (op instanceof Theme) || context.getTheme() == theme || context.getTheme() == -1;
            }
            if (apply) {
                op.apply(context);
            }
        }
        context.mMode = RemoteContext.ContextMode.UNSET;
    }
}
