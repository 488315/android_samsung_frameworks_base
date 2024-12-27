package com.android.systemui.volume;

import android.content.Context;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.volume.soundassistant.SoundAssistantChecker;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.SoundAssistantManagerWrapper;
import com.android.systemui.volume.view.standard.VolumePanelWindow;
import com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion;
import com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView;
import com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelWindow;
import com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation;
import com.samsung.systemui.splugins.volume.ExtendableVolumePanel;
import com.samsung.systemui.splugins.volume.VolumeDisposable;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumeObservable;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VolumePanelImpl implements VolumeObserver, ExtendableVolumePanel {
    public VolumeObserver actionObserver;
    public final HandlerWrapper handlerWrapper;
    public final VolumeInfraMediator infraMediator;
    public final VolumePanelImpl$safetyVolumeCallback$1 safetyVolumeCallback;
    public final SoundAssistantManagerWrapper soundAssistant;
    public final SoundAssistantChecker soundAssistantChecker;
    public final VolumePanelStore store;
    public VolumeDisposable storeDisposable;
    public SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation;
    public SubFullLayoutVolumePanelWindow subFullLayoutWindow;
    public final VolumePanelImpl$timeOutCallback$1 timeOutCallback;
    public final VolumeDependencyBase volDeps;
    public VolumePanelWindow window;

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

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            try {
                iArr[VolumePanelState.StateType.STATE_IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW_SUB_DISPLAY_VOLUME_PANEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_ARROW_LEFT_CLICKED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_ARROW_RIGHT_CLICKED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_TOUCH_PANEL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_RESCHEDULE_TIME_OUT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_EXPAND_STATE_CHANGED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_OPEN_THEME_CHANGED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_CONFIGURATION_CHANGED.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SETTINGS_BUTTON_CLICKED.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_STATUS_MESSAGE_CLICKED.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_STATUS_LE_BROADCASTING_MESSAGE_CLICKED.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_STATUS_DO_NOT_DISTURB_MESSAGE_CLICKED.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_FOLDER_STATE_CHANGED.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW_VOLUME_CSD_100_WARNING_DIALOG.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_COVER_STATE_CHANGED.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_VOLUME_CSD_100_WARNING_DIALOG_FLAG_DISMISS.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_VOLUME_CSD_100_WARNING_DIALOG_SET_SAFETY_VOLUME.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v13, types: [com.android.systemui.volume.VolumePanelImpl$timeOutCallback$1] */
    /* JADX WARN: Type inference failed for: r2v14, types: [com.android.systemui.volume.VolumePanelImpl$safetyVolumeCallback$1] */
    public VolumePanelImpl(Context context, VolumeDependencyBase volumeDependencyBase) {
        this.volDeps = volumeDependencyBase;
        VolumeDependency volumeDependency = (VolumeDependency) volumeDependencyBase;
        this.infraMediator = (VolumeInfraMediator) volumeDependency.get(VolumeInfraMediator.class);
        this.handlerWrapper = (HandlerWrapper) volumeDependency.get(HandlerWrapper.class);
        this.soundAssistant = (SoundAssistantManagerWrapper) volumeDependency.get(SoundAssistantManagerWrapper.class);
        VolumePanelStore volumePanelStore = (VolumePanelStore) volumeDependency.get(VolumePanelStore.class);
        this.store = volumePanelStore;
        this.soundAssistantChecker = (SoundAssistantChecker) volumeDependency.get(SoundAssistantChecker.class);
        this.window = (VolumePanelWindow) volumeDependency.get(VolumePanelWindow.class);
        this.storeDisposable = volumePanelStore.subscribe(this);
        this.actionObserver = volumePanelStore;
        this.timeOutCallback = new Runnable() { // from class: com.android.systemui.volume.VolumePanelImpl$timeOutCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumePanelImpl.this.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TIME_OUT).build(), false);
            }
        };
        this.safetyVolumeCallback = new Runnable() { // from class: com.android.systemui.volume.VolumePanelImpl$safetyVolumeCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumePanelImpl.this.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_CSD_100_WARNING_DIALOG_TIMEOUT).build(), false);
            }
        };
    }

    public final void dispatch(final VolumePanelAction volumePanelAction, boolean z) {
        if (!z) {
            this.actionObserver.onChanged(volumePanelAction);
        } else {
            this.handlerWrapper.post(new Runnable() { // from class: com.android.systemui.volume.VolumePanelImpl$dispatch$1
                @Override // java.lang.Runnable
                public final void run() {
                    VolumePanelImpl.this.actionObserver.onChanged(volumePanelAction);
                }
            });
        }
    }

    @Override // com.samsung.systemui.splugins.volume.ExtendableVolumePanel
    public final VolumePanelState getVolumePanelCurrentState() {
        return this.store.currentState;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(Object obj) {
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelState.getStateType().ordinal()];
        VolumePanelImpl$timeOutCallback$1 volumePanelImpl$timeOutCallback$1 = this.timeOutCallback;
        VolumeInfraMediator volumeInfraMediator = this.infraMediator;
        VolumePanelImpl$safetyVolumeCallback$1 volumePanelImpl$safetyVolumeCallback$1 = this.safetyVolumeCallback;
        HandlerWrapper handlerWrapper = this.handlerWrapper;
        switch (i) {
            case 1:
                volumeInfraMediator.notifyVisible(false);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                int timeOut = volumePanelState.getTimeOut();
                handlerWrapper.remove(volumePanelImpl$timeOutCallback$1);
                handlerWrapper.postDelayed(volumePanelImpl$timeOutCallback$1, timeOut);
                volumeInfraMediator.userActivity();
                volumeInfraMediator.notifyVisible(true);
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                if (volumePanelState.isShowing() || volumePanelState.isShowingSubDisplayVolumePanel()) {
                    int timeOut2 = volumePanelState.getTimeOut();
                    handlerWrapper.remove(volumePanelImpl$timeOutCallback$1);
                    handlerWrapper.postDelayed(volumePanelImpl$timeOutCallback$1, timeOut2);
                    volumeInfraMediator.userActivity();
                    break;
                }
                break;
            case 10:
                handlerWrapper.remove(volumePanelImpl$timeOutCallback$1);
                volumeInfraMediator.notifyVisible(false);
                break;
            case 11:
                volumeInfraMediator.startSettingsActivity();
                break;
            case 12:
            case 13:
                recreateVolumePanelForNewConfig();
                break;
            case 14:
                volumeInfraMediator.startVolumeSettingsActivity();
                break;
            case 15:
                volumeInfraMediator.startHearingEnhancementsActivity();
                break;
            case 16:
                volumeInfraMediator.startLeBroadcastActivity();
                break;
            case 17:
                volumeInfraMediator.startDoNotDisturbActivity();
                break;
            case 18:
                if (!volumePanelState.isFolded()) {
                    if (!BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG) {
                        SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation = this.subDisplayVolumePanelPresentation;
                        if (subDisplayVolumePanelPresentation != null) {
                            subDisplayVolumePanelPresentation.dismiss();
                            subDisplayVolumePanelPresentation.mStoreInteractor.dispose();
                            this.subDisplayVolumePanelPresentation = null;
                            break;
                        }
                    } else {
                        SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow = this.subFullLayoutWindow;
                        if (subFullLayoutVolumePanelWindow != null) {
                            subFullLayoutVolumePanelWindow.dismiss();
                            ((StoreInteractor) subFullLayoutVolumePanelWindow.storeInteractor$delegate.getValue()).dispose();
                            SubFullLayoutVolumePanelView subFullLayoutVolumePanelView = subFullLayoutVolumePanelWindow.panelView;
                            subFullLayoutVolumePanelView.storeInteractor.dispose();
                            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelView.volumePanelMotion;
                            if (subFullLayoutVolumePanelMotion == null) {
                                subFullLayoutVolumePanelMotion = null;
                            }
                            subFullLayoutVolumePanelMotion.storeInteractor.dispose();
                            this.subFullLayoutWindow = null;
                            break;
                        }
                    }
                } else {
                    boolean z = BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG;
                    VolumeDependencyBase volumeDependencyBase = this.volDeps;
                    if (!z) {
                        SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation2 = (SubDisplayVolumePanelPresentation) ((VolumeDependency) volumeDependencyBase).getNewObject(SubDisplayVolumePanelPresentation.class);
                        subDisplayVolumePanelPresentation2.mStoreInteractor.observeStore();
                        this.subDisplayVolumePanelPresentation = subDisplayVolumePanelPresentation2;
                        break;
                    } else {
                        SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow2 = (SubFullLayoutVolumePanelWindow) ((VolumeDependency) volumeDependencyBase).getNewObject(SubFullLayoutVolumePanelWindow.class);
                        subFullLayoutVolumePanelWindow2.observeStore();
                        this.subFullLayoutWindow = subFullLayoutVolumePanelWindow2;
                        break;
                    }
                }
                break;
            case 19:
                if (BasicRune.VOLUME_MONITOR_PHASE_3) {
                    handlerWrapper.remove(volumePanelImpl$safetyVolumeCallback$1);
                    handlerWrapper.postDelayed(volumePanelImpl$safetyVolumeCallback$1, 60000L);
                    volumeInfraMediator.userActivity();
                    break;
                }
                break;
            case 20:
                if (BasicRune.VOLUME_MONITOR_PHASE_3 && volumePanelState.getCoverType() != 8) {
                    handlerWrapper.remove(volumePanelImpl$safetyVolumeCallback$1);
                    break;
                }
                break;
            case 21:
            case 22:
                if (BasicRune.VOLUME_MONITOR_PHASE_3) {
                    handlerWrapper.remove(volumePanelImpl$safetyVolumeCallback$1);
                    break;
                }
                break;
            case 23:
                if (BasicRune.VOLUME_MONITOR_PHASE_3) {
                    volumeInfraMediator.setSafeMediaVolume();
                    break;
                }
                break;
        }
    }

    @Override // com.samsung.systemui.splugins.volume.ExtendableVolumePanel
    public final void recreateVolumePanelForNewConfig() {
        Log.d("VolumePanelImpl", "recreateVolumePanelForNewConfig");
        VolumePanelWindow volumePanelWindow = this.window;
        volumePanelWindow.dismiss();
        volumePanelWindow.dispose();
        VolumeDependency volumeDependency = (VolumeDependency) this.volDeps;
        VolumePanelWindow volumePanelWindow2 = (VolumePanelWindow) volumeDependency.getNewObject(VolumePanelWindow.class);
        volumePanelWindow2.observeStore();
        this.window = volumePanelWindow2;
        SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow = this.subFullLayoutWindow;
        if (subFullLayoutVolumePanelWindow != null) {
            subFullLayoutVolumePanelWindow.dismiss();
            ((StoreInteractor) subFullLayoutVolumePanelWindow.storeInteractor$delegate.getValue()).dispose();
            SubFullLayoutVolumePanelView subFullLayoutVolumePanelView = subFullLayoutVolumePanelWindow.panelView;
            subFullLayoutVolumePanelView.storeInteractor.dispose();
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelView.volumePanelMotion;
            if (subFullLayoutVolumePanelMotion == null) {
                subFullLayoutVolumePanelMotion = null;
            }
            subFullLayoutVolumePanelMotion.storeInteractor.dispose();
            SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow2 = (SubFullLayoutVolumePanelWindow) volumeDependency.getNewObject(SubFullLayoutVolumePanelWindow.class);
            subFullLayoutVolumePanelWindow2.observeStore();
            this.subFullLayoutWindow = subFullLayoutVolumePanelWindow2;
        }
    }

    @Override // com.samsung.systemui.splugins.volume.ExtendableVolumePanel
    public final void restoreToDefaultStore() {
        Log.d("VolumePanelImpl", "restoreToDefaultStore");
        this.storeDisposable.dispose();
        VolumePanelStore volumePanelStore = this.store;
        this.storeDisposable = volumePanelStore.subscribe(this);
        this.actionObserver = volumePanelStore;
        this.window.observeStore();
        this.soundAssistantChecker.updateState(false);
    }

    @Override // com.samsung.systemui.splugins.volume.ExtendableVolumePanel
    public final void setActionObserver(VolumeObserver volumeObserver) {
        Log.d("VolumePanelImpl", "setActionObserver : newActionObserver=" + volumeObserver + ", volumeStarVersion=0");
        this.window.dispose();
        this.actionObserver = volumeObserver;
    }

    @Override // com.samsung.systemui.splugins.volume.ExtendableVolumePanel
    public final void setStateObservable(VolumeObservable volumeObservable) {
        Log.d("VolumePanelImpl", "setStateObservable : newStateObservable=" + volumeObservable + ", volumeStarVersion=0");
        this.storeDisposable.dispose();
        this.window.dispose();
        this.storeDisposable = volumeObservable.subscribe(this);
        this.soundAssistantChecker.updateState(true);
    }
}
