package com.android.server.display;

import android.hardware.display.AmbientBrightnessDayStats;
import android.os.SystemClock;
import android.os.UserManager;
import android.util.Xml;

import com.android.internal.util.FrameworkStatsLog;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.utils.DebugUtils;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public final class AmbientBrightnessStatsTracker {
    static final int MAX_DAYS_TO_TRACK = 7;
    public int mCurrentUserId;
    public final UserManager mUserManager;
    public static final boolean DEBUG = DebugUtils.isDebuggable("AmbientBrightnessStatsTracker");
    static final float[] BUCKET_BOUNDARIES_FOR_NEW_STATS = {
        FullScreenMagnificationGestureHandler.MAX_SCALE,
        0.1f,
        0.3f,
        1.0f,
        3.0f,
        10.0f,
        30.0f,
        100.0f,
        300.0f,
        1000.0f,
        3000.0f,
        10000.0f
    };
    public final Injector mInjector = new Injector();
    public final AmbientBrightnessStats mAmbientBrightnessStats = new AmbientBrightnessStats();
    public final Timer mTimer =
            new Timer(
                    new Clock() { // from class:
                                  // com.android.server.display.AmbientBrightnessStatsTracker$$ExternalSyntheticLambda0
                        @Override // com.android.server.display.AmbientBrightnessStatsTracker.Clock
                        public final long elapsedTimeMillis() {
                            AmbientBrightnessStatsTracker.this.mInjector.getClass();
                            return SystemClock.elapsedRealtime();
                        }
                    });
    public float mCurrentAmbientBrightness = -1.0f;

    public final class AmbientBrightnessStats {
        public Map mStats = new HashMap();

        public AmbientBrightnessStats() {}

        public static Deque getOrCreateUserStats(int i, Map map) {
            HashMap hashMap = (HashMap) map;
            if (!hashMap.containsKey(Integer.valueOf(i))) {
                hashMap.put(Integer.valueOf(i), new ArrayDeque());
            }
            return (Deque) hashMap.get(Integer.valueOf(i));
        }

        public final void log(int i, LocalDate localDate, float f, float f2) {
            Deque orCreateUserStats = getOrCreateUserStats(i, this.mStats);
            AmbientBrightnessDayStats ambientBrightnessDayStats =
                    (AmbientBrightnessDayStats) orCreateUserStats.peekLast();
            if (ambientBrightnessDayStats == null
                    || !ambientBrightnessDayStats.getLocalDate().equals(localDate)) {
                if (ambientBrightnessDayStats != null) {
                    FrameworkStatsLog.write(
                            FrameworkStatsLog.AMBIENT_BRIGHTNESS_STATS_REPORTED,
                            ambientBrightnessDayStats.getStats(),
                            ambientBrightnessDayStats.getBucketBoundaries());
                }
                ambientBrightnessDayStats =
                        new AmbientBrightnessDayStats(
                                localDate,
                                AmbientBrightnessStatsTracker.BUCKET_BOUNDARIES_FOR_NEW_STATS);
                if (orCreateUserStats.size() == 7) {
                    orCreateUserStats.poll();
                }
                orCreateUserStats.offer(ambientBrightnessDayStats);
            }
            ambientBrightnessDayStats.log(f, f2);
        }

        /* JADX WARN: Code restructure failed: missing block: B:27:0x004d, code lost:

           if (r6 != 4) goto L54;
        */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x005a, code lost:

           if ("ambient-brightness-day-stats".equals(r15.getName()) == false) goto L63;
        */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x005c, code lost:

           r6 = r15.getAttributeInt((java.lang.String) null, "user");
           r8 = java.time.LocalDate.parse(r15.getAttributeValue((java.lang.String) null, "local-date"));
           r9 = r15.getAttributeValue((java.lang.String) null, "bucket-boundaries").split(",");
           r7 = r15.getAttributeValue((java.lang.String) null, "bucket-stats").split(",");
        */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0087, code lost:

           if (r9.length != r7.length) goto L57;
        */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x008a, code lost:

           if (r9.length < 1) goto L58;
        */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x008c, code lost:

           r10 = new float[r9.length];
           r11 = new float[r7.length];
           r12 = 0;
        */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0094, code lost:

           if (r12 >= r9.length) goto L68;
        */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0096, code lost:

           r10[r12] = java.lang.Float.parseFloat(r9[r12]);
           r11[r12] = java.lang.Float.parseFloat(r7[r12]);
           r12 = r12 + 1;
        */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00a9, code lost:

           r7 = r1.mInjector;
           r9 = r1.mUserManager;
           r7.getClass();
           r6 = r9.getUserHandle(r6);
        */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x00b5, code lost:

           if (r6 == (-1)) goto L64;
        */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x00bb, code lost:

           if (r8.isAfter(r3) == false) goto L65;
        */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00bd, code lost:

           getOrCreateUserStats(r6, r2).offer(new android.hardware.display.AmbientBrightnessDayStats(r8, r10, r11));
        */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00d2, code lost:

           throw new java.io.IOException("Invalid brightness stats string.");
        */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x0037, code lost:

           continue;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void readFromXML(java.io.InputStream r15) {
            /*
                Method dump skipped, instructions count: 245
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.AmbientBrightnessStatsTracker.AmbientBrightnessStats.readFromXML(java.io.InputStream):void");
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry entry : this.mStats.entrySet()) {
                for (AmbientBrightnessDayStats ambientBrightnessDayStats :
                        (Deque) entry.getValue()) {
                    sb.append("  ");
                    sb.append(entry.getKey());
                    sb.append(" ");
                    sb.append(ambientBrightnessDayStats);
                    sb.append("\n");
                }
            }
            return sb.toString();
        }

        public final void writeToXML(OutputStream outputStream) {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(outputStream);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.setFeature(
                    "http://xmlpull.org/v1/doc/features.html#indent-output", true);
            AmbientBrightnessStatsTracker ambientBrightnessStatsTracker =
                    AmbientBrightnessStatsTracker.this;
            ambientBrightnessStatsTracker.mInjector.getClass();
            LocalDate minusDays = LocalDate.now().minusDays(7L);
            resolveSerializer.startTag((String) null, "ambient-brightness-stats");
            for (Map.Entry entry : this.mStats.entrySet()) {
                for (AmbientBrightnessDayStats ambientBrightnessDayStats :
                        (Deque) entry.getValue()) {
                    Injector injector = ambientBrightnessStatsTracker.mInjector;
                    UserManager userManager = ambientBrightnessStatsTracker.mUserManager;
                    int intValue = ((Integer) entry.getKey()).intValue();
                    injector.getClass();
                    int userSerialNumber = userManager.getUserSerialNumber(intValue);
                    if (userSerialNumber != -1
                            && ambientBrightnessDayStats.getLocalDate().isAfter(minusDays)) {
                        resolveSerializer.startTag((String) null, "ambient-brightness-day-stats");
                        resolveSerializer.attributeInt((String) null, "user", userSerialNumber);
                        resolveSerializer.attribute(
                                (String) null,
                                "local-date",
                                ambientBrightnessDayStats.getLocalDate().toString());
                        StringBuilder sb = new StringBuilder();
                        StringBuilder sb2 = new StringBuilder();
                        for (int i = 0;
                                i < ambientBrightnessDayStats.getBucketBoundaries().length;
                                i++) {
                            if (i > 0) {
                                sb.append(",");
                                sb2.append(",");
                            }
                            sb.append(ambientBrightnessDayStats.getBucketBoundaries()[i]);
                            sb2.append(ambientBrightnessDayStats.getStats()[i]);
                        }
                        resolveSerializer.attribute(
                                (String) null, "bucket-boundaries", sb.toString());
                        resolveSerializer.attribute((String) null, "bucket-stats", sb2.toString());
                        resolveSerializer.endTag((String) null, "ambient-brightness-day-stats");
                    }
                }
            }
            resolveSerializer.endTag((String) null, "ambient-brightness-stats");
            resolveSerializer.endDocument();
            outputStream.flush();
        }
    }

    interface Clock {
        long elapsedTimeMillis();
    }

    class Injector {}

    class Timer {
        public final Clock clock;
        public long startTimeMillis;
        public boolean started;

        public Timer(
                AmbientBrightnessStatsTracker$$ExternalSyntheticLambda0
                        ambientBrightnessStatsTracker$$ExternalSyntheticLambda0) {
            this.clock = ambientBrightnessStatsTracker$$ExternalSyntheticLambda0;
        }
    }

    public AmbientBrightnessStatsTracker(UserManager userManager) {
        this.mUserManager = userManager;
    }
}
