package com.android.server.emergency;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.attention.AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EmergencyAffordanceService extends SystemService {
    public boolean mAirplaneModeEnabled;
    public boolean mAnyNetworkNeedsEmergencyAffordance;
    public boolean mAnySimNeedsEmergencyAffordance;
    public final AnonymousClass1 mBroadcastReceiver;
    public final Context mContext;
    public boolean mEmergencyAffordanceNeeded;
    public final ArrayList mEmergencyCallCountryIsos;
    public MyHandler mHandler;
    public final AnonymousClass2 mSubscriptionChangedListener;
    public SubscriptionManager mSubscriptionManager;
    public TelephonyManager mTelephonyManager;
    public boolean mVoiceCapable;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends Binder {
        public BinderService() {}

        @Override // android.os.Binder
        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    EmergencyAffordanceService.this.mContext,
                    "EmergencyAffordanceService",
                    printWriter)) {
                EmergencyAffordanceService emergencyAffordanceService =
                        EmergencyAffordanceService.this;
                IndentingPrintWriter indentingPrintWriter =
                        new IndentingPrintWriter(printWriter, "  ");
                emergencyAffordanceService.getClass();
                indentingPrintWriter.println(
                        "EmergencyAffordanceService (dumpsys emergency_affordance) state:\n");
                StringBuilder m =
                        AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                                AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                                        AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0
                                                .m(
                                                        AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0
                                                                .m(
                                                                        new StringBuilder(
                                                                                "mEmergencyAffordanceNeeded="),
                                                                        emergencyAffordanceService
                                                                                .mEmergencyAffordanceNeeded,
                                                                        indentingPrintWriter,
                                                                        "mVoiceCapable="),
                                                        emergencyAffordanceService.mVoiceCapable,
                                                        indentingPrintWriter,
                                                        "mAnySimNeedsEmergencyAffordance="),
                                        emergencyAffordanceService.mAnySimNeedsEmergencyAffordance,
                                        indentingPrintWriter,
                                        "mAnyNetworkNeedsEmergencyAffordance="),
                                emergencyAffordanceService.mAnyNetworkNeedsEmergencyAffordance,
                                indentingPrintWriter,
                                "mEmergencyCallCountryIsos=");
                m.append(String.join(",", emergencyAffordanceService.mEmergencyCallCountryIsos));
                indentingPrintWriter.println(m.toString());
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            EmergencyAffordanceService emergencyAffordanceService = EmergencyAffordanceService.this;
            if (i == 1) {
                emergencyAffordanceService.mAirplaneModeEnabled =
                        Settings.Global.getInt(
                                        emergencyAffordanceService.mContext.getContentResolver(),
                                        "airplane_mode_on",
                                        0)
                                == 1;
                emergencyAffordanceService.handleUpdateSimSubscriptionInfo();
                emergencyAffordanceService.updateNetworkCountry();
                emergencyAffordanceService.updateEmergencyAffordanceNeeded();
                return;
            }
            if (i != 2) {
                if (i == 3) {
                    emergencyAffordanceService.handleUpdateSimSubscriptionInfo();
                    return;
                } else if (i != 4) {
                    VaultKeeperService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Unexpected message received: "),
                            message.what,
                            "EmergencyAffordanceService");
                    return;
                } else {
                    emergencyAffordanceService.mAirplaneModeEnabled =
                            Settings.Global.getInt(
                                            emergencyAffordanceService.mContext
                                                    .getContentResolver(),
                                            "airplane_mode_on",
                                            0)
                                    == 1;
                    return;
                }
            }
            String str = (String) message.obj;
            emergencyAffordanceService.getClass();
            if (TextUtils.isEmpty(str) && emergencyAffordanceService.mAirplaneModeEnabled) {
                Slog.w(
                        "EmergencyAffordanceService",
                        "Ignore empty countryIso report when APM is on.");
            } else {
                emergencyAffordanceService.updateNetworkCountry();
                emergencyAffordanceService.updateEmergencyAffordanceNeeded();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.emergency.EmergencyAffordanceService$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.emergency.EmergencyAffordanceService$2] */
    public EmergencyAffordanceService(Context context) {
        super(context);
        this.mBroadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.emergency.EmergencyAffordanceService.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.telephony.action.NETWORK_COUNTRY_CHANGED"
                                .equals(intent.getAction())) {
                            String stringExtra =
                                    intent.getStringExtra(
                                            "android.telephony.extra.NETWORK_COUNTRY");
                            EmergencyAffordanceService.this
                                    .mHandler
                                    .obtainMessage(
                                            2,
                                            intent.getIntExtra(
                                                    "android.telephony.extra.SLOT_INDEX", -1),
                                            0,
                                            stringExtra)
                                    .sendToTarget();
                        } else if ("android.intent.action.AIRPLANE_MODE"
                                .equals(intent.getAction())) {
                            EmergencyAffordanceService.this
                                    .mHandler
                                    .obtainMessage(4)
                                    .sendToTarget();
                        }
                    }
                };
        this.mSubscriptionChangedListener =
                new SubscriptionManager
                        .OnSubscriptionsChangedListener() { // from class:
                                                            // com.android.server.emergency.EmergencyAffordanceService.2
                    @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                    public final void onSubscriptionsChanged() {
                        EmergencyAffordanceService.this.mHandler.obtainMessage(3).sendToTarget();
                    }
                };
        this.mContext = context;
        String[] stringArray = context.getResources().getStringArray(R.array.maps_starting_lat_lng);
        this.mEmergencyCallCountryIsos = new ArrayList(stringArray.length);
        for (String str : stringArray) {
            this.mEmergencyCallCountryIsos.add(str);
        }
        if (Build.IS_DEBUGGABLE) {
            String string =
                    Settings.Global.getString(
                            this.mContext.getContentResolver(),
                            "emergency_affordance_override_iso");
            if (TextUtils.isEmpty(string)) {
                return;
            }
            this.mEmergencyCallCountryIsos.clear();
            this.mEmergencyCallCountryIsos.add(string);
        }
    }

    public final void handleUpdateSimSubscriptionInfo() {
        boolean z;
        List<SubscriptionInfo> activeSubscriptionInfoList =
                this.mSubscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null) {
            return;
        }
        Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (this.mEmergencyCallCountryIsos.contains(it.next().getCountryIso())) {
                z = true;
                break;
            }
        }
        this.mAnySimNeedsEmergencyAffordance = z;
        updateEmergencyAffordanceNeeded();
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 600) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
            this.mTelephonyManager = telephonyManager;
            boolean isVoiceCapable = telephonyManager.isVoiceCapable();
            this.mVoiceCapable = isVoiceCapable;
            if (!isVoiceCapable) {
                updateEmergencyAffordanceNeeded();
                return;
            }
            this.mHandler =
                    new MyHandler(
                            KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                                            "EmergencyAffordanceService")
                                    .getLooper());
            SubscriptionManager from = SubscriptionManager.from(this.mContext);
            this.mSubscriptionManager = from;
            from.addOnSubscriptionsChangedListener(this.mSubscriptionChangedListener);
            IntentFilter intentFilter = new IntentFilter("android.intent.action.AIRPLANE_MODE");
            intentFilter.addAction("android.telephony.action.NETWORK_COUNTRY_CHANGED");
            this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
            this.mHandler.obtainMessage(1).sendToTarget();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("emergency_affordance", new BinderService());
    }

    public final void updateEmergencyAffordanceNeeded() {
        boolean z = this.mEmergencyAffordanceNeeded;
        boolean z2 =
                this.mVoiceCapable
                        && (this.mAnySimNeedsEmergencyAffordance
                                || this.mAnyNetworkNeedsEmergencyAffordance);
        this.mEmergencyAffordanceNeeded = z2;
        if (z != z2) {
            Settings.Global.putInt(
                    this.mContext.getContentResolver(),
                    "emergency_affordance_needed",
                    this.mEmergencyAffordanceNeeded ? 1 : 0);
        }
    }

    public final void updateNetworkCountry() {
        int activeModemCount = this.mTelephonyManager.getActiveModemCount();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= activeModemCount) {
                break;
            }
            if (this.mEmergencyCallCountryIsos.contains(
                    this.mTelephonyManager.getNetworkCountryIso(i))) {
                z = true;
                break;
            }
            i++;
        }
        this.mAnyNetworkNeedsEmergencyAffordance = z;
        updateEmergencyAffordanceNeeded();
    }
}
