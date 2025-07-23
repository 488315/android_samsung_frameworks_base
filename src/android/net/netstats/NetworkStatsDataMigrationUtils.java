package android.net.netstats;

import android.annotation.SystemApi;
import android.content.Context;
import android.media.MediaMetrics;
import android.net.NetworkIdentity;
import android.net.NetworkStatsCollection;
import android.net.NetworkStatsHistory;
import android.os.Environment;
import android.util.AtomicFile;
import com.android.internal.util.ArtFastDataInput;
import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import libcore.io.IoUtils;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class NetworkStatsDataMigrationUtils {
    private static final int BUFFER_SIZE = 8192;
    private static final int FILE_MAGIC = 1095648596;
    public static final String PREFIX_UID = "uid";
    public static final String PREFIX_XT = "xt";
    public static final String PREFIX_UID_TAG = "uid_tag";
    private static final Map<String, String> sPrefixLegacyFileNameMap = Map.of(PREFIX_XT, "netstats_xt.bin", "uid", "netstats_uid.bin", PREFIX_UID_TAG, "netstats_uid.bin");

    @Retention(RetentionPolicy.SOURCE)
    public @interface Prefix {
    }

    private static int getCollapsedLegacyType(int i) {
        if (i == 0 || i == 2 || i == 3 || i == 4 || i == 5 || i == 14 || i == 15) {
            return 0;
        }
        switch (i) {
            case 10:
            case 11:
            case 12:
                return 0;
            default:
                return i;
        }
    }

    private static class CollectionVersion {
        static final int VERSION_NETWORK_INIT = 1;
        static final int VERSION_UID_INIT = 1;
        static final int VERSION_UID_WITH_IDENT = 2;
        static final int VERSION_UID_WITH_SET = 4;
        static final int VERSION_UID_WITH_TAG = 3;
        static final int VERSION_UNIFIED_INIT = 16;

        private CollectionVersion() {
        }
    }

    private static class HistoryVersion {
        static final int VERSION_ADD_ACTIVE = 3;
        static final int VERSION_ADD_PACKETS = 2;
        static final int VERSION_INIT = 1;

        private HistoryVersion() {
        }
    }

    private static class IdentitySetVersion {
        static final int VERSION_ADD_DEFAULT_NETWORK = 5;
        static final int VERSION_ADD_METERED = 4;
        static final int VERSION_ADD_NETWORK_ID = 3;
        static final int VERSION_ADD_OEM_MANAGED_NETWORK = 6;
        static final int VERSION_ADD_ROAMING = 2;
        static final int VERSION_ADD_SUB_ID = 7;
        static final int VERSION_INIT = 1;

        private IdentitySetVersion() {
        }
    }

    private NetworkStatsDataMigrationUtils() {
    }

    private static File getPlatformSystemDir() {
        return new File(Environment.getDataDirectory(), "system");
    }

    private static File getPlatformBaseDir() {
        File file = new File(getPlatformSystemDir(), Context.NETWORK_STATS_SERVICE);
        file.mkdirs();
        return file;
    }

    private static File getLegacyBinFileForPrefix(String str) {
        return new File(getPlatformSystemDir(), sPrefixLegacyFileNameMap.get(str));
    }

    private static ArrayList<File> getPlatformFileListForPrefix(String str) {
        String[] list;
        ArrayList<File> arrayList = new ArrayList<>();
        File platformBaseDir = getPlatformBaseDir();
        if (platformBaseDir.exists() && (list = platformBaseDir.list()) != null) {
            Arrays.sort(list);
            for (String str2 : list) {
                if (str2.startsWith(str + MediaMetrics.SEPARATOR)) {
                    arrayList.add(new File(platformBaseDir, str2));
                }
            }
        }
        return arrayList;
    }

    public static NetworkStatsCollection readPlatformCollection(String str, long j) throws IOException {
        NetworkStatsCollection.Builder builder = new NetworkStatsCollection.Builder(j);
        str.hashCode();
        if (str.equals(PREFIX_UID_TAG) || str.equals("uid")) {
            File legacyBinFileForPrefix = getLegacyBinFileForPrefix(str);
            if (legacyBinFileForPrefix.exists()) {
                readLegacyUid(builder, legacyBinFileForPrefix, PREFIX_UID_TAG.equals(str));
            }
        }
        Iterator<File> it = getPlatformFileListForPrefix(str).iterator();
        while (it.hasNext()) {
            File next = it.next();
            if (next.exists()) {
                readPlatformCollection(builder, next);
            }
        }
        return builder.build();
    }

    private static void readPlatformCollection(NetworkStatsCollection.Builder builder, File file) throws IOException {
        ArtFastDataInput artFastDataInput = new ArtFastDataInput(new FileInputStream(file), 8192);
        try {
            readPlatformCollection(builder, artFastDataInput);
        } finally {
            IoUtils.closeQuietly(artFastDataInput);
        }
    }

    public static void readPlatformCollection(NetworkStatsCollection.Builder builder, DataInput dataInput) throws IOException {
        int readInt = dataInput.readInt();
        if (readInt != FILE_MAGIC) {
            throw new ProtocolException("unexpected magic: " + readInt);
        }
        int readInt2 = dataInput.readInt();
        if (readInt2 == 16) {
            int readInt3 = dataInput.readInt();
            for (int i = 0; i < readInt3; i++) {
                Set<NetworkIdentity> readPlatformNetworkIdentitySet = readPlatformNetworkIdentitySet(dataInput);
                int readInt4 = dataInput.readInt();
                for (int i2 = 0; i2 < readInt4; i2++) {
                    builder.addEntry(new NetworkStatsCollection.Key(readPlatformNetworkIdentitySet, dataInput.readInt(), dataInput.readInt(), dataInput.readInt()), readPlatformHistory(dataInput));
                }
            }
            return;
        }
        throw new ProtocolException("unexpected version: " + readInt2);
    }

    private static long[] readFullLongArray(DataInput dataInput) throws IOException {
        int readInt = dataInput.readInt();
        if (readInt < 0) {
            throw new ProtocolException("negative array size");
        }
        long[] jArr = new long[readInt];
        for (int i = 0; i < readInt; i++) {
            jArr[i] = dataInput.readLong();
        }
        return jArr;
    }

    private static long[] readVarLongArray(DataInput dataInput) throws IOException {
        int readInt = dataInput.readInt();
        if (readInt == -1) {
            return null;
        }
        if (readInt < 0) {
            throw new ProtocolException("negative array size");
        }
        long[] jArr = new long[readInt];
        for (int i = 0; i < readInt; i++) {
            jArr[i] = readVarLong(dataInput);
        }
        return jArr;
    }

    private static long readVarLong(DataInput dataInput) throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            j |= (r3 & Byte.MAX_VALUE) << i;
            if ((dataInput.readByte() & 128) == 0) {
                return j;
            }
        }
        throw new ProtocolException("malformed var long");
    }

    private static String readOptionalString(DataInput dataInput) throws IOException {
        if (dataInput.readByte() != 0) {
            return dataInput.readUTF();
        }
        return null;
    }

    private static NetworkStatsHistory readPlatformHistory(DataInput dataInput) throws IOException {
        long readLong;
        long[] readFullLongArray;
        long[] jArr;
        long[] readFullLongArray2;
        long[] jArr2;
        long[] jArr3;
        int length;
        long[] jArr4;
        long[] jArr5;
        long[] jArr6 = new long[0];
        int readInt = dataInput.readInt();
        if (readInt == 1) {
            readLong = dataInput.readLong();
            long[] readFullLongArray3 = readFullLongArray(dataInput);
            readFullLongArray = readFullLongArray(dataInput);
            jArr = new long[readFullLongArray3.length];
            readFullLongArray2 = readFullLongArray(dataInput);
            jArr2 = new long[readFullLongArray3.length];
            jArr3 = new long[readFullLongArray3.length];
            length = readFullLongArray3.length;
            jArr4 = jArr6;
            jArr5 = readFullLongArray3;
        } else if (readInt == 2 || readInt == 3) {
            readLong = dataInput.readLong();
            jArr5 = readVarLongArray(dataInput);
            if (readInt >= 3) {
                jArr4 = readVarLongArray(dataInput);
            } else {
                jArr4 = new long[jArr5.length];
            }
            readFullLongArray = readVarLongArray(dataInput);
            jArr = readVarLongArray(dataInput);
            readFullLongArray2 = readVarLongArray(dataInput);
            jArr2 = readVarLongArray(dataInput);
            jArr3 = readVarLongArray(dataInput);
            length = jArr5.length;
        } else {
            throw new ProtocolException("unexpected version: " + readInt);
        }
        NetworkStatsHistory.Builder builder = new NetworkStatsHistory.Builder(readLong, length);
        for (int i = 0; i < length; i++) {
            builder.addEntry(new NetworkStatsHistory.Entry(jArr5[i], jArr4[i], readFullLongArray[i], jArr[i], readFullLongArray2[i], jArr2[i], jArr3[i]));
        }
        return builder.build();
    }

    private static Set<NetworkIdentity> readPlatformNetworkIdentitySet(DataInput dataInput) throws IOException {
        boolean z;
        int readInt = dataInput.readInt();
        int readInt2 = dataInput.readInt();
        HashSet hashSet = new HashSet();
        for (int i = 0; i < readInt2; i++) {
            if (readInt <= 1) {
                dataInput.readInt();
            }
            int readInt3 = dataInput.readInt();
            int readInt4 = dataInput.readInt();
            String readOptionalString = readOptionalString(dataInput);
            String readOptionalString2 = readInt >= 3 ? readOptionalString(dataInput) : null;
            boolean readBoolean = readInt >= 2 ? dataInput.readBoolean() : false;
            if (readInt >= 4) {
                z = dataInput.readBoolean();
            } else {
                z = readInt3 == 0;
            }
            boolean readBoolean2 = readInt >= 5 ? dataInput.readBoolean() : true;
            NetworkIdentity.Builder subId = new NetworkIdentity.Builder().setType(getCollapsedLegacyType(readInt3)).setSubscriberId(readOptionalString).setWifiNetworkKey(readOptionalString2).setRoaming(readBoolean).setMetered(z).setDefaultNetwork(readBoolean2).setOemManaged(readInt >= 6 ? dataInput.readInt() : 0).setSubId(readInt >= 7 ? dataInput.readInt() : -1);
            if (readInt3 == 0 && readInt4 != -1) {
                subId.setRatType(readInt4);
            }
            hashSet.add(subId.build());
        }
        return hashSet;
    }

    private static void readLegacyUid(NetworkStatsCollection.Builder builder, File file, boolean z) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new AtomicFile(file).openRead()));
        try {
            readLegacyUid(builder, dataInputStream, z);
        } finally {
            IoUtils.closeQuietly(dataInputStream);
        }
    }

    public static void readLegacyUid(NetworkStatsCollection.Builder builder, DataInput dataInput, boolean z) throws IOException {
        try {
            int readInt = dataInput.readInt();
            if (readInt != FILE_MAGIC) {
                throw new ProtocolException("unexpected magic: " + readInt);
            }
            int readInt2 = dataInput.readInt();
            if (readInt2 == 1 || readInt2 == 2) {
                return;
            }
            if (readInt2 != 3 && readInt2 != 4) {
                throw new ProtocolException("unknown version: " + readInt2);
            }
            int readInt3 = dataInput.readInt();
            for (int i = 0; i < readInt3; i++) {
                Set<NetworkIdentity> readPlatformNetworkIdentitySet = readPlatformNetworkIdentitySet(dataInput);
                int readInt4 = dataInput.readInt();
                for (int i2 = 0; i2 < readInt4; i2++) {
                    int readInt5 = dataInput.readInt();
                    int readInt6 = readInt2 >= 4 ? dataInput.readInt() : 0;
                    int readInt7 = dataInput.readInt();
                    NetworkStatsCollection.Key key = new NetworkStatsCollection.Key(readPlatformNetworkIdentitySet, readInt5, readInt6, readInt7);
                    NetworkStatsHistory readPlatformHistory = readPlatformHistory(dataInput);
                    if ((readInt7 == 0) != z) {
                        builder.addEntry(key, readPlatformHistory);
                    }
                }
            }
        } catch (FileNotFoundException | ProtocolException unused) {
        }
    }
}
