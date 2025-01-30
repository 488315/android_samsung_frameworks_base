package com.samsung.android.service.ProtectedATCommand.list;

import android.p009os.SystemProperties;
import android.util.NtpTrustedTime;
import android.util.Slog;
import java.nio.charset.Charset;

/* loaded from: classes5.dex */
public class ATCommands {
  private static final String TAG = "ATCommands";
  private static final boolean mIsTestBinary = "eng".equals(SystemProperties.get("ro.build.type"));
  private byte[] mCmds;
  private ExtendedAttribute mExtendedAttribute;
  private boolean mFlags;
  private boolean mHasAttribute;
  private String mName;
  private int mType;

  public ATCommands() {
    this.mName = "";
    this.mCmds = null;
    this.mFlags = false;
    this.mType = 0;
  }

  public ATCommands(String cmd_name, byte[] cmds) {
    this.mName = cmd_name;
    this.mCmds = cmds;
  }

  public ATCommands(String cmd_name, byte[] cmds, boolean flags) {
    this.mName = cmd_name;
    this.mCmds = cmds;
    this.mFlags = flags;
  }

  public ATCommands(String cmd_name, byte[] cmds, boolean flags, int type) {
    this.mName = cmd_name;
    this.mCmds = cmds;
    this.mFlags = flags;
    this.mType = type;
    this.mHasAttribute = false;
  }

  public ATCommands(String cmd_name, byte[] cmds, boolean flags, int type, boolean hasAttribute) {
    this.mName = cmd_name;
    this.mCmds = cmds;
    this.mFlags = flags;
    this.mType = type;
    this.mHasAttribute = hasAttribute;
    debugLog("CMD Name = " + this.mName);
    debugLog("CMD Type = " + this.mType);
    debugLog("CMD Attribute = " + this.mHasAttribute);
    if (this.mHasAttribute) {
      this.mName = cmd_name.split("\\|")[0];
      ExtendedAttribute extendedAttribute = new ExtendedAttribute();
      this.mExtendedAttribute = extendedAttribute;
      this.mCmds = extendedAttribute.setAttribute(cmds);
    }
  }

  public void setName(String name) {
    this.mName = name;
  }

  public String getName() {
    return this.mName;
  }

  public void setCmdBytes(byte[] cmds) {
    this.mCmds = cmds;
  }

  public byte[] getCmdBytes() {
    return this.mCmds;
  }

  public void setFlags(boolean flags) {
    this.mFlags = flags;
  }

  public boolean getFlags() {
    return this.mFlags;
  }

  public void setType(int type) {
    this.mType = type;
  }

  public int getType() {
    return this.mType;
  }

  public boolean getHasAttribute() {
    return this.mHasAttribute;
  }

  public ExtendedAttribute getExtendedAttribute() {
    return this.mExtendedAttribute;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof ATCommands)) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    ATCommands cmd1 = (ATCommands) obj;
    String c1 = new String(cmd1.getCmdBytes(), Charset.forName("UTF-8"));
    String c2 = new String(this.mCmds, Charset.forName("UTF-8"));
    String[] splited_cmd1 = c1.split("=");
    String[] splited_cmd2 = c2.split("=");
    if (splited_cmd1.length < 2 || splited_cmd2.length < 2) {
      if (c1.contains("=") && !c2.contains("=")) {
        c2 = c2 + "=*";
      } else if (c2.contains("=") && !c1.contains("=")) {
        c1 = c1 + "=*";
      }
      return c1.equals(c2);
    }
    String[] raw_c1 = splited_cmd1[1].split(",");
    String[] raw_c2 = splited_cmd2[1].split(",");
    int size = raw_c2.length > raw_c1.length ? raw_c1.length : raw_c2.length;
    for (int i = 0; i < size; i++) {
      try {
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (raw_c1[i].equals("*")) {
        if ((this.mFlags || cmd1.getFlags()) && Integer.valueOf(raw_c2[i]).intValue() >= 0) {
          if (Integer.valueOf(raw_c2[i]).intValue() <= 9) {
            return false;
          }
        }
        return true;
      }
      if (!raw_c1[i].equals(raw_c2[i])) {
        return false;
      }
    }
    int i2 = raw_c1.length;
    return i2 == raw_c2.length;
  }

  public int hashCode() {
    return this.mName.hashCode();
  }

  public void debugLog(String str) {
    if (mIsTestBinary) {
      Slog.m113d(TAG, str);
    }
  }

  public final class ExtendedAttribute {
    private static final String AUTOBLOCKER_OPEN = "ABO";
    private static final String CARRIER_BLOCK = "CRB";
    private static final String CARRIER_OPEN = "CRO";
    private static final String CSTOOL_OPEN = "CSO";
    private static final String FACBIN_OPEN_ATD = "FBOA";
    private static final String FACBIN_OPEN_ATD_DDEX = "FBOAD";
    private static final String FACBIN_OPEN_DDEX = "FBOD";
    private static final String SECURELOCK_OPEN = "SLO";
    private static final String SHIPBIN_BLOCK = "SBB";
    private boolean mSecureLockOpen = false;
    private boolean mShipBlock = false;
    private boolean mCSOpen = false;
    private boolean mFacBinOpenATDDDEX = false;
    private boolean mFacBinOpenATD = false;
    private boolean mFacBinOpenDDEX = false;
    private boolean mAutoBlockerOpen = false;
    private boolean mCarrierOpen = false;
    private String mCarrierOpenList = null;
    private boolean mCarrierBlock = false;
    private String mCarrierBlockList = null;

    public ExtendedAttribute() {}

    public boolean getSecureLockOpen() {
      return this.mSecureLockOpen;
    }

    public boolean getShipBlock() {
      return this.mShipBlock;
    }

    public boolean getCSOpen() {
      return this.mCSOpen;
    }

    public boolean getCarrierOpen() {
      return this.mCarrierOpen;
    }

    public String getCarrierOpenList() {
      return this.mCarrierOpenList;
    }

    public boolean getCarrierBlock() {
      return this.mCarrierBlock;
    }

    public String getCarrierBlockList() {
      return this.mCarrierBlockList;
    }

    public boolean getFacBinOpenATD() {
      return this.mFacBinOpenATD;
    }

    public boolean getFacBinOpenDDEX() {
      return this.mFacBinOpenDDEX;
    }

    public boolean getFacBinOpenATDDDEX() {
      return this.mFacBinOpenATDDDEX;
    }

    public boolean getAutoBlockerOpen() {
      return this.mAutoBlockerOpen;
    }

    byte[] setAttribute(byte[] cmds) {
      boolean z;
      String cmd = new String(cmds, Charset.forName("UTF-8"));
      boolean z2 = true;
      String[] option =
          cmd.substring(cmd.indexOf(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER) + 1)
              .split("\\|");
      int length = option.length;
      int i = 0;
      while (i < length) {
        String list = option[i];
        ATCommands.this.debugLog("list = " + list);
        if (list.equals(SECURELOCK_OPEN)) {
          ATCommands.this.debugLog("SECURELOCK_OPEN set");
          this.mSecureLockOpen = z2;
        }
        if (list.equals(SHIPBIN_BLOCK)) {
          ATCommands.this.debugLog("SHIPBIN_BLOCK set");
          this.mShipBlock = z2;
        }
        if (list.equals(FACBIN_OPEN_ATD_DDEX)) {
          ATCommands.this.debugLog("FACBIN_OPEN_ATDDDEX set");
          this.mFacBinOpenATDDDEX = z2;
        }
        if (list.equals(FACBIN_OPEN_ATD)) {
          ATCommands.this.debugLog("FACBIN_OPEN_ATD set");
          this.mFacBinOpenATD = z2;
        }
        if (list.equals(FACBIN_OPEN_DDEX)) {
          ATCommands.this.debugLog("FACBIN_OPEN_DDEX set");
          this.mFacBinOpenDDEX = z2;
        }
        if (list.equals(CSTOOL_OPEN)) {
          ATCommands.this.debugLog("CSTOOL_OPEN set");
          this.mCSOpen = z2;
        }
        if (list.equals(AUTOBLOCKER_OPEN)) {
          ATCommands.this.debugLog("AUTOBLOCKER_OPEN set");
          this.mAutoBlockerOpen = z2;
        }
        if (list.contains(CARRIER_OPEN)) {
          ATCommands.this.debugLog("CARRIER_OPEN set");
          if (list.charAt(3) != '(' || list.charAt(list.length() - 1) != ')') {
            Slog.m115e(
                ATCommands.TAG, "#### Error Command Convention, Must check AT Command List File");
            Slog.m115e(ATCommands.TAG, "#### And This command can't operate with attribute");
            break;
          }
          this.mCarrierOpen = true;
          this.mCarrierOpenList =
              list.split(CARRIER_OPEN)[1].substring(1, list.split(CARRIER_OPEN)[1].length() - 1);
        }
        if (!list.contains(CARRIER_BLOCK)) {
          z = true;
        } else {
          ATCommands.this.debugLog("CARRIER_BLOCK set");
          if (list.charAt(3) == '(') {
            z = true;
            if (list.charAt(list.length() - 1) == ')') {
              this.mCarrierBlock = true;
              this.mCarrierBlockList =
                  list.split(CARRIER_BLOCK)[1].substring(
                      1, list.split(CARRIER_BLOCK)[1].length() - 1);
            }
          }
          Slog.m115e(
              ATCommands.TAG, "#### Error Command Convention, Must check AT Command List File");
          Slog.m115e(ATCommands.TAG, "#### And This command can't operate with attribute");
          break;
        }
        i++;
        z2 = z;
      }
      return cmd.split("\\|")[0].getBytes();
    }
  }
}
