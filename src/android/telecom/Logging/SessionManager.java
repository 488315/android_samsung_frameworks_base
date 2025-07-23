package android.telecom.Logging;

import android.content.ContentResolver;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.provider.Settings;
import android.telecom.Log;
import android.telecom.Logging.Session;
import android.util.Base64;
import com.android.server.telecom.flags.Flags;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class SessionManager {
    private static final long DEFAULT_SESSION_TIMEOUT_MS = 30000;
    private static final String LOGGING_TAG = "Logging";
    private static final long SESSION_ID_ROLLOVER_THRESHOLD = 262144;
    private static final String TIMEOUTS_PREFIX = "telecom.";
    private final java.lang.Runnable mCleanStaleSessions;
    private Context mContext;
    public ICurrentThreadId mCurrentThreadId;
    private final Handler mSessionCleanupHandler;
    private ISessionCleanupTimeoutMs mSessionCleanupTimeoutMs;
    private final List<ISessionListener> mSessionListeners;
    public final ConcurrentHashMap<Integer, Session> mSessionMapper;
    private int sCodeEntryCounter;

    public interface ICurrentThreadId {
        int get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface ISessionCleanupTimeoutMs {
        long get();
    }

    public interface ISessionIdQueryHandler {
        String getSessionId();
    }

    public interface ISessionListener {
        void sessionComplete(String str, long j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ long lambda$new$0() {
        Context context = this.mContext;
        if (context == null) {
            return 30000L;
        }
        return getCleanupTimeout(context);
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public SessionManager() {
        this.sCodeEntryCounter = 0;
        this.mSessionMapper = new ConcurrentHashMap<>(64);
        this.mSessionCleanupHandler = new Handler(Looper.getMainLooper());
        this.mCurrentThreadId = new ICurrentThreadId() { // from class: android.telecom.Logging.SessionManager$$ExternalSyntheticLambda0
            @Override // android.telecom.Logging.SessionManager.ICurrentThreadId
            public final int get() {
                return Process.myTid();
            }
        };
        this.mSessionCleanupTimeoutMs = new ISessionCleanupTimeoutMs() { // from class: android.telecom.Logging.SessionManager$$ExternalSyntheticLambda1
            @Override // android.telecom.Logging.SessionManager.ISessionCleanupTimeoutMs
            public final long get() {
                long lambda$new$0;
                lambda$new$0 = SessionManager.this.lambda$new$0();
                return lambda$new$0;
            }
        };
        this.mSessionListeners = new ArrayList();
        this.mCleanStaleSessions = new java.lang.Runnable() { // from class: android.telecom.Logging.SessionManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SessionManager.this.lambda$new$1();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        cleanupStaleSessions(getSessionCleanupTimeoutMs());
    }

    public SessionManager(java.lang.Runnable runnable) {
        this.sCodeEntryCounter = 0;
        this.mSessionMapper = new ConcurrentHashMap<>(64);
        this.mSessionCleanupHandler = new Handler(Looper.getMainLooper());
        this.mCurrentThreadId = new ICurrentThreadId() { // from class: android.telecom.Logging.SessionManager$$ExternalSyntheticLambda0
            @Override // android.telecom.Logging.SessionManager.ICurrentThreadId
            public final int get() {
                return Process.myTid();
            }
        };
        this.mSessionCleanupTimeoutMs = new ISessionCleanupTimeoutMs() { // from class: android.telecom.Logging.SessionManager$$ExternalSyntheticLambda1
            @Override // android.telecom.Logging.SessionManager.ISessionCleanupTimeoutMs
            public final long get() {
                long lambda$new$0;
                lambda$new$0 = SessionManager.this.lambda$new$0();
                return lambda$new$0;
            }
        };
        this.mSessionListeners = new ArrayList();
        this.mCleanStaleSessions = runnable;
    }

    private long getSessionCleanupTimeoutMs() {
        return this.mSessionCleanupTimeoutMs.get();
    }

    private void resetStaleSessionTimer() {
        if (!Flags.endSessionImprovements()) {
            resetStaleSessionTimerOld();
        } else {
            if (this.mCleanStaleSessions == null) {
                return;
            }
            synchronized (this.mSessionCleanupHandler) {
                if (!this.mSessionCleanupHandler.hasCallbacks(this.mCleanStaleSessions)) {
                    this.mSessionCleanupHandler.postDelayed(this.mCleanStaleSessions, getSessionCleanupTimeoutMs());
                }
            }
        }
    }

    private synchronized void resetStaleSessionTimerOld() {
        if (this.mCleanStaleSessions == null) {
            return;
        }
        this.mSessionCleanupHandler.removeCallbacksAndMessages(null);
        this.mSessionCleanupHandler.postDelayed(this.mCleanStaleSessions, getSessionCleanupTimeoutMs());
    }

    public synchronized void startSession(Session.Info info, String str, String str2) {
        if (info == null) {
            startSession(str, str2);
        } else {
            startExternalSession(info, str);
        }
    }

    public synchronized void startSession(String str, String str2) {
        resetStaleSessionTimer();
        int callingThreadId = getCallingThreadId();
        if (this.mSessionMapper.get(Integer.valueOf(callingThreadId)) != null) {
            continueSession(createSubsession(true), str);
            return;
        }
        this.mSessionMapper.put(Integer.valueOf(callingThreadId), new Session(getNextSessionID(), str, System.currentTimeMillis(), false, false, str2));
        Log.d(LOGGING_TAG, Session.START_SESSION, new Object[0]);
    }

    public synchronized void startExternalSession(Session.Info info, String str) {
        if (info == null) {
            return;
        }
        int callingThreadId = getCallingThreadId();
        if (this.mSessionMapper.get(Integer.valueOf(callingThreadId)) != null) {
            Log.w(LOGGING_TAG, "trying to start an external session with a session already active.", new Object[0]);
            return;
        }
        Session session = new Session(Session.EXTERNAL_INDICATOR + info.sessionId, info.methodPath, System.currentTimeMillis(), false, true, info.ownerInfo);
        session.markSessionCompleted(-1L);
        this.mSessionMapper.put(Integer.valueOf(callingThreadId), session);
        Log.d(LOGGING_TAG, Session.START_EXTERNAL_SESSION, new Object[0]);
        continueSession(createSubsession(), str);
    }

    public Session createSubsession() {
        return createSubsession(false);
    }

    public synchronized Session createSubsession(boolean z) {
        Session session = this.mSessionMapper.get(Integer.valueOf(getCallingThreadId()));
        if (session == null) {
            Log.d(LOGGING_TAG, "Log.createSubsession was called with no session active.", new Object[0]);
            return null;
        }
        Session session2 = new Session(session.getNextChildId(), session.getShortMethodName(), System.currentTimeMillis(), z, false, session.getOwnerInfo());
        session.addChild(session2);
        session2.setParentSession(session);
        if (!z) {
            Log.v(LOGGING_TAG, Session.CREATE_SUBSESSION, new Object[0]);
        } else {
            Log.v(LOGGING_TAG, "CREATE_SUBSESSION (Invisible subsession)", new Object[0]);
        }
        return session2;
    }

    public synchronized Session.Info getExternalSession() {
        return getExternalSession(null);
    }

    public synchronized Session.Info getExternalSession(String str) {
        Session session = this.mSessionMapper.get(Integer.valueOf(getCallingThreadId()));
        if (session == null) {
            Log.d(LOGGING_TAG, "Log.getExternalSession was called with no session active.", new Object[0]);
            return null;
        }
        return session.getExternalInfo(str);
    }

    public synchronized void cancelSubsession(Session session) {
        if (session == null) {
            return;
        }
        session.markSessionCompleted(-1L);
        cleanupSessionTreeAndNotify(session);
    }

    public synchronized void continueSession(Session session, String str) {
        if (session == null) {
            return;
        }
        resetStaleSessionTimer();
        session.setShortMethodName(str);
        session.setExecutionStartTimeMs(System.currentTimeMillis());
        if (session.getParentSession() == null) {
            Log.i(LOGGING_TAG, "Log.continueSession was called with no session active for method " + str, new Object[0]);
        } else {
            this.mSessionMapper.put(Integer.valueOf(getCallingThreadId()), session);
            if (!session.isStartedFromActiveSession()) {
                Log.v(LOGGING_TAG, Session.CONTINUE_SUBSESSION, new Object[0]);
            } else {
                Log.v(LOGGING_TAG, "CONTINUE_SUBSESSION (Invisible Subsession) with Method " + str, new Object[0]);
            }
        }
    }

    public synchronized void endSession() {
        int callingThreadId = getCallingThreadId();
        Session session = this.mSessionMapper.get(Integer.valueOf(callingThreadId));
        if (session == null) {
            Log.w(LOGGING_TAG, "Log.endSession was called with no session active.", new Object[0]);
            return;
        }
        session.markSessionCompleted(System.currentTimeMillis());
        if (!session.isStartedFromActiveSession()) {
            Log.v(LOGGING_TAG, "END_SUBSESSION (dur: " + session.getLocalExecutionTime() + " mS)", new Object[0]);
        } else {
            Log.v(LOGGING_TAG, "END_SUBSESSION (Invisible Subsession) (dur: " + session.getLocalExecutionTime() + " ms)", new Object[0]);
        }
        Session parentSession = session.getParentSession();
        this.mSessionMapper.remove(Integer.valueOf(callingThreadId));
        cleanupSessionTreeAndNotify(session);
        if (parentSession != null && !parentSession.isSessionCompleted() && session.isStartedFromActiveSession()) {
            this.mSessionMapper.put(Integer.valueOf(callingThreadId), parentSession);
        }
    }

    private void cleanupSessionTreeAndNotify(Session session) {
        if (session == null) {
            return;
        }
        if (!Flags.endSessionImprovements()) {
            endParentSessionsRecursive(session);
            return;
        }
        while (session != null && session.isSessionCompleted() && session.getChildSessions().isEmpty()) {
            Session parentSession = session.getParentSession();
            session.setParentSession(null);
            boolean z = (parentSession == null && !session.isExternal()) || (parentSession != null && parentSession.isExternal());
            if (parentSession != null) {
                parentSession.removeChild(session);
            }
            if (z) {
                long currentTimeMillis = System.currentTimeMillis() - session.getExecutionStartTimeMilliseconds();
                Log.d(LOGGING_TAG, "END_SESSION (dur: " + currentTimeMillis + " ms): " + session, new Object[0]);
                notifySessionCompleteListeners(session.getShortMethodName(), currentTimeMillis);
            }
            session = parentSession;
        }
    }

    private void endParentSessionsRecursive(Session session) {
        if (session.isSessionCompleted() && session.getChildSessions().size() == 0) {
            Session parentSession = session.getParentSession();
            if (parentSession != null) {
                session.setParentSession(null);
                parentSession.removeChild(session);
                if (parentSession.isExternal()) {
                    notifySessionCompleteListeners(session.getShortMethodName(), System.currentTimeMillis() - session.getExecutionStartTimeMilliseconds());
                }
                endParentSessionsRecursive(parentSession);
                return;
            }
            long currentTimeMillis = System.currentTimeMillis() - session.getExecutionStartTimeMilliseconds();
            Log.d(LOGGING_TAG, "END_SESSION (dur: " + currentTimeMillis + " ms): " + session.toString(), new Object[0]);
            if (session.isExternal()) {
                return;
            }
            notifySessionCompleteListeners(session.getShortMethodName(), currentTimeMillis);
        }
    }

    private void notifySessionCompleteListeners(String str, long j) {
        synchronized (this.mSessionListeners) {
            Iterator<ISessionListener> it = this.mSessionListeners.iterator();
            while (it.hasNext()) {
                it.next().sessionComplete(str, j);
            }
        }
    }

    public String getSessionId() {
        Session session = this.mSessionMapper.get(Integer.valueOf(getCallingThreadId()));
        return session != null ? session.toString() : "";
    }

    public void registerSessionListener(ISessionListener iSessionListener) {
        synchronized (this.mSessionListeners) {
            this.mSessionListeners.add(iSessionListener);
        }
    }

    private synchronized String getNextSessionID() {
        Integer valueOf;
        int i = this.sCodeEntryCounter;
        this.sCodeEntryCounter = i + 1;
        valueOf = Integer.valueOf(i);
        valueOf.getClass();
        if (i >= 262144) {
            restartSessionCounter();
            int i2 = this.sCodeEntryCounter;
            this.sCodeEntryCounter = i2 + 1;
            valueOf = Integer.valueOf(i2);
        }
        return getBase64Encoding(valueOf.intValue());
    }

    private synchronized void restartSessionCounter() {
        this.sCodeEntryCounter = 0;
    }

    private String getBase64Encoding(int i) {
        return Base64.encodeToString(Arrays.copyOfRange(ByteBuffer.allocate(4).putInt(i).array(), 2, 4), 3);
    }

    private int getCallingThreadId() {
        return this.mCurrentThreadId.get();
    }

    public synchronized String printActiveSessions() {
        StringBuilder sb;
        sb = new StringBuilder();
        Iterator<Map.Entry<Integer, Session>> it = this.mSessionMapper.entrySet().iterator();
        while (it.hasNext()) {
            sb.append(it.next().getValue().printFullSessionTree());
            sb.append(ShaderAssembler.NEWLINE);
        }
        return sb.toString();
    }

    public synchronized void cleanupStaleSessions(long j) {
        StringBuilder sb = new StringBuilder("Stale Sessions Cleaned:");
        long currentTimeMillis = System.currentTimeMillis();
        Iterator<Map.Entry<Integer, Session>> it = this.mSessionMapper.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Session value = it.next().getValue();
            long executionStartTimeMilliseconds = currentTimeMillis - value.getExecutionStartTimeMilliseconds();
            if (executionStartTimeMilliseconds > j) {
                it.remove();
                sb.append(ShaderAssembler.NEWLINE);
                sb.append(NavigationBarInflaterView.SIZE_MOD_START);
                sb.append(executionStartTimeMilliseconds);
                sb.append("ms] ");
                sb.append(value.printFullSessionTree());
                z = true;
            }
        }
        if (z) {
            Log.w(LOGGING_TAG, sb.toString(), new Object[0]);
        } else {
            Log.v(LOGGING_TAG, "No stale logging sessions needed to be cleaned...", new Object[0]);
        }
    }

    private long getCleanupTimeout(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        return Settings.Secure.getLongForUser(contentResolver, "telecom.stale_session_cleanup_timeout_millis", 30000L, contentResolver.getUserId());
    }
}
