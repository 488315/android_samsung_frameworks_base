package com.android.server.knox.dar.ddar.p009ta;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.ServiceManager;
import android.os.UserHandle;
import com.android.server.knox.dar.ddar.DDLog;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import vendor.samsung.hardware.tlc.ddar.ISehDdar;

/* loaded from: classes2.dex */
public class TAProxy extends IProxyAgentService {
  public static Context mContext;
  public static TAProxy mInstance;
  public ISehDdar iSehDdar;
  public Map mTAMap = new HashMap();

  public class TAInfo {
    public int maxRecvRespSize;
    public int maxSendCmdSize;

    /* renamed from: ta */
    public TZNative f1708ta;
    public int taId;
    public String taProcessName;
    public String taRootName;
    public String taTechnology;

    public TAInfo() {}
  }

  public static synchronized TAProxy getInstance(Context context) {
    TAProxy tAProxy;
    synchronized (TAProxy.class) {
      if (mInstance == null) {
        mInstance = new TAProxy(context);
      }
      tAProxy = mInstance;
    }
    return tAProxy;
  }

  public TAProxy(Context context) {
    DDLog.m33d("TAProxy", "TAProxy() called updated", new Object[0]);
    mContext = context;
  }

  /* JADX WARN: Code restructure failed: missing block: B:28:0x0051, code lost:

     if (r7.equals("SETUP_TA") != false) goto L19;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public Bundle onMessage(int i, String str, Bundle bundle) {
    enforceCallingUser(i);
    try {
      char c = 0;
      DDLog.m33d("TAProxy", "onMessage() " + str, new Object[0]);
      Bundle bundle2 = new Bundle();
      switch (str.hashCode()) {
        case -2076560497:
          break;
        case 540100525:
          if (str.equals("UNLOAD_TA")) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case 1054654566:
          if (str.equals("LOAD_TA")) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case 1663958331:
          if (str.equals("PROCESS_COMMAND")) {
            c = 3;
            break;
          }
          c = 65535;
          break;
        default:
          c = 65535;
          break;
      }
      if (c == 0) {
        bundle2.putBoolean("dual_dar_response", setupTARequest(bundle));
      } else if (c == 1) {
        bundle2.putBoolean("dual_dar_response", loadTARequest(bundle));
      } else if (c == 2) {
        bundle2.putBoolean("dual_dar_response", unloadTARequest(bundle));
      } else if (c == 3) {
        bundle2.putBundle("dual_dar_response", processCommandRequest(bundle));
      }
      return bundle2;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public final boolean setupTARequest(Bundle bundle) {
    DDLog.m33d("TAProxy", "setupTARequest called", new Object[0]);
    ArrayList parcelableArrayList = bundle.getParcelableArrayList("ALL_TAS");
    for (int i = 0; i < parcelableArrayList.size(); i++) {
      Bundle bundle2 = (Bundle) parcelableArrayList.get(i);
      int i2 = bundle2.getInt("TA_ID");
      if (!this.mTAMap.containsKey(Integer.valueOf(i2))) {
        createTANative(bundle2, i2);
      }
    }
    DDLog.m33d("TAProxy", "setupTARequest completed", new Object[0]);
    return true;
  }

  public final void createTANative(Bundle bundle, int i) {
    TAInfo tAInfo = new TAInfo();
    tAInfo.taId = i;
    tAInfo.taTechnology = bundle.getString("TA_TECHNOLOGY");
    tAInfo.taRootName = bundle.getString("TA_ROOT_NAME");
    tAInfo.taProcessName = bundle.getString("TA_PROCESS_NAME");
    tAInfo.maxSendCmdSize = bundle.getInt("TA_SEND_CMD_SIZE");
    int i2 = bundle.getInt("TA_RECV_RESP_SIZE");
    tAInfo.maxRecvRespSize = i2;
    tAInfo.f1708ta =
        new TZNative(
            tAInfo.taId,
            tAInfo.taTechnology,
            tAInfo.taRootName,
            tAInfo.taProcessName,
            tAInfo.maxSendCmdSize,
            i2);
    this.mTAMap.put(Integer.valueOf(tAInfo.taId), tAInfo);
  }

  public final boolean loadTARequest(Bundle bundle) {
    DDLog.m33d("TAProxy", "loadTARequest called", new Object[0]);
    return loadTA(
        bundle.getInt("TA_ID"),
        (ParcelFileDescriptor) bundle.getParcelable("TA_FD"),
        bundle.getLong("TA_FD_OFFSET"),
        bundle.getLong("TA_FD_SIZE"));
  }

  public final boolean unloadTARequest(Bundle bundle) {
    DDLog.m33d("TAProxy", "unloadTARequest called", new Object[0]);
    unloadTA(bundle.getInt("TA_ID"));
    return true;
  }

  public final Bundle processCommandRequest(Bundle bundle) {
    DDLog.m33d("TAProxy", "processCommandRequest called", new Object[0]);
    int i = bundle.getInt("TA_ID");
    Bundle bundle2 = new Bundle();
    TACommandRequest tACommandRequest = new TACommandRequest();
    tACommandRequest.init(
        bundle.getInt("TA_VERSION"),
        bundle.getByteArray("TA_MAGICNUM"),
        bundle.getInt("TA_CMD_ID"),
        bundle.getByteArray("TA_CMD_DATA"));
    TACommandResponse processTACommand = processTACommand(i, tACommandRequest);
    if (processTACommand != null) {
      bundle2.putInt("TA_RESP_CODE", processTACommand.mResponseCode);
      bundle2.putString("TA_ERROR_MSG", processTACommand.mErrorMsg);
      bundle2.putByteArray("TA_RESPDATA", processTACommand.mResponse);
    }
    return bundle2;
  }

  public TACommandResponse processTACommand(int i, TACommandRequest tACommandRequest) {
    DDLog.m33d(
        "TAProxy",
        "TAProxy::processTACommand: request = "
            + tACommandRequest
            + "; request.mCommandId = "
            + tACommandRequest.mCommandId
            + "; this.mTAId = "
            + i,
        new Object[0]);
    return ((TAInfo) this.mTAMap.get(Integer.valueOf(i)))
        .f1708ta.processTACommand(tACommandRequest);
  }

  public boolean loadTA(int i, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
    DDLog.m33d("TAProxy", "TAProxy::loadTA", new Object[0]);
    int fd = parcelFileDescriptor != null ? parcelFileDescriptor.getFd() : -1;
    DDLog.m33d("TAProxy", "TA fd=" + fd + " offset=" + j + " size=" + j2, new Object[0]);
    try {
      TAInfo tAInfo = (TAInfo) this.mTAMap.get(Integer.valueOf(i));
      if (tAInfo == null) {
        DDLog.m33d("TAProxy", "TAProxy::loadTA failed. TAInfo is null on Map.", new Object[0]);
        return false;
      }
      boolean loadTA = tAInfo.f1708ta.loadTA(fd, j, j2);
      if (loadTA) {
        updateServiceHolder(true);
      }
      if (parcelFileDescriptor != null) {
        try {
          parcelFileDescriptor.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return loadTA;
    } finally {
      if (parcelFileDescriptor != null) {
        try {
          parcelFileDescriptor.close();
        } catch (IOException e2) {
          e2.printStackTrace();
        }
      }
    }
  }

  public void unloadTA(int i) {
    DDLog.m33d("TAProxy", "TAProxy::unloadTA", new Object[0]);
    TAInfo tAInfo = (TAInfo) this.mTAMap.get(Integer.valueOf(i));
    if (tAInfo == null) {
      DDLog.m33d("TAProxy", "TAProxy::unloadTA failed. TAInfo is null on Map.", new Object[0]);
      return;
    }
    tAInfo.f1708ta.unloadTA();
    this.mTAMap.remove(Integer.valueOf(i));
    if (this.mTAMap.size() <= 0) {
      updateServiceHolder(false);
    }
  }

  public final void enforceCallingUser(int i) {
    DDLog.m33d("TAProxy", "enforceCallingUser", new Object[0]);
    int callingUid = Binder.getCallingUid();
    if (UserHandle.getAppId(callingUid) != 5250
        && UserHandle.getAppId(callingUid) != Process.myUid()) {
      throw new SecurityException("Can only be called by system user");
    }
  }

  public final void updateServiceHolder(boolean z) {
    try {
      if (Integer.parseInt("33") >= 34) {
        boolean isDeclared =
            ServiceManager.isDeclared("vendor.samsung.hardware.tlc.ddar.ISehDdar/default");
        DDLog.m33d(
            "TAProxy",
            "updateServiceHolder: " + isDeclared + ", " + z + ", " + this.iSehDdar,
            new Object[0]);
        if (isDeclared) {
          if (z) {
            ISehDdar iSehDdar = this.iSehDdar;
            if (iSehDdar == null) {
              iSehDdar =
                  ISehDdar.Stub.asInterface(
                      ServiceManager.waitForService(
                          "vendor.samsung.hardware.tlc.ddar.ISehDdar/default"));
            }
            this.iSehDdar = iSehDdar;
            return;
          }
          this.iSehDdar = null;
        }
      }
    } catch (Exception e) {
      DDLog.m34e("TAProxy", "updateServiceHolder failed: " + e, new Object[0]);
    }
  }
}
