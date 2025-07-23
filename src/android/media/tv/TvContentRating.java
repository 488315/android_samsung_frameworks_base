package android.media.tv;

import android.text.TextUtils;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class TvContentRating {
    private static final String DELIMITER = "/";
    public static final TvContentRating UNRATED = new TvContentRating(PerfettoProtoLogImpl.NULL_STRING, PerfettoProtoLogImpl.NULL_STRING, PerfettoProtoLogImpl.NULL_STRING, null);
    private final String mDomain;
    private final int mHashCode;
    private final String mRating;
    private final String mRatingSystem;
    private final String[] mSubRatings;

    public static TvContentRating createRating(String str, String str2, String str3, String... strArr) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("domain cannot be empty");
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("ratingSystem cannot be empty");
        }
        if (TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("rating cannot be empty");
        }
        return new TvContentRating(str, str2, str3, strArr);
    }

    public static TvContentRating unflattenFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("ratingString cannot be empty");
        }
        String[] split = str.split(DELIMITER);
        if (split.length < 3) {
            throw new IllegalArgumentException("Invalid rating string: " + str);
        }
        if (split.length > 3) {
            int length = split.length - 3;
            String[] strArr = new String[length];
            System.arraycopy(split, 3, strArr, 0, length);
            return new TvContentRating(split[0], split[1], split[2], strArr);
        }
        return new TvContentRating(split[0], split[1], split[2], null);
    }

    private TvContentRating(String str, String str2, String str3, String[] strArr) {
        this.mDomain = str;
        this.mRatingSystem = str2;
        this.mRating = str3;
        if (strArr == null || strArr.length == 0) {
            this.mSubRatings = null;
        } else {
            Arrays.sort(strArr);
            this.mSubRatings = strArr;
        }
        this.mHashCode = (Objects.hash(str, str3) * 31) + Arrays.hashCode(this.mSubRatings);
    }

    public String getDomain() {
        return this.mDomain;
    }

    public String getRatingSystem() {
        return this.mRatingSystem;
    }

    public String getMainRating() {
        return this.mRating;
    }

    public List<String> getSubRatings() {
        String[] strArr = this.mSubRatings;
        if (strArr == null) {
            return null;
        }
        return Collections.unmodifiableList(Arrays.asList(strArr));
    }

    public String flattenToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mDomain);
        sb.append(DELIMITER);
        sb.append(this.mRatingSystem);
        sb.append(DELIMITER);
        sb.append(this.mRating);
        String[] strArr = this.mSubRatings;
        if (strArr != null) {
            for (String str : strArr) {
                sb.append(DELIMITER);
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public final boolean contains(TvContentRating tvContentRating) {
        Preconditions.checkNotNull(tvContentRating);
        if (!tvContentRating.getMainRating().equals(this.mRating) || !tvContentRating.getDomain().equals(this.mDomain) || !tvContentRating.getRatingSystem().equals(this.mRatingSystem) || !tvContentRating.getMainRating().equals(this.mRating)) {
            return false;
        }
        List<String> subRatings = getSubRatings();
        List<String> subRatings2 = tvContentRating.getSubRatings();
        if (subRatings == null && subRatings2 == null) {
            return true;
        }
        if (subRatings == null && subRatings2 != null) {
            return false;
        }
        if (subRatings == null || subRatings2 != null) {
            return subRatings.containsAll(subRatings2);
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TvContentRating)) {
            return false;
        }
        TvContentRating tvContentRating = (TvContentRating) obj;
        if (this.mHashCode == tvContentRating.mHashCode && TextUtils.equals(this.mDomain, tvContentRating.mDomain) && TextUtils.equals(this.mRatingSystem, tvContentRating.mRatingSystem) && TextUtils.equals(this.mRating, tvContentRating.mRating)) {
            return Arrays.equals(this.mSubRatings, tvContentRating.mSubRatings);
        }
        return false;
    }

    public int hashCode() {
        return this.mHashCode;
    }
}
