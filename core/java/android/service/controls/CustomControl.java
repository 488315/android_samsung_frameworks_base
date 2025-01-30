package android.service.controls;

import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class CustomControl implements Parcelable {
  public static final Parcelable.Creator<CustomControl> CREATOR =
      new Parcelable.Creator<
          CustomControl>() { // from class: android.service.controls.CustomControl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CustomControl createFromParcel(Parcel source) {
          return new CustomControl(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CustomControl[] newArray(int size) {
          return new CustomControl[size];
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
  public @interface CustomSound {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface LayoutType {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface StatusIconType {}

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

  CustomControl(Parcel in) {
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
    this.mCustomIconAnimationJson = in.readString();
    this.mCustomIconAnimationJsonCache = in.readString();
    this.mCustomIconAnimationStartFrame = in.readInt();
    this.mCustomIconAnimationEndFrame = in.readInt();
    this.mCustomIconAnimationRepeatCount = in.readInt();
    if (in.readByte() == 1) {
      this.mActionIcon = Icon.CREATOR.createFromParcel(in);
    } else {
      this.mActionIcon = null;
    }
    this.mUseFullScreenDetailDialog = in.readBoolean();
    this.mAllowBasicActionWhenLocked = in.readBoolean();
    if (in.readByte() == 1) {
      this.mStatusTextColor = ColorStateList.CREATOR.createFromParcel(in);
    } else {
      this.mStatusTextColor = null;
    }
    this.mStatusIconType = in.readInt();
    this.mUseCustomIconWithoutShadowBg = in.readBoolean();
    this.mUseCustomIconWithoutPadding = in.readBoolean();
    this.mOrder = in.readInt();
    if (in.readByte() == 1) {
      this.mCustomStatusIcon = Icon.CREATOR.createFromParcel(in);
    } else {
      this.mCustomStatusIcon = null;
    }
    this.mLayoutType = in.readInt();
    this.mCustomSound = in.readInt();
    this.mOverlayCustomIcon = in.readByte() == 1 ? Icon.CREATOR.createFromParcel(in) : null;
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

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.mCustomIconAnimationJson);
    dest.writeString(this.mCustomIconAnimationJsonCache);
    dest.writeInt(this.mCustomIconAnimationStartFrame);
    dest.writeInt(this.mCustomIconAnimationEndFrame);
    dest.writeInt(this.mCustomIconAnimationRepeatCount);
    if (this.mActionIcon != null) {
      dest.writeByte((byte) 1);
      this.mActionIcon.writeToParcel(dest, flags);
    } else {
      dest.writeByte((byte) 0);
    }
    dest.writeBoolean(this.mUseFullScreenDetailDialog);
    dest.writeBoolean(this.mAllowBasicActionWhenLocked);
    if (this.mStatusTextColor != null) {
      dest.writeByte((byte) 1);
      this.mStatusTextColor.writeToParcel(dest, flags);
    } else {
      dest.writeByte((byte) 0);
    }
    dest.writeInt(this.mStatusIconType);
    dest.writeBoolean(this.mUseCustomIconWithoutShadowBg);
    dest.writeBoolean(this.mUseCustomIconWithoutPadding);
    dest.writeInt(this.mOrder);
    if (this.mCustomStatusIcon != null) {
      dest.writeByte((byte) 1);
      this.mCustomStatusIcon.writeToParcel(dest, flags);
    } else {
      dest.writeByte((byte) 0);
    }
    dest.writeInt(this.mLayoutType);
    dest.writeInt(this.mCustomSound);
    if (this.mOverlayCustomIcon != null) {
      dest.writeByte((byte) 1);
      this.mOverlayCustomIcon.writeToParcel(dest, flags);
    } else {
      dest.writeByte((byte) 0);
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

    public CustomStatelessBuilder() {}

    public CustomStatelessBuilder(CustomControl customControl) {
      setUseCustomIconWithoutShadowBg(customControl.mUseCustomIconWithoutShadowBg);
      setUseCustomIconWithoutPadding(customControl.mUseCustomIconWithoutPadding);
      setLayoutType(customControl.mLayoutType);
      setCustomIconAnimationJson(
          customControl.mCustomIconAnimationJson, customControl.mCustomIconAnimationJsonCache);
      setCustomIconAnimationStartAndEndFrame(
          customControl.mCustomIconAnimationStartFrame, customControl.mCustomIconAnimationEndFrame);
      setCustomIconAnimationRepeatCount(customControl.mCustomIconAnimationRepeatCount);
      setOverlayCustomIcon(customControl.mOverlayCustomIcon);
    }

    public CustomStatelessBuilder setUseCustomIconWithoutShadowBg(
        boolean useCustomIconWithoutShadowBg) {
      this.mUseCustomIconWithoutShadowBg = useCustomIconWithoutShadowBg;
      return this;
    }

    public CustomStatelessBuilder setUseCustomIconWithoutPadding(
        boolean useCustomIconWithoutPadding) {
      this.mUseCustomIconWithoutPadding = useCustomIconWithoutPadding;
      return this;
    }

    public CustomStatelessBuilder setLayoutType(int type) {
      this.mLayoutType = type;
      return this;
    }

    public CustomStatelessBuilder setCustomIconAnimationJson(String jsonString, String jsonCache) {
      Preconditions.checkNotNull(jsonString);
      this.mCustomIconAnimationJson = jsonString;
      this.mCustomIconAnimationJsonCache = jsonCache;
      return this;
    }

    public CustomStatelessBuilder setCustomIconAnimationStartAndEndFrame(
        int startFrame, int endFrame) {
      this.mCustomIconAnimationStartFrame = startFrame;
      this.mCustomIconAnimationEndFrame = endFrame;
      return this;
    }

    public CustomStatelessBuilder setCustomIconAnimationRepeatCount(int count) {
      this.mCustomIconAnimationRepeatCount = count;
      return this;
    }

    public CustomStatelessBuilder setOverlayCustomIcon(Icon overlayCustomIcon) {
      this.mOverlayCustomIcon = overlayCustomIcon;
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

    public CustomStatefulBuilder() {}

    public CustomStatefulBuilder(CustomControl customControl) {
      setCustomIconAnimationJson(
          customControl.mCustomIconAnimationJson, customControl.mCustomIconAnimationJsonCache);
      setCustomIconAnimationStartAndEndFrame(
          customControl.mCustomIconAnimationStartFrame, customControl.mCustomIconAnimationEndFrame);
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

    public CustomStatefulBuilder setCustomIconAnimationJson(String jsonString, String jsonCache) {
      Preconditions.checkNotNull(jsonString);
      Preconditions.checkNotNull(jsonCache);
      this.mCustomIconAnimationJson = jsonString;
      this.mCustomIconAnimationJsonCache = jsonCache;
      return this;
    }

    public CustomStatefulBuilder setCustomIconAnimationStartAndEndFrame(
        int startFrame, int endFrame) {
      this.mCustomIconAnimationStartFrame = startFrame;
      this.mCustomIconAnimationEndFrame = endFrame;
      return this;
    }

    public CustomStatefulBuilder setCustomIconAnimationRepeatCount(int count) {
      this.mCustomIconAnimationRepeatCount = count;
      return this;
    }

    public CustomStatefulBuilder setActionIcon(Icon actionIcon) {
      this.mActionIcon = actionIcon;
      return this;
    }

    public CustomStatefulBuilder setUseFullScreenDetailDialog(boolean useFullScreenDetailDialog) {
      this.mUseFullScreenDetailDialog = useFullScreenDetailDialog;
      return this;
    }

    public CustomStatefulBuilder setAllowBasicActionWhenLocked(boolean allowBasicActionWhenLocked) {
      this.mAllowBasicActionWhenLocked = allowBasicActionWhenLocked;
      return this;
    }

    public CustomStatefulBuilder setStatusTextColor(ColorStateList statusTextColor) {
      this.mStatusTextColor = statusTextColor;
      return this;
    }

    public CustomStatefulBuilder setStatusIconType(int statusIconType) {
      if (statusIconType < 0 || statusIconType >= 3) {
        this.mStatusIconType = 0;
        Log.m96e(TAG, "Status Icon type unknown:" + statusIconType);
      } else {
        this.mStatusIconType = statusIconType;
      }
      return this;
    }

    public CustomStatefulBuilder setUseCustomIconWithoutShadowBg(
        boolean useCustomIconWithoutShadowBg) {
      this.mUseCustomIconWithoutShadowBg = useCustomIconWithoutShadowBg;
      return this;
    }

    public CustomStatefulBuilder setUseCustomIconWithoutPadding(
        boolean useCustomIconWithoutPadding) {
      this.mUseCustomIconWithoutPadding = useCustomIconWithoutPadding;
      return this;
    }

    public CustomStatefulBuilder setOrder(int order) {
      this.mOrder = order;
      return this;
    }

    public CustomStatefulBuilder setCustomStatusIcon(Icon icon) {
      this.mCustomStatusIcon = icon;
      return this;
    }

    public CustomStatefulBuilder setLayoutType(int type) {
      this.mLayoutType = type;
      return this;
    }

    public CustomStatefulBuilder setCustomSound(int customSound) {
      this.mCustomSound = customSound;
      return this;
    }

    public CustomStatefulBuilder setOverlayCustomIcon(Icon overlayCustomIcon) {
      this.mOverlayCustomIcon = overlayCustomIcon;
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
