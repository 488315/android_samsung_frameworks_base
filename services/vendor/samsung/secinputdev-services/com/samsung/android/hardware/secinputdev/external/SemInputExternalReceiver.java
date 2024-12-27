package com.samsung.android.hardware.secinputdev.external;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputFeatures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SemInputExternalReceiver implements SemInputExternal.IExternalEventRegister {
    private static final String COVER_ATTACH_CHANGED_INTENT =
            "com.samsung.android.intent.action.COVER_ATTACH_CHANGED";
    private static final String EXTRA_COVER_ATTACH = "attach";
    private static final String EXTRA_REAL_COVER_TYPE = "real_cover_type";
    private static final String GOS_INTENT = "com.samsung.android.game.gos.action.TSP";
    private static final String RF_FIELD_OFF =
            "com.android.nfc_extras.action.RF_FIELD_OFF_DETECTED";
    private static final String RF_FIELD_ON = "com.android.nfc_extras.action.RF_FIELD_ON_DETECTED";
    private static final String SET_FAST_RESPONSE = "set_fast_response";
    private static final String SET_GAME_MODE = "set_game_mode";
    private static final String SET_SCAN_RATE = "set_scan_rate";
    private static final String TAG = "SemInputExternalReceiver";
    private Context context;
    private Handler mainHandler;
    private final Map<SemInputExternal.Event, ArrayList> registeredList = new HashMap();
    private final Map<SemInputExternal.Event, BroadcastSet> supportList = new HashMap();
    private final BroadcastReceiver chargerBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                // com.samsung.android.hardware.secinputdev.external.SemInputExternalReceiver.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    ArrayList<SemInputExternal.IBroadcastReceiver> list;
                    if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())
                            && (list =
                                            (ArrayList)
                                                    SemInputExternalReceiver.this.registeredList
                                                            .get(
                                                                    SemInputExternal.Event
                                                                            .BROADCAST_BATTERY))
                                    != null) {
                        int status = 0;
                        int extra_status = intent.getIntExtra("status", -1);
                        int type = intent.getIntExtra("plugged", -1);
                        if (extra_status == 2 || extra_status == 5 || type == 65536) {
                            status = 1;
                        }
                        Iterator<SemInputExternal.IBroadcastReceiver> it = list.iterator();
                        while (it.hasNext()) {
                            SemInputExternal.IBroadcastReceiver element = it.next();
                            element.onBatteryChanged(status, type);
                        }
                    }
                }
            };
    private final BroadcastReceiver extraChargerBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                // com.samsung.android.hardware.secinputdev.external.SemInputExternalReceiver.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    ArrayList<SemInputExternal.IBroadcastReceiver> list;
                    Log.d(
                            SemInputExternalReceiver.TAG,
                            "extraChargerBroadcastReceiver: " + intent.getAction());
                    if (!"com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT"
                                    .equals(intent.getAction())
                            || (list =
                                            (ArrayList)
                                                    SemInputExternalReceiver.this.registeredList
                                                            .get(
                                                                    SemInputExternal.Event
                                                                            .BROADCAST_BATTERY_EXTRA))
                                    == null) {
                        return;
                    }
                    int tx_id = intent.getIntExtra("wc_tx_id", -1);
                    if (tx_id >= 0) {
                        Iterator<SemInputExternal.IBroadcastReceiver> it = list.iterator();
                        while (it.hasNext()) {
                            SemInputExternal.IBroadcastReceiver element = it.next();
                            element.onBatteryTxIdChanged(tx_id);
                        }
                        return;
                    }
                    Log.e(
                            SemInputExternalReceiver.TAG,
                            "extraChargerBroadcastReceiver: error tx_id: " + tx_id);
                }
            };
    private final BroadcastReceiver shutdownBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                // com.samsung.android.hardware.secinputdev.external.SemInputExternalReceiver.3
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (!"android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                        return;
                    }
                    Log.d(SemInputExternalReceiver.TAG, "shutdownBroadcastReceiver");
                    ArrayList<SemInputExternal.IBroadcastReceiver> list =
                            (ArrayList)
                                    SemInputExternalReceiver.this.registeredList.get(
                                            SemInputExternal.Event.BROADCAST_SHUTDOWN);
                    if (list == null) {
                        return;
                    }
                    Iterator<SemInputExternal.IBroadcastReceiver> it = list.iterator();
                    while (it.hasNext()) {
                        SemInputExternal.IBroadcastReceiver element = it.next();
                        element.onShutdown();
                    }
                }
            };
    private final BroadcastReceiver lazyBootCompleteBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                // com.samsung.android.hardware.secinputdev.external.SemInputExternalReceiver.4
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (!"com.samsung.intent.action.LAZY_BOOT_COMPLETE"
                            .equals(intent.getAction())) {
                        return;
                    }
                    Log.d(SemInputExternalReceiver.TAG, "lazyBootCompleteBroadcastReceiver");
                    ArrayList<SemInputExternal.IBroadcastReceiver> list =
                            (ArrayList)
                                    SemInputExternalReceiver.this.registeredList.get(
                                            SemInputExternal.Event.BROADCAST_LAZY_BOOT);
                    if (list == null) {
                        return;
                    }
                    Iterator<SemInputExternal.IBroadcastReceiver> it = list.iterator();
                    while (it.hasNext()) {
                        SemInputExternal.IBroadcastReceiver element = it.next();
                        element.onLazybootCompleted();
                    }
                }
            };
    private final BroadcastReceiver gameServiceBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                // com.samsung.android.hardware.secinputdev.external.SemInputExternalReceiver.5
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    ArrayList<SemInputExternal.IBroadcastReceiver> list;
                    if (SemInputExternalReceiver.GOS_INTENT.equals(intent.getAction())
                            && (list =
                                            (ArrayList)
                                                    SemInputExternalReceiver.this.registeredList
                                                            .get(
                                                                    SemInputExternal.Event
                                                                            .BROADCAST_GAME))
                                    != null) {
                        String gameMode =
                                intent.getStringExtra(SemInputExternalReceiver.SET_GAME_MODE);
                        String scanRate =
                                intent.getStringExtra(SemInputExternalReceiver.SET_SCAN_RATE);
                        String fastResponse =
                                intent.getStringExtra(SemInputExternalReceiver.SET_FAST_RESPONSE);
                        Log.d(
                                SemInputExternalReceiver.TAG,
                                "gameServiceBroadcastReceiver: game:"
                                        + gameMode
                                        + " scan:"
                                        + scanRate
                                        + " fastResponse:"
                                        + fastResponse);
                        Iterator<SemInputExternal.IBroadcastReceiver> it = list.iterator();
                        while (it.hasNext()) {
                            SemInputExternal.IBroadcastReceiver element = it.next();
                            element.onGameMode(gameMode, scanRate, fastResponse);
                        }
                    }
                }
            };
    private final BroadcastReceiver coverBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                // com.samsung.android.hardware.secinputdev.external.SemInputExternalReceiver.6
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    ArrayList<SemInputExternal.IBroadcastReceiver> list;
                    if (SemInputExternalReceiver.COVER_ATTACH_CHANGED_INTENT.equals(
                                    intent.getAction())
                            && (list =
                                            (ArrayList)
                                                    SemInputExternalReceiver.this.registeredList
                                                            .get(
                                                                    SemInputExternal.Event
                                                                            .BROADCAST_COVER))
                                    != null) {
                        boolean attached =
                                intent.getBooleanExtra(
                                        SemInputExternalReceiver.EXTRA_COVER_ATTACH, false);
                        int cover_type =
                                intent.getIntExtra(
                                        SemInputExternalReceiver.EXTRA_REAL_COVER_TYPE, 0);
                        Log.d(
                                SemInputExternalReceiver.TAG,
                                "coverBroadcastReceiver: attached:"
                                        + attached
                                        + " cover_type:"
                                        + cover_type);
                        Iterator<SemInputExternal.IBroadcastReceiver> it = list.iterator();
                        while (it.hasNext()) {
                            SemInputExternal.IBroadcastReceiver element = it.next();
                            element.onCoverAttached(attached, cover_type);
                        }
                    }
                }
            };
    private final BroadcastReceiver userSwitchedBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                // com.samsung.android.hardware.secinputdev.external.SemInputExternalReceiver.7
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (!"android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                        return;
                    }
                    Log.d(SemInputExternalReceiver.TAG, "userSwitchedBroadcastReceiver");
                    ArrayList<SemInputExternal.IBroadcastReceiver> list =
                            (ArrayList)
                                    SemInputExternalReceiver.this.registeredList.get(
                                            SemInputExternal.Event.BROADCAST_USER_SWITCHED);
                    if (list == null) {
                        return;
                    }
                    Iterator<SemInputExternal.IBroadcastReceiver> it = list.iterator();
                    while (it.hasNext()) {
                        SemInputExternal.IBroadcastReceiver element = it.next();
                        element.onUserSwitched();
                    }
                }
            };
    private final BroadcastReceiver nfcRFBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                // com.samsung.android.hardware.secinputdev.external.SemInputExternalReceiver.8
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (SemInputExternalReceiver.RF_FIELD_ON.equals(intent.getAction())) {
                        Log.d(SemInputExternalReceiver.TAG, "nfcRFBroadcastReceiver: on");
                        ArrayList<SemInputExternal.IBroadcastReceiver> list =
                                (ArrayList)
                                        SemInputExternalReceiver.this.registeredList.get(
                                                SemInputExternal.Event.BROADCAST_RF_DETECTED);
                        if (list == null) {
                            return;
                        }
                        Iterator<SemInputExternal.IBroadcastReceiver> it = list.iterator();
                        while (it.hasNext()) {
                            SemInputExternal.IBroadcastReceiver element = it.next();
                            element.onRFDetected(true);
                        }
                        return;
                    }
                    if (SemInputExternalReceiver.RF_FIELD_OFF.equals(intent.getAction())) {
                        Log.d(SemInputExternalReceiver.TAG, "nfcRFBroadcastReceiver: off");
                        ArrayList<SemInputExternal.IBroadcastReceiver> list2 =
                                (ArrayList)
                                        SemInputExternalReceiver.this.registeredList.get(
                                                SemInputExternal.Event.BROADCAST_RF_OFF_DETECTED);
                        if (list2 == null) {
                            return;
                        }
                        Iterator<SemInputExternal.IBroadcastReceiver> it2 = list2.iterator();
                        while (it2.hasNext()) {
                            SemInputExternal.IBroadcastReceiver element2 = it2.next();
                            element2.onRFDetected(false);
                        }
                    }
                }
            };

    public SemInputExternalReceiver(Context context, Handler handler) {
        this.context = null;
        this.mainHandler = null;
        this.context = context;
        this.mainHandler = handler;
        this.supportList.put(
                SemInputExternal.Event.BROADCAST_BATTERY,
                new BroadcastSet(
                        "android.intent.action.BATTERY_CHANGED",
                        null,
                        this.chargerBroadcastReceiver,
                        true));
        this.supportList.put(
                SemInputExternal.Event.BROADCAST_BATTERY_EXTRA,
                new BroadcastSet(
                        "com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT",
                        null,
                        this.extraChargerBroadcastReceiver,
                        true));
        this.supportList.put(
                SemInputExternal.Event.BROADCAST_SHUTDOWN,
                new BroadcastSet(
                        "android.intent.action.ACTION_SHUTDOWN",
                        null,
                        this.shutdownBroadcastReceiver,
                        false));
        this.supportList.put(
                SemInputExternal.Event.BROADCAST_LAZY_BOOT,
                new BroadcastSet(
                        "com.samsung.intent.action.LAZY_BOOT_COMPLETE",
                        null,
                        this.lazyBootCompleteBroadcastReceiver,
                        true));
        this.supportList.put(
                SemInputExternal.Event.BROADCAST_USER_SWITCHED,
                new BroadcastSet(
                        "android.intent.action.USER_SWITCHED",
                        null,
                        this.userSwitchedBroadcastReceiver,
                        true));
        if (SemInputFeatures.IS_WEAROS) {
            this.supportList.put(
                    SemInputExternal.Event.BROADCAST_RF_DETECTED,
                    new BroadcastSet(RF_FIELD_ON, null, this.nfcRFBroadcastReceiver, false));
            this.supportList.put(
                    SemInputExternal.Event.BROADCAST_RF_OFF_DETECTED,
                    new BroadcastSet(RF_FIELD_OFF, null, this.nfcRFBroadcastReceiver, false));
        } else {
            this.supportList.put(
                    SemInputExternal.Event.BROADCAST_GAME,
                    new BroadcastSet(
                            GOS_INTENT,
                            "android.permission.HARDWARE_TEST",
                            this.gameServiceBroadcastReceiver,
                            true));
            this.supportList.put(
                    SemInputExternal.Event.BROADCAST_COVER,
                    new BroadcastSet(
                            COVER_ATTACH_CHANGED_INTENT, null, this.coverBroadcastReceiver, true));
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IExternalEventRegister
    public boolean registerServiceListener(
            SemInputExternal.Event event, SemInputExternal.IServiceListener receiver) {
        return false;
    }

    @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IExternalEventRegister
    public boolean registerBroadcastReceiver(
            SemInputExternal.Event event, SemInputExternal.IBroadcastReceiver ireceiver) {
        BroadcastSet broadcastSet = this.supportList.get(event);
        if (broadcastSet == null) {
            Log.d(TAG, "registerReceiver: Not support: " + event);
            return false;
        }
        BroadcastReceiver receiver = broadcastSet.getReceiver();
        String action = broadcastSet.getAction();
        if (action == null || receiver == null) {
            Log.d(TAG, "registerReceiver: BroadcastSet problem ");
            return false;
        }
        ArrayList<SemInputExternal.IBroadcastReceiver> listenerList =
                this.registeredList.get(event);
        if (listenerList == null) {
            listenerList = new ArrayList<>();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(action);
            if (broadcastSet.needScheduler()) {
                this.context.registerReceiver(
                        receiver, intentFilter, broadcastSet.getPermission(), this.mainHandler, 2);
            } else {
                this.context.registerReceiver(receiver, intentFilter, 2);
            }
        }
        if (listenerList != null) {
            listenerList.add(ireceiver);
            if (!listenerList.isEmpty()) {
                this.registeredList.put(event, listenerList);
            }
        }
        Log.d(TAG, "registerBroadcastReceiver " + event);
        return true;
    }

    private static class BroadcastSet {
        private String intent;
        private String permission;
        private BroadcastReceiver receiver;
        private boolean scheduler;

        BroadcastSet(
                String intent, String permission, BroadcastReceiver receiver, boolean scheduler) {
            this.intent = intent;
            this.permission = permission;
            this.receiver = receiver;
            this.scheduler = scheduler;
        }

        public BroadcastReceiver getReceiver() {
            return this.receiver;
        }

        public String getAction() {
            return this.intent;
        }

        public String getPermission() {
            return this.permission;
        }

        public boolean needScheduler() {
            return this.scheduler;
        }
    }
}
