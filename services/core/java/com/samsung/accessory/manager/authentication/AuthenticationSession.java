package com.samsung.accessory.manager.authentication;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.samsung.accessory.manager.SAccessoryManager;
import com.samsung.accessory.manager.connectivity.Connectivity;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class AuthenticationSession extends Thread {
    public static final String TAG = "SAccessoryManager_" + AuthenticationSession.class.getSimpleName();
    public Authenticator mAuthenticator;
    public Context mContext;
    public HandlerThread mHandlerThread;
    public AuthenticationResult mResult;
    public EventHandler mSessionHandler;
    public Thread mSessionThread;
    public int mInternalState = -1;
    public int mSessionState = 0;
    public int current_hall = 0;
    public int mState = 1;
    public Object mStateLock = new Object();
    public AtomicBoolean mConnectAfterEnable = new AtomicBoolean(false);
    public AtomicBoolean mTeardownRequested = new AtomicBoolean(false);
    public SAccessoryManager.AuthenticationResultCallback mAuthResultCallback = null;
    public SessionEventListener mSessionEventListener = null;
    public volatile boolean mTurnedOffWhileRunning = false;
    public final Connectivity.StateChangedCallback mConnectivityStateCallback = new Connectivity.StateChangedCallback() { // from class: com.samsung.accessory.manager.authentication.AuthenticationSession.1
        @Override // com.samsung.accessory.manager.connectivity.Connectivity.StateChangedCallback
        public void onStateChanged(int i) {
            if (i == 1) {
                AuthenticationSession.this.mSessionHandler.sendEmptyMessage(3);
            } else if (i == 2) {
                AuthenticationSession.this.mSessionHandler.sendEmptyMessage(4);
            } else if (i == 3) {
                AuthenticationSession.this.mSessionHandler.sendEmptyMessage(5);
            }
        }

        @Override // com.samsung.accessory.manager.connectivity.Connectivity.StateChangedCallback
        public void onConnectionStateChanged(int i) {
            if (i == 1) {
                AuthenticationSession.this.mSessionHandler.sendEmptyMessage(8);
            } else if (i == 2) {
                AuthenticationSession.this.mSessionHandler.sendEmptyMessage(9);
            }
        }
    };

    public interface SessionEventListener {
        void onSessionEvent(int i, AuthenticationSession authenticationSession);
    }

    public final String convertMsg(int i) {
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

    public void setSessionCallback(SessionEventListener sessionEventListener) {
        this.mSessionEventListener = sessionEventListener;
    }

    public void setAuthenticationResultCallback(SAccessoryManager.AuthenticationResultCallback authenticationResultCallback) {
        this.mAuthResultCallback = authenticationResultCallback;
    }

    public SAccessoryManager.AuthenticationResultCallback getAuthenticationCallback() {
        return this.mAuthResultCallback;
    }

    public int getSessionState() {
        int i;
        synchronized (this.mStateLock) {
            i = this.mState;
        }
        return i;
    }

    public class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.i(AuthenticationSession.TAG, "handleMessage : " + AuthenticationSession.this.convertMsg(message.what));
            switch (message.what) {
                case 1:
                    AuthenticationSession.this.handleSetConnection();
                    break;
                case 3:
                    if (AuthenticationSession.this.mTeardownRequested.get()) {
                        Authenticator authenticator = AuthenticationSession.this.mAuthenticator;
                        if (authenticator != null && authenticator.getConnectivity() != null && AuthenticationSession.this.mAuthenticator.getConnectivity().isEnabledInternally() && !AuthenticationSession.this.mAuthenticator.getConnectivity().disable()) {
                            AuthenticationSession.this.replayAuthfail(12);
                            AuthenticationSession.this.handleTearDown();
                            break;
                        } else {
                            AuthenticationSession.this.mInternalState = 3;
                            break;
                        }
                    } else if (AuthenticationSession.this.mConnectAfterEnable.get()) {
                        AuthenticationSession.this.connect();
                        AuthenticationSession.this.mConnectAfterEnable.set(false);
                        break;
                    }
                    break;
                case 4:
                    if (AuthenticationSession.this.mTeardownRequested.get()) {
                        AuthenticationSession.this.mTeardownRequested.set(false);
                        AuthenticationSession.this.mSessionHandler.sendEmptyMessage(13);
                        break;
                    } else {
                        Log.i(AuthenticationSession.TAG, "Connectivity is disabled by user");
                        break;
                    }
                case 5:
                    if (AuthenticationSession.this.getSessionState() == 3) {
                        Log.i(AuthenticationSession.TAG, "Connectivity is turned off while authentication is running!");
                        AuthenticationSession.this.mTurnedOffWhileRunning = true;
                        break;
                    }
                    break;
                case 7:
                    AuthenticationSession.this.connect();
                    break;
                case 8:
                    if (AuthenticationSession.this.mSessionEventListener != null) {
                        AuthenticationSession.this.mSessionEventListener.onSessionEvent(2, AuthenticationSession.this);
                    }
                    AuthenticationSession.this.mTurnedOffWhileRunning = false;
                    AuthenticationSession.this.mSessionThread = new Thread(AuthenticationSession.this);
                    AuthenticationSession.this.mSessionThread.start();
                    Authenticator authenticator2 = AuthenticationSession.this.mAuthenticator;
                    if (authenticator2 != null) {
                        authenticator2.setSessionState(3);
                    }
                    AuthenticationSession.this.setState(3);
                    break;
                case 11:
                    AuthenticationSession.this.handleStartSession();
                    break;
                case 12:
                    AuthenticationSession.this.handleStopSession(false);
                    break;
                case 13:
                    AuthenticationSession.this.handleTearDown();
                    break;
                case 14:
                    Log.e(AuthenticationSession.TAG, "session is timed out!");
                    AuthenticationSession.this.replayAuthfail(31);
                    AuthenticationSession.this.handleStopSession(true);
                    break;
            }
        }
    }

    public AuthenticationSession(Context context, String str, int i) {
        this.mContext = context;
        AuthenticationResult authenticationResult = new AuthenticationResult();
        this.mResult = authenticationResult;
        authenticationResult.setRequestPackage(str);
        this.mResult.setConnectivityType(i);
        this.mResult.setReason(90);
    }

    public static AuthenticationSession createNewSession(Context context, String str, int i) {
        return new AuthenticationSession(context, str, i);
    }

    public void setCurrentHall(int i) {
        this.current_hall = i;
    }

    public final void setState(int i) {
        synchronized (this.mStateLock) {
            Log.i(TAG, "Session state " + convertMsg(this.mState) + " -> " + convertMsg(i));
            this.mState = i;
        }
    }

    public synchronized void startSession() {
        String str = TAG;
        Log.i(str, "startSession");
        if (this.mHandlerThread == null) {
            Log.v(str, "Create handler thread");
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
    }

    public synchronized void stopSession() {
        Log.i(TAG, "stopSession");
        this.mSessionHandler.removeMessages(11);
        this.mSessionHandler.removeMessages(12);
        this.mSessionHandler.sendEmptyMessage(12);
    }

    public final void handleStopSession(boolean z) {
        String str = TAG;
        Log.i(str, "handleStopSession, force? = " + z);
        if (this.mSessionThread != null) {
            Log.i(str, "waiting for session thread to terminate");
            try {
                Authenticator authenticator = this.mAuthenticator;
                if (authenticator != null) {
                    authenticator.onInterrupted();
                }
                this.mSessionThread.join(3000L);
                if (this.mSessionThread.isAlive()) {
                    Log.e(str, "Thread is still running.. force stop session!");
                    z = true;
                }
                this.mSessionThread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "session thread is terminated");
        }
        if (z) {
            this.mSessionHandler.removeMessages(11);
            this.mResult.setReason(31);
        } else if (this.mSessionHandler.hasMessages(11)) {
            Log.d(TAG, "session will be restarted.. ");
            this.mInternalState = 1;
            return;
        }
        Authenticator authenticator2 = this.mAuthenticator;
        if (authenticator2 != null) {
            authenticator2.setSessionState(4);
        }
        setState(4);
        Authenticator authenticator3 = this.mAuthenticator;
        if (authenticator3 != null && authenticator3.getConnectivity() != null && this.mAuthenticator.getConnectivity().isEnabledInternally()) {
            if (this.mAuthenticator.getConnectivity().isEnabled() && this.mAuthenticator.getConnectivity().disable()) {
                this.mTeardownRequested.set(true);
                if (!z) {
                    Log.i(TAG, "waiting for connectivity off");
                    this.mInternalState = 2;
                    return;
                }
            } else if (!this.mTurnedOffWhileRunning && this.mConnectAfterEnable.get()) {
                Log.i(TAG, "waiting for connectivity on");
                if (!z) {
                    this.mInternalState = 4;
                    this.mTeardownRequested.set(true);
                    return;
                }
            }
        }
        this.mConnectAfterEnable.set(false);
        this.mTeardownRequested.set(false);
        this.mSessionHandler.sendEmptyMessage(13);
    }

    public final void handleStartSession() {
        String str = TAG;
        Log.i(str, "handleStartSession");
        if (getSessionState() != 1) {
            Log.e(str, "can't start session, state = " + this.mState);
            replayAuthfail(30);
            return;
        }
        setState(2);
        SessionEventListener sessionEventListener = this.mSessionEventListener;
        if (sessionEventListener != null) {
            sessionEventListener.onSessionEvent(1, this);
        }
        if (this.mAuthenticator == null) {
            try {
                setAuthenticator(this.mContext, new AuthenticatorClientImpl(this.mContext, this.mResult.getConnectivityType()));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(str, "authenticator is not null, so initialize interrupt parameter");
            this.mAuthenticator.setInterrupt(false);
        }
        this.mSessionHandler.sendEmptyMessage(1);
    }

    public final void handleSetConnection() {
        String str = TAG;
        Log.i(str, "handleSetConnection");
        if (this.mAuthenticator == null) {
            Log.e(str, "mAuthenticator is null");
            replayAuthfail(12);
            return;
        }
        if (getSessionState() != 2) {
            Log.e(str, "session is stopped!");
            return;
        }
        this.mAuthenticator.setConnection();
        this.mAuthenticator.setStateChangedCallback(this.mConnectivityStateCallback);
        this.mAuthenticator.setSessionState(2);
        if (this.mAuthenticator.getConnectivity().isEnabled()) {
            this.mSessionHandler.sendEmptyMessage(7);
            return;
        }
        if (this.mAuthenticator.getConnectivity().enable()) {
            this.mConnectAfterEnable.set(true);
            this.mInternalState = 5;
        } else {
            Log.e(str, "enable fail");
            this.mAuthenticator.setSessionState(1);
            setState(1);
            replayAuthfail(10);
        }
    }

    public final void handleTearDown() {
        Log.i(TAG, "handleTearDown");
        Authenticator authenticator = this.mAuthenticator;
        if (authenticator != null) {
            try {
                authenticator.sendEndCommand();
                this.mAuthenticator.setSessionState(5);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mAuthenticator.close();
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.getLooper().quit();
            this.mHandlerThread.interrupt();
            this.mHandlerThread = null;
        }
        setState(5);
        SessionEventListener sessionEventListener = this.mSessionEventListener;
        if (sessionEventListener != null) {
            sessionEventListener.onSessionEvent(4, this);
        }
        this.mSessionHandler.removeMessages(14);
    }

    public AuthenticationResult getAuthenticationResult() {
        return this.mResult;
    }

    public final void replayAuthfail(int i) {
        this.mResult.setReason(i);
        SessionEventListener sessionEventListener = this.mSessionEventListener;
        if (sessionEventListener != null) {
            sessionEventListener.onSessionEvent(3, this);
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            startAuthChall();
        } catch (IOException e) {
            e.printStackTrace();
        }
        disconnect();
        Authenticator authenticator = this.mAuthenticator;
        if (authenticator != null) {
            authenticator.setSessionState(1);
        }
        setState(1);
        if (this.mTurnedOffWhileRunning) {
            Log.i(TAG, "mTurnedOffWhileRunning ? " + this.mTurnedOffWhileRunning);
            this.mResult.setReason(12);
        }
        SessionEventListener sessionEventListener = this.mSessionEventListener;
        if (sessionEventListener != null) {
            sessionEventListener.onSessionEvent(3, this);
        }
    }

    public final boolean startAuthChall() {
        Authenticator authenticator = this.mAuthenticator;
        if (authenticator == null) {
            return false;
        }
        authenticator.onAuthenticationChallenge(this.mResult);
        return true;
    }

    public final void setAuthenticator(Context context, Authenticator authenticator) {
        if (authenticator == null) {
            throw new IllegalArgumentException("Authenticator must not be null");
        }
        this.mAuthenticator = authenticator;
    }

    public final void connect() {
        Log.i(TAG, "connect");
        Authenticator authenticator = this.mAuthenticator;
        if (authenticator == null) {
            return;
        }
        authenticator.connect();
    }

    public final void disconnect() {
        Log.i(TAG, "disconnect");
        Authenticator authenticator = this.mAuthenticator;
        if (authenticator == null) {
            return;
        }
        authenticator.disconnect();
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(" Current AuthenticationSession state:");
        printWriter.println("  state = " + getSessionState());
        printWriter.println("  internal state = " + this.mInternalState);
        printWriter.println("  mConnectAfterEnable = " + this.mConnectAfterEnable.get());
        printWriter.println("  mTeardownRequested = " + this.mTeardownRequested.get());
        Authenticator authenticator = this.mAuthenticator;
        if (authenticator == null || authenticator.getConnectivity() == null) {
            return;
        }
        this.mAuthenticator.getConnectivity().dump(fileDescriptor, printWriter, strArr);
    }
}
