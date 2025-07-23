package android.view.inputmethod;

import android.content.ClipDescription;
import android.content.ContentProvider;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.inputmethod.IInputContentUriToken;
import java.security.InvalidParameterException;

/* loaded from: classes4.dex */
public final class InputContentInfo implements Parcelable {
    public static final Parcelable.Creator<InputContentInfo> CREATOR = new Parcelable.Creator<InputContentInfo>() { // from class: android.view.inputmethod.InputContentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputContentInfo createFromParcel(Parcel parcel) {
            return new InputContentInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputContentInfo[] newArray(int i) {
            return new InputContentInfo[i];
        }
    };
    private final Uri mContentUri;
    private final int mContentUriOwnerUserId;
    private final ClipDescription mDescription;
    private final Uri mLinkUri;
    private IInputContentUriToken mUriToken;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InputContentInfo(Uri uri, ClipDescription clipDescription) {
        this(uri, clipDescription, null);
    }

    public InputContentInfo(Uri uri, ClipDescription clipDescription, Uri uri2) {
        validateInternal(uri, clipDescription, uri2, true);
        this.mContentUri = uri;
        this.mContentUriOwnerUserId = ContentProvider.getUserIdFromUri(uri, UserHandle.myUserId());
        this.mDescription = clipDescription;
        this.mLinkUri = uri2;
    }

    public boolean validate() {
        return validateInternal(this.mContentUri, this.mDescription, this.mLinkUri, false);
    }

    private static boolean validateInternal(Uri uri, ClipDescription clipDescription, Uri uri2, boolean z) {
        if (uri == null) {
            if (z) {
                throw new NullPointerException("contentUri");
            }
            return false;
        }
        if (clipDescription == null) {
            if (z) {
                throw new NullPointerException("description");
            }
            return false;
        }
        if (!"content".equals(uri.getScheme())) {
            if (z) {
                throw new InvalidParameterException("contentUri must have content scheme");
            }
            return false;
        }
        if (uri2 == null) {
            return true;
        }
        String scheme = uri2.getScheme();
        if (scheme != null && (scheme.equalsIgnoreCase(IntentFilter.SCHEME_HTTP) || scheme.equalsIgnoreCase(IntentFilter.SCHEME_HTTPS))) {
            return true;
        }
        if (z) {
            throw new InvalidParameterException("linkUri must have either http or https scheme");
        }
        return false;
    }

    public Uri getContentUri() {
        if (this.mContentUriOwnerUserId != UserHandle.myUserId()) {
            return ContentProvider.maybeAddUserId(this.mContentUri, this.mContentUriOwnerUserId);
        }
        return this.mContentUri;
    }

    public ClipDescription getDescription() {
        return this.mDescription;
    }

    public Uri getLinkUri() {
        return this.mLinkUri;
    }

    public void setUriToken(IInputContentUriToken iInputContentUriToken) {
        if (this.mUriToken != null) {
            throw new IllegalStateException("URI token is already set");
        }
        this.mUriToken = iInputContentUriToken;
    }

    public void requestPermission() {
        IInputContentUriToken iInputContentUriToken = this.mUriToken;
        if (iInputContentUriToken == null) {
            return;
        }
        try {
            iInputContentUriToken.take();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void releasePermission() {
        IInputContentUriToken iInputContentUriToken = this.mUriToken;
        if (iInputContentUriToken == null) {
            return;
        }
        try {
            iInputContentUriToken.release();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Uri.writeToParcel(parcel, this.mContentUri);
        parcel.writeInt(this.mContentUriOwnerUserId);
        this.mDescription.writeToParcel(parcel, i);
        Uri.writeToParcel(parcel, this.mLinkUri);
        if (this.mUriToken != null) {
            parcel.writeInt(1);
            parcel.writeStrongBinder(this.mUriToken.asBinder());
        } else {
            parcel.writeInt(0);
        }
    }

    private InputContentInfo(Parcel parcel) {
        this.mContentUri = Uri.CREATOR.createFromParcel(parcel);
        this.mContentUriOwnerUserId = parcel.readInt();
        this.mDescription = ClipDescription.CREATOR.createFromParcel(parcel);
        this.mLinkUri = Uri.CREATOR.createFromParcel(parcel);
        if (parcel.readInt() == 1) {
            this.mUriToken = IInputContentUriToken.Stub.asInterface(parcel.readStrongBinder());
        } else {
            this.mUriToken = null;
        }
    }
}
