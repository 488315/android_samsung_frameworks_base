package android.media;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class Rating implements Parcelable {
    public static final Parcelable.Creator<Rating> CREATOR = new Parcelable.Creator<Rating>() { // from class: android.media.Rating.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Rating createFromParcel(Parcel parcel) {
            return new Rating(parcel.readInt(), parcel.readFloat());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Rating[] newArray(int i) {
            return new Rating[i];
        }
    };
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final String TAG = "Rating";
    private final int mRatingStyle;
    private final float mRatingValue;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StarStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {
    }

    private Rating(int i, float f) {
        this.mRatingStyle = i;
        this.mRatingValue = f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Rating:style=");
        sb.append(this.mRatingStyle);
        sb.append(" rating=");
        float f = this.mRatingValue;
        sb.append(f < 0.0f ? "unrated" : String.valueOf(f));
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mRatingStyle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRatingStyle);
        parcel.writeFloat(this.mRatingValue);
    }

    public static Rating newUnratedRating(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return new Rating(i, -1.0f);
            default:
                return null;
        }
    }

    public static Rating newHeartRating(boolean z) {
        return new Rating(1, z ? 1.0f : 0.0f);
    }

    public static Rating newThumbRating(boolean z) {
        return new Rating(2, z ? 1.0f : 0.0f);
    }

    public static Rating newStarRating(int i, float f) {
        float f2;
        if (i == 3) {
            f2 = 3.0f;
        } else if (i == 4) {
            f2 = 4.0f;
        } else {
            if (i != 5) {
                Log.e(TAG, "Invalid rating style (" + i + ") for a star rating");
                return null;
            }
            f2 = 5.0f;
        }
        if (f < 0.0f || f > f2) {
            Log.e(TAG, "Trying to set out of range star-based rating");
            return null;
        }
        return new Rating(i, f);
    }

    public static Rating newPercentageRating(float f) {
        if (f >= 0.0f && f <= 100.0f) {
            return new Rating(6, f);
        }
        Log.e(TAG, "Invalid percentage-based rating value");
        return null;
    }

    public boolean isRated() {
        return this.mRatingValue >= 0.0f;
    }

    public int getRatingStyle() {
        return this.mRatingStyle;
    }

    public boolean hasHeart() {
        return this.mRatingStyle == 1 && this.mRatingValue == 1.0f;
    }

    public boolean isThumbUp() {
        return this.mRatingStyle == 2 && this.mRatingValue == 1.0f;
    }

    public float getStarRating() {
        int i = this.mRatingStyle;
        if ((i == 3 || i == 4 || i == 5) && isRated()) {
            return this.mRatingValue;
        }
        return -1.0f;
    }

    public float getPercentRating() {
        if (this.mRatingStyle == 6 && isRated()) {
            return this.mRatingValue;
        }
        return -1.0f;
    }
}
