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
    public static final Parcelable.Creator<AvailableNetworkInfo> CREATOR =
            new Parcelable.Creator<
                    AvailableNetworkInfo>() { // from class:
                                              // android.telephony.AvailableNetworkInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AvailableNetworkInfo createFromParcel(Parcel in) {
                    return new AvailableNetworkInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AvailableNetworkInfo[] newArray(int size) {
                    return new AvailableNetworkInfo[size];
                }
            };
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = 3;
    public static final int PRIORITY_MED = 2;

    @Deprecated private ArrayList<Integer> mBands;
    private ArrayList<String> mMccMncs;
    private int mPriority;
    private ArrayList<RadioAccessSpecifier> mRadioAccessSpecifiers;
    private int mSubId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AvailableNetworkInfoPriority {}

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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mSubId);
        dest.writeInt(this.mPriority);
        dest.writeStringList(this.mMccMncs);
        dest.writeList(this.mBands);
        dest.writeList(this.mRadioAccessSpecifiers);
    }

    private AvailableNetworkInfo(Parcel in) {
        this.mSubId = in.readInt();
        this.mPriority = in.readInt();
        this.mMccMncs = new ArrayList<>();
        in.readStringList(this.mMccMncs);
        this.mBands = new ArrayList<>();
        in.readList(this.mBands, Integer.class.getClassLoader(), Integer.class);
        this.mRadioAccessSpecifiers = new ArrayList<>();
        in.readList(
                this.mRadioAccessSpecifiers,
                RadioAccessSpecifier.class.getClassLoader(),
                RadioAccessSpecifier.class);
    }

    public AvailableNetworkInfo(
            int subId, int priority, List<String> mccMncs, List<Integer> bands) {
        this(subId, priority, mccMncs, bands, new ArrayList());
    }

    private AvailableNetworkInfo(
            int subId,
            int priority,
            List<String> mccMncs,
            List<Integer> bands,
            List<RadioAccessSpecifier> radioAccessSpecifiers) {
        this.mSubId = subId;
        this.mPriority = priority;
        this.mMccMncs = new ArrayList<>(mccMncs);
        this.mBands = new ArrayList<>(bands);
        this.mRadioAccessSpecifiers = new ArrayList<>(radioAccessSpecifiers);
    }

    public boolean equals(Object o) {
        try {
            AvailableNetworkInfo ani = (AvailableNetworkInfo) o;
            return o != null
                    && this.mSubId == ani.mSubId
                    && this.mPriority == ani.mPriority
                    && this.mMccMncs != null
                    && this.mMccMncs.equals(ani.mMccMncs)
                    && this.mBands.equals(ani.mBands)
                    && this.mRadioAccessSpecifiers.equals(ani.getRadioAccessSpecifiers());
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mSubId),
                Integer.valueOf(this.mPriority),
                this.mMccMncs,
                this.mBands,
                this.mRadioAccessSpecifiers);
    }

    public String toString() {
        return "AvailableNetworkInfo: mSubId: "
                + this.mSubId
                + " mPriority: "
                + this.mPriority
                + " mMccMncs: "
                + Arrays.toString(this.mMccMncs.toArray())
                + " mBands: "
                + Arrays.toString(this.mBands.toArray())
                + " mRadioAccessSpecifiers: "
                + Arrays.toString(this.mRadioAccessSpecifiers.toArray());
    }

    public static final class Builder {
        private int mSubId;
        private int mPriority = 3;
        private ArrayList<String> mMccMncs = new ArrayList<>();
        private ArrayList<RadioAccessSpecifier> mRadioAccessSpecifiers = new ArrayList<>();

        public Builder(int subId) {
            this.mSubId = Integer.MIN_VALUE;
            this.mSubId = subId;
        }

        public Builder setPriority(int priority) {
            if (priority > 3 || priority < 1) {
                throw new IllegalArgumentException("A valid priority must be set");
            }
            this.mPriority = priority;
            return this;
        }

        public Builder setMccMncs(List<String> mccMncs) {
            Objects.requireNonNull(
                    mccMncs,
                    "A non-null List of mccmncs must be set. An empty List is still accepted."
                        + " Please read documentation in AvailableNetworkInfo to see consequences"
                        + " of an empty List.");
            this.mMccMncs = new ArrayList<>(mccMncs);
            return this;
        }

        public Builder setRadioAccessSpecifiers(List<RadioAccessSpecifier> radioAccessSpecifiers) {
            Objects.requireNonNull(
                    radioAccessSpecifiers,
                    "A non-null List of RadioAccessSpecifiers must be set. An empty List is still"
                        + " accepted. Please read documentation in AvailableNetworkInfo to see"
                        + " consequences of an empty List.");
            this.mRadioAccessSpecifiers = new ArrayList<>(radioAccessSpecifiers);
            return this;
        }

        public AvailableNetworkInfo build() {
            if (this.mSubId == Integer.MIN_VALUE) {
                throw new IllegalArgumentException("A valid subId must be set");
            }
            return new AvailableNetworkInfo(
                    this.mSubId,
                    this.mPriority,
                    this.mMccMncs,
                    new ArrayList(),
                    this.mRadioAccessSpecifiers);
        }
    }
}
