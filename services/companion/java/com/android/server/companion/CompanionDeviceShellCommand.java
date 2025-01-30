package com.android.server.companion;

import android.companion.AssociationInfo;
import android.companion.datatransfer.PermissionSyncRequest;
import android.companion.datatransfer.SystemDataTransferRequest;
import android.net.MacAddress;
import android.os.Binder;
import android.os.ShellCommand;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.FunctionalUtils;
import com.android.server.companion.datatransfer.SystemDataTransferRequestStore;
import com.android.server.companion.datatransfer.contextsync.BitmapUtils;
import com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController;
import com.android.server.companion.presence.CompanionDevicePresenceMonitor;
import com.android.server.companion.transport.CompanionTransportManager;
import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes.dex */
public class CompanionDeviceShellCommand extends ShellCommand {
  public final AssociationRequestsProcessor mAssociationRequestsProcessor;
  public final AssociationStoreImpl mAssociationStore;
  public final CompanionDevicePresenceMonitor mDevicePresenceMonitor;
  public final CompanionDeviceManagerService mService;
  public final SystemDataTransferRequestStore mSystemDataTransferRequestStore;
  public final CompanionTransportManager mTransportManager;

  public CompanionDeviceShellCommand(
      CompanionDeviceManagerService companionDeviceManagerService,
      AssociationStoreImpl associationStoreImpl,
      CompanionDevicePresenceMonitor companionDevicePresenceMonitor,
      CompanionTransportManager companionTransportManager,
      SystemDataTransferRequestStore systemDataTransferRequestStore,
      AssociationRequestsProcessor associationRequestsProcessor) {
    this.mService = companionDeviceManagerService;
    this.mAssociationStore = associationStoreImpl;
    this.mDevicePresenceMonitor = companionDevicePresenceMonitor;
    this.mTransportManager = companionTransportManager;
    this.mSystemDataTransferRequestStore = systemDataTransferRequestStore;
    this.mAssociationRequestsProcessor = associationRequestsProcessor;
  }

  public int onCommand(String str) {
    char c;
    PrintWriter outPrintWriter = getOutPrintWriter();
    try {
      switch (str.hashCode()) {
        case -2105020158:
          if (str.equals("clear-association-memory-cache")) {
            c = 3;
            break;
          }
          c = 65535;
          break;
        case -2027841817:
          if (str.equals("send-context-sync-call-message")) {
            c = '\f';
            break;
          }
          c = 65535;
          break;
        case -1993991071:
          if (str.equals("send-context-sync-call-facilitators-message")) {
            c = 11;
            break;
          }
          c = 65535;
          break;
        case -1855910485:
          if (str.equals("remove-inactive-associations")) {
            c = 6;
            break;
          }
          c = 65535;
          break;
        case -1224243197:
          if (str.equals("enable-context-sync")) {
            c = 14;
            break;
          }
          c = 65535;
          break;
        case -984232290:
          if (str.equals("create-emulated-transport")) {
            c = 7;
            break;
          }
          c = 65535;
          break;
        case -318851754:
          if (str.equals("send-context-sync-call-create-message")) {
            c = '\t';
            break;
          }
          c = 65535;
          break;
        case -191868716:
          if (str.equals("simulate-device-disappeared")) {
            c = 5;
            break;
          }
          c = 65535;
          break;
        case 3322014:
          if (str.equals("list")) {
            c = 0;
            break;
          }
          c = 65535;
          break;
        case 665140760:
          if (str.equals("send-context-sync-empty-message")) {
            c = '\b';
            break;
          }
          c = 65535;
          break;
        case 784321104:
          if (str.equals("disassociate")) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case 1066447189:
          if (str.equals("allow-permission-sync")) {
            c = 15;
            break;
          }
          c = 65535;
          break;
        case 1069692606:
          if (str.equals("disable-context-sync")) {
            c = '\r';
            break;
          }
          c = 65535;
          break;
        case 1586499358:
          if (str.equals("associate")) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case 1996509815:
          if (str.equals("send-context-sync-call-control-message")) {
            c = '\n';
            break;
          }
          c = 65535;
          break;
        case 2001610978:
          if (str.equals("simulate-device-appeared")) {
            c = 4;
            break;
          }
          c = 65535;
          break;
        default:
          c = 65535;
          break;
      }
      long j = 1138166333441L;
      switch (c) {
        case 0:
          for (AssociationInfo associationInfo :
              this.mAssociationStore.getAssociationsForUser(getNextIntArgRequired())) {
            outPrintWriter.println(
                associationInfo.getPackageName()
                    + " "
                    + associationInfo.getDeviceMacAddress()
                    + " "
                    + associationInfo.getId());
          }
          return 0;
        case 1:
          this.mService.createNewAssociation(
              getNextIntArgRequired(),
              getNextArgRequired(),
              MacAddress.fromString(getNextArgRequired()),
              null,
              null,
              false);
          return 0;
        case 2:
          AssociationInfo associationWithCallerChecks =
              this.mService.getAssociationWithCallerChecks(
                  getNextIntArgRequired(), getNextArgRequired(), getNextArgRequired());
          if (associationWithCallerChecks != null) {
            this.mService.disassociateInternal(associationWithCallerChecks.getId());
          }
          return 0;
        case 3:
          this.mService.persistState();
          this.mService.loadAssociationsFromDisk();
          return 0;
        case 4:
          this.mDevicePresenceMonitor.simulateDeviceAppeared(getNextIntArgRequired());
          return 0;
        case 5:
          this.mDevicePresenceMonitor.simulateDeviceDisappeared(getNextIntArgRequired());
          return 0;
        case 6:
          final CompanionDeviceManagerService companionDeviceManagerService = this.mService;
          Objects.requireNonNull(companionDeviceManagerService);
          Binder.withCleanCallingIdentity(
              new FunctionalUtils.ThrowingRunnable() { // from class:
                // com.android.server.companion.CompanionDeviceShellCommand$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                  CompanionDeviceManagerService.this.removeInactiveSelfManagedAssociations();
                }
              });
          return 0;
        case 7:
          this.mTransportManager.createEmulatedTransport(getNextIntArgRequired());
          return 0;
        case '\b':
          this.mTransportManager
              .createEmulatedTransport(getNextIntArgRequired())
              .processMessage(1667729539, 0, CrossDeviceSyncController.createEmptyMessage());
          return 0;
        case '\t':
          this.mTransportManager
              .createEmulatedTransport(getNextIntArgRequired())
              .processMessage(
                  1667729539,
                  0,
                  CrossDeviceSyncController.createCallCreateMessage(
                      getNextArgRequired(), getNextArgRequired(), getNextArgRequired()));
          return 0;
        case '\n':
          this.mTransportManager
              .createEmulatedTransport(getNextIntArgRequired())
              .processMessage(
                  1667729539,
                  0,
                  CrossDeviceSyncController.createCallControlMessage(
                      getNextArgRequired(), getNextIntArgRequired()));
          return 0;
        case 11:
          int nextIntArgRequired = getNextIntArgRequired();
          int nextIntArgRequired2 = getNextIntArgRequired();
          String nextArgRequired = getNextArgRequired();
          String nextArgRequired2 = getNextArgRequired();
          ProtoOutputStream protoOutputStream = new ProtoOutputStream();
          int i = 1;
          protoOutputStream.write(1120986464257L, 1);
          long start = protoOutputStream.start(1146756268036L);
          int i2 = 0;
          while (i2 < nextIntArgRequired2) {
            long start2 = protoOutputStream.start(2246267895811L);
            protoOutputStream.write(
                j, nextIntArgRequired2 == i ? nextArgRequired : nextArgRequired + i2);
            protoOutputStream.write(
                1138166333442L,
                nextIntArgRequired2 == 1 ? nextArgRequired2 : nextArgRequired2 + i2);
            protoOutputStream.end(start2);
            i2++;
            i = 1;
            j = 1138166333441L;
          }
          protoOutputStream.end(start);
          this.mTransportManager
              .createEmulatedTransport(nextIntArgRequired)
              .processMessage(1667729539, 0, protoOutputStream.getBytes());
          return 0;
        case '\f':
          int nextIntArgRequired3 = getNextIntArgRequired();
          String nextArgRequired3 = getNextArgRequired();
          String nextArgRequired4 = getNextArgRequired();
          int nextIntArgRequired4 = getNextIntArgRequired();
          boolean nextBooleanArgRequired = getNextBooleanArgRequired();
          boolean nextBooleanArgRequired2 = getNextBooleanArgRequired();
          boolean nextBooleanArgRequired3 = getNextBooleanArgRequired();
          boolean nextBooleanArgRequired4 = getNextBooleanArgRequired();
          boolean nextBooleanArgRequired5 = getNextBooleanArgRequired();
          boolean nextBooleanArgRequired6 = getNextBooleanArgRequired();
          boolean nextBooleanArgRequired7 = getNextBooleanArgRequired();
          boolean nextBooleanArgRequired8 = getNextBooleanArgRequired();
          ProtoOutputStream protoOutputStream2 = new ProtoOutputStream();
          protoOutputStream2.write(1120986464257L, 1);
          long start3 = protoOutputStream2.start(1146756268036L);
          long start4 = protoOutputStream2.start(2246267895809L);
          protoOutputStream2.write(1138166333441L, nextArgRequired3);
          long start5 = protoOutputStream2.start(1146756268034L);
          protoOutputStream2.write(1138166333441L, "Caller Name");
          protoOutputStream2.write(
              1151051235330L,
              BitmapUtils.renderDrawableToByteArray(
                  this.mService
                      .getContext()
                      .getPackageManager()
                      .getApplicationIcon(nextArgRequired4)));
          long start6 = protoOutputStream2.start(1146756268035L);
          protoOutputStream2.write(1138166333441L, "Test App Name");
          protoOutputStream2.write(1138166333442L, nextArgRequired4);
          protoOutputStream2.end(start6);
          protoOutputStream2.end(start5);
          protoOutputStream2.write(1159641169923L, nextIntArgRequired4);
          if (nextBooleanArgRequired) {
            protoOutputStream2.write(2259152797700L, 1);
          }
          if (nextBooleanArgRequired2) {
            protoOutputStream2.write(2259152797700L, 2);
          }
          if (nextBooleanArgRequired3) {
            protoOutputStream2.write(2259152797700L, 3);
          }
          if (nextBooleanArgRequired4) {
            protoOutputStream2.write(2259152797700L, 4);
          }
          if (nextBooleanArgRequired5) {
            protoOutputStream2.write(2259152797700L, 5);
          }
          if (nextBooleanArgRequired6) {
            protoOutputStream2.write(2259152797700L, 6);
          }
          if (nextBooleanArgRequired7) {
            protoOutputStream2.write(2259152797700L, 7);
          }
          if (nextBooleanArgRequired8) {
            protoOutputStream2.write(2259152797700L, 8);
          }
          protoOutputStream2.end(start4);
          protoOutputStream2.end(start3);
          this.mTransportManager
              .createEmulatedTransport(nextIntArgRequired3)
              .processMessage(1667729539, 0, protoOutputStream2.getBytes());
          return 0;
        case '\r':
          this.mAssociationRequestsProcessor.disableSystemDataSync(
              getNextIntArgRequired(), getNextIntArgRequired());
          return 0;
        case 14:
          this.mAssociationRequestsProcessor.enableSystemDataSync(
              getNextIntArgRequired(), getNextIntArgRequired());
          return 0;
        case 15:
          int nextIntArgRequired5 = getNextIntArgRequired();
          int nextIntArgRequired6 = getNextIntArgRequired();
          boolean nextBooleanArgRequired9 = getNextBooleanArgRequired();
          SystemDataTransferRequest permissionSyncRequest =
              new PermissionSyncRequest(nextIntArgRequired6);
          permissionSyncRequest.setUserId(nextIntArgRequired5);
          permissionSyncRequest.setUserConsented(nextBooleanArgRequired9);
          this.mSystemDataTransferRequestStore.writeRequest(
              nextIntArgRequired5, permissionSyncRequest);
          return 0;
        default:
          return handleDefaultCommands(str);
      }
    } catch (Throwable th) {
      PrintWriter errPrintWriter = getErrPrintWriter();
      errPrintWriter.println();
      errPrintWriter.println("Exception occurred while executing '" + str + "':");
      th.printStackTrace(errPrintWriter);
      return 1;
    }
  }

  public final int getNextIntArgRequired() {
    return Integer.parseInt(getNextArgRequired());
  }

  public final boolean getNextBooleanArgRequired() {
    String nextArgRequired = getNextArgRequired();
    if ("true".equalsIgnoreCase(nextArgRequired) || "false".equalsIgnoreCase(nextArgRequired)) {
      return Boolean.parseBoolean(nextArgRequired);
    }
    throw new IllegalArgumentException("Expected a boolean argument but was: " + nextArgRequired);
  }

  public void onHelp() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("Companion Device Manager (companiondevice) commands:");
    outPrintWriter.println("  help");
    outPrintWriter.println("      Print this help text.");
    outPrintWriter.println("  list USER_ID");
    outPrintWriter.println("      List all Associations for a user.");
    outPrintWriter.println("  associate USER_ID PACKAGE MAC_ADDRESS");
    outPrintWriter.println("      Create a new Association.");
    outPrintWriter.println("  disassociate USER_ID PACKAGE MAC_ADDRESS");
    outPrintWriter.println("      Remove an existing Association.");
    outPrintWriter.println("  clear-association-memory-cache");
    outPrintWriter.println(
        "      Clear the in-memory association cache and reload all association ");
    outPrintWriter.println(
        "      information from persistent storage. USE FOR DEBUGGING PURPOSES ONLY.");
    outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
    outPrintWriter.println("  simulate-device-appeared ASSOCIATION_ID");
    outPrintWriter.println("      Make CDM act as if the given companion device has appeared.");
    outPrintWriter.println("      I.e. bind the associated companion application's");
    outPrintWriter.println(
        "      CompanionDeviceService(s) and trigger onDeviceAppeared() callback.");
    outPrintWriter.println(
        "      The CDM will consider the devices as present for 60 seconds and then");
    outPrintWriter.println(
        "      will act as if device disappeared, unless 'simulate-device-disappeared'");
    outPrintWriter.println(
        "      or 'simulate-device-appeared' is called again before 60 seconds run out.");
    outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
    outPrintWriter.println("  simulate-device-disappeared ASSOCIATION_ID");
    outPrintWriter.println("      Make CDM act as if the given companion device has disappeared.");
    outPrintWriter.println("      I.e. unbind the associated companion application's");
    outPrintWriter.println(
        "      CompanionDeviceService(s) and trigger onDeviceDisappeared() callback.");
    outPrintWriter.println(
        "      NOTE: This will only have effect if 'simulate-device-appeared' was");
    outPrintWriter.println(
        "      invoked for the same device (same ASSOCIATION_ID) no longer than");
    outPrintWriter.println("      60 seconds ago.");
    outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
    outPrintWriter.println("  remove-inactive-associations");
    outPrintWriter.println("      Remove self-managed associations that have not been active ");
    outPrintWriter.println("      for a long time (90 days or as configured via ");
    outPrintWriter.println("      \"debug.cdm.cdmservice.cleanup_time_window\" system property). ");
    outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
    outPrintWriter.println("  create-emulated-transport <ASSOCIATION_ID>");
    outPrintWriter.println("      Create an EmulatedTransport for testing purposes only");
  }
}
