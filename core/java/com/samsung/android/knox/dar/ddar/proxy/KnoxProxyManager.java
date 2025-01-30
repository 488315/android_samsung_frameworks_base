package com.samsung.android.knox.dar.ddar.proxy;

import android.content.Context;
import android.p009os.Bundle;
import android.p009os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.DualDarConstants;
import com.samsung.android.knox.dar.ddar.proxy.IProxyService;
import com.samsung.android.knox.dar.ddar.securesession.SecureClient;

/* loaded from: classes5.dex */
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
    private static KnoxProxyManager mInstance = null;
    private IProxyService _service = null;
    private Context mContext;

    private KnoxProxyManager(Context context) {
        this.mContext = null;
        this.mContext = context;
    }

    public static synchronized KnoxProxyManager getInstance(Context context) {
        KnoxProxyManager knoxProxyManager;
        synchronized (KnoxProxyManager.class) {
            if (mInstance == null) {
                mInstance = new KnoxProxyManager(context);
            }
            knoxProxyManager = mInstance;
        }
        return knoxProxyManager;
    }

    private synchronized IProxyService getService() {
        try {
            if (this._service == null) {
                this._service = IProxyService.Stub.asInterface(ServiceManager.getService(PROXY_SERVICE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this._service == null) {
            Log.m96e(TAG, "Error: DualDAR Communication Proxy Service Not Found.");
        }
        return this._service;
    }

    public boolean registerAgentByAction(String agentName, int userId, String packageName, String actionName) {
        try {
            IProxyService service = getService();
            if (service != null) {
                return service.registerAgentByAction(agentName, userId, packageName, actionName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.m96e(TAG, "Error: registerAgentByAction failed. agentName = " + agentName + ", userId = " + userId + ", packageName = " + packageName + ",actionName = " + actionName);
        return false;
    }

    public boolean registerAgentByMetadata(String agentName, int userId, String packageName, String metadata) {
        try {
            IProxyService service = getService();
            if (service != null) {
                return service.registerAgentByMetadata(agentName, userId, packageName, metadata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.m96e(TAG, "Error: registerAgentByMetadata failed. agentName = " + agentName + ", userId = " + userId + ", packageName = " + packageName + ",metadata = " + metadata);
        return false;
    }

    public void deregisterAgent(String agentName) {
        try {
            IProxyService service = getService();
            if (service != null) {
                service.deregisterAgent(agentName);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.m96e(TAG, "Error: deregisterAgent failed. agentName = " + agentName);
    }

    public Bundle relayMessage(String agentName, String svcName, String command, Bundle args) {
        try {
            IProxyService service = getService();
            if (service != null) {
                if (args == null) {
                    args = new Bundle();
                }
                args.putBoolean(IS_SECURE_API, false);
                return service.relay(agentName, svcName, command, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.m96e(TAG, "Error: relayMessage failed. agentName = " + agentName + ", svcName = " + svcName + ", command = " + command);
        return null;
    }

    public Bundle relayMessageSecurely(String agentName, String svcName, String command, Bundle args, SecureClient secureClient) {
        try {
            IProxyService service = getService();
            if (service != null) {
                if (args == null) {
                    args = new Bundle();
                }
                args.putBoolean(IS_SECURE_API, true);
                args.putString(ORIGINATING_SECURE_CLIENT_ID, secureClient.getClientId());
                return service.relay(agentName, svcName, command, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.m96e(TAG, "Error: relayMessage failed. agentName = " + agentName + ", svcName = " + svcName + ", command = " + command);
        return null;
    }

    public Bundle relayMessageAsync(String agentName, String svcName, String command, Bundle args) {
        try {
            IProxyService service = getService();
            if (service != null) {
                if (args == null) {
                    args = new Bundle();
                }
                args.putBoolean(IS_SECURE_API, false);
                return service.relayAsync(agentName, svcName, command, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.m96e(TAG, "Error: relayMessage failed. agentName = " + agentName + ", svcName = " + svcName + ", command = " + command);
        return null;
    }

    public synchronized SecureClient initializeSecureSession(String initClientAlias, String endClientAgentName, String endClientSvcName) throws Exception {
        SecureClient secureClient;
        try {
            secureClient = new SecureClient(initClientAlias);
            Log.m94d(TAG, "secure connection of " + initClientAlias + " w/ " + endClientSvcName);
            Bundle input = new Bundle();
            input.putString(SECURE_CLIENT_ID, secureClient.getClientId());
            input.putString(SECURE_CLIENT_PUB_KEY, secureClient.getPublicKeyString());
            Bundle ret = relayMessage(endClientAgentName, endClientSvcName, INITIALIZE_SECURE_SESSION, input);
            if (ret != null) {
                String taProxyPubKey = ret.getString(DualDarConstants.DUAL_DAR_RESPONSE);
                Log.m94d(TAG, "generating session key w/ " + endClientSvcName);
                secureClient.initializeSecureSession(endClientSvcName, taProxyPubKey);
            } else {
                throw new Exception("initializeSecureSession response null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.m96e(TAG, "initializeSecureSession failed");
            throw e;
        }
        return secureClient;
    }

    public void terminateSecureSession(SecureClient secureClient, String endClientAgentName, String endClientSvcName) throws Exception {
        if (secureClient != null) {
            try {
                Log.m94d(TAG, "destroying all session and private keys of: " + secureClient.getClientId());
                Bundle input = new Bundle();
                input.putString(SECURE_CLIENT_ID, secureClient.getClientId());
                relayMessage(endClientAgentName, endClientSvcName, TERMINATE_SECURE_SESSION, input);
                secureClient.destroy();
            } catch (Exception e) {
                e.printStackTrace();
                Log.m96e(TAG, "terminateSecureSession failed");
                throw e;
            }
        }
    }
}
