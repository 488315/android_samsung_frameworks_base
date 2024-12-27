package com.android.systemui.volume.middleware;

import com.android.systemui.BasicRune;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.StreamUtil;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumeMiddleware;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AudioManagerController implements VolumeMiddleware {
    public final Lazy infraMediator$delegate;
    public Boolean isHeadsetConnected;
    public boolean isPanelShowing;
    public final Lazy log$delegate;
    public final Lazy store$delegate;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[VolumePanelAction.ActionType.values().length];
            try {
                iArr[VolumePanelAction.ActionType.ACTION_STATE_CHANGED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_PANEL_SHOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_START_SLIDER_TRACKING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_HEADSET_CONNECTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[VolumePanelState.StateType.values().length];
            try {
                iArr2[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_PLAY_SOUND_ON.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_SET_STREAM_VOLUME.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_VOLUME_ICON_CLICKED.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        new Companion(null);
    }

    public AudioManagerController(final VolumeDependencyBase volumeDependencyBase) {
        this.infraMediator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.middleware.AudioManagerController$infraMediator$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (VolumeInfraMediator) ((VolumeDependency) VolumeDependencyBase.this).get(VolumeInfraMediator.class);
            }
        });
        this.store$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.middleware.AudioManagerController$store$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (VolumePanelStore) ((VolumeDependency) VolumeDependencyBase.this).get(VolumePanelStore.class);
            }
        });
        this.log$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.middleware.AudioManagerController$log$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (LogWrapper) ((VolumeDependency) VolumeDependencyBase.this).get(LogWrapper.class);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0166  */
    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object apply(java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 600
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.middleware.AudioManagerController.apply(java.lang.Object):java.lang.Object");
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final void applyState(Object obj) {
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        int i = WhenMappings.$EnumSwitchMapping$1[volumePanelState.getStateType().ordinal()];
        if (i == 1) {
            this.isPanelShowing = false;
            return;
        }
        if (i == 2) {
            if (BasicRune.VOLUME_HOME_IOT) {
                getInfraMediator().initSound(1);
                getInfraMediator().playSound(volumePanelState.getVolumeDirection());
                return;
            } else {
                getInfraMediator().initSound(volumePanelState.getActiveStream());
                getInfraMediator().playSound();
                return;
            }
        }
        if (i == 3) {
            int stream = volumePanelState.getStream();
            VolumePanelRow findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, stream);
            int realLevel = findRow != null ? findRow.getRealLevel() : 0;
            boolean isRemoteMic = volumePanelState.isRemoteMic();
            if (VolumePanelValues.isBluetoothSco(stream) && !isRemoteMic) {
                realLevel--;
            }
            setStreamVolume(volumePanelState, stream, realLevel);
            return;
        }
        if (i != 4) {
            if (i != 5) {
                return;
            }
            getInfraMediator().disableSafeMediaVolume();
        } else {
            int stream2 = volumePanelState.getStream();
            VolumePanelRow findRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, stream2);
            int level = findRow2 != null ? findRow2.getLevel() : 0;
            getInfraMediator().setActiveStream(stream2);
            setStreamVolume(volumePanelState, stream2, level);
            getInfraMediator().setRingerMode(volumePanelState.getRingerModeInternal(), false);
        }
    }

    public final VolumeInfraMediator getInfraMediator() {
        return (VolumeInfraMediator) this.infraMediator$delegate.getValue();
    }

    public final VolumePanelStore getStore() {
        return (VolumePanelStore) this.store$delegate.getValue();
    }

    public final void setStreamVolume(VolumePanelState volumePanelState, int i, int i2) {
        Object obj;
        if ((!volumePanelState.isDualAudio() || i != StreamUtil.getMusicStream(volumePanelState.isMultiSoundBt())) && !VolumePanelValues.isDualAudio(i)) {
            getInfraMediator().setStreamVolume(i, i2);
            return;
        }
        Iterator<T> it = volumePanelState.getVolumeRowList().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (((VolumePanelRow) obj).getStreamType() == i) {
                    break;
                }
            }
        }
        VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
        getInfraMediator().setStreamVolumeDualAudio(i, i2, volumePanelRow != null ? volumePanelRow.getDualBtDeviceAddress() : null);
    }
}
