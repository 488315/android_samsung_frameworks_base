package android.telecom;

import android.annotation.SystemApi;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.location.Location;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.OutcomeReceiver;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.CallScreeningService;
import android.telecom.Conference;
import android.telecom.Connection;
import android.telecom.VideoProfile;
import android.util.ArraySet;
import android.view.Surface;
import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.IVideoCallback;
import com.android.internal.telecom.IVideoProvider;
import com.android.internal.transition.EpicenterTranslateClipReveal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public abstract class Connection extends Conferenceable {
    public static final int AUDIO_CODEC_AMR = 1;
    public static final int AUDIO_CODEC_AMR_WB = 2;
    public static final int AUDIO_CODEC_EVRC = 4;
    public static final int AUDIO_CODEC_EVRC_B = 5;
    public static final int AUDIO_CODEC_EVRC_NW = 7;
    public static final int AUDIO_CODEC_EVRC_WB = 6;
    public static final int AUDIO_CODEC_EVS_FB = 20;
    public static final int AUDIO_CODEC_EVS_NB = 17;
    public static final int AUDIO_CODEC_EVS_SWB = 19;
    public static final int AUDIO_CODEC_EVS_WB = 18;
    public static final int AUDIO_CODEC_G711A = 13;
    public static final int AUDIO_CODEC_G711AB = 15;
    public static final int AUDIO_CODEC_G711U = 11;
    public static final int AUDIO_CODEC_G722 = 14;
    public static final int AUDIO_CODEC_G723 = 12;
    public static final int AUDIO_CODEC_G729 = 16;
    public static final int AUDIO_CODEC_GSM_EFR = 8;
    public static final int AUDIO_CODEC_GSM_FR = 9;
    public static final int AUDIO_CODEC_GSM_HR = 10;
    public static final int AUDIO_CODEC_NONE = 0;
    public static final int AUDIO_CODEC_QCELP13K = 3;
    public static final int CAPABILITY_ADD_PARTICIPANT = 67108864;
    public static final int CAPABILITY_CANNOT_DOWNGRADE_VIDEO_TO_AUDIO = 8388608;
    public static final int CAPABILITY_CAN_PAUSE_VIDEO = 1048576;
    public static final int CAPABILITY_CAN_PULL_CALL = 16777216;
    public static final int CAPABILITY_CAN_SEND_RESPONSE_VIA_CONNECTION = 4194304;
    public static final int CAPABILITY_CAN_UPGRADE_TO_VIDEO = 524288;

    @SystemApi
    public static final int CAPABILITY_CONFERENCE_HAS_NO_CHILDREN = 2097152;
    public static final int CAPABILITY_DISCONNECT_FROM_CONFERENCE = 8192;
    public static final int CAPABILITY_HOLD = 1;
    public static final int CAPABILITY_MANAGE_CONFERENCE = 128;
    public static final int CAPABILITY_MERGE_CONFERENCE = 4;
    public static final int CAPABILITY_MUTE = 64;
    public static final int CAPABILITY_REMOTE_PARTY_SUPPORTS_RTT = 536870912;
    public static final int CAPABILITY_RESPOND_VIA_TEXT = 32;
    public static final int CAPABILITY_SEPARATE_FROM_CONFERENCE = 4096;

    @SystemApi
    public static final int CAPABILITY_SPEED_UP_MT_AUDIO = 262144;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_BIDIRECTIONAL = 768;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_RX = 256;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_TX = 512;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_BIDIRECTIONAL = 3072;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_RX = 1024;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_TX = 2048;
    public static final int CAPABILITY_SUPPORT_DEFLECT = 33554432;
    public static final int CAPABILITY_SUPPORT_HOLD = 2;
    public static final int CAPABILITY_SWAP_CONFERENCE = 8;
    public static final int CAPABILITY_TRANSFER = 134217728;
    public static final int CAPABILITY_TRANSFER_CONSULTATIVE = 268435456;
    public static final int CAPABILITY_UNUSED = 16;
    public static final int CAPABILITY_UNUSED_2 = 16384;
    public static final int CAPABILITY_UNUSED_3 = 32768;
    public static final int CAPABILITY_UNUSED_4 = 65536;
    public static final int CAPABILITY_UNUSED_5 = 131072;
    public static final String EVENT_CALL_HOLD_FAILED = "android.telecom.event.CALL_HOLD_FAILED";
    public static final String EVENT_CALL_MERGE_FAILED = "android.telecom.event.CALL_MERGE_FAILED";
    public static final String EVENT_CALL_PULL_FAILED = "android.telecom.event.CALL_PULL_FAILED";

    @SystemApi
    public static final String EVENT_CALL_QUALITY_REPORT = "android.telecom.event.CALL_QUALITY_REPORT";
    public static final String EVENT_CALL_REMOTELY_HELD = "android.telecom.event.CALL_REMOTELY_HELD";
    public static final String EVENT_CALL_REMOTELY_UNHELD = "android.telecom.event.CALL_REMOTELY_UNHELD";
    public static final String EVENT_CALL_RESUME_FAILED = "android.telecom.event.CALL_RESUME_FAILED";
    public static final String EVENT_CALL_SWITCH_FAILED = "android.telecom.event.CALL_SWITCH_FAILED";

    @SystemApi
    public static final String EVENT_DEVICE_TO_DEVICE_MESSAGE = "android.telecom.event.DEVICE_TO_DEVICE_MESSAGE";
    public static final String EVENT_MERGE_COMPLETE = "android.telecom.event.MERGE_COMPLETE";
    public static final String EVENT_MERGE_START = "android.telecom.event.MERGE_START";
    public static final String EVENT_ON_HOLD_TONE_END = "android.telecom.event.ON_HOLD_TONE_END";
    public static final String EVENT_ON_HOLD_TONE_START = "android.telecom.event.ON_HOLD_TONE_START";
    public static final String EVENT_RTT_AUDIO_INDICATION_CHANGED = "android.telecom.event.RTT_AUDIO_INDICATION_CHANGED";
    public static final String EXTRA_ADD_TO_CONFERENCE_ID = "android.telecom.extra.ADD_TO_CONFERENCE_ID";
    public static final String EXTRA_ANSWERING_DROPS_FG_CALL = "android.telecom.extra.ANSWERING_DROPS_FG_CALL";
    public static final String EXTRA_ANSWERING_DROPS_FG_CALL_APP_NAME = "android.telecom.extra.ANSWERING_DROPS_FG_CALL_APP_NAME";
    public static final String EXTRA_AUDIO_CODEC = "android.telecom.extra.AUDIO_CODEC";
    public static final String EXTRA_AUDIO_CODEC_BANDWIDTH_KHZ = "android.telecom.extra.AUDIO_CODEC_BANDWIDTH_KHZ";
    public static final String EXTRA_AUDIO_CODEC_BITRATE_KBPS = "android.telecom.extra.AUDIO_CODEC_BITRATE_KBPS";
    public static final String EXTRA_CALLER_NUMBER_VERIFICATION_STATUS = "android.telecom.extra.CALLER_NUMBER_VERIFICATION_STATUS";

    @SystemApi
    public static final String EXTRA_CALL_QUALITY_REPORT = "android.telecom.extra.CALL_QUALITY_REPORT";
    public static final String EXTRA_CALL_SUBJECT = "android.telecom.extra.CALL_SUBJECT";
    public static final String EXTRA_CHILD_ADDRESS = "android.telecom.extra.CHILD_ADDRESS";

    @SystemApi
    public static final String EXTRA_DEVICE_TO_DEVICE_MESSAGE_TYPE = "android.telecom.extra.DEVICE_TO_DEVICE_MESSAGE_TYPE";

    @SystemApi
    public static final String EXTRA_DEVICE_TO_DEVICE_MESSAGE_VALUE = "android.telecom.extra.DEVICE_TO_DEVICE_MESSAGE_VALUE";

    @SystemApi
    public static final String EXTRA_DISABLE_ADD_CALL = "android.telecom.extra.DISABLE_ADD_CALL";
    public static final String EXTRA_IS_DEVICE_TO_DEVICE_COMMUNICATION_AVAILABLE = "android.telecom.extra.IS_DEVICE_TO_DEVICE_COMMUNICATION_AVAILABLE";
    public static final String EXTRA_IS_RTT_AUDIO_PRESENT = "android.telecom.extra.IS_RTT_AUDIO_PRESENT";
    public static final String EXTRA_KEY_QUERY_LOCATION = "android.telecom.extra.KEY_QUERY_LOCATION";
    public static final String EXTRA_LAST_FORWARDED_NUMBER = "android.telecom.extra.LAST_FORWARDED_NUMBER";
    public static final String EXTRA_LAST_KNOWN_CELL_IDENTITY = "android.telecom.extra.LAST_KNOWN_CELL_IDENTITY";
    public static final String EXTRA_ORIGINAL_CONNECTION_ID = "android.telecom.extra.ORIGINAL_CONNECTION_ID";
    public static final String EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME = "android.telecom.extra.REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME";
    public static final String EXTRA_REMOTE_PHONE_ACCOUNT_HANDLE = "android.telecom.extra.REMOTE_PHONE_ACCOUNT_HANDLE";
    public static final String EXTRA_SIP_INVITE = "android.telecom.extra.SIP_INVITE";
    private static final boolean PII_DEBUG;
    public static final int PROPERTY_ASSISTED_DIALING = 512;
    public static final int PROPERTY_CROSS_SIM = 8192;

    @SystemApi
    public static final int PROPERTY_EMERGENCY_CALLBACK_MODE = 1;

    @SystemApi
    public static final int PROPERTY_GENERIC_CONFERENCE = 2;
    public static final int PROPERTY_HAS_CDMA_VOICE_PRIVACY = 32;
    public static final int PROPERTY_HIGH_DEF_AUDIO = 4;
    public static final int PROPERTY_IS_ADHOC_CONFERENCE = 4096;

    @SystemApi
    public static final int PROPERTY_IS_DOWNGRADED_CONFERENCE = 64;
    public static final int PROPERTY_IS_EXTERNAL_CALL = 16;
    public static final int PROPERTY_IS_RTT = 256;
    public static final int PROPERTY_NETWORK_IDENTIFIED_EMERGENCY_CALL = 1024;

    @SystemApi
    public static final int PROPERTY_REMOTELY_HOSTED = 2048;
    public static final int PROPERTY_SELF_MANAGED = 128;
    public static final int PROPERTY_WIFI = 8;
    public static final String SEM_EVENT_CALL_CMC_SECONDARY_DEVICE_PULL = "com.samsung.telecom.event.CALL_SECONDARY_DEVICE_PULL";
    public static final int STATE_ACTIVE = 4;
    public static final int STATE_DIALING = 3;
    public static final int STATE_DISCONNECTED = 6;
    public static final int STATE_HOLDING = 5;
    public static final int STATE_INITIALIZING = 0;
    public static final int STATE_NEW = 1;
    public static final int STATE_PULLING_CALL = 7;
    public static final int STATE_RINGING = 2;
    public static final int VERIFICATION_STATUS_FAILED = 2;
    public static final int VERIFICATION_STATUS_NOT_VERIFIED = 0;
    public static final int VERIFICATION_STATUS_PASSED = 1;
    private Uri mAddress;
    private int mAddressPresentation;
    private boolean mAudioModeIsVoip;
    private CallAudioState mCallAudioState;
    private int mCallDirection;
    private CallEndpoint mCallEndpoint;
    private String mCallerDisplayName;
    private int mCallerDisplayNamePresentation;
    private int mCallerNumberVerificationStatus;
    private Conference mConference;
    private final List<Conferenceable> mConferenceables;
    private long mConnectElapsedTimeMillis;
    private long mConnectTimeMillis;
    private int mConnectionCapabilities;
    private int mConnectionProperties;
    private ConnectionService mConnectionService;
    private DisconnectCause mDisconnectCause;
    private Bundle mExtras;
    private final Object mExtrasLock;
    private PhoneAccountHandle mPhoneAccountHandle;
    private Set<String> mPreviousExtraKeys;
    private boolean mRingbackRequested;
    private int mState;
    private StatusHints mStatusHints;
    private int mSupportedAudioRoutes;
    private String mTelecomCallId;
    private final List<Conferenceable> mUnmodifiableConferenceables;
    private VideoProvider mVideoProvider;
    private int mVideoState;
    private final Listener mConnectionDeathListener = new Listener() { // from class: android.telecom.Connection.1
        @Override // android.telecom.Connection.Listener
        public void onDestroyed(Connection connection) {
            if (Connection.this.mConferenceables.remove(connection)) {
                Connection.this.fireOnConferenceableConnectionsChanged();
            }
        }
    };
    private final Conference.Listener mConferenceDeathListener = new Conference.Listener() { // from class: android.telecom.Connection.2
        @Override // android.telecom.Conference.Listener
        public void onDestroyed(Conference conference) {
            if (Connection.this.mConferenceables.remove(conference)) {
                Connection.this.fireOnConferenceableConnectionsChanged();
            }
        }
    };
    private final Set<Listener> mListeners = Collections.newSetFromMap(new ConcurrentHashMap(8, 0.9f, 1));

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioCodec {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VerificationStatus {
    }

    public void checkImmutable() {
    }

    public void handleRttUpgradeResponse(RttTextStream rttTextStream) {
    }

    public void onAbort() {
    }

    public void onAddConferenceParticipants(List<Uri> list) {
    }

    public void onAnswer(int i) {
    }

    @SystemApi
    @Deprecated
    public void onAudioStateChanged(AudioState audioState) {
    }

    public void onAvailableCallEndpointsChanged(List<CallEndpoint> list) {
    }

    @Deprecated
    public void onCallAudioStateChanged(CallAudioState callAudioState) {
    }

    public void onCallEndpointChanged(CallEndpoint callEndpoint) {
    }

    public void onCallEvent(String str, Bundle bundle) {
    }

    @SystemApi
    public void onCallFilteringCompleted(CallFilteringCompletionInfo callFilteringCompletionInfo) {
    }

    public void onDeflect(Uri uri) {
    }

    public void onDisconnect() {
    }

    public void onExtrasChanged(Bundle bundle) {
    }

    public void onHandoverComplete() {
    }

    public void onHold() {
    }

    public void onMuteStateChanged(boolean z) {
    }

    public void onPlayDtmfTone(char c) {
    }

    public void onPostDialContinue(boolean z) {
    }

    public void onPullExternalCall() {
    }

    public void onReject() {
    }

    public void onReject(int i) {
    }

    public void onReject(String str) {
    }

    public void onSeparate() {
    }

    public void onShowIncomingCallUi() {
    }

    public void onSilence() {
    }

    public void onStartRtt(RttTextStream rttTextStream) {
    }

    public void onStateChanged(int i) {
    }

    public void onStopDtmfTone() {
    }

    public void onStopRtt() {
    }

    public void onTrackedByNonUiService(boolean z) {
    }

    public void onTransfer(Uri uri, boolean z) {
    }

    public void onTransfer(Connection connection) {
    }

    public void onUnhold() {
    }

    public void onUsingAlternativeUi(boolean z) {
    }

    static {
        PII_DEBUG = !Log.SHIP_BUILD && Log.isLoggable(3);
    }

    public static String capabilitiesToString(int i) {
        return capabilitiesToStringInternal(i, true);
    }

    public static String capabilitiesToStringShort(int i) {
        return capabilitiesToStringInternal(i, false);
    }

    private static String capabilitiesToStringInternal(int i, boolean z) {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        if (z) {
            sb.append("Capabilities:");
        }
        if ((i & 1) == 1) {
            sb.append(z ? " CAPABILITY_HOLD" : " hld");
        }
        if ((i & 2) == 2) {
            sb.append(z ? " CAPABILITY_SUPPORT_HOLD" : " sup_hld");
        }
        if ((i & 4) == 4) {
            sb.append(z ? " CAPABILITY_MERGE_CONFERENCE" : " mrg_cnf");
        }
        if ((i & 8) == 8) {
            sb.append(z ? " CAPABILITY_SWAP_CONFERENCE" : " swp_cnf");
        }
        if ((i & 32) == 32) {
            sb.append(z ? " CAPABILITY_RESPOND_VIA_TEXT" : " txt");
        }
        if ((i & 64) == 64) {
            sb.append(z ? " CAPABILITY_MUTE" : " mut");
        }
        if ((i & 128) == 128) {
            sb.append(z ? " CAPABILITY_MANAGE_CONFERENCE" : " mng_cnf");
        }
        if ((i & 256) == 256) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_LOCAL_RX" : " VTlrx");
        }
        if ((i & 512) == 512) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_LOCAL_TX" : " VTltx");
        }
        if ((i & 768) == 768) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_LOCAL_BIDIRECTIONAL" : " VTlbi");
        }
        if ((i & 1024) == 1024) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_REMOTE_RX" : " VTrrx");
        }
        if ((i & 2048) == 2048) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_REMOTE_TX" : " VTrtx");
        }
        if ((i & 3072) == 3072) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_REMOTE_BIDIRECTIONAL" : " VTrbi");
        }
        if ((i & 8388608) == 8388608) {
            sb.append(z ? " CAPABILITY_CANNOT_DOWNGRADE_VIDEO_TO_AUDIO" : " !v2a");
        }
        if ((i & 262144) == 262144) {
            sb.append(z ? " CAPABILITY_SPEED_UP_MT_AUDIO" : " spd_aud");
        }
        if ((i & 524288) == 524288) {
            sb.append(z ? " CAPABILITY_CAN_UPGRADE_TO_VIDEO" : " a2v");
        }
        if ((i & 1048576) == 1048576) {
            sb.append(z ? " CAPABILITY_CAN_PAUSE_VIDEO" : " paus_VT");
        }
        if ((i & 2097152) == 2097152) {
            sb.append(z ? " CAPABILITY_SINGLE_PARTY_CONFERENCE" : " 1p_cnf");
        }
        if ((i & 4194304) == 4194304) {
            sb.append(z ? " CAPABILITY_CAN_SEND_RESPONSE_VIA_CONNECTION" : " rsp_by_con");
        }
        if ((i & 16777216) == 16777216) {
            sb.append(z ? " CAPABILITY_CAN_PULL_CALL" : " pull");
        }
        if ((i & 33554432) == 33554432) {
            sb.append(z ? " CAPABILITY_SUPPORT_DEFLECT" : " sup_def");
        }
        if ((i & 67108864) == 67108864) {
            sb.append(z ? " CAPABILITY_ADD_PARTICIPANT" : " add_participant");
        }
        if ((134217728 & i) == 134217728) {
            sb.append(z ? " CAPABILITY_TRANSFER" : " sup_trans");
        }
        if ((268435456 & i) == 268435456) {
            sb.append(z ? " CAPABILITY_TRANSFER_CONSULTATIVE" : " sup_cTrans");
        }
        if ((i & 536870912) == 536870912) {
            sb.append(z ? " CAPABILITY_REMOTE_PARTY_SUPPORTS_RTT" : " sup_rtt");
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public static String propertiesToString(int i) {
        return propertiesToStringInternal(i, true);
    }

    public static String propertiesToStringShort(int i) {
        return propertiesToStringInternal(i, false);
    }

    private static String propertiesToStringInternal(int i, boolean z) {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        if (z) {
            sb.append("Properties:");
        }
        if ((i & 128) == 128) {
            sb.append(z ? " PROPERTY_SELF_MANAGED" : " self_mng");
        }
        if ((i & 1) == 1) {
            sb.append(z ? " PROPERTY_EMERGENCY_CALLBACK_MODE" : " ecbm");
        }
        if ((i & 4) == 4) {
            sb.append(z ? " PROPERTY_HIGH_DEF_AUDIO" : " HD");
        }
        if ((i & 8) == 8) {
            sb.append(z ? " PROPERTY_WIFI" : " wifi");
        }
        if ((i & 2) == 2) {
            sb.append(z ? " PROPERTY_GENERIC_CONFERENCE" : " gen_conf");
        }
        if ((i & 16) == 16) {
            sb.append(z ? " PROPERTY_IS_EXTERNAL_CALL" : " xtrnl");
        }
        if ((i & 32) == 32) {
            sb.append(z ? " PROPERTY_HAS_CDMA_VOICE_PRIVACY" : " priv");
        }
        if ((i & 256) == 256) {
            sb.append(z ? " PROPERTY_IS_RTT" : " rtt");
        }
        if ((i & 1024) == 1024) {
            sb.append(z ? " PROPERTY_NETWORK_IDENTIFIED_EMERGENCY_CALL" : " ecall");
        }
        if ((i & 2048) == 2048) {
            sb.append(z ? " PROPERTY_REMOTELY_HOSTED" : " remote_hst");
        }
        if ((i & 4096) == 4096) {
            sb.append(z ? " PROPERTY_IS_ADHOC_CONFERENCE" : " adhoc_conf");
        }
        if ((i & 64) == 64) {
            sb.append(z ? " PROPERTY_IS_DOWNGRADED_CONFERENCE" : " dngrd_conf");
        }
        if ((i & 8192) == 8192) {
            sb.append(z ? " PROPERTY_CROSS_SIM" : " xsim");
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    static abstract class Listener {
        public void onAddressChanged(Connection connection, Uri uri, int i) {
        }

        public void onAudioModeIsVoipChanged(Connection connection, boolean z) {
        }

        public void onAudioRouteChanged(Connection connection, int i, String str) {
        }

        public void onCallerDisplayNameChanged(Connection connection, String str, int i) {
        }

        public void onConferenceChanged(Connection connection, Conference conference) {
        }

        public void onConferenceMergeFailed(Connection connection) {
        }

        public void onConferenceablesChanged(Connection connection, List<Conferenceable> list) {
        }

        public void onConnectionCapabilitiesChanged(Connection connection, int i) {
        }

        public void onConnectionEvent(Connection connection, String str, Bundle bundle) {
        }

        public void onConnectionPropertiesChanged(Connection connection, int i) {
        }

        public void onConnectionTimeReset(Connection connection) {
        }

        public void onDestroyed(Connection connection) {
        }

        public void onDisconnected(Connection connection, DisconnectCause disconnectCause) {
        }

        public void onEndpointChanged(Connection connection, CallEndpoint callEndpoint, Executor executor, OutcomeReceiver<Void, CallEndpointException> outcomeReceiver) {
        }

        public void onExtrasChanged(Connection connection, Bundle bundle) {
        }

        public void onExtrasRemoved(Connection connection, List<String> list) {
        }

        public void onPhoneAccountChanged(Connection connection, PhoneAccountHandle phoneAccountHandle) {
        }

        public void onPostDialChar(Connection connection, char c) {
        }

        public void onPostDialWait(Connection connection, String str) {
        }

        public void onQueryLocation(Connection connection, long j, String str, Executor executor, OutcomeReceiver<Location, QueryLocationException> outcomeReceiver) {
        }

        public void onRemoteRttRequest(Connection connection) {
        }

        public void onRingbackRequested(Connection connection, boolean z) {
        }

        public void onRttInitiationFailure(Connection connection, int i) {
        }

        public void onRttInitiationSuccess(Connection connection) {
        }

        public void onRttSessionRemotelyTerminated(Connection connection) {
        }

        public void onStateChanged(Connection connection, int i) {
        }

        public void onStatusHintsChanged(Connection connection, StatusHints statusHints) {
        }

        public void onSupportedAudioRoutesChanged(Connection connection, int i) {
        }

        public void onVideoProviderChanged(Connection connection, VideoProvider videoProvider) {
        }

        public void onVideoStateChanged(Connection connection, int i) {
        }

        Listener() {
        }
    }

    public static final class RttTextStream {
        private static final int READ_BUFFER_SIZE = 1000;
        private final ParcelFileDescriptor mFdFromInCall;
        private final ParcelFileDescriptor mFdToInCall;
        private final FileInputStream mFromInCallFileInputStream;
        private final InputStreamReader mPipeFromInCall;
        private final OutputStreamWriter mPipeToInCall;
        private char[] mReadBuffer = new char[1000];

        public RttTextStream(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) {
            this.mFdFromInCall = parcelFileDescriptor2;
            this.mFdToInCall = parcelFileDescriptor;
            FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptor2.getFileDescriptor());
            this.mFromInCallFileInputStream = fileInputStream;
            this.mPipeFromInCall = new InputStreamReader(Channels.newInputStream(Channels.newChannel(fileInputStream)));
            this.mPipeToInCall = new OutputStreamWriter(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        }

        public void write(String str) throws IOException {
            this.mPipeToInCall.write(str);
            this.mPipeToInCall.flush();
        }

        public String read() throws IOException {
            int read = this.mPipeFromInCall.read(this.mReadBuffer, 0, 1000);
            if (read < 0) {
                return null;
            }
            return new String(this.mReadBuffer, 0, read);
        }

        public String readImmediately() throws IOException {
            if (this.mFromInCallFileInputStream.available() > 0) {
                return read();
            }
            return null;
        }

        public ParcelFileDescriptor getFdFromInCall() {
            return this.mFdFromInCall;
        }

        public ParcelFileDescriptor getFdToInCall() {
            return this.mFdToInCall;
        }
    }

    public static final class RttModifyStatus {
        public static final int SESSION_MODIFY_REQUEST_FAIL = 2;
        public static final int SESSION_MODIFY_REQUEST_INVALID = 3;
        public static final int SESSION_MODIFY_REQUEST_REJECTED_BY_REMOTE = 5;
        public static final int SESSION_MODIFY_REQUEST_SUCCESS = 1;
        public static final int SESSION_MODIFY_REQUEST_TIMED_OUT = 4;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RttSessionModifyStatus {
        }

        private RttModifyStatus() {
        }
    }

    public static abstract class VideoProvider {
        private static final int MSG_ADD_VIDEO_CALLBACK = 1;
        private static final int MSG_REMOVE_VIDEO_CALLBACK = 12;
        private static final int MSG_REQUEST_CAMERA_CAPABILITIES = 9;
        private static final int MSG_REQUEST_CONNECTION_DATA_USAGE = 10;
        private static final int MSG_SEND_SESSION_MODIFY_REQUEST = 7;
        private static final int MSG_SEND_SESSION_MODIFY_RESPONSE = 8;
        private static final int MSG_SET_CAMERA = 2;
        private static final int MSG_SET_DEVICE_ORIENTATION = 5;
        private static final int MSG_SET_DISPLAY_SURFACE = 4;
        private static final int MSG_SET_PAUSE_IMAGE = 11;
        private static final int MSG_SET_PREVIEW_SURFACE = 3;
        private static final int MSG_SET_ZOOM = 6;
        public static final int SESSION_EVENT_CAMERA_FAILURE = 5;
        private static final String SESSION_EVENT_CAMERA_FAILURE_STR = "CAMERA_FAIL";
        public static final int SESSION_EVENT_CAMERA_PERMISSION_ERROR = 7;
        private static final String SESSION_EVENT_CAMERA_PERMISSION_ERROR_STR = "CAMERA_PERMISSION_ERROR";
        public static final int SESSION_EVENT_CAMERA_READY = 6;
        private static final String SESSION_EVENT_CAMERA_READY_STR = "CAMERA_READY";
        public static final int SESSION_EVENT_RX_PAUSE = 1;
        private static final String SESSION_EVENT_RX_PAUSE_STR = "RX_PAUSE";
        public static final int SESSION_EVENT_RX_RESUME = 2;
        private static final String SESSION_EVENT_RX_RESUME_STR = "RX_RESUME";
        public static final int SESSION_EVENT_TX_START = 3;
        private static final String SESSION_EVENT_TX_START_STR = "TX_START";
        public static final int SESSION_EVENT_TX_STOP = 4;
        private static final String SESSION_EVENT_TX_STOP_STR = "TX_STOP";
        private static final String SESSION_EVENT_UNKNOWN_STR = "UNKNOWN";
        public static final int SESSION_MODIFY_REQUEST_FAIL = 2;
        public static final int SESSION_MODIFY_REQUEST_INVALID = 3;
        public static final int SESSION_MODIFY_REQUEST_REJECTED_BY_REMOTE = 5;
        public static final int SESSION_MODIFY_REQUEST_SUCCESS = 1;
        public static final int SESSION_MODIFY_REQUEST_TIMED_OUT = 4;
        private VideoProviderBinder mBinder;
        private VideoProviderHandler mMessageHandler;
        private ConcurrentHashMap<IBinder, IVideoCallback> mVideoCallbacks;

        public abstract void onRequestCameraCapabilities();

        public abstract void onRequestConnectionDataUsage();

        public abstract void onSendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2);

        public abstract void onSendSessionModifyResponse(VideoProfile videoProfile);

        public abstract void onSetCamera(String str);

        public void onSetCamera(String str, String str2, int i, int i2, int i3) {
        }

        public abstract void onSetDeviceOrientation(int i);

        public abstract void onSetDisplaySurface(Surface surface);

        public abstract void onSetPauseImage(Uri uri);

        public abstract void onSetPreviewSurface(Surface surface);

        public abstract void onSetZoom(float f);

        private final class VideoProviderHandler extends Handler {
            public VideoProviderHandler() {
            }

            public VideoProviderHandler(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                SomeArgs someArgs;
                switch (message.what) {
                    case 1:
                        IBinder iBinder = (IBinder) message.obj;
                        IVideoCallback asInterface = IVideoCallback.Stub.asInterface((IBinder) message.obj);
                        if (asInterface == null) {
                            Log.w(this, "addVideoProvider - skipped; callback is null.", new Object[0]);
                            return;
                        } else if (VideoProvider.this.mVideoCallbacks.containsKey(iBinder)) {
                            Log.i(this, "addVideoProvider - skipped; already present.", new Object[0]);
                            return;
                        } else {
                            VideoProvider.this.mVideoCallbacks.put(iBinder, asInterface);
                            return;
                        }
                    case 2:
                        someArgs = (SomeArgs) message.obj;
                        try {
                            VideoProvider.this.onSetCamera((String) someArgs.arg1);
                            VideoProvider.this.onSetCamera((String) someArgs.arg1, (String) someArgs.arg2, someArgs.argi1, someArgs.argi2, someArgs.argi3);
                            return;
                        } finally {
                        }
                    case 3:
                        VideoProvider.this.onSetPreviewSurface((Surface) message.obj);
                        return;
                    case 4:
                        VideoProvider.this.onSetDisplaySurface((Surface) message.obj);
                        return;
                    case 5:
                        VideoProvider.this.onSetDeviceOrientation(message.arg1);
                        return;
                    case 6:
                        VideoProvider.this.onSetZoom(((Float) message.obj).floatValue());
                        return;
                    case 7:
                        someArgs = (SomeArgs) message.obj;
                        try {
                            VideoProvider.this.onSendSessionModifyRequest((VideoProfile) someArgs.arg1, (VideoProfile) someArgs.arg2);
                            return;
                        } finally {
                        }
                    case 8:
                        VideoProvider.this.onSendSessionModifyResponse((VideoProfile) message.obj);
                        return;
                    case 9:
                        VideoProvider.this.onRequestCameraCapabilities();
                        return;
                    case 10:
                        VideoProvider.this.onRequestConnectionDataUsage();
                        return;
                    case 11:
                        VideoProvider.this.onSetPauseImage((Uri) message.obj);
                        return;
                    case 12:
                        IBinder iBinder2 = (IBinder) message.obj;
                        IVideoCallback.Stub.asInterface((IBinder) message.obj);
                        if (!VideoProvider.this.mVideoCallbacks.containsKey(iBinder2)) {
                            Log.i(this, "removeVideoProvider - skipped; not present.", new Object[0]);
                            return;
                        } else {
                            VideoProvider.this.mVideoCallbacks.remove(iBinder2);
                            return;
                        }
                    default:
                        return;
                }
            }
        }

        public void close() {
            VideoProviderBinder videoProviderBinder = this.mBinder;
            if (videoProviderBinder != null) {
                videoProviderBinder.reset();
                this.mBinder = null;
            }
            VideoProviderHandler videoProviderHandler = this.mMessageHandler;
            if (videoProviderHandler != null) {
                videoProviderHandler.removeCallbacksAndMessages(null);
                this.mMessageHandler = null;
            }
        }

        private static final class VideoProviderBinder extends IVideoProvider.Stub {
            VideoProviderHandler mMessageHandler;

            public VideoProviderBinder(VideoProviderHandler videoProviderHandler) {
                this.mMessageHandler = videoProviderHandler;
            }

            public void reset() {
                this.mMessageHandler = null;
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void addVideoCallback(IBinder iBinder) {
                this.mMessageHandler.obtainMessage(1, iBinder).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void removeVideoCallback(IBinder iBinder) {
                this.mMessageHandler.obtainMessage(12, iBinder).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setCamera(String str, String str2, int i) {
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = str2;
                obtain.argi1 = Binder.getCallingUid();
                obtain.argi2 = Binder.getCallingPid();
                obtain.argi3 = i;
                this.mMessageHandler.obtainMessage(2, obtain).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setPreviewSurface(Surface surface) {
                this.mMessageHandler.obtainMessage(3, surface).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setDisplaySurface(Surface surface) {
                this.mMessageHandler.obtainMessage(4, surface).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setDeviceOrientation(int i) {
                this.mMessageHandler.obtainMessage(5, i, 0).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setZoom(float f) {
                this.mMessageHandler.obtainMessage(6, Float.valueOf(f)).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) {
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = videoProfile;
                obtain.arg2 = videoProfile2;
                this.mMessageHandler.obtainMessage(7, obtain).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void sendSessionModifyResponse(VideoProfile videoProfile) {
                this.mMessageHandler.obtainMessage(8, videoProfile).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void requestCameraCapabilities() {
                this.mMessageHandler.obtainMessage(9).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void requestCallDataUsage() {
                this.mMessageHandler.obtainMessage(10).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setPauseImage(Uri uri) {
                this.mMessageHandler.obtainMessage(11, uri).sendToTarget();
            }
        }

        public VideoProvider() {
            this.mVideoCallbacks = new ConcurrentHashMap<>(8, 0.9f, 1);
            this.mMessageHandler = new VideoProviderHandler(Looper.getMainLooper());
            this.mBinder = new VideoProviderBinder(this.mMessageHandler);
        }

        public VideoProvider(Looper looper) {
            this.mVideoCallbacks = new ConcurrentHashMap<>(8, 0.9f, 1);
            this.mMessageHandler = new VideoProviderHandler(looper);
            this.mBinder = new VideoProviderBinder(this.mMessageHandler);
        }

        public final IVideoProvider getInterface() {
            return this.mBinder;
        }

        public void receiveSessionModifyRequest(VideoProfile videoProfile) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                Iterator<IVideoCallback> it = concurrentHashMap.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().receiveSessionModifyRequest(videoProfile);
                    } catch (RemoteException e) {
                        Log.w(this, "receiveSessionModifyRequest callback failed", e);
                    }
                }
            }
        }

        public void receiveSessionModifyResponse(int i, VideoProfile videoProfile, VideoProfile videoProfile2) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                Iterator<IVideoCallback> it = concurrentHashMap.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().receiveSessionModifyResponse(i, videoProfile, videoProfile2);
                    } catch (RemoteException e) {
                        Log.w(this, "receiveSessionModifyResponse callback failed", e);
                    }
                }
            }
        }

        public void handleCallSessionEvent(int i) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                Iterator<IVideoCallback> it = concurrentHashMap.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().handleCallSessionEvent(i);
                    } catch (RemoteException e) {
                        Log.w(this, "handleCallSessionEvent callback failed", e);
                    }
                }
            }
        }

        public void changePeerDimensions(int i, int i2) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                Iterator<IVideoCallback> it = concurrentHashMap.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().changePeerDimensions(i, i2);
                    } catch (RemoteException e) {
                        Log.w(this, "changePeerDimensions callback failed", e);
                    }
                }
            }
        }

        public void setCallDataUsage(long j) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                Iterator<IVideoCallback> it = concurrentHashMap.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().changeCallDataUsage(j);
                    } catch (RemoteException e) {
                        Log.w(this, "setCallDataUsage callback failed", e);
                    }
                }
            }
        }

        public void changeCallDataUsage(long j) {
            setCallDataUsage(j);
        }

        public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                Iterator<IVideoCallback> it = concurrentHashMap.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().changeCameraCapabilities(cameraCapabilities);
                    } catch (RemoteException e) {
                        Log.w(this, "changeCameraCapabilities callback failed", e);
                    }
                }
            }
        }

        public void changeVideoQuality(int i) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                Iterator<IVideoCallback> it = concurrentHashMap.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().changeVideoQuality(i);
                    } catch (RemoteException e) {
                        Log.w(this, "changeVideoQuality callback failed", e);
                    }
                }
            }
        }

        public static String sessionEventToString(int i) {
            switch (i) {
                case 1:
                    return SESSION_EVENT_RX_PAUSE_STR;
                case 2:
                    return SESSION_EVENT_RX_RESUME_STR;
                case 3:
                    return SESSION_EVENT_TX_START_STR;
                case 4:
                    return SESSION_EVENT_TX_STOP_STR;
                case 5:
                    return SESSION_EVENT_CAMERA_FAILURE_STR;
                case 6:
                    return SESSION_EVENT_CAMERA_READY_STR;
                case 7:
                    return SESSION_EVENT_CAMERA_PERMISSION_ERROR_STR;
                default:
                    return "UNKNOWN " + i;
            }
        }
    }

    public Connection() {
        ArrayList arrayList = new ArrayList();
        this.mConferenceables = arrayList;
        this.mUnmodifiableConferenceables = Collections.unmodifiableList(arrayList);
        this.mState = 1;
        this.mAddressPresentation = 3;
        this.mRingbackRequested = false;
        this.mSupportedAudioRoutes = 31;
        this.mConnectTimeMillis = 0L;
        this.mConnectElapsedTimeMillis = 0L;
        this.mExtrasLock = new Object();
        this.mCallDirection = -1;
    }

    @SystemApi
    public final String getTelecomCallId() {
        return this.mTelecomCallId;
    }

    public final Uri getAddress() {
        return this.mAddress;
    }

    public final int getAddressPresentation() {
        return this.mAddressPresentation;
    }

    public final String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public final int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public final int getState() {
        return this.mState;
    }

    public final int getVideoState() {
        return this.mVideoState;
    }

    @SystemApi
    @Deprecated
    public final AudioState getAudioState() {
        if (this.mCallAudioState == null) {
            return null;
        }
        return new AudioState(this.mCallAudioState);
    }

    @Deprecated
    public final CallAudioState getCallAudioState() {
        return this.mCallAudioState;
    }

    public final Conference getConference() {
        return this.mConference;
    }

    public final boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public final boolean getAudioModeIsVoip() {
        return this.mAudioModeIsVoip;
    }

    @SystemApi
    public final long getConnectTimeMillis() {
        return this.mConnectTimeMillis;
    }

    @SystemApi
    public final long getConnectionStartElapsedRealtimeMillis() {
        return this.mConnectElapsedTimeMillis;
    }

    public final StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public final Bundle getExtras() {
        Bundle bundle;
        synchronized (this.mExtrasLock) {
            bundle = this.mExtras != null ? new Bundle(this.mExtras) : null;
        }
        return bundle;
    }

    final Connection addConnectionListener(Listener listener) {
        this.mListeners.add(listener);
        return this;
    }

    final Connection removeConnectionListener(Listener listener) {
        if (listener != null) {
            this.mListeners.remove(listener);
        }
        return this;
    }

    public final DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    @SystemApi
    public void setTelecomCallId(String str) {
        this.mTelecomCallId = str;
    }

    final void setCallAudioState(CallAudioState callAudioState) {
        checkImmutable();
        Log.d(this, "setAudioState %s", callAudioState);
        this.mCallAudioState = callAudioState;
        onAudioStateChanged(getAudioState());
        onCallAudioStateChanged(callAudioState);
    }

    final void setCallEndpoint(CallEndpoint callEndpoint) {
        checkImmutable();
        Log.d(this, "setCallEndpoint %s", callEndpoint);
        this.mCallEndpoint = callEndpoint;
        onCallEndpointChanged(callEndpoint);
    }

    final void setAvailableCallEndpoints(List<CallEndpoint> list) {
        checkImmutable();
        Log.d(this, "setAvailableCallEndpoints", new Object[0]);
        onAvailableCallEndpointsChanged(list);
    }

    final void setMuteState(boolean z) {
        checkImmutable();
        Log.d(this, "setMuteState %s", Boolean.valueOf(z));
        onMuteStateChanged(z);
    }

    public static String stateToString(int i) {
        switch (i) {
            case 0:
                return "INITIALIZING";
            case 1:
                return "NEW";
            case 2:
                return "RINGING";
            case 3:
                return "DIALING";
            case 4:
                return "ACTIVE";
            case 5:
                return "HOLDING";
            case 6:
                return "DISCONNECTED";
            case 7:
                return "PULLING_CALL";
            default:
                Log.wtf(Connection.class, "Unknown state %d", Integer.valueOf(i));
                return "UNKNOWN";
        }
    }

    public final int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public final int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public final int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public final void setAddress(Uri uri, int i) {
        Log.d(this, "setAddress %s", Log.maskPii(uri));
        this.mAddress = uri;
        this.mAddressPresentation = i;
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onAddressChanged(this, uri, i);
        }
    }

    public final void setCallerDisplayName(String str, int i) {
        checkImmutable();
        boolean equals = Objects.equals(this.mCallerDisplayName, str);
        boolean z = this.mCallerDisplayNamePresentation != i;
        if (!equals) {
            this.mCallerDisplayName = str;
        }
        if (z) {
            this.mCallerDisplayNamePresentation = i;
        }
        if (!equals || z) {
            Iterator<Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onCallerDisplayNameChanged(this, this.mCallerDisplayName, this.mCallerDisplayNamePresentation);
            }
        }
    }

    public final void setVideoState(int i) {
        checkImmutable();
        Log.d(this, "setVideoState %d", Integer.valueOf(i));
        this.mVideoState = i;
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onVideoStateChanged(this, this.mVideoState);
        }
    }

    public final void setActive() {
        checkImmutable();
        setRingbackRequested(false);
        setState(4);
    }

    public final void setRinging() {
        checkImmutable();
        setState(2);
    }

    public final void setInitializing() {
        checkImmutable();
        setState(0);
    }

    public final void setInitialized() {
        checkImmutable();
        setState(1);
    }

    public final void setDialing() {
        checkImmutable();
        setState(3);
    }

    public final void setPulling() {
        checkImmutable();
        setState(7);
    }

    public final void setOnHold() {
        checkImmutable();
        setState(5);
    }

    public final void setVideoProvider(VideoProvider videoProvider) {
        checkImmutable();
        this.mVideoProvider = videoProvider;
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onVideoProviderChanged(this, videoProvider);
        }
    }

    public final VideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public final void setDisconnected(DisconnectCause disconnectCause) {
        checkImmutable();
        this.mDisconnectCause = disconnectCause;
        setState(6);
        Log.d(this, "Disconnected with cause %s", disconnectCause);
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onDisconnected(this, disconnectCause);
        }
    }

    public final void setPostDialWait(String str) {
        checkImmutable();
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onPostDialWait(this, str);
        }
    }

    public final void setNextPostDialChar(char c) {
        checkImmutable();
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onPostDialChar(this, c);
        }
    }

    public final void setRingbackRequested(boolean z) {
        checkImmutable();
        if (this.mRingbackRequested != z) {
            this.mRingbackRequested = z;
            Iterator<Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onRingbackRequested(this, z);
            }
        }
    }

    public final void setConnectionCapabilities(int i) {
        checkImmutable();
        if (this.mConnectionCapabilities != i) {
            this.mConnectionCapabilities = i;
            Iterator<Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionCapabilitiesChanged(this, this.mConnectionCapabilities);
            }
        }
    }

    public final void setConnectionProperties(int i) {
        checkImmutable();
        if (this.mConnectionProperties != i) {
            this.mConnectionProperties = i;
            Iterator<Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionPropertiesChanged(this, this.mConnectionProperties);
            }
        }
    }

    public final void setSupportedAudioRoutes(int i) {
        if ((i & 9) == 0) {
            throw new IllegalArgumentException("supported audio routes must include either speaker or earpiece");
        }
        if (this.mSupportedAudioRoutes != i) {
            this.mSupportedAudioRoutes = i;
            Iterator<Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onSupportedAudioRoutesChanged(this, this.mSupportedAudioRoutes);
            }
        }
    }

    public final void destroy() {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onDestroyed(this);
        }
    }

    public final void setAudioModeIsVoip(boolean z) {
        if (!z && (this.mConnectionProperties & 128) == 128) {
            Log.i(this, "setAudioModeIsVoip: Ignored request to set a self-managed connection's audioModeIsVoip to false. Doing so can cause unwanted behavior.", new Object[0]);
            return;
        }
        checkImmutable();
        this.mAudioModeIsVoip = z;
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onAudioModeIsVoipChanged(this, z);
        }
    }

    @SystemApi
    public final void setConnectTimeMillis(long j) {
        this.mConnectTimeMillis = j;
    }

    @SystemApi
    public final void setConnectionStartElapsedRealtimeMillis(long j) {
        this.mConnectElapsedTimeMillis = j;
    }

    public final void setStatusHints(StatusHints statusHints) {
        checkImmutable();
        this.mStatusHints = statusHints;
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onStatusHintsChanged(this, statusHints);
        }
    }

    public final void setConferenceableConnections(List<Connection> list) {
        checkImmutable();
        clearConferenceableList();
        for (Connection connection : list) {
            if (!this.mConferenceables.contains(connection)) {
                connection.addConnectionListener(this.mConnectionDeathListener);
                this.mConferenceables.add(connection);
            }
        }
        fireOnConferenceableConnectionsChanged();
    }

    public final void setConferenceables(List<Conferenceable> list) {
        clearConferenceableList();
        for (Conferenceable conferenceable : list) {
            if (!this.mConferenceables.contains(conferenceable)) {
                if (conferenceable instanceof Connection) {
                    ((Connection) conferenceable).addConnectionListener(this.mConnectionDeathListener);
                } else if (conferenceable instanceof Conference) {
                    ((Conference) conferenceable).addListener(this.mConferenceDeathListener);
                }
                this.mConferenceables.add(conferenceable);
            }
        }
        fireOnConferenceableConnectionsChanged();
    }

    @SystemApi
    public final void resetConnectionTime() {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConnectionTimeReset(this);
        }
    }

    public final List<Conferenceable> getConferenceables() {
        return this.mUnmodifiableConferenceables;
    }

    public final void setConnectionService(ConnectionService connectionService) {
        checkImmutable();
        if (this.mConnectionService != null) {
            Log.e(this, new Exception(), "Trying to set ConnectionService on a connection which is already associated with another ConnectionService.", new Object[0]);
        } else {
            this.mConnectionService = connectionService;
        }
    }

    public final void unsetConnectionService(ConnectionService connectionService) {
        if (this.mConnectionService != connectionService) {
            Log.e(this, new Exception(), "Trying to remove ConnectionService from a Connection that does not belong to the ConnectionService.", new Object[0]);
        } else {
            this.mConnectionService = null;
        }
    }

    public final boolean setConference(Conference conference) {
        checkImmutable();
        if (this.mConference != null) {
            return false;
        }
        this.mConference = conference;
        ConnectionService connectionService = this.mConnectionService;
        if (connectionService == null || !connectionService.containsConference(conference)) {
            return true;
        }
        fireConferenceChanged();
        return true;
    }

    public final void resetConference() {
        if (this.mConference != null) {
            Log.d(this, "Conference reset", new Object[0]);
            this.mConference = null;
            fireConferenceChanged();
        }
    }

    public final void setExtras(Bundle bundle) {
        checkImmutable();
        putExtras(bundle);
        if (this.mPreviousExtraKeys != null) {
            ArrayList arrayList = new ArrayList();
            for (String str : this.mPreviousExtraKeys) {
                if (bundle == null || !bundle.containsKey(str)) {
                    arrayList.add(str);
                }
            }
            if (!arrayList.isEmpty()) {
                removeExtras(arrayList);
            }
        }
        if (this.mPreviousExtraKeys == null) {
            this.mPreviousExtraKeys = new ArraySet();
        }
        this.mPreviousExtraKeys.clear();
        if (bundle != null) {
            this.mPreviousExtraKeys.addAll(bundle.keySet());
        }
    }

    public final void putExtras(Bundle bundle) {
        Bundle bundle2;
        checkImmutable();
        if (bundle == null) {
            return;
        }
        synchronized (this.mExtrasLock) {
            if (this.mExtras == null) {
                this.mExtras = new Bundle();
            }
            this.mExtras.putAll(bundle);
            bundle2 = new Bundle(this.mExtras);
        }
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onExtrasChanged(this, new Bundle(bundle2));
        }
    }

    public final void removeExtras(List<String> list) {
        synchronized (this.mExtrasLock) {
            if (this.mExtras != null) {
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    this.mExtras.remove(it.next());
                }
            }
        }
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        Iterator<Listener> it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
            it2.next().onExtrasRemoved(this, unmodifiableList);
        }
    }

    public final void removeExtras(String... strArr) {
        removeExtras(Arrays.asList(strArr));
    }

    @Deprecated
    public final void setAudioRoute(int i) {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onAudioRouteChanged(this, i, null);
        }
    }

    @Deprecated
    public void requestBluetoothAudio(BluetoothDevice bluetoothDevice) {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onAudioRouteChanged(this, 2, bluetoothDevice.getAddress());
        }
    }

    public final void requestCallEndpointChange(CallEndpoint callEndpoint, Executor executor, OutcomeReceiver<Void, CallEndpointException> outcomeReceiver) {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onEndpointChanged(this, callEndpoint, executor, outcomeReceiver);
        }
    }

    public final CallEndpoint getCurrentCallEndpoint() {
        return this.mCallEndpoint;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendRttInitiationSuccess$0(Listener listener) {
        listener.onRttInitiationSuccess(this);
    }

    public final void sendRttInitiationSuccess() {
        this.mListeners.forEach(new Consumer() { // from class: android.telecom.Connection$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Connection.this.lambda$sendRttInitiationSuccess$0((Connection.Listener) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendRttInitiationFailure$1(int i, Listener listener) {
        listener.onRttInitiationFailure(this, i);
    }

    public final void sendRttInitiationFailure(final int i) {
        this.mListeners.forEach(new Consumer() { // from class: android.telecom.Connection$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Connection.this.lambda$sendRttInitiationFailure$1(i, (Connection.Listener) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendRttSessionRemotelyTerminated$2(Listener listener) {
        listener.onRttSessionRemotelyTerminated(this);
    }

    public final void sendRttSessionRemotelyTerminated() {
        this.mListeners.forEach(new Consumer() { // from class: android.telecom.Connection$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Connection.this.lambda$sendRttSessionRemotelyTerminated$2((Connection.Listener) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendRemoteRttRequest$3(Listener listener) {
        listener.onRemoteRttRequest(this);
    }

    public final void sendRemoteRttRequest() {
        this.mListeners.forEach(new Consumer() { // from class: android.telecom.Connection$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Connection.this.lambda$sendRemoteRttRequest$3((Connection.Listener) obj);
            }
        });
    }

    public final void queryLocationForEmergency(final long j, final String str, final Executor executor, final OutcomeReceiver<Location, QueryLocationException> outcomeReceiver) {
        if (str == null || executor == null || outcomeReceiver == null) {
            throw new IllegalArgumentException("There are arguments that must not be null");
        }
        if (j < 100 || j > 5000) {
            throw new IllegalArgumentException("The timeoutMillis should be min 100, max 5000");
        }
        this.mListeners.forEach(new Consumer() { // from class: android.telecom.Connection$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Connection.this.lambda$queryLocationForEmergency$4(j, str, executor, outcomeReceiver, (Connection.Listener) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLocationForEmergency$4(long j, String str, Executor executor, OutcomeReceiver outcomeReceiver, Listener listener) {
        listener.onQueryLocation(this, j, str, executor, outcomeReceiver);
    }

    public void onAnswer() {
        onAnswer(0);
    }

    @SystemApi
    public static final class CallFilteringCompletionInfo implements Parcelable {
        public static final Parcelable.Creator<CallFilteringCompletionInfo> CREATOR = new Parcelable.Creator<CallFilteringCompletionInfo>() { // from class: android.telecom.Connection.CallFilteringCompletionInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CallFilteringCompletionInfo createFromParcel(Parcel parcel) {
                return new CallFilteringCompletionInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CallFilteringCompletionInfo[] newArray(int i) {
                return new CallFilteringCompletionInfo[i];
            }
        };
        private final CallScreeningService.CallResponse mCallResponse;
        private final ComponentName mCallScreeningComponent;
        private final boolean mIsBlocked;
        private final boolean mIsInContacts;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public CallFilteringCompletionInfo(boolean z, boolean z2, CallScreeningService.CallResponse callResponse, ComponentName componentName) {
            this.mIsBlocked = z;
            this.mIsInContacts = z2;
            this.mCallResponse = callResponse;
            this.mCallScreeningComponent = componentName;
        }

        protected CallFilteringCompletionInfo(Parcel parcel) {
            this.mIsBlocked = parcel.readByte() != 0;
            this.mIsInContacts = parcel.readByte() != 0;
            CallScreeningService.ParcelableCallResponse parcelableCallResponse = (CallScreeningService.ParcelableCallResponse) parcel.readParcelable(CallScreeningService.class.getClassLoader(), CallScreeningService.ParcelableCallResponse.class);
            this.mCallResponse = parcelableCallResponse == null ? null : parcelableCallResponse.toCallResponse();
            this.mCallScreeningComponent = (ComponentName) parcel.readParcelable(ComponentName.class.getClassLoader(), ComponentName.class);
        }

        public boolean isBlocked() {
            return this.mIsBlocked;
        }

        public boolean isInContacts() {
            return this.mIsInContacts;
        }

        public CallScreeningService.CallResponse getCallResponse() {
            return this.mCallResponse;
        }

        public ComponentName getCallScreeningComponent() {
            return this.mCallScreeningComponent;
        }

        public String toString() {
            return "CallFilteringCompletionInfo{mIsBlocked=" + this.mIsBlocked + ", mIsInContacts=" + this.mIsInContacts + ", mCallResponse=" + this.mCallResponse + ", mCallScreeningPackageName='" + this.mCallScreeningComponent + "'}";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mIsBlocked ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mIsInContacts ? (byte) 1 : (byte) 0);
            CallScreeningService.CallResponse callResponse = this.mCallResponse;
            parcel.writeParcelable(callResponse == null ? null : callResponse.toParcelable(), 0);
            parcel.writeParcelable(this.mCallScreeningComponent, 0);
        }
    }

    static String toLogSafePhoneNumber(String str) {
        if (str == null) {
            return "";
        }
        if (PII_DEBUG) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '-' || charAt == '@' || charAt == '.') {
                sb.append(charAt);
            } else {
                sb.append(EpicenterTranslateClipReveal.StateProperty.TARGET_X);
            }
        }
        return sb.toString();
    }

    private void setState(int i) {
        checkImmutable();
        int i2 = this.mState;
        if (i2 == 6 && i2 != i) {
            Log.d(this, "Connection already DISCONNECTED; cannot transition out of this state.", new Object[0]);
            return;
        }
        if (i2 != i) {
            Log.d(this, "setState: %s", stateToString(i));
            this.mState = i;
            onStateChanged(i);
            Iterator<Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onStateChanged(this, i);
            }
        }
    }

    private static class FailureSignalingConnection extends Connection {
        private boolean mImmutable;

        public FailureSignalingConnection(DisconnectCause disconnectCause) {
            this.mImmutable = false;
            setDisconnected(disconnectCause);
            this.mImmutable = true;
        }

        @Override // android.telecom.Connection
        public void checkImmutable() {
            if (this.mImmutable) {
                throw new UnsupportedOperationException("Connection is immutable");
            }
        }
    }

    public static Connection createFailedConnection(DisconnectCause disconnectCause) {
        return new FailureSignalingConnection(disconnectCause);
    }

    public static Connection createCanceledConnection() {
        return new FailureSignalingConnection(new DisconnectCause(4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fireOnConferenceableConnectionsChanged() {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConferenceablesChanged(this, getConferenceables());
        }
    }

    private final void fireConferenceChanged() {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConferenceChanged(this, this.mConference);
        }
    }

    private final void clearConferenceableList() {
        for (Conferenceable conferenceable : this.mConferenceables) {
            if (conferenceable instanceof Connection) {
                ((Connection) conferenceable).removeConnectionListener(this.mConnectionDeathListener);
            } else if (conferenceable instanceof Conference) {
                ((Conference) conferenceable).removeListener(this.mConferenceDeathListener);
            }
        }
        this.mConferenceables.clear();
    }

    final void handleExtrasChanged(Bundle bundle) {
        Bundle bundle2;
        synchronized (this.mExtrasLock) {
            this.mExtras = bundle;
            bundle2 = bundle != null ? new Bundle(this.mExtras) : null;
        }
        onExtrasChanged(bundle2);
    }

    public final void notifyConferenceMergeFailed() {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConferenceMergeFailed(this);
        }
    }

    public void notifyPhoneAccountChanged(PhoneAccountHandle phoneAccountHandle) {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onPhoneAccountChanged(this, phoneAccountHandle);
        }
    }

    @SystemApi
    public void setPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle) {
        if (this.mPhoneAccountHandle != phoneAccountHandle) {
            this.mPhoneAccountHandle = phoneAccountHandle;
            notifyPhoneAccountChanged(phoneAccountHandle);
        }
    }

    @SystemApi
    public PhoneAccountHandle getPhoneAccountHandle() {
        return this.mPhoneAccountHandle;
    }

    public void sendConnectionEvent(String str, Bundle bundle) {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConnectionEvent(this, str, bundle);
        }
    }

    @SystemApi
    public final int getCallDirection() {
        return this.mCallDirection;
    }

    @SystemApi
    public void setCallDirection(int i) {
        this.mCallDirection = i;
    }

    public final int getCallerNumberVerificationStatus() {
        return this.mCallerNumberVerificationStatus;
    }

    public final void setCallerNumberVerificationStatus(int i) {
        this.mCallerNumberVerificationStatus = i;
    }
}
