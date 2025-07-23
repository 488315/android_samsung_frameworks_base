package com.android.internal.widget.remotecompose.core;

import com.android.internal.widget.remotecompose.core.operations.FloatExpression;
import com.android.internal.widget.remotecompose.core.operations.ShaderData;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.utilities.ArrayAccess;
import com.android.internal.widget.remotecompose.core.operations.utilities.CollectionsAccess;
import com.android.internal.widget.remotecompose.core.operations.utilities.DataMap;
import com.android.internal.widget.remotecompose.core.operations.utilities.IntMap;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/* loaded from: classes6.dex */
public abstract class RemoteContext {
    public static final int ID_ACCELERATION_X = 17;
    public static final int ID_ACCELERATION_Y = 18;
    public static final int ID_ACCELERATION_Z = 19;
    public static final int ID_ANIMATION_DELTA_TIME = 31;
    public static final int ID_ANIMATION_TIME = 30;
    public static final int ID_API_LEVEL = 28;
    public static final int ID_CALENDAR_MONTH = 9;
    public static final int ID_COMPONENT_HEIGHT = 8;
    public static final int ID_COMPONENT_WIDTH = 7;
    public static final int ID_CONTINUOUS_SEC = 1;
    public static final int ID_DAY_OF_MONTH = 12;
    public static final int ID_DENSITY = 27;
    public static final int ID_EPOCH_SECOND = 32;
    public static final int ID_FONT_SIZE = 33;
    public static final int ID_GYRO_ROT_X = 20;
    public static final int ID_GYRO_ROT_Y = 21;
    public static final int ID_GYRO_ROT_Z = 22;
    public static final int ID_LIGHT = 26;
    public static final int ID_MAGNETIC_X = 23;
    public static final int ID_MAGNETIC_Y = 24;
    public static final int ID_MAGNETIC_Z = 25;
    public static final int ID_OFFSET_TO_UTC = 10;
    public static final int ID_TIME_IN_HR = 4;
    public static final int ID_TIME_IN_MIN = 3;
    public static final int ID_TIME_IN_SEC = 2;
    public static final int ID_TOUCH_EVENT_TIME = 29;
    public static final int ID_TOUCH_POS_X = 13;
    public static final int ID_TOUCH_POS_Y = 14;
    public static final int ID_TOUCH_VEL_X = 15;
    public static final int ID_TOUCH_VEL_Y = 16;
    public static final int ID_WEEK_DAY = 11;
    public static final int ID_WINDOW_HEIGHT = 6;
    public static final int ID_WINDOW_WIDTH = 5;
    public static final long INT_EPOCH_SECOND = 4294967328L;
    private static final int MAX_OP_COUNT = 20000;
    private float mAnimationTime;
    public Component mLastComponent;
    private int mOpCount;
    public static final float FLOAT_DENSITY = Utils.asNan(27);
    public static final float FLOAT_CONTINUOUS_SEC = Utils.asNan(1);
    public static final float FLOAT_TIME_IN_SEC = Utils.asNan(2);
    public static final float FLOAT_TIME_IN_MIN = Utils.asNan(3);
    public static final float FLOAT_TIME_IN_HR = Utils.asNan(4);
    public static final float FLOAT_CALENDAR_MONTH = Utils.asNan(9);
    public static final float FLOAT_WEEK_DAY = Utils.asNan(11);
    public static final float FLOAT_DAY_OF_MONTH = Utils.asNan(12);
    public static final float FLOAT_WINDOW_WIDTH = Utils.asNan(5);
    public static final float FLOAT_WINDOW_HEIGHT = Utils.asNan(6);
    public static final float FLOAT_COMPONENT_WIDTH = Utils.asNan(7);
    public static final float FLOAT_COMPONENT_HEIGHT = Utils.asNan(8);
    public static final float FLOAT_OFFSET_TO_UTC = Utils.asNan(10);
    public static final float FLOAT_TOUCH_POS_X = Utils.asNan(13);
    public static final float FLOAT_TOUCH_POS_Y = Utils.asNan(14);
    public static final float FLOAT_TOUCH_VEL_X = Utils.asNan(15);
    public static final float FLOAT_TOUCH_VEL_Y = Utils.asNan(16);
    public static final float FLOAT_TOUCH_EVENT_TIME = Utils.asNan(29);
    public static final float FLOAT_ANIMATION_TIME = Utils.asNan(30);
    public static final float FLOAT_ANIMATION_DELTA_TIME = Utils.asNan(31);
    public static final float FLOAT_ACCELERATION_X = Utils.asNan(17);
    public static final float FLOAT_ACCELERATION_Y = Utils.asNan(18);
    public static final float FLOAT_ACCELERATION_Z = Utils.asNan(19);
    public static final float FLOAT_GYRO_ROT_X = Utils.asNan(20);
    public static final float FLOAT_GYRO_ROT_Y = Utils.asNan(21);
    public static final float FLOAT_GYRO_ROT_Z = Utils.asNan(22);
    public static final float FLOAT_MAGNETIC_X = Utils.asNan(23);
    public static final float FLOAT_MAGNETIC_Y = Utils.asNan(24);
    public static final float FLOAT_MAGNETIC_Z = Utils.asNan(25);
    public static final float FLOAT_LIGHT = Utils.asNan(26);
    public static final float FLOAT_API_LEVEL = Utils.asNan(28);
    public static final float FLOAT_FONT_SIZE = Utils.asNan(33);
    protected CoreDocument mDocument = new CoreDocument();
    public RemoteComposeState mRemoteComposeState = new RemoteComposeState();
    private long mDocLoadTime = System.currentTimeMillis();
    protected PaintContext mPaintContext = null;
    protected float mDensity = Float.NaN;
    ContextMode mMode = ContextMode.UNSET;
    int mDebug = 0;
    private int mTheme = -1;
    public float mWidth = 0.0f;
    public float mHeight = 0.0f;
    private boolean mAnimate = true;
    public long currentTime = 0;
    private boolean mUseChoreographer = true;

    public enum ContextMode {
        UNSET,
        DATA,
        PAINT
    }

    public abstract void addClickArea(int i, int i2, float f, float f2, float f3, float f4, int i3);

    public abstract void addCollection(int i, ArrayAccess arrayAccess);

    public void addTouchListener(TouchListener touchListener) {
    }

    public abstract void clearNamedDataOverride(String str);

    public abstract void clearNamedFloatOverride(String str);

    public abstract void clearNamedIntegerOverride(String str);

    public abstract void clearNamedStringOverride(String str);

    public abstract int getColor(int i);

    public abstract DataMap getDataMap(int i);

    public abstract float getFloat(int i);

    public abstract int getInteger(int i);

    public abstract long getLong(int i);

    public abstract Object getObject(int i);

    public abstract float[] getPathData(int i);

    public abstract ShaderData getShader(int i);

    public abstract String getText(int i);

    public abstract void hapticEffect(int i);

    public abstract void listensTo(int i, VariableSupport variableSupport);

    public abstract void loadAnimatedFloat(int i, FloatExpression floatExpression);

    public abstract void loadBitmap(int i, short s, short s2, int i2, int i3, byte[] bArr);

    public abstract void loadColor(int i, int i2);

    public abstract void loadFloat(int i, float f);

    public abstract void loadInteger(int i, int i2);

    public abstract void loadPathData(int i, float[] fArr);

    public abstract void loadShader(int i, ShaderData shaderData);

    public abstract void loadText(int i, String str);

    public abstract void loadVariableName(String str, int i, int i2);

    public abstract void overrideFloat(int i, float f);

    public abstract void overrideInteger(int i, int i2);

    public abstract void overrideText(int i, int i2);

    public abstract void putDataMap(int i, DataMap dataMap);

    public abstract void putObject(int i, Object obj);

    public abstract void runAction(int i, String str);

    public abstract void runNamedAction(int i, Object obj);

    public abstract void setNamedColorOverride(String str, int i);

    public abstract void setNamedDataOverride(String str, Object obj);

    public abstract void setNamedFloatOverride(String str, float f);

    public abstract void setNamedIntegerOverride(String str, int i);

    public abstract void setNamedLong(String str, long j);

    public abstract void setNamedStringOverride(String str, String str2);

    public abstract int updateOps();

    public boolean supportsVersion(int i, int i2, int i3) {
        return this.mDocument.mVersion.supportsVersion(i, i2, i3);
    }

    public float getDensity() {
        return this.mDensity;
    }

    public void setDensity(float f) {
        if (Float.isNaN(f) || f <= 0.0f) {
            return;
        }
        this.mDensity = f;
    }

    public long getDocLoadTime() {
        return this.mDocLoadTime;
    }

    public void setDocLoadTime() {
        this.mDocLoadTime = System.currentTimeMillis();
    }

    public boolean isAnimationEnabled() {
        return this.mAnimate;
    }

    public void setAnimationEnabled(boolean z) {
        this.mAnimate = z;
    }

    public CollectionsAccess getCollectionsAccess() {
        return this.mRemoteComposeState;
    }

    public void setAnimationTime(float f) {
        this.mAnimationTime = f;
    }

    public float getAnimationTime() {
        return this.mAnimationTime;
    }

    public void needsRepaint() {
        PaintContext paintContext = this.mPaintContext;
        if (paintContext != null) {
            paintContext.needsRepaint();
        }
    }

    public boolean useChoreographer() {
        return this.mUseChoreographer;
    }

    public void setUseChoreographer(boolean z) {
        this.mUseChoreographer = z;
    }

    public int getTheme() {
        return this.mTheme;
    }

    public void setTheme(int i) {
        this.mTheme = i;
    }

    public ContextMode getMode() {
        return this.mMode;
    }

    public void setMode(ContextMode contextMode) {
        this.mMode = contextMode;
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
        return this.mDebug == 1;
    }

    public boolean isVisualDebug() {
        return this.mDebug == 2;
    }

    public void setDebug(int i) {
        this.mDebug = i;
    }

    public void setDocument(CoreDocument coreDocument) {
        this.mDocument = coreDocument;
    }

    public void header(int i, int i2, int i3, int i4, int i5, long j, IntMap<Object> intMap) {
        this.mRemoteComposeState.setWindowWidth(i4);
        this.mRemoteComposeState.setWindowHeight(i5);
        this.mDocument.setVersion(i, i2, i3);
        this.mDocument.setWidth(i4);
        this.mDocument.setHeight(i5);
        this.mDocument.setRequiredCapabilities(j);
        this.mDocument.setProperties(intMap);
    }

    public void setRootContentBehavior(int i, int i2, int i3, int i4) {
        this.mDocument.setRootContentBehavior(i, i2, i3, i4);
    }

    public void setDocumentContentDescription(int i) {
        this.mDocument.setContentDescription((String) this.mRemoteComposeState.getFromId(i));
    }

    public static boolean isTime(float f) {
        int idFromNan = Utils.idFromNan(f);
        return idFromNan >= 1 && idFromNan <= 12;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [java.time.ZonedDateTime] */
    public static float getTime(float f) {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        int idFromNan = Utils.idFromNan(f);
        int value = now.getMonth().getValue();
        int hour = now.getHour();
        int minute = now.getMinute();
        int i = (hour * 60) + minute;
        float second = (minute * 60) + now.getSecond();
        float nano = (now.getNano() * 1.0E-9f) + second;
        int value2 = now.getDayOfWeek().getValue();
        ZoneOffset offset = now.atZone(ZoneId.systemDefault()).toOffsetDateTime().getOffset();
        if (idFromNan == 1) {
            return nano;
        }
        if (idFromNan == 2) {
            return second;
        }
        if (idFromNan == 3) {
            return i;
        }
        if (idFromNan == 4) {
            return hour;
        }
        switch (idFromNan) {
            case 9:
            case 12:
                return value;
            case 10:
                return offset.getTotalSeconds();
            case 11:
                return value2;
            default:
                return f;
        }
    }

    public void incrementOpCount() {
        int i = this.mOpCount + 1;
        this.mOpCount = i;
        if (i > 20000) {
            throw new RuntimeException("Too many operations executed");
        }
    }

    public int getLastOpCount() {
        int i = this.mOpCount;
        this.mOpCount = 0;
        return i;
    }

    public void clearLastOpCount() {
        this.mOpCount = 0;
    }
}
