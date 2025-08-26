package android.media;

import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public final class MediaMetadata implements Parcelable {
    public static final Parcelable.Creator<MediaMetadata> CREATOR;
    private static final SparseArray<String> EDITOR_KEY_MAPPING;
    private static final ArrayMap<String, Integer> METADATA_KEYS_TYPE;
    public static final String METADATA_KEY_BT_FOLDER_TYPE = "android.media.metadata.BT_FOLDER_TYPE";
    public static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    public static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
    public static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    public static final String METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION";
    public static final String METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE";
    public static final String METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE";
    public static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    public static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    public static final String METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID";
    public static final String METADATA_KEY_MEDIA_URI = "android.media.metadata.MEDIA_URI";
    public static final String METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS";
    public static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    public static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    public static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    public static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";
    private static final int METADATA_TYPE_BITMAP = 2;
    private static final int METADATA_TYPE_INVALID = -1;
    private static final int METADATA_TYPE_LONG = 0;
    private static final int METADATA_TYPE_RATING = 3;
    private static final int METADATA_TYPE_TEXT = 1;
    private static final String TAG = "MediaMetadata";
    private final int mBitmapDimensionLimit;
    private final Bundle mBundle;
    private MediaDescription mDescription;
    public static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    public static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    public static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    public static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    public static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    public static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    public static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    private static final String[] PREFERRED_DESCRIPTION_ORDER = {METADATA_KEY_TITLE, METADATA_KEY_ARTIST, METADATA_KEY_ALBUM, METADATA_KEY_ALBUM_ARTIST, METADATA_KEY_WRITER, METADATA_KEY_AUTHOR, METADATA_KEY_COMPOSER};
    public static final String METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON";
    public static final String METADATA_KEY_ART = "android.media.metadata.ART";
    public static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    private static final String[] PREFERRED_BITMAP_ORDER = {METADATA_KEY_DISPLAY_ICON, METADATA_KEY_ART, METADATA_KEY_ALBUM_ART};
    public static final String METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI";
    public static final String METADATA_KEY_ART_URI = "android.media.metadata.ART_URI";
    public static final String METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI";
    private static final String[] PREFERRED_URI_ORDER = {METADATA_KEY_DISPLAY_ICON_URI, METADATA_KEY_ART_URI, METADATA_KEY_ALBUM_ART_URI};

    @Retention(RetentionPolicy.SOURCE)
    public @interface BitmapKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LongKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RatingKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextKey {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        ArrayMap<String, Integer> arrayMap = new ArrayMap<>();
        METADATA_KEYS_TYPE = arrayMap;
        arrayMap.put(METADATA_KEY_TITLE, 1);
        arrayMap.put(METADATA_KEY_ARTIST, 1);
        arrayMap.put(METADATA_KEY_DURATION, 0);
        arrayMap.put(METADATA_KEY_ALBUM, 1);
        arrayMap.put(METADATA_KEY_AUTHOR, 1);
        arrayMap.put(METADATA_KEY_WRITER, 1);
        arrayMap.put(METADATA_KEY_COMPOSER, 1);
        arrayMap.put(METADATA_KEY_COMPILATION, 1);
        arrayMap.put(METADATA_KEY_DATE, 1);
        arrayMap.put(METADATA_KEY_YEAR, 0);
        arrayMap.put(METADATA_KEY_GENRE, 1);
        arrayMap.put(METADATA_KEY_TRACK_NUMBER, 0);
        arrayMap.put(METADATA_KEY_NUM_TRACKS, 0);
        arrayMap.put(METADATA_KEY_DISC_NUMBER, 0);
        arrayMap.put(METADATA_KEY_ALBUM_ARTIST, 1);
        arrayMap.put(METADATA_KEY_ART, 2);
        arrayMap.put(METADATA_KEY_ART_URI, 1);
        arrayMap.put(METADATA_KEY_ALBUM_ART, 2);
        arrayMap.put(METADATA_KEY_ALBUM_ART_URI, 1);
        arrayMap.put(METADATA_KEY_USER_RATING, 3);
        arrayMap.put(METADATA_KEY_RATING, 3);
        arrayMap.put(METADATA_KEY_DISPLAY_TITLE, 1);
        arrayMap.put(METADATA_KEY_DISPLAY_SUBTITLE, 1);
        arrayMap.put(METADATA_KEY_DISPLAY_DESCRIPTION, 1);
        arrayMap.put(METADATA_KEY_DISPLAY_ICON, 2);
        arrayMap.put(METADATA_KEY_DISPLAY_ICON_URI, 1);
        arrayMap.put(METADATA_KEY_BT_FOLDER_TYPE, 0);
        arrayMap.put(METADATA_KEY_MEDIA_ID, 1);
        arrayMap.put(METADATA_KEY_MEDIA_URI, 1);
        SparseArray<String> sparseArray = new SparseArray<>();
        EDITOR_KEY_MAPPING = sparseArray;
        sparseArray.put(100, METADATA_KEY_ART);
        sparseArray.put(101, METADATA_KEY_RATING);
        sparseArray.put(268435457, METADATA_KEY_USER_RATING);
        sparseArray.put(1, METADATA_KEY_ALBUM);
        sparseArray.put(13, METADATA_KEY_ALBUM_ARTIST);
        sparseArray.put(2, METADATA_KEY_ARTIST);
        sparseArray.put(3, METADATA_KEY_AUTHOR);
        sparseArray.put(0, METADATA_KEY_TRACK_NUMBER);
        sparseArray.put(4, METADATA_KEY_COMPOSER);
        sparseArray.put(15, METADATA_KEY_COMPILATION);
        sparseArray.put(5, METADATA_KEY_DATE);
        sparseArray.put(14, METADATA_KEY_DISC_NUMBER);
        sparseArray.put(9, METADATA_KEY_DURATION);
        sparseArray.put(6, METADATA_KEY_GENRE);
        sparseArray.put(10, METADATA_KEY_NUM_TRACKS);
        sparseArray.put(7, METADATA_KEY_TITLE);
        sparseArray.put(11, METADATA_KEY_WRITER);
        sparseArray.put(8, METADATA_KEY_YEAR);
        CREATOR = new Parcelable.Creator<MediaMetadata>() { // from class: android.media.MediaMetadata.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MediaMetadata createFromParcel(Parcel parcel) {
                return new MediaMetadata(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MediaMetadata[] newArray(int i) {
                return new MediaMetadata[i];
            }
        };
    }

    private MediaMetadata(Bundle bundle, int i) {
        this.mBundle = new Bundle(bundle);
        this.mBitmapDimensionLimit = i;
    }

    private MediaMetadata(Parcel parcel) {
        this.mBundle = parcel.readBundle();
        this.mBitmapDimensionLimit = Math.max(parcel.readInt(), 1);
        getBitmap(METADATA_KEY_ART);
        getBitmap(METADATA_KEY_ALBUM_ART);
        getBitmap(METADATA_KEY_DISPLAY_ICON);
    }

    public boolean containsKey(String str) {
        return this.mBundle.containsKey(str);
    }

    public CharSequence getText(String str) {
        return this.mBundle.getCharSequence(str);
    }

    public String getString(String str) {
        CharSequence text = getText(str);
        if (text != null) {
            return text.toString();
        }
        return null;
    }

    public long getLong(String str) {
        return this.mBundle.getLong(str, 0L);
    }

    public Rating getRating(String str) {
        try {
            return (Rating) this.mBundle.getParcelable(str, Rating.class);
        } catch (Exception e) {
            Log.w(TAG, "Failed to retrieve a key as Rating.", e);
            return null;
        }
    }

    public Bitmap getBitmap(String str) {
        try {
            return (Bitmap) this.mBundle.getParcelable(str, Bitmap.class);
        } catch (Exception e) {
            Log.w(TAG, "Failed to retrieve a key as Bitmap.", e);
            return null;
        }
    }

    public int getBitmapDimensionLimit() {
        return this.mBitmapDimensionLimit;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
        parcel.writeInt(this.mBitmapDimensionLimit);
    }

    public int size() {
        return this.mBundle.size();
    }

    public Set<String> keySet() {
        return this.mBundle.keySet();
    }

    public MediaDescription getDescription() {
        Bitmap bitmap;
        Uri uri;
        MediaDescription mediaDescription = this.mDescription;
        if (mediaDescription != null) {
            return mediaDescription;
        }
        String string = getString(METADATA_KEY_MEDIA_ID);
        CharSequence[] charSequenceArr = new CharSequence[3];
        CharSequence text = getText(METADATA_KEY_DISPLAY_TITLE);
        if (TextUtils.isEmpty(text)) {
            int i = 0;
            int i2 = 0;
            while (i < 3) {
                String[] strArr = PREFERRED_DESCRIPTION_ORDER;
                if (i2 >= strArr.length) {
                    break;
                }
                int i3 = i2 + 1;
                CharSequence text2 = getText(strArr[i2]);
                if (!TextUtils.isEmpty(text2)) {
                    charSequenceArr[i] = text2;
                    i++;
                }
                i2 = i3;
            }
        } else {
            charSequenceArr[0] = text;
            charSequenceArr[1] = getText(METADATA_KEY_DISPLAY_SUBTITLE);
            charSequenceArr[2] = getText(METADATA_KEY_DISPLAY_DESCRIPTION);
        }
        int i4 = 0;
        while (true) {
            String[] strArr2 = PREFERRED_BITMAP_ORDER;
            if (i4 >= strArr2.length) {
                bitmap = null;
                break;
            }
            bitmap = getBitmap(strArr2[i4]);
            if (bitmap != null) {
                break;
            }
            i4++;
        }
        int i5 = 0;
        while (true) {
            String[] strArr3 = PREFERRED_URI_ORDER;
            if (i5 >= strArr3.length) {
                uri = null;
                break;
            }
            String string2 = getString(strArr3[i5]);
            if (!TextUtils.isEmpty(string2)) {
                uri = Uri.parse(string2);
                break;
            }
            i5++;
        }
        String string3 = getString(METADATA_KEY_MEDIA_URI);
        Uri uri2 = TextUtils.isEmpty(string3) ? null : Uri.parse(string3);
        MediaDescription.Builder builder = new MediaDescription.Builder();
        builder.setMediaId(string);
        builder.setTitle(charSequenceArr[0]);
        builder.setSubtitle(charSequenceArr[1]);
        builder.setDescription(charSequenceArr[2]);
        builder.setIconBitmap(bitmap);
        builder.setIconUri(uri);
        builder.setMediaUri(uri2);
        if (this.mBundle.containsKey(METADATA_KEY_BT_FOLDER_TYPE)) {
            Bundle bundle = new Bundle();
            bundle.putLong(MediaDescription.EXTRA_BT_FOLDER_TYPE, getLong(METADATA_KEY_BT_FOLDER_TYPE));
            builder.setExtras(bundle);
        }
        MediaDescription mediaDescriptionBuild = builder.build();
        this.mDescription = mediaDescriptionBuild;
        return mediaDescriptionBuild;
    }

    public static String getKeyFromMetadataEditorKey(int i) {
        return EDITOR_KEY_MAPPING.get(i, null);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MediaMetadata)) {
            return false;
        }
        MediaMetadata mediaMetadata = (MediaMetadata) obj;
        int i = 0;
        while (true) {
            ArrayMap<String, Integer> arrayMap = METADATA_KEYS_TYPE;
            if (i >= arrayMap.size()) {
                return true;
            }
            String strKeyAt = arrayMap.keyAt(i);
            int iIntValue = arrayMap.valueAt(i).intValue();
            if (iIntValue != 0) {
                if (iIntValue == 1 && !Objects.equals(getString(strKeyAt), mediaMetadata.getString(strKeyAt))) {
                    return false;
                }
            } else if (getLong(strKeyAt) != mediaMetadata.getLong(strKeyAt)) {
                return false;
            }
            i++;
        }
    }

    public int hashCode() {
        int i;
        int iHashCode;
        int i2 = 17;
        int i3 = 0;
        while (true) {
            ArrayMap<String, Integer> arrayMap = METADATA_KEYS_TYPE;
            if (i3 >= arrayMap.size()) {
                return i2;
            }
            String strKeyAt = arrayMap.keyAt(i3);
            int iIntValue = arrayMap.valueAt(i3).intValue();
            if (iIntValue == 0) {
                i = i2 * 31;
                iHashCode = Long.hashCode(getLong(strKeyAt));
            } else if (iIntValue != 1) {
                i3++;
            } else {
                i = i2 * 31;
                iHashCode = Objects.hash(getString(strKeyAt));
            }
            i2 = i + iHashCode;
            i3++;
        }
    }

    public static final class Builder {
        private int mBitmapDimensionLimit;
        private final Bundle mBundle;

        public Builder() {
            this.mBitmapDimensionLimit = Integer.MAX_VALUE;
            this.mBundle = new Bundle();
        }

        public Builder(MediaMetadata mediaMetadata) {
            this.mBitmapDimensionLimit = Integer.MAX_VALUE;
            this.mBundle = new Bundle(mediaMetadata.mBundle);
            this.mBitmapDimensionLimit = mediaMetadata.mBitmapDimensionLimit;
        }

        public Builder putText(String str, CharSequence charSequence) {
            if (MediaMetadata.METADATA_KEYS_TYPE.containsKey(str) && ((Integer) MediaMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 1) {
                throw new IllegalArgumentException("The " + str + " key cannot be used to put a CharSequence");
            }
            this.mBundle.putCharSequence(str, charSequence);
            return this;
        }

        public Builder putString(String str, String str2) {
            if (MediaMetadata.METADATA_KEYS_TYPE.containsKey(str) && ((Integer) MediaMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 1) {
                throw new IllegalArgumentException("The " + str + " key cannot be used to put a String");
            }
            this.mBundle.putCharSequence(str, str2);
            return this;
        }

        public Builder putLong(String str, long j) {
            if (MediaMetadata.METADATA_KEYS_TYPE.containsKey(str) && ((Integer) MediaMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 0) {
                throw new IllegalArgumentException("The " + str + " key cannot be used to put a long");
            }
            this.mBundle.putLong(str, j);
            return this;
        }

        public Builder putRating(String str, Rating rating) {
            if (MediaMetadata.METADATA_KEYS_TYPE.containsKey(str) && ((Integer) MediaMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 3) {
                throw new IllegalArgumentException("The " + str + " key cannot be used to put a Rating");
            }
            this.mBundle.putParcelable(str, rating);
            return this;
        }

        public Builder putBitmap(String str, Bitmap bitmap) {
            if (MediaMetadata.METADATA_KEYS_TYPE.containsKey(str) && ((Integer) MediaMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 2) {
                throw new IllegalArgumentException("The " + str + " key cannot be used to put a Bitmap");
            }
            this.mBundle.putParcelable(str, bitmap);
            return this;
        }

        public Builder setBitmapDimensionLimit(int i) {
            if (i > 0) {
                this.mBitmapDimensionLimit = i;
                return this;
            }
            Log.w(MediaMetadata.TAG, "setBitmapDimensionLimit(): Ignoring non-positive bitmapDimensionLimit: " + i);
            return this;
        }

        public MediaMetadata build() {
            if (this.mBitmapDimensionLimit != Integer.MAX_VALUE) {
                for (String str : this.mBundle.keySet()) {
                    Object obj = this.mBundle.get(str);
                    if (obj instanceof Bitmap) {
                        Bitmap bitmap = (Bitmap) obj;
                        if (bitmap.getHeight() > this.mBitmapDimensionLimit || bitmap.getWidth() > this.mBitmapDimensionLimit) {
                            putBitmap(str, scaleBitmap(bitmap, this.mBitmapDimensionLimit));
                        }
                    }
                }
            }
            return new MediaMetadata(this.mBundle, this.mBitmapDimensionLimit);
        }

        private Bitmap scaleBitmap(Bitmap bitmap, int i) {
            float f = i;
            float fMin = Math.min(f / bitmap.getWidth(), f / bitmap.getHeight());
            return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * fMin), (int) (bitmap.getHeight() * fMin), true);
        }
    }
}
