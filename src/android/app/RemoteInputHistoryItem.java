package android.app;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class RemoteInputHistoryItem implements Parcelable {
    public static final Parcelable.Creator<RemoteInputHistoryItem> CREATOR = new Parcelable.Creator<RemoteInputHistoryItem>() { // from class: android.app.RemoteInputHistoryItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteInputHistoryItem createFromParcel(Parcel parcel) {
            return new RemoteInputHistoryItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteInputHistoryItem[] newArray(int i) {
            return new RemoteInputHistoryItem[i];
        }
    };
    private String mMimeType;
    private CharSequence mText;
    private Uri mUri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RemoteInputHistoryItem(String str, Uri uri, CharSequence charSequence) {
        this.mMimeType = str;
        this.mUri = uri;
        this.mText = Notification.safeCharSequence(charSequence);
    }

    public RemoteInputHistoryItem(CharSequence charSequence) {
        this.mText = Notification.safeCharSequence(charSequence);
    }

    protected RemoteInputHistoryItem(Parcel parcel) {
        this.mText = parcel.readCharSequence();
        this.mMimeType = parcel.readStringNoHelper();
        this.mUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader(), Uri.class);
    }

    public CharSequence getText() {
        return this.mText;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public Uri getUri() {
        return this.mUri;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mText);
        parcel.writeStringNoHelper(this.mMimeType);
        parcel.writeParcelable(this.mUri, i);
    }
}
