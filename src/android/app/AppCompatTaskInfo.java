package android.app;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes.dex */
public class AppCompatTaskInfo implements Parcelable {
    public static final Parcelable.Creator<AppCompatTaskInfo> CREATOR = new Parcelable.Creator<AppCompatTaskInfo>() { // from class: android.app.AppCompatTaskInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppCompatTaskInfo createFromParcel(Parcel parcel) {
            return new AppCompatTaskInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppCompatTaskInfo[] newArray(int i) {
            return new AppCompatTaskInfo[i];
        }
    };
    private static final int FLAGS_COMPAT_UI_INTERESTED = 69611;
    private static final int FLAGS_ORGANIZER_INTERESTED = 69600;
    private static final int FLAG_BASE = 1;
    private static final int FLAG_ELIGIBLE_FOR_LETTERBOX_EDU = 2;
    private static final int FLAG_ELIGIBLE_FOR_USER_ASPECT_RATIO_BUTTON = 64;
    private static final int FLAG_ENABLE_RESTART_MENU_FOR_DISPLAY_MOVE = 1024;
    private static final int FLAG_FULLSCREEN_OVERRIDE_SYSTEM = 128;
    private static final int FLAG_FULLSCREEN_OVERRIDE_USER = 256;
    public static final int FLAG_HAS_MIN_ASPECT_RATIO_OVERRIDE = 512;
    private static final int FLAG_IN_SIZE_COMPAT = 8;
    private static final int FLAG_IS_FROM_LETTERBOX_DOUBLE_TAP = 32;
    private static final int FLAG_LETTERBOXED = 4;
    private static final int FLAG_LETTERBOX_DOUBLE_TAP_ENABLED = 16;
    private static final int FLAG_LETTERBOX_EDU_ENABLED = 1;
    public static final int FLAG_OPT_OUT_EDGE_TO_EDGE = 2048;
    private static final int FLAG_ROTATION_COMPAT_MODE_ENABLED = 65536;
    private static final int FLAG_UNDEFINED = 0;
    public CameraCompatTaskInfo cameraCompatTaskInfo;
    private int mTopActivityFlags;
    public boolean singleTapFromLetterbox;
    public final Rect topActivityAppBounds;
    public Rect topActivityBounds;
    public boolean topActivityInDisplayCompat;
    public Rect topActivityLetterboxBounds;
    public int topActivityLetterboxHeight;
    public int topActivityLetterboxHorizontalPosition;
    public int topActivityLetterboxVerticalPosition;
    public int topActivityLetterboxWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TopActivityFlag {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AppCompatTaskInfo() {
        this.topActivityLetterboxVerticalPosition = -1;
        this.topActivityLetterboxHorizontalPosition = -1;
        this.topActivityLetterboxWidth = -1;
        this.topActivityLetterboxHeight = -1;
        this.topActivityAppBounds = new Rect();
        this.cameraCompatTaskInfo = CameraCompatTaskInfo.create();
    }

    static AppCompatTaskInfo create() {
        return new AppCompatTaskInfo();
    }

    private AppCompatTaskInfo(Parcel parcel) {
        this.topActivityLetterboxVerticalPosition = -1;
        this.topActivityLetterboxHorizontalPosition = -1;
        this.topActivityLetterboxWidth = -1;
        this.topActivityLetterboxHeight = -1;
        this.topActivityAppBounds = new Rect();
        this.cameraCompatTaskInfo = CameraCompatTaskInfo.create();
        readFromParcel(parcel);
    }

    public boolean hasCompatUI() {
        return isTopActivityInSizeCompat() || eligibleForLetterboxEducation() || isLetterboxDoubleTapEnabled() || eligibleForUserAspectRatioButton() || isRestartMenuEnabledForDisplayMove();
    }

    public boolean isTopActivityPillarboxShaped() {
        return isTopActivityLetterboxed() && this.topActivityLetterboxWidth <= this.topActivityLetterboxHeight;
    }

    public boolean isLetterboxEducationEnabled() {
        return isTopActivityFlagEnabled(1);
    }

    public void setLetterboxEducationEnabled(boolean z) {
        setTopActivityFlag(1, z);
    }

    public boolean eligibleForLetterboxEducation() {
        return isTopActivityFlagEnabled(2);
    }

    public void setEligibleForLetterboxEducation(boolean z) {
        setTopActivityFlag(2, z);
    }

    public boolean eligibleForUserAspectRatioButton() {
        return isTopActivityFlagEnabled(64);
    }

    public void setEligibleForUserAspectRatioButton(boolean z) {
        setTopActivityFlag(64, z);
    }

    public boolean isLetterboxDoubleTapEnabled() {
        return isTopActivityFlagEnabled(16);
    }

    public void setLetterboxDoubleTapEnabled(boolean z) {
        setTopActivityFlag(16, z);
    }

    public boolean isFromLetterboxDoubleTap() {
        return isTopActivityFlagEnabled(32);
    }

    public void setIsFromLetterboxDoubleTap(boolean z) {
        setTopActivityFlag(32, z);
    }

    public boolean hasMultiTaskingCompatUi() {
        if (isRestartMenuEnabledForDisplayMove()) {
            return false;
        }
        if (isTopActivityInSizeCompat()) {
            return CoreRune.MT_APP_COMPAT_ROTATION_COMPAT_MODE && isRotationCompatModeEnabled();
        }
        return true;
    }

    public boolean isRotationCompatModeEnabled() {
        return isTopActivityFlagEnabled(65536);
    }

    public void setRotationCompatModeEnabled(boolean z) {
        setTopActivityFlag(65536, z);
    }

    public boolean isUserFullscreenOverrideEnabled() {
        return isTopActivityFlagEnabled(256);
    }

    public void setUserFullscreenOverrideEnabled(boolean z) {
        setTopActivityFlag(256, z);
    }

    public boolean isSystemFullscreenOverrideEnabled() {
        return isTopActivityFlagEnabled(128);
    }

    public void setSystemFullscreenOverrideEnabled(boolean z) {
        setTopActivityFlag(128, z);
    }

    public boolean isTopActivityInSizeCompat() {
        return isTopActivityFlagEnabled(8);
    }

    public void setTopActivityInSizeCompat(boolean z) {
        setTopActivityFlag(8, z);
    }

    public boolean isRestartMenuEnabledForDisplayMove() {
        return isTopActivityFlagEnabled(1024);
    }

    public void setRestartMenuEnabledForDisplayMove(boolean z) {
        setTopActivityFlag(1024, z);
    }

    public boolean isTopActivityLetterboxed() {
        return isTopActivityFlagEnabled(4);
    }

    public void setTopActivityLetterboxed(boolean z) {
        setTopActivityFlag(4, z);
    }

    public boolean hasMinAspectRatioOverride() {
        return isTopActivityFlagEnabled(512);
    }

    public void setHasMinAspectRatioOverride(boolean z) {
        setTopActivityFlag(512, z);
    }

    public void setOptOutEdgeToEdge(boolean z) {
        setTopActivityFlag(2048, z);
    }

    public boolean hasOptOutEdgeToEdge() {
        return isTopActivityFlagEnabled(2048);
    }

    public void clearTopActivityFlags() {
        this.mTopActivityFlags = 0;
    }

    public boolean equalsForTaskOrganizer(AppCompatTaskInfo appCompatTaskInfo) {
        return appCompatTaskInfo != null && (this.mTopActivityFlags & FLAGS_ORGANIZER_INTERESTED) == (FLAGS_ORGANIZER_INTERESTED & appCompatTaskInfo.mTopActivityFlags) && this.topActivityLetterboxVerticalPosition == appCompatTaskInfo.topActivityLetterboxVerticalPosition && this.topActivityLetterboxWidth == appCompatTaskInfo.topActivityLetterboxWidth && this.topActivityLetterboxHeight == appCompatTaskInfo.topActivityLetterboxHeight && this.topActivityAppBounds.equals(appCompatTaskInfo.topActivityAppBounds) && this.topActivityLetterboxHorizontalPosition == appCompatTaskInfo.topActivityLetterboxHorizontalPosition && this.cameraCompatTaskInfo.equalsForTaskOrganizer(appCompatTaskInfo.cameraCompatTaskInfo);
    }

    public boolean equalsForCompatUi(AppCompatTaskInfo appCompatTaskInfo) {
        return appCompatTaskInfo != null && (this.mTopActivityFlags & FLAGS_COMPAT_UI_INTERESTED) == (FLAGS_COMPAT_UI_INTERESTED & appCompatTaskInfo.mTopActivityFlags) && this.topActivityLetterboxVerticalPosition == appCompatTaskInfo.topActivityLetterboxVerticalPosition && this.topActivityLetterboxHorizontalPosition == appCompatTaskInfo.topActivityLetterboxHorizontalPosition && this.topActivityLetterboxWidth == appCompatTaskInfo.topActivityLetterboxWidth && this.topActivityLetterboxHeight == appCompatTaskInfo.topActivityLetterboxHeight && this.topActivityAppBounds.equals(appCompatTaskInfo.topActivityAppBounds) && this.cameraCompatTaskInfo.equalsForCompatUi(appCompatTaskInfo.cameraCompatTaskInfo);
    }

    void readFromParcel(Parcel parcel) {
        this.mTopActivityFlags = parcel.readInt();
        this.topActivityLetterboxVerticalPosition = parcel.readInt();
        this.topActivityLetterboxHorizontalPosition = parcel.readInt();
        this.topActivityLetterboxWidth = parcel.readInt();
        this.topActivityLetterboxHeight = parcel.readInt();
        this.topActivityAppBounds.set((Rect) Objects.requireNonNull((Rect) parcel.readTypedObject(Rect.CREATOR)));
        this.topActivityLetterboxBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.cameraCompatTaskInfo = (CameraCompatTaskInfo) parcel.readTypedObject(CameraCompatTaskInfo.CREATOR);
        this.topActivityBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        if (CoreRune.MT_APP_COMPAT_CONFIGURATION || CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT_CONFIGURATION) {
            this.singleTapFromLetterbox = parcel.readBoolean();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTopActivityFlags);
        parcel.writeInt(this.topActivityLetterboxVerticalPosition);
        parcel.writeInt(this.topActivityLetterboxHorizontalPosition);
        parcel.writeInt(this.topActivityLetterboxWidth);
        parcel.writeInt(this.topActivityLetterboxHeight);
        parcel.writeTypedObject(this.topActivityAppBounds, i);
        parcel.writeTypedObject(this.topActivityLetterboxBounds, i);
        parcel.writeTypedObject(this.cameraCompatTaskInfo, i);
        parcel.writeTypedObject(this.topActivityBounds, i);
        if (CoreRune.MT_APP_COMPAT_CONFIGURATION || CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT_CONFIGURATION) {
            parcel.writeBoolean(this.singleTapFromLetterbox);
        }
    }

    public String toString() {
        return "AppCompatTaskInfo { topActivityInSizeCompat=" + isTopActivityInSizeCompat() + " eligibleForLetterboxEducation= " + eligibleForLetterboxEducation() + " isLetterboxEducationEnabled= " + isLetterboxEducationEnabled() + " isLetterboxDoubleTapEnabled= " + isLetterboxDoubleTapEnabled() + " eligibleForUserAspectRatioButton= " + eligibleForUserAspectRatioButton() + " topActivityBoundsLetterboxed= " + isTopActivityLetterboxed() + " isFromLetterboxDoubleTap= " + isFromLetterboxDoubleTap() + " topActivityLetterboxVerticalPosition= " + this.topActivityLetterboxVerticalPosition + " topActivityLetterboxHorizontalPosition= " + this.topActivityLetterboxHorizontalPosition + " topActivityLetterboxWidth=" + this.topActivityLetterboxWidth + " topActivityLetterboxHeight=" + this.topActivityLetterboxHeight + " topActivityAppBounds=" + this.topActivityAppBounds + " isUserFullscreenOverrideEnabled=" + isUserFullscreenOverrideEnabled() + " isSystemFullscreenOverrideEnabled=" + isSystemFullscreenOverrideEnabled() + " hasMinAspectRatioOverride=" + hasMinAspectRatioOverride() + " topActivityLetterboxBounds=" + this.topActivityLetterboxBounds + " cameraCompatTaskInfo=" + this.cameraCompatTaskInfo.toString() + " topActivityBounds=" + this.topActivityBounds + " topActivityInDisplayCompat=" + this.topActivityInDisplayCompat + " singleTapFromLetterbox=" + this.singleTapFromLetterbox + "}";
    }

    private void setTopActivityFlag(int i, boolean z) {
        int i2;
        if (z) {
            i2 = i | this.mTopActivityFlags;
        } else {
            i2 = (~i) & this.mTopActivityFlags;
        }
        this.mTopActivityFlags = i2;
    }

    private boolean isTopActivityFlagEnabled(int i) {
        return (this.mTopActivityFlags & i) == i;
    }
}
