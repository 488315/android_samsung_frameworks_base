package android.view.textservice;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.ArrayUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class SuggestionsInfo implements Parcelable {
    public static final int RESULT_ATTR_DONT_SHOW_UI_FOR_SUGGESTIONS = 16;
    public static final int RESULT_ATTR_HAS_RECOMMENDED_SUGGESTIONS = 4;
    public static final int RESULT_ATTR_IN_THE_DICTIONARY = 1;
    public static final int RESULT_ATTR_LOOKS_LIKE_GRAMMAR_ERROR = 8;
    public static final int RESULT_ATTR_LOOKS_LIKE_TYPO = 2;
    private int mCookie;
    private int mSequence;
    private final String[] mSuggestions;
    private final int mSuggestionsAttributes;
    private final boolean mSuggestionsAvailable;
    private static final String[] EMPTY = (String[]) ArrayUtils.emptyArray(String.class);
    public static final Parcelable.Creator<SuggestionsInfo> CREATOR = new Parcelable.Creator<SuggestionsInfo>() { // from class: android.view.textservice.SuggestionsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuggestionsInfo createFromParcel(Parcel parcel) {
            return new SuggestionsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuggestionsInfo[] newArray(int i) {
            return new SuggestionsInfo[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultAttrs {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SuggestionsInfo(int i, String[] strArr) {
        this(i, strArr, 0, 0);
    }

    public SuggestionsInfo(int i, String[] strArr, int i2, int i3) {
        if (strArr == null) {
            this.mSuggestions = EMPTY;
            this.mSuggestionsAvailable = false;
        } else {
            this.mSuggestions = strArr;
            this.mSuggestionsAvailable = true;
        }
        this.mSuggestionsAttributes = i;
        this.mCookie = i2;
        this.mSequence = i3;
    }

    public SuggestionsInfo(Parcel parcel) {
        this.mSuggestionsAttributes = parcel.readInt();
        this.mSuggestions = parcel.readStringArray();
        this.mCookie = parcel.readInt();
        this.mSequence = parcel.readInt();
        this.mSuggestionsAvailable = parcel.readInt() == 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSuggestionsAttributes);
        parcel.writeStringArray(this.mSuggestions);
        parcel.writeInt(this.mCookie);
        parcel.writeInt(this.mSequence);
        parcel.writeInt(this.mSuggestionsAvailable ? 1 : 0);
    }

    public void setCookieAndSequence(int i, int i2) {
        this.mCookie = i;
        this.mSequence = i2;
    }

    public int getCookie() {
        return this.mCookie;
    }

    public int getSequence() {
        return this.mSequence;
    }

    public int getSuggestionsAttributes() {
        return this.mSuggestionsAttributes;
    }

    public int getSuggestionsCount() {
        if (this.mSuggestionsAvailable) {
            return this.mSuggestions.length;
        }
        return -1;
    }

    public String getSuggestionAt(int i) {
        return this.mSuggestions[i];
    }
}
