package com.samsung.android.hardware.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SemHqmManager;
import android.util.Slog;
import android.view.WindowManager;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Thread;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class AfterimageCompensationService {
    public static final String[] mAFPC_KEYS = {"ApplyCount", "JND", "AvgLum", "MaxBDI", "NBDI", "EffAvgLum", "EffMaxBDI", "EffNBDI", "ApplyCount_sub", "JND_sub", "AvgLum_sub", "MaxBDI_sub", "NBDI_sub", "EffAvgLum_sub", "EffMaxBDI_sub", "EffNBDI_sub"};
    public boolean AfcStateCondition;
    public final int AfpcPeriodMax;
    public final int mAfcType;
    public final int mAfpcPanelNumber_main;
    public final int mAfpcSize;
    public int mApplyCount;
    public int mApplyCount_sub;
    public final int mApplyScaleEffect;
    public float mApplyValue;
    public float mApplyValue_sub;
    public final Context mContext;
    public final boolean mThreadAFPC;
    public final long mThreadSleepTime;
    public final boolean DEBUG = "eng".equals(Build.TYPE);
    public Thread mAfcThread = null;
    public boolean AfcThreadCondition = true;
    public boolean AfcThreadAODCondition = false;
    public boolean AfcThreadTerminateCondition = false;
    public boolean isRunningCameraApp = false;
    public int AodBrightness = 0;
    public int mLuminance = 0;
    public int mLuminance_sub = 0;
    public int AfpcPeriodCount = 0;
    public int AfpcPeriodCount_sub = 0;
    public final float[] mAfpcJndRef = {7.0f, 11.0f};
    public final float[] mAfpcJndRefV2 = {3.0f, 5.0f, 7.0f, 9.0f, 11.0f};
    public final float[] effNbdiTh = {150.0f, 150.0f, 200.0f, 200.0f, 250.0f, 250.0f, 250.0f, 250.0f, 250.0f, 250.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f};
    public final float[] effNbdiTh_V3 = {100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 150.0f, 150.0f, 150.0f, 150.0f, 150.0f, 200.0f, 200.0f, 200.0f, 200.0f, 200.0f, 250.0f, 250.0f, 250.0f, 250.0f, 250.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f};
    public final float[] effNbdiTh_V4 = {100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 150.0f, 150.0f, 150.0f, 150.0f, 150.0f, 200.0f, 200.0f, 200.0f, 200.0f, 200.0f, 250.0f, 250.0f, 250.0f, 250.0f, 250.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f};
    public final float[] effNbdiTh_V5 = {100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 150.0f, 150.0f, 150.0f, 150.0f, 150.0f, 200.0f, 200.0f, 200.0f, 200.0f, 200.0f, 250.0f, 250.0f, 250.0f, 250.0f, 250.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f};
    public final float[] effNbdiTh_V6 = {30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f};
    public final float[] effNbdiTh_V7 = {30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f};
    public final float[] effNbdiTh_V9 = {30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f};
    public double mAvgLum = 0.0d;
    public double mMaxBDI = 0.0d;
    public double mNBDI = 0.0d;
    public double mEffAvgLum = 0.0d;
    public double mEffMaxBDI = 0.0d;
    public double mEffNBDI = 0.0d;
    public double mAvgLum_sub = 0.0d;
    public double mMaxBDI_sub = 0.0d;
    public double mNBDI_sub = 0.0d;
    public double mEffAvgLum_sub = 0.0d;
    public double mEffMaxBDI_sub = 0.0d;
    public double mEffNBDI_sub = 0.0d;
    public SemHqmManager mSemHqmManager = null;
    public WindowManager mWindowManager = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AfcThread extends Thread {
        public boolean mDataValid = false;
        public boolean mNormalValid = false;
        public int rotation = 0;

        public AfcThread() {
        }

        /* JADX WARN: Removed duplicated region for block: B:361:0x02ba A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:365:0x01ba A[Catch: all -> 0x0023, Exception -> 0x0027, TryCatch #19 {Exception -> 0x0027, blocks: (B:4:0x0002, B:6:0x0008, B:8:0x000f, B:11:0x0017, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:20:0x0062, B:21:0x006b, B:23:0x0073, B:25:0x007b, B:55:0x0083, B:57:0x0089, B:59:0x0099, B:61:0x00a1, B:62:0x00d6, B:64:0x00dd, B:66:0x00f0, B:69:0x0103, B:71:0x0115, B:73:0x011d, B:74:0x0127, B:76:0x012f, B:77:0x015e, B:79:0x0164, B:84:0x0174, B:85:0x0177, B:86:0x017a, B:87:0x017d, B:88:0x0180, B:89:0x0182, B:91:0x0194, B:363:0x01a8, B:365:0x01ba, B:367:0x0215, B:370:0x021f, B:371:0x0225, B:382:0x0235, B:385:0x0236, B:95:0x023a, B:97:0x0295, B:100:0x029f, B:101:0x02a5, B:112:0x02b5, B:115:0x02b6, B:391:0x0139, B:393:0x0141, B:394:0x014c, B:396:0x0154, B:118:0x02ba, B:120:0x02c6, B:122:0x02cd, B:124:0x02d9, B:126:0x02fa, B:127:0x0335, B:131:0x031a, B:132:0x033c, B:134:0x0345, B:136:0x0351, B:138:0x0372, B:139:0x03ad, B:141:0x03ba, B:143:0x03cc, B:145:0x03d8, B:147:0x03f9, B:151:0x0449, B:152:0x0464, B:156:0x0392, B:157:0x046b, B:159:0x0475, B:161:0x0481, B:163:0x053c, B:165:0x0544, B:167:0x0550, B:169:0x0564, B:171:0x056e, B:175:0x05bf, B:176:0x05da, B:177:0x05e1, B:179:0x05e9, B:181:0x05f5, B:183:0x06ab, B:185:0x06ba, B:187:0x06d3, B:189:0x06e7, B:191:0x06f1, B:195:0x0742, B:196:0x075d, B:197:0x0764, B:199:0x0774, B:201:0x0780, B:203:0x0834, B:205:0x083e, B:207:0x0857, B:209:0x086b, B:211:0x0875, B:215:0x08c6, B:216:0x08e1, B:217:0x08e8, B:219:0x08f1, B:221:0x08fd, B:223:0x09b1, B:225:0x09bb, B:227:0x09d0, B:229:0x09e4, B:231:0x09ee, B:235:0x0a3f, B:236:0x0a5a, B:237:0x0a61, B:239:0x0a6b, B:241:0x0a77, B:243:0x0b2e, B:245:0x0b36, B:247:0x0b42, B:249:0x0b57, B:251:0x0b6b, B:253:0x0b75, B:257:0x0bc7, B:258:0x0c9d, B:259:0x0be4, B:261:0x0bee, B:263:0x0bfa, B:265:0x0c13, B:267:0x0c27, B:269:0x0c31, B:273:0x0c82, B:274:0x0ca4, B:276:0x0cae, B:279:0x0cba, B:281:0x0cc2, B:283:0x0cce, B:285:0x0d84, B:287:0x0d90, B:289:0x0da9, B:291:0x0dbd, B:293:0x0dc7, B:297:0x0e18, B:298:0x0e33, B:299:0x0e3a, B:301:0x0e44, B:303:0x0e50, B:305:0x0f06, B:307:0x0f12, B:309:0x0f2b, B:311:0x0f3f, B:313:0x0f49, B:317:0x0f9a, B:318:0x0fb5, B:319:0x0fbc, B:321:0x0fc8, B:323:0x107e, B:325:0x108a, B:327:0x109f, B:329:0x10b3, B:331:0x10bd, B:335:0x110e, B:336:0x1129, B:337:0x112e, B:339:0x113a, B:341:0x1142, B:343:0x114c, B:345:0x1158, B:347:0x1205, B:349:0x120d, B:351:0x1218, B:353:0x122c, B:355:0x1234, B:359:0x1283, B:360:0x129e, B:399:0x00aa, B:401:0x00c8, B:403:0x00d0, B:405:0x0067, B:28:0x12a3, B:31:0x12ab, B:34:0x12b3, B:35:0x12b9, B:47:0x12c9, B:51:0x12ca, B:407:0x002b, B:410:0x002e, B:412:0x0036), top: B:3:0x0002, outer: #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:389:0x02ba A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:79:0x0164 A[Catch: all -> 0x0023, Exception -> 0x0027, TryCatch #19 {Exception -> 0x0027, blocks: (B:4:0x0002, B:6:0x0008, B:8:0x000f, B:11:0x0017, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:20:0x0062, B:21:0x006b, B:23:0x0073, B:25:0x007b, B:55:0x0083, B:57:0x0089, B:59:0x0099, B:61:0x00a1, B:62:0x00d6, B:64:0x00dd, B:66:0x00f0, B:69:0x0103, B:71:0x0115, B:73:0x011d, B:74:0x0127, B:76:0x012f, B:77:0x015e, B:79:0x0164, B:84:0x0174, B:85:0x0177, B:86:0x017a, B:87:0x017d, B:88:0x0180, B:89:0x0182, B:91:0x0194, B:363:0x01a8, B:365:0x01ba, B:367:0x0215, B:370:0x021f, B:371:0x0225, B:382:0x0235, B:385:0x0236, B:95:0x023a, B:97:0x0295, B:100:0x029f, B:101:0x02a5, B:112:0x02b5, B:115:0x02b6, B:391:0x0139, B:393:0x0141, B:394:0x014c, B:396:0x0154, B:118:0x02ba, B:120:0x02c6, B:122:0x02cd, B:124:0x02d9, B:126:0x02fa, B:127:0x0335, B:131:0x031a, B:132:0x033c, B:134:0x0345, B:136:0x0351, B:138:0x0372, B:139:0x03ad, B:141:0x03ba, B:143:0x03cc, B:145:0x03d8, B:147:0x03f9, B:151:0x0449, B:152:0x0464, B:156:0x0392, B:157:0x046b, B:159:0x0475, B:161:0x0481, B:163:0x053c, B:165:0x0544, B:167:0x0550, B:169:0x0564, B:171:0x056e, B:175:0x05bf, B:176:0x05da, B:177:0x05e1, B:179:0x05e9, B:181:0x05f5, B:183:0x06ab, B:185:0x06ba, B:187:0x06d3, B:189:0x06e7, B:191:0x06f1, B:195:0x0742, B:196:0x075d, B:197:0x0764, B:199:0x0774, B:201:0x0780, B:203:0x0834, B:205:0x083e, B:207:0x0857, B:209:0x086b, B:211:0x0875, B:215:0x08c6, B:216:0x08e1, B:217:0x08e8, B:219:0x08f1, B:221:0x08fd, B:223:0x09b1, B:225:0x09bb, B:227:0x09d0, B:229:0x09e4, B:231:0x09ee, B:235:0x0a3f, B:236:0x0a5a, B:237:0x0a61, B:239:0x0a6b, B:241:0x0a77, B:243:0x0b2e, B:245:0x0b36, B:247:0x0b42, B:249:0x0b57, B:251:0x0b6b, B:253:0x0b75, B:257:0x0bc7, B:258:0x0c9d, B:259:0x0be4, B:261:0x0bee, B:263:0x0bfa, B:265:0x0c13, B:267:0x0c27, B:269:0x0c31, B:273:0x0c82, B:274:0x0ca4, B:276:0x0cae, B:279:0x0cba, B:281:0x0cc2, B:283:0x0cce, B:285:0x0d84, B:287:0x0d90, B:289:0x0da9, B:291:0x0dbd, B:293:0x0dc7, B:297:0x0e18, B:298:0x0e33, B:299:0x0e3a, B:301:0x0e44, B:303:0x0e50, B:305:0x0f06, B:307:0x0f12, B:309:0x0f2b, B:311:0x0f3f, B:313:0x0f49, B:317:0x0f9a, B:318:0x0fb5, B:319:0x0fbc, B:321:0x0fc8, B:323:0x107e, B:325:0x108a, B:327:0x109f, B:329:0x10b3, B:331:0x10bd, B:335:0x110e, B:336:0x1129, B:337:0x112e, B:339:0x113a, B:341:0x1142, B:343:0x114c, B:345:0x1158, B:347:0x1205, B:349:0x120d, B:351:0x1218, B:353:0x122c, B:355:0x1234, B:359:0x1283, B:360:0x129e, B:399:0x00aa, B:401:0x00c8, B:403:0x00d0, B:405:0x0067, B:28:0x12a3, B:31:0x12ab, B:34:0x12b3, B:35:0x12b9, B:47:0x12c9, B:51:0x12ca, B:407:0x002b, B:410:0x002e, B:412:0x0036), top: B:3:0x0002, outer: #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:97:0x0295 A[Catch: all -> 0x0023, Exception -> 0x0027, TRY_LEAVE, TryCatch #19 {Exception -> 0x0027, blocks: (B:4:0x0002, B:6:0x0008, B:8:0x000f, B:11:0x0017, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:20:0x0062, B:21:0x006b, B:23:0x0073, B:25:0x007b, B:55:0x0083, B:57:0x0089, B:59:0x0099, B:61:0x00a1, B:62:0x00d6, B:64:0x00dd, B:66:0x00f0, B:69:0x0103, B:71:0x0115, B:73:0x011d, B:74:0x0127, B:76:0x012f, B:77:0x015e, B:79:0x0164, B:84:0x0174, B:85:0x0177, B:86:0x017a, B:87:0x017d, B:88:0x0180, B:89:0x0182, B:91:0x0194, B:363:0x01a8, B:365:0x01ba, B:367:0x0215, B:370:0x021f, B:371:0x0225, B:382:0x0235, B:385:0x0236, B:95:0x023a, B:97:0x0295, B:100:0x029f, B:101:0x02a5, B:112:0x02b5, B:115:0x02b6, B:391:0x0139, B:393:0x0141, B:394:0x014c, B:396:0x0154, B:118:0x02ba, B:120:0x02c6, B:122:0x02cd, B:124:0x02d9, B:126:0x02fa, B:127:0x0335, B:131:0x031a, B:132:0x033c, B:134:0x0345, B:136:0x0351, B:138:0x0372, B:139:0x03ad, B:141:0x03ba, B:143:0x03cc, B:145:0x03d8, B:147:0x03f9, B:151:0x0449, B:152:0x0464, B:156:0x0392, B:157:0x046b, B:159:0x0475, B:161:0x0481, B:163:0x053c, B:165:0x0544, B:167:0x0550, B:169:0x0564, B:171:0x056e, B:175:0x05bf, B:176:0x05da, B:177:0x05e1, B:179:0x05e9, B:181:0x05f5, B:183:0x06ab, B:185:0x06ba, B:187:0x06d3, B:189:0x06e7, B:191:0x06f1, B:195:0x0742, B:196:0x075d, B:197:0x0764, B:199:0x0774, B:201:0x0780, B:203:0x0834, B:205:0x083e, B:207:0x0857, B:209:0x086b, B:211:0x0875, B:215:0x08c6, B:216:0x08e1, B:217:0x08e8, B:219:0x08f1, B:221:0x08fd, B:223:0x09b1, B:225:0x09bb, B:227:0x09d0, B:229:0x09e4, B:231:0x09ee, B:235:0x0a3f, B:236:0x0a5a, B:237:0x0a61, B:239:0x0a6b, B:241:0x0a77, B:243:0x0b2e, B:245:0x0b36, B:247:0x0b42, B:249:0x0b57, B:251:0x0b6b, B:253:0x0b75, B:257:0x0bc7, B:258:0x0c9d, B:259:0x0be4, B:261:0x0bee, B:263:0x0bfa, B:265:0x0c13, B:267:0x0c27, B:269:0x0c31, B:273:0x0c82, B:274:0x0ca4, B:276:0x0cae, B:279:0x0cba, B:281:0x0cc2, B:283:0x0cce, B:285:0x0d84, B:287:0x0d90, B:289:0x0da9, B:291:0x0dbd, B:293:0x0dc7, B:297:0x0e18, B:298:0x0e33, B:299:0x0e3a, B:301:0x0e44, B:303:0x0e50, B:305:0x0f06, B:307:0x0f12, B:309:0x0f2b, B:311:0x0f3f, B:313:0x0f49, B:317:0x0f9a, B:318:0x0fb5, B:319:0x0fbc, B:321:0x0fc8, B:323:0x107e, B:325:0x108a, B:327:0x109f, B:329:0x10b3, B:331:0x10bd, B:335:0x110e, B:336:0x1129, B:337:0x112e, B:339:0x113a, B:341:0x1142, B:343:0x114c, B:345:0x1158, B:347:0x1205, B:349:0x120d, B:351:0x1218, B:353:0x122c, B:355:0x1234, B:359:0x1283, B:360:0x129e, B:399:0x00aa, B:401:0x00c8, B:403:0x00d0, B:405:0x0067, B:28:0x12a3, B:31:0x12ab, B:34:0x12b3, B:35:0x12b9, B:47:0x12c9, B:51:0x12ca, B:407:0x002b, B:410:0x002e, B:412:0x0036), top: B:3:0x0002, outer: #2 }] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 4871
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.hardware.display.AfterimageCompensationService.AfcThread.run():void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScreenWatchingReceiver extends BroadcastReceiver {
        public ScreenWatchingReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int i;
            int i2;
            Thread thread;
            Thread thread2;
            String action = intent.getAction();
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                Slog.i("AfterimageCompensationService", "ACTION_BOOT_COMPLETED");
                AfterimageCompensationService afterimageCompensationService = AfterimageCompensationService.this;
                afterimageCompensationService.mSemHqmManager = (SemHqmManager) afterimageCompensationService.mContext.getSystemService("HqmManagerService");
                AfterimageCompensationService afterimageCompensationService2 = AfterimageCompensationService.this;
                afterimageCompensationService2.mWindowManager = (WindowManager) afterimageCompensationService2.mContext.getSystemService("window");
                AfterimageCompensationService afterimageCompensationService3 = AfterimageCompensationService.this;
                if (!afterimageCompensationService3.AfcStateCondition) {
                    HeimdAllFsService$$ExternalSyntheticOutline0.m("AfterimageCompensationService", new StringBuilder("AfcStateCondition is already done - "), AfterimageCompensationService.this.AfcStateCondition);
                    return;
                }
                afterimageCompensationService3.mAfcThread = AfterimageCompensationService.this.new AfcThread();
                AfterimageCompensationService.this.mAfcThread.start();
                Slog.i("AfterimageCompensationService", "AFC Thread Start");
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                Slog.i("AfterimageCompensationService", "ACTION_SCREEN_ON");
                AfterimageCompensationService afterimageCompensationService4 = AfterimageCompensationService.this;
                if (afterimageCompensationService4.AfcStateCondition) {
                    afterimageCompensationService4.AfcThreadCondition = true;
                    afterimageCompensationService4.AfcThreadAODCondition = false;
                    Thread thread3 = afterimageCompensationService4.mAfcThread;
                    if (thread3 != null) {
                        synchronized (thread3) {
                            afterimageCompensationService4.mAfcThread.notify();
                        }
                    }
                    if (afterimageCompensationService4.mAfcType < 10 || (thread2 = afterimageCompensationService4.mAfcThread) == null || !afterimageCompensationService4.AfcThreadTerminateCondition || thread2.getState() != Thread.State.TERMINATED) {
                        return;
                    }
                    AfcThread afcThread = afterimageCompensationService4.new AfcThread();
                    afterimageCompensationService4.mAfcThread = afcThread;
                    afcThread.start();
                    Slog.i("AfterimageCompensationService", "AFC Thread ReStart");
                    afterimageCompensationService4.AfcThreadTerminateCondition = false;
                    return;
                }
                return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                Slog.i("AfterimageCompensationService", "ACTION_SCREEN_OFF");
                AfterimageCompensationService afterimageCompensationService5 = AfterimageCompensationService.this;
                afterimageCompensationService5.AfcThreadCondition = false;
                if (!afterimageCompensationService5.AfcStateCondition || afterimageCompensationService5.mAfcType < 10 || (thread = afterimageCompensationService5.mAfcThread) == null) {
                    return;
                }
                synchronized (thread) {
                    afterimageCompensationService5.mAfcThread.interrupt();
                }
                return;
            }
            if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                Slog.i("AfterimageCompensationService", "ACTION_SHUTDOWN");
                AfterimageCompensationService.this.getClass();
                AfterimageCompensationService.this.getClass();
                AfterimageCompensationService.this.getClass();
                AfterimageCompensationService afterimageCompensationService6 = AfterimageCompensationService.this;
                boolean z = afterimageCompensationService6.AfcStateCondition;
                if (z && afterimageCompensationService6.mAfcType == 6) {
                    int i3 = afterimageCompensationService6.AfpcPeriodCount;
                    if (i3 > 20) {
                        AfterimageCompensationService.nativeDataTerminate(i3);
                        return;
                    }
                    return;
                }
                if (z && afterimageCompensationService6.mAfcType == 7) {
                    int i4 = afterimageCompensationService6.AfpcPeriodCount;
                    if (i4 > 20) {
                        AfterimageCompensationService.nativeDataTerminate(i4);
                        return;
                    }
                    return;
                }
                if (z && afterimageCompensationService6.mAfcType == 8) {
                    int i5 = afterimageCompensationService6.AfpcPeriodCount;
                    if (i5 > 20) {
                        AfterimageCompensationService.nativeDataTerminate(i5);
                        return;
                    }
                    return;
                }
                if (z && ((i2 = afterimageCompensationService6.mAfcType) == 9 || i2 == 11)) {
                    int i6 = afterimageCompensationService6.AfpcPeriodCount;
                    if (i6 > 20) {
                        AfterimageCompensationService.nativeDataTerminate(i6);
                    }
                    int i7 = AfterimageCompensationService.this.AfpcPeriodCount_sub;
                    if (i7 > 20) {
                        AfterimageCompensationService.nativeDataTerminateSub(i7);
                        return;
                    }
                    return;
                }
                if (z && afterimageCompensationService6.mAfcType == 10) {
                    int i8 = afterimageCompensationService6.AfpcPeriodCount;
                    if (i8 > 20) {
                        AfterimageCompensationService.nativeDataTerminate(i8);
                        return;
                    }
                    return;
                }
                if (z && afterimageCompensationService6.mAfcType == 12 && (i = afterimageCompensationService6.AfpcPeriodCount) > 20) {
                    AfterimageCompensationService.nativeDataTerminate(i);
                }
            }
        }
    }

    /* renamed from: -$$Nest$mgetBrightness, reason: not valid java name */
    public static boolean m1194$$Nest$mgetBrightness(AfterimageCompensationService afterimageCompensationService) {
        afterimageCompensationService.getClass();
        if (!new File("/sys/class/lcd/panel/brt_avg").exists()) {
            return false;
        }
        try {
            String readStrFromFile = readStrFromFile("/sys/class/lcd/panel/brt_avg");
            if (readStrFromFile == null) {
                return false;
            }
            try {
                int parseInt = Integer.parseInt(readStrFromFile);
                afterimageCompensationService.mLuminance = parseInt;
                return parseInt >= 0;
            } catch (NumberFormatException e) {
                Slog.e("AfterimageCompensationService", "NumberFormatException : " + e);
                return false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* renamed from: -$$Nest$mgetBrightness_sub, reason: not valid java name */
    public static boolean m1195$$Nest$mgetBrightness_sub(AfterimageCompensationService afterimageCompensationService) {
        afterimageCompensationService.getClass();
        if (!new File("/sys/class/lcd/panel1/brt_avg").exists()) {
            return false;
        }
        try {
            String readStrFromFile = readStrFromFile("/sys/class/lcd/panel1/brt_avg");
            if (readStrFromFile == null) {
                return false;
            }
            try {
                int parseInt = Integer.parseInt(readStrFromFile);
                afterimageCompensationService.mLuminance_sub = parseInt;
                return parseInt >= 0;
            } catch (NumberFormatException e) {
                Slog.e("AfterimageCompensationService", "NumberFormatException : " + e);
                return false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00cd  */
    /* renamed from: -$$Nest$mupdateHWParam, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m1196$$Nest$mupdateHWParam(com.samsung.android.hardware.display.AfterimageCompensationService r19) {
        /*
            r1 = r19
            int r0 = r1.mApplyCount
            java.lang.String r2 = java.lang.Integer.toString(r0)
            float r0 = r1.mApplyValue
            java.lang.String r3 = java.lang.Float.toString(r0)
            double r4 = r1.mAvgLum
            java.lang.String r4 = java.lang.Double.toString(r4)
            double r5 = r1.mMaxBDI
            java.lang.String r5 = java.lang.Double.toString(r5)
            double r6 = r1.mNBDI
            java.lang.String r6 = java.lang.Double.toString(r6)
            double r7 = r1.mEffAvgLum
            java.lang.String r7 = java.lang.Double.toString(r7)
            double r8 = r1.mEffMaxBDI
            java.lang.String r8 = java.lang.Double.toString(r8)
            double r9 = r1.mEffNBDI
            java.lang.String r9 = java.lang.Double.toString(r9)
            int r0 = r1.mApplyCount_sub
            java.lang.String r10 = java.lang.Integer.toString(r0)
            float r0 = r1.mApplyValue_sub
            java.lang.String r11 = java.lang.Float.toString(r0)
            double r12 = r1.mAvgLum_sub
            java.lang.String r12 = java.lang.Double.toString(r12)
            double r13 = r1.mMaxBDI_sub
            java.lang.String r13 = java.lang.Double.toString(r13)
            double r14 = r1.mNBDI_sub
            java.lang.String r14 = java.lang.Double.toString(r14)
            r0 = r14
            double r14 = r1.mEffAvgLum_sub
            java.lang.String r15 = java.lang.Double.toString(r14)
            r16 = r15
            double r14 = r1.mEffMaxBDI_sub
            java.lang.String r17 = java.lang.Double.toString(r14)
            double r14 = r1.mEffNBDI_sub
            java.lang.String r18 = java.lang.Double.toString(r14)
            r14 = r0
            r15 = r16
            r16 = r17
            r17 = r18
            java.lang.String[] r0 = new java.lang.String[]{r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17}
            java.lang.String[] r2 = com.samsung.android.hardware.display.AfterimageCompensationService.mAFPC_KEYS
            r3 = 0
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch: org.json.JSONException -> L8a
            r4.<init>()     // Catch: org.json.JSONException -> L8a
            r3 = 0
        L79:
            r5 = 16
            if (r3 >= r5) goto L8f
            r5 = r2[r3]     // Catch: org.json.JSONException -> L87
            r6 = r0[r3]     // Catch: org.json.JSONException -> L87
            r4.put(r5, r6)     // Catch: org.json.JSONException -> L87
            int r3 = r3 + 1
            goto L79
        L87:
            r0 = move-exception
            r3 = r4
            goto L8b
        L8a:
            r0 = move-exception
        L8b:
            r0.printStackTrace()
            r4 = r3
        L8f:
            java.lang.String r0 = r4.toString()
            java.lang.String r2 = "\\{"
            java.lang.String r3 = ""
            java.lang.String r0 = r0.replaceAll(r2, r3)
            java.lang.String r2 = "\\}"
            java.lang.String r12 = r0.replaceAll(r2, r3)
            boolean r0 = r1.DEBUG
            java.lang.String r2 = "AfterimageCompensationService"
            if (r0 == 0) goto Lac
            java.lang.String r0 = "customDataSet : "
            com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r0, r12, r2)
        Lac:
            android.os.SemHqmManager r0 = r1.mSemHqmManager
            if (r0 == 0) goto Lcd
            java.lang.String r0 = "sendBroadcastToHWParam() mSemHqmManager.sendHWParamToHQM"
            android.util.Slog.i(r2, r0)
            android.os.SemHqmManager r4 = r1.mSemHqmManager
            java.lang.String r9 = "0.0"
            java.lang.String r10 = "sec"
            r5 = 0
            java.lang.String r6 = "Display"
            java.lang.String r7 = "DIQE"
            java.lang.String r8 = "sm"
            java.lang.String r11 = ""
            java.lang.String r13 = ""
            r4.sendHWParamToHQM(r5, r6, r7, r8, r9, r10, r11, r12, r13)
            goto Ld3
        Lcd:
            java.lang.String r0 = "sendBroadcastToHWParam() mSemHqmManager is null"
            android.util.Slog.d(r2, r0)
        Ld3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.hardware.display.AfterimageCompensationService.m1196$$Nest$mupdateHWParam(com.samsung.android.hardware.display.AfterimageCompensationService):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0670  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x06ee A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x06fb  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0733  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0753 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0764  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0798  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x07f6  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x083f  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0857  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x06ea  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0653  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x048c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0465  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0564  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x05ba  */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v34 */
    /* JADX WARN: Type inference failed for: r9v35 */
    /* JADX WARN: Type inference failed for: r9v36 */
    /* JADX WARN: Type inference failed for: r9v37 */
    /* JADX WARN: Type inference failed for: r9v38 */
    /* JADX WARN: Type inference failed for: r9v39 */
    /* JADX WARN: Type inference failed for: r9v40 */
    /* JADX WARN: Type inference failed for: r9v45 */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AfterimageCompensationService(android.content.Context r33) {
        /*
            Method dump skipped, instructions count: 3054
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.hardware.display.AfterimageCompensationService.<init>(android.content.Context):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0075 A[Catch: IOException -> 0x0071, TryCatch #4 {IOException -> 0x0071, blocks: (B:44:0x006d, B:33:0x0075, B:35:0x007a, B:37:0x007f), top: B:43:0x006d }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x007a A[Catch: IOException -> 0x0071, TryCatch #4 {IOException -> 0x0071, blocks: (B:44:0x006d, B:33:0x0075, B:35:0x007a, B:37:0x007f), top: B:43:0x006d }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007f A[Catch: IOException -> 0x0071, TRY_LEAVE, TryCatch #4 {IOException -> 0x0071, blocks: (B:44:0x006d, B:33:0x0075, B:35:0x007a, B:37:0x007f), top: B:43:0x006d }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void fileCopy() {
        /*
            java.lang.String r0 = "/cache/recovery/poc.vec"
            java.lang.String r1 = "/dev/poc"
            r2 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4f
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4f
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L42 java.io.IOException -> L46
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L42 java.io.IOException -> L46
            java.nio.channels.FileChannel r1 = r3.getChannel()     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L3f
            java.nio.channels.FileChannel r2 = r0.getChannel()     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L3a
            long r7 = r1.size()     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L3a
            r5 = 0
            r4 = r1
            r9 = r2
            r4.transferTo(r5, r7, r9)     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L3a
            if (r2 == 0) goto L2a
            r2.close()     // Catch: java.io.IOException -> L28
            goto L2a
        L28:
            r0 = move-exception
            goto L34
        L2a:
            r1.close()     // Catch: java.io.IOException -> L28
            r3.close()     // Catch: java.io.IOException -> L28
            r0.close()     // Catch: java.io.IOException -> L28
            goto L6a
        L34:
            r0.printStackTrace()
            goto L6a
        L38:
            r4 = move-exception
            goto L6b
        L3a:
            r4 = move-exception
            goto L53
        L3c:
            r4 = move-exception
            r1 = r2
            goto L6b
        L3f:
            r4 = move-exception
            r1 = r2
            goto L53
        L42:
            r4 = move-exception
            r0 = r2
            r1 = r0
            goto L6b
        L46:
            r4 = move-exception
            r0 = r2
            r1 = r0
            goto L53
        L4a:
            r4 = move-exception
            r0 = r2
            r1 = r0
            r3 = r1
            goto L6b
        L4f:
            r4 = move-exception
            r0 = r2
            r1 = r0
            r3 = r1
        L53:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L38
            if (r2 == 0) goto L5b
            r2.close()     // Catch: java.io.IOException -> L28
        L5b:
            if (r1 == 0) goto L60
            r1.close()     // Catch: java.io.IOException -> L28
        L60:
            if (r3 == 0) goto L65
            r3.close()     // Catch: java.io.IOException -> L28
        L65:
            if (r0 == 0) goto L6a
            r0.close()     // Catch: java.io.IOException -> L28
        L6a:
            return
        L6b:
            if (r2 == 0) goto L73
            r2.close()     // Catch: java.io.IOException -> L71
            goto L73
        L71:
            r0 = move-exception
            goto L83
        L73:
            if (r1 == 0) goto L78
            r1.close()     // Catch: java.io.IOException -> L71
        L78:
            if (r3 == 0) goto L7d
            r3.close()     // Catch: java.io.IOException -> L71
        L7d:
            if (r0 == 0) goto L86
            r0.close()     // Catch: java.io.IOException -> L71
            goto L86
        L83:
            r0.printStackTrace()
        L86:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.hardware.display.AfterimageCompensationService.fileCopy():void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v9 */
    public static String getStringFromFile(String str) {
        byte[] bArr = new byte[128];
        for (int i = 0; i < 128; i++) {
            bArr[i] = 0;
        }
        FileInputStream fileInputStream = null;
        r2 = 0;
        ?? r2 = 0;
        FileInputStream fileInputStream2 = null;
        FileInputStream fileInputStream3 = null;
        try {
            try {
                try {
                    FileInputStream fileInputStream4 = new FileInputStream(new File(str));
                    try {
                        int read = fileInputStream4.read(bArr);
                        r2 = read != 0 ? new String(bArr, 0, read - 1, StandardCharsets.UTF_8) : 0;
                        fileInputStream4.close();
                        try {
                            fileInputStream4.close();
                            return r2;
                        } catch (IOException unused) {
                            Slog.e("AfterimageCompensationService", "File Close error");
                            return r2;
                        }
                    } catch (FileNotFoundException e) {
                        e = e;
                        bArr = r2;
                        fileInputStream2 = fileInputStream4;
                        Slog.e("AfterimageCompensationService", "FileNotFoundException : " + e);
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        return bArr;
                    } catch (IOException e2) {
                        e = e2;
                        bArr = r2;
                        fileInputStream3 = fileInputStream4;
                        e.printStackTrace();
                        Slog.e("AfterimageCompensationService", "IOException : " + e);
                        if (fileInputStream3 != null) {
                            fileInputStream3.close();
                        }
                        return bArr;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream4;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException unused2) {
                                Slog.e("AfterimageCompensationService", "File Close error");
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e3) {
                    e = e3;
                    bArr = null;
                } catch (IOException e4) {
                    e = e4;
                    bArr = null;
                }
            } catch (IOException unused3) {
                Slog.e("AfterimageCompensationService", "File Close error");
                return bArr;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static native int nativeDataApply(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native float nativeDataEvaluate();

    private static native int nativeDataInit(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    private static native int nativeDataInitSub(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    private static native int nativeDataOff();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataRead(int i, int i2, int i3, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadAvgLum();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadAvgLumSub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffAvgLum();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffAvgLumSub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffMaxBDI();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffMaxBDISub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffNBDI();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffNBDISub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadMaxBDI();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadMaxBDISub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadNBDI();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadNBDISub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataReadSub(int i, int i2, int i3, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataSave(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataSaveSub(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataTerminate(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataTerminateSub(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataUpdate(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataUpdateSub(int i);

    private static native int nativeDataVerify();

    private static native int nativeDataWrite(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataWriteV2(int i);

    private static native int nativeMdnieBlockVerify(boolean z);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v23 */
    /* JADX WARN: Type inference failed for: r7v28 */
    /* JADX WARN: Type inference failed for: r7v30 */
    /* JADX WARN: Type inference failed for: r7v37 */
    /* JADX WARN: Type inference failed for: r7v39 */
    /* JADX WARN: Type inference failed for: r7v40 */
    /* JADX WARN: Type inference failed for: r7v41 */
    /* JADX WARN: Type inference failed for: r7v42 */
    /* JADX WARN: Type inference failed for: r7v43 */
    /* JADX WARN: Type inference failed for: r7v44 */
    /* JADX WARN: Type inference failed for: r7v45 */
    /* JADX WARN: Type inference failed for: r7v46 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.io.BufferedReader] */
    public static String readStrFromFile(String str) {
        FileReader fileReader;
        Throwable th;
        BufferedReader bufferedReader;
        PrintStream printStream;
        StringBuilder sb;
        String str2 = null;
        try {
            try {
                fileReader = new FileReader((String) str);
            } catch (FileNotFoundException e) {
                e = e;
                bufferedReader = null;
                fileReader = null;
            } catch (IOException e2) {
                e = e2;
                bufferedReader = null;
                fileReader = null;
            } catch (Throwable th2) {
                fileReader = null;
                th = th2;
                str = 0;
            }
            try {
                bufferedReader = new BufferedReader(fileReader);
            } catch (FileNotFoundException e3) {
                e = e3;
                bufferedReader = null;
            } catch (IOException e4) {
                e = e4;
                bufferedReader = null;
            } catch (Throwable th3) {
                th = th3;
                str = 0;
                if (str != 0) {
                    try {
                        str.close();
                    } catch (IOException e5) {
                        System.out.println("BufferedReader Close IOException : " + e5.getMessage());
                    }
                }
                if (fileReader == null) {
                    throw th;
                }
                try {
                    fileReader.close();
                    throw th;
                } catch (IOException e6) {
                    System.out.println("FileReader Close IOException : " + e6.getMessage());
                    throw th;
                }
            }
            try {
                str2 = bufferedReader.readLine();
                try {
                    bufferedReader.close();
                    str = bufferedReader;
                } catch (IOException e7) {
                    PrintStream printStream2 = System.out;
                    String str3 = "BufferedReader Close IOException : " + e7.getMessage();
                    printStream2.println(str3);
                    str = str3;
                }
            } catch (FileNotFoundException e8) {
                e = e8;
                e.printStackTrace();
                str = bufferedReader;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                        str = bufferedReader;
                    } catch (IOException e9) {
                        PrintStream printStream3 = System.out;
                        String str4 = "BufferedReader Close IOException : " + e9.getMessage();
                        printStream3.println(str4);
                        str = str4;
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e10) {
                        e = e10;
                        printStream = System.out;
                        sb = new StringBuilder("FileReader Close IOException : ");
                        sb.append(e.getMessage());
                        printStream.println(sb.toString());
                        return str2;
                    }
                }
                return str2;
            } catch (IOException e11) {
                e = e11;
                e.printStackTrace();
                str = bufferedReader;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                        str = bufferedReader;
                    } catch (IOException e12) {
                        PrintStream printStream4 = System.out;
                        String str5 = "BufferedReader Close IOException : " + e12.getMessage();
                        printStream4.println(str5);
                        str = str5;
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e13) {
                        e = e13;
                        printStream = System.out;
                        sb = new StringBuilder("FileReader Close IOException : ");
                        sb.append(e.getMessage());
                        printStream.println(sb.toString());
                        return str2;
                    }
                }
                return str2;
            }
            try {
                fileReader.close();
            } catch (IOException e14) {
                e = e14;
                printStream = System.out;
                sb = new StringBuilder("FileReader Close IOException : ");
                sb.append(e.getMessage());
                printStream.println(sb.toString());
                return str2;
            }
            return str2;
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public final boolean afpcDataApply() {
        Slog.i("AfterimageCompensationService", "afpcDataApply()");
        int i = this.mAfcType;
        if (i != 3 && i < 5) {
            return false;
        }
        if (BatteryService$$ExternalSyntheticOutline0.m45m("/efs/afc/mdnie_block")) {
            nativeDataApply(this.mApplyScaleEffect);
            return true;
        }
        Slog.i("AfterimageCompensationService", "afpcDataApply - AFC_MDNIE_BLOCK not exist");
        return false;
    }

    public final boolean afpcDataOff() {
        Slog.i("AfterimageCompensationService", "afpcDataOff()");
        int i = this.mAfcType;
        if (i != 3 && i < 5) {
            return false;
        }
        nativeDataOff();
        return true;
    }

    public final boolean afpcDataVerify() {
        StringBuilder sb = new StringBuilder("afpcDataVerify() - ");
        int i = this.mAfpcSize;
        sb.append(i);
        sb.append(", ");
        sb.append(i);
        Slog.i("AfterimageCompensationService", sb.toString());
        return nativeDataVerify() == 0;
    }

    public final boolean afpcDataWrite() {
        Slog.i("AfterimageCompensationService", "afpcDataWrite()");
        if (this.mAfcType != 2 || !this.mThreadAFPC || !this.AfcStateCondition) {
            return false;
        }
        int i = this.mApplyCount;
        float[] fArr = this.mAfpcJndRef;
        if (i >= fArr.length) {
            return false;
        }
        float nativeDataEvaluate = nativeDataEvaluate();
        Locale locale = Locale.US;
        float parseFloat = Float.parseFloat(String.format(locale, "%.2f", Float.valueOf(nativeDataEvaluate)));
        this.mApplyValue = parseFloat;
        if (parseFloat < fArr[this.mApplyCount] || nativeDataWrite(this.mApplyScaleEffect) != 0) {
            return false;
        }
        this.mApplyCount++;
        try {
            String str = this.mApplyCount + " " + String.format(locale, "%.2f", Float.valueOf(this.mApplyValue));
            fileWriteString("/efs/afc/apply_count", str);
            Slog.i("AfterimageCompensationService", "afpcDataWrite - str : " + str);
        } catch (NumberFormatException e) {
            Slog.e("AfterimageCompensationService", "NumberFormatException : " + e);
            this.mApplyValue = FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        return true;
    }

    public final boolean fileWriteString(String str, String str2) {
        FileOutputStream fileOutputStream;
        if (this.DEBUG) {
            Slog.i("AfterimageCompensationService", "fileWriteString : " + str + "  value : " + str2);
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(new File(str));
            } catch (IOException e) {
                e = e;
            }
            try {
                fileOutputStream.write(str2.getBytes());
                fileOutputStream.close();
                return true;
            } catch (IOException e2) {
                e = e2;
                fileOutputStream2 = fileOutputStream;
                e.printStackTrace();
                try {
                    fileOutputStream2.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                return false;
            }
        } catch (FileNotFoundException unused) {
            Slog.d("AfterimageCompensationService", "fileWriteString : file not found : ".concat(str));
            return false;
        }
    }
}
