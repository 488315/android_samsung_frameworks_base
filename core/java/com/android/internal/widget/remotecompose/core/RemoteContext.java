package com.android.internal.widget.remotecompose.core;

import com.android.internal.widget.remotecompose.core.operations.FloatExpression;
import com.android.internal.widget.remotecompose.core.operations.ShaderData;
import com.android.internal.widget.remotecompose.core.operations.Utils;

public abstract class RemoteContext {
    public static final int ID_CALENDAR_MONTH = 9;
    public static final int ID_COMPONENT_HEIGHT = 8;
    public static final int ID_COMPONENT_WIDTH = 7;
    public static final int ID_CONTINUOUS_SEC = 1;
    public static final int ID_TIME_IN_HR = 4;
    public static final int ID_TIME_IN_MIN = 3;
    public static final int ID_TIME_IN_SEC = 2;
    public static final int ID_WINDOW_HEIGHT = 6;
    public static final int ID_WINDOW_WIDTH = 5;
    protected CoreDocument mDocument;
    public RemoteComposeState mRemoteComposeState;
    public static final float FLOAT_CONTINUOUS_SEC = Utils.asNan(1);
    public static final float FLOAT_TIME_IN_SEC = Utils.asNan(2);
    public static final float FLOAT_TIME_IN_MIN = Utils.asNan(3);
    public static final float FLOAT_TIME_IN_HR = Utils.asNan(4);
    public static final float FLOAT_CALENDAR_MONTH = Utils.asNan(9);
    public static final float FLOAT_WINDOW_WIDTH = Utils.asNan(5);
    public static final float FLOAT_WINDOW_HEIGHT = Utils.asNan(6);
    public static final float FLOAT_COMPONENT_WIDTH = Utils.asNan(7);
    public static final float FLOAT_COMPONENT_HEIGHT = Utils.asNan(8);
    long mStart = System.nanoTime();
    protected PaintContext mPaintContext = null;
    ContextMode mMode = ContextMode.UNSET;
    boolean mDebug = false;
    private int mTheme = -1;
    public float mWidth = 0.0f;
    public float mHeight = 0.0f;

    public enum ContextMode {
        UNSET,
        DATA,
        PAINT
    }

    public abstract void addClickArea(int i, int i2, float f, float f2, float f3, float f4, int i3);

    public abstract int getColor(int i);

    public abstract float getFloat(int i);

    public abstract ShaderData getShader(int i);

    public abstract String getText(int i);

    public abstract void listensTo(int i, VariableSupport variableSupport);

    public abstract void loadAnimatedFloat(int i, FloatExpression floatExpression);

    public abstract void loadBitmap(int i, int i2, int i3, byte[] bArr);

    public abstract void loadColor(int i, int i2);

    public abstract void loadFloat(int i, float f);

    public abstract void loadPathData(int i, float[] fArr);

    public abstract void loadShader(int i, ShaderData shaderData);

    public abstract void loadText(int i, String str);

    public abstract void loadVariableName(String str, int i, int i2);

    public abstract int updateOps();

    public float getAnimationTime() {
        return (System.nanoTime() - this.mStart) * 1.0E-9f;
    }

    public int getTheme() {
        return this.mTheme;
    }

    public void setTheme(int theme) {
        this.mTheme = theme;
    }

    public ContextMode getMode() {
        return this.mMode;
    }

    public void setMode(ContextMode mode) {
        this.mMode = mode;
    }

    public PaintContext getPaintContext() {
        return this.mPaintContext;
    }

    public void setPaintContext(PaintContext paintContext) {
        this.mPaintContext = paintContext;
    }

    public CoreDocument getDocument() {
        return this.mDocument;
    }

    public boolean isDebug() {
        return this.mDebug;
    }

    public void setDebug(boolean debug) {
        this.mDebug = debug;
    }

    public void setDocument(CoreDocument document) {
        this.mDocument = document;
    }

    public void header(
            int majorVersion,
            int minorVersion,
            int patchVersion,
            int width,
            int height,
            long capabilities) {
        this.mRemoteComposeState.setWindowWidth(width);
        this.mRemoteComposeState.setWindowHeight(height);
        this.mDocument.setVersion(majorVersion, minorVersion, patchVersion);
        this.mDocument.setWidth(width);
        this.mDocument.setHeight(height);
        this.mDocument.setRequiredCapabilities(capabilities);
    }

    public void setRootContentBehavior(int scroll, int alignment, int sizing, int mode) {
        this.mDocument.setRootContentBehavior(scroll, alignment, sizing, mode);
    }

    public void setDocumentContentDescription(int contentDescriptionId) {
        String contentDescription =
                (String) this.mRemoteComposeState.getFromId(contentDescriptionId);
        this.mDocument.setContentDescription(contentDescription);
    }
}
