package com.android.systemui.communal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import java.util.ArrayList;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalBackupRestoreStartable extends BroadcastReceiver implements CoreStartable {
    public final BroadcastDispatcher broadcastDispatcher;
    public final CommunalInteractor communalInteractor;
    public final Logger logger;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public CommunalBackupRestoreStartable(BroadcastDispatcher broadcastDispatcher, CommunalInteractor communalInteractor, LogBuffer logBuffer) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.communalInteractor = communalInteractor;
        this.logger = new Logger(logBuffer, "CommunalBackupRestoreStartable");
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Logger.w$default(this.logger, "On app widget host restored, but intent is null", null, 2, null);
            return;
        }
        if (Intrinsics.areEqual(intent.getAction(), "android.appwidget.action.APPWIDGET_HOST_RESTORED")) {
            if (intent.getIntExtra("hostId", 0) != 116) {
                return;
            }
            int[] intArrayExtra = intent.getIntArrayExtra("appWidgetOldIds");
            int[] intArrayExtra2 = intent.getIntArrayExtra("appWidgetIds");
            if (intArrayExtra == null || intArrayExtra2 == null || intArrayExtra.length != intArrayExtra2.length) {
                Logger.w$default(this.logger, "On app widget host restored, but old to new ids mapping is invalid", null, 2, null);
                ((CommunalWidgetRepositoryImpl) this.communalInteractor.widgetRepository).abortRestoreWidgets();
                return;
            }
            int min = Math.min(intArrayExtra.length, intArrayExtra2.length);
            ArrayList arrayList = new ArrayList(min);
            for (int i = 0; i < min; i++) {
                arrayList.add(new Pair(Integer.valueOf(intArrayExtra[i]), Integer.valueOf(intArrayExtra2[i])));
            }
            ((CommunalWidgetRepositoryImpl) this.communalInteractor.widgetRepository).restoreWidgets(MapsKt__MapsKt.toMap(arrayList));
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this, new IntentFilter("android.appwidget.action.APPWIDGET_HOST_RESTORED"), null, null, 0, null, 60);
    }
}
