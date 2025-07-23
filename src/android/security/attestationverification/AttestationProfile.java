package android.security.attestationverification;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.attestationverification.AttestationVerificationManager;
import android.service.timezone.TimeZoneProviderService;
import android.util.Log;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class AttestationProfile implements Parcelable {
    public static final Parcelable.Creator<AttestationProfile> CREATOR = new Parcelable.Creator<AttestationProfile>() { // from class: android.security.attestationverification.AttestationProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AttestationProfile[] newArray(int i) {
            return new AttestationProfile[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AttestationProfile createFromParcel(Parcel parcel) {
            return new AttestationProfile(parcel);
        }
    };
    private static final String TAG = "AVF";
    private final int mAttestationProfileId;
    private final String mPackageName;
    private final String mProfileName;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AttestationProfile(int i, String str, String str2) {
        this.mAttestationProfileId = i;
        this.mPackageName = str;
        this.mProfileName = str2;
    }

    public AttestationProfile(int i) {
        this(i, null, null);
        if (i == 1) {
            throw new IllegalArgumentException("App-defined profiles must be specified with the constructor AttestationProfile#constructor(String, String)");
        }
    }

    public AttestationProfile(String str, String str2) {
        this(1, str, str2);
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("Both packageName and profileName must be non-null");
        }
    }

    public String toString() {
        String str;
        int i = this.mAttestationProfileId;
        if (i == 1) {
            return "AttestationProfile(package=" + this.mPackageName + ", name=" + this.mProfileName + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 0) {
            str = "PROFILE_UNKNOWN";
        } else {
            Log.e(TAG, "ERROR: Missing case in AttestationProfile#toString");
            str = TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY;
        }
        return "AttestationProfile(" + str + "/" + this.mAttestationProfileId + NavigationBarInflaterView.KEY_CODE_END;
    }

    public int getAttestationProfileId() {
        return this.mAttestationProfileId;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getProfileName() {
        return this.mProfileName;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AttestationProfile attestationProfile = (AttestationProfile) obj;
            if (this.mAttestationProfileId == attestationProfile.mAttestationProfileId && Objects.equals(this.mPackageName, attestationProfile.mPackageName) && Objects.equals(this.mProfileName, attestationProfile.mProfileName)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((this.mAttestationProfileId + 31) * 31) + Objects.hashCode(this.mPackageName)) * 31) + Objects.hashCode(this.mProfileName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mPackageName != null ? (byte) 2 : (byte) 0;
        if (this.mProfileName != null) {
            b = (byte) (b | 4);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mAttestationProfileId);
        String str = this.mPackageName;
        if (str != null) {
            parcel.writeString(str);
        }
        String str2 = this.mProfileName;
        if (str2 != null) {
            parcel.writeString(str2);
        }
    }

    AttestationProfile(Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        String readString = (readByte & 2) == 0 ? null : parcel.readString();
        String readString2 = (readByte & 4) == 0 ? null : parcel.readString();
        this.mAttestationProfileId = readInt;
        AnnotationValidations.validate((Class<? extends Annotation>) AttestationVerificationManager.AttestationProfileId.class, (Annotation) null, readInt);
        this.mPackageName = readString;
        this.mProfileName = readString2;
    }
}
