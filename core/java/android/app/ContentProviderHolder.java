package android.app;

import android.content.ContentProviderNative;
import android.content.IContentProvider;
import android.content.p002pm.ProviderInfo;
import android.p009os.IBinder;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes.dex */
public class ContentProviderHolder implements Parcelable {
    public static final Parcelable.Creator<ContentProviderHolder> CREATOR = new Parcelable.Creator<ContentProviderHolder>() { // from class: android.app.ContentProviderHolder.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentProviderHolder createFromParcel(Parcel source) {
            return new ContentProviderHolder(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentProviderHolder[] newArray(int size) {
            return new ContentProviderHolder[size];
        }
    };
    public IBinder connection;
    public final ProviderInfo info;
    public boolean mLocal;
    public boolean noReleaseNeeded;
    public IContentProvider provider;

    public ContentProviderHolder(ProviderInfo _info) {
        this.info = _info;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.info.writeToParcel(parcel, 0);
        IContentProvider iContentProvider = this.provider;
        if (iContentProvider != null) {
            parcel.writeStrongBinder(iContentProvider.asBinder());
        } else {
            parcel.writeStrongBinder(null);
        }
        parcel.writeStrongBinder(this.connection);
        parcel.writeInt(this.noReleaseNeeded ? 1 : 0);
        parcel.writeInt(this.mLocal ? 1 : 0);
    }

    private ContentProviderHolder(Parcel source) {
        this.info = ProviderInfo.CREATOR.createFromParcel(source);
        this.provider = ContentProviderNative.asInterface(source.readStrongBinder());
        this.connection = source.readStrongBinder();
        this.noReleaseNeeded = source.readInt() != 0;
        this.mLocal = source.readInt() != 0;
    }
}
