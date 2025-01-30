package com.samsung.android.service.ProtectedATCommand;

import android.app.ActivityManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.SystemProperties;
import android.security.keystore.KeyProperties;
import android.util.Slog;
import com.samsung.android.service.ProtectedATCommand.list.ATCommands;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;

/* loaded from: classes5.dex */
public class PACMClassifier {
  public static final int ATD = 1;
  public static final int ATDDDEXERR = 0;
  public static final int DDEX = 2;
  public static final int NOK = 0;

  /* renamed from: OK */
  public static final int f3045OK = 1;
  private static final String PROP_FACBIN_PROPERTY = "ro.factory.factory_binary";
  private static final String PROP_ISMDFPPENABLED_PROPERTY = "security.mdf";
  private static final String PROP_SHIPBIN_PROPERTY = "ro.product_ship";
  private static final String TAG = "PACMClassifier";
  private static final String mSalesCode =
      SystemProperties.get("ro.csc.sales_code", KeyProperties.DIGEST_NONE).trim().toUpperCase();
  private final Object mLock = new Object();

  private static boolean checkNullParameter(Object... args) {
    int idx = 1;
    boolean isNull = false;
    for (Object arg : args) {
      if (arg == null) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Slog.m115e(
            TAG,
            NavigationBarInflaterView.SIZE_MOD_START
                + stackTrace[2].getMethodName()
                + "] Parameter("
                + idx
                + ") is null.");
        isNull = true;
      }
      idx++;
    }
    return isNull;
  }

  private static ATCommands findATCommands(
      LinkedHashMap<String, LinkedHashSet<ATCommands>> atMap, String name, ATCommands atCmd) {
    if (checkNullParameter(atMap, name, atCmd)) {
      return null;
    }
    LinkedHashSet<ATCommands> cmdSet = atMap.get(name);
    Iterator<ATCommands> it = cmdSet.iterator();
    while (it.hasNext()) {
      ATCommands CmdRet = it.next();
      if (atCmd.equals(CmdRet)) {
        return CmdRet;
      }
    }
    Slog.m117i(TAG, "findATCommands Failed to find command.");
    return null;
  }

  private static String getName(String cmd, String token) {
    if (cmd == null) {
      Slog.m115e(TAG, "getName cmd is null.");
      return null;
    }
    try {
      StringTokenizer st = new StringTokenizer(cmd);
      st.nextToken(token);
      if (st.hasMoreTokens()) {
        String tmp = st.nextToken();
        String name = tmp.split("=")[0];
        if (name != null) {
          return name;
        }
      }
    } catch (Exception e) {
      Slog.m115e(TAG, "getName error occured.");
      e.printStackTrace();
    }
    return null;
  }

  public static ATCommands getCommand(
      LinkedHashMap<String, LinkedHashSet<ATCommands>> atMap, String cmd) {
    Slog.m117i(TAG, "getCommand() is called.");
    if (checkNullParameter(atMap, cmd)) {
      return null;
    }
    String cmdName = cmd;
    try {
      int i = 0;
      String[] tokens = {"+", "$", "^", "#"};
      int length = tokens.length;
      while (true) {
        if (i >= length) {
          break;
        }
        String token = tokens[i];
        if (cmd.indexOf("AT" + token) != 0) {
          i++;
        } else {
          cmdName = getName(cmd, token);
          break;
        }
      }
      if (cmdName == null) {
        Slog.m115e(TAG, "Failed to get cmd name(" + cmd + NavigationBarInflaterView.KEY_CODE_END);
        return null;
      }
      LinkedHashSet<ATCommands> cmdPool = atMap.get(cmdName);
      if (cmdPool == null) {
        Slog.m117i(TAG, "This cmd(" + cmdName + ") is not registered");
        return null;
      }
      ATCommands atCmd = new ATCommands(cmdName, cmd.getBytes());
      if (!cmdPool.contains(atCmd)) {
        Slog.m117i(TAG, "This cmd(" + cmdName + ") is not registered.");
        return null;
      }
      ATCommands foundedRet = findATCommands(atMap, cmdName, atCmd);
      if (foundedRet == null) {
        Slog.m115e(TAG, "Failed to find AT Commands");
        return null;
      }
      return foundedRet;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static int checkSpecialCommand(String cmd) {
    if (cmd == null) {
      Slog.m115e(TAG, "cmd is null");
      return -268435456;
    }
    String[] spcCmds = {"ATD", "AT+CDV", "AT+TESTSPECIAL"};
    try {
      for (String spcCmd : spcCmds) {
        if (cmd.indexOf(spcCmd) == 0) {
          return 1;
        }
      }
      return 255;
    } catch (Exception e) {
      e.printStackTrace();
      return -268435456;
    }
  }

  public static int isJDMProtectedCommand(String cmd) {
    Slog.m117i(TAG, "Input Cmd : " + cmd);
    if (cmd == null) {
      Slog.m115e(TAG, "cmd is null");
      return -268435456;
    }
    String[] protectedCmds = {
      "AT+ALERTDIS=0,", "AT+DEBUGLVC=0,5", "AT+DEBUGLVC=0,6", "AT+DEVROOTK=2,2,", "AT+DEVROOTK=2,3,"
    };
    try {
      for (String protectedCmd : protectedCmds) {
        if (cmd.indexOf(protectedCmd) == 0) {
          Slog.m117i(TAG, "This command is a protected command");
          return 1;
        }
      }
      return 176;
    } catch (Exception e) {
      e.printStackTrace();
      return -268435456;
    }
  }

  public static int isJDMOpenCommand(String cmd) {
    Slog.m117i(TAG, "Input Cmd : " + cmd);
    if (cmd == null) {
      Slog.m115e(TAG, "cmd is null");
      return -268435456;
    }
    String[] openCmds = {"AT+IFPMICCN=0,0,6,0", "AT+BATTTEST=4,7", "AT+PRODCODE=2,"};
    try {
      for (String openCmd : openCmds) {
        if (cmd.indexOf(openCmd) == 0) {
          Slog.m117i(TAG, "This command is a JDM open command");
          return 1;
        }
      }
      return 176;
    } catch (Exception e) {
      e.printStackTrace();
      return -268435456;
    }
  }

  public static boolean isMaintenanceMode() {
    try {
      int userID = ActivityManager.getCurrentUser();
      maintenanceMode = userID == 77;
      Slog.m113d(TAG, "Maintenance mode : " + maintenanceMode);
    } catch (Exception e) {
      Slog.m116e(TAG, "Failed to get maintenance mode", e);
    }
    return maintenanceMode;
  }

  public static boolean hasAutoBlockerAttribute(ATCommands atCmd) {
    if (atCmd == null) {
      return false;
    }
    ATCommands.ExtendedAttribute attribute = atCmd.getExtendedAttribute();
    String cmd = new String(atCmd.getCmdBytes(), Charset.forName("UTF-8"));
    if (!atCmd.getHasAttribute() || !attribute.getAutoBlockerOpen()) {
      return false;
    }
    Slog.m113d(TAG, cmd + " is always opened regardless of Auto Blocker");
    return true;
  }

  public static int checkAttribute(ATCommands atCmd, boolean isSecureLock, int atdddex) {
    ATCommands.ExtendedAttribute attribute = atCmd.getExtendedAttribute();
    String cmd = new String(atCmd.getCmdBytes(), Charset.forName("UTF-8"));
    String isShipBin = SystemProperties.get(PROP_SHIPBIN_PROPERTY);
    String isFacBin = SystemProperties.get(PROP_FACBIN_PROPERTY);
    String isMDFEnable = SystemProperties.get(PROP_ISMDFPPENABLED_PROPERTY);
    if (isMDFEnable.equals("Enabled")) {
      Slog.m117i(TAG, cmd + " is blocked when CC mode is enabled");
      return 193;
    }
    if (isSecureLock && isShipBin != null && isShipBin.equals("true") && !isMaintenanceMode()) {
      if (atCmd.getHasAttribute()) {
        if (!attribute.getSecureLockOpen()) {
          Slog.m117i(
              TAG,
              cmd
                  + " is blocked when secure lock is on there is no SLO althought there is"
                  + " attribute");
          return 192;
        }
      } else {
        Slog.m117i(TAG, cmd + " is blocked when secure lock is on because there is no attribute");
        return 192;
      }
    }
    if (atCmd.getHasAttribute()) {
      if (attribute.getCarrierOpen()) {
        String carrierOpenList = attribute.getCarrierOpenList();
        String str = mSalesCode;
        if (!carrierOpenList.contains(str)) {
          Slog.m117i(
              TAG,
              cmd
                  + " is opened in only ("
                  + attribute.getCarrierOpenList()
                  + ") device, so this cmd is block in "
                  + str
                  + " device");
          return 196;
        }
      }
      if (attribute.getCarrierBlock()) {
        String carrierBlockList = attribute.getCarrierBlockList();
        String str2 = mSalesCode;
        if (carrierBlockList.contains(str2)) {
          Slog.m117i(TAG, cmd + " is blocked in " + str2 + " device");
          return 197;
        }
      }
      if (attribute.getShipBlock()
          && isShipBin != null
          && isShipBin.equals("true")
          && atdddex == 2) {
        Slog.m117i(
            TAG,
            cmd
                + " must be used in only no ship binary. So this is blocked because this binary is"
                + " ship binary.");
        return 194;
      }
      if (isFacBin != null && !isFacBin.equals("factory")) {
        if (attribute.getFacBinOpenATDDDEX()) {
          Slog.m117i(
              TAG,
              cmd
                  + " must be used in only factory binary. So this is blocked because this binary"
                  + " is not factory binary.");
          return 198;
        }
        if (attribute.getFacBinOpenATD() && atdddex == 1) {
          Slog.m117i(
              TAG,
              cmd
                  + " from ATD must be used in only factory binary. So this is blocked because this"
                  + " binary is not factory binary.");
          return 198;
        }
        if (attribute.getFacBinOpenDDEX() && atdddex == 2) {
          Slog.m117i(
              TAG,
              cmd
                  + " from DDEX must be used in only factory binary. So this is blocked because"
                  + " this binary is not factory binary.");
          return 198;
        }
      }
      if (attribute.getCSOpen()) {
        Slog.m117i(TAG, cmd + " is only opend in Galaxy Diag Tool.");
        return 195;
      }
    }
    return atCmd.getType();
  }

  public static int putCommandList(
      LinkedHashMap<String, LinkedHashSet<ATCommands>> atMap, String cmdName, ATCommands atCmd) {
    if (checkNullParameter(atMap, cmdName, atCmd)) {
      return -268435456;
    }
    LinkedHashSet<ATCommands> base = null;
    if (atMap.containsKey(cmdName)) {
      LinkedHashSet<ATCommands> base2 = atMap.get(cmdName);
      base = base2;
      atMap.remove(cmdName);
    }
    if (base == null) {
      base = new LinkedHashSet<>();
    }
    base.add(atCmd);
    atMap.put(cmdName, base);
    return 1;
  }

  public static int putCommandList(
      LinkedHashMap<String, LinkedHashSet<ATCommands>> atMap, List<ATCommands> list) {
    if (checkNullParameter(atMap, list)) {
      return -268435456;
    }
    for (ATCommands CmdRet : list) {
      putCommandList(atMap, CmdRet.getName(), CmdRet);
    }
    return 1;
  }
}
