package android.app.smartspace.uitemplatedata;

import android.annotation.SystemApi;
import android.app.smartspace.SmartspaceUtils;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SubCardTemplateData extends BaseTemplateData {
    public static final Parcelable.Creator<SubCardTemplateData> CREATOR = new Parcelable.Creator<SubCardTemplateData>() { // from class: android.app.smartspace.uitemplatedata.SubCardTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubCardTemplateData createFromParcel(Parcel parcel) {
            return new SubCardTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubCardTemplateData[] newArray(int i) {
            return new SubCardTemplateData[i];
        }
    };
    private final TapAction mSubCardAction;
    private final Icon mSubCardIcon;
    private final Text mSubCardText;

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SubCardTemplateData(Parcel parcel) {
        super(parcel);
        this.mSubCardIcon = (Icon) parcel.readTypedObject(Icon.CREATOR);
        this.mSubCardText = (Text) parcel.readTypedObject(Text.CREATOR);
        this.mSubCardAction = (TapAction) parcel.readTypedObject(TapAction.CREATOR);
    }

    private SubCardTemplateData(int i, BaseTemplateData.SubItemInfo subItemInfo, BaseTemplateData.SubItemInfo subItemInfo2, BaseTemplateData.SubItemInfo subItemInfo3, BaseTemplateData.SubItemInfo subItemInfo4, BaseTemplateData.SubItemInfo subItemInfo5, int i2, Icon icon, Text text, TapAction tapAction) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mSubCardIcon = icon;
        this.mSubCardText = text;
        this.mSubCardAction = tapAction;
    }

    public Icon getSubCardIcon() {
        return this.mSubCardIcon;
    }

    public Text getSubCardText() {
        return this.mSubCardText;
    }

    public TapAction getSubCardAction() {
        return this.mSubCardAction;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mSubCardIcon, i);
        parcel.writeTypedObject(this.mSubCardText, i);
        parcel.writeTypedObject(this.mSubCardAction, i);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SubCardTemplateData) || !super.equals(obj)) {
            return false;
        }
        SubCardTemplateData subCardTemplateData = (SubCardTemplateData) obj;
        return this.mSubCardIcon.equals(subCardTemplateData.mSubCardIcon) && SmartspaceUtils.isEqual(this.mSubCardText, subCardTemplateData.mSubCardText) && Objects.equals(this.mSubCardAction, subCardTemplateData.mSubCardAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.mSubCardIcon, this.mSubCardText, this.mSubCardAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public String toString() {
        return super.toString() + " + SmartspaceSubCardUiTemplateData{mSubCardIcon=" + this.mSubCardIcon + ", mSubCardText=" + this.mSubCardText + ", mSubCardAction=" + this.mSubCardAction + '}';
    }

    @SystemApi
    public static final class Builder extends BaseTemplateData.Builder {
        private TapAction mSubCardAction;
        private final Icon mSubCardIcon;
        private Text mSubCardText;

        public Builder(Icon icon) {
            super(7);
            this.mSubCardIcon = (Icon) Objects.requireNonNull(icon);
        }

        public Builder setSubCardText(Text text) {
            this.mSubCardText = text;
            return this;
        }

        public Builder setSubCardAction(TapAction tapAction) {
            this.mSubCardAction = tapAction;
            return this;
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public SubCardTemplateData build() {
            return new SubCardTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mSubCardIcon, this.mSubCardText, this.mSubCardAction);
        }
    }
}
