package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaMetadataCompat implements Parcelable {
    public static final Parcelable.Creator<MediaMetadataCompat> CREATOR;
    public static final String[] PREFERRED_BITMAP_ORDER;
    public static final String[] PREFERRED_DESCRIPTION_ORDER;
    public static final String[] PREFERRED_URI_ORDER;
    public final Bundle mBundle;
    public MediaDescriptionCompat mDescription;

    static {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("android.media.metadata.TITLE", 1);
        arrayMap.put("android.media.metadata.ARTIST", 1);
        arrayMap.put("android.media.metadata.DURATION", 0);
        arrayMap.put("android.media.metadata.ALBUM", 1);
        arrayMap.put("android.media.metadata.AUTHOR", 1);
        arrayMap.put("android.media.metadata.WRITER", 1);
        arrayMap.put("android.media.metadata.COMPOSER", 1);
        arrayMap.put("android.media.metadata.COMPILATION", 1);
        arrayMap.put("android.media.metadata.DATE", 1);
        arrayMap.put("android.media.metadata.YEAR", 0);
        arrayMap.put("android.media.metadata.GENRE", 1);
        arrayMap.put("android.media.metadata.TRACK_NUMBER", 0);
        arrayMap.put("android.media.metadata.NUM_TRACKS", 0);
        arrayMap.put("android.media.metadata.DISC_NUMBER", 0);
        arrayMap.put("android.media.metadata.ALBUM_ARTIST", 1);
        arrayMap.put("android.media.metadata.ART", 2);
        arrayMap.put("android.media.metadata.ART_URI", 1);
        arrayMap.put("android.media.metadata.ALBUM_ART", 2);
        arrayMap.put("android.media.metadata.ALBUM_ART_URI", 1);
        arrayMap.put("android.media.metadata.USER_RATING", 3);
        arrayMap.put("android.media.metadata.RATING", 3);
        arrayMap.put("android.media.metadata.DISPLAY_TITLE", 1);
        arrayMap.put("android.media.metadata.DISPLAY_SUBTITLE", 1);
        arrayMap.put("android.media.metadata.DISPLAY_DESCRIPTION", 1);
        arrayMap.put("android.media.metadata.DISPLAY_ICON", 2);
        arrayMap.put("android.media.metadata.DISPLAY_ICON_URI", 1);
        arrayMap.put("android.media.metadata.MEDIA_ID", 1);
        arrayMap.put("android.media.metadata.BT_FOLDER_TYPE", 0);
        arrayMap.put("android.media.metadata.MEDIA_URI", 1);
        arrayMap.put("android.media.metadata.ADVERTISEMENT", 0);
        arrayMap.put("android.media.metadata.DOWNLOAD_STATUS", 0);
        PREFERRED_DESCRIPTION_ORDER = new String[]{"android.media.metadata.TITLE", "android.media.metadata.ARTIST", "android.media.metadata.ALBUM", "android.media.metadata.ALBUM_ARTIST", "android.media.metadata.WRITER", "android.media.metadata.AUTHOR", "android.media.metadata.COMPOSER"};
        PREFERRED_BITMAP_ORDER = new String[]{"android.media.metadata.DISPLAY_ICON", "android.media.metadata.ART", "android.media.metadata.ALBUM_ART"};
        PREFERRED_URI_ORDER = new String[]{"android.media.metadata.DISPLAY_ICON_URI", "android.media.metadata.ART_URI", "android.media.metadata.ALBUM_ART_URI"};
        CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.MediaMetadataCompat.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new MediaMetadataCompat(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new MediaMetadataCompat[i];
            }
        };
    }

    public MediaMetadataCompat(Bundle bundle) {
        Bundle bundle2 = new Bundle(bundle);
        this.mBundle = bundle2;
        MediaSessionCompat.ensureClassLoader(bundle2);
    }

    public static MediaMetadataCompat fromMediaMetadata(Object obj) {
        if (obj == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        ((MediaMetadata) obj).writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        MediaMetadataCompat createFromParcel = CREATOR.createFromParcel(obtain);
        obtain.recycle();
        createFromParcel.getClass();
        return createFromParcel;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final MediaDescriptionCompat getDescription() {
        Bitmap bitmap;
        Uri uri;
        MediaDescriptionCompat mediaDescriptionCompat = this.mDescription;
        if (mediaDescriptionCompat != null) {
            return mediaDescriptionCompat;
        }
        CharSequence charSequence = this.mBundle.getCharSequence("android.media.metadata.MEDIA_ID");
        String charSequence2 = charSequence != null ? charSequence.toString() : null;
        CharSequence[] charSequenceArr = new CharSequence[3];
        CharSequence charSequence3 = this.mBundle.getCharSequence("android.media.metadata.DISPLAY_TITLE");
        if (TextUtils.isEmpty(charSequence3)) {
            int i = 0;
            int i2 = 0;
            while (i < 3) {
                String[] strArr = PREFERRED_DESCRIPTION_ORDER;
                if (i2 >= strArr.length) {
                    break;
                }
                int i3 = i2 + 1;
                CharSequence charSequence4 = this.mBundle.getCharSequence(strArr[i2]);
                if (!TextUtils.isEmpty(charSequence4)) {
                    charSequenceArr[i] = charSequence4;
                    i++;
                }
                i2 = i3;
            }
        } else {
            charSequenceArr[0] = charSequence3;
            charSequenceArr[1] = this.mBundle.getCharSequence("android.media.metadata.DISPLAY_SUBTITLE");
            charSequenceArr[2] = this.mBundle.getCharSequence("android.media.metadata.DISPLAY_DESCRIPTION");
        }
        int i4 = 0;
        while (true) {
            String[] strArr2 = PREFERRED_BITMAP_ORDER;
            if (i4 >= strArr2.length) {
                bitmap = null;
                break;
            }
            try {
                bitmap = (Bitmap) this.mBundle.getParcelable(strArr2[i4]);
            } catch (Exception e) {
                Log.w("MediaMetadata", "Failed to retrieve a key as Bitmap.", e);
                bitmap = null;
            }
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
            CharSequence charSequence5 = this.mBundle.getCharSequence(strArr3[i5]);
            String charSequence6 = charSequence5 != null ? charSequence5.toString() : null;
            if (!TextUtils.isEmpty(charSequence6)) {
                uri = Uri.parse(charSequence6);
                break;
            }
            i5++;
        }
        CharSequence charSequence7 = this.mBundle.getCharSequence("android.media.metadata.MEDIA_URI");
        String charSequence8 = charSequence7 != null ? charSequence7.toString() : null;
        Uri parse = TextUtils.isEmpty(charSequence8) ? null : Uri.parse(charSequence8);
        MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder();
        builder.mMediaId = charSequence2;
        builder.mTitle = charSequenceArr[0];
        builder.mSubtitle = charSequenceArr[1];
        builder.mDescription = charSequenceArr[2];
        builder.mIcon = bitmap;
        builder.mIconUri = uri;
        builder.mMediaUri = parse;
        Bundle bundle = new Bundle();
        if (this.mBundle.containsKey("android.media.metadata.BT_FOLDER_TYPE")) {
            bundle.putLong("android.media.extra.BT_FOLDER_TYPE", this.mBundle.getLong("android.media.metadata.BT_FOLDER_TYPE", 0L));
        }
        if (this.mBundle.containsKey("android.media.metadata.DOWNLOAD_STATUS")) {
            bundle.putLong("android.media.extra.DOWNLOAD_STATUS", this.mBundle.getLong("android.media.metadata.DOWNLOAD_STATUS", 0L));
        }
        if (!bundle.isEmpty()) {
            builder.mExtras = bundle;
        }
        MediaDescriptionCompat mediaDescriptionCompat2 = new MediaDescriptionCompat(builder.mMediaId, builder.mTitle, builder.mSubtitle, builder.mDescription, builder.mIcon, builder.mIconUri, builder.mExtras, builder.mMediaUri);
        this.mDescription = mediaDescriptionCompat2;
        return mediaDescriptionCompat2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
    }

    public MediaMetadataCompat(Parcel parcel) {
        this.mBundle = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
    }
}
