package android.view.textservice;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/* loaded from: classes4.dex */
public final class SentenceSuggestionsInfo implements Parcelable {
    public static final Parcelable.Creator<SentenceSuggestionsInfo> CREATOR =
            new Parcelable.Creator<
                    SentenceSuggestionsInfo>() { // from class:
                                                 // android.view.textservice.SentenceSuggestionsInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SentenceSuggestionsInfo createFromParcel(Parcel source) {
                    return new SentenceSuggestionsInfo(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SentenceSuggestionsInfo[] newArray(int size) {
                    return new SentenceSuggestionsInfo[size];
                }
            };
    private final int[] mLengths;
    private final int[] mOffsets;
    private final SuggestionsInfo[] mSuggestionsInfos;

    public SentenceSuggestionsInfo(
            SuggestionsInfo[] suggestionsInfos, int[] offsets, int[] lengths) {
        if (suggestionsInfos == null || offsets == null || lengths == null) {
            throw new NullPointerException();
        }
        if (suggestionsInfos.length != offsets.length || offsets.length != lengths.length) {
            throw new IllegalArgumentException();
        }
        int infoSize = suggestionsInfos.length;
        this.mSuggestionsInfos = (SuggestionsInfo[]) Arrays.copyOf(suggestionsInfos, infoSize);
        this.mOffsets = Arrays.copyOf(offsets, infoSize);
        this.mLengths = Arrays.copyOf(lengths, infoSize);
    }

    public SentenceSuggestionsInfo(Parcel source) {
        int infoSize = source.readInt();
        this.mSuggestionsInfos = new SuggestionsInfo[infoSize];
        source.readTypedArray(this.mSuggestionsInfos, SuggestionsInfo.CREATOR);
        this.mOffsets = new int[this.mSuggestionsInfos.length];
        source.readIntArray(this.mOffsets);
        this.mLengths = new int[this.mSuggestionsInfos.length];
        source.readIntArray(this.mLengths);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        int infoSize = this.mSuggestionsInfos.length;
        dest.writeInt(infoSize);
        dest.writeTypedArray(this.mSuggestionsInfos, 0);
        dest.writeIntArray(this.mOffsets);
        dest.writeIntArray(this.mLengths);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getSuggestionsCount() {
        return this.mSuggestionsInfos.length;
    }

    public SuggestionsInfo getSuggestionsInfoAt(int i) {
        if (i >= 0 && i < this.mSuggestionsInfos.length) {
            return this.mSuggestionsInfos[i];
        }
        return null;
    }

    public int getOffsetAt(int i) {
        if (i >= 0 && i < this.mOffsets.length) {
            return this.mOffsets[i];
        }
        return -1;
    }

    public int getLengthAt(int i) {
        if (i >= 0 && i < this.mLengths.length) {
            return this.mLengths[i];
        }
        return -1;
    }
}
