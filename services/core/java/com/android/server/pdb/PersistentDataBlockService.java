package com.android.server.pdb;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.security.Flags;
import android.service.persistentdata.IPersistentDataBlockService;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemServerInitThreadPool;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserManagerInternal;

import libcore.io.IoUtils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class PersistentDataBlockService extends SystemService {
    static final int FRP_CREDENTIAL_RESERVED_SIZE = 1000;
    static final byte[] FRP_SECRET_MAGIC = {-38, -62, -4, -51, -71, 27, 9, -120};
    static final int FRP_SECRET_SIZE = 32;
    static final int HEADER_SIZE = 8;
    static final int MAX_DATA_BLOCK_SIZE = 102400;
    static final int MAX_FRP_CREDENTIAL_HANDLE_SIZE = 996;
    static final int MAX_TEST_MODE_DATA_SIZE = 9996;
    static final int TEST_MODE_RESERVED_SIZE = 10000;
    public int mAllowedUid;
    public long mBlockDeviceSize;
    public final Context mContext;
    public final String mDataBlockFile;
    public boolean mFrpActive;
    public final boolean mFrpEnforced;
    public final String mFrpSecretFile;
    public final String mFrpSecretTmpFile;
    public final CountDownLatch mInitDoneSignal;
    public final InternalService mInternalService;
    public final boolean mIsFileBacked;
    public boolean mIsWritable;
    public final Object mLock;
    public final AnonymousClass1 mService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pdb.PersistentDataBlockService$1, reason: invalid class name */
    public final class AnonymousClass1 extends IPersistentDataBlockService.Stub {
        public AnonymousClass1() {}

        public static void printFrpDataFileContents(
                PrintWriter printWriter, String str, boolean z) {
            if (Files.exists(Paths.get(str, new String[0]), new LinkOption[0])) {
                if (!z) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            printWriter, "FRP secret file ", str, " exists, contents omitted.");
                    return;
                }
                try {
                    printWriter.println(
                            "FRP secret in "
                                    + str
                                    + ": "
                                    + HexFormat.of()
                                            .formatHex(
                                                    Files.readAllBytes(
                                                            Paths.get(str, new String[0]))));
                } catch (IOException e) {
                    Slog.e("PersistentDataBlockService", "Failed to read " + str, e);
                }
            }
        }

        public final boolean deactivateFactoryResetProtection(byte[] bArr) {
            PersistentDataBlockService persistentDataBlockService = PersistentDataBlockService.this;
            if (persistentDataBlockService.mFrpEnforced
                    && persistentDataBlockService.mContext.checkCallingOrSelfPermission(
                                    "android.permission.CONFIGURE_FACTORY_RESET_PROTECTION")
                            == -1) {
                throw new SecurityException(
                        "Can't configure Factory Reset Protection. Requires"
                            + " CONFIGURE_FACTORY_RESET_PROTECTION");
            }
            return PersistentDataBlockService.this.deactivateFrp(bArr);
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    PersistentDataBlockService.this.mContext,
                    "PersistentDataBlockService",
                    printWriter)) {
                StringBuilder m =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        printWriter,
                                        PersistentDataBlockService.this.mDataBlockFile,
                                        "mIsFileBacked: ",
                                        new StringBuilder("mDataBlockFile: ")),
                                PersistentDataBlockService.this.mIsFileBacked,
                                printWriter,
                                "mInitDoneSignal: ");
                m.append(PersistentDataBlockService.this.mInitDoneSignal);
                printWriter.println(m.toString());
                StringBuilder m2 =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("mAllowedUid: "),
                                PersistentDataBlockService.this.mAllowedUid,
                                printWriter,
                                "mBlockDeviceSize: ");
                m2.append(PersistentDataBlockService.this.mBlockDeviceSize);
                printWriter.println(m2.toString());
                synchronized (PersistentDataBlockService.this.mLock) {
                    printWriter.println(
                            "mIsWritable: " + PersistentDataBlockService.this.mIsWritable);
                }
                printFrpStatus(printWriter, false);
            }
        }

        public final void enforcePersistentDataBlockAccess() {
            if (PersistentDataBlockService.this.mContext.checkCallingPermission(
                            "android.permission.ACCESS_PDB_STATE")
                    != 0) {
                PersistentDataBlockService.m745$$Nest$menforceUid(
                        PersistentDataBlockService.this, Binder.getCallingUid());
            }
        }

        public final int getDataBlockSize() {
            int readInt;
            enforcePersistentDataBlockAccess();
            try {
                DataInputStream dataInputStream =
                        new DataInputStream(
                                new FileInputStream(
                                        new File(PersistentDataBlockService.this.mDataBlockFile)));
                try {
                    synchronized (PersistentDataBlockService.this.mLock) {
                        PersistentDataBlockService.this.getClass();
                        dataInputStream.skipBytes(32);
                        readInt =
                                dataInputStream.readInt() == 428873843
                                        ? dataInputStream.readInt()
                                        : 0;
                    }
                    return readInt;
                } catch (IOException unused) {
                    Slog.e("PersistentDataBlockService", "error reading data block size");
                    return 0;
                } finally {
                    IoUtils.closeQuietly(dataInputStream);
                }
            } catch (FileNotFoundException unused2) {
                Slog.e("PersistentDataBlockService", "partition not available");
                return 0;
            }
        }

        public final int getFlashLockState() {
            PersistentDataBlockService.m744$$Nest$menforceOemUnlockReadPermission(
                    PersistentDataBlockService.this);
            String str = SystemProperties.get("ro.boot.flash.locked");
            str.getClass();
            if (str.equals("0")) {
                return 0;
            }
            return !str.equals("1") ? -1 : 1;
        }

        public final long getMaximumDataBlockSize() {
            PersistentDataBlockService.m745$$Nest$menforceUid(
                    PersistentDataBlockService.this, Binder.getCallingUid());
            return PersistentDataBlockService.m743$$Nest$mdoGetMaximumDataBlockSize(
                    PersistentDataBlockService.this);
        }

        public final boolean getOemUnlockEnabled() {
            PersistentDataBlockService.m744$$Nest$menforceOemUnlockReadPermission(
                    PersistentDataBlockService.this);
            return PersistentDataBlockService.this.doGetOemUnlockEnabled();
        }

        public final String getPersistentDataPackageName() {
            enforcePersistentDataBlockAccess();
            return PersistentDataBlockService.this.mContext.getString(
                    R.string.ext_media_move_specific_title);
        }

        public final boolean hasFrpCredentialHandle() {
            PersistentDataBlockService persistentDataBlockService = PersistentDataBlockService.this;
            if (!persistentDataBlockService.mFrpEnforced) {
                enforcePersistentDataBlockAccess();
            } else if (persistentDataBlockService.mContext.checkCallingOrSelfPermission(
                            "android.permission.CONFIGURE_FACTORY_RESET_PROTECTION")
                    == -1) {
                enforcePersistentDataBlockAccess();
            }
            try {
                InternalService internalService = PersistentDataBlockService.this.mInternalService;
                return internalService.readInternal(
                                PersistentDataBlockService.MAX_FRP_CREDENTIAL_HANDLE_SIZE,
                                PersistentDataBlockService.this.getFrpCredentialDataOffset())
                        != null;
            } catch (IllegalStateException e) {
                Slog.e("PersistentDataBlockService", "error reading frp handle", e);
                throw new UnsupportedOperationException("cannot read frp credential");
            }
        }

        public final boolean isFactoryResetProtectionActive() {
            return PersistentDataBlockService.this.isFrpActive();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            if (PersistentDataBlockService.this.mFrpEnforced) {
                new ShellCommand() { // from class:
                    // com.android.server.pdb.PersistentDataBlockService.1.1
                    public static byte[] hashSecretString(String str) {
                        try {
                            return MessageDigest.getInstance("SHA-256").digest(str.getBytes());
                        } catch (NoSuchAlgorithmException e) {
                            Slog.e("ShellCommand", "Can't happen", e);
                            return new byte[32];
                        }
                    }

                    public final int onCommand(String str) {
                        PrintWriter outPrintWriter;
                        if (str == null) {
                            return handleDefaultCommands(str);
                        }
                        outPrintWriter = getOutPrintWriter();
                        switch (str) {
                            case "deactivate":
                                byte[] hashSecretString = hashSecretString(getNextArg());
                                outPrintWriter.println(
                                        "Attempting to deactivate with: "
                                                + HexFormat.of().formatHex(hashSecretString));
                                outPrintWriter.println(
                                        "Deactivation "
                                                .concat(
                                                        PersistentDataBlockService.this
                                                                        .deactivateFrp(
                                                                                hashSecretString)
                                                                ? "succeeded"
                                                                : "failed"));
                                AnonymousClass1.this.printFrpStatus(
                                        outPrintWriter,
                                        !PersistentDataBlockService.this.mFrpActive);
                                return 1;
                            case "activate":
                                PersistentDataBlockService.this.activateFrp();
                                AnonymousClass1.this.printFrpStatus(
                                        outPrintWriter,
                                        !PersistentDataBlockService.this.mFrpActive);
                                return 1;
                            case "set_secret":
                                byte[] bArr = new byte[32];
                                String nextArg = getNextArg();
                                if (!nextArg.equals("default")) {
                                    bArr = hashSecretString(nextArg);
                                }
                                StringBuilder sb = new StringBuilder("Setting FRP secret to: ");
                                sb.append(HexFormat.of().formatHex(bArr));
                                sb.append(" length: ");
                                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                        sb, bArr.length, outPrintWriter);
                                AnonymousClass1.this.setFactoryResetProtectionSecret(bArr);
                                AnonymousClass1.this.printFrpStatus(
                                        outPrintWriter,
                                        !PersistentDataBlockService.this.mFrpActive);
                                return 1;
                            case "status":
                                AnonymousClass1.this.printFrpStatus(
                                        outPrintWriter,
                                        !PersistentDataBlockService.this.mFrpActive);
                                return 1;
                            case "auto_deactivate":
                                outPrintWriter.println(
                                        "Automatic deactivation "
                                                .concat(
                                                        PersistentDataBlockService.this
                                                                        .automaticallyDeactivateFrpIfPossible()
                                                                ? "succeeded"
                                                                : "failed"));
                                AnonymousClass1.this.printFrpStatus(
                                        outPrintWriter,
                                        !PersistentDataBlockService.this.mFrpActive);
                                return 1;
                            default:
                                return handleDefaultCommands(str);
                        }
                    }

                    public final void onHelp() {
                        PrintWriter outPrintWriter = getOutPrintWriter();
                        outPrintWriter.println("Commands");
                        outPrintWriter.println(
                                "status: Print the FRP state and associated information.");
                        outPrintWriter.println("activate:  Put FRP into \"active\" mode.");
                        outPrintWriter.println(
                                "deactivate <secret>:  Deactivate with a hash of 'secret'.");
                        outPrintWriter.println(
                                "auto_deactivate: Deactivate with the stored secret or the"
                                    + " default");
                        outPrintWriter.println(
                                "set_secret <secret>:  Set the stored secret to a hash of"
                                    + " `secret`");
                    }
                }.exec(
                        this,
                        fileDescriptor,
                        fileDescriptor2,
                        fileDescriptor3,
                        strArr,
                        shellCallback,
                        resultReceiver);
            } else {
                super.onShellCommand(
                        fileDescriptor,
                        fileDescriptor2,
                        fileDescriptor3,
                        strArr,
                        shellCallback,
                        resultReceiver);
            }
        }

        public final void printFrpStatus(PrintWriter printWriter, boolean z) {
            boolean equals;
            PersistentDataBlockService.m745$$Nest$menforceUid(
                    PersistentDataBlockService.this, Binder.getCallingUid());
            printWriter.println("FRP state");
            printWriter.println("=========");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Enforcement enabled: "),
                            PersistentDataBlockService.this.mFrpEnforced,
                            printWriter,
                            "FRP state: "),
                    PersistentDataBlockService.this.mFrpActive,
                    printWriter);
            printFrpDataFileContents(
                    printWriter, PersistentDataBlockService.this.mFrpSecretFile, z);
            printFrpDataFileContents(
                    printWriter, PersistentDataBlockService.this.mFrpSecretTmpFile, z);
            PersistentDataBlockService persistentDataBlockService = PersistentDataBlockService.this;
            long frpSecretMagicOffset = persistentDataBlockService.getFrpSecretMagicOffset();
            byte[] bArr = PersistentDataBlockService.FRP_SECRET_MAGIC;
            byte[] readDataBlock =
                    persistentDataBlockService.readDataBlock(frpSecretMagicOffset, bArr.length);
            if (readDataBlock == null) {
                Slog.e("PersistentDataBlockService", "Failed to read FRP magic region.");
                equals = false;
            } else {
                equals = Arrays.equals(readDataBlock, bArr);
            }
            if (!equals) {
                printWriter.println("FRP magic not found");
            } else if (z) {
                StringBuilder sb = new StringBuilder("FRP secret in PDB: ");
                HexFormat of = HexFormat.of();
                PersistentDataBlockService persistentDataBlockService2 =
                        PersistentDataBlockService.this;
                sb.append(
                        of.formatHex(
                                persistentDataBlockService2.readDataBlock(
                                        persistentDataBlockService2.getFrpSecretDataOffset(), 32)));
                printWriter.println(sb.toString());
            } else {
                printWriter.println("FRP secret present but omitted.");
            }
            printWriter.println("OEM unlock state: " + getOemUnlockEnabled());
            printWriter.println("Bootloader lock state: " + getFlashLockState());
            printWriter.println(
                    "Verified boot state: " + SystemProperties.get("ro.boot.verifiedbootstate"));
            printWriter.println("Has FRP credential handle: " + hasFrpCredentialHandle());
            printWriter.println("FRP challenge block size: " + getDataBlockSize());
        }

        public final byte[] read() {
            PersistentDataBlockService.m745$$Nest$menforceUid(
                    PersistentDataBlockService.this, Binder.getCallingUid());
            if (!PersistentDataBlockService.this.enforceChecksumValidity()) {
                return new byte[0];
            }
            try {
                DataInputStream dataInputStream =
                        new DataInputStream(
                                new FileInputStream(
                                        new File(PersistentDataBlockService.this.mDataBlockFile)));
                try {
                    try {
                        synchronized (PersistentDataBlockService.this.mLock) {
                            PersistentDataBlockService.this.getClass();
                            dataInputStream.skipBytes(32);
                            int readInt =
                                    dataInputStream.readInt() == 428873843
                                            ? dataInputStream.readInt()
                                            : 0;
                            if (readInt == 0) {
                                return new byte[0];
                            }
                            byte[] bArr = new byte[readInt];
                            int read = dataInputStream.read(bArr, 0, readInt);
                            if (read >= readInt) {
                                try {
                                    dataInputStream.close();
                                } catch (IOException unused) {
                                    Slog.e(
                                            "PersistentDataBlockService",
                                            "failed to close OutputStream");
                                }
                                return bArr;
                            }
                            Slog.e(
                                    "PersistentDataBlockService",
                                    "failed to read entire data block. bytes read: "
                                            + read
                                            + "/"
                                            + readInt);
                            try {
                                dataInputStream.close();
                            } catch (IOException unused2) {
                                Slog.e(
                                        "PersistentDataBlockService",
                                        "failed to close OutputStream");
                            }
                            return null;
                        }
                    } catch (IOException e) {
                        Slog.e("PersistentDataBlockService", "failed to read data", e);
                        try {
                            dataInputStream.close();
                        } catch (IOException unused3) {
                            Slog.e("PersistentDataBlockService", "failed to close OutputStream");
                        }
                        return null;
                    }
                } finally {
                    try {
                        dataInputStream.close();
                    } catch (IOException unused4) {
                        Slog.e("PersistentDataBlockService", "failed to close OutputStream");
                    }
                }
            } catch (FileNotFoundException e2) {
                Slog.e("PersistentDataBlockService", "partition not available?", e2);
                return null;
            }
        }

        public final boolean setFactoryResetProtectionSecret(byte[] bArr) {
            PersistentDataBlockService persistentDataBlockService = PersistentDataBlockService.this;
            if (persistentDataBlockService.mFrpEnforced
                    && persistentDataBlockService.mContext.checkCallingOrSelfPermission(
                                    "android.permission.CONFIGURE_FACTORY_RESET_PROTECTION")
                            == -1) {
                throw new SecurityException(
                        "Can't configure Factory Reset Protection. Requires"
                            + " CONFIGURE_FACTORY_RESET_PROTECTION");
            }
            PersistentDataBlockService.m745$$Nest$menforceUid(
                    PersistentDataBlockService.this, Binder.getCallingUid());
            if (bArr == null || bArr.length != 32) {
                throw new IllegalArgumentException(
                        "Invalid FRP secret: " + HexFormat.of().formatHex(bArr));
            }
            PersistentDataBlockService.this.enforceFactoryResetProtectionInactive();
            PersistentDataBlockService persistentDataBlockService2 =
                    PersistentDataBlockService.this;
            persistentDataBlockService2.getClass();
            try {
                Files.write(
                        Paths.get(persistentDataBlockService2.mFrpSecretTmpFile, new String[0]),
                        bArr,
                        StandardOpenOption.WRITE,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.SYNC);
                if (!persistentDataBlockService2.mInternalService.writeDataBuffer(
                        persistentDataBlockService2.getFrpSecretDataOffset(),
                        ByteBuffer.wrap(bArr))) {
                    return false;
                }
                persistentDataBlockService2.moveFrpTempFileToPrimary();
                return true;
            } catch (IOException e) {
                Slog.e("PersistentDataBlockService", "Failed to write FRP secret file", e);
                return false;
            }
        }

        public final void setOemUnlockEnabled(boolean z) {
            if (ActivityManager.isUserAMonkey()) {
                return;
            }
            PersistentDataBlockService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.OEM_UNLOCK_STATE", "Can't modify OEM unlock state");
            PersistentDataBlockService persistentDataBlockService = PersistentDataBlockService.this;
            persistentDataBlockService.getClass();
            if (!UserManager.get(persistentDataBlockService.mContext)
                    .isUserAdmin(UserHandle.getCallingUserId())) {
                throw new SecurityException(
                        "Only the Admin user is allowed to change OEM unlock state");
            }
            if (z) {
                PersistentDataBlockService.m746$$Nest$menforceUserRestriction(
                        PersistentDataBlockService.this, "no_oem_unlock");
                PersistentDataBlockService.m746$$Nest$menforceUserRestriction(
                        PersistentDataBlockService.this, "no_factory_reset");
            }
            synchronized (PersistentDataBlockService.this.mLock) {
                PersistentDataBlockService.this.doSetOemUnlockEnabledLocked(z);
                PersistentDataBlockService.this.computeAndWriteDigestLocked();
            }
        }

        public final void wipe() {
            int i;
            PersistentDataBlockService.this.enforceFactoryResetProtectionInactive();
            PersistentDataBlockService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.OEM_UNLOCK_STATE", "Can't modify OEM unlock state");
            synchronized (PersistentDataBlockService.this.mLock) {
                PersistentDataBlockService persistentDataBlockService =
                        PersistentDataBlockService.this;
                if (persistentDataBlockService.mIsFileBacked) {
                    try {
                        Files.write(
                                Paths.get(persistentDataBlockService.mDataBlockFile, new String[0]),
                                new byte[PersistentDataBlockService.MAX_DATA_BLOCK_SIZE],
                                StandardOpenOption.TRUNCATE_EXISTING);
                        i = 0;
                    } catch (IOException unused) {
                        i = -1;
                    }
                } else {
                    i =
                            persistentDataBlockService.nativeWipe(
                                    persistentDataBlockService.mDataBlockFile);
                }
                if (i < 0) {
                    Slog.e("PersistentDataBlockService", "failed to wipe persistent partition");
                } else {
                    PersistentDataBlockService.this.mIsWritable = false;
                    Slog.i(
                            "PersistentDataBlockService",
                            "persistent partition now wiped and unwritable");
                }
            }
        }

        public final int write(byte[] bArr) {
            PersistentDataBlockService.m745$$Nest$menforceUid(
                    PersistentDataBlockService.this, Binder.getCallingUid());
            long m743$$Nest$mdoGetMaximumDataBlockSize =
                    PersistentDataBlockService.m743$$Nest$mdoGetMaximumDataBlockSize(
                            PersistentDataBlockService.this);
            if (bArr.length > m743$$Nest$mdoGetMaximumDataBlockSize) {
                return (int) (-m743$$Nest$mdoGetMaximumDataBlockSize);
            }
            ByteBuffer allocate = ByteBuffer.allocate(bArr.length + 40);
            allocate.put(new byte[32]);
            allocate.putInt(428873843);
            allocate.putInt(bArr.length);
            allocate.put(bArr);
            allocate.flip();
            synchronized (PersistentDataBlockService.this.mLock) {
                PersistentDataBlockService persistentDataBlockService =
                        PersistentDataBlockService.this;
                if (!persistentDataBlockService.mIsWritable) {
                    return -1;
                }
                try {
                    persistentDataBlockService.enforceFactoryResetProtectionInactive();
                    FileChannel channel =
                            new RandomAccessFile(persistentDataBlockService.mDataBlockFile, "rw")
                                    .getChannel();
                    try {
                        channel.write(allocate);
                        channel.force(true);
                        channel.close();
                        if (!PersistentDataBlockService.this.computeAndWriteDigestLocked()) {
                            return -1;
                        }
                        return bArr.length;
                    } catch (Throwable th) {
                        if (channel != null) {
                            try {
                                channel.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (IOException e) {
                    Slog.e(
                            "PersistentDataBlockService",
                            "failed writing to the persistent data block",
                            e);
                    return -1;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InternalService implements PersistentDataBlockManagerInternal {
        public InternalService() {}

        public final void clearTestHarnessModeData() {
            PersistentDataBlockService persistentDataBlockService = PersistentDataBlockService.this;
            byte[] readInternal =
                    readInternal(
                            PersistentDataBlockService.MAX_TEST_MODE_DATA_SIZE,
                            persistentDataBlockService.getTestHarnessModeDataOffset());
            if (readInternal == null) {
                readInternal = new byte[0];
            }
            writeDataBuffer(
                    persistentDataBlockService.getTestHarnessModeDataOffset(),
                    ByteBuffer.allocate(
                            Math.min(
                                            PersistentDataBlockService.MAX_TEST_MODE_DATA_SIZE,
                                            readInternal.length)
                                    + 4));
        }

        public final byte[] readInternal(int i, long j) {
            if (!PersistentDataBlockService.this.enforceChecksumValidity()) {
                throw new IllegalStateException("invalid checksum");
            }
            try {
                DataInputStream dataInputStream =
                        new DataInputStream(
                                new FileInputStream(
                                        new File(PersistentDataBlockService.this.mDataBlockFile)));
                try {
                    try {
                        synchronized (PersistentDataBlockService.this.mLock) {
                            dataInputStream.skip(j);
                            int readInt = dataInputStream.readInt();
                            if (readInt > 0 && readInt <= i) {
                                byte[] bArr = new byte[readInt];
                                dataInputStream.readFully(bArr);
                                return bArr;
                            }
                            IoUtils.closeQuietly(dataInputStream);
                            return null;
                        }
                    } catch (IOException e) {
                        throw new IllegalStateException("persistent partition not readable", e);
                    }
                } finally {
                    IoUtils.closeQuietly(dataInputStream);
                }
            } catch (FileNotFoundException unused) {
                throw new IllegalStateException("persistent partition not available");
            }
        }

        public final boolean writeDataBuffer(long j, ByteBuffer byteBuffer) {
            synchronized (PersistentDataBlockService.this.mLock) {
                PersistentDataBlockService persistentDataBlockService =
                        PersistentDataBlockService.this;
                if (!persistentDataBlockService.mIsWritable) {
                    return false;
                }
                try {
                    persistentDataBlockService.enforceFactoryResetProtectionInactive();
                    FileChannel channel =
                            new RandomAccessFile(persistentDataBlockService.mDataBlockFile, "rw")
                                    .getChannel();
                    try {
                        channel.position(j);
                        channel.write(byteBuffer);
                        channel.force(true);
                        channel.close();
                        return PersistentDataBlockService.this.computeAndWriteDigestLocked();
                    } catch (Throwable th) {
                        if (channel != null) {
                            try {
                                channel.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (IOException e) {
                    Slog.e(
                            "PersistentDataBlockService",
                            "unable to access persistent partition",
                            e);
                    return false;
                }
            }
        }

        public final void writeInternal(long j, byte[] bArr, int i) {
            boolean z = true;
            Preconditions.checkArgument(
                    bArr == null || bArr.length > 0, "data must be null or non-empty");
            if (bArr != null && bArr.length > i) {
                z = false;
            }
            Preconditions.checkArgument(z, "data must not be longer than " + i);
            ByteBuffer allocate = ByteBuffer.allocate(i + 4);
            allocate.putInt(bArr != null ? bArr.length : 0);
            if (bArr != null) {
                allocate.put(bArr);
            }
            allocate.flip();
            writeDataBuffer(j, allocate);
        }
    }

    /* renamed from: -$$Nest$mdoGetMaximumDataBlockSize, reason: not valid java name */
    public static long m743$$Nest$mdoGetMaximumDataBlockSize(
            PersistentDataBlockService persistentDataBlockService) {
        long blockDeviceSize =
                ((persistentDataBlockService.getBlockDeviceSize() - 10040)
                                - (persistentDataBlockService.mFrpEnforced
                                        ? FRP_SECRET_MAGIC.length + 32
                                        : 0L))
                        - 1001;
        if (blockDeviceSize <= 102400) {
            return blockDeviceSize;
        }
        return 102400L;
    }

    /* renamed from: -$$Nest$menforceOemUnlockReadPermission, reason: not valid java name */
    public static void m744$$Nest$menforceOemUnlockReadPermission(
            PersistentDataBlockService persistentDataBlockService) {
        if (persistentDataBlockService.mContext.checkCallingOrSelfPermission(
                                "android.permission.READ_OEM_UNLOCK_STATE")
                        == -1
                && persistentDataBlockService.mContext.checkCallingOrSelfPermission(
                                "android.permission.OEM_UNLOCK_STATE")
                        == -1) {
            throw new SecurityException(
                    "Can't access OEM unlock state. Requires READ_OEM_UNLOCK_STATE or"
                        + " OEM_UNLOCK_STATE permission.");
        }
    }

    /* renamed from: -$$Nest$menforceUid, reason: not valid java name */
    public static void m745$$Nest$menforceUid(
            PersistentDataBlockService persistentDataBlockService, int i) {
        if (i != persistentDataBlockService.mAllowedUid && i != 0) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "uid ", " not allowed to access PDB"));
        }
    }

    /* renamed from: -$$Nest$menforceUserRestriction, reason: not valid java name */
    public static void m746$$Nest$menforceUserRestriction(
            PersistentDataBlockService persistentDataBlockService, String str) {
        if (UserManager.get(persistentDataBlockService.mContext).hasUserRestriction(str)) {
            throw new SecurityException(
                    "OEM unlock is disallowed by user restriction: ".concat(str));
        }
    }

    public PersistentDataBlockService(Context context) {
        super(context);
        this.mLock = new Object();
        this.mInitDoneSignal = new CountDownLatch(1);
        this.mAllowedUid = -1;
        this.mBlockDeviceSize = -1L;
        this.mFrpActive = false;
        this.mIsWritable = true;
        this.mService = new AnonymousClass1();
        this.mInternalService = new InternalService();
        this.mContext = context;
        boolean frpEnforcement = Flags.frpEnforcement();
        this.mFrpEnforced = frpEnforcement;
        this.mFrpActive = frpEnforcement;
        this.mFrpSecretFile = "/data/system/frp_secret";
        this.mFrpSecretTmpFile = "/data/system/frp_secret_tmp";
        if (SystemProperties.getBoolean("ro.gsid.image_running", false)) {
            this.mIsFileBacked = true;
            this.mDataBlockFile = "/data/gsi_persistent_data";
        } else {
            this.mIsFileBacked = false;
            this.mDataBlockFile = SystemProperties.get("ro.frp.pst");
        }
    }

    public PersistentDataBlockService(
            Context context, boolean z, String str, long j, boolean z2, String str2, String str3) {
        super(context);
        this.mLock = new Object();
        this.mInitDoneSignal = new CountDownLatch(1);
        this.mAllowedUid = -1;
        this.mBlockDeviceSize = -1L;
        this.mFrpActive = false;
        this.mIsWritable = true;
        this.mService = new AnonymousClass1();
        this.mInternalService = new InternalService();
        this.mContext = context;
        this.mIsFileBacked = z;
        this.mDataBlockFile = str;
        this.mBlockDeviceSize = j;
        this.mFrpEnforced = z2;
        this.mFrpActive = z2;
        this.mFrpSecretFile = str2;
        this.mFrpSecretTmpFile = str3;
    }

    private native long nativeGetBlockDeviceSize(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeWipe(String str);

    public void activateFrp() {
        synchronized (this.mLock) {
            this.mFrpActive = true;
            setOldSettingForBackworkCompatibility(true);
        }
    }

    public boolean automaticallyDeactivateFrpIfPossible() {
        synchronized (this.mLock) {
            try {
                if (deactivateFrpWithFileSecret(this.mFrpSecretFile)) {
                    return true;
                }
                Slog.w(
                        "PersistentDataBlockService",
                        "Failed to deactivate with primary secret file, trying backup.");
                if (deactivateFrpWithFileSecret(this.mFrpSecretTmpFile)) {
                    moveFrpTempFileToPrimary();
                    return true;
                }
                Slog.w(
                        "PersistentDataBlockService",
                        "Failed to deactivate with backup secret file, trying default secret.");
                if (deactivateFrp(new byte[32])) {
                    return true;
                }
                if (!isUpgradingFromPreVRelease()) {
                    Slog.e(
                            "PersistentDataBlockService",
                            "Did not find valid FRP secret, FRP remains active.");
                    return false;
                }
                Slog.w(
                        "PersistentDataBlockService",
                        "Upgrading from Android 14 or lower, defaulting FRP secret");
                writeFrpMagicAndDefaultSecret();
                this.mFrpActive = false;
                setOldSettingForBackworkCompatibility(false);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean computeAndWriteDigestLocked() {
        byte[] computeDigestLocked = computeDigestLocked(null);
        if (computeDigestLocked != null) {
            try {
                enforceFactoryResetProtectionInactive();
                FileChannel channel = new RandomAccessFile(this.mDataBlockFile, "rw").getChannel();
                try {
                    ByteBuffer allocate = ByteBuffer.allocate(32);
                    allocate.put(computeDigestLocked);
                    allocate.flip();
                    channel.write(allocate);
                    channel.force(true);
                    channel.close();
                    return true;
                } finally {
                }
            } catch (IOException e) {
                Slog.e("PersistentDataBlockService", "failed to write block checksum", e);
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003b A[Catch: all -> 0x0025, IOException -> 0x0027, LOOP:0: B:11:0x0034->B:13:0x003b, LOOP_END, TRY_LEAVE, TryCatch #0 {IOException -> 0x0027, blocks: (B:30:0x001e, B:32:0x0021, B:10:0x002c, B:11:0x0034, B:13:0x003b, B:9:0x0029), top: B:29:0x001e, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] computeDigestLocked(byte[] r7) {
        /*
            r6 = this;
            java.lang.String r0 = "PersistentDataBlockService"
            r1 = 0
            java.io.DataInputStream r2 = new java.io.DataInputStream     // Catch: java.io.FileNotFoundException -> L5e
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.io.FileNotFoundException -> L5e
            java.io.File r4 = new java.io.File     // Catch: java.io.FileNotFoundException -> L5e
            java.lang.String r6 = r6.mDataBlockFile     // Catch: java.io.FileNotFoundException -> L5e
            r4.<init>(r6)     // Catch: java.io.FileNotFoundException -> L5e
            r3.<init>(r4)     // Catch: java.io.FileNotFoundException -> L5e
            r2.<init>(r3)     // Catch: java.io.FileNotFoundException -> L5e
            java.lang.String r6 = "SHA-256"
            java.security.MessageDigest r6 = java.security.MessageDigest.getInstance(r6)     // Catch: java.security.NoSuchAlgorithmException -> L54
            r3 = 32
            if (r7 == 0) goto L29
            int r4 = r7.length     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            if (r4 != r3) goto L29
            r2.read(r7)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            goto L2c
        L25:
            r6 = move-exception
            goto L50
        L27:
            r6 = move-exception
            goto L47
        L29:
            r2.skipBytes(r3)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
        L2c:
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r7 = new byte[r7]     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            r4 = 0
            r6.update(r7, r4, r3)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
        L34:
            int r3 = r2.read(r7)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            r5 = -1
            if (r3 == r5) goto L3f
            r6.update(r7, r4, r3)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            goto L34
        L3f:
            libcore.io.IoUtils.closeQuietly(r2)
            byte[] r6 = r6.digest()
            return r6
        L47:
            java.lang.String r7 = "failed to read partition"
            android.util.Slog.e(r0, r7, r6)     // Catch: java.lang.Throwable -> L25
            libcore.io.IoUtils.closeQuietly(r2)
            return r1
        L50:
            libcore.io.IoUtils.closeQuietly(r2)
            throw r6
        L54:
            r6 = move-exception
            java.lang.String r7 = "SHA-256 not supported?"
            android.util.Slog.e(r0, r7, r6)
            libcore.io.IoUtils.closeQuietly(r2)
            return r1
        L5e:
            r6 = move-exception
            java.lang.String r7 = "partition not available?"
            android.util.Slog.e(r0, r7, r6)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pdb.PersistentDataBlockService.computeDigestLocked(byte[]):byte[]");
    }

    public final boolean deactivateFrp(byte[] bArr) {
        boolean equals;
        if (bArr == null || bArr.length != 32) {
            Slog.w(
                    "PersistentDataBlockService",
                    "Attempted to deactivate FRP with a null or incorrectly-sized secret");
            return false;
        }
        synchronized (this.mLock) {
            try {
                long frpSecretMagicOffset = getFrpSecretMagicOffset();
                byte[] bArr2 = FRP_SECRET_MAGIC;
                byte[] readDataBlock = readDataBlock(frpSecretMagicOffset, bArr2.length);
                if (readDataBlock == null) {
                    Slog.e("PersistentDataBlockService", "Failed to read FRP magic region.");
                    equals = false;
                } else {
                    equals = Arrays.equals(readDataBlock, bArr2);
                }
                if (!equals) {
                    Slog.i(
                            "PersistentDataBlockService",
                            "No FRP secret magic, system must have been upgraded.");
                    writeFrpMagicAndDefaultSecret();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        byte[] readDataBlock2 = readDataBlock(getFrpSecretDataOffset(), 32);
        if (readDataBlock2 == null || readDataBlock2.length != 32) {
            Slog.e(
                    "PersistentDataBlockService",
                    "Failed to read FRP secret from persistent data partition");
            return false;
        }
        if (MessageDigest.isEqual(bArr, readDataBlock2)) {
            this.mFrpActive = false;
            Slog.i("PersistentDataBlockService", "FRP secret matched, FRP deactivated.");
            setOldSettingForBackworkCompatibility(this.mFrpActive);
            return true;
        }
        Slog.e(
                "PersistentDataBlockService",
                "FRP deactivation failed with secret " + HexFormat.of().formatHex(bArr));
        return false;
    }

    public final boolean deactivateFrpWithFileSecret(String str) {
        try {
            return deactivateFrp(Files.readAllBytes(Paths.get(str, new String[0])));
        } catch (IOException e) {
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "Failed to read FRP secret file: ", str, " ");
            m.append(e.getClass().getSimpleName());
            Slog.i("PersistentDataBlockService", m.toString());
            return false;
        }
    }

    public final boolean doGetOemUnlockEnabled() {
        boolean z;
        try {
            DataInputStream dataInputStream =
                    new DataInputStream(new FileInputStream(new File(this.mDataBlockFile)));
            try {
                synchronized (this.mLock) {
                    dataInputStream.skip(getBlockDeviceSize() - 1);
                    z = dataInputStream.readByte() != 0;
                }
                return z;
            } catch (IOException e) {
                Slog.e("PersistentDataBlockService", "unable to access persistent partition", e);
                return false;
            } finally {
                IoUtils.closeQuietly(dataInputStream);
            }
        } catch (FileNotFoundException unused) {
            Slog.e("PersistentDataBlockService", "partition not available");
            return false;
        }
    }

    public final void doSetOemUnlockEnabledLocked(boolean z) {
        try {
            enforceFactoryResetProtectionInactive();
            FileChannel channel = new RandomAccessFile(this.mDataBlockFile, "rw").getChannel();
            try {
                channel.position(getBlockDeviceSize() - 1);
                ByteBuffer allocate = ByteBuffer.allocate(1);
                allocate.put(z ? (byte) 1 : (byte) 0);
                allocate.flip();
                channel.write(allocate);
                channel.force(true);
                channel.close();
            } catch (Throwable th) {
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            Slog.e("PersistentDataBlockService", "unable to access persistent partition", e);
        } finally {
            setOemUnlockEnabledProperty(z);
        }
    }

    public final boolean enforceChecksumValidity() {
        byte[] bArr = new byte[32];
        synchronized (this.mLock) {
            try {
                byte[] computeDigestLocked = computeDigestLocked(bArr);
                if (computeDigestLocked != null && Arrays.equals(bArr, computeDigestLocked)) {
                    return true;
                }
                Slog.i("PersistentDataBlockService", "Formatting FRP partition...");
                formatPartitionLocked(false);
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void enforceFactoryResetProtectionInactive() {
        if (this.mFrpEnforced && isFrpActive()) {
            Slog.w(
                    "PersistentDataBlockService",
                    "Attempt to update PDB was blocked because FRP is active.");
            throw new SecurityException("FRP is active");
        }
    }

    public void formatPartitionLocked(boolean z) {
        ByteBuffer allocate;
        try {
            FileChannel channel = new RandomAccessFile(this.mDataBlockFile, "rw").getChannel();
            try {
                ByteBuffer allocate2 = ByteBuffer.allocate(40);
                allocate2.put(new byte[32]);
                allocate2.putInt(428873843);
                allocate2.putInt(0);
                allocate2.flip();
                channel.write(allocate2);
                channel.force(true);
                int blockDeviceSize = (int) getBlockDeviceSize();
                boolean z2 = this.mFrpEnforced;
                if (z2) {
                    allocate =
                            ByteBuffer.allocate(
                                    ((blockDeviceSize - 10040) - FRP_SECRET_MAGIC.length) - 1033);
                } else {
                    allocate = ByteBuffer.allocate(blockDeviceSize - 11041);
                }
                channel.write(allocate);
                channel.force(true);
                if (z2) {
                    Slog.i("PersistentDataBlockService", "Writing FRP secret magic");
                    channel.write(ByteBuffer.wrap(FRP_SECRET_MAGIC));
                    Slog.i("PersistentDataBlockService", "Writing default FRP secret");
                    channel.write(ByteBuffer.allocate(32));
                    channel.force(true);
                    this.mFrpActive = false;
                }
                channel.position(channel.position() + 10000);
                channel.write(ByteBuffer.allocate(1000));
                channel.force(true);
                ByteBuffer allocate3 = ByteBuffer.allocate(1000);
                allocate3.put((byte) 0);
                allocate3.flip();
                channel.write(allocate3);
                channel.force(true);
                channel.close();
                doSetOemUnlockEnabledLocked(z);
                computeAndWriteDigestLocked();
            } finally {
            }
        } catch (IOException e) {
            Slog.e("PersistentDataBlockService", "failed to format block", e);
        }
    }

    public long getBlockDeviceSize() {
        synchronized (this.mLock) {
            try {
                if (this.mBlockDeviceSize == -1) {
                    if (this.mIsFileBacked) {
                        this.mBlockDeviceSize = 102400L;
                    } else {
                        this.mBlockDeviceSize = nativeGetBlockDeviceSize(this.mDataBlockFile);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mBlockDeviceSize;
    }

    public long getFrpCredentialDataOffset() {
        return getOemUnlockDataOffset() - 1000;
    }

    public long getFrpSecretDataOffset() {
        return getTestHarnessModeDataOffset() - 32;
    }

    public long getFrpSecretMagicOffset() {
        return getFrpSecretDataOffset() - FRP_SECRET_MAGIC.length;
    }

    public IPersistentDataBlockService getInterfaceForTesting() {
        return IPersistentDataBlockService.Stub.asInterface(this.mService);
    }

    public PersistentDataBlockManagerInternal getInternalInterfaceForTesting() {
        return this.mInternalService;
    }

    public int getMaximumFrpDataSize() {
        return (int)
                ((getTestHarnessModeDataOffset() - 40)
                        - (this.mFrpEnforced ? FRP_SECRET_MAGIC.length + 32 : 0L));
    }

    public long getOemUnlockDataOffset() {
        return getBlockDeviceSize() - 1;
    }

    public long getTestHarnessModeDataOffset() {
        return getFrpCredentialDataOffset() - 10000;
    }

    public boolean isFrpActive() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mFrpActive;
        }
        return z;
    }

    public boolean isUpgradingFromPreVRelease() {
        PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        if (packageManagerInternal == null) {
            Slog.e("PersistentDataBlockService", "Unable to retrieve PackageManagerInternal");
            return false;
        }
        int i = PackageManagerService.this.mPriorSdkVersion;
        return i != -1 && i < 35;
    }

    public final void moveFrpTempFileToPrimary() {
        try {
            Files.move(
                    Paths.get(this.mFrpSecretTmpFile, new String[0]),
                    Paths.get(this.mFrpSecretFile, new String[0]),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Slog.e(
                    "PersistentDataBlockService",
                    "Error moving FRP backup file to primary (ignored)",
                    e);
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        int packageUidAsUser;
        if (i == 500) {
            try {
                if (!this.mInitDoneSignal.await(10L, TimeUnit.SECONDS)) {
                    throw new IllegalStateException(
                            "Service PersistentDataBlockService init timeout");
                }
                int mainUserId =
                        ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
                                .getMainUserId();
                if (mainUserId < 0) {
                    mainUserId = 0;
                }
                String string =
                        this.mContext
                                .getResources()
                                .getString(R.string.ext_media_move_specific_title);
                if (!TextUtils.isEmpty(string)) {
                    try {
                        packageUidAsUser =
                                this.mContext
                                        .getPackageManager()
                                        .getPackageUidAsUser(string, 1048576, mainUserId);
                    } catch (PackageManager.NameNotFoundException e) {
                        Slog.e(
                                "PersistentDataBlockService",
                                "not able to find package " + string,
                                e);
                    }
                    this.mAllowedUid = packageUidAsUser;
                    LocalServices.addService(
                            PersistentDataBlockManagerInternal.class, this.mInternalService);
                }
                packageUidAsUser = -1;
                this.mAllowedUid = packageUidAsUser;
                LocalServices.addService(
                        PersistentDataBlockManagerInternal.class, this.mInternalService);
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(
                        "Service PersistentDataBlockService init interrupted", e2);
            }
        }
        super.onBootPhase(i);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        SystemServerInitThreadPool.submit(
                "PersistentDataBlockService.onStart",
                new Runnable() { // from class:
                                 // com.android.server.pdb.PersistentDataBlockService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PersistentDataBlockService persistentDataBlockService =
                                PersistentDataBlockService.this;
                        persistentDataBlockService.enforceChecksumValidity();
                        if (persistentDataBlockService.mFrpEnforced) {
                            persistentDataBlockService.automaticallyDeactivateFrpIfPossible();
                            persistentDataBlockService.setOemUnlockEnabledProperty(
                                    persistentDataBlockService.doGetOemUnlockEnabled());
                            persistentDataBlockService.setOldSettingForBackworkCompatibility(
                                    persistentDataBlockService.mFrpActive);
                        } else {
                            boolean doGetOemUnlockEnabled =
                                    persistentDataBlockService.doGetOemUnlockEnabled();
                            if (doGetOemUnlockEnabled) {
                                synchronized (persistentDataBlockService.mLock) {
                                    persistentDataBlockService.formatPartitionLocked(true);
                                }
                            }
                            persistentDataBlockService.setOemUnlockEnabledProperty(
                                    doGetOemUnlockEnabled);
                        }
                        persistentDataBlockService.publishBinderService(
                                "persistent_data_block", persistentDataBlockService.mService);
                        persistentDataBlockService.signalInitDone();
                    }
                });
    }

    public byte[] readDataBlock(long j, int i) {
        byte[] bArr;
        try {
            DataInputStream dataInputStream =
                    new DataInputStream(new FileInputStream(new File(this.mDataBlockFile)));
            try {
                synchronized (this.mLock) {
                    dataInputStream.skip(j);
                    bArr = new byte[i];
                    dataInputStream.readFully(bArr);
                }
                dataInputStream.close();
                return bArr;
            } finally {
            }
        } catch (IOException e) {
            throw new IllegalStateException("persistent partition not readable", e);
        }
    }

    public void setAllowedUid(int i) {
        this.mAllowedUid = i;
    }

    public final void setOemUnlockEnabledProperty(boolean z) {
        setProperty("sys.oem_unlock_allowed", z ? "1" : "0");
    }

    public final void setOldSettingForBackworkCompatibility(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.Global.putInt(
                    this.mContext.getContentResolver(), "secure_frp_mode", z ? 1 : 0);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setProperty(String str, String str2) {
        SystemProperties.set(str, str2);
    }

    public void signalInitDone() {
        this.mInitDoneSignal.countDown();
    }

    public final void writeFrpMagicAndDefaultSecret() {
        try {
            FileChannel channel = new RandomAccessFile(this.mDataBlockFile, "rw").getChannel();
            try {
                synchronized (this.mLock) {
                    Slog.i("PersistentDataBlockService", "Writing default FRP secret");
                    channel.position(getFrpSecretDataOffset());
                    channel.write(ByteBuffer.allocate(32));
                    channel.force(true);
                    Slog.i("PersistentDataBlockService", "Writing FRP secret magic");
                    channel.position(getFrpSecretMagicOffset());
                    channel.write(ByteBuffer.wrap(FRP_SECRET_MAGIC));
                    channel.force(true);
                    this.mFrpActive = false;
                }
                channel.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e("PersistentDataBlockService", "Failed to write FRP magic and default secret", e);
        }
        computeAndWriteDigestLocked();
    }
}
