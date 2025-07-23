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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                if (fdFromPathForWrite != null && fdFromPathForWrite.fd != null) {
                    boolean z = DEBUG;
                    if (z) {
                        Log.d(TAG, "FS Log File fd=" + fdFromPathForWrite.fd.getFd());
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("FSLOG_FILE_INFO", fdFromPathForWrite);
                    Bundle processCommand = processCommand(FETCH_DUMPSTATE_REQUEST, bundle);
                    if (processCommand == null || !processCommand.getBoolean("dual_dar_response", true)) {
                        Log.e(TAG, "Fetch DumpState failed !!");
                        ParcelFileDescriptor parcelFileDescriptor3 = fdFromPathForWrite.fd;
                        if (parcelFileDescriptor3 != null) {
                            try {
                                parcelFileDescriptor3.close();
                            } catch (IOException e) {
                                e.printStackTrace();
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
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    return true;
                }
                Log.e(TAG, "Error: Not able to open the Log files");
                if (fdFromPathForWrite != null && (parcelFileDescriptor2 = fdFromPathForWrite.fd) != null) {
                    try {
                        parcelFileDescriptor2.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return false;
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

    private FileInfo getFdFromAsset(String str) {
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
                AssetFileDescriptor openFd = assets.openFd(str);
                if (z) {
                    Log.d(TAG, "Found FSRelay file: " + str);
                }
                if (openFd == null) {
                    return null;
                }
                str2 = str;
                try {
                    return new FileInfo(str2, openFd.getParcelFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                } catch (FileNotFoundException unused) {
                    Log.e(TAG, "FSRelay file not found: " + str2);
                    return null;
                }
            } catch (Exception e) {
                Log.e(TAG, "general exception");
                e.printStackTrace();
                return null;
            }
        } catch (FileNotFoundException unused2) {
            str2 = str;
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
        DualDARManager dualDARManager;
        synchronized (DualDARManager.class) {
            try {
                if (mInstance == null) {
                    mInstance = new DualDARManager(context);
                }
                dualDARManager = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return dualDARManager;
    }

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
                if (fdFromAsset.fd != null && fdFromAsset.offset >= 0 && fdFromAsset.len >= 0) {
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
                    for (int i3 = 0; i3 < 5; i3++) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("RELAY_FILE_INFO", fdFromAsset);
                        if (!arrayList.isEmpty()) {
                            bundle.putParcelableArray("CRYPTO_FILE_INFO", (Parcelable[]) arrayList.toArray(new FileInfo[0]));
                        }
                        Bundle processCommand = processCommand(INSTALL_CLIENT_LIBRARY_REQUEST, bundle);
                        z2 = processCommand != null && processCommand.getBoolean("dual_dar_response", true);
                        if (z2) {
                            break;
                        }
                        Log.e(TAG, "FSRelay loading failure: " + i3);
                    }
                    if (z2) {
                        if (DEBUG) {
                            Log.d(TAG, "FSRelay Loaded Successfully");
                        }
                        ParcelFileDescriptor parcelFileDescriptor3 = fdFromAsset.fd;
                        if (parcelFileDescriptor3 != null) {
                            try {
                                parcelFileDescriptor3.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            try {
                                int size2 = arrayList.size();
                                while (i < size2) {
                                    Object obj2 = arrayList.get(i);
                                    i++;
                                    ((FileInfo) obj2).fd.close();
                                }
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        return true;
                    }
                    Log.e(TAG, "FSRelay Load failed !!");
                    ParcelFileDescriptor parcelFileDescriptor4 = fdFromAsset.fd;
                    if (parcelFileDescriptor4 != null) {
                        try {
                            parcelFileDescriptor4.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        try {
                            int size3 = arrayList.size();
                            int i4 = 0;
                            while (i4 < size3) {
                                Object obj3 = arrayList.get(i4);
                                i4++;
                                ((FileInfo) obj3).fd.close();
                            }
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    return false;
                }
                Log.e(TAG, "pfd is null");
                ParcelFileDescriptor parcelFileDescriptor5 = fdFromAsset.fd;
                if (parcelFileDescriptor5 != null) {
                    try {
                        parcelFileDescriptor5.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
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
            } finally {
            }
        } catch (Exception e9) {
            Log.e(TAG, "Exception at installLibrary - " + e9.getMessage());
            e9.printStackTrace();
            if (0 != 0 && (parcelFileDescriptor = fileInfo.fd) != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e10) {
                    e10.printStackTrace();
                }
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
        Bundle processCommand = processCommand(GET_DUALDAR_USERS_REQUEST, null);
        if (processCommand == null) {
            Log.e(TAG, "Failed to get service");
            return null;
        }
        return processCommand.getIntegerArrayList("USERS");
    }

    public synchronized boolean getFileSystemLog(String str) {
        return fetchDumpState(str);
    }

    public String getInstalledClientLibraryVersion() {
        Bundle processCommand = processCommand(GET_CLIENT_VERSION_REQUEST, null);
        if (processCommand != null) {
            return processCommand.getString("CLIENT_VERSION");
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
        byte[] bArr;
        Log.d(TAG, "setSecret() ");
        try {
            Bundle bundle = new Bundle();
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            for (Secret secret : list) {
                try {
                    bArr = this.mSecureClientOutAPI.encryptMessageFor(DUALDAR_MGR_SERVICE, secret.data);
                } catch (Exception e) {
                    Log.e(TAG, "PUSH_SECRET_REQUEST failed to encrypt secrets");
                    e.printStackTrace();
                    bArr = null;
                }
                Wiper.wipe(secret.data);
                arrayList.add(new Secret(secret.alias, bArr));
            }
            list.clear();
            bundle.putParcelableArrayList("INNER_LAYER_SECRET", arrayList);
            bundle.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i);
            Bundle processCommandSecurely = processCommandSecurely(PUSH_SECRET_REQUEST, bundle);
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Parcelable parcelable = arrayList.get(i2);
                i2++;
                Wiper.wipe(((Secret) parcelable).data);
            }
            arrayList.clear();
            Log.d(TAG, "PUSH_SECRET_REQUEST response: " + (processCommandSecurely != null ? processCommandSecurely.getBoolean("dual_dar_response", true) : false));
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
