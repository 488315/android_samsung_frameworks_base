package com.android.systemui.bixby2.controller.volume;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.util.AudioManagerWrapper;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AllVolumeController extends VolumeType {
    public static final int $stable = 8;
    private final Lazy audioManagerWrapper$delegate;
    private final Lazy editor$delegate;
    private final Lazy preferences$delegate;

    public AllVolumeController(final Context context) {
        final int i = 0;
        this.audioManagerWrapper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.bixby2.controller.volume.AllVolumeController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioManagerWrapper audioManagerWrapper_delegate$lambda$0;
                SharedPreferences.Editor editor_delegate$lambda$2;
                int i2 = i;
                Object obj = context;
                switch (i2) {
                    case 0:
                        audioManagerWrapper_delegate$lambda$0 = AllVolumeController.audioManagerWrapper_delegate$lambda$0((Context) obj);
                        return audioManagerWrapper_delegate$lambda$0;
                    default:
                        editor_delegate$lambda$2 = AllVolumeController.editor_delegate$lambda$2((AllVolumeController) obj);
                        return editor_delegate$lambda$2;
                }
            }
        });
        this.preferences$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.bixby2.controller.volume.AllVolumeController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SharedPreferences sharedPreferences;
                sharedPreferences = AllVolumeController.this.getSharedPreferences(context);
                return sharedPreferences;
            }
        });
        final int i2 = 1;
        this.editor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.bixby2.controller.volume.AllVolumeController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioManagerWrapper audioManagerWrapper_delegate$lambda$0;
                SharedPreferences.Editor editor_delegate$lambda$2;
                int i22 = i2;
                Object obj = this;
                switch (i22) {
                    case 0:
                        audioManagerWrapper_delegate$lambda$0 = AllVolumeController.audioManagerWrapper_delegate$lambda$0((Context) obj);
                        return audioManagerWrapper_delegate$lambda$0;
                    default:
                        editor_delegate$lambda$2 = AllVolumeController.editor_delegate$lambda$2((AllVolumeController) obj);
                        return editor_delegate$lambda$2;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AudioManagerWrapper audioManagerWrapper_delegate$lambda$0(Context context) {
        return new AudioManagerWrapper(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SharedPreferences.Editor editor_delegate$lambda$2(AllVolumeController allVolumeController) {
        return allVolumeController.getPreferences().edit();
    }

    private final AudioManagerWrapper getAudioManagerWrapper() {
        return (AudioManagerWrapper) this.audioManagerWrapper$delegate.getValue();
    }

    private final SharedPreferences.Editor getEditor() {
        return (SharedPreferences.Editor) this.editor$delegate.getValue();
    }

    private final SharedPreferences getPreferences() {
        return (SharedPreferences) this.preferences$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("VolumeController_preferences", 0);
    }

    private final int getStreamVolume(int i) {
        return getAudioManagerWrapper().getStreamVolume(i);
    }

    private final int loadStreamVolume(String str) {
        int i = getPreferences().getInt(str, 0);
        if (i == 0) {
            i = (int) (getMaxVolume() * 0.3f);
        }
        getEditor().putInt(str, 0);
        getEditor().apply();
        return i;
    }

    private final void saveStreamVolume(String str, int i) {
        getEditor().putInt(str, i);
        getEditor().apply();
    }

    private final void setRingerMode(int i) {
        if (i != 2) {
            saveStreamVolume("Ringtone", getStreamVolume(2));
            saveStreamVolume(PluginLockStar.NOTIFICATION_TYPE, getStreamVolume(5));
            saveStreamVolume("System", getStreamVolume(1));
        }
        getAudioManagerWrapper().setRingerMode(i);
    }

    private final void setUnMuteStream(String str, int i) {
        if (getAudioManagerWrapper().isStreamMute(i)) {
            getAudioManagerWrapper().setStreamVolume(i, loadStreamVolume(str), 0);
        }
    }

    private final void showVolumePanel(int i) {
        getAudioManagerWrapper().adjustStreamVolume(2, 0, i);
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public boolean isStreamMute() {
        return getAudioManagerWrapper().isAllStreamMute();
    }

    public final CommandActionResponse setAllMute() {
        if (getAudioManagerWrapper().isAllStreamMute()) {
            return new CommandActionResponse(2, ActionResults.RESULT_VOLUME_ALREADY_MUTE);
        }
        setRingerMode(1);
        setMute("Media", 3, getStreamVolume(3));
        setMute("Bixby", 11, getStreamVolume(11));
        showVolumePanel(1);
        return new CommandActionResponse(1, "success");
    }

    public final CommandActionResponse setAllUnMute() {
        if (!getAudioManagerWrapper().isAllStreamMute()) {
            return new CommandActionResponse(2, ActionResults.RESULT_VOLUME_ALREADY_UNMUTE);
        }
        if (getRingerMode() != 2) {
            setRingerMode(2);
        }
        setUnMuteStream("System", 1);
        setUnMuteStream(PluginLockStar.NOTIFICATION_TYPE, 5);
        setUnMuteStream("Media", 3);
        setUnMuteStream("Bixby", 11);
        showVolumePanel(5);
        return new CommandActionResponse(1, "success");
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public CommandActionResponse setMute(boolean z) {
        return z ? setAllMute() : setAllUnMute();
    }

    private final void setMute(String str, int i, int i2) {
        if (getAudioManagerWrapper().isStreamMute(i)) {
            return;
        }
        saveStreamVolume(str, i2);
        getAudioManagerWrapper().setStreamVolume(i, 0, 0);
    }
}
