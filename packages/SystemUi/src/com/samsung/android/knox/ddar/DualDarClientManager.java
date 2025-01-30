package com.samsung.android.knox.ddar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgent;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;
import com.samsung.android.knox.dar.ddar.securesession.Wiper;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class DualDarClientManager extends IProxyAgent.Stub {
    private static final String TAG = "DualDarClientManager";
    private static DualDarClientManager mInstance;
    private final DualDARClientAgentService mClientAgentService;
    private final Context mContext;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class DualDARClientAgentService extends IProxyAgentService {
        private IDualDARClient mDualDARClient;

        public /* synthetic */ DualDARClientAgentService(IDualDARClient iDualDARClient, int i) {
            this(iDualDARClient);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public Bundle onMessage(int i, String str, Bundle bundle) {
            char c;
            Bundle bundle2 = new Bundle();
            bundle.setClassLoader(getClass().getClassLoader());
            String string = bundle.getString("ORIGINATING_SECURE_CLIENT_ID");
            try {
                switch (str.hashCode()) {
                    case -1796774992:
                        if (str.equals("ON_WORKSPACE_DESTROY")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1540060126:
                        if (str.equals("SET_RESET_PASSWORD_TOKEN")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1137042856:
                        if (str.equals("ON_PASSWORD2_CHANGE")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1007092807:
                        if (str.equals("IS_SUPPORTED")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case -68128204:
                        if (str.equals("RESET_PASSWORD_WITH_TOKEN")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case 358962909:
                        if (str.equals("ON_DATA_LOCK_STATE_CHANGE")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case 921051593:
                        if (str.equals("ON_WORKSPACE_CREATION")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1209819970:
                        if (str.equals("ON_DEVICE_OWNER_PROVISIONING")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1334135085:
                        if (str.equals("ON_BRINGUP")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1419997776:
                        if (str.equals("ON_PASSWORD2_AUTH")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1619296023:
                        if (str.equals("CLEAR_RESET_PASSWORD_TOKEN")) {
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
                        byte[] decryptMessageFrom = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("EXISTING_PASSWORD"));
                        boolean onPasswordAuth = this.mDualDARClient.onPasswordAuth(i4, decryptMessageFrom);
                        Wiper.wipe(decryptMessageFrom);
                        bundle2.putBoolean("dual_dar_response", onPasswordAuth);
                        bundle2.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i4);
                        break;
                    case 5:
                        int i5 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
                        byte[] decryptMessageFrom2 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("EXISTING_PASSWORD"));
                        byte[] decryptMessageFrom3 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("NEW_PASSWORD"));
                        boolean onPasswordChange = this.mDualDARClient.onPasswordChange(i5, decryptMessageFrom2, decryptMessageFrom3);
                        Wiper.wipe(decryptMessageFrom2);
                        Wiper.wipe(decryptMessageFrom3);
                        bundle2.putBoolean("dual_dar_response", onPasswordChange);
                        bundle2.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i5);
                        break;
                    case 6:
                        int i6 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
                        byte[] decryptMessageFrom4 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("EXISTING_PASSWORD"));
                        long j = bundle.getLong("RESET_PASSWORD_TOKEN_HANDLE");
                        byte[] decryptMessageFrom5 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("RESET_PASSWORD_TOKEN"));
                        boolean onSetResetPasswordToken = this.mDualDARClient.onSetResetPasswordToken(i6, decryptMessageFrom4, j, decryptMessageFrom5);
                        Wiper.wipe(decryptMessageFrom4);
                        Wiper.wipe(decryptMessageFrom5);
                        bundle2.putBoolean("dual_dar_response", onSetResetPasswordToken);
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
                        byte[] decryptMessageFrom6 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("NEW_PASSWORD"));
                        long j2 = bundle.getLong("RESET_PASSWORD_TOKEN_HANDLE");
                        byte[] decryptMessageFrom7 = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(string, bundle.getByteArray("RESET_PASSWORD_TOKEN"));
                        boolean onResetPasswordWithToken = this.mDualDARClient.onResetPasswordWithToken(i8, decryptMessageFrom6, j2, decryptMessageFrom7);
                        Wiper.wipe(decryptMessageFrom6);
                        Wiper.wipe(decryptMessageFrom7);
                        bundle2.putBoolean("dual_dar_response", onResetPasswordWithToken);
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

    public Bundle onMessage(int i, String str, String str2, Bundle bundle) {
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
