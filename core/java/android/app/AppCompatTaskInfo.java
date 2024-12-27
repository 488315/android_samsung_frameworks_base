package android.app;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class AppCompatTaskInfo implements Parcelable {
    public static final Parcelable.Creator<AppCompatTaskInfo> CREATOR =
            new Parcelable.Creator<
                    AppCompatTaskInfo>() { // from class: android.app.AppCompatTaskInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AppCompatTaskInfo createFromParcel(Parcel in) {
                    return new AppCompatTaskInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AppCompatTaskInfo[] newArray(int size) {
                    return new AppCompatTaskInfo[size];
                }
            };
    private static final int FLAGS_COMPAT_UI_INTERESTED = 99307;
    private static final int FLAGS_ORGANIZER_INTERESTED = 99296;
    private static final int FLAG_BASE = 1;
    private static final int FLAG_ELIGIBLE_FOR_LETTERBOX_EDU = 2;
    private static final int FLAG_ELIGIBLE_FOR_USER_ASPECT_RATIO_BUTTON = 64;
    private static final int FLAG_FULLSCREEN_OVERRIDE_SYSTEM = 128;
    private static final int FLAG_FULLSCREEN_OVERRIDE_USER = 256;
    public static final int FLAG_HAS_MIN_ASPECT_RATIO_OVERRIDE = 512;
    private static final int FLAG_IN_SIZE_COMPAT = 8;
    private static final int FLAG_IS_FROM_LETTERBOX_DOUBLE_TAP = 32;
    private static final int FLAG_IS_FROM_LETTERBOX_SINGLE_TAP = 32768;
    private static final int FLAG_LETTERBOXED = 4;
    private static final int FLAG_LETTERBOX_DOUBLE_TAP_ENABLED = 16;
    private static final int FLAG_LETTERBOX_EDU_ENABLED = 1;
    private static final int FLAG_ROTATION_COMPAT_MODE_ENABLED = 65536;
    private static final int FLAG_UNDEFINED = 0;
    public CameraCompatTaskInfo cameraCompatTaskInfo;
    private int mTopActivityFlags;
    public Rect topActivityBounds;
    public boolean topActivityInDisplayCompat;
    public int topActivityLetterboxAppHeight;
    public int topActivityLetterboxAppWidth;
    public int topActivityLetterboxHeight;
    public int topActivityLetterboxHorizontalPosition;
    public int topActivityLetterboxVerticalPosition;
    public int topActivityLetterboxWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TopActivityFlag {}

    private AppCompatTaskInfo() {
        this.topActivityLetterboxVerticalPosition = -1;
        this.topActivityLetterboxHorizontalPosition = -1;
        this.topActivityLetterboxWidth = -1;
        this.topActivityLetterboxHeight = -1;
        this.topActivityLetterboxAppHeight = -1;
        this.topActivityLetterboxAppWidth = -1;
        this.cameraCompatTaskInfo = CameraCompatTaskInfo.create();
    }

    static AppCompatTaskInfo create() {
        return new AppCompatTaskInfo();
    }

    private AppCompatTaskInfo(Parcel source) {
        this.topActivityLetterboxVerticalPosition = -1;
        this.topActivityLetterboxHorizontalPosition = -1;
        this.topActivityLetterboxWidth = -1;
        this.topActivityLetterboxHeight = -1;
        this.topActivityLetterboxAppHeight = -1;
        this.topActivityLetterboxAppWidth = -1;
        this.cameraCompatTaskInfo = CameraCompatTaskInfo.create();
        readFromParcel(source);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean hasCompatUI() {
        return isTopActivityInSizeCompat()
                || eligibleForLetterboxEducation()
                || isLetterboxDoubleTapEnabled()
                || eligibleForUserAspectRatioButton();
    }

    public boolean isTopActivityPillarboxed() {
        return this.topActivityLetterboxWidth < this.topActivityLetterboxHeight;
    }

    public boolean isLetterboxEducationEnabled() {
        return isTopActivityFlagEnabled(1);
    }

    public void setLetterboxEducationEnabled(boolean enable) {
        setTopActivityFlag(1, enable);
    }

    public boolean eligibleForLetterboxEducation() {
        return isTopActivityFlagEnabled(2);
    }

    public void setEligibleForLetterboxEducation(boolean enable) {
        setTopActivityFlag(2, enable);
    }

    public boolean eligibleForUserAspectRatioButton() {
        return isTopActivityFlagEnabled(64);
    }

    public void setEligibleForUserAspectRatioButton(boolean enable) {
        setTopActivityFlag(64, enable);
    }

    public boolean isLetterboxDoubleTapEnabled() {
        return isTopActivityFlagEnabled(16);
    }

    public void setLetterboxDoubleTapEnabled(boolean enable) {
        setTopActivityFlag(16, enable);
    }

    public boolean isFromLetterboxDoubleTap() {
        return isTopActivityFlagEnabled(32);
    }

    public void setIsFromLetterboxDoubleTap(boolean enable) {
        setTopActivityFlag(32, enable);
    }

    public boolean isFromLetterboxSingleTap() {
        return isTopActivityFlagEnabled(32768);
    }

    public void setIsFromLetterboxSingleTap(boolean enable) {
        setTopActivityFlag(32768, enable);
    }

    public boolean isRotationCompatModeEnabled() {
        return isTopActivityFlagEnabled(65536);
    }

    public void setRotationCompatModeEnabled(boolean enable) {
        setTopActivityFlag(65536, enable);
    }

    public boolean isUserFullscreenOverrideEnabled() {
        return isTopActivityFlagEnabled(256);
    }

    public void setUserFullscreenOverrideEnabled(boolean enable) {
        setTopActivityFlag(256, enable);
    }

    public boolean isSystemFullscreenOverrideEnabled() {
        return isTopActivityFlagEnabled(128);
    }

    public void setSystemFullscreenOverrideEnabled(boolean enable) {
        setTopActivityFlag(128, enable);
    }

    public boolean isTopActivityInSizeCompat() {
        return isTopActivityFlagEnabled(8);
    }

    public void setTopActivityInSizeCompat(boolean enable) {
        setTopActivityFlag(8, enable);
    }

    public boolean isTopActivityLetterboxed() {
        return isTopActivityFlagEnabled(4);
    }

    public void setTopActivityLetterboxed(boolean enable) {
        setTopActivityFlag(4, enable);
    }

    public boolean hasMinAspectRatioOverride() {
        return isTopActivityFlagEnabled(512);
    }

    public void setHasMinAspectRatioOverride(boolean enable) {
        setTopActivityFlag(512, enable);
    }

    public void clearTopActivityFlags() {
        this.mTopActivityFlags = 0;
    }

    public boolean equalsForTaskOrganizer(AppCompatTaskInfo that) {
        return that != null
                && (this.mTopActivityFlags & FLAGS_ORGANIZER_INTERESTED)
                        == (FLAGS_ORGANIZER_INTERESTED & that.mTopActivityFlags)
                && this.topActivityLetterboxVerticalPosition
                        == that.topActivityLetterboxVerticalPosition
                && this.topActivityLetterboxWidth == that.topActivityLetterboxWidth
                && this.topActivityLetterboxHeight == that.topActivityLetterboxHeight
                && this.topActivityLetterboxAppWidth == that.topActivityLetterboxAppWidth
                && this.topActivityLetterboxAppHeight == that.topActivityLetterboxAppHeight
                && this.topActivityLetterboxHorizontalPosition
                        == that.topActivityLetterboxHorizontalPosition
                && this.cameraCompatTaskInfo.equalsForTaskOrganizer(that.cameraCompatTaskInfo);
    }

    public boolean equalsForCompatUi(AppCompatTaskInfo that) {
        return that != null
                && (this.mTopActivityFlags & FLAGS_COMPAT_UI_INTERESTED)
                        == (FLAGS_COMPAT_UI_INTERESTED & that.mTopActivityFlags)
                && this.topActivityLetterboxVerticalPosition
                        == that.topActivityLetterboxVerticalPosition
                && this.topActivityLetterboxHorizontalPosition
                        == that.topActivityLetterboxHorizontalPosition
                && this.topActivityLetterboxWidth == that.topActivityLetterboxWidth
                && this.topActivityLetterboxHeight == that.topActivityLetterboxHeight
                && this.topActivityLetterboxAppWidth == that.topActivityLetterboxAppWidth
                && this.topActivityLetterboxAppHeight == that.topActivityLetterboxAppHeight
                && this.cameraCompatTaskInfo.equalsForCompatUi(that.cameraCompatTaskInfo);
    }

    void readFromParcel(Parcel source) {
        this.mTopActivityFlags = source.readInt();
        this.topActivityLetterboxVerticalPosition = source.readInt();
        this.topActivityLetterboxHorizontalPosition = source.readInt();
        this.topActivityLetterboxWidth = source.readInt();
        this.topActivityLetterboxHeight = source.readInt();
        this.topActivityLetterboxAppWidth = source.readInt();
        this.topActivityLetterboxAppHeight = source.readInt();
        this.cameraCompatTaskInfo =
                (CameraCompatTaskInfo) source.readTypedObject(CameraCompatTaskInfo.CREATOR);
        this.topActivityBounds = (Rect) source.readTypedObject(Rect.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mTopActivityFlags);
        dest.writeInt(this.topActivityLetterboxVerticalPosition);
        dest.writeInt(this.topActivityLetterboxHorizontalPosition);
        dest.writeInt(this.topActivityLetterboxWidth);
        dest.writeInt(this.topActivityLetterboxHeight);
        dest.writeInt(this.topActivityLetterboxAppWidth);
        dest.writeInt(this.topActivityLetterboxAppHeight);
        dest.writeTypedObject(this.cameraCompatTaskInfo, flags);
        dest.writeTypedObject(this.topActivityBounds, flags);
    }

    public String toString() {
        return "AppCompatTaskInfo { topActivityInSizeCompat="
                + isTopActivityInSizeCompat()
                + " eligibleForLetterboxEducation= "
                + eligibleForLetterboxEducation()
                + " isLetterboxEducationEnabled= "
                + isLetterboxEducationEnabled()
                + " isLetterboxDoubleTapEnabled= "
                + isLetterboxDoubleTapEnabled()
                + " eligibleForUserAspectRatioButton= "
                + eligibleForUserAspectRatioButton()
                + " topActivityBoundsLetterboxed= "
                + isTopActivityLetterboxed()
                + " isFromLetterboxDoubleTap= "
                + isFromLetterboxDoubleTap()
                + " topActivityLetterboxVerticalPosition= "
                + this.topActivityLetterboxVerticalPosition
                + " topActivityLetterboxHorizontalPosition= "
                + this.topActivityLetterboxHorizontalPosition
                + " topActivityLetterboxWidth="
                + this.topActivityLetterboxWidth
                + " topActivityLetterboxHeight="
                + this.topActivityLetterboxHeight
                + " topActivityLetterboxAppWidth="
                + this.topActivityLetterboxAppWidth
                + " topActivityLetterboxAppHeight="
                + this.topActivityLetterboxAppHeight
                + " isUserFullscreenOverrideEnabled="
                + isUserFullscreenOverrideEnabled()
                + " isSystemFullscreenOverrideEnabled="
                + isSystemFullscreenOverrideEnabled()
                + " hasMinAspectRatioOverride="
                + hasMinAspectRatioOverride()
                + " cameraCompatTaskInfo="
                + this.cameraCompatTaskInfo.toString()
                + " topActivityBounds="
                + this.topActivityBounds
                + " topActivityInDisplayCompat="
                + this.topActivityInDisplayCompat
                + "}";
    }

    private void setTopActivityFlag(int flag, boolean enable) {
        int i = this.mTopActivityFlags;
        this.mTopActivityFlags = enable ? i | flag : i & (~flag);
    }

    private boolean isTopActivityFlagEnabled(int flag) {
        return (this.mTopActivityFlags & flag) == flag;
    }
}
