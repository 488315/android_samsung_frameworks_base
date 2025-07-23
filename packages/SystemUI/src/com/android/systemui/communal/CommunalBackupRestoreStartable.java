package com.android.systemui.communal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Map;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalBackupRestoreStartable extends BroadcastReceiver implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BroadcastDispatcher broadcastDispatcher;
    public final CommunalInteractor communalInteractor;
    public final Logger logger;
    public Map oldToNewWidgetIdMap = MapsKt__MapsKt.emptyMap();
    public final SecureSettings secureSettings;
    public boolean userSetupComplete;
    public final CommunalBackupRestoreStartable$userSetupObserver$1 userSetupObserver;

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

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.communal.CommunalBackupRestoreStartable$userSetupObserver$1] */
    public CommunalBackupRestoreStartable(BroadcastDispatcher broadcastDispatcher, CommunalInteractor communalInteractor, LogBuffer logBuffer, SecureSettings secureSettings, final Handler handler) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.communalInteractor = communalInteractor;
        this.secureSettings = secureSettings;
        this.logger = new Logger(logBuffer, "CommunalBackupRestoreStartable");
        this.userSetupObserver = new ContentObserver(handler) { // from class: com.android.systemui.communal.CommunalBackupRestoreStartable$userSetupObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                CommunalBackupRestoreStartable communalBackupRestoreStartable = this;
                int i = CommunalBackupRestoreStartable.$r8$clinit;
                communalBackupRestoreStartable.maybeRestoreWidgets();
                CommunalBackupRestoreStartable communalBackupRestoreStartable2 = this;
                if (communalBackupRestoreStartable2.userSetupComplete) {
                    communalBackupRestoreStartable2.secureSettings.unregisterContentObserverSync(communalBackupRestoreStartable2.userSetupObserver);
                }
            }
        };
    }

    public final void maybeRestoreWidgets() {
        boolean z = this.secureSettings.getInt(SettingsHelper.INDEX_USER_SETUP_COMPLETE) > 0;
        if (this.userSetupComplete != z) {
            this.userSetupComplete = z;
            Logger logger = this.logger;
            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, new CommunalBackupRestoreStartable$$ExternalSyntheticLambda0(1), null);
            obtain.setBool1(this.userSetupComplete);
            logger.getBuffer().commit(obtain);
        }
        if (!this.userSetupComplete || this.oldToNewWidgetIdMap.isEmpty()) {
            return;
        }
        Logger.i$default(this.logger, "Starting to restore widgets", null, 2, null);
        this.communalInteractor.widgetRepository.restoreWidgets(MapsKt__MapsKt.toMap(this.oldToNewWidgetIdMap));
        this.oldToNewWidgetIdMap = MapsKt__MapsKt.emptyMap();
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Logger.w$default(this.logger, "On app widget host restored, but intent is null", null, 2, null);
            return;
        }
        if (Intrinsics.areEqual(intent.getAction(), "android.appwidget.action.APPWIDGET_HOST_RESTORED") && intent.getIntExtra("hostId", 0) == 116) {
            int[] intArrayExtra = intent.getIntArrayExtra("appWidgetOldIds");
            int[] intArrayExtra2 = intent.getIntArrayExtra("appWidgetIds");
            if (intArrayExtra == null || intArrayExtra2 == null || intArrayExtra.length != intArrayExtra2.length) {
                Logger.w$default(this.logger, "On app widget host restored, but old to new ids mapping is invalid", null, 2, null);
                this.communalInteractor.widgetRepository.abortRestoreWidgets();
                return;
            }
            this.oldToNewWidgetIdMap = MapsKt__MapsKt.toMap(ArraysKt___ArraysKt.zip(intArrayExtra, intArrayExtra2));
            Logger logger = this.logger;
            CommunalBackupRestoreStartable$$ExternalSyntheticLambda0 communalBackupRestoreStartable$$ExternalSyntheticLambda0 = new CommunalBackupRestoreStartable$$ExternalSyntheticLambda0(0);
            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, communalBackupRestoreStartable$$ExternalSyntheticLambda0, null);
            obtain.setStr1(this.oldToNewWidgetIdMap.toString());
            logger.getBuffer().commit(obtain);
            maybeRestoreWidgets();
            if (this.userSetupComplete) {
                return;
            }
            this.secureSettings.registerContentObserverSync(SettingsHelper.INDEX_USER_SETUP_COMPLETE, this.userSetupObserver);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this, new IntentFilter("android.appwidget.action.APPWIDGET_HOST_RESTORED"), null, null, 0, null, 60);
    }
}
