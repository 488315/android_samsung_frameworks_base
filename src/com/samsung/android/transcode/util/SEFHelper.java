package com.samsung.android.transcode.util;

import android.app.job.JobInfo;
import android.content.Context;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

/* loaded from: classes6.dex */
public class SEFHelper {
    public static final String SLOW_MOTION_DATA = "SlowMotion_Data";
    public static final String SUPER_SLOW_MOTION_DATA = "Super_SlowMotion_Data";
    private int mRecordingFps;
    private String mSEFData;
    private String mFilepath = null;
    private Context mContext = null;
    private Uri mUri = null;
    private int mRecordingMode = 0;
    private List<Region> mRegionList = new Vector();
    private long mDuration = 0;

    public static class Region {
        public int mRegionAudioEndTime;
        public int mRegionEndTime;
        public int mRegionSpeed;
        public Speed mRegionSpeedType = Speed.NORMAL;
        public int mRegionStartTime;
    }

    public static boolean isSEFVideoMode(int i) {
        return i == 1 || i == 2 || i == 7 || i == 8 || i == 9 || i == 12 || i == 13 || i == 15 || i == 18 || i == 19 || i == 21 || i == 22;
    }

    public void initialize(String str, Context context, Uri uri) throws IOException {
        if (str != null) {
            this.mFilepath = str;
        } else if (context != null && uri != null) {
            this.mContext = context;
            this.mUri = uri;
        } else {
            LogS.d("TranscodeLib", "SEFHelper Initialize failed!");
            throw new IOException("input file path cannot be null.");
        }
    }

    public enum Speed {
        NORMAL(1),
        HALF(2),
        ONE_FOURTH(3),
        ONE_EIGHTH(4),
        TWO_TIMES(5),
        FOUR_TIMES(6),
        EIGHT_TIMES(7),
        SIXTEEN_TIMES(8),
        THIRTY_TWO_TIMES(9);

        final int value;

        Speed(int i) {
            this.value = i;
        }
    }

    /* renamed from: com.samsung.android.transcode.util.SEFHelper$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed;

        static {
            int[] iArr = new int[Speed.values().length];
            $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed = iArr;
            try {
                iArr[Speed.ONE_FOURTH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.HALF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.NORMAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.ONE_EIGHTH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.TWO_TIMES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.FOUR_TIMES.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.EIGHT_TIMES.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.SIXTEEN_TIMES.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.THIRTY_TWO_TIMES.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public static float getTimeScale(Speed speed) {
        switch (AnonymousClass1.$SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[speed.ordinal()]) {
        }
        return 1.0f;
    }

    public static Speed getSpeed(int i) {
        switch (i) {
            case 1:
                return Speed.NORMAL;
            case 2:
                return Speed.HALF;
            case 3:
                return Speed.ONE_FOURTH;
            case 4:
                return Speed.ONE_EIGHTH;
            case 5:
                return Speed.TWO_TIMES;
            case 6:
                return Speed.FOUR_TIMES;
            case 7:
                return Speed.EIGHT_TIMES;
            case 8:
                return Speed.SIXTEEN_TIMES;
            case 9:
                return Speed.THIRTY_TWO_TIMES;
            default:
                return Speed.NORMAL;
        }
    }

    public boolean checkSEFData(int i, int i2, long j) {
        boolean slowfastSEFParser;
        this.mSEFData = null;
        this.mRegionList.clear();
        this.mRecordingMode = i;
        this.mRecordingFps = i2;
        this.mDuration = j;
        String extractSEFData = extractSEFData();
        this.mSEFData = extractSEFData;
        if (extractSEFData == null) {
            LogS.d("TranscodeLib", "extractSEFData : SEFData == null, createDefaultRegion");
            slowfastSEFParser = createDefaultRegion();
        } else {
            int i3 = this.mRecordingMode;
            if (i3 == 2 || i3 == 1) {
                slowfastSEFParser = slowfastSEFParser(extractSEFData);
            } else if (i3 == 8 || i3 == 7 || i3 == 9 || i3 == 22 || i3 == 18) {
                slowfastSEFParser = superslowSEFParser(extractSEFData);
            } else if (i3 == 12 || ((i3 == 21 && this.mRecordingFps > 120) || i3 == 19)) {
                slowfastSEFParser = newslowSEFParser(extractSEFData);
            } else {
                slowfastSEFParser = (i3 == 13 || i3 == 15 || i3 == 21) ? newslowSEFParserV2(extractSEFData) : true;
            }
        }
        if (!slowfastSEFParser) {
            this.mRegionList.clear();
        }
        return slowfastSEFParser;
    }

    public String extractSEFData(int i, long j) {
        this.mRecordingMode = i;
        this.mDuration = j;
        return extractSEFData();
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x007e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007f A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String extractSEFData() {
        /*
            r4 = this;
            android.net.Uri r0 = r4.mUri
            java.lang.String r1 = "TranscodeLib"
            r2 = 0
            if (r0 == 0) goto L1b
            android.content.Context r3 = r4.mContext
            java.lang.String r0 = com.samsung.android.transcode.util.FileHelper.getVEEditFilePath(r3, r0)
            if (r0 != 0) goto L15
            java.lang.String r4 = "filepath is Wrong"
            com.samsung.android.transcode.util.LogS.d(r1, r4)
            return r2
        L15:
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            goto L2c
        L1b:
            java.lang.String r0 = r4.mFilepath
            if (r0 != 0) goto L25
            java.lang.String r4 = "filepath is NULL"
            com.samsung.android.transcode.util.LogS.d(r1, r4)
            return r2
        L25:
            java.io.File r1 = new java.io.File
            java.lang.String r0 = r4.mFilepath
            r1.<init>(r0)
        L2c:
            boolean r0 = com.samsung.android.media.SemExtendedFormat.isValidFile(r1)     // Catch: java.lang.Exception -> L81
            if (r0 == 0) goto L80
            int r0 = r4.mRecordingMode     // Catch: java.lang.Exception -> L81
            r3 = 1
            if (r0 == r3) goto L6b
            r3 = 2
            if (r0 == r3) goto L6b
            r3 = 12
            if (r0 == r3) goto L6b
            r3 = 21
            if (r0 == r3) goto L6b
            r3 = 13
            if (r0 == r3) goto L6b
            r3 = 15
            if (r0 == r3) goto L6b
            r3 = 19
            if (r0 != r3) goto L4f
            goto L6b
        L4f:
            r3 = 8
            if (r0 == r3) goto L64
            r3 = 7
            if (r0 == r3) goto L64
            r3 = 9
            if (r0 == r3) goto L64
            r3 = 22
            if (r0 == r3) goto L64
            r3 = 18
            if (r0 != r3) goto L63
            goto L64
        L63:
            return r2
        L64:
            java.lang.String r0 = "Super_SlowMotion_Data"
            byte[] r0 = com.samsung.android.media.SemExtendedFormat.getData(r1, r0)     // Catch: java.lang.Exception -> L81
            goto L71
        L6b:
            java.lang.String r0 = "SlowMotion_Data"
            byte[] r0 = com.samsung.android.media.SemExtendedFormat.getData(r1, r0)     // Catch: java.lang.Exception -> L81
        L71:
            java.lang.String r1 = new java.lang.String     // Catch: java.lang.Exception -> L81
            java.nio.charset.Charset r3 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Exception -> L81
            r1.<init>(r0, r3)     // Catch: java.lang.Exception -> L81
            boolean r4 = r4.checkValidSEFData(r1)     // Catch: java.lang.Exception -> L81
            if (r4 != 0) goto L7f
            return r2
        L7f:
            return r1
        L80:
            return r2
        L81:
            r4 = move-exception
            r4.printStackTrace()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.transcode.util.SEFHelper.extractSEFData():java.lang.String");
    }

    public List<Region> getRegionList() {
        return this.mRegionList;
    }

    private boolean createDefaultRegion() {
        MediaExtractor mediaExtractor = null;
        try {
            try {
                Uri uri = this.mUri;
                if (uri != null) {
                    mediaExtractor = CodecsHelper.createExtractor(this.mContext, uri);
                } else {
                    mediaExtractor = CodecsHelper.createExtractor(this.mFilepath);
                }
                long j = mediaExtractor.getTrackFormat(CodecsHelper.getAndSelectVideoTrackIndex(mediaExtractor)).getLong(MediaFormat.KEY_DURATION);
                if (mediaExtractor != null) {
                    mediaExtractor.release();
                }
                LogS.d("TranscodeLib", "createDefaultRegion duration:" + j);
                if (j <= 0) {
                    return false;
                }
                if (isSlowMotionV2()) {
                    if (is120fpsSlowMotionVideo()) {
                        j *= 2;
                    }
                    Region region = new Region();
                    region.mRegionStartTime = 0;
                    int i = (int) ((2 * j) / JobInfo.MIN_BACKOFF_MILLIS);
                    region.mRegionEndTime = i;
                    region.mRegionSpeedType = Speed.EIGHT_TIMES;
                    region.mRegionSpeed = region.mRegionSpeedType.value;
                    this.mRegionList.add(region);
                    if (is120fpsSlowMotionVideo()) {
                        Region region2 = new Region();
                        region2.mRegionStartTime = i;
                        region2.mRegionEndTime = (int) ((j * 8) / JobInfo.MIN_BACKOFF_MILLIS);
                        region2.mRegionSpeedType = Speed.TWO_TIMES;
                        region2.mRegionSpeed = region2.mRegionSpeedType.value;
                        this.mRegionList.add(region2);
                    }
                    Region region3 = new Region();
                    region3.mRegionStartTime = (int) ((8 * j) / JobInfo.MIN_BACKOFF_MILLIS);
                    region3.mRegionEndTime = (int) (j / 1000);
                    region3.mRegionSpeedType = Speed.EIGHT_TIMES;
                    region3.mRegionSpeed = region3.mRegionSpeedType.value;
                    this.mRegionList.add(region3);
                } else {
                    Region region4 = new Region();
                    region4.mRegionStartTime = (int) ((2 * j) / JobInfo.MIN_BACKOFF_MILLIS);
                    region4.mRegionEndTime = (int) ((j * 8) / JobInfo.MIN_BACKOFF_MILLIS);
                    int i2 = this.mRecordingMode;
                    if (i2 == 1) {
                        region4.mRegionSpeedType = Speed.ONE_EIGHTH;
                    } else if (i2 == 2) {
                        region4.mRegionSpeedType = Speed.EIGHT_TIMES;
                    }
                    region4.mRegionSpeed = region4.mRegionSpeedType.value;
                    this.mRegionList.add(region4);
                }
                for (int i3 = 0; i3 < this.mRegionList.size(); i3++) {
                    LogS.d("TranscodeLib", "Region List " + i3);
                    LogS.d("TranscodeLib", "Region regionStartTime " + this.mRegionList.get(i3).mRegionStartTime);
                    LogS.d("TranscodeLib", "Region regionEndTime " + this.mRegionList.get(i3).mRegionEndTime);
                    LogS.d("TranscodeLib", "Region regionSpeed " + this.mRegionList.get(i3).mRegionSpeed);
                    LogS.d("TranscodeLib", "Region regionSpeedType " + this.mRegionList.get(i3).mRegionSpeedType);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                if (mediaExtractor != null) {
                    mediaExtractor.release();
                }
                return false;
            }
        } catch (Throwable th) {
            if (mediaExtractor != null) {
                mediaExtractor.release();
            }
            throw th;
        }
    }

    public boolean isSEFRegion(long j, int i) {
        int i2;
        if (j < 0) {
            return false;
        }
        List<Region> list = this.mRegionList;
        if (list != null && !list.isEmpty()) {
            for (int i3 = 0; i3 < this.mRegionList.size(); i3++) {
                if (i == 1 || i == 2) {
                    i2 = this.mRegionList.get(i3).mRegionStartTime;
                } else {
                    i2 = this.mRegionList.get(i3).mRegionAudioEndTime;
                }
                long j2 = this.mRegionList.get(i3).mRegionEndTime * 1000;
                if (j >= i2 * 1000 && j < j2) {
                    return true;
                }
            }
            return false;
        }
        LogS.d("TranscodeLib", "There is no region info.");
        return false;
    }

    public boolean newslowSEFParser(String str) {
        try {
            LogS.d("TranscodeLib", "sefData read slow : " + str);
            if (str == null) {
                LogS.d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] split = str.split("\\*");
            LogS.d("TranscodeLib", "slowDataregion,length: " + split.length);
            if (split.length == 1) {
                String[] split2 = split[0].split(":");
                Region region = new Region();
                region.mRegionStartTime = 0;
                region.mRegionEndTime = Integer.parseInt(split2[0]);
                region.mRegionSpeed = 7;
                region.mRegionSpeedType = getSpeed(region.mRegionSpeed);
                this.mRegionList.add(region);
                if (Integer.parseInt(split2[2]) != 4) {
                    Region region2 = new Region();
                    region2.mRegionStartTime = Integer.parseInt(split2[0]);
                    region2.mRegionEndTime = Integer.parseInt(split2[1]);
                    if (Integer.parseInt(split2[2]) == 3) {
                        region2.mRegionSpeed = 5;
                    } else if (Integer.parseInt(split2[2]) == 2) {
                        region2.mRegionSpeed = 6;
                    } else {
                        LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(split2[2]));
                        region2.mRegionSpeed = 7;
                    }
                    region2.mRegionSpeedType = getSpeed(region2.mRegionSpeed);
                    this.mRegionList.add(region2);
                }
                Region region3 = new Region();
                region3.mRegionStartTime = Integer.parseInt(split2[1]);
                region3.mRegionEndTime = (int) this.mDuration;
                region3.mRegionSpeed = 7;
                region3.mRegionSpeedType = getSpeed(region3.mRegionSpeed);
                this.mRegionList.add(region3);
            } else if (split.length == 2) {
                String[] split3 = split[0].split(":");
                String[] split4 = split[1].split(":");
                Region region4 = new Region();
                region4.mRegionStartTime = 0;
                region4.mRegionEndTime = Integer.parseInt(split3[0]);
                region4.mRegionSpeed = 7;
                region4.mRegionSpeedType = getSpeed(region4.mRegionSpeed);
                this.mRegionList.add(region4);
                if (Integer.parseInt(split3[2]) != 4) {
                    Region region5 = new Region();
                    region5.mRegionStartTime = Integer.parseInt(split3[0]);
                    region5.mRegionEndTime = Integer.parseInt(split3[1]);
                    if (Integer.parseInt(split3[2]) == 3) {
                        region5.mRegionSpeed = 5;
                    } else if (Integer.parseInt(split3[2]) == 2) {
                        region5.mRegionSpeed = 6;
                    } else {
                        LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(split3[2]));
                        region5.mRegionSpeed = 7;
                    }
                    region5.mRegionSpeedType = getSpeed(region5.mRegionSpeed);
                    this.mRegionList.add(region5);
                }
                Region region6 = new Region();
                region6.mRegionStartTime = Integer.parseInt(split3[1]);
                region6.mRegionEndTime = Integer.parseInt(split4[0]);
                region6.mRegionSpeed = 7;
                region6.mRegionSpeedType = getSpeed(region6.mRegionSpeed);
                this.mRegionList.add(region6);
                if (Integer.parseInt(split4[2]) != 4) {
                    Region region7 = new Region();
                    region7.mRegionStartTime = Integer.parseInt(split4[0]);
                    region7.mRegionEndTime = Integer.parseInt(split4[1]);
                    if (Integer.parseInt(split4[2]) == 3) {
                        region7.mRegionSpeed = 5;
                    } else if (Integer.parseInt(split4[2]) == 2) {
                        region7.mRegionSpeed = 6;
                    } else {
                        LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(split4[2]));
                        region7.mRegionSpeed = 7;
                    }
                    region7.mRegionSpeedType = getSpeed(region7.mRegionSpeed);
                    this.mRegionList.add(region7);
                }
                Region region8 = new Region();
                region8.mRegionStartTime = Integer.parseInt(split4[1]);
                region8.mRegionEndTime = (int) this.mDuration;
                region8.mRegionSpeed = 7;
                region8.mRegionSpeedType = getSpeed(region8.mRegionSpeed);
                this.mRegionList.add(region8);
            } else {
                LogS.d("TranscodeLib", "There is not slowDataregion, length: " + split.length);
            }
            for (int i = 0; i < this.mRegionList.size(); i++) {
                LogS.d("TranscodeLib", "Region List " + i);
                LogS.d("TranscodeLib", "Region regionStartTime " + this.mRegionList.get(i).mRegionStartTime);
                LogS.d("TranscodeLib", "Region regionEndTime " + this.mRegionList.get(i).mRegionEndTime);
                LogS.d("TranscodeLib", "Region regionSpeed " + this.mRegionList.get(i).mRegionSpeed);
                LogS.d("TranscodeLib", "Region regionSpeedType " + this.mRegionList.get(i).mRegionSpeedType);
            }
            return true;
        } catch (NumberFormatException e) {
            LogS.d("TranscodeLib", "throwing number format:" + e);
            return false;
        }
    }

    public boolean slowfastSEFParser(String str) {
        try {
            LogS.d("TranscodeLib", "sefData read slow : " + str);
            if (str == null) {
                LogS.d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] split = str.split("\\*");
            LogS.d("TranscodeLib", "slowDataregion,length: " + split.length);
            for (int i = 0; i < split.length; i++) {
                String[] split2 = split[i].split(":");
                Region region = new Region();
                region.mRegionStartTime = Integer.parseInt(split2[0]);
                region.mRegionEndTime = Integer.parseInt(split2[1]);
                region.mRegionSpeed = Integer.parseInt(split2[2]);
                region.mRegionSpeedType = getSpeed(region.mRegionSpeed);
                this.mRegionList.add(region);
            }
            return true;
        } catch (NumberFormatException e) {
            LogS.d("TranscodeLib", "throwing number format:" + e);
            return false;
        }
    }

    public boolean superslowSEFParser(String str) {
        try {
            LogS.d("TranscodeLib", "sefData read super : " + str);
            if (str == null) {
                LogS.d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] split = str.split("\\*");
            LogS.d("TranscodeLib", "slowDataregion,length: " + split.length);
            for (int i = 0; i < split.length; i++) {
                String[] split2 = split[i].split(":");
                String[] split3 = split2[3].split("!");
                if (split3.length > 1) {
                    split2[3] = split3[0];
                }
                Region region = new Region();
                region.mRegionStartTime = Integer.parseInt(split2[0]);
                region.mRegionEndTime = Integer.parseInt(split2[1]);
                region.mRegionAudioEndTime = Integer.parseInt(split2[2]);
                region.mRegionSpeed = Integer.parseInt(split2[3]);
                region.mRegionSpeedType = getSpeed(region.mRegionSpeed);
                this.mRegionList.add(region);
            }
            return true;
        } catch (NumberFormatException e) {
            LogS.d("TranscodeLib", "throwing number format:" + e);
            return false;
        }
    }

    private boolean newslowSEFParserV2(String str) {
        try {
            LogS.d("TranscodeLib", "sefData read slow : " + str);
            if (str == null) {
                LogS.d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] split = str.split("\\*");
            LogS.d("TranscodeLib", "slowDataregion,length: " + split.length);
            if (split.length == 1) {
                String[] split2 = split[0].split(":");
                Region region = new Region();
                region.mRegionStartTime = 0;
                region.mRegionEndTime = Integer.parseInt(split2[0]) * 2;
                region.mRegionSpeed = 7;
                region.mRegionSpeedType = getSpeed(region.mRegionSpeed);
                this.mRegionList.add(region);
                Region region2 = new Region();
                region2.mRegionStartTime = Integer.parseInt(split2[0]) * 2;
                region2.mRegionEndTime = Integer.parseInt(split2[1]) * 2;
                if (Integer.parseInt(split2[2]) == 3) {
                    region2.mRegionSpeed = 5;
                } else if (Integer.parseInt(split2[2]) == 2) {
                    region2.mRegionSpeed = 6;
                } else if (Integer.parseInt(split2[2]) == 4) {
                    region2.mRegionSpeed = 1;
                } else {
                    LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(split2[2]));
                    region2.mRegionSpeed = 1;
                }
                region2.mRegionSpeedType = getSpeed(region2.mRegionSpeed);
                this.mRegionList.add(region2);
                Region region3 = new Region();
                region3.mRegionStartTime = Integer.parseInt(split2[1]) * 2;
                region3.mRegionEndTime = (int) this.mDuration;
                region3.mRegionSpeed = 7;
                region3.mRegionSpeedType = getSpeed(region3.mRegionSpeed);
                this.mRegionList.add(region3);
            } else if (split.length == 2) {
                String[] split3 = split[0].split(":");
                String[] split4 = split[1].split(":");
                Region region4 = new Region();
                region4.mRegionStartTime = 0;
                region4.mRegionEndTime = Integer.parseInt(split3[0]) * 2;
                region4.mRegionSpeed = 7;
                region4.mRegionSpeedType = getSpeed(region4.mRegionSpeed);
                this.mRegionList.add(region4);
                Region region5 = new Region();
                region5.mRegionStartTime = Integer.parseInt(split3[0]) * 2;
                region5.mRegionEndTime = Integer.parseInt(split3[1]) * 2;
                if (Integer.parseInt(split3[2]) == 3) {
                    region5.mRegionSpeed = 5;
                } else if (Integer.parseInt(split3[2]) == 2) {
                    region5.mRegionSpeed = 6;
                } else if (Integer.parseInt(split3[2]) == 4) {
                    region5.mRegionSpeed = 1;
                } else {
                    LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(split3[2]));
                    region5.mRegionSpeed = 1;
                }
                region5.mRegionSpeedType = getSpeed(region5.mRegionSpeed);
                this.mRegionList.add(region5);
                Region region6 = new Region();
                region6.mRegionStartTime = Integer.parseInt(split3[1]) * 2;
                region6.mRegionEndTime = Integer.parseInt(split4[0]) * 2;
                region6.mRegionSpeed = 7;
                region6.mRegionSpeedType = getSpeed(region6.mRegionSpeed);
                this.mRegionList.add(region6);
                Region region7 = new Region();
                region7.mRegionStartTime = Integer.parseInt(split4[0]) * 2;
                region7.mRegionEndTime = Integer.parseInt(split4[1]) * 2;
                if (Integer.parseInt(split4[2]) == 3) {
                    region7.mRegionSpeed = 5;
                } else if (Integer.parseInt(split4[2]) == 2) {
                    region7.mRegionSpeed = 6;
                } else if (Integer.parseInt(split4[2]) == 4) {
                    region7.mRegionSpeed = 1;
                } else {
                    LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(split4[2]));
                    region7.mRegionSpeed = 7;
                }
                region7.mRegionSpeedType = getSpeed(region7.mRegionSpeed);
                this.mRegionList.add(region7);
                Region region8 = new Region();
                region8.mRegionStartTime = Integer.parseInt(split4[1]) * 2;
                region8.mRegionEndTime = (int) this.mDuration;
                region8.mRegionSpeed = 7;
                region8.mRegionSpeedType = getSpeed(region8.mRegionSpeed);
                this.mRegionList.add(region8);
            } else {
                LogS.d("TranscodeLib", "There is not slowDataregion, length: " + split.length);
            }
            for (int i = 0; i < this.mRegionList.size(); i++) {
                LogS.d("TranscodeLib", "Region List " + i);
                LogS.d("TranscodeLib", "Region regionStartTime " + this.mRegionList.get(i).mRegionStartTime);
                LogS.d("TranscodeLib", "Region regionEndTime " + this.mRegionList.get(i).mRegionEndTime);
                LogS.d("TranscodeLib", "Region regionSpeed " + this.mRegionList.get(i).mRegionSpeed);
                LogS.d("TranscodeLib", "Region regionSpeedType " + this.mRegionList.get(i).mRegionSpeedType);
            }
            return true;
        } catch (NumberFormatException e) {
            LogS.d("TranscodeLib", "throwing number format:" + e);
            return false;
        }
    }

    private boolean is120fpsSlowMotionVideo() {
        int i = this.mRecordingMode;
        if (i == 13 || i == 15) {
            return true;
        }
        return i == 21 && this.mRecordingFps == 120;
    }

    private boolean isSlowMotionV2() {
        int i = this.mRecordingMode;
        return i == 13 || i == 15 || i == 12 || i == 21 || i == 19;
    }

    private boolean checkValidSEFData(String str) {
        if (str == null) {
            return false;
        }
        for (String str2 : str.split("\\*")) {
            String[] split = str2.split(":");
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            if (parseInt >= parseInt2) {
                LogS.d("TranscodeLib", "checkValidSEFData : startTime >= endTime");
                return false;
            }
            if (parseInt < 0 || parseInt2 > this.mDuration) {
                LogS.d("TranscodeLib", "checkValidSEFData : startTime < 0  or endTime > mDuration");
                return false;
            }
        }
        return true;
    }

    public long getEditedDuration(long j) {
        if (extractSEFData() == null) {
            LogS.d("TranscodeLib", "getEditedDuration : use original data");
            return j;
        }
        return getConvertedTime(j);
    }

    public long getConvertedTime(long j) {
        double d;
        if (is120fpsSlowMotionVideo()) {
            j *= 2;
        }
        List<Region> list = this.mRegionList;
        long j2 = 0;
        if (list == null || list.isEmpty()) {
            return 0L;
        }
        int i = 0;
        while (true) {
            if (i < this.mRegionList.size()) {
                if (j >= this.mRegionList.get(i).mRegionStartTime * 1000 && j < this.mRegionList.get(i).mRegionEndTime * 1000) {
                    j = (((j - (this.mRegionList.get(i).mRegionStartTime * 1000)) * ((long) (getTimeScale(this.mRegionList.get(i).mRegionSpeedType) * 1000000.0f))) / 1000000) + (this.mRegionList.get(i).mRegionStartTime * 1000);
                    break;
                }
                if (j >= this.mRegionList.get(i).mRegionEndTime * 1000) {
                    double timeScale = getTimeScale(this.mRegionList.get(i).mRegionSpeedType);
                    if (timeScale > 1.0d) {
                        d = j2 + ((timeScale - 1.0d) * (this.mRegionList.get(i).mRegionEndTime - this.mRegionList.get(i).mRegionStartTime) * 1000.0d);
                    } else {
                        d = j2 - (((1.0d - timeScale) * 1000.0d) * (this.mRegionList.get(i).mRegionEndTime - this.mRegionList.get(i).mRegionStartTime));
                    }
                    j2 = (long) d;
                }
                i++;
            } else {
                break;
            }
        }
        return j + j2;
    }
}
