package com.samsung.android.knox.ucm.plugin.agent;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.security.keystore.KeyProtection;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.knox.ucm.plugin.keystore.KeyGenParameterSpec;
import com.samsung.android.knox.ucm.plugin.keystore.KeyStoreParameter;
import com.samsung.android.knox.ucm.plugin.service.Cipher;
import com.samsung.android.knox.ucm.plugin.service.KeyGenerator;
import com.samsung.android.knox.ucm.plugin.service.KeyPairGenerator;
import com.samsung.android.knox.ucm.plugin.service.Mac;
import com.samsung.android.knox.ucm.plugin.service.SecureRandom;
import com.samsung.android.knox.ucm.plugin.service.Signature;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class UcmAgentService extends Service {
    public static final String ACTION_UCM_PLUGIN_STATUS = "com.samsung.android.knox.intent.action.UCM_PLUGIN_STATUS";
    public static final String ADMIN_UID = "adminUid";
    public static final String AID = "aid";
    public static final String ALIASES = "aliases";
    public static final String ALLOWED_PACKAGES = "allowed_packages";
    public static final int ALLOW_ALL = 1;
    public static final int API_FAILED = -1;
    public static final String API_RESULT = "api_result";
    public static final int API_SUCCESS = 0;
    public static final int AUTH_ALPHA_NUMERIC = 1;
    public static final String AUTH_MAX_COUNT = "maxAuthCnt";
    public static final String AUTH_MODE = "authMode";
    public static final int AUTH_NUMERIC = 0;
    public static final int AUTH_UNKNOWN = -1;
    public static final int BLOCK_ALL = 2;
    public static final String BUNDLE_EXTRA_ADD_PIN_CACHE_EXEMPTLIST = "add_pin_cache_exemptlist";
    public static final String BUNDLE_EXTRA_APPLET_INSTALLATION_STATUS = "applet_installation_status";
    public static final String BUNDLE_EXTRA_ESE_CHIP_VENDOR = "applet_ese_chip_vendor";
    public static final String BUNDLE_EXTRA_PIN_CACHE = "pin_cache";
    public static final String BUNDLE_EXTRA_PIN_CACHE_TIMEOUT_MINUTES = "timeout";
    public static final String BUNDLE_EXTRA_REMOVE_PIN_CACHE_EXEMPTLIST = "remove_pin_cache_exemptlist";
    public static final String CS_NAME = "csName";
    public static final int ERROR_ALIAS_NOT_FOUND = 17;
    public static final int ERROR_APDU_CREATION = 16777472;
    public static final int ERROR_APPLET_INSTALLATION = 150994944;
    public static final int ERROR_APPLET_INSTALL_LOCATION = 25;
    public static final int ERROR_APPLET_MAX_ERROR_CODE = 134283264;
    public static final int ERROR_APPLET_UNKNOWN = 134217728;
    public static final int ERROR_BAD_APPLET_RESPONSE = 16777728;
    public static final int ERROR_BAD_PADDING_EXCEPTION = 267;
    public static final int ERROR_CANNOT_CHANGE_ODE_CONFIGURATION = 201327360;
    public static final int ERROR_CERTFACTORY_INSTANCE_NOT_FOUND = 12;
    public static final int ERROR_CERTIFICATE_ENCODING_EXCEPTION = 262;
    public static final int ERROR_CERTIFICATE_EXCEPTION = 261;
    public static final int ERROR_CIPHER_INSTANCE_NOT_FOUND = 11;
    public static final int ERROR_CORRUPTED_CS_RESPONSE = 23;
    public static final int ERROR_CREDENTIAL_STORAGE_ACCESS_DENIED_BY_ADMIN_POLICY = 15;
    public static final int ERROR_CRYPTO_ENGINE_EXCEPTION = 257;
    public static final int ERROR_DATABASE_COMPROMISED = 38;
    public static final int ERROR_EMPTY_CERTIFICATE_CHAIN = 9;
    public static final int ERROR_EMPTY_PARAMETER = 16;
    public static final int ERROR_ESECOMM_TRANSMIT_FAILURE = 21;
    public static final int ERROR_FAILED_TO_GET_READER_FOR_STORAGE = 33555714;
    public static final int ERROR_FILE_NOT_FOUND_EXCEPTION = 270;
    public static final int ERROR_GET_READERS_ILLEGAL_STATE_EXCEPTION = 33554690;
    public static final int ERROR_GET_READERS_NULL_POINTER_EXCEPTION = 33554689;
    public static final int ERROR_ILLEGAL_BLOCK_SIZE_EXCEPTION = 268;
    public static final int ERROR_INCORRECT_CARD = 36;
    public static final int ERROR_INCORRECT_PIN = 32;
    public static final int ERROR_INCORRECT_PUK = 33;
    public static final int ERROR_INTERNAL_COMMUNICATION = 16778240;
    public static final int ERROR_INTERNAL_UCM_FRMWK_END = 8191;
    public static final int ERROR_INTERNAL_UCM_FRMWK_START = 4096;
    public static final int ERROR_INVALID_ALGORTHM_PARAMETER_EXCEPTION = 259;
    public static final int ERROR_INVALID_INPUT = 4;
    public static final int ERROR_INVALID_KEY_SPEC_EXCEPTION = 263;
    public static final int ERROR_INVALID_ODE_CONFIGURATION = 201327104;
    public static final int ERROR_IO_EXCEPTION = 269;
    public static final int ERROR_KEYSTORE_ENTRY_NOT_FOUND = 8;
    public static final int ERROR_KEYSTORE_EXCEPTION = 266;
    public static final int ERROR_KEYSTORE_TYPE = 7;
    public static final int ERROR_MISSING_DEPENDENCY = 37;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NON_UCS_PLUGINSPI = 19;
    public static final int ERROR_NO_ADAPTER_FOUND = 24;
    public static final int ERROR_NO_ADAPTER_RESPONSE = 20;
    public static final int ERROR_NO_PLUGIN_AGENT_FOUND = 14;
    public static final int ERROR_NO_PLUGIN_RESPONSE = 13;
    public static final int ERROR_NO_SESSION_AVAILABLE = 33555713;
    public static final int ERROR_NO_SUCH_ALGORITHM_EXCEPTION = 258;
    public static final int ERROR_NO_SUCH_PROVIDER_EXCEPTION = 264;
    public static final int ERROR_OMA_TRANSMIT_FAILURE = 22;
    public static final int ERROR_OPEN_LOGICAL_CHANNEL_ILLEGAL_ARGUMENT_EXCEPTION = 33555203;
    public static final int ERROR_OPEN_LOGICAL_CHANNEL_ILLEGAL_STATE_EXCEPTION = 33555202;
    public static final int ERROR_OPEN_LOGICAL_CHANNEL_IO_EXCEPTION = 33555201;
    public static final int ERROR_OPEN_LOGICAL_CHANNEL_NO_SUCH_ELEMENT_EXCEPTION = 33555205;
    public static final int ERROR_OPEN_LOGICAL_CHANNEL_SECURITY_EXCEPTION = 33555204;
    public static final int ERROR_OPEN_LOGICAL_CHANNEL_UNKNOWN = 33555206;
    public static final int ERROR_OPEN_SESSION_IO_EXCEPTION = 33554945;
    public static final int ERROR_OUT_OF_BOUND = 6;
    public static final int ERROR_PLUGIN_ALREADY_USED = 34;
    public static final int ERROR_PRIVATEKEY_ENTRY_NOT_FOUND = 10;
    public static final int ERROR_SCP_CREATE_CHANNEL_FAILED = 50331651;
    public static final int ERROR_SCP_DECRYPTION_FAILED = 50331650;
    public static final int ERROR_SCP_ENCRYPTION_FAILED = 50331649;
    public static final int ERROR_SCP_NULL_RESPONSE_RECV = 50331652;
    public static final int ERROR_SCP_UNKNOWN = 50331648;
    public static final int ERROR_SHORT_BUFFER_EXCEPTION = 260;
    public static final int ERROR_SIGNATURE_EXCEPTION = 272;
    public static final int ERROR_SMARTCARD_UNAVAILABLE = 16777984;
    public static final int ERROR_STORAGE_FULL = 5;
    public static final int ERROR_TRANSMIT_ILLEGAL_ARGUMENT_EXCEPTION = 33555459;
    public static final int ERROR_TRANSMIT_ILLEGAL_STATE_EXCEPTION = 33555458;
    public static final int ERROR_TRANSMIT_IO_EXCEPTION = 33555457;
    public static final int ERROR_TRANSMIT_NULL_POINTER_EXCEPTION = 33555461;
    public static final int ERROR_TRANSMIT_SECURITY_EXCEPTION = 33555460;
    public static final int ERROR_TRANSMIT_UNKNOWN = 33555462;
    public static final int ERROR_UNDEFINED_EXCEPTION = 271;
    public static final int ERROR_UNKNOWN = 18;
    public static final int ERROR_UNREADABLE_ODE_CONFIGURATION = 201326848;
    public static final int ERROR_UNRECOVERABLE_KEY_EXCEPTION = 265;
    public static final int ERROR_UNRECOVERABLE_STATE = 35;
    public static final int ERROR_UNSUPPORTED_ALGORITHM = 2;
    public static final int ERROR_UNSUPPORTED_OPERATION = 3;
    public static final int ERROR_UNSUPPORTED_PARAMETER = 1;
    public static final int EVENT_ADMIN_LICENSE_EXPIRED = 13;
    public static final int EVENT_ADMIN_LICENSE_RENEWED = 14;
    public static final int EVENT_BOOT_COMPLETED = 17;
    public static final int EVENT_CONTAINER_LOCKED = 20;
    public static final int EVENT_CONTAINER_UNLOCKED = 21;
    public static final int EVENT_DEVICE_LOCKED = 15;
    public static final int EVENT_DEVICE_UNLOCKED = 16;
    public static final int EVENT_FACTORY_RESET = 101;
    public static final int EVENT_KEYGUARD_SET = 18;
    public static final int EVENT_KEYGUARD_UNSET = 19;
    public static final int EVENT_PACKAGE_UNINSTALL = 12;
    public static final int EVENT_PLUGIN_UNMANAGED = 10;
    public static final int EVENT_USER_REMOVED = 11;
    private static String KEY_EXTRA_AAD = "extra_aad";
    private static String KEY_EXTRA_IV = "extra_iv";
    private static String KEY_EXTRA_TAG_LEN = "extra_tag_length";
    public static final String LOCKSCREEN_MESSAGE = "lockscreen_message";
    public static final String LOCK_STATE = "state";
    public static final String MAX_PIN_LENGTH = "maxPinLength";
    public static final String MAX_PUK_LENGTH = "maxPukLength";
    public static final String MIN_PIN_LENGTH = "minPinLength";
    public static final String MIN_PUK_LENGTH = "minPukLength";
    public static final String MISC_INFO = "miscInfo";
    public static final String PACKAGE_ACCESS_TYPE = "package_access_type";
    public static final String PACKAGE_UID = "packageUid";
    public static final int PARTIALLY = 3;
    public static final int PIN_CACHE_KEYGUARD_TIMEOUT = 2;
    public static final int PIN_CACHE_TIMEOUT = 1;
    public static final String PLUGIN_BOOLEAN_RESPONSE = "booleanresponse";
    public static final String PLUGIN_BUNDLE_RESPONSE = "bundleresponse";
    public static final String PLUGIN_BYTEARRAY_RESPONSE = "bytearrayresponse";
    public static final String PLUGIN_DATA_STRING_RESPONSE = "RESPONSE_DATA";
    public static final String PLUGIN_ERROR_CODE = "errorresponse";
    public static final String PLUGIN_INT_RESPONSE = "intresponse";
    public static final int PLUGIN_KEY_TYPE_PRIVATE = 2;
    public static final String PLUGIN_KEY_TYPE_RESPONSE = "keytyperesponse";
    public static final int PLUGIN_KEY_TYPE_SECRET = 1;
    public static final int PLUGIN_KEY_TYPE_UNKNOWN = -1;
    public static final String PLUGIN_PUBLIC_KEY = "generatedpublickey";
    public static final String PLUGIN_SECRET_KEY = "generatedsecretkey";
    public static final String PLUGIN_STRINGARRAY_RESPONSE = "stringarrayresponse";
    public static final String PLUGIN_STRING_RESPONSE = "stringresponse";
    public static final String PLUGIN_TOASTMSG_RESPONSE = "toastmessageresponse";
    public static final String REMAIN_COUNT = "remainCnt";
    public static final String REQUEST_ID = "request_id";
    public static final String SCP_TYPE = "scptype";
    public static final int STATE_BLOCKED = 133;
    public static final int STATE_LOCKED = 132;
    public static final int STATE_UNKNOWN = -1;
    public static final int STATE_UNLOCKED = 131;
    public static final String STATUS_CODE = "status_code";
    public static final String STORAGE_TYPE = "storagetype";
    public static final String SUPPORTS_ODE = "supportsode";
    private static final String TAG = "UcmAgentService";
    public static final String TA_COMMAND_ID = "tacommandid";
    public static final String TA_DATA = "tadata";
    public static final String TA_DATA_LENGTH = "tadatalength";
    public static final String TA_ERROR_CODE = "taerrorcode";
    public static final String TA_ERROR_DESCRIPTION = "taerrordescription";
    public static final String TA_MAGIC_NUMBER = "tamagicnumber";
    public static final String TA_VERSION = "taversion";
    public static final String USER_ID = "userId";
    private Provider mProvider = new UcmAgentProviderImpl();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class UcmAgentLoadParameter implements KeyStore.LoadStoreParameter {
        KeyStoreParameter param;

        public UcmAgentLoadParameter(int i, int i2, Bundle bundle) {
            this.param = new KeyStoreParameter(i, i2, bundle);
        }

        @Override // java.security.KeyStore.LoadStoreParameter
        public KeyStore.ProtectionParameter getProtectionParameter() {
            return this.param;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UcmAgentServiceWrapper extends IUcmAgentService.Stub {
        public /* synthetic */ UcmAgentServiceWrapper(UcmAgentService ucmAgentService, int i) {
            this();
        }

        private Bundle reponseUnsupportedWithBoolean() {
            Bundle bundle = new Bundle();
            bundle.putBoolean(UcmAgentService.PLUGIN_BOOLEAN_RESPONSE, false);
            bundle.putInt(UcmAgentService.PLUGIN_ERROR_CODE, 3);
            return bundle;
        }

        private Bundle reponseUnsupportedWithNullBytes() {
            Bundle bundle = new Bundle();
            bundle.putByteArray(UcmAgentService.PLUGIN_BYTEARRAY_RESPONSE, null);
            bundle.putInt(UcmAgentService.PLUGIN_ERROR_CODE, 3);
            return bundle;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle APDUCommand(byte[] bArr, Bundle bundle) {
            return UcmAgentService.this.APDUCommand(bArr, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle changePin(String str, String str2) {
            return UcmAgentService.this.changePin(str, str2);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle changePinWithPassword(String str, String str2) {
            return UcmAgentService.this.changePinWithPassword(str, str2);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle configureCredentialStoragePlugin(int i, Bundle bundle, int i2) {
            return UcmAgentService.this.configureCredentialStoragePlugin(i, bundle, i2);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle containsAlias(String str, int i, int i2) {
            Log.e(UcmAgentService.TAG, "containsAlias. Not Supported");
            return reponseUnsupportedWithBoolean();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle decrypt(String str, byte[] bArr, String str2, Bundle bundle) {
            return UcmAgentService.this.decrypt(str, bArr, str2, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle delete(String str, Bundle bundle) {
            return UcmAgentService.this.deleteKey(str, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle encrypt(String str, byte[] bArr, String str2, Bundle bundle) {
            return UcmAgentService.this.encrypt(str, bArr, str2, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateDek() {
            return UcmAgentService.this.generateDek();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateKey(String str, String str2, int i, Bundle bundle) {
            if (bundle == null) {
                Log.e(UcmAgentService.TAG, "generateKey. params is null");
                return null;
            }
            int i2 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID);
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(str, i, i2, false, 1, i2);
            builder.mAlgorithm = str2;
            builder.mPurposes = bundle.getInt(UcmAgentProviderImpl.KEY_EXTRA_PURPOSE);
            builder.mIsRandomizedEncryptionRequired = bundle.getBoolean(UcmAgentProviderImpl.KEY_EXTRA_RANDOMIZED_ENCRYPTION, true);
            builder.mEcCurveName = bundle.getString(UcmAgentProviderImpl.KEY_EXTRA_EC_CURVE_NAME, "");
            builder.mBlockModes = bundle.getStringArray(UcmAgentProviderImpl.KEY_EXTRA_BLOCK_MODES);
            builder.mDigests = bundle.getStringArray(UcmAgentProviderImpl.KEY_EXTRA_DIGESTS);
            builder.mSignaturePaddings = bundle.getStringArray(UcmAgentProviderImpl.KEY_EXTRA_SIGNATURE_PADDINGS);
            builder.mOptions = bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS);
            return UcmAgentService.this.generateKey(builder.build(), bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) {
            return UcmAgentService.this.generateKeyPair(str, str2, i, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateKeyguardPassword(int i, Bundle bundle) {
            return UcmAgentService.this.generateKeyguardPassword(i, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateSecureRandom(int i, byte[] bArr, Bundle bundle) {
            return UcmAgentService.this.generateSecureRandom(i, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateWrappedDek() {
            return UcmAgentService.this.generateWrappedDek();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getCertificateChain(String str, Bundle bundle) {
            return UcmAgentService.this.getCertificateChain(str, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getCredentialStoragePluginConfiguration(int i) {
            return UcmAgentService.this.getCredentialStoragePluginConfiguration(i);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getCredentialStorageProperty(int i, int i2, Bundle bundle) {
            return UcmAgentService.this.getCredentialStorageProperty(i, i2, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getDek() {
            return UcmAgentService.this.getDek();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public String getDetailErrorMessage(int i) {
            try {
                return UcmAgentService.this.getDetailErrorMessage(i);
            } catch (AbstractMethodError e) {
                Log.e(UcmAgentService.TAG, "AbstractMethodError in getDetailErrorMessage", e);
                return null;
            }
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getInfo() {
            return UcmAgentService.this.getInfo();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyType(String str, Bundle bundle) {
            return UcmAgentService.this.getKeyType(str, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinCurrentRetryCount() {
            return UcmAgentService.this.getKeyguardPinCurrentRetryCount();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinMaximumLength() {
            return UcmAgentService.this.getKeyguardPinMaximumLength();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinMaximumRetryCount() {
            return UcmAgentService.this.getKeyguardPinMaximumRetryCount();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinMinimumLength() {
            return UcmAgentService.this.getKeyguardPinMinimumLength();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getStatus() {
            return UcmAgentService.this.getStatus();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle importKey(String str, Bundle bundle) {
            return UcmAgentService.this.importKey(str, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
            return UcmAgentService.this.importKeyPair(str, bArr, bArr2, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle initKeyguardPin(String str, Bundle bundle) {
            return UcmAgentService.this.initKeyguardPin(str, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) {
            return UcmAgentService.this.installCertificateIfSupported(str, bArr, str2, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle mac(String str, byte[] bArr, String str2, Bundle bundle) {
            return UcmAgentService.this.mac(str, bArr, str2, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public int notifyChange(int i, Bundle bundle) {
            return UcmAgentService.this.notifyChange(i, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle processCommand(byte[] bArr, Bundle bundle, int i) {
            Log.e(UcmAgentService.TAG, "processCommand. Not Supported");
            return reponseUnsupportedWithNullBytes();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle resetUid(int i) {
            Log.e(UcmAgentService.TAG, "resetUid. Not Supported");
            return reponseUnsupportedWithBoolean();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle resetUser(int i) {
            Log.e(UcmAgentService.TAG, "resetUser. Not Supported");
            return reponseUnsupportedWithBoolean();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle saw(Bundle bundle) {
            return UcmAgentService.this.saw(bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) {
            Log.e(UcmAgentService.TAG, "setCertificateChain. Not Supported");
            return reponseUnsupportedWithBoolean();
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setCredentialStorageProperty(int i, int i2, Bundle bundle) {
            return UcmAgentService.this.setCredentialStorageProperty(i, i2, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setKeyguardPinMaximumLength(int i) {
            return UcmAgentService.this.setKeyguardPinMaximumLength(i);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setKeyguardPinMaximumRetryCount(int i) {
            return UcmAgentService.this.setKeyguardPinMaximumRetryCount(i);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setKeyguardPinMinimumLength(int i) {
            return UcmAgentService.this.setKeyguardPinMinimumLength(i);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setState(int i) {
            return UcmAgentService.this.setState(i);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle sign(String str, byte[] bArr, String str2, boolean z, Bundle bundle) {
            return z ? UcmAgentService.this.encrypt(str, bArr, bundle) : UcmAgentService.this.sign(str, bArr, str2, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle unwrapDek(byte[] bArr) {
            return UcmAgentService.this.unwrapDek(bArr);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle verifyPassword(String str) {
            return UcmAgentService.this.verifyPassword(str);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle verifyPin(int i, String str, Bundle bundle) {
            return UcmAgentService.this.verifyPin(i, str, bundle);
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle verifyPuk(String str, String str2) {
            return UcmAgentService.this.verifyPuk(str, str2);
        }

        private UcmAgentServiceWrapper() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle decrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        byte[] doFinal;
        int i;
        AlgorithmParameterSpec algorithmParameterSpec;
        Log.d(TAG, "decrypt");
        if (bundle == null) {
            Log.e(TAG, "decrypt. property is null");
            return responseErrorWithNullBytes(16);
        }
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("decrypt ", str, ",uid: ");
        m4m.append(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID));
        m4m.append(", caller: ");
        m4m.append(Binder.getCallingUid());
        Log.i(TAG, m4m.toString());
        if (bArr == null) {
            Log.e(TAG, "decrypt. data is null");
            return responseErrorWithNullBytes(16);
        }
        Log.d(TAG, "data length " + bArr.length);
        Bundle bundle2 = new Bundle();
        try {
            com.samsung.android.knox.ucm.plugin.service.KeyStore keyStore = com.samsung.android.knox.ucm.plugin.service.KeyStore.getInstance("KNOX", this.mProvider);
            keyStore.setProperty(bundle);
            keyStore.load(new UcmAgentLoadParameter(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_OWNER_ID), bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_RESOURCE_ID), bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS)));
            KeyStore.Entry entry = keyStore.getEntry(str, null);
            if (entry == null) {
                Log.e(TAG, "decrypt. getEntry null ");
                return responseErrorWithNullBytes(keyStore.getErrorStatus() == 0 ? 8 : keyStore.getErrorStatus());
            }
            String str3 = str2.split("/")[0];
            Cipher cipher = Cipher.getInstance(str2, this.mProvider);
            cipher.setProperty(bundle);
            Log.d(TAG, "decrypt. base algorithm = " + str3);
            int i2 = 10;
            if (!TextUtils.isEmpty(str2) && "AES".equalsIgnoreCase(str3)) {
                SecretKey secretKey = ((KeyStore.SecretKeyEntry) entry).getSecretKey();
                if (secretKey == null) {
                    Log.e(TAG, "decrypt. getSecretKey null");
                    if (keyStore.getErrorStatus() != 0) {
                        i2 = keyStore.getErrorStatus();
                    }
                    return responseErrorWithNullBytes(i2);
                }
                boolean equals = "GCM".equals(str2.split("/")[1]);
                if (equals && bundle.containsKey(KEY_EXTRA_TAG_LEN) && bundle.containsKey(KEY_EXTRA_IV)) {
                    algorithmParameterSpec = new GCMParameterSpec(bundle.getInt(KEY_EXTRA_TAG_LEN), bundle.getByteArray(KEY_EXTRA_IV));
                } else {
                    if (!bundle.containsKey(KEY_EXTRA_IV)) {
                        i = 2;
                        algorithmParameterSpec = null;
                        cipher.init(i, secretKey, algorithmParameterSpec);
                        if (equals && bundle.containsKey(KEY_EXTRA_AAD)) {
                            cipher.updateAAD(bundle.getByteArray(KEY_EXTRA_AAD));
                        }
                        doFinal = cipher.doFinal(bArr);
                        bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, doFinal);
                        bundle2.putInt(PLUGIN_ERROR_CODE, cipher.getErrorStatus());
                        return bundle2;
                    }
                    algorithmParameterSpec = new IvParameterSpec(bundle.getByteArray(KEY_EXTRA_IV));
                }
                i = 2;
                cipher.init(i, secretKey, algorithmParameterSpec);
                if (equals) {
                    cipher.updateAAD(bundle.getByteArray(KEY_EXTRA_AAD));
                }
                doFinal = cipher.doFinal(bArr);
                bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, doFinal);
                bundle2.putInt(PLUGIN_ERROR_CODE, cipher.getErrorStatus());
                return bundle2;
            }
            PrivateKey privateKey = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
            if (privateKey == null) {
                Log.e(TAG, "decrypt. getPrivateKey null");
                if (keyStore.getErrorStatus() != 0) {
                    i2 = keyStore.getErrorStatus();
                }
                return responseErrorWithNullBytes(i2);
            }
            cipher.init(2, privateKey);
            doFinal = cipher.doFinal(bArr);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, doFinal);
            bundle2.putInt(PLUGIN_ERROR_CODE, cipher.getErrorStatus());
            return bundle2;
        } catch (IOException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 269);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 264);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (IllegalStateException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, ERROR_TRANSMIT_ILLEGAL_STATE_EXCEPTION);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (NullPointerException e4) {
            e4.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (UnsupportedOperationException e5) {
            e5.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 3);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (InvalidAlgorithmParameterException e6) {
            e6.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 259);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (InvalidKeyException e7) {
            e7.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 263);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (KeyStoreException e8) {
            e8.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 266);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (NoSuchAlgorithmException e9) {
            e9.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (UnrecoverableEntryException e10) {
            e10.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 265);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (CertificateException e11) {
            e11.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 261);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (BadPaddingException e12) {
            e12.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 267);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (IllegalBlockSizeException e13) {
            e13.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 268);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle deleteKey(String str, Bundle bundle) {
        Log.d(TAG, "deleteKey");
        if (bundle == null) {
            Log.e(TAG, "deleteKey. property is null");
            return responseErrorWithBoolean();
        }
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("deleteKey ", str, ", uid: ");
        m4m.append(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID));
        m4m.append(", caller: ");
        m4m.append(Binder.getCallingUid());
        Log.i(TAG, m4m.toString());
        Bundle bundle2 = new Bundle();
        try {
            com.samsung.android.knox.ucm.plugin.service.KeyStore keyStore = com.samsung.android.knox.ucm.plugin.service.KeyStore.getInstance("KNOX", this.mProvider);
            keyStore.setProperty(bundle);
            keyStore.load(new UcmAgentLoadParameter(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_OWNER_ID), bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_RESOURCE_ID), bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS)));
            keyStore.deleteEntry(str);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, true);
            bundle2.putInt(PLUGIN_ERROR_CODE, keyStore.getErrorStatus());
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to deleteKey with error code ks.getErrorStatus() = " + keyStore.getErrorStatus());
            return bundle2;
        } catch (IOException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 269);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to deleteKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 264);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to deleteKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (NullPointerException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to deleteKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (UnsupportedOperationException e4) {
            e4.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 3);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to deleteKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (KeyStoreException e5) {
            e5.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 266);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to deleteKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (NoSuchAlgorithmException e6) {
            e6.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to deleteKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (CertificateException e7) {
            e7.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 266);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to deleteKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle encrypt(String str, byte[] bArr, Bundle bundle) {
        return encrypt(str, bArr, null, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle generateKey(KeyGenParameterSpec keyGenParameterSpec, Bundle bundle) {
        Log.d(TAG, "generateKey");
        if (keyGenParameterSpec == null) {
            Log.e(TAG, "KeyGenParameterSpec is null");
            return responseErrorWithBoolean();
        }
        Log.i(TAG, "generateKey " + keyGenParameterSpec.mKeystoreAlias + ", uid: " + bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID) + ", caller: " + Binder.getCallingUid());
        Bundle bundle2 = new Bundle();
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(keyGenParameterSpec.mAlgorithm, this.mProvider);
            keyGenerator.setProperty(bundle);
            keyGenerator.init(keyGenParameterSpec, null);
            boolean z = keyGenerator.generateKey() != null;
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, z);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to generateKey : " + z);
            return bundle2;
        } catch (NullPointerException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to generateKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (InvalidAlgorithmParameterException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 259);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to generateKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to generateKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) {
        Log.d(TAG, "generateKeyPair");
        if (bundle == null) {
            Log.e(TAG, "generateKeyPair. property is null");
            return responseErrorWithBoolean();
        }
        int i2 = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID);
        Bundle bundle2 = bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS);
        if (bundle2 == null) {
            Log.e(TAG, "generateKeyPair. options is null");
            return responseErrorWithBoolean();
        }
        Bundle bundle3 = new Bundle();
        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(str, i, i2, false, 1, i2);
        builder.mAlgorithm = str2;
        builder.mPurposes = bundle2.getInt(UcmAgentProviderImpl.KEY_EXTRA_PURPOSE);
        builder.mIsRandomizedEncryptionRequired = bundle2.getBoolean(UcmAgentProviderImpl.KEY_EXTRA_RANDOMIZED_ENCRYPTION, true);
        builder.mEcCurveName = bundle2.getString(UcmAgentProviderImpl.KEY_EXTRA_EC_CURVE_NAME, "");
        builder.mBlockModes = bundle2.getStringArray(UcmAgentProviderImpl.KEY_EXTRA_BLOCK_MODES);
        builder.mDigests = bundle2.getStringArray(UcmAgentProviderImpl.KEY_EXTRA_DIGESTS);
        builder.mSignaturePaddings = bundle2.getStringArray(UcmAgentProviderImpl.KEY_EXTRA_SIGNATURE_PADDINGS);
        builder.mOptions = bundle2;
        KeyGenParameterSpec build = builder.build();
        Log.i(TAG, "generateKeyPair " + build.mKeystoreAlias + ",uid: " + bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID) + ", caller: " + Binder.getCallingUid());
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(build.mAlgorithm, this.mProvider);
            keyPairGenerator.setProperty(bundle);
            keyPairGenerator.initialize(build, null);
            KeyPair generateKeyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = generateKeyPair != null ? generateKeyPair.getPublic() : null;
            if (publicKey == null) {
                bundle3.putInt(PLUGIN_ERROR_CODE, 259);
                bundle3.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
                return bundle3;
            }
            bundle3.putSerializable(PLUGIN_PUBLIC_KEY, publicKey);
            bundle3.putInt(PLUGIN_ERROR_CODE, keyPairGenerator.getErrorStatus());
            return bundle3;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            bundle3.putInt(PLUGIN_ERROR_CODE, 264);
            bundle3.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle3;
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            bundle3.putInt(PLUGIN_ERROR_CODE, 2);
            bundle3.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle3;
        } catch (UnsupportedOperationException e3) {
            e3.printStackTrace();
            bundle3.putInt(PLUGIN_ERROR_CODE, 3);
            bundle3.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle3;
        } catch (InvalidAlgorithmParameterException e4) {
            e4.printStackTrace();
            bundle3.putInt(PLUGIN_ERROR_CODE, 259);
            bundle3.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle3;
        } catch (NoSuchAlgorithmException e5) {
            e5.printStackTrace();
            bundle3.putInt(PLUGIN_ERROR_CODE, 2);
            bundle3.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle generateSecureRandom(int i, Bundle bundle) {
        Log.d(TAG, "generateSecureRandom");
        if (bundle == null) {
            Log.e(TAG, "generateSecureRandom. property is null");
            return responseErrorWithNullBytes(16);
        }
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("generateSecureRandom ", i, ",uid: ");
        m1m.append(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID));
        m1m.append(", caller: ");
        m1m.append(Binder.getCallingUid());
        Log.i(TAG, m1m.toString());
        Bundle bundle2 = new Bundle();
        try {
            SecureRandom secureRandom = SecureRandom.getInstance(UcmAgentProviderImpl.SECURERANDOM_SHA1PRNG, this.mProvider);
            secureRandom.setProperty(bundle);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, secureRandom.generateSeed(i));
            bundle2.putInt(PLUGIN_ERROR_CODE, secureRandom.getErrorStatus());
            return bundle2;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 264);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (UnsupportedOperationException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 3);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (NoSuchAlgorithmException e4) {
            e4.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle getCertificateChain(String str, Bundle bundle) {
        Log.d(TAG, "getCertificateChain");
        if (bundle == null) {
            Log.e(TAG, "getCertificateChain. property is null");
            return responseErrorWithNullBytes(16);
        }
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("getCertificateChain ", str, ",uid:");
        m4m.append(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID));
        m4m.append(", caller: ");
        m4m.append(Binder.getCallingUid());
        Log.i(TAG, m4m.toString());
        Bundle bundle2 = new Bundle();
        try {
            com.samsung.android.knox.ucm.plugin.service.KeyStore keyStore = com.samsung.android.knox.ucm.plugin.service.KeyStore.getInstance("KNOX", this.mProvider);
            keyStore.setProperty(bundle);
            keyStore.load(new UcmAgentLoadParameter(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_OWNER_ID), bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_RESOURCE_ID), bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS)));
            Certificate[] certificateChain = keyStore.getCertificateChain(str);
            if (certificateChain != null && certificateChain.length != 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                for (Certificate certificate : certificateChain) {
                    byteArrayOutputStream.write(certificate.getEncoded());
                }
                bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, byteArrayOutputStream.toByteArray());
                bundle2.putInt(PLUGIN_ERROR_CODE, keyStore.getErrorStatus());
                return bundle2;
            }
            Log.d(TAG, "getCertificateChain empty");
            return responseErrorWithNullBytes(keyStore.getErrorStatus());
        } catch (IOException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 269);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 264);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (NullPointerException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (UnsupportedOperationException e4) {
            e4.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 3);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (KeyStoreException e5) {
            e5.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 266);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (NoSuchAlgorithmException e6) {
            e6.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (CertificateEncodingException e7) {
            e7.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 262);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (CertificateException e8) {
            e8.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 261);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle getKeyType(String str, Bundle bundle) {
        Log.d(TAG, "getKeyType");
        if (bundle == null) {
            Log.e(TAG, "getKeyType. property is null");
            return responseErrorWithNullBytes(16);
        }
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("getKeyType: ", str, ", uid: ");
        m4m.append(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID));
        m4m.append(", caller: ");
        m4m.append(Binder.getCallingUid());
        Log.i(TAG, m4m.toString());
        Bundle saw = saw(bundle);
        String[] stringArray = saw.getStringArray(PLUGIN_STRINGARRAY_RESPONSE);
        saw.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
        saw.putInt(PLUGIN_KEY_TYPE_RESPONSE, -1);
        if (stringArray == null) {
            Log.e(TAG, "getKeyType. aliases is null");
            return saw;
        }
        UcmAgentLoadParameter ucmAgentLoadParameter = new UcmAgentLoadParameter(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_OWNER_ID), bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_RESOURCE_ID), bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS));
        for (String str2 : stringArray) {
            if (str2 != null && str2.equals(str)) {
                try {
                    com.samsung.android.knox.ucm.plugin.service.KeyStore keyStore = com.samsung.android.knox.ucm.plugin.service.KeyStore.getInstance("KNOX", this.mProvider);
                    keyStore.setProperty(bundle);
                    keyStore.load(ucmAgentLoadParameter);
                    Key key = keyStore.getKey(str, null);
                    if (key == null) {
                        Log.e(TAG, "getKeyType. key is null");
                        return saw;
                    }
                    saw.putBoolean(PLUGIN_BOOLEAN_RESPONSE, true);
                    saw.putString(PLUGIN_STRING_RESPONSE, key.getAlgorithm());
                    saw.putInt(PLUGIN_ERROR_CODE, keyStore.getErrorStatus());
                    if (key instanceof SecretKey) {
                        saw.putInt(PLUGIN_KEY_TYPE_RESPONSE, 1);
                    } else if (key instanceof PrivateKey) {
                        saw.putInt(PLUGIN_KEY_TYPE_RESPONSE, 2);
                    }
                    Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to getKeyType with error code ks.getErrorStatus() = " + keyStore.getErrorStatus());
                    return saw;
                } catch (IOException e) {
                    e.printStackTrace();
                    saw.putInt(PLUGIN_ERROR_CODE, 269);
                } catch (KeyStoreException e2) {
                    e2.printStackTrace();
                    saw.putInt(PLUGIN_ERROR_CODE, 266);
                } catch (NoSuchAlgorithmException e3) {
                    e3.printStackTrace();
                    saw.putInt(PLUGIN_ERROR_CODE, 2);
                } catch (UnrecoverableKeyException e4) {
                    e4.printStackTrace();
                    saw.putInt(PLUGIN_ERROR_CODE, 265);
                } catch (CertificateException e5) {
                    e5.printStackTrace();
                    saw.putInt(PLUGIN_ERROR_CODE, 261);
                }
            }
        }
        Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to getKeyType with EXCEPTION error code  = " + saw.getInt(PLUGIN_ERROR_CODE));
        return saw;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle importKey(String str, Bundle bundle) {
        Log.d(TAG, "importKey");
        if (bundle == null) {
            Log.e(TAG, "importKey. property is null");
            return responseErrorWithBoolean();
        }
        SecretKey secretKey = (SecretKey) bundle.getSerializable(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_SECRET_KEY);
        if (secretKey == null || !bundle.containsKey(UcmAgentProviderImpl.KEY_EXTRA_PURPOSE)) {
            Log.e(TAG, "importKey. SecretKey is null or property doesn't have purpose");
            return responseErrorWithBoolean();
        }
        KeyProtection.Builder builder = new KeyProtection.Builder(bundle.getInt(UcmAgentProviderImpl.KEY_EXTRA_PURPOSE));
        if (bundle.containsKey(UcmAgentProviderImpl.KEY_EXTRA_BLOCK_MODES)) {
            builder.setBlockModes(bundle.getString(UcmAgentProviderImpl.KEY_EXTRA_BLOCK_MODES));
        }
        if (bundle.containsKey(UcmAgentProviderImpl.KEY_EXTRA_SIGNATURE_PADDINGS)) {
            builder.setEncryptionPaddings(bundle.getString(UcmAgentProviderImpl.KEY_EXTRA_SIGNATURE_PADDINGS));
        }
        builder.setRandomizedEncryptionRequired(bundle.getBoolean(UcmAgentProviderImpl.KEY_EXTRA_RANDOMIZED_ENCRYPTION, true));
        KeyProtection build = builder.build();
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("importKey: ", str, ", uid: ");
        m4m.append(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID));
        m4m.append(", caller: ");
        m4m.append(Binder.getCallingUid());
        Log.i(TAG, m4m.toString());
        Bundle bundle2 = new Bundle();
        try {
            com.samsung.android.knox.ucm.plugin.service.KeyStore keyStore = com.samsung.android.knox.ucm.plugin.service.KeyStore.getInstance("KNOX", this.mProvider);
            keyStore.setProperty(bundle);
            keyStore.load(new UcmAgentLoadParameter(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_OWNER_ID), bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_RESOURCE_ID), bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS)));
            keyStore.setEntry(str, new KeyStore.SecretKeyEntry(secretKey), build);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, true);
            bundle2.putInt(PLUGIN_ERROR_CODE, keyStore.getErrorStatus());
            Log.i(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKey with error code ks.getErrorStatus() = " + keyStore.getErrorStatus());
            return bundle2;
        } catch (IOException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 269);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (KeyStoreException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 266);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (CertificateException e4) {
            e4.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 261);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKey with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
        Log.d(TAG, "importKeyPair");
        if (bundle == null) {
            Log.e(TAG, "importKeyPair. property is null");
            return responseErrorWithNullBytes(16);
        }
        KeyProtection.Builder builder = new KeyProtection.Builder(bundle.getInt(UcmAgentProviderImpl.KEY_EXTRA_PURPOSE));
        if (bundle.containsKey(UcmAgentProviderImpl.KEY_EXTRA_BLOCK_MODES)) {
            builder.setBlockModes(bundle.getString(UcmAgentProviderImpl.KEY_EXTRA_BLOCK_MODES));
        }
        if (bundle.containsKey(UcmAgentProviderImpl.KEY_EXTRA_SIGNATURE_PADDINGS)) {
            builder.setEncryptionPaddings(bundle.getString(UcmAgentProviderImpl.KEY_EXTRA_SIGNATURE_PADDINGS));
        }
        builder.setRandomizedEncryptionRequired(bundle.getBoolean(UcmAgentProviderImpl.KEY_EXTRA_RANDOMIZED_ENCRYPTION, true));
        KeyProtection build = builder.build();
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("importKeyPair ", str, ",uid: ");
        m4m.append(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID));
        m4m.append(", caller: ");
        m4m.append(Binder.getCallingUid());
        Log.i(TAG, m4m.toString());
        Bundle bundle2 = new Bundle();
        try {
            List list = (List) CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(bArr2));
            Certificate[] certificateArr = (Certificate[]) list.toArray(new Certificate[list.size()]);
            com.samsung.android.knox.ucm.plugin.service.KeyStore keyStore = com.samsung.android.knox.ucm.plugin.service.KeyStore.getInstance("KNOX", this.mProvider);
            keyStore.setProperty(bundle);
            keyStore.load(new UcmAgentLoadParameter(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_OWNER_ID), bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_RESOURCE_ID), bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS)));
            keyStore.setEntry(str, new KeyStore.PrivateKeyEntry(bArr != null ? KeyFactory.getInstance(bundle.getString(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_ALGORITHM, "RSA")).generatePrivate(new PKCS8EncodedKeySpec(bArr)) : (PrivateKey) keyStore.getKey(str, null), certificateArr), build);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, true);
            bundle2.putInt(PLUGIN_ERROR_CODE, keyStore.getErrorStatus());
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKeyPair with error code ks.getErrorStatus() = " + keyStore.getErrorStatus());
            return bundle2;
        } catch (IOException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 269);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKeyPair with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 264);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKeyPair with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (NullPointerException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKeyPair with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (UnsupportedOperationException e4) {
            e4.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 3);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKeyPair with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (KeyStoreException e5) {
            e5.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 266);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKeyPair with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (NoSuchAlgorithmException e6) {
            e6.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKeyPair with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (UnrecoverableKeyException e7) {
            e7.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 265);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKeyPair with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (CertificateException e8) {
            e8.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 261);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKeyPair with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (InvalidKeySpecException e9) {
            e9.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 263);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to importKeyPair with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) {
        Log.d(TAG, "installCertificateIfSupported");
        if (bundle == null) {
            Log.e(TAG, "installCertificateIfSupported. property is null");
            return responseErrorWithNullBytes(16);
        }
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("installCertificateIfSupported: ", str, ", uid: ");
        m4m.append(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID));
        m4m.append(", caller: ");
        m4m.append(Binder.getCallingUid());
        Log.i(TAG, m4m.toString());
        Bundle bundle2 = new Bundle();
        try {
            com.samsung.android.knox.ucm.plugin.service.KeyStore keyStore = com.samsung.android.knox.ucm.plugin.service.KeyStore.getInstance("KNOX", this.mProvider);
            keyStore.setProperty(bundle);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            keyStore.load(byteArrayInputStream, str2.toCharArray());
            byteArrayInputStream.close();
            Enumeration<String> aliases = keyStore.aliases();
            while (true) {
                if (!aliases.hasMoreElements()) {
                    break;
                }
                String nextElement = aliases.nextElement();
                Key key = keyStore.getKey(nextElement, str2.toCharArray());
                if (key instanceof PrivateKey) {
                    Certificate[] certificateChain = keyStore.getCertificateChain(nextElement);
                    if (certificateChain == null || certificateChain.length == 0) {
                        throw new CertificateException("Certificate chain empty");
                    }
                    keyStore.setEntry(str, new KeyStore.PrivateKeyEntry((PrivateKey) key, certificateChain), new KeyProtection.Builder(bundle.getInt(UcmAgentProviderImpl.KEY_EXTRA_PURPOSE)).build());
                }
            }
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, true);
            bundle2.putInt(PLUGIN_ERROR_CODE, keyStore.getErrorStatus());
            return bundle2;
        } catch (IOException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 269);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to installCertificateIfSupported with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (KeyStoreException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 266);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to installCertificateIfSupported with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to installCertificateIfSupported with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (UnrecoverableKeyException e4) {
            e4.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 265);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to installCertificateIfSupported with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        } catch (CertificateException e5) {
            e5.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 261);
            bundle2.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to installCertificateIfSupported with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle mac(String str, byte[] bArr, String str2, Bundle bundle) {
        Log.d(TAG, "mac");
        if (bundle == null) {
            Log.e(TAG, "mac. property is null");
            return responseErrorWithNullBytes(16);
        }
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("mac ", str, ", uid: ");
        m4m.append(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID));
        m4m.append(", caller: ");
        m4m.append(Binder.getCallingUid());
        Log.i(TAG, m4m.toString());
        if (bArr == null) {
            Log.e(TAG, "mac. data is null");
            return responseErrorWithNullBytes(16);
        }
        Log.d(TAG, "data length " + bArr.length);
        Bundle bundle2 = new Bundle();
        try {
            com.samsung.android.knox.ucm.plugin.service.KeyStore keyStore = com.samsung.android.knox.ucm.plugin.service.KeyStore.getInstance("KNOX", this.mProvider);
            keyStore.setProperty(bundle);
            keyStore.load(new UcmAgentLoadParameter(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_OWNER_ID), bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_RESOURCE_ID), bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS)));
            KeyStore.Entry entry = keyStore.getEntry(str, null);
            if (entry == null) {
                Log.e(TAG, "mac. getEntry null ");
                return responseErrorWithNullBytes(keyStore.getErrorStatus() == 0 ? 8 : keyStore.getErrorStatus());
            }
            SecretKey secretKey = ((KeyStore.SecretKeyEntry) entry).getSecretKey();
            if (secretKey == null) {
                Log.e(TAG, "mac. getSecretKey null ");
                return responseErrorWithNullBytes(keyStore.getErrorStatus() == 0 ? 10 : keyStore.getErrorStatus());
            }
            Mac mac = Mac.getInstance(str2, this.mProvider);
            mac.setProperty(bundle);
            mac.init(secretKey, null);
            mac.update(bArr, 0, bArr.length);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, mac.doFinal());
            bundle2.putInt(PLUGIN_ERROR_CODE, mac.getErrorStatus());
            return bundle2;
        } catch (IOException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 269);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 264);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (InvalidAlgorithmParameterException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 259);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (InvalidKeyException e4) {
            e4.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 263);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (KeyStoreException e5) {
            e5.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 266);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (NoSuchAlgorithmException e6) {
            e6.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (UnrecoverableEntryException e7) {
            e7.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 265);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (CertificateException e8) {
            e8.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 261);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        }
    }

    private Bundle responseErrorWithBoolean() {
        return responseErrorWithBoolean(16);
    }

    private Bundle responseErrorWithNullBytes(int i) {
        Bundle bundle = new Bundle();
        bundle.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
        bundle.putInt(PLUGIN_ERROR_CODE, i);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle saw(Bundle bundle) {
        com.samsung.android.knox.ucm.plugin.service.KeyStore keyStore;
        Enumeration<String> aliases;
        Log.d(TAG, "saw");
        Bundle bundle2 = new Bundle();
        if (bundle == null) {
            Log.e(TAG, "saw. property is null");
            bundle2.putStringArray(PLUGIN_STRINGARRAY_RESPONSE, null);
            bundle2.putInt(PLUGIN_ERROR_CODE, 16);
            return bundle2;
        }
        Log.i(TAG, "saw uid:" + bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID) + ", caller: " + Binder.getCallingUid());
        try {
            keyStore = com.samsung.android.knox.ucm.plugin.service.KeyStore.getInstance("KNOX", this.mProvider);
            keyStore.setProperty(bundle);
            keyStore.load(new UcmAgentLoadParameter(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_OWNER_ID), bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_RESOURCE_ID), bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS)));
            aliases = keyStore.aliases();
        } catch (IOException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 269);
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 264);
        } catch (NullPointerException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
        } catch (UnsupportedOperationException e4) {
            e4.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 3);
        } catch (KeyStoreException e5) {
            e5.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 266);
        } catch (NoSuchAlgorithmException e6) {
            e6.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
        } catch (CertificateException e7) {
            e7.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 261);
        }
        if (aliases == null) {
            bundle2.putInt(PLUGIN_ERROR_CODE, keyStore.getErrorStatus());
            bundle2.putStringArray(PLUGIN_STRINGARRAY_RESPONSE, null);
            Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to saw with EXCEPTION error code  = " + bundle2.getInt(PLUGIN_ERROR_CODE));
            return bundle2;
        }
        ArrayList arrayList = new ArrayList();
        while (aliases.hasMoreElements()) {
            arrayList.add(aliases.nextElement());
        }
        bundle2.putStringArray(PLUGIN_STRINGARRAY_RESPONSE, (String[]) arrayList.toArray(new String[arrayList.size()]));
        bundle2.putInt(PLUGIN_ERROR_CODE, keyStore.getErrorStatus());
        Log.d(TAG, "UCMERRORTESTING: @UcmAgentService responding to saw with error code ks.getErrorStatus() = " + keyStore.getErrorStatus());
        return bundle2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle sign(String str, byte[] bArr, String str2, Bundle bundle) {
        Log.d(TAG, "sign");
        if (bundle == null) {
            Log.e(TAG, "sign. property is null");
            return responseErrorWithNullBytes(16);
        }
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("sign ", str, ",uid: ");
        m4m.append(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID));
        m4m.append(", caller: ");
        m4m.append(Binder.getCallingUid());
        Log.i(TAG, m4m.toString());
        if (bArr == null) {
            Log.e(TAG, "sign. data is null");
            return responseErrorWithNullBytes(16);
        }
        Log.d(TAG, "data length " + bArr.length);
        Bundle bundle2 = new Bundle();
        try {
            com.samsung.android.knox.ucm.plugin.service.KeyStore keyStore = com.samsung.android.knox.ucm.plugin.service.KeyStore.getInstance("KNOX", this.mProvider);
            keyStore.setProperty(bundle);
            keyStore.load(new UcmAgentLoadParameter(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_OWNER_ID), bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_RESOURCE_ID), bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS)));
            KeyStore.Entry entry = keyStore.getEntry(str, null);
            if (entry == null) {
                Log.e(TAG, "sign. getEntry null ");
                return responseErrorWithNullBytes(keyStore.getErrorStatus() == 0 ? 8 : keyStore.getErrorStatus());
            }
            PrivateKey privateKey = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
            if (privateKey == null) {
                Log.e(TAG, "sign. getPrivateKey null ");
                return responseErrorWithNullBytes(keyStore.getErrorStatus() == 0 ? 10 : keyStore.getErrorStatus());
            }
            Signature signature = Signature.getInstance(str2, this.mProvider);
            signature.setProperty(bundle);
            signature.initSign(privateKey);
            signature.update(bArr);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, signature.sign());
            bundle2.putInt(PLUGIN_ERROR_CODE, signature.getErrorStatus());
            return bundle2;
        } catch (IOException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 269);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 264);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (NullPointerException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (UnsupportedOperationException e4) {
            e4.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 3);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (InvalidKeyException e5) {
            e5.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 263);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (KeyStoreException e6) {
            e6.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 266);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (NoSuchAlgorithmException e7) {
            e7.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (SignatureException e8) {
            e8.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 272);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (UnrecoverableEntryException e9) {
            e9.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 265);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (CertificateException e10) {
            e10.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 261);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        }
    }

    public abstract Bundle APDUCommand(byte[] bArr, Bundle bundle);

    public abstract Bundle changePin(String str, String str2);

    public abstract Bundle changePinWithPassword(String str, String str2);

    public abstract Bundle configureCredentialStoragePlugin(int i, Bundle bundle, int i2);

    public abstract Bundle generateDek();

    public abstract Bundle generateKeyguardPassword(int i, Bundle bundle);

    public abstract Bundle generateWrappedDek();

    public abstract Bundle getCredentialStoragePluginConfiguration(int i);

    public abstract Bundle getCredentialStorageProperty(int i, int i2, Bundle bundle);

    public abstract Bundle getDek();

    public abstract String getDetailErrorMessage(int i);

    public abstract Bundle getInfo();

    public abstract Bundle getKeyguardPinCurrentRetryCount();

    public abstract Bundle getKeyguardPinMaximumLength();

    public abstract Bundle getKeyguardPinMaximumRetryCount();

    public abstract Bundle getKeyguardPinMinimumLength();

    public final Provider getProvider() {
        return this.mProvider;
    }

    public abstract Bundle getStatus();

    public abstract Bundle initKeyguardPin(String str, Bundle bundle);

    public abstract int notifyChange(int i, Bundle bundle);

    public final void notifyCredentialStorageChanged() {
        Log.d(TAG, "notifyCredentialStorageChanged ");
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new UcmAgentServiceWrapper(this, 0);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    public abstract Bundle setCredentialStorageProperty(int i, int i2, Bundle bundle);

    public abstract Bundle setKeyguardPinMaximumLength(int i);

    public abstract Bundle setKeyguardPinMaximumRetryCount(int i);

    public abstract Bundle setKeyguardPinMinimumLength(int i);

    public abstract Bundle setState(int i);

    public abstract Bundle unwrapDek(byte[] bArr);

    public abstract Bundle verifyPassword(String str);

    public abstract Bundle verifyPin(int i, String str, Bundle bundle);

    public abstract Bundle verifyPuk(String str, String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public final Bundle encrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        Cipher cipher;
        byte[] doFinal;
        Log.d(TAG, "encrypt");
        if (bundle == null) {
            Log.e(TAG, "encrypt. property is null");
            return responseErrorWithNullBytes(16);
        }
        StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("encrypt: ", str, ", algorithm: ", str2, ", uid: ");
        m87m.append(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_CALLER_UID));
        m87m.append(", caller: ");
        m87m.append(Binder.getCallingUid());
        Log.i(TAG, m87m.toString());
        if (bArr == null) {
            Log.e(TAG, "encrypt. data is null");
            return responseErrorWithNullBytes(16);
        }
        Log.d(TAG, "data length " + bArr.length);
        Bundle bundle2 = new Bundle();
        try {
            com.samsung.android.knox.ucm.plugin.service.KeyStore keyStore = com.samsung.android.knox.ucm.plugin.service.KeyStore.getInstance("KNOX", this.mProvider);
            keyStore.setProperty(bundle);
            keyStore.load(new UcmAgentLoadParameter(bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_OWNER_ID), bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_RESOURCE_ID), bundle.getBundle(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_EXTRA_ARGS)));
            KeyStore.Entry entry = keyStore.getEntry(str, null);
            if (entry == null) {
                Log.e(TAG, "encrypt. getEntry null");
                return responseErrorWithNullBytes(keyStore.getErrorStatus() == 0 ? 8 : keyStore.getErrorStatus());
            }
            int i = 10;
            if (TextUtils.isEmpty(str2) || !str2.toUpperCase().startsWith("AES/")) {
                PrivateKey privateKey = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
                if (privateKey == null) {
                    Log.e(TAG, "encrypt. getPrivateKey null ");
                    if (keyStore.getErrorStatus() != 0) {
                        i = keyStore.getErrorStatus();
                    }
                    return responseErrorWithNullBytes(i);
                }
                cipher = Cipher.getInstance(UcmAgentProviderImpl.CIPHER_RSA_ECB_PKCS1PADDING, this.mProvider);
                cipher.setProperty(bundle);
                cipher.init(1, privateKey);
                doFinal = cipher.doFinal(bArr);
            } else {
                SecretKey secretKey = ((KeyStore.SecretKeyEntry) entry).getSecretKey();
                if (secretKey == null) {
                    Log.e(TAG, "encrypt. getSecretKey null!");
                    if (keyStore.getErrorStatus() != 0) {
                        i = keyStore.getErrorStatus();
                    }
                    return responseErrorWithNullBytes(i);
                }
                cipher = Cipher.getInstance(str2, this.mProvider);
                cipher.setProperty(bundle);
                cipher.init(1, secretKey);
                if ("GCM".equals(str2.split("/")[1]) && bundle.containsKey(KEY_EXTRA_AAD)) {
                    cipher.updateAAD(bundle.getByteArray(KEY_EXTRA_AAD));
                }
                doFinal = cipher.doFinal(bArr);
            }
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, doFinal);
            bundle2.putInt(PLUGIN_ERROR_CODE, cipher.getErrorStatus());
            return bundle2;
        } catch (IOException e) {
            e.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 269);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 264);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (NullPointerException e3) {
            e3.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (UnsupportedOperationException e4) {
            e4.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 3);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (InvalidKeyException e5) {
            e5.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 263);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (KeyStoreException e6) {
            e6.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 266);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (NoSuchAlgorithmException e7) {
            e7.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 2);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (UnrecoverableEntryException e8) {
            e8.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 265);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (CertificateException e9) {
            e9.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 261);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (BadPaddingException e10) {
            e10.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 267);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        } catch (IllegalBlockSizeException e11) {
            e11.printStackTrace();
            bundle2.putInt(PLUGIN_ERROR_CODE, 268);
            bundle2.putByteArray(PLUGIN_BYTEARRAY_RESPONSE, null);
            return bundle2;
        }
    }

    private Bundle responseErrorWithBoolean(int i) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(PLUGIN_BOOLEAN_RESPONSE, false);
        bundle.putInt(PLUGIN_ERROR_CODE, i);
        return bundle;
    }
}
