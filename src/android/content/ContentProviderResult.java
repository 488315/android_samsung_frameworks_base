package android.content;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableException;
import java.util.Objects;

/* loaded from: classes.dex */
public class ContentProviderResult implements Parcelable {
    public static final Parcelable.Creator<ContentProviderResult> CREATOR = new Parcelable.Creator<ContentProviderResult>() { // from class: android.content.ContentProviderResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentProviderResult createFromParcel(Parcel parcel) {
            return new ContentProviderResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentProviderResult[] newArray(int i) {
            return new ContentProviderResult[i];
        }
    };
    public final Integer count;
    public final Throwable exception;
    public final Bundle extras;
    public final Uri uri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContentProviderResult(Uri uri) {
        this((Uri) Objects.requireNonNull(uri), null, null, null);
    }

    public ContentProviderResult(int i) {
        this(null, Integer.valueOf(i), null, null);
    }

    public ContentProviderResult(Bundle bundle) {
        this(null, null, (Bundle) Objects.requireNonNull(bundle), null);
    }

    public ContentProviderResult(Throwable th) {
        this(null, null, null, th);
    }

    public ContentProviderResult(Uri uri, Integer num, Bundle bundle, Throwable th) {
        this.uri = uri;
        this.count = num;
        this.extras = bundle;
        this.exception = th;
    }

    public ContentProviderResult(Parcel parcel) {
        if (parcel.readInt() != 0) {
            this.uri = Uri.CREATOR.createFromParcel(parcel);
        } else {
            this.uri = null;
        }
        if (parcel.readInt() != 0) {
            this.count = Integer.valueOf(parcel.readInt());
        } else {
            this.count = null;
        }
        if (parcel.readInt() != 0) {
            this.extras = parcel.readBundle();
        } else {
            this.extras = null;
        }
        if (parcel.readInt() != 0) {
            this.exception = ParcelableException.readFromParcel(parcel);
        } else {
            this.exception = null;
        }
    }

    public ContentProviderResult(ContentProviderResult contentProviderResult, int i) {
        this.uri = ContentProvider.maybeAddUserId(contentProviderResult.uri, i);
        this.count = contentProviderResult.count;
        this.extras = contentProviderResult.extras;
        this.exception = contentProviderResult.exception;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.uri != null) {
            parcel.writeInt(1);
            this.uri.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.count != null) {
            parcel.writeInt(1);
            parcel.writeInt(this.count.intValue());
        } else {
            parcel.writeInt(0);
        }
        if (this.extras != null) {
            parcel.writeInt(1);
            parcel.writeBundle(this.extras);
        } else {
            parcel.writeInt(0);
        }
        if (this.exception != null) {
            parcel.writeInt(1);
            ParcelableException.writeToParcel(parcel, this.exception);
        } else {
            parcel.writeInt(0);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ContentProviderResult(");
        if (this.uri != null) {
            sb.append("uri=");
            sb.append(this.uri);
            sb.append(' ');
        }
        if (this.count != null) {
            sb.append("count=");
            sb.append(this.count);
            sb.append(' ');
        }
        if (this.extras != null) {
            sb.append("extras=");
            sb.append(this.extras);
            sb.append(' ');
        }
        if (this.exception != null) {
            sb.append("exception=");
            sb.append(this.exception);
            sb.append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }
}
