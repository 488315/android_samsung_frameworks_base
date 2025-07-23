package android.app.smartspace.uitemplatedata;

import android.annotation.SystemApi;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class CombinedCardsTemplateData extends BaseTemplateData {
    public static final Parcelable.Creator<CombinedCardsTemplateData> CREATOR = new Parcelable.Creator<CombinedCardsTemplateData>() { // from class: android.app.smartspace.uitemplatedata.CombinedCardsTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CombinedCardsTemplateData createFromParcel(Parcel parcel) {
            return new CombinedCardsTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CombinedCardsTemplateData[] newArray(int i) {
            return new CombinedCardsTemplateData[i];
        }
    };
    private final List<BaseTemplateData> mCombinedCardDataList;

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    CombinedCardsTemplateData(Parcel parcel) {
        super(parcel);
        this.mCombinedCardDataList = parcel.createTypedArrayList(BaseTemplateData.CREATOR);
    }

    private CombinedCardsTemplateData(int i, BaseTemplateData.SubItemInfo subItemInfo, BaseTemplateData.SubItemInfo subItemInfo2, BaseTemplateData.SubItemInfo subItemInfo3, BaseTemplateData.SubItemInfo subItemInfo4, BaseTemplateData.SubItemInfo subItemInfo5, int i2, List<BaseTemplateData> list) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mCombinedCardDataList = list;
    }

    public List<BaseTemplateData> getCombinedCardDataList() {
        return this.mCombinedCardDataList;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mCombinedCardDataList);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof CombinedCardsTemplateData) && super.equals(obj)) {
            return this.mCombinedCardDataList.equals(((CombinedCardsTemplateData) obj).mCombinedCardDataList);
        }
        return false;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.mCombinedCardDataList);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public String toString() {
        return super.toString() + " + SmartspaceCombinedCardsUiTemplateData{mCombinedCardDataList=" + this.mCombinedCardDataList + '}';
    }

    @SystemApi
    public static final class Builder extends BaseTemplateData.Builder {
        private final List<BaseTemplateData> mCombinedCardDataList;

        public Builder(List<BaseTemplateData> list) {
            super(6);
            this.mCombinedCardDataList = (List) Objects.requireNonNull(list);
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public CombinedCardsTemplateData build() {
            if (this.mCombinedCardDataList == null) {
                throw new IllegalStateException("Please assign a value to all @NonNull args.");
            }
            return new CombinedCardsTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mCombinedCardDataList);
        }
    }
}
