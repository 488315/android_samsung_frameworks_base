package com.android.server.location.contexthub;

import android.content.Context;
import android.hardware.contexthub.ContextHubMessage;
import android.hardware.contexthub.NanoappBinary;
import android.hardware.contexthub.NanoappInfo;
import android.hardware.contexthub.NanoappRpcService;
import android.hardware.contexthub.V1_0.ContextHubMsg;
import android.hardware.contexthub.V1_0.NanoAppBinary;
import android.hardware.contexthub.V1_2.HubAppInfo;
import android.hardware.location.ContextHubInfo;
import android.hardware.location.NanoAppMessage;
import android.hardware.location.NanoAppRpcService;
import android.hardware.location.NanoAppState;
import android.util.Log;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class ContextHubServiceUtil {
  public static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("MM/dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault());

  public static int toTransactionResult(int i) {
    if (i == 0) {
      return 0;
    }
    if (i == 5) {
      return 4;
    }
    int i2 = 2;
    if (i != 2) {
      i2 = 3;
      if (i != 3) {
        return 1;
      }
    }
    return i2;
  }

  public static HashMap createContextHubInfoMap(List list) {
    HashMap hashMap = new HashMap();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      ContextHubInfo contextHubInfo = (ContextHubInfo) it.next();
      hashMap.put(Integer.valueOf(contextHubInfo.getId()), contextHubInfo);
    }
    return hashMap;
  }

  public static void copyToByteArrayList(byte[] bArr, ArrayList arrayList) {
    arrayList.clear();
    arrayList.ensureCapacity(bArr.length);
    for (byte b : bArr) {
      arrayList.add(Byte.valueOf(b));
    }
  }

  public static byte[] createPrimitiveByteArray(ArrayList arrayList) {
    byte[] bArr = new byte[arrayList.size()];
    for (int i = 0; i < arrayList.size(); i++) {
      bArr[i] = ((Byte) arrayList.get(i)).byteValue();
    }
    return bArr;
  }

  public static int[] createPrimitiveIntArray(Collection collection) {
    int[] iArr = new int[collection.size()];
    Iterator it = collection.iterator();
    int i = 0;
    while (it.hasNext()) {
      iArr[i] = ((Integer) it.next()).intValue();
      i++;
    }
    return iArr;
  }

  public static NanoAppBinary createHidlNanoAppBinary(
      android.hardware.location.NanoAppBinary nanoAppBinary) {
    NanoAppBinary nanoAppBinary2 = new NanoAppBinary();
    nanoAppBinary2.appId = nanoAppBinary.getNanoAppId();
    nanoAppBinary2.appVersion = nanoAppBinary.getNanoAppVersion();
    nanoAppBinary2.flags = nanoAppBinary.getFlags();
    nanoAppBinary2.targetChreApiMajorVersion = nanoAppBinary.getTargetChreApiMajorVersion();
    nanoAppBinary2.targetChreApiMinorVersion = nanoAppBinary.getTargetChreApiMinorVersion();
    try {
      copyToByteArrayList(nanoAppBinary.getBinaryNoHeader(), nanoAppBinary2.customBinary);
    } catch (IndexOutOfBoundsException e) {
      Log.w("ContextHubServiceUtil", e.getMessage());
    } catch (NullPointerException unused) {
      Log.w("ContextHubServiceUtil", "NanoApp binary was null");
    }
    return nanoAppBinary2;
  }

  public static NanoappBinary createAidlNanoAppBinary(
      android.hardware.location.NanoAppBinary nanoAppBinary) {
    NanoappBinary nanoappBinary = new NanoappBinary();
    nanoappBinary.nanoappId = nanoAppBinary.getNanoAppId();
    nanoappBinary.nanoappVersion = nanoAppBinary.getNanoAppVersion();
    nanoappBinary.flags = nanoAppBinary.getFlags();
    nanoappBinary.targetChreApiMajorVersion = nanoAppBinary.getTargetChreApiMajorVersion();
    nanoappBinary.targetChreApiMinorVersion = nanoAppBinary.getTargetChreApiMinorVersion();
    nanoappBinary.customBinary = new byte[0];
    try {
      nanoappBinary.customBinary = nanoAppBinary.getBinaryNoHeader();
    } catch (IndexOutOfBoundsException e) {
      Log.w("ContextHubServiceUtil", e.getMessage());
    } catch (NullPointerException unused) {
      Log.w("ContextHubServiceUtil", "NanoApp binary was null");
    }
    return nanoappBinary;
  }

  public static List createNanoAppStateList(List list) {
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      HubAppInfo hubAppInfo = (HubAppInfo) it.next();
      android.hardware.contexthub.V1_0.HubAppInfo hubAppInfo2 = hubAppInfo.info_1_0;
      arrayList.add(
          new NanoAppState(
              hubAppInfo2.appId, hubAppInfo2.version, hubAppInfo2.enabled, hubAppInfo.permissions));
    }
    return arrayList;
  }

  public static List createNanoAppStateList(NanoappInfo[] nanoappInfoArr) {
    ArrayList arrayList = new ArrayList();
    for (NanoappInfo nanoappInfo : nanoappInfoArr) {
      ArrayList arrayList2 = new ArrayList();
      for (NanoappRpcService nanoappRpcService : nanoappInfo.rpcServices) {
        arrayList2.add(new NanoAppRpcService(nanoappRpcService.id, nanoappRpcService.version));
      }
      arrayList.add(
          new NanoAppState(
              nanoappInfo.nanoappId,
              nanoappInfo.nanoappVersion,
              nanoappInfo.enabled,
              new ArrayList(Arrays.asList(nanoappInfo.permissions)),
              arrayList2));
    }
    return arrayList;
  }

  public static ContextHubMsg createHidlContextHubMessage(short s, NanoAppMessage nanoAppMessage) {
    ContextHubMsg contextHubMsg = new ContextHubMsg();
    contextHubMsg.appName = nanoAppMessage.getNanoAppId();
    contextHubMsg.hostEndPoint = s;
    contextHubMsg.msgType = nanoAppMessage.getMessageType();
    copyToByteArrayList(nanoAppMessage.getMessageBody(), contextHubMsg.msg);
    return contextHubMsg;
  }

  public static ContextHubMessage createAidlContextHubMessage(
      short s, NanoAppMessage nanoAppMessage) {
    ContextHubMessage contextHubMessage = new ContextHubMessage();
    contextHubMessage.nanoappId = nanoAppMessage.getNanoAppId();
    contextHubMessage.hostEndPoint = (char) s;
    contextHubMessage.messageType = nanoAppMessage.getMessageType();
    contextHubMessage.messageBody = nanoAppMessage.getMessageBody();
    contextHubMessage.permissions = new String[0];
    return contextHubMessage;
  }

  public static NanoAppMessage createNanoAppMessage(ContextHubMsg contextHubMsg) {
    return NanoAppMessage.createMessageFromNanoApp(
        contextHubMsg.appName,
        contextHubMsg.msgType,
        createPrimitiveByteArray(contextHubMsg.msg),
        contextHubMsg.hostEndPoint == -1);
  }

  public static NanoAppMessage createNanoAppMessage(ContextHubMessage contextHubMessage) {
    return NanoAppMessage.createMessageFromNanoApp(
        contextHubMessage.nanoappId,
        contextHubMessage.messageType,
        contextHubMessage.messageBody,
        contextHubMessage.hostEndPoint == 65535);
  }

  public static void checkPermissions(Context context) {
    context.enforceCallingOrSelfPermission(
        "android.permission.ACCESS_CONTEXT_HUB",
        "ACCESS_CONTEXT_HUB permission required to use Context Hub");
  }

  public static ArrayList toHubAppInfo_1_2(ArrayList arrayList) {
    ArrayList arrayList2 = new ArrayList();
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      android.hardware.contexthub.V1_0.HubAppInfo hubAppInfo =
          (android.hardware.contexthub.V1_0.HubAppInfo) it.next();
      HubAppInfo hubAppInfo2 = new HubAppInfo();
      android.hardware.contexthub.V1_0.HubAppInfo hubAppInfo3 = hubAppInfo2.info_1_0;
      hubAppInfo3.appId = hubAppInfo.appId;
      hubAppInfo3.version = hubAppInfo.version;
      hubAppInfo3.memUsage = hubAppInfo.memUsage;
      hubAppInfo3.enabled = hubAppInfo.enabled;
      hubAppInfo2.permissions = new ArrayList();
      arrayList2.add(hubAppInfo2);
    }
    return arrayList2;
  }

  public static int toContextHubEvent(int i) {
    if (i == 1) {
      return 1;
    }
    Log.e("ContextHubServiceUtil", "toContextHubEvent: Unknown event type: " + i);
    return 0;
  }

  public static int toContextHubEventFromAidl(int i) {
    if (i == 1) {
      return 1;
    }
    Log.e("ContextHubServiceUtil", "toContextHubEventFromAidl: Unknown event type: " + i);
    return 0;
  }

  public static String formatDateFromTimestamp(long j) {
    return DATE_FORMATTER.format(Instant.ofEpochMilli(j));
  }
}
