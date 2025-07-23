package android.media.tv;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class TunedInfo implements Parcelable {
    public static final int APP_TAG_SELF = 0;
    public static final int APP_TYPE_NON_SYSTEM = 3;
    public static final int APP_TYPE_SELF = 1;
    public static final int APP_TYPE_SYSTEM = 2;
    public static final Parcelable.Creator<TunedInfo> CREATOR = new Parcelable.Creator<TunedInfo>() { // from class: android.media.tv.TunedInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TunedInfo createFromParcel(Parcel parcel) {
            try {
                return new TunedInfo(parcel);
            } catch (Exception e) {
                Log.e(TunedInfo.TAG, "Exception creating TunedInfo from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TunedInfo[] newArray(int i) {
            return new TunedInfo[i];
        }
    };
    static final String TAG = "TunedInfo";
    private final int mAppTag;
    private final int mAppType;
    private final Uri mChannelUri;
    private final String mInputId;
    private final boolean mIsMainSession;
    private final boolean mIsRecordingSession;
    private final boolean mIsVisible;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AppType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TunedInfo(String str, Uri uri, boolean z, boolean z2, boolean z3, int i, int i2) {
        this.mInputId = str;
        this.mChannelUri = uri;
        this.mIsRecordingSession = z;
        this.mIsVisible = z2;
        this.mIsMainSession = z3;
        this.mAppType = i;
        this.mAppTag = i2;
    }

    private TunedInfo(Parcel parcel) {
        this.mInputId = parcel.readString();
        String readString = parcel.readString();
        this.mChannelUri = readString == null ? null : Uri.parse(readString);
        this.mIsRecordingSession = parcel.readInt() == 1;
        this.mIsVisible = parcel.readInt() == 1;
        this.mIsMainSession = parcel.readInt() == 1;
        this.mAppType = parcel.readInt();
        this.mAppTag = parcel.readInt();
    }

    public String getInputId() {
        return this.mInputId;
    }

    public Uri getChannelUri() {
        return this.mChannelUri;
    }

    public boolean isRecordingSession() {
        return this.mIsRecordingSession;
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }

    public boolean isMainSession() {
        return this.mIsMainSession;
    }

    public int getAppTag() {
        return this.mAppTag;
    }

    public int getAppType() {
        return this.mAppType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mInputId);
        Uri uri = this.mChannelUri;
        parcel.writeString(uri == null ? null : uri.toString());
        parcel.writeInt(this.mIsRecordingSession ? 1 : 0);
        parcel.writeInt(this.mIsVisible ? 1 : 0);
        parcel.writeInt(this.mIsMainSession ? 1 : 0);
        parcel.writeInt(this.mAppType);
        parcel.writeInt(this.mAppTag);
    }

    public String toString() {
        return "inputID=" + this.mInputId + ";channelUri=" + this.mChannelUri + ";isRecording=" + this.mIsRecordingSession + ";isVisible=" + this.mIsVisible + ";isMainSession=" + this.mIsMainSession + ";appType=" + this.mAppType + ";appTag=" + this.mAppTag;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TunedInfo)) {
            return false;
        }
        TunedInfo tunedInfo = (TunedInfo) obj;
        return TextUtils.equals(this.mInputId, tunedInfo.getInputId()) && Objects.equals(this.mChannelUri, tunedInfo.mChannelUri) && this.mIsRecordingSession == tunedInfo.mIsRecordingSession && this.mIsVisible == tunedInfo.mIsVisible && this.mIsMainSession == tunedInfo.mIsMainSession && this.mAppType == tunedInfo.mAppType && this.mAppTag == tunedInfo.mAppTag;
    }

    public int hashCode() {
        return Objects.hash(this.mInputId, this.mChannelUri, Boolean.valueOf(this.mIsRecordingSession), Boolean.valueOf(this.mIsVisible), Boolean.valueOf(this.mIsMainSession), Integer.valueOf(this.mAppType), Integer.valueOf(this.mAppTag));
    }
}
