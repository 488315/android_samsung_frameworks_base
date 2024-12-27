package com.samsung.accessory.manager.authentication;

import android.content.Context;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Slog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.accessory.manager.SAccessoryManager;
import com.samsung.accessory.manager.authentication.msg.MsgHelper;
import com.samsung.accessory.manager.authentication.msg.MsgParser;
import com.samsung.accessory.manager.connectivity.BTConnectivity;
import com.samsung.accessory.manager.connectivity.Connectivity;
import com.samsung.accessory.manager.connectivity.NfcConnectivity;
import com.samsung.accessory.manager.connectivity.UsbConnectivity;
import com.samsung.accessory.manager.connectivity.WirelessChargerConnectivity;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AuthenticationSession extends Thread {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Authenticator mAuthenticator;
    public final Context mContext;
    public HandlerThread mHandlerThread;
    public final AuthenticationResult mResult;
    public EventHandler mSessionHandler;
    public Thread mSessionThread;
    public int mInternalState = -1;
    public int mState = 1;
    public final Object mStateLock = new Object();
    public final AtomicBoolean mConnectAfterEnable = new AtomicBoolean(false);
    public final AtomicBoolean mTeardownRequested = new AtomicBoolean(false);
    public SAccessoryManager.AuthenticationResultCallback mAuthResultCallback = null;
    public SAccessoryManager.AnonymousClass1 mSessionEventListener = null;
    public volatile boolean mTurnedOffWhileRunning = false;
    public final AnonymousClass1 mConnectivityStateCallback = new AnonymousClass1();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.accessory.manager.authentication.AuthenticationSession$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }

        public final void onConnectionStateChanged() {
            AuthenticationSession.this.mSessionHandler.sendEmptyMessage(8);
        }

        public final void onStateChanged(int i) {
            AuthenticationSession authenticationSession = AuthenticationSession.this;
            if (i == 1) {
                authenticationSession.mSessionHandler.sendEmptyMessage(3);
            } else if (i == 2) {
                authenticationSession.mSessionHandler.sendEmptyMessage(4);
            } else if (i == 3) {
                authenticationSession.mSessionHandler.sendEmptyMessage(5);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Connectivity connectivity;
            Connectivity connectivity2;
            Connectivity connectivity3;
            int i = AuthenticationSession.$r8$clinit;
            StringBuilder sb = new StringBuilder("handleMessage : ");
            AuthenticationSession authenticationSession = AuthenticationSession.this;
            int i2 = message.what;
            authenticationSession.getClass();
            sb.append(AuthenticationSession.convertMsg(i2));
            Log.i("SAccessoryManager_AuthenticationSession", sb.toString());
            switch (message.what) {
                case 1:
                    AuthenticationSession authenticationSession2 = AuthenticationSession.this;
                    authenticationSession2.getClass();
                    Log.i("SAccessoryManager_AuthenticationSession", "handleSetConnection");
                    if (authenticationSession2.mAuthenticator != null) {
                        if (authenticationSession2.getSessionState() == 2) {
                            Authenticator authenticator = authenticationSession2.mAuthenticator;
                            if (authenticator.mConnectivity == null) {
                                int i3 = authenticator.mType;
                                if (i3 == 1) {
                                    NfcConnectivity nfcConnectivity = new NfcConnectivity(authenticator.mContext);
                                    nfcConnectivity.mEnableRequest = new AtomicBoolean(false);
                                    authenticator.mConnectivity = nfcConnectivity;
                                } else if (i3 == 2) {
                                    authenticator.mConnectivity = new BTConnectivity(authenticator.mContext);
                                } else if (i3 == 3) {
                                    UsbConnectivity usbConnectivity = new UsbConnectivity(authenticator.mContext);
                                    usbConnectivity.mMsgHelper = new MsgHelper();
                                    authenticator.mConnectivity = usbConnectivity;
                                } else if (i3 == 4) {
                                    authenticator.mConnectivity = new WirelessChargerConnectivity(authenticator.mContext);
                                }
                            }
                            authenticationSession2.mAuthenticator.mConnectivity.setStateChangedCallback(authenticationSession2.mConnectivityStateCallback);
                            authenticationSession2.mAuthenticator.setSessionState(2);
                            if (!authenticationSession2.mAuthenticator.mConnectivity.isEnabled()) {
                                if (!authenticationSession2.mAuthenticator.mConnectivity.enable()) {
                                    Log.e("SAccessoryManager_AuthenticationSession", "enable fail");
                                    authenticationSession2.mAuthenticator.setSessionState(1);
                                    authenticationSession2.setState(1);
                                    authenticationSession2.replayAuthfail(10);
                                    break;
                                } else {
                                    authenticationSession2.mConnectAfterEnable.set(true);
                                    authenticationSession2.mInternalState = 5;
                                    break;
                                }
                            } else {
                                authenticationSession2.mSessionHandler.sendEmptyMessage(7);
                                break;
                            }
                        } else {
                            Log.e("SAccessoryManager_AuthenticationSession", "session is stopped!");
                            break;
                        }
                    } else {
                        Log.e("SAccessoryManager_AuthenticationSession", "mAuthenticator is null");
                        authenticationSession2.replayAuthfail(12);
                        break;
                    }
                case 3:
                    if (!AuthenticationSession.this.mTeardownRequested.get()) {
                        if (AuthenticationSession.this.mConnectAfterEnable.get()) {
                            AuthenticationSession authenticationSession3 = AuthenticationSession.this;
                            authenticationSession3.getClass();
                            Log.i("SAccessoryManager_AuthenticationSession", "connect");
                            Authenticator authenticator2 = authenticationSession3.mAuthenticator;
                            if (authenticator2 != null && (connectivity = authenticator2.mConnectivity) != null) {
                                connectivity.connect();
                            }
                            AuthenticationSession.this.mConnectAfterEnable.set(false);
                            break;
                        }
                    } else {
                        Authenticator authenticator3 = AuthenticationSession.this.mAuthenticator;
                        if (authenticator3 != null && (connectivity2 = authenticator3.mConnectivity) != null && connectivity2.isEnabledInternally() && !AuthenticationSession.this.mAuthenticator.mConnectivity.disable()) {
                            AuthenticationSession.this.replayAuthfail(12);
                            AuthenticationSession.m1131$$Nest$mhandleTearDown(AuthenticationSession.this);
                            break;
                        } else {
                            AuthenticationSession.this.mInternalState = 3;
                            break;
                        }
                    }
                    break;
                case 4:
                    if (!AuthenticationSession.this.mTeardownRequested.get()) {
                        Log.i("SAccessoryManager_AuthenticationSession", "Connectivity is disabled by user");
                        break;
                    } else {
                        AuthenticationSession.this.mTeardownRequested.set(false);
                        AuthenticationSession.this.mSessionHandler.sendEmptyMessage(13);
                        break;
                    }
                case 5:
                    if (AuthenticationSession.this.getSessionState() == 3) {
                        Log.i("SAccessoryManager_AuthenticationSession", "Connectivity is turned off while authentication is running!");
                        AuthenticationSession.this.mTurnedOffWhileRunning = true;
                        break;
                    }
                    break;
                case 7:
                    AuthenticationSession authenticationSession4 = AuthenticationSession.this;
                    authenticationSession4.getClass();
                    Log.i("SAccessoryManager_AuthenticationSession", "connect");
                    Authenticator authenticator4 = authenticationSession4.mAuthenticator;
                    if (authenticator4 != null && (connectivity3 = authenticator4.mConnectivity) != null) {
                        connectivity3.connect();
                        break;
                    }
                    break;
                case 8:
                    AuthenticationSession authenticationSession5 = AuthenticationSession.this;
                    SAccessoryManager.AnonymousClass1 anonymousClass1 = authenticationSession5.mSessionEventListener;
                    if (anonymousClass1 != null) {
                        anonymousClass1.onSessionEvent(2, authenticationSession5);
                    }
                    AuthenticationSession.this.mTurnedOffWhileRunning = false;
                    AuthenticationSession.this.mSessionThread = new Thread(AuthenticationSession.this);
                    AuthenticationSession.this.mSessionThread.start();
                    Authenticator authenticator5 = AuthenticationSession.this.mAuthenticator;
                    if (authenticator5 != null) {
                        authenticator5.setSessionState(3);
                    }
                    AuthenticationSession.this.setState(3);
                    break;
                case 11:
                    AuthenticationSession authenticationSession6 = AuthenticationSession.this;
                    authenticationSession6.getClass();
                    Log.i("SAccessoryManager_AuthenticationSession", "handleStartSession");
                    if (authenticationSession6.getSessionState() == 1) {
                        authenticationSession6.setState(2);
                        SAccessoryManager.AnonymousClass1 anonymousClass12 = authenticationSession6.mSessionEventListener;
                        if (anonymousClass12 != null) {
                            anonymousClass12.onSessionEvent(1, authenticationSession6);
                        }
                        if (authenticationSession6.mAuthenticator == null) {
                            try {
                                authenticationSession6.mAuthenticator = new AuthenticatorClientImpl(authenticationSession6.mContext, authenticationSession6.mResult.mConnectivityType);
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("SAccessoryManager_AuthenticationSession", "authenticator is not null, so initialize interrupt parameter");
                            ((AuthenticatorClientImpl) authenticationSession6.mAuthenticator).isInterrupted = false;
                        }
                        authenticationSession6.mSessionHandler.sendEmptyMessage(1);
                        break;
                    } else {
                        Log.e("SAccessoryManager_AuthenticationSession", "can't start session, state = " + authenticationSession6.mState);
                        authenticationSession6.replayAuthfail(30);
                        break;
                    }
                case 12:
                    AuthenticationSession.m1130$$Nest$mhandleStopSession(AuthenticationSession.this, false);
                    break;
                case 13:
                    AuthenticationSession.m1131$$Nest$mhandleTearDown(AuthenticationSession.this);
                    break;
                case 14:
                    Log.e("SAccessoryManager_AuthenticationSession", "session is timed out!");
                    AuthenticationSession.this.replayAuthfail(31);
                    AuthenticationSession.m1130$$Nest$mhandleStopSession(AuthenticationSession.this, true);
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$mhandleStopSession, reason: not valid java name */
    public static void m1130$$Nest$mhandleStopSession(AuthenticationSession authenticationSession, boolean z) {
        Connectivity connectivity;
        authenticationSession.getClass();
        Log.i("SAccessoryManager_AuthenticationSession", "handleStopSession, force? = " + z);
        if (authenticationSession.mSessionThread != null) {
            Log.i("SAccessoryManager_AuthenticationSession", "waiting for session thread to terminate");
            try {
                Authenticator authenticator = authenticationSession.mAuthenticator;
                if (authenticator != null) {
                    ((AuthenticatorClientImpl) authenticator).isInterrupted = true;
                }
                authenticationSession.mSessionThread.join(3000L);
                if (authenticationSession.mSessionThread.isAlive()) {
                    Log.e("SAccessoryManager_AuthenticationSession", "Thread is still running.. force stop session!");
                    z = true;
                }
                authenticationSession.mSessionThread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("SAccessoryManager_AuthenticationSession", "session thread is terminated");
        }
        if (z) {
            authenticationSession.mSessionHandler.removeMessages(11);
            authenticationSession.mResult.setReason(31);
        } else if (authenticationSession.mSessionHandler.hasMessages(11)) {
            Log.d("SAccessoryManager_AuthenticationSession", "session will be restarted.. ");
            authenticationSession.mInternalState = 1;
            return;
        }
        Authenticator authenticator2 = authenticationSession.mAuthenticator;
        if (authenticator2 != null) {
            authenticator2.setSessionState(4);
        }
        authenticationSession.setState(4);
        Authenticator authenticator3 = authenticationSession.mAuthenticator;
        if (authenticator3 != null && (connectivity = authenticator3.mConnectivity) != null && connectivity.isEnabledInternally()) {
            if (authenticationSession.mAuthenticator.mConnectivity.isEnabled() && authenticationSession.mAuthenticator.mConnectivity.disable()) {
                authenticationSession.mTeardownRequested.set(true);
                if (!z) {
                    Log.i("SAccessoryManager_AuthenticationSession", "waiting for connectivity off");
                    authenticationSession.mInternalState = 2;
                    return;
                }
            } else if (!authenticationSession.mTurnedOffWhileRunning && authenticationSession.mConnectAfterEnable.get()) {
                Log.i("SAccessoryManager_AuthenticationSession", "waiting for connectivity on");
                if (!z) {
                    authenticationSession.mInternalState = 4;
                    authenticationSession.mTeardownRequested.set(true);
                    return;
                }
            }
        }
        authenticationSession.mConnectAfterEnable.set(false);
        authenticationSession.mTeardownRequested.set(false);
        authenticationSession.mSessionHandler.sendEmptyMessage(13);
    }

    /* renamed from: -$$Nest$mhandleTearDown, reason: not valid java name */
    public static void m1131$$Nest$mhandleTearDown(AuthenticationSession authenticationSession) {
        authenticationSession.getClass();
        Log.i("SAccessoryManager_AuthenticationSession", "handleTearDown");
        Authenticator authenticator = authenticationSession.mAuthenticator;
        if (authenticator != null) {
            try {
                Connectivity connectivity = authenticator.mConnectivity;
                if (connectivity != null) {
                    connectivity.sendStopUsbAuth();
                }
                authenticationSession.mAuthenticator.setSessionState(5);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Connectivity connectivity2 = authenticationSession.mAuthenticator.mConnectivity;
            if (connectivity2 != null) {
                connectivity2.close();
            }
        }
        HandlerThread handlerThread = authenticationSession.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.getLooper().quit();
            authenticationSession.mHandlerThread.interrupt();
            authenticationSession.mHandlerThread = null;
        }
        authenticationSession.setState(5);
        SAccessoryManager.AnonymousClass1 anonymousClass1 = authenticationSession.mSessionEventListener;
        if (anonymousClass1 != null) {
            anonymousClass1.onSessionEvent(4, authenticationSession);
        }
        authenticationSession.mSessionHandler.removeMessages(14);
    }

    public AuthenticationSession(Context context, String str, int i) {
        this.mContext = context;
        AuthenticationResult authenticationResult = new AuthenticationResult();
        this.mResult = authenticationResult;
        authenticationResult.mRequestPkg = str;
        authenticationResult.mConnectivityType = i;
        authenticationResult.setReason(90);
    }

    public static String convertMsg(int i) {
        switch (i) {
            case 1:
                return "MSG_CONNECTIVITY_SET_CONNECTION";
            case 2:
                return "MSG_CONNECTIVITY_READY";
            case 3:
                return "MSG_CONNECTIVITY_ON";
            case 4:
                return "MSG_CONNECTIVITY_OFF";
            case 5:
                return "MSG_CONNECTIVITY_TURNING_OFF";
            case 6:
            case 10:
            default:
                return null;
            case 7:
                return "MSG_CONNECTIVITY_CONNECT";
            case 8:
                return "MSG_CONNECTIVITY_CONNECTED";
            case 9:
                return "MSG_CONNECTIVITY_DISCONNECTED";
            case 11:
                return "MSG_SESSION_START";
            case 12:
                return "MSG_SESSION_STOP";
            case 13:
                return "MSG_SESSION_REQUEST_TEAR_DOWN";
            case 14:
                return "MSG_SESSION_TIMEOUT";
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Connectivity connectivity;
        StringBuilder m$1 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, " Current AuthenticationSession state:", "  state = ");
        m$1.append(getSessionState());
        printWriter.println(m$1.toString());
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  internal state = "), this.mInternalState, printWriter, "  mConnectAfterEnable = ");
        m.append(this.mConnectAfterEnable.get());
        printWriter.println(m.toString());
        printWriter.println("  mTeardownRequested = " + this.mTeardownRequested.get());
        Authenticator authenticator = this.mAuthenticator;
        if (authenticator == null || (connectivity = authenticator.mConnectivity) == null) {
            return;
        }
        connectivity.dump(printWriter);
    }

    public final int getSessionState() {
        int i;
        synchronized (this.mStateLock) {
            i = this.mState;
        }
        return i;
    }

    public final void replayAuthfail(int i) {
        this.mResult.setReason(i);
        SAccessoryManager.AnonymousClass1 anonymousClass1 = this.mSessionEventListener;
        if (anonymousClass1 != null) {
            anonymousClass1.onSessionEvent(3, this);
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Connectivity connectivity;
        try {
            startAuthChall();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("SAccessoryManager_AuthenticationSession", "disconnect");
        Authenticator authenticator = this.mAuthenticator;
        if (authenticator != null && (connectivity = authenticator.mConnectivity) != null) {
            connectivity.disconnect();
        }
        Authenticator authenticator2 = this.mAuthenticator;
        if (authenticator2 != null) {
            authenticator2.setSessionState(1);
        }
        setState(1);
        if (this.mTurnedOffWhileRunning) {
            FlashNotificationsController$$ExternalSyntheticOutline0.m("SAccessoryManager_AuthenticationSession", new StringBuilder("mTurnedOffWhileRunning ? "), this.mTurnedOffWhileRunning);
            this.mResult.setReason(12);
        }
        SAccessoryManager.AnonymousClass1 anonymousClass1 = this.mSessionEventListener;
        if (anonymousClass1 != null) {
            anonymousClass1.onSessionEvent(3, this);
        }
    }

    public final void setState(int i) {
        synchronized (this.mStateLock) {
            Log.i("SAccessoryManager_AuthenticationSession", "Session state " + convertMsg(this.mState) + " -> " + convertMsg(i));
            this.mState = i;
        }
    }

    public final void startAuthChall() {
        boolean openNode;
        byte[] sendStartAuth;
        boolean z;
        Authenticator authenticator = this.mAuthenticator;
        if (authenticator == null) {
            return;
        }
        AuthenticationResult authenticationResult = this.mResult;
        AuthenticatorClientImpl authenticatorClientImpl = (AuthenticatorClientImpl) authenticator;
        SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("onAuthenticationChallenge, type ="), authenticatorClientImpl.mType, "SAccessoryManager_AuthenticatorClientImpl");
        int i = authenticatorClientImpl.mType;
        MsgParser msgParser = authenticatorClientImpl.mMsgParser;
        if (i != 1) {
            if (i != 3) {
                if (i == 4) {
                    if (authenticatorClientImpl.mConnectivity == null) {
                        Slog.e("SAccessoryManager_AuthenticatorClientImpl", "onAuthenticationChallenge, mConnection is null!");
                        authenticationResult.setReason(12);
                        return;
                    }
                    msgParser.parseData(1, new byte[]{0, 85, 6, 8, 5, HwConstants.IQ_CONFIG_POS_WIFI_ENABLED, 29, 23, 0, 0, 2, 1, -98, -87, -127, 2}, false);
                    try {
                        openNode = authenticatorClientImpl.mConnectivity.openNode();
                        Slog.i("SAccessoryManager_AuthenticatorClientImpl", "open wirelesscharger: null");
                    } catch (IOException e) {
                        Slog.e("SAccessoryManager_AuthenticatorClientImpl", e.toString());
                        authenticationResult.setReason(14);
                    }
                    if (!openNode) {
                        Slog.e("SAccessoryManager_AuthenticatorClientImpl", "open fail");
                        authenticatorClientImpl.stopAuthentication();
                        return;
                    } else if (!authenticatorClientImpl.sendCommand(authenticationResult, 2)) {
                        authenticatorClientImpl.stopAuthentication();
                        return;
                    } else {
                        if (!authenticatorClientImpl.sendCommand(authenticationResult, 3)) {
                            authenticatorClientImpl.stopAuthentication();
                            return;
                        }
                        Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Success auth, call sendStopAuth()");
                        authenticationResult.setReason(0);
                        authenticatorClientImpl.stopAuthentication();
                        return;
                    }
                }
                return;
            }
            Connectivity connectivity = authenticatorClientImpl.mConnectivity;
            if (connectivity == null) {
                Slog.e("SAccessoryManager_AuthenticatorClientImpl", "onAuthenticationChallenge, mConnection is null!");
                authenticationResult.setReason(12);
                return;
            }
            try {
                authenticationResult.apiState = 1;
                sendStartAuth = connectivity.sendStartAuth(authenticationResult);
                Slog.i("SAccessoryManager_AuthenticatorClientImpl", "response from ccic: " + AuthenticatorClientImpl.byteArrayToString(sendStartAuth));
            } catch (IOException e2) {
                Slog.e("SAccessoryManager_AuthenticatorClientImpl", e2.toString());
                authenticationResult.setReason(14);
            }
            if (sendStartAuth.length == 1) {
                Slog.e("SAccessoryManager_AuthenticatorClientImpl", "need define error code");
                return;
            }
            if (!msgParser.parseData(1, sendStartAuth, false)) {
                authenticationResult.setReason(26);
                Slog.e("SAccessoryManager_AuthenticatorClientImpl", "atqS fail, call sendStopAuth()");
                authenticatorClientImpl.stopAuthentication();
                return;
            }
            if (!authenticatorClientImpl.sendCommand(authenticationResult, 2)) {
                authenticatorClientImpl.stopAuthentication();
                return;
            }
            if (!authenticatorClientImpl.sendCommand(authenticationResult, 3)) {
                authenticatorClientImpl.stopAuthentication();
                return;
            }
            if (!authenticatorClientImpl.sendCommand(authenticationResult, 5)) {
                Slog.e("SAccessoryManager_AuthenticatorClientImpl", "Read id fail.");
                authenticatorClientImpl.stopAuthentication();
                return;
            }
            int i2 = authenticationResult.isUrlExist;
            if (i2 == 1) {
                if (!authenticatorClientImpl.sendCommand(authenticationResult, 9)) {
                    Slog.e("SAccessoryManager_AuthenticatorClientImpl", "Url is not exist.");
                    authenticatorClientImpl.stopAuthentication();
                    return;
                }
            } else if (i2 == 2) {
                if (!authenticatorClientImpl.sendCommand(authenticationResult, 10)) {
                    Slog.e("SAccessoryManager_AuthenticatorClientImpl", "Extra is not exist.");
                    authenticatorClientImpl.stopAuthentication();
                    return;
                }
            } else if (i2 == 3 && !authenticatorClientImpl.sendCommand(authenticationResult, 11)) {
                Slog.e("SAccessoryManager_AuthenticatorClientImpl", "3rd data is not exist.");
                authenticatorClientImpl.stopAuthentication();
                return;
            }
            Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Success auth, call sendStopAuth()");
            authenticatorClientImpl.stopAuthentication();
            authenticationResult.setReason(0);
            return;
        }
        boolean isFactoryBinary = FactoryTest.isFactoryBinary();
        Connectivity connectivity2 = authenticatorClientImpl.mConnectivity;
        if (connectivity2 == null) {
            Slog.e("SAccessoryManager_AuthenticatorClientImpl", "onAuthenticationChallenge, mConnection is null!");
            authenticationResult.setReason(12);
            return;
        }
        try {
            authenticationResult.apiState = 1;
            byte[] sendStartAuth2 = connectivity2.sendStartAuth(authenticationResult);
            if (sendStartAuth2 != null) {
                Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Received atqS Data: " + AuthenticatorClientImpl.byteArrayToString(sendStartAuth2));
                Arrays.equals(Connectivity.NOT_SUPPORT, sendStartAuth2);
                if (sendStartAuth2.length == 1) {
                    byte b = sendStartAuth2[0];
                    if (b == 1) {
                        authenticationResult.setReason(20);
                    } else if (b == -79) {
                        authenticationResult.setReason(21);
                    } else if (b == -78) {
                        authenticationResult.setReason(22);
                    } else if (b == -32) {
                        authenticationResult.setReason(13);
                    } else if (b == -15) {
                        authenticationResult.setReason(40);
                    } else if (b == -14) {
                        authenticationResult.setReason(41);
                    } else if (b == -13) {
                        authenticationResult.setReason(42);
                    } else if (b == -12) {
                        authenticationResult.setReason(43);
                    } else if (b == -11) {
                        authenticationResult.setReason(44);
                    } else if (b == -10) {
                        authenticationResult.setReason(45);
                    } else if (b == -9) {
                        authenticationResult.setReason(46);
                    } else {
                        authenticationResult.setReason(27);
                    }
                    authenticatorClientImpl.sendStopAuth();
                    return;
                }
                if (sendStartAuth2.length != 16) {
                    Slog.e("SAccessoryManager_AuthenticatorClientImpl", "atqS is not correct");
                    authenticationResult.setReason(25);
                    authenticatorClientImpl.sendStopAuth();
                    return;
                }
                z = msgParser.parseData(1, sendStartAuth2, false);
            } else {
                Slog.e("SAccessoryManager_AuthenticatorClientImpl", " atqS is null");
                z = false;
            }
            if (!z) {
                authenticationResult.setReason(26);
                Slog.e("SAccessoryManager_AuthenticatorClientImpl", "atqS fail, call sendStopAuth()");
                authenticatorClientImpl.sendStopAuth();
                return;
            }
            if (!authenticationResult.isKeyChanged) {
                if (!authenticatorClientImpl.sendCommand(authenticationResult, 2)) {
                    authenticatorClientImpl.sendStopAuth();
                    return;
                }
                if (!authenticatorClientImpl.sendCommand(authenticationResult, 3)) {
                    authenticatorClientImpl.sendStopAuth();
                    return;
                }
                if (!authenticatorClientImpl.sendCommand(authenticationResult, 5)) {
                    Slog.e("SAccessoryManager_AuthenticatorClientImpl", "Read id fail.");
                    authenticatorClientImpl.sendStopAuth();
                    return;
                }
                int i3 = authenticationResult.isUrlExist;
                if (i3 == 1) {
                    if (!authenticatorClientImpl.sendCommand(authenticationResult, 9)) {
                        Slog.e("SAccessoryManager_AuthenticatorClientImpl", "Url is not exist.");
                        if (!isFactoryBinary) {
                            authenticatorClientImpl.sendStopAuth();
                            return;
                        }
                    }
                } else if (i3 == 2) {
                    if (!authenticatorClientImpl.sendCommand(authenticationResult, 10)) {
                        Slog.e("SAccessoryManager_AuthenticatorClientImpl", "Extra is not exist.");
                        if (!isFactoryBinary) {
                            authenticatorClientImpl.sendStopAuth();
                            return;
                        }
                    }
                } else if (i3 == 3 && !authenticatorClientImpl.sendCommand(authenticationResult, 11)) {
                    Slog.e("SAccessoryManager_AuthenticatorClientImpl", "3rd data is not exist.");
                    if (!isFactoryBinary) {
                        authenticatorClientImpl.sendStopAuth();
                        return;
                    }
                }
                Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Success auth, call sendStopAuth()");
            }
            if (authenticationResult.isKeyChanged) {
                if (!authenticatorClientImpl.sendCommand(authenticationResult, 8)) {
                    authenticatorClientImpl.sendStopAuth();
                    return;
                }
                if (!authenticatorClientImpl.sendCommand(authenticationResult, 3)) {
                    authenticatorClientImpl.sendStopAuth();
                    return;
                }
                if (!authenticatorClientImpl.sendCommand(authenticationResult, 5)) {
                    Slog.e("SAccessoryManager_AuthenticatorClientImpl", "Read id fail.");
                    authenticatorClientImpl.sendStopAuth();
                    return;
                }
                int i4 = authenticationResult.isUrlExist;
                if (i4 == 1) {
                    if (!authenticatorClientImpl.sendCommand(authenticationResult, 9)) {
                        Slog.e("SAccessoryManager_AuthenticatorClientImpl", "url is not exist.");
                        if (!isFactoryBinary) {
                            authenticatorClientImpl.sendStopAuth();
                            return;
                        }
                    }
                } else if (i4 == 2) {
                    if (!authenticatorClientImpl.sendCommand(authenticationResult, 10)) {
                        Slog.e("SAccessoryManager_AuthenticatorClientImpl", "extra is not exist.");
                        if (!isFactoryBinary) {
                            authenticatorClientImpl.sendStopAuth();
                            return;
                        }
                    }
                } else if (i4 == 3 && !authenticatorClientImpl.sendCommand(authenticationResult, 11)) {
                    Slog.e("SAccessoryManager_AuthenticatorClientImpl", "3rd data is not exist.");
                    if (!isFactoryBinary) {
                        authenticatorClientImpl.sendStopAuth();
                        return;
                    }
                }
                Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Success auth, call sendStopAuth()");
            }
            authenticatorClientImpl.sendStopAuth();
            authenticationResult.setReason(0);
        } catch (IOException e3) {
            e3.printStackTrace();
            authenticationResult.setReason(14);
        }
    }

    public final synchronized void startSession() {
        try {
            Log.i("SAccessoryManager_AuthenticationSession", "startSession");
            if (this.mHandlerThread == null) {
                Log.v("SAccessoryManager_AuthenticationSession", "Create handler thread");
                HandlerThread handlerThread = new HandlerThread("SAAuthentication Session Handler");
                this.mHandlerThread = handlerThread;
                handlerThread.start();
                EventHandler eventHandler = new EventHandler(this.mHandlerThread.getLooper());
                this.mSessionHandler = eventHandler;
                eventHandler.sendEmptyMessageDelayed(14, 60000L);
            }
            this.mSessionHandler.removeMessages(11);
            this.mSessionHandler.removeMessages(12);
            this.mSessionHandler.sendEmptyMessage(11);
        } catch (Throwable th) {
            throw th;
        }
    }
}
