package com.android.server.desktopmode;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.os.Binder;
import android.os.ShellCommand;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class Shell extends ShellCommand {
  public HardwareManager mHwManager;
  public MultiResolutionManager mMultiResolutionManager;
  public ContentResolver mResolver;
  public UiManager mUiManager;

  public Shell(
      ContentResolver contentResolver,
      MultiResolutionManager multiResolutionManager,
      UiManager uiManager,
      HardwareManager hardwareManager) {
    this.mResolver = contentResolver;
    this.mMultiResolutionManager = multiResolutionManager;
    this.mUiManager = uiManager;
    this.mHwManager = hardwareManager;
  }

  public int onCommand(String str) {
    int multiResolutionManagerCommand;
    if (str == null) {
      return handleDefaultCommands((String) null);
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    switch (str) {
      case "resolution":
        multiResolutionManagerCommand = multiResolutionManagerCommand();
        break;
      case "newdex":
        multiResolutionManagerCommand = enableNewDex();
        break;
      case "toggle":
      case "on":
      case "off":
        multiResolutionManagerCommand = this.mHwManager.command(getOutPrintWriter(), str);
        break;
      case "ui":
        multiResolutionManagerCommand = uiManagerCommand();
        break;
      case "settings":
        multiResolutionManagerCommand = desktopModeSettingsCommand();
        break;
      default:
        multiResolutionManagerCommand = handleDefaultCommands(str);
        break;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    return multiResolutionManagerCommand;
  }

  public final int enableNewDex() {
    String nextArg = getNextArg();
    if (nextArg == null) {
      return -1;
    }
    DesktopModeSettings.setSettingsOrThrowException(this.mResolver, "enable_new_dex_home", nextArg);
    return 0;
  }

  public final int uiManagerCommand() {
    char c;
    try {
      String nextArg = getNextArg();
      if (nextArg == null) {
        uiManagerPrintUsage();
        return -1;
      }
      String nextArg2 = getNextArg();
      String nextArg3 = getNextArg();
      if (getNextArg() != null) {
        uiManagerPrintUsage();
        return -1;
      }
      switch (nextArg.hashCode()) {
        case -2118182298:
          if (nextArg.equals("dismissOverlay")) {
            c = 5;
            break;
          }
          c = 65535;
          break;
        case -1528850031:
          if (nextArg.equals("startActivity")) {
            c = 4;
            break;
          }
          c = 65535;
          break;
        case -1066651761:
          if (nextArg.equals("removeNotification")) {
            c = 6;
            break;
          }
          c = 65535;
          break;
        case -869293886:
          if (nextArg.equals("finishActivity")) {
            c = 7;
            break;
          }
          c = 65535;
          break;
        case -788388728:
          if (nextArg.equals("showNotification")) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case -703128941:
          if (nextArg.equals("showOverlay")) {
            c = 3;
            break;
          }
          c = 65535;
          break;
        case -256832398:
          if (nextArg.equals("dismissDialog")) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case 343003813:
          if (nextArg.equals("showDialog")) {
            c = 0;
            break;
          }
          c = 65535;
          break;
        default:
          c = 65535;
          break;
      }
      switch (c) {
        case 0:
          this.mUiManager.showDialog(Integer.parseInt(nextArg2), Integer.parseInt(nextArg3), null);
          return 0;
        case 1:
          this.mUiManager.dismissDialog(Integer.parseInt(nextArg2), Integer.parseInt(nextArg3));
          return 0;
        case 2:
          if (nextArg3 != null) {
            uiManagerPrintUsage();
            return -1;
          }
          this.mUiManager.showNotification(Integer.parseInt(nextArg2));
          return 0;
        case 3:
          this.mUiManager.showOverlay(Integer.parseInt(nextArg2), Integer.parseInt(nextArg3));
          return 0;
        case 4:
          this.mUiManager.startActivity(
              Integer.parseInt(nextArg2), Integer.parseInt(nextArg3), null);
          return 0;
        case 5:
          this.mUiManager.dismissOverlay(Integer.parseInt(nextArg2), Integer.parseInt(nextArg3));
          return 0;
        case 6:
          if (nextArg3 != null) {
            uiManagerPrintUsage();
            return -1;
          }
          this.mUiManager.removeNotification(Integer.parseInt(nextArg2));
          return 0;
        case 7:
          if (nextArg3 != null) {
            uiManagerPrintUsage();
            return -1;
          }
          this.mUiManager.finishActivity(Integer.parseInt(nextArg2));
          return 0;
        default:
          uiManagerPrintUsage();
          return 0;
      }
    } catch (IllegalArgumentException unused) {
      uiManagerPrintUsage();
      return -1;
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:15:0x0047  */
  /* JADX WARN: Removed duplicated region for block: B:25:0x0078  */
  /* JADX WARN: Removed duplicated region for block: B:29:0x0084 A[Catch: IllegalArgumentException -> 0x00b2, TryCatch #0 {IllegalArgumentException -> 0x00b2, blocks: (B:3:0x0001, B:5:0x000c, B:8:0x001a, B:16:0x0049, B:18:0x004d, B:26:0x007a, B:28:0x007e, B:29:0x0084, B:30:0x005f, B:33:0x006a, B:36:0x008a, B:38:0x00a8, B:40:0x00ac, B:41:0x002e, B:44:0x0039), top: B:2:0x0001 }] */
  /* JADX WARN: Removed duplicated region for block: B:36:0x008a A[Catch: IllegalArgumentException -> 0x00b2, TryCatch #0 {IllegalArgumentException -> 0x00b2, blocks: (B:3:0x0001, B:5:0x000c, B:8:0x001a, B:16:0x0049, B:18:0x004d, B:26:0x007a, B:28:0x007e, B:29:0x0084, B:30:0x005f, B:33:0x006a, B:36:0x008a, B:38:0x00a8, B:40:0x00ac, B:41:0x002e, B:44:0x0039), top: B:2:0x0001 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int multiResolutionManagerCommand() {
    boolean z;
    boolean z2;
    try {
      PrintWriter outPrintWriter = getOutPrintWriter();
      if (peekNextArg() == null) {
        outPrintWriter.println(this.mMultiResolutionManager.getCustomDisplayMetrics());
        multiResolutionManagerPrintUsage();
      } else {
        String nextArg = getNextArg();
        int hashCode = nextArg.hashCode();
        if (hashCode != -613926062) {
          if (hashCode == 113762 && nextArg.equals("set")) {
            z = false;
            if (z) {
              int parseInt = Integer.parseInt(getNextArgRequired());
              int parseInt2 = Integer.parseInt(getNextArgRequired());
              int parseInt3 = Integer.parseInt(getNextArgRequired());
              if (getNextArg() != null) {
                multiResolutionManagerPrintUsage();
                return -1;
              }
              this.mMultiResolutionManager.setCustomResolutionFromAdbCommand(
                  outPrintWriter, parseInt, parseInt2, parseInt3);
            } else if (z) {
              String nextArgRequired = getNextArgRequired();
              int hashCode2 = nextArgRequired.hashCode();
              if (hashCode2 != 3551) {
                if (hashCode2 == 109935 && nextArgRequired.equals("off")) {
                  z2 = true;
                  if (z2) {
                    this.mMultiResolutionManager.setSupportAllResolution(true);
                  } else if (z2) {
                    this.mMultiResolutionManager.setSupportAllResolution(false);
                  } else {
                    multiResolutionManagerPrintUsage();
                    return -1;
                  }
                }
                z2 = -1;
                if (z2) {}
              } else {
                if (nextArgRequired.equals("on")) {
                  z2 = false;
                  if (z2) {}
                }
                z2 = -1;
                if (z2) {}
              }
            } else {
              multiResolutionManagerPrintUsage();
              return -1;
            }
          }
          z = -1;
          if (z) {}
        } else {
          if (nextArg.equals("supportAll")) {
            z = true;
            if (z) {}
          }
          z = -1;
          if (z) {}
        }
      }
      return 0;
    } catch (IllegalArgumentException unused) {
      multiResolutionManagerPrintUsage();
      return -1;
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  public final int desktopModeSettingsCommand() {
    char c;
    String nextArg = getNextArg();
    if (nextArg == null) {
      desktopModeSettingsPrintUsage();
      return -1;
    }
    PrintWriter outPrintWriter = getOutPrintWriter();
    String nextArg2 = getNextArg();
    String nextArg3 = getNextArg();
    if (getNextArg() != null) {
      desktopModeSettingsPrintUsage();
      return -1;
    }
    switch (nextArg.hashCode()) {
      case -1335458389:
        if (nextArg.equals("delete")) {
          c = 0;
          break;
        }
        c = 65535;
        break;
      case 102230:
        if (nextArg.equals("get")) {
          c = 1;
          break;
        }
        c = 65535;
        break;
      case 111375:
        if (nextArg.equals("put")) {
          c = 2;
          break;
        }
        c = 65535;
        break;
      case 3322014:
        if (nextArg.equals("list")) {
          c = 3;
          break;
        }
        c = 65535;
        break;
      case 94746189:
        if (nextArg.equals("clear")) {
          c = 4;
          break;
        }
        c = 65535;
        break;
      default:
        c = 65535;
        break;
    }
    switch (c) {
      case 0:
        if (nextArg3 != null) {
          desktopModeSettingsPrintUsage();
          return -1;
        }
        DesktopModeSettings.deleteSettingsOrThrowException(this.mResolver, nextArg2);
        return 0;
      case 1:
        if (nextArg3 != null) {
          desktopModeSettingsPrintUsage();
          return -1;
        }
        outPrintWriter.println(
            DesktopModeSettings.getSettingsOrThrowException(this.mResolver, nextArg2, "null"));
        return 0;
      case 2:
        DesktopModeSettings.setSettingsOrThrowException(this.mResolver, nextArg2, nextArg3);
        return 0;
      case 3:
        if (nextArg2 != null) {
          desktopModeSettingsPrintUsage();
          return -1;
        }
        Utils.dumpBundle(outPrintWriter, DesktopModeSettings.getAllSettings(this.mResolver));
        if (ActivityManager.getCurrentUser() != 0) {
          outPrintWriter.println();
          outPrintWriter.println("System user (0) settings:");
          Utils.dumpBundle(
              outPrintWriter, DesktopModeSettings.getAllSettingsAsUser(this.mResolver, 0));
        }
        return 0;
      case 4:
        if (nextArg2 != null) {
          desktopModeSettingsPrintUsage();
          return -1;
        }
        DesktopModeSettings.deleteAllSettingsOrThrowException(this.mResolver);
        return 0;
      default:
        desktopModeSettingsPrintUsage();
        return 0;
    }
  }

  public final void uiManagerPrintUsage() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("USAGE: ui showDialog DISPLAYID TYPE");
    outPrintWriter.println("       ui showNotification TYPE");
    outPrintWriter.println("       ui showOverlay WHERE TYPE");
    outPrintWriter.println("       ui startActivity DISPLAYID TYPE");
    outPrintWriter.println("       ui dismissDialog TYPE");
    outPrintWriter.println("       ui dismissOverlay WHERE TYPE");
    outPrintWriter.println("       ui removeNotification TYPE");
    outPrintWriter.println("       ui finishActivity TYPE");
  }

  public final void desktopModeSettingsPrintUsage() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("USAGE: settings get KEY");
    outPrintWriter.println("       settings put KEY VALUE");
    outPrintWriter.println("       settings delete KEY");
    outPrintWriter.println("       settings clear");
    outPrintWriter.println("       settings list");
  }

  public final void multiResolutionManagerPrintUsage() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("USAGE: resolution");
    outPrintWriter.println("       resolution set WIDTH HEIGHT DENSITY");
    outPrintWriter.println("       resolution supportAll [on|off]");
  }

  public void onHelp() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("DesktopModeService commands:");
    outPrintWriter.println("  help");
    outPrintWriter.println("    Print this help text.");
    outPrintWriter.println("");
    outPrintWriter.println("  on");
    outPrintWriter.println("    Enable desktop mode.");
    outPrintWriter.println("");
    outPrintWriter.println("  off");
    outPrintWriter.println("    Disable desktop mode.");
    outPrintWriter.println("");
    outPrintWriter.println("  toggle");
    outPrintWriter.println("    Toggle desktop mode.");
    outPrintWriter.println("");
    outPrintWriter.println("  settings");
    outPrintWriter.println("    Manage desktop mode settings.");
    outPrintWriter.println("");
    outPrintWriter.println("  resolution");
    outPrintWriter.println("    Manage desktop mode resolution.");
    outPrintWriter.println("");
    outPrintWriter.println("  ui");
    outPrintWriter.println("    Manage desktop mode UI elements.");
  }
}
