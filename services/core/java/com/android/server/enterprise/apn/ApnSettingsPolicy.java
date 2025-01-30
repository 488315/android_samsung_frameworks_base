package com.android.server.enterprise.apn;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemProperties;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.apn.IApnSettingsPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class ApnSettingsPolicy extends IApnSettingsPolicy.Stub
    implements EnterpriseServiceCallback {
  public static int AUTH_TYPE_NOTSET = -1;
  public Context mContext;
  public boolean dunRequired = false;
  public EnterpriseDeviceManager mEDM = null;
  public int mMDMConfigVersion = KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION;
  public HashMap mPendingGetApnList = new HashMap();

  public abstract class Carriers {
    public static final Uri CONTENT_URI = Uri.parse("content://telephony/carriers");
    public static final Uri PREFERAPN_URI = Uri.parse("content://telephony/carriers/preferapn");
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {}

  public final EnterpriseDeviceManager getEDM() {
    if (this.mEDM == null) {
      this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
    }
    return this.mEDM;
  }

  public final ContextInfo enforceOwnerOnlyAndApnPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceOwnerOnlyAndActiveAdminPermission(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_APN")));
  }

  public ApnSettingsPolicy(Context context) {
    this.mContext = context;
  }

  public synchronized boolean setPreferredApn(ContextInfo contextInfo, long j) {
    boolean z;
    enforceOwnerOnlyAndApnPermission(contextInfo);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        if (0 != j && 0 > j) {
          Log.w("ApnSettingsPolicyService", "setPreferedAp() : invalid ap Id " + j);
        } else if (isValidApnId(j)) {
          ContentValues contentValues = new ContentValues();
          if (0 == j) {
            j = -1;
          }
          contentValues.put("apn_id", Long.valueOf(j));
          int update =
              this.mContext
                  .getContentResolver()
                  .update(Carriers.PREFERAPN_URI, contentValues, null, null);
          z = update > 0;
          Log.w("ApnSettingsPolicyService", "setPreferedAp() row count : " + update);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
    return z;
  }

  public synchronized boolean deleteApn(ContextInfo contextInfo, long j) {
    enforceOwnerOnlyAndApnPermission(contextInfo);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    if (1 > j) {
      Log.w("ApnSettingsPolicyService", "deleteAp : apId is invalid");
      return false;
    }
    int i = -1;
    try {
      try {
        i =
            this.mContext
                .getContentResolver()
                .delete(ContentUris.withAppendedId(Carriers.CONTENT_URI, j), null, null);
        Log.w("ApnSettingsPolicyService", "deleteAp : deleted rowCount from ApTable : " + i);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return i > 0;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:163:0x0167, code lost:

     r0 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:165:0x01bc, code lost:

     r0.printStackTrace();
  */
  /* JADX WARN: Code restructure failed: missing block: B:55:0x013c, code lost:

     if (1 <= r5) goto L77;
  */
  /* JADX WARN: Code restructure failed: missing block: B:57:0x0140, code lost:

     r23 = r12;
  */
  /* JADX WARN: Code restructure failed: missing block: B:59:0x0142, code lost:

     android.util.Log.w("ApnSettingsPolicyService", "addUpdateAp() : invalid ap Id " + r5);
  */
  /* JADX WARN: Not initialized variable reg: 24, insn: 0x0389: INVOKE (r24 I:long) STATIC call: android.os.Binder.restoreCallingIdentity(long):void A[Catch: all -> 0x039b, MD:(long):void (c)], block:B:157:0x0389 */
  /* JADX WARN: Removed duplicated region for block: B:102:0x0274 A[Catch: all -> 0x037f, Exception -> 0x0381, TryCatch #3 {Exception -> 0x0381, blocks: (B:90:0x01c6, B:92:0x01d8, B:94:0x01de, B:95:0x01ee, B:99:0x0232, B:100:0x0243, B:102:0x0274, B:103:0x027b, B:110:0x029f, B:111:0x02b8, B:114:0x02c4, B:116:0x02cc, B:118:0x02d4, B:120:0x02e4, B:121:0x02f4, B:123:0x0304, B:124:0x0314, B:127:0x032c, B:131:0x033f, B:136:0x034c, B:140:0x0366, B:142:0x0372, B:143:0x029d, B:149:0x02af), top: B:89:0x01c6, outer: #2 }] */
  /* JADX WARN: Removed duplicated region for block: B:105:0x0294  */
  /* JADX WARN: Removed duplicated region for block: B:120:0x02e4 A[Catch: all -> 0x037f, Exception -> 0x0381, TryCatch #3 {Exception -> 0x0381, blocks: (B:90:0x01c6, B:92:0x01d8, B:94:0x01de, B:95:0x01ee, B:99:0x0232, B:100:0x0243, B:102:0x0274, B:103:0x027b, B:110:0x029f, B:111:0x02b8, B:114:0x02c4, B:116:0x02cc, B:118:0x02d4, B:120:0x02e4, B:121:0x02f4, B:123:0x0304, B:124:0x0314, B:127:0x032c, B:131:0x033f, B:136:0x034c, B:140:0x0366, B:142:0x0372, B:143:0x029d, B:149:0x02af), top: B:89:0x01c6, outer: #2 }] */
  /* JADX WARN: Removed duplicated region for block: B:123:0x0304 A[Catch: all -> 0x037f, Exception -> 0x0381, TryCatch #3 {Exception -> 0x0381, blocks: (B:90:0x01c6, B:92:0x01d8, B:94:0x01de, B:95:0x01ee, B:99:0x0232, B:100:0x0243, B:102:0x0274, B:103:0x027b, B:110:0x029f, B:111:0x02b8, B:114:0x02c4, B:116:0x02cc, B:118:0x02d4, B:120:0x02e4, B:121:0x02f4, B:123:0x0304, B:124:0x0314, B:127:0x032c, B:131:0x033f, B:136:0x034c, B:140:0x0366, B:142:0x0372, B:143:0x029d, B:149:0x02af), top: B:89:0x01c6, outer: #2 }] */
  /* JADX WARN: Removed duplicated region for block: B:126:0x032a  */
  /* JADX WARN: Removed duplicated region for block: B:142:0x0372 A[Catch: all -> 0x037f, Exception -> 0x0381, TRY_LEAVE, TryCatch #3 {Exception -> 0x0381, blocks: (B:90:0x01c6, B:92:0x01d8, B:94:0x01de, B:95:0x01ee, B:99:0x0232, B:100:0x0243, B:102:0x0274, B:103:0x027b, B:110:0x029f, B:111:0x02b8, B:114:0x02c4, B:116:0x02cc, B:118:0x02d4, B:120:0x02e4, B:121:0x02f4, B:123:0x0304, B:124:0x0314, B:127:0x032c, B:131:0x033f, B:136:0x034c, B:140:0x0366, B:142:0x0372, B:143:0x029d, B:149:0x02af), top: B:89:0x01c6, outer: #2 }] */
  /* JADX WARN: Removed duplicated region for block: B:144:0x02a7  */
  /* JADX WARN: Removed duplicated region for block: B:150:0x0279  */
  /* JADX WARN: Removed duplicated region for block: B:75:0x019a A[Catch: Exception -> 0x0167, all -> 0x039b, TryCatch #1 {Exception -> 0x0167, blocks: (B:59:0x0142, B:60:0x0159, B:62:0x015f, B:63:0x0169, B:65:0x016f, B:67:0x0178, B:70:0x0187, B:75:0x019a, B:77:0x01a4, B:159:0x01b1, B:161:0x0190, B:162:0x017e), top: B:58:0x0142 }] */
  /* JADX WARN: Removed duplicated region for block: B:77:0x01a4 A[Catch: Exception -> 0x0167, all -> 0x039b, TryCatch #1 {Exception -> 0x0167, blocks: (B:59:0x0142, B:60:0x0159, B:62:0x015f, B:63:0x0169, B:65:0x016f, B:67:0x0178, B:70:0x0187, B:75:0x019a, B:77:0x01a4, B:159:0x01b1, B:161:0x0190, B:162:0x017e), top: B:58:0x0142 }] */
  /* JADX WARN: Removed duplicated region for block: B:87:0x01c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public synchronized long addUpdateApn(
      ContextInfo contextInfo, boolean z, ApnSettings apnSettings) {
    String str;
    boolean z2;
    long restoreCallingIdentity;
    String str2;
    String str3;
    String validateString;
    String validateString2;
    String validateString3;
    String validateString4;
    String validateString5;
    String validateString6;
    String validateString7;
    String str4;
    ContentValues contentValues;
    String str5;
    String str6;
    long j;
    long parseId;
    int parseInt;
    int i;
    Log.d("ApnSettingsPolicyService", "isNewAp - " + z);
    enforceOwnerOnlyAndApnPermission(contextInfo);
    if (apnSettings == null) {
      return -1L;
    }
    long id = apnSettings.getId();
    String user = apnSettings.getUser();
    String server = apnSettings.getServer();
    String password = apnSettings.getPassword();
    String proxy = apnSettings.getProxy();
    int port = apnSettings.getPort();
    String mmsProxy = apnSettings.getMmsProxy();
    String mmsc = apnSettings.getMmsc();
    String type = apnSettings.getType();
    int authType = apnSettings.getAuthType();
    String validateString8 = validateString(apnSettings.getName());
    String validateString9 = validateString(apnSettings.getApn());
    if (validateString8 != null && validateString9 != null) {
      if (validateString8.length() > 65) {
        Log.w("ApnSettingsPolicyService", "APN name maximum length (65)");
        return -1L;
      }
      if (validateString9.length() > 120) {
        Log.w("ApnSettingsPolicyService", "APN excedes maximum length (120)");
        return -1L;
      }
      String validateString10 = validateString(apnSettings.getMcc());
      String validateString11 = validateString(apnSettings.getMnc());
      String validateString12 = validateString(apnSettings.getMmsPort());
      String str7 = apnSettings.protocol;
      if (!validateProtocol(str7)) {
        Log.e("ApnSettingsPolicyService", "Invalid protocol!");
        return -1L;
      }
      String str8 = apnSettings.roamingProtocol;
      if (!validateProtocol(str8)) {
        Log.e("ApnSettingsPolicyService", "Invalid Roaming Protocol!");
        return -1L;
      }
      String str9 = apnSettings.mvno_type;
      if (!validateMVNOType(str9)) {
        Log.e("ApnSettingsPolicyService", "Invalid mvno type!");
        return -1L;
      }
      String validateString13 = validateString(apnSettings.mvno_value);
      try {
        Integer.parseInt(validateString11);
        Integer.parseInt(validateString10);
        parseInt =
            (validateString12.isEmpty() || validateString12.compareTo("*") == 0)
                ? -1
                : Integer.parseInt(validateString12);
      } catch (Exception e) {
        e = e;
        str = validateString12;
      }
      if ((z || 1 <= id)
          && validateString10 != null
          && validateString10.length() != 0
          && validateString11 != null
          && validateString11.length() != 0
          && validateString8.length() != 0) {
        if (validateString9.length() != 0
            && -1 <= port
            && -1 <= parseInt
            && -2 <= authType
            && 3 >= authType) {
          str = validateString12;
          z2 = true;
          if (z2) {
            try {
              long clearCallingIdentity = Binder.clearCallingIdentity();
              try {
                String simOperator =
                    ((TelephonyManager) this.mContext.getSystemService("phone")).getSimOperator();
                if (simOperator == null || 4 >= simOperator.length()) {
                  str2 = null;
                  str3 = null;
                } else {
                  String substring = simOperator.substring(0, 3);
                  str3 = simOperator.substring(3);
                  str2 = substring;
                }
                validateString(simOperator);
                validateString = validateString(user);
                validateString2 = validateString(server);
                validateString3 = validateString(password);
                validateString4 = validateString(proxy);
                validateString5 = validateString(mmsProxy);
                validateString6 = validateString(mmsc);
                validateString7 = validateString(type);
                str4 = str2;
                contentValues = new ContentValues();
                str5 = str3;
                setColumnValue(z, contentValues, "name", validateString8);
              } catch (Exception e2) {
                e2.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
              }
              if (validateString10 != null && validateString11 != null) {
                str6 = validateString10 + validateString11;
                setColumnValue(z, contentValues, "numeric", str6);
                setColumnValue(z, contentValues, "mcc", validateString10);
                setColumnValue(z, contentValues, "mnc", validateString11);
                setColumnValue(z, contentValues, "apn", validateString9);
                setColumnValue(z, contentValues, "user", validateString);
                setColumnValue(z, contentValues, "server", validateString2);
                setColumnValue(z, contentValues, "password", validateString3);
                setColumnValue(z, contentValues, "proxy", validateString4);
                setColumnValue(z, contentValues, "port", port < 0 ? Integer.toString(port) : "");
                setColumnValue(z, contentValues, "mmsproxy", validateString5);
                setColumnValue(z, contentValues, "mmsport", str);
                setColumnValue(z, contentValues, "mmsc", validateString6);
                if (!z) {
                  if (-2 >= authType || 4 <= authType) {
                    authType = AUTH_TYPE_NOTSET;
                  }
                  contentValues.put("authtype", Integer.valueOf(authType));
                } else if (!z && -2 < authType && 4 > authType) {
                  contentValues.put("authtype", Integer.valueOf(authType));
                }
                setColumnValue(z, contentValues, "type", validateString7);
                if (str5 != null
                    && str4 != null
                    && str5.equals(validateString11)
                    && str4.equals(validateString10)) {
                  contentValues.put("current", (Integer) 1);
                  if (getDeviceSimSlotCnt() > 1) {
                    Log.d("ApnSettingsPolicyService", "IS DUAL SIM");
                    contentValues.put("current1", (Integer) 1);
                  }
                }
                contentValues.put("edited", (Integer) 1);
                if (this.mMDMConfigVersion >= 17) {
                  setColumnValue(z, contentValues, "protocol", str7);
                  setColumnValue(z, contentValues, "roaming_protocol", str8);
                }
                setColumnValue(z, contentValues, "mvno_type", str9);
                setColumnValue(z, contentValues, "mvno_match_data", validateString13);
                if (contentValues.size() > 0) {
                  Log.w("ApnSettingsPolicyService", "addUpdateAp Error: Empty dataset.");
                } else {
                  if (z) {
                    Uri insert =
                        this.mContext
                            .getContentResolver()
                            .insert(Carriers.CONTENT_URI, contentValues);
                    if (insert != null) {
                      Log.w("ApnSettingsPolicyService", "addUpdateAp Created rowUri : ");
                      parseId = ContentUris.parseId(insert);
                      j = parseId;
                    }
                  } else {
                    Uri withAppendedId = ContentUris.withAppendedId(Carriers.CONTENT_URI, id);
                    if (this.mContext
                            .getContentResolver()
                            .update(withAppendedId, contentValues, null, null)
                        > 0) {
                      Log.w("ApnSettingsPolicyService", "addUpdateAp Updated rowUri : ");
                      parseId = ContentUris.parseId(withAppendedId);
                      j = parseId;
                    }
                  }
                  Binder.restoreCallingIdentity(clearCallingIdentity);
                  return j;
                }
                j = -1;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return j;
              }
              str6 = null;
              setColumnValue(z, contentValues, "numeric", str6);
              setColumnValue(z, contentValues, "mcc", validateString10);
              setColumnValue(z, contentValues, "mnc", validateString11);
              setColumnValue(z, contentValues, "apn", validateString9);
              setColumnValue(z, contentValues, "user", validateString);
              setColumnValue(z, contentValues, "server", validateString2);
              setColumnValue(z, contentValues, "password", validateString3);
              setColumnValue(z, contentValues, "proxy", validateString4);
              setColumnValue(z, contentValues, "port", port < 0 ? Integer.toString(port) : "");
              setColumnValue(z, contentValues, "mmsproxy", validateString5);
              setColumnValue(z, contentValues, "mmsport", str);
              setColumnValue(z, contentValues, "mmsc", validateString6);
              if (!z) {}
              setColumnValue(z, contentValues, "type", validateString7);
              if (str5 != null) {
                contentValues.put("current", (Integer) 1);
                if (getDeviceSimSlotCnt() > 1) {}
              }
              contentValues.put("edited", (Integer) 1);
              if (this.mMDMConfigVersion >= 17) {}
              setColumnValue(z, contentValues, "mvno_type", str9);
              setColumnValue(z, contentValues, "mvno_match_data", validateString13);
              if (contentValues.size() > 0) {}
              j = -1;
              Binder.restoreCallingIdentity(clearCallingIdentity);
              return j;
            } catch (Throwable th) {
              Binder.restoreCallingIdentity(restoreCallingIdentity);
              throw th;
            }
          }
          j = -1;
          return j;
        }
      }
      str = validateString12;
      if (validateString8.length() == 0) {
        Log.w("ApnSettingsPolicyService", "addUpdateAp() : invalid ap name");
      }
      if (validateString9.length() == 0) {
        Log.w("ApnSettingsPolicyService", "addUpdateAp() : invalid ap apn");
      }
      if (validateString10 == null || validateString10.length() == 0) {
        Log.w("ApnSettingsPolicyService", "addUpdateAp() : invalid ap mcc");
      }
      if (validateString11 != null) {
        if (validateString11.length() == 0) {}
        i = -1;
        if (-1 > port) {
          Log.w("ApnSettingsPolicyService", "addUpdateAp() : invalid ap port");
          i = -1;
        }
        if (i > parseInt) {
          Log.w("ApnSettingsPolicyService", "addUpdateAp() : invalid ap iMmsPort");
        }
        if (-2 <= authType || 3 < authType) {
          Log.w("ApnSettingsPolicyService", "addUpdateAp() : invalid ap authType");
        }
        z2 = false;
        if (z2) {}
        j = -1;
        return j;
      }
      Log.w("ApnSettingsPolicyService", "addUpdateAp() : invalid ap mnc");
      i = -1;
      if (-1 > port) {}
      if (i > parseInt) {}
      if (-2 <= authType) {}
      Log.w("ApnSettingsPolicyService", "addUpdateAp() : invalid ap authType");
      z2 = false;
      if (z2) {}
      j = -1;
      return j;
    }
    Log.w("ApnSettingsPolicyService", "Invalid value");
    return -1L;
  }

  /* JADX WARN: Code restructure failed: missing block: B:25:0x00ee, code lost:

     if (r0.getString(2).equals("mms") != false) goto L31;
  */
  /* JADX WARN: Code restructure failed: missing block: B:34:0x0102, code lost:

     if (r0 == null) goto L43;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean isValidApnId(long j) {
    boolean z;
    boolean z2 = false;
    if (0 < j) {
      String[] strArr = {KnoxCustomManagerService.f1773ID, "numeric", "type"};
      Cursor cursor = null;
      try {
        try {
          int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
          String simOperator =
              ((TelephonyManager) this.mContext.getSystemService("phone"))
                  .getSimOperator(defaultDataSubscriptionId);
          int phoneId = SubscriptionManager.getPhoneId(defaultDataSubscriptionId);
          String telephonyProperty =
              TelephonyManager.getTelephonyProperty(phoneId, "ril.simoperator", "ETC");
          Log.d(
              "ApnSettingsPolicyService",
              "isValidApnId: simOp = " + telephonyProperty + ", numeric = " + simOperator);
          if ("CTC".equals(telephonyProperty)) {
            String telephonyProperty2 =
                TelephonyManager.getTelephonyProperty(phoneId, "gsm.sim.cdmaoperator.numeric", "");
            simOperator = TextUtils.isEmpty(telephonyProperty2) ? "46003" : telephonyProperty2;
            Log.d(
                "ApnSettingsPolicyService",
                "isValidApnId: CTC card! numeric = "
                    + simOperator
                    + ", cdmaOperator = "
                    + telephonyProperty2);
          }
          cursor =
              this.mContext
                  .getContentResolver()
                  .query(
                      ContentUris.withAppendedId(Carriers.CONTENT_URI, j),
                      strArr,
                      null,
                      null,
                      null);
          if (cursor != null) {
            Log.w(
                "ApnSettingsPolicyService",
                "isValidApnId() row count : " + cursor.getCount() + " for apId : " + j);
            z = cursor.moveToFirst();
          } else {
            z = false;
          }
          if (z) {
            try {
              if (cursor.getString(1).equalsIgnoreCase(simOperator)
                  && cursor.getString(2) != null
                  && !cursor.getString(2).equals("dun")) {}
            } catch (Exception e) {
              z2 = z;
              e = e;
              e.printStackTrace();
            }
          }
          z2 = z;
        } catch (Exception e2) {
          e = e2;
        }
      } finally {
        if (0 != 0) {
          cursor.close();
        }
      }
    } else {
      Log.w("ApnSettingsPolicyService", "isValidApnId() : invalid apId : " + j);
    }
    return z2;
  }

  public List getApnList(ContextInfo contextInfo, int i) {
    enforceOwnerOnlyAndApnPermission(contextInfo);
    ArrayList arrayList = new ArrayList();
    if (this.mPendingGetApnList.containsKey(Integer.valueOf(i))) {
      arrayList.addAll((Collection) this.mPendingGetApnList.get(Integer.valueOf(i)));
    } else {
      arrayList.addAll(retrieveApnListFromDatabase());
    }
    if (arrayList.size()
        >= com.samsung.android.knox.net.apn.ApnSettingsPolicy.MAXIMUM_APNS_OVER_IPC) {
      this.mPendingGetApnList.put(
          Integer.valueOf(i),
          arrayList.subList(
              com.samsung.android.knox.net.apn.ApnSettingsPolicy.MAXIMUM_APNS_OVER_IPC,
              arrayList.size()));
      return arrayList.subList(
          0, com.samsung.android.knox.net.apn.ApnSettingsPolicy.MAXIMUM_APNS_OVER_IPC);
    }
    this.mPendingGetApnList.remove(Integer.valueOf(i));
    return arrayList.subList(0, arrayList.size());
  }

  /* JADX WARN: Removed duplicated region for block: B:7:0x01ca  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final List retrieveApnListFromDatabase() {
    String str;
    ArrayList arrayList;
    String str2 = "mmsport";
    String str3 = "mnc";
    String str4 = "mvno_match_data";
    String str5 = "mcc";
    String str6 = "mvno_type";
    String str7 = "roaming_protocol";
    String str8 = "protocol";
    String str9 = "type";
    Log.d("ApnSettingsPolicyService", "retrieveApnListFromDatabase");
    ArrayList arrayList2 = new ArrayList();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    Cursor cursor = null;
    try {
      ArrayList arrayList3 = arrayList2;
      String[] strArr = {
        KnoxCustomManagerService.f1773ID,
        "name",
        "mcc",
        "mnc",
        "apn",
        "user",
        "server",
        "password",
        "proxy",
        "port",
        "mmsproxy",
        "mmsport",
        "mmsc",
        "authtype",
        str9,
        str8,
        str7,
        str6,
        str4
      };
      int firstSlotIndex = getFirstSlotIndex();
      String str10 = "authtype";
      String str11 = "mmsc";
      Cursor query =
          this.mContext
              .getContentResolver()
              .query(
                  getApnListUri(firstSlotIndex),
                  strArr,
                  getWhereClause(firstSlotIndex),
                  null,
                  null);
      if (query != null) {
        try {
          if (query.moveToFirst()) {
            while (true) {
              ApnSettings apnSettings = new ApnSettings();
              String str12 = str2;
              apnSettings.setId(
                  Long.parseLong(getColumnValue(KnoxCustomManagerService.f1773ID, query)));
              apnSettings.setName(getColumnValue("name", query));
              apnSettings.setMcc(getColumnValue(str5, query));
              apnSettings.setMnc(getColumnValue(str3, query));
              apnSettings.setApn(getColumnValue("apn", query));
              apnSettings.setUser(getColumnValue("user", query));
              apnSettings.setServer(getColumnValue("server", query));
              apnSettings.setPassword(getColumnValue("password", query));
              apnSettings.setProxy(getColumnValue("proxy", query));
              String columnValue = getColumnValue("port", query);
              if (!columnValue.isEmpty()) {
                apnSettings.setPort(Integer.parseInt(columnValue));
              }
              apnSettings.setMmsProxy(getColumnValue("mmsproxy", query));
              apnSettings.setMmsPort(getColumnValue(str12, query));
              String str13 = str3;
              String str14 = str11;
              apnSettings.setMmsc(getColumnValue(str14, query));
              String str15 = str10;
              String columnValue2 = getColumnValue(str15, query);
              if (columnValue2.isEmpty()) {
                str10 = str15;
              } else {
                str10 = str15;
                apnSettings.setAuthType(Integer.parseInt(columnValue2));
              }
              String str16 = str9;
              String str17 = str5;
              apnSettings.setType(getColumnValue(str16, query));
              if (this.mMDMConfigVersion >= 17) {
                String str18 = str8;
                apnSettings.protocol = getColumnValue(str18, query);
                str8 = str18;
                str = str7;
                apnSettings.roamingProtocol = getColumnValue(str, query);
              } else {
                str = str7;
              }
              str7 = str;
              String str19 = str6;
              String columnValue3 = getColumnValue(str19, query);
              if (!columnValue3.isEmpty()) {
                apnSettings.mvno_type = columnValue3;
              }
              str6 = str19;
              String str20 = str4;
              String columnValue4 = getColumnValue(str20, query);
              if (!columnValue4.isEmpty()) {
                apnSettings.mvno_value = columnValue4;
              }
              arrayList = arrayList3;
              arrayList.add(apnSettings);
              if (!query.moveToNext()) {
                break;
              }
              str4 = str20;
              arrayList3 = arrayList;
              str11 = str14;
              str5 = str17;
              str9 = str16;
              str3 = str13;
              str2 = str12;
            }
            if (query != null) {
              query.close();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return arrayList;
          }
        } catch (Throwable th) {
          th = th;
          cursor = query;
          if (cursor != null) {
            cursor.close();
          }
          Binder.restoreCallingIdentity(clearCallingIdentity);
          throw th;
        }
      }
      arrayList = arrayList3;
      if (query != null) {}
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return arrayList;
    } catch (Throwable th2) {
      th = th2;
    }
  }

  public final Uri getApnListUri(int i) {
    Log.d("ApnSettingsPolicyService", "getApnListUri");
    TelephonyManager telephonyManager = TelephonyManager.getDefault();
    if (telephonyManager.getSimState() == 0 || telephonyManager.getSimState() == 1) {
      Log.w("ApnSettingsPolicyService", "No SIM ");
      return Carriers.CONTENT_URI;
    }
    SubscriptionInfo subscriptionInfo =
        getSubscriptionInfo(
            (i == -1 || SubscriptionManager.getSubId(i) == null)
                ? -1
                : SubscriptionManager.getSubId(i)[0]);
    return Uri.withAppendedPath(
        Telephony.Carriers.SIM_APN_URI,
        String.valueOf(subscriptionInfo != null ? subscriptionInfo.getSubscriptionId() : -1));
  }

  public final String getWhereClause(int i) {
    Log.d("ApnSettingsPolicyService", "getWhereClause");
    StringBuilder sb = new StringBuilder("NOT (type='ia' AND (apn=\"\" OR apn IS NULL))");
    int slotIndex =
        SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultDataSubscriptionId());
    boolean z = false;
    boolean z2 =
        (Settings.Global.getInt(this.mContext.getContentResolver(), "phone1_on", 1) == 1)
            && !"0".equals(getTelephonyProperty("ril.ICC_TYPE0", 0, "0"));
    if (getDeviceSimSlotCnt() > 1) {
      String telephonyProperty = getTelephonyProperty("ril.ICC_TYPE1", 1, "0");
      if ((Settings.System.getInt(this.mContext.getContentResolver(), "phone2_on", 1) == 1)
          && !"0".equals(telephonyProperty)) {
        z = true;
      }
      if (z2 && slotIndex == 0) {
        sb.append(" AND current = 1 ");
      } else if (z && slotIndex == 1) {
        sb.append(" AND current1 = 1 ");
      }
    } else if (z2 && slotIndex == 0) {
      sb.append(" AND current = 1");
    }
    return sb.toString();
  }

  public final SubscriptionInfo getSubscriptionInfo(int i) {
    return SubscriptionManager.from(this.mContext).getActiveSubscriptionInfo(i);
  }

  public final int getDeviceSimSlotCnt() {
    int phoneCount = TelephonyManager.getDefault().getPhoneCount();
    Log.d("ApnSettingsPolicyService", "simSlotCnt : " + phoneCount);
    return phoneCount;
  }

  public final int getFirstSlotIndex() {
    int i = 0;
    String telephonyProperty = getTelephonyProperty("ril.ICC_TYPE0", 0, "0");
    String telephonyProperty2 = getTelephonyProperty("ril.ICC_TYPE1", 1, "0");
    boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "phone1_on", 1) == 1;
    boolean z2 = Settings.Global.getInt(this.mContext.getContentResolver(), "phone2_on", 1) == 1;
    if (!z || "0".equals(telephonyProperty)) {
      i = (!z2 || "0".equals(telephonyProperty2)) ? -1 : 1;
    }
    Log.d("ApnSettingsPolicyService", "getFirstSlotIndex : " + i);
    return i;
  }

  public final String getTelephonyProperty(String str, int i, String str2) {
    String str3 = SystemProperties.get("ril.ICC_TYPE0");
    String str4 = SystemProperties.get("ril.ICC_TYPE1");
    if (str.equals("ril.ICC_TYPE0")) {
      if (str3 == null || str3.length() <= 0) {
        str3 = str2;
      }
    } else if (str.equals("ril.ICC_TYPE1")) {
      if (str4 == null || str4.length() <= 0) {
        str4 = str2;
      }
      str3 = str4;
    } else {
      str3 = null;
    }
    if (TextUtils.isEmpty(str3)) {
      str3 = TelephonyManager.getTelephonyProperty(i, str, str2);
    }
    Log.d("ApnSettingsPolicyService", "getTelephonyProperty : " + str3);
    return str3;
  }

  /* JADX WARN: Code restructure failed: missing block: B:10:0x0121, code lost:

     if (r10 != null) goto L38;
  */
  /* JADX WARN: Code restructure failed: missing block: B:11:0x0130, code lost:

     android.os.Binder.restoreCallingIdentity(r1);
  */
  /* JADX WARN: Code restructure failed: missing block: B:12:0x0133, code lost:

     return null;
  */
  /* JADX WARN: Code restructure failed: missing block: B:13:0x012d, code lost:

     r10.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:37:0x012b, code lost:

     if (r10 == null) goto L39;
  */
  /* JADX WARN: Removed duplicated region for block: B:42:0x0138  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public ApnSettings getApnSettings(ContextInfo contextInfo, long j) {
    Cursor cursor;
    enforceOwnerOnlyAndApnPermission(contextInfo);
    Cursor cursor2 = null;
    if (1 > j) {
      Log.w("ApnSettingsPolicyService", "getApnSettings : apId is invalid");
      return null;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      cursor =
          this.mContext
              .getContentResolver()
              .query(ContentUris.withAppendedId(Carriers.CONTENT_URI, j), null, null, null, null);
      if (cursor != null) {
        try {
          try {
            if (cursor.moveToFirst()) {
              ApnSettings apnSettings = new ApnSettings();
              apnSettings.setId(
                  Long.parseLong(getColumnValue(KnoxCustomManagerService.f1773ID, cursor)));
              apnSettings.setName(getColumnValue("name", cursor));
              apnSettings.setMcc(getColumnValue("mcc", cursor));
              apnSettings.setMnc(getColumnValue("mnc", cursor));
              apnSettings.setApn(getColumnValue("apn", cursor));
              apnSettings.setUser(getColumnValue("user", cursor));
              apnSettings.setServer(getColumnValue("server", cursor));
              apnSettings.setPassword(getColumnValue("password", cursor));
              apnSettings.setProxy(getColumnValue("proxy", cursor));
              String columnValue = getColumnValue("port", cursor);
              if (!columnValue.isEmpty()) {
                apnSettings.setPort(Integer.parseInt(columnValue));
              }
              apnSettings.setMmsProxy(getColumnValue("mmsproxy", cursor));
              apnSettings.setMmsPort(getColumnValue("mmsport", cursor));
              apnSettings.setMmsc(getColumnValue("mmsc", cursor));
              String columnValue2 = getColumnValue("authtype", cursor);
              if (!columnValue2.isEmpty()) {
                apnSettings.setAuthType(Integer.parseInt(columnValue2));
              }
              apnSettings.setType(getColumnValue("type", cursor));
              if (this.mMDMConfigVersion >= 17) {
                apnSettings.protocol = getColumnValue("protocol", cursor);
                apnSettings.roamingProtocol = getColumnValue("roaming_protocol", cursor);
              }
              String columnValue3 = getColumnValue("mvno_type", cursor);
              if (!columnValue3.isEmpty()) {
                apnSettings.mvno_type = columnValue3;
              }
              String columnValue4 = getColumnValue("mvno_match_data", cursor);
              if (!columnValue4.isEmpty()) {
                apnSettings.mvno_value = columnValue4;
              }
              cursor.close();
              Binder.restoreCallingIdentity(clearCallingIdentity);
              return apnSettings;
            }
          } catch (Exception e) {
            e = e;
            e.printStackTrace();
          }
        } catch (Throwable th) {
          th = th;
          cursor2 = cursor;
          if (cursor2 != null) {
            cursor2.close();
          }
          Binder.restoreCallingIdentity(clearCallingIdentity);
          throw th;
        }
      }
    } catch (Exception e2) {
      e = e2;
      cursor = null;
    } catch (Throwable th2) {
      th = th2;
      if (cursor2 != null) {}
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  /* JADX WARN: Not initialized variable reg: 3, insn: 0x0096: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:28:0x0096 */
  /* JADX WARN: Removed duplicated region for block: B:30:0x0099 A[Catch: all -> 0x00a0, TRY_ENTER, TryCatch #0 {, blocks: (B:3:0x0001, B:9:0x006c, B:10:0x006f, B:25:0x008f, B:30:0x0099, B:31:0x009c, B:32:0x009f), top: B:2:0x0001 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public synchronized ApnSettings getPreferredApn(ContextInfo contextInfo) {
    ApnSettings apnSettings;
    Cursor cursor;
    ApnSettings apnSettings2;
    enforceOwnerOnlyAndApnPermission(contextInfo);
    Log.d("ApnSettingsPolicyService", "getPreferredApn():");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    apnSettings = null;
    try {
      try {
        cursor =
            this.mContext
                .getContentResolver()
                .query(
                    Carriers.PREFERAPN_URI,
                    new String[] {KnoxCustomManagerService.f1773ID, "name", "apn"},
                    null,
                    null,
                    "name ASC");
        if (cursor != null) {
          try {
            if (cursor.moveToFirst()) {
              long j =
                  cursor.getLong(cursor.getColumnIndexOrThrow(KnoxCustomManagerService.f1773ID));
              Log.w("ApnSettingsPolicyService", "getPreferredApn():" + j);
              if (j > 0) {
                apnSettings = getApnSettings(contextInfo, j);
              }
            }
          } catch (Exception e) {
            e = e;
            Log.w("ApnSettingsPolicyService", "getPreferredApn Ex: " + e);
            if (cursor != null) {
              cursor.close();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return apnSettings;
          }
        }
        if (cursor != null) {
          cursor.close();
        }
      } catch (Throwable th) {
        th = th;
        apnSettings = apnSettings2;
        if (apnSettings != null) {
          apnSettings.close();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    } catch (Exception e2) {
      e = e2;
      cursor = null;
    } catch (Throwable th2) {
      th = th2;
      if (apnSettings != null) {}
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    return apnSettings;
  }

  public final String getColumnValue(String str, Cursor cursor) {
    try {
      String string = cursor.getString(cursor.getColumnIndexOrThrow(str));
      return string == null ? "" : string;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public final void setColumnValue(
      boolean z, ContentValues contentValues, String str, String str2) {
    if (z) {
      contentValues.put(str, str2 == null ? "" : str2.trim());
    } else if (str2 != null) {
      contentValues.put(str, str2.trim());
    }
    Log.w("ApnSettingsPolicyService", "setColumnValue: key=" + str + " value=" + str2);
  }

  public final String validateString(String str) {
    return str == null ? str : str.trim();
  }

  public final boolean validateProtocol(String str) {
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    str.hashCode();
    switch (str) {
    }
    return false;
  }

  public final boolean validateMVNOType(String str) {
    if (str != null) {
      switch (str) {
        case "":
        case "gid":
        case "spn":
        case "imsi":
          return true;
      }
    }
    return false;
  }
}
