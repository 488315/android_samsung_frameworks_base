package com.android.server.enterprise.filter;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.IActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.INetd;
import android.net.LinkProperties;
import android.net.Network;
import android.net.UidRangeParcel;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.net.IOemNetd;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapter.IDnsResolverAdapter;
import com.android.server.enterprise.adapterlayer.DnsResolverAdapter;
import com.android.server.enterprise.firewall.EnforceDnsManager;
import com.android.server.enterprise.firewall.KnoxNetIdManager;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.app.networkfilter.INetworkFilterProxy;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class KnoxNetworkFilterService extends IKnoxNetworkFilterService.Stub implements EnterpriseServiceCallback {
    public static final boolean DBG = Debug.semIsProductDev();
    public final ConnectivityManager mCm;
    public final Context mContext;
    public NetworkCallback mDefaultNetworkCallback;
    public EnforceDnsManager mDnsManager;
    public final IDnsResolverAdapter mDnsResolverAdapter;
    public final EnterpriseDeviceManager mEdm;
    public KnoxNwFilterHandler mHandler;
    public HandlerThread mHandlerThread;
    public KnoxNetworkFilterFirewall mKnoxNwFilterFw;
    public final KnoxNetworkFilterHelper mKnoxNwFilterHelper;
    public final KnoxNetworkFilterValidation mKnoxNwFilterValidation;
    public final KnoxNetIdManager mNetIdManager;
    public INetd mNetdService;
    public IOemNetd mOemNetdService;
    public NwFilterReceiver mReceiver;
    public final Set mRegisteredPkgList = new ArraySet();
    public HashMap mDefaultDnsConnectionList = new HashMap();
    public HashMap mVirtualDnsConnectionList = new HashMap();
    public HashMap mDefaultTcpConnectionList = new HashMap();
    public HashMap mDefaultUdpConnectionList = new HashMap();
    public INetworkFilterProxy mDefaultDnsProxyInterface = null;
    public INetworkFilterProxy mVirtualDnsProxyInterface = null;
    public INetworkFilterProxy mDefaultTcpProxyInterface = null;
    public INetworkFilterProxy mDefaultUdpProxyInterface = null;
    public final List mLocalProxyStatus = new ArrayList();
    public int mNwFilterProxyAppUid = -1;

    public final void handleActionAirplaneMode() {
    }

    public final void handleUltraPowerSavingMode(Bundle bundle) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public KnoxNetworkFilterService(Context context) {
        this.mContext = context;
        this.mKnoxNwFilterValidation = KnoxNetworkFilterValidation.getInstance(context);
        KnoxNetworkFilterHelper knoxNetworkFilterHelper = KnoxNetworkFilterHelper.getInstance(context);
        this.mKnoxNwFilterHelper = knoxNetworkFilterHelper;
        this.mEdm = EnterpriseDeviceManager.getInstance(context);
        this.mCm = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mNetIdManager = new KnoxNetIdManager();
        this.mDnsResolverAdapter = DnsResolverAdapter.getInstance();
        initializeHandlerThread();
        knoxNetworkFilterHelper.initializeVendorCacheData();
        initializeModules();
        initializeListeners();
    }

    public final IOemNetd getOemNetdService() {
        IOemNetd iOemNetd = this.mOemNetdService;
        if (iOemNetd != null) {
            return iOemNetd;
        }
        if (this.mNetdService == null) {
            connectNativeNetdService();
        }
        INetd iNetd = this.mNetdService;
        if (iNetd != null) {
            try {
                this.mOemNetdService = IOemNetd.Stub.asInterface(iNetd.getOemNetd());
            } catch (RemoteException e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "Failed to get OemNetd listener " + e.getMessage());
            }
        }
        return this.mOemNetdService;
    }

    public final void connectNativeNetdService() {
        boolean z;
        INetd asInterface;
        try {
            asInterface = INetd.Stub.asInterface(ServiceManager.getService(KnoxVpnFirewallHelper.NETD_SERVICE_NAME));
            this.mNetdService = asInterface;
        } catch (RemoteException unused) {
            this.mNetdService = null;
            z = false;
        }
        if (asInterface == null) {
            return;
        }
        z = asInterface.isAlive();
        if (z) {
            return;
        }
        Log.e("knoxNwFilter-KnoxNetworkFilterService", "Can't connect to NativeNetdService netd");
    }

    public final IActivityManager getAMSInstance() {
        return IActivityManager.Stub.asInterface(ServiceManager.getService("activity"));
    }

    public synchronized int registerApplication(ContextInfo contextInfo, String str, String str2, Bundle bundle) {
        checkCallingUidPermissionEMM(contextInfo);
        int registerApplicationValidation = this.mKnoxNwFilterValidation.registerApplicationValidation(contextInfo, str, str2, bundle);
        if (registerApplicationValidation != 0 && registerApplicationValidation != -6) {
            return registerApplicationValidation;
        }
        if (!this.mKnoxNwFilterHelper.addRegisterInfoToDatabase(contextInfo.mCallerUid, str, str2, bundle)) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerApplication: inserting info into db failed");
            return -8;
        }
        this.mNwFilterProxyAppUid = this.mKnoxNwFilterHelper.getUIDForPackage(contextInfo.mContainerId, "com.samsung.android.knox.app.networkfilter");
        Bundle bundle2 = new Bundle();
        bundle2.putInt("userId", contextInfo.mContainerId);
        bundle2.putString("package", str);
        sendMessageToHandler(10, bundle2);
        return registerApplicationValidation;
    }

    public synchronized int unregisterApplication(ContextInfo contextInfo, String str) {
        checkCallingUidPermissionEMM(contextInfo);
        int unregisterApplicationValidation = this.mKnoxNwFilterValidation.unregisterApplicationValidation(contextInfo, str);
        if (unregisterApplicationValidation != 0 && unregisterApplicationValidation != -6) {
            return unregisterApplicationValidation;
        }
        if (!this.mKnoxNwFilterHelper.removeRegisteredInfoFromDatabase(str)) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "unregisterApplication: deleting info from db failed");
            return -8;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("userId", contextInfo.mContainerId);
        bundle.putString("package", str);
        sendMessageToHandler(11, bundle);
        return unregisterApplicationValidation;
    }

    public synchronized List getRegisteredPackageList(ContextInfo contextInfo) {
        checkCallingUidPermissionEMM(contextInfo);
        return this.mKnoxNwFilterHelper.getRegisteredAppsByAdminId(contextInfo.mCallerUid);
    }

    public synchronized int getInstanceValidation() {
        return this.mKnoxNwFilterValidation.getInstanceValidation();
    }

    public synchronized int setConfig(String str, String str2) {
        int i;
        checkCallingUidPermission();
        int callingUid = Binder.getCallingUid();
        String packageNameForUid = this.mKnoxNwFilterHelper.getPackageNameForUid(callingUid);
        int configValidation = this.mKnoxNwFilterValidation.setConfigValidation(str, str2);
        if (configValidation != 0) {
            return configValidation;
        }
        try {
            i = this.mKnoxNwFilterHelper.retrieveProfileState(str);
        } catch (NullPointerException unused) {
            i = 6;
        }
        if (i == 3) {
            return -9;
        }
        INetworkFilterProxy iNetworkFilterProxy = this.mDefaultDnsProxyInterface;
        if (iNetworkFilterProxy == null || this.mDefaultTcpProxyInterface == null || this.mDefaultUdpProxyInterface == null) {
            return -8;
        }
        try {
            configValidation = iNetworkFilterProxy.setConfig(str, str2);
        } catch (RemoteException | NullPointerException unused2) {
        }
        try {
            configValidation = this.mDefaultTcpProxyInterface.setConfig(str, str2);
        } catch (RemoteException | NullPointerException unused3) {
        }
        try {
            configValidation = this.mDefaultUdpProxyInterface.setConfig(str, str2);
        } catch (RemoteException | NullPointerException unused4) {
        }
        if (str2 != null && !str2.isEmpty()) {
            if (!this.mKnoxNwFilterHelper.addVendorInfoToStorage(str, str2, callingUid, packageNameForUid)) {
                return -8;
            }
            if (this.mKnoxNwFilterHelper.getProfileList().size() == 1) {
                initializeModules();
            }
            return configValidation;
        }
        this.mKnoxNwFilterHelper.removeVendorInfoFromStorage(str);
        try {
            this.mKnoxNwFilterFw.flushUnAuthorizedPackets();
        } catch (NullPointerException unused5) {
        }
        return 0;
    }

    public synchronized String getConfig(String str) {
        checkCallingUidPermission();
        return this.mKnoxNwFilterHelper.getRulesConfig(str);
    }

    public synchronized List getAllProfiles() {
        checkCallingUidPermission();
        return this.mKnoxNwFilterHelper.getProfileListByVendor(Binder.getCallingUid());
    }

    public synchronized int registerListeners(String str, String str2) {
        checkCallingUidPermission();
        int registerListenersValidation = this.mKnoxNwFilterValidation.registerListenersValidation(str, str2);
        if (registerListenersValidation != 0) {
            return registerListenersValidation;
        }
        int i = -8;
        if (this.mDefaultDnsProxyInterface != null && this.mDefaultTcpProxyInterface != null && this.mDefaultUdpProxyInterface != null) {
            this.mKnoxNwFilterFw.flushUnAuthorizedPackets();
            int retrieveDnsCacheStatus = this.mKnoxNwFilterHelper.retrieveDnsCacheStatus(str2);
            try {
                if (retrieveDnsCacheStatus == 1) {
                    Log.d("knoxNwFilter-KnoxNetworkFilterService", "enable clearing the dns system cache");
                    getOemNetdService().setDnsCacheStatus(true);
                } else {
                    Log.d("knoxNwFilter-KnoxNetworkFilterService", "disable clearing the dns system cache");
                    getOemNetdService().setDnsCacheStatus(false);
                }
            } catch (RemoteException unused) {
            }
            this.mKnoxNwFilterHelper.insertListenersInCache(str, str2, retrieveDnsCacheStatus);
            try {
                this.mDefaultDnsProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "dns", this.mDefaultDnsProxyInterface.getListener(str));
            } catch (RemoteException unused2) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:dns:error occured while communicating to remote service");
                registerListenersValidation = -8;
                this.mDefaultTcpProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "tcp", this.mDefaultTcpProxyInterface.getListener(str));
                this.mDefaultUdpProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "udp", this.mDefaultUdpProxyInterface.getListener(str));
                i = registerListenersValidation;
                return i;
            } catch (IllegalArgumentException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:dns:IllegalArgumentException");
                registerListenersValidation = -8;
                this.mDefaultTcpProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "tcp", this.mDefaultTcpProxyInterface.getListener(str));
                this.mDefaultUdpProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "udp", this.mDefaultUdpProxyInterface.getListener(str));
                i = registerListenersValidation;
                return i;
            } catch (NullPointerException unused4) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:dns:error occurred since the remote service is null");
                registerListenersValidation = -8;
                this.mDefaultTcpProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "tcp", this.mDefaultTcpProxyInterface.getListener(str));
                this.mDefaultUdpProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "udp", this.mDefaultUdpProxyInterface.getListener(str));
                i = registerListenersValidation;
                return i;
            }
            try {
                this.mDefaultTcpProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "tcp", this.mDefaultTcpProxyInterface.getListener(str));
            } catch (RemoteException unused5) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:tcp:error occured while communicating to remote service");
                registerListenersValidation = -8;
                this.mDefaultUdpProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "udp", this.mDefaultUdpProxyInterface.getListener(str));
                i = registerListenersValidation;
                return i;
            } catch (IllegalArgumentException unused6) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:tcp:IllegalArgumentException");
                registerListenersValidation = -8;
                this.mDefaultUdpProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "udp", this.mDefaultUdpProxyInterface.getListener(str));
                i = registerListenersValidation;
                return i;
            } catch (NullPointerException unused7) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:tcp:error occurred since the remote service is null");
                registerListenersValidation = -8;
                this.mDefaultUdpProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "udp", this.mDefaultUdpProxyInterface.getListener(str));
                i = registerListenersValidation;
                return i;
            }
            try {
                this.mDefaultUdpProxyInterface.registerRemoteProxyAddr(str, str2);
                this.mKnoxNwFilterFw.denyUnAuthorizedPackets(this.mNwFilterProxyAppUid, "udp", this.mDefaultUdpProxyInterface.getListener(str));
                i = registerListenersValidation;
            } catch (RemoteException unused8) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:udp:error occured while communicating to remote service");
            } catch (IllegalArgumentException unused9) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:udp:IllegalArgumentException");
            } catch (NullPointerException unused10) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "registerListeners:udp:error occurred since the remote service is null");
            }
            return i;
        }
        return -8;
    }

    public synchronized String getRegisteredListeners(String str) {
        checkCallingUidPermission();
        return this.mKnoxNwFilterHelper.retrieveListenersFromCache(str);
    }

    public synchronized int start(String str) {
        checkCallingUidPermission();
        int callingUid = Binder.getCallingUid();
        int startValidation = this.mKnoxNwFilterValidation.startValidation(str);
        if (startValidation != 0) {
            return startValidation;
        }
        if (this.mDefaultDnsProxyInterface != null && this.mDefaultTcpProxyInterface != null && this.mDefaultUdpProxyInterface != null) {
            if (this.mKnoxNwFilterHelper.retrieveProfileState(str) == 3) {
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "skipping start call since the profile is already in running state");
                return startValidation;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SystemProperties.set("net.redirect_socket_calls.hooked", "true");
                getOemNetdService().enableIpOptionModification(true);
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            registerSystemDefaultNetworkCallback();
            Bundle bundle = new Bundle();
            bundle.putInt("uid", callingUid);
            bundle.putString("profileName", str);
            sendMessageToHandler(8, bundle);
            return startValidation;
        }
        return -8;
    }

    public synchronized int stop(String str, String str2) {
        checkCallingUidPermission();
        int callingUid = Binder.getCallingUid();
        int stopValidation = this.mKnoxNwFilterValidation.stopValidation(str);
        if (stopValidation != 0) {
            return stopValidation;
        }
        if (this.mDefaultDnsProxyInterface != null && this.mDefaultTcpProxyInterface != null && this.mDefaultUdpProxyInterface != null) {
            if (this.mKnoxNwFilterHelper.retrieveProfileState(str) != 3) {
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "skipping stop call since the profile is already in idle state");
                return stopValidation;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SystemProperties.set("net.redirect_socket_calls.hooked", "false");
                getOemNetdService().enableIpOptionModification(false);
                getOemNetdService().enablePortInfoEntries(2, 6, false);
                getOemNetdService().enablePortInfoEntries(10, 6, false);
                getOemNetdService().enablePortInfoEntries(10, 17, false);
                getOemNetdService().clearNetworkFilterEntries();
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            unregisterSystemDefaultNetworkCallback();
            Bundle bundle = new Bundle();
            bundle.putInt("uid", callingUid);
            bundle.putString("profileName", str);
            sendMessageToHandler(9, bundle);
            return stopValidation;
        }
        return -8;
    }

    public synchronized int pause(String str) {
        checkCallingUidPermission();
        return -1;
    }

    public synchronized int getProfileStatus(String str) {
        int i;
        checkCallingUidPermission();
        try {
            i = this.mKnoxNwFilterHelper.retrieveProfileState(str);
        } catch (NullPointerException unused) {
            i = -2;
        }
        return i;
    }

    public String getTcpV4PortInfo(int i) {
        String str = "";
        if (Binder.getCallingUid() != this.mNwFilterProxyAppUid) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "unauthorized caller for getTcpV4PortInfo");
            return "";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                try {
                    str = getOemNetdService().getNetworkFilterTcpV4Entry(i);
                } catch (RemoteException unused) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "RemoteException");
                }
            } catch (IllegalArgumentException unused2) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "IllegalArgumentException");
            } catch (SecurityException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "SecurityException");
            }
            if (DBG) {
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "portInfo value is " + str + " port value is " + i);
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String getPkgNameForTcpV4Port(int i) {
        String str = "";
        if (Binder.getCallingUid() != this.mNwFilterProxyAppUid) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "unauthorized caller for getPkgNameForTcpV4Port");
            return "";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = -1;
        try {
            try {
                try {
                    try {
                        String networkFilterTcpV4Entry = getOemNetdService().getNetworkFilterTcpV4Entry(i);
                        if (networkFilterTcpV4Entry != null && !networkFilterTcpV4Entry.isEmpty()) {
                            i2 = Integer.parseInt(networkFilterTcpV4Entry.split("_")[0]);
                            str = AppGlobals.getPackageManager().getNameForUid(i2);
                        }
                    } catch (IllegalArgumentException unused) {
                        Log.e("knoxNwFilter-KnoxNetworkFilterService", "IllegalArgumentException");
                    }
                } catch (SecurityException unused2) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "SecurityException");
                }
            } catch (RemoteException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "RemoteException");
            }
            if (DBG) {
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "getPkgNameForTcpV4Port value is " + str + " for uid " + i2 + " port value is " + i);
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String getTcpV6PortInfo(int i) {
        String str = "";
        if (Binder.getCallingUid() != this.mNwFilterProxyAppUid) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "unauthorized caller for getTcpV6PortInfo ");
            return "";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                try {
                    str = getOemNetdService().getNetworkFilterTcpV6Entry(i);
                } catch (RemoteException unused) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "RemoteException");
                }
            } catch (IllegalArgumentException unused2) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "IllegalArgumentException");
            } catch (SecurityException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "SecurityException");
            }
            if (DBG) {
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "getTcpV6PortInfo value is " + str + " port value is " + i);
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String getPkgNameForTcpV6Port(int i) {
        String str = "";
        if (Binder.getCallingUid() != this.mNwFilterProxyAppUid) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "unauthorized caller for getPkgNameForTcpV6Port");
            return "";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = -1;
        try {
            try {
                try {
                    try {
                        String networkFilterTcpV6Entry = getOemNetdService().getNetworkFilterTcpV6Entry(i);
                        if (networkFilterTcpV6Entry != null && !networkFilterTcpV6Entry.isEmpty()) {
                            i2 = Integer.parseInt(networkFilterTcpV6Entry.split("_")[0]);
                            str = AppGlobals.getPackageManager().getNameForUid(i2);
                        }
                    } catch (IllegalArgumentException unused) {
                        Log.e("knoxNwFilter-KnoxNetworkFilterService", "IllegalArgumentException");
                    }
                } catch (SecurityException unused2) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "SecurityException");
                }
            } catch (RemoteException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "RemoteException");
            }
            if (DBG) {
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "getPkgNameForTcpV6Port value is " + str + " for uid " + i2 + " port value is " + i);
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String getUdpV6PortInfo(int i) {
        String str = "";
        if (Binder.getCallingUid() != this.mNwFilterProxyAppUid) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "unauthorized caller for getUdpV6PortInfo");
            return "";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                try {
                    str = getOemNetdService().getNetworkFilterUdpV6Entry(i);
                } catch (RemoteException unused) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "RemoteException");
                }
            } catch (IllegalArgumentException unused2) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "IllegalArgumentException");
            } catch (SecurityException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "SecurityException");
            }
            if (DBG) {
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "getUdpV6PortInfo value is " + str + " port value is " + i);
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getKnoxNwFilterHttpProxyPort(int i, String str) {
        String profilebyUserId;
        KnoxNetworkFilterProfileInfo profileEntry;
        if (!this.mKnoxNwFilterHelper.getProfileList().isEmpty() && (profilebyUserId = this.mKnoxNwFilterHelper.getProfilebyUserId(i)) != null && (profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId)) != null && profileEntry.getDnsCacheStatus() == 1 && profileEntry.getBrowserAppList().contains(str)) {
            try {
                return this.mDefaultTcpProxyInterface.getHttpLocalProxyPort();
            } catch (RemoteException unused) {
            }
        }
        return -1;
    }

    public final void initializeHandlerThread() {
        HandlerThread handlerThread = new HandlerThread("KnoxNwFilterHandler", 10);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new KnoxNwFilterHandler(this.mHandlerThread.getLooper());
    }

    public final void sendMessageToHandler(int i, Bundle bundle) {
        KnoxNwFilterHandler knoxNwFilterHandler = this.mHandler;
        if (knoxNwFilterHandler != null) {
            this.mHandler.sendMessage(Message.obtain(knoxNwFilterHandler, i, 0, 0, bundle));
        }
    }

    public final void registerFilterList() {
        if (this.mReceiver != null) {
            return;
        }
        this.mReceiver = new NwFilterReceiver();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
            intentFilter.addCategory("android.intent.category.DEFAULT");
            intentFilter.addDataScheme("package");
            this.mContext.registerReceiverAsUser(this.mReceiver, UserHandle.ALL, intentFilter, null, null);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.BOOT_COMPLETED");
            intentFilter2.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
            intentFilter2.addAction("android.intent.action.AIRPLANE_MODE");
            intentFilter2.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
            intentFilter2.addAction("android.intent.action.USER_REMOVED");
            this.mContext.registerReceiverAsUser(this.mReceiver, UserHandle.ALL, intentFilter2, null, null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unRegisterFilterList() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                NwFilterReceiver nwFilterReceiver = this.mReceiver;
                if (nwFilterReceiver != null) {
                    this.mContext.unregisterReceiver(nwFilterReceiver);
                }
                this.mReceiver = null;
            } catch (Exception unused) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "Error occured while trying to unregister the reciever");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public class NwFilterReceiver extends BroadcastReceiver {
        public NwFilterReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String schemeSpecificPart;
            String action = intent.getAction();
            if (isInitialStickyBroadcast()) {
                return;
            }
            Bundle bundle = new Bundle();
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_ADDED")) {
                Uri data = intent.getData();
                schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                if (intExtra > -1) {
                    bundle.putInt("uid", intExtra);
                    bundle.putString("package", schemeSpecificPart);
                    bundle.putBoolean("new_install_or_update", booleanExtra);
                    KnoxNetworkFilterService.this.sendMessageToHandler(1, bundle);
                    return;
                }
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_REMOVED")) {
                Uri data2 = intent.getData();
                schemeSpecificPart = data2 != null ? data2.getSchemeSpecificPart() : null;
                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                boolean booleanExtra2 = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                bundle.putInt("uid", intExtra2);
                bundle.putString("package", schemeSpecificPart);
                bundle.putBoolean("new_install_or_update", booleanExtra2);
                KnoxNetworkFilterService.this.sendMessageToHandler(2, bundle);
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_DATA_CLEARED")) {
                KnoxNetworkFilterService.this.sendMessageToHandler(3, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
                KnoxNetworkFilterService.this.sendMessageToHandler(4, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                KnoxNetworkFilterService.this.sendMessageToHandler(5, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.AIRPLANE_MODE")) {
                if (intent.getBooleanExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false)) {
                    KnoxNetworkFilterService.this.sendMessageToHandler(6, null);
                }
            } else if (action.equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED")) {
                KnoxNetworkFilterService.this.sendMessageToHandler(7, intent.getExtras());
            } else if (action.equals("android.intent.action.USER_REMOVED")) {
                KnoxNetworkFilterService.this.sendMessageToHandler(12, intent.getExtras());
            }
        }
    }

    public class KnoxNwFilterHandler extends Handler {
        public KnoxNwFilterHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Bundle bundle = (Bundle) message.obj;
            switch (message.what) {
                case 1:
                    KnoxNetworkFilterService.this.handleActionPackageAdded(bundle);
                    break;
                case 2:
                    KnoxNetworkFilterService.this.handleActionPackageRemoved(bundle);
                    break;
                case 3:
                    KnoxNetworkFilterService.this.handleActionClearData(bundle);
                    break;
                case 4:
                    int i = bundle.getInt("android.intent.extra.user_handle", -10000);
                    SemEmergencyManager.getInstance(KnoxNetworkFilterService.this.mContext);
                    if (!SemEmergencyManager.isEmergencyMode(KnoxNetworkFilterService.this.mContext)) {
                        KnoxNetworkFilterService.this.handleActionBootComplete(i);
                        break;
                    }
                    break;
                case 5:
                    bundle.getInt("android.intent.extra.user_handle", -10000);
                    SemEmergencyManager.getInstance(KnoxNetworkFilterService.this.mContext);
                    SemEmergencyManager.isEmergencyMode(KnoxNetworkFilterService.this.mContext);
                    break;
                case 6:
                    KnoxNetworkFilterService.this.handleActionAirplaneMode();
                    break;
                case 7:
                    if (bundle.getInt("reason", 0) == 5) {
                        Log.d("knoxNwFilter-KnoxNetworkFilterService", "ultra power saving mode has been disabled");
                        KnoxNetworkFilterService.this.handleUltraPowerSavingMode(bundle);
                        break;
                    }
                    break;
                case 8:
                    KnoxNetworkFilterService.this.handleStartFilteringOperation(bundle);
                    break;
                case 9:
                    KnoxNetworkFilterService.this.handleStopFilteringOperation(bundle);
                    break;
                case 10:
                    KnoxNetworkFilterService.this.handleRegisterOperation(bundle);
                    break;
                case 11:
                    KnoxNetworkFilterService.this.handleUnregisterOperation(bundle);
                    break;
                case 12:
                    KnoxNetworkFilterService.this.handleActionUserRemoved(bundle);
                    break;
            }
        }
    }

    public final synchronized void handleRegisterOperation(Bundle bundle) {
        int i = bundle.getInt("userId");
        String string = bundle.getString("package");
        int uIDForPackage = this.mKnoxNwFilterHelper.getUIDForPackage(i, string);
        try {
            bindInternalProxyServices(i);
            initializeListeners();
            if (uIDForPackage != -1) {
                this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization(string, uIDForPackage, true);
                this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(uIDForPackage, true);
            }
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "sending register broadcast to package " + string + " userId " + i + " success during register process");
            sendStatusIntent(i, 0, string, 0);
        } catch (InterruptedException unused) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "sending register broadcast to package " + string + " userId " + i + " failed during register process");
            sendStatusIntent(i, 0, string, -8);
        }
    }

    public final synchronized void handleUnregisterOperation(Bundle bundle) {
        String[] strArr;
        int i = bundle.getInt("userId");
        String string = bundle.getString("package");
        int uIDForPackage = this.mKnoxNwFilterHelper.getUIDForPackage(i, string);
        int uIDForPackage2 = this.mKnoxNwFilterHelper.getUIDForPackage(i, "com.samsung.android.knox.app.networkfilter");
        String profilebyUserId = this.mKnoxNwFilterHelper.getProfilebyUserId(i);
        if (profilebyUserId == null) {
            unbindInternalProxyServices(i);
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SystemProperties.set("net.redirect_socket_calls.hooked", "false");
            getOemNetdService().enableIpOptionModification(false);
            getOemNetdService().enablePortInfoEntries(2, 6, false);
            getOemNetdService().enablePortInfoEntries(10, 6, false);
            getOemNetdService().enablePortInfoEntries(10, 17, false);
            getOemNetdService().clearNetworkFilterEntries();
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        unregisterSystemDefaultNetworkCallback();
        try {
            releaseNwFilterNetId(getOemNetdService().getNwFilterNetId());
            getOemNetdService().removeUserFromNwFilterRange(i);
            getOemNetdService().setNwFilterNetId(-1);
            getOemNetdService().clearKnoxNwFilterProxyEntries();
        } catch (RemoteException unused2) {
        }
        for (String str : KnoxNetworkFilterConstants.NW_FILTER_EXEMPT_LIST) {
            int uIDForPackage3 = this.mKnoxNwFilterHelper.getUIDForPackage(i, str);
            if (uIDForPackage3 != -1) {
                removeExemptUidFromNwFilterRange(uIDForPackage3, uIDForPackage3);
            }
        }
        removeExemptUidFromNwFilterRange(uIDForPackage, uIDForPackage);
        if (i == 0) {
            removeExemptUidFromNwFilterRange(1, 9999);
        }
        KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
        if (profileEntry != null) {
            Iterator it = profileEntry.getVpnClientUidList().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                removeExemptUidFromNwFilterRange(intValue, intValue);
            }
        }
        this.mKnoxNwFilterFw.flushAppGeneratedDOTBlockRules();
        this.mKnoxNwFilterFw.flushExemptDnsRulesFromNat();
        try {
            this.mDefaultDnsProxyInterface.stopProxyServer();
        } catch (RemoteException unused3) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:dns:error occured while communicating to remote service");
        } catch (NullPointerException unused4) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:dns:error occured remote service null");
        }
        this.mKnoxNwFilterFw.flushRedirectDnsQueryRules();
        try {
            this.mDefaultTcpProxyInterface.stopProxyServer();
        } catch (RemoteException unused5) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:tcp:error occured while communicating to remote service");
        } catch (NullPointerException unused6) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:tcp:error occured remote service null");
        }
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectTcpConnRules();
        try {
            this.mDefaultUdpProxyInterface.stopProxyServer();
        } catch (RemoteException unused7) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:udp:error occured while communicating to remote service");
        } catch (NullPointerException unused8) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:udp:error occured remote service null");
        }
        this.mKnoxNwFilterFw.flushUdpPacketExemptRules(2);
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectUdpConnRules();
        try {
            this.mDefaultDnsProxyInterface.stopV6ProxyServer();
        } catch (RemoteException unused9) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:dns:v6:error occured while communicating to remote service");
        } catch (NullPointerException unused10) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:dns:v6:error occured remote service null");
        }
        this.mKnoxNwFilterFw.flushRedirectV6DnsQueryRules();
        try {
            this.mDefaultTcpProxyInterface.stopV6ProxyServer();
        } catch (RemoteException unused11) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:tcp:V6:error occured while communicating to remote service");
        } catch (NullPointerException unused12) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:tcp:V6:error occured remote service null");
        }
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6TcpConnRules();
        try {
            this.mDefaultUdpProxyInterface.stopV6ProxyServer();
        } catch (RemoteException unused13) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:udp:V6:error occured while communicating to remote service");
        } catch (NullPointerException unused14) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:udp:V6:error occured remote service null");
        }
        this.mKnoxNwFilterFw.flushUdpPacketExemptRules(10);
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6UdpConnRules();
        try {
            this.mDefaultTcpProxyInterface.stopHttpProxyServer();
            strArr = this.mDefaultTcpProxyInterface.getBrowserAppList();
            try {
                getOemNetdService().setHttpProxyPort(-1);
            } catch (RemoteException unused15) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:tcp:http:error occured while communicating to remote service");
                this.mKnoxNwFilterHelper.applyHttpProxyConfiguration(profilebyUserId, strArr, false, i, -1);
                this.mKnoxNwFilterFw.flushTcpRedirectRulesForCaptivePortal();
                this.mDefaultDnsProxyInterface.setConfig(profilebyUserId, (String) null);
                this.mDefaultTcpProxyInterface.setConfig(profilebyUserId, (String) null);
                this.mDefaultUdpProxyInterface.setConfig(profilebyUserId, (String) null);
                this.mKnoxNwFilterFw.flushUnAuthorizedPackets();
                this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization(string, uIDForPackage, false);
                this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(uIDForPackage, false);
                this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization("com.samsung.android.knox.app.networkfilter", uIDForPackage2, false);
                this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(uIDForPackage2, false);
                this.mKnoxNwFilterHelper.updateProfileState(profilebyUserId, 5);
                unbindInternalProxyServices(i);
                this.mKnoxNwFilterHelper.removeVendorInfoFromStorage(profilebyUserId);
                unRegisterFilterList();
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "sending unregister broadcast to package " + string + " userId " + i + " success during unregister process");
                sendStatusIntent(i, 4, string, 0);
            } catch (NullPointerException unused16) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:tcp:http:error occured remote service null");
                this.mKnoxNwFilterHelper.applyHttpProxyConfiguration(profilebyUserId, strArr, false, i, -1);
                this.mKnoxNwFilterFw.flushTcpRedirectRulesForCaptivePortal();
                this.mDefaultDnsProxyInterface.setConfig(profilebyUserId, (String) null);
                this.mDefaultTcpProxyInterface.setConfig(profilebyUserId, (String) null);
                this.mDefaultUdpProxyInterface.setConfig(profilebyUserId, (String) null);
                this.mKnoxNwFilterFw.flushUnAuthorizedPackets();
                this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization(string, uIDForPackage, false);
                this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(uIDForPackage, false);
                this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization("com.samsung.android.knox.app.networkfilter", uIDForPackage2, false);
                this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(uIDForPackage2, false);
                this.mKnoxNwFilterHelper.updateProfileState(profilebyUserId, 5);
                unbindInternalProxyServices(i);
                this.mKnoxNwFilterHelper.removeVendorInfoFromStorage(profilebyUserId);
                unRegisterFilterList();
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "sending unregister broadcast to package " + string + " userId " + i + " success during unregister process");
                sendStatusIntent(i, 4, string, 0);
            }
        } catch (RemoteException unused17) {
            strArr = null;
        } catch (NullPointerException unused18) {
            strArr = null;
        }
        this.mKnoxNwFilterHelper.applyHttpProxyConfiguration(profilebyUserId, strArr, false, i, -1);
        this.mKnoxNwFilterFw.flushTcpRedirectRulesForCaptivePortal();
        try {
            this.mDefaultDnsProxyInterface.setConfig(profilebyUserId, (String) null);
            this.mDefaultTcpProxyInterface.setConfig(profilebyUserId, (String) null);
            this.mDefaultUdpProxyInterface.setConfig(profilebyUserId, (String) null);
        } catch (RemoteException unused19) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:setConfig:error occured while communicating to remote service");
        } catch (NullPointerException unused20) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleUnregisterOperation:setConfig:error occured remote service null");
        }
        this.mKnoxNwFilterFw.flushUnAuthorizedPackets();
        this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization(string, uIDForPackage, false);
        this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(uIDForPackage, false);
        this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization("com.samsung.android.knox.app.networkfilter", uIDForPackage2, false);
        this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(uIDForPackage2, false);
        this.mKnoxNwFilterHelper.updateProfileState(profilebyUserId, 5);
        unbindInternalProxyServices(i);
        this.mKnoxNwFilterHelper.removeVendorInfoFromStorage(profilebyUserId);
        unRegisterFilterList();
        Log.d("knoxNwFilter-KnoxNetworkFilterService", "sending unregister broadcast to package " + string + " userId " + i + " success during unregister process");
        sendStatusIntent(i, 4, string, 0);
    }

    public final synchronized void handleStartFilteringOperation(Bundle bundle) {
        Set set;
        int i = bundle.getInt("uid");
        int userId = UserHandle.getUserId(i);
        String string = bundle.getString("profileName");
        String packageNameForUid = this.mKnoxNwFilterHelper.getPackageNameForUid(i);
        int uIDForPackage = this.mKnoxNwFilterHelper.getUIDForPackage(userId, "com.samsung.android.knox.app.networkfilter");
        String configuredDestIpRange = this.mKnoxNwFilterHelper.getConfiguredDestIpRange(string, 2);
        String configuredDestIpRange2 = this.mKnoxNwFilterHelper.getConfiguredDestIpRange(string, 10);
        this.mKnoxNwFilterHelper.getConfiguredProtocols(string);
        KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(string);
        if (profileEntry == null) {
            return;
        }
        int i2 = setupNetworkDns();
        if (i2 == -1) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:dns:invalid netId returned");
            sendStatusIntent(userId, 1, packageNameForUid, -8);
            return;
        }
        try {
            getOemNetdService().setNwFilterNetId(i2);
            getOemNetdService().addUserToNwFilterRange(userId);
            if (uIDForPackage != -1 && this.mKnoxNwFilterHelper.checkIfPlatformSigned(userId, "com.samsung.android.knox.app.networkfilter")) {
                getOemNetdService().setKnoxNwFilterProxyApp(uIDForPackage);
            }
            for (String str : KnoxNetworkFilterConstants.NW_FILTER_EXEMPT_LIST) {
                int uIDForPackage2 = this.mKnoxNwFilterHelper.getUIDForPackage(userId, str);
                if (uIDForPackage2 != -1) {
                    exemptUidFromNwFilterRange(uIDForPackage2, uIDForPackage2);
                }
            }
            exemptUidFromNwFilterRange(i, i);
            if (userId == 0) {
                exemptUidFromNwFilterRange(1, 9999);
            }
            this.mKnoxNwFilterFw.blockAppGeneratedDOT(userId);
            if (profileEntry.isV4DnsConfigured()) {
                try {
                    this.mKnoxNwFilterFw.redirectDnsQuery(this.mDefaultDnsProxyInterface.startProxyServer());
                } catch (RemoteException unused) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:dns:error occured while communicating to remote service");
                    sendStatusIntent(userId, 1, packageNameForUid, -8);
                    return;
                } catch (NullPointerException unused2) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:dns:error occured remote service null");
                    sendStatusIntent(userId, 1, packageNameForUid, -8);
                    return;
                }
            }
            if (profileEntry.isV4TcpConfigured()) {
                try {
                    try {
                        this.mKnoxNwFilterFw.redirectAppGeneratedTcpConn(userId, configuredDestIpRange, this.mDefaultTcpProxyInterface.startProxyServer());
                        try {
                            getOemNetdService().enablePortInfoEntries(2, 6, true);
                        } catch (RemoteException unused3) {
                        }
                    } catch (NullPointerException unused4) {
                        Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:tcp:error occured remote service null");
                        sendStatusIntent(userId, 1, packageNameForUid, -8);
                        return;
                    }
                } catch (RemoteException unused5) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:tcp:error occured while communicating to remote service");
                    sendStatusIntent(userId, 1, packageNameForUid, -8);
                    return;
                }
            }
            if (profileEntry.isV4UdpConfigured()) {
                try {
                    int startProxyServer = this.mDefaultUdpProxyInterface.startProxyServer();
                    this.mKnoxNwFilterFw.exemptUdpPacketFromNwFilterRange(UserHandle.getUserId(userId), 2);
                    this.mKnoxNwFilterFw.redirectAppGeneratedUdpConn(userId, configuredDestIpRange, startProxyServer);
                } catch (RemoteException unused6) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:udp:error occured while communicating to remote service");
                    sendStatusIntent(userId, 1, packageNameForUid, -8);
                    return;
                } catch (NullPointerException unused7) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:udp:error occured remote service null");
                    sendStatusIntent(userId, 1, packageNameForUid, -8);
                    return;
                }
            }
            if (profileEntry.isV6TcpConfigured()) {
                try {
                    this.mKnoxNwFilterFw.redirectAppGeneratedV6TcpConn(userId, configuredDestIpRange2, this.mDefaultTcpProxyInterface.startV6ProxyServer());
                    try {
                        getOemNetdService().enablePortInfoEntries(10, 6, true);
                    } catch (RemoteException unused8) {
                    }
                } catch (RemoteException unused9) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:tcp:V6:error occured while communicating to remote service");
                    sendStatusIntent(userId, 1, packageNameForUid, -8);
                    return;
                } catch (NullPointerException unused10) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:tcp:v6:error occured remote service null");
                    sendStatusIntent(userId, 1, packageNameForUid, -8);
                    return;
                }
            }
            if (profileEntry.isV6UdpConfigured()) {
                try {
                    try {
                        int startV6ProxyServer = this.mDefaultUdpProxyInterface.startV6ProxyServer();
                        this.mKnoxNwFilterFw.exemptUdpPacketFromNwFilterRange(UserHandle.getUserId(userId), 10);
                        this.mKnoxNwFilterFw.redirectAppGeneratedV6UdpConn(userId, configuredDestIpRange2, startV6ProxyServer);
                        try {
                            getOemNetdService().enablePortInfoEntries(10, 17, true);
                        } catch (RemoteException unused11) {
                        }
                    } catch (RemoteException unused12) {
                        Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:udp:V6:error occured while communicating to remote service");
                        sendStatusIntent(userId, 1, packageNameForUid, -8);
                        return;
                    }
                } catch (NullPointerException unused13) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:udp:V6:error occured remote service null");
                    sendStatusIntent(userId, 1, packageNameForUid, -8);
                    return;
                }
            }
            try {
                try {
                    int startHttpProxyServer = this.mDefaultTcpProxyInterface.startHttpProxyServer();
                    String[] browserAppList = this.mDefaultTcpProxyInterface.getBrowserAppList();
                    getOemNetdService().setHttpProxyPort(startHttpProxyServer);
                    this.mKnoxNwFilterHelper.applyHttpProxyConfiguration(string, browserAppList, true, userId, startHttpProxyServer);
                    Set installedVpnClientList = this.mKnoxNwFilterHelper.getInstalledVpnClientList(userId);
                    if (installedVpnClientList != null && !installedVpnClientList.isEmpty()) {
                        Iterator it = installedVpnClientList.iterator();
                        while (it.hasNext()) {
                            int intValue = ((Integer) it.next()).intValue();
                            profileEntry.addUidToVpnClientList(intValue);
                            exemptUidFromNwFilterRange(intValue, intValue);
                        }
                        int length = browserAppList.length;
                        int i3 = 0;
                        while (i3 < length) {
                            String str2 = browserAppList[i3];
                            int uIDForPackage3 = this.mKnoxNwFilterHelper.getUIDForPackage(userId, str2);
                            if (uIDForPackage3 > 0) {
                                this.mKnoxNwFilterFw.applyTcpRedirectRulesForCaptivePortal(uIDForPackage3, startHttpProxyServer);
                                if (installedVpnClientList.contains(Integer.valueOf(uIDForPackage3))) {
                                    StringBuilder sb = new StringBuilder();
                                    set = installedVpnClientList;
                                    sb.append("ignore adding the browser package ");
                                    sb.append(str2);
                                    sb.append(" to vpn list");
                                    Log.i("knoxNwFilter-KnoxNetworkFilterService", sb.toString());
                                    profileEntry.removeUidFromVpnClientList(uIDForPackage3);
                                    removeExemptUidFromNwFilterRange(uIDForPackage3, uIDForPackage3);
                                    i3++;
                                    installedVpnClientList = set;
                                }
                            }
                            set = installedVpnClientList;
                            i3++;
                            installedVpnClientList = set;
                        }
                    }
                    this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization(packageNameForUid, i, true);
                    this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(i, true);
                    this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization("com.samsung.android.knox.app.networkfilter", uIDForPackage, true);
                    this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(uIDForPackage, true);
                    this.mKnoxNwFilterHelper.updateProfileState(string, 3);
                    sendStatusIntent(userId, 1, packageNameForUid, 0);
                    sendEMMStatusIntent(userId, 1, this.mKnoxNwFilterHelper.getPackageNameForUid(this.mKnoxNwFilterHelper.getAdminIdForUser(userId)), 0);
                } catch (RemoteException e) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:tcp:http:error occured while communicating to remote service " + Log.getStackTraceString(e));
                    sendStatusIntent(userId, 1, packageNameForUid, -8);
                }
            } catch (NullPointerException e2) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:tcp:http:error occured remote service null" + Log.getStackTraceString(e2));
                sendStatusIntent(userId, 1, packageNameForUid, -8);
            }
        } catch (RemoteException unused14) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStartFilteringOperation:dns:error occured while communicating to remote oemnetd service");
            sendStatusIntent(userId, 1, packageNameForUid, -8);
        }
    }

    public final synchronized void handleStopFilteringOperation(Bundle bundle) {
        int i = bundle.getInt("uid");
        int userId = UserHandle.getUserId(i);
        String string = bundle.getString("profileName");
        String packageNameForUid = this.mKnoxNwFilterHelper.getPackageNameForUid(i);
        int uIDForPackage = this.mKnoxNwFilterHelper.getUIDForPackage(userId, "com.samsung.android.knox.app.networkfilter");
        try {
            releaseNwFilterNetId(getOemNetdService().getNwFilterNetId());
            getOemNetdService().removeUserFromNwFilterRange(userId);
            getOemNetdService().setNwFilterNetId(-1);
            getOemNetdService().clearKnoxNwFilterProxyEntries();
            this.mKnoxNwFilterFw.flushUnAuthorizedPackets();
            for (String str : KnoxNetworkFilterConstants.NW_FILTER_EXEMPT_LIST) {
                int uIDForPackage2 = this.mKnoxNwFilterHelper.getUIDForPackage(userId, str);
                if (uIDForPackage2 != -1) {
                    removeExemptUidFromNwFilterRange(uIDForPackage2, uIDForPackage2);
                }
            }
            removeExemptUidFromNwFilterRange(i, i);
            if (userId == 0) {
                removeExemptUidFromNwFilterRange(1, 9999);
            }
            KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(string);
            if (profileEntry != null) {
                Iterator it = profileEntry.getVpnClientUidList().iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    removeExemptUidFromNwFilterRange(intValue, intValue);
                }
            }
            this.mKnoxNwFilterFw.flushAppGeneratedDOTBlockRules();
            this.mKnoxNwFilterFw.flushExemptDnsRulesFromNat();
            try {
                this.mDefaultDnsProxyInterface.stopProxyServer();
            } catch (RemoteException unused) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:dns:error occured while communicating to remote service");
            } catch (NullPointerException unused2) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:dns:error occured remote service null");
            }
            this.mKnoxNwFilterFw.flushRedirectDnsQueryRules();
            try {
                this.mDefaultTcpProxyInterface.stopProxyServer();
            } catch (RemoteException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:tcp:error occured while communicating to remote service");
            } catch (NullPointerException unused4) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:tcp:error occured remote service null");
            }
            this.mKnoxNwFilterFw.flushAppGeneratedRedirectTcpConnRules();
            try {
                this.mDefaultUdpProxyInterface.stopProxyServer();
            } catch (RemoteException unused5) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:udp:error occured while communicating to remote service");
            } catch (NullPointerException unused6) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:udp:error occured remote service null");
            }
            this.mKnoxNwFilterFw.flushUdpPacketExemptRules(2);
            this.mKnoxNwFilterFw.flushAppGeneratedRedirectUdpConnRules();
            try {
                this.mDefaultDnsProxyInterface.stopV6ProxyServer();
            } catch (RemoteException unused7) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:dns:v6:error occured while communicating to remote service");
            } catch (NullPointerException unused8) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:dns:v6:error occured remote service null");
            }
            this.mKnoxNwFilterFw.flushRedirectV6DnsQueryRules();
            try {
                try {
                    this.mDefaultTcpProxyInterface.stopV6ProxyServer();
                } catch (NullPointerException unused9) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:tcp:V6:error occured remote service null");
                }
            } catch (RemoteException unused10) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:tcp:V6:error occured while communicating to remote service");
            }
            this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6TcpConnRules();
            try {
                this.mDefaultUdpProxyInterface.stopV6ProxyServer();
            } catch (RemoteException unused11) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:udp:V6:error occured while communicating to remote service");
            } catch (NullPointerException unused12) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:udp:V6:error occured remote service null");
            }
            this.mKnoxNwFilterFw.flushUdpPacketExemptRules(10);
            this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6UdpConnRules();
            String[] strArr = null;
            try {
                this.mDefaultTcpProxyInterface.stopHttpProxyServer();
                strArr = this.mDefaultTcpProxyInterface.getBrowserAppList();
                getOemNetdService().setHttpProxyPort(-1);
            } catch (RemoteException unused13) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:tcp:http:error occured while communicating to remote service");
            } catch (NullPointerException unused14) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:tcp:http:error occured remote service null");
            }
            this.mKnoxNwFilterHelper.applyHttpProxyConfiguration(string, strArr, false, userId, -1);
            this.mKnoxNwFilterFw.flushTcpRedirectRulesForCaptivePortal();
            this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization("com.samsung.android.knox.app.networkfilter", uIDForPackage, false);
            this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(uIDForPackage, false);
            this.mKnoxNwFilterHelper.updateProfileState(string, 5);
            sendStatusIntent(userId, 2, packageNameForUid, 0);
            sendEMMStatusIntent(userId, 2, this.mKnoxNwFilterHelper.getPackageNameForUid(this.mKnoxNwFilterHelper.getAdminIdForUser(userId)), 0);
        } catch (RemoteException unused15) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleStopFilteringOperation:error occured while communicating to remote oemnetd service");
            sendStatusIntent(userId, 2, packageNameForUid, -8);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006b A[Catch: all -> 0x0142, TryCatch #2 {, blocks: (B:3:0x0001, B:8:0x0024, B:12:0x003f, B:15:0x0042, B:19:0x004b, B:21:0x0057, B:25:0x0060, B:28:0x0063, B:30:0x006b, B:32:0x0098, B:35:0x00a0, B:37:0x00aa, B:38:0x00b1, B:41:0x00d1, B:44:0x00db, B:47:0x00e6, B:49:0x00ee, B:53:0x00f6, B:58:0x0104, B:59:0x010c), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0104 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized void handleActionPackageAdded(Bundle bundle) {
        String[] strArr;
        INetworkFilterProxy iNetworkFilterProxy;
        String registeredAppPackage;
        KnoxNetworkFilterProfileInfo profileEntry;
        String string = bundle.getString("package");
        int i = bundle.getInt("uid");
        int userId = UserHandle.getUserId(i);
        boolean z = bundle.getBoolean("new_install_or_update");
        if (this.mKnoxNwFilterHelper.isUserIdRegistered(userId)) {
            String versionName = this.mKnoxNwFilterHelper.getVersionName(userId, string);
            String signature = this.mKnoxNwFilterHelper.getSignature(userId, string);
            int versionCode = this.mKnoxNwFilterHelper.getVersionCode(userId, string);
            INetworkFilterProxy iNetworkFilterProxy2 = this.mDefaultDnsProxyInterface;
            if (iNetworkFilterProxy2 != null) {
                try {
                    iNetworkFilterProxy2.updateApplicationInfo(string, i, signature, versionName, versionCode);
                } catch (RemoteException | NullPointerException unused) {
                }
            }
            INetworkFilterProxy iNetworkFilterProxy3 = this.mDefaultTcpProxyInterface;
            if (iNetworkFilterProxy3 != null) {
                try {
                    iNetworkFilterProxy3.updateApplicationInfo(string, i, signature, versionName, versionCode);
                    strArr = this.mDefaultTcpProxyInterface.getBrowserAppList();
                } catch (RemoteException | NullPointerException unused2) {
                }
                String[] strArr2 = strArr;
                iNetworkFilterProxy = this.mDefaultUdpProxyInterface;
                if (iNetworkFilterProxy != null) {
                    try {
                        iNetworkFilterProxy.updateApplicationInfo(string, i, signature, versionName, versionCode);
                    } catch (RemoteException | NullPointerException unused3) {
                    }
                }
                if (this.mKnoxNwFilterHelper.isVpnClient(userId, string)) {
                    Log.d("knoxNwFilter-KnoxNetworkFilterService", "vpn client with package name " + string + " and uid " + i + " is installed");
                    String profilebyUserId = this.mKnoxNwFilterHelper.getProfilebyUserId(userId);
                    if (profilebyUserId != null && (profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId)) != null && strArr2 != null) {
                        if (!Arrays.asList(strArr2).contains(string)) {
                            profileEntry.addUidToVpnClientList(i);
                            exemptUidFromNwFilterRange(i, i);
                        } else {
                            Log.i("knoxNwFilter-KnoxNetworkFilterService", "ignore adding the browser package " + string + " to vpn list");
                        }
                    }
                }
                if (!z && strArr2 != null && Arrays.asList(strArr2).contains(string)) {
                    try {
                        this.mKnoxNwFilterFw.applyTcpRedirectRulesForCaptivePortal(i, this.mDefaultTcpProxyInterface.getHttpLocalProxyPort());
                    } catch (RemoteException | NullPointerException unused4) {
                    }
                }
                registeredAppPackage = this.mKnoxNwFilterHelper.getRegisteredAppPackage(userId);
                if (registeredAppPackage != null || registeredAppPackage.equalsIgnoreCase(string)) {
                    if (this.mKnoxNwFilterHelper.isAppRegistered(userId, string, this.mKnoxNwFilterHelper.getSignature(userId, string))) {
                        try {
                            Thread.currentThread();
                            Thread.sleep(5000L);
                        } catch (Exception unused5) {
                        }
                        this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization(string, i, true);
                        this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(i, true);
                        Log.d("knoxNwFilter-KnoxNetworkFilterService", "sending register broadcast to package " + string + " userId " + userId + " success after install");
                        sendStatusIntent(userId, 0, string, 0);
                    }
                }
                return;
            }
            strArr = null;
            String[] strArr22 = strArr;
            iNetworkFilterProxy = this.mDefaultUdpProxyInterface;
            if (iNetworkFilterProxy != null) {
            }
            if (this.mKnoxNwFilterHelper.isVpnClient(userId, string)) {
            }
            if (!z) {
                this.mKnoxNwFilterFw.applyTcpRedirectRulesForCaptivePortal(i, this.mDefaultTcpProxyInterface.getHttpLocalProxyPort());
            }
            registeredAppPackage = this.mKnoxNwFilterHelper.getRegisteredAppPackage(userId);
            if (registeredAppPackage != null) {
            }
            if (this.mKnoxNwFilterHelper.isAppRegistered(userId, string, this.mKnoxNwFilterHelper.getSignature(userId, string))) {
            }
        }
    }

    public final synchronized void handleActionPackageRemoved(Bundle bundle) {
        String[] strArr;
        KnoxNetworkFilterProfileInfo profileEntry;
        String string = bundle.getString("package");
        int i = bundle.getInt("uid");
        int userId = UserHandle.getUserId(i);
        boolean z = bundle.getBoolean("new_install_or_update");
        if (this.mKnoxNwFilterHelper.isUserIdRegistered(userId)) {
            INetworkFilterProxy iNetworkFilterProxy = this.mDefaultDnsProxyInterface;
            if (iNetworkFilterProxy != null) {
                try {
                    iNetworkFilterProxy.updateApplicationInfo(string, -1, (String) null, (String) null, 0);
                } catch (RemoteException | NullPointerException unused) {
                }
            }
            INetworkFilterProxy iNetworkFilterProxy2 = this.mDefaultTcpProxyInterface;
            if (iNetworkFilterProxy2 != null) {
                try {
                    iNetworkFilterProxy2.updateApplicationInfo(string, -1, (String) null, (String) null, 0);
                } catch (RemoteException | NullPointerException unused2) {
                }
            }
            INetworkFilterProxy iNetworkFilterProxy3 = this.mDefaultUdpProxyInterface;
            if (iNetworkFilterProxy3 != null) {
                try {
                    iNetworkFilterProxy3.updateApplicationInfo(string, -1, (String) null, (String) null, 0);
                } catch (RemoteException | NullPointerException unused3) {
                }
            }
            String profilebyUserId = this.mKnoxNwFilterHelper.getProfilebyUserId(userId);
            if (profilebyUserId != null && (profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId)) != null && profileEntry.removeUidFromVpnClientList(i)) {
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "vpn client with package name " + string + " and uid " + i + " is uninstalled");
                removeExemptUidFromNwFilterRange(i, i);
            }
            if (!z) {
                try {
                    strArr = this.mDefaultTcpProxyInterface.getBrowserAppList();
                } catch (RemoteException | NullPointerException unused4) {
                    strArr = null;
                }
                if (strArr != null && Arrays.asList(strArr).contains(string)) {
                    try {
                        this.mKnoxNwFilterFw.removeTcpRedirectRulesForCaptivePortal(i, this.mDefaultTcpProxyInterface.getHttpLocalProxyPort());
                    } catch (RemoteException | NullPointerException unused5) {
                    }
                }
            }
            if (this.mKnoxNwFilterHelper.isVendorPkgInstalled(string) && !z) {
                handleVendorPackageUninstall(i, string);
            }
        }
    }

    public final synchronized void handleActionClearData(Bundle bundle) {
        int i = bundle.getInt("android.intent.extra.UID");
        int userId = UserHandle.getUserId(i);
        if (this.mKnoxNwFilterHelper.isUserIdRegistered(userId)) {
            String packageNameForUid = this.mKnoxNwFilterHelper.getPackageNameForUid(i);
            String registeredAppPackage = this.mKnoxNwFilterHelper.getRegisteredAppPackage(userId);
            if (registeredAppPackage != null && registeredAppPackage.equalsIgnoreCase(packageNameForUid)) {
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "clear data action is triggered for package " + packageNameForUid + " with uid " + i);
                handleVendorPackageUninstall(i, packageNameForUid);
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "sending register broadcast to package " + packageNameForUid + " userId " + userId + " success after clear data event");
                sendStatusIntent(userId, 0, packageNameForUid, 0);
            }
        }
    }

    public final synchronized void handleActionBootComplete(int i) {
        if (this.mKnoxNwFilterHelper.isUserIdRegistered(i)) {
            this.mNwFilterProxyAppUid = this.mKnoxNwFilterHelper.getUIDForPackage(i, "com.samsung.android.knox.app.networkfilter");
            String registeredAppPackage = this.mKnoxNwFilterHelper.getRegisteredAppPackage(i);
            try {
                bindInternalProxyServices(i);
            } catch (InterruptedException unused) {
                if (registeredAppPackage != null) {
                    Log.d("knoxNwFilter-KnoxNetworkFilterService", "sending register broadcast to package " + registeredAppPackage + " userId " + i + " failed after boot complete");
                    sendStatusIntent(i, 0, registeredAppPackage, -8);
                    return;
                }
            }
            if (registeredAppPackage != null) {
                Log.d("knoxNwFilter-KnoxNetworkFilterService", "sending register broadcast to package " + registeredAppPackage + " userId " + i + " success after boot complete");
                sendStatusIntent(i, 0, registeredAppPackage, 0);
            }
        }
    }

    public final ContextInfo checkCallingUidPermissionEMM(ContextInfo contextInfo) {
        return this.mEdm.enforceDoPoOnlyPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_MGMT")));
    }

    public final void checkCallingUidPermission() {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_SERVICE_PROVIDER", " Permission not granted");
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        String packageNameForUid = this.mKnoxNwFilterHelper.getPackageNameForUid(callingUid);
        if (this.mKnoxNwFilterHelper.isAppRegistered(userId, packageNameForUid, this.mKnoxNwFilterHelper.getSignature(userId, packageNameForUid))) {
            return;
        }
        Log.e("knoxNwFilter-KnoxNetworkFilterService", "failed due to caller not registered");
        throw new SecurityException("failed due to caller not registered");
    }

    public final void sendStatusIntent(int i, int i2, String str, int i3) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.knox.intent.action.NOTIFY_STATUS");
            intent.putExtra("com.samsung.android.knox.intent.extra.ERROR_CODE", i3);
            intent.addFlags(67108864);
            intent.setFlags(32);
            if (i2 == 0) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 1);
            } else if (i2 == 1) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 100);
            } else if (i2 == 2) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 101);
            } else if (i2 == 3) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 102);
            } else if (i2 == 4) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 2);
            }
            intent.setPackage(str);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendEMMStatusIntent(int i, int i2, String str, int i3) {
        if (str == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.knox.intent.action.NOTIFY_NETWORK_FILTER_STATUS");
            intent.addFlags(67108864);
            intent.setFlags(32);
            if (i2 == 1) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 1);
            } else if (i2 == 2) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 3);
            } else if (i2 == 3) {
                intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", 2);
            }
            intent.setPackage(str);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void bindInternalProxyServices(int i) {
        DefaultDnsConnection defaultDnsConnection = new DefaultDnsConnection(i);
        defaultDnsConnection.countDownLatch = new CountDownLatch(1);
        Intent intent = new Intent();
        intent.setClassName("com.samsung.android.knox.app.networkfilter", "com.samsung.android.knox.app.networkfilter.dns.DefaultDnsProxyService");
        if (this.mContext.bindServiceAsUser(intent, defaultDnsConnection, 1073741829, new UserHandle(i)) && defaultDnsConnection.countDownLatch.await(30000L, TimeUnit.MILLISECONDS)) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "binding to default dns proxy service is success");
            this.mDefaultDnsConnectionList.put(Integer.valueOf(i), defaultDnsConnection);
        }
        defaultDnsConnection.countDownLatch = null;
        VirtualDnsConnection virtualDnsConnection = new VirtualDnsConnection(i);
        virtualDnsConnection.countDownLatch = new CountDownLatch(1);
        Intent intent2 = new Intent();
        intent2.setClassName("com.samsung.android.knox.app.networkfilter", "com.samsung.android.knox.app.networkfilter.dns.VirtualDnsProxyService");
        if (this.mContext.bindServiceAsUser(intent2, virtualDnsConnection, 1073741829, new UserHandle(i)) && virtualDnsConnection.countDownLatch.await(30000L, TimeUnit.MILLISECONDS)) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "binding to default virtual proxy service is success");
            this.mVirtualDnsConnectionList.put(Integer.valueOf(i), virtualDnsConnection);
        }
        virtualDnsConnection.countDownLatch = null;
        DefaultTcpConnection defaultTcpConnection = new DefaultTcpConnection(i);
        defaultTcpConnection.countDownLatch = new CountDownLatch(1);
        Intent intent3 = new Intent();
        intent3.setClassName("com.samsung.android.knox.app.networkfilter", "com.samsung.android.knox.app.networkfilter.tcp.DefaultTcpProxyService");
        if (this.mContext.bindServiceAsUser(intent3, defaultTcpConnection, 1073741829, new UserHandle(i)) && defaultTcpConnection.countDownLatch.await(30000L, TimeUnit.MILLISECONDS)) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "binding to default tcp proxy service is success");
            this.mDefaultTcpConnectionList.put(Integer.valueOf(i), defaultTcpConnection);
        }
        defaultTcpConnection.countDownLatch = null;
        DefaultUdpConnection defaultUdpConnection = new DefaultUdpConnection(i);
        defaultUdpConnection.countDownLatch = new CountDownLatch(1);
        Intent intent4 = new Intent();
        intent4.setClassName("com.samsung.android.knox.app.networkfilter", "com.samsung.android.knox.app.networkfilter.udp.DefaultUdpProxyService");
        if (this.mContext.bindServiceAsUser(intent4, defaultUdpConnection, 1073741829, new UserHandle(i)) && defaultUdpConnection.countDownLatch.await(30000L, TimeUnit.MILLISECONDS)) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "binding to default udp proxy service is success");
            this.mDefaultUdpConnectionList.put(Integer.valueOf(i), defaultUdpConnection);
        }
        defaultUdpConnection.countDownLatch = null;
    }

    public final void unbindInternalProxyServices(int i) {
        if (this.mDefaultDnsConnectionList.containsKey(Integer.valueOf(i))) {
            this.mContext.unbindService((ServiceConnection) this.mDefaultDnsConnectionList.get(Integer.valueOf(i)));
            this.mDefaultDnsConnectionList.remove(Integer.valueOf(i));
        }
        if (this.mVirtualDnsConnectionList.containsKey(Integer.valueOf(i))) {
            this.mContext.unbindService((ServiceConnection) this.mVirtualDnsConnectionList.get(Integer.valueOf(i)));
            this.mVirtualDnsConnectionList.remove(Integer.valueOf(i));
        }
        if (this.mDefaultTcpConnectionList.containsKey(Integer.valueOf(i))) {
            this.mContext.unbindService((ServiceConnection) this.mDefaultTcpConnectionList.get(Integer.valueOf(i)));
            this.mDefaultTcpConnectionList.remove(Integer.valueOf(i));
        }
        if (this.mDefaultUdpConnectionList.containsKey(Integer.valueOf(i))) {
            this.mContext.unbindService((ServiceConnection) this.mDefaultUdpConnectionList.get(Integer.valueOf(i)));
            this.mDefaultUdpConnectionList.remove(Integer.valueOf(i));
        }
    }

    public class DefaultDnsConnection implements ServiceConnection {
        public CountDownLatch countDownLatch = null;
        public int userId;

        public DefaultDnsConnection(int i) {
            this.userId = i;
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceDisconnected(ComponentName componentName) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "DefaultDnsConnection:onServicedisconnected is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("DefaultDnsConnection:onServiceDisconnected " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mDefaultDnsProxyInterface = null;
            try {
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.flushRedirectDnsQueryRules();
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.flushAppGeneratedRedirectRules();
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.flushUnAuthorizedPackets();
            } catch (NullPointerException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public synchronized void onBindingDied(ComponentName componentName) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "DefaultDnsConnection:onBindingDied is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("DefaultDnsConnection:onBindingDied " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mDefaultDnsProxyInterface = null;
            if (KnoxNetworkFilterService.this.mDefaultDnsConnectionList.containsKey(Integer.valueOf(this.userId))) {
                KnoxNetworkFilterService.this.mContext.unbindService((ServiceConnection) KnoxNetworkFilterService.this.mDefaultDnsConnectionList.get(Integer.valueOf(this.userId)));
                KnoxNetworkFilterService.this.mDefaultDnsConnectionList.remove(Integer.valueOf(this.userId));
            }
            DefaultDnsConnection defaultDnsConnection = KnoxNetworkFilterService.this.new DefaultDnsConnection(this.userId);
            Intent intent = new Intent();
            intent.setClassName("com.samsung.android.knox.app.networkfilter", "com.samsung.android.knox.app.networkfilter.dns.DefaultDnsProxyService");
            if (KnoxNetworkFilterService.this.mContext.bindServiceAsUser(intent, defaultDnsConnection, 1073741829, new UserHandle(this.userId))) {
                KnoxNetworkFilterService.this.mDefaultDnsConnectionList.put(Integer.valueOf(this.userId), defaultDnsConnection);
            } else {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "failed to bind dns process after onBinding died event, clearing up the rules applied");
                KnoxNetworkFilterService.this.handleBindingDiedEvent(this.userId);
            }
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "DefaultDnsConnection:onServiceConnected is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("DefaultDnsConnection:onServiceConnected " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mDefaultDnsProxyInterface = INetworkFilterProxy.Stub.asInterface(iBinder);
            CountDownLatch countDownLatch = this.countDownLatch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            String profilebyUserId = KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getProfilebyUserId(this.userId);
            if (profilebyUserId == null) {
                return;
            }
            KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getConfiguredProtocols(profilebyUserId);
            KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
            if (profileEntry == null) {
                return;
            }
            try {
                KnoxNetworkFilterService.this.mDefaultDnsProxyInterface.setConfig(profilebyUserId, KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getRulesConfig(profilebyUserId));
                KnoxNetworkFilterService.this.mDefaultDnsProxyInterface.registerRemoteProxyAddr(profilebyUserId, KnoxNetworkFilterService.this.mKnoxNwFilterHelper.retrieveListenersFromCache(profilebyUserId));
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.denyUnAuthorizedPackets(KnoxNetworkFilterService.this.mNwFilterProxyAppUid, "dns", KnoxNetworkFilterService.this.mDefaultDnsProxyInterface.getListener(profilebyUserId));
                if (KnoxNetworkFilterService.this.mKnoxNwFilterHelper.retrieveProfileState(profilebyUserId) == 3 && profileEntry.isV4DnsConfigured()) {
                    KnoxNetworkFilterService.this.mKnoxNwFilterFw.redirectDnsQuery(KnoxNetworkFilterService.this.mDefaultDnsProxyInterface.startProxyServer());
                }
            } catch (RemoteException | NullPointerException unused) {
            }
        }
    }

    public class VirtualDnsConnection implements ServiceConnection {
        public CountDownLatch countDownLatch = null;
        public int userId;

        public VirtualDnsConnection(int i) {
            this.userId = i;
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceDisconnected(ComponentName componentName) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "VirtualDnsConnection:onServicedisconnected is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("VirtualDnsConnection:onServiceDisconnected " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mVirtualDnsProxyInterface = null;
            try {
                KnoxNetworkFilterService.this.mDefaultDnsProxyInterface.vpnMessengerForDnsQuery((IBinder) null);
            } catch (RemoteException | NullPointerException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public synchronized void onBindingDied(ComponentName componentName) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "VirtualDnsConnection:onBindingDied is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("VirtualDnsConnection:onBindingDied " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mVirtualDnsProxyInterface = null;
            try {
                KnoxNetworkFilterService.this.mDefaultDnsProxyInterface.vpnMessengerForDnsQuery((IBinder) null);
            } catch (RemoteException | NullPointerException unused) {
            }
            if (KnoxNetworkFilterService.this.mVirtualDnsConnectionList.containsKey(Integer.valueOf(this.userId))) {
                KnoxNetworkFilterService.this.mContext.unbindService((ServiceConnection) KnoxNetworkFilterService.this.mVirtualDnsConnectionList.get(Integer.valueOf(this.userId)));
                KnoxNetworkFilterService.this.mVirtualDnsConnectionList.remove(Integer.valueOf(this.userId));
            }
            VirtualDnsConnection virtualDnsConnection = KnoxNetworkFilterService.this.new VirtualDnsConnection(this.userId);
            Intent intent = new Intent();
            intent.setClassName("com.samsung.android.knox.app.networkfilter", "com.samsung.android.knox.app.networkfilter.dns.VirtualDnsProxyService");
            if (KnoxNetworkFilterService.this.mContext.bindServiceAsUser(intent, virtualDnsConnection, 1073741829, new UserHandle(this.userId))) {
                KnoxNetworkFilterService.this.mVirtualDnsConnectionList.put(Integer.valueOf(this.userId), virtualDnsConnection);
            } else {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "failed to bind vpn process after onBinding died event");
            }
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "VirtualDnsConnection:onServiceConnected is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("VirtualDnsConnection:onServiceConnected " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mVirtualDnsProxyInterface = INetworkFilterProxy.Stub.asInterface(iBinder);
            CountDownLatch countDownLatch = this.countDownLatch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            try {
                KnoxNetworkFilterService.this.mDefaultDnsProxyInterface.vpnMessengerForDnsQuery(iBinder);
            } catch (RemoteException | NullPointerException unused) {
            }
        }
    }

    public class DefaultTcpConnection implements ServiceConnection {
        public CountDownLatch countDownLatch = null;
        public int userId;

        public DefaultTcpConnection(int i) {
            this.userId = i;
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceDisconnected(ComponentName componentName) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "DefaultTcpConnection:onServicedisconnected is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("DefaultTcpConnection:onServiceDisconnected " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mDefaultTcpProxyInterface = null;
            try {
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.flushAppGeneratedRedirectTcpConnRules();
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6TcpConnRules();
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.flushTcpRedirectRulesForCaptivePortal();
            } catch (NullPointerException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public synchronized void onBindingDied(ComponentName componentName) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "DefaultTcpConnection:onBindingDied is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("DefaultTcpConnection:onBindingDied " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mDefaultTcpProxyInterface = null;
            if (KnoxNetworkFilterService.this.mDefaultTcpConnectionList.containsKey(Integer.valueOf(this.userId))) {
                KnoxNetworkFilterService.this.mContext.unbindService((ServiceConnection) KnoxNetworkFilterService.this.mDefaultTcpConnectionList.get(Integer.valueOf(this.userId)));
                KnoxNetworkFilterService.this.mDefaultTcpConnectionList.remove(Integer.valueOf(this.userId));
            }
            DefaultTcpConnection defaultTcpConnection = KnoxNetworkFilterService.this.new DefaultTcpConnection(this.userId);
            Intent intent = new Intent();
            intent.setClassName("com.samsung.android.knox.app.networkfilter", "com.samsung.android.knox.app.networkfilter.tcp.DefaultTcpProxyService");
            if (KnoxNetworkFilterService.this.mContext.bindServiceAsUser(intent, defaultTcpConnection, 1073741829, new UserHandle(this.userId))) {
                KnoxNetworkFilterService.this.mDefaultTcpConnectionList.put(Integer.valueOf(this.userId), defaultTcpConnection);
            } else {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "failed to bind tcp process after onBinding died event");
            }
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "DefaultTcpConnection:onServiceConnected is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("DefaultTcpConnection:onServiceConnected " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mDefaultTcpProxyInterface = INetworkFilterProxy.Stub.asInterface(iBinder);
            CountDownLatch countDownLatch = this.countDownLatch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            String profilebyUserId = KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getProfilebyUserId(this.userId);
            if (profilebyUserId == null) {
                return;
            }
            KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getConfiguredProtocols(profilebyUserId);
            KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
            if (profileEntry == null) {
                return;
            }
            String configuredDestIpRange = KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getConfiguredDestIpRange(profilebyUserId, 2);
            String configuredDestIpRange2 = KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getConfiguredDestIpRange(profilebyUserId, 10);
            try {
                KnoxNetworkFilterService.this.mDefaultTcpProxyInterface.setConfig(profilebyUserId, KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getRulesConfig(profilebyUserId));
                KnoxNetworkFilterService.this.mDefaultTcpProxyInterface.registerRemoteProxyAddr(profilebyUserId, KnoxNetworkFilterService.this.mKnoxNwFilterHelper.retrieveListenersFromCache(profilebyUserId));
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.denyUnAuthorizedPackets(KnoxNetworkFilterService.this.mNwFilterProxyAppUid, "tcp", KnoxNetworkFilterService.this.mDefaultTcpProxyInterface.getListener(profilebyUserId));
                if (KnoxNetworkFilterService.this.mKnoxNwFilterHelper.retrieveProfileState(profilebyUserId) == 3) {
                    if (profileEntry.isV4TcpConfigured()) {
                        KnoxNetworkFilterService.this.mKnoxNwFilterFw.redirectAppGeneratedTcpConn(this.userId, configuredDestIpRange, KnoxNetworkFilterService.this.mDefaultTcpProxyInterface.startProxyServer());
                        KnoxNetworkFilterService.this.getOemNetdService().enablePortInfoEntries(2, 6, true);
                    }
                    if (profileEntry.isV6TcpConfigured()) {
                        KnoxNetworkFilterService.this.mKnoxNwFilterFw.redirectAppGeneratedV6TcpConn(this.userId, configuredDestIpRange2, KnoxNetworkFilterService.this.mDefaultTcpProxyInterface.startV6ProxyServer());
                        KnoxNetworkFilterService.this.getOemNetdService().enablePortInfoEntries(10, 6, true);
                    }
                    int startHttpProxyServer = KnoxNetworkFilterService.this.mDefaultTcpProxyInterface.startHttpProxyServer();
                    String[] browserAppList = KnoxNetworkFilterService.this.mDefaultTcpProxyInterface.getBrowserAppList();
                    KnoxNetworkFilterService.this.getOemNetdService().setHttpProxyPort(startHttpProxyServer);
                    KnoxNetworkFilterService.this.mKnoxNwFilterHelper.applyHttpProxyConfiguration(profilebyUserId, browserAppList, true, this.userId, startHttpProxyServer);
                    for (String str : browserAppList) {
                        int uIDForPackage = KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getUIDForPackage(this.userId, str);
                        if (uIDForPackage > 0) {
                            KnoxNetworkFilterService.this.mKnoxNwFilterFw.applyTcpRedirectRulesForCaptivePortal(uIDForPackage, startHttpProxyServer);
                        }
                    }
                }
            } catch (RemoteException | NullPointerException unused) {
            }
        }
    }

    public class DefaultUdpConnection implements ServiceConnection {
        public CountDownLatch countDownLatch = null;
        public int userId;

        public DefaultUdpConnection(int i) {
            this.userId = i;
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceDisconnected(ComponentName componentName) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "DefaultUdpConnection:onServicedisconnected is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("DefaultUdpConnection:onServiceDisconnected " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mDefaultUdpProxyInterface = null;
            try {
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.flushAppGeneratedRedirectUdpConnRules();
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6UdpConnRules();
            } catch (NullPointerException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public synchronized void onBindingDied(ComponentName componentName) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "DefaultUdpConnection:onBindingDied is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("DefaultUdpConnection:onBindingDied " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mDefaultUdpProxyInterface = null;
            if (KnoxNetworkFilterService.this.mDefaultUdpConnectionList.containsKey(Integer.valueOf(this.userId))) {
                KnoxNetworkFilterService.this.mContext.unbindService((ServiceConnection) KnoxNetworkFilterService.this.mDefaultUdpConnectionList.get(Integer.valueOf(this.userId)));
                KnoxNetworkFilterService.this.mDefaultUdpConnectionList.remove(Integer.valueOf(this.userId));
            }
            DefaultUdpConnection defaultUdpConnection = KnoxNetworkFilterService.this.new DefaultUdpConnection(this.userId);
            Intent intent = new Intent();
            intent.setClassName("com.samsung.android.knox.app.networkfilter", "com.samsung.android.knox.app.networkfilter.udp.DefaultUdpProxyService");
            if (KnoxNetworkFilterService.this.mContext.bindServiceAsUser(intent, defaultUdpConnection, 1073741829, new UserHandle(this.userId))) {
                KnoxNetworkFilterService.this.mDefaultUdpConnectionList.put(Integer.valueOf(this.userId), defaultUdpConnection);
            } else {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "failed to bind udp process after onBinding died event");
            }
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "DefaultUdpConnection:onServiceConnected is reached ");
            KnoxNetworkFilterService.this.mLocalProxyStatus.add("DefaultUdpConnection:onServiceConnected " + System.currentTimeMillis());
            KnoxNetworkFilterService.this.mDefaultUdpProxyInterface = INetworkFilterProxy.Stub.asInterface(iBinder);
            CountDownLatch countDownLatch = this.countDownLatch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            String profilebyUserId = KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getProfilebyUserId(this.userId);
            if (profilebyUserId == null) {
                return;
            }
            KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getConfiguredProtocols(profilebyUserId);
            KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
            if (profileEntry == null) {
                return;
            }
            String configuredDestIpRange = KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getConfiguredDestIpRange(profilebyUserId, 2);
            String configuredDestIpRange2 = KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getConfiguredDestIpRange(profilebyUserId, 10);
            try {
                KnoxNetworkFilterService.this.mDefaultUdpProxyInterface.setConfig(profilebyUserId, KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getRulesConfig(profilebyUserId));
                KnoxNetworkFilterService.this.mDefaultUdpProxyInterface.registerRemoteProxyAddr(profilebyUserId, KnoxNetworkFilterService.this.mKnoxNwFilterHelper.retrieveListenersFromCache(profilebyUserId));
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.denyUnAuthorizedPackets(KnoxNetworkFilterService.this.mNwFilterProxyAppUid, "udp", KnoxNetworkFilterService.this.mDefaultUdpProxyInterface.getListener(profilebyUserId));
                if (KnoxNetworkFilterService.this.mKnoxNwFilterHelper.retrieveProfileState(profilebyUserId) == 3) {
                    if (profileEntry.isV4UdpConfigured()) {
                        KnoxNetworkFilterService.this.mKnoxNwFilterFw.redirectAppGeneratedUdpConn(this.userId, configuredDestIpRange, KnoxNetworkFilterService.this.mDefaultUdpProxyInterface.startProxyServer());
                    }
                    if (profileEntry.isV6UdpConfigured()) {
                        KnoxNetworkFilterService.this.mKnoxNwFilterFw.redirectAppGeneratedV6UdpConn(this.userId, configuredDestIpRange2, KnoxNetworkFilterService.this.mDefaultUdpProxyInterface.startV6ProxyServer());
                        KnoxNetworkFilterService.this.getOemNetdService().enablePortInfoEntries(10, 17, true);
                    }
                }
            } catch (RemoteException | NullPointerException unused) {
            }
        }
    }

    public class NetworkCallback extends ConnectivityManager.NetworkCallback {
        public NetworkCallback() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "onLinkPropertiesChanged being called for netId " + network.getNetId() + " for interface " + linkProperties.getInterfaceName());
            Iterator it = KnoxNetworkFilterService.this.mKnoxNwFilterHelper.getUserIdList().iterator();
            while (it.hasNext()) {
                KnoxNetworkFilterService.this.mKnoxNwFilterFw.exemptDnsRulesFromNat(((Integer) it.next()).intValue(), linkProperties.getDnsServers());
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "onLost being called for netId " + network.getNetId());
            KnoxNetworkFilterService.this.mKnoxNwFilterFw.flushExemptDnsRulesFromNat();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    KnoxNetworkFilterService.this.getOemNetdService().clearNetworkFilterEntries();
                } catch (RemoteException unused) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "RemoteException");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            Log.d("knoxNwFilter-KnoxNetworkFilterService", "onAvailable being called for netId " + network.getNetId());
        }
    }

    public final void registerSystemDefaultNetworkCallback() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        NetworkCallback networkCallback = new NetworkCallback();
        this.mDefaultNetworkCallback = networkCallback;
        try {
            try {
                this.mCm.registerSystemDefaultNetworkCallback(networkCallback, this.mHandler);
            } catch (RuntimeException e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "Failed to register system default network callback " + Log.getStackTraceString(e));
                this.mDefaultNetworkCallback = null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterSystemDefaultNetworkCallback() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                NetworkCallback networkCallback = this.mDefaultNetworkCallback;
                if (networkCallback != null) {
                    this.mCm.unregisterNetworkCallback(networkCallback);
                }
                this.mDefaultNetworkCallback = null;
            } catch (RuntimeException e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "Failed to unregister system default network callback " + Log.getStackTraceString(e));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int setupNetworkDns() {
        Exception e;
        boolean z;
        INetd iNetd;
        connectNativeNetdService();
        int i = -1;
        boolean z2 = true;
        do {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            boolean z3 = false;
            try {
                try {
                    try {
                        i = this.mNetIdManager.reserveNetId();
                        iNetd = this.mNetdService;
                    } catch (IllegalStateException e2) {
                        Log.e("knoxNwFilter-KnoxNetworkFilterService", "setupNetworkDns() - Failed creating new network.", e2);
                    }
                } catch (Exception e3) {
                    z3 = z2;
                    e = e3;
                    z = false;
                }
                if (iNetd == null) {
                    return i;
                }
                try {
                    iNetd.networkCreateVpn(i, false);
                    this.mDnsResolverAdapter.createNetworkCache(i);
                    try {
                        LinkProperties linkProperties = new LinkProperties();
                        linkProperties.addDnsServer(InetAddress.getByName("127.0.0.1"));
                        EnforceDnsManager enforceDnsManager = new EnforceDnsManager(this.mContext, this.mDnsResolverAdapter);
                        this.mDnsManager = enforceDnsManager;
                        enforceDnsManager.noteDnsServersForNetwork(i, linkProperties);
                        this.mDnsManager.updateDnsUidForNetwork(i, false);
                        this.mDnsManager.updateTransportsForNetwork(i, new int[]{0, 1, 3, 4});
                        this.mDnsManager.flushVmDnsCache();
                    } catch (Exception e4) {
                        z = true;
                        z3 = z2;
                        e = e4;
                    }
                    try {
                        Log.d("knoxNwFilter-KnoxNetworkFilterService", "setupNetworkDns() - Network created id = " + i);
                    } catch (Exception e5) {
                        e = e5;
                        z = true;
                        if (z) {
                            Log.e("knoxNwFilter-KnoxNetworkFilterService", "setupNetworkDns() - Failed to set dns for network " + i, e);
                            try {
                                INetd iNetd2 = this.mNetdService;
                                if (iNetd2 != null) {
                                    iNetd2.networkDestroy(i);
                                }
                            } catch (Exception e6) {
                                Log.e("knoxNwFilter-KnoxNetworkFilterService", "setupNetworkDns() - Failed to remove network", e6);
                            }
                        } else {
                            Log.e("knoxNwFilter-KnoxNetworkFilterService", "setupNetworkDns() - Failed to create new network with id provided.", e);
                        }
                        this.mNetIdManager.releaseNetId(i);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        z2 = z3;
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    z2 = z3;
                } catch (RemoteException | ServiceSpecificException e7) {
                    Log.e("knoxNwFilter-KnoxNetworkFilterService", "Error creating private network: " + e7.getMessage());
                    this.mNetIdManager.releaseNetId(i);
                    return -1;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } while (z2);
        return i;
    }

    public final void releaseNwFilterNetId(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            INetd iNetd = this.mNetdService;
            if (iNetd != null) {
                iNetd.networkDestroy(i);
            }
        } catch (RemoteException | ServiceSpecificException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        this.mNetIdManager.releaseNetId(i);
    }

    public final void exemptUidFromNwFilterRange(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new UidRangeParcel(i, i2));
            getOemNetdService().exemptUidFromNwFilterRange((UidRangeParcel[]) arrayList.toArray(new UidRangeParcel[arrayList.size()]));
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (i == i2) {
            this.mKnoxNwFilterFw.allowAppGeneratedPackets(i);
        }
    }

    public final void removeExemptUidFromNwFilterRange(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new UidRangeParcel(i, i2));
            getOemNetdService().removeExemptUidFromNwFilterRange((UidRangeParcel[]) arrayList.toArray(new UidRangeParcel[arrayList.size()]));
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (i == i2) {
            this.mKnoxNwFilterFw.removeAppGeneratedPackets(i);
        }
    }

    public final void initializeModules() {
        if (this.mKnoxNwFilterHelper.getProfileList().isEmpty()) {
            return;
        }
        this.mKnoxNwFilterFw = KnoxNetworkFilterFirewall.getInstance();
    }

    public final void initializeListeners() {
        try {
            if (this.mKnoxNwFilterHelper.isRegisterDbEmpty()) {
                return;
            }
            registerFilterList();
        } catch (Exception unused) {
        }
    }

    public final synchronized void handleVendorPackageUninstall(int i, String str) {
        String[] strArr;
        int userId = UserHandle.getUserId(i);
        String profilebyUserId = this.mKnoxNwFilterHelper.getProfilebyUserId(userId);
        if (profilebyUserId == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SystemProperties.set("net.redirect_socket_calls.hooked", "false");
            getOemNetdService().enableIpOptionModification(false);
            getOemNetdService().enablePortInfoEntries(2, 6, false);
            getOemNetdService().enablePortInfoEntries(10, 6, false);
            getOemNetdService().enablePortInfoEntries(10, 17, false);
            getOemNetdService().clearNetworkFilterEntries();
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        unregisterSystemDefaultNetworkCallback();
        try {
            this.mDefaultDnsProxyInterface.stopProxyServer();
        } catch (RemoteException | NullPointerException unused2) {
        }
        try {
            this.mDefaultTcpProxyInterface.stopProxyServer();
        } catch (RemoteException | NullPointerException unused3) {
        }
        try {
            this.mDefaultUdpProxyInterface.stopProxyServer();
        } catch (RemoteException | NullPointerException unused4) {
        }
        try {
            this.mDefaultDnsProxyInterface.stopV6ProxyServer();
        } catch (RemoteException | NullPointerException unused5) {
        }
        try {
            this.mDefaultTcpProxyInterface.stopV6ProxyServer();
        } catch (RemoteException | NullPointerException unused6) {
        }
        try {
            this.mDefaultUdpProxyInterface.stopV6ProxyServer();
        } catch (RemoteException | NullPointerException unused7) {
        }
        try {
            this.mDefaultTcpProxyInterface.stopHttpProxyServer();
            strArr = this.mDefaultTcpProxyInterface.getBrowserAppList();
            try {
                getOemNetdService().setHttpProxyPort(-1);
            } catch (RemoteException | NullPointerException unused8) {
            }
        } catch (RemoteException | NullPointerException unused9) {
            strArr = null;
        }
        this.mKnoxNwFilterHelper.applyHttpProxyConfiguration(profilebyUserId, strArr, false, userId, -1);
        this.mKnoxNwFilterFw.flushTcpRedirectRulesForCaptivePortal();
        try {
            releaseNwFilterNetId(getOemNetdService().getNwFilterNetId());
            getOemNetdService().removeUserFromNwFilterRange(userId);
            getOemNetdService().setNwFilterNetId(-1);
            getOemNetdService().clearKnoxNwFilterProxyEntries();
        } catch (RemoteException unused10) {
        }
        for (String str2 : KnoxNetworkFilterConstants.NW_FILTER_EXEMPT_LIST) {
            int uIDForPackage = this.mKnoxNwFilterHelper.getUIDForPackage(userId, str2);
            if (uIDForPackage != -1) {
                removeExemptUidFromNwFilterRange(uIDForPackage, uIDForPackage);
            }
        }
        removeExemptUidFromNwFilterRange(i, i);
        if (userId == 0) {
            removeExemptUidFromNwFilterRange(1, 9999);
        }
        KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
        if (profileEntry != null) {
            Iterator it = profileEntry.getVpnClientUidList().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                removeExemptUidFromNwFilterRange(intValue, intValue);
            }
        }
        this.mKnoxNwFilterFw.flushAppGeneratedDOTBlockRules();
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectRules();
        this.mKnoxNwFilterFw.flushRedirectDnsQueryRules();
        this.mKnoxNwFilterFw.flushExemptDnsRulesFromNat();
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectTcpConnRules();
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6TcpConnRules();
        this.mKnoxNwFilterFw.flushUdpPacketExemptRules(2);
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectUdpConnRules();
        this.mKnoxNwFilterFw.flushUdpPacketExemptRules(10);
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6UdpConnRules();
        try {
            this.mDefaultDnsProxyInterface.setConfig(profilebyUserId, (String) null);
            this.mDefaultTcpProxyInterface.setConfig(profilebyUserId, (String) null);
            this.mDefaultUdpProxyInterface.setConfig(profilebyUserId, (String) null);
        } catch (RemoteException | NullPointerException unused11) {
        }
        try {
            this.mKnoxNwFilterFw.flushUnAuthorizedPackets();
        } catch (NullPointerException unused12) {
        }
        this.mKnoxNwFilterHelper.removeVendorInfoFromStorage(profilebyUserId);
    }

    public final synchronized void handleActionUserRemoved(Bundle bundle) {
        int i = bundle.getInt("android.intent.extra.user_handle", -10000);
        Log.d("knoxNwFilter-KnoxNetworkFilterService", "user removal is triggered for user " + i);
        String profilebyUserId = this.mKnoxNwFilterHelper.getProfilebyUserId(i);
        if (profilebyUserId == null) {
            return;
        }
        KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
        if (profileEntry == null) {
            return;
        }
        profileEntry.getPackageName();
        int packageUid = profileEntry.getPackageUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SystemProperties.set("net.redirect_socket_calls.hooked", "false");
            getOemNetdService().enableIpOptionModification(false);
            getOemNetdService().enablePortInfoEntries(2, 6, false);
            getOemNetdService().enablePortInfoEntries(10, 6, false);
            getOemNetdService().enablePortInfoEntries(10, 17, false);
            getOemNetdService().clearNetworkFilterEntries();
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        unregisterSystemDefaultNetworkCallback();
        try {
            this.mDefaultDnsProxyInterface.stopProxyServer();
        } catch (RemoteException | NullPointerException unused2) {
        }
        try {
            this.mDefaultTcpProxyInterface.stopProxyServer();
        } catch (RemoteException | NullPointerException unused3) {
        }
        try {
            this.mDefaultUdpProxyInterface.stopProxyServer();
        } catch (RemoteException | NullPointerException unused4) {
        }
        try {
            this.mDefaultDnsProxyInterface.stopV6ProxyServer();
        } catch (RemoteException | NullPointerException unused5) {
        }
        try {
            this.mDefaultTcpProxyInterface.stopV6ProxyServer();
        } catch (RemoteException | NullPointerException unused6) {
        }
        try {
            this.mDefaultUdpProxyInterface.stopV6ProxyServer();
        } catch (RemoteException | NullPointerException unused7) {
        }
        try {
            this.mDefaultTcpProxyInterface.stopHttpProxyServer();
            getOemNetdService().setHttpProxyPort(-1);
        } catch (RemoteException | NullPointerException unused8) {
        }
        try {
            releaseNwFilterNetId(getOemNetdService().getNwFilterNetId());
            getOemNetdService().removeUserFromNwFilterRange(i);
            getOemNetdService().setNwFilterNetId(-1);
            getOemNetdService().clearKnoxNwFilterProxyEntries();
        } catch (RemoteException unused9) {
        }
        for (String str : KnoxNetworkFilterConstants.NW_FILTER_EXEMPT_LIST) {
            int uIDForPackage = this.mKnoxNwFilterHelper.getUIDForPackage(i, str);
            if (uIDForPackage != -1) {
                removeExemptUidFromNwFilterRange(uIDForPackage, uIDForPackage);
            }
        }
        removeExemptUidFromNwFilterRange(packageUid, packageUid);
        if (i == 0) {
            removeExemptUidFromNwFilterRange(1, 9999);
        }
        Iterator it = profileEntry.getVpnClientUidList().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            removeExemptUidFromNwFilterRange(intValue, intValue);
        }
        this.mKnoxNwFilterFw.flushAppGeneratedDOTBlockRules();
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectRules();
        this.mKnoxNwFilterFw.flushRedirectDnsQueryRules();
        this.mKnoxNwFilterFw.flushExemptDnsRulesFromNat();
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectTcpConnRules();
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6TcpConnRules();
        this.mKnoxNwFilterFw.flushUdpPacketExemptRules(2);
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectUdpConnRules();
        this.mKnoxNwFilterFw.flushUdpPacketExemptRules(10);
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6UdpConnRules();
        this.mKnoxNwFilterFw.flushTcpRedirectRulesForCaptivePortal();
        try {
            this.mDefaultDnsProxyInterface.setConfig(profilebyUserId, (String) null);
            this.mDefaultTcpProxyInterface.setConfig(profilebyUserId, (String) null);
            this.mDefaultUdpProxyInterface.setConfig(profilebyUserId, (String) null);
        } catch (RemoteException | NullPointerException unused10) {
        }
        this.mKnoxNwFilterFw.flushUnAuthorizedPackets();
        this.mKnoxNwFilterHelper.removeVendorInfoFromStorage(profilebyUserId);
    }

    public final void handleBindingDiedEvent(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SystemProperties.set("net.redirect_socket_calls.hooked", "false");
            getOemNetdService().enableIpOptionModification(false);
            getOemNetdService().enablePortInfoEntries(2, 6, false);
            getOemNetdService().enablePortInfoEntries(10, 6, false);
            getOemNetdService().enablePortInfoEntries(10, 17, false);
            getOemNetdService().clearNetworkFilterEntries();
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        unregisterSystemDefaultNetworkCallback();
        String profilebyUserId = this.mKnoxNwFilterHelper.getProfilebyUserId(i);
        int vendorUidByProfile = this.mKnoxNwFilterHelper.getVendorUidByProfile(profilebyUserId);
        String packageNameForUid = this.mKnoxNwFilterHelper.getPackageNameForUid(vendorUidByProfile);
        int uIDForPackage = this.mKnoxNwFilterHelper.getUIDForPackage(i, "com.samsung.android.knox.app.networkfilter");
        try {
            releaseNwFilterNetId(getOemNetdService().getNwFilterNetId());
            getOemNetdService().removeUserFromNwFilterRange(i);
            getOemNetdService().setNwFilterNetId(-1);
            getOemNetdService().clearKnoxNwFilterProxyEntries();
        } catch (RemoteException unused2) {
            Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleBindingDiedEvent:error occured while communicating to remote oemnetd service");
        }
        this.mKnoxNwFilterFw.flushUnAuthorizedPackets();
        for (String str : KnoxNetworkFilterConstants.NW_FILTER_EXEMPT_LIST) {
            int uIDForPackage2 = this.mKnoxNwFilterHelper.getUIDForPackage(i, str);
            if (uIDForPackage2 != -1) {
                removeExemptUidFromNwFilterRange(uIDForPackage2, uIDForPackage2);
            }
        }
        removeExemptUidFromNwFilterRange(vendorUidByProfile, vendorUidByProfile);
        if (i == 0) {
            removeExemptUidFromNwFilterRange(1, 9999);
        }
        KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(profilebyUserId);
        if (profileEntry != null) {
            Iterator it = profileEntry.getVpnClientUidList().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                removeExemptUidFromNwFilterRange(intValue, intValue);
            }
        }
        this.mKnoxNwFilterFw.flushAppGeneratedDOTBlockRules();
        this.mKnoxNwFilterFw.flushExemptDnsRulesFromNat();
        this.mKnoxNwFilterFw.flushRedirectDnsQueryRules();
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectTcpConnRules();
        this.mKnoxNwFilterFw.flushUdpPacketExemptRules(2);
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectUdpConnRules();
        this.mKnoxNwFilterFw.flushRedirectV6DnsQueryRules();
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6TcpConnRules();
        this.mKnoxNwFilterFw.flushUdpPacketExemptRules(10);
        this.mKnoxNwFilterFw.flushAppGeneratedRedirectV6UdpConnRules();
        String[] strArr = null;
        if (profileEntry != null) {
            try {
                strArr = (String[]) profileEntry.getBrowserAppList().toArray(new String[profileEntry.getBrowserAppList().size()]);
            } catch (RemoteException unused3) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleBindingDiedEvent:tcp:http:error occured while communicating to remote service");
            } catch (NullPointerException unused4) {
                Log.e("knoxNwFilter-KnoxNetworkFilterService", "handleBindingDiedEvent:tcp:http:error occured remote service null");
            }
        }
        getOemNetdService().setHttpProxyPort(-1);
        this.mKnoxNwFilterHelper.applyHttpProxyConfiguration(profilebyUserId, strArr, false, i, -1);
        this.mKnoxNwFilterFw.flushTcpRedirectRulesForCaptivePortal();
        this.mKnoxNwFilterHelper.addOrRemoveAppsFromBatteryOptimization("com.samsung.android.knox.app.networkfilter", uIDForPackage, false);
        this.mKnoxNwFilterHelper.addOrRemoveSystemAppFromDataSaverWhitelist(uIDForPackage, false);
        this.mKnoxNwFilterHelper.updateProfileState(profilebyUserId, 5);
        sendStatusIntent(i, 2, packageNameForUid, -10);
        sendEMMStatusIntent(i, 2, this.mKnoxNwFilterHelper.getPackageNameForUid(this.mKnoxNwFilterHelper.getAdminIdForUser(i)), -10);
    }

    public synchronized void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("KnoxNetworkFilterProfileInfo: Permission Denial: can't dump PersonaManager from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " without permission android.permission.DUMP");
            return;
        }
        printWriter.print("KnoxNetworkFilterProfileInfo: The profile info being printed at time " + System.currentTimeMillis() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        Collection<KnoxNetworkFilterProfileInfo> profileEntries = KnoxNetworkFilterProfileInfo.getProfileEntries();
        if (profileEntries != null) {
            for (KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo : profileEntries) {
                printWriter.println("KnoxNetworkFilterProfileInfo: profileName: " + knoxNetworkFilterProfileInfo.getProfileName() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                printWriter.println("KnoxNetworkFilterProfileInfo: RulesConfig: " + knoxNetworkFilterProfileInfo.getRulesConfig() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                printWriter.println("KnoxNetworkFilterProfileInfo: socketConfig " + knoxNetworkFilterProfileInfo.getSocketConfig() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                printWriter.println("KnoxNetworkFilterProfileInfo: packageName " + knoxNetworkFilterProfileInfo.getPackageName() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                printWriter.println("KnoxNetworkFilterProfileInfo: packageUid " + knoxNetworkFilterProfileInfo.getPackageUid() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                printWriter.println("KnoxNetworkFilterProfileInfo: state: " + knoxNetworkFilterProfileInfo.getState() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                Iterator it = knoxNetworkFilterProfileInfo.getVpnClientUidList().iterator();
                while (it.hasNext()) {
                    printWriter.println("KnoxNetworkFilterProfileInfo: vpn client uid: " + ((Integer) it.next()).intValue() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                }
            }
        }
        Iterator it2 = this.mKnoxNwFilterHelper.getRegisterInfoFromDatabase().iterator();
        while (it2.hasNext()) {
            printWriter.println("KnoxNetworkFilterProfileInfo: register app info: " + ((String) it2.next()) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        try {
            printWriter.println("KnoxNetworkFilterProfileInfo: netId value is " + getOemNetdService().getNwFilterNetId() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        } catch (RemoteException | NullPointerException unused) {
        }
        List<ActivityManager.ProcessErrorStateInfo> processesInErrorState = getAMSInstance().getProcessesInErrorState();
        if (processesInErrorState != null) {
            for (ActivityManager.ProcessErrorStateInfo processErrorStateInfo : processesInErrorState) {
                String str = processErrorStateInfo.processName;
                if (str != null && str.equalsIgnoreCase("com.samsung.android.knox.app.networkfilter")) {
                    printWriter.print("KnoxNetworkFilterProfileInfo: local nwfilter proxy apk error condition is " + processErrorStateInfo.condition + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    printWriter.print("KnoxNetworkFilterProfileInfo: local nwfilter proxy apk error longMsg is " + processErrorStateInfo.longMsg + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    printWriter.print("KnoxNetworkFilterProfileInfo: local nwfilter proxy apk error shortMsg is " + processErrorStateInfo.shortMsg + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    printWriter.print("KnoxNetworkFilterProfileInfo: local nwfilter proxy apk error stackTrace is " + processErrorStateInfo.stackTrace + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    printWriter.print("KnoxNetworkFilterProfileInfo: local nwfilter proxy apk error pid is " + processErrorStateInfo.pid + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    printWriter.print("KnoxNetworkFilterProfileInfo: local nwfilter proxy apk apk error uid is " + processErrorStateInfo.uid + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                }
            }
        }
        if (this.mDefaultDnsProxyInterface != null) {
            printWriter.print("KnoxNetworkFilterProfileInfo: local default DNS proxy server address " + this.mDefaultDnsProxyInterface.getLocalProxyAddress() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default DNS proxy server port " + this.mDefaultDnsProxyInterface.getLocalProxyPort() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default DNS proxy server thread is active " + this.mDefaultDnsProxyInterface.isProxyThreadRunning() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default DNS proxy server thread state " + this.mDefaultDnsProxyInterface.getProxythreadStatus() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default DNS proxy server alive " + this.mDefaultDnsProxyInterface.isProxyThreadAlive() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default DNS proxy server address " + this.mDefaultDnsProxyInterface.getV6LocalProxyAddress() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default DNS proxy server port " + this.mDefaultDnsProxyInterface.getV6LocalProxyPort() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default DNS proxy server thread is active " + this.mDefaultDnsProxyInterface.isV6ProxyThreadRunning() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default DNS proxy server thread state " + this.mDefaultDnsProxyInterface.getV6ProxythreadStatus() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default DNS proxy server alive " + this.mDefaultDnsProxyInterface.isV6ProxyThreadAlive() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        if (this.mDefaultTcpProxyInterface != null) {
            printWriter.print("KnoxNetworkFilterProfileInfo: local default tcp proxy server address " + this.mDefaultTcpProxyInterface.getLocalProxyAddress() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default tcp proxy server port " + this.mDefaultTcpProxyInterface.getLocalProxyPort() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default tcp proxy server thread is active " + this.mDefaultTcpProxyInterface.isProxyThreadRunning() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default tcp proxy server thread state " + this.mDefaultTcpProxyInterface.getProxythreadStatus() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default tcp proxy server alive " + this.mDefaultTcpProxyInterface.isProxyThreadAlive() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default tcp proxy server address " + this.mDefaultTcpProxyInterface.getV6LocalProxyAddress() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default tcp proxy server port " + this.mDefaultTcpProxyInterface.getV6LocalProxyPort() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default tcp proxy server thread is active " + this.mDefaultTcpProxyInterface.isV6ProxyThreadRunning() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default tcp proxy server thread state " + this.mDefaultTcpProxyInterface.getV6ProxythreadStatus() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default tcp proxy server alive " + this.mDefaultTcpProxyInterface.isV6ProxyThreadAlive() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: http:local default tcp proxy server address " + this.mDefaultTcpProxyInterface.getHttpLocalProxyAddress() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: http:local default tcp proxy server port " + this.mDefaultTcpProxyInterface.getHttpLocalProxyPort() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: http:local default tcp proxy server thread is active " + this.mDefaultTcpProxyInterface.isHttpProxyThreadRunning() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: http:local default tcp proxy server thread state " + this.mDefaultTcpProxyInterface.getHttpProxythreadStatus() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: http:local default tcp proxy server alive " + this.mDefaultTcpProxyInterface.isHttpProxyThreadAlive() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        if (this.mDefaultUdpProxyInterface != null) {
            printWriter.print("KnoxNetworkFilterProfileInfo: local default udp proxy server address " + this.mDefaultUdpProxyInterface.getLocalProxyAddress() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default udp proxy server port " + this.mDefaultUdpProxyInterface.getLocalProxyPort() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default udp proxy server thread is active " + this.mDefaultUdpProxyInterface.isProxyThreadRunning() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default udp proxy server thread state " + this.mDefaultUdpProxyInterface.getProxythreadStatus() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: local default udp proxy server alive " + this.mDefaultUdpProxyInterface.isProxyThreadAlive() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default udp proxy server address " + this.mDefaultUdpProxyInterface.getV6LocalProxyAddress() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default udp proxy server port " + this.mDefaultUdpProxyInterface.getV6LocalProxyPort() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default udp proxy server thread is active " + this.mDefaultUdpProxyInterface.isV6ProxyThreadRunning() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default udp proxy server thread state " + this.mDefaultUdpProxyInterface.getV6ProxythreadStatus() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.print("KnoxNetworkFilterProfileInfo: V6:local default udp proxy server alive " + this.mDefaultUdpProxyInterface.isV6ProxyThreadAlive() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        Iterator it3 = this.mLocalProxyStatus.iterator();
        while (it3.hasNext()) {
            printWriter.print("KnoxNetworkFilterProfileInfo: " + ((String) it3.next()) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        printWriter.print("KnoxNetworkFilterProfileInfo: version " + this.mKnoxNwFilterHelper.getVersionCode(0, "com.samsung.android.knox.app.networkfilter") + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
}
