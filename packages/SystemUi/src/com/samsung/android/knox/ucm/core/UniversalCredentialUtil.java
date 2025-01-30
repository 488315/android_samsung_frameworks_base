package com.samsung.android.knox.ucm.core;

import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.jcajce.UcmKeystoreProvider;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.ucm.keystore.UcmKeyStoreHelper;
import com.samsung.ucm.keystore.UcmKeyStoreKeyFactory;
import com.samsung.ucm.keystore.UcmKeyStoreSecretKey;
import java.security.PrivateKey;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.SecretKey;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class UniversalCredentialUtil {
    public static final String AGENT_CONFIGURATORLIST = "configuratorList";
    public static final String AGENT_ENFORCEMANAGEMENT = "enforceManagement";
    public static final String AGENT_ID = "id";
    public static final String AGENT_ISDETACHABLE = "isDetachable";
    public static final String AGENT_ISHARDWAREBACKED = "isHardwareBacked";
    public static final String AGENT_ISMANAGEABLE = "isManageable";
    public static final String AGENT_ISREADONLY = "isReadOnly";
    public static final String AGENT_IS_GENERATE_PASSWORD_AVAILABLE = "isGeneratePasswordAvailable";
    public static final String AGENT_IS_ODE_SUPPORTED = "isODESupport";
    public static final String AGENT_IS_PUK_SUPPORTED = "isPUKSupported";
    public static final String AGENT_IS_SUPPORT_BIOMETRIC_FOR_UCM = "isSupportBiometricForUCM";
    public static final String AGENT_IS_SUPPORT_CHANGE_PIN = "isSupportChangePin";
    public static final String AGENT_IS_SUPPORT_CHANGE_PIN_WITH_PASSWORD = "isSupportChangePinWithPassword";
    public static final String AGENT_PACKAGENAME = "packageName";
    public static final String AGENT_REQUSERVERIFICATION = "reqUserVerification";
    public static final String AGENT_STORAGE_TYPE = "storageType";
    public static final String AGENT_SUMMARY = "summary";
    public static final String AGENT_TITLE = "title";
    public static final String AGENT_VENDORID = "vendorId";
    public static final int CACERT_ENTRY_TYPE = 4;
    public static final String CCM_SOURCE = "CCM";
    public static final boolean DBG = true;
    public static final int EVENT_FACTORY_RESET = 101;
    public static final int INVALID_RESOURCE = -1;
    public static final int INVALID_UID = -1;
    public static final String KNOX_SOURCE = "KNOX";
    public static final String ODE_ENABLED = "odeEnabled";
    public static final String ODE_SIGNATURE = "odeSignature";
    public static final int PRIVATEKEY_ENTRY_TYPE = 1;
    public static final int SECRETKEY_ENTRY_TYPE = 2;
    public static final String SERVICE_NAME = "com.samsung.ucs.ucsservice";
    public static final String TAG = "UniversalCredentialUtil";
    public static final String UCM_VERSION_1 = "v1";
    public static final String UCM_VERSION_2 = "v2";
    public static final int UCS_ALL_RESOURCE = 4;
    public static final String UCS_KEYCHAIN_SCHEME = "ucmkeychain";
    public static final int UCS_PRIVATE_KEYCHAIN_RESOURCE = 2;
    public static final int UCS_PUBLIC_KEYCHAIN_RESOURCE = 1;
    public static final String UCS_SCHEME = "ucmkeychain";
    public static final String UCS_SSL_ENGINE = "ucsengine";
    public static final int UCS_WIFI_KEYCHAIN_RESOURCE = 3;
    public static final int UID_SELF = -1;
    public static final String UNIQUE_ID = "uniqueId";
    public final IUcmService mBinder;
    public ContextInfo mContextInfo = new ContextInfo(Process.myUid());

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class UcmUri {
        public static final int ALIAS_PATH = 4;
        public static final int RESOURCE_PATH = 5;
        public static final int SOURCE_PATH = 2;
        public static final int USER_PATH = 3;
        public static final int VERSION_PATH = 1;
        public UriMatcher matcher = new UriMatcher(-1);
        public Uri uri;

        public UcmUri(String str) {
            if (str == null) {
                throw new NullPointerException("uri is null");
            }
            Log.d(UniversalCredentialUtil.TAG, "uri:".concat(str));
            this.uri = Uri.parse(str);
        }

        public final String getRawAlias() {
            return this.uri.getLastPathSegment();
        }

        public final int getResourceId() {
            List<String> pathSegments = this.uri.getPathSegments();
            if (pathSegments == null) {
                throw new IllegalArgumentException("resource is not known");
            }
            try {
                String str = pathSegments.get(1);
                Log.d(UniversalCredentialUtil.TAG, "resource:" + str);
                int parseInt = Integer.parseInt(str);
                if (parseInt == 1 || parseInt == 2 || parseInt == 3 || parseInt == 4) {
                    return parseInt;
                }
                return -1;
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return -1;
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
                return -1;
            }
        }

        public final String getSource() {
            List<String> pathSegments = this.uri.getPathSegments();
            if (pathSegments == null) {
                throw new IllegalArgumentException("Source is not known");
            }
            try {
                String str = pathSegments.get(0);
                Log.d(UniversalCredentialUtil.TAG, "resource:" + str);
                return str;
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return null;
            }
        }

        public final int getUid() {
            List<String> pathSegments = this.uri.getPathSegments();
            if (pathSegments == null) {
                throw new IllegalArgumentException("user is not known");
            }
            try {
                String str = pathSegments.get(2);
                Log.d(UniversalCredentialUtil.TAG, "uid : " + str);
                return Integer.parseInt(str);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return -1;
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
                return -1;
            }
        }

        public final int getUserId() {
            List<String> pathSegments = this.uri.getPathSegments();
            if (pathSegments == null) {
                throw new IllegalArgumentException("user is not known");
            }
            try {
                String str = pathSegments.get(3);
                Log.d(UniversalCredentialUtil.TAG, "userid : " + str);
                return Integer.parseInt(str);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return -1;
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
                return -1;
            }
        }

        public final int match() {
            String authority = this.uri.getAuthority();
            AbstractC0000x2c234b15.m3m("authority:", authority, UniversalCredentialUtil.TAG);
            this.matcher.addURI(authority, "#/#", 3);
            this.matcher.addURI(authority, "#/#/*", 4);
            this.matcher.addURI(authority, "#", 5);
            return this.matcher.match(this.uri);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class UcmUriBuilder {
        public String alias;
        public String resourceId;
        public String source;
        public String uid;
        public String user;
        public String version;

        public UcmUriBuilder(String str) {
            if (str == null) {
                throw new NullPointerException("source is null");
            }
            this.version = "v1";
            this.source = str;
        }

        public final String build() {
            String str;
            if (this.source == null) {
                throw new IllegalArgumentException("source is null");
            }
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("ucmkeychain").authority(this.version);
            builder.path(this.source);
            if (this.version.equals("v1") && (str = this.resourceId) != null) {
                builder.appendPath(str);
                if (this.uid == null) {
                    this.uid = String.valueOf(Process.myUid());
                }
                builder.appendPath(this.uid);
                String str2 = this.user;
                if (str2 != null) {
                    builder.appendPath(str2);
                }
                String str3 = this.alias;
                if (str3 != null) {
                    builder.appendPath(str3);
                }
            }
            Uri build = builder.build();
            Log.d(UniversalCredentialUtil.TAG, "uri created : " + build.toString());
            return build.toString();
        }

        public final UcmUriBuilder setAlias(String str) {
            if (str == null) {
                throw new NullPointerException("alias is null");
            }
            this.alias = str;
            return this;
        }

        public final UcmUriBuilder setResourceId(int i) {
            if (i != 1 && i != 2 && i != 3 && i != 4) {
                throw new IllegalArgumentException("resourceId not known");
            }
            this.resourceId = String.valueOf(i);
            return this;
        }

        public final UcmUriBuilder setUid(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("uid is not known");
            }
            this.uid = String.valueOf(i);
            return this;
        }

        public final UcmUriBuilder setUser(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("user is not known");
            }
            this.user = String.valueOf(i);
            return this;
        }
    }

    private UniversalCredentialUtil(IUcmService iUcmService) {
        this.mBinder = iUcmService;
    }

    public static UniversalCredentialUtil getInstance() {
        IUcmService asInterface = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            return null;
        }
        return new UniversalCredentialUtil(asInterface);
    }

    public static String getKeychainUri(String str, String str2, int i, int i2) {
        return new UcmUriBuilder(str).setResourceId(1).setUid(i).setUser(i2).setAlias(str2).build();
    }

    public static String getRawAlias(String str) {
        return Uri.parse(str).getLastPathSegment();
    }

    public static String getSource(String str) {
        List<String> pathSegments = Uri.parse(str).getPathSegments();
        if (pathSegments == null) {
            throw new IllegalArgumentException("Source is not known. Invalid URI.");
        }
        try {
            String str2 = pathSegments.get(0);
            Log.d(TAG, "resource:" + str2);
            return str2;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUCMVersion() {
        return "v1";
    }

    public static String getUri(String str, String str2) {
        return new UcmUriBuilder(str).setResourceId(1).setUid(Process.myUid()).setAlias(str2).build();
    }

    public static boolean isKeyChainUri(String str) {
        Uri parse;
        String scheme;
        return (str == null || (parse = Uri.parse(str)) == null || (scheme = parse.getScheme()) == null || !scheme.equals("ucmkeychain")) ? false : true;
    }

    public static boolean isUcsStoreUri(String str) {
        Uri parse;
        String scheme;
        return (str == null || (parse = Uri.parse(str)) == null || (scheme = parse.getScheme()) == null || !scheme.equals("ucmkeychain")) ? false : true;
    }

    public static boolean isValidUri(String str) {
        return isKeyChainUri(str);
    }

    public final Bundle APDUCommand(String str, byte[] bArr, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialUtil.APDUCommand");
        Log.d(TAG, "APDUCommand");
        IUcmService iUcmService = this.mBinder;
        if (iUcmService == null) {
            Log.d(TAG, "binder is null");
            return null;
        }
        try {
            return iUcmService.APDUCommand(str, bArr, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final void addUcmProvider() {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialUtil.addUcmProvider");
        Log.d(TAG, "addUcmProvider");
        UcmKeyStoreHelper.addUcmProvider();
    }

    public final Bundle changePin(String str, String str2, String str3) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialUtil.changePin");
        Log.d(TAG, "...changePin");
        IUcmService iUcmService = this.mBinder;
        if (iUcmService == null) {
            Log.d(TAG, "binder is null");
            return null;
        }
        try {
            return iUcmService.changePin(str, str2, str3, true);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final Bundle delete(String str) {
        try {
            Bundle delete = this.mBinder.delete(str);
            if (delete == null) {
                Log.d(TAG, "response is null");
                return null;
            }
            Log.d(TAG, "UCMERRORTESTING: @UniversalCredentialUtil responding to delete with error code  = " + delete.getInt(UcmAgentService.PLUGIN_ERROR_CODE));
            return delete;
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final Bundle generateKey(String str, String str2, int i, Bundle bundle) {
        try {
            return this.mBinder.generateKey(str, str2, i, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) {
        try {
            return this.mBinder.generateKeyPair(str, str2, i, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final Bundle generateKeyPairInternal(String str, String str2, int i, Bundle bundle) {
        try {
            return this.mBinder.generateKeyPairInternal(str, str2, i, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final Bundle generateSecureRandom(String str, int i, byte[] bArr) {
        try {
            return this.mBinder.generateSecureRandom(str, i, bArr);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final Bundle getCertificateChain(String str) {
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("Credential Manager calling getCertificateChain for ", str, TAG);
        try {
            ucmRetParcelable certificateChain = this.mBinder.getCertificateChain(str);
            if (certificateChain == null) {
                Log.d(TAG, "parcel is null");
                return null;
            }
            int i = certificateChain.mResult;
            byte[] bArr = certificateChain.mData;
            Bundle bundle = new Bundle();
            bundle.putInt(UcmAgentService.PLUGIN_ERROR_CODE, i);
            bundle.putByteArray(UcmAgentService.PLUGIN_BYTEARRAY_RESPONSE, bArr);
            return bundle;
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final String getFriendlyName(String str) {
        try {
            return this.mBinder.getAgentInfo(new UcmUriBuilder(str).build()).getString(AGENT_TITLE);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final Bundle getInfo(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "UniversalCredentialUtil.getInfo");
        Log.d(TAG, "getInfo");
        IUcmService iUcmService = this.mBinder;
        if (iUcmService == null) {
            Log.d(TAG, "binder is null");
            return null;
        }
        try {
            return iUcmService.getInfo(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final Bundle getKeyType(String str) {
        try {
            return this.mBinder.getKeyType(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final PrivateKey getPrivateKey(String str) {
        Log.i(TAG, "Credential Manager calling getPrivateKey for " + str);
        Bundle certificateChain = getCertificateChain(str);
        if (certificateChain == null || certificateChain.getInt(UcmAgentService.PLUGIN_ERROR_CODE) != 0 || certificateChain.getByteArray(UcmAgentService.PLUGIN_BYTEARRAY_RESPONSE) == null) {
            return null;
        }
        return UcmKeyStoreKeyFactory.getPrivateKey(str, certificateChain.getByteArray(UcmAgentService.PLUGIN_BYTEARRAY_RESPONSE));
    }

    public final Provider getProvider(String str) {
        try {
            return getProviderInfo(str, this.mBinder.getAgentInfo(new UcmUriBuilder(str).build()));
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final UcmKeystoreProvider getProviderInfo(String str, Bundle bundle) {
        UcmKeystoreProvider ucmKeystoreProvider = new UcmKeystoreProvider(str, bundle);
        StringBuilder sb = new StringBuilder("DETAILED agentInfo information for provider: AGENT_ID = ");
        sb.append(bundle.getString("id", ""));
        sb.append(" AGENT_SUMMARY = ");
        sb.append(bundle.getString(AGENT_SUMMARY, ""));
        sb.append(" AGENT_TITLE = ");
        sb.append(bundle.getString(AGENT_TITLE, ""));
        sb.append(" AGENT_VENDORID = ");
        sb.append(bundle.getString(AGENT_VENDORID, ""));
        sb.append(" AGENT_ISDETACHABLE = ");
        sb.append(((Boolean) bundle.get(AGENT_ISDETACHABLE)).toString());
        sb.append(" AGENT_PACKAGENAME = ");
        ExifInterface$$ExternalSyntheticOutline0.m35m(sb, (String) bundle.get("packageName"), TAG);
        return ucmKeystoreProvider;
    }

    public final Provider[] getProviders() {
        Log.d(TAG, "getProviders");
        try {
            Bundle[] listProviders = this.mBinder.listProviders();
            if (listProviders == null || listProviders.length == 0) {
                Log.d(TAG, "Provider list is empty");
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Bundle bundle : listProviders) {
                String string = bundle.getString(UNIQUE_ID);
                if (string == null) {
                    Log.d(TAG, "NULL agent ID name Returned for bundle");
                } else {
                    arrayList.add(getProviderInfo(string, bundle));
                }
            }
            return (Provider[]) arrayList.toArray(new Provider[arrayList.size()]);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final SecretKey getSecretKey(String str, String str2) {
        Log.i(TAG, "Credential Manager calling getSecretKey for " + str + " / " + str2);
        return new UcmKeyStoreSecretKey(str, str2);
    }

    public final boolean grantKeychainAccess(String str, int i) {
        try {
            return this.mBinder.grantKeyChainAccess(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return false;
        }
    }

    public final Bundle importKey(String str, Bundle bundle) {
        Bundle bundle2;
        String str2;
        try {
            bundle2 = this.mBinder.importKey(str, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            bundle2 = null;
        }
        StringBuilder sb = new StringBuilder("UCMERRORTESTING: @UniversalCredentialUtil responding to importKey ");
        if (bundle2 == null) {
            str2 = "null";
        } else {
            str2 = "Not null, error code = " + bundle2.getInt(UcmAgentService.PLUGIN_ERROR_CODE);
        }
        ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str2, TAG);
        return bundle2;
    }

    public final Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
        try {
            Bundle importKeyPair = this.mBinder.importKeyPair(str, bArr, bArr2, bundle);
            if (importKeyPair == null) {
                Log.d(TAG, "response is null");
                return null;
            }
            Log.d(TAG, "UCMERRORTESTING: @UniversalCredentialUtil responding to importKeyPair with error code  = " + importKeyPair.getInt(UcmAgentService.PLUGIN_ERROR_CODE));
            return importKeyPair;
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final void notifyPluginResult(Bundle bundle) {
        Log.d(TAG, "notifyPluginResult");
        IUcmService iUcmService = this.mBinder;
        if (iUcmService == null) {
            Log.e(TAG, "UCM Service is null");
            return;
        }
        try {
            iUcmService.notifyPluginResult(bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
        }
    }

    public final void refreshUCMPlugin() {
        Log.d(TAG, "refreshUCMPlugin");
        IUcmService iUcmService = this.mBinder;
        if (iUcmService == null) {
            Log.e(TAG, "UCM Service is null");
            return;
        }
        try {
            iUcmService.updateAgentList();
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
        }
    }

    public final Bundle saw(String str, int i) {
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("Credential Manager calling saw for ", str, TAG);
        try {
            Bundle saw = this.mBinder.saw(str, i);
            if (saw == null) {
                Log.d(TAG, "response is null");
                return null;
            }
            Log.d(TAG, "UCMERRORTESTING: @UniversalCredentialUtil responding to saw with error code  = " + saw.getInt(UcmAgentService.PLUGIN_ERROR_CODE));
            return saw;
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public final Bundle setCertificateChain(String str, byte[] bArr) {
        try {
            return this.mBinder.setCertificateChain(str, bArr, null);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot connect to service", e);
            return null;
        }
    }

    public static String getKeychainUri(String str, String str2, int i) {
        return new UcmUriBuilder(str).setResourceId(1).setUid(i).setAlias(str2).build();
    }

    public static String getKeychainUri(String str, String str2) {
        return new UcmUriBuilder(str).setResourceId(1).setUid(Process.myUid()).setAlias(str2).build();
    }
}
