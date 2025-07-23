package android.telecom;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes4.dex */
public final class CallEndpoint implements Parcelable {
    private static final String CALLENDPOINT_NAME_ID_NULL_ERROR = "CallEndpoint name cannot be null.";
    public static final Parcelable.Creator<CallEndpoint> CREATOR = new Parcelable.Creator<CallEndpoint>() { // from class: android.telecom.CallEndpoint.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallEndpoint createFromParcel(Parcel parcel) {
            return new CallEndpoint(parcel.readCharSequence(), parcel.readInt(), ParcelUuid.CREATOR.createFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallEndpoint[] newArray(int i) {
            return new CallEndpoint[i];
        }
    };
    public static final int ENDPOINT_OPERATION_FAILED = 1;
    public static final int ENDPOINT_OPERATION_SUCCESS = 0;
    public static final int TYPE_BLUETOOTH = 2;
    public static final int TYPE_EARPIECE = 1;
    public static final int TYPE_SPEAKER = 4;
    public static final int TYPE_STREAMING = 5;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_WIRED_HEADSET = 3;
    private final ParcelUuid mIdentifier;
    private final CharSequence mName;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EndpointType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CallEndpoint(CharSequence charSequence, int i, ParcelUuid parcelUuid) {
        if (charSequence == null || parcelUuid == null) {
            throw new IllegalArgumentException(CALLENDPOINT_NAME_ID_NULL_ERROR);
        }
        this.mName = charSequence;
        this.mType = i;
        this.mIdentifier = parcelUuid;
    }

    public CallEndpoint(CharSequence charSequence, int i) {
        this(charSequence, i, new ParcelUuid(UUID.randomUUID()));
    }

    public CallEndpoint(CallEndpoint callEndpoint) {
        if (callEndpoint.getEndpointName() == null || callEndpoint.getIdentifier() == null) {
            throw new IllegalArgumentException(CALLENDPOINT_NAME_ID_NULL_ERROR);
        }
        this.mName = callEndpoint.getEndpointName();
        this.mType = callEndpoint.getEndpointType();
        this.mIdentifier = callEndpoint.getIdentifier();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CallEndpoint)) {
            return false;
        }
        CallEndpoint callEndpoint = (CallEndpoint) obj;
        return Objects.equals(getEndpointName(), callEndpoint.getEndpointName()) && getEndpointType() == callEndpoint.getEndpointType() && getIdentifier().equals(callEndpoint.getIdentifier());
    }

    public int hashCode() {
        return Objects.hash(this.mName, Integer.valueOf(this.mType), this.mIdentifier);
    }

    public String toString() {
        return TextUtils.formatSimple("[CallEndpoint Name: %s, Type: %s, Identifier: %s]", this.mName.toString(), endpointTypeToString(this.mType), this.mIdentifier.toString());
    }

    public CharSequence getEndpointName() {
        return this.mName;
    }

    public int getEndpointType() {
        return this.mType;
    }

    public ParcelUuid getIdentifier() {
        return this.mIdentifier;
    }

    public static String endpointTypeToString(int i) {
        if (i == 1) {
            return "EARPIECE";
        }
        if (i == 2) {
            return "BLUETOOTH";
        }
        if (i == 3) {
            return "WIRED_HEADSET";
        }
        if (i == 4) {
            return "SPEAKER";
        }
        if (i == 5) {
            return "EXTERNAL";
        }
        return "UNKNOWN (" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mName);
        parcel.writeInt(this.mType);
        this.mIdentifier.writeToParcel(parcel, i);
    }
}
