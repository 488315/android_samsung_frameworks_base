package com.samsung.android.knox.ucm.core;

import android.os.Bundle;
import android.os.Process;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.ucm.core.IUcmService;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SecureChannelManager {
    public static final String BUNDLE_EXTRA_SCP_ENCRYPTION = "scp_encryption";
    public static final String BUNDLE_EXTRA_SCP_KEY_ID = "scp_key_id";
    public static final String BUNDLE_EXTRA_SCP_KEY_LENGTH = "scp_key_length";
    public static final String BUNDLE_EXTRA_SCP_KEY_PARAM_DH_G = "scp_key_agreement_param_dh_g";
    public static final String BUNDLE_EXTRA_SCP_KEY_PARAM_DH_P = "scp_key_agreement_param_dh_p";
    public static final String BUNDLE_EXTRA_SCP_KEY_PARAM_ECC = "scp_key_agreement_param_ecc";
    public static final String BUNDLE_EXTRA_SCP_KEY_VERSION = "scp_key_version";
    public static final String BUNDLE_EXTRA_SCP_MAC = "scp_mac";
    public static final String BUNDLE_EXTRA_SCP_PROTOCOL = "scp_protocol";
    public static final int BUNDLE_SCP_KEY_PARAM_ECC_FRP_P256 = 64;
    public static final int BUNDLE_SCP_KEY_PARAM_ECC_NIST_P256 = 0;
    public static final boolean DBG = true;
    public static final int ERROR_APDU_PARSING = 52;
    public static final int ERROR_CA_CERT_NOT_FOUND = 55;
    public static final int ERROR_CHANNEL_NOT_FOUND = 53;
    public static final int ERROR_DEVICE_COMPROMISED = 62;
    public static final int ERROR_INTERNAL = 50;
    public static final int ERROR_INTERNAL_CRYPTO_FAILED = 57;
    public static final int ERROR_INVALID_MESSAGE_TYPE = 64;
    public static final int ERROR_INVALID_PERMISSION = 65;
    public static final int ERROR_INVALID_PROTOCOL = 63;
    public static final int ERROR_INVALID_SD_CERT = 59;
    public static final int ERROR_INVALID_SD_MAC = 61;
    public static final int ERROR_INVALID_SD_RECEIPT = 60;
    public static final int ERROR_NOT_SUPPORTED_CURVE = 58;
    public static final int ERROR_NO_INTERNAL_MEMORY = 54;
    public static final int ERROR_SD_CERT_NOT_FOUND = 56;
    public static final int ERROR_TLV_PARSING = 51;
    public static final int ERROR_TZ_APP_CONNECTION_FAILED = 66;
    public static final int MESSAGE_TYPE_COMMAND = 200;
    public static final int MESSAGE_TYPE_FORWARD_TO_SD = 400;
    public static final int MESSAGE_TYPE_PROCESSING_COMPLETED = 401;
    public static final int MESSAGE_TYPE_RESPONSE = 201;
    public static final int MESSAGE_TYPE_SECURE_CHANNEL = 202;
    public static final int PROTOCOL_TYPE_SCP11A = 100;
    public static final int PROTOCOL_TYPE_SCP11B = 101;
    public static final int PROTOCOL_TYPE_SCP_CUSTOM = 102;
    public static final int PROTOCOL_TYPE_SCP_OTHER = 103;
    public static final String SERVICE_NAME = "com.samsung.ucs.ucsservice";
    public static final int STATUS_FAILURE = 1;
    public static final int STATUS_SC_CONSTRUCTED = 300;
    public static final int STATUS_SC_REQUIRED = 301;
    public static final int STATUS_SUCCESS = 0;
    public static final String TAG = "SecureChannelManager";
    public final IUcmService mBinder;
    public ContextInfo mContextInfo = new ContextInfo(Process.myUid());

    private SecureChannelManager(IUcmService iUcmService) {
        this.mBinder = iUcmService;
    }

    public static SecureChannelManager getInstance() {
        IUcmService asInterface = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            return null;
        }
        return new SecureChannelManager(asInterface);
    }

    public final ApduMessage createSecureChannel(int i, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "SecureChannelManager.createSecureChannel");
        Log.i(TAG, "createSecureChannel is deprecated from Knox 3.10, not supported anymore.");
        return null;
    }

    public final int destroySecureChannel() {
        EnterpriseLicenseManager.log(this.mContextInfo, "SecureChannelManager.destroySecureChannel");
        Log.i(TAG, "destroySecureChannel is deprecated from Knox 3.10, not supported anymore");
        return 1;
    }

    public final ApduMessage processMessage(int i, byte[] bArr) {
        EnterpriseLicenseManager.log(this.mContextInfo, "SecureChannelManager.processMessage");
        Log.i(TAG, "processMessage is deprecated from Knox 3.10, not supported anymore");
        return null;
    }
}
