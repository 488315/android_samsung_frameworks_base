package android.service.controls;

import android.app.PendingIntent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.controls.CustomControl;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.ControlTemplateWrapper;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class Control implements Parcelable {
    public static final Parcelable.Creator<Control> CREATOR = new Parcelable.Creator<Control>() { // from class: android.service.controls.Control.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Control createFromParcel(Parcel source) {
            return new Control(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Control[] newArray(int size) {
            return new Control[size];
        }
    };
    private static final int NUM_STATUS = 5;
    public static final int STATUS_DISABLED = 4;
    public static final int STATUS_ERROR = 3;
    public static final int STATUS_NOT_FOUND = 2;
    public static final int STATUS_OK = 1;
    public static final int STATUS_UNKNOWN = 0;
    private static final String TAG = "Control";
    private final PendingIntent mAppIntent;
    private final boolean mAuthRequired;
    private final String mControlId;
    private final ControlTemplate mControlTemplate;
    private final ColorStateList mCustomColor;
    private CustomControl mCustomControl;
    private final Icon mCustomIcon;
    private final int mDeviceType;
    private final int mStatus;
    private final CharSequence mStatusText;
    private final CharSequence mStructure;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;
    private final CharSequence mZone;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    Control(String controlId, int deviceType, CharSequence title, CharSequence subtitle, CharSequence structure, CharSequence zone, PendingIntent appIntent, Icon customIcon, ColorStateList customColor, int status, ControlTemplate controlTemplate, CharSequence statusText, boolean authRequired) {
        Preconditions.checkNotNull(controlId);
        Preconditions.checkNotNull(title);
        Preconditions.checkNotNull(subtitle);
        Preconditions.checkNotNull(appIntent);
        Preconditions.checkNotNull(controlTemplate);
        Preconditions.checkNotNull(statusText);
        this.mControlId = controlId;
        if (!DeviceTypes.validDeviceType(deviceType)) {
            Log.e(TAG, "Invalid device type:" + deviceType);
            this.mDeviceType = 0;
        } else {
            this.mDeviceType = deviceType;
        }
        this.mTitle = title;
        this.mSubtitle = subtitle;
        this.mStructure = structure;
        this.mZone = zone;
        this.mAppIntent = appIntent;
        this.mCustomColor = customColor;
        this.mCustomIcon = customIcon;
        if (status < 0 || status >= 5) {
            this.mStatus = 0;
            Log.e(TAG, "Status unknown:" + status);
        } else {
            this.mStatus = status;
        }
        this.mControlTemplate = controlTemplate;
        this.mStatusText = statusText;
        this.mAuthRequired = authRequired;
        this.mCustomControl = new CustomControl();
    }

    Control(Parcel in) {
        this.mControlId = in.readString();
        this.mDeviceType = in.readInt();
        this.mTitle = in.readCharSequence();
        this.mSubtitle = in.readCharSequence();
        if (in.readByte() == 1) {
            this.mStructure = in.readCharSequence();
        } else {
            this.mStructure = null;
        }
        if (in.readByte() == 1) {
            this.mZone = in.readCharSequence();
        } else {
            this.mZone = null;
        }
        this.mAppIntent = PendingIntent.CREATOR.createFromParcel(in);
        if (in.readByte() == 1) {
            this.mCustomIcon = Icon.CREATOR.createFromParcel(in);
        } else {
            this.mCustomIcon = null;
        }
        if (in.readByte() == 1) {
            this.mCustomColor = ColorStateList.CREATOR.createFromParcel(in);
        } else {
            this.mCustomColor = null;
        }
        this.mStatus = in.readInt();
        ControlTemplateWrapper wrapper = ControlTemplateWrapper.CREATOR.createFromParcel(in);
        this.mControlTemplate = wrapper.getWrappedTemplate();
        this.mStatusText = in.readCharSequence();
        this.mAuthRequired = in.readBoolean();
        this.mCustomControl = CustomControl.CREATOR.createFromParcel(in);
    }

    public String getControlId() {
        return this.mControlId;
    }

    public int getDeviceType() {
        return this.mDeviceType;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public CharSequence getStructure() {
        return this.mStructure;
    }

    public CharSequence getZone() {
        return this.mZone;
    }

    public PendingIntent getAppIntent() {
        return this.mAppIntent;
    }

    public Icon getCustomIcon() {
        return this.mCustomIcon;
    }

    public ColorStateList getCustomColor() {
        return this.mCustomColor;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public ControlTemplate getControlTemplate() {
        return this.mControlTemplate;
    }

    public CharSequence getStatusText() {
        return this.mStatusText;
    }

    public boolean isAuthRequired() {
        return this.mAuthRequired;
    }

    public CustomControl getCustomControl() {
        return this.mCustomControl;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mControlId);
        dest.writeInt(this.mDeviceType);
        dest.writeCharSequence(this.mTitle);
        dest.writeCharSequence(this.mSubtitle);
        if (this.mStructure != null) {
            dest.writeByte((byte) 1);
            dest.writeCharSequence(this.mStructure);
        } else {
            dest.writeByte((byte) 0);
        }
        if (this.mZone != null) {
            dest.writeByte((byte) 1);
            dest.writeCharSequence(this.mZone);
        } else {
            dest.writeByte((byte) 0);
        }
        this.mAppIntent.writeToParcel(dest, flags);
        if (this.mCustomIcon != null) {
            dest.writeByte((byte) 1);
            this.mCustomIcon.writeToParcel(dest, flags);
        } else {
            dest.writeByte((byte) 0);
        }
        if (this.mCustomColor != null) {
            dest.writeByte((byte) 1);
            this.mCustomColor.writeToParcel(dest, flags);
        } else {
            dest.writeByte((byte) 0);
        }
        dest.writeInt(this.mStatus);
        new ControlTemplateWrapper(this.mControlTemplate).writeToParcel(dest, flags);
        dest.writeCharSequence(this.mStatusText);
        dest.writeBoolean(this.mAuthRequired);
        this.mCustomControl.writeToParcel(dest, flags);
    }

    public static final class StatelessBuilder {
        private static final String TAG = "StatelessBuilder";
        private PendingIntent mAppIntent;
        private String mControlId;
        private ColorStateList mCustomColor;
        private Icon mCustomIcon;
        private CustomControl.CustomStatelessBuilder mCustomStatelessBuilder;
        private int mDeviceType;
        private CharSequence mStructure;
        private CharSequence mSubtitle;
        private CharSequence mTitle;
        private CharSequence mZone;

        public StatelessBuilder(String controlId, PendingIntent appIntent) {
            this.mDeviceType = 0;
            this.mTitle = "";
            this.mSubtitle = "";
            Preconditions.checkNotNull(controlId);
            Preconditions.checkNotNull(appIntent);
            this.mControlId = controlId;
            this.mAppIntent = appIntent;
            this.mCustomStatelessBuilder = new CustomControl.CustomStatelessBuilder();
        }

        public StatelessBuilder(Control control) {
            this.mDeviceType = 0;
            this.mTitle = "";
            this.mSubtitle = "";
            Preconditions.checkNotNull(control);
            this.mControlId = control.mControlId;
            this.mDeviceType = control.mDeviceType;
            this.mTitle = control.mTitle;
            this.mSubtitle = control.mSubtitle;
            this.mStructure = control.mStructure;
            this.mZone = control.mZone;
            this.mAppIntent = control.mAppIntent;
            this.mCustomIcon = control.mCustomIcon;
            this.mCustomColor = control.mCustomColor;
            this.mCustomStatelessBuilder = new CustomControl.CustomStatelessBuilder(control.mCustomControl);
        }

        public StatelessBuilder setControlId(String controlId) {
            Preconditions.checkNotNull(controlId);
            this.mControlId = controlId;
            return this;
        }

        public StatelessBuilder setDeviceType(int deviceType) {
            if (!DeviceTypes.validDeviceType(deviceType)) {
                Log.e(TAG, "Invalid device type:" + deviceType);
                this.mDeviceType = 0;
            } else {
                this.mDeviceType = deviceType;
            }
            return this;
        }

        public StatelessBuilder setTitle(CharSequence title) {
            Preconditions.checkNotNull(title);
            this.mTitle = title;
            return this;
        }

        public StatelessBuilder setSubtitle(CharSequence subtitle) {
            Preconditions.checkNotNull(subtitle);
            this.mSubtitle = subtitle;
            return this;
        }

        public StatelessBuilder setStructure(CharSequence structure) {
            this.mStructure = structure;
            return this;
        }

        public StatelessBuilder setZone(CharSequence zone) {
            this.mZone = zone;
            return this;
        }

        public StatelessBuilder setAppIntent(PendingIntent appIntent) {
            Preconditions.checkNotNull(appIntent);
            this.mAppIntent = appIntent;
            return this;
        }

        public StatelessBuilder setCustomIcon(Icon customIcon) {
            this.mCustomIcon = customIcon;
            return this;
        }

        public StatelessBuilder setCustomColor(ColorStateList customColor) {
            this.mCustomColor = customColor;
            return this;
        }

        public StatelessBuilder setUseCustomIconWithoutShadowBg(boolean useCustomIconWithoutShadowBg) {
            this.mCustomStatelessBuilder.setUseCustomIconWithoutShadowBg(useCustomIconWithoutShadowBg);
            return this;
        }

        public StatelessBuilder setUseCustomIconWithoutPadding(boolean useCustomIconWithoutPadding) {
            this.mCustomStatelessBuilder.setUseCustomIconWithoutPadding(useCustomIconWithoutPadding);
            return this;
        }

        public StatelessBuilder setLayoutType(int type) {
            this.mCustomStatelessBuilder.setLayoutType(type);
            return this;
        }

        public StatelessBuilder setCustomIconAnimationJson(String jsonString, String jsonCache) {
            this.mCustomStatelessBuilder.setCustomIconAnimationJson(jsonString, jsonCache);
            return this;
        }

        public StatelessBuilder setCustomIconAnimationStartAndEndFrame(int startFrame, int endFrame) {
            this.mCustomStatelessBuilder.setCustomIconAnimationStartAndEndFrame(startFrame, endFrame);
            return this;
        }

        public StatelessBuilder setCustomIconAnimationRepeatCount(int count) {
            this.mCustomStatelessBuilder.setCustomIconAnimationRepeatCount(count);
            return this;
        }

        public CustomControl.CustomStatelessBuilder getCustomStatelessBuilder() {
            return this.mCustomStatelessBuilder;
        }

        public Control build() {
            Control control = new Control(this.mControlId, this.mDeviceType, this.mTitle, this.mSubtitle, this.mStructure, this.mZone, this.mAppIntent, this.mCustomIcon, this.mCustomColor, 0, ControlTemplate.NO_TEMPLATE, "", true);
            control.mCustomControl = this.mCustomStatelessBuilder.build();
            return control;
        }
    }

    public static final class StatefulBuilder {
        private static final String TAG = "StatefulBuilder";
        private PendingIntent mAppIntent;
        private boolean mAuthRequired;
        private String mControlId;
        private ControlTemplate mControlTemplate;
        private ColorStateList mCustomColor;
        private Icon mCustomIcon;
        private CustomControl.CustomStatefulBuilder mCustomStatefulBuilder;
        private int mDeviceType;
        private int mStatus;
        private CharSequence mStatusText;
        private CharSequence mStructure;
        private CharSequence mSubtitle;
        private CharSequence mTitle;
        private CharSequence mZone;

        public StatefulBuilder(String controlId, PendingIntent appIntent) {
            this.mDeviceType = 0;
            this.mTitle = "";
            this.mSubtitle = "";
            this.mStatus = 0;
            this.mControlTemplate = ControlTemplate.NO_TEMPLATE;
            this.mStatusText = "";
            this.mAuthRequired = true;
            Preconditions.checkNotNull(controlId);
            Preconditions.checkNotNull(appIntent);
            this.mControlId = controlId;
            this.mAppIntent = appIntent;
            this.mCustomStatefulBuilder = new CustomControl.CustomStatefulBuilder();
        }

        public StatefulBuilder(Control control) {
            this.mDeviceType = 0;
            this.mTitle = "";
            this.mSubtitle = "";
            this.mStatus = 0;
            this.mControlTemplate = ControlTemplate.NO_TEMPLATE;
            this.mStatusText = "";
            this.mAuthRequired = true;
            Preconditions.checkNotNull(control);
            this.mControlId = control.mControlId;
            this.mDeviceType = control.mDeviceType;
            this.mTitle = control.mTitle;
            this.mSubtitle = control.mSubtitle;
            this.mStructure = control.mStructure;
            this.mZone = control.mZone;
            this.mAppIntent = control.mAppIntent;
            this.mCustomIcon = control.mCustomIcon;
            this.mCustomColor = control.mCustomColor;
            this.mStatus = control.mStatus;
            this.mControlTemplate = control.mControlTemplate;
            this.mStatusText = control.mStatusText;
            this.mAuthRequired = control.mAuthRequired;
            this.mCustomStatefulBuilder = new CustomControl.CustomStatefulBuilder(control.mCustomControl);
        }

        public StatefulBuilder setControlId(String controlId) {
            Preconditions.checkNotNull(controlId);
            this.mControlId = controlId;
            return this;
        }

        public StatefulBuilder setDeviceType(int deviceType) {
            if (!DeviceTypes.validDeviceType(deviceType)) {
                Log.e(TAG, "Invalid device type:" + deviceType);
                this.mDeviceType = 0;
            } else {
                this.mDeviceType = deviceType;
            }
            return this;
        }

        public StatefulBuilder setTitle(CharSequence title) {
            Preconditions.checkNotNull(title);
            this.mTitle = title;
            return this;
        }

        public StatefulBuilder setSubtitle(CharSequence subtitle) {
            Preconditions.checkNotNull(subtitle);
            this.mSubtitle = subtitle;
            return this;
        }

        public StatefulBuilder setStructure(CharSequence structure) {
            this.mStructure = structure;
            return this;
        }

        public StatefulBuilder setZone(CharSequence zone) {
            this.mZone = zone;
            return this;
        }

        public StatefulBuilder setAppIntent(PendingIntent appIntent) {
            Preconditions.checkNotNull(appIntent);
            this.mAppIntent = appIntent;
            return this;
        }

        public StatefulBuilder setCustomIcon(Icon customIcon) {
            this.mCustomIcon = customIcon;
            return this;
        }

        public StatefulBuilder setCustomColor(ColorStateList customColor) {
            this.mCustomColor = customColor;
            return this;
        }

        public StatefulBuilder setStatus(int status) {
            if (status < 0 || status >= 5) {
                this.mStatus = 0;
                Log.e(TAG, "Status unknown:" + status);
            } else {
                this.mStatus = status;
            }
            return this;
        }

        public StatefulBuilder setControlTemplate(ControlTemplate controlTemplate) {
            Preconditions.checkNotNull(controlTemplate);
            this.mControlTemplate = controlTemplate;
            return this;
        }

        public StatefulBuilder setStatusText(CharSequence statusText) {
            Preconditions.checkNotNull(statusText);
            this.mStatusText = statusText;
            return this;
        }

        public StatefulBuilder setAuthRequired(boolean authRequired) {
            this.mAuthRequired = authRequired;
            return this;
        }

        public StatefulBuilder setCustomIconAnimationJson(String jsonString, String jsonCache) {
            this.mCustomStatefulBuilder.setCustomIconAnimationJson(jsonString, jsonCache);
            return this;
        }

        public StatefulBuilder setCustomIconAnimationStartAndEndFrame(int startFrame, int endFrame) {
            this.mCustomStatefulBuilder.setCustomIconAnimationStartAndEndFrame(startFrame, endFrame);
            return this;
        }

        public StatefulBuilder setCustomIconAnimationRepeatCount(int count) {
            this.mCustomStatefulBuilder.setCustomIconAnimationRepeatCount(count);
            return this;
        }

        public StatefulBuilder setActionIcon(Icon actionIcon) {
            this.mCustomStatefulBuilder.setActionIcon(actionIcon);
            return this;
        }

        public StatefulBuilder setUseFullScreenDetailDialog(boolean useFullScreenDetailDialog) {
            this.mCustomStatefulBuilder.setUseFullScreenDetailDialog(useFullScreenDetailDialog);
            return this;
        }

        public StatefulBuilder setAllowBasicActionWhenLocked(boolean allowBasicActionWhenLocked) {
            this.mCustomStatefulBuilder.setAllowBasicActionWhenLocked(allowBasicActionWhenLocked);
            return this;
        }

        public StatefulBuilder setStatusTextColor(ColorStateList statusTextColor) {
            this.mCustomStatefulBuilder.setStatusTextColor(statusTextColor);
            return this;
        }

        public StatefulBuilder setStatusIconType(int statusIconType) {
            this.mCustomStatefulBuilder.setStatusIconType(statusIconType);
            return this;
        }

        public StatefulBuilder setUseCustomIconWithoutShadowBg(boolean useCustomIconWithoutShadowBg) {
            this.mCustomStatefulBuilder.setUseCustomIconWithoutShadowBg(useCustomIconWithoutShadowBg);
            return this;
        }

        public StatefulBuilder setUseCustomIconWithoutPadding(boolean useCustomIconWithoutPadding) {
            this.mCustomStatefulBuilder.setUseCustomIconWithoutPadding(useCustomIconWithoutPadding);
            return this;
        }

        public StatefulBuilder setOrder(int order) {
            this.mCustomStatefulBuilder.setOrder(order);
            return this;
        }

        public StatefulBuilder setCustomStatusIcon(Icon icon) {
            this.mCustomStatefulBuilder.setCustomStatusIcon(icon);
            return this;
        }

        public StatefulBuilder setLayoutType(int type) {
            this.mCustomStatefulBuilder.setLayoutType(type);
            return this;
        }

        public StatefulBuilder setCustomSound(int customSound) {
            this.mCustomStatefulBuilder.setCustomSound(customSound);
            return this;
        }

        public CustomControl.CustomStatefulBuilder getCustomStatefulBuilder() {
            return this.mCustomStatefulBuilder;
        }

        public Control build() {
            Control control = new Control(this.mControlId, this.mDeviceType, this.mTitle, this.mSubtitle, this.mStructure, this.mZone, this.mAppIntent, this.mCustomIcon, this.mCustomColor, this.mStatus, this.mControlTemplate, this.mStatusText, this.mAuthRequired);
            control.mCustomControl = this.mCustomStatefulBuilder.build();
            return control;
        }
    }
}
