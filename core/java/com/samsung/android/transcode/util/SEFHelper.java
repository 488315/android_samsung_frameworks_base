package com.samsung.android.transcode.util;

import android.app.job.JobInfo;
import android.content.Context;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Build;

import com.samsung.android.feature.SemFloatingFeature;

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

    public void initialize(String filepath, Context context, Uri uri) throws IOException {
        if (filepath != null) {
            this.mFilepath = filepath;
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

        Speed(int va) {
            this.value = va;
        }
    }

    public static float getTimeScale(Speed speedType) {
        switch (speedType) {
            case ONE_FOURTH:
                return 4.0f;
            case HALF:
                return 2.0f;
            case NORMAL:
                return 1.0f;
            case ONE_EIGHTH:
                return 8.0f;
            case TWO_TIMES:
                return 0.5f;
            case FOUR_TIMES:
                return 0.25f;
            case EIGHT_TIMES:
                return 0.125f;
            case SIXTEEN_TIMES:
                return 0.0625f;
            case THIRTY_TWO_TIMES:
                return 0.03125f;
            default:
                return 1.0f;
        }
    }

    public static Speed getSpeed(int speed) {
        switch (speed) {
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

    public static boolean isSEFVideoMode(int mode) {
        switch (mode) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
            case 15:
            case 21:
            case 22:
                return true;
            case 3:
            case 4:
            case 5:
            case 6:
            case 10:
            case 11:
            case 14:
            case 16:
            case 17:
            case 20:
            default:
                return false;
            case 18:
            case 19:
                return supportMTK_SSM();
        }
    }

    public boolean checkSEFData(int recordingMode, int recordingFps, long duration) {
        boolean ret = true;
        this.mSEFData = null;
        this.mRegionList.clear();
        this.mRecordingMode = recordingMode;
        this.mRecordingFps = recordingFps;
        this.mDuration = duration;
        this.mSEFData = extractSEFData();
        if (this.mSEFData == null) {
            LogS.d("TranscodeLib", "extractSEFData : SEFData == null, createDefaultRegion");
            ret = createDefaultRegion();
        } else if (this.mRecordingMode == 2 || this.mRecordingMode == 1) {
            ret = slowfastSEFParser(this.mSEFData);
        } else if (this.mRecordingMode == 8
                || this.mRecordingMode == 7
                || this.mRecordingMode == 9
                || this.mRecordingMode == 22
                || this.mRecordingMode == 18) {
            ret = superslowSEFParser(this.mSEFData);
        } else if (this.mRecordingMode == 12
                || ((this.mRecordingMode == 21 && this.mRecordingFps > 120)
                        || this.mRecordingMode == 19)) {
            ret = newslowSEFParser(this.mSEFData);
        } else if (this.mRecordingMode == 13
                || this.mRecordingMode == 15
                || this.mRecordingMode == 21) {
            ret = newslowSEFParserV2(this.mSEFData);
        }
        if (!ret) {
            this.mRegionList.clear();
        }
        return ret;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0097 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0098 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String extractSEFData() {
        /*
            r5 = this;
            android.net.Uri r0 = r5.mUri
            java.lang.String r1 = "TranscodeLib"
            r2 = 0
            if (r0 == 0) goto L1e
            android.content.Context r0 = r5.mContext
            android.net.Uri r3 = r5.mUri
            java.lang.String r0 = com.samsung.android.transcode.util.FileHelper.getVEEditFilePath(r0, r3)
            if (r0 != 0) goto L17
            java.lang.String r3 = "filepath is Wrong"
            com.samsung.android.transcode.util.LogS.d(r1, r3)
            return r2
        L17:
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            r0 = r1
            goto L2f
        L1e:
            java.lang.String r0 = r5.mFilepath
            if (r0 != 0) goto L28
            java.lang.String r0 = "filepath is NULL"
            com.samsung.android.transcode.util.LogS.d(r1, r0)
            return r2
        L28:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = r5.mFilepath
            r0.<init>(r1)
        L2f:
            boolean r1 = com.samsung.android.media.SemExtendedFormat.isValidFile(r0)     // Catch: java.lang.Exception -> L9b
            if (r1 == 0) goto L99
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 1
            if (r1 == r3) goto L84
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 2
            if (r1 == r3) goto L84
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 12
            if (r1 == r3) goto L84
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 21
            if (r1 == r3) goto L84
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 13
            if (r1 == r3) goto L84
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 15
            if (r1 == r3) goto L84
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 19
            if (r1 != r3) goto L5e
            goto L84
        L5e:
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 8
            if (r1 == r3) goto L7d
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 7
            if (r1 == r3) goto L7d
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 9
            if (r1 == r3) goto L7d
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 22
            if (r1 == r3) goto L7d
            int r1 = r5.mRecordingMode     // Catch: java.lang.Exception -> L9b
            r3 = 18
            if (r1 != r3) goto L7c
            goto L7d
        L7c:
            return r2
        L7d:
            java.lang.String r1 = "Super_SlowMotion_Data"
            byte[] r1 = com.samsung.android.media.SemExtendedFormat.getData(r0, r1)     // Catch: java.lang.Exception -> L9b
            goto L8a
        L84:
            java.lang.String r1 = "SlowMotion_Data"
            byte[] r1 = com.samsung.android.media.SemExtendedFormat.getData(r0, r1)     // Catch: java.lang.Exception -> L9b
        L8a:
            java.lang.String r3 = new java.lang.String     // Catch: java.lang.Exception -> L9b
            java.nio.charset.Charset r4 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Exception -> L9b
            r3.<init>(r1, r4)     // Catch: java.lang.Exception -> L9b
            boolean r4 = r5.checkValidSEFData(r3)     // Catch: java.lang.Exception -> L9b
            if (r4 != 0) goto L98
            return r2
        L98:
            return r3
        L99:
            return r2
        L9b:
            r1 = move-exception
            r1.printStackTrace()
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.transcode.util.SEFHelper.extractSEFData():java.lang.String");
    }

    public List<Region> getRegionList() {
        return this.mRegionList;
    }

    private boolean createDefaultRegion() {
        MediaExtractor me = null;
        try {
            try {
                me =
                        this.mUri != null
                                ? CodecsHelper.createExtractor(this.mContext, this.mUri)
                                : CodecsHelper.createExtractor(this.mFilepath);
                int videoTrack = CodecsHelper.getAndSelectVideoTrackIndex(me);
                MediaFormat inputFormat = me.getTrackFormat(videoTrack);
                long duration = inputFormat.getLong(MediaFormat.KEY_DURATION);
                if (me != null) {
                    me.release();
                }
                LogS.d("TranscodeLib", "createDefaultRegion duration:" + duration);
                if (duration <= 0) {
                    return false;
                }
                if (isSlowMotionV2()) {
                    if (is120fpsSlowMotionVideo()) {
                        duration *= 2;
                    }
                    Region slowRegion = new Region();
                    slowRegion.mRegionStartTime = 0;
                    slowRegion.mRegionEndTime = (int) ((duration * 2) / JobInfo.MIN_BACKOFF_MILLIS);
                    slowRegion.mRegionSpeedType = Speed.EIGHT_TIMES;
                    slowRegion.mRegionSpeed = slowRegion.mRegionSpeedType.value;
                    this.mRegionList.add(slowRegion);
                    if (is120fpsSlowMotionVideo()) {
                        Region slowRegion2 = new Region();
                        slowRegion2.mRegionStartTime =
                                (int) ((2 * duration) / JobInfo.MIN_BACKOFF_MILLIS);
                        slowRegion2.mRegionEndTime =
                                (int) ((duration * 8) / JobInfo.MIN_BACKOFF_MILLIS);
                        slowRegion2.mRegionSpeedType = Speed.TWO_TIMES;
                        slowRegion2.mRegionSpeed = slowRegion2.mRegionSpeedType.value;
                        this.mRegionList.add(slowRegion2);
                    }
                    Region slowRegion3 = new Region();
                    slowRegion3.mRegionStartTime =
                            (int) ((8 * duration) / JobInfo.MIN_BACKOFF_MILLIS);
                    slowRegion3.mRegionEndTime = (int) (duration / 1000);
                    slowRegion3.mRegionSpeedType = Speed.EIGHT_TIMES;
                    slowRegion3.mRegionSpeed = slowRegion3.mRegionSpeedType.value;
                    this.mRegionList.add(slowRegion3);
                } else {
                    Region slowRegion4 = new Region();
                    slowRegion4.mRegionStartTime =
                            (int) ((2 * duration) / JobInfo.MIN_BACKOFF_MILLIS);
                    slowRegion4.mRegionEndTime =
                            (int) ((8 * duration) / JobInfo.MIN_BACKOFF_MILLIS);
                    if (this.mRecordingMode == 1) {
                        slowRegion4.mRegionSpeedType = Speed.ONE_EIGHTH;
                    } else if (this.mRecordingMode == 2) {
                        slowRegion4.mRegionSpeedType = Speed.EIGHT_TIMES;
                    }
                    slowRegion4.mRegionSpeed = slowRegion4.mRegionSpeedType.value;
                    this.mRegionList.add(slowRegion4);
                }
                for (int i = 0; i < this.mRegionList.size(); i++) {
                    LogS.d("TranscodeLib", "Region List " + i);
                    LogS.d(
                            "TranscodeLib",
                            "Region regionStartTime " + this.mRegionList.get(i).mRegionStartTime);
                    LogS.d(
                            "TranscodeLib",
                            "Region regionEndTime " + this.mRegionList.get(i).mRegionEndTime);
                    LogS.d(
                            "TranscodeLib",
                            "Region regionSpeed " + this.mRegionList.get(i).mRegionSpeed);
                    LogS.d(
                            "TranscodeLib",
                            "Region regionSpeedType " + this.mRegionList.get(i).mRegionSpeedType);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                if (me != null) {
                    me.release();
                }
                return false;
            }
        } catch (Throwable th) {
            if (me != null) {
                me.release();
            }
            throw th;
        }
    }

    public boolean isSEFRegion(long TimeUs, int recordingmode) {
        long regStartTime;
        if (TimeUs < 0) {
            return false;
        }
        if (this.mRegionList != null && !this.mRegionList.isEmpty()) {
            for (int i = 0; i < this.mRegionList.size() && 0 == 0; i++) {
                if (recordingmode == 1 || recordingmode == 2) {
                    regStartTime = this.mRegionList.get(i).mRegionStartTime * 1000;
                } else {
                    regStartTime = this.mRegionList.get(i).mRegionAudioEndTime * 1000;
                }
                long regEndTime = this.mRegionList.get(i).mRegionEndTime * 1000;
                if (TimeUs >= regStartTime && TimeUs < regEndTime) {
                    return true;
                }
            }
            return false;
        }
        LogS.d("TranscodeLib", "There is no region info.");
        return false;
    }

    public boolean newslowSEFParser(String sefData) {
        try {
            LogS.d("TranscodeLib", "sefData read slow : " + sefData);
            if (sefData == null) {
                LogS.d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] slowDataregion = sefData.split("\\*");
            LogS.d("TranscodeLib", "slowDataregion,length: " + slowDataregion.length);
            if (slowDataregion.length == 1) {
                String[] regionData = slowDataregion[0].split(":");
                Region slowRegion = new Region();
                slowRegion.mRegionStartTime = 0;
                slowRegion.mRegionEndTime = Integer.parseInt(regionData[0]);
                slowRegion.mRegionSpeed = 7;
                slowRegion.mRegionSpeedType = getSpeed(slowRegion.mRegionSpeed);
                this.mRegionList.add(slowRegion);
                if (Integer.parseInt(regionData[2]) != 4) {
                    Region slowRegion2 = new Region();
                    slowRegion2.mRegionStartTime = Integer.parseInt(regionData[0]);
                    slowRegion2.mRegionEndTime = Integer.parseInt(regionData[1]);
                    if (Integer.parseInt(regionData[2]) == 3) {
                        slowRegion2.mRegionSpeed = 5;
                    } else if (Integer.parseInt(regionData[2]) == 2) {
                        slowRegion2.mRegionSpeed = 6;
                    } else {
                        LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(regionData[2]));
                        slowRegion2.mRegionSpeed = 7;
                    }
                    slowRegion2.mRegionSpeedType = getSpeed(slowRegion2.mRegionSpeed);
                    this.mRegionList.add(slowRegion2);
                }
                Region slowRegion3 = new Region();
                slowRegion3.mRegionStartTime = Integer.parseInt(regionData[1]);
                slowRegion3.mRegionEndTime = (int) this.mDuration;
                slowRegion3.mRegionSpeed = 7;
                slowRegion3.mRegionSpeedType = getSpeed(slowRegion3.mRegionSpeed);
                this.mRegionList.add(slowRegion3);
            } else if (slowDataregion.length == 2) {
                String[] regionData1 = slowDataregion[0].split(":");
                String[] regionData2 = slowDataregion[1].split(":");
                Region slowRegion4 = new Region();
                slowRegion4.mRegionStartTime = 0;
                slowRegion4.mRegionEndTime = Integer.parseInt(regionData1[0]);
                slowRegion4.mRegionSpeed = 7;
                slowRegion4.mRegionSpeedType = getSpeed(slowRegion4.mRegionSpeed);
                this.mRegionList.add(slowRegion4);
                if (Integer.parseInt(regionData1[2]) != 4) {
                    Region slowRegion5 = new Region();
                    slowRegion5.mRegionStartTime = Integer.parseInt(regionData1[0]);
                    slowRegion5.mRegionEndTime = Integer.parseInt(regionData1[1]);
                    if (Integer.parseInt(regionData1[2]) == 3) {
                        slowRegion5.mRegionSpeed = 5;
                    } else if (Integer.parseInt(regionData1[2]) == 2) {
                        slowRegion5.mRegionSpeed = 6;
                    } else {
                        LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(regionData1[2]));
                        slowRegion5.mRegionSpeed = 7;
                    }
                    slowRegion5.mRegionSpeedType = getSpeed(slowRegion5.mRegionSpeed);
                    this.mRegionList.add(slowRegion5);
                }
                Region slowRegion6 = new Region();
                slowRegion6.mRegionStartTime = Integer.parseInt(regionData1[1]);
                slowRegion6.mRegionEndTime = Integer.parseInt(regionData2[0]);
                slowRegion6.mRegionSpeed = 7;
                slowRegion6.mRegionSpeedType = getSpeed(slowRegion6.mRegionSpeed);
                this.mRegionList.add(slowRegion6);
                if (Integer.parseInt(regionData2[2]) != 4) {
                    Region slowRegion7 = new Region();
                    slowRegion7.mRegionStartTime = Integer.parseInt(regionData2[0]);
                    slowRegion7.mRegionEndTime = Integer.parseInt(regionData2[1]);
                    if (Integer.parseInt(regionData2[2]) == 3) {
                        slowRegion7.mRegionSpeed = 5;
                    } else if (Integer.parseInt(regionData2[2]) == 2) {
                        slowRegion7.mRegionSpeed = 6;
                    } else {
                        LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(regionData2[2]));
                        slowRegion7.mRegionSpeed = 7;
                    }
                    slowRegion7.mRegionSpeedType = getSpeed(slowRegion7.mRegionSpeed);
                    this.mRegionList.add(slowRegion7);
                }
                Region slowRegion8 = new Region();
                slowRegion8.mRegionStartTime = Integer.parseInt(regionData2[1]);
                slowRegion8.mRegionEndTime = (int) this.mDuration;
                slowRegion8.mRegionSpeed = 7;
                slowRegion8.mRegionSpeedType = getSpeed(slowRegion8.mRegionSpeed);
                this.mRegionList.add(slowRegion8);
            } else {
                LogS.d(
                        "TranscodeLib",
                        "There is not slowDataregion, length: " + slowDataregion.length);
            }
            for (int i = 0; i < this.mRegionList.size(); i++) {
                LogS.d("TranscodeLib", "Region List " + i);
                LogS.d(
                        "TranscodeLib",
                        "Region regionStartTime " + this.mRegionList.get(i).mRegionStartTime);
                LogS.d(
                        "TranscodeLib",
                        "Region regionEndTime " + this.mRegionList.get(i).mRegionEndTime);
                LogS.d(
                        "TranscodeLib",
                        "Region regionSpeed " + this.mRegionList.get(i).mRegionSpeed);
                LogS.d(
                        "TranscodeLib",
                        "Region regionSpeedType " + this.mRegionList.get(i).mRegionSpeedType);
            }
            return true;
        } catch (NumberFormatException nfe) {
            LogS.d("TranscodeLib", "throwing number format:" + nfe);
            return false;
        }
    }

    public boolean slowfastSEFParser(String sefData) {
        try {
            LogS.d("TranscodeLib", "sefData read slow : " + sefData);
            if (sefData == null) {
                LogS.d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] slowDataregion = sefData.split("\\*");
            LogS.d("TranscodeLib", "slowDataregion,length: " + slowDataregion.length);
            for (String str : slowDataregion) {
                String[] regionData = str.split(":");
                Region slowRegion = new Region();
                slowRegion.mRegionStartTime = Integer.parseInt(regionData[0]);
                slowRegion.mRegionEndTime = Integer.parseInt(regionData[1]);
                slowRegion.mRegionSpeed = Integer.parseInt(regionData[2]);
                slowRegion.mRegionSpeedType = getSpeed(slowRegion.mRegionSpeed);
                this.mRegionList.add(slowRegion);
            }
            return true;
        } catch (NumberFormatException nfe) {
            LogS.d("TranscodeLib", "throwing number format:" + nfe);
            return false;
        }
    }

    public boolean superslowSEFParser(String sefData) {
        try {
            LogS.d("TranscodeLib", "sefData read super : " + sefData);
            if (sefData == null) {
                LogS.d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] slowDataregion = sefData.split("\\*");
            LogS.d("TranscodeLib", "slowDataregion,length: " + slowDataregion.length);
            for (String str : slowDataregion) {
                String[] regionData = str.split(":");
                String[] mainData = regionData[3].split("!");
                if (mainData.length > 1) {
                    regionData[3] = mainData[0];
                }
                Region slowRegion = new Region();
                slowRegion.mRegionStartTime = Integer.parseInt(regionData[0]);
                slowRegion.mRegionEndTime = Integer.parseInt(regionData[1]);
                slowRegion.mRegionAudioEndTime = Integer.parseInt(regionData[2]);
                slowRegion.mRegionSpeed = Integer.parseInt(regionData[3]);
                slowRegion.mRegionSpeedType = getSpeed(slowRegion.mRegionSpeed);
                this.mRegionList.add(slowRegion);
            }
            return true;
        } catch (NumberFormatException nfe) {
            LogS.d("TranscodeLib", "throwing number format:" + nfe);
            return false;
        }
    }

    private boolean newslowSEFParserV2(String sefData) {
        try {
            LogS.d("TranscodeLib", "sefData read slow : " + sefData);
            if (sefData == null) {
                LogS.d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] slowDataregion = sefData.split("\\*");
            LogS.d("TranscodeLib", "slowDataregion,length: " + slowDataregion.length);
            if (slowDataregion.length == 1) {
                String[] regionData = slowDataregion[0].split(":");
                Region slowRegion = new Region();
                slowRegion.mRegionStartTime = 0;
                slowRegion.mRegionEndTime = Integer.parseInt(regionData[0]) * 2;
                slowRegion.mRegionSpeed = 7;
                slowRegion.mRegionSpeedType = getSpeed(slowRegion.mRegionSpeed);
                this.mRegionList.add(slowRegion);
                Region slowRegion2 = new Region();
                slowRegion2.mRegionStartTime = Integer.parseInt(regionData[0]) * 2;
                slowRegion2.mRegionEndTime = Integer.parseInt(regionData[1]) * 2;
                if (Integer.parseInt(regionData[2]) == 3) {
                    slowRegion2.mRegionSpeed = 5;
                } else if (Integer.parseInt(regionData[2]) == 2) {
                    slowRegion2.mRegionSpeed = 6;
                } else if (Integer.parseInt(regionData[2]) == 4) {
                    slowRegion2.mRegionSpeed = 1;
                } else {
                    LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(regionData[2]));
                    slowRegion2.mRegionSpeed = 1;
                }
                slowRegion2.mRegionSpeedType = getSpeed(slowRegion2.mRegionSpeed);
                this.mRegionList.add(slowRegion2);
                Region slowRegion3 = new Region();
                slowRegion3.mRegionStartTime = Integer.parseInt(regionData[1]) * 2;
                slowRegion3.mRegionEndTime = (int) this.mDuration;
                slowRegion3.mRegionSpeed = 7;
                slowRegion3.mRegionSpeedType = getSpeed(slowRegion3.mRegionSpeed);
                this.mRegionList.add(slowRegion3);
            } else if (slowDataregion.length == 2) {
                String[] regionData1 = slowDataregion[0].split(":");
                String[] regionData2 = slowDataregion[1].split(":");
                Region slowRegion4 = new Region();
                slowRegion4.mRegionStartTime = 0;
                slowRegion4.mRegionEndTime = Integer.parseInt(regionData1[0]) * 2;
                slowRegion4.mRegionSpeed = 7;
                slowRegion4.mRegionSpeedType = getSpeed(slowRegion4.mRegionSpeed);
                this.mRegionList.add(slowRegion4);
                Region slowRegion5 = new Region();
                slowRegion5.mRegionStartTime = Integer.parseInt(regionData1[0]) * 2;
                slowRegion5.mRegionEndTime = Integer.parseInt(regionData1[1]) * 2;
                if (Integer.parseInt(regionData1[2]) == 3) {
                    slowRegion5.mRegionSpeed = 5;
                } else if (Integer.parseInt(regionData1[2]) == 2) {
                    slowRegion5.mRegionSpeed = 6;
                } else if (Integer.parseInt(regionData1[2]) == 4) {
                    slowRegion5.mRegionSpeed = 1;
                } else {
                    LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(regionData1[2]));
                    slowRegion5.mRegionSpeed = 1;
                }
                slowRegion5.mRegionSpeedType = getSpeed(slowRegion5.mRegionSpeed);
                this.mRegionList.add(slowRegion5);
                Region slowRegion6 = new Region();
                slowRegion6.mRegionStartTime = Integer.parseInt(regionData1[1]) * 2;
                slowRegion6.mRegionEndTime = Integer.parseInt(regionData2[0]) * 2;
                slowRegion6.mRegionSpeed = 7;
                slowRegion6.mRegionSpeedType = getSpeed(slowRegion6.mRegionSpeed);
                this.mRegionList.add(slowRegion6);
                Region slowRegion7 = new Region();
                slowRegion7.mRegionStartTime = Integer.parseInt(regionData2[0]) * 2;
                slowRegion7.mRegionEndTime = Integer.parseInt(regionData2[1]) * 2;
                if (Integer.parseInt(regionData2[2]) == 3) {
                    slowRegion7.mRegionSpeed = 5;
                } else if (Integer.parseInt(regionData2[2]) == 2) {
                    slowRegion7.mRegionSpeed = 6;
                } else if (Integer.parseInt(regionData2[2]) == 4) {
                    slowRegion7.mRegionSpeed = 1;
                } else {
                    LogS.d("TranscodeLib", "region speed: " + Integer.parseInt(regionData2[2]));
                    slowRegion7.mRegionSpeed = 7;
                }
                slowRegion7.mRegionSpeedType = getSpeed(slowRegion7.mRegionSpeed);
                this.mRegionList.add(slowRegion7);
                Region slowRegion8 = new Region();
                slowRegion8.mRegionStartTime = Integer.parseInt(regionData2[1]) * 2;
                slowRegion8.mRegionEndTime = (int) this.mDuration;
                slowRegion8.mRegionSpeed = 7;
                slowRegion8.mRegionSpeedType = getSpeed(slowRegion8.mRegionSpeed);
                this.mRegionList.add(slowRegion8);
            } else {
                LogS.d(
                        "TranscodeLib",
                        "There is not slowDataregion, length: " + slowDataregion.length);
            }
            for (int i = 0; i < this.mRegionList.size(); i++) {
                LogS.d("TranscodeLib", "Region List " + i);
                LogS.d(
                        "TranscodeLib",
                        "Region regionStartTime " + this.mRegionList.get(i).mRegionStartTime);
                LogS.d(
                        "TranscodeLib",
                        "Region regionEndTime " + this.mRegionList.get(i).mRegionEndTime);
                LogS.d(
                        "TranscodeLib",
                        "Region regionSpeed " + this.mRegionList.get(i).mRegionSpeed);
                LogS.d(
                        "TranscodeLib",
                        "Region regionSpeedType " + this.mRegionList.get(i).mRegionSpeedType);
            }
            return true;
        } catch (NumberFormatException nfe) {
            LogS.d("TranscodeLib", "throwing number format:" + nfe);
            return false;
        }
    }

    private boolean is120fpsSlowMotionVideo() {
        return this.mRecordingMode == 13
                || this.mRecordingMode == 15
                || (this.mRecordingMode == 21 && this.mRecordingFps == 120);
    }

    private boolean isSlowMotionV2() {
        return this.mRecordingMode == 13
                || this.mRecordingMode == 15
                || this.mRecordingMode == 12
                || this.mRecordingMode == 21
                || this.mRecordingMode == 19;
    }

    public static boolean supportMTK_SSM() {
        if (Build.VERSION.SEM_PLATFORM_INT > 130100) {
            return true;
        }
        return SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_MTK_SSM_SM_VIDEO");
    }

    private boolean checkValidSEFData(String data) {
        if (data == null) {
            return false;
        }
        String[] slowDataregion = data.split("\\*");
        for (String s : slowDataregion) {
            String[] regionData = s.split(":");
            int startTime = Integer.parseInt(regionData[0]);
            int endTime = Integer.parseInt(regionData[1]);
            if (startTime >= endTime) {
                LogS.d("TranscodeLib", "checkValidSEFData : startTime >= endTime");
                return false;
            }
            if (startTime < 0 || endTime > this.mDuration) {
                LogS.d("TranscodeLib", "checkValidSEFData : startTime < 0  or endTime > mDuration");
                return false;
            }
        }
        return true;
    }

    public long getEditedDuration(long sampleTime) {
        if (extractSEFData() == null) {
            LogS.d("TranscodeLib", "getEditedDuration : use original data");
            return sampleTime;
        }
        return getConvertedTime(sampleTime);
    }

    public long getConvertedTime(long sampleTime) {
        long sampleTime2;
        long timedelta = 0;
        if (!is120fpsSlowMotionVideo()) {
            sampleTime2 = sampleTime;
        } else {
            sampleTime2 = 2 * sampleTime;
        }
        long tempSampleTime = sampleTime2;
        if (this.mRegionList != null && !this.mRegionList.isEmpty()) {
            int i = 0;
            while (true) {
                if (i < this.mRegionList.size()) {
                    if (sampleTime2 < this.mRegionList.get(i).mRegionStartTime * 1000
                            || sampleTime2 >= this.mRegionList.get(i).mRegionEndTime * 1000) {
                        if (sampleTime2 >= this.mRegionList.get(i).mRegionEndTime * 1000) {
                            Speed playSpeed = this.mRegionList.get(i).mRegionSpeedType;
                            float timescale = getTimeScale(playSpeed);
                            timedelta =
                                    ((double) timescale) > 1.0d
                                            ? (long)
                                                    (timedelta
                                                            + ((timescale - 1.0d)
                                                                    * (this.mRegionList.get(i)
                                                                                    .mRegionEndTime
                                                                            - this.mRegionList.get(
                                                                                            i)
                                                                                    .mRegionStartTime)
                                                                    * 1000.0d))
                                            : (long)
                                                    (timedelta
                                                            - (((1.0d - timescale) * 1000.0d)
                                                                    * (this.mRegionList.get(i)
                                                                                    .mRegionEndTime
                                                                            - this.mRegionList.get(
                                                                                            i)
                                                                                    .mRegionStartTime)));
                        }
                        i++;
                    } else {
                        Speed playSpeed2 = this.mRegionList.get(i).mRegionSpeedType;
                        tempSampleTime =
                                (this.mRegionList.get(i).mRegionStartTime * 1000)
                                        + (((sampleTime2
                                                                - (this.mRegionList.get(i)
                                                                                .mRegionStartTime
                                                                        * 1000))
                                                        * ((long)
                                                                (1000000.0f
                                                                        * getTimeScale(
                                                                                playSpeed2))))
                                                / 1000000);
                        break;
                    }
                } else {
                    break;
                }
            }
            return tempSampleTime + timedelta;
        }
        return 0L;
    }
}
