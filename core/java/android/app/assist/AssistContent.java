package android.app.assist;

import android.content.ClipData;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes.dex */
public class AssistContent implements Parcelable {
  public static final Parcelable.Creator<AssistContent> CREATOR =
      new Parcelable.Creator<AssistContent>() { // from class: android.app.assist.AssistContent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssistContent createFromParcel(Parcel in) {
          return new AssistContent(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssistContent[] newArray(int size) {
          return new AssistContent[size];
        }
      };
  private ClipData mClipData;
  private final Bundle mExtras;
  private Intent mIntent;
  private boolean mIsAppProvidedIntent;
  private boolean mIsAppProvidedWebUri;
  private String mStructuredData;
  private Uri mUri;

  public AssistContent() {
    this.mIsAppProvidedIntent = false;
    this.mIsAppProvidedWebUri = false;
    this.mExtras = new Bundle();
  }

  public void setDefaultIntent(Intent intent) {
    Uri uri;
    this.mIntent = intent;
    this.mIsAppProvidedIntent = false;
    this.mIsAppProvidedWebUri = false;
    this.mUri = null;
    if (intent != null
        && "android.intent.action.VIEW".equals(intent.getAction())
        && (uri = intent.getData()) != null) {
      if (IntentFilter.SCHEME_HTTP.equals(uri.getScheme())
          || IntentFilter.SCHEME_HTTPS.equals(uri.getScheme())) {
        this.mUri = uri;
      }
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

  public void setClipData(ClipData clip) {
    this.mClipData = clip;
  }

  public ClipData getClipData() {
    return this.mClipData;
  }

  public void setStructuredData(String structuredData) {
    this.mStructuredData = structuredData;
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

  public Bundle getExtras() {
    return this.mExtras;
  }

  AssistContent(Parcel in) {
    this.mIsAppProvidedIntent = false;
    this.mIsAppProvidedWebUri = false;
    if (in.readInt() != 0) {
      this.mIntent = Intent.CREATOR.createFromParcel(in);
    }
    if (in.readInt() != 0) {
      this.mClipData = ClipData.CREATOR.createFromParcel(in);
    }
    if (in.readInt() != 0) {
      this.mUri = Uri.CREATOR.createFromParcel(in);
    }
    if (in.readInt() != 0) {
      this.mStructuredData = in.readString();
    }
    this.mIsAppProvidedIntent = in.readInt() == 1;
    this.mExtras = in.readBundle();
    this.mIsAppProvidedWebUri = in.readInt() == 1;
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

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    writeToParcelInternal(dest, flags);
  }
}
