package android.content.pm.verify.domain;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.ArrayMap;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes.dex */
public final class DomainVerificationUserState implements Parcelable {
    public static final Parcelable.Creator<DomainVerificationUserState> CREATOR;
    public static final int DOMAIN_STATE_NONE = 0;
    public static final int DOMAIN_STATE_SELECTED = 1;
    public static final int DOMAIN_STATE_VERIFIED = 2;
    static Parcelling<UUID> sParcellingForIdentifier;
    private final Map<String, Integer> mHostToStateMap;
    private final UUID mIdentifier;
    private final boolean mLinkHandlingAllowed;
    private final String mPackageName;
    private final UserHandle mUser;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DomainState {
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

    @SystemApi
    public UUID getIdentifier() {
        return this.mIdentifier;
    }

    public static String domainStateToString(int i) {
        if (i == 0) {
            return "DOMAIN_STATE_NONE";
        }
        if (i == 1) {
            return "DOMAIN_STATE_SELECTED";
        }
        if (i == 2) {
            return "DOMAIN_STATE_VERIFIED";
        }
        return Integer.toHexString(i);
    }

    public DomainVerificationUserState(UUID uuid, String str, UserHandle userHandle, boolean z, Map<String, Integer> map) {
        this.mIdentifier = uuid;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) uuid);
        this.mPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mUser = userHandle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) userHandle);
        this.mLinkHandlingAllowed = z;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) Boolean.valueOf(z));
        this.mHostToStateMap = map;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) map);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public UserHandle getUser() {
        return this.mUser;
    }

    public boolean isLinkHandlingAllowed() {
        return this.mLinkHandlingAllowed;
    }

    public Map<String, Integer> getHostToStateMap() {
        return this.mHostToStateMap;
    }

    public String toString() {
        return "DomainVerificationUserState { identifier = " + this.mIdentifier + ", packageName = " + this.mPackageName + ", user = " + this.mUser + ", linkHandlingAllowed = " + this.mLinkHandlingAllowed + ", hostToStateMap = " + this.mHostToStateMap + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            DomainVerificationUserState domainVerificationUserState = (DomainVerificationUserState) obj;
            if (Objects.equals(this.mIdentifier, domainVerificationUserState.mIdentifier) && Objects.equals(this.mPackageName, domainVerificationUserState.mPackageName) && Objects.equals(this.mUser, domainVerificationUserState.mUser) && this.mLinkHandlingAllowed == domainVerificationUserState.mLinkHandlingAllowed && Objects.equals(this.mHostToStateMap, domainVerificationUserState.mHostToStateMap)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((Objects.hashCode(this.mIdentifier) + 31) * 31) + Objects.hashCode(this.mPackageName)) * 31) + Objects.hashCode(this.mUser)) * 31) + Boolean.hashCode(this.mLinkHandlingAllowed)) * 31) + Objects.hashCode(this.mHostToStateMap);
    }

    static {
        Parcelling<UUID> parcelling = Parcelling.Cache.get(Parcelling.BuiltIn.ForUUID.class);
        sParcellingForIdentifier = parcelling;
        if (parcelling == null) {
            sParcellingForIdentifier = Parcelling.Cache.put(new Parcelling.BuiltIn.ForUUID());
        }
        CREATOR = new Parcelable.Creator<DomainVerificationUserState>() { // from class: android.content.pm.verify.domain.DomainVerificationUserState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DomainVerificationUserState[] newArray(int i) {
                return new DomainVerificationUserState[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DomainVerificationUserState createFromParcel(Parcel parcel) {
                return new DomainVerificationUserState(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mLinkHandlingAllowed ? (byte) 8 : (byte) 0);
        sParcellingForIdentifier.parcel(this.mIdentifier, parcel, i);
        parcel.writeString(this.mPackageName);
        parcel.writeTypedObject(this.mUser, i);
        parcelHostToStateMap(parcel, i);
    }

    DomainVerificationUserState(Parcel parcel) {
        boolean z = (parcel.readByte() & 8) != 0;
        UUID uuidUnparcel = sParcellingForIdentifier.unparcel(parcel);
        String string = parcel.readString();
        UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
        Map<String, Integer> mapUnparcelHostToStateMap = unparcelHostToStateMap(parcel);
        this.mIdentifier = uuidUnparcel;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) uuidUnparcel);
        this.mPackageName = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mUser = userHandle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) userHandle);
        this.mLinkHandlingAllowed = z;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) Boolean.valueOf(z));
        this.mHostToStateMap = mapUnparcelHostToStateMap;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) mapUnparcelHostToStateMap);
    }
}
