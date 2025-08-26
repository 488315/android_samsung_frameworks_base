package android.telecom;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telecom.IVideoProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class ParcelableConference implements Parcelable {
    public static final Parcelable.Creator<ParcelableConference> CREATOR = new Parcelable.Creator<ParcelableConference>() { // from class: android.telecom.ParcelableConference.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableConference createFromParcel(Parcel parcel) throws ClassNotFoundException, IOException {
            ClassLoader classLoader = ParcelableConference.class.getClassLoader();
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readParcelable(classLoader, PhoneAccountHandle.class);
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            ArrayList arrayList = new ArrayList(2);
            parcel.readList(arrayList, classLoader, String.class);
            long j = parcel.readLong();
            return new ParcelableConference(phoneAccountHandle, i, i2, parcel.readInt(), arrayList, IVideoProvider.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), j, parcel.readLong(), (StatusHints) parcel.readParcelable(classLoader, StatusHints.class), parcel.readBundle(classLoader), (Uri) parcel.readParcelable(classLoader, Uri.class), parcel.readInt(), parcel.readString(), parcel.readInt(), (DisconnectCause) parcel.readParcelable(classLoader, DisconnectCause.class), parcel.readInt() == 1, parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableConference[] newArray(int i) {
            return new ParcelableConference[i];
        }
    };
    private final Uri mAddress;
    private final int mAddressPresentation;
    private final int mCallDirection;
    private final String mCallerDisplayName;
    private final int mCallerDisplayNamePresentation;
    private final long mConnectElapsedTimeMillis;
    private final long mConnectTimeMillis;
    private final int mConnectionCapabilities;
    private final List<String> mConnectionIds;
    private final int mConnectionProperties;
    private final DisconnectCause mDisconnectCause;
    private final Bundle mExtras;
    private final PhoneAccountHandle mPhoneAccount;
    private final boolean mRingbackRequested;
    private final int mState;
    private final StatusHints mStatusHints;
    private final IVideoProvider mVideoProvider;
    private final int mVideoState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private Uri mAddress;
        private String mCallerDisplayName;
        private int mConnectionCapabilities;
        private int mConnectionProperties;
        private DisconnectCause mDisconnectCause;
        private Bundle mExtras;
        private final PhoneAccountHandle mPhoneAccount;
        private boolean mRingbackRequested;
        private final int mState;
        private StatusHints mStatusHints;
        private IVideoProvider mVideoProvider;
        private List<String> mConnectionIds = Collections.EMPTY_LIST;
        private long mConnectTimeMillis = 0;
        private int mVideoState = 0;
        private long mConnectElapsedTimeMillis = 0;
        private int mAddressPresentation = 3;
        private int mCallerDisplayNamePresentation = 3;
        private int mCallDirection = -1;

        public Builder(PhoneAccountHandle phoneAccountHandle, int i) {
            this.mPhoneAccount = phoneAccountHandle;
            this.mState = i;
        }

        public Builder setDisconnectCause(DisconnectCause disconnectCause) {
            this.mDisconnectCause = disconnectCause;
            return this;
        }

        public Builder setRingbackRequested(boolean z) {
            this.mRingbackRequested = z;
            return this;
        }

        public Builder setCallerDisplayName(String str, int i) {
            this.mCallerDisplayName = str;
            this.mCallerDisplayNamePresentation = i;
            return this;
        }

        public Builder setAddress(Uri uri, int i) {
            this.mAddress = uri;
            this.mAddressPresentation = i;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setStatusHints(StatusHints statusHints) {
            this.mStatusHints = statusHints;
            return this;
        }

        public Builder setConnectTimeMillis(long j, long j2) {
            this.mConnectTimeMillis = j;
            this.mConnectElapsedTimeMillis = j2;
            return this;
        }

        public Builder setVideoAttributes(IVideoProvider iVideoProvider, int i) {
            this.mVideoProvider = iVideoProvider;
            this.mVideoState = i;
            return this;
        }

        public Builder setConnectionIds(List<String> list) {
            this.mConnectionIds = list;
            return this;
        }

        public Builder setConnectionProperties(int i) {
            this.mConnectionProperties = i;
            return this;
        }

        public Builder setConnectionCapabilities(int i) {
            this.mConnectionCapabilities = i;
            return this;
        }

        public Builder setCallDirection(int i) {
            this.mCallDirection = i;
            return this;
        }

        public ParcelableConference build() {
            return new ParcelableConference(this.mPhoneAccount, this.mState, this.mConnectionCapabilities, this.mConnectionProperties, this.mConnectionIds, this.mVideoProvider, this.mVideoState, this.mConnectTimeMillis, this.mConnectElapsedTimeMillis, this.mStatusHints, this.mExtras, this.mAddress, this.mAddressPresentation, this.mCallerDisplayName, this.mCallerDisplayNamePresentation, this.mDisconnectCause, this.mRingbackRequested, this.mCallDirection);
        }
    }

    private ParcelableConference(PhoneAccountHandle phoneAccountHandle, int i, int i2, int i3, List<String> list, IVideoProvider iVideoProvider, int i4, long j, long j2, StatusHints statusHints, Bundle bundle, Uri uri, int i5, String str, int i6, DisconnectCause disconnectCause, boolean z, int i7) {
        this.mPhoneAccount = phoneAccountHandle;
        this.mState = i;
        this.mConnectionCapabilities = i2;
        this.mConnectionProperties = i3;
        this.mConnectionIds = list;
        this.mVideoProvider = iVideoProvider;
        this.mVideoState = i4;
        this.mConnectTimeMillis = j;
        this.mStatusHints = statusHints;
        this.mExtras = bundle;
        this.mConnectElapsedTimeMillis = j2;
        this.mAddress = uri;
        this.mAddressPresentation = i5;
        this.mCallerDisplayName = str;
        this.mCallerDisplayNamePresentation = i6;
        this.mDisconnectCause = disconnectCause;
        this.mRingbackRequested = z;
        this.mCallDirection = i7;
    }

    public String toString() {
        return new StringBuffer("account: ").append(this.mPhoneAccount).append(", state: ").append(Connection.stateToString(this.mState)).append(", capabilities: ").append(Connection.capabilitiesToString(this.mConnectionCapabilities)).append(", properties: ").append(Connection.propertiesToString(this.mConnectionProperties)).append(", connectTime: ").append(this.mConnectTimeMillis).append(", children: ").append(this.mConnectionIds).append(", VideoState: ").append(this.mVideoState).append(", VideoProvider: ").append(this.mVideoProvider).append(", isRingbackRequested: ").append(this.mRingbackRequested).append(", disconnectCause: ").append(this.mDisconnectCause).append(", callDirection: ").append(this.mCallDirection).toString();
    }

    public PhoneAccountHandle getPhoneAccount() {
        return this.mPhoneAccount;
    }

    public int getState() {
        return this.mState;
    }

    public int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public List<String> getConnectionIds() {
        return this.mConnectionIds;
    }

    public long getConnectTimeMillis() {
        return this.mConnectTimeMillis;
    }

    public long getConnectElapsedTimeMillis() {
        return this.mConnectElapsedTimeMillis;
    }

    public IVideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Uri getHandle() {
        return this.mAddress;
    }

    public final DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public int getHandlePresentation() {
        return this.mAddressPresentation;
    }

    public int getCallDirection() {
        return this.mCallDirection;
    }

    public String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mPhoneAccount, 0);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mConnectionCapabilities);
        parcel.writeList(this.mConnectionIds);
        parcel.writeLong(this.mConnectTimeMillis);
        IVideoProvider iVideoProvider = this.mVideoProvider;
        parcel.writeStrongBinder(iVideoProvider != null ? iVideoProvider.asBinder() : null);
        parcel.writeInt(this.mVideoState);
        parcel.writeParcelable(this.mStatusHints, 0);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mConnectionProperties);
        parcel.writeLong(this.mConnectElapsedTimeMillis);
        parcel.writeParcelable(this.mAddress, 0);
        parcel.writeInt(this.mAddressPresentation);
        parcel.writeString(this.mCallerDisplayName);
        parcel.writeInt(this.mCallerDisplayNamePresentation);
        parcel.writeParcelable(this.mDisconnectCause, 0);
        parcel.writeInt(this.mRingbackRequested ? 1 : 0);
        parcel.writeInt(this.mCallDirection);
    }
}
