package com.samsung.android.service.SemService;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import com.android.internal.midi.MidiConstants;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public final class SemServiceManager {
  public static final int ERROR = -1;
  public static final int ERROR_ADD_DEL_LIST = -16;
  public static final int ERROR_BOOT_DEACT = -17;
  public static final int ERROR_CLASS_NOT_FOUND = -2;
  public static final int ERROR_DATA = -13;
  public static final int ERROR_DEACTIVATION = -18;
  public static final int ERROR_EXCEPTION = -90;
  public static final int ERROR_FACTORY_ERROR = -10;
  public static final int ERROR_NOT_SUPPORTED = -10;
  public static final int ERROR_NO_PERMISSION = -91;
  public static final int ERROR_NO_SERVICE = -92;
  public static final int ERROR_SELECT_ERROR = -11;
  public static final int ERROR_SEND_ERROR = -12;
  public static final int ERROR_UNSAT_LINK = -3;
  public static final int ESESTATUS_BUSY = -200;
  public static final int ESESTATUS_NOT_SUPPORTED = -100;
  private static final int MAX_CAPDU_SIZE = 65545;
  private static final int MAX_RAPDU_SIZE = 65538;
  public static final int NO_ERROR = 0;
  public static final int NO_ERROR_SPI = 0;
  public static final int SSD_NOT_EXIST_APPLET_EXIST = 5;
  public static final int SSD_NOT_EXIST_APPLET_NOT_EXIST = 4;
  public static final int SSD_NOT_SELECTABLE_APPLET_EXIST = 2;
  public static final int SSD_NOT_SELECTABLE_APPLET_NOT_EXIST = 3;
  public static final int SSD_NOT_SELECTABLE_APPLET_NOT_IN_SSD = 6;
  public static final int SSD_SELECTABLE_APPLET_EXIST = 0;
  public static final int SSD_SELECTABLE_APPLET_NOT_EXIST = 1;
  private static final String TAG = "SEC_ESE_ServiceManager";
  public static final boolean isSupportSemService = false;
  private static final int normalSpi_Flag = 0;
  private static final int secureSpi_Flag = 1;
  private boolean isSupportEseChip = !"".isEmpty();
  private Context mContext;
  private ISemService mSemService;
  public static final String ERROR_NO_PERMISSION_STRING = null;
  private static final char[] HEX_CHARS = {
    '0',
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9',
    DateFormat.CAPITAL_AM_PM,
    'B',
    'C',
    'D',
    DateFormat.DAY,
    'F'
  };

  /* renamed from: -$$Nest$fgetmContext, reason: not valid java name */
  static /* bridge */ /* synthetic */ Context m9187$$Nest$fgetmContext(
      SemServiceManager semServiceManager) {
    return semServiceManager.mContext;
  }

  /* renamed from: -$$Nest$mAIDDeactivation, reason: not valid java name */
  static /* bridge */ /* synthetic */ int m9188$$Nest$mAIDDeactivation(
      SemServiceManager semServiceManager, ArrayList arrayList) {
    return semServiceManager.AIDDeactivation(arrayList);
  }

  /* renamed from: -$$Nest$mparseList, reason: not valid java name */
  static /* bridge */ /* synthetic */ ArrayList m9189$$Nest$mparseList(
      SemServiceManager semServiceManager, byte[] bArr, int i) {
    return semServiceManager.parseList(bArr, i);
  }

  public SemServiceManager(Context context) {
    this.mContext = context;
    Log.m102w(TAG, "SemService is not supported");
  }

  public boolean isConnected() {
    Log.m98i(TAG, "SemService is not supported");
    return false;
  }

  public String get_ESEA() {
    Log.m98i(TAG, "get_ESEA() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return null;
  }

  public String getCPLC14mode() {
    Log.m98i(TAG, "getCPLC14mode() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return null;
  }

  public void semFactory() {
    Log.m98i(TAG, "semFactory() is called.");
    Log.m98i(TAG, "SemService is not supported");
  }

  private boolean isSupportEsek() {
    try {
      String propValue = SystemProperties.get("ro.security.ese.support_esek");
      if (!TextUtils.isEmpty(propValue)) {
        if (propValue.equals("1")) {
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      Log.m96e(TAG, "failed to get sysProp: ro.security.ese.support_esek");
      return false;
    }
  }

  public int esekCertificateCheck() {
    Log.m98i(TAG, "esekCertificateCheck() is called.");
    if (!isSupportEsek()) {
      Log.m98i(TAG, "eSEK is not supported");
      return -10;
    }
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int scp11CertificateCheck() {
    Log.m98i(TAG, "scp11CertificateCheck() is called.");
    if (!isSupportEsek()) {
      Log.m98i(TAG, "eSEK is not supported");
      return -10;
    }
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int injectEsesmKeyset() {
    Log.m98i(TAG, "injectEsesmKeyset() is called.");
    if (!isSupportEsek()) {
      Log.m98i(TAG, "eSEK is not supported");
      return -10;
    }
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int verifyEsesmKeyset() {
    Log.m98i(TAG, "verifyEsesmKeyset() is called.");
    if (!isSupportEsek()) {
      Log.m98i(TAG, "eSEK is not supported");
      return -10;
    }
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public String[] sem_handleCCM(byte[] ccmData, int ccmDataLen) {
    Log.m98i(TAG, "sem_handleCCM() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return null;
  }

  public String[] sem_handleCCMCB(byte[] ccmData, int ccmDataLen, byte[] respData, int respLen) {
    Log.m98i(TAG, "sem_handleCCMCB() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return null;
  }

  public int isLccmSwp() {
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int getHQMMemory(byte[] hw_memory_data) {
    Log.m98i(TAG, "getHQMMemory() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int deactivateCards(
      int RequestType, String[] package_name, int[] package_len, int arrayLen) {
    Log.m98i(TAG, "deactivateCards is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int deactivateCardsAID(
      int RequestType, int bean, String[] package_name, int[] package_len, int arrayLen) {
    Log.m98i(TAG, "deactivateCardsAID is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int getSCRSVersion() {
    byte[] bArr = {
      0, -92, 4, 0, 9, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 1, 81, 67, 82, 83, 0
    };
    byte[] bArr2 = {Byte.MIN_VALUE, -54, 0, -16, 0};
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int addSCRSList(String flag, ArrayList<String> aid) {
    byte[] bArr = {
      0, -92, 4, 0, 9, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 0, 0, 1, 81, 67, 82, 83, 0
    };
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int deactivateSCRSList(String inputFlag, ArrayList<String> inputAid) {
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  /* renamed from: com.samsung.android.service.SemService.SemServiceManager$1 */
  class RunnableC52401 implements Runnable {
    final /* synthetic */ ArrayList val$aid;
    final /* synthetic */ String val$flag;

    RunnableC52401(String str, ArrayList arrayList) {
      this.val$flag = str;
      this.val$aid = arrayList;
    }

    /*  JADX ERROR: Types fix failed
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
    */
    /* JADX WARN: Failed to apply debug info
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnoreUnknown(TypeUpdate.java:74)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:137)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:133)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.searchAndApplyVarDebugInfo(DebugInfoApplyVisitor.java:75)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.lambda$applyDebugInfo$0(DebugInfoApplyVisitor.java:68)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:68)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.visit(DebugInfoApplyVisitor.java:55)
     */
    /* JADX WARN: Not initialized variable reg: 23, insn: 0x0c09: MOVE (r10 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r23 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('aidListByteDataLen' int)]), block:B:514:0x0c08 */
    /* JADX WARN: Not initialized variable reg: 23, insn: 0x0c15: MOVE (r10 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r23 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('aidListByteDataLen' int)]), block:B:516:0x0c14 */
    /* JADX WARN: Not initialized variable reg: 23, insn: 0x0c21: MOVE (r10 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r23 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('aidListByteDataLen' int)]), block:B:512:0x0c20 */
    /* JADX WARN: Not initialized variable reg: 23, insn: 0x0c2d: MOVE (r10 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r23 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('aidListByteDataLen' int)]), block:B:518:0x0c2c */
    /* JADX WARN: Not initialized variable reg: 23, insn: 0x0c39: MOVE (r10 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r23 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('aidListByteDataLen' int)]), block:B:510:0x0c38 */
    /* JADX WARN: Not initialized variable reg: 23, insn: 0x0d20: MOVE (r10 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r23 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('aidListByteDataLen' int)]), block:B:569:0x0d1f */
    /* JADX WARN: Not initialized variable reg: 23, insn: 0x0d27: MOVE (r10 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r23 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('aidListByteDataLen' int)]), block:B:573:0x0d26 */
    /* JADX WARN: Not initialized variable reg: 23, insn: 0x0d2e: MOVE (r10 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r23 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('aidListByteDataLen' int)]), block:B:577:0x0d2d */
    /* JADX WARN: Not initialized variable reg: 23, insn: 0x0d36: MOVE (r10 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r23 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('aidListByteDataLen' int)]), block:B:575:0x0d35 */
    /* JADX WARN: Not initialized variable reg: 23, insn: 0x0d3e: MOVE (r10 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r23 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('aidListByteDataLen' int)]), block:B:571:0x0d3d */
    /* JADX WARN: Not initialized variable reg: 25, insn: 0x0c0b: MOVE (r14 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r25 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isOpen' boolean)]), block:B:514:0x0c08 */
    /* JADX WARN: Not initialized variable reg: 25, insn: 0x0c17: MOVE (r14 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r25 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isOpen' boolean)]), block:B:516:0x0c14 */
    /* JADX WARN: Not initialized variable reg: 25, insn: 0x0c23: MOVE (r14 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r25 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isOpen' boolean)]), block:B:512:0x0c20 */
    /* JADX WARN: Not initialized variable reg: 25, insn: 0x0c2f: MOVE (r14 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r25 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isOpen' boolean)]), block:B:518:0x0c2c */
    /* JADX WARN: Not initialized variable reg: 25, insn: 0x0c3b: MOVE (r14 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r25 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isOpen' boolean)]), block:B:510:0x0c38 */
    /* JADX WARN: Not initialized variable reg: 26, insn: 0x0c0d: MOVE (r15 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r26 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isMoreData' boolean)]), block:B:514:0x0c08 */
    /* JADX WARN: Not initialized variable reg: 26, insn: 0x0c19: MOVE (r15 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r26 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isMoreData' boolean)]), block:B:516:0x0c14 */
    /* JADX WARN: Not initialized variable reg: 26, insn: 0x0c25: MOVE (r15 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r26 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isMoreData' boolean)]), block:B:512:0x0c20 */
    /* JADX WARN: Not initialized variable reg: 26, insn: 0x0c31: MOVE (r15 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r26 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isMoreData' boolean)]), block:B:518:0x0c2c */
    /* JADX WARN: Not initialized variable reg: 26, insn: 0x0c3d: MOVE (r15 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r26 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isMoreData' boolean)]), block:B:510:0x0c38 */
    /* JADX WARN: Not initialized variable reg: 26, insn: 0x0d22: MOVE (r15 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r26 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isMoreData' boolean)]), block:B:569:0x0d1f */
    /* JADX WARN: Not initialized variable reg: 26, insn: 0x0d29: MOVE (r15 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r26 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isMoreData' boolean)]), block:B:573:0x0d26 */
    /* JADX WARN: Not initialized variable reg: 26, insn: 0x0d30: MOVE (r15 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r26 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isMoreData' boolean)]), block:B:577:0x0d2d */
    /* JADX WARN: Not initialized variable reg: 26, insn: 0x0d38: MOVE (r15 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r26 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isMoreData' boolean)]), block:B:575:0x0d35 */
    /* JADX WARN: Not initialized variable reg: 26, insn: 0x0d40: MOVE (r15 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r26 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isMoreData' boolean)]), block:B:571:0x0d3d */
    /* JADX WARN: Not initialized variable reg: 27, insn: 0x0c0f: MOVE (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r27 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('ret' int)]), block:B:514:0x0c08 */
    /* JADX WARN: Not initialized variable reg: 27, insn: 0x0c1b: MOVE (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r27 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('ret' int)]), block:B:516:0x0c14 */
    /* JADX WARN: Not initialized variable reg: 27, insn: 0x0c27: MOVE (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r27 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('ret' int)]), block:B:512:0x0c20 */
    /* JADX WARN: Not initialized variable reg: 27, insn: 0x0c33: MOVE (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r27 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('ret' int)]), block:B:518:0x0c2c */
    /* JADX WARN: Not initialized variable reg: 27, insn: 0x0c3f: MOVE (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r27 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('ret' int)]), block:B:510:0x0c38 */
    @Override // java.lang.Runnable
    public void run() {
      /*
          Method dump skipped, instructions count: 3753
          To view this dump add '--comments-level debug' option
      */
      throw new UnsupportedOperationException(
          "Method not decompiled:"
              + " com.samsung.android.service.SemService.SemServiceManager.RunnableC52401.run():void");
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public int AIDDeactivation(ArrayList<String> AID) {
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int eSEFactoryReset() {
    Log.m98i(TAG, "eSEFactoryReset() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int eSELowFactoryReset() {
    Log.m98i(TAG, "eSELowFactoryReset() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int eSEFullFactoryReset() {
    Log.m98i(TAG, "eSEFullFactoryReset() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public void checkFRANetwork(int type) {
    Log.m98i(TAG, "checkFRANetwork() is called. " + type);
    Log.m98i(TAG, "SemService is not supported");
  }

  /* JADX INFO: Access modifiers changed from: private */
  public ArrayList<String> parseList(byte[] respAid, int respAidLen) {
    if (respAid == null) {
      Log.m96e(TAG, "parse list aid null error");
      return null;
    }
    new ArrayList();
    Log.m98i(TAG, "SemService is not supported");
    return null;
  }

  public int jniICD() {
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int startattestation(byte[] drRsp, int drLen, byte[] svRsp, int svLen) {
    Log.m98i(TAG, "startattestation() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int continueattestation(String data, int dataLen, byte[] rspData) {
    Log.m98i(TAG, "continueattestation() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int getPK(byte[] rspData) {
    Log.m98i(TAG, "getPK() is called.");
    return 0;
  }

  public void sercureLog(String msg) {
    Log.m98i(TAG, "SecureLog() is called.");
    Log.m98i(TAG, "SemService is not supported");
  }

  public void startSLOG() {
    Log.m98i(TAG, "START SLOG is called.");
    Log.m98i(TAG, "SemService is not supported");
  }

  public void stopSLOG() {
    Log.m98i(TAG, "STOP SLOG is called.");
    Log.m98i(TAG, "SemService is not supported");
  }

  public int getAtr() {
    Log.m98i(TAG, "getAtr() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int accessControlForCOSU(int type) {
    return 0;
  }

  public int resetForCOSU(int type) {
    Log.m98i(TAG, "resetForCOSU is called. " + type);
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int open() {
    Log.m98i(TAG, "open() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int close() {
    Log.m98i(TAG, "close() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public boolean isOpened() {
    Log.m94d(TAG, "isOpened() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return true;
  }

  public byte[] send(byte[] baCmd) {
    Log.m98i(TAG, "send() for SE API is called.");
    Log.m98i(TAG, "SemService is not supported");
    return null;
  }

  public int open(int flag) {
    Log.m98i(TAG, "open() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int close(int flag) {
    Log.m98i(TAG, "close() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public byte[] send(byte[] baCmd, int flag) {
    Log.m98i(TAG, "send() for normal/secure SPI is called.");
    Log.m98i(TAG, "SemService is not supported");
    return null;
  }

  public int checkSeState(byte[] appletAid, byte[] associatedAid) {
    Log.m98i(TAG, "checkSeState() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int startRequestCredentials(
      byte[] appletAid, byte[] associatedAid, String serviceName, byte[] key_blob) {
    Log.m98i(TAG, "startRequestCredentials() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public void stopRequestCredentials() {
    Log.m98i(TAG, "stopRequestCredentials() is called.");
    Log.m98i(TAG, "SemService is not supported");
  }

  public int grdmGetSession() {
    Log.m98i(TAG, "grdmGetSession() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int grdmRequestKey(int domainIndex, byte[] key_blob) {
    Log.m98i(TAG, "grdmRequestKey() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int grdmReleaseSession() {
    Log.m98i(TAG, "grdmReleaseSession() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int grdmGetAttesCert(int index, byte[] rspData) {
    Log.m98i(TAG, "grdmGetAttesCert() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public String grdmCheckRestrictedMode() {
    Log.m98i(TAG, "grdmCheckRestrictedMode() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return null;
  }

  public int grdmCheckStatus() {
    Log.m98i(TAG, "grdmCheckStatus() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public static String bytesToHex(byte[] data) {
    if (data == null || data.length < 1) {
      return null;
    }
    char[] chars = new char[data.length * 2];
    for (int i = 0; i < data.length; i++) {
      char[] cArr = HEX_CHARS;
      chars[i * 2] = cArr[(data[i] & 240) >>> 4];
      chars[(i * 2) + 1] = cArr[data[i] & 15];
    }
    return new String(chars);
  }

  public static String byteToHex(byte data) {
    char[] cArr = HEX_CHARS;
    char[] array = {cArr[(data >> 4) & 15], cArr[data & 15]};
    return new String(array);
  }

  public static byte[] hexToBytes(String str) {
    if (str == null || str.length() < 2) {
      return null;
    }
    int len = str.length() / 2;
    byte[] buffer = new byte[len];
    for (int i = 0; i < len; i++) {
      buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16);
    }
    return buffer;
  }

  public boolean isEseSupported() {
    Log.m98i(TAG, "isEseSupported() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return false;
  }

  public int openSpi(int flag) {
    Log.m98i(TAG, "openSpi() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int closeSpi(int flag) {
    Log.m98i(TAG, "closeSpi() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int sendData(byte[] baCmd, int cLen, byte[] baRsp, int flag) {
    Log.m98i(TAG, "sendData() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int sendData(byte[] baCmd, int cLen, byte[] baRsp) {
    Log.m98i(TAG, "sendData() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }

  public int COSSPIAccessControl(int type) {
    Log.m98i(TAG, "COSSPIAccessControl() is called.");
    Log.m98i(TAG, "SemService is not supported");
    return -92;
  }
}
