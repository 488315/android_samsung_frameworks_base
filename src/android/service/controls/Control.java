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
        public Control createFromParcel(Parcel parcel) {
            return new Control(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Control[] newArray(int i) {
            return new Control[i];
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Control(String str, int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, PendingIntent pendingIntent, Icon icon, ColorStateList colorStateList, int i2, ControlTemplate controlTemplate, CharSequence charSequence5, boolean z) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(charSequence2);
        Preconditions.checkNotNull(pendingIntent);
        Preconditions.checkNotNull(controlTemplate);
        Preconditions.checkNotNull(charSequence5);
        this.mControlId = str;
        if (!DeviceTypes.validDeviceType(i)) {
            Log.e(TAG, "Invalid device type:" + i);
            this.mDeviceType = 0;
        } else {
            this.mDeviceType = i;
        }
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mStructure = charSequence3;
        this.mZone = charSequence4;
        this.mAppIntent = pendingIntent;
        this.mCustomColor = colorStateList;
        this.mCustomIcon = icon;
        if (i2 < 0 || i2 >= 5) {
            this.mStatus = 0;
            Log.e(TAG, "Status unknown:" + i2);
        } else {
            this.mStatus = i2;
        }
        this.mControlTemplate = controlTemplate;
        this.mStatusText = charSequence5;
        this.mAuthRequired = z;
        this.mCustomControl = new CustomControl();
    }

    Control(Parcel parcel) {
        this.mControlId = parcel.readString();
        this.mDeviceType = parcel.readInt();
        this.mTitle = parcel.readCharSequence();
        this.mSubtitle = parcel.readCharSequence();
        if (parcel.readByte() == 1) {
            this.mStructure = parcel.readCharSequence();
        } else {
            this.mStructure = null;
        }
        if (parcel.readByte() == 1) {
            this.mZone = parcel.readCharSequence();
        } else {
            this.mZone = null;
        }
        this.mAppIntent = PendingIntent.CREATOR.createFromParcel(parcel);
        if (parcel.readByte() == 1) {
            this.mCustomIcon = Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mCustomIcon = null;
        }
        if (parcel.readByte() == 1) {
            this.mCustomColor = ColorStateList.CREATOR.createFromParcel(parcel);
        } else {
            this.mCustomColor = null;
        }
        this.mStatus = parcel.readInt();
        this.mControlTemplate = ControlTemplateWrapper.CREATOR.createFromParcel(parcel).getWrappedTemplate();
        this.mStatusText = parcel.readCharSequence();
        this.mAuthRequired = parcel.readBoolean();
        this.mCustomControl = CustomControl.CREATOR.createFromParcel(parcel);
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mControlId);
        parcel.writeInt(this.mDeviceType);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeCharSequence(this.mSubtitle);
        if (this.mStructure != null) {
            parcel.writeByte((byte) 1);
            parcel.writeCharSequence(this.mStructure);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mZone != null) {
            parcel.writeByte((byte) 1);
            parcel.writeCharSequence(this.mZone);
        } else {
            parcel.writeByte((byte) 0);
        }
        this.mAppIntent.writeToParcel(parcel, i);
        if (this.mCustomIcon != null) {
            parcel.writeByte((byte) 1);
            this.mCustomIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mCustomColor != null) {
            parcel.writeByte((byte) 1);
            this.mCustomColor.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mStatus);
        new ControlTemplateWrapper(this.mControlTemplate).writeToParcel(parcel, i);
        parcel.writeCharSequence(this.mStatusText);
        parcel.writeBoolean(this.mAuthRequired);
        this.mCustomControl.writeToParcel(parcel, i);
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

        public StatelessBuilder(String str, PendingIntent pendingIntent) {
            this.mDeviceType = 0;
            this.mTitle = "";
            this.mSubtitle = "";
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(pendingIntent);
            this.mControlId = str;
            this.mAppIntent = pendingIntent;
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

        public StatelessBuilder setControlId(String str) {
            Preconditions.checkNotNull(str);
            this.mControlId = str;
            return this;
        }

        public StatelessBuilder setDeviceType(int i) {
            if (!DeviceTypes.validDeviceType(i)) {
                Log.e(TAG, "Invalid device type:" + i);
                this.mDeviceType = 0;
                return this;
            }
            this.mDeviceType = i;
            return this;
        }

        public StatelessBuilder setTitle(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            this.mTitle = charSequence;
            return this;
        }

        public StatelessBuilder setSubtitle(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            this.mSubtitle = charSequence;
            return this;
        }

        public StatelessBuilder setStructure(CharSequence charSequence) {
            this.mStructure = charSequence;
            return this;
        }

        public StatelessBuilder setZone(CharSequence charSequence) {
            this.mZone = charSequence;
            return this;
        }

        public StatelessBuilder setAppIntent(PendingIntent pendingIntent) {
            Preconditions.checkNotNull(pendingIntent);
            this.mAppIntent = pendingIntent;
            return this;
        }

        public StatelessBuilder setCustomIcon(Icon icon) {
            this.mCustomIcon = icon;
            return this;
        }

        public StatelessBuilder setCustomColor(ColorStateList colorStateList) {
            this.mCustomColor = colorStateList;
            return this;
        }

        public StatelessBuilder setUseCustomIconWithoutShadowBg(boolean z) {
            this.mCustomStatelessBuilder.setUseCustomIconWithoutShadowBg(z);
            return this;
        }

        public StatelessBuilder setUseCustomIconWithoutPadding(boolean z) {
            this.mCustomStatelessBuilder.setUseCustomIconWithoutPadding(z);
            return this;
        }

        public StatelessBuilder setLayoutType(int i) {
            this.mCustomStatelessBuilder.setLayoutType(i);
            return this;
        }

        public StatelessBuilder setCustomIconAnimationJson(String str, String str2) {
            this.mCustomStatelessBuilder.setCustomIconAnimationJson(str, str2);
            return this;
        }

        public StatelessBuilder setCustomIconAnimationStartAndEndFrame(int i, int i2) {
            this.mCustomStatelessBuilder.setCustomIconAnimationStartAndEndFrame(i, i2);
            return this;
        }

        public StatelessBuilder setCustomIconAnimationRepeatCount(int i) {
            this.mCustomStatelessBuilder.setCustomIconAnimationRepeatCount(i);
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

        public StatefulBuilder(String str, PendingIntent pendingIntent) {
            this.mDeviceType = 0;
            this.mTitle = "";
            this.mSubtitle = "";
            this.mStatus = 0;
            this.mControlTemplate = ControlTemplate.NO_TEMPLATE;
            this.mStatusText = "";
            this.mAuthRequired = true;
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(pendingIntent);
            this.mControlId = str;
            this.mAppIntent = pendingIntent;
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

        public StatefulBuilder setControlId(String str) {
            Preconditions.checkNotNull(str);
            this.mControlId = str;
            return this;
        }

        public StatefulBuilder setDeviceType(int i) {
            if (!DeviceTypes.validDeviceType(i)) {
                Log.e(TAG, "Invalid device type:" + i);
                this.mDeviceType = 0;
                return this;
            }
            this.mDeviceType = i;
            return this;
        }

        public StatefulBuilder setTitle(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            this.mTitle = charSequence;
            return this;
        }

        public StatefulBuilder setSubtitle(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            this.mSubtitle = charSequence;
            return this;
        }

        public StatefulBuilder setStructure(CharSequence charSequence) {
            this.mStructure = charSequence;
            return this;
        }

        public StatefulBuilder setZone(CharSequence charSequence) {
            this.mZone = charSequence;
            return this;
        }

        public StatefulBuilder setAppIntent(PendingIntent pendingIntent) {
            Preconditions.checkNotNull(pendingIntent);
            this.mAppIntent = pendingIntent;
            return this;
        }

        public StatefulBuilder setCustomIcon(Icon icon) {
            this.mCustomIcon = icon;
            return this;
        }

        public StatefulBuilder setCustomColor(ColorStateList colorStateList) {
            this.mCustomColor = colorStateList;
            return this;
        }

        public StatefulBuilder setStatus(int i) {
            if (i < 0 || i >= 5) {
                this.mStatus = 0;
                Log.e(TAG, "Status unknown:" + i);
                return this;
            }
            this.mStatus = i;
            return this;
        }

        public StatefulBuilder setControlTemplate(ControlTemplate controlTemplate) {
            Preconditions.checkNotNull(controlTemplate);
            this.mControlTemplate = controlTemplate;
            return this;
        }

        public StatefulBuilder setStatusText(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            this.mStatusText = charSequence;
            return this;
        }

        public StatefulBuilder setAuthRequired(boolean z) {
            this.mAuthRequired = z;
            return this;
        }

        public StatefulBuilder setCustomIconAnimationJson(String str, String str2) {
            this.mCustomStatefulBuilder.setCustomIconAnimationJson(str, str2);
            return this;
        }

        public StatefulBuilder setCustomIconAnimationStartAndEndFrame(int i, int i2) {
            this.mCustomStatefulBuilder.setCustomIconAnimationStartAndEndFrame(i, i2);
            return this;
        }

        public StatefulBuilder setCustomIconAnimationRepeatCount(int i) {
            this.mCustomStatefulBuilder.setCustomIconAnimationRepeatCount(i);
            return this;
        }

        public StatefulBuilder setActionIcon(Icon icon) {
            this.mCustomStatefulBuilder.setActionIcon(icon);
            return this;
        }

        public StatefulBuilder setUseFullScreenDetailDialog(boolean z) {
            this.mCustomStatefulBuilder.setUseFullScreenDetailDialog(z);
            return this;
        }

        public StatefulBuilder setAllowBasicActionWhenLocked(boolean z) {
            this.mCustomStatefulBuilder.setAllowBasicActionWhenLocked(z);
            return this;
        }

        public StatefulBuilder setStatusTextColor(ColorStateList colorStateList) {
            this.mCustomStatefulBuilder.setStatusTextColor(colorStateList);
            return this;
        }

        public StatefulBuilder setStatusIconType(int i) {
            this.mCustomStatefulBuilder.setStatusIconType(i);
            return this;
        }

        public StatefulBuilder setUseCustomIconWithoutShadowBg(boolean z) {
            this.mCustomStatefulBuilder.setUseCustomIconWithoutShadowBg(z);
            return this;
        }

        public StatefulBuilder setUseCustomIconWithoutPadding(boolean z) {
            this.mCustomStatefulBuilder.setUseCustomIconWithoutPadding(z);
            return this;
        }

        public StatefulBuilder setOrder(int i) {
            this.mCustomStatefulBuilder.setOrder(i);
            return this;
        }

        public StatefulBuilder setCustomStatusIcon(Icon icon) {
            this.mCustomStatefulBuilder.setCustomStatusIcon(icon);
            return this;
        }

        public StatefulBuilder setLayoutType(int i) {
            this.mCustomStatefulBuilder.setLayoutType(i);
            return this;
        }

        public StatefulBuilder setCustomSound(int i) {
            this.mCustomStatefulBuilder.setCustomSound(i);
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
