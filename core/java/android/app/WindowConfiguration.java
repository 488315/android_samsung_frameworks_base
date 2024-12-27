package android.app;

import android.app.admin.DevicePolicyResources;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.input.KeyboardLayout;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.WireTypeMismatchException;
import android.view.Surface;

import com.samsung.android.rune.CoreRune;
import com.samsung.android.wallpaperbackup.GenerateXML;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

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
    public static final Parcelable.Creator<WindowConfiguration> CREATOR =
            new Parcelable.Creator<
                    WindowConfiguration>() { // from class: android.app.WindowConfiguration.1
                @Override // android.os.Parcelable.Creator
                public WindowConfiguration createFromParcel(Parcel in) {
                    return new WindowConfiguration(in);
                }

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
    public static final int WINDOW_CONFIG_DISPLAY_ROTATION = 128;
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
    private Rect mCompatSandboxScaledBounds;
    private int mDexTaskDockingState;
    private int mDisplayRotation;
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

    @Retention(RetentionPolicy.SOURCE)
    public @interface ActivityType {}

    private @interface AlwaysOnTop {}

    public @interface DexTaskDocking {}

    public @interface EmbedActivityMode {}

    private @interface FlexPanelMode {}

    public @interface FreeformTaskPinning {}

    public @interface FreeformTranslucent {}

    public @interface StagePosition {}

    public @interface StageType {}

    public @interface WindowConfig {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface WindowingMode {}

    public void setPopOverState(int state) {
        this.mPopOverState = state;
    }

    public boolean isPopOver() {
        return this.mPopOverState == 1 || this.mPopOverState == 3;
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
        this.mOverlappingWithCutout = overlappingWithCutout;
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

    public void setFreeformTranslucent(int translucent) {
        this.mFreeformTranslucent = translucent;
    }

    public int getFreeformTranslucent() {
        return this.mFreeformTranslucent;
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

    public WindowConfiguration() {
        this.mBounds = new Rect();
        this.mMaxBounds = new Rect();
        this.mDisplayRotation = -1;
        this.mRotation = -1;
        this.mPopOverState = 0;
        this.mOverlappingWithCutout = false;
        this.mFlexPanelMode = 0;
        this.mCompatSandboxFlags = 0;
        this.mCompatSandboxScale = -1.0f;
        this.mFreeformTranslucent = 0;
        this.mDexTaskDockingState = -1;
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
        this.mCompatSandboxFlags = 0;
        this.mCompatSandboxScale = -1.0f;
        this.mFreeformTranslucent = 0;
        this.mDexTaskDockingState = -1;
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
        this.mCompatSandboxFlags = 0;
        this.mCompatSandboxScale = -1.0f;
        this.mFreeformTranslucent = 0;
        this.mDexTaskDockingState = -1;
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mBounds.writeToParcel(dest, flags);
        dest.writeTypedObject(this.mAppBounds, flags);
        this.mMaxBounds.writeToParcel(dest, flags);
        dest.writeInt(this.mWindowingMode);
        dest.writeInt(this.mActivityType);
        dest.writeInt(this.mAlwaysOnTop);
        dest.writeInt(this.mRotation);
        dest.writeInt(this.mDisplayRotation);
        dest.writeInt(this.mStage);
        if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
            dest.writeInt(this.mEmbedActivityMode);
        }
        dest.writeInt(this.mPopOverState);
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE) {
            dest.writeInt(this.mFlexPanelMode);
        }
        dest.writeInt(this.mCompatSandboxFlags);
        dest.writeFloat(this.mCompatSandboxScale);
        dest.writeTypedObject(this.mCompatSandboxBounds, flags);
        dest.writeInt(this.mDexTaskDockingState);
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING) {
            dest.writeInt(this.mFreeformTaskPinningState);
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
        this.mDisplayRotation = source.readInt();
        this.mStage = source.readInt();
        if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
            this.mEmbedActivityMode = source.readInt();
        }
        this.mPopOverState = source.readInt();
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE) {
            this.mFlexPanelMode = source.readInt();
        }
        this.mCompatSandboxFlags = source.readInt();
        this.mCompatSandboxScale = source.readFloat();
        this.mCompatSandboxBounds = (Rect) source.readTypedObject(Rect.CREATOR);
        this.mDexTaskDockingState = source.readInt();
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING) {
            this.mFreeformTaskPinningState = source.readInt();
        }
    }

    @Override // android.os.Parcelable
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

    public void setActivityType(int activityType) {
        if (this.mActivityType == activityType) {
            return;
        }
        if (ActivityThread.isSystem() && this.mActivityType != 0 && activityType != 0) {
            throw new IllegalStateException(
                    "Can't change activity type once set: "
                            + this
                            + " activityType="
                            + activityTypeToString(activityType));
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
        this.mStage &= this.mStage & 120;
        this.mStage |= stageType & 7;
    }

    public int getStageType() {
        return this.mStage & 7;
    }

    public void setStagePosition(int stagePosition) {
        this.mStage &= this.mStage & 7;
        this.mStage |= stagePosition & 120;
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
        setStage(other.mStage);
        if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
            setEmbedActivityMode(other.mEmbedActivityMode);
        }
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE) {
            setFlexPanelMode(other.mFlexPanelMode);
        }
        setPopOverState(other.mPopOverState);
        setCompatSandboxValues(
                other.mCompatSandboxFlags, other.mCompatSandboxScale, other.mCompatSandboxBounds);
        setDexTaskDockingState(other.mDexTaskDockingState);
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING) {
            setFreeformTaskPinningState(other.mFreeformTaskPinningState);
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
        setDexTaskDockingState(-1);
        setFreeformTranslucent(0);
        setFreeformTaskPinningState(0);
        setEmbedActivityMode(0);
    }

    public void scale(float scale) {
        scaleBounds(scale, this.mBounds);
        scaleBounds(scale, this.mMaxBounds);
        if (this.mAppBounds != null) {
            scaleBounds(scale, this.mAppBounds);
        }
    }

    private static void scaleBounds(float scale, Rect bounds) {
        int w = bounds.width();
        int h = bounds.height();
        bounds.left = (int) ((bounds.left * scale) + 0.5f);
        bounds.top = (int) ((bounds.top * scale) + 0.5f);
        bounds.right = bounds.left + ((int) ((w * scale) + 0.5f));
        bounds.bottom = bounds.top + ((int) ((h * scale) + 0.5f));
    }

    public int updateFrom(WindowConfiguration delta) {
        int changed = 0;
        if (!delta.mBounds.isEmpty() && !delta.mBounds.equals(this.mBounds)) {
            changed = 0 | 1;
            setBounds(delta.mBounds);
        }
        if (delta.mAppBounds != null && !delta.mAppBounds.equals(this.mAppBounds)) {
            changed |= 2;
            setAppBounds(delta.mAppBounds);
        }
        if (!delta.mMaxBounds.isEmpty() && !delta.mMaxBounds.equals(this.mMaxBounds)) {
            changed |= 4;
            setMaxBounds(delta.mMaxBounds);
        }
        if (delta.mWindowingMode != 0 && this.mWindowingMode != delta.mWindowingMode) {
            changed |= 8;
            setWindowingMode(delta.mWindowingMode);
        }
        if (delta.mActivityType != 0 && this.mActivityType != delta.mActivityType) {
            changed |= 16;
            setActivityType(delta.mActivityType);
        }
        if (delta.mAlwaysOnTop != 0 && this.mAlwaysOnTop != delta.mAlwaysOnTop) {
            changed |= 32;
            setAlwaysOnTop(delta.mAlwaysOnTop);
        }
        if (delta.mRotation != -1 && delta.mRotation != this.mRotation) {
            changed |= 64;
            setRotation(delta.mRotation);
        }
        if (delta.mDisplayRotation != -1 && delta.mDisplayRotation != this.mDisplayRotation) {
            changed |= 128;
            setDisplayRotation(delta.mDisplayRotation);
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
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE
                && delta.mFlexPanelMode != 0
                && this.mFlexPanelMode != delta.mFlexPanelMode) {
            changed |= 524288;
            setFlexPanelMode(delta.mFlexPanelMode);
        }
        if (CoreRune.MW_EMBED_ACTIVITY_MODE
                && delta.mEmbedActivityMode != 0
                && this.mEmbedActivityMode != delta.mEmbedActivityMode) {
            setEmbedActivityMode(delta.mEmbedActivityMode);
        }
        if (delta.mPopOverState != 0 && this.mPopOverState != delta.mPopOverState) {
            setPopOverState(delta.mPopOverState);
        }
        boolean compatSandboxChanged = false;
        int flags = this.mCompatSandboxFlags;
        if (delta.mCompatSandboxFlags != 0
                && this.mCompatSandboxFlags != delta.mCompatSandboxFlags) {
            compatSandboxChanged = true;
            flags = delta.mCompatSandboxFlags;
        }
        float scale = this.mCompatSandboxScale;
        if (delta.mCompatSandboxScale != -1.0f
                && this.mCompatSandboxScale != delta.mCompatSandboxScale) {
            compatSandboxChanged = true;
            scale = delta.mCompatSandboxScale;
        }
        Rect bounds = this.mCompatSandboxBounds;
        if (delta.mCompatSandboxBounds != null
                && !delta.mCompatSandboxBounds.equals(this.mCompatSandboxBounds)) {
            compatSandboxChanged = true;
            bounds = delta.mCompatSandboxBounds;
        }
        if (compatSandboxChanged) {
            changed |= 33554432;
            setCompatSandboxValues(flags, scale, bounds);
        }
        if (delta.mDexTaskDockingState != -1
                && this.mDexTaskDockingState != delta.mDexTaskDockingState) {
            changed |= 16777216;
            setDexTaskDockingState(delta.mDexTaskDockingState);
        }
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING
                && delta.mFreeformTaskPinningState != 0
                && this.mFreeformTaskPinningState != delta.mFreeformTaskPinningState) {
            setFreeformTaskPinningState(delta.mFreeformTaskPinningState);
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
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE && (524288 & mask) != 0) {
            setFlexPanelMode(delta.mFlexPanelMode);
        }
        if ((16777216 & mask) != 0) {
            setDexTaskDockingState(delta.mDexTaskDockingState);
        }
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING && (4194304 & mask) != 0) {
            setFreeformTaskPinningState(delta.mFreeformTaskPinningState);
        }
    }

    public long diff(WindowConfiguration other, boolean compareUndefined) {
        long changes = this.mBounds.equals(other.mBounds) ? 0L : 0 | 1;
        if ((compareUndefined || other.mAppBounds != null)
                && this.mAppBounds != other.mAppBounds
                && (this.mAppBounds == null || !this.mAppBounds.equals(other.mAppBounds))) {
            changes |= 2;
        }
        if (!this.mMaxBounds.equals(other.mMaxBounds)) {
            changes |= 4;
        }
        if ((compareUndefined || other.mWindowingMode != 0)
                && this.mWindowingMode != other.mWindowingMode) {
            changes |= 8;
        }
        if ((compareUndefined || other.mActivityType != 0)
                && this.mActivityType != other.mActivityType) {
            changes |= 16;
        }
        if ((compareUndefined || other.mAlwaysOnTop != 0)
                && this.mAlwaysOnTop != other.mAlwaysOnTop) {
            changes |= 32;
        }
        if ((compareUndefined || other.mRotation != -1) && this.mRotation != other.mRotation) {
            changes |= 64;
        }
        if ((compareUndefined || other.mDisplayRotation != -1)
                && this.mDisplayRotation != other.mDisplayRotation) {
            changes |= 128;
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
        if (CoreRune.MW_EMBED_ACTIVITY_MODE
                && ((compareUndefined || other.mEmbedActivityMode != 0)
                        && this.mEmbedActivityMode != other.mEmbedActivityMode)) {
            changes |= 8388608;
        }
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE
                && ((compareUndefined || other.mFlexPanelMode != 0)
                        && this.mFlexPanelMode != other.mFlexPanelMode)) {
            changes |= 524288;
        }
        if ((compareUndefined || other.mCompatSandboxFlags != 0)
                && this.mCompatSandboxFlags != other.mCompatSandboxFlags) {
            changes |= 33554432;
        } else if ((compareUndefined || other.mCompatSandboxScale != -1.0f)
                && this.mCompatSandboxScale != other.mCompatSandboxScale) {
            changes |= 33554432;
        } else if ((compareUndefined || other.mCompatSandboxBounds != null)
                && this.mCompatSandboxBounds != other.mCompatSandboxBounds
                && (this.mCompatSandboxBounds == null
                        || !this.mCompatSandboxBounds.equals(other.mCompatSandboxBounds))) {
            changes |= 33554432;
        }
        if ((compareUndefined || other.mDexTaskDockingState != -1)
                && this.mDexTaskDockingState != other.mDexTaskDockingState) {
            changes |= 16777216;
        }
        if (!CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING) {
            return changes;
        }
        if ((compareUndefined || other.mFreeformTaskPinningState != 0)
                && this.mFreeformTaskPinningState != other.mFreeformTaskPinningState) {
            return changes | 8;
        }
        return changes;
    }

    @Override // java.lang.Comparable
    public int compareTo(WindowConfiguration that) {
        int n;
        int n2;
        if (this.mAppBounds == null && that.mAppBounds != null) {
            return 1;
        }
        if (this.mAppBounds != null && that.mAppBounds == null) {
            return -1;
        }
        if (this.mAppBounds != null && that.mAppBounds != null) {
            int n3 = this.mAppBounds.left - that.mAppBounds.left;
            if (n3 != 0) {
                return n3;
            }
            int n4 = this.mAppBounds.top - that.mAppBounds.top;
            if (n4 != 0) {
                return n4;
            }
            int n5 = this.mAppBounds.right - that.mAppBounds.right;
            if (n5 != 0) {
                return n5;
            }
            int n6 = this.mAppBounds.bottom - that.mAppBounds.bottom;
            if (n6 != 0) {
                return n6;
            }
        }
        int n7 = this.mMaxBounds.left - that.mMaxBounds.left;
        if (n7 != 0) {
            return n7;
        }
        int n8 = this.mMaxBounds.top - that.mMaxBounds.top;
        if (n8 != 0) {
            return n8;
        }
        int n9 = this.mMaxBounds.right - that.mMaxBounds.right;
        if (n9 != 0) {
            return n9;
        }
        int n10 = this.mMaxBounds.bottom - that.mMaxBounds.bottom;
        if (n10 != 0) {
            return n10;
        }
        int n11 = this.mBounds.left - that.mBounds.left;
        if (n11 != 0) {
            return n11;
        }
        int n12 = this.mBounds.top - that.mBounds.top;
        if (n12 != 0) {
            return n12;
        }
        int n13 = this.mBounds.right - that.mBounds.right;
        if (n13 != 0) {
            return n13;
        }
        int n14 = this.mBounds.bottom - that.mBounds.bottom;
        if (n14 != 0) {
            return n14;
        }
        int n15 = this.mWindowingMode - that.mWindowingMode;
        if (n15 != 0) {
            return n15;
        }
        int n16 = this.mActivityType - that.mActivityType;
        if (n16 != 0) {
            return n16;
        }
        int n17 = this.mAlwaysOnTop - that.mAlwaysOnTop;
        if (n17 != 0) {
            return n17;
        }
        int n18 = this.mRotation - that.mRotation;
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
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE
                && (n2 = this.mFlexPanelMode - that.mFlexPanelMode) != 0) {
            return n2;
        }
        if (CoreRune.MW_EMBED_ACTIVITY_MODE
                && (n = this.mEmbedActivityMode - that.mEmbedActivityMode) != 0) {
            return n;
        }
        int n21 = this.mPopOverState - that.mPopOverState;
        if (n21 != 0) {
            return n21;
        }
        int n22 = this.mDexTaskDockingState - that.mDexTaskDockingState;
        return n22 != 0
                ? n22
                : (!CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING
                                || (n22 =
                                                this.mFreeformTaskPinningState
                                                        - that.mFreeformTaskPinningState)
                                        == 0)
                        ? n22
                        : n22;
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
        int result =
                (((((((((((((((((0 * 31) + Objects.hashCode(this.mAppBounds)) * 31)
                                                                                                                                        + Objects
                                                                                                                                                .hashCode(
                                                                                                                                                        this
                                                                                                                                                                .mBounds))
                                                                                                                                * 31)
                                                                                                                        + Objects
                                                                                                                                .hashCode(
                                                                                                                                        this
                                                                                                                                                .mMaxBounds))
                                                                                                                * 31)
                                                                                                        + this
                                                                                                                .mWindowingMode)
                                                                                                * 31)
                                                                                        + this
                                                                                                .mActivityType)
                                                                                * 31)
                                                                        + this.mAlwaysOnTop)
                                                                * 31)
                                                        + this.mRotation)
                                                * 31)
                                        + this.mDisplayRotation)
                                * 31)
                        + this.mStage;
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE) {
            return (result * 31) + this.mFlexPanelMode;
        }
        return result;
    }

    public String toString() {
        StringBuilder append =
                new StringBuilder()
                        .append("{ mBounds=")
                        .append(this.mBounds)
                        .append(" mAppBounds=")
                        .append(this.mAppBounds)
                        .append(" mMaxBounds=")
                        .append(this.mMaxBounds)
                        .append(" mDisplayRotation=");
        int i = this.mRotation;
        String str = KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        StringBuilder append2 =
                append.append(
                                i == -1
                                        ? KeyboardLayout.LAYOUT_TYPE_UNDEFINED
                                        : Surface.rotationToString(this.mDisplayRotation))
                        .append(" mWindowingMode=")
                        .append(windowingModeToString(this.mWindowingMode))
                        .append(" mActivityType=")
                        .append(activityTypeToString(this.mActivityType))
                        .append(" mAlwaysOnTop=")
                        .append(alwaysOnTopToString(this.mAlwaysOnTop))
                        .append(" mRotation=");
        if (this.mRotation != -1) {
            str = Surface.rotationToString(this.mRotation);
        }
        return append2.append(str)
                .append(" mStageConfig=")
                .append(stageConfigToString(this.mStage))
                .append(
                        CoreRune.MW_EMBED_ACTIVITY_MODE
                                ? " mEmbedActivityMode="
                                        + embedActivityModeToString(this.mEmbedActivityMode)
                                : "")
                .append(" mPopOver=")
                .append(popOverStateToString(this.mPopOverState))
                .append(
                        CoreRune.MW_SPLIT_FLEX_PANEL_MODE
                                ? " mFlexPanelMode=" + flexPanelModeToString(this.mFlexPanelMode)
                                : "")
                .append(compatSandboxInfoToString())
                .append(" mDexTaskDockingState=")
                .append(dexTaskDockingStateToString(this.mDexTaskDockingState))
                .append("")
                .append(
                        CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING
                                ? " mFreeformTaskPinningState="
                                        + freeformTaskPinningToString(
                                                this.mFreeformTaskPinningState)
                                : "")
                .append("}")
                .toString();
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

    public void dumpDebug(ProtoOutputStream protoOutputStream, long fieldId) {
        long token = protoOutputStream.start(fieldId);
        if (this.mAppBounds != null) {
            this.mAppBounds.dumpDebug(protoOutputStream, 1146756268033L);
        }
        protoOutputStream.write(1120986464258L, this.mWindowingMode);
        protoOutputStream.write(1120986464259L, this.mActivityType);
        this.mBounds.dumpDebug(protoOutputStream, 1146756268036L);
        this.mMaxBounds.dumpDebug(protoOutputStream, 1146756268037L);
        protoOutputStream.end(token);
    }

    public void readFromProto(ProtoInputStream proto, long fieldId)
            throws IOException, WireTypeMismatchException {
        long token = proto.start(fieldId);
        while (proto.nextField() != -1) {
            try {
                switch (proto.getFieldNumber()) {
                    case 1:
                        this.mAppBounds = new Rect();
                        this.mAppBounds.readFromProto(proto, 1146756268033L);
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

    public boolean canResizeTask() {
        return this.mWindowingMode == 5 || this.mWindowingMode == 6;
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
        if (this.mWindowingMode == 2 || this.mActivityType == 5) {
            return true;
        }
        if (this.mAlwaysOnTop != 1) {
            return false;
        }
        return this.mWindowingMode == 5 || this.mWindowingMode == 6;
    }

    public boolean useWindowFrameForBackdrop() {
        return this.mWindowingMode == 5 || this.mWindowingMode == 2;
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

    public static boolean areConfigurationsEqualForDisplay(
            Configuration newConfig, Configuration oldConfig) {
        return newConfig
                        .windowConfiguration
                        .getMaxBounds()
                        .equals(oldConfig.windowConfiguration.getMaxBounds())
                && newConfig.windowConfiguration.getDisplayRotation()
                        == oldConfig.windowConfiguration.getDisplayRotation();
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

    public void setCompatSandboxValues(WindowConfiguration other) {
        setCompatSandboxValues(
                other.mCompatSandboxFlags, other.mCompatSandboxScale, other.mCompatSandboxBounds);
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
        if ((this.mCompatSandboxFlags & 1) != 0) {
            return 0;
        }
        return this.mCompatSandboxFlags;
    }

    public float getCompatSandboxInvScale() {
        if (getCompatSandboxFlags() != 0 && this.mCompatSandboxScale != -1.0f) {
            float scale = 1.0f / this.mCompatSandboxScale;
            if (scale > 0.0f) {
                return scale;
            }
        }
        return 1.0f;
    }

    public Rect getCompatSandboxBounds() {
        if (this.mCompatSandboxBounds == null || this.mCompatSandboxBounds.isEmpty()) {
            return this.mBounds;
        }
        return this.mCompatSandboxBounds;
    }

    public Rect getCompatSandboxScaledBounds() {
        if (this.mCompatSandboxScaledBounds == null) {
            this.mCompatSandboxScaledBounds = new Rect();
        }
        this.mCompatSandboxScaledBounds.set(getCompatSandboxBounds());
        float scale = getCompatSandboxInvScale();
        if (scale != 1.0f) {
            scaleBounds(scale, this.mCompatSandboxScaledBounds);
        }
        return this.mCompatSandboxScaledBounds;
    }

    private String compatSandboxInfoToString() {
        return (this.mCompatSandboxFlags != 0
                        ? " mCompatSandboxFlags=0x" + this.mCompatSandboxFlags
                        : "")
                + (this.mCompatSandboxScale != 1.0f
                        ? " mCompatSandboxScale=" + this.mCompatSandboxScale
                        : "")
                + (this.mCompatSandboxBounds != null
                        ? " mCompatSandboxScale=" + this.mCompatSandboxBounds
                        : "");
    }

    public void overrideUndefinedFrom(WindowConfiguration delta) {
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING
                && this.mFreeformTaskPinningState == 0
                && delta.mFreeformTaskPinningState == 1) {
            this.mFreeformTaskPinningState = delta.mFreeformTaskPinningState;
        }
        if (this.mPopOverState == 0 && delta.mPopOverState == 2) {
            this.mPopOverState = delta.mPopOverState;
        }
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
}
