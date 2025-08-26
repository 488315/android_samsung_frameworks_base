package android.telecom.Logging;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Log;
import android.text.TextUtils;
import android.util.Slog;
import com.android.server.telecom.flags.Flags;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class Session {
    public static final String CONTINUE_SUBSESSION = "CONTINUE_SUBSESSION";
    public static final String CREATE_SUBSESSION = "CREATE_SUBSESSION";
    public static final String END_SESSION = "END_SESSION";
    public static final String END_SUBSESSION = "END_SUBSESSION";
    public static final String EXTERNAL_INDICATOR = "E-";
    public static final String LOG_TAG = "Session";
    private static final int SESSION_RECURSION_LIMIT = 25;
    public static final String SESSION_SEPARATION_CHAR_CHILD = "_";
    public static final String START_EXTERNAL_SESSION = "START_EXTERNAL_SESSION";
    public static final String START_SESSION = "START_SESSION";
    public static final String SUBSESSION_SEPARATION_CHAR = "->";
    public static final String TRUNCATE_STRING = "...";
    public static final long UNDEFINED = -1;
    private long mExecutionStartTimeMs;
    private volatile String mFullMethodPathCache;
    private final boolean mIsExternal;
    private final boolean mIsStartedFromActiveSession;
    private final String mOwnerInfo;
    private volatile Session mParentSession;
    private final String mSessionId;
    private volatile String mShortMethodName;
    private long mExecutionEndTimeMs = -1;
    private final ArrayList<Session> mChildSessions = new ArrayList<>(5);
    private boolean mIsCompleted = false;
    private final AtomicInteger mChildCounter = new AtomicInteger(0);

    public static class Info implements Parcelable {
        public static final Parcelable.Creator<Info> CREATOR = new Parcelable.Creator<Info>() { // from class: android.telecom.Logging.Session.Info.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Info createFromParcel(Parcel parcel) {
                return new Info(parcel.readString(), parcel.readString(), parcel.readString());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Info[] newArray(int i) {
                return new Info[i];
            }
        };
        public final String methodPath;
        public final String ownerInfo;
        public final String sessionId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Info(String str, String str2, String str3) {
            this.sessionId = str;
            this.methodPath = str2;
            this.ownerInfo = str3;
        }

        public static Info getInfo(Session session) {
            return new Info(session.getFullSessionId(), session.getFullMethodPath(!Log.DEBUG && session.isSessionExternal()), session.getOwnerInfo());
        }

        public static Info getExternalInfo(Session session, String str) {
            if (str != null && session.getOwnerInfo() != null) {
                str = session.getOwnerInfo() + "/" + str;
            } else if (str == null) {
                str = session.getOwnerInfo();
            }
            return new Info(session.getFullSessionId(), session.getFullMethodPath(!Log.DEBUG && session.isSessionExternal()), str);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.sessionId);
            parcel.writeString(this.methodPath);
            parcel.writeString(this.ownerInfo);
        }
    }

    public Session(String str, String str2, long j, boolean z, boolean z2, String str3) {
        this.mSessionId = str == null ? "???" : str;
        setShortMethodName(str2);
        this.mExecutionStartTimeMs = j;
        this.mParentSession = null;
        this.mIsStartedFromActiveSession = z;
        this.mIsExternal = z2;
        this.mOwnerInfo = str3;
    }

    public String getShortMethodName() {
        return this.mShortMethodName;
    }

    public void setShortMethodName(String str) {
        if (str == null) {
            str = "";
        }
        this.mShortMethodName = str;
    }

    public boolean isExternal() {
        return this.mIsExternal;
    }

    public void setParentSession(Session session) {
        this.mParentSession = session;
    }

    public void addChild(Session session) {
        if (session == null) {
            return;
        }
        synchronized (this.mChildSessions) {
            this.mChildSessions.add(session);
        }
    }

    public void removeChild(Session session) {
        if (session == null) {
            return;
        }
        synchronized (this.mChildSessions) {
            this.mChildSessions.remove(session);
        }
    }

    public long getExecutionStartTimeMilliseconds() {
        return this.mExecutionStartTimeMs;
    }

    public void setExecutionStartTimeMs(long j) {
        this.mExecutionStartTimeMs = j;
    }

    public Session getParentSession() {
        return this.mParentSession;
    }

    public ArrayList<Session> getChildSessions() {
        ArrayList<Session> arrayList;
        synchronized (this.mChildSessions) {
            arrayList = new ArrayList<>(this.mChildSessions);
        }
        return arrayList;
    }

    public boolean isSessionCompleted() {
        return this.mIsCompleted;
    }

    public boolean isStartedFromActiveSession() {
        return this.mIsStartedFromActiveSession;
    }

    public Info getInfo() {
        return Info.getInfo(this);
    }

    public Info getExternalInfo(String str) {
        return Info.getExternalInfo(this, str);
    }

    public String getOwnerInfo() {
        return this.mOwnerInfo;
    }

    public String getSessionId() {
        return this.mSessionId;
    }

    public void markSessionCompleted(long j) {
        this.mExecutionEndTimeMs = j;
        this.mIsCompleted = true;
    }

    public long getLocalExecutionTime() {
        long j = this.mExecutionEndTimeMs;
        if (j == -1) {
            return -1L;
        }
        return j - this.mExecutionStartTimeMs;
    }

    public String getNextChildId() {
        return String.valueOf(this.mChildCounter.getAndIncrement());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getFullSessionId() {
        if (!Flags.endSessionImprovements()) {
            return getFullSessionIdRecursive(0);
        }
        StringBuilder sb = new StringBuilder();
        Session session = this;
        int i = 0;
        while (session != null) {
            Session parentSession = session.getParentSession();
            if (parentSession == null) {
                sb.insert(0, session.getSessionId());
            } else {
                if (i >= 25) {
                    sb.insert(0, getSessionId());
                    sb.insert(0, TRUNCATE_STRING);
                    Slog.w(LOG_TAG, "getFullSessionId: Hit iteration limit!");
                    return sb.toString();
                }
                if (Log.VERBOSE) {
                    sb.insert(0, session.getSessionId());
                    sb.insert(0, SESSION_SEPARATION_CHAR_CHILD);
                }
            }
            i++;
            session = parentSession;
        }
        return sb.toString();
    }

    private String getFullSessionIdRecursive(int i) {
        if (i >= 25) {
            Slog.w(LOG_TAG, "getFullSessionId: Hit recursion limit!");
            return TRUNCATE_STRING + this.mSessionId;
        }
        Session session = this.mParentSession;
        if (session == null) {
            return this.mSessionId;
        }
        if (Log.VERBOSE) {
            return session.getFullSessionIdRecursive(i + 1) + SESSION_SEPARATION_CHAR_CHILD + this.mSessionId;
        }
        return session.getFullSessionIdRecursive(i + 1);
    }

    private Session getRootSession(String str) {
        Session parentSession = getParentSession();
        int i = 0;
        while (true) {
            Session session = parentSession;
            Session session2 = this;
            this = session;
            if (this == null) {
                return session2;
            }
            if (i >= 25) {
                Slog.w(LOG_TAG, "getRootSession: Hit iteration limit from " + str);
                return session2;
            }
            parentSession = this.getParentSession();
            i++;
        }
    }

    public String printFullSessionTree() {
        return getRootSession("printFullSessionTree").printSessionTree();
    }

    private String printSessionTree() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (!Flags.endSessionImprovements()) {
            printSessionTreeRecursive(0, sb, 0);
            return sb.toString();
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(this);
        while (!arrayDeque.isEmpty()) {
            Session session = (Session) arrayDeque.pollFirst();
            try {
                sb.append("\t".repeat(i));
                sb.append(session.toString());
                sb.append(ShaderAssembler.NEWLINE);
                if (i >= 25) {
                    sb.append(TRUNCATE_STRING);
                } else {
                    List listReversed = session.getChildSessions().reversed();
                    if (!listReversed.isEmpty()) {
                        i++;
                        Iterator it = listReversed.iterator();
                        while (it.hasNext()) {
                            arrayDeque.addFirst((Session) it.next());
                        }
                    }
                }
                i--;
            } catch (IllegalArgumentException e) {
                Slog.e(LOG_TAG, "printSessionTree's depth is wrong, " + e);
            }
        }
        return sb.toString();
    }

    private void printSessionTreeRecursive(int i, StringBuilder sb, int i2) {
        if (i2 >= 25) {
            Slog.w(LOG_TAG, "printSessionTree: Hit recursion limit!");
            sb.append(TRUNCATE_STRING);
            return;
        }
        sb.append(toString());
        Iterator<Session> it = this.mChildSessions.iterator();
        while (it.hasNext()) {
            Session next = it.next();
            sb.append(ShaderAssembler.NEWLINE);
            for (int i3 = 0; i3 <= i; i3++) {
                sb.append("\t");
            }
            next.printSessionTreeRecursive(i + 1, sb, i2 + 1);
        }
    }

    public String getFullMethodPath(boolean z) {
        StringBuilder sb = new StringBuilder();
        if (!Flags.endSessionImprovements()) {
            getFullMethodPathRecursive(sb, z, 0);
            return sb.toString();
        }
        Session parentSession = getParentSession();
        boolean z2 = parentSession == null || !getShortMethodName().equals(parentSession.getShortMethodName());
        Session session = this;
        int i = 0;
        while (session != null) {
            String str = session.mFullMethodPathCache;
            if (!TextUtils.isEmpty(str) && !z) {
                sb.insert(0, str);
                return sb.toString();
            }
            Session parentSession2 = session.getParentSession();
            if (!session.isExternal()) {
                sb.insert(0, session.getShortMethodName());
            } else if (z) {
                sb.insert(0, TRUNCATE_STRING);
            } else {
                sb.insert(0, NavigationBarInflaterView.KEY_CODE_END);
                sb.insert(0, session.getShortMethodName());
                sb.insert(0, NavigationBarInflaterView.KEY_CODE_START);
            }
            if (parentSession2 != null) {
                sb.insert(0, SUBSESSION_SEPARATION_CHAR);
            }
            if (i >= 25) {
                Slog.w(LOG_TAG, "getFullMethodPath: Hit iteration limit!");
                sb.insert(0, TRUNCATE_STRING);
                return sb.toString();
            }
            i++;
            session = parentSession2;
        }
        if (z2 && !z) {
            this.mFullMethodPathCache = sb.toString();
        }
        return sb.toString();
    }

    private synchronized void getFullMethodPathRecursive(StringBuilder sb, boolean z, int i) {
        if (i >= 25) {
            Slog.w(LOG_TAG, "getFullMethodPathRecursive: Hit recursion limit!");
            sb.append(TRUNCATE_STRING);
            return;
        }
        if (!TextUtils.isEmpty(this.mFullMethodPathCache) && !z) {
            sb.append(this.mFullMethodPathCache);
            return;
        }
        Session parentSession = getParentSession();
        boolean z2 = false;
        if (parentSession != null) {
            z2 = !this.mShortMethodName.equals(parentSession.mShortMethodName);
            parentSession.getFullMethodPathRecursive(sb, z, i + 1);
            sb.append(SUBSESSION_SEPARATION_CHAR);
        }
        if (!isExternal()) {
            sb.append(this.mShortMethodName);
        } else if (z) {
            sb.append(TRUNCATE_STRING);
        } else {
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(this.mShortMethodName);
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }
        if (z2 && !z) {
            this.mFullMethodPathCache = sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSessionExternal() {
        return getRootSession("isSessionExternal").isExternal();
    }

    public int hashCode() {
        int iHashCode = ((((((((((((((((this.mSessionId.hashCode() * 31) + this.mShortMethodName.hashCode()) * 31) + Long.hashCode(this.mExecutionStartTimeMs)) * 31) + Long.hashCode(this.mExecutionEndTimeMs)) * 31) + (this.mParentSession != null ? this.mParentSession.hashCode() : 0)) * 31) + this.mChildSessions.hashCode()) * 31) + (this.mIsCompleted ? 1 : 0)) * 31) + this.mChildCounter.hashCode()) * 31) + (this.mIsStartedFromActiveSession ? 1 : 0)) * 31;
        String str = this.mOwnerInfo;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Session session = (Session) obj;
        if (this.mExecutionStartTimeMs == session.mExecutionStartTimeMs && this.mExecutionEndTimeMs == session.mExecutionEndTimeMs && this.mIsCompleted == session.mIsCompleted && this.mChildCounter.get() == session.mChildCounter.get() && this.mIsStartedFromActiveSession == session.mIsStartedFromActiveSession && Objects.equals(this.mSessionId, session.mSessionId) && Objects.equals(this.mShortMethodName, session.mShortMethodName) && Objects.equals(this.mParentSession, session.mParentSession) && Objects.equals(this.mChildSessions, session.mChildSessions)) {
            return Objects.equals(this.mOwnerInfo, session.mOwnerInfo);
        }
        return false;
    }

    public String toString() {
        if (getParentSession() != null && isStartedFromActiveSession()) {
            this = getRootSession("toString");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.getFullMethodPath(false));
        if (this.getOwnerInfo() != null && !this.getOwnerInfo().isEmpty()) {
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(this.getOwnerInfo());
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }
        return sb.toString() + "@" + this.getFullSessionId();
    }
}
