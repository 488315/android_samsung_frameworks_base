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
        int i = dataInput.readInt();
        if (i != FILE_MAGIC) {
            throw new ProtocolException("unexpected magic: " + i);
        }
        int i2 = dataInput.readInt();
        if (i2 == 16) {
            int i3 = dataInput.readInt();
            for (int i4 = 0; i4 < i3; i4++) {
                Set<NetworkIdentity> platformNetworkIdentitySet = readPlatformNetworkIdentitySet(dataInput);
                int i5 = dataInput.readInt();
                for (int i6 = 0; i6 < i5; i6++) {
                    builder.addEntry(new NetworkStatsCollection.Key(platformNetworkIdentitySet, dataInput.readInt(), dataInput.readInt(), dataInput.readInt()), readPlatformHistory(dataInput));
                }
            }
            return;
        }
        throw new ProtocolException("unexpected version: " + i2);
    }

    private static long[] readFullLongArray(DataInput dataInput) throws IOException {
        int i = dataInput.readInt();
        if (i < 0) {
            throw new ProtocolException("negative array size");
        }
        long[] jArr = new long[i];
        for (int i2 = 0; i2 < i; i2++) {
            jArr[i2] = dataInput.readLong();
        }
        return jArr;
    }

    private static long[] readVarLongArray(DataInput dataInput) throws IOException {
        int i = dataInput.readInt();
        if (i == -1) {
            return null;
        }
        if (i < 0) {
            throw new ProtocolException("negative array size");
        }
        long[] jArr = new long[i];
        for (int i2 = 0; i2 < i; i2++) {
            jArr[i2] = readVarLong(dataInput);
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
        long j;
        long[] fullLongArray;
        long[] varLongArray;
        long[] fullLongArray2;
        long[] varLongArray2;
        long[] varLongArray3;
        int length;
        long[] varLongArray4;
        long[] varLongArray5;
        long[] jArr = new long[0];
        int i = dataInput.readInt();
        if (i == 1) {
            j = dataInput.readLong();
            long[] fullLongArray3 = readFullLongArray(dataInput);
            fullLongArray = readFullLongArray(dataInput);
            varLongArray = new long[fullLongArray3.length];
            fullLongArray2 = readFullLongArray(dataInput);
            varLongArray2 = new long[fullLongArray3.length];
            varLongArray3 = new long[fullLongArray3.length];
            length = fullLongArray3.length;
            varLongArray4 = jArr;
            varLongArray5 = fullLongArray3;
        } else if (i == 2 || i == 3) {
            j = dataInput.readLong();
            varLongArray5 = readVarLongArray(dataInput);
            if (i >= 3) {
                varLongArray4 = readVarLongArray(dataInput);
            } else {
                varLongArray4 = new long[varLongArray5.length];
            }
            fullLongArray = readVarLongArray(dataInput);
            varLongArray = readVarLongArray(dataInput);
            fullLongArray2 = readVarLongArray(dataInput);
            varLongArray2 = readVarLongArray(dataInput);
            varLongArray3 = readVarLongArray(dataInput);
            length = varLongArray5.length;
        } else {
            throw new ProtocolException("unexpected version: " + i);
        }
        NetworkStatsHistory.Builder builder = new NetworkStatsHistory.Builder(j, length);
        for (int i2 = 0; i2 < length; i2++) {
            builder.addEntry(new NetworkStatsHistory.Entry(varLongArray5[i2], varLongArray4[i2], fullLongArray[i2], varLongArray[i2], fullLongArray2[i2], varLongArray2[i2], varLongArray3[i2]));
        }
        return builder.build();
    }

    private static Set<NetworkIdentity> readPlatformNetworkIdentitySet(DataInput dataInput) throws IOException {
        boolean z;
        int i = dataInput.readInt();
        int i2 = dataInput.readInt();
        HashSet hashSet = new HashSet();
        for (int i3 = 0; i3 < i2; i3++) {
            if (i <= 1) {
                dataInput.readInt();
            }
            int i4 = dataInput.readInt();
            int i5 = dataInput.readInt();
            String optionalString = readOptionalString(dataInput);
            String optionalString2 = i >= 3 ? readOptionalString(dataInput) : null;
            boolean z2 = i >= 2 ? dataInput.readBoolean() : false;
            if (i >= 4) {
                z = dataInput.readBoolean();
            } else {
                z = i4 == 0;
            }
            boolean z3 = i >= 5 ? dataInput.readBoolean() : true;
            NetworkIdentity.Builder subId = new NetworkIdentity.Builder().setType(getCollapsedLegacyType(i4)).setSubscriberId(optionalString).setWifiNetworkKey(optionalString2).setRoaming(z2).setMetered(z).setDefaultNetwork(z3).setOemManaged(i >= 6 ? dataInput.readInt() : 0).setSubId(i >= 7 ? dataInput.readInt() : -1);
            if (i4 == 0 && i5 != -1) {
                subId.setRatType(i5);
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
            int i = dataInput.readInt();
            if (i != FILE_MAGIC) {
                throw new ProtocolException("unexpected magic: " + i);
            }
            int i2 = dataInput.readInt();
            if (i2 == 1 || i2 == 2) {
                return;
            }
            if (i2 != 3 && i2 != 4) {
                throw new ProtocolException("unknown version: " + i2);
            }
            int i3 = dataInput.readInt();
            for (int i4 = 0; i4 < i3; i4++) {
                Set<NetworkIdentity> platformNetworkIdentitySet = readPlatformNetworkIdentitySet(dataInput);
                int i5 = dataInput.readInt();
                for (int i6 = 0; i6 < i5; i6++) {
                    int i7 = dataInput.readInt();
                    int i8 = i2 >= 4 ? dataInput.readInt() : 0;
                    int i9 = dataInput.readInt();
                    NetworkStatsCollection.Key key = new NetworkStatsCollection.Key(platformNetworkIdentitySet, i7, i8, i9);
                    NetworkStatsHistory platformHistory = readPlatformHistory(dataInput);
                    if ((i9 == 0) != z) {
                        builder.addEntry(key, platformHistory);
                    }
                }
            }
        } catch (FileNotFoundException | ProtocolException unused) {
        }
    }
}
