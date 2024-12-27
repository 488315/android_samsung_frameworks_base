package com.android.systemui.doze;

import android.content.Context;
import android.hardware.Sensor;
import android.os.Handler;
import com.android.systemui.LsRune;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.settings.SystemSettings;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AODScreenBrightness extends DozeScreenBrightness {
    public int[] mBrightnessValues;
    public int mDozeMode;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.doze.AODScreenBrightness$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public AODScreenBrightness(Context context, DozeMachine.Service service, AsyncSensorManager asyncSensorManager, Optional<Sensor>[] optionalArr, DozeHost dozeHost, Handler handler, AlwaysOnDisplayPolicy alwaysOnDisplayPolicy, WakefulnessLifecycle wakefulnessLifecycle, DozeParameters dozeParameters, DevicePostureController devicePostureController, DozeLog dozeLog, SystemSettings systemSettings) {
        super(context, service, asyncSensorManager, optionalArr, dozeHost, handler, alwaysOnDisplayPolicy, wakefulnessLifecycle, dozeParameters, devicePostureController, dozeLog, systemSettings);
        this.mBrightnessValues = new int[]{1, 97};
        if (LsRune.AOD_BRIGHTNESS_CONTROL) {
            this.mDozeMode = 65538;
        }
    }

    @Override // com.android.systemui.doze.DozeScreenBrightness
    public final void resetBrightnessToDefault() {
        ((DozeServiceHost) this.mDozeHost).setAodDimmingScrim(0.0f);
    }

    @Override // com.android.systemui.doze.DozeScreenBrightness, com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()];
        if (i == 1) {
            resetBrightnessToDefault();
            return;
        }
        if (i == 2) {
            ((DozeServiceHost) this.mDozeHost).setAodDimmingScrim(0.0f);
            return;
        }
        if (i == 3) {
            this.mDozeService.semSetDozeScreenBrightness(this.mDozeMode, -1);
            ((DozeServiceHost) this.mDozeHost).setAodDimmingScrim(1.0f);
        } else {
            if (i != 4) {
                return;
            }
            resetBrightnessToDefault();
        }
    }

    public final void updateDozeBrightness(int i, int i2, int i3) {
        boolean z = this.mDozeMode != i;
        this.mDozeMode = i;
        if (i3 == -1) {
            if (i2 >= 0) {
                int[] iArr = this.mBrightnessValues;
                if (i2 < iArr.length) {
                    i3 = iArr[i2];
                }
            }
            i3 = -1;
        }
        boolean z2 = i3 >= 0;
        if (z || z2) {
            this.mDozeService.semSetDozeScreenBrightness(i, i3);
        }
    }
}
