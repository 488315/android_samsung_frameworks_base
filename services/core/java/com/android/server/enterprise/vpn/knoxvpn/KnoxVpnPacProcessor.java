package com.android.server.enterprise.vpn.knoxvpn;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.net.IProxyService;
import com.android.net.module.util.NetworkStackConstants;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;
import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import libcore.net.InetAddressUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class KnoxVpnPacProcessor {
    public static Context mContext;
    public static KnoxVpnPacProcessor mKnoxVpnPacProcessor;
    public KnoxVpnFirewallHelper mFirewallHelper;
    public List mKnoxVpnProxyClientStatus;
    public VpnProfileConfig mVpnConfig;
    public static HashMap mDownloadStatus = new HashMap();
    public static HashMap mRetryAttempt = new HashMap();
    public static HashMap mDownloadUrlThread = new HashMap();
    public static final HashMap mProxyServiceList = new HashMap();
    public static final HashMap mproxyConnectionList = new HashMap();
    public static final boolean DBG = Debug.semIsProductDev();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DownloadUrlThread extends Thread {
        public final String hostname;
        public final String interfaceName;
        public final String path;
        public final int port;
        public final String profileName;

        public DownloadUrlThread(String str, String str2, String str3, int i, String str4) {
            this.profileName = str;
            this.hostname = str2;
            this.path = str3;
            this.port = i;
            this.interfaceName = str4;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                KnoxVpnPacProcessor knoxVpnPacProcessor = KnoxVpnPacProcessor.this;
                String str = this.profileName;
                String str2 = this.hostname;
                String str3 = this.path;
                int i = this.port;
                String str4 = this.interfaceName;
                Context context = KnoxVpnPacProcessor.mContext;
                knoxVpnPacProcessor.downloadPacUrl(i, str, str2, str3, str4);
            } catch (IllegalArgumentException unused) {
                Log.e("KnoxVpnPacProcessor", "Exception occured while downloading the pac url due to unknown format");
                KnoxVpnPacProcessor.getDownloadStatus().put(this.profileName, 1);
            } catch (Exception unused2) {
                Log.e("KnoxVpnPacProcessor", "Exception occured while trying to start the download thread to start downloading the pac url ");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KnoxVpnPacConnection implements ServiceConnection {
        public final String profile;
        public final int userId;

        public KnoxVpnPacConnection(int i, String str) {
            this.userId = i;
            this.profile = str;
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onBindingDied(ComponentName componentName) {
            Log.d("KnoxVpnPacProcessor", "onBindingDied is reached for user and trying to bind again " + this.userId + this.profile);
            ((ArrayList) KnoxVpnPacProcessor.this.mKnoxVpnProxyClientStatus).add("onBindingDied callback has been recieved for the proxy client in user " + this.userId + " and for profile " + this.profile + " at " + System.currentTimeMillis());
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("KnoxVpnPacProcessor", "onServiceConnected is reached for user " + this.userId + this.profile);
            ((ArrayList) KnoxVpnPacProcessor.this.mKnoxVpnProxyClientStatus).add("onServiceConnected callback has been recieved for the proxy client in user " + this.userId + " and for profile " + this.profile + " at " + System.currentTimeMillis());
            IProxyService asInterface = IProxyService.Stub.asInterface(iBinder);
            if (asInterface == null) {
                Log.d("KnoxVpnPacProcessor", "Got a null IBinder reference, aborting...");
                return;
            }
            KnoxVpnPacProcessor knoxVpnPacProcessor = KnoxVpnPacProcessor.this;
            int i = this.userId;
            knoxVpnPacProcessor.getClass();
            Log.d("KnoxVpnPacProcessor", "hasProxyService is being called for the userId " + i);
            HashMap hashMap = KnoxVpnPacProcessor.mProxyServiceList;
            if (hashMap.containsKey(Integer.valueOf(i))) {
                Log.d("KnoxVpnPacProcessor", "onServiceConnected:Binder object already exists for the user " + this.userId);
                return;
            }
            Log.d("KnoxVpnPacProcessor", "onServiceConnected:After onservice disconnect for unknown reason or for the first onservice connect");
            KnoxVpnPacProcessor knoxVpnPacProcessor2 = KnoxVpnPacProcessor.this;
            int i2 = this.userId;
            knoxVpnPacProcessor2.getClass();
            Log.d("KnoxVpnPacProcessor", "setProxyService is being called for the userId " + i2);
            hashMap.put(Integer.valueOf(i2), asInterface);
            if (KnoxVpnPacProcessor.this.getVpnConfigInstance() != null) {
                for (VpnProfileInfo vpnProfileInfo : KnoxVpnPacProcessor.this.mVpnConfig.vpnProfileInfoMap.values()) {
                    String str = vpnProfileInfo.mProfileName;
                    if (KnoxVpnPacProcessor.this.getConfiguredUser(str) == this.userId && vpnProfileInfo.activateState == 1) {
                        String str2 = vpnProfileInfo.mInterfaceName;
                        Uri uri = vpnProfileInfo.mPacurl;
                        String str3 = vpnProfileInfo.mProxyServer;
                        int i3 = vpnProfileInfo.mNetId;
                        if (uri != Uri.EMPTY) {
                            try {
                                boolean startPacSystemForKnoxProfile = asInterface.startPacSystemForKnoxProfile(str);
                                Log.d("KnoxVpnPacProcessor", "pac support for the profile" + str + " is started since the service is connected and the result is " + startPacSystemForKnoxProfile);
                                if (startPacSystemForKnoxProfile && str2 != null) {
                                    KnoxVpnPacProcessor.this.setCurrentProxyScript(str, str2, uri);
                                }
                            } catch (RemoteException unused) {
                            }
                            KnoxVpnPacProcessor.this.startProxyServerForKnoxProfile(str);
                            KnoxVpnPacProcessor.this.setMiscValueForPacProfile(i3, str, str2);
                            KnoxVpnPacProcessor.this.getClass();
                            KnoxVpnPacProcessor.sendPacServiceStatus(1, str);
                        } else if (str3 != null) {
                            KnoxVpnPacProcessor.this.startProxyServerForKnoxProfile(str);
                            KnoxVpnPacProcessor.this.setMiscValueForPacProfile(i3, str, str2);
                            KnoxVpnPacProcessor.this.getClass();
                            KnoxVpnPacProcessor.sendPacServiceStatus(1, str);
                        }
                    }
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final synchronized void onServiceDisconnected(ComponentName componentName) {
            try {
                Log.d("KnoxVpnPacProcessor", "onServicedisconnected is reached for user " + this.userId + this.profile);
                ((ArrayList) KnoxVpnPacProcessor.this.mKnoxVpnProxyClientStatus).add("onServiceDisconnected callback has been recieved for the proxy client in user " + this.userId + " and for profile " + this.profile + " at " + System.currentTimeMillis());
                KnoxVpnPacProcessor.getDownloadStatus().clear();
                KnoxVpnPacProcessor.getCurrentRetryStatus().clear();
                KnoxVpnPacProcessor.getDownloadThread().clear();
                if (KnoxVpnPacProcessor.this.getVpnConfigInstance() != null) {
                    Iterator it = KnoxVpnPacProcessor.this.mVpnConfig.vpnProfileInfoMap.values().iterator();
                    while (it.hasNext()) {
                        String str = ((VpnProfileInfo) it.next()).mProfileName;
                        KnoxVpnPacProcessor.this.getClass();
                        KnoxVpnPacProcessor.sendPacServiceStatus(0, str);
                    }
                }
                KnoxVpnPacProcessor knoxVpnPacProcessor = KnoxVpnPacProcessor.this;
                int i = this.userId;
                knoxVpnPacProcessor.getClass();
                KnoxVpnPacProcessor.removePacInterface(i);
                KnoxVpnPacProcessor.this.bindProxyService(this.userId, this.profile);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void closeExistingConnection(Socket socket, BufferedReader bufferedReader, PrintStream printStream) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException unused) {
                Log.e("KnoxVpnPacProcessor", "IOException occured while trying to close the socket");
                return;
            }
        }
        if (printStream != null) {
            printStream.close();
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }

    public static synchronized HashMap getCurrentRetryStatus() {
        HashMap hashMap;
        synchronized (KnoxVpnPacProcessor.class) {
            try {
                if (mRetryAttempt == null) {
                    mRetryAttempt = new HashMap();
                }
                hashMap = mRetryAttempt;
            } catch (Throwable th) {
                throw th;
            }
        }
        return hashMap;
    }

    public static synchronized HashMap getDownloadStatus() {
        HashMap hashMap;
        synchronized (KnoxVpnPacProcessor.class) {
            try {
                if (mDownloadStatus == null) {
                    mDownloadStatus = new HashMap();
                }
                hashMap = mDownloadStatus;
            } catch (Throwable th) {
                throw th;
            }
        }
        return hashMap;
    }

    public static synchronized HashMap getDownloadThread() {
        HashMap hashMap;
        synchronized (KnoxVpnPacProcessor.class) {
            try {
                if (mDownloadUrlThread == null) {
                    mDownloadUrlThread = new HashMap();
                }
                hashMap = mDownloadUrlThread;
            } catch (Throwable th) {
                throw th;
            }
        }
        return hashMap;
    }

    public static synchronized KnoxVpnPacProcessor getInstance(Context context) {
        KnoxVpnPacProcessor knoxVpnPacProcessor;
        synchronized (KnoxVpnPacProcessor.class) {
            try {
                if (mKnoxVpnPacProcessor == null) {
                    KnoxVpnPacProcessor knoxVpnPacProcessor2 = new KnoxVpnPacProcessor();
                    knoxVpnPacProcessor2.mKnoxVpnProxyClientStatus = new ArrayList();
                    knoxVpnPacProcessor2.mVpnConfig = null;
                    knoxVpnPacProcessor2.mFirewallHelper = null;
                    mContext = context;
                    mKnoxVpnPacProcessor = knoxVpnPacProcessor2;
                }
                knoxVpnPacProcessor = mKnoxVpnPacProcessor;
            } catch (Throwable th) {
                throw th;
            }
        }
        return knoxVpnPacProcessor;
    }

    public static boolean getPacUrlDownloadStatus(String str) {
        boolean z = false;
        try {
            getDownloadStatus();
            if (mDownloadStatus.containsKey(str)) {
                if (((Integer) mDownloadStatus.get(str)).intValue() == 0) {
                    z = true;
                }
            }
        } catch (NullPointerException unused) {
            Log.e("KnoxVpnPacProcessor", "error occured while trying to get download status pac file due to some values being not intialized successfully");
        } catch (Exception unused2) {
            Log.e("KnoxVpnPacProcessor", " error occured while trying to get download status pac file");
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("check to see if the pac url has been downloaded successfully ", "KnoxVpnPacProcessor", z);
        return z;
    }

    public static int getPortFromProtocol(String str) {
        int i;
        str.getClass();
        switch (str) {
            case "ftp":
                i = 21;
                break;
            case "http":
                i = 80;
                break;
            case "https":
                i = 443;
                break;
            default:
                throw new IllegalArgumentException();
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "The pac file is going to be downloaded from the remote port ", "KnoxVpnPacProcessor");
        return i;
    }

    public static IProxyService getProxyService(int i) {
        HashMap hashMap = mProxyServiceList;
        if (hashMap.containsKey(Integer.valueOf(i))) {
            return (IProxyService) hashMap.get(Integer.valueOf(i));
        }
        return null;
    }

    public static void removePacInterface(int i) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "removePacInterface is being called for the userId ", "KnoxVpnPacProcessor");
        HashMap hashMap = mProxyServiceList;
        if (hashMap.containsKey(Integer.valueOf(i))) {
            hashMap.remove(Integer.valueOf(i));
        }
    }

    public static void sendPacServiceStatus(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.knox.intent.action.INTERFACE_STATUS_INTERNAL");
            intent.putExtra("com.samsung.android.knox.intent.extra.ACTION_INTERNAL", "pac_info");
            intent.putExtra("com.samsung.android.knox.intent.extra.STATE_INTERNAL", i);
            intent.putExtra("com.samsung.android.knox.intent.extra.PROFILE_NAME_INTERNAL", str);
            mContext.sendBroadcast(intent, "com.samsung.android.knox.permission.KNOX_VPN_INTERNAL");
        } catch (Exception unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void bindProxyService(int i, String str) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Binding to the proxy service for the user ", "KnoxVpnPacProcessor");
        HashMap hashMap = mproxyConnectionList;
        if (hashMap.containsKey(Integer.valueOf(i))) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "unbinding previous service before binding again for the user ", "KnoxVpnPacProcessor");
            try {
                mContext.unbindService((ServiceConnection) hashMap.get(Integer.valueOf(i)));
            } catch (Exception unused) {
                Log.e("KnoxVpnPacProcessor", "unbinding failed since the service is already unbinded or not existing");
            }
            mproxyConnectionList.remove(Integer.valueOf(i));
        }
        KnoxVpnPacConnection knoxVpnPacConnection = new KnoxVpnPacConnection(i, str);
        Intent intent = new Intent();
        intent.setClassName("com.knox.vpn.proxyhandler", "com.knox.vpn.proxyhandler.PacService");
        mContext.bindServiceAsUser(intent, knoxVpnPacConnection, 1073741829, new UserHandle(i));
        mproxyConnectionList.put(Integer.valueOf(i), knoxVpnPacConnection);
    }

    public final synchronized void bindProxyService(String str, String str2, Uri uri, int i) {
        if (mContext == null) {
            Log.e("KnoxVpnPacProcessor", "No context for binding");
            return;
        }
        int configuredUser = getConfiguredUser(str);
        if (getProxyService(configuredUser) == null) {
            bindProxyService(configuredUser, str);
            return;
        }
        Log.e("KnoxVpnPacProcessor", "Already binded to proxy service for the user " + configuredUser);
        try {
            if (uri != Uri.EMPTY) {
                boolean startPacSystemForKnoxProfile = getProxyService(configuredUser).startPacSystemForKnoxProfile(str);
                Log.d("KnoxVpnPacProcessor", "pac support for the profile" + str + " is started since the service is connected and the result is " + startPacSystemForKnoxProfile + "user " + configuredUser);
                if (startPacSystemForKnoxProfile) {
                    setCurrentProxyScript(str, str2, uri);
                }
                startProxyServerForKnoxProfile(str);
                setMiscValueForPacProfile(i, str, str2);
                sendPacServiceStatus(1, str);
            } else {
                startProxyServerForKnoxProfile(str);
                setMiscValueForPacProfile(i, str, str2);
                sendPacServiceStatus(1, str);
            }
        } catch (RemoteException unused) {
        }
    }

    public final void checkIfRetryNeeded(String str) {
        try {
            VpnProfileInfo profileEntry = getVpnConfigInstance().getProfileEntry(str);
            if (profileEntry == null || profileEntry.mPackageMap.size() <= 0 || profileEntry.activateState != 1) {
                Log.d("KnoxVpnPacProcessor", "vpn profile is in unknown state, resetting the retry-Status to default value " + str);
                if (getCurrentRetryStatus().containsKey(str)) {
                    getCurrentRetryStatus().remove(str);
                    return;
                }
                return;
            }
            if (!getCurrentRetryStatus().containsKey(str)) {
                Log.d("KnoxVpnPacProcessor", "Downloading the pac url failed for the first time, start the retry process " + str);
                getCurrentRetryStatus().put(str, 0);
                startRetryAttempt(profileEntry);
                return;
            }
            if (((Integer) getCurrentRetryStatus().get(str)).intValue() >= 10) {
                if (((Integer) getCurrentRetryStatus().get(str)).intValue() == 10) {
                    Log.d("KnoxVpnPacProcessor", "Downloading the pac url failed even after the final retry attempt " + str);
                    if (getDownloadThread().get(str) != null) {
                        getDownloadThread().remove(str);
                        return;
                    }
                    return;
                }
                return;
            }
            getCurrentRetryStatus().put(str, Integer.valueOf(((Integer) getCurrentRetryStatus().get(str)).intValue() + 1));
            Log.d("KnoxVpnPacProcessor", "Downloading the pac url failed, going to retry for the " + getCurrentRetryStatus().get(str) + "attempt " + str);
            try {
                Thread.currentThread();
                Thread.sleep(5000L);
            } catch (InterruptedException unused) {
            }
            startRetryAttempt(profileEntry);
        } catch (Exception unused2) {
            StorageManagerService$$ExternalSyntheticOutline0.m("Exception occured while retry attempt to download pac file ", str, "KnoxVpnPacProcessor");
        }
    }

    public final void downloadPacUrl(int i, String str, String str2, String str3, String str4) {
        BufferedReader bufferedReader;
        PrintStream printStream;
        VpnProfileInfo profileEntry;
        boolean z;
        synchronized (str) {
            if (getPacUrlDownloadStatus(str)) {
                return;
            }
            Socket socket = null;
            try {
                try {
                    profileEntry = getVpnConfigInstance().getProfileEntry(str);
                } catch (IllegalArgumentException unused) {
                    bufferedReader = null;
                    printStream = null;
                } catch (ConnectException unused2) {
                    bufferedReader = null;
                    printStream = null;
                } catch (SocketException unused3) {
                    bufferedReader = null;
                    printStream = null;
                } catch (UnknownHostException unused4) {
                    bufferedReader = null;
                    printStream = null;
                } catch (ClosedByInterruptException unused5) {
                    bufferedReader = null;
                    printStream = null;
                } catch (IOException unused6) {
                    bufferedReader = null;
                    printStream = null;
                } catch (Exception unused7) {
                    bufferedReader = null;
                    printStream = null;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = null;
                    printStream = null;
                }
                if (profileEntry == null) {
                    return;
                }
                int i2 = profileEntry.mNetId;
                if (str4 == null || i2 == -1) {
                    bufferedReader = null;
                    printStream = null;
                } else {
                    if (DBG) {
                        Log.d("KnoxVpnPacProcessor", "The host name to resolve is " + str2);
                    }
                    Socket validEndPointAddress = getValidEndPointAddress(profileEntry.mVpnClientType, i, i2, str2, str4);
                    try {
                        if (validEndPointAddress == null) {
                            throw new ConnectException();
                        }
                        StringBuilder sb = new StringBuilder();
                        InputStream inputStream = validEndPointAddress.getInputStream();
                        printStream = new PrintStream(validEndPointAddress.getOutputStream());
                        try {
                            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            try {
                                printStream.println("GET " + str3 + " HTTP/1.1\r");
                                printStream.println("Host: " + str2 + "\r");
                                printStream.println("Connection: close\r");
                                printStream.println("\r");
                                printStream.flush();
                                String readLine = bufferedReader.readLine();
                                boolean z2 = false;
                                while (readLine != null) {
                                    if (readLine.isEmpty()) {
                                        readLine = bufferedReader.readLine();
                                        z2 = true;
                                    } else {
                                        if (z2) {
                                            if (DBG) {
                                                Log.d("KnoxVpnPacProcessor", "The pac line printed is " + readLine);
                                            }
                                            sb.append(readLine.trim());
                                            sb.append("\n");
                                        }
                                        readLine = bufferedReader.readLine();
                                    }
                                }
                                String sb2 = sb.toString();
                                if (sb2 == null || sb2.length() <= 0) {
                                    getDownloadStatus().put(str, 1);
                                } else {
                                    try {
                                        z = getProxyService(getConfiguredUser(str)).setPacFileForKnoxProfile(str, sb2);
                                    } catch (NullPointerException unused8) {
                                        Log.e("KnoxVpnPacProcessor", "Exception occured due to the pac service not intialized");
                                        closeExistingConnection(validEndPointAddress, bufferedReader, printStream);
                                        getDownloadStatus().put(str, 2);
                                        checkIfRetryNeeded(str);
                                        z = false;
                                    }
                                    if (z) {
                                        getDownloadStatus().put(str, 0);
                                        if (getCurrentRetryStatus().containsKey(str)) {
                                            Log.d("KnoxVpnPacProcessor", "The pac file has been downloaded successfully,cancelling further retry attempts");
                                            getCurrentRetryStatus().remove(str);
                                        }
                                        if (getDownloadThread().get(str) != null) {
                                            getDownloadThread().remove(str);
                                        }
                                        Log.d("KnoxVpnPacProcessor", "The Misc value for the pac file has been set successfully " + setMiscValueForPacProfile(i2, str, str4));
                                        sendPacServiceStatus(1, str);
                                    } else {
                                        closeExistingConnection(validEndPointAddress, bufferedReader, printStream);
                                        getDownloadStatus().put(str, 2);
                                        checkIfRetryNeeded(str);
                                    }
                                }
                                socket = validEndPointAddress;
                            } catch (IllegalArgumentException unused9) {
                                socket = validEndPointAddress;
                                Log.e("KnoxVpnPacProcessor", "cannot find the dns server to resolve the pac url");
                                closeExistingConnection(socket, bufferedReader, printStream);
                                getDownloadStatus().put(str, 1);
                                closeExistingConnection(socket, bufferedReader, printStream);
                            } catch (ConnectException unused10) {
                                socket = validEndPointAddress;
                                Log.e("KnoxVpnPacProcessor", "ConnectException occured while trying to download the pac url");
                                closeExistingConnection(socket, bufferedReader, printStream);
                                getDownloadStatus().put(str, 2);
                                checkIfRetryNeeded(str);
                                closeExistingConnection(socket, bufferedReader, printStream);
                            } catch (SocketException unused11) {
                                socket = validEndPointAddress;
                                Log.e("KnoxVpnPacProcessor", "SocketException occured while trying to download the pac url");
                                closeExistingConnection(socket, bufferedReader, printStream);
                                getDownloadStatus().put(str, 2);
                                checkIfRetryNeeded(str);
                                closeExistingConnection(socket, bufferedReader, printStream);
                            } catch (UnknownHostException unused12) {
                                socket = validEndPointAddress;
                                Log.e("KnoxVpnPacProcessor", "UnknownHostException occured while trying to download the pac url ");
                                try {
                                    str.wait(5000L);
                                    closeExistingConnection(socket, bufferedReader, printStream);
                                    getDownloadStatus().put(str, 2);
                                    checkIfRetryNeeded(str);
                                    closeExistingConnection(socket, bufferedReader, printStream);
                                } catch (InterruptedException unused13) {
                                    Log.e("KnoxVpnPacProcessor", "Got Interruption while trying to resolve the domain name ");
                                    closeExistingConnection(socket, bufferedReader, printStream);
                                    getDownloadStatus().put(str, 2);
                                    closeExistingConnection(socket, bufferedReader, printStream);
                                    return;
                                }
                            } catch (ClosedByInterruptException unused14) {
                                socket = validEndPointAddress;
                                Log.e("KnoxVpnPacProcessor", "Downloading the pac url failed due to Interruption, close the existing connections");
                                closeExistingConnection(socket, bufferedReader, printStream);
                                getDownloadStatus().put(str, 2);
                                closeExistingConnection(socket, bufferedReader, printStream);
                            } catch (IOException unused15) {
                                socket = validEndPointAddress;
                                Log.e("KnoxVpnPacProcessor", "IOException occured while trying to download the pac url ");
                                closeExistingConnection(socket, bufferedReader, printStream);
                                getDownloadStatus().put(str, 2);
                                checkIfRetryNeeded(str);
                                closeExistingConnection(socket, bufferedReader, printStream);
                            } catch (Exception unused16) {
                                socket = validEndPointAddress;
                                Log.e("KnoxVpnPacProcessor", "Exception occured while trying to pac url ");
                                closeExistingConnection(socket, bufferedReader, printStream);
                                getDownloadStatus().put(str, 1);
                                closeExistingConnection(socket, bufferedReader, printStream);
                            } catch (Throwable th2) {
                                th = th2;
                                socket = validEndPointAddress;
                                closeExistingConnection(socket, bufferedReader, printStream);
                                throw th;
                            }
                        } catch (IllegalArgumentException unused17) {
                            bufferedReader = null;
                        } catch (ConnectException unused18) {
                            bufferedReader = null;
                        } catch (SocketException unused19) {
                            bufferedReader = null;
                        } catch (UnknownHostException unused20) {
                            bufferedReader = null;
                        } catch (ClosedByInterruptException unused21) {
                            bufferedReader = null;
                        } catch (IOException unused22) {
                            bufferedReader = null;
                        } catch (Exception unused23) {
                            bufferedReader = null;
                        } catch (Throwable th3) {
                            th = th3;
                            bufferedReader = null;
                        }
                    } catch (IllegalArgumentException unused24) {
                        bufferedReader = null;
                        printStream = null;
                    } catch (ConnectException unused25) {
                        bufferedReader = null;
                        printStream = null;
                    } catch (SocketException unused26) {
                        bufferedReader = null;
                        printStream = null;
                    } catch (UnknownHostException unused27) {
                        bufferedReader = null;
                        printStream = null;
                    } catch (ClosedByInterruptException unused28) {
                        bufferedReader = null;
                        printStream = null;
                    } catch (IOException unused29) {
                        bufferedReader = null;
                        printStream = null;
                    } catch (Exception unused30) {
                        bufferedReader = null;
                        printStream = null;
                    } catch (Throwable th4) {
                        th = th4;
                        bufferedReader = null;
                        printStream = null;
                    }
                }
                closeExistingConnection(socket, bufferedReader, printStream);
            } catch (Throwable th5) {
                th = th5;
            }
        }
    }

    public final void extractUrlParameters(String str, String str2, String str3) {
        try {
            URL url = new URL(str3);
            String host = url.getHost();
            String path = url.getPath();
            String protocol = url.getProtocol();
            int port = url.getPort();
            int portFromProtocol = port == -1 ? getPortFromProtocol(protocol) : port;
            if (getDownloadThread().get(str) != null) {
                Log.d("KnoxVpnPacProcessor", "The current status of the download thread of the profile " + str + "is " + ((DownloadUrlThread) getDownloadThread().get(str)).getState());
                if (((DownloadUrlThread) getDownloadThread().get(str)).isAlive()) {
                    ((DownloadUrlThread) getDownloadThread().get(str)).interrupt();
                }
                getDownloadThread().remove(str);
            }
            DownloadUrlThread downloadUrlThread = new DownloadUrlThread(str, host, path, portFromProtocol, str2);
            downloadUrlThread.start();
            getDownloadThread().put(str, downloadUrlThread);
        } catch (IllegalArgumentException unused) {
            getDownloadStatus().put(str, 1);
            Log.e("KnoxVpnPacProcessor", "error occured while trying to download the pac file due to unknown port number");
        } catch (NullPointerException unused2) {
            getDownloadStatus().put(str, 1);
            Log.e("KnoxVpnPacProcessor", "error occured while trying to download the pac file due to some values being not intialized successfully");
        } catch (MalformedURLException unused3) {
            getDownloadStatus().put(str, 1);
            Log.e("KnoxVpnPacProcessor", "error occured while trying to download the pac file due to unknow url format, remove and create the profile again");
        } catch (Exception unused4) {
            getDownloadStatus().put(str, 1);
            Log.e("KnoxVpnPacProcessor", "error occured while trying to download the pac file after the vpn connection is established");
        }
    }

    public final int getConfiguredUser(String str) {
        VpnProfileInfo profileEntry = getVpnConfigInstance().getProfileEntry(str);
        if (profileEntry != null) {
            return profileEntry.personaId;
        }
        return 0;
    }

    public final List getKnoxVpnProxyClientStatus() {
        return this.mKnoxVpnProxyClientStatus;
    }

    public final int getProxyPortForProfile(String str) {
        try {
            return getProxyService(getConfiguredUser(str)).getProxyPortForProfile(str);
        } catch (NullPointerException unused) {
            StorageManagerService$$ExternalSyntheticOutline0.m("The knox vpn proxy service has not yet started for profile while querying for port ", str, "KnoxVpnPacProcessor");
            return -1;
        } catch (Exception unused2) {
            StorageManagerService$$ExternalSyntheticOutline0.m("unknown error occured for profile while querying for port ", str, "KnoxVpnPacProcessor");
            return -1;
        }
    }

    public final String getProxythreadStatus(String str) {
        try {
            return getProxyService(getConfiguredUser(str)).getProxythreadStatus(str);
        } catch (NullPointerException unused) {
            StorageManagerService$$ExternalSyntheticOutline0.m("The knox vpn proxy service has not yet started for profile while querying for proxy thread Status ", str, "KnoxVpnPacProcessor");
            return "IntialValue";
        } catch (Exception unused2) {
            StorageManagerService$$ExternalSyntheticOutline0.m("unknown error occured for profile while querying for proxy thread Status ", str, "KnoxVpnPacProcessor");
            return "IntialValue";
        }
    }

    public final Socket getValidEndPointAddress(int i, int i2, int i3, String str, String str2) {
        InetAddress inetAddress;
        InetAddress[] allByNameOnNet;
        Socket socket;
        try {
            inetAddress = InetAddressUtils.parseNumericAddress(str);
        } catch (IllegalArgumentException unused) {
            Log.d("KnoxVpnPacProcessor", "The pac url being entered is not of numeric form and need to do a dns look up");
            inetAddress = null;
        }
        if (inetAddress == null) {
            try {
                allByNameOnNet = InetAddress.getAllByNameOnNet(str, i3);
            } catch (Exception unused2) {
                Log.e("KnoxVpnPacProcessor", "UnknownHostException occured while trying to query the ip address for the pac url domain name");
                throw new UnknownHostException();
            }
        } else {
            allByNameOnNet = new InetAddress[]{inetAddress};
        }
        if (allByNameOnNet == null) {
            return null;
        }
        Socket socket2 = null;
        String str3 = null;
        for (InetAddress inetAddress2 : allByNameOnNet) {
            try {
                socket = new Socket();
                try {
                    boolean z = inetAddress2 instanceof Inet4Address;
                    boolean z2 = DBG;
                    if (z) {
                        Log.d("KnoxVpnPacProcessor", "The pac file is going to be downloaded from an interface having ipv4 address");
                        str3 = inetAddress2.getHostAddress();
                        if (z2) {
                            Log.d("KnoxVpnPacProcessor", "The resolved host address is a ipv4 address " + str3);
                        }
                        socket.bind(new InetSocketAddress(NetworkStackConstants.IPV4_ADDR_ANY, 0));
                    } else if (inetAddress2 instanceof Inet6Address) {
                        Log.d("KnoxVpnPacProcessor", "The pac file is going to be downloaded from an interface having ipv6 address");
                        str3 = inetAddress2.getHostAddress();
                        if (z2) {
                            Log.d("KnoxVpnPacProcessor", "The resolved host address is a ipv6 address " + str3);
                        }
                        socket.bind(new InetSocketAddress(NetworkStackConstants.IPV6_ADDR_ANY, 0));
                    }
                    FileDescriptor fileDescriptor$ = socket.getFileDescriptor$();
                    if (fileDescriptor$.getInt$() != -1) {
                        try {
                            Log.d("KnoxVpnPacProcessor", "bindResult value is " + jnibindSocketToInterface(i, i3, fileDescriptor$.getInt$(), str2));
                        } catch (Exception unused3) {
                            Log.e("KnoxVpnPacProcessor", "Exception occured while trying to bind the socket to the interface ");
                        }
                    }
                    socket.setSoTimeout(10000);
                    if (str3 != null) {
                        socket.connect(new InetSocketAddress(str3, i2), 10000);
                    }
                } catch (Exception unused4) {
                    socket2 = socket;
                    StorageManagerService$$ExternalSyntheticOutline0.m("IO Exception occured while trying to connect to the pac url remote address ", str3, "KnoxVpnPacProcessor");
                    if (socket2 != null) {
                        try {
                            socket2.close();
                            socket2 = null;
                        } catch (IOException unused5) {
                        }
                    } else {
                        socket = socket2;
                        if (socket == null) {
                        }
                        socket2 = socket;
                    }
                }
            } catch (Exception unused6) {
            }
            if (socket == null && socket.isConnected()) {
                return socket;
            }
            socket2 = socket;
        }
        return socket2;
    }

    public final synchronized VpnProfileConfig getVpnConfigInstance() {
        try {
            if (this.mVpnConfig == null) {
                this.mVpnConfig = VpnProfileConfig.getInstance();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mVpnConfig;
    }

    public final boolean isProxyThreadAlive(String str) {
        try {
            return getProxyService(getConfiguredUser(str)).isProxyThreadAlive(str);
        } catch (NullPointerException unused) {
            StorageManagerService$$ExternalSyntheticOutline0.m("The knox vpn proxy service has not yet started for profile while querying for proxy thread alive ", str, "KnoxVpnPacProcessor");
            return false;
        } catch (Exception unused2) {
            StorageManagerService$$ExternalSyntheticOutline0.m("unknown error occured for profile while querying for proxy thread alive ", str, "KnoxVpnPacProcessor");
            return false;
        }
    }

    public final boolean isProxyThreadRunning(String str) {
        try {
            return getProxyService(getConfiguredUser(str)).isProxyThreadRunning(str);
        } catch (NullPointerException unused) {
            StorageManagerService$$ExternalSyntheticOutline0.m("The knox vpn proxy service has not yet started for profile while querying for proxy thread running or not ", str, "KnoxVpnPacProcessor");
            return false;
        } catch (Exception unused2) {
            StorageManagerService$$ExternalSyntheticOutline0.m("unknown error occured for profile while querying for proxy thread running or not ", str, "KnoxVpnPacProcessor");
            return false;
        }
    }

    public native int jnibindSocketToInterface(int i, int i2, int i3, String str);

    public final void setCurrentProxyScript(String str, String str2, Uri uri) {
        try {
            if (uri == Uri.EMPTY) {
                Log.d("KnoxVpnPacProcessor", "The proxy script is not being set since the pac url is empty or a static proxy has been configured");
                return;
            }
            if (getProxyService(getConfiguredUser(str)) == null) {
                Log.d("KnoxVpnPacProcessor", "The proxy service has not been intialized while trying to set the proxy script, trying to bind again");
                bindProxyService(getConfiguredUser(str), str);
                return;
            }
            getDownloadStatus();
            if (!mDownloadStatus.containsKey(str)) {
                extractUrlParameters(str, str2, uri.toString());
                return;
            }
            if (((Integer) mDownloadStatus.get(str)).intValue() != 2) {
                return;
            }
            Log.d("KnoxVpnPacProcessor", "Try to download the pac url for the profile after the interface is up for the profile " + str);
            if (getCurrentRetryStatus().containsKey(str)) {
                getCurrentRetryStatus().remove(str);
            }
            extractUrlParameters(str, str2, uri.toString());
        } catch (NullPointerException unused) {
            Log.e("KnoxVpnPacProcessor", "error occured while trying to get current status pac file due to some values being not intialized successfully");
        } catch (Exception unused2) {
            Log.e("KnoxVpnPacProcessor", " error occured while trying to get current status pac file");
        }
    }

    public final boolean setMiscValueForPacProfile(int i, String str, String str2) {
        boolean z = false;
        try {
            z = getProxyService(getConfiguredUser(str)).setMiscValueForPacProfile(i, str, str2, getConfiguredUser(str));
            Log.d("KnoxVpnPacProcessor", "checking if the Misc value has been set for the knox pac profile " + z);
            return z;
        } catch (Exception unused) {
            Log.e("KnoxVpnPacProcessor", "Exception occurred while trying to set the misc value for the pac profile ");
            return z;
        }
    }

    public final int startProxyServerForKnoxProfile(String str) {
        VpnProfileInfo profileEntry = getVpnConfigInstance().getProfileEntry(str);
        if (profileEntry == null) {
            return -1;
        }
        try {
            String str2 = profileEntry.mProxyusername;
            String str3 = profileEntry.mProxyPassword;
            return getProxyService(getConfiguredUser(str)).startProxyServerForKnoxProfile(str, profileEntry.proxyAuthRequried, str2, str3, profileEntry.credentialsPredefined, profileEntry.mProxyServer, profileEntry.mProxyPort);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Error occured while trying to start the proxy server for the profile");
            sb.append(str);
            VpnManagerService$$ExternalSyntheticOutline0.m(e, sb, "KnoxVpnPacProcessor");
            return -1;
        }
    }

    public final void startRetryAttempt(VpnProfileInfo vpnProfileInfo) {
        String str = null;
        try {
            str = vpnProfileInfo.mProfileName;
            String str2 = vpnProfileInfo.mInterfaceName;
            String uri = vpnProfileInfo.mPacurl.toString();
            URL url = new URL(uri);
            String host = url.getHost();
            String path = url.getPath();
            String protocol = url.getProtocol();
            int port = url.getPort();
            if (port == -1) {
                port = getPortFromProtocol(protocol);
            }
            int i = port;
            if (str == null || str2 == null || uri == null || ((Integer) getDownloadStatus().get(str)).intValue() != 2) {
                return;
            }
            downloadPacUrl(i, str, host, path, str2);
        } catch (IllegalArgumentException unused) {
            getDownloadStatus().put(str, 1);
            Log.e("KnoxVpnPacProcessor", "retry attempt: Invalid port value is defined when trying to download the pac url");
        } catch (NullPointerException unused2) {
            getDownloadStatus().put(str, 1);
            Log.e("KnoxVpnPacProcessor", "retry attempt: Some profile values are not intialized when trying to download the pac url");
        } catch (MalformedURLException unused3) {
            getDownloadStatus().put(str, 1);
            Log.e("KnoxVpnPacProcessor", "retry attempt: error occured while trying to download the pac file due to unknow url format");
        } catch (Exception unused4) {
            getDownloadStatus().put(str, 1);
            Log.e("KnoxVpnPacProcessor", "retry attempt: error occured while trying to download the pac file");
        }
    }

    public final void stopPacSupport(String str) {
        try {
            getDownloadStatus();
            if (mDownloadStatus.containsKey(str)) {
                mDownloadStatus.remove(str);
            }
            getCurrentRetryStatus();
            if (getCurrentRetryStatus().containsKey(str)) {
                getCurrentRetryStatus().remove(str);
            }
            getDownloadThread();
            if (getDownloadThread().containsKey(str)) {
                getDownloadThread().remove(str);
            }
            boolean stopPacSystemForKnoxProfile = getProxyService(getConfiguredUser(str)).stopPacSystemForKnoxProfile(str);
            Log.d("KnoxVpnPacProcessor", "check to see if the pac support is removed for the profile " + stopPacSystemForKnoxProfile);
            if (stopPacSystemForKnoxProfile) {
                getProxyService(getConfiguredUser(str)).stopProxyServerForKnoxProfile(str);
            }
        } catch (NullPointerException unused) {
            Log.e("KnoxVpnPacProcessor", "error occured while trying to remove the pac support due to some values being not intialized successfully");
        } catch (Exception unused2) {
            StorageManagerService$$ExternalSyntheticOutline0.m(" error occured while trying to remove the pac script for the profile ", str, "KnoxVpnPacProcessor");
        }
    }

    public final synchronized void unBindPacService(int i) {
        try {
            HashMap hashMap = mproxyConnectionList;
            if (hashMap.containsKey(Integer.valueOf(i))) {
                mContext.unbindService((ServiceConnection) hashMap.get(Integer.valueOf(i)));
                hashMap.remove(Integer.valueOf(i));
            }
            if (getProxyService(i) != null) {
                removePacInterface(i);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void updatePermissionForAppsToaccessLocalHost(int i, int i2, String str) {
        try {
            VpnProfileInfo profileEntry = getVpnConfigInstance().getProfileEntry(str);
            if (profileEntry != null) {
                new HashSet();
                new HashSet();
                if (i == 0) {
                    if (this.mFirewallHelper == null) {
                        this.mFirewallHelper = KnoxVpnFirewallHelper.getInstance();
                    }
                    this.mFirewallHelper.addRulesToDenyAccessToLocalHost(i2);
                    if (this.mFirewallHelper == null) {
                        this.mFirewallHelper = KnoxVpnFirewallHelper.getInstance();
                    }
                    this.mFirewallHelper.addRulesToAllowAccessToLocalHostWithValidMark(i2, profileEntry.mInterface_type, profileEntry.mInterfaceName);
                    return;
                }
                if (i != 1) {
                    return;
                }
                if (this.mFirewallHelper == null) {
                    this.mFirewallHelper = KnoxVpnFirewallHelper.getInstance();
                }
                this.mFirewallHelper.removeRulesToDenyAccessToLocalHost(i2);
                if (this.mFirewallHelper == null) {
                    this.mFirewallHelper = KnoxVpnFirewallHelper.getInstance();
                }
                this.mFirewallHelper.removeRulesToAllowAccessToLocalHostWithValidMark(i2, profileEntry.mInterface_type, profileEntry.mInterfaceName);
            }
        } catch (Exception unused) {
            StorageManagerService$$ExternalSyntheticOutline0.m("Exception occurred while trying to apply rules to access local host for the vpn profile ", str, "KnoxVpnPacProcessor");
        }
    }
}
