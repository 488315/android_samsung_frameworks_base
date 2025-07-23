package com.android.systemui.dreams;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.service.dreams.IDreamManager;
import android.util.Log;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.util.condition.ConditionalCoreStartable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DreamOverlayRegistrant extends ConditionalCoreStartable {
    public static final boolean DEBUG;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final Context context;
    public boolean currentRegisteredState;
    public final IDreamManager dreamManager;
    public final DreamLogger logger;
    public final ComponentName overlayServiceComponent;
    public final PackageManager packageManager;
    public final DreamOverlayRegistrant$receiver$1 receiver;

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
        DEBUG = Log.isLoggable("DreamOverlayRegistrant", 3);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.dreams.DreamOverlayRegistrant$receiver$1] */
    public DreamOverlayRegistrant(Context context, ComponentName componentName, Monitor monitor, PackageManager packageManager, IDreamManager iDreamManager, CommunalSettingsInteractor communalSettingsInteractor, LogBuffer logBuffer) {
        super(monitor);
        this.context = context;
        this.overlayServiceComponent = componentName;
        this.packageManager = packageManager;
        this.dreamManager = iDreamManager;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.logger = new DreamLogger(logBuffer, "DreamOverlayRegistrant");
        this.receiver = new BroadcastReceiver() { // from class: com.android.systemui.dreams.DreamOverlayRegistrant$receiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (DreamOverlayRegistrant.DEBUG) {
                    Log.d("DreamOverlayRegistrant", "package changed receiver - onReceive");
                }
                DreamOverlayRegistrant.this.registerOverlayService();
            }
        };
    }

    @Override // com.android.systemui.util.condition.ConditionalCoreStartable
    public final void onStart() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(this.overlayServiceComponent.getPackageName(), 0);
        this.context.registerReceiver(this.receiver, intentFilter);
        registerOverlayService();
        if (this.packageManager.getServiceInfo(this.overlayServiceComponent, 640).enabled) {
            return;
        }
        this.communalSettingsInteractor.isV2FlagEnabled();
    }

    public final void registerOverlayService() {
        String str;
        boolean z = false;
        try {
            Log.d("DreamOverlayRegistrant", "trying to find component:" + this.overlayServiceComponent);
            if (this.packageManager.getComponentEnabledSetting(this.overlayServiceComponent) != 2) {
                if (this.packageManager.getServiceInfo(this.overlayServiceComponent, 640).enabled) {
                    z = true;
                } else {
                    this.communalSettingsInteractor.isV2FlagEnabled();
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("DreamOverlayRegistrant", "could not find dream overlay service");
        }
        if (this.currentRegisteredState == z) {
            return;
        }
        this.currentRegisteredState = z;
        try {
            if (DEBUG) {
                if (z) {
                    str = "registering dream overlay service:" + this.overlayServiceComponent;
                } else {
                    str = "clearing dream overlay service";
                }
                Log.d("DreamOverlayRegistrant", str);
            }
            this.dreamManager.registerDreamOverlayService(this.currentRegisteredState ? this.overlayServiceComponent : null);
            DreamLogger dreamLogger = this.logger;
            boolean z2 = this.currentRegisteredState;
            dreamLogger.getClass();
            LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, new DreamLogger$$ExternalSyntheticLambda0(1), null);
            obtain.setBool1(z2);
            dreamLogger.getBuffer().commit(obtain);
        } catch (RemoteException e) {
            Log.e("DreamOverlayRegistrant", "could not register dream overlay service:" + e);
        }
    }
}
