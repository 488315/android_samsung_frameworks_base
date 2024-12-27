package com.android.server.usage;

import android.app.usage.ConfigurationStats;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class UsageStatsProtoV2 {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long ONE_HOUR_MS = TimeUnit.HOURS.toMillis(1);

    public static void loadChooserCounts(ProtoInputStream protoInputStream, UsageStats usageStats) {
        SparseIntArray sparseIntArray;
        if (protoInputStream.nextField(1120986464257L)) {
            int readInt = protoInputStream.readInt(1120986464257L) - 1;
            sparseIntArray = (SparseIntArray) usageStats.mChooserCountsObfuscated.get(readInt);
            if (sparseIntArray == null) {
                sparseIntArray = new SparseIntArray();
                usageStats.mChooserCountsObfuscated.put(readInt, sparseIntArray);
            }
        } else {
            sparseIntArray = new SparseIntArray();
        }
        while (true) {
            int nextField = protoInputStream.nextField();
            if (nextField == -1) {
                return;
            }
            if (nextField == 1) {
                usageStats.mChooserCountsObfuscated.put(
                        protoInputStream.readInt(1120986464257L) - 1, sparseIntArray);
            } else if (nextField == 2) {
                long start = protoInputStream.start(2246267895810L);
                int i = 0;
                int i2 = -1;
                while (true) {
                    int nextField2 = protoInputStream.nextField();
                    if (nextField2 == -1) {
                        break;
                    }
                    if (nextField2 == 1) {
                        i2 = protoInputStream.readInt(1120986464257L) - 1;
                    } else if (nextField2 == 2) {
                        i = protoInputStream.readInt(1120986464258L);
                    }
                }
                if (i2 != -1) {
                    sparseIntArray.put(i2, i);
                }
                protoInputStream.end(start);
            }
        }
    }

    public static void loadConfigStats(
            ProtoInputStream protoInputStream, IntervalStats intervalStats) {
        Configuration configuration = new Configuration();
        ConfigurationStats configurationStats = new ConfigurationStats();
        boolean z = false;
        if (protoInputStream.nextField(1146756268033L)) {
            configuration.readFromProto(protoInputStream, 1146756268033L);
            configurationStats = intervalStats.getOrCreateConfigurationStats(configuration);
        }
        while (true) {
            int nextField = protoInputStream.nextField();
            if (nextField == -1) {
                break;
            }
            if (nextField == 1) {
                configuration.readFromProto(protoInputStream, 1146756268033L);
                ConfigurationStats orCreateConfigurationStats =
                        intervalStats.getOrCreateConfigurationStats(configuration);
                orCreateConfigurationStats.mLastTimeActive = configurationStats.mLastTimeActive;
                orCreateConfigurationStats.mTotalTimeActive = configurationStats.mTotalTimeActive;
                orCreateConfigurationStats.mActivationCount = configurationStats.mActivationCount;
                configurationStats = orCreateConfigurationStats;
            } else if (nextField == 2) {
                configurationStats.mLastTimeActive =
                        protoInputStream.readLong(1112396529666L) + intervalStats.beginTime;
            } else if (nextField == 3) {
                configurationStats.mTotalTimeActive = protoInputStream.readLong(1112396529667L);
            } else if (nextField == 4) {
                configurationStats.mActivationCount = protoInputStream.readInt(1120986464260L);
            } else if (nextField == 5) {
                z = protoInputStream.readBoolean(1133871366149L);
            }
        }
        if (z) {
            intervalStats.activeConfiguration = configurationStats.mConfiguration;
        }
    }

    public static void loadCountAndTime(
            ProtoInputStream protoInputStream, long j, IntervalStats.EventTracker eventTracker) {
        try {
            long start = protoInputStream.start(j);
            while (true) {
                int nextField = protoInputStream.nextField();
                if (nextField == -1) {
                    protoInputStream.end(start);
                    return;
                } else if (nextField == 1) {
                    eventTracker.count = protoInputStream.readInt(1120986464257L);
                } else if (nextField == 2) {
                    eventTracker.duration = protoInputStream.readLong(1112396529666L);
                }
            }
        } catch (IOException e) {
            Slog.e("UsageStatsProtoV2", "Unable to read event tracker " + j, e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x00fb, code lost:

       if (r0.mPackageToken != (-1)) goto L80;
    */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00fd, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:?, code lost:

       return r0;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.app.usage.UsageEvents.Event parseEvent(
            android.util.proto.ProtoInputStream r12, long r13) {
        /*
            android.app.usage.UsageEvents$Event r0 = new android.app.usage.UsageEvents$Event
            r0.<init>()
        L5:
            int r1 = r12.nextField()
            r2 = 1120986464257(0x10500000001, double:5.538409014424E-312)
            r4 = 1120986464258(0x10500000002, double:5.53840901443E-312)
            r6 = -1
            r7 = 1
            switch(r1) {
                case -1: goto Lf9;
                case 0: goto L18;
                case 1: goto Lf0;
                case 2: goto Le7;
                case 3: goto Ld9;
                case 4: goto Lcc;
                case 5: goto Lbf;
                case 6: goto Lae;
                case 7: goto La0;
                case 8: goto L93;
                case 9: goto L85;
                case 10: goto L79;
                case 11: goto L6c;
                case 12: goto L5f;
                case 13: goto L52;
                case 14: goto L19;
                default: goto L18;
            }
        L18:
            goto L5
        L19:
            r8 = 1146756268046(0x10b0000000e, double:5.665728761946E-312)
            long r8 = r12.start(r8)     // Catch: java.io.IOException -> L49
            android.app.usage.UsageEvents$Event$UserInteractionEventExtrasToken r1 = new android.app.usage.UsageEvents$Event$UserInteractionEventExtrasToken     // Catch: java.io.IOException -> L49
            r1.<init>()     // Catch: java.io.IOException -> L49
        L27:
            int r10 = r12.nextField()     // Catch: java.io.IOException -> L49
            if (r10 == r6) goto L43
            if (r10 == r7) goto L3b
            r11 = 2
            if (r10 == r11) goto L33
            goto L27
        L33:
            int r10 = r12.readInt(r4)     // Catch: java.io.IOException -> L49
            int r10 = r10 - r7
            r1.mActionToken = r10     // Catch: java.io.IOException -> L49
            goto L27
        L3b:
            int r10 = r12.readInt(r2)     // Catch: java.io.IOException -> L49
            int r10 = r10 - r7
            r1.mCategoryToken = r10     // Catch: java.io.IOException -> L49
            goto L27
        L43:
            r0.mUserInteractionExtrasToken = r1     // Catch: java.io.IOException -> L49
            r12.end(r8)     // Catch: java.io.IOException -> L49
            goto L5
        L49:
            r1 = move-exception
            java.lang.String r2 = "UsageStatsProtoV2"
            java.lang.String r3 = "Unable to read some user interaction extras from proto."
            android.util.Slog.e(r2, r3, r1)
            goto L5
        L52:
            r1 = 1120986464269(0x1050000000d, double:5.538409014484E-312)
            int r1 = r12.readInt(r1)
            int r1 = r1 - r7
            r0.mLocusIdToken = r1
            goto L5
        L5f:
            r1 = 1120986464268(0x1050000000c, double:5.53840901448E-312)
            int r1 = r12.readInt(r1)
            int r1 = r1 - r7
            r0.mTaskRootClassToken = r1
            goto L5
        L6c:
            r1 = 1120986464267(0x1050000000b, double:5.538409014474E-312)
            int r1 = r12.readInt(r1)
            int r1 = r1 - r7
            r0.mTaskRootPackageToken = r1
            goto L5
        L79:
            r1 = 1120986464266(0x1050000000a, double:5.53840901447E-312)
            int r1 = r12.readInt(r1)
            r0.mInstanceId = r1
            goto L5
        L85:
            r1 = 1120986464265(0x10500000009, double:5.538409014464E-312)
            int r1 = r12.readInt(r1)
            int r1 = r1 - r7
            r0.mNotificationChannelIdToken = r1
            goto L5
        L93:
            r1 = 1120986464264(0x10500000008, double:5.53840901446E-312)
            int r1 = r12.readInt(r1)
            r0.mBucketAndReason = r1
            goto L5
        La0:
            r1 = 1120986464263(0x10500000007, double:5.538409014454E-312)
            int r1 = r12.readInt(r1)
            int r1 = r1 - r7
            r0.mShortcutIdToken = r1
            goto L5
        Lae:
            android.content.res.Configuration r1 = new android.content.res.Configuration
            r1.<init>()
            r0.mConfiguration = r1
            r2 = 1146756268038(0x10b00000006, double:5.665728761907E-312)
            r1.readFromProto(r12, r2)
            goto L5
        Lbf:
            r1 = 1120986464261(0x10500000005, double:5.538409014444E-312)
            int r1 = r12.readInt(r1)
            r0.mEventType = r1
            goto L5
        Lcc:
            r1 = 1120986464260(0x10500000004, double:5.53840901444E-312)
            int r1 = r12.readInt(r1)
            r0.mFlags = r1
            goto L5
        Ld9:
            r1 = 1112396529667(0x10300000003, double:5.495969098615E-312)
            long r1 = r12.readLong(r1)
            long r1 = r1 + r13
            r0.mTimeStamp = r1
            goto L5
        Le7:
            int r1 = r12.readInt(r4)
            int r1 = r1 - r7
            r0.mClassToken = r1
            goto L5
        Lf0:
            int r1 = r12.readInt(r2)
            int r1 = r1 - r7
            r0.mPackageToken = r1
            goto L5
        Lf9:
            int r12 = r0.mPackageToken
            if (r12 != r6) goto Lfe
            r0 = 0
        Lfe:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.usage.UsageStatsProtoV2.parseEvent(android.util.proto.ProtoInputStream,"
                    + " long):android.app.usage.UsageEvents$Event");
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00bb, code lost:

       r4 = r0.mEventType;
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00be, code lost:

       if (r4 == 5) goto L32;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00c4, code lost:

       if (r4 == 8) goto L29;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00c8, code lost:

       if (r4 == 12) goto L26;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00cd, code lost:

       if (r0.mNotificationChannelId != null) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00cf, code lost:

       r0.mNotificationChannelId = "";
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00e6, code lost:

       if (r0.mPackage != null) goto L68;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00e8, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:?, code lost:

       return r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00d4, code lost:

       if (r0.mShortcutId != null) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00d6, code lost:

       r0.mShortcutId = "";
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00db, code lost:

       if (r0.mConfiguration != null) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00dd, code lost:

       r0.mConfiguration = new android.content.res.Configuration();
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.app.usage.UsageEvents.Event parsePendingEvent(
            android.util.proto.ProtoInputStream r4) {
        /*
            android.app.usage.UsageEvents$Event r0 = new android.app.usage.UsageEvents$Event
            r0.<init>()
        L5:
            int r1 = r4.nextField()
            switch(r1) {
                case -1: goto Lbb;
                case 0: goto Lc;
                case 1: goto Lae;
                case 2: goto La1;
                case 3: goto L94;
                case 4: goto L87;
                case 5: goto L7a;
                case 6: goto L6a;
                case 7: goto L5e;
                case 8: goto L52;
                case 9: goto L46;
                case 10: goto L3a;
                case 11: goto L2e;
                case 12: goto L22;
                case 13: goto Lc;
                case 14: goto Ld;
                default: goto Lc;
            }
        Lc:
            goto L5
        Ld:
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream
            r2 = 1151051235342(0x10c0000000e, double:5.686948719856E-312)
            byte[] r2 = r4.readBytes(r2)
            r1.<init>(r2)
            android.os.PersistableBundle r1 = android.os.PersistableBundle.readFromStream(r1)
            r0.mExtras = r1
            goto L5
        L22:
            r1 = 1138166333452(0x1090000000c, double:5.623288846117E-312)
            java.lang.String r1 = r4.readString(r1)
            r0.mTaskRootClass = r1
            goto L5
        L2e:
            r1 = 1138166333451(0x1090000000b, double:5.62328884611E-312)
            java.lang.String r1 = r4.readString(r1)
            r0.mTaskRootPackage = r1
            goto L5
        L3a:
            r1 = 1120986464266(0x1050000000a, double:5.53840901447E-312)
            int r1 = r4.readInt(r1)
            r0.mInstanceId = r1
            goto L5
        L46:
            r1 = 1138166333449(0x10900000009, double:5.6232888461E-312)
            java.lang.String r1 = r4.readString(r1)
            r0.mNotificationChannelId = r1
            goto L5
        L52:
            r1 = 1120986464264(0x10500000008, double:5.53840901446E-312)
            int r1 = r4.readInt(r1)
            r0.mBucketAndReason = r1
            goto L5
        L5e:
            r1 = 1138166333447(0x10900000007, double:5.623288846093E-312)
            java.lang.String r1 = r4.readString(r1)
            r0.mShortcutId = r1
            goto L5
        L6a:
            android.content.res.Configuration r1 = new android.content.res.Configuration
            r1.<init>()
            r0.mConfiguration = r1
            r2 = 1146756268038(0x10b00000006, double:5.665728761907E-312)
            r1.readFromProto(r4, r2)
            goto L5
        L7a:
            r1 = 1120986464261(0x10500000005, double:5.538409014444E-312)
            int r1 = r4.readInt(r1)
            r0.mEventType = r1
            goto L5
        L87:
            r1 = 1120986464260(0x10500000004, double:5.53840901444E-312)
            int r1 = r4.readInt(r1)
            r0.mFlags = r1
            goto L5
        L94:
            r1 = 1112396529667(0x10300000003, double:5.495969098615E-312)
            long r1 = r4.readLong(r1)
            r0.mTimeStamp = r1
            goto L5
        La1:
            r1 = 1138166333442(0x10900000002, double:5.62328884607E-312)
            java.lang.String r1 = r4.readString(r1)
            r0.mClass = r1
            goto L5
        Lae:
            r1 = 1138166333441(0x10900000001, double:5.623288846063E-312)
            java.lang.String r1 = r4.readString(r1)
            r0.mPackage = r1
            goto L5
        Lbb:
            int r4 = r0.mEventType
            r1 = 5
            if (r4 == r1) goto Ld9
            r1 = 8
            java.lang.String r2 = ""
            if (r4 == r1) goto Ld2
            r1 = 12
            if (r4 == r1) goto Lcb
            goto Le4
        Lcb:
            java.lang.String r4 = r0.mNotificationChannelId
            if (r4 != 0) goto Le4
            r0.mNotificationChannelId = r2
            goto Le4
        Ld2:
            java.lang.String r4 = r0.mShortcutId
            if (r4 != 0) goto Le4
            r0.mShortcutId = r2
            goto Le4
        Ld9:
            android.content.res.Configuration r4 = r0.mConfiguration
            if (r4 != 0) goto Le4
            android.content.res.Configuration r4 = new android.content.res.Configuration
            r4.<init>()
            r0.mConfiguration = r4
        Le4:
            java.lang.String r4 = r0.mPackage
            if (r4 != 0) goto Le9
            r0 = 0
        Le9:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.usage.UsageStatsProtoV2.parsePendingEvent(android.util.proto.ProtoInputStream):android.app.usage.UsageEvents$Event");
    }

    public static UsageStats parseUsageStats(ProtoInputStream protoInputStream, long j) {
        UsageStats usageStats = new UsageStats();
        while (true) {
            int nextField = protoInputStream.nextField();
            if (nextField == -1) {
                return usageStats;
            }
            if (nextField == 1) {
                usageStats.mPackageToken = protoInputStream.readInt(1120986464257L) - 1;
            } else if (nextField == 3) {
                usageStats.mLastTimeUsed = protoInputStream.readLong(1112396529667L) + j;
            } else if (nextField != 4) {
                switch (nextField) {
                    case 6:
                        usageStats.mAppLaunchCount = protoInputStream.readInt(1120986464262L);
                        break;
                    case 7:
                        try {
                            long start = protoInputStream.start(2246267895815L);
                            loadChooserCounts(protoInputStream, usageStats);
                            protoInputStream.end(start);
                            break;
                        } catch (IOException unused) {
                            VaultKeeperService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Unable to read chooser counts for "),
                                    usageStats.mPackageToken,
                                    "UsageStatsProtoV2");
                            break;
                        }
                    case 8:
                        usageStats.mLastTimeForegroundServiceUsed =
                                protoInputStream.readLong(1112396529672L) + j;
                        break;
                    case 9:
                        usageStats.mTotalTimeForegroundServiceUsed =
                                protoInputStream.readLong(1112396529673L);
                        break;
                    case 10:
                        usageStats.mLastTimeVisible = protoInputStream.readLong(1112396529674L) + j;
                        break;
                    case 11:
                        usageStats.mTotalTimeVisible = protoInputStream.readLong(1112396529675L);
                        break;
                    case 12:
                        usageStats.mLastTimeComponentUsed =
                                protoInputStream.readLong(1112396529676L) + j;
                        break;
                }
            } else {
                usageStats.mTotalTimeInForeground = protoInputStream.readLong(1112396529668L);
            }
        }
    }

    public static void read(InputStream inputStream, IntervalStats intervalStats, boolean z) {
        ProtoInputStream protoInputStream = new ProtoInputStream(inputStream);
        while (true) {
            int nextField = protoInputStream.nextField();
            if (nextField == -1) {
                int size = intervalStats.packageStatsObfuscated.size();
                for (int i = 0; i < size; i++) {
                    UsageStats usageStats =
                            (UsageStats) intervalStats.packageStatsObfuscated.valueAt(i);
                    usageStats.mBeginTimeStamp = intervalStats.beginTime;
                    usageStats.mEndTimeStamp = intervalStats.endTime;
                }
                return;
            }
            if (nextField == 1) {
                intervalStats.endTime =
                        protoInputStream.readLong(1112396529665L) + intervalStats.beginTime;
            } else if (nextField == 2) {
                intervalStats.majorVersion = protoInputStream.readInt(1120986464258L);
            } else if (nextField != 3) {
                switch (nextField) {
                    case 10:
                        loadCountAndTime(
                                protoInputStream, 1146756268042L, intervalStats.interactiveTracker);
                        break;
                    case 11:
                        loadCountAndTime(
                                protoInputStream,
                                1146756268043L,
                                intervalStats.nonInteractiveTracker);
                        break;
                    case 12:
                        loadCountAndTime(
                                protoInputStream,
                                1146756268044L,
                                intervalStats.keyguardShownTracker);
                        break;
                    case 13:
                        loadCountAndTime(
                                protoInputStream,
                                1146756268045L,
                                intervalStats.keyguardHiddenTracker);
                        break;
                    default:
                        switch (nextField) {
                            case 20:
                                try {
                                    long start = protoInputStream.start(2246267895828L);
                                    UsageStats parseUsageStats =
                                            parseUsageStats(
                                                    protoInputStream, intervalStats.beginTime);
                                    protoInputStream.end(start);
                                    int i2 = parseUsageStats.mPackageToken;
                                    if (i2 != -1) {
                                        intervalStats.packageStatsObfuscated.put(
                                                i2, parseUsageStats);
                                        break;
                                    } else {
                                        break;
                                    }
                                } catch (IOException e) {
                                    Slog.e(
                                            "UsageStatsProtoV2",
                                            "Unable to read some usage stats from proto.",
                                            e);
                                    break;
                                }
                            case 21:
                                try {
                                    long start2 = protoInputStream.start(2246267895829L);
                                    loadConfigStats(protoInputStream, intervalStats);
                                    protoInputStream.end(start2);
                                    break;
                                } catch (IOException e2) {
                                    Slog.e(
                                            "UsageStatsProtoV2",
                                            "Unable to read some configuration stats from proto.",
                                            e2);
                                    break;
                                }
                            case 22:
                                if (z) {
                                    break;
                                } else {
                                    try {
                                        long start3 = protoInputStream.start(2246267895830L);
                                        UsageEvents.Event parseEvent =
                                                parseEvent(
                                                        protoInputStream, intervalStats.beginTime);
                                        protoInputStream.end(start3);
                                        if (parseEvent != null) {
                                            intervalStats.events.insert(parseEvent);
                                            break;
                                        } else {
                                            break;
                                        }
                                    } catch (IOException e3) {
                                        Slog.e(
                                                "UsageStatsProtoV2",
                                                "Unable to read some events from proto.",
                                                e3);
                                        break;
                                    }
                                }
                        }
                }
            } else {
                intervalStats.minorVersion = protoInputStream.readInt(1120986464259L);
            }
        }
    }

    public static void readGlobalComponentUsage(InputStream inputStream, Map map) {
        ProtoInputStream protoInputStream = new ProtoInputStream(inputStream);
        while (true) {
            int nextField = protoInputStream.nextField();
            if (nextField == -1) {
                return;
            }
            if (nextField == 24) {
                try {
                    long start = protoInputStream.start(2246267895832L);
                    String str = "";
                    long j = 0;
                    while (true) {
                        int nextField2 = protoInputStream.nextField();
                        if (nextField2 == -1) {
                            break;
                        }
                        if (nextField2 == 1) {
                            str = protoInputStream.readString(1138166333441L);
                        } else if (nextField2 == 2) {
                            j = protoInputStream.readLong(1112396529666L);
                        }
                    }
                    Pair pair = new Pair(str, Long.valueOf(j));
                    protoInputStream.end(start);
                    if (!((String) pair.first).isEmpty() && ((Long) pair.second).longValue() > 0) {
                        ((ArrayMap) map).put((String) pair.first, (Long) pair.second);
                    }
                } catch (IOException e) {
                    Slog.e(
                            "UsageStatsProtoV2",
                            "Unable to parse some package usage from proto.",
                            e);
                }
            }
        }
    }

    public static void readObfuscatedData(
            InputStream inputStream, PackagesTokenData packagesTokenData) {
        ProtoInputStream protoInputStream = new ProtoInputStream(inputStream);
        while (true) {
            int nextField = protoInputStream.nextField();
            if (nextField == -1) {
                return;
            }
            if (nextField == 1) {
                packagesTokenData.counter = protoInputStream.readInt(1120986464257L);
            } else if (nextField == 2) {
                long start = protoInputStream.start(2246267895810L);
                SparseArray sparseArray = packagesTokenData.tokensToPackagesMap;
                ArrayList arrayList = new ArrayList();
                int i = -1;
                while (true) {
                    int nextField2 = protoInputStream.nextField();
                    if (nextField2 == -1) {
                        break;
                    }
                    if (nextField2 == 1) {
                        i = protoInputStream.readInt(1120986464257L) - 1;
                    } else if (nextField2 == 2) {
                        arrayList.add(protoInputStream.readString(2237677961218L));
                    }
                }
                if (i != -1) {
                    sparseArray.put(i, arrayList);
                }
                protoInputStream.end(start);
            }
        }
    }

    public static void write(OutputStream outputStream, IntervalStats intervalStats) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
        long j = intervalStats.endTime - intervalStats.beginTime;
        if (j == 0) {
            j++;
        }
        protoOutputStream.write(1112396529665L, j);
        protoOutputStream.write(1120986464258L, intervalStats.majorVersion);
        protoOutputStream.write(1120986464259L, intervalStats.minorVersion);
        try {
            IntervalStats.EventTracker eventTracker = intervalStats.interactiveTracker;
            writeCountAndTime(
                    eventTracker.count, 1146756268042L, eventTracker.duration, protoOutputStream);
            IntervalStats.EventTracker eventTracker2 = intervalStats.nonInteractiveTracker;
            writeCountAndTime(
                    eventTracker2.count, 1146756268043L, eventTracker2.duration, protoOutputStream);
            IntervalStats.EventTracker eventTracker3 = intervalStats.keyguardShownTracker;
            writeCountAndTime(
                    eventTracker3.count, 1146756268044L, eventTracker3.duration, protoOutputStream);
            IntervalStats.EventTracker eventTracker4 = intervalStats.keyguardHiddenTracker;
            writeCountAndTime(
                    eventTracker4.count, 1146756268045L, eventTracker4.duration, protoOutputStream);
        } catch (IllegalArgumentException e) {
            Slog.e(
                    "UsageStatsProtoV2",
                    "Unable to write some interval stats trackers to proto.",
                    e);
        }
        int size = intervalStats.packageStatsObfuscated.size();
        for (int i = 0; i < size; i++) {
            try {
                long start = protoOutputStream.start(2246267895828L);
                writeUsageStats(
                        protoOutputStream,
                        intervalStats.beginTime,
                        (UsageStats) intervalStats.packageStatsObfuscated.valueAt(i));
                protoOutputStream.end(start);
            } catch (IllegalArgumentException e2) {
                Slog.e("UsageStatsProtoV2", "Unable to write some usage stats to proto.", e2);
            }
        }
        int size2 = intervalStats.configurations.size();
        for (int i2 = 0; i2 < size2; i2++) {
            boolean equals =
                    intervalStats.activeConfiguration.equals(
                            (Configuration) intervalStats.configurations.keyAt(i2));
            try {
                long start2 = protoOutputStream.start(2246267895829L);
                long j2 = intervalStats.beginTime;
                ConfigurationStats configurationStats =
                        (ConfigurationStats) intervalStats.configurations.valueAt(i2);
                configurationStats.mConfiguration.dumpDebug(protoOutputStream, 1146756268033L);
                writeOffsetTimestamp(
                        protoOutputStream, 1112396529666L, configurationStats.mLastTimeActive, j2);
                protoOutputStream.write(1112396529667L, configurationStats.mTotalTimeActive);
                protoOutputStream.write(1120986464260L, configurationStats.mActivationCount);
                protoOutputStream.write(1133871366149L, equals);
                protoOutputStream.end(start2);
            } catch (IllegalArgumentException e3) {
                Slog.e(
                        "UsageStatsProtoV2",
                        "Unable to write some configuration stats to proto.",
                        e3);
            }
        }
        int size3 = intervalStats.events.size();
        for (int i3 = 0; i3 < size3; i3++) {
            try {
                long start3 = protoOutputStream.start(2246267895830L);
                writeEvent(
                        protoOutputStream, intervalStats.beginTime, intervalStats.events.get(i3));
                protoOutputStream.end(start3);
            } catch (IllegalArgumentException e4) {
                Slog.e("UsageStatsProtoV2", "Unable to write some events to proto.", e4);
            }
        }
        protoOutputStream.flush();
    }

    public static void writeChooserCounts(
            ProtoOutputStream protoOutputStream, UsageStats usageStats) {
        if (usageStats == null || usageStats.mChooserCountsObfuscated.size() == 0) {
            return;
        }
        int size = usageStats.mChooserCountsObfuscated.size();
        for (int i = 0; i < size; i++) {
            int keyAt = usageStats.mChooserCountsObfuscated.keyAt(i);
            SparseIntArray sparseIntArray =
                    (SparseIntArray) usageStats.mChooserCountsObfuscated.valueAt(i);
            if (sparseIntArray != null && sparseIntArray.size() != 0) {
                long start = protoOutputStream.start(2246267895815L);
                long j = 1120986464257L;
                protoOutputStream.write(1120986464257L, keyAt + 1);
                int size2 = sparseIntArray.size();
                int i2 = 0;
                while (i2 < size2) {
                    int keyAt2 = sparseIntArray.keyAt(i2);
                    int valueAt = sparseIntArray.valueAt(i2);
                    if (valueAt > 0) {
                        long start2 = protoOutputStream.start(2246267895810L);
                        protoOutputStream.write(j, keyAt2 + 1);
                        protoOutputStream.write(1120986464258L, valueAt);
                        protoOutputStream.end(start2);
                    }
                    i2++;
                    j = 1120986464257L;
                }
                protoOutputStream.end(start);
            }
        }
    }

    public static void writeCountAndTime(
            int i, long j, long j2, ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1112396529666L, j2);
        protoOutputStream.end(start);
    }

    public static void writeEvent(
            ProtoOutputStream protoOutputStream, long j, UsageEvents.Event event) {
        int i;
        protoOutputStream.write(1120986464257L, event.mPackageToken + 1);
        int i2 = event.mClassToken;
        if (i2 != -1) {
            protoOutputStream.write(1120986464258L, i2 + 1);
        }
        writeOffsetTimestamp(protoOutputStream, 1112396529667L, event.mTimeStamp, j);
        protoOutputStream.write(1120986464260L, event.mFlags);
        protoOutputStream.write(1120986464261L, event.mEventType);
        protoOutputStream.write(1120986464266L, event.mInstanceId);
        int i3 = event.mTaskRootPackageToken;
        if (i3 != -1) {
            protoOutputStream.write(1120986464267L, i3 + 1);
        }
        int i4 = event.mTaskRootClassToken;
        if (i4 != -1) {
            protoOutputStream.write(1120986464268L, i4 + 1);
        }
        int i5 = event.mEventType;
        if (i5 == 5) {
            Configuration configuration = event.mConfiguration;
            if (configuration != null) {
                configuration.dumpDebug(protoOutputStream, 1146756268038L);
                return;
            }
            return;
        }
        if (i5 == 30) {
            int i6 = event.mLocusIdToken;
            if (i6 != -1) {
                protoOutputStream.write(1120986464269L, i6 + 1);
                return;
            }
            return;
        }
        if (i5 == 7) {
            UsageEvents.Event.UserInteractionEventExtrasToken userInteractionEventExtrasToken =
                    event.mUserInteractionExtrasToken;
            if (userInteractionEventExtrasToken != null) {
                long start = protoOutputStream.start(1146756268046L);
                protoOutputStream.write(
                        1120986464257L, userInteractionEventExtrasToken.mCategoryToken + 1);
                protoOutputStream.write(
                        1120986464258L, userInteractionEventExtrasToken.mActionToken + 1);
                protoOutputStream.end(start);
                return;
            }
            return;
        }
        if (i5 == 8) {
            int i7 = event.mShortcutIdToken;
            if (i7 != -1) {
                protoOutputStream.write(1120986464263L, i7 + 1);
                return;
            }
            return;
        }
        if (i5 != 11) {
            if (i5 == 12 && (i = event.mNotificationChannelIdToken) != -1) {
                protoOutputStream.write(1120986464265L, i + 1);
                return;
            }
            return;
        }
        int i8 = event.mBucketAndReason;
        if (i8 != 0) {
            protoOutputStream.write(1120986464264L, i8);
        }
    }

    public static void writeGlobalComponentUsage(Map map, OutputStream outputStream) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
        Map.Entry[] entryArr = (Map.Entry[]) map.entrySet().toArray();
        int length = entryArr.length;
        for (int i = 0; i < length; i++) {
            if (((Long) entryArr[i].getValue()).longValue() > 0) {
                long start = protoOutputStream.start(2246267895832L);
                protoOutputStream.write(1138166333441L, (String) entryArr[i].getKey());
                protoOutputStream.write(
                        1112396529666L, ((Long) entryArr[i].getValue()).longValue());
                protoOutputStream.end(start);
            }
        }
    }

    public static void writeObfuscatedData(
            OutputStream outputStream, PackagesTokenData packagesTokenData) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
        protoOutputStream.write(1120986464257L, packagesTokenData.counter);
        int size = packagesTokenData.tokensToPackagesMap.size();
        for (int i = 0; i < size; i++) {
            long start = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(
                    1120986464257L, packagesTokenData.tokensToPackagesMap.keyAt(i) + 1);
            ArrayList arrayList = (ArrayList) packagesTokenData.tokensToPackagesMap.valueAt(i);
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                protoOutputStream.write(2237677961218L, (String) arrayList.get(i2));
            }
            protoOutputStream.end(start);
        }
        protoOutputStream.flush();
    }

    public static void writeOffsetTimestamp(
            ProtoOutputStream protoOutputStream, long j, long j2, long j3) {
        if (j2 > j3 - ONE_HOUR_MS) {
            long j4 = j2 - j3;
            if (j4 == 0) {
                j4++;
            }
            protoOutputStream.write(j, j4);
        }
    }

    public static void writePendingEvent(
            ProtoOutputStream protoOutputStream, UsageEvents.Event event) {
        String str;
        protoOutputStream.write(1138166333441L, event.mPackage);
        String str2 = event.mClass;
        if (str2 != null) {
            protoOutputStream.write(1138166333442L, str2);
        }
        protoOutputStream.write(1112396529667L, event.mTimeStamp);
        protoOutputStream.write(1120986464260L, event.mFlags);
        protoOutputStream.write(1120986464261L, event.mEventType);
        protoOutputStream.write(1120986464266L, event.mInstanceId);
        String str3 = event.mTaskRootPackage;
        if (str3 != null) {
            protoOutputStream.write(1138166333451L, str3);
        }
        String str4 = event.mTaskRootClass;
        if (str4 != null) {
            protoOutputStream.write(1138166333452L, str4);
        }
        int i = event.mEventType;
        if (i == 5) {
            Configuration configuration = event.mConfiguration;
            if (configuration != null) {
                configuration.dumpDebug(protoOutputStream, 1146756268038L);
                return;
            }
            return;
        }
        if (i == 7) {
            PersistableBundle persistableBundle = event.mExtras;
            if (persistableBundle == null || persistableBundle.size() == 0) {
                return;
            }
            PersistableBundle persistableBundle2 = event.mExtras;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            persistableBundle2.writeToStream(byteArrayOutputStream);
            protoOutputStream.write(1151051235342L, byteArrayOutputStream.toByteArray());
            return;
        }
        if (i == 8) {
            String str5 = event.mShortcutId;
            if (str5 != null) {
                protoOutputStream.write(1138166333447L, str5);
                return;
            }
            return;
        }
        if (i != 11) {
            if (i == 12 && (str = event.mNotificationChannelId) != null) {
                protoOutputStream.write(1138166333449L, str);
                return;
            }
            return;
        }
        int i2 = event.mBucketAndReason;
        if (i2 != 0) {
            protoOutputStream.write(1120986464264L, i2);
        }
    }

    public static void writePendingEvents(OutputStream outputStream, LinkedList linkedList) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
        int size = linkedList.size();
        for (int i = 0; i < size; i++) {
            try {
                long start = protoOutputStream.start(2246267895831L);
                writePendingEvent(protoOutputStream, (UsageEvents.Event) linkedList.get(i));
                protoOutputStream.end(start);
            } catch (IllegalArgumentException e) {
                Slog.e("UsageStatsProtoV2", "Unable to write some pending events to proto.", e);
            }
        }
        protoOutputStream.flush();
    }

    public static void writeUsageStats(
            ProtoOutputStream protoOutputStream, long j, UsageStats usageStats) {
        protoOutputStream.write(1120986464257L, usageStats.mPackageToken + 1);
        writeOffsetTimestamp(protoOutputStream, 1112396529667L, usageStats.mLastTimeUsed, j);
        protoOutputStream.write(1112396529668L, usageStats.mTotalTimeInForeground);
        writeOffsetTimestamp(
                protoOutputStream, 1112396529672L, usageStats.mLastTimeForegroundServiceUsed, j);
        protoOutputStream.write(1112396529673L, usageStats.mTotalTimeForegroundServiceUsed);
        writeOffsetTimestamp(protoOutputStream, 1112396529674L, usageStats.mLastTimeVisible, j);
        protoOutputStream.write(1112396529675L, usageStats.mTotalTimeVisible);
        writeOffsetTimestamp(
                protoOutputStream, 1112396529676L, usageStats.mLastTimeComponentUsed, j);
        protoOutputStream.write(1120986464262L, usageStats.mAppLaunchCount);
        try {
            writeChooserCounts(protoOutputStream, usageStats);
        } catch (IllegalArgumentException e) {
            Slog.e(
                    "UsageStatsProtoV2",
                    "Unable to write chooser counts for " + usageStats.mPackageName,
                    e);
        }
    }
}
