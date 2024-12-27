package com.android.systemui.bixby2.controller.volume;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.provider.Settings;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.util.AudioManagerWrapper;
import com.android.systemui.plugins.subscreen.SubRoom;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class VolumeType {
    public static final int $stable = 0;
    public static final Companion Companion = new Companion(null);
    public static final String TAG = "VolumeController";
    private static AudioManagerWrapper audioManagerWrapper;
    private static Context context;
    private static SharedPreferences.Editor editor;
    private static NotificationManager notificationManager;
    private static SharedPreferences preferences;
    private final int status = 1;
    private final String statusCode = "success";
    private final String streamTypeToString = "";

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public final VolumeType create(Context context, int i) {
            VolumeType.audioManagerWrapper = new AudioManagerWrapper(context);
            VolumeType.notificationManager = (NotificationManager) context.getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION);
            VolumeType.context = context;
            VolumeType.preferences = context.getSharedPreferences("VolumeController_preferences", 0);
            SharedPreferences sharedPreferences = VolumeType.preferences;
            VolumeType.editor = sharedPreferences != null ? sharedPreferences.edit() : null;
            return i != 1 ? i != 2 ? i != 3 ? i != 5 ? i != 11 ? i != 30 ? i != 31 ? new InvalidVolumeController() : new AllVolumeController(context) : new BluetoothVolumeController(context) : new BixbyVolumeController() : new NotificationVolumeController() : new MusicVolumeController(context) : new RingVolumeController() : new SystemVolumeController();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final CommandActionResponse getAlreadySetResponse(int i) {
        return i == getMaxVolume() ? new CommandActionResponse(2, ActionResults.RESULT_VOLUME_ALREADY_MAXIMUM) : i == getMinVolume() ? new CommandActionResponse(2, ActionResults.RESULT_VOLUME_ALREADY_MINIMUM) : new CommandActionResponse(2, "already_set");
    }

    private final CommandActionResponse getMuteResponse() {
        return isStreamMute() ? new CommandActionResponse(2, ActionResults.RESULT_VOLUME_ALREADY_MUTE) : setVolume(0, 1, true);
    }

    private final CommandActionResponse getUnMuteResponse() {
        return !isStreamMute() ? new CommandActionResponse(2, ActionResults.RESULT_VOLUME_ALREADY_UNMUTE) : setVolume(loadVolume(), 5, false);
    }

    private final boolean isAllSoundOff() {
        try {
            Context context2 = context;
            if (context2 == null) {
                context2 = null;
            }
            return Settings.Global.getInt(context2.getContentResolver(), "all_sound_off") == 1;
        } catch (Settings.SettingNotFoundException unused) {
            return false;
        }
    }

    private final int loadVolume() {
        SharedPreferences sharedPreferences = preferences;
        int i = sharedPreferences != null ? sharedPreferences.getInt(getStreamTypeToString(), 0) : 0;
        if (i == 0) {
            getStreamTypeToString();
            i = (int) (getMaxVolume() * 0.3f);
        }
        saveVolume(0);
        return i;
    }

    private final void saveVolume(int i) {
        SharedPreferences.Editor editor2 = editor;
        if (editor2 != null) {
            editor2.putInt(getStreamTypeToString(), i);
            editor2.apply();
        }
    }

    private final boolean volumeStreamAllowedByDnd() {
        NotificationManager notificationManager2 = notificationManager;
        if (notificationManager2 == null) {
            notificationManager2 = null;
        }
        if (notificationManager2.getZenMode() == 0) {
            return true;
        }
        NotificationManager notificationManager3 = notificationManager;
        NotificationManager.Policy notificationPolicy = (notificationManager3 != null ? notificationManager3 : null).getNotificationPolicy();
        return notificationPolicy != null && volumeStreamAllowedByDnd(notificationPolicy);
    }

    public int getMaxVolume() {
        AudioManagerWrapper audioManagerWrapper2 = audioManagerWrapper;
        if (audioManagerWrapper2 == null) {
            audioManagerWrapper2 = null;
        }
        return audioManagerWrapper2.getStreamMaxVolume(getStreamType());
    }

    public int getMinVolume() {
        AudioManagerWrapper audioManagerWrapper2 = audioManagerWrapper;
        if (audioManagerWrapper2 == null) {
            audioManagerWrapper2 = null;
        }
        return audioManagerWrapper2.getStreamMinVolume(getStreamType());
    }

    public int getRingerMode() {
        AudioManagerWrapper audioManagerWrapper2 = audioManagerWrapper;
        if (audioManagerWrapper2 == null) {
            audioManagerWrapper2 = null;
        }
        return audioManagerWrapper2.getRingerMode();
    }

    public int getStatus() {
        return this.status;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public int getStreamType() {
        return AudioManager.semGetActiveStreamType();
    }

    public String getStreamTypeToString() {
        return this.streamTypeToString;
    }

    public int getVolume() {
        AudioManagerWrapper audioManagerWrapper2 = audioManagerWrapper;
        if (audioManagerWrapper2 == null) {
            audioManagerWrapper2 = null;
        }
        return audioManagerWrapper2.getStreamVolume(getStreamType());
    }

    public boolean isStreamDisabled() {
        return false;
    }

    public boolean isStreamMute() {
        return getVolume() == getMinVolume();
    }

    public boolean isVoiceCapable() {
        AudioManagerWrapper audioManagerWrapper2 = audioManagerWrapper;
        if (audioManagerWrapper2 == null) {
            audioManagerWrapper2 = null;
        }
        return audioManagerWrapper2.shouldShowRingtoneVolume();
    }

    public final CommandActionResponse isVolumeStateValid(boolean z) {
        return isAllSoundOff() ? new CommandActionResponse(2, ActionResults.RESULT_ALLSOUNDMUTE_ON) : !volumeStreamAllowedByDnd() ? new CommandActionResponse(2, ActionResults.RESULT_NOT_DISTURBMODE_ON) : (!isStreamDisabled() || z) ? new CommandActionResponse(1, "success") : new CommandActionResponse(2, ActionResults.RESULT_SILENTMODE_ON);
    }

    public CommandActionResponse setMute(boolean z) {
        return z ? getMuteResponse() : getUnMuteResponse();
    }

    public void setStreamVolume(int i, int i2) {
        if (i == 0) {
            saveVolume(getVolume());
        }
        getStreamType();
        AudioManagerWrapper audioManagerWrapper2 = audioManagerWrapper;
        if (audioManagerWrapper2 == null) {
            audioManagerWrapper2 = null;
        }
        audioManagerWrapper2.setStreamVolume(getStreamType(), i, i2);
    }

    public final CommandActionResponse setVolume(int i, int i2, boolean z) {
        if (getStatus() != 1) {
            return new CommandActionResponse(2, getStatusCode());
        }
        CommandActionResponse isVolumeStateValid = isVolumeStateValid(z);
        if (isVolumeStateValid.responseCode == 1) {
            if (getVolume() == i) {
                return getAlreadySetResponse(i);
            }
            setStreamVolume(i, i2);
        }
        return isVolumeStateValid;
    }

    public boolean volumeStreamAllowedByDnd(NotificationManager.Policy policy) {
        return (policy.priorityCategories & 64) != 0;
    }

    public static /* synthetic */ void getStreamTypeToString$annotations() {
    }
}
