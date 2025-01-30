package com.samsung.android.transcode.util;

import android.app.job.JobInfo;
import android.content.Context;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.p009os.Build;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.media.SemExtendedFormat;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Vector;

/* loaded from: classes5.dex */
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
            LogS.m473d("TranscodeLib", "SEFHelper Initialize failed!");
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

    /* renamed from: com.samsung.android.transcode.util.SEFHelper$1 */
    static /* synthetic */ class C53131 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed;

        static {
            int[] iArr = new int[Speed.values().length];
            $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed = iArr;
            try {
                iArr[Speed.ONE_FOURTH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.HALF.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.NORMAL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.ONE_EIGHTH.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.TWO_TIMES.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.FOUR_TIMES.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.EIGHT_TIMES.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.SIXTEEN_TIMES.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[Speed.THIRTY_TWO_TIMES.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    public static float getTimeScale(Speed speedType) {
        switch (C53131.$SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[speedType.ordinal()]) {
            case 1:
                return 4.0f;
            case 2:
                return 2.0f;
            case 3:
                return 1.0f;
            case 4:
                return 8.0f;
            case 5:
                return 0.5f;
            case 6:
                return 0.25f;
            case 7:
                return 0.125f;
            case 8:
                return 0.0625f;
            case 9:
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
        String extractSEFData = extractSEFData();
        this.mSEFData = extractSEFData;
        if (extractSEFData == null) {
            LogS.m473d("TranscodeLib", "extractSEFData : SEFData == null, createDefaultRegion");
            ret = createDefaultRegion();
        } else {
            int i = this.mRecordingMode;
            if (i == 2 || i == 1) {
                ret = slowfastSEFParser(extractSEFData);
            } else if (i == 8 || i == 7 || i == 9 || i == 22 || i == 18) {
                ret = superslowSEFParser(extractSEFData);
            } else if (i == 12 || ((i == 21 && this.mRecordingFps > 120) || i == 19)) {
                ret = newslowSEFParser(extractSEFData);
            } else if (i == 13 || i == 15 || i == 21) {
                ret = newslowSEFParserV2(extractSEFData);
            }
        }
        if (!ret) {
            this.mRegionList.clear();
        }
        return ret;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x007f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0080 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String extractSEFData() {
        File sefFile;
        byte[] sefByteData;
        String sefString;
        Uri uri = this.mUri;
        if (uri != null) {
            String path = FileHelper.getVEEditFilePath(this.mContext, uri);
            if (path == null) {
                LogS.m473d("TranscodeLib", "filepath is Wrong");
                return null;
            }
            sefFile = new File(path);
        } else {
            if (this.mFilepath == null) {
                LogS.m473d("TranscodeLib", "filepath is NULL");
                return null;
            }
            sefFile = new File(this.mFilepath);
        }
        try {
            if (!SemExtendedFormat.isValidFile(sefFile)) {
                return null;
            }
            int i = this.mRecordingMode;
            if (i != 1 && i != 2 && i != 12 && i != 21 && i != 13 && i != 15 && i != 19) {
                if (i != 8 && i != 7 && i != 9 && i != 22 && i != 18) {
                    return null;
                }
                sefByteData = SemExtendedFormat.getData(sefFile, "Super_SlowMotion_Data");
                sefString = new String(sefByteData, StandardCharsets.UTF_8);
                if (checkValidSEFData(sefString)) {
                    return null;
                }
                return sefString;
            }
            sefByteData = SemExtendedFormat.getData(sefFile, "SlowMotion_Data");
            sefString = new String(sefByteData, StandardCharsets.UTF_8);
            if (checkValidSEFData(sefString)) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Region> getRegionList() {
        return this.mRegionList;
    }

    private boolean createDefaultRegion() {
        MediaExtractor me = null;
        try {
            try {
                Uri uri = this.mUri;
                me = uri != null ? CodecsHelper.createExtractor(this.mContext, uri) : CodecsHelper.createExtractor(this.mFilepath);
                int videoTrack = CodecsHelper.getAndSelectVideoTrackIndex(me);
                MediaFormat inputFormat = me.getTrackFormat(videoTrack);
                long duration = inputFormat.getLong(MediaFormat.KEY_DURATION);
                if (me != null) {
                    me.release();
                }
                LogS.m473d("TranscodeLib", "createDefaultRegion duration:" + duration);
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
                        slowRegion2.mRegionStartTime = (int) ((2 * duration) / JobInfo.MIN_BACKOFF_MILLIS);
                        slowRegion2.mRegionEndTime = (int) ((duration * 8) / JobInfo.MIN_BACKOFF_MILLIS);
                        slowRegion2.mRegionSpeedType = Speed.TWO_TIMES;
                        slowRegion2.mRegionSpeed = slowRegion2.mRegionSpeedType.value;
                        this.mRegionList.add(slowRegion2);
                    }
                    Region slowRegion3 = new Region();
                    slowRegion3.mRegionStartTime = (int) ((8 * duration) / JobInfo.MIN_BACKOFF_MILLIS);
                    slowRegion3.mRegionEndTime = (int) (duration / 1000);
                    slowRegion3.mRegionSpeedType = Speed.EIGHT_TIMES;
                    slowRegion3.mRegionSpeed = slowRegion3.mRegionSpeedType.value;
                    this.mRegionList.add(slowRegion3);
                } else {
                    Region slowRegion4 = new Region();
                    slowRegion4.mRegionStartTime = (int) ((2 * duration) / JobInfo.MIN_BACKOFF_MILLIS);
                    slowRegion4.mRegionEndTime = (int) ((8 * duration) / JobInfo.MIN_BACKOFF_MILLIS);
                    int i = this.mRecordingMode;
                    if (i == 1) {
                        slowRegion4.mRegionSpeedType = Speed.ONE_EIGHTH;
                    } else if (i == 2) {
                        slowRegion4.mRegionSpeedType = Speed.EIGHT_TIMES;
                    }
                    slowRegion4.mRegionSpeed = slowRegion4.mRegionSpeedType.value;
                    this.mRegionList.add(slowRegion4);
                }
                for (int i2 = 0; i2 < this.mRegionList.size(); i2++) {
                    LogS.m473d("TranscodeLib", "Region List " + i2);
                    LogS.m473d("TranscodeLib", "Region regionStartTime " + this.mRegionList.get(i2).mRegionStartTime);
                    LogS.m473d("TranscodeLib", "Region regionEndTime " + this.mRegionList.get(i2).mRegionEndTime);
                    LogS.m473d("TranscodeLib", "Region regionSpeed " + this.mRegionList.get(i2).mRegionSpeed);
                    LogS.m473d("TranscodeLib", "Region regionSpeedType " + this.mRegionList.get(i2).mRegionSpeedType);
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
        List<Region> list = this.mRegionList;
        if (list != null && !list.isEmpty()) {
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
        LogS.m473d("TranscodeLib", "There is no region info.");
        return false;
    }

    public boolean newslowSEFParser(String sefData) {
        try {
            LogS.m473d("TranscodeLib", "sefData read slow : " + sefData);
            if (sefData == null) {
                LogS.m473d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] slowDataregion = sefData.split("\\*");
            LogS.m473d("TranscodeLib", "slowDataregion,length: " + slowDataregion.length);
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
                        LogS.m473d("TranscodeLib", "region speed: " + Integer.parseInt(regionData[2]));
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
                        LogS.m473d("TranscodeLib", "region speed: " + Integer.parseInt(regionData1[2]));
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
                        LogS.m473d("TranscodeLib", "region speed: " + Integer.parseInt(regionData2[2]));
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
                LogS.m473d("TranscodeLib", "There is not slowDataregion, length: " + slowDataregion.length);
            }
            for (int i = 0; i < this.mRegionList.size(); i++) {
                LogS.m473d("TranscodeLib", "Region List " + i);
                LogS.m473d("TranscodeLib", "Region regionStartTime " + this.mRegionList.get(i).mRegionStartTime);
                LogS.m473d("TranscodeLib", "Region regionEndTime " + this.mRegionList.get(i).mRegionEndTime);
                LogS.m473d("TranscodeLib", "Region regionSpeed " + this.mRegionList.get(i).mRegionSpeed);
                LogS.m473d("TranscodeLib", "Region regionSpeedType " + this.mRegionList.get(i).mRegionSpeedType);
            }
            return true;
        } catch (NumberFormatException nfe) {
            LogS.m473d("TranscodeLib", "throwing number format:" + nfe);
            return false;
        }
    }

    public boolean slowfastSEFParser(String sefData) {
        try {
            LogS.m473d("TranscodeLib", "sefData read slow : " + sefData);
            if (sefData == null) {
                LogS.m473d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] slowDataregion = sefData.split("\\*");
            LogS.m473d("TranscodeLib", "slowDataregion,length: " + slowDataregion.length);
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
            LogS.m473d("TranscodeLib", "throwing number format:" + nfe);
            return false;
        }
    }

    public boolean superslowSEFParser(String sefData) {
        try {
            LogS.m473d("TranscodeLib", "sefData read super : " + sefData);
            if (sefData == null) {
                LogS.m473d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] slowDataregion = sefData.split("\\*");
            LogS.m473d("TranscodeLib", "slowDataregion,length: " + slowDataregion.length);
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
            LogS.m473d("TranscodeLib", "throwing number format:" + nfe);
            return false;
        }
    }

    private boolean newslowSEFParserV2(String sefData) {
        try {
            LogS.m473d("TranscodeLib", "sefData read slow : " + sefData);
            if (sefData == null) {
                LogS.m473d("TranscodeLib", "sefData == null");
                return false;
            }
            String[] slowDataregion = sefData.split("\\*");
            LogS.m473d("TranscodeLib", "slowDataregion,length: " + slowDataregion.length);
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
                    LogS.m473d("TranscodeLib", "region speed: " + Integer.parseInt(regionData[2]));
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
                    LogS.m473d("TranscodeLib", "region speed: " + Integer.parseInt(regionData1[2]));
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
                    LogS.m473d("TranscodeLib", "region speed: " + Integer.parseInt(regionData2[2]));
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
                LogS.m473d("TranscodeLib", "There is not slowDataregion, length: " + slowDataregion.length);
            }
            for (int i = 0; i < this.mRegionList.size(); i++) {
                LogS.m473d("TranscodeLib", "Region List " + i);
                LogS.m473d("TranscodeLib", "Region regionStartTime " + this.mRegionList.get(i).mRegionStartTime);
                LogS.m473d("TranscodeLib", "Region regionEndTime " + this.mRegionList.get(i).mRegionEndTime);
                LogS.m473d("TranscodeLib", "Region regionSpeed " + this.mRegionList.get(i).mRegionSpeed);
                LogS.m473d("TranscodeLib", "Region regionSpeedType " + this.mRegionList.get(i).mRegionSpeedType);
            }
            return true;
        } catch (NumberFormatException nfe) {
            LogS.m473d("TranscodeLib", "throwing number format:" + nfe);
            return false;
        }
    }

    private boolean is120fpsSlowMotionVideo() {
        int i = this.mRecordingMode;
        return i == 13 || i == 15 || (i == 21 && this.mRecordingFps == 120);
    }

    private boolean isSlowMotionV2() {
        int i = this.mRecordingMode;
        return i == 13 || i == 15 || i == 12 || i == 21 || i == 19;
    }

    public static boolean supportMTK_SSM() {
        if (Build.VERSION.SEM_PLATFORM_INT > 130100) {
            return true;
        }
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_MTK_SSM_SM_VIDEO");
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
                LogS.m473d("TranscodeLib", "checkValidSEFData : startTime >= endTime");
                return false;
            }
            if (startTime < 0 || endTime > this.mDuration) {
                LogS.m473d("TranscodeLib", "checkValidSEFData : startTime < 0  or endTime > mDuration");
                return false;
            }
        }
        return true;
    }

    public long getEditedDuration(long sampleTime) {
        if (extractSEFData() == null) {
            LogS.m473d("TranscodeLib", "getEditedDuration : use original data");
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
        List<Region> list = this.mRegionList;
        if (list != null && !list.isEmpty()) {
            int i = 0;
            while (true) {
                if (i < this.mRegionList.size()) {
                    if (sampleTime2 < this.mRegionList.get(i).mRegionStartTime * 1000 || sampleTime2 >= this.mRegionList.get(i).mRegionEndTime * 1000) {
                        if (sampleTime2 >= this.mRegionList.get(i).mRegionEndTime * 1000) {
                            Speed playSpeed = this.mRegionList.get(i).mRegionSpeedType;
                            float timescale = getTimeScale(playSpeed);
                            timedelta = ((double) timescale) > 1.0d ? (long) (timedelta + ((timescale - 1.0d) * (this.mRegionList.get(i).mRegionEndTime - this.mRegionList.get(i).mRegionStartTime) * 1000.0d)) : (long) (timedelta - (((1.0d - timescale) * 1000.0d) * (this.mRegionList.get(i).mRegionEndTime - this.mRegionList.get(i).mRegionStartTime)));
                        }
                        i++;
                    } else {
                        Speed playSpeed2 = this.mRegionList.get(i).mRegionSpeedType;
                        tempSampleTime = (this.mRegionList.get(i).mRegionStartTime * 1000) + (((sampleTime2 - (this.mRegionList.get(i).mRegionStartTime * 1000)) * ((long) (1000000.0f * getTimeScale(playSpeed2)))) / 1000000);
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
