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

/* loaded from: classes2.dex */
public final class TileVisibilityRepositoryImpl implements TileVisibilityRepository {
    public final ArrayList removedTileListByAppIntent = new ArrayList();
    private final SettingsHelper settingsHelper;
    public final BufferedChannel visibilityFlowEmitter;
    public final ChannelAsFlow visibilityIntentByApp;

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
        BufferedChannel bufferedChannelChannel$default = ChannelKt.Channel$default(0, null, null, 7);
        this.visibilityFlowEmitter = bufferedChannelChannel$default;
        this.visibilityIntentByApp = FlowKt.receiveAsFlow(bufferedChannelChannel$default);
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.qs.pipeline.data.repository.TileVisibilityRepositoryImpl$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (!StringsKt__StringsJVMKt.equals(intent.getAction(), "com.samsung.systemui.qs.action.ACTION_UPDATE_CUSTOMTILE_VISIBILITY", false) || this.this$0.settingsHelper.isEmergencyMode()) {
                    return;
                }
                TileVisibilityRepositoryImpl tileVisibilityRepositoryImpl = this.this$0;
                tileVisibilityRepositoryImpl.getClass();
                String stringExtra = intent.getStringExtra("operation");
                String stringExtra2 = intent.getStringExtra("componentName");
                String stringExtra3 = intent.getStringExtra("packageName");
                String stringExtra4 = intent.getStringExtra("tileName");
                if (stringExtra3 != null && stringExtra2 != null && stringExtra4 != null) {
                    ComponentName componentName = new ComponentName(stringExtra3, stringExtra2);
                    TileSpec.Companion.getClass();
                    TileSpec.CustomTileSpec customTileSpecCreate = TileSpec.Companion.create(componentName);
                    Log.d("TileVisibilityRepository", "updateRemovedTileListByAppIntent : operation = " + stringExtra + ", spec = " + customTileSpecCreate + ", removedTileListByAppIntent = " + tileVisibilityRepositoryImpl.removedTileListByAppIntent);
                    if (StringsKt__StringsJVMKt.equals(stringExtra, "add", false)) {
                        if (tileVisibilityRepositoryImpl.removedTileListByAppIntent.contains(customTileSpecCreate)) {
                            tileVisibilityRepositoryImpl.removedTileListByAppIntent.remove(customTileSpecCreate);
                        }
                    } else if (StringsKt__StringsJVMKt.equals(stringExtra, "remove", false) && !tileVisibilityRepositoryImpl.removedTileListByAppIntent.contains(customTileSpecCreate)) {
                        tileVisibilityRepositoryImpl.removedTileListByAppIntent.add(customTileSpecCreate);
                    }
                }
                this.this$0.visibilityFlowEmitter.mo3476trySendJP2dKIU(intent);
            }
        }, new IntentFilter("com.samsung.systemui.qs.action.ACTION_UPDATE_CUSTOMTILE_VISIBILITY"), 2);
        dumpManager.registerNormalDumpable("TileVisibilityRepository", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        DumpUtilsKt.asIndenting(printWriter).println("removedTileListByAppIntent    : " + this.removedTileListByAppIntent);
    }
}
