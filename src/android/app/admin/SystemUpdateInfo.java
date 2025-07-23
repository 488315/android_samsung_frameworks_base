package android.app.admin;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class SystemUpdateInfo implements Parcelable {
    private static final String ATTR_ORIGINAL_BUILD = "original-build";
    private static final String ATTR_RECEIVED_TIME = "received-time";
    private static final String ATTR_SECURITY_PATCH_STATE = "security-patch-state";
    public static final Parcelable.Creator<SystemUpdateInfo> CREATOR = new Parcelable.Creator<SystemUpdateInfo>() { // from class: android.app.admin.SystemUpdateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SystemUpdateInfo createFromParcel(Parcel parcel) {
            return new SystemUpdateInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SystemUpdateInfo[] newArray(int i) {
            return new SystemUpdateInfo[i];
        }
    };
    public static final int SECURITY_PATCH_STATE_FALSE = 1;
    public static final int SECURITY_PATCH_STATE_TRUE = 2;
    public static final int SECURITY_PATCH_STATE_UNKNOWN = 0;
    private static final String TAG = "SystemUpdateInfo";
    private final long mReceivedTime;
    private final int mSecurityPatchState;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SecurityPatchState {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SystemUpdateInfo(long j, int i) {
        this.mReceivedTime = j;
        this.mSecurityPatchState = i;
    }

    private SystemUpdateInfo(Parcel parcel) {
        this.mReceivedTime = parcel.readLong();
        this.mSecurityPatchState = parcel.readInt();
    }

    public static SystemUpdateInfo of(long j) {
        if (j == -1) {
            return null;
        }
        return new SystemUpdateInfo(j, 0);
    }

    public static SystemUpdateInfo of(long j, boolean z) {
        if (j == -1) {
            return null;
        }
        return new SystemUpdateInfo(j, z ? 2 : 1);
    }

    public long getReceivedTime() {
        return this.mReceivedTime;
    }

    public int getSecurityPatchState() {
        return this.mSecurityPatchState;
    }

    public void writeToXml(TypedXmlSerializer typedXmlSerializer, String str) throws IOException {
        typedXmlSerializer.startTag(null, str);
        typedXmlSerializer.attributeLong(null, ATTR_RECEIVED_TIME, this.mReceivedTime);
        typedXmlSerializer.attributeInt(null, ATTR_SECURITY_PATCH_STATE, this.mSecurityPatchState);
        typedXmlSerializer.attribute(null, ATTR_ORIGINAL_BUILD, Build.FINGERPRINT);
        typedXmlSerializer.endTag(null, str);
    }

    public static SystemUpdateInfo readFromXml(TypedXmlPullParser typedXmlPullParser) {
        if (!Build.FINGERPRINT.equals(typedXmlPullParser.getAttributeValue(null, ATTR_ORIGINAL_BUILD))) {
            return null;
        }
        try {
            return new SystemUpdateInfo(typedXmlPullParser.getAttributeLong(null, ATTR_RECEIVED_TIME), typedXmlPullParser.getAttributeInt(null, ATTR_SECURITY_PATCH_STATE));
        } catch (XmlPullParserException e) {
            Log.w(TAG, "Load xml failed", e);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(getReceivedTime());
        parcel.writeInt(getSecurityPatchState());
    }

    public String toString() {
        return String.format("SystemUpdateInfo (receivedTime = %d, securityPatchState = %s)", Long.valueOf(this.mReceivedTime), securityPatchStateToString(this.mSecurityPatchState));
    }

    private static String securityPatchStateToString(int i) {
        if (i == 0) {
            return "unknown";
        }
        if (i == 1) {
            return "false";
        }
        if (i == 2) {
            return "true";
        }
        throw new IllegalArgumentException("Unrecognized security patch state: " + i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SystemUpdateInfo systemUpdateInfo = (SystemUpdateInfo) obj;
            if (this.mReceivedTime == systemUpdateInfo.mReceivedTime && this.mSecurityPatchState == systemUpdateInfo.mSecurityPatchState) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mReceivedTime), Integer.valueOf(this.mSecurityPatchState));
    }
}
