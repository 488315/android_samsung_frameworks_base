package android.app;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.input.KeyboardLayout;
import android.media.quality.PictureProfile;
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
        public WindowConfiguration createFromParcel(Parcel parcel) {
            return new WindowConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowConfiguration[] newArray(int i) {
            return new WindowConfiguration[i];
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
    public static final int POP_OVER_OFF = 2;
    public static final int POP_OVER_ON = 1;
    public static final int POP_OVER_ON_WITHOUT_OUTLINE_EFFECT = 3;
    public static final int POP_OVER_UNDEFINED = 0;
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
    private final Rect mMaxBounds;
    private boolean mOverlappingWithCutout;
    private int mPopOverState;
    private int mRotation;
    private int mStage;
    private int mWindowingMode;

    @Retention(RetentionPolicy.SOURCE)
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

    public @interface StagePosition {
    }

    public @interface StageType {
    }

    public @interface WindowConfig {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WindowingMode {
    }

    public static boolean inMultiWindowMode(int i) {
        return (i == 1 || i == 0) ? false : true;
    }

    public static boolean isDexTaskDocking(int i) {
        return i == 1 || i == 2;
    }

    public static boolean isFloating(int i) {
        return i == 5 || i == 2;
    }

    public static boolean isSplitScreenWindowingMode(int i) {
        return (i & 7) != 0;
    }

    public static boolean supportSplitScreenWindowingMode(int i) {
        return (i == 4 || i == 5) ? false : true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setPopOverState(int i) {
        this.mPopOverState = i;
    }

    public int getPopOverState() {
        return this.mPopOverState;
    }

    public boolean isPopOver() {
        int i = this.mPopOverState;
        return i == 1 || i == 3;
    }

    public boolean isPopOverWithoutOutlineEffect() {
        return this.mPopOverState == 3;
    }

    private static String popOverStateToString(int i) {
        if (i == 0) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i == 1) {
            return "on";
        }
        if (i == 2) {
            return "off";
        }
        if (i == 3) {
            return "on-without-outline-effect";
        }
        return String.valueOf(i);
    }

    public void setOverlappingWithCutout(boolean z) {
        this.mOverlappingWithCutout = z | this.mOverlappingWithCutout;
    }

    public boolean isOverlappingWithCutout() {
        return this.mOverlappingWithCutout;
    }

    public void setEmbedActivityMode(int i) {
        this.mEmbedActivityMode = i;
    }

    public int getEmbedActivityMode() {
        return this.mEmbedActivityMode;
    }

    public boolean isEmbedded() {
        int i = this.mEmbedActivityMode;
        if (i != 1) {
            return i == 2 || i == 3 || i == 4 || i == 5;
        }
        return !inMultiWindowMode(this.mWindowingMode);
    }

    public void setDexTaskDockingState(int i) {
        this.mDexTaskDockingState = i;
    }

    public int getDexTaskDockingState() {
        return this.mDexTaskDockingState;
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
        this.mDexTaskDockingState = -1;
        unset();
    }

    public WindowConfiguration(WindowConfiguration windowConfiguration) {
        this.mBounds = new Rect();
        this.mMaxBounds = new Rect();
        this.mDisplayRotation = -1;
        this.mRotation = -1;
        this.mPopOverState = 0;
        this.mOverlappingWithCutout = false;
        this.mFlexPanelMode = 0;
        this.mCompatSandboxFlags = 0;
        this.mCompatSandboxScale = -1.0f;
        this.mDexTaskDockingState = -1;
        setTo(windowConfiguration);
    }

    private WindowConfiguration(Parcel parcel) {
        this.mBounds = new Rect();
        this.mMaxBounds = new Rect();
        this.mDisplayRotation = -1;
        this.mRotation = -1;
        this.mPopOverState = 0;
        this.mOverlappingWithCutout = false;
        this.mFlexPanelMode = 0;
        this.mCompatSandboxFlags = 0;
        this.mCompatSandboxScale = -1.0f;
        this.mDexTaskDockingState = -1;
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mBounds.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mAppBounds, i);
        this.mMaxBounds.writeToParcel(parcel, i);
        parcel.writeInt(this.mWindowingMode);
        parcel.writeInt(this.mActivityType);
        parcel.writeInt(this.mAlwaysOnTop);
        parcel.writeInt(this.mRotation);
        parcel.writeInt(this.mDisplayRotation);
        parcel.writeInt(this.mStage);
        if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
            parcel.writeInt(this.mEmbedActivityMode);
        }
        parcel.writeInt(this.mPopOverState);
        parcel.writeBoolean(this.mOverlappingWithCutout);
        parcel.writeInt(this.mCompatSandboxFlags);
        parcel.writeFloat(this.mCompatSandboxScale);
        parcel.writeTypedObject(this.mCompatSandboxBounds, i);
    }

    public void readFromParcel(Parcel parcel) {
        this.mBounds.readFromParcel(parcel);
        this.mAppBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.mMaxBounds.readFromParcel(parcel);
        this.mWindowingMode = parcel.readInt();
        this.mActivityType = parcel.readInt();
        this.mAlwaysOnTop = parcel.readInt();
        this.mRotation = parcel.readInt();
        this.mDisplayRotation = parcel.readInt();
        this.mStage = parcel.readInt();
        if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
            this.mEmbedActivityMode = parcel.readInt();
        }
        this.mPopOverState = parcel.readInt();
        this.mOverlappingWithCutout = parcel.readBoolean();
        this.mCompatSandboxFlags = parcel.readInt();
        this.mCompatSandboxScale = parcel.readFloat();
        this.mCompatSandboxBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
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

    public void setMaxBounds(int i, int i2, int i3, int i4) {
        this.mMaxBounds.set(i, i2, i3, i4);
    }

    public void setDisplayRotation(int i) {
        this.mDisplayRotation = i;
    }

    public void setAlwaysOnTop(boolean z) {
        this.mAlwaysOnTop = z ? 1 : 2;
    }

    public void unsetAlwaysOnTop() {
        this.mAlwaysOnTop = 0;
    }

    private void setAlwaysOnTop(int i) {
        this.mAlwaysOnTop = i;
    }

    public void setAppBounds(int i, int i2, int i3, int i4) {
        if (this.mAppBounds == null) {
            this.mAppBounds = new Rect();
        }
        this.mAppBounds.set(i, i2, i3, i4);
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

    public void setRotation(int i) {
        this.mRotation = i;
    }

    public void setWindowingMode(int i) {
        this.mWindowingMode = i;
    }

    public int getWindowingMode() {
        return this.mWindowingMode;
    }

    public void setActivityType(int i) {
        if (this.mActivityType == i) {
            return;
        }
        if (ActivityThread.isSystem() && this.mActivityType != 0 && i != 0) {
            throw new IllegalStateException("Can't change activity type once set: " + this + " activityType=" + activityTypeToString(i));
        }
        this.mActivityType = i;
    }

    public int getActivityType() {
        return this.mActivityType;
    }

    public void setStage(int i) {
        this.mStage = i;
    }

    public int getStage() {
        return this.mStage;
    }

    public void setStageType(int i) {
        int i2 = this.mStage;
        this.mStage = (i & 7) | (i2 & i2 & 120);
    }

    public int getStageType() {
        return this.mStage & 7;
    }

    public void setStagePosition(int i) {
        int i2 = this.mStage;
        this.mStage = (i & 120) | (i2 & i2 & 7);
    }

    public int getStagePosition() {
        return this.mStage & 120;
    }

    public boolean isSplitScreen() {
        return (this.mStage & 7) != 0;
    }

    public static boolean isSplitScreenWindowingMode(WindowConfiguration windowConfiguration) {
        return (windowConfiguration.mStage & 7) != 0;
    }

    public void setTo(WindowConfiguration windowConfiguration) {
        setBounds(windowConfiguration.mBounds);
        setAppBounds(windowConfiguration.mAppBounds);
        setMaxBounds(windowConfiguration.mMaxBounds);
        setDisplayRotation(windowConfiguration.mDisplayRotation);
        setWindowingMode(windowConfiguration.mWindowingMode);
        setActivityType(windowConfiguration.mActivityType);
        setAlwaysOnTop(windowConfiguration.mAlwaysOnTop);
        setRotation(windowConfiguration.mRotation);
        setStage(windowConfiguration.mStage);
        if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
            setEmbedActivityMode(windowConfiguration.mEmbedActivityMode);
        }
        setPopOverState(windowConfiguration.mPopOverState);
        setOverlappingWithCutout(windowConfiguration.mOverlappingWithCutout);
        setCompatSandboxValues(windowConfiguration.mCompatSandboxFlags, windowConfiguration.mCompatSandboxScale, windowConfiguration.mCompatSandboxBounds);
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
        setOverlappingWithCutout(false);
        setDexTaskDockingState(-1);
        setEmbedActivityMode(0);
    }

    public void scale(float f) {
        scaleBounds(f, this.mBounds);
        scaleBounds(f, this.mMaxBounds);
        Rect rect = this.mAppBounds;
        if (rect != null) {
            scaleBounds(f, rect);
        }
    }

    private static void scaleBounds(float f, Rect rect) {
        int iWidth = rect.width();
        int iHeight = rect.height();
        rect.left = (int) ((rect.left * f) + 0.5f);
        rect.top = (int) ((rect.top * f) + 0.5f);
        rect.right = rect.left + ((int) ((iWidth * f) + 0.5f));
        rect.bottom = rect.top + ((int) ((iHeight * f) + 0.5f));
    }

    public int updateFrom(WindowConfiguration windowConfiguration) {
        int i;
        int i2;
        boolean z = true;
        boolean z2 = false;
        if (windowConfiguration.mBounds.isEmpty() || windowConfiguration.mBounds.equals(this.mBounds)) {
            i = 0;
        } else {
            setBounds(windowConfiguration.mBounds);
            i = 1;
        }
        Rect rect = windowConfiguration.mAppBounds;
        if (rect != null && !rect.equals(this.mAppBounds)) {
            i |= 2;
            setAppBounds(windowConfiguration.mAppBounds);
        }
        if (!windowConfiguration.mMaxBounds.isEmpty() && !windowConfiguration.mMaxBounds.equals(this.mMaxBounds)) {
            i |= 4;
            setMaxBounds(windowConfiguration.mMaxBounds);
        }
        int i3 = windowConfiguration.mWindowingMode;
        if (i3 != 0 && this.mWindowingMode != i3) {
            i |= 8;
            setWindowingMode(i3);
        }
        int i4 = windowConfiguration.mActivityType;
        if (i4 != 0 && this.mActivityType != i4) {
            i |= 16;
            setActivityType(i4);
        }
        int i5 = windowConfiguration.mAlwaysOnTop;
        if (i5 != 0 && this.mAlwaysOnTop != i5) {
            i |= 32;
            setAlwaysOnTop(i5);
        }
        int i6 = windowConfiguration.mRotation;
        if (i6 != -1 && i6 != this.mRotation) {
            i |= 64;
            setRotation(i6);
        }
        int i7 = windowConfiguration.mDisplayRotation;
        if (i7 != -1 && i7 != this.mDisplayRotation) {
            i |= 128;
            setDisplayRotation(i7);
        }
        if (windowConfiguration.mStage != 0 || (i & 8) != 0) {
            int stageType = windowConfiguration.getStageType();
            if (getStageType() != stageType) {
                i |= 1048576;
                setStageType(stageType);
            }
            int stagePosition = windowConfiguration.getStagePosition();
            if (getStagePosition() != stagePosition) {
                i |= 2097152;
                setStagePosition(stagePosition);
            }
        }
        if (CoreRune.MW_EMBED_ACTIVITY_MODE && (i2 = windowConfiguration.mEmbedActivityMode) != 0 && this.mEmbedActivityMode != i2) {
            setEmbedActivityMode(i2);
        }
        int i8 = windowConfiguration.mPopOverState;
        if (i8 != 0 && this.mPopOverState != i8) {
            setPopOverState(i8);
        }
        boolean z3 = windowConfiguration.mOverlappingWithCutout;
        if (z3 && this.mOverlappingWithCutout != z3) {
            setOverlappingWithCutout(z3);
        }
        int i9 = this.mCompatSandboxFlags;
        int i10 = windowConfiguration.mCompatSandboxFlags;
        if (i10 != 0 && i9 != i10) {
            z2 = true;
            i9 = i10;
        }
        float f = this.mCompatSandboxScale;
        float f2 = windowConfiguration.mCompatSandboxScale;
        if (f2 != -1.0f && f != f2) {
            z2 = true;
            f = f2;
        }
        Rect rect2 = this.mCompatSandboxBounds;
        Rect rect3 = windowConfiguration.mCompatSandboxBounds;
        if (rect3 == null || rect3.equals(rect2)) {
            z = z2;
        } else {
            rect2 = windowConfiguration.mCompatSandboxBounds;
        }
        if (!z) {
            return i;
        }
        int i11 = 33554432 | i;
        setCompatSandboxValues(i9, f, rect2);
        return i11;
    }

    public void setTo(WindowConfiguration windowConfiguration, int i) {
        if ((i & 1) != 0) {
            setBounds(windowConfiguration.mBounds);
        }
        if ((i & 2) != 0) {
            setAppBounds(windowConfiguration.mAppBounds);
        }
        if ((i & 4) != 0) {
            setMaxBounds(windowConfiguration.mMaxBounds);
        }
        if ((i & 8) != 0) {
            setWindowingMode(windowConfiguration.mWindowingMode);
        }
        if ((i & 16) != 0) {
            setActivityType(windowConfiguration.mActivityType);
        }
        if ((i & 32) != 0) {
            setAlwaysOnTop(windowConfiguration.mAlwaysOnTop);
        }
        if ((i & 64) != 0) {
            setRotation(windowConfiguration.mRotation);
        }
        if ((i & 128) != 0) {
            setDisplayRotation(windowConfiguration.mDisplayRotation);
        }
        if ((1048576 & i) != 0) {
            setStageType(windowConfiguration.getStageType());
        }
        if ((2097152 & i) != 0) {
            setStagePosition(windowConfiguration.getStagePosition());
        }
        if (!CoreRune.MW_EMBED_ACTIVITY_MODE || (i & 8388608) == 0) {
            return;
        }
        setEmbedActivityMode(windowConfiguration.mEmbedActivityMode);
    }

    public long diff(WindowConfiguration windowConfiguration, boolean z) {
        Rect rect;
        Rect rect2;
        Rect rect3;
        Rect rect4;
        long j = !this.mBounds.equals(windowConfiguration.mBounds) ? 1L : 0L;
        if ((z || windowConfiguration.mAppBounds != null) && (rect = this.mAppBounds) != (rect2 = windowConfiguration.mAppBounds) && (rect == null || !rect.equals(rect2))) {
            j |= 2;
        }
        if (!this.mMaxBounds.equals(windowConfiguration.mMaxBounds)) {
            j |= 4;
        }
        if ((z || windowConfiguration.mWindowingMode != 0) && this.mWindowingMode != windowConfiguration.mWindowingMode) {
            j |= 8;
        }
        if ((z || windowConfiguration.mActivityType != 0) && this.mActivityType != windowConfiguration.mActivityType) {
            j |= 16;
        }
        if ((z || windowConfiguration.mAlwaysOnTop != 0) && this.mAlwaysOnTop != windowConfiguration.mAlwaysOnTop) {
            j |= 32;
        }
        if ((z || windowConfiguration.mRotation != -1) && this.mRotation != windowConfiguration.mRotation) {
            j |= 64;
        }
        if ((z || windowConfiguration.mDisplayRotation != -1) && this.mDisplayRotation != windowConfiguration.mDisplayRotation) {
            j |= 128;
        }
        if (z || windowConfiguration.mStage != 0) {
            if (getStageType() != windowConfiguration.getStageType()) {
                j |= 1048576;
            }
            if (getStagePosition() != windowConfiguration.getStagePosition()) {
                j |= 2097152;
            }
        }
        if (CoreRune.MW_EMBED_ACTIVITY_MODE && ((z || windowConfiguration.mEmbedActivityMode != 0) && this.mEmbedActivityMode != windowConfiguration.mEmbedActivityMode)) {
            j |= 8388608;
        }
        return ((z || windowConfiguration.mCompatSandboxFlags != 0) && this.mCompatSandboxFlags != windowConfiguration.mCompatSandboxFlags) ? j | 33554432 : ((z || windowConfiguration.mCompatSandboxScale != -1.0f) && this.mCompatSandboxScale != windowConfiguration.mCompatSandboxScale) ? j | 33554432 : ((z || windowConfiguration.mCompatSandboxBounds != null) && (rect3 = this.mCompatSandboxBounds) != (rect4 = windowConfiguration.mCompatSandboxBounds) && (rect3 == null || !rect3.equals(rect4))) ? j | 33554432 : j;
    }

    @Override // java.lang.Comparable
    public int compareTo(WindowConfiguration windowConfiguration) {
        int i;
        Rect rect = this.mAppBounds;
        if (rect == null && windowConfiguration.mAppBounds != null) {
            return 1;
        }
        if (rect != null && windowConfiguration.mAppBounds == null) {
            return -1;
        }
        if (rect != null && windowConfiguration.mAppBounds != null) {
            int i2 = rect.left - windowConfiguration.mAppBounds.left;
            if (i2 != 0) {
                return i2;
            }
            int i3 = this.mAppBounds.top - windowConfiguration.mAppBounds.top;
            if (i3 != 0) {
                return i3;
            }
            int i4 = this.mAppBounds.right - windowConfiguration.mAppBounds.right;
            if (i4 != 0) {
                return i4;
            }
            int i5 = this.mAppBounds.bottom - windowConfiguration.mAppBounds.bottom;
            if (i5 != 0) {
                return i5;
            }
        }
        int i6 = this.mMaxBounds.left - windowConfiguration.mMaxBounds.left;
        if (i6 != 0) {
            return i6;
        }
        int i7 = this.mMaxBounds.top - windowConfiguration.mMaxBounds.top;
        if (i7 != 0) {
            return i7;
        }
        int i8 = this.mMaxBounds.right - windowConfiguration.mMaxBounds.right;
        if (i8 != 0) {
            return i8;
        }
        int i9 = this.mMaxBounds.bottom - windowConfiguration.mMaxBounds.bottom;
        if (i9 != 0) {
            return i9;
        }
        int i10 = this.mBounds.left - windowConfiguration.mBounds.left;
        if (i10 != 0) {
            return i10;
        }
        int i11 = this.mBounds.top - windowConfiguration.mBounds.top;
        if (i11 != 0) {
            return i11;
        }
        int i12 = this.mBounds.right - windowConfiguration.mBounds.right;
        if (i12 != 0) {
            return i12;
        }
        int i13 = this.mBounds.bottom - windowConfiguration.mBounds.bottom;
        if (i13 != 0) {
            return i13;
        }
        int i14 = this.mWindowingMode - windowConfiguration.mWindowingMode;
        if (i14 != 0) {
            return i14;
        }
        int i15 = this.mActivityType - windowConfiguration.mActivityType;
        if (i15 != 0) {
            return i15;
        }
        int i16 = this.mAlwaysOnTop - windowConfiguration.mAlwaysOnTop;
        if (i16 != 0) {
            return i16;
        }
        int i17 = this.mRotation - windowConfiguration.mRotation;
        if (i17 != 0) {
            return i17;
        }
        int i18 = this.mDisplayRotation - windowConfiguration.mDisplayRotation;
        if (i18 != 0) {
            return i18;
        }
        int i19 = this.mStage - windowConfiguration.mStage;
        return i19 != 0 ? i19 : (!CoreRune.MW_EMBED_ACTIVITY_MODE || (i = this.mEmbedActivityMode - windowConfiguration.mEmbedActivityMode) == 0) ? this.mPopOverState - windowConfiguration.mPopOverState : i;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return (obj instanceof WindowConfiguration) && compareTo((WindowConfiguration) obj) == 0;
    }

    public int hashCode() {
        return (((((((((((((((Objects.hashCode(this.mAppBounds) * 31) + Objects.hashCode(this.mBounds)) * 31) + Objects.hashCode(this.mMaxBounds)) * 31) + this.mWindowingMode) * 31) + this.mActivityType) * 31) + this.mAlwaysOnTop) * 31) + this.mRotation) * 31) + this.mDisplayRotation) * 31) + this.mStage;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder("{ mBounds=");
        sb.append(this.mBounds);
        sb.append(" mAppBounds=");
        sb.append(this.mAppBounds);
        sb.append(" mMaxBounds=");
        sb.append(this.mMaxBounds);
        sb.append(" mDisplayRotation=");
        int i = this.mRotation;
        String strRotationToString = KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        sb.append(i == -1 ? KeyboardLayout.LAYOUT_TYPE_UNDEFINED : Surface.rotationToString(this.mDisplayRotation));
        sb.append(" mWindowingMode=");
        sb.append(windowingModeToString(this.mWindowingMode));
        sb.append(" mActivityType=");
        sb.append(activityTypeToString(this.mActivityType));
        sb.append(" mAlwaysOnTop=");
        sb.append(alwaysOnTopToString(this.mAlwaysOnTop));
        sb.append(" mRotation=");
        int i2 = this.mRotation;
        if (i2 != -1) {
            strRotationToString = Surface.rotationToString(i2);
        }
        sb.append(strRotationToString);
        sb.append(" mStageConfig=");
        sb.append(stageConfigToString(this.mStage));
        if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
            str = " mEmbedActivityMode=" + embedActivityModeToString(this.mEmbedActivityMode);
        } else {
            str = "";
        }
        sb.append(str);
        sb.append(" mPopOver=");
        sb.append(popOverStateToString(this.mPopOverState));
        sb.append(" mOverlappingWithCutout=");
        sb.append(this.mOverlappingWithCutout);
        sb.append("");
        sb.append(compatSandboxInfoToString());
        sb.append("}");
        return sb.toString();
    }

    public static String dexTaskDockingStateToString(int i) {
        if (i == -1) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i == 0) {
            return "none";
        }
        if (i == 1) {
            return "left";
        }
        if (i == 2) {
            return "right";
        }
        return String.valueOf(i);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        Rect rect = this.mAppBounds;
        if (rect != null) {
            rect.dumpDebug(protoOutputStream, 1146756268033L);
        }
        protoOutputStream.write(1120986464258L, this.mWindowingMode);
        protoOutputStream.write(1120986464259L, this.mActivityType);
        this.mBounds.dumpDebug(protoOutputStream, 1146756268036L);
        this.mMaxBounds.dumpDebug(protoOutputStream, 1146756268037L);
        protoOutputStream.end(jStart);
    }

    public void readFromProto(ProtoInputStream protoInputStream, long j) throws WireTypeMismatchException, IOException {
        long jStart = protoInputStream.start(j);
        while (protoInputStream.nextField() != -1) {
            try {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    Rect rect = new Rect();
                    this.mAppBounds = rect;
                    rect.readFromProto(protoInputStream, 1146756268033L);
                } else if (fieldNumber == 2) {
                    this.mWindowingMode = protoInputStream.readInt(1120986464258L);
                } else if (fieldNumber == 3) {
                    this.mActivityType = protoInputStream.readInt(1120986464259L);
                } else if (fieldNumber == 4) {
                    this.mBounds.readFromProto(protoInputStream, 1146756268036L);
                } else if (fieldNumber == 5) {
                    this.mMaxBounds.readFromProto(protoInputStream, 1146756268037L);
                }
            } finally {
                protoInputStream.end(jStart);
            }
        }
    }

    public boolean hasWindowShadow() {
        return this.mWindowingMode != 6 && tasksAreFloating();
    }

    public boolean canResizeTask() {
        int i = this.mWindowingMode;
        return i == 5 || i == 6;
    }

    public boolean tasksAreFloating() {
        return isFloating(this.mWindowingMode);
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

    public static boolean areConfigurationsEqualForDisplay(Configuration configuration, Configuration configuration2) {
        return configuration.windowConfiguration.getMaxBounds().equals(configuration2.windowConfiguration.getMaxBounds()) && configuration.windowConfiguration.getDisplayRotation() == configuration2.windowConfiguration.getDisplayRotation();
    }

    public static String windowingModeToString(int i) {
        if (i == 0) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i == 1) {
            return "fullscreen";
        }
        if (i == 2) {
            return ContactsContract.ContactOptionsColumns.PINNED;
        }
        if (i == 5) {
            return "freeform";
        }
        if (i == 6) {
            return "multi-window";
        }
        return String.valueOf(i);
    }

    public static String activityTypeToString(int i) {
        if (i == 0) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i == 1) {
            return PictureProfile.NAME_STANDARD;
        }
        if (i == 2) {
            return "home";
        }
        if (i == 3) {
            return "recents";
        }
        if (i == 4) {
            return Settings.Secure.ASSISTANT;
        }
        if (i == 5) {
            return "dream";
        }
        return String.valueOf(i);
    }

    public static String alwaysOnTopToString(int i) {
        if (i == 0) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i == 1) {
            return "on";
        }
        if (i == 2) {
            return "off";
        }
        return String.valueOf(i);
    }

    public static String stageConfigToString(int i) {
        StringBuilder sb = new StringBuilder(32);
        int i2 = i & 7;
        if (i2 == 0) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i2 == 1) {
            sb.append("main/");
        } else if (i2 == 2) {
            sb.append("side/");
        } else if (i2 == 4) {
            sb.append("cell/");
        }
        int i3 = i & 120;
        if (i3 == 8) {
            sb.append("left");
        } else if (i3 == 16) {
            sb.append(GenerateXML.TOP);
        } else if (i3 == 24) {
            sb.append("left-top");
        } else if (i3 == 32) {
            sb.append("right");
        } else if (i3 == 48) {
            sb.append("right-top");
        } else if (i3 == 64) {
            sb.append(GenerateXML.BOTTOM);
        } else if (i3 == 72) {
            sb.append("left-bottom");
        } else if (i3 == 96) {
            sb.append("right-bottom");
        }
        return sb.toString();
    }

    public String getStageTypeToString() {
        StringBuilder sb = new StringBuilder(32);
        int i = this.mStage & 7;
        if (i == 0) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i == 1) {
            sb.append("main");
        } else if (i == 2) {
            sb.append("side");
        } else if (i == 4) {
            sb.append("cell");
        }
        return sb.toString();
    }

    public String getStagePositionToString() {
        return stagePositionToString(this.mStage);
    }

    public static String stagePositionToString(int i) {
        StringBuilder sb = new StringBuilder(32);
        int i2 = i & 120;
        if (i2 == 0) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i2 == 8) {
            sb.append("left");
        } else if (i2 == 16) {
            sb.append(GenerateXML.TOP);
        } else if (i2 == 24) {
            sb.append("left-top");
        } else if (i2 == 32) {
            sb.append("right");
        } else if (i2 == 48) {
            sb.append("right-top");
        } else if (i2 == 64) {
            sb.append(GenerateXML.BOTTOM);
        } else if (i2 == 72) {
            sb.append("left-bottom");
        } else if (i2 == 96) {
            sb.append("right-bottom");
        }
        return sb.toString();
    }

    public static String embedActivityModeToString(int i) {
        if (i == 0) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i == 1) {
            return "full";
        }
        if (i == 2) {
            return "left";
        }
        if (i == 3) {
            return "right";
        }
        if (i == 4) {
            return GenerateXML.TOP;
        }
        if (i == 5) {
            return GenerateXML.BOTTOM;
        }
        return String.valueOf(i);
    }

    public static String flexPanelModeToString(int i) {
        if (i == 0) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i == 1) {
            return "on";
        }
        if (i == 2) {
            return "off";
        }
        return String.valueOf(i);
    }

    public void setFlexPanelMode(int i) {
        this.mFlexPanelMode = i;
    }

    public boolean isFlexPanelEnabled() {
        return this.mFlexPanelMode == 1;
    }

    public void setCompatSandboxValues(WindowConfiguration windowConfiguration) {
        setCompatSandboxValues(windowConfiguration.mCompatSandboxFlags, windowConfiguration.mCompatSandboxScale, windowConfiguration.mCompatSandboxBounds);
    }

    public void setCompatSandboxValues(int i, float f, Rect rect) {
        this.mCompatSandboxFlags = i;
        this.mCompatSandboxScale = f;
        if (rect == null) {
            this.mCompatSandboxBounds = null;
            return;
        }
        if (this.mCompatSandboxBounds == null) {
            this.mCompatSandboxBounds = new Rect();
        }
        this.mCompatSandboxBounds.set(rect);
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
                float f2 = 1.0f / f;
                if (f2 > 0.0f) {
                    return f2;
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

    public Rect getCompatSandboxScaledBounds() {
        if (this.mCompatSandboxScaledBounds == null) {
            this.mCompatSandboxScaledBounds = new Rect();
        }
        this.mCompatSandboxScaledBounds.set(getCompatSandboxBounds());
        float compatSandboxInvScale = getCompatSandboxInvScale();
        if (compatSandboxInvScale != 1.0f) {
            scaleBounds(compatSandboxInvScale, this.mCompatSandboxScaledBounds);
        }
        return this.mCompatSandboxScaledBounds;
    }

    private String compatSandboxInfoToString() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        String str3 = "";
        if (this.mCompatSandboxFlags != 0) {
            str = " mCompatSandboxFlags=0x" + Integer.toHexString(this.mCompatSandboxFlags);
        } else {
            str = "";
        }
        sb.append(str);
        float f = this.mCompatSandboxScale;
        if (f == 1.0f || f == -1.0f) {
            str2 = "";
        } else {
            str2 = " mCompatSandboxScale=" + this.mCompatSandboxScale;
        }
        sb.append(str2);
        if (this.mCompatSandboxBounds != null) {
            str3 = " mCompatSandboxScale=" + this.mCompatSandboxBounds;
        }
        sb.append(str3);
        return sb.toString();
    }

    public void overrideUndefinedFrom(WindowConfiguration windowConfiguration) {
        int i;
        if (this.mPopOverState == 0 && (i = windowConfiguration.mPopOverState) == 2) {
            this.mPopOverState = i;
        }
    }
}
