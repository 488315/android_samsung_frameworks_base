package com.android.server.display;

import android.os.SystemClock;
import android.os.UserManager;
import android.util.Slog;
import android.util.Spline;

import com.android.internal.util.FastXmlSerializer;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.power.PowerManagerUtil;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AdaptiveBrightnessStatsTracker {
    static final int MAX_DAYS_TO_TRACK = 7;
    public final AdaptiveBrightnessStats mAdaptiveBrightnessStats;
    public final float[] mBucketBoundariesForStats;
    public float mCurrentScreenBrightness;
    public Spline mCurrentScreenBrightnessSpline;
    public int mCurrentUserId;
    public final UserManager mUserManager;
    public final Injector mInjector = new Injector();
    public final Timer mTimer =
            new Timer(
                    new Clock() { // from class:
                                  // com.android.server.display.AdaptiveBrightnessStatsTracker$$ExternalSyntheticLambda0
                        @Override // com.android.server.display.AdaptiveBrightnessStatsTracker.Clock
                        public final long elapsedTimeMillis() {
                            AdaptiveBrightnessStatsTracker.this.mInjector.getClass();
                            return SystemClock.elapsedRealtime();
                        }
                    });
    public float mCurrentAmbientLux = -1.0f;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdaptiveBrightnessStats {
        public final BrightnessMappingStrategy mBrightnessMapper;
        public Map mStats = new HashMap();

        public AdaptiveBrightnessStats(BrightnessMappingStrategy brightnessMappingStrategy) {
            this.mBrightnessMapper = brightnessMappingStrategy;
        }

        public final AdaptiveBrightnessWeightStats getOrCreateUserStats(int i, Map map) {
            if (!map.containsKey(Integer.valueOf(i))) {
                map.put(
                        Integer.valueOf(i),
                        new AdaptiveBrightnessWeightStats(
                                AdaptiveBrightnessStatsTracker.this.mBucketBoundariesForStats,
                                this.mBrightnessMapper));
            }
            return (AdaptiveBrightnessWeightStats) map.get(Integer.valueOf(i));
        }

        public final void getOrCreateUserStats(
                Map map,
                AdaptiveBrightnessWeightStats.BrightnessWeights[] brightnessWeightsArr,
                int i) {
            HashMap hashMap = (HashMap) map;
            if (!hashMap.containsKey(Integer.valueOf(i))) {
                hashMap.put(
                        Integer.valueOf(i),
                        new AdaptiveBrightnessWeightStats(
                                AdaptiveBrightnessStatsTracker.this.mBucketBoundariesForStats,
                                brightnessWeightsArr,
                                this.mBrightnessMapper));
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:26:0x0054, code lost:

           if (r14 != 4) goto L25;
        */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x005d, code lost:

           r6 = r4.getName();
        */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x006a, code lost:

           if ("fixed-lux-zone".equals(r6) == false) goto L37;
        */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x006c, code lost:

           r14 = r4.getAttributeValue(r9, "user");
           r8 = r4.getAttributeValue(r9, "bucket-boundaries").split(",");
        */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0080, code lost:

           if (r8 == null) goto L37;
        */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0082, code lost:

           r8 = r8.length;
           r9 = r2.mInjector;
           r12 = r2.mUserManager;
           r13 = java.lang.Integer.parseInt(r14);
           r9.getClass();
           r9 = r12.getUserHandle(r13);
           android.util.Slog.d("AdaptiveBrightnessStatsTracker", "TAG_LUX_STATS [" + r9 + r1 + r8 + r1 + r2.mBucketBoundariesForStats.length + "]");
        */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x00ba, code lost:

           if (r8 == 0) goto L36;
        */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x00bc, code lost:

           if (r9 == r7) goto L36;
        */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x00c1, code lost:

           if (r8 != r2.mBucketBoundariesForStats.length) goto L36;
        */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x00c3, code lost:

           r10 = new com.android.server.display.AdaptiveBrightnessWeightStats.BrightnessWeights[r8];
           r12 = r8;
           r13 = r9;
           r11 = 0;
        */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00c9, code lost:

           r12 = r8;
           r13 = r9;
        */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00d1, code lost:

           if ("adaptive-brightness-weight-stats".equals(r6) == false) goto L43;
        */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00d3, code lost:

           r8 = r4.getAttributeValue(null, "lux");
           r14 = r4.getAttributeValue(null, "brightness-learned");
           r7 = r4.getAttributeValue(null, "weight");
           r16 = r1;
           r1 = r4.getAttributeValue(null, "last-user-brighntess-time");
           r8 = java.lang.Float.parseFloat(r8);
           r14 = java.lang.Float.parseFloat(r14);
           r7 = java.lang.Float.parseFloat(r7);
           r23 = r10;
           r9 = java.lang.Long.parseLong(r1);
           r1 = new java.lang.StringBuilder();
           r24 = r2;
           r1.append("readFromXML: [");
           r1.append(r11);
           r1.append("] l = ");
           r1.append(r8);
           r1.append(", br = ");
           r1.append(r14);
           r1.append(", w = ");
           r1.append(r7);
           r1.append(", t = ");
           r1.append(r9);
           android.util.Slog.d("AdaptiveBrightnessStatsTracker", r1.toString());
        */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x013b, code lost:

           if (r23 == null) goto L44;
        */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x013d, code lost:

           if (r11 >= r12) goto L44;
        */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x013f, code lost:

           r23[r11] = new com.android.server.display.AdaptiveBrightnessWeightStats.BrightnessWeights(r8, r14, r7, r9);
           r11 = r11 + 1;
        */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x0160, code lost:

           if ("END".equals(r6) == false) goto L51;
        */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x0162, code lost:

           r1 = -1;
        */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x0163, code lost:

           if (r13 == (-1)) goto L50;
        */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0165, code lost:

           if (r23 == null) goto L50;
        */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x0167, code lost:

           android.util.Slog.d("AdaptiveBrightnessStatsTracker", "getOrCreateUserStats from xml");
           r10 = r23;
           getOrCreateUserStats(r3, r10, r13);
        */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x0173, code lost:

           r10 = r23;
        */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x0176, code lost:

           r10 = r23;
           r1 = -1;
        */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x0154, code lost:

           r16 = r1;
           r24 = r2;
           r23 = r10;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void readFromXML(java.io.InputStream r26) {
            /*
                Method dump skipped, instructions count: 420
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.AdaptiveBrightnessStatsTracker.AdaptiveBrightnessStats.readFromXML(java.io.InputStream):void");
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry entry : this.mStats.entrySet()) {
                AdaptiveBrightnessWeightStats adaptiveBrightnessWeightStats =
                        (AdaptiveBrightnessWeightStats) entry.getValue();
                sb.append("  ");
                sb.append(entry.getKey());
                sb.append(" ");
                sb.append(adaptiveBrightnessWeightStats);
                sb.append("\n");
            }
            return sb.toString();
        }

        public final void writeToXML(OutputStream outputStream) {
            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fastXmlSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
            fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
            fastXmlSerializer.setFeature(
                    "http://xmlpull.org/v1/doc/features.html#indent-output", true);
            Slog.d("AdaptiveBrightnessStatsTracker", "writeToXML start");
            fastXmlSerializer.startTag((String) null, "SBS-stats");
            for (Map.Entry entry : this.mStats.entrySet()) {
                AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker =
                        AdaptiveBrightnessStatsTracker.this;
                Injector injector = adaptiveBrightnessStatsTracker.mInjector;
                UserManager userManager = adaptiveBrightnessStatsTracker.mUserManager;
                int intValue = ((Integer) entry.getKey()).intValue();
                injector.getClass();
                int userSerialNumber = userManager.getUserSerialNumber(intValue);
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        userSerialNumber, "userSerialNumber = ", "AdaptiveBrightnessStatsTracker");
                if (userSerialNumber != -1) {
                    AdaptiveBrightnessWeightStats adaptiveBrightnessWeightStats =
                            (AdaptiveBrightnessWeightStats)
                                    this.mStats.get(Integer.valueOf(userSerialNumber));
                    fastXmlSerializer.startTag((String) null, "fixed-lux-zone");
                    fastXmlSerializer.attribute(
                            (String) null, "user", Integer.toString(userSerialNumber));
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0;
                            i < adaptiveBrightnessWeightStats.getBucketBoundaries().length;
                            i++) {
                        if (i > 0) {
                            sb.append(",");
                        }
                        sb.append(adaptiveBrightnessWeightStats.getBucketBoundaries()[i]);
                    }
                    fastXmlSerializer.attribute((String) null, "bucket-boundaries", sb.toString());
                    fastXmlSerializer.endTag((String) null, "fixed-lux-zone");
                    for (int i2 = 0; i2 < adaptiveBrightnessWeightStats.getStats().length; i2++) {
                        StringBuilder sb2 = new StringBuilder();
                        StringBuilder sb3 = new StringBuilder();
                        StringBuilder sb4 = new StringBuilder();
                        StringBuilder sb5 = new StringBuilder();
                        fastXmlSerializer.startTag(
                                (String) null, "adaptive-brightness-weight-stats");
                        sb2.append(adaptiveBrightnessWeightStats.getStats()[i2].getLux());
                        sb3.append(adaptiveBrightnessWeightStats.getStats()[i2].getBrightness());
                        sb4.append(adaptiveBrightnessWeightStats.getStats()[i2].getWeight());
                        sb5.append(
                                adaptiveBrightnessWeightStats.getStats()[i2]
                                        .getLastUserBrightnessTime());
                        fastXmlSerializer.attribute((String) null, "lux", sb2.toString());
                        fastXmlSerializer.attribute(
                                (String) null, "brightness-learned", sb3.toString());
                        fastXmlSerializer.attribute((String) null, "weight", sb4.toString());
                        fastXmlSerializer.attribute(
                                (String) null, "last-user-brighntess-time", sb5.toString());
                        Slog.d(
                                "AdaptiveBrightnessStatsTracker",
                                "writeToXML: ["
                                        + i2
                                        + "] l="
                                        + sb2.toString()
                                        + ", br="
                                        + sb3.toString()
                                        + ", w="
                                        + sb4.toString()
                                        + ", t="
                                        + sb5.toString());
                        fastXmlSerializer.endTag((String) null, "adaptive-brightness-weight-stats");
                    }
                    fastXmlSerializer.startTag((String) null, "END");
                    fastXmlSerializer.endTag((String) null, "END");
                }
            }
            fastXmlSerializer.endTag((String) null, "SBS-stats");
            fastXmlSerializer.endDocument();
            Slog.d("AdaptiveBrightnessStatsTracker", "writeToXML done");
            outputStream.flush();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface Clock {
        long elapsedTimeMillis();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Timer {
        public final Clock clock;
        public boolean paused;
        public long startTimeMillis;
        public boolean started;
        public float totalDurationSec;

        public Timer(
                AdaptiveBrightnessStatsTracker$$ExternalSyntheticLambda0
                        adaptiveBrightnessStatsTracker$$ExternalSyntheticLambda0) {
            this.clock = adaptiveBrightnessStatsTracker$$ExternalSyntheticLambda0;
        }

        public final float totalDurationSec() {
            if (!this.started) {
                return FullScreenMagnificationGestureHandler.MAX_SCALE;
            }
            if (!this.paused) {
                float f = this.totalDurationSec;
                Clock clock = this.clock;
                this.totalDurationSec =
                        f
                                + ((float)
                                        ((clock.elapsedTimeMillis() - this.startTimeMillis)
                                                / 1000.0d));
                this.startTimeMillis = clock.elapsedTimeMillis();
            }
            return this.totalDurationSec;
        }
    }

    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.server.display.AdaptiveBrightnessStatsTracker$$ExternalSyntheticLambda0] */
    public AdaptiveBrightnessStatsTracker(
            UserManager userManager, BrightnessMappingStrategy brightnessMappingStrategy) {
        int i;
        this.mUserManager = userManager;
        this.mAdaptiveBrightnessStats = new AdaptiveBrightnessStats(brightnessMappingStrategy);
        float[] fArr = {
            FullScreenMagnificationGestureHandler.MAX_SCALE,
            1.0f,
            2.0f,
            3.0f,
            4.0f,
            5.0f,
            6.0f,
            7.0f,
            9.0f,
            10.0f,
            12.0f,
            15.0f,
            18.0f,
            21.0f,
            26.0f,
            31.0f,
            37.0f,
            45.0f,
            53.0f,
            64.0f,
            77.0f,
            92.0f,
            111.0f,
            133.0f,
            160.0f,
            192.0f,
            230.0f,
            276.0f,
            331.0f,
            397.0f,
            477.0f,
            572.0f,
            687.0f,
            824.0f,
            989.0f,
            1187.0f,
            1424.0f,
            1709.0f,
            2051.0f,
            2461.0f,
            2953.0f,
            3544.0f,
            4253.0f,
            5103.0f,
            6124.0f,
            7349.0f,
            8819.0f,
            10582.0f,
            12699.0f,
            15239.0f,
            18286.0f,
            21944.0f,
            26332.0f,
            31599.0f,
            37918.0f,
            45502.0f
        };
        if (PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM) {
            i = 0;
            while (i < 56 && fArr[i] <= PowerManagerUtil.HBM_LUX) {
                i++;
            }
        } else {
            i = 55;
        }
        this.mBucketBoundariesForStats = Arrays.copyOf(fArr, i);
    }
}
