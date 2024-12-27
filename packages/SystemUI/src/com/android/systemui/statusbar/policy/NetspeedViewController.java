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
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.SwitchableDoubleShadowTextView;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.sec.ims.IMSParameter;
import java.util.Observable;
import java.util.Observer;

public final class NetspeedViewController extends ViewController implements ConfigurationController.ConfigurationListener {
    public static String sActiveInterface = null;
    public static boolean sNetspeedSwitch = false;
    public static boolean sVpnConnected = false;
    public boolean mAttached;
    public final Context mContext;
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public NetworkSpeedManager mNetworkSpeedManager;
    public boolean mNetworkStats;
    public Handler mNetworkStatsHandler;
    public final AnonymousClass1 mNetworkStatsReceiver;
    public boolean mRegisterReceiver;
    public boolean mScreenOn;
    public final SettingObserver mSettingObserver;
    public final NetspeedViewController$$ExternalSyntheticLambda0 mUpdateRunnable;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final AnonymousClass2 mWakefulnessObserver;

    /* renamed from: com.android.systemui.statusbar.policy.NetspeedViewController$3, reason: invalid class name */
    class AnonymousClass3 implements UserTracker.Callback {
        public AnonymousClass3() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            Log.d("NetspeedViewController", "onUserSwitched");
            ((NetspeedView) ((ViewController) NetspeedViewController.this).mView).postDelayed(new NetspeedViewController$$ExternalSyntheticLambda0(this, 1), 3000L);
        }
    }

    public final class NetworkSpeedManager extends Observable {
        public static volatile NetworkSpeedManager sInstance;
        public final AnonymousClass1 mHandler = new Handler() { // from class: com.android.systemui.statusbar.policy.NetspeedViewController.NetworkSpeedManager.1
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
                NetworkSpeedManager networkSpeedManager = NetworkSpeedManager.this;
                if (networkSpeedManager.countObservers() > 0) {
                    networkSpeedManager.setChanged();
                    networkSpeedManager.notifyObservers(format);
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
                    try {
                        if (sInstance == null) {
                            Log.i("NetworkSpeedManager", "getInstance == null");
                            sInstance = new NetworkSpeedManager(context);
                        }
                    } finally {
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

    public final class NetworkStatsThread extends Thread {
        public /* synthetic */ NetworkStatsThread(NetspeedViewController netspeedViewController, int i) {
            this();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            NetspeedViewController netspeedViewController = NetspeedViewController.this;
            ConnectivityManager connectivityManager = (ConnectivityManager) netspeedViewController.mContext.getSystemService("connectivity");
            boolean z = false;
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
                    }
                } else {
                    Log.i("NetspeedViewController", "Network is not connected,NetworkInfo.mDetailedState = " + activeNetworkInfo.getDetailedState());
                }
            }
            netspeedViewController.mNetworkStats = z;
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("NetworkStatsThread-mNetworkStats = "), NetspeedViewController.this.mNetworkStats, "NetspeedViewController");
            NetspeedViewController netspeedViewController2 = NetspeedViewController.this;
            netspeedViewController2.mNetworkStatsHandler.post(netspeedViewController2.mUpdateRunnable);
        }

        private NetworkStatsThread() {
        }
    }

    public final class SettingObserver implements SettingsHelper.OnChangedCallback {
        public final Uri mSettingsValue;

        public /* synthetic */ SettingObserver(NetspeedViewController netspeedViewController, int i) {
            this();
        }

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            NetspeedViewController.m2242$$Nest$monNetspeedSwitchChange(NetspeedViewController.this);
        }

        private SettingObserver() {
            this.mSettingsValue = Settings.System.getUriFor(SettingsHelper.INDEX_STATUSBAR_NETWORK_SPEED);
        }
    }

    /* renamed from: -$$Nest$monNetspeedSwitchChange, reason: not valid java name */
    public static void m2242$$Nest$monNetspeedSwitchChange(NetspeedViewController netspeedViewController) {
        netspeedViewController.getClass();
        sNetspeedSwitch = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNetworkSpeedInStatusBar();
        StringBuilder sb = new StringBuilder("onNetspeedSwitchChange - sNetspeedSwitch = ");
        sb.append(sNetspeedSwitch);
        sb.append("  mRegisterReceiver = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, netspeedViewController.mRegisterReceiver, "NetspeedViewController");
        boolean z = sNetspeedSwitch;
        AnonymousClass1 anonymousClass1 = netspeedViewController.mNetworkStatsReceiver;
        if (z && !netspeedViewController.mRegisterReceiver) {
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.net.conn.CONNECTIVITY_CHANGE"), anonymousClass1);
            netspeedViewController.mRegisterReceiver = true;
        }
        int i = 0;
        if (!sNetspeedSwitch && netspeedViewController.mRegisterReceiver) {
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(anonymousClass1);
            netspeedViewController.mRegisterReceiver = false;
        }
        new NetworkStatsThread(netspeedViewController, i).start();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.policy.NetspeedViewController$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.policy.NetspeedViewController$2] */
    public NetspeedViewController(NetspeedView netspeedView, IndicatorScaleGardener indicatorScaleGardener, IndicatorCutoutUtil indicatorCutoutUtil, UserTracker userTracker, WakefulnessLifecycle wakefulnessLifecycle) {
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
                    ExifInterface$$ExternalSyntheticOutline0.m(sb, NetspeedViewController.sActiveInterface, "NetspeedViewController");
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
        this.mUpdateRunnable = new NetspeedViewController$$ExternalSyntheticLambda0(this, 0);
        this.mNetworkStatsHandler = null;
        this.mRegisterReceiver = false;
        this.mUserTracker = userTracker;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mUserChangedCallback = new AnonymousClass3();
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
        IndicatorScaleGardener.ScaleModel latestScaleModel = this.mIndicatorScaleGardener.getLatestScaleModel(getContext());
        NetspeedView netspeedView = (NetspeedView) this.mView;
        float f = latestScaleModel.ratio;
        netspeedView.getClass();
        int i = BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC ? R.dimen.status_bar_netspeed_size_udc : R.dimen.status_bar_netspeed_size;
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView = netspeedView.mContentView;
        if (switchableDoubleShadowTextView != null) {
            switchableDoubleShadowTextView.setTextSize(0, netspeedView.mContext.getResources().getDimensionPixelSize(i) * f);
            float dimensionPixelSize = netspeedView.mContext.getResources().getDimensionPixelSize(R.dimen.network_speed_padding_end) * f;
            SwitchableDoubleShadowTextView switchableDoubleShadowTextView2 = netspeedView.mContentView;
            switchableDoubleShadowTextView2.setPaddingRelative(switchableDoubleShadowTextView2.getPaddingStart(), netspeedView.mContentView.getPaddingTop(), (int) dimensionPixelSize, netspeedView.mContentView.getPaddingBottom());
        }
        netspeedView.mStableWidthHelper.reset();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        if (this.mAttached || !BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            return;
        }
        this.mAttached = true;
        this.mScreenOn = ((PowerManager) this.mContext.getSystemService(PowerManager.class)).isInteractive();
        sNetspeedSwitch = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNetworkSpeedInStatusBar();
        this.mNetworkSpeedManager = NetworkSpeedManager.getInstance(this.mContext);
        this.mNetworkStatsHandler = new Handler();
        if (!this.mRegisterReceiver && sNetspeedSwitch) {
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.net.conn.CONNECTIVITY_CHANGE"), this.mNetworkStatsReceiver);
            this.mRegisterReceiver = true;
        }
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, new HandlerExecutor(this.mNetworkStatsHandler));
        SettingObserver settingObserver = this.mSettingObserver;
        settingObserver.getClass();
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(settingObserver, settingObserver.mSettingsValue);
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        ((ConfigurationControllerImpl) ((ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class))).addCallback(this);
        new NetworkStatsThread(this, 0).start();
        onDensityOrFontScaleChanged();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        if (this.mAttached) {
            if (this.mRegisterReceiver) {
                ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mNetworkStatsReceiver);
                this.mRegisterReceiver = false;
            }
            ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mUserChangedCallback);
            SettingObserver settingObserver = this.mSettingObserver;
            settingObserver.getClass();
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).unregisterCallback(settingObserver);
            this.mWakefulnessLifecycle.removeObserver(this.mWakefulnessObserver);
            this.mNetworkSpeedManager.deleteObserver((Observer) this.mView);
            this.mAttached = false;
            ((ConfigurationControllerImpl) ((ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class))).removeCallback(this);
        }
    }

    public final void setNetworkSpeed() {
        StringBuilder sb = new StringBuilder("sNetspeedSwitch = ");
        sb.append(sNetspeedSwitch);
        sb.append(" mNetworkStats = ");
        sb.append(this.mNetworkStats);
        sb.append(" mScreenOn = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mScreenOn, "NetspeedViewController");
        if (sNetspeedSwitch && this.mNetworkStats && this.mScreenOn) {
            this.mNetworkSpeedManager.addObserver((Observer) this.mView);
            ((NetspeedView) this.mView).setVisibility(0);
        } else {
            this.mNetworkSpeedManager.deleteObserver((Observer) this.mView);
            ((NetspeedView) this.mView).setVisibility(8);
        }
    }
}
