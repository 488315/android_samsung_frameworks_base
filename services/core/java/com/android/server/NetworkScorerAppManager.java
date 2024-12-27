package com.android.server;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.net.NetworkScorerAppData;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class NetworkScorerAppManager {
    public static final boolean DEBUG = Log.isLoggable("NetworkScorerAppManager", 3);
    public static final boolean VERBOSE = Log.isLoggable("NetworkScorerAppManager", 2);
    public final Context mContext;
    public final SettingsFacade mSettingsFacade;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsFacade {}

    public NetworkScorerAppManager(Context context, SettingsFacade settingsFacade) {
        this.mContext = context;
        this.mSettingsFacade = settingsFacade;
    }

    public NetworkScorerAppData getActiveScorer() {
        Context context = this.mContext;
        SettingsFacade settingsFacade = this.mSettingsFacade;
        settingsFacade.getClass();
        if (Settings.Global.getInt(
                        context.getContentResolver(), "network_recommendations_enabled", 0)
                == -1) {
            return null;
        }
        Context context2 = this.mContext;
        settingsFacade.getClass();
        return getScorer(
                Settings.Global.getString(
                        context2.getContentResolver(), "network_recommendations_package"));
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x00a8, code lost:

       if (android.text.TextUtils.isEmpty(r10) == false) goto L25;
    */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0151  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List getAllValidScorers() {
        /*
            Method dump skipped, instructions count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.NetworkScorerAppManager.getAllValidScorers():java.util.List");
    }

    public final NetworkScorerAppData getScorer(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        List allValidScorers = getAllValidScorers();
        for (int i = 0; i < allValidScorers.size(); i++) {
            NetworkScorerAppData networkScorerAppData =
                    (NetworkScorerAppData) allValidScorers.get(i);
            if (networkScorerAppData.getRecommendationServicePackageName().equals(str)) {
                return networkScorerAppData;
            }
        }
        return null;
    }

    public void migrateNetworkScorerAppSettingIfNeeded() {
        NetworkScorerAppData activeScorer;
        Context context = this.mContext;
        this.mSettingsFacade.getClass();
        String string =
                Settings.Global.getString(context.getContentResolver(), "network_scorer_app");
        if (TextUtils.isEmpty(string) || (activeScorer = getActiveScorer()) == null) {
            return;
        }
        boolean z = DEBUG;
        if (z) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "Migrating Settings.Global.NETWORK_SCORER_APP (",
                    string,
                    ")...",
                    "NetworkScorerAppManager");
        }
        ComponentName enableUseOpenWifiActivity = activeScorer.getEnableUseOpenWifiActivity();
        if (TextUtils.isEmpty(
                        Settings.Global.getString(
                                this.mContext.getContentResolver(), "use_open_wifi_package"))
                && enableUseOpenWifiActivity != null
                && string.equals(enableUseOpenWifiActivity.getPackageName())) {
            Settings.Global.putString(
                    this.mContext.getContentResolver(), "use_open_wifi_package", string);
            if (z) {
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "Settings.Global.USE_OPEN_WIFI_PACKAGE set to '",
                        string,
                        "'.",
                        "NetworkScorerAppManager");
            }
        }
        Settings.Global.putString(this.mContext.getContentResolver(), "network_scorer_app", null);
        if (z) {
            Log.d(
                    "NetworkScorerAppManager",
                    "Settings.Global.NETWORK_SCORER_APP migration complete.");
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "Settings.Global.USE_OPEN_WIFI_PACKAGE is: '",
                    Settings.Global.getString(
                            this.mContext.getContentResolver(), "use_open_wifi_package"),
                    "'.",
                    "NetworkScorerAppManager");
        }
    }

    public boolean setActiveScorer(String str) {
        Context context = this.mContext;
        this.mSettingsFacade.getClass();
        String string =
                Settings.Global.getString(
                        context.getContentResolver(), "network_recommendations_package");
        if (TextUtils.equals(string, str)) {
            return true;
        }
        if (TextUtils.isEmpty(str)) {
            Log.i("NetworkScorerAppManager", "Network scorer forced off, was: " + string);
            setNetworkRecommendationsPackage(null);
            setNetworkRecommendationsEnabledSetting(-1);
            return true;
        }
        if (getScorer(str) == null) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    "Requested network scorer is not valid: ", str, "NetworkScorerAppManager");
            return false;
        }
        Log.i("NetworkScorerAppManager", "Changing network scorer from " + string + " to " + str);
        setNetworkRecommendationsPackage(str);
        setNetworkRecommendationsEnabledSetting(1);
        return true;
    }

    public final void setNetworkRecommendationsEnabledSetting(int i) {
        Context context = this.mContext;
        this.mSettingsFacade.getClass();
        Settings.Global.putInt(context.getContentResolver(), "network_recommendations_enabled", i);
        if (VERBOSE) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    i, "network_recommendations_enabled set to ", "NetworkScorerAppManager");
        }
    }

    public final void setNetworkRecommendationsPackage(String str) {
        Context context = this.mContext;
        this.mSettingsFacade.getClass();
        Settings.Global.putString(
                context.getContentResolver(), "network_recommendations_package", str);
        if (VERBOSE) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "network_recommendations_package set to ", str, "NetworkScorerAppManager");
        }
    }

    public void updateState() {
        Context context = this.mContext;
        SettingsFacade settingsFacade = this.mSettingsFacade;
        settingsFacade.getClass();
        int i = 0;
        int i2 =
                Settings.Global.getInt(
                        context.getContentResolver(), "network_recommendations_enabled", 0);
        boolean z = DEBUG;
        if (i2 == -1) {
            if (z) {
                Log.d("NetworkScorerAppManager", "Recommendations forced off.");
                return;
            }
            return;
        }
        Context context2 = this.mContext;
        settingsFacade.getClass();
        String string =
                Settings.Global.getString(
                        context2.getContentResolver(), "network_recommendations_package");
        if (getScorer(string) != null) {
            if (VERBOSE) {
                Log.v("NetworkScorerAppManager", string + " is the active scorer.");
            }
            setNetworkRecommendationsEnabledSetting(1);
            return;
        }
        String string2 =
                this.mContext
                        .getResources()
                        .getString(R.string.default_audio_route_name_headphones);
        if (!TextUtils.equals(string, string2) && getScorer(string2) != null) {
            if (z) {
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "Defaulting the network recommendations app to: ",
                        string2,
                        "NetworkScorerAppManager");
            }
            setNetworkRecommendationsPackage(string2);
            i = 1;
        }
        setNetworkRecommendationsEnabledSetting(i);
    }
}
