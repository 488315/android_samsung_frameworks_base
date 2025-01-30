package com.android.server.audio;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Debug;
import android.os.ShellCommand;
import android.os.UserHandle;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class AudioManagerShellCommand extends ShellCommand {
  public final AudioService mService;

  public AudioManagerShellCommand(AudioService audioService) {
    this.mService = audioService;
  }

  public int onCommand(String str) {
    if (str == null) {
      return handleDefaultCommands(str);
    }
    getOutPrintWriter();
    switch (str) {
      case "set-encoded-surround-mode":
        return setEncodedSurroundMode();
      case "set-surround-format-enabled":
        return setSurroundFormatEnabled();
      case "clearScpm":
        return clearScpm();
      case "get-is-surround-format-enabled":
        return getIsSurroundFormatEnabled();
      case "sendScpm":
        return sendScpm();
      case "get-encoded-surround-mode":
        return getEncodedSurroundMode();
      default:
        return 0;
    }
  }

  public void onHelp() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("Audio manager commands:");
    outPrintWriter.println("  help");
    outPrintWriter.println("    Print this help text.");
    outPrintWriter.println();
    outPrintWriter.println("  set-surround-format-enabled SURROUND_FORMAT IS_ENABLED");
    outPrintWriter.println("    Enables/disabled the SURROUND_FORMAT based on IS_ENABLED");
    outPrintWriter.println("  get-is-surround-format-enabled SURROUND_FORMAT");
    outPrintWriter.println("    Returns if the SURROUND_FORMAT is enabled");
    outPrintWriter.println("  set-encoded-surround-mode SURROUND_SOUND_MODE");
    outPrintWriter.println("    Sets the encoded surround sound mode to SURROUND_SOUND_MODE");
    outPrintWriter.println("  get-encoded-surround-mode");
    outPrintWriter.println("    Returns the encoded surround sound mode");
    outPrintWriter.println("  sendScpm");
    outPrintWriter.println("  clearScpm");
  }

  public final int setSurroundFormatEnabled() {
    String nextArg = getNextArg();
    String nextArg2 = getNextArg();
    if (nextArg == null) {
      getErrPrintWriter().println("Error: no surroundFormat specified");
      return 1;
    }
    if (nextArg2 == null) {
      getErrPrintWriter().println("Error: no enabled value for surroundFormat specified");
      return 1;
    }
    try {
      int parseInt = Integer.parseInt(nextArg);
      boolean parseBoolean = Boolean.parseBoolean(nextArg2);
      if (parseInt < 0) {
        getErrPrintWriter().println("Error: invalid value of surroundFormat");
        return 1;
      }
      ((AudioManager) this.mService.mContext.getSystemService(AudioManager.class))
          .setSurroundFormatEnabled(parseInt, parseBoolean);
      return 0;
    } catch (NumberFormatException unused) {
      getErrPrintWriter().println("Error: wrong format specified for surroundFormat");
      return 1;
    }
  }

  public final int getIsSurroundFormatEnabled() {
    String nextArg = getNextArg();
    if (nextArg == null) {
      getErrPrintWriter().println("Error: no surroundFormat specified");
      return 1;
    }
    try {
      int parseInt = Integer.parseInt(nextArg);
      if (parseInt < 0) {
        getErrPrintWriter().println("Error: invalid value of surroundFormat");
        return 1;
      }
      AudioManager audioManager =
          (AudioManager) this.mService.mContext.getSystemService(AudioManager.class);
      getOutPrintWriter()
          .println(
              "Value of enabled for "
                  + parseInt
                  + " is: "
                  + audioManager.isSurroundFormatEnabled(parseInt));
      return 0;
    } catch (NumberFormatException unused) {
      getErrPrintWriter().println("Error: wrong format specified for surroundFormat");
      return 1;
    }
  }

  public final int setEncodedSurroundMode() {
    String nextArg = getNextArg();
    if (nextArg == null) {
      getErrPrintWriter().println("Error: no encodedSurroundMode specified");
      return 1;
    }
    try {
      int parseInt = Integer.parseInt(nextArg);
      if (parseInt < 0) {
        getErrPrintWriter().println("Error: invalid value of encodedSurroundMode");
        return 1;
      }
      ((AudioManager) this.mService.mContext.getSystemService(AudioManager.class))
          .setEncodedSurroundMode(parseInt);
      return 0;
    } catch (NumberFormatException unused) {
      getErrPrintWriter().println("Error: wrong format specified for encoded surround mode");
      return 1;
    }
  }

  public final int getEncodedSurroundMode() {
    AudioManager audioManager =
        (AudioManager) this.mService.mContext.getSystemService(AudioManager.class);
    getOutPrintWriter().println("Encoded surround mode: " + audioManager.getEncodedSurroundMode());
    return 0;
  }

  public final int sendScpm() {
    if (!Debug.semIsProductDev()) {
      return 0;
    }
    Context context = this.mService.mContext;
    Intent intent = new Intent("com.samsung.android.scpm.policy.UPDATE.Audio");
    intent.addFlags(67108864);
    intent.addFlags(268435456);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      context.sendBroadcastAsUser(intent, UserHandle.ALL, null, null);
      return 0;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final int clearScpm() {
    if (!Debug.semIsProductDev()) {
      return 0;
    }
    Context context = this.mService.mContext;
    Intent intent = new Intent(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
    intent.addFlags(67108864);
    intent.addFlags(268435456);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      context.sendBroadcastAsUser(intent, UserHandle.ALL, null, null);
      return 0;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }
}
