package android.content.pm.verify.domain;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@SystemApi
/* loaded from: classes.dex */
public final class DomainVerificationInfo implements Parcelable {
    public static final Parcelable.Creator<DomainVerificationInfo> CREATOR;
    public static final int STATE_FIRST_VERIFIER_DEFINED = 1024;
    public static final int STATE_MODIFIABLE_UNVERIFIED = 3;
    public static final int STATE_MODIFIABLE_VERIFIED = 4;
    public static final int STATE_NO_RESPONSE = 0;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_UNMODIFIABLE = 2;
    static Parcelling<UUID> sParcellingForIdentifier;
    private final Map<String, Integer> mHostToStateMap;
    private final UUID mIdentifier;
    private final String mPackageName;

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private void parcelHostToStateMap(Parcel parcel, int i) {
        DomainVerificationUtils.writeHostMap(parcel, this.mHostToStateMap);
    }

    private Map<String, Integer> unparcelHostToStateMap(Parcel parcel) {
        return DomainVerificationUtils.readHostMap(parcel, new ArrayMap(), DomainVerificationUserState.class.getClassLoader());
    }

    public static String stateToString(int i) {
        if (i == 0) {
            return "STATE_NO_RESPONSE";
        }
        if (i == 1) {
            return "STATE_SUCCESS";
        }
        if (i == 2) {
            return "STATE_UNMODIFIABLE";
        }
        if (i == 3) {
            return "STATE_MODIFIABLE_UNVERIFIED";
        }
        if (i == 4) {
            return "STATE_MODIFIABLE_VERIFIED";
        }
        if (i == 1024) {
            return "STATE_FIRST_VERIFIER_DEFINED";
        }
        return Integer.toHexString(i);
    }

    public DomainVerificationInfo(UUID uuid, String str, Map<String, Integer> map) {
        this.mIdentifier = uuid;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) uuid);
        this.mPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mHostToStateMap = map;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) map);
    }

    public UUID getIdentifier() {
        return this.mIdentifier;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public Map<String, Integer> getHostToStateMap() {
        return this.mHostToStateMap;
    }

    public String toString() {
        return "DomainVerificationInfo { identifier = " + this.mIdentifier + ", packageName = " + this.mPackageName + ", hostToStateMap = " + this.mHostToStateMap + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            DomainVerificationInfo domainVerificationInfo = (DomainVerificationInfo) obj;
            if (Objects.equals(this.mIdentifier, domainVerificationInfo.mIdentifier) && Objects.equals(this.mPackageName, domainVerificationInfo.mPackageName) && Objects.equals(this.mHostToStateMap, domainVerificationInfo.mHostToStateMap)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Objects.hashCode(this.mIdentifier) + 31) * 31) + Objects.hashCode(this.mPackageName)) * 31) + Objects.hashCode(this.mHostToStateMap);
    }

    static {
        Parcelling<UUID> parcelling = Parcelling.Cache.get(Parcelling.BuiltIn.ForUUID.class);
        sParcellingForIdentifier = parcelling;
        if (parcelling == null) {
            sParcellingForIdentifier = Parcelling.Cache.put(new Parcelling.BuiltIn.ForUUID());
        }
        CREATOR = new Parcelable.Creator<DomainVerificationInfo>() { // from class: android.content.pm.verify.domain.DomainVerificationInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DomainVerificationInfo[] newArray(int i) {
                return new DomainVerificationInfo[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DomainVerificationInfo createFromParcel(Parcel parcel) {
                return new DomainVerificationInfo(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        sParcellingForIdentifier.parcel(this.mIdentifier, parcel, i);
        parcel.writeString(this.mPackageName);
        parcelHostToStateMap(parcel, i);
    }

    DomainVerificationInfo(Parcel parcel) {
        UUID unparcel = sParcellingForIdentifier.unparcel(parcel);
        String readString = parcel.readString();
        Map<String, Integer> unparcelHostToStateMap = unparcelHostToStateMap(parcel);
        this.mIdentifier = unparcel;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) unparcel);
        this.mPackageName = readString;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString);
        this.mHostToStateMap = unparcelHostToStateMap;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) unparcelHostToStateMap);
    }
}
