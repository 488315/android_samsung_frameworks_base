package android.media.tv;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes3.dex */
public final class TvContentRatingSystemInfo implements Parcelable {
    public static final Parcelable.Creator<TvContentRatingSystemInfo> CREATOR = new Parcelable.Creator<TvContentRatingSystemInfo>() { // from class: android.media.tv.TvContentRatingSystemInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvContentRatingSystemInfo createFromParcel(Parcel parcel) {
            return new TvContentRatingSystemInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvContentRatingSystemInfo[] newArray(int i) {
            return new TvContentRatingSystemInfo[i];
        }
    };
    private final ApplicationInfo mApplicationInfo;
    private final Uri mXmlUri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final TvContentRatingSystemInfo createTvContentRatingSystemInfo(int i, ApplicationInfo applicationInfo) {
        return new TvContentRatingSystemInfo(new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE).authority(applicationInfo.packageName).appendPath(String.valueOf(i)).build(), applicationInfo);
    }

    private TvContentRatingSystemInfo(Uri uri, ApplicationInfo applicationInfo) {
        this.mXmlUri = uri;
        this.mApplicationInfo = applicationInfo;
    }

    public final boolean isSystemDefined() {
        return (this.mApplicationInfo.flags & 1) != 0;
    }

    public final Uri getXmlUri() {
        return this.mXmlUri;
    }

    private TvContentRatingSystemInfo(Parcel parcel) {
        this.mXmlUri = (Uri) parcel.readParcelable(null, Uri.class);
        this.mApplicationInfo = (ApplicationInfo) parcel.readParcelable(null, ApplicationInfo.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mXmlUri, i);
        parcel.writeParcelable(this.mApplicationInfo, i);
    }
}
