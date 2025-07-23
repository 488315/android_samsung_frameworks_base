package android.app.smartspace.uitemplatedata;

import android.annotation.SystemApi;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SubListTemplateData extends BaseTemplateData {
    public static final Parcelable.Creator<SubListTemplateData> CREATOR = new Parcelable.Creator<SubListTemplateData>() { // from class: android.app.smartspace.uitemplatedata.SubListTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubListTemplateData createFromParcel(Parcel parcel) {
            return new SubListTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubListTemplateData[] newArray(int i) {
            return new SubListTemplateData[i];
        }
    };
    private final TapAction mSubListAction;
    private final Icon mSubListIcon;
    private final List<Text> mSubListTexts;

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SubListTemplateData(Parcel parcel) {
        super(parcel);
        this.mSubListIcon = (Icon) parcel.readTypedObject(Icon.CREATOR);
        this.mSubListTexts = parcel.createTypedArrayList(Text.CREATOR);
        this.mSubListAction = (TapAction) parcel.readTypedObject(TapAction.CREATOR);
    }

    private SubListTemplateData(int i, BaseTemplateData.SubItemInfo subItemInfo, BaseTemplateData.SubItemInfo subItemInfo2, BaseTemplateData.SubItemInfo subItemInfo3, BaseTemplateData.SubItemInfo subItemInfo4, BaseTemplateData.SubItemInfo subItemInfo5, int i2, Icon icon, List<Text> list, TapAction tapAction) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mSubListIcon = icon;
        this.mSubListTexts = list;
        this.mSubListAction = tapAction;
    }

    public Icon getSubListIcon() {
        return this.mSubListIcon;
    }

    public List<Text> getSubListTexts() {
        return this.mSubListTexts;
    }

    public TapAction getSubListAction() {
        return this.mSubListAction;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mSubListIcon, i);
        parcel.writeTypedList(this.mSubListTexts);
        parcel.writeTypedObject(this.mSubListAction, i);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SubListTemplateData) || !super.equals(obj)) {
            return false;
        }
        SubListTemplateData subListTemplateData = (SubListTemplateData) obj;
        return Objects.equals(this.mSubListIcon, subListTemplateData.mSubListIcon) && Objects.equals(this.mSubListTexts, subListTemplateData.mSubListTexts) && Objects.equals(this.mSubListAction, subListTemplateData.mSubListAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.mSubListIcon, this.mSubListTexts, this.mSubListAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public String toString() {
        return super.toString() + " + SmartspaceSubListUiTemplateData{mSubListIcon=" + this.mSubListIcon + ", mSubListTexts=" + this.mSubListTexts + ", mSubListAction=" + this.mSubListAction + '}';
    }

    @SystemApi
    public static final class Builder extends BaseTemplateData.Builder {
        private TapAction mSubListAction;
        private Icon mSubListIcon;
        private final List<Text> mSubListTexts;

        public Builder(List<Text> list) {
            super(3);
            this.mSubListTexts = (List) Objects.requireNonNull(list);
        }

        public Builder setSubListIcon(Icon icon) {
            this.mSubListIcon = icon;
            return this;
        }

        public Builder setSubListAction(TapAction tapAction) {
            this.mSubListAction = tapAction;
            return this;
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public SubListTemplateData build() {
            return new SubListTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mSubListIcon, this.mSubListTexts, this.mSubListAction);
        }
    }
}
