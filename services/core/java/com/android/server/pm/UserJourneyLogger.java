package com.android.server.pm;

import android.content.pm.UserInfo;
import android.util.SparseArray;
import com.android.internal.util.FrameworkStatsLog;
import java.util.concurrent.ThreadLocalRandom;

/* loaded from: classes3.dex */
public class UserJourneyLogger {
    public final Object mLock = new Object();
    public final SparseArray mUserIdToUserJourneyMap = new SparseArray();

    public static int errorToFinishState(int i) {
        if (i != -1) {
            return i != 3 ? 4 : 3;
        }
        return 2;
    }

    public static int journeyToEvent(int i) {
        switch (i) {
            case 1:
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 7;
            case 6:
                return 8;
            case 7:
                return 9;
            case 8:
                return 10;
            default:
                return 0;
        }
    }

    public final int getUserJourneyKey(int i, int i2) {
        return (i * 100) + i2;
    }

    public static int getUserTypeForStatsd(String str) {
        str.hashCode();
        switch (str) {
            case "android.os.usertype.full.GUEST":
                return 3;
            case "android.os.usertype.profile.MANAGED":
                return 6;
            case "android.os.usertype.system.HEADLESS":
                return 7;
            case "android.os.usertype.full.SYSTEM":
                return 1;
            case "android.os.usertype.full.SECONDARY":
                return 2;
            case "android.os.usertype.full.RESTRICTED":
                return 5;
            case "android.os.usertype.full.DEMO":
                return 4;
            case "android.os.usertype.profile.CLONE":
                return 8;
            default:
                return 0;
        }
    }

    public void logUserLifecycleJourneyReported(UserJourneySession userJourneySession, int i, int i2, int i3, int i4, int i5, int i6) {
        if (userJourneySession == null) {
            writeUserLifecycleJourneyReported(-1L, i, i2, i3, i4, i5, 0, -1L);
        } else {
            writeUserLifecycleJourneyReported(userJourneySession.mSessionId, i, i2, i3, i4, i5, i6, System.currentTimeMillis() - userJourneySession.mStartTimeInMills);
        }
    }

    public void writeUserLifecycleJourneyReported(long j, int i, int i2, int i3, int i4, int i5, int i6, long j2) {
        FrameworkStatsLog.write(264, j, i, i2, i3, i4, i5, i6, j2);
    }

    public void logUserLifecycleEventOccurred(UserJourneySession userJourneySession, int i, int i2, int i3, int i4) {
        if (userJourneySession == null) {
            writeUserLifecycleEventOccurred(-1L, i, i2, 4, 0);
        } else {
            writeUserLifecycleEventOccurred(userJourneySession.mSessionId, i, i2, i3, i4);
        }
    }

    public void writeUserLifecycleEventOccurred(long j, int i, int i2, int i3, int i4) {
        FrameworkStatsLog.write(FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED, j, i, i2, i3, i4);
    }

    public void logUserLifecycleEvent(int i, int i2, int i3) {
        logUserLifecycleEventOccurred(findUserJourneySession(i), i, i2, i3, -1);
    }

    public final UserJourneySession findUserJourneySession(int i) {
        synchronized (this.mLock) {
            int size = this.mUserIdToUserJourneyMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = this.mUserIdToUserJourneyMap.keyAt(i2);
                if (keyAt / 100 == i) {
                    return (UserJourneySession) this.mUserIdToUserJourneyMap.get(keyAt);
                }
            }
            return null;
        }
    }

    public UserJourneySession finishAndClearIncompleteUserJourney(int i, int i2) {
        synchronized (this.mLock) {
            int userJourneyKey = getUserJourneyKey(i, i2);
            UserJourneySession userJourneySession = (UserJourneySession) this.mUserIdToUserJourneyMap.get(userJourneyKey);
            if (userJourneySession == null) {
                return null;
            }
            logUserLifecycleEventOccurred(userJourneySession, i, journeyToEvent(userJourneySession.mJourney), 4, 2);
            logUserLifecycleJourneyReported(userJourneySession, i2, -1, i, getUserTypeForStatsd(""), -1, 2);
            this.mUserIdToUserJourneyMap.remove(userJourneyKey);
            return userJourneySession;
        }
    }

    public UserJourneySession logUserJourneyFinish(int i, UserInfo userInfo, int i2) {
        return logUserJourneyFinishWithError(i, userInfo, i2, -1);
    }

    public UserJourneySession logUserSwitchJourneyFinish(int i, UserInfo userInfo) {
        synchronized (this.mLock) {
            int userJourneyKey = getUserJourneyKey(userInfo.id, 2);
            int userJourneyKey2 = getUserJourneyKey(userInfo.id, 1);
            if (this.mUserIdToUserJourneyMap.contains(userJourneyKey)) {
                return logUserJourneyFinish(i, userInfo, 2);
            }
            if (!this.mUserIdToUserJourneyMap.contains(userJourneyKey2)) {
                return null;
            }
            return logUserJourneyFinish(i, userInfo, 1);
        }
    }

    public UserJourneySession logUserJourneyFinishWithError(int i, UserInfo userInfo, int i2, int i3) {
        synchronized (this.mLock) {
            int errorToFinishState = errorToFinishState(i3);
            int userJourneyKey = getUserJourneyKey(userInfo.id, i2);
            UserJourneySession userJourneySession = (UserJourneySession) this.mUserIdToUserJourneyMap.get(userJourneyKey);
            if (userJourneySession == null) {
                return null;
            }
            logUserLifecycleEventOccurred(userJourneySession, userInfo.id, journeyToEvent(userJourneySession.mJourney), errorToFinishState, i3);
            logUserLifecycleJourneyReported(userJourneySession, i2, i, userInfo.id, getUserTypeForStatsd(userInfo.userType), userInfo.flags, i3);
            this.mUserIdToUserJourneyMap.remove(userJourneyKey);
            return userJourneySession;
        }
    }

    public UserJourneySession logDelayedUserJourneyFinishWithError(int i, UserInfo userInfo, int i2, int i3) {
        synchronized (this.mLock) {
            int userJourneyKey = getUserJourneyKey(userInfo.id, i2);
            UserJourneySession userJourneySession = (UserJourneySession) this.mUserIdToUserJourneyMap.get(userJourneyKey);
            if (userJourneySession == null) {
                return null;
            }
            logUserLifecycleJourneyReported(userJourneySession, i2, i, userInfo.id, getUserTypeForStatsd(userInfo.userType), userInfo.flags, i3);
            this.mUserIdToUserJourneyMap.remove(userJourneyKey);
            return userJourneySession;
        }
    }

    public UserJourneySession logNullUserJourneyError(int i, int i2, int i3, String str, int i4) {
        UserJourneySession userJourneySession;
        synchronized (this.mLock) {
            int userJourneyKey = getUserJourneyKey(i3, i);
            userJourneySession = (UserJourneySession) this.mUserIdToUserJourneyMap.get(userJourneyKey);
            logUserLifecycleEventOccurred(userJourneySession, i3, journeyToEvent(i), 4, 4);
            logUserLifecycleJourneyReported(userJourneySession, i, i2, i3, getUserTypeForStatsd(str), i4, 4);
            this.mUserIdToUserJourneyMap.remove(userJourneyKey);
        }
        return userJourneySession;
    }

    public UserJourneySession logUserCreateJourneyFinish(int i, UserInfo userInfo) {
        synchronized (this.mLock) {
            int userJourneyKey = getUserJourneyKey(-1, 4);
            UserJourneySession userJourneySession = (UserJourneySession) this.mUserIdToUserJourneyMap.get(userJourneyKey);
            if (userJourneySession == null) {
                return null;
            }
            logUserLifecycleEventOccurred(userJourneySession, userInfo.id, 3, 2, -1);
            logUserLifecycleJourneyReported(userJourneySession, 4, i, userInfo.id, getUserTypeForStatsd(userInfo.userType), userInfo.flags, -1);
            this.mUserIdToUserJourneyMap.remove(userJourneyKey);
            return userJourneySession;
        }
    }

    public UserJourneySession logUserJourneyBegin(int i, int i2) {
        UserJourneySession userJourneySession;
        long nextLong = ThreadLocalRandom.current().nextLong(1L, Long.MAX_VALUE);
        synchronized (this.mLock) {
            int userJourneyKey = getUserJourneyKey(i, i2);
            userJourneySession = new UserJourneySession(nextLong, i2);
            this.mUserIdToUserJourneyMap.append(userJourneyKey, userJourneySession);
            logUserLifecycleEventOccurred(userJourneySession, i, journeyToEvent(userJourneySession.mJourney), 1, -1);
        }
        return userJourneySession;
    }

    public UserJourneySession startSessionForDelayedJourney(int i, int i2, long j) {
        UserJourneySession userJourneySession;
        long nextLong = ThreadLocalRandom.current().nextLong(1L, Long.MAX_VALUE);
        synchronized (this.mLock) {
            int userJourneyKey = getUserJourneyKey(i, i2);
            userJourneySession = new UserJourneySession(nextLong, i2, j);
            this.mUserIdToUserJourneyMap.append(userJourneyKey, userJourneySession);
        }
        return userJourneySession;
    }

    public class UserJourneySession {
        public final int mJourney;
        public final long mSessionId;
        public long mStartTimeInMills;

        public UserJourneySession(long j, int i) {
            this.mJourney = i;
            this.mSessionId = j;
            this.mStartTimeInMills = System.currentTimeMillis();
        }

        public UserJourneySession(long j, int i, long j2) {
            this.mJourney = i;
            this.mSessionId = j;
            this.mStartTimeInMills = j2;
        }
    }
}
