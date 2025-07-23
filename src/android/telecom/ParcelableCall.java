package android.telecom;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.telecom.IVideoProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class ParcelableCall implements Parcelable {
    public static final Parcelable.Creator<ParcelableCall> CREATOR = new Parcelable.Creator<ParcelableCall>() { // from class: android.telecom.ParcelableCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableCall createFromParcel(Parcel parcel) {
            ClassLoader classLoader = ParcelableCall.class.getClassLoader();
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            DisconnectCause disconnectCause = (DisconnectCause) parcel.readParcelable(classLoader, DisconnectCause.class);
            ArrayList arrayList = new ArrayList();
            parcel.readList(arrayList, classLoader, String.class);
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            long readLong = parcel.readLong();
            Uri createFromParcel = Uri.CREATOR.createFromParcel(parcel);
            int readInt4 = parcel.readInt();
            String readString2 = parcel.readString();
            int readInt5 = parcel.readInt();
            GatewayInfo gatewayInfo = (GatewayInfo) parcel.readParcelable(classLoader, GatewayInfo.class);
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readParcelable(classLoader, PhoneAccountHandle.class);
            boolean z = parcel.readByte() == 1;
            IVideoProvider asInterface = IVideoProvider.Stub.asInterface(parcel.readStrongBinder());
            String readString3 = parcel.readString();
            ArrayList arrayList2 = new ArrayList();
            boolean z2 = z;
            parcel.readList(arrayList2, classLoader, String.class);
            StatusHints statusHints = (StatusHints) parcel.readParcelable(classLoader, StatusHints.class);
            int readInt6 = parcel.readInt();
            ArrayList arrayList3 = new ArrayList();
            parcel.readList(arrayList3, classLoader, String.class);
            Bundle readBundle = parcel.readBundle(classLoader);
            Bundle readBundle2 = parcel.readBundle(classLoader);
            int readInt7 = parcel.readInt();
            boolean z3 = parcel.readByte() == 1;
            ParcelableRttCall parcelableRttCall = (ParcelableRttCall) parcel.readParcelable(classLoader, ParcelableRttCall.class);
            boolean z4 = z3;
            long readLong2 = parcel.readLong();
            int readInt8 = parcel.readInt();
            int readInt9 = parcel.readInt();
            String readString4 = parcel.readString();
            String readString5 = parcel.readString();
            return new ParcelableCallBuilder().setId(readString).setState(readInt).setDisconnectCause(disconnectCause).setCannedSmsResponses(arrayList).setCapabilities(readInt2).setProperties(readInt3).setSupportedAudioRoutes(readInt7).setConnectTimeMillis(readLong).setHandle(createFromParcel).setHandlePresentation(readInt4).setCallerDisplayName(readString2).setCallerDisplayNamePresentation(readInt5).setGatewayInfo(gatewayInfo).setAccountHandle(phoneAccountHandle).setIsVideoCallProviderChanged(z2).setVideoCallProvider(asInterface).setIsRttCallChanged(z4).setRttCall(parcelableRttCall).setParentCallId(readString3).setChildCallIds(arrayList2).setStatusHints(statusHints).setVideoState(readInt6).setConferenceableCallIds(arrayList3).setIntentExtras(readBundle).setExtras(readBundle2).setCreationTimeMillis(readLong2).setCallDirection(readInt8).setCallerNumberVerificationStatus(readInt9).setContactDisplayName(readString4).setActiveChildCallId(readString5).setContactPhotoUri((Uri) parcel.readParcelable(classLoader, Uri.class)).setAssociatedUser((UserHandle) parcel.readParcelable(classLoader, UserHandle.class)).createParcelableCall();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableCall[] newArray(int i) {
            return new ParcelableCall[i];
        }
    };
    private final PhoneAccountHandle mAccountHandle;
    private final String mActiveChildCallId;
    private final UserHandle mAssociatedUser;
    private final int mCallDirection;
    private final String mCallerDisplayName;
    private final int mCallerDisplayNamePresentation;
    private final int mCallerNumberVerificationStatus;
    private final List<String> mCannedSmsResponses;
    private final int mCapabilities;
    private final List<String> mChildCallIds;
    private final List<String> mConferenceableCallIds;
    private final long mConnectTimeMillis;
    private final String mContactDisplayName;
    private final Uri mContactPhotoUri;
    private final long mCreationTimeMillis;
    private final DisconnectCause mDisconnectCause;
    private final Bundle mExtras;
    private final GatewayInfo mGatewayInfo;
    private final Uri mHandle;
    private final int mHandlePresentation;
    private final String mId;
    private final Bundle mIntentExtras;
    private final boolean mIsRttCallChanged;
    private final boolean mIsVideoCallProviderChanged;
    private final String mParentCallId;
    private final int mProperties;
    private final ParcelableRttCall mRttCall;
    private final int mState;
    private final StatusHints mStatusHints;
    private final int mSupportedAudioRoutes;
    private VideoCallImpl mVideoCall;
    private final IVideoProvider mVideoCallProvider;
    private final int mVideoState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class ParcelableCallBuilder {
        private PhoneAccountHandle mAccountHandle;
        private String mActiveChildCallId;
        private UserHandle mAssociatedUser;
        private int mCallDirection;
        private String mCallerDisplayName;
        private int mCallerDisplayNamePresentation;
        private int mCallerNumberVerificationStatus;
        private List<String> mCannedSmsResponses;
        private int mCapabilities;
        private List<String> mChildCallIds;
        private List<String> mConferenceableCallIds;
        private long mConnectTimeMillis;
        private String mContactDisplayName;
        private Uri mContactPhotoUri;
        private long mCreationTimeMillis;
        private DisconnectCause mDisconnectCause;
        private Bundle mExtras;
        private GatewayInfo mGatewayInfo;
        private Uri mHandle;
        private int mHandlePresentation;
        private String mId;
        private Bundle mIntentExtras;
        private boolean mIsRttCallChanged;
        private boolean mIsVideoCallProviderChanged;
        private String mParentCallId;
        private int mProperties;
        private ParcelableRttCall mRttCall;
        private int mState;
        private StatusHints mStatusHints;
        private int mSupportedAudioRoutes;
        private IVideoProvider mVideoCallProvider;
        private int mVideoState;

        public ParcelableCallBuilder setId(String str) {
            this.mId = str;
            return this;
        }

        public ParcelableCallBuilder setState(int i) {
            this.mState = i;
            return this;
        }

        public ParcelableCallBuilder setDisconnectCause(DisconnectCause disconnectCause) {
            this.mDisconnectCause = disconnectCause;
            return this;
        }

        public ParcelableCallBuilder setCannedSmsResponses(List<String> list) {
            this.mCannedSmsResponses = list;
            return this;
        }

        public ParcelableCallBuilder setCapabilities(int i) {
            this.mCapabilities = i;
            return this;
        }

        public ParcelableCallBuilder setProperties(int i) {
            this.mProperties = i;
            return this;
        }

        public ParcelableCallBuilder setSupportedAudioRoutes(int i) {
            this.mSupportedAudioRoutes = i;
            return this;
        }

        public ParcelableCallBuilder setConnectTimeMillis(long j) {
            this.mConnectTimeMillis = j;
            return this;
        }

        public ParcelableCallBuilder setHandle(Uri uri) {
            this.mHandle = uri;
            return this;
        }

        public ParcelableCallBuilder setHandlePresentation(int i) {
            this.mHandlePresentation = i;
            return this;
        }

        public ParcelableCallBuilder setCallerDisplayName(String str) {
            this.mCallerDisplayName = str;
            return this;
        }

        public ParcelableCallBuilder setCallerDisplayNamePresentation(int i) {
            this.mCallerDisplayNamePresentation = i;
            return this;
        }

        public ParcelableCallBuilder setGatewayInfo(GatewayInfo gatewayInfo) {
            this.mGatewayInfo = gatewayInfo;
            return this;
        }

        public ParcelableCallBuilder setAccountHandle(PhoneAccountHandle phoneAccountHandle) {
            this.mAccountHandle = phoneAccountHandle;
            return this;
        }

        public ParcelableCallBuilder setIsVideoCallProviderChanged(boolean z) {
            this.mIsVideoCallProviderChanged = z;
            return this;
        }

        public ParcelableCallBuilder setVideoCallProvider(IVideoProvider iVideoProvider) {
            this.mVideoCallProvider = iVideoProvider;
            return this;
        }

        public ParcelableCallBuilder setIsRttCallChanged(boolean z) {
            this.mIsRttCallChanged = z;
            return this;
        }

        public ParcelableCallBuilder setRttCall(ParcelableRttCall parcelableRttCall) {
            this.mRttCall = parcelableRttCall;
            return this;
        }

        public ParcelableCallBuilder setParentCallId(String str) {
            this.mParentCallId = str;
            return this;
        }

        public ParcelableCallBuilder setChildCallIds(List<String> list) {
            this.mChildCallIds = list;
            return this;
        }

        public ParcelableCallBuilder setStatusHints(StatusHints statusHints) {
            this.mStatusHints = statusHints;
            return this;
        }

        public ParcelableCallBuilder setVideoState(int i) {
            this.mVideoState = i;
            return this;
        }

        public ParcelableCallBuilder setConferenceableCallIds(List<String> list) {
            this.mConferenceableCallIds = list;
            return this;
        }

        public ParcelableCallBuilder setIntentExtras(Bundle bundle) {
            this.mIntentExtras = bundle;
            return this;
        }

        public ParcelableCallBuilder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public ParcelableCallBuilder setCreationTimeMillis(long j) {
            this.mCreationTimeMillis = j;
            return this;
        }

        public ParcelableCallBuilder setCallDirection(int i) {
            this.mCallDirection = i;
            return this;
        }

        public ParcelableCallBuilder setCallerNumberVerificationStatus(int i) {
            this.mCallerNumberVerificationStatus = i;
            return this;
        }

        public ParcelableCallBuilder setContactDisplayName(String str) {
            this.mContactDisplayName = str;
            return this;
        }

        public ParcelableCallBuilder setActiveChildCallId(String str) {
            this.mActiveChildCallId = str;
            return this;
        }

        public ParcelableCallBuilder setContactPhotoUri(Uri uri) {
            this.mContactPhotoUri = uri;
            return this;
        }

        public ParcelableCallBuilder setAssociatedUser(UserHandle userHandle) {
            this.mAssociatedUser = userHandle;
            return this;
        }

        public ParcelableCall createParcelableCall() {
            return new ParcelableCall(this.mId, this.mState, this.mDisconnectCause, this.mCannedSmsResponses, this.mCapabilities, this.mProperties, this.mSupportedAudioRoutes, this.mConnectTimeMillis, this.mHandle, this.mHandlePresentation, this.mCallerDisplayName, this.mCallerDisplayNamePresentation, this.mGatewayInfo, this.mAccountHandle, this.mIsVideoCallProviderChanged, this.mVideoCallProvider, this.mIsRttCallChanged, this.mRttCall, this.mParentCallId, this.mChildCallIds, this.mStatusHints, this.mVideoState, this.mConferenceableCallIds, this.mIntentExtras, this.mExtras, this.mCreationTimeMillis, this.mCallDirection, this.mCallerNumberVerificationStatus, this.mContactDisplayName, this.mActiveChildCallId, this.mContactPhotoUri, this.mAssociatedUser);
        }

        public static ParcelableCallBuilder fromParcelableCall(ParcelableCall parcelableCall) {
            ParcelableCallBuilder parcelableCallBuilder = new ParcelableCallBuilder();
            parcelableCallBuilder.mId = parcelableCall.mId;
            parcelableCallBuilder.mState = parcelableCall.mState;
            parcelableCallBuilder.mDisconnectCause = parcelableCall.mDisconnectCause;
            parcelableCallBuilder.mCannedSmsResponses = parcelableCall.mCannedSmsResponses;
            parcelableCallBuilder.mCapabilities = parcelableCall.mCapabilities;
            parcelableCallBuilder.mProperties = parcelableCall.mProperties;
            parcelableCallBuilder.mSupportedAudioRoutes = parcelableCall.mSupportedAudioRoutes;
            parcelableCallBuilder.mConnectTimeMillis = parcelableCall.mConnectTimeMillis;
            parcelableCallBuilder.mHandle = parcelableCall.mHandle;
            parcelableCallBuilder.mHandlePresentation = parcelableCall.mHandlePresentation;
            parcelableCallBuilder.mCallerDisplayName = parcelableCall.mCallerDisplayName;
            parcelableCallBuilder.mCallerDisplayNamePresentation = parcelableCall.mCallerDisplayNamePresentation;
            parcelableCallBuilder.mGatewayInfo = parcelableCall.mGatewayInfo;
            parcelableCallBuilder.mAccountHandle = parcelableCall.mAccountHandle;
            parcelableCallBuilder.mIsVideoCallProviderChanged = parcelableCall.mIsVideoCallProviderChanged;
            parcelableCallBuilder.mVideoCallProvider = parcelableCall.mVideoCallProvider;
            parcelableCallBuilder.mIsRttCallChanged = parcelableCall.mIsRttCallChanged;
            parcelableCallBuilder.mRttCall = parcelableCall.mRttCall;
            parcelableCallBuilder.mParentCallId = parcelableCall.mParentCallId;
            parcelableCallBuilder.mChildCallIds = parcelableCall.mChildCallIds;
            parcelableCallBuilder.mStatusHints = parcelableCall.mStatusHints;
            parcelableCallBuilder.mVideoState = parcelableCall.mVideoState;
            parcelableCallBuilder.mConferenceableCallIds = parcelableCall.mConferenceableCallIds;
            parcelableCallBuilder.mIntentExtras = parcelableCall.mIntentExtras;
            parcelableCallBuilder.mExtras = parcelableCall.mExtras;
            parcelableCallBuilder.mCreationTimeMillis = parcelableCall.mCreationTimeMillis;
            parcelableCallBuilder.mCallDirection = parcelableCall.mCallDirection;
            parcelableCallBuilder.mCallerNumberVerificationStatus = parcelableCall.mCallerNumberVerificationStatus;
            parcelableCallBuilder.mContactDisplayName = parcelableCall.mContactDisplayName;
            parcelableCallBuilder.mActiveChildCallId = parcelableCall.mActiveChildCallId;
            parcelableCallBuilder.mContactPhotoUri = parcelableCall.mContactPhotoUri;
            parcelableCallBuilder.mAssociatedUser = parcelableCall.mAssociatedUser;
            return parcelableCallBuilder;
        }
    }

    public ParcelableCall(String str, int i, DisconnectCause disconnectCause, List<String> list, int i2, int i3, int i4, long j, Uri uri, int i5, String str2, int i6, GatewayInfo gatewayInfo, PhoneAccountHandle phoneAccountHandle, boolean z, IVideoProvider iVideoProvider, boolean z2, ParcelableRttCall parcelableRttCall, String str3, List<String> list2, StatusHints statusHints, int i7, List<String> list3, Bundle bundle, Bundle bundle2, long j2, int i8, int i9, String str4, String str5, Uri uri2, UserHandle userHandle) {
        this.mId = str;
        this.mState = i;
        this.mDisconnectCause = disconnectCause;
        this.mCannedSmsResponses = list;
        this.mCapabilities = i2;
        this.mProperties = i3;
        this.mSupportedAudioRoutes = i4;
        this.mConnectTimeMillis = j;
        this.mHandle = uri;
        this.mHandlePresentation = i5;
        this.mCallerDisplayName = str2;
        this.mCallerDisplayNamePresentation = i6;
        this.mGatewayInfo = gatewayInfo;
        this.mAccountHandle = phoneAccountHandle;
        this.mIsVideoCallProviderChanged = z;
        this.mVideoCallProvider = iVideoProvider;
        this.mIsRttCallChanged = z2;
        this.mRttCall = parcelableRttCall;
        this.mParentCallId = str3;
        this.mChildCallIds = list2;
        this.mStatusHints = statusHints;
        this.mVideoState = i7;
        this.mConferenceableCallIds = Collections.unmodifiableList(list3);
        this.mIntentExtras = bundle;
        this.mExtras = bundle2;
        this.mCreationTimeMillis = j2;
        this.mCallDirection = i8;
        this.mCallerNumberVerificationStatus = i9;
        this.mContactDisplayName = str4;
        this.mActiveChildCallId = str5;
        this.mContactPhotoUri = uri2;
        this.mAssociatedUser = userHandle;
    }

    public String getId() {
        return this.mId;
    }

    public int getState() {
        return this.mState;
    }

    public DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public List<String> getCannedSmsResponses() {
        return this.mCannedSmsResponses;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public int getProperties() {
        return this.mProperties;
    }

    public int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public long getConnectTimeMillis() {
        return this.mConnectTimeMillis;
    }

    public Uri getHandle() {
        return this.mHandle;
    }

    public int getHandlePresentation() {
        return this.mHandlePresentation;
    }

    public String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public GatewayInfo getGatewayInfo() {
        return this.mGatewayInfo;
    }

    public PhoneAccountHandle getAccountHandle() {
        return this.mAccountHandle;
    }

    public VideoCallImpl getVideoCallImpl(String str, int i) {
        if (this.mVideoCall == null && this.mVideoCallProvider != null) {
            try {
                this.mVideoCall = new VideoCallImpl(this.mVideoCallProvider, str, i);
            } catch (RemoteException unused) {
            }
        }
        return this.mVideoCall;
    }

    public IVideoProvider getVideoProvider() {
        return this.mVideoCallProvider;
    }

    public boolean getIsRttCallChanged() {
        return this.mIsRttCallChanged;
    }

    public ParcelableRttCall getParcelableRttCall() {
        return this.mRttCall;
    }

    public String getParentCallId() {
        return this.mParentCallId;
    }

    public List<String> getChildCallIds() {
        return this.mChildCallIds;
    }

    public List<String> getConferenceableCallIds() {
        return this.mConferenceableCallIds;
    }

    public StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Bundle getIntentExtras() {
        return this.mIntentExtras;
    }

    public boolean isVideoCallProviderChanged() {
        return this.mIsVideoCallProviderChanged;
    }

    public long getCreationTimeMillis() {
        return this.mCreationTimeMillis;
    }

    public int getCallDirection() {
        return this.mCallDirection;
    }

    public int getCallerNumberVerificationStatus() {
        return this.mCallerNumberVerificationStatus;
    }

    public String getContactDisplayName() {
        return this.mContactDisplayName;
    }

    public Uri getContactPhotoUri() {
        return this.mContactPhotoUri;
    }

    public UserHandle getAssociatedUser() {
        return this.mAssociatedUser;
    }

    public String getActiveChildCallId() {
        return this.mActiveChildCallId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeInt(this.mState);
        parcel.writeParcelable(this.mDisconnectCause, 0);
        parcel.writeList(this.mCannedSmsResponses);
        parcel.writeInt(this.mCapabilities);
        parcel.writeInt(this.mProperties);
        parcel.writeLong(this.mConnectTimeMillis);
        Uri.writeToParcel(parcel, this.mHandle);
        parcel.writeInt(this.mHandlePresentation);
        parcel.writeString(this.mCallerDisplayName);
        parcel.writeInt(this.mCallerDisplayNamePresentation);
        parcel.writeParcelable(this.mGatewayInfo, 0);
        parcel.writeParcelable(this.mAccountHandle, 0);
        parcel.writeByte(this.mIsVideoCallProviderChanged ? (byte) 1 : (byte) 0);
        IVideoProvider iVideoProvider = this.mVideoCallProvider;
        parcel.writeStrongBinder(iVideoProvider != null ? iVideoProvider.asBinder() : null);
        parcel.writeString(this.mParentCallId);
        parcel.writeList(this.mChildCallIds);
        parcel.writeParcelable(this.mStatusHints, 0);
        parcel.writeInt(this.mVideoState);
        parcel.writeList(this.mConferenceableCallIds);
        parcel.writeBundle(this.mIntentExtras);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mSupportedAudioRoutes);
        parcel.writeByte(this.mIsRttCallChanged ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.mRttCall, 0);
        parcel.writeLong(this.mCreationTimeMillis);
        parcel.writeInt(this.mCallDirection);
        parcel.writeInt(this.mCallerNumberVerificationStatus);
        parcel.writeString(this.mContactDisplayName);
        parcel.writeString(this.mActiveChildCallId);
        parcel.writeParcelable(this.mContactPhotoUri, 0);
        parcel.writeParcelable(this.mAssociatedUser, 0);
    }

    public String toString() {
        return String.format("[%s, parent:%s, children:%s]", this.mId, this.mParentCallId, this.mChildCallIds);
    }
}
