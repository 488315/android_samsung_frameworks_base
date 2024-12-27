package com.android.server.pm;

import android.content.ComponentName;
import android.content.pm.ArchivedPackageParcel;
import android.content.pm.DataLoaderParams;
import android.content.pm.InstallationFile;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ShellCommand;
import android.service.dataloader.DataLoaderService;
import android.util.Slog;
import android.util.SparseArray;

import libcore.io.IoUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

public class PackageManagerShellCommandDataLoader extends DataLoaderService {
    public static final char ARGS_DELIM = '&';
    public static final int INVALID_SHELL_COMMAND_ID = -1;
    public static final String PACKAGE = "android";
    public static final String SHELL_COMMAND_ID_PREFIX = "shellCommandId=";
    public static final String STDIN_PATH = "-";
    public static final String TAG = "PackageManagerShellCommandDataLoader";
    public static final int TOO_MANY_PENDING_SHELL_COMMANDS = 10;
    public static final String CLASS = PackageManagerShellCommandDataLoader.class.getName();
    public static final SecureRandom sRandom = new SecureRandom();
    public static final SparseArray sShellCommands = new SparseArray();

    public final class DataLoader implements DataLoaderService.DataLoader {
        public DataLoaderService.FileSystemConnector mConnector;
        public DataLoaderParams mParams;

        public final boolean onCreate(
                DataLoaderParams dataLoaderParams,
                DataLoaderService.FileSystemConnector fileSystemConnector) {
            this.mParams = dataLoaderParams;
            this.mConnector = fileSystemConnector;
            return true;
        }

        public final boolean onPrepareImage(Collection collection, Collection collection2) {
            ShellCommand lookupShellCommand =
                    PackageManagerShellCommandDataLoader.lookupShellCommand(
                            this.mParams.getArguments());
            try {
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    InstallationFile installationFile = (InstallationFile) it.next();
                    Metadata fromByteArray = Metadata.fromByteArray(installationFile.getMetadata());
                    if (fromByteArray == null) {
                        Slog.e(
                                PackageManagerShellCommandDataLoader.TAG,
                                "Invalid metadata for file: " + installationFile.getName());
                        return false;
                    }
                    byte b = fromByteArray.mMode;
                    if (b != 0) {
                        if (b != 1) {
                            if (b != 4) {
                                Slog.e(
                                        PackageManagerShellCommandDataLoader.TAG,
                                        "Unsupported metadata mode: " + ((int) b));
                                return false;
                            }
                        } else {
                            if (lookupShellCommand == null) {
                                Slog.e(
                                        PackageManagerShellCommandDataLoader.TAG,
                                        "Missing shell command for Metadata.LOCAL_FILE.");
                                return false;
                            }
                            ParcelFileDescriptor parcelFileDescriptor = null;
                            try {
                                parcelFileDescriptor =
                                        lookupShellCommand.openFileForSystem(
                                                new String(
                                                        fromByteArray.mData,
                                                        StandardCharsets.UTF_8),
                                                "r");
                                this.mConnector.writeData(
                                        installationFile.getName(),
                                        0L,
                                        parcelFileDescriptor.getStatSize(),
                                        parcelFileDescriptor);
                                IoUtils.closeQuietly(parcelFileDescriptor);
                            } catch (Throwable th) {
                                IoUtils.closeQuietly(parcelFileDescriptor);
                                throw th;
                            }
                        }
                    } else {
                        if (lookupShellCommand == null) {
                            Slog.e(
                                    PackageManagerShellCommandDataLoader.TAG,
                                    "Missing shell command for Metadata.STDIN.");
                            return false;
                        }
                        this.mConnector.writeData(
                                installationFile.getName(),
                                0L,
                                installationFile.getLengthBytes(),
                                PackageManagerShellCommandDataLoader.getStdInPFD(
                                        lookupShellCommand));
                    }
                }
                return true;
            } catch (IOException e) {
                Slog.e(
                        PackageManagerShellCommandDataLoader.TAG,
                        "Exception while streaming files",
                        e);
                return false;
            }
        }
    }

    public class Metadata {
        public static final AtomicLong sGlobalSalt = new AtomicLong(new SecureRandom().nextLong());
        public final byte[] mData;
        public final byte mMode;
        public final String mSalt;

        public Metadata(byte b, String str, String str2) {
            this(b, (str == null ? "" : str).getBytes(StandardCharsets.UTF_8), str2);
        }

        public Metadata(byte b, byte[] bArr, String str) {
            this.mMode = b;
            this.mData = bArr;
            this.mSalt = str;
        }

        public static Metadata forArchived(ArchivedPackageParcel archivedPackageParcel) {
            Parcel obtain = Parcel.obtain();
            try {
                obtain.writeParcelable(archivedPackageParcel, 0);
                byte[] marshall = obtain.marshall();
                obtain.recycle();
                return new Metadata((byte) 4, marshall, (String) null);
            } catch (Throwable th) {
                obtain.recycle();
                throw th;
            }
        }

        public static Metadata forLocalFile(String str) {
            return new Metadata(
                    (byte) 1, str, Long.valueOf(sGlobalSalt.incrementAndGet()).toString());
        }

        public static Metadata fromByteArray(byte[] bArr) {
            byte[] bArr2;
            String str = null;
            if (bArr == null || bArr.length < 5) {
                return null;
            }
            byte b = bArr[0];
            if (b != 1) {
                bArr2 = Arrays.copyOfRange(bArr, 1, bArr.length);
            } else {
                int i = ByteBuffer.wrap(bArr, 1, 4).order(ByteOrder.LITTLE_ENDIAN).getInt() + 5;
                byte[] copyOfRange = Arrays.copyOfRange(bArr, 5, i);
                String str2 = new String(bArr, i, bArr.length - i, StandardCharsets.UTF_8);
                bArr2 = copyOfRange;
                str = str2;
            }
            return new Metadata(b, bArr2, str);
        }

        public static ArchivedPackageParcel readArchivedPackageParcel(byte[] bArr) {
            Parcel obtain = Parcel.obtain();
            try {
                obtain.unmarshall(bArr, 0, bArr.length);
                obtain.setDataPosition(0);
                return obtain.readParcelable(ArchivedPackageParcel.class.getClassLoader());
            } finally {
                obtain.recycle();
            }
        }

        public byte[] toByteArray() {
            byte[] bArr = this.mData;
            byte b = this.mMode;
            if (b != 1) {
                byte[] bArr2 = new byte[bArr.length + 1];
                bArr2[0] = b;
                System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
                return bArr2;
            }
            int length = bArr.length;
            byte[] bytes = this.mSalt.getBytes(StandardCharsets.UTF_8);
            byte[] bArr3 = new byte[length + 5 + bytes.length];
            bArr3[0] = b;
            ByteBuffer.wrap(bArr3, 1, 4).order(ByteOrder.LITTLE_ENDIAN).putInt(length);
            System.arraycopy(bArr, 0, bArr3, 5, length);
            System.arraycopy(bytes, 0, bArr3, 5 + length, bytes.length);
            return bArr3;
        }
    }

    public static int extractShellCommandId(String str) {
        int indexOf = str.indexOf(SHELL_COMMAND_ID_PREFIX);
        if (indexOf < 0) {
            Slog.e(TAG, "Missing shell command id param.");
            return -1;
        }
        int i = indexOf + 15;
        int indexOf2 = str.indexOf(38, i);
        try {
            return indexOf2 < 0
                    ? Integer.parseInt(str.substring(i))
                    : Integer.parseInt(str.substring(i, indexOf2));
        } catch (NumberFormatException e) {
            Slog.e(TAG, "Incorrect shell command id format.", e);
            return -1;
        }
    }

    public static String getDataLoaderParamsArgs(ShellCommand shellCommand) {
        int nextInt;
        SparseArray sparseArray;
        nativeInitialize();
        SparseArray sparseArray2 = sShellCommands;
        synchronized (sparseArray2) {
            try {
                for (int size = sparseArray2.size() - 1; size >= 0; size--) {
                    SparseArray sparseArray3 = sShellCommands;
                    if (((WeakReference) sparseArray3.valueAt(size)).get() == null) {
                        sparseArray3.removeAt(size);
                    }
                }
                SparseArray sparseArray4 = sShellCommands;
                if (sparseArray4.size() > 10) {
                    Slog.e(TAG, "Too many pending shell commands: " + sparseArray4.size());
                }
                do {
                    nextInt = sRandom.nextInt(2147483646) + 1;
                    sparseArray = sShellCommands;
                } while (sparseArray.contains(nextInt));
                sparseArray.put(nextInt, new WeakReference(shellCommand));
            } catch (Throwable th) {
                throw th;
            }
        }
        return VibrationParam$1$$ExternalSyntheticOutline0.m(nextInt, SHELL_COMMAND_ID_PREFIX);
    }

    public static DataLoaderParams getIncrementalDataLoaderParams(ShellCommand shellCommand) {
        return DataLoaderParams.forIncremental(
                new ComponentName("android", CLASS), getDataLoaderParamsArgs(shellCommand));
    }

    public static int getLocalFile(ShellCommand shellCommand, String str) {
        ParcelFileDescriptor openFileForSystem = shellCommand.openFileForSystem(str, "r");
        if (openFileForSystem == null) {
            return -1;
        }
        return openFileForSystem.detachFd();
    }

    public static ParcelFileDescriptor getLocalFilePFD(ShellCommand shellCommand, String str) {
        return shellCommand.openFileForSystem(str, "r");
    }

    public static int getStdIn(ShellCommand shellCommand) {
        ParcelFileDescriptor stdInPFD = getStdInPFD(shellCommand);
        if (stdInPFD == null) {
            return -1;
        }
        return stdInPFD.detachFd();
    }

    public static ParcelFileDescriptor getStdInPFD(ShellCommand shellCommand) {
        try {
            return ParcelFileDescriptor.dup(shellCommand.getInFileDescriptor());
        } catch (IOException e) {
            Slog.e(TAG, "Exception while obtaining STDIN fd", e);
            return null;
        }
    }

    public static DataLoaderParams getStreamingDataLoaderParams(ShellCommand shellCommand) {
        return DataLoaderParams.forStreaming(
                new ComponentName("android", CLASS), getDataLoaderParamsArgs(shellCommand));
    }

    public static ShellCommand lookupShellCommand(String str) {
        WeakReference weakReference;
        int extractShellCommandId = extractShellCommandId(str);
        if (extractShellCommandId == -1) {
            return null;
        }
        SparseArray sparseArray = sShellCommands;
        synchronized (sparseArray) {
            weakReference = (WeakReference) sparseArray.get(extractShellCommandId, null);
        }
        if (weakReference != null) {
            return (ShellCommand) weakReference.get();
        }
        return null;
    }

    private static native void nativeInitialize();

    public final DataLoaderService.DataLoader onCreateDataLoader(
            DataLoaderParams dataLoaderParams) {
        if (dataLoaderParams.getType() != 1) {
            return null;
        }
        DataLoader dataLoader = new DataLoader();
        dataLoader.mParams = null;
        dataLoader.mConnector = null;
        return dataLoader;
    }
}
