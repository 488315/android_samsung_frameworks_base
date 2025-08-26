package com.samsung.android.knox.dar.ddar.proxy;

import android.content.Context;
import android.os.Bundle;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.DualDarConstants;
import com.samsung.android.knox.dar.ddar.proxy.IProxyService;
import com.samsung.android.knox.dar.ddar.securesession.SecureClient;

/* loaded from: classes6.dex */
public class KnoxProxyManager {
    public static final String DDAR_CACHE_SERVICE = "DDAR_CACHE_SERVICE";
    private static final String INITIALIZE_SECURE_SESSION = "INITIALIZE_SECURE_SESSION";
    public static final String IS_SECURE_API = "IS_SECURE_API";
    public static final String ORIGINATING_SECURE_CLIENT_ID = "ORIGINATING_SECURE_CLIENT_ID";
    public static final String PROXY_SERVICE = "knox_adapter_service";
    private static final String SECURE_CLIENT_ID = "SECURE_CLIENT_ID";
    private static final String SECURE_CLIENT_PUB_KEY = "SECURE_CLIENT_PUB_KEY";
    public static final String SYSTEM_PROXY_AGENT = "SYSTEM_PROXY_AGENT";
    private static final String TAG = "DualDAR::ProxyManager";
    private static final String TERMINATE_SECURE_SESSION = "TERMINATE_SECURE_SESSION";
    private static KnoxProxyManager mInstance;
    private IProxyService _service = null;
    private Context mContext;

    private KnoxProxyManager(Context context) {
        this.mContext = context;
    }

    public static synchronized KnoxProxyManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new KnoxProxyManager(context);
        }
        return mInstance;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001c A[Catch: all -> 0x0012, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:11:0x0018, B:13:0x001c, B:14:0x0023, B:10:0x0015), top: B:19:0x0001, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized IProxyService getService() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this._service == null) {
            this._service = IProxyService.Stub.asInterface(ServiceManager.getService(PROXY_SERVICE));
            if (this._service == null) {
                Log.e(TAG, "Error: DualDAR Communication Proxy Service Not Found.");
            }
        } else {
            if (this._service == null) {
            }
        }
        return this._service;
    }

    public boolean registerAgentByAction(String str, int i, String str2, String str3) {
        try {
            IProxyService service = getService();
            if (service != null) {
                return service.registerAgentByAction(str, i, str2, str3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Error: registerAgentByAction failed. agentName = " + str + ", userId = " + i + ", packageName = " + str2 + ",actionName = " + str3);
        return false;
    }

    public boolean registerAgentByMetadata(String str, int i, String str2, String str3) {
        try {
            IProxyService service = getService();
            if (service != null) {
                return service.registerAgentByMetadata(str, i, str2, str3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Error: registerAgentByMetadata failed. agentName = " + str + ", userId = " + i + ", packageName = " + str2 + ",metadata = " + str3);
        return false;
    }

    public void deregisterAgent(String str) {
        try {
            IProxyService service = getService();
            if (service != null) {
                service.deregisterAgent(str);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Error: deregisterAgent failed. agentName = " + str);
    }

    public Bundle relayMessage(String str, String str2, String str3, Bundle bundle) {
        try {
            IProxyService service = getService();
            if (service != null) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putBoolean(IS_SECURE_API, false);
                return service.relay(str, str2, str3, bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Error: relayMessage failed. agentName = " + str + ", svcName = " + str2 + ", command = " + str3);
        return null;
    }

    public Bundle relayMessageSecurely(String str, String str2, String str3, Bundle bundle, SecureClient secureClient) {
        try {
            IProxyService service = getService();
            if (service != null) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putBoolean(IS_SECURE_API, true);
                bundle.putString(ORIGINATING_SECURE_CLIENT_ID, secureClient.getClientId());
                return service.relay(str, str2, str3, bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Error: relayMessage failed. agentName = " + str + ", svcName = " + str2 + ", command = " + str3);
        return null;
    }

    public Bundle relayMessageAsync(String str, String str2, String str3, Bundle bundle) {
        try {
            IProxyService service = getService();
            if (service != null) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putBoolean(IS_SECURE_API, false);
                return service.relayAsync(str, str2, str3, bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Error: relayMessage failed. agentName = " + str + ", svcName = " + str2 + ", command = " + str3);
        return null;
    }

    public synchronized SecureClient initializeSecureSession(String str, String str2, String str3) throws Exception {
        SecureClient secureClient;
        try {
            secureClient = new SecureClient(str);
            Log.d(TAG, "secure connection of " + str + " w/ " + str3);
            Bundle bundle = new Bundle();
            bundle.putString(SECURE_CLIENT_ID, secureClient.getClientId());
            bundle.putString(SECURE_CLIENT_PUB_KEY, secureClient.getPublicKeyString());
            Bundle bundleRelayMessage = relayMessage(str2, str3, INITIALIZE_SECURE_SESSION, bundle);
            if (bundleRelayMessage != null) {
                String string = bundleRelayMessage.getString(DualDarConstants.DUAL_DAR_RESPONSE);
                Log.d(TAG, "generating session key w/ " + str3);
                secureClient.initializeSecureSession(str3, string);
            } else {
                throw new Exception("initializeSecureSession response null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "initializeSecureSession failed");
            throw e;
        }
        return secureClient;
    }

    public void terminateSecureSession(SecureClient secureClient, String str, String str2) throws Exception {
        if (secureClient != null) {
            try {
                Log.d(TAG, "destroying all session and private keys of: " + secureClient.getClientId());
                Bundle bundle = new Bundle();
                bundle.putString(SECURE_CLIENT_ID, secureClient.getClientId());
                relayMessage(str, str2, TERMINATE_SECURE_SESSION, bundle);
                secureClient.destroy();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "terminateSecureSession failed");
                throw e;
            }
        }
    }
}
