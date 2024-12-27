package com.android.server.companion;

import android.companion.AssociationInfo;
import android.companion.ObservingDevicePresenceRequest;
import android.companion.datatransfer.PermissionSyncRequest;
import android.companion.datatransfer.SystemDataTransferRequest;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.MacAddress;
import android.os.Binder;
import android.os.ParcelUuid;
import android.os.ShellCommand;
import android.util.Base64;
import android.util.proto.ProtoOutputStream;

import com.android.internal.hidden_from_bootclasspath.android.companion.Flags;
import com.android.internal.util.FunctionalUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.companion.association.AssociationRequestsProcessor;
import com.android.server.companion.association.AssociationStore;
import com.android.server.companion.association.AssociationStore$$ExternalSyntheticLambda3;
import com.android.server.companion.association.DisassociationProcessor;
import com.android.server.companion.datatransfer.SystemDataTransferProcessor;
import com.android.server.companion.datatransfer.contextsync.BitmapUtils;
import com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController;
import com.android.server.companion.devicepresence.DevicePresenceProcessor;
import com.android.server.companion.devicepresence.ObservableUuid;
import com.android.server.companion.transport.CompanionTransportManager;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CompanionDeviceShellCommand extends ShellCommand {
    public final AssociationRequestsProcessor mAssociationRequestsProcessor;
    public final AssociationStore mAssociationStore;
    public final BackupRestoreProcessor mBackupRestoreProcessor;
    public final DevicePresenceProcessor mDevicePresenceProcessor;
    public final DisassociationProcessor mDisassociationProcessor;
    public final CompanionDeviceManagerService mService;
    public final SystemDataTransferProcessor mSystemDataTransferProcessor;
    public final CompanionTransportManager mTransportManager;

    public CompanionDeviceShellCommand(
            CompanionDeviceManagerService companionDeviceManagerService,
            AssociationStore associationStore,
            DevicePresenceProcessor devicePresenceProcessor,
            CompanionTransportManager companionTransportManager,
            SystemDataTransferProcessor systemDataTransferProcessor,
            AssociationRequestsProcessor associationRequestsProcessor,
            BackupRestoreProcessor backupRestoreProcessor,
            DisassociationProcessor disassociationProcessor) {
        this.mService = companionDeviceManagerService;
        this.mAssociationStore = associationStore;
        this.mDevicePresenceProcessor = devicePresenceProcessor;
        this.mTransportManager = companionTransportManager;
        this.mSystemDataTransferProcessor = systemDataTransferProcessor;
        this.mAssociationRequestsProcessor = associationRequestsProcessor;
        this.mBackupRestoreProcessor = backupRestoreProcessor;
        this.mDisassociationProcessor = disassociationProcessor;
    }

    public final boolean getNextBooleanArgRequired() {
        String nextArgRequired = getNextArgRequired();
        if ("true".equalsIgnoreCase(nextArgRequired) || "false".equalsIgnoreCase(nextArgRequired)) {
            return Boolean.parseBoolean(nextArgRequired);
        }
        throw new IllegalArgumentException(
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "Expected a boolean argument but was: ", nextArgRequired));
    }

    public final int getNextIntArgRequired() {
        return Integer.parseInt(getNextArgRequired());
    }

    public final int onCommand(String str) {
        char c;
        int i;
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -2027841817:
                    if (str.equals("send-context-sync-call-message")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case -1993991071:
                    if (str.equals("send-context-sync-call-facilitators-message")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case -1905713190:
                    if (str.equals("get-backup-payload")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -1860608114:
                    if (str.equals("enable-perm-sync")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case -1855910485:
                    if (str.equals("remove-inactive-associations")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -1224243197:
                    if (str.equals("enable-context-sync")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case -984232290:
                    if (str.equals("create-emulated-transport")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -757319323:
                    if (str.equals("get-perm-sync-state")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case -633878164:
                    if (str.equals("simulate-device-event")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -632811356:
                    if (str.equals("disassociate-all")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -548306091:
                    if (str.equals("start-observing-device-presence-uuid")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -517291495:
                    if (str.equals("simulate-device-event-device-unlocked")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -318851754:
                    if (str.equals("send-context-sync-call-create-message")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -191868716:
                    if (str.equals("simulate-device-disappeared")) {
                        c = 6;
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
                case 330925715:
                    if (str.equals("remove-perm-sync-state")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case 551574230:
                    if (str.equals("apply-restored-payload")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 665140760:
                    if (str.equals("send-context-sync-empty-message")) {
                        c = 17;
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
                case 791006133:
                    if (str.equals("stop-observing-device-presence-uuid")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 1069692606:
                    if (str.equals("disable-context-sync")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 1415349776:
                    if (str.equals("refresh-cache")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1527030451:
                    if (str.equals("disable-perm-sync")) {
                        c = 27;
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
                case 1912643190:
                    if (str.equals("simulate-device-uuid-event")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1981554624:
                    if (str.equals("simulate-device-event-device-locked")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1996509815:
                    if (str.equals("send-context-sync-call-control-message")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 2001610978:
                    if (str.equals("simulate-device-appeared")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            Object obj = "null";
            switch (c) {
                case 0:
                    List<AssociationInfo> activeAssociationsByUser =
                            this.mAssociationStore.getActiveAssociationsByUser(
                                    getNextIntArgRequired());
                    AssociationStore associationStore = this.mAssociationStore;
                    synchronized (associationStore.mLock) {
                        i = associationStore.mMaxId;
                    }
                    outPrintWriter.println("Max ID: " + i);
                    outPrintWriter.println("Association ID | Package Name | Mac Address");
                    for (AssociationInfo associationInfo : activeAssociationsByUser) {
                        outPrintWriter.println(
                                associationInfo.getId()
                                        + " | "
                                        + associationInfo.getPackageName()
                                        + " | "
                                        + associationInfo.getDeviceMacAddress());
                    }
                    return 0;
                case 1:
                    int nextIntArgRequired = getNextIntArgRequired();
                    String nextArgRequired = getNextArgRequired();
                    String nextArgRequired2 = getNextArgRequired();
                    String nextArg = getNextArg();
                    this.mAssociationRequestsProcessor.createAssociation(
                            nextIntArgRequired,
                            nextArgRequired,
                            MacAddress.fromString(nextArgRequired2),
                            nextArg,
                            nextArg,
                            null,
                            false,
                            null,
                            null);
                    return 0;
                case 2:
                    AssociationInfo firstAssociationByAddress =
                            this.mAssociationStore.getFirstAssociationByAddress(
                                    getNextIntArgRequired(),
                                    getNextArgRequired(),
                                    getNextArgRequired());
                    if (firstAssociationByAddress == null) {
                        outPrintWriter.println("Association doesn't exist.");
                    } else {
                        this.mDisassociationProcessor.disassociate(
                                firstAssociationByAddress.getId());
                    }
                    return 0;
                case 3:
                    Iterator it =
                            this.mAssociationStore
                                    .getAssociationsByUser(getNextIntArgRequired())
                                    .iterator();
                    while (it.hasNext()) {
                        this.mDisassociationProcessor.disassociate(
                                ((AssociationInfo) it.next()).getId());
                    }
                    return 0;
                case 4:
                    AssociationStore associationStore2 = this.mAssociationStore;
                    associationStore2.getClass();
                    Binder.withCleanCallingIdentity(
                            new AssociationStore$$ExternalSyntheticLambda3(associationStore2));
                    return 0;
                case 5:
                    this.mDevicePresenceProcessor.simulateDeviceEvent(getNextIntArgRequired(), 0);
                    return 0;
                case 6:
                    this.mDevicePresenceProcessor.simulateDeviceEvent(getNextIntArgRequired(), 1);
                    return 0;
                case 7:
                    if (Flags.devicePresence()) {
                        this.mDevicePresenceProcessor.simulateDeviceEvent(
                                getNextIntArgRequired(), getNextIntArgRequired());
                    }
                    return 0;
                case '\b':
                    if (Flags.devicePresence()) {
                        String nextArgRequired3 = getNextArgRequired();
                        String nextArgRequired4 = getNextArgRequired();
                        int nextIntArgRequired2 = getNextIntArgRequired();
                        int nextIntArgRequired3 = getNextIntArgRequired();
                        ObservableUuid observableUuid =
                                new ObservableUuid(
                                        nextIntArgRequired2,
                                        ParcelUuid.fromString(nextArgRequired3),
                                        nextArgRequired4,
                                        Long.valueOf(System.currentTimeMillis()));
                        DevicePresenceProcessor devicePresenceProcessor =
                                this.mDevicePresenceProcessor;
                        devicePresenceProcessor.getClass();
                        DevicePresenceProcessor.enforceCallerShellOrRoot();
                        devicePresenceProcessor.onDevicePresenceEventByUuid(
                                observableUuid, nextIntArgRequired3);
                    }
                    return 0;
                case '\t':
                    if (Flags.devicePresence()) {
                        int nextIntArgRequired4 = getNextIntArgRequired();
                        int nextIntArgRequired5 = getNextIntArgRequired();
                        int nextIntArgRequired6 = getNextIntArgRequired();
                        String nextArgRequired5 = getNextArgRequired();
                        ParcelUuid fromString =
                                nextArgRequired5.equals("null")
                                        ? null
                                        : ParcelUuid.fromString(nextArgRequired5);
                        DevicePresenceProcessor devicePresenceProcessor2 =
                                this.mDevicePresenceProcessor;
                        devicePresenceProcessor2.getClass();
                        DevicePresenceProcessor.enforceCallerShellOrRoot();
                        devicePresenceProcessor2.onDeviceLocked(
                                nextIntArgRequired4,
                                nextIntArgRequired5,
                                nextIntArgRequired6,
                                fromString);
                    }
                    return 0;
                case '\n':
                    if (Flags.devicePresence()) {
                        int nextIntArgRequired7 = getNextIntArgRequired();
                        DevicePresenceProcessor devicePresenceProcessor3 =
                                this.mDevicePresenceProcessor;
                        devicePresenceProcessor3.getClass();
                        DevicePresenceProcessor.enforceCallerShellOrRoot();
                        devicePresenceProcessor3.sendDevicePresenceEventOnUnlocked(
                                nextIntArgRequired7);
                    }
                    return 0;
                case 11:
                    if (Flags.devicePresence()) {
                        int nextIntArgRequired8 = getNextIntArgRequired();
                        String nextArgRequired6 = getNextArgRequired();
                        String nextArgRequired7 = getNextArgRequired();
                        if ("null".equals(nextArgRequired7)) {
                            outPrintWriter.println("UUID can not be null.");
                        } else {
                            this.mDevicePresenceProcessor.startObservingDevicePresence(
                                    new ObservingDevicePresenceRequest.Builder()
                                            .setUuid(ParcelUuid.fromString(nextArgRequired7))
                                            .build(),
                                    nextArgRequired6,
                                    nextIntArgRequired8,
                                    false);
                        }
                    }
                    return 0;
                case '\f':
                    if (Flags.devicePresence()) {
                        int nextIntArgRequired9 = getNextIntArgRequired();
                        String nextArgRequired8 = getNextArgRequired();
                        String nextArgRequired9 = getNextArgRequired();
                        if ("null".equals(nextArgRequired9)) {
                            outPrintWriter.println("UUID can not be null.");
                        } else {
                            this.mDevicePresenceProcessor.stopObservingDevicePresence(
                                    new ObservingDevicePresenceRequest.Builder()
                                            .setUuid(ParcelUuid.fromString(nextArgRequired9))
                                            .build(),
                                    nextArgRequired8,
                                    nextIntArgRequired9,
                                    false);
                        }
                    }
                    return 0;
                case '\r':
                    outPrintWriter.println(
                            Base64.encodeToString(
                                    this.mBackupRestoreProcessor.getBackupPayload(
                                            getNextIntArgRequired()),
                                    2));
                    return 0;
                case 14:
                    this.mBackupRestoreProcessor.applyRestoredPayload(
                            Base64.decode(getNextArgRequired(), 2), getNextIntArgRequired());
                    return 0;
                case 15:
                    final CompanionDeviceManagerService companionDeviceManagerService =
                            this.mService;
                    Objects.requireNonNull(companionDeviceManagerService);
                    Binder.withCleanCallingIdentity(
                            new FunctionalUtils
                                    .ThrowingRunnable() { // from class:
                                                          // com.android.server.companion.CompanionDeviceShellCommand$$ExternalSyntheticLambda0
                                public final void runOrThrow() {
                                    CompanionDeviceManagerService.this.mDisassociationProcessor
                                            .removeIdleSelfManagedAssociations();
                                }
                            });
                    return 0;
                case 16:
                    this.mTransportManager.createEmulatedTransport(getNextIntArgRequired());
                    return 0;
                case 17:
                    CompanionTransportManager.EmulatedTransport createEmulatedTransport =
                            this.mTransportManager.createEmulatedTransport(getNextIntArgRequired());
                    ProtoOutputStream protoOutputStream = new ProtoOutputStream();
                    protoOutputStream.write(1120986464257L, 1);
                    createEmulatedTransport.handleMessage(
                            1667729539, 0, protoOutputStream.getBytes());
                    return 0;
                case 18:
                    this.mTransportManager
                            .createEmulatedTransport(getNextIntArgRequired())
                            .handleMessage(
                                    1667729539,
                                    0,
                                    CrossDeviceSyncController.createCallCreateMessage(
                                            getNextArgRequired(),
                                            getNextArgRequired(),
                                            getNextArgRequired()));
                    return 0;
                case 19:
                    this.mTransportManager
                            .createEmulatedTransport(getNextIntArgRequired())
                            .handleMessage(
                                    1667729539,
                                    0,
                                    CrossDeviceSyncController.createCallControlMessage(
                                            getNextIntArgRequired(), getNextArgRequired()));
                    return 0;
                case 20:
                    int nextIntArgRequired10 = getNextIntArgRequired();
                    int nextIntArgRequired11 = getNextIntArgRequired();
                    String nextArgRequired10 = getNextArgRequired();
                    String nextArgRequired11 = getNextArgRequired();
                    ProtoOutputStream protoOutputStream2 = new ProtoOutputStream();
                    int i2 = 1;
                    protoOutputStream2.write(1120986464257L, 1);
                    long start = protoOutputStream2.start(1146756268036L);
                    int i3 = 0;
                    while (i3 < nextIntArgRequired11) {
                        long start2 = protoOutputStream2.start(2246267895811L);
                        protoOutputStream2.write(
                                1138166333441L,
                                nextIntArgRequired11 == i2
                                        ? nextArgRequired10
                                        : nextArgRequired10 + i3);
                        protoOutputStream2.write(
                                1138166333442L,
                                nextIntArgRequired11 == 1
                                        ? nextArgRequired11
                                        : nextArgRequired11 + i3);
                        protoOutputStream2.end(start2);
                        i3++;
                        i2 = 1;
                    }
                    protoOutputStream2.end(start);
                    this.mTransportManager
                            .createEmulatedTransport(nextIntArgRequired10)
                            .handleMessage(1667729539, 0, protoOutputStream2.getBytes());
                    return 0;
                case 21:
                    int nextIntArgRequired12 = getNextIntArgRequired();
                    String nextArgRequired12 = getNextArgRequired();
                    String nextArgRequired13 = getNextArgRequired();
                    int nextIntArgRequired13 = getNextIntArgRequired();
                    boolean nextBooleanArgRequired = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired2 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired3 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired4 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired5 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired6 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired7 = getNextBooleanArgRequired();
                    boolean nextBooleanArgRequired8 = getNextBooleanArgRequired();
                    ProtoOutputStream protoOutputStream3 = new ProtoOutputStream();
                    protoOutputStream3.write(1120986464257L, 1);
                    long start3 = protoOutputStream3.start(1146756268036L);
                    long start4 = protoOutputStream3.start(2246267895809L);
                    protoOutputStream3.write(1138166333441L, nextArgRequired12);
                    long start5 = protoOutputStream3.start(1146756268034L);
                    protoOutputStream3.write(1138166333441L, "Caller Name");
                    protoOutputStream3.write(
                            1151051235330L,
                            BitmapUtils.renderDrawableToByteArray(
                                    this.mService
                                            .getContext()
                                            .getPackageManager()
                                            .getApplicationIcon(nextArgRequired13)));
                    long start6 = protoOutputStream3.start(1146756268035L);
                    try {
                        protoOutputStream3.write(1138166333441L, "Test App Name");
                        protoOutputStream3.write(1138166333442L, nextArgRequired13);
                        protoOutputStream3.end(start6);
                        protoOutputStream3.end(start5);
                        protoOutputStream3.write(1159641169923L, nextIntArgRequired13);
                        if (nextBooleanArgRequired) {
                            protoOutputStream3.write(2259152797700L, 1);
                        }
                        if (nextBooleanArgRequired2) {
                            protoOutputStream3.write(2259152797700L, 2);
                        }
                        if (nextBooleanArgRequired3) {
                            protoOutputStream3.write(2259152797700L, 3);
                        }
                        if (nextBooleanArgRequired4) {
                            protoOutputStream3.write(2259152797700L, 4);
                        }
                        if (nextBooleanArgRequired5) {
                            protoOutputStream3.write(2259152797700L, 5);
                        }
                        if (nextBooleanArgRequired6) {
                            protoOutputStream3.write(2259152797700L, 6);
                        }
                        if (nextBooleanArgRequired7) {
                            protoOutputStream3.write(2259152797700L, 7);
                        }
                        if (nextBooleanArgRequired8) {
                            protoOutputStream3.write(2259152797700L, 8);
                        }
                        protoOutputStream3.end(start4);
                        protoOutputStream3.end(start3);
                        this.mTransportManager
                                .createEmulatedTransport(nextIntArgRequired12)
                                .handleMessage(1667729539, 0, protoOutputStream3.getBytes());
                        return 0;
                    } catch (Throwable th) {
                        th = th;
                        PrintWriter errPrintWriter = getErrPrintWriter();
                        errPrintWriter.println();
                        errPrintWriter.println("Exception occurred while executing '" + str + "':");
                        th.printStackTrace(errPrintWriter);
                        return 1;
                    }
                case 22:
                    int nextIntArgRequired14 = getNextIntArgRequired();
                    int nextIntArgRequired15 = getNextIntArgRequired();
                    AssociationStore associationStore3 =
                            this.mAssociationRequestsProcessor.mAssociationStore;
                    AssociationInfo associationWithCallerChecks =
                            associationStore3.getAssociationWithCallerChecks(nextIntArgRequired14);
                    associationStore3.updateAssociation(
                            new AssociationInfo.Builder(associationWithCallerChecks)
                                    .setSystemDataSyncFlags(
                                            associationWithCallerChecks.getSystemDataSyncFlags()
                                                    & (~nextIntArgRequired15))
                                    .build());
                    return 0;
                case 23:
                    int nextIntArgRequired16 = getNextIntArgRequired();
                    int nextIntArgRequired17 = getNextIntArgRequired();
                    AssociationStore associationStore4 =
                            this.mAssociationRequestsProcessor.mAssociationStore;
                    AssociationInfo associationWithCallerChecks2 =
                            associationStore4.getAssociationWithCallerChecks(nextIntArgRequired16);
                    associationStore4.updateAssociation(
                            new AssociationInfo.Builder(associationWithCallerChecks2)
                                    .setSystemDataSyncFlags(
                                            associationWithCallerChecks2.getSystemDataSyncFlags()
                                                    | nextIntArgRequired17)
                                    .build());
                    return 0;
                case 24:
                    PermissionSyncRequest permissionSyncRequest =
                            this.mSystemDataTransferProcessor.getPermissionSyncRequest(
                                    getNextIntArgRequired());
                    if (permissionSyncRequest != null) {
                        obj = Boolean.valueOf(permissionSyncRequest.isUserConsented());
                    }
                    outPrintWriter.println(obj);
                    return 0;
                case 25:
                    final int nextIntArgRequired18 = getNextIntArgRequired();
                    PermissionSyncRequest permissionSyncRequest2 =
                            this.mSystemDataTransferProcessor.getPermissionSyncRequest(
                                    nextIntArgRequired18);
                    outPrintWriter.print(
                            permissionSyncRequest2 == null
                                    ? "null"
                                    : Boolean.valueOf(permissionSyncRequest2.isUserConsented()));
                    final SystemDataTransferProcessor systemDataTransferProcessor =
                            this.mSystemDataTransferProcessor;
                    systemDataTransferProcessor.getClass();
                    Binder.withCleanCallingIdentity(
                            new FunctionalUtils
                                    .ThrowingRunnable() { // from class:
                                                          // com.android.server.companion.datatransfer.SystemDataTransferProcessor$$ExternalSyntheticLambda4
                                public final void runOrThrow() {
                                    SystemDataTransferProcessor systemDataTransferProcessor2 =
                                            SystemDataTransferProcessor.this;
                                    int i4 = nextIntArgRequired18;
                                    systemDataTransferProcessor2.mSystemDataTransferRequestStore
                                            .removeRequestsByAssociationId(
                                                    systemDataTransferProcessor2
                                                            .mAssociationStore
                                                            .getAssociationWithCallerChecks(i4)
                                                            .getUserId(),
                                                    i4);
                                }
                            });
                    PermissionSyncRequest permissionSyncRequest3 =
                            this.mSystemDataTransferProcessor.getPermissionSyncRequest(
                                    nextIntArgRequired18);
                    StringBuilder sb = new StringBuilder(" -> ");
                    if (permissionSyncRequest3 != null) {
                        obj = Boolean.valueOf(permissionSyncRequest3.isUserConsented());
                    }
                    sb.append(obj);
                    outPrintWriter.println(sb.toString());
                    return 0;
                case 26:
                    int nextIntArgRequired19 = getNextIntArgRequired();
                    PermissionSyncRequest permissionSyncRequest4 =
                            this.mSystemDataTransferProcessor.getPermissionSyncRequest(
                                    nextIntArgRequired19);
                    if (permissionSyncRequest4 != null) {
                        obj = Boolean.valueOf(permissionSyncRequest4.isUserConsented());
                    }
                    outPrintWriter.print(obj);
                    SystemDataTransferProcessor systemDataTransferProcessor2 =
                            this.mSystemDataTransferProcessor;
                    int userId =
                            systemDataTransferProcessor2
                                    .mAssociationStore
                                    .getAssociationWithCallerChecks(nextIntArgRequired19)
                                    .getUserId();
                    SystemDataTransferRequest permissionSyncRequest5 =
                            new PermissionSyncRequest(nextIntArgRequired19);
                    permissionSyncRequest5.setUserConsented(true);
                    systemDataTransferProcessor2.mSystemDataTransferRequestStore.writeRequest(
                            userId, permissionSyncRequest5);
                    outPrintWriter.println(
                            " -> "
                                    + this.mSystemDataTransferProcessor
                                            .getPermissionSyncRequest(nextIntArgRequired19)
                                            .isUserConsented());
                    return 0;
                case 27:
                    int nextIntArgRequired20 = getNextIntArgRequired();
                    PermissionSyncRequest permissionSyncRequest6 =
                            this.mSystemDataTransferProcessor.getPermissionSyncRequest(
                                    nextIntArgRequired20);
                    if (permissionSyncRequest6 != null) {
                        obj = Boolean.valueOf(permissionSyncRequest6.isUserConsented());
                    }
                    outPrintWriter.print(obj);
                    SystemDataTransferProcessor systemDataTransferProcessor3 =
                            this.mSystemDataTransferProcessor;
                    int userId2 =
                            systemDataTransferProcessor3
                                    .mAssociationStore
                                    .getAssociationWithCallerChecks(nextIntArgRequired20)
                                    .getUserId();
                    SystemDataTransferRequest permissionSyncRequest7 =
                            new PermissionSyncRequest(nextIntArgRequired20);
                    permissionSyncRequest7.setUserConsented(false);
                    systemDataTransferProcessor3.mSystemDataTransferRequestStore.writeRequest(
                            userId2, permissionSyncRequest7);
                    outPrintWriter.println(
                            " -> "
                                    + this.mSystemDataTransferProcessor
                                            .getPermissionSyncRequest(nextIntArgRequired20)
                                            .isUserConsented());
                    return 0;
                default:
                    return handleDefaultCommands(str);
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Companion Device Manager (companiondevice) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  list USER_ID");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      List all Associations for a user.",
                "  associate USER_ID PACKAGE MAC_ADDRESS [DEVICE_PROFILE]",
                "      Create a new Association.",
                "  disassociate USER_ID PACKAGE MAC_ADDRESS");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Remove an existing Association.",
                "  disassociate-all USER_ID",
                "      Remove all Associations for a user.",
                "  refresh-cache");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Clear the in-memory association cache and reload all association ",
                "      information from disk. USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.",
                "  simulate-device-appeared ASSOCIATION_ID",
                "      Make CDM act as if the given companion device has appeared.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      I.e. bind the associated companion application's",
                "      CompanionDeviceService(s) and trigger onDeviceAppeared() callback.",
                "      The CDM will consider the devices as present for 60 seconds and then",
                "      will act as if device disappeared, unless 'simulate-device-disappeared'");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      or 'simulate-device-appeared' is called again before 60 seconds run out.",
                "      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.",
                "  simulate-device-disappeared ASSOCIATION_ID",
                "      Make CDM act as if the given companion device has disappeared.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      I.e. unbind the associated companion application's",
                "      CompanionDeviceService(s) and trigger onDeviceDisappeared() callback.",
                "      NOTE: This will only have effect if 'simulate-device-appeared' was",
                "      invoked for the same device (same ASSOCIATION_ID) no longer than");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      60 seconds ago.",
                "  get-backup-payload USER_ID",
                "      Generate backup payload for the given user and print its content",
                "      encoded to a Base64 string.");
        outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
        outPrintWriter.println("  apply-restored-payload USER_ID PAYLOAD");
        outPrintWriter.println("      Apply restored backup payload for the given user.");
        outPrintWriter.println("      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
        if (Flags.devicePresence()) {
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  simulate-device-event ASSOCIATION_ID EVENT",
                    "  Simulate the companion device event changes:",
                    "    Case(0): ",
                    "      Make CDM act as if the given companion device has appeared.");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "      I.e. bind the associated companion application's",
                    "      CompanionDeviceService(s) and trigger onDeviceAppeared() callback.",
                    "      The CDM will consider the devices as present for60 seconds and then",
                    "      will act as if device disappeared, unless'simulate-device-disappeared'");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "      or 'simulate-device-appeared' is called again before 60 secondsrun out.",
                    "    Case(1): ",
                    "      Make CDM act as if the given companion device has disappeared.",
                    "      I.e. unbind the associated companion application's");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "      CompanionDeviceService(s) and trigger onDeviceDisappeared()callback.",
                    "      NOTE: This will only have effect if 'simulate-device-appeared' was",
                    "      invoked for the same device (same ASSOCIATION_ID) no longer than",
                    "      60 seconds ago.");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "    Case(2): ",
                    "      Make CDM act as if the given companion device is BT connected ",
                    "    Case(3): ",
                    "      Make CDM act as if the given companion device is BT disconnected ");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.",
                    "  simulate-device-uuid-event UUID PACKAGE USERID EVENT",
                    "  Simulate the companion device event changes:",
                    "    Case(2): ");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "      Make CDM act as if the given DEVICE is BT connected baseon the UUID",
                    "    Case(3): ",
                    "      Make CDM act as if the given DEVICE is BT disconnected baseon the UUID",
                    "      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  simulate-device-event-device-locked ASSOCIATION_ID USER_ID DEVICE_EVENT"
                        + " PARCEL_UUID",
                    "  Simulate device event when the device is locked",
                    "  USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.",
                    "  simulate-device-event-device-unlocked USER_ID");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  Simulate device unlocked for given user. This will send corresponding",
                    "  callback after simulate-device-event-device-locked",
                    "  command has been called.",
                    "  USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  start-observing-device-presence-uuid USER_ID PACKAGE_NAME UUID",
                    "  Start observing device presence base on the UUID.",
                    "  USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.",
                    "  stop-observing-device-presence-uuid USER_ID PACKAGE_NAME UUID");
            outPrintWriter.println("  Stop observing device presence base on the UUID.");
            outPrintWriter.println("  USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.");
        }
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  remove-inactive-associations",
                "      Remove self-managed associations that have not been active ",
                "      for a long time (90 days or as configured via ",
                "      \"debug.cdm.cdmservice.removal_time_window\" system property). ");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      USE FOR DEBUGGING AND/OR TESTING PURPOSES ONLY.",
                "  create-emulated-transport <ASSOCIATION_ID>",
                "      Create an EmulatedTransport for testing purposes only",
                "  enable-perm-sync <ASSOCIATION_ID>");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Enable perm sync for the association.",
                "  disable-perm-sync <ASSOCIATION_ID>",
                "      Disable perm sync for the association.",
                "  get-perm-sync-state <ASSOCIATION_ID>");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Get perm sync state for the association.",
                "  remove-perm-sync-state <ASSOCIATION_ID>",
                "      Remove perm sync state for the association.");
    }
}
