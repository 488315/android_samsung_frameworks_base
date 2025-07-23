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
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MusicControlActionInteractor implements ActionInteractor {
    private static final String KEY_NEW_VALUE = "key_new_value";
    private static final String MUTE_ACTION_PREFIX = "mute";
    public static final int STREAM_ALL = 31;
    public static final int STREAM_BLUETOOTH = 30;
    private static final int SUPPORTED_FLAG = 1023;
    private static final String TAG = "MusicControlActionInteractor";
    private AudioManagerWrapper audioManagerWrapper;
    private Context context;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Action {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ Action[] $VALUES;
        public static final Action volume_control = new Action("volume_control", 0);
        public static final Action volume_control_ringtone = new Action("volume_control_ringtone", 1);
        public static final Action volume_control_media = new Action("volume_control_media", 2);
        public static final Action volume_control_noti = new Action("volume_control_noti", 3);
        public static final Action volume_control_system = new Action("volume_control_system", 4);
        public static final Action volume_control_bixby = new Action("volume_control_bixby", 5);
        public static final Action volume_control_bluetooth = new Action("volume_control_bluetooth", 6);
        public static final Action mute_volume = new Action("mute_volume", 7);
        public static final Action mute_all_volume = new Action("mute_all_volume", 8);
        public static final Action mute_ringtones_volume = new Action("mute_ringtones_volume", 9);
        public static final Action mute_media_volume = new Action("mute_media_volume", 10);
        public static final Action mute_noti_volume = new Action("mute_noti_volume", 11);
        public static final Action mute_system_volume = new Action("mute_system_volume", 12);
        public static final Action mute_bixby_volume = new Action("mute_bixby_volume", 13);
        public static final Action mute_bluetooth_volume = new Action("mute_bluetooth_volume", 14);
        public static final Action control_music = new Action("control_music", 15);

        private static final /* synthetic */ Action[] $values() {
            return new Action[]{volume_control, volume_control_ringtone, volume_control_media, volume_control_noti, volume_control_system, volume_control_bixby, volume_control_bluetooth, mute_volume, mute_all_volume, mute_ringtones_volume, mute_media_volume, mute_noti_volume, mute_system_volume, mute_bixby_volume, mute_bluetooth_volume, control_music};
        }

        static {
            Action[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private Action(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static Action valueOf(String str) {
            return (Action) Enum.valueOf(Action.class, str);
        }

        public static Action[] values() {
            return (Action[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public MusicControlActionInteractor(Context context) {
        this.context = context;
        this.audioManagerWrapper = new AudioManagerWrapper(context);
    }

    private final int getStreamTypeFromString(Context context, String str) {
        switch (str.hashCode()) {
            case -1961507235:
                return !str.equals("mute_noti_volume") ? -1 : 5;
            case -1853328508:
                return !str.equals("mute_system_volume") ? -1 : 1;
            case -1719581065:
                return !str.equals("mute_bixby_volume") ? -1 : 11;
            case -1477758307:
                return !str.equals("volume_control_noti") ? -1 : 5;
            case -711551941:
                return !str.equals("mute_media_volume") ? -1 : 3;
            case -682366098:
                return !str.equals("mute_ringtones_volume") ? -1 : 2;
            case -610069815:
                return !str.equals("volume_control_ringtone") ? -1 : 2;
            case -84029337:
                return !str.equals("volume_control_bluetooth") ? -1 : 30;
            case 437139768:
                if (str.equals("volume_control")) {
                    return this.audioManagerWrapper.getAdjustedStreamType(context);
                }
                return -1;
            case 988376414:
                return !str.equals("mute_all_volume") ? -1 : 31;
            case 1232193329:
                return !str.equals("mute_bluetooth_volume") ? -1 : 30;
            case 1422875489:
                return !str.equals("volume_control_bixby") ? -1 : 11;
            case 1432896029:
                return !str.equals("volume_control_media") ? -1 : 3;
            case 1660806934:
                return !str.equals("volume_control_system") ? -1 : 1;
            default:
                return -1;
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
        if (StringsKt__StringsKt.contains(str, "volume_control", false)) {
            VolumeType.Companion companion = VolumeType.Companion;
            Context context = this.context;
            VolumeType create = companion.create(context, getStreamTypeFromString(context, str));
            Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder.mStatus = create.getStatus();
            statefulBuilder.mStatusCode = create.getStatusCode();
            statefulBuilder.mTemplate = new SliderTemplate(create.getMinVolume(), create.getMaxVolume(), create.getVolume(), 1.0f, null);
            return statefulBuilder.build();
        }
        if (!StringsKt__StringsKt.contains(str, MUTE_ACTION_PREFIX, false)) {
            if (!Intrinsics.areEqual(str, "control_music")) {
                return null;
            }
            Command.StatefulBuilder statefulBuilder2 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder2.mStatus = 1;
            statefulBuilder2.mTemplate = new MediaControlTemplate(1, 1023, "");
            return statefulBuilder2.build();
        }
        VolumeType.Companion companion2 = VolumeType.Companion;
        Context context2 = this.context;
        VolumeType create2 = companion2.create(context2, getStreamTypeFromString(context2, str));
        Command.StatefulBuilder statefulBuilder3 = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder3.mStatus = 1;
        statefulBuilder3.mTemplate = new ToggleTemplate(create2.isStreamMute());
        return statefulBuilder3.build();
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public void performCommandActionInteractor(String str, CommandAction commandAction, ICommandActionCallback iCommandActionCallback) {
        CommandActionResponse mute;
        if (matchAction(str)) {
            Log.d(TAG, "performCommandActionInteractor " + str);
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
            ((CommandProvider.AnonymousClass1) iCommandActionCallback).onActionFinished(mute.responseCode, mute.responseMessage);
        }
    }

    @Override // com.android.systemui.bixby2.interactor.ActionInteractor
    public Command loadStatefulCommandInteractor(String str, Command command, CommandAction commandAction) {
        if (!matchAction(str)) {
            return null;
        }
        Log.d(TAG, "loadStateful in MusicActionInteractor(with CommandAction) action=" + str + ", cmdAction = " + commandAction);
        if (StringsKt__StringsKt.contains(str, "volume_control", false)) {
            VolumeType.Companion companion = VolumeType.Companion;
            Context context = this.context;
            VolumeType create = companion.create(context, getStreamTypeFromString(context, str));
            Command.StatefulBuilder statefulBuilder = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder.mStatus = create.getStatus();
            statefulBuilder.mStatusCode = create.getStatusCode();
            statefulBuilder.mTemplate = new SliderTemplate(create.getMinVolume(), create.getMaxVolume(), create.getVolume(), 1.0f, null);
            return statefulBuilder.build();
        }
        if (StringsKt__StringsKt.contains(str, MUTE_ACTION_PREFIX, false)) {
            VolumeType.Companion companion2 = VolumeType.Companion;
            Context context2 = this.context;
            VolumeType create2 = companion2.create(context2, getStreamTypeFromString(context2, str));
            Command.StatefulBuilder statefulBuilder2 = new Command.StatefulBuilder(command.mCommandId);
            statefulBuilder2.mStatus = 1;
            statefulBuilder2.mTemplate = new ToggleTemplate(create2.isStreamMute());
            return statefulBuilder2.build();
        }
        if (!Intrinsics.areEqual(str, "control_music")) {
            return null;
        }
        Command.StatefulBuilder statefulBuilder3 = new Command.StatefulBuilder(command.mCommandId);
        statefulBuilder3.mStatus = 1;
        statefulBuilder3.mTemplate = new MediaControlTemplate(1, 1023, "");
        return statefulBuilder3.build();
    }
}
