package com.android.settingslib.wifi;

import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkScoreManager;
import android.net.NetworkScorerAppData;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.samsung.android.wifi.SemWifiApContentProviderHelper;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AccessPointPreference extends Preference {
    public final AccessPoint mAccessPoint;
    public Drawable mBadge;
    public final UserBadgeCache mBadgeCache;
    public final int mBadgePadding;
    public CharSequence mContentDescription;
    public final int mDefaultIconResId;
    public final boolean mForSavedNetworks;
    public final StateListDrawable mFrictionSld;
    public final IconInjector mIconInjector;
    public int mLevel;
    public final RunnableC09431 mNotifyChanged;
    public TextView mTitleView;
    public int mWifiSpeed;
    public static final int[] STATE_SECURED = {R.attr.state_encrypted};
    public static final int[] STATE_METERED = {R.attr.state_metered};
    public static final int[] FRICTION_ATTRS = {R.attr.wifi_friction};
    public static final int[] WIFI_CONNECTION_STRENGTH = {R.string.accessibility_no_wifi, R.string.accessibility_wifi_one_bar, R.string.accessibility_wifi_two_bars, R.string.accessibility_wifi_three_bars, R.string.accessibility_wifi_signal_full};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IconInjector {
        public final Context mContext;

        public IconInjector(Context context) {
            this.mContext = context;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UserBadgeCache {
        public final SparseArray mBadges = new SparseArray();
        public final PackageManager mPm;

        public UserBadgeCache(PackageManager packageManager) {
            this.mPm = packageManager;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AccessPointPreference(AccessPoint accessPoint, Context context, UserBadgeCache userBadgeCache, int i, boolean z) {
        this(accessPoint, context, userBadgeCache, i, z, r1 != null ? (StateListDrawable) r1.getDrawable(0) : null, -1, new IconInjector(context));
        TypedArray typedArray;
        try {
            typedArray = context.getTheme().obtainStyledAttributes(FRICTION_ATTRS);
        } catch (Resources.NotFoundException unused) {
            typedArray = null;
        }
    }

    public static CharSequence buildContentDescription(Context context, Preference preference, AccessPoint accessPoint) {
        CharSequence title = preference.getTitle();
        CharSequence summary = preference.getSummary();
        if (!TextUtils.isEmpty(summary)) {
            title = TextUtils.concat(title, ",", summary);
        }
        int level = accessPoint.getLevel();
        if (level >= 0 && level < 5) {
            title = TextUtils.concat(title, ",", context.getString(WIFI_CONNECTION_STRENGTH[level]));
        }
        CharSequence[] charSequenceArr = new CharSequence[3];
        charSequenceArr[0] = title;
        charSequenceArr[1] = ",";
        charSequenceArr[2] = accessPoint.security == 0 ? context.getString(R.string.accessibility_wifi_security_type_none) : context.getString(R.string.accessibility_wifi_security_type_secured);
        return TextUtils.concat(charSequenceArr);
    }

    public static void setTitle(AccessPointPreference accessPointPreference, AccessPoint accessPoint) {
        accessPointPreference.setTitle(accessPoint.getTitle());
    }

    @Override // androidx.preference.Preference
    public final void notifyChanged() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            super.notifyChanged();
            return;
        }
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.post(this.mNotifyChanged);
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        StateListDrawable stateListDrawable;
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mAccessPoint == null) {
            return;
        }
        Drawable icon = getIcon();
        if (icon != null) {
            icon.setLevel(this.mLevel);
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        this.mTitleView = textView;
        if (textView != null) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, this.mBadge, (Drawable) null);
            this.mTitleView.setCompoundDrawablePadding(this.mBadgePadding);
        }
        preferenceViewHolder.itemView.setContentDescription(this.mContentDescription);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.friction_icon);
        if (imageView != null && (stateListDrawable = this.mFrictionSld) != null) {
            AccessPoint accessPoint = this.mAccessPoint;
            int i = accessPoint.security;
            if (i == 0 || i == 4) {
                if (accessPoint.mIsScoredNetworkMetered || WifiConfiguration.isMetered(accessPoint.mConfig, accessPoint.mInfo)) {
                    this.mFrictionSld.setState(STATE_METERED);
                }
            } else {
                stateListDrawable.setState(STATE_SECURED);
            }
            imageView.setImageDrawable(this.mFrictionSld.getCurrent());
        }
        preferenceViewHolder.findViewById(R.id.two_target_divider).setVisibility(4);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settingslib.wifi.AccessPointPreference$1] */
    public AccessPointPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mForSavedNetworks = false;
        this.mWifiSpeed = 0;
        this.mNotifyChanged = new Runnable() { // from class: com.android.settingslib.wifi.AccessPointPreference.1
            @Override // java.lang.Runnable
            public final void run() {
                AccessPointPreference.this.notifyChanged();
            }
        };
        this.mFrictionSld = null;
        this.mBadgePadding = 0;
        this.mBadgeCache = null;
        this.mIconInjector = new IconInjector(context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:110:0x055f, code lost:
    
        if ((r9 != 0) == false) goto L246;
     */
    /* JADX WARN: Removed duplicated region for block: B:155:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0317 A[Catch: Exception -> 0x0354, TryCatch #2 {Exception -> 0x0354, blocks: (B:158:0x0305, B:160:0x0309, B:162:0x0313, B:164:0x0317, B:165:0x0324, B:167:0x0332), top: B:157:0x0305 }] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0332 A[Catch: Exception -> 0x0354, TRY_LEAVE, TryCatch #2 {Exception -> 0x0354, blocks: (B:158:0x0305, B:160:0x0309, B:162:0x0313, B:164:0x0317, B:165:0x0324, B:167:0x0332), top: B:157:0x0305 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AccessPointPreference(AccessPoint accessPoint, Context context, UserBadgeCache userBadgeCache, boolean z) {
        this(accessPoint, context, userBadgeCache, 0, z);
        NetworkCapabilities networkCapabilities;
        String str;
        WifiInfo wifiInfo;
        CharSequence charSequence;
        String sb;
        String string;
        Drawable drawable;
        setTitle(this, this.mAccessPoint);
        Context context2 = this.mContext;
        int level = this.mAccessPoint.getLevel();
        int i = this.mAccessPoint.mSpeed;
        ApplicationInfo applicationInfo = null;
        if (level != this.mLevel || i != this.mWifiSpeed) {
            this.mLevel = level;
            this.mWifiSpeed = i;
            if (level == -1) {
                int i2 = this.mDefaultIconResId;
                if (i2 != 0) {
                    setIcon(AppCompatResources.getDrawable(i2, this.mContext));
                    this.mIconResId = i2;
                } else {
                    setIcon(null);
                }
            } else {
                MetricsLogger.histogram(context2, "settings_wifi_speed_labels", i);
                IconInjector iconInjector = this.mIconInjector;
                iconInjector.getClass();
                if (level >= 0 && level < 5) {
                    Drawable drawable2 = iconInjector.mContext.getDrawable(Utils.WIFI_PIE[level]);
                    if (!this.mForSavedNetworks && drawable2 != null) {
                        drawable2.setTintList(Utils.getColorAttr(android.R.attr.colorControlNormal, context2));
                        setIcon(drawable2);
                    } else {
                        int i3 = this.mDefaultIconResId;
                        if (i3 != 0) {
                            setIcon(AppCompatResources.getDrawable(i3, this.mContext));
                            this.mIconResId = i3;
                        } else {
                            setIcon(null);
                        }
                    }
                } else {
                    throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("No Wifi icon found for level: ", level));
                }
            }
            notifyChanged();
        }
        WifiConfiguration wifiConfiguration = this.mAccessPoint.mConfig;
        boolean z2 = false;
        if (wifiConfiguration != null) {
            UserBadgeCache userBadgeCache2 = this.mBadgeCache;
            int i4 = wifiConfiguration.creatorUid;
            SparseArray sparseArray = userBadgeCache2.mBadges;
            int indexOfKey = sparseArray.indexOfKey(i4);
            if (indexOfKey < 0) {
                drawable = userBadgeCache2.mPm.getUserBadgeForDensity(new UserHandle(i4), 0);
                sparseArray.put(i4, drawable);
            } else {
                drawable = (Drawable) sparseArray.valueAt(indexOfKey);
            }
            this.mBadge = drawable;
        }
        String str2 = "";
        if (this.mForSavedNetworks) {
            AccessPoint accessPoint2 = this.mAccessPoint;
            WifiConfiguration wifiConfiguration2 = accessPoint2.mConfig;
            if (wifiConfiguration2 != null) {
                PackageManager packageManager = accessPoint2.mContext.getPackageManager();
                String nameForUid = packageManager.getNameForUid(1000);
                int userId = UserHandle.getUserId(wifiConfiguration2.creatorUid);
                String str3 = wifiConfiguration2.creatorName;
                if (str3 != null && str3.equals(nameForUid)) {
                    applicationInfo = accessPoint2.mContext.getApplicationInfo();
                } else {
                    try {
                        applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(wifiConfiguration2.creatorName, 0L, userId);
                    } catch (RemoteException unused) {
                    }
                }
                if (applicationInfo != null && !applicationInfo.packageName.equals(accessPoint2.mContext.getString(R.string.settings_package)) && !applicationInfo.packageName.equals(accessPoint2.mContext.getString(R.string.certinstaller_package))) {
                    str2 = accessPoint2.mContext.getString(R.string.saved_network, applicationInfo.loadLabel(packageManager));
                }
            }
            if (accessPoint2.mPasspointConfigurationVersion == 1) {
                if (accessPoint2.mSubscriptionExpirationTimeInMillis > 0 && System.currentTimeMillis() >= accessPoint2.mSubscriptionExpirationTimeInMillis) {
                    z2 = true;
                }
                if (z2) {
                    str2 = accessPoint2.mContext.getString(R.string.wifi_passpoint_expired);
                }
            }
        } else {
            AccessPoint accessPoint3 = this.mAccessPoint;
            if (accessPoint3.mPasspointConfigurationVersion == 1) {
                if (accessPoint3.mSubscriptionExpirationTimeInMillis > 0 && System.currentTimeMillis() >= accessPoint3.mSubscriptionExpirationTimeInMillis) {
                    sb = accessPoint3.mContext.getString(R.string.wifi_passpoint_expired);
                    str2 = sb;
                }
            }
            StringBuilder sb2 = new StringBuilder();
            if (accessPoint3.mOsuProvider != null) {
                if (accessPoint3.mOsuProvisioningComplete) {
                    sb2.append(accessPoint3.mContext.getString(R.string.osu_sign_up_complete));
                } else {
                    String str4 = accessPoint3.mOsuFailure;
                    if (str4 != null) {
                        sb2.append(str4);
                    } else {
                        String str5 = accessPoint3.mOsuStatus;
                        if (str5 != null) {
                            sb2.append(str5);
                        } else {
                            sb2.append(accessPoint3.mContext.getString(R.string.tap_to_sign_up));
                        }
                    }
                }
            } else if (accessPoint3.isActive()) {
                Context context3 = accessPoint3.mContext;
                NetworkInfo.DetailedState detailedState = accessPoint3.getDetailedState();
                WifiInfo wifiInfo2 = accessPoint3.mInfo;
                boolean z3 = wifiInfo2 != null && wifiInfo2.isEphemeral();
                WifiInfo wifiInfo3 = accessPoint3.mInfo;
                String requestingPackageName = wifiInfo3 != null ? wifiInfo3.getRequestingPackageName() : null;
                if (detailedState == NetworkInfo.DetailedState.CONNECTED) {
                    if (z3 && !TextUtils.isEmpty(requestingPackageName)) {
                        PackageManager packageManager2 = context3.getPackageManager();
                        try {
                            ApplicationInfo applicationInfoAsUser = packageManager2.getApplicationInfoAsUser(requestingPackageName, 0, UserHandle.getUserId(-2));
                            charSequence = str2;
                            if (applicationInfoAsUser != null) {
                                charSequence = applicationInfoAsUser.loadLabel(packageManager2);
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            Log.e("SettingsLib.AccessPoint", "Failed to get app info", e);
                            charSequence = str2;
                        }
                        str = context3.getString(R.string.connected_via_app, charSequence);
                    } else if (z3) {
                        NetworkScorerAppData activeScorer = ((NetworkScoreManager) context3.getSystemService(NetworkScoreManager.class)).getActiveScorer();
                        if (activeScorer != null && activeScorer.getRecommendationServiceLabel() != null) {
                            str = String.format(context3.getString(R.string.connected_via_network_scorer), activeScorer.getRecommendationServiceLabel());
                        } else {
                            str = context3.getString(R.string.connected_via_network_scorer_default);
                        }
                    }
                    sb2.append(str);
                    accessPoint3.smartApUserName = SemWifiApContentProviderHelper.get(accessPoint3.mContext, "auto_hotspot_connected_user");
                    if ((!TextUtils.isEmpty(r0)) && accessPoint3.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                        try {
                            wifiInfo = accessPoint3.mInfo;
                            if (wifiInfo != null && !TextUtils.isEmpty(wifiInfo.getBSSID())) {
                                if (accessPoint3.mSemWifiManager == null) {
                                    accessPoint3.mSemWifiManager = (SemWifiManager) accessPoint3.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
                                }
                                if (accessPoint3.mSemWifiManager.getSmartApConnectedStatus(accessPoint3.mInfo.getBSSID()) == 3) {
                                    sb2.insert(0, accessPoint3.smartApUserName + accessPoint3.mContext.getString(R.string.comma) + " ");
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                ConnectivityManager connectivityManager = (ConnectivityManager) context3.getSystemService("connectivity");
                if (detailedState == NetworkInfo.DetailedState.CONNECTED && (networkCapabilities = connectivityManager.getNetworkCapabilities(((WifiManager) context3.getSystemService(WifiManager.class)).getCurrentNetwork())) != null) {
                    if (networkCapabilities.hasCapability(17)) {
                        str = context3.getString(context3.getResources().getIdentifier("network_available_sign_in", "string", "android"));
                    } else if (networkCapabilities.hasCapability(24)) {
                        str = context3.getString(R.string.wifi_limited_connection);
                    } else if (!networkCapabilities.hasCapability(16)) {
                        Settings.Global.getString(context3.getContentResolver(), "private_dns_mode");
                        if (networkCapabilities.isPrivateDnsBroken()) {
                            str = context3.getString(R.string.private_dns_broken);
                        } else {
                            str = context3.getString(R.string.wifi_connected_no_internet);
                        }
                    }
                    sb2.append(str);
                    accessPoint3.smartApUserName = SemWifiApContentProviderHelper.get(accessPoint3.mContext, "auto_hotspot_connected_user");
                    if (!TextUtils.isEmpty(r0)) {
                        wifiInfo = accessPoint3.mInfo;
                        if (wifiInfo != null) {
                            if (accessPoint3.mSemWifiManager == null) {
                            }
                            if (accessPoint3.mSemWifiManager.getSmartApConnectedStatus(accessPoint3.mInfo.getBSSID()) == 3) {
                            }
                        }
                    }
                }
                if (detailedState == null) {
                    Log.w("SettingsLib.AccessPoint", "state is null, returning empty summary");
                    str = str2;
                } else {
                    String[] stringArray = context3.getResources().getStringArray(R.array.wifi_status);
                    int ordinal = detailedState.ordinal();
                    str = str2;
                    if (ordinal < stringArray.length) {
                        str = str2;
                        if (stringArray[ordinal].length() != 0) {
                            str = String.format(stringArray[ordinal], null);
                        }
                    }
                }
                sb2.append(str);
                accessPoint3.smartApUserName = SemWifiApContentProviderHelper.get(accessPoint3.mContext, "auto_hotspot_connected_user");
                if (!TextUtils.isEmpty(r0)) {
                }
            } else {
                WifiConfiguration wifiConfiguration3 = accessPoint3.mConfig;
                if (wifiConfiguration3 != null && wifiConfiguration3.hasNoInternetAccess()) {
                    sb2.append(accessPoint3.mContext.getString(accessPoint3.mConfig.getNetworkSelectionStatus().getNetworkSelectionStatus() == 2 ? R.string.wifi_no_internet_no_reconnect : R.string.wifi_no_internet));
                } else {
                    WifiConfiguration wifiConfiguration4 = accessPoint3.mConfig;
                    if (wifiConfiguration4 != null && wifiConfiguration4.getNetworkSelectionStatus().getNetworkSelectionStatus() != 0) {
                        int networkSelectionDisableReason = accessPoint3.mConfig.getNetworkSelectionStatus().getNetworkSelectionDisableReason();
                        if (networkSelectionDisableReason == 1) {
                            sb2.append(accessPoint3.mContext.getString(R.string.wifi_disabled_generic));
                        } else if (networkSelectionDisableReason == 2) {
                            sb2.append(accessPoint3.mContext.getString(R.string.wifi_disabled_password_failure));
                        } else if (networkSelectionDisableReason == 3) {
                            sb2.append(accessPoint3.mContext.getString(R.string.wifi_disabled_network_failure));
                        } else if (networkSelectionDisableReason == 8) {
                            sb2.append(accessPoint3.mContext.getString(R.string.wifi_check_password_try_again));
                        }
                    } else if (!(accessPoint3.mRssi != Integer.MIN_VALUE)) {
                        sb2.append(accessPoint3.mContext.getString(R.string.wifi_not_in_range));
                    } else {
                        WifiConfiguration wifiConfiguration5 = accessPoint3.mConfig;
                        if (wifiConfiguration5 != null) {
                            if (wifiConfiguration5.getRecentFailureReason() != 17) {
                                sb2.append(accessPoint3.mContext.getString(R.string.wifi_remembered));
                            } else {
                                sb2.append(accessPoint3.mContext.getString(R.string.wifi_ap_unable_to_handle_new_sta));
                            }
                        }
                    }
                }
            }
            if (WifiTracker.sVerboseLogging) {
                WifiConfiguration wifiConfiguration6 = accessPoint3.mConfig;
                StringBuilder sb3 = new StringBuilder();
                WifiInfo wifiInfo4 = accessPoint3.mInfo;
                if (accessPoint3.isActive() && wifiInfo4 != null) {
                    sb3.append(" f=" + Integer.toString(wifiInfo4.getFrequency()));
                }
                sb3.append(" " + WifiUtils.getVisibilityStatus(accessPoint3));
                if (wifiConfiguration6 != null && wifiConfiguration6.getNetworkSelectionStatus().getNetworkSelectionStatus() != 0) {
                    sb3.append(" (" + wifiConfiguration6.getNetworkSelectionStatus().getNetworkStatusString());
                    if (wifiConfiguration6.getNetworkSelectionStatus().getDisableTime() > 0) {
                        long currentTimeMillis = (System.currentTimeMillis() - wifiConfiguration6.getNetworkSelectionStatus().getDisableTime()) / 1000;
                        long j = currentTimeMillis % 60;
                        long j2 = (currentTimeMillis / 60) % 60;
                        long j3 = (j2 / 60) % 60;
                        sb3.append(", ");
                        if (j3 > 0) {
                            sb3.append(Long.toString(j3) + "h ");
                        }
                        sb3.append(Long.toString(j2) + "m ");
                        sb3.append(Long.toString(j) + "s ");
                    }
                    sb3.append(")");
                }
                if (wifiConfiguration6 != null) {
                    WifiConfiguration.NetworkSelectionStatus networkSelectionStatus = wifiConfiguration6.getNetworkSelectionStatus();
                    for (int i5 = 0; i5 <= WifiConfiguration.NetworkSelectionStatus.getMaxNetworkSelectionDisableReason(); i5++) {
                        if (networkSelectionStatus.getDisableReasonCounter(i5) != 0) {
                            sb3.append(" ");
                            sb3.append(WifiConfiguration.NetworkSelectionStatus.getNetworkSelectionDisableReasonString(i5));
                            sb3.append("=");
                            sb3.append(networkSelectionStatus.getDisableReasonCounter(i5));
                        }
                    }
                }
                sb2.append(sb3.toString());
            }
            WifiConfiguration wifiConfiguration7 = accessPoint3.mConfig;
            if (wifiConfiguration7 != null) {
                if ((wifiConfiguration7.meteredOverride != 0) || wifiConfiguration7.meteredHint) {
                    Resources resources = accessPoint3.mContext.getResources();
                    Object[] objArr = new Object[2];
                    Context context4 = accessPoint3.mContext;
                    WifiConfiguration wifiConfiguration8 = accessPoint3.mConfig;
                    int i6 = wifiConfiguration8.meteredOverride;
                    if (i6 != 1) {
                        if (wifiConfiguration8.meteredHint) {
                        }
                        string = context4.getString(R.string.wifi_unmetered_label);
                        objArr[0] = string;
                        objArr[1] = sb2.toString();
                        sb = resources.getString(R.string.preference_summary_default_combination, objArr);
                        str2 = sb;
                    }
                    string = context4.getString(R.string.wifi_metered_label);
                    objArr[0] = string;
                    objArr[1] = sb2.toString();
                    sb = resources.getString(R.string.preference_summary_default_combination, objArr);
                    str2 = sb;
                }
            }
            if (AccessPoint.getSpeedLabel(accessPoint3.mSpeed, accessPoint3.mContext) != null && sb2.length() != 0) {
                sb = accessPoint3.mContext.getResources().getString(R.string.preference_summary_default_combination, AccessPoint.getSpeedLabel(accessPoint3.mSpeed, accessPoint3.mContext), sb2.toString());
            } else if (AccessPoint.getSpeedLabel(accessPoint3.mSpeed, accessPoint3.mContext) != null) {
                sb = AccessPoint.getSpeedLabel(accessPoint3.mSpeed, accessPoint3.mContext);
            } else {
                sb = sb2.toString();
            }
            str2 = sb;
        }
        setSummary(str2);
        this.mContentDescription = buildContentDescription(this.mContext, this, this.mAccessPoint);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settingslib.wifi.AccessPointPreference$1] */
    public AccessPointPreference(AccessPoint accessPoint, Context context, UserBadgeCache userBadgeCache, int i, boolean z, StateListDrawable stateListDrawable, int i2, IconInjector iconInjector) {
        super(context);
        this.mForSavedNetworks = false;
        this.mWifiSpeed = 0;
        this.mNotifyChanged = new Runnable() { // from class: com.android.settingslib.wifi.AccessPointPreference.1
            @Override // java.lang.Runnable
            public final void run() {
                AccessPointPreference.this.notifyChanged();
            }
        };
        this.mLayoutResId = R.layout.preference_access_point;
        this.mWidgetLayoutResId = R.layout.access_point_friction_widget;
        this.mBadgeCache = userBadgeCache;
        this.mAccessPoint = accessPoint;
        this.mForSavedNetworks = z;
        accessPoint.getClass();
        this.mLevel = i2;
        this.mDefaultIconResId = i;
        this.mFrictionSld = stateListDrawable;
        this.mIconInjector = iconInjector;
        this.mBadgePadding = context.getResources().getDimensionPixelSize(R.dimen.wifi_preference_badge_padding);
    }
}
