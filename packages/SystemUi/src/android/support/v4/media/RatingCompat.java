package android.support.v4.media;

import android.media.Rating;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RatingCompat implements Parcelable {
    public static final Parcelable.Creator<RatingCompat> CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.RatingCompat.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new RatingCompat(parcel.readInt(), parcel.readFloat());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new RatingCompat[i];
        }
    };
    public Object mRatingObj;
    public final int mRatingStyle;
    public final float mRatingValue;

    public RatingCompat(int i, float f) {
        this.mRatingStyle = i;
        this.mRatingValue = f;
    }

    public static RatingCompat fromRating(Object obj) {
        RatingCompat ratingCompat;
        float f;
        RatingCompat ratingCompat2 = null;
        if (obj != null) {
            Rating rating = (Rating) obj;
            int ratingStyle = rating.getRatingStyle();
            if (!rating.isRated()) {
                switch (ratingStyle) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        ratingCompat2 = new RatingCompat(ratingStyle, -1.0f);
                        break;
                }
            } else {
                switch (ratingStyle) {
                    case 1:
                        ratingCompat = new RatingCompat(1, rating.hasHeart() ? 1.0f : 0.0f);
                        ratingCompat2 = ratingCompat;
                        break;
                    case 2:
                        ratingCompat = new RatingCompat(2, rating.isThumbUp() ? 1.0f : 0.0f);
                        ratingCompat2 = ratingCompat;
                        break;
                    case 3:
                    case 4:
                    case 5:
                        float starRating = rating.getStarRating();
                        if (ratingStyle == 3) {
                            f = 3.0f;
                        } else if (ratingStyle == 4) {
                            f = 4.0f;
                        } else if (ratingStyle != 5) {
                            Log.e("Rating", "Invalid rating style (" + ratingStyle + ") for a star rating");
                            break;
                        } else {
                            f = 5.0f;
                        }
                        if (starRating >= 0.0f && starRating <= f) {
                            ratingCompat2 = new RatingCompat(ratingStyle, starRating);
                            break;
                        } else {
                            Log.e("Rating", "Trying to set out of range star-based rating");
                            break;
                        }
                        break;
                    case 6:
                        float percentRating = rating.getPercentRating();
                        if (percentRating >= 0.0f && percentRating <= 100.0f) {
                            ratingCompat2 = new RatingCompat(6, percentRating);
                            break;
                        } else {
                            Log.e("Rating", "Invalid percentage-based rating value");
                            break;
                        }
                    default:
                        return null;
                }
            }
            ratingCompat2.mRatingObj = obj;
        }
        return ratingCompat2;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return this.mRatingStyle;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Rating:style=");
        sb.append(this.mRatingStyle);
        sb.append(" rating=");
        float f = this.mRatingValue;
        sb.append(f < 0.0f ? "unrated" : String.valueOf(f));
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRatingStyle);
        parcel.writeFloat(this.mRatingValue);
    }
}
