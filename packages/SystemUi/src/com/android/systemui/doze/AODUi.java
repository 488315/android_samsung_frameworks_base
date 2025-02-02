package com.android.systemui.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.util.Assert;
import com.android.systemui.util.wakelock.WakeLock;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AODUi extends DozeUi {
    public final AmbientDisplayConfiguration mConfig;
    public final C12351 mHostCallback;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.doze.AODUi$2 */
    public abstract /* synthetic */ class AbstractC12362 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.doze.AODUi$1] */
    public AODUi(Context context, AlarmManager alarmManager, WakeLock wakeLock, DozeHost dozeHost, Handler handler, DozeParameters dozeParameters, StatusBarStateController statusBarStateController, DozeLog dozeLog, AmbientDisplayConfiguration ambientDisplayConfiguration) {
        super(context, alarmManager, wakeLock, dozeHost, handler, dozeParameters, statusBarStateController, dozeLog);
        this.mHostCallback = new DozeHost.Callback() { // from class: com.android.systemui.doze.AODUi.1
            @Override // com.android.systemui.doze.DozeHost.Callback
            public final void onAlwaysOnSuppressedChanged(boolean z) {
                AODUi aODUi = AODUi.this;
                AmbientDisplayConfiguration ambientDisplayConfiguration2 = aODUi.mConfig;
                aODUi.mMachine.requestState((ambientDisplayConfiguration2 == null || !ambientDisplayConfiguration2.alwaysOnEnabled(-2) || z) ? DozeMachine.State.DOZE : DozeMachine.State.DOZE_AOD);
            }
        };
        this.mConfig = ambientDisplayConfiguration;
    }

    @Override // com.android.systemui.doze.DozeUi, com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        super.transitionTo(state, state2);
        int i = AbstractC12362.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()];
        C12351 c12351 = this.mHostCallback;
        DozeHost dozeHost = this.mHost;
        if (i == 1) {
            DozeServiceHost dozeServiceHost = (DozeServiceHost) dozeHost;
            dozeServiceHost.getClass();
            Assert.isMainThread();
            dozeServiceHost.mCallbacks.add(c12351);
            return;
        }
        if (i != 2) {
            return;
        }
        DozeServiceHost dozeServiceHost2 = (DozeServiceHost) dozeHost;
        dozeServiceHost2.getClass();
        Assert.isMainThread();
        dozeServiceHost2.mCallbacks.remove(c12351);
    }
}
