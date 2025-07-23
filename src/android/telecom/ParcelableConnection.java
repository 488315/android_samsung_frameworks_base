package android.telecom;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telecom.IVideoProvider;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class ParcelableConnection implements Parcelable {
    public static final Parcelable.Creator<ParcelableConnection> CREATOR = new Parcelable.Creator<ParcelableConnection>() { // from class: android.telecom.ParcelableConnection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableConnection createFromParcel(Parcel parcel) {
            ClassLoader classLoader = ParcelableConnection.class.getClassLoader();
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readParcelable(classLoader, PhoneAccountHandle.class);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            Uri uri = (Uri) parcel.readParcelable(classLoader, Uri.class);
            int readInt3 = parcel.readInt();
            String readString = parcel.readString();
            int readInt4 = parcel.readInt();
            IVideoProvider asInterface = IVideoProvider.Stub.asInterface(parcel.readStrongBinder());
            int readInt5 = parcel.readInt();
            boolean z = parcel.readByte() == 1;
            boolean z2 = parcel.readByte() == 1;
            long readLong = parcel.readLong();
            StatusHints statusHints = (StatusHints) parcel.readParcelable(classLoader, StatusHints.class);
            DisconnectCause disconnectCause = (DisconnectCause) parcel.readParcelable(classLoader, DisconnectCause.class);
            ArrayList arrayList = new ArrayList();
            parcel.readStringList(arrayList);
            return new ParcelableConnection(phoneAccountHandle, readInt, readInt2, parcel.readInt(), parcel.readInt(), uri, readInt3, readString, readInt4, asInterface, readInt5, z, z2, readLong, parcel.readLong(), statusHints, disconnectCause, arrayList, Bundle.setDefusable(parcel.readBundle(classLoader), true), parcel.readString(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableConnection[] newArray(int i) {
            return new ParcelableConnection[i];
        }
    };
    private final Uri mAddress;
    private final int mAddressPresentation;
    private int mCallDirection;
    private final String mCallerDisplayName;
    private final int mCallerDisplayNamePresentation;
    private int mCallerNumberVerificationStatus;
    private final List<String> mConferenceableConnectionIds;
    private final long mConnectElapsedTimeMillis;
    private final long mConnectTimeMillis;
    private final int mConnectionCapabilities;
    private final int mConnectionProperties;
    private final DisconnectCause mDisconnectCause;
    private final Bundle mExtras;
    private final boolean mIsVoipAudioMode;
    private String mParentCallId;
    private final PhoneAccountHandle mPhoneAccount;
    private final boolean mRingbackRequested;
    private final int mState;
    private final StatusHints mStatusHints;
    private final int mSupportedAudioRoutes;
    private final IVideoProvider mVideoProvider;
    private final int mVideoState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParcelableConnection(PhoneAccountHandle phoneAccountHandle, int i, int i2, int i3, int i4, Uri uri, int i5, String str, int i6, IVideoProvider iVideoProvider, int i7, boolean z, boolean z2, long j, long j2, StatusHints statusHints, DisconnectCause disconnectCause, List<String> list, Bundle bundle, String str2, int i8, int i9) {
        this(phoneAccountHandle, i, i2, i3, i4, uri, i5, str, i6, iVideoProvider, i7, z, z2, j, j2, statusHints, disconnectCause, list, bundle, i9);
        this.mParentCallId = str2;
        this.mCallDirection = i8;
    }

    public ParcelableConnection(PhoneAccountHandle phoneAccountHandle, int i, int i2, int i3, int i4, Uri uri, int i5, String str, int i6, IVideoProvider iVideoProvider, int i7, boolean z, boolean z2, long j, long j2, StatusHints statusHints, DisconnectCause disconnectCause, List<String> list, Bundle bundle, int i8) {
        this.mPhoneAccount = phoneAccountHandle;
        this.mState = i;
        this.mConnectionCapabilities = i2;
        this.mConnectionProperties = i3;
        this.mSupportedAudioRoutes = i4;
        this.mAddress = uri;
        this.mAddressPresentation = i5;
        this.mCallerDisplayName = str;
        this.mCallerDisplayNamePresentation = i6;
        this.mVideoProvider = iVideoProvider;
        this.mVideoState = i7;
        this.mRingbackRequested = z;
        this.mIsVoipAudioMode = z2;
        this.mConnectTimeMillis = j;
        this.mConnectElapsedTimeMillis = j2;
        this.mStatusHints = statusHints;
        this.mDisconnectCause = disconnectCause;
        this.mConferenceableConnectionIds = list;
        this.mExtras = bundle;
        this.mParentCallId = null;
        this.mCallDirection = -1;
        this.mCallerNumberVerificationStatus = i8;
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

    public int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public Uri getHandle() {
        return this.mAddress;
    }

    public int getHandlePresentation() {
        return this.mAddressPresentation;
    }

    public String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public IVideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public boolean getIsVoipAudioMode() {
        return this.mIsVoipAudioMode;
    }

    public long getConnectTimeMillis() {
        return this.mConnectTimeMillis;
    }

    public long getConnectElapsedTimeMillis() {
        return this.mConnectElapsedTimeMillis;
    }

    public final StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public final DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public final List<String> getConferenceableConnectionIds() {
        return this.mConferenceableConnectionIds;
    }

    public final Bundle getExtras() {
        return this.mExtras;
    }

    public final String getParentCallId() {
        return this.mParentCallId;
    }

    public int getCallDirection() {
        return this.mCallDirection;
    }

    public int getCallerNumberVerificationStatus() {
        return this.mCallerNumberVerificationStatus;
    }

    public String toString() {
        return "ParcelableConnection [act:" + this.mPhoneAccount + "], state:" + this.mState + ", capabilities:" + Connection.capabilitiesToString(this.mConnectionCapabilities) + ", properties:" + Connection.propertiesToString(this.mConnectionProperties) + ", extras:" + Log.maskPii(this.mExtras) + ", parent:" + this.mParentCallId + ", callDirection:" + this.mCallDirection;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mPhoneAccount, 0);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mConnectionCapabilities);
        parcel.writeParcelable(this.mAddress, 0);
        parcel.writeInt(this.mAddressPresentation);
        parcel.writeString(this.mCallerDisplayName);
        parcel.writeInt(this.mCallerDisplayNamePresentation);
        IVideoProvider iVideoProvider = this.mVideoProvider;
        parcel.writeStrongBinder(iVideoProvider != null ? iVideoProvider.asBinder() : null);
        parcel.writeInt(this.mVideoState);
        parcel.writeByte(this.mRingbackRequested ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mIsVoipAudioMode ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.mConnectTimeMillis);
        parcel.writeParcelable(this.mStatusHints, 0);
        parcel.writeParcelable(this.mDisconnectCause, 0);
        parcel.writeStringList(this.mConferenceableConnectionIds);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mConnectionProperties);
        parcel.writeInt(this.mSupportedAudioRoutes);
        parcel.writeString(this.mParentCallId);
        parcel.writeLong(this.mConnectElapsedTimeMillis);
        parcel.writeInt(this.mCallDirection);
        parcel.writeInt(this.mCallerNumberVerificationStatus);
    }
}
