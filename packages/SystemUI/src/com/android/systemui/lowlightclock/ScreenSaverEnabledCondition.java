package com.android.systemui.lowlightclock;

import android.R;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.util.Log;
import com.android.systemui.shared.condition.Condition;
import com.android.systemui.util.settings.SecureSettings;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class ScreenSaverEnabledCondition extends Condition {
    public static final String TAG;
    public final boolean screenSaverEnabledByDefaultConfig;
    public final ScreenSaverEnabledCondition$screenSaverSettingObserver$1 screenSaverSettingObserver;
    public final SecureSettings secureSettings;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = "ScreenSaverEnabledCondition";
    }

    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.lowlightclock.ScreenSaverEnabledCondition$screenSaverSettingObserver$1] */
    public ScreenSaverEnabledCondition(CoroutineScope coroutineScope, Resources resources, SecureSettings secureSettings) {
        super(coroutineScope, null, false, 6, null);
        this.secureSettings = secureSettings;
        this.screenSaverEnabledByDefaultConfig = resources.getBoolean(R.bool.config_enableAppWidgetService);
        this.screenSaverSettingObserver = new ContentObserver() { // from class: com.android.systemui.lowlightclock.ScreenSaverEnabledCondition$screenSaverSettingObserver$1
            {
                super(null);
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                ScreenSaverEnabledCondition screenSaverEnabledCondition = this.this$0;
                String str = ScreenSaverEnabledCondition.TAG;
                screenSaverEnabledCondition.updateScreenSaverEnabledSetting();
            }
        };
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final int getStartStrategy() {
        return 0;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final Object start(Continuation continuation) {
        this.secureSettings.registerContentObserverForUserSync("screensaver_enabled", this.screenSaverSettingObserver, -2);
        updateScreenSaverEnabledSetting();
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
        this.secureSettings.unregisterContentObserverSync(this.screenSaverSettingObserver);
    }

    public final void updateScreenSaverEnabledSetting() {
        boolean z = this.secureSettings.getIntForUser("screensaver_enabled", this.screenSaverEnabledByDefaultConfig ? 1 : 0, -2) != 0;
        if (!z) {
            Log.i(TAG, "Disabling low-light clock because screen saver has been disabled");
        }
        updateCondition(z);
    }
}
