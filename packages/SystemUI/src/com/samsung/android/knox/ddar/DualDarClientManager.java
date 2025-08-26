package com.samsung.android.knox.ddar;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgent;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;
import com.samsung.android.knox.dar.ddar.securesession.Wiper;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

/* loaded from: classes4.dex */
public class DualDarClientManager extends IProxyAgent.Stub {
    private static final String TAG = "DualDarClientManager";
    private static DualDarClientManager mInstance;
    private final DualDARClientAgentService mClientAgentService;
    private final Context mContext;

    class DualDARClientAgentService extends IProxyAgentService {
        private IDualDARClient mDualDARClient;

        public /* synthetic */ DualDARClientAgentService(IDualDARClient iDualDARClient, int i) {
            this(iDualDARClient);
        }

        /* JADX WARN: Removed duplicated region for block: B:41:0x009b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Bundle onMessage(int i, String str, Bundle bundle) {
            char c;
            Bundle bundle2 = new Bundle();
            bundle.setClassLoader(getClass().getClassLoader());
            String string = bundle.getString("ORIGINATING_SECURE_CLIENT_ID");
            try {
                switch (str.hashCode()) {
                    case -1796774992:
                        if (!str.equals("ON_WORKSPACE_DESTROY")) {
                            c = 65535;
                            break;
                        } else {
                            c = 3;
                            break;
                        }
                    case -1540060126:
                        if (str.equals("SET_RESET_PASSWORD_TOKEN")) {
                            c = 6;
                            break;
                        }
                        break;
                    case -1137042856:
                        if (str.equals("ON_PASSWORD2_CHANGE")) {
                            c = 5;
                            break;
                        }
                        break;
                    case -1007092807:
                        if (str.equals("IS_SUPPORTED")) {
                            c = '\t';
                            break;
                        }
                        break;
                    case -68128204:
                        if (str.equals("RESET_PASSWORD_WITH_TOKEN")) {
                            c = '\b';
                            break;
                        }
                        break;
                    case 358962909:
                        if (str.equals("ON_DATA_LOCK_STATE_CHANGE")) {
                            c = '\n';
                            break;
                        }
                        break;
                    case 921051593:
                        if (str.equals("ON_WORKSPACE_CREATION")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 1209819970:
                        if (str.equals("ON_DEVICE_OWNER_PROVISIONING")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1334135085:
                        if (str.equals("ON_BRINGUP")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 1419997776:
                        if (str.equals("ON_PASSWORD2_AUTH")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 1619296023:
                        if (str.equals("CLEAR_RESET_PASSWORD_TOKEN")) {
                            c = 7;
                            break;
                        }
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        bundle2.putBoolean("dual_dar_response", this.mDualDARClient.onClientBringup());
                        break;
                    case 1:
                    case 2:
                        int i2 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
                        bundle2.putBoolean("dual_dar_response", this.mDualDARClient.onDualDARSetupForUser(i2));
                        bundle2.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i2);
                        break;
                    case 3:
                        int i3 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
                        bundle2.putBoolean("dual_dar_response", this.mDualDARClient.onDualDARDestroyForUser(i3));
                        bundle2.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i3);
                        break;
                    case 4:
                        int i4 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
                        byte[] bArrDecryptMessageFrom = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("EXISTING_PASSWORD"));
                        boolean zOnPasswordAuth = this.mDualDARClient.onPasswordAuth(i4, bArrDecryptMessageFrom);
                        Wiper.wipe(bArrDecryptMessageFrom);
                        bundle2.putBoolean("dual_dar_response", zOnPasswordAuth);
                        bundle2.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i4);
                        break;
                    case 5:
                        int i5 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
                        byte[] bArrDecryptMessageFrom2 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("EXISTING_PASSWORD"));
                        byte[] bArrDecryptMessageFrom3 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("NEW_PASSWORD"));
                        boolean zOnPasswordChange = this.mDualDARClient.onPasswordChange(i5, bArrDecryptMessageFrom2, bArrDecryptMessageFrom3);
                        Wiper.wipe(bArrDecryptMessageFrom2);
                        Wiper.wipe(bArrDecryptMessageFrom3);
                        bundle2.putBoolean("dual_dar_response", zOnPasswordChange);
                        bundle2.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i5);
                        break;
                    case 6:
                        int i6 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
                        byte[] bArrDecryptMessageFrom4 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("EXISTING_PASSWORD"));
                        long j = bundle.getLong("RESET_PASSWORD_TOKEN_HANDLE");
                        byte[] bArrDecryptMessageFrom5 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("RESET_PASSWORD_TOKEN"));
                        boolean zOnSetResetPasswordToken = this.mDualDARClient.onSetResetPasswordToken(i6, bArrDecryptMessageFrom4, j, bArrDecryptMessageFrom5);
                        Wiper.wipe(bArrDecryptMessageFrom4);
                        Wiper.wipe(bArrDecryptMessageFrom5);
                        bundle2.putBoolean("dual_dar_response", zOnSetResetPasswordToken);
                        bundle2.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i6);
                        break;
                    case 7:
                        int i7 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
                        this.mDualDARClient.onClearResetPasswordToken(i7, bundle.getLong("RESET_PASSWORD_TOKEN_HANDLE"));
                        bundle2.putBoolean("dual_dar_response", true);
                        bundle2.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i7);
                        break;
                    case '\b':
                        int i8 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
                        byte[] bArrDecryptMessageFrom6 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("NEW_PASSWORD"));
                        long j2 = bundle.getLong("RESET_PASSWORD_TOKEN_HANDLE");
                        byte[] bArrDecryptMessageFrom7 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("RESET_PASSWORD_TOKEN"));
                        boolean zOnResetPasswordWithToken = this.mDualDARClient.onResetPasswordWithToken(i8, bArrDecryptMessageFrom6, j2, bArrDecryptMessageFrom7);
                        Wiper.wipe(bArrDecryptMessageFrom6);
                        Wiper.wipe(bArrDecryptMessageFrom7);
                        bundle2.putBoolean("dual_dar_response", zOnResetPasswordWithToken);
                        bundle2.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i8);
                        break;
                    case '\t':
                        bundle2.putBoolean("dual_dar_response", this.mDualDARClient.isSupported(bundle.getInt("FEATURE")));
                        bundle2.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -1);
                        break;
                    case '\n':
                        int i9 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
                        this.mDualDARClient.onDataLockStateChange(i9, bundle.getBoolean("is_data_locked"));
                        bundle2.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i9);
                        break;
                }
                return bundle2;
            } catch (Exception e) {
                Log.e(DualDarClientManager.TAG, "Failed to decrypt function params or complete the function invocation");
                e.printStackTrace();
                bundle2.putBoolean("dual_dar_response", false);
            }
            return bundle2;
        }

        private DualDARClientAgentService(IDualDARClient iDualDARClient) {
            this.mDualDARClient = iDualDARClient;
        }
    }

    private DualDarClientManager(Context context, IDualDARClient iDualDARClient) {
        this.mContext = context;
        this.mClientAgentService = new DualDARClientAgentService(iDualDARClient, 0);
    }

    public static DualDarClientManager getInstance(Context context, IDualDARClient iDualDARClient) {
        if (mInstance == null) {
            mInstance = new DualDarClientManager(context, iDualDARClient);
        }
        return mInstance;
    }

    public String initializeSecureSession(int i, String str, String str2, String str3) {
        try {
            DualDARManager.getInstance(this.mContext).establishSecureSession();
            return this.mClientAgentService.initializeSecureSession(i, str, str2, str3);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "initializeSecureSession failed!");
            return null;
        }
    }

    public void onAgentReconnected() {
        Log.d(TAG, "onAgentReconnected!");
        DualDARManager.getInstance(this.mContext).onAgentReconnected();
    }

    public Bundle onMessage(int i, String str, String str2, Bundle bundle) throws RemoteException {
        return this.mClientAgentService.onMessage(i, str2, bundle);
    }

    public boolean terminateSecureSession(int i, String str, String str2) {
        try {
            DualDARManager.getInstance(this.mContext).teardownSecureSession();
            return this.mClientAgentService.terminateSecureSession(i, str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "terminateSecureSession failed!");
            return false;
        }
    }
}
