package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.Uri;
import android.net.VpnManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.net.VpnConfig;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.sec.ims.IMSParameter;
import java.util.Observable;
import java.util.Observer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NetspeedViewController extends ViewController implements ConfigurationController.ConfigurationListener {
    public static String sActiveInterface = null;
    public static boolean sNetspeedSwitch = false;
    public static boolean sVpnConnected = false;
    public boolean mAttached;
    public final Context mContext;
    public float mFixedScaleFactorForSpecificNetspeedView;
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public NetworkSpeedManager mNetworkSpeedManager;
    public boolean mNetworkStats;
    public Handler mNetworkStatsHandler;
    public final C34131 mNetworkStatsReceiver;
    public boolean mRegisterReceiver;
    public boolean mScreenOn;
    public final SettingObserver mSettingObserver;
    public final NetspeedViewController$$ExternalSyntheticLambda0 mUpdateRunnable;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final C34142 mWakefulnessObserver;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.policy.NetspeedViewController$3 */
    public final class C34153 implements UserTracker.Callback {
        public C34153() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            Log.d("NetspeedViewController", "onUserSwitched");
            ((NetspeedView) NetspeedViewController.this.mView).postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.policy.NetspeedViewController$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NetspeedViewController.m1726$$Nest$monNetspeedSwitchChange(NetspeedViewController.this);
                }
            }, 3000L);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NetworkSpeedManager extends Observable {
        public static volatile NetworkSpeedManager sInstance;
        public final HandlerC34161 mHandler = new Handler() { // from class: com.android.systemui.statusbar.policy.NetspeedViewController.NetworkSpeedManager.1
            public long mBeforeSpd;
            public boolean mBeforeVPNConnected = false;
            public long mSpd;

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    this.mBeforeSpd = TrafficStats.getTotalTxBytes() + TrafficStats.getTotalRxBytes();
                    if (NetspeedViewController.sVpnConnected) {
                        this.mBeforeSpd -= TrafficStats.getTxBytes(NetspeedViewController.sActiveInterface) + TrafficStats.getRxBytes(NetspeedViewController.sActiveInterface);
                    }
                    this.mBeforeVPNConnected = NetspeedViewController.sVpnConnected;
                    sendEmptyMessageDelayed(2, 3000L);
                    return;
                }
                if (i != 2) {
                    return;
                }
                this.mSpd = TrafficStats.getTotalTxBytes() + TrafficStats.getTotalRxBytes();
                if (NetspeedViewController.sVpnConnected) {
                    this.mSpd -= TrafficStats.getTxBytes(NetspeedViewController.sActiveInterface) + TrafficStats.getRxBytes(NetspeedViewController.sActiveInterface);
                }
                float f = ((r4 - this.mBeforeSpd) / 1024.0f) / 3.0f;
                this.mBeforeSpd = this.mSpd;
                boolean z = this.mBeforeVPNConnected;
                boolean z2 = NetspeedViewController.sVpnConnected;
                if (z != z2) {
                    this.mBeforeVPNConnected = z2;
                    sendEmptyMessageDelayed(2, 3000L);
                    return;
                }
                double d = f;
                String format = d <= 0.0d ? "0\nK/s" : d < 100.0d ? String.format("%.2f\nK/s", Float.valueOf(f)) : d < 1000.0d ? String.format("%.1f\nK/s", Float.valueOf(f)) : d < 102400.0d ? String.format("%.2f\nM/s", Double.valueOf(d / 1024.0d)) : String.format("%.1f\nM/s", Double.valueOf(d / 1024.0d));
                if (NetworkSpeedManager.this.countObservers() > 0) {
                    NetworkSpeedManager.this.setChanged();
                    NetworkSpeedManager.this.notifyObservers(format);
                    sendEmptyMessageDelayed(2, 3000L);
                }
            }
        };

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.policy.NetspeedViewController$NetworkSpeedManager$1] */
        private NetworkSpeedManager(Context context) {
        }

        public static NetworkSpeedManager getInstance(Context context) {
            if (sInstance == null) {
                synchronized (NetworkSpeedManager.class) {
                    if (sInstance == null) {
                        Log.i("NetworkSpeedManager", "getInstance == null");
                        sInstance = new NetworkSpeedManager(context);
                    }
                }
            }
            return sInstance;
        }

        @Override // java.util.Observable
        public final void addObserver(Observer observer) {
            if (countObservers() == 0) {
                sendEmptyMessage(1);
            }
            super.addObserver(observer);
        }

        @Override // java.util.Observable
        public final void deleteObserver(Observer observer) {
            super.deleteObserver(observer);
            if (countObservers() == 0) {
                removeCallbacksAndMessages(null);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NetworkStatsThread extends Thread {
        public /* synthetic */ NetworkStatsThread(NetspeedViewController netspeedViewController, int i) {
            this();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            boolean z;
            NetspeedViewController netspeedViewController = NetspeedViewController.this;
            ConnectivityManager connectivityManager = (ConnectivityManager) netspeedViewController.mContext.getSystemService("connectivity");
            if (connectivityManager == null) {
                Log.i("NetspeedViewController", "Couldn't get connectivity manager");
            } else {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    Log.i("NetspeedViewController", "There is not active network");
                } else if (activeNetworkInfo.isConnected()) {
                    NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                    if (networkCapabilities != null && !networkCapabilities.hasCapability(4)) {
                        z = true;
                        netspeedViewController.mNetworkStats = z;
                        NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("NetworkStatsThread-mNetworkStats = "), NetspeedViewController.this.mNetworkStats, "NetspeedViewController");
                        NetspeedViewController netspeedViewController2 = NetspeedViewController.this;
                        netspeedViewController2.mNetworkStatsHandler.post(netspeedViewController2.mUpdateRunnable);
                    }
                } else {
                    Log.i("NetspeedViewController", "Network is not connected,NetworkInfo.mDetailedState = " + activeNetworkInfo.getDetailedState());
                }
            }
            z = false;
            netspeedViewController.mNetworkStats = z;
            NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("NetworkStatsThread-mNetworkStats = "), NetspeedViewController.this.mNetworkStats, "NetspeedViewController");
            NetspeedViewController netspeedViewController22 = NetspeedViewController.this;
            netspeedViewController22.mNetworkStatsHandler.post(netspeedViewController22.mUpdateRunnable);
        }

        private NetworkStatsThread() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingObserver implements SettingsHelper.OnChangedCallback {
        public final Uri mSettingsValue;

        public /* synthetic */ SettingObserver(NetspeedViewController netspeedViewController, int i) {
            this();
        }

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            NetspeedViewController.m1726$$Nest$monNetspeedSwitchChange(NetspeedViewController.this);
        }

        private SettingObserver() {
            this.mSettingsValue = Settings.System.getUriFor("network_speed");
        }
    }

    /* renamed from: -$$Nest$monNetspeedSwitchChange, reason: not valid java name */
    public static void m1726$$Nest$monNetspeedSwitchChange(NetspeedViewController netspeedViewController) {
        netspeedViewController.getClass();
        int i = 0;
        sNetspeedSwitch = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("network_speed").getIntValue() != 0;
        StringBuilder sb = new StringBuilder("onNetspeedSwitchChange - sNetspeedSwitch = ");
        sb.append(sNetspeedSwitch);
        sb.append("  mRegisterReceiver = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, netspeedViewController.mRegisterReceiver, "NetspeedViewController");
        boolean z = sNetspeedSwitch;
        C34131 c34131 = netspeedViewController.mNetworkStatsReceiver;
        if (z && !netspeedViewController.mRegisterReceiver) {
            ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("android.net.conn.CONNECTIVITY_CHANGE"), c34131);
            netspeedViewController.mRegisterReceiver = true;
        }
        if (!sNetspeedSwitch && netspeedViewController.mRegisterReceiver) {
            ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(c34131);
            netspeedViewController.mRegisterReceiver = false;
        }
        new NetworkStatsThread(netspeedViewController, i).start();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.policy.NetspeedViewController$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.policy.NetspeedViewController$2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.policy.NetspeedViewController$$ExternalSyntheticLambda0] */
    public NetspeedViewController(NetspeedView netspeedView, IndicatorScaleGardener indicatorScaleGardener, IndicatorCutoutUtil indicatorCutoutUtil, UserTracker userTracker) {
        super(netspeedView);
        this.mSettingObserver = new SettingObserver(this, 0);
        this.mNetworkStatsReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.policy.NetspeedViewController.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
                int i = 0;
                if (networkInfo != null && networkInfo.getType() == 17) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        NetspeedViewController.this.getClass();
                        VpnConfig vpnConfig = ((VpnManager) context.getSystemService(VpnManager.class)).getVpnConfig(0);
                        String str = vpnConfig != null ? vpnConfig.interfaze : null;
                        NetspeedViewController.sActiveInterface = str;
                        if (!TextUtils.isEmpty(str)) {
                            NetspeedViewController.sVpnConnected = true;
                        }
                    } else {
                        NetspeedViewController.sVpnConnected = false;
                    }
                    StringBuilder sb = new StringBuilder("onChange - sVpnConnected = ");
                    sb.append(NetspeedViewController.sVpnConnected);
                    sb.append(" sActiveInterface = ");
                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb, NetspeedViewController.sActiveInterface, "NetspeedViewController");
                }
                new NetworkStatsThread(NetspeedViewController.this, i).start();
            }
        };
        this.mNetworkStats = false;
        this.mScreenOn = false;
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.policy.NetspeedViewController.2
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                Log.d("NetspeedViewController", "mWakefulnessObserver onFinishedWakingUp ");
                NetspeedViewController netspeedViewController = NetspeedViewController.this;
                netspeedViewController.mScreenOn = true;
                netspeedViewController.setNetworkSpeed();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                Log.d("NetspeedViewController", "mWakefulnessObserver onStartedGoingToSleep ");
                NetspeedViewController netspeedViewController = NetspeedViewController.this;
                netspeedViewController.mScreenOn = false;
                netspeedViewController.setNetworkSpeed();
            }
        };
        this.mUpdateRunnable = new Runnable() { // from class: com.android.systemui.statusbar.policy.NetspeedViewController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NetspeedViewController.this.setNetworkSpeed();
            }
        };
        this.mNetworkStatsHandler = null;
        this.mRegisterReceiver = false;
        this.mFixedScaleFactorForSpecificNetspeedView = 0.0f;
        this.mUserTracker = userTracker;
        this.mUserChangedCallback = new C34153();
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            this.mContext = netspeedView.getContext();
            this.mIndicatorScaleGardener = indicatorScaleGardener;
            if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
                ((NetspeedView) this.mView).mIndicatorCutoutUtil = indicatorCutoutUtil;
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        float f = this.mFixedScaleFactorForSpecificNetspeedView;
        if (f > 0.0f) {
            ((NetspeedView) this.mView).scaleView(f);
        } else {
            ((NetspeedView) this.mView).scaleView(this.mIndicatorScaleGardener.getLatestScaleModel(getContext()).ratio);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        if (this.mAttached || !BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            return;
        }
        this.mAttached = true;
        Context context = this.mContext;
        this.mScreenOn = ((PowerManager) context.getSystemService(PowerManager.class)).isInteractive();
        int i = 0;
        sNetspeedSwitch = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("network_speed").getIntValue() != 0;
        this.mNetworkSpeedManager = NetworkSpeedManager.getInstance(context);
        this.mNetworkStatsHandler = new Handler();
        if (!this.mRegisterReceiver && sNetspeedSwitch) {
            ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("android.net.conn.CONNECTIVITY_CHANGE"), this.mNetworkStatsReceiver);
            this.mRegisterReceiver = true;
        }
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, new HandlerExecutor(this.mNetworkStatsHandler));
        SettingObserver settingObserver = this.mSettingObserver;
        settingObserver.getClass();
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(settingObserver, settingObserver.mSettingsValue);
        ((WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class)).addObserver(this.mWakefulnessObserver);
        ((ConfigurationControllerImpl) ((ConfigurationController) Dependency.get(ConfigurationController.class))).addCallback(this);
        new NetworkStatsThread(this, i).start();
        onDensityOrFontScaleChanged();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        if (this.mAttached) {
            if (this.mRegisterReceiver) {
                ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(this.mNetworkStatsReceiver);
                this.mRegisterReceiver = false;
            }
            ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mUserChangedCallback);
            SettingObserver settingObserver = this.mSettingObserver;
            settingObserver.getClass();
            ((SettingsHelper) Dependency.get(SettingsHelper.class)).unregisterCallback(settingObserver);
            ((WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class)).removeObserver(this.mWakefulnessObserver);
            this.mNetworkSpeedManager.deleteObserver((Observer) this.mView);
            this.mAttached = false;
            ((ConfigurationControllerImpl) ((ConfigurationController) Dependency.get(ConfigurationController.class))).removeCallback(this);
        }
    }

    public final void setNetworkSpeed() {
        StringBuilder sb = new StringBuilder("sNetspeedSwitch = ");
        sb.append(sNetspeedSwitch);
        sb.append(" mNetworkStats = ");
        sb.append(this.mNetworkStats);
        sb.append(" mScreenOn = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mScreenOn, "NetspeedViewController");
        if (sNetspeedSwitch && this.mNetworkStats && this.mScreenOn) {
            this.mNetworkSpeedManager.addObserver((Observer) this.mView);
            ((NetspeedView) this.mView).setVisibility(0);
        } else {
            this.mNetworkSpeedManager.deleteObserver((Observer) this.mView);
            ((NetspeedView) this.mView).setVisibility(8);
        }
    }
}
