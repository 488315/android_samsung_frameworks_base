package android.app;

import android.app.admin.DevicePolicyResources;
import android.graphics.Rect;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.input.KeyboardLayout;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.WireTypeMismatchException;
import android.view.Surface;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.io.IOException;
import java.util.Objects;

/* loaded from: classes.dex */
public class WindowConfiguration implements Parcelable, Comparable<WindowConfiguration> {
    public static final int ACTIVITY_TYPE_ASSISTANT = 4;
    public static final int ACTIVITY_TYPE_DREAM = 5;
    public static final int ACTIVITY_TYPE_HOME = 2;
    public static final int ACTIVITY_TYPE_RECENTS = 3;
    public static final int ACTIVITY_TYPE_STANDARD = 1;
    public static final int ACTIVITY_TYPE_UNDEFINED = 0;
    private static final int ALWAYS_ON_TOP_OFF = 2;
    private static final int ALWAYS_ON_TOP_ON = 1;
    private static final int ALWAYS_ON_TOP_UNDEFINED = 0;
    public static final Parcelable.Creator<WindowConfiguration> CREATOR = new Parcelable.Creator<WindowConfiguration>() { // from class: android.app.WindowConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowConfiguration createFromParcel(Parcel in) {
            return new WindowConfiguration(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowConfiguration[] newArray(int size) {
            return new WindowConfiguration[size];
        }
    };
    public static final int DEX_TASK_DOCKING_LEFT = 1;
    public static final int DEX_TASK_DOCKING_NONE = 0;
    public static final int DEX_TASK_DOCKING_RIGHT = 2;
    public static final int DEX_TASK_DOCKING_UNDEFINED = -1;
    public static final int EMBED_ACTIVITY_MODE_BOTTOM = 5;
    public static final int EMBED_ACTIVITY_MODE_FULL = 1;
    public static final int EMBED_ACTIVITY_MODE_LEFT = 2;
    public static final int EMBED_ACTIVITY_MODE_RIGHT = 3;
    public static final int EMBED_ACTIVITY_MODE_TOP = 4;
    public static final int EMBED_ACTIVITY_MODE_UNDEFINED = 0;
    public static final int FLEX_PANEL_MODE_OFF = 2;
    public static final int FLEX_PANEL_MODE_ON = 1;
    public static final int FLEX_PANEL_MODE_UNDEFINED = 0;
    public static final int FREEFORM_TASK_PINNING_DISABLE = 3;
    public static final int FREEFORM_TASK_PINNING_PINNED = 2;
    public static final int FREEFORM_TASK_PINNING_UNDEFINED = 0;
    public static final int FREEFORM_TASK_PINNING_UNPINNED = 1;
    public static final int FREEFORM_TRANSLUCENT_OFF = 2;
    public static final int FREEFORM_TRANSLUCENT_ON = 1;
    private static final int FREEFORM_TRANSLUCENT_UNDEFINED = 0;
    public static final int POP_OVER_OFF = 2;
    public static final int POP_OVER_ON = 1;
    public static final int POP_OVER_ON_WITHOUT_OUTLINE_EFFECT = 3;
    private static final int POP_OVER_UNDEFINED = 0;
    public static final int ROTATION_UNDEFINED = -1;
    static final int STAGE_CONFIG_POSITION_MASK = 120;
    static final int STAGE_CONFIG_TYPE_MASK = 7;
    public static final int STAGE_POSITION_BOTTOM = 64;
    public static final int STAGE_POSITION_LEFT = 8;
    public static final int STAGE_POSITION_RIGHT = 32;
    public static final int STAGE_POSITION_TOP = 16;
    public static final int STAGE_TYPE_CELL = 4;
    public static final int STAGE_TYPE_MAIN = 1;
    public static final int STAGE_TYPE_SIDE = 2;
    public static final int STAGE_UNDEFINED = 0;
    public static final int WINDOWING_MODE_FREEFORM = 5;
    public static final int WINDOWING_MODE_FULLSCREEN = 1;
    public static final int WINDOWING_MODE_MULTI_WINDOW = 6;
    public static final int WINDOWING_MODE_PINNED = 2;
    public static final int WINDOWING_MODE_UNDEFINED = 0;
    public static final int WINDOW_CONFIG_ACTIVITY_TYPE = 16;
    public static final int WINDOW_CONFIG_ALWAYS_ON_TOP = 32;
    public static final int WINDOW_CONFIG_APP_BOUNDS = 2;
    public static final int WINDOW_CONFIG_BOUNDS = 1;
    public static final int WINDOW_CONFIG_COMPAT_SANDBOX = 33554432;
    public static final int WINDOW_CONFIG_DEX_TASK_DOCKING = 16777216;
    public static final int WINDOW_CONFIG_DISPLAY_ROTATION = 256;
    public static final int WINDOW_CONFIG_DISPLAY_WINDOWING_MODE = 128;
    public static final int WINDOW_CONFIG_EMBED_ACTIVITY_MODE = 8388608;
    public static final int WINDOW_CONFIG_FLEX_PANEL_MODE = 524288;
    public static final int WINDOW_CONFIG_FREEFORM_TASK_PINNING = 4194304;
    public static final int WINDOW_CONFIG_FREEFORM_TRANSLUCENT = 67108864;
    public static final int WINDOW_CONFIG_MAX_BOUNDS = 4;
    public static final int WINDOW_CONFIG_ROTATION = 64;
    public static final int WINDOW_CONFIG_STAGE_POSITION = 2097152;
    public static final int WINDOW_CONFIG_STAGE_TYPE = 1048576;
    public static final int WINDOW_CONFIG_WINDOWING_MODE = 8;
    private int mActivityType;
    private int mAlwaysOnTop;
    private Rect mAppBounds;
    private final Rect mBounds;
    private Rect mCompatSandboxBounds;
    private int mCompatSandboxFlags;
    private float mCompatSandboxScale;
    private int mDexTaskDockingState;
    private int mDisplayRotation;
    private int mDisplayWindowingMode;
    private int mEmbedActivityMode;
    private int mFlexPanelMode;
    private int mFreeformTaskPinningState;
    private int mFreeformTranslucent;
    private final Rect mMaxBounds;
    private boolean mOverlappingWithCutout;
    private int mPopOverState;
    private int mRotation;
    private int mStage;
    private int mWindowingMode;

    public @interface ActivityType {
    }

    private @interface AlwaysOnTop {
    }

    public @interface DexTaskDocking {
    }

    public @interface EmbedActivityMode {
    }

    private @interface FlexPanelMode {
    }

    public @interface FreeformTaskPinning {
    }

    public @interface FreeformTranslucent {
    }

    public @interface StagePosition {
    }

    public @interface StageType {
    }

    public @interface WindowConfig {
    }

    public @interface WindowingMode {
    }

    public void setPopOverState(int state) {
        this.mPopOverState = state;
    }

    public boolean isPopOver() {
        int i = this.mPopOverState;
        return i == 1 || i == 3;
    }

    public boolean isPopOverWithoutOutlineEffect() {
        return this.mPopOverState == 3;
    }

    private static String popOverStateToString(int popOverState) {
        switch (popOverState) {
            case 0:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                return "on";
            case 2:
                return "off";
            case 3:
                return "on-without-outline-effect";
            default:
                return String.valueOf(popOverState);
        }
    }

    public void setOverlappingWithCutout(boolean overlappingWithCutout) {
        this.mOverlappingWithCutout |= overlappingWithCutout;
    }

    public boolean isOverlappingWithCutout() {
        return this.mOverlappingWithCutout;
    }

    public void setFreeformTaskPinningState(int state) {
        this.mFreeformTaskPinningState = state;
    }

    public int getFreeformTaskPinningState() {
        return this.mFreeformTaskPinningState;
    }

    public void setEmbedActivityMode(int mode) {
        this.mEmbedActivityMode = mode;
    }

    public int getEmbedActivityMode() {
        return this.mEmbedActivityMode;
    }

    public boolean isEmbedded() {
        switch (this.mEmbedActivityMode) {
            case 0:
                return false;
            case 1:
                return !inMultiWindowMode(this.mWindowingMode);
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    public void setDexTaskDockingState(int state) {
        this.mDexTaskDockingState = state;
    }

    public int getDexTaskDockingState() {
        return this.mDexTaskDockingState;
    }

    public static boolean isDexTaskDocking(int dexTaskDockingState) {
        return dexTaskDockingState == 1 || dexTaskDockingState == 2;
    }

    public void setFreeformTranslucent(int translucent) {
        this.mFreeformTranslucent = translucent;
    }

    public int getFreeformTranslucent() {
        return this.mFreeformTranslucent;
    }

    public WindowConfiguration() {
        this.mBounds = new Rect();
        this.mMaxBounds = new Rect();
        this.mDisplayRotation = -1;
        this.mRotation = -1;
        this.mPopOverState = 0;
        this.mOverlappingWithCutout = false;
        this.mFlexPanelMode = 0;
        this.mDexTaskDockingState = -1;
        this.mCompatSandboxFlags = 0;
        this.mCompatSandboxScale = -1.0f;
        this.mFreeformTranslucent = 0;
        unset();
    }

    public WindowConfiguration(WindowConfiguration configuration) {
        this.mBounds = new Rect();
        this.mMaxBounds = new Rect();
        this.mDisplayRotation = -1;
        this.mRotation = -1;
        this.mPopOverState = 0;
        this.mOverlappingWithCutout = false;
        this.mFlexPanelMode = 0;
        this.mDexTaskDockingState = -1;
        this.mCompatSandboxFlags = 0;
        this.mCompatSandboxScale = -1.0f;
        this.mFreeformTranslucent = 0;
        setTo(configuration);
    }

    private WindowConfiguration(Parcel in) {
        this.mBounds = new Rect();
        this.mMaxBounds = new Rect();
        this.mDisplayRotation = -1;
        this.mRotation = -1;
        this.mPopOverState = 0;
        this.mOverlappingWithCutout = false;
        this.mFlexPanelMode = 0;
        this.mDexTaskDockingState = -1;
        this.mCompatSandboxFlags = 0;
        this.mCompatSandboxScale = -1.0f;
        this.mFreeformTranslucent = 0;
        readFromParcel(in);
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mBounds.writeToParcel(dest, flags);
        dest.writeTypedObject(this.mAppBounds, flags);
        this.mMaxBounds.writeToParcel(dest, flags);
        dest.writeInt(this.mWindowingMode);
        dest.writeInt(this.mActivityType);
        dest.writeInt(this.mAlwaysOnTop);
        dest.writeInt(this.mRotation);
        dest.writeInt(this.mDisplayWindowingMode);
        dest.writeInt(this.mDisplayRotation);
        dest.writeInt(this.mStage);
        if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
            dest.writeInt(this.mEmbedActivityMode);
        }
        dest.writeInt(this.mPopOverState);
        dest.writeBoolean(this.mOverlappingWithCutout);
        dest.writeInt(this.mFreeformTaskPinningState);
        if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
            dest.writeInt(this.mCompatSandboxFlags);
            dest.writeFloat(this.mCompatSandboxScale);
            dest.writeTypedObject(this.mCompatSandboxBounds, flags);
        }
        dest.writeInt(this.mDexTaskDockingState);
        if (CoreRune.MW_CAPTION_SHELL_OPACITY) {
            dest.writeInt(this.mFreeformTranslucent);
        }
    }

    public void readFromParcel(Parcel source) {
        this.mBounds.readFromParcel(source);
        this.mAppBounds = (Rect) source.readTypedObject(Rect.CREATOR);
        this.mMaxBounds.readFromParcel(source);
        this.mWindowingMode = source.readInt();
        this.mActivityType = source.readInt();
        this.mAlwaysOnTop = source.readInt();
        this.mRotation = source.readInt();
        this.mDisplayWindowingMode = source.readInt();
        this.mDisplayRotation = source.readInt();
        this.mStage = source.readInt();
        if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
            this.mEmbedActivityMode = source.readInt();
        }
        this.mPopOverState = source.readInt();
        this.mOverlappingWithCutout = source.readBoolean();
        this.mFreeformTaskPinningState = source.readInt();
        if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
            this.mCompatSandboxFlags = source.readInt();
            this.mCompatSandboxScale = source.readFloat();
            this.mCompatSandboxBounds = (Rect) source.readTypedObject(Rect.CREATOR);
        }
        this.mDexTaskDockingState = source.readInt();
        if (CoreRune.MW_CAPTION_SHELL_OPACITY) {
            this.mFreeformTranslucent = source.readInt();
        }
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setBounds(Rect rect) {
        if (rect == null) {
            this.mBounds.setEmpty();
        } else {
            this.mBounds.set(rect);
        }
    }

    public void setAppBounds(Rect rect) {
        if (rect == null) {
            this.mAppBounds = null;
        } else {
            setAppBounds(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public void setMaxBounds(Rect rect) {
        if (rect == null) {
            this.mMaxBounds.setEmpty();
        } else {
            this.mMaxBounds.set(rect);
        }
    }

    public void setMaxBounds(int left, int top, int right, int bottom) {
        this.mMaxBounds.set(left, top, right, bottom);
    }

    public void setDisplayRotation(int rotation) {
        this.mDisplayRotation = rotation;
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
        this.mAlwaysOnTop = alwaysOnTop ? 1 : 2;
    }

    public void unsetAlwaysOnTop() {
        this.mAlwaysOnTop = 0;
    }

    private void setAlwaysOnTop(int alwaysOnTop) {
        this.mAlwaysOnTop = alwaysOnTop;
    }

    public void setAppBounds(int left, int top, int right, int bottom) {
        if (this.mAppBounds == null) {
            this.mAppBounds = new Rect();
        }
        this.mAppBounds.set(left, top, right, bottom);
    }

    public Rect getAppBounds() {
        return this.mAppBounds;
    }

    public Rect getBounds() {
        return this.mBounds;
    }

    public Rect getMaxBounds() {
        return this.mMaxBounds;
    }

    public int getDisplayRotation() {
        return this.mDisplayRotation;
    }

    public int getRotation() {
        return this.mRotation;
    }

    public void setRotation(int rotation) {
        this.mRotation = rotation;
    }

    public void setWindowingMode(int windowingMode) {
        this.mWindowingMode = windowingMode;
    }

    public int getWindowingMode() {
        return this.mWindowingMode;
    }

    public void setDisplayWindowingMode(int windowingMode) {
        this.mDisplayWindowingMode = windowingMode;
    }

    public int getDisplayWindowingMode() {
        return this.mDisplayWindowingMode;
    }

    public void setActivityType(int activityType) {
        if (this.mActivityType == activityType) {
            return;
        }
        if (ActivityThread.isSystem() && this.mActivityType != 0 && activityType != 0) {
            throw new IllegalStateException("Can't change activity type once set: " + this + " activityType=" + activityTypeToString(activityType));
        }
        this.mActivityType = activityType;
    }

    public int getActivityType() {
        return this.mActivityType;
    }

    public void setStage(int stage) {
        this.mStage = stage;
    }

    public int getStage() {
        return this.mStage;
    }

    public void setStageType(int stageType) {
        int i = this.mStage;
        int i2 = i & i & 120;
        this.mStage = i2;
        this.mStage = i2 | (stageType & 7);
    }

    public int getStageType() {
        return this.mStage & 7;
    }

    public void setStagePosition(int stagePosition) {
        int i = this.mStage;
        int i2 = i & i & 7;
        this.mStage = i2;
        this.mStage = i2 | (stagePosition & 120);
    }

    public int getStagePosition() {
        return this.mStage & 120;
    }

    public boolean isSplitScreen() {
        return (this.mStage & 7) != 0;
    }

    public static boolean isSplitScreenWindowingMode(WindowConfiguration winConfig) {
        return (winConfig.mStage & 7) != 0;
    }

    public static boolean isSplitScreenWindowingMode(int stage) {
        return (stage & 7) != 0;
    }

    public void setTo(WindowConfiguration other) {
        setBounds(other.mBounds);
        setAppBounds(other.mAppBounds);
        setMaxBounds(other.mMaxBounds);
        setDisplayRotation(other.mDisplayRotation);
        setWindowingMode(other.mWindowingMode);
        setActivityType(other.mActivityType);
        setAlwaysOnTop(other.mAlwaysOnTop);
        setRotation(other.mRotation);
        setDisplayWindowingMode(other.mDisplayWindowingMode);
        setStage(other.mStage);
        if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
            setEmbedActivityMode(other.mEmbedActivityMode);
        }
        setPopOverState(other.mPopOverState);
        setOverlappingWithCutout(other.mOverlappingWithCutout);
        setFreeformTaskPinningState(other.mFreeformTaskPinningState);
        if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
            setCompatSandboxValues(other.mCompatSandboxFlags, other.mCompatSandboxScale, other.mCompatSandboxBounds);
        }
        setDexTaskDockingState(other.mDexTaskDockingState);
        if (CoreRune.MW_CAPTION_SHELL_OPACITY) {
            setFreeformTranslucent(other.mFreeformTranslucent);
        }
    }

    public void unset() {
        setToDefaults();
    }

    public void setToDefaults() {
        setAppBounds(null);
        setBounds(null);
        setMaxBounds(null);
        setDisplayRotation(-1);
        setWindowingMode(0);
        setActivityType(0);
        setAlwaysOnTop(0);
        setRotation(-1);
        setDisplayWindowingMode(0);
        setEmbedActivityMode(0);
        setPopOverState(0);
        setOverlappingWithCutout(false);
        setFreeformTaskPinningState(0);
        setCompatSandboxValues(0, -1.0f, null);
        setDexTaskDockingState(-1);
        setFreeformTranslucent(0);
    }

    public void scale(float scale) {
        scaleBounds(scale, this.mBounds);
        scaleBounds(scale, this.mMaxBounds);
        Rect rect = this.mAppBounds;
        if (rect != null) {
            scaleBounds(scale, rect);
        }
    }

    public static void scaleBounds(float scale, Rect bounds) {
        int w = bounds.width();
        int h = bounds.height();
        bounds.left = (int) ((bounds.left * scale) + 0.5f);
        bounds.top = (int) ((bounds.top * scale) + 0.5f);
        bounds.right = bounds.left + ((int) ((w * scale) + 0.5f));
        bounds.bottom = bounds.top + ((int) ((h * scale) + 0.5f));
    }

    public int updateFrom(WindowConfiguration delta) {
        int i;
        int i2;
        int changed = 0;
        if (!delta.mBounds.isEmpty() && !delta.mBounds.equals(this.mBounds)) {
            changed = 0 | 1;
            setBounds(delta.mBounds);
        }
        Rect rect = delta.mAppBounds;
        if (rect != null && !rect.equals(this.mAppBounds)) {
            changed |= 2;
            setAppBounds(delta.mAppBounds);
        }
        if (!delta.mMaxBounds.isEmpty() && !delta.mMaxBounds.equals(this.mMaxBounds)) {
            changed |= 4;
            setMaxBounds(delta.mMaxBounds);
        }
        int i3 = delta.mWindowingMode;
        if (i3 != 0 && this.mWindowingMode != i3) {
            changed |= 8;
            setWindowingMode(i3);
        }
        int i4 = delta.mActivityType;
        if (i4 != 0 && this.mActivityType != i4) {
            changed |= 16;
            setActivityType(i4);
        }
        int i5 = delta.mAlwaysOnTop;
        if (i5 != 0 && this.mAlwaysOnTop != i5) {
            changed |= 32;
            setAlwaysOnTop(i5);
        }
        int i6 = delta.mRotation;
        if (i6 != -1 && i6 != this.mRotation) {
            changed |= 64;
            setRotation(i6);
        }
        int i7 = delta.mDisplayWindowingMode;
        if (i7 != 0 && this.mDisplayWindowingMode != i7) {
            changed |= 128;
            setDisplayWindowingMode(i7);
        }
        int i8 = delta.mDisplayRotation;
        if (i8 != -1 && i8 != this.mDisplayRotation) {
            changed |= 256;
            setDisplayRotation(i8);
        }
        if (delta.mStage != 0 || (changed & 8) != 0) {
            int deltaStageType = delta.getStageType();
            if (getStageType() != deltaStageType) {
                changed |= 1048576;
                setStageType(deltaStageType);
            }
            int deltaStagePosition = delta.getStagePosition();
            if (getStagePosition() != deltaStagePosition) {
                changed |= 2097152;
                setStagePosition(deltaStagePosition);
            }
        }
        if (CoreRune.MW_EMBED_ACTIVITY_MODE && (i2 = delta.mEmbedActivityMode) != 0 && this.mEmbedActivityMode != i2) {
            setEmbedActivityMode(i2);
        }
        int i9 = delta.mPopOverState;
        if (i9 != 0 && this.mPopOverState != i9) {
            setPopOverState(i9);
        }
        boolean z = delta.mOverlappingWithCutout;
        if (z && this.mOverlappingWithCutout != z) {
            setOverlappingWithCutout(z);
        }
        int i10 = delta.mFreeformTaskPinningState;
        if (i10 != 0 && this.mFreeformTaskPinningState != i10) {
            setFreeformTaskPinningState(i10);
        }
        if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
            boolean compatSandboxChanged = false;
            int flags = this.mCompatSandboxFlags;
            int i11 = delta.mCompatSandboxFlags;
            if (i11 != 0 && this.mCompatSandboxFlags != i11) {
                compatSandboxChanged = true;
                flags = delta.mCompatSandboxFlags;
            }
            float scale = this.mCompatSandboxScale;
            float f = delta.mCompatSandboxScale;
            if (f != -1.0f && this.mCompatSandboxScale != f) {
                compatSandboxChanged = true;
                scale = delta.mCompatSandboxScale;
            }
            Rect bounds = this.mCompatSandboxBounds;
            Rect rect2 = delta.mCompatSandboxBounds;
            if (rect2 != null && !rect2.equals(this.mCompatSandboxBounds)) {
                compatSandboxChanged = true;
                bounds = delta.mCompatSandboxBounds;
            }
            if (compatSandboxChanged) {
                changed |= 33554432;
                setCompatSandboxValues(flags, scale, bounds);
            }
        }
        int i12 = delta.mDexTaskDockingState;
        if (i12 != -1 && this.mDexTaskDockingState != i12) {
            changed |= 16777216;
            setDexTaskDockingState(i12);
        }
        if (CoreRune.MW_CAPTION_SHELL_OPACITY && (i = delta.mFreeformTranslucent) != 0 && this.mFreeformTranslucent != i) {
            int changed2 = changed | 67108864;
            setFreeformTranslucent(i);
            return changed2;
        }
        return changed;
    }

    public void setTo(WindowConfiguration delta, int mask) {
        if ((mask & 1) != 0) {
            setBounds(delta.mBounds);
        }
        if ((mask & 2) != 0) {
            setAppBounds(delta.mAppBounds);
        }
        if ((mask & 4) != 0) {
            setMaxBounds(delta.mMaxBounds);
        }
        if ((mask & 8) != 0) {
            setWindowingMode(delta.mWindowingMode);
        }
        if ((mask & 16) != 0) {
            setActivityType(delta.mActivityType);
        }
        if ((mask & 32) != 0) {
            setAlwaysOnTop(delta.mAlwaysOnTop);
        }
        if ((mask & 64) != 0) {
            setRotation(delta.mRotation);
        }
        if ((mask & 128) != 0) {
            setDisplayWindowingMode(delta.mDisplayWindowingMode);
        }
        if ((mask & 256) != 0) {
            setDisplayRotation(delta.mDisplayRotation);
        }
        if ((1048576 & mask) != 0) {
            setStageType(delta.getStageType());
        }
        if ((2097152 & mask) != 0) {
            setStagePosition(delta.getStagePosition());
        }
        if (CoreRune.MW_EMBED_ACTIVITY_MODE && (8388608 & mask) != 0) {
            setEmbedActivityMode(delta.mEmbedActivityMode);
        }
        if ((4194304 & mask) != 0) {
            setFreeformTaskPinningState(delta.mFreeformTaskPinningState);
        }
        if ((16777216 & mask) != 0) {
            setDexTaskDockingState(delta.mDexTaskDockingState);
        }
        if (CoreRune.MW_CAPTION_SHELL_OPACITY && (67108864 & mask) != 0) {
            setFreeformTranslucent(delta.mFreeformTranslucent);
        }
    }

    public long diff(WindowConfiguration other, boolean compareUndefined) {
        Rect rect;
        Rect rect2;
        Rect rect3;
        Rect rect4;
        long changes = this.mBounds.equals(other.mBounds) ? 0L : 0 | 1;
        if ((compareUndefined || other.mAppBounds != null) && (rect = this.mAppBounds) != (rect2 = other.mAppBounds) && (rect == null || !rect.equals(rect2))) {
            changes |= 2;
        }
        if (!this.mMaxBounds.equals(other.mMaxBounds)) {
            changes |= 4;
        }
        if ((compareUndefined || other.mWindowingMode != 0) && this.mWindowingMode != other.mWindowingMode) {
            changes |= 8;
        }
        if ((compareUndefined || other.mActivityType != 0) && this.mActivityType != other.mActivityType) {
            changes |= 16;
        }
        if ((compareUndefined || other.mAlwaysOnTop != 0) && this.mAlwaysOnTop != other.mAlwaysOnTop) {
            changes |= 32;
        }
        if ((compareUndefined || other.mRotation != -1) && this.mRotation != other.mRotation) {
            changes |= 64;
        }
        if ((compareUndefined || other.mDisplayWindowingMode != 0) && this.mDisplayWindowingMode != other.mDisplayWindowingMode) {
            changes |= 128;
        }
        if ((compareUndefined || other.mDisplayRotation != -1) && this.mDisplayRotation != other.mDisplayRotation) {
            changes |= 256;
        }
        if (compareUndefined || other.mStage != 0) {
            int deltaStageType = other.getStageType();
            if (getStageType() != deltaStageType) {
                changes |= 1048576;
            }
            int deltaStagePosition = other.getStagePosition();
            if (getStagePosition() != deltaStagePosition) {
                changes |= 2097152;
            }
        }
        if (CoreRune.MW_EMBED_ACTIVITY_MODE && ((compareUndefined || other.mEmbedActivityMode != 0) && this.mEmbedActivityMode != other.mEmbedActivityMode)) {
            changes |= 8388608;
        }
        if ((compareUndefined || other.mFreeformTaskPinningState != 0) && this.mFreeformTaskPinningState != other.mFreeformTaskPinningState) {
            changes |= 128;
        }
        if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
            if ((compareUndefined || other.mCompatSandboxFlags != 0) && this.mCompatSandboxFlags != other.mCompatSandboxFlags) {
                changes |= 33554432;
            } else if ((compareUndefined || other.mCompatSandboxScale != -1.0f) && this.mCompatSandboxScale != other.mCompatSandboxScale) {
                changes |= 33554432;
            } else if ((compareUndefined || other.mCompatSandboxBounds != null) && (rect3 = this.mCompatSandboxBounds) != (rect4 = other.mCompatSandboxBounds) && (rect3 == null || !rect3.equals(rect4))) {
                changes |= 33554432;
            }
        }
        if ((compareUndefined || other.mDexTaskDockingState != -1) && this.mDexTaskDockingState != other.mDexTaskDockingState) {
            changes |= 16777216;
        }
        if (!CoreRune.MW_CAPTION_SHELL_OPACITY) {
            return changes;
        }
        if ((compareUndefined || other.mFreeformTranslucent != 0) && this.mFreeformTranslucent != other.mFreeformTranslucent) {
            return changes | 67108864;
        }
        return changes;
    }

    @Override // java.lang.Comparable
    public int compareTo(WindowConfiguration that) {
        int n;
        Rect rect = this.mAppBounds;
        if (rect == null && that.mAppBounds != null) {
            return 1;
        }
        if (rect != null && that.mAppBounds == null) {
            return -1;
        }
        if (rect != null && that.mAppBounds != null) {
            int n2 = rect.left - that.mAppBounds.left;
            if (n2 != 0) {
                return n2;
            }
            int n3 = this.mAppBounds.top - that.mAppBounds.top;
            if (n3 != 0) {
                return n3;
            }
            int n4 = this.mAppBounds.right - that.mAppBounds.right;
            if (n4 != 0) {
                return n4;
            }
            int n5 = this.mAppBounds.bottom - that.mAppBounds.bottom;
            if (n5 != 0) {
                return n5;
            }
        }
        int n6 = this.mMaxBounds.left - that.mMaxBounds.left;
        if (n6 != 0) {
            return n6;
        }
        int n7 = this.mMaxBounds.top - that.mMaxBounds.top;
        if (n7 != 0) {
            return n7;
        }
        int n8 = this.mMaxBounds.right - that.mMaxBounds.right;
        if (n8 != 0) {
            return n8;
        }
        int n9 = this.mMaxBounds.bottom - that.mMaxBounds.bottom;
        if (n9 != 0) {
            return n9;
        }
        int n10 = this.mBounds.left - that.mBounds.left;
        if (n10 != 0) {
            return n10;
        }
        int n11 = this.mBounds.top - that.mBounds.top;
        if (n11 != 0) {
            return n11;
        }
        int n12 = this.mBounds.right - that.mBounds.right;
        if (n12 != 0) {
            return n12;
        }
        int n13 = this.mBounds.bottom - that.mBounds.bottom;
        if (n13 != 0) {
            return n13;
        }
        int n14 = this.mWindowingMode - that.mWindowingMode;
        if (n14 != 0) {
            return n14;
        }
        int n15 = this.mActivityType - that.mActivityType;
        if (n15 != 0) {
            return n15;
        }
        int n16 = this.mAlwaysOnTop - that.mAlwaysOnTop;
        if (n16 != 0) {
            return n16;
        }
        int n17 = this.mRotation - that.mRotation;
        if (n17 != 0) {
            return n17;
        }
        int n18 = this.mDisplayWindowingMode - that.mDisplayWindowingMode;
        if (n18 != 0) {
            return n18;
        }
        int n19 = this.mDisplayRotation - that.mDisplayRotation;
        if (n19 != 0) {
            return n19;
        }
        int n20 = this.mStage - that.mStage;
        if (n20 != 0) {
            return n20;
        }
        if (CoreRune.MW_EMBED_ACTIVITY_MODE && (n = this.mEmbedActivityMode - that.mEmbedActivityMode) != 0) {
            return n;
        }
        int n21 = this.mPopOverState - that.mPopOverState;
        if (n21 != 0) {
            return n21;
        }
        int n22 = this.mFreeformTaskPinningState - that.mFreeformTaskPinningState;
        if (n22 != 0) {
            return n22;
        }
        int n23 = this.mDexTaskDockingState - that.mDexTaskDockingState;
        return n23 != 0 ? n23 : (!CoreRune.MW_CAPTION_SHELL_OPACITY || (n23 = this.mFreeformTranslucent - that.mFreeformTranslucent) == 0) ? n23 : n23;
    }

    public boolean equals(Object that) {
        if (that == null) {
            return false;
        }
        if (that == this) {
            return true;
        }
        if (!(that instanceof WindowConfiguration) || compareTo((WindowConfiguration) that) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = (((((((((((((((((((0 * 31) + Objects.hashCode(this.mAppBounds)) * 31) + Objects.hashCode(this.mBounds)) * 31) + Objects.hashCode(this.mMaxBounds)) * 31) + this.mWindowingMode) * 31) + this.mActivityType) * 31) + this.mAlwaysOnTop) * 31) + this.mRotation) * 31) + this.mDisplayWindowingMode) * 31) + this.mDisplayRotation) * 31) + this.mStage;
        if (CoreRune.MW_CAPTION_SHELL_OPACITY) {
            return (result * 31) + this.mFreeformTranslucent;
        }
        return result;
    }

    public String toString() {
        StringBuilder append = new StringBuilder().append("{ mBounds=").append(this.mBounds).append(" mAppBounds=").append(this.mAppBounds).append(" mMaxBounds=").append(this.mMaxBounds).append(" mDisplayRotation=");
        int i = this.mRotation;
        String str = KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        StringBuilder append2 = append.append(i == -1 ? KeyboardLayout.LAYOUT_TYPE_UNDEFINED : Surface.rotationToString(this.mDisplayRotation)).append(" mWindowingMode=").append(windowingModeToString(this.mWindowingMode)).append(" mDisplayWindowingMode=").append(windowingModeToString(this.mDisplayWindowingMode)).append(" mActivityType=").append(activityTypeToString(this.mActivityType)).append(" mAlwaysOnTop=").append(alwaysOnTopToString(this.mAlwaysOnTop)).append(" mRotation=");
        int i2 = this.mRotation;
        if (i2 != -1) {
            str = Surface.rotationToString(i2);
        }
        return append2.append(str).append(" mPopOver=").append(popOverStateToString(this.mPopOverState)).append(" mOverlappingWithCutout=").append(this.mOverlappingWithCutout).append(" mStageConfig=").append(stageConfigToString(this.mStage)).append(CoreRune.MW_EMBED_ACTIVITY_MODE ? " mEmbedActivityMode=" + embedActivityModeToString(this.mEmbedActivityMode) : "").append(" mFreeformTaskPinningState=").append(freeformTaskPinningToString(this.mFreeformTaskPinningState)).append("").append(CoreRune.MT_SUPPORT_COMPAT_SANDBOX ? compatSandboxInfoToString() : "").append(" mDexTaskDockingState=").append(dexTaskDockingStateToString(this.mDexTaskDockingState)).append(CoreRune.MW_CAPTION_SHELL_OPACITY ? " mFreeformTranslucent=" + translucentToString(this.mFreeformTranslucent) : "").append("}").toString();
    }

    private static String translucentToString(int translucent) {
        switch (translucent) {
            case 0:
                return DevicePolicyResources.UNDEFINED;
            case 1:
                return "TRANSLUCENT_ON";
            case 2:
                return "TRANSLUCENT_OFF";
            default:
                return Integer.toString(translucent);
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long fieldId) {
        long token = protoOutputStream.start(fieldId);
        Rect rect = this.mAppBounds;
        if (rect != null) {
            rect.dumpDebug(protoOutputStream, 1146756268033L);
        }
        protoOutputStream.write(1120986464258L, this.mWindowingMode);
        protoOutputStream.write(1120986464259L, this.mActivityType);
        this.mBounds.dumpDebug(protoOutputStream, 1146756268036L);
        this.mMaxBounds.dumpDebug(protoOutputStream, 1146756268037L);
        protoOutputStream.end(token);
    }

    public void readFromProto(ProtoInputStream proto, long fieldId) throws IOException, WireTypeMismatchException {
        long token = proto.start(fieldId);
        while (proto.nextField() != -1) {
            try {
                switch (proto.getFieldNumber()) {
                    case 1:
                        Rect rect = new Rect();
                        this.mAppBounds = rect;
                        rect.readFromProto(proto, 1146756268033L);
                        break;
                    case 2:
                        this.mWindowingMode = proto.readInt(1120986464258L);
                        break;
                    case 3:
                        this.mActivityType = proto.readInt(1120986464259L);
                        break;
                    case 4:
                        this.mBounds.readFromProto(proto, 1146756268036L);
                        break;
                    case 5:
                        this.mMaxBounds.readFromProto(proto, 1146756268037L);
                        break;
                }
            } finally {
                proto.end(token);
            }
        }
    }

    public boolean hasWindowShadow() {
        return this.mWindowingMode != 6 && tasksAreFloating();
    }

    public boolean hasWindowDecorCaption() {
        return this.mActivityType == 1 && (this.mWindowingMode == 5 || this.mDisplayWindowingMode == 5);
    }

    public boolean canResizeTask() {
        int i = this.mWindowingMode;
        return i == 5 || i == 6;
    }

    public boolean persistTaskBounds() {
        return this.mWindowingMode == 5;
    }

    public boolean tasksAreFloating() {
        return isFloating(this.mWindowingMode);
    }

    public static boolean isFloating(int windowingMode) {
        return windowingMode == 5 || windowingMode == 2;
    }

    public static boolean inMultiWindowMode(int windowingMode) {
        return (windowingMode == 1 || windowingMode == 0) ? false : true;
    }

    public boolean canReceiveKeys() {
        return this.mWindowingMode != 2;
    }

    public boolean isAlwaysOnTop() {
        int i = this.mWindowingMode;
        if (i == 2 || this.mActivityType == 5) {
            return true;
        }
        if (this.mAlwaysOnTop != 1) {
            return false;
        }
        return i == 5 || i == 6;
    }

    public boolean useWindowFrameForBackdrop() {
        int i = this.mWindowingMode;
        return i == 5 || i == 2;
    }

    public boolean hasMovementAnimations() {
        return this.mWindowingMode != 2;
    }

    public boolean supportSplitScreenWindowingMode() {
        return supportSplitScreenWindowingMode(this.mActivityType);
    }

    public static boolean supportSplitScreenWindowingMode(int activityType) {
        return (activityType == 4 || activityType == 5) ? false : true;
    }

    public static String windowingModeToString(int windowingMode) {
        switch (windowingMode) {
            case 0:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                return "fullscreen";
            case 2:
                return ContactsContract.ContactOptionsColumns.PINNED;
            case 3:
            case 4:
            default:
                return String.valueOf(windowingMode);
            case 5:
                return "freeform";
            case 6:
                return "multi-window";
        }
    }

    public static String activityTypeToString(int applicationType) {
        switch (applicationType) {
            case 0:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                return "standard";
            case 2:
                return "home";
            case 3:
                return "recents";
            case 4:
                return Settings.Secure.ASSISTANT;
            case 5:
                return "dream";
            default:
                return String.valueOf(applicationType);
        }
    }

    public static String alwaysOnTopToString(int alwaysOnTop) {
        switch (alwaysOnTop) {
            case 0:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                return "on";
            case 2:
                return "off";
            default:
                return String.valueOf(alwaysOnTop);
        }
    }

    public static String stageConfigToString(int stageConfig) {
        StringBuilder sb = new StringBuilder(32);
        switch (stageConfig & 7) {
            case 0:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                sb.append("main/");
                break;
            case 2:
                sb.append("side/");
                break;
            case 4:
                sb.append("cell/");
                break;
        }
        switch (stageConfig & 120) {
            case 8:
                sb.append("left");
                break;
            case 16:
                sb.append(GenerateXML.TOP);
                break;
            case 24:
                sb.append("left-top");
                break;
            case 32:
                sb.append("right");
                break;
            case 48:
                sb.append("right-top");
                break;
            case 64:
                sb.append(GenerateXML.BOTTOM);
                break;
            case 72:
                sb.append("left-bottom");
                break;
            case 96:
                sb.append("right-bottom");
                break;
        }
        return sb.toString();
    }

    public String getStageTypeToString() {
        StringBuilder sb = new StringBuilder(32);
        switch (this.mStage & 7) {
            case 0:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                sb.append("main");
                break;
            case 2:
                sb.append("side");
                break;
            case 4:
                sb.append("cell");
                break;
        }
        return sb.toString();
    }

    public String getStagePositionToString() {
        return stagePositionToString(this.mStage);
    }

    public static String stagePositionToString(int position) {
        StringBuilder sb = new StringBuilder(32);
        switch (position & 120) {
            case 0:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 8:
                sb.append("left");
                break;
            case 16:
                sb.append(GenerateXML.TOP);
                break;
            case 24:
                sb.append("left-top");
                break;
            case 32:
                sb.append("right");
                break;
            case 48:
                sb.append("right-top");
                break;
            case 64:
                sb.append(GenerateXML.BOTTOM);
                break;
            case 72:
                sb.append("left-bottom");
                break;
            case 96:
                sb.append("right-bottom");
                break;
        }
        return sb.toString();
    }

    public static String embedActivityModeToString(int state) {
        switch (state) {
            case 0:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                return "full";
            case 2:
                return "left";
            case 3:
                return "right";
            case 4:
                return GenerateXML.TOP;
            case 5:
                return GenerateXML.BOTTOM;
            default:
                return String.valueOf(state);
        }
    }

    public static String flexPanelModeToString(int flexPanelMode) {
        switch (flexPanelMode) {
            case 0:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                return "on";
            case 2:
                return "off";
            default:
                return String.valueOf(flexPanelMode);
        }
    }

    public void setFlexPanelMode(int mode) {
        this.mFlexPanelMode = mode;
    }

    public boolean isFlexPanelEnabled() {
        return this.mFlexPanelMode == 1;
    }

    public static String freeformTaskPinningToString(int state) {
        switch (state) {
            case 0:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                return "unpinned";
            case 2:
                return ContactsContract.ContactOptionsColumns.PINNED;
            case 3:
                return SemWifiDisplayParameter.VALUE_DISABLE;
            default:
                return String.valueOf(state);
        }
    }

    public void setCompatSandboxValues(int flags, float scale, Rect bounds) {
        this.mCompatSandboxFlags = flags;
        this.mCompatSandboxScale = scale;
        if (bounds == null) {
            this.mCompatSandboxBounds = null;
            return;
        }
        if (this.mCompatSandboxBounds == null) {
            this.mCompatSandboxBounds = new Rect();
        }
        this.mCompatSandboxBounds.set(bounds);
    }

    public int getCompatSandboxFlags() {
        int i = this.mCompatSandboxFlags;
        if ((i & 1) != 0) {
            return 0;
        }
        return i;
    }

    public float getCompatSandboxInvScale() {
        if (getCompatSandboxFlags() != 0) {
            float f = this.mCompatSandboxScale;
            if (f != -1.0f) {
                float scale = 1.0f / f;
                if (scale > 0.0f) {
                    return scale;
                }
            }
        }
        return 1.0f;
    }

    public Rect getCompatSandboxBounds() {
        Rect rect = this.mCompatSandboxBounds;
        if (rect == null || rect.isEmpty()) {
            return this.mBounds;
        }
        return this.mCompatSandboxBounds;
    }

    private String compatSandboxInfoToString() {
        return (this.mCompatSandboxFlags != 0 ? " mCompatSandboxFlags=0x" + this.mCompatSandboxFlags : "") + (this.mCompatSandboxScale != 1.0f ? " mCompatSandboxScale=" + this.mCompatSandboxScale : "") + (this.mCompatSandboxBounds != null ? " mCompatSandboxScale=" + this.mCompatSandboxBounds : "");
    }

    public static String dexTaskDockingStateToString(int dexTaskDockingState) {
        switch (dexTaskDockingState) {
            case -1:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 0:
                return "none";
            case 1:
                return "left";
            case 2:
                return "right";
            default:
                return String.valueOf(dexTaskDockingState);
        }
    }

    public void overrideUndefinedFrom(WindowConfiguration delta) {
        int i;
        int i2;
        if (this.mFreeformTaskPinningState == 0 && (i2 = delta.mFreeformTaskPinningState) == 1) {
            this.mFreeformTaskPinningState = i2;
        }
        if (this.mPopOverState == 0 && (i = delta.mPopOverState) == 2) {
            this.mPopOverState = i;
        }
    }
}
