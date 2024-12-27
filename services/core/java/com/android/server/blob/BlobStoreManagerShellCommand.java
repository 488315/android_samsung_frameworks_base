package com.android.server.blob;

import android.app.ActivityManager;
import android.app.blob.BlobHandle;
import android.os.ShellCommand;
import android.util.LongSparseArray;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.Base64;

public final class BlobStoreManagerShellCommand extends ShellCommand {
    public final BlobStoreManagerService mService;

    public final class ParsedArgs {
        public long blobId;
        public byte[] digest;
        public long expiryTimeMillis;
        public CharSequence label;
        public String tag;
        public int userId = -2;
        public String algorithm = "SHA-256";
    }

    public BlobStoreManagerShellCommand(BlobStoreManagerService blobStoreManagerService) {
        this.mService = blobStoreManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    public final int onCommand(String str) {
        char c;
        int i;
        if (str == null) {
            return handleDefaultCommands((String) null);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        switch (str.hashCode()) {
            case -1168531841:
                if (str.equals("delete-blob")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -971115831:
                if (str.equals("clear-all-sessions")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -258166326:
                if (str.equals("clear-all-blobs")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 712607671:
                if (str.equals("query-blob-existence")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1861559962:
                if (str.equals("idle-maintenance")) {
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
                ParsedArgs parsedArgs = new ParsedArgs();
                if (parseOptions(outPrintWriter, parsedArgs) < 0) {
                    return -1;
                }
                BlobStoreManagerService blobStoreManagerService = this.mService;
                BlobHandle create =
                        BlobHandle.create(
                                parsedArgs.algorithm,
                                parsedArgs.digest,
                                parsedArgs.label,
                                parsedArgs.expiryTimeMillis,
                                parsedArgs.tag);
                int i2 = parsedArgs.userId;
                synchronized (blobStoreManagerService.mBlobsLock) {
                    try {
                        BlobMetadata blobMetadata =
                                (BlobMetadata) blobStoreManagerService.mBlobsMap.get(create);
                        if (blobMetadata != null) {
                            blobMetadata.removeDataForUser(i2);
                            if (blobMetadata.shouldBeDeleted(false)) {
                                blobStoreManagerService.deleteBlobLocked(blobMetadata);
                                blobStoreManagerService.mBlobsMap.remove(create);
                            }
                            blobStoreManagerService.writeBlobsInfoAsync();
                        }
                    } finally {
                    }
                }
                return 0;
            case 1:
                ParsedArgs parsedArgs2 = new ParsedArgs();
                parsedArgs2.userId = -1;
                if (parseOptions(outPrintWriter, parsedArgs2) < 0) {
                    return -1;
                }
                BlobStoreManagerService blobStoreManagerService2 = this.mService;
                int i3 = parsedArgs2.userId;
                synchronized (blobStoreManagerService2.mBlobsLock) {
                    try {
                        int size = blobStoreManagerService2.mSessions.size();
                        for (int i4 = 0; i4 < size; i4++) {
                            int keyAt = blobStoreManagerService2.mSessions.keyAt(i4);
                            if (i3 == -1 || i3 == keyAt) {
                                LongSparseArray longSparseArray =
                                        (LongSparseArray)
                                                blobStoreManagerService2.mSessions.valueAt(i4);
                                int size2 = longSparseArray.size();
                                for (int i5 = 0; i5 < size2; i5++) {
                                    blobStoreManagerService2.mActiveBlobIds.remove(
                                            Long.valueOf(
                                                    ((BlobStoreSession) longSparseArray.valueAt(i5))
                                                            .mSessionId));
                                }
                            }
                        }
                        if (i3 == -1) {
                            blobStoreManagerService2.mSessions.clear();
                        } else {
                            blobStoreManagerService2.mSessions.remove(i3);
                        }
                        blobStoreManagerService2.writeBlobSessionsAsync();
                    } finally {
                    }
                }
                return 0;
            case 2:
                ParsedArgs parsedArgs3 = new ParsedArgs();
                parsedArgs3.userId = -1;
                if (parseOptions(outPrintWriter, parsedArgs3) < 0) {
                    return -1;
                }
                BlobStoreManagerService blobStoreManagerService3 = this.mService;
                int i6 = parsedArgs3.userId;
                synchronized (blobStoreManagerService3.mBlobsLock) {
                    blobStoreManagerService3
                            .mBlobsMap
                            .entrySet()
                            .removeIf(
                                    new BlobStoreManagerService$$ExternalSyntheticLambda10(
                                            blobStoreManagerService3, i6, 0));
                    blobStoreManagerService3.writeBlobsInfoAsync();
                }
                return 0;
            case 3:
                ParsedArgs parsedArgs4 = new ParsedArgs();
                if (parseOptions(outPrintWriter, parsedArgs4) < 0) {
                    return -1;
                }
                BlobStoreManagerService blobStoreManagerService4 = this.mService;
                long j = parsedArgs4.blobId;
                int i7 = parsedArgs4.userId;
                synchronized (blobStoreManagerService4.mBlobsLock) {
                    try {
                        int size3 = blobStoreManagerService4.mBlobsMap.size();
                        int i8 = 0;
                        while (true) {
                            if (i8 < size3) {
                                BlobMetadata blobMetadata2 =
                                        (BlobMetadata)
                                                blobStoreManagerService4.mBlobsMap.valueAt(i8);
                                if (blobMetadata2.mBlobId != j) {
                                    i8++;
                                } else {
                                    boolean hasACommitterInUser =
                                            blobMetadata2.hasACommitterInUser(i7);
                                }
                            } else {
                                i = 0;
                            }
                        }
                    } finally {
                    }
                }
                outPrintWriter.println(i);
                return 0;
            case 4:
                BlobStoreManagerService blobStoreManagerService5 = this.mService;
                synchronized (blobStoreManagerService5.mBlobsLock) {
                    blobStoreManagerService5.handleIdleMaintenanceLocked();
                }
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("BlobStore service (blob_store) commands:");
        outPrintWriter.println("help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("clear-all-sessions [-u | --user USER_ID]");
        outPrintWriter.println("    Remove all sessions.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's sessions to be removed.");
        outPrintWriter.println(
                "                    If not specified, sessions in all users are removed.");
        outPrintWriter.println();
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "clear-all-blobs [-u | --user USER_ID]",
                "    Remove all blobs.",
                "    Options:",
                "      -u or --user: specify which user's blobs to be removed.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "                    If not specified, blobs in all users are removed.",
                "delete-blob [-u | --user USER_ID] [--digest DIGEST] [--expiry EXPIRY_TIME]"
                    + " [--label LABEL] [--tag TAG]",
                "    Delete a blob.",
                "    Options:");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      -u or --user: specify which user's blobs to be removed;",
                "                    If not specified, blobs in all users are removed.",
                "      --digest: Base64 encoded digest of the blob to delete.",
                "      --expiry: Expiry time of the blob to delete, in milliseconds.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      --label: Label of the blob to delete.",
                "      --tag: Tag of the blob to delete.",
                "idle-maintenance",
                "    Run idle maintenance which takes care of removing stale data.");
        outPrintWriter.println("query-blob-existence [-b BLOB_ID] [-u | --user USER_ID]");
        outPrintWriter.println("    Prints 1 if blob exists, otherwise 0.");
        outPrintWriter.println();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int parseOptions(PrintWriter printWriter, ParsedArgs parsedArgs) {
        char c;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (parsedArgs.userId == -2) {
                    parsedArgs.userId = ActivityManager.getCurrentUser();
                }
                return 0;
            }
            switch (nextOption.hashCode()) {
                case -1620968108:
                    if (nextOption.equals("--label")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1493:
                    if (nextOption.equals("-b")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1512:
                    if (nextOption.equals("-u")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 43013626:
                    if (nextOption.equals("--tag")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1068100452:
                    if (nextOption.equals("--digest")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1110854355:
                    if (nextOption.equals("--expiry")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1332867059:
                    if (nextOption.equals("--algo")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1333469547:
                    if (nextOption.equals("--user")) {
                        c = 7;
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
                    parsedArgs.label = getNextArgRequired();
                    break;
                case 1:
                    parsedArgs.blobId = Long.parseLong(getNextArgRequired());
                    break;
                case 2:
                case 7:
                    parsedArgs.userId = Integer.parseInt(getNextArgRequired());
                    break;
                case 3:
                    parsedArgs.tag = getNextArgRequired();
                    break;
                case 4:
                    parsedArgs.digest = Base64.getDecoder().decode(getNextArgRequired());
                    break;
                case 5:
                    parsedArgs.expiryTimeMillis = Long.parseLong(getNextArgRequired());
                    break;
                case 6:
                    parsedArgs.algorithm = getNextArgRequired();
                    break;
                default:
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            printWriter, "Error: unknown option '", nextOption, "'");
                    return -1;
            }
        }
    }
}
