package com.android.settingslib.wifi;

import android.app.ActivityManager;
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
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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
import com.android.settingslib.wifi.WifiUtils;
import com.android.systemui.R;
import com.samsung.android.wifi.SemWifiApContentProviderHelper;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* loaded from: classes.dex */
public class AccessPointPreference extends Preference {
    public final AccessPoint mAccessPoint;
    public final Drawable mBadge;
    public final UserBadgeCache mBadgeCache;
    public final int mBadgePadding;
    public final CharSequence mContentDescription;
    public final int mDefaultIconResId;
    public final boolean mForSavedNetworks;
    public final StateListDrawable mFrictionSld;
    public final IconInjector mIconInjector;
    public final int mLevel;
    public final AnonymousClass1 mNotifyChanged;
    public TextView mTitleView;
    public final int mWifiSpeed;
    public static final int[] STATE_SECURED = {R.attr.state_encrypted};
    public static final int[] STATE_METERED = {R.attr.state_metered};
    public static final int[] FRICTION_ATTRS = {R.attr.wifi_friction};
    public static final int[] WIFI_CONNECTION_STRENGTH = {R.string.accessibility_no_wifi, R.string.accessibility_wifi_one_bar, R.string.accessibility_wifi_two_bars, R.string.accessibility_wifi_three_bars, R.string.accessibility_wifi_signal_full};

    public class IconInjector {
        public final Context mContext;

        public IconInjector(Context context) {
            this.mContext = context;
        }
    }

    public class UserBadgeCache {
        public final SparseArray mBadges = new SparseArray();
        public final PackageManager mPm;

        public UserBadgeCache(PackageManager packageManager) {
            this.mPm = packageManager;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AccessPointPreference(AccessPoint accessPoint, Context context, UserBadgeCache userBadgeCache, int i, boolean z) {
        TypedArray typedArrayObtainStyledAttributes;
        StateListDrawable stateListDrawable = null;
        try {
            typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(FRICTION_ATTRS);
        } catch (Resources.NotFoundException unused) {
            typedArrayObtainStyledAttributes = null;
        }
        if (typedArrayObtainStyledAttributes != null) {
            stateListDrawable = (StateListDrawable) typedArrayObtainStyledAttributes.getDrawable(0);
            typedArrayObtainStyledAttributes.recycle();
        }
        this(accessPoint, context, userBadgeCache, i, z, stateListDrawable, -1, new IconInjector(context));
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
            if (i != 0 && i != 4) {
                stateListDrawable.setState(STATE_SECURED);
            } else if (accessPoint.mIsScoredNetworkMetered || WifiConfiguration.isMetered(accessPoint.mConfig, accessPoint.mInfo)) {
                this.mFrictionSld.setState(STATE_METERED);
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

    /* JADX WARN: Removed duplicated region for block: B:118:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x056c  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0129  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AccessPointPreference(AccessPoint accessPoint, Context context, UserBadgeCache userBadgeCache, boolean z) throws Resources.NotFoundException {
        long j;
        NetworkInfo.DetailedState detailedState;
        NetworkCapabilities networkCapabilities;
        String string;
        String str;
        NetworkInfo.DetailedState detailedState2;
        CharSequence charSequenceLoadLabel;
        String string2;
        String string3;
        String string4;
        Drawable userBadgeForDensity;
        this(accessPoint, context, userBadgeCache, 0, z);
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
                Context context3 = this.mIconInjector.mContext;
                if (level >= 0 && level < 5) {
                    Drawable drawable = context3.getDrawable(Utils.WIFI_PIE[level]);
                    if (!this.mForSavedNetworks && drawable != null) {
                        drawable.setTintList(Utils.getColorAttr(android.R.attr.colorControlNormal, context2));
                        setIcon(drawable);
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
                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(level, "No Wifi icon found for level: "));
                }
            }
            notifyChanged();
        }
        WifiConfiguration wifiConfiguration = this.mAccessPoint.mConfig;
        if (wifiConfiguration != null) {
            UserBadgeCache userBadgeCache2 = this.mBadgeCache;
            int i4 = wifiConfiguration.creatorUid;
            int iIndexOfKey = userBadgeCache2.mBadges.indexOfKey(i4);
            if (iIndexOfKey < 0) {
                userBadgeForDensity = userBadgeCache2.mPm.getUserBadgeForDensity(new UserHandle(i4), 0);
                userBadgeCache2.mBadges.put(i4, userBadgeForDensity);
            } else {
                userBadgeForDensity = (Drawable) userBadgeCache2.mBadges.valueAt(iIndexOfKey);
            }
            this.mBadge = userBadgeForDensity;
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
                    string4 = accessPoint2.mContext.getString(R.string.saved_network, applicationInfo.loadLabel(packageManager));
                } else {
                    string4 = str2;
                    if (accessPoint2.mPasspointConfigurationVersion == 1) {
                        string4 = str2;
                        if (accessPoint2.mSubscriptionExpirationTimeInMillis > 0) {
                            string4 = str2;
                            if (System.currentTimeMillis() >= accessPoint2.mSubscriptionExpirationTimeInMillis) {
                                string4 = accessPoint2.mContext.getString(R.string.wifi_passpoint_expired);
                            }
                        }
                    }
                }
            }
        } else {
            AccessPoint accessPoint3 = this.mAccessPoint;
            if (accessPoint3.mPasspointConfigurationVersion == 1 && accessPoint3.mSubscriptionExpirationTimeInMillis > 0 && System.currentTimeMillis() >= accessPoint3.mSubscriptionExpirationTimeInMillis) {
                string2 = accessPoint3.mContext.getString(R.string.wifi_passpoint_expired);
            } else {
                StringBuilder sb = new StringBuilder();
                if (accessPoint3.mOsuProvider != null) {
                    if (accessPoint3.mOsuProvisioningComplete) {
                        sb.append(accessPoint3.mContext.getString(R.string.osu_sign_up_complete));
                    } else {
                        String str4 = accessPoint3.mOsuFailure;
                        if (str4 != null) {
                            sb.append(str4);
                        } else {
                            String str5 = accessPoint3.mOsuStatus;
                            if (str5 != null) {
                                sb.append(str5);
                            } else {
                                sb.append(accessPoint3.mContext.getString(R.string.tap_to_sign_up));
                            }
                        }
                    }
                    j = 0;
                } else if (accessPoint3.isActive()) {
                    Context context4 = accessPoint3.mContext;
                    NetworkInfo networkInfo = accessPoint3.mNetworkInfo;
                    j = 0;
                    if (networkInfo != null) {
                        detailedState = networkInfo.getDetailedState();
                    } else {
                        Log.w("SettingsLib.AccessPoint", "NetworkInfo is null, cannot return detailed state");
                        detailedState = null;
                    }
                    WifiInfo wifiInfo = accessPoint3.mInfo;
                    boolean z2 = wifiInfo != null && wifiInfo.isEphemeral();
                    WifiInfo wifiInfo2 = accessPoint3.mInfo;
                    String requestingPackageName = wifiInfo2 != null ? wifiInfo2.getRequestingPackageName() : null;
                    NetworkInfo.DetailedState detailedState3 = NetworkInfo.DetailedState.CONNECTED;
                    if (detailedState == detailedState3) {
                        if (z2 && !TextUtils.isEmpty(requestingPackageName)) {
                            PackageManager packageManager2 = context4.getPackageManager();
                            try {
                                ApplicationInfo applicationInfoAsUser = packageManager2.getApplicationInfoAsUser(requestingPackageName, 0, ActivityManager.getCurrentUser());
                                charSequenceLoadLabel = str2;
                                if (applicationInfoAsUser != null) {
                                    charSequenceLoadLabel = applicationInfoAsUser.loadLabel(packageManager2);
                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.e("SettingsLib.AccessPoint", "Failed to get app info", e);
                                charSequenceLoadLabel = str2;
                            }
                            string = context4.getString(R.string.connected_via_app, charSequenceLoadLabel);
                        } else if (z2) {
                            NetworkScorerAppData activeScorer = ((NetworkScoreManager) context4.getSystemService(NetworkScoreManager.class)).getActiveScorer();
                            if (activeScorer != null && activeScorer.getRecommendationServiceLabel() != null) {
                                string = String.format(context4.getString(R.string.connected_via_network_scorer), activeScorer.getRecommendationServiceLabel());
                            } else {
                                string = context4.getString(R.string.connected_via_network_scorer_default);
                            }
                        }
                        sb.append(string);
                        str = SemWifiApContentProviderHelper.get(accessPoint3.mContext, "auto_hotspot_connected_user");
                        accessPoint3.smartApUserName = str;
                        if (!TextUtils.isEmpty(str)) {
                        }
                    } else {
                        ConnectivityManager connectivityManager = (ConnectivityManager) context4.getSystemService("connectivity");
                        if (detailedState == detailedState3 && (networkCapabilities = connectivityManager.getNetworkCapabilities(((WifiManager) context4.getSystemService(WifiManager.class)).getCurrentNetwork())) != null) {
                            if (networkCapabilities.hasCapability(17)) {
                                string = context4.getString(context4.getResources().getIdentifier("network_available_sign_in", "string", "android"));
                            } else if (networkCapabilities.hasCapability(24)) {
                                string = context4.getString(R.string.wifi_limited_connection);
                            } else if (!networkCapabilities.hasCapability(16)) {
                                Settings.Global.getString(context4.getContentResolver(), "private_dns_mode");
                                if (networkCapabilities.isPrivateDnsBroken()) {
                                    string = context4.getString(R.string.private_dns_broken);
                                } else {
                                    string = context4.getString(R.string.wifi_connected_no_internet);
                                }
                            }
                            sb.append(string);
                            str = SemWifiApContentProviderHelper.get(accessPoint3.mContext, "auto_hotspot_connected_user");
                            accessPoint3.smartApUserName = str;
                            if (!TextUtils.isEmpty(str)) {
                            }
                        } else {
                            if (detailedState == null) {
                                Log.w("SettingsLib.AccessPoint", "state is null, returning empty summary");
                                string = str2;
                            } else {
                                String[] stringArray = context4.getResources().getStringArray(R.array.wifi_status);
                                int iOrdinal = detailedState.ordinal();
                                string = str2;
                                if (iOrdinal < stringArray.length) {
                                    string = str2;
                                    if (stringArray[iOrdinal].length() != 0) {
                                        string = String.format(stringArray[iOrdinal], null);
                                    }
                                }
                            }
                            sb.append(string);
                            str = SemWifiApContentProviderHelper.get(accessPoint3.mContext, "auto_hotspot_connected_user");
                            accessPoint3.smartApUserName = str;
                            if (!TextUtils.isEmpty(str)) {
                                NetworkInfo networkInfo2 = accessPoint3.mNetworkInfo;
                                if (networkInfo2 != null) {
                                    detailedState2 = networkInfo2.getDetailedState();
                                } else {
                                    Log.w("SettingsLib.AccessPoint", "NetworkInfo is null, cannot return detailed state");
                                    detailedState2 = null;
                                }
                                if (detailedState2 == NetworkInfo.DetailedState.CONNECTED) {
                                    try {
                                        WifiInfo wifiInfo3 = accessPoint3.mInfo;
                                        if (wifiInfo3 != null && !TextUtils.isEmpty(wifiInfo3.getBSSID())) {
                                            if (accessPoint3.mSemWifiManager == null) {
                                                accessPoint3.mSemWifiManager = (SemWifiManager) accessPoint3.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
                                            }
                                            if (accessPoint3.mSemWifiManager.getSmartApConnectedStatus(accessPoint3.mInfo.getBSSID()) == 3) {
                                                sb.insert(0, accessPoint3.smartApUserName + accessPoint3.mContext.getString(R.string.comma) + " ");
                                            }
                                        }
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                } else {
                    j = 0;
                    WifiConfiguration wifiConfiguration3 = accessPoint3.mConfig;
                    if (wifiConfiguration3 != null && wifiConfiguration3.hasNoInternetAccess()) {
                        sb.append(accessPoint3.mContext.getString(accessPoint3.mConfig.getNetworkSelectionStatus().getNetworkSelectionStatus() == 2 ? R.string.wifi_no_internet_no_reconnect : R.string.wifi_no_internet));
                    } else {
                        WifiConfiguration wifiConfiguration4 = accessPoint3.mConfig;
                        if (wifiConfiguration4 != null && wifiConfiguration4.getNetworkSelectionStatus().getNetworkSelectionStatus() != 0) {
                            int networkSelectionDisableReason = accessPoint3.mConfig.getNetworkSelectionStatus().getNetworkSelectionDisableReason();
                            if (networkSelectionDisableReason == 1) {
                                sb.append(accessPoint3.mContext.getString(R.string.wifi_disabled_generic));
                            } else if (networkSelectionDisableReason == 2) {
                                sb.append(accessPoint3.mContext.getString(R.string.wifi_disabled_password_failure));
                            } else if (networkSelectionDisableReason == 3) {
                                sb.append(accessPoint3.mContext.getString(R.string.wifi_disabled_network_failure));
                            } else if (networkSelectionDisableReason == 8) {
                                sb.append(accessPoint3.mContext.getString(R.string.wifi_check_password_try_again));
                            }
                        } else if (accessPoint3.mRssi != Integer.MIN_VALUE) {
                            WifiConfiguration wifiConfiguration5 = accessPoint3.mConfig;
                            if (wifiConfiguration5 != null) {
                                if (wifiConfiguration5.getRecentFailureReason() != 17) {
                                    sb.append(accessPoint3.mContext.getString(R.string.wifi_remembered));
                                } else {
                                    sb.append(accessPoint3.mContext.getString(R.string.wifi_ap_unable_to_handle_new_sta));
                                }
                            }
                        } else {
                            sb.append(accessPoint3.mContext.getString(R.string.wifi_not_in_range));
                        }
                    }
                }
                if (WifiTracker.sVerboseLogging) {
                    WifiConfiguration wifiConfiguration6 = accessPoint3.mConfig;
                    WifiUtils.Companion companion = WifiUtils.Companion;
                    companion.getClass();
                    StringBuilder sb2 = new StringBuilder();
                    WifiInfo wifiInfo4 = accessPoint3.mInfo;
                    if (accessPoint3.isActive() && wifiInfo4 != null) {
                        sb2.append(" f=" + wifiInfo4.getFrequency());
                    }
                    sb2.append(" " + companion.getVisibilityStatus(accessPoint3));
                    if (wifiConfiguration6 != null && wifiConfiguration6.getNetworkSelectionStatus().getNetworkSelectionStatus() != 0) {
                        sb2.append(" (" + wifiConfiguration6.getNetworkSelectionStatus().getNetworkStatusString());
                        if (wifiConfiguration6.getNetworkSelectionStatus().getDisableTime() > j) {
                            long jCurrentTimeMillis = (System.currentTimeMillis() - wifiConfiguration6.getNetworkSelectionStatus().getDisableTime()) / 1000;
                            long j2 = 60;
                            long j3 = jCurrentTimeMillis % j2;
                            long j4 = (jCurrentTimeMillis / j2) % j2;
                            long j5 = (j4 / j2) % j2;
                            sb2.append(", ");
                            if (j5 > j) {
                                sb2.append(j5 + "h ");
                            }
                            sb2.append(j4 + "m ");
                            sb2.append(j3 + "s ");
                        }
                        sb2.append(")");
                    }
                    if (wifiConfiguration6 != null) {
                        WifiConfiguration.NetworkSelectionStatus networkSelectionStatus = wifiConfiguration6.getNetworkSelectionStatus();
                        int maxNetworkSelectionDisableReason = WifiConfiguration.NetworkSelectionStatus.getMaxNetworkSelectionDisableReason();
                        if (maxNetworkSelectionDisableReason >= 0) {
                            int i5 = 0;
                            while (true) {
                                if (networkSelectionStatus.getDisableReasonCounter(i5) != 0) {
                                    sb2.append(" ");
                                    sb2.append(WifiConfiguration.NetworkSelectionStatus.getNetworkSelectionDisableReasonString(i5));
                                    sb2.append("=");
                                    sb2.append(networkSelectionStatus.getDisableReasonCounter(i5));
                                }
                                if (i5 == maxNetworkSelectionDisableReason) {
                                    break;
                                } else {
                                    i5++;
                                }
                            }
                        }
                    }
                    sb.append(sb2.toString());
                }
                WifiConfiguration wifiConfiguration7 = accessPoint3.mConfig;
                if (wifiConfiguration7 != null) {
                    WifiUtils.Companion.getClass();
                    if (wifiConfiguration7.meteredOverride != 0 || accessPoint3.mConfig.meteredHint) {
                        Resources resources = accessPoint3.mContext.getResources();
                        Context context5 = accessPoint3.mContext;
                        WifiConfiguration wifiConfiguration8 = accessPoint3.mConfig;
                        int i6 = wifiConfiguration8.meteredOverride;
                        if (i6 != 1 && (!wifiConfiguration8.meteredHint || i6 != 0)) {
                            string3 = context5.getString(R.string.wifi_unmetered_label);
                        } else {
                            string3 = context5.getString(R.string.wifi_metered_label);
                            string3.getClass();
                        }
                        string2 = resources.getString(R.string.preference_summary_default_combination, string3, sb.toString());
                    } else if (AccessPoint.getSpeedLabel(accessPoint3.mSpeed, accessPoint3.mContext) != null && sb.length() != 0) {
                        string2 = accessPoint3.mContext.getResources().getString(R.string.preference_summary_default_combination, AccessPoint.getSpeedLabel(accessPoint3.mSpeed, accessPoint3.mContext), sb.toString());
                    } else if (AccessPoint.getSpeedLabel(accessPoint3.mSpeed, accessPoint3.mContext) != null) {
                        string2 = AccessPoint.getSpeedLabel(accessPoint3.mSpeed, accessPoint3.mContext);
                    } else {
                        string2 = sb.toString();
                    }
                }
            }
            string4 = string2;
        }
        setSummary(string4);
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
