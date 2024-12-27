package com.android.server.pm;

import android.content.pm.UserInfo;
import android.util.SparseArray;
import com.android.internal.util.FrameworkStatsLog;
import java.util.concurrent.ThreadLocalRandom;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UserJourneyLogger {
    public final Object mLock = new Object();
    public final SparseArray mUserIdToUserJourneyMap = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserJourneySession {
        public final int mJourney;
        public final long mSessionId;
        public final long mStartTimeInMills;

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

    public static int getUserJourneyKey(int i, int i2) {
        return (i * 100) + i2;
    }

    public static int getUserTypeForStatsd(String str) {
        str.getClass();
        switch (str) {
            case "android.os.usertype.profile.PRIVATE":
                return 9;
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

    public UserJourneySession finishAndClearIncompleteUserJourney(int i, int i2) {
        synchronized (this.mLock) {
            try {
                int userJourneyKey = getUserJourneyKey(i, i2);
                UserJourneySession userJourneySession = (UserJourneySession) this.mUserIdToUserJourneyMap.get(userJourneyKey);
                if (userJourneySession == null) {
                    return null;
                }
                logUserLifecycleEventOccurred(userJourneySession, i, journeyToEvent(userJourneySession.mJourney), 4, 2);
                logUserLifecycleJourneyReported(userJourneySession, i2, -1, i, 0, -1, 2);
                this.mUserIdToUserJourneyMap.remove(userJourneyKey);
                return userJourneySession;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void logDelayedUserJourneyFinishWithError(int i, UserInfo userInfo, int i2) {
        synchronized (this.mLock) {
            try {
                int userJourneyKey = getUserJourneyKey(userInfo.id, 9);
                UserJourneySession userJourneySession = (UserJourneySession) this.mUserIdToUserJourneyMap.get(userJourneyKey);
                if (userJourneySession != null) {
                    logUserLifecycleJourneyReported(userJourneySession, 9, i, userInfo.id, getUserTypeForStatsd(userInfo.userType), userInfo.flags, i2);
                    this.mUserIdToUserJourneyMap.remove(userJourneyKey);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void logNullUserJourneyError(int i, int i2, int i3, int i4, String str) {
        synchronized (this.mLock) {
            int userJourneyKey = getUserJourneyKey(i3, i);
            UserJourneySession userJourneySession = (UserJourneySession) this.mUserIdToUserJourneyMap.get(userJourneyKey);
            logUserLifecycleEventOccurred(userJourneySession, i3, journeyToEvent(i), 4, 4);
            logUserLifecycleJourneyReported(userJourneySession, i, i2, i3, getUserTypeForStatsd(str), i4, 4);
            this.mUserIdToUserJourneyMap.remove(userJourneyKey);
        }
    }

    public final UserJourneySession logUserJourneyBegin(int i, int i2) {
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

    public final UserJourneySession logUserJourneyFinishWithError(int i, UserInfo userInfo, int i2, int i3) {
        int i4;
        synchronized (this.mLock) {
            if (i3 != -1) {
                i4 = 3;
                if (i3 != 3) {
                    i4 = 4;
                }
            } else {
                i4 = 2;
            }
            int i5 = i4;
            try {
                int userJourneyKey = getUserJourneyKey(userInfo.id, i2);
                UserJourneySession userJourneySession = (UserJourneySession) this.mUserIdToUserJourneyMap.get(userJourneyKey);
                if (userJourneySession == null) {
                    return null;
                }
                logUserLifecycleEventOccurred(userJourneySession, userInfo.id, journeyToEvent(userJourneySession.mJourney), i5, i3);
                logUserLifecycleJourneyReported(userJourneySession, i2, i, userInfo.id, getUserTypeForStatsd(userInfo.userType), userInfo.flags, i3);
                this.mUserIdToUserJourneyMap.remove(userJourneyKey);
                return userJourneySession;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void logUserLifecycleEvent(int i, int i2, int i3) {
        UserJourneySession userJourneySession;
        synchronized (this.mLock) {
            try {
                int size = this.mUserIdToUserJourneyMap.size();
                int i4 = 0;
                while (true) {
                    if (i4 >= size) {
                        userJourneySession = null;
                        break;
                    }
                    int keyAt = this.mUserIdToUserJourneyMap.keyAt(i4);
                    if (keyAt / 100 == i) {
                        userJourneySession = (UserJourneySession) this.mUserIdToUserJourneyMap.get(keyAt);
                        break;
                    }
                    i4++;
                }
            } finally {
            }
        }
        logUserLifecycleEventOccurred(userJourneySession, i, i2, i3, -1);
    }

    public void logUserLifecycleEventOccurred(UserJourneySession userJourneySession, int i, int i2, int i3, int i4) {
        if (userJourneySession == null) {
            writeUserLifecycleEventOccurred(-1L, i, i2, 4, 0);
        } else {
            writeUserLifecycleEventOccurred(userJourneySession.mSessionId, i, i2, i3, i4);
        }
    }

    public void logUserLifecycleJourneyReported(UserJourneySession userJourneySession, int i, int i2, int i3, int i4, int i5, int i6) {
        if (userJourneySession == null) {
            writeUserLifecycleJourneyReported(-1L, i, i2, i3, i4, i5, 0, -1L);
        } else {
            writeUserLifecycleJourneyReported(userJourneySession.mSessionId, i, i2, i3, i4, i5, i6, System.currentTimeMillis() - userJourneySession.mStartTimeInMills);
        }
    }

    public UserJourneySession logUserSwitchJourneyFinish(int i, UserInfo userInfo) {
        synchronized (this.mLock) {
            try {
                int userJourneyKey = getUserJourneyKey(userInfo.id, 2);
                int userJourneyKey2 = getUserJourneyKey(userInfo.id, 1);
                if (this.mUserIdToUserJourneyMap.contains(userJourneyKey)) {
                    return logUserJourneyFinishWithError(i, userInfo, 2, -1);
                }
                if (!this.mUserIdToUserJourneyMap.contains(userJourneyKey2)) {
                    return null;
                }
                return logUserJourneyFinishWithError(i, userInfo, 1, -1);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void writeUserLifecycleEventOccurred(long j, int i, int i2, int i3, int i4) {
        FrameworkStatsLog.write(FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED, j, i, i2, i3, i4);
    }

    public void writeUserLifecycleJourneyReported(long j, int i, int i2, int i3, int i4, int i5, int i6, long j2) {
        FrameworkStatsLog.write(264, j, i, i2, i3, i4, i5, i6, j2);
    }
}
