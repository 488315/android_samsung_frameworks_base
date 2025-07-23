package android.app.assist;

import android.content.ClipData;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class AssistContent implements Parcelable {
    public static final Parcelable.Creator<AssistContent> CREATOR = new Parcelable.Creator<AssistContent>() { // from class: android.app.assist.AssistContent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssistContent createFromParcel(Parcel parcel) {
            return new AssistContent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssistContent[] newArray(int i) {
            return new AssistContent[i];
        }
    };
    public static final String EXTRA_APP_FUNCTION_DATA = "android.app.assist.extra.APP_FUNCTION_DATA";
    private ClipData mClipData;
    private final Bundle mExtras;
    private Intent mIntent;
    private boolean mIsAppProvidedIntent;
    private boolean mIsAppProvidedWebUri;
    private Uri mSessionTransferUri;
    private String mStructuredData;
    private Uri mUri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AssistContent() {
        this.mIsAppProvidedIntent = false;
        this.mIsAppProvidedWebUri = false;
        this.mExtras = new Bundle();
    }

    public AssistContent(Bundle bundle) {
        this.mIsAppProvidedIntent = false;
        this.mIsAppProvidedWebUri = false;
        this.mExtras = bundle;
    }

    public void setDefaultIntent(Intent intent) {
        Uri data;
        this.mIntent = intent;
        this.mIsAppProvidedIntent = false;
        this.mIsAppProvidedWebUri = false;
        this.mUri = null;
        if (intent == null || !"android.intent.action.VIEW".equals(intent.getAction()) || (data = intent.getData()) == null) {
            return;
        }
        if (IntentFilter.SCHEME_HTTP.equals(data.getScheme()) || IntentFilter.SCHEME_HTTPS.equals(data.getScheme())) {
            this.mUri = data;
        }
    }

    public void setIntent(Intent intent) {
        this.mIsAppProvidedIntent = true;
        this.mIntent = intent;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public boolean isAppProvidedIntent() {
        return this.mIsAppProvidedIntent;
    }

    public void setClipData(ClipData clipData) {
        this.mClipData = clipData;
    }

    public ClipData getClipData() {
        return this.mClipData;
    }

    public void setStructuredData(String str) {
        this.mStructuredData = str;
    }

    public String getStructuredData() {
        return this.mStructuredData;
    }

    public void setWebUri(Uri uri) {
        this.mIsAppProvidedWebUri = true;
        this.mUri = uri;
    }

    public Uri getWebUri() {
        return this.mUri;
    }

    public boolean isAppProvidedWebUri() {
        return this.mIsAppProvidedWebUri;
    }

    public void setSessionTransferUri(Uri uri) {
        this.mSessionTransferUri = uri;
    }

    public Uri getSessionTransferUri() {
        return this.mSessionTransferUri;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    AssistContent(Parcel parcel) {
        this.mIsAppProvidedIntent = false;
        this.mIsAppProvidedWebUri = false;
        if (parcel.readInt() != 0) {
            this.mIntent = Intent.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mClipData = ClipData.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mUri = Uri.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mSessionTransferUri = Uri.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mStructuredData = parcel.readString();
        }
        this.mIsAppProvidedIntent = parcel.readInt() == 1;
        this.mExtras = parcel.readBundle();
        this.mIsAppProvidedWebUri = parcel.readInt() == 1;
    }

    void writeToParcelInternal(Parcel parcel, int i) {
        if (this.mIntent != null) {
            parcel.writeInt(1);
            this.mIntent.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mClipData != null) {
            parcel.writeInt(1);
            this.mClipData.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mUri != null) {
            parcel.writeInt(1);
            this.mUri.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mSessionTransferUri != null) {
            parcel.writeInt(1);
            this.mSessionTransferUri.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mStructuredData != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mStructuredData);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mIsAppProvidedIntent ? 1 : 0);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mIsAppProvidedWebUri ? 1 : 0);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }
}
