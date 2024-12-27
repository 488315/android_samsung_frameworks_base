package com.android.systemui.plugins;

import android.content.ComponentName;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.os.Handler;
import android.os.VibrationEffect;
import android.util.SparseArray;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@Dependencies({@DependsOn(target = StreamState.class), @DependsOn(target = State.class), @DependsOn(target = Callbacks.class)})
@ProvidesInterface(version = 1)
public interface VolumeDialogController {
    public static final int VERSION = 1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    @ProvidesInterface(version = 2)
    public interface Callbacks {
        public static final int VERSION = 2;

        void onAccessibilityModeChanged(Boolean bool);

        void onCaptionComponentStateChanged(Boolean bool, Boolean bool2);

        void onCaptionEnabledStateChanged(Boolean bool, Boolean bool2);

        void onConfigurationChanged();

        void onDismissRequested(int i);

        void onLayoutDirectionChanged(int i);

        default void onPlaySound(int i, boolean z) {
        }

        void onScreenOff();

        void onShowCsdWarning(int i, int i2);

        void onShowRequested(int i, boolean z, int i2);

        void onShowSafetyWarning(int i);

        void onShowSilentHint();

        void onShowVibrateHint();

        void onStateChanged(State state);

        void onVolumeChangedFromKey();

        default void onPlaySound(int i, boolean z, int i2) {
        }

        default void onShowVolumeLimiterToast() {
        }

        default void onKeyEvent(boolean z, boolean z2) {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    @ProvidesInterface(version = 1)
    public final class State {
        public static int NO_ACTIVE_STREAM = -1;
        public static final int VERSION = 1;
        public boolean aodEnabled;
        public int broadcastMode;
        public boolean disallowAlarms;
        public boolean disallowMedia;
        public boolean disallowRinger;
        public boolean disallowSystem;
        public boolean dualAudio;
        public ComponentName effectsSuppressor;
        public String effectsSuppressorName;
        public boolean fixedSCOVolume;
        public boolean isLeBroadcasting;
        public boolean remoteMic;
        public int ringerModeExternal;
        public int ringerModeInternal;
        public int zenMode;
        public final SparseArray<StreamState> states = new SparseArray<>();
        public int activeStream = NO_ACTIVE_STREAM;

        private static void sep(StringBuilder sb, int i) {
            if (i <= 0) {
                sb.append(',');
                return;
            }
            sb.append('\n');
            for (int i2 = 0; i2 < i; i2++) {
                sb.append(' ');
            }
        }

        public State copy() {
            State state = new State();
            for (int i = 0; i < this.states.size(); i++) {
                state.states.put(this.states.keyAt(i), this.states.valueAt(i).copy());
            }
            state.ringerModeExternal = this.ringerModeExternal;
            state.ringerModeInternal = this.ringerModeInternal;
            state.zenMode = this.zenMode;
            ComponentName componentName = this.effectsSuppressor;
            if (componentName != null) {
                state.effectsSuppressor = componentName.clone();
            }
            state.effectsSuppressorName = this.effectsSuppressorName;
            state.activeStream = this.activeStream;
            state.disallowAlarms = this.disallowAlarms;
            state.disallowMedia = this.disallowMedia;
            state.disallowSystem = this.disallowSystem;
            state.disallowRinger = this.disallowRinger;
            state.fixedSCOVolume = this.fixedSCOVolume;
            state.remoteMic = this.remoteMic;
            state.dualAudio = this.dualAudio;
            state.aodEnabled = this.aodEnabled;
            state.isLeBroadcasting = this.isLeBroadcasting;
            state.broadcastMode = this.broadcastMode;
            return state;
        }

        public String toString() {
            return toString(0);
        }

        public String toString(int i) {
            StringBuilder sb = new StringBuilder("{");
            if (i > 0) {
                sep(sb, i);
            }
            for (int i2 = 0; i2 < this.states.size(); i2++) {
                if (i2 > 0) {
                    sep(sb, i);
                }
                int keyAt = this.states.keyAt(i2);
                StreamState valueAt = this.states.valueAt(i2);
                sb.append(AudioSystem.streamToString(keyAt));
                sb.append(":");
                sb.append(valueAt.level);
                sb.append('[');
                sb.append(valueAt.levelMin);
                sb.append("..");
                sb.append(valueAt.levelMax);
                sb.append(']');
                if (valueAt.muted) {
                    sb.append(" [MUTED]");
                }
                if (valueAt.dynamic) {
                    sb.append(" [DYNAMIC]");
                }
            }
            sep(sb, i);
            sb.append("ringerModeExternal:");
            sb.append(this.ringerModeExternal);
            sep(sb, i);
            sb.append("ringerModeInternal:");
            sb.append(this.ringerModeInternal);
            sep(sb, i);
            sb.append("zenMode:");
            sb.append(this.zenMode);
            sep(sb, i);
            sb.append("effectsSuppressor:");
            sb.append(this.effectsSuppressor);
            sep(sb, i);
            sb.append("effectsSuppressorName:");
            sb.append(this.effectsSuppressorName);
            sep(sb, i);
            sb.append("activeStream:");
            sb.append(this.activeStream);
            sep(sb, i);
            sb.append("disallowAlarms:");
            sb.append(this.disallowAlarms);
            sep(sb, i);
            sb.append("disallowMedia:");
            sb.append(this.disallowMedia);
            sep(sb, i);
            sb.append("disallowSystem:");
            sb.append(this.disallowSystem);
            sep(sb, i);
            sb.append("disallowRinger:");
            sb.append(this.disallowRinger);
            if (i > 0) {
                sep(sb, i);
            }
            sb.append('}');
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    @ProvidesInterface(version = 1)
    public final class StreamState {
        public static final int VERSION = 1;
        public boolean appMirroring;
        public String bluetoothDeviceAddress;
        public String bluetoothDeviceName;
        public boolean dynamic;
        public int level;
        public int levelMax;
        public int levelMin;
        public boolean muteSupported;
        public boolean muted;
        public int name;
        public String nameRes;
        public boolean remoteFixedVolume;
        public String remoteLabel;
        public boolean remoteSpeaker;
        public boolean routedToBluetooth;
        public boolean routedToBuds;
        public boolean routedToBuds3;
        public boolean routedToHeadset;
        public boolean routedToHearingAid;
        public boolean routedToHomeMini;

        public StreamState copy() {
            StreamState streamState = new StreamState();
            streamState.dynamic = this.dynamic;
            streamState.level = this.level;
            streamState.levelMin = this.levelMin;
            streamState.levelMax = this.levelMax;
            streamState.muted = this.muted;
            streamState.muteSupported = this.muteSupported;
            streamState.name = this.name;
            streamState.remoteLabel = this.remoteLabel;
            streamState.routedToBluetooth = this.routedToBluetooth;
            streamState.nameRes = this.nameRes;
            streamState.appMirroring = this.appMirroring;
            streamState.remoteSpeaker = this.remoteSpeaker;
            streamState.remoteFixedVolume = this.remoteFixedVolume;
            streamState.routedToHeadset = this.routedToHeadset;
            streamState.routedToBuds = this.routedToBuds;
            streamState.routedToBuds3 = this.routedToBuds3;
            streamState.routedToHomeMini = this.routedToHomeMini;
            streamState.routedToHearingAid = this.routedToHearingAid;
            streamState.bluetoothDeviceAddress = this.bluetoothDeviceAddress;
            streamState.bluetoothDeviceName = this.bluetoothDeviceName;
            return streamState;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("[routedToHeadset=");
            sb.append(this.routedToHeadset);
            sb.append(", routedToBuds=");
            sb.append(this.routedToBuds);
            sb.append(", routedToBuds3=");
            sb.append(this.routedToBuds3);
            sb.append(", routedToHomeMini=");
            sb.append(this.routedToHomeMini);
            sb.append(", routedToHearingAid=");
            sb.append(this.routedToHearingAid);
            sb.append(", remoteFixedVolume=");
            sb.append(this.remoteFixedVolume);
            sb.append(", remoteSpeaker=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.remoteSpeaker, "]");
        }
    }

    void addCallback(Callbacks callbacks, Handler handler);

    boolean areCaptionsEnabled();

    AudioManager getAudioManager();

    void getCaptionsComponentState(boolean z);

    void getCaptionsEnabledState(boolean z);

    void getState();

    boolean hasVibrator();

    boolean isAODVolumePanel();

    boolean isAudioMirroring();

    boolean isBudsTogetherEnabled();

    boolean isDLNAEnabled();

    boolean isLeBroadcasting();

    boolean isMusicShareEnabled();

    boolean isSmartViewEnabled();

    boolean isVolumeStarEnabled();

    void notifyVisible(boolean z);

    void removeCallback(Callbacks callbacks);

    void scheduleTouchFeedback();

    void setActiveStream(int i);

    void setCaptionsEnabledState(boolean z);

    void setRingerMode(int i, boolean z);

    void setSafeVolumeDialogShowing(boolean z);

    void setStreamVolume(int i, int i2);

    void setStreamVolumeDualAudio(int i, int i2, String str);

    boolean supportTvVolumeControl();

    void userActivity();

    void vibrate(VibrationEffect vibrationEffect);
}
