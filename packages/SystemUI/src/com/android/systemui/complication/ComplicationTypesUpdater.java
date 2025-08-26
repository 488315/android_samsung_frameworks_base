package com.android.systemui.complication;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArraySet;
import com.android.settingslib.dream.DreamBackend;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.util.condition.ConditionalCoreStartable;
import com.android.systemui.util.settings.SecureSettings;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class ComplicationTypesUpdater extends ConditionalCoreStartable {
    public final DreamBackend mDreamBackend;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final Executor mExecutor;
    public final SecureSettings mSecureSettings;

    /* renamed from: com.android.systemui.complication.ComplicationTypesUpdater$1, reason: invalid class name */
    public class AnonymousClass1 extends ContentObserver {
        public AnonymousClass1(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            ComplicationTypesUpdater.this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.complication.ComplicationTypesUpdater$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ComplicationTypesUpdater complicationTypesUpdater = ComplicationTypesUpdater.this;
                    final DreamOverlayStateController dreamOverlayStateController = complicationTypesUpdater.mDreamOverlayStateController;
                    DreamBackend dreamBackend = complicationTypesUpdater.mDreamBackend;
                    ArraySet arraySet = Settings.Secure.getInt(dreamBackend.mContext.getContentResolver(), "screensaver_complications_enabled", 1) == 1 ? new ArraySet(dreamBackend.mSupportedComplications) : new ArraySet();
                    if (Settings.Secure.getInt(dreamBackend.mContext.getContentResolver(), "lockscreen_show_controls", 0) != 1 || Settings.Secure.getInt(dreamBackend.mContext.getContentResolver(), "screensaver_home_controls_enabled", 1) != 1) {
                        arraySet.remove(6);
                    } else if (dreamBackend.mSupportedComplications.contains(6)) {
                        arraySet.add(6);
                    }
                    final int iReduce = arraySet.stream().mapToInt(new ComplicationUtils$$ExternalSyntheticLambda0()).reduce(0, new ComplicationUtils$$ExternalSyntheticLambda1());
                    dreamOverlayStateController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            DreamOverlayStateController dreamOverlayStateController2 = dreamOverlayStateController;
                            int i = iReduce;
                            DreamLogger dreamLogger = dreamOverlayStateController2.mLogger;
                            dreamLogger.getClass();
                            DreamLogger$$ExternalSyntheticLambda0 dreamLogger$$ExternalSyntheticLambda0 = new DreamLogger$$ExternalSyntheticLambda0(8);
                            LogMessage logMessageObtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, dreamLogger$$ExternalSyntheticLambda0, null);
                            logMessageObtain.setInt1(i);
                            dreamLogger.getBuffer().commit(logMessageObtain);
                            dreamOverlayStateController2.mAvailableComplicationTypes = i;
                            dreamOverlayStateController2.notifyCallbacksLocked(new DreamOverlayStateController$$ExternalSyntheticLambda0(1));
                        }
                    });
                }
            });
        }
    }

    public ComplicationTypesUpdater(DreamBackend dreamBackend, Executor executor, SecureSettings secureSettings, DreamOverlayStateController dreamOverlayStateController, Monitor monitor) {
        super(monitor);
        this.mDreamBackend = dreamBackend;
        this.mExecutor = executor;
        this.mSecureSettings = secureSettings;
        this.mDreamOverlayStateController = dreamOverlayStateController;
    }

    @Override // com.android.systemui.util.condition.ConditionalCoreStartable
    public final void onStart() {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
        int iMyUserId = UserHandle.myUserId();
        SecureSettings secureSettings = this.mSecureSettings;
        secureSettings.registerContentObserverForUserSync("screensaver_complications_enabled", anonymousClass1, iMyUserId);
        secureSettings.registerContentObserverForUserSync("screensaver_home_controls_enabled", anonymousClass1, UserHandle.myUserId());
        secureSettings.registerContentObserverForUserSync("lockscreen_show_controls", anonymousClass1, UserHandle.myUserId());
        anonymousClass1.onChange(false);
    }
}
