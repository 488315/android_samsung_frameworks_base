package com.android.server.net;

import android.app.ActivityManager;
import android.net.NetworkPolicyManager;
import android.os.PowerExemptionManager;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.am.ProcessList;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

/* loaded from: classes2.dex */
public class NetworkPolicyLogger {
  public static final boolean LOGD = Log.isLoggable("NetworkPolicy", 3);
  public static final boolean LOGV = Log.isLoggable("NetworkPolicy", 2);
  public static final int MAX_LOG_SIZE;
  public static final int MAX_NETWORK_BLOCKED_LOG_SIZE;
  public int mDebugUid;
  public final LogBuffer mEventsBuffer;
  public final Object mLock;
  public final LogBuffer mNetworkBlockedBuffer = new LogBuffer(MAX_NETWORK_BLOCKED_LOG_SIZE);
  public final LogBuffer mUidStateChangeBuffer;

  public NetworkPolicyLogger() {
    int i = MAX_LOG_SIZE;
    this.mUidStateChangeBuffer = new LogBuffer(i);
    this.mEventsBuffer = new LogBuffer(i);
    this.mDebugUid = -1;
    this.mLock = new Object();
  }

  static {
    MAX_LOG_SIZE = ActivityManager.isLowRamDeviceStatic() ? 100 : 400;
    MAX_NETWORK_BLOCKED_LOG_SIZE = ActivityManager.isLowRamDeviceStatic() ? 100 : 400;
  }

  public void networkBlocked(int i, NetworkPolicyManagerService.UidBlockedState uidBlockedState) {
    synchronized (this.mLock) {
      if (LOGD || i == this.mDebugUid) {
        Slog.d("NetworkPolicy", "Blocked state of " + i + ": " + uidBlockedState);
      }
      if (uidBlockedState == null) {
        this.mNetworkBlockedBuffer.networkBlocked(i, 0, 0, 0);
      } else {
        this.mNetworkBlockedBuffer.networkBlocked(
            i,
            uidBlockedState.blockedReasons,
            uidBlockedState.allowedReasons,
            uidBlockedState.effectiveBlockedReasons);
      }
    }
  }

  public void uidStateChanged(int i, int i2, long j, int i3) {
    synchronized (this.mLock) {
      if (LOGV || i == this.mDebugUid) {
        Slog.v(
            "NetworkPolicy",
            i
                + " state changed to "
                + ProcessList.makeProcStateString(i2)
                + ",seq="
                + j
                + ",cap="
                + ActivityManager.getCapabilitiesSummary(i3));
      }
      this.mUidStateChangeBuffer.uidStateChanged(i, i2, j, i3);
    }
  }

  public void uidPolicyChanged(int i, int i2, int i3) {
    synchronized (this.mLock) {
      if (LOGV || i == this.mDebugUid) {
        Slog.v("NetworkPolicy", getPolicyChangedLog(i, i2, i3));
      }
      this.mEventsBuffer.uidPolicyChanged(i, i2, i3);
    }
  }

  public void meterednessChanged(int i, boolean z) {
    synchronized (this.mLock) {
      if (LOGD || this.mDebugUid != -1) {
        Slog.d("NetworkPolicy", getMeterednessChangedLog(i, z));
      }
      this.mEventsBuffer.meterednessChanged(i, z);
    }
  }

  public void removingUserState(int i) {
    synchronized (this.mLock) {
      if (LOGD || this.mDebugUid != -1) {
        Slog.d("NetworkPolicy", getUserRemovedLog(i));
      }
      this.mEventsBuffer.userRemoved(i);
    }
  }

  public void restrictBackgroundChanged(boolean z, boolean z2) {
    synchronized (this.mLock) {
      if (LOGD || this.mDebugUid != -1) {
        Slog.d("NetworkPolicy", getRestrictBackgroundChangedLog(z, z2));
      }
      this.mEventsBuffer.restrictBackgroundChanged(z, z2);
    }
  }

  public void deviceIdleModeEnabled(boolean z) {
    synchronized (this.mLock) {
      if (LOGD || this.mDebugUid != -1) {
        Slog.d("NetworkPolicy", getDeviceIdleModeEnabled(z));
      }
      this.mEventsBuffer.deviceIdleModeEnabled(z);
    }
  }

  public void appIdleStateChanged(int i, boolean z) {
    synchronized (this.mLock) {
      if (LOGD || i == this.mDebugUid) {
        Slog.d("NetworkPolicy", getAppIdleChangedLog(i, z));
      }
      this.mEventsBuffer.appIdleStateChanged(i, z);
    }
  }

  public void appIdleWlChanged(int i, boolean z) {
    synchronized (this.mLock) {
      if (LOGD || i == this.mDebugUid) {
        Slog.d("NetworkPolicy", getAppIdleWlChangedLog(i, z));
      }
      this.mEventsBuffer.appIdleWlChanged(i, z);
    }
  }

  public void paroleStateChanged(boolean z) {
    synchronized (this.mLock) {
      if (LOGD || this.mDebugUid != -1) {
        Slog.d("NetworkPolicy", getParoleStateChanged(z));
      }
      this.mEventsBuffer.paroleStateChanged(z);
    }
  }

  public void tempPowerSaveWlChanged(int i, boolean z, int i2, String str) {
    synchronized (this.mLock) {
      if (LOGV || i == UserHandle.getAppId(this.mDebugUid)) {
        Slog.v("NetworkPolicy", getTempPowerSaveWlChangedLog(i, z, i2, str));
      }
      this.mEventsBuffer.tempPowerSaveWlChanged(i, z, i2, str);
    }
  }

  public void uidFirewallRuleChanged(int i, int i2, int i3) {
    synchronized (this.mLock) {
      if (LOGV || i2 == this.mDebugUid) {
        Slog.v("NetworkPolicy", getUidFirewallRuleChangedLog(i, i2, i3));
      }
      this.mEventsBuffer.uidFirewallRuleChanged(i, i2, i3);
    }
  }

  public void firewallChainEnabled(int i, boolean z) {
    synchronized (this.mLock) {
      if (LOGD || this.mDebugUid != -1) {
        Slog.d("NetworkPolicy", getFirewallChainEnabledLog(i, z));
      }
      this.mEventsBuffer.firewallChainEnabled(i, z);
    }
  }

  public void firewallRulesChanged(int i, int[] iArr, int[] iArr2) {
    synchronized (this.mLock) {
      String str =
          "Firewall rules changed for "
              + getFirewallChainName(i)
              + "; uids="
              + Arrays.toString(iArr)
              + "; rules="
              + Arrays.toString(iArr2);
      if (LOGD || this.mDebugUid != -1) {
        Slog.d("NetworkPolicy", str);
      }
      this.mEventsBuffer.event(str);
    }
  }

  public void meteredRestrictedPkgsChanged(Set set) {
    synchronized (this.mLock) {
      String str = "Metered restricted uids: " + set;
      if (LOGD || this.mDebugUid != -1) {
        Slog.d("NetworkPolicy", str);
      }
      this.mEventsBuffer.event(str);
    }
  }

  public void meteredAllowlistChanged(int i, boolean z) {
    synchronized (this.mLock) {
      if (LOGD || this.mDebugUid == i) {
        Slog.d("NetworkPolicy", getMeteredAllowlistChangedLog(i, z));
      }
      this.mEventsBuffer.meteredAllowlistChanged(i, z);
    }
  }

  public void meteredDenylistChanged(int i, boolean z) {
    synchronized (this.mLock) {
      if (LOGD || this.mDebugUid == i) {
        Slog.d("NetworkPolicy", getMeteredDenylistChangedLog(i, z));
      }
      this.mEventsBuffer.meteredDenylistChanged(i, z);
    }
  }

  public void roamingChanged(int i, boolean z) {
    synchronized (this.mLock) {
      if (LOGD || this.mDebugUid != -1) {
        Slog.d("NetworkPolicy", getRoamingChangedLog(i, z));
      }
      this.mEventsBuffer.roamingChanged(i, z);
    }
  }

  public void interfacesChanged(int i, ArraySet arraySet) {
    synchronized (this.mLock) {
      if (LOGD || this.mDebugUid != -1) {
        Slog.d("NetworkPolicy", getInterfacesChangedLog(i, arraySet.toString()));
      }
      this.mEventsBuffer.interfacesChanged(i, arraySet.toString());
    }
  }

  public void setDebugUid(int i) {
    this.mDebugUid = i;
  }

  public void dumpLogs(IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      indentingPrintWriter.println();
      indentingPrintWriter.println("mEventLogs (most recent first):");
      indentingPrintWriter.increaseIndent();
      this.mEventsBuffer.reverseDump(indentingPrintWriter);
      indentingPrintWriter.decreaseIndent();
      indentingPrintWriter.println();
      indentingPrintWriter.println("mNetworkBlockedLogs (most recent first):");
      indentingPrintWriter.increaseIndent();
      this.mNetworkBlockedBuffer.reverseDump(indentingPrintWriter);
      indentingPrintWriter.decreaseIndent();
      indentingPrintWriter.println();
      indentingPrintWriter.println("mUidStateChangeLogs (most recent first):");
      indentingPrintWriter.increaseIndent();
      this.mUidStateChangeBuffer.reverseDump(indentingPrintWriter);
      indentingPrintWriter.decreaseIndent();
    }
  }

  public static String getPolicyChangedLog(int i, int i2, int i3) {
    return "Policy for "
        + i
        + " changed from "
        + NetworkPolicyManager.uidPoliciesToString(i2)
        + " to "
        + NetworkPolicyManager.uidPoliciesToString(i3);
  }

  public static String getMeterednessChangedLog(int i, boolean z) {
    return "Meteredness of netId=" + i + " changed to " + z;
  }

  public static String getUserRemovedLog(int i) {
    return "Remove state for u" + i;
  }

  public static String getRestrictBackgroundChangedLog(boolean z, boolean z2) {
    return "Changed restrictBackground: " + z + "->" + z2;
  }

  public static String getDeviceIdleModeEnabled(boolean z) {
    return "DeviceIdleMode enabled: " + z;
  }

  public static String getAppIdleChangedLog(int i, boolean z) {
    return "App idle state of uid " + i + ": " + z;
  }

  public static String getAppIdleWlChangedLog(int i, boolean z) {
    return "App idle whitelist state of uid " + i + ": " + z;
  }

  public static String getParoleStateChanged(boolean z) {
    return "Parole state: " + z;
  }

  public static String getTempPowerSaveWlChangedLog(int i, boolean z, int i2, String str) {
    return "temp-power-save whitelist for "
        + i
        + " changed to: "
        + z
        + "; reason="
        + PowerExemptionManager.reasonCodeToString(i2)
        + " <"
        + str
        + ">";
  }

  public static String getUidFirewallRuleChangedLog(int i, int i2, int i3) {
    return String.format(
        "Firewall rule changed: %d-%s-%s",
        Integer.valueOf(i2), getFirewallChainName(i), getFirewallRuleName(i3));
  }

  public static String getFirewallChainEnabledLog(int i, boolean z) {
    return "Firewall chain " + getFirewallChainName(i) + " state: " + z;
  }

  public static String getMeteredAllowlistChangedLog(int i, boolean z) {
    return "metered-allowlist for " + i + " changed to " + z;
  }

  public static String getMeteredDenylistChangedLog(int i, boolean z) {
    return "metered-denylist for " + i + " changed to " + z;
  }

  public static String getRoamingChangedLog(int i, boolean z) {
    return "Roaming of netId=" + i + " changed to " + z;
  }

  public static String getInterfacesChangedLog(int i, String str) {
    return "Interfaces of netId=" + i + " changed to " + str;
  }

  public static String getFirewallChainName(int i) {
    return i != 1
        ? i != 2
            ? i != 3
                ? i != 4
                    ? i != 5 ? i != 7 ? String.valueOf(i) : "fw_oem_deny_1" : "low_power_standby"
                    : "restricted"
                : "powersave"
            : "standby"
        : "dozable";
  }

  public static String getFirewallRuleName(int i) {
    return i != 0 ? i != 1 ? i != 2 ? String.valueOf(i) : "deny" : "allow" : "default";
  }

  public final class LogBuffer {
    public int mMaxSize;
    public static final SimpleDateFormat sFormatter =
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS");
    public static final Date sDate = new Date();
    public ArrayList mDataList = new ArrayList();
    public long mCursor = 0;

    public LogBuffer(int i) {
      this.mMaxSize = i;
    }

    public final Data getNextSlot() {
      long j = this.mCursor;
      this.mCursor = 1 + j;
      int indexOf = indexOf(j);
      if (this.mDataList.size() <= this.mMaxSize) {
        this.mDataList.add(new Data());
      }
      return (Data) this.mDataList.get(indexOf);
    }

    public final int indexOf(long j) {
      return (int) Math.abs(j % this.mMaxSize);
    }

    public void uidStateChanged(int i, int i2, long j, int i3) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 2;
      nextSlot.ifield1 = i;
      nextSlot.ifield2 = i2;
      nextSlot.ifield3 = i3;
      nextSlot.lfield1 = j;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void event(String str) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 0;
      nextSlot.sfield1 = str;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void networkBlocked(int i, int i2, int i3, int i4) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 1;
      nextSlot.ifield1 = i;
      nextSlot.ifield2 = i2;
      nextSlot.ifield3 = i3;
      nextSlot.ifield4 = i4;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void uidPolicyChanged(int i, int i2, int i3) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 3;
      nextSlot.ifield1 = i;
      nextSlot.ifield2 = i2;
      nextSlot.ifield3 = i3;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void meterednessChanged(int i, boolean z) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 4;
      nextSlot.ifield1 = i;
      nextSlot.bfield1 = z;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void userRemoved(int i) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 5;
      nextSlot.ifield1 = i;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void restrictBackgroundChanged(boolean z, boolean z2) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 6;
      nextSlot.bfield1 = z;
      nextSlot.bfield2 = z2;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void deviceIdleModeEnabled(boolean z) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 7;
      nextSlot.bfield1 = z;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void appIdleStateChanged(int i, boolean z) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 8;
      nextSlot.ifield1 = i;
      nextSlot.bfield1 = z;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void appIdleWlChanged(int i, boolean z) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 14;
      nextSlot.ifield1 = i;
      nextSlot.bfield1 = z;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void paroleStateChanged(boolean z) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 9;
      nextSlot.bfield1 = z;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void tempPowerSaveWlChanged(int i, boolean z, int i2, String str) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 10;
      nextSlot.ifield1 = i;
      nextSlot.ifield2 = i2;
      nextSlot.bfield1 = z;
      nextSlot.sfield1 = str;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void uidFirewallRuleChanged(int i, int i2, int i3) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 11;
      nextSlot.ifield1 = i;
      nextSlot.ifield2 = i2;
      nextSlot.ifield3 = i3;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void firewallChainEnabled(int i, boolean z) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 12;
      nextSlot.ifield1 = i;
      nextSlot.bfield1 = z;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void meteredAllowlistChanged(int i, boolean z) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 15;
      nextSlot.ifield1 = i;
      nextSlot.bfield1 = z;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void meteredDenylistChanged(int i, boolean z) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 16;
      nextSlot.ifield1 = i;
      nextSlot.bfield1 = z;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void roamingChanged(int i, boolean z) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 17;
      nextSlot.ifield1 = i;
      nextSlot.bfield1 = z;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void interfacesChanged(int i, String str) {
      Data nextSlot = getNextSlot();
      if (nextSlot == null) {
        return;
      }
      nextSlot.reset();
      nextSlot.type = 18;
      nextSlot.ifield1 = i;
      nextSlot.sfield1 = str;
      nextSlot.timeStamp = System.currentTimeMillis();
    }

    public void reverseDump(IndentingPrintWriter indentingPrintWriter) {
      for (int size = this.mDataList.size() - 1; size >= 0; size--) {
        if (this.mDataList.get(size) == null) {
          indentingPrintWriter.println("NULL");
        } else {
          indentingPrintWriter.print(formatDate(((Data) this.mDataList.get(size)).timeStamp));
          indentingPrintWriter.print(" - ");
          indentingPrintWriter.println(getContent((Data) this.mDataList.get(size)));
        }
      }
    }

    public String getContent(Data data) {
      int i = data.type;
      switch (i) {
        case 0:
          return data.sfield1;
        case 1:
          return data.ifield1
              + PackageManagerShellCommandDataLoader.STDIN_PATH
              + NetworkPolicyManagerService.UidBlockedState.toString(
                  data.ifield2, data.ifield3, data.ifield4);
        case 2:
          return data.ifield1
              + XmlUtils.STRING_ARRAY_SEPARATOR
              + ProcessList.makeProcStateString(data.ifield2)
              + XmlUtils.STRING_ARRAY_SEPARATOR
              + ActivityManager.getCapabilitiesSummary(data.ifield3)
              + XmlUtils.STRING_ARRAY_SEPARATOR
              + data.lfield1;
        case 3:
          return NetworkPolicyLogger.getPolicyChangedLog(data.ifield1, data.ifield2, data.ifield3);
        case 4:
          return NetworkPolicyLogger.getMeterednessChangedLog(data.ifield1, data.bfield1);
        case 5:
          return NetworkPolicyLogger.getUserRemovedLog(data.ifield1);
        case 6:
          return NetworkPolicyLogger.getRestrictBackgroundChangedLog(data.bfield1, data.bfield2);
        case 7:
          return NetworkPolicyLogger.getDeviceIdleModeEnabled(data.bfield1);
        case 8:
          return NetworkPolicyLogger.getAppIdleChangedLog(data.ifield1, data.bfield1);
        case 9:
          return NetworkPolicyLogger.getParoleStateChanged(data.bfield1);
        case 10:
          return NetworkPolicyLogger.getTempPowerSaveWlChangedLog(
              data.ifield1, data.bfield1, data.ifield2, data.sfield1);
        case 11:
          return NetworkPolicyLogger.getUidFirewallRuleChangedLog(
              data.ifield1, data.ifield2, data.ifield3);
        case 12:
          return NetworkPolicyLogger.getFirewallChainEnabledLog(data.ifield1, data.bfield1);
        case 13:
        default:
          return String.valueOf(i);
        case 14:
          return NetworkPolicyLogger.getAppIdleWlChangedLog(data.ifield1, data.bfield1);
        case 15:
          return NetworkPolicyLogger.getMeteredAllowlistChangedLog(data.ifield1, data.bfield1);
        case 16:
          return NetworkPolicyLogger.getMeteredDenylistChangedLog(data.ifield1, data.bfield1);
        case 17:
          return NetworkPolicyLogger.getRoamingChangedLog(data.ifield1, data.bfield1);
        case 18:
          return NetworkPolicyLogger.getInterfacesChangedLog(data.ifield1, data.sfield1);
      }
    }

    public final String formatDate(long j) {
      Date date = sDate;
      date.setTime(j);
      return sFormatter.format(date);
    }
  }

  public final class Data {
    public boolean bfield1;
    public boolean bfield2;
    public int ifield1;
    public int ifield2;
    public int ifield3;
    public int ifield4;
    public long lfield1;
    public String sfield1;
    public long timeStamp;
    public int type;

    public void reset() {
      this.sfield1 = null;
    }
  }
}
