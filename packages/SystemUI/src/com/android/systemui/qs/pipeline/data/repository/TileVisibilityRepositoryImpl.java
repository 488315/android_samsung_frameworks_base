package com.android.systemui.qs.pipeline.data.repository;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileVisibilityRepositoryImpl implements TileVisibilityRepository {
    public final ArrayList removedTileListByAppIntent = new ArrayList();
    private final SettingsHelper settingsHelper;
    public final BufferedChannel visibilityFlowEmitter;
    public final ChannelAsFlow visibilityIntentByApp;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TileVisibilityRepositoryImpl(Context context, SettingsHelper settingsHelper, DumpManager dumpManager) {
        this.settingsHelper = settingsHelper;
        BufferedChannel Channel$default = ChannelKt.Channel$default(0, null, null, 7);
        this.visibilityFlowEmitter = Channel$default;
        this.visibilityIntentByApp = FlowKt.receiveAsFlow(Channel$default);
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.qs.pipeline.data.repository.TileVisibilityRepositoryImpl$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                SettingsHelper settingsHelper2;
                if (StringsKt__StringsJVMKt.equals(intent.getAction(), "com.samsung.systemui.qs.action.ACTION_UPDATE_CUSTOMTILE_VISIBILITY", false)) {
                    settingsHelper2 = TileVisibilityRepositoryImpl.this.settingsHelper;
                    if (settingsHelper2.isEmergencyMode()) {
                        return;
                    }
                    TileVisibilityRepositoryImpl tileVisibilityRepositoryImpl = TileVisibilityRepositoryImpl.this;
                    tileVisibilityRepositoryImpl.getClass();
                    String stringExtra = intent.getStringExtra("operation");
                    String stringExtra2 = intent.getStringExtra("componentName");
                    String stringExtra3 = intent.getStringExtra("packageName");
                    String stringExtra4 = intent.getStringExtra("tileName");
                    if (stringExtra3 != null && stringExtra2 != null && stringExtra4 != null) {
                        ComponentName componentName = new ComponentName(stringExtra3, stringExtra2);
                        TileSpec.Companion.getClass();
                        TileSpec.CustomTileSpec create = TileSpec.Companion.create(componentName);
                        Log.d("TileVisibilityRepository", "updateRemovedTileListByAppIntent : operation = " + stringExtra + ", spec = " + create + ", removedTileListByAppIntent = " + tileVisibilityRepositoryImpl.removedTileListByAppIntent);
                        if (StringsKt__StringsJVMKt.equals(stringExtra, "add", false)) {
                            if (tileVisibilityRepositoryImpl.removedTileListByAppIntent.contains(create)) {
                                tileVisibilityRepositoryImpl.removedTileListByAppIntent.remove(create);
                            }
                        } else if (StringsKt__StringsJVMKt.equals(stringExtra, "remove", false) && !tileVisibilityRepositoryImpl.removedTileListByAppIntent.contains(create)) {
                            tileVisibilityRepositoryImpl.removedTileListByAppIntent.add(create);
                        }
                    }
                    TileVisibilityRepositoryImpl.this.visibilityFlowEmitter.mo3456trySendJP2dKIU(intent);
                }
            }
        }, new IntentFilter("com.samsung.systemui.qs.action.ACTION_UPDATE_CUSTOMTILE_VISIBILITY"), 2);
        dumpManager.registerNormalDumpable("TileVisibilityRepository", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        DumpUtilsKt.asIndenting(printWriter).println("removedTileListByAppIntent    : " + this.removedTileListByAppIntent);
    }
}
