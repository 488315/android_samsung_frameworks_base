package com.android.server.timedetector;

import android.os.ShellCommand;
import android.util.NtpTrustedTime;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes3.dex */
public class NetworkTimeUpdateServiceShellCommand extends ShellCommand {
  public final NetworkTimeUpdateService mNetworkTimeUpdateService;

  public NetworkTimeUpdateServiceShellCommand(NetworkTimeUpdateService networkTimeUpdateService) {
    Objects.requireNonNull(networkTimeUpdateService);
    this.mNetworkTimeUpdateService = networkTimeUpdateService;
  }

  public int onCommand(String str) {
    if (str == null) {
      return handleDefaultCommands(str);
    }
    switch (str) {
    }
    return handleDefaultCommands(str);
  }

  public final int runForceRefresh() {
    getOutPrintWriter().println(this.mNetworkTimeUpdateService.forceRefreshForTests());
    return 0;
  }

  public final int runSetServerConfig() {
    ArrayList arrayList = new ArrayList();
    Duration duration = null;
    while (true) {
      String nextArg = getNextArg();
      if (nextArg != null) {
        if (nextArg.equals("--timeout_millis")) {
          duration = Duration.ofMillis(Integer.parseInt(getNextArgRequired()));
        } else if (nextArg.equals("--server")) {
          try {
            arrayList.add(NtpTrustedTime.parseNtpUriStrict(getNextArgRequired()));
          } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Bad NTP server value", e);
          }
        } else {
          throw new IllegalArgumentException("Unknown option: " + nextArg);
        }
      } else {
        if (arrayList.isEmpty()) {
          throw new IllegalArgumentException("Missing required option: ----server");
        }
        if (duration == null) {
          throw new IllegalArgumentException("Missing required option: ----timeout_millis");
        }
        this.mNetworkTimeUpdateService.setServerConfigForTests(
            new NtpTrustedTime.NtpConfig(arrayList, duration));
        return 0;
      }
    }
  }

  public final int runResetServerConfig() {
    this.mNetworkTimeUpdateService.setServerConfigForTests(null);
    return 0;
  }

  public void onHelp() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.printf(
        "Network Time Update Service (%s) commands:\n", "network_time_update_service");
    outPrintWriter.printf("  help\n", new Object[0]);
    outPrintWriter.printf("    Print this help text.\n", new Object[0]);
    outPrintWriter.printf("  %s\n", "force_refresh");
    outPrintWriter.printf(
        "    Refreshes the latest time. Prints whether it was successful.\n", new Object[0]);
    outPrintWriter.printf("  %s\n", "set_server_config_for_tests");
    outPrintWriter.printf(
        "    Sets the NTP server config for tests. The config is not persisted.\n", new Object[0]);
    outPrintWriter.printf(
        "      Options: %s <uri> [%s <additional uris>]+ %s <millis>\n",
        "--server", "--server", "--timeout_millis");
    outPrintWriter.printf(
        "      NTP server URIs must be in the form \"ntp://hostname\" or \"ntp://hostname:port\"\n",
        new Object[0]);
    outPrintWriter.printf("  %s\n", "reset_server_config_for_tests");
    outPrintWriter.printf(
        "    Resets/clears the NTP server config set via %s.\n", "set_server_config_for_tests");
    outPrintWriter.println();
  }
}
