package android.media.session;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.media.IRemoteSessionCallback;
import android.media.MediaCommunicationManager;
import android.media.MediaFrameworkPlatformInitializer;
import android.media.Session2Token;
import android.media.session.IActiveSessionsListener;
import android.media.session.IOnMediaKeyEventDispatchedListener;
import android.media.session.IOnMediaKeyEventSessionChangedListener;
import android.media.session.IOnMediaKeyListener;
import android.media.session.IOnVolumeKeyLongPressListener;
import android.media.session.ISession2TokensListener;
import android.media.session.ISessionManager;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class MediaSessionManager {

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int RESULT_MEDIA_KEY_HANDLED = 1;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int RESULT_MEDIA_KEY_NOT_HANDLED = 0;
    private static final String TAG = "SessionManager";
    private final MediaCommunicationManager mCommunicationManager;
    private Context mContext;
    private MediaSession.Token mCurMediaKeyEventSession;
    private final OnMediaKeyEventDispatchedListenerStub mOnMediaKeyEventDispatchedListenerStub;
    private final OnMediaKeyEventSessionChangedListenerStub mOnMediaKeyEventSessionChangedListenerStub;
    private OnMediaKeyListenerImpl mOnMediaKeyListener;
    private OnVolumeKeyLongPressListenerImpl mOnVolumeKeyLongPressListener;
    private final RemoteSessionCallbackStub mRemoteSessionCallbackStub;
    private final Object mLock = new Object();
    private final ArrayMap<OnActiveSessionsChangedListener, SessionsChangedWrapper> mListeners = new ArrayMap<>();
    private final ArrayMap<OnSession2TokensChangedListener, Session2TokensChangedWrapper> mSession2TokensListeners = new ArrayMap<>();
    private final Map<OnMediaKeyEventDispatchedListener, Executor> mOnMediaKeyEventDispatchedListeners = new HashMap();
    private final Map<OnMediaKeyEventSessionChangedListener, Executor> mMediaKeyEventSessionChangedCallbacks = new HashMap();
    private String mCurMediaKeyEventSessionPackage = "";
    private final Map<RemoteSessionCallback, Executor> mRemoteSessionCallbacks = new ArrayMap();
    private final ISessionManager mService = ISessionManager.Stub.asInterface(MediaFrameworkPlatformInitializer.getMediaServiceManager().getMediaSessionServiceRegisterer().get());

    public interface OnActiveSessionsChangedListener {
        void onActiveSessionsChanged(List<MediaController> list);
    }

    @SystemApi
    public interface OnMediaKeyEventDispatchedListener {
        void onMediaKeyEventDispatched(KeyEvent keyEvent, String str, MediaSession.Token token);
    }

    public interface OnMediaKeyEventSessionChangedListener {
        void onMediaKeyEventSessionChanged(String str, MediaSession.Token token);
    }

    @SystemApi
    public interface OnMediaKeyListener {
        boolean onMediaKey(KeyEvent keyEvent);
    }

    public interface OnSession2TokensChangedListener {
        void onSession2TokensChanged(List<Session2Token> list);
    }

    @SystemApi
    public interface OnVolumeKeyLongPressListener {
        void onVolumeKeyLongPress(KeyEvent keyEvent);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public interface RemoteSessionCallback {
        void onDefaultRemoteSessionChanged(MediaSession.Token token);

        void onVolumeChanged(MediaSession.Token token, int i);
    }

    @Deprecated
    public void notifySession2Created(Session2Token session2Token) {
    }

    public MediaSessionManager(Context context) {
        this.mOnMediaKeyEventDispatchedListenerStub = new OnMediaKeyEventDispatchedListenerStub();
        this.mOnMediaKeyEventSessionChangedListenerStub = new OnMediaKeyEventSessionChangedListenerStub();
        this.mRemoteSessionCallbackStub = new RemoteSessionCallbackStub();
        this.mContext = context;
        this.mCommunicationManager = (MediaCommunicationManager) context.getSystemService(Context.MEDIA_COMMUNICATION_SERVICE);
    }

    public ISession createSession(MediaSession.CallbackStub callbackStub, String str, Bundle bundle) {
        Objects.requireNonNull(callbackStub, "cbStub shouldn't be null");
        Objects.requireNonNull(str, "tag shouldn't be null");
        try {
            return this.mService.createSession(this.mContext.getPackageName(), callbackStub, str, bundle, UserHandle.myUserId());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MediaController> getActiveSessions(ComponentName componentName) {
        return getActiveSessionsForUser(componentName, UserHandle.myUserId());
    }

    public MediaSession.Token getMediaKeyEventSession() {
        try {
            return this.mService.getMediaKeyEventSession(this.mContext.getPackageName());
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get media key event session", e);
            return null;
        }
    }

    public String getMediaKeyEventSessionPackageName() {
        try {
            String mediaKeyEventSessionPackageName = this.mService.getMediaKeyEventSessionPackageName(this.mContext.getPackageName());
            return mediaKeyEventSessionPackageName != null ? mediaKeyEventSessionPackageName : "";
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get media key event session package name", e);
            return "";
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<MediaController> getActiveSessionsForUser(ComponentName componentName, UserHandle userHandle) {
        Objects.requireNonNull(userHandle, "userHandle shouldn't be null");
        return getActiveSessionsForUser(componentName, userHandle.getIdentifier());
    }

    private List<MediaController> getActiveSessionsForUser(ComponentName componentName, int i) {
        ArrayList arrayList = new ArrayList();
        try {
            List<MediaSession.Token> sessions = this.mService.getSessions(componentName, i);
            int size = sessions.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(new MediaController(this.mContext, sessions.get(i2)));
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get active sessions: ", e);
        }
        return arrayList;
    }

    public List<Session2Token> getSession2Tokens() {
        return this.mCommunicationManager.getSession2Tokens();
    }

    public void addOnActiveSessionsChangedListener(OnActiveSessionsChangedListener onActiveSessionsChangedListener, ComponentName componentName) {
        addOnActiveSessionsChangedListener(onActiveSessionsChangedListener, componentName, null);
    }

    public void addOnActiveSessionsChangedListener(OnActiveSessionsChangedListener onActiveSessionsChangedListener, ComponentName componentName, Handler handler) {
        addOnActiveSessionsChangedListener(onActiveSessionsChangedListener, componentName, UserHandle.myUserId(), handler == null ? null : new HandlerExecutor(handler));
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void addOnActiveSessionsChangedListener(ComponentName componentName, UserHandle userHandle, Executor executor, OnActiveSessionsChangedListener onActiveSessionsChangedListener) {
        Objects.requireNonNull(userHandle, "userHandle shouldn't be null");
        Objects.requireNonNull(executor, "executor shouldn't be null");
        addOnActiveSessionsChangedListener(onActiveSessionsChangedListener, componentName, userHandle.getIdentifier(), executor);
    }

    private void addOnActiveSessionsChangedListener(OnActiveSessionsChangedListener onActiveSessionsChangedListener, ComponentName componentName, int i, Executor executor) {
        Objects.requireNonNull(onActiveSessionsChangedListener, "sessionListener shouldn't be null");
        if (executor == null) {
            executor = new HandlerExecutor(new Handler());
        }
        synchronized (this.mLock) {
            if (this.mListeners.get(onActiveSessionsChangedListener) != null) {
                Log.w(TAG, "Attempted to add session listener twice, ignoring.");
                return;
            }
            SessionsChangedWrapper sessionsChangedWrapper = new SessionsChangedWrapper(this.mContext, onActiveSessionsChangedListener, executor);
            try {
                this.mService.addSessionsListener(sessionsChangedWrapper.mStub, componentName, i);
                this.mListeners.put(onActiveSessionsChangedListener, sessionsChangedWrapper);
            } catch (RemoteException e) {
                Log.e(TAG, "Error in addOnActiveSessionsChangedListener.", e);
            }
        }
    }

    public void removeOnActiveSessionsChangedListener(OnActiveSessionsChangedListener onActiveSessionsChangedListener) {
        Objects.requireNonNull(onActiveSessionsChangedListener, "sessionListener shouldn't be null");
        synchronized (this.mLock) {
            SessionsChangedWrapper sessionsChangedWrapperRemove = this.mListeners.remove(onActiveSessionsChangedListener);
            try {
                if (sessionsChangedWrapperRemove != null) {
                    try {
                        this.mService.removeSessionsListener(sessionsChangedWrapperRemove.mStub);
                    } catch (RemoteException e) {
                        Log.e(TAG, "Error in removeOnActiveSessionsChangedListener.", e);
                    }
                }
            } finally {
                sessionsChangedWrapperRemove.release();
            }
        }
    }

    public void addOnSession2TokensChangedListener(OnSession2TokensChangedListener onSession2TokensChangedListener) {
        addOnSession2TokensChangedListener(UserHandle.myUserId(), onSession2TokensChangedListener, new HandlerExecutor(new Handler()));
    }

    public void addOnSession2TokensChangedListener(OnSession2TokensChangedListener onSession2TokensChangedListener, Handler handler) {
        Objects.requireNonNull(handler, "handler shouldn't be null");
        addOnSession2TokensChangedListener(UserHandle.myUserId(), onSession2TokensChangedListener, new HandlerExecutor(handler));
    }

    public void addOnSession2TokensChangedListener(UserHandle userHandle, OnSession2TokensChangedListener onSession2TokensChangedListener, Executor executor) {
        Objects.requireNonNull(userHandle, "userHandle shouldn't be null");
        Objects.requireNonNull(executor, "executor shouldn't be null");
        addOnSession2TokensChangedListener(userHandle.getIdentifier(), onSession2TokensChangedListener, executor);
    }

    private void addOnSession2TokensChangedListener(int i, OnSession2TokensChangedListener onSession2TokensChangedListener, Executor executor) {
        Objects.requireNonNull(onSession2TokensChangedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            if (this.mSession2TokensListeners.get(onSession2TokensChangedListener) != null) {
                Log.w(TAG, "Attempted to add session listener twice, ignoring.");
                return;
            }
            Session2TokensChangedWrapper session2TokensChangedWrapper = new Session2TokensChangedWrapper(onSession2TokensChangedListener, executor);
            try {
                this.mService.addSession2TokensListener(session2TokensChangedWrapper.getStub(), i);
                this.mSession2TokensListeners.put(onSession2TokensChangedListener, session2TokensChangedWrapper);
            } catch (RemoteException e) {
                Log.e(TAG, "Error in addSessionTokensListener.", e);
                e.rethrowFromSystemServer();
            }
        }
    }

    public void removeOnSession2TokensChangedListener(OnSession2TokensChangedListener onSession2TokensChangedListener) {
        Session2TokensChangedWrapper session2TokensChangedWrapperRemove;
        Objects.requireNonNull(onSession2TokensChangedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            session2TokensChangedWrapperRemove = this.mSession2TokensListeners.remove(onSession2TokensChangedListener);
        }
        if (session2TokensChangedWrapperRemove != null) {
            try {
                this.mService.removeSession2TokensListener(session2TokensChangedWrapperRemove.getStub());
            } catch (RemoteException e) {
                Log.e(TAG, "Error in removeSessionTokensListener.", e);
                e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void registerRemoteSessionCallback(Executor executor, RemoteSessionCallback remoteSessionCallback) {
        boolean z;
        Objects.requireNonNull(executor, "executor shouldn't be null");
        Objects.requireNonNull(remoteSessionCallback, "callback shouldn't be null");
        synchronized (this.mLock) {
            int size = this.mRemoteSessionCallbacks.size();
            this.mRemoteSessionCallbacks.put(remoteSessionCallback, executor);
            if (size == 0) {
                z = true;
                if (this.mRemoteSessionCallbacks.size() != 1) {
                    z = false;
                }
            }
        }
        if (z) {
            try {
                this.mService.registerRemoteSessionCallback(this.mRemoteSessionCallbackStub);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to register remote volume controller callback", e);
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void unregisterRemoteSessionCallback(RemoteSessionCallback remoteSessionCallback) {
        boolean z;
        Objects.requireNonNull(remoteSessionCallback, "callback shouldn't be null");
        synchronized (this.mLock) {
            z = this.mRemoteSessionCallbacks.remove(remoteSessionCallback) != null && this.mRemoteSessionCallbacks.size() == 0;
        }
        if (z) {
            try {
                this.mService.unregisterRemoteSessionCallback(this.mRemoteSessionCallbackStub);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to unregister remote volume controller callback", e);
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void dispatchMediaKeyEvent(KeyEvent keyEvent, boolean z) {
        dispatchMediaKeyEventInternal(keyEvent, false, z);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void dispatchMediaKeyEventAsSystemService(KeyEvent keyEvent) {
        dispatchMediaKeyEventInternal(keyEvent, true, true);
    }

    private void dispatchMediaKeyEventInternal(KeyEvent keyEvent, boolean z, boolean z2) {
        Objects.requireNonNull(keyEvent, "keyEvent shouldn't be null");
        try {
            this.mService.dispatchMediaKeyEvent(this.mContext.getPackageName(), z, keyEvent, z2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean dispatchMediaKeyEventToSessionAsSystemService(KeyEvent keyEvent, MediaSession.Token token) {
        Objects.requireNonNull(token, "sessionToken shouldn't be null");
        Objects.requireNonNull(keyEvent, "keyEvent shouldn't be null");
        if (!KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
            return false;
        }
        try {
            return this.mService.dispatchMediaKeyEventToSessionAsSystemService(this.mContext.getPackageName(), keyEvent, token);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to send key event.", e);
            return false;
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void dispatchVolumeKeyEvent(KeyEvent keyEvent, int i, boolean z) {
        dispatchVolumeKeyEventInternal(keyEvent, i, z, false);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void dispatchVolumeKeyEventAsSystemService(KeyEvent keyEvent, int i) {
        dispatchVolumeKeyEventInternal(keyEvent, i, false, true);
    }

    private void dispatchVolumeKeyEventInternal(KeyEvent keyEvent, int i, boolean z, boolean z2) {
        Objects.requireNonNull(keyEvent, "keyEvent shouldn't be null");
        try {
            this.mService.dispatchVolumeKeyEvent(this.mContext.getPackageName(), this.mContext.getOpPackageName(), z2, keyEvent, i, z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to send volume key event.", e);
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void dispatchVolumeKeyEventToSessionAsSystemService(KeyEvent keyEvent, MediaSession.Token token) {
        Objects.requireNonNull(token, "sessionToken shouldn't be null");
        Objects.requireNonNull(keyEvent, "keyEvent shouldn't be null");
        try {
            this.mService.dispatchVolumeKeyEventToSessionAsSystemService(this.mContext.getPackageName(), this.mContext.getOpPackageName(), keyEvent, token);
        } catch (RemoteException e) {
            Log.wtf(TAG, "Error calling dispatchVolumeKeyEventAsSystemService", e);
        }
    }

    public void dispatchAdjustVolume(int i, int i2, int i3) {
        try {
            this.mService.dispatchAdjustVolume(this.mContext.getPackageName(), this.mContext.getOpPackageName(), i, i2, i3);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to send adjust volume.", e);
        }
    }

    public boolean isTrustedForMediaControl(RemoteUserInfo remoteUserInfo) {
        Objects.requireNonNull(remoteUserInfo, "userInfo shouldn't be null");
        if (remoteUserInfo.getPackageName() == null) {
            return false;
        }
        try {
            return this.mService.isTrusted(remoteUserInfo.getPackageName(), remoteUserInfo.getPid(), remoteUserInfo.getUid());
        } catch (RemoteException e) {
            Log.wtf(TAG, "Cannot communicate with the service.", e);
            return false;
        }
    }

    public boolean isGlobalPriorityActive() {
        try {
            return this.mService.isGlobalPriorityActive();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to check if the global priority is active.", e);
            return false;
        }
    }

    @SystemApi
    public void setOnVolumeKeyLongPressListener(OnVolumeKeyLongPressListener onVolumeKeyLongPressListener, Handler handler) {
        synchronized (this.mLock) {
            try {
                try {
                    if (onVolumeKeyLongPressListener == null) {
                        this.mOnVolumeKeyLongPressListener = null;
                        this.mService.setOnVolumeKeyLongPressListener(null);
                    } else {
                        if (handler == null) {
                            handler = new Handler();
                        }
                        OnVolumeKeyLongPressListenerImpl onVolumeKeyLongPressListenerImpl = new OnVolumeKeyLongPressListenerImpl(onVolumeKeyLongPressListener, handler);
                        this.mOnVolumeKeyLongPressListener = onVolumeKeyLongPressListenerImpl;
                        this.mService.setOnVolumeKeyLongPressListener(onVolumeKeyLongPressListenerImpl);
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "Failed to set volume key long press listener", e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @SystemApi
    public void setOnMediaKeyListener(OnMediaKeyListener onMediaKeyListener, Handler handler) {
        synchronized (this.mLock) {
            try {
                try {
                    if (onMediaKeyListener == null) {
                        this.mOnMediaKeyListener = null;
                        this.mService.setOnMediaKeyListener(null);
                    } else {
                        if (handler == null) {
                            handler = new Handler();
                        }
                        OnMediaKeyListenerImpl onMediaKeyListenerImpl = new OnMediaKeyListenerImpl(onMediaKeyListener, handler);
                        this.mOnMediaKeyListener = onMediaKeyListenerImpl;
                        this.mService.setOnMediaKeyListener(onMediaKeyListenerImpl);
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "Failed to set media key listener", e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @SystemApi
    public void addOnMediaKeyEventDispatchedListener(Executor executor, OnMediaKeyEventDispatchedListener onMediaKeyEventDispatchedListener) {
        Objects.requireNonNull(executor, "executor shouldn't be null");
        Objects.requireNonNull(onMediaKeyEventDispatchedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            try {
                this.mOnMediaKeyEventDispatchedListeners.put(onMediaKeyEventDispatchedListener, executor);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to set media key listener", e);
            }
            if (this.mOnMediaKeyEventDispatchedListeners.size() == 1) {
                this.mService.addOnMediaKeyEventDispatchedListener(this.mOnMediaKeyEventDispatchedListenerStub);
            }
        }
    }

    @SystemApi
    public void removeOnMediaKeyEventDispatchedListener(OnMediaKeyEventDispatchedListener onMediaKeyEventDispatchedListener) {
        Objects.requireNonNull(onMediaKeyEventDispatchedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            try {
                this.mOnMediaKeyEventDispatchedListeners.remove(onMediaKeyEventDispatchedListener);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to set media key event dispatched listener", e);
            }
            if (this.mOnMediaKeyEventDispatchedListeners.size() == 0) {
                this.mService.removeOnMediaKeyEventDispatchedListener(this.mOnMediaKeyEventDispatchedListenerStub);
            }
        }
    }

    public void addOnMediaKeyEventSessionChangedListener(Executor executor, final OnMediaKeyEventSessionChangedListener onMediaKeyEventSessionChangedListener) {
        Objects.requireNonNull(executor, "executor shouldn't be null");
        Objects.requireNonNull(onMediaKeyEventSessionChangedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            try {
                if (this.mMediaKeyEventSessionChangedCallbacks.isEmpty()) {
                    this.mService.addOnMediaKeyEventSessionChangedListener(this.mOnMediaKeyEventSessionChangedListenerStub, this.mContext.getPackageName());
                }
                this.mMediaKeyEventSessionChangedCallbacks.put(onMediaKeyEventSessionChangedListener, executor);
                executor.execute(new Runnable() { // from class: android.media.session.MediaSessionManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$addOnMediaKeyEventSessionChangedListener$0(onMediaKeyEventSessionChangedListener);
                    }
                });
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to add MediaKeyEventSessionChangedListener", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOnMediaKeyEventSessionChangedListener$0(OnMediaKeyEventSessionChangedListener onMediaKeyEventSessionChangedListener) {
        onMediaKeyEventSessionChangedListener.onMediaKeyEventSessionChanged(this.mCurMediaKeyEventSessionPackage, this.mCurMediaKeyEventSession);
    }

    public void removeOnMediaKeyEventSessionChangedListener(OnMediaKeyEventSessionChangedListener onMediaKeyEventSessionChangedListener) {
        Objects.requireNonNull(onMediaKeyEventSessionChangedListener, "listener shouldn't be null");
        synchronized (this.mLock) {
            try {
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to remove MediaKeyEventSessionChangedListener", e);
            }
            if (this.mMediaKeyEventSessionChangedCallbacks.remove(onMediaKeyEventSessionChangedListener) != null && this.mMediaKeyEventSessionChangedCallbacks.isEmpty()) {
                this.mService.removeOnMediaKeyEventSessionChangedListener(this.mOnMediaKeyEventSessionChangedListenerStub);
            }
        }
    }

    public void setCustomMediaKeyDispatcher(String str) {
        try {
            this.mService.setCustomMediaKeyDispatcher(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to set custom media key dispatcher name", e);
        }
    }

    public void setCustomMediaSessionPolicyProvider(String str) {
        try {
            this.mService.setCustomMediaSessionPolicyProvider(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to set custom session policy provider name", e);
        }
    }

    public boolean hasCustomMediaKeyDispatcher(String str) {
        Objects.requireNonNull(str, "componentName shouldn't be null");
        try {
            return this.mService.hasCustomMediaKeyDispatcher(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to check if custom media key dispatcher with given component name exists", e);
            return false;
        }
    }

    public boolean hasCustomMediaSessionPolicyProvider(String str) {
        Objects.requireNonNull(str, "componentName shouldn't be null");
        try {
            return this.mService.hasCustomMediaSessionPolicyProvider(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to check if custom media session policy provider with given component name exists", e);
            return false;
        }
    }

    public int getSessionPolicies(MediaSession.Token token) {
        try {
            return this.mService.getSessionPolicies(token);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get session policies", e);
            return 0;
        }
    }

    public void setSessionPolicies(MediaSession.Token token, int i) {
        try {
            this.mService.setSessionPolicies(token, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to set session policies", e);
        }
    }

    public static final class RemoteUserInfo {
        private final String mPackageName;
        private final int mPid;
        private final int mUid;

        public RemoteUserInfo(String str, int i, int i2) {
            this.mPackageName = str;
            this.mPid = i;
            this.mUid = i2;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public int getPid() {
            return this.mPid;
        }

        public int getUid() {
            return this.mUid;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof RemoteUserInfo)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            RemoteUserInfo remoteUserInfo = (RemoteUserInfo) obj;
            return TextUtils.equals(this.mPackageName, remoteUserInfo.mPackageName) && this.mPid == remoteUserInfo.mPid && this.mUid == remoteUserInfo.mUid;
        }

        public int hashCode() {
            return Objects.hash(this.mPackageName, Integer.valueOf(this.mPid), Integer.valueOf(this.mUid));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SessionsChangedWrapper {
        private Context mContext;
        private Executor mExecutor;
        private OnActiveSessionsChangedListener mListener;
        private final IActiveSessionsListener.Stub mStub = new AnonymousClass1();

        public SessionsChangedWrapper(Context context, OnActiveSessionsChangedListener onActiveSessionsChangedListener, Executor executor) {
            this.mContext = context;
            this.mListener = onActiveSessionsChangedListener;
            this.mExecutor = executor;
        }

        /* renamed from: android.media.session.MediaSessionManager$SessionsChangedWrapper$1, reason: invalid class name */
        class AnonymousClass1 extends IActiveSessionsListener.Stub {
            AnonymousClass1() {
            }

            @Override // android.media.session.IActiveSessionsListener
            public void onActiveSessionsChanged(final List<MediaSession.Token> list) {
                if (SessionsChangedWrapper.this.mExecutor != null) {
                    SessionsChangedWrapper.this.mExecutor.execute(new Runnable() { // from class: android.media.session.MediaSessionManager$SessionsChangedWrapper$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onActiveSessionsChanged$0(list);
                        }
                    });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onActiveSessionsChanged$0(List list) {
                SessionsChangedWrapper.this.callOnActiveSessionsChangedListener(list);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void callOnActiveSessionsChangedListener(List<MediaSession.Token> list) {
            Context context = this.mContext;
            if (context != null) {
                ArrayList arrayList = new ArrayList();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    arrayList.add(new MediaController(context, list.get(i)));
                }
                OnActiveSessionsChangedListener onActiveSessionsChangedListener = this.mListener;
                if (onActiveSessionsChangedListener != null) {
                    onActiveSessionsChangedListener.onActiveSessionsChanged(arrayList);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void release() {
            this.mListener = null;
            this.mContext = null;
            this.mExecutor = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Session2TokensChangedWrapper {
        private final Executor mExecutor;
        private final OnSession2TokensChangedListener mListener;
        private final ISession2TokensListener.Stub mStub = new AnonymousClass1();

        /* renamed from: android.media.session.MediaSessionManager$Session2TokensChangedWrapper$1, reason: invalid class name */
        class AnonymousClass1 extends ISession2TokensListener.Stub {
            AnonymousClass1() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onSession2TokensChanged$0(List list) {
                Session2TokensChangedWrapper.this.mListener.onSession2TokensChanged(list);
            }

            @Override // android.media.session.ISession2TokensListener
            public void onSession2TokensChanged(final List<Session2Token> list) {
                Session2TokensChangedWrapper.this.mExecutor.execute(new Runnable() { // from class: android.media.session.MediaSessionManager$Session2TokensChangedWrapper$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSession2TokensChanged$0(list);
                    }
                });
            }
        }

        Session2TokensChangedWrapper(OnSession2TokensChangedListener onSession2TokensChangedListener, Executor executor) {
            this.mListener = onSession2TokensChangedListener;
            this.mExecutor = executor;
        }

        public ISession2TokensListener.Stub getStub() {
            return this.mStub;
        }
    }

    private static final class OnVolumeKeyLongPressListenerImpl extends IOnVolumeKeyLongPressListener.Stub {
        private Handler mHandler;
        private OnVolumeKeyLongPressListener mListener;

        public OnVolumeKeyLongPressListenerImpl(OnVolumeKeyLongPressListener onVolumeKeyLongPressListener, Handler handler) {
            this.mListener = onVolumeKeyLongPressListener;
            this.mHandler = handler;
        }

        @Override // android.media.session.IOnVolumeKeyLongPressListener
        public void onVolumeKeyLongPress(final KeyEvent keyEvent) {
            Handler handler;
            if (this.mListener == null || (handler = this.mHandler) == null) {
                Log.w(MediaSessionManager.TAG, "Failed to call volume key long-press listener. Either mListener or mHandler is null");
            } else {
                handler.post(new Runnable() { // from class: android.media.session.MediaSessionManager.OnVolumeKeyLongPressListenerImpl.1
                    @Override // java.lang.Runnable
                    public void run() {
                        OnVolumeKeyLongPressListenerImpl.this.mListener.onVolumeKeyLongPress(keyEvent);
                    }
                });
            }
        }
    }

    private static final class OnMediaKeyListenerImpl extends IOnMediaKeyListener.Stub {
        private Handler mHandler;
        private OnMediaKeyListener mListener;

        public OnMediaKeyListenerImpl(OnMediaKeyListener onMediaKeyListener, Handler handler) {
            this.mListener = onMediaKeyListener;
            this.mHandler = handler;
        }

        @Override // android.media.session.IOnMediaKeyListener
        public void onMediaKey(final KeyEvent keyEvent, final ResultReceiver resultReceiver) {
            Handler handler;
            if (this.mListener == null || (handler = this.mHandler) == null) {
                Log.w(MediaSessionManager.TAG, "Failed to call media key listener. Either mListener or mHandler is null");
            } else {
                handler.post(new Runnable() { // from class: android.media.session.MediaSessionManager.OnMediaKeyListenerImpl.1
                    @Override // java.lang.Runnable
                    public void run() {
                        boolean zOnMediaKey = OnMediaKeyListenerImpl.this.mListener.onMediaKey(keyEvent);
                        Log.d(MediaSessionManager.TAG, "The media key listener is returned " + zOnMediaKey);
                        ResultReceiver resultReceiver2 = resultReceiver;
                        if (resultReceiver2 != null) {
                            resultReceiver2.send(zOnMediaKey ? 1 : 0, null);
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class OnMediaKeyEventDispatchedListenerStub extends IOnMediaKeyEventDispatchedListener.Stub {
        private OnMediaKeyEventDispatchedListenerStub() {
        }

        @Override // android.media.session.IOnMediaKeyEventDispatchedListener
        public void onMediaKeyEventDispatched(final KeyEvent keyEvent, final String str, final MediaSession.Token token) {
            synchronized (MediaSessionManager.this.mLock) {
                for (final Map.Entry entry : MediaSessionManager.this.mOnMediaKeyEventDispatchedListeners.entrySet()) {
                    ((Executor) entry.getValue()).execute(new Runnable() { // from class: android.media.session.MediaSessionManager$OnMediaKeyEventDispatchedListenerStub$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((MediaSessionManager.OnMediaKeyEventDispatchedListener) entry.getKey()).onMediaKeyEventDispatched(keyEvent, str, token);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class OnMediaKeyEventSessionChangedListenerStub extends IOnMediaKeyEventSessionChangedListener.Stub {
        private OnMediaKeyEventSessionChangedListenerStub() {
        }

        @Override // android.media.session.IOnMediaKeyEventSessionChangedListener
        public void onMediaKeyEventSessionChanged(final String str, final MediaSession.Token token) {
            synchronized (MediaSessionManager.this.mLock) {
                MediaSessionManager.this.mCurMediaKeyEventSessionPackage = str;
                MediaSessionManager.this.mCurMediaKeyEventSession = token;
                for (final Map.Entry entry : MediaSessionManager.this.mMediaKeyEventSessionChangedCallbacks.entrySet()) {
                    ((Executor) entry.getValue()).execute(new Runnable() { // from class: android.media.session.MediaSessionManager$OnMediaKeyEventSessionChangedListenerStub$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((MediaSessionManager.OnMediaKeyEventSessionChangedListener) entry.getKey()).onMediaKeyEventSessionChanged(str, token);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class RemoteSessionCallbackStub extends IRemoteSessionCallback.Stub {
        private RemoteSessionCallbackStub() {
        }

        @Override // android.media.IRemoteSessionCallback
        public void onVolumeChanged(final MediaSession.Token token, final int i) {
            ArrayMap arrayMap = new ArrayMap();
            synchronized (MediaSessionManager.this.mLock) {
                arrayMap.putAll(MediaSessionManager.this.mRemoteSessionCallbacks);
            }
            Iterator it = arrayMap.entrySet().iterator();
            while (it.hasNext()) {
                final Map.Entry entry = (Map.Entry) it.next();
                ((Executor) entry.getValue()).execute(new Runnable() { // from class: android.media.session.MediaSessionManager$RemoteSessionCallbackStub$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((MediaSessionManager.RemoteSessionCallback) entry.getKey()).onVolumeChanged(token, i);
                    }
                });
            }
        }

        @Override // android.media.IRemoteSessionCallback
        public void onSessionChanged(final MediaSession.Token token) {
            ArrayMap arrayMap = new ArrayMap();
            synchronized (MediaSessionManager.this.mLock) {
                arrayMap.putAll(MediaSessionManager.this.mRemoteSessionCallbacks);
            }
            Iterator it = arrayMap.entrySet().iterator();
            while (it.hasNext()) {
                final Map.Entry entry = (Map.Entry) it.next();
                ((Executor) entry.getValue()).execute(new Runnable() { // from class: android.media.session.MediaSessionManager$RemoteSessionCallbackStub$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((MediaSessionManager.RemoteSessionCallback) entry.getKey()).onDefaultRemoteSessionChanged(token);
                    }
                });
            }
        }
    }
}
