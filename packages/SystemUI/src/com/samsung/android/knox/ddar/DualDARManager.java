package com.samsung.android.knox.ddar;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;
import com.samsung.android.knox.dar.ddar.securesession.SecureClient;
import com.samsung.android.knox.dar.ddar.securesession.Wiper;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class DualDARManager {
    private static final boolean DEBUG = "eng".equals(Build.TYPE);
    public static final String DUALDAR_AGENT = "KNOXCORE_PROXY_AGENT";
    public static final String DUALDAR_MGR_SERVICE = "DUALDAR_MGR_SERVICE";
    private static final String DUAL_DAR_CLIENT = "DUAL_DAR_CLIENT";
    public static final String FETCH_DUMPSTATE_REQUEST = "FETCH_DUMPSTATE_REQUEST";
    public static final String GET_CLIENT_VERSION_REQUEST = "GET_CLIENT_VERSION_REQUEST";
    public static final String GET_DUALDAR_USERS_REQUEST = "GET_DUALDAR_USERS_REQUEST";
    public static final String INSTALL_CLIENT_LIBRARY_REQUEST = "INSTALL_CLIENT_LIBRARY_REQUEST";
    private static final int LOAD_RETRY_COUNT = 5;
    public static final String ON_AGENT_RECONNECTED = "ON_AGENT_RECONNECTED";
    public static final String PUSH_SECRET_REQUEST = "PUSH_SECRET_REQUEST";
    private static final String TAG = "DualDarManager";
    private static DualDARManager mInstance;
    private Context mContext;
    private SecureClient mSecureClientOutAPI;

    private DualDARManager(Context context) {
        this.mContext = context;
    }

    private synchronized boolean fetchDumpState(String str) {
        ParcelFileDescriptor parcelFileDescriptor;
        ParcelFileDescriptor parcelFileDescriptor2;
        FileInfo fileInfo = null;
        try {
            try {
                FileInfo fdFromPathForWrite = getFdFromPathForWrite(str);
                if (fdFromPathForWrite == null || fdFromPathForWrite.fd == null) {
                    Log.e(TAG, "Error: Not able to open the Log files");
                    if (fdFromPathForWrite != null && (parcelFileDescriptor2 = fdFromPathForWrite.fd) != null) {
                        try {
                            parcelFileDescriptor2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return false;
                }
                boolean z = DEBUG;
                if (z) {
                    Log.d(TAG, "FS Log File fd=" + fdFromPathForWrite.fd.getFd());
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("FSLOG_FILE_INFO", fdFromPathForWrite);
                Bundle bundleProcessCommand = processCommand(FETCH_DUMPSTATE_REQUEST, bundle);
                if (bundleProcessCommand == null || !bundleProcessCommand.getBoolean("dual_dar_response", true)) {
                    Log.e(TAG, "Fetch DumpState failed !!");
                    ParcelFileDescriptor parcelFileDescriptor3 = fdFromPathForWrite.fd;
                    if (parcelFileDescriptor3 != null) {
                        try {
                            parcelFileDescriptor3.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    return false;
                }
                if (z) {
                    Log.d(TAG, "Fetch DumpState Success");
                }
                ParcelFileDescriptor parcelFileDescriptor4 = fdFromPathForWrite.fd;
                if (parcelFileDescriptor4 != null) {
                    try {
                        parcelFileDescriptor4.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return true;
            } catch (Exception e4) {
                Log.e(TAG, "Exception at fetchDumpState - " + e4.getMessage());
                e4.printStackTrace();
                if (0 != 0 && (parcelFileDescriptor = fileInfo.fd) != null) {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                return false;
            }
        } finally {
        }
    }

    private FileInfo getFdFromAsset(String str) throws IOException {
        String str2;
        AssetManager assets = this.mContext.getAssets();
        if (assets == null) {
            return null;
        }
        try {
            try {
                boolean z = DEBUG;
                if (z) {
                    Log.d(TAG, "FileName: " + str);
                }
                AssetFileDescriptor assetFileDescriptorOpenFd = assets.openFd(str);
                if (z) {
                    Log.d(TAG, "Found FSRelay file: " + str);
                }
                if (assetFileDescriptorOpenFd == null) {
                    return null;
                }
                str2 = str;
                try {
                    return new FileInfo(str2, assetFileDescriptorOpenFd.getParcelFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
                } catch (FileNotFoundException unused) {
                    Log.e(TAG, "FSRelay file not found: " + str2);
                    return null;
                }
            } catch (FileNotFoundException unused2) {
                str2 = str;
            }
        } catch (Exception e) {
            Log.e(TAG, "general exception");
            e.printStackTrace();
            return null;
        }
    }

    private FileInfo getFdFromPath(String str) {
        if (str != null && !str.isEmpty()) {
            File file = new File(str);
            try {
                return new FileInfo(str.substring(str.lastIndexOf(47) + 1), ParcelFileDescriptor.open(file, 268435456), 0L, file.length());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private FileInfo getFdFromPathForWrite(String str) {
        if (str != null && !str.isEmpty()) {
            File file = new File(str);
            try {
                return new FileInfo(str.substring(str.lastIndexOf(47) + 1), ParcelFileDescriptor.open(file, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), 0L, file.length());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static synchronized DualDARManager getInstance(Context context) {
        try {
            if (mInstance == null) {
                mInstance = new DualDARManager(context);
            }
        } catch (Throwable th) {
            throw th;
        }
        return mInstance;
    }

    /* JADX WARN: Removed duplicated region for block: B:165:0x01ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0180 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0034 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x01b3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x022e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0148 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:207:? A[Catch: all -> 0x0027, DONT_GENERATE, FINALLY_INSNS, SYNTHETIC, TryCatch #4 {, blocks: (B:4:0x0003, B:15:0x001f, B:17:0x0023, B:22:0x002b, B:23:0x002e, B:25:0x0034, B:27:0x003b, B:30:0x004a, B:73:0x0136, B:75:0x013a, B:79:0x0142, B:81:0x0148, B:83:0x014f, B:86:0x015e, B:78:0x013f, B:92:0x016e, B:94:0x0172, B:98:0x017a, B:100:0x0180, B:102:0x0186, B:105:0x0195, B:97:0x0177, B:109:0x01a1, B:111:0x01a5, B:115:0x01ad, B:117:0x01b3, B:119:0x01ba, B:122:0x01c9, B:114:0x01aa, B:144:0x021c, B:146:0x0220, B:149:0x0225, B:150:0x0228, B:152:0x022e, B:154:0x0234, B:158:0x0246, B:157:0x0243, B:127:0x01ed, B:129:0x01f1, B:132:0x01f6, B:133:0x01f9, B:135:0x01ff, B:137:0x0206, B:140:0x0215, B:7:0x000c, B:33:0x004f, B:35:0x0053, B:37:0x005b, B:40:0x0063, B:42:0x0067, B:44:0x0094, B:46:0x009a, B:47:0x009e, B:49:0x00a4, B:51:0x00ac, B:52:0x00b4, B:53:0x00bc, B:55:0x00c0, B:59:0x00e1, B:61:0x00f1, B:62:0x00fe, B:64:0x0106, B:70:0x0114, B:72:0x012f, B:89:0x0163, B:91:0x0167, B:108:0x019a, B:12:0x0017, B:125:0x01ce), top: B:169:0x0003, inners: #0, #1, #2, #3, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized boolean installLibraryInternal(String str, List<String> list, boolean z) {
        ParcelFileDescriptor parcelFileDescriptor;
        ParcelFileDescriptor parcelFileDescriptor2;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        FileInfo fileInfo = null;
        try {
            try {
                FileInfo fdFromAsset = z ? getFdFromAsset(str) : getFdFromPath(str);
                if (fdFromAsset == null) {
                    if (fdFromAsset != null && (parcelFileDescriptor2 = fdFromAsset.fd) != null) {
                        try {
                            parcelFileDescriptor2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!arrayList.isEmpty()) {
                        }
                        return false;
                    }
                    if (!arrayList.isEmpty()) {
                        try {
                            int size = arrayList.size();
                            int i2 = 0;
                            while (i2 < size) {
                                Object obj = arrayList.get(i2);
                                i2++;
                                ((FileInfo) obj).fd.close();
                            }
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    return false;
                }
                if (fdFromAsset.fd == null || fdFromAsset.offset < 0 || fdFromAsset.len < 0) {
                    Log.e(TAG, "pfd is null");
                    ParcelFileDescriptor parcelFileDescriptor3 = fdFromAsset.fd;
                    if (parcelFileDescriptor3 == null) {
                        if (!arrayList.isEmpty()) {
                        }
                        return false;
                    }
                    try {
                        parcelFileDescriptor3.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    if (!arrayList.isEmpty()) {
                        try {
                            int size2 = arrayList.size();
                            int i3 = 0;
                            while (i3 < size2) {
                                Object obj2 = arrayList.get(i3);
                                i3++;
                                ((FileInfo) obj2).fd.close();
                            }
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    return false;
                }
                if (DEBUG) {
                    Log.d(TAG, "FSRelay fd=" + fdFromAsset.fd.getFd() + " offset=" + fdFromAsset.offset + " len=" + fdFromAsset.len);
                }
                if (list != null && !list.isEmpty()) {
                    for (String str2 : list) {
                        if (z) {
                            arrayList.add(getFdFromAsset(str2));
                        } else {
                            arrayList.add(getFdFromPath(str2));
                        }
                    }
                }
                if (DEBUG) {
                    Log.d(TAG, "load FSRelay " + str + " from app");
                }
                boolean z2 = false;
                for (int i4 = 0; i4 < 5; i4++) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("RELAY_FILE_INFO", fdFromAsset);
                    if (!arrayList.isEmpty()) {
                        bundle.putParcelableArray("CRYPTO_FILE_INFO", (Parcelable[]) arrayList.toArray(new FileInfo[0]));
                    }
                    Bundle bundleProcessCommand = processCommand(INSTALL_CLIENT_LIBRARY_REQUEST, bundle);
                    z2 = bundleProcessCommand != null && bundleProcessCommand.getBoolean("dual_dar_response", true);
                    if (z2) {
                        break;
                    }
                    Log.e(TAG, "FSRelay loading failure: " + i4);
                }
                if (z2) {
                    if (DEBUG) {
                        Log.d(TAG, "FSRelay Loaded Successfully");
                    }
                    ParcelFileDescriptor parcelFileDescriptor4 = fdFromAsset.fd;
                    if (parcelFileDescriptor4 == null) {
                        if (!arrayList.isEmpty()) {
                        }
                        return true;
                    }
                    try {
                        parcelFileDescriptor4.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                    if (!arrayList.isEmpty()) {
                        try {
                            int size3 = arrayList.size();
                            while (i < size3) {
                                Object obj3 = arrayList.get(i);
                                i++;
                                ((FileInfo) obj3).fd.close();
                            }
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    return true;
                }
                Log.e(TAG, "FSRelay Load failed !!");
                ParcelFileDescriptor parcelFileDescriptor5 = fdFromAsset.fd;
                if (parcelFileDescriptor5 == null) {
                    if (!arrayList.isEmpty()) {
                    }
                    return false;
                }
                try {
                    parcelFileDescriptor5.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
                if (!arrayList.isEmpty()) {
                    try {
                        int size4 = arrayList.size();
                        int i5 = 0;
                        while (i5 < size4) {
                            Object obj4 = arrayList.get(i5);
                            i5++;
                            ((FileInfo) obj4).fd.close();
                        }
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                return false;
            } catch (Exception e9) {
                Log.e(TAG, "Exception at installLibrary - " + e9.getMessage());
                e9.printStackTrace();
                if (0 != 0 && (parcelFileDescriptor = fileInfo.fd) != null) {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e10) {
                        e10.printStackTrace();
                    }
                    if (!arrayList.isEmpty()) {
                    }
                    return false;
                }
                if (!arrayList.isEmpty()) {
                    try {
                        int size5 = arrayList.size();
                        int i6 = 0;
                        while (i6 < size5) {
                            Object obj5 = arrayList.get(i6);
                            i6++;
                            ((FileInfo) obj5).fd.close();
                        }
                    } catch (IOException e11) {
                        e11.printStackTrace();
                    }
                }
                return false;
            }
        } finally {
        }
    }

    private Bundle processCommand(String str, Bundle bundle) {
        return KnoxProxyManager.getInstance(this.mContext).relayMessage(DUALDAR_AGENT, DUALDAR_MGR_SERVICE, str, bundle);
    }

    private Bundle processCommandSecurely(String str, Bundle bundle) {
        return KnoxProxyManager.getInstance(this.mContext).relayMessageSecurely(DUALDAR_AGENT, DUALDAR_MGR_SERVICE, str, bundle, this.mSecureClientOutAPI);
    }

    public IBinder bindClient(IDualDARClient iDualDARClient) {
        return DualDarClientManager.getInstance(this.mContext, iDualDARClient);
    }

    public void establishSecureSession() {
        try {
            this.mSecureClientOutAPI = KnoxProxyManager.getInstance(this.mContext).initializeSecureSession(DUAL_DAR_CLIENT, DUALDAR_AGENT, DUALDAR_MGR_SERVICE);
        } catch (Exception e) {
            Log.e(TAG, "Failed to establish secure connection from SDK to KnoxCore");
            e.printStackTrace();
        }
    }

    public synchronized List<Integer> getDualDARUsers() {
        Bundle bundleProcessCommand = processCommand(GET_DUALDAR_USERS_REQUEST, null);
        if (bundleProcessCommand == null) {
            Log.e(TAG, "Failed to get service");
            return null;
        }
        return bundleProcessCommand.getIntegerArrayList("USERS");
    }

    public synchronized boolean getFileSystemLog(String str) {
        return fetchDumpState(str);
    }

    public String getInstalledClientLibraryVersion() {
        Bundle bundleProcessCommand = processCommand(GET_CLIENT_VERSION_REQUEST, null);
        if (bundleProcessCommand != null) {
            return bundleProcessCommand.getString("CLIENT_VERSION");
        }
        Log.e(TAG, "Failed to get service");
        return null;
    }

    public synchronized boolean installLibrary(String str, List<String> list, boolean z) {
        return installLibraryInternal(str, list, z);
    }

    public void onAgentReconnected() {
        processCommand(ON_AGENT_RECONNECTED, null);
    }

    public synchronized void setSecret(int i, List<Secret> list) {
        byte[] bArrEncryptMessageFor;
        Log.d(TAG, "setSecret() ");
        try {
            Bundle bundle = new Bundle();
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            for (Secret secret : list) {
                try {
                    bArrEncryptMessageFor = this.mSecureClientOutAPI.encryptMessageFor(DUALDAR_MGR_SERVICE, secret.data);
                } catch (Exception e) {
                    Log.e(TAG, "PUSH_SECRET_REQUEST failed to encrypt secrets");
                    e.printStackTrace();
                    bArrEncryptMessageFor = null;
                }
                Wiper.wipe(secret.data);
                arrayList.add(new Secret(secret.alias, bArrEncryptMessageFor));
            }
            list.clear();
            bundle.putParcelableArrayList("INNER_LAYER_SECRET", arrayList);
            bundle.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i);
            Bundle bundleProcessCommandSecurely = processCommandSecurely(PUSH_SECRET_REQUEST, bundle);
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Parcelable parcelable = arrayList.get(i2);
                i2++;
                Wiper.wipe(((Secret) parcelable).data);
            }
            arrayList.clear();
            Log.d(TAG, "PUSH_SECRET_REQUEST response: " + (bundleProcessCommandSecurely != null ? bundleProcessCommandSecurely.getBoolean("dual_dar_response", true) : false));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void teardownSecureSession() {
        try {
            KnoxProxyManager.getInstance(this.mContext).terminateSecureSession(this.mSecureClientOutAPI, DUALDAR_AGENT, DUALDAR_MGR_SERVICE);
            this.mSecureClientOutAPI = null;
        } catch (Exception e) {
            Log.e(TAG, "Failed to teardown secure connection from SDK to KnoxCore");
            e.printStackTrace();
        }
    }
}
