package android.app.smartspace.uitemplatedata;

import android.annotation.SystemApi;
import android.app.smartspace.SmartspaceUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public class BaseTemplateData implements Parcelable {
    public static final Parcelable.Creator<BaseTemplateData> CREATOR = new Parcelable.Creator<BaseTemplateData>() { // from class: android.app.smartspace.uitemplatedata.BaseTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseTemplateData createFromParcel(Parcel parcel) {
            return new BaseTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseTemplateData[] newArray(int i) {
            return new BaseTemplateData[i];
        }
    };
    private final int mLayoutWeight;
    private final SubItemInfo mPrimaryItem;
    private final SubItemInfo mSubtitleItem;
    private final SubItemInfo mSubtitleSupplementalItem;
    private final SubItemInfo mSupplementalAlarmItem;
    private final SubItemInfo mSupplementalLineItem;
    private final int mTemplateType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    BaseTemplateData(Parcel parcel) {
        this.mTemplateType = parcel.readInt();
        this.mPrimaryItem = (SubItemInfo) parcel.readTypedObject(SubItemInfo.CREATOR);
        this.mSubtitleItem = (SubItemInfo) parcel.readTypedObject(SubItemInfo.CREATOR);
        this.mSubtitleSupplementalItem = (SubItemInfo) parcel.readTypedObject(SubItemInfo.CREATOR);
        this.mSupplementalLineItem = (SubItemInfo) parcel.readTypedObject(SubItemInfo.CREATOR);
        this.mSupplementalAlarmItem = (SubItemInfo) parcel.readTypedObject(SubItemInfo.CREATOR);
        this.mLayoutWeight = parcel.readInt();
    }

    BaseTemplateData(int i, SubItemInfo subItemInfo, SubItemInfo subItemInfo2, SubItemInfo subItemInfo3, SubItemInfo subItemInfo4, SubItemInfo subItemInfo5, int i2) {
        this.mTemplateType = i;
        this.mPrimaryItem = subItemInfo;
        this.mSubtitleItem = subItemInfo2;
        this.mSubtitleSupplementalItem = subItemInfo3;
        this.mSupplementalLineItem = subItemInfo4;
        this.mSupplementalAlarmItem = subItemInfo5;
        this.mLayoutWeight = i2;
    }

    public int getTemplateType() {
        return this.mTemplateType;
    }

    public SubItemInfo getPrimaryItem() {
        return this.mPrimaryItem;
    }

    public SubItemInfo getSubtitleItem() {
        return this.mSubtitleItem;
    }

    public SubItemInfo getSubtitleSupplementalItem() {
        return this.mSubtitleSupplementalItem;
    }

    public SubItemInfo getSupplementalLineItem() {
        return this.mSupplementalLineItem;
    }

    public SubItemInfo getSupplementalAlarmItem() {
        return this.mSupplementalAlarmItem;
    }

    public int getLayoutWeight() {
        return this.mLayoutWeight;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTemplateType);
        parcel.writeTypedObject(this.mPrimaryItem, i);
        parcel.writeTypedObject(this.mSubtitleItem, i);
        parcel.writeTypedObject(this.mSubtitleSupplementalItem, i);
        parcel.writeTypedObject(this.mSupplementalLineItem, i);
        parcel.writeTypedObject(this.mSupplementalAlarmItem, i);
        parcel.writeInt(this.mLayoutWeight);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BaseTemplateData)) {
            return false;
        }
        BaseTemplateData baseTemplateData = (BaseTemplateData) obj;
        return this.mTemplateType == baseTemplateData.mTemplateType && this.mLayoutWeight == baseTemplateData.mLayoutWeight && Objects.equals(this.mPrimaryItem, baseTemplateData.mPrimaryItem) && Objects.equals(this.mSubtitleItem, baseTemplateData.mSubtitleItem) && Objects.equals(this.mSubtitleSupplementalItem, baseTemplateData.mSubtitleSupplementalItem) && Objects.equals(this.mSupplementalLineItem, baseTemplateData.mSupplementalLineItem) && Objects.equals(this.mSupplementalAlarmItem, baseTemplateData.mSupplementalAlarmItem);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mTemplateType), this.mPrimaryItem, this.mSubtitleItem, this.mSubtitleSupplementalItem, this.mSupplementalLineItem, this.mSupplementalAlarmItem, Integer.valueOf(this.mLayoutWeight));
    }

    public String toString() {
        return "BaseTemplateData{mTemplateType=" + this.mTemplateType + ", mPrimaryItem=" + this.mPrimaryItem + ", mSubtitleItem=" + this.mSubtitleItem + ", mSubtitleSupplementalItem=" + this.mSubtitleSupplementalItem + ", mSupplementalLineItem=" + this.mSupplementalLineItem + ", mSupplementalAlarmItem=" + this.mSupplementalAlarmItem + ", mLayoutWeight=" + this.mLayoutWeight + '}';
    }

    @SystemApi
    public static class Builder {
        private int mLayoutWeight = 0;
        private SubItemInfo mPrimaryItem;
        private SubItemInfo mSubtitleItem;
        private SubItemInfo mSubtitleSupplementalItem;
        private SubItemInfo mSupplementalAlarmItem;
        private SubItemInfo mSupplementalLineItem;
        private final int mTemplateType;

        public Builder(int i) {
            this.mTemplateType = i;
        }

        int getTemplateType() {
            return this.mTemplateType;
        }

        SubItemInfo getPrimaryItem() {
            return this.mPrimaryItem;
        }

        SubItemInfo getSubtitleItem() {
            return this.mSubtitleItem;
        }

        SubItemInfo getSubtitleSupplemtnalItem() {
            return this.mSubtitleSupplementalItem;
        }

        SubItemInfo getSupplementalLineItem() {
            return this.mSupplementalLineItem;
        }

        SubItemInfo getSupplementalAlarmItem() {
            return this.mSupplementalAlarmItem;
        }

        int getLayoutWeight() {
            return this.mLayoutWeight;
        }

        public Builder setPrimaryItem(SubItemInfo subItemInfo) {
            this.mPrimaryItem = subItemInfo;
            return this;
        }

        public Builder setSubtitleItem(SubItemInfo subItemInfo) {
            this.mSubtitleItem = subItemInfo;
            return this;
        }

        public Builder setSubtitleSupplementalItem(SubItemInfo subItemInfo) {
            this.mSubtitleSupplementalItem = subItemInfo;
            return this;
        }

        public Builder setSupplementalLineItem(SubItemInfo subItemInfo) {
            this.mSupplementalLineItem = subItemInfo;
            return this;
        }

        public Builder setSupplementalAlarmItem(SubItemInfo subItemInfo) {
            this.mSupplementalAlarmItem = subItemInfo;
            return this;
        }

        public Builder setLayoutWeight(int i) {
            this.mLayoutWeight = i;
            return this;
        }

        public BaseTemplateData build() {
            return new BaseTemplateData(this.mTemplateType, this.mPrimaryItem, this.mSubtitleItem, this.mSubtitleSupplementalItem, this.mSupplementalLineItem, this.mSupplementalAlarmItem, this.mLayoutWeight);
        }
    }

    public static final class SubItemInfo implements Parcelable {
        public static final Parcelable.Creator<SubItemInfo> CREATOR = new Parcelable.Creator<SubItemInfo>() { // from class: android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SubItemInfo createFromParcel(Parcel parcel) {
                return new SubItemInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SubItemInfo[] newArray(int i) {
                return new SubItemInfo[i];
            }
        };
        private final Icon mIcon;
        private final SubItemLoggingInfo mLoggingInfo;
        private final TapAction mTapAction;
        private final Text mText;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        SubItemInfo(Parcel parcel) {
            this.mText = (Text) parcel.readTypedObject(Text.CREATOR);
            this.mIcon = (Icon) parcel.readTypedObject(Icon.CREATOR);
            this.mTapAction = (TapAction) parcel.readTypedObject(TapAction.CREATOR);
            this.mLoggingInfo = (SubItemLoggingInfo) parcel.readTypedObject(SubItemLoggingInfo.CREATOR);
        }

        private SubItemInfo(Text text, Icon icon, TapAction tapAction, SubItemLoggingInfo subItemLoggingInfo) {
            this.mText = text;
            this.mIcon = icon;
            this.mTapAction = tapAction;
            this.mLoggingInfo = subItemLoggingInfo;
        }

        public Text getText() {
            return this.mText;
        }

        public Icon getIcon() {
            return this.mIcon;
        }

        public TapAction getTapAction() {
            return this.mTapAction;
        }

        public SubItemLoggingInfo getLoggingInfo() {
            return this.mLoggingInfo;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedObject(this.mText, i);
            parcel.writeTypedObject(this.mIcon, i);
            parcel.writeTypedObject(this.mTapAction, i);
            parcel.writeTypedObject(this.mLoggingInfo, i);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SubItemInfo)) {
                return false;
            }
            SubItemInfo subItemInfo = (SubItemInfo) obj;
            return SmartspaceUtils.isEqual(this.mText, subItemInfo.mText) && Objects.equals(this.mIcon, subItemInfo.mIcon) && Objects.equals(this.mTapAction, subItemInfo.mTapAction) && Objects.equals(this.mLoggingInfo, subItemInfo.mLoggingInfo);
        }

        public int hashCode() {
            return Objects.hash(this.mText, this.mIcon, this.mTapAction, this.mLoggingInfo);
        }

        public String toString() {
            return "SubItemInfo{mText=" + this.mText + ", mIcon=" + this.mIcon + ", mTapAction=" + this.mTapAction + ", mLoggingInfo=" + this.mLoggingInfo + '}';
        }

        @SystemApi
        public static final class Builder {
            private Icon mIcon;
            private SubItemLoggingInfo mLoggingInfo;
            private TapAction mTapAction;
            private Text mText;

            public Builder setText(Text text) {
                this.mText = text;
                return this;
            }

            public Builder setIcon(Icon icon) {
                this.mIcon = icon;
                return this;
            }

            public Builder setTapAction(TapAction tapAction) {
                this.mTapAction = tapAction;
                return this;
            }

            public Builder setLoggingInfo(SubItemLoggingInfo subItemLoggingInfo) {
                this.mLoggingInfo = subItemLoggingInfo;
                return this;
            }

            public SubItemInfo build() {
                if (SmartspaceUtils.isEmpty(this.mText) && this.mIcon == null && this.mTapAction == null && this.mLoggingInfo == null) {
                    throw new IllegalStateException("SubItem data is empty");
                }
                return new SubItemInfo(this.mText, this.mIcon, this.mTapAction, this.mLoggingInfo);
            }
        }
    }

    public static final class SubItemLoggingInfo implements Parcelable {
        public static final Parcelable.Creator<SubItemLoggingInfo> CREATOR = new Parcelable.Creator<SubItemLoggingInfo>() { // from class: android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SubItemLoggingInfo createFromParcel(Parcel parcel) {
                return new SubItemLoggingInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SubItemLoggingInfo[] newArray(int i) {
                return new SubItemLoggingInfo[i];
            }
        };
        private final int mFeatureType;
        private final int mInstanceId;
        private final CharSequence mPackageName;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        SubItemLoggingInfo(Parcel parcel) {
            this.mInstanceId = parcel.readInt();
            this.mFeatureType = parcel.readInt();
            this.mPackageName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }

        private SubItemLoggingInfo(int i, int i2, CharSequence charSequence) {
            this.mInstanceId = i;
            this.mFeatureType = i2;
            this.mPackageName = charSequence;
        }

        public int getInstanceId() {
            return this.mInstanceId;
        }

        public int getFeatureType() {
            return this.mFeatureType;
        }

        public CharSequence getPackageName() {
            return this.mPackageName;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mInstanceId);
            parcel.writeInt(this.mFeatureType);
            TextUtils.writeToParcel(this.mPackageName, parcel, i);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SubItemLoggingInfo)) {
                return false;
            }
            SubItemLoggingInfo subItemLoggingInfo = (SubItemLoggingInfo) obj;
            return this.mInstanceId == subItemLoggingInfo.mInstanceId && this.mFeatureType == subItemLoggingInfo.mFeatureType && SmartspaceUtils.isEqual(this.mPackageName, subItemLoggingInfo.mPackageName);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mInstanceId), Integer.valueOf(this.mFeatureType), this.mPackageName);
        }

        public String toString() {
            return "SubItemLoggingInfo{mInstanceId=" + this.mInstanceId + ", mFeatureType=" + this.mFeatureType + ", mPackageName=" + ((Object) this.mPackageName) + '}';
        }

        @SystemApi
        public static final class Builder {
            private final int mFeatureType;
            private final int mInstanceId;
            private CharSequence mPackageName;

            public Builder(int i, int i2) {
                this.mInstanceId = i;
                this.mFeatureType = i2;
            }

            public Builder setPackageName(CharSequence charSequence) {
                this.mPackageName = charSequence;
                return this;
            }

            public SubItemLoggingInfo build() {
                return new SubItemLoggingInfo(this.mInstanceId, this.mFeatureType, this.mPackageName);
            }
        }
    }
}
