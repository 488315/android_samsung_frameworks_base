package android.app.smartspace.uitemplatedata;

import android.annotation.SystemApi;
import android.app.smartspace.SmartspaceUtils;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class CarouselTemplateData extends BaseTemplateData {
    public static final Parcelable.Creator<CarouselTemplateData> CREATOR = new Parcelable.Creator<CarouselTemplateData>() { // from class: android.app.smartspace.uitemplatedata.CarouselTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarouselTemplateData createFromParcel(Parcel parcel) {
            return new CarouselTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarouselTemplateData[] newArray(int i) {
            return new CarouselTemplateData[i];
        }
    };
    private final TapAction mCarouselAction;
    private final List<CarouselItem> mCarouselItems;

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    CarouselTemplateData(Parcel parcel) {
        super(parcel);
        this.mCarouselItems = parcel.createTypedArrayList(CarouselItem.CREATOR);
        this.mCarouselAction = (TapAction) parcel.readTypedObject(TapAction.CREATOR);
    }

    private CarouselTemplateData(int i, BaseTemplateData.SubItemInfo subItemInfo, BaseTemplateData.SubItemInfo subItemInfo2, BaseTemplateData.SubItemInfo subItemInfo3, BaseTemplateData.SubItemInfo subItemInfo4, BaseTemplateData.SubItemInfo subItemInfo5, int i2, List<CarouselItem> list, TapAction tapAction) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mCarouselItems = list;
        this.mCarouselAction = tapAction;
    }

    public List<CarouselItem> getCarouselItems() {
        return this.mCarouselItems;
    }

    public TapAction getCarouselAction() {
        return this.mCarouselAction;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mCarouselItems);
        parcel.writeTypedObject(this.mCarouselAction, i);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CarouselTemplateData) || !super.equals(obj)) {
            return false;
        }
        CarouselTemplateData carouselTemplateData = (CarouselTemplateData) obj;
        return this.mCarouselItems.equals(carouselTemplateData.mCarouselItems) && Objects.equals(this.mCarouselAction, carouselTemplateData.mCarouselAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.mCarouselItems, this.mCarouselAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public String toString() {
        return super.toString() + " + SmartspaceCarouselUiTemplateData{mCarouselItems=" + this.mCarouselItems + ", mCarouselActions=" + this.mCarouselAction + '}';
    }

    @SystemApi
    public static final class Builder extends BaseTemplateData.Builder {
        private TapAction mCarouselAction;
        private final List<CarouselItem> mCarouselItems;

        public Builder(List<CarouselItem> list) {
            super(4);
            this.mCarouselItems = (List) Objects.requireNonNull(list);
        }

        public Builder setCarouselAction(TapAction tapAction) {
            this.mCarouselAction = tapAction;
            return this;
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public CarouselTemplateData build() {
            if (this.mCarouselItems.isEmpty()) {
                throw new IllegalStateException("Carousel data is empty");
            }
            return new CarouselTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mCarouselItems, this.mCarouselAction);
        }
    }

    public static final class CarouselItem implements Parcelable {
        public static final Parcelable.Creator<CarouselItem> CREATOR = new Parcelable.Creator<CarouselItem>() { // from class: android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CarouselItem createFromParcel(Parcel parcel) {
                return new CarouselItem(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CarouselItem[] newArray(int i) {
                return new CarouselItem[i];
            }
        };
        private final Icon mImage;
        private final Text mLowerText;
        private final TapAction mTapAction;
        private final Text mUpperText;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        CarouselItem(Parcel parcel) {
            this.mUpperText = (Text) parcel.readTypedObject(Text.CREATOR);
            this.mImage = (Icon) parcel.readTypedObject(Icon.CREATOR);
            this.mLowerText = (Text) parcel.readTypedObject(Text.CREATOR);
            this.mTapAction = (TapAction) parcel.readTypedObject(TapAction.CREATOR);
        }

        private CarouselItem(Text text, Icon icon, Text text2, TapAction tapAction) {
            this.mUpperText = text;
            this.mImage = icon;
            this.mLowerText = text2;
            this.mTapAction = tapAction;
        }

        public Text getUpperText() {
            return this.mUpperText;
        }

        public Icon getImage() {
            return this.mImage;
        }

        public Text getLowerText() {
            return this.mLowerText;
        }

        public TapAction getTapAction() {
            return this.mTapAction;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedObject(this.mUpperText, i);
            parcel.writeTypedObject(this.mImage, i);
            parcel.writeTypedObject(this.mLowerText, i);
            parcel.writeTypedObject(this.mTapAction, i);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CarouselItem)) {
                return false;
            }
            CarouselItem carouselItem = (CarouselItem) obj;
            return SmartspaceUtils.isEqual(this.mUpperText, carouselItem.mUpperText) && Objects.equals(this.mImage, carouselItem.mImage) && SmartspaceUtils.isEqual(this.mLowerText, carouselItem.mLowerText) && Objects.equals(this.mTapAction, carouselItem.mTapAction);
        }

        public int hashCode() {
            return Objects.hash(this.mUpperText, this.mImage, this.mLowerText, this.mTapAction);
        }

        public String toString() {
            return "CarouselItem{mUpperText=" + this.mUpperText + ", mImage=" + this.mImage + ", mLowerText=" + this.mLowerText + ", mTapAction=" + this.mTapAction + '}';
        }

        @SystemApi
        public static final class Builder {
            private Icon mImage;
            private Text mLowerText;
            private TapAction mTapAction;
            private Text mUpperText;

            public Builder setUpperText(Text text) {
                this.mUpperText = text;
                return this;
            }

            public Builder setImage(Icon icon) {
                this.mImage = icon;
                return this;
            }

            public Builder setLowerText(Text text) {
                this.mLowerText = text;
                return this;
            }

            public Builder setTapAction(TapAction tapAction) {
                this.mTapAction = tapAction;
                return this;
            }

            public CarouselItem build() {
                if (SmartspaceUtils.isEmpty(this.mUpperText) && this.mImage == null && SmartspaceUtils.isEmpty(this.mLowerText)) {
                    throw new IllegalStateException("Carousel data is empty");
                }
                return new CarouselItem(this.mUpperText, this.mImage, this.mLowerText, this.mTapAction);
            }
        }
    }
}
