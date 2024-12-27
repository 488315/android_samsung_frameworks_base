package com.android.server.testharness;

import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.debug.AdbManagerInternal;
import android.location.LocationManager;
import android.os.Binder;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.pdb.PersistentDataBlockManagerInternal;
import com.android.server.pdb.PersistentDataBlockService;
import com.android.server.pm.UserManagerInternal;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TestHarnessModeService extends SystemService {
    public boolean mEnableKeepMemtagMode;
    public PersistentDataBlockManagerInternal mPersistentDataBlockManagerInternal;
    public final AnonymousClass1 mService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PersistentData {
        public final byte[] mAdbKeys;
        public final byte[] mAdbTempKeys;

        public PersistentData(byte[] bArr, byte[] bArr2) {
            this.mAdbKeys = bArr;
            this.mAdbTempKeys = bArr2;
        }

        public static PersistentData fromBytes(byte[] bArr) {
            try {
                DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
                if (dataInputStream.readInt() == 1) {
                    dataInputStream.readBoolean();
                }
                byte[] bArr2 = new byte[dataInputStream.readInt()];
                dataInputStream.readFully(bArr2);
                byte[] bArr3 = new byte[dataInputStream.readInt()];
                dataInputStream.readFully(bArr3);
                return new PersistentData(bArr2, bArr3);
            } catch (IOException e) {
                throw new SetUpTestHarnessModeException(e);
            }
        }

        public final byte[] toBytes() {
            byte[] bArr = this.mAdbTempKeys;
            byte[] bArr2 = this.mAdbKeys;
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeInt(2);
                dataOutputStream.writeInt(bArr2.length);
                dataOutputStream.write(bArr2);
                dataOutputStream.writeInt(bArr.length);
                dataOutputStream.write(bArr);
                dataOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class SetUpTestHarnessModeException extends Exception {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TestHarnessModeShellCommand extends ShellCommand {
        public TestHarnessModeShellCommand() {
        }

        public static byte[] getBytesFromFile(File file) {
            if (file == null || !file.exists()) {
                return new byte[0];
            }
            Path path = file.toPath();
            InputStream newInputStream = Files.newInputStream(path, new OpenOption[0]);
            try {
                int size = (int) Files.size(path);
                byte[] bArr = new byte[size];
                if (newInputStream.read(bArr) != size) {
                    throw new IOException("Failed to read the whole file");
                }
                newInputStream.close();
                return bArr;
            } catch (Throwable th) {
                if (newInputStream != null) {
                    try {
                        newInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int handleEnable() {
            AdbManagerInternal adbManagerInternal = (AdbManagerInternal) LocalServices.getService(AdbManagerInternal.class);
            try {
                PersistentData persistentData = new PersistentData(getBytesFromFile(adbManagerInternal.getAdbKeysFile()), getBytesFromFile(adbManagerInternal.getAdbTempKeysFile()));
                PersistentDataBlockManagerInternal persistentDataBlock = TestHarnessModeService.this.getPersistentDataBlock();
                if (persistentDataBlock == null) {
                    Slog.e("ShellCommand", "Failed to enable Test Harness Mode. No implementation of PersistentDataBlockManagerInternal was bound.");
                    getErrPrintWriter().println("Failed to enable Test Harness Mode");
                    return 1;
                }
                PersistentDataBlockService.InternalService internalService = (PersistentDataBlockService.InternalService) persistentDataBlock;
                internalService.writeInternal(PersistentDataBlockService.this.getTestHarnessModeDataOffset(), persistentData.toBytes(), 9996);
                Intent intent = new Intent("android.intent.action.FACTORY_RESET");
                intent.setPackage("android");
                intent.addFlags(268435456);
                intent.putExtra("android.intent.extra.REASON", "ShellCommand");
                intent.putExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", true);
                intent.putExtra("keep_memtag_mode", TestHarnessModeService.this.mEnableKeepMemtagMode);
                TestHarnessModeService.this.getContext().sendBroadcastAsUser(intent, UserHandle.SYSTEM);
                return 0;
            } catch (IOException e) {
                Slog.e("ShellCommand", "Failed to store ADB keys.", e);
                getErrPrintWriter().println("Failed to enable Test Harness Mode");
                return 1;
            }
        }

        public final int onCommand(String str) {
            if (str == null) {
                return handleDefaultCommands(str);
            }
            if (!str.equals("enable") && !str.equals("restore")) {
                return handleDefaultCommands(str);
            }
            while (true) {
                String nextOption = getNextOption();
                if (nextOption == null) {
                    TestHarnessModeService.this.getContext().enforceCallingPermission("android.permission.ENABLE_TEST_HARNESS_MODE", "You must hold android.permission.ENABLE_TEST_HARNESS_MODE to enable Test Harness Mode");
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        KeyguardManager keyguardManager = (KeyguardManager) TestHarnessModeService.this.getContext().getSystemService(KeyguardManager.class);
                        TestHarnessModeService.this.getClass();
                        if (!keyguardManager.isDeviceSecure(TestHarnessModeService.getMainUserId())) {
                            return handleEnable();
                        }
                        getErrPrintWriter().println("Test Harness Mode cannot be enabled if there is a lock screen");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 2;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                if (!nextOption.equals("--keep-memtag")) {
                    getErrPrintWriter().println("Invalid option: ".concat(nextOption));
                    return 1;
                }
                TestHarnessModeService.this.mEnableKeepMemtagMode = true;
            }
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("About:");
            outPrintWriter.println("  Test Harness Mode is a mode that the device can be placed in to prepare");
            outPrintWriter.println("  the device for running UI tests. The device is placed into this mode by");
            outPrintWriter.println("  first wiping all data from the device, preserving ADB keys.");
            outPrintWriter.println();
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  By default, the following settings are configured:", "    * Package Verifier is disabled", "    * Stay Awake While Charging is enabled", "    * OTA Updates are disabled");
            outPrintWriter.println("    * Auto-Sync for accounts is disabled");
            outPrintWriter.println();
            outPrintWriter.println("  Other apps may configure themselves differently in Test Harness Mode by");
            outPrintWriter.println("  checking ActivityManager.isRunningInUserTestHarness()");
            outPrintWriter.println();
            outPrintWriter.println("Test Harness Mode commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println();
            outPrintWriter.println("  enable|restore");
            outPrintWriter.println("    Erase all data from this device and enable Test Harness Mode,");
            outPrintWriter.println("    preserving the stored ADB keys currently on the device and toggling");
            outPrintWriter.println("    settings in a way that are conducive to Instrumentation testing.");
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.testharness.TestHarnessModeService$1] */
    public TestHarnessModeService(Context context) {
        super(context);
        this.mEnableKeepMemtagMode = false;
        this.mService = new Binder() { // from class: com.android.server.testharness.TestHarnessModeService.1
            public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
                TestHarnessModeService.this.new TestHarnessModeShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }
        };
    }

    public static int getMainUserId() {
        int mainUserId = ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getMainUserId();
        if (mainUserId >= 0) {
            return mainUserId;
        }
        Slog.w("TestHarnessModeService", "No MainUser exists; using user 0 instead");
        return 0;
    }

    public static void setUpAdbFiles(PersistentData persistentData) {
        AdbManagerInternal adbManagerInternal = (AdbManagerInternal) LocalServices.getService(AdbManagerInternal.class);
        if (adbManagerInternal.getAdbKeysFile() != null) {
            writeBytesToFile(persistentData.mAdbKeys, adbManagerInternal.getAdbKeysFile().toPath());
        }
        if (adbManagerInternal.getAdbTempKeysFile() != null) {
            writeBytesToFile(persistentData.mAdbTempKeys, adbManagerInternal.getAdbTempKeysFile().toPath());
        }
        adbManagerInternal.notifyKeyFilesUpdated();
    }

    public static void writeBytesToFile(byte[] bArr, Path path) {
        try {
            OutputStream newOutputStream = Files.newOutputStream(path, new OpenOption[0]);
            newOutputStream.write(bArr);
            newOutputStream.close();
            Set<PosixFilePermission> posixFilePermissions = Files.getPosixFilePermissions(path, new LinkOption[0]);
            posixFilePermissions.add(PosixFilePermission.GROUP_READ);
            Files.setPosixFilePermissions(path, posixFilePermissions);
        } catch (IOException e) {
            Slog.e("TestHarnessModeService", "Failed to set up adb keys", e);
        }
    }

    public final void configureSettings() {
        ContentResolver contentResolver = getContext().getContentResolver();
        if (Settings.Global.getInt(contentResolver, "adb_enabled", 0) == 1) {
            SystemProperties.set("ctl.restart", "adbd");
            Slog.d("TestHarnessModeService", "Restarted adbd");
        }
        Settings.Global.putLong(contentResolver, "adb_allowed_connection_time", 0L);
        Settings.Global.putInt(contentResolver, "development_settings_enabled", 1);
        Settings.Global.putInt(contentResolver, "verifier_verify_adb_installs", 0);
        Settings.Global.putInt(contentResolver, "stay_on_while_plugged_in", 15);
        Settings.Global.putInt(contentResolver, "ota_disable_automatic_update", 1);
    }

    public final void configureUser() {
        int mainUserId = getMainUserId();
        ContentResolver.setMasterSyncAutomaticallyAsUser(false, mainUserId);
        ((LocationManager) getContext().getSystemService(LocationManager.class)).setLocationEnabledForUser(true, UserHandle.of(mainUserId));
    }

    public final PersistentDataBlockManagerInternal getPersistentDataBlock() {
        if (this.mPersistentDataBlockManagerInternal == null) {
            Slog.d("TestHarnessModeService", "Getting PersistentDataBlockManagerInternal from LocalServices");
            this.mPersistentDataBlockManagerInternal = (PersistentDataBlockManagerInternal) LocalServices.getService(PersistentDataBlockManagerInternal.class);
        }
        return this.mPersistentDataBlockManagerInternal;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0037, code lost:
    
        if (r0.length == 0) goto L10;
     */
    @Override // com.android.server.SystemService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBootPhase(int r11) {
        /*
            Method dump skipped, instructions count: 305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.testharness.TestHarnessModeService.onBootPhase(int):void");
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("testharness", this.mService);
    }
}
