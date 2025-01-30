package android.telecom;

import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.RemoteException;
import android.telephony.Rlog;
import com.android.internal.telecom.IVideoProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class ParcelableCall implements Parcelable {
  public static final Parcelable.Creator<ParcelableCall> CREATOR =
      new Parcelable.Creator<ParcelableCall>() { // from class: android.telecom.ParcelableCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableCall createFromParcel(Parcel source) {
          ClassLoader classLoader = ParcelableCall.class.getClassLoader();
          String id = source.readString();
          int state = source.readInt();
          DisconnectCause disconnectCause =
              (DisconnectCause) source.readParcelable(classLoader, DisconnectCause.class);
          ArrayList arrayList = new ArrayList();
          source.readList(arrayList, classLoader, String.class);
          int capabilities = source.readInt();
          int properties = source.readInt();
          long connectTimeMillis = source.readLong();
          Uri handle = Uri.CREATOR.createFromParcel(source);
          int handlePresentation = source.readInt();
          String callerDisplayName = source.readString();
          int callerDisplayNamePresentation = source.readInt();
          GatewayInfo gatewayInfo =
              (GatewayInfo) source.readParcelable(classLoader, GatewayInfo.class);
          PhoneAccountHandle accountHandle =
              (PhoneAccountHandle) source.readParcelable(classLoader, PhoneAccountHandle.class);
          boolean isVideoCallProviderChanged = source.readByte() == 1;
          IVideoProvider videoCallProvider =
              IVideoProvider.Stub.asInterface(source.readStrongBinder());
          String parentCallId = source.readString();
          ArrayList arrayList2 = new ArrayList();
          boolean isVideoCallProviderChanged2 = isVideoCallProviderChanged;
          source.readList(arrayList2, classLoader, String.class);
          StatusHints statusHints =
              (StatusHints) source.readParcelable(classLoader, StatusHints.class);
          int videoState = source.readInt();
          ArrayList arrayList3 = new ArrayList();
          source.readList(arrayList3, classLoader, String.class);
          Bundle intentExtras = source.readBundle(classLoader);
          Bundle extras = source.readBundle(classLoader);
          int supportedAudioRoutes = source.readInt();
          boolean isRttCallChanged = source.readByte() == 1;
          ParcelableRttCall rttCall =
              (ParcelableRttCall) source.readParcelable(classLoader, ParcelableRttCall.class);
          long creationTimeMillis = source.readLong();
          int callDirection = source.readInt();
          int callerNumberVerificationStatus = source.readInt();
          String contactDisplayName = source.readString();
          String activeChildCallId = source.readString();
          Uri contactPhotoUri = (Uri) source.readParcelable(classLoader, Uri.class);
          return new ParcelableCallBuilder()
              .setId(id)
              .setState(state)
              .setDisconnectCause(disconnectCause)
              .setCannedSmsResponses(arrayList)
              .setCapabilities(capabilities)
              .setProperties(properties)
              .setSupportedAudioRoutes(supportedAudioRoutes)
              .setConnectTimeMillis(connectTimeMillis)
              .setHandle(handle)
              .setHandlePresentation(handlePresentation)
              .setCallerDisplayName(callerDisplayName)
              .setCallerDisplayNamePresentation(callerDisplayNamePresentation)
              .setGatewayInfo(gatewayInfo)
              .setAccountHandle(accountHandle)
              .setIsVideoCallProviderChanged(isVideoCallProviderChanged2)
              .setVideoCallProvider(videoCallProvider)
              .setIsRttCallChanged(isRttCallChanged)
              .setRttCall(rttCall)
              .setParentCallId(parentCallId)
              .setChildCallIds(arrayList2)
              .setStatusHints(statusHints)
              .setVideoState(videoState)
              .setConferenceableCallIds(arrayList3)
              .setIntentExtras(intentExtras)
              .setExtras(extras)
              .setCreationTimeMillis(creationTimeMillis)
              .setCallDirection(callDirection)
              .setCallerNumberVerificationStatus(callerNumberVerificationStatus)
              .setContactDisplayName(contactDisplayName)
              .setActiveChildCallId(activeChildCallId)
              .setContactPhotoUri(contactPhotoUri)
              .createParcelableCall();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableCall[] newArray(int size) {
          return new ParcelableCall[size];
        }
      };
  private final PhoneAccountHandle mAccountHandle;
  private final String mActiveChildCallId;
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

  public static class ParcelableCallBuilder {
    private PhoneAccountHandle mAccountHandle;
    private String mActiveChildCallId;
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

    public ParcelableCallBuilder setId(String id) {
      this.mId = id;
      return this;
    }

    public ParcelableCallBuilder setState(int state) {
      this.mState = state;
      return this;
    }

    public ParcelableCallBuilder setDisconnectCause(DisconnectCause disconnectCause) {
      this.mDisconnectCause = disconnectCause;
      return this;
    }

    public ParcelableCallBuilder setCannedSmsResponses(List<String> cannedSmsResponses) {
      this.mCannedSmsResponses = cannedSmsResponses;
      return this;
    }

    public ParcelableCallBuilder setCapabilities(int capabilities) {
      this.mCapabilities = capabilities;
      return this;
    }

    public ParcelableCallBuilder setProperties(int properties) {
      this.mProperties = properties;
      return this;
    }

    public ParcelableCallBuilder setSupportedAudioRoutes(int supportedAudioRoutes) {
      this.mSupportedAudioRoutes = supportedAudioRoutes;
      return this;
    }

    public ParcelableCallBuilder setConnectTimeMillis(long connectTimeMillis) {
      this.mConnectTimeMillis = connectTimeMillis;
      return this;
    }

    public ParcelableCallBuilder setHandle(Uri handle) {
      this.mHandle = handle;
      return this;
    }

    public ParcelableCallBuilder setHandlePresentation(int handlePresentation) {
      this.mHandlePresentation = handlePresentation;
      return this;
    }

    public ParcelableCallBuilder setCallerDisplayName(String callerDisplayName) {
      this.mCallerDisplayName = callerDisplayName;
      return this;
    }

    public ParcelableCallBuilder setCallerDisplayNamePresentation(
        int callerDisplayNamePresentation) {
      this.mCallerDisplayNamePresentation = callerDisplayNamePresentation;
      return this;
    }

    public ParcelableCallBuilder setGatewayInfo(GatewayInfo gatewayInfo) {
      this.mGatewayInfo = gatewayInfo;
      return this;
    }

    public ParcelableCallBuilder setAccountHandle(PhoneAccountHandle accountHandle) {
      this.mAccountHandle = accountHandle;
      return this;
    }

    public ParcelableCallBuilder setIsVideoCallProviderChanged(boolean isVideoCallProviderChanged) {
      this.mIsVideoCallProviderChanged = isVideoCallProviderChanged;
      return this;
    }

    public ParcelableCallBuilder setVideoCallProvider(IVideoProvider videoCallProvider) {
      this.mVideoCallProvider = videoCallProvider;
      return this;
    }

    public ParcelableCallBuilder setIsRttCallChanged(boolean isRttCallChanged) {
      this.mIsRttCallChanged = isRttCallChanged;
      return this;
    }

    public ParcelableCallBuilder setRttCall(ParcelableRttCall rttCall) {
      this.mRttCall = rttCall;
      return this;
    }

    public ParcelableCallBuilder setParentCallId(String parentCallId) {
      this.mParentCallId = parentCallId;
      return this;
    }

    public ParcelableCallBuilder setChildCallIds(List<String> childCallIds) {
      this.mChildCallIds = childCallIds;
      return this;
    }

    public ParcelableCallBuilder setStatusHints(StatusHints statusHints) {
      this.mStatusHints = statusHints;
      return this;
    }

    public ParcelableCallBuilder setVideoState(int videoState) {
      this.mVideoState = videoState;
      return this;
    }

    public ParcelableCallBuilder setConferenceableCallIds(List<String> conferenceableCallIds) {
      this.mConferenceableCallIds = conferenceableCallIds;
      return this;
    }

    public ParcelableCallBuilder setIntentExtras(Bundle intentExtras) {
      this.mIntentExtras = intentExtras;
      return this;
    }

    public ParcelableCallBuilder setExtras(Bundle extras) {
      this.mExtras = extras;
      return this;
    }

    public ParcelableCallBuilder setCreationTimeMillis(long creationTimeMillis) {
      this.mCreationTimeMillis = creationTimeMillis;
      return this;
    }

    public ParcelableCallBuilder setCallDirection(int callDirection) {
      this.mCallDirection = callDirection;
      return this;
    }

    public ParcelableCallBuilder setCallerNumberVerificationStatus(
        int callerNumberVerificationStatus) {
      this.mCallerNumberVerificationStatus = callerNumberVerificationStatus;
      return this;
    }

    public ParcelableCallBuilder setContactDisplayName(String contactDisplayName) {
      this.mContactDisplayName = contactDisplayName;
      return this;
    }

    public ParcelableCallBuilder setActiveChildCallId(String activeChildCallId) {
      this.mActiveChildCallId = activeChildCallId;
      return this;
    }

    public ParcelableCallBuilder setContactPhotoUri(Uri contactPhotoUri) {
      this.mContactPhotoUri = contactPhotoUri;
      return this;
    }

    public ParcelableCall createParcelableCall() {
      return new ParcelableCall(
          this.mId,
          this.mState,
          this.mDisconnectCause,
          this.mCannedSmsResponses,
          this.mCapabilities,
          this.mProperties,
          this.mSupportedAudioRoutes,
          this.mConnectTimeMillis,
          this.mHandle,
          this.mHandlePresentation,
          this.mCallerDisplayName,
          this.mCallerDisplayNamePresentation,
          this.mGatewayInfo,
          this.mAccountHandle,
          this.mIsVideoCallProviderChanged,
          this.mVideoCallProvider,
          this.mIsRttCallChanged,
          this.mRttCall,
          this.mParentCallId,
          this.mChildCallIds,
          this.mStatusHints,
          this.mVideoState,
          this.mConferenceableCallIds,
          this.mIntentExtras,
          this.mExtras,
          this.mCreationTimeMillis,
          this.mCallDirection,
          this.mCallerNumberVerificationStatus,
          this.mContactDisplayName,
          this.mActiveChildCallId,
          this.mContactPhotoUri);
    }

    public static ParcelableCallBuilder fromParcelableCall(ParcelableCall parcelableCall) {
      ParcelableCallBuilder newBuilder = new ParcelableCallBuilder();
      newBuilder.mId = parcelableCall.mId;
      newBuilder.mState = parcelableCall.mState;
      newBuilder.mDisconnectCause = parcelableCall.mDisconnectCause;
      newBuilder.mCannedSmsResponses = parcelableCall.mCannedSmsResponses;
      newBuilder.mCapabilities = parcelableCall.mCapabilities;
      newBuilder.mProperties = parcelableCall.mProperties;
      newBuilder.mSupportedAudioRoutes = parcelableCall.mSupportedAudioRoutes;
      newBuilder.mConnectTimeMillis = parcelableCall.mConnectTimeMillis;
      newBuilder.mHandle = parcelableCall.mHandle;
      newBuilder.mHandlePresentation = parcelableCall.mHandlePresentation;
      newBuilder.mCallerDisplayName = parcelableCall.mCallerDisplayName;
      newBuilder.mCallerDisplayNamePresentation = parcelableCall.mCallerDisplayNamePresentation;
      newBuilder.mGatewayInfo = parcelableCall.mGatewayInfo;
      newBuilder.mAccountHandle = parcelableCall.mAccountHandle;
      newBuilder.mIsVideoCallProviderChanged = parcelableCall.mIsVideoCallProviderChanged;
      newBuilder.mVideoCallProvider = parcelableCall.mVideoCallProvider;
      newBuilder.mIsRttCallChanged = parcelableCall.mIsRttCallChanged;
      newBuilder.mRttCall = parcelableCall.mRttCall;
      newBuilder.mParentCallId = parcelableCall.mParentCallId;
      newBuilder.mChildCallIds = parcelableCall.mChildCallIds;
      newBuilder.mStatusHints = parcelableCall.mStatusHints;
      newBuilder.mVideoState = parcelableCall.mVideoState;
      newBuilder.mConferenceableCallIds = parcelableCall.mConferenceableCallIds;
      newBuilder.mIntentExtras = parcelableCall.mIntentExtras;
      newBuilder.mExtras = parcelableCall.mExtras;
      newBuilder.mCreationTimeMillis = parcelableCall.mCreationTimeMillis;
      newBuilder.mCallDirection = parcelableCall.mCallDirection;
      newBuilder.mCallerNumberVerificationStatus = parcelableCall.mCallerNumberVerificationStatus;
      newBuilder.mContactDisplayName = parcelableCall.mContactDisplayName;
      newBuilder.mActiveChildCallId = parcelableCall.mActiveChildCallId;
      newBuilder.mContactPhotoUri = parcelableCall.mContactPhotoUri;
      return newBuilder;
    }
  }

  public ParcelableCall(
      String id,
      int state,
      DisconnectCause disconnectCause,
      List<String> cannedSmsResponses,
      int capabilities,
      int properties,
      int supportedAudioRoutes,
      long connectTimeMillis,
      Uri handle,
      int handlePresentation,
      String callerDisplayName,
      int callerDisplayNamePresentation,
      GatewayInfo gatewayInfo,
      PhoneAccountHandle accountHandle,
      boolean isVideoCallProviderChanged,
      IVideoProvider videoCallProvider,
      boolean isRttCallChanged,
      ParcelableRttCall rttCall,
      String parentCallId,
      List<String> childCallIds,
      StatusHints statusHints,
      int videoState,
      List<String> conferenceableCallIds,
      Bundle intentExtras,
      Bundle extras,
      long creationTimeMillis,
      int callDirection,
      int callerNumberVerificationStatus,
      String contactDisplayName,
      String activeChildCallId,
      Uri contactPhotoUri) {
    this.mId = id;
    this.mState = state;
    this.mDisconnectCause = disconnectCause;
    this.mCannedSmsResponses = cannedSmsResponses;
    this.mCapabilities = capabilities;
    this.mProperties = properties;
    this.mSupportedAudioRoutes = supportedAudioRoutes;
    this.mConnectTimeMillis = connectTimeMillis;
    this.mHandle = handle;
    this.mHandlePresentation = handlePresentation;
    this.mCallerDisplayName = callerDisplayName;
    this.mCallerDisplayNamePresentation = callerDisplayNamePresentation;
    this.mGatewayInfo = gatewayInfo;
    this.mAccountHandle = accountHandle;
    this.mIsVideoCallProviderChanged = isVideoCallProviderChanged;
    this.mVideoCallProvider = videoCallProvider;
    this.mIsRttCallChanged = isRttCallChanged;
    this.mRttCall = rttCall;
    this.mParentCallId = parentCallId;
    this.mChildCallIds = childCallIds;
    this.mStatusHints = statusHints;
    this.mVideoState = videoState;
    this.mConferenceableCallIds = Collections.unmodifiableList(conferenceableCallIds);
    this.mIntentExtras = intentExtras;
    this.mExtras = extras;
    this.mCreationTimeMillis = creationTimeMillis;
    this.mCallDirection = callDirection;
    this.mCallerNumberVerificationStatus = callerNumberVerificationStatus;
    this.mContactDisplayName = contactDisplayName;
    this.mActiveChildCallId = activeChildCallId;
    this.mContactPhotoUri = contactPhotoUri;
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

  public VideoCallImpl getVideoCallImpl(String callingPackageName, int targetSdkVersion) {
    IVideoProvider iVideoProvider;
    if (this.mVideoCall == null && (iVideoProvider = this.mVideoCallProvider) != null) {
      try {
        this.mVideoCall = new VideoCallImpl(iVideoProvider, callingPackageName, targetSdkVersion);
        Rlog.m77d(
            "Telecom-ParcelableCall",
            "Make new VideoCallImpl - VideoCallProvider: "
                + this.mVideoCallProvider
                + ", Package: "
                + callingPackageName
                + ", SdkVer: "
                + targetSdkVersion);
      } catch (RemoteException e) {
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

  public String getActiveChildCallId() {
    return this.mActiveChildCallId;
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
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
  }

  public String toString() {
    return String.format(
        "[%s, parent:%s, children:%s]", this.mId, this.mParentCallId, this.mChildCallIds);
  }
}
