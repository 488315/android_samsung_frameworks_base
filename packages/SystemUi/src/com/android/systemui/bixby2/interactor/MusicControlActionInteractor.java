package com.android.systemui.bixby2.interactor;

import android.content.Context;
import android.media.session.MediaSessionManager;
import android.util.Log;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType;
import com.android.systemui.bixby2.controller.volume.VolumeType;
import com.android.systemui.bixby2.util.AudioManagerWrapper;
import com.android.systemui.bixby2.util.MediaParamsParser;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.BooleanAction;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.action.ModeAction;
import com.samsung.android.sdk.command.provider.CommandProvider;
import com.samsung.android.sdk.command.provider.ICommandActionCallback;
import com.samsung.android.sdk.command.template.MediaControlTemplate;
import com.samsung.android.sdk.command.template.SliderTemplate;
import com.samsung.android.sdk.command.template.ToggleTemplate;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MusicControlActionInteractor implements ActionInteractor {
    public static final Companion Companion = new Companion(null);
    private static final String KEY_NEW_VALUE = "key_new_value";
    private static final String MUTE_ACTION_PREFIX = "mute";
    public static final int STREAM_ALL = 31;
    public static final int STREAM_BLUETOOTH = 30;
    private static final int SUPPORTED_FLAG = 1023;
    private static final String TAG = "MusicControlActionInteractor";
    private AudioManagerWrapper audioManagerWrapper;
    private Context context;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Action {
        volume_control,
        volume_control_ringtone,
        volume_control_media,
        volume_control_noti,
        volume_control_system,
        volume_control_bixby,
        volume_control_bluetooth,
        mute_volume,
        mute_all_volume,
        mute_ringtones_volume,
        mute_media_volume,
        mute_noti_volume,
        mute_system_volume,
        mute_bixby_volume,
        mute_bluetooth_volume,
        control_music
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MusicControlActionInteractor(Context context) {
        this.context = context;
        this.audioManagerWrapper = new AudioManagerWrapper(context);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0099 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0060 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0077 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b2 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b4 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x00a6 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0083 A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final int getStreamTypeFromString(Context context, String str) {
        switch (str.hashCode()) {
            case -1961507235:
                return !str.equals("mute_noti_volume") ? -1 : 5;
            case -1853328508:
                if (str.equals("mute_system_volume")) {
                    return 1;
                }
                break;
            case -1719581065:
                if (str.equals("mute_bixby_volume")) {
                    return 11;
                }
                break;
            case -1477758307:
                if (!str.equals("volume_control_noti")) {
                }
                break;
            case -711551941:
                if (str.equals("mute_media_volume")) {
                    return 3;
                }
                break;
            case -682366098:
                if (str.equals("mute_ringtones_volume")) {
                    return 2;
                }
                break;
            case -610069815:
                if (!str.equals("volume_control_ringtone")) {
                }
                break;
            case -84029337:
                if (str.equals("volume_control_bluetooth")) {
                    return 30;
                }
                break;
            case 437139768:
                if (str.equals("volume_control")) {
                    return this.audioManagerWrapper.getAdjustedStreamType(context);
                }
            case 988376414:
                if (str.equals("mute_all_volume")) {
                    return 31;
                }
                break;
            case 1232193329:
                if (!str.equals("mute_bluetooth_volume")) {
                }
                break;
            case 1422875489:
                if (!str.equals("volume_control_bixby")) {
                }
                break;
            case 1432896029:
                if (!str.equals("volume_control_media")) {
                }
                break;
            case 1660806934:
                if (!str.equals("volume_control_system")) {
                }
                break;
        }
    }

    private final boolean matchAction(String str) {
        for (Action action : Action.values()) {
            if (Intrinsics.areEqual(action.name(), str)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public List<String> getSupportingActions() {
        Action[] values = Action.values();
        ArrayList arrayList = new ArrayList(values.length);
        for (Action action : values) {
            arrayList.add(action.name());
        }
        return arrayList;
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command) {
        if (!matchAction(str)) {
            return null;
        }
        boolean contains = StringsKt__StringsKt.contains(str, "volume_control", false);
        String str2 = command.mCommandId;
        if (contains) {
            VolumeType.Companion companion = VolumeType.Companion;
            Context context = this.context;
            VolumeType create = companion.create(context, getStreamTypeFromString(context, str));
            Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(str2);
            statefulBuilder.mStatus = create.getStatus();
            statefulBuilder.mStatusCode = create.getStatusCode();
            statefulBuilder.mTemplate = new SliderTemplate(create.getMinVolume(), create.getMaxVolume(), create.getVolume(), 1.0f, null);
            return statefulBuilder.build();
        }
        if (!StringsKt__StringsKt.contains(str, MUTE_ACTION_PREFIX, false)) {
            if (!Intrinsics.areEqual(str, "control_music")) {
                return null;
            }
            Command.StatefulBuilder statefulBuilder2 = new Command.StatefulBuilder(str2);
            statefulBuilder2.mStatus = 1;
            statefulBuilder2.mTemplate = new MediaControlTemplate(1, 1023, "");
            return statefulBuilder2.build();
        }
        VolumeType.Companion companion2 = VolumeType.Companion;
        Context context2 = this.context;
        VolumeType create2 = companion2.create(context2, getStreamTypeFromString(context2, str));
        Command.StatefulBuilder statefulBuilder3 = new Command.StatefulBuilder(str2);
        statefulBuilder3.mStatus = 1;
        statefulBuilder3.mTemplate = new ToggleTemplate(create2.isStreamMute());
        return statefulBuilder3.build();
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public void performCommandActionInteractor(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback) {
        CommandActionResponse mute;
        if (matchAction(str)) {
            Log.d(TAG, "performCommandActionInteractor ".concat(str));
            int actionType = commandAction.getActionType();
            if (actionType == 1) {
                VolumeType.Companion companion = VolumeType.Companion;
                Context context = this.context;
                mute = companion.create(context, getStreamTypeFromString(context, str)).setMute(((BooleanAction) commandAction).mNewState);
            } else if (actionType == 2) {
                VolumeType.Companion companion2 = VolumeType.Companion;
                Context context2 = this.context;
                mute = companion2.create(context2, getStreamTypeFromString(context2, str)).setVolume((int) commandAction.getDataBundle().getFloat(KEY_NEW_VALUE), 5, false);
            } else if (actionType != 6) {
                mute = new CommandActionResponse(2, "invalid_action");
            } else {
                ModeAction modeAction = (ModeAction) commandAction;
                mute = MediaCommandType.Companion.create(this.context, modeAction.mNewMode, MediaParamsParser.getMediaInfoFromJson(modeAction.mExtraValue), new AudioManagerWrapper(this.context), (MediaSessionManager) this.context.getSystemService("media_session")).action();
            }
            ((CommandProvider.C47571) iCommandActionCallback).onActionFinished(mute.responseCode, mute.responseMessage);
        }
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) {
        if (!matchAction(str)) {
            return null;
        }
        Log.d(TAG, "loadStateful in MusicActionInteractor(with CommandAction) action=" + str + ", cmdAction = " + commandAction);
        boolean contains = StringsKt__StringsKt.contains(str, "volume_control", false);
        String str2 = command.mCommandId;
        if (contains) {
            VolumeType.Companion companion = VolumeType.Companion;
            Context context = this.context;
            VolumeType create = companion.create(context, getStreamTypeFromString(context, str));
            Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(str2);
            statefulBuilder.mStatus = create.getStatus();
            statefulBuilder.mStatusCode = create.getStatusCode();
            statefulBuilder.mTemplate = new SliderTemplate(create.getMinVolume(), create.getMaxVolume(), create.getVolume(), 1.0f, null);
            return statefulBuilder.build();
        }
        if (StringsKt__StringsKt.contains(str, MUTE_ACTION_PREFIX, false)) {
            VolumeType.Companion companion2 = VolumeType.Companion;
            Context context2 = this.context;
            VolumeType create2 = companion2.create(context2, getStreamTypeFromString(context2, str));
            Command.StatefulBuilder statefulBuilder2 = new Command.StatefulBuilder(str2);
            statefulBuilder2.mStatus = 1;
            statefulBuilder2.mTemplate = new ToggleTemplate(create2.isStreamMute());
            return statefulBuilder2.build();
        }
        if (!Intrinsics.areEqual(str, "control_music")) {
            return null;
        }
        Command.StatefulBuilder statefulBuilder3 = new Command.StatefulBuilder(str2);
        statefulBuilder3.mStatus = 1;
        statefulBuilder3.mTemplate = new MediaControlTemplate(1, 1023, "");
        return statefulBuilder3.build();
    }
}
