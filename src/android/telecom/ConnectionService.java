package android.telecom;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.location.Location;
import android.media.MediaMetrics;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.OutcomeReceiver;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.telecom.Conference;
import android.telecom.Connection;
import android.telecom.Logging.Runnable;
import android.telecom.Logging.Session;
import android.telecom.ParcelableConference;
import android.telephony.ims.ImsCallProfile;
import com.android.internal.os.SomeArgs;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.telecom.IConnectionService;
import com.android.internal.telecom.IConnectionServiceAdapter;
import com.android.internal.telecom.RemoteServiceCallback;
import com.android.internal.telephony.SemRILConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public abstract class ConnectionService extends Service {
    public static final String EXTRA_IS_HANDOVER = "android.telecom.extra.IS_HANDOVER";
    private static final int MSG_ABORT = 3;
    private static final int MSG_ADD_CONNECTION_SERVICE_ADAPTER = 1;
    private static final int MSG_ADD_PARTICIPANT = 39;
    private static final int MSG_ANSWER = 4;
    private static final int MSG_ANSWER_VIDEO = 17;
    private static final int MSG_CONFERENCE = 12;
    private static final int MSG_CONNECTION_SERVICE_FOCUS_GAINED = 31;
    private static final int MSG_CONNECTION_SERVICE_FOCUS_LOST = 30;
    private static final int MSG_CREATE_CONFERENCE = 35;
    private static final int MSG_CREATE_CONFERENCE_COMPLETE = 36;
    private static final int MSG_CREATE_CONFERENCE_FAILED = 37;
    private static final int MSG_CREATE_CONNECTION = 2;
    private static final int MSG_CREATE_CONNECTION_COMPLETE = 29;
    private static final int MSG_CREATE_CONNECTION_FAILED = 25;
    private static final int MSG_DEFLECT = 34;
    private static final int MSG_DISCONNECT = 6;
    private static final int MSG_EXPLICIT_CALL_TRANSFER = 40;
    private static final int MSG_EXPLICIT_CALL_TRANSFER_CONSULTATIVE = 41;
    private static final int MSG_HANDOVER_COMPLETE = 33;
    private static final int MSG_HANDOVER_FAILED = 32;
    private static final int MSG_HOLD = 7;
    private static final int MSG_MERGE_CONFERENCE = 18;
    private static final int MSG_ON_AVAILABLE_CALL_ENDPOINTS_CHANGED = 46;
    private static final int MSG_ON_CALL_AUDIO_STATE_CHANGED = 9;
    private static final int MSG_ON_CALL_ENDPOINT_CHANGED = 45;
    private static final int MSG_ON_CALL_FILTERING_COMPLETED = 42;
    private static final int MSG_ON_EXTRAS_CHANGED = 24;
    private static final int MSG_ON_MUTE_STATE_CHANGED = 47;
    private static final int MSG_ON_POST_DIAL_CONTINUE = 14;
    private static final int MSG_ON_START_RTT = 26;
    private static final int MSG_ON_STOP_RTT = 27;
    private static final int MSG_ON_TRACKED_BY_NON_UI_SERVICE = 44;
    private static final int MSG_ON_USING_ALTERNATIVE_UI = 43;
    private static final int MSG_PLAY_DTMF_TONE = 10;
    private static final int MSG_PULL_EXTERNAL_CALL = 22;
    private static final int MSG_REJECT = 5;
    private static final int MSG_REJECT_WITH_MESSAGE = 20;
    private static final int MSG_REJECT_WITH_REASON = 38;
    private static final int MSG_REMOVE_CONNECTION_SERVICE_ADAPTER = 16;
    private static final int MSG_RTT_UPGRADE_RESPONSE = 28;
    private static final int MSG_SEND_CALL_EVENT = 23;
    private static final int MSG_SILENCE = 21;
    private static final int MSG_SPLIT_FROM_CONFERENCE = 13;
    private static final int MSG_STOP_DTMF_TONE = 11;
    private static final int MSG_SWAP_CONFERENCE = 19;
    private static final int MSG_UNHOLD = 8;
    private static final boolean PII_DEBUG;
    public static final String SERVICE_INTERFACE = "android.telecom.ConnectionService";
    private static final String SESSION_ABORT = "CS.ab";
    private static final String SESSION_ADD_CS_ADAPTER = "CS.aCSA";
    private static final String SESSION_ADD_PARTICIPANT = "CS.aP";
    private static final String SESSION_ANSWER = "CS.an";
    private static final String SESSION_ANSWER_VIDEO = "CS.anV";
    private static final String SESSION_AVAILABLE_CALL_ENDPOINTS_CHANGED = "CS.oACEC";
    private static final String SESSION_CALL_AUDIO_SC = "CS.cASC";
    private static final String SESSION_CALL_ENDPOINT_CHANGED = "CS.oCEC";
    private static final String SESSION_CALL_FILTERING_COMPLETED = "CS.oCFC";
    private static final String SESSION_CONFERENCE = "CS.c";
    private static final String SESSION_CONNECTION_SERVICE_FOCUS_GAINED = "CS.cSFG";
    private static final String SESSION_CONNECTION_SERVICE_FOCUS_LOST = "CS.cSFL";
    private static final String SESSION_CONSULTATIVE_TRANSFER = "CS.cTrans";
    private static final String SESSION_CREATE_CONF = "CS.crConf";
    private static final String SESSION_CREATE_CONF_COMPLETE = "CS.crConfC";
    private static final String SESSION_CREATE_CONF_FAILED = "CS.crConfF";
    private static final String SESSION_CREATE_CONN = "CS.crCo";
    private static final String SESSION_CREATE_CONN_COMPLETE = "CS.crCoC";
    private static final String SESSION_CREATE_CONN_FAILED = "CS.crCoF";
    private static final String SESSION_DEFLECT = "CS.def";
    private static final String SESSION_DISCONNECT = "CS.d";
    private static final String SESSION_EXTRAS_CHANGED = "CS.oEC";
    private static final String SESSION_HANDLER = "H.";
    private static final String SESSION_HANDOVER_COMPLETE = "CS.hC";
    private static final String SESSION_HANDOVER_FAILED = "CS.haF";
    private static final String SESSION_HOLD = "CS.h";
    private static final String SESSION_MERGE_CONFERENCE = "CS.mC";
    private static final String SESSION_MUTE_STATE_CHANGED = "CS.oMSC";
    private static final String SESSION_PLAY_DTMF = "CS.pDT";
    private static final String SESSION_POST_DIAL_CONT = "CS.oPDC";
    private static final String SESSION_PULL_EXTERNAL_CALL = "CS.pEC";
    private static final String SESSION_REJECT = "CS.r";
    private static final String SESSION_REJECT_MESSAGE = "CS.rWM";
    private static final String SESSION_REMOVE_CS_ADAPTER = "CS.rCSA";
    private static final String SESSION_RTT_UPGRADE_RESPONSE = "CS.rTRUR";
    private static final String SESSION_SEND_CALL_EVENT = "CS.sCE";
    private static final String SESSION_SILENCE = "CS.s";
    private static final String SESSION_SPLIT_CONFERENCE = "CS.sFC";
    private static final String SESSION_START_RTT = "CS.+RTT";
    private static final String SESSION_STOP_DTMF = "CS.sDT";
    private static final String SESSION_STOP_RTT = "CS.-RTT";
    private static final String SESSION_SWAP_CONFERENCE = "CS.sC";
    private static final String SESSION_TRACKED_BY_NON_UI_SERVICE = "CS.tBNUS";
    private static final String SESSION_TRANSFER = "CS.trans";
    private static final String SESSION_UNHOLD = "CS.u";
    private static final String SESSION_UPDATE_RTT_PIPES = "CS.uRTT";
    private static final String SESSION_USING_ALTERNATIVE_UI = "CS.uAU";
    private static Connection sNullConnection;
    private Conference sNullConference;
    private final Map<String, Connection> mConnectionById = new ConcurrentHashMap();
    private final Map<Connection, String> mIdByConnection = new ConcurrentHashMap();
    private final Map<String, Conference> mConferenceById = new ConcurrentHashMap();
    private final Map<Conference, String> mIdByConference = new ConcurrentHashMap();
    private final RemoteConnectionManager mRemoteConnectionManager = new RemoteConnectionManager(this);
    private final List<Runnable> mPreInitializationConnectionRequests = new ArrayList();
    private final ConnectionServiceAdapter mAdapter = new ConnectionServiceAdapter();
    private boolean mAreAccountsInitialized = false;
    private Object mIdSyncRoot = new Object();
    private int mId = 0;
    private final IBinder mBinder = new IConnectionService.Stub() { // from class: android.telecom.ConnectionService.1
        @Override // com.android.internal.telecom.IConnectionService
        public void addConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_ADD_CS_ADAPTER);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = iConnectionServiceAdapter;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(1, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void removeConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_REMOVE_CS_ADAPTER);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = iConnectionServiceAdapter;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(16, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnection(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_CREATE_CONN);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = phoneAccountHandle;
                someArgsObtain.arg2 = str;
                someArgsObtain.arg3 = connectionRequest;
                someArgsObtain.arg4 = Log.createSubsession();
                someArgsObtain.argi1 = z ? 1 : 0;
                someArgsObtain.argi2 = z2 ? 1 : 0;
                ConnectionService.this.mHandler.obtainMessage(2, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnectionComplete(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_CREATE_CONN_COMPLETE);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(29, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnectionFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_CREATE_CONN_FAILED);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = connectionRequest;
                someArgsObtain.arg3 = Log.createSubsession();
                someArgsObtain.arg4 = phoneAccountHandle;
                someArgsObtain.argi1 = z ? 1 : 0;
                ConnectionService.this.mHandler.obtainMessage(25, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConference(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_CREATE_CONF);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = phoneAccountHandle;
                someArgsObtain.arg2 = str;
                someArgsObtain.arg3 = connectionRequest;
                someArgsObtain.arg4 = Log.createSubsession();
                someArgsObtain.argi1 = z ? 1 : 0;
                someArgsObtain.argi2 = z2 ? 1 : 0;
                ConnectionService.this.mHandler.obtainMessage(35, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConferenceComplete(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_CREATE_CONF_COMPLETE);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(36, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConferenceFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_CREATE_CONF_FAILED);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = connectionRequest;
                someArgsObtain.arg3 = Log.createSubsession();
                someArgsObtain.arg4 = phoneAccountHandle;
                someArgsObtain.argi1 = z ? 1 : 0;
                ConnectionService.this.mHandler.obtainMessage(37, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void handoverFailed(String str, ConnectionRequest connectionRequest, int i, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_HANDOVER_FAILED);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = connectionRequest;
                someArgsObtain.arg3 = Log.createSubsession();
                someArgsObtain.arg4 = Integer.valueOf(i);
                ConnectionService.this.mHandler.obtainMessage(32, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void handoverComplete(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_HANDOVER_COMPLETE);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(33, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void abort(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_ABORT);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(3, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void answerVideo(String str, int i, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_ANSWER_VIDEO);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                someArgsObtain.argi1 = i;
                ConnectionService.this.mHandler.obtainMessage(17, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void answer(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_ANSWER);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(4, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void deflect(String str, Uri uri, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_DEFLECT);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = uri;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(34, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void reject(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_REJECT);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(5, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void rejectWithReason(String str, int i, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_REJECT);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.argi1 = i;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(38, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void rejectWithMessage(String str, String str2, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_REJECT_MESSAGE);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = str2;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(20, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void transfer(String str, Uri uri, boolean z, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_TRANSFER);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = uri;
                someArgsObtain.argi1 = z ? 1 : 0;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(40, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void consultativeTransfer(String str, String str2, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_CONSULTATIVE_TRANSFER);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = str2;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(41, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void silence(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_SILENCE);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(21, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void disconnect(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_DISCONNECT);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(6, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void hold(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_HOLD);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(7, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void unhold(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_UNHOLD);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(8, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallAudioStateChanged(String str, CallAudioState callAudioState, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_CALL_AUDIO_SC);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = callAudioState;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(9, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallEndpointChanged(String str, CallEndpoint callEndpoint, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_CALL_ENDPOINT_CHANGED);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = callEndpoint;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(45, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onAvailableCallEndpointsChanged(String str, List<CallEndpoint> list, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_AVAILABLE_CALL_ENDPOINTS_CHANGED);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = list;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(46, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onMuteStateChanged(String str, boolean z, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_MUTE_STATE_CHANGED);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Boolean.valueOf(z);
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(47, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onUsingAlternativeUi(String str, boolean z, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_USING_ALTERNATIVE_UI);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Boolean.valueOf(z);
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(43, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onTrackedByNonUiService(String str, boolean z, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_TRACKED_BY_NON_UI_SERVICE);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Boolean.valueOf(z);
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(44, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void playDtmfTone(String str, char c, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_PLAY_DTMF);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = Character.valueOf(c);
                someArgsObtain.arg2 = str;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(10, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void stopDtmfTone(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_STOP_DTMF);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(11, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void conference(String str, String str2, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_CONFERENCE);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = str2;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(12, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void splitFromConference(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_SPLIT_CONFERENCE);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(13, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void mergeConference(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_MERGE_CONFERENCE);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(18, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void swapConference(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_SWAP_CONFERENCE);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(19, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void addConferenceParticipants(String str, List<Uri> list, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_ADD_PARTICIPANT);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = list;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(39, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onPostDialContinue(String str, boolean z, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_POST_DIAL_CONT);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                someArgsObtain.argi1 = z ? 1 : 0;
                ConnectionService.this.mHandler.obtainMessage(14, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void pullExternalCall(String str, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_PULL_EXTERNAL_CALL);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(22, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void sendCallEvent(String str, String str2, Bundle bundle, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_SEND_CALL_EVENT);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = str2;
                someArgsObtain.arg3 = bundle;
                someArgsObtain.arg4 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(23, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallFilteringCompleted(String str, Connection.CallFilteringCompletionInfo callFilteringCompletionInfo, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_CALL_FILTERING_COMPLETED);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = callFilteringCompletionInfo;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(42, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onExtrasChanged(String str, Bundle bundle, Session.Info info) {
            Log.startSession(info, ConnectionService.SESSION_EXTRAS_CHANGED);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = bundle;
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(24, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void startRtt(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
            Log.startSession(info, ConnectionService.SESSION_START_RTT);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = new Connection.RttTextStream(parcelFileDescriptor2, parcelFileDescriptor);
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(26, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void stopRtt(String str, Session.Info info) throws RemoteException {
            Log.startSession(info, ConnectionService.SESSION_STOP_RTT);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                someArgsObtain.arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(27, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void respondToRttUpgradeRequest(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
            Log.startSession(info, ConnectionService.SESSION_RTT_UPGRADE_RESPONSE);
            try {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = str;
                if (parcelFileDescriptor2 == null || parcelFileDescriptor == null) {
                    someArgsObtain.arg2 = null;
                } else {
                    someArgsObtain.arg2 = new Connection.RttTextStream(parcelFileDescriptor2, parcelFileDescriptor);
                }
                someArgsObtain.arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(28, someArgsObtain).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void connectionServiceFocusLost(Session.Info info) throws RemoteException {
            Log.startSession(info, ConnectionService.SESSION_CONNECTION_SERVICE_FOCUS_LOST);
            try {
                ConnectionService.this.mHandler.obtainMessage(30).sendToTarget();
            } finally {
                Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void connectionServiceFocusGained(Session.Info info) throws RemoteException {
            Log.startSession(info, ConnectionService.SESSION_CONNECTION_SERVICE_FOCUS_GAINED);
            try {
                ConnectionService.this.mHandler.obtainMessage(31).sendToTarget();
            } finally {
                Log.endSession();
            }
        }
    };
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: android.telecom.ConnectionService.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            SomeArgs someArgs;
            Object obj = null;
            switch (message.what) {
                case 1:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        IConnectionServiceAdapter iConnectionServiceAdapter = (IConnectionServiceAdapter) someArgs.arg1;
                        Log.continueSession((Session) someArgs.arg2, "H.CS.aCSA");
                        ConnectionService.this.mAdapter.addAdapter(iConnectionServiceAdapter);
                        ConnectionService.this.onAdapterAttached();
                        return;
                    } finally {
                    }
                case 2:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg4, "H.CS.crCo");
                    try {
                        final PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) someArgs.arg1;
                        final String str = (String) someArgs.arg2;
                        final ConnectionRequest connectionRequest = (ConnectionRequest) someArgs.arg3;
                        boolean z = someArgs.argi1 == 1;
                        boolean z2 = someArgs.argi2 == 1;
                        if (!ConnectionService.this.mAreAccountsInitialized) {
                            Log.d(this, "Enqueueing pre-init request %s", str);
                            final boolean z3 = z2;
                            final boolean z4 = z;
                            ConnectionService.this.mPreInitializationConnectionRequests.add(new Runnable("H.CS.crCo.pICR", null) { // from class: android.telecom.ConnectionService.2.1
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    ConnectionService.this.createConnection(phoneAccountHandle, str, connectionRequest, z4, z3);
                                }
                            }.prepare());
                        } else {
                            ConnectionService.this.createConnection(phoneAccountHandle, str, connectionRequest, z, z2);
                        }
                        return;
                    } finally {
                    }
                case 3:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg2, "H.CS.ab");
                    try {
                        ConnectionService.this.abort((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 4:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg2, "H.CS.an");
                    try {
                        ConnectionService.this.answer((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 5:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg2, "H.CS.r");
                    try {
                        ConnectionService.this.reject((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 6:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg2, "H.CS.d");
                    try {
                        ConnectionService.this.disconnect((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 7:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg2, "H.CS.r");
                    try {
                        ConnectionService.this.hold((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 8:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg2, "H.CS.u");
                    try {
                        ConnectionService.this.unhold((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 9:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.cASC");
                    try {
                        ConnectionService.this.onCallAudioStateChanged((String) someArgs.arg1, new CallAudioState((CallAudioState) someArgs.arg2));
                        return;
                    } finally {
                    }
                case 10:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg3, "H.CS.pDT");
                        ConnectionService.this.playDtmfTone((String) someArgs.arg2, ((Character) someArgs.arg1).charValue());
                        return;
                    } finally {
                    }
                case 11:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg2, "H.CS.sDT");
                        ConnectionService.this.stopDtmfTone((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 12:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg3, "H.CS.c");
                        ConnectionService.this.conference((String) someArgs.arg1, (String) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 13:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg2, "H.CS.sFC");
                        ConnectionService.this.splitFromConference((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 14:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg2, "H.CS.oPDC");
                        ConnectionService.this.onPostDialContinue((String) someArgs.arg1, someArgs.argi1 == 1);
                        return;
                    } finally {
                    }
                case 15:
                default:
                    return;
                case 16:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg2, "H.CS.rCSA");
                        ConnectionService.this.mAdapter.removeAdapter((IConnectionServiceAdapter) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 17:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg2, "H.CS.anV");
                    try {
                        ConnectionService.this.answerVideo((String) someArgs.arg1, someArgs.argi1);
                        return;
                    } finally {
                    }
                case 18:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg2, "H.CS.mC");
                        ConnectionService.this.mergeConference((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 19:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg2, "H.CS.sC");
                        ConnectionService.this.swapConference((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 20:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.rWM");
                    try {
                        ConnectionService.this.reject((String) someArgs.arg1, (String) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 21:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg2, "H.CS.s");
                    try {
                        ConnectionService.this.silence((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 22:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg2, "H.CS.pEC");
                        ConnectionService.this.pullExternalCall((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 23:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg4, "H.CS.sCE");
                        ConnectionService.this.sendCallEvent((String) someArgs.arg1, (String) someArgs.arg2, (Bundle) someArgs.arg3);
                        return;
                    } finally {
                    }
                case 24:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg3, "H.CS.oEC");
                        ConnectionService.this.handleExtrasChanged((String) someArgs.arg1, (Bundle) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 25:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.crCoF");
                    try {
                        final String str2 = (String) someArgs.arg1;
                        final ConnectionRequest connectionRequest2 = (ConnectionRequest) someArgs.arg2;
                        final boolean z5 = someArgs.argi1 == 1;
                        final PhoneAccountHandle phoneAccountHandle2 = (PhoneAccountHandle) someArgs.arg4;
                        if (!ConnectionService.this.mAreAccountsInitialized) {
                            Log.d(this, "Enqueueing pre-init request %s", str2);
                            ConnectionService.this.mPreInitializationConnectionRequests.add(new Runnable("H.CS.crCoF.pICR", null) { // from class: android.telecom.ConnectionService.2.3
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    ConnectionService.this.createConnectionFailed(phoneAccountHandle2, str2, connectionRequest2, z5);
                                }
                            }.prepare());
                        } else {
                            Log.i(this, "createConnectionFailed %s", str2);
                            ConnectionService.this.createConnectionFailed(phoneAccountHandle2, str2, connectionRequest2, z5);
                        }
                        return;
                    } finally {
                    }
                case 26:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg3, "H.CS.+RTT");
                        ConnectionService.this.startRtt((String) someArgs.arg1, (Connection.RttTextStream) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 27:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg2, "H.CS.-RTT");
                        ConnectionService.this.stopRtt((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 28:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg3, "H.CS.rTRUR");
                        ConnectionService.this.handleRttUpgradeResponse((String) someArgs.arg1, (Connection.RttTextStream) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 29:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg2, "H.CS.crCoC");
                    try {
                        final String str3 = (String) someArgs.arg1;
                        if (!ConnectionService.this.mAreAccountsInitialized) {
                            Log.d(this, "Enqueueing pre-init request %s", str3);
                            ConnectionService.this.mPreInitializationConnectionRequests.add(new Runnable("H.CS.crCoC.pICR", obj) { // from class: android.telecom.ConnectionService.2.2
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    ConnectionService.this.notifyCreateConnectionComplete(str3);
                                }
                            }.prepare());
                        } else {
                            ConnectionService.this.notifyCreateConnectionComplete(str3);
                        }
                        return;
                    } finally {
                    }
                case 30:
                    ConnectionService.this.onConnectionServiceFocusLost();
                    return;
                case 31:
                    ConnectionService.this.onConnectionServiceFocusGained();
                    return;
                case 32:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.haF");
                    try {
                        final String str4 = (String) someArgs.arg1;
                        final ConnectionRequest connectionRequest3 = (ConnectionRequest) someArgs.arg2;
                        final int iIntValue = ((Integer) someArgs.arg4).intValue();
                        if (!ConnectionService.this.mAreAccountsInitialized) {
                            Log.d(this, "Enqueueing pre-init request %s", str4);
                            ConnectionService.this.mPreInitializationConnectionRequests.add(new Runnable("H.CS.haF.pICR", null) { // from class: android.telecom.ConnectionService.2.7
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    ConnectionService.this.handoverFailed(str4, connectionRequest3, iIntValue);
                                }
                            }.prepare());
                        } else {
                            Log.i(this, "createConnectionFailed %s", str4);
                            ConnectionService.this.handoverFailed(str4, connectionRequest3, iIntValue);
                        }
                        return;
                    } finally {
                    }
                case 33:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg2, "H.CS.hC");
                        ConnectionService.this.notifyHandoverComplete((String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 34:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.def");
                    try {
                        ConnectionService.this.deflect((String) someArgs.arg1, (Uri) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 35:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg4, "H.CS.crCo");
                    try {
                        final PhoneAccountHandle phoneAccountHandle3 = (PhoneAccountHandle) someArgs.arg1;
                        final String str5 = (String) someArgs.arg2;
                        final ConnectionRequest connectionRequest4 = (ConnectionRequest) someArgs.arg3;
                        boolean z6 = someArgs.argi1 == 1;
                        boolean z7 = someArgs.argi2 == 1;
                        if (!ConnectionService.this.mAreAccountsInitialized) {
                            Log.d(this, "Enqueueing pre-initconference request %s", str5);
                            final boolean z8 = z7;
                            final boolean z9 = z6;
                            ConnectionService.this.mPreInitializationConnectionRequests.add(new Runnable("H.CS.crConf.pIConfR", null) { // from class: android.telecom.ConnectionService.2.4
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    ConnectionService.this.createConference(phoneAccountHandle3, str5, connectionRequest4, z9, z8);
                                }
                            }.prepare());
                        } else {
                            ConnectionService.this.createConference(phoneAccountHandle3, str5, connectionRequest4, z6, z7);
                        }
                        return;
                    } finally {
                    }
                case 36:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg2, "H.CS.crCoC");
                    try {
                        final String str6 = (String) someArgs.arg1;
                        if (!ConnectionService.this.mAreAccountsInitialized) {
                            Log.d(this, "Enqueueing pre-init conference request %s", str6);
                            ConnectionService.this.mPreInitializationConnectionRequests.add(new Runnable("H.CS.crConfC.pIConfR", obj) { // from class: android.telecom.ConnectionService.2.5
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    ConnectionService.this.notifyCreateConferenceComplete(str6);
                                }
                            }.prepare());
                        } else {
                            ConnectionService.this.notifyCreateConferenceComplete(str6);
                        }
                        return;
                    } finally {
                    }
                case 37:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.crCoF");
                    try {
                        final String str7 = (String) someArgs.arg1;
                        final ConnectionRequest connectionRequest5 = (ConnectionRequest) someArgs.arg2;
                        final boolean z10 = someArgs.argi1 == 1;
                        final PhoneAccountHandle phoneAccountHandle4 = (PhoneAccountHandle) someArgs.arg4;
                        if (!ConnectionService.this.mAreAccountsInitialized) {
                            Log.d(this, "Enqueueing pre-init conference request %s", str7);
                            ConnectionService.this.mPreInitializationConnectionRequests.add(new Runnable("H.CS.crConfF.pIConfR", null) { // from class: android.telecom.ConnectionService.2.6
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    ConnectionService.this.createConferenceFailed(phoneAccountHandle4, str7, connectionRequest5, z10);
                                }
                            }.prepare());
                        } else {
                            Log.i(this, "createConferenceFailed %s", str7);
                            ConnectionService.this.createConferenceFailed(phoneAccountHandle4, str7, connectionRequest5, z10);
                        }
                        return;
                    } finally {
                    }
                case 38:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg2, "H.CS.r");
                    try {
                        ConnectionService.this.reject((String) someArgs.arg1, someArgs.argi1);
                        return;
                    } finally {
                    }
                case 39:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg3, "H.CS.aP");
                        ConnectionService.this.addConferenceParticipants((String) someArgs.arg1, (List) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 40:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.trans");
                    try {
                        ConnectionService.this.transfer((String) someArgs.arg1, (Uri) someArgs.arg2, someArgs.argi1 == 1);
                        return;
                    } finally {
                    }
                case 41:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.cTrans");
                    try {
                        ConnectionService.this.consultativeTransfer((String) someArgs.arg1, (String) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 42:
                    someArgs = (SomeArgs) message.obj;
                    try {
                        Log.continueSession((Session) someArgs.arg3, "H.CS.oCFC");
                        ConnectionService.this.onCallFilteringCompleted((String) someArgs.arg1, (Connection.CallFilteringCompletionInfo) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 43:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.uAU");
                    try {
                        ConnectionService.this.onUsingAlternativeUi((String) someArgs.arg1, ((Boolean) someArgs.arg2).booleanValue());
                        return;
                    } finally {
                    }
                case 44:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.tBNUS");
                    try {
                        ConnectionService.this.onTrackedByNonUiService((String) someArgs.arg1, ((Boolean) someArgs.arg2).booleanValue());
                        return;
                    } finally {
                    }
                case 45:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.cASC");
                    try {
                        ConnectionService.this.onCallEndpointChanged((String) someArgs.arg1, (CallEndpoint) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 46:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.cASC");
                    try {
                        ConnectionService.this.onAvailableCallEndpointsChanged((String) someArgs.arg1, (List) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 47:
                    someArgs = (SomeArgs) message.obj;
                    Log.continueSession((Session) someArgs.arg3, "H.CS.cASC");
                    try {
                        ConnectionService.this.onMuteStateChanged((String) someArgs.arg1, ((Boolean) someArgs.arg2).booleanValue());
                        return;
                    } finally {
                    }
            }
        }
    };
    private final Conference.Listener mConferenceListener = new Conference.Listener() { // from class: android.telecom.ConnectionService.3
        @Override // android.telecom.Conference.Listener
        public void onConnectionAdded(Conference conference, Connection connection) {
        }

        @Override // android.telecom.Conference.Listener
        public void onConnectionRemoved(Conference conference, Connection connection) {
        }

        @Override // android.telecom.Conference.Listener
        public void onStateChanged(Conference conference, int i, int i2) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            if (i2 == 2) {
                ConnectionService.this.mAdapter.setRinging(str);
                return;
            }
            if (i2 == 3) {
                ConnectionService.this.mAdapter.setDialing(str);
            } else if (i2 == 4) {
                ConnectionService.this.mAdapter.setActive(str);
            } else {
                if (i2 != 5) {
                    return;
                }
                ConnectionService.this.mAdapter.setOnHold(str);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onDisconnected(Conference conference, DisconnectCause disconnectCause) {
            ConnectionService.this.mAdapter.setDisconnected((String) ConnectionService.this.mIdByConference.get(conference), disconnectCause);
        }

        @Override // android.telecom.Conference.Listener
        public void onConferenceableConnectionsChanged(Conference conference, List<Connection> list) {
            ConnectionService.this.mAdapter.setConferenceableConnections((String) ConnectionService.this.mIdByConference.get(conference), ConnectionService.this.createConnectionIdList(list));
        }

        @Override // android.telecom.Conference.Listener
        public void onDestroyed(Conference conference) {
            ConnectionService.this.removeConference(conference);
        }

        @Override // android.telecom.Conference.Listener
        public void onConnectionCapabilitiesChanged(Conference conference, int i) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            Log.d(this, "call capabilities: conference: %s", Connection.capabilitiesToString(i));
            ConnectionService.this.mAdapter.setConnectionCapabilities(str, i);
        }

        @Override // android.telecom.Conference.Listener
        public void onConnectionPropertiesChanged(Conference conference, int i) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            Log.d(this, "call capabilities: conference: %s", Connection.propertiesToString(i));
            ConnectionService.this.mAdapter.setConnectionProperties(str, i);
        }

        @Override // android.telecom.Conference.Listener
        public void onVideoStateChanged(Conference conference, int i) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            Log.d(this, "onVideoStateChanged set video state %d", Integer.valueOf(i));
            ConnectionService.this.mAdapter.setVideoState(str, i);
        }

        @Override // android.telecom.Conference.Listener
        public void onVideoProviderChanged(Conference conference, Connection.VideoProvider videoProvider) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            Log.d(this, "onVideoProviderChanged: Connection: %s, VideoProvider: %s", conference, videoProvider);
            ConnectionService.this.mAdapter.setVideoProvider(str, videoProvider);
        }

        @Override // android.telecom.Conference.Listener
        public void onStatusHintsChanged(Conference conference, StatusHints statusHints) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                ConnectionService.this.mAdapter.setStatusHints(str, statusHints);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onExtrasChanged(Conference conference, Bundle bundle) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                ConnectionService.this.mAdapter.putExtras(str, bundle);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onExtrasRemoved(Conference conference, List<String> list) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                ConnectionService.this.mAdapter.removeExtras(str, list);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onConferenceStateChanged(Conference conference, boolean z) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                ConnectionService.this.mAdapter.setConferenceState(str, z);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onCallDirectionChanged(Conference conference, int i) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                ConnectionService.this.mAdapter.setCallDirection(str, i);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onAddressChanged(Conference conference, Uri uri, int i) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                ConnectionService.this.mAdapter.setAddress(str, uri, i);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onCallerDisplayNameChanged(Conference conference, String str, int i) {
            String str2 = (String) ConnectionService.this.mIdByConference.get(conference);
            if (str2 != null) {
                ConnectionService.this.mAdapter.setCallerDisplayName(str2, str, i);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onConnectionEvent(Conference conference, String str, Bundle bundle) {
            String str2 = (String) ConnectionService.this.mIdByConference.get(conference);
            if (str2 != null) {
                ConnectionService.this.mAdapter.onConnectionEvent(str2, str, bundle);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onRingbackRequested(Conference conference, boolean z) {
            String str = (String) ConnectionService.this.mIdByConference.get(conference);
            Log.d(this, "Adapter conference onRingback %b", Boolean.valueOf(z));
            ConnectionService.this.mAdapter.setRingbackRequested(str, z);
        }
    };
    private final Connection.Listener mConnectionListener = new Connection.Listener() { // from class: android.telecom.ConnectionService.4
        @Override // android.telecom.Connection.Listener
        public void onStateChanged(Connection connection, int i) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "Adapter set state %s %s", str, Connection.stateToString(i));
            if (i == 2) {
                ConnectionService.this.mAdapter.setRinging(str);
                return;
            }
            if (i == 3) {
                ConnectionService.this.mAdapter.setDialing(str);
                return;
            }
            if (i == 4) {
                ConnectionService.this.mAdapter.setActive(str);
            } else if (i == 5) {
                ConnectionService.this.mAdapter.setOnHold(str);
            } else {
                if (i != 7) {
                    return;
                }
                ConnectionService.this.mAdapter.setPulling(str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onDisconnected(Connection connection, DisconnectCause disconnectCause) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "Adapter set disconnected %s", disconnectCause);
            ConnectionService.this.mAdapter.setDisconnected(str, disconnectCause);
        }

        @Override // android.telecom.Connection.Listener
        public void onVideoStateChanged(Connection connection, int i) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "Adapter set video state %d", Integer.valueOf(i));
            ConnectionService.this.mAdapter.setVideoState(str, i);
        }

        @Override // android.telecom.Connection.Listener
        public void onAddressChanged(Connection connection, Uri uri, int i) {
            ConnectionService.this.mAdapter.setAddress((String) ConnectionService.this.mIdByConnection.get(connection), uri, i);
        }

        @Override // android.telecom.Connection.Listener
        public void onCallerDisplayNameChanged(Connection connection, String str, int i) {
            ConnectionService.this.mAdapter.setCallerDisplayName((String) ConnectionService.this.mIdByConnection.get(connection), str, i);
        }

        @Override // android.telecom.Connection.Listener
        public void onDestroyed(Connection connection) {
            ConnectionService.this.removeConnection(connection);
        }

        @Override // android.telecom.Connection.Listener
        public void onPostDialWait(Connection connection, String str) {
            String str2 = (String) ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "Adapter onPostDialWait %s, %s", connection, Log.maskPii(str));
            ConnectionService.this.mAdapter.onPostDialWait(str2, str);
        }

        @Override // android.telecom.Connection.Listener
        public void onPostDialChar(Connection connection, char c) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "Adapter onPostDialChar %s, %s", connection, Log.maskPii(Character.valueOf(c)));
            ConnectionService.this.mAdapter.onPostDialChar(str, c);
        }

        @Override // android.telecom.Connection.Listener
        public void onRingbackRequested(Connection connection, boolean z) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "Adapter onRingback %b", Boolean.valueOf(z));
            ConnectionService.this.mAdapter.setRingbackRequested(str, z);
        }

        @Override // android.telecom.Connection.Listener
        public void onConnectionCapabilitiesChanged(Connection connection, int i) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "capabilities: parcelableconnection: %s", Connection.capabilitiesToString(i));
            ConnectionService.this.mAdapter.setConnectionCapabilities(str, i);
        }

        @Override // android.telecom.Connection.Listener
        public void onConnectionPropertiesChanged(Connection connection, int i) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "properties: parcelableconnection: %s", Connection.propertiesToString(i));
            ConnectionService.this.mAdapter.setConnectionProperties(str, i);
        }

        @Override // android.telecom.Connection.Listener
        public void onVideoProviderChanged(Connection connection, Connection.VideoProvider videoProvider) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "onVideoProviderChanged: Connection: %s, VideoProvider: %s", connection, videoProvider);
            ConnectionService.this.mAdapter.setVideoProvider(str, videoProvider);
        }

        @Override // android.telecom.Connection.Listener
        public void onAudioModeIsVoipChanged(Connection connection, boolean z) {
            ConnectionService.this.mAdapter.setIsVoipAudioMode((String) ConnectionService.this.mIdByConnection.get(connection), z);
        }

        @Override // android.telecom.Connection.Listener
        public void onStatusHintsChanged(Connection connection, StatusHints statusHints) {
            ConnectionService.this.mAdapter.setStatusHints((String) ConnectionService.this.mIdByConnection.get(connection), statusHints);
        }

        @Override // android.telecom.Connection.Listener
        public void onConferenceablesChanged(Connection connection, List<Conferenceable> list) {
            ConnectionService.this.mAdapter.setConferenceableConnections((String) ConnectionService.this.mIdByConnection.get(connection), ConnectionService.this.createIdList(list));
        }

        @Override // android.telecom.Connection.Listener
        public void onConferenceChanged(Connection connection, Conference conference) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                ConnectionService.this.mAdapter.setIsConferenced(str, conference != null ? (String) ConnectionService.this.mIdByConference.get(conference) : null);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onConferenceMergeFailed(Connection connection) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                ConnectionService.this.mAdapter.onConferenceMergeFailed(str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onExtrasChanged(Connection connection, Bundle bundle) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                ConnectionService.this.mAdapter.putExtras(str, bundle);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onExtrasRemoved(Connection connection, List<String> list) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                ConnectionService.this.mAdapter.removeExtras(str, list);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onConnectionEvent(Connection connection, String str, Bundle bundle) {
            String str2 = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str2 != null) {
                ConnectionService.this.mAdapter.onConnectionEvent(str2, str, bundle);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onAudioRouteChanged(Connection connection, int i, String str) {
            String str2 = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str2 != null) {
                ConnectionService.this.mAdapter.setAudioRoute(str2, i, str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onRttInitiationSuccess(Connection connection) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                ConnectionService.this.mAdapter.onRttInitiationSuccess(str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onRttInitiationFailure(Connection connection, int i) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                ConnectionService.this.mAdapter.onRttInitiationFailure(str, i);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onRttSessionRemotelyTerminated(Connection connection) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                ConnectionService.this.mAdapter.onRttSessionRemotelyTerminated(str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onRemoteRttRequest(Connection connection) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                ConnectionService.this.mAdapter.onRemoteRttRequest(str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onPhoneAccountChanged(Connection connection, PhoneAccountHandle phoneAccountHandle) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                ConnectionService.this.mAdapter.onPhoneAccountChanged(str, phoneAccountHandle);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onConnectionTimeReset(Connection connection) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                ConnectionService.this.mAdapter.resetConnectionTime(str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onEndpointChanged(Connection connection, CallEndpoint callEndpoint, Executor executor, OutcomeReceiver<Void, CallEndpointException> outcomeReceiver) {
            String str = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                ConnectionService.this.mAdapter.requestCallEndpointChange(str, callEndpoint, executor, outcomeReceiver);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onQueryLocation(Connection connection, long j, String str, Executor executor, OutcomeReceiver<Location, QueryLocationException> outcomeReceiver) {
            String str2 = (String) ConnectionService.this.mIdByConnection.get(connection);
            if (str2 != null) {
                ConnectionService.this.mAdapter.queryLocation(str2, j, str, executor, outcomeReceiver);
            }
        }
    };

    public void onBindClient(Intent intent) {
    }

    public void onConference(Connection connection, Connection connection2) {
    }

    public void onConferenceAdded(Conference conference) {
    }

    public void onConferenceRemoved(Conference conference) {
    }

    public void onConnectionAdded(Connection connection) {
    }

    public void onConnectionRemoved(Connection connection) {
    }

    public void onConnectionServiceFocusGained() {
    }

    public void onConnectionServiceFocusLost() {
    }

    public void onCreateConferenceComplete(Conference conference) {
    }

    public void onCreateConnectionComplete(Connection connection) {
    }

    public Conference onCreateIncomingConference(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    public void onCreateIncomingConferenceFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
    }

    public Connection onCreateIncomingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    public void onCreateIncomingConnectionFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
    }

    public Connection onCreateIncomingHandoverConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    public Conference onCreateOutgoingConference(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    public void onCreateOutgoingConferenceFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
    }

    public Connection onCreateOutgoingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    public void onCreateOutgoingConnectionFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
    }

    public Connection onCreateOutgoingHandoverConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public Connection onCreateUnknownConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    public void onHandoverFailed(ConnectionRequest connectionRequest, int i) {
    }

    public void onRemoteConferenceAdded(RemoteConference remoteConference) {
    }

    public void onRemoteExistingConnectionAdded(RemoteConnection remoteConnection) {
    }

    public void triggerConferenceRecalculate() {
    }

    static {
        PII_DEBUG = !Log.SHIP_BUILD && Log.isLoggable(3);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        onBindClient(intent);
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        endAllConnections();
        return super.onUnbind(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createConference(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2) {
        Conference conferenceOnCreateOutgoingConference;
        if (z) {
            conferenceOnCreateOutgoingConference = onCreateIncomingConference(phoneAccountHandle, connectionRequest);
        } else {
            conferenceOnCreateOutgoingConference = onCreateOutgoingConference(phoneAccountHandle, connectionRequest);
        }
        Log.d(this, "createConference, conference: %s", conferenceOnCreateOutgoingConference);
        if (conferenceOnCreateOutgoingConference == null) {
            Log.i(this, "createConference, implementation returned null conference.", new Object[0]);
            conferenceOnCreateOutgoingConference = Conference.createFailedConference(new DisconnectCause(1, "IMPL_RETURNED_NULL_CONFERENCE"), connectionRequest.getAccountHandle());
        }
        Bundle extras = connectionRequest.getExtras();
        Bundle bundle = new Bundle();
        bundle.putString(Connection.EXTRA_ORIGINAL_CONNECTION_ID, str);
        if (extras != null && extras.containsKey(Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME)) {
            bundle.putString(Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME, extras.getString(Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME));
            bundle.putParcelable(Connection.EXTRA_REMOTE_PHONE_ACCOUNT_HANDLE, connectionRequest.getAccountHandle());
        }
        conferenceOnCreateOutgoingConference.putExtras(bundle);
        this.mConferenceById.put(str, conferenceOnCreateOutgoingConference);
        this.mIdByConference.put(conferenceOnCreateOutgoingConference, str);
        conferenceOnCreateOutgoingConference.addListener(this.mConferenceListener);
        ParcelableConference parcelableConferenceBuild = new ParcelableConference.Builder(connectionRequest.getAccountHandle(), conferenceOnCreateOutgoingConference.getState()).setConnectionCapabilities(conferenceOnCreateOutgoingConference.getConnectionCapabilities()).setConnectionProperties(conferenceOnCreateOutgoingConference.getConnectionProperties()).setVideoAttributes(conferenceOnCreateOutgoingConference.getVideoProvider() == null ? null : conferenceOnCreateOutgoingConference.getVideoProvider().getInterface(), conferenceOnCreateOutgoingConference.getVideoState()).setConnectTimeMillis(conferenceOnCreateOutgoingConference.getConnectTimeMillis(), conferenceOnCreateOutgoingConference.getConnectionStartElapsedRealtimeMillis()).setStatusHints(conferenceOnCreateOutgoingConference.getStatusHints()).setExtras(conferenceOnCreateOutgoingConference.getExtras()).setAddress(conferenceOnCreateOutgoingConference.getAddress(), conferenceOnCreateOutgoingConference.getAddressPresentation()).setCallerDisplayName(conferenceOnCreateOutgoingConference.getCallerDisplayName(), conferenceOnCreateOutgoingConference.getCallerDisplayNamePresentation()).setDisconnectCause(conferenceOnCreateOutgoingConference.getDisconnectCause()).setRingbackRequested(conferenceOnCreateOutgoingConference.isRingbackRequested()).build();
        if (conferenceOnCreateOutgoingConference.getState() != 6) {
            conferenceOnCreateOutgoingConference.setTelecomCallId(str);
            this.mAdapter.setVideoProvider(str, conferenceOnCreateOutgoingConference.getVideoProvider());
            this.mAdapter.setVideoState(str, conferenceOnCreateOutgoingConference.getVideoState());
            onConferenceAdded(conferenceOnCreateOutgoingConference);
        }
        Log.d(this, "createConference, calling handleCreateConferenceSuccessful %s", str);
        this.mAdapter.handleCreateConferenceComplete(str, connectionRequest, parcelableConferenceBuild);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createConnection(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2) {
        Connection connectionOnCreateOutgoingConnection;
        boolean z3 = connectionRequest.getExtras() != null && connectionRequest.getExtras().getBoolean("android.telecom.extra.IS_HANDOVER", false);
        boolean z4 = connectionRequest.getExtras() != null && connectionRequest.getExtras().getBoolean(TelecomManager.EXTRA_IS_HANDOVER_CONNECTION, false);
        Log.i(this, "createConnection, callManagerAccount: %s, callId: %s, request: %s, isIncoming: %b, isUnknown: %b, isLegacyHandover: %b, isHandover: %b,  addSelfManaged: %b", phoneAccountHandle, str, connectionRequest, Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4), Boolean.valueOf(connectionRequest.getExtras() != null && connectionRequest.getExtras().getBoolean(PhoneAccount.EXTRA_ADD_SELF_MANAGED_CALLS_TO_INCALLSERVICE, true)));
        if (z4) {
            PhoneAccountHandle phoneAccountHandle2 = connectionRequest.getExtras() != null ? (PhoneAccountHandle) connectionRequest.getExtras().getParcelable(TelecomManager.EXTRA_HANDOVER_FROM_PHONE_ACCOUNT, PhoneAccountHandle.class) : null;
            if (!z) {
                connectionOnCreateOutgoingConnection = onCreateOutgoingHandoverConnection(phoneAccountHandle2, connectionRequest);
            } else {
                connectionOnCreateOutgoingConnection = onCreateIncomingHandoverConnection(phoneAccountHandle2, connectionRequest);
            }
        } else if (z2) {
            connectionOnCreateOutgoingConnection = onCreateUnknownConnection(phoneAccountHandle, connectionRequest);
        } else if (z) {
            connectionOnCreateOutgoingConnection = onCreateIncomingConnection(phoneAccountHandle, connectionRequest);
        } else {
            connectionOnCreateOutgoingConnection = onCreateOutgoingConnection(phoneAccountHandle, connectionRequest);
        }
        Log.d(this, "createConnection, connection: %s", connectionOnCreateOutgoingConnection);
        if (connectionOnCreateOutgoingConnection == null) {
            Log.i(this, "createConnection, implementation returned null connection.", new Object[0]);
            connectionOnCreateOutgoingConnection = Connection.createFailedConnection(new DisconnectCause(1, "IMPL_RETURNED_NULL_CONNECTION"));
        } else {
            try {
                Bundle extras = connectionRequest.getExtras();
                if (extras != null && extras.containsKey(Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME, extras.getString(Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME));
                    bundle.putParcelable(Connection.EXTRA_REMOTE_PHONE_ACCOUNT_HANDLE, connectionRequest.getAccountHandle());
                    connectionOnCreateOutgoingConnection.putExtras(bundle);
                }
            } catch (UnsupportedOperationException unused) {
            }
        }
        boolean z5 = (connectionOnCreateOutgoingConnection.getConnectionProperties() & 128) == 128;
        if (z5) {
            connectionOnCreateOutgoingConnection.setAudioModeIsVoip(true);
        }
        connectionOnCreateOutgoingConnection.setTelecomCallId(str);
        PhoneAccountHandle accountHandle = connectionOnCreateOutgoingConnection.getPhoneAccountHandle() == null ? connectionRequest.getAccountHandle() : connectionOnCreateOutgoingConnection.getPhoneAccountHandle();
        if (connectionOnCreateOutgoingConnection.getState() != 6) {
            addConnection(accountHandle, str, connectionOnCreateOutgoingConnection);
        }
        Uri address = connectionOnCreateOutgoingConnection.getAddress();
        Log.v(this, "createConnection, number: %s, state: %s, capabilities: %s, properties: %s", Connection.toLogSafePhoneNumber(address == null ? PerfettoProtoLogImpl.NULL_STRING : address.getSchemeSpecificPart()), Connection.stateToString(connectionOnCreateOutgoingConnection.getState()), Connection.capabilitiesToString(connectionOnCreateOutgoingConnection.getConnectionCapabilities()), Connection.propertiesToString(connectionOnCreateOutgoingConnection.getConnectionProperties()));
        Log.d(this, "createConnection, calling handleCreateConnectionSuccessful %s", str);
        this.mAdapter.handleCreateConnectionComplete(str, connectionRequest, new ParcelableConnection(accountHandle, connectionOnCreateOutgoingConnection.getState(), connectionOnCreateOutgoingConnection.getConnectionCapabilities(), connectionOnCreateOutgoingConnection.getConnectionProperties(), connectionOnCreateOutgoingConnection.getSupportedAudioRoutes(), connectionOnCreateOutgoingConnection.getAddress(), connectionOnCreateOutgoingConnection.getAddressPresentation(), connectionOnCreateOutgoingConnection.getCallerDisplayName(), connectionOnCreateOutgoingConnection.getCallerDisplayNamePresentation(), connectionOnCreateOutgoingConnection.getVideoProvider() != null ? connectionOnCreateOutgoingConnection.getVideoProvider().getInterface() : null, connectionOnCreateOutgoingConnection.getVideoState(), connectionOnCreateOutgoingConnection.isRingbackRequested(), connectionOnCreateOutgoingConnection.getAudioModeIsVoip(), connectionOnCreateOutgoingConnection.getConnectTimeMillis(), connectionOnCreateOutgoingConnection.getConnectionStartElapsedRealtimeMillis(), connectionOnCreateOutgoingConnection.getStatusHints(), connectionOnCreateOutgoingConnection.getDisconnectCause(), createIdList(connectionOnCreateOutgoingConnection.getConferenceables()), connectionOnCreateOutgoingConnection.getExtras(), connectionOnCreateOutgoingConnection.getCallerNumberVerificationStatus()));
        if (z && connectionRequest.shouldShowIncomingCallUi() && z5) {
            connectionOnCreateOutgoingConnection.onShowIncomingCallUi();
        }
        if (z2) {
            triggerConferenceRecalculate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createConnectionFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z) {
        Log.i(this, "createConnectionFailed %s", str);
        if (z) {
            onCreateIncomingConnectionFailed(phoneAccountHandle, connectionRequest);
        } else {
            onCreateOutgoingConnectionFailed(phoneAccountHandle, connectionRequest);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createConferenceFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z) {
        Log.i(this, "createConferenceFailed %s", str);
        if (z) {
            onCreateIncomingConferenceFailed(phoneAccountHandle, connectionRequest);
        } else {
            onCreateOutgoingConferenceFailed(phoneAccountHandle, connectionRequest);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handoverFailed(String str, ConnectionRequest connectionRequest, int i) {
        Log.i(this, "handoverFailed %s", str);
        onHandoverFailed(connectionRequest, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCreateConnectionComplete(String str) {
        Log.i(this, "notifyCreateConnectionComplete %s", str);
        if (str == null) {
            Log.w(this, "notifyCreateConnectionComplete: callId is null.", new Object[0]);
        } else {
            onCreateConnectionComplete(findConnectionForAction(str, "notifyCreateConnectionComplete"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCreateConferenceComplete(String str) {
        Log.i(this, "notifyCreateConferenceComplete %s", str);
        if (str == null) {
            Log.w(this, "notifyCreateConferenceComplete: callId is null.", new Object[0]);
        } else {
            onCreateConferenceComplete(findConferenceForAction(str, "notifyCreateConferenceComplete"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abort(String str) {
        Log.i(this, "abort %s", str);
        findConnectionForAction(str, "abort").onAbort();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void answerVideo(String str, int i) {
        Log.i(this, "answerVideo %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, SemRILConstants.CmcCall.CMC_CALL_SD_ANSWER).onAnswer(i);
        } else {
            findConferenceForAction(str, SemRILConstants.CmcCall.CMC_CALL_SD_ANSWER).onAnswer(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void answer(String str) {
        Log.i(this, "answer %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, SemRILConstants.CmcCall.CMC_CALL_SD_ANSWER).onAnswer();
        } else {
            findConferenceForAction(str, SemRILConstants.CmcCall.CMC_CALL_SD_ANSWER).onAnswer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deflect(String str, Uri uri) {
        Log.i(this, "deflect %s", str);
        findConnectionForAction(str, "deflect").onDeflect(uri);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reject(String str) {
        Log.i(this, "reject %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, SemRILConstants.CmcCall.CMC_CALL_SD_REJECT).onReject();
        } else {
            findConferenceForAction(str, SemRILConstants.CmcCall.CMC_CALL_SD_REJECT).onReject();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reject(String str, String str2) {
        Log.i(this, "reject %s with message", str);
        findConnectionForAction(str, SemRILConstants.CmcCall.CMC_CALL_SD_REJECT).onReject(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reject(String str, int i) {
        Log.i(this, "reject %s with reason %d", str, Integer.valueOf(i));
        findConnectionForAction(str, SemRILConstants.CmcCall.CMC_CALL_SD_REJECT).onReject(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void transfer(String str, Uri uri, boolean z) {
        Log.i(this, "transfer %s", str);
        findConnectionForAction(str, "transfer").onTransfer(uri, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void consultativeTransfer(String str, String str2) {
        Log.i(this, "consultativeTransfer %s", str);
        findConnectionForAction(str, "consultativeTransfer").onTransfer(findConnectionForAction(str2, " consultativeTransfer"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void silence(String str) {
        Log.i(this, "silence %s", str);
        findConnectionForAction(str, "silence").onSilence();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnect(String str) {
        Log.i(this, "disconnect %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, MediaMetrics.Value.DISCONNECT).onDisconnect();
        } else {
            findConferenceForAction(str, MediaMetrics.Value.DISCONNECT).onDisconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hold(String str) {
        Log.i(this, "hold %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, SemRILConstants.CmcCall.CMC_CALL_SD_HOLD).onHold();
        } else {
            findConferenceForAction(str, SemRILConstants.CmcCall.CMC_CALL_SD_HOLD).onHold();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unhold(String str) {
        Log.i(this, "unhold %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "unhold").onUnhold();
        } else {
            findConferenceForAction(str, "unhold").onUnhold();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCallAudioStateChanged(String str, CallAudioState callAudioState) {
        Log.i(this, "onAudioStateChanged %s %s", str, callAudioState);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onCallAudioStateChanged").setCallAudioState(callAudioState);
        } else {
            findConferenceForAction(str, "onCallAudioStateChanged").setCallAudioState(callAudioState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCallEndpointChanged(String str, CallEndpoint callEndpoint) {
        Log.i(this, "onCallEndpointChanged %s %s", str, callEndpoint);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onCallEndpointChanged").setCallEndpoint(callEndpoint);
        } else {
            findConferenceForAction(str, "onCallEndpointChanged").setCallEndpoint(callEndpoint);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAvailableCallEndpointsChanged(String str, List<CallEndpoint> list) {
        Log.i(this, "onAvailableCallEndpointsChanged %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onAvailableCallEndpointsChanged").setAvailableCallEndpoints(list);
        } else {
            findConferenceForAction(str, "onAvailableCallEndpointsChanged").setAvailableCallEndpoints(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMuteStateChanged(String str, boolean z) {
        Log.i(this, "onMuteStateChanged %s %s", str, Boolean.valueOf(z));
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onMuteStateChanged").setMuteState(z);
        } else {
            findConferenceForAction(str, "onMuteStateChanged").setMuteState(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUsingAlternativeUi(String str, boolean z) {
        Log.i(this, "onUsingAlternativeUi %s %s", str, Boolean.valueOf(z));
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onUsingAlternativeUi").onUsingAlternativeUi(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTrackedByNonUiService(String str, boolean z) {
        Log.i(this, "onTrackedByNonUiService %s %s", str, Boolean.valueOf(z));
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onTrackedByNonUiService").onTrackedByNonUiService(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playDtmfTone(String str, char c) {
        Log.i(this, "playDtmfTone %s %s", str, Log.pii(Character.valueOf(c)));
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "playDtmfTone").onPlayDtmfTone(c);
        } else {
            findConferenceForAction(str, "playDtmfTone").onPlayDtmfTone(c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopDtmfTone(String str) {
        Log.i(this, "stopDtmfTone %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "stopDtmfTone").onStopDtmfTone();
        } else {
            findConferenceForAction(str, "stopDtmfTone").onStopDtmfTone();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void conference(String str, String str2) {
        Log.i(this, "conference %s, %s", str, str2);
        Connection connectionFindConnectionForAction = findConnectionForAction(str2, ImsCallProfile.EXTRA_CONFERENCE_DEPRECATED);
        Conference nullConference = getNullConference();
        if (connectionFindConnectionForAction == getNullConnection() && (nullConference = findConferenceForAction(str2, ImsCallProfile.EXTRA_CONFERENCE_DEPRECATED)) == getNullConference()) {
            Log.w(this, "Connection2 or Conference2 missing in conference request %s.", str2);
            return;
        }
        Connection connectionFindConnectionForAction2 = findConnectionForAction(str, ImsCallProfile.EXTRA_CONFERENCE_DEPRECATED);
        if (connectionFindConnectionForAction2 == getNullConnection()) {
            Conference conferenceFindConferenceForAction = findConferenceForAction(str, "addConnection");
            if (conferenceFindConferenceForAction == getNullConference()) {
                Log.w(this, "Connection1 or Conference1 missing in conference request %s.", str);
                return;
            } else if (connectionFindConnectionForAction != getNullConnection()) {
                conferenceFindConferenceForAction.onMerge(connectionFindConnectionForAction);
                return;
            } else {
                Log.wtf(this, "There can only be one conference and an attempt was made to merge two conferences.", new Object[0]);
                return;
            }
        }
        if (nullConference != getNullConference()) {
            nullConference.onMerge(connectionFindConnectionForAction2);
        } else {
            onConference(connectionFindConnectionForAction2, connectionFindConnectionForAction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void splitFromConference(String str) {
        Log.i(this, "splitFromConference(%s)", str);
        Connection connectionFindConnectionForAction = findConnectionForAction(str, "splitFromConference");
        if (connectionFindConnectionForAction == getNullConnection()) {
            Log.w(this, "Connection missing in conference request %s.", str);
            return;
        }
        Conference conference = connectionFindConnectionForAction.getConference();
        if (conference != null) {
            conference.onSeparate(connectionFindConnectionForAction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeConference(String str) {
        Log.i(this, "mergeConference(%s)", str);
        Conference conferenceFindConferenceForAction = findConferenceForAction(str, "mergeConference");
        if (conferenceFindConferenceForAction != null) {
            conferenceFindConferenceForAction.onMerge();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void swapConference(String str) {
        Log.i(this, "swapConference(%s)", str);
        Conference conferenceFindConferenceForAction = findConferenceForAction(str, "swapConference");
        if (conferenceFindConferenceForAction != null) {
            conferenceFindConferenceForAction.onSwap();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addConferenceParticipants(String str, List<Uri> list) {
        Log.i(this, "addConferenceParticipants(%s)", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "addConferenceParticipants").onAddConferenceParticipants(list);
        } else {
            findConferenceForAction(str, "addConferenceParticipants").onAddConferenceParticipants(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pullExternalCall(String str) {
        Log.i(this, "pullExternalCall(%s)", str);
        Connection connectionFindConnectionForAction = findConnectionForAction(str, "pullExternalCall");
        if (connectionFindConnectionForAction != null) {
            connectionFindConnectionForAction.onPullExternalCall();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCallEvent(String str, String str2, Bundle bundle) {
        Log.i(this, "sendCallEvent(%s, %s)", str, str2);
        Connection connectionFindConnectionForAction = findConnectionForAction(str, "sendCallEvent");
        if (connectionFindConnectionForAction != null) {
            connectionFindConnectionForAction.onCallEvent(str2, bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCallFilteringCompleted(String str, Connection.CallFilteringCompletionInfo callFilteringCompletionInfo) {
        Log.i(this, "onCallFilteringCompleted(%s, %s)", str, callFilteringCompletionInfo);
        Connection connectionFindConnectionForAction = findConnectionForAction(str, "onCallFilteringCompleted");
        if (connectionFindConnectionForAction != null) {
            connectionFindConnectionForAction.onCallFilteringCompleted(callFilteringCompletionInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyHandoverComplete(String str) {
        Log.i(this, "notifyHandoverComplete(%s)", str);
        Connection connectionFindConnectionForAction = findConnectionForAction(str, "notifyHandoverComplete");
        if (connectionFindConnectionForAction != null) {
            connectionFindConnectionForAction.onHandoverComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleExtrasChanged(String str, Bundle bundle) {
        Log.i(this, "handleExtrasChanged(%s, %s)", str, Log.maskPii(bundle));
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "handleExtrasChanged").handleExtrasChanged(bundle);
        } else if (this.mConferenceById.containsKey(str)) {
            findConferenceForAction(str, "handleExtrasChanged").handleExtrasChanged(bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRtt(String str, Connection.RttTextStream rttTextStream) {
        Log.i(this, "startRtt(%s)", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "startRtt").onStartRtt(rttTextStream);
        } else if (this.mConferenceById.containsKey(str)) {
            Log.w(this, "startRtt called on a conference.", new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopRtt(String str) {
        Log.i(this, "stopRtt(%s)", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "stopRtt").onStopRtt();
        } else if (this.mConferenceById.containsKey(str)) {
            Log.w(this, "stopRtt called on a conference.", new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRttUpgradeResponse(String str, Connection.RttTextStream rttTextStream) {
        Log.i(this, "handleRttUpgradeResponse(%s, %s)", str, Boolean.valueOf(rttTextStream == null));
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "handleRttUpgradeResponse").handleRttUpgradeResponse(rttTextStream);
        } else if (this.mConferenceById.containsKey(str)) {
            Log.w(this, "handleRttUpgradeResponse called on a conference.", new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPostDialContinue(String str, boolean z) {
        Log.i(this, "onPostDialContinue(%s)", str);
        findConnectionForAction(str, "stopDtmfTone").onPostDialContinue(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAdapterAttached() {
        if (this.mAreAccountsInitialized) {
            return;
        }
        this.mAdapter.queryRemoteConnectionServices(new RemoteServiceCallback.Stub() { // from class: android.telecom.ConnectionService.5
            @Override // com.android.internal.telecom.RemoteServiceCallback
            public void onResult(final List<ComponentName> list, final List<IBinder> list2) {
                ConnectionService.this.mHandler.post(new Runnable("oAA.qRCS.oR", null) { // from class: android.telecom.ConnectionService.5.1
                    @Override // android.telecom.Logging.Runnable
                    public void loggedRun() {
                        for (int i = 0; i < list.size() && i < list2.size(); i++) {
                            ConnectionService.this.mRemoteConnectionManager.addConnectionService((ComponentName) list.get(i), IConnectionService.Stub.asInterface((IBinder) list2.get(i)));
                        }
                        ConnectionService.this.onAccountsInitialized();
                        Log.d(this, "remote connection services found: " + list2, new Object[0]);
                    }
                }.prepare());
            }

            @Override // com.android.internal.telecom.RemoteServiceCallback
            public void onError() {
                ConnectionService.this.mHandler.post(new Runnable("oAA.qRCS.oE", null) { // from class: android.telecom.ConnectionService.5.2
                    @Override // android.telecom.Logging.Runnable
                    public void loggedRun() {
                        ConnectionService.this.mAreAccountsInitialized = true;
                    }
                }.prepare());
            }
        }, getOpPackageName());
    }

    public final RemoteConnection createRemoteIncomingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return this.mRemoteConnectionManager.createRemoteConnection(phoneAccountHandle, connectionRequest, true);
    }

    public final RemoteConnection createRemoteOutgoingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return this.mRemoteConnectionManager.createRemoteConnection(phoneAccountHandle, connectionRequest, false);
    }

    public final RemoteConference createRemoteIncomingConference(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return this.mRemoteConnectionManager.createRemoteConference(phoneAccountHandle, connectionRequest, true);
    }

    public final RemoteConference createRemoteOutgoingConference(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return this.mRemoteConnectionManager.createRemoteConference(phoneAccountHandle, connectionRequest, false);
    }

    public final void conferenceRemoteConnections(RemoteConnection remoteConnection, RemoteConnection remoteConnection2) {
        this.mRemoteConnectionManager.conferenceRemoteConnections(remoteConnection, remoteConnection2);
    }

    public final void addConference(Conference conference) {
        Log.d(this, "addConference: conference=%s", conference);
        String strAddConferenceInternal = addConferenceInternal(conference);
        if (strAddConferenceInternal != null) {
            ArrayList arrayList = new ArrayList(2);
            for (Connection connection : conference.getConnections()) {
                if (this.mIdByConnection.containsKey(connection)) {
                    arrayList.add(this.mIdByConnection.get(connection));
                }
            }
            conference.setTelecomCallId(strAddConferenceInternal);
            this.mAdapter.addConferenceCall(strAddConferenceInternal, new ParcelableConference.Builder(conference.getPhoneAccountHandle(), conference.getState()).setConnectionCapabilities(conference.getConnectionCapabilities()).setConnectionProperties(conference.getConnectionProperties()).setConnectionIds(arrayList).setVideoAttributes(conference.getVideoProvider() == null ? null : conference.getVideoProvider().getInterface(), conference.getVideoState()).setConnectTimeMillis(conference.getConnectTimeMillis(), conference.getConnectionStartElapsedRealtimeMillis()).setStatusHints(conference.getStatusHints()).setExtras(conference.getExtras()).setAddress(conference.getAddress(), conference.getAddressPresentation()).setCallerDisplayName(conference.getCallerDisplayName(), conference.getCallerDisplayNamePresentation()).setDisconnectCause(conference.getDisconnectCause()).setRingbackRequested(conference.isRingbackRequested()).setCallDirection(conference.getCallDirection()).build());
            this.mAdapter.setVideoProvider(strAddConferenceInternal, conference.getVideoProvider());
            this.mAdapter.setVideoState(strAddConferenceInternal, conference.getVideoState());
            if (!conference.isMultiparty()) {
                this.mAdapter.setConferenceState(strAddConferenceInternal, conference.isMultiparty());
            }
            Iterator<Connection> it = conference.getConnections().iterator();
            while (it.hasNext()) {
                String str = this.mIdByConnection.get(it.next());
                if (str != null) {
                    this.mAdapter.setIsConferenced(str, strAddConferenceInternal);
                }
            }
            onConferenceAdded(conference);
        }
    }

    public final void addExistingConnection(PhoneAccountHandle phoneAccountHandle, Connection connection) {
        addExistingConnection(phoneAccountHandle, connection, null);
    }

    public final void connectionServiceFocusReleased() {
        this.mAdapter.onConnectionServiceFocusReleased();
    }

    @SystemApi
    public final void addExistingConnection(PhoneAccountHandle phoneAccountHandle, Connection connection, Conference conference) {
        String strAddExistingConnectionInternal = addExistingConnectionInternal(phoneAccountHandle, connection);
        if (strAddExistingConnectionInternal != null) {
            this.mAdapter.addExistingConnection(strAddExistingConnectionInternal, new ParcelableConnection(phoneAccountHandle, connection.getState(), connection.getConnectionCapabilities(), connection.getConnectionProperties(), connection.getSupportedAudioRoutes(), connection.getAddress(), connection.getAddressPresentation(), connection.getCallerDisplayName(), connection.getCallerDisplayNamePresentation(), connection.getVideoProvider() != null ? connection.getVideoProvider().getInterface() : null, connection.getVideoState(), connection.isRingbackRequested(), connection.getAudioModeIsVoip(), connection.getConnectTimeMillis(), connection.getConnectionStartElapsedRealtimeMillis(), connection.getStatusHints(), connection.getDisconnectCause(), new ArrayList(0), connection.getExtras(), conference != null ? this.mIdByConference.get(conference) : null, connection.getCallDirection(), connection.getCallerNumberVerificationStatus()));
        }
    }

    public final Collection<Connection> getAllConnections() {
        return this.mConnectionById.values();
    }

    public final Collection<Conference> getAllConferences() {
        return this.mConferenceById.values();
    }

    public boolean containsConference(Conference conference) {
        return this.mIdByConference.containsKey(conference);
    }

    void addRemoteConference(RemoteConference remoteConference) {
        onRemoteConferenceAdded(remoteConference);
    }

    void addRemoteExistingConnection(RemoteConnection remoteConnection) {
        onRemoteExistingConnectionAdded(remoteConnection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccountsInitialized() {
        this.mAreAccountsInitialized = true;
        Iterator<Runnable> it = this.mPreInitializationConnectionRequests.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
        this.mPreInitializationConnectionRequests.clear();
    }

    private String addExistingConnectionInternal(PhoneAccountHandle phoneAccountHandle, Connection connection) {
        String string;
        if (connection.getExtras() != null && connection.getExtras().containsKey(Connection.EXTRA_ORIGINAL_CONNECTION_ID)) {
            string = connection.getExtras().getString(Connection.EXTRA_ORIGINAL_CONNECTION_ID);
            Log.d(this, "addExistingConnectionInternal - conn %s reusing original id %s", connection.getTelecomCallId(), string);
        } else if (phoneAccountHandle == null) {
            string = UUID.randomUUID().toString();
        } else {
            string = phoneAccountHandle.getComponentName().getClassName() + "@" + getNextCallId();
        }
        addConnection(phoneAccountHandle, string, connection);
        return string;
    }

    private void addConnection(PhoneAccountHandle phoneAccountHandle, String str, Connection connection) {
        connection.setTelecomCallId(str);
        this.mConnectionById.put(str, connection);
        this.mIdByConnection.put(connection, str);
        connection.addConnectionListener(this.mConnectionListener);
        connection.setConnectionService(this);
        connection.setPhoneAccountHandle(phoneAccountHandle);
        onConnectionAdded(connection);
    }

    protected void removeConnection(Connection connection) {
        connection.unsetConnectionService(this);
        connection.removeConnectionListener(this.mConnectionListener);
        String str = this.mIdByConnection.get(connection);
        if (str != null) {
            this.mConnectionById.remove(str);
            this.mIdByConnection.remove(connection);
            this.mAdapter.removeCall(str);
            onConnectionRemoved(connection);
        }
    }

    private String addConferenceInternal(Conference conference) {
        String string;
        if (conference.getExtras() == null || !conference.getExtras().containsKey(Connection.EXTRA_ORIGINAL_CONNECTION_ID)) {
            string = null;
        } else {
            string = conference.getExtras().getString(Connection.EXTRA_ORIGINAL_CONNECTION_ID);
            Log.d(this, "addConferenceInternal: conf %s reusing original id %s", conference.getTelecomCallId(), string);
        }
        if (this.mIdByConference.containsKey(conference)) {
            Log.w(this, "Re-adding an existing conference: %s.", conference);
        } else if (conference != null) {
            if (string == null) {
                string = UUID.randomUUID().toString();
            }
            this.mConferenceById.put(string, conference);
            this.mIdByConference.put(conference, string);
            conference.addListener(this.mConferenceListener);
            return string;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeConference(Conference conference) {
        if (this.mIdByConference.containsKey(conference)) {
            conference.removeListener(this.mConferenceListener);
            String str = this.mIdByConference.get(conference);
            this.mConferenceById.remove(str);
            this.mIdByConference.remove(conference);
            this.mAdapter.removeCall(str);
            onConferenceRemoved(conference);
        }
    }

    private Connection findConnectionForAction(String str, String str2) {
        if (str != null && this.mConnectionById.containsKey(str)) {
            return this.mConnectionById.get(str);
        }
        Log.w(this, "%s - Cannot find Connection %s", str2, str);
        return getNullConnection();
    }

    static synchronized Connection getNullConnection() {
        if (sNullConnection == null) {
            sNullConnection = new Connection() { // from class: android.telecom.ConnectionService.6
            };
        }
        return sNullConnection;
    }

    private Conference findConferenceForAction(String str, String str2) {
        if (this.mConferenceById.containsKey(str)) {
            return this.mConferenceById.get(str);
        }
        Log.w(this, "%s - Cannot find conference %s", str2, str);
        return getNullConference();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> createConnectionIdList(List<Connection> list) {
        ArrayList arrayList = new ArrayList();
        for (Connection connection : list) {
            if (this.mIdByConnection.containsKey(connection)) {
                arrayList.add(this.mIdByConnection.get(connection));
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> createIdList(List<Conferenceable> list) {
        ArrayList arrayList = new ArrayList();
        for (Conferenceable conferenceable : list) {
            if (conferenceable instanceof Connection) {
                Connection connection = (Connection) conferenceable;
                if (this.mIdByConnection.containsKey(connection)) {
                    arrayList.add(this.mIdByConnection.get(connection));
                }
            } else if (conferenceable instanceof Conference) {
                Conference conference = (Conference) conferenceable;
                if (this.mIdByConference.containsKey(conference)) {
                    arrayList.add(this.mIdByConference.get(conference));
                }
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private Conference getNullConference() {
        if (this.sNullConference == null) {
            this.sNullConference = new Conference(this, null) { // from class: android.telecom.ConnectionService.7
            };
        }
        return this.sNullConference;
    }

    private void endAllConnections() {
        for (Connection connection : this.mIdByConnection.keySet()) {
            if (connection.getConference() == null) {
                connection.onDisconnect();
            }
        }
        Iterator<Conference> it = this.mIdByConference.keySet().iterator();
        while (it.hasNext()) {
            it.next().onDisconnect();
        }
    }

    private int getNextCallId() {
        int i;
        synchronized (this.mIdSyncRoot) {
            i = this.mId + 1;
            this.mId = i;
        }
        return i;
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public void setReadyForTest() {
        this.mAreAccountsInitialized = true;
    }
}
