package android.service.controls;

import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class CustomControl implements Parcelable {
    public static final Parcelable.Creator<CustomControl> CREATOR = new Parcelable.Creator<CustomControl>() { // from class: android.service.controls.CustomControl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CustomControl createFromParcel(Parcel parcel) {
            return new CustomControl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CustomControl[] newArray(int i) {
            return new CustomControl[i];
        }
    };
    public static final int CUSTOM_SOUND_AUTOMATION_ERROR = 1;
    public static final int CUSTOM_SOUND_AUTOMATION_SUCCESS = 2;
    public static final int CUSTOM_SOUND_MEDIA_PAUSE = 3;
    public static final int CUSTOM_SOUND_MEDIA_PLAY_RESUME = 4;
    public static final int CUSTOM_SOUND_NONE = 0;
    public static final int LAYOUT_TYPE_NORMAL = 0;
    public static final int LAYOUT_TYPE_SMALL = 1;
    private static final int NUM_STATUS_ICON_TYPE = 3;
    public static final int STATUS_ICON_TYPE_OFFLINE = 1;
    public static final int STATUS_ICON_TYPE_UNKNOWN = 0;
    public static final int STATUS_ICON_TYPE_WARNING = 2;
    private Icon mActionIcon;
    private boolean mAllowBasicActionWhenLocked;
    private int mCustomIconAnimationEndFrame;
    private String mCustomIconAnimationJson;
    private String mCustomIconAnimationJsonCache;
    private int mCustomIconAnimationRepeatCount;
    private int mCustomIconAnimationStartFrame;
    private int mCustomSound;
    private Icon mCustomStatusIcon;
    private int mLayoutType;
    private int mOrder;
    private Icon mOverlayCustomIcon;
    private int mStatusIconType;
    private ColorStateList mStatusTextColor;
    private boolean mUseCustomIconWithoutPadding;
    private boolean mUseCustomIconWithoutShadowBg;
    private boolean mUseFullScreenDetailDialog;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CustomSound {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LayoutType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusIconType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    CustomControl() {
        this.mCustomIconAnimationJson = "";
        this.mCustomIconAnimationJsonCache = "";
        this.mCustomIconAnimationStartFrame = -1;
        this.mCustomIconAnimationEndFrame = -1;
        this.mCustomIconAnimationRepeatCount = -1;
        this.mUseFullScreenDetailDialog = false;
        this.mAllowBasicActionWhenLocked = false;
        this.mStatusIconType = 0;
        this.mUseCustomIconWithoutShadowBg = false;
        this.mUseCustomIconWithoutPadding = false;
        this.mOrder = 0;
        this.mLayoutType = 0;
        this.mCustomSound = 0;
    }

    CustomControl(Parcel parcel) {
        this.mCustomIconAnimationJson = "";
        this.mCustomIconAnimationJsonCache = "";
        this.mCustomIconAnimationStartFrame = -1;
        this.mCustomIconAnimationEndFrame = -1;
        this.mCustomIconAnimationRepeatCount = -1;
        this.mUseFullScreenDetailDialog = false;
        this.mAllowBasicActionWhenLocked = false;
        this.mStatusIconType = 0;
        this.mUseCustomIconWithoutShadowBg = false;
        this.mUseCustomIconWithoutPadding = false;
        this.mOrder = 0;
        this.mLayoutType = 0;
        this.mCustomSound = 0;
        this.mCustomIconAnimationJson = parcel.readString();
        this.mCustomIconAnimationJsonCache = parcel.readString();
        this.mCustomIconAnimationStartFrame = parcel.readInt();
        this.mCustomIconAnimationEndFrame = parcel.readInt();
        this.mCustomIconAnimationRepeatCount = parcel.readInt();
        if (parcel.readByte() == 1) {
            this.mActionIcon = Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mActionIcon = null;
        }
        this.mUseFullScreenDetailDialog = parcel.readBoolean();
        this.mAllowBasicActionWhenLocked = parcel.readBoolean();
        if (parcel.readByte() == 1) {
            this.mStatusTextColor = ColorStateList.CREATOR.createFromParcel(parcel);
        } else {
            this.mStatusTextColor = null;
        }
        this.mStatusIconType = parcel.readInt();
        this.mUseCustomIconWithoutShadowBg = parcel.readBoolean();
        this.mUseCustomIconWithoutPadding = parcel.readBoolean();
        this.mOrder = parcel.readInt();
        if (parcel.readByte() == 1) {
            this.mCustomStatusIcon = Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mCustomStatusIcon = null;
        }
        this.mLayoutType = parcel.readInt();
        this.mCustomSound = parcel.readInt();
        this.mOverlayCustomIcon = parcel.readByte() == 1 ? Icon.CREATOR.createFromParcel(parcel) : null;
    }

    public String getCustomIconAnimationJson() {
        return this.mCustomIconAnimationJson;
    }

    public String getCustomIconAnimationJsonCache() {
        return this.mCustomIconAnimationJsonCache;
    }

    public int getCustomIconAnimationStartFrame() {
        return this.mCustomIconAnimationStartFrame;
    }

    public int getCustomIconAnimationEndFrame() {
        return this.mCustomIconAnimationEndFrame;
    }

    public int getCustomIconAnimationRepeatCount() {
        return this.mCustomIconAnimationRepeatCount;
    }

    public Icon getActionIcon() {
        return this.mActionIcon;
    }

    public boolean getUseFullScreenDetailDialog() {
        return this.mUseFullScreenDetailDialog;
    }

    public boolean getAllowBasicActionWhenLocked() {
        return this.mAllowBasicActionWhenLocked;
    }

    public ColorStateList getStatusTextColor() {
        return this.mStatusTextColor;
    }

    public int getStatusIconType() {
        return this.mStatusIconType;
    }

    public boolean getUseCustomIconWithoutShadowBg() {
        return this.mUseCustomIconWithoutShadowBg;
    }

    public boolean getUseCustomIconWithoutPadding() {
        return this.mUseCustomIconWithoutPadding;
    }

    public int getOrder() {
        return this.mOrder;
    }

    public Icon getCustomStatusIcon() {
        return this.mCustomStatusIcon;
    }

    public int getLayoutType() {
        return this.mLayoutType;
    }

    public int getCustomSound() {
        return this.mCustomSound;
    }

    public Icon getOverlayCustomIcon() {
        return this.mOverlayCustomIcon;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCustomIconAnimationJson);
        parcel.writeString(this.mCustomIconAnimationJsonCache);
        parcel.writeInt(this.mCustomIconAnimationStartFrame);
        parcel.writeInt(this.mCustomIconAnimationEndFrame);
        parcel.writeInt(this.mCustomIconAnimationRepeatCount);
        if (this.mActionIcon != null) {
            parcel.writeByte((byte) 1);
            this.mActionIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeBoolean(this.mUseFullScreenDetailDialog);
        parcel.writeBoolean(this.mAllowBasicActionWhenLocked);
        if (this.mStatusTextColor != null) {
            parcel.writeByte((byte) 1);
            this.mStatusTextColor.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mStatusIconType);
        parcel.writeBoolean(this.mUseCustomIconWithoutShadowBg);
        parcel.writeBoolean(this.mUseCustomIconWithoutPadding);
        parcel.writeInt(this.mOrder);
        if (this.mCustomStatusIcon != null) {
            parcel.writeByte((byte) 1);
            this.mCustomStatusIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mLayoutType);
        parcel.writeInt(this.mCustomSound);
        if (this.mOverlayCustomIcon != null) {
            parcel.writeByte((byte) 1);
            this.mOverlayCustomIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
    }

    public static final class CustomStatelessBuilder {
        private Icon mOverlayCustomIcon;
        private boolean mUseCustomIconWithoutPadding;
        private boolean mUseCustomIconWithoutShadowBg;
        private int mLayoutType = 0;
        private String mCustomIconAnimationJson = "";
        private String mCustomIconAnimationJsonCache = "";
        private int mCustomIconAnimationStartFrame = -1;
        private int mCustomIconAnimationEndFrame = -1;
        private int mCustomIconAnimationRepeatCount = -1;

        public CustomStatelessBuilder() {
        }

        public CustomStatelessBuilder(CustomControl customControl) {
            setUseCustomIconWithoutShadowBg(customControl.mUseCustomIconWithoutShadowBg);
            setUseCustomIconWithoutPadding(customControl.mUseCustomIconWithoutPadding);
            setLayoutType(customControl.mLayoutType);
            setCustomIconAnimationJson(customControl.mCustomIconAnimationJson, customControl.mCustomIconAnimationJsonCache);
            setCustomIconAnimationStartAndEndFrame(customControl.mCustomIconAnimationStartFrame, customControl.mCustomIconAnimationEndFrame);
            setCustomIconAnimationRepeatCount(customControl.mCustomIconAnimationRepeatCount);
            setOverlayCustomIcon(customControl.mOverlayCustomIcon);
        }

        public CustomStatelessBuilder setUseCustomIconWithoutShadowBg(boolean z) {
            this.mUseCustomIconWithoutShadowBg = z;
            return this;
        }

        public CustomStatelessBuilder setUseCustomIconWithoutPadding(boolean z) {
            this.mUseCustomIconWithoutPadding = z;
            return this;
        }

        public CustomStatelessBuilder setLayoutType(int i) {
            this.mLayoutType = i;
            return this;
        }

        public CustomStatelessBuilder setCustomIconAnimationJson(String str, String str2) {
            Preconditions.checkNotNull(str);
            this.mCustomIconAnimationJson = str;
            this.mCustomIconAnimationJsonCache = str2;
            return this;
        }

        public CustomStatelessBuilder setCustomIconAnimationStartAndEndFrame(int i, int i2) {
            this.mCustomIconAnimationStartFrame = i;
            this.mCustomIconAnimationEndFrame = i2;
            return this;
        }

        public CustomStatelessBuilder setCustomIconAnimationRepeatCount(int i) {
            this.mCustomIconAnimationRepeatCount = i;
            return this;
        }

        public CustomStatelessBuilder setOverlayCustomIcon(Icon icon) {
            this.mOverlayCustomIcon = icon;
            return this;
        }

        public CustomControl build() {
            CustomControl customControl = new CustomControl();
            customControl.mUseCustomIconWithoutShadowBg = this.mUseCustomIconWithoutShadowBg;
            customControl.mUseCustomIconWithoutPadding = this.mUseCustomIconWithoutPadding;
            customControl.mLayoutType = this.mLayoutType;
            customControl.mCustomIconAnimationJson = this.mCustomIconAnimationJson;
            customControl.mCustomIconAnimationJsonCache = this.mCustomIconAnimationJsonCache;
            customControl.mCustomIconAnimationStartFrame = this.mCustomIconAnimationStartFrame;
            customControl.mCustomIconAnimationEndFrame = this.mCustomIconAnimationEndFrame;
            customControl.mCustomIconAnimationRepeatCount = this.mCustomIconAnimationRepeatCount;
            customControl.mOverlayCustomIcon = this.mOverlayCustomIcon;
            return customControl;
        }
    }

    public static final class CustomStatefulBuilder {
        private static final String TAG = "CustomStatefulBuilder";
        private Icon mActionIcon;
        private boolean mAllowBasicActionWhenLocked;
        private Icon mCustomStatusIcon;
        private Icon mOverlayCustomIcon;
        private ColorStateList mStatusTextColor;
        private boolean mUseCustomIconWithoutPadding;
        private boolean mUseCustomIconWithoutShadowBg;
        private boolean mUseFullScreenDetailDialog;
        private String mCustomIconAnimationJson = "";
        private String mCustomIconAnimationJsonCache = "";
        private int mCustomIconAnimationStartFrame = -1;
        private int mCustomIconAnimationEndFrame = -1;
        private int mCustomIconAnimationRepeatCount = -1;
        private int mStatusIconType = 0;
        private int mOrder = 0;
        private int mLayoutType = 0;
        private int mCustomSound = 0;

        public CustomStatefulBuilder() {
        }

        public CustomStatefulBuilder(CustomControl customControl) {
            setCustomIconAnimationJson(customControl.mCustomIconAnimationJson, customControl.mCustomIconAnimationJsonCache);
            setCustomIconAnimationStartAndEndFrame(customControl.mCustomIconAnimationStartFrame, customControl.mCustomIconAnimationEndFrame);
            setCustomIconAnimationRepeatCount(customControl.mCustomIconAnimationRepeatCount);
            setActionIcon(customControl.mActionIcon);
            setUseFullScreenDetailDialog(customControl.mUseFullScreenDetailDialog);
            setAllowBasicActionWhenLocked(customControl.mAllowBasicActionWhenLocked);
            setStatusTextColor(customControl.mStatusTextColor);
            setStatusIconType(customControl.mStatusIconType);
            setUseCustomIconWithoutShadowBg(customControl.mUseCustomIconWithoutShadowBg);
            setUseCustomIconWithoutPadding(customControl.mUseCustomIconWithoutPadding);
            setOrder(customControl.mOrder);
            setCustomStatusIcon(customControl.mCustomStatusIcon);
            setLayoutType(customControl.mLayoutType);
            setCustomSound(customControl.mCustomSound);
            setOverlayCustomIcon(customControl.mOverlayCustomIcon);
        }

        public CustomStatefulBuilder setCustomIconAnimationJson(String str, String str2) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(str2);
            this.mCustomIconAnimationJson = str;
            this.mCustomIconAnimationJsonCache = str2;
            return this;
        }

        public CustomStatefulBuilder setCustomIconAnimationStartAndEndFrame(int i, int i2) {
            this.mCustomIconAnimationStartFrame = i;
            this.mCustomIconAnimationEndFrame = i2;
            return this;
        }

        public CustomStatefulBuilder setCustomIconAnimationRepeatCount(int i) {
            this.mCustomIconAnimationRepeatCount = i;
            return this;
        }

        public CustomStatefulBuilder setActionIcon(Icon icon) {
            this.mActionIcon = icon;
            return this;
        }

        public CustomStatefulBuilder setUseFullScreenDetailDialog(boolean z) {
            this.mUseFullScreenDetailDialog = z;
            return this;
        }

        public CustomStatefulBuilder setAllowBasicActionWhenLocked(boolean z) {
            this.mAllowBasicActionWhenLocked = z;
            return this;
        }

        public CustomStatefulBuilder setStatusTextColor(ColorStateList colorStateList) {
            this.mStatusTextColor = colorStateList;
            return this;
        }

        public CustomStatefulBuilder setStatusIconType(int i) {
            if (i < 0 || i >= 3) {
                this.mStatusIconType = 0;
                Log.e(TAG, "Status Icon type unknown:" + i);
                return this;
            }
            this.mStatusIconType = i;
            return this;
        }

        public CustomStatefulBuilder setUseCustomIconWithoutShadowBg(boolean z) {
            this.mUseCustomIconWithoutShadowBg = z;
            return this;
        }

        public CustomStatefulBuilder setUseCustomIconWithoutPadding(boolean z) {
            this.mUseCustomIconWithoutPadding = z;
            return this;
        }

        public CustomStatefulBuilder setOrder(int i) {
            this.mOrder = i;
            return this;
        }

        public CustomStatefulBuilder setCustomStatusIcon(Icon icon) {
            this.mCustomStatusIcon = icon;
            return this;
        }

        public CustomStatefulBuilder setLayoutType(int i) {
            this.mLayoutType = i;
            return this;
        }

        public CustomStatefulBuilder setCustomSound(int i) {
            this.mCustomSound = i;
            return this;
        }

        public CustomStatefulBuilder setOverlayCustomIcon(Icon icon) {
            this.mOverlayCustomIcon = icon;
            return this;
        }

        public CustomControl build() {
            CustomControl customControl = new CustomControl();
            customControl.mCustomIconAnimationJson = this.mCustomIconAnimationJson;
            customControl.mCustomIconAnimationJsonCache = this.mCustomIconAnimationJsonCache;
            customControl.mCustomIconAnimationStartFrame = this.mCustomIconAnimationStartFrame;
            customControl.mCustomIconAnimationEndFrame = this.mCustomIconAnimationEndFrame;
            customControl.mCustomIconAnimationRepeatCount = this.mCustomIconAnimationRepeatCount;
            customControl.mActionIcon = this.mActionIcon;
            customControl.mUseFullScreenDetailDialog = this.mUseFullScreenDetailDialog;
            customControl.mAllowBasicActionWhenLocked = this.mAllowBasicActionWhenLocked;
            customControl.mStatusTextColor = this.mStatusTextColor;
            customControl.mStatusIconType = this.mStatusIconType;
            customControl.mUseCustomIconWithoutShadowBg = this.mUseCustomIconWithoutShadowBg;
            customControl.mUseCustomIconWithoutPadding = this.mUseCustomIconWithoutPadding;
            customControl.mOrder = this.mOrder;
            customControl.mCustomStatusIcon = this.mCustomStatusIcon;
            customControl.mLayoutType = this.mLayoutType;
            customControl.mCustomSound = this.mCustomSound;
            customControl.mOverlayCustomIcon = this.mOverlayCustomIcon;
            return customControl;
        }
    }
}
