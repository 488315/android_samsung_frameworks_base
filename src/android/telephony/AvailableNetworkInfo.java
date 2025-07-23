package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class AvailableNetworkInfo implements Parcelable {
    public static final Parcelable.Creator<AvailableNetworkInfo> CREATOR = new Parcelable.Creator<AvailableNetworkInfo>() { // from class: android.telephony.AvailableNetworkInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AvailableNetworkInfo createFromParcel(Parcel parcel) {
            return new AvailableNetworkInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AvailableNetworkInfo[] newArray(int i) {
            return new AvailableNetworkInfo[i];
        }
    };
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = 3;
    public static final int PRIORITY_MED = 2;

    @Deprecated
    private ArrayList<Integer> mBands;
    private ArrayList<String> mMccMncs;
    private int mPriority;
    private ArrayList<RadioAccessSpecifier> mRadioAccessSpecifiers;
    private int mSubId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AvailableNetworkInfoPriority {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getSubId() {
        return this.mSubId;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public List<String> getMccMncs() {
        return (List) this.mMccMncs.clone();
    }

    public List<Integer> getBands() {
        return (List) this.mBands.clone();
    }

    public List<RadioAccessSpecifier> getRadioAccessSpecifiers() {
        return (List) this.mRadioAccessSpecifiers.clone();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSubId);
        parcel.writeInt(this.mPriority);
        parcel.writeStringList(this.mMccMncs);
        parcel.writeList(this.mBands);
        parcel.writeList(this.mRadioAccessSpecifiers);
    }

    private AvailableNetworkInfo(Parcel parcel) {
        this.mSubId = parcel.readInt();
        this.mPriority = parcel.readInt();
        ArrayList<String> arrayList = new ArrayList<>();
        this.mMccMncs = arrayList;
        parcel.readStringList(arrayList);
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        this.mBands = arrayList2;
        parcel.readList(arrayList2, Integer.class.getClassLoader(), Integer.class);
        ArrayList<RadioAccessSpecifier> arrayList3 = new ArrayList<>();
        this.mRadioAccessSpecifiers = arrayList3;
        parcel.readList(arrayList3, RadioAccessSpecifier.class.getClassLoader(), RadioAccessSpecifier.class);
    }

    public AvailableNetworkInfo(int i, int i2, List<String> list, List<Integer> list2) {
        this(i, i2, list, list2, new ArrayList());
    }

    private AvailableNetworkInfo(int i, int i2, List<String> list, List<Integer> list2, List<RadioAccessSpecifier> list3) {
        this.mSubId = i;
        this.mPriority = i2;
        this.mMccMncs = new ArrayList<>(list);
        this.mBands = new ArrayList<>(list2);
        this.mRadioAccessSpecifiers = new ArrayList<>(list3);
    }

    public boolean equals(Object obj) {
        AvailableNetworkInfo availableNetworkInfo;
        ArrayList<String> arrayList;
        try {
            availableNetworkInfo = (AvailableNetworkInfo) obj;
        } catch (ClassCastException unused) {
        }
        return obj != null && this.mSubId == availableNetworkInfo.mSubId && this.mPriority == availableNetworkInfo.mPriority && (arrayList = this.mMccMncs) != null && arrayList.equals(availableNetworkInfo.mMccMncs) && this.mBands.equals(availableNetworkInfo.mBands) && this.mRadioAccessSpecifiers.equals(availableNetworkInfo.getRadioAccessSpecifiers());
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mSubId), Integer.valueOf(this.mPriority), this.mMccMncs, this.mBands, this.mRadioAccessSpecifiers);
    }

    public String toString() {
        return "AvailableNetworkInfo: mSubId: " + this.mSubId + " mPriority: " + this.mPriority + " mMccMncs: " + Arrays.toString(this.mMccMncs.toArray()) + " mBands: " + Arrays.toString(this.mBands.toArray()) + " mRadioAccessSpecifiers: " + Arrays.toString(this.mRadioAccessSpecifiers.toArray());
    }

    public static final class Builder {
        private int mSubId;
        private int mPriority = 3;
        private ArrayList<String> mMccMncs = new ArrayList<>();
        private ArrayList<RadioAccessSpecifier> mRadioAccessSpecifiers = new ArrayList<>();

        public Builder(int i) {
            this.mSubId = Integer.MIN_VALUE;
            this.mSubId = i;
        }

        public Builder setPriority(int i) {
            if (i > 3 || i < 1) {
                throw new IllegalArgumentException("A valid priority must be set");
            }
            this.mPriority = i;
            return this;
        }

        public Builder setMccMncs(List<String> list) {
            Objects.requireNonNull(list, "A non-null List of mccmncs must be set. An empty List is still accepted. Please read documentation in AvailableNetworkInfo to see consequences of an empty List.");
            this.mMccMncs = new ArrayList<>(list);
            return this;
        }

        public Builder setRadioAccessSpecifiers(List<RadioAccessSpecifier> list) {
            Objects.requireNonNull(list, "A non-null List of RadioAccessSpecifiers must be set. An empty List is still accepted. Please read documentation in AvailableNetworkInfo to see consequences of an empty List.");
            this.mRadioAccessSpecifiers = new ArrayList<>(list);
            return this;
        }

        public AvailableNetworkInfo build() {
            if (this.mSubId == Integer.MIN_VALUE) {
                throw new IllegalArgumentException("A valid subId must be set");
            }
            return new AvailableNetworkInfo(this.mSubId, this.mPriority, this.mMccMncs, new ArrayList(), this.mRadioAccessSpecifiers);
        }
    }
}
