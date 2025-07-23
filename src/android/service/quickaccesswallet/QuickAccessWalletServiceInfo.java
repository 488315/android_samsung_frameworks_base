package android.service.quickaccesswallet;

import android.Manifest;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Xml;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import java.io.IOException;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
class QuickAccessWalletServiceInfo {
    private static final String TAG = "QAWalletSInfo";
    private static final String TAG_WALLET_SERVICE = "quickaccesswallet-service";
    private final ServiceInfo mServiceInfo;
    private final ServiceMetadata mServiceMetadata;
    private final TileServiceMetadata mTileServiceMetadata;
    private final int mUserId;

    private QuickAccessWalletServiceInfo(ServiceInfo serviceInfo, ServiceMetadata serviceMetadata, TileServiceMetadata tileServiceMetadata, int i) {
        this.mServiceInfo = serviceInfo;
        this.mServiceMetadata = serviceMetadata;
        this.mTileServiceMetadata = tileServiceMetadata;
        this.mUserId = i;
    }

    static QuickAccessWalletServiceInfo tryCreate(Context context) {
        String packageName;
        ServiceInfo walletServiceInfo;
        int myUserId = UserHandle.myUserId();
        if (isWalletRoleAvailable(context)) {
            Pair<String, Integer> defaultWalletApp = getDefaultWalletApp(context);
            packageName = defaultWalletApp.first;
            myUserId = defaultWalletApp.second.intValue();
        } else {
            ComponentName defaultPaymentApp = getDefaultPaymentApp(context);
            if (defaultPaymentApp == null) {
                return null;
            }
            packageName = defaultPaymentApp.getPackageName();
        }
        if (packageName == null || myUserId < 0 || (walletServiceInfo = getWalletServiceInfo(context, packageName, myUserId)) == null) {
            return null;
        }
        if (!Manifest.permission.BIND_QUICK_ACCESS_WALLET_SERVICE.equals(walletServiceInfo.permission)) {
            Log.w(TAG, String.format("%s.%s does not require permission %s", walletServiceInfo.packageName, walletServiceInfo.name, Manifest.permission.BIND_QUICK_ACCESS_WALLET_SERVICE));
            return null;
        }
        return new QuickAccessWalletServiceInfo(walletServiceInfo, parseServiceMetadata(context, walletServiceInfo), new TileServiceMetadata(parseTileServiceMetadata(context, walletServiceInfo)), myUserId);
    }

    private static Pair<String, Integer> getDefaultWalletApp(Context context) {
        UserHandle of = UserHandle.of(UserHandle.myUserId());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
            String str = null;
            if (Flags.walletRoleCrossUserEnabled() && com.android.internal.hidden_from_bootclasspath.com.android.permission.flags.Flags.crossUserRoleEnabled() && context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) == 0 && (of = roleManager.getActiveUserForRole("android.app.role.WALLET")) == null) {
                return new Pair<>(null, Integer.valueOf(UserHandle.myUserId()));
            }
            List roleHoldersAsUser = roleManager.getRoleHoldersAsUser("android.app.role.WALLET", of);
            if (!roleHoldersAsUser.isEmpty()) {
                str = (String) roleHoldersAsUser.get(0);
            }
            return new Pair<>(str, Integer.valueOf(of.getIdentifier()));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static boolean isWalletRoleAvailable(Context context) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((RoleManager) context.getSystemService(RoleManager.class)).isRoleAvailable("android.app.role.WALLET");
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static ComponentName getDefaultPaymentApp(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "nfc_payment_default_component");
        if (string == null) {
            return null;
        }
        return ComponentName.unflattenFromString(string);
    }

    private static ServiceInfo getWalletServiceInfo(Context context, String str, int i) {
        Intent intent = new Intent(QuickAccessWalletService.SERVICE_INTERFACE);
        intent.setPackage(str);
        List<ResolveInfo> queryIntentServicesAsUser = context.getPackageManager().queryIntentServicesAsUser(intent, 852096, i);
        if (queryIntentServicesAsUser.isEmpty()) {
            return null;
        }
        return queryIntentServicesAsUser.get(0).serviceInfo;
    }

    private static class TileServiceMetadata {
        private final Drawable mTileIcon;

        private TileServiceMetadata(Drawable drawable) {
            this.mTileIcon = drawable;
        }
    }

    private static Drawable parseTileServiceMetadata(Context context, ServiceInfo serviceInfo) {
        PackageManager packageManager = context.getPackageManager();
        int i = serviceInfo.metaData.getInt(QuickAccessWalletService.TILE_SERVICE_META_DATA);
        if (i != 0) {
            try {
                return packageManager.getResourcesForApplication(serviceInfo.applicationInfo).getDrawable(i, null);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Error parsing quickaccesswallet tile service meta-data", e);
            }
        }
        return null;
    }

    static class ServiceMetadata {
        private final String mSettingsActivity;
        private final CharSequence mShortcutLongLabel;
        private final CharSequence mShortcutShortLabel;
        private final String mTargetActivity;

        /* JADX INFO: Access modifiers changed from: private */
        public static ServiceMetadata empty() {
            return new ServiceMetadata(null, null, null, null);
        }

        private ServiceMetadata(String str, String str2, CharSequence charSequence, CharSequence charSequence2) {
            this.mTargetActivity = str;
            this.mSettingsActivity = str2;
            this.mShortcutShortLabel = charSequence;
            this.mShortcutLongLabel = charSequence2;
        }
    }

    static ServiceMetadata parseServiceMetadata(Context context, ServiceInfo serviceInfo) {
        Resources resourcesForApplication;
        PackageManager packageManager = context.getPackageManager();
        XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, QuickAccessWalletService.SERVICE_META_DATA);
        if (loadXmlMetaData == null) {
            return ServiceMetadata.empty();
        }
        try {
            resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
            for (int i = 0; i != 1 && i != 2; i = loadXmlMetaData.next()) {
            }
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
            Log.e(TAG, "Error parsing quickaccesswallet service meta-data", e);
        }
        if (!TAG_WALLET_SERVICE.equals(loadXmlMetaData.getName())) {
            Log.e(TAG, "Meta-data does not start with quickaccesswallet-service tag");
            return ServiceMetadata.empty();
        }
        TypedArray typedArray = null;
        try {
            typedArray = resourcesForApplication.obtainAttributes(Xml.asAttributeSet(loadXmlMetaData), R.styleable.QuickAccessWalletService);
            ServiceMetadata serviceMetadata = new ServiceMetadata(typedArray.getString(0), typedArray.getString(1), typedArray.getText(2), typedArray.getText(3));
            if (typedArray != null) {
                typedArray.recycle();
            }
            return serviceMetadata;
        } finally {
        }
    }

    ComponentName getComponentName() {
        return this.mServiceInfo.getComponentName();
    }

    int getUserId() {
        return this.mUserId;
    }

    String getWalletActivity() {
        return this.mServiceMetadata.mTargetActivity;
    }

    String getSettingsActivity() {
        return this.mServiceMetadata.mSettingsActivity;
    }

    Drawable getWalletLogo(Context context) {
        Drawable loadLogo = this.mServiceInfo.loadLogo(context.getPackageManager());
        return loadLogo != null ? loadLogo : this.mServiceInfo.loadIcon(context.getPackageManager());
    }

    Drawable getTileIcon() {
        return this.mTileServiceMetadata.mTileIcon;
    }

    CharSequence getShortcutShortLabel(Context context) {
        if (!TextUtils.isEmpty(this.mServiceMetadata.mShortcutShortLabel)) {
            return this.mServiceMetadata.mShortcutShortLabel;
        }
        return this.mServiceInfo.loadLabel(context.getPackageManager());
    }

    CharSequence getShortcutLongLabel(Context context) {
        if (!TextUtils.isEmpty(this.mServiceMetadata.mShortcutLongLabel)) {
            return this.mServiceMetadata.mShortcutLongLabel;
        }
        return this.mServiceInfo.loadLabel(context.getPackageManager());
    }

    CharSequence getServiceLabel(Context context) {
        return this.mServiceInfo.loadLabel(context.getPackageManager());
    }
}
