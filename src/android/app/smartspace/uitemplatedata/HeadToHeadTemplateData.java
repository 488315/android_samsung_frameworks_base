package android.app.smartspace.uitemplatedata;

import android.annotation.SystemApi;
import android.app.smartspace.SmartspaceUtils;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class HeadToHeadTemplateData extends BaseTemplateData {
    public static final Parcelable.Creator<HeadToHeadTemplateData> CREATOR = new Parcelable.Creator<HeadToHeadTemplateData>() { // from class: android.app.smartspace.uitemplatedata.HeadToHeadTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HeadToHeadTemplateData createFromParcel(Parcel parcel) {
            return new HeadToHeadTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HeadToHeadTemplateData[] newArray(int i) {
            return new HeadToHeadTemplateData[i];
        }
    };
    private final TapAction mHeadToHeadAction;
    private final Icon mHeadToHeadFirstCompetitorIcon;
    private final Text mHeadToHeadFirstCompetitorText;
    private final Icon mHeadToHeadSecondCompetitorIcon;
    private final Text mHeadToHeadSecondCompetitorText;
    private final Text mHeadToHeadTitle;

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    HeadToHeadTemplateData(Parcel parcel) {
        super(parcel);
        this.mHeadToHeadTitle = (Text) parcel.readTypedObject(Text.CREATOR);
        this.mHeadToHeadFirstCompetitorIcon = (Icon) parcel.readTypedObject(Icon.CREATOR);
        this.mHeadToHeadSecondCompetitorIcon = (Icon) parcel.readTypedObject(Icon.CREATOR);
        this.mHeadToHeadFirstCompetitorText = (Text) parcel.readTypedObject(Text.CREATOR);
        this.mHeadToHeadSecondCompetitorText = (Text) parcel.readTypedObject(Text.CREATOR);
        this.mHeadToHeadAction = (TapAction) parcel.readTypedObject(TapAction.CREATOR);
    }

    private HeadToHeadTemplateData(int i, BaseTemplateData.SubItemInfo subItemInfo, BaseTemplateData.SubItemInfo subItemInfo2, BaseTemplateData.SubItemInfo subItemInfo3, BaseTemplateData.SubItemInfo subItemInfo4, BaseTemplateData.SubItemInfo subItemInfo5, int i2, Text text, Icon icon, Icon icon2, Text text2, Text text3, TapAction tapAction) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mHeadToHeadTitle = text;
        this.mHeadToHeadFirstCompetitorIcon = icon;
        this.mHeadToHeadSecondCompetitorIcon = icon2;
        this.mHeadToHeadFirstCompetitorText = text2;
        this.mHeadToHeadSecondCompetitorText = text3;
        this.mHeadToHeadAction = tapAction;
    }

    public Text getHeadToHeadTitle() {
        return this.mHeadToHeadTitle;
    }

    public Icon getHeadToHeadFirstCompetitorIcon() {
        return this.mHeadToHeadFirstCompetitorIcon;
    }

    public Icon getHeadToHeadSecondCompetitorIcon() {
        return this.mHeadToHeadSecondCompetitorIcon;
    }

    public Text getHeadToHeadFirstCompetitorText() {
        return this.mHeadToHeadFirstCompetitorText;
    }

    public Text getHeadToHeadSecondCompetitorText() {
        return this.mHeadToHeadSecondCompetitorText;
    }

    public TapAction getHeadToHeadAction() {
        return this.mHeadToHeadAction;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mHeadToHeadTitle, i);
        parcel.writeTypedObject(this.mHeadToHeadFirstCompetitorIcon, i);
        parcel.writeTypedObject(this.mHeadToHeadSecondCompetitorIcon, i);
        parcel.writeTypedObject(this.mHeadToHeadFirstCompetitorText, i);
        parcel.writeTypedObject(this.mHeadToHeadSecondCompetitorText, i);
        parcel.writeTypedObject(this.mHeadToHeadAction, i);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HeadToHeadTemplateData) || !super.equals(obj)) {
            return false;
        }
        HeadToHeadTemplateData headToHeadTemplateData = (HeadToHeadTemplateData) obj;
        return SmartspaceUtils.isEqual(this.mHeadToHeadTitle, headToHeadTemplateData.mHeadToHeadTitle) && Objects.equals(this.mHeadToHeadFirstCompetitorIcon, headToHeadTemplateData.mHeadToHeadFirstCompetitorIcon) && Objects.equals(this.mHeadToHeadSecondCompetitorIcon, headToHeadTemplateData.mHeadToHeadSecondCompetitorIcon) && SmartspaceUtils.isEqual(this.mHeadToHeadFirstCompetitorText, headToHeadTemplateData.mHeadToHeadFirstCompetitorText) && SmartspaceUtils.isEqual(this.mHeadToHeadSecondCompetitorText, headToHeadTemplateData.mHeadToHeadSecondCompetitorText) && Objects.equals(this.mHeadToHeadAction, headToHeadTemplateData.mHeadToHeadAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.mHeadToHeadTitle, this.mHeadToHeadFirstCompetitorIcon, this.mHeadToHeadSecondCompetitorIcon, this.mHeadToHeadFirstCompetitorText, this.mHeadToHeadSecondCompetitorText, this.mHeadToHeadAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public String toString() {
        return super.toString() + " + SmartspaceHeadToHeadUiTemplateData{mH2HTitle=" + this.mHeadToHeadTitle + ", mH2HFirstCompetitorIcon=" + this.mHeadToHeadFirstCompetitorIcon + ", mH2HSecondCompetitorIcon=" + this.mHeadToHeadSecondCompetitorIcon + ", mH2HFirstCompetitorText=" + this.mHeadToHeadFirstCompetitorText + ", mH2HSecondCompetitorText=" + this.mHeadToHeadSecondCompetitorText + ", mH2HAction=" + this.mHeadToHeadAction + '}';
    }

    @SystemApi
    public static final class Builder extends BaseTemplateData.Builder {
        private TapAction mHeadToHeadAction;
        private Icon mHeadToHeadFirstCompetitorIcon;
        private Text mHeadToHeadFirstCompetitorText;
        private Icon mHeadToHeadSecondCompetitorIcon;
        private Text mHeadToHeadSecondCompetitorText;
        private Text mHeadToHeadTitle;

        public Builder() {
            super(5);
        }

        public Builder setHeadToHeadTitle(Text text) {
            this.mHeadToHeadTitle = text;
            return this;
        }

        public Builder setHeadToHeadFirstCompetitorIcon(Icon icon) {
            this.mHeadToHeadFirstCompetitorIcon = icon;
            return this;
        }

        public Builder setHeadToHeadSecondCompetitorIcon(Icon icon) {
            this.mHeadToHeadSecondCompetitorIcon = icon;
            return this;
        }

        public Builder setHeadToHeadFirstCompetitorText(Text text) {
            this.mHeadToHeadFirstCompetitorText = text;
            return this;
        }

        public Builder setHeadToHeadSecondCompetitorText(Text text) {
            this.mHeadToHeadSecondCompetitorText = text;
            return this;
        }

        public Builder setHeadToHeadAction(TapAction tapAction) {
            this.mHeadToHeadAction = tapAction;
            return this;
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public HeadToHeadTemplateData build() {
            return new HeadToHeadTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mHeadToHeadTitle, this.mHeadToHeadFirstCompetitorIcon, this.mHeadToHeadSecondCompetitorIcon, this.mHeadToHeadFirstCompetitorText, this.mHeadToHeadSecondCompetitorText, this.mHeadToHeadAction);
        }
    }
}
