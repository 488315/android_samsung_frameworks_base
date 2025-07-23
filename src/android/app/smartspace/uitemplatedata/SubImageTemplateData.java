package android.app.smartspace.uitemplatedata;

import android.annotation.SystemApi;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SubImageTemplateData extends BaseTemplateData {
    public static final Parcelable.Creator<SubImageTemplateData> CREATOR = new Parcelable.Creator<SubImageTemplateData>() { // from class: android.app.smartspace.uitemplatedata.SubImageTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubImageTemplateData createFromParcel(Parcel parcel) {
            return new SubImageTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubImageTemplateData[] newArray(int i) {
            return new SubImageTemplateData[i];
        }
    };
    private final TapAction mSubImageAction;
    private final List<Text> mSubImageTexts;
    private final List<Icon> mSubImages;

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SubImageTemplateData(Parcel parcel) {
        super(parcel);
        this.mSubImageTexts = parcel.createTypedArrayList(Text.CREATOR);
        this.mSubImages = parcel.createTypedArrayList(Icon.CREATOR);
        this.mSubImageAction = (TapAction) parcel.readTypedObject(TapAction.CREATOR);
    }

    private SubImageTemplateData(int i, BaseTemplateData.SubItemInfo subItemInfo, BaseTemplateData.SubItemInfo subItemInfo2, BaseTemplateData.SubItemInfo subItemInfo3, BaseTemplateData.SubItemInfo subItemInfo4, BaseTemplateData.SubItemInfo subItemInfo5, int i2, List<Text> list, List<Icon> list2, TapAction tapAction) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mSubImageTexts = list;
        this.mSubImages = list2;
        this.mSubImageAction = tapAction;
    }

    public List<Text> getSubImageTexts() {
        return this.mSubImageTexts;
    }

    public List<Icon> getSubImages() {
        return this.mSubImages;
    }

    public TapAction getSubImageAction() {
        return this.mSubImageAction;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mSubImageTexts);
        parcel.writeTypedList(this.mSubImages);
        parcel.writeTypedObject(this.mSubImageAction, i);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SubImageTemplateData) || !super.equals(obj)) {
            return false;
        }
        SubImageTemplateData subImageTemplateData = (SubImageTemplateData) obj;
        return Objects.equals(this.mSubImageTexts, subImageTemplateData.mSubImageTexts) && Objects.equals(this.mSubImages, subImageTemplateData.mSubImages) && Objects.equals(this.mSubImageAction, subImageTemplateData.mSubImageAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.mSubImageTexts, this.mSubImages, this.mSubImageAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public String toString() {
        return super.toString() + " + SmartspaceSubImageUiTemplateData{mSubImageTexts=" + this.mSubImageTexts + ", mSubImages=" + this.mSubImages + ", mSubImageAction=" + this.mSubImageAction + '}';
    }

    @SystemApi
    public static final class Builder extends BaseTemplateData.Builder {
        private TapAction mSubImageAction;
        private final List<Text> mSubImageTexts;
        private final List<Icon> mSubImages;

        public Builder(List<Text> list, List<Icon> list2) {
            super(2);
            this.mSubImageTexts = (List) Objects.requireNonNull(list);
            this.mSubImages = (List) Objects.requireNonNull(list2);
        }

        public Builder setSubImageAction(TapAction tapAction) {
            this.mSubImageAction = tapAction;
            return this;
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public SubImageTemplateData build() {
            return new SubImageTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mSubImageTexts, this.mSubImages, this.mSubImageAction);
        }
    }
}
